package WM.EvolucionRetiroSinTarjeta.HU014;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_002_Validar_Configuracion_de_MEANING_para_depuracion_BD_EBS_mediante_Aplicativo_Oracle extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_Validar_Configuracion_de_MEANING_para_depuracion_BD_EBS_mediante_Aplicativo_Oracle_test(HashMap<String, String> data)
			throws Exception{

		/**
		 * Plataforma de EBS http://fcrebsqa.femcom.net:8011/OA_HTML/AppsLocalLogin.jsp
		 * Desc:
		 * Validar Configuracion de MEANING para depuracion BD EBS mediante aplicativo EBS
		 * @author Mónica Soto
		 * @date 22/02/2024
		 **/

		/*
         * VARIABLES
         */
        String ebsUrl = GlobalVariables.getAvanteServer;
        String userName = GlobalVariables.get6008201User;
        String password = GlobalVariables.get6008201Pswd;
        
        String desarrolloAplicaciones = data.get("desarrolloAplicaciones");
        String carpetaAplicacion = data.get("carpetaAplicacion");
        String carpetaConsultas = data.get("carpetaConsultas");
        String careptaConsultasGl = data.get("careptaConsultasGl");

		testCase.setTest_Description(data.get("id") + data.get("description"));

		/*
		 * Utilerias
		 *********************************************************************/

		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
        EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
    	LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
      
    	SikuliUtil sk = new SikuliUtil();
    	String pathImg = sk.PatternPath("EBS\\"+"EBS");
    	Pattern pathimg = new Pattern();
    	ArrayList<String> ListImage = new ArrayList<String>();
    	HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
    	ArrayList<Pattern> patterns = new ArrayList<Pattern>();
    	File Fol = new File(pathImg);
    	
    	MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
        PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);


		System.out.println(
				"*****************************************PASO 1 y 2************************************************");
		addStep("Se ingresa a la página de EBS");
		inicio.login();

		addStep("Iniciar sesion con el usuario y contraseña al portal de EBS");	
		testCase.addScreenShotCurrentStep(u, "Portal de EBS");
		
		System.out.println(
				"*****************************************PASO 3************************************************");
		
		addStep("Ingresar a la responsabilidad de Application Developer y luego a las carpetas /Application/Lookups/ y despues abrir GL Lookups para que se descargue el aplicativo frmservlet");
		
        paginaInicial.clickOnResponsabilidad(desarrolloAplicaciones);
        paginaInicial.clickOnResponsabilidad(carpetaAplicacion);
        paginaInicial.clickOnResponsabilidad(carpetaConsultas);
        
		testCase.addScreenShotCurrentStep(u, "Carpeta Consultas GL");
		paginaInicial.clickOnResponsabilidad(careptaConsultasGl);
		
		System.out.println(
				"*****************************************PASO 4************************************************");
		
		addStep("Ejecutar el aplicativo frmservlet desde la carpeta de Descargas");
        patternUtil.runApplet();
        
		testCase.addScreenShotCurrentStep("Applicativo frmservlet");
						
		System.out.println(
				"*****************************************PASO 5************************************************");
		
		addStep("En la pestaña de arriba debajo File esta el boton de Find, debemos de darle click para que se nos muestre la ventana de Busqueda de Lookup Types con su buscador");

        p.lamparaBusqueda();
		testCase.addScreenShotCurrentStep("Ventana de búsqueda Lookups");
		
		p.buscarNombreSolicitud("XXFC_GL_RST_CONFIG");
		
		System.out.println(
				"*****************************************PASO 6 y 7************************************************");
		
		addStep("En el campo de Find debemos buscar las configuraciones de XXFC_GL_RST_CONFIG y darle click en el boton Find");
	
		for (int i = 0; i <= 13; i++) {
			patternUtil.doubleClickPattern("ocDownArrow", 10);
		}
		
		addStep("En la ventana XXFC_GL_RST_CONFIG, debemos buscar el campo Code = 'RESP_MESES'  y en el MEANING ='30'");
		testCase.addScreenShotCurrentStep("Ventana XXFC_GL_RST_CONFIG");
		
        u.hardWait(5);
		testCase.addTextEvidenceCurrentStep("El dato : "+patternUtil.textCopy());
		p.enter();
		testCase.addTextEvidenceCurrentStep("Tiene el valor de: "+patternUtil.textCopy());
		
		System.out.println("***********************Se sale de oracle*************************");
		
		p.cerrarApplet();
		u.quit();
				
	}
	
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestDescription() {
	
		return "Validar Configuracion de MEANING para depuracion BD EBS mediante aplicativo Oracle test";
	}
	
	@Override
	public String setTestDesigner() {
		return "Mónica Soto";
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_002_Validar_Configuracion_de_MEANING_para_depuracion_BD_EBS_mediante_Aplicativo_Oracle_test";
	}
	
	@Override
	public String setTestInstanceID() {
		return null;
	}
	
	@Override
	public String setPrerequisites() {
		return null;
	}
	
	@AfterMethod
	public void afterMethod() {
	
	}
}
