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

/**DESCRIPCIÓN: Valida la opción Limpiar selección del filtro Tienda en Liquidación de Ruta con perfiles CEDIS y Admin.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_066 Opción Limpiar selección del filtro Tienda
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
public class ATC_FT_066_OpcionLimpiarSeleccionFiltroTienda extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_066_OpcionLimpiarSeleccionFiltroTienda_test(HashMap<String, String> data) throws Exception {
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
		addStep("Capturar Fecha inicial y Fecha final, elegir Tienda y validar Limpiar selección deshabilitado sin tiendas marcadas con perfil " + perfil);
		boolean resultStep5Fechas = rutaPage.capturaFechasInicialFinalParaHabilitarSelector(true);
		assertTrue(resultStep5Fechas, "No se pudieron capturar Fecha inicial y Fecha final correctamente");
		boolean resultStep5Selector = rutaPage.seleccionaTiendaEnSelectorFiltro(true);
		assertTrue(resultStep5Selector, "No se mostro Tienda como valor elegido en el Selector de Filtro");
		boolean resultStep5Filtro = rutaPage.validaFiltroTiendaSinSeleccionYLimpiarDeshabilitado(true);
		assertTrue(resultStep5Filtro, "El filtro Tienda no mostro opciones sin preseleccion o Limpiar selección no estuvo deshabilitado");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Marcar una tienda del listado y validar que Limpiar selección se habilite con perfil " + perfil);
		boolean resultStep6Tienda = rutaPage.marcaPrimeraTiendaDisponible(true);
		assertTrue(resultStep6Tienda, "No se pudo marcar una tienda disponible del listado");
		boolean resultStep6Limpiar = rutaPage.validaLimpiarSeleccionHabilitadoConTiendaMarcada(true);
		assertTrue(resultStep6Limpiar, "Limpiar selección no se habilito después de marcar una tienda");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Hacer clic en Limpiar selección y validar que el filtro Tienda quede sin tiendas marcadas con perfil " + perfil);
		boolean resultStep7 = rutaPage.clicLimpiarSeleccionYValidaTiendaSinSeleccion(true);
		assertTrue(resultStep7, "Limpiar selección no desmarco las tiendas o no regreso a estado deshabilitado");
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
		return "Valida la opción Limpiar selección del filtro Tienda en Liquidación de Ruta con perfiles CEDIS y Admin.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_066_OpcionLimpiarSeleccionFiltroTienda_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}