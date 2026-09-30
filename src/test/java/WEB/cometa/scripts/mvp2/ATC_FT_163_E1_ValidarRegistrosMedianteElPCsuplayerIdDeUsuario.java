package WEB.cometa.scripts.mvp2;


import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**
 * Validar que los regsitros mostrados en el grid de Cedis correspondan con el id de el usuario
 * MTC-FT-163-E1-Validarregistros mediante el PC suplayer id de usuario
 * @author Jose Zarate - 1000072516
 * @date Octubre 2024
*/
public class ATC_FT_163_E1_ValidarRegistrosMedianteElPCsuplayerIdDeUsuario extends BaseExecution {
	
	SeleniumUtil u;
	PortalCometa pom;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_163_E1_ValidarRegistrosMedianteElPCsuplayerIdDeUsuario_test (HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("Portal Web: Cometa");
		
		String user = data.get("user");
		String password = data.get("password");
		u = new SeleniumUtil(new ChromeTest(), true);
		pom = new PortalCometa(u, testCase);
		
		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Ingresar a la liga del portal"); 
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
		addStep("Ingresar a CEDIS");
		pom.clickCedis();
		
		/*
		 *                                             PASO 5
		 ******************************************************************************************/
		addStep("Ingresar a Recepcion");
		pom.clickRecepcion();		
		
		/*
		 *                                             PASO 6
		 ******************************************************************************************/
		addStep("Validar los registros mostrados en el portal");
		pom.scrollToFecha();
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
		return "Credenciales de acceso a Cedis y conexion a BD";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que los regsitros mostrados en el grid de Cedis correspondan con el id de el usuario";
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
