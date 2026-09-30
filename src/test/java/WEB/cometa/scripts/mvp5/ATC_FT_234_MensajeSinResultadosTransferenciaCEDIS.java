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

/**DESCRIPCION: Verifica el mensaje exacto cuando la consulta por Transferencia no arroja resultados con perfil CEDIS.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_234 CA04 - Verificar mensaje cuando la consulta por Transferencia no arroja resultados - perfil CEDIS
* 
*PREREQUISITOS:
*1. Ambiente QA disponible y perfil CEDIS con acceso al modulo CEDIS.
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Usuario CEDIS configurado en el data provider y transferencia inexistente 0000000000.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_234_MensajeSinResultadosTransferenciaCEDIS extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_234_MensajeSinResultadosTransferenciaCEDIS_test(
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
				"URL, perfil CEDIS y credenciales configurados para validar una consulta sin resultados.");

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
				&& recepcion.validaCargaInicialFiltrosRapidosEHistoricos(7);
		assertTrue(resultStep7, "Paso 7 fallido: no carga Recepcion de Transferencias y Devoluciones");
		testCase.addTextEvidenceCurrentStep(
				"La pantalla muestra las secciones de filtros rapidos y filtros historicos.");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Dar clic en el campo 'Transferencia' en la seccion de filtros rapidos");
		boolean resultStep8 = recepcion.enfocaCampoTransferencia();
		assertTrue(resultStep8, "Paso 8 fallido: el campo Transferencia no recibe el foco");
		testCase.addTextEvidenceCurrentStep("El campo Transferencia recibe el foco.");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Escribir un numero de transferencia que no existe en el sistema: '0000000000'");
		boolean resultStep9 = recepcion.capturaTransferenciaInexistente();
		assertTrue(resultStep9, "Paso 9 fallido: el campo no conserva la transferencia inexistente 0000000000");
		testCase.addTextEvidenceCurrentStep(
				"El campo muestra el valor '0000000000' sin errores de formato.");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Dar clic en el boton 'Consultar' de la seccion de filtros rapidos");
		boolean resultStep10 = recepcion.consultaTransferenciaSinResultados();
		assertTrue(resultStep10, "Paso 10 fallido: la consulta negativa no termina sin registros");
		testCase.addTextEvidenceCurrentStep(
				"El sistema ejecuta la consulta con el valor ingresado.");

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("Observar el contenido del area del grid tras ejecutar la consulta");
		boolean resultStep11 = recepcion.validaGridSinRegistrosConAviso();
		assertTrue(resultStep11, "Paso 11 fallido: el grid muestra registros o no presenta el aviso esperado");
		testCase.addTextEvidenceCurrentStep(
				"El grid no muestra registros y el area de resultados presenta el mensaje sin informacion.");

		/*
		 *                                             PASO 12
		 ***************************************************************************/
		addStep("Confirmar que el mensaje mostrado corresponde exactamente al texto definido en la HU: 'No se encontro informacion con el filtro seleccionado.'");
		boolean resultStep12 = recepcion.validaMensajeSinResultadosExacto();
		assertTrue(resultStep12, "Paso 12 fallido: el mensaje no coincide exactamente con el definido en CA04");
		testCase.addTextEvidenceCurrentStep(
				"El mensaje es exactamente 'No se encontro informacion con el filtro seleccionado.' sin variaciones.");
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
		return "Verifica que una consulta sin resultados por Transferencia muestre el mensaje exacto definido en CA04 con perfil CEDIS.";
	}

	@Override
	public String setTestDesigner() {
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_234_MensajeSinResultadosTransferenciaCEDIS_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}