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

/**DESCRIPCIÓN: Valida la habilitación del filtro Tienda al elegir Tienda en Selector de Filtro y el bloqueo de Ruta Dinámica (UNIGIS) con perfiles CEDIS y Admin.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_057 Habilitación del filtro Tienda al elegir Tienda en Selector de Filtro y bloqueo de Ruta Dinámica (UNIGIS)
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
public class ATC_FT_057_HabilitacionFiltroTiendaBloqueoRutaDinamica extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_057_HabilitacionFiltroTiendaBloqueoRutaDinamica_test(HashMap<String, String> data) throws Exception {
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
		addStep("Capturar Fecha inicial y Fecha final para habilitar el Selector de Filtro con perfil " + perfil);
		boolean resultStep5 = rutaPage.capturaFechasInicialFinalParaHabilitarSelector(true);
		assertTrue(resultStep5, "No se pudieron capturar Fecha inicial y Fecha final correctamente");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Elegir Tienda en el Selector de Filtro con perfil " + perfil);
		boolean resultStep6 = rutaPage.seleccionaTiendaEnSelectorFiltro(true);
		assertTrue(resultStep6, "No se mostro Tienda como valor elegido en el Selector de Filtro");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Validar que Tienda se active, Consultar permanezca inactivo y Ruta Dinámica quede bloqueada antes de seleccionar tienda con perfil " + perfil);
		boolean resultStep7 = rutaPage.validaTiendaActivaRutaDinamicaYConsultarInactivos(true);
		assertTrue(resultStep7, "Tienda no se activo, Consultar no permanecio inactivo o Ruta Dinámica no quedo bloqueada");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Abrir filtro Tienda y marcar el checkbox de una tienda disponible con perfil " + perfil);
		boolean resultStep8 = rutaPage.marcaPrimeraTiendaDisponible(true);
		assertTrue(resultStep8, "No se pudo marcar una tienda disponible en el filtro Tienda");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Validar que el botón Consultar se habilite después de seleccionar una tienda con perfil " + perfil);
		boolean resultStep9 = rutaPage.validaBotonConsultarActivo(true);
		assertTrue(resultStep9, "El botón Consultar no se habilito después de seleccionar una tienda");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Intentar hacer clic sobre Ruta Dinámica (UNIGIS) y validar que permanezca bloqueada con perfil " + perfil);
		boolean resultStep10 = rutaPage.validaRutaDinamicaBloqueadaEnFlujoTienda(true);
		assertTrue(resultStep10, "Ruta Dinámica (UNIGIS) permitió interacción durante el flujo Tienda");
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
		return "Valida la habilitación del filtro Tienda al elegir Tienda en Selector de Filtro y el bloqueo de Ruta Dinámica (UNIGIS) con perfiles CEDIS y Admin.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_057_HabilitacionFiltroTiendaBloqueoRutaDinamica_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}