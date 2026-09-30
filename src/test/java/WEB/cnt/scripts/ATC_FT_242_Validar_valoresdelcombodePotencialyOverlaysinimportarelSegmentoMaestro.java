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

public class ATC_FT_242_Validar_valoresdelcombodePotencialyOverlaysinimportarelSegmentoMaestro extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void  ATC_FT_242_Validar_valoresdelcombodePotencialyOverlaysinimportarelSegmentoMaestro_test(HashMap<String, String> data)
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
	
		String diferenciador = data.get("Diferenciador");
	
		String Hogar_Bajo1= data.get("Hogar_Bajo1");
		String Hogar_Medio1= data.get("Hogar_Medio1");
		String Hogar_Alto1 =data.get("Hogar_Alto1");
		String Comida_Bajo1= data.get("Comida_Bajo1");
		String Comida_Medio1= data.get("Comida_Medio1");
		String Comida_Alto1 =data.get("Comida_Alto1");
		String Desayuno_Bajo1= data.get("Desayuno_Bajo1");
		String Desayuno_Medio1= data.get("Desayuno_Medio1");
		String Desayuno_Alto1 =data.get("Desayuno_Alto1");
		
		String o1= data.get("o1");
		String o2= data.get("o2");
		String o3= data.get("o3");
		String o1medio = data.get("o1medio");
		String o2medio= data.get("o2medio");
		String o3medio= data.get("o3medio");
		String o1bajo= data.get("o1bajo");
		String o2bajo= data.get("o2bajo");
		String o3bajo= data.get("o3bajo");
		String bajo= data.get("Hogar_Bajo1");
		String medio= data.get("Hogar_Medio1");
		String alto= data.get("Hogar_Alto1");
		
		String o1c= data.get("o1c");
		String o2c= data.get("o2c");
		String o3c= data.get("o3c");
		String o1medioc = data.get("o1medioc");
		String o2medioc= data.get("o2medioc");
		String o3medioc= data.get("o3medioc");
		String o1bajoc= data.get("o1bajoc");
		String o2bajoc= data.get("o2bajoc");
		String o3bajoc= data.get("o3bajoc");
		
		String o1d= data.get("o1d");
		String o2d= data.get("o2d");
		String o3d= data.get("o3d");
		String o1mediod = data.get("o1mediod");
		String o2mediod= data.get("o2mediod");
		String o3mediod= data.get("o3mediod");
		String o1bajod= data.get("o1bajod");
		String o2bajod= data.get("o2bajod");
		String o3bajod= data.get("o3bajod");
	
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
		addStep("Seleccionar el Distrito y Tienda con estatus Nueva; a continuaci�n dar clic en el bot�n Continuar");
		pa.clickSeleccionarDistritoTienda(Distrito, Tienda);

//		//------------------------------------PASO 4
	
		// PASO 7
		addStep("Dar clic en el link Formato y Entorno.");
		Formato_y_Entorno fe = new Formato_y_Entorno(u, testCase);
		fe.FormatoyEntorno();

//		//      						   PASO 8
		addStep("Usuario selecciona un Concepto diferenciador que no sea Excepci�n y despu�s la opci�n de \"Segmento de Tienda\".");
		
		fe.Framedatos();
		fe.Diferenciador(diferenciador);
		fe.ClicBtbSegmentoTienda();
		fe.alertSegmentoTienda();
		boolean exist= fe.ElementDis();
		assertTrue(exist,"NO existen los campos");

		addStep("Usuario selecciona el combo de \"Potencial Hogar\"");
		fe.ElementosPotencialHogar(Hogar_Bajo1,Hogar_Medio1,Hogar_Alto1);
		addStep("Usuario selecciona el combo de \"Potencial Comida\"");
		fe.ElementosPotencialComida(Comida_Bajo1,Comida_Medio1,Comida_Alto1);
		addStep("Usuario selecciona el combo de \"Potencial Desayuno\"");
		fe.ElementosPotencialDesayuno(Desayuno_Bajo1,Desayuno_Medio1,Desayuno_Alto1);
	
		
		addStep("Usuario selecciona en el combo de \"Overlay Hogar\"");
		//addStep(" Usuario selecciona en el combo de \"Potencial Hogar\" el valor de \"Hogar_Alto\" para despues abrir las opciones del combo de \"Overlay Hogar\"");
		fe.ElementosPotencialHogar1opcion(alto);
		fe.OpcionesOverlayHogar(o1,o2,o3);
		
	//	addStep("Usuario selecciona en el combo de \"Potencial Hogar\" el valor de \"Hogar_Medio\" para despues abrir las opciones del combo de \"Overlay Hogar\" ");
		fe.ElementosPotencialHogar1opcion(medio);
		fe.OpcionesOverlayHogar(o1medio,o2medio,o3medio);
		
	//	addStep("Usuario selecciona en el combo de \"Potencial Hogar\" el valor de \"Hogar_Bajo\" para despues abrir las opciones del combo de \"Overlay Hogar\"");
		fe.ElementosPotencialHogar1opcion(bajo);
		fe.OpcionesOverlayHogar(o1bajo,o2bajo,o3bajo);
	
		addStep("Usuario selecciona en el combo de \"Overlay Comida\"");
		fe.ElementosPotencialComida1opcion(Comida_Alto1);
		fe.OpcionesOverlayComida(o1c,o2c,o3c);
		
	//	addStep("Usuario selecciona en el combo de \"Potencial Hogar\" el valor de \"Hogar_Medio\" para despues abrir las opciones del combo de \"Overlay Hogar\" ");
		fe.ElementosPotencialComida1opcion(Comida_Medio1);
		fe.OpcionesOverlayComida(o1medioc,o2medioc,o3medioc);
		
	//	addStep("Usuario selecciona en el combo de \"Potencial Hogar\" el valor de \"Hogar_Bajo\" para despues abrir las opciones del combo de \"Overlay Hogar\"");
		fe.ElementosPotencialComida1opcion(Comida_Bajo1);
		fe.OpcionesOverlayComida(o1bajoc,o2bajoc,o3bajoc);
		
		addStep("Usuario selecciona en el combo de \"Overlay Desayuno\"");
		fe.ElementosPotencialDesayuno1opcion(Desayuno_Alto1);
		fe.OpcionesOverlayDesayuno(o1d,o2d,o3d);
		
	//	addStep("Usuario selecciona en el combo de \"Potencial Hogar\" el valor de \"Hogar_Medio\" para despues abrir las opciones del combo de \"Overlay Hogar\" ");
		fe.ElementosPotencialDesayuno1opcion(Desayuno_Medio1);
		fe.OpcionesOverlayDesayuno(o1mediod,o2mediod,o3mediod);
		
	//	addStep("Usuario selecciona en el combo de \"Potencial Hogar\" el valor de \"Hogar_Bajo\" para despues abrir las opciones del combo de \"Overlay Hogar\"");
		fe.ElementosPotencialDesayuno1opcion(Desayuno_Bajo1);
		fe.OpcionesOverlayDesayuno(o1bajod,o2bajod,o3bajod);
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_242_Validar_valoresdelcombodePotencialyOverlaysinimportarelSegmentoMaestror_test";
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
		u.getDriver().quit();
	}

}
