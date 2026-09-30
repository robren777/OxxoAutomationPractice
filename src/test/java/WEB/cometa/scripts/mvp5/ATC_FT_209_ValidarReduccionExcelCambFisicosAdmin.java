package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.generaDuplicadosCambiosFisicos;
import WEB.cometa.om.generaDuplicadosCambiosFisicos.ResultadoValidacionDuplicadosCambiosFisicos;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCION: Validar que el tiempo de generacion del archivo Excel de Cambios Fisicos con la optimizacion instalada se reduzca al menos 50pct para el perfil Admin.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_208 CA02 - Validar reduccion mayor o igual a 50pct tiempo Excel - Cambios Fisicos - perfil Admin
*
*PREREQUISITOS:
*1. Optimizacion de generacion de Excel implementada en backend Spring Boot, desplegada en QA, y datos disponibles para Cambios Fisicos segun alcance del usuario Admin.
*2.Liga de Portal Cometa
*
*INSUMOS:
*Usuario Admin 5555555 disponible en QA. Rango de fechas obtenido desde BD segun alcance del usuario.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Michelle Ceja
* @date Julio 2026
*/
public class ATC_FT_209_ValidarReduccionExcelCambFisicosAdmin extends BaseExecution {

	SeleniumUtil u;
	private List<Long> idsDuplicadosGenerados;
	private String userActual;
	private generaDuplicadosCambiosFisicos generadorDuplicadosClase;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_209_ValidarReduccionExcelCambFisicosAdmin_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		generadorDuplicadosClase = new generaDuplicadosCambiosFisicos(testCase);
		DateTimeFormatter formatoTimestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter formatoFechaUi = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "Admin";
		int minimoRegistrosPerformance = 1461;

		userActual = user;
		idsDuplicadosGenerados = null;

		addStep("Precondicion: obtener password, validar alcance de plaza y generar duplicados hasta cumplir el minimo de registros");
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
		System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);
		assertTrue("5555555".equals(user), "El data provider no corresponde al perfil Admin requerido");
		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null && !password.trim().isEmpty(), "No se encontro password configurada para el usuario: " + user);
		ResultadoValidacionDuplicadosCambiosFisicos validacionRegistros = generadorDuplicadosClase.generarDuplicadosHastaMinimo(user, minimoRegistrosPerformance);
		idsDuplicadosGenerados = validacionRegistros.idsDuplicadosGenerados;
		assertTrue(validacionRegistros.cumpleMinimo, "Precondicion fallida: No se alcanzo el minimo " + minimoRegistrosPerformance
				+ " registros para el usuario " + user + ". Total actual: " + validacionRegistros.totalRegistros
				+ " | Faltantes: " + validacionRegistros.registrosFaltantes);
		testCase.addTextEvidenceCurrentStep("Datos preparados para performance. Total actual: "
				+ validacionRegistros.totalRegistros + " | Minimo requerido: " + minimoRegistrosPerformance
				+ " | Faltantes: " + validacionRegistros.registrosFaltantes
				+ " | IDs duplicados a limpiar al terminar: " + idsDuplicadosGenerados.size());
		HashMap<String, String> rangoFechas = generadorDuplicadosClase.obtenerRangoFechasCambiosFisicosDesdeBD(user);
		String fechaInicio = rangoFechas.get("fechaInicio");
		String fechaFin = rangoFechas.get("fechaFin");
		int totalRegistrosBD = Integer.parseInt(rangoFechas.get("totalRegistros"));
		String fechaInicioUi = LocalDate.parse(fechaInicio).format(formatoFechaUi);
		String fechaFinUi = LocalDate.parse(fechaFin).format(formatoFechaUi);

		/* PASO 1 *******************************************************************/
		addStep("Ingresar la URL de Portal COMETA en el navegador");
		boolean resultStep1 = pc4.IngresarURL(url, true);
		assertTrue(resultStep1, "Paso 1 fallido: No se muestra la pantalla de inicio de sesion de Access Control");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El sistema muestra la pantalla de inicio de sesion del portal Access Control");
		pc4.PrivatePortal();

		/* PASO 2, PASO 3 Y PASO 4 **************************************************/
		addStep("Ingresar las credenciales correspondientes al perfil Admin para el inicio de sesion");
		boolean resultStep2 = pc4.IniciarSesionNoToken(user, password, true);
		assertTrue(resultStep2, "Paso 2, 3 y 4 fallido: No se validaron correctamente las credenciales del usuario Admin");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El sistema valida las credenciales, inicia sesion y redirige a la pantalla de seleccion de portales");

		/* PASO 5 *******************************************************************/
		addStep("Dar clic en la tarjeta Portal COMETA");
		boolean resultStep5 = pc4.seleccionarPortalCometaQAConRefresh(true);
		assertTrue(resultStep5, "Paso 5 fallido: No se mostro o no fue posible seleccionar Portal COMETA desde Access Control");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Se visualiza la pantalla principal del portal Cometa con las opciones Directos, FAQS Directos y Centro de descarga");

		/* PASO 6 *******************************************************************/
		addStep("Dar clic en la tarjeta Directos");
		boolean resultStep6 = pc4.clicBotonCometaDirectos(true);
		assertTrue(resultStep6, "Paso 6 fallido: No se visualiza la pantalla del modulo Directos");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Se visualiza el modulo Directos con Comprobantes de Compra, Devoluciones y Cambios Fisicos");

		/* PASO 7 *******************************************************************/
		addStep("Dar clic en la tarjeta Cambios Fisicos");
		boolean resultStep7 = pc4.ClicBotonCF(true);
		assertTrue(resultStep7, "Paso 7 fallido: No se visualiza la pantalla Cambios Fisicos");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Se visualiza la pantalla Cambios Fisicos con los filtros de busqueda disponibles");

		/* PASO 8 Y PASO 9 **********************************************************/
		addStep("Seleccionar el rango de fechas obtenido desde BD en el campo Rango de Fechas");
		testCase.addTextEvidenceCurrentStep("Rango obtenido desde BD | Fecha Inicio: " + fechaInicioUi
				+ " | Fecha Fin: " + fechaFinUi + " | Total registros BD: " + totalRegistrosBD);
		boolean resultStep8 = pc4.seleccionarRangoFechasCambiosFisicosSinEvidenciaIntermedia(fechaInicio, fechaFin);
		assertTrue(resultStep8, "Paso 8 y 9 fallido: No se selecciono correctamente el rango de fechas " + fechaInicioUi + " - " + fechaFinUi);
		testCase.addScreenShotCurrentStep(u, "Rango de fechas Cambios Fisicos seleccionado");

		/* PASO 10 ******************************************************************/
		addStep("Dar clic en el boton Consultar y validar que N sea mayor a 1000 registros");
		boolean resultStep10 = pc4.consultarComprobantes(true) && pc4.LabelOfandCountMayor1000CF(true);
		assertTrue(resultStep10, "Paso 10 fallido: El grid de Cambios Fisicos no muestra registros suficientes para performance (N > 1000)");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El grid muestra registros filtrados y un volumen mayor a 1000 para descarga masiva. Total esperado desde BD: " + totalRegistrosBD);

		/* PASO 11 ******************************************************************/
		addStep("Registrar la fecha y hora exacta T1 inmediatamente antes de solicitar la descarga");
		LocalDateTime t1 = LocalDateTime.now();
		testCase.addBoldTextEvidenceCurrentStep("T1 inicio generacion: " + t1.format(formatoTimestamp));
		System.out.println("[SCRIPT 206] T1 inicio generacion: " + t1.format(formatoTimestamp));
		assertTrue(t1 != null, "Paso 11 fallido: No se pudo registrar T1");

		/* PASO 12 ******************************************************************/
		addStep("Dar clic en descargar Excel masivo y cerrar el pop-up informativo de Centro de Descargas");
		boolean resultStep12 = pc4.ClickBtnExcelDevolu(true);
		assertTrue(resultStep12, "Paso 12-13 fallido: No se solicito la descarga Excel de Cambios Fisicos o no se cerro el pop-up informativo");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El pop-up informativo se muestra y se cierra sin errores; la solicitud queda procesandose en segundo plano");

		/* PASO 13 ******************************************************************/
		addStep("Dar clic en la opcion Centro de descargas en el menu de navegacion");
		boolean resultStep13 = pc4.ClicBotonCentroDescarga(true);
		assertTrue(resultStep13, "Paso 13 fallido: No se visualiza Centro de Descargas");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Se visualiza la pantalla Centro de Descargas con el listado de solicitudes");

		/* PASO 14 ******************************************************************/
		addStep("Identificar en el grid de Centro de Descargas el registro de Cambios Fisicos Excel hasta que muestre Terminado");
		long tiempoGeneracionSegundos = pc4.esperarUltimaDescargaCambiosFisicosTerminada(t1, 900, true);
		assertTrue(tiempoGeneracionSegundos >= 0, "Paso 14 fallido: La solicitud no llego a estatus Terminado dentro del tiempo maximo de 15 minutos");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El registro de Cambios Fisicos transiciona de En proceso a Terminado");

		/* PASO 15 ******************************************************************/
		addStep("Registrar la fecha y hora exacta T2 cuando el Estatus muestra Terminado");
		LocalDateTime t2 = t1.plusSeconds(tiempoGeneracionSegundos);
		testCase.addBoldTextEvidenceCurrentStep("T2 finalizacion calculada: " + t2.format(formatoTimestamp));
		assertTrue(tiempoGeneracionSegundos >= 0, "Paso 15 fallido: No se pudo registrar T2 porque no se obtuvo Terminado");

		/* PASO 16 ******************************************************************/
		addStep("Calcular T_gen = T2 - T1 en segundos y comparar contra el umbral menor o igual a 150 s");
		assertTrue(tiempoGeneracionSegundos <= 150, "Paso 16 fallido: T_gen excede el umbral de 150 segundos. T_gen = " + tiempoGeneracionSegundos + " segundos");
		testCase.addBoldTextEvidenceCurrentStep("Resultado final: T_gen = " + tiempoGeneracionSegundos + " segundos <= 150 segundos. Reduccion esperada mayor o igual a 50pct contra linea base CA01");
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
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "*1. Optimizacion de generacion de Excel implementada en backend Spring Boot, desplegada en QA, y datos disponibles para Cambios Fisicos segun alcance del usuario Admin.      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Validar que el tiempo de generacion del archivo Excel de Cambios Fisicos con la optimizacion instalada se reduzca al menos 50pct para el perfil Admin.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_209_ValidarReduccionExcelCambFisicosAdmin_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
