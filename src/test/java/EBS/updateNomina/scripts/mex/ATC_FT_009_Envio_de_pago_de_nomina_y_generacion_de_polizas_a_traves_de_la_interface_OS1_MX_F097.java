package EBS.updateNomina.scripts.mex;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.sikuli.script.Pattern;
import org.testng.Assert;
import org.testng.annotations.Test;

import EBS.updateNomina.om.UpdateNomina;
import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;



public class ATC_FT_009_Envio_de_pago_de_nomina_y_generacion_de_polizas_a_traves_de_la_interface_OS1_MX_F097 extends BaseExecution {
		
	 @Test(dataProvider = "data-provider")
	    public void ATC_FT_009_Envio_de_pago_de_nomina_y_generacion_de_polizas_a_traves_de_la_interface_OS1_MX_F097_test(HashMap<String, String> data) throws Exception {
	    		
		 
		 //** Utileria******************************************************/
		 //VARIABLES
		 String serverFCWM6QA = data.get("serverFCWM6QA");
		 String usernameFCWM6QA = data.get("usernameFCWM6QA");
		 String passwordFCWM6QA = data.get("passwordFCWM6QA");
		 String serverFCWMLQA = data.get("serverFCWMLQA");
		 String usernameFCWMLQA = data.get("usernameFCWMLQA");
		 String passwordFCWMLQA = data.get("passwordFCWMLQA");
		 String controlMUser = data.get("controlMUser");
		 String controlMPwd = data.get("controlMPwd");
		 String serverIMMEX = data.get("servImmex");
		 String userIMMEX = data.get("userImmex");
		 String paswIMMEX = data.get("paswImmex");
		 String IDWMLOG = null;
		 
		 JSONObject obj1 = null;
		 try {
			 obj1 = new JSONObject(data.get("job"));
		 } catch (JSONException e) {
			 System.err.println("JSONException: 'job' key not found in data: " + data);
			 throw e;
		 }
		 
		  //UTILERIAS WEB
		 SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);
		 Control_mInicio CM = new Control_mInicio(u, controlMUser, controlMPwd);
		 ControlM controlM = new ControlM(u, testCase, obj1);
		 SikuliUtil sk = new SikuliUtil();
		 Pattern pathimg = new Pattern();
		 String pathImg = sk.PatternPath("EBS\\"+"EBS");
		 ArrayList<String> ListImage = new ArrayList<String>();
		 HashMap<String, Pattern> ImgPattern = new HashMap<String, Pattern>();
		 ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		 File Folder = new File(pathImg);
		 UpdateNomina UN = new UpdateNomina(sk,u, testCase, pathimg,ListImage,ImgPattern,patterns,Folder);

		 //UTILERIAS SQL
		 utils.sql.SQLUtil bdWMLog = new utils.sql.SQLUtil(serverFCWM6QA, usernameFCWM6QA, passwordFCWM6QA);
		 utils.sql.SQLUtil ebsMexico = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
		 utils.sql.SQLUtil BDWMLog = new utils.sql.SQLUtil(serverFCWMLQA, usernameFCWMLQA, passwordFCWMLQA);
		 utils.sql.SQLUtil bdIMMEX= new utils.sql.SQLUtil(serverIMMEX, userIMMEX, paswIMMEX);
		 
		 // PASO 1
		 addStep("Ingresar a la BD FCWM6QA de WMLOG" + "usuario: QAVIEW" + "host: oxzvmwmiracq01.femcom.net" + "puerto: 1521" + "nombre servicio: FCWM6QA.FEMCOM.NET");
		 testCase.addTextEvidenceCurrentStep("Conexion Exitosa a BD FCWM6QA ");

		 // PASO 2
		 addStep("Validar tener información en la tabla SAP_INBOUND_DOCS de SAPUSER de la BD FCWM6QA con estatus S.");
		
		 String query2 = "SELECT * FROM SAPUSER.SAP_INBOUND_DOCS T1, SAPUSER.SAP_PYR_HEADER T2, SAPUSER.SAP_PYR_DETL T3\r\n"
		 		+ "WHERE (T1.ID = T2.SID_ID\r\n"
		 		+ "AND T2.SID_ID = T3.SID_ID)\r\n"
		 		+ "AND (T1.STATUS = 'S'\r\n"
		 		+ "AND T2.TARGET_SYS = 'O')\r\n"
		 		+ "AND T1.TARGET_ID = '6238710'";
		 
		 SQLResult sap_inbound_docs2 = bdWMLog.executeQuery(query2);
		 
		 if (sap_inbound_docs2.getRowCount() > 0) {
			 System.out.println("correcto, la consulta trae datos");
			 testCase.addQueryEvidenceCurrentStep(sap_inbound_docs2);
			 String statusBD = sap_inbound_docs2.getData(0, "STATUS").toString();
			 assertEquals(statusBD,"S");
		 }else {
			 Assert.fail("Error, la consulta no trae datos");
		 }
		 
		 // PASO 3
		 addStep("\"Ingresar a la BD IMMEX");
		 testCase.addTextEvidenceCurrentStep("Conexion Exitosa a BD FCIMXQA ");
		 
		 //PASO 4
		 addStep("Validar la información de las pólizas en BD FCIMXQA en la tabla GL_INTERFACE con estatus diferente de NEW.");
		 
		 String difnew = "SELECT * From\r\n"
		 		+ "(select STATUS,LEDGER_ID,USER_JE_CATEGORY_NAME,SEGMENT1,SEGMENT2,SEGMENT3,SEGMENT4,SEGMENT7, REFERENCE6, DATE_CREATED, GROUP_ID\r\n"
		 		+ "FROM apps.GL_INTERFACE\r\n"
		 		+ "WHERE UPPER( STATUS ) <> 'NEW'\r\n"
		 		+ "and  GROUP_ID =6238710)\r\n"
		 		+ "where ROWNUM <= 10";
		 SQLResult gl_interface = bdIMMEX.executeQuery(difnew); 
		 
		 if (gl_interface.getRowCount() > 0) {
			 System.out.println("correcto, la consulta trae datos");
			 testCase.addQueryEvidenceCurrentStep(gl_interface);
			 String Status = gl_interface.getData(0, "STATUS").toString();
			  assertEquals(Status,"P");
		 }else {
			 Assert.fail("Error, la consulta no trae datos");
		 }
		 
		// PASO 5
		 addStep("Ingresar a Control -M con usuario y contraseña valido http://10.184.56.194:18080/ControlM/");
		 testCase.addTextEvidenceCurrentStep("Se ingresa a Control M y ejecuta Job en paso 6");
		 
		 // PASO 6
		 addStep("Ejecutar interfaz S01 Carpeta: TQA_BO_1011");
		 UN.logInControlMyEjecutarJob(CM, controlM);

		
		 // PASO 7
		 addStep("Realizar la conexión a la BD FCWMLQA Usuario: QAVIEW host:oxzvmwmiracq01.femcom.net puerto:1521 servicio: FCWMLQA.FEMCOM.NET");
		 testCase.addTextEvidenceCurrentStep("Conexion Exitosa a BD BD FCWMLQA ");
		 
	 	
	
		 // PASO 8
		 addStep("Validar la correcta ejecución de la interface OS1_MX en la tabla WM WM_LOG_RUN de la BD FCWMLQA");
		 		
		 String status = "SELECT * FROM\r\n"
		 		+ "(select * from WMLOG.WM_LOG_RUN WHERE INTERFACE LIKE '%OS1%' ORDER BY END_DT DESC)\r\n"
		 		+ "where ROWNUM <= 1";
		 SQLResult GL_BDwmlog = BDWMLog.executeQuery(status);
		 
		 if (GL_BDwmlog.getRowCount() > 0) {
			 System.out.println("correcto, la consulta trae datos");
			 testCase.addQueryEvidenceCurrentStep(GL_BDwmlog);
			 String statusBD = GL_BDwmlog.getData(0, "STATUS").toString();
			 IDWMLOG = GL_BDwmlog.getData(0, "Run_ID");
			 assertEquals(statusBD,"S");
		 }else {
			 Assert.fail("Error, la consulta no trae datos");
		 }
		 
		 	
		 
		 // PASO 9
		 addStep("Validar la correcta ejecución de los Threads lanzados por la interface OS1_MX en la tabla WM_LOG_THREAD de la BD FCWMLQA.");
		 String query6 = "SELECT * FROM WMLOG.WM_LOG_THREAD WHERE PARENT_ID ="+ IDWMLOG;
		 SQLResult THREAD = BDWMLog.executeQuery(query6);
		 
		 if (THREAD.getRowCount() == 0) {
			 System.out.println("correcto, la consulta No trae datos");
			 testCase.addQueryEvidenceCurrentStep(THREAD);
			 
		 }else {
			 Assert.fail("Error, la consulta contiene datos");
		 }
		 
		 // PASO 10
		 addStep("Realizar la siguiente consulta para verificar que no se encuentre ningún error presente en la ejecución de la interfaz OS1_MX en la tabla WM_LOG_ERROR de BD FCWMLQA.");
		 String query7 = "SELECT * FROM WMLOG.WM_LOG_ERROR WHERE RUN_ID ="+ IDWMLOG;
         SQLResult LOG_ERROR = BDWMLog.executeQuery(query7);
         
         if (LOG_ERROR.getRowCount() == 0) {
			 System.out.println("correcto, no contiene error en THREAD");
			 testCase.addQueryEvidenceCurrentStep(LOG_ERROR);
			 
		 }else {
			 Assert.fail("Error, la consulta contiene datos");
		 }
		 
		 // PASO 11
		 addStep("Validar que la información de la póliza se inserta correctamente en SAP_RPY_DETL en SAPUSER de la BD de FCWM6QA");
		 String  query8 = " SELECT * FROM SAPUSER.SAP_RPY_DETL WHERE JOURNALID ='6238710' ";
		 SQLResult Insercion_correcta = bdWMLog.executeQuery(query8);
		 
		 if (Insercion_correcta.getRowCount() > 0) {
			 System.out.println("correcto, la consulta contiene datos ");
			 testCase.addQueryEvidenceCurrentStep(Insercion_correcta);
			 
		 }else {
			 Assert.fail("Error, la consulta NO contiene datos no se insertaron los datos correctamnete ");
		 }
		 
		 
		 // PASO 12 
		 addStep(" Validar que se actualiza correctamente la información de la tabla SAP_INBOUND_DOCS ");
		 String query9 = "SELECT * FROM SAPUSER.SAP_INBOUND_DOCS T1, SAPUSER.SAP_PYR_HEADER T2, SAPUSER.SAP_PYR_DETL T3\r\n"
		 		+ "WHERE (T1.ID = T2.SID_ID AND T2.SID_ID = T3.SID_ID)\r\n"
		 		+ "AND (T1.STATUS = 'F' AND T2.TARGET_SYS = 'O')\r\n"
		 		+ "AND T1.TARGET_ID = 6238710";
		 
		 SQLResult SAP_INBOUND_DOCS =  bdWMLog.executeQuery(query9); 
		 if (SAP_INBOUND_DOCS.getRowCount() > 0) {
			 System.out.println("correcto, la consulta trae datos");
			 testCase.addQueryEvidenceCurrentStep(SAP_INBOUND_DOCS);
			 String statusBD = SAP_INBOUND_DOCS.getData(0, "STATUS").toString();
			 assertEquals(statusBD,"F");
		 }else {
			 Assert.fail("Error, la consulta no trae datos");
		 }
		 
		 
		 
}

	    @Override
	    public String setTestFullName() {
	        return "ATC_FT_009_Envio_de_pago de_nomina_y_generacion_de_polizas_a_traves_de_la_interface_OS1_MX_F097";
	    }

	    @Override
	    public String setTestDescription() {
	        return "Ejecucion de la interfaz RO25(MEx) envie la tienda nueva hacia CNT";
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
	        return "Acceso a EBS, CNT, Control M";
	    }

	    @Override
	    public void beforeTest() {

	    }


}
