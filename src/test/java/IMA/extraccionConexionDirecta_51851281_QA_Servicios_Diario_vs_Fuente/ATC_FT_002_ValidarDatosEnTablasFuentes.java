package IMA.extraccionConexionDirecta_51851281_QA_Servicios_Diario_vs_Fuente;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_002_ValidarDatosEnTablasFuentes extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_ValidarDatosEnTablasFuentes_test(HashMap<String, String> data) throws Exception {

		IMATools ima = new IMATools(null, testCase);
		String query;
		SQLResult connectionResult;
		utils.sql.SQLUtil DB;
		
		/**
		 * Se limitan los registros a 10 para limitar los tiempos de carga en la ejecucion.
		 */

		/*		*//***************************************
					 * BASE DATOS
					 ***************************************/
		/*
		 * DB = new SQLUtil(GlobalVariables.DB_HOST_EBS, GlobalVariables.DB_USER_EBS,
		 * GlobalVariables.DB_PASSWORD_EBS);
		 * 
		 *//***************************************
			 * PASO 1 y 2
			 ***************************************//*
														 * 
														 * addStep("Conectar con base de datos FCFINQA y realizar consulta en la tabla BIQUERYMKT.TEMP_PAGO_SERVICIOS_BI"
														 * ); query = "SELECT * FROM BIQUERYMKT.TEMP_PAGO_SERVICIOS_BI";
														 * connectionResult = executeQuery(DB, query);
														 * ima.validarConexion(connectionResult);
														 * testCase.addTextEvidenceCurrentStep(query);
														 * ima.validarContenidoTablas(connectionResult, query);
														 */

		/***************************************
		 * BASE DATOS
		 ***************************************/
		DB = new SQLUtil(GlobalVariables.DB_HOST_FCDW4QA, GlobalVariables.DB_USER_FCDW4QA,
				GlobalVariables.DB_PASSWORD_FCDW4QA);

		/***************************************
		 * PASO 3 y 4
		 ***************************************/

		addStep("Conectar con base de datos FCDW4QA y realizar consulta en la tabla BIDMMKT.FACT_SERVICIOS");
		query = "SELECT * FROM BIDMMKT.FACT_SERVICIOS WHERE ROWNUM <=10";
		connectionResult = executeQuery(DB, query);
		ima.validarConexion(connectionResult);
		ima.validarContenidoTablas(connectionResult, query);
		testCase.addQueryEvidenceCurrentStep(connectionResult);
		
		/***************************************
		 * PASO 5
		 ***************************************/

		addStep("Realizar consulta en la tabla BIODSMKT.CNV_010_MULTIJUEGOS_POS");
		query = "SELECT * FROM BIODSMKT.CNV_010_MULTIJUEGOS_POS WHERE ROWNUM <=10";
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
		testCase.addQueryEvidenceCurrentStep(connectionResult);

		/***************************************
		 * PASO 6
		 ***************************************/

		addStep("Realizar consulta en la tabla BIDMMKT.DM_FACT_SERVICIOS_SEMANA");
		query = "SELECT * FROM BIDMMKT.DM_FACT_SERVICIOS_SEMANA WHERE ROWNUM <=3";
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
		testCase.addQueryEvidenceCurrentStep(connectionResult);

		/***************************************
		 * PASO 7
		 ***************************************/

		addStep("Realizar consulta en la tabla BIDMMKT.DM_FACT_SERVICIOS_MES");
		query = "SELECT * FROM BIDMMKT.DM_FACT_SERVICIOS_MES WHERE ROWNUM <=10";
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
		testCase.addQueryEvidenceCurrentStep(connectionResult);

		/***************************************
		 * PASO 8
		 ***************************************/

		addStep("Realizar consulta en la tabla BIODSMKT.CNV_010_SERV_OTROS_INGRESOS");
		query = "SELECT * FROM BIODSMKT.CNV_010_SERV_OTROS_INGRESOS WHERE ROWNUM <=10";
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
		testCase.addQueryEvidenceCurrentStep(connectionResult);

	}

	@Override
	public String setTestFullName() {
// TODO Auto-generated method stub
		return "ATC_FT_002_ValidarDatosEnTablasFuentes_test";
	}

	@Override
	public String setTestDescription() {
// TODO Auto-generated method stub
		return "CP002 - Validar datos en tablas fuentes";
	}

	@Override
	public String setTestDesigner() {
// TODO Auto-generated method stub
		return "Team IMA";
	}

	@Override
	public String setTestInstanceID() {
// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
// TODO Auto-generated method stub
		return "Prerequisitos";
	}

	@Override
	public void beforeTest() {
// TODO Auto-generated method stub
	}

	@Override
	public void baseAfterTest() {
	}

}