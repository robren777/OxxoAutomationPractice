package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.CometaNuevaIdentidadVisualPage;
import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.rutaLiquidacionPage;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: Verifica que el logotipo Plataforma COMETA sea consistente en las pantallas accesibles del modulo CEDIS para el perfil CEDIS.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_153 CA02 - Verificar que el logotipo Plataforma COMETA es consistente en todas las pantallas del modulo CEDIS - perfil CEDIS
* 
*PREREQUISITOS:
*1. Logotipo oficial Plataforma COMETA implementado. Perfil CEDIS con acceso al modulo CEDIS
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Usuario CEDIS 6666666 con acceso al modulo CEDIS
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Michelle Ceja
* @date Julio 2026
*/
public class ATC_FT_153_VerificarLogoConsistenteModuloCedisCEDIS extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_153_VerificarLogoConsistenteModuloCedisCEDIS_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("HU308186 - Identidad visual Plataforma COMETA");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		CometaNuevaIdentidadVisualPage identidadVisualPage = new CometaNuevaIdentidadVisualPage(u, testCase);
		rutaLiquidacionPage rutaPage = new rutaLiquidacionPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "CEDIS";

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
		assertTrue(user != null && user.equals("6666666"), "El usuario del data provider no corresponde al perfil CEDIS esperado");
		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);
		assertTrue(password != null && !password.trim().isEmpty(), "No se obtuvo password valido para el usuario: " + user);
		boolean resultStep2 = pc4.IniciarSesionNoToken(user, password, true);
		assertTrue(resultStep2, "No se pudo iniciar sesion con el usuario CEDIS: " + user);

		/*
		 *                                             PASO 3
		 ***************************************************************************/
		addStep("Dar clic en la opcion Portal COMETA");
		boolean resultStep3 = pc4.seleccionarPortalCometaQA(true);
		assertTrue(resultStep3, "No se visualizo o no se pudo seleccionar Portal COMETA");

		/*
		 *                                             PASO 4
		 ***************************************************************************/
		addStep("Dar clic en el modulo CEDIS y verificar el logotipo en la pantalla principal del modulo");
		boolean resultStep4 = pc4.clicBotonCometaCedis(true);
		assertTrue(resultStep4, "No se pudo ingresar al modulo CEDIS");
		boolean resultStep4Logo = identidadVisualPage.validarLogoNuevaIdentidadVisualVisible(true);
		assertTrue(resultStep4Logo, "El logotipo Plataforma COMETA no se visualizo en la pantalla principal del modulo CEDIS");

		/*
		 *                                             PASO 5
		 ***************************************************************************/
		addStep("Dar clic en Recepcion y Devoluciones y verificar el logotipo en la solapa superior");
		boolean resultStep5 = pc4.ClicBotonRecepcionCedis(true);
		assertTrue(resultStep5, "No se pudo ingresar a Recepcion de Transferencias y Devoluciones");
		boolean resultStep5Logo = identidadVisualPage.validarLogoNuevaIdentidadVisualVisible(true);
		assertTrue(resultStep5Logo, "El logotipo Plataforma COMETA no se visualizo en Recepcion de Transferencias y Devoluciones");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Dar clic en Liquidacion de Ruta y verificar el logotipo en la solapa superior");
		boolean resultStep6 = pc4.opcLiquidacionRuta();
		assertTrue(resultStep6, "No se pudo seleccionar la opcion Liquidacion de Ruta");
		boolean resultStep6Logo = identidadVisualPage.validarLogoNuevaIdentidadVisualVisible(true);
		assertTrue(resultStep6Logo, "El logotipo Plataforma COMETA no se visualizo en Liquidacion de Ruta");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Dar clic en Devoluciones Especiales y verificar el logotipo en la solapa superior");
		boolean resultStep7 = rutaPage.clicBotonDevolucionesEspecialesLiquidacionRuta();
		assertTrue(resultStep7, "No se pudo hacer clic en Devoluciones Especiales desde Liquidacion de Ruta");
		boolean resultStep7Pantalla = rutaPage.scDevolucionesEspecialesCedis(true);
		assertTrue(resultStep7Pantalla, "No se mostro la pantalla Devoluciones Especiales de CEDIS");
		boolean resultStep7Logo = identidadVisualPage.validarLogoNuevaIdentidadVisualVisible(true);
		assertTrue(resultStep7Logo, "El logotipo Plataforma COMETA no se visualizo en Devoluciones Especiales");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Navegar a FAQS CEDIS y verificar el logotipo en la solapa superior");
		boolean resultStep8 = identidadVisualPage.clicFaqsCedis(true);
		assertTrue(resultStep8, "No se pudo ingresar a FAQS CEDIS desde la navegacion superior");
		boolean resultStep8Logo = identidadVisualPage.validarLogoNuevaIdentidadVisualVisible(true);
		assertTrue(resultStep8Logo, "El logotipo Plataforma COMETA no se visualizo en FAQS CEDIS");
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
		return "*1. Logotipo oficial Plataforma COMETA implementado. Perfil CEDIS con acceso al modulo CEDIS      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Verifica el MTC_FT_153 CA02: el logotipo Plataforma COMETA se muestra de forma consistente en las pantallas accesibles del modulo CEDIS para el perfil CEDIS.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_153_VerificarLogoConsistenteModuloCedisCEDIS_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}