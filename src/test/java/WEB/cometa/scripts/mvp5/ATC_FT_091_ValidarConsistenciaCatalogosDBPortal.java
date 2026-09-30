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

/**DESCRIPCION: Validar que los catalogos CEDIS, Plaza y Tienda de Devoluciones Especiales coincidan entre DB y Portal.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_091 - Validar consistencia de catalogos entre DB y Portal
* 
*PREREQUISITOS:
*1. Credenciales para el perfil CEDIS
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Contar con informacion vigente en xxcm_cedis, xxcm_dim_tienda y xxcm_region_plaza_tienda_job_sync.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Michelle Ceja
* @date Julio 2026
*/
public class ATC_FT_091_ValidarConsistenciaCatalogosDBPortal extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_091_ValidarConsistenciaCatalogosDBPortal_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		devolucionesEspecialesPage devEspecialesPage = new devolucionesEspecialesPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = resolverPerfil(user);

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
		addStep("Validar que existen catalogos vigentes de CEDIS, Plaza y Tienda en DB");
		testCase.addTextEvidenceCurrentStep("Consultas base del caso:\n"
				+ "SELECT id_supplier, supplier_name FROM xxcm_cedis ORDER BY id_supplier;\n"
				+ "SELECT d.plaza_cve, r.desc_plaza, r.cr_tienda, r.desc_tienda FROM xxcm_dim_tienda d JOIN xxcm_region_plaza_tienda_job_sync r ON d.id_retek = r.retek_loc_id ORDER BY d.plaza_cve");

		/*
		 *                                             PASO 2
		 ***************************************************************************/
		addStep("Acceder a la URL de inicio de sesion del portal CEDIS");
		boolean resultStep2Url = pc4.IngresarURL(url, true);
		assertTrue(resultStep2Url, "No se cargo correctamente la pantalla de inicio de sesion del portal CEDIS");
		pc4.PrivatePortal();

		addStep("Capturar usuario y password validos, y hacer clic en Entrar");
		boolean resultStep2Login = pc4.IniciarSesionNoToken(user, password, true);
		assertTrue(resultStep2Login, "No se procesaron correctamente las credenciales validas del usuario " + user);

		addStep("Esperar la carga del portal y hacer clic en Portal COMETA");
		boolean resultStep2Portal = pc4.seleccionarPortalCometaQA(true);
		assertTrue(resultStep2Portal, "No se mostro o no fue posible seleccionar Portal COMETA desde Access Control");

		addStep("Hacer clic en el modulo CEDIS");
		boolean resultStep2Cedis = pc4.clicBotonCometaCedis(true);
		assertTrue(resultStep2Cedis, "No se mostro la opcion Recepcion dentro del modulo CEDIS");

		addStep("Ingresar a Recepcion y abrir la pantalla Devoluciones Especiales");
		boolean resultStep2Recepcion = pc4.ClicBotonRecepcionCedis(true);
		assertTrue(resultStep2Recepcion, "No se pudo ingresar a Recepcion de Transferencias y Devoluciones");
		boolean resultStep2DevEspeciales = devEspecialesPage.ingresarDevolucionesEspeciales(true);
		assertTrue(resultStep2DevEspeciales, "No se pudo abrir la pantalla Devoluciones Especiales");
		boolean resultStep2Filtros = devEspecialesPage.validarFiltrosDevolucionesEspecialesVisibles(true);
		assertTrue(resultStep2Filtros, "No se mostraron los filtros CEDIS, Plaza, Tienda, BOL y Rango de fecha");

		/*
		 *                                             PASO 3
		 ***************************************************************************/
		addStep("Comparar el catalogo CEDIS mostrado en Portal contra xxcm_cedis");
		boolean resultStep3CedisCatalogo = devEspecialesPage.abrirFiltroCedisYValidarFormatoOpciones(true);
		assertTrue(resultStep3CedisCatalogo, "El filtro CEDIS no desplego opciones con formato id_supplier supplier_name");
		boolean resultStep3CedisSeleccion = devEspecialesPage.seleccionarPrimeraOpcionFiltroCedis(true);
		assertTrue(resultStep3CedisSeleccion, "No fue posible seleccionar una opcion de CEDIS para compararla contra DB");
		boolean resultStep3CedisBD = devEspecialesPage.validarCedisSeleccionadoContraBD(true);
		assertTrue(resultStep3CedisBD, "El valor CEDIS mostrado en Portal no coincide con xxcm_cedis.id_supplier + xxcm_cedis.supplier_name");

		addStep("Comparar el catalogo Plaza mostrado en Portal contra xxcm_dim_tienda y xxcm_region_plaza_tienda_job_sync");
		boolean resultStep3PlazaCatalogo = devEspecialesPage.abrirFiltroPlazaYValidarFormatoOpciones(true);
		assertTrue(resultStep3PlazaCatalogo, "El filtro Plaza no desplego opciones con formato plaza_cve desc_plaza");
		boolean resultStep3PlazaSeleccion = devEspecialesPage.seleccionarPrimeraOpcionFiltroPlaza(true);
		assertTrue(resultStep3PlazaSeleccion, "No fue posible seleccionar una opcion de Plaza para compararla contra DB");
		boolean resultStep3PlazaBD = devEspecialesPage.validarPlazaSeleccionadaContraBD(true);
		assertTrue(resultStep3PlazaBD, "El valor Plaza mostrado en Portal no coincide con xxcm_dim_tienda.plaza_cve + xxcm_region_plaza_tienda_job_sync.desc_plaza");

		addStep("Comparar el catalogo Tienda mostrado en Portal contra xxcm_region_plaza_tienda_job_sync");
		boolean resultStep3TiendaCatalogo = devEspecialesPage.abrirFiltroTiendaYValidarFormatoOpciones(true);
		assertTrue(resultStep3TiendaCatalogo, "El filtro Tienda no desplego opciones con formato cr_tienda desc_tienda");
		boolean resultStep3TiendaSeleccion = devEspecialesPage.seleccionarPrimeraOpcionFiltroTienda(true);
		assertTrue(resultStep3TiendaSeleccion, "No fue posible seleccionar una opcion de Tienda para compararla contra DB");
		boolean resultStep3TiendaBD = devEspecialesPage.validarTiendaSeleccionadaContraBD(true);
		assertTrue(resultStep3TiendaBD, "El valor Tienda mostrado en Portal no coincide con xxcm_region_plaza_tienda_job_sync.cr_tienda + xxcm_region_plaza_tienda_job_sync.desc_tienda");


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
		return "*1. Credenciales para el perfil CEDIS      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Validar que los catalogos CEDIS, Plaza y Tienda de Devoluciones Especiales coincidan entre DB y Portal, omitiendo grid y descargas porque no aplican.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_091_ValidarConsistenciaCatalogosDBPortal_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	private String resolverPerfil(String user) {
		if ("6666666".equals(user)) {
			return "CEDIS";
		}
		if ("5555555".equals(user)) {
			return "ADMIN";
		}
		return "NO_DEFINIDO";
	}
}