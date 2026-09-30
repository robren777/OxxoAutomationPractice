package WM.Reversa_pagosEnLinea.scripts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
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
* ATC_FT_02_RealizarReversoEnLineaNoExitosoConProveedorCONEKTAYValidarQueSeRegistreEnProcesoSAF
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
* @author Juan Gerardo Lopez
* @date 11/03/2025
*/

public class ATC_FT_47_EjecutarJOBDeProcesoDeReversasV2CuandoNoExistaAlgunaReversaQueProcesar extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_46_EjecutarJOBDeProcesoDeReversasV2CuandoSeEncuentreUnaReversaDeEntityConNivel2EITEMConPrioridadBaja_test (HashMap <String, String> data) throws Exception {
		
		/* Utils**************************************************************/
		SQLUtil dbFCTPE = new SQLUtil(GlobalVariables.DB_HOST_FCTPE, GlobalVariables.DB_USER_FCTPE,
				GlobalVariables.DB_PASSWORD_FCTPE);
		SQLUtil dbFCWMLTAQ= new SQLUtil(GlobalVariables.DB_HOST_FCWMLTAQ_MTY, GlobalVariables.DB_USER_FCWMLTAQ_MTY,
				GlobalVariables.DB_PASSWORD_FCWMLTAQ_MTY);
		SeleniumUtil u;
		String pssDecrypted = PasswordUtil.decryptPassword(data.get("ps"));
		Control_mInicio cm;
		JSONObject obj = new JSONObject(data.get("job"));
		ControlM job;
		String queryLog = data.get("queryLog");
		String querysaf = data.get("querysaf");
		
		/*       PASOS
		 * "Ingresar y validar en la BD FCTPEQA en la tabla de TPE_FR_SAF que no se encuentren reversas a procesar o con estatus INICIAL o EN PROCESO
			SELECT
			    *
			FROM
			    tpeuser.tpe_fr_saf
			WHERE
			    entity = 'CNKTA'
			    AND APPLICATION = 'OLS'
			    AND creation_date >= SYSDATE - 1
			    order by creation_date desc"

		 *Ejecutamos el query
		 *"Ingresar a Control-M y ejecutar nuevo JOB de proceso centralizado de reversas SAF o Levantar ticket a equipo correspondiente para la ejecucion del JOB"
		 *Validar que no se realice ningun movimiento en la TPE_FR_SAF
		 */

		//*****PRE REQUISITO*********
		
		//******TERMINA PREREQUISITO*********
		
		//Comienzo de Script
		
		//Validar que en la tabla SAf no se encuentre ninguna reversa procesar o en estatus inicial, reprocesar, terminada o expirada
		addStep("Se valida el estado de la tabla FR_SAF para determinar si el script puede continuar.");
		System.out.println("Tu consulta es: " + querysaf);
		SQLResult resultValidSaf = dbFCTPE.executeQuery(querysaf);

		int rowCount = resultValidSaf.getRowCount();
		System.out.println("Número de registros encontrados: " + rowCount);

		// Si la consulta no devuelve datos, se permite continuar con el script
		if (rowCount == 0) {
		    System.out.println("No hay registros en la tabla FR_SAF. Se permite continuar con el script.");
		    return; // Sale de la validación y permite continuar con el flujo
		}

		// Variable de control
		boolean contieneBloqueo = false;

		//Ver todas las filas obtenidas
		for (String[] fila : resultValidSaf.getAllData()) {
		    System.out.println("Fila obtenida: " + Arrays.toString(fila));
		    
		    // Ajusta el índice correcto según la columna donde está STATUS
		    String status = fila[3].trim().toUpperCase();

		    // Si encontramos un STATUS "INICIAL" o "REPROCESADO", detenemos el script
		    if ("INICIAL".equals(status) || "REPROCESAR".equals(status)) {
		        contieneBloqueo = true;
		        System.out.println("Se encontró un registro con STATUS = " + status + ". El script no puede continuar.");
		        throw new AssertionError("Se encontraron registros con STATUS no permitido: " + status);
		    }
		}

		// Validación final
		if (contieneBloqueo) {
		    System.out.println("El script no puede continuar porque hay registros con STATUS = INICIAL o REPROCESADO.");
		    throw new AssertionError("Se encontraron registros con STATUS no permitido para continuar con el script.");
		} else {
		    System.out.println("Todos los registros tienen STATUS = TERMINADO o EXPIRADO. Se permite continuar.");
		}
		
		//Evidencia
		testCase.addQueryEvidenceCurrentStep(resultValidSaf);
		
		//Se realiza ejecucion de JOB runSAFV2
		addStep("Ingresar a Control-M y ejecutar nuevo JOB de proceso centralizado de reversas SAF");
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
		testCase.addBoldTextEvidenceCurrentStep("Se ejecuta de manera exitosa el job runSAFV2");
		testCase.addBoldTextEvidenceCurrentStep("La ejecución dura (500-1000) MiliSegundos");
		u.quit();
		
		//Validar que la ejecucion del JOB no haya realizado ningun movimiento en la tabla SAF
		addStep("Validar que la ejecución del JOB no haya generado registros con STATUS prohibido en la tabla SAF");
		System.out.println("Ejecutando validación post-JOB...");

		// Ejecutar la consulta después del proceso
		SQLResult resultValidSaf2 = dbFCTPE.executeQuery(querysaf);
		int rowCount2 = resultValidSaf2.getRowCount();
		System.out.println("Número de registros encontrados: " + rowCount2);

		// Revisamos si algún registro tiene STATUS prohibido
		for (String[] fila : resultValidSaf2.getAllData()) {
		    String status = fila[3].trim().toUpperCase();

		    if ("INICIAL".equals(status) || "REPROCESAR".equals(status)) {
		        System.out.println("ERROR: Se encontró STATUS prohibido: " + status);
		        throw new AssertionError("El JOB dejó registros con STATUS prohibido: " + status);
		    }
		}

		// Si no hay STATUS prohibidos, la validación pasa
		System.out.println("Validación exitosa. No hay registros con STATUS prohibido después del JOB.");
		testCase.addQueryEvidenceCurrentStep(resultValidSaf2);
		testCase.addTextEvidenceCurrentStep("Ningun registro prohibido fue encontrado");
		
		//Validar la tabla de LOGS para visualizacion de error que genero el JOB
		addStep("Validar la tabla de WM_LOG_ERROR_TPE en FCWMLTAQ para visualización de error que generó el JOB");
		System.out.println("Tu consulta es: " + queryLog);
		SQLResult resultValidLog = dbFCWMLTAQ.executeQuery(queryLog);
		int rowCountLog = resultValidLog.getRowCount();
		System.out.println("Número de registros encontrados: " + rowCountLog);

		// Si la consulta no devuelve datos, se considera fallido
		if (rowCountLog == 0) {
		    System.out.println("No se encontraron registros con el error esperado en el día actual.");
		    throw new AssertionError("No se encontraron registros con el error esperado en la tabla WM_LOG_ERROR_TPE.");
		}

		// Obtener la fecha actual en formato "yyyy-MM-dd"
		SQLResult currentDate = dbFCWMLTAQ.executeQuery("SELECT TO_CHAR(SYSDATE, 'YYYY-MM-DD') \"NOW\" FROM DUAL");
		
		// Variable de control
		boolean encontrado = false;
		String[] filaEncontrada = null;  // Para almacenar la fila con el error esperado
		
		for (String[] fila : resultValidLog.getAllData()) {  
		    String errorDate = fila[1].split(" ")[0];
		    String description = fila[2];	    

		    // Validar si la fecha es del día actual y la descripción contiene el mensaje esperado
		    if (errorDate.equals(currentDate.getData(0, "NOW")) && description.contains("TPE.FR.Mapping:mainSafApplication : No existen datos para procesar en la tabla TPE_FR_SAF")) {
		        encontrado = true;
		        filaEncontrada = fila;
		        break;
		    }
		}

		// Validación final
		if (encontrado) {
		    System.out.println(" Se encontró un registro con el error esperado en el día actual.");
		    System.out.println(" Fila encontrada: " + Arrays.toString(filaEncontrada));
		    
		    // Agregar la fila encontrada como evidencia en formato de texto
			testCase.addQueryEvidenceCurrentStep(resultValidLog);
		    testCase.addCodeEvidenceCurrentStep("Fila con error encontrada: " + Arrays.toString(filaEncontrada));
		} else {
		    System.out.println(" No se encontró un registro con el error esperado en el día actual.");
		    throw new AssertionError("No se encontró el error esperado en la tabla WM_LOG_ERROR_TPE.");
		}
 
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
		return "ATC_FT_02_RealizarReversoEnLineaNoExitosoConProveedorCONEKTAYValidarQueSeRegistreEnProcesoSAF";
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