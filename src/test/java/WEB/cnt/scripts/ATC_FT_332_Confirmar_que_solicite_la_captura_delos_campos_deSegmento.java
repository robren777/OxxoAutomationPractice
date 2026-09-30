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
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_332_Confirmar_que_solicite_la_captura_delos_campos_deSegmento extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_332_Confirmar_que_solicite_la_captura_delos_campos_deSegmento_test(HashMap<String, String> data)
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
		String PlazaE = data.get("PlazaE");
		String Distrito = data.get("Distrito");
		String Tienda = data.get("Tienda");
		String URL = data.get("Url");

		String diferenciador6 = data.get("Diferenciador6");

		String query = " select * from (SELECT ID_PUNTO,CODIGO_POSTAL , ID_MUNICIPIO "
				+ "FROM TIENDAS.XXCNT_BIT_GIS_INFO  " + "WHERE ID_PUNTO NOT IN " + "(SELECT T_TIENDAS.ID_PUNTO "
				+ "FROM T_TIENDAS "
				+ "WHERE T_TIENDAS.CR_SUPERIOR ='CRPLAZA' AND ID_PUNTO IS NOT NULL))where rownum = 1";

		// ------------------------------------PASO 1

		addStep("Ingresar al portal " + URL + " con el <Usuario>" + user + ""); // viene en
																				// excel
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
		addStep("Seleccionar el Distrito y Tienda con estatus Nueva; a continuaci�n dar clic en el boton Continuar");
		pa.clickSeleccionarDistritoTienda(Distrito, Tienda);

		// ------------------------------------PASO 4

		// PASO 7
		addStep("Dar clic en el link Formato y Entorno.");
		Formato_y_Entorno fe = new Formato_y_Entorno(u, testCase);
		fe.FormatoyEntorno();

		// PASO 8
		addStep("Usuario no captura los dem�s datos solicitados en la pantalla y selecciona Actualizar");
		fe.Metodoesperar();
		fe.Framedatos();
		fe.clickBotonActualizarEntorno();
		
		Boolean si=fe.validacionErrorFaltaConcpDif();
		assertTrue(si, "se grabaron los datos");
		
//		fe.Framedatos();
//
//		fe.Diferenciador(diferenciador6);
////		boolean na=fe.ElementDisConValidacionDeTexto();
////		assertTrue(na,"no estan en NA las opciones");
//
//		addStep("Usuario selecciona el bot�n de \"Asignar\" de la opci�n de Segmento de Tienda.");
//		fe.ClicBtbSegmentoTienda();
//
//		addStep("Usuario selecciona Aceptar");
//
//		fe.alertSegmentoTienda();
//		boolean exist = fe.ElementDis();
//		addStep("Usuario selecciona el bot�n de \"Cancelar\".");
//		fe.cancelarOnSegmentodeTienda();
//
////		fe.ClicBtbSegmentoTienda();
////		fe.alertSegmentoTienda();
////		boolean exist= fe.ElementDis();
////		assertTrue(exist,"NO existen los campos");
//		fe.cambiar_ventana();
//		fe.Framedatos();
//		String text = fe.DiferenciadorText();
//		Boolean si = true;
//		String text2 = "CAP";
//		if (text.equals(text2)) {
//			si = false;
//		} else {
//		
//			si = true;
//		}
//
//		assertTrue(si, "es igual a CAP");

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
		return "ATC_FT_332_Confirmar_que_solicite_la_captura_delos_campos_deSegmento_Test";
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
