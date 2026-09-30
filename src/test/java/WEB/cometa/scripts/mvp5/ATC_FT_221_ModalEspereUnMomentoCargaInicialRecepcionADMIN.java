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

/**DESCRIPCIÓN: Verificar que la pantalla Recepción de Transferencias y Devoluciones muestre el modal Espere un momento durante la carga inicial cuando aplique, cierre automáticamente dentro de 25 segundos y cargue los filtros sin No items found para el perfil ADMIN.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_221 CA01, CA02, CA03 - Verificar modal Espere un momento en carga inicial de Recepcion de Transferencias y Devoluciones - perfil ADMIN
* 
*PREREQUISITOS:
*1. Optimizacion de carga inicial implementada y desplegada en ambiente QA
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Usuario ADMIN 5555555 con acceso al modulo CEDIS
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Michelle Ceja
* @date Julio 2026
*/
public class ATC_FT_221_ModalEspereUnMomentoCargaInicialRecepcionADMIN extends BaseExecution {

	private SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_221_ModalEspereUnMomentoCargaInicialRecepcionADMIN_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolución del proceso de recibo en Tienda Cedis | MVP5");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		recepcionTransferenciasPage recepcionPage = new recepcionTransferenciasPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "ADMIN";

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
		addStep("Ingresar las credenciales correspondientes al perfil ADMIN para el inicio de sesion");
		boolean resultStep2 = pc4.IniciarSesionNoToken(user, password, true);
		assertTrue(resultStep2, "No se validaron correctamente las credenciales del usuario ADMIN");

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
		 *                                             PASO 7 AL PASO 17
		 ***************************************************************************/
		addStep("Registrar T1, dar clic en Recepcion y Devolucion, validar modal Espere un momento, cierre automatico <= 25 segundos y filtros cargados sin No items found");
		boolean resultStep7 = recepcionPage.ingresarRecepcionYValidarCargaInicial(25, user);
		assertTrue(resultStep7, "La carga inicial de Recepcion no cumplio con el modal opcional, tiempo maximo de 25 segundos o filtros esperados para ADMIN");
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
		return "*1. Optimizacion de carga inicial implementada y desplegada en ambiente QA      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Verificar que la pantalla Recepcion de Transferencias y Devoluciones muestre el modal Espere un momento durante la carga inicial cuando aplique, cierre automaticamente dentro de 25 segundos y cargue los filtros sin No items found para el perfil ADMIN.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_221_ModalEspereUnMomentoCargaInicialRecepcionADMIN_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}