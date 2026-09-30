package RMS.OXXOInternacionalPeruChile;

import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.App;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RMS.util.Queries_Tiendas;
import RMS.util.PageObject.Tiendas_Jorge;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;import RMS.util.GlobalVariables;
import RMS.util.JobUtil;

public class ATC_FT_039_ModificaciondeRecetayvalidarsuenvioalPOS extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_039_ModificaciondeRecetayvalidarsuenvioalPOS_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * Informacion para consultas en base de datos de PERU
		 */
		
		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_BDRMSPERQA, GlobalVariables.DB_USER_BDRMSPERQA,
				GlobalVariables.DB_PASSWORD_BDRMSPERQA);

		String cb = null;
		boolean ejecucionCorrectaJob = false;
		String resultadoEjecucion = null;
		boolean st = false;
		SeleniumUtil su = new SeleniumUtil(new IETest(), true);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("RMS\\"+"OXXO_Internacional");
		File Fol = new File(pathImg);
		Robot robot = new Robot();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();
		Tiendas_Jorge t = new Tiendas_Jorge(sk, su, cb, robot, pathimg, ListImage, ImgPatter, patterns, Fol, pathImg,
				dbRms, st, data, testCase);
		JobUtil job = new JobUtil(su, testCase, data);
		SQLResult executeQuery;
		Boolean statusQuery = false;

		// Paso 1
		addStep("Ingresar a RMS a trav�s de la siguiente liga: " + data.get("rms_url") + " y hacer login");
		testCase.addScreenShotCurrentStep("Paso1");
		t.Login_En_RMS();

		// Paso 2
		addStep("Seleccionar el menu Items y elegir la opci�n� FEMSA Packitem Maintenance.");
		t.navItemsFEMSA();

		// Paso 3
		addStep("En el campo Pack No capturamos el PLU de la receta, nos mostrara todos los insumos de esta."
				+ "Para Cambiar el factor nos posicionamos en el rengl�n del insumo� a modificar, "
				+ "en Quantity modificamos� el factor de consumo y damos click en Apply."
				+ " Cuando terminemos de aplicar los cambios solicitados damos click en OK.");
		t.fillPackNo();

		t.close_RMS_Menu();
		t.CloseBrowser();

		addStep("Revisar ITEM en tablas de RMS:\r\n" + " PACKITEM\r\n" + " ITEM_LOC\r\n" + " ITEM_MASTER");

		String packNo = data.get("packNo");
		String qty = data.get("quantity");
		String queryItem = String.format(Queries_Tiendas.tbl_item_PackNo, packNo);
		System.out.println(queryItem);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryItem);

		if (executeQuery.getData(0, "PACK_QTY").equals(qty)) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			statusQuery = true;
		}

		assertTrue(statusQuery, "El registro fue modificado");

		// item
		cb = App.getClipboard();
		queryItem = String.format(Queries_Tiendas.tbl_item_loc, cb);
		System.out.println(queryItem);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryItem);

//		if (executeQuery.getData(0, "PACK_QTY").equals(qty)) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//			statusQuery = true;
//		}
//
//		assertTrue(statusQuery,"El registro fue modificado");

		// item
		queryItem = String.format(Queries_Tiendas.tbl_Item_Master, cb);
		System.out.println(queryItem);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryItem);

//		if (executeQuery.getData(0, "PACK_QTY").equals(qty)) {
//			testCase.addQueryEvidenceCurrentStep(executeQuery);
//			statusQuery = true;
//		}
//
//		assertTrue(statusQuery,"El registro fue modificado");

		addStep("Ejecutar aix_precios en Control M Nota: Los JOBS a ejecutar son PR26.");
		resultadoEjecucion = "";

		resultadoEjecucion = job.ejecutar("PR26");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
		assertTrue(ejecucionCorrectaJob, "El job PR26 no se ejecuto correctamente");

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_039_Modificaci�ndeRecetayvalidarsuenvioalPOS_test";
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_039_Modificaci�ndeRecetayvalidarsuenvioalPOS";
	}

	@Override
	public String setTestDesigner() {
		return "Equipo RMS";
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
	public void beforeTest() {
	}

}
