package WM.interfaces.pr36.om;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import utils.sql.SQLResult;

public class conexionesSQL {

public static SQLResult sqlExecute(String bd, String query, String user, String password) throws SQLException {
		//Crear conexión
		Connection conn = DriverManager.getConnection(bd,user,password);
		//Ejecutar consulta
		Statement statement = conn.createStatement();
		
		if(statement.execute(query)) {
			SQLResult res = new SQLResult(conn, query);
			return res;
		}else {
			return null;
		}	
	}
}