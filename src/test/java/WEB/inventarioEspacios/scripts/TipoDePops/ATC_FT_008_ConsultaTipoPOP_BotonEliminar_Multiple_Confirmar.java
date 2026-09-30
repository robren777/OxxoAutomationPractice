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

public class ATC_FT_008_ConsultaTipoPOP_BotonEliminar_Multiple_Confirmar extends BaseExecution{
	
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_008_ConsultaTipoPOP_BotonEliminar_Multiple_Confirmar_Test(HashMap<String, String> data) throws Exception {
		
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
		
		testCase.setTest_Description("ATC_FT_008_ConsultaTipoPOP_BotonEliminar_Multiple_Confirmar - "+ perfil +" " + negocio);

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
		
		addStep("Seleccionar mas de un checkbox correspondiente al registro de Tipo de POP que desee eliminar");
		
		validacion = tp.clickSeleccionarPop(data.get("TipoPop1"));
		assertTrue(validacion, "No se encontr� el pop a eliminar " + data.get("TipoPop1"));
		
		validacion = tp.clickSeleccionarPop(data.get("TipoPop2"));
		assertTrue(validacion, "No se encontr� el pop a eliminar " + data.get("TipoPop2"));
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		
		addStep("El sistema habilitar� el bot�n Eliminar");
		
		validacion = tp.isVisibleEliminarBtn();
		assertTrue(validacion, "No se habilito el bot�n Eliminar");
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
		
		addStep("Dar clic en el bot�n Eliminar");
		
		tp.clickEliminarBtn();
		
		/**
		 *                                             PASO 11
		 ******************************************************************************************/
		
		addStep("El sistema deber� mostrar el mensaje mensaje \"�Est� seguro que quiere eliminar el tipo "
				+ "de POP<<nombre>>? y las opciones Aceptar / Cancelar.");
		
		validacion = tp.confirmarMensaje();
		assertTrue(validacion, "No se muestra correctamente el mensaje de confirmacion");
		
		/**
		 *                                             PASO 12
		 ******************************************************************************************/
		
		addStep("Dar clic en el bot�n Aceptar");
		
		tp.clickConfirmar();
		
		/**
		 *                                             PASO 13
		 ******************************************************************************************/
		
		addStep("El sistema cerrar� la ventana del mensaje, se borra el registro y se actualiza el listado.\r\n" + 
				" Validar que se elimine el registro seleccionado de la tabla XXINE_CAT_TIPOS_POP.");
		
		query = "SELECT TIPO_POP_ID, NOMBRE_TIPO_POP "
				+ "FROM XXINE.XXINE_CAT_TIPOS_POP "
				+ "WHERE NOMBRE_TIPO_POP = '" + data.get("TipoPop1") 
				+ "' AND NOMBRE_TIPO_POP = '" + data.get("TipoPop2") + "'";
		
		ExecuteQuery = executeQuery(dbFCIASQAS, query);
		StatusQuery = ExecuteQuery.isEmpty();
		
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		
		if (!StatusQuery) {
			assertTrue(false, "Los registros se encuentran en la tabla");
		} else {			
			testCase.addTextEvidenceCurrentStep("Los registros no se encuentran en la tabla");
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
		return "ATC_FT_008_ConsultaTipoPOP_BotonEliminar_Multiple_Confirmar_Test";
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
