package IMA.ods01;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import IMA.util.UtilsFM;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_002_CargaInicial extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_CargaInicial_test(HashMap<String, String> data) throws Exception {
		
		/** UTILERIA *********************************************************************/	
		
		utils.sql.SQLUtil dbPos = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		
		/** VARIABLES *********************************************************************/	
		
		String tdcExistenDatosOrigen = "SELECT COUNT(*) FROM BIDMMKT.CONTROL_CEDIS_IMPULSO"; // NO INDICA QUE TABLA ES, PUSE ESA PARA PROBAR SOLAMENTE
		String ValidaEstaVaciaDestino = "SELECT * FROM BIDMMKT.BIDMMKT_BITACORA_CARGA"; // NO INDICA QUE TABLA ES, PUSE ESA PARA PROBAR SOLAMENTE
		String ValidaTabladeControl = "SELECT * FROM BIQUERTMKT.CFG_000_CONTROL_CARGA"; // No esta el usuario ni la tabla
		String RegistroEnCarga = "SELECT * FROM BIODSMKTCOL.TBL_109_BITACORA_CARGA WHERE FECHA_CARGA = '%s'";
			
		/* PASO 1 *********************************************************************/	
				
		addStep("Validar la existencia de datos en la tabla origen");
		
		System.out.println(tdcExistenDatosOrigen);		
		SQLResult connectionResult = executeQuery(dbPos, tdcExistenDatosOrigen);

		boolean connection = false;
		int totalRegistrosIni = connectionResult.getRowCount();
		if(totalRegistrosIni > 0)
			connection = true;
		
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(connectionResult);					
		} 
		
		
		
		System.out.println(connection);

		assertTrue(connection, "La tabla no contiene informaci�n.");
		
		
		
		/* PASO 2 *********************************************************************/	

		addStep("Validar que la tabla destino se encuentre vac�a");
		
		System.out.println(ValidaEstaVaciaDestino);		
		connectionResult = executeQuery(dbPos, ValidaEstaVaciaDestino);

		connection = false;
		if(connectionResult.getRowCount() == 0)
			connection = true;

		System.out.println(connection);

		assertTrue(connection, "La tabla contiene informaci�n.");		
		
		/* PASO 3 *********************************************************************/	

		addStep("Validar en la tabla de control de carga las fechas de inicio y fin que se procesar�n en la carga inicial");
		
		System.out.println(ValidaTabladeControl);		
		connectionResult = executeQuery(dbPos, ValidaTabladeControl);
		String fecha_fin = "";
		String fecha_inicio = "";

		boolean inf = connectionResult.isEmpty();
		if (!inf) {
			fecha_fin = connectionResult.getData(0, "COMPLETED_DATE");
			fecha_inicio = connectionResult.getData(0, "RECEIVE_DATE");
			testCase.addQueryEvidenceCurrentStep(connectionResult);
		}

		System.out.println(inf);
		assertTrue(inf, "La tabla no contiene informaci�n.");	
		
		/* PASO 4 *********************************************************************/	

		addStep("Ejecutar el proceso de carga inicial en Control M");
		
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
		
		/* PASO 5 *********************************************************************/	

		addStep("Validar la informaci�n insertada en la tabla destino");
		
		System.out.println(ValidaEstaVaciaDestino);		
		connectionResult = executeQuery(dbPos, ValidaEstaVaciaDestino);

		connection = false;
		if(connectionResult.getRowCount() == totalRegistrosIni)
			connection = true;

		System.out.println(connection);

		assertTrue(connection, "El total de registros de la tabla destino no es igual a los registros de la tabla origen respetando el rango de fechas de control de carga.");	
		
		/* PASO 6 *********************************************************************/	

		addStep("Validar que el campo ODS_FECHA_CARGA de la tabla destino contenga la fecha actual de carga");
		
		/* PASO 7 *********************************************************************/	

		addStep("Validar que se genere un registro de la carga realizada en la tabla BIODSMKTCOL.TBL_109_BITACORA_CARGA (en DW4 QA)");
		// La validaci�n del d�a de hoy se hara en el SQL debido a que es mas facil y practica
		UtilsFM ut = new UtilsFM();
		String fecha = ut.getDateTodayddMMYYYY();
        
        
		RegistroEnCarga = String.format(RegistroEnCarga, fecha);
		System.out.println(RegistroEnCarga);		
		connectionResult = executeQuery(dbPos, RegistroEnCarga);
		
		connection = false;
		if(connectionResult.getRowCount() > 0)
			connection = true;

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
		return "ATC_FT_002_CargaInicial_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}