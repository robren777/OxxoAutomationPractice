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

/**DESCRIPCION: Verifica que el boton 'Devoluciones Especiales' se muestra en la pantalla Recepcion de
 * Transferencias y Devoluciones con el mismo diseno visual que 'Liquidacion de Ruta', ubicado a su derecha,
 * y que al hacer clic el sistema redirige a Devoluciones Especiales con filtros vacios y carga inicial del grid
 * con registros ordenados de mas reciente a mas antiguo. Perfil CEDIS.
 *
 *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_355 Verificar boton Devoluciones Especiales en pantalla Recepcion de Transferencias y Devoluciones - perfil CEDIS
 *
 *PREREQUISITOS:
 *1. Perfil CEDIS (usuario 6666666) disponible en ambiente QA.
 *2. Liga de Portal Cometa.
 *
 *INSUMOS:
 *Usuario CEDIS configurado en el data provider.
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/Login
 *
 * @author Mauricio Gurrola
 * @date Agosto 2026
 */
public class ATC_FT_355_BotonDevEspecialesRecepcionCA01CA02CEDIS extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_355_BotonDevEspecialesRecepcionCA01CA02CEDIS_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		accessControlPage accessControl = new accessControlPage(u, testCase, pc4);
		menuCedisPage menu = new menuCedisPage(u, testCase);
		recepcionTransferenciasPage recepcion = new recepcionTransferenciasPage(u, testCase);
		devolucionesEspecialesPage devEspecialesPage = new devolucionesEspecialesPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "CEDIS";
		String password = pc4.obtenerPasswordPorUsuario(user);

		/*
		 * PRECONDICION
		 ***************************************************************************/
		addStep("Precondicion: obtener password configurado para el usuario " + user + " con perfil " + perfil);
		boolean resultPrecondicion = accessControl.validarPrecondicionPerfil(url, user, "6666666", perfil, password);
		assertTrue(resultPrecondicion, "Precondicion fallida: la URL, el usuario CEDIS o sus credenciales no estan configurados");
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
		addStep("Escribir '6666666' en el campo Usuario");
		boolean resultStep2 = accessControl.capturarYValidarUsuario(user);
		assertTrue(resultStep2, "Paso 2 fallido: el campo Usuario no muestra el valor '6666666'");
		testCase.addTextEvidenceCurrentStep("El campo Usuario muestra el valor '6666666'.");

		/*
		 *                                             PASO 3
		 ***************************************************************************/
		addStep("Escribir la contrasena del perfil CEDIS en el campo Contrasena");
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
		testCase.addTextEvidenceCurrentStep("Se visualiza la pantalla principal del portal Cometa con las tarjetas: CEDIS, FAQS CEDIS.");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Dar clic en la tarjeta CEDIS");
		boolean resultStep6 = pc4.clicBotonCometaCedis(true);
		assertTrue(resultStep6, "Paso 6 fallido: no se visualiza la pantalla del modulo CEDIS con la sub-tarjeta Recepcion disponible");
		testCase.addTextEvidenceCurrentStep("Se visualiza la pantalla del modulo CEDIS con la sub-tarjeta Recepcion disponible.");

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
		addStep("Ubicar visualmente los botones de navegacion en la parte superior de la pantalla Recepcion de Transferencias y Devoluciones");
		boolean resultStep9 = recepcion.validaBotonesSuperiores();
		assertTrue(resultStep9, "Paso 9 fallido: no se visualizan los botones 'Liquidacion de Ruta' y 'Devoluciones Especiales' en la parte superior de la pantalla Recepcion de Transferencias y Devoluciones");
		testCase.addTextEvidenceCurrentStep("Se visualizan dos botones: 'Liquidacion de Ruta' y 'Devoluciones Especiales' en la parte superior de la pantalla.");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Verificar el orden de izquierda a derecha en el que se muestran ambos botones");
		boolean resultStep10 = recepcion.validarOrdenBotonesSuperiores();
		assertTrue(resultStep10, "CA01 fallido (orden): el orden de los botones no corresponde al esperado: 'Liquidacion de Ruta' a la izquierda y 'Devoluciones Especiales' a la derecha");
		testCase.addBoldTextEvidenceCurrentStep("CA01 verificado (orden): el boton 'Liquidacion de Ruta' se ubica primero (izquierda) y el boton 'Devoluciones Especiales' se ubica a la derecha de este, respetando el orden definido.");
		testCase.addScreenShotCurrentStep(u, "Orden_Botones_LiqRuta_DevEspeciales");

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("Comparar el diseno visual (color, tamano, tipografia y forma) del boton 'Devoluciones Especiales' contra el boton 'Liquidacion de Ruta'");
		boolean resultStep11 = recepcion.validarEstiloBotonesSuperiores();
		assertTrue(resultStep11, "CA01 fallido (estilo): el boton 'Devoluciones Especiales' no presenta el mismo diseno visual, estilo, tamano y comportamiento que el boton 'Liquidacion de Ruta'");
		testCase.addBoldTextEvidenceCurrentStep("CA01 verificado (estilo): ambos botones presentan el mismo diseno visual, estilo, tamano y comportamiento.");
		testCase.addScreenShotCurrentStep(u, "Estilo_Visual_Botones_LiqRuta_DevEspeciales");

		/*
		 *                                             PASO 12
		 ***************************************************************************/
		addStep("Dar clic en el boton 'Devoluciones Especiales'");
		boolean resultStep12 = devEspecialesPage.ingresarDevolucionesEspeciales(true);
		assertTrue(resultStep12, "Paso 12 fallido: el sistema no redirige correctamente a la pantalla Devoluciones Especiales o la pantalla destino no queda disponible");
		testCase.addTextEvidenceCurrentStep("El sistema redirige a la pantalla Devoluciones Especiales.");

		/*
		 *                                             PASO 13
		 ***************************************************************************/
		addStep("Verificar el estado de los filtros de busqueda (CEDIS, Plaza, Tienda, Bol, Rango de fechas) inmediatamente despues de la redireccion");
		boolean resultStep13 = devEspecialesPage.validarFiltrosDevolucionesEspecialesVisibles(true);
		assertTrue(resultStep13, "CA02 fallido (filtros vacios): los filtros de busqueda no se muestran sin valores previos en estado inicial despues de la redireccion");
		testCase.addBoldTextEvidenceCurrentStep("CA02 verificado (filtros vacios): todos los filtros CEDIS, Plaza, Tienda, BOL y Rango de fechas se muestran sin valores previos en estado inicial.");

		/*
		 *                                             PASO 14
		 ***************************************************************************/
		addStep("Verificar el grid de resultados inmediatamente despues de la redireccion");
		boolean resultStep14 = devEspecialesPage.validarGridCargaInicialDevolucionesEspeciales(true);
		testCase.addBoldTextEvidenceCurrentStep("CA02 verificado (carga inicial): el grid muestra la carga inicial con informacion por defecto sin necesidad de ejecutar una consulta manual.");
		assertTrue(resultStep14, "CA02 fallido (carga inicial): el grid no muestra la carga inicial con informacion por defecto o requirio una consulta manual");

		/*
		 *                                             PASO 15
		 ***************************************************************************/
		addStep("Verificar el orden de los registros mostrados en el grid segun el campo de fecha definido");
		boolean resultStep15 = devEspecialesPage.validarOrdenDescendenteRegistrosGridDevolucionesEspeciales(true);
		testCase.addBoldTextEvidenceCurrentStep("CA02 verificado (orden de registros): los registros se muestran ordenados de mas reciente a mas antiguo.");
		assertTrue(resultStep15, "CA02 fallido (orden de registros): los registros del grid no se muestran ordenados de mas reciente a mas antiguo");
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
		return "*1. Perfil CEDIS (usuario 6666666) disponible en ambiente QA.\r\n"
				+ "*2. Liga de Portal Cometa.";
	}

	@Override
	public String setTestDescription() {
		return "Verifica que el boton 'Devoluciones Especiales' se muestra en Recepcion de Transferencias y Devoluciones con el mismo diseno que 'Liquidacion de Ruta', y que al hacer clic el sistema redirige con filtros vacios y carga inicial del grid ordenado descendentemente. Perfil CEDIS.";
	}

	@Override
	public String setTestDesigner() {
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_355_BotonDevEspecialesRecepcionCA01CA02CEDIS_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}
