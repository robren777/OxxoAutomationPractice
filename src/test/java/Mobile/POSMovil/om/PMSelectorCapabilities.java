package Mobile.POSMovil.om;

import java.util.Properties;

public class PMSelectorCapabilities {

    public static String getUdid(Properties props) {

        // 1. Parametro
        String udid = System.getProperty("android.udid");
        if (isValid(udid)) {
            log("UDID tomado de JVM param (-Dandroid.udid)");
            return udid;
        }

        // 2. Variable de entorno
        udid = System.getenv("ANDROID_UDID");
        if (isValid(udid)) {
            log("UDID tomado de ENV var (ANDROID_UDID)");
            return udid;
        }

        // 3. Default: dispositivo fisico
        udid = props.getProperty("android.real.udid");
        if (isValid(udid)) {
            log("UDID tomado de properties (android.real.udid)");
            return udid;
        }

        throw new RuntimeException("No se pudo resolver el UDID para Android");
    }

    private static boolean isValid(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private static void log(String msg) {
        System.out.println("[UDID-RESOLVER] " + msg);
    }
}

