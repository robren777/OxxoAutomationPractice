package WEB.cartaPorte.objectModelPortalCP;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class AdministracionUnidades {
	SeleniumUtil u;
	TestCase testCase;
	
	//Boton 
	By botonNuevoEquipo=By.xpath("//button[@class='btn']");	
	By botonBuscar=By.xpath("//button[@class='mat-focus-indicator mat-mini-fab mat-button-base mat-primary']");
   
    By botonAlerta=By.xpath("//div[@class='alert-footer mt-3 mb-3']/button[@class='btn warn']");
    
    By botonSiguiente=By.xpath("//button[@class='mat-focus-indicator mat-tooltip-trigger mat-paginator-navigation-next mat-icon-button mat-button-base']");
    By botonRemove=By.xpath("//button[@class='mat-focus-indicator mat-icon-button mat-button-base']");
	//Casilla
	By capturaOrigen=By.xpath("//input[@id='origen']");
	
	
	public AdministracionUnidades(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}
	
	
	/**
	 * Metodo para seleccionar elementos de la pagina de administracion -- Unidades
	
	 * @param nombreCampo-----------nombre del campo a seleccionardos
	 * @param captura---------------true para sacar una captura de pantalla en la evidencia, false cuando no es necesario 
	 *  @param adicional---------------Información adicional en caso de ser requerida
	 * 
	 * @return regresa true si la seleccion fue exitosa
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean seleccionarElemento(String nombreCampo, boolean captura, String adicional) {
		By elementoSeleccion=null;
		boolean seleccion=false;
		try {
			switch(nombreCampo) {
				case "botonNuevoEquipo":
					elementoSeleccion=botonNuevoEquipo;
					seleccion=true;
				break;
				case "seleccionaBuscar":
					buscarYQuitarAlerta();	
					elementoSeleccion=botonBuscar;
					seleccion=true;
				break;
				case "seleccionaEditar":
					boolean continuar=false;
					elementoSeleccion=By.xpath("//tbody/tr["+adicional+"]/td[10]/button");
					do {
						u.getDriver().findElement(elementoSeleccion).click();
						continuar=false;
						try {
							u.hardWait(2);
							if(u.getDriver().findElement(elementoSeleccion).isDisplayed()) {
								continuar=false;
							}
						}catch(Exception e) {
						 continuar=true;	
						}
					}while(!continuar);
					return true;
				case "selectRemove":
					elementoSeleccion=botonRemove;
					seleccion=true;
				break;
				case "selectCasillaOrigen":
					elementoSeleccion=capturaOrigen;
					seleccion=true;
				break;
			
			}
			u.highLight(elementoSeleccion);
			if(captura) {
				testCase.addScreenShotCurrentStep("Seleccion"+nombreCampo);
			}
			
			u.hardWait(2);
			u.unhighLight(elementoSeleccion);
			u.getDriver().findElement(elementoSeleccion).click();		
			if(elementoSeleccion!=null) {
				seleccion=true;
			}
			
			return seleccion;
		} catch (Exception e) {
			System.out.println("Error" + e);
			return false;
		}
	}
	
	/**
	 * Metodo para capturar casillas de la pagina de detalle de Administración/Unidades
	
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
		WebDriverWait wait= new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
		try {
			switch(nombreCampo) {
			case "capturaOrigen":
				esperarAqueSeMuestreElemento(capturaOrigen);
				elementoCasilla=capturaOrigen;
				break;
			}
			if(nombreCampo.compareTo("capturaOrigen")!=0) {
				u.highLight(elementoCasilla);
				u.getDriver().findElement(elementoCasilla).clear();
			}
			u.getDriver().findElement(elementoCasilla).sendKeys(valor);
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
			System.out.println("Error" + e);
			return false;
		}
	}
	/**
	 * Metodo para esperar a que la tabla de registros de Unidades se actualice segun el filtro de origen
	 * 
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public void esperarActualizaTabla(String origen) {
		buscarYQuitarAlerta();
		int contador=1;
		By elemento=By.xpath("//table/tbody/tr[1]/td[1]");
		WebDriverWait wait= new WebDriverWait(u.getDriver(), Duration.ofSeconds(120));
		wait.until(ExpectedConditions.visibilityOfElementLocated(elemento));
		do {
			elemento=By.xpath("//table/tbody/tr["+contador+"]/td[1]");
			wait.until(ExpectedConditions.visibilityOfElementLocated(elemento));
			if(elemento!=null) {
				String origenTabla=u.getDriver().findElement(elemento).getText();
				if(origenTabla!=null) {
					if(origenTabla.compareTo(origen)!=0) {
						contador=1;
					}else {
						contador++;
					}
				}
			}
			buscarYQuitarAlerta();
		}while(contador < 10);
	}
	
	/**
	 * Buscar la unidad segun el identificador esperado para posteriormente seleccionar la modificacion
	 * 
	 * @param identificador---------ID de la Unidad que se espera seleccionar
	 * @param captura---------------true para sacar una captura de pantalla en la evidencia, false cuando no es necesario 
	 * 
	 * @return regresa true si la accion es exitosa y false si no
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean buscaySeleccionaUnidad(String identificador, boolean captura) {
		boolean seleccionarCasilla=false;
		try {
			u.hardWait(5);
			int contador=1;
			boolean indicadorEncontrado=false;
			WebDriverWait wait= new WebDriverWait(u.getDriver(), Duration.ofSeconds(120));
			do {
				By obtenerRegistro= By.xpath("//tbody/tr["+contador+"]/td[5]");
				wait.until(ExpectedConditions.visibilityOfElementLocated(obtenerRegistro));
				if(obtenerRegistro!=null) {
					if(contador==1){
						u.getDriver().findElement(obtenerRegistro).click();
					}
					u.highLight(obtenerRegistro);
					
					String idenfiticadorRegistro=u.getDriver().findElement(obtenerRegistro).getText();
					idenfiticadorRegistro=idenfiticadorRegistro.replace(" ", "");
					identificador=identificador.replace(" ", "");
					if(contador==5) {
						buscarYQuitarAlerta();
					}
					if(idenfiticadorRegistro.equalsIgnoreCase(identificador)) {
						//System.out.println("Indicador: "+idenfiticadorRegistro);
						if(captura) {
							testCase.addScreenShotCurrentStep("RegistroEditar");
						}
						u.unhighLight(obtenerRegistro);
						seleccionarCasilla=seleccionarElemento("seleccionaEditar", false, String.valueOf(contador));
						return seleccionarCasilla;
					}	
					u.unhighLight(obtenerRegistro);
					contador++;
					if(contador==11) {
						//wait.until(ExpectedConditions.visibilityOfElementLocated(botonSiguiente));
						u.getDriver().findElement(botonSiguiente).click();
						contador=1;
					}
				}else {
					return seleccionarCasilla;
				}
			}while(!indicadorEncontrado);
		} catch (Exception e) {
			System.out.println("Error" + e);
			return false;
		}
		
		return seleccionarCasilla;
	}
	
	/**
	 * Metodo mover y bajar el scroll de la pagina
	 * 
	 * @param bajarScroll---------------- cantidad para bajar Scroll
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	
	public void bajarScroll(String bajarScroll) {
		JavascriptExecutor jse=(JavascriptExecutor)u.getDriver();
		jse.executeScript("window.scrollBy(0, "+bajarScroll+")");
	}
	
	/**
	 * Permite continuar el flujo cuando se presenta la alerta para cargar datos.
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public void buscarYQuitarAlerta() {
		boolean continuar=false;
		int intentos=1;
		do {
			try {
				if(u.getDriver().findElement(botonAlerta).isDisplayed()) {
					u.getDriver().findElement(botonAlerta).click();	
				}
			}catch(org.openqa.selenium.StaleElementReferenceException e) {
				continuar=false;
			}catch(org.openqa.selenium.NoSuchElementException NOs) {
				continuar=false;
			}
			intentos++;
			if(intentos>3) {
				continuar = true;
			}
		}while(!continuar);
	}
	/**
	 * Esperar a que se actualice la informacion esperada para continuar
	 * 
	 * @author nellyoliviab@hexaware.com
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
}
