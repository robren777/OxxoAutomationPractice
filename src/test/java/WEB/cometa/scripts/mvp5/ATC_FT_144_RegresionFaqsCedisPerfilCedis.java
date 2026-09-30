package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.accessControlPage;
import WEB.cometa.om.faqsCedisPage;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCION: Verifica que FAQS CEDIS siga accesible y funcional tras el cambio de submenu CEDIS.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_144 Regresion - Verificar que el menu FAQS CEDIS sigue accesible y funcional tras el cambio de submenu CEDIS - perfil CEDIS
* 
*PREREQUISITOS:
*1. Script de reorganizacion aplicado y perfil CEDIS (usuario 6666666) disponible en QA.
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Usuario CEDIS 6666666.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_144_RegresionFaqsCedisPerfilCedis extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_144_RegresionFaqsCedisPerfilCedis_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		accessControlPage accessControl = new accessControlPage(u, testCase, pc4);
		faqsCedisPage faqsCedis = new faqsCedisPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "CEDIS";
		String password = pc4.obtenerPasswordPorUsuario(user);

		/*
		 * PRECONDICION
		 ***************************************************************************/
		addStep("Precondicion: declarar el script aplicado y validar el perfil CEDIS con sus credenciales configuradas");
		boolean resultPrecondition = accessControl.validarPrecondicionPerfil(url, user, "6666666", perfil, password);
		assertTrue(resultPrecondition,
				"Precondicion fallida: el perfil CEDIS, el usuario 6666666 o sus credenciales no estan disponibles");
		testCase.addTextEvidenceCurrentStep(
				"Prerrequisito declarado: script aplicado. Perfil CEDIS (usuario 6666666) validado para comprobar FAQS CEDIS.");

		/*
		 *                                             PASO 1
		 ***************************************************************************/
		addStep("Ingresar la URL " + url + " en el campo de direcciones del navegador");
		boolean resultStep1 = pc4.IngresarURL(url, true);
		assertTrue(resultStep1, "Paso 1 fallido: no se muestra el inicio de sesion de Access Control");
		testCase.addTextEvidenceCurrentStep("El sistema muestra la pantalla de inicio de sesion del portal Access Control.");
		pc4.PrivatePortal();

		/*
		 *                                             PASOS 2, 3 Y 4
		 ***************************************************************************/
		addStep("Escribir '" + user + "' en el campo Usuario, escribir la contrasena del perfil y dar clic en el boton Iniciar sesion");
		boolean resultStep2 = pc4.IniciarSesionNoToken(user, password, true);
		assertTrue(resultStep2,
				"Los pasos 2, 3 y 4 fallaron: el sistema no valido las credenciales ni redirigio a la seleccion de portales");
		testCase.addTextEvidenceCurrentStep(
				"El campo Usuario recibe '" + user + "', la contrasena se captura enmascarada y el sistema inicia sesion.");

		/*
		 *                                             PASO 5
		 ***************************************************************************/
		addStep("Dar clic en la opcion Portal COMETA");
		boolean resultStep5 = pc4.seleccionarPortalCometaQA(true);
		assertTrue(resultStep5, "Paso 5 fallido: no se muestran las opciones del perfil CEDIS");
		testCase.addTextEvidenceCurrentStep(
				"Se visualiza la pantalla principal del portal Cometa con las opciones: CEDIS y FAQS CEDIS.");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Identificar en la barra de navegacion superior la opcion 'FAQS CEDIS'");
		boolean resultStep6 = faqsCedis.validarOpcionFaqsCedisVisible();
		assertTrue(resultStep6, "Paso 6 fallido: FAQS CEDIS no esta visible para el perfil CEDIS");
		testCase.addTextEvidenceCurrentStep(
				"La opcion 'FAQS CEDIS' es visible en la barra de navegacion superior, accesible para el perfil CEDIS.");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Dar clic en la opcion 'FAQS CEDIS' de la barra de navegacion");
		boolean resultStep7 = faqsCedis.abrirYValidarFaqsCedis();
		assertTrue(resultStep7, "Paso 7 fallido: no se navega a Preguntas Frecuentes de CEDIS");
		testCase.addTextEvidenceCurrentStep(
				"El sistema navega a la pantalla de Preguntas Frecuentes de CEDIS sin errores.");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Verificar que la pantalla FAQS CEDIS muestra el contenido esperado de preguntas frecuentes");
		boolean resultStep8 = faqsCedis.validarContenidoFaqsCedis();
		assertTrue(resultStep8, "Paso 8 fallido: FAQS CEDIS no muestra el listado esperado");
		testCase.addTextEvidenceCurrentStep(
				"La pantalla FAQS CEDIS carga correctamente con el listado de preguntas frecuentes. La funcionalidad de FAQS CEDIS no fue afectada por la reorganizacion del submenu.");
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
		return "*1. Script de reorganizacion aplicado y perfil CEDIS (usuario 6666666) disponible en QA.      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Verifica que FAQS CEDIS siga accesible y funcional tras el cambio de submenu CEDIS.";
	}

	@Override
	public String setTestDesigner() {
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_144_RegresionFaqsCedisPerfilCedis_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}