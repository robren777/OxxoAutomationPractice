package WM.Reversa_pagosEnLinea.scripts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import WM.util.GlobalVariables;
//import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

//@AzureTestInfo(
//		areaPath = "Test-POCFrameworkOxxo\\test1",
//		iterationPath = "Test-POCFrameworkOxxo\\Iteration 1",
//		tags = "TestTag1;TestTag2",
//		userStoryId = 17086
//)

/**
* DESCRIPCIÓN: Caso de prueba que valida que se ejecute el job de proceso de reversa V2 exitosamente 
* 
* *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* ATC_FT_40_EjecutarJOBDeProcesoDeReversasV2Correctamente
* 
* PREREQUISITOS:
* Contar con las credenciales de la base de datos FCTPEQA 
* Contar con el nuevo servicio en ambiente QA
* Contar con el ambiente de pruebas del proveedor
* Contar con el reverso realizado 
* 
* INSUMOS:
* JOB de control M
* 
* @author Juan Valadez
* @date 20/02/2025
*/

public class ATC_FT_40_EjecutarJOBDeProcesoDeReversasV2Correctamente extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_40_EjecutarJOBDeProcesoDeReversasV2Correctamente_test (HashMap <String, String> data) throws Exception {
		
		/* Utils**************************************************************/
		SQLUtil dbFCTPE = new SQLUtil(GlobalVariables.DB_HOST_FCTPE, GlobalVariables.DB_USER_FCTPE,
				GlobalVariables.DB_PASSWORD_FCTPE);
		
		//Comienzo de Script
		/***************************** Paso 1 ************************************/
		addStep("Validar en la BD FCTPEQA en la tabla de TPE_FR_SAF que la reversa NO se encuentre registrada en SAFv2	");
		String queryValidaSAFv2 = data.get("querySAF");
		SQLResult resultValidaSAFv2 = dbFCTPE.executeQuery(queryValidaSAFv2);
		System.out.println(queryValidaSAFv2);
		System.out.println(resultValidaSAFv2.getRowCount());
		System.out.println(resultValidaSAFv2.getColumnNames());
		 assertEquals(resultValidaSAFv2.getData(0, "STATUS"), "INICIAL");		
		testCase.addQueryEvidenceCurrentStep(resultValidaSAFv2);
		
		addStep("Acceder a control M y Ejecutar el job runTPECopiaRST");
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		String pssDecrypted = PasswordUtil.decryptPassword(data.get("ps"));
		Control_mInicio cm = new Control_mInicio(u, data.get("user"), pssDecrypted);
		u.get(data.get("server"));
 
		u.waitForLoadPage();
		u.waitForLoadPage();
		cm.logOn();
 
		JSONObject obj = new JSONObject(data.get("job"));
		ControlM job= new ControlM(u, testCase, obj);
		boolean flag = job.searchJob();
 
		assertTrue(flag);
      
        
		String resultado1 = job.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado1);
		System.out.println ("Valor de output :" +job.getOutput());
		assertEquals(resultado1, "Ended OK");
 
		job.closeViewpoint();
		addStep("Se ejecuta de manera exitosa el job runTPECopiaRST");
		addStep("La ejecución dura (500-1000) MiliSegundos");
		u.quit();
		
		/*Query SAFv2 */
		addStep("Validar en la BD FCTPEQA en la tabla de TPE_FR_SAF que la reversa NO se encuentre registrada en SAFv2	");
		resultValidaSAFv2 = dbFCTPE.executeQuery(queryValidaSAFv2);
		System.out.println(queryValidaSAFv2);
		System.out.println(resultValidaSAFv2.getRowCount());
		System.out.println(resultValidaSAFv2.getColumnNames());
		assertEquals(resultValidaSAFv2.getData(0, "STATUS"), "TERMINADO");
		 assertEquals(resultValidaSAFv2.getData(0, "FLAG"), "Versión 2", "No se encontro resultado esperado");
		testCase.addQueryEvidenceCurrentStep(resultValidaSAFv2);
		
	}
	
	@Override
	public void beforeTest(){
		// TODO Auto-generated method stub

	}
	
	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Contar con las credenciales de la base de datos FCTPEQA \r\n"
				+ "2. Contar con el nuevo servicio en ambiente QA.\r\n"
				+ "3. Contar con el ambiente de pruebas del proveedor.\r\n"
				+ "4. Contar con el reverso realizado ";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Juan Valadez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_40_EjecutarJOBDeProcesoDeReversasV2Correctamente";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se realizara reverso en linea con el nuevo servicio hacia el proveedor CNKTA";
	}
}