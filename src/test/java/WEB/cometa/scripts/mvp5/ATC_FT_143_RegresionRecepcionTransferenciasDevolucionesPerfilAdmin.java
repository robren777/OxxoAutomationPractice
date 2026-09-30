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

/**DESCRIPCION: Verifica la regresion funcional de Recepcion de Transferencias y Devoluciones para el perfil ADMIN.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_143 Regresion - Verificar que la pantalla Recepcion de Transferencias y Devoluciones sigue funcionando correctamente tras el cambio de menu - perfil ADMIN
* 
*PREREQUISITOS:
*1. Script de reorganizacion aplicado y perfil ADMIN (usuario 5555555) disponible en QA.
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Usuario ADMIN 5555555 y registros historicos disponibles segun datos dinamicos de BD.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_143_RegresionRecepcionTransferenciasDevolucionesPerfilAdmin extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_143_RegresionRecepcionTransferenciasDevolucionesPerfilAdmin_test(
			HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		accessControlPage accessControl = new accessControlPage(u, testCase, pc4);
		menuCedisPage menu = new menuCedisPage(u, testCase);
		recepcionTransferenciasPage recepcion = new recepcionTransferenciasPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "ADMIN";
		String password = pc4.obtenerPasswordPorUsuario(user);
		System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);

		/*
		 * PRECONDICION
		 ***************************************************************************/
		addStep("Precondicion: declarar el script aplicado y validar el perfil ADMIN con sus credenciales configuradas");
		boolean resultPrecondition = accessControl.validarPrecondicionPerfil(url, user, "5555555", perfil, password);
		assertTrue(resultPrecondition,
				"Precondicion fallida: el perfil ADMIN, el usuario 5555555 o sus credenciales no estan disponibles");
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
		testCase.addTextEvidenceCurrentStep(
				"Prerrequisito declarado: script aplicado. Perfil ADMIN (usuario 5555555) validado para la regresion funcional.");

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
		assertTrue(resultStep5, "Paso 5 fallido: no se muestran las opciones del perfil ADMIN");
		testCase.addTextEvidenceCurrentStep(
				"Se visualiza la pantalla principal del portal Cometa con las opciones: CEDIS, Directos, FAQS Directos, FAQS CEDIS y Centro de descarga.");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Dar clic en 'CEDIS' del menu y luego en 'Recepcion y Devolucion'");
		boolean submenuAbiertoStep6 = pc4.clicBotonCometaCedis(true);
		boolean recepcionAbiertaStep6 = menu.clicRecepcionYDevolucion();
		boolean resultStep6 = submenuAbiertoStep6 && recepcionAbiertaStep6;
		assertTrue(resultStep6, "Paso 6 fallido: la pantalla Recepcion no carga completamente");
		testCase.addTextEvidenceCurrentStep(
				"Se visualiza la pantalla Recepcion de Transferencias y Devoluciones completamente cargada.");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Seleccionar el campo 'Plaza' en la seccion Filtros de Busqueda Historica y seleccionar una plaza disponible");
		boolean resultStep7 = recepcion.seleccionaPlazaConDatosDesdeBD(user);
		assertTrue(resultStep7, "Paso 7 fallido: el filtro Plaza no acepta una seleccion");
		testCase.addTextEvidenceCurrentStep("El filtro Plaza acepta una seleccion valida obtenida dinamicamente desde BD.");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Seleccionar fecha inicio y fecha fin con datos dinamicos de BD en el campo Rango de fechas de la seccion Filtros de Busqueda Historica");
		boolean resultStep8 = recepcion.capturaRangoHistoricoConDatos(user);
		assertTrue(resultStep8, "Paso 8 fallido: el rango de fechas no queda configurado correctamente");
		testCase.addTextEvidenceCurrentStep(
				"Los campos de fecha quedan configurados con un rango dinamico consultable desde BD. El boton Consultar de la seccion historica se habilita.");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Dar clic en el boton 'Consultar' de la seccion Filtros de Busqueda Historica");
		boolean consultaEjecutadaStep9 = recepcion.consultaHistoricaConResultadosConRecuperacion(user);
		boolean columnasVisiblesStep9 = recepcion.validaColumnasGridEntrega();
		boolean resultStep9 = consultaEjecutadaStep9 && columnasVisiblesStep9;
		assertTrue(resultStep9, "Paso 9 fallido: la consulta no muestra registros o faltan columnas en ENTREGA");
		testCase.addTextEvidenceCurrentStep(
				"El sistema ejecuta la consulta y muestra registros en el grid de la seccion ENTREGA. Las columnas del grid (Fecha, CR Tienda, Nombre de Tienda, Transferencia, # Canastillas Devueltas, Bols en entrega, FUE, Formato de Verificacion, Evidencias Fotograficas, Tira de transferencia) son visibles.");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Dar clic en el icono FUE de la primera fila del grid");
		boolean resultStep10 = recepcion.abrePrimerFueYConservaCanastilla();
		assertTrue(resultStep10, "Paso 10 fallido: el FUE individual no se descarga ni se visualiza");
		testCase.addTextEvidenceCurrentStep(
				"El sistema descarga o visualiza el documento FUE correctamente. La funcionalidad de descarga individual de FUE no fue afectada por el cambio de menu.");

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("Dar clic en el icono de descarga masiva FUE en la seccion 'Descarga MASIVA de comprobantes filtrados'");
		boolean resultStep11 = recepcion.descargaMasivaFue();
		assertTrue(resultStep11, "Paso 11 fallido: no inicia la descarga masiva de FUEs");
		testCase.addTextEvidenceCurrentStep(
				"El sistema inicia el proceso de descarga masiva de FUEs sin errores. La funcionalidad de descarga masiva no fue afectada.");

		/*
		 *                                             PASO 12
		 ***************************************************************************/
		addStep("Limpiar los filtros historicos usando el boton 'Limpiar filtros' y escribir un valor en el campo 'Escanea o teclea la canastilla' en la seccion Filtros de Busqueda Rapida");
		boolean resultStep12 = recepcion.limpiaFiltrosYCapturaCanastilla();
		assertTrue(resultStep12, "Paso 12 fallido: no se limpian los filtros o la canastilla no queda capturada");
		testCase.addTextEvidenceCurrentStep(
				"El campo acepta la entrada. El boton Consultar de filtros rapidos se habilita.");

		/*
		 *                                             PASO 13
		 ***************************************************************************/
		addStep("Dar clic en 'Consultar' de filtros rapidos");
		boolean resultStep13 = recepcion.consultaRapidaConResultados();
		assertTrue(resultStep13, "Paso 13 fallido: la busqueda rapida no muestra resultados coherentes");
		testCase.addTextEvidenceCurrentStep(
				"El sistema ejecuta la busqueda rapida y muestra resultados coherentes en el grid. La funcionalidad completa de la pantalla opera correctamente tras el cambio de nombre y reorganizacion del menu.");
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
		return "*1. Script de reorganizacion aplicado y perfil ADMIN (usuario 5555555) disponible en QA.      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Verifica la regresion funcional de Recepcion de Transferencias y Devoluciones para el perfil ADMIN.";
	}

	@Override
	public String setTestDesigner() {
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_143_RegresionRecepcionTransferenciasDevolucionesPerfilAdmin_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}