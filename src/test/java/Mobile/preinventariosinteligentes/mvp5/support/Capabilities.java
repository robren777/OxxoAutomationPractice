package Mobile.preinventariosinteligentes.mvp5.support;

import java.time.Duration;
import java.util.Map;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;

import utils.AppiumUtil;

/** Fábrica única de sesión Appium para handheld TC52 y tablet ET51.
 * @author Marcos Morales
 * @date dd/mm/yyyy */
public final class Capabilities {
    /** Impide instanciar la fabrica de capacidades. */
    private Capabilities() {
    }

    /** Ejecuta o consulta la operación {@code abrir} del soporte MVP5. */
    public static SesionAppium abrir(Map<String, String> datosPrueba) {
        Configuraciones configuracion = Configuraciones.cargar(datosPrueba);
        TipoDispositivo tipoDispositivo = SelectorDispositivo.resolver(configuracion);
        String nombrePaquete = configuracion.requeridoCapabilities(
                "android.appPackage", "appPackage");

        DesiredCapabilities capacidades = new DesiredCapabilities();
        capacidades.setCapability("platformName",
                configuracion.requeridoCapabilities("android.platformName", "platformName"));
        capacidades.setCapability("appium:automationName",
                configuracion.requeridoCapabilities("android.automationName", "automationName"));
        capacidades.setCapability("appium:platformVersion",
                configuracion.requeridoCapabilities("android.platformVersion", "platformVersion"));
        capacidades.setCapability("appium:udid",
                SelectorDispositivo.resolverUdid(configuracion, tipoDispositivo));
        String nombreDispositivo = configuracion.opcionalCapabilities("android.deviceName", "deviceName");
        if (nombreDispositivo == null) {
            nombreDispositivo = configuracion.requerido("device." + tipoDispositivo.valorPropiedad() + ".model");
        }
        capacidades.setCapability("appium:deviceName", nombreDispositivo);
        capacidades.setCapability("appium:appPackage", nombrePaquete);
        // El ciclo se controla despues de crear la sesion para evitar
        // el patron abrir-cerrar-abrir de Appium.
        capacidades.setCapability("appium:autoLaunch", false);
        String actividad = configuracion.opcionalCapabilities("android.appActivity", "appActivity");
        if (actividad != null) {
            capacidades.setCapability("appium:appActivity", actividad);
        }
        capacidades.setCapability("appium:noReset",
                configuracion.valorBooleano("android.noReset", true));

        try {
            AppiumUtil appium = new AppiumUtil(configuracion.requerido("appium.url"), capacidades);
            appium.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(
                    configuracion.valorLong("timeouts.implicit.seconds", 4L)));
            return new SesionAppium(appium, configuracion, nombrePaquete);
        } catch (SessionNotCreatedException excepcion) {
            throw new IllegalStateException("No fue posible crear la sesión Appium para " + tipoDispositivo, excepcion);
        } catch (Exception excepcion) {
            throw new IllegalStateException("No fue posible inicializar Appium para " + tipoDispositivo, excepcion);
        }
    }
}
