package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.rutaLiquidacionPage;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: Valida el indicador asterisco en campos obligatorios dinámicos de Liquidación de Ruta con perfiles CEDIS y Admin.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_082 Indicador asterisco en campos obligatorios dinámicos
* 
*PREREQUISITOS:
*1. Credenciales para los perfiles CEDIS y Admin
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Contar con usuarios activos para los perfiles CEDIS y Admin.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_082_IndicadorAsteriscoCamposObligatoriosDinamicos extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_082_IndicadorAsteriscoCamposObligatoriosDinamicos_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolución del proceso de recibo en Tienda Cedis | MVP5");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		rutaLiquidacionPage rutaPage = new rutaLiquidacionPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = user.equals("6666666") ? "CEDIS" : user.equals("5555555") ? "Admin" : "Perfil no identificado";

		/*
		 * PRECONDICIÓN
		 ***************************************************************************/
		addStep("Precondicion: obtener password configurado para el usuario " + user + " con perfil " + perfil);
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
		System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);
		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);

		/*
		 *                                             PASO 1
		 ***************************************************************************/
		addStep("Capturar usuario, contraseña y hacer clic en Inicio de sesión");
		boolean resultStep1 = pc4.IngresarURL(url, true);
		assertTrue(resultStep1, "No se pudo ingresar a la URL de Portal Cometa");
		pc4.PrivatePortal();
		boolean resultStep1Login = pc4.IniciarSesionNoToken(user, password, true);
		assertTrue(resultStep1Login, "No se pudo iniciar sesion con el usuario: " + user);

		/*
		 *                                             PASO 2
		 ***************************************************************************/
		addStep("Hacer clic en Portal COMETA");
		boolean resultStep2 = pc4.seleccionarPortalCometaQA(true);
		assertTrue(resultStep2, "No se mostro la pantalla principal del portal COMETA");

		/*
		 *                                             PASO 3
		 ***************************************************************************/
		addStep("Hacer clic en el modulo CEDIS");
		boolean resultStep3 = pc4.clicBotonCometaCedis(true);
		assertTrue(resultStep3, "No se mostro la opcion Recepcion dentro del modulo CEDIS");

		/*
		 *                                             PASO 4
		 ***************************************************************************/
		addStep("Ingresar a Recepcion y seleccionar Liquidacion de Ruta");
		boolean resultStep4Recepcion = pc4.ClicBotonRecepcionCedis(true);
		assertTrue(resultStep4Recepcion, "No se pudo ingresar a Recepcion de Transferencias y Devoluciones");
		boolean resultStep4Liquidacion = pc4.opcLiquidacionRuta();
		assertTrue(resultStep4Liquidacion, "No se pudo seleccionar la opcion Liquidacion de Ruta");
		boolean resultStep4Pantalla = pc4.scLiquidacionRuta();
		assertTrue(resultStep4Pantalla, "No se mostro correctamente la pantalla Liquidacion de Ruta");

		/*
		 *                                             PASO 5
		 ***************************************************************************/
		addStep("Revisar asteriscos iniciales de campos obligatorios Fecha inicial y Fecha final con perfil " + perfil);
		boolean resultStep5 = rutaPage.validaAsteriscosInicialesCamposObligatorios(true);
		assertTrue(resultStep5, "Fecha inicial o Fecha final no mostraron asterisco inicial, o campos dinámicos mostraron asterisco antes de selección");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Capturar Fecha inicial y Fecha final, elegir Ruta Dinámica (UNIGIS) y validar asterisco dinámico con perfil " + perfil);
		boolean resultStep6Fechas = rutaPage.capturaFechasInicialFinalParaHabilitarSelector(true);
		assertTrue(resultStep6Fechas, "No se pudieron capturar Fecha inicial y Fecha final correctamente");
		boolean resultStep6Selector = rutaPage.seleccionaRutaDinamicaEnSelectorFiltro(true);
		assertTrue(resultStep6Selector, "No se mostro Ruta Dinámica (UNIGIS) como valor elegido");
		boolean resultStep6Asterisco = rutaPage.validaAsteriscoDinamicoRutaDinamica(true);
		assertTrue(resultStep6Asterisco, "Ruta Dinámica (UNIGIS) no mostro asterisco dinámico o Tienda mantuvo asterisco incorrectamente");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Cambiar Selector de Filtro a Tienda y validar que el asterisco dinámico cambie de Ruta Dinámica a Tienda con perfil " + perfil);
		boolean resultStep7Selector = rutaPage.seleccionaTiendaEnSelectorFiltro(true);
		assertTrue(resultStep7Selector, "No se mostro Tienda como valor elegido o no reemplazo la selección previa");
		boolean resultStep7Asterisco = rutaPage.validaAsteriscoDinamicoTienda(true);
		assertTrue(resultStep7Asterisco, "Tienda no mostro asterisco dinámico o Ruta Dinámica no retiro el asterisco");
	}

	@AfterMethod
	public void afterMethod() {
		u.quit();

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "*1. Credenciales para los perfiles CEDIS y Admin      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Valida el indicador asterisco en campos obligatorios dinámicos de Liquidación de Ruta con perfiles CEDIS y Admin.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_082_IndicadorAsteriscoCamposObligatoriosDinamicos_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}