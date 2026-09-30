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

public class MTC_FT_007_ReprocesoCargaTruncadoaTablaDia extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_007_ReprocesoCargaTruncadoaTablaDia_(HashMap<String, String> data) throws Exception {
		
		/** UTILERIA *********************************************************************/	
		
		utils.sql.SQLUtil dbPos = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);

		/** VARIABLES *********************************************************************/	
		
		String ExistenDatosFuente = "SELECT * FROM TABLENAME";
		String ExistenDatosDestino = "SELECT * FROM TABLENAME";
		String BitacoraCarga = "SELECT * FROM BIODSMKTCOL.TBL_109_BITACORA_CARGA";
		
		/* PASO 1 *********************************************************************/	
				
		addStep("Validar la existencia de los datos en la tabla fuente y tabla destino");
		
		System.out.println(ExistenDatosFuente);		
		SQLResult connectionResult = executeQuery(dbPos, ExistenDatosFuente);

		boolean connection = false;
		if(connectionResult.getRowCount() > 0)
			connection = true;
		
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(connectionResult);					
		} 
		
		// Ahora validamos si existen en el destino
		
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
		
		
		/* PASO 2 *********************************************************************/	

		addStep("Configurar en la tabla de control de carga las fechas de inicio y fin que se procesar�n en la carga inicial");

		
		/* PASO 3 *********************************************************************/	

		addStep("Ejecutar el proceso de carga inicial en Control M");
		
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

		/* PASO 4 *********************************************************************/	

		addStep("Validar el reproceso de los datos en la tabla destino");		
		
		System.out.println(ExistenDatosDestino);		
		connectionResult = executeQuery(dbPos, ExistenDatosDestino);

		connection = false;
		if(connectionResult.getRowCount() > 0)
			connection = true;
		
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(connectionResult);					
		} 
		
		String fecha = connectionResult.getData(0, "FECHA_CARGA");
		
		
		System.out.println(connection);

		assertTrue(connection, "La tabla no contiene informaci�n.");
		
		/* PASO 5 *********************************************************************/	

		addStep("Validar que se genere un registro de la carga realizada en la tabla BIODSMKTCOL.TBL_109_BITACORA_CARGA");
		
		System.out.println(BitacoraCarga);		
		connectionResult = executeQuery(dbPos, BitacoraCarga);

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