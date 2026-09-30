package WEB.AccessControl;


import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import org.testng.annotations.Test;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import WEB.AccessControl.om.*;
import utils.password.PasswordUtil;

/**
 * DESCRIPCION : Validar que el script desarrollado por una herramienta(IA)
 * funcione, permitiendo que el usuario pueda cambiar su contraseña y volver a
 * iniciar sesion correctamente.
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
public class ATC_FT_006_CambioContrasenaAccessControlIA extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_CambioContrasenaAccessControlIA_test(HashMap<String, String> data) throws Exception {
		// 1) Variables del caso
		String vpnStatus = data.get("vpnStatus"); // ejemplo: connected
		String portalUrl = data.get("portalUrl"); // ejemplo: www.portal.com
		String usuario = data.get("usuario"); // ejemplo: usuario123
		String contrasenaActual = PasswordUtil.decryptPassword(data.get("contrasenaActual"));
		String nuevaContrasena = PasswordUtil.decryptPassword(data.get("nuevaContrasena")); // ejemplo: nuevaPass123
		String confirmacion = PasswordUtil.decryptPassword(data.get("confirmacion"));

		// 2) Utileras
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		PortalLoginPage portalLogin = new PortalLoginPage(u, testCase);
		PortalHomePage portalHomePage = new PortalHomePage(u, testCase);

		// 3) Pasos
		/*********************************************************************************
		 * Paso 1
		 **********************************************************************************/
		addStep("DEBEMOS ESTAR DESCONECTADOS DE LA VPN");
		assertTrue(vpnStatus.equals("connected"), "La VPN no esta conectada");
		testCase.addTextEvidenceCurrentStep("Asegurarse de estar desconectado de la VPN.");

		/*********************************************************************************
		 * Paso 2
		 **********************************************************************************/
		addStep("ABRIR NUESTRO NAVEGADOR");
		u.getDriver().manage().window().maximize();
		testCase.addScreenShotCurrentStep(u, "navegador_abierto");

		/*********************************************************************************
		 * Paso 3
		 **********************************************************************************/
		addStep("INGRESAMOS EL LINK DE LA PAGINA DEL PORTAL");
		portalLogin.abrirPagina(portalUrl);


		/*********************************************************************************
		 * Paso 4
		 **********************************************************************************/
		addStep("VERIFICAR QUE CARGUE LA PAGINA Y NOS APAREZCA EL LOGIN PARA PODER INGRESAR");
		assertTrue(portalLogin.verificarLoginCargado());

		/*********************************************************************************
		 * Paso 5
		 **********************************************************************************/
		addStep("OPRIMIR EL BOTON DE CAMBIAR CONTRASEÑA Y PONER NUESTRA CONTRASEÑA NUEVA");
		portalLogin.cambiarContrasena(nuevaContrasena, usuario, contrasenaActual, confirmacion);
		assertTrue(portalLogin.verificarLoginCargado(), "No se regreso al login despues de cambiar la contraseña");

		/*********************************************************************************
		 * Paso 6
		 **********************************************************************************/
		addStep("INGRESAMOS EL USUARIO Y LA NUEVA CONTRASEÑA");
		portalLogin.ingresarCredenciales(usuario, nuevaContrasena);


		/*********************************************************************************
		 * Paso
		 **********************************************************************************/
		addStep("SE CARGA LA PAGINA DE MANERA CORRECTA Y NOS APARECEN LAS DIFERENTES OPCIONES QUE TIENE EL PORTAL");
		assertTrue(portalHomePage.existePaginaPrincipal(), "No se visualiza la pagina principal del portal");
		testCase.addScreenShotCurrentStep(u, "pagina_principal");
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "TENER ACCESO AL INTERNET, USUARIO Y CONTRASEÑA DE LA VPN,\r\n"
				+ " * USUARIO Y CONTRASEÑA DEL PORTAL";
	}

	@Override
	public String setTestDescription() {
		return "Validar que el script desarrollado por una herramienta(IA)\r\n"
				+ " funcione, permitiendo que el usuario pueda cambiar su contraseña y volver a\r\n"
				+ " iniciar sesion correctamente.";
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