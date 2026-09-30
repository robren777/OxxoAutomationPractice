package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.recepcionTransferenciasPage;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: Verificar que el mensaje No items found se muestre correctamente en el filtro Tienda solo cuando la carga finalizó y no existen registros para la búsqueda, con perfil CEDIS.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_226 CA03 - Verificar que No items found se muestra correctamente solo cuando la carga finalizo y no existen registros en el filtro - perfil CEDIS
* 
*PREREQUISITOS:
*1. Optimizacion implementada en ambiente QA
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Usuario CEDIS 6666666 con acceso al modulo CEDIS
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Michelle Ceja
* @date Julio 2026
*/
public class ATC_FT_226_NoItemsFoundPostCargaFiltroTiendaCEDIS extends BaseExecution {

	private SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_226_NoItemsFoundPostCargaFiltroTiendaCEDIS_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolución del proceso de recibo en Tienda Cedis | MVP5");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		recepcionTransferenciasPage recepcionPage = new recepcionTransferenciasPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "CEDIS";
		String valorInexistente = "3299";

		/*
		 * PRECONDICIÓN
		 ***************************************************************************/
		addStep("Precondicion: obtener password configurado para el usuario " + user + " con perfil " + perfil + " y confirmar optimizacion desplegada en QA");
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
		System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);
		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);

		/*
		 *                                             PASO 1
		 ***************************************************************************/
		addStep("Ingresar la URL de Portal COMETA en el navegador");
		boolean resultStep1 = pc4.IngresarURL(url, true);
		assertTrue(resultStep1, "No se cargo correctamente la pantalla de inicio de sesion del portal Access Control");
		pc4.PrivatePortal();

		/*
		 *                                             PASO 2, PASO 3 Y PASO 4
		 ***************************************************************************/
		addStep("Ingresar las credenciales correspondientes al perfil CEDIS para el inicio de sesion");
		boolean resultStep2 = pc4.IniciarSesionNoToken(user, password, true);
		assertTrue(resultStep2, "No se validaron correctamente las credenciales del usuario CEDIS");

		/*
		 *                                             PASO 5
		 ***************************************************************************/
		addStep("Dar clic en la tarjeta Portal COMETA");
		boolean resultStep5 = pc4.seleccionarPortalCometaQA(true);
		assertTrue(resultStep5, "No se mostro o no fue posible seleccionar Portal COMETA desde Access Control");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Dar clic en la tarjeta CEDIS");
		boolean resultStep6 = pc4.clicBotonCometaCedis(true);
		assertTrue(resultStep6, "No se mostro el modulo CEDIS con la sub-tarjeta Recepcion y Devolucion");

		/*
		 *                                             PASO 7 AL PASO 9
		 ***************************************************************************/
		addStep("Ingresar a Recepcion y Devolucion y esperar que el modal Espere un momento se cierre con filtros disponibles");
		boolean resultStep7 = recepcionPage.ingresarRecepcionYValidarCargaInicialPlazaTienda(25);
		assertTrue(resultStep7, "Recepcion de Transferencias y Devoluciones no quedo disponible para validar No items found post carga");

		/*
		 *                                             PASO 10 Y PASO 11
		 ***************************************************************************/
		addStep("Seleccionar Plaza, escribir valor inexistente en Tienda y validar No items found solo despues de la carga finalizada");
		boolean resultStep10 = recepcionPage.validarNoItemsFoundTiendaPostCarga(valorInexistente);
		assertTrue(resultStep10, "No se mostro No items found en Tienda con la carga finalizada o aun existia overlay activo");
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
		return "*1. Optimizacion implementada en ambiente QA      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Verificar que el mensaje No items found se muestre correctamente en el filtro Tienda solo cuando la carga finalizó y no existen registros para la búsqueda, con perfil CEDIS.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_226_NoItemsFoundPostCargaFiltroTiendaCEDIS_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}