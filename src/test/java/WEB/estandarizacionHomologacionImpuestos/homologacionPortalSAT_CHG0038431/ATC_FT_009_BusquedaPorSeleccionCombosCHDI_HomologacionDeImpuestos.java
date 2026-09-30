package WEB.estandarizacionHomologacionImpuestos.homologacionPortalSAT_CHG0038431;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

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
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_009_BusquedaPorSeleccionCombosCHDI_HomologacionDeImpuestos extends BaseExecution{
	
	/**
	 * PORTAL CATALOGOS SAT/ CONSULTAR IMPUESTOS / CONSULTA DE IMPUESTOS ASIGNADOS (RT003)
	 * 
	 * DESCRIPCION: 
	 * Validar la busqueda de información seleccionando los filtros de Estructura.  CONSULTA DE IMPUESTOS ASIGNADOS
	 * 
	 * REREQUISITOS: 
	 *		1. Levantar ticket para solicitar scceso a la base de datos : 
	 *			Connection Name: FCPFACQA
	 *			Host: 10.80.3.57
	 *			Port: 1522
	 *			SERVICE_NAME=FCPFACQA.FEMCOM.NET
	 *		
	 *      2. Actualizar los datos de Dataprovider:
	 *      	Usuario y contraseña: ---> Validar que el usuario tenga acceso a Portal SAT
	 *      
	 *      3. Contar con informacion de articulos e impuestos en los esquemas XXCFD y XXSAT
	 *      	
	 * @author nellyoliviab@hexaware.com
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_009_BusquedaPorSeleccionCombosCHDI_HomologacionDeImpuestos_test(HashMap<String, String> data) throws SQLException {
		String portalurl = data.get("server");
		String userName=data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String opcionPortalDeAplicaciones=data.get("menuPortalDeAplicaciones");
		String opcionPortalSAT=data.get("menuPortalSAT");
		String opcionMenuDesplegadoPortalSAT=data.get("subMenuDesplegadoPortalSAT");
		String opcionSuperGrupo=data.get("opcionSuperGrupo");
		String opcionCategoria=data.get("opcionCategoria");
		String opcionSubCategoria=data.get("opcionSubCategoria");
		String opcionSegmento=data.get("opcionSegmento");
		String opcionSubSegmento=data.get("opcionSubSegmento");
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		
		ModeloPortalLogin login = new ModeloPortalLogin(u, testCase, portalurl);
		MenuPortal portal= new MenuPortal(u, testCase);
		MenuPortalSAT portalSAT= new MenuPortalSAT(u, testCase);
		ConsultaDeImpuestosAsignados consultaImpuestos= new ConsultaDeImpuestosAsignados(u, testCase);
		String mensajeSeleccion= "No se pudo seleccionar el elemento: ";
		String mensajeEncontrar= "No se pudo encontrar el elemento: ";
		String mensajeEstatusComboActivo= "El combo se encuentra seleccionable, el estatus correcto es: deshabilidato";
		String mensajeEstatusComboInactivo= "El combo se encuentra deshabilitado, el estatus correcto es: habilitado ";
		String mensajeComparacion="La información obtenida no coincide con la esperada. ";
		boolean resultValue=false;
		
		utils.sql.SQLUtil dbFCPFACQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPFACQA_VIRTUAL,
				 GlobalVariables.DB_USER_FCPFACQA_VIRTUAL, GlobalVariables.DB_PASSW_FCPFACQA_VIRTUAL);

		String selectDIVISION_SUPERGRUPO="select DIV_NAME from XXCFD.RMS_DIVISION WHERE DIVISION=";
		String selectGROUP_NO_CATEGORIA="SELECT GROUP_NAME FROM XXCFD.RMS_GROUPS WHERE GROUP_NO=";
		String selectDEPT_SUBCATEGORIA="SELECT DEPT_NAME FROM XXCFD.RMS_DEPS WHERE DEPT=";
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
		assertTrue(cargarPagina, "El sitio no fue cargado satisfactoriamente");
		/*
		 * PASO 2:
		 * Descripción del paso: Capturar credenciales de acceso para iniciar sesión
		 * 
		 * Resultado Esperado: Encontrar mensaje: Encontrar mensaje: "Portal de Aplicaciones"
		 */
		addStep("Capturar credenciales de acceso para iniciar sesión");
		login.cicloLogin2(userName, password);
		String mensajePortal=portal.accesoMenuPortal(userName, password);
		assertTrue( mensajePortal.compareTo("Portal de Aplicaciones")==0, "No se encontró mensaje: Portal de Aplicaciones");
		/*
		 * PASO 3:
		 * Descripción del paso: Seleccionar Portal SAT
		 * 
		 * Resultado Esperado: Visualizar menú SAT
		 * 
		 */
		addStep("Seleccionar opcion de portal");
		resultValue= portal.seleccionMenu(opcionPortalDeAplicaciones,true);
		assertTrue(resultValue, mensajeSeleccion+opcionPortalDeAplicaciones);
		/*
		 * PASO 4:
		 * Descripción del paso: Seleccionar Portal Catalogos SAT
		 * 
		 * Resultado Esperado: Encontrar elemento__
		 * 
		 */
		addStep("Seleccionar Consultar Impuestos");
		resultValue= portalSAT.moverCursorSobreOpcionMenu(opcionPortalSAT,true);
		assertTrue(resultValue, mensajeSeleccion+opcionPortalSAT);
		/*
		 * PASO 5:
		 * Descripción del paso: Seleccionar la opcion desplegada del menu SAT 
		 * 
		 * Resultado Esperado: Encontrar elemento__
		 * 
		 */
		addStep("Seleccionar opcion desplegada");
		resultValue= portalSAT.seleccionOpcionDelMenuDesplegado(opcionMenuDesplegadoPortalSAT,true);
		assertTrue(resultValue, mensajeSeleccion+opcionMenuDesplegadoPortalSAT);
		String campoActual=consultaImpuestos.obtenerTextoDeUnElemento("textoConsultaDeImpuestosAsignados",false);
		assertEquals(campoActual,"Consulta de Impuestos Asignados","No se encontro el titulo de la ventana");
		/*
		 * PASO 6:
		 * Descripción del paso: Seleccionar Súper Grupo
		 * 
		 * Resultado Esperado: Habilitar combo Categoria__
		 * 
		 */
		String seleccionActual="Super Grupo";
		addStep("Seleccionar Super Grupo: "+opcionSuperGrupo);
		resultValue= consultaImpuestos.consultarEstatusElemento("estatusComboCategoria",false,false);
		assertTrue( resultValue, mensajeEstatusComboActivo);
		String textoDeLaOpcionSeleccionada= consultaImpuestos.seleccionarOpcionCombo("comboSuperGrupo",true,opcionSuperGrupo);
		assertNotEquals("ERROR", textoDeLaOpcionSeleccionada,mensajeSeleccion+opcionSuperGrupo);
		resultValue= consultaImpuestos.consultarEstatusElemento("estatusComboCategoria",false,true);
		assertFalse(resultValue,mensajeEstatusComboInactivo);
		/*
		 * PASO 7:
		 * Descripción del paso: Encontrar Super Grupo seleccionado en la tabla: XXCFD.RMS_DIVISION
		 * 
		 * Resultado Esperado: Obtener el super grupo__
		 * 
		 */
		addStep("Encontrar Super Grupo: "+textoDeLaOpcionSeleccionada+" en la tabla: XXCFD.RMS_DIVISION");
		String idOpcionSeleccionada=consultaImpuestos.obtenerIdSeleccion(textoDeLaOpcionSeleccionada);
		selectDIVISION_SUPERGRUPO+=idOpcionSeleccionada;
		utils.sql.SQLResult resultadoSQL= dbFCPFACQA.executeQuery(selectDIVISION_SUPERGRUPO);
		testCase.addQueryEvidenceCurrentStep(resultadoSQL);
		String bdNombreSupergrupo=resultadoSQL.getFirstData();
		assertEquals(bdNombreSupergrupo.toLowerCase(), opcionSuperGrupo.toLowerCase(), mensajeComparacion);
		/*
		 * PASO 8:
		 * Descripción del paso: Seleccionar Categoria
		 * 
		 * Resultado Esperado: Habilitar combo SubCategoria__
		 * 
		 */
		seleccionActual="Categoria";
		addStep("Seleccionar Categoria: "+opcionCategoria);
		resultValue= consultaImpuestos.consultarEstatusElemento("estatusComboSubCategoria",false,false);
		assertTrue( resultValue,mensajeEstatusComboActivo);
		textoDeLaOpcionSeleccionada= consultaImpuestos.seleccionarOpcionCombo("comboCategoria",true,opcionCategoria);
		assertNotEquals("ERROR", textoDeLaOpcionSeleccionada,mensajeSeleccion+opcionCategoria);
		resultValue= consultaImpuestos.consultarEstatusElemento("estatusComboSubCategoria",false,true);
		assertFalse(resultValue, mensajeEstatusComboInactivo); 
		/*
		 * PASO 9:
		 * Descripción del paso: Encontrar Categoria seleccionado en la tabla: XXCFD.RMS_GROUPS
		 * 
		 * Resultado Esperado: Obtener el super grupo__
		 * 
		 */
		addStep("Encontrar Categoria: "+textoDeLaOpcionSeleccionada+" en la tabla: XXCFD.RMS_DIVISION");
		idOpcionSeleccionada=consultaImpuestos.obtenerIdSeleccion(textoDeLaOpcionSeleccionada);
		selectGROUP_NO_CATEGORIA+=idOpcionSeleccionada;
		resultadoSQL= dbFCPFACQA.executeQuery(selectGROUP_NO_CATEGORIA);
		testCase.addQueryEvidenceCurrentStep(resultadoSQL);
		String bdNombreCategoria=resultadoSQL.getFirstData();
		assertEquals(bdNombreCategoria.toLowerCase(), opcionCategoria.toLowerCase(), mensajeComparacion );
		/*
		 * PASO 10:
		 * Descripción del paso: Seleccionar SubCategoria
		 * 
		 * Resultado Esperado: Habilitar combo Segmento_
		 * 
		 */
		seleccionActual="Sub Categoria";
		addStep("Seleccionar Sub Categoria: "+opcionSubCategoria);
		resultValue= consultaImpuestos.consultarEstatusElemento("estatusComboSegmento",false,false);
		assertTrue( resultValue, mensajeEstatusComboActivo);
		textoDeLaOpcionSeleccionada= consultaImpuestos.seleccionarOpcionCombo("comboSubCategoria",true,opcionSubCategoria);
		
		assertNotEquals( "ERROR", textoDeLaOpcionSeleccionada,mensajeSeleccion+opcionSubCategoria);
		resultValue= consultaImpuestos.consultarEstatusElemento("estatusComboSegmento",false,true);
		assertFalse( resultValue,mensajeEstatusComboInactivo); 
		
		/*
		 * PASO 11:
		 * Descripción del paso: Encontrar Sub Categoria seleccionado en la tabla: XXCFD.RMS_DEPS
		 * 
		 * Resultado Esperado: Obtener sub categoria
		 * 
		 */
		addStep("Encontrar Sub Categoria: "+textoDeLaOpcionSeleccionada+" en la tabla: XXCFD.RMS_DEPS");
		String idSubCategoria=consultaImpuestos.obtenerIdSeleccion(textoDeLaOpcionSeleccionada);
		selectDEPT_SUBCATEGORIA+=idSubCategoria;
		resultadoSQL= dbFCPFACQA.executeQuery(selectDEPT_SUBCATEGORIA);
		testCase.addQueryEvidenceCurrentStep(resultadoSQL);
		String bdSubCategoria=resultadoSQL.getFirstData();
		assertEquals(bdSubCategoria.toLowerCase(), opcionSubCategoria.toLowerCase(), mensajeComparacion );
		/*
		 * PASO 12:
		 * Descripción del paso: Seleccionar Segmento
		 * 
		 * Resultado Esperado: Habilitar combo sub Segmento_
		 * 
		 */
		seleccionActual="Segmento";
		addStep("Seleccionar Segmento: "+opcionSegmento);
		resultValue= consultaImpuestos.consultarEstatusElemento("estatusComboSubSegmento",false,false);
		assertTrue( resultValue, mensajeEstatusComboActivo);
		textoDeLaOpcionSeleccionada= consultaImpuestos.seleccionarOpcionCombo("comboSegmento",true,opcionSegmento);
		assertNotEquals("ERROR", textoDeLaOpcionSeleccionada,mensajeSeleccion+opcionSegmento);
		resultValue= consultaImpuestos.consultarEstatusElemento("estatusComboSubSegmento",false,true);
		assertFalse( resultValue, mensajeEstatusComboInactivo); 

		/*
		 * PASO 13:
		 * Descripción del paso: Encontrar Segmento seleccionado en la tabla: XXCFD.RMS_CLASS
		 * 
		 * Resultado Esperado: Obtener segmento
		 * 
		 */
		addStep("Encontrar Segmento: "+textoDeLaOpcionSeleccionada+" en la tabla: XXCFD.RMS_CLASS");
		String idSegmento=consultaImpuestos.obtenerIdSeleccion(textoDeLaOpcionSeleccionada);
		String selectCLASS_NAME_SEGMENTO="SELECT CLASS_NAME FROM XXCFD.RMS_CLASS WHERE DEPT="+idSubCategoria+" AND CLASS="+idSegmento;
		resultadoSQL= dbFCPFACQA.executeQuery(selectCLASS_NAME_SEGMENTO);
		testCase.addQueryEvidenceCurrentStep(resultadoSQL);
		String bdSegmento=resultadoSQL.getFirstData();
		assertEquals(bdSegmento.toLowerCase(), opcionSegmento.toLowerCase(), mensajeComparacion);
		
		/*
		 * PASO 14:
		 * Descripción del paso: Seleccionar Sub Segmento
		 * 
		 * Resultado Esperado: Encontrar boton buscar
		 * 
		 */
		seleccionActual="SubSegmento";
		addStep("Seleccionar Sub Segmento: "+opcionSubSegmento);
		textoDeLaOpcionSeleccionada= consultaImpuestos.seleccionarOpcionCombo("comboSubSegmento",true,opcionSubSegmento.replace(" ", "").toLowerCase().replace("-", ""));
		assertNotEquals("ERROR", textoDeLaOpcionSeleccionada,mensajeSeleccion+opcionSubSegmento);
		seleccionActual="botonBuscar";
		resultValue= consultaImpuestos.encontrarElemento(seleccionActual, false);
		assertTrue(resultValue, mensajeEncontrar+seleccionActual);
		
		/*
		 * PASO 15:
		 * Descripción del paso: Encontrar Sub Segmento seleccionado en la tabla: XXCFD.RMS_SUBCLASS
		 * 
		 * Resultado Esperado: Obtener sub segmento
		 * 
		 */
		addStep("Encontrar Sub Segmento: "+textoDeLaOpcionSeleccionada+" en la tabla: XXCFD.RMS_SUBCLASS");
		String idSubSegmento=consultaImpuestos.obtenerIdSeleccion(textoDeLaOpcionSeleccionada);
		String selectSUB_NAME_SUBSEGMENTO="SELECT SUB_NAME FROM XXCFD.RMS_SUBCLASS WHERE DEPT="+idSubCategoria+" AND CLASS="+idSegmento+
				" AND SUBCLASS= "+idSubSegmento;
		resultadoSQL= dbFCPFACQA.executeQuery(selectSUB_NAME_SUBSEGMENTO);
		testCase.addQueryEvidenceCurrentStep(resultadoSQL);
		String bdSubSegmento=resultadoSQL.getFirstData();
		assertEquals(bdSubSegmento.toLowerCase().replace("-", ""), opcionSubSegmento.toLowerCase().replace("-", ""), mensajeComparacion );
		
		/*
		 * PASO 16:
		 * Descripción del paso: Seleccionar boton Buscar
		 * 
		 * Resultado Esperado: Elementos de busqueda vacios
		 * 
		 */
		addStep("Seleccionar boton Buscar");
		resultValue=consultaImpuestos.seleccionarElemento(seleccionActual,true,"");
		assertTrue(resultValue, mensajeSeleccion+seleccionActual);
		consultaImpuestos.esperarImagenCargaDeDatos();
		
		/*
		 * PASO 17:
		 * Descripción del paso: Seleccionar un registro
		 * 
		 * Resultado Esperado: La información del registro coincide con los filtros de busqueda
		 * 
		 */
		
		seleccionActual="registro";
		int numeroRegistro=consultaImpuestos.obtenerRegistroAleatorio();
		u.scroll(100*numeroRegistro);
		addStep("Se selecciona el registro: "+numeroRegistro+" para compararlo con los filtros de búsqueda seleccionados");
		//Se obtiene CLAVE
		String textoRegistroITEM=consultaImpuestos.validarInformacionRegistro(true, numeroRegistro,1);
		assertNotEquals(textoRegistroITEM,"NO EXISTEN DATOS","Los filtros seleccionados no arrojan resultados");
		String selectITEM_MASTER="SELECT ITEM, ITEM_DESC FROM XXCFD.ITEM_MASTER WHERE ITEM="+textoRegistroITEM;
		resultadoSQL= dbFCPFACQA.executeQuery(selectITEM_MASTER);
		testCase.addQueryEvidenceCurrentStep(resultadoSQL);
		// Se valida Descioncion
		String textoRegistroDESC=consultaImpuestos.validarInformacionRegistro(false, numeroRegistro,2);
		String obtenerDatoBD= resultadoSQL.getData(0, "ITEM_DESC");
		assertEquals(textoRegistroDESC, obtenerDatoBD, mensajeComparacion+"Campo Actual: ITEM_DESC");
		//Se valida REGION
		String textoRegistroRegion=consultaImpuestos.validarInformacionRegistro(false, numeroRegistro,4);
		String selectVAT_REGION="SELECT VAT_REGION, VAT_REGION_NAME FROM XXCFD.RMS_VAT_REGION WHERE VAT_REGION_NAME= '"+textoRegistroRegion+"'";
		resultadoSQL= dbFCPFACQA.executeQuery(selectVAT_REGION);
		testCase.addQueryEvidenceCurrentStep(resultadoSQL);
		assertTrue((resultadoSQL.getFirstData()!=null),"No se encontro la Region del registro en base de datos");
		String textoIdRegion=resultadoSQL.getData(0, "VAT_REGION");;
		String textoFechaActive =consultaImpuestos.validarInformacionRegistro(false, numeroRegistro,15).substring(0,10);
		String textoVATCODE =consultaImpuestos.validarInformacionRegistro(false, numeroRegistro,9);
		textoFechaActive=consultaImpuestos.cambiarFormatoFecha(textoFechaActive);
		// Se valida TABLA XXCFD.VAT_ITEM
		String selectXXCFD_VAT_ITEM="SELECT VAT_TYPE, CREATE_ID FROM XXCFD.VAT_ITEM WHERE ITEM='"+textoRegistroITEM+"'"
				+ " AND ACTIVE_DATE='"+textoFechaActive+"'"
				+ " AND VAT_CODE='"+textoVATCODE+"'"
				+ " AND VAT_REGION='"+textoIdRegion+"'";
		resultadoSQL= dbFCPFACQA.executeQuery(selectXXCFD_VAT_ITEM);
		testCase.addQueryEvidenceCurrentStep(resultadoSQL);
		String obtenerDatoVatTBD= resultadoSQL.getData(0, "VAT_TYPE");
		String resultadoBDVatType=consultaImpuestos.obtenerVatType(obtenerDatoVatTBD);
		String vatTypeGrig=consultaImpuestos.validarInformacionRegistro(false, numeroRegistro,14);
		assertEquals(vatTypeGrig, resultadoBDVatType, "El VAT_TYPE no coincide con ninguno esperado");
		getTestCase().addTextEvidenceCurrentStep("El VAT_TYPE obtenido de la base de datos es:  "+obtenerDatoVatTBD+" el cual significa: "+resultadoBDVatType
				+" y coincide con la informacion del grid: "+vatTypeGrig);
		String obtenerCreadorBD= resultadoSQL.getData(0, "CREATE_ID");
		String creadorGrig=consultaImpuestos.validarInformacionRegistro(false, numeroRegistro,17);
		assertEquals(creadorGrig, obtenerCreadorBD, "El CREATE_ID no coincide con ninguno esperado");
		//VALIDA TABLA XXCFD.RMS_VAT_CODES
		//Se concateta la info de la columna VAT_CODE_DESC IVA,IEPS,IVA IEPS y el impuesto estatal
		String ivaGrid=consultaImpuestos.validarInformacionRegistro(false, numeroRegistro,10);
		String iepsGrid=consultaImpuestos.validarInformacionRegistro(false, numeroRegistro,11);
		String ivaIepsGrid=consultaImpuestos.validarInformacionRegistro(false, numeroRegistro,12);
		String impuestoEstatalGrid=consultaImpuestos.validarInformacionRegistro(false, numeroRegistro,13);
		String filtroVATCODEDESC=ivaGrid+";"+iepsGrid+";"+ivaIepsGrid+";"+impuestoEstatalGrid;
		String selectXXCFDRMS_VAT_CODES="SELECT * FROM XXCFD.RMS_VAT_CODES WHERE VAT_CODE_DESC='"+filtroVATCODEDESC+"'";
		resultadoSQL= dbFCPFACQA.executeQuery(selectXXCFDRMS_VAT_CODES);
		testCase.addQueryEvidenceCurrentStep(resultadoSQL);
		String vatCodeBD= resultadoSQL.getData(0, "VAT_CODE");
		assertEquals(textoVATCODE, vatCodeBD, "El VAT_COD no coincide con ninguno esperado");
		// Nombre de la categoria
		String textoCategoria=consultaImpuestos.validarInformacionRegistro(false, numeroRegistro,5);
		assertEquals(bdNombreCategoria.toLowerCase(), textoCategoria.toLowerCase(), mensajeComparacion+"Campo Actual: Categoria");
		// Nombre de la sub categoria
		String textoSubCategoria=consultaImpuestos.validarInformacionRegistro(false, numeroRegistro,6);
		assertEquals(bdSubCategoria.toLowerCase(), textoSubCategoria.toLowerCase(), mensajeComparacion+"Campo Actual: SubCategoria");
		// Nombre del segmento
		String textoSegmento=consultaImpuestos.validarInformacionRegistro(false, numeroRegistro,7);
		assertEquals(bdSegmento.toLowerCase(), textoSegmento.toLowerCase(), mensajeComparacion+"Campo Actual: Segmento");
		// Nombre del subsegmento
		String textoSubSegmento=consultaImpuestos.validarInformacionRegistro(false, numeroRegistro,8);
		assertEquals(bdSubSegmento.toLowerCase(), textoSubSegmento.toLowerCase(), mensajeComparacion+"Campo Actual: SubSegmento");

		
		u.close(); 
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
		// TODO Auto-generated method stub
		return "Validar la busqueda de informacion seleccionando los filtros de Estructura";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "nellyoliviab@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_009_BusquedaPorSeleccionCombosCHDI_HomologacionDeImpuestos_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}
	
	@AfterMethod
	public void afterMethod(ITestResult result) {
		MetodoGeneral.obtenerTiempoDeEjecucion(result, testCase);
	}
	
}
