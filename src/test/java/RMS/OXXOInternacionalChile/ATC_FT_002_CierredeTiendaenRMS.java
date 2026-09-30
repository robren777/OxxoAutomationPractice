package RMS.OXXOInternacionalChile;

import static org.testng.Assert.assertEquals;

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

public class ATC_FT_002_CierredeTiendaenRMS extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_CierredeTiendaenRMS_test(HashMap<String, String> data) throws Exception {
		
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

		Tiendas_Jorge t = new Tiendas_Jorge(sk, su, cb, robot, pathimg, ListImage, ImgPatter, patterns, Fol, pathImg, dbRms, st, data, testCase);
		SQLResult executeQuery;
		
		//Paso 1
		addStep("Ingresar a RMS a trav�s de la siguiente liga: "+data.get("rms_url")+" y hacer login");

		testCase.addScreenShotCurrentStep("Paso1");
		t.OpenBrowser();
		t.Login_En_RMS();

		// Paso 2
		addStep("Seleccionar el folder Action, elegir la opcion Organizational Hierarchy y dar clic en el bot�n open");
		t.Org_Herarky();

		// Paso 4
		addStep("Seleccionar la opci�n Store dentro de la jerarqu�a organizacional.");
		t.OpenStore();

		// Paso 5
		addStep("En la secci�n Filter, capturar el n�mero de tienda en el campo store y dar clic en la tecla tab para que nos arroje el nombre de la tienda y dar clic en el bot�n Apply filter.");
		t.CapturaTienta();

		// Paso 6
		addStep("Dar clic en el bot�n Edit");
		t.ClicBtnEdit();

		// Paso 7
		addStep("Capturar la fecha de cierre de tienda en el campo Store Close Date o elegir la fecha mediante el bot�n de calendario.");
		t.setFCierre();

		// Paso 8
		addStep("Capturar los d�as de suspensi�n de pedidos en el campo Stop Order Days.");
		t.setNCierre();

		// Paso 9
		addStep("Validar que no existan transacciones pendientes para la tienda (�rdenes de compra, transferencias, devoluciones).");
		System.out.println("validar que no existan transacciones pendientes e la tienda");
		// Paso 10
		addStep("Verificar que el inventario de mercanc�a en tienda se encuentre en ceros");
		System.out.println("verificar que el inventario de mercancia se muestre en ceros");
		// Paso 11
		addStep("Actualizar la zona de transferencia a 9999 dentro del campo Transfer Zone y dar clic en OK");
		t.UpdateTransferZone();
		t.close_RMS();
		t.CloseBrowser();

		// Paso 12
		addStep("Accesar a la Base de Datos de RMS y Validar en la tabla Store que se hayan actualizado los campos STORE_CLOSE_DATE, STOP_ORDER_DAYS y TRANSFER_ZONE.");

		String tienda = data.get("Store");
		String queryTienda = String.format(Queries_Tiendas.Tbl_Store_Cierre, tienda);
		System.out.println(queryTienda);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, queryTienda);
		testCase.addQueryEvidenceCurrentStep(executeQuery);
		assertEquals(executeQuery.getData(0, "TRANSFER_ZONE"), data.get("TransferZone"));

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_CierredeTiendaenRMS_test";
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_002_CierredeTiendaenRMS";
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
