package WorkFlows.SipreOXXO;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WorkFlows.util.Jobs.JobUtils;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_SIPRE_061_Performance_JOB_XXWF_SIPRE2_API_CAMBIO_PRECIOS extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	 public void ATC_FT_SIPRE_061_Performance_JOB_XXWF_SIPRE2_API_CAMBIO_PRECIOS_Test(HashMap<String, String> data) throws Exception {
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
		
		u = new SeleniumUtil(new FirefoxTest(), true);
		JobUtils jobUtil = new JobUtils(u, testCase, data, "JobsSIPREOXXO.xlsx");


	
	
			// paso 1
					addStep("Abrir Control M");
					
			// paso 2
					addStep("Ingresar las credenciales usuario y pasword");
			// paso 3
					addStep("Dar click a ingresar");
			// paso 4
					addStep("Entrar a la pestaña ALL JOBS");
			// paso 5
					addStep("Ingresar a la carpeta TQA_SIV_SIPRE");
			// paso 6
					addStep("Seleccionar el JOB XXWF_SIPRE2_API_CAMBIO_PRECIOS");
			// paso 7
					addStep("Dar clic derecho en el JOB y dar clic en Ejecutar");
					
					resultadoEjecucion = jobUtil.ejecutar("XXWF_SIPRE2_API_CAMBIO_PRECIOS");
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
		return "Validar la correcta Ejecución del JOB XXWF_SIPRE2_API_CAMBIO_PRECIOS";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Misraim Hernandez Monsivais";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_SIPRE_061_Performance_JOB_XXWF_SIPRE2_API_CAMBIO_PRECIOS_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
