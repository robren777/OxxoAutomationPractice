package WEB.cometa.scripts.mvp2;


import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**
 * Validar que al haber una asignacion de plaza o plazas se valide el cambio
 * MTC-FT-49- HU12- Validar Plazas asignadas al perfil
 * @author Jose Zarate - 1000072516
 * @date Octubre 2024
*/
public class ATC_FT_49_HU12_ValidarPlazasAsignadasAlPerfil extends BaseExecution {
	
	SeleniumUtil u;
	PortalCometa pom;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_49_HU12_ValidarPlazasAsignadasAlPerfil_test (HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("Portal Web: Cometa");
		
		String user = data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		u = new SeleniumUtil(new ChromeTest(), true);
		pom = new PortalCometa(u, testCase);
		 /*
         *                                           PASO 1
         ******************************************************************************************/
        addStep("Ingresar a la liga");
		u.get(data.get("Url"));
        u.refresh();
        u.hardWait(3);
        testCase.addScreenShotCurrentStep(u, "Pantalla cargada");
        pom.PrivatePortal();
		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("Ingresar las credenciales correspondientes para el inicio de sesion");
		pom.IniciarSesion(user, password);
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Ingresar al portal Cometa desde Access Control");
		pom.ClicPortalCometa();
		
		/*
		 *                                             PASO 4
		 ******************************************************************************************/
		addStep("Ingresar al apartado Directos");
		pom.clickDirectos_plaza();
		
		
		/*
		 *                                             PASO 5
		 ******************************************************************************************/
		addStep("Ingresar a Comprobantes");
		pom.clickComprobantesDeCompra();
		pom.scrollToFa();		
		
		/*
		 *                                             PASO 6
		 ******************************************************************************************/
		addStep("Seleccionar Plaza");		
		pom.llenaFiltros_plaza();
		pom.consultarComprobantes();
		pom.scrollToFa();
		u.getDriver().quit();
		
	}
	
	@AfterMethod
	public void afterMethod() {
		 
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		return;
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Contar con credenciales vï¿½lidas para el perfil OXXO Plaza de DIRECTOS, asignacion(es) de plaza, ticket solicitud alta plaza";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que al haber una asignacion de plaza o plazas se valide el cambio";
	}

	@Override
	public String setTestDesigner() {
		return"Jose Zarate";
	}

	@Override
	public String setTestFullName() {
		return "";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
