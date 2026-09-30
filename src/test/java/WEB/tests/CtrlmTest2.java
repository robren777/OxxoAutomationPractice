package WEB.tests;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class CtrlmTest2 extends BaseExecution {
//  SeleniumUtil u = new SeleniumUtil(new FirefoxTest(), true);
	//SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);

	@Test(dataProvider = "data-provider", enabled = true)
	public void Testing_ctrlM(HashMap<String, String> data) throws Exception {

		SQLUtil dbFCIASQAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL, GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		
		String negocio = data.get("Negocio");
		String db = "";
		String folio = "";
		switch(negocio.toUpperCase()) {
			case "BARA": 
				db = "XXINE_BA";
				folio = "12NEO_ACCESORIOS_ABR2021_51889";
			break;
			
			default: db = "XXINE";
			folio = "P782022_10MON_FALTANTE-ADICIONAL_52352";
			break;
		
		}
		
		
		
	addStep("Ingresar a la BD del esquema XXINE y validar que se guarde el pedido correctamente en las tablas XXINE_PEDIDOS_HEADER/ XXINE_PEDIDOS_DETAIL con estatus borrador (estatus=1)");
		
	//query = "SELECT * FROM " + db + ".XXINE_PEDIDOS_HEADER WHERE FOLIO_PEDIDO = '" + folio + "'";
	//ExecuteQuery = executeQuery(dbFCIASQAS, query);
	//StatusQuery = ExecuteQuery.isEmpty();
	
	String queryH1 = "SELECT PEDIDO_HEADER_ID, FOLIO_PEDIDO, TIPO_PEDIDO_ID, ESTATUS FROM " + db + ".XXINE_PEDIDOS_HEADER WHERE FOLIO_PEDIDO = '" + folio + "'";
	String queryH2 = "SELECT PLAZA_ID, PERIODO, FECHA_PEDIDO, LAST_UPDATE_DATE, LAST_UPDATED_BY FROM " + db + ".XXINE_PEDIDOS_HEADER WHERE FOLIO_PEDIDO = '" + folio + "'";
	String queryH3 = "SELECT SUBPLAZA_ID, CREATION_DATE, CREATED_BY FROM " + db + ".XXINE_PEDIDOS_HEADER WHERE FOLIO_PEDIDO = '" + folio + "'";
			
	SQLResult ExecuteQueryH1 = executeQuery(dbFCIASQAS, queryH1);
	SQLResult ExecuteQueryH2 = executeQuery(dbFCIASQAS, queryH2);
	SQLResult ExecuteQueryH3 = executeQuery(dbFCIASQAS, queryH3);
	boolean StatusQueryH = ExecuteQueryH1.isEmpty();
	
	if (!StatusQueryH) {
		if (ExecuteQueryH1.getData(0, "ESTATUS").equals("1")) {
			testCase.addTextEvidenceCurrentStep("Se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_HEADER");
		} else {
			testCase.addTextEvidenceCurrentStep("No se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_HEADER");
		}
	} else {			
		testCase.addTextEvidenceCurrentStep("No se encontro ningun registro con el periodo mostrado en pantalla");
	}
	testCase.addQueryEvidenceCurrentStep(ExecuteQueryH1);
	testCase.addQueryEvidenceCurrentStep(ExecuteQueryH2);
	testCase.addQueryEvidenceCurrentStep(ExecuteQueryH3);
	
	
	//query = "SELECT * FROM " + db + ".XXINE_PEDIDOS_DETAIL WHERE PEDIDO_HEADER_ID = '" + ExecuteQuery.getData(0, "PEDIDO_HEADER_ID") + "'";
	//ExecuteQuery = executeQuery(dbFCIASQAS, query);
	//StatusQuery = ExecuteQuery.isEmpty();
	
	String queryD1 = "SELECT PEDIDO_HEADER_ID, PEDIDO_HEADER_ID, TIENDA_ID, PAQUETE_ID, PROMOCION_ID FROM " + db + ".XXINE_PEDIDOS_DETAIL WHERE PEDIDO_HEADER_ID = '" + ExecuteQueryH1.getData(0, "PEDIDO_HEADER_ID") + "'";	
	String queryD2 = "SELECT POP_ID, TIPO_POP_ID, CANTIDAD, LAST_UPDATE_DATE, LAST_UPDATED_BY FROM " + db + ".XXINE_PEDIDOS_DETAIL WHERE PEDIDO_HEADER_ID = '" + ExecuteQueryH1.getData(0, "PEDIDO_HEADER_ID") + "'";
	String queryD3 = "SELECT CREATION_DATE, CREATED_BY, COMENTARIOS FROM " + db + ".XXINE_PEDIDOS_DETAIL WHERE PEDIDO_HEADER_ID = '" + ExecuteQueryH1.getData(0, "PEDIDO_HEADER_ID") + "'";
	
	SQLResult ExecuteQueryD1 = executeQuery(dbFCIASQAS, queryD1);
	SQLResult ExecuteQueryD2 = executeQuery(dbFCIASQAS, queryD2);
	SQLResult ExecuteQueryD3 = executeQuery(dbFCIASQAS, queryD3);
	boolean StatusQueryD = ExecuteQueryD1.isEmpty();
	
	if (!StatusQueryD) {
		testCase.addTextEvidenceCurrentStep("Se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_DETAIL");
	} else {			
		testCase.addTextEvidenceCurrentStep("No se encontro ningun registro con el periodo mostrado en pantalla");
	}
	testCase.addQueryEvidenceCurrentStep(ExecuteQueryD1);
	testCase.addQueryEvidenceCurrentStep(ExecuteQueryD2);
	testCase.addQueryEvidenceCurrentStep(ExecuteQueryD3);

	//										PASO 18
	
	addStep("Ingresar a la BD del esquema XXINE y validar que se guarde el pedido correctamente en las tablas XXINE_PEDIDOS_HEADER/ XXINE_PEDIDOS_DETAIL con estatus borrador  (estatus=1).");
	
	//query = "SELECT * FROM " + db + ".XXINE_PEDIDOS_HEADER WHERE FOLIO_PEDIDO = '" + folio + "'";
	//ExecuteQuery = executeQuery(dbFCIASQAS, query);
	//StatusQuery = ExecuteQuery.isEmpty();
	
	queryH1 = "SELECT PEDIDO_HEADER_ID, FOLIO_PEDIDO, TIPO_PEDIDO_ID, ESTATUS FROM " + db + ".XXINE_PEDIDOS_HEADER WHERE FOLIO_PEDIDO = '" + folio + "'";
	queryH2 = "SELECT PLAZA_ID, PERIODO, FECHA_PEDIDO, LAST_UPDATE_DATE, LAST_UPDATED_BY FROM " + db + ".XXINE_PEDIDOS_HEADER WHERE FOLIO_PEDIDO = '" + folio + "'";
	queryH3 = "SELECT SUBPLAZA_ID, CREATION_DATE, CREATED_BY FROM " + db + ".XXINE_PEDIDOS_HEADER WHERE FOLIO_PEDIDO = '" + folio + "'";
	
	ExecuteQueryH1 = executeQuery(dbFCIASQAS, queryH1);
	ExecuteQueryH2 = executeQuery(dbFCIASQAS, queryH2);
	ExecuteQueryH3 = executeQuery(dbFCIASQAS, queryH3);
	boolean StatusQueryH2 = ExecuteQueryH1.isEmpty();
	
	if (!StatusQueryH2) {
		if (ExecuteQueryH1.getData(0, "ESTATUS").equals("1")) {
			testCase.addTextEvidenceCurrentStep("Se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_HEADER");
		} else {
			testCase.addTextEvidenceCurrentStep("No se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_HEADER");
		}
	} else {			
		testCase.addTextEvidenceCurrentStep("No se encontro ningun registro con el periodo mostrado en pantalla");
	}
	testCase.addQueryEvidenceCurrentStep(ExecuteQueryH1);
	testCase.addQueryEvidenceCurrentStep(ExecuteQueryH2);
	testCase.addQueryEvidenceCurrentStep(ExecuteQueryH3);
	
	
	//query = "SELECT * FROM " + db + ".XXINE_PEDIDOS_DETAIL WHERE PEDIDO_HEADER_ID = '" + ExecuteQuery.getData(0, "PEDIDO_HEADER_ID") + "'";
	//ExecuteQuery = executeQuery(dbFCIASQAS, query);
	//StatusQuery = ExecuteQuery.isEmpty();
	
	queryD1 = "SELECT PEDIDO_HEADER_ID, PEDIDO_HEADER_ID, TIENDA_ID, PAQUETE_ID, PROMOCION_ID FROM " + db + ".XXINE_PEDIDOS_DETAIL WHERE PEDIDO_HEADER_ID = '" + ExecuteQueryH1.getData(0, "PEDIDO_HEADER_ID") + "'";	
	queryD2 = "SELECT POP_ID, TIPO_POP_ID, CANTIDAD, LAST_UPDATE_DATE, LAST_UPDATED_BY FROM " + db + ".XXINE_PEDIDOS_DETAIL WHERE PEDIDO_HEADER_ID = '" + ExecuteQueryH1.getData(0, "PEDIDO_HEADER_ID") + "'";
	queryD3 = "SELECT CREATION_DATE, CREATED_BY, COMENTARIOS FROM " + db + ".XXINE_PEDIDOS_DETAIL WHERE PEDIDO_HEADER_ID = '" + ExecuteQueryH1.getData(0, "PEDIDO_HEADER_ID") + "'";
	ExecuteQueryD1 = executeQuery(dbFCIASQAS, queryD1);
	ExecuteQueryD2 = executeQuery(dbFCIASQAS, queryD2);
	ExecuteQueryD3 = executeQuery(dbFCIASQAS, queryD3);
	boolean StatusQueryD2 = ExecuteQueryD1.isEmpty();
	
	if (!StatusQueryD2) {
		testCase.addTextEvidenceCurrentStep("Se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_DETAIL");
	} else {			
		testCase.addTextEvidenceCurrentStep("No se encontro ningun registro con el periodo mostrado en pantalla");
	}
	testCase.addQueryEvidenceCurrentStep(ExecuteQueryD1);
	testCase.addQueryEvidenceCurrentStep(ExecuteQueryD2);
	testCase.addQueryEvidenceCurrentStep(ExecuteQueryD3);
	

	
	//		*								PASO 21
	
	addStep("Ingresar a la BD del esquema XXINE y validar que se guarde el pedido correctamente en las tablas XXINE_PEDIDOS_HEADER/ XXINE_PEDIDOS_DETAIL con estatus por autorizar (estatus=2).");
	//query = "SELECT * FROM " + db + ".XXINE_PEDIDOS_HEADER WHERE FOLIO_PEDIDO = '" + folio + "'";
	//ExecuteQuery = executeQuery(dbFCIASQAS, query);
	//StatusQuery = ExecuteQuery.isEmpty();
	
	queryH1 = "SELECT PEDIDO_HEADER_ID, FOLIO_PEDIDO, TIPO_PEDIDO_ID, ESTATUS FROM " + db + ".XXINE_PEDIDOS_HEADER WHERE FOLIO_PEDIDO = '" + folio + "'";
	queryH2 = "SELECT PLAZA_ID, PERIODO, FECHA_PEDIDO, LAST_UPDATE_DATE, LAST_UPDATED_BY FROM " + db + ".XXINE_PEDIDOS_HEADER WHERE FOLIO_PEDIDO = '" + folio + "'";
	queryH3 = "SELECT SUBPLAZA_ID, CREATION_DATE, CREATED_BY FROM " + db + ".XXINE_PEDIDOS_HEADER WHERE FOLIO_PEDIDO = '" + folio + "'";
	
	ExecuteQueryH1 = executeQuery(dbFCIASQAS, queryH1);
	ExecuteQueryH2 = executeQuery(dbFCIASQAS, queryH2);
	ExecuteQueryH3 = executeQuery(dbFCIASQAS, queryH3);
	boolean StatusQueryH3 = ExecuteQueryH1.isEmpty();
	
	
	if (!StatusQueryH3) {
		if (ExecuteQueryH1.getData(0, "ESTATUS").equals("2")) {
			testCase.addTextEvidenceCurrentStep("Se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_HEADER");
		} else {
			testCase.addTextEvidenceCurrentStep("No se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_HEADER");
		}
	} else {			
		testCase.addTextEvidenceCurrentStep("No se encontro ningun registro con el periodo mostrado en pantalla");
	}
	testCase.addQueryEvidenceCurrentStep(ExecuteQueryH1);
	testCase.addQueryEvidenceCurrentStep(ExecuteQueryH2);
	testCase.addQueryEvidenceCurrentStep(ExecuteQueryH3);
	
	
	//query = "SELECT * FROM " + db + ".XXINE_PEDIDOS_DETAIL WHERE PEDIDO_HEADER_ID = '" + ExecuteQuery.getData(0, "PEDIDO_HEADER_ID") + "'";
	//ExecuteQuery = executeQuery(dbFCIASQAS, query);
	//StatusQuery = ExecuteQuery.isEmpty();
	
	queryD1 = "SELECT PEDIDO_HEADER_ID, PEDIDO_HEADER_ID, TIENDA_ID, PAQUETE_ID, PROMOCION_ID FROM " + db + ".XXINE_PEDIDOS_DETAIL WHERE PEDIDO_HEADER_ID = '" + ExecuteQueryH1.getData(0, "PEDIDO_HEADER_ID") + "'";	
	queryD2 = "SELECT POP_ID, TIPO_POP_ID, CANTIDAD, LAST_UPDATE_DATE, LAST_UPDATED_BY FROM " + db + ".XXINE_PEDIDOS_DETAIL WHERE PEDIDO_HEADER_ID = '" + ExecuteQueryH1.getData(0, "PEDIDO_HEADER_ID") + "'";
	queryD3 = "SELECT CREATION_DATE, CREATED_BY, COMENTARIOS FROM " + db + ".XXINE_PEDIDOS_DETAIL WHERE PEDIDO_HEADER_ID = '" + ExecuteQueryH1.getData(0, "PEDIDO_HEADER_ID") + "'";		
	ExecuteQueryD1 = executeQuery(dbFCIASQAS, queryD1);
	ExecuteQueryD2 = executeQuery(dbFCIASQAS, queryD2);
	ExecuteQueryD3 = executeQuery(dbFCIASQAS, queryD3);
	boolean StatusQueryD3 = ExecuteQueryD1.isEmpty();
	
	if (!StatusQueryD3) {
		testCase.addTextEvidenceCurrentStep("Se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_DETAIL");
	} else {			
		testCase.addTextEvidenceCurrentStep("No se encontro ningun registro con el periodo mostrado en pantalla");
	}
	testCase.addQueryEvidenceCurrentStep(ExecuteQueryD1);
	testCase.addQueryEvidenceCurrentStep(ExecuteQueryD2);
	testCase.addQueryEvidenceCurrentStep(ExecuteQueryD3);


	}

	@Override
	public String setTestFullName() {
// TODO Auto-generated method stub
		return "CtrlmTest";
	}

	@Override
	public String setTestDescription() {
// TODO Auto-generated method stub
		return "Testing_ctrlM como una prueba";
	}

	@Override
	public String setTestDesigner() {
// TODO Auto-generated method stub
		return "Nombre del automatizador Core";
	}

	@Override
	public String setTestInstanceID() {
// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
// TODO Auto-generated method stub
		return "Prerequisitos";
	}

	@Override
	public void beforeTest() {
// TODO Auto-generated method stub

	}

	@Override
	public void baseAfterTest() {
		

	}

}