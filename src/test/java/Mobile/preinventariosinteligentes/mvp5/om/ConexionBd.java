package Mobile.preinventariosinteligentes.mvp5.om;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Mobile.preinventariosinteligentes.mvp5.support.Configuraciones;
import Mobile.util.GlobalVariables;

/**
 * Resolucion de conexion BD para casos MVP5.
 */
final class ConexionBd {
    private final Configuraciones configuracion =
            Configuraciones.cargar(null);

    Connection abrirConexionSatelitesMvp5() {
        try {
            return DriverManager.getConnection(urlSatelitesMvp5(), usuarioBdMvp5(), passwordBdMvp5());
        } catch (SQLException ex) {
            throw new RuntimeException("Error al conectar a la base de datos (MVP5)", ex);
        }
    }

    void cerrarConexionMvp5(Connection conexion) {
        if (conexion == null) {
            return;
        }
        try {
            if (!conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error al cerrar la conexion (MVP5)", ex);
        }
    }

    private String urlSatelitesMvp5() {
        String urlDirecta = valorConfig("preinv.db.satelites.url");
        if (esValorConfig(urlDirecta)) {
            return urlDirecta;
        }
        String host = valorConfig("preinv.db.host");
        if (esValorConfig(host)) {
            return "jdbc:sqlserver://" + host + ";databaseName=Satelites;encrypt=true;trustServerCertificate=true";
        }
        return GlobalVariables.urlPI_Satelites;
    }

    private String usuarioBdMvp5() {
        String usuario = valorConfig("preinv.db.user");
        return esValorConfig(usuario) ? usuario : GlobalVariables.userDB_PI;
    }

    private String passwordBdMvp5() {
        String password = valorConfig("preinv.db.password");
        return esValorConfig(password) ? password : GlobalVariables.passDB_PI;
    }

    private String valorConfig(String propiedad) {
        // Configuraciones resuelve JVM > entorno > archivo properties.
        // GlobalVariables permanece como fallback en los metodos llamadores.
        return configuracion.opcional(propiedad);
    }

    private static boolean esValorConfig(String valor) {
        return valor != null && !valor.trim().isEmpty();
    }
}
