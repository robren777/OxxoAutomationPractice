package Mobile.preinventariosinteligentes.mvp5.support;

import java.util.Map;

import io.appium.java_client.android.AndroidDriver;

/**
 * TAG_PREINV_BOOTSTRAP_V1: arranque estandar de app y sesion Appium para
 * homologar ejecuciones entre scripts legacy y MVP5.
 */
public final class ArranqueSesion {

    private ArranqueSesion() {
    }

    /** Crea la sesion sin autoarranque y abre una sola vez desde Login. */
    public static SesionAppium abrirSesion(Map<String, String> datos) {
        Configuraciones configuracion = Configuraciones.cargar(datos);
        String appPackage = configuracion.requeridoCapabilities(
                "android.appPackage", "appPackage");
        String appActivity = configuracion.requeridoCapabilities(
                "android.appActivity", "appActivity");
        SesionAppium sesion = Capabilities.abrir(datos);
        try {
            CicloAplicacion.iniciarLimpio(
                    (AndroidDriver) sesion.appium().driver,
                    appPackage,
                    appActivity);
            trazaInterna("Inicio limpio aplicado para paquete=" + appPackage
                    + "|actividad=" + appActivity);
            return sesion;
        } catch (RuntimeException excepcion) {
            sesion.close();
            throw excepcion;
        }
    }

    private static void trazaInterna(String mensaje) {
        System.out.println("[PREINV][INTERNAL][ARRANQUE] " + mensaje);
    }
}
