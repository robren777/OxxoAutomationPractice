package WEB.cnt.om;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Captura_DatosGrales_Tda_CNT {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;

	/**
	 * Localizadores y métodos del Menú Principal o Home del portal Catalogo
	 * Nacional de Tiendas
	 ******************************************************************************************/
	/*
	 * Localizadores login portal CNT login
	 *****************************************************************************************/
	By user = By.xpath("//input[@name=\"p_user\"]");
	By pass = By.xpath("//input[@name=\"p_pass\"]");
	By CONTINUAR = By.xpath("//input[@value=\"CONTINUAR\"]");

	/*
	 * Localizadores login portal CNT elegir Plaza
	 *****************************************************************************************/
	By plazas = By.xpath("//select[@name=\"p_cr_plaza\"]");
	By CONTINUAR2 = By.xpath("//input[@name=\"CONTINUAR\"]");
	/*
	 * Localizadores login portal CNT elegir Distrito, Tienda
	 *****************************************************************************************/
	By Distrito = By.xpath("//select[@name=\"vid_Campos1\"]");
	By Tienda = By.xpath("//select[@name=\"p_cr_tienda\"]");

	/*
	 * Localizadores login portal CNT tienda ventana emergente
	 *****************************************************************************************/
	
	
	By TiendaE = By.xpath("//*");
//	By TiendaE = By.xpath("//*[text()=\"Seleccionar Tienda\"]");
	/*
	 * Localizadores contenedor principal
	 *****************************************************************************************/

	By xmlContainer = By.xpath("//*[@id=\"content-nav\"]");
	By xmlPEP = By.xpath("//*[@id=\"tituloInicio\"]");
	
	By DatosGrales = By.xpath("/html/body/center/a[2]");
	

	/**
	 * M�todo para iniciar sesi�n, ingresa usuario y contras�ea
	 * 
	 * @param dato  Nombre de usuario
	 * @param dato2 Contrase�a
	 * @author Mar�a Due�as
	 */
	public void logginInventarioEspacios(String dato, String dato2) {

		u.highLight(user);
		u.sendKeys(user, dato);

		u.highLight(pass);
		u.sendKeys(pass, dato2);
		u.highLight(CONTINUAR);
		testCase.addScreenShotCurrentStep(u, "Login");
		u.click(CONTINUAR);
		u.waitForLoadPage();
		testCase.addScreenShotCurrentStep(u, "Resultado");

	}

	public void clickSeleccionarPlaza(String plazaE) {
		u.highLight(plazas);
		Select plazasS = new Select(u.getDriver().findElement(plazas));
		plazasS.selectByVisibleText(plazaE);
		testCase.addScreenShotCurrentStep(u, "Plaza");
		u.click(CONTINUAR);
	}

	public void clickDatosGrales() {
		
		u.highLight(DatosGrales);
		u.click(DatosGrales);
		
	}

	
	
	
	
	
	/**
	 * Constructor
	 ******************************************************************************************/

	public Captura_DatosGrales_Tda_CNT(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}

	
		

	/*
	 * METODOS - Adicionales En este apartado estaran los métodos adicionales que
	 * podemos utilizar en otros metodos, como ejemplo agrego el método que valida
	 * si un elemento existe y regresa true o false
	 ******************************************************************************************/

	public boolean existElement(By element) {

		boolean exist = false;

		try {
			u.getDriver().findElement(element);
			exist = true;
		} catch (Exception e) {
			exist = false;
		}

		return exist;

	}

}
