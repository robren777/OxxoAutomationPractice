package WEB.AccessControl;

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
 * DESCRIPCION : Validar que un usuario externo puede iniciar sesión y cerrar
 * sesión correctamente en Access Control.
 * 
 * MTC-FT-01-ACCESO AL PORTAL DE ACCESS CONTROL CON USUARIO EXTERNO
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
public class ATC_FT_001_AccesoPortalAccessControlUsuarioExterno extends BaseExecution {

	private SeleniumUtil u;
	private AccessControlPortalLogin AC;
	private AuxMethods aux;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_AccesoPortalAccessControlUsuarioExterno_Test(HashMap<String, String> data) throws Exception {

		testCase.setProject_Name("Evolución Tecnológica de Portales");

		String url = data.get("URL");
		String user = data.get("email");
		String pass = data.get("password");

		// ---- 1. Crear perfil temporal limpio ----
		Path tempProfile = Files.createTempDirectory("chromeTempProfile");

		// ---- 2. Inicializar Chrome con perfil temporal ----
		CustomChromeTest chromeTest = new CustomChromeTest();
		WebDriver realDriver = chromeTest.initDriverWithProfile(true, tempProfile.toString());

		// ---- 3. Configurar SeleniumUtil ----
		u = new SeleniumUtil(realDriver);

		// ---- 4. Abrir la URL ----
		addStep("La URL cargo de manera correcta");
		u.get(url);

		// ---- Inicializar auxiliares ----
		aux = new AuxMethods(u, testCase);
		AC = new AccessControlPortalLogin(u, testCase);

		aux.logP("1. Se abrió el portal: " + url);
		testCase.addScreenShotCurrentStep(u, "Portal cargado");

		// ---- 5. Login ----
		addStep("Ingresar usuario y contraseña");
		AC.loginAccessControl1(user, pass);
		testCase.addTextEvidenceCurrentStep("Validación correcta: Se ingresaron los datos correctos");

		// ---- 6. Validar página principal ----
		addStep("Validar que la página principal se cargó correctamente");
		assertTrue(AC.validarAccesoPortal(), "No se pudo validar el acceso al portal");
		testCase.addScreenShotCurrentStep(u, "Pagina principal cargada");
		testCase.addTextEvidenceCurrentStep("Validación correcta: el usuario ingresó al portal");

		// ---- 7. (OPCIONAL) Cerrar sesión ----
		addStep("Cerrar sesión del portal");
		try {
			AC.logoutAccessControl();
			aux.logP("7. Sesión cerrada correctamente.");
			testCase.addScreenShotCurrentStep(u, "Cerrar Sesion Exitoso");
		} catch (Exception e) {
			aux.logP("No se pudo cerrar sesión: " + e.getMessage());
		}

		// ---- 9. Finalizar ----
		testCase.addTextEvidenceCurrentStep("Finalizar prueba y cerrar navegador");
		u.close();
		tempProfile.toFile().deleteOnExit();
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "1. TENER ACCESO A INTERNET\n" + "2. USUARIO Y CONTRASEÑA DE LA VPN\n"
				+ "3. USUARIO Y CONTRASEÑA DEL PORTAL\n" + "4. TENER ACTIVA LA VPN\n"
				+ "5. NO TENER NINGUNA PESTAÑA DEL NAVEGADOR ABIERTO";
	}

	@Override
	public String setTestDescription() {
		return "Validar que un usuario externo puede iniciar sesión y cerrar sesión correctamente en Access Control.";
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