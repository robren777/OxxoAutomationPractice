package WEB.simoti_Colombia.scripts.ConsultarEditarProveedoresEDI;

import static org.testng.Assert.assertTrue;

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
import utils.sql.SQLUtil;

public class ATC_FT_010_Administrador_Consultar_y_Editar_Proveedores_EDI_Eliminar extends BaseExecution {
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_010_Administrador_Consultar_y_Editar_Proveedores_EDI_Eliminar_test(HashMap<String, String> data)
			throws SQLException {

		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = data.get("Password");
		String URL = data.get("Url");
		String INE = data.get("INE");
		String plaza = data.get("Plaza");
		SQLUtil dbRmsCol = new SQLUtil(GlobalVariables.DB_HOST_BDRMSCQA_QAVIEW, GlobalVariables.DB_USER_BDRMSCQA_QAVIEW,
				GlobalVariables.DB_PASSWORD_BDRMSCQA_QAVIEW);

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
		System.out.println("paso 4 ");
		
		// Paso 5
		addStep("El usuario selecciona o captura valor en los filtros que se indique:\r\n" + "*Plaza: \r\n"
				+ "*Cedis: �\r\n" + "*Proveedor: �\r\n" + "Pulsar el bot�n Consultar"
				+ " El sistema deshabilita los campos Cedis, Proveedor y muestra la consulta");
		cep.capturaFiltros(plaza, "", "");

		boolean filtrosDeshabilitados = cep.camposDeshabilitadosCedisProveedor();
		System.out.print(filtrosDeshabilitados);

		assertTrue(filtrosDeshabilitados, "El sistema no deshabilita los campos Cedis y Proveedor");

		cep.clickConsultarBtn();
		System.out.println("paso 5 ");
		
		// Paso 6
		addStep("El usuario valida que el sistema muestre los resultados de la consulta en las siguientes columnas, validar que se muestre el tipo de dato adecuado:\r\n"
				+ "1. No. (Tipo de dato: Number)\r\n" + "2. Proveedor: (Tipo de dato: Number)\r\n"
				+ "3. Nombre (Tipo de dato: String)\r\n" + "4. Tipo archivo (Tipo de dato: String)\r\n"
				+ "5. est�do (Tipo de dato: String)\r\n" + "6. Editar (link)\r\n" + "7. Ver (link)\r\n"
				+ "8. Editar plaza y buzones (link)\r\n" + "9. Eliminar (link)");

		boolean columnas = cep.validarEncabezadosTablaResultados();
		assertTrue(columnas, "No se muestran las columnas");

		boolean resultados = cep.validarExistenResultados();
		assertTrue(resultados, "No se muestran resultados de la consulta");
		System.out.println("paso 6 ");

		// Paso 7
		addStep("El usuario da clic en el link de Editar plaza y Buzones\r\n"
				+ "El usuario selecciona un registro y da clic en el link de Eliminar que se muestra en la Columna Eliminar.");

		// Paso 8
		addStep("El usuario confirma el mensaje de dar de baja la Plaza.");

		// Paso 9
		addStep("El usuario valida que la Plaza eliminada ya no se muestre en pantalla.");

		// Paso 10
		addStep("El usuario confirma que ya no se muestre el registro que elimino.");

	/*	String query = "SELECT DISTINCT CAST( ES.SUPPLIER AS CHAR(10)),\r\n" + " CAST( ES.FILETYPE AS CHAR(10)),\r\n"
				+ "CAST( ES.STATUS AS CHAR(1) )," + " CAST( S.SUP_NAME AS CHAR(32)) \r\n"
				+ "FROM WMUSER.WM_EDI_MAP_SUPPLIER ES ,\r\n" + " SUPS S\r\n"
				+ " WHERE ES.SUPPLIER = S.SUPPLIER AND ES.SUPPLIER�='218'; ";

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
*/
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
		return "ATC_FT_010_Administrador_Consultar_y_Editar_Proveedores_EDI_Eliminar";
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