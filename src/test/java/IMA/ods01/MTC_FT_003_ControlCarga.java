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

public class MTC_FT_003_ControlCarga extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_ControlCarga_(HashMap<String, String> data) throws Exception {
		
		/** UTILERIA *********************************************************************/	
		
		utils.sql.SQLUtil dbPos = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		
		/** VARIABLES *********************************************************************/	
		
		String InsertarRegistrosOrigen = "INSERT INTO TABLENAME"; // No se encuentra la sintaxis completa que se utilizar�a
		String TablaDestino = "SELECT * FROM *";
		String RegistroEnCarga = "SELECT * FROM BIODSMKTCOL.TBL_109_BITACORA_CARGA WHERE FECHA_CARGA = '%s'";

		/* PASO 1 *********************************************************************/	
				
		addStep("Insertar registros en la tabla origen");
		
		System.out.println(InsertarRegistrosOrigen);		
		SQLResult connectionResult = executeQuery(dbPos, InsertarRegistrosOrigen);

		boolean connection = false;
		if(!connectionResult.isEmpty())
			connection = true;
		
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(connectionResult);					
		} 

		assertTrue(connection, "La tabla no contiene informaci�n.");
		
		
		
		/* PASO 2 *********************************************************************/	

		addStep("Validar que las modificaciones realizadas a la tabla origen sean registradas en el Journal");
		
		
		/* PASO 3 *********************************************************************/	

		addStep("Ejecutar el grupo de jobs  DSRODS_RMS100_RMS_TD_03 para el proceso de control de carga en Control M");
		
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

		addStep("Validar los datos insertados en la tabla destino");
		
		System.out.println(TablaDestino);		
		connectionResult = executeQuery(dbPos, TablaDestino);

		connection = false;
		if(connectionResult.getRowCount() > 0)
			connection = true;

		System.out.println(connection);

		assertTrue(connection, "Los datos insertados en la tabla origen no se muestran en la tabla destino.");
		
		/* PASO 5 *********************************************************************/	

		addStep("Validar que el journal de la tabla est� vac�o");
		
		/* PASO 6 *********************************************************************/	

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
		return "";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}