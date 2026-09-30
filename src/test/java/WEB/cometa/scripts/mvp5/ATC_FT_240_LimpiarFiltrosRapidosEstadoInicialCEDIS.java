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

/**DESCRIPCION: Verifica que Limpiar filtros rapidos regresa la pantalla al estado inicial con perfil CEDIS.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_240 CA07 - Verificar que Limpiar filtros rapidos regresa la pantalla al estado inicial - perfil CEDIS
* 
*PREREQUISITOS:
*1. Ambiente QA disponible, perfil CEDIS y transferencia valida asociada al PCSUPPLIERID del usuario.
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Usuario CEDIS configurado en el data provider y transferencia descubierta dinamicamente.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_240_LimpiarFiltrosRapidosEstadoInicialCEDIS extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_240_LimpiarFiltrosRapidosEstadoInicialCEDIS_test(
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
				"URL, perfil CEDIS y credenciales configurados; el estado CA05 se establecera en el paso 8.");

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
				"Se visualiza la pantalla Recepcion de Transferencias y Devoluciones con la seccion de filtros rapidos y la seccion de filtros historicos.");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Escribir una transferencia valida obtenida desde BD y dar clic en Consultar de filtros rapidos");
		boolean resultStep8 = recepcion.descubreTransferenciaValidaPorSupplier(user)
				&& recepcion.capturaTransferenciaA()
				&& recepcion.consultaTransferenciaConResultados();
		assertTrue(resultStep8, "Paso 8 fallido: no se establece la consulta rapida activa por transferencia");
		testCase.addTextEvidenceCurrentStep(
				"El grid muestra los resultados de la transferencia y los filtros historicos quedan deshabilitados.");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Dar clic en el boton 'Limpiar filtros' de la seccion de filtros rapidos");
		boolean resultStep9 = recepcion.limpiaFiltrosRapidos();
		assertTrue(resultStep9, "Paso 9 fallido: no se procesa la accion de limpiar filtros rapidos");
		testCase.addTextEvidenceCurrentStep("El sistema procesa la accion de limpiar.");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Verificar que el campo 'Transferencia' queda vacio tras dar clic en 'Limpiar filtros'");
		boolean resultStep10 = recepcion.validaTransferenciaVacia();
		assertTrue(resultStep10, "Paso 10 fallido: el campo Transferencia conserva el valor anterior");
		testCase.addTextEvidenceCurrentStep(
				"El campo Transferencia se encuentra completamente vacio, sin el valor ingresado previamente.");

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("Verificar que el campo 'Escanea o teclea una canastilla' tambien queda vacio tras la limpieza");
		boolean resultStep11 = recepcion.validaCanastillaVacia();
		assertTrue(resultStep11, "Paso 11 fallido: el campo Canastilla conserva un valor tras la limpieza");
		testCase.addTextEvidenceCurrentStep("El campo Escanea o teclea una canastilla queda vacio.");

		/*
		 *                                             PASO 12
		 ***************************************************************************/
		addStep("Verificar que la seccion historica regresa al estado inicial y que 'Consultar' queda condicionado unicamente por el rango de fechas");
		boolean resultStep12 = recepcion.validaEstadoInicialConsultarHistorico();
		assertTrue(resultStep12, "Paso 12 fallido: la seccion historica conserva un bloqueo ajeno al rango de fechas");
		testCase.addTextEvidenceCurrentStep(
				"Los campos y el calendario historicos recuperan interaccion; Consultar permanece en su estado inicial deshabilitado hasta capturar un rango valido.");

		/*
		 *                                             PASO 13
		 ***************************************************************************/
		addStep("Verificar que el boton 'Limpiar filtros' de la seccion de filtros historicos esta habilitado nuevamente");
		boolean resultStep13 = recepcion.validaLimpiarHistoricoHabilitado();
		assertTrue(resultStep13, "Paso 13 fallido: Limpiar filtros historicos permanece deshabilitado");
		testCase.addTextEvidenceCurrentStep("El boton Limpiar filtros de la seccion de filtros historicos aparece habilitado.");

		/*
		 *                                             PASO 14
		 ***************************************************************************/
		addStep("Verificar que no se muestran mensajes o tooltips de bloqueo entre filtros (ni 'Para usar los filtros historicos...' ni 'Para usar los filtros rapidos...')");
		boolean resultStep14 = recepcion.validaSinMensajesBloqueo();
		assertTrue(resultStep14, "Paso 14 fallido: permanece visible un mensaje o tooltip de bloqueo");
		testCase.addTextEvidenceCurrentStep(
				"No se muestran mensajes informativos de bloqueo ni indicadores visuales de estado bloqueado.");
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
		return "*1. Ambiente QA disponible, perfil CEDIS y transferencia valida asociada al PCSUPPLIERID del usuario.      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Verifica que Limpiar filtros rapidos vacia los campos, habilita los filtros historicos y retira los bloqueos con perfil CEDIS.";
	}

	@Override
	public String setTestDesigner() {
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_240_LimpiarFiltrosRapidosEstadoInicialCEDIS_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}