package WM.EvolucionRetiroSinTarjeta.HU07;

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

public class ATC_FT_014_Validar_que_al_efectuar_el_proceso_de_dispersion_se_genere_correctamente_una_Poliza_Plaza extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_014_Validar_que_al_efectuar_el_proceso_de_dispersion_se_genere_correctamente_una_Poliza_Plaza_test(HashMap<String, String> data)
			
			throws Exception{

				/**
				 * Plataforma de EBS http://fcrebsqa.femcom.net:8011/OA_HTML/AppsLocalLogin.jsp
				 * Desc:
				 * Validar que al efectuar el proceso de dispersion, se genere correctamente una Poliza Plaza
				 * @author Mónica Soto
				 * @date 06/03/2024
				 **/

				/*
		         * VARIABLES
		         */
				String ebsUrl = GlobalVariables.getAvanteServer;
		        String userName = GlobalVariables.get6008201User;
		        String password = GlobalVariables.get6008201Pswd;
		        
		        String superUsuario = data.get("superUsuario");
		        String carpetaAsientos = data.get("carpetaAsientos");
		        String carpetaEnter = data.get("carpetaEnter");
		        String fechaConsulta = data.get("fechaConsulta");
		        
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
						"*****************************************PASO 1************************************************");
				addStep("Se ingresa a la página de EBS");
				
				inicio.login();
										
				testCase.addScreenShotCurrentStep(u, "Portal de EBS");
				
				System.out.println(
						"*****************************************PASO 2************************************************");
				
				addStep("Ingresar a la responsabilidad MXOXXO-GLXXXXX-Super Usuario");
				
		        paginaInicial.clickOnResponsabilidad(superUsuario);
				testCase.addScreenShotCurrentStep("MXOXXO-GLXXXXX-Super Usuario");
		
				System.out.println(
						"*****************************************PASO 3************************************************");
				
				addStep("Ingresar a la opcion Asientos: Enter");
		        paginaInicial.clickOnResponsabilidad(carpetaAsientos);
		        paginaInicial.clickOnResponsabilidad(carpetaEnter);
		        		
				testCase.addScreenShotCurrentStep("Enter");
		        patternUtil.runApplet();
		        
		        
				System.out.println(
						"*****************************************PASO 4************************************************");
				
				addStep("Hacer la busqueda de la poliza generada");
		        //El  hardwait nos ayudará a cargar el aplicativo
		        u.hardWait(5);
				testCase.addScreenShotCurrentStep("Búsqueda");
				p.nTAB(4);

				p.soloIngresarCampo(fechaConsulta);
				patternUtil.clickPattern("encontrar3", 5);

				patternUtil.clickWhileExist("flechaArriba","estadoLoteVacio");

				testCase.addScreenShotCurrentStep("Polizas");
				
				System.out.println(
						"*****************************************PASO 5************************************************");
				
				addStep("Seleccionar la poliza, clic boton Revisar Asiento");
				patternUtil.clickPattern("revisarAsiento", 5);
				testCase.addScreenShotCurrentStep("Asiento");
				
				System.out.println(
						"*****************************************PASO 6************************************************");
		
				addStep("Validar que la cuenta contable es correcta");

				u.hardWait(5);
				testCase.addTextEvidenceCurrentStep("- Cargo 19EC0007 por el total del retiro+comisiones+iva\r\n"
						+ "- Abono 15TS0116 por cada importe de retiro\r\n"
						+ "- Abono 51OI0116(por tienda) por comision del retiro (24.93)\r\n"
						+ "- Abono 45DA0413 por IVA trasladado de la comisión 16%");

				testCase.addScreenShotCurrentStep("Valores de cuenta contable");
				
				// ahora cierra la aplicacion
				u.hardWait(2);
				p.cerrarApplet();
				// cierra los navegadores
				u.quit();
			}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestDescription() {
	
		return "Validar que al efectuar el proceso de dispersion, se genere correctamente una Poliza Plaza";
	}
	
	@Override
	public String setTestDesigner() {
		return "Mónica Soto";
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_014_Validar_que_al_efectuar_el_proceso_de_dispersion_se_genere_correctamente_una_Poliza_Plaza_test";
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
