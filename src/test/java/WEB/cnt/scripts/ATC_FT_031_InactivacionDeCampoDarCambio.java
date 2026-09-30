package WEB.cnt.scripts;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

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

public class ATC_FT_031_InactivacionDeCampoDarCambio extends BaseExecution{
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_031_InactivacionDeCampoDarCambio_Test(
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
				+ " WHERE ID_TIENDA IN(SELECT ID_TIENDA FROM TIENDAS.T_TIENDAS WHERE CR_SUPERIOR='" + PLAZAA
				+ "' AND CR_TIENDA='" + TIENDAA + "')";
		// ------------------------------------PASO 1

		addStep("Ingresar al portal " + URL + " con el <Usuario>" + user + ""); // viene en
																				// excel
		u = new SeleniumUtil(new ChromeTest(), true); // IETest para usar internet explorer
		Login pa = new Login(u, testCase); // crea objeto de locator
		// PortalApps pa = new PortalApps(u, testCase); // crea objeto de locator
		u.get(data.get("Url")); // toma el valor Url del data provider
		u.hardWait(3); // tiempo en segundos

		//pa.proceedToPortal();    // es para lo de la pagina no segura
		pa.logginInventarioEspacios(user, password);

		//------------------------------------PASO 2
		addStep("Seleccionar la Plaza y dar clic en el boton Continuar");
		pa.clickSeleccionarPlaza(PlazaE);
		
		//------------------------------------PASO 3 
		addStep("Seleccionar el Distrito y Tienda; a continuacion dar clic en el boton Continuar");
		pa.clickSeleccionarDistritoTienda(Distrito, Tienda);

		//------------------------------------PASO 4
		Permisos_y_Contratos po = new Permisos_y_Contratos(u, testCase);
		addStep("Dar clic en el link Permisos y Contratos");
		po.PermCon();
		
		/*
		 * PASO 5
		 *****************************************************************************************/
		addStep("Desactivar el Checkbox Dar Cambio");
		boolean msj = po.DesactivarEnDarCambio();
		assertTrue(msj, "Checkbox existe y no esta habilitado");
		//assertTrue(msj, "Checkbox existe y  esta deshabilitado");
		//assertTrue(msj,"Checkbox existe y esta habilitado");
		//assertFalse(msj, "Checkbox existe y  esta deshabilitado ");
        //System.out.print("consulta"+query);
		//boolean msj2 = po.DesactivarEnDarCambio();
		//boolean msj2 = po.DesactivarEnDarCambio();
		//assertFalse(msj2, "Checkbox esta deshabilitado");

		
		//Paso 6 
		addStep("Dar clic en el bot�n actualizar");
		String NO = "N";
		po.clickBotonActualizar();
		boolean StatusQuerybank = po.validacion();
		u.hardWait(10);
		SQLResult ExecuteQuerybank = executeQuery(dbFCIAS, query);
		System.out.print("consulta"+ExecuteQuerybank );
		StatusQuerybank = ExecuteQuerybank.isEmpty();
		if (!StatusQuerybank) {
			testCase.addTextEvidenceCurrentStep("Se encontro correctamente la tabla");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuerybank);
			CAMBIO = ExecuteQuerybank.getData(0, "DAR_CAMBIO");
			System.out.print("cambio:" + CAMBIO);
			
		} else {
			testCase.addTextEvidenceCurrentStep(
					"No se encontro ninguna tabla o no tiene activado seleccionado dolares");
			        CAMBIO = ExecuteQuerybank.getData(0, "DAR_CAMBIO");
			        testCase.addQueryEvidenceCurrentStep(ExecuteQuerybank);
		}
		
		if (CAMBIO.equals(NO)) {
			msj = false;
		}else {msj = true;}
		assertFalse(msj, "BD con cambio es NO ");
		/*if (CAMBIO.equals(NO)) {
			msj = true;
		}else {msj = false;}
		assertFalse(msj, "BD con cambio es NO ");
		addStep("Dar click en boton actualizar."); //Paso 6
        po.clickBotonActualizar();
		//po.EligecheckBoxCorresponsalCambiario();
		
		//assertFalse();
		
		/*
		 * PASO 6
		 *****************************************************************************************/
		//addStep("Dar click en boton actualizar.");
		//po.clickBotonActualizar();
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
		// TODO ATC_FT_031_InactivacionDeCampoDarCambio_Test
		return "ATC_FT_031_InactivacionDeCampoDarCambio_Test";
	}

	@Override
	public String setTestDesigner() {
		// TODO "AutomationQA"
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		// TODO ATC_FT_031_InactivacionDeCampoDarCambio_Test
		return "ATC_FT_031_InactivacionDeCampoDarCambio_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
