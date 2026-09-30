package WM.interfaces.MARK_PRM;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

@AzureTestInfo(
		areaPath = "I240701_EvolucionAlProcesoDe_ERDD_enLaNube\\Onpremise",
		iterationPath = "I240701_EvolucionAlProcesoDe_ERDD_enLaNube\\Onpremise\\Sprint 3",
		tags = "",
		userStoryId = 71351
)

public class ATC_015_MARKPRM_ValidarOrdenamientoJob extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_015_MARKPRM_ValidarOrdenamientoJob_test(HashMap<String, String> data) throws Exception {
		/*Nombre del caso de prueba manual que cubre: MTC-FT-015 Validar ordenamiento de job MARK_PRM_CLOUD
		Desc:
		Prueba de componente nuevo, el equipo de desarrollo general el job nuevo MARK_PRM_CLOUD,
		que marca las tiendas con el campo MIGRATED_FLAG_OUT_CLOUD, en este caso debe validar que
		en Control M exista y este ordenado el job MARK_PRM_CLOUD y el job PR63_MX
		@Valeria Fernandez
		@date   2024/10/31*/
		
		//***************************Paso 1*****************************************************************************
		addStep("Ingresar a Control M");
		SeleniumUtil u = new SeleniumUtil(new ChromeTest());
		JSONObject obj = new JSONObject(data.get("job"));
		JSONObject obj2 = new JSONObject(data.get("job2"));
		Control_mInicio CM = new Control_mInicio(u, data.get("user"), data.get("ps"));
		
		u.get(data.get("server"));
		u.hardWait(50);
		u.waitForLoadPage();
		CM.logOn();
		testCase.addScreenShotCurrentStep(u, "Login");
		
		//***************************Paso 2*****************************************************************************
		addStep("Confirmar que el job este ordenado MARK_PRM_CLOUD (siempre debe estar antes del job PR63_MX)");
		
		//Buscar el job
		ControlM control = new ControlM(u, testCase, obj);
		testCase.addBoldTextEvidenceCurrentStep("Inicio de job ");
		u.hardWait(50);
		boolean flag = control.searchJob();
		u.hardWait(50);
		assertFalse(!flag, "No se encontro el job");

		//***************************Paso 3*****************************************************************************
		addStep("Confirmar que el job este ordenado PR63_MX");
		
		//Buscar el job
		By regresa = By.xpath("//span[contains(text(), 'Viewpoints')]");
		u.click(regresa);
		control = new ControlM(u, testCase, obj2);
		testCase.addBoldTextEvidenceCurrentStep("Inicio de job ");	
		boolean flag2 = control.searchJob();
		u.hardWait(50);
		assertFalse(!flag2, "No se encontro el job");
		
		u.close();
	}

	@Override
	public String setTestFullName() {
		return "MTC-FT-015 Validar ordenamiento de job MARK_PRM_CLOUD";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Ordenamiento del job runMarkDocCloudPRM_MX antes de la ejecucion de la PR63_MX";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Valeria Fernandez";
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
		return "1. Acceso control M\r\n" +
		"2. Los jobs MARK_PRM_CLOUD y PR63_MX deben estar ordenados (haberlo solicitado por correo)\r\n" +
		"Job Name: MARK_PRM\r\n" +
		"Command Line: runMarkPRMCloud_MX.sh\r\n" +
		"Run As: brmsdas\r\n" +
		"Host: tqa_clu_int_ng_1011, oxvwqbosq03 y tqa_clu_int_1011\r\n" +
		"Folder Name: PERF_ITEMS_PROM_PC_AND_CL_16, PERF_PROM_16 y TQA_BAJO_DEMANDA\r\n" +
		"Job Name: PR63_MX\r\n" +
		"Command Line: runInterfase.sh PR63_MX\r\n" +
		"Run As: iuser\r\n" +
		"Host: tqa_clu_int_ng_1011, oxvwqbosq03 y tqa_clu_int_1011\r\n" +
		"Folder Name: PERF_ITEMS_PROM_PC_AND_CL_16, PERF_PROM_16 y TQA_BAJO_DEMANDA";

	}
}
