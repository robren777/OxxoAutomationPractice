package IMA.ods01;

import static org.testng.Assert.assertFalse;
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

public class MTC_FT_008_ReprocesoCargaTruncadoaTablaMes extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_008_ReprocesoCargaTruncadoaTablaMes_(HashMap<String, String> data) throws Exception {
		
		/** UTILERIA *********************************************************************/	
		
		utils.sql.SQLUtil dbPos = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);

		/** VARIABLES *********************************************************************/	
		
		String ExistenDatosFuente = "SELECT * FROM TABLENAME";
		String ExistenDatosDestino = "SELECT * FROM TABLENAME";
		String ActualizaRegistros = "UPDATE * FROM * SET NOSE = '%s'";
		String BitacoraCarga = "BIODSMKTCOL.TBL_109_BITACORA_CARGA";
		String CambiaReproceso = "UPDATE * FROM BIODSMKTCOL.CFG_109_CONTROL_CARGA SET REPROCESO = '1', FECHAINICIO = '%s', FECHAFIN = '%s'";
		
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
		String fecha_ini = "";
		String fecha_fin = "";
		
		/* PASO 3 *********************************************************************/	

		addStep("Actualizar varios registros en la tabla fuente");

		String dato_a_actualizar = "123";
		System.out.println(GlobalVariables.DB_HOST_FCWMLQA);
		String Registro = String.format(ActualizaRegistros, dato_a_actualizar);
		System.out.println(Registro);
		SQLResult paso3 = executeQuery(dbPos, Registro);

		boolean error = paso3.isEmpty();

		if (!error) {

			testCase.addQueryEvidenceCurrentStep(paso3);
		}

		System.out.println(error);

		assertFalse(error, "Los registros no se actualizaron correctamente");

		/* PASO 4 *********************************************************************/	

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
		
		/* PASO 5 *********************************************************************/	

		addStep("Validar el reproceso de los datos en la tabla destino");
		
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
		
		/* PASO 6 *********************************************************************/
		
		addStep("Validar que se genere un registro de la carga realizada en la tabla BIODSMKTCOL.TBL_109_BITACORA_CARGA (en DW4 QA)");	

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
		
		/* PASO 7 *********************************************************************/
		
		addStep("Configurar la tabla BIODSMKTCOL.CFG_109_CONTROL_CARGA, activar la bandera de reproceso modificando el valor de la columna de REPROCESO de 0 a 1, poner la FECHAINICIO Y FECHAFIN al dia en que se hizo el paso 2 y 3");	
		
		CambiaReproceso = String.format(CambiaReproceso, fecha_ini,fecha_fin);
		System.out.println(CambiaReproceso);		
		connectionResult = executeQuery(dbPos, CambiaReproceso);

		connection = false;
		if(!connectionResult.isEmpty())
			connection = true;
		
		if (!connection) {				
			testCase.addQueryEvidenceCurrentStep(connectionResult);					
		} 		
		
		System.out.println(connection);

		assertTrue(connection, "La tabla no contiene informaci�n.");
		
		/* PASO 8 *********************************************************************/
		
		addStep("Ejecutar jobs de BI ");
		
		/* PASO 9 *********************************************************************/
		
		addStep("Validar los cambios en la tabla de sumarizacion mensual BIDMMKTCOL.DM_109_REC_DEV_CPRAS_MM");
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