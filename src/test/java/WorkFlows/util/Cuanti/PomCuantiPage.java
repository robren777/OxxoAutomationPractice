package WorkFlows.util.Cuanti;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import modelo.BaseExecution;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class PomCuantiPage {
	
	private SikuliUtil sikuli = new SikuliUtil();
	private Screen screen = new Screen();
	private SeleniumUtil u;	
	private Pattern p1;
	private Select list;
	
	private String userUltimus;
	private String userCuanti;
	private String passCuanti;
	private String s1;
	private String folder = "WorkFlows"+File.separator+"Cuanti";
	private String mainTab;
	private String folioPromocion;
	
	private Set<String> handles;
	TestCase testCase;
	
	//Locators de la pagina login de "Ultimus" /////////////////////////////////////////////////////////
	By userIdXpath = By.xpath("//*[@id=\"igtxtUserNameBox\"]");
	By buttonLogOnXpath = By.xpath("//*[@id=\"LoginBtn__5\"]");		
	
	//Locators de la pagina de "Promoxxo" //////////////////////////////////////////////////////////////
	By radioButtonNuevoCuantiXpath = By.xpath("//*[@id=\"cuantiCV\"]");
	By buttonAbrirCuantiXpath = By.xpath("/html/body/div[2]/div[3]/div/button");
	
	//Locators de "Nuevo Cuanti" ///////////////////////////////////////////////////////////////////////
	By promocionesCategoriaXpath = By.xpath("/html/body/div[1]/table/tbody/tr/td/div/table/tbody/tr/td[1]/div/div/div/table/tbody/tr/td/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/ul/li[2]");
	By promocionCrearXpath = By.xpath("//a[contains(text(),'Cuanti - Crear')]");
	By promocionBuscarXpath = By.xpath("/html/body/div[1]/table/tbody/tr/td/div/table/tbody/tr/td[1]/div/div/div/table/tbody/tr/td/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/ul/li[3]/ul/li[3]/a");
	
	//Locators de "Crear promoción" ////////////////////////////////////////////////////////////////////		
	By buttonAltaPromocionCss = By.cssSelector("#ui-id-2");
	By buttonAltaPromocionXpath = By.xpath("//*[@id=\"ui-id-1\"]");	
	By buttonGuardarPromoXpath = By.xpath("//*[@id=\"btnGuardar\"]");
	By buttonEnviarPromoXpath = By.xpath("//*[@id=\"btnEnviar\"]");
	By headerAltaPromocion = By.xpath("//*[@id=\"idDivTituloPromo\"]");
	By folioPromocionXpath = By.xpath("//*[@id=\"idPromocionSpan\"]");
	By buttonBusacarArticuloXpath = By.xpath("//*[@id=\"btnBuscarArticulo\"]");
	
	//Botones de Aceptar ///////////////////////////////////////////////////////////////////////////////
	By buttonAceptarCloneXpath = By.xpath("/html/body/div[9]/div[3]/div/button");
	By buttonAceptarXpath = By.xpath("/html/body/div[4]/div[3]/div/button");
	By buttonAceptarCambios = By.xpath("/html/body/div[4]/div[3]/div/button[1]/span[2]");
	By buttonAceptarEnvioCloneXpath = By.xpath("/html/body/div[9]/div[3]/div/button/span[2]");
	By buttonAceptarEnvioXpath = By.xpath("/html/body/div[4]/div[3]/div/button/span[2]");	
	
	//Locators de evento y caracteristicas en "Crear promoción" ////////////////////////////////////////	
	By dropDownEventoXpath = By.xpath("//*[@id=\"tipoDeEventoSeleccionChange\"]");
	By dropDownTiposPromocionXpath = By.xpath("//*[@id=\"selTiposDePromociones\"]");
	
	//Locators de mecanica y metodo en "Crear promoción" ///////////////////////////////////////////////
	By dropDownTipoMecanicaXpath = By.xpath("//*[@id=\"tipoMecanica\"]");
	By dropDownMetodoMecanicaXpath = By.xpath("//*[@id=\"metMecId\"]");	
	By textBoxDescripcionArticuloXpath = By.xpath("//*[@id=\"simple2_articulo\"]");
	By textBoxProcentajeDescuentoXpath = By.xpath("//*[@id=\"simple2_descuento\"]");
	By dropDownTextoApoyoXpath = By.xpath("//*[@id=\"txtApoyoId\"]");
	
	//Locators de selección de paquete existente en "Crear promoción" //////////////////////////////////	
	By textBoxPaquetePromocionalXpath = By.xpath("//*[@id=\"textPaqueteProm\"]");
	By selectPaqueteXpath = By.cssSelector("#ui-id-44");
	
	//Locators de selección de segmentos y permisos en "Crear promoción" ///////////////////////////////
	By dropDownNecesidadXpath = By.xpath("//*[@id=\"necesidad\"]");
	By buttonPeriodoAnterionXpath = By.xpath("/html/body/div[2]/form/div/div[1]/div[1]/table[2]/tbody/tr[6]/td/table/tbody/tr/td/div/table/tbody/tr[1]/td[4]/table/tbody/tr[2]/td/div/table/tbody/tr[1]/td/label[1]/span");
	By checkBoxSegmentosXpath = By.xpath("//*[@id=\"chkSegmentoTodos\"]");
	By dropDownTipoTiendaXpath = By.xpath("//*[@id=\"tipoTienda\"]");
	
	//Locators de "Crear promocion" seccion "Coberturas" ///////////////////////////////////////////////
	By buttonCoberturasCss = By.cssSelector("#ui-id-3");
	By buttonCoberturasXpath = By.xpath("//*[@id=\"ui-id-2\"]");
	By checBoxCobertura = By.xpath("//*[@id=\"10CAN\"]");
	By checkBoxProvedorXpath = By.xpath("//*[@id=\"chkSelAllCedis\"]");
	By checkBoxPlazasXpath = By.xpath("//*[@id=\"chkSelAllCoberturas\"]");

	//Locators de "Crear promocion" seccion "Captura productos en la promoción" ////////////////////////
	By textBoxProvedorXpath = By.xpath("//*[@id=\"proveedor_1\"]");
	By textBoxBonificacionXpath = By.xpath("//*[@id=\"bonificacionComent_1\"]");
	By dropDownBonificacionXpath = By.xpath("//*[@id=\"bonificacionList_1\"]");
	By textBoxCargoATientasXpath = By.xpath("//*[@id=\"cargoTiendaPiezas_1\"]");
	By textBoxCantSugeridaXpath = By.xpath("//*[@id=\"ctdSugeridaVta_1\"]");
	By dropDownMotivoOfertaXpath = By.xpath("//*[@id=\"motivoOfertaList_1\"]");
	By comboBoxCoberturasXpath = By.xpath("#tabs-1 > div:nth-child(1) > table:nth-child(3) > tbody:nth-child(1) > tr:nth-child(5) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(3) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(2) > td:nth-child(1) > div:nth-child(1)");
	
	//Locators de "Ligar convenios" ////////////////////////////////////////////////////////////////////
	By buttonLigarConveniosXpath = By.xpath("//*[@id=\"btnLigaConvenios\"]");
	By textBoxNumConveniosXpath = By.xpath("//*[@id=\"txtNumConvenios\"]");
	By buttonObtenerConveniosXpath = By.xpath("//*[@id=\"btnGetFoliosPre\"]");
	By buttonContinuarXpath = By.xpath("//*[@id=\"btnContLigaConv\"]");
	
	//Locators de "Búsqueda de artículos" //////////////////////////////////////////////////////////////
	By textBoxBuscarDescripcionXpath = By.xpath("//*[@id=\"txtCoincidencias\"]");
	By buttonBuscarDescripcionXpath = By.xpath("//*[@id=\"btnBuscarFilter\"]");
	By buttonAgregarXpath = By.xpath("//*[@id=\"btnAgregar\"]");
	By selectArticulosDisponibles = By.xpath("//*[@id=\"popUp_lstArt\"]");
	By articuloXpath = By.xpath("/html/body/div[4]/div[2]/div[5]/table/tbody/tr[1]/td/table/tbody/tr[2]/td[1]/select/option[2]");
	By textBoxDescripcionXpath = By.xpath("/html/body/div[2]/table[2]/tbody/tr/td[1]/table/tbody/tr/td/table/tbody/tr/td/table/tbody/tr/td/div/div[3]/div[3]/div/table/tbody/tr[2]/td[5]");
	
	//Locators de "Búsqueda promociones" ///////////////////////////////////////////////////////////////
	By textBoxFolioXpath = By.xpath("//*[@id=\"folio\"]");
	By buttonBuscarFolioXpath = By.xpath("//*[@id=\"btnBuscar\"]");
	By rowFolioXpath = By.xpath("/html/body/div[2]/table[2]/tbody/tr/td[1]/table/tbody/tr/td/table/tbody/tr/td/table/tbody/tr/td/div/div[3]/div[3]/div/table/tbody/tr[2]/td[4]");
	By numFolioXpath = By.xpath("/html/body/div[2]/table[2]/tbody/tr/td[1]/table/tbody/tr/td/table/tbody/tr/td/table/tbody/tr/td/div/div[3]/div[3]/div/table/tbody/tr[2]/td[3]");
	By buttonClonarCss = By.cssSelector("#\\33 59010 > td:nth-child(13) > img:nth-child(1)");
	By buttonClonarXpath = By.cssSelector("#\\33 59007 > td:nth-child(13) > img:nth-child(1)");
	By codigoBarrasXpath = By.xpath("/html/body/div[2]/form/div/div[1]/div[3]/div/table/tbody/tr[4]/td[2]");
	By textBoxUsuarioCategoriaXpath = By.xpath("//*[@id=\"categoria\"]");
	By dropDownAnioXpath = By.xpath("//*[@id=\"anio\"]");
	By dropDownEvento = By.xpath("//*[@id=\"idEventos\"]");
	By dropDownPaqueteXpath = By.xpath("//*[@id=\"paqueteId\"]");
	By dropDownEstatusXpath = By.xpath("//*[@id=\"estatusId\"]");
	By dropDownTipoEventoXpath = By.xpath("//*[@id=\"tipoEvento\"]");
	By dropDownTipoDeMecanicaXpath = By.xpath("//*[@id=\"tipoMecanicaId\"]");
	By buttonEditPromoXpath = By.cssSelector("#\\33 59007 > td:nth-child(12) > img:nth-child(1)");
	By textBoxDescripcion = By.xpath("//*[@id=\"descMecanica\"]");
	
	/**
	 * @author José Zárate
	 * Constructor de la clase
	 * @param u --> Objeto selenium con el driver del navegador a utilizar
	 * @param userUltimus --> Usuario para el acceso a ultimus
	 * @param userCuanti ---> Usuario para el acceso a Cuanti 
	 * @param passCuanti ---> Contraseña para el acceso a Cuanti
	 */
	public PomCuantiPage(SeleniumUtil u, String userUltimus, String userCuanti, String passCuanti, TestCase testCase) {
		super();
		this.u = u;
		this.userCuanti = userCuanti;
		this.userUltimus = userUltimus;
		this.passCuanti = passCuanti;
		this.testCase = testCase;
	}
	
	/**
	 * @author José Zárate
	 * Realiza el proceso de acceso a Cuanti para la busqueda o creacion de una nueva promocion
	 * @param bExecution --> Metodo para la creacion de evidencias de cada step
	 * @param folio -------> Folio que será buscado para su clonacion o edicion 
	 * @param action ------> Tipo de accion que se realizará, busqueda o creacion 
	 * @throws Exception
	 */
	public void accessCuanti(BaseExecution bExecution, String folio, String action) throws Exception {
        System.out.println("TestCrear case "+bExecution.getTestCase().getFullTestName());
        
        bExecution.addStep("Ingresar al menu Promociones - Cuanti Buscar");
        
		loginUltimus();
		accessPromoxxoClone();
		logCuanti();
		selectNuevoCuanti();
		if(action.contains("buscar")) {
			accessBuscarPromocion(folio);
			testCase.addScreenShotCurrentStep(u, "Se muestra la pantalla de busqueda");
			assertTrue(validateAccessBusquedaPromo(), "Falló el acceso a \"Buscar promocion\"");
		}else {
			accessCrearNuevaPromocion();
			bExecution.getTestCase().addScreenShotCurrentStep(u, "La aplicación muestra la pantalla con las promociones pendientes de capturar");
			assertTrue(validateAccessCrearNuevaPromocion(), "Falló el acceso a \"Crear nueva promocion\"");
		}
		
    }
	
	public void guardarYEnviarPromoClonada(BaseExecution bExecution) throws FindFailed, InterruptedException {
		bExecution.addStep("Guardar y enviar promocion");
		guardarPromocionClonada();
		enviarPromocionClonada();
		bExecution.getTestCase().addScreenShotCurrentStep(u, "Se muestra el numero de folio creado");
		assertTrue(validateGuardarPromocion(), "Falló al guaradr la nueva promoción");
	}
	
	/**
	 * @author José Zárate
	 * Realiza el loguin a la pataforma de ultimus 
	 */
	public void loginUltimus() throws Exception {		
		u.screenShotFull("Login de Ultimus");
		u.clear(userIdXpath);
		u.highLight(userIdXpath);
		u.sendKeys(userIdXpath, userUltimus);
		u.highLight(buttonLogOnXpath);
		testCase.addScreenShotCurrentStep(u, "Click en el botón LogOn");
		u.click(buttonLogOnXpath);	
	}
	
	/**
	 * @author José Zárate
	 * dentro de ultimus selecciona el botón "Iniciar"
	 * @throws FindFailed
	 */
	public void selectIniciar() throws FindFailed {		
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "Iniciar.png");
		screen.wait(p1, 30.0);
		screen.click();
	}
	
	/**
	 * @author José Zárate
	 * Dentro de ultimus seleciona el portal promoxxoClone y obtiene el Id de la pagina actual 
	 * @throws FindFailed
	 */
	public void accessPromoxxoClone() throws FindFailed {
		mainTab = u.getDriver().getWindowHandle(); //Obtiene el id de la página actual donde se esta trabajando
		
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "promoxxoClone.png");		
		screen.wait(p1, 30.0);
		screen.doubleClick(p1);
		
		System.out.println("Main Tab: "+ mainTab);			
	}
	
	/**
	 * @author José Zárate
	 * Escribe las credenciales del dominio de Oxxo para el acceso al portal de Cuanti  
	 * @throws FindFailed
	 */
	public void logCuanti() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "user-textbox.png");		
		screen.wait(p1, 30.0);
		screen.type(p1, userCuanti);
		
		p1 = sikuli.PatternImage(s1 + "pass-testbox.png");		
		screen.wait(p1, 30.0);
		screen.type(p1, passCuanti);
		
		p1 = sikuli.PatternImage(s1 + "inicio-button");		
		screen.wait(p1, 30.0);
		screen.click();	
	}
	
	/**
	 * @author José Zárate
	 * Obtiene el Id de la nueva url y hace el switch del focus
	 * Selecciona el radio button del nuevo cuanti y accede 
	 * @throws FindFailed
	 */
	public void selectNuevoCuanti() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "logo-tienda.png");		
		screen.wait(p1, 30.0);
		
		handles = u.getDriver().getWindowHandles();
		
		for(String actual : handles) {
			System.out.println("-- Handle ID: "+ actual);
			if(!actual.equalsIgnoreCase(mainTab)) {
				System.out.println("-- Changing Tab --");
				u.getDriver().switchTo().window(actual);
				
				System.out.println("Se realizó el switch");
				if(u.waitWebElement(radioButtonNuevoCuantiXpath, 20, 2) != null) {
					System.out.println("Se encontro el radioButton");
					u.highLight(radioButtonNuevoCuantiXpath);
					u.click(radioButtonNuevoCuantiXpath);
				}
			}			
		}		
		u.highLight(buttonAbrirCuantiXpath);
		u.click(buttonAbrirCuantiXpath);		
	}
	
	/**
	 * @author José Zárate
	 * Del listbox de Promociones selecciona la opción de crear
	 * @throws Exception
	 */
	public void accessCrearNuevaPromocion() throws Exception {
		selectPromociones();
		u.highLight(promocionCrearXpath);
		u.click(promocionCrearXpath);		
	}
	
	/**
	 * @author José Zárate
	 *  Del listbox de Promociones selecciona la opción de buscar
	 * @param folioPromocion
	 * @throws Exception
	 */
	public void accessBuscarPromocion(String folioPromocion) throws Exception {
		this.folioPromocion = folioPromocion;
		selectPromociones();
		u.highLight(promocionBuscarXpath);
		u.click(promocionBuscarXpath);		
	}
	
	/**
	 * @author José Zárate
	 * Selecciona la promocion encontrada
	 */
	public void selectPromociones() {
		u.waitWebElement(promocionesCategoriaXpath, 30, 2);
		u.highLight(promocionesCategoriaXpath);
		u.click(promocionesCategoriaXpath);
	}
	
	/**
	 * @author José Antonio 
	 * Selecciona la opcion de clonar, para la promocion enconytrada
	 * @throws Exception
	 */
	public void selectClonarPromo() throws Exception {
		u.click(buttonClonarCss);		
	}
	
	/**
	 * @author José Zárate
	 * Selecciona la opcion de editar para la promocion encontrada
	 * @throws Exception
	 */
	public void selectEditPromo() throws Exception {
		u.click(buttonEditPromoXpath);
	}
	
	/**
	 * @author José Antonio 
	 * Dentro de la pagina "Crear promoción" Selecciona la pestaña "Cobertura" y selecciona el checkbox para todas las plazas
	 * @throws Exception
	 */
	public void selectPlazas() throws Exception {
		selectCoberturas();
		u.click(checkBoxPlazasXpath);			
	}
	
	/**
	 * @author José Zárate
	 * Selecciona la cobertura de las plazas
	 */
	public void selectCoberturas() {
		u.highLight(buttonCoberturasXpath);
		u.click(buttonCoberturasXpath);
	}
	
	/**
	 * @author José Zárate
	 * Selecciona las coberturas
	 */
	public void selectCoberturasCss() {		
		u.highLight(buttonCoberturasCss);
		u.click(buttonCoberturasCss);
	}
	
	/**
	 * @author José Zárate
	 * Selecciona el botón Alta, para el alta de la promocion creada
	 */
	public void selectAltaPromocionCss() {		
		u.highLight(buttonAltaPromocionCss);
		u.click(buttonAltaPromocionCss);
	}
	
	/**
	 * @author José Antonio 
	 * Selecciona el checkbox del tipo de provedor "CEDIS"
	 * @throws Exception
	 */
	public void selectProvedor() throws Exception {
		u.click(checkBoxProvedorXpath);
	}
	
	/**
	 * @author José Antonio 
	 * Selecciona el botón para el alta de la promocion
	 * @throws Exception
	 */
	public void selectAltaPromocion() throws Exception {
		u.highLight(buttonAltaPromocionXpath);
		u.click(buttonAltaPromocionXpath);
	}
	
	/**
	 * @author José Antonio 
	 * Selecciona el botón "Aceptar" del splash screen
	 * @throws Exception
	 */
	public void aceptarCambios() throws Exception {
		u.highLight(buttonAceptarCambios);
		u.click(buttonAceptarCambios);
	}
	
	/**
	 * @author José Antonio 
	 * Llena la información de evento y caracteristicas de la pagina "Crear nueva promoción"
	 * @throws Exception
	 */
	public void selectEventoYCaracteristicas() throws Exception {
		
		list = new Select(u.getDriver().findElement(dropDownEventoXpath));
		list.selectByIndex(1);
		
		list = new Select(u.getDriver().findElement(dropDownTiposPromocionXpath));
		list.selectByIndex(1);
		
		list = new Select(u.getDriver().findElement(dropDownTipoMecanicaXpath));
		list.selectByVisibleText("simple");
		
		list = new Select(u.getDriver().findElement(dropDownMetodoMecanicaXpath));
		list.selectByVisibleText("Promoción con descuento");
		
		u.highLight(textBoxDescripcionArticuloXpath);
		u.sendKeys(textBoxDescripcionArticuloXpath, "Cerveza");
		u.highLight(textBoxProcentajeDescuentoXpath);
		u.sendKeys(textBoxProcentajeDescuentoXpath, "10");	
	}
	
	//Cuando se cree el json con la info que debe llevar se eliminara este metodo y se podra parametrizar el proceso de la creacion de promociones 
	public void selectEventoYCaracteristicasAmpersand() throws Exception {
		
		list = new Select(u.getDriver().findElement(dropDownEventoXpath));
		list.selectByIndex(1);
		
		list = new Select(u.getDriver().findElement(dropDownTiposPromocionXpath));
		list.selectByIndex(1);
		
		list = new Select(u.getDriver().findElement(dropDownTipoMecanicaXpath));
		list.selectByVisibleText("simple");
		
		list = new Select(u.getDriver().findElement(dropDownMetodoMecanicaXpath));
		list.selectByVisibleText("Promoción con descuento");
		
		u.highLight(textBoxDescripcionArticuloXpath);
		u.sendKeys(textBoxDescripcionArticuloXpath, "Cerveza & cigarros");
		u.highLight(textBoxProcentajeDescuentoXpath);
		u.sendKeys(textBoxProcentajeDescuentoXpath, "10");	
	}
	
	/**
	 * @author José Antonio 
	 * Llena la información de la sección de paquete promocional de la pagina "Crear nueva promoción"
	 * @throws Exception
	 */
	public void selectPaqueteExistente() throws Exception {
		list = new Select(u.getDriver().findElement(dropDownTextoApoyoXpath));
		list.selectByIndex(1);
		
		u.highLight(textBoxPaquetePromocionalXpath);
		u.scroll();
		
		u.sendKeys(textBoxPaquetePromocionalXpath, "PA");
		u.click(selectPaqueteXpath);
	}
	
	/**
	 * @author José Antonio 
	 * Llena la información de la sección de segmentos y permisos de la pagina "Crear nueva promoción"
	 * @throws Exception
	 */
	public void selectSegmento() throws Exception {
		u.click(checkBoxSegmentosXpath);

		list = new Select(u.getDriver().findElement(dropDownTipoTiendaXpath));
		list.selectByVisibleText("Sin permisos");
		
		list = new Select(u.getDriver().findElement(dropDownNecesidadXpath));
		list.selectByIndex(1);
		
		u.click(buttonPeriodoAnterionXpath);
	}
	
	/**
	 * @author José Antonio 
	 * Agrega un artículo a la promoción
	 * @throws Exception
	 */
	public void selectAgregarArticulo() throws Exception {
		u.highLight(buttonBusacarArticuloXpath);		
		u.click(buttonBusacarArticuloXpath);
		
		u.waitWebElement(textBoxBuscarDescripcionXpath, 30, 2);
		u.highLight(textBoxBuscarDescripcionXpath);
		u.sendKeys(textBoxBuscarDescripcionXpath, "pepsi");
		
		u.highLight(buttonBuscarDescripcionXpath);
		u.click(buttonBuscarDescripcionXpath);
		
		u.waitWebElement(articuloXpath, 30, 2);
		list = new Select(u.getDriver().findElement(selectArticulosDisponibles));
		list.selectByIndex(1);
		
		u.click(buttonAgregarXpath);
	}	
	
	/**
	 * @author José Antonio 
	 * Llena la información del artículo agregado a la promoción
	 * @throws FindFailed
	 * @throws InterruptedException
	 */
	public void selectDatosArticulo() throws FindFailed, InterruptedException {
		u.waitWebElement(codigoBarrasXpath, 30, 2);
		Thread.sleep(4000);
		
		u.scroll();
		u.highLight(textBoxProvedorXpath);
		u.sendKeys(textBoxProvedorXpath, "P");
		u.highLight(textBoxBonificacionXpath);
		u.sendKeys(textBoxBonificacionXpath, "1");		
		list = new Select(u.getDriver().findElement(dropDownBonificacionXpath));
		list.selectByIndex(1);
		
		u.highLight(textBoxCargoATientasXpath);
		u.sendKeys(textBoxCargoATientasXpath, "1");		
		u.highLight(textBoxCantSugeridaXpath);
		u.sendKeys(textBoxCantSugeridaXpath, "1");
		list = new Select(u.getDriver().findElement(dropDownMotivoOfertaXpath));
		list.selectByIndex(1);
	}
	
	/**
	 * @author José Antonio 
	 * Llena la información ventana de "Liga de convenios"
	 * @throws Exception
	 */
	public void ligarConvenios() throws Exception {
		u.highLight(buttonLigarConveniosXpath);
		u.click(buttonLigarConveniosXpath);
		
		u.waitWebElement(textBoxNumConveniosXpath, 30, 2);
		u.highLight(textBoxNumConveniosXpath);
		u.sendKeys(textBoxNumConveniosXpath, "1");
		u.highLight(buttonObtenerConveniosXpath);
		u.click(buttonObtenerConveniosXpath);
		u.highLight(buttonContinuarXpath);
		u.click(buttonContinuarXpath);
	}
	
	/**
	 * @author José Antonio 
	 * Guarda la promoción creada y guarda su folio 
	 * @throws Exception
	 */
	public void guardarPromocion() throws Exception {
		u.highLight(buttonGuardarPromoXpath);
		u.click(buttonGuardarPromoXpath);
		u.waitWebElement(buttonAceptarXpath, 30, 2);
		u.highLight(buttonAceptarXpath);
		u.click(buttonAceptarXpath);
		u.waitWebElement(headerAltaPromocion, 30, 2);
		folioPromocion = u.getText(folioPromocionXpath).trim();
		System.out.println("El id de la promocion es: "+folioPromocion);
	}
	
	/**
	 * @author José Antonio 
	 * Después del clonar la promoción guarda la nueva promoción y guarda su folio 
	 * @throws FindFailed
	 * @throws InterruptedException
	 */
	public void guardarPromocionClonada() throws FindFailed, InterruptedException {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "crear-nueva-promocion.png");
		
		if(screen.wait(p1, 30.0) != null){
			u.scroll();
			Thread.sleep(5000);
			u.highLight(buttonGuardarPromoXpath);
			u.click(buttonGuardarPromoXpath);
			
			u.highLight(buttonAceptarCloneXpath);
			u.click(buttonAceptarCloneXpath);
			
			u.waitWebElement(headerAltaPromocion, 30, 2);
			folioPromocion = u.getText(folioPromocionXpath).trim();
			System.out.println("El id de la promocion es: "+folioPromocion);		
		}
	}
	
	/**
	 * @author José Antonio 
	 * Después de guardar la promoción la envia 
	 * @throws InterruptedException
	 * @throws FindFailed
	 */
	public void enviarPromocion() throws InterruptedException, FindFailed {
		Thread.sleep(3000);
		u.highLight(buttonEnviarPromoXpath);
		u.click(buttonEnviarPromoXpath);
		
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "button-aceptar-envio.png");
		
		if(screen.wait(p1, 30.0) != null){
			System.out.println("Se encontro el boton Aceptar");
			Thread.sleep(10000);			
			u.highLight(buttonAceptarEnvioXpath);
			u.click(buttonAceptarEnvioXpath);
		}
	}
			
	/**
	 * @author José Zárate
	 * Despues de llenar los campos, envia la promocion que ha sido clonada
	 * @throws InterruptedException
	 * @throws FindFailed
	 */
	public void enviarPromocionClonada() throws InterruptedException, FindFailed {
		Thread.sleep(3000);
		u.highLight(buttonEnviarPromoXpath);
		u.click(buttonEnviarPromoXpath);
		
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "button-aceptar-envio.png");
		
		if(screen.wait(p1, 30.0) != null){
			System.out.println("Se encontro el boton Aceptar");
			Thread.sleep(10000);			
			System.out.println("Termino el tiempo de espera");
			u.highLight(buttonAceptarEnvioCloneXpath);
			u.click(buttonAceptarEnvioCloneXpath);
		}
	}
	
	/**
	 * @author José Antonio 
	 * Click en el botón "Buscar" del listbox "Promociones"
	 * @throws Exception
	 */
	public void selectBuscarPromocion() throws Exception {
		u.highLight(promocionesCategoriaXpath);
		u.click(promocionesCategoriaXpath);
		u.highLight(promocionBuscarXpath);
		u.click(promocionBuscarXpath);		
	}
	
	/**
	 * @author José Antonio
	 * Da click en el botón "Aceptar" del splash screen
	 * @throws FindFailed
	 */
	public void selectAceptarCambios() throws FindFailed {
		if(u.waitWebElement(buttonAceptarCambios, 30, 2) != null) {
			u.click(buttonAceptarCambios);
		}
	}
	
	/**
	 * @author José Antonio
	 * Captura el folio de la promocion y realiza la busqueda
	 * @throws InterruptedException
	 */
	public void buscarFolio() throws InterruptedException {
		u.waitWebElement(textBoxFolioXpath, 30, 2);
		u.highLight(textBoxFolioXpath);
		u.sendKeys(textBoxFolioXpath, folioPromocion);
		
		selectBuscarFolio();
	}
	
	/**
	 * @author José Zárate
	 * Busqueda de folio previamente clonado 
	 * @throws InterruptedException
	 */
	public void buscarFolioClonado() throws InterruptedException {
		u.waitWebElement(textBoxFolioXpath, 30, 2);
		u.highLight(textBoxFolioXpath);
		u.sendKeys(textBoxFolioXpath, folioPromocion);
	}
	
	/**
	 * @author José Zárate
	 * Busqueda de promocion por medio de los filtro
	 */
	public void buscarFolioPorFiltros() {
		
		u.highLight(textBoxUsuarioCategoriaXpath);
		u.sendKeys(textBoxUsuarioCategoriaXpath, "comercio.fne/"+userUltimus);
		
		list = new Select(u.getDriver().findElement(dropDownAnioXpath));
		list.selectByVisibleText("2022");
		
		list = new Select(u.getDriver().findElement(dropDownTipoEventoXpath));
		list.selectByVisibleText("EVENTO REGULAR");
		
		list = new Select(u.getDriver().findElement(dropDownEvento));
		list.selectByVisibleText("QAPROMOLOGISTICA");

		list = new Select(u.getDriver().findElement(dropDownPaqueteXpath));
		list.selectByVisibleText("paq QA");
		
		list = new Select(u.getDriver().findElement(dropDownEstatusXpath));
		list.selectByVisibleText("Enviada");
		
		list = new Select(u.getDriver().findElement(dropDownTipoDeMecanicaXpath));
		list.selectByVisibleText("simple");
		
		selectBuscarFolio();
	}
	
	/**
	 * @author José Zárate 
	 * Busca la promocion promopack anteriormente clonada
	 */
	public void buscaFolioPromopack() {
		u.highLight(textBoxUsuarioCategoriaXpath);
		u.sendKeys(textBoxUsuarioCategoriaXpath, "comercio.fne/"+userUltimus);
		
		list = new Select(u.getDriver().findElement(dropDownPaqueteXpath));
		list.selectByIndex(8);
		
		list = new Select(u.getDriver().findElement(dropDownEstatusXpath));
		list.selectByVisibleText("Enviada");
		
		list = new Select(u.getDriver().findElement(dropDownTipoDeMecanicaXpath));
		list.selectByVisibleText("promopack");		
		
		selectBuscarFolio();
	}
	
	/**
	 * @author José Zárate
	 * Selecciona el boton de busqueda en el menu de promociones
	 */
	public void selectBuscarFolio() {
		u.highLight(buttonBuscarFolioXpath);
		u.click(buttonBuscarFolioXpath);
	}
	
	/**
	 * @author José Antonio
	 * Verifica que la pagina haya terminado de cargar
	 * @return valor boolean
	 * @throws FindFailed
	 * @throws InterruptedException 
	 */
	public Boolean statusProcesando() throws FindFailed, InterruptedException {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "splashScreen-cargando.png");
		
		try {
			screen.find(p1);
			System.out.println("Sigue cargando la pagina");
			statusProcesando();
		}
		catch(Exception ex) {
			System.out.println("Exception: Termino de cargar la pagina");		
			return true; //Se agrego com pruba
		}
		return true;		
	}
	
	/**
	 * @author José Antonio
	 * Verifica el acceso a la pagina "Crear nueva promoción"
	 * @return valor boolean
	 * @throws FindFailed
	 */
	public Boolean validateAccessCrearNuevaPromocion() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "header-crear-promocion.png");
		
		if(screen.wait(p1, 60.0) != null){
			System.out.println("Se encontro la imagen");
			return true;			
		}
		else{
			System.out.println("No encontro la imagen");
			return false;
		}
	}
	
	/**
	 * @author José Antonio
	 * Verifica que se hayan capturado todos los campos necesarios de la nueva promoción
	 * @return valor boolean
	 * @throws FindFailed
	 */
	public Boolean validateCapturaNuevaPromocion() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "captura-articulos.png");
		
		if(screen.wait(p1, 60.0) != null){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * @author José Antonio
	 * Verifica que se hayan clonado la promoción
	 * @return valor boolean
	 * @throws FindFailed
	 */
	public Boolean validateClonarPromocion() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "crear-nueva-promocion.png");
		
		if(screen.wait(p1, 60.0) != null){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * @author José Antonio
	 * Verifica que se hayan clonado la promoción
	 * @return valor boolean
	 * @throws FindFailed
	 */
	public Boolean validateClonaPromocion() throws FindFailed {
		s1 = sikuli.PatternPath(folder);		
		p1 = sikuli.PatternImage(s1 + "captura-segmentos.png");
		
		if(screen.wait(p1, 60.0) != null){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * @author José Antonio
	 * Verifica que se hayan enviado la promoción creada
	 * @return valor boolean
	 * @throws FindFailed
	 */
	public Boolean validateGuardarPromocion() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "promo-enviada.png");
		
		if(screen.wait(p1, 60.0) != null){
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * @author José Antonio
	 * Verifica el acceso a la pagina "Buscar promociones"
	 * @return valor boolean
	 * @throws FindFailed
	 * @throws InterruptedException 
	 */
	public Boolean validateAccessBusquedaPromo() throws FindFailed, InterruptedException {
		if(statusProcesando()) {
			s1 = sikuli.PatternPath(folder);
			p1 = sikuli.PatternImage(s1 + "busqueda-promo.png");
			
			if(screen.wait(p1, 60.0) != null){
				return true;
			}
			else{
				return false;
			}
		}
		else {
			return false;			
		}
	}
	
	/**
	 * @author José Antonio
	 * Valida que se haya encontrado el folio ingresado
	 * @return valor boolean
	 * @throws Exception
	 */
	public Boolean validateBusquedaFolio() throws Exception {
		if(u.waitWebElement(rowFolioXpath, 60, 2) != null) {
			System.out.println("Se encontro el folio");
			return true;
		}
		else {
			System.out.println("No se encontro el folio");
			return false;
		}
	}
	
	/**
	 * @author José Zárate
	 * Verifica que exista la promocion anteriormente clonada
	 * @return --> True en caso de encontrar el folio de la promocion, false en caso contrario
	 * @throws Exception
	 */
	public Boolean validateBusquedaFolioPromocion() throws Exception {
		if(u.getText(numFolioXpath).trim().contentEquals(folioPromocion)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * @author José Zárate
	 * Validar la información de coberturas y excepción de coberturas se muestre correcta
	 * @return --> True en caso de que se visualisen, False en caso contrario
	 * @throws Exception
	 */
	public Boolean validateCoberturasYExcepsiones() throws Exception {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "header-crear-promocion.png");
		
		if(screen.wait(p1, 60.0) != null){
			
			u.scroll(500);			
			s1 = sikuli.PatternPath(folder);
			p1 = sikuli.PatternImage(s1 + "coberturas-excepciones.png");
			if(screen.wait(p1, 60.0) != null) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}		
	}
	
	/**
	 * @author José Zárate
	 * Valida que al menos una de las coberturas este selecionada
	 * @return --> True en caso de que haya al menos una cobertura seleccionada, False en caso contrario
	 */
	public Boolean validateCoberturas() {
		return u.getDriver().findElement(checBoxCobertura).isSelected();
	}
	
	/**
	 * @author José Zárate
	 * Valida que la descripcion sea separada por comas
	 * @return --> True en caso de encontrar comas, False en caso contrario
	 * @throws FindFailed
	 */
	public Boolean validateComas() throws FindFailed {
		validateAccessCrearNuevaPromocion();
		if(u.getText(textBoxDescripcion).trim().contains(",")) {
				return true;
		}else {
			return false;
		}
	}
	
	/**
	 * @author José Zárate
	 * Valida que la descripcion de busqueda sea separada por comas
	 * @return
	 */
	public Boolean validateDescripcionBusqueda() {
		if(u.getText(textBoxDescripcionXpath).trim().contains(",")) {
			return true;
		}else {
			return false;
		}
	}
}
