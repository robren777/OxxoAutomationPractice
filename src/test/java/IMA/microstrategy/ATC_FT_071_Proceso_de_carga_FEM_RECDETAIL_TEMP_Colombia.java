package IMA.microstrategy;



import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;


public class ATC_FT_071_Proceso_de_carga_FEM_RECDETAIL_TEMP_Colombia extends BaseExecution{
	SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
	@Test(dataProvider = "data-provider")
	public void ATC_FT_071_Proceso_de_carga_FEM_RECDETAIL_TEMP_Colombia_test(HashMap<String, String> data) throws Exception {
		
		JSONObject obj = new JSONObject(data.get("job"));

		addStep("Ejecutar Job");
		Control_mInicio CM = new Control_mInicio(u, data.get("ctrl_user"), PasswordUtil.decryptPassword(data.get("ctrl_ps")));

		addStep("Login");
		u.get(data.get("ctrl_url"));
		u.hardWait(20);
		u.waitForLoadPage();		
		CM.logOn(); // loguea

		addStep("Inicio de job ");
		ControlM control = new ControlM(u, testCase, obj); // si es uno por uno, debes instanciar esta clase

		control.searchJob(); // busca el job

		String resultadoEjecucion = control.executeJob(); // ejecuta el job

		System.out.println("Resultado de la ejecucion: " + resultadoEjecucion);
		System.out.println("Output: " + control.getOutput());

		u.close();

	}

@Override
public String setTestFullName() {
	// TODO Auto-generated method stub
	return "ATC_FT_071_Proceso_de_carga_FEM_RECDETAIL_TEMP_Colombia_test";
}

@Override
public String setTestDescription() {
	// TODO Auto-generated method stub
	return "ATC_FT_071_Proceso_de_carga_FEM_RECDETAIL_TEMP_Colombia_test";
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

