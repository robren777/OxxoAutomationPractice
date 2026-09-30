package WEB.inventarioEspacios.scripts.CargaMasivaEspacios;

import static org.testng.Assert.assertTrue;

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

public class ATC_FT_003_CargaMasivaEspacios_Equipos_Espacios_Eliminar extends BaseExecution{
	
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_003_CargaMasivaEspacios_Equipos_Espacios_Eliminar_test(HashMap<String, String> data) throws Exception {
	
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
		
		testCase.setTest_Description("ATC_FT_003_CargaMasivaEspacios_Equipos_Espacios_Eliminar - "+ perfil +" " + negocio);

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
		validacion = mp.verificarInformacion(nameUser, negocio, perfil);
		if (validacion) {
		testCase.addTextEvidenceCurrentStep("Se muestra la informacion del usuario, negocio y perfil");
		}else {
		testCase.addTextEvidenceCurrentStep("La informacion del usuario y/o negocio y/o perfil no son correctas");
		}
		
		assertTrue(validacion, "No Muestra la informaci�n");
		
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
		
		addStep("Seleccionar en el grid una carga que se desee eliminar dando clic en el boton eliminar");
		
		if (!numNextToLastFile.equals("")) {
			cmi.clickEliminar();
		}else {
			assertTrue(false, "No hay cargas en el grid");
		}
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		
		addStep("Muestra mensaje de confirmacion: �Esta seguro que quiere eliminar el proceso?");
		
		validacion = cmi.confirmarEliminacion();
		assertTrue(validacion, "No aparece correctamente la ventana de confirmacion");
		
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
		
		addStep("Se da clic en confirmar");
		
		cmi.clickConfirmarBtn();
		
		/**
		 *                                             PASO 11
		 ******************************************************************************************/
		
		addStep("Se elimina el proceso corectamente y muestra mensaje de confirmaci�n.");
		
		validacion = cmi.mensajeConfirmacion();
		assertTrue(validacion, "No aparece correctamente el mensaje de eliminacion");
		
		if (numNextToLastFile.equals(cmi.getLastFile())) {
			assertTrue(false, "La carga seleccionada se sigue mostrando en pantalla");
		}
	
		
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
		return "ATC_FT_003_CargaMasivaEspacios_Equipos_Espacios_Eliminar_test";
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
