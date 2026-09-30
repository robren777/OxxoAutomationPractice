package WEB.estandarizacionHomologacionImpuestos.homologacionPortalSAT_CHG0038431;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.AssertJUnit.assertTrue;

import java.util.HashMap;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.ElementosObligatoriosDeCapturaPorEstructura;
import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.MenuPortal;
import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.MenuPortalSAT;
import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.MetodoGeneral;
import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.ModeloPortalLogin;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_025_PaginadoDelGridCEDI_HomologacionDeImpuestos extends BaseExecution {
	/**
	 * PORTAL CATALOGOS SAT/ CATALOGOS SAT / ELEMENTOS OBLIGATORIOS DE CAPTURA POR
	 * ESTRUCTURA
	 * 
	 * DESCRIPCION: Paginado del grid. en la pantalla CELEMENTOS OBLIGATORIOS DE
	 * CAPTURA POR ESTRUCTURA
	 * 
	 * 1.- Acceso a la plataforma:
	 * https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf
	 * 
	 * 2. Contar con informacion de articulos e impuestos en los esquemas XXCFD y XXSAT
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_025_PaginadoDelGridCEDI_HomologacionDeImpuestos_test(HashMap<String, String> data) {
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
		ElementosObligatoriosDeCapturaPorEstructura elementosObligatorios = new ElementosObligatoriosDeCapturaPorEstructura(
				u, testCase);
		String mensajeSeleccion = "No se pudo seleccionar el elemento: ";
		/*
		 * PASO 1: DescripciOn del paso: Ingresar a la liga de acceso:
		 * https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf
		 * 
		 * Resultado Esperado: Encontrar mensaje: "Bienvenido a Oxxo"
		 */
		addStep("Ingresar a la liga de acceso: https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf");
		u.get(login.getUrl());
		login.proceedToPortal();
		boolean cargarPagina = login.encontrarMensajeInicio();
		testCase.addScreenShotCurrentStep("CargarURL");
		assertTrue("El sitio no fue cargado satisfactoriamente ", cargarPagina);
		/*
		 * PASO 2: DescripciOn del paso: Capturar credenciales de acceso para iniciar
		 * sesiOn
		 * 
		 * Resultado Esperado: Encontrar mensaje: Encontrar mensaje:
		 * "Portal de Aplicaciones"
		 */
		addStep("Capturar credenciales de acceso para iniciar sesiOn");
		login.cicloLogin2(userName, password);
		String mensajePortal = portal.accesoMenuPortal(userName, password);
		assertTrue("No se encontrO mensaje: Portal de Aplicaciones",
				mensajePortal.compareTo("Portal de Aplicaciones") == 0);
		/*
		 * PASO 3: DescripciOn del paso: Seleccionar Portal SAT
		 * 
		 * Resultado Esperado: Visualizar menU SAT
		 * 
		 */
		addStep("Seleccionar opcion de portal");
		boolean resultValue = portal.seleccionMenu(opcionPortalDeAplicaciones, true);
		assertTrue(mensajeSeleccion + opcionPortalDeAplicaciones, resultValue);
		/*
		 * PASO 4: DescripciOn del paso: Seleccionar Portal Catalogos SAT
		 * 
		 * Resultado Esperado: Encontrar elemento__
		 * 
		 */
		addStep("Seleccionar Catalogos SAT");
		resultValue = portalSAT.moverCursorSobreOpcionMenu(opcionPortalSAT, true);
		assertTrue(mensajeSeleccion + opcionPortalSAT, resultValue);
		/*
		 * PASO 5: DescripciOn del paso: Seleccionar la opcion desplegada del menu SAT
		 * 
		 * Resultado Esperado: Encontrar elemento__
		 * 
		 */
		addStep("Seleccionar opcion desplegada: Elementos obligatorios de captura por estructura");
		resultValue = portalSAT.seleccionOpcionDelMenuDesplegado(opcionMenuDesplegadoPortalSAT, true);
		assertTrue(mensajeSeleccion + opcionMenuDesplegadoPortalSAT, resultValue);
		String campoActual = elementosObligatorios.obtenerTextoDeUnElemento("textoElementosObligatorios", false);
		assertEquals(campoActual, "Elementos obligatorios de Captura por Estructura",
				"No se encontro el titulo de la ventana");
		/*
		 * PASO 6: DescripciOn del paso: Seleccionar super grupo
		 * 
		 * Resultado Esperado: Habilitar combo Categoria_
		 * 
		 */
		addStep("Seleccionar super grupo: " + opcionSuperGrupo);
		String textoDeLaOpcionSeleccionada = elementosObligatorios.seleccionarOpcionCombo("comboSuperGrupo", true,
				opcionSuperGrupo);
		assertNotEquals("ERROR", textoDeLaOpcionSeleccionada, mensajeSeleccion + opcionSuperGrupo);
		/*
		 * PASO 7: DescripciOn del paso: Seleccionar botOn de bUsqueda
		 * 
		 * Resultado Esperado: Actualizar grid
		 * 
		 */
		addStep("Seleccionar boton Buscar");
		campoActual = "botonBuscar";
		resultValue = elementosObligatorios.seleccionarElemento(campoActual, true, "");
		assertTrue(mensajeSeleccion + campoActual, resultValue);
		elementosObligatorios.esperarCargaDeDatos();
		/*
		 * PASO 9: Descripción del paso: Seleccionar página anterior
		 * 
		 * Resultado Esperado: Botón con estatus deshabilitado
		 * 
		 */
		addStep("Seleccionar pagina anterior");
		campoActual = "botonAtras";
		resultValue = elementosObligatorios.consultarEstatusPaginado(campoActual, true);
		assertTrue("El boton se encuentra habilidado y deberia estar deshabilitado", resultValue);
		getTestCase().addTextEvidenceCurrentStep("El boton de pagina anterior se encuentra deshabilitado");
		/*
		 * PASO 10: Descripción del paso: Seleccionar página siguiente
		 * 
		 * Resultado Esperado: Botón con estatus deshabilitado
		 * 
		 */
		addStep("Seleccionar pagina siguiente");
		campoActual = "botonSiguiente";
		resultValue = elementosObligatorios.consultarEstatusPaginado(campoActual, true);
		assertTrue("El boton se encuentra habilidado y deberia estar deshabilitado", resultValue);
		getTestCase().addTextEvidenceCurrentStep("El boton de pagina siguiente se encuentra deshabilitado");
		/*
		 * PASO 12: Descripción del paso: Seleccionar última página
		 * 
		 * Resultado Esperado: Botón con estatus deshabilitado
		 * 
		 */
		addStep("Seleccionar ultima pagina");
		campoActual = "botonUltimaPagina";
		resultValue = elementosObligatorios.consultarEstatusPaginado(campoActual, true);
		assertTrue("El boton se encuentra habilidado y deberia estar deshabilitado", resultValue);
		getTestCase().addTextEvidenceCurrentStep("El boton de ultima pagina se encuentra deshabilitado");
		/*
		 * PASO 13: Descripción del paso:Seleccionar página de inicio
		 * 
		 * Resultado Esperado: Botón con estatus deshabilitado
		 * 
		 */
		addStep("Seleccionar pagina de inicio");
		campoActual = "botonPrimerPagina";
		resultValue = elementosObligatorios.consultarEstatusPaginado(campoActual, true);
		assertTrue("El boton se encuentra habilidado y deberia estar deshabilitado", resultValue);
		getTestCase().addTextEvidenceCurrentStep("El boton de pagina inicio se encuentra deshabilitado");

		u.close();

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		return "1.- Acceso a la plataforma: https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf\r\n"
				+ "" + "\n" + "2.- Contar con informacion de articulos e impuestos en los esquemas XXCFD y XXSAT"
				+ "\n";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar la funcionalidad del paginador del grid";
	}

	@Override
	public String setTestDesigner() {

		return "nellyoliviab@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_025_PaginadoDelGridCEDI_HomologacionDeImpuestos_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		MetodoGeneral.obtenerTiempoDeEjecucion(result, testCase);
	}

}
