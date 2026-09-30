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

/**DESCRIPCION: Verificar que, cuando la consulta de Devoluciones Especiales no devuelve resultados,
 * no se muestre la opcion de descarga masiva (leyenda ni icono PDF) para perfil ADMIN.
 *
 *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_382 Verificar que sin resultados no se muestra la opcion de descarga masiva CA06 - perfil ADMIN
 *
 *PREREQUISITOS:
 *1. Perfil ADMIN (usuario 5555555) disponible en ambiente QA.
 *2. Pantalla Devoluciones Especiales accesible con filtros que permitan obtener una consulta sin resultados.
 *3.Liga de Portal Cometa
 *
 *INSUMOS:
 *Usuario ADMIN configurado en data provider.
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
 * @author Mauricio Gurrola
 * @date Agosto 2026
 */
public class ATC_FT_382_SinResultadosSinDescargaMasivaADMIN extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_382_SinResultadosSinDescargaMasivaADMIN_test(HashMap<String, String> data) throws Exception {
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
		addStep("Seleccionar la tienda '504KX OS MTY' en el filtro Tienda y una fecha sin registros obtenida desde BD");
		boolean resultStep7 = devEspecialesPage.seleccionarTiendaYRangoSinDatosBdCedis(user, "504KX OS MTY", true);
		assertTrue(resultStep7,
				"Paso 7 fallido: no fue posible seleccionar la tienda y el rango de fechas sin registros disponibles");
		testCase.addTextEvidenceCurrentStep("La tienda y el rango de fechas sin datos quedan seleccionados y el boton Consultar queda habilitado.");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Dar clic en el boton Consultar");
		boolean resultStep8 = devEspecialesPage.ejecutarConsultaDevolucionesEspeciales(true);
		assertTrue(resultStep8,
				"Paso 8 fallido: no fue posible ejecutar la consulta para validar el escenario sin resultados");
		testCase.addTextEvidenceCurrentStep("El grid se muestra sin registros, indicando que la consulta no arrojo resultados.");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Verificar la zona de la pantalla donde se muestra la leyenda 'Descarga MASIVA de comprobantes filtrados' y su icono de PDF");
		boolean resultStep9 = devEspecialesPage.validarAusenciaLeyendaEIconoPdfMasivo(true);
		assertTrue(resultStep9,
				"CA06 fallido: la leyenda o el icono de descarga masiva se muestran cuando la consulta no tiene resultados");
		testCase.addTextEvidenceCurrentStep(
				"CA06 verificado: la leyenda y el icono PDF de descarga masiva no se muestran en pantalla para una consulta sin resultados.");
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
				+ "*2. Pantalla Devoluciones Especiales accesible con filtros que permitan obtener una consulta sin resultados.\r\n"
				+ "*3.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Verificar que, cuando la consulta de Devoluciones Especiales no devuelve resultados, no se muestre la opcion de descarga masiva (leyenda ni icono PDF) para perfil ADMIN.";
	}

	@Override
	public String setTestDesigner() {
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_382_SinResultadosSinDescargaMasivaADMIN_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}
