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

/**DESCRIPCION: Validar que el Excel de resumen mantiene columnas existentes en mismo orden y agrega columna Precio en Cambios Fisicos Pendientes - Admin.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_309 [CA02] - Validar que el Excel de resumen mantiene columnas existentes en mismo orden y agrega columna Precio - Admin.
 *
 * PREREQUISITOS:
 * 1. Usuario activo en ambiente QA con perfil ADMIN (usuario 5555555).
 * 2. Registros disponibles en grid de Cambios Fisicos Pendientes.
 * 3. Estructura previa del Excel de resumen conocida antes de HU 308163.
 *
 * INSUMOS:
 * Credenciales BD cometa configuradas en GlobalVariables.
 * Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
 * @author Michelle Ceja
 * @date agosto 2026
 */
public class ATC_FT_309_ValidarExcelResumenMantieneColumnasYAgregaPrecioAdmin extends BaseExecution {

	private SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_309_ValidarExcelResumenMantieneColumnasYAgregaPrecioAdmin_test(HashMap<String, String> data)
			throws Exception {

		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");
		u = new SeleniumUtil(new MEdgeTest(), true);

		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		cambiosFisicosPage cfPage = new cambiosFisicosPage(u, testCase);
		generaDuplicadosCambFisPendiente gen = new generaDuplicadosCambFisPendiente(testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String password = "";
		ResultadoValidacionDuplicadosCambFisPendiente val;

		addStep("Ingresar a la URL del portal COMETA");
		assertTrue("5555555".equals(user),
				"CA02 fallido - Precondicion: El data provider no corresponde al perfil ADMIN (usuario esperado: 5555555)");
		assertTrue(pc4.IngresarURL(url, true),
				"CA02 fallido - Paso 1: No se visualiza la pantalla de inicio de sesion del portal COMETA");
		testCase.addTextEvidenceCurrentStep("Se visualiza la pantalla de inicio de sesion del portal COMETA.");
		pc4.PrivatePortal();

		addStep("Ingresar las credenciales correspondientes al perfil de Cometa para el inicio de sesion (perfil ADMIN)");
		password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null && !password.trim().isEmpty(),
				"CA02 fallido - Paso 2: No se encontro password para el usuario: " + user);
		assertTrue(pc4.IniciarSesionNoToken(user, password, true),
				"CA02 fallido - Paso 2: El sistema no autentica correctamente al perfil ADMIN");
		testCase.addTextEvidenceCurrentStep("El sistema autentica correctamente y redirige a Access Control.");

		addStep("Seleccionar Portal COMETA > DIRECTOS > Cambios Fisicos > Cambios Fisicos Pendientes");
		assertTrue(pc4.seleccionarPortalCometaQAConRefresh(true),
				"CA02 fallido - Paso 3: No fue posible seleccionar Portal COMETA");
		assertTrue(pc4.clicBotonCometaDirectos(true),
				"CA02 fallido - Paso 3: No fue posible abrir el modulo Directos");
		assertTrue(pc4.ClicBotonCF(true),
				"CA02 fallido - Paso 3: No fue posible abrir la pantalla Cambios Fisicos");
		assertTrue(pc4.clickCambiosFisicosPendientesPantalla(true),
				"CA02 fallido - Paso 3: No fue posible ingresar a Cambios Fisicos Pendientes");
		testCase.addTextEvidenceCurrentStep("Se visualiza la pantalla Cambios Fisicos Pendientes con filtros disponibles.");

		addStep("Seleccionar un rango de fechas valido que incluya registros y presionar Consultar");
		val = gen.validarMinimoRegistrosPorUsuario(user, 1);
		String filtro = gen.aplicarFiltrosNecesariosParaConsulta(user, pc4, val, true);
		assertTrue(filtro != null && !filtro.trim().isEmpty(),
				"CA02 fallido - Paso 4: No fue posible aplicar filtros para realizar la consulta");
		assertTrue(pc4.consultarComprobantes(true),
				"CA02 fallido - Paso 4: El grid no cargo resultados despues de presionar Consultar");
		testCase.addTextEvidenceCurrentStep("El grid se carga con resultados y se habilitan las opciones de descarga.");
		testCase.addTextEvidenceCurrentStep("Filtro aplicado: " + filtro);

		addStep("Ejecutar la descarga de resumen en Excel y esperar a que el archivo se descargue");
		assertTrue(cfPage.validarBotonDescargaResumenExcelVisibleCFP(true),
				"CA02 fallido - Paso 5: No se visualiza habilitado el boton o icono de descarga de resumen en Excel");
		LocalDateTime t1 = LocalDateTime.now();
		assertTrue(pc4.ClicBotonDescargarMasivoCFPend(true),
				"CA02 fallido - Paso 5: No fue posible iniciar la descarga del resumen en Excel");
		assertTrue(pc4.ClicBotonCentroDescarga(true),
				"CA02 fallido - Paso 5: No fue posible ingresar a Centro de descarga para monitorear el proceso");
		long tiempoGeneracionSegundos = pc4.esperarUltimaDescargaCambiosFisicosTerminada(t1, 900, true);
		assertTrue(tiempoGeneracionSegundos >= 0,
				"CA02 fallido - Paso 5: El archivo no llego a estatus Terminado dentro del tiempo maximo de espera");
		testCase.addTextEvidenceCurrentStep("El archivo Excel Resumen_CAMBIOS_FISICOS_PENDIENTES se descarga en el equipo.");

		addStep("En la pantalla Centro de Descargas, descargar el archivo desde la URL del registro generado y revisar la lista completa de columnas presentes");
		assertTrue(pc4.selUrlDescarga(true),
				"CA02 fallido - Paso 6: No fue posible descargar el archivo desde la URL del registro en Centro de Descargas");
		assertTrue(cfPage.abrirResumenYMostrarColumnaPrecioEnEvidencia(true),
				"CA02 fallido - Paso 6: No fue posible abrir el Excel descargado y navegar visualmente hasta la columna Precio");
		assertTrue(cfPage.validarEncabezadosResumenCFPendientesConPrecio(true),
				"CA02 fallido - Paso 6: El archivo Excel no contiene las columnas esperadas para el resumen de Cambios Fisicos");
		testCase.addTextEvidenceCurrentStep("El archivo descargado desde Centro de Descargas se abre correctamente y muestra las columnas esperadas.");

		addStep("Comparar el orden y los nombres de las columnas del Excel descargado contra la estructura de columnas previa conocida (antes de HU 308163)");
		assertTrue(pc4.validarTitulosYFormatoCostoExcel(true),
				"CA02 fallido - Paso 7: El orden o nombre de columnas preexistentes no coincide con la estructura esperada");
		testCase.addTextEvidenceCurrentStep(
				"Las columnas preexistentes del Excel conservan nombre y orden esperado en la estructura evaluada.");

		addStep("Verificar que la columna Precio es la unica adicion respecto a la estructura anterior y que se encuentra ubicada despues de la columna Costo");
		assertTrue(cfPage.validarEncabezadosResumenCFPendientesConPrecio(true),
				"CA02 fallido - Paso 8: En el Excel de resumen, la columna Precio no se encuentra visible o no esta ubicada inmediatamente despues de Costo");
		testCase.addTextEvidenceCurrentStep(
				"Se valida que la columna Precio se ubica inmediatamente despues de Costo conforme al criterio CA02.");
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
		return "*1. Usuario ADMIN activo en QA (5555555).\r\n"
				+ "*2. Registros disponibles en la pantalla Cambios Fisicos Pendientes.\r\n"
				+ "*3. Estructura previa del Excel de resumen conocida antes de HU 308163.";
	}

	@Override
	public String setTestDescription() {
		return "Validar que el Excel de resumen mantiene columnas existentes en mismo orden y agrega columna Precio en Cambios Fisicos Pendientes - ADMIN.";
	}

	@Override
	public String setTestDesigner() {
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_309_ValidarExcelResumenMantieneColumnasYAgregaPrecioAdmin_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}

