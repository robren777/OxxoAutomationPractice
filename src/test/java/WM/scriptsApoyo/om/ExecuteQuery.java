package WM.scriptsApoyo.om;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.tigervnc.rdr.Exception;

import modelo.BaseExecution;
import modelo.TestCase;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ExecuteQuery extends BaseExecution {

	SQLResult ExecuteQuery;
	private boolean StatusQuery;
	private TestCase testCase;

	public ExecuteQuery(TestCase testCase) {
		this.testCase = testCase;
	}
	
	/**
	 * Ejecuta una query y regresa el estatus de la conexion
	 * 
	 * @param BD
	 * @param query
	 * @return !StatusQuery
	 * @throws SQLException
	 */
	public boolean validarConexion(SQLUtil BD) throws SQLException {
		try {
			System.out.println("Conexion:" + BD.getConn().toString());
			if (!BD.getConn().isClosed()) {
				testCase.addTextEvidenceCurrentStep("Conexion: " + BD.getConn().toString());
				testCase.addBoldTextEvidenceCurrentStep(BD.getHost());

			} else {
				testCase.addTextEvidenceCurrentStep("No se encuentra la conexion a la BAse de datos");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return !BD.getConn().isClosed();
	}

	/**
	 * Ejecuta una query y regresa el estatus de la conexion
	 * 
	 * @param BD
	 * @param query
	 * @return !StatusQuery
	 * @throws SQLException
	 */
	public boolean ejecutarConsulta(SQLUtil BD, String query) throws SQLException {
		try {
			System.out.println("#CONSULTA#: " + query);
			System.out.println(" ");
			ExecuteQuery = executeQuery(BD, query);

			StatusQuery = ExecuteQuery.isEmpty();

			if (!StatusQuery) {
				testCase.addTextEvidenceCurrentStep("Se encontraron resultados con la consulta");
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
			}

			else {
				System.out.println("No se encontraron registros");
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return !StatusQuery;
	}



	/**
	 * Ejecuta consultas SQL separando la consulta por columnas para que sea legible
	 * en los reportes
	 * 
	 * @author 1000068915
	 */
	public Boolean executeQuerySeparatedColumns(SQLUtil db, String query) throws SQLException {
		try {
			System.out.println("#Query principal#: " + query);
			ExecuteQuery = executeQuery(db, query);

			Boolean empty = ExecuteQuery.isEmpty();

			if (!empty) {

				int columnCount = ExecuteQuery.getColumnCount();
				System.out.println("#Numero de columnas#: " + columnCount);
				List<String> columnNames = new ArrayList<>();

				columnNames.addAll(ExecuteQuery.getColumnNames());

				// Separar columnas de 5 en 5 y ejecuta las consultas
				int currentIndex = 0;
				while (currentIndex < columnNames.size()) {

					int endIndex = Math.min(currentIndex + 5, columnNames.size());
					List<String> currentColumns = columnNames.subList(currentIndex, endIndex);
					String listString = currentColumns.stream().collect(Collectors.joining(", "));
					// Crear una nueva consulta con las columnas actuales
					String currentQuery = query.replace("*", listString);

					System.out.println("#Consulta Modificada#: " + currentQuery);
					ExecuteQuery = executeQuery(db, currentQuery);
					empty = ExecuteQuery.isEmpty();

					if (!empty) {
						testCase.addTextEvidenceCurrentStep("Se encontraron resultados con la consulta");
						testCase.addQueryEvidenceCurrentStep(ExecuteQuery, true);
					}

					else {
						System.out.println("No se encontraron registros");
						testCase.addQueryEvidenceCurrentStep(ExecuteQuery, true);
						return false;
					}
					currentIndex += 5;
				}
				System.out.println("");
				return true;
			} else {
				System.out.println("No se encontraron registros");
				testCase.addBoldTextEvidenceCurrentStep("No se han encontrado registros con el query: " + query);
				return false;
			}

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			return false;
		}
	}

	/**
	 * Consulta el total de registros de una consulta
	 * 
	 * @param BD
	 * @param query
	 * @return !StatusQuery
	 * @throws SQLException
	 */
	public int getRowNum() throws SQLException {
		int rowNum = 0;
		try {
			rowNum = ExecuteQuery.getAllData().size();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return rowNum;
	}

	
	

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
