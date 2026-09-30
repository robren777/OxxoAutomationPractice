package WEB.estandarizacionHomologacionImpuestos.homologacionPortalSAT_CHG0038431;

import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.AssertJUnit.assertTrue;

import java.sql.SQLException;
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

public class ATC_FT_018_BusquedaPorDatoRequeridoCEDI_HomologacionDeImpuestos extends BaseExecution {

	/**
	 * PORTAL CATALOGOS SAT/ CATALOGOS SAT / ELEMENTOS OBLIGATORIOS DE CAPTURA POR
	 * ESTRUCTURA
	 * 
	 * DESCRIPCION: Validar búsqueda por selección de filtro solo nuevas
	 * estructuras. en la pantalla CELEMENTOS OBLIGATORIOS DE CAPTURA POR ESTRUCTURA
	 * 
	 * 1. Levantar ticket para solicitar scceso a la base de datos : Connection
	 * Name: FCPFACQA Host: 10.80.3.57 Port: 1522 SERVICE_NAME=FCPFACQA.FEMCOM.NET
	 * 
	 * 2. Usuario con acceso al portal CatAlogos SAT: Usuario y contraseña:
	 * corroborar acceso
	 * 
	 * 3. Contar con informacion de articulos e impuestos en los esquemas XXCFD y XXSAT
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_018_BusquedaPorDatoRequeridoCEDI_HomologacionDeImpuestos_test(HashMap<String, String> data)
			throws SQLException {
		String portalurl = data.get("server");
		String userName = data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String opcionPortalDeAplicaciones = data.get("menuPortalDeAplicaciones");
		String opcionPortalSAT = data.get("menuPortalSAT");
		String opcionMenuDesplegadoPortalSAT = data.get("subMenuDesplegadoPortalSAT");
		String opcionSuperGrupo = data.get("opcionSuperGrupo");
		String opcionCategoria = data.get("opcionCategoria");
		String opcionSubCategoria = data.get("opcionSubCategoria");
		String opcionCambioSuperGrupo = data.get("opcionCambioSuperGrupo");
		String opcionDatoRequerido = data.get("opcionDatoRequerido");

		String mensajeEstatusComboActivo = "El combo se encuentra seleccionable, el estatus correcto es: deshabilidato.";

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
		resultValue = elementosObligatorios.consultarEstatusElemento("estatusComboCategoria", false, false);
		assertTrue(mensajeEstatusComboActivo, resultValue);
		String textoDeLaOpcionSeleccionada = elementosObligatorios.seleccionarOpcionCombo("comboSuperGrupo", true,
				opcionSuperGrupo);
		assertNotEquals("ERROR", textoDeLaOpcionSeleccionada, mensajeSeleccion + opcionSuperGrupo);
		resultValue = elementosObligatorios.consultarEstatusElemento("estatusComboCategoria", false, true);
		assertFalse(mensajeEstatusComboActivo, resultValue);
		/*
		 * PASO 7: DescripciOn del paso: Seleccionar categoria
		 * 
		 * Resultado Esperado: Habilitar combo Sub Categoria
		 * 
		 */
		addStep("Seleccionar categoria: " + opcionCategoria);
		resultValue = elementosObligatorios.consultarEstatusElemento("estatusComboSubCategoria", false, false);
		assertTrue(mensajeEstatusComboActivo, resultValue);
		textoDeLaOpcionSeleccionada = elementosObligatorios.seleccionarOpcionCombo("comboCategoria", true,
				opcionCategoria);
		assertNotEquals("ERROR", textoDeLaOpcionSeleccionada, mensajeSeleccion + opcionCategoria);
		resultValue = elementosObligatorios.consultarEstatusElemento("estatusComboSubCategoria", false, true);
		assertFalse(mensajeEstatusComboActivo, resultValue);
		/*
		 * PASO 8: DescripciOn del paso: Seleccionar subcategoria
		 * 
		 * Resultado Esperado: Habilitar boton Buscar
		 * 
		 */
		addStep("Seleccionar subcategoria: " + opcionSubCategoria);
		textoDeLaOpcionSeleccionada = elementosObligatorios.seleccionarOpcionCombo("comboSubCategoria", true,
				opcionSubCategoria);
		assertNotEquals("ERROR", textoDeLaOpcionSeleccionada, mensajeSeleccion + opcionCategoria);
		resultValue = elementosObligatorios.consultarEstatusElemento("estatusComboDatoRequerido", false, true);
		assertFalse(mensajeEstatusComboActivo, resultValue);
		/*
		 * PASO 9: DescripciOn del paso: Cambiar seleccion de super grupo
		 * 
		 * Resultado Esperado: Deshabilitar combo subcategoria y resetear combo
		 * Categoria
		 * 
		 */
		addStep("Cambiar seleccion de super grupo por: " + opcionCambioSuperGrupo);
		textoDeLaOpcionSeleccionada = elementosObligatorios.seleccionarOpcionCombo("comboSuperGrupo", true,
				opcionCambioSuperGrupo);
		assertNotEquals("ERROR", textoDeLaOpcionSeleccionada, mensajeSeleccion + opcionSuperGrupo);
		String textoOpcionCategoria = elementosObligatorios.obtenerTextoDeUnElemento("comboCategoria", false);
		assertEquals(textoOpcionCategoria, "");
		resultValue = elementosObligatorios.consultarEstatusElemento("estatusComboSubCategoria", false, false);
		assertTrue("Combo Subcategoria. " + mensajeEstatusComboActivo, resultValue);
		/*
		 * PASO 10: DescripciOn del paso: Seleccionar Dato Requerido
		 * 
		 * Resultado Esperado: Habilitar boton Buscar
		 * 
		 */
		addStep("Seleccionar dato requerido: " + opcionDatoRequerido);
		textoDeLaOpcionSeleccionada = elementosObligatorios.seleccionarOpcionComboValorD("comboDatoRequerido", true,
				opcionDatoRequerido);
		assertNotEquals("ERROR", textoDeLaOpcionSeleccionada, mensajeSeleccion + opcionDatoRequerido);
		resultValue = elementosObligatorios.consultarEstatusElemento("botonBuscar", false, true);
		assertFalse(mensajeEstatusComboActivo, resultValue);
		/*
		 * PASO 11: DescripciOn del paso: Seleccionar botOn de bUsqueda
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
		 * PASO 12: DescripciOn del paso: Seleccionar un registro
		 * 
		 * Resultado Esperado: Validar que el registro exista coincida con la selecciOn
		 * del combo
		 * 
		 */
		int numeroRegistro = elementosObligatorios.obtenerRegistroAleatorio();
		addStep("Se valida la informacion de Dato Requerido del registro: " + numeroRegistro);
		campoActual = "registro";
		String textoDatoRequerido = elementosObligatorios.validarInformacionRegistro(true, numeroRegistro, 4);
		assertEquals(opcionDatoRequerido, textoDatoRequerido,
				"El id del combo Categoria no coincide con el id obtenido del registro: " + numeroRegistro);
		getTestCase().addTextEvidenceCurrentStep("La seleccion del combo:  " + opcionDatoRequerido
				+ ". Coincide con la informacion obtenida del registro " + numeroRegistro + ": " + textoDatoRequerido);

		u.close();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		return "1.- Acceso a la plataforma: https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf\r\n"
				+ "" + "\n" + "2.- Acceso a la base de datos: FCPFACQA" + "\n"
				+ "3.- Contar con informacion de articulos e impuestos en los esquemas XXCFD y XXSAT" + "\n";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se Valida la busqueda por seleccion de dato requerido";
	}

	@Override
	public String setTestDesigner() {

		return "nellyoliviab@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_018_BusquedaPorDatoRequeridoCEDI_HomologacionDeImpuestos_test";
	}

	@Override
	public String setTestInstanceID() {
		return "";
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		MetodoGeneral.obtenerTiempoDeEjecucion(result, testCase);
	}

}
