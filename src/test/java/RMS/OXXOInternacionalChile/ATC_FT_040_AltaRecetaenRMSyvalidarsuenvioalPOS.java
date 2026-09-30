package RMS.OXXOInternacionalChile;

import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RMS.util.GlobalVariables;
import RMS.util.Queries_Tiendas;
import RMS.util.PageObject.Tiendas_Jorge;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_040_AltaRecetaenRMSyvalidarsuenvioalPOS extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_040_AltaRecetaenRMSyvalidarsuenvioalPOS_test(HashMap<String, String> data) throws Exception {

		/*
		 * Informacion para consultas en base de datos de CHILE
		 */
		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_BDCHRMSCQA, GlobalVariables.DB_USER_BDCHRMSCQA,
				GlobalVariables.DB_PASSWORD_BDCHRMSCQA);

		String cb = null;
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

		Tiendas_Jorge t = new Tiendas_Jorge(sk, su, cb, robot, pathimg, ListImage, ImgPatter, patterns, Fol, pathImg, dbRms, st, data,testCase);
		
		SQLResult executeQuery;
		
		//Paso 1
		addStep("Ingresar a RMS a trav�s de la siguiente liga: "+data.get("rms_url")+" y hacer login");

		testCase.addScreenShotCurrentStep("Paso1");
		t.Login_En_RMS();

		// Paso 2
		addStep("Seleccionar el folder items, elegir la opcion items y dar clic en el bot�n open");
		t.navItems();

		// Paso 3
		addStep("Seleccionar en el campo Action la opci�n New item.\r\n"
				+ "Llenar los siguientes campos seg�n corresponda:\r\n" + " Subcategoria <<<subcategoria>>>\r\n"
				+ " Segmento <<<segmento>>>\r\n" + " Subsegmento <<<subsegmento>>>\r\n" + " Pack item <<<Yes>>>\r\n"
				+ " Presionar OK.");

		t.NewItemVirtualYes();

		// Paso 4
		addStep("Capturar o seleccionar la opci�n correspondiente de los siguientes campos:\r\n"
				+ "  item Type <<<EAN8>>>\r\n" + "  item <<<n�mero item>>>\r\n"
				+ "  Description <<<Descripci�n Larga Item>>>\r\n" + "  Short Desc <<<Descripci�n corta Item>>>\r\n"
				+ "  Tran Level <<<Level 1>>>\r\n" + "  Merchandise <<<Check>>>\r\n" + "  Forecastable <<<Check>>>\r\n"
				+ "  Catch Weight <<<Check>>>\r\n" + "  Constant Dimensions <<<Check>>>\r\n"
				+ "  Retail Zone <<<1>>>\r\n" + "  Cost Zone <<<1>>>\r\n"
				+ "  Selling Unit Retail <<<Precio Unitario de Venta>>>\r\n"
				+ "  Suggested Retail <<<Precio Sugerido de Venta>>>\r\n" + "  Store Order Multiple <<<Eaches>>>\r\n"
				+ "  Standard UOM <<<EA>>>\r\n" + "  Dar clic en Groc Attributes");
		t.fil_ItemVirtual();

		// Paso 5
		addStep("Capturar los datos correspondientes en los siguientes campos:\r\n" + "  package RMS.Size <<<1>>>\r\n"
				+ "  package RMS.UOM <<<KG>>>\r\n" + "  Dar clic en el bot�n OK.");
		t.FillItemMaster();

		// Paso 6
		addStep("Dar clic en la opci�n Supplier");
		t.ClicBtnSupplier();

		// Paso 7
		addStep("Dar clic en el bot�n Add, en el campo Supplier, seleccionar un proveedor de la lista y dar clic en el bot�n Apply.");
		t.AddSupplier();

		// Paso 8
		addStep("Dar clic en la opci�n Item-Supplier-Origin-Country");
		t.Clicbtn_ISOC();

		// Paso 9
		addStep("En el campo Origin Country, seleccionar el pa�s de origen del proveedor y dar clic en el bot�n Create y llenar los siguientes campos:\r\n"
				+ "  Inner Size <<<Tama�o interior>>>\r\n" + "  Case Size <<<Tama�o de la caja>>>\r\n"
				+ "  Tier <<<Nivel>>>\r\n" + "  Height <<<Altura>>>\r\n" + "  Case Cost <<<Costo Caja>>>\r\n"
				+ "  Unit Cost <<<Costo Unitario>>>");
		t.SelectOriginCountry();

		// Paso 10
		addStep("En la secci�n Dimensions, llenar los campos de cada package RMS.Type (Each, Case, Inner, Palet) dando click en OK + Repeat en cada caso.\r\n"
				+ "  Length <<<Largo>>>\r\n" + "  Width <<<Ancho>>>\r\n" + "  Height <<<Altura>>>\r\n"
				+ "  Dimension Unit Of Measure <<<CM>>>\r\n" + "  Volume Unit Of Measure <<<CM3>>>\r\n"
				+ "  Gross Weight <<<Peso Bruto>>>\r\n" + "  Net Weight <<<Peso Neto>>>\r\n"
				+ "  Weight Unit Of Measure <<<KG>>>\r\n" + "  Liquid Volume <<<Volumen>>>\r\n"
				+ "  Liquid Unit Of Measure <<<CM3>>>\r\n" + "  Al finalizar, presionar el bot�n OK.");
		t.FillPackageType();

		// Paso 11
		addStep("Dar clic en la opci�n Retail by Zone.");
		t.clicRetailbyZone();

		// Paso 12
		addStep("Despliega la zona seleccionada y se da clic en OK.");
		t.clic_btn_OK_skuretl();

		// Paso 13
		addStep("Dar clic en la opci�n Location.");
		t.clicLocations();

		// Paso 14
		addStep("En la ventana Item Location seleccionar los siguientes campos que se encuentran en la parte inferior izquierda de la pantalla:\r\n"
				+ "  Group Type <<<Store>>>\r\n" + "  Group Value <<<#Tienda>>>\r\n" + "  Presionar el bot�n Apply\r\n"
				+ "  Dar clic en el bot�n OK.");
		t.ItemLocation();

		// Paso 15
		addStep("Dar clic en la opci�n Submit.");
		t.ClicSubmit();

		// Paso 16
		addStep("Dar clic en la opci�n Approve y Presionar el bot�n OK.");
		t.ClicApprove();

		// Paso 17
		addStep("Validar que la informaci�n del item se vea reflejada en las tablas item_master, item_loc, item_supplier, item_supp_country y item_supp_country_dim dentro de la Base de Datos.");
		String item = data.get("Item");
		String queryItem = String.format(Queries_Tiendas.tbl_Item_Master, item);
		System.out.println(queryItem);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryItem);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(), "el item fue encontrado");

		queryItem = String.format(Queries_Tiendas.tbl_item_loc, item);
		System.out.println(queryItem);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryItem);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(), "el item fue encontrado");

		queryItem = String.format(Queries_Tiendas.tbl_item_supplier, item);
		System.out.println(queryItem);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryItem);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(), "el item fue encontrado");

		queryItem = String.format(Queries_Tiendas.tbl_item_supp_country, item);
		System.out.println(queryItem);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryItem);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(), "el item fue encontrado");

		queryItem = String.format(Queries_Tiendas.tbl_item_supp_country_dim, item);
		System.out.println(queryItem);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryItem);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(), "el item fue encontrado");

		// Paso 18
		addStep("Validar que se haya insertado el alta del art�culo receta en la tabla POS_MODS con tran type 1");
		queryItem = String.format(Queries_Tiendas.tbl_pos_mods, item);
		System.out.println(queryItem);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryItem);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(), "el tran_type 1 fue encontrado");

		// Paso 19
		addStep("Ejecutar la cadena de jobs del grupo AIX_PRECIOS_Y_PROMOCIONES hasta el job XXFC_PROCESA_POSDNLD_ITM en Control-M");
//		String resultadoEjecucion = "";
//		
//		resultadoEjecucion = job.ejecutar("rr_setup.sh_9_peru");
//		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
//		SoftAssert(ejecucionCorrectaJob, "El job rr_setup.sh_9_peru no se ejecuto correctamente");
//		
//		resultadoEjecucion = job.ejecutar("xxfc_pre_prmext");
//		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
//		SoftAssert(ejecucionCorrectaJob, "El job rule_check_peru no se ejecuto correctamente");
//		
//		resultadoEjecucion = job.ejecutar("prmxpld_peru");
//		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
//		SoftAssert(ejecucionCorrectaJob, "El job prmxpld_peru no se ejecuto correctamente");
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_040_AltaRecetaenRMSyvalidarsuenvioalPOS_test";
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_040_AltaRecetaenRMSyvalidarsuenvioalPOS";
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
