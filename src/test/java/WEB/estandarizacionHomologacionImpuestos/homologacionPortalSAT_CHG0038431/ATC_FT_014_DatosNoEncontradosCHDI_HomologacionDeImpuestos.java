package WEB.estandarizacionHomologacionImpuestos.homologacionPortalSAT_CHG0038431;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertTrue;

import java.sql.SQLException;
import java.util.HashMap;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.ConsultaDeImpuestosAsignados;
import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.MenuPortal;
import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.MenuPortalSAT;
import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.MetodoGeneral;
import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.ModeloPortalLogin;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_014_DatosNoEncontradosCHDI_HomologacionDeImpuestos extends BaseExecution {
	/**
	 * PORTAL CATALOGOS SAT/ CONSULTAR IMPUESTOS / CONSULTA DE IMPUESTOS ASIGNADOS
	 * (RT003)
	 * 
	 * DESCRIPCION: Validar funcionamiento de grid cuando no se obtienen registros que 
	 * coincidan con los filtros de busqueda seleccionados
	 * 
	 * PREREQUISITOS
	 * 
	 * Actualizar los datos de Dataprovider: Usuario y contraseña: validar acceso
	 * Contar con informacion de articulos e impuestos en los esquemas XXCFD y XXSAT
	 * 
	 * @author nellyoliviab@hexaware.com
	 * @throws SQLException
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_014_DatosNoEncontradosCHDI_HomologacionDeImpuesto_test(HashMap<String, String> data) {
		String portalurl = data.get("server");
		String userName = data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String opcionPortalDeAplicaciones = data.get("menuPortalDeAplicaciones");
		String opcionPortalSAT = data.get("menuPortalSAT");
		String opcionMenuDesplegadoPortalSAT = data.get("subMenuDesplegadoPortalSAT");
		String opcionSuperGrupo = data.get("opcionSuperGrupo");

		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		ModeloPortalLogin login = new ModeloPortalLogin(u, testCase, portalurl);
		MenuPortal portal = new MenuPortal(u, testCase);
		MenuPortalSAT portalSAT = new MenuPortalSAT(u, testCase);
		String mensajeSeleccion = "No se pudo seleccionar el elemento: ";
		ConsultaDeImpuestosAsignados consultaImpuestos = new ConsultaDeImpuestosAsignados(u, testCase);
		String mensajeEncontrar = "No se pudo encontrar el elemento: ";
		String mensajeEstatusComboActivo = "El combo se encuentra seleccionable, el estatus correcto es: deshabilidato";

		/*
		 * PASO 1: Descripción del paso: Ingresar a la liga de acceso:
		 * https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf
		 * 
		 * Resultado Esperado: Encontrar mensaje: "Bienvenido a Oxxo"
		 */
		addStep("Ingresar a la liga de acceso: https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf");
		u.get(login.getUrl());
		login.proceedToPortal();
		boolean cargarPagina = login.encontrarMensajeInicio();
		testCase.addScreenShotCurrentStep("CargarURL");
		assertTrue(cargarPagina, "El sitio no fue cargado satisfactoriamente");
		/*
		 * PASO 2: Descripción del paso: Capturar credenciales de acceso para iniciar
		 * sesión
		 * 
		 * Resultado Esperado: Encontrar mensaje: Encontrar mensaje:
		 * "Portal de Aplicaciones"
		 */
		addStep("Capturar credenciales de acceso para iniciar sesión");
		login.cicloLogin2(userName, password);
		String mensajePortal = portal.accesoMenuPortal(userName, password);
		assertTrue(mensajePortal.compareTo("Portal de Aplicaciones") == 0,
				"No se encontró mensaje: Portal de Aplicaciones");
		/*
		 * PASO 3: Descripción del paso: Seleccionar Portal SAT
		 * 
		 * Resultado Esperado: Visualizar menú SAT
		 * 
		 */
		addStep("Seleccionar opcion de portal");
		boolean resultValue = portal.seleccionMenu(opcionPortalDeAplicaciones, true);
		assertTrue(resultValue, mensajeSeleccion + opcionPortalDeAplicaciones);
		/*
		 * PASO 4: Descripción del paso: Seleccionar Portal Catalogos SAT
		 * 
		 * Resultado Esperado: Encontrar elemento__
		 * 
		 */
		addStep("Seleccionar Consultar Impuestos");
		resultValue = portalSAT.moverCursorSobreOpcionMenu(opcionPortalSAT, true);
		assertTrue(resultValue, mensajeSeleccion + opcionPortalSAT);
		/*
		 * PASO 5: Descripción del paso: Seleccionar la opcion desplegada del menu SAT
		 * 
		 * Resultado Esperado: Encontrar elemento__
		 * 
		 */
		addStep("Seleccionar opcion desplegada");
		resultValue = portalSAT.seleccionOpcionDelMenuDesplegado(opcionMenuDesplegadoPortalSAT, true);
		assertTrue(resultValue, mensajeSeleccion + opcionMenuDesplegadoPortalSAT);
		String campoActual = consultaImpuestos.obtenerTextoDeUnElemento("textoConsultaDeImpuestosAsignados", false);
		assertEquals(campoActual, "Consulta de Impuestos Asignados", "No se encontro el titulo de la ventana");

		/*
		 * PASO 8: Descripción del paso: Seleccionar botón Buscar
		 * 
		 * Resultado Esperado: Boton buscar deshabilitado
		 * 
		 */
		// addStep("Seleccionar boton Buscar");
		String seleccionActual = "botonBuscarEstatus";
		resultValue = consultaImpuestos.consultarEstatusElemento(seleccionActual, false, true);
		assertTrue(resultValue, "El boton de Buscar se encuentra habilitado. Deberia entonrarse deshabilitado");
		getTestCase().addTextEvidenceCurrentStep("Boton buscar con estatus deshabilitado");
		/*
		 * PASO 7: Descripción del paso: Seleccionar Súper Grupo
		 * 
		 * Resultado Esperado: Habilitar combo Categoria__
		 * 
		 */
		seleccionActual = "Super Grupo";
		addStep("Seleccionar Super Grupo: " + opcionSuperGrupo);
		resultValue = consultaImpuestos.consultarEstatusElemento("estatusComboCategoria", false, false);
		assertTrue(resultValue, mensajeEstatusComboActivo);
		String textoDeLaOpcionSeleccionada = consultaImpuestos.seleccionarOpcionCombo("comboSuperGrupo", true,
				opcionSuperGrupo);
		assertNotEquals("ERROR", textoDeLaOpcionSeleccionada, mensajeSeleccion + opcionSuperGrupo);
		seleccionActual = "botonBuscar";
		resultValue = consultaImpuestos.encontrarElemento(seleccionActual, false);
		assertTrue(resultValue, mensajeEncontrar + seleccionActual);
		/*
		 * PASO 8: Descripción del paso: Seleccionar boton Buscar
		 * 
		 * Resultado Esperado: Actualizar grid
		 * 
		 */
		addStep("Seleccionar boton Buscar");
		resultValue = consultaImpuestos.seleccionarElemento(seleccionActual, true, "");
		assertTrue(resultValue, mensajeSeleccion + seleccionActual);
		consultaImpuestos.esperarImagenCargaDeDatos();

		/*
		 * PASO 9: Descripción del paso: Seleccionar primer registro
		 * 
		 * Resultado Esperado: Obtener mensaje esperado
		 * 
		 */
		addStep("Se selecciona el registro primer registro");
		String infoPrimerRegistro = consultaImpuestos.validarInformacionRegistroVacio(true);
		assertEquals(infoPrimerRegistro, "NO EXISTEN DATOS", "Se encontro información no esperada");
		/*
		 * PASO 10: Descripción del paso: Seleccionar lista de resultados a mostrar en
		 * le paginador
		 * 
		 * Resultado Esperado: Opción deshabilitada
		 * 
		 */
		addStep("Seleccionar lista desplegable del paginador");
		resultValue = consultaImpuestos.validarElTotalDeResultadosPaginado(true);
		assertTrue("Se muestran mas resultados de los esperados", resultValue);
		getTestCase().addTextEvidenceCurrentStep("Lista desplegable habilitada");
		/*
		 * PASO 11: Descripción del paso: Seleccionar pagina anterior
		 * 
		 * Resultado Esperado: Pagina anterior deshabilitada
		 * 
		 */
		addStep("Seleccionar pagina anterior");
		campoActual = "botonAtras";
		resultValue = consultaImpuestos.consultarEstatusPaginado(campoActual, true);
		assertTrue("El boton se encuentra habilidado y deberia estar deshabilitado", resultValue);
		getTestCase().addTextEvidenceCurrentStep("El boton de pagina anterior se encuentra deshabilitado");
		/*
		 * PASO 12: Descripción del paso:Seleccionar página siguiente
		 * 
		 * Resultado Esperado: Botón con estatus deshabilitado
		 * 
		 */
		addStep("Seleccionar pagina siguiente");
		campoActual = "botonSiguiente";
		resultValue = consultaImpuestos.consultarEstatusPaginado(campoActual, true);
		assertTrue("El boton siguiente se encuentra habilidado y deberia estar deshabilitado", resultValue);
		getTestCase().addTextEvidenceCurrentStep("Boton siguiente deshabilitado");

		u.close();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		return "1.- Acceso a la plataforma: https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf\r\n"
				+ "" + "\n" + "2.- Informacion existente de estructuras" + "\n"
				+ "3.- Contar con informacion de articulos e impuestos en los esquemas XXCFD y XXSAT";
	}

	@Override
	public String setTestDescription() {
		return "Validar funcionamiento de grid cuando no se obtienen registros que coincidan con los filtros de busqueda seleccionados";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_014_DatosNoEncontradosCHDI_HomologacionDeImpuesto_test";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "nellyoliviab@hexaware.com";
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		MetodoGeneral.obtenerTiempoDeEjecucion(result, testCase);
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
