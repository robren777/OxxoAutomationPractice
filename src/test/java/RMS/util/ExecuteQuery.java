package RMS.util;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.tigervnc.rdr.Exception;

import modelo.BaseExecution;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ExecuteQuery extends BaseExecution {

	SQLResult ExecuteQuery;
	private boolean StatusQuery;
	private TestCase testCase;
	private SQLUtil db;
	private SeleniumUtil u;

	public ExecuteQuery(TestCase testCase) {
		this.testCase = testCase;
	}
	
	public ExecuteQuery(TestCase testCase, SeleniumUtil u) {
		this.testCase = testCase;
		this.u = u;
	}

	/**
	 * Ejecuta una query y regresa el estatus de la conexion
	 * 
	 * @param BD
	 * @param query
	 * @return !StatusQuery
	 * @throws SQLException
	 */
	public boolean validarConexion(SQLUtil BD, String query, String conexionName) throws SQLException {
		try {
			System.out.println("consulta: " + query);
			ExecuteQuery = executeQuery(BD, query);

			StatusQuery = ExecuteQuery.isEmpty();

			if (!StatusQuery) {
				testCase.addTextEvidenceCurrentStep("Conexion:" + conexionName);
				testCase.addBoldTextEvidenceCurrentStep(BD.getHost());

			} else {
				testCase.addTextEvidenceCurrentStep("No se encontro ninguna tabla ");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return !StatusQuery;
	}

	/**
	 * Valida la conexion a la base de datos de RDM
	 * 
	 * @author GilbertoAguayo_Hexa
	 * @throws SQLException
	 */
	public void newValidarConexion(String host, String user, String pass) throws SQLException {
		try {
			db = new SQLUtil(host, user, pass);
			testCase.addTextEvidenceCurrentStep("Se inicio la conexion de manera correcta");
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo la conexion a la base RMS16");
		}
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
	 * Obtiene el numero de filas de una consulta
	 * 
	 * @param BD
	 * @param query
	 * @return !StatusQuery
	 * @throws SQLException
	 */
	public List<String[]> getFilas(SQLUtil BD, String query) throws SQLException {

		List<String[]> filas = new ArrayList<>();
		try {
			System.out.println("#CONSULTA#: " + query);
			System.out.println(" ");
			ExecuteQuery = executeQuery(BD, query);

			StatusQuery = ExecuteQuery.isEmpty();

			if (!StatusQuery) {
				filas = ExecuteQuery.getAllData();

			} else {
				testCase.addTextEvidenceCurrentStep("No se encontro ninguna tabla ");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return filas;
	}

	/**
	 * Obtiene el nombre de las columnas de una consulta
	 * 
	 * @param BD
	 * @param query
	 * @return !StatusQuery
	 * @throws SQLException
	 */
	public ArrayList<String> getColumnas(SQLUtil BD, String query) throws SQLException {

		ArrayList<String> columnas = new ArrayList<>();
		try {
			System.out.println("#CONSULTA#: " + query);
			System.out.println(" ");
			ExecuteQuery = executeQuery(BD, query);

			StatusQuery = ExecuteQuery.isEmpty();

			if (!StatusQuery) {
				columnas = (ArrayList<String>) ExecuteQuery.getColumnNames();

			} else {
				testCase.addTextEvidenceCurrentStep("No se encontro ninguna tabla ");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return columnas;
	}

	/**
	 * Obtiene el nombre de las columnas de una consulta
	 * 
	 * @param BD
	 * @param query
	 * @return !StatusQuery
	 * @throws SQLException
	 */
	public ArrayList<String> getStructure(SQLUtil BD, String query) throws SQLException {

		ArrayList<String> columnas = new ArrayList<>();
		try {
			System.out.println("#CONSULTA#: " + query);
			System.out.println(" ");
			ExecuteQuery = executeQuery(BD, query);

			StatusQuery = ExecuteQuery.isEmpty();

			if (!StatusQuery) {
				columnas = (ArrayList<String>) ExecuteQuery.getColumnNames();

			} else {
				testCase.addTextEvidenceCurrentStep("No se encontro ninguna tabla ");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return columnas;
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
			SQLResult ExecuteQuery = executeQuery(db, query);

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
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery, true);
				return false;
			}

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			return false;
		}
	}

	/**
	 * Ejecuta consultas SQL separando la consulta por columnas para que sea legible
	 * en los reportes
	 * 
	 * A DIFERENCIA DEL ANTERIOR EJECUTA CONSULTA QUE YA TIENEN LAS COLUMNAS
	 * DEFINIDAS (NO ES UN SELECT * FROM)
	 * 
	 * @author 1000068915
	 */
	public Boolean executeQuerySeparatedSpecificColumns(SQLUtil db, String query) throws SQLException {
		try {
			System.out.println("#Query principal#: " + query);
			SQLResult ExecuteQuery = executeQuery(db, query);

			Boolean empty = ExecuteQuery.isEmpty();
			String[] columnasArray = null;

			if (!empty) {

				Pattern pattern = Pattern.compile("SELECT\\s+(.*?)\\s+FROM", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
				Matcher matcher = pattern.matcher(query);

				// Verificar si hay coincidencias
				if (matcher.find()) {
					// Obtener las columnas
					String columnas = matcher.group(1);

					// Dividir las columnas por coma y eliminar espacios en blanco
					columnasArray = columnas.split("\\s*,\\s*");
				}

				int columnCount = columnasArray.length;
				System.out.println("#Numero de columnas#: " + columnCount);

				for (int i = 0; i < columnasArray.length; i += 5) {
					int endIndex = Math.min(i + 5, columnasArray.length);
					String[] subArray = Arrays.copyOfRange(columnasArray, i, endIndex);
					String columnasSubquery = String.join(", ", subArray);

					String subquery = query.replaceFirst("(?i)SELECT .*? FROM", "SELECT " + columnasSubquery + " FROM");

					System.out.println("#Consulta Modificada#: " + subquery);
					ExecuteQuery = executeQuery(db, subquery);
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
				}

				System.out.println("");
				return true;
			} else {
				System.out.println("No se encontraron registros");
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery, true);
				return false;
			}

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			return false;
		}

	}

	/**
	 * Obtiene informacion de un dato en especifico de una consulta utilizando el
	 * nombre de la columna y el index del registro
	 * 
	 * @author 1000068915
	 */
	public String getData(SQLUtil db, String query, int index, String columnName) throws SQLException {
		try {

			//System.out.println(query);
			SQLResult ExecuteQuery = executeQuery(db, query);
			String dato = "";

			Boolean empty = ExecuteQuery.isEmpty();

			if (!empty) {

				dato = ExecuteQuery.getData(index, columnName);
				return dato;
			} else {
				System.out.println("No se encontraron registros");
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery, true);
				throw new AssertionError("No se encontraron registros");
			}

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("La consulta no muestra informacion");
		}
	}

	/**
	 * Suma un numero a un dato en especifico obtenido de una consulta para la
	 * creacion de un nuevo registro
	 * 
	 * @author 1000068915
	 */
	public String sumarStringData(String data, int aumento) {
		int i = Integer.parseInt(data);

		i = i + aumento;

		data = Integer.toString(i);

		return data;

	}

	/**
	 * Ejecuta comandos SQL como un Insert
	 * 
	 * @author 1000068915
	 * @throws SQLException
	 */
	public boolean ejecutarComando(Statement s, String query, String table) throws SQLException {
		try {

			int result= s.executeUpdate(query);
			testCase.addTextEvidenceCurrentStep("Query: " + query);
			testCase.addTextEvidenceCurrentStep("Se inserto "+result+" en la tabla "+table);
			
			return true;
			
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("El comando no se ejecuto correctamente");
		}

	}

	/**
	 * Valida la conexion a la base de datos de RDM
	 * 
	 * @author GilbertoAguayo_Hexa
	 * @throws SQLException
	 */
	public boolean validarConexion2(String dbHost, String dbUser, String dbPass) throws SQLException {
		try {
			db = new SQLUtil(dbHost, dbUser, dbPass);
			testCase.addTextEvidenceCurrentStep("Se inicio la conexion de manera correcta");
			testCase.addBoldTextEvidenceCurrentStep(db.getHost());

			return true;
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo la conexion a la base de datos");
		}
	}
	
	/**
	 * Ejecuta consultas SQL separando la consulta por columnas para que sea legible
	 * en los reportes
	 * 
	 * A DIFERENCIA DEL ANTERIOR EJECUTA CONSULTA QUE YA TIENEN LAS COLUMNAS
	 * DEFINIDAS (NO ES UN SELECT * FROM)
	 * 
	 * ESTE METODO EJECUTA CONSULTAS MODIFICADAS PARA QUE SEAN COMPARADAS EN PASOS POSTERIORES
	 * 
	 * @author 1000068915
	 */
	public Boolean executeQuerySeparatedSpecificColumns2(SQLUtil db, String query) throws SQLException {
		try {
			System.out.println("#Query principal#: " + query);
			SQLResult ExecuteQuery = executeQuery(db, query);

			Boolean empty = ExecuteQuery.isEmpty();
			String[] columnasArray = null;

			if (!empty) {

				Pattern pattern = Pattern.compile("\\(SELECT\\s+(.*?)\\s+ROW_NUMBER\\(\\)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
				Matcher matcher = pattern.matcher(query);

				// Verificar si hay coincidencias
				if (matcher.find()) {
					// Obtener las columnas
					String columnas = matcher.group(1);

					// Dividir las columnas por coma y eliminar espacios en blanco
					columnasArray = columnas.split("\\s*,\\s*");
				}

				int columnCount = columnasArray.length;
				System.out.println("#Numero de columnas#: " + columnCount);

				for (int i = 0; i < columnasArray.length; i += 5) {
					int endIndex = Math.min(i + 5, columnasArray.length);
					String[] subArray = Arrays.copyOfRange(columnasArray, i, endIndex);
					String columnasSubquery = String.join(", ", subArray);

					String subquery = query.replaceFirst("\\(SELECT\\s+(.*?)\\s+ROW_NUMBER\\(\\)", "(SELECT " + columnasSubquery + ", ROW_NUMBER()");

					System.out.println("");
					System.out.println("#Consulta Modificada#" +i+" : " + subquery);
					ExecuteQuery = executeQuery(db, subquery);
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
				}

				System.out.println("");
				return true;
			} else {
				System.out.println("No se encontraron registros");
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery, true);
				return false;
			}

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			return false;
		}

	}
	
	public boolean ejecutarConsulta_N_Intentos(SQLUtil BD, String query, int numeroIntentos) throws SQLException, InterruptedException {
		    int intentos = 0;
		    boolean consultaExitosa = false;
	 
		    while (intentos < numeroIntentos && !consultaExitosa) {
		        try {
		            System.out.println("#CONSULTA INTENTO #"+intentos+": " + query);
		            System.out.println(" ");
		            ExecuteQuery = executeQuery(BD, query);
	 
		            StatusQuery = ExecuteQuery.isEmpty();
	 
		            if (!StatusQuery) {
		                testCase.addTextEvidenceCurrentStep("Se encontro correctamente la tabla");
		                testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		                consultaExitosa = true;
		                break;
		            } else {
		            	 if (intentos == numeroIntentos-1) {
		                     // Se encuentra en el ultimo intento, imprime el mensaje
		                     testCase.addTextEvidenceCurrentStep("No se encontro ninguna tabla ");
		                 }
		            }
	 
		        } catch (Exception e) {
		            System.out.println(e.getMessage());
		        }
		        
		        Thread.sleep(2000);
		        intentos++;
		    }
	 
		    return consultaExitosa;
		}
	
	public boolean ejecutarConsulta_N_IntentosFallo(SQLUtil BD, String query, int numeroIntentos) throws SQLException, InterruptedException {
	    int intentos = 0;
	    boolean consultaExitosa = true;
 
	    while (intentos < numeroIntentos && !consultaExitosa) {
	        try {
	            System.out.println("#CONSULTA INTENTO #"+intentos+": " + query);
	            System.out.println(" ");
	            ExecuteQuery = executeQuery(BD, query);
 
	            StatusQuery = ExecuteQuery.isEmpty();
 
	            if (StatusQuery) {
	                testCase.addTextEvidenceCurrentStep("Se encontro correctamente la tabla");
	                testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
	                consultaExitosa = false;
	                break;
	            } else {
	            	 if (intentos == numeroIntentos-1) {
	                     // Se encuentra en el ultimo intento, imprime el mensaje
	                     testCase.addTextEvidenceCurrentStep("Aparecen registros en la tabla");
	                 }
	            }
 
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	        }
	        
	        Thread.sleep(2000);
	        intentos++;
	    }
 
	    return consultaExitosa;
	}
	
	public boolean ejecutarConsulta2(SQLUtil BD, String query) throws SQLException {
		try {
			System.out.println("#CONSULTA#: " + query);
			System.out.println(" ");
			ExecuteQuery = executeQuery(BD, query);

			StatusQuery = ExecuteQuery.isEmpty();

			if (!StatusQuery) {
				System.out.println("Se encontraron resultados con la consulta");
			}

			else {
				System.out.println("No se encontraron registros");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return !StatusQuery;
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
