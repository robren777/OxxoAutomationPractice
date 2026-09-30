package WEB.cnt.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cnt.om.Datos_Generales;
import WEB.cnt.om.Login;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_103_Modificar_informacion_de_tiendas_no_Corresponsales_Poscambios extends BaseExecution{

	
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_103_Modificar_informacion_de_tiendas_no_Corresponsales_Poscambios_test(
			HashMap<String, String> data) throws Exception {
		
		/*
		 * Utiler�as
		 ******************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,
				GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);

		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String URL = data.get("Url");
		String plazaE = data.get("PlazaE");
		String tienda = data.get("Tienda");
		String distrito = data.get("Distrito");
		String tel = data.get("Tel");
		String no = data.get("OpcionTelefono");
		String alternativa = data.get("AlternativaTelefono");
		String p_especifique1 = data.get("Numero De Telefono otro");
		String query = "SELECT WM_RUN_ID, PLAZA_CR, TIENDA_CR, NOMBRE_TIENDA, DIRECCION_FISCAL, DIRECCION_TIENDA, COLONIA_TIENDA FROM tiendas.POS_CAMBIOS\r\n"
				+ "WHERE WM_STATUS = 'L'";

		/*
		 * PASO 1
		 *****************************************************************************************/
		addStep("Ingresar al portal " + URL + " con el <Usuario>" + user + "");
		u = new SeleniumUtil(new ChromeTest(), true);
		Login pa = new Login(u, testCase);
		u.get(data.get("Url"));
		u.hardWait(3);
		pa.logginInventarioEspacios(user, password);
		
		/*
		 * PASO 2
		 *****************************************************************************************/
		addStep("Seleccionar la Plaza y dar clic en el bot�n Continuar");
		pa.clickSeleccionarPlaza(plazaE);
		/*
		 * PASO 3
		 *****************************************************************************************/
		addStep("Seleccionar el Distrito y Tienda; a continuaci�n dar clic en el bot�n Continuar");
		pa.clickSeleccionarDistritoTienda(distrito, tienda);
		
		/*
		 * PASO 4
		 *****************************************************************************************/
		Datos_Generales dG =  new Datos_Generales(u, testCase);
		addStep("Dar clic en el link Datos Generales");
		dG.DatosGen();
		
		/*
		 * PASO 5
		 *****************************************************************************************/
		addStep("Dar clic en entre calles y mandar la nueva informacion");
		dG.inputEntreCalles("Hidalgo S");
		
		/*
		 * PASO 6
		 *****************************************************************************************/
		addStep("Dar clic en la casilla para desenmarcar el telefono");
		dG.NTel(tel, no, alternativa, p_especifique1);
		
		/*
		 * PASO 7
		 *****************************************************************************************/
		addStep("Dar click en Actualizar.");
		dG.clickBotonActualizar();
		
		/*
		 * PASO 8
		 *****************************************************************************************/
		addStep("Validar en la tabla POS_CAMBIOS que el valor asignado al campo Calle haya sido reflejado correctamente.");
		
		SQLResult executeQuery = executeQuery(dbFCIAS, query);
		boolean statusQuery = query.isEmpty();

		if (!statusQuery) {
			testCase.addTextEvidenceCurrentStep("Las Tiendas se muestran en la tabla consultada.");
			System.out.println(" la tabla no esta vacia ");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontraron datos en la tabla");
		}

		testCase.addQueryEvidenceCurrentStep(executeQuery);
		
		assertTrue(!statusQuery, "Se muestra el valor correctamente en la tabla.");
		
		
		
		
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
		return "Se validar� que al realizar un cambio en las Tiendas No Corresponsales,";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_103_Modificar_informacion_de_tiendas_no_Corresponsales_Poscambios_test";
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
