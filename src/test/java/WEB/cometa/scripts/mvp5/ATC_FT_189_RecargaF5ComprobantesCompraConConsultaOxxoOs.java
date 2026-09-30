package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.comprobantesCompraPage;
import WEB.cometa.om.recargaNavegacionPage;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCION: Verificar que F5 recarga Comprobantes de Compra despues de consultar datos, sin error IIS y conservando la ruta.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_189 CA01-CA02 - Verificar que F5 recarga correctamente Comprobantes de Compra despues de ejecutar una consulta - perfil OXXO_OS
*
*PREREQUISITOS:
*1. Fix de navegacion Angular desplegado, perfil OXXO_OS (usuario 3333333) y registros disponibles en xxcm_cc.
*2.Liga de Portal Cometa
*
*INSUMOS:
*Usuario OXXO_OS y registros de Comprobantes de Compra disponibles en QA.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_189_RecargaF5ComprobantesCompraConConsultaOxxoOs extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_189_RecargaF5ComprobantesCompraConConsultaOxxoOs_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		comprobantesCompraPage comprobantesPage = new comprobantesCompraPage(u, testCase);
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
		String fechaConInformacion = comprobantesPage.obtenerFechaConInformacionDesdeBD();
		assertTrue(fechaConInformacion != null,
				"No se obtuvo desde xxcm_cc una fecha con informacion para Comprobantes de Compra");

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
		addStep("Dar clic en la tarjeta Comprobantes de Compra");
		boolean resultStep7 = pc4.ClicBotonComprobantesDeCompra(true)
				&& comprobantesPage.validarPantallaComprobantesCompra();
		assertTrue(resultStep7, "Paso 7 fallido: No se visualiza Comprobantes de Compra con sus filtros");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Se visualiza Comprobantes de Compra con los filtros disponibles");

		/* PASO 8 *******************************************************************/
		addStep("Seleccionar la fecha inicio con informacion obtenida desde BD en el campo Rango de Fecha");
		boolean resultStep8 = comprobantesPage.seleccionarFechaInicio(fechaConInformacion);
		assertTrue(resultStep8, "Paso 8 fallido: El campo Fecha Inicio no muestra la fecha obtenida desde BD " + fechaConInformacion);
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El campo Fecha Inicio muestra la fecha con informacion obtenida desde BD: " + fechaConInformacion);

		/* PASO 9 *******************************************************************/
		addStep("Seleccionar la fecha fin con informacion obtenida desde BD en el campo Rango de Fecha");
		boolean resultStep9 = comprobantesPage.seleccionarFechaFin(fechaConInformacion);
		assertTrue(resultStep9, "Paso 9 fallido: El campo Fecha Fin no muestra la fecha obtenida desde BD "
				+ fechaConInformacion + " o Consultar no queda disponible");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El campo Fecha Fin muestra la fecha con informacion obtenida desde BD "
				+ fechaConInformacion + " y el boton Consultar se habilita");

		/* PASO 10 ******************************************************************/
		addStep("Dar clic en el boton Consultar");
		boolean resultStep10 = pc4.consultarComprobantes(true);
		assertTrue(resultStep10, "Paso 10 fallido: La consulta no muestra resultados en el grid");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El sistema ejecuta la consulta y muestra N registros en el grid");

		/* PASO 11 ******************************************************************/
		addStep("Verificar que el grid muestra registros correctamente cargados");
		boolean resultStep11 = comprobantesPage.validarGridConRegistros();
		assertTrue(resultStep11, "Paso 11 fallido: El grid no muestra registros del periodo consultado");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El grid de Comprobantes de Compra muestra registros del periodo sin errores");

		/* PASO 12 ******************************************************************/
		addStep("Leer y documentar la URL completa en la barra de direcciones del navegador");
		String urlAntes = recargaPage.capturarUrlActual();
		boolean resultStep12 = recargaPage.validarUrlCorrespondePantalla(urlAntes, "comprobantes");
		assertTrue(resultStep12, "Paso 12 fallido: No fue posible documentar la URL de Comprobantes de Compra");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: La URL corresponde a Comprobantes de Compra y queda registrada para comparacion posterior");

		/* PASO 13 ******************************************************************/
		addStep("Presionar la tecla F5 del teclado para ejecutar el refresco de la pagina");
		boolean resultStep13 = recargaPage.refrescarConF5();
		assertTrue(resultStep13, "Paso 13 fallido: El navegador no inicio la recarga con F5");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El navegador inicia la recarga de la pagina");

		/* PASO 14 ******************************************************************/
		addStep("Esperar a que el navegador complete la recarga");
		boolean resultStep14 = recargaPage.esperarCargaCompleta();
		assertTrue(resultStep14, "Paso 14 fallido: La pagina no termino de cargar");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El navegador completa la carga de la pagina");

		/* PASO 15 ******************************************************************/
		addStep("Verificar que la pagina recargada NO muestra el mensaje de error IIS");
		boolean resultStep15 = recargaPage.validarAusenciaErrorIis();
		assertTrue(resultStep15, "Paso 15 fallido: Comprobantes de Compra muestra el error IIS despues del refresco");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: La pagina no muestra el mensaje de error IIS ni texto de fallo del servidor");

		/* PASO 16 ******************************************************************/
		addStep("Verificar que la URL en la barra de direcciones despues del refresco es identica a la URL documentada en el paso 12");
		boolean resultStep16 = recargaPage.validarUrlSinCambio(urlAntes);
		assertTrue(resultStep16, "Paso 16 fallido: La URL cambio o el sistema redirigio fuera de Comprobantes de Compra");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: La URL es identica y el sistema permanece en Comprobantes de Compra");

		/* PASO 17 ******************************************************************/
		addStep("Verificar que la pantalla de Comprobantes de Compra es visible con sus filtros de busqueda disponibles");
		boolean resultStep17 = comprobantesPage.validarPantallaComprobantesCompra();
		assertTrue(resultStep17, "Paso 17 fallido: Comprobantes de Compra no muestra sus filtros despues del refresco");
		testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Comprobantes de Compra muestra el area de filtros y permite volver a interactuar");
	}

	@AfterMethod
	public void afterMethod() {
		if (u != null) {
			u.quit();
		}
	}

	@Override public void beforeTest() {}
	@Override public String setPrerequisites() { return "*1. Fix de navegacion Angular desplegado, perfil OXXO_OS (usuario 3333333) y registros disponibles en xxcm_cc.      \r\n" + "*2.Liga de Portal Cometa"; }
	@Override public String setTestDescription() { return "Verificar que F5 recarga Comprobantes de Compra despues de consultar datos, sin error IIS y conservando la ruta."; }
	@Override public String setTestDesigner() { return "Mauricio Gurrola"; }
	@Override public String setTestFullName() { return "ATC_FT_189_RecargaF5ComprobantesCompraConConsultaOxxoOs_test"; }
	@Override public String setTestInstanceID() { return null; }
}