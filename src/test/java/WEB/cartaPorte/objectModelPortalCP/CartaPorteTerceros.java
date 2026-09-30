package WEB.cartaPorte.objectModelPortalCP;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class CartaPorteTerceros {
	SeleniumUtil u;
	TestCase testCase;
	private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);
	PortalCartaPorte portal;
	
	//Casillas
		By casillaOrigen=By.xpath("//input[@id='origen']");
		By casillaDestino=By.xpath("//input[@id='destino']");
		By casillaRuta=By.xpath("//input[@placeholder='Ruta']");
		By casillaOla=By.xpath("//input[@placeholder='Ola']");
		By casillaTransferencia=By.xpath("//input[@placeholder='Transferencia']");
		By casillaNombreArchivo=By.xpath("//div[@class='col7']/input");
		
	//Boton
		By seleccionFechaFacturacion=By.xpath("//mat-datepicker-toggle[@data-mat-calendar='mat-datepicker-2']/button");
		By botonAnio=By.xpath("//button[@class='mat-focus-indicator mat-calendar-period-button mat-button mat-button-base']");
		By botonBuscar=By.xpath("//div/button[@class='btn ng-star-inserted']");
		By seleccionarTienda=By.xpath("//mat-checkbox[@id='mat-checkbox-5']");
		By seleccionDerecha=By.xpath("//mat-icon[text()='chevron_right']");
		By seleccionarCSV=By.xpath("//label[@for='mat-radio-23-input']");
		By seleccionarXLS=By.xpath("//label[@for='mat-radio-24-input']");
		By botonGenerarArchivo=By.xpath("//button[text()='Generar Archivo']");
		
	
	//Tabla
		By tablaBusqueda= By.xpath("//div[@class='form-container']/div[@class='row modulo']/div[1]/table");
	
	//Texto
		By textoEstatusTransferencia=By.xpath("//label[text()='Estatus Transferencia']");
		By textoTipoTraslado=By.xpath("//label[text()='Tipo Traslado']");
		By textoNombreArchivo=By.xpath("//div[@class='col7']");
	
	/**
	 * Constructor de la clase CartaPorteTerceros.
	 * Inicializa los elementos necesarios para las pruebas de CartaPorte.
	 * @param u Utilidad de Selenium para la interacción con la página.
	 * @param testCase Caso de prueba que almacena los resultados y capturas.
	 * @param portal Instancia del portal de CartaPorte utilizado para las pruebas.
	 * @author nellyoliviab@hexaware.com
	 * @date 21/Agosto/2024
	 */
	public CartaPorteTerceros(SeleniumUtil u, TestCase testCase, PortalCartaPorte portal) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.portal = portal;
	}
	
	/**
	 * Captura el valor en una casilla de entrada y realiza las acciones necesarias según el campo.
	 * @param valor El valor a ingresar en la casilla.
	 * @param nombreCampo Nombre del campo a capturar.
	 * @param textoSeleccionable Indica si el texto en la casilla es seleccionable.
	 * @param captura Indica si se debe tomar una captura de pantalla.
	 * @return Retorna true si la operación fue exitosa, false en caso contrario.
	 * @author nellyoliviab@hexaware.com
	 * @date 21/Agosto/2024
	 */
	public boolean capturarCasilla(String valor, String nombreCampo, boolean textoSeleccionable, boolean captura) {
		By elementoCasilla=null;
		try {
			switch(nombreCampo) {
			case "origen":
				elementoCasilla=casillaOrigen;
			break;
			case "destino":
				elementoCasilla=casillaDestino;
			break;
			case "ruta":
				elementoCasilla=casillaRuta;				
				break;
			case "ola":
				elementoCasilla=casillaOla;				
				break;
			case "transferencia":
				elementoCasilla=casillaTransferencia;	
				break;
			case "nombrarArchivo":
				elementoCasilla=casillaNombreArchivo;
			break;
			}
			u.getDriver().findElement(elementoCasilla).clear();
			u.highLight(elementoCasilla);
			u.hardWait(2);
			u.getDriver().findElement(elementoCasilla).sendKeys(valor);
			u.hardWait(2);
			if(captura) {
				testCase.addScreenShotCurrentStep("Captura"+nombreCampo);
			}
			if(textoSeleccionable) {
				By seleccionTextoActual=By.xpath("//span[text()=' "+valor+" ']");
				u.getDriver().findElement(seleccionTextoActual).click();
			}
			u.unhighLight(elementoCasilla);
			return true;
		} catch (Exception e) {
			logger.error("Error" + e);
			System.out.println("Error" + e);
			return false;
		}
	}
	
	/**
	 * Verifica si un elemento está presente en la página según el nombre del campo.
	 * @param nombreCampo Nombre del campo a verificar.
	 * @return Retorna true si el elemento está presente y visible, false en caso contrario.
	 * @author nellyoliviab@hexaware.com
	 * @date 21/Agosto/2024
	 */
	public boolean encontrarElemento(String nombreCampo) {
		try {
			switch(nombreCampo) {
				case "destino":
					if(u.getDriver().findElement(casillaDestino).isDisplayed()) {
						return true;
					}
					return false;
				case "fechaFacturacion":
					if(u.getDriver().findElement(seleccionFechaFacturacion).isDisplayed()) {
						return true;
					}
					return false;
				case "estatusTransferencia":
					if(u.getDriver().findElement(textoEstatusTransferencia).isDisplayed()) {
						return true;
					}
					return false;
				case "ruta":
					if(u.getDriver().findElement(casillaRuta).isDisplayed()) {
						return true;
					}
					return false;	
				case "ola":
					if(u.getDriver().findElement(casillaOla).isDisplayed()) {
						return true;
					}
					return false;	
				case "transferencia":
					if(u.getDriver().findElement(casillaTransferencia).isDisplayed()) {
						return true;
					}
					return false;
				case "botonBuscar":
					if(u.getDriver().findElement(botonBuscar).isDisplayed()) {
						return true;
					}
					return false;
				case "tablaRegistros":
					if(u.getDriver().findElement(tablaBusqueda).isDisplayed()) {
						return true;
					}
					return false;	
				case "seleccionDerecha":
					if(u.getDriver().findElement(seleccionDerecha).isDisplayed()) {
						return true;
					}
					return false;
				case "tipoTraslado":
					if(u.getDriver().findElement(textoTipoTraslado).isDisplayed()) {
						return true;
					}
					return false;
				case "nombrarArchivo":
					if(u.getDriver().findElement(textoNombreArchivo).isDisplayed()) {
						return true;
					}
					return false;
				case "seleccionarCSV":
					if(u.getDriver().findElement(seleccionarCSV).isDisplayed()) {
						return true;
					}
					return false;
				case "generarArchivo":
					if(u.getDriver().findElement(botonGenerarArchivo).isDisplayed()) {
						return true;
					}
					return false;
				case "seleccionarXLS":
					if(u.getDriver().findElement(seleccionarXLS).isDisplayed()) {
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
	 * Selecciona un elemento en la página según el nombre del campo y realiza una acción de captura si es necesario.
	 * @param nombreCampo Nombre del campo a seleccionar.
	 * @param captura Indica si se debe tomar una captura de pantalla.
	 * @param infoAdicional Información adicional necesaria para la selección.
	 * @return Retorna true si la selección fue exitosa, false en caso contrario.
	 * @author nellyoliviab@hexaware.com
	 * @date 21/Agosto/2024
	 */
	public boolean seleccionarElemento(String nombreCampo, boolean captura, String infoAdicional) {
		By elementoSeleccion=null;
		boolean seleccion=false;
		try {
			switch(nombreCampo) {
			case "fechaFacturacion":
				elementoSeleccion=seleccionFechaFacturacion;
				break;
			case "botonAnio":
				elementoSeleccion=botonAnio;
				break;
			case "anio":
				By anio=By.xpath("//td/div[text()=' "+infoAdicional+" ']");
				elementoSeleccion=anio;
				seleccion=true;
			break;
			case "mes":
				String nombreMes=prepararMes(infoAdicional);
				By mes=By.xpath("//tr/td/div[text()=' "+nombreMes+" ' ]");
				elementoSeleccion=mes;
				seleccion=true;
			break;
			case "dia":
				By dia=By.xpath("//tr/td/div[text()=' "+infoAdicional+" ']");
				elementoSeleccion=dia;
				seleccion=true;
			break;
			case "opciones1":
				int opcion=Integer.parseInt(infoAdicional);
				By check1=null;
				switch(opcion) {
				case 1:
					check1=By.xpath("//span[text()=' Shipped']");
				break;
				case 2:
					check1=By.xpath("//span[text()=' Closed']");
					break;
				case 3:
					check1=By.xpath("//span[text()=' Ambos']");
					break;
				}
				elementoSeleccion=check1;
				seleccion=true;
			break;
			case "opciones2":
				int opcio2=Integer.parseInt(infoAdicional);
				By check2=null;
				switch(opcio2) {
				case 1:
					check2=By.xpath("//span[text()=' Ruta']");
				break;
				case 2:
					check2=By.xpath("//span[text()=' Ola']");
					break;
				case 3:
					check2=By.xpath("//span[text()=' Transferencia']");
					break;
				}
				elementoSeleccion=check2;
				seleccion=true;
			break;
			case "botonBuscar":
				elementoSeleccion=botonBuscar;
				seleccion=true;
			break;
			case "seleccionaTiendas":
				elementoSeleccion=seleccionarTienda;
				seleccion=true;
			break;
			case "seleccionDerecha":
				elementoSeleccion=seleccionDerecha;
				seleccion=true;
			break;
			case "tipoTraslado":
				int opcionT=Integer.parseInt(infoAdicional);
				By traslado=null;
				switch(opcionT) {
				case 1:
					traslado=By.xpath("//span[text()=' Todos']");
				break;
				case 2:
					traslado=By.xpath("//span[text()=' Refrigerados']");
					break;
				case 3:
					traslado=By.xpath("//span[text()=' Secos']");
					break;
				case 4:
					traslado=By.xpath("//label[@for='mat-radio-22-input']/span[text()=' Ambos']");
				break;
				}
				elementoSeleccion=traslado;
				seleccion=true;
			break;
			case "seleccionarCSV":
				elementoSeleccion=seleccionarCSV;
				seleccion=true;
			break;
			case "generarArchivo":
				elementoSeleccion=botonGenerarArchivo;
				seleccion=true;
			break;
			case "seleccionarXLS":
				elementoSeleccion=seleccionarXLS;
				seleccion=true;
			break;
			}
			u.highLight(elementoSeleccion);
			if(captura) {
				testCase.addScreenShotCurrentStep("Seleccion"+nombreCampo);
			}
			u.hardWait(2);
			u.getDriver().findElement(elementoSeleccion).click();
			if(nombreCampo.compareTo("anio")!=0 && nombreCampo.compareTo("mes")!=0&& nombreCampo.compareTo("dia")!=0) {
				u.unhighLight(elementoSeleccion);
			}
			if(elementoSeleccion!=null) {
				seleccion=true;
			}
			return seleccion;
		} catch (Exception e) {
			logger.error("Error" + e);
			System.out.println("Error" + e);
			return false;
		}
	}
	
	
	/**
	 * Obtiene la opción de traslado correspondiente a un número.
	 * @param check2 Número que representa el tipo de traslado.
	 * @return Retorna una cadena que representa el tipo de traslado correspondiente.
	 * @author nellyoliviab@hexaware.com
	 * @date 21/Agosto/2024
	 */
	public String obtenerOpcion(String check2) {
		switch(check2) {
		case "1":
		return "ruta";
		case "2":
			return "ola";
		case "3":
			return "transferencia";
		}
		return "";
	}
	
	/**
	 * Prepara el mes en formato abreviado para su uso en la selección.
	 * @param mes El nombre completo del mes.
	 * @return Retorna el nombre del mes en formato abreviado.
	 * @author nellyoliviab@hexaware.com
	 * @date 21/Agosto/2024
	 */
	public String prepararMes(String mes) {
		int caracterer=3;
		mes= mes.toUpperCase();
		if(mes.compareTo("SEPTIEMBRE")==0) {
			caracterer=4;
		}
		mes=mes.substring(0, caracterer);
		return mes;
	}
}