package WM.EvolucionRetiroSinTarjeta.HU08;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.util.HashMap;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_007_Validar_tabla_fuente_de_reportes_y_archivo_conciliacion_FACT_CONSILIACION_RCT extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_007_Validar_tabla_fuente_de_reportes_y_archivo_conciliacion_FACT_CONSILIACION_RCT_test(HashMap<String, String> data)
	throws Exception{
		/**
		 * Plataforma de Oracle: Tener conexion a la BD FCODSQA
		 * Desc:Validar tabla fuente de reportes y archivo conciliación: FACT_CONSILIACION_RCT
		 * @author Mario Garza
		 * @date 13/03/2024
		 **/
		/*
		 * Utilerias
		 *********************************************************************/
		SQLUtil dbFCODSQA = new SQLUtil(GlobalVariables.DB_HOST_IMA, GlobalVariables.DB_USER_IMA,
				GlobalVariables.DB_PASSWORD_IMA);
		/*
		 * Variables
		 ******************************************************************************************/

		testCase.setTest_Description(data.get("id") + data.get("description"));

		testCase.setPrerequisites("Acceso a base de datos FCWM6QA");
		//Se coloca la referincia para compararla
		String folio_referenica = "    1005485844000568";
		
		
		System.out.println(
				"*****************************************PASO 1 ************************************************");
		//Se realiza la consulta
		addStep("Validar la información guardada de la tabla FACT_CONSILIACION_RST  con la siguiente consulta");
		String consulta_FACT_CONSILIACION_RST = "SELECT FOLIO_REFERENCIA, DESCRIPCIÓN_EMISOR, CR_PLAZA, CR_TIENDA, OPERADOR, FECHA_RECEPEPCION_TPC FROM WM.FACT_CONSILIACION_RST WHERE CR_PLAZA = '10MON' AND CR_TIENDA = '50MCZ' AND FOLIO_WM = '2017993831'";
		
		System.out.println("Consulta: " + consulta_FACT_CONSILIACION_RST);
		//Se guarda en la variable
		SQLResult ExecuteQuery_FACT_CONSILIACION_RST = executeQuery(dbFCODSQA, consulta_FACT_CONSILIACION_RST);

		boolean conexiondbFCWM6QA_FACT_CONSILIACION_RST  = true;
		conexiondbFCWM6QA_FACT_CONSILIACION_RST  = ExecuteQuery_FACT_CONSILIACION_RST.isEmpty();
		//Se cuenta los registros de la fila
		int cantidad_Registros_FACT_CONSILIACION_RST = ExecuteQuery_FACT_CONSILIACION_RST.getRowCount();
		System.out.println(cantidad_Registros_FACT_CONSILIACION_RST);
		//Se guarda en una lista de string la fila 2
		List<String> datos_FACT_CONSILIACION_RST= ExecuteQuery_FACT_CONSILIACION_RST.getRow(2);
		String id_FACT_CONSILIACION_RST = datos_FACT_CONSILIACION_RST.get(0);
		System.out.println(id_FACT_CONSILIACION_RST);
		 
		boolean maximo_Registros = false;
		//Se valida sique la cantidad de regitros este de 1 o 5
		if (cantidad_Registros_FACT_CONSILIACION_RST == 1 || cantidad_Registros_FACT_CONSILIACION_RST == 5){ 
			maximo_Registros = true;
		}
		assertEquals(id_FACT_CONSILIACION_RST, folio_referenica);
		//Se toma la evidencia
		testCase.addTextEvidenceCurrentStep("Conexion: FCODSQA");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMREQA);
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery_FACT_CONSILIACION_RST);
		//Se valida si la conexion  no fue exitosa
		assertFalse(conexiondbFCWM6QA_FACT_CONSILIACION_RST, "La conexion no fue exitosa");

	}

	@Override
	public void beforeTest() {
	}	

	@Override
	public String setTestDescription() {
		return "Validar tabla fuente de reportes y archivo conciliación: FACT_CONSILIACION_RCT";
	}

	@Override
	public String setTestDesigner() {
		return "Mario.garza@grupotimexico.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_007_Validar_tabla_fuente_de_reportes_y_archivo_conciliación_FACT_CONSILIACION_RCT";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Acceso a base de datos FCODSQA";
	}

	@AfterMethod
	public void afterMethod() {

	}


}
