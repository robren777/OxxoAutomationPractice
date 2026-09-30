package WM.EvolucionRetiroSinTarjeta.HU13;

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

public class ATC_FT_002_Validar_en_la_pantalla_Consultas_General_Ledger_existan_los_medios_de_pago_segun_configuracion extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_Validar_en_la_pantalla_Consultas_General_Ledger_existan_los_medios_de_pago_segun_configuracion_test (HashMap<String, String> data)
			throws Exception{

				/**
				 * Plataforma de EBS http://fcrebsqa.femcom.net:8011/OA_HTML/AppsLocalLogin.jsp
				 * Desc:
				 * Validar en la pantalla Consultas General Ledger exitan los medios de pago segun configuracion R_PYIO24 - RST_Hotpay, R_PYIO26 - R_RST_Ftech, R_PYIO49 - DEV_OXPAY y que tenga en el campo configuracion el valor de  WO02
				 * @author Mónica Soto
				 * @date 21/03/2024
				 **/

				/*
		         * VARIABLES
		         */
				String ebsUrl = GlobalVariables.getAvanteServer;
		        String userName = GlobalVariables.get6008201User;
		        String password = GlobalVariables.get6008201Pswd;
		        
		        String superUsuario = data.get("superUsuario");
		        String carpetaConfiguracion = data.get("carpetaConfiguracion");
		        String carpetaSistema = data.get("carpetaSistema");
		        String carpetaGL = data.get("carpetaGL");
		
				testCase.setTest_Description(data.get("id") + data.get("description"));
				testCase.setPrerequisites("Se debe tener acceso al aplicativo de Oracle con la responsabilidad MXOXXO-GLXXXXX-Super Usuario");

				/*
				 * Utilerias
				 *********************************************************************/
				System.setProperty("webdriver.ie.driver","Config\\Drivers\\IE\\Windows\\IEDriverServer32.exe");  
		
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
						"*****************************************PASO 0************************************************");
				addStep("Se ingresa a la página de EBS");
				inicio.login();
				
		        u.hardWait(5);
				testCase.addScreenShotCurrentStep(u, "Portal de EBS");
				
				System.out.println(
						"*****************************************PASO 1************************************************");
				
				addStep("Ingresar a la responsabilidad MXOXXO-GLXXXXX-Super Usuario");
				
		        paginaInicial.clickOnResponsabilidad(superUsuario);
				testCase.addScreenShotCurrentStep(u, "MXOXXO-GLXXXXX-Super Usuario");
				
				System.out.println(
						"*****************************************PASO 2************************************************");
				
				addStep("Seleccionar Configuracion, Seleccionar Sistema y Seleccionar Consultas de General Ledger");
		        paginaInicial.clickOnResponsabilidad(carpetaConfiguracion);
		        paginaInicial.clickOnResponsabilidad(carpetaSistema);
		        
				testCase.addScreenShotCurrentStep(u, "General Ledger");

		        paginaInicial.clickOnResponsabilidad(carpetaGL);

				System.out.println(
						"*****************************************PASO 3************************************************");
				
				addStep("Ejecutar el aplicativo frmservlet.jnlp");
		        patternUtil.runApplet();
				u.hardWait(3);
				testCase.addScreenShotCurrentStep("Aplicativo");
				
				System.out.println(
						"*****************************************PASO 4************************************************");
				
				addStep("Abrir el buscador de la lampara superior izquierda");
				patternUtil.clickPattern("lamparabusqueda", 5);
				testCase.addScreenShotCurrentStep("Buscar");

				System.out.println(
						"*****************************************PASO 5************************************************");
				
				addStep("Buscar XXFC_GL_RST_CONFIG y dar aceptar");
				p.buscarNombreSolicitud("XXFC_GL_RST_CONFIG");
				testCase.addScreenShotCurrentStep("XXFC_GL_RST_CONFIG");

				System.out.println(
						"*****************************************PASO 6************************************************");
				
				addStep("Se abre la pantalla Consultas General Ledger con el tipo XXFC_GL_RST_CONFIG");
				testCase.addTextEvidenceCurrentStep("Se mostrará la pantalla Consultas General Ledger con la informacion de configuración de las formas de pago en donde deberán estar las siguientes formas de pago agregadas: R_PYIO24 - RST_Hotpay, R_PYIO26 - RST_Ftech, R_PYIO49 - DEV_OXPAY. Y se deberá mostrar en descripción que tenga configurado la interfaz WO01, esto para que el proceso de dispersión los tome ya que estarán marcados los registros por la interfaz WO01.");
				testCase.addTextEvidenceCurrentStep("Si es WO01 se asume que son registros que vienen de la tabla réplica de la transaccional. Si no tiene de método de pago WO01, tomará los registros de la TPC..");
				
				for (int i = 0; i <= 16; i++) {
					patternUtil.doubleClickPattern("ocDownArrow", 10);
				}
		        u.hardWait(5);
				testCase.addScreenShotCurrentStep("Formas de pago");

				// ahora cierra la aplicacion
				p.cerrarApplet();
				// cierra los navegadores
				u.quit();
			}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {

		return "Validar en la pantalla Consultas General Ledger exitan los medios de pago segun configuracion R_PYIO24 - RST_Hotpay, R_PYIO26 - R_RST_Ftech, R_PYIO49 - DEV_OXPAY y que tenga en el campo configuracion el valor de  WO021";
	}

	@Override
	public String setTestDesigner() {
		return "Mónica Soto";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_Validar_en_la_pantalla_Consultas_General_Ledger_existan_los_medios_de_pago_segun_configuracion_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Se debe tener acceso al aplicativo de Oracle con la responsabilidad MXOXXO-GLXXXXX-Super Usuario";
	}

	@AfterMethod
	public void afterMethod() {

	}
	
}
