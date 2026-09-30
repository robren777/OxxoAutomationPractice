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

/**DESCRIPCIÓN: Valida la consulta por Tienda sin Ruta Dinámica (UNIGIS) y encabezado Ruta: Personalizada con perfiles CEDIS y Admin.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_068 Consulta por Tienda sin Ruta Dinámica (UNIGIS) y encabezado Personalizada
* 
*PREREQUISITOS:
*1. Credenciales para los perfiles CEDIS y Admin y tiendas disponibles para consultar en Liquidación de Ruta
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Contar con usuarios activos para los perfiles CEDIS y Admin y tiendas disponibles en el filtro Tienda.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_068_ConsultaTiendaSinRutaDinamicaEncabezadoPersonalizada extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_068_ConsultaTiendaSinRutaDinamicaEncabezadoPersonalizada_test(HashMap<String, String> data) throws Exception {
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
		addStep("Precondicion: obtener password y fecha con informacion CEDIS configurados para el usuario " + user + " con perfil " + perfil);
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
		System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);
		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);
		String fechaConInformacion = rutaPage.obtenerFechaConInformacionTiendaDesdeBD(user);
		assertTrue(fechaConInformacion != null && !fechaConInformacion.isEmpty(), "No se obtuvo una fecha con informacion CEDIS desde BD para consultar por Tienda");

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
		boolean resultStep4Modal = rutaPage.esperaModalEspereUnMomentoLiquidacionRutaDesaparece(true);
		assertTrue(resultStep4Modal, "El modal Espere un momento no desaparecio antes de seleccionar Liquidacion de Ruta");
		boolean resultStep4Liquidacion = pc4.opcLiquidacionRuta();
		assertTrue(resultStep4Liquidacion, "No se pudo seleccionar la opcion Liquidacion de Ruta");
		boolean resultStep4ModalCargaLiquidacion = rutaPage.esperaModalEspereUnMomentoLiquidacionRutaDesaparece(true);
		assertTrue(resultStep4ModalCargaLiquidacion, "El modal Espere un momento no desaparecio al cargar Liquidacion de Ruta");
		boolean resultStep4Pantalla = pc4.scLiquidacionRuta();
		assertTrue(resultStep4Pantalla, "No se mostro correctamente la pantalla Liquidacion de Ruta");

		/*
		 *                                             PASO 5
		 ***************************************************************************/
		addStep("Capturar Fecha inicial y Fecha final con informacion desde BD, elegir Tienda y abrir el filtro Tienda con perfil " + perfil);
		boolean resultStep5Fechas = rutaPage.capturaFechasInicialFinalDesdeBD(fechaConInformacion, true);
		assertTrue(resultStep5Fechas, "No se pudieron capturar Fecha inicial y Fecha final con informacion desde BD correctamente");
		boolean resultStep5Selector = rutaPage.seleccionaTiendaEnSelectorFiltro(true);
		assertTrue(resultStep5Selector, "No se mostro Tienda como valor elegido en el Selector de Filtro");
		boolean resultStep5Filtro = rutaPage.validaFiltroTiendaSinSeleccionYLimpiarDeshabilitado(true);
		assertTrue(resultStep5Filtro, "El filtro Tienda no desplego tiendas sin preseleccion para el flujo Tienda");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Marcar el checkbox de dos tiendas con informacion dentro del rango de fechas en el filtro Tienda con perfil " + perfil);
		boolean resultStep6 = rutaPage.marcaDosTiendasConInformacionEnRangoDesdeBD(user, true);
		assertTrue(resultStep6, "No se pudieron marcar dos tiendas con informacion dentro del rango de fechas consultado");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Cerrar el selector haciendo clic fuera del componente y validar texto Multiples seleccionados con perfil " + perfil);
		boolean resultStep7Cierre = rutaPage.cierraSelectorTiendaYValidaTextoMultiple(true);
		assertTrue(resultStep7Cierre, "El filtro Tienda no conservo dos tiendas seleccionadas al cerrar el selector");
		boolean resultStep7Consultar = rutaPage.validaBotonConsultarActivo(true);
		assertTrue(resultStep7Consultar, "El botón Consultar no se habilito después de seleccionar dos tiendas");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Hacer clic en Consultar y validar encabezado Ruta: Personalizada con perfil " + perfil);
		boolean resultStep8 = rutaPage.consultaTiendaYValidaEncabezadoPersonalizada(true);
		assertTrue(resultStep8, "El grid no mostro el encabezado Ruta: Personalizada al consultar por Tienda");
		

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Validar que debajo del encabezado Ruta: Personalizada aparezcan los renglones tienda-transferencia con formato Tienda: {id_retek} {CR} {Nombre}. Transferencia: {folio} con perfil " + perfil);
		boolean resultStep9 = rutaPage.validaTiendasVisiblesEnGridDebajoEncabezado(null, true);
		assertTrue(resultStep9, "El grid no muestra los renglones tienda-transferencia esperados debajo de Ruta: Personalizada");

	

		
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
		return "*1. Credenciales para los perfiles CEDIS y Admin y tiendas disponibles para consultar en Liquidación de Ruta      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Valida la consulta por Tienda sin Ruta Dinámica (UNIGIS) y encabezado Ruta: Personalizada con perfiles CEDIS y Admin.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_068_ConsultaTiendaSinRutaDinamicaEncabezadoPersonalizada_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}