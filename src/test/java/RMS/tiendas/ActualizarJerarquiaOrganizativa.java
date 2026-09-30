package RMS.tiendas;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RMS.util.GlobalVariables;
import RMS.util.JobUtil;
import RMS.util.PageObject.Tiendas;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ActualizarJerarquiaOrganizativa extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_Actualizar_Jerarquia_Organizativa(HashMap<String, String> data) throws Exception {

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
		Tiendas t = new Tiendas(sk, su, pathImg, new Robot(), pathimg, ListImage, ImgPatter, patterns, Fol, pathImg,
				dbRms, null, data, testCase);
		JobUtil job = new JobUtil(null, testCase, data);

		System.out.println(dbRms.getHost());

		// PASO 1
		addStep("Ingresar a RMS");
		System.out.println("PASO 1");

		t.Login_En_RMS();

		// PASO 2
		addStep("Seleccionar el folder Action, elegir la opcion Organizational Hierarchy y "
				+ "dar clic en el bot�n open");
		System.out.println("PASO 2");

		// t.menuActionOrganizational();

		// PASO 3
		addStep("Seleccionar la opci�n Store dentro de la jerarqu�a organizacional.");
		System.out.println("PASO 3");

		// t.hierorgSelStore();

		// PASO 4
		addStep("En la secci�n Filter, capturar el n�mero de tienda en el campo store, "
				+ "dar clic en la tecla tab para que nos arroje el nombre de la tienda, "
				+ "dar clic en el bot�n Apply filter, Dar clic en el bot�n Edit" + "");
		System.out.println("PASO 4");

		// t.hierorgCapturarStore();

		/*
		 * // PASO 5 addStep("Revisar ITEM en tablas de RMS: " +
		 * "PACKITEM, ITEM_LOC, ITEM_MASTER"); System.out.println("PASO 5");
		 * 
		 * String packNo = data.get("pack_no");
		 * 
		 * // Valida PACKITEM String packItem =
		 * String.format(QueriesRecetas.PACKITEM_PACK_ITEM_QUANTITY, packNo, item,
		 * quantity); System.out.println(packItem);
		 * 
		 * SQLResult executeQuery = executeQuery(dbRms, packItem);
		 * 
		 * boolean statusQuery = executeQuery.isEmpty(); if (!statusQuery) {
		 * testCase.addQueryEvidenceCurrentStep(executeQuery); }
		 * 
		 * assertFalse(statusQuery, "No se encontraron registros con PACK_NO = " +
		 * packNo + " ITEM = " + item + " y con " + "el nuevo valor QUANTITY = " +
		 * quantity + " en PACKITEM");
		 * 
		 * 
		 * // Valida ITEM_LOC String store = data.get("store"); String itemLoc =
		 * String.format(QueriesRecetas.ITEM_LOC_ITEM_STORE_STATUS_A, item, store);
		 * System.out.println(itemLoc);
		 * 
		 * executeQuery = executeQuery(dbRms, itemLoc);
		 * 
		 * statusQuery = executeQuery.isEmpty(); if (!statusQuery) {
		 * testCase.addQueryEvidenceCurrentStep(executeQuery); }
		 * 
		 * assertFalse(statusQuery, "No se encontraron registros con PACK_NO = ITEM = "
		 * + item + " y " + "LOC = " + store + " en ITEM_LOC");
		 * 
		 * 
		 * // Valida ITEM_MASTER String itemMaster =
		 * String.format(QueriesRecetas.ITEM_MASTER_ITEM, item);
		 * System.out.println(itemMaster);
		 * 
		 * executeQuery = executeQuery(dbRms, itemMaster);
		 * 
		 * statusQuery = executeQuery.isEmpty(); if (!statusQuery) {
		 * testCase.addQueryEvidenceCurrentStep(executeQuery); }
		 * 
		 * assertFalse(statusQuery, "No se encontraron registros con ITEM = " + item +
		 * " en ITEM_MASTER");
		 * 
		 * 
		 * // PASO 6 addStep("Ejecutar runPR26(PR26) en Control M");
		 * System.out.println("PASO 6");
		 * 
		 * String resultadoEjecucion = job.ejecutar("PR26"); boolean
		 * ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
		 * 
		 * assertTrue(ejecucionCorrectaJob, "El job PR26 no se ejecuto correctamente");
		 * 
		 * 
		 * // PASO 7 addStep("Ejecutar PR35"); System.out.println("PASO 7");
		 * 
		 * resultadoEjecucion = job.ejecutar("PR35"); ejecucionCorrectaJob =
		 * job.fueEjecutadoCorrectamente(resultadoEjecucion);
		 * 
		 * assertTrue(ejecucionCorrectaJob, "El job PR35 no se ejecuto correctamente");
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
		return "Construido. Validar la actualizaci�n de Jerarqu�a Organizativa de Tienda."
				+ "-DB: 10.184.37.12:1521/FCRMSQA.FEMCOM.NET " + "-JOBS: " + "-CASOS QUE CUMPLE: "
				+ "MTC-FT-003 Modificaci�n de Receta - MREGUL - PLU - IVA16 - Interior, "
				+ "MTC-FT-004 Modificaci�n de Receta - MREGUL - PLU - IVA08 - Frontera.";
	}

	@Override
	public String setTestDesigner() {
		return "Guerrero Vargas - Equipo RMS";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_Modificacion_Receta";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
