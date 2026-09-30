package WEB.inventarioEspacios.om;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class PedidosAdministracionIEP {
	
	SeleniumUtil u;
	TestCase testCase;
	
	MetodosExtra me;
	
	/**
	 * Localizadores Tipo de Pedido
	 ******************************************************************************************/
	
	By xmlTituloPantalla = By.id("titulo_gral");
	
	By xmlMatPopAccesorios = By.xpath("//*[@id=\"mat-radio-2\"]");
	By xmlFaltante = By.xpath("//*[@id=\"mat-radio-3\"]");
	By xmlAdicional = By.xpath("//*[@id=\"mat-radio-4\"]");
	By xmlApertura = By.xpath("//*[@id=\"mat-radio-5\"]");
	By xmlSiguienteBtnTP = By.xpath("//span[contains(text(),'Siguiente')]");
	
	/**
	 * Localizadores Datos receptor
	 ******************************************************************************************/
	
	By xmlPlaza = By.id("mat-select-0");
	By xmlSubPlaza = By.id("mat-select-2");
	By xmlDireccion = By.id("mat-select-4");
	By xmlPeriodo = By.id("mat-select-10");
	By xmlTienda = By.id("mat-select-12");
	By xmlTiendaE = By.id("mat-select-16");
	
	By xmlPlazaOp = By.xpath("//div[@id=\"mat-select-0-panel\"]/mat-option/span");
	By xmlSubPlazaOp = By.xpath("//div[@id=\"mat-select-2-panel\"]/mat-option/span");
	By xmlDireccionOp = By.xpath("//div[@id=\"mat-select-4-panel\"]/mat-option/span");
	By xmlPeriodoOp = By.xpath("//div[@id=\"mat-select-10-panel\"]/mat-option/span");
	By xmlTiendaOp = By.xpath("//div[@id=\"mat-select-12-panel\"]/mat-option/span");
	By xmlTiendaEOp = By.xpath("//div[@id=\"mat-select-16-panel\"]/mat-option/span");
	
	By xmlPeriodoActual = By.xpath("//mat-select[@formcontrolname='periodosCtrl']//span/span");
	String xmlPeriodoPosicion = "//div[@id='mat-select-10-panel']/mat-option[%s]/span";
	
	By xmlSiguienteBtn2 = By.xpath("/html/body/app-root/mat-sidenav-container/mat-sidenav-content/app-order-create/div/mat-horizontal-stepper/div[2]/div[2]/form/div[2]/div/button[2]");
	By xmlDatosReceptor = By.xpath("/html/body/app-root/mat-sidenav-container/mat-sidenav-content/app-order-create/div/mat-horizontal-stepper/div[2]/div[2]/form/div[1]/div");
	
	/**
	 * Localizadores Seleccionar Pop's
	 ******************************************************************************************/
	
	By xmlMatExpansionFiltros = By.xpath("//*[@id=\"cdk-step-content-0-2\"]/form/div[1]/div/mat-accordion/mat-expansion-panel");
	By xmlFiltros = By.xpath("//*[@id=\"mat-expansion-panel-header-2\"]");
	By xmlTipoPedido = By.id("mat-select-10");
	By xmlTipoPedidoOp = By.xpath("//div[@id=\"mat-select-10-panel\"]/mat-option/span");
	By xmlNombrePop = By.xpath("//*[@id=\"mat-input-0\"]");
	By xmlBuscarBtn = By.xpath("//*[@id=\"formFiltroPop\"]/div[2]/div/div/button");
	
	By xmlTable = By.xpath("//*[@id=\"cdk-step-content-0-2\"]/form/div[1]/div/div[2]");
	By xmlTableTr = By.xpath("//*[@id=\"cdk-step-content-0-2\"]/form/div[1]/div/div[2]/table/tbody/tr");
	By xmlTableTh = By.xpath("//*[@id=\"cdk-step-content-0-2\"]/form/div[1]/div/div[2]/table/thead/tr/th");
	
	String xmlCantidad = "//*[@id=\"cdk-step-content-0-2\"]/form/div[1]/div/div[2]/table/tbody/tr[%s]/td[7]/div";
	String xmlCantidadM = "//*[@id=\"cdk-step-content-0-2\"]/form/div[1]/div/div[2]/table/tbody/tr/td[5]/div";
	String xmlCantidadAD = "//*[@id=\"cdk-step-content-0-2\"]/form/div[1]/div/div[2]/table/tbody/tr/td[5]/div";
	
	//*[@id="cdk-step-content-0-2"]/form/div[1]/div/div[2]/table/tbody/tr/td[5]/div
	
	String xmlAgregar = "//*[@id=\"cdk-step-content-0-2\"]/form/div[1]/div/div[2]/table/tbody/tr/td[6]/button";
	String xmlAgregarM = "//*[@id=\"cdk-step-content-0-2\"]/form/div[1]/div/div[2]/table/tbody/tr/td[6]/button/span[1]";
	String xmlAgregarAp = "//*[@id=\"cdk-step-content-0-2\"]/form/div[1]/div/div[2]/table/tbody/tr[%s]/td[6]/button";


	By xmlBotones = By.xpath("//*[@id=\"cdk-step-content-0-2\"]/form/div[2]");
	By xmlSiguienteBtnSP = By.xpath("//*[@id=\"cdk-step-content-0-2\"]/form/div[2]/div/button[3]");
	By xmlAnteriorBtnSP = By.xpath("//*[@id=\"cdk-step-content-0-2\"]/form/div[2]/div/button[2]");
	By xmlGuardarBtnSP = By.xpath("//span[contains(text(),'Guardar')]");
	By xmlConfirmarBtn = By.xpath("//span[contains(text(),'Confirmar')]");
	//*[@id="cdk-step-content-0-2"]/form/div[2]/div/button[2]/span[1]
	
	By xmlMensajeFolio = By.xpath("//snack-bar-container/div/div/simple-snack-bar/span");
	
	/**
	 * Localizadores Generar Pedido
	 ******************************************************************************************/
	
	By xmlTableGP = By.xpath("//*[@id=\"cdk-step-content-0-3\"]/div[2]");
	By xmlTableTrGP = By.xpath("//*[@id=\"cdk-step-content-0-3\"]/div[2]/table/tbody/tr");
	By xmlTableThGP = By.xpath("//*[@id=\"cdk-step-content-0-3\"]/div[2]/table/thead/tr/th"); 
	
	By xmlComentarios = By.xpath("//*[@id=\"mat-input-2\"]");
	By xmlGuardarBtnGP = By.xpath("//*[@id=\"cdk-step-content-0-3\"]/div[3]/div/button[1]");
   By xmlGuardarBtnGP2 = By.xpath("//*[@id=\"cdk-step-content-1-3\"]/div[3]/div/button[1]");
 //*[@id="cdk-step-content-1-3"]/div[3]/div/button[1]
	By xmlCrearBtn =  By.xpath("//*[@id=\"cdk-step-content-0-3\"]/div[3]/div/button[3]");
	By xmlCrearBtn2 =  By.xpath("//*[@id=\"cdk-step-content-1-3\"]/div[3]/div/button[3]");
	//*[@id="cdk-step-content-1-3"]/div[3]/div/button[3]
	By xmlBandejaPedidos = By.xpath("//*[@id=\"tituloEscritorio\"]/ol/li[2]");
	
	String xmlComentario = "//*[@id=\"mat-input-13\"]";
	//*[@id="mat-input-13"]
	
	/**
	 * Constructor
	 ******************************************************************************************/
	
	public PedidosAdministracionIEP(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}
	
	/*
	 * METODOS - Apartado Tipo de Pedido
	 ******************************************************************************************/
	

	/**
	 * M�todo que valida se muestren la pantalla 'Crear Pedidos > Nuevo'
	 * 
	 * @return Regresa true si se muestran la pantalla o false si no se muestran
	 * @author Andrea Contreras
	 */
	public Boolean visualizarPantalla() {
        me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		boolean validacion = false;
		String pantallaActual = u.getText(xmlTituloPantalla);
		if (pantallaActual.contains("Crear Pedidos")) {
			validacion = true;
		}

		return validacion;
	}
	
	/**
	 * M�todo para seleccionar radioButton segun el tipo de Nuevo pedido que se vaya a generar
	 * @param nuevoPedido Recibe el tipo de nuevo pedido
	 * @author Mar�a Due�as
	 */
	public void seleccionarNuevoPedido(String nuevoPedido) {
		
		switch (nuevoPedido) {
			case "Material Pop y Accesorios": clickNuevoPedido(xmlMatPopAccesorios);
				break;
				
			case "Faltante": clickNuevoPedido(xmlFaltante);	
				break;
			
			case "Adicional": clickNuevoPedido(xmlAdicional);	
				break;
				
			case "Apertura": clickNuevoPedido(xmlApertura);
				break;

		
		}
		
	}
	
	/**
	 * M�todo que da click en el radio button del tipo de nuevo pedido
	 * @param by Recibe el localizador del radio button correspondiente
	 * @author Mar�a Due�as
	 */
	public void clickNuevoPedido(By by) {
		u.highLight(by);
		testCase.addScreenShotCurrentStep("clickNuevoPedido"); 
		u.click(by);
		
	}
	
	public void clickFaltante() {
    	u.click(xmlFaltante);
    	testCase.addScreenShotCurrentStep("clickFaltante"); 
	}
	
	public void clickAdicional() {
    	u.click(xmlAdicional);
    	testCase.addScreenShotCurrentStep("clickAdicional"); 
	}
	
	public void clickApertura() {
    	u.click(xmlApertura);
    	testCase.addScreenShotCurrentStep("clickApertura"); 
	}
	
	/**
	 * M�todo para obtener el xpath del boton siguiente en la pesta�a de Tipo Pedido
	 * @return xmlSiguienteBtnTP  Regresa el xpath del boton Siguiente
	 * @author Mar�a Due�as
	 */
	public By getSiguienteBtnTP() {
		return xmlSiguienteBtnTP;
	}
	
	
	/*
	 * METODOS - Apartado Datos Receptor
	 ******************************************************************************************/
	
	public void clickSiguienteBtn2() {
		testCase.addScreenShotCurrentStep("clickSiguienteBtn2");
		me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
    	u.click(xmlSiguienteBtn2);
	}
	
	/**
	 * Metodo para validar que el sistema despliegue la ventana Datos Receptor
	 * @return Retorna true o false si la ventana se muestra correctamente
	 * @author Mar�a Due�as
	 */
	
	public Boolean validarCamposDatosReceptor() {
		
		me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		Boolean exist = false;
		exist = me.validarVisualizacion(xmlDatosReceptor);
		return exist;
		
	}
	
	public Boolean MPADatosReceptor(String option, String option2, String option3) {
		Boolean existOptions = false;
		Boolean plaza = selectOption(xmlPlaza, xmlPlazaOp, option);
		u.hardWait(1);
		Boolean subplaza = selectOption(xmlSubPlaza, xmlSubPlazaOp, option2);
		u.hardWait(1);
		Boolean direccion = selectOption(xmlDireccion, xmlDireccionOp, option3);
		u.hardWait(1);
		if (plaza == true && subplaza == true && direccion == true) {
			existOptions = true;
		}
		return existOptions;
	}
	
	public Boolean FDatosReceptorPorPosicionPeriodo(String posicionPeriodo, String option2, String option3, String option4, String option5) {
		Boolean existOptions = false;		
		By periodoOp = By.xpath(String.format(xmlPeriodoPosicion, posicionPeriodo));
		
		Boolean periodo = selectOption(xmlPeriodo, periodoOp);
		Boolean plaza = selectOption(xmlPlaza, xmlPlazaOp, option2);
		Boolean subplaza = selectOption(xmlSubPlaza, xmlSubPlazaOp, option3);
		Boolean tienda = selectOption(xmlTienda, xmlTiendaOp, option4);
		Boolean direccion = selectOption(xmlDireccion, xmlDireccionOp, option5);
		if (periodo == true && plaza == true && subplaza == true && tienda == true && direccion == true) {
			existOptions = true;
		}
		return existOptions;
	}
	
	public String obtenerPeriodoSeleccionado() {
		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(xmlPeriodoActual));		
		String periodo = element.getText();

		return periodo;
	}
	
	public Boolean FDatosReceptor(String option, String option2, String option3, String option4, String option5) {
		Boolean existOptions = false;
		Boolean periodo = selectOption(xmlPeriodo, xmlPeriodoOp, option);
		Boolean plaza = selectOption(xmlPlaza, xmlPlazaOp, option2);
		Boolean subplaza = selectOption(xmlSubPlaza, xmlSubPlazaOp, option3);
		Boolean tienda = selectOption(xmlTienda, xmlTiendaOp, option4);
		Boolean direccion = selectOption(xmlDireccion, xmlDireccionOp, option5);
		if (periodo == true && plaza == true && subplaza == true && tienda == true && direccion == true) {
			existOptions = true;
		}
		return existOptions;
	}
	
	
	public Boolean ADatosReceptor(String option, String option2, String option3, String option4) {
		Boolean existOptions = false;
		Boolean periodo = selectOption(xmlPeriodo, xmlPeriodoOp, option);
		Boolean plaza = selectOption(xmlPlaza, xmlPlazaOp, option2);
		Boolean subplaza = selectOption(xmlSubPlaza, xmlSubPlazaOp, option3);
		//Boolean tienda = selectOption(xmlTienda, xmlTiendaOp, option4);
		Boolean direccion = selectOption(xmlDireccion, xmlDireccionOp, option4);
		if (periodo == true && plaza == true && subplaza == true  && direccion == true) {
			existOptions = true;
		}
		return existOptions;
	}
	
	public Boolean ADatosReceptorPorPosicionPeriodo(String posicionPeriodo, String option2, String option3, String option4) {
		Boolean existOptions = false;
		By periodoOp = By.xpath(String.format(xmlPeriodoPosicion, posicionPeriodo));
		Boolean periodo = selectOption(xmlPeriodo, periodoOp);
		Boolean plaza = selectOption(xmlPlaza, xmlPlazaOp, option2);
		Boolean subplaza = selectOption(xmlSubPlaza, xmlSubPlazaOp, option3);
		//Boolean tienda = selectOption(xmlTienda, xmlTiendaOp, option4);
		Boolean direccion = selectOption(xmlDireccion, xmlDireccionOp, option4);
		if (periodo == true && plaza == true && subplaza == true  && direccion == true) {
			existOptions = true;
		}
		return existOptions;
	}
	
	
	public Boolean ApDatosReceptor(String option, String option2, String option3, String option4, String option5) {
		Boolean existOptions = false;
		Boolean periodo = selectOption(xmlPeriodo, xmlPeriodoOp, option);
		Boolean plaza = selectOption(xmlPlaza, xmlPlazaOp, option2);
		Boolean subplaza = selectOption(xmlSubPlaza, xmlSubPlazaOp, option3);
		Boolean tienda = selectOption(xmlTienda, xmlTiendaOp, option4);
		Boolean direccion = selectOption(xmlDireccion, xmlDireccionOp, option5);
		if (periodo == true && plaza == true && subplaza == true && tienda == true && direccion == true) {
			existOptions = true;
		}
		return existOptions;
	}
	

	/*
	 * METODOS - Apartado Seleccionar POPs
	 ******************************************************************************************/
	
	public void clickFiltro() {
		u.highLight(xmlFiltros);
    	u.click(xmlFiltros);
    	testCase.addScreenShotCurrentStep("clickFiltro");
	}
	
	public void typingNombrePop(String dato) {
		u.sendKeys(xmlNombrePop, dato);
		u.highLight(xmlNombrePop);
		testCase.addScreenShotCurrentStep("typingNombrePop");
		u.unhighLight(xmlNombrePop);
		u.hardWait(2);
		clickBuscarBtn();
		u.hardWait(5);
	}
	
	public void clickBuscarBtn() {
		u.highLight(xmlBuscarBtn);
		testCase.addScreenShotCurrentStep("clickBuscarBtn");
    	u.click(xmlBuscarBtn);
    	u.unhighLight(xmlBuscarBtn);
	}
	
	public void selectTipoPedido(String option) {
		me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		selectOption(xmlTipoPedido, xmlTipoPedidoOp, option);
		clickBuscarBtn();
		testCase.addScreenShotCurrentStep(u, "selectTipoPedido");
		u.unhighLight(xmlTipoPedido);
	}
	 
	 /**
	  * Metodo para validar que se visualice pantalla de Seleccionar POPs
	  * @return Retorna true o false si se visualiza correctamente la pantalla
	  * @author Mar�a Due�as
	  */
	 public Boolean validarPantallaSeleccionarPOPs() {
			
			me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
			Boolean exist = false;
			exist = me.validarVisualizacion(xmlMatExpansionFiltros);
			return exist;
			
		}
	 
	 public Boolean resultadosBusquedaPOPs(int col) {
		 Boolean resultado = false;
		 resultado = resultadosBusqueda(col, xmlTableTh, xmlTableTr, xmlTable);
		 return resultado;
	 }
	 
	 /**
	  * M�todo para validar que se muestren los botones Guardar, anterior y siguiente en la pantalla Seleccionar POPs
	  * @return Retorna true o false si se muestran los tres botones
	  * @author Mar�a Due�as
	  */
	 public Boolean validarBotonesPOPs() {
		 
		 Boolean exist = false;

		 Boolean guardar = me.existElement(xmlSiguienteBtnSP);
		 Boolean anterior = me.existElement(xmlAnteriorBtnSP);
		 Boolean siguiente = me.existElement(xmlSiguienteBtnSP);
		 
		 if (guardar == true && anterior == true && siguiente == true) {
			 exist = true;
		 }
		 
		 u.highLight(xmlBotones);
		 testCase.addScreenShotCurrentStep(u, "validarBotonesPOPs");
		 u.unhighLight(xmlBotones);
		 
		 return exist;
	 }
	 
	 /**
	  * M�todo que ingresa cantidad a los POP's
	  * @author Maria Due�as
	  * @param cantidad Numero
	  */
	 public Boolean agregarCantidad(String cantidad, String NuevoPedido) {
		 
		 Boolean add = false;
		 String cant = "";
		 String agregar = "";
		 
		 switch(NuevoPedido) {
			 case "Material Pop y Accesorios":
				 	cant = xmlCantidadM;
				 	agregar = xmlAgregarM;
				 break;
			 case "Apertura":
				    agregar = xmlAgregarAp;
				 break;
			 case "Adicional":
				 	cant = xmlCantidadAD;
				 	agregar = xmlAgregar;
				 break;
			 default: 
				 	cant = xmlCantidad;
				 	agregar = xmlAgregar;
				 break;
		 }
		 
		 
		for (int i = 1; i < 6; i++) {
				
			String agregarBtn = String.format(agregar, Integer.toString(i));
			
			if (!u.getText(By.xpath(agregarBtn)).trim().equals("Quitar")) {
				if (Integer.parseInt(cantidad) != 0) {
					 
					String row = String.format(cant, Integer.toString(i));

					if(me.existElement(By.xpath(row))){
						u.clear(By.xpath(row));
						u.sendKeys(By.xpath(row), cantidad);
							 
					} 
						 
				}
				
				me.explicitWaitToBeInvisible(Duration.ofSeconds(10));					 
				u.click(By.xpath(agregarBtn));
				if (popSinProveedor()) {
					add = true;
					break;
				}
			}
				 
		}
		
		me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		 
		 return add;
		 
	 }
	 
	 /**
	  * M�todo para guardar los Pops agregados al pedido
	  * @author Mar�a Due�as
	  */
	 public void guardarPops() {
		 clickBoton(xmlGuardarBtnSP);
		 me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		 clickBoton(xmlConfirmarBtn);
		 me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		 
	 }
	 /**
	  * M�todo para generar parte del folio que se genera del pedido
	  * PLAZA + TIPO  DE PEDIDO + FECHA +FOLIO EJEMPLO: 10POZ_ANDATTI Y COMIDA R�PIDA_DIC2014_12075
	  * @author Mar�a Due�as
	  * @param plaza tipoPedido
	  * @return cadena con texto que se genera en el folio PLAZA + TIPO  DE PEDIDO + FECHA +FOLIO EJEMPLO: 10POZ_ANDATTI Y COMIDA R�PIDA_DIC2014_
	  */
	 public String generarFolio3(String periodo, String plaza, String tipoPedido) {
		 
		 String cadena = "";
		 String [] p = plaza.split(" - ");
		 
	/*	 Calendar c1 = Calendar.getInstance();
		 int annio = c1.get(Calendar.YEAR);
		 
		// Obtienes el mes actual
		 Month mes = LocalDate.now().getMonth();

		 // Obtienes el nombre del mes
		 String nombre = mes.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));*/
		 
		 cadena = periodo + "_" + p[1] + "_" + tipoPedido.toUpperCase() + "_" ;//+ nombre.substring(0, 3).toUpperCase() + annio + "_"; 
		 System.out.print(cadena);
		 
		 return cadena;
	 }
	 
	 /**
	  * M�todo para generar parte del folio que se genera del pedido
	  * PLAZA + TIPO  DE PEDIDO + FECHA +FOLIO EJEMPLO: 10POZ_ANDATTI Y COMIDA R�PIDA_DIC2014_12075
	  * @author Mar�a Due�as
	  * @param plaza tipoPedido
	  * @return cadena con texto que se genera en el folio PLAZA + TIPO  DE PEDIDO + FECHA +FOLIO EJEMPLO: 10POZ_ANDATTI Y COMIDA R�PIDA_DIC2014_
	  */
	 public String generarFolio(String plaza, String tipoPedido) {
		 
		 String cadena = "";
		 String [] p = plaza.split(" - ");
		 
	/*	 Calendar c1 = Calendar.getInstance();
		 int annio = c1.get(Calendar.YEAR);
		 
		// Obtienes el mes actual
		 Month mes = LocalDate.now().getMonth();

		 // Obtienes el nombre del mes
		 String nombre = mes.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));*/
		 
		 cadena = p[1] + "_" + tipoPedido.toUpperCase() + "_" ;//+ nombre.substring(0, 3).toUpperCase() + annio + "_"; 
		 System.out.print(cadena);
		 
		 return cadena;
	 }
	 
	 /**
	  * Metodo para generar parte del folio que se genera del pedido
	  * PLAZA + TIPO  DE PEDIDO + FOLIO EJEMPLO: 10POZ_ANDATTI Y COMIDA RAPIDA_12075
	  * @author Maria Due�as
	  * @param plaza tipoPedido
	  * @return cadena con texto que se genera en el folio PLAZA + TIPO  DE PEDIDO +FOLIO EJEMPLO: 10POZ_ANDATTI Y COMIDA RAPIDA_
	  */
	 public String generarFolio2(String plaza, String tipoPedido) {
		 
		 String cadena = "";
		 String [] p = plaza.split(" - ");
		 
		 cadena = p[1] + "_" + tipoPedido.toUpperCase() + "_"; 
		// System.out.print(cadena);
		 
		 return cadena;
	 }
	 
	 /**
	  * M�todo para validar que se muestre correctamente el folio del pedido
	  * @author Mar�a Due�as
	  * @param cadena
	  * @return Retorna el folio generado (Solo los ultimos digitos)
	  */
	 public String mensajeFolio(String cadena) {
		 String mensaje = "";
		 String msj = "";
		 
		try {
			me.explicitWaitVisibilityOfElement(xmlMensajeFolio, Duration.ofSeconds(70));
			msj = u.getText(xmlMensajeFolio);
			u.highLight(xmlMensajeFolio);
			 
			 testCase.addScreenShotCurrentStep("mensajeFolio");
			 System.out.print(msj);
			 u.unhighLight(xmlMensajeFolio);
			 
			 if (msj.length() > 1) {
				 if(msj.contains(cadena)) {
					String [] m = msj.split(" : ");
					mensaje = m[1];
				 }
			 }
		}catch(Exception e) {
			System.out.print("No aparece mensaje con folio\n");
		}
		 System.out.print("FOLIO:"+mensaje);
	     me.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		 
		 return mensaje.trim();
	 }
	 
	 /**
	  * Metodo para verificar si se pueden agregar pops al pedido
	  * @author Mar�a Due�as
	  * @return Retorna false si no se puede agregar el pop o true si se puede agregar
	  */
	 public Boolean popSinProveedor() {
		 Boolean mensaje = true;
		 String msj = "";
		 Boolean visible;
		 u.hardWait(2);
		 
		 try {
			 visible = u.getDriver().findElement(xmlMensajeFolio).isDisplayed();
		 }catch(Exception e) {
			 visible = false;
		 }
		
			
		 if (visible) {
		 	msj = u.getText(xmlMensajeFolio);
			 
			 if (msj.equals("El POP no puede ser utilizado ya que no tiene proveedor asignado")) {
				 mensaje = false; 
				 u.highLight(xmlMensajeFolio);
				 testCase.addScreenShotCurrentStep(u, "mensaje");
				 //System.out.print(msj);
				 u.unhighLight(xmlMensajeFolio);
				 me.explicitWaitInvisibilityOfElement(xmlMensajeFolio, Duration.ofSeconds(60));
			 }
		 }
		
		 return mensaje;
	 }
	 
	 /**
	  * M�todo para obtener xpath de boton Siguiente
	  * @return Retorna xpath
	  * @author Mar�a Due�as
	  */
	 public By getSiguienteBtnSP() {
		 return xmlSiguienteBtnSP;
	 }
	
	 /**
	  * M�todo para obtener xpath de boton Anterior
	  * @return Retorna xpath
	  * @author Mar�a Due�as
	  */
	 public By getAnteriorBtnSP() {
		 return xmlAnteriorBtnSP;
	 }
	 
	 public Integer cantidadPops(int col) {
		 Integer resultado = 0;
		 resultado = filasTabla(col, xmlTableTh, xmlTableTr, xmlTable);
		 return resultado;
	 }
	 /*
	  * METODOS - Apartado Generar Pedidos
	  ******************************************************************************************/
	 
	 public Boolean resultadosBusquedaGenerarPedido(int col) {
		 Boolean resultado = false;
		 me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		 resultado = resultadosBusqueda(col, xmlTableThGP, xmlTableTrGP, xmlTableGP);
		 return resultado;
	 }
	 
	 public By getComentarios() {
		 return xmlComentarios;
	 }
	 
	 public By getGuardarGP() {
		 return xmlGuardarBtnGP;
	 }
	 public By getCrear() {
		 return xmlCrearBtn;
	 }
	 
	 public By getGuardarGP2() {
		 return xmlGuardarBtnGP2;
	 }
	 public By getCrear2() {
		 return xmlCrearBtn2;
	 }
	
	 public By getConfirmar() {
		 return xmlConfirmarBtn;
	 }
	 
	 public Boolean bandejaPedidos() {
		 Boolean exist = false;
		 me.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		 u.hardWait(30);
		 String bp = u.getText(xmlBandejaPedidos);
		 if (bp.contentEquals("Bandeja pedidos")) {
			 u.scrollToElement(xmlBandejaPedidos);
			 exist = true;
		 }
		 testCase.addScreenShotCurrentStep("bandejaPedidos");
		 
		 return exist;
	 }
	
	public void typingComentarios(Integer r, String comentario) {
		 String input = String.format(xmlComentario, r);
		 u.highLight(By.xpath(input));
		 u.sendKeys(By.xpath(input), comentario);
		 testCase.addScreenShotCurrentStep("typingComentarios");
		 u.unhighLight(By.xpath(input));
	}
	/*
	 * Metodos extra 
	 *****************************************************************************************/
	
	
	 public Boolean selectOption(By select, By opcion) { 
			Boolean existOption = false;
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(select));
			u.hardWait(10);
			element.click();
			u.hardWait(7);
			testCase.addScreenShotCurrentStep("selectOption");
			if(me.existElement(opcion)) {
				u.hardWait(3);
				u.click(opcion);
				u.hardWait(1);
				existOption = true;
			}
            me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
			u.highLight(select);
			testCase.addScreenShotCurrentStep("selectOption");
			u.unhighLight(select);
			return existOption;
		}
	 
	 
	 public Boolean selectOption(By select, By opciones, String opcion) { 
			Boolean existOption = false;
			//WebElement element =  me.explicitWaitToBeClickable(select, 30);
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(select));
			u.hardWait(10);
			element.click();
			u.hardWait(7);
			testCase.addScreenShotCurrentStep("selectOption");
			List<WebElement> allOptions = u.getDriver().findElements(opciones);
			
			for(WebElement option:allOptions){
				//System.out.print("-"+option.getText()+"-"+opcion+"-\n");
				if(option.getText().equals(opcion)) {//busca la opcion que se pasa por parametro
				  u.hardWait(3);
				  option.click(); //da clic a esa opcion 
				  u.hardWait(1);
				  existOption = true;
				  break;
				}
			}
			me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
			u.highLight(select);
			testCase.addScreenShotCurrentStep("selectOption");
			u.unhighLight(select);
			return existOption;
		}
	 
	public Boolean resultadosBusqueda(int col, By tableTh, By tableTr, By table) {
		Boolean resultado = false;
		u.hardWait(5);
		me.explicitWaitToBeInvisible(Duration.ofSeconds(60));
		List <WebElement> tablaTh = u.getDriver().findElements(tableTh);
		Integer col_count = tablaTh.size();
		List <WebElement> tablaTr = u.getDriver().findElements(tableTr);
		Integer row_count = tablaTr.size();
		
		if (row_count >= 1 && col_count == col) {
			resultado = true;
		}
		
		me.scrollToElement(table);
		u.highLight(table);
		testCase.addScreenShotCurrentStep("resultadosBusqueda");
		u.unhighLight(table);
		
		return resultado;
	}
	
	/**
	 * Metodo para verificar que haya m�s de un Pop para realizar pedido
	 * @param col
	 * @param tableTh
	 * @param tableTr
	 * @param table
	 * @return Retorna el numero de filas que hay en la tabla sin contar el encabezado
	 * @author Mar�a Due�as
	 */
	public Integer filasTabla(int col, By tableTh, By tableTr, By table) {
		Integer resultado = 0;
		
		List <WebElement> tablaTh = u.getDriver().findElements(tableTh);
		Integer col_count = tablaTh.size();
		List <WebElement> tablaTr = u.getDriver().findElements(tableTr);
		Integer row_count = tablaTr.size();
		
		if (row_count >= 2 && col_count == col) {
			resultado = row_count;
		}
		
		me.scrollToElement(table);
		
		return resultado;
	}
	
	public void clickBoton(By by) {
		me.explicitWaitToBeInvisible(Duration.ofSeconds(30));
		u.highLight(by);		
		testCase.addScreenShotCurrentStep("clickBoton");
		me.explicitWaitToBeInvisible(Duration.ofSeconds(30));
		u.unhighLight(by);
		me.explicitWaitToBeInvisible(Duration.ofSeconds(30));
		u.click(by);
	}
	
	public void typingInput(By by, String dato) {
		me.explicitWaitToBeInvisible(Duration.ofSeconds(30));
		u.sendKeys(by, dato);
		u.highLight(by);
		testCase.addScreenShotCurrentStep("typingInput");
		u.unhighLight(by);
	}
	

}
