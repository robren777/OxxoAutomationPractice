package WEB.cartaPorte.cartaPorte3CHG0041763;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.sikuli.hotkey.Keys;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cartaPorte.objectModelPortalCP.AdministracionUnidades;
import WEB.cartaPorte.objectModelPortalCP.CartaPorteManual;
import WEB.cartaPorte.objectModelPortalCP.LoginCP;
import WEB.cartaPorte.objectModelPortalCP.MenuPortal;
import WEB.cartaPorte.objectModelPortalCP.PortalCartaPorte;
import WEB.cartaPorte.objectModelPortalCP.UnidadesDetalleEquipo;
import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.MetodoGeneral;
import WEB.util.MetodosSikuli;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_012_EliminacionDeElementosIstmo_CartaPorteManual extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_012_EliminacionDeElementosIstmo_CartaPorteManual_test(HashMap<String, String> data)
			throws FindFailed, SQLException {
		/*
		 * El objetivo es validar que los elementos Istmo no se encuentren visibles en
		 * el formulario para generar carta porte Carta Porte CEDIS Carta Porte Manual
		 * Carta Porte OC
		 * 
		 * portalUrl = Url para acceder al portal WEB carta porte userName = usuario de
		 * acceso password = contraseña encriptada para acceder a la aplicación
		 * tipoCartaPorte = cedis, manual u oc origen = CEDIS o PT origen destino =
		 * CEDIS o PT destino check = Opcion de sección de checkbox: // cedis / plaza/
		 * terceros tipoArticulo = especial o normal nombreChofer= nombre del chofer,
		 * relacionado con el CEDIS o PT origen capturado numeroEconomico =
		 * numeroEconomico de transporte relacionado con el nombre del Chofer correo =
		 * correo adicional para enviar los documentos campo= descripcion que se muestra
		 * en la de los testcase pesoBruto = vacio para enviar y modificar el valor en
		 * los casos que sea 0, o vacio para solo para encontrar el elemento.
		 * registroIstmo= SI para seleccionar la casilla No para no seleccionar
		 * poloOrigen = Origen que pertenezca a la zona Istmo poloDestino = Destino que
		 * pertenezca a la zona Istmo logisticaInversa= Si para seleccionar y no para no
		 * seleccionar pac= pac con el que se realizará la prueba registro= Número de
		 * registros a agregar
		 */
		String portalurl = data.get("server");
		String userName = data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String pac = data.get("pac");
		String campo = data.get("campo");
		String origen = data.get("origen");
		String tipoCartaPorte = data.get("tipoCartaPorte");
		String check = data.get("check");
		String destino = data.get("destino");
		String tipoArticulo = data.get("tipoArticulo");
		String registro = data.get("registro");
		String nombreChofer = data.get("nombreChofer");
		String numeroEconomico = data.get("numeroEconomico");
		String registroIstmo = data.get("registroIstmo");
		String poloOrigen = data.get("poloOrigen");
		String poloDestino = data.get("poloDestino");
		String logisticaInversa = data.get("logisticaInversa");
		String correo = data.get("correo");
		String rutaGuardarTira = data.get("rutaGuardarTira");
		testCase.setTest_Description("");
		testCase.setTest_Description(campo + " . PAC de prueba: " + pac);
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		LoginCP login = new LoginCP(u, testCase, portalurl);
		MenuPortal portalmenu = new MenuPortal(u, testCase);
		PortalCartaPorte portalCP = new PortalCartaPorte(u, testCase);
		AdministracionUnidades admonUnidades = new AdministracionUnidades(u, testCase);
		UnidadesDetalleEquipo unidadesDetalle = new UnidadesDetalleEquipo(u, testCase);
		CartaPorteManual manual = new CartaPorteManual(u, testCase, portalCP);
		String mensajeSeleccion = "No fue posible seleccionar elemento: ";
		String mensajeEncontrar = "No fue posible encontrar elemento: ";
		String mensajeCaptura = "No fue posible capturar datos ";
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("WEB\\"+"CARTAPORTEW");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		// Preparar datos
		check = check.toUpperCase().charAt(0) + check.substring(1, check.length()).toLowerCase();
		tipoArticulo = tipoArticulo.toUpperCase().charAt(0)
				+ tipoArticulo.substring(1, tipoArticulo.length()).toLowerCase();
		Integer registroTotal = Integer.parseInt(registro);
		MetodoGeneral metodoGeneral = new MetodoGeneral();
		Date fechaInicial = new Date();

		// PASO 1:
		addStep("Ingresar a la liga de acceso: https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf");
		u.get(login.getUrl());
		login.proceedToPortal();
		boolean cargarPagina = login.encontrarMensajeInicio();
		testCase.addScreenShotCurrentStep("CargarURL");
		assertTrue(cargarPagina,"El sitio no fue cargado satisfactoriamente");
		// PASO 2:
		addStep("Capturar credenciales de acceso para iniciar sesion");
		login.cicloLogin2(userName, password);
		String mensajePortal = portalmenu.accesoMenuPortal(userName, password);
		assertTrue(mensajePortal.compareTo("Portal de Aplicaciones") == 0,
				"No se encontro mensaje: Portal de Aplicaciones");

		// PASO 3:
		addStep("Seleccionar: Portal Carta Porte");
		portalmenu.seleccionarOpcionCartaPorte();
		u.hardWait(5);
		boolean encontrarMenuCP = portalCP.encontrarMenuOpcionesCartaPorte();
		assertTrue(encontrarMenuCP, "No se encontro Menu de Carta Porte");

		// PASO 4:
		addStep("Seleccionar Menu de Carta Porte");
		u.hardWait(5);
		String encontrarOpcionMenuCP = portalCP.encontrarOpcionesCartaPorte(tipoCartaPorte);
		assertEquals(encontrarOpcionMenuCP, "Carta Porte Manual", "No se encontro la opcion: Carta Porte Manual");

		// PASO 5:
		addStep("Seleccionar opcion: Carta Porte manual");
		String seleccionarCPManual = portalCP.seleccionarOpcionCartaPorte(tipoCartaPorte);
		assertEquals(seleccionarCPManual, "Carta Porte Manual", "No se encontro el texto: Carta Porte Manual");// PASO
																												// 29
		addStep("Capturar campo: Origen");
		String campoActual = "origen";
		boolean encontrarCasilla = manual.capturarCasilla(origen, campoActual, true, true);
		assertTrue(encontrarCasilla,mensajeCaptura + campoActual);
		campoActual = "check" + check;
		encontrarCasilla = manual.encontrarElemento(campoActual);
		assertTrue(encontrarCasilla,mensajeEncontrar + campoActual);

		// PASO 30: Seleccionar checkbox (CEDIS, plaza, Terceros)
		addStep("Seleccionar checkbox");
		encontrarCasilla = manual.seleccionarElemento(campoActual, true);
		assertTrue(encontrarCasilla, mensajeSeleccion + campoActual);
		campoActual = "destino";
		encontrarCasilla = manual.encontrarElemento(campoActual);
		manual.encontrarElemento(campoActual);
		assertTrue(encontrarCasilla, mensajeEncontrar + campoActual);

		// PASO 31:
		addStep("Capturar campo: Destino");
		encontrarCasilla = manual.capturarCasilla(destino, campoActual, true, true);
		assertTrue(encontrarCasilla, mensajeCaptura + campoActual);
		campoActual = "Mercancias";
		encontrarCasilla = manual.encontrarElemento(campoActual);
		assertTrue(encontrarCasilla, mensajeEncontrar + "texto " + campoActual);

		String valorFiltro = "";
		int contador = 1;
		int filas = 1;
		boolean captura = true;
		do {
			if (contador > 1) {
				portalCP.bajarScroll2("50");
				campoActual = "botonAgregar";
				encontrarCasilla = manual.seleccionarElemento(campoActual, false);
				if (contador > 10) {
					String valor = String.valueOf(contador);
					valor = valor.substring(valor.length() - 1);
					if (valor.compareTo("1") == 0) {
						portalCP.bajarScroll2("200");
						campoActual = "paginadoAvanza";
						encontrarCasilla = manual.seleccionarElemento(campoActual, false);
						filas = 1;
					}
				}
			}
			// PASO 32:
			if (captura) {
				addStep("Seleccionar elemento visual para la busqueda por SKU");
			}
			assertTrue(encontrarCasilla, mensajeSeleccion + campoActual);
			encontrarCasilla = manual.rellenaArticulos(filas, tipoArticulo, captura);
			assertTrue(encontrarCasilla, mensajeSeleccion + campoActual);
			campoActual = "buscarArticulos";
			encontrarCasilla = manual.encontrarElemento(campoActual);
			assertTrue(encontrarCasilla, mensajeEncontrar + campoActual);
			// PASO 33 Seleccionar Tipo de Articulo(normal o especial)
			if (captura) {
				addStep("Seleccionar Tipo de Articulo");
			}
			campoActual = "tipoArticulo";
			encontrarCasilla = manual.seleccionarElemento(campoActual + tipoArticulo, captura);
			assertTrue(encontrarCasilla, mensajeSeleccion + campoActual);
			campoActual = "filtrar";
			encontrarCasilla = manual.encontrarElemento(campoActual);
			assertTrue(encontrarCasilla, mensajeEncontrar + campoActual);
			// PASO 34
			if (captura) {
				addStep("Capturar informacion de casilla: Filtrar");
			}
			int tipoArticuloOp = manual.determinaTipoArticulo(tipoArticulo);
			valorFiltro = manual.obtenerValorArticulos(tipoArticuloOp);
			encontrarCasilla = manual.capturarCasilla(valorFiltro, campoActual, false, captura);
			assertTrue(encontrarCasilla, mensajeCaptura + campoActual);
			campoActual = "botonBuscarArticulo";
			encontrarCasilla = manual.seleccionarElemento(campoActual, false);
			assertTrue(encontrarCasilla, mensajeSeleccion + campoActual);
			if (captura) {
				addStep("Seleccionar un articulo");
			}
			u.hardWait(5);
			encontrarCasilla = manual.seleccionaArticulos(captura);
			assertTrue(encontrarCasilla,"Error al seleccionar articulo");
			encontrarCasilla = manual.capturaDetalleArticulo(filas);
			assertTrue(encontrarCasilla,"Error capturar detalles de artículo");

			if (contador == registroTotal) {
				getTestCase().addTextEvidenceCurrentStep("Total de mercancia seleccionada");
				testCase.addScreenShotCurrentStep("MercanciaTotal");
			}
			captura = false;
			contador++;
			filas++;

		} while (encontrarCasilla && contador <= registroTotal);

		// PASO 37
		addStep("Capturar nombre de transportista");
		portalCP.bajarScroll();
		campoActual = "nombreChofer";
		encontrarCasilla = manual.capturarCasilla(nombreChofer, campoActual, true, true);
		assertTrue(encontrarCasilla, mensajeCaptura + campoActual);
		campoActual = "numeroEconomico";
		encontrarCasilla = manual.encontrarElemento(campoActual);
		assertTrue(encontrarCasilla,mensajeEncontrar + "texto " + campoActual);

		// PASO 38
		addStep("Capturar numero economico de transporte");
		encontrarCasilla = manual.capturarCasilla(numeroEconomico, campoActual, true, true);
		assertTrue(encontrarCasilla, mensajeCaptura + campoActual);
		u.hardWait(5);
		encontrarCasilla = manual.verificarAutollenado();
		assertTrue(encontrarCasilla,"Los campos del transportista no fueron autollenados totalmente");

		campoActual = "registroIstmo";
		encontrarCasilla = manual.seleccionarElemento(campoActual, false);
		assertFalse(encontrarCasilla, mensajeSeleccion + campoActual);
		campoActual = "poloOrigen";
		encontrarCasilla = manual.capturarCasilla(poloOrigen, campoActual, true, false);
		assertFalse(encontrarCasilla, mensajeSeleccion + campoActual);
		campoActual = "poloDestino";
		encontrarCasilla = manual.capturarCasilla(poloDestino, campoActual, true, true);
		assertFalse(encontrarCasilla, mensajeSeleccion + campoActual);
		getTestCase().addTextEvidenceCurrentStep("Los elementos Istmo han sido eliminados de la pantalla");

		// LOGISTICA INVERSA
		if (logisticaInversa != null) {
			if (logisticaInversa.compareToIgnoreCase("si") == 0) {
				campoActual = "logisticaInversa";
				addStep("Se selecciona la opcion de logistica inversa o devolucion de mercancia");
				encontrarCasilla = manual.seleccionarElemento(campoActual, true);
				assertTrue(encontrarCasilla, mensajeSeleccion + campoActual);
			}
		}
		campoActual = "generarCartaPorte";
		addStep("Se genera carta porte");
		encontrarCasilla = manual.seleccionarElemento(campoActual, false);
		assertTrue(encontrarCasilla, mensajeSeleccion + campoActual);

		encontrarCasilla = manual.determinarEstatusTimbrado();
		assertTrue(encontrarCasilla,"Timbrado fallo");

//PASO 20
		addStep("Se visualiza documentacion generada");
		campoActual = "capturaCorreo";
		encontrarCasilla = manual.capturarCasilla(correo, campoActual, false, false);
		assertTrue(encontrarCasilla,mensajeCaptura + campoActual);
		u.hardWait(5);
		campoActual = "botonEnviar";
		encontrarCasilla = manual.seleccionarElemento(campoActual, false);
		assertTrue(encontrarCasilla, mensajeSeleccion + campoActual);
		getTestCase().addTextEvidenceCurrentStep("Se muestra tira de impresion");
		u.hardWait(5);
		p.clickImagen("printCP");
		String numeroAleatorio=String.valueOf(portalCP.generarNumeroAleatorio(500, 600));
		testCase.addScreenShotCurrentStep(u, "TiraImpresion"+numeroAleatorio);
		u.hardWait(3);
		p.clickImagen("Folder");
		u.hardWait(2);
		sk.screen.type(rutaGuardarTira);
		sk.screen.type(Keys.ENTER);
		u.hardWait(2);
		p.pressTAB(6);
		u.hardWait(2);
		String nombreTira = "CartaPorteTiraDocManual_" + String.valueOf(portalCP.generarNumeroAleatorio(300, 201))
				+ ".pdf";
		getTestCase().addTextEvidenceCurrentStep("Se guarda documento: " + nombreTira);
		sk.screen.type(nombreTira);
		u.hardWait(10);
		sk.screen.type(Keys.TAB);
		u.hardWait(20);
		p.clickImagen("saveCP");
		u.hardWait(20);
		getTestCase().addTextEvidenceCurrentStep(
				"Las validaciones sobre el contenido de cada documento se realizaran de forma manual");
		u.close();
	}

	@Override
	public String setTestDescription() {
		return "Validar que los elementos Istmo no se encuentren visibles en\r\n"
				+ " el formulario para generar carta porte Carta Porte CEDIS Carta Porte Manual\r\n"
				+ " Carta Porte OC";
	}

	@Override
	public String setTestDesigner() {
		return "nellyoliviab@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_012_EliminacionDeElementosIstmo_CartaPorteManual_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		return "1.- Acceso a la plataforma: https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf\r\n"
				+ "" + "\n" + "2.- Origen de Cedis, plaza o terceros" + "\n" + "3.- Destino de Cedis, plaza o terceros "
				+ "\n" + "4.- Articulos existentes (normales o especiales)"
				+ "5.- Datos del transportista validos para el Cedis, plaza o terceros";
	}

	@AfterMethod
	public void obtenerTiempoDeEjecucion(ITestResult result) {
		MetodoGeneral.obtenerTiempoDeEjecucion(result, testCase);
	}
}
