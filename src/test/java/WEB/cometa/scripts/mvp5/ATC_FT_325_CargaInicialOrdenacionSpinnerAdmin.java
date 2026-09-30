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

/**DESCRIPCIÓN: Validar que la carga inicial de la pantalla Devoluciones Especiales muestre 20 registros
 * ordenados de forma descendente, el spinner durante la consulta y el contador 1 - 20 of Z para el perfil Admin.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_325 CA01 - Carga inicial con ordenacion y spinner usuario Admin
* 
*PREREQUISITOS:
*1. Portal COMETA desplegado en ambiente QA con datos disponibles en Devoluciones Especiales
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Usuario Admin con acceso al modulo CEDIS
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Michelle Ceja
* @date Agosto 2026
*/
public class ATC_FT_325_CargaInicialOrdenacionSpinnerAdmin extends BaseExecution {

	private SeleniumUtil u;
	private generaDuplicadosDevEsp duplicados;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_325_CargaInicialOrdenacionSpinnerAdmin_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolución del proceso de recibo en Tienda Cedis | MVP5");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		recepcionTransferenciasPage recepcionPage = new recepcionTransferenciasPage(u, testCase);
		devolucionesEspecialesPage devEspecialesPage = new devolucionesEspecialesPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "Admin";

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
		addStep("Ingresar las credenciales del perfil Admin");
		boolean resultStep2 = pc4.IniciarSesionNoToken(user, password, true);
		assertTrue(resultStep2, "No se validaron correctamente las credenciales del perfil Admin");

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
		assertTrue(resultStep5, "CA01 fallido: no fue posible navegar a la pantalla Devoluciones Especiales desde Recepcion de Transferencias y Devoluciones");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Validar que se muestre la pantalla de Devoluciones Especiales");
		boolean resultStep6 = devEspecialesPage.validarFiltrosDevolucionesEspecialesVisibles(true);
		assertTrue(resultStep6, "CA01 fallido: la pantalla Devoluciones Especiales no se visualiza correctamente o los filtros no estan presentes");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Esperar carga inicial del grid");
		boolean resultStep7 = devEspecialesPage.validarGridCargaInicialDevolucionesEspeciales(true);
		assertTrue(resultStep7, "CA01 fallido: el grid de Devoluciones Especiales no presento registros en la carga inicial o el spinner no desaparecio");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Validar cantidad orden y contador inicial");
		boolean orden = devEspecialesPage.validarOrdenDescendenteRegistrosGridDevolucionesEspeciales(false);
		boolean minimo20 = devEspecialesPage.validarGridConMinimoRegistrosDevolucionesEspeciales(20, false);
		boolean contador = devEspecialesPage.validarContadorPaginadorInicialDevEspeciales(true);
		boolean resultStep8 = orden && minimo20 && contador;
		assertTrue(resultStep8, "CA01 fallido: el grid no muestra 20 registros en orden descendente con contador 1 - 20 of Z en la carga inicial de Devoluciones Especiales");
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
		return "Validar que la carga inicial de la pantalla Devoluciones Especiales muestre 20 registros ordenados de forma descendente, el spinner durante la consulta y el contador 1 - 20 of Z para el perfil Admin.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_325_CargaInicialOrdenacionSpinnerAdmin_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
