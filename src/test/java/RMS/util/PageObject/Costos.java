package RMS.util.PageObject;

import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.sikuli.script.ScreenImage;

import RMS.util.ExecuteQuery;
import RMS.util.GlobalVariables;
import config.Constants;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class Costos {

//	private static final TestCase TestCase = null;
	static SQLUtil dbRms;
	static Robot r;
	static SeleniumUtil u;
	static SikuliUtil sk;
	static String User;
	static String Pwd;
	static ArrayList<String> ListImage;
	static ArrayList<Pattern> patterns;
	static HashMap<String, Pattern> ImgPattern;
	static File Fol;
	static Pattern pathimg;
	public static String cb;
	static String status;
	static Boolean st;
	static HashMap<String, String> data;
	static ControlM j;
	static TestCase testCase;
	static JSONObject jobs;
	static String formatDate;
	static String df;
	int numeroEvidencia = 0;

	private String urlControlM = GlobalVariables.CtrlM_URL;
	private String userControlM = GlobalVariables.CtrlM_User;
	private String passControlM = GlobalVariables.CtrlM_Pss;

	By inputPass = By.id("login-user-password");
	By txtLoginControlM = By.xpath("//*[@id=\"login-user-name\"]");
	ExecuteQuery e = new ExecuteQuery(testCase);

	@SuppressWarnings("static-access")
	public Costos(SikuliUtil sk, SeleniumUtil u, String cb, Robot r, Pattern pathimg, ArrayList<String> ListImage,
			HashMap<String, Pattern> ImgPattern, ArrayList<Pattern> patterns, File Fol, String status, SQLUtil dbRms,
			Boolean st, HashMap<String, String> data, TestCase testcase) throws SQLException {
		this.u = u;
		this.sk = sk;
		this.cb = cb;
		this.r = r;
		this.pathimg = pathimg;
		this.ListImage = ListImage;
		this.ImgPattern = ImgPattern;
		this.patterns = patterns;
		this.Fol = Fol;
		this.status = status;
		this.dbRms = dbRms;
		this.st = st;
		this.data = data;
		this.testCase = testcase;
	}

	/**
	 * @author 72843
	 * @param fol
	 * @return ImgPatter
	 * @Comments genera la data de las imagenes a utilizar en la automatizaci�n
	 * 
	 */
	private HashMap<String, Pattern> GetPatternsImg(File fol) {
		File[] files = Fol.listFiles();
		for (File s : files) {
			patterns.add(new Pattern(s.getAbsolutePath()).similar((float) .95));
//			patterns.add( new Pattern(s.getAbsolutePath()).exact());			
		}
		for (Pattern p : patterns) {
			String fname = p.getFilename().toString();
			fname = fname.replace("\\", "-");
			fname = fname.replace(".png", "");
			String[] afname = fname.split("-");
			ImgPattern.put(afname[afname.length - 1], p);
		}

		return ImgPattern;
	}

	/**
	 * @author 1000068915
	 * @Comments abre el navegdaor que se este utiilizando
	 */
	public void OpenBrowser() {
		try {
			System.out.println("Driver: " + u.getDriver());
			System.out.println(data.get("rms_url"));
			u.get(data.get("rms_url"));
			screenshot2();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	/**
	 * @author 1000068915
	 * @Comments cierra el navegdaor que se este utiilizando
	 */
	public void CloseBrowser() {
		try {
			u.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @author 72843
	 * @throws InterruptedException
	 * @Comments realiza login en RMS
	 * 
	 */
	public void loginRMS() throws FindFailed, InterruptedException {
		try {
			ImgPattern = GetPatternsImg(Fol);
			OpenBrowser();

			waitImage2(100, "FEMSA_Label");
			sk.screen.wait(ImgPattern.get("btn_max"), 15).click();
			u.hardWait(2);
			waitImage2(100, "login_screen");
			u.hardWait(2);
			sk.screen.wait(ImgPattern.get("user"), 10).highlight("red").right(20).click();
			u.hardWait(1);
			sk.screen.type(data.get("rms_user"));
			u.hardWait(2);
			sk.screen.wait(ImgPattern.get("password"), 10).highlight("red").right(20).click();
			u.hardWait(1);
			sk.screen.type(PasswordUtil.decryptPassword(data.get("rms_ps")) + Key.TAB + Key.TAB);
			r.delay(1000);
			screenshot2();
			r.delay(1000);
			sk.screen.type(Key.ENTER);
			Screen.highlightAllOff();
			waitImage2(20, "menu");
			sk.screen.wait(ImgPattern.get("btn_max"), 15).click();

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al realizar el Login en RMS");
		}
	}

	/**
	 * @author 1000068915
	 * @param navega a la opcion Pricing del menu y busca Retailcos
	 */
	public void navPricingRetailCost() throws FindFailed {
		try {
			waitImage(30, "pricing");
			sk.screen.wait(ImgPattern.get("pricing"), 1).highlight("red").doubleClick();

			waitImage(30, "retailCostChange");
			sk.screen.wait(ImgPattern.get("retailCostChange"), 1).highlight("red").doubleClick();
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			Screen.highlightAllOff();
			waitImage(25, "retail_action");
			u.hardWait(1);
			screenshot2();

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al dirigirse a la pantalla Retail Cost");
		}
	}

	/**
	 * @author 1000068915
	 * @comment Selecciona la opcion New en la pantalla Retail Cost Change
	 */
	public void fillRetail() throws FindFailed {
		try {
			waitImage(20, "retail_action");
			sk.screen.wait(ImgPattern.get("retail_action"), 20).highlight("red").right(20).click();
			r.delay(1000);
			sk.screen.wait(ImgPattern.get(data.get("action")), 20).click();
			r.delay(1000);

			sk.screen.wait(ImgPattern.get("retail_origin"), 20).highlight("red").right(20).click();
			r.delay(1000);
			sk.screen.wait(ImgPattern.get(data.get("origin")), 20).click();
			r.delay(1000);

			sk.screen.type(Key.TAB);
			r.delay(1000);

			screenshot2();
			r.delay(1000);
			Screen.highlightAllOff();

			sk.screen.type(Key.ENTER);
			waitImage(20, "retail_costChange");

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al llenar la pantalla de Reatil");
		}
	}

	String costChangeId = "";

	/**
	 * @author 1000068915
	 * @comment Llena la pantalla Retail Cost Change en base a la opcion New
	 *          seleccionada en la pantalla anterior
	 */
	public boolean fillRetailCostChange() throws FindFailed {
		try {
			waitImage(20, "retail_costChange");
			sk.screen.wait(ImgPattern.get("retail_costChange"), 20).right(20).click();
			r.delay(1000);
			sk.screen.keyDown(Key.CTRL);
			sk.screen.keyDown("c");
			sk.screen.keyUp("c");
			sk.screen.keyUp(Key.CTRL);
			r.delay(3000);

			costChangeId = App.getClipboard();

			System.out.println(costChangeId);

			sk.screen.type(Key.TAB);
			r.delay(1000);

			sk.screen.type(data.get("costChange"));
			r.delay(1000);
			sk.screen.type(Key.TAB);

			sk.screen.type(data.get("supplier"));
			r.delay(1000);
			sk.screen.type(Key.TAB);

			sk.screen.type(data.get("item"));
			r.delay(1000);
			sk.screen.type(Key.TAB);

			sk.screen.type(data.get("country"));
			r.delay(1000);
			sk.screen.type(Key.TAB);

			sk.screen.type(data.get("reason"));
			r.delay(1000);
			sk.screen.type(Key.TAB);

			LocalDate diaActual = LocalDate.now();
			diaActual = diaActual.plusDays(1);

			DateTimeFormatter formatC = DateTimeFormatter.ofPattern("dd-MMM-yy");
			String diaActualFormatC = diaActual.format(formatC);

			String query = String.format(
					"SELECT * FROM COST_SUSP_SUP_DETAIL_LOC where SUPPLIER = '%s' and ITEM = '%s' and loc = '%s' and COST_CHANGE in (SELECT COST_CHANGE FROM COST_SUSP_SUP_HEAD Where REASON = '%s' and ACTIVE_DATE = '%s')",
					data.get("supplier"), data.get("item"), data.get("location"), data.get("reason"), diaActualFormatC);
			boolean result = e.ejecutarConsulta2(dbRms, query);

			if (result == true) {
				return false;
			}

			DateTimeFormatter formatN = DateTimeFormatter.ofPattern("ddMMyy");
			String diaActualFormat = diaActual.format(formatN);

			System.out.println("dia: " + diaActualFormat);
			sk.screen.type(diaActualFormat);
			r.delay(1000);
			sk.screen.type(Key.TAB);

			screenshot2();
			r.delay(2000);

			sk.screen.type(Key.ENTER);
			waitImage(20, "retail_locations");

			return !result;
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al llenar la pantalla Retail Cost Change");
		}
	}

	public String getCostChange() {
		return costChangeId;
	}

	/**
	 * @author 1000068915
	 * @comment Llena la pantalla Locations
	 */
	public void fillRetailLocations(String cost) throws FindFailed {
		waitImage(20, "retail_locations");

		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("retail_locations"), 20).click();

		waitImage(120, "menuLocations");
		u.hardWait(2);

		String option = data.get("opt_costChange");
		System.out.println(option);

		if (option.equals("Change_in_Percent")) {
			waitImage(120, "Change_in_Amount");
			sk.screen.wait(ImgPattern.get("Change_in_Amount"), 20).click();
			r.delay(2000);
			waitImage(120, option);
			sk.screen.wait(ImgPattern.get(option), 20).click();
			r.delay(2000);
		} else {
			waitImage(120, option);
			sk.screen.wait(ImgPattern.get(option), 20).click();
			r.delay(2000);
		}

		u.hardWait(2);
		sk.screen.type(Key.TAB);
		r.delay(1000);

		if (cost.equals("")) {
			cost = getNewCost(data.get("item"), data.get("supplier"), data.get("location"));
		}

		sk.screen.type(cost);
		r.delay(1000);
		sk.screen.type(Key.TAB);
		r.delay(1000);

		sk.screen.type(data.get("locType"));
		r.delay(1000);
		sk.screen.type(Key.TAB);
		r.delay(2000);

		sk.screen.type(data.get("location"));
		r.delay(1000);
		sk.screen.type(Key.TAB);
		r.delay(1000);

		screenshot2();
		r.delay(2000);

		sk.screen.type(Key.ENTER);
		u.hardWait(3);

		sk.screen.type(Key.ENTER);
		u.hardWait(3);

	}

	/**
	 * @author 1000068915
	 * @comment Llena la pantalla retail New cost
	 */
	public void fillRetailNewCost(String cost) throws FindFailed {
		try {

			if (cost.equals("")) {
				cost = getNewCost(data.get("item"), data.get("supplier"), data.get("location"));
			}

			u.hardWait(1);

			waitImage(10, "retail_newCost");
			sk.screen.wait(ImgPattern.get("retail_newCost"), 20).below(20).click();
			u.hardWait(1);
			sk.screen.type(cost);
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);

			screenshot2();
			u.hardWait(2);

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al llenar el campo New Cost");
		}
	}

	/**
	 * @author 1000068915
	 * @comment Cierra RMS repitiendo el proceso de close 3 veces para cerrar desde
	 *          la pantalla en la que se encuentre
	 */
	public void close_RMS_3() throws FindFailed {

		sk.screen.wait(ImgPattern.get("mn_action"), 5).highlight(1, "red").click();
		sk.screen.wait(ImgPattern.get("smn_close"), 5).highlight(1, "red").click();
		r.delay(2000);
		sk.screen.wait(ImgPattern.get("mn_action"), 5).highlight(1, "red").click();
		sk.screen.wait(ImgPattern.get("smn_close"), 5).highlight(1, "red").click();
		r.delay(2000);
		sk.screen.wait(ImgPattern.get("mn_action"), 5).highlight(1, "red").click();
		sk.screen.wait(ImgPattern.get("smn_close"), 5).highlight(1, "red").click();

	}

	/**
	 * @author 1000068915
	 * @comment Toma un screenshot de la pantalla actual
	 */
	public void screenshot() {
		Region window = App.focusedWindow(); // obtener la ventana con el focus
		ScreenImage image = sk.screen.capture(window); // toma la captura
		String savePath = Constants.BASE_PATH + "\\" + Constants.OUTPUT_IMAGES;
		String filename = "evidence" + numeroEvidencia;
		image.save(savePath, filename);
		String imgPath = Constants.OUTPUT_IMAGES + "\\" + filename + ".png";
		testCase.addScreenShotCurrentStep(imgPath, true);
		numeroEvidencia++;

	}

	/**
	 * Toma capturas de pantalla y las agrega al testCase
	 * 
	 * @author guerrero.vargas
	 */
	public void screenshot2() {
		String filename = "evidence" + numeroEvidencia;
		testCase.addScreenShotCurrentStep(filename);
		numeroEvidencia++;
	}

	/**
	 * @author 1000068915
	 * @comment Va ala barra de opciones y selecciona la opcion Submit
	 */
	public void submit() throws FindFailed {
		// TODO Auto-generated method stub

		r.delay(2000);
		sk.screen.wait(ImgPattern.get("mn_options"), 15).click();
		sk.screen.wait(ImgPattern.get("smn_submit"), 15).click();
		screenshot2();

	}

	/**
	 * @author 1000068915
	 * @comment Va ala barra de opciones y selecciona la opcion Submit y da clic en
	 *          la opcion YES en el cuadro de texto que se genera
	 */
	public void submitPrice() throws FindFailed {
		try {
			waitImage(150, "mn_options");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("mn_options"), 15).click();

			waitImage(50, "submit");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("submit"), 60).click();
			u.hardWait(1);

			waitImage(150, "rpch_btn_yes");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("rpch_btn_yes"), 15).click();
			u.hardWait(1);

			waitImage(50, "statusSubmitted");
			sk.screen.wait(ImgPattern.get("statusSubmitted"), 15).highlight("red");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			Screen.highlightAllOff();
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al llenar seleccionar la opcion Submit");
		}
	}

	/**
	 * @author 1000068915
	 * @comment Va ala barra de opciones y selecciona la opcion Aprove
	 */
	public void approve() throws FindFailed {
		waitImage(50, "mn_options");
		sk.screen.wait(ImgPattern.get("mn_options"), 15).click();
		waitImage(50, "itm_approve");
		sk.screen.wait(ImgPattern.get("itm_approve"), 15).click();
		screenshot2();

	}

	/**
	 * @author 1000068915
	 * @comment Va ala barra de opciones y selecciona la opcion Aprove y da clic en
	 *          la opcion YES en el cuadro de texto que se genera
	 */
	public void approveRetailPrice() throws FindFailed {
		try {

			waitImage(150, "mn_options");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("mn_options"), 80).click();

			waitImage(150, "itm_approve");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("itm_approve"), 100).click();

			waitImage(150, "rpch_btn_yes");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("rpch_btn_yes"), 15).click();

			waitImage(150, "statusApproved");
			sk.screen.wait(ImgPattern.get("statusApproved"), 15).highlight("red");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			Screen.highlightAllOff();

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al llenar seleccionar la opcion Submit");
		}

	}

	public void clicBtnOkU() throws FindFailed {
		try {
			waitImage(50, "cost_btnOKU");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("cost_btnOKU"), 15).click();
			waitImage(80, "retail_action");
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al llenar seleccionar la opcion Submit");
		}
	}

	/**
	 * @author 1000068915
	 * @comments Cierra la pantalla de RMS repitiendo el paso 2 veces
	 */
	public void close_RMS_2() throws FindFailed {
		try {

			waitImage(150, "mn_action");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("mn_action"), 60).highlight(1, "red").click();
			u.hardWait(1);

			waitImage(150, "smn_close");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("smn_close"), 60).highlight(1, "red").click();

			u.hardWait(1);
			waitImage(150, "mn_action");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("mn_action"), 60).highlight(1, "red").click();
			u.hardWait(1);

			waitImage(150, "smn_close");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("smn_close"), 60).highlight(1, "red").click();
			u.hardWait(1);
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al llenar cerrar RDM");
		}
	}

	public void waitImage(int time, String image) {
		for (int i = 0; i < time; i++) {
			try {
				sk.screen.wait(ImgPattern.get(image), 2);
				System.out.println("Se muestra la imagen: " + image);
				// screenshot();
				break;
			} catch (Exception e) {
				if (i % 30 == 0) {
					System.out.println("Esperando imagen " + image + "...");
				}
			}
		}

		try {
			sk.screen.wait(ImgPattern.get(image), 2);
		} catch (Exception e) {
			screenshot();
			throw new AssertionError("No se muestra la imagen " + image);
		}
	}

	public void waitImage2(int time, String image) {
		for (int i = 0; i < time; i++) {
			try {
				sk.screen.wait(ImgPattern.get(image), 2);
				System.out.println("Se muestra la imagen: " + image);
				// screenshot();
				break;
			} catch (Exception e) {
				if (i % 30 == 0) {
					System.out.println("Esperando imagen " + image + "...");
					u.hardWait(1);
				}
			}
		}

		try {
			sk.screen.wait(ImgPattern.get(image), 2);
		} catch (Exception e) {
			// screenshot();
			System.out.println("No se muestra la imagen " + image);
		}
	}

	public void getControlM() {
		try {
			Control_mInicio controlMInicio = new Control_mInicio(u, userControlM, passControlM);
			u.get(urlControlM);

			u.waitWebElement(inputPass, 200, 3);
			u.waitWebElement(txtLoginControlM, 120, 3);
			u.clear(txtLoginControlM);
			controlMInicio.logOn();

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("No se pudo ingresar a control-M");
		}
	}

	public Boolean ejecutarJob(String nombreJob) throws FindFailed, InterruptedException {
		testCase.addTextEvidenceCurrentStep("Ejecuta Job: " + nombreJob);
		JSONObject obj = new JSONObject(getJobInfo(nombreJob));

		ControlM control = new ControlM(u, testCase, obj);
		try {
			control.searchJob();
		} catch (Exception e) {
			By msg = By.cssSelector(
					"body > div.popover.ng-isolate-scope.bottom.start-viewpoints.fade.in.bottom-left > div.popover-inner > div > div > div.rightArea > a");

			u.waitWebElement(msg, 50, 1);
			u.click(msg);

			u.hardWait(2);

			By planning = By.cssSelector("#WcmDomainTabHeader > div > a");
			u.waitWebElement(planning, 120, 1);
			u.clear(planning);
			u.hardWait(2);

			control.searchJob();
		}

		String resultadoEjecucion = control.executeJob();
		System.out.println("Resultado de la ejecucion: " + resultadoEjecucion);
		String output = control.getOutput();
		System.out.println("Output: " + output);
		testCase.addTextEvidenceCurrentStep("Output: " + output);
		control.closeViewpoint();

		if (resultadoEjecucion.equals("Ended OK") || resultadoEjecucion.equals("Wait Condition")) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean ejecutarJobWaitCondition(String nombreJob) throws FindFailed, InterruptedException {
		testCase.addTextEvidenceCurrentStep("Ejecuta Job: " + nombreJob);
		JSONObject obj = new JSONObject(nombreJob);

		ControlM control = new ControlM(u, testCase, obj);
		control.searchJob();

		String resultadoEjecucion = control.executeJob();
		System.out.println("Resultado de la ejecucion: " + resultadoEjecucion);
		String output = control.getOutput();
		System.out.println("Output: " + output);
		testCase.addTextEvidenceCurrentStep("Output: " + output);
		control.closeViewpoint();

		if (resultadoEjecucion.equals("Wait Condition")) {
			return true;
		} else {
			return false;
		}
	}

	public String getJobInfo(String nombreJob) {
		String value = null;
		try {
			FileInputStream file = new FileInputStream(
					new File(System.getProperty("user.dir") + "/Config/Input/DataProviders/RMS/Jobs.xlsx"));
			Workbook workbook = new XSSFWorkbook(file);
			Sheet sheet = workbook.getSheetAt(0); // Suponiendo que los datos están en la primera hoja

			Row headerRow = sheet.getRow(0); // La fila de encabezados
			Row valueRow = sheet.getRow(1); // La fila de valores

			// Buscar la columna con el encabezado "dato4"
			int column = -1; // Iniciar con -1 para representar que no se encontró
			for (Cell cell : headerRow) {
				if (cell.getStringCellValue().trim().equals(nombreJob)) {
					column = cell.getColumnIndex();
					break;
				}
			}

			if (column == -1) {
				System.out.println("La informacion del JOB " + nombreJob + " No ha sido encontrada");
			} else {
				// Obtener el valor de la segunda fila en la columna encontrada
				Cell valueCell = valueRow.getCell(column);
				value = valueCell.getStringCellValue(); // Asumiendo que es un string
				System.out.println("Value is: " + value);
			}

			workbook.close();
			file.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return value;
	}

	public String getNewCost(String item, String supplier, String loc) {
		try {
			testCase.addBoldTextEvidenceCurrentStep(
					"El campo newCost del Data Provider se encuentra vacio, por lo que se le calculara un aumento de 1 al precio actual");
			String query = String.format(
					"select UNIT_COST from item_supp_country_loc where item = '%s' and supplier = '%s' and loc= '%s'",
					item, supplier, loc);

			boolean result = e.ejecutarConsulta2(dbRms, query);
			assertTrue(result, "No se ha encontrado item con el supplier: " + supplier + " y el location: " + loc);

			String cost = e.getData(dbRms, query, 0, "UNIT_COST");
			System.out.println("Precio actual: " + cost);

			Double newCost = Double.parseDouble(cost);
			newCost++;

			cost = Double.toString(newCost);

			System.out.println("Nuevo costo: " + cost);
			testCase.addBoldTextEvidenceCurrentStep("Nuevo costo: " + cost);

			return cost;

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al calcular el aumento de precio");
		}

	}

}
