package WEB.cometa.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**Desc: Validar que el menu del modulo nos lleve a los diferentes procesos habilitados para el mismo
* MTC-FT-04-E1 - Verificar accesos del MÃ³dulo Directos
* @author Yair Lizarraga
* @date Junio 2024
*/
public class ATC_FT_04_VerificarAccesosModuloDirectos extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_04_VerificarAccesosModuloDirectos_test (HashMap<String, String> data) throws Exception {
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
		addStep("Seleccionar el botÃ³n de MÃ³dulo Directos de la pantalla principal luego de iniciar sesiÃ³n en el portal"); 
		u.get(data.get("Url")); // toma el valor Url del data provider
		u.hardWait(3); // tiempo en segundos
		pc.PrivatePortal(); 
		pc.IniciarSesion(user, password);
		pc.ClicPortalCometa();
		pc.ClicBotonDirectos();

		
		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("Seleccionar el botÃ³n \"Reportes\" del menÃº");
		pc.ClicBotonReportesDirecto();
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
	/*Se desactivo esta pantalla
	 * 	addStep("Seleccionar el botÃ³n \"Reportes\" del menÃº");
	
		pc.ClicBotonInicio();
		pc.ClicBotonDirectos();
		pc.ClicBotonGraficasDirecto();
		 */
		/*
		 *                                             PASO 4
		 ******************************************************************************************/
		addStep("Seleccionar el botÃ³n \"Reportes\" del menÃº");
		pc.ClicBotonInicio();
		pc.ClicBotonDirectos();
		boolean validarExistenciaBtns = pc.ClicBotonComprobantesDeCompra();
		if(validarExistenciaBtns)
			testCase.addTextEvidenceCurrentStep("Los botones existen");
		else
			testCase.addTextEvidenceCurrentStep("Los botones no existen");
		assertTrue(validarExistenciaBtns, "Se muestra la informaciÃ³n esperada");
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
		return "Validar que el menÃº del mÃ³dulo nos lleve a los diferentes procesos habilitados para el mismo";
	}

	@Override
	public String setTestDesigner() {
		return"Yair Lizarraga";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_04_VerificarAccesosModuloDirectos_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}