package RMS.RegresionesAdministracionDeals;

import static org.testng.Assert.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import RMS.util.ExecuteQuery;
import RMS.util.GlobalVariables;
import RMS.util.PageObject.CTRL_M_UTIL;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_013_CierredeDealPromocional extends BaseExecution {
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_013_CierredeDealPromocional_test(HashMap<String, String> data) throws Exception {

		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);
		
		u = new SeleniumUtil(new ChromeTest(), true);
		ExecuteQuery e = new ExecuteQuery(testCase);
		CTRL_M_UTIL j = new CTRL_M_UTIL(u, testCase);

		// Paso 1
		addStep("Realizar la consulta para revisar deals con fecha de cierre hoy o fechas anteriores al d�a de hoy");
		testCase.addScreenShotCurrentStep("Paso1");

		LocalDate diaActual = LocalDate.now();
		DateTimeFormatter formatN = DateTimeFormatter.ofPattern("dd-MMM-yy", Locale.ENGLISH);
		String diaActualFormat = diaActual.format(formatN).toUpperCase();
		
		String query = String.format("Select * from DEAL_HEAD where STATUS = 'A' and type = 'P' and close_date < '%s' and rownum <= 10 order by close_date desc", diaActualFormat);
		boolean result = e.executeQuerySeparatedColumns(dbRms,query);
		assertTrue(result, "No se han encontrado registros");

		j.getControlM();
		testCase.addScreenShotCurrentStep("Ingreso a control-M");	
		
		testCase.addBoldTextEvidenceCurrentStep("JOB: dealcls");
		boolean resultadoEjecucion = j.ejecutarJob(("dealcls"));
		assertTrue(resultadoEjecucion, "El JOB dealcls no se ha ejecutado exitosamente");
		
		// Paso 3
		addStep("Realizar la consulta para revisar que los deals con fecha de cierre hoy o fechas anteriores al d�a de hoy se encuentren en estatus C.");
		query = String.format("Select * from DEAL_HEAD where STATUS = 'C' and type = 'P' and close_date < '%s' and rownum <= 10 order by close_date desc", diaActualFormat);
		result = e.executeQuerySeparatedColumns(dbRms,query);
		assertTrue(result, "No se han encontrado registros");

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_013_CierredeDealPromocional_test";
	}

	@Override
	public String setTestDescription() {
		return "Realiza el cierre de un Deal Promocional una vez que haya llegado a su fecha de finalizacion.";
	}

	@Override
	public String setTestDesigner() {
		return "1000068915 - Jorge Hernandez";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@Override
	public void beforeTest() {}
	
	@AfterMethod
	public void afterMethod() {
		 u.getDriver().quit();
	}


}
