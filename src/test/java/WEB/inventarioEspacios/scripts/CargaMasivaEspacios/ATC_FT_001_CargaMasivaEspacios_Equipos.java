package WEB.inventarioEspacios.scripts.CargaMasivaEspacios;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.CargaMasivaInventariosIEP;
import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.om.MetodosExtra;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_001_CargaMasivaEspacios_Equipos extends BaseExecution{
	
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_001_CargaMasivaEspacios_Equipos_test(HashMap<String, String> data) throws Exception {
	
		/*
		 * Utiler�as
		 *********************************************************************/
		
		SQLUtil dbFCIASQAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL, GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");
		Boolean validacion;
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
		CargaMasivaInventariosIEP cmi = new CargaMasivaInventariosIEP(u, testCase);
		MetodosExtra me = new MetodosExtra(u, testCase);
		String query;
		
		
		String db = "";
		switch(negocio.toUpperCase()) {
			case "BARA": 
				db = "XXINE_BA";
			break;
			
			default: db = "XXINE";
			break;
		
		}
		
		testCase.setTest_Description("ATC_FT_001_CargaMasivaEspacios_Equipos - "+ perfil +" " + negocio);

		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal con el usuario y contrase�a correcta");
		u.get(pa.getUrl());
		u.hardWait(3);
		
		pa.proceedToPortal();
		//pa.logginInventarioEspacios(user, password);
		pa.cicloLogin(user, password);
		
		query = "select XXAC_USERNAME from " + db + ".xxine_users_empleados_v where XXAC_USER_ID = '" + user + "'";
		SQLResult ExecuteQuery = executeQuery(dbFCIASQAS, query);
		String nameUser = ExecuteQuery.getData(0, "XXAC_USERNAME");
		//String nameUser = data.get("nombreUsuario");
		validacion = pa.accesoCorrectoPortal(nameUser);
		assertTrue(validacion, "No se accede correctamente al portal de aplicaciones");
		
		
		/**
		 *                                             PASO 2
		 ******************************************************************************************/
		
		addStep("Seleccionar Inventario de Espacios V2");
		pa.clickPortalInventarioEspacios();
		u.waitForLoadPage();
		validacion = mp.accesoCorrectoIEP();
		assertTrue(validacion, "No se ingresa al Inventario de espacios correctamente.");
		
		/**
		 *                                             PASO 3
		 ******************************************************************************************/
		
		addStep("Verificar que se muestre la informaci�n del usuario, el negocio y el perfil");

		boolean validacionInfo = mp.verificarInformacion(nameUser, negocio, perfil);
			
		if (validacionInfo) {
			testCase.addTextEvidenceCurrentStep("Se muestra la informacion del usuario, negocio y perfil");
		}else {
			testCase.addTextEvidenceCurrentStep("La informacion del usuario y/o negocio y/o perfil no son correctas");
		}
		
		assertTrue(validacionInfo, "No se muestra la informaci�n esperada");

		
		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep("Validar que se visualicen los MENUS");
		mp.clickMenus();
		switch(perfil.toUpperCase()) {
		
			case "ADMINISTRADOR": 
				if (negocio.toUpperCase().equals("OXXO") || negocio.toUpperCase().equals("BARA")){
					mpo.menuAdministrador();
				} else if (negocio.toUpperCase().equals("MARCA")) {
					mpo.menuAdministradorM();
				}
				
			break;
			
			case "PLAZA": mpo.menuPlaza();
			break;
			
			case "ESTAFETA": mpo.menuEstafeta();
			break;
			
		}
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("Validar que se muestre en el encabezado de la pantalla el Negocio, periodo y estatus del periodo");
		validacion = mp.verificarEncabezado(negocio);
		if (validacion) {
			testCase.addTextEvidenceCurrentStep("Se muestra la informacion del negocio y periodo");
		}else {
			testCase.addTextEvidenceCurrentStep("La informacion del negocio y/o periodo no se muestran");
		}
		
		assertTrue(validacion, "No Muestra la informaci�n en el encabezado");
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("Seleccionar menu Espacios --> Carga masiva de Inventario");
		
		switch(perfil.toUpperCase()) {
		case "ADMINISTRADOR": 
			if (negocio.toUpperCase().equals("OXXO") || negocio.toUpperCase().equals("BARA")){
				mp.clickEspacios();
			}
			
		break;
		
		case "PLAZA": mp.clickEspaciosPO();
		break;
		
		}
		
		mp.clickCargaMasivaInventarios();
		
		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		
		addStep("Se muestra la pantalla con los filtros: " + 
				"Carga de archivo " + 
				"Sistema " + 
				"Tipo" + 
				"y el boton : \"\"Subir Archivo\"\"\"");
		
		validacion = cmi.pantallaFiltros();
		assertTrue(validacion, "No se muestran correctamente los filtros");
		
		String numNextToLastFile = cmi.getLastFile();
		
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		
		addStep("Seleccionar archivo CSV desde tu maquina ");
		
		//cmi.clickChooseFileBtn();
		validacion = cmi.cargarArchivo();
		assertTrue(validacion, "No se pudo cargar correctamente el archivo");
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		
		addStep("Escoger el sistema y dar click en el boton de \"Subir Archivo\"");
		
		cmi.clickSubirArchivoBtn();
		
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
		
		addStep("El archivo comienza a cargarse y en pantalla se muestra lo siguiente: " + 
				"Archivo: Ruta y nombre del documento " + 
				"Fecha Inicio: Fecha de inicio de carga " + 
				"Fecha Fin: Fecha fin de carga " + 
				"Conteo: Porcentaje de carga (Avance al momento de la consulta) " + 
				"Estatus: Estatus de carga (Validando, ejecutando, cancelado o terminado) " + 
				"Log: Informaci�n de la carga y log de errores");
		
		me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		
		String numLastFile = cmi.getLastFile();
		if(numLastFile != numNextToLastFile) {
			validacion = cmi.validarDatosExcelCargado();
			assertTrue(validacion, "La informaci�n no se muestra correctamente");
		}else {
			assertTrue(false, "No se cre� un registro nuevo para el archivo cargado");
		}
		
		/**
		 *                                             PASO 11
		 ******************************************************************************************/
		
		addStep("Se ejecuta el proceso de validaciones: " + 
				"Plaza: Busca registro en XXINE_CAT_PLAZAS por nombre (S�lo plazas activas). Si existe, contin�a revisando la siguiente validaci�n. " + 
				"Si no existe, escribe en log: No se encontr� la plaza con Nombre <valor celda>. " +  
				"ESPACIO: Busca registro en XXINE_CAT_ESPACIOS por Nombre de Espacio, Negocio y Sistema. Si existe, contin�a revisando la siguiente validaci�n. " + 
				"Si no existe, escribe en log: No se encontr� la tienda con CR <valor celda>. " + 
				"El negocio y sistema son par�metros recibidos de la pantalla. " + 
				"CANTIDAD:Validar que sea num�rico, que no est� vac�o y que no sea mayor que 100. " + 
				"Si cumple, contin�a revisando la siguiente validaci�n. " + 
				"Si no cumple, escribir error en log");
		
		validacion = cmi.esperarCarga();
		assertTrue(validacion , "No ha terminado de subirse el archivo");
		
		if(cmi.existenErrores()) {
			validacion = cmi.validarLog();
			assertTrue(validacion, "No se genero el archivo Log");
		}
		
		/**
		 *                                             PASO 12
		 ******************************************************************************************/
		
		addStep("Validar que se ejecuten los INSERT/UPDATE en XXINE_ESPACIOS_TIENDA usando el id de la tienda y espacio buscados. " + 
				"Al terminar el proceso se muestra un mensaje en la parte inferior derecha con la confirmacion.");
		
		query = "SELECT * FROM " + db + ".XXINE_ESPACIOS_TIENDA "
				+ "WHERE LAST_UPDATE_DATE >= trunc (sysdate) AND ROWNUM <= 10";
		
		ExecuteQuery = executeQuery(dbFCIASQAS, query);
		boolean StatusQuery = ExecuteQuery.isEmpty();
		
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		
		if (!StatusQuery) {
			testCase.addTextEvidenceCurrentStep("Se muestran correctamente los registros del archivo cargado");
			
		} else {			
			testCase.addTextEvidenceCurrentStep("No se encontro ningun registro del archivo cargado");
		}
		
		
		query = "SELECT count(*) FROM " + db + ".XXINE_ESPACIOS_TIENDA "
				+ "WHERE LAST_UPDATE_DATE >= trunc (sysdate)";
		
		ExecuteQuery = executeQuery(dbFCIASQAS, query);
		StatusQuery = ExecuteQuery.isEmpty();
		
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ConsultaDireccionesEnvio_BotonBuscar_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}
	
	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}
}