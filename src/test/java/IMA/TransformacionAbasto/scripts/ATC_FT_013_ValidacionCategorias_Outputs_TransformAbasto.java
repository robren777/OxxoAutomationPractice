package IMA.TransformacionAbasto.scripts;

import static org.testng.Assert.assertTrue;

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
 * PRE-REQUISITOS: Acceso a la base de datos de REPLE: FCREPQA -
 * oxrepledbq00.femcom.net con el user: RMSREPLE_QA
 * 
 * DESCRIPCION: Validar que en la tabla RFS_ROLLOUT_CONFIG no existan categor�as
 * duplicadas entre los sistemas.
 * 
 * @author esmeraldag@hexaware.com
 */

public class ATC_FT_013_ValidacionCategorias_Outputs_TransformAbasto extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_013_ValidacionCategorias_Outputs_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22141-TI01_TransformacionAbastoPronostico");

		// Toma de tiempo: inicio de prueba
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		ZoneId z = ZoneId.of("America/Mexico_City") ;
		String inicio = Instant.now().atZone(z).format(formato);

		// **** PASO 1 **** Validacion de categorias repetidas
		addStep("Validar que no haya categorías repetidas en la tabla RFS_ROLLOUT_CONFIG");
		try {
			// Establecer conexión con la base de datos
			SQLResult queryResult;
			utils.sql.SQLUtil dbFCREPQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCREPQA,
					GlobalVariables.DB_USER_FCREPQA_RMSREPLE_QA, GlobalVariables.DB_PASS_FCREPQA_RMSREPLE_QA);

			// Generar query para validar que no hay categorías repetidas
			String query_category = "SELECT GROUP_NO, FCST_SOURCE FROM RMSREPLE.RFS_ROLLOUT_CONFIG "
					+ "GROUP BY GROUP_NO, FCST_SOURCE " + "HAVING COUNT(*) > 1";
			System.out.println(query_category);

			// Ejecutar el query
			queryResult = executeQuery(dbFCREPQA, query_category);

			/**
			 * Si el query arroja resultados es porque hay categorías repetidas, muestra en
			 * la evidencia el resultado de la query.
			 * 
			 * Si no arroja resultados mostrará en la evidencia los registros de la tabla.
			 */
			Boolean validacion = true;
			if (queryResult.getRowCount() > 0) {
				validacion = false;
				testCase.addQueryEvidenceCurrentStep(queryResult);
				assertTrue(validacion, "Hay valores repetidos");
			} else {
				testCase.addTextEvidenceCurrentStep("No hay valores repetidos");
				String query_table = "SELECT * FROM RMSREPLE.RFS_ROLLOUT_CONFIG ORDER BY GROUP_NO";
				System.out.println(query_table);
				queryResult = executeQuery(dbFCREPQA, query_table);
				testCase.addQueryEvidenceCurrentStep(queryResult);
				testCase.passStep();
			}

		} catch (SQLException e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al ejecutar el query");
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
		return "Acceso a la base de datos de REPLE: FCREPQA  - oxrepledbq00.femcom.net con el user: RMSREPLE_QA";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que en la tabla RFS_ROLLOUT_CONFIG no existan categorías duplicadas entre los sistemas";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Angel Del Río";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_013_ValidacionCategorias_Outputs_test";
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
