package WEB.cometa.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**Desc: Validar que al seleccionar el Modulo de Cedis se nos lleve a la pantalla principal con la informacion habilitada para su rol y su respectivo menu de opciones
* MTC-FT-16- E3 - Validar Menu inicio con usuario Administrativo-Directos
* @author Yair Lizarraga
* @date Junio 2024
*/
public class ATC_FT_16_ValidarMenuInicioUsuarioAdministrativoDirectos extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_16_ValidarMenuInicioUsuarioAdministrativoDirectos_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		String password = data.get("password");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometa pc = new PortalCometa(u, testCase); // crea objeto de locator
		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Ingresar a la liga del portal web"); 
		u.get(data.get("Url")); // toma el valor Url del data provider
		u.refresh();
		u.hardWait(3); // tiempo en segundos
		pc.PrivatePortal(); 
		

		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("Ingresar las credenciales correspondientes al perfil de Cedis para el inicio de sesiÃ³n");
		pc.IniciarSesion(user, password);
		pc.ClicPortalCometa();
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Validar en la pantalla de inicio");
		boolean validarMenuDirectos = pc.ValidarMenuAdminDirectos();
		if(validarMenuDirectos)
			testCase.addTextEvidenceCurrentStep("El Menu es correcto");
		else
			testCase.addTextEvidenceCurrentStep("El Menu no es correcto");
		assertTrue(validarMenuDirectos, "El Menu es correcto");
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		return;
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Contar con credenciales vÃ¡lidas para el inicio de sesiÃ³n del perfil";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar acceso menu inicio con usuario: Administrativo-Directos";
	}

	@Override
	public String setTestDesigner() {
		return"Yair Lizarraga";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_16_ValidarMenuInicioUsuarioAdministrativoDirectos_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
