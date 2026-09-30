package EBS.updateNomina.MCRColombia.script;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.updateNomina.om.UpdateNomina;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_Desde_CNT_hacer_el_cambio_de_asesor_de_tienda_y_se_refleje extends BaseExecution {

	
	 @Test(dataProvider = "data-provider")
	    public void ATC_FT_001_Desde_CNT_hacer_el_cambio_de_asesor_de_tienda_y_se_refleje_test(HashMap<String, String> data) throws Exception{
		 
		 //Start.generateEncryptPassword();
			//VARIABLES
		 	
		 	String dateTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
		 	String controlMUser = data.get("ctrlUser");
			String controlMPwd = data.get("ctrlpass");
			String controlMUrl = data.get("UrlctrlM"); 
			String usercol = data.get("usercol");
			String passcol = data.get("passwordcol");
			String urlcol = data.get("urlcol");
			String PlazaE = data.get("plaza");
			String NomAsesor = data.get("asesor");
					
			
			 //UTILERIAS
			SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);
			//LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
			//EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
			//Login login = new Login(u, testCase);
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
			PatternUtil patternutil = new PatternUtil(sk,u,pathimg,ListImage,ImgPattern,patterns,Folder);
			
			 //UTILERIAS SQL
			// utils.sql.SQLUtil EBSCol = new utils.sql.SQLUtil(GlobalVariables.DB_USER_EBS_COL, GlobalVariables.DB_PASSWORD_EBS_COL, GlobalVariables.DB_HOST_RMS_COL);
			
			//QUERIES
			 String query0 = "Select  * from apps.xxfc_centros_responsabilidad;where retek_cr = and retek_asesor_nombre ='';";
			 String query1 = "Select  * from apps.xxfc_centros_responsabilidad;where retek_cr = and retek_asesor_nombre ='';";
			 
			//utilerias WEb
			//JSONObject obj = null;
			//try {
			//obj = new JSONObject(data.get("job"));
			//} catch (JSONException e) {
			//System.err.println("JSONException: 'job' key not found in data: " + data);
			//throw e;
			//}
			
			
			//ControlM controlM = new ControlM(u, testCase, obj);
		

			// PASO 1
			addStep("ingresar a la BD EBS usuario:APPSVIEW host: fcfin5.femcom.net puerto: 1531 ingresar a la BD EBS usuario:APPSVIEW host: fcfin5.femcom.net puerto: 1531 servicio: FCEBSCO2 ");
			//System.out.println("Paso 1: Conexi�n con BD EBS exitosa -> "+ EBSCol.toString());
						
			// PASO 2
			addStep("Validar el asesor que tiene asignada la tienda Select  * from apps.xxfc_centros_responsabilidad;where retek_cr = and retek_asesor_nombre ='';");
			
			/* SQLResult asesor = EBSCol.executeQuery(query0);
			 if (asesor.getRowCount() > 0) {
				 System.out.println("correcto, la consulta obtiene datos ");
				 testCase.addQueryEvidenceCurrentStep(asesor);
				 
			 }else {
				 Assert.fail("Error, la consulta no trae datos");
			 }*/
			
			// PASO 3
			addStep("Ingresar a CNT https://opvcntnp1.femcom.net:8443/pls/colombia/tiendas");
			/*u.get("http://fcowlq07.femcom.net:8000/tiendas/html/login.html");
			u.waitForLoadPage();
			//u.hardWait(3); 
			un.loginCNT(usercol,passcol);*/
						
			// PASO 4
			addStep(" Elegir tienda para realizar el cambio de asesor");
			//un.clickSeleccionarPlaza(PlazaE);
			
			
			//PASO 5
			addStep("ingresar a datos de operación y hacer el cambio de asesor y dar clic en guardar");
			//un.clickDatosOperacion(Folder, NomAsesor);
						
						
			//PASO 6
			addStep("ingresar a control -M con usuario y contraseña valido: http://10.184.56.194:18080/ControlM/ ");
			//Control_mInicio CM = new Control_mInicio(u, controlMUser, controlMPwd);
			 //u.get(GlobalVariables.CONTROL_M_SERVER);
			// WebDriver wd = u.getDriver();
			 //String aa = wd.getWindowHandle();
			 //wd.switchTo().window(aa);
						
			 //CM.logOn();
			 //testCase.addScreenShotCurrentStep(u, "control m inicio");

			
			//PASO 7
			addStep("ejecutar la interfaz CO1CO");
			
			  //addStep("ejecutar la interfaz OS9_MX");
			  //ControlM controlM = new ControlM(u, testCase, obj);
			  //boolean flag = controlM.searchJob();
			  //assertFalse(!flag, "No se encontro el job");
			  //System.out.println("Job Status " + controlM.getCurrentStatus());
			  //Ejecucion
			  //String resultadoDelJob = controlM.executeJob();
			  //testCase.addTextEvidenceCurrentStep("Resultado de la ejecuci�n " + resultadoDelJob);
			  //System.out.println("Resultado de la ejecuci�n " + resultadoDelJob);

			  ///Valor del output
			  //testCase.addTextEvidenceCurrentStep("Output " + controlM.getOutput());
			  //System.out.println("Output " + controlM.getOutput());

			  ///Validacion del caso
			  //boolean testResult = true;
			  //if (resultadoDelJob.equals("Failure")) {testResult = false;
			  //testCase.addTextEvidenceCurrentStep("Fallo la ejecuci�n del job ");
			  //System.out.println("Fallo la ejecuci�n del job ");
			  //}

			  //assertTrue(testResult, "La ejecucion del job fue exitosa");
			  //controlM.closeViewpoint();
			
			//PASO 8
			addStep("ingresar a la BD EBS usuario:APPSVIEW host: fcfin5.femcom.net puerto: 1531 servicio: FCEBSCO2");
			
			/*System.out.println("Paso 1: Conexi�n con BD EBS exitosa -> "+ EBSCol.toString());
			u.get("http://fcowlq07.femcom.net:8000/tiendas/html/login.html");
			u.waitForLoadPage();
			//u.hardWait(3); 
			un.loginCNT(usercol,passcol);*/
			
						
			//PASO 9
			addStep("Validar el cambio de asesor que tiene asignada la tienda Select  * from apps.xxfc_centros_responsabilidad;where retek_cr = and retek_asesor_nombre ='';");
			
			/*SQLResult Cambioasesor = EBSCol.executeQuery(query1);
			 if (asesor.getRowCount() > 0) {
				 System.out.println("correcto, la consulta obtiene datos ");
				 testCase.addQueryEvidenceCurrentStep(Cambioasesor);
				 
			 }else {
				 Assert.fail("Error, la consulta no trae datos");
			 }*/
			
								
	 }
	 
	 @Override
	    public String setTestFullName() {
	        return "ATC_FT_001_Desde_CNT_hacer_el_cambio_de_asesor_de_tienda_y_se_refleje_la_BD_EBS_centros_de_responsabilidad";
	    }

	    @Override
	    public String setTestDescription() {
	        return "Desde CNT Colombia hacer el cambio de asesor de una tienda existente y validar el cambio en la BD EBS";
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
	        return "Contar con una tienda en CNT Colombia 2- Contar con la BD EBS AVANTE";
	    }

	    @Override
	    public void beforeTest() {

	    }
}
