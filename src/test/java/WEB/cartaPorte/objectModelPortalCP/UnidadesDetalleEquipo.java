package WEB.cartaPorte.objectModelPortalCP;

import java.time.Duration;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class UnidadesDetalleEquipo {
	SeleniumUtil u;
	TestCase testCase;
	
	//Texto 
	By textoDetalleEquipo=By.xpath("//div[@class='current-screen p-3 ng-star-inserted']");
	By textoMensaje=By.xpath("//div[@class='overlay-container']/div/div");
	
	//Casilla captura
	By capturaIdentificador=By.xpath("//input[@id='identificador']");
	By capturaOrigen=By.xpath("//input[@id='origen']");
	By capturaConfVehicular=By.xpath("//input[@formcontrolname='confVehicular']");
	By capturaSerie=By.xpath("//input[@formcontrolname='serie']");
	By capturaModelo=By.xpath("//input[@formcontrolname='modelo']");
	By capturaSubtipoRemolque=By.xpath("//input[@formcontrolname='subtipoRemolque']");
	By capturaCapacidad=By.xpath("//*[@id=\"capacidadT\"]");
	By capturaAseguradora=By.xpath("//input[@formcontrolname='aseguradora']");
	By capturaPermisoSct=By.xpath("//input[@formcontrolname='permisoSCT']");
	By capturaCrOrigen=By.xpath("//input[@formcontrolname='crOrigen']");
	By capturaPlaca=By.xpath("//input[@formcontrolname='placa']");
	By capturaPlacaRemolque=By.xpath("//input[@formcontrolname='placaRemolque']");
	By capturaPesoBrutoVehicular=By.xpath("//*[@id=\"pesoVehicular\"]");
	By capturaNumeroPoliza=By.xpath("//input[@formcontrolname='poliza']");
	By capturaNumeroPermisoSCT=By.xpath("//input[@formcontrolname='comentarios']");
	
	//Captura autollenado
	By obtenerUnidadOperativa=By.xpath("//div[@class='col4 form-group ng-invalid ng-dirty ng-touched']/input[@formcontrolname='unidadOperativa']");
	By obtenerDescripcion=By.xpath("//input[@formcontrolname='descripcion']");
	By obtenerRemolque=By.xpath("//input[@formcontrolname='remolque']");
	
	By obtenerMensajeAlerta=By.xpath("//div[@class='ng-tns-c38-60 toast-message ng-star-inserted']");
	
	
	//Elemento Seleccion
	By botonVigencia=By.xpath("//div[@class='col4 form-group datepicker ng-invalid ng-dirty ng-touched']/mat-datepicker-toggle");
	By botonAnio=By.xpath("//button[@class='mat-focus-indicator mat-calendar-period-button mat-button mat-button-base']");
	By botonGuardar=By.xpath("//button[text()=' GUARDAR ']");
	
	By botonCancelar=By.xpath("//button/span[text()=' Cancelar ']");
	public UnidadesDetalleEquipo(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}
	
	
	/**
	 * Metodo para encontrar elementos de la pagina de detalle de equipo
	
	 * @param nombreCampo-----------nombre del campo a capturar
	 * 
	 * @return regresa true si la busqueda fue exitosa
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean encontrarElemento(String nombreCampo) {
		try {
		switch(nombreCampo) {
			case "textoDetalleDeEquipo":
				if(u.getDriver().findElement(textoDetalleEquipo).isDisplayed()) {
					return true;
				}
				return false;	
		}
		} catch (Exception e) {
			System.out.println("Error" + e);
			return false;
		}
		return false;
	}
	
	
	
	/**
	 * Metodo para capturar casillas de la pagina de detalle de equipo en Unidades
	
	 * @param valor---------------- valor a capturar en casilla
	 * @param nombreCampo-----------nombre del campo a capturar
	 * @param textoSeleccionable----true si el texto se despliega al ir escribiendo o false cuando no se requiere un valor de los mostrados
	 * @param captura---------------true para sacar una captura de pantalla en la evidencia, false cuando no es necesario 
	 * 
	 * @return regresa true si la captura fue exitosa
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean capturarCasilla(String valor, String nombreCampo, boolean textoSeleccionable, boolean captura) {
		By elementoCasilla=null;
		WebDriverWait wait= new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
		try {
			switch(nombreCampo) {
			case "capturaIdentificador":
				wait.until(ExpectedConditions.visibilityOfElementLocated(capturaIdentificador));
				elementoCasilla=capturaIdentificador;
				break;
			case "capturaOrigen":
				elementoCasilla=capturaOrigen;
				break;
			case "confVehicular":
				elementoCasilla=capturaConfVehicular;
				break;
			case "serie":
				elementoCasilla=capturaSerie;
				break;
			case "modelo":
				elementoCasilla=capturaModelo;
				break;
			case "subtipoRemolque":
				elementoCasilla=capturaSubtipoRemolque;
				break;
			case "capacidad":
				elementoCasilla=capturaCapacidad;
				break;
			case "aseguradora":
				elementoCasilla=capturaAseguradora;
				break;
			case "permisoSct":
				elementoCasilla=capturaPermisoSct;
				break;
			case "crOrigen":
				elementoCasilla=capturaCrOrigen;
				break;
			case "placa":
				elementoCasilla=capturaPlaca;
				break;
			case "placaRemolque":
				elementoCasilla=capturaPlacaRemolque;
				break;
			case "pesoBrutoVehicular":
				elementoCasilla=capturaPesoBrutoVehicular;
				break;
			case "numeroPoliza":
				elementoCasilla=capturaNumeroPoliza;
				break;
			case "numPermisoSCT":
				wait.until(ExpectedConditions.visibilityOfElementLocated(capturaNumeroPermisoSCT));
				u.hardWait(10);
				elementoCasilla=capturaNumeroPermisoSCT;
				break;
			
			}
			u.highLight(elementoCasilla);
			u.getDriver().findElement(elementoCasilla).clear();
			u.getDriver().findElement(elementoCasilla).sendKeys(valor);
			u.hardWait(3);
			if(captura) {
				testCase.addScreenShotCurrentStep("Captura"+nombreCampo);
			}
			if(textoSeleccionable) {
				u.hardWait(3);
				By seleccionTextoActual=By.xpath("//span[text()=' "+valor+" ']");
				u.getDriver().findElement(seleccionTextoActual).click();
			}
			u.unhighLight(elementoCasilla);
			return true;
		} catch (Exception e) {
			System.out.println("Error" + e);
			return false;
		}
	}
	
	/**
	 * Metodo para obtener el autollenado de casillas de la pagina de detalle de equipo en Unidades
	
	 * @param campo-----------------nombre del campo a obtener la información
	 * @param captura---------------true para sacar una captura de pantalla en la evidencia, false cuando no es necesario 
	 * 
	 * @return regresa el texto que se obtiene de la casilla
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public String obtenerAutollenado(String campo, boolean captura) {
		By elementoCasilla=null;
		try {
			switch(campo) {
			case "obtenerUnidadOperativa":
				elementoCasilla=obtenerUnidadOperativa;
				u.hardWait(5);
				break;
			case "obtenerDescripcion":
				elementoCasilla=obtenerDescripcion;
				u.hardWait(5);
				break;
			case "obtenerRemolque":
				elementoCasilla=obtenerRemolque;
				u.hardWait(5);
				break;
		//borrar		
			case "obtenerSerie":
				elementoCasilla=capturaSerie;
				u.hardWait(5);
				break;	
			case "pesoBruto":
				elementoCasilla=capturaPesoBrutoVehicular;
				u.hardWait(5);
				break;
			}
			if(captura) {
				testCase.addScreenShotCurrentStep("Captura"+campo);
			}
			String texto=u.getDriver().findElement(elementoCasilla).getAttribute("value");
			return texto;
		} catch (Exception e) {
			System.out.println("Error" + e);
			return "";
		}
	}
	
	/**
	 * Metodo para seleccionar elementos de la pagina de carta porte cedis
	
	 * @param nombreCampo-----------nombre del campo a seleccionardos
	 * @param captura---------------true para sacar una captura de pantalla en la evidencia, false cuando no es necesario
	 * @param infoAdicional---------información adicional del elemento seleccionable(año, mes y día)
	 * @return regresa true si la seleccion fue exitosa
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean seleccionarElemento(String nombreCampo, boolean captura, String infoAdicional) {
		//WebDriverWait wait= new WebDriverWait(u.getDriver(), 120);
		By elementoSeleccion=null;
		boolean seleccion=false;
		try {
			switch(nombreCampo) {
				case "botonVigencia":
					elementoSeleccion=botonVigencia;
					seleccion=true;
				break;
				case "botonAnio":
					elementoSeleccion=botonAnio;
					seleccion=true;
				break;
				case "vigenciaAnio":
					By anio=By.xpath("//td/div[text()=' "+infoAdicional+" ']");
					elementoSeleccion=anio;
					seleccion=true;
				break;
				case "vigenciaMes":
					String nombreMes=prepararMes(infoAdicional);
					By mes=By.xpath("//tr/td/div[text()=' "+nombreMes+" ' ]");
					elementoSeleccion=mes;
					seleccion=true;
				break;
				case "vigenciaDia":
					By dia=By.xpath("//tr/td/div[text()=' "+infoAdicional+" ']");
					elementoSeleccion=dia;
					seleccion=true;
				break;
				case "botonCancelar":
					elementoSeleccion=botonCancelar;
					seleccion=true;
				break;
			}
			u.highLight(elementoSeleccion);
			if(captura) {
				testCase.addScreenShotCurrentStep("Seleccion"+nombreCampo);
			}	
			if(nombreCampo.compareTo("vigenciaAnio")!=0 && nombreCampo.compareTo("vigenciaMes")!=0&& nombreCampo.compareTo("vigenciaDia")!=0) {
				u.unhighLight(elementoSeleccion);
			}
			u.getDriver().findElement(elementoSeleccion).click();	
			
			return seleccion;
		} catch (Exception e) {
			System.out.println("Error" + e);
			return false;
		}
	}
	
	/**
	 * Metodo para obtener los primeros 3 caracteres del mes enviado a excepcion del mes de septiembre, del cual se requieren
	 * los primeros 4 caracteres para buscarlos y seleccionarlos en las opciones del fecha
	 * 
	 * @param mes-----------nombre del mes enviado
	 * 
	 * @return regresa los caracteres del mes requeridos
	 * 
	 * @author nellyoliviab@hexaware.com
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
	
	/**
	 * Metodo para obtener mensaje al momento de Guardar la nueva unidad
	 * 
	 * @return regresa mensaje de alerta en caso de existir
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public String guardarUnidad() {
		WebDriverWait wait= new WebDriverWait(u.getDriver(), Duration.ofSeconds(15));
		String alerta=null;
		u.highLight(botonGuardar);
		testCase.addScreenShotCurrentStep("GuardarUnidad");
		u.unhighLight(botonGuardar);
		int contador=0;
		do {
			u.getDriver().findElement(botonGuardar).click();
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(textoMensaje));
				u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
				alerta=u.getDriver().findElement(textoMensaje).getText();
				if(alerta.contains("actualizado")) {
					return "¡Equipoactualizadoexitosamente!";
				}else {
					if(alerta.contains("agregado")) {
						return "Equipoagregadoexitosamente!";
					}else {
						return alerta;
					}
				}
			}catch(Exception e) {
				
			}
			contador++;
		}while(contador<4);
		return alerta;
	}
	
	 /**
	 * Método para obtener el texto de una casilla.
	 * @param nombreCampo Nombre del campo del que se desea obtener el texto.
	 * @param captura True para sacar una captura de pantalla en la evidencia, false cuando no es necesario.
	 * @return Regresa el texto obtenido de la casilla.
	 * @author nellyoliviab@hexaware.com
	 * @date 21/Agosto/2024
	 */
	public String obtenerTexto(String nombreCampo, boolean captura) {
		By elementoSeleccion=null;
		String texto=null;
		try {
			switch(nombreCampo) {
				case "capacidad":
					elementoSeleccion=capturaCapacidad;
				break;
				case "pesoBrutoVehicular":
					elementoSeleccion=capturaPesoBrutoVehicular;
				break;
				case "numeroPoliza":
					elementoSeleccion=capturaNumeroPoliza;
				break;
			}
			if(captura) {
				u.highLight(elementoSeleccion);
				testCase.addScreenShotCurrentStep("ObtenerTexto"+nombreCampo);
				u.unhighLight(elementoSeleccion);
			}
			texto=u.getDriver().findElement(elementoSeleccion).getAttribute("value");
			return texto;
		} catch (Exception e) {
			System.out.println("Error" + e);
			return texto;
		}
	}
	
	/**
	 * Metodo mover y bajar el scroll de la pagina
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	
	public void bajarScroll(int pixeles) {
		JavascriptExecutor jse=(JavascriptExecutor)u.getDriver();
		jse.executeScript("window.scrollBy(0, "+pixeles+")");
	}
	
	/**
	 * Metodo para generar un numero de poliza ocn caracteres alfanumericos aleatorios
	 * 
	 * @param  longitus ---parametro para determinar la longitud del texot aleatorio a generar
	 * 
	 * @return regresa numero de poliza aleatorio respetando la longitud recibida
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	
	public String generarNumeroPoliza(int longitudPoliza) {
		String poliza="";
		Random random= new Random();
		int randomInt=0;
		String alfaberico= "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		do {
			boolean alfabetico=random.nextBoolean();
			if(alfabetico) {
				randomInt=random.nextInt(alfaberico.length());
				char randomChar=alfaberico.charAt(randomInt);
				poliza+=randomChar;
			}else {
				randomInt=random.nextInt(10);
				poliza+=randomInt;
			}
		}while(poliza.length()<longitudPoliza);
		return poliza;
	}
	
}
