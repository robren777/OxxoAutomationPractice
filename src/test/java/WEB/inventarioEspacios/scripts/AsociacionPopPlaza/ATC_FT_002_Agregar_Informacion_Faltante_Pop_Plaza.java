package WEB.inventarioEspacios.scripts.AsociacionPopPlaza;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.AsociarPopPlazasIEP;
import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;


/**
 * Desc: Script que valida se muestre el mensaje de Informaci�n Faltante al no ingresar filtros
 * 
 * 6693 - MTC-FT-267-Agregar Informacion Faltante_Pop y plaza_Admin_Oxxo
 * 6693 - MTC-FT-267-Agregar Informacion Faltante_Pop y plaza_Admin_Bara
 * 6693 - MTC-FT-267-Agregar Informacion Faltante_Pop y plaza_Admin_Marca
 * 
 * @author Andrea Contreras
 * @date Mayo 2022
 */
public class ATC_FT_002_Agregar_Informacion_Faltante_Pop_Plaza extends BaseExecution {
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_002_Agregar_Informacion_Faltante_Pop_Plaza_test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		SQLUtil dbFCIASQA = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL, GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");
		
		testCase.setTest_Description("Agregar Informaci�n Faltante POP y Plaza - "+perfil+" "+negocio);
		
		
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal");
		
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);	
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
		AsociarPopPlazasIEP app = new AsociarPopPlazasIEP(u, testCase);
		
		u.getDriver().manage().window().maximize();
		u.get(pa.getUrl());
		
		pa.proceedToPortal();
		u.waitForLoadPage();
		pa.cicloLogin(user, password);
		
		/**
		 *                                             PASO 2
		 ******************************************************************************************/
		 
		addStep("Seleccionar Inventario de Espacios");
		
		pa.clickPortalInventarioEspacios();
		
		/**
		 *                                             PASO 3
		 ******************************************************************************************/
		
		addStep("Verificar la informaci�n del usuario y que se muestre el negocio y el perfil.");
			
		String db = "";
		switch(negocio.toUpperCase()) {
			case "BARA": 
				db = "XXINE_BA";
			break;
			
			default: db = "XXINE";
			break;
		
		}
		
		String query = "select XXAC_USERNAME from " + db + ".xxine_users_empleados_v where XXAC_USER_ID = '" + user + "'";
		SQLResult ExecuteQuery = executeQuery(dbFCIASQA, query);
		String nameUser = ExecuteQuery.getData(0, "XXAC_USERNAME");
		
		boolean validacion = mp.verificarInformacion(nameUser, negocio, perfil);
		
		if (validacion) {
			testCase.addTextEvidenceCurrentStep("Se muestra la informacion del usuario, negocio y perfil");
		}else {
			testCase.addTextEvidenceCurrentStep("La informacion del usuario y/o negocio y/o perfil no son correctas");
		}
		
		assertTrue(validacion, "No Muestra la informaci�n");
		
		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep("Validar que visualice los MENUS:");
		
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
		
		addStep(" Validar que se muestre en el encabezado de la pantalla el Negocio, periodo y estatus del periodo.");
		
		boolean verEncabezado = mp.verificarEncabezado(data.get("Negocio"));
		
		if(verEncabezado)
			testCase.addTextEvidenceCurrentStep("La informaci�n del negocio, periodo y estatus del periodo es correcta");
		else
			testCase.addTextEvidenceCurrentStep("La informaci�n del negocio, periodo y estatus del periodo es incorrecta");
		
		assertTrue(verEncabezado, "No Muestra la informaci�n correcta");
		
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("Seleccionar Menu-> Adminsitracion --> Asociacion de POPs");
	    //mp.clickMenus();
		mp.clickAdministracion();
		mp.clickAsociarPopPlazaOp();

		
		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		
		addStep("Se visualiza la pantalla de Asociacion de POPs y los campos: Tipo de POP, POP, Buscar plazas por nombre, Plazas filtradas");
		
		boolean existenCampos = app.visualizarCampos();
         
		assertTrue(existenCampos, "No se visualizan todos los campos en la pantalla: Tipo de POP, POP, Plazas por nombre, Plazas filtradas");
		
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		addStep("Dar clic al boton Guardar");

		app.clickGuardar();
		app.clickBtnConfirmarPopUpGuardar();
				
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		addStep("Se muestra el mensaje: 'seleccione pop/plazas a asociar!'");

		boolean validarMsg = app.validarMsgFaltaPopPlaza();

		assertTrue(validarMsg, "No se muestra el mensaje esperado: seleccione pop/plazas a asociar!");
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
		return "ATC_FT_002_Agregar_Informacion_Faltante_Pop_Plaza_test";
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