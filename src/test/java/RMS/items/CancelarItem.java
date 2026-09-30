package RMS.items;

import java.util.HashMap;

import org.testng.annotations.Test;

import modelo.BaseExecution;
import utils.sql.SQLUtil;import RMS.util.GlobalVariables;

public class CancelarItem extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_CancelarItem(HashMap<String, String> data) throws Exception {

		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);

		/*
		 * //SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true); SeleniumUtil su
		 * = new SeleniumUtil(new IETest(), true); SikuliUtil sk = new SikuliUtil();
		 * String pathImg = sk.PatternPath("RMS\\"+"RMSS"); File Fol = new File(pathImg);
		 * HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		 * ArrayList<Pattern> patterns = new ArrayList<Pattern>(); ArrayList<String>
		 * ListImage = new ArrayList<String>(); Pattern pathimg = new Pattern(); Items i
		 * = new Items(sk, su, new Robot(), pathimg, ListImage, ImgPatter, patterns,
		 * Fol, pathImg, dbRms, data, testCase); JobUtil job = new JobUtil(null,
		 * testCase, data);
		 * 
		 * 
		 * System.out.println(dbRms.getHost());
		 * 
		 * // PASO 1 addStep("Ingresar a RMS"); System.out.println("PASO 1");
		 * 
		 * i.loginRMS();
		 * 
		 * 
		 * // PASO 2
		 * addStep("Seleccionar el folder items, elegir la opcion items y dar clic en el bot�n open"
		 * ); System.out.println("PASO 2");
		 * 
		 * i.menuItemItems();
		 * 
		 * 
		 * // PASO 3 addStep("Seleccionar la opci�n Edit �tem");
		 * System.out.println("PASO 3");
		 * 
		 * i.editItem();
		 * 
		 * 
		 * // PASO 4 addStep("Presionar el bot�n Delete"); System.out.println("PASO 4");
		 * 
		 * i.cancelarItem();
		 * 
		 * 
		 * // PASO 5 addStep("Correr el JOB DLYPRG"); System.out.println("PASO 5");
		 * 
		 * String resultadoEjecucion = job.ejecutar("DLYPRG"); boolean
		 * ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
		 * 
		 * assertTrue(ejecucionCorrectaJob,
		 * "El job DLYPRG no se ejecuto correctamente");
		 * 
		 * 
		 * // PASO
		 * addStep("Validar en la tabla de daily_purge si se encuentra el item");
		 * System.out.println("PASO ");
		 * 
		 * String item = data.get("item");
		 * 
		 * String daily = String.format(QueriesItems.DAILY_PURGE_KEY_VALUE, item);
		 * System.out.println(daily);
		 * 
		 * SQLResult executeQuery = executeQuery(dbRms, daily);
		 * 
		 * boolean statusQuery = executeQuery.isEmpty(); if (statusQuery) {
		 * testCase.addQueryEvidenceCurrentStep(executeQuery); }
		 * 
		 * assertTrue(statusQuery, "Se encontraron registros con KEY_VALUE = " + item +
		 * " en DAILY_PURGE");
		 */
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Construido. Borrar item en RMS." + "-DB: 10.184.37.12:1521/FCRMSQA.FEMCOM.NET ";
	}

	@Override
	public String setTestDesigner() {
		return "Guerrero Vargas - Equipo RMS";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_CancelarItem";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
