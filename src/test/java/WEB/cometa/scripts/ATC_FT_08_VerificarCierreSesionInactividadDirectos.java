package WEB.cometa.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**Desc:Validar que la plataforma cierra la sesiÃ³n para el perfil Directos luego de que se detecte inactividad en el portal por quince minutos

* MTC-FT-08- E1 - Verificar cierre de sesiÃ³n por inactividad en Directos
* @author Yair Lizarraga
* @date Junio 2024
*/
public class ATC_FT_08_VerificarCierreSesionInactividadDirectos extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_08_VerificarCierreSesionInactividadDirectos_test (HashMap<String, String> data) throws Exception {
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
		addStep("Ingresar las credenciales correspondientes al perfil de Cedis para el inicio de sesiÃ³n"); 
		u.get(data.get("Url")); // toma el valor Url del data provider
		u.hardWait(3); // tiempo en segundos
		pc.PrivatePortal(); 
		pc.IniciarSesion(user, password);
		pc.ClicPortalCometa();
		
		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("Mantener inactividad en el portal durante quince minutos");
		boolean validarCierreSesion = pc.ValidarCierreDeSesionInactividad();
		if(validarCierreSesion)
			testCase.addTextEvidenceCurrentStep("Se cerro sesion");
		else
			testCase.addTextEvidenceCurrentStep("No se cerro sesion");
		assertTrue(validarCierreSesion, "Se cerro sesion de forma esperada");
	}
	

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		return;
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Contar con credenciales vÃ¡lidas para el perfil de Directos";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que la plataforma cierra la sesiÃ³n para el perfil Directos luego de que se detecte inactividad en el portal por quince minutos";
	}

	@Override
	public String setTestDesigner() {
		return"Yair Lizarraga";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_08_VerificarCierreSesionInactividadDirectos_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}