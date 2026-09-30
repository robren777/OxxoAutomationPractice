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

/**DESCRIPCION: Verificar que F5 recarga Cambios Fisicos Pendientes sin error IIS y conserva la ruta actual.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_187 CA01-CA02 - Verificar que F5 recarga correctamente Directos - Cambios Fisicos pendientes sin error - perfil OXXO_OS
*
*PREREQUISITOS:
*1. Fix de navegacion Angular desplegado y perfil OXXO_OS (usuario 3333333) disponible.
*2.Liga de Portal Cometa
*
*INSUMOS:
*Usuario OXXO_OS disponible en QA.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_187_RecargaF5CambiosFisicosPendientesOxxoOs extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_187_RecargaF5CambiosFisicosPendientesOxxoOs_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		recargaNavegacionPage recargaPage = new recargaNavegacionPage(u, testCase);
		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "OXXO_OS";

		addStep("Precondicion: obtener password configurado para el usuario " + user + " con perfil " + perfil);
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
		System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);
		assertTrue("3333333".equals(user), "El data provider no corresponde al perfil OXXO_OS requerido");
		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null && !password.trim().isEmpty(), "No se encontro password configurada para el usuario: " + user);

		/* PASO 1 *******************************************************************/
		addStep("Ingresar la URL https://pruebasweb.oxxo.com/AuthFemcoProx/Login en el campo de direcciones del navegador");
		boolean resultStep1 = pc4.IngresarURL(url, true);
		assertTrue(resultStep1, "Paso 1 fallido: No se muestra la pantalla de inicio de sesion de Access Control");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El sistema muestra la pantalla de inicio de sesion del portal Access Control");
		pc4.PrivatePortal();

		/* PASO 2 *******************************************************************/
		addStep("Escribir '3333333' en el campo Usuario");
		boolean resultStep2 = "3333333".equals(user);
		assertTrue(resultStep2, "Paso 2 fallido: El usuario no corresponde a '3333333'");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El campo Usuario muestra el valor '3333333'");

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
				&& recargaPage.validarContenidoVisible("Directos", "FAQS Directos", "Centro de descarga");
		assertTrue(resultStep5, "Paso 5 fallido: No se visualiza la pantalla principal del Portal COMETA");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Se visualiza la pantalla principal con Directos, FAQS Directos y Centro de descarga");

		/* PASO 6 *******************************************************************/
		addStep("Dar clic en la tarjeta Directos");
		boolean resultStep6 = pc4.clicBotonCometaDirectos(true);
		assertTrue(resultStep6, "Paso 6 fallido: No se visualiza el modulo Directos");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Se visualiza el modulo Directos");

		/* PASO 7 *******************************************************************/
		addStep("Dar clic en la tarjeta Cambios Fisicos");
		boolean resultStep7 = pc4.ClicBotonCF(true);
		assertTrue(resultStep7, "Paso 7 fallido: No se visualiza la pantalla Cambios Fisicos");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Se visualiza Cambios Fisicos con los filtros de busqueda disponibles");

		/* PASO 8 *******************************************************************/
		addStep("Verificar que la pantalla 'Cambios Fisicos' se carga correctamente antes del refresco");
		boolean resultStep8 = recargaPage.validarContenidoVisible("Cambios Físicos", "Proveedor", "Región",
				"Zona", "Plaza", "Tienda", "Consultar") && u.isDisplayed(pc4.gridTabla);
		assertTrue(resultStep8, "Paso 8 fallido: Cambios Fisicos no muestra filtros y grid completamente cargados");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Cambios Fisicos muestra filtros y grid sin errores");

		/* PASO 9 *******************************************************************/
		addStep("Dar clic en la opcion 'Cambios Fisicos Pendientes'");
		boolean resultStep9 = pc4.clickCambiosFisicosPendientesPantalla(true)
				&& recargaPage.validarContenidoVisible("Detalles de Cambios Físicos Pendientes",
						"Región", "Plaza", "Tienda", "Consultar")
				&& u.isDisplayed(pc4.gridTabla);
		assertTrue(resultStep9, "Paso 9 fallido: No se visualiza Cambios Fisicos Pendientes");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Cambios Fisicos Pendientes muestra filtros y grid completamente cargados");

		/* PASO 10 ******************************************************************/
		addStep("Leer y documentar la URL completa que se muestra en la barra de direcciones del navegador en este momento");
		String urlAntes = recargaPage.capturarUrlActual();
		boolean resultStep10 = recargaPage.validarUrlCorrespondePantalla(urlAntes,
				"/PortalCometa/directos/cambios-fisicos-pendientes");
		assertTrue(resultStep10, "Paso 10 fallido: No fue posible documentar la URL de Cambios Fisicos Pendientes");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: La URL corresponde a Cambios Fisicos Pendientes y queda registrada para comparacion posterior");

		/* PASO 11 ******************************************************************/
		addStep("Presionar la tecla F5 del teclado para ejecutar el refresco de la pagina");
		boolean resultStep11 = recargaPage.refrescarConF5();
		assertTrue(resultStep11, "Paso 11 fallido: El navegador no inicio la recarga con F5");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El navegador inicia el proceso de recarga de la pagina");

		/* PASO 12 ******************************************************************/
		addStep("Esperar a que el navegador complete la recarga de la pagina");
		boolean resultStep12 = recargaPage.esperarCargaCompleta();
		assertTrue(resultStep12, "Paso 12 fallido: La pagina no termino de cargar");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El indicador de carga desaparece y la pagina termina de cargar");

		/* PASO 13 ******************************************************************/
		addStep("Verificar que la pagina recargada NO muestra el mensaje de error IIS");
		boolean resultStep13 = recargaPage.validarAusenciaErrorIis();
		assertTrue(resultStep13, "Paso 13 fallido: Cambios Fisicos Pendientes muestra el error IIS despues del refresco");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Cambios Fisicos Pendientes se muestra correctamente sin texto de error IIS");

		/* PASO 14 ******************************************************************/
		addStep("Verificar que la URL en la barra de direcciones del navegador despues del refresco es identica a la URL documentada antes del refresco");
		boolean resultStep14 = recargaPage.validarUrlSinCambio(urlAntes);
		assertTrue(resultStep14, "Paso 14 fallido: La URL cambio o el sistema redirigio fuera de Cambios Fisicos Pendientes");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: La URL posterior al refresco es identica y permanece en Cambios Fisicos Pendientes");

		/* PASO 15 ******************************************************************/
		addStep("Verificar que el contenido de la pagina 'Cambios Fisicos Pendientes' es visible y correcto despues del refresco");
		boolean resultStep15 = recargaPage.validarContenidoVisible("Detalles de Cambios Físicos Pendientes",
				"Región", "Plaza", "Tienda", "Consultar") && u.isDisplayed(pc4.gridTabla);
		assertTrue(resultStep15, "Paso 15 fallido: Cambios Fisicos Pendientes no conserva sus filtros y grid despues del refresco");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Cambios Fisicos Pendientes muestra filtros y grid; el usuario permanece en la misma pagina");
	}

	@AfterMethod
	public void afterMethod() {
		if (u != null) {
			u.quit();
		}
	}

	@Override public void beforeTest() {}
	@Override public String setPrerequisites() { return "*1. Fix de navegacion Angular desplegado y perfil OXXO_OS (usuario 3333333) disponible.      \r\n" + "*2.Liga de Portal Cometa"; }
	@Override public String setTestDescription() { return "Verificar que F5 recarga Cambios Fisicos Pendientes sin error IIS y conserva la ruta actual."; }
	@Override public String setTestDesigner() { return "Mauricio Gurrola"; }
	@Override public String setTestFullName() { return "ATC_FT_187_RecargaF5CambiosFisicosPendientesOxxoOs_test"; }
	@Override public String setTestInstanceID() { return null; }
}