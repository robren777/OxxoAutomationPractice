package WEB.LaborManagmentSystemMVP2;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.LaborManagmentSystemMVP2.om.LaborManagment;
import WEB.LaborManagmentSystemMVP2.om.LaborManagmentPlantilla;
import WEB.util.GlobalVariables;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;

//import util.PortalEquipamiento.PomPortalEquipamiento;

import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCION: En este caso de prueba se validan los Valores de un Usuario Editado en el Portal Incentivos contra los datos guardados 
 * en la BD XXMAI, este caso se puede realizar ya sea nosotros llenando el Data Provider con Usuarios agregados o dejar con un espacio
 * vacio en el data provider para que la comparación se realice al primer registro de la tabla del Portal. 
 * Nota. Si el primer registro de la tabla no se ha llegado a editar marcara error en las ultimas 2 columnas
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
 * MTC-FT-098-Validar que los datos editados del usuario se actualicen en la tabla XXMAI_PLANTILLA de la BD XXMAI
 * 
 * PRE-REQUISITOS:
 * 		- Acceso a la VPN
 * 		- Haber ejecutado el ATC_FT_015ValidarlaediciondeunusuarioCEDISTest
 * 		- Agregar en la columna nombreUsuario el nombre del usuario editado en el caso ATC_FT_015ValidarlaediciondeunusuarioCEDISTest
 * 
 * @author Carlos Martínez
 * @date 26-09-25
 */
@AzureTestInfo(
		areaPath = "I24025-Labor Management System",
		iterationPath = "I24025-Labor Management System\\MVP 2\\Sprint 19 MVP2",
		tags = "Regresion;AutomationMVP2;I24025_Labor_Management",
		userStoryId = 147309
		)
public class ATC_FT_016_PortalvsBDUsuarioeditado extends BaseExecution {
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_016_PortalvsBDUsuarioeditado_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("LaborManagmentSystem --> Portal de Incentivos");

		
		/**
		 * Variables
		 ******************************************************************************************/
		String url = GlobalVariables.URL_PORTAL_APPS_LABOR;
		String url2 = GlobalVariables.URL_PORTAL_APPS_LABOR2;
		String user = GlobalVariables.USER_PORTAL_APPS_USU1_ACCESSCONTROLQA;
		String password = GlobalVariables.PASSWORD_PORTAL_APPS_USU1_ACCESSCONTROLQA;
		String URL = GlobalVariables.DB_HOST_BDXXMAI_QAVIEW;
		String USER = GlobalVariables.DB_USER__BDXXMAI_QAVIEW;
		String PASSWORD = GlobalVariables.DB_PASSWORD_BDXXMAI_QAVIEW;

		/**
		 * Variables Data Provider
		 ******************************************************************************************/
		String nombreUsuario = data.get("nombreUsuario");

		/**
		 * Utilerias
		 ******************************************************************************************/
		String basePath = System.getProperty("user.dir"); // Ya estás en FrameworkWeb
		String driverPath = basePath + File.separator + "Config" +
		                    File.separator + "Drivers" + File.separator + "MEdge" +
		                    File.separator + "Windows" + File.separator + "140.exe";

		System.setProperty("webdriver.edge.driver", driverPath);
		u = new SeleniumUtil(new MEdgeTest(), true);
		LaborManagment la = new LaborManagment(u, testCase);
		LaborManagmentPlantilla lap = new LaborManagmentPlantilla(u, testCase);

		//Se puede ejecutar con un espacio en el data provider para que se elija el primer Usuario del Portal
		if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
	        nombreUsuario = null;
	    }
		
		addStep("Ingresamos al Portal para obtener los Datos de un Usuario");		
		la.navegarURLsConManejoDeError(url,url2);
		
		Map<String, Object> datos = LaborManagmentPlantilla.prepararDatosPruebaEditar(user, password, URL, USER,
				PASSWORD, nombreUsuario, la, lap, u);

		//Convierte el objeto genérico (Object) recuperado del mapa datos en un Map<String,String> tipado, 
		//para poder acceder a los valores del portal con .get("clave").
		Map<String, String> valoresPortal = (Map<String, String>) datos.get("valoresPortal");
		
		// Sirve para traer los resultados de la consulta ejecutada en BD (Editar)
		LaborManagmentPlantilla.SQLResultEditar resultadoEditar = 
		        (LaborManagmentPlantilla.SQLResultEditar) datos.get("resultadoEditar");

		// Obtenemos la primera fila de la BD
		String[] fila = resultadoEditar.getResultados().get(0);
		
		// Pasos de evidencia:
		addStep("Ingresar a la BD XXMAI");
		assertTrue(resultadoEditar != null, "No se pudo establecer conexión con la BD");

		addStep("Ingresar a la tabla XXMAI_PLANTILLA y ejecutar el query: SELECT * FROM XXMAI_PLANTILLA WHERE no_empleado = 'Escribir el nombre del usuario del portal', AND nombre = 'Escribir el nombre del usuario del portal';");
		assertTrue(!resultadoEditar.isEmpty(), "El query no devolvió resultados");

		addStep("Validar que muestre información el campo ID_PLANT de la tabla de la BD");
		assertTrue(fila[0] != null && !fila[0].trim().isEmpty(), "El campo ID_PLANT no tiene información");

		addStep("Validar que muestre información el campo ID_WAREHOUSE de la tabla de la BD");
		assertTrue(fila[1] != null && !fila[1].trim().isEmpty(), "El campo ID_WAREHOUSE no tiene información");

		addStep("Validar que los datos sean los mismos de la columna \"Usuario (No. de empleado)\" del portal contra la columna \"NO_EMPLEADO\" de la tabla");
		assertTrue(valoresPortal.get("usuario").equals(fila[2]), "No coinciden. Portal: " + valoresPortal.get("usuario") + ", BD: " + fila[2]);

		addStep("Validar que los datos sean los mismos de la columna \"Nombre\" del portal contra la columna \"NOMBRE\" de la tabla");
		assertTrue(valoresPortal.get("nombre").equals(fila[3]), "No coinciden. Portal: " + valoresPortal.get("nombre") + ", BD: " + fila[3]);

		addStep("Validar los valores editados de la columna \"Turno\" del portal contra la columna \"TURNO\" de la tabla");
		assertTrue(valoresPortal.get("turno").equals(fila[4]), "No coinciden. Portal: " + valoresPortal.get("turno") + ", BD: " + fila[4]);
		
		addStep("Validar que los datos sean los mismos de la columna \"Antigüedad (fecha ingreso)\" del portal contra la columna \"FECHA_INGRESO\" de la tabla");
		assertTrue(valoresPortal.get("antiguedad").equals(fila[5]), "No coinciden. Portal: " + valoresPortal.get("antiguedad") + ", BD: " + fila[5]);
		
		addStep("Validar que los datos sean los mismos de la columna \"Semana\" del portal contra la columna \"SEMANAS\" de la tabla");
		assertTrue(valoresPortal.get("semana").equals(fila[6]), "No coinciden. Portal: " + valoresPortal.get("semana") + ", BD: " + fila[6]);
		
		addStep("Validar los valores editados de la columna \"Tipo de colaborador\" del portal contra la columna \"CLASIFICACION\" de la tabla");
		assertTrue(valoresPortal.get("colaborador").equalsIgnoreCase(fila[7]), "No coinciden. Portal: " + valoresPortal.get("colaborador") + ", BD: " + fila[7]);
		
		addStep("Validar los valores editados de la columna \"Tipo de incentivo\" del portal contra la columna \"TPO_INCENTIVO\" de la tabla");
		assertTrue(valoresPortal.get("incentivo").equalsIgnoreCase(fila[8]), "No coinciden. Portal: " + valoresPortal.get("incentivo") + ", BD: " + fila[8]);
		
		addStep("Validar los valores editados de la columna \"Posición\" del portal contra la columna \"ACTIVIDAD\" de la tabla");
		assertTrue(valoresPortal.get("posicion").equalsIgnoreCase(fila[9]), "No coinciden. Portal: " + valoresPortal.get("posicion") + ", BD: " + fila[9]);
		
		addStep("Validar los valores editados de la columna \"Posición clave\" del portal contra la columna \"CLAVE_ACTIVIDAD\" de la tabla");
		assertTrue(valoresPortal.get("posicionClave").equalsIgnoreCase(fila[10]),"No coinciden. Portal: " + valoresPortal.get("posicionClave") + ", BD: " + fila[10]);
		
		addStep("Validar que se muestre quien lo creó en la columna CREATED_BY de la tabla de la BD");
		assertTrue(fila[11] != null && !fila[11].trim().isEmpty(), "El campo CREATED_BY no tiene información");
		
		addStep("Validar que se muestre la fechay hora en que se creó CREATED_DATE de la tabla de la BD");
		assertTrue(fila[12] != null && !fila[12].trim().isEmpty(), "El campo CREATION_DATE no tiene información");
		
		addStep("Validar que se muestre quien lo modifico en la columna MODIFIED_BY en la tabla de la BD");
		assertTrue(fila[13] != null && !fila[13].trim().isEmpty(), "El campo MODIFIED_BY no tiene información");
		
		addStep("Validar que se muestre la fecha y hora en que se edito MODIFIED_DATE en la tabla de la BD");
		assertTrue(fila[14] != null && !fila[14].trim().isEmpty(), "El campo MODIFIED_DATE no tiene información");
		
	}

	@AfterMethod
	public void afterMethod() {
		// u.getDriver().quit();

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "- Acceso a la VPN\r\n"
				+ "- Haber ejecutado el ATC_FT_015ValidarlaediciondeunusuarioCEDISTest\r\n"
				+ "- Agregar en la columna nombreUsuario el nombre del usuario editado en el caso ATC_FT_015ValidarlaediciondeunusuarioCEDISTest";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar el acceso a la pantalla plantilla";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Carlos Martínez Ortega";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_016_PortalvsBDUsuarioeditado_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
