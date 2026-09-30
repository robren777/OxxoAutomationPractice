package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.CometaNuevaIdentidadVisualPage;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: Verifica que la solapa superior del portal muestre la nueva identidad visual Plataforma COMETA con logotipo oficial para el perfil OXXO_OS.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_146 CA01 - Verificar que la solapa superior muestra la nueva identidad visual Plataforma COMETA con logotipo oficial - perfil OXXO_OS
* 
*PREREQUISITOS:
*1. Logotipo oficial Plataforma COMETA implementado en el frontend Angular del portal COMETA. Perfil OXXO_OS con acceso a Portal COMETA
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Usuario OXXO_OS 3333333 con acceso al Portal COMETA
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Michelle Ceja
* @date Julio 2026
*/
public class ATC_FT_146_VerificarSolapaSuperiorNuevaIdentidadVisualOXXOOS extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_146_VerificarSolapaSuperiorNuevaIdentidadVisualOXXOOS_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("HU308186 - Identidad visual Plataforma COMETA");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		CometaNuevaIdentidadVisualPage identidadVisualPage = new CometaNuevaIdentidadVisualPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "OXXO_OS";

		/*
		 *                                             PASO 1
		 ***************************************************************************/
		addStep("Ingresar la URL del portal en el navegador");
		boolean resultStep1 = pc4.IngresarURL(url, true);
		assertTrue(resultStep1, "No se pudo ingresar a la URL de Portal COMETA");
		pc4.PrivatePortal();

		/*
		 *                                             PASO 2
		 ***************************************************************************/
		addStep("Ingresar las credenciales correspondientes al perfil de Cometa para el inicio de sesion");
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
		System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);
		assertTrue(user != null && user.equals("3333333"), "El usuario del data provider no corresponde al perfil OXXO_OS esperado");
		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);
		assertTrue(password != null && !password.trim().isEmpty(), "No se obtuvo password valido para el usuario: " + user);
		boolean resultStep2 = pc4.IniciarSesionNoToken(user, password, true);
		assertTrue(resultStep2, "No se pudo iniciar sesion con el usuario OXXO_OS: " + user);

		/*
		 *                                             PASO 3
		 ***************************************************************************/
		addStep("Dar clic en la opcion Portal COMETA");
		boolean resultStep3 = pc4.seleccionarPortalCometaQA(true);
		assertTrue(resultStep3, "No se visualizo o no se pudo seleccionar Portal COMETA");

		/*
		 *                                             PASO 4
		 ***************************************************************************/
		addStep("Localizar el logotipo en la solapa superior de la pantalla principal del portal COMETA");
		boolean resultStep4 = identidadVisualPage.validarLogoNuevaIdentidadVisualVisible(true);
		assertTrue(resultStep4, "No se localizo visible el logotipo Plataforma COMETA en la solapa superior");

		/*
		 *                                             PASO 5
		 ***************************************************************************/
		addStep("Verificar que la solapa superior muestra la nueva identidad visual Plataforma COMETA");
		boolean resultStep5 = identidadVisualPage.validarTextoOImagenPlataformaCometa(true);
		assertTrue(resultStep5, "La solapa superior no mostro la identidad visual Plataforma COMETA esperada");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Validar que el logotipo Plataforma COMETA renderiza sin deformacion ni recorte aparente");
		boolean resultStep6 = identidadVisualPage.validarProporcionLogoPlataformaCometa(true);
		assertTrue(resultStep6, "El logotipo Plataforma COMETA no conserva proporciones visuales consistentes con la referencia oficial");
	}

	@AfterMethod
	public void afterMethod() {
		if (u != null) {
			u.quit();
		}

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "*1. Logotipo oficial Plataforma COMETA implementado en el frontend Angular del portal COMETA. Perfil OXXO_OS con acceso a Portal COMETA      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Verifica el MTC_FT_146 CA01: la solapa superior del portal COMETA muestra la nueva identidad visual Plataforma COMETA con logotipo oficial para el perfil OXXO_OS.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_146_VerificarSolapaSuperiorNuevaIdentidadVisualOXXOOS_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}