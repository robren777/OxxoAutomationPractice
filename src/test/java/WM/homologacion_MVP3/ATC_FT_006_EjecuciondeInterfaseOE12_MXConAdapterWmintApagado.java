package WM.homologacion_MVP3;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.adapters.AdaptersOneButton;
import WM.integrationServer.om.PakageManagementNewVersion;
import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_006_EjecuciondeInterfaseOE12_MXConAdapterWmintApagado extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_EjecuciondeInterfaseOE12_MXConAdapterWmintApagado_test(HashMap<String, String> data) throws Exception {
	
/** UTILERIA *********************************************************************/	
		
		utils.sql.SQLUtil dbLog = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQANew, GlobalVariables.DB_USER_FCWMLQANew,GlobalVariables.DB_PASSWORD_FCWMLQANew);
		utils.sql.SQLUtil dbAvebqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
		
		SeleniumUtil u;
		PakageManagementNewVersion pok;	
		AdaptersOneButton aob;
		
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
		
		String selectStatusE = "SELECT WM_STATUS, WM_MESSAGE, WM_SHPT_DATE, WM_RUN_ID FROM XXFC.XXFC_XP_CAT_CLAVES_ARTICULO WHERE wm_status='E' AND ROWNUM <= 10";
		
		String selectDate = "SELECT TRUNC(SYSDATE) FROM DUAL";
		
		String selectArticulo = "SELECT ID_ARTICULO, CLAVE_ARTICULO, WM_STATUS, WM_MESSAGE, WM_RUN_ID, WM_SHPT_DATE FROM XXFC.XXFC_XP_CAT_CLAVES_ARTICULO WHERE WM_STATUS !='E'";

		String tdcIntegrationServerFormat = "select * from (SELECT Tbl.run_id,interface, start_dt, end_dt, status, server "
				+ "FROM WMLOG.WM_LOG_RUN Tbl " + "WHERE INTERFACE LIKE '%OE12MX%' "
				+ "ORDER BY START_DT DESC) where rownum <=1";
		
		String consultaError1 = "select * from (Select ERROR_ID, RUN_ID, ERROR_DATE, SEVERITY,ERROR_TYPE "
				+ "from  wmlog.WM_LOG_ERROR " + "where RUN_ID='%s') where rownum <=1";

		String consultaError2 = "select * from (select description,MESSAGE "
				+ "from wmlog.WM_LOG_ERROR where RUN_ID='%s')WHERE rownum <= 1";

		String consultaError3 = "select * from (select PROC_ROLE, PROC_STATUS,CR_PLAZA, THREAD_ID,ATT1 "
				+ "from wmlog.WM_LOG_ERROR where RUN_ID='%s')WHERE rownum <= 1"; //FCWMLQA 
		
			
	
	
		/* PASO 1 *********************************************************************/
		
		addStep("Entrar a la tabla" + data.get("tabla"));		
		
		System.out.println(GlobalVariables.DB_HOST_AVEBQA);
		SQLResult paso1 = executeQuery(dbAvebqa, selectCampos);		
		System.out.println(selectCampos);
		
		boolean av = paso1.isEmpty();
		
		if (!av) {
			testCase.addQueryEvidenceCurrentStep(paso1);
		} 

		System.out.println(av);		
		assertFalse(av, "No se obtiene informacion de la consulta en la tabla " + data.get("tabla"));
		 
		
		/* PASO 2 *********************************************************************/
		
		addStep("Validar que se tenga informacion para procesar en la tabla " + data.get("tabla"));		
	
		System.out.println(GlobalVariables.DB_HOST_AVEBQA);

		SQLResult paso4 = executeQuery(dbAvebqa, selectArticulo);		
		
		boolean av2 = paso4.isEmpty();
		
		if (!av2) {
			testCase.addQueryEvidenceCurrentStep(paso4);

		} 

		System.out.println(av2);		
		assertFalse(av2, "No se obtiene informaci�n de la consulta en la tabla " + data.get("tabla"));
		  
		
		
		// PASO 3 *********************************************************************
			
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
		    
		
	// PASO 4 *********************************************************************
		
		addStep("Validar que la tabla XXFC_XP_CAT_CLAVES_ARTICULO wm_status='E'");		
		
		System.out.println(GlobalVariables.DB_HOST_AVEBQA);

		SQLResult paso9 = executeQuery(dbAvebqa, selectStatusE);		
		
		boolean av3 = paso9.isEmpty();
		
		if (!av3) {
			testCase.addQueryEvidenceCurrentStep(paso9);

		} 
		assertFalse(av3, "No se obtiene informacion de la consulta en la tabla " + data.get("tabla"));
		System.out.println(av3);		

		// PASO 5*********************************************************************		    

	    addStep("Se muestra el error relacionado  “No se pudo establecer conexión con la base de datos de Oracle EBS");		

		String error = String.format(consultaError1, run_id);
		String error1 = String.format(consultaError2, run_id);
		String error2 = String.format(consultaError3, run_id);

		SQLResult errorr = dbLog.executeQuery(error);
		boolean emptyError = errorr.isEmpty();
		
		if (!emptyError) {
			testCase.addTextEvidenceCurrentStep("Se encontro un error en la ejecucion de la interfaz en la tabla WM_LOG_ERROR");
			testCase.addQueryEvidenceCurrentStep(errorr);
			
			SQLResult errorIS = dbLog.executeQuery(error1);
			testCase.addQueryEvidenceCurrentStep(errorIS);
			
			SQLResult errorIS2 = dbLog.executeQuery(error2);
			testCase.addQueryEvidenceCurrentStep(errorIS2);
		}
		assertFalse(emptyError, "No se obtienen errores de la consulta en la tabla " + data.get("tabla"));

		
		
	}
	
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que al ejecutar la interface por Control-M termine en error y se registre el mensaje de error en wm_log_error referente al server Adapter WMINT";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Yair Lizarraga";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_006_EjecuciondeInterfaseOE12_MXConAdapterWmintApagado_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Contar con acceso  a  Control-M\r\n"
				+ "Contar con acceso  a  Integration Server\r\n"
				+ "Contar con acceso a WM_LOG";
	}
}
