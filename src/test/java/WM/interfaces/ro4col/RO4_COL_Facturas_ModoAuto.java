package WM.interfaces.ro4col;

import static org.junit.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.integrationServer.om.PakageManagment;
import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class RO4_COL_Facturas_ModoAuto extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_RO4_COL_001_Facturas_ModoAuto(HashMap<String, String> data) throws Exception {
		
		/*
		 * Utilerias
		 ***********************************************************************************************/

		utils.sql.SQLUtil dbRms = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS_COL_QAVIEW,GlobalVariables.DB_USER_RMS_COL_QAVIEW, GlobalVariables.DB_PASSWORD_RMS_COL_QAVIEW);
		utils.sql.SQLUtil dbPos = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMQA_NUEVA,GlobalVariables.DB_USER_FCWMQA_NUEVA, GlobalVariables.DB_PASSWORD_FCWMQA_NUEVA);
		utils.sql.SQLUtil dbEbs = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_EBS_COL, GlobalVariables.DB_USER_EBS_COL,GlobalVariables.DB_PASSWORD_EBS_COL);
		utils.sql.SQLUtil dbLog = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQA,GlobalVariables.DB_USER_FCWMLQA, GlobalVariables.DB_PASSWORD_FCWMLQA);

		/**
		 * Variables
		 * ***********************************************************************************************
		 * 
		 * 
		 */
		
		String validaInsumo = "SELECT SUPPLIER_ID, TERMS_DESC, LOC, REFERENCE_3, REFERENCE_8"
				+ " FROM fem_rechead_temp" + 
				" WHERE trunc(receive_date) >= trunc(sysdate - 170) " + 
				" AND reference_3 IS NULL " + 
				" AND reference_8 IS NULL " + 
				" AND reference_4 = 'DAILY'" + 
				" AND loc IN (SELECT store FROM STORE WHERE substr(store_name10, 1,5) = " + "'"+ data.get("plaza") + "'"+ ")" ;
		
		String ValidarInsumo2 ="select * from (SELECT SUPPLIER_ID, TERMS_DESC, LOC, REFERENCE_3, REFERENCE_8"
				+ " FROM fem_rechead_temp " + 
				" WHERE trunc(receive_date) >= trunc(SYSDATE - 170) " + 
				" AND reference_3 IS NOT NULL "
				+ "AND reference_8 IS NOT NULL "
				+ " AND reference_4 = 'DAILY' "
				+ " AND loc IN (SELECT store FROM STORE WHERE substr(store_name10, 1,5) = " + "'"+ data.get("plaza") + "'"+ ")) where ROWNUM <= 20";
		
		
		String wm_cfg_launcher ="SELECT INTERFACE_NAME,AUTO_STATUS,MANUAL_STATUS,ATTRIBUTE1,ATTRIBUTE2 FROM wmuser.wm_cfg_launcher " + 
				" WHERE interface_name = 'RO4_COL'" + 
				" AND auto_status = 'A' AND attribute1 = "+ "'"+ data.get("plaza") + "'" ;
		

		String tdcQueryIntegrationServer = "SELECT RUN_ID,INTERFACE,START_DT,STATUS,SERVER "
				+ "FROM wmlog.wm_log_run" + 
				" WHERE interface = 'RO04_COL'" + 
				" AND start_dt>=TRUNC(SYSDATE)"
				+ "AND ROWNUM = 1" + 
				" ORDER BY start_dt DESC";
		
		
		String tdcQueryErrorId = " SELECT ERROR_ID,RUN_ID,ERROR_DATE,DESCRIPTION " 
				+ " FROM WMLOG.WM_LOG_ERROR "
				+ " where RUN_ID=%s"; // FCWMLQA


		String tdcQueryStatusThread = "SELECT parent_id,thread_id,name,wm_log_thread.status,att1,att2 "
				+ " FROM WMLOG.wm_log_thread " + " WHERE parent_id = %s"; // FCWMLQA
			
		String invoicesInterface="SELECT INVOICE_ID, INVOICE_DATE, INVOICE_CURRENCY_CODE, DESCRIPTION "
				+ " FROM ap.ap_invoices_interface" + 
				" WHERE invoice_id = '%s'";

		String invoiceLines = "SELECT INVOICE_ID, INVOICE_LINE_ID, LINE_TYPE_LOOKUP_CODE, AMOUNT, TAX_CODE"
				+ " FROM ap.ap_invoice_lines_interface " + 
				" WHERE invoice_id = '%s'";
		
		 testCase.setProject_Name("Proyecto NEO");
	

		// utileria
		
		/**
		 * 
		 * **********************************Pasos del caso de Prueba
		 * *****************************************
		 * 
		 * 
		 */

//		Paso 1	************************		

		addStep("Verificar que existen datos pendientes por procesar en la tabla fem_rechead_temp de RETEK para la plaza " + data.get("plaza")  );

		System.out.println(validaInsumo);

		SQLResult registro = dbRms.executeQuery(validaInsumo);
	
		boolean existeRegistro = registro.isEmpty();
		if (!existeRegistro) {
			testCase.addQueryEvidenceCurrentStep(registro);
		}
		testCase.addQueryEvidenceCurrentStep(registro);
		

//		Paso 2	**************************************************************************************************************************************************

		addStep("Verificar que la plaza " + data.get("plaza")+ " esta configurada para su procesamiento en la tabla wm_cfg_launcher de WMINT."); 
		
		
		SQLResult cfg = dbPos.executeQuery(wm_cfg_launcher);
		System.out.println(wm_cfg_launcher);

		boolean existeRegistro2 = cfg.isEmpty();
		
		if (!existeRegistro2) {
			testCase.addQueryEvidenceCurrentStep(cfg);
		}
		testCase.addQueryEvidenceCurrentStep(cfg);
//		assertTrue(!existeRegistro2, "La plaza no esta configurada");

	
//		Paso 3	************************	
		
		addStep("Ejecutar el servicio RO4_COL.Pub:run. solicitando la ejecucion del job: runRO4_COL.");
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		PakageManagment pok = new PakageManagment(u, testCase);
		String status = "S";

		
		String user = data.get("user");
		String ps = PasswordUtil.decryptPassword(data.get("ps"));
		String server = data.get("server");
		String con = "http://" + user + ":" + ps + "@" + server;
		String searchedStatus = "R";
		String run_id;

		String contra = "http://" + user + ":" + ps + "@" + server + ":5555";
		u.get(contra);

		pok.runIntefaceWmWithInput10(data.get("interfase"), data.get("servicio"),"AUTO","type");

		SQLResult result5 = executeQuery(dbLog, tdcQueryIntegrationServer);
		
		
		String status1 = result5.getData(0, "STATUS");
		run_id = result5.getData(0, "RUN_ID");

		boolean valuesStatus = status1.equals(searchedStatus);// Valida si se encuentra en estatus R
		while (valuesStatus) {
			result5 = executeQuery(dbLog, tdcQueryIntegrationServer);
			status1 = result5.getData(0, "STATUS");
			run_id = result5.getData(0, "RUN_ID");
			

			u.hardWait(2);

		}
		boolean successRun = status1.equals(status);// Valida si se encuentra en estatus S
		if (!successRun) {

			String error = String.format(tdcQueryErrorId, run_id);
			SQLResult result3 = executeQuery(dbLog, error);

			boolean emptyError = result3.isEmpty();
			

			if (!emptyError) {

				testCase.addTextEvidenceCurrentStep("Se encontra un error en la ejecución de la interfaz en la tabla WM_LOG_ERROR");

				testCase.addQueryEvidenceCurrentStep(result3);

			}
		}
				u.close();
		// Paso 4 ************************
		addStep("Verificar que la interfaz se ejecuto correctamente, en la tabla wm_log_run deberá existir un registro con el campo status en \"S\".");
		
		
			System.out.println(tdcQueryIntegrationServer);

			
			if (successRun) {

				testCase.addQueryEvidenceCurrentStep(result5);
				
			} 

			System.out.println("El status es S : "+successRun);

			testCase.addQueryEvidenceCurrentStep(result5);
			

//		Paso 5  *************************************************
		addStep("Validar que se inserte el detalle de la ejecucion de los Threads lanzados por la interface en la tabla WM_LOG_THREAD con STATUS = 'S'");
		
		System.out.println(GlobalVariables.DB_HOST_FCWMLQA);
		String consultaTemp6 = String.format(tdcQueryStatusThread, run_id);
		SQLResult paso5 = executeQuery(dbLog, consultaTemp6);
		
		System.out.println(consultaTemp6);
		String estatusThread = "";

		boolean SR = estatusThread.equals(status);
		SR = !SR;
		
		if (!SR) {

			testCase.addQueryEvidenceCurrentStep(paso5);
			estatusThread = paso5.getData(0, "Status");
			System.out.println("estatusThread: "+estatusThread);
		} 

		System.out.println(SR);

		testCase.addQueryEvidenceCurrentStep(paso5);

		
		// ******************* Paso 6 ************************
		addStep("Verificar que los datos de la tabla fem_rechead_temp de RETEK fueron procesados correctamente.");


		 registro = dbRms.executeQuery(ValidarInsumo2);
		 String ref3 = "";
		 existeRegistro = registro.isEmpty();
		if (!existeRegistro) {
			testCase.addQueryEvidenceCurrentStep(registro);
			 ref3 = registro.getData(0, "reference_3");
			 System.out.println("reference_3: "+ref3);
		}
		testCase.addQueryEvidenceCurrentStep(registro);
		

		// ************************ Paso 7 ************************
		addStep("Verificar que las Facturas fueron insertadas en las tablas ap_invoices_interface y ap_invoice_lines_interface de ORAFIN.");
		
		String consulta1 = String.format(invoicesInterface, ref3);
		SQLResult queryInvoices1 = dbEbs.executeQuery(consulta1);
		boolean av = queryInvoices1.isEmpty();
		if (!av) {

			testCase.addQueryEvidenceCurrentStep(queryInvoices1);
		}
		System.out.println(av);
		// .-----------Segunda consulta
		String consulta2 = String.format(invoiceLines, ref3);
		System.out.println(consulta2);
		SQLResult queryInvoices2 = dbEbs.executeQuery(consulta2);
		 av = queryInvoices2.isEmpty();
		if (!av) {

			testCase.addQueryEvidenceCurrentStep(queryInvoices2);
		}
		System.out.println(av);
		testCase.addQueryEvidenceCurrentStep(queryInvoices2);
		assertFalse("No se generaron threads en la tabla", av);


	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_RO4_COL_001_Facturas_ModoAuto";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Terminada. Ejecucion de la interfaz para el procesamiento de las Facturas en modo AUTO";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "QA Automation";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

}
