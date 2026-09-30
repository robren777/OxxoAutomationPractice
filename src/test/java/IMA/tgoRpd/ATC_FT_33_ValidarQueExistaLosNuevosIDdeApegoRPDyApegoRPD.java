package IMA.tgoRpd;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import modelo.BaseExecution;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;
public class ATC_FT_33_ValidarQueExistaLosNuevosIDdeApegoRPDyApegoRPD extends BaseExecution {

	private SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_33_ValidarQueExistaLosNuevosIDdeApegoRPDyApegoRPD_test(HashMap<String, String> data) throws Exception {

		SQLUtil BD_IMA_TGO = new SQLUtil(GlobalVariables.DB_HOST_TGO, GlobalVariables.DB_USER_TGO,
				GlobalVariables.DB_PASSWORD_TGO);
			testCase.setPrerequisites("Que en la tabla BIDMMKT.FACT_RES_OPERATIVO_SEMANA esten las columnas RO_PORCENTAJE_APEGO_RPD Y RO_PORCENTAJE_APEGO_RPD_ACUM");

	        addStep("Ingresar a BD Oracle ");
	        addStep("Se consulta SELECT count(RO_PORCENTAJE_APEGO_RPD) FROM BIDMMKT.FACT_RES_OPERATIVO_SEMANA, para ver si se encuentra la columna en la tabla ");
	        addStep("Se consulta SELECT count(RO_PORCENTAJE_APEGO_RPD_ACUM) FROM BIDMMKT.FACT_RES_OPERATIVO_SEMANA para ver si es visible la columna en la tabla ");
			String consulta_ima_tgo = "SELECT count(RO_PORCENTAJE_APEGO_RPD) FROM BIDMMKT.FACT_RES_OPERATIVO_SEMANA";
			String consulta_ima_tgo2 = "SELECT count(RO_PORCENTAJE_APEGO_RPD_ACUM) FROM BIDMMKT.FACT_RES_OPERATIVO_SEMANA";
			SQLResult Execute_Query = executeQuery(BD_IMA_TGO, consulta_ima_tgo);
			SQLResult Execute_Query2 = executeQuery(BD_IMA_TGO, consulta_ima_tgo2);
			
			boolean conexiondb  = true;
			
			conexiondb  = Execute_Query.isEmpty();
			boolean exist_registros = false;
			int cantidad_Registros = Execute_Query.getRowCount();
			int cantidad_Registros2 = Execute_Query2.getRowCount();
	        if (cantidad_Registros >0 && cantidad_Registros2>0 ) {
	            exist_registros =  true;

	            testCase.addTextEvidenceCurrentStep("\"?se visualizan las columnas  RO_PORCENTAJE_APEGO_RPD Y RO_PORCENTAJE_APEGO_RPD_ACUM\r\n");
	        }

	        testCase.addTextEvidenceCurrentStep("Conexion: con la base de datos ");
			testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_TGO);
			

	        Assert.assertFalse(conexiondb, "La conexion no fue exitosa");
			assertTrue(exist_registros);
		}
 
	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {

		return "validar que en la tabla BIDMMKT.FACT_RES_OPERATIVO_SEMANA se visualicen las columnas RO_PORCENTAJE_APEGO_RPD Y RO_PORCENTAJE_APEGO_RPD_ACUM en el campo COLUMN_NAME";
	}

	@Override
	public String setTestDesigner() {
		return "QA TESTER";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_33_ValidarQueExistaLosNuevosIDdeApegoRPDyApegoRPD_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Haber agregado las 2 nuevas columnas a la tabla de BIDMMKT.FACT_RES_OPERATIVO_SEMANA";
	}

	@AfterMethod
	public void afterMethod() {

	}
 
}
