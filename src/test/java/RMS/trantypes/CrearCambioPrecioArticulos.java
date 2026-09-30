package RMS.trantypes;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RMS.util.GlobalVariables;
import RMS.util.JobUtil;
import RMS.util.QueriesTranTypes;
import RMS.util.PageObject.TranTypes;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.ChromeTest;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class CrearCambioPrecioArticulos extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_Crear_Cambio_Precio_Articulos(HashMap<String, String> data) throws Exception {

		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);

		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		SeleniumUtil su = new SeleniumUtil(new IETest(), true);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("RMS\\"+"RMSS");
		File Fol = new File(pathImg);
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();
		TranTypes t = new TranTypes(sk, su, new Robot(), pathimg, ListImage, ImgPatter, patterns, Fol, pathImg, dbRms,
				data, testCase);
		JobUtil job = new JobUtil(u, testCase, data);

		System.out.println(dbRms.getHost());
		/*
		 * // PASO 1
		 * addStep("Seleccionar art�culo que cumpla con las caracter�sticas necesarias para que se generen los tran_type. "
		 * + "Seleccionar tienda a la que no est� ligada el art�culo.");
		 * System.out.println("PASO 1");
		 * 
		 * // PRECIO NORMAL String group = data.get("group_id"); String zone =
		 * data.get("zone_id"); String store = data.get("store"); String item =
		 * data.get("item");
		 * 
		 * String itemZonePrice = String.format(JQueriesTranTypes.ITEM_ZONE_PRICE,
		 * group, zone, store, item); System.out.println(itemZonePrice);
		 * 
		 * SQLResult executeQuery = executeQuery(dbRms, itemZonePrice);
		 * 
		 * boolean statusQuery = executeQuery.isEmpty(); if (!statusQuery) {
		 * testCase.addQueryEvidenceCurrentStep(executeQuery); }
		 * 
		 * assertFalse(statusQuery, "No existen registros con ZONE_GROUP_ID = " + group
		 * + ", ZONE_ID = " + zone + ", LOC = " + store + " E ITEM = " + item +
		 * " en ITEM_ZONE_PRICE");
		 * 
		 * // MULTIUNIT String groupMu = data.get("group_id_mu"); String zoneMu =
		 * data.get("zone_id_mu"); String storeMu = data.get("store_mu"); String itemMu
		 * = data.get("item_mu");
		 * 
		 * String itemZonePriceMu = String.format(JQueriesTranTypes.ITEM_ZONE_PRICE_MU,
		 * groupMu, zoneMu, storeMu, itemMu); System.out.println(itemZonePriceMu);
		 * 
		 * executeQuery = executeQuery(dbRms, itemZonePriceMu);
		 * 
		 * statusQuery = executeQuery.isEmpty(); if (!statusQuery) {
		 * testCase.addQueryEvidenceCurrentStep(executeQuery); }
		 * 
		 * assertFalse(statusQuery, "No existen registros con ZONE_GROUP_ID = " +
		 * groupMu + ", ZONE_ID = " + zoneMu + ", LOC = " + storeMu + " E ITEM = " +
		 * itemMu + " en ITEM_ZONE_PRICE");
		 * 
		 */
		// PASO 2
		addStep("Ingresar a la aplicacion RMS");
		System.out.println("PASO 2");

		t.loginRMS();

		// PASO 3
		addStep("Seleccionar en el men� Pricing>Retail/Cost Change");
		System.out.println("PASO 3");

		t.menuPricingRetail();

		// PASO 4
		addStep("Seleccionar las opciones Action = New, Origin=Retail y Item Type = Single Item, "
				+ "capturar el art�culo y clic en OK.");
		System.out.println("PASO 4");

		t.precioNuevo();

		// PASO 5
		addStep("Capturar la descripci�n, la fecha, clic en Locations para agregar la zona de precio.");
		System.out.println("PASO 5");

		t.agregarZonaPrecio();

		// PASO 6
		addStep("Capturar el tipo de cambio de precio y su valor.");
		System.out.println("PASO 6");

		t.capturarCambioPrecio();

		// PASO 7
		addStep("Presentar y aprobar el cambio de precio.");
		System.out.println("PASO 7");

		t.submitCambio();
		t.aprobarCambio();

		// PASO 8
		addStep("Crear cambio de precio multiunit. Seleccionar las opciones Action = New, Origin=Retail y Item Type = Single Item, "
				+ "capturar el art�culo y clic en OK.");
		System.out.println("PASO 8");

		t.precioNuevoMU();

		// PASO 9
		addStep("Capturar la descripci�n, la fecha, clic en Locations para agregar la zona de precio.");
		System.out.println("PASO 9");

		t.agregarZonaPrecioMU();

		// PASO 10
		addStep("Capturar el tipo de cambio de precio y su valor.");
		System.out.println("PASO 10");

		t.capturarCambioPrecioMU();

		// PASO 11
		addStep("Presentar y aprobar el cambio de precio.");
		System.out.println("PASO 11");

		t.submitCambio();
		t.aprobarCambio();

		// PASO 12
		addStep("Ejecutar la cadena de jobs AIX_PRECIOS");
		System.out.println("PASO 12");

		String resultadoEjecucion = job.ejecutar("pcdnld");
		boolean ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job pcdnld no se ejecuto correctamente");

		// PASO 13
		addStep("Se valida que en la tabla POS_MODS se hayan insertado los tran_type 11.");
		System.out.println("PASO 13");

		// PRECIO NORMAL
		String item = data.get("item");

		String posMods = String.format(QueriesTranTypes.POS_MODS_ITEM_TRAN_TYPE_11, item);
		System.out.println(posMods);

		SQLResult executeQuery = executeQuery(dbRms, posMods);

		boolean statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con ITEM = " + item + " y TRAN_TYPE = 11 en POS_MODS");

		// MULTIUNIT
		String itemMu = data.get("item_mu");

		posMods = String.format(QueriesTranTypes.POS_MODS_ITEM_TRAN_TYPE_11, itemMu);
		System.out.println(posMods);

		executeQuery = executeQuery(dbRms, posMods);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con ITEM = " + item + " y TRAN_TYPE = 11 en POS_MODS");
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
		return "Construido. Crear cambios de precio para art�culos de M�xico."
				+ "-DB: 10.184.37.12:1521/FCRMSQA.FEMCOM.NET " + "-JOBS: " + "-CASOS QUE CUMPLE: " + "";
	}

	@Override
	public String setTestDesigner() {
		return "Guerrero Vargas - Equipo RMS";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_Crear_Cambio_Precio_Articulos";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
