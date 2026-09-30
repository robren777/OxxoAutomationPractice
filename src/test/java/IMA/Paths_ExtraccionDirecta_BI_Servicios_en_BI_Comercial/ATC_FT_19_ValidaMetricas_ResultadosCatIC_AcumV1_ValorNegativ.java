package IMA.Paths_ExtraccionDirecta_BI_Servicios_en_BI_Comercial;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_19_ValidaMetricas_ResultadosCatIC_AcumV1_ValorNegativ extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_19_ValidaMetricas_ResultadosCatIC_AcumV1_ValorNegativ_Test(HashMap<String, String> data) throws Exception {

		/** VARIABLES *********************************************************************/	

		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);		
		IMATools ima = new IMATools(u,testCase);	

		
	}
	
  
	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_19_ValidaMetricas_ResultadosCatIC_AcumV1_ValorNegativ";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se deber� validar que la m�trica del reporte en BI Servicios contenga un valor en negativo y que sea trasladada de manera correcta a la m�trica destino";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATIZACION";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
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
}