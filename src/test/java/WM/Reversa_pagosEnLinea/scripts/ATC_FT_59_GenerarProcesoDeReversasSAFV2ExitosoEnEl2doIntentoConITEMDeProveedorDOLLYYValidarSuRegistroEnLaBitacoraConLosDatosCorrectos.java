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

/** DESCRIPCIÓN: Caso de prueba que valida el proceso de reversas SAFv2 exitoso en el 2do intento con ITEM de proveedor DOLLY y validar su registro en la bitacora con los datos correctos 
* 
* *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* MTC-FT-20-Generar proceso de reversas SAFv2 exitoso en el 2do intento con ITEM de proveedor DOLLY y validar su registro en la bitacora con los datos correctos
* 
* PREREQUISITOS:
* Contar con las credenciales de la base de datos FCTPEQA 
* Contar con el nuevo servicio en ambiente QA
* Contar con el ambiente de pruebas del proveedor
* Contar con las credenciales de Control M
* Contar con la transacción de Reverso realizado 
* Contar con el cambio en la url en la BD para el fallo en el reverso
* 
* INSUMOS:
* URL para realizar peticion 
* 
* @author Melba Solis
* @date 21/02/2025   */

public class ATC_FT_59_GenerarProcesoDeReversasSAFV2ExitosoEnEl2doIntentoConITEMDeProveedorDOLLYYValidarSuRegistroEnLaBitacoraConLosDatosCorrectos extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_59_GenerarProcesoDeReversasSAFV2ExitosoEnEl2doIntentoConITEMDeProveedorDOLLYYValidarSuRegistroEnLaBitacoraConLosDatosCorrectos_test (HashMap <String, String> data) throws Exception {
		
		/* Utils**************************************************************/
		//SQLUtil dbFCTPE = new SQLUtil(GlobalVariables.DB_HOST_FCTPE, GlobalVariables.DB_USER_FCTPE, GlobalVariables.DB_PASSWORD_FCTPE);
		SQLUtil	dbFCTPE = new SQLUtil(GlobalVariables.DB_HOST_FCTPE_QRO, GlobalVariables.DB_USER_FCTPE_QRO, GlobalVariables.DB_PASSWORD_FCTPE_QRO);
		
		//Comienza Script 	
		/*Query dbFCTPE MTI */
		addStep("Se valida en la BD FCTPEQA en la tabla TPE_FR_TRANSACTION  que la transaccion se encuentre registrada con el campo MTI en 0420");
		String queryValidaTranRev = data.get("Query");
		System.out.println("Tu consulta es: " + queryValidaTranRev); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
		SQLResult resultValidaTranRev = dbFCTPE.executeQuery(queryValidaTranRev);
		System.out.println(queryValidaTranRev);
		System.out.println(resultValidaTranRev.getColumnNames());
		System.out.println(resultValidaTranRev.getFirstRow());
		String mti = resultValidaTranRev.getData(0, "MTI");
		String operation = resultValidaTranRev.getData(0, "OPERATION");
		String reversed = resultValidaTranRev.getData(0, "REVERSED");
		String revCounter = resultValidaTranRev.getData(0, "REV_COUNTER");
		assertTrue(resultValidaTranRev.getRowCount() > 0, "No se encontraron datos en la consulta inicial");
		assertEquals(mti, "0420", "El código 0420 no se encuentra o no coincide con el esperado");
		assertEquals(operation, "TRN03", "El resultado no se encuentra o no coincide con el esperado");
		assertEquals(reversed, "0", "El reverse no se encuentra o no coincide con el esperado");
		assertEquals(revCounter, "0", "El revCounter no se encuentra o no coincide con el esperado");
		testCase.addQueryEvidenceCurrentStep(resultValidaTranRev);
		
		/*Query dbFCTPE MTI 0402 SAF VALIDACION EN BD*/
		addStep("Se valida en la BD FCTPEQA en la tabla TPE_FR_SAF que la transaccion se encuentre registrada con el campo STATUS en INICIAL");
		String queryValidaTranRevJ = data.get("QuerySaf");
		System.out.println("Tu consulta es: " + queryValidaTranRevJ); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
		SQLResult resultValidaTranRevJ = dbFCTPE.executeQuery(queryValidaTranRevJ);
		System.out.println(queryValidaTranRevJ);
		System.out.println(resultValidaTranRevJ.getRowCount());
		System.out.println(resultValidaTranRevJ.getColumnNames());
		System.out.println(resultValidaTranRevJ.getFirstRow());
		String status = resultValidaTranRevJ.getData(0, "STATUS");
		String counter = resultValidaTranRevJ.getData(0, "COUNTER");
		String flagSaf = resultValidaTranRevJ.getData(0, "FLAG");
		operation = resultValidaTranRevJ.getData(0, "OPERATION");
		mti = resultValidaTranRevJ.getData(0, "MTI");
		assertTrue(resultValidaTranRevJ.getRowCount() > 0, "No se encontraron datos en la consulta Saf inicial");
		assertEquals(mti, "0420", "El código 0420 no se encuentra o no coincide con el esperado");
		assertEquals(operation, "TRN04", "El resultado no se encuentra o no coincide con el esperado");
		assertEquals(counter, "0", "El resultado del contador no se encuentra o no coincide con el esperado");
		assertEquals(status, "INICIAL", "El STATUS no se encuentra o no coincide con el esperado");
		assertNull(flagSaf, "El FLAG no se encuentra o no coincide con el esperado: null");
		testCase.addQueryEvidenceCurrentStep(resultValidaTranRevJ);

		//Se ejecuta Job runSAFManager
		addStep("Ejecutar el job runSAFManager");
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
		addStep("Se ejecuta de manera exitosa el job runSAFManager");
		addStep("La ejecución dura (500-1000) MiliSegundos");
		u.quit();

		/*Query dbFCTPE MTI 0402 SAF VALIDACION EN BD*/
		addStep("Se valida en la BD FCTPEQA en la tabla TPE_FR_SAF que la transaccion se encuentre registrada con el campo STATUS en EN PROCESO");
		Thread.sleep(30000);
		String queryValidaTranRevJob = data.get("QuerySaf");
		System.out.println("Tu consulta es: " + queryValidaTranRevJob); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
		SQLResult resultValidaTranRevJob = dbFCTPE.executeQuery(queryValidaTranRevJob);
		System.out.println(queryValidaTranRevJob);
		System.out.println(resultValidaTranRevJob.getRowCount());
		System.out.println(resultValidaTranRevJob.getColumnNames());
		System.out.println(resultValidaTranRevJob.getFirstRow());
		operation = resultValidaTranRevJob.getData(0, "OPERATION");
		mti = resultValidaTranRevJob.getData(0, "MTI");
		status = resultValidaTranRevJob.getData(0, "STATUS");
		counter = resultValidaTranRevJob.getData(0, "COUNTER");
		flagSaf = resultValidaTranRevJob.getData(0, "FLAG");
		assertTrue(resultValidaTranRevJob.getRowCount() > 0, "No se encontraron datos en la consulta Saf tras la ejecución del Job");
		assertEquals(mti, "0420", "El código 0420 no se encuentra o no coincide con el esperado");
		assertEquals(operation, "TRN04", "El resultado no se encuentra o no coincide con el esperado");
		assertEquals(counter, "1", "El resultado del contador no se encuentra o no coincide con el esperado");
		assertEquals(status, "TERMINADO", "El STATUS no se encuentra o no coincide con el esperado");
		assertEquals(flagSaf, "Versión 2", "El FLAG no se encuentra o no coincide con el esperado: version 2");
		testCase.addQueryEvidenceCurrentStep(resultValidaTranRevJob);

		/*Query Bitacora */
		addStep("Realizar una con consulta filtrada en la BD FCTPEQA hacia la tabla de bitacora de registro	");
		String queryBitacora = data.get("QueryBitacora");
		SQLResult resultBitacora = dbFCTPE.executeQuery(queryBitacora);
		System.out.println(queryBitacora);
		System.out.println(resultBitacora.getRowCount());
		System.out.println(resultBitacora.getColumnNames());
		System.out.println(resultBitacora.getFirstRow());
		assertTrue(resultBitacora.getRowCount() > 0, "No se encontraron registros en la bitácora");
		assertEquals(resultBitacora.getData(0, "COUNTER"), "2", "El resultado del contador no se encuentra o no coincide con el esperado");
		testCase.addQueryEvidenceCurrentStep(resultBitacora);

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
		+ "4.- Contar con las credenciales de Control M.\r\n"
		+ "5.- Contar con la transacción de Reverso realizado.\r\n"
		+ "6.- Contar con el cambio en la url en la BD para el fallo en el reverso";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Melba Solis";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_59_GenerarProcesoDeReversasSAFV2ExitosoEnEl2doIntentoConITEMDeProveedorDOLLYYValidarSuRegistroEnLaBitacoraConLosDatosCorrectos_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Valida el proceso de reversas SAFv2 exitoso en el 2do intento con ITEM de proveedor DOLLY y validar su registro en la bitacora con los datos correctos";
	}
}