package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.accessControlPage;
import WEB.cometa.om.devolucionesEspecialesPage;
import WEB.cometa.om.faqsCedisPage;
import WEB.cometa.om.menuCedisPage;
import WEB.cometa.om.recepcionTransferenciasPage;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCION: Verifica que el perfil CEDIS visualice unicamente Recepcion y Devolucion en el submenu CEDIS y que Reportes y Graficas esten ocultos.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_134 CA01 - Verificar que el submenu CEDIS muestra unicamente la opcion Recepcion y Devolucion y que Reportes y Graficas estan ocultos - perfil CEDIS
* 
*PREREQUISITOS:
*1. Script de reorganizacion de menus ejecutado en Access Control y perfil CEDIS (usuario 6666666) disponible en QA.
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Usuario CEDIS 6666666.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_134_SubmenuCedisRecepcionDevolucionPerfilCedis extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_134_SubmenuCedisRecepcionDevolucionPerfilCedis_test(HashMap<String, String> data)
			throws Exception {
		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		accessControlPage accessControl = new accessControlPage(u, testCase, pc4);
		menuCedisPage menuCedis = new menuCedisPage(u, testCase);
		faqsCedisPage faqsCedis = new faqsCedisPage(u, testCase);
		recepcionTransferenciasPage recepcion = new recepcionTransferenciasPage(u, testCase);
		devolucionesEspecialesPage devEspeciales = new devolucionesEspecialesPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "CEDIS";
		String password = pc4.obtenerPasswordPorUsuario(user);

		/*
		 * PRECONDICION
		 ***************************************************************************/
		addStep("Precondicion: confirmar que el script de reorganizacion fue ejecutado y validar el perfil CEDIS con credenciales configuradas");
		boolean resultPrecondition = accessControl.validarPrecondicionPerfil(url, user, "6666666", perfil, password);
		assertTrue(resultPrecondition,
				"Precondicion fallida: el perfil CEDIS, el usuario 6666666 o sus credenciales no estan disponibles");
		testCase.addTextEvidenceCurrentStep(
				"Prerrequisito declarado: script de reorganizacion aplicado. Perfil CEDIS (usuario 6666666) y credenciales validados en QA.");

		/*
		 *                                             PASO 1
		 ***************************************************************************/
		addStep("Ingresar la URL " + url + " en el campo de direcciones del navegador");
		boolean resultStep1 = pc4.IngresarURL(url, true);
		assertTrue(resultStep1,
				"Paso 1 fallido: el sistema no muestra la pantalla de inicio de sesion del portal Access Control");
		testCase.addTextEvidenceCurrentStep(
				"El sistema muestra la pantalla de inicio de sesion del portal Access Control.");
		pc4.PrivatePortal();

		/*
		 *                                             PASOS 2, 3 Y 4
		 ***************************************************************************/
		addStep("Escribir '" + user + "' en el campo Usuario, escribir la contrasena del perfil y dar clic en el boton Iniciar sesion");
		boolean resultStep2 = pc4.IniciarSesionNoToken(user, password, true);
		assertTrue(resultStep2,
				"Los pasos 2, 3 y 4 fallaron: el sistema no valido las credenciales ni redirigio a la seleccion de portales");
		testCase.addTextEvidenceCurrentStep(
				"El campo Usuario recibe '" + user + "', la contrasena se captura enmascarada y el sistema inicia sesion.");

		/*
		 *                                             PASO 5
		 ***************************************************************************/
		addStep("Dar clic en la opcion Portal COMETA");
		boolean resultStep5 = pc4.seleccionarPortalCometaQA(true);
		assertTrue(resultStep5,
				"Paso 5 fallido: no se visualiza la pantalla principal del portal Cometa con CEDIS y FAQS CEDIS");
		testCase.addTextEvidenceCurrentStep(
				"Se visualiza la pantalla principal del portal Cometa con las opciones: CEDIS y FAQS CEDIS.");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Dar clic en la opcion 'CEDIS' del menu principal de navegacion en la parte superior de la pantalla");
		boolean resultStep6 = pc4.clicBotonCometaCedis(true);
		assertTrue(resultStep6, "Paso 6 fallido: el sistema no despliega el submenu del modulo CEDIS");
		testCase.addTextEvidenceCurrentStep(
				"El sistema despliega el submenu del modulo CEDIS mostrando las opciones disponibles.");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Observar todas las opciones visibles dentro del submenu desplegado de CEDIS");
		boolean resultStep7 = menuCedis.validaSubmenuSoloRecepcionYDevolucion();
		assertTrue(resultStep7,
				"Paso 7 fallido: el submenu CEDIS no muestra unicamente la opcion 'Recepcion y Devolucion'");
		testCase.addTextEvidenceCurrentStep(
				"El submenu de CEDIS muestra UNICAMENTE la opcion 'Recepcion y Devolucion'. Las opciones 'Reportes' y 'Graficas' NO aparecen en el submenu lateral desplegado.");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Confirmar que el submenu de CEDIS NO contiene la opcion 'Reportes'");
		boolean resultStep8 = menuCedis.validaReportesOcultoEnSubmenu();
		assertTrue(resultStep8, "Paso 8 fallido: la opcion 'Reportes' es visible en el submenu de CEDIS");
		testCase.addTextEvidenceCurrentStep(
				"La opcion 'Reportes' no es visible en el submenu de CEDIS. No existe ningun elemento HTML con ese texto en el menu desplegado del modulo CEDIS.");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Confirmar que el submenu de CEDIS NO contiene la opcion 'Graficas'");
		boolean resultStep9 = menuCedis.validaGraficasOcultoEnSubmenu();
		assertTrue(resultStep9, "Paso 9 fallido: la opcion 'Graficas' es visible en el submenu de CEDIS");
		testCase.addTextEvidenceCurrentStep(
				"La opcion 'Graficas' no es visible en el submenu de CEDIS. No existe ningun elemento HTML con ese texto en el menu desplegado del modulo CEDIS.");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Dar clic en la opcion 'Recepcion y Devolucion' del submenu para navegar a la pantalla");
		boolean resultStep10 = menuCedis.clicRecepcionYDevolucion();
		assertTrue(resultStep10,
				"Paso 10 fallido: el sistema no navega a Recepcion de Transferencias y Devoluciones");
		boolean resultStep10Filtros = recepcion.validaFiltrosHistoricos();
		assertTrue(resultStep10Filtros,
				"Paso 10 fallido: los filtros de Busqueda Historica no terminaron de cargar en Recepcion de Transferencias y Devoluciones");
		testCase.addTextEvidenceCurrentStep(
				"El sistema navega a la pantalla Recepcion de Transferencias y Devoluciones y los filtros de busqueda terminan de cargar.");

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("Volver a desplegar el submenu CEDIS desde Recepcion de Transferencias y Devoluciones y observar que conserve el mismo texto");
		boolean resultStep11Abrir = menuCedis.abrirSubmenuCedis();
		assertTrue(resultStep11Abrir,
				"Paso 11 fallido: el submenu CEDIS no se despliega desde Recepcion de Transferencias y Devoluciones");
		boolean resultStep11 = menuCedis.validaSubmenuSoloRecepcionYDevolucion();
		assertTrue(resultStep11,
				"Paso 11 fallido: el submenu CEDIS no conserva unicamente 'Recepcion y Devolucion' en Recepcion de Transferencias y Devoluciones");
		boolean resultStep11Solapa = menuCedis.validaSolapaSuperiorSinReportesNiGraficas();
		assertTrue(resultStep11Solapa,
				"Paso 11 fallido: la solapa superior muestra Reportes o Graficas en Recepcion de Transferencias y Devoluciones");
		testCase.addTextEvidenceCurrentStep(
				"El submenu CEDIS desplegado desde Recepcion de Transferencias y Devoluciones conserva el mismo texto 'Recepcion y Devolucion' sin mostrar Reportes ni Graficas.");
		boolean resultStep11Cerrar = menuCedis.cerrarSubmenuCedis();
		assertTrue(resultStep11Cerrar, "Paso 11 fallido: no fue posible cerrar el submenu CEDIS antes de continuar");

		/*
		 *                                    PASO 12 (Liquidacion de Ruta)
		 ***************************************************************************/
		addStep("Navegar a Liquidacion de Ruta desde Recepcion de Transferencias y Devoluciones y volver a desplegar el submenu CEDIS");
		boolean resultStep12aNavega = recepcion.abreLiquidacionRuta();
		assertTrue(resultStep12aNavega, "Paso 12 fallido: no fue posible navegar a Liquidacion de Ruta");
		boolean resultStep12aAbrir = menuCedis.abrirSubmenuCedis();
		assertTrue(resultStep12aAbrir, "Paso 12 fallido: el submenu CEDIS no se despliega desde Liquidacion de Ruta");
		boolean resultStep12a = menuCedis.validaSubmenuSoloRecepcionYDevolucion();
		assertTrue(resultStep12a,
				"Paso 12 fallido: el submenu CEDIS no conserva unicamente 'Recepcion y Devolucion' en Liquidacion de Ruta");
		boolean resultStep12aSolapa = menuCedis.validaSolapaSuperiorSinReportesNiGraficas();
		assertTrue(resultStep12aSolapa,
				"Paso 12 fallido: la solapa superior muestra Reportes o Graficas en Liquidacion de Ruta");
		testCase.addTextEvidenceCurrentStep(
				"En la pantalla Liquidacion de Ruta, el submenu CEDIS conserva el mismo texto 'Recepcion y Devolucion' sin mostrar Reportes ni Graficas.");
		boolean resultStep12aCerrar = menuCedis.cerrarSubmenuCedis();
		assertTrue(resultStep12aCerrar, "Paso 12 fallido: no fue posible cerrar el submenu CEDIS antes de continuar");

		/*
		 *                                    PASO 12 (Devoluciones Especiales)
		 ***************************************************************************/
		addStep("Navegar a Devoluciones Especiales desde Liquidacion de Ruta y volver a desplegar el submenu CEDIS");
		boolean resultStep12bNavega = devEspeciales.ingresarDevolucionesEspeciales(true);
		assertTrue(resultStep12bNavega, "Paso 12 fallido: no fue posible navegar a Devoluciones Especiales");
		boolean resultStep12bAbrir = menuCedis.abrirSubmenuCedis();
		assertTrue(resultStep12bAbrir,
				"Paso 12 fallido: el submenu CEDIS no se despliega desde Devoluciones Especiales");
		boolean resultStep12b = menuCedis.validaSubmenuSoloRecepcionYDevolucion();
		assertTrue(resultStep12b,
				"Paso 12 fallido: el submenu CEDIS no conserva unicamente 'Recepcion y Devolucion' en Devoluciones Especiales");
		boolean resultStep12bSolapa = menuCedis.validaSolapaSuperiorSinReportesNiGraficas();
		assertTrue(resultStep12bSolapa,
				"Paso 12 fallido: la solapa superior muestra Reportes o Graficas en Devoluciones Especiales");
		testCase.addTextEvidenceCurrentStep(
				"En la pantalla Devoluciones Especiales, el submenu CEDIS conserva el mismo texto 'Recepcion y Devolucion' sin mostrar Reportes ni Graficas.");
		boolean resultStep12bCerrar = menuCedis.cerrarSubmenuCedis();
		assertTrue(resultStep12bCerrar, "Paso 12 fallido: no fue posible cerrar el submenu CEDIS antes de continuar");

		/*
		 *                                    PASO 12 (FAQS CEDIS)
		 ***************************************************************************/
		addStep("Navegar a FAQS CEDIS y volver a desplegar el submenu CEDIS para confirmar que se mantiene sin Reportes ni Graficas");
		boolean resultStep12cNavega = faqsCedis.abrirYValidarFaqsCedis();
		assertTrue(resultStep12cNavega,
				"Paso 12 fallido: no fue posible navegar a la pantalla disponible FAQS CEDIS");
		boolean resultStep12cAbrir = menuCedis.abrirSubmenuCedis();
		assertTrue(resultStep12cAbrir, "Paso 12 fallido: el submenu CEDIS no se despliega desde FAQS CEDIS");
		boolean resultStep12c = menuCedis.validaSubmenuSoloRecepcionYDevolucion();
		assertTrue(resultStep12c,
				"Paso 12 fallido: el submenu CEDIS no conserva unicamente 'Recepcion y Devolucion' en FAQS CEDIS");
		boolean resultStep12cSolapa = menuCedis.validaSolapaSuperiorSinReportesNiGraficas();
		assertTrue(resultStep12cSolapa,
				"Paso 12 fallido: la solapa superior muestra Reportes o Graficas en FAQS CEDIS");
		testCase.addTextEvidenceCurrentStep(
				"En todas las pantallas del modulo CEDIS a las que se puede acceder (Recepcion de Transferencias y Devoluciones, Liquidacion de Ruta, Devoluciones Especiales y FAQS CEDIS), el submenu CEDIS conserva de manera consistente unicamente 'Recepcion y Devolucion' sin mostrar Reportes ni Graficas.");
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
		return "*1. Script de reorganizacion de menus ejecutado en Access Control y perfil CEDIS (usuario 6666666) disponible en QA.      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Verifica que el perfil CEDIS visualice unicamente Recepcion y Devolucion en el submenu CEDIS y que Reportes y Graficas esten ocultos.";
	}

	@Override
	public String setTestDesigner() {
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_134_SubmenuCedisRecepcionDevolucionPerfilCedis_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}