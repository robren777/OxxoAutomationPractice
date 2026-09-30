package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.accessControlPage;
import WEB.cometa.om.devolucionesEspecialesPage;
import WEB.cometa.om.menuCedisPage;
import WEB.cometa.om.recepcionTransferenciasPage;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCION: Verificar funcionalidad de los iconos de primera pagina (|<)
 * y pagina anterior (<) en la vista preliminar de Devoluciones Especiales para perfil CEDIS.
 *
 *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_375 Verificar funcionalidad de los iconos de primera pagina (|<) y pagina anterior (<) en la vista preliminar - perfil CEDIS
 *
 *PREREQUISITOS:
 *1. Perfil CEDIS (usuario 6666666) disponible en ambiente QA.
 *2. Grid de Devoluciones Especiales con datos para paginacion en vista preliminar.
 *3. Liga de Portal Cometa.
 *
 *INSUMOS:
 *Usuario CEDIS configurado en el data provider.
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
 * @author Mauricio Gurrola
 * @date Agosto 2026
 */
public class ATC_FT_375_FuncionalidadIconosPrimeraAnteriorVistaPreliminarDevEspecialesCEDIS extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_375_FuncionalidadIconosPrimeraAnteriorVistaPreliminarDevEspecialesCEDIS_test(HashMap<String, String> data)
			throws Exception {
		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		accessControlPage accessControl = new accessControlPage(u, testCase, pc4);
		menuCedisPage menu = new menuCedisPage(u, testCase);
		recepcionTransferenciasPage recepcion = new recepcionTransferenciasPage(u, testCase);
		devolucionesEspecialesPage devEspecialesPage = new devolucionesEspecialesPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "CEDIS";
		String password = pc4.obtenerPasswordPorUsuario(user);
		String bolPrimerRegistro = "";
		String bolSegundoRegistro = "";

		/*
		 * PRECONDICION
		 ***************************************************************************/
		addStep("Precondicion: obtener password configurado para el usuario " + user + " con perfil " + perfil);
		boolean resultPrecondicion = accessControl.validarPrecondicionPerfil(url, user, user, perfil, password);
		assertTrue(resultPrecondicion,
				"Precondicion fallida: la URL, el usuario CEDIS o sus credenciales no estan configurados");
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);

		/*
		 *                                             PASO 1
		 ***************************************************************************/
		addStep("Ingresar la URL " + url + " en el campo de direcciones del navegador");
		boolean resultStep1 = pc4.IngresarURL(url, true);
		assertTrue(resultStep1,
				"Paso 1 fallido: no se muestra la pantalla de inicio de sesion del portal Access Control");
		pc4.PrivatePortal();

		/*
		 *                                             PASO 2
		 ***************************************************************************/
		addStep("Escribir '" + user + "' en el campo Usuario");
		boolean resultStep2 = accessControl.capturarYValidarUsuario(user);
		assertTrue(resultStep2, "Paso 2 fallido: el campo Usuario no muestra el valor esperado del perfil CEDIS");

		/*
		 *                                             PASO 3
		 ***************************************************************************/
		addStep("Escribir la contrasena del perfil CEDIS en el campo Contrasena");
		boolean resultStep3 = accessControl.capturarYValidarPassword(password);
		assertTrue(resultStep3, "Paso 3 fallido: la contrasena no queda capturada y enmascarada en el campo");

		/*
		 *                                             PASO 4
		 ***************************************************************************/
		addStep("Dar clic en el boton Iniciar sesion");
		boolean resultStep4 = accessControl.iniciarSesionYValidarAcceso();
		assertTrue(resultStep4,
				"Paso 4 fallido: las credenciales no permiten iniciar sesion o no se redirige a la pantalla de seleccion de portales");

		/*
		 *                                             PASO 5
		 ***************************************************************************/
		addStep("Dar clic en la opcion Portal COMETA");
		boolean resultStep5 = pc4.seleccionarPortalCometaQA(true);
		assertTrue(resultStep5,
				"Paso 5 fallido: no se visualiza la pantalla principal del portal Cometa con las opciones del perfil CEDIS");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Dar clic en CEDIS > Recepcion y devolucion > Devoluciones especiales");
		boolean resultStep6Cedis = pc4.clicBotonCometaCedis(true);
		assertTrue(resultStep6Cedis, "Paso 6 fallido: no fue posible ingresar al modulo CEDIS");
		boolean resultStep6Recepcion = menu.clicRecepcionYDevolucion();
		assertTrue(resultStep6Recepcion,
				"Paso 6 fallido: no fue posible abrir Recepcion y devolucion para continuar hacia Devoluciones Especiales");
		boolean resultStep6CargaRecepcion = recepcion.validaCargaInicialFiltrosRapidosEHistoricos(7);
		assertTrue(resultStep6CargaRecepcion,
				"Paso 6 fallido: la pantalla Recepcion y devolucion no quedo estable para seleccionar Devoluciones Especiales");
		boolean resultStep6 = devEspecialesPage.ingresarDevolucionesEspeciales(true);
		assertTrue(resultStep6,
				"Paso 6 fallido: no se visualiza la pantalla Devoluciones Especiales con los filtros esperados");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Seleccionar un rango de fechas que retorne minimo 2 registros en el grid y dar clic en Consultar");
		boolean resultStep7Rango = devEspecialesPage.seleccionarRangoSieteDiasDesdeFechaInicialBdCedis(user, true);
		assertTrue(resultStep7Rango,
				"Paso 7 fallido: no fue posible seleccionar un rango de fechas valido igual o menor a 7 dias");
		boolean resultStep7Consultar = devEspecialesPage.ejecutarConsultaDevolucionesEspeciales(true);
		assertTrue(resultStep7Consultar,
				"Paso 7 fallido: no se pudo ejecutar la consulta o el grid no quedo visible al terminar la carga");
		boolean resultStep7Minimo = devEspecialesPage.validarGridConMinimoRegistrosDevolucionesEspeciales(2, true);
		assertTrue(resultStep7Minimo,
				"Paso 7 fallido: el grid no muestra al menos 2 registros despues de la consulta");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Anotar los ultimos 4 digitos del BOL del primer registro y del segundo registro del grid");
		boolean resultStep8 = devEspecialesPage.capturarBolPrimerYSegundoRegistroGridDevEspeciales(true);
		assertTrue(resultStep8,
				"Paso 8 fallido: no fue posible capturar el BOL del primer y segundo registro del grid");
		String bolPrimerRegistroCompleto = devEspecialesPage.obtenerBolPrimerRegistroGridDevEspecialesCapturado();
		String bolSegundoRegistroCompleto = devEspecialesPage.obtenerBolSegundoRegistroGridDevEspecialesCapturado();
		bolPrimerRegistro = devEspecialesPage.obtenerUltimos4DigitosBolParaValidacion(bolPrimerRegistroCompleto);
		bolSegundoRegistro = devEspecialesPage.obtenerUltimos4DigitosBolParaValidacion(bolSegundoRegistroCompleto);
		testCase.addTextEvidenceCurrentStep("BOL registro 1 (ultimos 4 digitos): " + bolPrimerRegistro);
		testCase.addTextEvidenceCurrentStep("BOL registro 2 (ultimos 4 digitos): " + bolSegundoRegistro);

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Dar clic en el icono de la columna Devolucion Especial del segundo registro del grid");
		boolean resultStep9Abrir = devEspecialesPage.abrirVistaPreliminarRegistroPorIndiceDevolucionEspecial(2, true);
		assertTrue(resultStep9Abrir,
				"Paso 9 fallido: no se abre la vista preliminar del segundo registro del grid");
		boolean resultStep9Rango = devEspecialesPage.validarRangoActualPaginacionTicketContiene("2 - 2", true);
		assertTrue(resultStep9Rango,
				"Paso 9 fallido: el rango del paginador no indica '2 - 2 of [total]' al abrir el segundo registro");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Validar dentro de la vista preliminar que el ticket mostrado corresponde a los ultimos 4 digitos del BOL del segundo registro");
		boolean resultStep10 = devEspecialesPage.validarDatosGlobalesVistaPreliminarDevEspeciales(bolSegundoRegistro, true, false);
		assertTrue(resultStep10,
				"Paso 10 fallido: el BOL mostrado en el ticket no coincide con el BOL del segundo registro del grid");

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("Dar clic en el icono |< en la parte inferior de la vista preliminar");
		boolean resultStep11Nav = devEspecialesPage.navegarPaginacionTicketPorIcono("primera", true);
		assertTrue(resultStep11Nav,
				"Paso 11 fallido: no fue posible navegar con el icono |< en la vista preliminar");
		boolean resultStep11Rango = devEspecialesPage.validarRangoActualPaginacionTicketContiene("1 - 1", true);
		assertTrue(resultStep11Rango,
				"Paso 11 fallido: el rango del paginador no indica '1 - 1 of [total]' despues de usar |<");

		/*
		 *                                             PASO 12
		 ***************************************************************************/
		addStep("Comparar en la vista preliminar los ultimos 4 digitos del BOL mostrado contra los del primer registro anotado en el paso 8");
		boolean resultStep12 = devEspecialesPage.validarDatosGlobalesVistaPreliminarDevEspeciales(bolPrimerRegistro, true, false);
		assertTrue(resultStep12,
				"Paso 12 fallido: el BOL del ticket no coincide con el BOL del primer registro despues de usar |<");

		/*
		 *                                             PASO 13
		 ***************************************************************************/
		addStep("Dar clic en el icono > en la parte inferior para avanzar al siguiente registro");
		boolean resultStep13Nav = devEspecialesPage.navegarPaginacionTicketPorIcono("siguiente", true);
		assertTrue(resultStep13Nav,
				"Paso 13 fallido: no fue posible navegar con el icono > en la vista preliminar");
		boolean resultStep13Rango = devEspecialesPage.validarRangoActualPaginacionTicketContiene("2 - 2", true);
		assertTrue(resultStep13Rango,
				"Paso 13 fallido: el rango del paginador no indica '2 - 2 of [total]' despues de usar >");

		/*
		 *                                             PASO 14
		 ***************************************************************************/
		addStep("Dar clic en el icono < en la parte inferior de la vista preliminar");
		boolean resultStep14Nav = devEspecialesPage.navegarPaginacionTicketPorIcono("anterior", true);
		assertTrue(resultStep14Nav,
				"Paso 14 fallido: no fue posible navegar con el icono < en la vista preliminar");
		boolean resultStep14Rango = devEspecialesPage.validarRangoActualPaginacionTicketContiene("1 - 1", true);
		assertTrue(resultStep14Rango,
				"Paso 14 fallido: el rango del paginador no indica '1 - 1 of [total]' despues de usar <");

		/*
		 *                                             PASO 15
		 ***************************************************************************/
		addStep("Comparar en la vista preliminar los ultimos 4 digitos del BOL mostrado contra los del primer registro anotado en el paso 8");
		boolean resultStep15 = devEspecialesPage.validarDatosGlobalesVistaPreliminarDevEspeciales(bolPrimerRegistro, true, false);
		assertTrue(resultStep15,
				"Paso 15 fallido: el BOL del ticket no coincide con el BOL del primer registro despues de navegar con <");
	}

	@AfterMethod
	public void afterMethod() {
		if (u != null) {
			u.quit();
		}
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "*1. Perfil CEDIS (usuario 6666666) disponible en ambiente QA.\r\n"
				+ "*2. Grid de Devoluciones Especiales con datos para paginacion en vista preliminar.\r\n"
				+ "*3. Liga de Portal Cometa.";
	}

	@Override
	public String setTestDescription() {
		return "Verificar funcionalidad de los iconos de primera pagina (|<) y pagina anterior (<) en la vista preliminar de Devoluciones Especiales para perfil CEDIS.";
	}

	@Override
	public String setTestDesigner() {
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_375_FuncionalidadIconosPrimeraAnteriorVistaPreliminarDevEspecialesCEDIS_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}
