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

/**DESCRIPCIÓN: Validar que el filtro BOL de Devoluciones Especiales acepte busqueda por Folio_BOL, muestre sugerencias con BOL_Completo y que el valor visible coincida con la proyeccion tecnica consultada en base de datos.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_086 - Validar busqueda y sugerencias del filtro BOL
* 
*PREREQUISITOS:
*1. Credenciales para el perfil ADMIN
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Contar con informacion disponible en public.xxcm_historico_correos con BOL_Completo/Folio_BOL.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Michelle Ceja
* @date Julio 2026
*/
public class ATC_FT_086_ValidarBusquedaYSugerenciasFiltroBOL extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_086_ValidarBusquedaYSugerenciasFiltroBOL_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolución del proceso de recibo en Tienda Cedis | MVP5");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		devolucionesEspecialesPage devEspecialesPage = new devolucionesEspecialesPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "ADMIN";

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
		addStep("Abrir el filtro BOL");
		boolean resultStep8 = devEspecialesPage.abrirFiltroBol(true);
		assertTrue(resultStep8, "El filtro BOL no se desplego correctamente");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Escribir 3 digitos validos en el filtro BOL y validar sugerencias");
		boolean resultStep10 = devEspecialesPage.escribirTresDigitosValidosFiltroBolYValidarSugerencias(true);
		assertTrue(resultStep10, "El filtro BOL no mostro sugerencias para los 3 digitos validos");

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("Seleccionar una sugerencia de BOL");
		boolean resultStep11 = devEspecialesPage.seleccionarPrimeraSugerenciaFiltroBol(true);
		assertTrue(resultStep11, "No fue posible seleccionar una sugerencia de BOL");

		/*
		 *                                             PASO 12
		 ***************************************************************************/
		addStep("Verificar el valor mostrado en el control BOL");
		boolean resultStep12 = devEspecialesPage.validarValorVisibleFiltroBol(true);
		assertTrue(resultStep12, "El control BOL no mostro el BOL_Completo seleccionado");

		/*
		 *                                             PASO 13
		 ***************************************************************************/
		addStep("Validar en DB el origen de xxcm_historico_correos.body y la concatenacion Folio_BOL a BOL_Completo");
		boolean resultStep13 = devEspecialesPage.validarBolSeleccionadoContraBD(true);
		assertTrue(resultStep13, "El BOL_Completo mostrado no coincide con la proyeccion tecnica consultada en BD");

		/*
		 *                                             PASO 14
		 ***************************************************************************/
		addStep("Escribir un valor sin coincidencias en el filtro BOL y validar que no se muestren sugerencias");
		boolean resultStep14 = devEspecialesPage.escribirBolSinCoincidenciasYValidarSinSugerencias(true);
		assertTrue(resultStep14, "El filtro BOL mostro sugerencias para un valor sin coincidencias");

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
		return "Validar que el filtro BOL de Devoluciones Especiales acepte busqueda por Folio_BOL, muestre sugerencias con BOL_Completo y que el valor visible coincida con la proyeccion tecnica consultada en base de datos.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_086_ValidarBusquedaYSugerenciasFiltroBOL_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}