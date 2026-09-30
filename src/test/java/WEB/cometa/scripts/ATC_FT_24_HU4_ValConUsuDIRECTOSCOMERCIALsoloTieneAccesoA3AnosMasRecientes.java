package WEB.cometa.scripts;


import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**
 * Validar que solo se tiene informacion de los últimos 3 años 
 * MTC-FT-24-HU4- Validar con usuario DIRECTOS COMERCIAL, que solo se tiene acceso en el grid a los 3 años más recientes a partir de la fecha actual
 * @author Jose Zarate - 1000072516
 * @date Octubre 2024
*/
public class ATC_FT_24_HU4_ValConUsuDIRECTOSCOMERCIALsoloTieneAccesoA3AnosMasRecientes extends BaseExecution {
	
	SeleniumUtil u;
	PortalCometa pom;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_24_HU4_ValConUsuDIRECTOSCOMERCIALsoloTieneAccesoA3AnosMasRecientes_test (HashMap<String, String> data) throws Exception {
		
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
		addStep("Ingresar las credenciales correspondientes para el inicio de sesion");
		pom.IniciarSesion(user, password);
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Ingresar a la pagina de Comprobantes de Compra");
		pom.ClicPortalCometa();
		pom.clickDirectos();
		pom.clickComprobantesDeCompra();
		
		/*
		 *                                             PASO 4
		 ******************************************************************************************/
		addStep("Realizar una búsqueda filtrando menos de 3 años de antiguedad");
		pom.clickRangoDeFechas();
		pom.select2024();
		pom.select2021();
		
		
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
		return "Contar con credenciales válidas para el inicio de sesión del perfil\r\n"
				+ "Contar con información de 3 años de antiguedad";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que solo se tiene informacion de los últimos 3 años ";
	}

	@Override
	public String setTestDesigner() {
		return"Jose Zarate - 1000072516";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_24_HU4_ValConUsuDIRECTOSCOMERCIALsoloTieneAccesoA3AnosMasRecientes_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
