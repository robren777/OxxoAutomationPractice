package WEB.finiquitoTiendas.om;

import java.time.Duration;
import org.openqa.selenium.By;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class pantalla_instructivo {
		
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	
	String url = "https://10.182.92.88:8443/AccessControl/pages/login_form.jsf";
	
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
	
	By xmlFiniquitotiendas = By.xpath("//*[@id=\"homeSubview:j_idt61\"]/div/div[2]/div[1]/div[1]/div[2]/div/ul/li[7]/a");
	
	/*
	 * Localizadores Administracion de finiquitos
	 ******************************************************************************************/
	
	By xmlFiniquito = By.xpath("//*[@id=\"e0_1i\"]");
	By xmlConsultarFiniquitos = By.xpath("//*[@id=\"e0_2i\"]");
	
	/*
	 * Localizadores Consultar finiquitos 
	 ******************************************************************************************/
	
	By xmlPlaza = By.xpath("//*[@id=\"plazaCmb\"]");
	By xmlDistrito = By.xpath("//*[@id=\"distritoCmb\"]");
	By xmlRadioTienda = By.xpath("//*[@id=\"tblFiltros\"]/tbody/tr[6]/td[1]/input");
	By xmlTienda = By.xpath("//*[@id=\"tiendaCmb\"]");
	By xmlBtnBuscarF = By.xpath("//*[@id=\"tblFiltros\"]/tbody/tr[8]/td[2]/input");
	By xmlBtnConsultarF = By.xpath("//*[@id=\"tblResultados\"]/tbody/tr[4]/td/input");
	
	/**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase MenuPrincipalIEP
	 * @author Yair Lizarraga
	 * @param u (SeleniumUtil): objeto de la clase 
	 * @param testCase (testCase): objeto de la clase
	 */
	
	public pantalla_instructivo(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
}
	
	/**
	 * Metodo para dar click en la opcion Finiquito en tienda
	 * @author Yair Lizarraga
	 */
	 public void clickContainer() {
		 u.highLight(xmlFiniquitotiendas);
		 u.click(xmlFiniquitotiendas);	}
	 
		/**
		 *  Metodo para posicionar el mouse sobre la barra que despliega el menu
		 * @author Yair Lizarraga
		 */
    public void MostrarFiniquito() {
    	    	me.explicitWaitToBeInvisible(Duration.ofSeconds(20));
    	    	do { 		
    	    		u.mouseOver(xmlFiniquito);		
    	    	}while (u.waitWebElement(xmlFiniquito, 5, 5).isEnabled()==false); 	
    	    	testCase.addScreenShotCurrentStep(u, "clickMostrarFiniquito");
    }
    /**
	 * Metodo para dar click en la opcion consultar finiquitos en el menu administracion de finiquitos
	 * @author Yair Lizarraga
	 */
    
    public void clickConsultarFiniquitos() {
    	
    	u.highLight(xmlConsultarFiniquitos);
    	u.click(xmlConsultarFiniquitos);
    	testCase.addScreenShotCurrentStep(u, "clickConsultarFiniquitos");
    	u.unhighLight(xmlConsultarFiniquitos);
    	
    }
    
    /**
   	 * Metodo para dar click en la opcion instructivo en el menu administracion de finiquitos
   	 * @author Andrea Cano
   	 */
 public void clickAdministracionEInstructivo() {
    	
    	u.highLight(By.xpath("//*[@id=\"e0_6i\"]"));
    	u.mouseOver(By.xpath("//*[@id=\"e0_6i\"]"));
    	//u.unhighLight(By.xpath("//*[@id=\\\"e0_6i\\\"]"));
    	u.hardWait(1);
    	u.highLight(By.xpath("//*[@id=\"e0_8i\"]"));
    	testCase.addScreenShotCurrentStep(u, "clickInstructivo");
    	u.click(By.xpath("//*[@id=\"e0_8i\"]"));  	
    	u.unhighLight(By.xpath("//*[@id=\"e0_8i\"]"));
    	
    }
 
 
 /**
	 * Metodo para click btn modificar pantalla instructivo
	 * @author Andrea Cano
	 */
public void clickModificar() {
	
	u.scroll();
	u.highLight(By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table/tbody/tr[5]/td[1]/input"));
	testCase.addScreenShotCurrentStep(u, "clickInstructivo"); 	
 	u.click(By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table/tbody/tr[5]/td[1]/input"));
 	u.unhighLight(By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table/tbody/tr[5]/td[1]/input"));
 	
 }

/**
	 * Metodo para modificar pantalla instructivo
	 * @author Andrea Cano & David Valdez
	 */
public void modificarInstructivo() {

		/*
	u.highLight(By.xpath("/html/body"));  
	u.click(By.xpath("/html/body"));
	u.click(By.xpath("/html/body"));
	//u.click(By.xpath("/html/body/p"));
	u.hardWait(1);
	u.sendKeys(By.xpath("/html/body"), "Prueba enero 20"); //no escribe en la pantalla, no hace modificaciones
	u.hardWait(2); 
	testCase.addScreenShotCurrentStep(u, "click instructivo"); 
	u.unhighLight(By.xpath("/html/body"));
	u.scroll();
	
	*/
	
	u.getDriver().switchTo().frame("caratulaTxt_ifr");
	By elem = By.id("tinymce");
	
	u.scrollToElement(elem);
	u.highLight(elem);
	String texto = u.getText(elem);
	u.clear(elem);
	testCase.addScreenShotCurrentStep(u, "clickbtnGuardar"); 
	u.hardWait(2);
	u.sendKeys(elem, texto);
	testCase.addScreenShotCurrentStep(u, "clickbtnGuardar"); 
	u.getDriver().switchTo().parentFrame();
	
	//guardar
	u.hardWait(2);
	u.highLight(By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table/tbody/tr[5]/td[1]/input[2]"));
	testCase.addScreenShotCurrentStep(u, "clickbtnGuardar"); 	
	u.click(By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table/tbody/tr[5]/td[1]/input[2]"));
	u.hardWait(5);
	
	//u.close();
	
}

}