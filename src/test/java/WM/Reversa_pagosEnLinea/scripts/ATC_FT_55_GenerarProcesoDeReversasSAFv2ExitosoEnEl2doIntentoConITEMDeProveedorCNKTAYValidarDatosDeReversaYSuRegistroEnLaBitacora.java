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
* 	MTC-FT-16-Generar proceso de reversas SAFv2 exitoso en el 2do intento con ITEM de proveedor CNKTA y validar datos de reversa y su registro en la bitacora
* 
* PREREQUISITOS:
* Contar con las credenciales de la base de datos FCTPEQA 
* Contar con el nuevo servicio en ambiente QA
* Contar con el ambiente de pruebas del proveedor
* Contar con algun folio procesado para su reversa valido
* 
* INSUMOS:
* URL para realizar peticion
* 
* @author Gabriel J. Rubio
* @date 21/01/2024
*/

public class ATC_FT_55_GenerarProcesoDeReversasSAFv2ExitosoEnEl2doIntentoConITEMDeProveedorCNKTAYValidarDatosDeReversaYSuRegistroEnLaBitacora extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_55_GenerarProcesoDeReversasSAFv2ExitosoEnEl2doIntentoConITEMDeProveedorCNKTAYValidarDatosDeReversaYSuRegistroEnLaBitacora_test (HashMap <String, String> data) throws Exception {
		
		/* Utils**************************************************************/

		SQLUtil dbFCTPE = new SQLUtil(GlobalVariables.DB_HOST_FCTPE, GlobalVariables.DB_USER_FCTPE,
				GlobalVariables.DB_PASSWORD_FCTPE);
		SeleniumUtil u;
		String pssDecrypted = PasswordUtil.decryptPassword(data.get("ps"));
		Control_mInicio cm;
		JSONObject obj = new JSONObject(data.get("job"));
		ControlM job;
		String queryValidaTransaction = data.get("Query");
		String queryValidaSaf = data.get("QuerySaf");
		String queryBitacora = data.get("QueryBitacora");
		String folio = data.get("folio");
		Time timeValue = Time.valueOf("00:00:00");
		
		//Comienza Script 	
		/*Query dbFCTPE MTI */
		addStep("Se valida en la BD FCTPEQA en la tabla TPE_FR_TRANSACTION  que la transaccion se encuentre registrada con el campo MTI en 0420");
		
		SQLResult resultValidaTranRev = dbFCTPE.executeQuery(queryValidaTransaction);
		System.out.println(queryValidaTransaction);
		System.out.println(resultValidaTranRev.getColumnNames());
		System.out.println(resultValidaTranRev.getFirstRow());
		assertTrue(resultValidaTranRev.getRowCount() > 0, "No se encontraron datos en la consulta inicial");
		assertEquals(resultValidaTranRev.getData(0, "FOLIO"), folio, "El folio no se encuentra o no coincide con el esperado");
		System.out.println("Folio obtenido: " + folio);
		assertEquals(resultValidaTranRev.getData(1, "MTI"), "0420", "El código 0420 no se encuentra o no coincide con el esperado");
		assertEquals(resultValidaTranRev.getData(0, "OPERATION"), "TRN03", "El resultado no se encuentra o no coincide con el esperado");
		assertEquals(resultValidaTranRev.getData(0, "REVERSED"), "2", "El reverse no se encuentra o no coincide con el esperado");
		assertEquals(resultValidaTranRev.getData(0, "REV_COUNTER"), "0", "El revCounter no se encuentra o no coincide con el esperado");
		testCase.addQueryEvidenceCurrentStep(resultValidaTranRev);
		
		/*Query dbFCTPE SAF VALIDACION EN BD*/
		addStep("Se valida en la BD FCTPEQA en la tabla TPE_FR_SAF que la transaccion se encuentre registrada con el campo STATUS en INICIAL");
		System.out.println("Tu consulta es: " + queryValidaSaf); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
		
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
		
		//Se ejecuta Job runSAFManager
		addStep("Ejecutar el job runSAFManager Intento 1");
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
		System.out.println("Resultado de la ejecucion -> " + resultado1);
		System.out.println ("Valor de output :" +job.getOutput());
		assertEquals(resultado1, "Ended OK");
 		job.closeViewpoint();
		testCase.addBoldTextEvidenceCurrentStep("Se ejecuta de manera exitosa el job runSAFManager");
		testCase.addBoldTextEvidenceCurrentStep("La ejecución dura (500-1000) MiliSegundos");
		u.quit(); 
		
		/*Query dbFCTPE MTI 0402 SAF VALIDACION EN BD */
		addStep("Se valida en la BD FCTPEQA en la tabla TPE_FR_SAF que la transaccion se encuentre registrada con el campo STATUS en REPROCESAR");
		Thread.sleep(10000);//IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
		
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
		assertEquals(resultValidaSaf.getData(0, "COUNTER"), "1", "El resultado del contador no se encuentra o no coincide con el esperado");
		assertEquals(resultValidaSaf.getData(0, "STATUS"), "REPROCESAR", "El STATUS no se encuentra o no coincide con el esperado");
		
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
		assertEquals(resultBitacora.getData(0, "COUNTER"), "2", "El resultado del contador no se encuentra o no coincide con el esperado");
		testCase.addQueryEvidenceCurrentStep(resultBitacora);

		for (int i=3; i>0; i--) {
			System.out.println("!!!!!Tenemos " + i + "minutos!!!!!!");
			Thread.sleep(60000);
		}
		System.out.println("Continuamos");
		//Se ejecuta Job runSAFManager
		addStep("Ejecutar el job runSAFManager Intento 2");
		u = new SeleniumUtil(new ChromeTest(), true);
		cm = new Control_mInicio(u, data.get("user"), pssDecrypted);
		job= new ControlM(u, testCase, obj);
		u.get(data.get("server"));
		u.waitForLoadPage();
		u.waitForLoadPage();
		cm.logOn();
		flag = job.searchJob();
		assertTrue(flag);
		String resultado2 = job.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado2);
		System.out.println ("Valor de output :" +job.getOutput());
		assertEquals(resultado2, "Ended OK");
 		job.closeViewpoint();
 		testCase.addBoldTextEvidenceCurrentStep("Se ejecuta de manera exitosa el job runSAFManager");
		testCase.addBoldTextEvidenceCurrentStep("La ejecución dura (500-1000) MiliSegundos");
		u.quit(); 
		
		/*Query dbFCTPE MTI 0402 SAF VALIDACION EN BD */
		addStep("Se valida en la BD FCTPEQA en la tabla TPE_FR_SAF que la transaccion se encuentre registrada con el campo STATUS en REPROCESAR");
		Thread.sleep(10000);//IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
		
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
		assertEquals(resultValidaSaf.getData(0, "COUNTER"), "2", "El resultado del contador no se encuentra o no coincide con el esperado");
		assertEquals(resultValidaSaf.getData(0, "STATUS"), "TERMINADO", "El STATUS no se encuentra o no coincide con el esperado");
		
		testCase.addQueryEvidenceCurrentStep(resultValidaSaf); 

		/*Query Bitacora */
		addStep("Realizar una con consulta filtrada en la BD FCTPEQA hacia la tabla de bitacora de registro	");
		resultBitacora = dbFCTPE.executeQuery(queryBitacora);
		System.out.println(queryBitacora);
		System.out.println(resultBitacora.getRowCount());
		System.out.println(resultBitacora.getColumnNames());
		System.out.println(resultBitacora.getFirstRow());
		assertTrue(resultBitacora.getRowCount() > 0, "No se encontraron registros en la bitácora");
		assertEquals(resultBitacora.getData(0, "FOLIO_TPE"), folio, "El folio en la bitácora no coincide");
		assertEquals(resultBitacora.getData(0, "COUNTER"), "3", "El resultado del contador no se encuentra o no coincide con el esperado");
		testCase.addQueryEvidenceCurrentStep(resultBitacora);
		
		Time qryTme = Time.valueOf(resultBitacora.getData(0, "RESPONSE_TIME"));
		timeValue = Time.valueOf(LocalTime.ofNanoOfDay(timeValue.toLocalTime().toNanoOfDay() + qryTme.toLocalTime().toNanoOfDay()));

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
				+ "4.- Contar con algun folio procesado para su reversa valido";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Gabriel J. Rubio";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_55_GenerarProcesoDeReversasSAFv2ExitosoEnEl2doIntentoConITEMDeProveedorCNKTAYValidarDatosDeReversaYSuRegistroEnLaBitacora";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Generar proceso de reversas SAFv2 exitoso en el 2do intento con ITEM de proveedor CNKTA y validar datos de reversa y su registro en la bitacora";
	}
}