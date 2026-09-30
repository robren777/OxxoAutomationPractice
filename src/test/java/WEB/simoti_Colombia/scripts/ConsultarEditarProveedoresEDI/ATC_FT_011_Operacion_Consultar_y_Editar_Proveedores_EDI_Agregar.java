package WEB.simoti_Colombia.scripts.ConsultarEditarProveedoresEDI;

import static org.testng.Assert.assertFalse;

import java.sql.SQLException;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.simoti_Colombia.om.ConsultarEditarProveedoresEDI_SimotiColombia;
import WEB.simoti_Colombia.om.Login;
import WEB.simoti_Colombia.om.MenuPrincipal_SimotiColombia;
import WEB.simoti_Colombia.om.transaccionesElectronicas.transaccionesTDC.Transacciones_TDC;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_011_Operacion_Consultar_y_Editar_Proveedores_EDI_Agregar extends BaseExecution {
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_011_Operacion_Consultar_y_Editar_Proveedores_EDI_Agrega_test(HashMap<String, String> data) throws SQLException {

		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = data.get("Password");
		String URL = data.get("Url");
		String INE = data.get("INE");
		String plaza = data.get("Plaza");
		String proveedor = data.get("Proveedor");
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
		Transacciones_TDC ttdc = new Transacciones_TDC(u, testCase);
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
		addStep("Pulsar bot�n Agregar Proveedor");
		ttdc.clickByXpath("//*[@id=\"formConsultarEditarProvEdi:btnAgregarProveedor\"]");

		// Paso 6
		addStep("Ingresar un nombre de Proveedor y Pulsar Consultar");
		cep.consultarProveedor(proveedor);

		// Paso 7
		addStep("Seleccionar un registro y pulsar Aceptar");
		ttdc.clickByXpath("//*[@id=\"formAgregaProv:verProveedEDI_data\"]/tr[1]/td[1]/div/div[2]/span");
		ttdc.clickByXpath("//*[@id=\"formAgregaProv:btnAceptarProveed\"]");

		// Paso 8
		addStep("Informar los campos\r\n"
				+ "Tipo Archivo: \r\n"
				+ "Estado: \r\n"
				+ "Pulsar el bot�n Actualizar");
		cep.seleccionarTipoArchivoYEstado();
		
		// Paso 9
		addStep("Pulsar el bot�n Regresar");
		u.hardWait(6);
		ttdc.clickByXpath("//*[@id=\"formAgregaProv:btnRegresar\"]");

		// Paso 11
		addStep("Buscar el Proveedor agregado, realizando una consulta");
		//sale un error en la pantalla al momento de realizar alguna busqueda "Ha ocurrido un error, favor de reportar el equipo de soporte" 

		// Paso 12
		addStep("Se consulta en BD");
		
		String query = "SELECT DISTINCT CAST( ES.SUPPLIER AS CHAR(10)),\r\n" + 
				" CAST( ES.FILETYPE AS CHAR(10)),\r\n" +
				"CAST( ES.STATUS AS CHAR(1) )," + 
				" CAST( S.SUP_NAME AS CHAR(32)) \r\n" +  
				"FROM WMUSER.WM_EDI_MAP_SUPPLIER ES ,\r\n" +
				" SUPS S\r\n" +
				" WHERE ES.SUPPLIER = S.SUPPLIER AND ES.SUPPLIER�='218'; ";		
				
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
		return "ATC_FT_011_Operaci�n_Consultar_y_Editar_Proveedores_EDI_Agregar";
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