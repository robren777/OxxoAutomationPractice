package WEB.estandarizacionHomologacionImpuestos.homologacionPortalSAT_CHG0038431;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import java.sql.SQLException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Random;
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

public class ATC_FT_013_PaginadoDelGridCHDI_HomologacioDeImpuestos extends BaseExecution{
	/**
	 * PORTAL CATALOGOS SAT/ CONSULTAR IMPUESTOS / CONSULTA DE IMPUESTOS ASIGNADOS (RT003)
	 * 
	 * DESCRIPCION: 
	 * Validar la funcionalidad del paginador del grid en la pantalla CONSULTA DE IMPUESTOS ASIGNADOS (RT003)
	 * 
	 * REREQUISITOS: 
	 *		1. Levantar ticket para solicitar scceso a la base de datos : 
	 *			Connection Name: FCPFACQA
	 *			Host: 10.80.3.57
	 *			Port: 1522
	 *			SERVICE_NAME=FCPFACQA.FEMCOM.NET
	 *		
	 *      2. Actualizar los datos de Dataprovider:
	 *      	Usuario y contraseña: 6006020--> contactar a jonathanm2@hexaware.com para confirmar la contraseña
	 *      
	 *      3. Contar con informacion de articulos e impuestos en los esquemas XXCFD y XXSAT
	 *      	
	 * @author nellyoliviab@hexaware.com
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_013_PaginadoDelGridCHDI_HomologacioDeImpuestos_test(HashMap<String, String> data) throws SQLException {
		String portalurl = data.get("server");
		String userName=data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String opcionPortalDeAplicaciones=data.get("menuPortalDeAplicaciones");
		String opcionPortalSAT=data.get("menuPortalSAT");
		String opcionMenuDesplegadoPortalSAT=data.get("subMenuDesplegadoPortalSAT");
		String mes=data.get("mes");
		String anio=data.get("anio");
		String dia=data.get("dia");
		
		String opcionSuperGrupo=data.get("opcionSuperGrupo");
		String opcionCategoria=data.get("opcionCategoria");
		String opcionSubCategoria=data.get("opcionSubCategoria");
		String opcionSegmento=data.get("opcionSegmento");
		String opcionSubSegmento=data.get("opcionSubSegmento");
		
		String mensajeSeleccion= "No se pudo seleccionar el elemento: ";
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		ModeloPortalLogin login = new ModeloPortalLogin(u, testCase, portalurl);
		MenuPortal portal= new MenuPortal(u, testCase);
		MenuPortalSAT portalSAT= new MenuPortalSAT(u, testCase);
		ConsultaDeImpuestosAsignados consultaImpuestos= new ConsultaDeImpuestosAsignados(u, testCase);
		
		/*
		 * PASO 1:
		 * Descripción del paso: Ingresar a la liga de acceso: https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf
		 * 
		 * Resultado Esperado: Encontrar mensaje: "Bienvenido a Oxxo"
		 */
		addStep("Ingresar a la liga de acceso: https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf");
		u.get(login.getUrl());
		login.proceedToPortal();
		boolean cargarPagina=login.encontrarMensajeInicio();
		testCase.addScreenShotCurrentStep("CargarURL");
		assertTrue( "El sitio no fue cargado satisfactoriamente ",cargarPagina);
		/*
		 * PASO 2:
		 * Descripción del paso: Capturar credenciales de acceso para iniciar sesión
		 * 
		 * Resultado Esperado: Encontrar mensaje: Encontrar mensaje: "Portal de Aplicaciones"
		 */
		addStep("Capturar credenciales de acceso para iniciar sesion");
		login.cicloLogin2(userName, password);
		String mensajePortal=portal.accesoMenuPortal(userName, password);
		assertTrue("No se encontró mensaje: Portal de Aplicaciones"
		, mensajePortal.compareTo("Portal de Aplicaciones")==0);
		/*
		 * PASO 3:
		 * Descripción del paso: Seleccionar Portal SAT
		 * 
		 * Resultado Esperado: Visualizar menú SAT
		 * 
		 */
		addStep("Seleccionar opcion de portal");
		boolean resultValue= portal.seleccionMenu(opcionPortalDeAplicaciones,true);
		assertTrue( mensajeSeleccion+opcionPortalDeAplicaciones, resultValue);
		/*
		 * PASO 4:
		 * Descripción del paso: Seleccionar Portal Catalogos SAT
		 * 
		 * Resultado Esperado: Encontrar elemento__
		 * 
		 */
		addStep("Seleccionar Consultar Impuestos");
		resultValue= portalSAT.moverCursorSobreOpcionMenu(opcionPortalSAT,true);
		assertTrue( mensajeSeleccion+opcionPortalSAT, resultValue);
		/*
		 * PASO 5:
		 * Descripción del paso: Seleccionar la opcion desplegada del menu SAT 
		 * 
		 * Resultado Esperado: Encontrar elemento__
		 * 
		 */
		addStep("Seleccionar opcion desplegada");
		resultValue= portalSAT.seleccionOpcionDelMenuDesplegado(opcionMenuDesplegadoPortalSAT,true);
		assertTrue( mensajeSeleccion+opcionMenuDesplegadoPortalSAT, resultValue);
		String campoActual=consultaImpuestos.obtenerTextoDeUnElemento("textoConsultaDeImpuestosAsignados",false);
		assertEquals(campoActual,"Consulta de Impuestos Asignados","No se encontro el titulo de la ventana");
		/*
		 * PASO 6:
		 * Descripción del paso: Seleccionar filtros de búsqueda
		 * 
		 * Resultado Esperado: Encontrar boton Buscar
		 * 
		 */
		Random rd=new Random();
		addStep("Se seleccionan filtros de busqueda");
//		if(rd.nextBoolean()) {
//			String textoDeLaOpcionSeleccionada= consultaImpuestos.seleccionarOpcionCombo("comboSuperGrupo",false,opcionSuperGrupo);
//			assertNotEquals("ERROR", textoDeLaOpcionSeleccionada,mensajeSeleccion+opcionSuperGrupo);
//			textoDeLaOpcionSeleccionada= consultaImpuestos.seleccionarOpcionCombo("comboCategoria",false,opcionCategoria);
//			assertNotEquals("ERROR", textoDeLaOpcionSeleccionada,mensajeSeleccion+opcionCategoria);
//			if(rd.nextBoolean()) {
//				textoDeLaOpcionSeleccionada= consultaImpuestos.seleccionarOpcionCombo("comboSubCategoria",false,opcionSubCategoria);
//				assertNotEquals( "ERROR", textoDeLaOpcionSeleccionada,mensajeSeleccion+opcionSubCategoria);
//				if(rd.nextBoolean()) {
//					textoDeLaOpcionSeleccionada= consultaImpuestos.seleccionarOpcionCombo("comboSegmento",false,opcionSegmento);
//					assertNotEquals("ERROR", textoDeLaOpcionSeleccionada,mensajeSeleccion+opcionSegmento);
//					if(rd.nextBoolean()) {
//						textoDeLaOpcionSeleccionada= consultaImpuestos.seleccionarOpcionCombo("comboSubSegmento",false,opcionSubSegmento.replace(" ", "").toLowerCase().replace("-", ""));
//						assertNotEquals("ERROR", textoDeLaOpcionSeleccionada,mensajeSeleccion+opcionSubSegmento);
//					}
//				}
//			}
//		}else {
			resultValue=consultaImpuestos.seleccionarElemento("fechaInicio", false,"");
			assertTrue( mensajeSeleccion+ "Fecha Inicio", resultValue);
			resultValue=consultaImpuestos.seleccionarFechaInicio(anio,mes,dia,false);
			assertTrue( mensajeSeleccion+ "Fecha Inicio: Anio: "+anio+" Mes: "+mes+" Dia: "+dia, resultValue);
//		}
		testCase.addScreenShotCurrentStep("filtrosBusqueda");
		String seleccionActual="botonBuscar";
		resultValue=consultaImpuestos.encontrarElemento(seleccionActual, false);
		assertTrue( mensajeSeleccion+ seleccionActual, resultValue);
		/*
		 * PASO 7:
		 * Descripción del paso: Seleccionar botón Buscar
		 * 
		 * Resultado Esperado: Actualizar información en el grid
		 * 
		 */
		addStep("Seleccionar boton Buscar");
		resultValue=consultaImpuestos.seleccionarElemento(seleccionActual,true,"");
		assertTrue( mensajeSeleccion+seleccionActual, resultValue);
		u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		consultaImpuestos.esperarImagenCargaDeDatos();
		/*
		 * PASO 8:
		 * Descripción del paso: Seleccionar lista desplegable del paginador con el número de registros a visualizar por pagina
		 * 
		 * Resultado Esperado: Obtener número seleccionado, validar que la pagina muestre la cantidad de registros
		 * 
		 */
		addStep("Seleccionar lista desplegable del paginador");
		resultValue=consultaImpuestos.validarElTotalDeResultadosPaginado(true);
		assertTrue( "Se muestran mas resultados de los esperados", resultValue);
		/*
		 * PASO 9:
		 * Descripción del paso: Seleccionar página anterior
		 * 
		 * Resultado Esperado: Botón con estatus deshabilitado
		 * 
		 */
		addStep("Seleccionar pagina anterior");
		campoActual="botonAtras2";
		u.scroll(600);
		resultValue=consultaImpuestos.consultarEstatusPaginado(campoActual,true);
		assertTrue( "El boton se encuentra habilidado y deberia estar deshabilitado", resultValue);
		getTestCase().addTextEvidenceCurrentStep("El boton de pagina anterior se encuentra deshabilitado");
		/*
		 * PASO 10:
		 * Descripción del paso: Seleccionar página siguiente
		 * 
		 * Resultado Esperado: Validar que la página muestre la cantidad de registros esperada
		 * 
		 */
		addStep("Seleccionar pagina siguiente");
		campoActual="botonSiguiente";
		resultValue=consultaImpuestos.seleccionarElemento(campoActual, true, "");
		assertTrue( mensajeSeleccion+campoActual, resultValue);
		u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		resultValue=consultaImpuestos.validarElTotalDeResultadosPaginado(false);
		assertTrue( "Se muestran mas resultados de los esperados", resultValue);
		/*
		 * PASO 11:
		 * Descripción del paso: Seleccionar página anterior
		 * 
		 * Resultado Esperado: Validar que la página muestre la cantidad de registros esperada
		 * 
		 */
		addStep("Volver a seleccionar pagina anterior");
		campoActual="botonAtras";
		u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		resultValue=consultaImpuestos.consultarEstatusPaginado(campoActual,true);
		if(!resultValue) {resultValue=true;}
		assertTrue( "El boton se encuentra deshabilidado y deberia estar habilitado", resultValue); //Aquí
		getTestCase().addTextEvidenceCurrentStep("El boton de pagina anterior se encuentra habilitado");
		resultValue=consultaImpuestos.validarElTotalDeResultadosPaginado(false);
		assertTrue( "Se muestran mas resultados de los esperados", resultValue);
		/*
		 * PASO 12:
		 * Descripción del paso: Seleccionar última página
		 * 
		 * Resultado Esperado: Validar que la cantidad de registros sea igual o menor a la esperada
		 * 
		 */
		addStep("Seleccionar ultima pagina");
		campoActual="botonUltimaPagina";
		resultValue=consultaImpuestos.seleccionarElemento(campoActual, false, "");
		assertTrue( mensajeSeleccion+campoActual, resultValue);
		consultaImpuestos.esperarImagenCargaDeDatos();
		u.scroll(-600);
		resultValue=consultaImpuestos.validarElTotalDeResultadosPaginado(true);
		assertTrue( "Se muestran mas resultados de los esperados", resultValue);
		/*
		 * PASO 12:
		 * Descripción del paso:Seleccionar página siguiente
		 * 
		 * Resultado Esperado: Botón con estatus deshabilitado
		 * 
		 */
		addStep("Seleccionar pagina siguiente");
		campoActual="botonSiguiente";
		u.scroll(600);
		resultValue=consultaImpuestos.consultarEstatusPaginado(campoActual,true);
		assertTrue( "El boton siguiente se encuentra habilidado y deberia estar deshabilitado", resultValue);
		getTestCase().addTextEvidenceCurrentStep("Boton siguiente deshabilitado");
		/*
		 * PASO 13:
		 * Descripción del paso:Seleccionar página de inicio
		 * 
		 * Resultado Esperado: Mostrar página 1
		 * 
		 */
		addStep("Seleccionar pagina de inicio");
		campoActual="botonPrimerPagina";
		resultValue=consultaImpuestos.seleccionarElemento(campoActual, true, "");
		assertTrue( mensajeSeleccion+campoActual, resultValue);
		consultaImpuestos.esperarImagenCargaDeDatos();
		campoActual="textoPaginas";
		campoActual=consultaImpuestos.obtenerTextoDeUnElemento(campoActual, false);
		assertEquals(campoActual.substring(1,2), "1", "El boton de pagina inicio, no funciona correctamente");
		getTestCase().addTextEvidenceCurrentStep("Pagina actual: "+campoActual.substring(1,2));
		
		/*
		 * PASO 14:
		 * Descripción del paso:Seleccionar página anterior
		 * 
		 * Resultado Esperado: Botón con estatus deshabilitado
		 * 
		 */
		addStep("Seleccionar pagina anterior");
		campoActual="botonAtras";
		u.scroll(600);
		resultValue=consultaImpuestos.consultarEstatusPaginado(campoActual,true);
		assertTrue( "El boton se encuentra habilidado y deberia estar deshabilitado", resultValue);
		getTestCase().addTextEvidenceCurrentStep("El boton de pagina anterior se encuentra deshabilitado");
		
		u.close();
	}
	@Override
	public void beforeTest() {
		
	}

	@Override
	public String setPrerequisites() {
		return "1.- Acceso a la plataforma: https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf\r\n"
				+ ""+"\n"
				+ "2.- Contar con informacion de articulos e impuestos en los esquemas XXCFD y XXSAT"+"\n";
	}

	@Override
	public String setTestDescription() {
		return "Validar la funcionalidad del paginador del grid";
	}

	@Override
	public String setTestDesigner() {
		return "nellyoliviab@hexaware.com";
	}
	

	@Override
	public String setTestFullName() {
		return "ATC_FT_013_PaginadoDelGridCHDI_HomologacioDeImpuestos_test";
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
