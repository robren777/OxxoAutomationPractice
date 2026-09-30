package WM.Reversa_pagosEnLinea.scripts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.sql.Time;
import java.time.LocalTime;
import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import WM.util.GlobalVariables;
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
* DESCRIPCIÓN: Caso de prueba que valida el reverso en linea exitoso con proveedor CNKTA
* 
* *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
*	MTC-FT-10-Procesar mas de 5 veces reversas mediante SAFv2 y validar que un 6to intento no se registre en la tabla TPE_FR_SAF y nueva bitacora
* 
* PREREQUISITOS:
* Contar con las credenciales de la base de datos FCTPEQA 
* Contar con el nuevo servicio en ambiente QA
* Contar con el ambiente de pruebas del proveedor
* Contar con algun folio con una reversa fallida
* Contar con acceso al JOB Saf
* 
* INSUMOS:
* Folio de reversa fallida
* queries a BD
* accesos para Control M y Job
* 
* @author Gabriel J. Rubio
* @date 21/01/2024
*/

public class ATC_FT_49_ProcesarMasDe5VecesReversasMedianteSAFV2YValidarQueUn6toIntentoNoSeRegistreEnLaTablaTPE_FR_SAF extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_49_ProcesarMasDe5VecesReversasMedianteSAFV2YValidarQueUn6toIntentoNoSeRegistreEnLaTablaTPE_FR_SAF_test (HashMap <String, String> data) throws Exception {
		
		/* Utils**************************************************************/

		SQLUtil dbFCTPE = new SQLUtil(GlobalVariables.DB_HOST_FCTPE_QRO, GlobalVariables.DB_USER_FCTPE_QRO,
				GlobalVariables.DB_PASSWORD_FCTPE_QRO);
		SeleniumUtil u;
		String pssDecrypted = PasswordUtil.decryptPassword(data.get("ps"));
		Control_mInicio cm;
		JSONObject obj = new JSONObject(data.get("job"));
		ControlM job;
		String queryValidaTransaction = data.get("Query");
		String queryValidaSaf = data.get("QuerySaf");
		String queryBitacora = data.get("QueryBitacora");
		String folio = data.get("folio");
		String entity = data.get("entity");
		Time timeValue = Time.valueOf("00:00:00");
		 	
		/*Query dbFCTPE MTI */
		addStep("Se valida en la BD FCTPEQA en la tabla TPE_FR_TRANSACTION  que la transaccion se encuentre registrada con el campo MTI en 0420");
		SQLResult resultValidaTranRev = dbFCTPE.executeQuery(queryValidaTransaction);
		System.out.println(queryValidaTransaction);
		System.out.println(resultValidaTranRev.getColumnNames());
		System.out.println(resultValidaTranRev.getFirstRow());
		assertEquals(resultValidaTranRev.getRowCount(), 1);
		assertEquals(resultValidaTranRev.getData(0, "FOLIO"), folio, "El folio no se encuentra o no coincide con el esperado");
		assertEquals(resultValidaTranRev.getData(0, "MTI"), "0420", "El código 0420 no se encuentra o no coincide con el esperado");
		assertEquals(resultValidaTranRev.getData(0, "OPERATION"), "TRN03", "El resultado no se encuentra o no coincide con el esperado");
		assertEquals(resultValidaTranRev.getData(0, "REVERSED"), "0", "El reverse no se encuentra o no coincide con el esperado");
		assertEquals(resultValidaTranRev.getData(0, "REV_COUNTER"), "0", "El revCounter no se encuentra o no coincide con el esperado");
		testCase.addQueryEvidenceCurrentStep(resultValidaTranRev);
		
		/*Query dbFCTPE SAF VALIDACION EN BD*/
		addStep("Se valida en la BD FCTPEQA en la tabla TPE_FR_SAF que la transaccion se encuentre registrada con el campo STATUS en INICIAL");		
		SQLResult resultValidaSaf = dbFCTPE.executeQuery(queryValidaSaf);
		System.out.println(queryValidaSaf);
		System.out.println(resultValidaSaf.getRowCount());
		System.out.println(resultValidaSaf.getColumnNames());
		System.out.println(resultValidaSaf.getFirstRow());
		assertEquals(resultValidaSaf.getRowCount(), 1, "No se encontraron datos en la consulta Saf inicial");
		assertEquals(folio, resultValidaSaf.getData(0, "FOLIO_TPE"), "El folio esperado no se encuentra o no coincide con el esperado");
		assertEquals(resultValidaSaf.getData(0, "MTI"), "0420", "El código 0420 no se encuentra o no coincide con el esperado");
		assertEquals(resultValidaSaf.getData(0, "OPERATION"), "TRN04", "El resultado no se encuentra o no coincide con el esperado");
		assertEquals(resultValidaSaf.getData(0, "COUNTER"), "0", "El resultado del contador no se encuentra o no coincide con el esperado");
		assertEquals(resultValidaSaf.getData(0, "STATUS"), "INICIAL", "El STATUS no se encuentra o no coincide con el esperado");
		assertNull(resultValidaSaf.getData(0, "FLAG"), "El FLAG no se encuentra o no coincide con el esperado: null");
		testCase.addQueryEvidenceCurrentStep(resultValidaSaf);
		
		// El limite de ejeccuciones se deja variable para que funcione no solo con CNKTA si no tambien con DOLLY
		int limite = (entity.equals("CNKTA")) ? 3 : 5; 
		for (int i=0; i<=limite; i++) {
			/*Se ejecuta Job runSAFManagerV2 */
			System.out.println("Ejecucion del job runSAFManagerV2 numero " + String.valueOf(i + 1));
			addStep("Ejecucion del job runSAFManagerV2 numero " + String.valueOf(i + 1));
			u = new SeleniumUtil(new ChromeTest(), true);
			cm = new Control_mInicio(u, data.get("user"), pssDecrypted);
			job= new ControlM(u, testCase, obj);
			u.get(data.get("server"));
			u.waitForLoadPage();
			u.waitForLoadPage();
			cm.logOn();
			boolean flag = job.searchJob();
			assertTrue(flag);
			String resultado1 = job.executeJob();
			if (!resultado1.equals("Ended OK")) {
				System.out.println("----Esperamos 10 segundos-----");
				Thread.sleep(10000);
				resultado1 = job.getNewStatus();
			}
			System.out.println("Resultado de la ejecucion -> " + resultado1);
			System.out.println ("Valor de output :" +job.getOutput());
			
			assertEquals(resultado1, "Ended OK");
	 		job.closeViewpoint();
			testCase.addBoldTextEvidenceCurrentStep("Se ejecuta de manera exitosa el job runDepuraSE");
			testCase.addBoldTextEvidenceCurrentStep("La ejecución dura (500-1000) MiliSegundos");
			u.quit();
			
			System.out.println("----Esperamos 60 Segundos-----");
			Thread.sleep(60000);
			
			/*Query dbFCTPE MTI */
			addStep("Se valida en la BD FCTPEQA en la tabla TPE_FR_TRANSACTION  que la transaccion se encuentre registrada con el campo MTI en 0420");
			
			resultValidaTranRev = dbFCTPE.executeQuery(queryValidaTransaction);
			System.out.println(queryValidaTransaction);
			System.out.println(resultValidaTranRev.getColumnNames());
			System.out.println(resultValidaTranRev.getFirstRow());
			assertEquals(resultValidaTranRev.getRowCount(), 1);
			assertEquals(resultValidaTranRev.getData(0, "FOLIO"), folio, "El folio no se encuentra o no coincide con el esperado");
			assertEquals(resultValidaTranRev.getData(0, "MTI"), "0420", "El código 0420 no se encuentra o no coincide con el esperado");
			assertEquals(resultValidaTranRev.getData(0, "OPERATION"), "TRN04", "El resultado no se encuentra o no coincide con el esperado");
			assertEquals(resultValidaTranRev.getData(0, "REVERSED"), "0", "El reverse no se encuentra o no coincide con el esperado");
			if (i < limite) {
				assertEquals(resultValidaTranRev.getData(0, "REV_COUNTER"), String.valueOf(i + 1), "El resultado del contador no se encuentra o no coincide con el esperado");
			} else {
				assertEquals(resultValidaTranRev.getData(0, "REV_COUNTER"), String.valueOf(limite), "El resultado del contador no se encuentra o no coincide con el esperado");
			}
			testCase.addQueryEvidenceCurrentStep(resultValidaTranRev);
			
			/*Query dbFCTPE MTI 0402 SAF VALIDACION EN BD */
			addStep("Se valida en la BD FCTPEQA en la tabla TPE_FR_SAF que la transaccion se encuentre registrada con el campo STATUS en REPROCESAR");
			
			resultValidaSaf = dbFCTPE.executeQuery(queryValidaSaf);
			System.out.println(queryValidaSaf);
			System.out.println(resultValidaSaf.getRowCount());
			System.out.println(resultValidaSaf.getColumnNames());
			System.out.println(resultValidaSaf.getFirstRow());
			assertTrue(resultValidaSaf.getRowCount() > 0, "No se encontraron datos en la consulta Saf tras la ejecución del Job");
			assertEquals(folio, resultValidaSaf.getData(0, "FOLIO_TPE"),"El folio esperado no se encuentra o no coincide con el esperado");
			assertEquals(resultValidaSaf.getData(0, "MTI"), "0420", "El código 0420 no se encuentra o no coincide con el esperado");
			assertEquals(resultValidaSaf.getData(0, "OPERATION"), "TRN04", "El resultado no se encuentra o no coincide con el esperado");
			assertEquals(resultValidaSaf.getData(0, "FLAG"), "Versión 2", "El FLAG no se encuentra o no coincide con el esperado: version 2");
			if (i < limite) {
				assertEquals(resultValidaSaf.getData(0, "COUNTER"), String.valueOf(i + 1), "El resultado del contador no se encuentra o no coincide con el esperado");
				assertEquals(resultValidaSaf.getData(0, "STATUS"), "REPROCESAR", "El STATUS no se encuentra o no coincide con el esperado");
			} else {
				assertEquals(resultValidaSaf.getData(0, "COUNTER"), String.valueOf(limite), "El resultado del contador no se encuentra o no coincide con el esperado");
				assertEquals(resultValidaSaf.getData(0, "STATUS"), "EXPIRADO", "El STATUS no se encuentra o no coincide con el esperado");
			}
			testCase.addQueryEvidenceCurrentStep(resultValidaSaf); 
	
			/*Query Bitacora */
			addStep("Realizar una con consulta filtrada en la BD FCTPEQA hacia la tabla de bitacora de registro	");
			SQLResult resultBitacora = dbFCTPE.executeQuery(queryBitacora);
			System.out.println(queryBitacora);
			System.out.println(resultBitacora.getRowCount());
			System.out.println(resultBitacora.getColumnNames());
			System.out.println(resultBitacora.getFirstRow());
			assertTrue(resultBitacora.getRowCount() > 0, "No se encontraron registros en la bitácora");
			assertEquals(resultBitacora.getData(0, "FOLIO_TPE"), folio, "El folio en la bitácora no coincide");
			
			if (i < limite) {
				assertEquals(resultBitacora.getData(0, "COUNTER"), String.valueOf(i + 2), "El resultado del contador no se encuentra o no coincide con el esperado");
			} else {
				assertEquals(resultBitacora.getData(0, "COUNTER"), String.valueOf(limite + 1), "El resultado del contador no se encuentra o no coincide con el esperado");
			}
			testCase.addQueryEvidenceCurrentStep(resultBitacora);
			Time qryTme = Time.valueOf(resultBitacora.getData(0, "RESPONSE_TIME"));
			timeValue = Time.valueOf(LocalTime.ofNanoOfDay(timeValue.toLocalTime().toNanoOfDay() + qryTme.toLocalTime().toNanoOfDay()));
		}

		Time tenMin = Time.valueOf("00:10:00");
		assertTrue(timeValue.before(tenMin));
		testCase.addBoldTextEvidenceCurrentStep("Tiempo total de ejecucion en SAF: " + timeValue);
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
				+ "4.- Contar con algun folio procesado para su reversa valido"
				+ "";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Gabriel J. Rubio";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_49_ProcesarMasDe5VecesReversasMedianteSAFV2YValidarQueUn6toIntentoNoSeRegistreEnLaTablaTPE_FR_SAF";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Procesar mas de 5 veces reversas mediante SAFv2 y validar que un 6to intento no se registre en la tabla TPE_FR_SAF y nueva bitacora";
	}
}