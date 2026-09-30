package WEB.cnt.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cnt.om.Formato_y_Entorno;
import WEB.cnt.om.Login;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_238_Validar_valores_del_combo_de_Potencial_Desayuno extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void  ATC_FT_238_Validar_valores_del_combo_de_Potencial_Desayuno_test(HashMap<String, String> data)
			throws Exception {
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
		String Plaza = data.get("PlazaE");
		String Distrito = data.get("Distrito");
		String Tienda = data.get("Tienda");
		String URL = data.get("Url");
		String diferenciador = data.get("Diferenciador");
		String bajo= "Desayuno_Bajo";
		String medio= "Desayuno_Medio";
		String alto= "Desayuno_Alto";

		String query = " select * from (SELECT ID_PUNTO,CODIGO_POSTAL , ID_MUNICIPIO "
				+ "FROM TIENDAS.XXCNT_BIT_GIS_INFO  " + "WHERE ID_PUNTO NOT IN " + "(SELECT T_TIENDAS.ID_PUNTO "
				+ "FROM T_TIENDAS "
				+ "WHERE T_TIENDAS.CR_SUPERIOR ='CRPLAZA' AND ID_PUNTO IS NOT NULL))where rownum = 1";

		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Ingresar a '" + URL +"' del portal de aplicaciones"); 
		
		u = new SeleniumUtil(new MEdgeTest(), true); // IETest para usar internet explorer
		Login pa = new Login(u, testCase); // crea objeto de locator
		// PortalApps pa = new PortalApps(u, testCase); // crea objeto de locator
		u.get(data.get("Url")); // toma el valor Url del data provider
		u.hardWait(3); // tiempo en segundos
		
		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Ingresar el usuario "  + user + " y contrase�a  para despu�s seleccionar el bot�n de Continuar");

		pa.logginInventarioEspacios(user, password);
		
		
		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Usuario selecciona la Plaza con la cual trabajara y selecciona el bot�n de Continuar");
		
		pa.clickSeleccionarPlaza(Plaza);
		
		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("El usuario selecciona selecciona una tienda con estatus Nueva y da clic en el bot�n de Continuar.");
		
		pa.clickSeleccionarDistritoTienda(Distrito, Tienda);
		
		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Usuario selecciona la opci�n de Formato y Entorno");
		
		Formato_y_Entorno fe = new Formato_y_Entorno(u, testCase);
		fe.FormatoyEntorno();
		

		/*
		 *                                             PASO 1
		 ******************************************************************************************/	     			
		addStep("Usuario selecciona un Concepto diferenciador que no sea Excepci�n y despu�s la opci�n de \"Segmento de Tienda\".");
		
		fe.Framedatos();
		fe.Diferenciador(diferenciador);
		fe.ClicBtbSegmentoTienda();
		fe.alertSegmentoTienda();
		u.hardWait(2);
		boolean exist= fe.ElementDis();
		assertTrue(exist,"NO existen los campos");

		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Usuario selecciona el combo de \"Potencial Desayuno\"");
		
		fe.ElementosPotencialDesayuno(bajo,medio,alto);
		
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return null;
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
		//u.getDriver().quit();
	}

}
