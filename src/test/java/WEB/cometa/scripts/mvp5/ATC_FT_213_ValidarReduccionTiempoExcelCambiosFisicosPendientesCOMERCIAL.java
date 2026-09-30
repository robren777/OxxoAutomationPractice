package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.generaDuplicadosCambFisPendiente;
import WEB.cometa.om.generaDuplicadosCambFisPendiente.ResultadoValidacionDuplicadosCambFisPendiente;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCION: Validar que el tiempo de generacion del archivo Excel de Cambios
 * Fisicos Pendientes con la optimizacion instalada se reduzca al menos 50pct para
 * el perfil COMERCIAL.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_213 CA02 - Validar reduccion mayor o igual a 50pct tiempo Excel -
 * Cambios Fisicos Pendientes - perfil COMERCIAL
 *
 * PREREQUISITOS:
 * 1. Optimizacion de generacion de Excel implementada en backend Spring Boot,
 * desplegada en QA, y datos representativos en Cambios Fisicos Pendientes segun
 * alcance del perfil COMERCIAL.
 * 2. Liga de Portal Cometa.
 *
 * INSUMOS:
 * Usuario COMERCIAL 2222222 disponible en QA.
 * Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
 * @author Michelle Ceja
 * @date 2026-07-28
 */
public class ATC_FT_213_ValidarReduccionTiempoExcelCambiosFisicosPendientesCOMERCIAL extends BaseExecution {

	SeleniumUtil u;
	private List<String> idsDuplicadosGenerados;
	private String userActual;
	private generaDuplicadosCambFisPendiente generadorDuplicadosClase;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_213_ValidarReduccionTiempoExcelCambiosFisicosPendientesCOMERCIAL_test(
			HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		generadorDuplicadosClase = new generaDuplicadosCambFisPendiente(testCase);
		DateTimeFormatter formatoTimestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "COMERCIAL";
		int minimoRegistrosPerformance = 1461;

		userActual = user;
		idsDuplicadosGenerados = null;

		addStep("Precondicion: obtener password, validar alcance y generar duplicados hasta cumplir el minimo de registros");
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
		System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);
		assertTrue("2222222".equals(user), "El data provider no corresponde al perfil COMERCIAL requerido");
		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null && !password.trim().isEmpty(),
				"No se encontro password configurada para el usuario: " + user);

		ResultadoValidacionDuplicadosCambFisPendiente validacionRegistros = generadorDuplicadosClase
				.generarDuplicadosHastaMinimo(user, minimoRegistrosPerformance);
		idsDuplicadosGenerados = validacionRegistros.idsDuplicadosGenerados;
		assertTrue(validacionRegistros.cumpleMinimo,
				"Precondicion fallida: No se alcanzo el minimo " + minimoRegistrosPerformance
						+ " registros para el usuario " + user + ". Total actual: "
						+ validacionRegistros.totalRegistros + " | Faltantes: " + validacionRegistros.registrosFaltantes);
		testCase.addTextEvidenceCurrentStep("Datos preparados para performance. Total actual alcance: "
				+ validacionRegistros.totalRegistros + " | Minimo requerido: " + minimoRegistrosPerformance
				+ " | IDs duplicados a limpiar al terminar: " + idsDuplicadosGenerados.size()
				+ " | Filtro de alcance: " + validacionRegistros.alcanceUsuario.tipoFiltro + " | Valor alcance: "
				+ validacionRegistros.alcanceUsuario.valorFiltro);

		/*
		 *                                             PASO 1
		 ***************************************************************************/
		addStep("Ingresar la URL de Portal COMETA en el navegador");
		boolean resultStep1 = pc4.IngresarURL(url, true);
		assertTrue(resultStep1, "Paso 1 fallido: No se muestra la pantalla de inicio de sesion de Access Control");
		testCase.addTextEvidenceCurrentStep(
				"Resultado esperado validado: El sistema muestra la pantalla de inicio de sesion del portal Access Control");
		pc4.PrivatePortal();

		/*
		 *                                             PASO 2, PASO 3 Y PASO 4
		 ***************************************************************************/
		addStep("Ingresar las credenciales correspondientes al perfil COMERCIAL para el inicio de sesion");
		boolean resultStep2 = pc4.IniciarSesionNoToken(user, password, true);
		assertTrue(resultStep2,
				"Paso 2, 3 y 4 fallido: No se validaron correctamente las credenciales del usuario COMERCIAL");
		testCase.addTextEvidenceCurrentStep(
				"Resultado esperado validado: El sistema valida las credenciales, inicia sesion y redirige a la pantalla de seleccion de portales");

		/*
		 *                                             PASO 5
		 ***************************************************************************/
		addStep("Dar clic en la tarjeta Portal COMETA");
		boolean resultStep5 = pc4.seleccionarPortalCometaQAConRefresh(true);
		assertTrue(resultStep5,
				"Paso 5 fallido: No se mostro o no fue posible seleccionar Portal COMETA desde Access Control");
		testCase.addTextEvidenceCurrentStep(
				"Resultado esperado validado: Se visualiza la pantalla principal del portal Cometa con las opciones CEDIS, Directos, FAQS Directos, FAQS CEDIS y Centro de descarga");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Dar clic en la tarjeta Directos");
		boolean resultStep6 = pc4.clicBotonCometaDirectos(true);
		assertTrue(resultStep6, "Paso 6 fallido: No se visualiza la pantalla del modulo Directos");
		testCase.addTextEvidenceCurrentStep(
				"Resultado esperado validado: Se visualiza el modulo Directos con Comprobantes de Compra, Devoluciones y Cambios Fisicos");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Dar clic en la tarjeta Cambios Fisicos");
		boolean resultStep7 = pc4.ClicBotonCF(true);
		assertTrue(resultStep7, "Paso 7 fallido: No se visualiza la pantalla Cambios Fisicos");
		testCase.addTextEvidenceCurrentStep(
				"Resultado esperado validado: Se visualiza la pantalla Cambios Fisicos con los filtros de busqueda disponibles");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Dar clic en la opcion Cambios Fisicos pendientes actuales");
		boolean resultStep8 = pc4.clickCambiosFisicosPendientesPantalla(true);
		assertTrue(resultStep8,
				"Paso 8 fallido: No se visualiza la pantalla Cambios Fisicos Pendientes con filtros de busqueda disponibles");
		testCase.addTextEvidenceCurrentStep(
				"Resultado esperado validado: Se visualiza la pantalla Cambios Fisicos Pendientes con los filtros de busqueda disponibles");

		/*
		 *                                             PASO 9 Y PASO 10
		 ***************************************************************************/
		addStep("Ingresar los filtros necesarios para realizar la consulta");
		String descripcionFiltroAplicado = generadorDuplicadosClase.aplicarFiltrosNecesariosParaConsulta(user, pc4,
				validacionRegistros, true);
		testCase.addTextEvidenceCurrentStep("Filtro aplicado: " + descripcionFiltroAplicado);

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("Dar clic en Consultar y validar disponibilidad de resultados");
		boolean resultStep11 = pc4.consultarComprobantes(true) && pc4.LabelOfandCountCFdet(true);
		assertTrue(resultStep11,
				"Paso 11 fallido: El grid de Cambios Fisicos Pendientes no muestra resultados despues de consultar");
		testCase.addTextEvidenceCurrentStep(
				"Resultado esperado validado: El grid muestra registros filtrados y el contador superior queda visible para documentar N");

		/*
		 *                                             PASO 12
		 ***************************************************************************/
		addStep("Registrar la fecha y hora exacta T1 inmediatamente antes de solicitar la descarga");
		LocalDateTime t1 = LocalDateTime.now();
		testCase.addBoldTextEvidenceCurrentStep("T1 inicio generacion: " + t1.format(formatoTimestamp));
		System.out.println("[SCRIPT 213] T1 inicio generacion: " + t1.format(formatoTimestamp));
		assertTrue(t1 != null, "Paso 12 fallido: No se pudo registrar T1");

		/*
		 *                                             PASO 13 Y PASO 14
		 ***************************************************************************/
		addStep("Dar clic en descargar Excel masivo y cerrar el pop-up informativo");
		boolean resultStep13 = pc4.ClicBotonDescargarMasivoCFPend(true);
		assertTrue(resultStep13,
				"Paso 13 y 14 fallido: No se solicito la descarga Excel de Cambios Fisicos Pendientes o no se cerro el pop-up");
		testCase.addTextEvidenceCurrentStep(
				"Resultado esperado validado: El pop-up informativo se muestra y se cierra sin errores; la solicitud queda procesandose en segundo plano");

		/*
		 *                                             PASO 15
		 ***************************************************************************/
		addStep("Dar clic en la opcion Centro de descargas en el menu de navegacion");
		boolean resultStep15 = pc4.ClicBotonCentroDescarga(true);
		assertTrue(resultStep15, "Paso 15 fallido: No se visualiza Centro de Descargas");
		testCase.addTextEvidenceCurrentStep(
				"Resultado esperado validado: Se visualiza la pantalla Centro de Descargas con el listado de solicitudes");

		/*
		 *                                             PASO 16
		 ***************************************************************************/
		addStep("Esperar a que el registro de descarga cambie a estatus Terminado");
		long tiempoGeneracionSegundos = pc4.esperarUltimaDescargaCambiosFisicosTerminada(t1, 900, true);
		assertTrue(tiempoGeneracionSegundos >= 0,
				"Paso 16 fallido: La solicitud no llego a estatus Terminado dentro del tiempo maximo de 15 minutos");
		testCase.addTextEvidenceCurrentStep(
				"Resultado esperado validado: El registro de Cambios Fisicos Pendientes transiciona de En proceso a Terminado");

		/*
		 *                                             PASO 17
		 ***************************************************************************/
		addStep("Registrar la fecha y hora exacta T2 cuando el estatus muestra Terminado");
		LocalDateTime t2 = t1.plusSeconds(tiempoGeneracionSegundos);
		testCase.addBoldTextEvidenceCurrentStep("T2 finalizacion calculada: " + t2.format(formatoTimestamp));
		assertTrue(tiempoGeneracionSegundos >= 0,
				"Paso 17 fallido: No se pudo registrar T2 porque no se obtuvo estatus Terminado");

		/*
		 *                                             PASO 18
		 ***************************************************************************/
		addStep("Calcular T_gen = T2 - T1 en segundos y comparar contra el umbral menor o igual a 150 s");
		assertTrue(tiempoGeneracionSegundos <= 150,
				"Paso 18 fallido: T_gen excede el umbral de 150 segundos. T_gen = " + tiempoGeneracionSegundos
						+ " segundos");
		testCase.addBoldTextEvidenceCurrentStep(
				"Resultado final: T_gen = " + tiempoGeneracionSegundos + " segundos <= 150 segundos");
	}

	@AfterMethod
	public void afterMethod() {
		try {
			if (idsDuplicadosGenerados != null && !idsDuplicadosGenerados.isEmpty() && userActual != null) {
				System.out.println("[AFTERMETHOD] Iniciando cleanup de duplicados para usuario " + userActual);
				generadorDuplicadosClase.eliminarDuplicadosGenerados(userActual, idsDuplicadosGenerados);
				System.out.println("[AFTERMETHOD] Cleanup completado");
			}
		} catch (Exception exception) {
			System.out.println("[AFTERMETHOD] Error en cleanup de duplicados: " + exception.getMessage());
			exception.printStackTrace();
		} finally {
			if (u != null) {
				u.quit();
			}
		}
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "*1. Optimizacion de generacion de Excel implementada en backend Spring Boot, desplegada en QA, y datos representativos en Cambios Fisicos Pendientes segun alcance COMERCIAL.      \r\n"
				+ "*2. Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Validar que el tiempo de generacion del archivo Excel de Cambios Fisicos Pendientes con la optimizacion instalada se reduzca al menos 50pct para el perfil COMERCIAL.";
	}

	@Override
	public String setTestDesigner() {
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_213_ValidarReduccionTiempoExcelCambiosFisicosPendientesCOMERCIAL_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}
