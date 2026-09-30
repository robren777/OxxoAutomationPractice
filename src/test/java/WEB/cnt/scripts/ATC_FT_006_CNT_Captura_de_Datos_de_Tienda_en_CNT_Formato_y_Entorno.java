package WEB.cnt.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cnt.om.Formato_y_Entorno;
import WEB.cnt.om.Login;
import WEB.cnt.om.Permisos_y_Contratos;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_006_CNT_Captura_de_Datos_de_Tienda_en_CNT_Formato_y_Entorno extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_006_Captura_de_Datos_de_Tienda_en_CNT_Formato_y_Entorno(HashMap<String, String> data)
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
		addStep("Seleccionar al menos una Forma de Pago.");

		po.EligecheckBox(cheque, dolares, efectivo, TDD1, TDC1, vales);

//		//------------------------------------ PASO 6

		addStep("Dar clic en el bot�n Actualizar.");
		po.clickBotonActualizar();
		po.validacion();
		
//		//System.out.print(user);
//		dc.clickMenuPedidoAdmin(plaza,direcc,cp,calle,numero,entreCalle,yEntreCalle,nomContacto,telContacto,comentario);
//		
		// PASO 7
		addStep("Dar clic en el link Formato y Entorno.");
		Formato_y_Entorno fe = new Formato_y_Entorno(u, testCase);
		fe.FormatoyEntorno();

//		//      						   PASO 8
		addStep("Seleccionar una opci�n en el campo Tama�o de tienda.");
		fe.TamanoTienda(Tamano);

//		
//		//	  							   PASO 9	

		addStep("Seleccionar una opci�n en el campo Segmento al que pertenece.");
		fe.Segmento(Segmento1);

//		//		                           PASO 10
		addStep("Seleccionar una opci�n en el campo Tipo ubicaci�n de tienda.");
		fe.Ubicacion(Ubicacion1);
		fe.Diferenciador(diferenciador);
//		//		                           PASO 11
		addStep("Seleccionar una opci�n en el campo Tienda espejo.");
		fe.TiendaEspejo(TiendaE);
//		//		                           PASO 12
		addStep("Seleccionar una opci�n en el campo Tienda hermana.");
		fe.TiendaSister(TiendaSis);
//		//		                           PASO 13
		addStep("Capturar el Porcentaje de ajuste.");
		fe.Proce(Porcentaje);
//		//		                           PASO 14
		addStep("Seleccionar una opci�n en el campo Nivel socioecon�mico.");
		fe.NivelSocio(NivelSocioe);
//		//		                           PASO 15
		addStep("Seleccionar una opci�n en el campo Zona econ�mica.");
		fe.ZonaE(ZonaEco);
//		//		                           PASO 16
		addStep("Dar clic en el bot�n Actualizar.");
		fe.clickBotonActualizar();
		
//		//		                           PASO 17
		addStep("Seleccionar en el Link Entorno.");
		fe.BtnEntorno();
//		//		                           PASO 18
		addStep("Seleccionar una opci�n en el campo Tipo de vialidad.");
		fe.Vialidad(Vial);
//		//		                           PASO 19
		addStep("Seleccionar una opci�n en el campo Referencia de Ubicaci�n.");
		fe.UbicacionEntorno(UbicacionEntorno);
//		//		                           PASO 20
		addStep("Dar clic en el bot�n Actualizar.");
		fe.clickBotonActualizarEntorno();
		boolean msj =fe.validacion();
		assertTrue(msj, "No se presento pantalla de cambios guardados");
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_006_Captura_de_Datos_de_Tienda_en_CNT_Formato_y_Entorno";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_Captura_de_Datos_de_Tienda_en_CNT_Formato_y_Entorno";
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
