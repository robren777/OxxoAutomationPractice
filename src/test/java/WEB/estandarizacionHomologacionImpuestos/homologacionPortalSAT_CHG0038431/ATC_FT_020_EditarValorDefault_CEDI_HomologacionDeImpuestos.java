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

public class ATC_FT_020_EditarValorDefault_CEDI_HomologacionDeImpuestos extends BaseExecution {

	/**
	 * PORTAL CATALOGOS SAT/ CATALOGOS SAT / ELEMENTOS OBLIGATORIOS DE CAPTURA POR
	 * ESTRUCTURA
	 * 
	 * DESCRIPCION: Validar la edicion de valor default y visualizar la actualizacion de la informacion
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
	public void ATC_FT_020_EditarValorDefault_CEDI_HomologacionDeImpuestos_test(HashMap<String, String> data)
			throws SQLException {
		String portalurl = data.get("server");
		String userName = data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String opcionPortalDeAplicaciones = data.get("menuPortalDeAplicaciones");
		String opcionPortalSAT = data.get("menuPortalSAT");
		String opcionMenuDesplegadoPortalSAT = data.get("subMenuDesplegadoPortalSAT");
		String opcionSuperGrupo = data.get("opcionSuperGrupo");
		String opcionCategoria = data.get("opcionCategoria");
		String checkValorDefault = data.get("checkValorDefault");
		String valorDefault = data.get("valorDefault");
		String descripcion = data.get("descripcion");
		testCase.setTest_Description("");
		testCase.setTest_Description(descripcion);
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		ModeloPortalLogin login = new ModeloPortalLogin(u, testCase, portalurl);
		MenuPortal portal = new MenuPortal(u, testCase);
		MenuPortalSAT portalSAT = new MenuPortalSAT(u, testCase);
		ElementosObligatoriosDeCapturaPorEstructura elementosObligatorios = new ElementosObligatoriosDeCapturaPorEstructura(
				u, testCase);
		String mensajeSeleccion = "No se pudo seleccionar el elemento: ";
		String mensajeEstatusComboActivo = "El combo se encuentra seleccionable, el estatus correcto es: deshabilidato.";

		/*
		 * PASO 1: Descripcion del paso: Ingresar a la liga de acceso:
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
		 * PASO 2: Descripcion del paso: Capturar credenciales de acceso para iniciar
		 * sesion
		 * 
		 * Resultado Esperado: Encontrar mensaje: Encontrar mensaje:
		 * "Portal de Aplicaciones"
		 */
		addStep("Capturar credenciales de acceso para iniciar sesion");
		login.cicloLogin2(userName, password);
		String mensajePortal = portal.accesoMenuPortal(userName, password);
		assertTrue("No se encontro mensaje: Portal de Aplicaciones",
				mensajePortal.compareTo("Portal de Aplicaciones") == 0);
		/*
		 * PASO 3: Descripcion del paso: Seleccionar Portal SAT
		 * 
		 * Resultado Esperado: Visualizar menu SAT
		 * 
		 */
		addStep("Seleccionar opcion de portal");
		boolean resultValue = portal.seleccionMenu(opcionPortalDeAplicaciones, true);
		assertTrue(mensajeSeleccion + opcionPortalDeAplicaciones, resultValue);
		/*
		 * PASO 4: Descripcion del paso: Seleccionar Portal Catalogos SAT
		 * 
		 * Resultado Esperado: Encontrar elemento__
		 * 
		 */
		addStep("Seleccionar Catalogos SAT");
		resultValue = portalSAT.moverCursorSobreOpcionMenu(opcionPortalSAT, true);
		assertTrue(mensajeSeleccion + opcionPortalSAT, resultValue);
		/*
		 * PASO 5: Descripcion del paso: Seleccionar la opcion desplegada del menu SAT
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
		 * PASO 6: Descripcion del paso: Seleccionar super grupo
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
		 * PASO 7: Descripcion del paso: Seleccionar categoria
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
		 * PASO 8: Descripcion del paso: Seleccionar boton de busqueda
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
		 * PASO 9: Descripcion del paso: Seleccionar registro y abrir ventana de edicion
		 * 
		 * Resultado Esperado: Obtener informacion del registro y comparar con
		 * informacion del grid y filtros de busqueda
		 * 
		 */
		int numeroRegistro = elementosObligatorios.obtenerRegistroAleatorio();
		addStep("Se selecciona elemento visual de edicion del registro: " + numeroRegistro);
		campoActual = "registro";
		String gridSubCategoria = elementosObligatorios.validarInformacionRegistro(false, numeroRegistro, 3);
		String gridDatoRequerido = elementosObligatorios.validarInformacionRegistro(false, numeroRegistro, 4);
		resultValue = elementosObligatorios.seleccionarElemento(campoActual, true, String.valueOf(numeroRegistro));
		assertTrue(mensajeSeleccion + campoActual + " :" + String.valueOf(numeroRegistro), resultValue);
		// VENTANA DE EDICION
		campoActual = "configuracionSuperGrupo";
		String nombreSuperGrupo = elementosObligatorios.obtenerTextoDeUnElemento(campoActual, false);
		assertEquals(opcionSuperGrupo, nombreSuperGrupo, "El Super Grupo no coincide con los filtros de busqueda");
		campoActual = "configuracionCategoria";
		String nombreCategoria = elementosObligatorios.obtenerTextoDeUnElemento(campoActual, false);
		assertEquals(opcionCategoria, nombreCategoria, "La categoria no coincide con los filtros de busqueda");
		campoActual = "configuracionSubCategoria";
		String nombreSubCategoria = elementosObligatorios.obtenerTextoDeUnElemento(campoActual, false);
		assertEquals(gridSubCategoria, nombreSubCategoria,
				"La sub categoria no coincide con el registro seleccionado en el grid");
		resultValue = elementosObligatorios.validarLaSeleccionInicialDeConfiguracion(gridDatoRequerido);
		assertTrue("No se encuentra seleccionado la opcion de check correspondiente a la informacion de la"
				+ " columna valor default del registro: " + numeroRegistro, resultValue);
		/*
		 * PASO 10: Descripcion del paso: Seleccionar check del valor a modificar
		 * 
		 * Resultado Esperado: Validar estatus de check aplicar excepcion y casilla
		 * default
		 * 
		 */
		addStep("Seleccionar opcion del valor a modificar: " + checkValorDefault);
		resultValue = elementosObligatorios.seleccionarCheckValorDefault(checkValorDefault, true);
		System.out.println("Valor=" + resultValue);
		assertTrue(mensajeSeleccion + checkValorDefault, resultValue);
		elementosObligatorios.esperarCargaDeDatos();
		resultValue = elementosObligatorios.validarEstatusCasillasValorDefault();
		System.out.println("RESULT: " + resultValue);
		assertTrue("El estatus del check y la casilla para modificar valor default no mantienen los estatus correctos",
				resultValue);
		/*
		 * PASO 11: Descripcion del paso: Seleccionar checkbox aplicar excepcion
		 * 
		 * Resultado Esperado: Habilitar caja de texto de valor default
		 * 
		 */
		addStep("Seleccionar checkbox aplicar excepcion");
		elementosObligatorios.seleccionarAplicarExcepcion();
		elementosObligatorios.esperarCargaDeDatos();
		resultValue = elementosObligatorios.consultarEstatusValorDefault("casillaValorDefault", false, false);
		assertFalse("La casilla de valor default deberia encontrarse habilitada", resultValue);
		/*
		 * PASO 11: Descripcion del paso: Capturar valor default
		 * 
		 * Resultado Esperado: Habilitar guardar cambios
		 * 
		 */
		addStep("Capturar el nuevo valor default: " + valorDefault);
		resultValue = elementosObligatorios.capturarCasilla("casillaValorDefault", valorDefault, true);
		assertTrue("No fue posible capturar la informacion", resultValue);
		resultValue = elementosObligatorios.consultarEstatusElemento("botonGuardarCambios", false, true);
		assertFalse(mensajeEstatusComboActivo, resultValue);
		/*
		 * PASO 12: Descripcion del paso: Seleccionar guardar cambios
		 * 
		 * Resultado Esperado: Actualizar grid
		 * 
		 */
		addStep("Seleccionar boton Guardar cambios");
		campoActual = "botonGuardarCambios";
		String alerta = elementosObligatorios.seleccionarElementoGuardarCambios();
		assertEquals(alerta, "Cambios guardados exitosamente.", "Se esperaba mensaje de actualizacion exitosa");
		elementosObligatorios.esperarCargaDeDatos();
		/*
		 * PASO 13: Descripcion del paso: Obtener valor de la columna Valor default del
		 * registro
		 * 
		 * Resultado Esperado: Obtener el valor recien modificado
		 * 
		 */
		addStep("Se obtiene valor default del registro recien modificado");
		String gridValorDefault = elementosObligatorios.validarInformacionRegistro(true, numeroRegistro, 5);
		assertEquals(valorDefault, gridValorDefault, "El valor default no fue actualizado correctamente");

		u.close();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		return "1.- Usuario con acceso a Portal Catalogos SAT: https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf\r\n"
				+ "2.- Informacion de la estructura a buscar"
				+ "3.- Contar con informacion de articulos e impuestos en los esquemas XXCFD y XXSAT";

	}

	@Override
	public String setTestDescription() {
		return "Validar la edicion de valor default y visualizar la actualizacion de la informacion";
	}

	@Override
	public String setTestDesigner() {
		return "nellyoliviab@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_020_EditarValorDefault_CEDI_HomologacionDeImpuestos_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		MetodoGeneral.obtenerTiempoDeEjecucion(result, testCase);
	}

}
