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

/**DESCRIPCIÓN: Valida el modal Ver tiendas con listado de tiendas sin información y el cierre conservando estado en Liquidación de Ruta con perfiles CEDIS y Admin.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_071 Modal Ver tiendas con listado de tiendas sin información y cierre conservando estado
* 
*PREREQUISITOS:
*1. Credenciales para los perfiles CEDIS y Admin, y una Ruta Dinámica con al menos seis tiendas precargadas sin información en el rango
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Contar con usuarios activos para los perfiles CEDIS y Admin y una Ruta Dinámica en BD que tenga seis o más tiendas precargadas sin información en la fecha consultada.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_071_ModalVerTiendasListadoCierreConservandoEstado extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_071_ModalVerTiendasListadoCierreConservandoEstado_test(HashMap<String, String> data) throws Exception {
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
		addStep("Precondicion: obtener password, fecha y Ruta Dinámica con al menos seis tiendas sin información en el rango para el usuario " + user + " con perfil " + perfil);
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
		System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);
		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);
		boolean resultPreRuta = pc4.obtenerRutaPropuesta(user);
		assertTrue(resultPreRuta, "No se obtuvo una ruta dinámica base desde BD para el usuario: " + user);
		boolean resultPreFecha = pc4.obtenerFechaRegistroRutaPropuesta(user);
		assertTrue(resultPreFecha, "No se obtuvo fecha de registro válida desde BD para el usuario: " + user);
		String rutaConSeisTiendasSinInfo = rutaPage.obtenerRutaDinamicaConAlMenosSeisTiendasSinInfoEnRangoDesdeBD(PortalCometaMVP4.fechaFormateada);
		assertTrue(rutaConSeisTiendasSinInfo != null && !rutaConSeisTiendasSinInfo.isEmpty(), "No se obtuvo desde BD una Ruta Dinámica con al menos seis tiendas precargadas sin información en el rango de la fecha " + PortalCometaMVP4.fechaFormateada);

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
		addStep("Ejecutar una consulta por Ruta Dinámica " + rutaConSeisTiendasSinInfo + " que incluya al menos seis tiendas sin información en el rango con perfil " + perfil);
		boolean resultStep5Fechas = rutaPage.capturaFechasInicialFinalDesdeBD(PortalCometaMVP4.fechaFormateada, true);
		assertTrue(resultStep5Fechas, "No se pudieron capturar Fecha inicial y Fecha final desde BD correctamente");
		boolean resultStep5Selector = rutaPage.seleccionaRutaDinamicaEnSelectorFiltro(true);
		assertTrue(resultStep5Selector, "No se mostro Ruta Dinámica (UNIGIS) como valor elegido en el Selector de Filtro");
		boolean resultStep5Ruta = rutaPage.capturaRutaDinamicaUnigis(rutaConSeisTiendasSinInfo, true);
		assertTrue(resultStep5Ruta, "No se pudo capturar la Ruta Dinámica con seis o más tiendas sin información: " + rutaConSeisTiendasSinInfo);
		boolean resultStep5Tienda = rutaPage.validaFiltroTiendaActivo(true);
		assertTrue(resultStep5Tienda, "El filtro Tienda no se activo después de capturar la Ruta Dinámica con seis o más tiendas sin información");
		boolean resultStep5Consultar = rutaPage.validaBotonConsultarActivo(true);
		assertTrue(resultStep5Consultar, "El botón Consultar no se habilito después de capturar la Ruta Dinámica con seis o más tiendas sin información");
		boolean resultStep5Grid = rutaPage.consultaYValidaMensajeYBotonVerTiendasVisibleEnGrid(true);
		assertTrue(resultStep5Grid, "El grid no mostro el mensaje de tiendas sin información o el botón Ver tiendas después de consultar por Ruta Dinámica con seis o más tiendas sin información");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Hacer clic en el botón Ver tiendas y validar que el modal muestre el listado con formato CR Tienda + Nombre Tienda y scroll vertical con perfil " + perfil);
		boolean resultStep6 = rutaPage.haceClicBotonVerTiendasYValidaModalListado(true);
		assertTrue(resultStep6, "El modal Ver tiendas no mostro el listado esperado con formato CR Tienda + Nombre Tienda o scroll vertical disponible");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Hacer clic en la X del modal en color blanco y validar cierre conservando filtros, información previa y estado de la pantalla con perfil " + perfil);
		boolean resultStep7 = rutaPage.cierraModalVerTiendasConXBlancaYValidaEstadoConservado(true);
		assertTrue(resultStep7, "El modal Ver tiendas no cerro correctamente con la X en color blanco o no se conservaron los filtros, información previa y estado de la pantalla");
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
		return "*1. Credenciales para los perfiles CEDIS y Admin, y una Ruta Dinámica con al menos seis tiendas precargadas sin información en el rango      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Valida el modal Ver tiendas con listado de tiendas sin información y el cierre conservando estado en Liquidación de Ruta con perfiles CEDIS y Admin.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_071_ModalVerTiendasListadoCierreConservandoEstado_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
