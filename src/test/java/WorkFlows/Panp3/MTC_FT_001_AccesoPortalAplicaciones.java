package WorkFlows.Panp3;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.Panp3.PomPanp;
import modelo.BaseExecution;
//import utils.Panp3.UtilsPanp;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class MTC_FT_001_AccesoPortalAplicaciones extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_AccesoPortalAplicaciones(HashMap<String, String> data) {		
		
		/*
		 * variables
		 */
		SeleniumUtil u = new SeleniumUtil(new FirefoxTest(), true);
		PomPanp pom = new PomPanp(u, "", testCase);
		//UtilsPanp panp = new UtilsPanp();
		String RFC = "AAAA030116"; //Es necesario cabiar este RFC en cada prueba
		
		try {
			// Step 1.-/////////////////////////////////////////////////////////
			addStep("Ingresar a la liga: http://fcowlq02.femcom.net:7032/AccessControl/pages/login_form.jsf");
			pom.deleteCookies();
			u.get(data.get("urlPortalAplicaciones"));
			testCase.addScreenShotCurrentStep(u,"Se muestra la pantalla de Login de acces control");
			assertTrue(pom.validateAccessPortalAplicaciones(), "No se pudo acceder a la pantalla del portal de aplicaciones");

			// Step 2.-/////////////////////////////////////////////////////////
			addStep("Ingresar los datos de\r\n"
					+ "RFC\r\n"
					+ "Pass: SADEL1");
			pom.loginUltimus();
			pom.loginPortalAplicaciones(RFC, data.get("pass"));
			testCase.addScreenShotCurrentStep(u, "Se muestra la pantalla para cambiar la contraseña");
			assertTrue(pom.validateAccessCambioPass(), "No se muestra la pantalla para cambiar la contraseña");

			/////////////////////////////////////////////////////////
			addStep("Se ingresa y re ingresa la contraseña nueva para asignarsela.");
			pom.cambioPass(data.get("newPass"));
			testCase.addScreenShotCurrentStep(u, "Se guarda la informacion y muestra  la pantalla de access control");
			assertTrue(pom.validateAccessControl(), "Fallo el despliegue de la pantalla de Access Control");
			
			/////////////////////////////////////////////////////////
			addStep("se selecciona la liga del PANP v3");
			pom.clickPanpV3();
			testCase.addScreenShotCurrentStep(u, "Se muestra el portal del PANP con una ventana de Aviso Importante");
			assertTrue(pom.validateShowAviso(), "No se muestra l apantalla de aviso");
			
			/////////////////////////////////////////////////////////
			addStep("Se acepta la ventana");
			pom.aceptarAviso();
			testCase.addScreenShotCurrentStep(u, "Se muestra la pantalla para llenar la informacion del proveedor.");
			assertTrue(pom.validateAccessInfoProveedor(), "No se muestra la pantalla para llenar la informacion del proveedor");			
			
			/////////////////////////////////////////////////////////
			addStep("Se llena la informacion del proveedor, se cargan los archivos correspondientes y se autoriza la solicitud.");
			//pom.InfoProveedor();			
			
			//u.close();
		}catch(Exception ex) {
			testCase.addScreenShotCurrentStep(u, ex.getMessage());
			assertTrue(false, ex.getMessage());
		}
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Construido. Se tiene por objetivo validar el flujo del portal de aplicaciones de PANP3";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Jose Antonio Zarate";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_AccesoPortalAplicaciones";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}