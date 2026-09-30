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

/**DESCRIPCION: Validar que la columna Precio en el Excel de resumen de Cambios Fisicos Pendientes
 * muestra el simbolo de pesos mexicanos ($) y formato monetario - perfil OXXO OS.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_315 [CA06, CA07] - Validar columna Precio en Excel de resumen con formato monetario y simbolo $ - OXXO OS.
 *
 * PREREQUISITOS:
 * 1. Usuario activo en ambiente QA con perfil OXXO OS (usuario 3333333).
 * 2. Registros con sale_price mayor a cero disponibles en Cambios Fisicos Pendientes.
 * 3. Acceso a Centro de descarga habilitado para el usuario.
 *
 * INSUMOS:
 * Credenciales BD cometa configuradas en GlobalVariables.
 * Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
 * @author Michelle Ceja
 * @date agosto 2026
 */
public class ATC_FT_315_ValidarSimboloPesoColumnaPrecioExcelResumenCFPOXXOOS extends BaseExecution {

	private SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_315_ValidarSimboloPesoColumnaPrecioExcelResumenCFPOXXOOS_test(HashMap<String, String> data)
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
		assertTrue("3333333".equals(user),
				"CA06 CA07 fallido - Precondicion: El data provider no corresponde al perfil OXXO OS (usuario esperado: 3333333)");
		assertTrue(pc4.IngresarURL(url, true),
				"CA06 CA07 fallido - Paso 1: No se visualiza la pantalla de inicio de sesion del portal COMETA");
		testCase.addTextEvidenceCurrentStep("Se visualiza la pantalla de inicio de sesion del portal COMETA.");
		pc4.PrivatePortal();

		/*
		 *                                             PASO 2
		 ***************************************************************************/
		addStep("Ingresar las credenciales correspondientes al perfil de Cometa para el inicio de sesion (perfil OXXO OS)");
		password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null && !password.trim().isEmpty(),
				"CA06 CA07 fallido - Paso 2: No se encontro password para el usuario: " + user);
		assertTrue(pc4.IniciarSesionNoToken(user, password, true),
				"CA06 CA07 fallido - Paso 2: El sistema no autentica correctamente al perfil OXXO OS");
		testCase.addTextEvidenceCurrentStep("El sistema autentica correctamente y redirige a Access Control.");

		/*
		 *                                             PASO 3
		 ***************************************************************************/
		addStep("Seleccionar Portal COMETA > DIRECTOS > Cambios Fisicos > Cambios Fisicos Pendientes");
		assertTrue(pc4.seleccionarPortalCometaQAConRefresh(true),
				"CA06 CA07 fallido - Paso 3: No fue posible seleccionar Portal COMETA");
		assertTrue(pc4.clicBotonCometaDirectos(true),
				"CA06 CA07 fallido - Paso 3: No fue posible abrir el modulo Directos");
		assertTrue(pc4.ClicBotonCF(true),
				"CA06 CA07 fallido - Paso 3: No fue posible abrir la pantalla Cambios Fisicos");
		assertTrue(pc4.clickCambiosFisicosPendientesPantalla(true),
				"CA06 CA07 fallido - Paso 3: No fue posible ingresar a Cambios Fisicos Pendientes");
		testCase.addTextEvidenceCurrentStep("Se visualiza la pantalla Cambios Fisicos Pendientes con filtros disponibles.");

		/*
		 *                                             PASO 4
		 ***************************************************************************/
		addStep("Seleccionar un rango de fechas valido con registros y presionar Consultar");
		val = gen.validarMinimoRegistrosPorUsuario(user, 1);
		String filtro = gen.aplicarFiltrosNecesariosParaConsulta(user, pc4, val, true);
		assertTrue(filtro != null && !filtro.trim().isEmpty(),
				"CA06 CA07 fallido - Paso 4: No fue posible aplicar filtros para realizar la consulta");
		assertTrue(pc4.consultarComprobantes(true),
				"CA06 CA07 fallido - Paso 4: El grid no cargo resultados despues de presionar Consultar");
		testCase.addTextEvidenceCurrentStep("El grid se carga con resultados que incluyen valores en la columna Precio.");
		testCase.addTextEvidenceCurrentStep("Filtro aplicado: " + filtro);

		/*
		 *                                             PASO 5
		 ***************************************************************************/
		addStep("Ejecutar la descarga de resumen en Excel y esperar a que el archivo se descargue en el equipo");
		assertTrue(cfPage.validarBotonDescargaResumenExcelVisibleCFP(true),
				"CA06 CA07 fallido - Paso 5: No se visualiza habilitado el boton de descarga de resumen en Excel");
		LocalDateTime t1 = LocalDateTime.now();
		assertTrue(pc4.ClicBotonDescargarMasivoCFPend(true),
				"CA06 CA07 fallido - Paso 5: No fue posible iniciar la descarga del resumen en Excel");
		assertTrue(pc4.ClicBotonCentroDescarga(true),
				"CA06 CA07 fallido - Paso 5: No fue posible ingresar a Centro de descarga");
		long tiempoGeneracion = pc4.esperarUltimaDescargaCambiosFisicosTerminada(t1, 900, true);
		assertTrue(tiempoGeneracion >= 0,
				"CA06 CA07 fallido - Paso 5: El archivo no llego a estatus Terminado dentro del tiempo maximo");
		assertTrue(pc4.selUrlDescarga(true),
				"CA06 CA07 fallido - Paso 5: No fue posible descargar el archivo desde la URL del registro en Centro de Descargas");
		testCase.addTextEvidenceCurrentStep("El archivo Excel Resumen_CAMBIOS_FISICOS_PENDIENTES se descarga correctamente.");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Abrir el archivo Excel y localizar la columna Precio para validar simbolo $ y formato de celdas");
		assertTrue(cfPage.abrirResumenYMostrarColumnaPrecioEnEvidencia(true),
				"CA06 CA07 fallido - Paso 6: No fue posible abrir el Excel descargado y navegar a la columna Precio");
		testCase.addTextEvidenceCurrentStep("La columna Precio se visualiza en el archivo descargado.");
		assertTrue(cfPage.validarSimboloPesoEnColumnaPrecioExcelResumenCFP(false),
				"CA06 fallido - Paso 6: Los valores de la columna Precio en el Excel no incluyen el simbolo $ o no tienen formato monetario correcto");
		testCase.addTextEvidenceCurrentStep(
				"Los valores de la columna Precio en el Excel se muestran con el simbolo $ y formato monetario (ejemplo: $12.50).");
		assertTrue(cfPage.validarEncabezadosResumenCFPendientesConPrecio(true),
				"CA07 fallido - Paso 6: El formato de celdas de la columna Precio en el Excel no cumple la presentacion y alineacion esperadas conforme al diseno");
		testCase.addTextEvidenceCurrentStep(
				"Las celdas de la columna Precio presentan formato monetario en pesos mexicanos con alineacion conforme al diseno del modulo.");
	}

	@AfterMethod
	public void afterMethod() {
		try {
			Process cierrePorTitulo = new ProcessBuilder(
					"cmd",
					"/c",
					"taskkill /f /fi \"IMAGENAME eq EXCEL.EXE\" /fi \"WINDOWTITLE eq *Resumen_CAMBIOS_FISICOS_PENDIENTES*\"")
					.start();
			int exitCode = cierrePorTitulo.waitFor();

			if (exitCode != 0) {
				new ProcessBuilder("cmd", "/c", "taskkill /f /im EXCEL.EXE").start().waitFor();
			}
		} catch (Exception ignored) {
			try {
				new ProcessBuilder("cmd", "/c", "taskkill /f /im EXCEL.EXE").start().waitFor();
			} catch (Exception ignoredFallback) {
			}
		}

		if (u != null) {
			u.quit();
		}
	}

	@Override
	public void beforeTest() {

	}

	@Override
	public String setPrerequisites() {
		return "*1. Usuario OXXO OS activo en QA (3333333).\r\n"
				+ "*2. Registros con sale_price mayor a cero disponibles en Cambios Fisicos Pendientes.\r\n"
				+ "*3. Acceso a Centro de descarga habilitado para el usuario.";
	}

	@Override
	public String setTestDescription() {
		return "Validar columna Precio en Excel de resumen de Cambios Fisicos Pendientes con formato monetario y simbolo $ - OXXO OS.";
	}

	@Override
	public String setTestDesigner() {
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_315_ValidarSimboloPesoColumnaPrecioExcelResumenCFPOXXOOS_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}



