package WorkFlows.util.Cuanti;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.sql.SQLException;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import exceptions.FrameworkException;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class PomCuantiMecanica {
	
	private UtilsMecanicas utils = new UtilsMecanicas();
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
	private String folio;
	
	Boolean guardada;
	private Set<String> handles;
	TestCase testCase;
	
		By msnGuardar = By.xpath("//*[@id=\"divMensaje\"]");
	//Locators de la pagina login de "Ultimus" /////////////////////////////////////////////////////////
		By userIdXpath = By.xpath("//*[@id=\"igtxtUserNameBox\"]");
		By buttonLogOnXpath = By.xpath("//*[@id=\"LoginBtn__5\"]");		
		
	//Locators de la pagina de "Promoxxo" //////////////////////////////////////////////////////////////
		By radioButtonNuevoCuantiXpath = By.xpath("//*[@id=\"cuantiCV\"]");
		By buttonAbrirCuantiXpath = By.xpath("/html/body/div[2]/div[3]/div/button");
		
	//Locators de "Nuevo Cuanti" ///////////////////////////////////////////////////////////////////////
		By promocionesXpath = By.xpath("/html/body/div[1]/table/tbody/tr/td/div/table/tbody/tr/td[1]/div/div/div/table/tbody/tr/td/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/ul/li[2]/a");
		By promocionesCategoriaXpath = By.xpath("/html/body/div[1]/table/tbody/tr/td/div/table/tbody/tr/td[1]/div/div/div/table/tbody/tr/td/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/ul/li[2]");
		By adminXpath = By.xpath("//a[contains(text(),'Administración')]");
		By motivosOfertaXpath = By.xpath("//a[contains(text(),'Motivos de oferta')]");
		By paquetesPromocionalesXpath = By.xpath("//a[contains(text(),'Paquetes promocionales')]");
		//By promocionCrearXpath = By.xpath("/html/body/div[1]/table/tbody/tr/td/div/table/tbody/tr/td[1]/div/div/div/table/tbody/tr/td/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/ul/li[3]/ul/li[4]/a");
		By promocionCrearXpath = By.xpath("//a[contains(text(),'Cuanti - Crear')]");
		By promocionBuscarXpath = By.xpath("/html/body/div[1]/table/tbody/tr/td/div/table/tbody/tr/td[1]/div/div/div/table/tbody/tr/td/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/ul/li[3]/ul/li[3]/a");
		By promocionBuscarCategoriaXpath = By.xpath("/html/body/div[1]/table/tbody/tr/td/div/table/tbody/tr/td[1]/div/div/div/table/tbody/tr/td/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/ul/li[2]/ul/li[3]/a");
		
	//Locators de "Motivos de oferta" //////////////////////////////////////////////////////////////////
		By txtNombreMotivoXpath = By.xpath("//input[@id='nombreMotivoOferta']");
		By btnGuardarMotivo = By.xpath("//button[@id='btnCrear']");
		By btnAceptarXpath = By.xpath("//div[@class='ui-dialog-buttonset']//button[@type='button']");
		By tbMotivosXpath = By.xpath("//table[@id='gridRegistros']");
		
	//Locators Administración de paquetes y compnentes /////////////////////////////////////////////////
		By btnComponentesXpath = By.xpath("//a[@id='ui-id-2']");
		By iconBasuaraXpath = By.xpath("//td[@id='del_gridComponentesUnicos']//div[@class='ui-pg-div']");
		By txtComponenteXpath = By.xpath("//input[@id='inputBuscarComponenteUnicos']");
		By btnConfirmBorrarXpath = By.xpath("//a[@id='dData']");
		By btnAceptarBorradoXpath = By.xpath("/html/body/div[9]/div[3]/div/button");
	
	//Locators de "Búsqueda promociones" ///////////////////////////////////////////////////////////////
		By selectTipoDeMecanicaXpath = By.xpath("//*[@id=\"tipoMecanicaId\"]");
		By selectEstatusXpath = By.xpath("//select[@id='estatusId']");
		By checkBoxFolioXpath = By.xpath("//td[contains(text(),'CUANTI')]");
		By selectCambioEstatusXpath = By.xpath("//*[@id=\"cambiarEstatusSelId\"]");
		By btnGuradarCambiosXpath = By.xpath("//button[@id='btnGuardarCambios']");
		By textBoxFolioXpath = By.xpath("//*[@id=\"folio\"]");
		By buttonBuscarFolioXpath = By.xpath("//*[@id=\"btnBuscar\"]");
		By numFolioXpath = By.xpath("/html/body/div[2]/table[2]/tbody/tr/td[1]/table/tbody/tr/td/table/tbody/tr/td/table/tbody/tr/td/div/div[3]/div[3]/div/table/tbody/tr[2]/td[3]");
		By buttonEditPromoXpath = By.xpath("//img[@title='Modificar Promoción']");
		By buttonClonarPromoXpath = By.xpath("//img[@title='Clonar Promoción']");
		By txtUsuarioCategoriaXpath = By.xpath("//input[@id='categoria']");
		By cbFoliosXpath = By.xpath("//input[@id='cb_gridBuscar']");
		
	//Locators de "Crear promoción" ////////////////////////////////////////////////////////////////////		
		By buttonGuardarPromoXpath = By.xpath("//*[@id=\"btnGuardar\"]");
		By buttonEnviarPromoXpath = By.xpath("//*[@id=\"btnEnviar\"]");
		By barCoberturasXpath = By.xpath("/html/body/div[2]/form/div/ul");
		By headerAltaPromocion = By.xpath("//*[@id=\"idDivTituloPromo\"]");
		By folioPromocionXpath = By.xpath("//*[@id=\"idPromocionSpan\"]");
		By selectEventoXpath = By.xpath("//select[@id='tipoDeEventoSeleccionChange']");
		By selectTipoPromocionXpath = By.xpath("//*[@id=\"selTiposDePromociones\"]");
		By buttonAltaPromocionXpath = By.xpath("//li[@class='ui-state-default ui-corner-top ui-tabs-active ui-state-active']");
		
	//Locators para cambio de mecanica simple - descuento //////////////////////////////////////////////
		By textBoxDescripcionSimpleXpath = By.xpath("//*[@id=\"simple2_articulo\"]");
		By textBoxDescuentoXpath = By.xpath("//*[@id=\"simple2_descuento\"]");
		By selectTipoMecanicaXpath = By.xpath("//*[@id=\"tipoMecanica\"]");
		By selectMetodoMecanicaXpath = By.xpath("//*[@id=\"metMecId\"]");
		By selectTextoApoyoXpath = By.xpath("//*[@id=\"txtApoyoId\"]");
		
	//Locatos para cambio de mecanica simple - precio //////////////////////////////////////////////////
		By textBoxDescripcionPrecioXpath = By.xpath("//*[@id=\"simple1_articulo\"]");
		By textBoxPrecioMecanicaXpath = By.xpath("//*[@id=\"simple1_precio\"]");
		
	//Locators para cambio de mecanica compra multiple /////////////////////////////////////////////////
		By textBoxCantidad1Xpath = By.xpath("//*[@id=\"multiple1_cant1\"]");
		By textBoxCantidad2Xpath = By.xpath("//*[@id=\"multiple1_cant2\"]");
		By textBoxDescripcionMuiltipleXpath = By.xpath("//*[@id=\"multiple1_articulo\"]");
		By textBoxUnidadXpath = By.xpath("//*[@id=\"txtCant1\"]");
		By textBoxPrecioXpath = By.xpath("//*[@id=\"txtCant2\"]");
		By buttonUnidadXpath = By.xpath("/html/body/div[2]/form/div/div[1]/div[1]/div[1]/table/tbody/tr/td/div/table/tbody/tr/td/div/table/tbody/tr[2]/td/div/table/tbody/tr[2]/td/div/label[1]");
		
	//Locators para cambio de mecanoca compara multiple por precio /////////////////////////////////////
		By textBoxCantidadXpath = By.xpath("//*[@id=\"multiple2_cant1\"]");//*[@id="multiple1_cant1"]
		By textBoxMultiplePrecioXpath = By.xpath("//*[@id=\"multiple2_precio\"]");
		By textBoxMultipleDescXpath = By.xpath("//*[@id=\"multiple2_articulo\"]");
		By buttonPrecioXpath = By.xpath("/html/body/div[2]/form/div/div[1]/div[1]/div[1]/table/tbody/tr/td/div/table/tbody/tr/td/div/table/tbody/tr[2]/td/div/table/tbody/tr[2]/td/div/label[2]");
		
	//Locators para cambio de mecanica promopack - suma de articulos por precio ////////////////////////
		By buttonAceptarPromopackXpath = By.cssSelector(".ui-dialog-buttonset > button:nth-child(1)");
		By textBoxDescArticulo1Xpath = By.xpath("//*[@id=\"prom_art1\"]");
		By textBoxDescArticulo2Xpath = By.xpath("//*[@id=\"prom_art2\"]");
		By textBoxPrecioPromoPackXpath = By.xpath("//*[@id=\"prom1_precio\"]");
		By textBoxSum1MecanicaXpath = By.xpath("//*[@id=\"txtComponente1\"]");
		By textBoxSum2MecanicaXpath = By.xpath("//*[@id=\"txtComponente2\"]");	
		By categoria1Xpath = By.xpath("//*[@id=\"selCategoriaComp_1-button\"]");
		By categoriaComponente1Xpath = By.xpath("//*[@id=\"ui-id-72\"]"); 
		By categoria2Xpath = By.xpath("//*[@id=\"selCategoriaComp_2-button\"]");
		By categoriaComponente2Xpath = By.xpath("//*[@id=\"ui-id-82\"]");
		By buttonActivarXpath = By.xpath("//*[@id=\"btnActivarPP\"]");
		
	//Locators para cambio de macanica promopack - Articulo mas precio /////////////////////////////////
		By textBoxDescArticuloXpath = By.xpath("//*[@id=\"prom2_articulo1\"]");
		By textBoxPromPrecioXpath = By.xpath("//*[@id=\"prom2_precio\"]");
		By textBoxDescArticulosXpath = By.xpath("//*[@id=\"prom2_articulo2\"]");
		
	//Locators para cambio de mecanica promopack - Articulo gratis /////////////////////////////////////
		By textBoxDescArtXpath = By.xpath("//*[@id=\"prom3_articulo\"]");
		By textBoxDescArticulosGratisXpath = By.xpath("//*[@id=\"prom3_gratis\"]");
		
	//Locators para cambio de mecanica promopack - Articulo descuento //////////////////////////////////
		By textBoxDescArtDescuentoXpath = By.xpath("//*[@id=\"prom4_articulo\"]");
		By textBoxDescXpath = By.xpath("//*[@id=\"prom4_descuento\"]");
		By textBoxArtDescuentoXpath = By.xpath("//*[@id=\"prom4_gratis\"]");
		
	//Locators para cambio de mecánica promoipack servicios - Servicio mas precio //////////////////////
		By textBoxDescArtSerXpath = By.xpath("//input[@id='serv_articulo1']");
		By textBoxPrecioSerXpath = By.xpath("//input[@id='serv_precio']");
		By textBoxDescArtSerXpath2 = By.xpath("//input[@id='serv_articulo2']");
		By categotiaServicioXpath = By.xpath("//*[@id=\"ui-id-141\"]");//*[@id="ui-id-141"]
		By buttonBuscarArticuloXpath = By.xpath("//button[@id='btnBuscarArticulo']");
		By textBoxCoincidenciasXpath = By.xpath("//input[@id='txtCoincidencias']");		
		By buttonBuscarCoincidenciasXpath = By.xpath("//td[@colspan='2']//button[@id='btnBuscarFilter']");
		By selectArticuloXpath = By.xpath("//option[@value='7500478016297']");
		By buttonAgregarArtXpath = By.xpath("//button[@id='btnAgregar']");
		By textBoxProvedorXpath = By.xpath("//input[@id='proveedor_1']");
		By textBoxBonificacionXpath = By.xpath("//input[@id='bonificacionComent_1']");
		By selectBonificacionXpath = By.xpath("//select[@id='bonificacionList_1']");
		By textBoxPiezasXpath = By.xpath("//input[@id='cargoTiendaPiezas_1']");
		By textBoxCtdSugeridaXpath = By.xpath("//input[@id='ctdSugeridaVta_1']");
		By selectMotivoXpath = By.xpath("//select[@id='motivoOfertaList_1']");
		By buttonServiciosXpath = By.xpath("//a[@id='ui-id-157']");
		By selectServiciosXpath = By.xpath("//option[@value='101900331']");
		By textBoxProvedor2Xpath = By.xpath("//input[@id='proveedor_2']");
		
	//Locators para cambio de mecanica comunicacion e imagen - sin precio //////////////////////////////
		By textBoxDescArtComunicacionXpath = By.xpath("//input[@id='img1_articulo']");
		
	//Locators para cambio de mecanica comunicacion e imagen - precio regular //////////////////////////
		By textBoxDescArtComXpath = By.xpath("//*[@id=\"img2_articulo\"]");
		By textBoxPrecioComunicacionXpath = By.xpath("//*[@id=\"img2_precio\"]");
		
	//Locators de Medios de pago en "Crear promocion" ////////////////////////////////////////////////// 
		By radioButtonTCTDXpath = By.xpath("//*[@id=\"medio1\"]");		
		By textBoxMsnPromoXpath = By.xpath("//*[@id=\"descripcion\"]");
		By selectBanderaXpath = By.xpath("//*[@id=\"bandera_ban\"]");
		By selectBancoXpath = By.xpath("//*[@id=\"banco\"]");
		By selectTipoXpath = By.xpath("//*[@id=\"tipo\"]");		
		
	//Locators de selección de paquete existente en "Crear promoción" //////////////////////////////////	
		By textBoxPaquetePromocionalXpath = By.xpath("//*[@id=\"textPaqueteProm\"]");
		By selectPaqueteXpath = By.xpath("//*[@id=\"ui-id-45\"]");
		
	//Locators de selección de segmentos y permisos en "Crear promoción" ///////////////////////////////
		By checkBoxSegmentosXpath = By.xpath("//*[@id=\"chkSegmentoTodos\"]");
		By checkBoxSiIngresoXpath = By.xpath("//*[@id=\"ingresoObligatorio1\"]");
		By checkBoxNoIngresoXpath = By.xpath("//*[@id=\"ingresoObligatorio2\"]");
		
	//Botones de Aceptar ///////////////////////////////////////////////////////////////////////////////
		By buttonAceptarXpath = By.xpath("//span[contains(text(),'Aceptar')]");
		By buttonAceptarEnvioXpath = By.xpath("/html/body/div[4]/div[3]/div/button/span[2]");
		
	//Articulos ////////////////////////////////////////////////////////////////////////////////////////
		By textBoxCargoXpath = By.xpath("//*[@id=\"cargoTiendaPiezas_1\"]");
		
	
	/**
	 * @author José Zárate
	 * Constructor de la clase
	 * @param u 		  --> objeto selenium con el driver del navegador a utilizar
	 * @param userUltimus --> usuario para el login en el potal de ultimus
	 * @param userCuanti  --> usuario con dominio de oxxo para el login en el portal de Cuanti
	 * @param passCuanti  --> password del usuaro con dominio de oxxo
	 * @param folio 	  --> folio de la promocion a buscar
	 * @throws SQLException 
	 * @throws FrameworkException 
	 */
	public PomCuantiMecanica(SeleniumUtil u, String userUltimus, String userCuanti, String passCuanti, String folio, TestCase testCase) {
		super();
		this.u = u;
		this.userCuanti = userCuanti;
		this.userUltimus = userUltimus;
		this.passCuanti = passCuanti;
		this.folio = folio;
		this.testCase = testCase;
	}
	
	/**
	 * @author José Zárate
	 * Elimina cookies del navegador
	 */
	public void deleteCookies() {
		u.getDriver().manage().deleteAllCookies();
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
	  * @author José Záarate
	  * Realiza todo el proceso de acceso a cuanti, desde el login en Ultimus hasta la seleccion de la acción 
	  * a realizar en el menu de promociones
	  * @param bExecution --> Clase heredada para la captura de evidencias y generación de los reportes  
	  * @param action 	  --> indica la accion a realizar, ya sea búscar o crear
	  * @throws Exception
	  */
	public void accessCuanti(BaseExecution bExecution, String action) throws Exception {
        System.out.println("TestCrear case "+bExecution.getTestCase().getFullTestName());
        
        ///////////////////////////////////////////////////////////////////////////////////////////////
        bExecution.addStep("Iniciar sesión el Ultimus 8.2 con usuario Categoría");
		loginUltimus();
		testCase.addScreenShotCurrentStep(u, "Ultimus muestra los procesos a los que tiene acceso el usuario");
		assertTrue(validateAccessUltimusCategoria(), "Falló el acceso a Ultimus");
		
		//selectIniciar();
		
		///////////////////////////////////////////////////////////////////////////////////////////////
		bExecution.addStep("Ingresar al proceso PromoxxoClone");
		accessPromoxxoClone();
		logCuanti();
		testCase.addScreenShotCurrentStep(u, "Se abre la aplicación mostrando la pantalla principal");
		assertTrue(validateAccessPromoxxoClone(), "Falló el acceso a PromoxxoClone");
		
		selectNuevoCuanti();
		if(action.contains("buscar")) {
			///////////////////////////////////////////////////////////////////////////////////////////////
			bExecution.addStep("Ingresar al menu Promociones - Cuanti Buscar");
			accessBuscarPromocionCategoria();
			//accessBuscarPromocion();
			testCase.addScreenShotCurrentStep(u, "Se muestra la pantalla de busqueda");
			assertTrue(validateAccessBusquedaPromo(), "Falló el acceso a \"Buscar promocion\"");
		}else {
			accessCrearNuevaPromocion();
			testCase.addScreenShotCurrentStep(u, "La aplicación muestra la pantalla con las promociones pendientes de capturar");
			assertTrue(validateAccessCrearNuevaPromocion(), "Falló el acceso a \"Crear nueva promocion\"");
		}		
    }
	
	/**
	 * @author José Zárate
	 * Dentro de ultimus selecciona el botón "Iniciar"
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
	 * Dentro del munú de aplicaciones de ultimus, seleciona el portal promoxxoClone y obtiene el Id de la pagina actual 
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
	 * Obtiene el Id de la nueva ventana y hace el switch del focus
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
					u.highLight(radioButtonNuevoCuantiXpath);
					u.click(radioButtonNuevoCuantiXpath);
				}
			}			
		}		
		u.highLight(buttonAbrirCuantiXpath);
		u.click(buttonAbrirCuantiXpath);		
	}
	
	/**
	 * @author José Antonio
	 * Con un usuario Administrador da clic en el menu de promociones del portal de Cuanti
	 * @throws Exception 
	 */
	public void selectPromociones() throws Exception {
		u.waitWebElement(promocionesXpath, 30, 2);
		u.highLight(promocionesXpath);
		u.click(promocionesXpath);
	}
	
	/**
	 * @author José Antonio
	 * Con un usuario Categoria da clic en el menu de promociones del portal de Cuanti
	 * @throws Exception 
	 */
	public void selectPromocionesCategoria() throws Exception {
		u.waitWebElement(promocionesCategoriaXpath, 30, 2);
		u.highLight(promocionesCategoriaXpath);
		u.click(promocionesCategoriaXpath);
	}
	
	/**
	 * @author José Zárate
	 * Con un usuario Administrador del menú de Promociones selecciona la opción Cuanti-buscar
	 * @throws Exception
	 */
	public void accessBuscarPromocion() throws Exception {
		selectPromociones();
		u.highLight(promocionBuscarXpath);
		u.click(promocionBuscarXpath);		
	}	
	
	/**
	 * @author José Zárate
	 * Con un usuario Categoria del menú de Promociones selecciona la opción Cuanti-buscar
	 * @throws Exception
	 */
	public void accessBuscarPromocionCategoria() throws Exception {
		selectPromocionesCategoria();
		u.highLight(promocionBuscarCategoriaXpath);
		u.click(promocionBuscarCategoriaXpath);		
	}
	
	/**
	 * @author José Zárate
	 * Con un usuario Administrador del menú de Administracion selecciona la opción Paquetes promocionales
	 * @throws Exception
	 */
	public void accessPaquetesPromocionales() throws Exception {
		selectAministracion();
		u.highLight(paquetesPromocionalesXpath);
		u.click(paquetesPromocionalesXpath);		
	}	
	
	/**
	 * @author José Zárate
	 * Da clic en el menu de Administración del portal de Cuanti
	 * @throws Exception
	 */
	public void selectAministracion() throws Exception {
		u.waitWebElement(adminXpath, 30, 2);
		u.highLight(adminXpath);
		u.click(adminXpath);
	}
	
	/**
	 * @author José Zárate
	 * Da click en la opción "Motivos de oferta" del menú de Admoinistración
	 * @throws Exception
	 */
	public void accessMotivosDeOferta() throws Exception  {
		selectAministracion();
		u.highLight(motivosOfertaXpath);
		u.click(motivosOfertaXpath);
	}
	
	public void capturaNombreMotivo(String motivo) throws Exception {
		u.highLight(txtNombreMotivoXpath);
		u.sendKeys(txtNombreMotivoXpath, motivo);
	}
	
	public void guardarMotivo() throws Exception {
		u.highLight(btnGuardarMotivo);
		u.click(btnGuardarMotivo);
	}
	
	public void clickAceptarGuardado() throws Exception {
		u.highLight(btnAceptarXpath);
		u.click(btnAceptarXpath);
	}
	
	/**
	 * @author José Zárate
	 * Selecciona la pestaña componentes de la pantalla "Administracion de paqutes y componentes"
	 * @throws Exception
	 */
	public void selectComponentes() throws Exception{
		u.highLight(btnComponentesXpath);
		u.click(btnComponentesXpath);
	}
	
	/**
	 * @author José Zárate
	 * Captura la descripción del componente a eliminar
	 * @param desc --> Descripción del componente a borrar 
	 * @throws FindFailed
	 */
	public void buscaComponente(String desc) throws FindFailed {
		u.highLight(txtComponenteXpath);
		u.sendKeys(txtComponenteXpath, desc);
		
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "primer-componente.png");
		screen.wait(p1, 30.0);
		screen.click();
		
		u.highLight(iconBasuaraXpath);
		u.click(iconBasuaraXpath);
	}
	
	/**
	 * @author José Zárate
	 * Da click en el botón "Borrar" del mensaje de confirmación
	 * @throws Exception
	 */
	public void clickConfirmBorrado() throws Exception {
		u.highLight(btnConfirmBorrarXpath);
		u.click(btnConfirmBorrarXpath);
	}
	
	/**
	 * @author José Zárate
	 * Da click en el botón "Aceptar" del mensaje de confirmación del borrado
	 * @throws Exception
	 */
	public void clickAceptarBorrado() {
		u.highLight(btnAceptarBorradoXpath);
		u.click(btnAceptarBorradoXpath);
	}
	
	/**
	 * @author José Zárate
	 * Limpia el campo "Usuario Categoria" para poder realizar la busqueda por folio
	 * @throws Exception
	 */
	public void limpiarUsuario() throws Exception {
		u.clear(txtUsuarioCategoriaXpath);
	}
	
	/**
	 * @author José Antonio
	 * Captura el folio de la promocion solicitada y realiza la búsqueda
	 * @throws Exception 
	 */
	public void buscarFolio() throws Exception {		
		u.waitWebElement(textBoxFolioXpath, 30, 2);
		u.highLight(textBoxFolioXpath);
		u.sendKeys(textBoxFolioXpath, folio);
		limpiarUsuario();
		
		u.highLight(buttonBuscarFolioXpath);
		u.click(buttonBuscarFolioXpath);
	}
	
	/**
	 * @author José Zárate
	 * Captura los folios separados por comas para la busqueda multiple
	 * @throws Exception
	 */
	public void buscarFolios() throws Exception {
		u.waitWebElement(textBoxFolioXpath, 30, 2);
		u.highLight(textBoxFolioXpath);
		u.sendKeys(textBoxFolioXpath, folio);
		
		u.highLight(buttonBuscarFolioXpath);
		u.click(buttonBuscarFolioXpath);
	}
	
	
	/**
	 * @author José Antonio
	 * Usando los filtros, busca un folio del tipo de promocion solicitado
	 * @throws Exception 
	 */
	public void buscarFolioPorFilrtros(String tipoFolio) throws Exception {		
		u.hardWait(5);
		//wait.until(ExpectedConditions.elementToBeClickable(selectTipoDeMecanicaXpath));
		switch(tipoFolio) {
			case "simple-Descuento":
				list = new Select(u.getDriver().findElement(selectTipoDeMecanicaXpath));
				list.selectByIndex(1);
			break;
			case "multiple-Unidad":
				list = new Select(u.getDriver().findElement(selectTipoDeMecanicaXpath));
				list.selectByIndex(2);
			break;
			case "promopack - Suma art = precio":
				list = new Select(u.getDriver().findElement(selectTipoDeMecanicaXpath));
				list.selectByIndex(3);
			break;
			case "comunicación e imagen - Sin Precio":
				list = new Select(u.getDriver().findElement(selectTipoDeMecanicaXpath));
				list.selectByIndex(4);
			break;
			case "promopack servicios - Servicio mas precio":
				list = new Select(u.getDriver().findElement(selectTipoDeMecanicaXpath));
				list.selectByIndex(5);
			break;
		}
		list = new Select(u.getDriver().findElement(selectEstatusXpath));
		list.selectByIndex(1);
		
		u.highLight(buttonBuscarFolioXpath);
		u.click(buttonBuscarFolioXpath);
	}
	
	public void cambioEstatusCategoria() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "grid-cuanti.png");
		screen.wait(p1, 30.0);
		screen.click();
		testCase.addScreenShotCurrentStep(u,"");
		u.hardWait(2);
		
		u.scrollToElement(selectCambioEstatusXpath);
		
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "select-estatus.png");
		screen.wait(p1, 30.0);
		screen.click();
		
		u.hardWait(2);
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "estatus-enviada.png");
		screen.wait(p1, 30.0);
		screen.click();
		testCase.addScreenShotCurrentStep(u,"");
		
		u.highLight(btnGuradarCambiosXpath);
		u.click(btnGuradarCambiosXpath);
	}
	
	public void cambioEstatusMultiple() throws FindFailed {
		u.highLight(cbFoliosXpath);
		u.click(cbFoliosXpath);
		testCase.addScreenShotCurrentStep(u,"");
		
		u.scrollToElement(selectCambioEstatusXpath);
		
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "select-estatus.png");
		screen.wait(p1, 30.0);
		screen.click();
		
		u.hardWait(2);
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "estatus-enviada.png");
		screen.wait(p1, 30.0);
		screen.click();
		testCase.addScreenShotCurrentStep(u,"");
		
		u.highLight(btnGuradarCambiosXpath);
		u.click(btnGuradarCambiosXpath);
	}
	
	/**
	 * @author José Zárate
	 * Cambia el estatus de una promoción, de "Borrador" a "Enviada"
	 * @throws FindFailed 
	 */
	public void cambioEstatusAdmin() throws FindFailed {
		u.waitWebElement(checkBoxFolioXpath, 30, 2);
		u.click(checkBoxFolioXpath);
		testCase.addScreenShotCurrentStep(u,"");
		u.hardWait(3);
		
		u.scrollToElement(selectCambioEstatusXpath);
		
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "select-estatus.png");
		screen.wait(p1, 30.0);
		screen.click();
		
		u.hardWait(2);
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "estatus-enviada.png");
		screen.wait(p1, 30.0);
		screen.click();
		testCase.addScreenShotCurrentStep(u,"");
		
		u.highLight(btnGuradarCambiosXpath);
		u.click(btnGuradarCambiosXpath);
	}
	
	/**
	 * @author José Antonio
	 * Click en el botón Modificar, del grid de folios encontrados
	 * @throws Exception 
	 */
	public void selectEditPromo() throws Exception {
		u.click(buttonEditPromoXpath);
	}
	
	/**
	 * @author José Antonio
	 * Click en el botón clonar, del grid de folios encontrados
	 * @throws Exception 
	 */
	public void selectClonarPromo() throws Exception {
		u.click(buttonClonarPromoXpath);
	}
	
	/**
	 * @author José Zárate
	 * Continua con el llenado de los campos de una mecánica simple
	 */
	public void continueMecanicaSimple() {
		u.scrollToElement(radioButtonTCTDXpath);
		u.click(radioButtonTCTDXpath);
		
		u.click(checkBoxSegmentosXpath);
		
		u.scrollToElement(textBoxMsnPromoXpath);
		u.clear(textBoxMsnPromoXpath);
		u.highLight(textBoxMsnPromoXpath);
		u.sendKeys(textBoxMsnPromoXpath, "PAGO");
		
		list = new Select(u.getDriver().findElement(selectBanderaXpath));
		list.selectByIndex(utils.randomIndex(6));
		
		list = new Select(u.getDriver().findElement(selectBancoXpath));
		list.selectByIndex(utils.randomIndex(50));
		
		list = new Select(u.getDriver().findElement(selectTipoXpath));
		list.selectByIndex(utils.randomIndex(5));
		testCase.addScreenShotCurrentStep(u, "Muestra el llenado de los datos");
	}
	
	/**
	 * @author José Zárate
	 * Continua con el llenado de los campos de una mecánica multiple
	 * @param tipo --> Tipo de mecánica múltiple (Unidad o Precio)
	 */
	public void continueEditMecanicaMultiple(String tipo) {
		u.scrollToElement(radioButtonTCTDXpath);
		u.click(radioButtonTCTDXpath);

		u.click(checkBoxSegmentosXpath);
		
		u.clear(textBoxMsnPromoXpath);
		u.highLight(textBoxMsnPromoXpath);
		u.sendKeys(textBoxMsnPromoXpath, "PAGO");
		
		list = new Select(u.getDriver().findElement(selectBanderaXpath));
		list.selectByIndex(utils.randomIndex(6));
		
		list = new Select(u.getDriver().findElement(selectBancoXpath));
		list.selectByIndex(utils.randomIndex(50));
		
		list = new Select(u.getDriver().findElement(selectTipoXpath));
		list.selectByIndex(utils.randomIndex(5));
		
		//LLena las cajas de la implementacion mecanica
		u.highLight(textBoxUnidadXpath);
		u.sendKeys(textBoxUnidadXpath, "1");
		
		u.highLight(textBoxPrecioXpath);
		u.sendKeys(textBoxPrecioXpath, "30");
		
		if(tipo.equals("Unidad")) {
			u.click(buttonUnidadXpath);
		}else {
			u.click(buttonPrecioXpath);
		}
		testCase.addScreenShotCurrentStep(u,"");		
	}

	/**
	 * @author José Zárate
	 * Limpia el tipo de mecanica que trae originalmente
	 * @throws InterruptedException
	 */
	public void limpiaMecanicaYMetodo() throws InterruptedException {
		list = new Select(u.getDriver().findElement(selectTipoMecanicaXpath));
		list.selectByIndex(0);
		Thread.sleep(3000);
		testCase.addScreenShotCurrentStep(u, "");
	}

	/**
	 * @author José Zárate
	 * Realiza las transformaciones a un nuevo tipo de mecánica
	 * @param mecanica --> Tipo de mecánica a la cual se realizará el cambio
	 * @throws Exception
	 */
	public void cambioMecanica(String mecanica) throws Exception {
		Thread.sleep(3000);
		switch(mecanica) {
			case "simple-Descuento":
				mecanicaSimpleDescuento();
				continueMecanicaSimple();
				break;
			case "simple-Precio":
				mecanicaSimplePrecio();
				continueMecanicaSimple();
				break;
			case "multiple-Unidad":
				mecanicaCompraMultipleUnidad();
				continueEditMecanicaMultiple("Unidad");
				break;
			case "multiple-Precio":
				mecanicaCompraMultiplePrecio();
				continueEditMecanicaMultiple("Precio");
				break;
			case "promopack - Suma art = precio":
				mecanicaPromopack();
				continueEditPromo();
				break;
			case "promopack-art+precio=art2":
				mecanicaPromopackArticuloMasPrecio();
				continueEditPromo();
				break;
			case "promopack-art gratis":
				mecanicaPromopackArticuloGratis();
				continueEditPromo();
				break;
			case "promopack-art = descuento art2":
				mecanicaPromopackArticuloDescuento();
				continueEditPromo();
				break;
			case "comunicación e imagen - Sin Precio":
				mecanicaComunicacionEImagenSinPrecio();
				continueEditPromo();
				break;
			case "comunicación e imagen - Precio Regular":
				mecanicaComunicacionEImagenPrecioRegular();
				continueEditPromo();
				break;
			case "promopack servicios - Servicio mas precio":
				mecanicaPromopackServicios();
				continueEditPromo();
				break;
		}
	}
	
	/**
	 * @author José Zárate
	 * Continua la edición de la promoción
	 */
	public void continueEditPromo() {
		u.scrollToElement(checkBoxSiIngresoXpath);
		
		if(u.getDriver().findElement(checkBoxSiIngresoXpath).isSelected()) {
			u.click(checkBoxNoIngresoXpath);
		}else if(u.getDriver().findElement(checkBoxNoIngresoXpath).isSelected()) {
			u.click(checkBoxSiIngresoXpath);
		}
		
		u.scrollToElement(radioButtonTCTDXpath);
		u.click(radioButtonTCTDXpath);

		u.click(checkBoxSegmentosXpath);
		
		u.clear(textBoxMsnPromoXpath);
		u.scrollToElement(textBoxMsnPromoXpath);
		u.highLight(textBoxMsnPromoXpath);
		u.sendKeys(textBoxMsnPromoXpath, "PAGO");
		
		list = new Select(u.getDriver().findElement(selectBanderaXpath));
		list.selectByIndex(utils.randomIndex(6));
		
		list = new Select(u.getDriver().findElement(selectBancoXpath));
		list.selectByIndex(utils.randomIndex(50));
		
		list = new Select(u.getDriver().findElement(selectTipoXpath));
		list.selectByIndex(utils.randomIndex(5));
	}
	
	/**
	 * @author José Zárate
	 * Edita una promoción existente y la convierte en una mecánica promopack
	 * @throws Exception
	 */
	public void mecanicaPromopack() throws Exception {
		list = new Select(u.getDriver().findElement(selectTipoMecanicaXpath));
		list.selectByIndex(3);
		
		u.waitWebElement(buttonAceptarPromopackXpath, 10, 2);
		u.highLight(buttonAceptarPromopackXpath);
		u.click(buttonAceptarPromopackXpath);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u,"");
		
		list = new Select(u.getDriver().findElement(selectMetodoMecanicaXpath));
		list.selectByIndex(1);
		
		u.highLight(textBoxDescArticulo1Xpath);
		u.sendKeys(textBoxDescArticulo1Xpath, "cigarros");
		
		u.highLight(textBoxDescArticulo2Xpath);
		u.sendKeys(textBoxDescArticulo2Xpath, "cerveza");
		
		u.highLight(textBoxPrecioPromoPackXpath);
		u.sendKeys(textBoxPrecioPromoPackXpath, "100");
		
		list = new Select(u.getDriver().findElement(selectTextoApoyoXpath));
		list.selectByIndex(1);
		testCase.addScreenShotCurrentStep(u,"");
	}
	
	/**
	 * @author José Zárate
	 * Edita una promoción existente y la convierte en una mecánica promopack - Articulo mas precio 
	 * @throws Exception
	 */
	public void mecanicaPromopackArticuloMasPrecio() throws Exception {
		list = new Select(u.getDriver().findElement(selectTipoMecanicaXpath));
		list.selectByIndex(3);
		
		u.waitWebElement(buttonAceptarPromopackXpath, 10, 2);
		u.highLight(buttonAceptarPromopackXpath);
		u.click(buttonAceptarPromopackXpath);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u,"");
		
		list = new Select(u.getDriver().findElement(selectMetodoMecanicaXpath));
		list.selectByIndex(2);

		u.highLight(textBoxDescArticuloXpath);
		u.sendKeys(textBoxDescArticuloXpath, "cigarros");
		
		u.highLight(textBoxPromPrecioXpath);
		u.sendKeys(textBoxPromPrecioXpath, "30");
		
		u.highLight(textBoxDescArticulosXpath);
		u.sendKeys(textBoxDescArticulosXpath, "cerveza");
		
		list = new Select(u.getDriver().findElement(selectTextoApoyoXpath));
		list.selectByIndex(1);
		testCase.addScreenShotCurrentStep(u,"");
	}
	
	/**
	 * @author José Zárate
	 * Edita un promoción existente y la convierte en una mecánica promopack - Articulo gratis 
	 * @throws Exception
	 */
	public void mecanicaPromopackArticuloGratis() throws Exception {
		list = new Select(u.getDriver().findElement(selectTipoMecanicaXpath));
		list.selectByIndex(3);
		
		u.waitWebElement(buttonAceptarPromopackXpath, 10, 2);
		u.highLight(buttonAceptarPromopackXpath);
		u.click(buttonAceptarPromopackXpath);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u,"");
		
		list = new Select(u.getDriver().findElement(selectMetodoMecanicaXpath));
		list.selectByIndex(3);

		u.highLight(textBoxDescArtXpath);
		u.sendKeys(textBoxDescArtXpath, "cerveza");
		
		u.highLight(textBoxDescArticulosGratisXpath);
		u.sendKeys(textBoxDescArticulosGratisXpath, "sabritas");
		
		list = new Select(u.getDriver().findElement(selectTextoApoyoXpath));
		list.selectByIndex(1);
		testCase.addScreenShotCurrentStep(u,"");
	}
	
	/**
	 * @author José Zárate
	 * Edita una promoción existente y la convierte en una mecánica promopack - Articulo descuento 
	 * @throws Exception
	 */
	public void mecanicaPromopackArticuloDescuento() throws Exception {
		list = new Select(u.getDriver().findElement(selectTipoMecanicaXpath));
		list.selectByIndex(3);
		
		u.waitWebElement(buttonAceptarPromopackXpath, 10, 2);
		u.highLight(buttonAceptarPromopackXpath);
		u.click(buttonAceptarPromopackXpath);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u,"");
		
		list = new Select(u.getDriver().findElement(selectMetodoMecanicaXpath));
		list.selectByIndex(4);
		
		u.highLight(textBoxDescArtDescuentoXpath);
		u.sendKeys(textBoxDescArtDescuentoXpath, "cerveza");
		
		u.highLight(textBoxDescXpath);
		u.sendKeys(textBoxDescXpath, "10");
		
		u.highLight(textBoxArtDescuentoXpath);
		u.sendKeys(textBoxArtDescuentoXpath, "cigarros");
		
		list = new Select(u.getDriver().findElement(selectTextoApoyoXpath));
		list.selectByIndex(1);
		testCase.addScreenShotCurrentStep(u,"");
	}
	
	/**
	 * @author José Zárate
	 * Edita una promoción existente y la convierte en una mecánica Promopack servicios - Servicio mas precio
	 * @throws Exception
	 */
	public void mecanicaPromopackServicios() throws Exception {
		list = new Select(u.getDriver().findElement(selectTipoMecanicaXpath));
		list.selectByIndex(5);
		
		u.waitWebElement(buttonAceptarPromopackXpath, 10, 2);
		u.highLight(buttonAceptarPromopackXpath);
		u.click(buttonAceptarPromopackXpath);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u,"");
		
		list = new Select(u.getDriver().findElement(selectMetodoMecanicaXpath)); 
		list.selectByIndex(1);
		
		u.highLight(textBoxDescArtSerXpath);
		u.sendKeys(textBoxDescArtSerXpath, "Recarga");
		
		u.highLight(textBoxPrecioSerXpath);
		u.sendKeys(textBoxPrecioSerXpath, "10");
		
		u.highLight(textBoxDescArtSerXpath2);
		u.sendKeys(textBoxDescArtSerXpath2, "Sabritas");
		
		list = new Select(u.getDriver().findElement(selectTextoApoyoXpath));
		list.selectByIndex(1);
		testCase.addScreenShotCurrentStep(u,"");
	}
	
	/**
	 * @author José Zárate
	 * Edita una promoción existente y la convierte en una mecánica Comunicacion e imagen - sin precio 
	 * @throws Exception
	 */
	public void mecanicaComunicacionEImagenSinPrecio() throws Exception {
		list = new Select(u.getDriver().findElement(selectTipoMecanicaXpath));
		list.selectByIndex(4);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u,"");

		list = new Select(u.getDriver().findElement(selectMetodoMecanicaXpath));
		list.selectByIndex(1);
		
		u.highLight(textBoxDescArtComunicacionXpath);
		u.sendKeys(textBoxDescArtComunicacionXpath, "cerveza");
		
		list = new Select(u.getDriver().findElement(selectTextoApoyoXpath));
		list.selectByIndex(1);
		testCase.addScreenShotCurrentStep(u,"");
	}
	
	/**
	 * @author José Zárate
	 * Edita una promoción existente y la convierte en una mecánica Comunicacion e imagen - Precio regular 
	 * @throws Exception
	 */
	public void mecanicaComunicacionEImagenPrecioRegular() throws Exception {
		list = new Select(u.getDriver().findElement(selectTipoMecanicaXpath));
		list.selectByIndex(4);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u,"");

		list = new Select(u.getDriver().findElement(selectMetodoMecanicaXpath));
		list.selectByIndex(2);

		u.highLight(textBoxDescArtComXpath);
		u.sendKeys(textBoxDescArtComXpath, "cerveza");
		
		u.highLight(textBoxPrecioComunicacionXpath);
		u.sendKeys(textBoxPrecioComunicacionXpath, "30");
		
		list = new Select(u.getDriver().findElement(selectTextoApoyoXpath));
		list.selectByIndex(1);
		testCase.addScreenShotCurrentStep(u,"");
	}
	
	/**
	 * @author José Zárate
	 * Edita una promoción existente y la convierte en una mecánica de compra multiple
	 * @throws Exception
	 */
	public void mecanicaCompraMultipleUnidad() throws Exception {
		u.scrollToElement(selectEventoXpath);
		//Selecciona el evento y tipo de promocion ya que en algunas ocaciones aparecen sin selección 
		list = new Select(u.getDriver().findElement(selectEventoXpath));
		list.selectByIndex(1);
		
		list = new Select(u.getDriver().findElement(selectTipoPromocionXpath));
		list.selectByIndex(1);
		
		list = new Select(u.getDriver().findElement(selectTipoMecanicaXpath));
		list.selectByIndex(2);
		list = new Select(u.getDriver().findElement(selectMetodoMecanicaXpath));
		list.selectByIndex(1);
		
		u.highLight(textBoxCantidad1Xpath);
		u.sendKeys(textBoxCantidad1Xpath, "1");
		u.highLight(textBoxCantidad2Xpath);
		u.sendKeys(textBoxCantidad2Xpath, "30");
		
		u.highLight(textBoxDescripcionMuiltipleXpath);
		u.sendKeys(textBoxDescripcionMuiltipleXpath, "cerveza");
		
		list = new Select(u.getDriver().findElement(selectTextoApoyoXpath));
		list.selectByIndex(1);
		
		testCase.addScreenShotCurrentStep(u,"");
	}
	
	/**
	 * @author José Zárate
	 * Edita una promoción existente y la convierte en una mecánica de compra multiple - precio
	 * @throws Exception
	 */
	public void mecanicaCompraMultiplePrecio() {
		u.scrollToElement(selectEventoXpath);
		//Selecciona el evento y tipo de promocion ya que en algunas ocaciones aparecen sin selección 
		list = new Select(u.getDriver().findElement(selectEventoXpath));
		list.selectByIndex(1);
		
		list = new Select(u.getDriver().findElement(selectTipoPromocionXpath));
		list.selectByIndex(1);
		
		list = new Select(u.getDriver().findElement(selectTipoMecanicaXpath));
		list.selectByIndex(2);
		list = new Select(u.getDriver().findElement(selectMetodoMecanicaXpath));
		list.selectByIndex(2);
		
		u.highLight(textBoxCantidadXpath);
		u.sendKeys(textBoxCantidadXpath, "1");
		u.highLight(textBoxMultiplePrecioXpath);
		u.sendKeys(textBoxMultiplePrecioXpath, "30");
		
		u.highLight(textBoxMultipleDescXpath);
		u.sendKeys(textBoxMultipleDescXpath, "cerveza");
		
		list = new Select(u.getDriver().findElement(selectTextoApoyoXpath));
		list.selectByIndex(1);
		
		testCase.addScreenShotCurrentStep(u,"");
	}
	
	/**
	 * @author José Zárate
	 * Edita una promoción existente y la convierte en una mecánica simple - descuento
	 * @throws Exception
	 */
	public void mecanicaSimpleDescuento() {
		list = new Select(u.getDriver().findElement(selectEventoXpath));
		list.selectByIndex(1);
		
		list = new Select(u.getDriver().findElement(selectTipoPromocionXpath));
		list.selectByIndex(1);
		
		list = new Select(u.getDriver().findElement(selectTipoMecanicaXpath));
		list.selectByIndex(1);
		
		list = new Select(u.getDriver().findElement(selectMetodoMecanicaXpath));
		list.selectByIndex(1);
		
		u.highLight(textBoxDescripcionSimpleXpath);
		u.sendKeys(textBoxDescripcionSimpleXpath, "cerveza");
		u.highLight(textBoxDescuentoXpath);
		u.sendKeys(textBoxDescuentoXpath, "10");
		
		list = new Select(u.getDriver().findElement(selectTextoApoyoXpath));
		list.selectByIndex(1);
		testCase.addScreenShotCurrentStep(u, "");
	}
	
	/**
	 * @author José Zárate
	 * Edita una promoción existente y la convierte en una mecánica simple - precio
	 * @throws Exception
	 */
	public void mecanicaSimplePrecio() {
		list = new Select(u.getDriver().findElement(selectEventoXpath));
		list.selectByIndex(1);
		
		list = new Select(u.getDriver().findElement(selectTipoPromocionXpath));
		list.selectByIndex(1);
		
		list = new Select(u.getDriver().findElement(selectTipoMecanicaXpath));
		list.selectByIndex(1);
		
		list = new Select(u.getDriver().findElement(selectMetodoMecanicaXpath));
		list.selectByIndex(2);
		
		u.highLight(textBoxDescripcionPrecioXpath);
		u.sendKeys(textBoxDescripcionPrecioXpath, "cerveza");
		
		u.highLight(textBoxPrecioMecanicaXpath);
		u.sendKeys(textBoxPrecioMecanicaXpath, "30");
		
		list = new Select(u.getDriver().findElement(selectTextoApoyoXpath));
		list.selectByIndex(1);
		testCase.addScreenShotCurrentStep(u, "");
	}
	
	//Cambiar los if por un switch case 
	public void editMecanicas(String tipoMecanica) {
		u.waitWebElement(selectTipoMecanicaXpath, 30, 2);
		if(tipoMecanica.contentEquals("simple-Descuento") || tipoMecanica.contentEquals("simple-Precio")) {
			list = new Select(u.getDriver().findElement(selectTipoMecanicaXpath));
			list.selectByIndex(1);
		}
		else if(tipoMecanica.contentEquals("multiple-Unidad") || tipoMecanica.contentEquals("multiple-Precio")) {
			list = new Select(u.getDriver().findElement(selectTipoMecanicaXpath));
			list.selectByIndex(2);
		}
		else if(tipoMecanica.contentEquals("promopack - Suma art = precio")) {
			list = new Select(u.getDriver().findElement(selectMetodoMecanicaXpath));
			list.selectByIndex(1);
		}
		else if(tipoMecanica.contentEquals("promopack-art+precio=art2")) {
			list = new Select(u.getDriver().findElement(selectMetodoMecanicaXpath));
			list.selectByIndex(2);
		}
		else if(tipoMecanica.contentEquals("promopack-art gratis")) {
			list = new Select(u.getDriver().findElement(selectMetodoMecanicaXpath));
			list.selectByIndex(3);
		}
		else if(tipoMecanica.contentEquals("promopack-art = descuento art2")) {
			list = new Select(u.getDriver().findElement(selectMetodoMecanicaXpath));
			list.selectByIndex(4);
		}
		else if(tipoMecanica.contentEquals("comunicación e imagen - Sin Precio") || tipoMecanica.contentEquals("comunicación e imagen - Precio Regular")) {
			list = new Select(u.getDriver().findElement(selectTipoMecanicaXpath));
			list.selectByIndex(4);
		}
		else if(tipoMecanica.contentEquals("promopack servicios - Servicio mas precio")) {
			list = new Select(u.getDriver().findElement(selectTipoMecanicaXpath));
			list.selectByIndex(5);
		}
	}
	
	/**
	 * @author José Zárate
	 * Del listbox de Promociones selecciona la opción de crear
	 * @throws Exception
	 */
	public void accessCrearNuevaPromocion() throws Exception {
		selectPromocionesCategoria();
		u.highLight(promocionCrearXpath);
		u.click(promocionCrearXpath);		
	}
	
	/**
	 * @author José Zárate
	 * LLena los campos para activar la mecánica promopack
	 * @throws FindFailed 
	 * @throws Exception
	 */
	public void implementacionMecanica() throws FindFailed {			
		u.scrollToElement(textBoxSum1MecanicaXpath);
		u.highLight(textBoxSum1MecanicaXpath);
		u.sendKeys(textBoxSum1MecanicaXpath, "1");
		
		u.highLight(textBoxSum2MecanicaXpath);
		u.sendKeys(textBoxSum2MecanicaXpath, "1");
		
		u.scrollToElement(categoria1Xpath);
		u.highLight(categoria1Xpath);
		u.click(categoria1Xpath);
		
		//u.highLight(categoriaComponente1Xpath);
		//u.click(categoriaComponente1Xpath);
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "telefonia.png");
		screen.wait(p1, 30.0);
		screen.click();
		
		u.scrollToElement(categoria2Xpath);
		u.highLight(categoria2Xpath);
		u.click(categoria2Xpath);
		
		//u.highLight(categoriaComponente2Xpath);
		//u.click(categoriaComponente2Xpath);
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "telefonia.png");
		screen.wait(p1, 30.0);
		screen.click();
		u.scrollToElement(textBoxSum1MecanicaXpath);
	}
	
	/**
	 * @author José Zárate
	 * LLena los campos para activar la mecánica promopack servicios
	 * @throws Exception
	 */
	public void implementacionMecanicaServicios() throws Exception {		
		//u.scroll(500);
		u.scrollToElement(textBoxSum1MecanicaXpath);
		u.highLight(textBoxSum1MecanicaXpath);
		u.sendKeys(textBoxSum1MecanicaXpath, "1");
		
		u.highLight(textBoxSum2MecanicaXpath);
		u.sendKeys(textBoxSum2MecanicaXpath, "1");
		 
		u.scrollToElement(categoria1Xpath);
		u.highLight(categoria1Xpath);
		u.click(categoria1Xpath);
		
		//u.highLight(categoriaComponente1Xpath);
		//u.click(categoriaComponente1Xpath);
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "telefonia.png");
		screen.wait(p1, 30.0);
		screen.click();
		
		u.scrollToElement(categoria2Xpath);
		u.highLight(categoria2Xpath);
		u.click(categoria2Xpath);
		
		//u.highLight(categotiaServicioXpath);
		//u.click(categotiaServicioXpath); 
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "telefonia.png");
		screen.wait(p1, 30.0);
		screen.click();
		u.scrollToElement(textBoxSum1MecanicaXpath);
	}
	
	/**
	 * @author José Zárate
	 * Selecciona el tipo de mecánica múltiple
	 */
	public void selectMecanicaMultiple() {
		list = new Select(u.getDriver().findElement(selectTipoMecanicaXpath));
		list.selectByIndex(2);
		u.hardWait(3);
		
		u.scrollToElement(textBoxMsnPromoXpath);
	}
	
	/**
	 * @author José Zárate
	 * Da click en el botón Activar, para activar la mecanica promopack
	 * @throws Exception
	 */
	public void selectActivarMecanica() throws Exception {
		u.scroll(500);
		u.highLight(buttonActivarXpath);
		u.click(buttonActivarXpath);	
	}
	
	/**
	 * @author José Zárate
	 * Carga los productos de la categoria telefonia y entretenimiento de la implementacion de la mecanica promopack servicios
	 * @throws Exception
	 */
	public void cargarArticulos() throws Exception {
		u.hardWait(3);
		u.scroll(500);
		u.highLight(buttonBuscarArticuloXpath);
		u.click(buttonBuscarArticuloXpath);
		
		u.waitWebElement(textBoxCoincidenciasXpath, 30, 2);
		u.sendKeys(textBoxCoincidenciasXpath, "sabritas");
		
		u.highLight(buttonBuscarCoincidenciasXpath);
		u.click(buttonBuscarCoincidenciasXpath);
		
		u.waitWebElement(selectArticuloXpath, 30, 2);
		u.highLight(selectArticuloXpath);
		u.click(selectArticuloXpath);
		
		u.highLight(buttonAgregarArtXpath);
		u.click(buttonAgregarArtXpath);	

		u.highLight(textBoxProvedorXpath);
		u.sendKeys(textBoxProvedorXpath, "1");
		
		u.highLight(textBoxBonificacionXpath);
		u.sendKeys(textBoxBonificacionXpath, "1");
		
		list = new Select(u.getDriver().findElement(selectBonificacionXpath));
		list.selectByIndex(1);
		
		u.highLight(textBoxPiezasXpath);
		u.sendKeys(textBoxPiezasXpath, "1");
		
		u.highLight(textBoxCtdSugeridaXpath);
		u.sendKeys(textBoxCtdSugeridaXpath, "1");
		
		list = new Select(u.getDriver().findElement(selectMotivoXpath));
		list.selectByIndex(1);
	}
	
	/**
	 * @author José Zárate
	 * Carga los servicios de la categoria servicios de la implementacion de la mecanica promopack servicios
	 * @throws Exception
	 */
	public void cargarServicios() throws Exception {
		u.highLight(buttonServiciosXpath);
		u.click(buttonServiciosXpath);
		
		u.highLight(buttonBuscarArticuloXpath);
		u.click(buttonBuscarArticuloXpath);
		
		u.waitWebElement(textBoxCoincidenciasXpath, 30, 2);
		u.sendKeys(textBoxCoincidenciasXpath, "recarga");
		
		u.highLight(buttonBuscarCoincidenciasXpath);
		u.click(buttonBuscarCoincidenciasXpath);
		
		u.waitWebElement(selectServiciosXpath, 30, 2);
		u.highLight(selectServiciosXpath);
		u.click(selectServiciosXpath);
		
		u.highLight(buttonAgregarArtXpath);
		u.click(buttonAgregarArtXpath);	

		u.highLight(textBoxProvedor2Xpath);
		u.sendKeys(textBoxProvedor2Xpath, "1");
	}
	
	
	/**
	 * @author José Antonio 
	 * Guarda la promoción creada y guarda su folio 
	 * @throws Exception
	 */
	public void guardarPromocion() throws Exception, FindFailed {
		u.scrollToElement(buttonGuardarPromoXpath);
		u.highLight(buttonGuardarPromoXpath);
		u.click(buttonGuardarPromoXpath);
		
		Thread.sleep(8000);
		validateMSNGuardar();
		aceptarGuardado();
		
		u.waitWebElement(headerAltaPromocion, 30, 2);
		folio = u.getText(folioPromocionXpath).trim();
		System.out.println("El id de la promocion es: "+folio);
	}
	
	
	/**
	 * @author José Zárate
	 * Una vez que se de click en el botón "Guardar", se acepta el mensaje del guardado correcto
	 */
	public void aceptarGuardado() throws FindFailed, InterruptedException {
		try {
			s1 = sikuli.PatternPath(folder);
			p1 = sikuli.PatternImage(s1 + "button-aceptar-envio.png");
			screen.wait(p1, 30.0);
			System.out.println("Se encontro la imagen del boton Aceptar");
			testCase.addScreenShotCurrentStep(u,"");
			u.hardWait(3);
			
			u.waitWebElement(buttonAceptarXpath, 120, 2);
			u.click(buttonAceptarXpath);
			u.highLight(buttonAceptarXpath);
				
			System.out.println("Se dio click en el boton Aceptar");
				
			if(u.getDriver().findElement(buttonAceptarXpath).isDisplayed()) {
				System.out.println("El mensaje de aceptacion al guaradar, aun esta desplegado");
				Thread.sleep(2000);
				aceptarGuardado();
			}
		}catch(Exception ex) {
			
		}							
	}
	
	/**
	 * @author José Antonio 
	 * Después de guardar la promoción la envia 
	 * @throws InterruptedException
	 * @throws FindFailed
	 */
	/*public void enviarPromocion() throws InterruptedException, FindFailed {
		Thread.sleep(3000);
		while(!u.waitWebElement(buttonAceptarEnvioXpath, 120, 2).isEnabled()) {
			System.out.println("El boton enviar aun no esta disponible");
			Thread.sleep(1000);
		}
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
	}*/
	
	/**
	 * @author José Antonio
	 * Verifica que la pagina haya terminado de cargar
	 * @return --> valor boolean
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
	 * @author José Zárate
	 * Da click en el botón Aceptar del mensaje de error
	 */
	public void aceptarError() {
		testCase.addScreenShotCurrentStep(u, "Da click en el botón aceptar del mensaje de error");
		u.highLight(buttonAceptarXpath);
		u.click(buttonAceptarXpath);
	}
	
	/**
	 * @author José Zárate
	 * Valida el ingreso de las credenciales en el login de ultimus
	 * @return --> True si se llenaron los campos de forma correcta, False en el caso contrario
	 * @throws FindFailed
	 */
	public Boolean validateAccessLoginUltimus() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "access-ultimus.png");
		
		if(screen.wait(p1, 60.0) != null){
			return true;			
		}
		else{
			return false;
		}
	}
	
	/**
	 * @author José Zárate
	 * Valida el acceso a la pagina de ultimus con usuario Administrador
	 * @return --> True si se accedio de forma correcta, False en el caso contrariov
	 * @throws FindFailed
	 */
	public Boolean validateAccessUltimus() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "ultimus.png");
		
		if(screen.wait(p1, 60.0) != null){
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * @author José Zárate
	 * Valida el acceso a la pagina de ultimus con usuario Categoria
	 * @return --> True si se accedio de forma correcta, False en el caso contrariov
	 * @throws FindFailed
	 */
	public Boolean validateAccessUltimusCategoria() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "ultimus-categoria.png");
		
		if(screen.wait(p1, 60.0) != null){
			return true;
		}else {
			return false;
		}
	}
	
	
	/**
	 * @author José Zárate
	 * Valida las aplicaciones de ultimus disponibles para el usuario Administrador
	 * @return --> True si se muestran las apps de forma correcta, False en el caso contrariov
	 * @throws FindFailed
	 */
	public Boolean validateUserAdminAppsUltimus() throws FindFailed{
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "user-apps-ultimus.png");
		
		if(screen.wait(p1, 60.0) != null){
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * @author José Zárate
	 * Valida las aplicaciones de ultimus disponibles para el usuario Categoria
	 * @return --> True si se muestran las apps de forma correcta, False en el caso contrariov
	 * @throws FindFailed
	 */
	public Boolean validateUserAdminAppsUltimusCategoria() throws FindFailed{
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "user-apps-ultimus-categoria.png");
		
		if(screen.wait(p1, 60.0) != null){
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * @author José Zárate
	 * Valida el acceso al portal de Cuanti
	 * @return --> True si se muestran la pantalla de inicio del portal promoxxo, False en el caso contrariov
	 * @throws FindFailed
	 */
	public Boolean validateAccessPromoxxoClone() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "logo-tienda.png");
		
		if(screen.wait(p1, 60.0) != null){
			System.out.println("Se ingreso a la pagina de Cuanti");
			return true;
		}else {
			System.out.println("No se pudo validar el acceso a Cuanti");
			return false;
		}
	}
	
	/**
	 * @author José Zárate
	 * Valida el acceso a la pantalla de Motivos de oferta
	 * @return --> True si se muestran la pantalla de inicio del portal promoxxo, False en el caso contrariov
	 * @throws FindFailed
	 */
	public Boolean validateAccessMotivosDeOferta() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "motivos-de-oferta.png");
		
		if(screen.wait(p1, 60.0) != null){
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * @author José Zárate
	 * Valida el acceso a la pantalla de Motivos de oferta
	 * @return --> True si se muestran la pantalla de motivos de oferta, False en el caso contrariov
	 * @throws FindFailed
	 */
	public Boolean validateCapturaNombreMotivo() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "nombre-motivo.png");
		
		if(screen.wait(p1, 60.0) != null){
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * @author José Zárate
	 * Valida la creación del motivo de oferta
	 * @return --> True si se muestran el mensaje "El Motivo Oferta se ha creado exitosamente", False en el caso contrariov
	 * @throws FindFailed
	 */
	public Boolean validateGuardarMotivo() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "motivo-creado.png");
		
		if(screen.wait(p1, 60.0) != null){
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * @author José Zárate
	 * Valida la aparición del mensaje "Ya existe un motivo oferta con el nombre especificado"
	 * @return --> True en caso de encontrar el mensaje, False en caso contrario
	 * @throws FindFailed
	 */
	public Boolean validateMotivoOfertaExistente() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "motivo-ya-creado.png");
		
		if(screen.wait(p1, 60.0) != null){
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * @author José Zárate
	 * Valida la aparición del botón "Borrar" del mensaje de confirmación del borrado
	 * @return --> True en caso de aparecer el botón "Borrar", False en caso contrario
	 * @throws FindFailed
	 */
	public Boolean validateBotonConfirmBorrado() throws Exception {
		if(u.waitWebElement(btnConfirmBorrarXpath, 120, 2).isEnabled()) {
			return true;
		}
		else {
			return false;			
		}
	}
	
	/**
	 * @author José Zárate
	 * Valida la aparición del mensaje de confirmación del borrado
	 * @return --> True en caso de aparecer el mensaje de confirmación, False en caso contrario
	 * @throws FindFailed
	 */
	public Boolean validateMsnConfirmBorrados() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "msn-confirmacion.png");
		
		if(screen.wait(p1, 60.0) != null){
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * @author José Zárate
	 * Valida que se haya eliminado el componente de forma correcta
	 * @param bExecution --> Clase heredada para la ejecucion del metodos del framework 
	 * @param id		 --> ID del componnete eliminado
	 * @return --> True si el componente se elimino de forma correcta, False en caso contrario
	 * @throws SQLException
	 */
	public Boolean validateExistComponente(BaseExecution bExecution, String id) throws SQLException {
		
		if(new UtilsMecanicas().buscarComponenteEliminado(bExecution, id)) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * @author José Zárate
	 * Valida que se haya cambiado de un estatus "Borrador" a "Enviada" de forma exitosa.
	 * @return --> True en caso que se haya encontrado el mensaje de exito, False en caso contrario.
	 * @throws FindFailed
	 */
	public Boolean validateSelectEstatus() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "actualizacion-exitosa.png");
		
		if(screen.wait(p1, 60.0) != null){
			return true;
		}else {
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
	 * Verifica el acceso a la pagina "Administración de paquetes y componentes"
	 * @return --> True en caso de que se haya ingresado a la pagina y el boton componentes ya se encuentre habilitado. False en caso contrario.
	 * @throws FindFailed
	 * @throws InterruptedException 
	 */
	public Boolean validateAccessAdminPaquetes() throws FindFailed, InterruptedException {
		if(u.waitWebElement(btnComponentesXpath, 120, 2).isEnabled()) {
			return true;
		}
		else {
			return false;			
		}
	}
	
	/**
	 * @author José Antonio
	 * Verifica el acceso a la pagina "Administración de paquetes y componentes"
	 * @return --> True en caso de que se haya ingresado a la pagina y el boton componentes ya se encuentre habilitado. False en caso contrario.
	 * @throws FindFailed
	 * @throws InterruptedException 
	 */
	public Boolean validateAccessComponents() throws FindFailed, InterruptedException {
		if(u.waitWebElement(txtComponenteXpath, 120, 2).isEnabled()) {
			return true;
		}
		else {
			return false;			
		}
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
			return true;			
		}
		else{
			return false;
		}
	}
	
	/**
	 * @author José Antonio 
	 * Verifica que encuentre almenos un folio de la promoción especificada
	 * @return valor boolean
	 * @throws FindFailed
	 */
	public Boolean validateBusquedaFolioPromocionPorFiltro() throws Exception {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "grid-cuanti.png");
		
		if(screen.wait(p1, 60.0) != null){
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * @author José Antonio
	 * Verifica que el folio encontrado sea el correcto
	 * @return valor boolean
	 * @throws FindFailed
	 */
	public Boolean validateBusquedaFolioPromocion() throws Exception {
		if(u.getText(numFolioXpath).trim().contentEquals(folio)) {
			testCase.addScreenShotCurrentStep(u, "Encontró el folio ingresado");
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * @author José Antonio
	 * Verifica que se hayan enviado la promoción creada
	 * @return valor boolean
	 * @throws FindFailed
	 */
	public Boolean validateEnviarPromocion() throws FindFailed { 
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
	 * Verifica el acceso a la pagina "Crear nueva promoción" para editar la promoción encontrada
	 * @return valor boolean
	 * @throws FindFailed
	 * @throws InterruptedException 
	 */
	public Boolean validateAccessEdit() throws FindFailed, InterruptedException {
		Boolean status = false;
		
		if(u.waitWebElement(buttonAltaPromocionXpath, 120, 2).isEnabled()) {
			Thread.sleep(2000);
			status = true;
		}else {
			Thread.sleep(2000);
			validateAccessEdit();
		}
		System.out.println("El status retornado será: "+status);
		Thread.sleep(3000);
		return status;
	}
	
	public Boolean validateMecanicaEditable() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "limpia-mecanica.png");
		
		if(screen.wait(p1, 60.0) != null){
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * @author José Antonio
	 * Verifica que el apartado de segmentos sea haya modificado
	 * @return valor boolean
	 * @throws FindFailed
	 */
	public Boolean validateCoberturas() throws Exception {
		return u.getDriver().findElement(checkBoxSegmentosXpath).isSelected();
	}
	
	/**
	 * @author José Zárate
	 * Verifica que los campos de la implementación mecanica promopack hayan sido capturados
	 * @return valor Boolean
	 * @throws Exception
	 */
	public Boolean validateImplementacionMecanica() throws Exception {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "implementacion-mecanica.png");
		
		if(screen.wait(p1, 60.0) != null){
			return true;
		}else {
			return false;
		}
	}
	
	public Boolean validateImplementacionMecanicaVacia() throws Exception {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "implementacion-mecanica-vacia.png");
		
		if(screen.wait(p1, 60.0) != null){
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * @author José Zárate
	 * Verifica que la mecánica se haya activado, si se activo la mecánica el boton Activar se deshabilitará
	 * @return valor Boolean
	 * @throws Exception
	 */
	public Boolean validateActivacion() throws Exception {
		if(!u.waitWebElement(buttonActivarXpath, 120, 2).isEnabled()) { 	
			return true;
		}else {
			return false;
		}
	}
	
	public void validateMSNGuardar() throws Exception {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "ha-sido-guardada.png");
		
		if(screen.wait(p1, 60.0) != null){
			System.out.println("El mensaje \"HA SIDO GUARDADA\" ha sido desplegado");
			guardada = true;
		}else {
			guardada = false;
		}
	}
	
	public Boolean validateGuardar() throws Exception {
		return guardada;
	}
	
	public Boolean validateMSNError() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "msn-error-promopack.png");
		
		if(screen.wait(p1, 60.0) != null){
			System.out.println("Se arrojo el mensaje de error");
			return true;
		}else {
			return false;
		}
	}
}
