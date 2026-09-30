package Mobile.preinventariosinteligentes.mvp5.support;

import Mobile.preinventariosinteligentes.mvp5.support.ConsultaSelectivoHu24.Escenario;
import Mobile.preinventariosinteligentes.mvp5.support.ConsultaSelectivoHu24.EscenarioEdicion;

/**
 * Preflight backend para HU24 sin afectar scripts oficiales.
 */
public final class PreflightHu24Support {

    public Escenario validarEscenarioSelectivoCompleto(ConsultaSelectivoHu24 datosHu24) {
        return validarEscenarioSelectivoCompleto(datosHu24, 15, "A", false);
    }

    /**
     * Intenta primero con selectivos pequenos y despues cae a cualquier tamano.
     * Evita disparar clasificacion/distribucion UI cuando ya existe distribucion vigente.
     */
    public Escenario validarEscenarioSelectivoCompleto(
            ConsultaSelectivoHu24 datosHu24,
            int maximoSkuPreferente) {
        return validarEscenarioSelectivoCompleto(
                datosHu24,
                maximoSkuPreferente,
                "A",
                false);
    }

    /**
     * Intenta primero con selectivos cercanos a la letra objetivo y luego cae a
     * cualquier selectivo pendiente.
     */
    public Escenario validarEscenarioSelectivoCompleto(
            ConsultaSelectivoHu24 datosHu24,
            int maximoSkuPreferente,
            String letraReferencia) {
        return validarEscenarioSelectivoCompleto(
                datosHu24,
                maximoSkuPreferente,
                letraReferencia,
                false);
    }

    /**
     * @param permitirFallbackSinLimite true para permitir selectivos mayores al
     *                                  maximo preferente; false para fallar si no
     *                                  existe candidato dentro del rango.
     */
    public Escenario validarEscenarioSelectivoCompleto(
            ConsultaSelectivoHu24 datosHu24,
            int maximoSkuPreferente,
            String letraReferencia,
            boolean permitirFallbackSinLimite) {
        int limitePreferente = maximoSkuPreferente <= 0 ? 15 : maximoSkuPreferente;
        Escenario escenario = datosHu24.consultarAsignadoCercanoALetraSinFiltro(
                letraReferencia,
                1,
                limitePreferente);
        if (escenario != null) {
            return escenario;
        }

        escenario = datosHu24.consultarAsignadoConRangoSinFiltro(1, limitePreferente);
        if (escenario != null) {
            return escenario;
        }

        if (!permitirFallbackSinLimite) {
            throw new IllegalStateException(
                    "Preflight HU24-001: no existe selectivo entre 1 y "
                            + limitePreferente
                            + " SKU con pendientes positivos en la distribucion vigente");
        }

        escenario = datosHu24.consultarAsignadoCercanoALetraSinFiltro(
                letraReferencia,
                1,
                0);
        if (escenario != null) {
            return escenario;
        }

        escenario = datosHu24.consultarAsignadoConRangoSinFiltro(1, 0);
        if (escenario == null) {
            throw new IllegalStateException(
                    "Preflight HU24-001: no existe selectivo con pendientes positivos en la distribucion vigente");
        }
        return escenario;
    }

    public EscenarioEdicion validarEscenarioSkuCapturado(ConsultaSelectivoHu24 datosHu24) {
        EscenarioEdicion escenario = datosHu24.consultarSkuCapturadoSinFiltro();
        if (escenario == null) {
            throw new IllegalStateException(
                    "Preflight HU24-002: no existe SKU con captura previa en la distribucion vigente");
        }
        return escenario;
    }

    public static String evidenciaEscenario(String caso, int folio, int total, String sku) {
        return "CASO|IO_FOLIO|TOTAL_SKU|SKU|RESULTADO\n"
                + caso + "|" + folio + "|" + total + "|"
                + (sku == null ? "N/A" : sku) + "|DISPONIBLE";
    }
}
