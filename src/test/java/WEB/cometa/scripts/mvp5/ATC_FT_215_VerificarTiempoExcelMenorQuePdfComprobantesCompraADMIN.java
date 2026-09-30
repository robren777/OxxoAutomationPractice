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
import WEB.cometa.om.comprobantesCompraPage;
import WEB.cometa.om.generaDuplicadosComprobanteCompra;
import WEB.cometa.om.generaDuplicadosComprobanteCompra.ResultadoValidacionDuplicadosComprobanteCompra;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCION: Verificar que el tiempo de generacion del archivo Excel de Comprobantes de Compra con la optimizacion instalada sea estrictamente menor al tiempo de generacion del PDF para el mismo volumen de registros, en el perfil ADMIN.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_215 CA02 - Verificar que tiempo de generacion Excel es estrictamente menor que PDF en el mismo volumen - perfil ADMIN
*
*PREREQUISITOS:
*1. Optimizacion de generacion de Excel implementada en backend Spring Boot, desplegada en QA, y datos representativos mayores a 1000 registros en Comprobantes de Compra.
*2.Liga de Portal Cometa
*
*INSUMOS:
*Usuario ADMIN 5555555 disponible en QA. Rango de fechas obtenido desde BD segun alcance global del usuario.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_215_VerificarTiempoExcelMenorQuePdfComprobantesCompraADMIN extends BaseExecution {

	SeleniumUtil u;
	private List<Long> idsDuplicadosGenerados;
	private String userActual;
	private generaDuplicadosComprobanteCompra generadorDuplicadosClase;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_215_VerificarTiempoExcelMenorQuePdfComprobantesCompraADMIN_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		comprobantesCompraPage comprobantesPage = new comprobantesCompraPage(u, testCase);
		generadorDuplicadosClase = new generaDuplicadosComprobanteCompra(testCase);
		DateTimeFormatter formatoTimestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter formatoFechaUi = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "ADMIN";
		int minimoRegistrosPerformance = 1461;
		long umbralExcelSegundos = 180L;

		userActual = user;
		idsDuplicadosGenerados = null;

		addStep("Precondicion: obtener password, validar alcance global y generar duplicados en ultimos 7 dias hasta cumplir el minimo de registros");
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
		System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);
		assertTrue("5555555".equals(user), "El data provider no corresponde al perfil ADMIN requerido");
		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null && !password.trim().isEmpty(), "No se encontro password configurada para el usuario: " + user);
		ResultadoValidacionDuplicadosComprobanteCompra validacionRegistros = generadorDuplicadosClase.generarDuplicadosHastaMinimoUltimos7Dias(user, minimoRegistrosPerformance);
		idsDuplicadosGenerados = validacionRegistros.idsDuplicadosGenerados;
		assertTrue(validacionRegistros.cumpleMinimo, "Precondicion fallida: No se alcanzo el minimo " + minimoRegistrosPerformance
				+ " registros para el usuario " + user + ". Total actual: " + validacionRegistros.totalRegistros
				+ " | Faltantes: " + validacionRegistros.registrosFaltantes);
		testCase.addTextEvidenceCurrentStep("Datos preparados para performance. Total actual: "
				+ validacionRegistros.totalRegistros + " | Minimo requerido: " + minimoRegistrosPerformance
				+ " | Faltantes: " + validacionRegistros.registrosFaltantes
				+ " | IDs duplicados a limpiar al terminar: " + idsDuplicadosGenerados.size());
		HashMap<String, String> rangoFechas = comprobantesPage.obtenerRangoFechasComprobantesCompraUltimos7DiasDesdeBD(user, minimoRegistrosPerformance);
		String fechaInicio = rangoFechas.get("fechaInicio");
		String fechaFin = rangoFechas.get("fechaFin");
		int totalRegistrosBD = Integer.parseInt(rangoFechas.get("totalRegistros"));
		String fechaInicioUi = LocalDate.parse(fechaInicio).format(formatoFechaUi);
		String fechaFinUi = LocalDate.parse(fechaFin).format(formatoFechaUi);
		assertTrue(Boolean.parseBoolean(rangoFechas.get("cumpleMinimo")), "Precondicion fallida: El rango de 7 dias (" + fechaInicioUi
				+ " - " + fechaFinUi + ") no alcanza el minimo " + minimoRegistrosPerformance
				+ " registros requerido para medir el tiempo Excel vs PDF con el mismo volumen. Total en 7 dias: " + totalRegistrosBD);

		/* PASO 1 *******************************************************************/
		addStep("Ingresar la URL de Portal COMETA en el navegador");
		boolean resultStep1 = pc4.IngresarURL(url, true);
		assertTrue(resultStep1, "Paso 1 fallido: No se muestra la pantalla de inicio de sesion de Access Control");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El sistema muestra la pantalla de inicio de sesion del portal Access Control");
		pc4.PrivatePortal();

		/* PASO 2, PASO 3 Y PASO 4 **************************************************/
		addStep("Ingresar las credenciales correspondientes al perfil ADMIN para el inicio de sesion");
		boolean resultStep2 = pc4.IniciarSesionNoToken(user, password, true);
		assertTrue(resultStep2, "Paso 2, 3 y 4 fallido: No se validaron correctamente las credenciales del usuario ADMIN");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El sistema valida las credenciales, inicia sesion y redirige a la pantalla de seleccion de portales");

		/* PASO 5 *******************************************************************/
		addStep("Dar clic en la tarjeta Portal COMETA");
		boolean resultStep5 = pc4.seleccionarPortalCometaQAConRefresh(true);
		assertTrue(resultStep5, "Paso 5 fallido: No se mostro o no fue posible seleccionar Portal COMETA desde Access Control");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Se visualiza la pantalla principal del portal Cometa con las opciones CEDIS, Directos, FAQS Directos, FAQS CEDIS y Centro de descarga");

		/* PASO 6 *******************************************************************/
		addStep("Dar clic en la tarjeta Directos");
		boolean resultStep6 = pc4.clicBotonCometaDirectos(true);
		assertTrue(resultStep6, "Paso 6 fallido: No se visualiza la pantalla del modulo Directos");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Se visualiza el modulo Directos con Comprobantes de Compra, Devoluciones y Cambios Fisicos");

		/* PASO 7 *******************************************************************/
		addStep("Dar clic en la tarjeta Comprobantes de Compra");
		boolean resultStep7 = pc4.ClicBotonComprobantesDeCompra(true);
		assertTrue(resultStep7, "Paso 7 fallido: No se visualiza la pantalla Comprobantes de Compra");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Se visualiza la pantalla Comprobantes de Compra con los filtros de busqueda disponibles");

		/* PASO 8 Y PASO 9 **********************************************************/
		addStep("Seleccionar el rango de fechas obtenido desde BD en el campo Rango de Fechas");
		testCase.addTextEvidenceCurrentStep("Rango obtenido desde BD | Fecha Inicio: " + fechaInicioUi
				+ " | Fecha Fin: " + fechaFinUi + " | Total registros BD: " + totalRegistrosBD);
		boolean resultStep8 = comprobantesPage.seleccionarRangoFechasSinEvidenciaIntermedia(fechaInicio, fechaFin);
		assertTrue(resultStep8, "Paso 8 y 9 fallido: No se selecciono correctamente el rango de fechas " + fechaInicioUi + " - " + fechaFinUi);
		testCase.addScreenShotCurrentStep(u, "Rango de fechas Comprobantes de Compra seleccionado");

		/* PASO 10 ******************************************************************/
		addStep("Dar clic en el boton Consultar y documentar el total N de registros");
		boolean resultStep10 = pc4.consultarComprobantes(true) && comprobantesPage.validarGridConRegistros();
		assertTrue(resultStep10, "Paso 10 fallido: El grid no muestra registros filtrados");
		int totalRegistrosExcel = comprobantesPage.obtenerTotalRegistrosComprobantesCompraActual(true);
		assertTrue(totalRegistrosExcel > 1000, "Paso 10 fallido: El total N debe ser mayor a 1000 para que el archivo se envie al Centro de Descargas. Total obtenido: " + totalRegistrosExcel);
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El grid muestra registros filtrados y N = " + totalRegistrosExcel + ". Total esperado desde BD: " + totalRegistrosBD);

		/* PASO 11 ******************************************************************/
		addStep("Registrar timestamp T1_excel antes de solicitar la descarga Excel");
		boolean resultStep11Boton = comprobantesPage.capturarBotonDescargaMasivaExcel(true);
		assertTrue(resultStep11Boton, "Paso 11 fallido: No se pudo evidenciar el boton de descarga masiva Excel antes de registrar T1_excel");
		LocalDateTime t1Excel = LocalDateTime.now();
		testCase.addBoldTextEvidenceCurrentStep("T1_excel documentado: " + t1Excel.format(formatoTimestamp));
		System.out.println("[SCRIPT 215] T1_excel: " + t1Excel.format(formatoTimestamp));
		assertTrue(t1Excel != null, "Paso 11 fallido: No se pudo registrar T1_excel");

		/* PASO 12 Y PASO 13 **********************************************************/
		addStep("Dar clic en Descargar Excel masivo. Esperar que el estatus en Centro de Descargas cambie a Terminado y registrar T2_excel");
		boolean resultStep12 = pc4.ClicBotonDescargarMasivoplus1k(true);
		assertTrue(resultStep12, "Paso 12 fallido: No se solicito la descarga Excel o no se cerro el pop-up informativo");
		boolean resultStep12CentroDescargas = pc4.ClicBotonCentroDescarga(true);
		assertTrue(resultStep12CentroDescargas, "Paso 12 fallido: No se visualiza Centro de Descargas");
		long tGenExcel = comprobantesPage.esperarUltimaDescargaComprobantesCompraTerminada(t1Excel, fechaInicio, fechaFin, 900, true);
		assertTrue(tGenExcel >= 0, "Paso 12-13 fallido: La solicitud Excel no llego a estatus Terminado dentro del tiempo maximo de 15 minutos");
		LocalDateTime t2Excel = t1Excel.plusSeconds(tGenExcel);
		testCase.addBoldTextEvidenceCurrentStep("T2_excel documentado: " + t2Excel.format(formatoTimestamp));
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El archivo Excel se genera y el registro transiciona de En proceso a Terminado");

		/* PASO 14 ******************************************************************/
		addStep("Regresar a la pantalla Comprobantes de Compra y aplicar los mismos filtros. Dar clic en Consultar");
		boolean resultStep14Nav = comprobantesPage.regresarAComprobantesCompraDesdeMenuDirectos(true);
		assertTrue(resultStep14Nav, "Paso 14 fallido: No se pudo regresar a la pantalla Comprobantes de Compra desde el menu Directos del nav superior");
		boolean resultStep14Filtros = comprobantesPage.seleccionarRangoFechasSinEvidenciaIntermedia(fechaInicio, fechaFin);
		assertTrue(resultStep14Filtros, "Paso 14 fallido: No se selecciono nuevamente el rango de fechas " + fechaInicioUi + " - " + fechaFinUi);
		boolean resultStep14Consultar = pc4.consultarComprobantes(true) && comprobantesPage.validarGridConRegistros();
		assertTrue(resultStep14Consultar, "Paso 14 fallido: El grid no muestra registros filtrados al reaplicar el mismo rango");
		int totalRegistrosPdf = comprobantesPage.obtenerTotalRegistrosComprobantesCompraActual(true);
		assertTrue(totalRegistrosPdf == totalRegistrosExcel, "Paso 14 fallido: El grid muestra un N distinto al de la descarga Excel. N Excel: " + totalRegistrosExcel + " | N PDF: " + totalRegistrosPdf);
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El grid muestra el mismo N de registros que en la descarga Excel. N = " + totalRegistrosPdf);

		/* PASO 15 ******************************************************************/
		addStep("Registrar timestamp T1_pdf antes de solicitar la descarga PDF");
		boolean resultStep15Boton = comprobantesPage.capturarBotonDescargaMasivaPdf(true);
		assertTrue(resultStep15Boton, "Paso 15 fallido: No se pudo evidenciar el boton de descarga masiva PDF antes de registrar T1_pdf");
		LocalDateTime t1Pdf = LocalDateTime.now();
		testCase.addBoldTextEvidenceCurrentStep("T1_pdf documentado: " + t1Pdf.format(formatoTimestamp));
		System.out.println("[SCRIPT 215] T1_pdf: " + t1Pdf.format(formatoTimestamp));
		assertTrue(t1Pdf != null, "Paso 15 fallido: No se pudo registrar T1_pdf");

		/* PASO 16 Y PASO 17 **********************************************************/
		addStep("Dar clic en Descargar PDF masivo. Esperar que el estatus en Centro de Descargas cambie a Terminado y registrar T2_pdf");
		boolean resultStep16 = pc4.ClicBotonDescargarMasivoPDFplus1k(true);
		assertTrue(resultStep16, "Paso 16 fallido: No se solicito la descarga PDF o no se cerro el pop-up informativo");
		boolean resultStep16CentroDescargas = pc4.ClicBotonCentroDescarga(true);
		assertTrue(resultStep16CentroDescargas, "Paso 16 fallido: No se visualiza Centro de Descargas");
		long tGenPdf = comprobantesPage.esperarUltimaDescargaComprobantesCompraTerminada(t1Pdf, fechaInicio, fechaFin, 900, true, "zip");
		assertTrue(tGenPdf >= 0, "Paso 16-17 fallido: La solicitud PDF no llego a estatus Terminado dentro del tiempo maximo de 15 minutos");
		LocalDateTime t2Pdf = t1Pdf.plusSeconds(tGenPdf);
		testCase.addBoldTextEvidenceCurrentStep("T2_pdf documentado: " + t2Pdf.format(formatoTimestamp));
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El archivo PDF/ZIP se genera y el registro transiciona de En proceso a Terminado");

		/* PASO 18 ******************************************************************/
		addStep("Comparar T_gen_excel contra T_gen_pdf y validar el umbral de la premisa de la HU");
		testCase.addBoldTextEvidenceCurrentStep("T_gen_excel = " + tGenExcel + " segundos | T_gen_pdf = " + tGenPdf + " segundos");
		assertTrue(tGenExcel < tGenPdf, "Paso 18 fallido: T_gen_excel no es estrictamente menor que T_gen_pdf. T_gen_excel = " + tGenExcel + " | T_gen_pdf = " + tGenPdf);
		assertTrue(tGenExcel < umbralExcelSegundos, "Paso 18 fallido: T_gen_excel excede el umbral de " + umbralExcelSegundos + " segundos. T_gen_excel = " + tGenExcel + " segundos");
		testCase.addBoldTextEvidenceCurrentStep("Resultado final: T_gen_excel (" + tGenExcel + " s) es estrictamente menor que T_gen_pdf (" + tGenPdf + " s) y menor al umbral de " + umbralExcelSegundos + " s");
	}

	@AfterMethod
	public void afterMethod() {
		try {
			// Limpiar duplicados si fueron generados
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
		return "*1. Optimizacion de generacion de Excel implementada en backend Spring Boot, desplegada en QA, y datos representativos mayores a 1000 registros en Comprobantes de Compra.      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Verificar que el tiempo de generacion del archivo Excel de Comprobantes de Compra con la optimizacion instalada sea estrictamente menor al tiempo de generacion del PDF para el mismo volumen de registros, en el perfil ADMIN.";
	}

	@Override
	public String setTestDesigner() {
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_215_VerificarTiempoExcelMenorQuePdfComprobantesCompraADMIN_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}
