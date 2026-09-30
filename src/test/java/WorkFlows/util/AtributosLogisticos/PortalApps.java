package WorkFlows.util.AtributosLogisticos;

import org.openqa.selenium.By;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class PortalApps {
	
	SeleniumUtil u;
	TestCase testCase;
	String user, password;
	    
	/*
	 * Localizadores conexion no privada
	 ******************************************************************************************/
	By xmlMensaje = By.xpath("//*[@id=\"main-message\"]/h1");
	By xmlAdvanced = By.xpath("//*[@id=\"details-button\"]");
	By xmlProceed = By.xpath("//*[@id=\"proceed-link\"]");
	
	/*
	 * Localizadores Inicio de sesion Inventarios Espacios
	 ******************************************************************************************/
	
	By xmlUser = By.xpath("//*[@id=\"login:userName\"]");
	By xmlPassword = By.xpath("//*[@id=\"login:userPass\"]");
	By xmlLogginButton = By.xpath("//*[@id=\"login:loginButton\"]");
	By xmlUserPassInv = By.className("mensaje_error");
	
	By xmlNameUser = By.xpath("//*[@id=\"header\"]/div[2]/div[2]");
	
	/*
	 * Localizadores Menu Principal Portal Aplicaciones
	 ******************************************************************************************/
	
	By xmlContainer = By.xpath("/html/body/div/div");
	By xmlPortalIEP = By.xpath("//a[contains(text(),'Inventario de Espacios Publicitarios V2')]");
	By xmlPortalSARv3 = By.xpath("//a[contains(text(),'Sistema de administracion de riesgos V3')]");
	
	By buttonProveedores = By.cssSelector("div.envelope:nth-child(5) > div:nth-child(2) > div:nth-child(1) > ul:nth-child(1) > li:nth-child(1) > a:nth-child(1)");
	By buttonHome = By.cssSelector(".espacio");
	
	/**
	 * Constructor
	 ******************************************************************************************/
	
	public PortalApps(SeleniumUtil u, TestCase testCase, String user, String password) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.user = user;
		this.password = password;
		
	}
	
	/*
	 * METODOS 
	 ******************************************************************************************/
	
	/**
	 * Método para obtener url de Portal de aplicaciones
	 * @return url Regresa la url del portal
	 * @author María Dueñas
	 *
	public String getUrl() {
		return url;
	}
	*/
	 
	/**
	 * Método para obtener xpath de mensaje de "Usuario y/o xontraseña invalido"
	 * @return xpath
	 * @author María Dueñas
	 */
	public By getxmlUserPassInv() {
		
		return xmlUserPassInv;
		
	}

	/**
	 * Método para saltar ventana de "Conexión no privada"
	 * @author María Dueñas
	 */
	public void proceedToPortal() {
    	
    	Boolean bandera = false;
    	try {
    		u.getDriver().findElement(xmlMensaje);
    		bandera = true;
    	}catch (Exception e) {
    		bandera = false;
    	}
    	
    	if (bandera) {
    		u.click(xmlAdvanced);
        	u.click(xmlProceed);
    	}
    	
    }

	/**
	 * Método para iniciar sesión, ingresa usuario y contrasñea
	 * @param dato Nombre de usuario
	 * @param dato2 Contraseña
	 * @author María Dueñas
	 */
	public void logginAtributosLogisticos() {	
		
		try {
			testCase.addTextEvidenceCurrentStep("Ingresar Usuario y Contrase�a");
			u.highLight(xmlUser);
			u.sendKeys(xmlUser,user);
			
			u.highLight(xmlPassword);
			u.sendKeys(xmlPassword, password);
			u.highLight(xmlLogginButton);
			testCase.addScreenShotCurrentStep(u, "inputs");
			u.click(xmlLogginButton);
			u.waitForLoadPage();
			testCase.addScreenShotCurrentStep(u, "fin");
			
			u.waitWebElement( buttonProveedores, 120, 2);//Sustituir proveedores por cualquier input que aparezca en pantalla.
			u.scrollToElement(buttonProveedores);
			
			testCase.addTextEvidenceCurrentStep("Seleccionar m�dulo de Gesti�n de Proveedores");
			u.highLight(buttonProveedores);
			testCase.addScreenShotCurrentStep("");
			u.click(buttonProveedores);
			
			u.waitWebElement( buttonHome, 120, 2);
			testCase.addScreenShotCurrentStep(u, "inputs");
		}
		catch (Exception e) {
			System.out.println(" -> " + e);
            throw new AssertionError("Error al iniciar sesi�n---> "+e);
		}
		
	}
    
    /**
     * Método para intentar iniciar sesión, si el sistema no permite el acceso se intenta tres veces y luego
     * se recarga la pagina para evitar bloquear el usuario 
     * @param dato Usuario
     * @param dato2 Contraseña
     * @author María Dueñas
     */
	 public void cicloLogin(String dato, String dato2) {
	    	
	    	Boolean bandera = false;
	    	Integer cont = 0;
	    	
	    	do {
	    		
	    		cont += 1;
	    		if (cont < 4) {
	    			logginAtributosLogisticos();
	        		try {
	            		u.getDriver().findElement(xmlNameUser);
	            		bandera = true;
	            	}catch (Exception e) {
	            		bandera = false;
	            	}
	    		}else {
	    			u.getDriver().navigate().refresh();
	    			cont = 0;
	    		}
	    		
	    	}while(!bandera);
	    	
	    }
    
	 
    
    
}
