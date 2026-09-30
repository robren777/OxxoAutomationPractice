package RDM.articulosDeValor;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import RDM.util.GlobalVariables;
import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.ArticulosValor;
import modelo.BaseExecution;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_003_ValidarEnvioConfigArticulos extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_ValidarEnvioConfigArticulos_test(HashMap<String, String> data) throws Exception {
			
		/*
		 * VARIABLES
		 */
		String item = data.get("item");
		String uda10 = data.get("uda10");
		
		/*
		 * UTILERIAS
		 */	
		SQLUtil dbWm = new SQLUtil(GlobalVariables.DB_HOST_WM, GlobalVariables.DB_USER_WM,
				GlobalVariables.DB_PASSWORD_WM);
		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);
		SQLUtil dbDas = new SQLUtil(GlobalVariables.DB_HOST_DAS, GlobalVariables.DB_USER_DAS,
				GlobalVariables.DB_PASSWORD_DAS);
		SeleniumUtil u = new SeleniumUtil(new IETest());
		ArticulosValor a = new ArticulosValor(u, testCase, data.get("server"));
		
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */			
		// PASO 1
		addStep("Entrar al aplicativo de RDMv10 del cedis en transicion "
				+ "-Ingresar a la siguiente URL en el navegador Internet Explorer "
				+ "<URL valida>"
				+ "Ingresar usuario y password");
		System.out.println("PASO 1"); 

		a.logIn();		
		
		// PASO 2
		addStep("En la pantalla Retek Distribution Management Menu "
				+ "Ingresar a la opcion Support Functions "
				+ "En el submenu seleccionar la opcion Item Setup "
				+ "En el submenu seleccionar la opcion Item Master Editor o "
				+ "XXFC Item Master Editor");
		System.out.println("PASO 2");
		
		a.supportFunctionsItemSetupMaster();
				
		// PASO 3
		addStep("Ingresar el articulo en el campo: "
				+ "Item_id [ID del Articulo] "
				+ "Dar click en \"Execute\"");
		System.out.println("PASO 3");
		
		a.ingresarArticuloExecute(item);
		
		// PASO 4
		addStep("Activar la configuracion del articulo de valor en el Campo Item_master_uda10 con 'Y', y Dar Click en el boton Save"
				+ " Item_master_uda10 [   Y   ] ");
		System.out.println("PASO 4");
		
		boolean configuroCorrectamente = a.activarConfigArticulo(uda10);
		
		assertTrue(configuroCorrectamente, "Item_master_uda10 no se configuro correctamente");
		
		String uda10Value = a.validarModificacionUda(uda10);
		
		assertEquals(uda10, uda10Value, "No se encontro " + uda10 + " se encontro " + uda10Value);
		
		u.close();		
		
		// PASO 5
		addStep("Ejecutar Oracle SQL para Ingresar a la base de datos de WM");
		System.out.println("PASO 5");
		
		testCase.addTextEvidenceCurrentStep("Conectado a: " + dbWm.getHost());
		
		// PASO 6
		addStep("Consultar las tiendas de las Plazas que no han sido migradas del Cedis a "
				+ "probar");
		System.out.println("PASO 6");
		
		// ID_CEDIS NO EXISTE EN ESA TABLA
		String tiendasNoMigradas = String.format("SELECT * FROM WMUSER.WM_OXXO_ROLLOUT_CONFIG "
				+ "WHERE ID_CEDIS = '%s' " // "cedis_en_transicion"
				+ "AND LOC_TYPE = 'S' "
				+ "AND V10 = 'Y' "
				+ "AND V16 = 'N'", "");
		System.out.println(tiendasNoMigradas);

		SQLResult executeQuery = executeQuery(dbWm, tiendasNoMigradas);

		boolean statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron resultados en WMUSER.WM_OXXO_ROLLOUT_CONFIG");
		
		// PASO 7
		addStep("Consultar las tiendas de las Plazas migradas del Cedis a probar");
		System.out.println("PASO 7");
		
		// ID_CEDIS NO EXISTE EN ESA TABLA
		String tiendasMigradas = String.format("SELECT * FROM WMUSER.WM_OXXO_ROLLOUT_CONFIG "
				+ "WHERE ID_CEDIS = '%s' " // "cedis_en_transicion"
				+ "AND LOC_TYPE = 'S' "
				+ "AND V10 = 'N' "
				+ "AND V16 = 'Y'", "");
		System.out.println(tiendasMigradas);

		executeQuery = executeQuery(dbWm, tiendasMigradas);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron resultados en WMUSER.WM_OXXO_ROLLOUT_CONFIG");
		
		// PASO 8
		addStep("Ejecutar Oracle SQL para Ingresar a la base de datos de RMS 10");
		System.out.println("PASO 8");
		
		testCase.addTextEvidenceCurrentStep("Conectado a: " + dbRms.getHost());
		
		// PASO 9 
		addStep("Consultar la insercion del registro del articulo de valor en la tabla "
				+ "ITEM_LOC_TRAITS en RMS 10 para el Cedis en transicion");
		System.out.println("PASO 9");
		
		String insercionArticulo = String.format("SELECT ITEM, LOC, IN_STORE_MARKET_BASKET, "
				+ "CREATE_DATETIME, LAST_UPDATE_ID, LAST_UPDATE_DATETIME "
				+ "FROM RMS100.ITEM_LOC_TRAITS "
				+ "WHERE LOC = '%s'" // cedis_en_transicion
				+ "AND IN_STORE_MARKET_BASKET = 1", "");
		System.out.println(insercionArticulo);

		executeQuery = executeQuery(dbRms, insercionArticulo);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron resultados en RMS100.ITEM_LOC_TRAITS");
		
		// PASO 10
		addStep("Consultar la insercion de los registros del articulo de valor en la tabla "
				+ "POS_MODS en RMS 10 para las tiendas del Paso 10 con tran_type=99 "
				+ "cuando el articulo es marcado como Y");
		System.out.println("PASO 10");
		
		String insercionRegistro = String.format("SELECT * FROM RMS100.POS_MODS "
				+ "WHERE STORE IN('Tiendas Paso 10') " // TIENDAS
				+ "AND ITEM = '%s' "
				+ "AND TRAN_TYPE = 99 "
				+ "ORDER BY CREATE_DATETIME DESC", "", item);
		System.out.println(insercionRegistro);

		executeQuery = executeQuery(dbRms, insercionRegistro);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron resultados en RMS100.POS_MODS");
		
		// PASO 11
		addStep("Ejecutar Oracle SQL para Ingresar a la base de datos del DAS");
		System.out.println("PASO 11");
		
		testCase.addTextEvidenceCurrentStep("Conectado a: " + dbDas.getHost());
		
		// PASO 12
		addStep("Consultar la insercion del registro del articulo de valor en la tabla "
				+ "XXFC_ITEM_LOC_TRAITS en DAS en el esquema XXFC para el Cedis en Transicion");
		System.out.println("PASO 12");
		
		String insercionRegistroDas = String.format("SELECT * FROM XXFC.XXFC_ITEM_LOC_TRAITS "
				+ "WHERE ITEM = '%s' "
				+ "AND LOC IN('ID Cedis virtual en Transici�n') " // TIENDAS
				+ "AND IN_STORE_MARKET_BASKET = 1 "
				+ "AND STATUS = 'S' "
				+ "ORDER BY CREATE_DATETIME_DESC", item, "");
		System.out.println(insercionRegistroDas);

		executeQuery = executeQuery(dbRms, insercionRegistroDas);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron resultados en XXFC_ITEM_LOC_TRAITS");
		
		// PASO 13
		addStep("Consultar la insercion de los registros del articulo de valor en la tabla "
				+ "XXFC_POS_MODS_STG en el esquema XXFC para las tiendas del Paso 11 con tran_type=99 "
				+ "cuando el articulo es marcado como Y");
		System.out.println("PASO 13");
		
		String insercionRegistroDasPos = String.format("SELECT * FROM XXFC.XXFC_POS_MODS_STG "
				+ "WHERE STORE IN('Tiendas Paso 11') " // TIENDAS
				+ "AND ITEM = '%s' "
				+ "AND TRAN_TYPE = 99 "
				+ "AND ELECT_MTK_CLUBS = 1 "
				+ "AND STATUS = 'L' "
				+ "ORDER BY CREATE_DATETIME DESC", "", item);
		System.out.println(insercionRegistroDasPos);

		executeQuery = executeQuery(dbRms, insercionRegistroDasPos);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron resultados en XXFC_POS_MODS_STG");
	}
	
	@Override
	public void beforeTest() {}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Se valida el envio de informacion de los articulos de valor configurados en las "
				+ "pantallas de RDM de un Cedis con al menos una plaza en V16.";	
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_ValidarEnvioConfigArticulos_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
