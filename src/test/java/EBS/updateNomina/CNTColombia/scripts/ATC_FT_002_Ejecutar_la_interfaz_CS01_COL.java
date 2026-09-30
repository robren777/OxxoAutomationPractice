package EBS.updateNomina.CNTColombia.scripts;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.updateNomina.om.UpdateNomina;
import EBS.util.appletsMethods.MetodosSikuli;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_002_Ejecutar_la_interfaz_CS01_COL extends BaseExecution {
	
	 @Test(dataProvider = "data-provider")
	    public void ATC_FT_002_Ejecutar_la_interfaz_CS01_COL_test(HashMap<String, String> data) throws Exception{
		 
		 //Start.generateEncryptPassword();
		 //VARIABLES
		 String ebsUrl = data.get("server");
		 String userName = data.get("userName");
		 String password = PasswordUtil.decryptPassword(data.get("password"));
					 
		 String serverFCWMLQA = data.get("serverFCWMLQA");
		 String usernameFCWMLQA = data.get("usernameFCWMLQA");
		 String passwordFCWMLQA = data.get("passwordFCWMLQA");
	
		 String controlMUser = data.get("ctrlUser");
		 String controlMPwd = data.get("ctrlpass");
		 String controlMUrl = data.get("UrlctrlM");
		 
		
		 
		/* JSONObject obj = null;
		 try {
			 obj = new JSONObject(data.get("job"));
		 } catch (JSONException e) {
			 System.err.println("JSONException: 'job' key not found in data: " + data);
			 throw e;
		 }
		 
		 JSONObject obj2 = null;
		 try {
			 obj2 = new JSONObject(data.get("job2"));
		 } catch (JSONException e) {
			 System.err.println("JSONException: 'job' key not found in data: " + data);
			 throw e;
		 }*/
		 	
			
			
		 //UTILERIAS
		 SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);
		 LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		 Control_mInicio CM = new Control_mInicio(u, controlMUser, controlMPwd);
		// ControlM controlM = new ControlM(u, testCase, obj);
		 //ControlM controlM2 = new ControlM(u, testCase, obj2);
		 //LoginTool inicio1 = new LoginTool(u, testCase, colurl, coluserName, colpassword);
		// Login login = new Login(u, testCase);
		 //EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		 //EBSExtraccion extraccion = new EBSExtraccion(u, testCase);
		 SikuliUtil sk = new SikuliUtil();
		 Pattern pathimg = new Pattern();
		 String pathImg = sk.PatternPath("EBS\\"+"EBS");
		 ArrayList<String> ListImage = new ArrayList<String>();
		 HashMap<String, Pattern> ImgPattern = new HashMap<String, Pattern>();
		 ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		 File Folder = new File(pathImg);
		 	
		 MetodosSikuli ms = new MetodosSikuli(sk,u,pathimg,ListImage,ImgPattern,patterns,Folder);
		 UpdateNomina un = new UpdateNomina(sk,u, testCase, pathimg,ListImage,ImgPattern,patterns,Folder);
		 
		 /*utils.sql.SQLUtil BDCNTCOL = new utils.sql.SQLUtil(GlobalVariables.USER_CNTCOL, GlobalVariables.PASWWORD_CNTCOL, GlobalVariables.URL_CNTCOL);
		 utils.sql.SQLUtil BDWMLOG2 = new utils.sql.SQLUtil(serverFCWMLQA, usernameFCWMLQA, passwordFCWMLQA);*/
		
			
			
		 // PASO 1
		 addStep("Validar que en CNT la tienda ya esté en estatus Operativa");
		
		 				
		 				
		  // PASO 2
		 addStep("Ingresar a controlM con usuario y contraseña valido: http://10.184.56.194:18080/ControlM/");
		 /*u.get(GlobalVariables.CONTROL_M_SERVER);
		 u.waitForLoadPage();
		 CM.logOn();
		 testCase.addScreenShotCurrentStep(u, "control m inicio");*/
		 				
		 // PASO 3
		 addStep("Pasar información a NOMCOM Grupo: TQACO_OXXO_COL Job: CntaSAP_COL");
		 
		 /*boolean flag = controlM.searchJob();
		 assertFalse(!flag, "No se encontro el job");
		 System.out.println("Job Status " + controlM.getCurrentStatus());
		 //Ejecucion
		 String resultadoDelJob = controlM.executeJob();
		 testCase.addTextEvidenceCurrentStep("Resultado de la ejecuci�n " + resultadoDelJob);
		 System.out.println("Resultado de la ejecuci�n " + resultadoDelJob);

		 //Valor del output
		 testCase.addTextEvidenceCurrentStep("Output " + controlM.getOutput());
		 System.out.println("Output " + controlM.getOutput());

		 //Validacion del caso
		 boolean testResult = true;
		 if (resultadoDelJob.equals("Failure")) {			 testResult = false;
			 testCase.addTextEvidenceCurrentStep("Fallo la ejecuci�n del job ");
			 System.out.println("Fallo la ejecuci�n del job ");
		
		 }		*/		
		 				
		 // PASO 4
		 addStep(" Validar envio del ceco a la tabla temporal select*from tiendas.XX_NOMCOM_CNT_SAP where ceco in  ('50JP6') Validar status en L, sociedad F474 y la división pertensca para 01BOG =  O181 01BOG");
		 /*System.out.println("Paso 1: Conexi�n con BD CNT COLOMBIA exitosa -> "+ BDCNTCOL.toString());
		 String query0 = "select * from tiendas.XX_NOMCOM_CNT_SAP where ceco in  ('50JP6')";
		 
		 SQLResult VALIDA = BDCNTCOL.executeQuery(query0);
		 if (VALIDA.getRowCount() > 0) {
			 System.out.println("correcto, la consulta obtiene datos ");
			 testCase.addQueryEvidenceCurrentStep(VALIDA);
			 
		 }else {
			 Assert.fail("Error, la consulta no trae datos");
		 }*/
		 	
		 // PASO 5
		 addStep("Ingresar a controlM con usuario y contraseña valido: http://10.184.56.194:18080/ControlM/");
		 
		/*u.get(GlobalVariables.CONTROL_M_SERVER);
		 u.waitForLoadPage();
		 CM.logOn();
		 testCase.addScreenShotCurrentStep(u, "control m inicio");*/
		 	
		 // PASO 6
		 addStep("ejecutar interfaz CS01_COL Carpeta: TQA_BAJO_DEMANDA");
			 
		/* boolean flag2 = controlM2.searchJob();
		 assertFalse(!flag2, "No se encontro el job");
		 System.out.println("Job Status " + controlM2.getCurrentStatus());
		 //Ejecucion
		 String resultadoDelJob2 = controlM2.executeJob();
		 testCase.addTextEvidenceCurrentStep("Resultado de la ejecuci�n " + resultadoDelJob2);
		 System.out.println("Resultado de la ejecuci�n " + resultadoDelJob2);

		 //Valor del output
		 testCase.addTextEvidenceCurrentStep("Output " + controlM2.getOutput());
		 System.out.println("Output " + controlM2.getOutput());

		 //Validacion del caso
		 boolean testResult2 = true;
		 if (resultadoDelJob2.equals("Failure")) {			 testResult = false;
			 testCase.addTextEvidenceCurrentStep("Fallo la ejecuci�n del job ");
			 System.out.println("Fallo la ejecuci�n del job ");
		 }
		 
		 assertTrue(testResult2, "La ejecucion del job fue exitosa");
		 controlM2.closeViewpoint();*/
		 	 
		 
		 // PASO 7
		 addStep(" Se valida que la ejecucion termino en \"S\" en el servidor redhat oxwwqbocq01 SELECT * FROM WMLOG.WM_LOG_RUN  WHERE INTERFACE LIKE '%S%' ORDER BY START_DT DESC;");
		/* System.out.println("Paso 1: Conexi�n con BD WMLOG exitosa -> "+ BDWMLOG2.toString());
		 String query1 = "SELECT * FROM WMLOG.WM_LOG_RUN  WHERE INTERFACE LIKE '%S%' ORDER BY START_DT DESC";
		 
		 SQLResult validabdwmlog = BDWMLOG2.executeQuery(query1);
		 if (validabdwmlog.getRowCount() > 0) {
			 System.out.println("correcto, la consulta obtiene datos ");
			 testCase.addQueryEvidenceCurrentStep(validabdwmlog);
			 
		 }else {
			 Assert.fail("Error, la consulta no trae datos");
		 }
		 	
		 	*/	 
		 
		 // PASO 8
		 addStep("Validar cambio estatus “E” en la tabla xx_nomcom_cnt_sap que indica que el ceco o cr_tienda se encuentra de lado del SAP FI");
		
		 /*String query2 = "Select * from tiendas.XX_NOMCOM_CNT_SAP where WM_status = 'E' ";
		 SQLResult validaestatusE = BDCNTCOL.executeQuery(query2);
		 if (validaestatusE.getRowCount() > 0) {
			 System.out.println("correcto, la consulta obtiene datos ");
			 testCase.addQueryEvidenceCurrentStep(validaestatusE);
			 
		 }else {
			 Assert.fail("Error, la consulta no trae datos");
		 }*/
		 
		 // PASO 9		 
		 addStep("Validar envio de Cecos en SAP FI");
		 
		 //PASO 10
		 addStep("Se ejecutara interfaz ALE por parte de expertal ");
		 
		 //PASO 11
		 addStep("se visualiza cecos en SAP HCM ");
		 
		 
		 
	 }
	 
	 @Override
	    public String setTestFullName() {
	        return " ATC_FT_002_Ejecutar_la_interfaz_CS01_COL";
	    }

	    @Override
	    public String setTestDescription() {
	        return "Ejecución de la interfaz CS01(COL) envie la tienda operativa hacia el SAP financiero (SAP FI)";
	    }

	    @Override
	    public String setTestDesigner() {
	        return "Mario de Jesus Salazar Rocha";
	    }

	    @Override
	    public String setTestInstanceID() {
	        return null;
	    }

	    @Override
	    public String setPrerequisites() {
	        return "Acceso a Control-M Acceso a CNT Contar con tienda operativa";
	    }

	    @Override
	    public void beforeTest() {

	    }	 

}
