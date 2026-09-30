package EBS.updateNomina.CNTColombia.scripts;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.updateNomina.om.Login;
import EBS.updateNomina.om.UpdateNomina;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSExtraccion;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import start.Start;
import utils.SikuliUtil;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_Desde_CNT_enviar_la_creacion_de_una_tienda_completa_CO_UO_Posiciones_hacia_SAP_HCM_COL extends BaseExecution {

	 @Test(dataProvider = "data-provider")
	    public void ATC_FT_001_Desde_CNT_enviar_la_creacion_de_una_tienda_completa_CO_UO_Posiciones_hacia_SAP_HCM_COL_test(HashMap<String, String> data) throws Exception {
		 
		 Start.generateEncryptPassword();
		 //VARIABLES
		 SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm:ss");
		 Date date = new Date(0);
		
		 
		 String ebsUrl = data.get("server");
		 String userName = data.get("userName");
		 String password = PasswordUtil.decryptPassword(data.get("password"));
		 
		 String colurl = data.get("urlcol");
		 String coluserName = data.get("usercol");
		 String colpassword = data.get("passwordcol");
		 
		 String responsabilidad = data.get("responsabilidad");
		 String compania = data.get("compania");
		 String estadoFinanciero = data.get("estadoFinanciero");
		 String crSuperior = data.get("crSuperior");
		 String fecha_VI = data.get("Fechavigencia");
		 String prefijo = data.get("prefijo");
		 String descripcion = data.get("descripcion");
		 String descripcionHrs = descripcion + dateformat.getTimeZone();
		 String PlazaC = data.get("plaza");
		 //String servercnt = data.get(descripcionHrs);
		  //String password = PasswordUtil.decryptPassword(data.get("password"));
		 String controlMUser = data.get("ctrlUser");
		 String controlMPwd = data.get("ctrlpass");
		 String controlMUrl = data.get("UrlctrlM");
		 
		 String userCol = data.get("usercol");
		 String passwordCol = PasswordUtil.decryptPassword(data.get("passwordcol"));
		 String PlazaECol = data.get("PlazaE");
		 String Distrito = data.get("Distrito");
		 String TiendaCol = data.get("Tienda");
		 String URLCol =data.get("urlcol");
		 
		 JSONObject obj = null;
		 try {
			 obj = new JSONObject(data.get("job"));
		 } catch (JSONException e) {
			 System.err.println("JSONException: 'job' key not found in data: " + data);
			 throw e;
		 }
		 	
			
			
		 //UTILERIAS
		 SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);
		 LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		 Control_mInicio CM = new Control_mInicio(u, controlMUser, controlMPwd);
		 ControlM controlM = new ControlM(u, testCase, obj);
		 //LoginTool inicio1 = new LoginTool(u, testCase, colurl, coluserName, colpassword);
		 Login login = new Login(u, testCase);
		 EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		 EBSExtraccion extraccion = new EBSExtraccion(u, testCase);
		 SikuliUtil sk = new SikuliUtil();
		 Pattern pathimg = new Pattern();
		 String pathImg = sk.PatternPath("EBS\\"+"EBS");
		 ArrayList<String> ListImage = new ArrayList<String>();
		 HashMap<String, Pattern> ImgPattern = new HashMap<String, Pattern>();
		 ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		 File Folder = new File(pathImg);
		 	
		 MetodosSikuli ms = new MetodosSikuli(sk,u,pathimg,ListImage,ImgPattern,patterns,Folder);
		 UpdateNomina un = new UpdateNomina(sk,u,testCase,pathimg,ListImage,ImgPattern,patterns,Folder);
		 
		 
		
			
			
		 // PASO 1
		 addStep("ingresar a Oracle EBS con usuario y contraseña valido: http://fcrebsqa.femcom.net:8011/OA_HTML/AppsLocalLogin.jsp");
		 /*logger.info("EBS URL: " + ebsUrl);
		 System.out.println("EBS URL: " + ebsUrl);
		 logger.info("User Name: " + userName);
		 System.out.println("User Name: " + userName);
		 inicio.login();*/
		 				
		 				
		  // PASO 2
		 addStep("Ingresar a la Responsabilidad MXOXXO-GLXXXXX-Super Usuario");
		 //paginaInicial.clickOnResponsabilidad(responsabilidad);
		 				
		 // PASO 3
		 addStep("Ingresar al apartado  a CRs > Alta de centros de Responsabilidad.");
		 //paginaInicial.carpetaCRsElement.click();
		 //paginaInicial.clickAltaCentrosResponsabilidad();
		 						
		 				
		 // PASO 4
		 addStep(" Llenar los siguientes campos: Compñia, Edo Fin, Cr superior, Fecha Vigencia VI");
		 //un.ejecutararchivofrmservlet(Folder, compania, estadoFinanciero, crSuperior, fecha_VI, prefijo, descripcionHrs);
		 	
		 // PASO 5
		 //un.selecionarbotones(Folder);
		 	
		 // PASO 6
		 addStep(" Ingresar a controlM con usuario y contraseña valido: ");
		/* u.get(GlobalVariables.CONTROL_M_SERVER);
		 u.waitForLoadPage();
		 CM.logOn();
		 testCase.addScreenShotCurrentStep(u, "control m inicio");
					
		 // PASO 7
		 addStep("Ejecutar la interfaz runNewCRRO25_COL carpeta: TQACO_WM_COL");
		 boolean flag = controlM.searchJob();
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
		 }

		 assertTrue(testResult, "La ejecucion del job fue exitosa");
		 controlM.closeViewpoint();*/
		 
		 
		 // PASO 8
		 addStep(" Ingresar  a la siguiente liga de CNT: https://opvcntnp1.femcom.net:8443/pls/colombia/tiendas para iniciar sesion");
		un.loginCNT(userCol,passwordCol);
		
		addStep("Ingresar plaza, En este caso es 10BGA y damos clic en \"CONTINUAR\"");
		un.clickSeleccionarPlaza(PlazaC);
		
		
	 }	
	 
	 @Override
	    public String setTestFullName() {
	        return "ATC_FT_001_Desde_CNT_enviar_la_creacion_de_una_tienda_completa_CO_UO_Posiciones_hacia_SAP_HCM_COL";
	    }

	    @Override
	    public String setTestDescription() {
	        return "Creacion de un CR desde MCR viaje a CNT se ejecuten las interfaces R025(CO)y CS01 (CO) envie la tienda operativa hacia el SAP financiero (SAP FI)";
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
	        return "Acceso a EBS, CNT, SAP, SADEL, CONTROL-M para ejecucion de la R025 y las BD correspondientesAcceso a EBS, CNT, Control M";
	    }

	    @Override
	    public void beforeTest() {

	    }
	
	
}
