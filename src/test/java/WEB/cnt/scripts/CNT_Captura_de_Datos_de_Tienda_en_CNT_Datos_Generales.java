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

public class CNT_Captura_de_Datos_de_Tienda_en_CNT_Datos_Generales extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_003_Captura_de_Datos_de_Tienda_en_CNT_Datos_Generales(HashMap<String, String> data)
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
		String Asesor = data.get("Asesor");
		String plaza= data.get("plaza");
		String mun=data.get("Mun");
		String tel= data.get("Telefono");
		String Punto= data.get("Punto");
		String No= data.get("No");
		String	Alternativa= data.get("Alternativa");
		String	p_especifique= data.get("Especificar");
//		String	dis= data.get("Distrito");

		//String query = "SELECT * FROM (SELECT CODIGO_POSTAL,ID_MUNICIPIO FROM TIENDAS.XXCNT_BIT_GIS_INFO WHERE ID_PUNTO NOT IN   (SELECT T_TIENDAS.ID_PUNTO  FROM T_TIENDAS WHERE T_TIENDAS.CR_SUPERIOR ='"+plaza+"') AND ID_PUNTO IS NOT NULL) )WHERE ROWNUM = 1 ";
		String query="select * from (select CODIGO_POSTAL,ID_MUNICIPIO "
				+ "from tiendas.xxcnt_bit_gis_info  "
				+ "where   id_punto not in "
				+ "(select t_tiendas.id_punto from t_tiendas where T_TIENDAS.CR_SUPERIOR ='"+plaza+"' and id_punto is not null) "
				+ "and municipio = '"+mun+"' ) WHERE ROWNUM = 1  ";	
//Chihuahua
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
		Datos_Generales po = new Datos_Generales(u, testCase);
		addStep("Dar Click en Link Datos Generales"); // PENDIENTE
		po.DatosGen();
		
//		//------------------------------------PASO 5
		addStep("Capturar el C�digo Postal y dar clic en la opci�n Buscar, Para obtener un C�digo valido ejecutar el siguiente query en la BD FCIASQA en el Esquema TIENDAS:SELECT ID_PUNTO,CODIGO_POSTAL FROM XXCNT_BIT_GIS_INFO WHERE ID_PUNTO NOT IN (SELECT T_TIENDAS.ID_PUNTO FROM T_TIENDAS WHERE T_TIENDAS.CR_SUPERIOR ='CRPLAZA' AND ID_PUNTO IS NOT NULL);");
//		po.capturaDatosOp(Asesor);

		System.out.println(query);

		SQLResult queryResult = executeQuery(dbFCIAS, query);
		String CP = queryResult.getData(0, "CODIGO_POSTAL");
	
		String ID_MUNICIPIO = queryResult.getData(0, "ID_MUNICIPIO");
	
		boolean empty = queryResult.isEmpty();

		if (empty) {

			testCase.addTextEvidenceCurrentStep("No se muestran resultados de la consulta");

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
		//								   PASO 8
		addStep("Capturar el N�mero telef�nico.");
		po.NTel(tel,No, Alternativa, p_especifique);
//		//								   PASO 9
		addStep("Seleccionar el Distrito.");
		po.Distrito(Distrito);
		//								   PASO 10
		addStep("Dar clic en el bot�n Actualizar.");
      	po.clickBotonActualizar();
		//		System.out.println(GlobalVariables.DB_HOST_FCIASQAS_PORTAL);
//		System.out.println(query);
//		
//		SQLResult queryResult = executeQuery(dbFCIAS, query);
//		
//		boolean empty = queryResult.isEmpty();
//
//		if (empty) {
//			
//			testCase.addTextEvidenceCurrentStep("No se muestran resultados de la consulta");
//		
//		}						
//		testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
//
//		assertFalse(empty, "No se guardo la direccion de envio en la tabla XXINE_CAT_MOVIMIENTOS_SISTEMA");
//		
//		//      						   PASO 8
//		addStep("Seleccionar menu Administrador : Consulta de movimientos");
//		dc.clickSeleccionMenuAdmon();
//		
//		//	  							   PASO 9	
//		AdmonConsultaMovtos cm = new AdmonConsultaMovtos(u, testCase);
//		addStep("Ingresar filtros y dar click en Buscar");
//		u.hardWait(5);
//		cm.clickIngresaFiltros(user);
//
//		//		                           PASO 10
//		addStep("Se muestran los resultados en pantalla con los campos:\r\n"
//				+ "ID\r\n"
//				+ "FECHA\r\n"
//				+ "NOMBRE TIPO DE MOVIMIENTO\r\n"
//				+ "CVE PLAZA\r\n"
//				+ "NOMBRE PLAZA\r\n"
//				+ "NOMBRE SUBPLAZA\r\n"
//				+ "CR TIENDA\r\n"
//				+ "NOMBRE TIENDA\r\n"
//				+ "DIRECCION\r\n"
//				+ "ESPACIO ID\r\n"
//				+ "NOMBRE ESPACIO\r\n"
//				+ "ELIMINADO ID\r\n"
//				+ "CAMPO\r\n"
//				+ "VALOR\r\n"
//				+ "USUARIO\r\n"
//				+ "NOMBRE\r\n"
//				+ "PATERNO\r\n"
//				+ "MATERNO\r\n"
//				+ "POSICION\r\n"
//				+ "");
//        cm.clickMuestraCamposPasoDiez();
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_003_Captura_de_Datos_de_Tienda_en_CNT_Datos_Generales";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_Captura_de_Datos_de_Tienda_en_CNT_Datos_Generales";
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
