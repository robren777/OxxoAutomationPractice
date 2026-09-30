package WEB.inventarioEspacios.scripts.TipoDePops;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.inventarioEspacios.om.TiposDePopsIEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_002_ConsultaTipoPOP_BotonBuscar_Limpiar extends BaseExecution{
	
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_002_ConsultaTipoPOP_BotonBuscar_Limpiar_Test(HashMap<String, String> data) throws Exception {
	
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
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
		TiposDePopsIEP tp = new TiposDePopsIEP(u, testCase);
		
		String query;
		

		String db = "";
		switch(negocio.toUpperCase()) {
			case "BARA": 
				db = "XXINE_BA";
			break;
			
			default: db = "XXINE";
			break;
		
		}

		
		testCase.setTest_Description("ATC_FT_002_ConsultaTipoPOP_BotonBuscar_Limpiar - "+ perfil +" " + negocio);

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
		boolean StatusQuery = ExecuteQuery.isEmpty();
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
		
		addStep("Seleccionar Menu->Administraci�n->Tipo POP");
		
		switch(perfil.toUpperCase()) {
		
		case "ADMINISTRADOR": 
			if (negocio.toUpperCase().equals("OXXO") || negocio.toUpperCase().equals("BARA")){
				mp.clickPops();
			} else if (negocio.toUpperCase().equals("MARCA")) {
				mp.clickPopsM();
			}
			
		break;
		
		}
		
		mp.clickTipoPopsOp();
		
		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		
		addStep("Se abre la pantalla Tipos de POP mostrando el listado de los tipos de POP existentes con el siguiente contenido;\r\n" + 
				" - Filtros. Nombre de tipo de POP\r\n" + 
				"   - Bot�n. Buscar\r\n" + 
				"   - Bot�n. Limpiar\r\n" + 
				" - Bot�n. Nuevo\r\n" + 
				" - Grid principal. ID Tipo de POP/Nombre/Para promociones.\"\r\n");
		
		validacion = tp.pantallaTipoPop();
		assertTrue(validacion, "No se muestran los componentes correctamente");
		
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		
		addStep("Realizar la busqueda  capturando <Tipo_POP> y dar clic en buscar");
		
		validacion = tp.buscarTipoPop(data.get("TipoPop"));
		assertTrue(validacion, "No se puede ingresar la informacion a buscar");
		
		tp.clickBuscarBtn();
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		
		addStep("Validar que el sistema muestra los tipo de pop que coinciden con el criterio de busqueda, "
				+ "por cada registro se mostrar� un checkbox con la siguiente informaci�n:"
				+ "Tipo de POP: El sistema asigna en autom�tico el ID cuando se da de alta un tipo de POP\r\n" + 
				"Nombre: Muestra el nombre con el que se dio de alta el tipo de POP\r\n" + 
				"Para Promociones: Muestra el valor si / no que se le asigno desde el alta.\r\n" + 
				"Bot�n. Editar");
		
		validacion = tp.resultadosBusqueda(5);
		assertTrue(validacion, "No se muestran resultados de la busqueda");
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
		
		addStep("Dar clic en el bot�n Limpiar");
		
		tp.clickLimpiarBtn();
		
		/**
		 *                                             PASO 11
		 ******************************************************************************************/
		
		addStep("El sistema limpia la pantalla, ocultando los resultados de busqueda que se hab�an obtenido.");
		
		u.hardWait(5);
		validacion = tp.limpiarPantalla();
		assertTrue(validacion, "No se limpio correctamente la pantalla");
		
		/**
		 *                                             PASO 12
		 ******************************************************************************************/
		
		addStep("Capturar Tipo de pop y dar clic en el bot�n Buscar");
		
		validacion = tp.buscarTipoPop(data.get("TipoPop2"));
		assertTrue(validacion, "No se puede ingresar la informacion a buscar");
		
		tp.clickBuscarBtn();
		
		/**
		 *                                             PASO 13
		 ******************************************************************************************/
		
		addStep("El sistema se mantiene en la pantalla de Tipos de POP, pero ahora muestra �nicamente los tipo de pop que coinciden con el criterio de busqueda, por cada registro se mostrar� un checkbox con la siguiente informaci�n:\r\n" + 
				"Tipo de POP: El sistema asigna en autom�tico el ID cuando se da de alta un tipo de POP\r\n" + 
				"Nombre: Muestra el nombre con el que se dio de alta el tipo de POP\r\n" + 
				"Para Promociones: Muestra el valor si / no que se le asigno desde el alta.\r\n" + 
				"Bot�n. Editar\r\n");
		
		validacion = tp.resultadosBusqueda(5);
		assertTrue(validacion, "No se muestran resultados de la busqueda");
		
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
		return "ATC_FT_002_ConsultaTipoPOP_BotonBuscar_Limpiar_Test";
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
