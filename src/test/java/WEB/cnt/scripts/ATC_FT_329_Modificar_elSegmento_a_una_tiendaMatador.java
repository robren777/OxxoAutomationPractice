package WEB.cnt.scripts;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cnt.om.Formato_y_Entorno;
import WEB.cnt.om.Login;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_329_Modificar_elSegmento_a_una_tiendaMatador extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_329_Modificar_elSegmento_a_una_tiendaMatador_test(
			HashMap<String, String> data) throws Exception {
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
		String PlazaE = data.get("PlazaE");
		String Distrito = data.get("Distrito");
		String Tienda = data.get("Tienda");
		String URL = data.get("Url");
		String boton = data.get("boton");

		String diferenciador6 = data.get("Diferenciador6");

		String query = " select * from (SELECT ID_PUNTO,CODIGO_POSTAL , ID_MUNICIPIO "
				+ "FROM TIENDAS.XXCNT_BIT_GIS_INFO  " + "WHERE ID_PUNTO NOT IN " + "(SELECT T_TIENDAS.ID_PUNTO "
				+ "FROM T_TIENDAS "
				+ "WHERE T_TIENDAS.CR_SUPERIOR ='CRPLAZA' AND ID_PUNTO IS NOT NULL))where rownum = 1";

		// ------------------------------------PASO 1

		addStep("Ingresar al portal " + URL + " con el <Usuario>" + user + ""); // viene en
																				// excel
		// u = new SeleniumUtil(new ChromeTest(), true); // IETest para usar internet
		// explorer
		u = new SeleniumUtil(new ChromeTest(), true); // IETest para usar internet explorer

		Login pa = new Login(u, testCase); // crea objeto de locator
		// PortalApps pa = new PortalApps(u, testCase); // crea objeto de locator
		u.get(data.get("Url")); // toma el valor Url del data provider
		u.hardWait(3); // tiempo en segundos

		// pa.proceedToPortal(); // es para lo de la pagina no segura
		pa.logginInventarioEspacios(user, password);

		// ------------------------------------PASO 2
		addStep("Seleccionar la Plaza y dar clic en el boton Continuar");
		pa.clickSeleccionarPlaza(PlazaE);
		// ------------------------------------PASO 3
		addStep("Seleccionar el Distrito y Tienda ; \r\n"
				+ "\r\n"
				+ "El usuario selecciona una tienda \"Matador\" que corresponde a un CR que comienza con 56 con estatus diferente a \"Nueva\" que no corresponda a Oxxo M�xico, a continuaci�n dar clic en el boton Continuar");
		pa.clickSeleccionarDistritoTienda(Distrito, Tienda);

		// ------------------------------------PASO 4

		// PASO 7
		addStep("Dar clic en el link Formato y Entorno.");
		Formato_y_Entorno fe = new Formato_y_Entorno(u, testCase);
		fe.FormatoyEntorno();

		// PASO 8
	//_	addStep("Usuario selecciona la opci�n de " + diferenciador6 + " en el combo de Concepto Diferenciador.");

		fe.Framedatos();

		fe.Diferenciador(diferenciador6);

//		boolean na = fe.DiferenciadorActivo();
//		assertFalse(na, "ESTA ACTIVADO");

				
			boolean na=fe.DiferenciadorActivo();
			assertFalse(na,"esta activo");
	

//		fe.ClicBtbSegmentoTienda();
//		fe.alertSegmentoTienda();
//		boolean exist= fe.ElementDis();
//		assertTrue(exist,"NO existen los campos");

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
		u.getDriver().quit();
	}

}
