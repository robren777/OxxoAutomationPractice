package WorkFlows.util.PromoBara;

import java.io.File;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Pattern;

import WorkFlows.util.ultimus.pomUltimus;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;


public class loginUltimus {
	

	SeleniumUtil u;
	TestCase testCase;
	String user;
	private WebDriverWait wait;

	
	By btnPromociones = By.cssSelector(".sf-with-ul");

	
	/// WEB ELEMENTS ULTIMUS SIKULI - MENU VISTAS
	SikuliUtil sikuli = new SikuliUtil();
	String pathImages = sikuli.PatternPath("WorkFlows"+File.separator+"Ultimus");
	Pattern iniciarSelectedImg2 = sikuli.PatternImage(pathImages + "iniciarSelected2.png");
	Pattern promoBara = sikuli.PatternImage(pathImages + "promoBara.png");
	Pattern proces = sikuli.PatternImage(pathImages + "procesando.png");

	By panelProcess = By.xpath("//div[text()=\"Procesando. por favor espere…\"]");

	
	public loginUltimus (SeleniumUtil u, TestCase testCase, String user ) {
		this.u = u;
		this.testCase = testCase;
		this.user = user;


	}
	
	/**
	 * Inicia sesión en promoBara mediante ultimus con
	 * usuario category
	 * @param userUltimus
	 */
	public void login_Ultimus (String userUltimus) {
		
		try {
			
			pomUltimus pom = new pomUltimus(u);

	    	testCase.addTextEvidenceCurrentStep("Inicia Sesion en Ultimus");
			pom.loginUltimus(user, "");
			testCase.addScreenShotCurrentStep(u, "");
				
			testCase.addTextEvidenceCurrentStep("Selecciona Iniciar");
			
			sikuli.screen.wait(iniciarSelectedImg2, 20).click();
			loadingUltimus();


			testCase.addTextEvidenceCurrentStep("Inicia sesion en PromoBara");
			sikuli.screen.wait(promoBara, 50).doubleClick();
			
			testCase.addTextEvidenceCurrentStep("Ingresa credenciales");
			pom.ingresarCredencialesPortal("6006015", "Comercio.42");
			
			cambiarVentana(1);
			u.waitWebElement(btnPromociones, 120, 1);
			testCase.addScreenShotCurrentStep(u, "");
			
		}catch (Exception e) {
			System.out.println("Error---> " + e);

			throw new AssertionError("Error al iniciar sesion");
		}
		

		
	}

	
	/**
	 * Cambia el foco a una nueva ventana 
	 * para continuar con el flujo
	 * @param pantalla
	 */
	 public  void cambiarVentana(int pantalla) {
	        try {
	            ArrayList<String> tabs = new ArrayList<String> (u.getDriver().getWindowHandles());
	            u.getDriver().switchTo().window(tabs.get(pantalla));
	        } catch (Exception e) {
	            
	        }
	    }
	 
	 /**
	  * Inicia sesión en PromoBara con usuario Administrador
	  */
	 public void loginAdmin(String userAdmin) {
		pomUltimus pom = new pomUltimus(u);

		 testCase.addBoldTextEvidenceCurrentStep("Inicia Sesion en Ultimus con usuario Categoria");
			pom.loginUltimus(userAdmin, "");
			
			testCase.addBoldTextEvidenceCurrentStep("Selecciona iniciar");
			pom.seleccionarVista("INICIAR");
			
			testCase.addBoldTextEvidenceCurrentStep("Selecciona PromoBara");
			pom.openPromoBara("6006015", "Comercio.42");
	 }
	 
	 /**
		 * Verifica que exista el modal de procesando y espera a que desaparezca.
		 * 
		 * @author Esmeralda Garcia Torres
		 */
		public void loadingUltimus() {
			
			try {
				u.hardWait(1);
				while (sikuli.screen.exists(proces, 0)!=null) {
					System.out.println("Cargando...");
					
					u.hardWait(2);
					
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}



}
