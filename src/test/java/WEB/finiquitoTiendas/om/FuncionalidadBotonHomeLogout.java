package WEB.finiquitoTiendas.om;

import java.io.IOException;
import org.openqa.selenium.By;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class FuncionalidadBotonHomeLogout {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	MetodosExtra m;
	
//	String url = "https://10.182.92.88:8443/AccessControl/pages/login_form.jsf";
	
	/*
	 * Localizadores conexion no privada
	 ******************************************************************************************/
	By xmlMensaje = By.xpath("//*[@id=\"main-message\"]/h1");
	By xmlAdvanced = By.xpath("//*[@id=\"details-button\"]");
	By xmlProceed = By.xpath("//*[@id=\"proceed-link\"]");
	
	/*
	 * Localizadores Inicio de sesion Finiquito en Tiendas
	 ******************************************************************************************/
	
	By xmlUser = By.xpath("//*[@id=\"login:userName\"]");
	By xmlPassword = By.xpath("//*[@id=\"login:userPass\"]");
	By xmlLogginButton = By.xpath("//*[@id=\"login:loginButton\"]");
	By xmlUserPassInv = By.className("mensaje_error");
	By xmlNameUser = By.xpath("//*[@id=\"header\"]/div[2]/div[2]");
	By xmlPortalApli = By.xpath("//*[@id=\"header\"]/div/div/h1");
	
	/*
	 * Localizadores lista de aplicaciones
	 ******************************************************************************************/
	By xmlFiniquitoTiendas = By.xpath("//*[@id=\"homeSubview:j_idt61\"]/div/div[2]/div[1]/div[1]/div[2]/div/ul/li[7]/a");
	
	/*
	 * Localizadores Administracion de Finiquitos
	 ******************************************************************************************/
	By xmlHome = By.xpath("/html/body/table/tbody/tr[1]/td/table/tbody/tr/td/table/tbody/tr/td/table/tbody/tr/td[2]/table/tbody/tr/td[3]/div/a[2]");
	By xmlLogout = By.xpath("/html/body/table/tbody/tr[1]/td/table/tbody/tr/td/table/tbody/tr/td/table/tbody/tr/td[2]/table/tbody/tr/td[3]/div/a[4]");
	
			
	/**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase MenuPrincipalIEP
	 * @author Manuel Cuen
	 * @param u (SeleniumUtil): objeto de la clase 
	 * @param testCase (testCase): objeto de la clase
	 */
	
	public FuncionalidadBotonHomeLogout(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
		m = new MetodosExtra(u, testCase);
	}
	
	/**
	 * @Metodo: Da click en la lista de aplicaciones 'Finiquito en Tiendas'
	 * @author: Manuel Cuen
	 * @param : Ninguno 
	 * @return: Ninguno
	 */
	public void clickFiniquitoTiendas() {
		
		u.highLight(xmlFiniquitoTiendas);
		testCase.addScreenShotCurrentStep(u, "clickFiniquitoTiendas");
		u.click(xmlFiniquitoTiendas);
		testCase.addScreenShotCurrentStep(u, "clickFiniquitoTiendas");
		
	}
	
	/**
	 * @Metodo: Da click en el boton Home
	 * @author: Manuel Cuen
	 * @param : Ninguno 
	 * @return: Ninguno
	 */
	public void clickHome() {
		
		u.highLight(xmlHome);
		testCase.addScreenShotCurrentStep(u, "clickHome");
		u.click(xmlHome);
		testCase.addScreenShotCurrentStep(u, "clickHome");
		
	}
	
	/**
	 * @Metodo: Manda ejecutar el metodo 'Existe Elemento'
	 * @author: Manuel Cuen
	 * @param : Ninguno 
	 * @return: Validacion: regresa true, si encuentra el elemento y falso en caso contrario
	 */
	public Boolean validaEtiquetaPortalApli() throws IOException{
		  u.hardWait(2);
		  Boolean validacion = false;
		  validacion = m.existElement(xmlPortalApli);
		  if(validacion) u.highLight(xmlPortalApli);
		  testCase.addScreenShotCurrentStep(u, "pla");

		return validacion;
	 }
	
	/**
	 * @Metodo: Da click en el boton Logout
	 * @author: Manuel Cuen
	 * @param : Ninguno 
	 * @return: Ninguno
	 */
	 public void clickLogout() {
		
		u.highLight(xmlLogout);
		testCase.addScreenShotCurrentStep(u, "clickLogout");
		u.click(xmlLogout);
		testCase.addScreenShotCurrentStep(u, "clickLogout");
		
	 }
	
		/**
		 * @Metodo: agrega imagen, sin tener que ejecutar un metodo
		 * @author: Manuel Cuen
		 * @param : Ninguno 
		 * @return: Ninguno
		 */
	 public void clickAgregarImagen() {
		testCase.addScreenShotCurrentStep(u, "clickAgregarImagen");
		u.hardWait(2);
	 }

}
