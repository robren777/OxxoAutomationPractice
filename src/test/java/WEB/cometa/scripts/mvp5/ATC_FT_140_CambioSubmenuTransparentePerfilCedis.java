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

/**DESCRIPCION: Verifica que el cambio de submenu sea transparente sin requerir nuevos accesos ni configuraciones para el perfil CEDIS.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_140 CA04 - Verificar que el cambio de submenu es transparente sin requerir nuevos accesos ni configuraciones - perfil CEDIS
* 
*PREREQUISITOS:
*1. Script de reorganizacion aplicado y perfil CEDIS (usuario 6666666) disponible con su configuracion existente.
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Usuario CEDIS 6666666.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_140_CambioSubmenuTransparentePerfilCedis extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_140_CambioSubmenuTransparentePerfilCedis_test(HashMap<String, String> data) throws Exception {
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
		addStep("Precondicion: declarar el script aplicado y validar el perfil CEDIS con sus credenciales configuradas");
		boolean resultPrecondition = accessControl.validarPrecondicionPerfil(url, user, "6666666", perfil, password);
		assertTrue(resultPrecondition,
				"Precondicion fallida: el perfil CEDIS, el usuario 6666666 o sus credenciales no estan disponibles");
		testCase.addTextEvidenceCurrentStep(
				"Prerrequisito declarado: script aplicado. Perfil CEDIS (usuario 6666666) validado con su configuracion existente.");

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
		addStep("Dar clic en la opcion 'CEDIS' del menu principal y luego en 'Recepcion y Devolucion'");
		boolean submenuAbiertoStep6 = pc4.clicBotonCometaCedis(true);
		boolean recepcionAbiertaStep6 = menu.clicRecepcionYDevolucion();
		boolean resultStep6 = submenuAbiertoStep6 && recepcionAbiertaStep6;
		assertTrue(resultStep6,
				"Paso 6 fallido: Recepcion de Transferencias y Devoluciones no carga o muestra un error de acceso");
		testCase.addTextEvidenceCurrentStep(
				"El portal navega directamente a la pantalla Recepcion de Transferencias y Devoluciones sin solicitar credenciales adicionales, sin mostrar pantalla de error de acceso (403) y sin requerir ninguna configuracion adicional del usuario.");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Seleccionar un rango de fechas valido en los filtros historicos y dar clic en el boton Consultar de filtros historicos");
		boolean rangoCapturadoStep7 = recepcion.capturaRangoHistoricoConDatos(user);
		boolean consultaEjecutadaStep7 = recepcion.consultaHistoricaConResultados();
		boolean resultStep7 = rangoCapturadoStep7 && consultaEjecutadaStep7;
		assertTrue(resultStep7, "Paso 7 fallido: la consulta historica no muestra registros para el perfil CEDIS");
		testCase.addTextEvidenceCurrentStep(
				"El sistema ejecuta la consulta correctamente con un rango historico valido obtenido desde BD para el usuario en prueba. El grid muestra los registros correspondientes a los CEDIS asignados al perfil. No se solicitan permisos adicionales.");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Dar clic en el icono FUE (rojo) de la primera fila del grid para intentar descargar el documento");
		boolean resultStep8 = recepcion.abrePrimerFueYConservaCanastilla();
		assertTrue(resultStep8, "Paso 8 fallido: el documento FUE no se descarga ni se muestra correctamente");
		testCase.addTextEvidenceCurrentStep(
				"El sistema descarga o muestra el documento FUE correctamente, identico al comportamiento previo al cambio de nombre en el menu. No aparece error de acceso ni solicitud de nuevos permisos.");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Dar clic en el boton 'Liquidacion de ruta' en la parte superior de la pantalla");
		boolean resultStep9 = recepcion.abreLiquidacionRuta();
		assertTrue(resultStep9, "Paso 9 fallido: Liquidacion de Ruta no carga correctamente");
		testCase.addTextEvidenceCurrentStep(
				"El sistema navega a la pantalla Liquidacion de Ruta correctamente sin requerir nuevos accesos ni configuracion adicional.");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Regresar a la pantalla anterior usando el menu de navegacion y dar clic en 'Devoluciones Especiales'");
		boolean resultStep10 = recepcion.regresaYAbreDevolucionesEspeciales();
		assertTrue(resultStep10, "Paso 10 fallido: Devoluciones Especiales no carga correctamente");
		testCase.addTextEvidenceCurrentStep(
				"El sistema navega a la pantalla Devoluciones Especiales correctamente. Todas las funcionalidades del modulo CEDIS siguen accesibles sin cambios en los permisos del usuario.");

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("Cerrar sesion usando la opcion de logout y volver a iniciar sesion con las mismas credenciales");
		boolean logoutStep11 = accessControl.cerrarSesionPortalYPrepararReLogin(url, 35);
		assertTrue(logoutStep11,
				"Paso 11 fallido: no se logro cerrar sesion correctamente o no se mostro login de Access Control para reingresar");
		pc4.PrivatePortal();
		boolean sesionIniciadaStep11 = pc4.IniciarSesionNoToken(user, password, true);
		boolean portalSeleccionadoStep11 = pc4.seleccionarPortalCometaQA(true);
		boolean submenuAbiertoStep11 = pc4.clicBotonCometaCedis(true);
		boolean estructuraValidadaStep11 = menu.validaSubmenuSoloRecepcionYDevolucion();
		boolean resultStep11 = logoutStep11 && sesionIniciadaStep11 && portalSeleccionadoStep11
				&& submenuAbiertoStep11 && estructuraValidadaStep11;
		assertTrue(resultStep11, "Paso 11 fallido: el relogin o la estructura del menu CEDIS no son correctos");
		testCase.addTextEvidenceCurrentStep(
				"El usuario puede iniciar sesion nuevamente con las mismas credenciales sin necesidad de que el administrador realice ninguna configuracion adicional. El menu CEDIS muestra la misma estructura correcta tras el re-login.");
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
		return "*1. Script de reorganizacion aplicado y perfil CEDIS (usuario 6666666) disponible con su configuracion existente.      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Verifica que el cambio de submenu sea transparente sin requerir nuevos accesos ni configuraciones para el perfil CEDIS.";
	}

	@Override
	public String setTestDesigner() {
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_140_CambioSubmenuTransparentePerfilCedis_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}