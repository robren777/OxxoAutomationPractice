package WM.interfaces.pb1;

import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;


public class ATC_FT_004_NEO_PB1_WMx86Validar_ProcesaArchivosTIC extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_NEO_PB1_WMx86Validar_ProcesaArchivosTIC_test(HashMap<String, String> data) throws Exception { 
		
		
		/*Back Office Mexico: MTC-FT-024 PB1 Procesar al menos 10 archivos TIC de tickets de tienda a trav�s de la interface FEMSA_PB1
 * Desc:
 * Prueba de regresi�n  para comprobar la no afectaci�n en la funcionalidad principal de la interface FEMSA_PB1 para procesar al menos 
 * 10 archivos TIC (Tickets de Tienda) de subida (de WM INBOUND a BI/IMA), al ser migrada la interface de WM9.9 a WM10.5.

   Esta interface permite consultar y procesar  la informaci�n de los tickets de las tiendas almacenados en el sistema del POS, y
    enviarla al sistema de BI.
 * @author Marisol Rodriguez
 * @date   2022/07/16*/
		
		
/** UTILERIA *********************************************************************/	
		
		utils.sql.SQLUtil dbLog = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQA_WMLOG,GlobalVariables.DB_USER_FCWMLQA_WMLOG, GlobalVariables.DB_PASSWORD_FCWMLQA_WMLOG );
		utils.sql.SQLUtil dbPos = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMQA_NUEVA, GlobalVariables.DB_USER_FCWMQA_NUEVA, GlobalVariables.DB_PASSWORD_FCWMQA_NUEVA);
		utils.sql.SQLUtil dbIas = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_IAS, GlobalVariables.DB_USER_IAS, GlobalVariables.DB_PASSWORD_IAS);
		utils.sql.SQLUtil dbBi = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BiTicket, GlobalVariables.DB_USER_BiTicket,GlobalVariables.DB_PASSWORD_BiTicket);
				
/** VARIABLES *********************************************************************/	
		//Paso 2
		String selectPlazas = "SELECT id, cr_plaza, cr_plaza_desc, fch_arranque, ventana_dias, dias_tolerancia\r\n"
				+ "FROM POSUSER.PLAZAS \r\n"
				+ "WHERE FCH_ARRANQUE <= SYSDATE \r\n"
				+ "AND FCH_ARRANQUE IS NOT NULL \r\n"
				+ "AND CR_PLAZA =  '" + data.get("plaza") + "'"; //POSUSER
		//Paso 3
		String selectI = "SELECT ID, PE_ID, status, doc_type, pv_doc_name, received_date \r\n"
				+ "FROM POSUSER.POS_INBOUND_DOCS \r\n"
				+ "WHERE  RECEIVED_DATE BETWEEN TRUNC(SYSDATE-30) AND SYSDATE\r\n"
				+ "AND STATUS = 'I' \r\n"
				+ "AND SUBSTR(PV_DOC_NAME,4,5) = '" + data.get("plaza") + "'\r\n"
				+ "AND DOC_TYPE = 'TIC'";
		
		//Paso 4
		String consultaPOS_TIC = "SELECT * FROM  POSUSER.POS_TIC where PID_ID = '%s'";
		
		//Paso 5
		String  consultaPOS_TIC_DETL = "SELECT pid_id, item, pv_cve_mvt,pv_fch_mvt\r\n"
				+ "FROM  POSUSER.POS_TIC_DETL\r\n"
				+ "where pid_id ='%s'";

		//Consulta de ejecucion
		String tdcIntegrationServerFormat = "select run_id,interface, start_dt, end_dt, status, server \r\n"
				+ "from WMLOG.WM_LOG_RUN  \r\n"
				+ "WHERE INTERFACE = 'PB1main'\r\n"
				+ "AND  start_dt >= TRUNC(SYSDATE) \r\n"
				+ "AND rownum =1 \r\n"
				+ "ORDER BY START_DT DESC";// Consulta para estatus de la ejecucion
		

		// consultas de error
		String consultaError1 = " select ERROR_ID, RUN_ID, ERROR_DATE, SEVERITY,ERROR_TYPE \r\n"+
					 "from wmlog.WM_LOG_ERROR \r\n" + 
					 "where RUN_ID='%s' \r\n" +
					 "and rownum <=1"; // dbLog
				
		String consultaError2 = " select description,MESSAGE \r\n" +
					 "from wmlog.WM_LOG_ERROR \r\n" +
					 "where RUN_ID='%s' \r\n" +
					 "and rownum <= 1"; // dbLog
				
				  
				//Consultas de threrads
		String consultaTHREAD = "SELECT THREAD_ID, PARENT_ID, NAME,START_DT, END_DT, STATUS  \r\n"
						+ "FROM wmlog.WM_LOG_THREAD \r\n" + 
						" where PARENT_ID= '%s'";


		String consultaTHREAD2 = "SELECT ATT1, ATT2,ATT3,ATT4,ATT5 FROM wmlog.WM_LOG_THREAD \r\n"
						+ "where PARENT_ID='%s'";
						
		//Paso 8
		String selectE = "SELECT id,pe_id,pv_doc_name,doc_type,status,partition_date \r\n"
				+ "FROM POSUSER.POS_INBOUND_DOCS \r\n" 
				+ "WHERE ID = '%s' \r\n"
				+ "AND DOC_TYPE = 'TIC' \r\n"
				+ "AND STATUS = 'E' \r\n" 
				+ "AND SUBSTR(PV_DOC_NAME,4,5) = '" + data.get("plaza") + "' \r\n" 
				+ "and SUBSTR(PV_DOC_NAME,9,5) = '" + data.get("tienda") + "'";
		
	
		
		String noDatosBI = "SELECT VT_NUM_CAJA, VT_CVE_ART, VT_CVE_MOV \r\n"
				+ "FROM DWADMIN.TEMP_TICKET \r\n"
				+ "WHERE TRUNC(VT_FCH_MOV) = TRUNC(SYSDATE)\r\n"
				+ "AND VT_CR_PLAZ = '" + data.get("plaza") + "'";


		
		 testCase.setProject_Name("POC WMX86");
			
		 testCase.setPrerequisites(data.get("prerequicitos"));



/** PASOS DEL CASO DE PRUEBA *********************************************************************/	
		 
				 			
		
		
//****************************************** Paso 4 *********************************************************************

		 addStep("Se valida que No existan datos en la tabla TEMP_TICKET de la BD BI");

			System.out.println(noDatosBI);
			SQLResult noDatosBI_result = dbBi.executeQuery(noDatosBI);		
			//String VT_NUM_CAJA = noDatosBI_result.getData(0, "VT_NUM_CAJA");

			boolean noDatosBI_valida = noDatosBI_result.isEmpty(); // checa que el string contenga datos

			if (noDatosBI_valida) {
				
				testCase.addQueryEvidenceCurrentStep(noDatosBI_result); // Si no esta vacio, lo agrega a la evidencia
			}

			System.out.println(noDatosBI_valida);
			assertTrue(noDatosBI_valida, "Se encontraron registros en BI"); // Si esta vacio, imprime mensaje
							
		
//**********************************************Paso 6 y 7 ********************************************************************	

		addStep("Validar la ejecuci�n del Job en Control M, donde debe mostrarse en color verde, que indica que la ejecuci�n se realiz� de forma exitosa para el Job PB1.");

		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		u.get(data.get("server"));
	
		
		String status = "S";
		
		
		JSONObject obj = new JSONObject(data.get("job"));

		testCase.addTextEvidenceCurrentStep("Job en  Control M ");
		Control_mInicio CM = new Control_mInicio(u, data.get("user"), data.get("ps"));
		
		testCase.addTextEvidenceCurrentStep("Login");
		
		u.hardWait(40);
		u.waitForLoadPage();
		CM.logOn(); 

		//Buscar del job
		testCase.addTextEvidenceCurrentStep("Inicio de job ");
		ControlM control = new ControlM(u, testCase, obj);
		boolean flag = control.searchJob();
		assertFalse(!flag);
		
		//Ejecucion
		String resultado = control.executeJob();
		testCase.addTextEvidenceCurrentStep( resultado);
		System.out.println("Resultado de la ejecucion -> " + resultado);

		//Valor del output 
		testCase.addTextEvidenceCurrentStep("Output: " + control.getOutput());
		System.out.println ("Valor de output :" +control.getOutput());
		
		//Validacion del caso
		Boolean casoPasado = true;
		
		if(resultado.equals("Failure")) {
			
		casoPasado = false;
		
		System.out.println("La ejecucion del Job fue exitosa");
		
		}		
		assertTrue(casoPasado, "La ejecucion del job fallo");

		control.closeViewpoint(); 
		
		u.close();
		
			
//************************************************* Paso 9 *****************************************************************
		
		 addStep("Realizar conexi�n a la BD FCTICQA.FEMCOM.NET de BI.");
		 
		 testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_BiTicket);
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_USER_BiTicket);

//************************************************Paso 10 *******************************************************************	 
		 
			addStep("Validar que los datos se hayan insertado en la tabla TEMP_TICKET de la BD FCTICQA.");
	
			System.out.println(noDatosBI);
			
			SQLResult selectTempTicket_Result = dbBi.executeQuery(noDatosBI);	
			
			boolean selectTempTicket_valida = selectTempTicket_Result.isEmpty(); // checa que el string contenga datos
	
			if (!selectTempTicket_valida) {
				
				testCase.addQueryEvidenceCurrentStep(selectTempTicket_Result); // Si no esta vacio, lo agrega a la evidencia
		     }
	
			System.out.println(selectTempTicket_valida);
			
			assertFalse("No se encontraron registros en  la tabla TEMP_TICKET", selectTempTicket_valida); // Si esta vacio, imprime mensaje	
			


	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "ATC-FT-024 PB1 Procesar al menos 10 archivos TIC de tickets de tienda a trav�s de la interface FEMSA_PB1";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Sergio Robles Ramos";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_002_PB1_WMx86Validar_ProcesaArchivosTIC_test";
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