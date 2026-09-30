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

public class ATC_FT_219_EliminColArchCargarRelaSegLegAdmin_SembradoNexxo extends BaseExecution{

	
	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_219_EliminColArchCargarRelaSegLegAdmin_SembradoNexxo_test(HashMap<String, String> data) throws Exception {
		
	testCase.setProject_Name("Framework WorkFlows --> Sembrado Nexxo");
	
	SQLResult queryResult;
	String BASE_PATH = System.getProperty("user.dir");
	String rutaArch = BASE_PATH+data.get("path");
	String QUERY_XXVA_SEM_LEGOS = "\n"
			+ "SELECT FILENAME, STATUS, STARTTIME, ENDTIME, ERROR_MESSAGE\n"
			+ "FROM xxva_siv3.XXVA_SEM_CONTROL_CARGA_MASIVA where FILENAME = '"+data.get("nomArch")+"' and (STATUS = 'NUEVO' or STATUS ='PROCESADO') and ROWNUM <=1\n"
					+ "and last_update_date is not null order by LAST_UPDATE_DATE desc ";
	
	utils.sql.SQLUtil dbXXVA_SIV3 = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_XXVA_SIV3,GlobalVariables.DB_USER_ESMEG_FCPVEQA, 
			GlobalVariables.DB_PASS_ESMEG_FCPVEQA);
	
	
	SeleniumUtil u = new SeleniumUtil(new MEdgeTest());
	u.get(data.get("url"));

	pomSembradoNexxo pomSembrado = new pomSembradoNexxo(u, testCase);

	addStep("Inicia sesión");
	pomSembrado.login(data.get("user"), data.get("ps"));
	addStep("Ingresa a la pantalla Carga de Archivos");

	pomSembrado.cargaArchivos(rutaArch, data.get("opArchivo"), data.get("nomArch"));

	addStep("Ingresar a la BD XXVA_SIV3  y realizar la consulta :");

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
		return "Terminado. MTC-FT-219-Eliminar Columnas de archivo_Cargar masiva Relación Segmento de tienda Segmento de lego_Administrador";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_219_EliminColArchCargarRelaSegLegAdmin_SembradoNexxo_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
