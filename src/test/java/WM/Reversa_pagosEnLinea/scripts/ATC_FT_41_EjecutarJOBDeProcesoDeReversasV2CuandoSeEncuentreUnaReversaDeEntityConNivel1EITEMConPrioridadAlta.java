
package WM.Reversa_pagosEnLinea.scripts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
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
* DESCRIPCIÓN: Caso de prueba que valida el reverso de entity con nivel 1 de prioridad alta 
* 
* *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* ATC_FT_41_EjecutarJOBDeProcesoDeReversasV2CuandoSeEncuentreUnaReversaDeEntityConNivel1EITEMConPrioridadAlta
* 
* PREREQUISITOS:
* Contar con las credenciales de la base de datos FCTPEQA 
* Contar con el nuevo servicio en ambiente QA
* Contar con el ambiente de pruebas del proveedor
* Contar con algun folio procesado para su reversa valido
* Contar con folio fallido y que este en SAF con estatus inicial
* 
* INSUMOS:
* job runSAFManager
* Folio de reversa fallido
* 
* @author Juan Valadez
* @date 11/03/2025
*/

public class ATC_FT_41_EjecutarJOBDeProcesoDeReversasV2CuandoSeEncuentreUnaReversaDeEntityConNivel1EITEMConPrioridadAlta extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_41_EjecutarJOBDeProcesoDeReversasV2CuandoSeEncuentreUnaReversaDeEntityConNivel1EITEMConPrioridadAlta_test (HashMap <String, String> data) throws Exception {
		
		/* Utils**************************************************************/
		SQLUtil dbFCTPE = new SQLUtil(GlobalVariables.DB_HOST_FCTPE, GlobalVariables.DB_USER_FCTPE,
				GlobalVariables.DB_PASSWORD_FCTPE);
	    String folio = data.get("Folio");
	    
		//Comienzo de Script
		/***************************** Paso 1 ************************************/
	    /*Query SAFv2 */
		addStep("Validar en la BD FCTPEQA  en tabla TPE_FR_SAF que el folio anteriormente procesado se encuentre en la tabla SAFv2 con campo STATUS TERMINADO");
		String queryValidaSAFv2 = data.get("QuerySaf");
		SQLResult resultValidaSAFv2 = dbFCTPE.executeQuery(queryValidaSAFv2);
		System.out.println(queryValidaSAFv2);
		System.out.println(resultValidaSAFv2.getRowCount());
		System.out.println(resultValidaSAFv2.getColumnNames());
		assertTrue(resultValidaSAFv2.getRowCount() > 0, "No se encontraron datos en la consulta Saf tras la ejecución del Job");
		assertEquals(resultValidaSAFv2.getData(0, "FOLIO_TPE"), folio,"El folio esperado no se encuentra o no coincide con el esperado");
		assertEquals(resultValidaSAFv2.getData(0, "MTI"), "0420", "El código 0420 no se encuentra o no coincide con el esperado");
		assertEquals(resultValidaSAFv2.getData(0, "OPERATION"), "TRN04", "El resultado no se encuentra o no coincide con el esperado");
		assertEquals(resultValidaSAFv2.getData(0, "COUNTER"), "0", "El resultado del contador no se encuentra o no coincide con el esperado");
		assertEquals(resultValidaSAFv2.getData(0, "STATUS"), "INICIAL", "El STATUS no se encuentra o no coincide con el esperado");
		assertNull(resultValidaSAFv2.getData(0, "FLAG"));
	    testCase.addQueryEvidenceCurrentStep(resultValidaSAFv2);
	    
		/*Query Bitacora */
		addStep("Realizar una con consulta filtrada en la BD FCTPEQA hacia la tabla de bitacora de registro	");
		String queryBitacora = data.get("QueryBitacora");
		SQLResult resultBitacora = dbFCTPE.executeQuery(queryBitacora);
		System.out.println(queryBitacora);
		System.out.println(resultBitacora.getRowCount());
		System.out.println(resultBitacora.getColumnNames());
		System.out.println(resultBitacora.getFirstRow());
		assertTrue(resultBitacora.getRowCount() > 0, "No se encontraron registros en la bitácora");
        assertEquals(resultBitacora.getData(0, "FOLIO_TPE"), folio, "El folio en la bitácora no coincide");
        assertEquals(resultBitacora.getData(0, "COUNTER"), "1");
		testCase.addQueryEvidenceCurrentStep(resultBitacora);
		
		
		/*Query transaction*/
		addStep("Se valida en la BD FCTPEQA la tabla de TPE_FR_TRANSACTION  que la transaccion se encuentre registrada");
		String queryValidaTransaccion = data.get("Query");
		SQLResult resultValidaTransaccion = dbFCTPE.executeQuery(queryValidaTransaccion);
		System.out.println(queryValidaTransaccion);
		System.out.println(resultValidaTransaccion.getRowCount());
		System.out.println(resultValidaTransaccion.getColumnNames());
		System.out.println(resultValidaTransaccion.getFirstRow());
		System.out.println(resultValidaTransaccion.getRow(1));
		assertEquals(folio, resultValidaTransaccion.getData(0, "FOLIO"));
		assertEquals(resultValidaTransaccion.getData(0, "REVERSED"), "2");
		assertEquals(resultValidaTransaccion.getData(0, "OPERATION"), "TRN03");
		assertEquals(resultValidaTransaccion.getData(1, "MTI"), "0420");
		assertEquals(resultValidaTransaccion.getData(0, "REV_COUNTER"), "0");
		testCase.addQueryEvidenceCurrentStep(resultValidaTransaccion);
		
				
		/*Query config prioridades */
		addStep("Se valida en la BD FCTPEQA la tabla de TPEUSER.TPE_CONFIG_PRIORIDADES que la transaccion se encuentre registrada");
		String queryValidaPrioridad = data.get("QueryPrioridades"); 
		SQLResult resultValidaPrioridad = dbFCTPE.executeQuery(queryValidaPrioridad);
		System.out.println(queryValidaPrioridad);
		System.out.println(resultValidaPrioridad.getRowCount());
		System.out.println(resultValidaPrioridad.getColumnNames());
		System.out.println(resultValidaPrioridad.getFirstRow());
		assertEquals(resultValidaPrioridad.getData(0, "LEVEL_CONFIG"), "1");
		assertEquals(resultValidaPrioridad.getData(0, "PRIORITY"), "ALTA");
		testCase.addQueryEvidenceCurrentStep(resultValidaPrioridad);
		
		
		addStep("Acceder a control M y Ejecutar el job runSAFV2");
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
		testCase.addBoldTextEvidenceCurrentStep("Se ejecuta de manera exitosa el job runSAFV2");
		testCase.addBoldTextEvidenceCurrentStep("La ejecución dura (500-1000) MiliSegundos");
		u.quit();
		
		/*Query transaction*/
		addStep("Se valida en la BD FCTPEQA la tabla de TPE_FR_TRANSACTION  que la transaccion se encuentre registrada correctamente");
		resultValidaTransaccion = dbFCTPE.executeQuery(queryValidaTransaccion);
		System.out.println(queryValidaTransaccion);
		System.out.println(resultValidaTransaccion.getRowCount());
		System.out.println(resultValidaTransaccion.getColumnNames());
		System.out.println(resultValidaTransaccion.getRow(1));
		assertEquals(resultValidaTransaccion.getData(1, "FOLIO"), folio);
		assertEquals(resultValidaTransaccion.getData(1,"REVERSED"), "1");
		assertEquals(resultValidaTransaccion.getData(1,"REV_COUNTER"), "1");
		assertEquals(resultValidaTransaccion.getData(1,"OPERATION"), "TRN04");
		testCase.addQueryEvidenceCurrentStep(resultValidaTransaccion);
		
		/*Query SAFv2 */
		addStep("Validar en la BD FCTPEQA  en tabla TPE_FR_SAF que el folio anteriormente procesado se encuentre en la tabla SAFv2 con campo STATUS TERMINADO");
		resultValidaSAFv2 = dbFCTPE.executeQuery(queryValidaSAFv2);
		System.out.println(queryValidaSAFv2);
		System.out.println(resultValidaSAFv2.getRowCount());
		System.out.println(resultValidaSAFv2.getColumnNames());
		assertTrue(resultValidaSAFv2.getRowCount() > 0, "No se encontraron datos en la consulta Saf tras la ejecución del Job");
		assertEquals(folio, resultValidaSAFv2.getData(0, "FOLIO_TPE"),"El folio esperado no se encuentra o no coincide con el esperado");
		assertEquals(resultValidaSAFv2.getData(0, "MTI"), "0420", "El código 0420 no se encuentra o no coincide con el esperado");
		assertEquals(resultValidaSAFv2.getData(0, "OPERATION"), "TRN04", "El resultado no se encuentra o no coincide con el esperado");
		assertEquals(resultValidaSAFv2.getData(0, "FLAG"), "Versión 2", "El FLAG no se encuentra o no coincide con el esperado: version 2");
		assertEquals(resultValidaSAFv2.getData(0, "COUNTER"), "1", "El resultado del contador no se encuentra o no coincide con el esperado");
		assertEquals(resultValidaSAFv2.getData(0, "STATUS"), "TERMINADO", "El STATUS no se encuentra o no coincide con el esperado");
		
		testCase.addQueryEvidenceCurrentStep(resultValidaSAFv2);
		
		/*Query Bitacora */
		addStep("Realizar una con consulta filtrada en la BD FCTPEQA hacia la tabla de bitacora de registro	");
		resultBitacora = dbFCTPE.executeQuery(queryBitacora);
		System.out.println(queryBitacora);
		System.out.println(resultBitacora.getRowCount());
		System.out.println(resultBitacora.getColumnNames());
		System.out.println(resultBitacora.getFirstRow());
		assertTrue(resultBitacora.getRowCount() > 0, "No se encontraron registros en la bitácora");
        assertEquals(resultBitacora.getData(0, "FOLIO_TPE"), folio, "El folio en la bitácora no coincide");
        assertEquals(resultBitacora.getData(0, "COUNTER"), "2");
		testCase.addQueryEvidenceCurrentStep(resultBitacora);
		
		/*Query config prioridades */
		addStep("Se valida en la BD FCTPEQA la tabla de TPEUSER.TPE_CONFIG_PRIORIDADES que la transaccion se encuentre registrada");
		resultValidaPrioridad = dbFCTPE.executeQuery(queryValidaPrioridad);
		System.out.println(queryValidaPrioridad);
		System.out.println(resultValidaPrioridad.getRowCount());
		System.out.println(resultValidaPrioridad.getColumnNames());
		assertEquals(resultValidaPrioridad.getRowCount(),0);
		testCase.addQueryEvidenceCurrentStep(resultValidaPrioridad);
		
		
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
				+ "4.- Contar con algun folio procesado para su reversa valido";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Juan Valadez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_41_EjecutarJOBDeProcesoDeReversasV2CuandoSeEncuentreUnaReversaDeEntityConNivel1EITEMConPrioridadAlta";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Caso de prueba que valida el reverso de entity con nivel 1 de prioridad alta";
	}
}