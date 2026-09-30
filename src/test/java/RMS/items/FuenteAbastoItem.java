package RMS.items;

import java.util.HashMap;

import org.testng.annotations.Test;

import modelo.BaseExecution;
import utils.sql.SQLUtil;import RMS.util.GlobalVariables;

public class FuenteAbastoItem extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_Fuente_Abasto_Item(HashMap<String, String> data) throws Exception {

		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_FCRMSQA, GlobalVariables.DB_USER_FCRMSQA,
				GlobalVariables.DB_PASSWORD_FCRMSQA);

		/*
		 * //SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true); SeleniumUtil su
		 * = new SeleniumUtil(new IETest(), true); SikuliUtil sk = new SikuliUtil();
		 * String pathImg = sk.PatternPath("RMS\\"+"RMSS"); File Fol = new File(pathImg);
		 * HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		 * ArrayList<Pattern> patterns = new ArrayList<Pattern>(); ArrayList<String>
		 * ListImage = new ArrayList<String>(); Pattern pathimg = new Pattern(); Items i
		 * = new Items (sk, su, new Robot(), pathimg, ListImage, ImgPatter, patterns,
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
		 * // PASO 2 addStep("Seleccionar el menu items y elegir la opcion items");
		 * System.out.println("PASO 2");
		 * 
		 * i.menuItemItems();
		 * 
		 * 
		 * // PASO 3 addStep("Seleccionar en el campo Action la opci�n Edit item. " +
		 * "En el campo Item, capturar el n�mero de �tem al que se le agregar� " +
		 * "una Fuente de Abasto."); System.out.println("PASO 3");
		 * 
		 * i.editItem();
		 * 
		 * 
		 * // PASO 4
		 * addStep("En la ventana Item Maintenance Window dar click en la opci�n Supplier."
		 * ); System.out.println("PASO 4");
		 * 
		 * i.itemSupplier();
		 * 
		 * 
		 * // PASO 5 addStep("En la ventana Item-Supplier dar click en el bot�n Add. " +
		 * "Dar click en el campo Supplier y seleccionar un proveedor de la lista. " +
		 * "Presionar el bot�n Apply"); System.out.println("PASO 5");
		 * 
		 * i.agregarSupplier();
		 * 
		 * // FALTA AGREGAR EL RESTO DE PASOS
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
		return "Construido. A�adir un Supplier a un Item." + "-DB: 10.184.37.12:1521/FCRMSQA.FEMCOM.NET " + "-JOBS: "
				+ "-CASOS QUE CUMPLE: " + "MTC-FT-006 A�adir una Fuente de Abasto a Item Existente, "
				+ "MTC-FT-007 Asignar Fuente de Abasto Primaria a Item Existente.";
	}

	@Override
	public String setTestDesigner() {
		return "Guerrero Vargas - Equipo RMS";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_Fuente_Abasto_Item";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
