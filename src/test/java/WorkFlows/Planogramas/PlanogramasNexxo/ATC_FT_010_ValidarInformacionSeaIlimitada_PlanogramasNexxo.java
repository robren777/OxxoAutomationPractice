package WorkFlows.Planogramas.PlanogramasNexxo;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.PlanogramasNexxo.MonitoreoUtils;
import WorkFlows.util.ultimus.pomUltimus;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_010_ValidarInformacionSeaIlimitada_PlanogramasNexxo extends BaseExecution{
	
	
	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_010_ValidarInformacionSeaIlimitada_PlanogramasNexxo_test(HashMap<String, String> data) throws Exception {
		
	testCase.setProject_Name("Framework WorkFlows --> PLANOGRAMAS NEXXO");
	

	/**
	 * Variables
	 * 
	 */
	String resultadoEjecucion;
	boolean ejecucionCorrectaJob;
	
	/**
	 * 
	 * Utilerías
	 */
	
	SeleniumUtil u = new SeleniumUtil(new FirefoxTest(), true);


			
			u.get(data.get("url_ultimus"));
			pomUltimus pom = new pomUltimus(u);
			String userUltimus = data.get("user_ultimus");

			//pom_metodosSipreBara_userAdmin pom_userAdmin = new pom_metodosSipreBara_userAdmin(u, testCase);

			/*-----------------------------------------------------------------------------------------*/
				addStep("Inicia Sesi�n en Ultimus");
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
				
				pomMonitoreo.buscar(data.get("plaza"));
				u.hardWait(5);

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
		return "Terminado. Validar que no tenga límite para que admita todos los legos y los conjuntos que están en más de una región tomarlo y mostrarlo en la pantalla de planogramas aprobados, como lo hace actualmente.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_010_ValidarInformacionSeaIlimitada_PlanogramasNexxo_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
