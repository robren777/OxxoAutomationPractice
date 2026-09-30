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

import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.ConsultaDeImpuestosAsignados;
import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.MenuPortal;
import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.MenuPortalSAT;
import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.MetodoGeneral;
import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.ModeloPortalLogin;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_010_BusquedaPorClaveArticuloCHDI_HomologacionDeImpuestos extends BaseExecution {
	/**
	 * PORTAL CATALOGOS SAT/ CONSULTAR IMPUESTOS / CONSULTA DE IMPUESTOS ASIGNADOS
	 * (RT003)
	 * 
	 * DESCRIPCION: Validar la busqueda de información capturando clave de articulo
	 * en la pantalla CATALOGO DE ESTRUCTURA DE IMPUESTOS
	 * 
	 * PREREQUISITOS: 1. Levantar ticket para solicitar scceso a la base de datos :
	 * Connection Name: FCPFACQA Host: 10.80.3.57 Port: 1522
	 * SERVICE_NAME=FCPFACQA.FEMCOM.NET
	 * 
	 * 2. Actualizar los datos de Dataprovider: Usuario y contraseña: validar acceso
	 * 
	 * 3. Contar con informacion de articulos e impuestos en los esquemas XXCFD y
	 * XXSAT
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_010_BusquedaPorClaveArticuloCHDI_HomologacionDeImpuestos_test(HashMap<String, String> data)
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
		String opcionSegmento = data.get("opcionSegmento");
		String opcionSubSegmento = data.get("opcionSubSegmento");
		String claveArticulo = data.get("claveArticulo").toLowerCase();
		String mes = data.get("mes");
		String anio = data.get("anio");
		String dia = data.get("dia");
		String descripcion = data.get("descripcion");
		String claveSAT = data.get("claveSAT");
		String mensajeEncontrar = "No se pudo encontrar el elemento: ";
		String mensajeSeleccion = "No se pudo seleccionar el elemento: ";
		String mensajeEstatusComboActivo = "El combo se encuentra seleccionable, el estatus correcto es: deshabilidato";
		String mensajeEstatusComboInactivo = "El combo se encuentra deshabilitado, el estatus correcto es: habilitado ";
		String mensajeCaptura = "No fue posible capturar el elemento: ";

		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		ModeloPortalLogin login = new ModeloPortalLogin(u, testCase, portalurl);
		MenuPortal portal = new MenuPortal(u, testCase);
		MenuPortalSAT portalSAT = new MenuPortalSAT(u, testCase);
		ConsultaDeImpuestosAsignados consultaImpuestos = new ConsultaDeImpuestosAsignados(u, testCase);
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
		assertTrue("El sitio no fue cargado satisfactoriamente ", cargarPagina);
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
		assertTrue("No se encontró mensaje: Portal de Aplicaciones",
				mensajePortal.compareTo("Portal de Aplicaciones") == 0);
		/*
		 * PASO 3: Descripción del paso: Seleccionar Portal SAT
		 * 
		 * Resultado Esperado: Visualizar menú SAT
		 * 
		 */
		addStep("Seleccionar opcion de portal");
		boolean resultValue = portal.seleccionMenu(opcionPortalDeAplicaciones, true);
		assertTrue(mensajeSeleccion + opcionPortalDeAplicaciones, resultValue);
		/*
		 * PASO 4: Descripción del paso: Seleccionar Portal Catalogos SAT
		 * 
		 * Resultado Esperado: Encontrar elemento__
		 * 
		 */
		addStep("Seleccionar Consultar Impuestos");
		resultValue = portalSAT.moverCursorSobreOpcionMenu(opcionPortalSAT, true);
		assertTrue(mensajeSeleccion + opcionPortalSAT, resultValue);
		/*
		 * PASO 5: Descripción del paso: Seleccionar la opcion desplegada del menu SAT
		 * 
		 * Resultado Esperado: Encontrar elemento__
		 * 
		 */
		addStep("Seleccionar opcion desplegada");
		resultValue = portalSAT.seleccionOpcionDelMenuDesplegado(opcionMenuDesplegadoPortalSAT, true);
		assertTrue(mensajeSeleccion + opcionMenuDesplegadoPortalSAT, resultValue);
		String campoActual = consultaImpuestos.obtenerTextoDeUnElemento("textoConsultaDeImpuestosAsignados", false);
		assertEquals(campoActual, "Consulta de Impuestos Asignados", "No se encontro el titulo de la ventana");

		/*
		 * PASO 6: Descripción del paso: Seleccionar Súper Grupo
		 * 
		 * Resultado Esperado: Habilitar combo Categoria__
		 * 
		 */
		String seleccionActual = "Super Grupo";
		addStep("Seleccionar Super Grupo: " + opcionSuperGrupo);
		resultValue = consultaImpuestos.consultarEstatusElemento("estatusComboCategoria", false, false);
		assertTrue(mensajeEstatusComboActivo, resultValue);
		String textoDeLaOpcionSeleccionada = consultaImpuestos.seleccionarOpcionCombo("comboSuperGrupo", true,
				opcionSuperGrupo);
		assertNotEquals("ERROR", textoDeLaOpcionSeleccionada, mensajeSeleccion + opcionSuperGrupo);
		resultValue = consultaImpuestos.consultarEstatusElemento("estatusComboCategoria", false, true);
		assertFalse(mensajeEstatusComboInactivo, resultValue);
		/*
		 * PASO 7: Descripción del paso: Seleccionar Categoria
		 * 
		 * Resultado Esperado: Habilitar combo Sub Categoria
		 * 
		 */
		seleccionActual = "Categoria";
		addStep("Seleccionar Categoria: " + opcionCategoria);
		resultValue = consultaImpuestos.consultarEstatusElemento("estatusComboSubCategoria", false, false);
		assertTrue(mensajeEstatusComboActivo, resultValue);
		textoDeLaOpcionSeleccionada = consultaImpuestos.seleccionarOpcionCombo("comboCategoria", true, opcionCategoria);
		assertNotEquals("ERROR", textoDeLaOpcionSeleccionada, mensajeSeleccion + opcionCategoria);
		resultValue = consultaImpuestos.consultarEstatusElemento("estatusComboSubCategoria", false, true);
		assertFalse(mensajeEstatusComboInactivo, resultValue);
		/*
		 * PASO 8: Descripción del paso: Seleccionar SubCategoria
		 * 
		 * Resultado Esperado: Habilitar combo Segmento_
		 * 
		 */
		seleccionActual = "Sub Categoria";
		addStep("Seleccionar Sub Categoria: " + opcionSubCategoria);
		resultValue = consultaImpuestos.consultarEstatusElemento("estatusComboSegmento", false, false);
		assertTrue(mensajeEstatusComboActivo, resultValue);
		textoDeLaOpcionSeleccionada = consultaImpuestos.seleccionarOpcionCombo("comboSubCategoria", true,
				opcionSubCategoria);
		assertNotEquals("ERROR", textoDeLaOpcionSeleccionada, mensajeSeleccion + opcionSubCategoria);
		resultValue = consultaImpuestos.consultarEstatusElemento("estatusComboSegmento", false, true);
		assertFalse(mensajeEstatusComboInactivo, resultValue);
		/*
		 * PASO 9: Descripción del paso: Seleccionar Segmento
		 * 
		 * Resultado Esperado: Habilitar combo sub Segmento_
		 * 
		 */
		seleccionActual = "Segmento";
		addStep("Seleccionar Segmento: " + opcionSegmento);
		resultValue = consultaImpuestos.consultarEstatusElemento("estatusComboSubSegmento", false, false);
		assertTrue(mensajeEstatusComboActivo, resultValue);
		textoDeLaOpcionSeleccionada = consultaImpuestos.seleccionarOpcionCombo("comboSegmento", true, opcionSegmento);
		assertNotEquals("ERROR", textoDeLaOpcionSeleccionada, mensajeSeleccion + opcionSegmento);
		resultValue = consultaImpuestos.consultarEstatusElemento("estatusComboSubSegmento", false, true);
		assertFalse(mensajeEstatusComboInactivo, resultValue);
		/*
		 * PASO 10: Descripción del paso: Seleccionar Sub Segmento
		 * 
		 * Resultado Esperado: Encontrar Casilla Clave de articulo
		 * 
		 */
		seleccionActual = "SubSegmento";
		addStep("Seleccionar Sub Segmento: " + opcionSubSegmento);
		textoDeLaOpcionSeleccionada = consultaImpuestos.seleccionarOpcionCombo("comboSubSegmento", true,
				opcionSubSegmento.replace(" ", "").toLowerCase().replace("-", ""));
		assertNotEquals("ERROR", textoDeLaOpcionSeleccionada, mensajeSeleccion + opcionSubSegmento);
		seleccionActual = "casillaClaveArticulo";
		resultValue = consultaImpuestos.encontrarElemento(seleccionActual, false);
		assertTrue(mensajeEncontrar + seleccionActual, resultValue);
		/*
		 * PASO 11: Descripción del paso: Se capturan todas las casillas menos la que
		 * corresponde a clave articulo
		 * 
		 * Resultado Esperado: Captura de casillas
		 * 
		 */
		addStep("Se capturan fecha de Inicio: " + dia + "/" + mes + "/" + anio);
		resultValue = consultaImpuestos.seleccionarElemento("fechaInicio", false, "");
		assertTrue(mensajeSeleccion + "Fecha Inicio", resultValue);
		resultValue = consultaImpuestos.seleccionarFechaInicio(anio, mes, dia, true);
		getTestCase().addTextEvidenceCurrentStep("Filtros reiniciados");
		assertTrue(mensajeSeleccion + "Fecha Inicio: Anio: " + anio + " Mes: " + mes + " Dia: " + dia, resultValue);
		
		addStep("Se captura clave SAT: " + claveSAT);
		seleccionActual = "casillaClaveSAT";
		resultValue = consultaImpuestos.capturarCasilla(seleccionActual, claveSAT, true);
		assertTrue(mensajeCaptura + seleccionActual, resultValue);
		getTestCase().addTextEvidenceCurrentStep("Filtros reiniciados");
		addStep("Se captura descripcion: " + descripcion);
		seleccionActual = "casillaDescripcion";
		resultValue = consultaImpuestos.capturarCasilla(seleccionActual, descripcion, true);
		assertTrue(mensajeCaptura + seleccionActual, resultValue);
		getTestCase().addTextEvidenceCurrentStep("Filtros reiniciados");

		/*
		 * PASO 12: Descripción del paso: Capturar: clave de artículo
		 * 
		 * Resultado Esperado: Información previamente capturada se reinicia. Se muestra
		 * lista de opciones que coincidan con los caracteres
		 * 
		 */
		addStep("Se captura el articulo " + claveArticulo);
		// Informacion Previamente Capturada se reinicia
		seleccionActual = "casillaClaveArticulo";
		resultValue = consultaImpuestos.capturarCasilla(seleccionActual, claveArticulo, true);
		// CASILLAS
		textoDeLaOpcionSeleccionada = consultaImpuestos.obtenerValorDeUnElemento("casillaDescripcion", false);
		assertEquals(textoDeLaOpcionSeleccionada, "", "El elemento Descripcion cuenta aun con informacion");
		textoDeLaOpcionSeleccionada = consultaImpuestos.obtenerValorDeUnElemento("casillaClaveSAT", false);
		assertEquals(textoDeLaOpcionSeleccionada, "", "El elemento Clave SAT cuenta aun con informacion");
		textoDeLaOpcionSeleccionada = consultaImpuestos.obtenerValorDeUnElemento("fechaInicio", false);
		assertEquals(textoDeLaOpcionSeleccionada, "", "El elemento Feche Inicio cuenta aun con informacion");
		getTestCase().addTextEvidenceCurrentStep("Ningun otro filtro se encuentra con informacion");
		/*
		 * PASO 13: Descripción del paso: Seleccionar fecha inicio
		 * 
		 * Resultado Esperado: Encontrar boton Buscar
		 * 
		 */
		addStep("Seleccionar boton Buscar");
		seleccionActual = "botonBuscar";
		resultValue = consultaImpuestos.seleccionarElemento(seleccionActual, true, "");
		consultaImpuestos.esperarImagenCargaDeDatos();
		assertTrue(mensajeSeleccion + seleccionActual, resultValue);

		/*
		 * PASO 15: Descripción del paso: Seleccionar un registro
		 * 
		 * Resultado Esperado: La información del registro coincide con los filtros de
		 * busqueda
		 * 
		 */
		seleccionActual = "registro";
		addStep("Seleccionar un registro");
		int numeroRegistro = consultaImpuestos.obtenerRegistroAleatorio();
		String textoRegistroITEM = consultaImpuestos.validarInformacionRegistro(true, numeroRegistro, 1);
		assertNotEquals("Los filtros seleccionados no arrojan resultados", "NO EXISTEN DATOS", textoRegistroITEM);
		assertEquals(textoRegistroITEM, claveArticulo, "La clave del articulo no coincide con la esperada");
		getTestCase().addTextEvidenceCurrentStep(
				"La información del artículo: " + textoRegistroITEM + "Coincide con la capturada: " + claveArticulo);

		u.close();

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		return "1.- Acceso a la plataforma: https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf\r\n"
				+ "" + "\n" + "2.- Informacion para captura de filtros" + "\n"
				+ "3.- Contar con informacion de articulos e impuestos en los esquemas XXCFD y XXSAT";
	}

	@Override
	public String setTestDescription() {
		return "Validar la busqueda de información capturando clave de articulo";
	}

	@Override
	public String setTestDesigner() {
		return "nellyoliviab@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_010_BusquedaPorClaveArticuloCHDI_HomologacionDeImpuestos_test";
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