package WEB.cnt.om;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.support.ui.Select;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Planogramacion {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;

	/**
	 * Localizadores y métodos del Menú Principal o Home del portal Catalogo
	 * Nacional de Tiendas
	 ******************************************************************************************/
	/*
	 * Localizadores login portal CNT Formato y Entorno
	 */

	By TiendaE = By.xpath("//*[text()=\"Seleccionar Tienda\"]");
	// By DatosOp= By.xpath("//*[@target=\"framedatos\"][2]");

	/*
	 * Localizadores login portal CNT Captura de Datos Menu >Formato y Entorno
	 */

	By FyE = By.xpath("//*[@target=\"framedatos\"][4]");
	By TamTienda = By.xpath("//select[@name=\"p_lid_tamanio_tienda\"]");
	By Diferenciador = By.xpath("//select[@name=\"p_concepto_diferenciador_id\"]");
	By Segmento = By.xpath("//select[@name=\"p_lid_segmento\"]");
	By Ubi = By.xpath("//select[@name=\"p_lid_ubicacion_tienda\"]");
	By TiendaEspejo = By.xpath("//select[@name=\"p_retek_like_store\"]");
	By TiendaSister = By.xpath("//select[@name=\"p_retek_sister_store\"]");
	By Porcentaje = By.xpath("//input[@name=\"p_retek_por_ajuste_venta\"]");
	By NivelSocioE = By.xpath("//select[@name=\"p_lid_nivel_socioeconomico\"]");
	By ZonaEco = By.xpath("//select[@name=\"p_lid_zona_economica\"]");

	By Entorno = By.xpath("//*[contains(@href,'p_subopcion=2')]");
	By Vialidad = By.xpath("//select[@name=\"p_lid_tipo_vialidad\"]");
	By CamRef = By.xpath("//input[@name=\"p_lid_referencia_ubicacion\"]");

	By frame = By.xpath("//frame[contains(@src,'app')]");
	By framed = By.xpath("//frame[contains(@src,'/tiendas')][@name=\"framedatos\"]");// frameoper
	/*
	 * Localizadores Formulario Datos de OP
	 *****************************************************************************************/

	By cot = By.xpath("//select[@name=\"p_lid_caracteristica_operacion\"]");

	By xmlContainer = By.xpath("//*[@id=\"content-nav\"]");
	By xmlPEP = By.xpath("//*[@id=\"tituloInicio\"]");

	/* localizadores planogramacion */
	By Plano = By.xpath("//*[@target=\"framedatos\"][5]");
	By Estacionamiento = By.xpath("//input[@name=\"p_area_estacionamiento\"]");
	By Cajones = By.xpath("//select[@name=\"p_lid_cajones_estacionamiento\"]");
	By BtnActualizar = By.xpath("//input[@value=\"Actualizar\"]");
	
	
	//notificacion
	By notificacion = By.xpath("//*[text()='notificacion']");
	
	//congelador cerveza p_tiene_congelador_cerveza
	By Congelador = By.xpath("//select[@name=\"p_tiene_congelador_cerveza\"]");
	//poste p_tiene_anuncio_poste
	By Poste = By.xpath("//select[@name=\"p_tiene_anuncio_poste\"]");
	//porta abnner p_tiene_porta_banner_ext
	By banner = By.xpath("//select[@name=\"p_tiene_porta_banner_ext\"]");
	/**
	 * Metodo para validar dar clic en planogramacion
	 * 
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return
	 */
	public void Plano() {
	//	u.getDriver().switchTo().frame("framemenu");
		String winHandleBefore = u.getDriver().getWindowHandle();

		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

		u.hardWait(4);
		u.getDriver().manage().window().maximize();
		u.highLight(frame);
		u.getDriver().switchTo().frame("framemenu");

		u.highLight(Plano);
		testCase.addScreenShotCurrentStep(u, "Plano");
		u.click(Plano);
		u.getDriver().switchTo().window(winHandleBefore);
		u.waitForLoadPage();
		testCase.addScreenShotCurrentStep(u, "Plano2");
		
	}
	/**
	 * Metodo para poner metros en el estacionamiento del oxxo
	 * 
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return
	 */
	public void Estacionamiento(String Esta) {
		String si = "SI";
		String winHandleBefore = u.getDriver().getWindowHandle();

		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

//		u.waitForLoadPage();
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
//		u.highLight(TiendaE);

		u.hardWait(10);
		u.getDriver().manage().window().maximize();
		u.highLight(framed);
		u.getDriver().switchTo().frame("framedatos");
		
//		u.getDriver().switchTo().window(winHandleBefore);
//		u.waitForLoadPage();
		MetodosExtra ME = new MetodosExtra(u, testCase);
		ME.scrollToElement(Estacionamiento);
		u.highLight(Estacionamiento);
		u.clear(Estacionamiento);
		u.sendKeys(Estacionamiento, Esta);
		testCase.addScreenShotCurrentStep(u, "Estacionamiento");
	
	}
	/**
	 * Metodo para seleccionar congelador  para cerveza en NO
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return
	 */
	public void conge() {
		String si = "SI";
		String winHandleBefore = u.getDriver().getWindowHandle();

		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

//		u.waitForLoadPage();
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
//		u.highLight(TiendaE);

		u.hardWait(10);
		u.getDriver().manage().window().maximize();
		u.highLight(framed);
		u.getDriver().switchTo().frame("framedatos");
		
		MetodosExtra ME = new MetodosExtra(u, testCase);
		ME.scrollToElement(Congelador);
		u.highLight(Congelador);
		u.click(Congelador);

        Select tama1 = new Select(u.getDriver().findElement(Congelador));
        tama1.selectByValue("N");
		testCase.addScreenShotCurrentStep(u, "Cong");
	
		
	}
	/**
	 * Metodo para seleccionar poste en NO
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return
	 */
	public void poste() {
		MetodosExtra ME = new MetodosExtra(u, testCase);
		ME.scrollToElement(Poste);
     u.highLight(Poste);
		u.click(Poste);

        Select tama1 = new Select(u.getDriver().findElement(Poste));
        tama1.selectByValue("N");
		testCase.addScreenShotCurrentStep(u, "Congposte");
	
		
	}
	/**
	 * Metodo para seleccionar banner en NO
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return
	 */
	public void banner() {
		MetodosExtra ME = new MetodosExtra(u, testCase);
		ME.scrollToElement(banner);
		u.highLight(banner);
		u.click(banner);

        Select tama1 = new Select(u.getDriver().findElement(banner));
        tama1.selectByValue("N");
		testCase.addScreenShotCurrentStep(u, "banner");
	
		
	}
	/**
	 * Metodo para seleccionar del select de cajones
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return
	 */
	public void CajonesEstacionamiento(String cajon) {
		MetodosExtra ME = new MetodosExtra(u, testCase);
		ME.scrollToElement(Cajones);
		u.highLight(Cajones).isEnabled();
		u.click(Cajones);

        Select tama1 = new Select(u.getDriver().findElement(Cajones));
        tama1.selectByVisibleText(cajon);
		testCase.addScreenShotCurrentStep(u, "Cajones");
	
		
	}
	
	/**
	 * Metodo para dar clic en boton actualizar 
	 * @author Baltazar Lugo
	 * @param  NA
	 * @return  NA
	 */
	public void clickBotonActualizar() {
		MetodosExtra ME = new MetodosExtra(u, testCase);
		ME.scrollToElement(BtnActualizar);
		u.highLight(BtnActualizar);
		testCase.addScreenShotCurrentStep(u, "DatosFORMATOAC");
		u.click(BtnActualizar);
////		u.waitForLoadPage();
//

		u.hardWait(5);
//		testCase.addScreenShotCurrentStep(u, "Respuesta_POP_PERMISOS_Y_CONTRATOS_ha_sido_grabada");
	String ventanaE = u.getDriver().switchTo().alert().getText();
	System.out.println("Texto en mensaje de alerta" + ventanaE);
//
	String str2 = "La informacion de \"PLANOGRAMACION\" ha sido grabada.";
	if (ventanaE.equals(str2)) {
////			testCase.addScreenShotCurrentStep(u, "RespuestaPOP");
//
		u.getDriver().switchTo().alert().accept();
	} else {			System.out.println("Fallo Datos de La informacion de \"PLANOGRAMACION\" ha sido grabada.");
	}
	u.hardWait(4);
	}
	/**
	 * Metodo para dar clic en Notificacion
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return
	 */
	public void Notificacion() {
	//	u.getDriver().switchTo().frame("framemenu");
		String winHandleBefore = u.getDriver().getWindowHandle();

		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

		u.hardWait(4);
		u.getDriver().manage().window().maximize();
		u.highLight(frame);
		u.getDriver().switchTo().frame("framemenu");

		u.highLight(notificacion);
		testCase.addScreenShotCurrentStep(u, "notificacion");

		u.click(notificacion);
		testCase.addScreenShotCurrentStep("foto2");
//		u.getDriver().switchTo().window(winHandleBefore);
//		u.waitForLoadPage();
//		testCase.addScreenShotCurrentStep(u, "Plano2");
		
	}
	
	/**
	 * Metodo para dar clic en alerts despues de notificacion
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return
	 */
	public boolean NotificacionAlert() {
		boolean existenoti=true;
		u.hardWait(4);
//	//	u.getDriver().switchTo().frame("framemenu")
////		WebDriverWait w = new WebDriverWait(u.getDriver(),20);
		
		int x=0;
//		
//		WebDriverWait w = new WebDriverWait(u.getDriver(),20);
do {
	u.hardWait(10);
	if(isAlertPresent()==false) {
		System.out.println("Alert not exists");
	}else { if(isAlertPresent()==true) {
		System.out.println("Alert exists");
		testCase.addScreenShotCurrentStep("foto3");
		u.getDriver().switchTo().alert().accept();
	}
	}
	x++;

//	for (int i = 0; i < 9; i++) {
//			if ((ExpectedConditions.alertIsPresent()) != null) {
//				testCase.addScreenShotCurrentStep("foto3");
//				u.getDriver().switchTo().alert().accept();
//				i = i + 1;
//				System.out.println("for"+i);
//				
//			}
//		}
////			x=x+1;
//	System.out.print("x: "+x);

} while (x<=6);

u.hardWait(15);
existenoti=me.existElement(notificacion);

return existenoti;

//testCase.addScreenShotCurrentStep("foto3");

//		u.click(Plano);
//		u.getDriver().switchTo().window(winHandleBefore);
//		u.waitForLoadPage();
//		testCase.addScreenShotCurrentStep(u, "Plano2");
		
	}
	public boolean isAlertPresent() 
	{ 
	    try 
	    { 
	    	u.getDriver().switchTo().alert();
	        return true; 
	    }   // try 
	    catch (NoAlertPresentException Ex) 
	    { 
	        return false; 
	    }   // catch 
	}   // isAlertPresent()
		
	/**
	 * Constructor
	 ******************************************************************************************/

	public Planogramacion(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
		
	}

	/*
	 * METODOS - Acceso correcto a Inventario de espacios Publicitarios V2
	 ******************************************************************************************/

}