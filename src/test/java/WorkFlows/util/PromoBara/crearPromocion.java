package WorkFlows.util.PromoBara;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class crearPromocion {
	
	SeleniumUtil u;
	TestCase testCase;
	String user;
	
	
	By btnPromociones = By.xpath("//a[text()='Promociones']");
	By btnBuscar = By.xpath("//a[text( )=\"Buscar\"]");
	By btnCrear = By.xpath("//a[text( )=\"Crear\"]");
	By btnCancelar = By.cssSelector(".ui-dialog-buttonset > button:nth-child(2) > span:nth-child(2)");
	
	
	By btnSalir = By.xpath("//a[text()=\"Salir\"]");//Superior pantalla
	By btnSalirInf = By.cssSelector("#btnCancelar"); //Inferior pantalla
	
	//PANTALLA BUSCAR PROMOCION
	By btnCrearNuevaPromo = By.cssSelector("#btnCrearNueva > span:nth-child(2)");

	
	//PANTALLA CREAR PROMOCION
	By pestanaPromocion = By.cssSelector("li.ui-corner-top:nth-child(1)");
	By dropDPeriodoPromocional = By.cssSelector("#periodoPromocional-button");
	By btpBuscaArtitulo = By.cssSelector("#btnBuscarArticulo"); 
	By btnBuscaUDA = By.cssSelector("#btnBuscarUda");
	By btnArticuloNuevo = By.cssSelector("#btnArticuloNuevo");
	By btnGuardar = By.cssSelector("#btnGuardar");
	
	By tbxPaquetePromocional = By.cssSelector("#textPaqueteProm"); 
	By opcionesPaquetesPromo = By.xpath("//ul/li[@class=\"ui-menu-item\"]");
	By opcionPaquetePromo = By.cssSelector("#ui-id-18");
	By tbxDescMecanica = By.cssSelector("#descMecanica");
	By tbxDescPos = By.id("descPOS");
	
	By medioPagoTodos = By.cssSelector("#divMedioPago > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > label:nth-child(2) > span:nth-child(1)");
	By medioPagoTDC = By.cssSelector("#divMedioPago > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > label:nth-child(2)");
	By medioPagoMoned = By.cssSelector("#divMedioPago > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(3) > label:nth-child(2) > span:nth-child(1)");

	By selectNecesidad = By.cssSelector("#necesidad-button > span:nth-child(2)");
	By selectNecesidad_Option = By.xpath("//ul/li[@role=\"option\"]");
	By btnRentaEspacios_No = By.cssSelector("#divRentaEspacios > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > label:nth-child(2) > span:nth-child(1)");
	By btnRentaEspacios_SI = By.cssSelector("#divRentaEspacios > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > label:nth-child(2)");

	By btnPeriodoRefe_anterior = By.cssSelector("#divPeriodoReferencia > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > label:nth-child(2) > span:nth-child(1)");
	By btnPeriodoRefe_mismoA = By.cssSelector("label.ui-button:nth-child(4) > span:nth-child(1)");
	
	By btnEnviar = By.cssSelector("#btnEnviar");
	By btnAceptar = By.cssSelector(".ui-dialog-buttonset > button:nth-child(1)");
	By selectTipoMecanica = By.cssSelector("#tipoMecanica-button");
	By optionTipoMecanica = By.xpath("//ul/li[@role = \"option\"]");
	
	By btnLigaConvenio = By.cssSelector("#btnLigaConvenios");
	By btnObtenerFolio = By.cssSelector("#btnGetFoliosPre");
	By pestanaMotivoNoConve = By.xpath("//table//td[text()='MOTIVO DE NO CONVENIO']");
	By rdbutonNoConvenio = By.id("idMotivo_6");
	By btnContinuar = By.id("btnContMotivConvenos");

	
	//PANTALLA BUSCAR UDA
	
	By selectUDAcampo = By.cssSelector("#popUp_lstCampoUda");
	By selectUDAbusqueda = By.cssSelector("#popUp_lstOperadoresUda");
	By inputUDAvalor = By.cssSelector("#popUp_ValorUda");
	By btnUDAbuscar = By.cssSelector("#btnBuscarUdaFilter");
	By selectUDAartiDis = By.cssSelector("#popUp_lstUda");
	By selectUDAartiSelec = By.cssSelector("#popUp_lstSelUda");
	By btnUDAagregar = By.cssSelector("#btnAgregarUda");
	By btnCancelaUda = By.cssSelector("#btnCancelarUda");
	By btnLimpiaUda = By.cssSelector("#btnLimpiarUda");

	
	//COBERTURAS
	By pestanaCobertura = By.cssSelector("li.ui-corner-top:nth-child(2)");
	By chkTodasPlazas = By.cssSelector("#chkSelAllCoberturas");
	By chkCedis = By.cssSelector("#chkSelAllCedis");
	
	//	BUSQUEDA DE ARTICULOS
	
	By btnCancelarArti = By.cssSelector("#btnCancelarArti > span:nth-child(2)");
	By selectSuperGrupo = By.cssSelector("#popUp_lstSupergrupo");
	By selectCategorias = By.cssSelector("#popUp_lstCat");
	By selectSubCategorias = By.cssSelector("#popUp_lstSubCat");
	By selectSegmento = By.cssSelector("#popUp_lstSegmento");
	By selectSubSegmento = By.cssSelector("#popUp_lstSubsegmento");
	By selectCampo = By.cssSelector("#popUp_lstCampo");
	By selectBusquedaPor = By.cssSelector("#popUp_lstOperadores");
	By inputValor = By.cssSelector("#popUp_Valor");
	By btnBuscarArti = By.cssSelector("#btnBuscarFilter");
	By selectArtiDisponibles = By.cssSelector("#popUp_lstArt");
	By selectArtiSeleccionados = By.cssSelector("#popUp_lstSelArticulos");
	By btnFlechaAgregaTodos = By.cssSelector("#popUp_btnAgragarTodos");
	By btnFlechaQuitaTodos = By.cssSelector("#popUp_btnQuitarTodos");
	By btnAgregaArti = By.cssSelector("#btnAgregar");
	By btnLimpiar = By.cssSelector("#btnLimpiar");
	
	//BUSQUEDA DE PROMOCIONES
	
	By inputfolio = By.cssSelector("#folio");
	By selectA = By.cssSelector("#anio");
	By selectPeriodo_buscar = By.cssSelector("#periodoId");
	By btnBuscaFolio = By.cssSelector("#btnBuscar");
	
	
	By alert = By.cssSelector("#divMensaje > p:nth-child(1)");
	
	//CONFIGURACION MECANICA
	By numComponentes = By.id("selNumComponentes-button");
	By selectOptionComp = By.xpath("//ul[@id='selNumComponentes-menu']/li[@role=\"option\"]");
	
	By componente1 = By.cssSelector("#selCategoriaComp_1-button");
	By selectOptionComp1 = By.xpath("//ul[@id='selCategoriaComp_1-menu']/li[@role=\"option\"]");

	By componente2 = By.cssSelector("#selCategoriaComp_2-button");
	By selectOptionComp2 = By.xpath("//ul[@id='selCategoriaComp_2-menu']/li[@role=\"option\"]");

	By userCatComp1 = By.cssSelector("#selUsuarioComp_1-button");
	By selectOptionUserCom1 = By.xpath("//ul[@id='selUsuarioComp_1-menu']/li[@role=\"option\"]");

	By userCatComp2 = By.cssSelector("#selUsuarioComp_1-button");
	By selectOptionUserCom2 = By.xpath("//ul[@id='selUsuarioComp_2-menu']/li[@role=\"option\"]");
	
	By selecCatComp3 = By.cssSelector("#selCategoriaComp_3-button");
	By selectOpCat3 = By.cssSelector("//ul[@id='selCategoriaComp_3-menu']/li[@role=\"option\"]");
	
	By selecUserCat3 = By.id("selUsuarioComp_3-button");
	By selectOptionUserCom3 = By.xpath("//ul[@id='selUsuarioComp_3-menu']/li[@role=\"option\"]");

	
	By tbxMecanica1 = By.cssSelector("#txtComponente1"); 
	By tbxMecanica2 = By.cssSelector("#txtComponente2");
	By tbxMecanica3 = By.cssSelector("#txtComponente3");

	
	By btnActivarMec = By.cssSelector("#btnActivarPP");

	//Configura mecanica precio/unidad 
	
	By tbxP1 = By.cssSelector("#txtCant1");
	By tbxP2 = By.cssSelector("#txtCant2");
	By btnPrecio = By.xpath("//span[text( )='Precio']");
	By btnUnidad = By.xpath("//span[text( )='Unidad']");
	
	By btnSegApartadoArti = By.cssSelector("#divContenedor > ul:nth-child(1) > li:nth-child(2)");
	By btnSegApartadoArti3 = By.id("ui-id-66");
	By btnTerApartadoArti3 = By.id("ui-id-67");

		public crearPromocion (SeleniumUtil u, TestCase testCase, String user ) {
		this.u = u;
		this.testCase = testCase;
		this.user = user;


	}
	
	

	/**
	 * Click en botón aceptar
	 */
	public void btnAceptar() {
		try {
			u.waitWebElement(btnAceptar, 120, 2);
			u.scrollToElement(btnAceptar);

			u.highLight(btnAceptar);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(1);
			u.click(btnAceptar);
			
		}catch(Exception e) {
			
		}

		
	}
	
	/**
	 * Click en boton cancelar
	 * aplica para todas las alertas
	 */
	public void btnCancelar() {
		u.waitWebElement(btnCancelar, 120, 2);

		u.highLight(btnCancelar);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.click(btnCancelar);
	}
	
	/**
	 * Click en la opcion buscar
	 * en el menu promociones
	 */
	public void btnBuscaPromocion() {
		try {
			u.waitWebElement(btnPromociones, 60, 3);
			u.click(tbxPaquetePromocional);
		//	u.mouseOver(dropDPeriodoPromocional);
		//	u.highLight(btnPromociones);
			u.mouseOver(btnPromociones);
			u.hardWait(2); 
			testCase.addScreenShotCurrentStep("");
			u.waitWebElement(btnBuscar, 10, 2);
			u.highLight(btnBuscar);
			u.mouseOverAndClick(btnBuscar);
//			u.unhighLight(btnPromociones);
			
		}catch(Exception e) {
			
		}
	}
	/**
	 * Click en la opcion crear
	 * en el menu promociones
	 */
	public void btnCrearPromocion() {
		
		try {
			u.hardWait(4);
			//u.highLight(btnPromociones);
		//	u.click(dropDPeriodoPromocional);
			//	u.mouseOver(dropDPeriodoPromocional);
			//	u.highLight(btnPromociones);
				u.hardWait(2);
			u.mouseOver(btnPromociones);
			u.hardWait(2); 
			testCase.addScreenShotCurrentStep("");
			u.waitWebElement(btnBuscar, 10, 2);
			u.click(btnCrear);
			
			
		}catch(Exception e) {
			
		}

	}
	
	/**
	 * Click en boton salir ubicado en la parte
	 * superior
	 */
	public void btnSalir() {
		try {
			
			u.waitWebElement(dropDPeriodoPromocional, 50, 4);
			u.highLight(btnSalir);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(1);
			u.click(btnSalir);
			u.unhighLight(btnSalir);
			
		}catch(Exception e) {
			System.out.println("Error---> " + e);

			throw new AssertionError("Error al hacer click en el botón Salir"+e);

		}
	}
	
	/**
	 * Click en el boton salir
	 * ubicado en la parte inferior
	 */
	public void btnSalirInferior() {
		u.scrollToElement(btnSalirInf);
		u.highLight(btnSalirInf);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.click(btnSalirInf);
		u.unhighLight(btnSalir);
	}
	
	/**
	 * Click en boton Busca articulo
	 */
	public void btnBuscaArti() {
		u.scrollToElement(btpBuscaArtitulo);
		u.highLight(btpBuscaArtitulo);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.click(btpBuscaArtitulo);
		u.unhighLight(btpBuscaArtitulo);
	}
	
	/**
	 * Click en boton buscaUDA
	 */
	public void btnBuscaUda() {
		u.scrollToElement(btnBuscaUDA);
		u.highLight(btnBuscaUDA);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.click(btnBuscaUDA);
		u.unhighLight(btnBuscaUDA);
	}
	
	/**
	 * Click en articulo nuevo
	 */
	public void btnArtiNuevo() {
		u.scrollToElement(btnArticuloNuevo);
		u.highLight(btnArticuloNuevo);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.click(btnArticuloNuevo);
		u.unhighLight(btnArticuloNuevo);
	}
	
	/**
	 * Click en el boton cancelar UDA
	 * para cerrar el form de buscaUDA
	 */
	public void btnCancelarUDA() {
		u.waitWebElement(btnCancelaUda, 120, 2);

		u.highLight(btnCancelaUda);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.click(btnCancelaUda);
	}
	
	/**
	 * Click en boton guardar
	 */
	public void btnGuardar() {
		
		u.scrollToElement(btnGuardar);
		u.highLight(btnGuardar);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.click(btnGuardar);
		u.unhighLight(btnGuardar);
	}
	
	/**
	 * Click en boton enviar
	 */
	public void btnEnviar() {

		u.highLight(btnEnviar);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.click(btnEnviar);
		u.unhighLight(btnEnviar);
	
	}
	
	/**
	 * Agrega uno o varios artículos en la pantalla
	 * ''busqueda de articulos''
	 * @author alicia.ceja
	 */
	
	public void btnAgregaArti() {
		u.highLight(btnAgregaArti);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.click(btnAgregaArti);
		u.unhighLight(btnAgregaArti);
	}
	
	/**
	 * Limpia la selección de filtros en la pantalla
	 * ''Busqueda de articulos''
	 * @author alicia.ceja
	 */
	
	public void btnLimpiar() {
		u.highLight(btnLimpiar);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.click(btnLimpiar);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.unhighLight(btnLimpiar);
	}
	
	/**
	 * Limpia la selección de filtros en la pantalla 
	 * ´´Busca UDA´´
	 */
	public void btnLimpiaUDA() {
		u.highLight(btnLimpiaUda);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.click(btnLimpiaUda);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.unhighLight(btnLimpiaUda);
	}
	
	/**
	 * Click en el botón Cancelar de la pantalla
	 * ''Busca UDA´´
	 */
	public void btnCancelaUda() {
		u.highLight(btnLimpiaUda);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.click(btnLimpiaUda);
	}
	
	/**
	 * Hace click en le flecha derecha para agrgegar
	 * todos los artículos en la pantalla 
	 * ´´Busqueda de articulos''
	 * @author alicia.ceja
	 */
	
	public void btnFlechaAgregaTodos() {
		u.highLight(btnFlechaAgregaTodos);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.click(btnFlechaAgregaTodos);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.unhighLight(btnFlechaAgregaTodos);
	}
	
	/**
	 * Hace click en le flecha izquierda para agrgegar
	 * todos los artículos en la pantalla 
	 * ´´Busqueda de articulos''
	 * @author alicia.ceja
	 */
	
	public void btnFlechaQuitaTodos() {
		u.highLight(btnFlechaQuitaTodos);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.click(btnFlechaQuitaTodos);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.unhighLight(btnFlechaQuitaTodos);
	}
	
	/**
	 * Hace click en el botón Activar
	 * en configuración mecánica
	 * @author alicia.ceja
	 */
	public void btnActivaMec() {
		u.scrollToElement(btnActivarMec);
		u.highLight(btnActivarMec);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.click(btnActivarMec);
		
	}
	
	/**
	 * Hace click en el segundo apartado para el llenado
	 * de la información de los artículos que se agregaran 
	 * a la promoción (solo aplica para promoPack)
	 * @author alicia.ceja
	 */
	public void btnSegundaCat() {
		
		u.click(btnSegApartadoArti);
		
	}
	
	/**
	 * Hace click en el segundo apartado para el llenado
	 * de la información de los artículos que se agregaran 
	 * a la promoción (solo aplica para promoPack con 3 artículos)
	 * @author alicia.ceja
	 */
	public void segApartado3arti() {
		u.click(btnSegApartadoArti3);
	}
	
	/**
	 * Hace click en el tercer apartado para el llenado
	 * de la información de los artículos que se agregaran 
	 * a la promoción (solo aplica para promoPack con 3 artículos)
	 * @author alicia.ceja
	 */
	public void sterApartado3arti() {
		u.click(btnTerApartadoArti3);
	}
	
	
	
	/**
	 * ingresa descripcion de mecanica y Pos
	 * @param descripcion
	 * @author alicia.ceja
	 */
	public void descripcionMecanica(String descripcion) {
		u.waitWebElement(tbxDescMecanica, 120, 5);
		u.scrollToElement(tbxDescMecanica);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("");
		System.out.print("Textoo>>>>"+u.getDriver().findElement(tbxDescMecanica).getText());
		u.hardWait(2);
		if(!u.getDriver().findElement(tbxDescMecanica).getText().isEmpty()) {
			System.out.print("Limpia botones");
		
			u.clear(tbxDescPos);
			u.clear(tbxDescMecanica);
			u.sendKeys(tbxDescPos, descripcion);

			
		}

		u.hardWait(2);
		u.highLight(tbxDescMecanica);
		u.sendKeys(tbxDescMecanica, descripcion);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("");
		u.unhighLight(tbxDescMecanica);
		u.hardWait(3);		
	}
	
	/**
	 * Selecciona plaza y cedis para continuar con la promoción
	 * @author alicia.ceja
	 */
	
	public void coberturas() {
		
		u.highLight(pestanaCobertura);
		testCase.addScreenShotCurrentStep("");
		u.mouseOverAndClick(pestanaCobertura);
		u.hardWait(1);
		u.unhighLight(pestanaCobertura);

		
		testCase.addBoldTextEvidenceCurrentStep("Seleccionar el CheckBox 'Seleccionar todas las plazas'.");
		u.highLight(chkTodasPlazas);
		u.click(chkTodasPlazas);
		testCase.addScreenShotCurrentStep("");
		u.unhighLight(chkTodasPlazas);
		
		testCase.addBoldTextEvidenceCurrentStep("Seleccionar el CheckBox principal 'CEDIS'.");
		u.highLight(chkCedis);
		u.click(chkCedis);
		testCase.addScreenShotCurrentStep("");
		u.unhighLight(chkCedis);


		testCase.addBoldTextEvidenceCurrentStep("Hacer clic en la pestaña 'Alta de promoción'");
		u.highLight(pestanaPromocion);
		testCase.addScreenShotCurrentStep("");
		u.mouseOverAndClick(pestanaPromocion);
		u.hardWait(1);
		u.unhighLight(pestanaPromocion);
		u.hardWait(2);

	}
	
	/**
	 * Dirige a la pestaña de alta promocion
	 * en la pantalla de crear promociones
	 */
	public void pantallaAltaPromo() {
		u.click(pestanaPromocion);
	}
	
	/**
	 * Valida que al crear una promocion no
	 * existan Periodos promocionales vigentes
	 */
	public void validaPromocionNoExiste() {
		
		try {
			
			u.hardWait(1);
			testCase.addBoldTextEvidenceCurrentStep("Ingresar a la opción del menú 'Promociones Buscar'");
			u.highLight(btnPromociones);
			u.mouseOver(btnPromociones);
			u.hardWait(2); 
			
			testCase.addScreenShotCurrentStep("");
			u.hardWait(1);
			u.click(btnBuscar);
			
			u.unhighLight(btnPromociones);
			
			u.waitWebElement(btnCrearNuevaPromo, 120, 5);
			
			testCase.addBoldTextEvidenceCurrentStep("Hacer click en 'Crear nueva promoción''");
			u.highLight(btnCrearNuevaPromo);
			testCase.addScreenShotCurrentStep("");
			u.mouseOverAndClick(btnCrearNuevaPromo); 
		 	u.hardWait(2);
 
			testCase.addBoldTextEvidenceCurrentStep("El sistema muestra que no hay promociones disponibles");
		 	u.waitWebElement(alert, 12, 4);
		 	//u.highLight(alert);

		 	u.hardWait(2);
			String mensaje = u.getDriver().findElement(alert).getText(); 
			System.out.println("------->"+mensaje);
			testCase.addScreenShotCurrentStep("");

			if(!mensaje.equals("No hay periodos disponibles para crear promociones. Favor de crear un periodo promocional.")) {
				throw new AssertionError("Hay promociones disponibles");
		 	} 

		 	u.hardWait(2);

		}catch (Exception e) {
			System.out.println("Error---> " + e);

			throw new AssertionError("Error al hacer click en crear propoción "+e);
		}
		
	}
	
	
	/**
	 * Ingresa a crear una promocion nueva
	 * desde la opcion buscar
	 */
	public void pantallaBuscar_CrearPromocion() {

		try {
			
			u.hardWait(1);
			testCase.addBoldTextEvidenceCurrentStep("Ingresar a la opción del menú 'Promociones Buscar'");
			u.highLight(btnPromociones);
			u.mouseOver(btnPromociones);
			u.hardWait(2);
			
			testCase.addScreenShotCurrentStep("");
			u.hardWait(1);
			u.mouseOverAndClick(btnBuscar);
			
			u.unhighLight(btnPromociones);
			
			u.waitWebElement(btnCrearNuevaPromo, 120, 5);
			
			testCase.addBoldTextEvidenceCurrentStep("Hacer click en 'Crear nueva promoción''");
			u.highLight(btnCrearNuevaPromo);
			testCase.addScreenShotCurrentStep("");
			u.mouseOverAndClick(btnCrearNuevaPromo); 
			u.hardWait(2);

			
		}catch (Exception e) {
			System.out.println("Error---> " + e);

			throw new AssertionError("Error al hacer click en crear propoción "+e);
		}
		
		
	}
	
	/**
	 * Selecciona el tipo de mecanica dependiento de la opción recibida
	 * @param opcionMecanica
	 * @author alicia.ceja
	 */
	public void selectTipoMecanica (String opcionMecanica) {
		//testCase.addBoldTextEvidenceCurrentStep("Seleccionar la opción '"+opcionMecanica+"' en Tipo de mecanica");
		u.scrollToElement(selectTipoMecanica);
		u.waitWebElement(selectTipoMecanica, 120, 3);
		selectOption(opcionMecanica, selectTipoMecanica, optionTipoMecanica);
		u.hardWait(2);
		
	}
	
	/**
	 * Selecciona un preciodo promocional dependiendo de la opción recibida
	 * @param opcionPeriodo
	 */
	
	public void periodoPromocional (String opcionPeriodo) {
		testCase.addBoldTextEvidenceCurrentStep("Seleccionar Periodo Promocional Actual");
		selectOption(opcionPeriodo, dropDPeriodoPromocional, optionTipoMecanica);
		u.unhighLight(dropDPeriodoPromocional);
		u.hardWait(2);
	}
	

	

	

	
	/**
	 * Selecciona la opción de pago deseada
	 * y realiza las configuraciones del metodo
	 * de pago
	 * 	
	 * @param op
	 */
	public void medioPago(String op) {
		By desc = By.id("descripcion");
    	By selectBandera = By.id("bandera_ban-button");
    	By selectBanco = By.id("banco-button");
    	By selectTipo = By.id("tipo-button");
		
		try {
			
			 switch (op) {
			 
		        case "TODOS":
					
					testCase.addBoldTextEvidenceCurrentStep("Configurar los campos correspondientes a 'Medios de pago'.");
					u.highLight(medioPagoTodos);
					u.click(medioPagoTodos);
					testCase.addScreenShotCurrentStep("");
					u.hardWait(1);
					u.unhighLight(medioPagoTodos);
					u.hardWait(2);

			
				break;
	        
		        case "TC_DC":
		    		
		        	

		    		testCase.addBoldTextEvidenceCurrentStep("Configurar los campos correspondientes a 'Medios de pago'.");
		    		u.highLight(medioPagoTDC);
		    		u.click(medioPagoTDC);
		    		testCase.addScreenShotCurrentStep("");
		    		u.hardWait(1);
		    		u.unhighLight(medioPagoTDC);
		    		u.hardWait(2);
		    		
		    		testCase.addBoldTextEvidenceCurrentStep("Descripción");
		    		u.highLight(desc);
		    		u.sendKeys(desc, "PAGO TC/TD");
		    		testCase.addScreenShotCurrentStep("");
		    		u.hardWait(1);
		    		u.unhighLight(desc);
		    		u.hardWait(2);

		    		selectOption("CARNET", selectBandera, selectNecesidad_Option);
		    		selectOption("AFIRME", selectBanco, selectNecesidad_Option);
		    		selectOption("CREDITO", selectTipo, selectNecesidad_Option);


		    		
		    		
		            break;
		            
		        case "MONEDERO":
		    		
		    		testCase.addBoldTextEvidenceCurrentStep("Configurar los campos correspondientes a 'Medios de pago'.");
		    		u.highLight(medioPagoMoned);
		    		u.click(medioPagoMoned);
		    		testCase.addScreenShotCurrentStep("");
		    		u.hardWait(1);
		    		u.unhighLight(medioPagoMoned);
		    		u.hardWait(2);
		    		
		    		testCase.addBoldTextEvidenceCurrentStep("Descripción");
		    		u.highLight(desc);
		    		u.sendKeys(desc, "PAGO MONEDERO");
		    		testCase.addScreenShotCurrentStep("");
		    		u.hardWait(1);
		    		u.unhighLight(desc);
		    		u.hardWait(2);

		    		selectOption("CARNET", selectBandera, selectNecesidad_Option);
		    		selectOption("AFIRME", selectBanco, selectNecesidad_Option);
		    		selectOption("CREDITO", selectTipo, selectNecesidad_Option);

		    		
		    		
		            break;

	    default:
	    	
	    }
			
		}catch(Exception e) {
			System.out.println("Error---> " + e);

			throw new AssertionError("Error al ingresar metodos de pago "+e);
		}		
		
	}
	
	/**
	 * Selecciona una opcion de Necesidad
	 */
	public void selectNecesidad(String op) {
		u.scrollToElement(selectNecesidad);
		u.hardWait(2);
		testCase.addBoldTextEvidenceCurrentStep("Seleccionar un valor del campo 'Necesidad'.");
		selectOption(op, selectNecesidad, selectNecesidad_Option);
		u.unhighLight(selectNecesidad);
		u.hardWait(2);

		
	}
	
	/**
	 * Selecciona renta espacios
	 * @param op
	 */
	public void rentaEspacios(String op) {
		
		 switch (op) {
		 
	        case "SI":
				
	        	testCase.addBoldTextEvidenceCurrentStep("Seleccionar la opción 'SI' del campo 'Ingreso renta espacio");
	    		u.hardWait(1);
	    		u.highLight(btnRentaEspacios_SI);
	    		u.click(btnRentaEspacios_SI);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.unhighLight(btnRentaEspacios_SI);
	    		u.hardWait(2);

				
		
			break;
     
	        case "NO":
	    		
	        	testCase.addBoldTextEvidenceCurrentStep("Seleccionar la opción 'No' del campo 'Ingreso renta espacio");
	    		u.hardWait(1);
	    		u.highLight(btnRentaEspacios_No);
	    		u.click(btnRentaEspacios_No);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.unhighLight(btnRentaEspacios_No);
	    		u.hardWait(2);

	    		
	    		
	            break;
	             	
		 	}
	}
	
	/**
	 * Selecciona un periodo de referencia
	 * para la promocion
	 * @param op
	 */
	public void periodoReferencia(String op) {
		
		 switch (op) {
		 
	        case "ANTERIOR":
				
	        	testCase.addBoldTextEvidenceCurrentStep("Seleccionar 'Periodo anterior' en el campo 'Periodo de referencia'.");
	    		
	    		u.hardWait(1);
	    		u.highLight(btnPeriodoRefe_anterior);
	    		u.click(btnPeriodoRefe_anterior);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.unhighLight(btnPeriodoRefe_anterior);
				
		
			break;
  
	        case "MISMO_PERIODO":
	    		
	        	testCase.addBoldTextEvidenceCurrentStep("Seleccionar 'Mismo periodo año anterior' en el campo 'Periodo de referencia'.");
	    		
	    		u.hardWait(1);
	    		u.highLight(btnPeriodoRefe_mismoA);
	    		u.click(btnPeriodoRefe_mismoA);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.unhighLight(btnPeriodoRefe_mismoA);

	    		
	    		
	            break;
	             	
		 	}
		
	}
	
	/**
	 * Selecciona el paquete promocional deseado
	 * @param paquete
	 */
	public void paquetePromocional(String paquete) {
		u.scrollToElement(tbxPaquetePromocional);
		u.hardWait(2);
		testCase.addBoldTextEvidenceCurrentStep("Validar Campo paquete promocinal");
		u.scrollToElement(pestanaCobertura);
		u.clear(tbxPaquetePromocional);
		selectAutocompleteTextbox(paquete,tbxPaquetePromocional, paquete, opcionesPaquetesPromo);
		u.hardWait(2);

	}
	/**
	 * Click en boton Buscar Articulo
	 */
	public void btnBuscaArticulo() {

		u.scrollToElement(btpBuscaArtitulo);
		u.highLight(btpBuscaArtitulo);
		u.hardWait(1);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.click(btpBuscaArtitulo);
		u.hardWait(3);

	}
	
	/**
	 * Click en el boton Agregar UDA
	 */
	public void btnAgregaUDA() {
		
		u.highLight(btnUDAagregar);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.click(btnUDAagregar);
		u.unhighLight(btnUDAagregar);
		
	}
	
	
	/**
	 * Busca y selecciona un articulo nuevo aleatorio 
	 * de acuerdo a los filtros seleccionados
	 * @param superGrupo
	 * @param cat
	 * @param subCat
	 * @param segmento
	 * @param subSegmento
	 */
	public void buscaArtiNuevo(String superGrupo, String cat, String subCat, String segmento, String subSegmento) {
		System.out.println(superGrupo);
		System.out.println(cat);
		System.out.println(subCat);
		System.out.println(segmento);


		testCase.addBoldTextEvidenceCurrentStep("Seleccionar una opción en el filtro 'Supergrupo'.");
		u.highLight(selectSuperGrupo);
		drop(superGrupo, selectSuperGrupo);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.unhighLight(selectSuperGrupo);
		u.hardWait(2);

		testCase.addBoldTextEvidenceCurrentStep("Seleccionar una opción en el filtro 'Categoria'.");
		u.highLight(selectCategorias);
		drop(cat, selectCategorias);
		System.out.println("----------> "+cat);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.unhighLight(selectCategorias);
		u.hardWait(2);

		
		testCase.addBoldTextEvidenceCurrentStep("Seleccionar una opción en el filtro 'SubCategoria'.");
		u.highLight(selectSubCategorias);
		drop(subCat, selectSubCategorias);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.unhighLight(selectSubCategorias);
		u.hardWait(2);

		
		testCase.addBoldTextEvidenceCurrentStep("Seleccionar una opción en el filtro 'Segmento'.");
		u.highLight(selectSegmento);
		drop(segmento, selectSegmento);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.unhighLight(selectSegmento);
		u.hardWait(2);

		

		testCase.addBoldTextEvidenceCurrentStep("Seleccionar una opción en el filtro 'SubSegmento'.");
		u.highLight(selectSubSegmento);
		drop(subSegmento, selectSubSegmento);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.unhighLight(selectSubSegmento);
		u.hardWait(2);

		
		testCase.addBoldTextEvidenceCurrentStep("Del cuadro 'Disponibles' seleccionar el artículo que se desea agregar a la promoción.");
		
		u.highLight(selectArtiDisponibles);
		selectRandomElement( selectArtiDisponibles);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.unhighLight(selectArtiDisponibles);
		
	}
	
	public void buscaUda(String campo, String busqueda, String valor) {
		
		testCase.addBoldTextEvidenceCurrentStep("Agregar 1 o más articulos en seleccionadas");
		u.waitWebElement(btnUDAbuscar, 120, 3);

		testCase.addBoldTextEvidenceCurrentStep("Seleccionar una opción en el filtro 'Campo'.");
		u.highLight(selectUDAcampo);
		drop(campo, selectUDAcampo);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.unhighLight(selectUDAcampo);
		
		testCase.addBoldTextEvidenceCurrentStep("Seleccionar una opción en el filtro 'Busqueda'.");
		u.highLight(selectUDAbusqueda);
		drop(busqueda, selectUDAbusqueda);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.unhighLight(selectUDAbusqueda);
		
		testCase.addBoldTextEvidenceCurrentStep("Capturar en el campo 'Valor'");
		u.highLight(inputUDAvalor);
		u.sendKeys(inputUDAvalor, valor);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.scrollToElement(btnUDAagregar);
		u.unhighLight(inputUDAvalor);
		
		testCase.addBoldTextEvidenceCurrentStep("Dar click en el boton Buscar");
		u.highLight(btnUDAbuscar);
		testCase.addScreenShotCurrentStep("");
		u.click(btnUDAbuscar);
		u.unhighLight(btnUDAbuscar);

		testCase.addBoldTextEvidenceCurrentStep("Del cuadro 'Disponibles' seleccionar el artículo que se desea agregar a la promoción.");
		u.highLight(selectUDAartiDis);
		selectRandomElement(selectUDAartiDis);
		u.hardWait(1);
		testCase.addScreenShotCurrentStep("");
		u.unhighLight(selectUDAartiDis);
		
	}
	


	
		/**
	 * Llena la información necesaria de un articulo agregado desde 'UDA' 
	 * o ´Buscar articulo'
	 * @param articulo
	 */
	public void datosArticulo(String articulo, String op, String datosGen[], String datosSinO[], String aportaProv[], String datosOferta, String cadenaS[], String coment) {
		
		By chckArti = By.cssSelector("#chkItem_"+articulo);
		By inputArtNuevo_CB = By.cssSelector("#codigoBarras_"+articulo);
		By inputArtNuevo_Desc = By.cssSelector("#codigoBarras_"+articulo);
		By inputArtNuevo_Prov = By.cssSelector("#proveedor_"+articulo);
		
		//DATOS DEL PRODUCTO SIN OFERTA
		By inputArtNuevo_CostoB = By.cssSelector("#costobruto_"+articulo);
		By inputArtNuevo_DescuLinea = By.cssSelector("#descLinea_"+articulo);
		By inputArtNuevo_SinIVA = By.cssSelector("#precioSinIva_"+articulo);
		By inputArtNuevo_ConIVA = By.cssSelector("#precioConIva_"+articulo);
		
		//APORTACION DEL PROVEEDOR A LA PROMOCION
		By inputArtNuevo_Descuento = By.cssSelector("#desc_"+articulo);
		By ArtNuevo_FechaIni = By.cssSelector("#fechaInicio_"+articulo);
		By ArtNuevo_FechaFin = By.cssSelector("#fechaFin_"+articulo);
		By inputArtNuevo_BoniVacio = By.cssSelector("#bonificacionComent_"+articulo);
		By SelectArtNuevo_Bonificacion = By.cssSelector("#bonificacionList_"+articulo);
		By selectArtNuevo_SelecOpBonificacion = By.xpath("//select[(@id='bonificacionList_"+articulo+"')]//option[text( )='"+aportaProv[4]+"']");
		System.out.println("----------> "+selectArtNuevo_SelecOpBonificacion);

		//DATOS DEL PRODUCTO CON OFERTA
		By inputArtNuevo_Precio = By.cssSelector("#dpPrecio_"+articulo);
		
		//CADENA DE SUMINISTRO #bonificacionList_1
		By inputArtNuevo_CargoTienda = By.cssSelector("#cargoTiendaPiezas_"+articulo);
		By ArtNuevo_CantSugerida = By.cssSelector("#ctdSugeridaVta_"+articulo);
		By SelectArtNuevo_MotivoOfer = By.cssSelector("#motivoOfertaList_"+articulo);
		By selectArtNuevo_opMotivoOfer = By.xpath("//select[(@id='motivoOfertaList_"+articulo+"')]//option[text( )='"+cadenaS[2]+"']");
		System.out.println("----------> "+selectArtNuevo_opMotivoOfer);
		By inputArtNuevo_Comen = By.cssSelector("#comentariosCategoria_"+articulo);

		try {
			testCase.addBoldTextEvidenceCurrentStep("LLenar información del artículo");
			
			switch (op) {
			 
	        case "NUEVO":
				//Datos generales
	        	u.highLight(inputArtNuevo_CB);
	        	u.sendKeys(inputArtNuevo_CB, datosGen[0]);
	        	u.unhighLight(inputArtNuevo_CB);
	        	u.hardWait(1);
				
	        	u.highLight(inputArtNuevo_Desc);
	        	u.sendKeys(inputArtNuevo_Desc, datosGen[1]);
	        	u.unhighLight(inputArtNuevo_Desc);
	        	u.hardWait(1);
				testCase.addScreenShotCurrentStep("");
	        	
	        	
			break;
			
	        default:
	        	
	        	u.highLight(inputArtNuevo_Prov);
	        	u.sendKeys(inputArtNuevo_Prov, datosGen[2]);
	        	u.scrollToElement(inputArtNuevo_Prov);
	        	u.unhighLight(inputArtNuevo_Prov);
	        	u.hardWait(1);
	        	
	        	//DATOS DEL ARTICULO SIN OFERTA
	        	u.highLight(inputArtNuevo_CostoB);
	        	u.sendKeys(inputArtNuevo_CostoB, datosSinO[0]);
	        	u.scrollToElement(inputArtNuevo_CostoB);
	        	u.unhighLight(inputArtNuevo_CostoB);
	        	u.hardWait(1);
	        	
	         	u.highLight(inputArtNuevo_DescuLinea);
	        	u.sendKeys(inputArtNuevo_DescuLinea, datosSinO[1]);
	        	u.scrollToElement(inputArtNuevo_DescuLinea);
	        	u.unhighLight(inputArtNuevo_DescuLinea);
	        	u.hardWait(1);
	        	
	        	u.highLight(inputArtNuevo_SinIVA);
	        	u.sendKeys(inputArtNuevo_SinIVA, datosSinO[2]);
	        	u.scrollToElement(inputArtNuevo_SinIVA);
	        	u.unhighLight(inputArtNuevo_SinIVA);
	        	u.hardWait(1);
	        	
	         	u.highLight(inputArtNuevo_ConIVA);
	        	u.sendKeys(inputArtNuevo_ConIVA, datosSinO[3]);
	        	u.scrollToElement(inputArtNuevo_ConIVA);
	        	u.unhighLight(inputArtNuevo_ConIVA);
	        	u.hardWait(1);
				testCase.addScreenShotCurrentStep("");
	        	u.hardWait(1);

	        	//Aportación del proveedor
	        	u.scrollToElement(SelectArtNuevo_Bonificacion);
	        	
	        	u.highLight(inputArtNuevo_Descuento);
	        	u.sendKeys(inputArtNuevo_Descuento, aportaProv[0]);
	        	u.scrollToElement(inputArtNuevo_Descuento);
	        	u.unhighLight(inputArtNuevo_Descuento);
	        	u.hardWait(1);
	        	
	         	u.highLight(ArtNuevo_FechaIni);
	         	sendDate(articulo,"10","9","Dec","2022");
	        	u.scrollToElement(ArtNuevo_FechaIni);
	        	u.unhighLight(ArtNuevo_FechaIni);
	        	u.hardWait(1);
				testCase.addScreenShotCurrentStep("");

	         	u.highLight(ArtNuevo_FechaFin);
	         	sendDate(articulo,"11","4","Jan","2023");
	         	u.unhighLight(ArtNuevo_FechaFin);
	        	u.hardWait(1);
	    		
	        	u.highLight(inputArtNuevo_BoniVacio);
	        	u.sendKeys(inputArtNuevo_BoniVacio, aportaProv[3]);
	        	u.scrollToElement(inputArtNuevo_BoniVacio);
	        	u.unhighLight(inputArtNuevo_BoniVacio);
	        	u.hardWait(1);
	        	
	        	
	        	u.highLight(SelectArtNuevo_Bonificacion);
	        	u.click(SelectArtNuevo_Bonificacion);
	        	u.hardWait(1);
	        	u.click(selectArtNuevo_SelecOpBonificacion);
	        	u.unhighLight(SelectArtNuevo_Bonificacion);
	        	u.hardWait(1);
	        	
	        	By btnEditar = By.cssSelector("#btnCobertura_"+articulo);
	        	u.scrollToElement(btnEditar);
	        	
	    		//DATOS DEL PRODUCTO CON OFERTA
	        	u.highLight(inputArtNuevo_Precio);
	        	u.sendKeys(inputArtNuevo_Precio, aportaProv[3]);
	        	u.scrollToElement(inputArtNuevo_Precio);
	        	u.unhighLight(inputArtNuevo_Precio);
	        	u.hardWait(1);
	        	
	    		//CADENA DE SUMINISTRO
	    		u.highLight(inputArtNuevo_CargoTienda);
	        	u.sendKeys(inputArtNuevo_CargoTienda, cadenaS[0]);
	        	u.scrollToElement(inputArtNuevo_CargoTienda);
	        	u.unhighLight(inputArtNuevo_CargoTienda);
	        	u.hardWait(1);
	        	
	        	u.highLight(ArtNuevo_CantSugerida);
	        	u.sendKeys(ArtNuevo_CantSugerida, cadenaS[1]);
	        	u.scrollToElement(ArtNuevo_CantSugerida);
	        	u.unhighLight(ArtNuevo_CantSugerida);
	        	u.hardWait(1);

	        	u.highLight(SelectArtNuevo_MotivoOfer);
	        	u.click(SelectArtNuevo_MotivoOfer);
	        	u.hardWait(1);
	        	u.click(selectArtNuevo_opMotivoOfer);
	        	u.unhighLight(SelectArtNuevo_MotivoOfer);
	        	u.hardWait(1);
	        	
				testCase.addScreenShotCurrentStep("");

	        	
	    		
		 }

		}catch(Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Falló al llenar información del artículo");
		}
		

	}
	
	/**
	 * Selecciona fechas en elementos calendar
	 * @param numArti
	 * @param td
	 * @param day
	 * @param month
	 * @param year
	 */
	public void sendDate(String numArti,String td,String day, String month, String year) {
		
		By calendar = By.cssSelector("#trArticulo_"+numArti+" > td:nth-child("+td+") > img:nth-child(2)");
		By tableCalendar = By.cssSelector(".ui-datepicker-calendar");
		By selectMonth = By.cssSelector(".ui-datepicker-month");
		By selectYear = By.cssSelector(".ui-datepicker-year");
		
		
		u.mouseOverAndClick(calendar);
		u.hardWait(2);
		drop(month, selectMonth);
		u.hardWait(2);
		drop(year, selectYear);

		
		WebElement dateWidget = u.getDriver().findElement(tableCalendar);
		List<WebElement> columns=dateWidget.findElements(By.tagName("td"));

		for (WebElement cell: columns){
		   if (cell.getText().equals(day)){
		      cell.findElement(By.linkText(day)).click();
		      break;
		 }
		   
		}
	}
	

	/**
	 * Selecciona la opcion enviada a un elemento textBox
	 * que tiene la funcionalidad de autocompletar
	 * @param text
	 * @param tbxAutoComplete
	 * @param opcion
	 * @param listElements
	 */
	public void selectAutocompleteTextbox(String text, By tbxAutoComplete, String opcion, By listElements) {
		 
		
		try {
			
			WebElement autoOptions = u.getDriver().findElement(tbxAutoComplete);
			   u.highLight(tbxAutoComplete);
			   autoOptions.sendKeys(text);
			   u.hardWait(2);
				testCase.addScreenShotCurrentStep("");

			    List<WebElement> optionsToSelect  = u.getDriver().findElements(listElements);

			    System.out.println("-------------->"+optionsToSelect.size());
			    for(WebElement option : optionsToSelect){
			        System.out.println("opcion = "+option);
			        if(option.getText().equals(opcion)) {
			            System.out.println("Trying to select: "+opcion);
			            option.click();
						testCase.addScreenShotCurrentStep("");
						u.hardWait(1);
						u.unhighLight(tbxAutoComplete);
			            break;
			        }
			    }
			
		}catch (Exception e ) {
			
			System.out.println("Error---> " + e);

			throw new AssertionError("Error al seleccionar una opción: "+ opcion+"del cmb "+e);
		}
		
	}
	
	/**
	 * Selecciona una opcion de un elemento tipo select
	 * 
	 * @param op
	 * @param selectElement
	 * @param selectOption
	 */
	public void selectOption(String op, By selectElement, By selectOption) {
		try {
			
			u.highLight(selectElement);
			u.click(selectElement);
			
		    List<WebElement> optionsToSelect  = u.getDriver().findElements(selectOption);

		    System.out.println("-------------->"+optionsToSelect.size());
		    for(WebElement option : optionsToSelect){
		        System.out.println("opcion = "+option);
		        if(option.getText().equals(op)) {
		            System.out.println("Trying to select: "+op);
		            option.click();
					testCase.addScreenShotCurrentStep("");
					u.hardWait(1);
					u.unhighLight(selectElement);
		            break;
		        }
		    }

			
		}catch(Exception e) {
			System.out.println("Error---> " + e);

			throw new AssertionError("Error al seleccionar una opción: "+ op+"del cmb "+e);
		}		
	}
	
	/**
	 * Selecciona una opcion de la lista de un elemento
	 * tipo dropdown
	 * @param op
	 * @param selectElement
	 */
	public void drop(String op, By selectElement) {
		
		Select dropdown=  new Select (u.getDriver().findElement((selectElement)));
		u.hardWait(2);
		System.out.println("Elemento selec <<<<"+selectElement);
	//	u.click(selectElement);
		dropdown.selectByVisibleText(op);
		u.hardWait(2);
		//By selectElementClick = By.xpath("//option[text()=\""+op+"\"]");

	}

	/**
	 * Selecciona una opcion random de la lista de
	 * un elemento tipo select
	 * @param selectElement
	 */
	public void selectRandomElement(By selectElement) {
		
		 List<WebElement> options = u.getDriver().findElements(selectElement);
		 int size = options.size();

		 options.get(new Random().nextInt(size)).click();
		
	}


/**
 * Selecciona el checkBox de un articulo y copia el renglon
 * para pegarlo en uno nuevo	
 * @param artCoppy
 * @param artPaste
 */
	public void coppyArticulo(String artCoppy, String artPaste) {
		
		By chkarticuloCoppy = By.cssSelector("#chkItem_"+artCoppy);
		By chkarticuloPaste = By.cssSelector("#chkItem_"+artPaste);
		By btnCopiarRegion = By.cssSelector("#btnCopiar");
		By btnPegar = By.cssSelector("#btnPegar");
		
	
		u.highLight(chkarticuloCoppy);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.click(chkarticuloCoppy);
		u.unhighLight(chkarticuloCoppy);
		
		u.hardWait(1);
		u.highLight(btnCopiarRegion);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.click(btnCopiarRegion);
		u.unhighLight(btnCopiarRegion);



		testCase.addBoldTextEvidenceCurrentStep("Seleccionar el artículo recién agregado"
				+ " que no cuenta con información y hacer clic en el botón 'Pegar'.");
	
		u.highLight(chkarticuloPaste);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.click(chkarticuloPaste);
		u.unhighLight(chkarticuloPaste);
		
		u.hardWait(2);
		u.highLight(btnPegar);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.click(btnPegar);
		u.unhighLight(btnPegar);
		

		

	}
	
	/**
	 * Selecciona un articulo de la lista 
	 * y lo elimina 
	 * 
	 * @param arti
	 */
	public void elimiaArti(String arti) {
		
		By chkarticulo = By.cssSelector("#chkItem_"+arti);
		By btnEliminar = By.cssSelector("#btnEliminar");
		
		u.scrollToElement(chkarticulo);
		
		u.highLight(chkarticulo);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.click(chkarticulo);
		u.unhighLight(chkarticulo);
		
		u.hardWait(1);
		u.highLight(btnEliminar);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.click(btnEliminar);
		u.unhighLight(btnEliminar);
		
		u.waitWebElement(btnAceptar, 120, 2);
		
		u.highLight(btnAceptar);
		testCase.addScreenShotCurrentStep("");
		u.click(btnAceptar);

		
	}
	
	/**
	 * Obtiene el folio de la promoción generada y lo
	 * regresa para realizar busquedas
	 * @return
	 * @author alicia.ceja
	 */
	
	public String getFolio() {
		String folio="";
		
		By mensaje = By.cssSelector("#divMensaje > p:nth-child(1)");
		
		u.waitWebElement(mensaje, 120, 2);
		u.scrollToElement(mensaje);


			
		String texto = u.getDriver().findElement(mensaje).getText();
		
		String []part1 = texto.split("FOLIO");
		
		String cadena1=part1[1];
		
		String[]part2 = cadena1.split(" ");
			folio = part2[1];
			System.out.println("Mensaje--> "+folio);
			
			u.highLight(btnAceptar);
			u.click(btnAceptar);
			
			return folio;
		
	}
	
	/**
	 * Busca una promocion que se dio de alta 
	 * para exportar el reporte de exel
	 * @param folio
	 */
	public void buscaPromo(String folio) {
		
		By generaReporte = By.cssSelector("#btnReportePrecios");
		By chkFolio = By.cssSelector("#jqg_gridBuscar_"+folio);
		u.hardWait(5);
		u.scrollToElement(btnPromociones);
		testCase.addBoldTextEvidenceCurrentStep("Ingresar a la opción del menú 'Promociones Buscar'");
		u.highLight(btnPromociones);
		u.mouseOver(btnPromociones);
		u.hardWait(2);
		u.unhighLight(btnPromociones);
		
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.mouseOverAndClick(btnBuscar);
		
		u.click(btnAceptar);
		
		
		u.waitWebElement(btnCrearNuevaPromo, 120, 3);
		
		testCase.addBoldTextEvidenceCurrentStep("Ingresa folio");
		u.highLight(inputfolio);
		u.sendKeys(inputfolio, folio);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.unhighLight(inputfolio);
		
		testCase.addBoldTextEvidenceCurrentStep("Selecciona año");
		u.highLight(selectA);
		drop("2022", selectA);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.unhighLight(selectA);
		
		u.hardWait(2);
		
		testCase.addBoldTextEvidenceCurrentStep("Selecciona Periodo");
		u.highLight(selectPeriodo_buscar);
		drop("PruebaAutomatizacion01", selectPeriodo_buscar);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.unhighLight(selectPeriodo_buscar);
		
		testCase.addBoldTextEvidenceCurrentStep("Click en buscar");
		u.highLight(btnBuscaFolio);
		u.click(btnBuscaFolio);
		testCase.addScreenShotCurrentStep("");
	
		testCase.addBoldTextEvidenceCurrentStep("Selecciona la promoción");
		u.highLight(chkFolio);
		u.click(chkFolio);
		testCase.addScreenShotCurrentStep("");

		testCase.addBoldTextEvidenceCurrentStep("Click en generar reporte ");
		u.scrollToElement(generaReporte);
		u.hardWait(2);
		u.highLight(generaReporte);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.click(generaReporte);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("");
		
	}
	
	/**
	 * Busca una promoción promoPack sin folio
	 * ya que la interfaz no lo muestra al guardar la promoción
	 * @author alicia.ceja
	 */
	public void buscaPromoPack() {
		
		By generaReporte = By.cssSelector("#btnReportePrecios");
		By selectPromo = By.id("tipoMecanicaId");
		By chkFolio = By.xpath("/html/body/div[2]/table[2]/tbody/tr/td[1]/table/tbody/tr/td/div/table[1]/tbody/tr/td/table/tbody/tr/td/div/div[3]/div[3]/div/table/tbody/tr[2]/td[2]");
		u.hardWait(1);
		u.scrollToElement(btnPromociones);
		testCase.addBoldTextEvidenceCurrentStep("Ingresar a la opción del menú 'Promociones Buscar'");
		u.highLight(btnPromociones);
		u.mouseOver(btnPromociones);
		u.hardWait(2);
		u.unhighLight(btnPromociones);
		
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.mouseOverAndClick(btnBuscar);
		
		u.click(btnAceptar);
		
		
		u.waitWebElement(btnCrearNuevaPromo, 120, 3);
		

		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.unhighLight(inputfolio);
		
		testCase.addBoldTextEvidenceCurrentStep("Selecciona año");
		u.highLight(selectA);
		drop("2022", selectA);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.unhighLight(selectA);
		
		u.hardWait(2);
		
		testCase.addBoldTextEvidenceCurrentStep("Selecciona Periodo");
		u.highLight(selectPeriodo_buscar);
		drop("PruebaAutomatizacion01", selectPeriodo_buscar);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.unhighLight(selectPeriodo_buscar);
		
		testCase.addBoldTextEvidenceCurrentStep("Selecciona el tipo de promoción");
		u.highLight(selectPromo);
		drop("promopack", selectPromo);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.unhighLight(selectPromo);

		
		testCase.addBoldTextEvidenceCurrentStep("Click en buscar");
		u.highLight(btnBuscaFolio);
		u.click(btnBuscaFolio);
		testCase.addScreenShotCurrentStep("");
	
		testCase.addBoldTextEvidenceCurrentStep("Selecciona la promoción");
		u.highLight(chkFolio);
		u.click(chkFolio);
		testCase.addScreenShotCurrentStep("");

		testCase.addBoldTextEvidenceCurrentStep("Click en generar reporte ");
		u.scrollToElement(generaReporte);
		u.hardWait(2);
		u.highLight(generaReporte);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.click(generaReporte);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("");
		
	}
	
	/**
	 * Configura la mecanica de las promociones /Compra multiple/
	 * y selecciona si es por PRECIO O UNIDAD 
	 * @param p1
	 * @param p2
	 * @param mecanica
	 */
	public void configMecanica(String p1, String p2, String mecanica) {
		u.highLight(tbxP1);
		u.sendKeys(tbxP1, p1);
		u.scrollToElement(tbxP1);
		
		u.highLight(tbxP2);
		u.sendKeys(tbxP2, p2);
		u.scrollToElement(tbxP2);
		
		if (mecanica.equals("UNIDAD")) {
			u.click(btnUnidad);
		}else {
			u.click(btnPrecio);
		}

		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.unhighLight(tbxP1);
		u.unhighLight(tbxP2);

		
	}
	
	/**
	 * Metodo que configura la liga de convenio
	 * selecciona /sin convenio/ para poder enviar 
	 * una promoción
	 */
	public void ligaConvenio() {
		
		u.scrollToElement(btnLigaConvenio);
		
		u.click(btnLigaConvenio);
		u.waitWebElement(btnObtenerFolio, 15, 2);
		u.click(pestanaMotivoNoConve);
		u.waitWebElement(rdbutonNoConvenio, 12, 2);
		u.click(rdbutonNoConvenio);
		u.hardWait(2);
		u.click(btnContinuar);
	}
	
	/**
	 * Configura la mecanica para las promociones tipo
	 * promopack
	 * @param nCompo
	 * @param com1
	 * @param com2
	 * @param userCatCom1
	 * @param userCatCom2
	 * @param mec1
	 * @param mec2
	 */
	public void configMecanica(String nCompo, String com1, String com2, String userCatCom1, String userCatCom2, String mec1, String mec2) {
		
		u.scrollToElement(numComponentes);
		u.hardWait(2);
		u.highLight(numComponentes);
		selectOption(nCompo, numComponentes, selectOptionComp);
		u.hardWait(2);
		

		
		testCase.addBoldTextEvidenceCurrentStep("Selecciona la Categoria de componente 1");
		selectOption(com1, componente1, selectOptionComp1);
		u.hardWait(2);
		selectOption(com1, componente1, selectOptionComp1);
		
		
		testCase.addBoldTextEvidenceCurrentStep("Selecciona la Categoria de componente 2");
		selectOption(com2, componente2, selectOptionComp2);
		u.hardWait(2);
		
		testCase.addBoldTextEvidenceCurrentStep("Selecciona el Usuario categoria del componente 2");
		selectOption(userCatCom2, userCatComp2, selectOptionUserCom2);
		u.hardWait(2);
		
		testCase.addBoldTextEvidenceCurrentStep("Selecciona el Usuario categoria del componente 1");
		selectOption(userCatCom1, userCatComp1, selectOptionUserCom1);
		u.hardWait(2);
		
		if(nCompo.equals("3")) {
			

			testCase.addBoldTextEvidenceCurrentStep("Selecciona la Categoria de componente 3");
			selectOption("Fast Food", selecCatComp3, selectOpCat3);
			u.hardWait(2);
			testCase.addBoldTextEvidenceCurrentStep("Selecciona la Categoria de componente 3");
			selectOption("Fast Food", selecCatComp3, selectOpCat3);
			testCase.addBoldTextEvidenceCurrentStep("Selecciona el Usuario categoria del componente 3");
			selectOption(userCatCom2, selecUserCat3, selectOptionUserCom3);			
		}
		
		testCase.addBoldTextEvidenceCurrentStep("Ingresa Mecanica");
		u.sendKeys(tbxMecanica1, mec1);
		u.hardWait(2);
		
		u.highLight(tbxMecanica2);
		u.sendKeys(tbxMecanica2, mec2);
		
		if(nCompo.equals("3")) {
			u.sendKeys(tbxMecanica3, mec1);
		}
		
		u.scrollToElement(tbxMecanica2);
		testCase.addScreenShotCurrentStep("");
		u.unhighLight(tbxMecanica2);
		u.hardWait(2);

		testCase.addBoldTextEvidenceCurrentStep("Hacer click en el botón Activar");

		u.highLight(btnActivarMec);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.click(btnActivarMec);
		u.hardWait(2);
		u.unhighLight(userCatComp1);

	}

	
	/**
	 * Cambia el foco a la aventana indicada donde 0 = Pantalla inicial
	 * y 1 = Pantalla nueva
	 * @param pantalla
	 * @author alicia.ceja
	 */
	
	 public  void cambiarVentana(int pantalla) {
	        try {
	            ArrayList<String> tabs = new ArrayList<String> (u.getDriver().getWindowHandles());
	            u.getDriver().switchTo().window(tabs.get(pantalla));
	        } catch (Exception e) {
	            
	        }
	    }
	
}
