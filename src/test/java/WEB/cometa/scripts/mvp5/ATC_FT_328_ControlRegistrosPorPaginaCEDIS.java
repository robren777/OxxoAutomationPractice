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

/**DESCRIPCIÓN: Validar que el control de registros por pagina del grid de Devoluciones Especiales
 * actualice correctamente la cantidad de filas y el contador al seleccionar las opciones 5, 10, 15 y 20 para el perfil CEDIS.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_328 CA02 - Control de registros por pagina usuario CEDIS
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
public class ATC_FT_328_ControlRegistrosPorPaginaCEDIS extends BaseExecution {

	private SeleniumUtil u;
	private generaDuplicadosDevEsp duplicados;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_328_ControlRegistrosPorPaginaCEDIS_test(HashMap<String, String> data) throws Exception {
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
		assertTrue(resultStep5, "CA02 fallido: no fue posible navegar a la pantalla Devoluciones Especiales");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Validar que se muestre la pantalla de Devoluciones Especiales");
		boolean resultStep6 = devEspecialesPage.validarFiltrosDevolucionesEspecialesVisibles(true);
		assertTrue(resultStep6, "CA02 fallido: la pantalla Devoluciones Especiales no se visualiza correctamente");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Ejecutar una consulta con resultados");
		boolean resultStep7 = devEspecialesPage.validarGridCargaInicialDevolucionesEspeciales(true);
		assertTrue(resultStep7, "CA02 fallido: el grid de Devoluciones Especiales no presenta registros para ejecutar la validacion de paginacion");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Cambiar contador a 5 por pagina");
		boolean resultStep8 = devEspecialesPage.seleccionarYValidarRegistrosPorPaginaDevEspeciales(5, true);
		assertTrue(resultStep8, "CA02 fallido: el grid no muestra 5 registros por pagina o el contador no refleja el cambio correctamente");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Cambiar contador a 10 por pagina");
		boolean resultStep9 = devEspecialesPage.seleccionarYValidarRegistrosPorPaginaDevEspeciales(10, true);
		assertTrue(resultStep9, "CA02 fallido: el grid no muestra 10 registros por pagina o el contador no refleja el cambio correctamente");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Cambiar contador a 15 por pagina");
		boolean resultStep10 = devEspecialesPage.seleccionarYValidarRegistrosPorPaginaDevEspeciales(15, true);
		assertTrue(resultStep10, "CA02 fallido: el grid no muestra 15 registros por pagina o el contador no refleja el cambio correctamente");

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("Cambiar contador a 20 por pagina");
		boolean resultStep11 = devEspecialesPage.seleccionarYValidarRegistrosPorPaginaDevEspeciales(20, true);
		assertTrue(resultStep11, "CA02 fallido: el grid no muestra 20 registros por pagina o el contador no refleja el cambio correctamente");
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
		return "Validar que el control de registros por pagina del grid de Devoluciones Especiales actualice correctamente la cantidad de filas y el contador al seleccionar las opciones 5, 10, 15 y 20 para el perfil CEDIS.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_328_ControlRegistrosPorPaginaCEDIS_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
