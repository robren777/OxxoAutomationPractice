package WorkFlows.util.SipreBara;

import java.io.File;

import org.openqa.selenium.By;
import org.sikuli.script.Pattern;

import WorkFlows.util.ultimus.pomUltimus;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class pom_inicioSesionUltimusPega {

	SeleniumUtil u;
	TestCase testCase;
	String user;
	SikuliUtil sikuli = new SikuliUtil();
	String pathImages = sikuli.PatternPath("WorkFlows"+File.separator+"Ultimus");
	Pattern scrollDown = sikuli.PatternImage(pathImages + "scrollDown.png");

	
	
	By btnInicio = By.cssSelector("#home");
	
	public pom_inicioSesionUltimusPega (SeleniumUtil u, TestCase testCase, String user ) {
		this.u = u;
		this.testCase = testCase;
		this.user = user;


	}
	
/**
 * Inicia sesión en Ultimus o Pega según lo requiera el caso de prueba 
 * @author alicia.ceja
 * @param op
 * @param userSIPRE
 * @param psSipre
 */
	public void inicioSesion(String op, String userSIPRE, String psSipre) {
		By iniciar= By.cssSelector("#WebSplitter1tmpl0UltraWebListbar2ctl00MyViewTree_20_1");
		
		try {
        	pomUltimus pom = new pomUltimus(u);
	        switch (op) {
	        case "PEGA":
	        pom_Login_PEGA pomPega = new pom_Login_PEGA(u, testCase, user);
	        pomPega.iniciaSesion();
    		u.waitWebElement(btnInicio, 120, 1);
    		u.hardWait(2);
    		testCase.addScreenShotCurrentStep(u, "");
	        break;
	            
	        case "ULTIMUS":
	        	testCase.addTextEvidenceCurrentStep("Inicia Sesion en Ultimus");
	    		pom.loginUltimus(user, "");
	    		testCase.addScreenShotCurrentStep(u, "");
	    		u.hardWait(3);
	    		
	    		testCase.addTextEvidenceCurrentStep("Selecciona Iniciar");
	    		pom.seleccionarVista("INICIAR");
	    		testCase.addScreenShotCurrentStep(u, "");
	    		testCase.addTextEvidenceCurrentStep("Inicia sesion en SIPRE_BARA");
	    		
	    		pom.openSipreBara(userSIPRE, psSipre);
	    		u.waitWebElement(btnInicio, 120, 1);
	    		u.hardWait(2);
	    		testCase.addScreenShotCurrentStep(u, "");
	            break;
	            
	        case "PEGA_Cat_Plaza":
	            pom_Login_PEGA pomPega1 = new pom_Login_PEGA(u, testCase, user);
		        pomPega1.iniciaSesionCatPla();
		        u.waitWebElement(btnInicio, 120, 1);
	    		u.hardWait(2);
	    		testCase.addScreenShotCurrentStep(u, "");
	    		break;
	    		
	        case "ULTIMUS_SIGUIENTE":
	        	testCase.addTextEvidenceCurrentStep("Inicia Sesion en Ultimus");
	    		pom.loginUltimus(user, "");
	    		testCase.addScreenShotCurrentStep(u, "");
	    			
	    		testCase.addTextEvidenceCurrentStep("Selecciona Iniciar");
	    		
	    		sikuli.screen.wait(scrollDown, 30);
	    		sikuli.screen.click(scrollDown);
	    		
	    		u.hardWait(2);
	    			
	    		
	    		pom.seleccionarVista("INICIAR");
	    		testCase.addScreenShotCurrentStep(u, "");
	    		

	    		testCase.addTextEvidenceCurrentStep("Inicia sesion en SIPRE_BARA");
	    		
	    		pom.openSipreBaraSiguiente(userSIPRE, psSipre);
	    		
	    		u.waitWebElement(btnInicio, 120, 1);
	    		u.hardWait(2);
	    		testCase.addScreenShotCurrentStep(u, "");

		 
		            break;
	            
	        // Case 4
	        // Default case
	        default:
	        	
	        }
			
		}catch(Exception e) {
			System.out.println("Error---> " + e);

			throw new AssertionError("Error al iniciar sesion");

		}
 
	}
	
	

}
