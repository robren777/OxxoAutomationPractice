package IMA.TransformacionAbasto.scripts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;

/**
 * PRE-REQUISITOS: 1. Acceso a la base de datos de REPLE: FCREPQA -
 * oxrepledbq00.femcom.net
 * 
 * DESCRIPCION: A) Validar que la tabla FORECAST contenga la información del
 * sistema correspondiente por categoría. B) Validar que la estructura de la
 * tabla FORECAST es la esperada.
 * 
 * @author esmeraldag@hexaware.com
 */

public class ATC_FT_014_ValidacionTablaFORECAST_Outputs_TransformAbasto extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_014_ValidacionTablaFORECAST_Outputs_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22141-TI01_TransformacionAbastoPronostico");

		// Inicializar variables
		int totalRDF = 0;
		int totalRFS = 0;
		int totalForecast = 0;

		// Establecer conexión a la base de datos
		utils.sql.SQLUtil dbFCREPQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCREPQA,
				GlobalVariables.DB_USER_FCREPQA_RMSREPLE_QA, GlobalVariables.DB_PASS_FCREPQA_RMSREPLE_QA);

		// Archivos
		final String BASE_PATH = System.getProperty("user.dir");
		String fileRDF = "" + ".txt";
		String pathRDF = BASE_PATH + "\\Config\\Input\\Files\\TransformacionAbasto\\RDF\\" + fileRDF;
		String fileRelex = "" + ".csv";
		String pathRelex = BASE_PATH + "\\Config\\Input\\Files\\TransformacionAbasto\\Relex\\" + fileRelex;

		// Toma de tiempo: inicio de prueba
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		ZoneId z = ZoneId.of("America/Mexico_City") ;
		String inicio = Instant.now().atZone(z).format(formato);

		// **** PASO 1 **** Validación de datos
		addStep("Validar que la tabla FORECAST contenga la información del sistema correspondiente por categoría");
		/// Extraccion datos de RDF ---------------------
		try {
			SQLResult queryResult;
			String query_rdf = "SELECT GROUP_NO, FCST_SOURCE FROM RMSREPLE.RFS_ROLLOUT_CONFIG WHERE FCST_SOURCE = 'RDF' ORDER BY GROUP_NO";
			System.out.println(query_rdf);

			/**
			 * Ejecutar la query,
			 * 
			 * Si el resultado arroja registros se muestra en la evidencia el resultado de
			 * la query y se ejecuta el metodo para extraer los datos del archivo de RDF.
			 * 
			 * Si el resultado no arroja registros, se mostrará en la evidencia que no se
			 * encontraron categorias/items para el sistema de RDF
			 */
			queryResult = executeQuery(dbFCREPQA, query_rdf);
			if (queryResult.getRowCount() > 0) {
				testCase.addTextEvidenceCurrentStep("Categorías correspondientes a RDF");
				testCase.addQueryEvidenceCurrentStep(queryResult);
				// resultRDF = pom.extraccionDatos(queryResult, pathRDF);

				query_rdf = "select COUNT(*) from RMSREPLE.FORECAST_RDF_STG FCST "
						+ "inner join JERARQUIA_ARTICULOS JA on FCST.ITEM = JA.ARTICULO_ID "
						+ "inner join RMSREPLE.RFS_ROLLOUT_CONFIG RRC on JA.CATEGORIA_ID = RRC.GROUP_NO "
						+ "where rrc.fcst_source = 'RDF'";
				totalRDF = Integer.parseInt(executeQuery(dbFCREPQA, query_rdf).getFirstData());
			} else {
				testCase.addTextEvidenceCurrentStep("No se encontraron categorías para RDF");
			}
		} catch (SQLException e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al ejecutar el query para RDF");
		}

		/// Extraccion datos de Relex ---------------------
		try {
			SQLResult queryResult;
			String query_relex = "SELECT GROUP_NO, FCST_SOURCE FROM RMSREPLE.RFS_ROLLOUT_CONFIG WHERE FCST_SOURCE = 'RFS' ORDER BY GROUP_NO";
			System.out.println(query_relex);

			/**
			 * Ejecutar la query,
			 * 
			 * Si el resultado arroja registros se muestra en la evidencia el resultado de
			 * la query y se ejecuta el metodo para extraer los datos del archivo de Relex.
			 * 
			 * Si el resultado no arroja registros, se mostrará en la evidencia que no se
			 * encontraron categorias/items para el sistema de Relex
			 */
			queryResult = executeQuery(dbFCREPQA, query_relex);
			if (queryResult.getRowCount() > 0) {
				testCase.addTextEvidenceCurrentStep("Categorías correspondientes a Relex");
				testCase.addQueryEvidenceCurrentStep(queryResult);
				// resultRelex = pom.extraccionDatos(queryResult, pathRelex);

				query_relex = "select COUNT(*) from RMSREPLE.FORECAST_RFS_STG FCST "
						+ "inner join JERARQUIA_ARTICULOS JA on FCST.ITEM = JA.ARTICULO_ID "
						+ "inner join RMSREPLE.RFS_ROLLOUT_CONFIG RRC on JA.CATEGORIA_ID = RRC.GROUP_NO "
						+ "where rrc.fcst_source = 'RFS'";
				totalRFS = Integer.parseInt(executeQuery(dbFCREPQA, query_relex).getFirstData());

			} else {
				testCase.addTextEvidenceCurrentStep("No se encontraron categorías para Relex");
			}
		} catch (SQLException e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al ejecutar el query para Relex");
		}

		/// Validacion de datos en tabla FORECAST ---------------------
		/*
		 * Se valida cuantos registros se obtuvieron de la extracción del archivo tanto
		 * para RDF como para Relex y se suman, este resultado debe corresponder a los
		 * registros existentes en la tabla FORECAST.
		 */
		testCase.addTextEvidenceCurrentStep("RDF: " + totalRDF + " registros");
		System.out.println("RDF: " + totalRDF + " registros");
		testCase.addTextEvidenceCurrentStep("Relex: " + totalRFS + " registros");
		System.out.println("Relex: " + totalRFS + " registros");

		int totalSuma = totalRDF + totalRFS;
		testCase.addTextEvidenceCurrentStep("Suma RDF + Relex: " + totalSuma + " registros");
		System.out.println("Suma RDF + Relex: " + totalSuma + " registros");
		try {
			// Se genera query para obtener los registros de la tabla FORECAST
			SQLResult queryResult;
			String query_forecast = "SELECT COUNT(*) FROM FORECAST";
			System.out.println(query_forecast);

			/**
			 * Se ejecuta el query y valida que la cantidad de registros que arrojó el
			 * resultado sea igual al conteo de los registros extraidos de los archivos.
			 */
			queryResult = executeQuery(dbFCREPQA, query_forecast);
			totalForecast = Integer.parseInt(queryResult.getFirstData());
			testCase.addTextEvidenceCurrentStep("Tabla FORECAST: " + totalForecast + " registros");
			System.out.println("Tabla FORECAST: " + totalForecast + " registros");
			if (totalSuma == totalForecast) {
				testCase.addTextEvidenceCurrentStep(
						"Coinciden la cantidad de registros correspondientes a RDF y Relex vs los registros en la tabla de FORECAST");
				testCase.addTextEvidenceCurrentStep(totalSuma + " - " + totalForecast);
			} else {
				throw new AssertionError(
						"No coinciden la cantidad de registros correspondientes a RDF y Relex vs los registros en la tabla de FORECAST \n"
								+ totalSuma + " - " + totalForecast);
			}
		} catch (SQLException e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al ejecutar el query para FORECAST");
		}

		// **** PASO 2 **** Validacion de estructura
		addStep("Validar que la estructura de la tabla FORECAST es la esperada");
		// Se valida que la tabla de FORECAST siga teniendo 16 particiones
		testCase.addTextEvidenceCurrentStep("PARTICIONES");
		try {
			// Se genera la query que mostrará las particiones de la tabla
			SQLResult queryResult;
			String query_partitions = "SELECT table_name, partition_name FROM dba_tab_partitions WHERE table_name = 'FORECAST'";
			System.out.println(query_partitions);

			/**
			 * Se ejecuta la query
			 * 
			 * Si el resultado arroja 16 registros pasa el test y muestra en la evidencia
			 * las particiones.
			 * 
			 * Si el resultado no arroja 16 registros muestra en la evidencia hasta 20
			 * registros del resultado y falla el test
			 */
			queryResult = executeQuery(dbFCREPQA, query_partitions);
			System.out.println(queryResult.getQuery());
			if (queryResult.getRowCount() == 16) {
				testCase.addTextEvidenceCurrentStep("La tabla tiene 16 particiones");
				testCase.addQueryEvidenceCurrentStep(queryResult);
				testCase.passStep();
			} else {
				testCase.addTextEvidenceCurrentStep("La tabla no tiene 16 particiones");
				testCase.addTextEvidenceCurrentStep("Esperadas: 16 - Actuales: " + queryResult.getRowCount());
				if (queryResult.getRowCount() < 20) {
					testCase.addQueryEvidenceCurrentStep(queryResult);
				}
				throw new AssertionError("La tabla no tiene la particiones esperadas");
			}
		} catch (SQLException e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al ejecutar el query de las particiones");
		}

		//// Se valida la estructura de la tabla FORECAST
		testCase.addTextEvidenceCurrentStep("ESTRUCTURA");
		try {
			// Se genera el query para obtener los datos de las columnas de la tabla.
			SQLResult queryResult;
			String query_structure = "SELECT column_name, data_type, data_length, data_precision, nullable "
					+ "FROM all_tab_columns " + "WHERE table_name = 'FORECAST' " + "ORDER BY column_name desc";
			System.out.println(query_structure);

			/**
			 * Se ejecuta el query
			 * 
			 * Si el resultado arroja registros se valida versus el archivo de estructura,
			 * este archivo se obtuvo ejecutando la misma query el 19 de diciembre del 2023.
			 * En caso de coincidir la información mostrará el resultado de la query en la
			 * evidencia.
			 * 
			 * Si el resultado no arroja resultados falla el caso.
			 */
			queryResult = executeQuery(dbFCREPQA, query_structure);
			if (queryResult.getRowCount() > 0) {

				// Leer archivo
				String urlFilestructure = BASE_PATH + "\\Config\\Input\\Files\\TransformacionAbasto\\Config\\"
						+ "structureDB.csv";
				BufferedReader br = new BufferedReader(new FileReader(urlFilestructure));

				// Lee linea por linea el archivo
				String lineFile;
				int i = 0;
				Boolean band = true;
				while ((lineFile = br.readLine()) != null) {
					// Obtiene la linea correspondiente a la iteraci�n del resultado de la query. Se
					// limpia de espacios en blanco y de "[]".
					String rowTable = queryResult.getRow(i).toString().replace(" ", "").replace("[", "").replace("]",
							"");

					/*
					 * Se compara la linea del archivo y la linea de la query, en caso de no
					 * coincidir la bandera cambia a false y se muestra en la evidencia la
					 * discrepancia.
					 */
					if (!rowTable.equals(lineFile)) {
						band = false;
						testCase.addTextEvidenceCurrentStep("Discrepancia encontrada: " + rowTable + " != " + lineFile);
						System.out.println("Discrepancia encontrada: " + rowTable + " != " + lineFile);
					}
					i++;
				}
				br.close();

				/**
				 * Si la bandera es verdadera, también se valida que la cantidad de registros de
				 * la query y la cantidad de líneas en el archivo coincida. Si coincide muestra
				 * el resultado de la query en la evidencia. Si no coincide muestra lo obtenido
				 * tanto en la query como en el archivo.
				 * 
				 * Si la bandera es falsa, muestra la info obtenida de la query y falla el caso.
				 */
				if (band) {
					if (queryResult.getRowCount() == i) {
						testCase.addTextEvidenceCurrentStep("La tabla FORECAST tiene la estructura esperada");
						testCase.addQueryEvidenceCurrentStep(queryResult);
						testCase.passStep();
					} else {
						System.out.println("No coincide el tamaño: " + queryResult.getRowCount() + " != " + i);
						throw new AssertionError("No coincide el tamaño: " + queryResult.getRowCount() + " != " + i);
					}
				} else {
					testCase.addQueryEvidenceCurrentStep(queryResult);
					throw new AssertionError("La estructura no es la esperada");
				}
			} else {
				throw new AssertionError("La sentencia no tiene datos");
			}
		} catch (SQLException e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al ejecutar la validacion de la estructura");
		}

		// Toma de tiempo: fin de prueba
		String fin = Instant.now().atZone(z).format(formato);

		// Resultado
		testCase.addTextEvidenceCurrentStep("Inicio:\t" + inicio + "\nFin: \t" + fin);
		System.out.println("Inicio:\t" + inicio + "\nFin: \t" + fin);

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Acceso a la base de datos de REPLE: FCREPQA  - oxrepledbq00.femcom.net";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "A) Validar que la tabla FORECAST contenga la información del sistema correspondiente por categoría."
				+ "\nB) Validar que la estructura de la tabla FORECAST es la esperada.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Angel Del Río";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_014_ValidacionTablaFORECAST_Outputs_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}
