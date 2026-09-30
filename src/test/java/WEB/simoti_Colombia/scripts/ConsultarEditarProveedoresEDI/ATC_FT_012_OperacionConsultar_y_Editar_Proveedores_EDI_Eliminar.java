package WEB.simoti_Colombia.scripts.ConsultarEditarProveedoresEDI;

import static org.testng.Assert.assertFalse;

import java.sql.SQLException;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.simoti_Colombia.om.ConsultarEditarProveedoresEDI_SimotiColombia;
import WEB.simoti_Colombia.om.Login;
import WEB.simoti_Colombia.om.MenuPrincipal_SimotiColombia;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_012_OperacionConsultar_y_Editar_Proveedores_EDI_Eliminar extends BaseExecution {
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_012_OperacionConsultar_y_Editar_Proveedores_EDI_Eliminar_test(HashMap<String, String> data) throws SQLException {

		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = data.get("Password");
		String URL = data.get("Url");
		String INE = data.get("INE");
		String plaza = data.get("Plaza");
		SQLUtil dbRmsCol = new SQLUtil(GlobalVariables.DB_HOST_BDRMSCQA_QAVIEW, GlobalVariables.DB_USER_BDRMSCQA_QAVIEW, GlobalVariables.DB_PASSWORD_BDRMSCQA_QAVIEW);		

		// Paso 1
		addStep("El usuario ingresa a la aplicaci�n Access Control V3");
		u = new SeleniumUtil(new MEdgeTest(), true);
		u.getDriver().manage().window().maximize();	
		u.get(URL);
		Login pa = new Login(u, testCase);
		MenuPrincipal_SimotiColombia mp = new MenuPrincipal_SimotiColombia(u, testCase);
		ConsultarEditarProveedoresEDI_SimotiColombia cep = new ConsultarEditarProveedoresEDI_SimotiColombia(u,
				testCase);
		testCase.addScreenShotCurrentStep("Imagen de login");

		// Paso 2
		addStep("El usuario ingresa usuario y contrase�a y da clic en el bot�n �Iniciar Sesi�n�.");
		pa.logginPlatform(user, password);

		// Paso 3
		addStep("El usuario da clic en opci�n de SIMOTI.");
		pa.click_on_simoti();

		// Paso 4
		addStep("El usuario despliega el men�: Transacciones Electr�nicas y selecciona el reporte: Consultar y Editar Proveedores EDI");
		mp.clickTransaccionesElectronicasTDC();
		u.hardWait(3);
		mp.clickConsultarEditarProveedoresEDI();

		// Paso 5
		addStep("El usuario selecciona o captura valor en los filtros que se indique:"
				+ " Plaza, Cedis, Proveedor. Pulsar el bot�n Consultar");
		cep.capturaFiltros(plaza, "", "");
		boolean filtrosDeshabilitados = cep.camposDeshabilitadosCedisProveedor();
		System.out.print(filtrosDeshabilitados);
		//assertTrue(filtrosDeshabilitados, "El sistema no deshabilita los campos Cedis y Proveedor");
		cep.clickConsultarBtn();
		//sale error al momento de consultar "Ha ocurrido un error, favor de reportar al area de soporte"
		
		// Paso 6
		addStep("Pulsar el icono de Eliminar");

		// Paso 7
		addStep("Pulsar el bot�n Aceptar");

		// Paso 8
		addStep("Se consulta en BD que el registro se haya eliminado correctemente \n");

		String query = "SELECT DISTINCT CAST( ES.SUPPLIER AS CHAR(10)),\r\n"
				+ " CAST( ES.FILETYPE AS CHAR(10)) ,\r\n"
				+ " CAST( ES.STATUS AS CHAR(1) ),\r\n"
				+ " CAST( S.SUP_NAME AS CHAR(32))\r\n"
				+ "	FROM WMUSER.WM_EDI_MAP_SUPPLIER ES,\r\n"
				+ " SUPS S\r\n"
				+ "WHERE ES.SUPPLIER = S.SUPPLIER\r\n"
				+ "AND ES.SUPPLIER�� = '218' ";
		
				
		SQLResult Resultado1 = executeQuery(dbRmsCol, query);
		System.out.print("consulta" + query);
		
		boolean validacionQuery = Resultado1.isEmpty();
		if (!validacionQuery) {
			testCase.addTextEvidenceCurrentStep("Existen registros");
		} else {
			testCase.addTextEvidenceCurrentStep("No existen registros");
		}
		
		testCase.addTextEvidenceCurrentStep(query);
		assertFalse(validacionQuery, "No existen registros en la base de datos");	
		
		u.close();
		
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_012_Operaci�nConsultar_y_Editar_Proveedores_EDI_Eliminar";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@AfterMethod
	public void afterMethod() {
		 u.getDriver().quit();
	}

}