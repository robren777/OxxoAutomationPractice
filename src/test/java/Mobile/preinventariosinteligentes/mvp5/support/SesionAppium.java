package Mobile.preinventariosinteligentes.mvp5.support;

import io.appium.java_client.android.AndroidDriver;
import utils.AppiumUtil;

/**
 * Contexto de una ejecución MVP5 y cierre seguro de la aplicación.
 *
 * @author Marcos Morales
 * @date dd/mm/yyyy
 */
public final class SesionAppium implements AutoCloseable {
    private final AppiumUtil appium;
    private final Configuraciones configuracion;
    private final String nombrePaquete;

    /** Conserva la sesión Appium y los datos necesarios para cerrarla de forma segura. */
    SesionAppium(AppiumUtil appium, Configuraciones configuracion, String nombrePaquete) {
        this.appium = appium;
        this.configuracion = configuracion;
        this.nombrePaquete = nombrePaquete;
    }

    /** Ejecuta o consulta la operación {@code appium} del soporte MVP5. */
    public AppiumUtil appium() {
        return appium;
    }

    /** Ejecuta o consulta la operación {@code configuracion} del soporte MVP5. */
    public Configuraciones configuracion() {
        return configuracion;
    }


    /** Ejecuta o consulta la operación {@code nombrePaquete} del soporte MVP5. */
    public String nombrePaquete() {
        return nombrePaquete;
    }

    @Override
    public void close() {
        if (appium == null || appium.driver == null) {
            return;
        }
        try {
            if (appium.driver instanceof AndroidDriver) {
                CicloAplicacion.cerrar(
                        (AndroidDriver) appium.driver,
                        nombrePaquete);
            }
        } catch (RuntimeException ignorada) {
            // El cierre de la sesión aún debe intentarse.
        } finally {
            appium.driver.quit();
        }
    }
}
