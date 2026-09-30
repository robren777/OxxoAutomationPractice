package WEB.cnt.scripts;

import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import modelo.BaseExecution;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_364_Confirmar_cambios_en_catalogo_DIM_Tienda extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_364_Confirmar_cambios_en_catalogo_DIM_Tienda_test(HashMap<String, String> data) throws SQLException {
		/*
		 * Utiler�as
		 ******************************************************************************************/
		
		
		/*
		 * Variables
		 ******************************************************************************************/
		//String user = data.get("User");
		//String password = PasswordUtil.decryptPassword(data.get("Password"));
		String query = "select ID_SEGMENTO_MAESTRO, SEGMENTO_MAESTRO_DESC, ID_POTENCIAL_HOGAR, "
				+ "POTENCIAL_HOGAR_DESC, ID_POTENCIAL_COMIDA, POTENCIAL_COMIDA_DESC, "
				+ "ID_POTENCIAL_DESAYUNO, POTENCIAL_DESAYUNO_DESC, ID_OVERLAY_HOGAR, "
				+ "OVERLAY_HOGAR_DESC, ID_OVERLAY_COMIDA, OVERLAY_COMIDA_DESC, ID_OVERLAY_DESAYUNO, "
				+ "OVERLAY_DESAYUNO_DESC, ID_CONCEPTO_DIFERENCIADOR, CONCEPTO_DIFERENCIADOR_DESC from DWADMIN.DIM_TIENDA WHERE rownum <= 10";
		
		// Paso 1
		addStep("Usuario accede al cat�logo de DIM_TIENDA de IMA.");
		SQLUtil FCDW4QA = new SQLUtil("10.184.80.26:1535/FCDW4QA.FEMCOM.NET", "GUIARECTORA_WEB", "GUIARECTORA_WEB");
		
		// Paso 2
		addStep("Realizar la consulta de la tabla de DIM_TIENDA");
		System.out.println(query);
		SQLResult ExecuteQuery = executeQuery(FCDW4QA, query);
		boolean StatusQuery = !ExecuteQuery.isEmpty();
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);

		// Paso 3
		addStep("Usuario confirma que se muestra las columnas correspondientes a segmento de tienda y concepto diferenciador dentro de DIM_TIENDA");
		if(StatusQuery == true)
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		else
			assertTrue(StatusQuery, "No se ha encontrado ningun resultado en la busqueda de las tablas");
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
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_364_Confirmar_cambios_en_catalogo_DIM_Tienda";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@AfterMethod
	public void afterMethod() {
		//u.getDriver().quit();
	}

}