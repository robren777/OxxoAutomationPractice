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

/**DESCRIPCIÓN: Verificar en Devoluciones Especiales la leyenda de descarga masiva, el modal "Espere un momento"
 * durante la generacion y la deshabilitacion temporal del icono PDF de descarga masiva para perfil ADMIN.
 *
 *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_378  Verificar leyenda de descarga masiva, modal Espere un momento y deshabilitacion del icono durante la generacion - perfil ADMIN
 *
 *PREREQUISITOS:
 *1. Perfil ADMIN (usuario 5555555) disponible en ambiente QA. Grid de Devoluciones Especiales con al menos un registro disponible tras ejecutar una consulta.
 *2.Liga de Portal Cometa
 *
 *INSUMOS:
 *Usuario ADMIN configurado en el data provider.
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
 * @author Mauricio Gurrola
 * @date Agosto 2026
 */
public class ATC_FT_378_LeyendaModalYBloqueoDescargaMasivaADMIN extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_378_LeyendaModalYBloqueoDescargaMasivaADMIN_test(HashMap<String, String> data) throws Exception {
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
		addStep("Precondicion: obtener password configurado para el usuario " + user + " con perfil " + perfil);
		boolean resultPrecondicion = accessControl.validarPrecondicionPerfil(url, user, user, perfil, password);
		assertTrue(resultPrecondicion,
				"Precondicion fallida: la URL, el usuario ADMIN o sus credenciales no estan configurados");
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
		System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);

		/*
		 *                                             PASO 1
		 ***************************************************************************/
		addStep("Ingresar la URL " + url + " en el campo de direcciones del navegador");
		boolean resultStep1 = pc4.IngresarURL(url, true);
		assertTrue(resultStep1,
				"Paso 1 fallido: no se muestra la pantalla de inicio de sesion del portal Access Control");
		testCase.addTextEvidenceCurrentStep("El sistema muestra la pantalla de inicio de sesion del portal Access Control.");
		pc4.PrivatePortal();

		/*
		 *                                             PASO 2
		 ***************************************************************************/
		addStep("Escribir '" + user + "' en el campo Usuario");
		boolean resultStep2 = accessControl.capturarYValidarUsuario(user);
		assertTrue(resultStep2, "Paso 2 fallido: el campo Usuario no muestra el valor esperado del perfil ADMIN");
		testCase.addTextEvidenceCurrentStep("El campo Usuario muestra el valor capturado desde data provider: " + user + ".");
		testCase.addScreenShotCurrentStep(u, "Campo_Usuario_Admin_Capturado");

		/*
		 *                                             PASO 3
		 ***************************************************************************/
		addStep("Escribir la contrasena del perfil ADMIN en el campo Contrasena");
		boolean resultStep3 = accessControl.capturarYValidarPassword(password);
		assertTrue(resultStep3, "Paso 3 fallido: la contrasena no queda capturada y enmascarada en el campo");
		testCase.addTextEvidenceCurrentStep("El campo Contrasena muestra los caracteres enmascarados.");
		testCase.addScreenShotCurrentStep(u, "Campo_Contrasena_Enmascarada");

		/*
		 *                                             PASO 4
		 ***************************************************************************/
		addStep("Dar clic en el boton Iniciar sesion");
		boolean resultStep4 = accessControl.iniciarSesionYValidarAcceso();
		assertTrue(resultStep4,
				"Paso 4 fallido: las credenciales no permiten iniciar sesion o no se redirige a la seleccion de portales");
		testCase.addTextEvidenceCurrentStep(
				"El sistema valida las credenciales, inicia sesion y redirige a la pantalla de seleccion de portales donde se visualizan las opciones General y Portal COMETA.");
		testCase.addScreenShotCurrentStep(u, "Pantalla_Seleccion_De_Portales");

		/*
		 *                                             PASO 5
		 ***************************************************************************/
		addStep("Dar clic en la opcion 'Portal COMETA'");
		boolean resultStep5 = pc4.seleccionarPortalCometaQA(true);
		assertTrue(resultStep5,
				"Paso 5 fallido: no se visualiza la pantalla principal del portal Cometa con las opciones del perfil ADMIN");
		testCase.addTextEvidenceCurrentStep(
				"Se visualiza la pantalla principal del portal Cometa con las opciones disponibles para el perfil.");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Dar clic en la opcion Recepcion y devolucion> Devoluciones especiales");
		boolean resultStep6Cedis = pc4.clicBotonCometaCedis(true);
		assertTrue(resultStep6Cedis, "Paso 6 fallido: no fue posible ingresar al modulo CEDIS");
		boolean resultStep6Recepcion = menu.clicRecepcionYDevolucion();
		assertTrue(resultStep6Recepcion,
				"Paso 6 fallido: no fue posible abrir Recepcion y devolucion para continuar hacia Devoluciones Especiales");
		boolean resultStep6CargaRecepcion = recepcion.validaCargaInicialFiltrosRapidosEHistoricos(7);
		assertTrue(resultStep6CargaRecepcion,
				"Paso 6 fallido: la pantalla Recepcion y devolucion no quedo estable para seleccionar Devoluciones Especiales");
		boolean resultStep6 = devEspecialesPage.ingresarDevolucionesEspeciales(true);
		assertTrue(resultStep6,
				"Paso 6 fallido: no se visualiza la pantalla Devoluciones Especiales con los filtros esperados");
		testCase.addTextEvidenceCurrentStep(
				"Se visualiza la pantalla Devoluciones Especiales con los filtros de busqueda CEDIS, Plaza, Tienda, Bol y Rango de fechas.");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Seleccionar el rango de fechas en el campo Rango de fechas (maximo 7 dias) iniciando desde fecha con informacion disponible");
		boolean resultStep7 = devEspecialesPage.seleccionarRangoSieteDiasDesdeFechaInicialBdCedis(user, true);
		assertTrue(resultStep7,
				"Paso 7 fallido: no fue posible seleccionar un rango de fechas valido igual o menor a 7 dias");
		testCase.addTextEvidenceCurrentStep("El campo muestra el rango seleccionado y el boton Consultar se habilita.");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Dar clic en el boton Consultar");
		boolean resultStep8 = devEspecialesPage.ejecutarConsultaDevolucionesEspeciales(true);
		assertTrue(resultStep8,
				"Paso 8 fallido: no se pudo ejecutar la consulta o el grid no quedo visible al terminar la carga");
		testCase.addTextEvidenceCurrentStep(
				"Se muestra modal 'Espere un momento' y al terminar se visualiza el grid con columnas de resultados.");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Observar la zona de la pantalla Devoluciones Especiales donde se muestran las opciones de descarga inmediatamente despues de dar clic en Consultar");
		boolean resultStep9 = devEspecialesPage.validarLeyendaEIconoPdfMasivoVisible(true);
		assertTrue(resultStep9,
				"CA01/CA08 fallido: no se muestra la leyenda 'Descarga MASIVA de comprobantes filtrados' junto con el icono PDF activo tras consultar");
		testCase.addTextEvidenceCurrentStep(
				"Se muestra la leyenda de descarga masiva con su icono PDF activo junto a la seccion de descarga.");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Dar clic en el icono de PDF de la leyenda 'Descarga MASIVA de comprobantes filtrados'");
		boolean resultStep10 = devEspecialesPage.accionarPdfMasivoYValidarModalEspere(true);
		assertTrue(resultStep10,
				"CA01 fallido: al accionar el icono PDF no se muestra el modal 'Espere un momento'");
		testCase.addTextEvidenceCurrentStep(
				"El sistema muestra una ventana modal con el texto 'Espere un momento' al iniciar la generacion.");

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("Observar el icono de PDF inmediatamente despues de dar clic");
		boolean resultStep11 = devEspecialesPage.validarIconoPdfMasivoDeshabilitado(true);
		assertTrue(resultStep11,
				"CA08 fallido: el icono de PDF no se muestra deshabilitado durante la generacion y permite solicitudes duplicadas");
		testCase.addTextEvidenceCurrentStep(
				"El icono PDF permanece deshabilitado durante la generacion para evitar solicitudes duplicadas.");

		/*
		 *                                             PASO 12
		 ***************************************************************************/
		addStep("Observar el modal mientras el sistema genera el archivo de descarga");
		boolean resultStep12 = devEspecialesPage.validarModalEspereUnMomentoVisible(true);
		assertTrue(resultStep12,
				"CA01 fallido: el modal 'Espere un momento' no permanece visible durante la generacion del archivo");
		testCase.addTextEvidenceCurrentStep(
				"El modal 'Espere un momento' permanece visible durante la generacion del archivo de descarga.");

		/*
		 *                                             PASO 13
		 ***************************************************************************/
		addStep("Esperar a que el archivo de descarga quede listo");
		boolean resultStep13 = devEspecialesPage.esperarCierreModalEspereUnMomento(true);
		assertTrue(resultStep13,
				"CA01 fallido: el modal 'Espere un momento' no se cierra automaticamente al finalizar la generacion");
		testCase.addTextEvidenceCurrentStep(
				"El modal se cierra automaticamente al finalizar la generacion y la descarga se inicia automaticamente o queda registrada en Centro de Descarga segun volumen.");

		/*
		 *                                             PASO 14
		 ***************************************************************************/
		addStep("Observar el icono de PDF despues de finalizada la descarga");
		boolean resultStep14 = devEspecialesPage.validarIconoPdfMasivoHabilitado(true);
		assertTrue(resultStep14,
				"CA08 fallido: el icono de PDF no se reactiva despues de finalizar el proceso de descarga");
		testCase.addTextEvidenceCurrentStep("El icono de PDF se muestra nuevamente habilitado al finalizar el proceso.");
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
		return "*1. Perfil ADMIN (usuario 5555555) disponible en ambiente QA. Grid de Devoluciones Especiales con al menos un registro disponible tras ejecutar una consulta.\r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Verificar en Devoluciones Especiales la leyenda de descarga masiva, el modal Espere un momento durante la generacion y la deshabilitacion temporal del icono PDF para perfil ADMIN.";
	}

	@Override
	public String setTestDesigner() {
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_378_LeyendaModalYBloqueoDescargaMasivaADMIN_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}
