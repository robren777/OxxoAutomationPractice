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

/**DESCRIPCIÓN: Valida la actualización del encabezado del grid al realizar nuevas consultas en Liquidación de Ruta con perfiles CEDIS y Admin, cubriendo el encabezado Ruta sin personalizada, Ruta con palabra personalizada y Ruta: Personalizada.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_069 Actualización del encabezado del grid al realizar nuevas consultas
* 
*PREREQUISITOS:
*1. Credenciales para los perfiles CEDIS y Admin, ruta dinámica válida disponible en BD y tiendas con información en el rango consultado
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Contar con usuarios activos para los perfiles CEDIS y Admin, ruta dinámica válida obtenida desde BD y tiendas con información en el rango de fechas.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_069_ActualizacionEncabezadoGridNuevasConsultas extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_069_ActualizacionEncabezadoGridNuevasConsultas_test(HashMap<String, String> data) throws Exception {
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
		addStep("Precondicion: obtener password, ruta dinámica y fecha configurados para el usuario " + user + " con perfil " + perfil);
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
		System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);
		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);
		boolean resultPreEscenario = rutaPage.obtenerEscenarioRutaDinamicaConMultiplesTiendasParaUsuario(user);
		assertTrue(resultPreEscenario, "No se obtuvo desde BD una Ruta Dinámica de origen CEDIS, con fecha visible, entre dos y cincuenta tiendas y transferencias para el usuario: " + user);

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
		addStep("Capturar Fecha inicial y Fecha final desde BD; elegir Ruta Dinámica (UNIGIS), capturar ruta válida y consultar sin modificar tiendas con perfil " + perfil);
		boolean resultStep5Fechas = rutaPage.capturaFechasInicialFinalDesdeBD(PortalCometaMVP4.fechaFormateada, true);
		assertTrue(resultStep5Fechas, "No se pudieron capturar Fecha inicial y Fecha final desde BD correctamente");
		boolean resultStep5Selector = rutaPage.seleccionaRutaDinamicaEnSelectorFiltro(true);
		assertTrue(resultStep5Selector, "No se mostro Ruta Dinámica (UNIGIS) como valor elegido en el Selector de Filtro");
		boolean resultStep5Ruta = rutaPage.capturaRutaDinamicaUnigis(PortalCometaMVP4.rutaPropuesta, true);
		assertTrue(resultStep5Ruta, "No se pudo ingresar la Ruta Dinámica (UNIGIS) correctamente");
		boolean resultStep5Tienda = rutaPage.validaFiltroTiendaActivo(true);
		assertTrue(resultStep5Tienda, "El filtro Tienda no se activo después de capturar la Ruta Dinámica");
		boolean resultStep5Consultar = rutaPage.validaBotonConsultarActivo(true);
		assertTrue(resultStep5Consultar, "El botón Consultar no se habilito después de capturar la Ruta Dinámica");
		boolean resultStep5Encabezado = rutaPage.consultaRutaDinamicaYValidaEncabezadoSinPersonalizada(PortalCometaMVP4.rutaPropuesta, true);
		assertTrue(resultStep5Encabezado, "El grid no mostro el encabezado Ruta esperado sin la palabra personalizada al consultar por Ruta Dinámica sin modificar tiendas");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Modificar el filtro Tienda agregando una tienda adicional fuera de la Ruta Dinámica y volver a Consultar validando encabezado con palabra personalizada con perfil " + perfil);
		boolean resultStep6Abrir = rutaPage.abreFiltroTiendaYValidaOpcionesVisibles(true);
		assertTrue(resultStep6Abrir, "No se pudo abrir el filtro Tienda para modificar la selección antes de la segunda consulta");
		boolean resultStep6Adicional = rutaPage.marcaTiendaAdicionalFueraRutaDinamica(PortalCometaMVP4.rutaPropuesta, true);
		assertTrue(resultStep6Adicional, "No se pudo marcar una tienda adicional fuera de la Ruta Dinámica para personalizar la consulta");
		boolean resultStep6Cierre = rutaPage.cierraSelectorTiendaYValidaTextoMultiple(true);
		assertTrue(resultStep6Cierre, "El filtro Tienda no conservo selección múltiple después de agregar la tienda adicional");
		boolean resultStep6Consultar = rutaPage.validaBotonConsultarActivo(true);
		assertTrue(resultStep6Consultar, "El botón Consultar no permanecio habilitado después de agregar la tienda adicional");
		boolean resultStep6Encabezado = rutaPage.consultaRutaDinamicaYValidaEncabezadoPersonalizada(PortalCometaMVP4.rutaPropuesta, true);
		assertTrue(resultStep6Encabezado, "El grid no mostro el encabezado Ruta con la palabra personalizada después de agregar la tienda adicional");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Cambiar el Selector de Filtro a Tienda, seleccionar dos tiendas y volver a Consultar validando encabezado Ruta: Personalizada con perfil " + perfil);
		boolean resultStep7Selector = rutaPage.seleccionaTiendaEnSelectorFiltro(true);
		assertTrue(resultStep7Selector, "No se mostro Tienda como valor elegido en el Selector de Filtro para la tercera consulta");
		boolean resultStep7Filtro = rutaPage.validaFiltroTiendaSinSeleccionYLimpiarDeshabilitado(true);
		assertTrue(resultStep7Filtro, "El filtro Tienda no desplego tiendas sin preseleccion al cambiar el Selector de Filtro a Tienda");
		boolean resultStep7Marca = rutaPage.marcaDosTiendasConInformacionEnRangoDesdeBD(user, true);
		assertTrue(resultStep7Marca, "No se pudieron marcar dos tiendas con informacion dentro del rango de fechas consultado");
		boolean resultStep7Cierre = rutaPage.cierraSelectorTiendaYValidaTextoMultiple(true);
		assertTrue(resultStep7Cierre, "El filtro Tienda no conservo dos tiendas seleccionadas al cerrar el selector para la tercera consulta");
		boolean resultStep7Consultar = rutaPage.validaBotonConsultarActivo(true);
		assertTrue(resultStep7Consultar, "El botón Consultar no se habilito después de seleccionar dos tiendas en el flujo Tienda");
		boolean resultStep7Encabezado = rutaPage.consultaTiendaYValidaEncabezadoPersonalizada(true);
		assertTrue(resultStep7Encabezado, "El grid no mostro el encabezado Ruta: Personalizada al consultar por Tienda con dos tiendas seleccionadas");

		
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
		return "*1. Credenciales para los perfiles CEDIS y Admin, ruta dinámica válida disponible en BD y tiendas con información en el rango consultado      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Valida la actualización del encabezado del grid al realizar nuevas consultas en Liquidación de Ruta con perfiles CEDIS y Admin, cubriendo el encabezado Ruta sin personalizada, Ruta con palabra personalizada y Ruta: Personalizada.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_069_ActualizacionEncabezadoGridNuevasConsultas_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
