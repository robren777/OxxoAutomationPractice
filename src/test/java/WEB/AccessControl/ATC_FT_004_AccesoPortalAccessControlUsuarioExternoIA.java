package WEB.AccessControl;

import static org.testng.Assert.assertTrue;
import java.util.HashMap;
import org.testng.annotations.Test;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.password.PasswordUtil;
import WEB.AccessControl.om.PortalLoginPage;
import WEB.AccessControl.om.PortalHomePage;

/**
 * DESCRIPCION : Validar que el script desarrollado por una herramienta(IA)
 * funcione, permitiendo que un usuario externo puede iniciar sesion y cerrar
 * sesion correctamente en Access Control
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
public class ATC_FT_004_AccesoPortalAccessControlUsuarioExternoIA extends BaseExecution {

	private SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_AccesoPortalAccessControlUsuarioExternoIA_test(HashMap<String, String> data)
			throws Exception {

		testCase.setProject_Name("Evolucion Tecnologica de Portales");
		// 1) Variables del caso
		String vpnStatus = data.get("vpnStatus"); // ejemplo: connected
		String portalUrl = data.get("URL"); // ejemplo: http://portal.example.com
		String username = data.get("email"); // ejemplo: usuarioQA
		String password = PasswordUtil.decryptPassword(data.get("password")); // ejemplo: passQA

		// 2) Inicializacion de utileras
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalLoginPage portalLoginPage = new PortalLoginPage(u, testCase);
		PortalHomePage portalHomePage = new PortalHomePage(u, testCase);

		// 3) Pasos
		/*********************************************************************************
		 * Paso 1.0
		 **********************************************************************************/
		addStep("CONECTARNOS A LA VPN DE OXXO(DEBEMOS TENER LA VPN ACTIVA)");
		assertTrue(vpnStatus.equals("connected"), "La VPN no esta conectada");
		testCase.addTextEvidenceCurrentStep("Estado de la VPN: " + vpnStatus);

		/*********************************************************************************
		 * Paso 3.0
		 **********************************************************************************/
		addStep("INGRESAMOS EL LINK DE LA PAGINA DEL PORTAL");
		u.get(portalUrl);
		testCase.addTextEvidenceCurrentStep("URL del portal: " + portalUrl);

		/*********************************************************************************
		 * Paso 4.0
		 **********************************************************************************/
		addStep("VERIFICAR QUE CARGUE LA PAGINA Y NOS APAREZCA EL LOGIN PARA PODER INGRESAR");
		assertTrue(portalLoginPage.verificarLoginCargado(), "No se visualiza el formulario de login");
		testCase.addScreenShotCurrentStep(u, "pagina_login");

		/*********************************************************************************
		 * Paso 5.0
		 **********************************************************************************/
		addStep("INGRESAR CON NUESTRO USUARIO Y CONTRASEÑA DE QA, LE DAMOS AL BOTON DE INICIAR SESION");
		portalLoginPage.ingresarCredenciales(username, password);

		/*********************************************************************************
		 * Paso 6.0
		 **********************************************************************************/
		addStep("LA PAGINA CARGA Y SE ENTRA AL PORTAL");
		assertTrue(portalHomePage.existePaginaPrincipal(), "No se visualiza la pagina principal del portal");
		testCase.addScreenShotCurrentStep(u, "pagina_principal");
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "1. TENER ACCESO A INTERNET\n" + "2. USUARIO Y CONTRASEÑA DE LA VPN\n"
				+ "3. USUARIO Y CONTRASEÑA DEL PORTAL\n";
	}

	@Override
	public String setTestDescription() {
		return "Validar que el script desarrollado por una herramienta(IA)\r\n"
				+ " funcione, permitiendo que un usuario externo puede iniciar sesion y cerrar\r\n"
				+ " sesion correctamente en Access Control";
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