package RMS.proyectoNeo;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RMS.util.GlobalVariables;
import RMS.util.QueriesTranTypes;
import RMS.util.PageObject.TranTypes;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_ModificacionenDescripcionCortayLargadeArticuloExistenteTran_Type1012 extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_ModificacionenDescripcionCortayLargadeArticuloExistenteTran_Type1012_test(HashMap<String, String> data) throws Exception {
		
		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_BDRMSCQA, GlobalVariables.DB_USER_BDRMSCQA,
				GlobalVariables.DB_PASSWORD_BDRMSCQA);

		SeleniumUtil su = new SeleniumUtil(new IETest(), true);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("RMS\\"+"RMSS");
		File Fol = new File(pathImg);
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();	
		TranTypes t = new TranTypes(sk, su, new Robot(),
				pathimg, ListImage, ImgPatter, patterns, Fol, pathImg, dbRms, data, testCase);
		
		System.out.println(dbRms.getHost());		
		
		//PASO 1
				addStep("Seleccionar art�culo que cumpla con las caracter�sticas necesarias para que se generen los tran_type. "
						+ "Seleccionar tienda a la que no est� ligada el art�culo.");
				System.out.println("PASO 1"); 
				
				String item = data.get("item");
				String store = data.get("store");
				
				String udaItemLocStore = String.format(QueriesTranTypes.ITEM_LOC, item, store);
				System.out.println(udaItemLocStore);

				SQLResult executeQuery = executeQuery(dbRms, udaItemLocStore);
				
				boolean statusQuery = executeQuery.isEmpty();
				if (!statusQuery) {
					testCase.addQueryEvidenceCurrentStep(executeQuery);
					item = executeQuery.getData(0, "ITEM");
				}

				assertNotNull(item, "No existe ITEM no ligado a STORE = " + store);
				
				testCase.addTextEvidenceCurrentStep("Item seleccionado: " + item);
				System.out.println("Item seleccionado: " + item);
		
		//PASO 1

		addStep("Ingresar a RMS");
		System.out.println("PASO 1");

		t.loginRMS();

		// PASO 2
		addStep("Seleccionar el menu items y elegir la opcion items");
		System.out.println("PASO 2");

		t.menuItemItems();

		// PASO 3
		addStep("Seleccionar en el campo Action la opci�n Edit item. "
				+ "En el campo Item, capturar el n�mero de �tem. " + "Presionar OK.");
		System.out.println("PASO 3");

		t.editItem(data.get("item"));
		
		
		// PASO 4
		addStep("Seleccionar el men� Locations para editar art�culo." + "En la pantalla itemloc, seleccionar la tienda."
				+ "Validar la descripcion corta y larga" + "Se modifica la descripcion corta y larga");
		System.out.println("PASO 4");

		t.modificarDescripcion(store);

		// PASO 8
		addStep("Se valida en la tabla POS_MODS los movimientos insertados para el art�culo-tienda");
		System.out.println("PASO 8");

		String posMods = String.format(QueriesTranTypes.POS_MODS_ITEM_STORE, item, store);
		System.out.println(posMods);

		executeQuery = executeQuery(dbRms, posMods);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery,
				"No se encontraron registros con ITEM = " + item + " y STORE = " + store + " en POS_MODS");
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
		return "ATC_FT_ModificacionenDescripcionCortayLargadeArticuloExistenteTran_Type1012_test";
		}

	@Override
	public String setTestDesigner() {
		return "Equipo RMS";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_ModificacionenDescripcionCortayLargadeArticuloExistenteTran_Type1012";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
