package RMS.RegresionOrdenDeCompra;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RMS.util.GlobalVariables;
import RMS.util.PageObject.Ordenes;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_008_GenerarOrdendeCompraCentral_Cerveza_Tienda_ProveedorDirecto_conDeal_Interior extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_008_GenerarOrdendeCompraCentral_Cerveza_Tienda_ProveedorDirecto_conDeal_Interior_test(HashMap<String, String> data) throws Exception {
		
		/*
		 * En este caso no se tiene concordancia ni continuidad en los pasos, se debe revisar la prueba manual nuevamente
		 */
		
		/*
		 * Datos para ingresar a la base de datos RMS 
		 */
		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);
		
		String cb = null;
		boolean st = false;
		SeleniumUtil su = new SeleniumUtil(new IETest(), true);
		SikuliUtil sk = new SikuliUtil();		
		String pathImg = sk.PatternPath("RMS\\"+"RMSS");
		File Fol = new File(pathImg);	
		Robot robot = new Robot();		
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();
		Ordenes o = new Ordenes(sk, su, cb, robot, pathimg, ListImage, ImgPatter, patterns, Fol, pathImg, dbRms, st, data,testCase);
		
		SQLResult executeQuery;
		Boolean statusQuery = false;
		
		//Paso 1
		addStep("Ingresar a RMS a trav�s de la siguiente liga: "+data.get("rms_url")+" y hacer login");
		testCase.addScreenShotCurrentStep("Paso1");
		o.loginRMS();	
		
		//Paso 2
		addStep("Selecci�n de New Order y se da clic en OK.");
		o.navOrderingOrders();
		o.actionNewOrder();
		
		//Paso 3
		addStep("Se ingresan los datos generales y se da clic en Item");
		o.fillNewOrder();

		//Paso 4
		addStep("Se ingresa el item y se da clic en OK");
		o.fillNewOrderItem();
		
		
		
/*		
		//Paso 4
		addStep("Seleccionar en el campo Action la opci�n New item.\r\n"
				+ "Llenar los siguientes campos seg�n corresponda:\r\n"
				+ " Subcategoria <<<subcategoria>>>\r\n"
				+ " Segmento <<<segmento>>>\r\n"
				+ " Subsegmento <<<subsegmento>>>\r\n"
				+ " Pack item <<<No>>>\r\n"
				+ " Presionar OK.");
		
		o.NewItem_2();
		
		//Paso 5
		addStep("Capturar o seleccionar la opci�n correspondiente de los siguientes campos:\r\n"
				+ "  item Type <<<EAN8>>>\r\n"
				+ "  item <<<n�mero item>>>\r\n"
				+ "  Description <<<Descripci�n Larga Item>>>\r\n"
				+ "  Short Desc <<<Descripci�n corta Item>>>\r\n"
				+ "  Tran Level <<<Level 1>>>\r\n"
				+ "  Merchandise <<<Check>>>\r\n"
				+ "  Forecastable <<<Check>>>\r\n"
				+ "  Catch Weight <<<Check>>>\r\n"
				+ "  Constant Dimensions <<<Check>>>\r\n"
				+ "  Retail Zone <<<1>>>\r\n"
				+ "  Cost Zone <<<1>>>\r\n"
				+ "  Selling Unit Retail <<<Precio Unitario de Venta>>>\r\n"
				+ "  Suggested Retail <<<Precio Sugerido de Venta>>>\r\n"
				+ "  Store Order Multiple <<<Eaches>>>\r\n"
				+ "  Standard UOM <<<EA>>>\r\n"
				+ "  Dar clic en Groc Attributes");
		o.fil_ItemVirtual_SinItem();
		
		//Paso 6
		addStep("Capturar los datos correspondientes en los siguientes campos:\r\n"
				+ "  package RMS.Size <<<1>>>\r\n"
				+ "  package RMS.UOM <<<KG>>>\r\n"
				+ "  Dar clic en el bot�n OK.");
		o.FillItemMaster();
		
		//Paso 7
		addStep("Dar clic en la opci�n Supplier");
		o.ClicBtnSupplier();
		
		
		//Paso 8
		addStep("Dar clic en el bot�n Add, en el campo Supplier, seleccionar un proveedor de la lista y dar clic en el bot�n Apply.");
		o.AddSupplier();
		
		
		//Paso 9
		addStep("Dar clic en la opci�n Item-Supplier-Origin-Country");
		o.Clicbtn_ISOC();			
		
		//Paso 10
		addStep("En el campo Origin Country, seleccionar el pa�s de origen del proveedor y dar clic en el bot�n Create y llenar los siguientes campos:\r\n"
				+ "  Inner Size <<<Tama�o interior>>>\r\n"
				+ "  Case Size <<<Tama�o de la caja>>>\r\n"
				+ "  Tier <<<Nivel>>>\r\n"
				+ "  Height <<<Altura>>>\r\n"
				+ "  Case Cost <<<Costo Caja>>>\r\n"
				+ "  Unit Cost <<<Costo Unitario>>>");
		o.SelectOriginCountry();	
		
		//Paso 11
		addStep("En la secci�n Dimensions, llenar los campos de cada package RMS.Type (Each, Case, Inner, Palet) dando click en OK + Repeat en cada caso.\r\n"
				+ "  Length <<<Largo>>>\r\n"
				+ "  Width <<<Ancho>>>\r\n"
				+ "  Height <<<Altura>>>\r\n"
				+ "  Dimension Unit Of Measure <<<CM>>>\r\n"
				+ "  Volume Unit Of Measure <<<CM3>>>\r\n"
				+ "  Gross Weight <<<Peso Bruto>>>\r\n"
				+ "  Net Weight <<<Peso Neto>>>\r\n"
				+ "  Weight Unit Of Measure <<<KG>>>\r\n"
				+ "  Liquid Volume <<<Volumen>>>\r\n"
				+ "  Liquid Unit Of Measure <<<CM3>>>\r\n"
				+ "  Al finalizar, presionar el bot�n OK.");
		o.FillPackageType();		
		
		//Paso 12
		addStep("Dar clic en la opci�n Retail by Zone.");
		o.clicRetailbyZone();	
		
		//Paso 13
		addStep("Despliega la zona seleccionada y se da clic en OK.");
		o.clic_btn_OK_skuretl();	
		
		//Paso 14
		addStep("Dar clic en la opci�n Location.");
		o.clicLocations();	
		
		//Paso 15
		addStep("En la ventana Item Location seleccionar los siguientes campos que se encuentran en la parte inferior izquierda de la pantalla:\r\n"
				+ "  Group Type <<<Store>>>\r\n"
				+ "  Group Value <<<#Tienda>>>\r\n"
				+ "  Presionar el bot�n Apply\r\n"
				+ "  Dar clic en el bot�n OK.");
		o.ItemLocation();	
		
		//Paso 16
		addStep("Dar clic en la opci�n Submit.");
		o.ClicSubmit();		
		
		//Paso 17
		addStep("Dar clic en la opci�n Approve y Presionar el bot�n OK.");
		o.ClicApprove();	
		
		o.close_RMS_CostChange();
		o.CloseBrowser();
		
		
		//Paso 18
		addStep("Validar que la informaci�n del item se vea reflejada en las tablas item_master, item_loc, item_supplier, item_supp_country y item_supp_country_dim dentro de la Base de Datos.");
		cb = App.getClipboard();
		System.out.println(cb);
		String item = cb;
		String store = null;
		String queryItem = String.format(Queries_Tiendas.tbl_Item_Master,item);
		System.out.println(queryItem);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryItem);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(),"el item fue encontrado");
		
		queryItem = String.format(Queries_Tiendas.tbl_item_loc,item);
		System.out.println(queryItem);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryItem);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			store = executeQuery.getData(0, "LOC");
		}

		assertTrue(!executeQuery.isEmpty(),"el item fue encontrado");
		
		queryItem = String.format(Queries_Tiendas.tbl_item_supplier,item);
		System.out.println(queryItem);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryItem);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertTrue(!executeQuery.isEmpty(),"el item fue encontrado");
		
		queryItem = String.format(Queries_Tiendas.tbl_item_supp_country,item);
		System.out.println(queryItem);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryItem);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertTrue(!executeQuery.isEmpty(),"el item fue encontrado");
		
		queryItem = String.format(Queries_Tiendas.tbl_item_supp_country_dim,item);
		System.out.println(queryItem);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryItem);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertTrue(!executeQuery.isEmpty(),"el item fue encontrado");
		
		//Paso 19
		addStep("Validar que se haya insertado el alta del art�culo receta en la tabla POS_MODS con tran type 1");
		
		queryItem = String.format(Queries_Tiendas.tbl_pos_mods,item);
		System.out.println(queryItem);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryItem);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertTrue(!executeQuery.isEmpty(),"el tran_type 1 fue encontrado");		
		
		////////////////////////Consultas extra//////////////////////
		queryItem = Queries_Tiendas.tbl_xxfc_configuracion_general;
		System.out.println(queryItem);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryItem);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertTrue(!executeQuery.isEmpty(),"Se realizo la consulta");
		
		store = data.get("GroupValue");
		queryItem = String.format(Queries_Tiendas.tbl_xxfc_tiendas_migradas_geocom,store);
		System.out.println(queryItem);
		System.out.println("conexion = "+ dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryItem);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}
		
		assertTrue(!executeQuery.isEmpty(),"El registro cuenta con estatus 1");	
*/
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_008_GenerarOrdendeCompraCentral_Cerveza_Tienda_ProveedorDirecto_conDeal_Interior_test";
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_008_GenerarOrdendeCompraCentral_Cerveza_Tienda_ProveedorDirecto_conDeal_Interior";
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
	public void beforeTest() {}

}
