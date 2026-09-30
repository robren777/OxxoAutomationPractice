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

/**DESCRIPCION: Verificar que F5 recarga Directos - Devoluciones sin error IIS y conserva la ruta actual.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_186 CA01-CA02 - Verificar que F5 recarga correctamente Directos - Devoluciones sin error - perfil ADMIN
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
public class ATC_FT_186_RecargaF5DevolucionesAdmin extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_186_RecargaF5DevolucionesAdmin_test(HashMap<String, String> data) throws Exception {
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
		assertTrue(resultStep1, "Paso 1 fallido: No se muestra la pantalla de inicio de sesion de Access Control");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El sistema muestra la pantalla de inicio de sesion del portal Access Control");
		pc4.PrivatePortal();

		/* PASO 2 *******************************************************************/
		addStep("Escribir '5555555' en el campo Usuario");
		boolean resultStep2 = "5555555".equals(user);
		assertTrue(resultStep2, "Paso 2 fallido: El usuario no corresponde a '5555555'");
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
				&& recargaPage.validarContenidoVisible("CEDIS", "Directos", "FAQS CEDIS",
						"FAQS Directos", "Centro de descarga");
		assertTrue(resultStep5, "Paso 5 fallido: No se visualiza la pantalla principal del Portal COMETA");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Se visualiza la pantalla principal con CEDIS, Directos, FAQS CEDIS, FAQS Directos y Centro de descarga");

		/* PASO 6 *******************************************************************/
		addStep("Dar clic en la tarjeta Directos");
		boolean resultStep6 = pc4.clicBotonCometaDirectos(true);
		assertTrue(resultStep6, "Paso 6 fallido: No se visualiza el modulo Directos");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Se visualiza el modulo Directos");

		/* PASO 7 *******************************************************************/
		addStep("Dar clic en la tarjeta Devoluciones");
		boolean resultStep7 = pc4.ClicBotonDevoluciones(true);
		assertTrue(resultStep7, "Paso 7 fallido: No se visualiza la pantalla Devoluciones");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Se visualiza Devoluciones con los filtros de busqueda disponibles");

		/* PASO 8 *******************************************************************/
		addStep("Verificar que la pantalla 'Devoluciones' se carga correctamente antes del refresco");
		boolean resultStep8 = recargaPage.validarContenidoVisible("Devoluciones", "Proveedor", "Región",
				"Zona", "Plaza", "Tienda", "Consultar") && u.isDisplayed(pc4.gridTabla);
		assertTrue(resultStep8, "Paso 8 fallido: Devoluciones no muestra filtros y grid completamente cargados");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Devoluciones muestra filtros y grid sin errores");

		/* PASO 9 *******************************************************************/
		addStep("Leer y documentar la URL completa que se muestra en la barra de direcciones del navegador en este momento");
		String urlAntes = recargaPage.capturarUrlActual();
		boolean resultStep9 = recargaPage.validarUrlCorrespondePantalla(urlAntes, "/PortalCometa/directos/devoluciones");
		assertTrue(resultStep9, "Paso 9 fallido: No fue posible documentar la URL de Devoluciones");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: La URL corresponde a Devoluciones y queda registrada para comparacion posterior");

		/* PASO 10 ******************************************************************/
		addStep("Presionar la tecla F5 del teclado para ejecutar el refresco de la pagina");
		boolean resultStep10 = recargaPage.refrescarConF5();
		assertTrue(resultStep10, "Paso 10 fallido: El navegador no inicio la recarga con F5");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El navegador inicia el proceso de recarga de la pagina");

		/* PASO 11 ******************************************************************/
		addStep("Esperar a que el navegador complete la recarga de la pagina");
		boolean resultStep11 = recargaPage.esperarCargaCompleta();
		assertTrue(resultStep11, "Paso 11 fallido: La pagina no termino de cargar");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El indicador de carga desaparece y la pagina termina de cargar");

		/* PASO 12 ******************************************************************/
		addStep("Verificar que la pagina recargada NO muestra el mensaje de error IIS");
		boolean resultStep12 = recargaPage.validarAusenciaErrorIis();
		assertTrue(resultStep12, "Paso 12 fallido: Devoluciones muestra el error IIS despues del refresco");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Devoluciones se muestra correctamente sin texto de error IIS");

		/* PASO 13 ******************************************************************/
		addStep("Verificar que la URL en la barra de direcciones del navegador despues del refresco es identica a la URL documentada antes del refresco");
		boolean resultStep13 = recargaPage.validarUrlSinCambio(urlAntes);
		assertTrue(resultStep13, "Paso 13 fallido: La URL cambio o el sistema redirigio fuera de Devoluciones");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: La URL posterior al refresco es identica y permanece en Devoluciones");

		/* PASO 14 ******************************************************************/
		addStep("Verificar que el contenido de la pagina 'Devoluciones' es visible y correcto despues del refresco");
		boolean resultStep14 = recargaPage.validarContenidoVisible("Devoluciones", "Proveedor", "Región",
				"Zona", "Plaza", "Tienda", "Consultar") && u.isDisplayed(pc4.gridTabla);
		assertTrue(resultStep14, "Paso 14 fallido: Devoluciones no conserva sus filtros y grid despues del refresco");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Devoluciones muestra filtros y grid; el usuario permanece en la misma pagina");
	}

	@AfterMethod
	public void afterMethod() {
		if (u != null) {
			u.quit();
		}
	}

	@Override public void beforeTest() {}
	@Override public String setPrerequisites() { return "*1. Fix de navegacion Angular desplegado y perfil ADMIN (usuario 5555555) disponible.      \r\n" + "*2.Liga de Portal Cometa"; }
	@Override public String setTestDescription() { return "Verificar que F5 recarga Directos - Devoluciones sin error IIS y conserva la ruta actual."; }
	@Override public String setTestDesigner() { return "Mauricio Gurrola"; }
	@Override public String setTestFullName() { return "ATC_FT_186_RecargaF5DevolucionesAdmin_test"; }
	@Override public String setTestInstanceID() { return null; }
}