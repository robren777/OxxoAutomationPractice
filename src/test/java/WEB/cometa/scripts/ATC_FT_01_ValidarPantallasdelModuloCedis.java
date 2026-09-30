package WEB.cometa.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**Desc: Validar que al seleccionar el Modulo de Cedis se nos lleve a la pantalla principal con la informacion habilitada para su rol y su respectivo menu de opciones
* MTC-FT-01-E1 -  Validar pantallas del Modulo de Cedis
* @author Yair Lizarraga
* @date Junio 2024
*/
public class ATC_FT_01_ValidarPantallasdelModuloCedis extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_01_ValidarPantallasdelModuloCedis_test (HashMap<String, String> data) throws Exception {
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
		addStep("Ingresar las credenciales correspondientes al perfil de Cedis para el inicio de sesion");
		pc.IniciarSesion(user, password);
		u.hardWait(5);
		pc.ClicPortalCometa();
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Verificar la existencia de los botones para acceder a las diferentes pantallas del modulo botones");		
		boolean validarExistenciaBtns = pc.ValidarExistenciaBotonesCedis();
		if(validarExistenciaBtns)
			testCase.addTextEvidenceCurrentStep("Los botones existen");
		else
			testCase.addTextEvidenceCurrentStep("Los botones no existen");
		assertTrue(validarExistenciaBtns, "Se muestra la informacion esperada");
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		return;
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Contar con credenciales validas para el perfil de Cedis";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que al seleccionar el Modulo de Cedis se nos lleve a la pantalla principal con la informacion habilitada para su rol y su respectivo menu de opciones\r\n"
				+ "";
	}

	@Override
	public String setTestDesigner() {
		return"Yair Lizarraga";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_01_ValidarPantallasdelModuloCedis_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
