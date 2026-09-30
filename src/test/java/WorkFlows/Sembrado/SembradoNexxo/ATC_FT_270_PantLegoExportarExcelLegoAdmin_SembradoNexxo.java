package WorkFlows.Sembrado.SembradoNexxo;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.Sembrado.pomSembradoNexxo;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_270_PantLegoExportarExcelLegoAdmin_SembradoNexxo extends BaseExecution {
	

	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_270_PantLegoExportarExcelLegoAdmin_SembradoNexxo_test(HashMap<String, String> data) throws Exception {
		
	testCase.setProject_Name("Framework WorkFlows --> SEMBRADO NEXXO");
	
	
	
	SeleniumUtil u = new SeleniumUtil(new MEdgeTest());
	u.get(data.get("url"));

	pomSembradoNexxo pomSembrado = new pomSembradoNexxo(u, testCase);

	addStep("Inicia sesion");
	pomSembrado.login(data.get("user"), data.get("ps"));
	
	addStep("Ingresa a la pantalla Creacion de Legos");
	pomSembrado.exportaLegos();
	
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
		return "Terminado. MTC-FT-270-Pantalla Legos - Exportar Excel en la pantalla de Legos_Administrador  ";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_270_PantLegoExportarExcelLegoAdmin_SembradoNexxo_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
