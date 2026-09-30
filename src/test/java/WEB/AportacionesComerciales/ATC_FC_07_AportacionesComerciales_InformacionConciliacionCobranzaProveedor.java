package WEB.AportacionesComerciales;

import java.util.HashMap;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import WEB.util.AccessControl;
import WEB.util.AuxMethods;
import WEB.util.GlobalVariables;
import WEB.util.AportacionesComerciales.utilAportacionesC;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FC_07_AportacionesComerciales_InformacionConciliacionCobranzaProveedor extends BaseExecution {
	/**
	 * Se crean las instancias
	 */
	private SeleniumUtil u;
	private AccessControl AC;
	private AuxMethods aux;
	private utilAportacionesC util;

	@Test(dataProvider = "data-provider")
	public void ATC_FC_07_AportacionesComerciales_InformacionConciliacionCobranzaProveedor_Test(HashMap<String, String> data)
			throws Exception {
		testCase.setProject_Name("Framework Web: Aportaciones Comerciales");

		/**
		 * Obtener datos del Data Provider
		 */
		String user = data.get("User");
		String pass = data.get("Pass");
		String rfc = data.get("RFC");
		String tipoDoc = "Conciliacion Cobranza Cliente 1224";

		/**
		 * Se inicializan las instancias
		 */
		u = new SeleniumUtil(new MEdgeTest(), true); // Edge
		aux = new AuxMethods(u, testCase);
		util = new utilAportacionesC(u, testCase);
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
		aux.logP("2. Ingresar al Portal \"Gestion de Proveedores\"");
		addStep("Portal Gestion de Proveedores");
		util.goToPortalGestionProveedores();
		u.hardWait(1);

		/**
		 ***** PASO 3
		 */
		aux.logP("3. Seleccionar Abasto del menu");
		addStep("Seleccionar el modulo \"Aportaciones Comerciales\" y la opcion \"Aportaciones Comerciales\"");
		util.goToAportacionesComerciales();
		u.hardWait(3);

		util.changeFrame();
		
		/**
		 ***** PASO 4
		 */
		aux.logP("4. Buscar RFC");
		addStep("Buscar RFC");
		util.buscarRFC(rfc);

		/**
		 ***** PASO 5
		 */
		aux.logP("5. Seleccionar el Tipo de Documento");
		addStep("Seleccionar el Tipo de Documento: Conciliacion Cobranza Cliente 1224");
		util.seleccionarTipoDoc(tipoDoc, true);
		util.buscarApotaciones();
		
		/**
		 ***** PASO 6
		 */
		aux.logP("6. Descargar");
		addStep("Descargar");
		util.descargarArchivos();
		
		util.defaultFrame();
		
		/**
		 ***** PASO 7
		 */
		aux.logP("7. Cerrar Sesion");
		addStep("Cerrar Sesion");
		util.cerrarSesionPortal();

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
		return "1. Acceso al Portal de Aplicaciones"
				+ "\n2. Usuario con acceso a Gestion de Proveedores > Aportaciones Comerciales"
				+ "\n3. Ingresar en el dataProvider el RFC";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Objetivo:Se visualiza la informaci�n de Conciliaci�n Cobranza del Proveedor";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Esmeralda Garcia Torres";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FC_07_AportacionesComerciales_InformacionConciliacionCobranzaProveedor";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
