package RMS.OXXOInternacionalPeruChile;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RMS.util.PageObject.Items_Jorge;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;import RMS.util.GlobalVariables;

public class ATC_FT_006_AnadirunaFuentedeAbastoaItemExistente extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_AnadirunaFuentedeAbastoaItemExistente_test(HashMap<String, String> data) throws Exception {
		
		/*
		 * Informacion para consultas en base de datos de PERU
		 */
		
		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_BDRMSPERQA, GlobalVariables.DB_USER_BDRMSPERQA,
				GlobalVariables.DB_PASSWORD_BDRMSPERQA);

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

		Items_Jorge i = new Items_Jorge(sk, su, cb, robot, pathimg, ListImage, ImgPatter, patterns, Fol, pathImg, dbRms, st, data, testCase);
		System.out.println("JOBS");
		
		//Paso 1
		addStep("Ingresar a RMS a trav�s de la siguiente liga: "+data.get("rms_url")+" y hacer login");
		testCase.addScreenShotCurrentStep("Paso1");
		i.Login_En_RMS();

		// Paso 2
		addStep("Seleccionar el menu items y elegir la opci�n �tems");
		i.navItems();

		// Paso 3
		addStep("Seleccionar en el campo Action la opci�n Edit item. En el campo Item, capturar el n�mero de �tem al que se le agregar� una Fuente de Abasto. Presionar OK");
		i.EditItem();

		// Paso 4
		addStep("En la ventana Item Maintenance Window dar click en la opci�n Supplier.");
		i.clicSuppliers();

		// Paso 5
		addStep("En la ventana Item-Supplier dar click en el bot�n Add.\r\n"
				+ "Dar click en el campo Supplier y seleccionar un proveedor de la lista. Presionar el bot�n Apply");
		i.appliSupplier();

		// Paso 6
		addStep("Dar click en Item-Supplier-Origin Country.");
		i.clicOriginCountry();

		// Paso 8
		addStep("En el campo Origin Country seleccionar el pa�s de origen y dar click en el bot�n Create. Llenar los siguientes campos:Case Cost >>Unit Cost >>Inner Size >>Case Size >>Tier >>Height >> Al finalizar dar click en OK.");
		i.selectOriginContry();

		// Paso 9
		addStep("Regresa a la pantalla Item-Supplier y en la ventana Item-Supllier dar click en el bot�n OK.");
		i.clic_btnOK();

		// Paso 10
		addStep("Dar clic en OK para Guarar los cambios");
		i.clic_btnOK_itmaster();

		i.close_RMS();
		i.CloseBrowser();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_AnadirunaFuentedeAbastoaItemExistente_test";
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_006_AnadirunaFuentedeAbastoaItemExistente";
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
