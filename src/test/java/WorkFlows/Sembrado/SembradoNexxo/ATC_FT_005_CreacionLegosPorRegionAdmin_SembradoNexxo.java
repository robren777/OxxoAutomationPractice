package WorkFlows.Sembrado.SembradoNexxo;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Sembrado.pomSembradoNexxo;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_005_CreacionLegosPorRegionAdmin_SembradoNexxo extends BaseExecution{


	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_005_CreacionLegosPorRegionAdmin_SembradoNexxo_test(HashMap<String, String> data) throws Exception {
		
	testCase.setProject_Name("Project: Sembrado Nexxo");
	
	SQLResult queryResult;
	String QUERY_XXVA_SEM_LEGOS = "SELECT LEGOS_ID, PLAZA_ID, SEGMENTO_ID, PLANOGRUPO_ID,CREATE_DATE,last_update_DATE,last_update_by  FROM xxva_siv3.XXVA_SEM_LEGOS \n"
			+ "where last_update_by= '"+data.get("user")+"' and TRUNC(CREATE_DATE)= TRUNC(SYSDATE)\n"
			+ "and last_update_date is not null order by LAST_UPDATE_DATE desc";
	
	utils.sql.SQLUtil dbXXVA_SIV3 = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_XXVA_SIV3,GlobalVariables.DB_USER_ESMEG_FCPVEQA, 
			GlobalVariables.DB_PASS_ESMEG_FCPVEQA);
	
	
	SeleniumUtil u = new SeleniumUtil(new MEdgeTest());
	u.get(data.get("url"));

	pomSembradoNexxo pomSembrado = new pomSembradoNexxo(u, testCase);

	addStep("Inicia sesion");
	pomSembrado.login(data.get("user"), data.get("ps"));
	
	addStep("Ingresa a la pantalla Creacion de Legos");
	pomSembrado.addLegos(data.get("plaza"),data.get("segLego"),data.get("planogramable"),data.get("planogrupo"),data.get("mueble"),
			data.get("tamano"),data.get("dirLego"),data.get("varDina"),data.get("fit_np"));
	
	addStep("Realizar la consulta en la DB xxva_siv3 en la tabla XXVA_SEM_LEGOS");
	System.out.println(QUERY_XXVA_SEM_LEGOS);
	queryResult= executeQuery(dbXXVA_SIV3, QUERY_XXVA_SEM_LEGOS);
	testCase.addQueryEvidenceCurrentStep(queryResult);
	
	assertFalse(queryResult.isEmpty(), "No se encontraron registros");


	u.getDriver().quit();

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
		return "Terminado. Solo un usuario con perfil Administrador podra entrar a esta opcion desde el Menu Administrador y podra acceder a la informacion de todas las regiones.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_005_CreacionLegosPorRegionAdmin_SembradoNexxo_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
