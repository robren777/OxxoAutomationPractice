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

/**DESCRIPCION: Valida que al seleccionar filtros opcionales sin definir un rango de fechas el boton 'Consultar'
 * permanece deshabilitado y muestra un tooltip con el mensaje de fecha obligatoria; y que al capturar
 * un rango de fechas valido el boton se habilita automaticamente y el tooltip deja de mostrarse. Perfil ADMIN.
 *
 *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_366 Verificar tooltip de fecha obligatoria y habilitacion automatica de Consultar - perfil ADMIN
 *
 *PREREQUISITOS:
 *1. Perfil ADMIN (usuario 5555555) disponible en ambiente QA. Pantalla Devoluciones Especiales recien cargada sin filtros.
 *2. Liga de Portal Cometa.
 *
 *INSUMOS:
 *Usuario ADMIN configurado en el data provider.
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/Login
 *
 * @author Mauricio Gurrola
 * @date Agosto 2026
 */
public class ATC_FT_366_TooltipFechaObligatoriaConsultarCA09ADMIN extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_366_TooltipFechaObligatoriaConsultarCA09ADMIN_test(HashMap<String, String> data) throws Exception {
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
		addStep("Seleccionar un valor en el filtro opcional 'Tienda' sin capturar un rango de fechas");
		boolean resultStep10 = devEspecialesPage.seleccionarPrimeraOpcionFiltroTienda(true);
		assertTrue(resultStep10, "Paso 10 fallido: no fue posible seleccionar un valor en el filtro opcional 'Tienda'");
		testCase.addTextEvidenceCurrentStep("El filtro Tienda muestra el valor seleccionado.");

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("Observar el estado del boton 'Consultar' despues de seleccionar el filtro opcional sin rango de fechas");
		boolean resultStep11 = devEspecialesPage.validarBotonConsultarDeshabilitadoSinRangoFechas(true);
		assertTrue(resultStep11, "CA09 fallido: el boton 'Consultar' no permanece deshabilitado con filtro opcional seleccionado y sin rango de fechas");
		testCase.addTextEvidenceCurrentStep("El boton 'Consultar' permanece deshabilitado (CA09 verificado - permanece deshabilitado).");

		/*
		 *                                             PASO 12
		 ***************************************************************************/
		addStep("Posicionar el cursor del mouse sobre el boton 'Consultar' deshabilitado sin hacer clic");
		boolean resultStep12 = devEspecialesPage.validarTooltipConsultarRangoFechasObligatorio(true);
		assertTrue(resultStep12, "CA09 fallido: el tooltip con el mensaje 'Rango de fechas obligatorio' no se muestra al posicionar el cursor sobre el boton Consultar deshabilitado");
		testCase.addTextEvidenceCurrentStep("Se muestra un tooltip con el mensaje 'Rango de fechas obligatorio' (CA09 verificado - tooltip).");

		/*
		 *                                             PASO 13
		 ***************************************************************************/
		addStep("Seleccionar un rango de fechas valido en el campo 'Rango de fechas'");
		boolean resultStep13 = devEspecialesPage.seleccionarMismoDiaRangoFechas(true);
		assertTrue(resultStep13, "Paso 13 fallido: no fue posible seleccionar un rango de fechas valido en el campo 'Rango de fechas'");
		testCase.addTextEvidenceCurrentStep("El campo Rango de fechas muestra el rango seleccionado.");

		/*
		 *                                             PASO 14
		 ***************************************************************************/
		addStep("Observar el estado del boton 'Consultar' inmediatamente despues de capturar el rango de fechas valido");
		boolean resultStep14 = devEspecialesPage.validarBotonConsultarHabilitadoPorRangoFechas(true);
		assertTrue(resultStep14, "CA09 fallido: el boton 'Consultar' no se habilita automaticamente al capturar un rango de fechas valido");
		testCase.addTextEvidenceCurrentStep("El boton 'Consultar' se habilita automaticamente (CA09 verificado - habilitacion automatica).");

		/*
		 *                                             PASO 15
		 ***************************************************************************/
		addStep("Posicionar el cursor del mouse sobre el boton 'Consultar' habilitado sin hacer clic");
		boolean resultStep15 = devEspecialesPage.validarTooltipConsultarAusente(true);
		assertTrue(resultStep15, "CA09 fallido: el tooltip 'Rango de fechas obligatorio' sigue mostrando con el boton Consultar habilitado");
		testCase.addTextEvidenceCurrentStep("El tooltip 'Rango de fechas obligatorio' ya no se muestra (CA09 verificado - tooltip removido).");
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
		return "*1. Perfil ADMIN (usuario 5555555) disponible en ambiente QA. Pantalla Devoluciones Especiales recien cargada sin filtros.\r\n"
				+ "*2. Liga de Portal Cometa.";
	}

	@Override
	public String setTestDescription() {
		return "Valida que al seleccionar filtros opcionales sin fecha el boton Consultar permanece deshabilitado con tooltip de fecha obligatoria; y al capturar fecha valida el boton se habilita y el tooltip desaparece. Perfil ADMIN.";
	}

	@Override
	public String setTestDesigner() {
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_366_TooltipFechaObligatoriaConsultarCA09ADMIN_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}
