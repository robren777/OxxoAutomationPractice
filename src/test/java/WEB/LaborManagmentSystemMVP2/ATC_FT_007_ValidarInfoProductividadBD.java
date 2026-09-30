package WEB.LaborManagmentSystemMVP2;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.LaborManagmentSystemMVP2.om.LaborManagment;
import WEB.util.GlobalVariables;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;

//import util.PortalEquipamiento.PomPortalEquipamiento;

import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**
* DESCRIPCIÓN: Validar la informacion del archivo CSV con la informacion de BD
*
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: MTC-FT-038-Validar que la información del archivo Productividad se vacié en la tabla XXMAI_PRODUCTION
*
* PREREQUISITOS:
*   - Asegurar que existe al menos un archivo de productividad de carga CSV generado para la fecha actual en el directorio de pruebas
*   - Acceso al aplicativo LaborManagment (VPN, Puertos)
*   - Usar Microsoft Edge con la última versión
*
* INSUMOS:
*   - Archivo CSV de productividad correspondiente a la fecha actual
*
* @author Alejandro Montalvo Sillas
* @date 29/Septiembre/2025
*/
@AzureTestInfo(
		areaPath = "I24025-Labor Management System",
		iterationPath = "I24025-Labor Management System\\MVP 2\\Sprint 19 MVP2",
		tags = "Regresion;CargaCSV;Productividad;ValidacionBD;Edge",
		userStoryId = 153968
		)
public class ATC_FT_007_ValidarInfoProductividadBD extends BaseExecution {
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_007_ValidarInfoProductividadBD_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("LaborManagmentSystem --> Portal de Incentivos");

		/**
		 * Variables
		 ******************************************************************************************/
		String url = GlobalVariables.URL_PORTAL_APPS_LABOR;
		String url2 = GlobalVariables.URL_PORTAL_APPS_LABOR2;
		String user = GlobalVariables.USER_PORTAL_APPS_USU1_ACCESSCONTROLQA;
		String password = GlobalVariables.PASSWORD_PORTAL_APPS_USU1_ACCESSCONTROLQA;
		String mensaje = data.get("MensajeLogin");
		//String filepath = data.get("File_Path"); 
		String URL = GlobalVariables.DB_HOST_BDXXMAI_QAVIEW;
		String USER = GlobalVariables.DB_USER__BDXXMAI_QAVIEW;
		String PASSWORD = GlobalVariables.DB_PASSWORD_BDXXMAI_QAVIEW;

		
		
		/**
		 * Utilerias
		 ******************************************************************************************/
		//utils.sql.SQLUtil dbFCTPEQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCTPEQA_QAVIEW, GlobalVariables.DB_USER_FCTPEQA_QAVIEW,GlobalVariables.DB_PASSWORD_FCTPEQA_QAVIEW);
		String basePath = System.getProperty("user.dir"); // Ya estás en FrameworkWeb
		String driverPath = basePath + File.separator + "Config" +
		                    File.separator + "Drivers" + File.separator + "MEdge" +
		                    File.separator + "Windows" + File.separator + "140.exe";

		System.setProperty("webdriver.edge.driver", driverPath);
		u = new SeleniumUtil(new MEdgeTest(), true);		
		LaborManagment la = new LaborManagment(u, testCase);

				
	    addStep("Validar integridad de los datos de los CSVs con la Base de Datos.");
	    assertTrue(la.validarDatosCSVsVsBD(URL, USER, PASSWORD),"FAIL: Se encontraron discrepancias entre al menos uno de los CSVs generados y la Base de Datos.");


		
		
	}

	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();

	}

	@Override
	public void beforeTest() {

	}

	@Override
	public String setPrerequisites() {
		return "*   - Asegurar que existe al menos un archivo de productividad de carga CSV generado para la fecha actual en el directorio de pruebas\r\n"
				+ "*   - Acceso al aplicativo LaborManagment (VPN, Puertos)\r\n"
				+ "*   - Usar Microsoft Edge con la última versión";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar la carga de los archivos csv en la BD";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alejandro Montalvo Sillas";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_007_ValidarInfoProductividadBD_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
