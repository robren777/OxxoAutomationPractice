package WEB.cartaPorte.objectModelPortalCP;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class PortalCartaPorte {
	SeleniumUtil u;
	TestCase testCase;
	private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);
	
	/*
	 * Localizador Menú
	 * 
	 * 
	 */
	//Menu
	By opcionMenuCartaPorte= By.xpath("//div[text()='Carta Porte']");
	By opcionMenuCartaPorteTerceros= By.xpath("//div[@class='menu-container']/div/div[text()='Carta Porte Terceros']");
	By portalTexto= By.xpath("//div[@class='header-label']");
	
	By opcionMenuAdministracion= By.xpath("//div[text()='Administracion']");
	By opcionMenuMonitoreo= By.xpath("//div[text()='Monitoreo']");
	
	//opciones CARTA PORTE
	By cartaPorteManual=By.xpath("/html/body/app-root/div/app-menu/div/div/div[2]/div[2]");
	By cartaPorteCedis=By.xpath("/html/body/app-root/div/app-menu/div/div/div[2]/div[1]");
	By cartaPorteOC=By.xpath("/html/body/app-root/div/app-menu/div/div/div[2]/div[3]");
	
	By cartaPorteTerceros=By.xpath("//div[@class='menu-container']/div[3]/div[2]/div[1]");
	By cartaPorteTercerosDevolucion= By.xpath("//div[@class='menu-container']/div[3]/div[2]/div[2]");
	By cartaPorteTercerosTiendas= By.xpath("//div[@class='menu-container']/div[3]/div[2]/div[3]");
	
	//opciones Administracion
	By admonUnidades=By.xpath("/html/body/app-root/div/app-menu/div/div[4]/div/div[4]/div");
	By admonParametros=By.xpath("/html/body/app-root/div/app-menu/div/div[4]/div/div[6]/div");
	//opciones Monitoreo
	By monitoreoPAC=By.xpath("/html/body/app-root/div/app-menu/div/div[5]/div/div[1]/div");
	// texto CartaPorte
	By textoCartaPorteManual=By.xpath("//app-current-screen/div");
	By textoCartaPorteCedis=By.xpath("//h4[text()='Carta Porte Entrega']");
	By textoCartaPorteOC=By.xpath("//div[@class='current-screen p-3 ng-star-inserted']");
	By textoCartaPorteOCEdit=By.xpath("//div[@class='current-screen p-3 ng-star-inserted' and contains(text(), 'Carta Porte OC') ]");
	By textoCartaPorteT=By.xpath("//div[@class='menu-container']/div/div[text()='Carta Porte Terceros']");
	By textoCartaPorteTD=By.xpath("//div[@class='current-screen p-3 ng-star-inserted']");
	By textoCartaPorteTT=By.xpath("//div[@class='envelope mv10 border_title']/h4");
	
	// texto Admon
	By textoAdmonListadoEquipos=By.xpath("//div[@class='current-screen p-3 ng-star-inserted']");
	//texto PAC
	By textoPac=By.xpath("//div[@class='current-screen p-3 ng-star-inserted']");
	//Casillas (cajas de texto
	By casillaOrigen=By.xpath("//input[@id='origen']");
	

	
	public PortalCartaPorte(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}
	
	/**
	 * Metodo para encontrar el menu de CartaPorte
	 * 
	 * @return regresa una bandera true si la busqueda fue exitosa
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean encontrarMenuOpcionesCartaPorte() {
		esperarAqueSeMuestreElemento(portalTexto);
		esperarAqueSeMuestreElemento(opcionMenuCartaPorte);
		if(u.getDriver().findElement(opcionMenuCartaPorte).isDisplayed()) {
			return true;
		}else {
			return false;
		}	
	}
	
	/**
	 * Metodo para obtener el menu de CartaPorteTerceros
	
	 * @return regresa una bandera true si la busqueda fue exitosa
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean encontrarMenuOpcionesCartaPorteTerceros() {
		Boolean bandera = false;
		do {
			try {
				if(u.getDriver().findElement(portalTexto).isDisplayed()) {
					if(u.getDriver().findElement(opcionMenuCartaPorteTerceros).isDisplayed()) {
						return true;
					}else {
						return false;
					}
				}
			} catch (Exception e) {
				//logger.error("Error" + e);
				//System.out.println("Error" + e);
				bandera = false;
			}
		}while(!bandera);
		return bandera;
	}
	
	/**
	 * Metodo para encontrar las opciones del menu de CartaPorte
	
	 * @param opcion-- opcion del menu de carta porta que se requiere encontrar(cedis, manual u oc)
	 * 
	 * @return regresa el texto del menu encontrado
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public String encontrarOpcionesCartaPorte(String opcion) {
		WebDriverWait wait= new WebDriverWait(u.getDriver(), Duration.ofSeconds(300));
		wait.until(ExpectedConditions.visibilityOfElementLocated(opcionMenuCartaPorte));
		esperarAqueSeMuestreElemento(opcionMenuCartaPorte);
		u.highLight(opcionMenuCartaPorte);
		testCase.addScreenShotCurrentStep("SeleccionarMenuCP");
		u.getDriver().findElement(opcionMenuCartaPorte).click();
		u.unhighLight(opcionMenuCartaPorte);
		switch(opcion) {
		case "manual":
			esperarAqueSeMuestreElemento(cartaPorteManual);
			return u.getDriver().findElement(cartaPorteManual).getText();	
		case "cedis":
			//esperarAqueSeMuestreElemento(cartaPorteCedis);
			wait.until(ExpectedConditions.visibilityOfElementLocated(cartaPorteCedis));
			return u.getDriver().findElement(cartaPorteCedis).getText();
		case "oc":
			esperarAqueSeMuestreElemento(cartaPorteOC);
			return u.getDriver().findElement(cartaPorteOC).getText();
		}
		return "";
	}
	
	/**
	 * Metodo para encontrar las opciones del menu de CartaPorte Terceros
	
	 * @param opcion-- opcion del menu de carta porte terceros que se requiere encontrar(terceros, devolucion o tiendas)
	 * 
	 * @return regresa el texto del menu encontrado
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public String encontrarOpcionesCartaPorteTerceros(String opcion) {
		u.highLight(opcionMenuCartaPorteTerceros);
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("SeleccionarMenuCPT");
		u.getDriver().findElement(opcionMenuCartaPorteTerceros).click();
		u.unhighLight(opcionMenuCartaPorteTerceros);
		switch(opcion) {
		case "terceros":
			return u.getDriver().findElement(cartaPorteTerceros).getText();	
		case "devolucion":
			return u.getDriver().findElement(cartaPorteTercerosDevolucion).getText();	
		case "tiendas":
			return u.getDriver().findElement(cartaPorteTercerosTiendas).getText();	
		}
		return "";
	}
	
	/**
	 * Metodo para seleccionar la opcion enviada del menu de CartaPorte
	
	 * @param opcion-- opcion del menu de carta porta que se requiere encontrar(cedis, manual u oc)
	 * 
	 * @return regresa el texto mostrado en pantalla un avez seleccionada la opcion del menu
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public String seleccionarOpcionCartaPorte(String opcion) {
		WebDriverWait wait= new WebDriverWait(u.getDriver(), Duration.ofSeconds(280));
		switch(opcion) {
		case "manual":
			esperarAqueSeMuestreElemento(cartaPorteManual);
			wait.until(ExpectedConditions.visibilityOfElementLocated(cartaPorteManual));
			u.highLight(cartaPorteManual);
			testCase.addScreenShotCurrentStep("SeleccionarTipoCartaPorte");
			u.getDriver().findElement(cartaPorteManual).click();
			esperarAqueSeMuestreElemento(casillaOrigen);
			wait.until(ExpectedConditions.visibilityOfElementLocated(casillaOrigen));
			u.unhighLight(cartaPorteManual);
			u.getDriver().findElement(casillaOrigen).click();
			u.hardWait(5);
			wait.until(ExpectedConditions.visibilityOfElementLocated(textoCartaPorteManual));
			return u.getDriver().findElement(textoCartaPorteManual).getText();	
		case "cedis":
			//esperarAqueSeMuestreElemento(cartaPorteCedis);
			wait.until(ExpectedConditions.visibilityOfElementLocated(cartaPorteCedis));
			u.highLight(cartaPorteCedis);
			testCase.addScreenShotCurrentStep("SeleccionarTipoCartaPorte");
			u.getDriver().findElement(cartaPorteCedis).click();
			esperarAqueSeMuestreElemento(casillaOrigen);
			wait.until(ExpectedConditions.visibilityOfElementLocated(casillaOrigen));
			u.unhighLight(cartaPorteCedis);
			u.getDriver().findElement(casillaOrigen).click();
			esperarAqueSeMuestreElemento(textoCartaPorteCedis);
			wait.until(ExpectedConditions.visibilityOfElementLocated(textoCartaPorteCedis));
			u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			return u.getDriver().findElement(textoCartaPorteCedis).getText();	
		case "oc":
			u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(35));
			//WebDriverWait wai2t= new WebDriverWait(u.getDriver(), 300);
			//wai2t.until(ExpectedConditions.visibilityOfElementLocated(cartaPorteOC));
			u.highLight(cartaPorteOC);
			u.getDriver().findElement(cartaPorteOC).click();
			testCase.addScreenShotCurrentStep("SeleccionarTipoCartaPorte");
			u.unhighLight(cartaPorteOC);
			u.getDriver().findElement(casillaOrigen).click();
			u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
			//wait.until(ExpectedConditions.visibilityOfElementLocated(textoCartaPorteOC));
			By textoCartaPorteOC=By.xpath("/html/body/app-root/div/app-current-screen/div");
			esperarAqueSeMuestreElemento(textoCartaPorteOC);
			
			//By textoCartaPorteOC=By.xpath("//div[@class='current-screen p-3 ng-star-inserted']");
			return u.getDriver().findElement(textoCartaPorteOC).getText();
		case "oc_edit":
			u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(35));
			u.highLight(cartaPorteOC);
			u.getDriver().findElement(cartaPorteOC).click();
			testCase.addScreenShotCurrentStep("SeleccionarTipoCartaPorte");
			u.unhighLight(cartaPorteOC);
			u.getDriver().findElement(casillaOrigen).click();
			u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOfElementLocated(textoCartaPorteOCEdit));
			By textoCartaPorteOCEdit=By.xpath("/html/body/app-root/div/app-current-screen/div");
			esperarAqueSeMuestreElemento(textoCartaPorteOCEdit);
			
			//By textoCartaPorteOC=By.xpath("//div[@class='current-screen p-3 ng-star-inserted']");
			return u.getDriver().findElement(textoCartaPorteOCEdit).getText();
		case "terceros":
			wait.until(ExpectedConditions.visibilityOfElementLocated(cartaPorteTerceros));
			u.highLight(cartaPorteTerceros);
			u.getDriver().findElement(cartaPorteTerceros).click();
			testCase.addScreenShotCurrentStep("SeleccionarTipoCartaPorte");
			u.unhighLight(cartaPorteTerceros);
			u.getDriver().findElement(casillaOrigen).click();
			return u.getDriver().findElement(textoCartaPorteT).getText();
		case "devolucion":
			wait.until(ExpectedConditions.visibilityOfElementLocated(cartaPorteTercerosDevolucion));
			u.highLight(cartaPorteTercerosDevolucion);
			u.getDriver().findElement(cartaPorteTercerosDevolucion).click();
			testCase.addScreenShotCurrentStep("SeleccionarTipoCartaPorte");
			u.unhighLight(cartaPorteTercerosDevolucion);
			u.getDriver().findElement(casillaOrigen).click();
			return u.getDriver().findElement(textoCartaPorteTD).getText();
		case "tiendas":
			wait.until(ExpectedConditions.visibilityOfElementLocated(cartaPorteTercerosTiendas));
			u.highLight(cartaPorteTercerosTiendas);
			u.getDriver().findElement(cartaPorteTercerosTiendas).click();
			testCase.addScreenShotCurrentStep("SeleccionarTipoCartaPorte");
			u.unhighLight(cartaPorteTercerosTiendas);
			u.getDriver().findElement(casillaOrigen).click();
		return u.getDriver().findElement(textoCartaPorteTT).getText();
	 }
		return "";
	}
	
	/**
	 * Metodo para generar numeros aleatorios
	
	 * @param max-- valor maximo a obtener del numero aleatoreo
	 * @param min-- valor minimo a obtener del numero aleatoreo
	 * 
	 * @return regresa el numero aleatorio obtenido dentro del rango especificado
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public int generarNumeroAleatorio(int max, int min) {
		return (int)Math.floor(Math.random()* (max-min+1))+1;
	}
	
	/**
	 * Metodo mover y bajar el scroll de la pagina
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	
	public void bajarScroll() {
		JavascriptExecutor jse=(JavascriptExecutor)u.getDriver();
		jse.executeScript("window.scrollBy(0,350)");
	}
	
	/**
	 * Desplaza la página hacia abajo una cantidad específica de píxeles.
	 * Utiliza JavaScript para ejecutar un desplazamiento vertical basado en el valor del parámetro `pixel`.
	 * @param pixel Número de píxeles a desplazar la página hacia abajo.
	 * @author nellyoliviab@hexaware.com
	 * @date 21/Agosto/2024
	 */
	public void bajarScroll2(String pixel) {
		JavascriptExecutor jse=(JavascriptExecutor)u.getDriver();
		jse.executeScript("window.scrollBy(0,"+pixel+")");
	}
	
	/**
	 * Metodo para encontrar las opciones del menu de Administracion
	
	 * @param opcion----opcion del menu de administracion
	 * @param captura---bandera para agregar evidencia  
	 * 
	 * @return regresa el texto del menu encontrado
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public String encontrarOpcionesAdministracione(String opcion, boolean captura) {
		WebDriverWait wait= new WebDriverWait(u.getDriver(), Duration.ofSeconds(300));
		//esperarAqueSeMuestreElemento(opcionMenuAdministracion);
		wait.until(ExpectedConditions.visibilityOfElementLocated(opcionMenuAdministracion));
		u.highLight(opcionMenuAdministracion);
		if(captura) {
			testCase.addScreenShotCurrentStep("SeleccionarMenuAdmin");
		}
		u.unhighLight(opcionMenuAdministracion);
		u.getDriver().findElement(opcionMenuAdministracion).click();
		switch(opcion) {
			case "unidades":
			esperarAqueSeMuestreElemento(admonUnidades);
				return u.getDriver().findElement(admonUnidades).getText();	
			case "parametros":
				esperarAqueSeMuestreElemento(admonParametros);
				return u.getDriver().findElement(admonParametros).getText();	
			}
		return "";
	}
	
	
	/**
	 * Metodo para seleccionar la opcion enviada del menu administración
	
	 * @param opcion-- opcion del menu de administración que se requiere encontrar(unidades)
	 * 
	 * @return regresa el texto mostrado en pantalla una vez seleccionada la opcion del menu
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public String seleccionarOpcionAdministracion(String opcion) {
		//WebDriverWait wait= new WebDriverWait(u.getDriver(), 60);
		switch(opcion) {
		case "unidades":
			//wait.until(ExpectedConditions.visibilityOfElementLocated(admonUnidades));
			esperarAqueSeMuestreElemento(admonUnidades);
			u.highLight(admonUnidades);
			testCase.addScreenShotCurrentStep("SeleccionarAdministracionUnidades");
			u.getDriver().findElement(admonUnidades).click();
			u.unhighLight(admonUnidades);
			u.getDriver().findElement(casillaOrigen).click();
			esperarAqueSeMuestreElemento(textoAdmonListadoEquipos);
			u.hardWait(5);
			return u.getDriver().findElement(textoAdmonListadoEquipos).getText();
		case "parametros":
			//wait.until(ExpectedConditions.visibilityOfElementLocated(admonUnidades));
			esperarAqueSeMuestreElemento(admonParametros);
			u.highLight(admonParametros);
			testCase.addScreenShotCurrentStep("SeleccionarAdministracionParametros");
			u.getDriver().findElement(admonParametros).click();
			u.unhighLight(admonParametros);
			esperarAqueSeMuestreElemento(textoPac);
			u.hardWait(5);
			return u.getDriver().findElement(textoPac).getText();
			
	 }
	
		return "";
	}
	
	/**
	 * Espera hasta que un elemento web sea visible en la página.
	 * Este método verifica repetidamente si el elemento especificado por el selector `elementoWeb` está presente y visible en la página.
	 * Si el elemento no está disponible o se vuelve obsoleto, el método seguirá intentando hasta que el elemento se muestre.
	 * Utiliza un bucle `do-while` para hacer las comprobaciones hasta que el elemento se encuentre visible.
	 * 
	 * Maneja excepciones relacionadas con la presencia y visibilidad del elemento:
	 * - `StaleElementReferenceException`: Ocurre cuando el elemento ya no está presente en el DOM.
	 * - `NoSuchElementException`: Ocurre cuando el elemento no se encuentra en el DOM.
	 * 
	 * @param elementoWeb Selector `By` que representa el elemento web que se debe esperar.
	 * @author nellyoliviab@hexaware.com
	 * @date 21/Agosto/2024
	 */
	public void esperarAqueSeMuestreElemento(By elementoWeb) {
		boolean continuar=false;
		do {
			
			try {
				if(u.getDriver().findElement(elementoWeb).isDisplayed()) {
					continuar=true;
				}
			}catch(org.openqa.selenium.StaleElementReferenceException e) {
				continuar=false;
			}catch(org.openqa.selenium.NoSuchElementException ex) {
				continuar=false;
			}
		}while(!continuar);
	}
	
	/**
	 * Metodo para encontrar la opcion enviada del menu Monitoreo
	
	 * @param opcion-- opcion del menu de monitoreo que se requiere encontrar
	 * 
	 * @return regresa el texto mostrado en pantalla una vez seleccionada la opcion del menu
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public String encontrarOpcionesMonitoreo(String opcion) {
		//WebDriverWait wait= new WebDriverWait(u.getDriver(), 120);
		esperarAqueSeMuestreElemento(opcionMenuMonitoreo);
		u.highLight(opcionMenuMonitoreo);
		testCase.addScreenShotCurrentStep("SeleccionarMenuMonitoreo");
		u.getDriver().findElement(opcionMenuMonitoreo).click();
		u.unhighLight(opcionMenuMonitoreo);
		switch(opcion) {
			case "ConfiguraciónPAC":
			esperarAqueSeMuestreElemento(monitoreoPAC);
				return u.getDriver().findElement(monitoreoPAC).getText();	
			}
		return "";
	}
	/**
	 * Metodo para seleccionar la opcion enviada del menu Monitoreo
	
	 * @param opcion-- opcion del menu de monitoreo que se requiere encontrar
	 * 
	 * @return regresa el texto mostrado en pantalla una vez seleccionada la opcion del menu
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public String seleccionarOpcionMonitoreo(String opcion) {
		//WebDriverWait wait= new WebDriverWait(u.getDriver(), 60);
		switch(opcion) {
		case "ConfiguraciónPAC":
			//wait.until(ExpectedConditions.visibilityOfElementLocated(admonUnidades));
			esperarAqueSeMuestreElemento(monitoreoPAC);
			u.highLight(monitoreoPAC);
			testCase.addScreenShotCurrentStep("SeleccionarMonitoreoPAC");
			u.getDriver().findElement(monitoreoPAC).click();
			u.unhighLight(monitoreoPAC);
			esperarAqueSeMuestreElemento(textoPac);
			u.hardWait(5);
			return u.getDriver().findElement(textoPac).getText();	
		
	 }
		return "";
	}
}
