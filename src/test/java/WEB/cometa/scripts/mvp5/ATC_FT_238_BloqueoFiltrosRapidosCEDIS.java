package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.accessControlPage;
import WEB.cometa.om.menuCedisPage;
import WEB.cometa.om.recepcionTransferenciasPage;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCION: Verifica que los filtros rapidos se deshabilitan cuando los filtros historicos estan activos con perfil CEDIS.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_238 CA06 - Verificar que filtros rapidos se deshabilitan cuando filtros historicos estan activos - perfil CEDIS
* 
*PREREQUISITOS:
*1. Ambiente QA disponible y perfil CEDIS con acceso al modulo CEDIS.
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Usuario CEDIS configurado en el data provider y rango historico con datos obtenido desde BD.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_238_BloqueoFiltrosRapidosCEDIS extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_238_BloqueoFiltrosRapidosCEDIS_test(
			HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		accessControlPage accessControl = new accessControlPage(u, testCase, pc4);
		menuCedisPage menu = new menuCedisPage(u, testCase);
		recepcionTransferenciasPage recepcion = new recepcionTransferenciasPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "CEDIS";
		String password = pc4.obtenerPasswordPorUsuario(user);

		/*
		 * PRECONDICION
		 ***************************************************************************/
		addStep("Precondicion: validar URL, usuario CEDIS y credenciales configuradas");
		boolean resultPrecondition = accessControl.validarPrecondicionPerfil(
				url, user, "6666666", perfil, password);
		assertTrue(resultPrecondition,
				"Precondicion fallida: la URL, el usuario CEDIS o sus credenciales no estan configurados");
		testCase.addTextEvidenceCurrentStep(
				"URL, perfil CEDIS y credenciales configurados para validar filtros historicos.");

		/*
		 *                                             PASO 1
		 ***************************************************************************/
		addStep("Ingresar la URL " + url + " en el campo de direcciones del navegador");
		boolean resultStep1 = pc4.IngresarURL(url, true);
		assertTrue(resultStep1, "Paso 1 fallido: no se muestra el inicio de sesion de Access Control");
		testCase.addTextEvidenceCurrentStep(
				"El sistema muestra la pantalla de inicio de sesion del portal Access Control.");
		pc4.PrivatePortal();

		/*
		 *                                             PASO 2
		 ***************************************************************************/
		addStep("Escribir '6666666' en el campo Usuario");
		boolean resultStep2 = accessControl.capturarYValidarUsuario(user);
		assertTrue(resultStep2, "Paso 2 fallido: el campo Usuario no muestra el valor 6666666");
		testCase.addTextEvidenceCurrentStep("El campo Usuario muestra el valor '6666666'.");

		/*
		 *                                             PASO 3
		 ***************************************************************************/
		addStep("Escribir la contrasena del perfil en el campo Contrasena");
		boolean resultStep3 = accessControl.capturarYValidarPassword(password);
		assertTrue(resultStep3, "Paso 3 fallido: la contrasena no queda capturada y enmascarada");
		testCase.addTextEvidenceCurrentStep("El campo Contrasena muestra los caracteres enmascarados.");

		/*
		 *                                             PASO 4
		 ***************************************************************************/
		addStep("Dar clic en el boton Iniciar sesion");
		boolean resultStep4 = accessControl.iniciarSesionYValidarAcceso();
		assertTrue(resultStep4, "Paso 4 fallido: las credenciales no permiten iniciar sesion");
		testCase.addTextEvidenceCurrentStep(
				"El sistema valida las credenciales, inicia sesion y muestra las opciones General y Portal COMETA.");

		/*
		 *                                             PASO 5
		 ***************************************************************************/
		addStep("Dar clic en la opcion Portal COMETA");
		boolean resultStep5 = pc4.seleccionarPortalCometaQA(true);
		assertTrue(resultStep5, "Paso 5 fallido: Portal COMETA no muestra las opciones del perfil CEDIS");
		testCase.addTextEvidenceCurrentStep(
				"Se visualiza la pantalla principal del portal Cometa con las opciones CEDIS y FAQS CEDIS.");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Dar clic en la tarjeta CEDIS");
		boolean resultStep6 = pc4.clicBotonCometaCedis(true);
		assertTrue(resultStep6, "Paso 6 fallido: no se muestra la opcion Recepcion y Devoluciones");
		testCase.addTextEvidenceCurrentStep(
				"Se visualiza el modulo CEDIS con la opcion Recepcion y Devoluciones.");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Dar clic en la tarjeta Recepcion y Devoluciones");
		boolean resultStep7 = menu.clicRecepcionYDevolucion()
				&& recepcion.validaPantallaYSeccionesRecepcion(7);
		assertTrue(resultStep7, "Paso 7 fallido: no carga Recepcion de Transferencias y Devoluciones");
		testCase.addTextEvidenceCurrentStep(
				"Se visualiza la pantalla Recepcion de Transferencias y Devoluciones con la seccion de filtros rapidos y la seccion de filtros historicos.");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Localizar la seccion de filtros historicos en la pantalla Recepcion de Transferencias y Devoluciones");
		boolean resultStep8 = recepcion.validaFiltrosHistoricos();
		assertTrue(resultStep8, "Paso 8 fallido: la seccion de filtros historicos no esta visible");
		testCase.addTextEvidenceCurrentStep(
				"La seccion de filtros historicos es visible con sus campos de fecha y filtros adicionales disponibles.");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Seleccionar una fecha inicio con datos en el campo Fecha Inicio de la seccion de filtros historicos");
		boolean resultStep9 = recepcion.seleccionaFechaInicioHistoricaConDatosDesdeCalendario(user);
		assertTrue(resultStep9, "Paso 9 fallido: no fue posible seleccionar la Fecha Inicio con datos");
		testCase.addTextEvidenceCurrentStep(
				"El campo Fecha Inicio muestra la fecha dinamica con datos para el supplier CEDIS.");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Seleccionar la fecha fin con datos en el campo Fecha Fin de la seccion de filtros historicos");
		boolean resultStep10 = recepcion.seleccionaFechaFinHistoricaConDatosDesdeCalendario();
		assertTrue(resultStep10, "Paso 10 fallido: no fue posible seleccionar la Fecha Fin con datos");
		testCase.addTextEvidenceCurrentStep(
				"El campo Fecha Fin muestra la fecha dinamica con datos y Consultar queda habilitado.");

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("Dar clic en el boton 'Consultar' de la seccion de filtros historicos");
		boolean resultStep11 = recepcion.consultaHistoricaConResultados();
		assertTrue(resultStep11, "Paso 11 fallido: la consulta historica no muestra resultados");
		testCase.addTextEvidenceCurrentStep(
				"El sistema ejecuta la consulta historica y el grid muestra resultados del periodo seleccionado.");

		/*
		 *                                             PASO 12
		 ***************************************************************************/
		addStep("Verificar el estado visual de los filtros rapidos");
		boolean resultStep12 = recepcion.validaFiltrosRapidosDeshabilitados();
		assertTrue(resultStep12, "Paso 12 fallido: Canastilla o Transferencia permanece habilitada");
		testCase.addTextEvidenceCurrentStep(
				"Canastilla y Transferencia aparecen visualmente deshabilitadas y atenuadas.");

		/*
		 *                                             PASO 13
		 ***************************************************************************/
		addStep("Verificar el estado visual de los botones 'Consultar' y 'Limpiar filtros' de la seccion de filtros rapidos");
		boolean resultStep13 = recepcion.validaBotonesRapidosDeshabilitados();
		assertTrue(resultStep13, "Paso 13 fallido: Consultar o Limpiar filtros permanece habilitado");
		testCase.addTextEvidenceCurrentStep(
				"Los botones Consultar y Limpiar filtros rapidos aparecen visualmente deshabilitados.");

		/*
		 *                                             PASO 14
		 ***************************************************************************/
		addStep("Intentar dar clic en el boton 'Consultar' de la seccion de filtros rapidos mientras los filtros historicos estan activos");
		boolean resultStep14 = recepcion.intentaConsultaRapidaBloqueadaYValidaTooltip();
		assertTrue(resultStep14, "Paso 14 fallido: no se muestra el tooltip exacto de bloqueo");
		testCase.addTextEvidenceCurrentStep(
				"El sistema muestra el tooltip exacto 'Para usar los filtros rapidos, limpia primero los filtros historicos.' y no ejecuta la consulta rapida.");

		/*
		 *                                             PASO 15
		 ***************************************************************************/
		addStep("Verificar que el grid sigue mostrando los resultados de la consulta historica activa sin modificaciones");
		boolean resultStep15 = recepcion.validaGridSinCambios();
		assertTrue(resultStep15, "Paso 15 fallido: el grid cambio tras interactuar con Consultar rapido");
		testCase.addTextEvidenceCurrentStep(
				"El grid mantiene los resultados de la consulta historica sin modificaciones.");
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
		return "*1. Ambiente QA disponible y perfil CEDIS con acceso al modulo CEDIS.      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Verifica que los filtros rapidos se deshabilitan y bloquean su consulta cuando los filtros historicos estan activos con perfil CEDIS.";
	}

	@Override
	public String setTestDesigner() {
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_238_BloqueoFiltrosRapidosCEDIS_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}