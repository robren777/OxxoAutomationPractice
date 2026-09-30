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

public class MTC_FT_004_ControlCargaUpdate extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_ControlCargaUpdate_(HashMap<String, String> data) throws Exception {
		
		/** UTILERIA *********************************************************************/	
		
		utils.sql.SQLUtil dbPos = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);

		/** VARIABLES *********************************************************************/	
		
		String ExistenDatosOrigen = "SELECT * FROM TABLENAME";
		String ExistenDatosDestino = "SELECT * FROM TABLENAME";
		String DatosActualizados = "SELECT * FROM TABLENAME";
		String ValidaRegistrodeCarga = "SELECT * FROM BIODSMKTCOL.TBL_109_BITACORA_CARGA";
		
		/* PASO 1 *********************************************************************/	
				
		addStep("Validar la existencia de datos en la tabla origen");
		
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

		addStep("Modificar registros en la tabla origen");
				
		
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

		addStep("Validar que los datos actualizados en la tabla origen se encuentren en la tabla destino");
		
		System.out.println(DatosActualizados);		
		connectionResult = executeQuery(dbPos, DatosActualizados);

		connection = false;
		if(connectionResult.getRowCount() > 0)
			connection = true;
		
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(connectionResult);					
		} 
		
		System.out.println(connection);

		assertTrue(connection, "La tabla no contiene informaci�n.");		
		
		/* PASO 7 *********************************************************************/	

		addStep("Validar que el journal de la tabla est� vac�o");
		
		/* PASO 8 *********************************************************************/	

		addStep("Validar que se genere un registro de la carga realizada en la tabla BIODSMKTCOL.TBL_109_BITACORA_CARGA (en DW4 QA)");
		
		System.out.println(ValidaRegistrodeCarga);		
		connectionResult = executeQuery(dbPos, ValidaRegistrodeCarga);

		connection = false;
		if(connectionResult.getRowCount() > 0)
			connection = true;
		
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(connectionResult);					
		} 
		
		System.out.println(connection);

		assertTrue(connection, "La tabla no contiene informaci�n.");
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