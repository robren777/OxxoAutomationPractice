package Mobile.preinventariosinteligentes.mvp5.support;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Resuelve la configuración de MVP5 sin colocar datos de dispositivo dentro
 * de cada script. La prioridad es JVM, entorno, datos provider y propiedades.
 *
 * @author Marcos Morales
 * @date dd/mm/yyyy
 */
public final class Configuraciones {
    private static final String RECURSO = "config/preinventarios-mvp5.device.properties";

    private final Properties propiedades;
    private final Map<String, String> datos;

    /** Conserva las propiedades y datos ya validados. */
    private Configuraciones(Properties propiedades, Map<String, String> datos) {
        this.propiedades = propiedades;
        this.datos = datos;
    }

    /** Ejecuta o consulta la operación {@code cargar} del soporte MVP5. */
    public static Configuraciones cargar(Map<String, String> datosPrueba) {
        Properties propiedades = new Properties();
        try (InputStream entrada = Configuraciones.class.getClassLoader().getResourceAsStream(RECURSO)) {
            if (entrada == null) {
                throw new IllegalStateException("No se encontró el recurso " + RECURSO);
            }
            propiedades.load(entrada);
        } catch (IOException exception) {
            throw new IllegalStateException("No fue posible leer " + RECURSO, exception);
        }
        Map<String, String> datosSeguros = datosPrueba == null
                ? Collections.emptyMap()
                : new HashMap<>(datosPrueba);
        return new Configuraciones(propiedades, datosSeguros);
    }

    /** Ejecuta o consulta la operación {@code requerido} del soporte MVP5. */
    public String requerido(String clave, String... clavesDatos) {
        String valor = opcional(clave, clavesDatos);
        if (!esValido(valor)) {
            throw new IllegalStateException("Falta configuración obligatoria: " + clave);
        }
        return valor;
    }

    /** Ejecuta o consulta la operación {@code opcional} del soporte MVP5. */
    public String opcional(String clave, String... clavesDatos) {
        String valor = System.getProperty(clave);
        if (esValido(valor)) {
            return valor.trim();
        }

        valor = System.getenv(convertirClaveEntorno(clave));
        if (esValido(valor)) {
            return valor.trim();
        }

        for (String claveDatos : clavesDatos) {
            valor = datos.get(claveDatos);
            if (esValido(valor)) {
                return valor.trim();
            }
        }

        valor = propiedades.getProperty(clave);
        return esValido(valor) ? valor.trim() : null;
    }

    /**
     * Resuelve capabilities con prioridad fija: JVM > ENV > properties > data provider.
     */
    public String opcionalCapabilities(String clave, String... clavesDatos) {
        return resolverCapabilities(clave, clavesDatos).valor;
    }

    /** Indica la fuente ganadora con prioridad JVM > ENV > properties > data provider. */
    public String origenCapabilities(String clave, String... clavesDatos) {
        return resolverCapabilities(clave, clavesDatos).origen;
    }

    private Resolucion resolverCapabilities(
            String clave,
            String... clavesDatos) {
        String valor = System.getProperty(clave);
        if (esValido(valor)) {
            return new Resolucion(valor.trim(), "-D");
        }

        valor = System.getenv(convertirClaveEntorno(clave));
        if (esValido(valor)) {
            return new Resolucion(valor.trim(), "ENTORNO");
        }

        valor = propiedades.getProperty(clave);
        if (esValido(valor)) {
            return new Resolucion(valor.trim(), "PROPERTIES");
        }

        for (String claveDatos : clavesDatos) {
            valor = datos.get(claveDatos);
            if (esValido(valor)) {
                return new Resolucion(
                        valor.trim(),
                        "DATA_PROVIDER:" + claveDatos);
            }
        }

        return new Resolucion(null, "AUSENTE");
    }

    /** Versión obligatoria de configuración de capabilities. */
    public String requeridoCapabilities(String clave, String... clavesDatos) {
        String valor = opcionalCapabilities(clave, clavesDatos);
        if (!esValido(valor)) {
            throw new IllegalStateException("Falta configuración obligatoria: " + clave);
        }
        return valor;
    }

    /** Ejecuta o consulta la operación {@code valorBooleano} del soporte MVP5. */
    public boolean valorBooleano(String clave, boolean valorPredeterminado) {
        String valor = opcional(clave);
        return valor == null ? valorPredeterminado : Boolean.parseBoolean(valor);
    }

    /** Ejecuta o consulta la operación {@code valorLong} del soporte MVP5. */
    public long valorLong(String clave, long valorPredeterminado) {
        String valor = opcional(clave);
        return valor == null ? valorPredeterminado : Long.parseLong(valor);
    }


    /** Convierte una clave Java al formato usado por variables de entorno. */
    private static String convertirClaveEntorno(String clave) {
        return clave.toUpperCase().replace('.', '_').replace('-', '_');
    }

    /** Indica si un valor de configuracion contiene informacion util. */
    private static boolean esValido(String valor) {
        return valor != null && !valor.trim().isEmpty();
    }

    private static final class Resolucion {
        private final String valor;
        private final String origen;

        private Resolucion(String valor, String origen) {
            this.valor = valor;
            this.origen = origen;
        }
    }
}
