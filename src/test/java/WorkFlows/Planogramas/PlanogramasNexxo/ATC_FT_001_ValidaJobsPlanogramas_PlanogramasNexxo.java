package WorkFlows.Planogramas.PlanogramasNexxo;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.Jobs.JobUtils;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_ValidaJobsPlanogramas_PlanogramasNexxo extends BaseExecution{
	
	
	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_001_ValidaJobsPlanogramas_PlanogramasNexxo_test(HashMap<String, String> data) throws Exception {
		
	testCase.setProject_Name("Framework WorkFlows --> PLANOGRAMAS NEXXO");
	

	/**
	 * Variables
	 * 
	 */
	String resultadoEjecucion;
	boolean ejecucionCorrectaJob;
	String BASE_PATH = System.getProperty("user.dir");

	/**
	 * 
	 * Utilerias
	 */
	
	
	SeleniumUtil u = new SeleniumUtil(new FirefoxTest(), true);
	JobUtils jobUtil = new JobUtils(u, testCase, data, "jobs.xlsx");


	
 /*
		addStep("Ejecutar Job xxvaengplanogramamon");
		
		resultadoEjecucion = jobUtil.ejecutar("xxvaengplanogramamon");
		ejecucionCorrectaJob = jobUtil.fueEjecutadoCorrectamente(resultadoEjecucion);
		
		System.out.println("Resultado -------> "+resultadoEjecucion);
		System.out.println(ejecucionCorrectaJob);

		
		assertTrue(ejecucionCorrectaJob, "El job xxvaengplanogramamon no se ejecuto correctamente");
		
		//////////////////////////////////////////////////////////////////////////////////////
        addStep("Ejecutar Job xxvaengplanogramaini");
		
		resultadoEjecucion = jobUtil.ejecutar("xxvaengplanogramaini");
		ejecucionCorrectaJob = jobUtil.fueEjecutadoCorrectamente(resultadoEjecucion);
		System.out.println("Resultado -------> "+resultadoEjecucion);
		System.out.println(ejecucionCorrectaJob);

		
		assertTrue(ejecucionCorrectaJob, "El job xxvaengplanogramamon no se ejecuto correctamente");
	
		//////////////////////////////////////////////////////////////////////////////////////
		   addStep("Ejecutar Job xxvaengplanograma");
			
			resultadoEjecucion = jobUtil.ejecutar("xxvaengplanograma");
			ejecucionCorrectaJob = jobUtil.fueEjecutadoCorrectamente(resultadoEjecucion);
			System.out.println("Resultado -------> "+resultadoEjecucion);
			System.out.println(ejecucionCorrectaJob);

			
			assertTrue(ejecucionCorrectaJob, "El job xxvaengplanograma no se ejecuto correctamente");
			*/
	
	   addStep("Ejecutar Job starMonitor");
		
		resultadoEjecucion = jobUtil.ejecutar("starMonitor");
		ejecucionCorrectaJob = jobUtil.fueEjecutadoCorrectamente(resultadoEjecucion);
		System.out.println("Resultado -------> "+resultadoEjecucion);
		System.out.println(ejecucionCorrectaJob);

		
		assertTrue(ejecucionCorrectaJob, "El job starMonitor no se ejecuto correctamente");
		
		u.getDriver().quit();

	}
	
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Solicitar en hold el job StarMonitor";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Terminado. Validar que el job starMonitor se ejecute correctamente";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_ValidaJobsPlanogramas_PlanogramasNexxo_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
