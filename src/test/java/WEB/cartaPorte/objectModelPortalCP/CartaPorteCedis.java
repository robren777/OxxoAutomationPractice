package WEB.cartaPorte.objectModelPortalCP;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class CartaPorteCedis {
	SeleniumUtil u;
	TestCase testCase;
	PortalCartaPorte portal;
	private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);
		
	//Casillas
	By casillaOrigen=By.xpath("//input[@id='origen']");
	By casillaRuta=By.xpath("//input[@placeholder='Ruta']");
	By casillaOla=By.xpath("//input[@placeholder='Ola']");
	By casillaTransferencia=By.xpath("//input[@placeholder='Transferencia']");
	By nombreChofer=By.xpath("//input[@placeholder='Nombre Chofer']");
	By casillaNumeroEconomico=By.xpath("//div[@class='col3 form-group autocomplete']/input");
	By casillaPlacas=By.xpath("//input[@placeholder='Placas']");
	By casillaSeguro=By.xpath("//input[@placeholder='Seguro']");
	By capturaCorreo=By.xpath("//div[@class='mat-chip-list-wrapper']/input");
	
	//Texto
	By textoFechaFacturacion=By.xpath("//label/span[@class='rojo']");
	By textoEstatusTransferencia=By.xpath("//label[text()='Estatus Transferencia']");
	
	By textoMensaje=By.xpath("//div[@class='overlay-container']/div/div");
	
	//Boton 
	By botonAnio=By.xpath("//button[@class='mat-focus-indicator mat-calendar-period-button mat-button mat-button-base']");
	By fechaFactura=By.xpath("//div[@class='col2 form-group datepicker']/mat-datepicker-toggle/button");
	By botonBuscar=By.xpath("//div/button[@class='btn ng-star-inserted']");
	By seleccionarTienda=By.xpath("//div[@class='envelope']/div/form/div/div[2]/div[4]/div[1]/table/thead/tr/th/mat-checkbox");
	By seleccionDerecha=By.xpath("//div[@class='form-container']/div[@class='row modulo']/div[2]/button[2]");
	By fechainicialBOL=By.xpath("//div[@class='col2 form-group datepickerBol']/mat-datepicker-toggle/button");
	By botonDevolucion=By.xpath("//div[@class='col3 right']/button");
	By seleccionarDevolcion1= By.xpath("//div[@class='form-container']/div[6]/app-carta-porte-devolucion/div[2]/div[1]/table/thead/tr/th/mat-checkbox");
	By seleccionDerechaDevolucion=By.xpath("//div[@class='form-container']/div[6]/app-carta-porte-devolucion/div[2]/div[2]/button[2]");
	By seleccionarCartaPorte=By.xpath("//button[text()='Generar Carta Porte']");
	By botonEnviar=By.xpath("//button[text()='Enviar']");
	By reSeleccionaTiendas=By.xpath("//*[@id=\"mat-checkbox-3\"]/label");
	By seleccionarCheckDerecha1=By.xpath("//table/thead/tr/th/mat-checkbox[@id='mat-checkbox-10']");
	By botonAlerta=By.xpath("//div[@class='alert-footer mt-3 mb-3']/button[@class='btn warn']");
	By primerElementoTabla=By.xpath("//div[@class='envelope']/div/form/div/div[2]/div[4]/div[1]/table/tbody/tr[1]");
	By primerElementoTablaDerecha=By.xpath("//table/tbody/tr[1]");
	By primerElementoTablaDevolucion=By.xpath("//div[@class='form-container']/div[6]/app-carta-porte-devolucion/div[2]/div/table/tbody/tr[1]");
	
	By seleccionarTienda2=By.xpath("//*[@id=\"mat-checkbox-7\"]/label/span[1]");
	By reSeleccionaTiendas2=By.xpath("//*[@id=\"mat-checkbox-8\"]/label/span[1]");
	By elementoBol=By.xpath("//div[@class='col5 p-10']/table[@class='mat-table cdk-table table-tienda']/tbody/tr[1]");
	
	By seleccionarDevolcion5= By.xpath("//table/thead/tr/th/mat-checkbox[@id='mat-checkbox-4']");
	
	By tablaBusqueda= By.xpath("//div[@class='form-container']/div[@class='row modulo']/div[1]/table");
	By tablaDevolucion1= By.xpath("/html/body/app-root/div/div/app-carta-porte-view/div/div/form/div[1]/div[2]/div[6]/app-carta-porte-devolucion/div[2]/div[1]");
	By tablaDevolucion5= By.xpath("//table[@class='mat-table cdk-table table-tienda']/tbody/tr/td/mat-checkbox[@id='mat-checkbox-23']");
	By tablaDevolucion8= By.xpath("//table[@class='mat-table cdk-table table-tienda']/tbody/tr/td/mat-checkbox[@id='mat-checkbox-47']");
	
	By seleccionarCheckDerecha5=By.xpath("//table/thead/tr/th/mat-checkbox[@id='mat-checkbox-5']");
	
	By botonLimpiar=By.xpath("//button[text()='Limpiar datos']");
	
	//NUEVOS CAMBIOS CARTA PORTE 3
	By casillaPesoBruto=By.xpath("//*[@id=\"pesoBrutoV\"]");
	By seleccionaRegistroIstmo=By.xpath("//mat-checkbox[@id='checkItsmo']");
	By casillaPoloOrigen=By.xpath("//input[@placeholder='Polo Origen']");
	By casillaPoloDestino=By.xpath("//input[@placeholder='Polo Destino']");
	By seleccionLogisticaInversa= By.xpath("//form/div/div[2]/div[8]/div[2]/mat-checkbox/label/span");
	By estatusLogisticaInversa=By.xpath("//form/div/div[2]/div[8]/div[2]/mat-checkbox/label/span/input");
	
	public CartaPorteCedis(SeleniumUtil u, TestCase testCase, PortalCartaPorte portal) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.portal = portal;
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
	/**
	 * Metodo para buscar un valor en la lista seleccionable que se despliega de las cajas de Texto
	 * 
	 * @param nombreCampo-----------nombre del elemento WEB al que se le tomara la captura
	 * 
	 * @return boolean------- Se regresara true si el valor fue encontrado y seleccionado y false si este no se encontro
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	
	public boolean buscarYSeleccionarTextoDesplegadoDeCajaDeTexto(String valor) {
		boolean busquedaExitosa=false;
		try {
			u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			By seleccionTextoActual=By.xpath("//span[text()=' "+valor+" ']");
			u.getDriver().findElement(seleccionTextoActual).click();
			busquedaExitosa= true;
		}catch (Exception e) {
			logger.error("Error" + e);
			System.out.println("Error" + e);
		}
		return busquedaExitosa;
	}
	/**
	 * Metodo para capturar casillas de la pagina de carta porte cedis
	
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
		//WebDriverWait wait= new WebDriverWait(u.getDriver(), 240);
		By elementoCasilla=null;
		boolean capturaExitosa=false;
		elementoCasilla=obtenerElementoWEB(nombreCampo,"");
		if(elementoCasilla!=null) {
			u.getDriver().findElement(elementoCasilla).clear();
			u.getDriver().findElement(elementoCasilla).sendKeys(valor);
			capturaExitosa=true;
			if(textoSeleccionable) {
				capturaExitosa=buscarYSeleccionarTextoDesplegadoDeCajaDeTexto(valor);
			}
			if(captura) {
				agregarCapturaDeEvidencia(nombreCampo, elementoCasilla);
			}
		}
		return capturaExitosa;
	}
	/**
	 * Metodo para esperar y obtener el mensaje de timbrado (exitoso o no exitoso)
	 * @return bandera---- true cuando el timbrado fue exitoso y false cuando no es exitoso.
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean determinarEstatusTimbrado() {
		String mensaje=null;
		boolean bandera=false;
		WebDriverWait wait= new WebDriverWait(u.getDriver(), Duration.ofSeconds(360));
		By textoMensaje=By.xpath("//div[@class='overlay-container']/div/div");
		wait.until(ExpectedConditions.visibilityOfElementLocated(textoMensaje));
		mensaje=u.getDriver().findElement(textoMensaje).getText();
		testCase.addScreenShotCurrentStep("MensajeTimbrado");
		testCase.addTextEvidenceCurrentStep("Mensaje de timbrado: "+mensaje);
		String mensajeCompeto[]= mensaje.split("\\W+");
		System.out.println("Timbrado: "+mensaje);
		for(String busqueda: mensajeCompeto) {
			if("exitosamente".compareToIgnoreCase(busqueda)==0) {
			bandera= true;
			}
		}
		return bandera;
			
	}
	
	/**
	 * Metodo para encontrar elementos de la pagina de carta porte cedis
	
	 * @param nombreCampo-----------nombre del campo a capturar
	 * @param infoAdicional---------información adicional para encontrar el elemento web esperado
	 * @return regresa true si la busqueda fue exitosa
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean encontrarElemento(String nombreCampo, String infoAdicional) {
		By elementoWEB= obtenerElementoWEB(nombreCampo, infoAdicional);
		boolean busquedaExitosa=false;
		if(elementoWEB!=null) {
			WebDriverWait wait= new WebDriverWait(u.getDriver(), Duration.ofSeconds(140));
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(elementoWEB));
				busquedaExitosa=true;
			}catch (Exception e) {
				logger.error("Error" + e);
				System.out.println("Error" + e);
			}
		}
		return busquedaExitosa;
	}
	
	
	/**
	 * Metodo para obtener el elemento web con el que se espera interactuar
	
	 * @param campoActual---------------- Nombre del campo para identificar al elemento 
	 * @param infoAdicional-------------- Información adicional para encontrar el elemento WEB
	 * @return regresa el elemento web como By para continuar con las diferentes acciones necesarias
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	
	public By obtenerElementoWEB(String campoActual, String infoAdicional) {
		WebDriverWait wait= new WebDriverWait(u.getDriver(), Duration.ofSeconds(120));
		By elementoCasilla=null;
		int contador = 0;
		do {
			try {
				switch(campoActual) {
					case "anio":
						By anio=By.xpath("//td/div[text()=' "+infoAdicional+" ']");
						elementoCasilla=anio;
					break;
					case "botonAnio":
						elementoCasilla=botonAnio;
					break;
					case "botonBuscar":
						elementoCasilla=botonBuscar;
					break;
					case "botonDevolucion":
						elementoCasilla=botonDevolucion;
					break;
					case "botonEnviar":
						elementoCasilla=botonEnviar;
					break;
					case "botonLimpiar":
						elementoCasilla=botonLimpiar;
					break;	
					case "capturaCorreo":
						WebDriverWait wait2= new WebDriverWait(u.getDriver(), Duration.ofSeconds(600));
						wait2.until(ExpectedConditions.visibilityOfElementLocated(capturaCorreo));
						elementoCasilla=capturaCorreo;
					break;
					case "dia":
						By dia=By.xpath("//tr/td/div[text()=' "+infoAdicional+" ']");
						elementoCasilla=dia;
					break;
					case "estatusTransferencia":
						elementoCasilla=textoEstatusTransferencia;
					case "fechaFacturacion":
						elementoCasilla=textoFechaFacturacion;
					break;
					case "fechaFacturacionSeleccion":
						elementoCasilla=fechaFactura;
					break;
					case "fechainicialBOL":
						elementoCasilla=fechainicialBOL;
					break;	
					case "flecha":
						elementoCasilla=seleccionDerechaDevolucion;
					break;
					case "logisticaInversa":
						elementoCasilla=seleccionLogisticaInversa;
					break;
					case "mensaje":
						String msj=u.getDriver().findElement(textoMensaje).getText();
						testCase.addScreenShotCurrentStep("Mensaje");
						if(msj.compareToIgnoreCase("Favor de seleccionar Polo Origen/Destino")==0) {
							return textoMensaje;
						}
					break;					
					case "mes":
						String nombreMes=prepararMes(infoAdicional);
						By mes=By.xpath("//tr/td/div[text()=' "+nombreMes+" ' ]");
						elementoCasilla=mes;
					break;
					case "nombreChofer":
						elementoCasilla=nombreChofer;
					break;
					case "numeroEconomico":
						//wait.until(ExpectedConditions.visibilityOfElementLocated(casillaNumeroEconomico));
						elementoCasilla=casillaNumeroEconomico;
					break;
					case "ola":
						wait.until(ExpectedConditions.visibilityOfElementLocated(casillaOla));
						elementoCasilla=casillaOla;
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
						elementoCasilla=check1;
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
						elementoCasilla=check2;
					break;
					case "origen":
						elementoCasilla=casillaOrigen;
					break;
					case "pesoBruto":
						elementoCasilla=casillaPesoBruto;	
						break;
					case "poloOrigen":
						try {
							contador=10;
							WebDriverWait w= new WebDriverWait(u.getDriver(), Duration.ofSeconds(1));
							w.until(ExpectedConditions.visibilityOfElementLocated(casillaPoloOrigen));
						}catch (org.openqa.selenium.NoSuchElementException e) {
							System.out.print("ENTREEE");
							return null;
						}	
						break;
					case "poloDestino":
						try {
							contador=10;
							WebDriverWait w= new WebDriverWait(u.getDriver(), Duration.ofSeconds(1));
							w.until(ExpectedConditions.visibilityOfElementLocated(seleccionaRegistroIstmo));
						}catch (org.openqa.selenium.NoSuchElementException e) {
							return null;
						}
						break;
					case "registroIstmo":
						try {
							contador=10;
							WebDriverWait w= new WebDriverWait(u.getDriver(), Duration.ofSeconds(1));
							w.until(ExpectedConditions.visibilityOfElementLocated(seleccionaRegistroIstmo));
						}catch (org.openqa.selenium.NoSuchElementException e) {
							return null;
						}
					break;
					case "ruta":
						wait.until(ExpectedConditions.visibilityOfElementLocated(casillaRuta));
						elementoCasilla=casillaRuta;	
					break;
					case "seleccionarCartaPorte":
						elementoCasilla=seleccionarCartaPorte;
					break;
					case "seleccionDerecha":
						elementoCasilla=seleccionDerecha;	
					break;
					case "seleccionarDevolcion":
						wait.until(ExpectedConditions.visibilityOfElementLocated(seleccionarDevolcion1));
						elementoCasilla=seleccionarDevolcion1;
					break;
					case "seleccionaTiendas":
						wait.until(ExpectedConditions.visibilityOfElementLocated(primerElementoTabla));
						elementoCasilla=seleccionarTienda;
					break;
					case "tablaDevolucion":
						wait.until(ExpectedConditions.visibilityOfElementLocated(primerElementoTablaDevolucion));
						elementoCasilla=primerElementoTablaDevolucion;
					break;
					case "tablaRegistros":
						elementoCasilla=tablaBusqueda;
					break;
					case "transferencia":
						wait.until(ExpectedConditions.visibilityOfElementLocated(casillaTransferencia));
						elementoCasilla=casillaTransferencia;
					break;
				}
			}catch (Exception e) {
				contador ++;
			}
		}while(elementoCasilla==null && contador <10);
		return elementoCasilla;
	}
	
	/**
	 * Metodo para obtener la opcion segun el numero de check2 enviado
	
	 * @param check2-----------opcion de check a seleccionar (1, 2 o 3)
	 * 
	 * @return regresa la opcion en texto equivalente al numero enviado
	 * 
	 * @author nellyoliviab@hexaware.com
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
	 * Metodo para seleccionar elementos de la pagina de carta porte cedis
	
	 * @param nombreCampo-----------nombre del campo a seleccionardos
	 * @param captura---------------true para sacar una captura de pantalla en la evidencia, false cuando no es necesario 
	 * @param infoAdicional---------informacion adicional requerida para algunos campor
	 * 
	 * @return regresa true si la seleccion fue exitosa
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean seleccionarElemento(String nombreCampo, boolean captura, String infoAdicional) {
		boolean seleccionExitosa=false;
		By elementoWEB=obtenerElementoWEB(nombreCampo, infoAdicional);
		if(elementoWEB!=null) {
			if(captura) {
				agregarCapturaDeEvidencia(nombreCampo, elementoWEB);
			}
			try {
				u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
				u.getDriver().findElement(elementoWEB).click();	
				seleccionExitosa=true;
			}catch (Exception e) {
				logger.error("Error" + e);
				System.out.println("Error" + e);
			}
		}
		u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		return seleccionExitosa;
	}	
	
	
	
	
	
	/**
	 * Metodo para verificar que los campos de placa y seguro se auntollenen una vez capturados los datos de nombreChrofer
	 * y numeroEconomico
	 * 
	 * @return regresa true si la verificacion fue exitosa
	 * 
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
				u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				u.highLight(obtenerTexto);
				testCase.addScreenShotCurrentStep(nombreCampo);
				u.unhighLight(obtenerTexto);
				return u.getDriver().findElement(obtenerTexto).getAttribute("value");
			case "Origen":
				obtenerTexto=casillaOrigen;
				break;
			case "Descripcion":
				obtenerTexto=casillaRuta;
			break;
			case "SeleccionTiendas":
				obtenerTexto=primerElementoTabla;
				if(u.getDriver().findElement(primerElementoTabla).isSelected()) {
					return "Seleccionado";
				}else {
					return null;
				}
			case "primerElementoTablaDerecha":
				obtenerTexto=primerElementoTablaDerecha;
				if(u.getDriver().findElement(primerElementoTablaDerecha).isSelected()) {
					return "Seleccionado";
				}else {
					return null;
				}
			case "SeleccionTiendasBOL":
				if(u.getDriver().findElement(elementoBol).isSelected()) {
					return "Seleccionado";
				}else {
					return null;
				}
			case "SeleccionTiendasDerechaBOL":
				if(u.getDriver().findElement(tablaDevolucion1).isSelected() || u.getDriver().findElement(tablaDevolucion5).isSelected()) {
					return "Seleccionado";
				}else {
					return null;
				}
			case "NombreChofer":
				obtenerTexto=nombreChofer;
				break;
			case "NumeroEconomico":
				obtenerTexto=casillaNumeroEconomico;
				break;
			case "RegistroIstmo":
				if(u.getDriver().findElement(seleccionaRegistroIstmo).isSelected()) {
					return "Seleccionado";
				}else {
					return null;
				}
			case "estatusLogisticaInversa":
				String status=u.getDriver().findElement(estatusLogisticaInversa).getAttribute("aria-checked");
				if(status.compareToIgnoreCase("true")==0) {
					agregarCapturaDeEvidencia(nombreCampo, seleccionLogisticaInversa);
					return "Seleccionado";
				}else {
					return null;
				}
			}
			String text=u.getDriver().findElement(obtenerTexto).getText();
			if(text.compareTo("")==0){
				return null;
			}
			return u.getDriver().findElement(obtenerTexto).getText();		
		} catch (Exception e) {
			logger.error("Error" + e);
			System.out.println("Error" + e);
			return null;
		}
	}
	
	/**
	 * Método para convertir un peso de kilogramos a toneladas.
	 * @param campoActual El peso en kilogramos como una cadena.
	 * @return El peso en toneladas como una cadena, o null si el parámetro es null.
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
	 * @return El peso bruto hasta el punto decimal como una cadena.
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
	 * Método para buscar y quitar una alerta en la interfaz de usuario.
	 * @author nellyoliviab@hexaware.com
	 * @date 21/Agosto/2024
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
	 * Método para esperar hasta que un elemento sea visible en la interfaz de usuario.
	 * @param elementoWeb El elemento web que se desea esperar a que sea visible.
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
			}catch(org.openqa.selenium.NoSuchElementException NOs) {
				continuar=false;
			}
		}while(!continuar);
	}
}
