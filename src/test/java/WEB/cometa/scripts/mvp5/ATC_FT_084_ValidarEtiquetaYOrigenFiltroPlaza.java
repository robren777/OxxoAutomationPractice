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

/**DESCRIPCIÓN: Validar que el filtro Plaza de Devoluciones Especiales muestre opciones concatenadas con xxcm_dim_tienda.plaza_cve y xxcm_region_plaza_tienda_job_sync.desc_plaza, relacionando ambas tablas por RETEK_LOC_ID/id_retek, y que el valor visible coincida con base de datos.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_084 - Validar etiqueta y origen del filtro Plaza
* 
*PREREQUISITOS:
*1. Credenciales para el perfil ADMIN
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Contar con informacion disponible en public.xxcm_dim_tienda y public.xxcm_region_plaza_tienda_job_sync.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Michelle Ceja
* @date Julio 2026
*/
public class ATC_FT_084_ValidarEtiquetaYOrigenFiltroPlaza extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_084_ValidarEtiquetaYOrigenFiltroPlaza_test(HashMap<String, String> data) throws Exception {
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
		 *                                             PASO 8 Y PASO 9
		 ***************************************************************************/
		addStep("Abrir el filtro Plaza y revisar que las opciones tengan formato plaza_cve desc_plaza");
		boolean resultStep8 = devEspecialesPage.abrirFiltroPlazaYValidarFormatoOpciones(true);
		assertTrue(resultStep8, "El filtro Plaza no desplego opciones con el formato esperado plaza_cve desc_plaza");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Seleccionar una opcion de Plaza");
		boolean resultStep10 = devEspecialesPage.seleccionarPrimeraOpcionFiltroPlaza(true);
		assertTrue(resultStep10, "No fue posible seleccionar una opcion del filtro Plaza");

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("Verificar el valor visible en el control Plaza y la etiqueta del filtro");
		boolean resultStep11 = devEspecialesPage.validarValorVisibleFiltroPlaza(true);
		assertTrue(resultStep11, "El filtro Plaza no mostro el valor seleccionado o no respeto la etiqueta Plaza");

		/*
		 *                                             PASO 12
		 ***************************************************************************/
		addStep("Validar en DB la concatenacion xxcm_dim_tienda.plaza_cve + xxcm_region_plaza_tienda_job_sync.desc_plaza");
		boolean resultStep12 = devEspecialesPage.validarPlazaSeleccionadaContraBD(true);
		assertTrue(resultStep12, "La concatenacion de Plaza en BD no coincide con el valor mostrado en el filtro Plaza");

		
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
		return "Validar que el filtro Plaza de Devoluciones Especiales muestre opciones concatenadas con xxcm_dim_tienda.plaza_cve y xxcm_region_plaza_tienda_job_sync.desc_plaza, relacionando ambas tablas por RETEK_LOC_ID/id_retek, y que el valor visible coincida con base de datos.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_084_ValidarEtiquetaYOrigenFiltroPlaza_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}