package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;
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

/**DESCRIPCION: Validar alineacion centrada del encabezado y contenido de columna Precio en pantalla Cambios Fisicos Pendientes - OXXO OS.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_290 [CA04, CA07] - Validar alineacion centrada del encabezado y contenido de columna Precio en pantalla - OXXO OS.
 *
 * PREREQUISITOS:
 * 1. Usuario activo en ambiente QA con perfil OXXO OS (usuario 3333333).
 * 2. Al menos 1 registro visible en grid de Cambios Fisicos Pendientes con valor en columna Precio.
 *
 * INSUMOS:
 * Datos en xxcm_cambios_fisicos_pendientes con valores visibles en columna Precio para el usuario OXXO OS.
 * Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
 * @author Michelle Ceja
 * @date agosto 2026
 */
public class ATC_FT_290_ValidarAlineacionCentradaPrecioGridCFPendientesOXXOOS extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_290_ValidarAlineacionCentradaPrecioGridCFPendientesOXXOOS_test(HashMap<String, String> data) throws Exception {

		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");
		u = new SeleniumUtil(new MEdgeTest(), true);

		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		cambiosFisicosPage cfPage = new cambiosFisicosPage(u, testCase);
		generaDuplicadosCambFisPendiente gen = new generaDuplicadosCambFisPendiente(testCase);

		String user = data.get("user");
		String url = data.get("Url");

		addStep("Precondicion: obtener credenciales del perfil OXXO OS y validar usuario configurado");
		testCase.addBoldTextEvidenceCurrentStep("Perfil: OXXO OS | Usuario: " + user);
		assertTrue("3333333".equals(user), "El data provider no corresponde al perfil OXXO OS (usuario esperado: 3333333)");

		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null && !password.trim().isEmpty(), "No se encontro password para el usuario: " + user);

		ResultadoValidacionDuplicadosCambFisPendiente val = gen.validarMinimoRegistrosPorUsuario(user, 1);

		addStep("Ingresar a la URL del portal COMETA");
		assertTrue(pc4.IngresarURL(url, true), "CA04/CA07 fallido - Paso 1");
		testCase.addTextEvidenceCurrentStep("Se visualiza la pantalla de inicio de sesion del portal COMETA.");
		pc4.PrivatePortal();

		addStep("Ingresar las credenciales correspondientes al perfil de Cometa para el inicio de sesion (perfil OXXO OS)");
		assertTrue(pc4.IniciarSesionNoToken(user, password, true), "CA04/CA07 fallido - Paso 2");
		testCase.addTextEvidenceCurrentStep("El sistema autentica correctamente y redirige a Access Control.");

		addStep("Seleccionar la opcion Portal COMETA en Access Control");
		assertTrue(pc4.seleccionarPortalCometaQAConRefresh(true), "CA04/CA07 fallido - Paso 3");
		testCase.addTextEvidenceCurrentStep("Se visualiza la pantalla principal del portal COMETA.");

		addStep("Seleccionar el modulo DIRECTOS en el menu principal");
		assertTrue(pc4.clicBotonCometaDirectos(true), "CA04/CA07 fallido - Paso 4");
		testCase.addTextEvidenceCurrentStep("Se despliega el menu del modulo Directos con sus opciones disponibles.");

		addStep("Seleccionar la opcion Cambios Fisicos");
		assertTrue(pc4.ClicBotonCF(true), "CA04/CA07 fallido - Paso 5");
		testCase.addTextEvidenceCurrentStep("Se visualiza la pantalla Cambios Fisicos con sus filtros.");

		addStep("Ingresar a Cambios Fisicos Pendientes");
		assertTrue(pc4.clickCambiosFisicosPendientesPantalla(true), "CA04/CA07 fallido - Paso 6");
		testCase.addTextEvidenceCurrentStep("Se visualiza la pantalla Cambios Fisicos Pendientes con los filtros disponibles.");

		addStep("Seleccionar un rango de fechas valido que incluya registros y presionar Consultar");
		String filtro = gen.aplicarFiltrosNecesariosParaConsulta(user, pc4, val, true);
		assertTrue(filtro != null && !filtro.trim().isEmpty(), "CA04/CA07 fallido - Paso 7: No fue posible aplicar filtros");
		assertTrue(pc4.consultarComprobantes(true), "CA04/CA07 fallido - Paso 7: El grid no se cargo con registros visibles");
		testCase.addTextEvidenceCurrentStep("El grid se carga con registros visibles incluyendo la columna Precio.");
		testCase.addTextEvidenceCurrentStep("Filtro aplicado: " + filtro);

		addStep("Revisar la alineacion del encabezado Precio en el grid");
		assertTrue(cfPage.validarAlineacionCentradaEncabezadoPrecioGridCFP(true), "CA04/CA07 fallido - Paso 8: El encabezado de la columna Precio no esta alineado de forma centrada");
		testCase.addTextEvidenceCurrentStep("El encabezado de la columna Precio esta alineado de forma centrada en la celda de encabezado.");

		addStep("Revisar la alineacion de los valores de datos en la columna Precio para al menos 3 registros del grid");
		assertTrue(cfPage.validarAlineacionCentradaContenidoPrecioGridCFP(3, true), "CA04/CA07 fallido - Paso 9: El contenido de la columna Precio no mantiene alineacion centrada consistente");
		testCase.addTextEvidenceCurrentStep("El contenido de los registros en la columna Precio esta alineado de forma centrada consistente con el diseno visual actual del modulo.");
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
		return "*1. Usuario OXXO OS activo en QA (3333333).\r\n"
				+ "*2. Al menos 1 registro visible en grid con valor en Precio.\r\n"
				+ "*3. Liga de Portal Cometa.";
	}

	@Override
	public String setTestDescription() {
		return "Validar alineacion centrada del encabezado y contenido de columna Precio en pantalla Cambios Fisicos Pendientes - OXXO OS.";
	}

	@Override
	public String setTestDesigner() {
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_290_ValidarAlineacionCentradaPrecioGridCFPendientesOXXOOS_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}