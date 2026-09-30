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

public class ATC_FT_019_ValVariedadLegoMenor_PlanogramasNexxo extends BaseExecution {
	
	
	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_019_ValVariedadLegoMenor_PlanogramasNexxo_test(HashMap<String, String> data) throws Exception {
		
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

	addStep("Cargar archivo csv de  <ambas direcciones> del lego menor del set de legos con  <misma>  variedad que lego mayor inmediato");
		pom.sftpCarga(this, host, port, user, pass, ruta, "MTC-FT-019.csv", "/u11/usrplan/.usrplan/csv");
			
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
		return "Terminado. Comprobar que se realicen las siguientes validaciones al cargar los archivos del lego mayor de un set y ejecutar el job XXVAENGPLANOGRAMA.ksh. Se muestre el mensaje de \"Cargado\" en el archivo adjunto enviado por correo. Se muestre el mensaje \"Falta artículo de lego menor  (tamaño) dirección [SKU del artículo]\" en el archivo adjunto enviado por correo.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_019_ValVariedadLegoMenor_PlanogramasNexxo_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
