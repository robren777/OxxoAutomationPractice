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

public class ATC_FC_01_AportacionesComerciales_InformacionConvenios extends BaseExecution {
	/**
	 * Se crean las instancias
	 */
	private SeleniumUtil u;
	private AccessControl AC;
	private AuxMethods aux;
	private utilAportacionesC util;

	@Test(dataProvider = "data-provider")
	public void ATC_FC_01_AportacionesComerciales_InformacionConvenios_Test(HashMap<String, String> data)
			throws Exception {
		testCase.setProject_Name("Framework Web: Aportaciones Comerciales");

		/**
		 * Obtener datos del Data Provider
		 */
		String user = data.get("User");
		String pass = data.get("Pass");
		String rfc = data.get("RFC");
		String tipoDoc = "Convenios";
		String folio = data.get("Folio");
		String periodo = data.get("Periodo");
		String tipoConvenio = data.get("TipoConvenio");
		String rubro = data.get("Rubro");

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
		aux.logP("5. Seleccionar el Tipo de Documento: Convenios");
		addStep("Seleccionar el Tipo de Documento: Convenios");
		util.seleccionarTipoDoc(tipoDoc, true);

		/**
		 ***** PASO 6
		 */
		aux.logP("6. Validar que se muestren las opciones");
		addStep("El sistema muestra nuevas opciones para captura de Per�odo, Tipo de Convenio y Rubro as� como la opci�n de Folio del Convenio");
		util.validarOpConvenio();

		/**
		 ***** PASO 7
		 */
		aux.logP("7. Buscar Folio");
		addStep("Buscar Folio");
		util.buscarFolioConvenio(folio);

		/**
		 ***** PASO 8
		 */
		aux.logP("8. Buscar sin seleccionar Periodo");
		addStep("Buscar sin seleccionar Periodo");
		util.seleccionarTipoDoc("Seleccionar un Documento", false);
		util.seleccionarTipoDoc(tipoDoc, false);
		util.buscarApotaciones();

		/**
		 ***** PASO 9
		 */
		aux.logP("9. Buscar Periodo");
		addStep("Buscar Periodo");
		util.seleccionarTipoDoc("Seleccionar un Documento", false);
		util.seleccionarTipoDoc(tipoDoc, false);
		util.seleccionarPeriodo(periodo, true);
		util.buscarApotaciones();

		/**
		 ***** PASO 10
		 */
		aux.logP("10. Buscar sin seleccionar Tipo Convenio");
		addStep("Buscar sin seleccionar Tipo Convenio");
		util.seleccionarTipoDoc("Seleccionar un Documento", false);
		util.seleccionarTipoDoc(tipoDoc, false);
		util.seleccionarPeriodo(periodo, false);
		util.buscarApotaciones();

		/**
		 ***** PASO 11
		 */
		aux.logP("11. Buscar Tipo Convenio");
		addStep("Buscar Tipo Convenio");
		util.seleccionarTipoDoc("Seleccionar un Documento", false);
		util.seleccionarTipoDoc(tipoDoc, false);
		util.seleccionarPeriodo(periodo, false);
		util.seleccionarTipoConvenio(tipoConvenio, true);
		util.buscarApotaciones();

		/**
		 ***** PASO 12
		 */
		aux.logP("12. Buscar sin seleccionar Rubro");
		addStep("Buscar sin seleccionar Rubro");
		util.seleccionarTipoDoc("Seleccionar un Documento", false);
		util.seleccionarTipoDoc(tipoDoc, false);
		util.seleccionarPeriodo(periodo, false);
		util.seleccionarTipoConvenio(tipoConvenio, false);
		util.buscarApotaciones();

		/**
		 ***** PASO 13
		 */
		aux.logP("13. Buscar Rubro");
		addStep("Buscar Rubro");
		util.seleccionarTipoDoc("Seleccionar un Documento", false);
		util.seleccionarTipoDoc(tipoDoc, false);
		util.seleccionarPeriodo(periodo, false);
		util.seleccionarTipoConvenio(tipoConvenio, false);
		util.seleccionarRubro(rubro, true);
		util.buscarApotaciones();
		
		/**
		 ***** PASO 14
		 */
		aux.logP("14. Descargar");
		addStep("Descargar");
		util.descargarArchivos();
		
		util.defaultFrame();
		
		/**
		 ***** PASO 15
		 */
		aux.logP("15. Cerrar Sesion");
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
				+ "\n3. Ingresar en el dataProvider el RFC, Folio, Periodo, Tipo de Convenio y Rubro";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Objetivo:Se visualiza la informaci�n de convenios hacia los proveedores.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Esmeralda Garcia Torres";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FC_01_AportacionesComerciales_InformacionConvenios";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
