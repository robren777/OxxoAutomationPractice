package Mobile.preinventariosinteligentes.mvp5.support;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Locale;

/**
 * Activa y restaura escenarios HU14 mediante el controlador QA documentado.
 * No conoce datos de negocio ni implementa respuestas simuladas localmente.
 */
public final class ControlAmbienteHu14 {
    private final URI base;
    private final Duration timeout;
    private final HttpClient cliente;
    private boolean escenarioPreparado;

    public ControlAmbienteHu14(String urlBase, Duration timeout) {
        if (urlBase == null || urlBase.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Falta configuracion qa.environment.control.url");
        }
        if (timeout == null || timeout.isZero() || timeout.isNegative()) {
            throw new IllegalArgumentException(
                    "El timeout del controlador QA debe ser positivo");
        }
        String normalizada = urlBase.trim();
        if (!normalizada.endsWith("/")) {
            normalizada += "/";
        }
        base = URI.create(normalizada);
        if (base.getScheme() == null || base.getHost() == null) {
            throw new IllegalArgumentException(
                    "qa.environment.control.url no es una URL HTTP valida");
        }
        this.timeout = timeout;
        cliente = HttpClient.newBuilder()
                .connectTimeout(timeout)
                .build();
    }

    /** Prepara de forma idempotente el escenario solicitado. */
    public void preparar(String escenario) {
        if (escenario == null
                || !escenario.matches("[a-z0-9-]+")) {
            throw new IllegalArgumentException(
                    "El identificador de escenario HU14 no es valido");
        }
        ejecutar("scenario/" + escenario);
        escenarioPreparado = true;
    }

    /** Restaura el ambiente solo cuando la preparacion fue aprobada. */
    public void restaurar() {
        if (!escenarioPreparado) {
            return;
        }
        ejecutar("restore");
        escenarioPreparado = false;
    }

    public boolean escenarioPreparado() {
        return escenarioPreparado;
    }

    private void ejecutar(String ruta) {
        HttpRequest solicitud = HttpRequest.newBuilder(base.resolve(ruta))
                .timeout(timeout)
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        try {
            HttpResponse<String> respuesta = cliente.send(
                    solicitud,
                    HttpResponse.BodyHandlers.ofString());
            if (respuesta.statusCode() < 200
                    || respuesta.statusCode() >= 300
                    || !respuestaAprobatoria(respuesta.body())) {
                throw new IllegalStateException(
                        "El controlador QA no aprobo la operacion solicitada");
            }
        } catch (IOException excepcion) {
            throw new IllegalStateException(
                    "El controlador QA HU14 no esta disponible", excepcion);
        } catch (InterruptedException excepcion) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(
                    "La operacion del controlador QA HU14 fue interrumpida",
                    excepcion);
        }
    }

    private static boolean respuestaAprobatoria(String cuerpo) {
        if (cuerpo == null) {
            return false;
        }
        String valor = cuerpo.trim().toLowerCase(Locale.ROOT)
                .replaceAll("\\s+", "");
        return "true".equals(valor)
                || "ok".equals(valor)
                || valor.contains("\"success\":true");
    }
}

