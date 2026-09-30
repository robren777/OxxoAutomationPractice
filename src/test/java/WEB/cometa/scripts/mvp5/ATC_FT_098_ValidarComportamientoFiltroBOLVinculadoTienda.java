package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.devolucionesEspecialesPage;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCION: Validar que el filtro BOL se habilite al seleccionar Tienda, permita busqueda por Folio_BOL y muestre sugerencias asociadas a BOL_Completo.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_098 - Validar comportamiento del filtro BOL vinculado a Tienda
* 
*PREREQUISITOS:
*1. Credenciales para el perfil ADMIN
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Contar con usuario ADMIN activo, tiendas disponibles y registros BOL disponibles para Devoluciones Especiales.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Michelle Ceja
* @date Julio 2026
*/
public class ATC_FT_098_ValidarComportamientoFiltroBOLVinculadoTienda extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_098_ValidarComportamientoFiltroBOLVinculadoTienda_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		devolucionesEspecialesPage devEspecialesPage = new devolucionesEspecialesPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "ADMIN";

		/*
		 * PRECONDICION
		 ***************************************************************************/
		addStep("Precondicion: obtener password configurado para el usuario " + user + " con perfil " + perfil);
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
		System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);
		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);

		/*
		 *                                             PASO 1
		 ***************************************************************************/
		addStep("Acceder a la URL de inicio de sesion del portal CEDIS ");
		boolean resultStep1 = pc4.IngresarURL(url, true);
		assertTrue(resultStep1, "No se cargo correctamente la pantalla de inicio de sesion del portal CEDIS");
		pc4.PrivatePortal();

		/*
		 *                                             PASO 2 Y PASO 3
		 ***************************************************************************/
		addStep("Capturar usuario y password validos, y hacer clic en Entrar");
		boolean resultStep2 = pc4.IniciarSesionNoToken(user, password, true);
		assertTrue(resultStep2, "No se procesaron correctamente las credenciales validas del usuario ADMIN");

		/*
		 *                                             PASO 4 Y PASO 5
		 ***************************************************************************/
		addStep("Esperar la carga del portal y hacer clic en Portal COMETA");
		boolean resultStep4 = pc4.seleccionarPortalCometaQA(true);
		assertTrue(resultStep4, "No se mostro o no fue posible seleccionar Portal COMETA desde Access Control");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Hacer clic en el modulo CEDIS");
		boolean resultStep6 = pc4.clicBotonCometaCedis(true);
		assertTrue(resultStep6, "No se mostro la opcion Recepcion dentro del modulo CEDIS");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Ingresar a Recepcion y abrir la pantalla Devoluciones Especiales");
		boolean resultStep7Recepcion = pc4.ClicBotonRecepcionCedis(true);
		assertTrue(resultStep7Recepcion, "No se pudo ingresar a Recepcion de Transferencias y Devoluciones");
		boolean resultStep7DevEspeciales = devEspecialesPage.ingresarDevolucionesEspeciales(true);
		assertTrue(resultStep7DevEspeciales, "No se pudo abrir la pantalla Devoluciones Especiales");
		boolean resultStep7Filtros = devEspecialesPage.validarFiltrosDevolucionesEspecialesVisibles(true);
		assertTrue(resultStep7Filtros, "No se mostraron todos los filtros esperados de Devoluciones Especiales");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Seleccionar el filtro BOL y validar que no este disponible sin Tienda");
		boolean resultStep8 = devEspecialesPage.validarFiltroBolDeshabilitadoSinTienda(true);
		assertTrue(resultStep8, "El filtro BOL se encontro habilitado antes de seleccionar una Tienda");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Seleccionar una Tienda para habilitar el filtro BOL");
		boolean resultStep9Tienda = devEspecialesPage.seleccionarPrimeraOpcionFiltroTienda(true);
		assertTrue(resultStep9Tienda, "No fue posible seleccionar una Tienda para habilitar BOL");
		boolean resultStep9ValorTienda = devEspecialesPage.validarValorVisibleFiltroTienda(true);
		assertTrue(resultStep9ValorTienda, "La Tienda seleccionada no se mostro correctamente");
		boolean resultStep9Bol = devEspecialesPage.abrirFiltroBol(true);
		assertTrue(resultStep9Bol, "El filtro BOL no se habilito despues de seleccionar Tienda");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Escribir 3 digitos en el filtro BOL");
		boolean resultStep10 = devEspecialesPage.escribirTresDigitosValidosFiltroBolYValidarSugerencias(true);
		assertTrue(resultStep10, "No fue posible escribir 3 digitos validos o no se mostraron sugerencias BOL");

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("Revisar las sugerencias mostradas en BOL");
		boolean resultStep11 = devEspecialesPage.validarSugerenciasFiltroBolVisibles(true);
		assertTrue(resultStep11, "No se mantuvieron visibles las sugerencias BOL despues de escribir el prefijo de 3 digitos");

		/*
		 *                                             PASO 12
		 ***************************************************************************/
		addStep("Validar en DB origen de sugerencias antes de seleccionarlas");
		boolean resultStep12 = devEspecialesPage.validarSugerenciasFiltroBolContraBD(true);
		assertTrue(resultStep12, "Las sugerencias BOL visibles no coinciden contra Folio_BOL o BOL_Completo en xxcm_historico_correos");

		/*
		 *                                             PASO 13
		 ***************************************************************************/
		addStep("Seleccionar una sugerencia de BOL y validar en DB");
		boolean resultStep13Seleccion = devEspecialesPage.seleccionarPrimeraSugerenciaFiltroBol(true);
		assertTrue(resultStep13Seleccion, "No fue posible seleccionar una sugerencia del filtro BOL");
		boolean resultStep13Visible = devEspecialesPage.validarValorVisibleFiltroBol(true);
		assertTrue(resultStep13Visible, "El filtro BOL no mostro la sugerencia seleccionada");
		boolean resultStep13BD = devEspecialesPage.validarBolSeleccionadoContraBD(true);
		assertTrue(resultStep13BD, "El BOL seleccionado no coincide con la proyeccion tecnica consultada en BD");

		/*
		 *                                             PASO 14
		 ***************************************************************************/
		addStep("Validar que el boton Consultar no se encuentre activo");
		boolean resultStep14 = devEspecialesPage.validarBotonConsultarDeshabilitadoSinRangoFechas(true);
		assertTrue(resultStep14, "El boton Consultar se habilito sin seleccionar Rango de Fechas despues de filtrar por Tienda y BOL");

		/*
		 *                                             PASO 15
		 ***************************************************************************/
		addStep("Verificar que el usuario pueda seleccionar mas de un BOL si existe mas de una coincidencia");
		boolean resultStep15 = devEspecialesPage.seleccionarMultiplesValoresFiltroBol(true);
		assertTrue(resultStep15, "El filtro BOL no permitio seleccion multiple con las coincidencias disponibles");

		/*
		 *                                             PASO 16
		 ***************************************************************************/
		addStep("Validar que el boton Consultar no se encuentre activo con multiples BOL sin Rango de Fechas");
		boolean resultStep16 = devEspecialesPage.validarBotonConsultarDeshabilitadoSinRangoFechas(true);
		assertTrue(resultStep16, "El boton Consultar se habilito sin seleccionar Rango de Fechas despues de seleccionar multiples BOL");

	}

	@AfterMethod
	public void afterMethod() {
		if (u != null) {
			u.quit();
		}

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "*1. Credenciales para el perfil ADMIN      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Validar que el filtro BOL se habilite por Tienda y que las sugerencias de BOL_Completo coincidan con los campos consultados en DB.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_098_ValidarComportamientoFiltroBOLVinculadoTienda_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}