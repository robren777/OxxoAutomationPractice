package WEB.cnt.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cnt.om.Login;
import WEB.cnt.om.Permisos_y_Contratos;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_042_Acceso_a_Pantalla_Configuracion_Cuenta_Dolares_Rol_para_Editar extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_042_Acceso_a_Pantalla_Configuracion_Cuenta_Dolares_Rol_para_Editar_test(
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
		Permisos_y_Contratos po = new Permisos_y_Contratos(u, testCase);
		addStep("Dar clic en el link Permisos y Contratos");
		po.PermCon();
		
		
		/*
		 * PASO 5
		 *****************************************************************************************/
		addStep("Dar click en CONFIGURACION CUENTA DOLARES.");
		po.pantallaConfigCuentaDolares();
		
		// Paso 6
		addStep("Ir al campo Banco Corresponsal.");
		Boolean activado = po.capturaBancoCorresponsal();
		assertTrue(activado,"No esta habilitado la selecci�n multiple del banco corresponsal");
		
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
		
		return "Se validar� que el rol de Tesorer�a tenga privilegios para la edici�n del campo Banco Corresponsal";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Automation - QA";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_042_Acceso_a_Pantalla_Configuracion_Cuenta_Dolares_Rol_para_Editar_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@AfterMethod
	public void afterMethod() {
	//	u.getDriver().quit();
	}
}
