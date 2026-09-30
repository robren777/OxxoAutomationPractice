package WEB.cnt.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cnt.om.Login;
import WEB.cnt.om.Permisos_y_Contratos;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
public class ATC_FT_032_Habilitacion_LigaConfiguracion_CuentaDolares extends BaseExecution{
	
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_032_Habilitacion_LigaConfiguracion_CuentaDolares_Test(
			HashMap<String, String> data) throws Exception {
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String PlazaE = data.get("PlazaE");
		String Distrito = data.get("Distrito");
		String Tienda = data.get("Tienda");
		String URL = data.get("Url");
		String cheque = data.get("cheque");
		String dolares = data.get("dolares");
		String efectivo = data.get("efectivo");
		String TDD1 = data.get("TDD1");
		String TDC1 = data.get("TDC1");
		String vales = data.get("vales");

		/*String query = " select * from (SELECT ID_PUNTO,CODIGO_POSTAL , ID_MUNICIPIO "
				+ "FROM TIENDAS.XXCNT_BIT_GIS_INFO  " + "WHERE ID_PUNTO NOT IN " + "(SELECT T_TIENDAS.ID_PUNTO "
				+ "FROM T_TIENDAS "
				+ "WHERE T_TIENDAS.CR_SUPERIOR ='CRPLAZA' AND ID_PUNTO IS NOT NULL))where rownum = 1";*/

		// ------------------------------------PASO 1

		addStep("Ingresar al portal " + URL + " con el <Usuario>" + user + ""); // viene en
																				// excel
		u = new SeleniumUtil(new ChromeTest(), true); // IETest para usar internet explorer
		Login pa = new Login(u, testCase); // crea objeto de locator
		// PortalApps pa = new PortalApps(u, testCase); // crea objeto de locator
		//u.get(data.get("Url")); // toma el valor Url del data provider
		u.get("http://10.184.32.40:8000/tiendas/html/login.html");
		u.hardWait(3); // tiempo en segundos
		
		//pa.proceedToPortal();    // es para lo de la pagina no segura
		pa.logginInventarioEspacios(user, password);

		
		addStep("Seleccionar la Plaza y dar clic en el botón Continuar");
		pa.clickSeleccionarPlaza(PlazaE);

		addStep("Seleccionar el Distrito y Tienda; a continuación dar clic en el botón Continuar");
		pa.clickSeleccionarDistritoTienda(Distrito, Tienda);
		
		//------------------------------------PASO 2
		Permisos_y_Contratos po = new Permisos_y_Contratos(u, testCase);
		addStep("Dar clic en el link Permisos y Contratos"); 
		po.PermCon();
		//Habilitar para configuración cuenta Dólares 
		po.validarConfigCuentaDolares();
		boolean vali = po.validarConfigCuentaDolares();
        assertTrue(vali, "Notificacion existe, no existe el validar dólares");
		
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
		// TODO "ATC_FT_032_Habilitacion_LigaConfiguracion_CuentaDolares_Test"
		return "ATC_FT_032_Habilitacion_LigaConfiguracion_CuentaDolares_Test";
	}

	@Override
	public String setTestDesigner() {
		// TODO "AutomationQA"
		return "Automation QA";
	}

	@Override
	public String setTestFullName() {
		// TODO "ATC_FT_032_Habilitacion_LigaConfiguracion_CuentaDolares_Test"
		return "ATC_FT_032_Habilitacion_LigaConfiguracion_CuentaDolares_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
