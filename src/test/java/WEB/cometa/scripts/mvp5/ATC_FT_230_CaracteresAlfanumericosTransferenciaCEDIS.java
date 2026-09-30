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

/**DESCRIPCION: Verifica que el filtro Transferencia acepte caracteres alfanumericos con perfil CEDIS.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_230 CA02 - Verificar aceptacion de caracteres alfanumericos en el campo Transferencia - perfil CEDIS
* 
*PREREQUISITOS:
*1. Ambiente QA disponible y perfil CEDIS con acceso al modulo CEDIS.
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Usuario CEDIS configurado en el data provider.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_230_CaracteresAlfanumericosTransferenciaCEDIS extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_230_CaracteresAlfanumericosTransferenciaCEDIS_test(
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
				"URL, perfil CEDIS y credenciales configurados para validar caracteres alfanumericos.");

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
		addStep("Dar clic en el campo 'Transferencia' en la seccion de filtros rapidos para posicionar el cursor");
		boolean resultStep8 = recepcion.enfocaCampoTransferencia();
		assertTrue(resultStep8, "Paso 8 fallido: el campo Transferencia no recibe el foco");
		testCase.addTextEvidenceCurrentStep(
				"El campo Transferencia recibe el foco y el cursor queda posicionado dentro del campo.");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Escribir el valor '4020504111' (solo numeros) en el campo 'Transferencia'");
		boolean resultStep9 = recepcion.capturaYValidaTransferencia("4020504111");
		assertTrue(resultStep9, "Paso 9 fallido: el campo no acepta el valor numerico 4020504111");
		testCase.addTextEvidenceCurrentStep(
				"El campo muestra el valor '4020504111'. Los caracteres numericos son aceptados sin errores ni bloqueos.");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Limpiar el contenido del campo 'Transferencia' seleccionando la opcion limpiar filtro");
		boolean resultStep10 = recepcion.limpiaFiltrosRapidos();
		assertTrue(resultStep10, "Paso 10 fallido: el campo Transferencia no queda vacio");
		testCase.addTextEvidenceCurrentStep("El campo Transferencia queda vacio.");

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("Escribir el valor 'ABC123' (letras mayusculas y numeros) en el campo 'Transferencia'");
		boolean resultStep11 = recepcion.capturaYValidaTransferencia("ABC123");
		assertTrue(resultStep11, "Paso 11 fallido: el campo no acepta el valor ABC123");
		testCase.addTextEvidenceCurrentStep(
				"El campo muestra el valor 'ABC123'. Los caracteres alfanumericos en mayuscula son aceptados sin errores.");

		/*
		 *                                             PASO 12
		 ***************************************************************************/
		addStep("Limpiar el contenido del campo 'Transferencia' seleccionando la opcion limpiar filtro");
		boolean resultStep12 = recepcion.limpiaFiltrosRapidos();
		assertTrue(resultStep12, "Paso 12 fallido: el campo Transferencia no queda vacio");
		testCase.addTextEvidenceCurrentStep("El campo Transferencia queda vacio.");

		/*
		 *                                             PASO 13
		 ***************************************************************************/
		addStep("Escribir el valor 'abc456' (letras minusculas y numeros) en el campo 'Transferencia'");
		boolean resultStep13 = recepcion.capturaYValidaTransferencia("abc456");
		assertTrue(resultStep13, "Paso 13 fallido: el campo no acepta el valor abc456");
		testCase.addTextEvidenceCurrentStep(
				"El campo muestra el valor 'abc456'. Los caracteres alfanumericos en minuscula son aceptados sin errores.");

		/*
		 *                                             PASO 14
		 ***************************************************************************/
		addStep("Limpiar el campo seleccionando la opcion limpiar filtro y escribir el valor 'Transfer2026XYZ' (combinacion de mayusculas, minusculas y numeros)");
		boolean resultStep14 = recepcion.limpiaFiltrosRapidos()
				&& recepcion.capturaYValidaTransferencia("Transfer2026XYZ");
		assertTrue(resultStep14,
				"Paso 14 fallido: el campo no queda limpio o no acepta el valor Transfer2026XYZ");
		testCase.addTextEvidenceCurrentStep(
				"El campo muestra el valor 'Transfer2026XYZ'. La combinacion de caracteres alfanumericos es aceptada correctamente.");

		/*
		 *                                             PASO 15
		 ***************************************************************************/
		addStep("Verificar que el campo no muestra errores de validacion ni caracteres bloqueados al escribir valores alfanumericos validos");
		boolean resultStep15 = recepcion.validaSinErroresTransferencia();
		assertTrue(resultStep15, "Paso 15 fallido: se muestran errores o caracteres bloqueados");
		testCase.addTextEvidenceCurrentStep(
				"No se muestran mensajes de error ni indicadores visuales de rechazo de caracteres alfanumericos.");
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
		return "Verifica que el filtro Transferencia acepte numeros, letras mayusculas y letras minusculas con perfil CEDIS.";
	}

	@Override
	public String setTestDesigner() {
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_230_CaracteresAlfanumericosTransferenciaCEDIS_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}