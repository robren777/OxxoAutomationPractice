package Mobile.preinventariosinteligentes.om;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Mobile.util.GlobalVariables;


public class DatabaseUtil {

	/**
	 * Metodo para establecer la conexion a BD y mantener en un solo lugar las variables
	 * de conexion
	 * @author Marcos Morales
	 */
	public static Connection obtenerConexion() {
		try {
			return DriverManager.getConnection(
					GlobalVariables.urlPI_Satelites, 
					GlobalVariables.userDB_PI, 
					GlobalVariables.passDB_PI
					);
		} catch (SQLException e) {
			throw new RuntimeException("Error al conectar a la base de datos", e);
		}
	}

	/**
	 * Metodo para cerrar la conexion a BD
	 * @author Marcos Morales
	 */
	public static void cerrarConexion(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error al cerrar la conexión", e);
		}
	}
}