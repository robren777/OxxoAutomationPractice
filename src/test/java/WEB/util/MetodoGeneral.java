package WEB.util;

import org.testng.ITestResult;
import modelo.TestCase;

public class MetodoGeneral {

	/**
	 * Obtiene el tiempo de ejecucion de la prueba, debe ser llamado con la etiqueta AfterMethod 
	 * @param result
	 * @param testCase
	 * @author AngelD1@hexaware.com
	 */
	public static void obtenerTiempoDeEjecucion(ITestResult result, TestCase testCase) {
		long ms = result.getEndMillis() - result.getStartMillis();
		int ss = (int) (ms / 1000) % 60;
		int mm = (int) (ms / (1000 * 60) % 60);
		int hh = (int) ((ms / (1000 * 60 * 60)) % 24);
		String tiempoDeEjecucion = String.format("%02d:%02d:%02d", hh, mm, ss);

		testCase.addTextEvidenceCurrentStep("Tiempo de ejecución:  " + tiempoDeEjecucion);
		System.out.println("Tiempo de ejecución:  " + tiempoDeEjecucion);
	}
}
