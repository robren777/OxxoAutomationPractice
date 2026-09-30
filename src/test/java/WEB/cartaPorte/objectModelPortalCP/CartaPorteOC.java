package WEB.cartaPorte.objectModelPortalCP;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class CartaPorteOC {
	SeleniumUtil u;
	TestCase testCase;
	private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);
	PortalCartaPorte portal;

	// Boton buscar
	By botonBuscar = By.xpath("//div[@class='col2']/button");
	By elementoDerecha = By.xpath("//form/div/div[2]/div[4]/div[2]/button[2]");
	By botonTiendas = By.xpath("//form/div/div[2]/div[4]/div[1]/table/thead/tr/th[1]/mat-checkbox");
	By botonTiendasDerecha = By.xpath("//mat-checkbox[@id='mat-checkbox-10']");
	By seleccionarCartaPorte = By.xpath("//button[text()='Generar Carta Porte']");
	By botonEnviar = By.xpath("//button[text()='Enviar']");
	By botonLimpiarDatos = By.xpath("//button[text()='Limpiar datos']");
	By botonAlerta = By.xpath("//div[@class='alert-footer mt-3 mb-3']/button[@class='btn warn']");

	By textoMensaje = By.xpath("//div[@class='overlay-container']/div/div");

	// Casillas
	By casillaOrigen = By.xpath("//input[@id='origen']");
	By casillaDestino = By.xpath("//input[@id='terceros']");
	By casillaOrdenCompra = By.xpath("//input[@id='orden']");
	By nombreChofer = By.xpath("//input[@placeholder='Nombre Chofer']");
	By casillaNumeroEconomico = By.xpath("//div[@class='col3 form-group autocomplete']/input");
	By casillaPlacas = By.xpath("//input[@placeholder='Placas']");
	By casillaSeguro = By.xpath("//input[@placeholder='Seguro']");
	By capturaCorreo = By.xpath("//div[@class='mat-chip-list-wrapper']/input");

	// NUEVOS CAMBIOS CARTA PORTE 3
	By casillaPesoBruto = By.xpath("//*[@id=\"pesoBrutoV\"]");
	By seleccionaRegistroIstmo = By.xpath("//mat-checkbox[@id='checkItsmo']");
	By casillaPoloOrigen = By.xpath("//input[@placeholder='Polo Origen']");
	By casillaPoloDestino = By.xpath("//input[@placeholder='Polo Destino']");
	By seleccionLogisticaInversa = By.xpath("//mat-checkbox[@id='checkInversa']");

	// Tabla
	By tablaBusqueda = By.xpath("//input[@id='mat-checkbox-5-input']");

	By estatusLogisticaInversa=By.xpath("//mat-checkbox[@id='checkInversa']/label/span/input");
	
	 /**
	 * Constructor de la clase CartaPorteOC.
	 * @author nellyoliviab@hexaware.com
	 * @param u Objeto de tipo SeleniumUtil para la gestión de Selenium WebDriver.
	 * @param testCase Objeto de tipo TestCase para la gestión de casos de prueba.
	 * @param portal Objeto de tipo PortalCartaPorte que representa el portal de Carta Porte.
	 * @date 26/Junio/2024
	 */
	public CartaPorteOC(SeleniumUtil u, TestCase testCase, PortalCartaPorte portal) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.portal = portal;
	}

	/**
	 * Metodo para capturar informacion enviada
	 * 
	 * @param valor --------------valor a capturar en casilla
	 * @param nombre campo ------ foco actual para capturar valor
	 * @param textoSeleccionable -true cuando se requiere comprobar que se despliega el valor campturado
	 * @param captura ------------true para obtener la captura en la evidencia
	 * 
	 * @return regresa true si se autollenaron los datos
	 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean capturarCasilla(String valor, String nombreCampo, boolean textoSeleccionable, boolean captura) {
		By elementoCasilla=null;
		try {
			switch(nombreCampo) {
			case "origen":
				System.out.println("Valor: "+valor);
				if(valor.compareTo("ESC")==0) {
					u.getDriver().findElement(casillaOrigen).sendKeys(Key.ESC);
					return true;
				}
				elementoCasilla=casillaOrigen;				
				break;
			case "destino":
				elementoCasilla=casillaDestino;				
				break;
			case "ordenCompra":
				elementoCasilla=casillaOrdenCompra;				
				break;
			case "nombreChofer":
				elementoCasilla=nombreChofer;				
				break;
			case "numeroEconomico":
				elementoCasilla=casillaNumeroEconomico;				
				break;
			case "capturaCorreo":
				u.hardWait(10);
				elementoCasilla=capturaCorreo;	
				u.hardWait(2);
				u.getDriver().findElement(elementoCasilla).sendKeys(valor+Key.TAB);
				testCase.addScreenShotCurrentStep("Captura"+nombreCampo);
				return true;
			case "pesoBruto":
				elementoCasilla=casillaPesoBruto;	
				break;
			case "poloOrigen":
				WebDriverWait w= new WebDriverWait(u.getDriver(), Duration.ofSeconds(1));
				w.until(ExpectedConditions.visibilityOfElementLocated(casillaPoloOrigen));
				break;
			case "poloDestino":
				WebDriverWait w2= new WebDriverWait(u.getDriver(), Duration.ofSeconds(1));
				w2.until(ExpectedConditions.visibilityOfElementLocated(casillaPoloDestino));
				break;
			}
			if(elementoCasilla!=null) {
				u.highLight(elementoCasilla);
				//u.hardWait(2);
				u.getDriver().findElement(elementoCasilla).sendKeys(valor);
				//u.hardWait(2);
				if(captura) {
					testCase.addScreenShotCurrentStep("Captura"+nombreCampo);
				}
				if(textoSeleccionable) {
					By seleccionTextoActual=By.xpath("//span[text()=' "+valor+" ']");
					u.getDriver().findElement(seleccionTextoActual).click();
				}
				u.unhighLight(elementoCasilla);
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			logger.error("Error" + e);
			System.out.println("Error" + e);
			return false;
		}
	}

	/**
	 * Metodo para encontrar elemento en pantalla
	 * 
	 * @param nombre campo ------ foco actual para capturar valor
	 * 
	 * @return regresa true si la operacion fue exitosa
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean encontrarElemento(String nombreCampo) {
		try {
			switch (nombreCampo) {
			case "destino":
				if (u.getDriver().findElement(casillaDestino).isDisplayed()) {
					return true;
				}
				return false;
			case "botonLimpiarDatos":
				if (u.getDriver().findElement(botonLimpiarDatos).isDisplayed()) {
					return true;
				}
				return false;
			case "ordenCompra":
				if (u.getDriver().findElement(casillaOrdenCompra).isDisplayed()) {
					return true;
				}
				return false;
			case "botonBuscar":
				if (u.getDriver().findElement(botonBuscar).isDisplayed()) {
					return true;
				}
				return false;
			case "tablaBusqueda":
				u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
				if (u.getDriver().findElement(botonTiendas).isDisplayed()) {
					return true;
				}
				return false;
			case "elementoDerecha":
				if (u.getDriver().findElement(elementoDerecha).isDisplayed()) {
					return true;
				}
				return false;
			case "nombreChofer":
				if (u.getDriver().findElement(nombreChofer).isDisplayed()) {
					return true;
				}
				return false;
			case "numeroEconomico":
				if (u.getDriver().findElement(casillaNumeroEconomico).isDisplayed()) {
					return true;
				}
				return false;
			case "pesoBruto":
				if (u.getDriver().findElement(casillaPesoBruto).isDisplayed()) {
					u.highLight(casillaPesoBruto);
					testCase.addScreenShotCurrentStep("PesoBrutoEncontrado");
					u.highLight(casillaPesoBruto);
					return true;
				}
				return false;

			}
		} catch (Exception e) {
			logger.error("Error" + e);
			System.out.println("Error" + e);
			return false;
		}
		return false;
	}

	/**
	 * Metodo para seleccionar elementos en pantalla
	 * 
	 * @param nombre  campo ------ foco actual
	 * @param captura ------------true para obtener la captura en la evidencia
	 * 
	 * @return regresa true si la operacion fue exitosa
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean seleccionarElemento(String nombreCampo, boolean captura) {
		By elementoSeleccion = null;
		boolean seleccion = false;
		try {
			switch (nombreCampo) {
			case "botonBuscar":
				elementoSeleccion = botonBuscar;
				break;
			case "botonTiendas":
				elementoSeleccion = botonTiendas;
				break;
			case "elementoDerecha":
				elementoSeleccion = elementoDerecha;
				break;
			case "generarCartaPorte":
				elementoSeleccion = seleccionarCartaPorte;
				break;
			case "botonEnviar":
				elementoSeleccion = botonEnviar;
				break;
			case "registroIstmo":
				WebDriverWait w = new WebDriverWait(u.getDriver(), Duration.ofSeconds(1));
				w.until(ExpectedConditions.visibilityOfElementLocated(seleccionaRegistroIstmo));
				return seleccion;
			case "logisticaInversa":
				elementoSeleccion = seleccionLogisticaInversa;
				seleccion = true;
				break;
			case "botonTiendasDerecha":
				elementoSeleccion = botonTiendasDerecha;
				seleccion = true;
				break;
			case "botonLimpiarDatos":
				elementoSeleccion = botonLimpiarDatos;
				break;

			}
			u.highLight(elementoSeleccion);
			if (captura) {
				testCase.addScreenShotCurrentStep("Seleccion" + nombreCampo);
			}
			u.unhighLight(elementoSeleccion);
			u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
			u.getDriver().findElement(elementoSeleccion).click();

			if (elementoSeleccion != null) {
				seleccion = true;
			}
			return seleccion;

		} catch (Exception e) {
			logger.error("Error" + e);
			System.out.println("Error" + e);
			return false;
		}
	}

	/**
	 * Metodo para verificar si la informacion de placas y seguro de chofer fue
	 * autollenada
	 * 
	 * 
	 * @return regresa true si la operacion fue exitosa
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean verificarAutollenado() {
		try {
			String placas = u.getDriver().findElement(casillaPlacas).getText();
			String seguro = u.getDriver().findElement(casillaSeguro).getText();
			if (placas != null && placas != "" && seguro != null && seguro != "") {

				return true;
			}
			return false;
		} catch (Exception e) {
			logger.error("Error" + e);
			System.out.println("Error" + e);
			return false;
		}
	}

	 /**
	 * Determinar el estatus de timbrado, esperando hasta 60 segundos para obtener un mensaje y verificar si es exitoso.
	 * @author nellyoliviab@hexaware.com
	 * @return Retorna true si el mensaje de timbrado contiene la palabra "exitosamente", de lo contrario false.
	 * @date 26/Junio/2024
	 */
	public boolean determinarEstatusTimbrado() {
		String mensaje = "";
		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(60));
		boolean elementoEncontrado = false;
		do {
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(textoMensaje));
				mensaje = u.getDriver().findElement(textoMensaje).getText();
				if (mensaje.compareTo("") != 0) {
					testCase.addScreenShotCurrentStep("MensajeTimbrado");
					testCase.addTextEvidenceCurrentStep("Mensaje de timbrado: " + mensaje);
					String mensajeCompeto[] = mensaje.split("\\W+");
					for (String busqueda : mensajeCompeto) {
						if ("exitosamente".contains(busqueda)) {
							return true;
						}
					}

					return false;
				}
			} catch (Exception e) {

			}
		} while (!elementoEncontrado);
		return false;
	}

	/**
	 * Metodo para obtener valor de casillas
	 * 
	 * @param nombreCampo-----------nombre del campo a capturar
	 * 
	 * @return regresa el texto de la casilla
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public String obtenerTexto(String nombreCampo) {
		u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		By obtenerTexto = null;
		try {
			switch (nombreCampo) {
			case "Origen":
				obtenerTexto = casillaOrigen;
				break;
			case "Destino":
				obtenerTexto = casillaDestino;
				break;
			case "OrdenCompra":
				obtenerTexto = casillaOrdenCompra;
				break;
			case "NombreChofer":
				obtenerTexto = nombreChofer;
				break;
			case "NumeroEconomico":
				obtenerTexto = casillaNumeroEconomico;
				break;
			case "PesoBruto":
				obtenerTexto = casillaPesoBruto;
				u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				u.highLight(obtenerTexto);
				testCase.addScreenShotCurrentStep("pesovehicularAutollenado");
				u.unhighLight(obtenerTexto);
				return u.getDriver().findElement(obtenerTexto).getAttribute("value");

			case "SeleccionaTiendas":
				if (u.getDriver().findElement(botonTiendas).isSelected() == false) {
					return null;
				}
				return "Seleccionado";
			case "SeleccionaTiendasD":
				if (u.getDriver().findElement(botonTiendasDerecha).isSelected() == false) {
					return null;
				}
				return "Seleccionado";
			case "SeleccionIstmo":
				if (u.getDriver().findElement(seleccionaRegistroIstmo).isSelected() == false) {
					return null;
				}
				return "Seleccionado";
			case "estatusLogisticaInversa":
				String status=u.getDriver().findElement(estatusLogisticaInversa).getAttribute("aria-checked");
				if(status.compareToIgnoreCase("true")==0) {
					agregarCapturaDeEvidencia(nombreCampo, seleccionLogisticaInversa);
					return "Seleccionado";
				}else {
					return null;
				}
			case "SeleccionLogistica":
				if (u.getDriver().findElement(seleccionLogisticaInversa).isSelected() == false) {
					return null;
				}
				return "Seleccionado";
			}
			String texto = u.getDriver().findElement(obtenerTexto).getText();
			if (texto.compareTo("") == 0) {
				return null;
			}
			return texto;
		} catch (Exception e) {
			logger.error("Error" + e);
			System.out.println("Error" + e);
			return null;
		}
	}

	 /**
	 * Buscar y quitar una alerta en la interfaz de usuario, intentando hasta 3 veces.
	 * @author nellyoliviab@hexaware.com
	 * @date 26/Junio/2024
	 */
	public void buscarYQuitarAlerta() {
		boolean continuar = false;
		int intentos = 1;
		do {
			try {
				if (u.getDriver().findElement(botonAlerta).isDisplayed()) {
					u.getDriver().findElement(botonAlerta).click();
				}
			} catch (org.openqa.selenium.StaleElementReferenceException e) {
				continuar = false;
			} catch (org.openqa.selenium.NoSuchElementException NOs) {
				continuar = false;
			}
			intentos++;
			if (intentos > 3) {
				continuar = true;
			}
		} while (!continuar);
	}

	/**
	 * Convertir peso de kilogramos a toneladas.
	 * @author nellyoliviab@hexaware.com
	 * @param campoActual El valor del peso en kilogramos en formato String.
	 * @return Retorna el peso convertido a toneladas en formato String.
	 * @date 26/Junio/2024
	 */
	public String convertirKiloEnTonelada(String campoActual) {
		if (campoActual != null) {
			int pesoBrutoKilo = Integer.parseInt(campoActual);
			int pesoBrutoTonelada = pesoBrutoKilo / 1000;
			return String.valueOf(pesoBrutoTonelada);
		}
		return null;
	}
	
	/**
	 * Obtener peso bruto autollenado a partir de un campo dado, redondeado a entero.
	 * @author nellyoliviab@hexaware.com
	 * @param campoActual El valor del campo actual en formato String que representa el peso bruto.
	 * @return Retorna el peso bruto redondeado en formato String.
	 * @date 26/Junio/2024
	 */
	public String obtenerPesoBrutoAutollenado(String campoActual) {
		if (campoActual != null) {
			campoActual = obtenerTexto(campoActual);
			int pesoBrutoObtenido = (int) Double.parseDouble(campoActual);
			campoActual = String.valueOf(pesoBrutoObtenido);
		}
		return campoActual;
	}
	
	/**
	 * Metodo para agregar una captura de pantalla al reporte de evidencia
	 * 
	 * @param valor-----------Texto que se espera encontrar en esta lsita desplegable
	 * @param elemtoCaptura---------elemento WEB en el que se enfocara la captura.
	 * @author nellyoliviab@hexaware.com
	 */
	public void agregarCapturaDeEvidencia(String nombreCampo, By elementoCaptura) {
		u.highLight(elementoCaptura);
		u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		testCase.addScreenShotCurrentStep("Captura"+nombreCampo);
		u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		u.unhighLight(elementoCaptura);
	}
}
