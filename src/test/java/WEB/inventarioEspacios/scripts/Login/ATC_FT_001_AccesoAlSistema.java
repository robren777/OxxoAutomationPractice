package WEB.inventarioEspacios.scripts.Login;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_001_AccesoAlSistema extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_AccesoAlSistema_test(HashMap<String, String> data) throws Exception {
		
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
		String query;
		
		String db = "";
		switch(negocio.toUpperCase()) {
			case "BARA": 
				db = "XXINE_BA";
			break;
			
			default: db = "XXINE";
			break;
		
		}
		
		//									PASO 1
		
		addStep("Ingresar al portal");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
		
		u.get(pa.getUrl());
		u.hardWait(3);
		
		pa.proceedToPortal();
		//pa.logginInventarioEspacios(user, password);
		pa.cicloLogin(user, password);
		
		//		                            PASO 2
		
		addStep("Seleccionar Inventario de Espacios");
		pa.clickPortalInventarioEspacios();
		
		//        							PASO 3
		
		addStep("Verificar que se muestre la informaci�n del usuario, el negocio y el perfil");
		
		query = "select XXAC_USERNAME from " + db + ".xxine_users_empleados_v where XXAC_USER_ID = '" + user + "'";
		SQLResult ExecuteQuery = executeQuery(dbFCIASQAS, query);
		String nameUser = ExecuteQuery.getData(0, "XXAC_USERNAME");
		
		Boolean validacion = mp.verificarInformacion(nameUser, negocio, perfil);
		
		if (validacion) {
			testCase.addTextEvidenceCurrentStep("Se muestra la informacion del usuario, negocio y perfil");
		}else {
			testCase.addTextEvidenceCurrentStep("La informacion del usuario y/o negocio y/o perfil no son correctas");
		}
		
		assertTrue(validacion, "No Muestra la informaci�n");
		
		//									PASO 4
		
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
		
		
		//									PASO 5
		
		addStep("Validar que se muestre en el encabezado de la pantalla el Negocio, periodo y estatus del periodo");
		validacion = mp.verificarEncabezado(negocio);
		if (validacion) {
			testCase.addTextEvidenceCurrentStep("Se muestra la informacion del negocio y periodo");
		}else {
			testCase.addTextEvidenceCurrentStep("La informacion del negocio y/o periodo no se muestran");
		}
		
		assertTrue(validacion, "No Muestra la informaci�n en el encabezado");
		
		
		
		
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido. Validar el acceso al sistema correcto perfil OXXO Administrador";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_AccesoAlSistema_test";
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
