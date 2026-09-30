package RDM.pruebas.automatizacion;

import java.util.HashMap;
import org.testng.annotations.Test;
import modelo.BaseExecution;
import RDM.util.BaseRDM;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_Pruebauno extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_Pruebauno_Test(HashMap<String, String> data) throws Exception {		
		SeleniumUtil u = new SeleniumUtil(new IETest());
		BaseRDM rdm = new BaseRDM(u, testCase, data.get("server"));

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		addStep("test de funciones");
		
		// PASO 1
		addStep("Ingresar a RDM a la siguiente ruta: " + rdm.getUrl());
		System.out.println("PASO 1");
		
		logger.info("RDM URL: " + rdm.getUrl());
		System.out.println("RDM URL: " + rdm.getUrl());
		
		logger.info("User Name: " + rdm.getUserName());
		System.out.println("User Name: " + rdm.getUserName());
		
		rdm.logIn();
		//rdm.clickAppointments();
		u.close();
		
		
	}

	public String setTestFullName() {
		return "ATC_FT_Pruebauno_Test";
	}

	@Override
	public String setTestDescription() {
		return "Prueba inicio de session";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
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