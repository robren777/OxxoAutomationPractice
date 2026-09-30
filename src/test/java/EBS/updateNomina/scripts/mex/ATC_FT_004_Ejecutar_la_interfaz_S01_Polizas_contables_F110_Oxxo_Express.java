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

import EBS.updateNomina.om.Login;
import EBS.updateNomina.om.PolizasSOL;
import EBS.updateNomina.om.UpdateNomina;
import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_004_Ejecutar_la_interfaz_S01_Polizas_contables_F110_Oxxo_Express extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_004_Ejecutar_la_interfaz_S01_Polizas_contables_F110_Oxxo_Express_test(HashMap<String, String> data) throws Exception{
	
		/** Utileria******************************************************/
		 //VARIABLES
		 String serverFCWM6QA = data.get("serverFCWM6QA");
		 String usernameFCWM6QA = data.get("usernameFCWM6QA");
		 String passwordFCWM6QA = data.get("passwordFCWM6QA"); 
		 String controlMUser = data.get("controlMUser");
		 String controlMPwd = data.get("controlMPwd");
		  String portalAppsUser = data.get("portalAppsUser");
		 String portalAppsPwd = data.get("portalAppsPwd");
		 String gl_ID = null;
		

		 
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
		 UpdateNomina UN = new UpdateNomina(sk,u, testCase, pathimg,ListImage,ImgPattern,patterns,Folder);;

		 //UTILERIAS SQL
		utils.sql.SQLUtil bdWMLog = new utils.sql.SQLUtil(serverFCWM6QA, usernameFCWM6QA, passwordFCWM6QA);
		utils.sql.SQLUtil ebsMexico = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
		 
		 
		 // PASO 1
		 addStep("Ingresar a la BD FCWM6QA de WMLOG" + "usuario: QAVIEW" + "host: oxzvmwmiracq01.femcom.net" + "puerto: 1521" + "nombre servicio: FCWM6QA.FEMCOM.NET");
		 testCase.addTextEvidenceCurrentStep("Conexion Exitosa a BD FCWM6QA ");

		 // PASO 2
		 addStep("Validar estatus L para polizas con la sociedad F099 en la tabla sapuser.sap_inbound_docs ,SAP_PYR_HEADER");
		String consulta = UN.CF110L()  ;
		 SQLResult sap_inbound_docs = bdWMLog.executeQuery(consulta);
		 if (sap_inbound_docs.getRowCount() > 0) {
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

		 // PASO 5
		 addStep("\"Ingresar a la BD FCWM6QA de WMLOG\n" + "usuario: QAVIEW\n" + "host: oxzvmwmiracq01.femcom.net\n" + "puerto: 1521\n" + "nombre servicio: FCWM6QA.FEMCOM.NET\"\n");
		 testCase.addTextEvidenceCurrentStep("Conexion Exitosa a BD FCWM6QA ");
		 
		 // PASO 6
		 addStep("Validar el cambio de status a S en la tabla sapuser.sap_inbound_docs");
		 
		 String consulta1 = UN.CF110S();
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
		 addStep("Ingresar a la BD OXXO GAS en Microsoft SQL HOST: 10.52.34.23 BD: oxxogas_turbo_nom USER: wmuserqa PASS: Comercio*1");
		 testCase.addTextEvidenceCurrentStep("Conexion Exitosa a BD OXXO GAS ");
		 
		 // PASO 8
		 addStep("validar el envio de polizas a la tabla de EBS select * from apps.gl_interface where group_id in");
		 UN.F110(gl_ID);
			
		 
		 
		
	}
	
	@Override
	public String setTestFullName() {
	    return "ATC_FT_003_Ejecutar_la_interfaz_S01_Polizas_contables_OXXO_F099";
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
