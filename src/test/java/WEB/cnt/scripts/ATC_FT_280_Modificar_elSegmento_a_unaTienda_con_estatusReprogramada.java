package WEB.cnt.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cnt.om.Formato_y_Entorno;
import WEB.cnt.om.Login;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_280_Modificar_elSegmento_a_unaTienda_con_estatusReprogramada extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_280_Modificar_elSegmento_a_unaTienda_con_estatusReprogramada_test(HashMap<String, String> data) throws Exception {
		/*
		 * Utiler�as
		 ******************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,
				GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);

		String cp = data.get("CP");
		String num = data.get("num");
		testCase.setTest_Description(num+""+cp);

		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String Plaza = data.get("Plaza");
		String Distrito = data.get("Distrito");
		String Tienda = data.get("Tienda");
		String URL = data.get("Url");
		String TiendaP = data.get("TiendaP");
		String PlazaP  = data.get("PlazaP ");
		String diferenciadorModificar = data.get("DiferenciadorModificar");
		String diferenciadorActual = data.get("DiferenciadorActual");

		String query = " select  ID_TIENDA, MAESTRO_SEGMENTO_ID, MAESTRO_SEGMENTO_DESC from TIENDAS.t_formato_segmento  where id_tienda in ("
				+ " select id_tienda from t_tiendas"
				+ " where cr_superior='" + PlazaP +"'"
				+ " and cr_tienda in ('" + TiendaP +"'" +") )";
		
		
		String query2 = " select  CONCEPTO_DIFER_ID, CONCEPTO_DIFERENCIADOR from TIENDAS.t_formato_segmento  where id_tienda in ("
				+ " select id_tienda from t_tiendas"
				+ " where cr_superior='" + PlazaP + "'"
				+ " and cr_tienda in ('" + TiendaP + "'" +") )";

		// ------------------------------------PASO 1

		addStep("Ingresar al portal " + URL + " con el <Usuario>" + user + ""); 
		
		u = new SeleniumUtil(new MEdgeTest(), true);
		Login pa = new Login(u, testCase); 
		u.get(data.get("Url"));
		u.getDriver().manage().window().maximize();
		u.hardWait(3); 

		pa.logginInventarioEspacios(user, password);

		// ------------------------------------PASO 2
		addStep("Seleccionar la Plaza y dar clic en el boton Continuar");
		pa.clickSeleccionarPlaza(Plaza);
		// ------------------------------------PASO 3
		addStep("Seleccionar el Distrito y Tienda con estatus Reprogramada; a continuacion dar clic en el boton Continuar");
		pa.clickSeleccionarDistritoTienda(Distrito, Tienda);

		// ------------------------------------PASO 4
		addStep("Se confirmara que la tienda que se seleccion� cuenta con un Concepto diferenciador de "+diferenciadorActual+" y da clic en el bot�n de Continuar.");
		SQLResult ExecuteQueryLEGAL = executeQuery(dbFCIAS, query);
		SQLResult ExecuteQueryLEGAL2 = executeQuery(dbFCIAS, query2);
		System.out.print("consulta" +query);
		boolean StatusQueryLEGAL = ExecuteQueryLEGAL.isEmpty();
		
		testCase.addQueryEvidenceCurrentStep(ExecuteQueryLEGAL);
		testCase.addQueryEvidenceCurrentStep(ExecuteQueryLEGAL2);
		
		if (!StatusQueryLEGAL) {
	
			String diferenciadorBD = ExecuteQueryLEGAL2.getData(0, "CONCEPTO_DIFERENCIADOR");
			
			if (diferenciadorBD.toUpperCase().equals(diferenciadorActual.toUpperCase())) {
				testCase.addTextEvidenceCurrentStep("La tienda cuenta con un Concepto diferenciador de "+diferenciadorActual);

			} else {
				Assert.fail("La tienda no cuenta con un Concepto diferenciador de "+diferenciadorActual);
			}
		} else {
			Assert.fail("No se encontro registro de la tienda en la tabla T_FORMATO_SEGMENTO");
		}
		
		// PASO 7
		addStep("Dar clic en el link Formato y Entorno.");
		Formato_y_Entorno fe = new Formato_y_Entorno(u, testCase);
		fe.FormatoyEntorno();

		// PASO 8
		addStep("Usuario selecciona la opci�n de " + diferenciadorModificar + " en el combo de Concepto Diferenciador.");

		fe.Framedatos();

		fe.Diferenciador(diferenciadorModificar);

		
		boolean na=fe.ElementBtnAsingar();
		assertTrue(na,"El BOTON ESTA DESACTIVADO");


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
		return null;
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@AfterMethod
	public void afterMethod() {
		//u.getDriver().quit();
	}

}
