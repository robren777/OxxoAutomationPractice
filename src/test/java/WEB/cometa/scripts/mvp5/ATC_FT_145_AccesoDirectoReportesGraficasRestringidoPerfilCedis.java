package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.accessControlPage;
import WEB.cometa.om.menuCedisPage;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCION: Verifica que Reportes y Graficas de CEDIS no sean accesibles directamente por URL para el perfil CEDIS.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_145 Test to Fail - Verificar que las opciones ocultadas Reportes y Graficas de CEDIS no son accesibles directamente por URL - perfil CEDIS
* 
*PREREQUISITOS:
*1. Script de reorganizacion aplicado y perfil CEDIS (usuario 6666666) con sesion activa.
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Usuario CEDIS 6666666 y rutas restringidas de Reportes y Graficas CEDIS.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_145_AccesoDirectoReportesGraficasRestringidoPerfilCedis extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_145_AccesoDirectoReportesGraficasRestringidoPerfilCedis_test(
			HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		accessControlPage accessControl = new accessControlPage(u, testCase, pc4);
		menuCedisPage menu = new menuCedisPage(u, testCase);

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
				"Prerrequisito declarado: script aplicado. Perfil CEDIS (usuario 6666666) validado para comprobar la autorizacion por URL.");

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
		/* PASO 5 ******************************************************************/
		addStep("Dar clic en la opcion Portal COMETA");
		boolean resultStep5 = pc4.seleccionarPortalCometaQA(true);
		assertTrue(resultStep5, "Paso 5 fallido: Portal COMETA no muestra las opciones del perfil CEDIS");
		testCase.addTextEvidenceCurrentStep("Se visualiza la pantalla principal con las opciones CEDIS y FAQS CEDIS.");
		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Dar clic en la opcion 'CEDIS' del menu principal de navegacion en la parte superior de la pantalla");
		boolean resultStep6 = pc4.clicBotonCometaCedis(true);
		assertTrue(resultStep6, "Paso 6 fallido: el sistema no despliega el submenu del modulo CEDIS");
		testCase.addTextEvidenceCurrentStep(
				"El sistema despliega el submenu del modulo CEDIS mostrando las opciones disponibles.");
		

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Estando autenticado como perfil CEDIS, intentar acceder directamente a la URL de la pantalla 'Reportes CEDIS' escribiendola en la barra de direcciones del navegador (la URL puede ser similar a: https://pruebasweb.oxxo.com/.../cedis/reportes o la ruta equivalente conocida por el equipo tecnico)");
		boolean resultStep7 = menu.validaAccesoDirectoRestringido("/PortalCometa/cedis/reportes");
		assertTrue(resultStep7, "Paso 6 fallido: Reportes CEDIS permanece accesible directamente por URL");
		testCase.addTextEvidenceCurrentStep(
				"El sistema no permite acceder a la pantalla 'Reportes CEDIS'. El portal muestra un mensaje de error 403 Forbidden o redirige al usuario a la pantalla de inicio sin mostrar el contenido de Reportes. La URL no es accesible directamente para el perfil CEDIS.");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Intentar acceder directamente a la URL de la pantalla 'Graficas CEDIS' escribiendola en la barra de direcciones del navegador (la URL puede ser similar a: https://pruebasweb.oxxo.com/.../cedis/graficas o la ruta equivalente)");
		boolean resultStep8 = menu.validaAccesoDirectoRestringido("/PortalCometa/cedis/graficas");
		assertTrue(resultStep8, "Paso 7 fallido: Graficas CEDIS permanece accesible directamente por URL");
		testCase.addTextEvidenceCurrentStep(
				"El sistema no permite acceder a la pantalla 'Graficas CEDIS'. El portal muestra un mensaje de error 403 Forbidden o redirige al usuario al inicio. El acceso directo por URL tampoco funciona para la pantalla de Graficas.");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Verificar que el sistema no muestra mensajes de error tecnicos (500) ni stacktraces ante el intento de acceso a URL restringidas");
		boolean resultStep9 = menu.validaRespuestaSinErroresTecnicos();
		assertTrue(resultStep9, "Paso 8 fallido: el acceso restringido muestra un error 500 o stacktrace");
		testCase.addTextEvidenceCurrentStep(
				"El sistema maneja el acceso no autorizado mostrando el mensaje definido: 'No tienes permiso para acceder a esta pagina. Si crees que esto es un error, por favor contacta al equipo de soporte.' o redireccionando apropiadamente. No se muestran errores tecnicos del servidor.");
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
		return "Verifica que Reportes y Graficas de CEDIS no sean accesibles directamente por URL para el perfil CEDIS.";
	}

	@Override
	public String setTestDesigner() {
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_145_AccesoDirectoReportesGraficasRestringidoPerfilCedis_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}