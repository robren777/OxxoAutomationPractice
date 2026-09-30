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

/**DESCRIPCION: Validar que el filtro BOL permanezca deshabilitado por defecto, se habilite al seleccionar Tienda y se limpie al quitar todas las tiendas.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_102 - Validar estado y limpieza del filtro BOL
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
public class ATC_FT_102_ValidarEstadoYLimpiezaFiltroBOL extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_102_ValidarEstadoYLimpiezaFiltroBOL_test(HashMap<String, String> data) throws Exception {
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
		addStep("Acceder a la URL de inicio de sesion del portal CEDIS");
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
		addStep("Verificar que el filtro BOL aparezca deshabilitado al entrar a la pantalla");
		boolean resultStep8 = devEspecialesPage.validarFiltroBolDeshabilitadoSinTienda(true);
		assertTrue(resultStep8, "El filtro BOL no se mostro deshabilitado por defecto al entrar a Devoluciones Especiales");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Seleccionar una tienda");
		boolean resultStep9 = devEspecialesPage.seleccionarPrimeraOpcionFiltroTienda(true);
		assertTrue(resultStep9, "No fue posible seleccionar una Tienda");
		boolean resultStep9Visible = devEspecialesPage.validarValorVisibleFiltroTienda(true);
		assertTrue(resultStep9Visible, "La Tienda seleccionada no se mostro correctamente");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Verificar que el filtro BOL se habilite");
		boolean resultStep10AbrirBol = devEspecialesPage.abrirFiltroBol(true);
		assertTrue(resultStep10AbrirBol, "El filtro BOL no se habilito despues de seleccionar Tienda");
		boolean resultStep10Sugerencias = devEspecialesPage.escribirTresDigitosValidosFiltroBolYValidarSugerencias(true);
		assertTrue(resultStep10Sugerencias, "El filtro BOL no mostro sugerencias despues de habilitarse");
		boolean resultStep10Seleccion = devEspecialesPage.seleccionarPrimeraSugerenciaFiltroBol(true);
		assertTrue(resultStep10Seleccion, "No fue posible seleccionar una sugerencia BOL para validar limpieza posterior");
		boolean resultStep10BolVisible = devEspecialesPage.validarValorVisibleFiltroBol(true);
		assertTrue(resultStep10BolVisible, "El filtro BOL no mostro el valor seleccionado correctamente");

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("Eliminar todas las tiendas seleccionadas y validar limpieza de BOL");
		boolean resultStep11 = devEspecialesPage.limpiarTiendaYValidarBolDeshabilitadoLimpio(true);
		assertTrue(resultStep11, "El filtro BOL no se deshabilito o no limpio sus valores despues de quitar todas las Tiendas");

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
		return "Validar que el filtro BOL permanezca deshabilitado por defecto, se habilite al seleccionar Tienda y se limpie al quitar todas las tiendas.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_102_ValidarEstadoYLimpiezaFiltroBOL_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}