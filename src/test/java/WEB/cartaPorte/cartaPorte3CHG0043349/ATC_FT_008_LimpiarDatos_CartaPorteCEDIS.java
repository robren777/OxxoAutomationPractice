
package WEB.cartaPorte.cartaPorte3CHG0043349;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.hotkey.Keys;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cartaPorte.objectModelPortalCP.CartaPorteCedis;
import WEB.cartaPorte.objectModelPortalCP.LoginCP;
import WEB.cartaPorte.objectModelPortalCP.MenuPortal;
import WEB.cartaPorte.objectModelPortalCP.PortalCartaPorte;
import WEB.util.MetodosSikuli;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_008_LimpiarDatos_CartaPorteCEDIS extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_008_LimpiarDatos_CartaPorteCEDIS_test(HashMap<String, String> data)
			throws FindFailed, SQLException {
		/**
		 * PORTAL CARTA PORTE/ CARTA PORTE / CARTA PORTE CEDIS
		 * 
		 * Cambio a validar: Boton limpiar en Carta Porte CEDIS
		 * 
		 * DESCRIPCION: El objetivo del script es Validar que no se precarguen datos en
		 * la pantalla de Carta Porte CEDIS, se encuentre visible el botón para limpiar
		 * datos y al seleccionarlo se limpien los datos capturados.
		 * 
		 * REREQUISITOS: 2. Actualizar los datos de Dataprovider: Usuario y contraseña:
		 * 6006023--> contactar a myriamguadalupem@hexaware.com para confirmar la
		 * contraseña Nombre chofer y numero economico, asegurarse que estos datos
		 * pertenezcan al origen capturado
		 * 
		 * @author nellyoliviab@hexaware.com
		 */
		String portalurl = data.get("server");
		String userName = data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		// Impresion en documento de evidencia
		String pac = data.get("pac");
		String impresionDescripcion = data.get("impresionDescripcion");
		// Datos de Pantalla Carta Porte CEDIS
		String tipoCartaPorte = data.get("tipoCartaPorte");
		String origen = data.get("origen");
		String mes = data.get("mes");
		String dia = data.get("dia");
		String anio = data.get("anio");
		String detalle = data.get("detalle");
		String nombreChofer = data.get("nombreChofer");
		String correo = data.get("correo");
		String bol = data.get("bol");
		String mesBOL = data.get("mesBOL");
		String diaBOL = data.get("diaBOL");
		String anioBOL = data.get("anioBOL");
		String rutaGuardarTira = data.get("rutaGuardarTira");
		String numeroEconomico = data.get("numeroEconomico");
		// DESCRIPCION DE EVIDENCIA
		testCase.setTest_Description("");
		testCase.setTest_Description(impresionDescripcion + " . PAC de prueba: " + pac);
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		LoginCP login = new LoginCP(u, testCase, portalurl);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("WEB\\"+"CARTAPORTEW");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		MenuPortal portalmenu = new MenuPortal(u, testCase);
		PortalCartaPorte portalCP = new PortalCartaPorte(u, testCase);
		CartaPorteCedis portalCedis = new CartaPorteCedis(u, testCase, portalCP);
		String mensajeDatoEncontrado = "Se encontro informacion en el campo: ";
		String mensajeEncontrar = "No fue posible encontrar elemento: ";
		String mensajeCaptura = "No fue posible capturar datos ";
		String mensajeSeleccion = "No fue posible seleccionar elemento: ";
		// PASO 1:
		addStep("Ingresar a la liga de acceso: https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf");
		u.get(login.getUrl());
		login.proceedToPortal();
		boolean cargarPagina = login.encontrarMensajeInicio();
		testCase.addScreenShotCurrentStep("CargarURL");
		assertTrue("El sitio no fue cargado satisfactoriamente", cargarPagina);

		// PASO 2:
		addStep("Capturar credenciales de acceso para iniciar sesión");
		login.cicloLogin2(userName, password);
		String mensajePortal = portalmenu.accesoMenuPortal(userName, password);
		assertTrue("No se encontró mensaje: Portal de Aplicaciones",
				mensajePortal.compareTo("Portal de Aplicaciones") == 0);

		// PASO 3:
		addStep("Seleccionar \"Portal Carta Porte\"");
		portalmenu.seleccionarOpcionCartaPorte();
		boolean encontrarMenuCP = portalCP.encontrarMenuOpcionesCartaPorte();
		assertTrue("No se encontro Menu de Carta Porte", encontrarMenuCP);

		// PASO 4:
		addStep("Seleccionar Menu de Carta Porte");
		String encontrarOpcionMenuCP = portalCP.encontrarOpcionesCartaPorte(tipoCartaPorte);
		assertEquals(encontrarOpcionMenuCP, "Carta Porte CEDIS", "No se encontro la opcion: Carta Porte Cedis");

		// PASO 5:
		addStep("Seleccionar opcion \"Carta Porte Cedis\"");
		String seleccionarCPCedis = portalCP.seleccionarOpcionCartaPorte(tipoCartaPorte);
		assertEquals(seleccionarCPCedis, "Carta Porte Entrega", "No se encontro el texto: Carta Porte Cedis");

		// PASO 6:
		addStep("Validar que los campos se encuentran vacios");
		String campo = "Origen";
		String datoEncontrado = portalCedis.obtenerTexto(campo);
		assertEquals(datoEncontrado, null, mensajeDatoEncontrado + campo);
		campo = "Descripcion";
		datoEncontrado = portalCedis.obtenerTexto(campo);
		assertEquals(datoEncontrado, null, mensajeDatoEncontrado + campo);
		campo = "SeleccionTiendas";
		datoEncontrado = portalCedis.obtenerTexto(campo);
		assertEquals(datoEncontrado, null, mensajeDatoEncontrado + campo);
		campo = "SeleccionTiendasDerecha";
		datoEncontrado = portalCedis.obtenerTexto(campo);
		assertEquals(datoEncontrado, null, mensajeDatoEncontrado + campo);
		campo = "SeleccionTiendasBOL";
		datoEncontrado = portalCedis.obtenerTexto(campo);
		assertEquals(datoEncontrado, null, mensajeDatoEncontrado + campo);
		campo = "SeleccionTiendasDerechaBOL";
		datoEncontrado = portalCedis.obtenerTexto(campo);
		assertEquals(datoEncontrado, null, mensajeDatoEncontrado + campo);
		campo = "NombreChofer";
		datoEncontrado = portalCedis.obtenerTexto(campo);
		assertEquals(datoEncontrado, null, mensajeDatoEncontrado + campo);
		campo = "NumeroEconomico";
		datoEncontrado = portalCedis.obtenerTexto(campo);
		assertEquals(datoEncontrado, null, mensajeDatoEncontrado + campo);
		boolean encontrarDato = portalCedis.verificarAutollenado();
		assertTrue("Se encontraron datos de autollenado", encontrarDato);
		campo = "pesoBruto";
		datoEncontrado = portalCedis.obtenerTexto(campo);
		assertEquals(datoEncontrado, null, mensajeDatoEncontrado + campo);
		campo = "RegistroIstmo";
		datoEncontrado = portalCedis.obtenerTexto(campo);
		assertEquals(datoEncontrado, null, mensajeDatoEncontrado + campo);
		campo = "LogisticaInversa";
		datoEncontrado = portalCedis.obtenerTexto(campo);
		assertEquals(datoEncontrado, null, mensajeDatoEncontrado + campo);
		testCase.addScreenShotCurrentStep("DatosIniciales");

		// PASO 6:
		addStep("Capturar origen: " + origen);
		String campoActual = "origen";
		boolean encontrarCasilla = portalCedis.capturarCasilla(origen, campoActual, true, true);
		assertTrue(mensajeCaptura + campoActual, encontrarCasilla);
		campoActual = "fechaFacturacion";
		encontrarCasilla = portalCedis.encontrarElemento(campoActual, null);
		assertTrue(mensajeEncontrar + campoActual, encontrarCasilla);

		// PASO 7:
		addStep("Seleccionar fecha de facturacion: " + anio + "-" + mes + "-" + dia);
		// portalCedis.seleccionarElemento("botonAlerta", false, "");
		campoActual = "fechaFacturacionSeleccion";
		encontrarCasilla = portalCedis.seleccionarElemento(campoActual, false, "");
		assertTrue(mensajeSeleccion + campoActual, encontrarCasilla);
		// SELECCIONAR AÑO
		campoActual = "botonAnio";
		encontrarCasilla = portalCedis.seleccionarElemento(campoActual, false, anio);
		assertTrue(mensajeSeleccion + campoActual, encontrarCasilla);
		campoActual = "anio";
		encontrarCasilla = portalCedis.seleccionarElemento(campoActual, false, anio);
		assertTrue(mensajeSeleccion + campoActual, encontrarCasilla);
		campoActual = "mes";
		encontrarCasilla = portalCedis.seleccionarElemento(campoActual, false, mes);
		assertTrue(mensajeSeleccion + campoActual, encontrarCasilla);
		campoActual = "dia";
		encontrarCasilla = portalCedis.seleccionarElemento(campoActual, true, dia);
		assertTrue(mensajeSeleccion + campoActual, encontrarCasilla);
		// ENCONTRAR ESTATUS TRANSFERENCIA
		campoActual = "estatusTransferencia";
		encontrarCasilla = portalCedis.encontrarElemento(campoActual, null);
		assertTrue(mensajeEncontrar + campoActual, encontrarCasilla);

		String estatusTransferencia[] = detalle.split(",");
		int contador = 0;
		int registros = estatusTransferencia.length;
		boolean imprime = true;
		do {
			String check1 = String.valueOf(estatusTransferencia[contador].charAt(0));
			String check2 = String.valueOf(estatusTransferencia[contador].charAt(1));
			String descripcion = estatusTransferencia[contador].substring(2, estatusTransferencia[contador].length());
			// PASO 8 Seleccionar opciones de checkbox 1 (Shipped, Closed o Ambos) y 2
			// (Ruta, Ola, Transferencia)
			if (imprime) {
				addStep("Seleccion de opciones estatus transferencia");
			}
			campoActual = "opciones1";
			encontrarCasilla = portalCedis.seleccionarElemento(campoActual, false, check1);
			assertTrue(mensajeSeleccion + campoActual, encontrarCasilla);
			campoActual = "opciones2";
			encontrarCasilla = portalCedis.seleccionarElemento(campoActual, imprime, check2);
			assertTrue(mensajeSeleccion + campoActual, encontrarCasilla);
			campoActual = portalCedis.obtenerOpcion(check2);
			encontrarCasilla = portalCedis.encontrarElemento(campoActual, null);
			assertTrue(mensajeEncontrar + campoActual, encontrarCasilla);

			// PASO 9: Capturar descripción en casilla de opción 2

			addStep("Capturar descripcion: " + descripcion);

			encontrarCasilla = portalCedis.capturarCasilla(descripcion, campoActual, false, true);
			assertTrue(mensajeCaptura + campoActual, encontrarCasilla);
			int cuentaOp = 1;
			boolean continuar = false;
			do {
				campoActual = "botonBuscar";
				// portalCedis.seleccionarElemento("botonAlerta", false, "");
				encontrarCasilla = portalCedis.encontrarElemento(campoActual, null);
				assertTrue(mensajeEncontrar + campoActual, encontrarCasilla);

				// PASO 10: Seleccionar boton Buscar
				if (imprime) {
					addStep("Seleccionar boton Buscar");
				}
				encontrarCasilla = portalCedis.seleccionarElemento(campoActual, imprime, "");
				assertTrue(mensajeSeleccion + campoActual, encontrarCasilla);
				// encontrarCasilla=portalCedis.seleccionarElemento("botonAlerta", false, "");

				continuar = true;
				campoActual = "tablaRegistros";
				encontrarCasilla = portalCedis.encontrarElemento(campoActual, null);
				assertTrue(mensajeEncontrar + campoActual, encontrarCasilla);

				// PASO 11:
				if (imprime) {
					addStep("Seleccionar tiendas");
				}
				campoActual = "seleccionaTiendas";
				encontrarCasilla = portalCedis.seleccionarElemento(campoActual, imprime, "");
				assertTrue(mensajeSeleccion + campoActual, encontrarCasilla);

				cuentaOp++;
			} while (!continuar && cuentaOp < 3);
			campoActual = "seleccionDerecha";
			encontrarCasilla = portalCedis.encontrarElemento(campoActual, null);
			assertTrue(mensajeEncontrar + campoActual, encontrarCasilla);

			// PASO 12:
			addStep("Seleccionar elemento visual para mover tiendas de la descripcion: " + descripcion);
			encontrarCasilla = portalCedis.seleccionarElemento(campoActual, true, "");
			assertTrue(mensajeSeleccion + campoActual, encontrarCasilla);
			contador++;
			imprime = false;
		} while (contador < registros);

		if (bol.compareToIgnoreCase("SI") == 0) {
			campoActual = "fechainicialBOL";
			encontrarCasilla = portalCedis.encontrarElemento(campoActual, null);
			assertTrue(mensajeEncontrar + campoActual, encontrarCasilla);
			// PASO 13:
			addStep("Seleccionar fecha inicial BOL: " + anioBOL + "-" + mesBOL + "-" + diaBOL);
			u.scroll(400);
			encontrarCasilla = portalCedis.seleccionarElemento(campoActual, true, "");
			assertTrue(mensajeSeleccion + campoActual, encontrarCasilla);
			// PASO 14 Seleccionar fecha inicial BOL (año, mes, día)
			addStep("Seleccionar fecha inicial BOL");
			campoActual = "botonAnio";
			encontrarCasilla = portalCedis.seleccionarElemento(campoActual, false, anioBOL);
			assertTrue(mensajeSeleccion + campoActual, encontrarCasilla);
			campoActual = "anio";
			encontrarCasilla = portalCedis.seleccionarElemento(campoActual, false, anioBOL);
			assertTrue(mensajeSeleccion + campoActual, encontrarCasilla);
			campoActual = "mes";
			encontrarCasilla = portalCedis.seleccionarElemento(campoActual, false, mesBOL);
			assertTrue(mensajeSeleccion + campoActual, encontrarCasilla);
			campoActual = "dia";
			encontrarCasilla = portalCedis.seleccionarElemento(campoActual, false, diaBOL);
			assertTrue(mensajeSeleccion + campoActual, encontrarCasilla);
			campoActual = "botonDevolucion";
			encontrarCasilla = portalCedis.seleccionarElemento(campoActual, true, "");
			assertTrue(mensajeSeleccion + campoActual, encontrarCasilla);
			u.scroll(400);
			campoActual = "tablaDevolucion";
			encontrarCasilla = portalCedis.encontrarElemento(campoActual, "");
			assertTrue(mensajeEncontrar + campoActual, encontrarCasilla);

			// PASO 16
			addStep("Seleccionar registros de devolucion");
			u.scroll(400);
			campoActual = "seleccionarDevolcion";
			encontrarCasilla = portalCedis.seleccionarElemento(campoActual, false, "1");
			assertTrue(mensajeSeleccion + campoActual, encontrarCasilla);
			campoActual = "flecha";
			encontrarCasilla = portalCedis.seleccionarElemento(campoActual, true, "");
			assertTrue(mensajeSeleccion + campoActual, encontrarCasilla);

		}

		campoActual = "nombreChofer";
		encontrarCasilla = portalCedis.encontrarElemento(campoActual, null);
		assertTrue(mensajeEncontrar + campoActual, encontrarCasilla);

		// PASO 37
		addStep("Capturar nombre de transportista: " + nombreChofer);
		encontrarCasilla = portalCedis.capturarCasilla(nombreChofer, campoActual, true, true);
		assertTrue(mensajeCaptura + campoActual, encontrarCasilla);
		campoActual = "numeroEconomico";
		encontrarCasilla = portalCedis.encontrarElemento(campoActual, null);
		assertTrue(mensajeEncontrar + "texto " + campoActual, encontrarCasilla);

		// PASO 18
		addStep("Capturar numero economico de transporte: " + numeroEconomico);
		encontrarCasilla = portalCedis.capturarCasilla(numeroEconomico, campoActual, true, true);
		assertTrue(mensajeCaptura + campoActual, encontrarCasilla);
		encontrarCasilla = portalCedis.verificarAutollenado();
		assertTrue("Los campos del transportista no fueron autollenados totalmente", encontrarCasilla);

		addStep("Obtener valor de Logistica inversa");
		campoActual = "estatusLogisticaInversa";
		String obtenerValor = portalCedis.obtenerTexto(campoActual);
		getTestCase().addTextEvidenceCurrentStep("Estatus: seleccionado");
		assertEquals(obtenerValor, "Seleccionado",
				"El valor inicial de Logistica Inversa debe mostrarse seleccionable");
		// PASO 19
		addStep("Se genera carta porte");
		campoActual = "seleccionarCartaPorte";
		u.scroll(200);
		encontrarCasilla = portalCedis.seleccionarElemento(campoActual, false, mesBOL);
		assertTrue(mensajeSeleccion + campoActual, encontrarCasilla);
		encontrarCasilla = portalCedis.determinarEstatusTimbrado();

		if (encontrarCasilla == true) {
			//PASO 20
			addStep("Se visualiza documentacion generada");
			campoActual="capturaCorreo";
			encontrarCasilla= portalCedis.capturarCasilla(correo+Key.TAB, campoActual,false,false);
			assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
			campoActual="botonEnviar";
			encontrarCasilla= portalCedis.seleccionarElemento(campoActual,false,"");
			assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
			getTestCase().addTextEvidenceCurrentStep("Se muestra tira de impresion");
			u.hardWait(20);
			p.clickImagen("printCP");
			testCase.addScreenShotCurrentStep("TiraImpresion"+portalCP.generarNumeroAleatorio(500, 600));
			u.hardWait(2);
			p.clickImagen("Folder");
			u.hardWait(2);
			sk.screen.type(rutaGuardarTira);
			sk.screen.type(Keys.ENTER);
			u.hardWait(2);
			p.pressTAB(6);
			u.hardWait(2);
			String nombreTira="CartaPorteTiraDocCEDIS_"+String.valueOf(portalCP.generarNumeroAleatorio(300, 201))+".pdf";
			getTestCase().addTextEvidenceCurrentStep("Se guarda documento: "+nombreTira);
			sk.screen.type(nombreTira);
			u.hardWait(20);
			sk.screen.type(Keys.TAB);
			u.hardWait(20);
			p.clickImagen("saveCP");
		}

		// PASO 7
		// Cuando Instalen
		
		 addStep("Seleccionar botón Limpiar datos"); 
		 campoActual="botonLimpiar";
		 encontrarCasilla=portalCedis.seleccionarElemento(campoActual,true, "");
		 assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		 
		// PASO 6:
		addStep("Validar que los campos se encuentran vacios");
		campo = "Origen";
		datoEncontrado = portalCedis.obtenerTexto(campo);
		assertEquals(datoEncontrado, null, mensajeDatoEncontrado + campo);
		campo = "Descripcion";
		datoEncontrado = portalCedis.obtenerTexto(campo);
		assertEquals(datoEncontrado, null, mensajeDatoEncontrado + campo);
		campo = "SeleccionTiendas";
		datoEncontrado = portalCedis.obtenerTexto(campo);
		assertEquals(datoEncontrado, null, mensajeDatoEncontrado + campo);
		campo = "SeleccionTiendasDerecha";
		datoEncontrado = portalCedis.obtenerTexto(campo);
		assertEquals(datoEncontrado, null, mensajeDatoEncontrado + campo);
		campo = "SeleccionTiendasBOL";
		datoEncontrado = portalCedis.obtenerTexto(campo);
		assertEquals(datoEncontrado, null, mensajeDatoEncontrado + campo);
		campo = "SeleccionTiendasDerechaBOL";
		datoEncontrado = portalCedis.obtenerTexto(campo);
		assertEquals(datoEncontrado, null, mensajeDatoEncontrado + campo);
		campo = "NombreChofer";
		datoEncontrado = portalCedis.obtenerTexto(campo);
		assertEquals(datoEncontrado, null, mensajeDatoEncontrado + campo);
		campo = "NumeroEconomico";
		datoEncontrado = portalCedis.obtenerTexto(campo);
		assertEquals(datoEncontrado, null, mensajeDatoEncontrado + campo);
		encontrarDato = portalCedis.verificarAutollenado();
		assertTrue("Se encontraron datos de autollenado", encontrarDato);
		campo = "pesoBruto";
		datoEncontrado = portalCedis.obtenerTexto(campo);
		assertEquals(datoEncontrado, null, mensajeDatoEncontrado + campo);
		testCase.addScreenShotCurrentStep("DatosReiniciados");
		
		addStep("Obtener valor de Logistica inversa");
		campoActual = "estatusLogisticaInversa";
		obtenerValor = portalCedis.obtenerTexto(campoActual);
		getTestCase().addTextEvidenceCurrentStep("Estatus: seleccionado");
		assertEquals(obtenerValor, "Seleccionado",
				"El valor inicial de Logistica Inversa debe mostrarse seleccionable");
		u.close();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		return "1.- Usuario con acceso a Portal Carta Porte: https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf\r\n"
				+ ""+"\n"
				+ "2.- Informacion para crear Unidad"+"\n"
				+ "3.- Origen de Cedis o PT"+"\n"
				+ "4.- Fecha de facturacion "+"\n"
				+ "5.- Estatus de transferencia (Shipped, closed o ambos) y (Ruta, Ola o Transterencia)"+"\n"
				+ "6.- Fecha inicial BOL"+"\n"
				+ "7.- Datos del transportista validos para el Cedis, plaza o terceros";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que las mejoras en los tiempos de consulta\r\n"
				+ "	de la BD no afectan la generación de documentación Carta Porte";
	}

	@Override
	public String setTestDesigner() {
		return "nellyoliviab@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_008_LimpiarDatos_CartaPorteCEDIS_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}


	@AfterMethod
	public void obtenerTiempoDeEjecucion(ITestResult result) {
		long ms = result.getEndMillis() - result.getStartMillis();
		int ss = (int) (ms / 1000) % 60;
		int mm = (int) (ms / (1000 * 60) % 60);
		int hh = (int) ((ms / (1000 * 60 * 60)) % 24);
		String tiempoDeEjecucion = String.format("%02d:%02d:%02d", hh, mm, ss);

		getTestCase().addTextEvidenceCurrentStep("Tiempo de ejecución:  " + tiempoDeEjecucion);
		System.out.println("Tiempo de ejecución:  " + tiempoDeEjecucion);
	}
}
