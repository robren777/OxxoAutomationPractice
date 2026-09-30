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

/**DESCRIPCION: Validar que el valor de columna Precio en pantalla coincide con sale_price en BD cometa - Comercial.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_298 [CA04] - Validar que el valor de columna Precio en pantalla coincide con sale_price en BD cometa - Comercial.
 *
 * PREREQUISITOS:
 * 1. Usuario activo en ambiente QA con perfil COMERCIAL (usuario 2222222).
 * 2. Acceso a BD cometa con permisos de lectura.
 * 3. Al menos 1 registro con sale_price no nulo en xxcm_cambios_fisicos_pendientes.
 *
 * INSUMOS:
 * Credenciales BD cometa configuradas en GlobalVariables.
 * Datos en xxcm_cambios_fisicos_pendientes con sale_price no nulo.
 * Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
 * @author Michelle Ceja
 * @date agosto 2026
 */
public class ATC_FT_298_ValidarValorPrecioGridCoincideSalePriceBDComercial extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_298_ValidarValorPrecioGridCoincideSalePriceBDComercial_test(HashMap<String, String> data)
			throws Exception {

		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");
		u = new SeleniumUtil(new MEdgeTest(), true);

		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		cambiosFisicosPage cfPage = new cambiosFisicosPage(u, testCase);
		generaDuplicadosCambFisPendiente gen = new generaDuplicadosCambFisPendiente(testCase);

		String user = data.get("user");
		String url = data.get("Url");
		addStep("Validar precondiciones de usuario y disponibilidad minima de datos para ejecutar el caso");
		testCase.addBoldTextEvidenceCurrentStep("Perfil: COMERCIAL | Usuario: " + user);
		assertTrue("2222222".equals(user),
				"CA04 fallido - Precondicion: El data provider no corresponde al perfil COMERCIAL (usuario esperado: 2222222)");

		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null && !password.trim().isEmpty(),
				"CA04 fallido - Precondicion: No se encontro password para el usuario: " + user);

		ResultadoValidacionDuplicadosCambFisPendiente val = gen.validarMinimoRegistrosPorUsuario(user, 1);
		testCase.addTextEvidenceCurrentStep(
				"Se validan las precondiciones de perfil, credenciales y disponibilidad minima de datos para ejecutar el flujo.");

		addStep("Ingresar a la URL del portal COMETA");
		assertTrue(pc4.IngresarURL(url, true), "CA04 fallido - Paso 2: No se visualiza la pantalla de inicio de sesion");
		testCase.addTextEvidenceCurrentStep("Se visualiza la pantalla de inicio de sesion del portal COMETA.");
		pc4.PrivatePortal();

		addStep("Ingresar las credenciales correspondientes al perfil de Cometa para el inicio de sesion (perfil COMERCIAL)");
		assertTrue(pc4.IniciarSesionNoToken(user, password, true),
				"CA04 fallido - Paso 3: El sistema no autentica correctamente al perfil COMERCIAL");
		testCase.addTextEvidenceCurrentStep("El sistema autentica correctamente y redirige a Access Control.");

		addStep("Seleccionar Portal COMETA > DIRECTOS > Cambios Fisicos > Cambios Fisicos Pendientes");
		assertTrue(pc4.seleccionarPortalCometaQAConRefresh(true),
				"CA04 fallido - Paso 4: No fue posible seleccionar Portal COMETA");
		assertTrue(pc4.clicBotonCometaDirectos(true),
				"CA04 fallido - Paso 4: No fue posible abrir el modulo Directos");
		assertTrue(pc4.ClicBotonCF(true), "CA04 fallido - Paso 4: No fue posible abrir la pantalla Cambios Fisicos");
		assertTrue(pc4.clickCambiosFisicosPendientesPantalla(true),
				"CA04 fallido - Paso 4: No fue posible ingresar a Cambios Fisicos Pendientes");
		testCase.addTextEvidenceCurrentStep("Se visualiza la pantalla Cambios Fisicos Pendientes con filtros disponibles.");

		addStep("Aplicar filtros que incluyan los registros identificados en el paso 1 y presionar Consultar");
		String filtro = gen.aplicarFiltrosNecesariosParaConsulta(user, pc4, val, true);
		assertTrue(filtro != null && !filtro.trim().isEmpty(),
				"CA04 fallido - Paso 5: No fue posible aplicar filtros para cargar registros");
		assertTrue(pc4.consultarComprobantes(true), "CA04 fallido - Paso 5: El grid no cargo resultados para comparar");
		testCase.addTextEvidenceCurrentStep("El grid carga resultados que incluyen registros para la comparacion.");
		testCase.addTextEvidenceCurrentStep("Filtro aplicado: " + filtro);

		addStep("Localizar registros en el grid y anotar SKU y valor visible en la columna Precio");
		assertTrue(cfPage.capturarValoresPrecioVisiblesEnGridCFP(3, true),
				"CA04 fallido - Paso 6: No fue posible capturar pares SKU-Precio visibles en el grid");
		testCase.addTextEvidenceCurrentStep(
				"Los SKU y valores de Precio visibles en pantalla quedan anotados para comparacion.");

		addStep("Consultar en BD cometa los valores de sale_price filtrando por los SKU obtenidos del grid en el paso previo");
		assertTrue(cfPage.consultarMuestraSalePriceBDCFPendientes(3, true),
				"CA04 fallido - Paso previo a Paso 7: La consulta en BD por SKU no retorno registros con sale_price para comparar");
		testCase.addTextEvidenceCurrentStep(
				"La consulta en BD retorna sale_price para los SKU capturados en el grid y queda lista para la comparacion final.");

		addStep("Comparar los valores de la columna Precio en pantalla con los valores de sale_price obtenidos de BD en el paso previo");
		assertTrue(cfPage.compararMuestraPrecioGridVsSalePriceBDCFP(true),
				"CA04 fallido - Paso 7: Los valores de Precio en pantalla no coinciden con sale_price en BD");
		testCase.addTextEvidenceCurrentStep(
				"Los valores mostrados en la columna Precio de la pantalla coinciden con los valores del campo sale_price en BD cometa para la muestra comparada.");
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
				+ "*2. Acceso a BD cometa con permisos de lectura.\r\n"
				+ "*3. Al menos 1 registro con sale_price no nulo en xxcm_cambios_fisicos_pendientes.";
	}

	@Override
	public String setTestDescription() {
		return "Validar que el valor de columna Precio en pantalla coincide con sale_price en BD cometa - COMERCIAL.";
	}

	@Override
	public String setTestDesigner() {
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_298_ValidarValorPrecioGridCoincideSalePriceBDComercial_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}