package RMS.impuestos;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RMS.util.QueriesImpuestos;
import RMS.util.PageObject.Impuestos;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;import RMS.util.GlobalVariables;import RMS.util.JobUtil;

public class GenerarAjuste extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_GenerarAjuste(HashMap<String, String> data) throws Exception {

		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);

		// SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		SeleniumUtil su = new SeleniumUtil(new IETest(), true);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("RMS\\"+"RMSS");
		File Fol = new File(pathImg);
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();
		Impuestos i = new Impuestos(sk, su, new Robot(), pathimg, ListImage, ImgPatter, patterns, Fol, pathImg, dbRms,
				data, testCase);
		JobUtil job = new JobUtil(null, testCase, data);

		System.out.println(dbRms.getHost());

		// PASO 1
		addStep("Ingresar a RMS");
		System.out.println("PASO 1");

		i.loginRMS();

		// PASO 2
		addStep("Seleccionar el folder finance y elegir la opci�n XXFC-Captura de Ajustes de Transacciones y dar clic en el bot�n Open.");
		System.out.println("PASO 2");

		i.menuFinanceAjustes();

		// PASO 3
		String mov = data.get("movimiento");
		String impuesto = data.get("impuesto");
		/*
		 * addStep("En la pantalla Captura de Ajuste de Transacciones registrar el movimiento "
		 * + mov + " de Promoci�n para la tienda Zona con impuesto " + impuesto);
		 * System.out.println("PASO 3");
		 * 
		 * i.registrarMov();
		 * 
		 * // ERROR NO DEJA CONTINUAR CON RMS
		 */

		// PASO 4
		addStep("Validar en la tabla FEM_FIF_STG que se haya registrado el " + "Tran Code " + mov
				+ " de Devoluci�n para el Art�culo " + "con " + impuesto + " correspondiente a la tienda de la Zona.");
		System.out.println("PASO 4");

		String numeroTiendaQuery = String.format(QueriesImpuestos.OBTENER_TIENDA, data.get("tienda"));
		System.out.println(numeroTiendaQuery);

		SQLResult executeQuery = executeQuery(dbRms, numeroTiendaQuery);

		String numeroTienda = executeQuery.getData(0, "STORE");
		String plaza = data.get("plaza");

		String fem = String.format(QueriesImpuestos.FEM_FIF_STG, plaza, numeroTienda, impuesto, mov);
		System.out.println(fem);

		executeQuery = executeQuery(dbRms, fem);

		boolean statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No hay registros con CR_PLAZA = " + plaza + ", VAT_CODE = " + impuesto
				+ " y TRAN_CODE " + mov + " en FEM_FIF_STG");

		// PASO 5
		addStep("Ejecutar la interfaz R08_MEX.");
		System.out.println("PASO 5");

		String resultadoEjecucion = job.ejecutar("R08_MEX");
		boolean ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job R08_MEX no se ejecuto correctamente");

		// PASO 6
		addStep("Validar que el campo reference_3 ya no se encuentre en null en la tabla "
				+ "FEM_FIF_STG para el Tran Code " + mov + " y la tienda de Frontera.");
		System.out.println("PASO 6");

		String femRef = String.format(QueriesImpuestos.FEM_FIF_STG_NULL, plaza, numeroTienda, impuesto, mov);
		System.out.println(femRef);

		executeQuery = executeQuery(dbRms, femRef);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No hay registros con CR_PLAZA = " + plaza + ", VAT_CODE = " + impuesto
				+ " y TRAN_CODE " + mov + " y REFERENCE_3 != NULL en FEM_FIF_STG");
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
		return "Construido. Se genera ajuste de ventas por pantalla." + "-DB: 10.184.37.12:1521/FCRMSQA.FEMCOM.NET "
				+ "-JOBS: " + "-CASOS QUE CUMPLE: "
				+ "MTC-FT-006 Generar Ajuste de Venta por pantalla Captura de Ajuste de Transacciones - IVA08 - Zona Frontera"
				+ "MTC-FT-007 Generar Ajuste de Devoluci�n por pantalla Captura de Ajuste de Transacciones - IVA16 - Zona Interior"
				+ "MTC-FT-008 Generar Ajuste de Promoci�n por pantalla Captura de Ajuste de Transacciones - IVA16 - Zona Frontera";
	}

	@Override
	public String setTestDesigner() {
		return "Guerrero Vargas - Equipo RMS";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_GenerarAjuste";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
