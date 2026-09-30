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

/**DESCRIPCIÓN: Valida el tooltip de Fecha final menor que Fecha inicial al posicionarse sobre Consultar con perfiles CEDIS y Admin.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_063 Tooltip de Fecha final menor que Fecha inicial al posicionarse sobre Consultar
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
public class ATC_FT_063_TooltipFechaFinalMenorFechaInicialConsultar extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_063_TooltipFechaFinalMenorFechaInicialConsultar_test(HashMap<String, String> data) throws Exception {
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
		assertTrue(pc4.IngresarURL(url, true), "No se pudo ingresar a la URL de Portal Cometa");
		pc4.PrivatePortal();
		assertTrue(pc4.IniciarSesionNoToken(user, password, true), "No se pudo iniciar sesion con el usuario: " + user);

		/*
		 *                                             PASO 2
		 ***************************************************************************/
		addStep("Hacer clic en Portal COMETA");
		assertTrue(pc4.seleccionarPortalCometaQA(true), "No se mostro la pantalla principal del portal COMETA");

		/*
		 *                                             PASO 3
		 ***************************************************************************/
		addStep("Hacer clic en el modulo CEDIS");
		assertTrue(pc4.clicBotonCometaCedis(true), "No se mostro la opcion Recepcion dentro del modulo CEDIS");

		/*
		 *                                             PASO 4
		 ***************************************************************************/
		addStep("Ingresar a Recepcion y seleccionar Liquidacion de Ruta");
		assertTrue(pc4.ClicBotonRecepcionCedis(true), "No se pudo ingresar a Recepcion de Transferencias y Devoluciones");
		assertTrue(pc4.opcLiquidacionRuta(), "No se pudo seleccionar la opcion Liquidacion de Ruta");
		assertTrue(pc4.scLiquidacionRuta(), "No se mostro correctamente la pantalla Liquidacion de Ruta");

		/*
		 *                                             PASO 5
		 ***************************************************************************/
		addStep("Capturar Fecha inicial mayor que Fecha final con perfil " + perfil);
		assertTrue(rutaPage.capturaFechaFinalMenorQueFechaInicial(true), "No se pudo capturar Fecha final menor que Fecha inicial");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Posicionar cursor sobre Consultar y validar tooltip de Fecha final menor que Fecha inicial con perfil " + perfil);
		assertTrue(rutaPage.validaTooltipConsultarFechaFinalMenorInicial(true), "No se mostro el tooltip esperado de Fecha final menor que Fecha inicial");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Retirar el cursor del botón Consultar y validar que el tooltip se oculte con perfil " + perfil);
		assertTrue(rutaPage.retiraCursorConsultarYValidaTooltipOculto("La fecha final no puede ser menor que la fecha inicial.", true), "El tooltip de Fecha final menor que Fecha inicial no se oculto al retirar el cursor de Consultar");
	}

	@AfterMethod public void afterMethod() { u.quit(); }
	@Override public void beforeTest() {}
	@Override public String setPrerequisites() { return "*1. Credenciales para los perfiles CEDIS y Admin      \r\n" + "*2.Liga de Portal Cometa"; }
	@Override public String setTestDescription() { return "Valida el tooltip de Fecha final menor que Fecha inicial al posicionarse sobre Consultar con perfiles CEDIS y Admin."; }
	@Override public String setTestDesigner() { return "Mauricio Gurrola"; }
	@Override public String setTestFullName() { return "ATC_FT_063_TooltipFechaFinalMenorFechaInicialConsultar_test"; }
	@Override public String setTestInstanceID() { return null; }
}