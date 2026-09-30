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

public class ATC_FT_006_ConsultaTipoPOP_BotonEditar_Actualizar extends BaseExecution{
	
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_006_ConsultaTipoPOP_BotonEditar_Actualizar_Test(HashMap<String, String> data) throws Exception {
		
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
		
		testCase.setTest_Description("ATC_FT_006_ConsultaTipoPOP_BotonEditar_Actualizar - "+ perfil +" " + negocio);

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
		
		addStep("Dar clic en el bot�n Editar del Tipo de POP que se desee modificar");
		
		validacion = tp.clickEditarPop(data.get("TipoPop"));
		assertTrue(validacion, "No se encontr� el pop a editar");
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		
		addStep("Validar que el sistema debe habilitar la p�gina \"Edici�n de Tipos de POP\"  mostrando el siguiente contenido:\r\n" + 
				"-Id Tipo de POP: Muestra el valor que asigno autom�ticamente el sistema, este valor no puede modificarse.\r\n" + 
				"-Para Promociones\r\n" + 
				"-Ver Segmento\r\n" + 
				"-Ver kit\r\n" + 
				"-Ver Contenido\r\n" + 
				"-Ver Precio\r\n" + 
				"-Ver Tipo de Negocio");
		
		validacion = tp.pantallaTipoPopEditar();
		assertTrue(validacion, "No se muestran correctamente los componentes");
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
		
		addStep("Bot�n Actualizar.");
		
		tp.clickGuardarBtn();
		
		/**
		 *                                             PASO 11
		 ******************************************************************************************/
		
		addStep("Bot�n Cancelar.");
		
		tp.clickCancelar();
		
		/**
		 *                                             PASO 12
		 ******************************************************************************************/
		
		addStep("Realizar modificaciones(selecci�n m�ltiple de opciones) y posteriormente dar clic en el bot�n Actualizar.");
		
		validacion = tp.editarTipoPop(data.get("TipoPopEditar"));
		assertTrue(validacion, "No se puede editar el nombre del Pop");
		
		tp.clickGuardarBtn();
		tp.clickConfirmar();
		
		/**
		 *                                             PASO 13
		 ******************************************************************************************/
		
		addStep("El sistema deber� actualizar la informaci�n que se modific� del tipo de POP exitosamente y regresa a la p�gina anterior.");
		
		u.waitForLoadPage();
		validacion = tp.buscarPop(data.get("TipoPopEditar")); 
		//assertTrue(validacion, "No se encontr� el pop editado");
		
		/**
		 *                                             PASO 14
		 ******************************************************************************************/
		
		addStep("Ingresar a la BD XXINE para consultar la tabla XXINE_CAT_TIPOS_POP y validar que se genere la actualizacion del registro editado.");
		
		query = "SELECT TIPO_POP_ID, NOMBRE_TIPO_POP "
				+ "FROM XXINE.XXINE_CAT_TIPOS_POP "
				+ "WHERE NOMBRE_TIPO_POP = '" + data.get("TipoPopEditar") + "'";
		
		ExecuteQuery = executeQuery(dbFCIASQAS, query);
		StatusQuery = ExecuteQuery.isEmpty();
		
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		
		if (!StatusQuery) {
			testCase.addTextEvidenceCurrentStep("El registro se encuentra en la tabla");
		} else {			
			//assertTrue(false, "El registro no se encuentra en la tabla");
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
		return "ATC_FT_006_ConsultaTipoPOP_BotonEditar_Actualizar_Test";
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
