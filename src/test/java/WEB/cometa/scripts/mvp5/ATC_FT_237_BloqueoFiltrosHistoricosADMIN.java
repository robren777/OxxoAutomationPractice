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

/**DESCRIPCION: Verifica que los filtros historicos se deshabilitan cuando los filtros rapidos estan activos con perfil ADMIN.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_237 CA05 - Verificar que filtros historicos se deshabilitan cuando filtros rapidos estan activos - perfil ADMIN
* 
*PREREQUISITOS:
*1. Ambiente QA disponible, perfil ADMIN y al menos una transferencia valida en xxcm_historico_cedis_view.
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Usuario ADMIN configurado en el data provider y transferencia descubierta dinamicamente.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_237_BloqueoFiltrosHistoricosADMIN extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_237_BloqueoFiltrosHistoricosADMIN_test(
			HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		accessControlPage accessControl = new accessControlPage(u, testCase, pc4);
		menuCedisPage menu = new menuCedisPage(u, testCase);
		recepcionTransferenciasPage recepcion = new recepcionTransferenciasPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "ADMIN";
		String password = pc4.obtenerPasswordPorUsuario(user);

		/*
		 * PRECONDICION
		 ***************************************************************************/
		addStep("Precondicion: validar URL, usuario ADMIN y credenciales configuradas");
		boolean resultPrecondition = accessControl.validarPrecondicionPerfil(
				url, user, "5555555", perfil, password);
		assertTrue(resultPrecondition,
				"Precondicion fallida: la URL, el usuario ADMIN o sus credenciales no estan configurados");
		testCase.addTextEvidenceCurrentStep(
				"URL, perfil ADMIN y credenciales configurados; la transferencia se identificara dinamicamente.");

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
		addStep("Escribir '5555555' en el campo Usuario");
		boolean resultStep2 = accessControl.capturarYValidarUsuario(user);
		assertTrue(resultStep2, "Paso 2 fallido: el campo Usuario no muestra el valor 5555555");
		testCase.addTextEvidenceCurrentStep("El campo Usuario muestra el valor '5555555'.");

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
		assertTrue(resultStep5, "Paso 5 fallido: Portal COMETA no muestra las opciones del perfil ADMIN");
		testCase.addTextEvidenceCurrentStep(
				"Se visualiza la pantalla principal del portal Cometa con las opciones CEDIS, Directos, FAQS Directos y FAQS CEDIS.");

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
				&& recepcion.validaCargaInicialFiltrosRapidosEHistoricos(7);
		assertTrue(resultStep7, "Paso 7 fallido: no carga Recepcion de Transferencias y Devoluciones");
		testCase.addTextEvidenceCurrentStep(
				"Se visualiza la pantalla Recepcion de Transferencias y Devoluciones con la seccion de filtros rapidos y la seccion de filtros historicos.");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Escribir un numero de transferencia valido en el campo 'Transferencia' de la seccion de filtros rapidos");
		boolean resultStep8 = recepcion.descubreTransferenciaValidaGlobal()
				&& recepcion.capturaTransferenciaA();
		assertTrue(resultStep8, "Paso 8 fallido: no se descubrio o capturo una transferencia valida");
		testCase.addTextEvidenceCurrentStep(
				"El campo Transferencia muestra un valor valido obtenido con alcance global ADMIN.");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Dar clic en el boton 'Consultar' de la seccion de filtros rapidos");
		boolean resultStep9 = recepcion.consultaTransferenciaConResultados();
		assertTrue(resultStep9, "Paso 9 fallido: la busqueda por transferencia no muestra resultados");
		testCase.addTextEvidenceCurrentStep(
				"El sistema ejecuta la busqueda por transferencia. El grid muestra los resultados correspondientes.");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Verificar el estado visual de los filtros historicos");
		boolean resultStep10 = recepcion.validaFiltrosHistoricosDeshabilitados();
		assertTrue(resultStep10, "Paso 10 fallido: los filtros historicos no aparecen deshabilitados");
		testCase.addTextEvidenceCurrentStep(
				"Los filtros historicos aparecen visualmente deshabilitados, atenuados o con indicador de estado inactivo.");

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("Verificar el estado visual de los botones 'Consultar' y 'Limpiar filtros' de la seccion de filtros historicos");
		boolean resultStep11 = recepcion.validaBotonesHistoricosDeshabilitados();
		assertTrue(resultStep11, "Paso 11 fallido: los botones historicos no aparecen deshabilitados");
		testCase.addTextEvidenceCurrentStep(
				"Los botones Consultar y Limpiar filtros de la seccion de filtros historicos aparecen visualmente deshabilitados.");

		/*
		 *                                             PASO 12
		 ***************************************************************************/
		addStep("Intentar dar clic en el boton 'Consultar' de la seccion de filtros historicos mientras los filtros rapidos estan activos");
		boolean resultStep12 = recepcion.intentaConsultaHistoricaBloqueadaYValidaTooltip();
		assertTrue(resultStep12, "Paso 12 fallido: no se muestra el tooltip exacto o se ejecuta la consulta historica");
		testCase.addTextEvidenceCurrentStep(
				"El sistema muestra el tooltip exacto 'Para usar los filtros historicos, limpia primero los filtros rapidos.' y no ejecuta la consulta historica.");

		/*
		 *                                             PASO 13
		 ***************************************************************************/
		addStep("Verificar que el tooltip desaparece al retirar el cursor del boton 'Consultar' de filtros historicos");
		boolean resultStep13 = recepcion.validaTooltipBloqueoDesaparece();
		assertTrue(resultStep13, "Paso 13 fallido: el tooltip permanece visible al retirar el cursor");
		testCase.addTextEvidenceCurrentStep(
				"El tooltip se oculta al retirar el cursor y la pantalla conserva los filtros rapidos activos.");

		/*
		 *                                             PASO 14
		 ***************************************************************************/
		addStep("Verificar que el grid sigue mostrando los resultados del filtro rapido de Transferencia activo");
		boolean resultStep14 = recepcion.validaGridSinCambios();
		assertTrue(resultStep14, "Paso 14 fallido: el grid cambio tras interactuar con el boton deshabilitado");
		testCase.addTextEvidenceCurrentStep(
				"El grid mantiene los mismos N registros de la transferencia consultada sin modificaciones.");
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
		return "*1. Ambiente QA disponible, perfil ADMIN y transferencia valida en xxcm_historico_cedis_view.      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Verifica que los filtros historicos se deshabilitan y bloquean su consulta cuando los filtros rapidos estan activos con perfil ADMIN.";
	}

	@Override
	public String setTestDesigner() {
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_237_BloqueoFiltrosHistoricosADMIN_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}