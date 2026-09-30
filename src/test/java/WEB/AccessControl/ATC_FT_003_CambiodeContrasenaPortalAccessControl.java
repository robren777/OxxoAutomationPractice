package WEB.AccessControl;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import WEB.util.AccessControlPortalLogin;
import WEB.util.AuxMethods;
import modelo.BaseExecution;
import utils.selenium.CustomChromeTest;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCION : Validar que el usuario puede cambiar su contraseña y volver a
 * iniciar sesión correctamente.
 * 
 * MTC-FT-03-CAMBIO DE CONTRASEÑA DE MANERA CORRECTA
 * 
 * PREREQUISITOS: TENER ACCESO AL INTERNET, USUARIO Y CONTRASEÑA DE LA VPN,
 * USUARIO Y CONTRASEÑA DEL PORTAL
 * 
 * 
 * INSUMOS: Accesos al portal
 * 
 * @Author Emmanuel Gomez
 * @Date 04/Diciembre/2025
 */
public class ATC_FT_003_CambiodeContrasenaPortalAccessControl extends BaseExecution {

	private SeleniumUtil u;
	private AccessControlPortalLogin AC;
	private AuxMethods aux;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_CambiodeContrasenaPortalAccessControl_Test(HashMap<String, String> data) throws Exception {

		testCase.setProject_Name("Evolución Tecnológica de Portales");

		String url = data.get("URL");
		String usuario = data.get("email");
		String passActual = data.get("password");
		String passNueva = data.get("passwordN");

		// -------------------------------------------------------
		// 1) Crear perfil temporal limpio para evitar 403
		// -------------------------------------------------------
		Path tempProfile = Files.createTempDirectory("chromeTempProfile");
		CustomChromeTest chromeTest = new CustomChromeTest();
		WebDriver driver = chromeTest.initDriverWithProfile(true, tempProfile.toString());
		u = new SeleniumUtil(driver);

		// -------------------------------------------------------
		// 2) Iniciar helpers
		// -------------------------------------------------------
		aux = new AuxMethods(u, testCase);
		AC = new AccessControlPortalLogin(u, testCase);

		addStep("Abrir portal");
		u.get(url);
		testCase.addScreenShotCurrentStep(u, "Portal cargado");
		aux.logP("1. Se abrió el portal: " + url);

		// -------------------------------------------------------
		// 3) Ir a cambiar contraseña
		// -------------------------------------------------------
		addStep("Ir a la pantalla de cambio de contraseña");

		boolean cambioPantalla = AC.irACambiarContrasena();
		assertTrue(cambioPantalla, "No se pudo abrir la pantalla de cambio de contraseña");
		aux.logP("2. Se abrió el formulario de cambio de contraseña");

		// -------------------------------------------------------
		// 4) Llenar formulario de cambio de contraseña
		// -------------------------------------------------------
		addStep("Llenar formulario de cambio de contraseña");

		boolean cambiado = AC.cambiarContrasena(usuario, passActual, passNueva);

		assertFalse(cambiado, "No se pudo completar el cambio de contraseña");
		aux.logP("3. Contraseña cambiada correctamente");

		// -------------------------------------------------------
		// 5) Esperar redirección al login normal
		// -------------------------------------------------------
		addStep("Esperar redirección al login normal");
		AC.esperarLoginYTomarScreenshot();
		aux.logP("4. Redirección correcta al login");

		// -------------------------------------------------------
		// 6) Iniciar sesión con la nueva contraseña
		// -------------------------------------------------------
		addStep("Intentar login con la nueva contraseña");
		AC.loginAccessControl1(usuario, passNueva);
		testCase.addScreenShotCurrentStep(u, "Login exitoso con nueva contraseña");
		aux.logP("5. Login exitoso con nueva contraseña");

		// 8) Cerrar Sesion
		addStep("Cerrar sesión del portal");
		try {
			AC.logoutAccessControl();
			testCase.addScreenShotCurrentStep(u, "Cerrar Sesion Exitoso");
			aux.logP("7. Sesión cerrada correctamente.");
		} catch (Exception e) {
			aux.logP("No se pudo cerrar sesión: " + e.getMessage());
		}

		// -------------------------------------------------------
		// 8) Cerrar navegador
		// -------------------------------------------------------
		testCase.addTextEvidenceCurrentStep("Finalizar prueba y cerrar navegador");
		u.close();
		tempProfile.toFile().deleteOnExit();
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "1. Acceso a internet\n" + "2. Usuario y contraseña actual del portal\n" + "3. Contraseña nueva válida\n"
				+ "4. VPN activa\n" + "5. Navegador cerrado antes de iniciar";
	}

	@Override
	public String setTestDescription() {
		return "Validar que el usuario puede cambiar su contraseña y volver a iniciar sesión correctamente.";
	}

	@Override
	public String setTestDesigner() {
		return "Emmanuel Gomez";
	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}