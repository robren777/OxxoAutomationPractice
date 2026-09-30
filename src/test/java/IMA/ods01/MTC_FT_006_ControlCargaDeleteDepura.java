package IMA.ods01;

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

public class MTC_FT_006_ControlCargaDeleteDepura extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_ControlCargaDeleteDepura_(HashMap<String, String> data) throws Exception {
		
		/** UTILERIA *********************************************************************/	
		
		utils.sql.SQLUtil dbPos = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);

		/** VARIABLES *********************************************************************/	
		
		String ExistenDatosOrigen = "SELECT * FROM TABLENAME";
		String ExistenDatosDestino = "SELECT * FROM TABLENAME";
		String EliminaRegistrosOrigen = "DELETE FROM TABLENAME WHERE ... = ...";
		String RegistrosEliminadosResp = "SELECT * FROM BKP_DELETE WHERE ... = 'DL$*'";
		String CreacionIndiceEnDest = "SELECT OWNER, INDEX_NAME, TABLE_OWNER, TABLE_NAME, from ALL_INDEXES where table_name='>>';";
		String GeneraRegistroCarga = "SELECT * FROM BIODSMKTCOL.TBL_109_BITACORA_CARGA";
		
		/* PASO 1 *********************************************************************/	
				
		addStep("Validar la existencia de datos en la tabla origen.");
		
		System.out.println(ExistenDatosOrigen);		
		SQLResult connectionResult = executeQuery(dbPos, ExistenDatosOrigen);

		boolean connection = false;
		if(connectionResult.getRowCount() > 0)
			connection = true;
		
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(connectionResult);					
		} 
		
		System.out.println(connection);

		assertTrue(connection, "La tabla no contiene informaci�n.");		
		
		
		/* PASO 2 *********************************************************************/	

		addStep("Validar la existencia de los datos en la tabla destino");
		
		System.out.println(ExistenDatosDestino);		
		connectionResult = executeQuery(dbPos, ExistenDatosDestino);

		connection = false;
		if(connectionResult.getRowCount() > 0)
			connection = true;
		
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(connectionResult);					
		} 
		
		System.out.println(connection);

		assertTrue(connection, "La tabla no contiene informaci�n.");		
		
		
		/* PASO 3 *********************************************************************/	

		addStep("Eliminar registros de la tabla origen");
		
		System.out.println(EliminaRegistrosOrigen);		
		connectionResult = executeQuery(dbPos, EliminaRegistrosOrigen);

		connection = true;
		if(connectionResult.isEmpty())
			connection = false;
		
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(connectionResult);					
		} 
		
		System.out.println(connection);

		assertTrue(connection, "Los registros no fueron eliminados de la tabla origen");				
		
		/* PASO 4 *********************************************************************/	

		addStep("Validar que las modificaciones realizadas a la tabla origen sean registradas en el Journal");		
		
		
		/* PASO 5 *********************************************************************/	

		addStep("Ejecutar el grupo de jobs  DSRODS_RMS100_RMS_TD_03 para el proceso de control de carga en Control M");
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		JSONObject obj = new JSONObject(data.get("query"));
		Control_mInicio CM = new Control_mInicio(u, data.get("ctrlM_user"), data.get("ctrlM_passwd"));
	
		addStep("Login");
		u.get(data.get("ctrlM_url"));
		u.hardWait(20);
		u.waitForLoadPage();
		CM.logOn();
	
		addStep("Inicio de job");
		ControlM control = new ControlM(u, testCase, obj);
	
		control.searchJob();	
		String resultado = control.executeJob();
	
		System.out.println("Resultado de la ejecuci�n: "+resultado);
		System.out.println("Valor de output : "+control.getOutput());
		
		/* PASO 6 *********************************************************************/	

		addStep("Validar que los datos sean eliminados de la tabla destino");
		
		System.out.println(ExistenDatosDestino);		
		connectionResult = executeQuery(dbPos, ExistenDatosDestino);

		connection = false;
		if(connectionResult.getRowCount() == 0)
			connection = true;
		
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(connectionResult);					
		} 
		
		System.out.println(connection);

		assertTrue(connection, "Los registros no se conservan en la tabla destino");	
		
		/* PASO 7 *********************************************************************/	

		addStep("Validar que el journal de la tabla est� vac�o");
		
		/* PASO 8 *********************************************************************/	

		addStep("Validar que los registros eliminados sean respaldados en la tabla BKP_DELETE con prefijo DL$ correspondiente a la tabla");
		
		System.out.println(RegistrosEliminadosResp);		
		connectionResult = executeQuery(dbPos, RegistrosEliminadosResp);

		connection = true;
		if(connectionResult.getRowCount() > 0)
			connection = false;
		
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(connectionResult);					
		} 
		
		System.out.println(connection);

		assertTrue(connection, "Los registros eliminados no se muestran en la tabla de respaldo");	
		
		/* PASO 9 *********************************************************************/	

		addStep("Validar la creaci�n del �ndice en tabla destino, si aplica con respecto a la configuraci�n en el mapeo con el IKM");
		
		System.out.println(CreacionIndiceEnDest);		
		connectionResult = executeQuery(dbPos, CreacionIndiceEnDest);

		connection = true;
		if(connectionResult.getColumnCount() > 0)
			connection = false;
		
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(connectionResult);					
		} 
		
		System.out.println(connection);

		assertTrue(connection, "No se muestra el registro de �ndice correspondiente");	
		
		/* PASO 10 *********************************************************************/	

		addStep("Validar que se genere un registro de la carga realizada en la tabla BIODSMKTCOL.TBL_109_BITACORA_CARGA");
		
		System.out.println(GeneraRegistroCarga);		
		connectionResult = executeQuery(dbPos, GeneraRegistroCarga);

		connection = true;
		if(connectionResult.getColumnCount() > 0)
			connection = false;
		
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(connectionResult);					
		} 
		
		System.out.println(connection);

		assertTrue(connection, "La tabla de bitacora no muestra la carga realizada");	
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
		return "";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "QA Automation";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}