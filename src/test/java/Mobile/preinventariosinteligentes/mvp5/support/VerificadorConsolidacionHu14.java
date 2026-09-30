package Mobile.preinventariosinteligentes.mvp5.support;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Verifica de forma local y de solo lectura la cardinalidad de solicitudes
 * ExecuteConsolidation escritas por XPOS Service API durante un caso HU14.
 */
public final class VerificadorConsolidacionHu14 {
    private static final String INICIO_SOLICITUD = "Request starting";
    private static final String OPERACION = "/ExecuteConsolidation";

    private final Path archivoLog;
    private long posicionInicial = -1L;

    public VerificadorConsolidacionHu14(String rutaArchivoLog) {
        if (rutaArchivoLog == null || rutaArchivoLog.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "La ruta del log XPOS no puede estar vacia");
        }
        archivoLog = Paths.get(rutaArchivoLog.trim()).toAbsolutePath().normalize();
        validarArchivo();
    }

    /** Registra el tamano actual para ignorar ejecuciones anteriores. */
    public void marcarInicio() throws IOException {
        validarArchivo();
        posicionInicial = Files.size(archivoLog);
    }

    /** Cuenta solo solicitudes nuevas registradas desde la marca del caso. */
    public Resultado verificarSolicitudesNuevas() throws IOException {
        if (posicionInicial < 0L) {
            throw new IllegalStateException(
                    "Debe registrarse la posicion inicial antes de verificar el log");
        }
        validarArchivo();
        long tamanoActual = Files.size(archivoLog);
        if (tamanoActual < posicionInicial) {
            throw new IllegalStateException(
                    "El log XPOS fue reemplazado o truncado durante la ejecucion");
        }

        int solicitudes = 0;
        try (RandomAccessFile lector = new RandomAccessFile(
                archivoLog.toFile(), "r")) {
            lector.seek(posicionInicial);
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (linea.contains(INICIO_SOLICITUD)
                        && linea.contains(OPERACION)) {
                    solicitudes++;
                }
            }
        }
        return new Resultado(solicitudes, tamanoActual - posicionInicial);
    }

    private void validarArchivo() {
        if (!Files.isRegularFile(archivoLog)
                || !Files.isReadable(archivoLog)) {
            throw new IllegalArgumentException(
                    "El archivo de log XPOS configurado no existe o no es legible");
        }
    }

    /** Resultado seguro para evidencia; no expone lineas, URLs ni deviceId. */
    public static final class Resultado {
        private final int solicitudes;
        private final long bytesAnalizados;

        private Resultado(int solicitudes, long bytesAnalizados) {
            this.solicitudes = solicitudes;
            this.bytesAnalizados = bytesAnalizados;
        }

        public int solicitudes() {
            return solicitudes;
        }

        public String evidencia() {
            return "EVENTO|SOLICITUDES_NUEVAS|BYTES_ANALIZADOS|RESULTADO\n"
                    + "ExecuteConsolidation|"
                    + solicitudes + "|"
                    + bytesAnalizados + "|"
                    + (solicitudes == 1 ? "UNICA" : "CARDINALIDAD_INVALIDA");
        }
    }
}
