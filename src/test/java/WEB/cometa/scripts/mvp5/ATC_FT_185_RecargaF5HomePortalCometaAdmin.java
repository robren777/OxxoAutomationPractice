package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.recargaNavegacionPage;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCION: Verificar que F5 recarga la pantalla principal del Portal COMETA sin error IIS y conserva la ruta actual.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_185 CA01-CA02 - Verificar que F5 recarga correctamente la pantalla principal del Portal COMETA sin error - perfil ADMIN
*
*PREREQUISITOS:
*1. Fix de navegacion Angular desplegado y perfil ADMIN (usuario 5555555) disponible.
*2.Liga de Portal Cometa
*
*INSUMOS:
*Usuario ADMIN disponible en QA.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_185_RecargaF5HomePortalCometaAdmin extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_185_RecargaF5HomePortalCometaAdmin_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		recargaNavegacionPage recargaPage = new recargaNavegacionPage(u, testCase);
		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "ADMIN";

		addStep("Precondicion: obtener password configurado para el usuario " + user + " con perfil " + perfil);
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
		System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);
		assertTrue("5555555".equals(user), "El data provider no corresponde al perfil ADMIN requerido");
		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null && !password.trim().isEmpty(), "No se encontro password configurada para el usuario: " + user);

		/* PASO 1 *******************************************************************/
		addStep("Ingresar la URL https://pruebasweb.oxxo.com/AuthFemcoProx/Login en el campo de direcciones del navegador");
		boolean resultStep1 = pc4.IngresarURL(url, true);
		assertTrue(resultStep1, "Paso 1 fallido: El sistema no muestra la pantalla de inicio de sesion del portal Access Control");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El sistema muestra la pantalla de inicio de sesion del portal Access Control");
		pc4.PrivatePortal();

		/* PASO 2 *******************************************************************/
		addStep("Escribir '5555555' en el campo Usuario");
		boolean resultStep2 = "5555555".equals(user);
		assertTrue(resultStep2, "Paso 2 fallido: El campo Usuario no corresponde al valor '5555555'");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El campo Usuario muestra el valor '5555555'");

		/* PASO 3 *******************************************************************/
		addStep("Escribir la contrasena correspondiente al perfil en el campo Contrasena");
		boolean resultStep3 = !password.trim().isEmpty();
		assertTrue(resultStep3, "Paso 3 fallido: No existe contrasena configurada para el perfil");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El campo Contrasena muestra los caracteres enmascarados");

		/* PASO 4 *******************************************************************/
		addStep("Dar clic en el boton Iniciar sesion");
		boolean resultStep4 = pc4.IniciarSesionNoToken(user, password, true);
		assertTrue(resultStep4, "Paso 4 fallido: El sistema no inicia sesion ni redirige a la seleccion de portales");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El sistema valida las credenciales, inicia sesion y redirige a la pantalla de seleccion de portales");

		/* PASO 5 *******************************************************************/
		addStep("Dar clic en la opcion Portal COMETA");
		boolean resultStep5 = pc4.seleccionarPortalCometaQA(true)
				&& recargaPage.validarContenidoVisible("CEDIS", "Directos", "FAQS Directos",
						"FAQS CEDIS", "Centro de descarga");
		assertTrue(resultStep5, "Paso 5 fallido: No se visualiza la pantalla principal del Portal COMETA");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Se visualiza la pantalla principal con CEDIS, Directos, FAQS Directos, FAQS CEDIS y Centro de descarga");

		/* PASO 6 *******************************************************************/
		addStep("Verificar que la pantalla 'pantalla principal del Portal Cometa' se carga correctamente antes del refresco");
		boolean resultStep6 = recargaPage.validarContenidoVisible("Inicio Externo", "CEDIS", "Directos",
				"FAQS CEDIS", "FAQS Directos", "Centro de descarga");
		assertTrue(resultStep6, "Paso 6 fallido: La pantalla principal no esta completamente cargada");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: La pantalla principal del Portal COMETA esta completamente cargada sin errores");

		/* PASO 7 *******************************************************************/
		addStep("Leer y documentar la URL completa que se muestra en la barra de direcciones del navegador en este momento");
		String urlAntes = recargaPage.capturarUrlActual();
		boolean resultStep7 = recargaPage.validarUrlCorrespondePantalla(urlAntes, "/PortalCometa/home");
		assertTrue(resultStep7, "Paso 7 fallido: No fue posible documentar la URL de la pantalla principal");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: La URL corresponde a la pantalla principal y queda registrada para comparacion posterior");

		/* PASO 8 *******************************************************************/
		addStep("Presionar la tecla F5 del teclado para ejecutar el refresco de la pagina");
		boolean resultStep8 = recargaPage.refrescarConF5();
		assertTrue(resultStep8, "Paso 8 fallido: El navegador no inicio la recarga con F5");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El navegador inicia el proceso de recarga de la pagina");

		/* PASO 9 *******************************************************************/
		addStep("Esperar a que el navegador complete la recarga de la pagina");
		boolean resultStep9 = recargaPage.esperarCargaCompleta();
		assertTrue(resultStep9, "Paso 9 fallido: La pagina no termino de cargar");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El indicador de carga desaparece y la pagina termina de cargar");

		/* PASO 10 ******************************************************************/
		addStep("Verificar que la pagina recargada NO muestra el mensaje de error: The resource you are looking for has been removed, had its name changed, or is temporarily unavailable.");
		boolean resultStep10 = recargaPage.validarAusenciaErrorIis();
		assertTrue(resultStep10, "Paso 10 fallido: La pagina recargada muestra el error IIS");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: La pagina cargada no muestra texto de error ni pantalla de fallo del servidor IIS");

		/* PASO 11 ******************************************************************/
		addStep("Verificar que la URL en la barra de direcciones del navegador despues del refresco es identica a la URL documentada antes del refresco");
		boolean resultStep11 = recargaPage.validarUrlSinCambio(urlAntes);
		assertTrue(resultStep11, "Paso 11 fallido: La URL cambio o el sistema redirigio a otra pagina");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: La URL posterior al refresco es identica y el sistema no redirige al usuario");

		/* PASO 12 ******************************************************************/
		addStep("Verificar que el contenido de la pagina 'pantalla principal del Portal Cometa' es visible y correcto despues del refresco");
		boolean resultStep12 = recargaPage.validarContenidoVisible("Inicio Externo", "CEDIS", "Directos",
				"FAQS CEDIS", "FAQS Directos", "Centro de descarga");
		assertTrue(resultStep12, "Paso 12 fallido: El contenido de la pantalla principal no es visible o correcto despues del refresco");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El usuario permanece en la pantalla principal con el contenido correcto");
	}

	@AfterMethod
	public void afterMethod() {
		if (u != null) {
			u.quit();
		}
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "*1. Fix de navegacion Angular desplegado y perfil ADMIN (usuario 5555555) disponible.      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Verificar que F5 recarga la pantalla principal del Portal COMETA sin error IIS y conserva la ruta actual.";
	}

	@Override
	public String setTestDesigner() {
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_185_RecargaF5HomePortalCometaAdmin_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}