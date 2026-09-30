package RMS.BimodalRMS.scripts;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import modelo.BaseExecution;
import RMS.BimodalRMS.om.PortalRMS;
import RMS.util.DbUtils;
import RMS.util.GlobalVariables;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_001_ValidarLoginPortalRMSCredencialesCorrectas extends BaseExecution {
	
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ValidarLoginPortalRMSCredencialesCorrectas_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Bimodal RMS");
	
		u = new SeleniumUtil(new ChromeTest(),true);
		
		PortalRMS pr = new PortalRMS(u,testCase);
		
		String linkRMS = data.get("linkRMS");
		
		addStep("Ir al portal RMS, Link: http://10.184.37.86:7013/Rms/faces/RmsLogin");
		pr.goToRMS(linkRMS);
		
		addStep("Ingresar Usuario válido");
		pr.typeUser("RMSUSER1");

		addStep("Ingresar Contraseña válida");
		pr.typePass("Comercio.1");
		
		addStep("Dar click en botón \"Conexión\"");
		pr.clickbtnConexion();
		assertTrue(pr.validateOpenPortal(),"No entró exitosamente al portal RMS");
		
	}
	
	
	@AfterMethod
	public void tearDown() {
		u.quit();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ValidarLoginPortalRMSCredencialesCorrectas";
	}

	@Override
	public String setTestDescription() {
		return "Se valida el acceso al portal RMS usando usuario y contraseña correctas.";
	}

	@Override
	public String setTestDesigner() {
		return "Roberto Daniel Rentería Escamilla";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1. Credenciales existentes de Portal RMS\r\n"
				+ "2. Conexión a VPN de OXXO activa";
	}

	@Override
	public void beforeTest() {
	}

}
