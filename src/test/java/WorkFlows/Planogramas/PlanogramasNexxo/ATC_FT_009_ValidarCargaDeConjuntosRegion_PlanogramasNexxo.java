package WorkFlows.Planogramas.PlanogramasNexxo;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.PlanogramasNexxo.MonitoreoUtils;
import WorkFlows.util.ultimus.pomUltimus;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_009_ValidarCargaDeConjuntosRegion_PlanogramasNexxo extends BaseExecution{
	
	
	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_009_ValidarCargaDeConjuntosRegion_PlanogramasNexxo_test(HashMap<String, String> data) throws Exception {
		
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
	 * Utilerías
	 */
	
	SeleniumUtil u = new SeleniumUtil(new FirefoxTest(), true);
	/*JobUtils jobUtil = new JobUtils(u, testCase, data, "jobs.xlsx");

		
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
			//////////////////////////////////////////////////////////////
			*/u.get(data.get("url_ultimus"));
			pomUltimus pom = new pomUltimus(u);
			String userUltimus = data.get("user_ultimus");

			//pom_metodosSipreBara_userAdmin pom_userAdmin = new pom_metodosSipreBara_userAdmin(u, testCase);

			/*-----------------------------------------------------------------------------------------*/
				addStep("Inicia Sesión en Ultimus");
				pom.loginUltimus(userUltimus, "");
				testCase.addScreenShotCurrentStep(u, "");
				
			/*-----------------------------------------------------------------------------------------*/
				addStep("Selecciona Iniciar");
			
				pom.seleccionarVista("INICIAR");
				testCase.addScreenShotCurrentStep(u, "");
				
			/*-----------------------------------------------------------------------------------------*/
				addStep("Selecciona Monitoreo");
			
				pom.openMonitoreo();
				testCase.addScreenShotCurrentStep(u, "");
			
				MonitoreoUtils pomMonitoreo = new MonitoreoUtils(u, testCase);
				pomMonitoreo.monitoreo();
				

		u.getDriver().quit();

	}
	
	
	
	
	

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Solicitar en hold el job StarMonitor y configurar el lego en el archivo csv";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Terminado. Validar que se permita la carga de más de un conjunto en una región";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_009_ValidarCargaDeConjuntosRegion_PlanogramasNexxo_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
