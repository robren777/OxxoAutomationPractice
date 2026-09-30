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
import WEB.cometa.om.rutaLiquidacionPage;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCION: Valida que en la pantalla Devoluciones Especiales se muestre el boton 'Liquidacion de Ruta'
 * con el mismo estilo visual y que al hacer clic el sistema redirija a la pantalla Liquidacion de Ruta
 * en su estado inicial con perfil ADMIN.
 *
 *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_360 Verificar boton Liquidacion de Ruta en pantalla Devoluciones Especiales y navegacion en estado inicial - perfil ADMIN
 *
 *PREREQUISITOS:
 *1. Perfil ADMIN (usuario 5555555) disponible en ambiente QA.
 *2. Liga de Portal Cometa.
 *
 *INSUMOS:
 *Usuario ADMIN configurado en el data provider.
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/Login
 *
 * @author Mauricio Gurrola
 * @date Agosto 2026
 */
public class ATC_FT_360_BotonLiqRutaDesdeDevEspecialesCA05CA06ADMIN extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_360_BotonLiqRutaDesdeDevEspecialesCA05CA06ADMIN_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		accessControlPage accessControl = new accessControlPage(u, testCase, pc4);
		menuCedisPage menu = new menuCedisPage(u, testCase);
		recepcionTransferenciasPage recepcion = new recepcionTransferenciasPage(u, testCase);
		devolucionesEspecialesPage devEspecialesPage = new devolucionesEspecialesPage(u, testCase);
		rutaLiquidacionPage rutaPage = new rutaLiquidacionPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "ADMIN";
		String password = pc4.obtenerPasswordPorUsuario(user);

		/*
		 * PRECONDICION
		 ***************************************************************************/
		addStep("Precondicion: validar URL, usuario ADMIN y credenciales configuradas");
		boolean resultPrecondicion = accessControl.validarPrecondicionPerfil(url, user, "5555555", perfil, password);
		assertTrue(resultPrecondicion, "Precondicion fallida: la URL, el usuario ADMIN o sus credenciales no estan configurados");
		testCase.addTextEvidenceCurrentStep("URL, perfil ADMIN y credenciales configurados.");

		/*
		 *                                             PASO 1
		 ***************************************************************************/
		addStep("Ingresar la URL " + url + " en el campo de direcciones del navegador");
		boolean resultStep1 = pc4.IngresarURL(url, true);
		assertTrue(resultStep1, "Paso 1 fallido: no se muestra el inicio de sesion de Access Control");
		testCase.addTextEvidenceCurrentStep("El sistema muestra la pantalla de inicio de sesion del portal Access Control.");
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
		assertTrue(resultStep5, "Paso 5 fallido: no se visualiza la pantalla principal del portal Cometa con las tarjetas del perfil CEDIS");
		testCase.addTextEvidenceCurrentStep("Se visualiza la pantalla principal del portal Cometa con las opciones CEDIS y FAQS CEDIS.");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Dar clic en la tarjeta CEDIS");
		boolean resultStep6 = pc4.clicBotonCometaCedis(true);
		assertTrue(resultStep6, "Paso 6 fallido: no se visualiza la pantalla del modulo CEDIS con la sub-tarjeta Recepcion disponible");
		testCase.addTextEvidenceCurrentStep("Se visualiza el modulo CEDIS con la sub-tarjeta Recepcion disponible.");

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
		addStep("Dar clic en el boton 'Devoluciones Especiales'");
		boolean resultStep9 = devEspecialesPage.ingresarDevolucionesEspeciales(true);
		assertTrue(resultStep9, "Paso 9 fallido: el sistema no redirige correctamente a la pantalla Devoluciones Especiales o la pantalla destino no queda disponible");
		testCase.addTextEvidenceCurrentStep("El sistema navega a Devoluciones Especiales con filtros sin valores y grid con carga inicial por defecto.");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Verificar la presencia y ubicacion del boton 'Recepcion de Transferencias y Devoluciones' en la pantalla Devoluciones Especiales");
		boolean resultBotonRecepcion = devEspecialesPage.validaBotonRecepcionTransferenciasEnDevolucionesEspeciales(true);
		assertTrue(resultBotonRecepcion, "Paso CA05 fallido: no se visualiza el boton 'Recepcion de Transferencias y Devoluciones' en Devoluciones Especiales");
		testCase.addTextEvidenceCurrentStep("El boton 'Recepcion de Transferencias y Devoluciones' es visible en la pantalla Devoluciones Especiales.");

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("Verificar la presencia del boton 'Liquidacion de Ruta' y su estilo visual en la pantalla Devoluciones Especiales");
		boolean resultCA05 = rutaPage.validaBotonLiquidacionRutaDevolucionesEspeciales(true);
		assertTrue(resultCA05, "CA05 fallido: no se visualiza el boton 'Liquidacion de Ruta' en Devoluciones Especiales con el estilo visual esperado");
		testCase.addTextEvidenceCurrentStep("CA05 verificado: el boton 'Liquidacion de Ruta' se muestra junto al boton 'Recepcion de Transferencias y Devoluciones' respetando el mismo estilo visual.");

		/*
		 *                                             PASO 12
		 ***************************************************************************/
		addStep("Dar clic en el boton 'Liquidacion de Ruta'");
		boolean resultClicCA06 = rutaPage.clicBotonLiquidacionRutaDesdeDevolucionesEspeciales();
		assertTrue(resultClicCA06, "CA06 fallido: no se pudo hacer clic en el boton 'Liquidacion de Ruta' desde Devoluciones Especiales");
		testCase.addTextEvidenceCurrentStep("El sistema redirige a la pantalla Liquidacion de Ruta.");

		/*
		 *                                             PASO 13
		 ***************************************************************************/
		addStep("Verificar que la pantalla Liquidacion de Ruta se muestra en su estado inicial");
		boolean resultCA06 = rutaPage.validaEstadoInicialInactivoFiltrosYConsultar(true);
		assertTrue(resultCA06, "CA06 fallido: la pantalla Liquidacion de Ruta no muestra el estado inicial esperado tras la redireccion");
		testCase.addTextEvidenceCurrentStep("CA06 verificado: la pantalla Liquidacion de Ruta se muestra en su estado inicial sin resultados ni resumen de ruta previamente cargados.");

		/*
		 *                                             PASO 14
		 ***************************************************************************/
		addStep("Verificar que los filtros no conservan valores previos y que la consulta permanece inactiva en estado inicial");
		assertTrue(resultCA06, "CA06 fallido: la pantalla Liquidacion de Ruta conserva valores previos o permite consulta fuera del estado inicial");
		testCase.addTextEvidenceCurrentStep("CA06 verificado: filtros sin valores previos y accion de consulta en estado inicial segun comportamiento esperado.");
		testCase.addScreenShotCurrentStep(u, "CA06_Filtros_Sin_Valores_Consulta_Inactiva");
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
		return "*1. Perfil ADMIN (usuario 5555555) disponible en ambiente QA.\r\n"
				+ "*2. Liga de Portal Cometa.";
	}

	@Override
	public String setTestDescription() {
		return "Valida que en Devoluciones Especiales se muestre el boton 'Liquidacion de Ruta' con el mismo estilo visual y que al hacer clic el sistema redirija a Liquidacion de Ruta en estado inicial con perfil ADMIN.";
	}

	@Override
	public String setTestDesigner() {
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_360_BotonLiqRutaDesdeDevEspecialesCA05CA06ADMIN_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}
