package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.cambiosFisicosPage;
import WEB.cometa.om.generaDuplicadosCambFisPendiente;
import WEB.cometa.om.generaDuplicadosCambFisPendiente.ResultadoValidacionDuplicadosCambFisPendiente;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCION: Validar nombre del archivo Excel descargado igual a Resumen_CAMBIOS_FISICOS_PENDIENTES en pantalla Cambios Fisicos Pendientes - Admin.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_308 [CA01] - Validar nombre del archivo Excel descargado igual a Resumen_CAMBIOS_FISICOS_PENDIENTES - Comercial.
 *
 * PREREQUISITOS:
 * 1. Usuario activo en ambiente QA con perfil COMERCIAL (usuario 2222222).
 * 2. Registros disponibles en grid de Cambios Fisicos Pendientes.
 *
 * INSUMOS:
 * Credenciales BD cometa configuradas en GlobalVariables.
 * Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
 * @author Michelle Ceja
 * @date agosto 2026
 */
public class ATC_FT_308_ValidarNombreArchivoExcelResumenCFPendientesComercial extends BaseExecution {

	private SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_308_ValidarNombreArchivoExcelResumenCFPendientesComercial_test(HashMap<String, String> data)
			throws Exception {

		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");
		u = new SeleniumUtil(new MEdgeTest(), true);

		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		cambiosFisicosPage cambiosFisicos = new cambiosFisicosPage(u, testCase);
		generaDuplicadosCambFisPendiente gen = new generaDuplicadosCambFisPendiente(testCase);

		String user = data.get("user");
		String url = data.get("Url");

		addStep("Precondicion: validar perfil ADMIN, obtener credenciales y confirmar datos disponibles para la consulta");
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: COMERCIAL | Usuario: " + user);
		assertTrue("2222222".equals(user),
				"CA01 fallido - Precondicion: El data provider no corresponde al perfil COMERCIAL (usuario esperado: 2222222)");

		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null && !password.trim().isEmpty(),
				"CA01 fallido - Precondicion: No se encontro password para el usuario: " + user);

		ResultadoValidacionDuplicadosCambFisPendiente val = gen.validarMinimoRegistrosPorUsuario(user, 1);

		addStep("Ingresar a la URL del portal COMETA");
		assertTrue(pc4.IngresarURL(url, true),
				"CA01 fallido - Paso 1: No se visualiza la pantalla de inicio de sesion del portal COMETA");
		testCase.addTextEvidenceCurrentStep("Se visualiza la pantalla de inicio de sesion del portal COMETA.");
		pc4.PrivatePortal();

		addStep("Ingresar las credenciales correspondientes al perfil de Cometa para el inicio de sesion (perfil COMERCIAL)");
		assertTrue(pc4.IniciarSesionNoToken(user, password, true),
				"CA01 fallido - Paso 2: El sistema no autentica correctamente al perfil COMERCIAL");
		testCase.addTextEvidenceCurrentStep("El sistema autentica correctamente y redirige a Access Control.");

		addStep("Seleccionar la opcion Portal COMETA en Access Control");
		assertTrue(pc4.seleccionarPortalCometaQAConRefresh(true),
				"CA01 fallido - Paso 3: No fue posible seleccionar Portal COMETA");
		testCase.addTextEvidenceCurrentStep("Se visualiza la pantalla principal del portal COMETA.");

		addStep("Seleccionar el modulo DIRECTOS en el menu principal");
		assertTrue(pc4.clicBotonCometaDirectos(true),
				"CA01 fallido - Paso 4: No fue posible abrir el modulo Directos");
		testCase.addTextEvidenceCurrentStep(
				"Se despliega el menu del modulo Directos con sus opciones disponibles.");

		addStep("Seleccionar la opcion Cambios Fisicos");
		assertTrue(pc4.ClicBotonCF(true),
				"CA01 fallido - Paso 5: No fue posible abrir la pantalla Cambios Fisicos");
		testCase.addTextEvidenceCurrentStep("Se visualiza la pantalla Cambios Fisicos con sus filtros.");

		addStep("Ingresar a Cambios Fisicos Pendientes");
		assertTrue(pc4.clickCambiosFisicosPendientesPantalla(true),
				"CA01 fallido - Paso 6: No fue posible ingresar a Cambios Fisicos Pendientes");
		testCase.addTextEvidenceCurrentStep(
				"Se visualiza la pantalla Cambios Fisicos Pendientes con los filtros disponibles.");

		addStep("Seleccionar un rango de fechas valido que incluya registros y presionar Consultar");
		String filtro = gen.aplicarFiltrosNecesariosParaConsulta(user, pc4, val, true);
		assertTrue(filtro != null && !filtro.trim().isEmpty(),
				"CA01 fallido - Paso 7: No fue posible aplicar filtros para realizar la consulta");
		assertTrue(pc4.consultarComprobantes(true),
				"CA01 fallido - Paso 7: El grid no cargo resultados despues de presionar Consultar");
		testCase.addTextEvidenceCurrentStep(
				"El grid se carga con resultados y se habilitan las opciones de descarga.");
		testCase.addTextEvidenceCurrentStep("Filtro aplicado: " + filtro);

		addStep("Localizar el boton o icono de descarga de resumen en Excel en la seccion de descargas de la pantalla");
		assertTrue(cambiosFisicos.validarBotonDescargaResumenExcelVisibleCFP(true),
				"CA01 fallido - Paso 8: No se visualiza habilitado el boton o icono de descarga de resumen en Excel");
		testCase.addTextEvidenceCurrentStep(
				"El boton o icono de descarga de resumen en Excel se visualiza habilitado.");

		addStep("Presionar el boton o icono de descarga de resumen en Excel");
		LocalDateTime t1 = LocalDateTime.now();
		assertTrue(pc4.ClicBotonDescargarMasivoCFPend(true),
				"CA01 fallido - Paso 9: No fue posible iniciar la descarga del resumen en Excel");
		testCase.addTextEvidenceCurrentStep(
				"El sistema inicia la generacion del archivo y muestra el indicador de proceso.");

		addStep("Esperar a que el archivo se descargue en el equipo de computo");
		assertTrue(pc4.ClicBotonCentroDescarga(true),
				"CA01 fallido - Paso 10: No fue posible ingresar a Centro de descarga para monitorear el proceso");
		long tiempoGeneracionSegundos = pc4.esperarUltimaDescargaCambiosFisicosTerminada(t1, 900, true);
		assertTrue(tiempoGeneracionSegundos >= 0,
				"CA01 fallido - Paso 10: El archivo no llego a estatus Terminado dentro del tiempo maximo de espera");
		assertTrue(pc4.openLastFileDownloadFolder("Excel", true),
				"CA01 fallido - Paso 10: No se detecto archivo Excel descargado en el equipo");
		testCase.addTextEvidenceCurrentStep("El archivo Excel se descarga correctamente en el equipo.");

		addStep("Verificar el nombre del archivo descargado en el explorador de archivos o en la barra de descargas del navegador");
		String nombreArchivoDescargado = pc4.getLastDownloadedFileName();
		boolean nombreValido = nombreArchivoDescargado != null
				&& nombreArchivoDescargado.matches("^Resumen_CAMBIOS_FISICOS_PENDIENTES(?: \\(\\d+\\))?\\.(xlsx|xls)$");
		testCase.addTextEvidenceCurrentStep("Nombre de archivo detectado: " + nombreArchivoDescargado);
		assertTrue(nombreValido,
				"CA01 fallido - Paso 11: El nombre del archivo descargado no cumple la nomenclatura esperada Resumen_CAMBIOS_FISICOS_PENDIENTES con extension .xlsx/.xls (se permite sufijo de duplicado '(n)')");
		testCase.addTextEvidenceCurrentStep(
				"El nombre del archivo descargado cumple la nomenclatura esperada Resumen_CAMBIOS_FISICOS_PENDIENTES con extension .xlsx o .xls; si existe un archivo previo, se acepta el sufijo de duplicado '(n)'.");
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
		return "*1. Usuario COMERCIAL activo en QA (2222222).\r\n"
				+ "*2. Registros disponibles en la pantalla Cambios Fisicos Pendientes.\r\n"
				+ "*3. Acceso a Centro de descarga habilitado para el usuario.";
	}

	@Override
	public String setTestDescription() {
		return "Validar nombre del archivo Excel descargado igual a Resumen_CAMBIOS_FISICOS_PENDIENTES en pantalla Cambios Fisicos Pendientes - COMERCIAL.";
	}

	@Override
	public String setTestDesigner() {
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_308_ValidarNombreArchivoExcelResumenCFPendientesComercial_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}


