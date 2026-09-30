package WEB.cometa.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**Desc:Verificar que la informaciÃ³n almacenada tenga como mÃ¡ximo dos aÃ±os de antigÃ¼edad

* MTC-FT-11- E1 - Verificar el apoyo del sistema para reestablecer contraseÃ±a
* @author Yair Lizarraga
* @date Junio 2024
*/
public class ATC_FT_11_VerificarApoyoSistemaRestablecerContrasena extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_11_VerificarApoyoSistemaRestablecerContrasena_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		String password = data.get("password");
		String newpassword = data.get("newpassword");
		String passwordConfirmation = data.get("passwordConfirmation");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometa pc = new PortalCometa(u, testCase); // crea objeto de locator
		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Ingresar a la liga del portal web"); 
		u.get(data.get("Url")); // toma el valor Url del data provider
		u.hardWait(3); // tiempo en segundos
		pc.PrivatePortal(); 
		
		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("Seleccionar la opciÃ³n de \"reestablecer contraseÃ±a\" en la pantalla de login");
		pc.clicCambiarContrasena();
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Ingresar una contrasena diferente a la utilizada anteriormente");
		boolean validarCambiarContrasena = pc.cambiarContrasena(user, password, newpassword, passwordConfirmation);
		if(validarCambiarContrasena)
			testCase.addTextEvidenceCurrentStep("El Menu es correcto");
		else
			testCase.addTextEvidenceCurrentStep("El Menu no es correcto");
		assertTrue(validarCambiarContrasena, "El Menu es correcto");
	}
		


	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		return;
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Contar con credenciales de administrador para el inicio de sesiÃ³n";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que el portal nos guie paso a paso durante el proceso de cambio de la contraseÃ±a del usuario donde nos indique lo siguiente:\r\n"
				+ "\"La contraseÃ±a debe tener al menos 12 caracteres de longitud combinando caracteres nÃºmericos y alfabÃ©ticos, ademÃ¡s que no debe coincidir con sus 5 Ãºltimas contraseÃ±as\".";
	}

	@Override
	public String setTestDesigner() {
		return"Yair Lizaraga";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_11_VerificarApoyoSistemaRestablecerContrasena_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}