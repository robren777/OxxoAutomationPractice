package WEB.cometa.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**Desc: Validar que al seleccionar el MÃ³dulo Directos se nos lleve a la pantalla principal con la informaciÃ³n habilitada para su rol y su respectivo menÃº de opciones
* MTC-FT-03-E1 - Validar pantalla principal de MÃ³dulo Directos
* @author Yair Lizarraga
* @date Junio 2024
*/
public class ATC_FT_03_AccesosPantallasPrincipalModuloDirectos extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_03_AccesosPantallasPrincipalModuloDirectos_test (HashMap<String, String> data) throws Exception {
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
		addStep("Verificar la existencia de los botones para acceder a las diferentes pantallas del mÃ³dulo ");
		pc.ClicBotonDirectos();
		boolean validarExistenciaBtns = pc.ClicBotonRecepcionDeTransferencias();
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
		return "Contar con credenciales validas para el perfil de Directos";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que al seleccionar el Modulo Directos se nos lleve a la pantalla principal \"\r\n"
				+ "				+ \"con la informacion habilitada para su rol y su respectivo menu de opciones";
	}

	@Override
	public String setTestDesigner() {
		return"Yair Lizarraga";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_03_AccesosPantallasPrincipalModuloDirectos_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
