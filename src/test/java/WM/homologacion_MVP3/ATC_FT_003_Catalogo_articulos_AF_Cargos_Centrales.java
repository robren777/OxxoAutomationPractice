package WM.homologacion_MVP3;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.integrationServer.om.PakageManagementNewVersion;
import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_003_Catalogo_articulos_AF_Cargos_Centrales extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_Catalogo_articulos_AF_Cargos_Centrales_test(HashMap<String, String> data) throws Exception {
	
/** UTILERIA *********************************************************************/	
		
		utils.sql.SQLUtil dbLog = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQANew, GlobalVariables.DB_USER_FCWMLQANew,GlobalVariables.DB_PASSWORD_FCWMLQANew);
		utils.sql.SQLUtil dbAvebqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
		utils.sql.SQLUtil dbFCIASQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCIASQAS, GlobalVariables.DB_USER_FCIASQAS, GlobalVariables.DB_PASSWORD_FCIASQAS);
		
		
		SeleniumUtil u;
		PakageManagementNewVersion pok;	
		
		testCase.setProject_Name("I20134 Homologacion Cuentas por Pagar");    
				
		/** VARIABLES *********************************************************************/	
		
		String status = data.get("status");  // status exitoso
		String wm_status = data.get("wmStatus"); // status exitoso
		String user = data.get("user");
		String ps = PasswordUtil.decryptPassword(data.get("ps"));
		String server = data.get("server");
		String searchedStatus = "R";
		String run_id = "";
		
		String selectCamposID2 = "SELECT WM_STATUS, WM_MESSAGE, TRUNC(WM_SHPT_DATE) AS WM_SHPT_DATE, WM_RUN_ID FROM XXFC.XXFC_XP_CAT_CLAVES_ARTICULO  WHERE WM_SHPT_DATE >= TRUNC(SYSDATE) AND WM_SHPT_DATE <TRUNC(SYSDATE)+1 order by WM_RUN_ID DESC";
		
		String selectCampos = "SELECT WM_STATUS, WM_MESSAGE, WM_SHPT_DATE, WM_RUN_ID FROM "+ data.get("tabla") + " where rownum <=1";//retek_cr
		
		String selectStatusE = "SELECT WM_STATUS, WM_MESSAGE, WM_SHPT_DATE, WM_RUN_ID FROM XXFC.XXFC_XP_CAT_CLAVES_ARTICULO WHERE wm_status='E'";
		
		String selectDate = "SELECT TRUNC(SYSDATE) FROM DUAL";
		
		String selectArticulo = "SELECT ID_ARTICULO, CLAVE_ARTICULO, DESCRIPCION, CUENTA, AUXILIAR, WM_STATUS, WM_RUN_ID, WM_SHPT_DATE FROM XXFC.XXFC_XP_CAT_CLAVES_ARTICULO where rownum <=10";

		String tdcIntegrationServerFormat = "select * from (SELECT Tbl.run_id,interface, start_dt, end_dt, status, server "
				+ "FROM WMLOG.WM_LOG_RUN Tbl " + "WHERE INTERFACE LIKE '%OE12MX%' "
				+ "ORDER BY START_DT DESC) where rownum <=1";
		
		String consultaError1 = "select * from (Select ERROR_ID, RUN_ID, ERROR_DATE, SEVERITY,ERROR_TYPE "
				+ "from  wmlog.WM_LOG_ERROR " + "where RUN_ID='%s') where rownum <=1";

		String consultaError2 = "select * from (select description,MESSAGE "
				+ "from wmlog.WM_LOG_ERROR where RUN_ID='%s')WHERE rownum <= 1";

		String consultaError3 = "select * from (select PROC_ROLE, PROC_STATUS,CR_PLAZA, THREAD_ID,ATT1 "
				+ "from wmlog.WM_LOG_ERROR where RUN_ID='%s')WHERE rownum <= 1"; //FCWMLQA 
		
			
	
		
/** PASOS DEL CASO DE PRUEBA *********************************************************************/	
		
		/* PASO 1, 2 y 3 *********************************************************************/
		
		addStep("Validar que la tabla cuente con los siguientes campos para WM en la tabla " + data.get("tabla"));		
		
		System.out.println(GlobalVariables.DB_HOST_AVEBQA);
		SQLResult paso1 = executeQuery(dbAvebqa, selectCampos);		
		System.out.println(selectCampos);
		
		boolean av = paso1.isEmpty();
		
		if (!av) {
			testCase.addQueryEvidenceCurrentStep(paso1);
		} 

		System.out.println(av);		
		assertFalse(av, "No se obtiene informacion de la consulta en la tabla " + data.get("tabla"));
		 
		
		/* PASO 4 *********************************************************************/
		
		addStep("Validar que se tenga informacion para procesar en la tabla " + data.get("tabla"));		
	
		System.out.println(GlobalVariables.DB_HOST_AVEBQA);

		SQLResult paso4 = executeQuery(dbAvebqa, selectArticulo);		
		
		boolean av2 = paso4.isEmpty();
		
		if (!av2) {
			testCase.addQueryEvidenceCurrentStep(paso4);

		} 

		System.out.println(av2);		
		assertFalse(av2, "No se obtiene informacion de la consulta en la tabla " + data.get("tabla"));
		  
		
		
		// PASO 5 y 6 *********************************************************************
		addStep("Ejecutar el servicio " + data.get("servicio"));
		
		u = new SeleniumUtil(new ChromeTest(), true);
		pok = new PakageManagementNewVersion(u, testCase);
		
		System.out.println(GlobalVariables.DB_HOST_FCWMLQA);
		String contra = "http://" + server + ":5555";
		u.get(contra);
		pok.inicarSesion(user, ps);
		String dateExecution = pok.runIntefaceWM(data.get("interfase"), data.get("servicio"), null);
		System.out.println("Respuesta dateExecution " + dateExecution);
		
		System.out.println(tdcIntegrationServerFormat);
		SQLResult is = dbLog.executeQuery(tdcIntegrationServerFormat);
		run_id = is.getData(0, "RUN_ID");
		String status1 = is.getData(0, "STATUS");
		System.out.println("RUN_ID = " + run_id + "\t Status: " + status1 );

		boolean valuesStatus = status1.equals(searchedStatus);// Valida si se encuentra en estatus R

		while (valuesStatus) {			
			status1 = is.getData(0, "STATUS");
			run_id = is.getData(0, "RUN_ID");
			valuesStatus = status1.equals(searchedStatus);
			u.hardWait(2);				
		}
	  
		/* PASO 7 *********************************************************************/		    
	    
	    addStep("Verificar el estatus con el cual fue terminada la ejecucion de la interface en la tabla WM_LOG_RUN del usuario WMLOG.");		
		
		boolean validateStatus = status.equals(status1);
		System.out.println("VALIDACION DE STATUS = S - " + validateStatus);
	
		testCase.addQueryEvidenceCurrentStep(is);					
		assertFalse(!validateStatus, "La ejecuci�n de la interfaz no fue exitosa");
		
		
		// PASO 8 *********************************************************************		    

	    addStep("Validar en la tabla  WMLOG.WM_LOG_ERROR  no se genere errores");		

		String error = String.format(consultaError1, run_id);
		String error1 = String.format(consultaError2, run_id);
		String error2 = String.format(consultaError3, run_id);

		SQLResult errorr = dbLog.executeQuery(error);
		boolean emptyError = errorr.isEmpty();
		
		if (!emptyError) {
			testCase.addTextEvidenceCurrentStep("Se encontro un error en la ejecuci�n de la interfaz en la tabla WM_LOG_ERROR");
			testCase.addQueryEvidenceCurrentStep(errorr);
			
			SQLResult errorIS = dbLog.executeQuery(error1);
			testCase.addQueryEvidenceCurrentStep(errorIS);
			
			SQLResult errorIS2 = dbLog.executeQuery(error2);
			testCase.addQueryEvidenceCurrentStep(errorIS2);
		}
		testCase.addQueryEvidenceCurrentStep(errorr);

		assertFalse(!emptyError, "Se obtiene informacion de la consulta en la tabla WM_LOG_ERROR");
		

		// PASO 9 *********************************************************************
		
		addStep("Validar que la tabla XXFC_XP_CAT_CLAVES_ARTICULO wm_status='E'");		
		
		System.out.println(GlobalVariables.DB_HOST_AVEBQA);

		SQLResult paso9 = executeQuery(dbAvebqa, selectStatusE);		
		
		boolean av3 = paso9.isEmpty();
		
		if (!av3) {
			testCase.addQueryEvidenceCurrentStep(paso9);

		} 
		testCase.addQueryEvidenceCurrentStep(paso9);
		System.out.println(av3);		
		assertFalse(av3, "No se obtiene informacion de la consulta en la tabla" + data.get("tabla"));
		
		
		// PASO 10, 11, 12 y *********************************************************************
		
		addStep("Validar que la tabla cuente con los siguientes campos para WM en la tabla " + data.get("tabla"));		
		
		System.out.println(GlobalVariables.DB_HOST_AVEBQA);
		String selectFormat10 = String.format(selectCamposID2);
		System.out.println(selectFormat10);
		SQLResult paso10 = executeQuery(dbAvebqa, selectFormat10);		
		
		boolean av10 = paso10.isEmpty();
		
		if (!av10) {
		
			addStep("Validar  campos  WM_MESSAGE");	
			String wmMensaje = paso10.getData(0, "WM_MESSAGE");				
			boolean wm2;
			if (wmMensaje == null) {
				wm2 = true;
			}else {
				wm2 = false;
				System.out.println("WM Mensaje: " + wmMensaje);
			assertFalse(!wm2, "La respuesta no tiene el Mensaje esperado.");	
			}
			
			addStep("Validar  campos  WM_FECHA_ENVIO");	
			SQLResult queryDate = executeQuery(dbAvebqa, selectDate);
			String date = queryDate.getData(0, "TRUNC(SYSDATE)");				
			String wmfecha = paso10.getData(0, "WM_SHPT_DATE");			
			boolean wmDate = wmfecha.equals(date);
			System.out.println("WM fecha envio: " + wmfecha);
			assertFalse(!wmDate, "La respuesta no tiene el WM_FECHA_ENVIO esperado.");	
			
			addStep("Validar  campos  WM_RUN_ID");	
			String wmRun = paso10.getData(0, "WM_RUN_ID");			
			boolean wmID = wmRun.equals(run_id);
			System.out.println("WM RUN ID: " + wmRun);
			assertFalse(!wmID, "No se obtiene el id " + data.get("tabla"));
			
		} 
		testCase.addQueryEvidenceCurrentStep(paso10);
		assertFalse(av10, "No se obtiene informacion de la consulta en la tabla " + data.get("tabla"));
		
	}
	
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar el envió del Catálogo de artículos (Activos Fijos) de Cargos Centrales a Expertal\r\n";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Yair Lizarraga";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_003_Catalogo_articulos_AF_Cargos_Centrales_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Contar con información de del catalogo cargos centrales\r\n"
				+ "en las tablas de paso de EBS\r\n"
				+ "Contar con acceso  a  Control-M\r\n"
				+ "Contar con acceso a tabla staging de Oracle\r\n"
				+ "Contar con acceso a WM_LOG";
	}
}
