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

/**DESCRIPCIÓN: Valida la consulta por Ruta Dinámica (UNIGIS) con tiendas precargadas y encabezado sin personalizada con perfiles CEDIS y Admin.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_064 Consulta por Ruta Dinámica (UNIGIS) con tiendas precargadas y encabezado sin personalizada
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
public class ATC_FT_064_ConsultaRutaDinamicaTiendasPrecargadasEncabezadoSinPersonalizada extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_064_ConsultaRutaDinamicaTiendasPrecargadasEncabezadoSinPersonalizada_test(HashMap<String, String> data) throws Exception {
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
		addStep("Capturar Fecha inicial y Fecha final del escenario BD; elegir Ruta Dinámica (UNIGIS) y capturar la ruta " + PortalCometaMVP4.rutaPropuesta + " con entre dos y cincuenta tiendas y transferencias, con perfil " + perfil);
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

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Abrir el filtro Tienda sin modificar la selección automatica y validar tiendas precargadas contra BD con perfil " + perfil);
		boolean resultStep6 = rutaPage.validaTiendasPrecargadasRutaDinamicaContraBD(PortalCometaMVP4.rutaPropuesta, true);
		assertTrue(resultStep6, "Las tiendas precargadas de Ruta Dinámica no coinciden contra BD o no aparecen antes del resto de tiendas");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Cerrar el selector haciendo clic fuera del componente y revisar el texto Multiples seleccionados con perfil " + perfil);
		boolean resultStep7 = rutaPage.cierraSelectorTiendaYValidaTextoMultiple(true);
		assertTrue(resultStep7, "El filtro Tienda no mostro el texto Multiples seleccionados con la selección automatica de tiendas");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Hacer clic en Consultar sin modificar la selección automatica y validar encabezado Ruta sin personalizada con perfil " + perfil);
		boolean resultStep8 = rutaPage.consultaRutaDinamicaYValidaEncabezadoSinPersonalizada(PortalCometaMVP4.rutaPropuesta, true);
		assertTrue(resultStep8, "El grid no mostro el encabezado Ruta esperado o incluyo la palabra personalizada");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Validar que el total de tiendas mostrado en el grid corresponda contra BD para la Ruta Dinámica y fecha consultada con perfil " + perfil);
		boolean resultStep9 = rutaPage.validaGridTiendasRutaDinamicaContraBD(PortalCometaMVP4.rutaPropuesta, PortalCometaMVP4.fechaFormateada, true);
		assertTrue(resultStep9, "El total de tiendas mostrado en el grid no corresponde contra BD para la Ruta Dinámica y fecha consultada");

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
		return "Valida la consulta por Ruta Dinámica (UNIGIS) con tiendas precargadas y encabezado sin personalizada con perfiles CEDIS y Admin.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_064_ConsultaRutaDinamicaTiendasPrecargadasEncabezadoSinPersonalizada_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
