package WEB.AvisosYPoliticas;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.util.HashMap;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import WEB.util.AccessControl;
import WEB.util.AuxMethods;
import WEB.util.GlobalVariables;
import WEB.util.AvisosYPoliticas.utilAvisosPoliticas;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FC_003_AltaAvisosPoliticas extends BaseExecution {
	/**
	 * Se crean las instancias
	 */
	private SeleniumUtil u;
	private AccessControl AC;
	private AuxMethods aux;
	private utilAvisosPoliticas util;

	@Test(dataProvider = "data-provider")
	public void ATC_FC_003_AltaAvisosPoliticas_Test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Framework Web: Avisos y Politicas");

		/**
		 * Obtener datos del Data Provider
		 */
		String user = data.get("User");
		String pass = data.get("Pass");
		String nombre = data.get("Nombre");
		String contenido = data.get("Contenido");

		/**
		 * Se inicializan las instancias
		 */
		u = new SeleniumUtil(new MEdgeTest(), true); // Edge
		aux = new AuxMethods(u, testCase);
		util = new utilAvisosPoliticas(u, testCase);
		AC = new AccessControl(u, testCase);

		/**
		 * Inicio del flujo
		 */

		/**
		 * ********** PASO 1
		 */
		aux.logP("1. Ingresar a Access Control");
		addStep("Ingresar a Access Control e ingresar User y Password correctos, dar clic en bot�n \"Iniciar sesi�n\"");
		String url = GlobalVariables.URL_PORTAL_APPS_PORTALES_PUBLICOS_QA;
		AC.loginAccessControl(url, user, pass);

		/**
		 * ********** PASO 2
		 */
		aux.logP("2. Ir a Altas de Avisos y Politicas");
		addStep("Seleccionar \"Administracion\" del menu y seleccionar la opci�n de \"Alta de Avisos Y Politicas\"");
		util.goToPortalGestionProveedores();
		u.hardWait(1);
		util.goToAltaAvisosPoliticas();
		u.hardWait(2);

		util.switchFrame();

		/**
		 * ********** PASO 3
		 */
		aux.logP("3. Crear Aviso o Politica");
		addStep("Clic en el boton Crear Nuevo Aviso o Politica: "
				+ "Se muestran los campos para introducir el nombre y contenido, "
				+ "seleccionar fechas  y dar clic en Publicar");
		util.crearAvisoPolitica(nombre, contenido);
		u.hardWait(1);

		testCase.addTextEvidenceCurrentStep("Validar que se muestre en la tabla el nuevo Aviso o Politica");
		String row = util.buscarTablaAvPo(nombre, "Publicado");
		assertNotNull(row);

		/**
		 * ********** PASO 4
		 */
		aux.logP("4. Editar");
		addStep("Dar clic en bot�n Editar, editar la politica y publicar los cambios");
		String nombreNew = nombre + " " + aux.cadenaAleatoria(3);
		String contenidoNew = contenido + " Ed";
		util.editarAvPo(row, nombreNew, contenidoNew);

		testCase.addTextEvidenceCurrentStep("Validar que se muestre en la tabla los cambios");
		String row2 = util.buscarTablaAvPo(nombreNew, "Publicado");
		assertNotNull(row2);

		/**
		 * ********** PASO 5
		 */
		aux.logP("5. Clonar");
		addStep("Dar clic en bot�n Clonar. Clonar el Aviso o Politica");
		util.clonarAvPo(row2);

		testCase.addTextEvidenceCurrentStep("Validar que se muestre en la tabla el aviso o politica clonado");
		String row3 = util.buscarTablaAvPo(nombreNew, "Borrador");
		assertNotNull(row3);

		/**
		 * ********** PASO 6
		 */
		aux.logP("6. Eliminar");
		addStep("Ingresar en la pol�tica clonada y Dar clic en bot�n Eliminar");
		util.eliminarAvPo(row3);

		testCase.addTextEvidenceCurrentStep("Validar que ya no se muestre en la tabla el aviso o politica eliminado");
		String row4 = util.buscarTablaAvPo(nombreNew, "Borrador");
		assertNull(row4);

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
				+ "\n2. Usuario con acceso a Gestion de Proveedores > Administracion"
				+ "\n3. Ingresar en el dataProvider el nombre y contenido que se requiere dar de alta";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "MTC-FC-003-Alta de Avisos y pol�ticas";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Esmeralda Garcia Torres";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FC_003_AltaAvisosPoliticas";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
