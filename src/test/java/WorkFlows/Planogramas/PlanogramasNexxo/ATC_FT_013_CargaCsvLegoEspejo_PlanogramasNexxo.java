package WorkFlows.Planogramas.PlanogramasNexxo;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Jobs.JobUtils;
import WorkFlows.util.Planogramas.PomPlanogramas;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_013_CargaCsvLegoEspejo_PlanogramasNexxo extends BaseExecution{

	

	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_013_CargaCsvLegoEspejo_PlanogramasNexxo_test(HashMap<String, String> data) throws Exception {
		
	testCase.setProject_Name("Framework WorkFlows --> PLANOGRAMAS NEXXO");
	

	/**
	 * Variables
	 * 
	 */
	String resultadoEjecucion;
	boolean ejecucionCorrectaJob;
	SQLResult queryResultBuscaArchivos;
	String BASE_PATH = System.getProperty("user.dir");
	String lego_id=data.get("item");
	String accion = data.get("accion");
	String ruta =(BASE_PATH+"\\src\\test\\java\\utils\\PlanogramasNexxo\\ArchivosFTP\\");
	String host = GlobalVariables.HOST_FTP_PLANOGRAMAS;
	String user = GlobalVariables.USER_FTP_PLANOGRAMAS;
	String pass = GlobalVariables.PASS_FTP_PLANOGRAMAS;
	String port = "22";

	SeleniumUtil u = new SeleniumUtil(new FirefoxTest(), true);
	PomPlanogramas pom = new PomPlanogramas(u, testCase, data);

	addStep("Cargar archivo csv de  <ambas direcciones> del lego menor del set de legos con  <diferente>  variedad que lego mayor inmediato ");
		pom.sftpCarga(this, host, port, user, pass, ruta, "MTC-FT-013.csv", "/u11/usrplan/.usrplan/csv");
			
	 addStep("Ejecutar Job starMonitor");
	 		JobUtils jobUtil = new JobUtils(u, testCase, data, "jobs.xlsx");
		
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
		return "Solicitar en hold el job StarMonitor y configurar el lego en el archivo csv";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Terminado. Validar que al cargar en el FTP los archivos csv con ambas direcciones y ejecutar el job XXVA_ENG_PLANPGRAMA.ksh se realicen las validaciones correspondientes";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_013_CargaCsvLegoEspejo_PlanogramasNexxo_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
