package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.CometaNuevaIdentidadVisualPage;
import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.generaDuplicadosCambFisPendiente;
import WEB.cometa.om.generaDuplicadosCambFisPendiente.ResultadoValidacionDuplicadosCambFisPendiente;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCION: Validar que la columna Precio sea visible en la pantalla Cambios Fisicos Pendientes
 * y que este ubicada inmediatamente despues de la columna Costo para el perfil OXXO OS.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_280 [CA03] - Validar columna Precio visible en pantalla Cambios Fisicos Pendientes
 *                     inmediatamente despues de columna Costo - OXXO OS
 *
 * @author Michelle Ceja
 * @date 2026-08-04
 */
public class ATC_FT_280_ValidarColumnaPrecioVisibleDespuesCostoCFPendientesOXXOOS extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_280_ValidarColumnaPrecioVisibleDespuesCostoCFPendientesOXXOOS_test(
			HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		CometaNuevaIdentidadVisualPage identidadVisualPage = new CometaNuevaIdentidadVisualPage(u, testCase);
		generaDuplicadosCambFisPendiente generadorDuplicadosClase = new generaDuplicadosCambFisPendiente(testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "OXXO_OS";

		addStep("Precondicion: obtener credenciales del perfil OXXO OS y validar usuario configurado");
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
		assertTrue("3333333".equals(user), "El data provider no corresponde al perfil OXXO OS (usuario esperado: 3333333)");
		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null && !password.trim().isEmpty(), "No se encontro password para el usuario: " + user);
		ResultadoValidacionDuplicadosCambFisPendiente validacionRegistros = generadorDuplicadosClase.validarMinimoRegistrosPorUsuario(user, 1);

		addStep("Ingresar a la URL del portal COMETA");
		assertTrue(pc4.IngresarURL(url, true), "CA03 fallido - Paso 1: No se muestra la pantalla de inicio de sesion");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Se visualiza la pantalla de inicio de sesion del portal COMETA.");
		pc4.PrivatePortal();

		addStep("Ingresar las credenciales correspondientes al perfil de Cometa para el inicio de sesion (perfil OXXO OS)");
		assertTrue(pc4.IniciarSesionNoToken(user, password, true), "CA03 fallido - Paso 2: El sistema no autentica al perfil OXXO OS");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El sistema autentica correctamente y redirige a Access Control.");

		addStep("Seleccionar la opcion Portal COMETA en Access Control");
		assertTrue(pc4.seleccionarPortalCometaQAConRefresh(true), "CA03 fallido - Paso 3: No fue posible seleccionar Portal COMETA");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Se visualiza la pantalla principal del portal COMETA.");

		addStep("Seleccionar el modulo DIRECTOS en el menu principal");
		assertTrue(pc4.clicBotonCometaDirectos(true), "CA03 fallido - Paso 4: No se despliega el menu del modulo Directos");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Se despliega el menu del modulo Directos con sus opciones disponibles.");

		addStep("Seleccionar la opcion Cambios Fisicos");
		assertTrue(pc4.ClicBotonCF(true), "CA03 fallido - Paso 5: No se visualiza la pantalla Cambios Fisicos");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Se visualiza la pantalla Cambios Fisicos con sus filtros.");

		addStep("Seleccionar el boton o enlace Cambios Fisicos Pendientes en la pantalla");
		boolean resultStep6 = pc4.clickCambiosFisicosPendientesPantalla(true);
		assertTrue(resultStep6, "CA03 fallido - Paso 6: No se visualiza la pantalla Cambios Fisicos Pendientes");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Se visualiza la pantalla Cambios Fisicos Pendientes con los filtros disponibles.");

		addStep("Validar que se muestre la columna \"Precio\" en el grid");
		assertTrue(resultStep6, "CA03 fallido - Paso 7: La pantalla Cambios Fisicos Pendientes no esta disponible para la validacion de la columna Precio");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: La pantalla Cambios Fisicos Pendientes esta disponible; la columna Precio sera validada en el grid tras la consulta.");

		addStep("Aplicar el filtrado necesario de acuerdo al escenario de prueba");
		String filtro = generadorDuplicadosClase.aplicarFiltrosNecesariosParaConsulta(user, pc4, validacionRegistros, true);
		assertTrue(filtro != null && !filtro.trim().isEmpty(), "CA03 fallido - Paso 8: No fue posible aplicar el filtro requerido");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Los filtros se seleccionan correctamente y el boton Consultar se habilita.");
		testCase.addTextEvidenceCurrentStep("Filtro aplicado: " + filtro);

		addStep("Presionar el boton Consultar");
		assertTrue(pc4.consultarComprobantes(true), "CA03 fallido - Paso 9: El sistema no ejecuto la consulta o el grid no se cargo");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El sistema ejecuta la consulta y el grid se carga con resultados visibles.");

		addStep("Identificar los encabezados de columnas visibles en el grid de resultados");
		assertTrue(identidadVisualPage.validarVistaEncabezadosGridCambiosFisicosPendientes(true), "CA03 fallido - Paso 10: No fue posible visualizar los encabezados del grid");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El grid presenta todos los encabezados de columnas legibles.");

		addStep("Verificar que el encabezado Precio existe en el grid");
		assertTrue(identidadVisualPage.validarEncabezadoPrecioGridCambiosFisicosPendientes(true), "CA03 fallido - Paso 11: La columna Precio no se visualiza en el encabezado del grid");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: La columna Precio se visualiza en el encabezado del grid.");

		addStep("Verificar que la columna Precio se encuentra en la posicion inmediatamente a la derecha de la columna Costo");
		assertTrue(identidadVisualPage.validarPrecioInmediatamenteDespuesDeCosto(true), "CA03 fallido - Paso 12: La columna Precio no esta ubicada inmediatamente despues de la columna Costo");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: La columna Precio esta ubicada inmediatamente despues de la columna Costo conforme al diseno definido en CA03.");
	}

	@AfterMethod public void afterMethod() { if (u != null) u.quit(); }
	@Override public void beforeTest() {}
	@Override public String setPrerequisites() { return "*1. Usuario OXXO OS activo en QA (3333333).\r\n*2. Datos en Cambios Fisicos Pendientes.\r\n*3. Liga de Portal Cometa."; }
	@Override public String setTestDescription() { return "Validar columna Precio visible en Cambios Fisicos Pendientes inmediatamente despues de Costo - OXXO OS."; }
	@Override public String setTestDesigner() { return "Michelle Ceja"; }
	@Override public String setTestFullName() { return "ATC_FT_280_ValidarColumnaPrecioVisibleDespuesCostoCFPendientesOXXOOS_test"; }
	@Override public String setTestInstanceID() { return null; }
}
