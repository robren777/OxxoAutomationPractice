package RMS.RegresionesArticulos;

import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import org.sikuli.script.Pattern;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import RMS.util.ExecuteQuery;
import RMS.util.GlobalVariables;
import RMS.util.PageObject.CTRL_M_UTIL;
import RMS.util.PageObject.Tiendas_Jorge;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.ChromeTest;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_038_Generar_Impuesto_por_Pantalla_VAT_Item_Maintenance_Tran_Type_20 extends BaseExecution {
	
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_038_Generar_Impuesto_por_Pantalla_VAT_Item_Maintenance_Tran_Type_20_test(
			HashMap<String, String> data) throws Exception {

		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);

		// Queries
		String cb = null;
		boolean st = false;
		u = new SeleniumUtil(new IETest(), true);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("RMS\\"+"OXXO_Internacional");
		File Fol = new File(pathImg);
		Robot robot = new Robot();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();
		Tiendas_Jorge t = new Tiendas_Jorge(sk, u, cb, robot, pathimg, ListImage, ImgPatter, patterns, Fol, pathImg,
				dbRms, st, data, testCase);
		boolean resultadoEjecucion;

		ExecuteQuery e = new ExecuteQuery(testCase);

		String VATRegion = data.get("VATRegion");
		String VATCode = data.get("VATCode");
		String VATType = data.get("VATType");
		
		String item = "";
		String query;
		boolean result = false;
		
		String loc = data.get("loc");

		// Paso 1
		addStep("Seleccionar artículo que cumpla con las características necesarias para que se generen los tran_type.");
		LocalDate diaActualVAT = LocalDate.now();
		diaActualVAT = diaActualVAT.plusDays(6);
		
		DateTimeFormatter formatN = DateTimeFormatter.ofPattern("dd-MMM-yy", Locale.ENGLISH);
		String diaActualFormat = diaActualVAT.format(formatN).toUpperCase();
		
		String VATTypeSQL = t.getVATType(VATType);
		
		query = String.format("select IL.ITEM, IL.LOC, IL.LOCAL_ITEM_DESC, IL.STATUS from item_loc il, item_supp_country_loc iscl \r\n"
				+ "where il.item = iscl.item \r\n"
				+ "and il.loc = iscl.loc \r\n"
				+ "and il.primary_supp = iscl.supplier \r\n"
				+ "and il.status = 'A' and il.loc = '%s' and il.ITEM not in (SELECT ITEM FROM (SELECT ITEM, ROW_NUMBER() OVER (PARTITION BY ITEM ORDER BY  ITEM DESC)AS rn\r\n"
				+ "FROM vat_item\r\n"
				+ "WHERE ACTIVE_DATE = '%s'\r\n"
				+ "and VAT_REGION = '%s'\r\n"
				+ "and VAT_TYPE = '%s'\r\n"
				+ "and VAT_CODE = '%s') \r\n"
				+ "sub WHERE rn = 1) and rownum <= '10'", loc, diaActualFormat, VATRegion, VATTypeSQL, VATCode);
		result = e.ejecutarConsulta(dbRms,query);
		assertTrue(result, "No se han encontrado items que cumplan con el requisito");
		
		testCase.addBoldTextEvidenceCurrentStep("Item Seleccionado");
		query = String.format("select IL.ITEM, IL.LOC, IL.LOCAL_ITEM_DESC, IL.STATUS from item_loc il, item_supp_country_loc iscl \r\n"
				+ "where il.item = iscl.item \r\n"
				+ "and il.loc = iscl.loc \r\n"
				+ "and il.primary_supp = iscl.supplier \r\n"
				+ "and il.status = 'A' and il.loc = '%s' and il.ITEM not in (SELECT ITEM FROM (SELECT ITEM, ROW_NUMBER() OVER (PARTITION BY ITEM ORDER BY  ITEM DESC)AS rn\r\n"
				+ "FROM vat_item\r\n"
				+ "WHERE ACTIVE_DATE = '%s'\r\n"
				+ "and VAT_REGION = '%s'\r\n"
				+ "and VAT_TYPE = '%s'\r\n"
				+ "and VAT_CODE = '%s') \r\n"
				+ "sub WHERE rn = 1) and rownum <= '1'", loc, diaActualFormat, VATRegion, VATTypeSQL, VATCode);
		result = e.ejecutarConsulta(dbRms,query);
		assertTrue(result, "No se han encontrado items que cumplan con el requisito");
		
		item = e.getData(dbRms, query, 0, "ITEM");
		
		testCase.addBoldTextEvidenceCurrentStep("Validar los impuestos actuales del artículo en la tabla vat_item");
		query = String.format("select * from vat_item where item = '%s'", item);
		result = e.executeQuerySeparatedColumns(dbRms,query);
		assertTrue(result, "No se han encontrado registros");
		
		// Paso 2
		addStep("Ingresar a RMS a traves de la siguiente liga: " + data.get("rms_url") + " y hacer login");
		testCase.addScreenShotCurrentStep("Paso1");
		t.Login_En_RMS();

		// Paso 3
		addStep("En el menu Items>Items>, editar el articulo, acceder a la pantalla VAT Maintenance (vatitem).");
		t.navItems();
		t.EditItemVirtual(item);

		// Paso 4
		addStep("Editar articulo, clic en la opcion VAT Maintenance para anadir el nuevo impuesto.");
		t.NavVATMaintenance();
		t.fillVATMaintenance(VATRegion, VATCode, VATType);

		t.clic_btn_OKItems();
		t.close_RMS_2();
		
		t.CloseBrowser();

		// Paso 5
		addStep("Validar que se haya insertado el nuevo impuesto en la tabla vat_item.");

		LocalDate diaActual = t.getDate();
		formatN = DateTimeFormatter.ofPattern("dd-MMM-yy", Locale.ENGLISH);
		diaActualFormat = diaActual.format(formatN).toUpperCase();
		
		System.out.println(diaActualFormat);
		
		query = String.format("select * from vat_item where item = '%s' and VAT_REGION = '%s' and ACTIVE_DATE = '%s' and VAT_TYPE = '%s' and VAT_CODE = '%s'", item, VATRegion, diaActualFormat, VATTypeSQL, VATCode);
		result = e.executeQuerySeparatedColumns(dbRms,query);
		assertTrue(result, "No se ha encontrado el nuevo impuesto");
		
		// Paso 6
		addStep("Ejecutar el job prepost con el parametro vatdlxpl post.");
		u = new SeleniumUtil(new ChromeTest(), true);
		CTRL_M_UTIL j = new CTRL_M_UTIL(u, testCase);
		
		j.getControlM();
		testCase.addScreenShotCurrentStep("Ingreso a control-M");	
		
		testCase.addBoldTextEvidenceCurrentStep("JOB: prepostbtchcyclpre");
		resultadoEjecucion = j.ejecutarJob("prepostbtchcyclpre");
		assertTrue(resultadoEjecucion, "El JOB prepostbtchcyclpre no se ha ejecutado exitosamente");

		testCase.addBoldTextEvidenceCurrentStep("JOB: it_setupsh");
		resultadoEjecucion = j.ejecutarJob("it_setupsh");
		assertTrue(resultadoEjecucion, "El JOB it_setupsh no se ha ejecutado exitosamente");
		
		testCase.addBoldTextEvidenceCurrentStep("JOB: vatdlxpl");
		resultadoEjecucion = j.ejecutarJob("vatdlxpl2");
		assertTrue(resultadoEjecucion, "El JOB vatdlxpl no se ha ejecutado exitosamente");
		
		testCase.addBoldTextEvidenceCurrentStep("JOB: post");
		resultadoEjecucion = j.ejecutarJob("post");
		assertTrue(resultadoEjecucion, "El JOB post no se ha ejecutado exitosamente");
		
		// Paso 7
		addStep("Validar que en la tabla pos_mods se hayan insertado los tran_typ");
		query = String.format("Select * from POS_MODS WHERE ITEM = '%s' and rownum <= 20", item);
		result = e.executeQuerySeparatedColumns(dbRms,query);
		assertTrue(result, "No se han encontrado registros con item: " + item);

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_038_Generar_Impuesto_por_Pantalla_VAT_Item_Maintenance_Tran_Type_20_test";
	}

	@Override
	public String setTestDescription() {
		return "Agregar impuesto a un articulo desde la pantalla VAT Item Maintenance.";
	}

	@Override
	public String setTestDesigner() {
		return "1000068916 - Jorge Hernandez";
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

	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}
}