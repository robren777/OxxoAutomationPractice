package WEB.simoti_Colombia.scripts.ConsultarEditarProveedoresEDI;

import static org.testng.Assert.assertFalse;
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
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_009_Administrador_Consultar_y_Editar_Proveedores_EDI_Eliminar extends BaseExecution {
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_009_Administrador_Consultar_y_Editar_Proveedores_EDI_Eliminar_test(HashMap<String, String> data)
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
		addStep("El usuario valida que los resultados de la consulta son acorde a los resultados del query");

		String query = "SELECT DISTINCT CAST( ES.SUPPLIER AS����������������� CHAR(10)),\r\n"
				+ "� CAST( ES.FILETYPE AS������������������������������� CHAR(10)) ,\r\n"
				+ "� CAST( ES.STATUS AS��������������������������������� CHAR(1) ),\r\n"
				+ "� CAST(DECODE( ES.COST, 0, 'No', 1, 'Si', ES.COST )AS CHAR (3)),\r\n" + "� ID,\r\n"
				+ "� CAST (DECODE( ES.SURTIRPORPIEZA, 0, 'Cajas', 1, 'Piezas', ES.SURTIRPORPIEZA ) AS������ CHAR(6)),\r\n"
				+ "� CAST(DECODE( ES.FORMAT_TYPE, 1, 'Orden por caja', 2, 'Multitienda', ES.FORMAT_TYPE )AS CHAR(15)),\r\n"
				+ "� ES.MAX_ATTEMPT,\r\n" + "� CAST( S.SUP_NAME AS CHAR(32))\r\n"
				+ "FROM WMUSER.WM_EDI_MAP_SUPPLIER ES,\r\n" + "� SUPS S\r\n" + "WHERE ES.SUPPLIER = S.SUPPLIER\r\n"
				+ "AND S.SUPPLIER�� IN\r\n" + "� (SELECT SUP_ID\r\n"
				+ "� FROM POSUSER.WM_EDI_SUPPLIER_PLAZA@FCWMPRD.FEMCOM.NET\r\n" + "� WHERE plaza_id ='110')\r\n"
				+ "ORDER BY 1;";

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
		System.out.println("paso 7");

		// Paso 8
		addStep("El usuario da clic en el link de Editar plaza y Buzones");

		// Paso 9
		addStep("El usuario valida que en la pantalla de \"Editar Plaza y Buzones� se muestren los siguientes datos:\r\n"
				+ "1. Proveedor\r\n" + "2. Agregar buz�n\r\n" + "  - Buz�n\r\n" + "  - Tipo\r\n" + "  - Calificador\r\n"
				+ "3. Lista de Buzones\r\n" + " - Buz�n\r\n" + " - Calificador\r\n" + " - Tipo\r\n" + " - Editar\r\n"
				+ " - Eliminar\r\n" + "4. Agregar plaza\r\n" + " - Plaza\r\n" + "5. Agregar Cedis\r\n" + "6. Cedis\r\n"
				+ "7. Lista de plazas y Cedis\r\n" + " - Plaza/Cedis\r\n" + " - Descripci�n\r\n" + " - Eliminar");

		// Paso 10
		addStep("El usuario selecciona un registro y da clic en Eliminar. El sistema muestra mensaje de confirmaci�n de la eliminaci�n");

		// Paso 11
		addStep("El usuario confirma el mensaje de dar de baja el Cedis.");

		// Paso 12
		addStep("El usuario valida que el Cedis eliminado ya no se muestre en pantalla. El sistema no muestra el Cedis que se dio de baja.");
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
		return "ATC_FT_009_Administrador_Consultar_y_Editar_Proveedores_EDI_Eliminar";
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