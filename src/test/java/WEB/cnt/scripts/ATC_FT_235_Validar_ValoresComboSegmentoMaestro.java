package WEB.cnt.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cnt.om.Formato_y_Entorno;
import WEB.cnt.om.Login;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;
public class ATC_FT_235_Validar_ValoresComboSegmentoMaestro extends BaseExecution{
	
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	
	public void  ATC_FT_235_Validar_Valores_del_ComboSegmentoMaestro_Test(HashMap<String, String> data)
			throws Exception {
		/*
		 * Utilerias
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
		String Tamano = data.get("Tamano");
		String Segmento1 = data.get("Segmento");
		String Ubicacion1 = data.get("Ubicacion");
		String diferenciador = data.get("Diferenciador");
		String TiendaE = data.get("TiendaEspejo");
		String TiendaSis = data.get("TiendaHermana");
		String Porcentaje = data.get("Porcentaje");
		String NivelSocioe = data.get("NivelSocioEconomico");
		String ZonaEco = data.get("ZonaEconomica");
		String Vial = data.get("Vialidad");
		String UbicacionEntorno= data.get("UbicacionEntorno");
		String clasico= data.get("Clasico_1");
		String barrio= data.get("Barrio_Competido1");
		String hogar= data.get("Hogar_reunion1");
		String Oficinista = data.get("Oficinistas_1");
		String paradaT = data.get("Parada_Tecnica1");

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
		u.get(data.get("Url")); // toma el valor Url del data provider
		u.hardWait(3); // tiempo en segundos

		//pa.proceedToPortal();    // es para lo de la pagina no segura
		pa.logginInventarioEspacios(user, password);

		//------------------------------------PASO 2
		addStep("Seleccionar la Plaza y dar clic en el bot�n Continuar");
		pa.clickSeleccionarPlaza(PlazaE);
		//------------------------------------PASO 3
		addStep("Seleccionar el Distrito y Tienda; a continuaci�n dar clic en el boton Continuar");
		pa.clickSeleccionarDistritoTienda(Distrito, Tienda);

		//------------------------------------PASO 4
	
		// PASO 7
		addStep("Dar clic en el link Formato y Entorno.");
		Formato_y_Entorno fe = new Formato_y_Entorno(u, testCase);
		fe.FormatoyEntorno();

		//PASO 8
		addStep("Usuario selecciona un Concepto diferenciador que no sea Excepcion y despues la opcion de \"Segmento de Tienda\".");
		
		fe.Framedatos();
		fe.Diferenciador(diferenciador);
		fe.ClicBtbSegmentoTienda();
		fe.alertSegmentoTienda2();
		boolean exist= fe.ElementDis();
		assertTrue(exist,"NO existen los campos");
		
		addStep("Usuario selecciona el combo de \"Segmento Maestro\"");
		fe.ElementosSegmentoMaestro(clasico,barrio,hogar,Oficinista,paradaT);

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
		return "ATC_FT_235_Validar_Valores_del_ComboSegmentoMaestro_Test";
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

}
