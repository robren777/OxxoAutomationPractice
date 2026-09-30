package WM.EvolucionRetiroSinTarjeta.HU07;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.util.HashMap;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;
/*@AzureTestInfo(
		areaPath = "I22032-Evolución Retiros sin Tarjeta",
		iterationPath = "I22032-Evolución Retiros sin Tarjeta\\Sprint 9",
		tags = "AUT;TestTag1",
		userStoryId = 6639 
	)*/
public class ATC_FT_02_Validar_que_exita_la_configuracion_para_RST_Ftech_RST_HPAY_y_DEV_OXPAY extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_02_Validar_que_exita_la_configuracion_para_RST_Ftech_RST_HPAY_y_DEV_OXPAY_test(HashMap<String, String> data)
	throws Exception{
		/**
		 * Plataforma de Oracle: Tener conexion a la BD AVEBQA
		 * Desc: Validar que exita la configuracion para los siguientes formas de pago(RST_Ftech, RST_HPAY y DEV_OXPAY) en la Base de Datos AVEBQA
		 * @author Mario Garza
		 * @date 16/02/2024
		 **/
		/*
		 * Utilerias
		 *********************************************************************/
		SQLUtil dbAVEBQA = new SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
				GlobalVariables.DB_PASSWORD_AVEBQA);
		/*
		 * Variables
		 ******************************************************************************************/

		testCase.setTest_Description(data.get("id") + data.get("description"));

		testCase.setPrerequisites("Acceso a base de datos AVEBQA");
		
		System.out.println(
				"*****************************************PASO 1 ************************************************");
		//Se realiza la consulta
		addStep("Realizar la siguiente consulta para la forma de pago PYIO24 - RST_Hotpay");
		String consulta_RST_Hotpay = "select FORMA_PAGO, NOMBRE_CUENTA, fecha_efectiva_desde, ESTADO from apps.xxfc_MPC_gl_config_ctas where forma_pago = 'PYIO24' order by fecha_efectiva_desde desc";
		
		System.out.println("Consulta: " + consulta_RST_Hotpay);
		//Se guarda en la variable
		SQLResult ExecuteQuery_RST_Hotpay = executeQuery(dbAVEBQA, consulta_RST_Hotpay);

		boolean conexiondbAVEBQA_RST_Hotpay  = true;
		conexiondbAVEBQA_RST_Hotpay  = ExecuteQuery_RST_Hotpay.isEmpty();
		//Se cuenta los registros de la fila
		int cantidad_Registros_RST_Hotpay = ExecuteQuery_RST_Hotpay.getRowCount();
		System.out.println(cantidad_Registros_RST_Hotpay);
		//Se guarda en una lista de string la fila 0
		List<String> datos_RST_Hotpay = ExecuteQuery_RST_Hotpay.getRow(0);
		String id_RST_Hotpay = datos_RST_Hotpay.get(0);
		System.out.println(id_RST_Hotpay);
		
		//Se coloca el valor a validar
		String formaPago_RST_Hotpay = "PYIO24";
		 
		boolean maximo_Registros = false;
		boolean formaPagoRegistrado = false;
		/*if (cantidad_Registros_RST_Hotpay== 1 || cantidad_Registros_RST_Hotpay == 5){ 
			maximo_Registros = true;
		}*/
	
		if (formaPago_RST_Hotpay == id_RST_Hotpay){ 
			formaPagoRegistrado = true;
			System.out.println("Si hay registros: " + formaPago_RST_Hotpay);
		}
		//assertEquals(formaPago_RST_Hotpay, id_RST_Hotpay);
		testCase.addTextEvidenceCurrentStep("Conexion: AVEBQA");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery_RST_Hotpay);
		assertFalse(conexiondbAVEBQA_RST_Hotpay, "La conexion no fue exitosa");
		
		System.out.println(
				"*****************************************PASO 2 ************************************************");
		//Se realiza la consulta
		addStep("Realizar la siguiente consulta para la forma de pago  PYIO26 -  RST_Ftech");
		String consulta_RST_Ftech = "select FORMA_PAGO, NOMBRE_CUENTA, fecha_efectiva_desde, ESTADO from apps.xxfc_MPC_gl_config_ctas where forma_pago = 'PYIO26' order by fecha_efectiva_desde desc";
		
		System.out.println("Consulta: " + consulta_RST_Ftech);
		//Se guarda en la variable
		SQLResult ExecuteQuery_RST_Ftech = executeQuery(dbAVEBQA, consulta_RST_Ftech);

		boolean conexiondbAVEBQA_RST_Ftech  = true;
		conexiondbAVEBQA_RST_Ftech  = ExecuteQuery_RST_Ftech.isEmpty();
		//Se cuenta los registros de la fila
		int cantidad_Registros_RST_Ftech = ExecuteQuery_RST_Ftech.getRowCount();
		System.out.println(cantidad_Registros_RST_Ftech);

		List<String> datos_RST_Ftech = ExecuteQuery_RST_Ftech.getRow(0);
		String id_RST_Ftech= datos_RST_Ftech.get(0);

		System.out.println(id_RST_Ftech);
		//Se coloca el valor a validar
		String formaPago_RST_Ftech = "PYIO26";
		 
		boolean maximo_Registros_RST_Ftech = false;
		boolean formaPagoRegistrado_RST_Ftech = false;
			
		/*if (cantidad_Registros_RST_Ftech== 1 || cantidad_Registros_RST_Ftech == 5){ 
			maximo_Registros = true;
		}*/
		
		if (formaPago_RST_Ftech == id_RST_Ftech){ 
			formaPagoRegistrado_RST_Ftech = true;
			System.out.println("Si hay registros: " + formaPago_RST_Ftech);
		}
		assertEquals(formaPago_RST_Ftech, id_RST_Ftech);
		//Se toma la evidencia
		testCase.addTextEvidenceCurrentStep("Conexion: AVEBQA");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery_RST_Ftech);
		//Se valida si la conexion  no fue exitosa
		assertFalse(conexiondbAVEBQA_RST_Ftech, "La conexion no fue exitosa");
		
		System.out.println(
				"*****************************************PASO 3 ************************************************");
		//Se realiza la consulta
		addStep("Realizar la siguiente consulta para la forma de pago  PYIO49 - DEV_OXPAY");
		String consulta_DEV_OXPAY = "select FORMA_PAGO, NOMBRE_CUENTA, fecha_efectiva_desde, ESTADO from apps.xxfc_MPC_gl_config_ctas where forma_pago = 'PYIO49' order by fecha_efectiva_desde desc";
		
		System.out.println("Consulta: " + consulta_DEV_OXPAY);
		//Se guarda en la variable
		SQLResult ExecuteQuery_DEV_OXPAY = executeQuery(dbAVEBQA, consulta_DEV_OXPAY);

		boolean conexiondbAVEBQA_DEV_OXPAY  = true;
		conexiondbAVEBQA_DEV_OXPAY  = ExecuteQuery_DEV_OXPAY.isEmpty();
		//Se cuenta los registros de la fila
		int cantidad_Registros_DEV_OXPAY = ExecuteQuery_DEV_OXPAY.getRowCount();
		System.out.println(cantidad_Registros_DEV_OXPAY);
		//Se guarda en una lista de string la fila 0
		List<String> datos_DEV_OXPAY = ExecuteQuery_DEV_OXPAY.getRow(0);

		String id_DEV_OXPAY= datos_DEV_OXPAY.get(0);

		System.out.println(id_DEV_OXPAY);
		//Se coloca el valor a validar
		String formaPago_DEV_OXPAY = "PYIO49";
		 
		boolean maximo_Registros_DEV_OXPAY = false;
		boolean formaPagoRegistrado_DEV_OXPAY = false;
			
		/*if (cantidad_Registros_DEV_OXPAY== 1 || cantidad_Registros_DEV_OXPAY == 5){ 
			maximo_Registros_DEV_OXPAY = true;
		}*/
		
		if (formaPago_DEV_OXPAY == id_DEV_OXPAY){ 
			formaPagoRegistrado_DEV_OXPAY = true;
			System.out.println("Si hay registros: " + formaPago_DEV_OXPAY);
		}
		assertEquals(formaPago_DEV_OXPAY, id_DEV_OXPAY);
		//Se toma la evidencia
		testCase.addTextEvidenceCurrentStep("Conexion: AVEBQA");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery_DEV_OXPAY);
		//Se valida si la conexion  no fue exitosa
		assertFalse(conexiondbAVEBQA_DEV_OXPAY, "La conexion no fue exitosa");
		
	}

	@Override
	public void beforeTest() {
	}	

	@Override
	public String setTestDescription() {
		return "Validar que exita la configuracion para los siguientes formas de pago(RST_Ftech, RST_HPAY y DEV_OXPAY) en la Base de Datos AVEBQA";
	}

	@Override
	public String setTestDesigner() {
		return "Mario.garza@grupotimexico.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_02_Validar_que_exita_la_configuracion_para_RST_Ftech_RST_HPAY_y_DEV_OXPAY";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Acceso a base de datos AVEBQA";
	}

	@AfterMethod
	public void afterMethod() {

	}

}
