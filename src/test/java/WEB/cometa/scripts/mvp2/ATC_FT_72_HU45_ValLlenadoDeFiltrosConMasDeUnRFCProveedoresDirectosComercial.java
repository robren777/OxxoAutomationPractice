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
 * Validar focus en opcion seleccionada en el menu 
 * MTC-FT-73-HU41-Validar opcion seleccionada en menu
 * @author Jose Zarate - 1000072516
 * @date Octubre 2024
*/
public class ATC_FT_72_HU45_ValLlenadoDeFiltrosConMasDeUnRFCProveedoresDirectosComercial extends BaseExecution {
	
	SeleniumUtil u;
	PortalCometa pom;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_72_HU45_ValLlenadoDeFiltrosConMasDeUnRFCProveedoresDirectosComercial_test (HashMap<String, String> data) throws Exception {
		
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
		addStep("Ingresar las credenciales correspondientes al perfil de Cometa para el inicio de sesion");
		pom.IniciarSesion(user, password);
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Ingresar al portal cometa desde Access Control");
		pom.ClicPortalCometa();
		
		/*
		 *                                             PASO 4
		 ******************************************************************************************/
		addStep("Ingresar al apartado Directos");
		pom.clickDirectos();
		
		/*
		 *                                             PASO 5
		 ******************************************************************************************/
		addStep("Ingresar a Comprobantes");
		pom.clickComprobantesDeCompra();
		
		/*
		 *                                             PASO 6
		 ******************************************************************************************/
		addStep("Validar que listado de proveedores en el filtro");
		pom.clickProveedor();
		u.getDriver().quit();
		
		
	}
	
	@AfterMethod
	public void afterMethod() {
		 // u.getDriver().quit();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		return;
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Contar con credenciales validas para el inicio de sesion y acceso a Directos";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que el filtro de proveedores muestre la informacion de los RFC";
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
