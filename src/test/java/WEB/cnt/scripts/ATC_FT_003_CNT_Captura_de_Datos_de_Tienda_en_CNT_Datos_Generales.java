package WEB.cnt.scripts;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cnt.om.Datos_Generales;
import WEB.cnt.om.Login;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_003_CNT_Captura_de_Datos_de_Tienda_en_CNT_Datos_Generales extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_003_Captura_Datos_Tienda_CNT_DatosGrales_test(HashMap<String, String> data) throws Exception {
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
		//String Asesor = data.get("Asesor");
		String plaza= data.get("plaza");
		//String mun=data.get("Mun");
		String tel= data.get("Telefono");
		//String Punto= data.get("Punto");
		String No= data.get("No");
		String	Alternativa= data.get("Alternativa");
		String	p_especifique= data.get("Especificar");
		
		String query = "SELECT * FROM (SELECT CODIGO_POSTAL,ID_MUNICIPIO FROM TIENDAS.XXCNT_BIT_GIS_INFO\r\n" + 
				"WHERE ID_PUNTO NOT IN (SELECT T_TIENDAS.ID_PUNTO FROM T_TIENDAS WHERE\r\n" + 
				"T_TIENDAS.CR_SUPERIOR ='CRPLAZA' AND ID_PUNTO IS NOT NULL) and cr_plaza='"+plaza+"' and length(CODIGO_POSTAL)=5 )WHERE\r\n" + 
				"ROWNUM = 1";
		
		//query antiguo
		/*String query2 ="select * from (select CODIGO_POSTAL,ID_MUNICIPIO "
				+ "from tiendas.xxcnt_bit_gis_info  "
				+ "where   id_punto not in "
				+ "(select t_tiendas.id_punto from t_tiendas where T_TIENDAS.CR_SUPERIOR ='"+plaza+"' and id_punto is not null) "
				+ "and municipio = '"+mun+"' ) WHERE ROWNUM = 1  ";	*/


//		// ------------------------------------PASO 1

		addStep("Ingresar al portal "+URL+" con el <Usuario>"+user+""); 
																											// excel
		u = new SeleniumUtil(new ChromeTest(), true); 
		Login pa = new Login(u, testCase); 
		u.get(data.get("Url"));
		u.hardWait(3);
        pa.logginInventarioEspacios(user, password);

	
//		//------------------------------------PASO 2
		addStep("Seleccionar la Plaza y dar clic en el bot�n Continuar.");
		pa.clickSeleccionarPlaza(PlazaE);
		
//		//------------------------------------PASO 3
		addStep("Seleccionar el Distrito y Tienda; a continuaci�n dar clic en el bot�n Continuar");
		pa.clickSeleccionarDistritoTienda(Distrito, Tienda);
		u.waitForLoadPage();
		//pa.ScreenVentanaEmergente();

//		//------------------------------------PASO 4
		Datos_Generales po = new Datos_Generales(u, testCase);
		addStep("Dar Click en Link Datos Generales"); // PENDIENTE
		po.DatosGen();
		
//		//------------------------------------PASO 5
		addStep("Capturar el C�digo Postal y dar clic en la opci�n Buscar, Para obtener un C�digo valido ejecutar el siguiente query en la BD FCIASQA en el Esquema TIENDAS:SELECT ID_PUNTO,CODIGO_POSTAL FROM XXCNT_BIT_GIS_INFO WHERE ID_PUNTO NOT IN (SELECT T_TIENDAS.ID_PUNTO FROM T_TIENDAS WHERE T_TIENDAS.CR_SUPERIOR ='CRPLAZA' AND ID_PUNTO IS NOT NULL);");
        //po.capturaDatosOp(Asesor);

		System.out.println(query);

		SQLResult queryResult = executeQuery(dbFCIAS, query);
		boolean empty = queryResult.isEmpty();
		String CP = "", ID_MUNICIPIO = "";

		if (empty) {

			testCase.addTextEvidenceCurrentStep("No se muestran resultados de la consulta");

		} else {
			
			CP = queryResult.getData(0, "CODIGO_POSTAL");		
			ID_MUNICIPIO = queryResult.getData(0, "ID_MUNICIPIO");	
			
		}
			
		testCase.addQueryEvidenceCurrentStep(queryResult); // Agrega la consulta tal cual que se hace, siempre y cuando
															// solo use una sola consulta

		assertFalse(empty, "No hay datos en la consulta");

		po.capturaDatosGen(CP);
		
		
//		//------------------------------------ PASO 6

		addStep("Seleccionar un Id Punto");
		po.IdPunto();
		//System.out.print(user);
			
//		//								   PASO 7
		addStep("Capturar la Clave de larga distancia");
		po.ClaveLargaDistancia(ID_MUNICIPIO);
		
//		//								   PASO 8
		addStep("Capturar el N�mero telef�nico.");
		po.NTel(tel,No.toUpperCase(), Alternativa, p_especifique);
		
//		//								   PASO 9
		addStep("Seleccionar el Distrito.");
		po.Distrito(Distrito);
		
//		//								   PASO 10
		addStep("Dar clic en el bot�n Actualizar.");
      	po.clickBotonActualizar();
		

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_003_Captura_Datos_Tienda_CNT_Datos_Generales";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_Captura_Datos_Tienda_CNT_DatosGrales";
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
