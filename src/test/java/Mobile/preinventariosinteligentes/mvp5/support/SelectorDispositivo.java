package Mobile.preinventariosinteligentes.mvp5.support;

/**
 * Selecciona el perfil físico sin duplicar scripts por handheld y tablet.
 *
 * @author Marcos Morales
 * @date dd/mm/yyyy
 */
public final class SelectorDispositivo {
    /** Impide instanciar el selector de perfiles. */
    private SelectorDispositivo() {
    }

    /** Ejecuta o consulta la operación {@code resolver} del soporte MVP5. */
    public static TipoDispositivo resolver(Configuraciones configuracion) {
        String solicitado = configuracion.opcionalCapabilities("device.tipo", "device.type", "deviceType", "deviceProfile");
        return TipoDispositivo.desde(solicitado);
    }

    /** Ejecuta o consulta la operación {@code resolverUdid} del soporte MVP5. */
    public static String resolverUdid(Configuraciones configuracion, TipoDispositivo tipo) {
        String clavePerfil = "device." + tipo.valorPropiedad() + ".udid";
        String valor = configuracion.opcionalCapabilities("android.udid", "udid", "deviceUdid");
        if (valor != null) {
            return valor;
        }
        return configuracion.requerido(clavePerfil);
    }
}

