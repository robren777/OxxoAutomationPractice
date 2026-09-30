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

/**DESCRIPCION: Valida que el boton 'Consultar' se muestra deshabilitado al cargar la pantalla Devoluciones Especiales
 * y que se habilita unicamente al capturar un rango de fechas valido, pudiendo combinarse con filtros opcionales.
 * Valida ademas que al hacer clic en Consultar el sistema ejecuta la busqueda con los criterios seleccionados.
 * Perfil ADMIN.
 *
 *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_362 Verificar habilitacion condicional del boton Consultar y ejecucion de busqueda con fecha y filtros opcionales - perfil ADMIN
 *
 *PREREQUISITOS:
 *1. Perfil ADMIN (usuario 5555555) disponible en ambiente QA con registros disponibles para los filtros CEDIS y Plaza.
 *2. Liga de Portal Cometa.
 *
 *INSUMOS:
 *Usuario ADMIN configurado en el data provider.
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/Login
 *
 * @author Mauricio Gurrola
 * @date Agosto 2026
 */
public class ATC_FT_362_HabilitacionConsultarRangoFechasCA07ADMIN extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_362_HabilitacionConsultarRangoFechasCA07ADMIN_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		accessControlPage accessControl = new accessControlPage(u, testCase, pc4);
		menuCedisPage menu = new menuCedisPage(u, testCase);
		recepcionTransferenciasPage recepcion = new recepcionTransferenciasPage(u, testCase);
		devolucionesEspecialesPage devEspecialesPage = new devolucionesEspecialesPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "ADMIN";
		String password = pc4.obtenerPasswordPorUsuario(user);

		/*
		 * PRECONDICION
		 ***************************************************************************/
		addStep("Precondicion: obtener password configurado para el usuario " + user + " con perfil " + perfil);
		boolean resultPrecondicion = accessControl.validarPrecondicionPerfil(url, user, "5555555", perfil, password);
		assertTrue(resultPrecondicion, "Precondicion fallida: la URL, el usuario ADMIN o sus credenciales no estan configurados");
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
		System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);

		/*
		 *                                             PASO 1
		 ***************************************************************************/
		addStep("Ingresar la URL " + url + " en el campo de direcciones del navegador");
		boolean resultStep1 = pc4.IngresarURL(url, true);
		assertTrue(resultStep1, "Paso 1 fallido: no se muestra la pantalla de inicio de sesion del portal Access Control");
		testCase.addTextEvidenceCurrentStep("El sistema muestra la pantalla de inicio de sesion del portal Access Control.");
		pc4.PrivatePortal();

		/*
		 *                                             PASO 2
		 ***************************************************************************/
		addStep("Escribir '5555555' en el campo Usuario");
		boolean resultStep2 = accessControl.capturarYValidarUsuario(user);
		assertTrue(resultStep2, "Paso 2 fallido: el campo Usuario no muestra el valor '5555555'");
		testCase.addTextEvidenceCurrentStep("El campo Usuario muestra el valor '5555555'.");

		/*
		 *                                             PASO 3
		 ***************************************************************************/
		addStep("Escribir la contrasena del perfil ADMIN en el campo Contrasena");
		boolean resultStep3 = accessControl.capturarYValidarPassword(password);
		assertTrue(resultStep3, "Paso 3 fallido: la contrasena no queda capturada y enmascarada en el campo");
		testCase.addTextEvidenceCurrentStep("El campo Contrasena muestra los caracteres enmascarados.");

		/*
		 *                                             PASO 4
		 ***************************************************************************/
		addStep("Dar clic en el boton Iniciar sesion");
		boolean resultStep4 = accessControl.iniciarSesionYValidarAcceso();
		assertTrue(resultStep4, "Paso 4 fallido: las credenciales no permiten iniciar sesion o no se redirige a la seleccion de portales");
		testCase.addTextEvidenceCurrentStep("El sistema valida las credenciales, inicia sesion y redirige a la pantalla de seleccion de portales donde se visualizan las opciones General y Portal COMETA.");

		/*
		 *                                             PASO 5
		 ***************************************************************************/
		addStep("Dar clic en la tarjeta 'Portal COMETA'");
		boolean resultStep5 = pc4.seleccionarPortalCometaQA(true);
		assertTrue(resultStep5, "Paso 5 fallido: no se visualiza la pantalla principal del portal Cometa con las tarjetas del perfil ADMIN");
		testCase.addTextEvidenceCurrentStep("Se visualiza la pantalla principal del portal Cometa con las tarjetas: CEDIS, Directos, FAQS CEDIS, FAQS Directos y Centro de descarga.");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Dar clic en la tarjeta CEDIS");
		boolean resultStep6 = pc4.clicBotonCometaCedis(true);
		assertTrue(resultStep6, "Paso 6 fallido: no se visualiza la pantalla del modulo CEDIS con las sub-tarjetas disponibles");
		testCase.addTextEvidenceCurrentStep("Se visualiza la pantalla del modulo CEDIS con las sub-tarjetas: Recepcion, Reportes y Graficas.");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Dar clic en la sub-tarjeta 'Recepcion y devolucion' del modulo CEDIS");
		boolean resultStep7 = menu.clicRecepcionYDevolucion();
		assertTrue(resultStep7, "Paso 7 fallido: el sistema no navego hacia la pantalla Recepcion de Transferencias y Devoluciones");
		testCase.addTextEvidenceCurrentStep("El sistema navega a la pantalla Recepcion de Transferencias y Devoluciones y muestra el modal 'Espere un momento...'.");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Esperar a que el modal 'Espere un momento...' se cierre automaticamente");
		boolean resultStep8 = recepcion.validaCargaInicialFiltrosRapidosEHistoricos(7);
		assertTrue(resultStep8, "Paso 8 fallido: el modal no se cerro o la pantalla Recepcion de Transferencias y Devoluciones no quedo completamente cargada con sus filtros y grid disponibles");
		testCase.addTextEvidenceCurrentStep("El modal se cierra y la pantalla Recepcion de Transferencias y Devoluciones queda completamente cargada con sus filtros y grid disponibles.");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Dar clic en el boton 'Devoluciones Especiales' visible en la parte superior de la pantalla Recepcion de Transferencias y Devoluciones");
		boolean resultStep9 = devEspecialesPage.ingresarDevolucionesEspeciales(true);
		assertTrue(resultStep9, "Paso 9 fallido: el sistema no redirige a la pantalla Devoluciones Especiales o la pantalla no quedo disponible");
		testCase.addTextEvidenceCurrentStep("El sistema redirige a la pantalla Devoluciones Especiales mostrando los filtros (CEDIS, Plaza, Tienda, Bol, Rango de fechas) sin valores previos y el grid con la carga inicial por defecto.");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Observar el estado del boton 'Consultar' inmediatamente despues de la carga inicial de la pantalla Devoluciones Especiales");
		boolean resultStep10 = devEspecialesPage.validarBotonConsultarDeshabilitadoSinRangoFechas(true);
		assertTrue(resultStep10, "CA07 fallido: el boton 'Consultar' no se muestra deshabilitado en la carga inicial de Devoluciones Especiales");
		testCase.addTextEvidenceCurrentStep("El boton 'Consultar' se muestra deshabilitado (CA07 verificado - estado inicial deshabilitado).");

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("Seleccionar un rango de fechas valido en el campo 'Rango de fechas'");
		boolean resultStep11 = devEspecialesPage.seleccionarMismoDiaRangoFechas(true);
		assertTrue(resultStep11, "Paso 11 fallido: no fue posible seleccionar un rango de fechas valido en el campo 'Rango de fechas'");
		testCase.addTextEvidenceCurrentStep("El campo Rango de fechas muestra el rango seleccionado.");

		/*
		 *                                             PASO 12
		 ***************************************************************************/
		addStep("Observar el estado del boton 'Consultar' inmediatamente despues de capturar el rango de fechas valido");
		boolean resultStep12 = devEspecialesPage.validarBotonConsultarHabilitadoPorRangoFechas(true);
		assertTrue(resultStep12, "CA07 fallido: el boton 'Consultar' no se habilita al capturar un rango de fechas valido");
		testCase.addTextEvidenceCurrentStep("El boton 'Consultar' se habilita (CA07 verificado - habilitacion con fecha).");

		/*
		 *                                             PASO 13
		 ***************************************************************************/
		addStep("Seleccionar un valor en el filtro opcional 'CEDIS'");
		boolean resultStep13 = devEspecialesPage.seleccionarPrimeraOpcionFiltroCedis(true);
		assertTrue(resultStep13, "Paso 13 fallido: no fue posible seleccionar un valor en el filtro opcional 'CEDIS'");
		testCase.addTextEvidenceCurrentStep("El filtro CEDIS muestra el valor seleccionado.");

		/*
		 *                                             PASO 14
		 ***************************************************************************/
		addStep("Seleccionar un valor en el filtro opcional 'Plaza'");
		boolean resultStep14 = devEspecialesPage.seleccionarPrimeraOpcionFiltroPlaza(true);
		assertTrue(resultStep14, "Paso 14 fallido: no fue posible seleccionar un valor en el filtro opcional 'Plaza'");
		testCase.addTextEvidenceCurrentStep("El filtro Plaza muestra el valor seleccionado.");

		/*
		 *                                             PASO 15
		 ***************************************************************************/
		addStep("Dar clic en el boton 'Consultar'");
		boolean resultStep15 = devEspecialesPage.ejecutarConsultaDevolucionesEspeciales(true);
		assertTrue(resultStep15, "CA07.1 fallido: el sistema no ejecuto la busqueda considerando el rango de fechas y los filtros opcionales CEDIS y Plaza seleccionados");
		testCase.addTextEvidenceCurrentStep("El sistema ejecuta la busqueda considerando el rango de fechas capturado y los filtros opcionales CEDIS y Plaza seleccionados, y muestra en el grid los registros que cumplen con dichos criterios (CA07.1 verificado).");
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
		return "*1. Perfil ADMIN (usuario 5555555) disponible en ambiente QA con registros disponibles para los filtros CEDIS y Plaza.\r\n"
				+ "*2. Liga de Portal Cometa.";
	}

	@Override
	public String setTestDescription() {
		return "Valida que el boton Consultar se muestra deshabilitado al cargar Devoluciones Especiales y se habilita al capturar un rango de fechas valido, y que al hacer clic el sistema ejecuta la busqueda con los filtros seleccionados. Perfil ADMIN.";
	}

	@Override
	public String setTestDesigner() {
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_362_HabilitacionConsultarRangoFechasCA07ADMIN_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}
