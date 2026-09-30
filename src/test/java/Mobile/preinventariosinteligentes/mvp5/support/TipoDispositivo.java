package Mobile.preinventariosinteligentes.mvp5.support;

/**
 * Tipos de dispositivo soportados por los mismos scripts de MVP5.
 *
 * @author Marcos Morales
 * @date dd/mm/yyyy
 */
public enum TipoDispositivo {
    HANDHELD("handheld"),
    TABLET("tablet");

    private final String valorPropiedad;

    /** Asocia el tipo con su valor de configuracion. */
    TipoDispositivo(String valorPropiedad) {
        this.valorPropiedad = valorPropiedad;
    }

    /** Ejecuta o consulta la operación {@code valorPropiedad} del soporte MVP5. */
    public String valorPropiedad() {
        return valorPropiedad;
    }

    /** Ejecuta o consulta la operación {@code desde} del soporte MVP5. */
    public static TipoDispositivo desde(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return HANDHELD;
        }
        for (TipoDispositivo tipo : values()) {
            if (tipo.valorPropiedad.equalsIgnoreCase(valor.trim())
                    || tipo.name().equalsIgnoreCase(valor.trim())) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de dispositivo no soportado: " + valor
                + ". Valores válidos: handheld, tablet");
    }
}

