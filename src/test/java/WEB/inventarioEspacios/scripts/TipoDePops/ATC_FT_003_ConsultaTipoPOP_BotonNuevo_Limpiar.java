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
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_003_ConsultaTipoPOP_BotonNuevo_Limpiar extends BaseExecution{
	
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_003_ConsultaTipoPOP_BotonNuevo_Limpiar_Test(HashMap<String, String> data) throws Exception {
		
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
		
		testCase.setTest_Description("ATC_FT_003_ConsultaTipoPOP_BotonNuevo_Limpiar - "+ perfil +" " + negocio);

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
		
		addStep("Dar clic en el bot�n Nuevo");
		
		tp.clickNuevoBtn();
		
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		
		addStep("Validar que el sistema mostrar� la p�gina \"Captura de Tipos de POP\" mostrando los siguientes campos habilitados: \r\n" + 
				"Nombre, deber� ser un campo de texto alfanum�rico\r\n" + 
				"Para Promociones, deber� mostrar las opciones SI / NO. En caso de seleccionar SI, el resto de las opciones se ocultan. \r\n" + 
				"Ver Segmento, deber� mostrar las opciones SI / NO \r\n" + 
				"Ver kit,  deber� mostrar las opciones SI / NO \r\n" + 
				"Ver Contenido, deber� mostrar las opciones SI /NO \r\n" + 
				"Ver Precio, deber� mostrar las opciones SI / NO \r\n" + 
				"Ver Tipo de Negocio, deber� mostrar las opciones SI / NO \r\n" + 
				"Bot�n. Grabar\r\n" + 
				"Bot�n. Limpiar\r\n" + 
				"Bot�n. Cancelar");
		
		validacion = tp.pantallaTipoPopNuevo();
		assertTrue(validacion, "No se muestran correctamente los componentes");
		
		//ESTOS PASOS NO SE REALIZAR�N PORQUE ESTAS FUNCIONALIDADES ESTAN DESHABILITADAS EN EL SISTEMA
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		
		//addStep("Seleccionar la opci�n SI en Para Promociones");
		
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
		
		//addStep("El sistema deber� ocultar el resto de las opciones.");
		
		
		
		/**
		 *                                             PASO 11
		 ******************************************************************************************/
		
		//addStep("Dar clic en el bot�n Limpiar");
		
		
		
		/**
		 *                                             PASO 12
		 ******************************************************************************************/
		
		//addStep("El sistema deber� borrar las opciones seleccionadas y mostrar nuevamente todas las opciones.");
		
		
		
		/**
		 *                                             PASO 13
		 ******************************************************************************************/
		
		addStep("Capturar Nombre del tipo de POP, realizar selecci�n m�ltiple de opciones y dar clic en Limpiar");
		
		validacion = tp.buscarTipoPop(data.get("TipoPop"));
		assertTrue(validacion, "No se puede ingresar el nombre del Pop");
		
		/**
		 *                                             PASO 14
		 ******************************************************************************************/
		
		addStep("El sistema deber� borrar las opciones seleccionadas incluido el campo de nombre.");
		
		tp.clickLimpiarPopBtn();
		
		validacion = tp.limpiarPantalla();
		assertTrue(validacion, "No se borro el nombre introducido");
		
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
		return "ATC_FT_003_ConsultaTipoPOP_BotonNuevo_Limpiar_Test";
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
