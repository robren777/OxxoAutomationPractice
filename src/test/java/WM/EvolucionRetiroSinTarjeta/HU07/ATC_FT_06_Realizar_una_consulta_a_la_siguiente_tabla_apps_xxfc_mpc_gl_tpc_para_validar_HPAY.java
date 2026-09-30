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

public class ATC_FT_06_Realizar_una_consulta_a_la_siguiente_tabla_apps_xxfc_mpc_gl_tpc_para_validar_HPAY extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_03_Realizar_una_consulta_a_la_siguiente_tabla_apps_xxfc_mpc_gl_tpc_para_validar_que_se_tenga_registros_de_actual_fecha_o_dia_anterior_test(HashMap<String, String> data)
	throws Exception{
		/**
		 * Plataforma de Oracle: Tener conexion a la BD AVEBQA
		 * Desc: Realizar una consulta a la siguiente tabla apps.xxfc_mpc_gl_tpc para validar que se tenga registros de actual fecha o dia anterior para tomar en cuenta el rango de fecha para la ejecucion del proceso de Dispersion
		 * @author Mario Garza
		 * @date 20/02/2024
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
		addStep("Realizar la siguiente consulta para la forma de pago   PYIO24  -> RST_HPAY");
		String consulta_RST_Ftech = "select FORMA_PAGO, CR_PLAZA, CR_TIENDA, ACCOUNTING_DATE from apps.xxfc_mpc_gl_tpc where forma_pago = 'PYIO24' order by accounting_date desC fetch first 5 rows only";
		
		System.out.println("Consulta: " + consulta_RST_Ftech);
		//Se guarda en la variable
		SQLResult ExecuteQuery_RST_Ftech = executeQuery(dbAVEBQA, consulta_RST_Ftech);

		boolean conexiondbAVEBQA_RST_Ftech  = true;
		conexiondbAVEBQA_RST_Ftech  = ExecuteQuery_RST_Ftech.isEmpty();
		//Se cuenta los registros de la fila
		int cantidad_Registros_RST_Ftech = ExecuteQuery_RST_Ftech.getRowCount();
		System.out.println(cantidad_Registros_RST_Ftech);
		//Se guarda en una lista de string la fila 0
		List<String> datos_RST_Ftech = ExecuteQuery_RST_Ftech.getRow(0);
		String id_RST_Ftech= datos_RST_Ftech.get(0);

		System.out.println(id_RST_Ftech);
		//Se coloca el valor a validar
		String formaPago_RST_Ftech = "PYIO24";
		 
		boolean maximo_Registros_RST_Ftech = false;
		boolean formaPagoRegistrado_RST_Ftech = false;
		//Se valida sique la cantidad de regitros este de 1 o 5
		if (cantidad_Registros_RST_Ftech== 1 || cantidad_Registros_RST_Ftech == 5){ 
			maximo_Registros_RST_Ftech = true;
		}
		//Se valida que el dato que tenemos seteado con el que se trajo de la consulta
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
				"*****************************************PASO 2 ************************************************");
		//Se realiza la consulta
		addStep("Validar que los CR_PAZA que se van a considerar para que se tome por el Proceso de dispercion no exista en estas dos tablas con los siguientes querys:");
		String consulta_CR_PAZA = "SELECT FORMA_PAGO, CR_PLAZA, CR_TIENDA, ACCOUNTING_DATE, IMPORTE FROM apps.xxfc_gl_rst_lineas where cr_plaza = '10MON' order by accounting_date desc fetch first 5 rows only";
		
		System.out.println("Consulta: " + consulta_CR_PAZA);
		//Se guarda en la variable
		SQLResult ExecuteQuery_CR_PAZA = executeQuery(dbAVEBQA, consulta_CR_PAZA);

		boolean conexiondbAVEBQA_CR_PAZA  = true;
		conexiondbAVEBQA_CR_PAZA  = ExecuteQuery_CR_PAZA.isEmpty();
		//Se cuenta los registros de la fila
		int cantidad_Registros_CR_PAZA = ExecuteQuery_CR_PAZA.getRowCount();
		System.out.println(cantidad_Registros_CR_PAZA);
		
		 
		boolean maximo_Registros_CR_PAZA = false;
		//Se valida sique la cantidad de regitros este de 1 o 5
		if (cantidad_Registros_CR_PAZA== 1 || cantidad_Registros_CR_PAZA == 5){ 
			maximo_Registros_CR_PAZA = true;
		}
		//Se toma la evidencia
		testCase.addTextEvidenceCurrentStep("Conexion: AVEBQA");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery_CR_PAZA);
		//Se valida si la conexion  no fue exitosa
		assertFalse(conexiondbAVEBQA_CR_PAZA, "La conexion no fue exitosa");
		
	}

	@Override
	public void beforeTest() {
	}	

	@Override
	public String setTestDescription() {
		return "Realizar una consulta a la siguiente tabla apps.xxfc_mpc_gl_tpc para validar que se tenga registros de actual fecha o dia anterior para tomar en cuenta el rango de fecha para la ejecucion del proceso de Dispersion";
	}

	@Override
	public String setTestDesigner() {
		return "Mario.garza@grupotimexico.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_06_Realizar_una_consulta_a_la_siguiente_tabla_apps_xxfc_mpc_gl_tpc_para_validar_HPAY";
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
