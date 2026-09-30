package IMA.tgoRpd;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import modelo.BaseExecution;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;
public class ATC_FT_32_ValidarQueExistaLosNuevosIDdeApegoRPD extends BaseExecution {

	private SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_32_ValidarQueExistaLosNuevosIDdeApegoRPD_test(HashMap<String, String> data) throws Exception {

		SQLUtil BD_IMA_TGO = new SQLUtil(GlobalVariables.DB_HOST_TGO, GlobalVariables.DB_USER_TGO,
				GlobalVariables.DB_PASSWORD_TGO);
			testCase.setPrerequisites("Que el nuevo indicador a buscar este dado de alta en la tabla de BI biodsmkt.temp_res_operativo");

	        addStep("Ingresar a BD Oracle ");
	        addStep("Se realizar un SELECT * FROM BIDMMKT.DIM_INDICADORES_RO WHERE ID_INDICATOR IN(99,98);");
			String consulta_ima_tgo = "SELECT * FROM BIDMMKT.DIM_INDICADORES_RO WHERE ID_INDICATOR IN(99,98)";
			SQLResult Execute_Query = executeQuery(BD_IMA_TGO, consulta_ima_tgo);
			
			boolean conexiondb  = true;
			conexiondb  = Execute_Query.isEmpty();
			boolean exist_registros = false;
			int cantidad_Registros = Execute_Query.getRowCount();
	   
	        if (cantidad_Registros >0) {
	            exist_registros =  true;
	            List<String> resultado = new ArrayList<>();
	            List<String> resultado2 = new ArrayList<>();
	            List<String> columnas = new ArrayList<>();
	            columnas = Execute_Query.getColumnNames();
	            resultado = Execute_Query.getFirstRow();
	            resultado2 = Execute_Query.getRow(1);
	            addStep("El resultado de la consulta es: ");
	            testCase.addTextEvidenceCurrentStep(columnas.toString());
	            testCase.addTextEvidenceCurrentStep(resultado.toString());
	            testCase.addTextEvidenceCurrentStep(resultado2.toString());
	            testCase.addTextEvidenceCurrentStep("muestra el ID Indicator 098 (%Apego RPD) y 099 (%Apego RPD Acumulado) y el ID");
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

		return "validar si existe el nuevo indicador de % de Apego en la primera tabla de BI biodsmkt.temp_res_operativo";
	}

	@Override
	public String setTestDesigner() {
		return "QA TESTER";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_31_SoporteIMAValidacionPrimeratablaBI_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Haber agregado los indicadores nuevos";
	}

	@AfterMethod
	public void afterMethod() {

	}
 
}
