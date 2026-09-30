package WM.EvolucionRetiroSinTarjeta.HU09;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_002_Acceso_a_Base_de_datos_FCODSQA extends BaseExecution {
	/**
	 * Consulta a la base de datos FCODSQA
	 * Desc:
	 * Validar acceso a base de datos FCODSQA con consulta
	 * @author Miguel Escamilla
	 * @date 25/01/2024
	 **/
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_Acceso_a_Base_de_datos_FCODSQA(HashMap<String, String> data) throws Exception {

		//BD FCODSQA
		SQLUtil dbFCODSQA = new SQLUtil(GlobalVariables.DB_HOST_FCODSQA, GlobalVariables.DB_USER_FCODSQA,
				GlobalVariables.DB_PASSWORD_FCODSQA);


		System.out.println(
				"*****************************************PASO 1 y 2 ************************************************");

		String consulta_dbFCODSQA = "SELECT * FROM ( SELECT APPLICATION, ENTITY, FOLIO, CREATION_DATE FROM WM.TPE_FR_TRANSACTION ORDER BY CREATION_DATE DESC ) WHERE ROWNUM <= 5";
		System.out.println("Consulta: " + consulta_dbFCODSQA);
		SQLResult Execute_dbFCODSQA_Query = executeQuery(dbFCODSQA, consulta_dbFCODSQA);

		boolean conexionFCODSQA = true;
		conexionFCODSQA = Execute_dbFCODSQA_Query.isEmpty();

		addStep("Ingresar a la BD de FCODSQA");
		testCase.addTextEvidenceCurrentStep("Conexion: FCODSQA");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCODSQA);

		addStep("Se ingresa a la Base de datos FCODSQA para validar la conexion");
		testCase.addQueryEvidenceCurrentStep(Execute_dbFCODSQA_Query);
		assertFalse(conexionFCODSQA, "La conexion no fue exitosa");

		if (!conexionFCODSQA){
			System.out.println("Conexion con la base de datos FCODSQA se realizo de manera exitosa");
		}

		System.out.println("Se imprime una muestra de datos:");
		List<String> columnNames = Execute_dbFCODSQA_Query.getColumnNames();
		Map<String, String> datos = Execute_dbFCODSQA_Query.getRowMap(1);

		columnNames.forEach( x -> { System.out.println(x + ": " + datos.get(x)); } );


	}
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Acceso a Base de datos FCODSQA";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar acceso a Base de datos FCODSQA";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Miguel Angel Escamilla Reyes";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "002-Acceso a Base de datos FCODSQA";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
