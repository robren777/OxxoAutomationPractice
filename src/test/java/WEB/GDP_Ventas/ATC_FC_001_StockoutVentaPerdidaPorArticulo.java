package WEB.GDP_Ventas;

import java.util.HashMap;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import WEB.util.AccessControl;
import WEB.util.AuxMethods;
import WEB.util.GDP_Ventas.utilGDP_Ventas;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FC_001_StockoutVentaPerdidaPorArticulo extends BaseExecution {
	/**
	 * Se crean las instancias
	 */
	private SeleniumUtil u;
	private AccessControl AC;
	private AuxMethods aux;
	private utilGDP_Ventas util;

	@Test(dataProvider = "data-provider")
	public void ATC_FC_001_StockoutVentaPerdidaPorArticulo_Test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Framework Web: GDP_Ventas");

		/**
		 * Obtener datos del Data Provider
		 */
		String user = data.get("User");
		String pass = data.get("Pass");
		String acomodo = "Articulo";
		String anio = data.get("Anio");
		String tipoTienda = data.get("TipoTienda");
		String tipoPeriodo = data.get("TipoPeriodo");
		String mes = data.get("Mes");

		/**
		 * Se inicializan las instancias
		 */
		u = new SeleniumUtil(new MEdgeTest(), true); // Edge
		aux = new AuxMethods(u, testCase);
		util = new utilGDP_Ventas(u, testCase);
		AC = new AccessControl(u, testCase);

		/**
		 ***** PASO 1
		 */
		aux.logP("1. Ingresar a Access Control");
		addStep("Ingresar a Access Control e ingresar User y Password correctos, dar clic en bot�n \"Iniciar sesi�n\"");
		String url = GlobalVariables.URL_PORTAL_APPS_PORTALES_PUBLICOS_QA;
		AC.loginAccessControl(url, user, pass);

		/**
		 ***** PASO 2
		 */
		aux.logP("2. Seleccionar Abasto del menu");
		addStep("Seleccionar el modulo \"Abasto\" y la opcion \"Stockout y Venta Perdida\"");
		util.goToPortalGestionProveedores();
		u.hardWait(1);
		util.goToStockoutVentaPerdida();
		u.hardWait(3);

		util.changeFrame();

		/**
		 ***** PASO 3
		 */
		aux.logP("3. Buscar");
		addStep("Ingresar datos y buscar");
		util.buscarStockoutVentasPerdidas(acomodo, anio, tipoTienda, tipoPeriodo, mes);

		/**
		 ***** PASO 4
		 */
		aux.logP("4. Descargar Excel y PDF");
		testCase.addBoldTextEvidenceCurrentStep("Descargar Excel y PDF");
		util.descargarExcel();
		util.descargarPDF();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "Descargas");
		testCase.addTextEvidenceCurrentStep("Para obtener los archivos debe ir a la carpeta de descargas del equipo.");

	}

	@AfterTest
	public void afterTest() {
		u.getDriver().quit();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Acceso al Portal de Aplicaciones" + "\n2. Usuario con acceso a Gestion de Proveedores > Abasto"
				+ "\n3. Ingresar en el dataProvider el Acomodo, A�o, Tipo de Tienda, Tipo de Periodo y Mes, del reporte que se requiere obtener.";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Objetivo: \"El sistema mostrar� 3 im�genes con las siguientes descripciones: " + "\n1.- Nacional "
				+ "\n2.- Plaza Se puede elegir el nivel a consultar deseado\" Precondiciones: Acceso al portal Acceso a la BD Datos: *Usuario y pwd al aplicativo GP con perfil Proveedor";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Esmeralda Garcia Torres";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FC_001_StockoutVentaPerdidaPorArticulo";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
