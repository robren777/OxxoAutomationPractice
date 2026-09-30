package WEB.cnt.scripts;

import static org.testng.Assert.assertFalse;
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
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_029_Activacion_de_Campo_Monto_Maximo_de_Saldo_Inicial extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_029_Activacion_de_Campo_Monto_Maximo_de_Saldo_Inicial_Test(HashMap<String, String> data)
			throws Exception {
		/*
		 * Utiler�as
		 ******************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCDW2QA_PORTAL, GlobalVariables.DB_USER_FCDW2QA_PORTAL,
				GlobalVariables.DB_PASSWORD_FCDW2QA_PORTAL);

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
		String SALDO = data.get("SALDO");
		String SALDO1 = data.get("SALDO1");
		String SALDO2 = data.get("SALDO2");
		String SALDO3 = data.get("SALDO3");
		String SALDO4 = data.get("SALDO4");
		String SALDO5 = data.get("SALDO5");
		String plaza = data.get("plaza");
		String tienda = data.get("tienda");

		String query = " SELECT max_sal_ini_dlls, id_tienda FROM T_OPERACION where  ID_TIENDA=(SELECT ID_TIENDA FROM TIENDAS.T_TIENDAS "
				+ "WHERE CR_SUPERIOR='" + plaza + "' " + "AND CR_TIENDA='" + tienda + "' )";

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
		addStep("Activar el Checkbox Corresponsal Cambiario."); // PENDIENTE
		po.clickcheckBoxCorresponsalCambiario();
//		//------------------------------------PASO 6
		addStep("Activar el Checkbox Saldo Inicial D�lares"); // PENDIENTE
		po.PermCon();
////		//------------------------------------PASO 7
		addStep("Dejar el campo Monto M�ximo de Saldo Inicial vac�o y dar clic en el bot�n Actualizar."); // PENDIENTE
		SQLResult ExecuteQuerycondiciones = executeQuery(dbFCIAS, query);
		System.out.print("consulta" + query);
		boolean StatusQuerycondiciones = ExecuteQuerycondiciones.isEmpty();

		if (!StatusQuerycondiciones) {
			testCase.addTextEvidenceCurrentStep("Se encontro correctamente la tabla");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuerycondiciones);
//		CAMBIO = ExecuteQuerybank.getData(0, "DAR_CAMBIO");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro ninguna tabla ");
		}
		po.editar_C_SI_MIM(SALDO);
		po.clickBotonActualizar();
		boolean mej = po.validacion3();
		assertTrue(mej, "No salio alert de error");

////		//------------------------------------PASO 8
		addStep("Capturar el campo Monto M�ximo de Saldo Inicial con un n�mero entero mayor a cero y dar clic en el bot�n Actualizar."); // PENDIENTE
		po.editar_C_SI_MIM(SALDO1);
		po.clickBotonActualizar();
		boolean mej1 = po.validacion();
		assertTrue(mej1, "No salio alert de error");

		// ------------------------------------PASO 10
		addStep("Modificar el campo Monto M�ximo de Saldo Inicial con valor 0 (Cero) y dar clic en el bot�n Actualizar"); // PENDIENTE
		po.editar_C_SI_MIM(SALDO2);
		po.clickBotonActualizar();
		boolean mej4 = po.validacion4();
		assertTrue(mej4, "No salio alert de error");
////		//------------------------------------PASO 11
		addStep("Modificar el campo Monto M�ximo de Saldo Inicial con caracteres especiales."); // PENDIENTE
		po.editar_C_SI_MIM(SALDO3);
////		//------------------------------------PASO 12
		addStep("Modificar el campo Monto M�ximo de Saldo Inicial con texto."); // PENDIENTE
		po.editar_C_SI_MIM(SALDO4);
////		//------------------------------------PASO 13
		addStep("Modificar el campo Monto M�ximo de Saldo Inicial con decimales."); // PENDIENTE
		po.editar_C_SI_MIM(SALDO5);
////		//------------------------------------PASO 14
		addStep("Copiar y Pegar caracteres especiales en el campo Monto M�ximo de Saldo Inicial"); // PENDIENTE
		po.editar_C_SI_MIM_ctrlcopy_ctrlv(SALDO3);
////		//------------------------------------PASO 15
	addStep("Copiar y Pegar texto en el campo Monto M�ximo de Saldo Inicial"); // PENDIENTE
	po.editar_C_SI_MIM_ctrlcopy_ctrlv(SALDO4);
////		//------------------------------------PASO 16
	addStep("Copiar y Pegar n�meros con decimales en el campo Monto M�ximo de Saldo Inicial"); // PENDIENTE
	po.editar_C_SI_MIM_ctrlcopy_ctrlv(SALDO5);
	
	boolean co = po.CompText(SALDO5);
	assertFalse(co, "Son iguales");
//		

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_029_Activacion_de_Campo_Monto_Maximo_de_Saldo_Inicial_Test";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_029_Activacion_de_Campo_Monto_Maximo_de_Saldo_Inicial_Test";
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
