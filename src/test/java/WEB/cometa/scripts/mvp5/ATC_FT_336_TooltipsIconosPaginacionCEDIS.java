package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.devolucionesEspecialesPage;
import WEB.cometa.om.generaDuplicadosDevEsp;
import WEB.cometa.om.recepcionTransferenciasPage;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: Validar que cada icono de navegacion del paginador del grid de Devoluciones Especiales
 * muestre el tooltip especifico correspondiente al posicionar el cursor sobre el para el perfil CEDIS.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_336 CA06 - Tooltips en iconos de paginacion usuario CEDIS
* 
*PREREQUISITOS:
*1. Portal COMETA desplegado en ambiente QA con datos disponibles en Devoluciones Especiales
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Usuario CEDIS con acceso al modulo CEDIS
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Michelle Ceja
* @date Agosto 2026
*/
public class ATC_FT_336_TooltipsIconosPaginacionCEDIS extends BaseExecution {

	private SeleniumUtil u;
	private generaDuplicadosDevEsp duplicados;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_336_TooltipsIconosPaginacionCEDIS_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolución del proceso de recibo en Tienda Cedis | MVP5");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		recepcionTransferenciasPage recepcionPage = new recepcionTransferenciasPage(u, testCase);
		devolucionesEspecialesPage devEspecialesPage = new devolucionesEspecialesPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "CEDIS";

		/*
		 * PRECONDICIÓN
		 ***************************************************************************/
		addStep("Precondicion: obtener password configurado para el usuario " + user + " con perfil " + perfil);
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
		System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);
		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);

		duplicados = new generaDuplicadosDevEsp(testCase);
		boolean precondicionOk = duplicados.prepararMinimoRegistros(user, data.get("minimoRegistros"));
		assertTrue(precondicionOk, "No fue posible garantizar el minimo de registros requerido en Devoluciones Especiales");

		/*
		 *                                             PASO 1
		 ***************************************************************************/
		addStep("Ingresar la URL de Portal COMETA en el navegador");
		boolean resultStep1 = pc4.IngresarURL(url, true);
		assertTrue(resultStep1, "No se cargo correctamente la pantalla de inicio de sesion del portal Access Control");
		pc4.PrivatePortal();

		/*
		 *                                             PASO 2
		 ***************************************************************************/
		addStep("Ingresar las credenciales del perfil CEDIS");
		boolean resultStep2 = pc4.IniciarSesionNoToken(user, password, true);
		assertTrue(resultStep2, "No se validaron correctamente las credenciales del perfil CEDIS");

		/*
		 *                                             PASO 3
		 ***************************************************************************/
		addStep("Ingresar a Cedis");
		boolean resultStep3 = pc4.seleccionarPortalCometaQA(true);
		assertTrue(resultStep3, "No se mostro o no fue posible seleccionar Portal COMETA desde Access Control");

		/*
		 *                                             PASO 4
		 ***************************************************************************/
		addStep("Ingresar al modulo Recepcion y Devolucion");
		boolean resultStep4a = pc4.clicBotonCometaCedis(true);
		assertTrue(resultStep4a, "No se mostro el modulo CEDIS con la sub-tarjeta Recepcion y Devolucion");
		boolean resultStep4b = recepcionPage.ingresarRecepcionYEsperarDisponible(25);
		assertTrue(resultStep4b, "La pantalla Recepcion de Transferencias y Devoluciones no cargo correctamente");

		/*
		 *                                             PASO 5
		 ***************************************************************************/
		addStep("Dar clic en el boton Devoluciones Especiales");
		boolean resultStep5 = devEspecialesPage.ingresarDevolucionesEspeciales(true);
		assertTrue(resultStep5, "CA06 fallido: no fue posible navegar a la pantalla Devoluciones Especiales");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Validar que se muestre la pantalla de Devoluciones Especiales");
		boolean resultStep6 = devEspecialesPage.validarFiltrosDevolucionesEspecialesVisibles(true);
		assertTrue(resultStep6, "CA06 fallido: la pantalla Devoluciones Especiales no se visualiza correctamente");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Posicionar el cursor sobre |<");
		boolean resultStep7 = devEspecialesPage.validarTooltipBotonNavegacionGridDevEspeciales("primera", "Primera p\u00e1gina", true);
		assertTrue(resultStep7, "CA06 fallido: el tooltip del icono |< no muestra el texto 'Primera pagina' en el grid de Devoluciones Especiales");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Posicionar el cursor sobre <");
		boolean resultStep8 = devEspecialesPage.validarTooltipBotonNavegacionGridDevEspeciales("anterior", "P\u00e1gina anterior", true);
		assertTrue(resultStep8, "CA06 fallido: el tooltip del icono < no muestra el texto 'Pagina anterior' en el grid de Devoluciones Especiales");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Posicionar el cursor sobre >");
		boolean resultStep9 = devEspecialesPage.validarTooltipBotonNavegacionGridDevEspeciales("siguiente", "P\u00e1gina siguiente", true);
		assertTrue(resultStep9, "CA06 fallido: el tooltip del icono > no muestra el texto 'Pagina siguiente' en el grid de Devoluciones Especiales");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Posicionar el cursor sobre >|");
		boolean resultStep10 = devEspecialesPage.validarTooltipBotonNavegacionGridDevEspeciales("ultima", "\u00daltima p\u00e1gina", true);
		assertTrue(resultStep10, "CA06 fallido: el tooltip del icono >| no muestra el texto 'Ultima pagina' en el grid de Devoluciones Especiales");
	}

	@AfterMethod
	public void afterMethod() {
		if (duplicados != null) {
			duplicados.eliminarDuplicados();
		}
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
		return "*1. Portal COMETA desplegado en ambiente QA con datos disponibles en Devoluciones Especiales\r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Validar que cada icono de navegacion del paginador del grid de Devoluciones Especiales muestre el tooltip especifico correspondiente al posicionar el cursor sobre el para el perfil CEDIS.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_336_TooltipsIconosPaginacionCEDIS_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
