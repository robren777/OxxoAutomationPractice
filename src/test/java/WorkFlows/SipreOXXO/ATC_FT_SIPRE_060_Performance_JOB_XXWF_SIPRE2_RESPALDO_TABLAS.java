package WorkFlows.SipreOXXO;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WorkFlows.util.Jobs.JobUtils;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_SIPRE_060_Performance_JOB_XXWF_SIPRE2_RESPALDO_TABLAS extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	 public void ATC_FT_SIPRE_060_Performance_JOB_XXWF_SIPRE2_RESPALDO_TABLAS_Test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Workflows Library: Sipre Oxxo");
	
	/**
	 * Variables
	 * 
	 */
		String resultadoEjecucion;
		boolean ejecucionCorrectaJob;
		
		

	/**
	 * 
	 * Utilerias
	 */
		u = new SeleniumUtil(new ChromeTest(), true);
		JobUtils jobUtil = new JobUtils(u, testCase, data, "JobsSIPREOXXO.xlsx");
	
	
		// paso 1
			addStep("Ejecutar Job XXWF_SIPRE2_RESPALDO_TABLAS");
			resultadoEjecucion = jobUtil.ejecutar("XXWF_SIPRE2_RESPALDO_TABLAS");
			ejecucionCorrectaJob = jobUtil.fueEjecutadoCorrectamente(resultadoEjecucion);
			System.out.println("Resultado -------> "+resultadoEjecucion);
			System.out.println(ejecucionCorrectaJob);

		
		assertTrue(ejecucionCorrectaJob, "El job XXWF_SIPRE2_RESPALDO_TABLAS no se ejecuto correctamente");
					
		
	
	}
	
	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}
	
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Tener Acceso Dominio Oxxo" + 
				"Contar con Orgchart de sipre" + 
				"Contar con usuarios configurados al orgchart" + 
				"Contar con JFG de perfil adecuado en Ultimus o Pega" + 
				"Contar con Acceso a Control -M" + 
				"Solicitar el Holdeo de los JOBS a ejecutar" + 
				"Contar con Acceso al esquema de XXWF_SIPRE2" + 
				"CONEXION FCIAS";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que se pueda realizar una Carga exitosa del INPUT de ESTRATEGIA PLAZAS desde el usuario Administrador";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Misraim Hernandez Monsivais";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_SIPRE_060_Performance_JOB_XXWF_SIPRE2_RESPALDO_TABLAS_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
