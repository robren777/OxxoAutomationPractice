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

/**DESCRIPCION: Valida que dentro de la pantalla Devoluciones Especiales se muestre el boton
 * 'Recepcion de Transferencias y Devoluciones' y que al hacer clic el sistema regrese a dicha pantalla
 * en su estado inicial con perfil ADMIN.
 *
 *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_358 Verificar boton de navegacion dentro de la pantalla Devoluciones Especiales - perfil ADMIN
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
public class ATC_FT_358_BotonRecepcionDesdeDevEspecialesCA03CA04ADMIN extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_358_BotonRecepcionDesdeDevEspecialesCA03CA04ADMIN_test(HashMap<String, String> data) throws Exception {
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
		 *                                             PASO 10 (CA03)
		 ***************************************************************************/
		addStep("Ubicar visualmente el boton 'Recepcion de Transferencias y Devoluciones' en la pantalla Devoluciones Especiales");
		boolean resultCA03 = devEspecialesPage.validaBotonRecepcionTransferenciasEnDevolucionesEspeciales(true);
		assertTrue(resultCA03, "CA03 fallido: no se visualiza el boton 'Recepcion de Transferencias y Devoluciones' dentro de la pantalla Devoluciones Especiales");
		testCase.addTextEvidenceCurrentStep("CA03 verificado: se visualiza el boton 'Recepcion de Transferencias y Devoluciones' dentro de Devoluciones Especiales.");

		/*
		 *                                             PASO 11 (clic CA04)
		 ***************************************************************************/
		addStep("Dar clic en el boton 'Recepcion de Transferencias y Devoluciones'");
		boolean resultClicCA04 = devEspecialesPage.clicBotonRecepcionTransferenciasDesdeDevolucionesEspeciales();
		assertTrue(resultClicCA04, "Clic CA04 fallido: no se pudo accionar el boton 'Recepcion de Transferencias y Devoluciones' desde Devoluciones Especiales");
		testCase.addTextEvidenceCurrentStep("El sistema redirige a la pantalla Recepcion de Transferencias y Devoluciones.");

		/*
		 *                                             PASO 12
		 ***************************************************************************/
		addStep("Verificar que la pantalla Recepcion de Transferencias y Devoluciones se muestra en su estado inicial");
		boolean resultCA04 = recepcion.validaCargaInicialFiltrosRapidosEHistoricos(7);
		assertTrue(resultCA04, "CA04 fallido: la pantalla Recepcion de Transferencias y Devoluciones no muestra el estado inicial esperado tras la redireccion");
		testCase.addTextEvidenceCurrentStep("CA04 verificado: la pantalla Recepcion de Transferencias y Devoluciones se muestra en estado inicial tras la redireccion.");

		/*
		 *                                             PASO 13
		 ***************************************************************************/
		addStep("Verificar que los filtros no conservan valores previos y que el grid mantiene la carga por defecto");
		assertTrue(resultCA04, "CA04 fallido: los filtros conservan valores previos o el grid no mantiene la carga inicial por defecto");
		testCase.addTextEvidenceCurrentStep("CA04 verificado: todos los filtros se muestran sin valores previos y el grid muestra la carga inicial con informacion por defecto.");
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
		return "Valida que dentro de Devoluciones Especiales se muestre el boton 'Recepcion de Transferencias y Devoluciones' y que al hacer clic el sistema regrese a dicha pantalla en estado inicial con perfil ADMIN.";
	}

	@Override
	public String setTestDesigner() {
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_358_BotonRecepcionDesdeDevEspecialesCA03CA04ADMIN_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}
