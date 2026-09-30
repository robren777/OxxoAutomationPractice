package WEB.cometa.scripts.mvp2;


import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**
 * Validar que se oculten registros por fecha y plaza/tienda
 * MTC-FT-41-E1 - Validar el ocultar registros que no deben verse
 * @author Jose Zarate - 1000072516
 * @date Octubre 2024
*/
public class ATC_FT_41_E1_ValidarElOcultarRegistrosQueNoDebenVerse extends BaseExecution {
	
	SeleniumUtil u;
	PortalCometa pom;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_41_E1_ValidarElOcultarRegistrosQueNoDebenVerse_test (HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("Portal Web: Cometa");
		
		String user = data.get("user");
		String password = data.get("password");
		u = new SeleniumUtil(new ChromeTest(), true);
		pom = new PortalCometa(u, testCase);
		
		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Ingresar a la liga del portal web"); 
		u.get(data.get("Url"));
		pom.PrivatePortal();
		pom.validarAccesoLogin();
		
		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("Ingresar las credenciales correspondientes al perfil Cometa para el inicio de sesion");
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
		pom.clickDirectos();
		
		
		/*
		 *                                             PASO 5
		 ******************************************************************************************/
		addStep("Ingresar a Comprobantes de Compra y Resumen");
		pom.clickComprobantesDeCompra();
		pom.scrollToFa();		
		
		/*
		 *                                             PASO 5
		 ******************************************************************************************/
		addStep("Validar que no se muestren las OC que no deben verse");
		
		pom.llenaFiltros();
		pom.consultarComprobantes();
		pom.scrollToFa();
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
		return "Contar con credenciales validas para el inicio de sesion de perfil DIRECTOS";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que se oculten registros por fecha y plaza/tienda";
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
