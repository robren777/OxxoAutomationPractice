package IMA.Paths_ExtraccionDirecta_Guia_Recolectora_03RevisionDestino;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_13_ValidarCarga_BIDMMKT_DIM_PRODUCTO_EXPULSION extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_13_ValidarCarga_BIDMMKT_DIM_PRODUCTO_EXPULSION_Test(HashMap<String, String> data) throws Exception {
		//utils.sql.SQLUtil rms_mex = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS_MEX, GlobalVariables.DB_USER_RMS_MEX, GlobalVariables.DB_PASSWORD_RMS_MEX);
		utils.sql.SQLUtil fcdw4qa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);
		//utils.sql.SQLUtil fcodsqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_IMA, GlobalVariables.DB_USER_IMA, GlobalVariables.DB_PASSWORD_IMA);
		
		String queryStep1 = "SELECT * FROM BIDMMKT.DIM_VIGENCIA_TIENDA";
		String queryStep2 = "SELECT * FROM BIQUERYMKT.VM_BIMK_XXFC_ITEM_LOC_MERMA";
		String queryStep3 = "SELECT * FROM DWADMIN.CATCONVTIENDA";
		String queryStep4 = "SELECT * FROM BIDMMKT.DIM_PRODUCTORETEK";
		String queryStep5 = "SELECT * FROM BIDMMKT.PRODUCTOS_EXPULSION";
		String queryStep6 = "SELECT D.PRODUCTO_KEY, D.TIENDA_KEY, PRODUCTOS.FECHAINICIO_EXPULSION, PRODUCTOS.FECHAFIN_EXPULSION, D.FECHA_INACTIVACION, SYSDATE, PRODUCTOS.METHOD_EXPULSION "
				+ "FROM ( "
				+ "SELECT C.ID_PRODUCTO, B.ID_TIENDA, A.START_DATE, A.END_DATE, 1 AS FLAG_EXPULSION , SYSDATE FECHACARGA, A.METHOD_EXPULSION "
				+ "FROM BIQUERYMKT.VM_BIMK_XXFC_ITEM_LOC_MERMA A,DWADMIN.CATCONVTIENDA B, BIDMMKT.DIM_PRODUCTO_RETEK C "
				+ "WHERE A.LOC = B.ID_RETEK AND A.UPC_CVE = C.ID_PRODUCTO) PRODUCTOS, BIDMMKT.DIM_VIGENCIA_TIENDA D "
				+ "WHERE PRODUCTOS.ID_PRODUCTO = D.ID_PRODUCTO AND PRODUCTOS.ID_TIENDA=D.ID_TIENDA";
		String queryStep8 = "SELECT * FROM BIDMMKT.DIM_PRODUCTO_EXPULSION";
		
		// Paso 1
		addStep("Se verifica la siguiente tabla origen contenga datos. SELECT * FROM BIDMMKT.DIM_VIGENCIA_TIENDA");
		System.out.println(queryStep1);
		SQLResult Res = executeQuery(fcdw4qa, queryStep1);

		boolean connection = false;
		if(Res.getRowCount()>0)
			connection = true;
	
		System.out.println(connection);
		testCase.addQueryEvidenceCurrentStep(Res);		

		assertTrue(connection, "La tabla no contiene informaci�n.");
		
		// Paso 2
		addStep("Se verifica la siguiente tabla origen contenga datos. SELECT * FROM BIQUERYMKT.VM_BIMK_XXFC_ITEM_LOC_MERMA");
		System.out.println(queryStep2);
		Res = executeQuery(fcdw4qa, queryStep2);

		connection = false;
		if(Res.getRowCount()>0)
			connection = true;

		System.out.println(connection);
		testCase.addQueryEvidenceCurrentStep(Res);		

		assertTrue(connection, "La tabla no contiene informaci�n.");
		
		// Paso 3
		addStep("Se verifica la siguiente tabla origen contenga datos. SELECT * FROM DWADMIN.CATCONVTIENDA");
		System.out.println(queryStep3);
		Res = executeQuery(fcdw4qa, queryStep3);

		connection = false;
		if(Res.getRowCount()>0)
			connection = true;

		System.out.println(connection);
		testCase.addQueryEvidenceCurrentStep(Res);		

		assertTrue(connection, "La tabla no contiene informaci�n.");
		
		// Paso 4
		addStep("Se verifica la siguiente tabla origen contenga datos. SELECT * FROM BIDMMKT.DIM_PRODUCTORETEK");
		System.out.println(queryStep4);
		Res = executeQuery(fcdw4qa, queryStep4);

		connection = false;
		if(Res.getRowCount()>0)
			connection = true;

		System.out.println(connection);
		testCase.addQueryEvidenceCurrentStep(Res);		

		assertTrue(connection, "La tabla no contiene informaci�n.");
		
		// Paso 5
		addStep("Se verifica la siguiente tabla origen contenga datos. SELECT * FROM BIDMMKT.PRODUCTOS_EXPULSION");
		System.out.println(queryStep5);
		Res = executeQuery(fcdw4qa, queryStep5);

		connection = false;
		if(Res.getRowCount()>0)
			connection = true;

		System.out.println(connection);
		testCase.addQueryEvidenceCurrentStep(Res);		

		assertTrue(connection, "La tabla no contiene informaci�n.");
		
		// Paso 6
		addStep("Se verifican los datos que ser�n insertados en la tabla destino, mediante la siguiente consulta.\n"
				+ " \n"
				+ "QUERY TENTATIVO\n"
				+ " \n"
				+ "SELECT D.PRODUCTO_KEY, D.TIENDA_KEY, PRODUCTOS.FECHAINICIO_EXPULSION, PRODUCTOS.FECHAFIN_EXPULSION, D.FECHA_INACTIVACION, SYSDATE, PRODUCTOS.METHOD_EXPULSION\n"
				+ "FROM (\n"
				+ "SELECT C.ID_PRODUCTO, B.ID_TIENDA, A.START_DATE, A.END_DATE, 1 AS FLAG_EXPULSION , SYSDATE FECHACARGA, A.METHOD_EXPULSION\n"
				+ "FROM BIQUERYMKT.VM_BIMK_XXFC_ITEM_LOC_MERMA A,DWADMIN.CATCONVTIENDA B, BIDMMKT.DIM_PRODUCTO_RETEK C\n"
				+ "WHERE A.LOC = B.ID_RETEK AND A.UPC_CVE = C.ID_PRODUCTO) PRODUCTOS, BIDMMKT.DIM_VIGENCIA_TIENDA D\n"
				+ "WHERE PRODUCTOS.ID_PRODUCTO = D.ID_PRODUCTO AND PRODUCTOS.ID_TIENDA=D.ID_TIENDA");
		System.out.println(queryStep6);
		Res = executeQuery(fcdw4qa, queryStep6);

		connection = false;
		if(Res.getRowCount()>0)
			connection = true;

		System.out.println(connection);
		testCase.addQueryEvidenceCurrentStep(Res);		

		assertTrue(connection, "La tabla no contiene informaci�n.");		
		
		// Paso 7
		JSONObject obj = new JSONObject(data.get("job"));

		addStep("Se verifica la correcta ejecuci�n del Job en ambiente de QA\n"
				+ "Job: BIMKT_EXPMER_MAP_DIMQA\n"
				+ "Grupo: TQABI_MKT_EXPMER");
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);	
		Control_mInicio CM = new Control_mInicio(u, GlobalVariables.USER_CTRL_M_JH, GlobalVariables.PASSWORD_CTRL_M_JH);
		u.get(data.get("ctrl_url"));
		u.hardWait(20);
		u.waitForLoadPage();
		CM.logOn();

		ControlM control = new ControlM(u, testCase, obj);
		control.searchJob();
		String resultado = control.executeJob();

		System.out.println("Resultado de la ejecuci�n: "+resultado);
		System.out.println("Valor de output : "+control.getOutput());
		u.close();		
		
		// Paso 8
		addStep("Se validan los datos de la tabla destino, los cuales deben ser los mismos que el paso 6. SELECT * FROM BIDMMKT.DIM_PRODUCTO_EXPULSION");
		System.out.println(queryStep8);
		Res = executeQuery(fcdw4qa, queryStep8);

		connection = false;
		if(Res.getRowCount()>0)
			connection = true;

		System.out.println(connection);
		testCase.addQueryEvidenceCurrentStep(Res);		

		assertTrue(connection, "La tabla no contiene informaci�n.");
	}
	
  
	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_13_ValidarCarga_BIDMMKT_DIM_PRODUCTO_EXPULSION";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se validara los datos sean cargados de manera correcta a la tabla BIDMMKT.DIM_PRODUCTO_EXPULSION";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATIZACION";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
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
}