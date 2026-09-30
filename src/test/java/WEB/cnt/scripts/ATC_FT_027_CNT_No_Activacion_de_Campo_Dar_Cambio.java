package WEB.cnt.scripts;

import static org.testng.Assert.assertFalse;

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
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_027_CNT_No_Activacion_de_Campo_Dar_Cambio extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_027_No_Activacion_de_Campo_Dar_Cambio(HashMap<String, String> data) throws Exception {
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
		String CAMBIO = "";
		String PLAZAA = data.get("PLAZAA");
		String TIENDAA = data.get("TIENDAA");
		String query = " SELECT ID_TIENDA, DAR_CAMBIO FROM TIENDAS.T_OPERACION \r\n"
				+ " WHERE ID_TIENDA IN(SELECT ID_TIENDA FROM TIENDAS.T_TIENDAS WHERE CR_SUPERIOR='"+PLAZAA+"' AND CR_TIENDA='"+TIENDAA+"')";

		// ------------------------------------PASO 1

		addStep("Ingresar al portal " + URL + " con el <Usuario>" + user + ""); // viene en
																				// excel
		u = new SeleniumUtil(new ChromeTest(), true); // IETest para usar internet explorer
		Login pa = new Login(u, testCase); // crea objeto de locator
		// PortalApps pa = new PortalApps(u, testCase); // crea objeto de locator
		u.get(data.get("Url")); // toma el valor Url del data provider
		u.hardWait(3); // tiempo en segundos

//		pa.proceedToPortal();    // es para lo de la pagina no segura
		pa.logginInventarioEspacios(user, password);

//		//------------------------------------PASO 2
		addStep("Seleccionar la Plaza y dar clic en el bot�n Continuar");
		pa.clickSeleccionarPlaza(PlazaE);
//		//------------------------------------PASO 3
		addStep("Seleccionar el Distrito y Tienda; a continuaci�n dar clic en el bot�n Continuar");
		pa.clickSeleccionarDistritoTienda(Distrito, Tienda);

//		//------------------------------------PASO 4
		Permisos_y_Contratos po = new Permisos_y_Contratos(u, testCase);
		addStep("Dar clic en el link Permisos y Contratos"); // PENDIENTE
		po.PermCon();
//		//------------------------------------PASO 5
		addStep("Ir al campo Corresponsal Cambiario y revisar que est� Inactivo.");

	
		boolean msj = po.EligecheckBoxCorresponsalCambiario();
		assertFalse(msj, "Checkbox existe y  esta habilitado ");
         System.out.print("consulta"+query);
		SQLResult ExecuteQuerybank = executeQuery(dbFCIAS, query);
		  System.out.print("consulta"+ExecuteQuerybank );
		boolean StatusQuerybank = ExecuteQuerybank.isEmpty();
		
		String NO = "N";
		if (!StatusQuerybank) {
			testCase.addTextEvidenceCurrentStep("Se encontro correctamente la tabla");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuerybank);
			CAMBIO = ExecuteQuerybank.getData(0, "DAR_CAMBIO");
		} else {
			testCase.addTextEvidenceCurrentStep(
					"No se encontro ninguna tabla o no tiene activado seleccionado dolares");
		}

		if (CAMBIO.equals(NO)) {
			msj = false;
		}else {msj = true;}
		assertFalse(msj, "BD con cambio es SI ");
//		addStep("Dar clic en el bot�n Actualizar.");
//		po.clickBotonActualizar();
//		

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_027_No_Activacion_de_Campo_Dar_Cambio";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_027_No_Activacion_de_Campo_Dar_Cambio";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}

}
