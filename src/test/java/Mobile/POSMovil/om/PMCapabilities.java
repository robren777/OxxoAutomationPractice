package Mobile.POSMovil.om;

import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.remote.DesiredCapabilities;

import utils.AppiumUtil;

public class PMCapabilities {

	protected static Properties props;

	public static AppiumUtil initializeAppium(String activity) throws Exception {

    Properties props = new Properties();
    String propFileName = "config/pm.device.properties";

    try (InputStream inputStream =
            PMCapabilities.class
                    .getClassLoader()
                    .getResourceAsStream(propFileName)) {

        if (inputStream == null) {
            throw new RuntimeException(
                    "No se encontró el archivo " + propFileName + " en classpath");
        }

        props.load(inputStream);

        DesiredCapabilities cap = new DesiredCapabilities();

        // Capacidad estándar W3C
        cap.setCapability("platformName", "Android");

        // Capacidades específicas de Appium
        cap.setCapability(
                "appium:automationName",
                props.getProperty("android.automationName"));

        cap.setCapability(
                "appium:platformVersion",
                props.getProperty("android.platformVersion"));

        String udid = System.getProperty("android.udid");

        if (udid == null || udid.isBlank()) {
            udid = PMSelectorCapabilities.getUdid(props);
        }

        if (udid == null || udid.isBlank()) {
            throw new IllegalArgumentException(
                    "No se pudo determinar el UDID del dispositivo");
        }

        udid = udid.trim();

        System.out.println("UDID utilizado: " + udid);

        cap.setCapability("appium:udid", udid);

        String deviceName = props.getProperty("android.deviceName");

        if (deviceName != null && !deviceName.isBlank()) {
            cap.setCapability("appium:deviceName", deviceName);
        }

        cap.setCapability(
                "appium:appPackage",
                props.getProperty("android.appPackage"));

        String appActivity;

        switch (activity.toLowerCase()) {
            case "splash":
                appActivity = props.getProperty("android.appSplashActivity");
                break;

            case "login":
                appActivity = props.getProperty("android.appLoginActivity");
                break;

            default:
                throw new IllegalArgumentException(
                        "Opción inválida: " + activity);
        }

        cap.setCapability("appium:appActivity", appActivity);
        cap.setCapability("appium:noReset", true);

        String url = "http://127.0.0.1:4723";

        return new AppiumUtil(url, cap);

    } catch (Exception e) {
        throw new RuntimeException("Error inicializando Appium", e);
    }
}
}
