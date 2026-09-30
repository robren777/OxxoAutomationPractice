package RMS.proyectoNeo;

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

public class ATC_FT_GenerarImpuestoporPantallaVATItemMaintenanceTran_Type20 extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_GenerarImpuestoporPantallaVATItemMaintenanceTran_Type20_test(HashMap<String, String> data) throws Exception {
		
		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);

		SeleniumUtil su = new SeleniumUtil(new IETest(), true);
		SikuliUtil sk = new SikuliUtil();		
		String pathImg = sk.PatternPath("RMS\\"+"RMSS");
		File Fol = new File(pathImg);	
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();	
		Impuestos i = new Impuestos(sk, su, new Robot(),
				pathimg, ListImage, ImgPatter, patterns, Fol, pathImg, dbRms, data, testCase);
		JobUtil job = new JobUtil(su, testCase, data);

		System.out.println(dbRms.getHost());		
		
		
		//PASO 1
		addStep("Seleccionar art�culos que cumplan con las caracter�sticas "
				+ "necesarias para que se generen los tran_type 20 de impuestos. "
				+ "El articulo se encuentra activo en la tienda");
		System.out.println("PASO 1"); 
		
		String store = data.get("store");
		String item = data.get("item");
		
		String itemLoc = String.format(QueriesImpuestos.ITEM_LOC_ITEM_STORE, item, store);
		System.out.println(itemLoc);

		SQLResult executeQuery = executeQuery(dbRms, itemLoc);
	
		boolean statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);			
		}

		assertFalse(statusQuery, "No existen registros con ITEM = " + item + " y LOC = " + store
				+ " en ITEM_LOC");
		
		
		// PASO 2
		addStep("Validar los impuestos actuales de los art�culos en la tabla vat_item. "
				+ "No cuenta con el impuesto");
		System.out.println("PASO 2");
		
		String vatCode = data.get("vat_code");
		
		String vatItem = String.format(QueriesImpuestos.VAT_ITEM, item, vatCode);
		System.out.println(vatItem);

		executeQuery = executeQuery(dbRms, vatItem);
	
		statusQuery = executeQuery.isEmpty();
		if (statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);			
		}

		assertTrue(statusQuery, "Existen registros con ITEM = " + item + " y VAT_CODE = " + vatCode
				+ "en VAT_ITEM.");
		
		
		// PASO 3
		addStep("Ingresar a la aplicacion RMS");
		System.out.println("PASO 3"); 
		
		i.loginRMS();
		
		
		// PASO 4
		addStep("Seleccionar el menu items y elegir la opcion �tems");
		System.out.println("PASO 4");
		
		i.menuItemItems();
		
		
		// PASO 5
		addStep("Editar el art�culo seleccionando en el campo Action la opci�n Edit > item "
				+ "En el campo Item, capturar el n�mero de �tem. Presionar OK.");
		System.out.println("PASO 5");
		
		i.editItem();
		
		// PASO 6
		addStep("En la ventana Item Maintenance Window dar click en la opci�n VAT Maintenance.");
		System.out.println("PASO 6");
		
		i.vatMaintenance();
		
		
		// PASO 7
		String vatRegion = data.get("vat_region");
		
		addStep("En el campo VAT Region seleccionar la opci�n " + vatRegion
				+ " Dar clic en el bot�n Add VAT Code, a�adir el nuevo impuesto y dar clic en Ok. "
				+ " VAT Code >> IVA16, VAT Type >> Retail, Active Date >>");
		System.out.println("PASO 7");
		
		i.vatRegion(); 		
			
		
		// PASO 8
		addStep("Se valida que se haya insertado el nuevo impuesto en la tabla vat_item");
		System.out.println("PASO 8");
				
		String vatItemCode = String.format(QueriesImpuestos.VAT_ITEM, item, vatCode);
		System.out.println(vatItemCode);

		executeQuery = executeQuery(dbRms, vatItemCode);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con VAT_ITEM = " + item
				+ " y VAT_CODE = " + vatCode + " en VAT_ITEM");
		
		
		//PASO 9
		addStep("Ejecutar el job prepost con el par�metro vatdlxpl post.");
		System.out.println("PASO 9"); 

		String resultadoEjecucion = job.ejecutar("vatdlxpl");
		boolean ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
		
		assertTrue(ejecucionCorrectaJob, "El job vatdlxpl no se ejecuto correctamente");
		
				
		// PASO 10
		addStep("Se valida que en la tabla POS_MODS se hayan insertado los tran_type 20.");
		System.out.println("PASO 10");
		
		String posMods = String.format(QueriesImpuestos.POS_MODS_TRAN_TYPE_20_ITEM, item);
		System.out.println(posMods);

		executeQuery = executeQuery(dbRms, posMods);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con ITEM = " + item
				+ " y TRAN_TYPE = 20 en POS_MODS"); 
	
	
		// PASO 11
		addStep("Se ejecuta la interfaz PR26.");
		System.out.println("PASO 11"); 

		resultadoEjecucion = job.ejecutar("PR26");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
		
		assertTrue(ejecucionCorrectaJob, "El job PR26 no se ejecuto correctamente");
		
		
		// PASO 12
		addStep("Se valida que el documento ITM se encuentra en el buz�n de la tienda " + store
				+ " en la ruta /u01/posuser/FEMSA_OXXO/POS/10MON/50VAZ/backup");
		System.out.println("PASO 12");
		su.close();
		// TODO
	}

	@Override
	public void beforeTest() {}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_GenerarImpuestoporPantallaVATItemMaintenanceTran_Type20_test";
		}

	
	@Override
	public String setTestDesigner() {
		return "Equipo RMS";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_GenerarImpuestoporPantallaVATItemMaintenanceTran_Type20";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
