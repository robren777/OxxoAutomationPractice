package WEB.cnt.om;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Datos_Generales {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	WebDriverWait wait;

	/**
	 * Localizadores y métodos del Menú Principal o Home del portal Catalogo
	 * Nacional de Tiendas
	 ******************************************************************************************/
	/*
	 * Localizadores login portal CNT Captura de Datos
	 */

	By TiendaE = By.xpath("//*[text()=\"Seleccionar Tienda\"]");
	// By DatosOp= By.xpath("//*[@target=\"framedatos\"][2]");

	/*
	 * Localizadores login portal CNT Captura de Datos Menu >Datos Generales
	 */

	By DatosGen = By.xpath("//*[@target=\"framedatos\"][1]");
	By DatosOp = By.xpath("//*[@target=\"framedatos\"][2]");
	By CodPostal = By.xpath("//input[@name=\"p_codigopostal\"]");
	By BtnBuscar = By.xpath("//input[@value=\"buscar\"]");
	By IdPunto = By.xpath("//select[@name=\"p_id_punto\"]");
	By CLD = By.xpath("//input[@name=\"p_codigo_area\"]");
	By Telefono = By.xpath("//input[@name=\"p_telefono\"]");
	By Distrito = By.xpath("//select[@name=\"p_retek_region\"]");
	By entreCalles = By.xpath("//input[@name=\"p_entre_calles\"]");
			
	By Sintel = By.xpath("//input[@name=\"p_tel_no_disponible\"]");
	By p_alternativa_tel= By.xpath("//select[@name=\"p_alternativa_tel\"]");
	By p_especifique=By.xpath("//input[@name=\"p_especifique\"]");
	
	
	
	By frame = By.xpath("//frame[contains(@src,'app')]");
	//By frame = By.xpath("//frame[contains(@name,'framemenu')]");
	//By framed = By.xpath("//frame[contains(@src,'/tiendas')][@name=\"framedatos\"]");// frameoper
	By framed = By.xpath("//frame[@name='framedatos']"); //añadido por mí
	//Elementos añadidos por mi
	By aDatosGeneralesMenu = By.xpath("//a[contains(text()='Datos Generales')");
	/*
	 * Localizadores Formulario Datos de OP
	 *****************************************************************************************/

	By cot = By.xpath("//select[@name=\"p_lid_caracteristica_operacion\"]");
	By BtnActualizar = By.xpath("//input[@value=\"Actualizar\"]");

	By xmlContainer = By.xpath("//*[@id=\"content-nav\"]");
	By xmlPEP = By.xpath("//*[@id=\"tituloInicio\"]");

	/**
	 * Metodo para seleccionar el Frame correcto y dar clic a datos Generales
	 * 
	 * @author Baltazar Lugo
	 * @return N/A
	 */
	public void DatosGen() {
		//u.getDriver().switchTo().frame("framemenu");
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

		u.highLight(frame);
		u.getDriver().switchTo().frame("framemenu");
		u.highLight(DatosGen);
		testCase.addScreenShotCurrentStep(u, "DatosGen");
		u.click(DatosGen);

		//u.getDriver().switchTo().window(winHandleBefore); //comentado por mi
		//añadido por mi
		u.waitForLoadPage();
		System.out.println("Sí funciona u.waitForLoadPage(); linea 105");

	}

	public void DatosGenMario() {
		//u.getDriver().switchTo().frame("framemenu");
		String winHandleBefore = u.getDriver().getWindowHandle();

		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}
		System.out.println("Se movio a ventana nueva");
//		u.waitForLoadPage();
		System.out.println("espera 4 segundos");
		u.hardWait(4);
		//u.getDriver().manage().window().maximize();
//		u.highLight(TiendaE);

		//u.highLight(frame);
		//u.getDriver().switchTo().frame("framemenu"); // a lo mejor esto hace que se cierre la ventana

		//u.highLight(DatosGen);
		u.highLight(aDatosGeneralesMenu);
		System.out.println("Se encontró Datos Gen");
		testCase.addScreenShotCurrentStep(u, "DatosGen");
		//u.click(DatosGen);
		u.click(aDatosGeneralesMenu);
		System.out.println("Se dio click en datos generales");
		//comento esto para que no cierre la ventana
		//u.getDriver().switchTo().window(winHandleBefore);
		//u.waitForLoadPage();

	}

	/**
	 * Metodo para seleccionar el select de proveedores (Campo caracteristicas de
	 * operacion de la tienda)
	 * 
	 * @author Baltazar Lugo
	 * @param cara String (Caracteristicas del proveedor)
	 * @return N/A
	 */
	public void capturaDatosGen(String cp) {
		String winHandleBefore = u.getDriver().getWindowHandle();

		// Perform the click operation that opens new window

//		// Switch to new window opened  COMENTADO POR MI
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

//		u.waitForLoadPage(); 159 - 165 comentado por mi
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
//		u.highLight(TiendaE);

		u.hardWait(10);
		u.getDriver().manage().window().maximize();
		wait.until(ExpectedConditions.presenceOfElementLocated(framed));
		u.highLight(framed);
		u.getDriver().switchTo().frame("framedatos");
		u.highLight(CodPostal);

		//u.getDriver().switchTo().alert().accept();
		u.clear(CodPostal);
		wait.until(ExpectedConditions.alertIsPresent());
		u.getDriver().switchTo().alert().accept();
		//WebElement e = u.getDriver().findElement(CodPostal);
		//e.clear();
		//e.sendKeys(cp);
		//e.sendKeys(Key.ENTER);
		
		u.sendKeys(CodPostal, cp);
		testCase.addScreenShotCurrentStep("cp");
//		u.getDriver().switchTo().alert().accept();
		u.highLight(BtnBuscar);
		u.click(BtnBuscar);
		System.out.println("termina metodo capturaDatosGen(), se capturó codigo postal y se presionó buscar");
		//u.hardWait(10);

//		Select DistritoS = new Select(u.getDriver().findElement(cot));
////		DistritoS.selectByVisibleText(cara);
//		testCase.addScreenShotCurrentStep(u, "cot");
////		u.getDriver().switchTo().window(winHandleBefore);
//		u.waitForLoadPage();
	}

	/**
	 * Metodo elegir un id punto
	 * 
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return N/A
	 */
	public void IdPunto() {
		me.explicitWaitToBeClickable(IdPunto, Duration.ofSeconds(10));
		String idpunto1="";
		  Select s = new Select(u.getDriver().findElement(IdPunto));
	      // getting the list of options in the dropdown with getOptions()
	      List <WebElement> op = s.getOptions();
	      int size = op.size();
	      System.out.print(size);
	      for(int i =0; i<size ; i++){
	         String options = op.get(i).getText();
	         //System.out.println(options);
	        
	        
	      
	      if (options.contains("*")==false &&  options.isEmpty()==false && options.trim().isEmpty()==false) {
//	     string.isEmpty() || string.trim().isEmpty()
	    	//  && !options.contains("some")==true
	 	        	 System.out.println("entro if ");
	 	        	 System.out.println(options);
	 	        	 idpunto1=options;
	 	        	 System.out.println(idpunto1);
	 	        	 i=size;
		} else {
			 System.out.println("no entro");
		}
	      
	      
	      
	      }
	      System.out.println(idpunto1);
	
		
//		u.waitForLoadPage();
//		u.getDriver().switchTo().frame("framedatos");
		u.hardWait(3);
		Select idp = new Select(u.getDriver().findElement(IdPunto));
		u.highLight(IdPunto);
		// option[@value="437 | 10MON000014"]
//		By IdPunto1 = By.xpath("//option[@value="+idpunto+"]");
		System.out.print("cont" + idpunto1);
		u.hardWait(3);
		
		
		
		//testCase.addScreenShotCurrentStep(u, "p_alternativa_tel1");
		idp.selectByVisibleText(idpunto1);
		testCase.addScreenShotCurrentStep("p_alternativa_tel2");
		u.hardWait(2);
		u.getDriver().switchTo().alert().accept();
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("p_alternativa_tel3");
		
		
		
	}

	/**
	 * Metodo para sendkeys larga distancia
	 * 
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return N/A
	 */
	public void ClaveLargaDistancia(String clave) {
			
		if (u.getDriver().findElement(p_especifique).isDisplayed()==true) {
			MetodosExtra ME = new MetodosExtra(u, testCase);
			ME.scrollToElement(Sintel);
			u.highLight(Sintel);
		     u.click(Sintel);
		     u.highLight(CLD);
			 u.clear(CLD);
				u.sendKeys(CLD, clave);
				System.out.print("entro if con sintel");
				
		} else {
			System.out.print("entro if solo clear");
			me.scrollToElement(CLD);
			u.highLight(CLD);
            u.clear(CLD);
			u.sendKeys(CLD, clave);
		}
		testCase.addScreenShotCurrentStep(u, "clave");

	}
	
	/**
	 * Metodo para sendkeys a campo entre calles
	 * 
	 * @author Alexis Garcia
	 * @param N/A
	 * @return N/A
	 */
	public void inputEntreCalles(String clave) {
		String si = "SI";
		String winHandleBefore = u.getDriver().getWindowHandle();
		boolean Vacio = false;
		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

		u.hardWait(4);
		u.getDriver().manage().window().maximize();

		u.hardWait(10);
		u.getDriver().manage().window().maximize();
		u.highLight(framed);
		u.getDriver().switchTo().frame("framedatos");
		// u.highLight(CONFIGCUENTADOLARES);
		// u.click(CONFIGCUENTADOLARES);

		u.hardWait(3);
		if (u.getDriver().findElement(entreCalles).isDisplayed() == true) {
			u.highLight(entreCalles);
			u.click(entreCalles);
			u.clear(entreCalles);
			u.sendKeys(entreCalles, clave);
			System.out.print("entro if");

		} else {
			System.out.print("entro if solo clear");
			u.clear(entreCalles);
			u.sendKeys(entreCalles, clave);
		}
		testCase.addScreenShotCurrentStep(u, "entreCalles");

	}
	/**
	 * Metodo para seleccionar si se usa telefono o alternativa y su especificacion
	 * 
	 * @author Baltazar Lugo
	 * @param String Tel, String No,String alternativa,String p_especifique1
	 * @return N/A
	 */
	public void NTel(String Tel, String No,String alternativa,String p_especifique1) {
		
		if (No.equalsIgnoreCase("NO")) {
			u.click(Sintel);
			
			u.hardWait(4);
			Select tama = new Select(u.getDriver().findElement(p_alternativa_tel));
			tama.selectByVisibleText(alternativa);
			u.highLight(p_alternativa_tel);
			u.clear(p_especifique);

			u.sendKeys(p_especifique, p_especifique1);
			testCase.addScreenShotCurrentStep(u, "p_alternativa_tel");
		} else {
			u.highLight(Telefono);
			u.sendKeys(Telefono, Tel);
			testCase.addScreenShotCurrentStep(u, "telefono");}
		
	}

	/**
	 * Metodo para seleccionar Distrito
	 * 
	 * @author Baltazar Lugo
	 * @param String dis (distrito)
	 * @return N/A
	 */
	public void Distrito(String dis) {
//		u.waitForLoadPage();
//		u.getDriver().switchTo().frame("framedatos");
		u.hardWait(3);
		Select idp = new Select(u.getDriver().findElement(Distrito));
		u.highLight(Distrito);
		// option[@value="437 | 10MON000014"]
//		By IdPunto1 = By.xpath("//option[@value="+idpunto+"]");
	
		u.hardWait(3);
		idp.selectByVisibleText(dis);
		testCase.addScreenShotCurrentStep(u, "Dist");
//		u.getDriver().switchTo().alert().accept();
	}
	/**
	 * Metodo para dar clic en actualizar
	 * @author Baltazar Lugo
	 * @param 
	 * @return N/A
	 */
	
	public void clickBotonActualizar() {
//		u.click(DatosOp);
//		u.hardWait(3);
//		u.click(DatosGen);
		wait.until(ExpectedConditions.elementToBeClickable(BtnActualizar));
		MetodosExtra ME = new MetodosExtra(u, testCase);
		ME.scrollToElement(BtnActualizar);
		u.highLight(BtnActualizar);
		testCase.addScreenShotCurrentStep(u, "Datosactuali");
		u.click(BtnActualizar);
		//u.waitForLoadPage();

		//u.hardWait(10);
//		testCase.addScreenShotCurrentStep(u, "Respuesta_POP_PERMISOS_Y_CONTRATOS_ha_sido_grabada");
		wait.until(ExpectedConditions.alertIsPresent());
		String ventanaE = u.getDriver().switchTo().alert().getText();
		System.out.println("Texto en mensaje de alerta" + ventanaE);

		String str2 = "La informacion de \"DATOS GENERALES\" ha sido grabada.";
		if (ventanaE.equals(str2)) {
			testCase.addScreenShotCurrentStep("alertaInfoGrabada");
			u.getDriver().switchTo().alert().accept();
		} else {
			System.out.println("Fallo Datos de La informacion de \"DATOS GENERALES\" ha sido grabada.");
		}
		u.hardWait(4);
	}
	/**
	 * Constructor
	 ******************************************************************************************/

	public Datos_Generales(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
		this.wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(60));
	}

	/*
	 * METODOS - Acceso correcto a Inventario de espacios Publicitarios V2
	 ******************************************************************************************/

}