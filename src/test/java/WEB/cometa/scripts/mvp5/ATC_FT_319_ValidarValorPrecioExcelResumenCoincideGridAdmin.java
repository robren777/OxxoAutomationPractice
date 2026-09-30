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

/**DESCRIPCION: Validar que el valor de la columna Precio en el Excel de resumen de Cambios Fisicos
 * Pendientes coincide exactamente con el valor mostrado en el grid de la pantalla - perfil ADMIN.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_319 [CA06] - Validar que el valor de Precio en Excel coincide con el valor mostrado en el grid de pantalla - Admin.
 *
 * PREREQUISITOS:
 * 1. Usuario activo en ambiente QA con perfil ADMIN (usuario 5555555).
 * 2. Al menos 3 registros con sale_price no nulo visibles en el grid de Cambios Fisicos Pendientes.
 * 3. Acceso a Centro de descarga habilitado para el usuario.
 *
 * INSUMOS:
 * Credenciales BD cometa configuradas en GlobalVariables.
 * Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
 * @author Michelle Ceja
 * @date agosto 2026
 */
public class ATC_FT_319_ValidarValorPrecioExcelResumenCoincideGridAdmin extends BaseExecution {

	private SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_319_ValidarValorPrecioExcelResumenCoincideGridAdmin_test(HashMap<String, String> data)
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

		/*
		 *                                             PASO 1
		 ***************************************************************************/
		addStep("Ingresar a la URL del portal COMETA");
		assertTrue("5555555".equals(user),
				"CA06 fallido - Precondicion: El data provider no corresponde al perfil ADMIN (usuario esperado: 5555555)");
		assertTrue(pc4.IngresarURL(url, true),
				"CA06 fallido - Paso 1: No se visualiza la pantalla de inicio de sesion del portal COMETA");
		testCase.addTextEvidenceCurrentStep("Se visualiza la pantalla de inicio de sesion del portal COMETA.");
		pc4.PrivatePortal();

		/*
		 *                                             PASO 2
		 ***************************************************************************/
		addStep("Ingresar las credenciales correspondientes al perfil de Cometa para el inicio de sesion (perfil ADMIN)");
		password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null && !password.trim().isEmpty(),
				"CA06 fallido - Paso 2: No se encontro password para el usuario: " + user);
		assertTrue(pc4.IniciarSesionNoToken(user, password, true),
				"CA06 fallido - Paso 2: El sistema no autentica correctamente al perfil ADMIN");
		testCase.addTextEvidenceCurrentStep("El sistema autentica correctamente y redirige a Access Control.");

		/*
		 *                                             PASO 3
		 ***************************************************************************/
		addStep("Seleccionar Portal COMETA > DIRECTOS > Cambios Fisicos > Cambios Fisicos Pendientes");
		assertTrue(pc4.seleccionarPortalCometaQAConRefresh(true),
				"CA06 fallido - Paso 3: No fue posible seleccionar Portal COMETA");
		assertTrue(pc4.clicBotonCometaDirectos(true),
				"CA06 fallido - Paso 3: No fue posible abrir el modulo Directos");
		assertTrue(pc4.ClicBotonCF(true),
				"CA06 fallido - Paso 3: No fue posible abrir la pantalla Cambios Fisicos");
		assertTrue(pc4.clickCambiosFisicosPendientesPantalla(true),
				"CA06 fallido - Paso 3: No fue posible ingresar a Cambios Fisicos Pendientes");
		testCase.addTextEvidenceCurrentStep("Se visualiza la pantalla Cambios Fisicos Pendientes con filtros disponibles.");

		/*
		 *                                             PASO 4
		 ***************************************************************************/
		addStep("Seleccionar un rango de fechas valido con registros y presionar Consultar");
		val = gen.validarMinimoRegistrosPorUsuario(user, 3);
		String filtro = gen.aplicarFiltrosNecesariosParaConsulta(user, pc4, val, true);
		assertTrue(filtro != null && !filtro.trim().isEmpty(),
				"CA06 fallido - Paso 4: No fue posible aplicar filtros para realizar la consulta");
		assertTrue(pc4.consultarComprobantes(true),
				"CA06 fallido - Paso 4: El grid no cargo resultados despues de presionar Consultar");
		testCase.addTextEvidenceCurrentStep("El grid se carga con resultados incluyendo valores en la columna Precio.");
		testCase.addTextEvidenceCurrentStep("Filtro aplicado: " + filtro);

		/*
		 *                                             PASO 5
		 ***************************************************************************/
		addStep("Identificar 3 registros visibles en el grid y anotar el campo identificador y el valor de la columna Precio de cada uno");
		assertTrue(cfPage.capturarValoresPrecioVisiblesEnGridCFP(3, true),
				"CA06 fallido - Paso 5: No fue posible capturar al menos 3 pares SKU-Precio del grid de Cambios Fisicos Pendientes");
		testCase.addTextEvidenceCurrentStep("Los datos de los 3 registros (SKU como campo clave y Precio) quedan capturados para la comparacion.");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Presionar el boton o icono de descarga de resumen en Excel y esperar a que el archivo se descargue");
		assertTrue(cfPage.validarBotonDescargaResumenExcelVisibleCFP(true),
				"CA06 fallido - Paso 6: No se visualiza habilitado el boton de descarga de resumen en Excel");
		LocalDateTime t1 = LocalDateTime.now();
		assertTrue(pc4.ClicBotonDescargarMasivoCFPend(true),
				"CA06 fallido - Paso 6: No fue posible iniciar la descarga del resumen en Excel");
		assertTrue(pc4.ClicBotonCentroDescarga(true),
				"CA06 fallido - Paso 6: No fue posible ingresar a Centro de descarga");
		long tiempoGeneracion = pc4.esperarUltimaDescargaCambiosFisicosTerminada(t1, 900, true);
		assertTrue(tiempoGeneracion >= 0,
				"CA06 fallido - Paso 6: El archivo no llego a estatus Terminado dentro del tiempo maximo");
		assertTrue(pc4.selUrlDescarga(true),
				"CA06 fallido - Paso 6: No fue posible descargar el archivo desde la URL del registro en Centro de Descargas");
		testCase.addTextEvidenceCurrentStep("El archivo Excel Resumen_CAMBIOS_FISICOS_PENDIENTES se descarga correctamente.");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Abrir el archivo Excel, localizar los 3 registros identificados y comparar los valores de Precio contra el grid para los mismos SKUs");
		assertTrue(cfPage.abrirResumenYSeleccionarFilasSkusCapturadosEnEvidencia(3, true),
				"CA06 fallido - Paso 7: No fue posible abrir el Excel descargado y localizar/seleccionar las filas de los SKUs capturados en el grid");
		assertTrue(cfPage.compararPreciosGridVsExcelResumenCFP(true),
				"CA06 fallido - Paso 7: Los valores de la columna Precio en el Excel no coinciden exactamente con los valores mostrados en el grid para los mismos SKUs capturados");
		testCase.addTextEvidenceCurrentStep("Los 3 SKUs se localizan en el Excel y sus valores de Precio coinciden con el grid.");
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
				+ "*2. Al menos 3 registros con sale_price no nulo visibles en Cambios Fisicos Pendientes.\r\n"
				+ "*3. Acceso a Centro de descarga habilitado para el usuario.";
	}

	@Override
	public String setTestDescription() {
		return "Validar que el valor de la columna Precio en el Excel de resumen coincide con el valor mostrado en el grid de Cambios Fisicos Pendientes - ADMIN.";
	}

	@Override
	public String setTestDesigner() {
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_319_ValidarValorPrecioExcelResumenCoincideGridAdmin_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}

