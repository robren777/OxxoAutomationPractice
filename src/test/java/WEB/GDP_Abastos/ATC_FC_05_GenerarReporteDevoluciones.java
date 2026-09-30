package WEB.GDP_Abastos;

import java.util.HashMap;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import WEB.util.AccessControl;
import WEB.util.AuxMethods;
import WEB.util.GDP_Abastos.utilGDP_Abastos;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FC_05_GenerarReporteDevoluciones extends BaseExecution {
	/**
	 * Se crean las instancias
	 */
	private SeleniumUtil u;
	private AccessControl AC;
	private AuxMethods aux;
	private utilGDP_Abastos util;

	@Test(dataProvider = "data-provider")
	public void ATC_FC_05_GenerarReporteDevoluciones_Test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Framework Web: GDP_Abastos");

		/**
		 * Obtener datos del Data Provider
		 */
		String user = data.get("User");
		String pass = data.get("Pass");
		String mes = data.get("Mes");
		String rcf = data.get("RFC");

		/**
		 * Se inicializan las instancias
		 */
		u = new SeleniumUtil(new MEdgeTest(), true); // Edge
		aux = new AuxMethods(u, testCase);
		util = new utilGDP_Abastos(u, testCase);
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
		addStep("Ingresar al Portal \"Gestion de Proveedores\"");
		util.goToPortalGestionProveedores();
		u.hardWait(1);

		/**
		 ***** PASO 3
		 */
		aux.logP("3. Seleccionar Abasto del menu");
		addStep("Seleccionar el modulo \"Abasto\" y la opcion \"Reporte de Devoluciones\"");
		util.goToReporteDevoluciones();
		u.hardWait(3);

		String vista = "DEVOLUCIONES";

		util.changeFrame();

		/**
		 ***** PASO 4
		 */
		aux.logP("4. Seleccionar mes");
		addStep("Seleccionar mes");
		util.ingresarMes(mes);

		/**
		 ***** PASO 5
		 */
		aux.logP("5. Ingresar RFC");
		addStep("Ingresa y selecciona el RFC del proveedor a consultar");
		util.seleccionarRFC(rcf, vista);

		/**
		 ***** PASO 6
		 */
		aux.logP("6. Buscar");
		addStep("Buscar reporte");
		util.buscarReporte(rcf, vista);
		u.hardWait(3);

		/**
		 ***** PASO 7
		 */
		aux.logP("7. Descargar Excel y PDF");
		testCase.addBoldTextEvidenceCurrentStep("Descargar Excel y PDF");
		util.descargarExcel();
		util.descargarPDF();
		u.hardWait(1);
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
				+ "\n3. Ingresar en el dataProvider el usuario, mes y RFC del reporte que se requiere obtener.";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Esmeralda Garcia Torres";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FC_05_GenerarReporteDevoluciones";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
