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

/**DESCRIPCIÓN: Valida la consulta por Ruta Dinámica (UNIGIS) con tiendas personalizadas y encabezado con palabra personalizada con perfiles CEDIS y Admin.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_067 Consulta por Ruta Dinámica (UNIGIS) con tiendas personalizadas y encabezado con palabra personalizada
* 
*PREREQUISITOS:
*1. Credenciales para los perfiles CEDIS y Admin, ruta dinámica válida disponible en BD y tiendas asociadas para personalizar la consulta
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Contar con usuarios activos para los perfiles CEDIS y Admin y ruta dinámica válida obtenida desde BD.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_067_ConsultaRutaDinamicaTiendasPersonalizadasEncabezadoPersonalizada extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_067_ConsultaRutaDinamicaTiendasPersonalizadasEncabezadoPersonalizada_test(HashMap<String, String> data) throws Exception {
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
		addStep("Precondicion: obtener password y escenario de Ruta Dinámica de origen CEDIS dentro de los cuatro días visibles, con entre dos y cincuenta tiendas y transferencias para el usuario " + user + " con perfil " + perfil);
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
		addStep("Capturar Fecha inicial y Fecha final del escenario BD; elegir Ruta Dinámica (UNIGIS), capturar la ruta " + PortalCometaMVP4.rutaPropuesta + " y abrir Tienda con perfil " + perfil);
		boolean resultStep5Fechas = rutaPage.capturaFechasInicialFinalDesdeBD(PortalCometaMVP4.fechaFormateada, true);
		assertTrue(resultStep5Fechas, "No se pudieron capturar Fecha inicial y Fecha final desde BD correctamente");
		boolean resultStep5Selector = rutaPage.seleccionaRutaDinamicaEnSelectorFiltro(true);
		assertTrue(resultStep5Selector, "No se mostro Ruta Dinámica (UNIGIS) como valor elegido en el Selector de Filtro");
		boolean resultStep5Ruta = rutaPage.capturaRutaDinamicaUnigis(PortalCometaMVP4.rutaPropuesta, true);
		assertTrue(resultStep5Ruta, "No se pudo ingresar la Ruta Dinámica (UNIGIS) correctamente");
		boolean resultStep5Tienda = rutaPage.validaFiltroTiendaActivo(true);
		assertTrue(resultStep5Tienda, "El filtro Tienda no se activo después de capturar la Ruta Dinámica");
		boolean resultStep5Listado = rutaPage.validaTiendasPrecargadasRutaDinamicaContraBD(PortalCometaMVP4.rutaPropuesta, true);
		assertTrue(resultStep5Listado, "No se mostro el listado de tiendas con las asociadas a la ruta precargadas o no coincidieron contra BD");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Desmarcar el checkbox de una tienda precargada por la ruta dinámica con perfil " + perfil);
		boolean resultStep6 = rutaPage.desmarcaPrimeraTiendaPrecargadaRutaDinamica(true);
		assertTrue(resultStep6, "No se pudo desmarcar una tienda precargada por la Ruta Dinámica");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Marcar el checkbox de una tienda adicional fuera de la ruta dinámica con perfil " + perfil);
		boolean resultStep7 = rutaPage.marcaTiendaAdicionalFueraRutaDinamica(PortalCometaMVP4.rutaPropuesta, true);
		assertTrue(resultStep7, "No se pudo marcar una tienda adicional fuera de la Ruta Dinámica");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Cerrar el selector haciendo clic fuera del componente y validar texto Multiples seleccionados con perfil " + perfil);
		boolean resultStep8Cierre = rutaPage.cierraSelectorTiendaYValidaTextoMultiple(true);
		assertTrue(resultStep8Cierre, "El filtro Tienda no conservo selección múltiple después de personalizar tiendas");
		boolean resultStep8Consultar = rutaPage.validaBotonConsultarActivo(true);
		assertTrue(resultStep8Consultar, "El botón Consultar no permanecio habilitado después de personalizar tiendas");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Hacer clic en Consultar y validar encabezado Ruta con palabra personalizada con perfil " + perfil);
		boolean resultStep9 = rutaPage.consultaRutaDinamicaYValidaEncabezadoPersonalizada(PortalCometaMVP4.rutaPropuesta, true);
		assertTrue(resultStep9, "El grid no mostro el encabezado Ruta personalizada esperado después de modificar tiendas");
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
		return "*1. Credenciales para los perfiles CEDIS y Admin, ruta dinámica válida disponible en BD y tiendas asociadas para personalizar la consulta      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Valida la consulta por Ruta Dinámica (UNIGIS) con tiendas personalizadas y encabezado con palabra personalizada con perfiles CEDIS y Admin.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_067_ConsultaRutaDinamicaTiendasPersonalizadasEncabezadoPersonalizada_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}