package WM.interfaces.tpe.copia;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.testng.annotations.Test;

import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.JobManagement;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_023_TPE_COPIA_PrimeraEjecucionJobRespaldoInfoTPE_FR_TRANSACTION_sinDepuracion extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_023_TPE_COPIA_PrimeraEjecucionJobRespaldoInfoTPE_FR_TRANSACTION_sinDepuracion_test(HashMap<String, String> data) throws Exception {

		/**
		 * Proyecto: Actualizaci�n tecnol�gica X86 (Regresion Enero 2023)
		 * Caso de prueba: MTC-FT-023 TPE_COPIA Primera ejecuci�n del job de respaldo de informaci�n de premia de la tabla TPE_FR_TRANSACTION sin depuraci�n
		 * @author edwin.ramirez
		 * @date 2023/Ene/10
		 */
/*
* Utiler�as
*********************************************************************/

		SQLUtil dbOXTPEQA = new SQLUtil(GlobalVariables.DB_HOST_OXTPEQA,
				GlobalVariables.DB_USER_OXTPEQA, GlobalVariables.DB_PASSWORD_OXTPEQA); 
		SQLUtil dbFCWMREQA = new SQLUtil(GlobalVariables.DB_HOST_FCWMREQA,
				GlobalVariables.DB_USER_FCWMREQA, GlobalVariables.DB_PASSWORD_FCWMREQA);


/**
* Variables
* ******************************************************************************************
* 
*/
//Paso 2
		String consultaCantidadReg = "SELECT COUNT (*) "
				+ "FROM TPEUSER.TPE_FR_TRANSACTION "
				+ "WHERE TRUNC(CREATION_DATE) <= '"+data.get("date")+"'"; //Se cambio fecha para encontrar registros
//Paso 3
		String consultaDetalle = "SELECT * FROM(\r\n"
				+ "SELECT APPLICATION,ENTITY,OPERATION,SOURCE,FOLIO,CREATION_DATE,PLAZA\r\n"
				+ "FROM TPEUSER.TPE_FR_TRANSACTION \r\n"
				+ "WHERE TRUNC(CREATION_DATE) <= '"+data.get("date")+"' \r\n"
				+ "ORDER BY CREATION_DATE DESC\r\n"
				+ ")WHERE ROWNUM <= 10"; 
//Paso 5
		String consultaConfig = "SELECT ID,TABLA,STATUS,TABLA_STATUS,TIPO,FECHA_COPIADA "
				+ "FROM TPEREP.TPE_TABLAS_COPIA "
				+ "WHERE TABLA = 'TPE_FR_TRANSACTION' AND TABLA_STATUS = 'A' \r\n"
				+ "AND TRUNC(FECHA_COPIADA) <= '"+data.get("date")+"'"; //Se cambio fecha para encontrar registros
//Paso 6
		String consultaRegRespaldo = "SELECT COUNT (*) "
				+ "FROM TPEREP.TPE_FR_TRANSACTION_LOY "
				+ "WHERE TRUNC(CREATION_DATE) >= TRUNC(SYSDATE-1 - INTERVAL '5' YEAR)";
//Paso 7
		String  consultaRegRespaldo2 = "SELECT * FROM (\r\n"
				+ "SELECT APPLICATION,ENTITY,OPERATION,SOURCE,FOLIO,CREATION_DATE,PLAZA,TIENDA \r\n"
				+ "FROM TPEREP.TPE_FR_TRANSACTION_LOY \r\n"
				+ "WHERE TRUNC(CREATION_DATE) >= TRUNC(SYSDATE-1 - INTERVAL '5' YEAR) \r\n"
				+ "ORDER BY CREATION_DATE DESC)\r\n"
				+ "WHERE ROWNUM <= 10";
//Paso 10
		Date fechaEjecucionInicio;
//Paso 13
		String consultaNvosReg = "SELECT APPLICATION,ENTITY,OPERATION,SOURCE,FOLIO,CREATION_DATE,PLAZA,TIENDA "
				+ "FROM TPEREP.TPE_FR_TRANSACTION_LOY "
				+ "WHERE TRUNC(CREATION_DATE) = TRUNC(SYSDATE-1)";
//Paso 14
		String consultaTotalReg = "SELECT COUNT (*) "
				+ "FROM TPEREP.TPE_FR_TRANSACTION_LOY "
				+ "WHERE TRUNC(CREATION_DATE) >= TRUNC(SYSDATE-1 - INTERVAL '5' YEAR) "
				+ "ORDER BY CREATION_DATE DESC";
//Paso 15 
		String consultaDetalle2 = "SELECT COUNT (*) "
				+ "FROM TPEREP.TPE_FR_TRANSACTION_LOY "
				+ "WHERE TRUNC(CREATION_DATE) >= TRUNC(SYSDATE-1 - INTERVAL '5' YEAR) "
				+ "ORDER BY CREATION_DATE DESC";
//Paso 16
		String consultaRespInfo = "SELECT RUN_ID,STATUS,FECHAINI,FECHAFIN,FECHAPROCESADA,TABLA,TOTALDESTINO,TOTALFUENTE "
				+ "FROM TPEREP.TPE_CONTROL_COPIA "
				+ "WHERE FECHAINI > SYSDATE-1 AND TABLA = 'TPE_FR_TRANSACTION' "
				+ "ORDER BY FECHAINI DESC";
//Paso 18
		String actualizacion = "SELECT ID, TABLA, STATUS,TABLA_STATUS,TIPO,FECHA_COPIADA,OWNER "
				+ "FROM TPEREP.TPE_TABLAS_COPIA "
				+ "WHERE TABLA = 'TPE_FR_TRANSACTION' "
				+ "AND TABLA_STATUS = 'A' AND TRUNC(FECHA_COPIADA) = TRUNC(SYSDATE-1)";
//Paso
/**
* 	
* **********************************Pasos del caso de Prueba *****************************************
* 
*/
				
//***************************************** Paso 1 y 2***************************************************************** 
		
		addStep("Ejecutar la siguiente consulta en la BD \"OXTPEQA\" para validar la cantidad de registros que se van a respaldar:");
		System.out.println("Paso 1 y 2: "+GlobalVariables.DB_HOST_OXTPEQA); //Debe ser OXTPEQA_PREM
		System.out.println(consultaCantidadReg);
		
		SQLResult exe_consultaCantidadReg = executeQuery(dbOXTPEQA, consultaCantidadReg); //Debe ser OXTPEQA_PREM
		String totalFuente = exe_consultaCantidadReg.getData(0, "COUNT(*)");
				
		boolean validaConsultaCantidadReg = exe_consultaCantidadReg.isEmpty();
		
		if (!validaConsultaCantidadReg) {
			testCase.addQueryEvidenceCurrentStep(exe_consultaCantidadReg);
		} 
		
		System.out.println(validaConsultaCantidadReg);
		assertFalse(validaConsultaCantidadReg, "No existe informaci�n pendiente de procesar en la tabla.");
		
		
//***************************************** Paso 3 ***************************************************************** 
		addStep("Ejecutar la siguiente consulta en la BD \"OXTPEQA\" para ver el detalle de los registros que se van a respaldar");
		System.out.println("Paso 3 "+GlobalVariables.DB_HOST_OXTPEQA); //Debe ser OXTPEQA_PREM
		System.out.println(consultaDetalle);
		
		SQLResult exe_consultaDetalle = executeQuery(dbOXTPEQA, consultaDetalle); //Debe ser OXTPEQA_PREM
		
		boolean validaConsultaDetalle = exe_consultaDetalle.isEmpty();
		
		if (!validaConsultaDetalle) {
			testCase.addQueryEvidenceCurrentStep(exe_consultaDetalle);
		} 
		
		System.out.println(validaConsultaDetalle);
		assertFalse(validaConsultaDetalle, "No existe informaci�n pendiente de procesar en la tabla.");
	
//***************************************** Paso 4 y 5 ***************************************************************** 
		addStep("Ejecutar la siguiente consulta en la BD \"FCWMREQA\" para ver la configuraci�n para la copia de la tabla:");
		System.out.println("Paso 4 y 5 "+GlobalVariables.DB_HOST_FCWMREQA);
		System.out.println(consultaConfig);
		
		SQLResult exe_consultaConfig = executeQuery(dbFCWMREQA, consultaConfig); 
		
		boolean validaConsultaConfig = exe_consultaConfig.isEmpty();
		
		if (!validaConsultaConfig) {
			testCase.addQueryEvidenceCurrentStep(exe_consultaConfig);
		} 
		
		System.out.println(validaConsultaConfig);
		assertFalse(validaConsultaConfig, "No existe informaci�n pendiente de procesar en la tabla.");
		
//***************************************** Paso 6 ***************************************************************** 
		addStep("Ejecutar la siguiente consulta en la BD \"FCWMREQA\" para consultar el total "
				+ "de registros existentes que se encuentren dentro del rango de respaldo de 5 a�os:");
		System.out.println("Paso 6 "+GlobalVariables.DB_HOST_FCWMREQA);
		System.out.println(consultaRegRespaldo);
		
		SQLResult exe_consultaRegRespaldo = executeQuery(dbFCWMREQA, consultaRegRespaldo); 
		
		boolean validaConsultaRegRespaldo = exe_consultaRegRespaldo.isEmpty();
		
		if (!validaConsultaRegRespaldo) {
			testCase.addQueryEvidenceCurrentStep(exe_consultaRegRespaldo);
		} 
		
		System.out.println(validaConsultaRegRespaldo);
		assertFalse(validaConsultaRegRespaldo, "No existe informaci�n pendiente de procesar en la tabla.");
		
//***************************************** Paso 7 ***************************************************************** 
		addStep("Ejecutar la siguiente consulta en la BD \"FCWMREQA\" para ver el detalle de los registros "
				+ "con los que cuenta el repositorio hist�rico dentro del rango de respaldo de 5 a�os:");
		System.out.println("Paso 7 "+GlobalVariables.DB_HOST_FCWMREQA);
		System.out.println(consultaRegRespaldo2);
		
		SQLResult exe_consultaRegRespaldo2 = executeQuery(dbFCWMREQA, consultaRegRespaldo2); 
		
		boolean validaConsultaRegRespaldo2 = exe_consultaRegRespaldo2.isEmpty();
		
		if (!validaConsultaRegRespaldo2) {
			testCase.addQueryEvidenceCurrentStep(exe_consultaRegRespaldo2);
		} 
		
		System.out.println(validaConsultaRegRespaldo2);
		assertFalse(validaConsultaRegRespaldo2, "No existe informaci�n pendiente de procesar en la tabla.");

//***************************************** Paso 8 ***************************************************************** 
		/**
		 * "NO APLICA
		 *  Las validaciones para el flujo de Depuraci�n quedan fuera del alcance de las pruebas de QA 
		 *  debido a que actualmente en Producci�n existe un error con este proceso, y para estas 
		 *  pruebas de QA se anul� el c�digo de la depuraci�n."
		 */
//***************************************** Paso 9 ***************************************************************** 		
		/**
		 * "NO APLICA
		 *  Las validaciones para el flujo de Depuraci�n quedan fuera del alcance de las pruebas de QA 
		 *  debido a que actualmente en Producci�n existe un error con este proceso, y para estas 
		 *  pruebas de QA se anul� el c�digo de la depuraci�n."
		 */
		
//***************************************** Paso 10 *****************************************************************		
		addStep("Ejecuci�n control-M");

		fechaEjecucionInicio = new Date();

		// Se obtiene la cadena de texto del data provider en la columna "jobs"
		// Se asigna a un array para poder manejarlo
		JSONArray array = new JSONArray(data.get("cm_jobs"));

		testCase.addTextEvidenceCurrentStep("Ejecuci�n Job: " + data.get("cm_jobs"));
		SeleniumUtil u = new SeleniumUtil(new ChromeTest());
		Control_mInicio cm = new Control_mInicio(u, data.get("cm_user"), data.get("cm_ps"));

		testCase.addTextEvidenceCurrentStep("Login");
		addStep("Login");
		u.get(data.get("cm_server"));
		u.hardWait(40);
		u.waitForLoadPage();
		cm.logOn();

		testCase.addTextEvidenceCurrentStep("Inicio de job");
		JobManagement j = new JobManagement(u, testCase, array);
		u.hardWait(5);
		System.out.println(data.get("Espera 5 sec, antes de Resultado Ejecucion"));
		String resultadoEjecucion = j.jobRunner();

		// Abrir la herramienta de control M para validar que la ejecuci�n del job haya
		// sido exitosa
		testCase.addTextEvidenceCurrentStep("Resultado de la ejecucion: " + resultadoEjecucion);
		System.out.println("Resultado de la ejecucion -> " + resultadoEjecucion);

		//assertEquals(resultadoEjecucion, "Ended OK");
		u.close();
		
//***************************************** Paso 13 *****************************************************************	
		
		addStep("Ejecutar la siguiente consulta en la BD \"FCWMREQA\" para validar la cantidad de los nuevos registros copiados :");
		System.out.println("Paso 13 "+GlobalVariables.DB_HOST_FCWMREQA);
		System.out.println(consultaNvosReg);
		
		SQLResult exe_consultaNvosReg = executeQuery(dbFCWMREQA, consultaNvosReg); 
		String totalDestino = "";
		
		boolean validaConsultaNvosReg = exe_consultaNvosReg.isEmpty();
		
		if (!validaConsultaNvosReg) {
			totalDestino = exe_consultaNvosReg.getData(0, "COUNT(*)");
			testCase.addQueryEvidenceCurrentStep(exe_consultaNvosReg);
		} 
		
		System.out.println(validaConsultaNvosReg);
		assertFalse(validaConsultaNvosReg, "No existe informaci�n pendiente de procesar en la tabla.");
//***************************************** Paso 14 *****************************************************************
		addStep("Ejecutar la siguiente consulta para obtener el detalle de los registros existentes "
				+ "(Y que se encuentren dentro del rango de respaldo de 5 a�os) en la "
				+ "tabla TPEREP.TPE_FR_TRANSACTION_LOY de la BD FCWMREQA:");
		System.out.println("Paso 15 "+GlobalVariables.DB_HOST_FCWMREQA);
		System.out.println(consultaTotalReg);
		
		SQLResult exe_consultaTotalReg = executeQuery(dbFCWMREQA, consultaTotalReg); 
		
		boolean validaConsultaTotalReg = exe_consultaTotalReg.isEmpty();
		
		if (!validaConsultaTotalReg) {
			testCase.addQueryEvidenceCurrentStep(exe_consultaTotalReg);
		} 
		
		System.out.println(validaConsultaTotalReg);
		assertFalse(validaConsultaTotalReg, "No existe informaci�n pendiente de procesar en la tabla.");
	
//***************************************** Paso 15 *****************************************************************
		addStep("Ejecutar la siguiente consulta para obtener el detalle de los registros existentes"
				+ " (Y que se encuentren dentro del rango de respaldo de 5 a�os) en la tabla "
				+ "TPEREP.TPE_FR_TRANSACTION_LOY de la BD FCWMREQA:");
		System.out.println("Paso 15 "+GlobalVariables.DB_HOST_FCWMREQA); 
		System.out.println(consultaDetalle2);
		
		SQLResult exe_consultaDetalle2 = executeQuery(dbFCWMREQA, consultaDetalle2); 
		
		boolean validaConsultaDetalle2 = exe_consultaDetalle2.isEmpty();
		
		if (!validaConsultaDetalle2) {
			testCase.addQueryEvidenceCurrentStep(exe_consultaDetalle2);
		} 
		
		System.out.println(validaConsultaDetalle2);
		assertFalse(validaConsultaDetalle2, "No existe informaci�n pendiente de procesar en la tabla.");
	
//***************************************** Paso 16 *****************************************************************
		addStep("Ejecutar la siguiente consulta en la BD \"FCWMREQA\" para validar la bit�cora del respaldo de la informaci�n:");
		System.out.println("Paso 16 "+GlobalVariables.DB_HOST_FCWMREQA); 
		System.out.println(consultaRespInfo);
		
		SQLResult exe_consultaRespInfo = executeQuery(dbFCWMREQA, consultaRespInfo); 
		String statusS = exe_consultaRespInfo.getData(0, "STATUS");
		
		boolean validaConsultaRespInfo = exe_consultaRespInfo.isEmpty();
		
		if (!validaConsultaRespInfo) {
			testCase.addQueryEvidenceCurrentStep(exe_consultaRespInfo);
		} 
		
		System.out.println(validaConsultaRespInfo);
		assertEquals(statusS, "S");
		
//***************************************** Paso 17 *****************************************************************
		addStep("Ejecutar la siguiente consulta en la BD \"FCWMREQA\" para validar la bit�cora del respaldo de la informaci�n:");
		System.out.println("Paso 17 "); 
		
		assertEquals(totalFuente,totalDestino);
		
//***************************************** Paso 18 *****************************************************************
		addStep("Ejecutar la siguiente consulta en la BD \"FCWMREQA\" para verificar que se haya actualizado la FEHCA_COPIADA:");
		System.out.println("Paso 18 "+GlobalVariables.DB_HOST_FCWMREQA); 
		System.out.println(actualizacion);
		
		SQLResult exe_consultaActualizacion = executeQuery(dbFCWMREQA, actualizacion); 
		
		boolean validaConsultaActualizacion = exe_consultaActualizacion.isEmpty();
		
		if (!validaConsultaActualizacion) {
			testCase.addQueryEvidenceCurrentStep(exe_consultaActualizacion);
		} 
		
		System.out.println(validaConsultaActualizacion);
		assertFalse(validaConsultaActualizacion, "No existe informaci�n pendiente de procesar en la tabla.");

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Prueba de regresi�n para comprobar la no afectaci�n en la funcionalidad de "
				+ "Primera ejecuci�n del job de respaldo de informaci�n de de premia de la "
				+ "tabla TPE_FR_TRANSACTION sin depuraci�n del proceso de TPE_COPIA ";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "QAautomation";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_023_TPE_COPIA_PrimeraEjecucionJobRespaldoInfoTPE_FR_TRANSACTION_sinDepuracion_test";
	}
	
	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

}
