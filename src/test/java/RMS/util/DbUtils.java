package RMS.util;

import java.sql.SQLException;

import modelo.TestCase;
import utils.sql.SQLResult;

public class DbUtils {

	@SuppressWarnings("unused")
//	private static final TestCase TestCase = null;
	TestCase testCase;

	@SuppressWarnings("static-access")
	public DbUtils() throws SQLException {

	}

	/*
	 * validarConexion se usa para validar la conexion a una base de datos, dando
	 * como parametro un SQLResult
	 * 
	 * @param connectionResult
	 */
	public boolean validarConexion(SQLResult connectionResult) {
		boolean result = true;

		if (connectionResult.isEmpty()) {
			result = false;
		}

		return result;
	}
}