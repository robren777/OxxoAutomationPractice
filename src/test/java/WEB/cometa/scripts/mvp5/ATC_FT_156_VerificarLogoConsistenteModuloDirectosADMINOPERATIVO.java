package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.CometaNuevaIdentidadVisualPage;
import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: Verifica que el logotipo Plataforma COMETA sea consistente en las pantallas accesibles del modulo Directos para el perfil ADMIN_OPERATIVO.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_156 CA02 - Verificar que el logotipo Plataforma COMETA es consistente en todas las pantallas del modulo Directos -perfil ADMIN_OPERATIVO
* 
*PREREQUISITOS:
*1. Logotipo oficial Plataforma COMETA implementado. Perfil ADMIN_OPERATIVO con acceso al modulo Directos
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Usuario ADMIN_OPERATIVO 1111111 con acceso al modulo Directos
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Michelle Ceja
* @date Julio 2026
*/
public class ATC_FT_156_VerificarLogoConsistenteModuloDirectosADMINOPERATIVO extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_156_VerificarLogoConsistenteModuloDirectosADMINOPERATIVO_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("HU308186 - Identidad visual Plataforma COMETA");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		CometaNuevaIdentidadVisualPage identidadVisualPage = new CometaNuevaIdentidadVisualPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "ADMIN_OPERATIVO";

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
		assertTrue(user != null && user.equals("1111111"), "El usuario del data provider no corresponde al perfil ADMIN_OPERATIVO esperado");
		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);
		assertTrue(password != null && !password.trim().isEmpty(), "No se obtuvo password valido para el usuario: " + user);
		boolean resultStep2 = pc4.IniciarSesionNoToken(user, password, true);
		assertTrue(resultStep2, "No se pudo iniciar sesion con el usuario ADMIN_OPERATIVO: " + user);

		/*
		 *                                             PASO 3
		 ***************************************************************************/
		addStep("Dar clic en la opcion Portal COMETA y verificar el logotipo en la pantalla principal");
		boolean resultStep3 = pc4.seleccionarPortalCometaQA(true);
		assertTrue(resultStep3, "No se visualizo o no se pudo seleccionar Portal COMETA");
		boolean resultStep3Logo = identidadVisualPage.validarLogoNuevaIdentidadVisualVisible(true);
		assertTrue(resultStep3Logo, "El logotipo Plataforma COMETA no se visualizo en la pantalla principal del portal");

		/*
		 *                                             PASO 4
		 ***************************************************************************/
		addStep("Dar clic en Directos y verificar el logotipo en la solapa superior del modulo");
		boolean resultStep4 = pc4.clicBotonCometaDirectos(true);
		assertTrue(resultStep4, "No se pudo ingresar al modulo Directos");
		boolean resultStep4Logo = identidadVisualPage.validarLogoNuevaIdentidadVisualVisible(true);
		assertTrue(resultStep4Logo, "El logotipo Plataforma COMETA no se visualizo en el modulo Directos");

		/*
		 *                                             PASO 5
		 ***************************************************************************/
		addStep("Dar clic en Comprobantes de Compra y verificar el logotipo en la solapa superior de la pantalla del modulo");
		boolean resultStep5Inicio = pc4.opcInicioExterno(true);
		assertTrue(resultStep5Inicio, "No se pudo regresar al menu principal con Inicio Externo antes de ingresar a Comprobantes de Compra");
		boolean resultStep5Directos = pc4.clicBotonCometaDirectos(true);
		assertTrue(resultStep5Directos, "No se pudo ingresar al modulo Directos antes de seleccionar Comprobantes de Compra");
		boolean resultStep5 = pc4.ClicBotonComprobantesDeCompra(true);
		assertTrue(resultStep5, "No se pudo ingresar a Comprobantes de Compra");
		boolean resultStep5Logo = identidadVisualPage.validarLogoNuevaIdentidadVisualVisible(true);
		assertTrue(resultStep5Logo, "El logotipo Plataforma COMETA no se visualizo en Comprobantes de Compra");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Regresar y dar clic en Devoluciones. Verificar el logotipo en la solapa superior de la pantalla Devoluciones");
		boolean resultStep6Inicio = pc4.opcInicioExterno(true);
		assertTrue(resultStep6Inicio, "No se pudo regresar al menu principal con Inicio Externo antes de ingresar a Devoluciones");
		boolean resultStep6Directos = pc4.clicBotonCometaDirectos(true);
		assertTrue(resultStep6Directos, "No se pudo ingresar al modulo Directos antes de seleccionar Devoluciones");
		boolean resultStep6 = pc4.selectDevolucionesSE();
		assertTrue(resultStep6, "No se pudo ingresar a Devoluciones");
		boolean resultStep6Logo = identidadVisualPage.validarLogoNuevaIdentidadVisualVisible(true);
		assertTrue(resultStep6Logo, "El logotipo Plataforma COMETA no se visualizo en Devoluciones");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Regresar y dar clic en Cambios Fisicos. Verificar el logotipo en la solapa superior");
		boolean resultStep7Inicio = pc4.opcInicioExterno(true);
		assertTrue(resultStep7Inicio, "No se pudo regresar al menu principal con Inicio Externo antes de ingresar a Cambios Fisicos");
		boolean resultStep7Directos = pc4.clicBotonCometaDirectos(true);
		assertTrue(resultStep7Directos, "No se pudo ingresar al modulo Directos antes de seleccionar Cambios Fisicos");
		boolean resultStep7 = pc4.selectCambiosFisicosSE();
		assertTrue(resultStep7, "No se pudo ingresar a Cambios Fisicos");
		boolean resultStep7Logo = identidadVisualPage.validarLogoNuevaIdentidadVisualVisible(true);
		assertTrue(resultStep7Logo, "El logotipo Plataforma COMETA no se visualizo en Cambios Fisicos");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Regresar y dar clic en FAQS DIRECTOS Verificar el logotipo en la solapa superior");
		boolean resultStep8Inicio = pc4.opcInicioExterno(true);
		assertTrue(resultStep8Inicio, "No se pudo regresar al menu principal con Inicio Externo antes de ingresar a FAQS Directos");
		boolean resultStep8 = identidadVisualPage.clicFaqsDirectos(true);
		assertTrue(resultStep8, "No se pudo ingresar a FAQS Directos desde la navegacion superior");
		boolean resultStep8Logo = identidadVisualPage.validarLogoNuevaIdentidadVisualVisible(true);
		assertTrue(resultStep8Logo, "El logotipo Plataforma COMETA no se visualizo en FAQS Directos");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Regresar y dar clic en Centro de descargas usando el menu. Verificar el logotipo en la solapa superior");
		boolean resultStep9Inicio = pc4.opcInicioExterno(true);
		assertTrue(resultStep9Inicio, "No se pudo regresar al menu principal con Inicio Externo desde FAQS Directos");
		boolean resultStep9 = pc4.ClicBotonCentroDescargaMenu(true);
		assertTrue(resultStep9, "No se pudo ingresar a Centro de descarga desde el menu");
		boolean resultStep9Logo = identidadVisualPage.validarLogoNuevaIdentidadVisualVisible(true);
		assertTrue(resultStep9Logo, "El logotipo Plataforma COMETA no se visualizo en Centro de descarga");
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
		return "*1. Logotipo oficial Plataforma COMETA implementado. Perfil ADMIN_OPERATIVO con acceso al modulo Directos      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Verifica el MTC_FT_156 CA02: el logotipo Plataforma COMETA se muestra de forma consistente en las pantallas accesibles del modulo Directos para el perfil ADMIN_OPERATIVO.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_156_VerificarLogoConsistenteModuloDirectosADMINOPERATIVO_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}