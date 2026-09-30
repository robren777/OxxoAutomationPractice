package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.rutaLiquidacionPage;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: Valida el mensaje "Algunas tiendas no se muestran porque no cuentan con información dentro del rango seleccionado" acompañado del botón Ver tiendas en el grid de Liquidación de Ruta con perfiles CEDIS y Admin, cubriendo el flujo por Tienda y por Ruta Dinámica (UNIGIS).
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_070 Mensaje y botón Ver tiendas para tiendas sin información en el rango
* 
*PREREQUISITOS:
*1. Credenciales para los perfiles CEDIS y Admin, tiendas con y sin información en el rango disponibles en BD y ruta dinámica con al menos una tienda precargada sin información en el rango
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Contar con usuarios activos para los perfiles CEDIS y Admin, tiendas con y sin información en el rango obtenidas desde BD y ruta dinámica con tiendas precargadas sin información.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_070_MensajeBotonVerTiendasSinInformacionRango extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_070_MensajeBotonVerTiendasSinInformacionRango_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		rutaLiquidacionPage rutaPage = new rutaLiquidacionPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = user.equals("6666666") ? "CEDIS" : user.equals("5555555") ? "Admin" : "Perfil no identificado";

		/*
		 * PRECONDICIÓN
		 ***************************************************************************/
		addStep("Precondicion: obtener password, fecha y ruta dinámica con tienda sin información configurados para el usuario " + user + " con perfil " + perfil);
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
		System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);
		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);
		boolean resultPreRuta = pc4.obtenerRutaPropuesta(user);
		assertTrue(resultPreRuta, "No se obtuvo una ruta dinámica base desde BD para el usuario: " + user);
		boolean resultPreFecha = pc4.obtenerFechaRegistroRutaPropuesta(user);
		assertTrue(resultPreFecha, "No se obtuvo fecha de registro válida desde BD para el usuario: " + user);
		String rutaConTiendaSinInfo = rutaPage.obtenerRutaDinamicaConTiendaSinInfoEnRangoDesdeBD(user, PortalCometaMVP4.fechaFormateada);
		assertTrue(rutaConTiendaSinInfo != null && !rutaConTiendaSinInfo.isEmpty(), "No se obtuvo desde BD una Ruta Dinámica con tienda precargada sin información en el rango de la fecha " + PortalCometaMVP4.fechaFormateada);

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
		addStep("Capturar Fecha inicial y Fecha final, elegir Tienda y seleccionar una tienda con información y una tienda sin información en el rango con perfil " + perfil);
		boolean resultStep5Fechas = rutaPage.capturaFechasInicialFinalDesdeBD(PortalCometaMVP4.fechaFormateada, true);
		assertTrue(resultStep5Fechas, "No se pudieron capturar Fecha inicial y Fecha final desde BD correctamente");
		boolean resultStep5Selector = rutaPage.seleccionaTiendaEnSelectorFiltro(true);
		assertTrue(resultStep5Selector, "No se mostro Tienda como valor elegido en el Selector de Filtro");
		boolean resultStep5Filtro = rutaPage.validaFiltroTiendaSinSeleccionYLimpiarDeshabilitado(true);
		assertTrue(resultStep5Filtro, "El filtro Tienda no desplego tiendas sin preseleccion al elegir Tienda en el Selector de Filtro");
		boolean resultStep5Marca = rutaPage.marcaPrimeraTiendaConInfoYUnaSinInfoEnRangoDesdeBD(user, PortalCometaMVP4.fechaFormateada, true);
		assertTrue(resultStep5Marca, "No se pudieron marcar una tienda con información y una tienda sin información en el rango consultado");
		boolean resultStep5Cierre = rutaPage.cierraSelectorTiendaYValidaTextoMultiple(true);
		assertTrue(resultStep5Cierre, "El filtro Tienda no conservo dos tiendas seleccionadas al cerrar el selector");
		boolean resultStep5Consultar = rutaPage.validaBotonConsultarActivo(true);
		assertTrue(resultStep5Consultar, "El botón Consultar no se habilito después de marcar dos tiendas en el flujo Tienda");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Hacer clic en Consultar y validar mensaje 'Algunas tiendas no se muestran...' acompanado del botón Ver tiendas con perfil " + perfil);
		boolean resultStep6 = rutaPage.consultaYValidaMensajeYBotonVerTiendasVisibleEnGrid(true);
		assertTrue(resultStep6, "El grid no mostro el mensaje de tiendas sin información o el botón Ver tiendas después de consultar con la tienda mixta");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Validar diseño, comportamiento y estilo del botón Ver tiendas contra el estándar de la aplicación con perfil " + perfil);
		boolean resultStep7 = rutaPage.validaEstiloBotonVerTiendasEstandarAplicacion(true);
		assertTrue(resultStep7, "El botón Ver tiendas no respeta el estándar visual definido para los botones de la aplicación");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Repetir la consulta seleccionando únicamente tiendas con información y validar que el mensaje y botón Ver tiendas queden ocultos con perfil " + perfil);
		boolean resultStep8 = rutaPage.repiteConsultaSoloTiendasConInformacionYValidaMensajeOculto(true);
		assertTrue(resultStep8, "No se pudo repetir la consulta únicamente con tiendas con información o el mensaje/botón Ver tiendas no se ocultó");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Cambiar el Selector de Filtro a Ruta Dinámica (UNIGIS), capturar " + rutaConTiendaSinInfo + " y validar mensaje y botón Ver tiendas al consultar por Ruta Dinámica con perfil " + perfil);
		boolean resultStep9Selector = rutaPage.seleccionaRutaDinamicaEnSelectorFiltro(true);
		assertTrue(resultStep9Selector, "No se mostro Ruta Dinámica (UNIGIS) como valor elegido en el Selector de Filtro para el paso 9");
		boolean resultStep9Ruta = rutaPage.capturaRutaDinamicaUnigis(rutaConTiendaSinInfo, true);
		assertTrue(resultStep9Ruta, "No se pudo capturar la Ruta Dinámica con tienda precargada sin información: " + rutaConTiendaSinInfo);
		boolean resultStep9Tienda = rutaPage.validaFiltroTiendaActivo(true);
		assertTrue(resultStep9Tienda, "El filtro Tienda no se activo después de capturar la Ruta Dinámica con tienda sin información");
		boolean resultStep9Consultar = rutaPage.validaBotonConsultarActivo(true);
		assertTrue(resultStep9Consultar, "El botón Consultar no se habilito después de capturar la Ruta Dinámica con tienda sin información");
		boolean resultStep9Grid = rutaPage.consultaYValidaMensajeYBotonVerTiendasVisibleEnGrid(true);
		assertTrue(resultStep9Grid, "El grid no mostro el mensaje o el botón Ver tiendas al consultar por Ruta Dinámica con tienda precargada sin información");

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
		return "*1. Credenciales para los perfiles CEDIS y Admin, tiendas con y sin información en el rango disponibles en BD y ruta dinámica con al menos una tienda precargada sin información en el rango      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Valida el mensaje 'Algunas tiendas no se muestran porque no cuentan con información dentro del rango seleccionado' acompañado del botón Ver tiendas en el grid de Liquidación de Ruta con perfiles CEDIS y Admin, cubriendo el flujo por Tienda y por Ruta Dinámica (UNIGIS).";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_070_MensajeBotonVerTiendasSinInformacionRango_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
