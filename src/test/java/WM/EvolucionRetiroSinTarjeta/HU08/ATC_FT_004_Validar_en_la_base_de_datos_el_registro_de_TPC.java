package WM.EvolucionRetiroSinTarjeta.HU08;
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
		iterationPath = "I22032-Evolución Retiros sin Tarjeta\\Sprint 6",
		tags = "AUT",
		userStoryId = 6640
)*/
public class ATC_FT_004_Validar_en_la_base_de_datos_el_registro_de_TPC extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_Validar_en_la_base_de_datos_el_registro_de_TPC_test(HashMap<String, String> data)
	throws Exception{
		
		/**
		 * Plataforma de Oracle: Tener conexion a la BD FCWM6QA
		 * Desc: Validar en la base de datos el registro de TPC
		 * @author Mario Garza
		 * @date 10/03/2024
		 **/
		
		/*
		 * Utilerias
		 *********************************************************************/
		SQLUtil dbFCWM6QA = new SQLUtil(GlobalVariables.DB_HOST_WM, GlobalVariables.DB_USER_WM,
				GlobalVariables.DB_PASSWORD_WM);
		
		/*
		 * Variables
		 ******************************************************************************************/

		testCase.setTest_Description(data.get("id") + data.get("description"));

		testCase.setPrerequisites("La configuraciones deben estar creadas en la tabla TPE_TABLAS_COPIA de la BD FCWMREQA");
		
		System.out.println(
				"*****************************************PASO 1 ************************************************");
		//Se realiza la consulta
		addStep("Realizar la siguiente consulta para validar el registro del ENVELOPE en la tabla POS_ENVELOPE enviado desde POS con la fecha del día en que fue enviado.");
		String consulta_FCWM6QA_POS_ENVELOPE = "SELECT ID,PV_CR_PLAZA,PV_CR_TIENDA,PV_ENVELOPE_ID,RECEIVED_DATE,NO_DOCUMENTS,PR50_VERSION,SOURCEAPP,PARTITION_DATE FROM POSUSER.POS_ENVELOPE WHERE PV_CR_PLAZA='10MON' AND PV_CR_TIENDA='50OAY' AND TRUNC (RECEIVED_DATE)= TO_DATE ('29/01/24', 'DD/MM/YY') and rownum == 5";
		
		System.out.println("Consulta: " + consulta_FCWM6QA_POS_ENVELOPE);
		//Se guarda en la variable
		SQLResult ExecuteQuery_POS_ENVELOPE = executeQuery(dbFCWM6QA, consulta_FCWM6QA_POS_ENVELOPE);

		boolean conexiondbFCWM6QA  = true;
		conexiondbFCWM6QA  = ExecuteQuery_POS_ENVELOPE.isEmpty();
		//Se cuenta los registros de la fila
		int cantidad_Registros_POS_ENVELOPE = ExecuteQuery_POS_ENVELOPE.getRowCount();
		System.out.println(cantidad_Registros_POS_ENVELOPE);
		//Se guarda en una lista de string la fila 1
		List<String> datos_POS_ENVELOPE = ExecuteQuery_POS_ENVELOPE.getRow(1);
		//Se guarda el valor uno de la fila
		String id_POS_ENVELOPE = datos_POS_ENVELOPE.get(0);
		System.out.println(id_POS_ENVELOPE);
		 
		boolean maximo_Registros = false;
		//Se valida sique la cantidad de regitros este de 1 o 5
		if (cantidad_Registros_POS_ENVELOPE== 1 || cantidad_Registros_POS_ENVELOPE == 5){ 
			maximo_Registros = true;
		}
		//Se toma la evidencia
		testCase.addTextEvidenceCurrentStep("Conexion: FCWMREQA");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMREQA);
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery_POS_ENVELOPE);
		//Se valida si la conexion  no fue exitosa
		assertFalse(conexiondbFCWM6QA, "La conexion no fue exitosa");
		
		System.out.println(
				"***************************************** PASO 2 ************************************************");
		//Se realiza la consulta
	    addStep("Con el ID obtenido en el paso anterior, Realizar la siguiente consulta para Validar se que realizo el registro del documento TPC en la tabla POS_INBOUND_DOCS con STATUS=I(Insertado) y el nombre del documento en PV_DOC_NAME");
	    String consulta_POS_INBOUND_DOCS  = "SELECT ID,PE_ID,PV_DOC_ID,STATUS,DOC_TYPE,PV_DOC_NAME,RECEIVED_DATE,PARTITION_DATE,PR50_VERSION,SOURCEAPP FROM POSUSER.POS_INBOUND_DOCS WHERE DOC_TYPE='TPC' AND PE_ID='"+ id_POS_ENVELOPE +"'";
	    System.out.println("Consulta: " + consulta_POS_INBOUND_DOCS);
	  //Se guarda en la variable
		SQLResult ExecuteQuery_POS_INBOUND_DOCS = executeQuery(dbFCWM6QA, consulta_POS_INBOUND_DOCS);
		
		boolean conexiondbFCWM6QA1  = true;
		conexiondbFCWM6QA1 = ExecuteQuery_POS_INBOUND_DOCS.isEmpty();
		//Se cuenta los registros de la fila
		int cantidad_Registros_POS_INBOUND_DOCS = ExecuteQuery_POS_INBOUND_DOCS.getRowCount();
		System.out.println(cantidad_Registros_POS_INBOUND_DOCS);
		//Se guarda en una lista de string la fila 1
		List<String> datos_POS_INBOUND_DOCS= ExecuteQuery_POS_INBOUND_DOCS.getFirstRow();
		//Se guarda ek valor 3
		String id_POS_INBOUND_DOCS = datos_POS_INBOUND_DOCS.get(3);
		//Se guarda el valor 0
		String id_POS_TPC = datos_POS_INBOUND_DOCS.get(0);
		System.out.println("Consulta: " + id_POS_INBOUND_DOCS);
		//Se toma la evidencia
		testCase.addTextEvidenceCurrentStep("Conexion: FCWMREQA");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMREQA);
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery_POS_INBOUND_DOCS);
		//Se valida si la conexion  no fue exitosa
		assertFalse(conexiondbFCWM6QA, "La conexion no fue exitosa");
		
		System.out.println(
				"***************************************** PASO 3 ************************************************");
		//Se realiza la consulta
	    addStep("Con el ID obtenido en el paso anterior, Realizar la siguiente consulta para Validar se que realizo el registro del documento TPC en la tabla POS_INBOUND_DOCS con STATUS=I(Insertado) y el nombre del documento en PV_DOC_NAME");
	    String consulta_id_POS_TPC  = "SELECT PID_ID,NO_RECORDS,PARTITION_DATE FROM POSUSER.POS_TPC WHERE  PID_ID='"+ id_POS_TPC +"'";
	    System.out.println("Consulta: " + consulta_id_POS_TPC);
	  //Se guarda en la variable
		SQLResult ExecuteQuery_id_POS_TPC = executeQuery(dbFCWM6QA, consulta_id_POS_TPC);
		
		boolean conexiondbFCWM6QA1_id_POS_TPC  = true;
		conexiondbFCWM6QA1_id_POS_TPC = ExecuteQuery_POS_INBOUND_DOCS.isEmpty();
		//Se cuenta los registros de la fila
		int cantidad_Registros_id_POS_TPC = ExecuteQuery_id_POS_TPC.getRowCount();
		System.out.println(cantidad_Registros_id_POS_TPC);
		//Se guarda en una lista de string la fila 1
		List<String> datos_POS_id_POS_TPC= ExecuteQuery_id_POS_TPC.getFirstRow();
		//Se guarda el valor 1
		String id_POS_TPCP3 = datos_POS_id_POS_TPC.get(1);
		//String id_POS_TPC = datos_POS_INBOUND_DOCS.get(3);
		System.out.println("Consulta: " + id_POS_TPCP3);
		//Se toma la evidencia
		testCase.addTextEvidenceCurrentStep("Conexion: FCWMREQA");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMREQA);
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery_id_POS_TPC);
		//Se valida si la conexion  no fue exitosa
		assertFalse(conexiondbFCWM6QA1_id_POS_TPC, "La conexion no fue exitosa");
		
		System.out.println(
				"***************************************** PASO 4 ************************************************");
		//Se realiza la consulta
	    addStep("Con el ID obtenido en el paso anterior, Realizar la siguiente consulta para Validar se que realizo el registro del documento TPC en la tabla POS_INBOUND_DOCS con STATUS=I(Insertado) y el nombre del documento en PV_DOC_NAME");
	    String consulta_POS_TPC_DETL  = "SELECT * FROM POSUSER.POS_TPC_DETL WHERE PID_ID='"+ id_POS_TPC +"'";
	    System.out.println("Consulta: " + consulta_POS_TPC_DETL);
	  //Se guarda en la variable
		SQLResult ExecuteQuery_POS_TPC_DETL = executeQuery(dbFCWM6QA, consulta_POS_TPC_DETL);
		
		boolean conexiondbFCWM6QA1_POS_TPC_DETL  = true;
		conexiondbFCWM6QA1_POS_TPC_DETL = ExecuteQuery_POS_TPC_DETL.isEmpty();
		//Se cuenta los registros de la fila
		int cantidad_Registros_POS_TPC_DETL = ExecuteQuery_POS_TPC_DETL.getRowCount();
		System.out.println(cantidad_Registros_POS_TPC_DETL);
		//Se guarda en una lista de string la fila 1
		List<String> datos_POS_POS_TPC_DETL= ExecuteQuery_POS_TPC_DETL.getFirstRow();
		//Se guarda el valor 1
		String POS_TPC_DETL = datos_POS_POS_TPC_DETL.get(1);
		//String id_POS_TPC = datos_POS_INBOUND_DOCS.get(3);
		System.out.println("Consulta: " + POS_TPC_DETL);
		//Se toma la evidencia
		testCase.addTextEvidenceCurrentStep("Conexion: FCWMREQA");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMREQA);
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery_POS_TPC_DETL);
		//Se valida si la conexion  no fue exitosa
		assertFalse(conexiondbFCWM6QA1_POS_TPC_DETL, "La conexion no fue exitosa");
	}

	@Override
	public void beforeTest() {
	}	

	@Override
	public String setTestDescription() {
		return "Validar en la base de datos el registro de TPC";
	}

	@Override
	public String setTestDesigner() {
		return "Mario.garza@grupotimexico.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_Validar_en_la_base_de_datos_el_registro_de_TPC";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Acceso a base de datos FCWM6QA";
	}

	@AfterMethod
	public void afterMethod() {

	}

}
