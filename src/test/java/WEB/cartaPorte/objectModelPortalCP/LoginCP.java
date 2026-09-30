package WEB.cartaPorte.objectModelPortalCP;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class LoginCP {

	SeleniumUtil u;
	TestCase testCase;
	String url;
	private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);
	
	
	
	public LoginCP(SeleniumUtil u, TestCase testCase, String url) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.url = url;
	}
	
	 
	
	/*By userField = By.xpath("//input[@id='login:userName']");
	By passwordField = By.xpath("//input[@id='login:userPass']");
	
	By welcomeText= By.xpath("//h2[@class='mtop10']");
	
	By buttonLogin = By.xpath("//a[@id='login:loginButton']");*/
	/*
	 * Portal Login
	 ******************************************************************************************/
	By welcomeText= By.xpath("//h2[@class='mtop10']");
	By xmlUser = By.xpath("//*[@id=\"login:userName\"]");
	By xmlPassword = By.xpath("//*[@id=\"login:userPass\"]");
	By xmlLogginButton = By.xpath("//*[@id=\"login:loginButton\"]");
	By xmlNameUser = By.xpath("//*[@id=\"header\"]/div[2]/div[2]");
	/*
	 * Localizadores conexion no privada
	 ******************************************************************************************/
	By xmlMensaje = By.xpath("//*[@id=\"main-message\"]/h1");                      
	By xmlAdvanced = By.xpath("//*[@id=\"details-button\"]");
	By xmlProceed = By.xpath("//*[@id=\"proceed-link\"]");
	//By xmlSuccessLogin = By.xpath("//div[@role='heading']");
	By cartaPorteMenu= By.xpath("/html/body/div/div/form/div/div/div/h1[1]");
	
	/**
	 * Método para obtener url de Portal de aplicaciones
	 * @return url Regresa la url del portal
	 * @author María Dueñas
	 */
	public String getUrl() {
		return url;
	}
	

	/**
	 * Método para encontrar el mensaje de texto de bienvenida de oxxo
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean encontrarMensajeInicio() {
		esperarAqueSeMuestreElemento(welcomeText);
		try {
			if(u.getDriver().findElement(welcomeText).isDisplayed()) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	
	 /**
   * Método para intentar iniciar sesión, si el sistema no permite el acceso se intenta tres veces y luego
   * se recarga la pagina para evitar bloquear el usuario 
   * @param dato Usuario
   * @param dato2 Contraseña
   * @author María Dueñas
   */
	 public void cicloLogin2(String dato, String dato2) {
	    	
	    	Boolean bandera = false;
	    	Integer cont = 0;
	    	boolean captura= true;
	    	do {
	    		
	    		cont += 1;
	    		if (cont < 4) {
	    			
	    			logginSinCaptura(dato, dato2,captura);
	        		captura=false;
	    			try {
	            		u.getDriver().findElement(cartaPorteMenu);
	            		bandera = true;
	            	}catch (Exception e) {
	            		bandera = false;
	            	}
	    		}else {
	    			//u.getDriver().navigate().refresh();
	    			u.getDriver().navigate().to(url);
	    			cont = 0;
	    		}
	    		
	    	}while(!bandera);
	    	
	    	
	    }
	 
	 /**
		 * Método para iniciar sesión, ingresa usuario y contrasñea
		 * @param dato Nombre de usuario
		 * @param dato2 Contraseña
		 * @author María Dueñas
		 */
		public void logginSinCaptura(String dato, String dato2,boolean captura) {
			
			u.highLight(xmlUser);
			u.clear(xmlUser);
			u.sendKeys(xmlUser,dato);
			
			u.highLight(xmlPassword);
			u.sendKeys(xmlPassword,dato2);
			u.highLight(xmlLogginButton);
			if(captura==true) {
				testCase.addScreenShotCurrentStep("capturaCredenciales");
			}
			u.click(xmlLogginButton);

			u.waitForLoadPage();
			
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
		 * Espera hasta que un elemento específico se muestre en la página.
		 * Este método entra en un bucle continuo hasta que el elemento web indicado sea visible en la página.
		 * @param elementoWeb Localizador del elemento web que se espera que se muestre.
		 * @author nellyoliviab@hexaware.com
		 * @date 21/Agosto/2024
		 */
		public void esperarAqueSeMuestreElemento(By elementoWeb) {
			boolean continuar=false;
			do {
				if(u.getDriver().findElement(elementoWeb).isDisplayed()) {
					continuar=true;
				}
			}while(!continuar);
		}
}
