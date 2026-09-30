package WEB.cartaPorte.objectModelPortalCP;

import java.io.File;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class CartaPorteManual {
	SeleniumUtil u;
	TestCase testCase;
	private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);
	PortalCartaPorte portal;
	
	//Casillas
	By casillaOrigen=By.xpath("//input[@id='origen']");
	By casillaDestino=By.xpath("//input[@id='destino']");
	By casillaFiltrar=By.xpath("//div[@class='col3 form-group']/input");
	By nombreChofer=By.xpath("//input[@id='mat-input-1']");
	By casillaNumeroEconomico=By.xpath("//input[@id='mat-input-2']");
	By casillaPlacas=By.xpath("//input[@id='mat-input-3']");
	By casillaSeguro=By.xpath("//input[@id='mat-input-4']");
	By casillaNombrarArchivo=By.xpath("//input[@id='validateExtension']");
	By capturaCorreo=By.xpath("//div[@class='mat-chip-list-wrapper']/input");
	//input
	By masiva=By.xpath("//div[@class='col2 right']/input");
	
	By casilla1Articulos=By.xpath("//table[@class='mat-table cdk-table table-tienda']/tbody/tr/td/mat-form-field/div/div/div[3]/input[@id='mat-input-11']");
	
	//Checkbox
	By checkCedis=By.xpath("//span[text()=' Cedis']");
	By checkPlaza=By.xpath("//span[text()=' Plaza']");
	By checkTerceros=By.xpath("//span[text()=' Terceros']");
	
	// texto CartaPorte
	By textMercancias=By.xpath("//div[@class='row ng-star-inserted']/div/div/h4");
	By textBuscarArticulos=By.xpath("//h2[text()='BUSCAR ARTICULOS']");
	
	By textoMensaje=By.xpath("//div[@class='overlay-container']/div/div");
	//Botones
	By botonAgregar=By.xpath("//div[@class='col2 left']/button");
	By botonBuscar=By.xpath("//span[@class='mat-button-wrapper']/mat-icon[text()='search']");
	By botonBuscarArticulo=By.xpath("//div[@class='col1 form-group']/button/span/mat-icon");
	By botonEnviar=By.xpath("//button[text()='Enviar']");
	By botonAlert=By.xpath("//button[@class='btn warn']");
	
	By botonLimpiarDatos= By.xpath("//button[text()='Limpiar datos']");
	
	By seleccionPaginadoAvanza=By.xpath("//button[@class='mat-focus-indicator mat-tooltip-trigger mat-paginator-navigation-next mat-icon-button mat-button-base']");
	
	By seleccionaCargaMasiva=By.xpath("//button[@class='mat-focus-indicator mat-flat-button mat-button-base mat-primary ng-star-inserted']");
	//Check
	By checkNormal=By.xpath("//span[text()=' Normal']");
	By checkEspecial=By.xpath("//span[text()=' Especial']");
	By seleccionarCSV=By.xpath("//label[@for='mat-radio-14-input']");
	By seleccionarXLS=By.xpath("//label[@for='mat-radio-15-input']");
	By generarArchivo=By.xpath("//button[text()='Generar Archivo']");
	By generarCartaPorte=By.xpath("//button[text()='Generar Carta Porte']");
	
	
	//NUEVOS CAMBIOS CARTA PORTE 3
	By casillaPesoBruto=By.xpath("//input[@id='pesoBrutoV']");
	By seleccionaRegistroIstmo=By.xpath("//mat-checkbox[@id='checkItsmo']/label/span");
	By casillaPoloOrigen=By.xpath("//input[@placeholder='Polo Origen']");
	By casillaPoloDestino=By.xpath("//input[@placeholder='Polo Destino']");
	By seleccionLogisticaInversa= By.xpath("//mat-checkbox[@id='checkInversa']/label/span[1]");
		
	By estatusLogisticaInversa=By.xpath("//mat-checkbox/label/span/input");
	public CartaPorteManual(SeleniumUtil u, TestCase testCase,PortalCartaPorte portal) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.portal= portal;
	}
	
	/**
	 * Metodo para capturar casillas de la pagina de carta porte manual
	
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
		WebDriverWait wait= new WebDriverWait(u.getDriver(), Duration.ofSeconds(15));
		By elementoCasilla=null;
		try {
			switch(nombreCampo) {
			case "origen":
				elementoCasilla=casillaOrigen;				
				break;
			case "destino":
				elementoCasilla=casillaDestino;				
				break;
			case "filtrar":
				elementoCasilla=casillaFiltrar;	
				break;
			case "nombreChofer":
				elementoCasilla=nombreChofer;	
				u.hardWait(2);
				break;
			case "numeroEconomico":
				elementoCasilla=casillaNumeroEconomico;	
				u.hardWait(2);
				break;
			case "nombrarArchivo":
				elementoCasilla=casillaNombrarArchivo;	
				u.getDriver().findElement(casillaNombrarArchivo).clear();
				break;
			case "capturaCorreo":
				wait.until(ExpectedConditions.visibilityOfElementLocated(capturaCorreo));
				u.getDriver().findElement(capturaCorreo).sendKeys(valor+Key.TAB);
				return true;
			case "pesoBruto":
				elementoCasilla=casillaPesoBruto;	
				break;
			case "poloOrigen":
				WebDriverWait w= new WebDriverWait(u.getDriver(), Duration.ofSeconds(1));
				w.until(ExpectedConditions.visibilityOfElementLocated(casillaPoloOrigen));
				break;
			case "poloDestino":
				elementoCasilla=casillaPoloDestino;	
				break;
			}
			if(elementoCasilla!=null) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(elementoCasilla));
			u.highLight(elementoCasilla);
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
	 * Metodo para encontrar elemento de la pagina de carta porte manual
	
	 * @param nombreCampo-----------nombre del campo a capturar
	 * 
	 * @return regresa true si la busqueda  fue exitosa
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean encontrarElemento(String nombreCampo) {
		WebDriverWait wait= new WebDriverWait(u.getDriver(), Duration.ofSeconds(15));
		try {
			switch(nombreCampo) {
			case "destino":
				if(u.getDriver().findElement(casillaDestino).isDisplayed()) {
					return true;
				}
				return false;
			case "limpiarDatos":
			if(u.getDriver().findElement(botonLimpiarDatos).isDisplayed()) {
				return true;
			}
			return false;	
			case "checkCedis":
				if(u.getDriver().findElement(checkCedis).isDisplayed()) {
					return true;
				}
				return false;	
			case "checkPlaza":
				if(u.getDriver().findElement(checkPlaza).isDisplayed()) {
					return true;
				}
				return false;
			case "checkTerceros":
				if(u.getDriver().findElement(checkTerceros).isDisplayed()) {
					return true;
				}
				return false;
			case "Mercancias":	
				if(u.getDriver().findElement(textMercancias).isDisplayed()) {
					return true;
				}
				return false;
			case "buscarArticulos":
				wait.until(ExpectedConditions.visibilityOfElementLocated(textBuscarArticulos));
				if(u.getDriver().findElement(textBuscarArticulos).isDisplayed()) {
					return true;
				}
				return false;
			case "filtrar":
				u.hardWait(4);
				if(u.getDriver().findElement(casillaFiltrar).isDisplayed()) {
					return true;
				}
				return false;
			case "numeroEconomico":
				u.hardWait(4);
				if(u.getDriver().findElement(casillaNumeroEconomico).isDisplayed()) {
					return true;
				}
				return false;
			case "pesoBruto":
				if(u.getDriver().findElement(casillaPesoBruto).isDisplayed()) {
					u.highLight(casillaPesoBruto);
					testCase.addScreenShotCurrentStep("PesoBrutoEncontrado");
					u.highLight(casillaPesoBruto);
					return true;
				}
				return false;
			case "mensaje":
				wait.until(ExpectedConditions.visibilityOfElementLocated(textoMensaje));
				String msj=u.getDriver().findElement(textoMensaje).getText();
				testCase.addScreenShotCurrentStep("Mensaje");
				if(msj.compareToIgnoreCase("Favor de seleccionar Polo Origen/Destino")==0) {
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
	 * Método para convertir un peso de kilogramos a toneladas.
	 * @param campoActual El peso en kilogramos como una cadena. Si el valor es nulo, se devuelve null.
	 * @return El peso en toneladas como una cadena, o null si el parámetro es nulo.
	 * @author nellyoliviab@hexaware.com
	 * @date 21/Agosto/2024
	 */
	public String convertirKiloEnTonelada(String campoActual) {
		if(campoActual!=null) {
				int pesoBrutoKilo=Integer.parseInt(campoActual);
				int pesoBrutoTonelada=pesoBrutoKilo/1000;
				return String.valueOf(pesoBrutoTonelada);
		}
		return null;
	}
	
	/**
	 * Método para obtener el peso bruto autollenado desde un campo de texto.
	 * @param campoActual Nombre del campo del que se desea obtener el peso bruto.
	 * @return El peso bruto hasta el punto decimal como una cadena. Si el campo es nulo o vacío, se devuelve una cadena vacía.
	 * @author nellyoliviab@hexaware.com
	 * @date 21/Agosto/2024
	 */
	public String obtenerPesoBrutoAutollenado(String campoActual) {
		String dato="";
		if(campoActual!=null) {
			campoActual=obtenerTexto(campoActual);
			int contador=0;
			do {
				dato+=campoActual.charAt(contador);
				contador++;
			}while(campoActual.charAt(contador)!='.');
		}
		
		return dato;
	}
	
	/**
	 * Metodo para seleccionar elemento de la pagina de carta porte manual
	
	 * @param nombreCampo-----------nombre del campo a seleccionar
	 * @param captura---------------true para sacar una captura de pantalla en la evidencia, false cuando no es necesario 
	 * 
	 * @return regresa true si la captura fue exitosa
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean seleccionarElemento(String nombreCampo, boolean captura) {
		WebDriverWait wait= new WebDriverWait(u.getDriver(), Duration.ofSeconds(15));
		By elementoSeleccion=null;
		boolean seleccion=false;
		try {
			switch(nombreCampo) {
			case "checkCedis":
				elementoSeleccion=checkCedis;
				break;
			case "checkPlaza":
				elementoSeleccion=checkPlaza;
				break;
			case "checkTerceros":
				elementoSeleccion=checkTerceros;
				break;
			case "botonBuscar":
				elementoSeleccion=botonBuscar;
				break;		
			case "botonAgregar":
				elementoSeleccion=botonAgregar;
				break;
			case "tipoArticuloNormal":
				elementoSeleccion=checkNormal;
				break;	
			case "tipoArticuloEspecial":
				elementoSeleccion=checkEspecial;
				break;	
			case "botonBuscarArticulo":
				wait.until(ExpectedConditions.visibilityOfElementLocated(botonBuscarArticulo));
				elementoSeleccion=botonBuscarArticulo;
				break;	
			case "seleccionarCSV":
				wait.until(ExpectedConditions.visibilityOfElementLocated(seleccionarCSV));
				elementoSeleccion=seleccionarCSV;
				break;	
			case "seleccionarXLS":
				elementoSeleccion=seleccionarXLS;
				break;	
			case "generarArchivo":
				elementoSeleccion=generarArchivo;
				break;	
			case "generarCartaPorte":
				elementoSeleccion=generarCartaPorte;
				break;
			case "botonEnviar":
				elementoSeleccion=botonEnviar;
				break;
			case "registroIstmo":
				WebDriverWait w= new WebDriverWait(u.getDriver(), Duration.ofSeconds(1));
				w.until(ExpectedConditions.visibilityOfElementLocated(seleccionaRegistroIstmo));
				break;
			case "cargaMasiva":
				elementoSeleccion=seleccionaCargaMasiva;
				break;
			case "logisticaInversa":
				if(u.getDriver().findElement(seleccionLogisticaInversa).isSelected()) {
					u.highLight(seleccionLogisticaInversa);
					if(captura) {
						testCase.addScreenShotCurrentStep("Seleccion"+nombreCampo);
					}
					u.unhighLight(seleccionLogisticaInversa);
					return true;
				}else {
					elementoSeleccion=seleccionLogisticaInversa;
					seleccion=true;
				}
				break;
			case "paginadoAvanza":
				elementoSeleccion=seleccionPaginadoAvanza;
				break;
			case "botonLimpiarDatos":
				elementoSeleccion=botonLimpiarDatos;
				break;
			}
			if(elementoSeleccion!=null) {
				u.highLight(elementoSeleccion);
				if(captura) {
					testCase.addScreenShotCurrentStep("Seleccion"+nombreCampo);
				}
				u.unhighLight(elementoSeleccion);
				u.getDriver().findElement(elementoSeleccion).click();		
				if(elementoSeleccion!=null) {
					seleccion=true;
				}
			}
			return seleccion;
		} catch (Exception e) {
			logger.error("Error" + e);
			System.out.println("Error" + e);
			return false;
		}
	}
	
	/**
	 * Metodo para obtener filtro de articulos
	
	 * @param tipoArticulo---------------- normal o especial
	 *
	 * @return  valor aleatorio obtenido
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public String obtenerValorArticulos(int tipoArticulo) {
		String valorFiltro="";
		if(tipoArticulo==1) {
			int numeroRandom=portal.generarNumeroAleatorio(20, 5);
			valorFiltro="00"+numeroRandom;
		}else {
			valorFiltro=String.valueOf(portal.generarNumeroAleatorio(200, 10));
		}
		return valorFiltro;
	}
	
	/**
	 * Metodo para seleccionar n articulo de forma aleatorea
	
	 * @param captura---------------true para sacar una captura de pantalla en la evidencia, false cuando no es necesario 
	 * 
	 * @return regresa true si la seleccion fue exitosa
	 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean seleccionaArticulos(boolean captura) {		
		WebDriverWait wait= new WebDriverWait(u.getDriver(), Duration.ofSeconds(10));
		try {
			int seleccionaValor=1;
			By tablaArticulosEspecial=By.xpath("//table[@class='mat-table cdk-table demo-table']/tbody/tr");
			wait.until(ExpectedConditions.visibilityOfElementLocated(tablaArticulosEspecial));
			List<WebElement> elementosTabla=u.getDriver().findElements(By.xpath("//table[@class='mat-table cdk-table demo-table']/tbody/tr[1]/td[3]/button"));
			if(elementosTabla.size()>1) {
				seleccionaValor=(int)Math.floor(Math.random()* (elementosTabla.size()-1+1))+1;
			}else {
				seleccionaValor=1;
			}
				By columnaRegistro=By.xpath("//table[@class='mat-table cdk-table demo-table']/tbody/tr["+seleccionaValor+"]/td[3]/button");
				u.highLight(columnaRegistro);
				u.getDriver().findElement(columnaRegistro).click();
				u.hardWait(2);
				if(captura) {
					testCase.addScreenShotCurrentStep("seleccionarArticulo"+seleccionaValor);
				}
				return true;
			
		} catch (Exception e) {
			logger.error("Error" + e);
			System.out.println("Error" + e);
			return false;
		}
	}
	
	/**
	 * Metodo para completar los valores de cantidad, peso-- del articulo aleatoreo seleccionado
	
	 * @param numeroRegistro---------------numero de registro en donde se encuentra el foco actual
	 * 
	 * @return regresa true si la captura fue exitosa
	 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean capturaDetalleArticulo(int numeroRegistro) {
		try {
			u.hardWait(5);
			By elementoActual=null;
			By opcionSeleccion=null;
			int contador=0;
			int contadorColumna=4;
			String tipo="input";
			do {
				elementoActual=By.xpath("//table/tbody/tr["+numeroRegistro+"]/td["+ contadorColumna +"]/mat-form-field/div/div/div[3]/"+tipo);
				u.highLight(elementoActual);
				if(contador<2) {
					u.getDriver().findElement(elementoActual).clear();
					u.getDriver().findElement(elementoActual).sendKeys(String.valueOf(portal.generarNumeroAleatorio(3, 1)));
				}else {
					u.getDriver().findElement(elementoActual).click();
					String opcionAleatoria = String.valueOf(portal.generarNumeroAleatorio(2, 1));
					opcionSeleccion=By.xpath("//div[@role='listbox']/mat-option["+opcionAleatoria+"]/span");
					u.getDriver().findElement(opcionSeleccion).click();
				}
				try {
					u.getDriver().findElement(botonAlert).click();
					}catch (Exception e) {
						
					}
				contador++;
				contadorColumna++;
				if(contador==2) {
					tipo="mat-select";
				}
				u.unhighLight(elementoActual);
			}while(contador<4);
			return true;
		} catch (Exception e) {
			logger.error("Error" + e);
			System.out.println("Error" + e);
			return false;
		}
	}
	

	/**
	 * Metodo para seleccionar icono de lupa al cargar articulos
	
	 * @param registro---------------numero de registro actual
	 * @param tipoArticulo-----------tipo de articulo normal o especial
	 * @param captura----------------bandera para agregar captura de pantalla a la evidencia
	 * 
	 * @return regresa true si la operacion fue exitosa
	 
	 * @author nellyoliviab@hexaware.com
	 */
	
	public boolean rellenaArticulos(int registro,String tipoArticulo, boolean captura) {
		try {
			By tablaBotonBuscar=By.xpath("//table/tbody/tr["+registro+"]/td[1]/mat-form-field/div/div/div[4]/button");	
			u.getDriver().findElement(tablaBotonBuscar).click();
			if(captura) {
				testCase.addScreenShotCurrentStep("rellenaArticulo");
			}
				return true;

		} catch (Exception e) {
			logger.error("Error" + e);
			System.out.println("Error" + e);
			return false;
		}
	}
	
	/**
	 * Metodo para verificar autollenado de placar y seguro al completar informacion de chofer: nombre y numero economico
	 * 
	 * @return regresa true si se autollenaron los datos
	 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean verificarAutollenado() {
		try {
			String placas=u.getDriver().findElement(casillaPlacas).getText();
			String seguro=u.getDriver().findElement(casillaSeguro).getText();
			if(placas!=null && placas!=""&& seguro!=null && seguro!="") {
				
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
	 * Metodo para subir un archivo en carga masiva
	 *
	 * @author nellyoliviab@hexaware.com
	 */
	public void cargaMasiva() {
		File file =new File("C:\\Users\\2000103650\\Documents\\CartaPorte\\CartaPorteMasiva.xlsx");
		String path= file.getAbsolutePath();
		//u.getDriver().findElement(seleccionaCargaMasiva).sendKeys(path);
		u.getDriver().findElement(masiva).sendKeys(path);
	}
	

	/**
	 * Metodo para identificar el tipo de articulo a seleccionar
	 * 
	 * @param tipoArticulo nombre del articulo: (especial y normal)
	 * @return articulo 1- para especial y 2 - para normal
	 
	 * @author nellyoliviab@hexaware.com
	 */
	public int determinaTipoArticulo(String tipoArticulo) {
		int articulo=0;
		if("especial".compareToIgnoreCase(tipoArticulo)==0) {
			articulo=1;
		}else {
			articulo=2;
		}
		return articulo;
	}
	
	
	
	 /**
	 * Método para determinar el estatus de timbrado basado en el mensaje mostrado en la interfaz.
	 * Espera hasta que el mensaje de timbrado sea visible y verifica si contiene la palabra "exitosamente".
	 * 
	 * @return Retorna `true` si el mensaje de timbrado contiene la palabra "exitosamente", de lo contrario `false`.
	 * @author nellyoliviab@hexaware.com
	 * @date 21/Agosto/2024
	 */
	public boolean determinarEstatusTimbrado() {
		String mensaje="";
		WebDriverWait wait= new WebDriverWait(u.getDriver(), Duration.ofSeconds(60));
		boolean elementoEncontrado=false;
		do {
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(textoMensaje));
				mensaje=u.getDriver().findElement(textoMensaje).getText();
				if(mensaje.compareTo("")!=0) {
					testCase.addScreenShotCurrentStep("MensajeTimbrado");
					testCase.addTextEvidenceCurrentStep("Mensaje de timbrado: "+mensaje);
					String mensajeCompeto[]= mensaje.split("\\W+");
					for(String busqueda: mensajeCompeto) {
						if("exitosamente".contains(busqueda)) {
							elementoEncontrado= true;
						}
					}
					return elementoEncontrado;
				}
			}catch(Exception e) {
				
			}
		}while(!elementoEncontrado);
		return elementoEncontrado;
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
		WebDriverWait wait= new WebDriverWait(u.getDriver(), Duration.ofSeconds(15));
		By obtenerTexto=null;
		try {
			switch(nombreCampo) {
			case "PesoBruto":
				obtenerTexto=casillaPesoBruto;
				u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				u.highLight(obtenerTexto);
				testCase.addScreenShotCurrentStep("pesovehicularAutollenado");
				u.unhighLight(obtenerTexto);
				return u.getDriver().findElement(obtenerTexto).getAttribute("value");
			case "Origen":
				obtenerTexto=casillaOrigen;
				break;
			case "Destino":
				obtenerTexto=casillaDestino;
				break;
			case "TablaArticulos":
				obtenerTexto=casilla1Articulos;
				break;
			case "NombreArchivo":
			obtenerTexto=casillaNombrarArchivo;
			break;
			case "NombreChofer":
				obtenerTexto=nombreChofer;
				break;
			case "NumeroEconomico":
				obtenerTexto=casillaNumeroEconomico;
				break;
			case "seleccionIstmo":
				if(u.getDriver().findElement(seleccionaRegistroIstmo).isSelected()==false) {
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
			case "seleccionLogistica":
				if(u.getDriver().findElement(seleccionLogisticaInversa).isSelected()==false) {
					return null;
				}
				return "Seleccionado";
			}
			String texto=u.getDriver().findElement(obtenerTexto).getText();
			if(texto.compareTo("")==0) {
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
