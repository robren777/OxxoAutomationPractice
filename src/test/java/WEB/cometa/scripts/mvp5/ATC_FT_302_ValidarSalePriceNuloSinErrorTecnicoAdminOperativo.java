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

/**DESCRIPCION: Validar que registro con sale_price nulo no genera error tecnico en pantalla Cambios Fisicos Pendientes - Admin Operativo.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_302 [CA04] - Validar que registro con sale_price nulo no genera error tecnico en pantalla Cambios Fisicos Pendientes - Admin Operativo.
 *
 * PREREQUISITOS:
 * 1. Existe al menos 1 registro con sale_price NULL en xxcm_cambios_fisicos_pendientes.
 * 2. Usuario activo en ambiente QA con perfil ADMIN OPERATIVO (usuario 1111111).
 *
 * INSUMOS:
 * Credenciales BD cometa configuradas en GlobalVariables.
 * Datos en xxcm_cambios_fisicos_pendientes con sale_price nulo.
 * Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
 * @author Michelle Ceja
 * @date agosto 2026
 */
public class ATC_FT_302_ValidarSalePriceNuloSinErrorTecnicoAdminOperativo extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_302_ValidarSalePriceNuloSinErrorTecnicoAdminOperativo_test(HashMap<String, String> data)
			throws Exception {

		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");
		u = new SeleniumUtil(new MEdgeTest(), true);

		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		cambiosFisicosPage cfPage = new cambiosFisicosPage(u, testCase);
		generaDuplicadosCambFisPendiente gen = new generaDuplicadosCambFisPendiente(testCase);

		String user = data.get("user");
		String url = data.get("Url");

		addStep("Consultar en BD cometa: SELECT id FROM xxcm_cambios_fisicos_pendientes WHERE sale_price IS NULL LIMIT 1; y anotar el identificador del registro con sale_price NULL");
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: ADMIN OPERATIVO | Usuario: " + user);
		assertTrue("1111111".equals(user),
				"CA04 fallido - Precondicion: El data provider no corresponde al perfil ADMIN OPERATIVO (usuario esperado: 1111111)");
		assertTrue(cfPage.consultarMuestraSalePriceNuloBDCFPendientesPorUsuario(user, 1, true),
				"CA04 fallido - Paso 1: La consulta no retorno registros con sale_price NULL para ejecutar la validacion");
		testCase.addTextEvidenceCurrentStep("La consulta retorna al menos 1 registro con sale_price NULL.");

		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null && !password.trim().isEmpty(),
				"CA04 fallido - Precondicion: No se encontro password para el usuario: " + user);

		ResultadoValidacionDuplicadosCambFisPendiente val = gen.validarMinimoRegistrosPorUsuario(user, 1);

		addStep("Ingresar a la URL del portal COMETA");
		assertTrue(pc4.IngresarURL(url, true), "CA04 fallido - Paso 2: No se visualiza la pantalla de inicio de sesion");
		testCase.addTextEvidenceCurrentStep("Se visualiza la pantalla de inicio de sesion del portal COMETA.");
		pc4.PrivatePortal();

		addStep("Ingresar las credenciales correspondientes al perfil de Cometa para el inicio de sesion (perfil ADMIN OPERATIVO)");
		assertTrue(pc4.IniciarSesionNoToken(user, password, true),
				"CA04 fallido - Paso 3: El sistema no autentica correctamente al perfil ADMIN OPERATIVO");
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

		addStep("Aplicar filtros que incluyan el registro con sale_price NULL identificado en el paso 1 y presionar Consultar");
		String filtro = gen.aplicarFiltrosNecesariosParaConsulta(user, pc4, val, true);
		assertTrue(filtro != null && !filtro.trim().isEmpty(),
				"CA04 fallido - Paso 5: No fue posible aplicar filtros para cargar registros");
		assertTrue(cfPage.filtrarPorSkuSalePriceNuloAntesDeConsultarCFP(true),
				"CA04 fallido - Paso 5: No fue posible cargar en filtro el SKU obtenido de BD con sale_price NULL");
		assertTrue(pc4.consultarComprobantes(true),
				"CA04 fallido - Paso 5: El grid no cargo resultados para validar sale_price NULL");
		testCase.addTextEvidenceCurrentStep("El grid carga resultados y se mantiene visible para ejecutar la validacion.");
		testCase.addTextEvidenceCurrentStep("Filtro aplicado: " + filtro);

		addStep("Verificar que la pantalla no presenta mensaje de error tecnico crash ni pantalla en blanco al mostrar el registro con sale_price NULL");
		assertTrue(cfPage.validarPantallaSinErrorTecnicoCFPendientes(true),
				"CA04 fallido - Paso 6: La pantalla presenta error tecnico, crash o no muestra contenido util");
		testCase.addTextEvidenceCurrentStep(
				"La pantalla se visualiza sin errores tecnicos; la interfaz se mantiene operable en Cambios Fisicos Pendientes.");

		addStep("Observar el valor mostrado en la columna Precio para el registro con sale_price NULL");
		assertTrue(cfPage.validarValorNeutroPrecioParaSalePriceNuloEnGridCFP(true),
				"CA04 fallido - Paso 7: El valor de Precio para registro con sale_price NULL no es neutro o no se localizo en grid");
		testCase.addTextEvidenceCurrentStep(
				"El campo Precio muestra un valor neutro sin generar error tecnico visible al usuario.");
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
		return "*1. Existe al menos 1 registro con sale_price NULL en xxcm_cambios_fisicos_pendientes.\r\n"
				+ "*2. Usuario ADMIN OPERATIVO activo en QA (1111111).\r\n"
				+ "*3. Acceso a BD cometa con permisos de lectura.";
	}

	@Override
	public String setTestDescription() {
		return "Validar que registro con sale_price nulo no genera error tecnico en pantalla Cambios Fisicos Pendientes - ADMIN OPERATIVO.";
	}

	@Override
	public String setTestDesigner() {
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_302_ValidarSalePriceNuloSinErrorTecnicoAdminOperativo_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}
