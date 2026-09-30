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

/**DESCRIPCION: Valida que al hacer clic en el boton 'Limpiar filtros' de la pantalla Devoluciones Especiales
 * el sistema limpia el rango de fechas y los filtros opcionales, deshabilita el boton 'Consultar'
 * y restablece el grid a su estado inicial con carga por defecto. Perfil ADMIN.
 *
 *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_364 Verificar que Limpiar filtros restablece filtros, Consultar y grid a estado inicial - perfil ADMIN
 *
 *PREREQUISITOS:
 *1. Perfil ADMIN (usuario 5555555) disponible en ambiente QA con registros disponibles para filtros CEDIS y Plaza.
 *2. Liga de Portal Cometa.
 *
 *INSUMOS:
 *Usuario ADMIN configurado en el data provider.
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/Login
 *
 * @author Mauricio Gurrola
 * @date Agosto 2026
 */
public class ATC_FT_364_LimpiarFiltrosDevEspecialesCA08ADMIN extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_364_LimpiarFiltrosDevEspecialesCA08ADMIN_test(HashMap<String, String> data) throws Exception {
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
		addStep("Seleccionar un rango de fechas valido en el campo 'Rango de fechas'");
		boolean resultStep10 = devEspecialesPage.seleccionarMismoDiaRangoFechas(true);
		assertTrue(resultStep10, "Paso 10 fallido: no fue posible seleccionar un rango de fechas valido en el campo 'Rango de fechas'");
		testCase.addTextEvidenceCurrentStep("El campo Rango de fechas muestra el rango seleccionado y el boton 'Consultar' se habilita.");

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("Seleccionar un valor en el filtro opcional 'CEDIS'");
		boolean resultStep11 = devEspecialesPage.seleccionarPrimeraOpcionFiltroCedis(true);
		assertTrue(resultStep11, "Paso 11 fallido: no fue posible seleccionar un valor en el filtro opcional 'CEDIS'");
		testCase.addTextEvidenceCurrentStep("El filtro CEDIS muestra el valor seleccionado.");

		/*
		 *                                             PASO 12
		 ***************************************************************************/
		addStep("Seleccionar un valor en el filtro opcional 'Plaza'");
		boolean resultStep12 = devEspecialesPage.seleccionarPrimeraOpcionFiltroPlaza(true);
		assertTrue(resultStep12, "Paso 12 fallido: no fue posible seleccionar un valor en el filtro opcional 'Plaza'");
		testCase.addTextEvidenceCurrentStep("El filtro Plaza muestra el valor seleccionado.");

		/*
		 *                                             PASO 13
		 ***************************************************************************/
		addStep("Dar clic en el boton 'Consultar'");
		boolean resultStep13 = devEspecialesPage.ejecutarConsultaDevolucionesEspeciales(true);
		assertTrue(resultStep13, "Paso 13 fallido: el sistema no ejecuto la busqueda con los filtros capturados");
		testCase.addTextEvidenceCurrentStep("El sistema ejecuta la busqueda y el grid muestra los registros que cumplen con los filtros capturados.");

		/*
		 *                                             PASO 14
		 ***************************************************************************/
		addStep("Dar clic en el boton 'Limpiar filtros'");
		boolean resultStep14 = devEspecialesPage.clicLimpiarFiltrosDevolucionesEspeciales(true);
		assertTrue(resultStep14, "Paso 14 fallido: no fue posible dar clic en el boton 'Limpiar filtros'");
		testCase.addTextEvidenceCurrentStep("El sistema ejecuta la limpieza de todos los filtros de la pantalla.");

		/*
		 *                                             PASO 15
		 ***************************************************************************/
		addStep("Verificar el campo 'Rango de fechas' inmediatamente despues de dar clic en 'Limpiar filtros'");
		boolean resultStep15 = devEspecialesPage.validarRangoFechasVacioTrasLimpiar(true);
		assertTrue(resultStep15, "CA08 fallido: el campo 'Rango de fechas' no queda vacio tras dar clic en 'Limpiar filtros'");
		testCase.addTextEvidenceCurrentStep("El campo Rango de fechas queda vacio, sin valores previos (CA08 verificado - limpieza de rango de fechas).");

		/*
		 *                                             PASO 16
		 ***************************************************************************/
		addStep("Verificar los filtros opcionales CEDIS y Plaza inmediatamente despues de dar clic en 'Limpiar filtros'");
		boolean resultStep16Cedis = devEspecialesPage.validarFiltroCedisEstadoInicial(true);
		assertTrue(resultStep16Cedis, "CA08 fallido: el filtro CEDIS conserva un valor seleccionado tras 'Limpiar filtros'");
		boolean resultStep16Plaza = devEspecialesPage.validarFiltroPlazaEstadoInicial(true);
		assertTrue(resultStep16Plaza, "CA08 fallido: el filtro Plaza conserva un valor seleccionado tras 'Limpiar filtros'");
		testCase.addTextEvidenceCurrentStep("Los filtros opcionales CEDIS y Plaza quedan vacios, sin valores seleccionados (CA08 verificado - limpieza de filtros opcionales).");

		/*
		 *                                             PASO 17
		 ***************************************************************************/
		addStep("Verificar el estado del boton 'Consultar' inmediatamente despues de dar clic en 'Limpiar filtros'");
		boolean resultStep17 = devEspecialesPage.validarBotonConsultarDeshabilitadoSinRangoFechas(true);
		assertTrue(resultStep17, "CA08 fallido: el boton 'Consultar' no queda deshabilitado tras 'Limpiar filtros'");
		testCase.addTextEvidenceCurrentStep("El boton 'Consultar' se muestra deshabilitado (CA08 verificado - deshabilitacion).");

		/*
		 *                                             PASO 18
		 ***************************************************************************/
		addStep("Verificar el grid de resultados inmediatamente despues de dar clic en 'Limpiar filtros'");
		boolean resultStep18 = devEspecialesPage.validarGridEstadoInicialTrasLimpiar(true);
		assertTrue(resultStep18, "CA08 fallido: el grid no se restablece al estado inicial con la carga por defecto tras 'Limpiar filtros'");
		testCase.addTextEvidenceCurrentStep("El grid se restablece a su estado inicial con la carga por defecto (CA08 verificado - grid restablecido).");
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
		return "*1. Perfil ADMIN (usuario 5555555) disponible en ambiente QA con registros disponibles para filtros CEDIS y Plaza.\r\n"
				+ "*2. Liga de Portal Cometa.";
	}

	@Override
	public String setTestDescription() {
		return "Valida que al hacer clic en Limpiar filtros de Devoluciones Especiales el sistema limpia el rango de fechas y filtros opcionales, deshabilita Consultar y restablece el grid a estado inicial. Perfil ADMIN.";
	}

	@Override
	public String setTestDesigner() {
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_364_LimpiarFiltrosDevEspecialesCA08ADMIN_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}
