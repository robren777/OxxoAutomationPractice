package RDM.pruebas.automatizacion;

import java.util.HashMap;

import org.testng.annotations.Test;

import modelo.BaseExecution;

public class ATC_FT_PruebaRF extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_PruebaRF_Test(HashMap<String, String> data) throws Exception {		

	
		
	}

	public String setTestFullName() {
		return "ATC_FT_PruebaRF_Test";
	}

	@Override
	public String setTestDescription() {
		return "Prueba radio frecuencia";
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}