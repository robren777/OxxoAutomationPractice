package EBS.updateNomina.scripts.mex;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.sikuli.script.Pattern;
import org.testng.Assert;
import org.testng.annotations.Test;

import EBS.updateNomina.om.Login;
import EBS.updateNomina.om.PolizasSOL;
import EBS.updateNomina.om.UpdateNomina;
import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.SikuliUtil;
//import utils.sql.SQLUtil;
//import util.pageObjectModel.EBSPaginaInicial;
//import utils.SikuliUtil;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
//import util.RequestUtil;
import utils.sql.SQLResult;


public class ATC_FT_002_Ejecutar_la_interfaz_S01_Polizas_contables_IMMEX_F097 extends BaseExecution {
	
	 @Test(dataProvider = "data-provider")
	    public void ATC_FT_002_Ejecutar_la_interfaz_S01_Polizas_contables_IMMEX_F097_test(HashMap<String, String> data) throws Exception {
	
		 //Utileria******************************************************/
		 //VARIABLES
		 String serverFCWM6QA = data.get("serverFCWM6QA");
		 String usernameFCWM6QA = data.get("usernameFCWM6QA");
		 String passwordFCWM6QA = data.get("passwordFCWM6QA"); 
		 String serverIMMEX = data.get("servImmex");
		 String userIMMEX = data.get("userImmex");
		 String paswIMMEX = data.get("paswImmex"); 
		 String controlMUser = data.get("controlMUser");
		 String controlMPwd = data.get("controlMPwd");

		 String portalAppsUser = data.get("portalAppsUser");
		 String portalAppsPwd = data.get("portalAppsPwd");
		 String ServidorInmex= data.get("servidor");
		 String gl_ID = null;
		 
		 
		
		
		 String INMEX = "F097";
		 
		 
		 JSONObject obj = null;
		 try {
			 obj = new JSONObject(data.get("job"));
		 } catch (JSONException e) {
			 System.err.println("JSONException: 'job' key not found in data: " + data);
			 throw e;
		 }

		 //UTILERIAS WEB
		 SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);
		 Control_mInicio CM = new Control_mInicio(u, controlMUser, controlMPwd);
		 ControlM controlM = new ControlM(u, testCase, obj);
		 Login login = new Login(u, testCase);
		 PolizasSOL polizasSOL = new PolizasSOL(u, testCase);
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
		 utils.sql.SQLUtil bdIMMEX= new utils.sql.SQLUtil(serverIMMEX, userIMMEX, paswIMMEX);
		 
		 // PASO 1
		 addStep("Ingresar a la BD FCWM6QA de WMLOG" + "usuario: QAVIEW" + "host: oxzvmwmiracq01.femcom.net" + "puerto: 1521" + "nombre servicio: FCWM6QA.FEMCOM.NET");
		 testCase.addTextEvidenceCurrentStep("Conexion Exitosa a BD FCWM6QA ");

		 // PASO 2
		 addStep("Validar estatus L para polizas con la sociedad F097 en la tabla sapuser.sap_inbound_docs ,SAP_PYR_HEADER");
		 
		 String consulta = UN.CF097L();
		 SQLResult sap_inbound_docs = bdWMLog.executeQuery(consulta);
		 if (sap_inbound_docs.getRowCount() > 0) {
			 String statusbdL = sap_inbound_docs.getData(0, "STATUS").toString();
			 
			 System.out.println("correcto, la consulta trae datos");
			 testCase.addQueryEvidenceCurrentStep(sap_inbound_docs);
			
			 
		 }
		 else {
			 Assert.fail("Error, la consulta no trae datos");
		 }
		 
		 
		 // PASO 3
		 addStep("Ingresar a Control -M con usuario y contrase�a valido http://10.184.56.194:18080/ControlM/");
		 testCase.addTextEvidenceCurrentStep("Se ingresa a Control M y ejecuta Job en paso 4");

		 // PASO 4
		 addStep("Ejecutar interfaz S01 Carpeta: TQA_TEST_WM99_INT");
		 UN.logInControlMyEjecutarJob(CM, controlM);
		 
		  //PASO 5
		 addStep("\"Ingresar a la BD FCWM6QA de WMLOG\n" + "usuario: QAVIEW\n" + "host: oxzvmwmiracq01.femcom.net\n" + "puerto: 1521\n" + "nombre servicio: FCWM6QA.FEMCOM.NET\"\n");
		 testCase.addTextEvidenceCurrentStep("Conexion Exitosa a BD FCWM6QA ");
		 
		 //PASO 6
		 addStep("Validar el cambio de status a 'S' en la tabla sapuser.sap_inbound_docs");
		 
		 
		 String consulta1 = UN.CF097S();
		 SQLResult sap_inbound_docs2 = bdWMLog.executeQuery(consulta1);
		 
		 if (sap_inbound_docs2.getRowCount() > 0) {
			 System.out.println("correcto, la consulta trae datos");
			 testCase.addQueryEvidenceCurrentStep(sap_inbound_docs2);
			 String statusBD = sap_inbound_docs2.getData(0, "STATUS").toString();
			 gl_ID = sap_inbound_docs.getData(0, "TARGET_ID").toString();
			 assertEquals(statusBD,"S");
			 
		 }else {
			Assert.fail("Error, la consulta no trae datos");
		 }
		 
		 
		 // PASO 7
		 addStep("\"Ingresar a la BD EBS IMMEX usuario: APPSVIEW host: oxzvmimmexqdb01.femcom.net puerto: 1535nombre servicio: FCIMXQA esquema: APPS");
		 testCase.addTextEvidenceCurrentStep("Conexion Exitosa a EBS IMMEX ");
		 
		 // PASO 8
		 addStep("validar el envio de polizas a la tabla de EBS select * from apps.gl_interface where group_id in");
		 
		 String consulta3 = UN.GLF097(gl_ID);
		 SQLResult gl_interface = bdIMMEX.executeQuery(consulta3);
		 
		 if (gl_interface.getRowCount() > 0) {
			 
			 System.out.println("correcto, la consulta trae datos");
			 testCase.addQueryEvidenceCurrentStep(gl_interface);
			 
		 }else {
			 Assert.fail("Error, la consulta no trae datos");
		 }
		
		 
}
	 @Override
	public String setTestFullName() {
	    return "ATC_FT_002_Ejecutar_la_interfaz_S01_Polizas_contables_IMMEX_F097";
	}
	 @Override
	public String setTestDescription() {
	    return "Ejecución de la interfaz S01 para enviar los fondeos de polizas hacia EBS";
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
	    return "Contar con el equipo de expertal para el envio de polizas desde sap HCM, Acceso a EBS y Control M";
	}
	
	@Override
	public void beforeTest() {
	
	}
	

}
