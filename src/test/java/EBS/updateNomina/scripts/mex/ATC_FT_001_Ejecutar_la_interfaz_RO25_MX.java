package EBS.updateNomina.scripts.mex;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.updateNomina.om.UpdateNomina;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_Ejecutar_la_interfaz_RO25_MX extends BaseExecution {
		
	 @Test(dataProvider = "data-provider")
	    public void ATC_FT_001_Ejecutar_la_interfaz_RO25_MX_test(HashMap<String, String> data) throws Exception {
	    
		    
		   
			//VARIABLES
				 	
		 	String dateTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
		 	String ebsUrl = data.get("server");
			String userName = data.get("userName");
			String password = PasswordUtil.decryptPassword(data.get("password"));
			String responsabilidad = data.get("responsabilidad");
			String compania = data.get("compania");
			String estadoFinanciero = data.get("estadoFinanciero");
			String crSuperior = data.get("crSuperior");
			String fecha_VI = data.get("Fechavigencia");
			String prefijo = data.get("prefijo");
			String descripcion = data.get("descripcion");
			String descripcionHrs = descripcion + dateTime;
			String controlMUser = data.get("ctrlUser");
			String controlMPwd = data.get("ctrlpass");
			String controlMUrl = data.get("UrlctrlM"); 
			String usercol = data.get("usercol");
			String passcol = data.get("passwordcol");
			String urlcol = data.get("urlcol");
			String PlazaE = data.get("plaza");
			
			
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
			EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
			SikuliUtil sk = new SikuliUtil();
			Pattern pathimg = new Pattern();
			String pathImg = sk.PatternPath("EBS\\"+"EBS");
			EBS.cnt.om.Login loginCNT = new EBS.cnt.om.Login(u, testCase);
			Control_mInicio CM = new Control_mInicio(u, controlMUser, controlMPwd);
			ControlM controlM = new ControlM(u, testCase, obj);
			
			ArrayList<String> ListImage = new ArrayList<String>();
			HashMap<String, Pattern> ImgPattern = new HashMap<String, Pattern>();
			ArrayList<Pattern> patterns = new ArrayList<Pattern>();
			File Folder = new File(pathImg);
			
			MetodosSikuli ms = new MetodosSikuli(sk,u,pathimg,ListImage,ImgPattern,patterns,Folder);
			UpdateNomina un = new UpdateNomina(sk,u, testCase, pathimg,ListImage,ImgPattern,patterns,Folder);
			PatternUtil patternutil = new PatternUtil(sk,u,pathimg,ListImage,ImgPattern,patterns,Folder);
			UpdateNomina UN = new UpdateNomina(sk,u, testCase, pathimg,ListImage,ImgPattern,patterns,Folder);
								

			// PASO 1
			addStep("ingresar a Oracle EBS con usuario y contraseña valido http//fcrebsqa.femcom.net:8011/OA_HTML/AppsLocalLogin.jsp");
			logger.info("EBS URL: " + ebsUrl);
			System.out.println("EBS URL: " + ebsUrl);
			logger.info("User Name: " + userName);
			System.out.println("User Name: " + userName);
		    inicio.login();
			
			
			// PASO 2
			addStep("Ingresar a la Responsabilidad MXOXXO-GLXXXXX-Super Usuario");
			paginaInicial.clickOnResponsabilidad(responsabilidad);
			
			// PASO 3
			addStep("Ingresar al apartado  a CRs > Alta de centros de Responsabilidad.");
			paginaInicial.carpetaCRsElement.click();
			paginaInicial.clickAltaCentrosResponsabilidad();
					
			
			// PASO 4
     		addStep(" Llenar los siguientes campos: Compñia, Edo Fin, Cr superior, Fecha Vigencia VI");
			un.ejecutararchivofrmservlet(Folder, compania, estadoFinanciero, crSuperior, fecha_VI, prefijo, descripcionHrs); 

			//PASO 5
			addStep(" Oprimir el botón 'Obtener CR Valido' donde el sufijo se llenará de manera automática, Seguido del botónGenera CRs ");
			testCase.addTextEvidenceCurrentStep(" El paso 5 se ejecuta en el paso anterior addStep 4 ");
						
			//PASO 6
			addStep(" Ingresar a controlM con usuario y contraseña valido: ");
			un.logInControlMyEjecutarJob(CM, controlM);
			
			
			//PASO 7
			addStep("Ejecutar la interfaz runNewCRO25 carpeta: UAT_WM");
			testCase.addTextEvidenceCurrentStep("Control M se ejecuta en el paso 6");
				
			//PASO 8
			addStep("Ingresar  a la siguiente liga de CNT http://fcowlq07.femcom.net:8000/tiendas/html/login.html");
			UN.ConexionCNT(usercol, passcol);
							
			
			//PASO 9
			addStep("Ingresar plaza, En este caso es 10MON y damos clic en 'CONTINUAR' ");
			un.clickSeleccionarPlaza(PlazaE);
			
			//PASO 10
			addStep("En la siguiente pantalla seleccionamos el CR que acabamos de crear y le damos a 'CONTINUAR' ");
			UN.clicktiendsCR(Folder);		
			
			

	    }

	    @Override
	    public String setTestFullName() {
	        return "ATC_FT_001_Ejecutar_la_interfaz_RO25_MX";
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
