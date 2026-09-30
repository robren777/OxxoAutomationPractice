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
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_017_BusquedaPorSubCategoriaCEDI_HomologacionDeImpuestos extends BaseExecution{

	/**
	 * PORTAL CATALOGOS SAT/ CATaLOGOS SAT / ELEMENTOS OBLIGATORIOS DE CAPTURA POR ESTRUCTURA
	 * 
	 * DESCRIPCION: 
	 * Validar busqueda por seleccion de sub categoria.  en la pantalla CELEMENTOS OBLIGATORIOS DE CAPTURA POR ESTRUCTURA
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
	public void ATC_FT_017_BusquedaPorSubCategoriaCEDI_HomologacionDeImpuestos_test(HashMap<String, String> data) throws SQLException {
		String portalurl = data.get("server");
		String userName=data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String opcionPortalDeAplicaciones=data.get("menuPortalDeAplicaciones");
		String opcionPortalSAT=data.get("menuPortalSAT");
		String opcionMenuDesplegadoPortalSAT=data.get("subMenuDesplegadoPortalSAT");
		String opcionSuperGrupo=data.get("opcionSuperGrupo");
		String opcionCategoria=data.get("opcionCategoria");
		String opcionSubCategoria=data.get("opcionSubCategoria");
		
		String mensajeEstatusComboActivo= "El combo se encuentra seleccionable, el estatus correcto es: deshabilidato.";
		
		utils.sql.SQLUtil dbFCPFACQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPFACQA_VIRTUAL,
		 GlobalVariables.DB_USER_FCPFACQA_VIRTUAL, GlobalVariables.DB_PASSW_FCPFACQA_VIRTUAL);

		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		ModeloPortalLogin login = new ModeloPortalLogin(u, testCase, portalurl);
		MenuPortal portal= new MenuPortal(u, testCase);
		MenuPortalSAT portalSAT= new MenuPortalSAT(u, testCase);
		ElementosObligatoriosDeCapturaPorEstructura elementosObligatorios=new ElementosObligatoriosDeCapturaPorEstructura(u, testCase);
		String mensajeSeleccion= "No se pudo seleccionar el elemento: ";
		
		
		/*
		 * PASO 1:
		 * Descripcion del paso: Ingresar a la liga de acceso: https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf
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
		 * Descripcion del paso: Capturar credenciales de acceso para iniciar sesion
		 * 
		 * Resultado Esperado: Encontrar mensaje: Encontrar mensaje: "Portal de Aplicaciones"
		 */
		addStep("Capturar credenciales de acceso para iniciar sesion");
		login.cicloLogin2(userName, password);
		String mensajePortal=portal.accesoMenuPortal(userName, password);
		assertTrue("No se encontro mensaje: Portal de Aplicaciones"
		, mensajePortal.compareTo("Portal de Aplicaciones")==0);
		/*
		 * PASO 3:
		 * Descripcion del paso: Seleccionar Portal SAT
		 * 
		 * Resultado Esperado: Visualizar menu SAT
		 * 
		 */
		addStep("Seleccionar opcion de portal");
		boolean resultValue= portal.seleccionMenu(opcionPortalDeAplicaciones,true);
		assertTrue( mensajeSeleccion+opcionPortalDeAplicaciones, resultValue);
		/*
		 * PASO 4:
		 * Descripcion del paso: Seleccionar Portal Catalogos SAT
		 * 
		 * Resultado Esperado: Encontrar elemento__
		 * 
		 */
		addStep("Seleccionar Catalogos SAT");
		resultValue= portalSAT.moverCursorSobreOpcionMenu(opcionPortalSAT,true);
		assertTrue( mensajeSeleccion+opcionPortalSAT, resultValue);
		/*
		 * PASO 5:
		 * Descripcion del paso: Seleccionar la opcion desplegada del menu SAT 
		 * 
		 * Resultado Esperado: Encontrar elemento__
		 * 
		 */
		addStep("Seleccionar opcion desplegada: Elementos obligatorios de captura por estructura");
		resultValue= portalSAT.seleccionOpcionDelMenuDesplegado(opcionMenuDesplegadoPortalSAT,true);
		assertTrue( mensajeSeleccion+opcionMenuDesplegadoPortalSAT, resultValue);
		String campoActual=elementosObligatorios.obtenerTextoDeUnElemento("textoElementosObligatorios", false);
		assertEquals(campoActual,"Elementos obligatorios de Captura por Estructura","No se encontro el titulo de la ventana");
		/*
		 * PASO 6:
		 * Descripcion del paso: Seleccionar super grupo 
		 * 
		 * Resultado Esperado: Habilitar combo Categoria_
		 * 
		 */
		addStep("Seleccionar super grupo: "+opcionSuperGrupo);
		resultValue=elementosObligatorios.consultarEstatusElemento("estatusComboCategoria",false,false);
		assertTrue(mensajeEstatusComboActivo, resultValue);
		String textoDeLaOpcionSeleccionada= elementosObligatorios.seleccionarOpcionCombo("comboSuperGrupo",true,opcionSuperGrupo);
		assertNotEquals("ERROR", textoDeLaOpcionSeleccionada,mensajeSeleccion+opcionSuperGrupo);
		resultValue=elementosObligatorios.consultarEstatusElemento("estatusComboCategoria",false,true);
		assertFalse(mensajeEstatusComboActivo, resultValue);
		/*
		 * PASO 7:
		 * Descripcion del paso: Seleccionar categoria
		 * 
		 * Resultado Esperado: Habilitar combo Sub Categoria
		 * 
		 */
		addStep("Seleccionar categoria: "+opcionCategoria);
		resultValue=elementosObligatorios.consultarEstatusElemento("estatusComboSubCategoria",false,false);
		assertTrue(mensajeEstatusComboActivo, resultValue);
		textoDeLaOpcionSeleccionada= elementosObligatorios.seleccionarOpcionCombo("comboCategoria",true,opcionCategoria);
		assertNotEquals("ERROR", textoDeLaOpcionSeleccionada,mensajeSeleccion+opcionCategoria);
		resultValue=elementosObligatorios.consultarEstatusElemento("estatusComboSubCategoria",false,true);
		assertFalse(mensajeEstatusComboActivo, resultValue);
		/*
		 * PASO 8:
		 * Descripcion del paso: Seleccionar subcategoria
		 * 
		 * Resultado Esperado: Habilitar boton Buscar
		 * 
		 */
		addStep("Seleccionar subcategoria: "+opcionSubCategoria);
		textoDeLaOpcionSeleccionada= elementosObligatorios.seleccionarOpcionCombo("comboSubCategoria",true,opcionSubCategoria);
		assertNotEquals("ERROR", textoDeLaOpcionSeleccionada,mensajeSeleccion+opcionCategoria);
		resultValue=elementosObligatorios.consultarEstatusElemento("botonBuscar",false,true);
		assertFalse(mensajeEstatusComboActivo, resultValue);
		/*
		 * PASO 9:
		 * Descripcion del paso: Seleccionar boton de busqueda
		 * 
		 * Resultado Esperado: Actualizar grid
		 * 
		 */
		addStep("Seleccionar boton Buscar");
		campoActual="botonBuscar";
		resultValue=elementosObligatorios.seleccionarElemento(campoActual,true,"");
		assertTrue( mensajeSeleccion+campoActual, resultValue);
		elementosObligatorios.esperarCargaDeDatos();
		/*
		 * PASO 10:
		 * Descripcion del paso: Seleccionar un registro
		 * 
		 * Resultado Esperado: Validar que el registro exista en XXCFD.RMS_DEPS y su id en XXSAT.XXSAT_DATO_REQUERIDO
		 * 
		 */
		int numeroRegistro=elementosObligatorios.obtenerRegistroAleatorio();
		addStep("Se valida la informacion de Categoria del registro: "+numeroRegistro);
		campoActual="registro";
		String textoRegistroITEM=elementosObligatorios.validarInformacionRegistro(true, numeroRegistro,3);
		assertNotEquals("Los filtros seleccionados no arrojan resultados","NO EXISTEN DATOS", textoRegistroITEM);
		String selectSUBCATEGORIA_XXCFD="SELECT DEPT FROM XXCFD.RMS_DEPS WHERE DEPT_NAME= '"+textoRegistroITEM+"'";
		utils.sql.SQLResult resultadoSQL= dbFCPFACQA.executeQuery(selectSUBCATEGORIA_XXCFD);
		testCase.addQueryEvidenceCurrentStep(resultadoSQL);
		String idSubCategoriaXXCFD=resultadoSQL.getFirstData();
		String selectSUBCATEGORIA_XXSAT="SELECT DISTINCT ID_SUB_CATEGORIA FROM XXSAT.XXSAT_DATO_REQUERIDO WHERE ID_SUB_CATEGORIA='"+idSubCategoriaXXCFD+"'";
		resultadoSQL= dbFCPFACQA.executeQuery(selectSUBCATEGORIA_XXSAT);
		testCase.addQueryEvidenceCurrentStep(resultadoSQL);
		String idSubCategoriaXXSAT=resultadoSQL.getFirstData();
		String idComboSubCategoria=elementosObligatorios.obtenerIdSeleccion(textoDeLaOpcionSeleccionada);
		assertEquals(idComboSubCategoria, idSubCategoriaXXSAT, "El id del combo Categoria no coincide con el id obtenido del registro: "+numeroRegistro);
		getTestCase().addTextEvidenceCurrentStep("El id del combo:  "+idComboSubCategoria+". Coincide con la informacion obtenida del registro "+numeroRegistro+": "+idSubCategoriaXXSAT);
		
		u.close();
	}
	
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		return "1.- Acceso a la plataforma: https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf\r\n"
				+ ""+"\n"
				+ "2.- Acceso a la base de datos: FCPFACQA"+"\n"
				+ "3.- Contar con informacion de articulos e impuestos en los esquemas XXCFD y XXSAT"+"\n";
	}

	@Override
	public String setTestDescription() {
		return "Validar busqueda por selección de sub categoria";
	}


	@Override
	public String setTestDesigner() {
		return "nellyoliviab@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_017_BusquedaPorSubCategoriaCEDI_HomologacionDeImpuestos_test";
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
