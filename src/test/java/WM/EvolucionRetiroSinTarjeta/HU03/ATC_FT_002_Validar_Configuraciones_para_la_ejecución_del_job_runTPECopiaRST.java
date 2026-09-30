package WM.EvolucionRetiroSinTarjeta.HU03;
import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_002_Validar_Configuraciones_para_la_ejecución_del_job_runTPECopiaRST extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_Validar_Configuraciones_para_la_ejecución_del_job_runTPECopiaRST_test(HashMap<String, String> data)
	throws Exception{
		
		/**
		 * Plataforma de Oracle: Tener conexion a la BD FCWMREQA
		 * Desc: Validar que no se pueda ejecutar el concurrente XXFC_Proceso_de_dispersion_RST FI
		 * @author Mario Garza
		 * @date 13/02/2024
		 **/
		/*
		 * Variables
		 *********************************************************************/
		SQLUtil dbFCWMREQA = new SQLUtil(GlobalVariables.DB_HOST_FCWMREQA, GlobalVariables.DB_USER_FCWMREQA,
				GlobalVariables.DB_PASSWORD_FCWMREQA);
		
		/*
		 * 
		 ******************************************************************************************/

		testCase.setTest_Description(data.get("id") + data.get("description"));

		testCase.setPrerequisites("La configuraciones deben estar creadas en la tabla TPE_TABLAS_COPIA de la BD FCWMREQA");
		
		System.out.println(
				"*****************************************PASO 1 y 2************************************************");
		//Se realiza la consulta a la BD
		addStep("Ingresar a la BD de FCWMREQA y Realizar la siguiente consulta para validar en TPE_TABLAS_COPIA para validar las configuraciones necesarias para la ejecución del job runTPECopiaRST");
		String consulta_FCWMREQA = "SELECT TABLA,STATUS,TABLA_STATUS,TIPO,FECHA_COPIADA,DIAS_ESPACIO_DEPURACION,FECHA_TRUNCADA,PROCEDURE_COPIA,PROCEDURE_DEPURA FROM TPEREP.TPE_TABLAS_COPIA WHERE TIPO IN('RST') AND STATUS ='P' AND TABLA_STATUS = 'A'";
		System.out.println("Consulta: " + consulta_FCWMREQA);
		//Se almacena en la variable ExecuteQuery
		SQLResult ExecuteQuery = executeQuery(dbFCWMREQA, consulta_FCWMREQA);

		boolean conexiondbFCWMREQA  = true;
		conexiondbFCWMREQA  = ExecuteQuery.isEmpty();
		//Se toma la evidencia 
		testCase.addTextEvidenceCurrentStep("Conexion: FCWMREQA ");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMREQA);
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);

		assertFalse(conexiondbFCWMREQA, "La conexion no fue exitosa");
		
	}

	@Override
	public void beforeTest() {
	}	

	@Override
	public String setTestDescription() {
		return "Validar configuraciones para la ejecucion del job runTPECopia.RST.sh en la tabla TPE_TABLAS_COPIA";
	}

	@Override
	public String setTestDesigner() {
		return "Mario.garza@grupotimexico.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_Validar_Configuraciones_para_la_ejecución_del_job_runTPECopiaRST_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "La configuraciones deben estar creadas en la tabla TPE_TABLAS_COPIA de la BD FCWMREQA";
	}

	@AfterMethod
	public void afterMethod() {

	}

}
