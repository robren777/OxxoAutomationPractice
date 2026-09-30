package Mobile.preinventariosinteligentes.mvp5.support;

import Mobile.util.GlobalVariables;

/**
 * TAG_PREINV_DB_CONFIG_V1: resolución dinámica de conexión BD para
 * preinventarios sin modificar GlobalVariables por ambiente.
 */
public final class ConfiguracionBd {

    private ConfiguracionBd() {
    }

    /** URL JDBC para la BD Satelites de preinventarios. */
    public static String urlSatelites() {
        String urlDirecta = leer("preinv.db.satelites.url", "PREINV_DB_SATELITES_URL");
        if (esValida(urlDirecta)) {
            return urlDirecta;
        }
        String host = leer("preinv.db.host", "PREINV_DB_HOST");
        if (esValida(host)) {
            return "jdbc:sqlserver://" + host + ";databaseName=Satelites;encrypt=true;trustServerCertificate=true";
        }
        return GlobalVariables.urlPI_Satelites;
    }

    /** URL JDBC opcional para XposStore, útil en scripts de preparación. */
    public static String urlXposStore() {
        String urlDirecta = leer("preinv.db.xpos.url", "PREINV_DB_XPOS_URL");
        if (esValida(urlDirecta)) {
            return urlDirecta;
        }
        String host = leer("preinv.db.host", "PREINV_DB_HOST");
        if (esValida(host)) {
            return "jdbc:sqlserver://" + host + ";databaseName=XposStore;encrypt=true;trustServerCertificate=true";
        }
        return GlobalVariables.urlPI_XposStore;
    }

    public static String usuario() {
        String usuario = leer("preinv.db.user", "PREINV_DB_USER");
        return esValida(usuario) ? usuario : GlobalVariables.userDB_PI;
    }

    public static String password() {
        String password = leer("preinv.db.password", "PREINV_DB_PASSWORD");
        return esValida(password) ? password : GlobalVariables.passDB_PI;
    }

    private static String leer(String propiedad, String entorno) {
        String valor = System.getProperty(propiedad);
        if (esValida(valor)) {
            return valor.trim();
        }
        valor = System.getenv(entorno);
        return esValida(valor) ? valor.trim() : null;
    }

    private static boolean esValida(String valor) {
        return valor != null && !valor.trim().isEmpty();
    }
}
