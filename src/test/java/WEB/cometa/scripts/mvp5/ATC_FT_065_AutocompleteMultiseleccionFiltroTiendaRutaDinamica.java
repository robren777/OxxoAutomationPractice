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

/**DESCRIPCIÓN: Valida el autocomplete y la multiselección del filtro Tienda en escenario Ruta Dinámica (UNIGIS) con perfiles CEDIS y Admin.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_065 Autocomplete y multiselección del filtro Tienda en escenario Ruta Dinámica (UNIGIS)
* 
*PREREQUISITOS:
*1. Credenciales para los perfiles CEDIS y Admin y ruta dinámica válida disponible en BD
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Contar con usuarios activos para los perfiles CEDIS y Admin y ruta dinámica válida obtenida desde BD.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_065_AutocompleteMultiseleccionFiltroTiendaRutaDinamica extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_065_AutocompleteMultiseleccionFiltroTiendaRutaDinamica_test(HashMap<String, String> data) throws Exception {
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
		addStep("Precondicion: obtener password y ruta dinámica configurados para el usuario " + user + " con perfil " + perfil);
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
		System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);
		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);
		boolean resultPreRuta = pc4.obtenerRutaPropuesta(user);
		assertTrue(resultPreRuta, "No se obtuvo una ruta dinámica válida desde BD para el usuario: " + user);

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
		addStep("Capturar Fecha inicial y Fecha final visibles, elegir Ruta Dinámica (UNIGIS), capturar ruta válida y abrir filtro Tienda con perfil " + perfil);
		boolean resultStep5Fechas = rutaPage.capturaFechasInicialFinalParaHabilitarSelector(true);
		assertTrue(resultStep5Fechas, "No se pudieron capturar Fecha inicial y Fecha final correctamente");
		boolean resultStep5Selector = rutaPage.seleccionaRutaDinamicaEnSelectorFiltro(true);
		assertTrue(resultStep5Selector, "No se mostro Ruta Dinámica (UNIGIS) como valor elegido en el Selector de Filtro");
		boolean resultStep5Ruta = rutaPage.capturaRutaDinamicaUnigis(PortalCometaMVP4.rutaPropuesta, true);
		assertTrue(resultStep5Ruta, "No se pudo ingresar la Ruta Dinámica (UNIGIS) correctamente");
		boolean resultStep5Tienda = rutaPage.validaFiltroTiendaActivo(true);
		assertTrue(resultStep5Tienda, "El filtro Tienda no se activo después de capturar la Ruta Dinámica");
		boolean resultStep5Listado = rutaPage.abreFiltroTiendaYValidaOpcionesVisibles(true);
		assertTrue(resultStep5Listado, "No se mostro el listado de tiendas al abrir el filtro Tienda");
		String textoBusquedaTienda = rutaPage.obtieneTextoBusquedaDesdePrimeraTiendaVisible();
		assertTrue(textoBusquedaTienda != null && !textoBusquedaTienda.isEmpty(), "No se pudo obtener un texto de busqueda desde una tienda visible");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Escribir " + textoBusquedaTienda + " en minusculas en el buscador del filtro Tienda y validar sugerencias coincidentes con perfil " + perfil);
		boolean resultStep6 = rutaPage.capturaBusquedaFiltroTiendaYValidaSugerencias(textoBusquedaTienda, textoBusquedaTienda, true);
		assertTrue(resultStep6, "El autocomplete de Tienda no mostro sugerencias coincidentes con " + textoBusquedaTienda + " en minusculas");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Borrar la busqueda y escribir " + textoBusquedaTienda.toUpperCase() + " en mayusculas validando las mismas sugerencias con perfil " + perfil);
		boolean resultStep7 = rutaPage.comparaAutocompleteFiltroTiendaSinDistinguirMayusculas(textoBusquedaTienda, textoBusquedaTienda.toUpperCase(), true);
		assertTrue(resultStep7, "El autocomplete de Tienda distinguio mayusculas/minusculas o no mostro las mismas sugerencias");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Borrar el texto capturado y navegar el listado completo usando el scroll del selector con perfil " + perfil);
		boolean resultStep8 = rutaPage.limpiaBusquedaTiendaYNavegaListadoConScroll(true);
		assertTrue(resultStep8, "No se pudo limpiar la busqueda o navegar el listado completo de Tienda con scroll");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Marcar dos tiendas adicionales y validar que el selector Tienda permanezca abierto con perfil " + perfil);
		boolean resultStep9 = rutaPage.marcaDosTiendasAdicionalesYValidaSelectorAbierto(true);
		assertTrue(resultStep9, "No se pudieron marcar dos tiendas adicionales o el selector Tienda se cerro automaticamente");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Hacer clic fuera del componente del filtro Tienda y validar texto Multiples seleccionados con perfil " + perfil);
		boolean resultStep10 = rutaPage.cierraSelectorTiendaYValidaTextoMultiple(true);
		assertTrue(resultStep10, "El filtro Tienda no mostro el texto Multiples seleccionados al cerrar el selector");

		u.close();
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
		return "*1. Credenciales para los perfiles CEDIS y Admin y ruta dinámica válida disponible en BD      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Valida el autocomplete y la multiselección del filtro Tienda en escenario Ruta Dinámica (UNIGIS) con perfiles CEDIS y Admin.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_065_AutocompleteMultiseleccionFiltroTiendaRutaDinamica_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}