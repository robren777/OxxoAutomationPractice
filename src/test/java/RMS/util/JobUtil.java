package RMS.util;


import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.sikuli.script.FindFailed;

import modelo.TestCase;
import utils.controlm.ControlM;
import utils.selenium.SeleniumUtil;


public class JobUtil {
	
	HashMap<String, String> data;
	private SeleniumUtil u;
	private TestCase testCase;
	/**
	 * @param seleniumUtil Objeto para manejar el navegador.
	 * @param testCase Usado por el objeto JobManagment.
	 * @param dataProvider Usado para obtener los jobs y credenciales de Control-M.
	 */
	public JobUtil(SeleniumUtil seleniumUtil, TestCase testCase, HashMap<String, String> data) {
		this.u = seleniumUtil;
		this.testCase = testCase;				
		this.data = data;
	}	
	
	/**
	 * Manda a ejecutar los jobs
	 */
	public String ejecutar(String jobName) throws FindFailed {		
		JSONObject obj = new JSONObject(data.get(jobName));
		ControlM job = new ControlM(u, testCase, obj);
		
		u.hardWait(4);
		boolean flag = job.searchJob();
		assertTrue(flag);

		String resultado = job.executeJob();
		
		System.out.println("Resultado de la ejecucion -> " + resultado);
		testCase.addBoldTextEvidenceCurrentStep("Output: ");
		System.out.println("Valor de output :" + job.getOutput());
		testCase.addTextEvidenceCurrentStep(job.getOutput());
		
		job.closeViewpoint();
		
		return resultado;
	}
	
	/**
	 * Manda a ejecutar los Jobs que terminan en EndedOk
	 */
	public String ejecutarSetOk(String jobName) throws FindFailed {		
		JSONObject obj = new JSONObject(data.get(jobName));
		ControlM job = new ControlM(u, testCase, obj);
		u.hardWait(4);
		boolean flag = job.searchJob();
		assertTrue(flag);

		String resultado = job.executeJob();
		
		System.out.println("Resultado de la ejecucion -> " + resultado);
		testCase.addBoldTextEvidenceCurrentStep("Output: ");
		System.out.println("Valor de output :" + job.getOutput());
		testCase.addTextEvidenceCurrentStep(job.getOutput());
		
		job.closeViewpoint();
		
		resultado = "Ended OK";
		return resultado;
	}
	
	public boolean fueEjecutadoCorrectamente(String resultadoEjecucion) {		
		if(resultadoEjecucion.equals("Ended Ok") || resultadoEjecucion.equals("Wait Condition") || resultadoEjecucion.equals("Ended OK")) {
			return true;
		}
		else {
			return false;
		}
	}
 
}
