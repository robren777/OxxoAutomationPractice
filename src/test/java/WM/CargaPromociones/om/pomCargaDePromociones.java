package WM.CargaPromociones.om;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.sikuli.script.Key;

import WM.om.MetodosExtra;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class pomCargaDePromociones {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	WebElement a;
	HashMap<String, String> data;
	SikuliUtil sk = new SikuliUtil();
	int numeroEvidencia = 0;
	int months = 1;
	
	/************************LOCATORS*********************/
	By userR = By.xpath("//*[@id='pt1:r1:0:login_it1::content']");
	By passR = By.xpath("//*[@id='pt1:r1:0:login_it2::content']");
	By BtnLogin = By.xpath("//*[@id='pt1:r1:0:login_cb1']");
	
	By BtnTasks = By.xpath("//a[@title='Tasks']| //a[@title='Tareas']");
	By BtnBuscarPromociones = By.xpath("//a[@title='Tasks']| //a[@title='Tareas']");
	By btnRPMTask = By.xpath("//*[text()='RPM Tasks' or text()='Tareas RPM']");
	By BtnRPMBuscarPromociones = By.xpath("//a[@title='Tasks']| //a[@title='Tareas']");
	By btnFileUpload = By.xpath("//*[text()='File Upload' or text()='Carga de Archivos']");
	By btnMassivePromotions = By.xpath("//*[text()='Massive Promotions Change' or text()='Cambio Carga Masiva']");
	By btnPromotions = By.xpath("//*[text()='Promotions' or text()='Promociones']");
	By btnFilterPromotions = By.xpath("//*[text()='Filter Promotions' or text()='Filtrar Promociones']");
	By btnUpload = By.xpath("//*[text()='Upload' or text()='Cargar']");
	By btnUpload2 = By.xpath("(//*[text()='Upload' or text()='Cargar'])[2]");
	By btnUploadOK = By.xpath("//*[@id=\"pt1:contentAreaReg:0:tabrg1:2:pb5\"]/a/span[text()='Aceptar' or text()='OK']");
	By btnUploadOK2 = By.xpath("//*[@id=\"pt1:contentAreaReg:0:tabrg3:2:b6\"]/a/span[text()='Aceptar' or text()='OK']");
	By btnUploadOK3 = By.xpath("//*[@id=\"pt1:contentAreaReg:0:tabrg1:2:b6\"]/a/span[text()='Aceptar' or text()='OK']");
	By btnReviewStatus = By.xpath("//*[text()='Review Status' or text()='Revisar Estado']");
	By btnReviewStatus2 = By.xpath("(//*[text()='Review Status' or text()='Revisar Estado'])[2]");
	By btnViewIssues = By.xpath("//span[text()='View Issues' or text()='Ver Problemas']");

	/*******************VARIABLES PARA METODOS*******************/
	String currentDateAsString;
	
	public pomCargaDePromociones(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}
	
	/*
	 * MÃ©todo para iniciar sesiÃ³n, ingresa usuario y contraseÃ±a eN RMS16
	 * 
	 * @param dato Nombre de usuario
	 * 
	 * @param dato2 ContraseÃ±a
	 * 
	 * @author Baltazar Lugo
	 */
	public void logginRMS16(String user, String pass) {
		try {
			System.out.println(user);
			System.out.println(pass);
			u.waitWebElement(userR, 180, 1);
			u.waitForLoadPage();

			u.highLight(userR);
			u.sendKeys(userR, user);

			u.highLight(passR);
			u.sendKeys(passR, pass);

			u.highLight(BtnLogin);
			u.click(BtnLogin);
			testCase.addScreenShotCurrentStep(u, "NewLogin");
			u.waitForLoadPage();

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("No ha iniciado sesion correctamente");
		}
	}
	
	/**
	 * 
	 * Metodo para darle click en task rms16
	 * 
	 * @author Baltazar Lugo
	 */
	public void BtnTasks() {
		try {

			u.waitWebElement(BtnTasks, 200, 1);
			u.highLight(BtnTasks);
			testCase.addScreenShotCurrentStep(u, "task");
			u.click(BtnTasks);
		} catch (Exception e) {
			System.out.println("->" + e.getMessage());
			throw new AssertionError("Fallo al dar click en tasks");
		}
	}
	
	public void BuscarPromotions() {
		try {

			u.waitWebElement(BtnBuscarPromociones, 200, 1);
			u.highLight(BtnBuscarPromociones);
			testCase.addScreenShotCurrentStep(u, "Buscar Promociones");
			u.click(BtnBuscarPromociones);
		} catch (Exception e) {
			System.out.println("->" + e.getMessage());
			throw new AssertionError("Fallo al dar click en Buscar Promociones");
		}
	}
	
	/**
	 * Metodo para dar click en la opcion RPMTasks en tasks>RPM Tasks
	 * 
	 * @author 1000068915
	 */
	public void clicRPMTask() {
		u.waitForLoadPage();
		u.waitWebElement(btnRPMTask, 90, 2);
		u.highLight(btnRPMTask);
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "optRPMTasks");
		u.click(btnRPMTask);
	}
	
	public void clicRPMBuscarPromotion() {
		u.waitForLoadPage();
		u.waitWebElement(BtnRPMBuscarPromociones, 90, 2);
		u.highLight(BtnRPMBuscarPromociones);
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "RPMBuscarPromociones");
		u.click(BtnRPMBuscarPromociones);
	}
	
	/**
	 * Metodo para dar click en la opcion FileUpload en tasks>RPM Tasks>File Upload
	 * 
	 * @author 1000068915
	 */
	public void clicFileUpload() {
		u.waitForLoadPage();
		u.waitWebElement(btnFileUpload, 90, 2);
		u.highLight(btnFileUpload);
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "optFileUpload");
		u.click(btnFileUpload);
	}
	
	public void clicMassivePromotions() {
		u.waitForLoadPage();
		u.waitWebElement(btnMassivePromotions, 90, 2);
		u.highLight(btnMassivePromotions);
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "optMassivePromotions");
		u.click(btnMassivePromotions);
	}
	
	/**
	 * Metodo para dar click en la opcion Promotion en tasks>RPM Tasks>File
	 * Upload>Promotion
	 * 
	 * @author 1000068915
	 */
	public void clicPromotion() {
		u.waitForLoadPage();
		u.waitWebElement(btnPromotions, 90, 2);
		u.highLight(btnPromotions);
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "optPromotion");
		u.click(btnPromotions);
	}

	public void clicFilterPromotions() {
		u.waitForLoadPage();
		u.waitWebElement(btnFilterPromotions, 90, 2);
		u.highLight(btnFilterPromotions);
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "optFilterPromotions");
		u.click(btnFilterPromotions);
	}
	
	
	/**
	 * Metodo para dar click en CARGAR/UPLOAD
	 * 
	 * @author 1000068915
	 */
	public void clicBtnUpload() {

		try {
			u.waitForLoadPage();
			u.waitWebElement(btnUpload, 90, 2);
			u.highLight(btnUpload);
			u.click(btnUpload);
			u.hardWait(2);
			testCase.addScreenShotCurrentStep(u, "btnUpload");
			u.hardWait(2);
			u.click(btnUploadOK);
		} catch (Exception e) {

			throw new AssertionError("Fallo al realizar Click en Btn Upload" + e.getMessage());

		}
	}
	
	public void clicBtnUpload2() {

		try {
			u.waitForLoadPage();
			u.waitWebElement(btnUpload2, 90, 2);
			u.highLight(btnUpload2);
			u.click(btnUpload2);
			u.hardWait(2);
			testCase.addScreenShotCurrentStep(u, "btnUpload2");
			u.hardWait(5);
			u.click(btnUploadOK2);
		} catch (Exception e) {

			throw new AssertionError("Fallo al realizar Click en Btn Upload" + e.getMessage());

		}
	}
	
	public void clicBtnUpload3() {

		try {
			u.waitForLoadPage();
			u.waitWebElement(btnUpload, 90, 2);
			u.highLight(btnUpload);
			u.click(btnUpload);
			u.hardWait(2);
			testCase.addScreenShotCurrentStep(u, "btnUpload");
			u.hardWait(5);
			u.click(btnUploadOK3);
		} catch (Exception e) {

			throw new AssertionError("Fallo al realizar Click en Btn Upload" + e.getMessage());

		}
	}
	
	/**
	 * Metodo para dar click en Review Status
	 * 
	 * @author 1000068915
	 */
	public String clicReviewStatus(String file) {
		u.waitForLoadPage();
		u.waitWebElement(btnReviewStatus, 90, 2);
		u.highLight(btnReviewStatus);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "btnReviewStatus");
		u.click(btnReviewStatus);
		u.waitWebElement(btnViewIssues, 90, 2);

		By txtboxFileName = By.xpath(
				"(//th/span/input[starts-with(@id, 'pt1:contentAreaReg:0:tabrg2:2:ap1:at1:_ATp:t1:_afrFltrMdlc') and substring(@id, string-length(@id) - string-length('::content') + 1) = '::content'])[2]");

		By txtboxDate = By.xpath(
				"//th/span/input[starts-with(@id, 'pt1:contentAreaReg:0:tabrg') and substring(@id, string-length(@id) - string-length(':2:ap1:at1:_ATp:t1:id1::content') + 1) = ':2:ap1:at1:_ATp:t1:id1::content']");
		
		u.waitWebElement(txtboxFileName, 90, 2);
		u.clear(txtboxFileName);
		u.highLight(txtboxFileName);
		u.sendKeys(txtboxFileName, file);

		String formeteDate = u.getDriver().findElement(txtboxDate).getAttribute("placeholder");
		// Format the current date as needed
		if (formeteDate.equals("m/d/yy")) {
			formeteDate = "M/d/yy";
		} else if (formeteDate.equals("m/d/yyyy")) {
			formeteDate = "M/d/yyyy";
		}

		System.out.println(formeteDate);

		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formeteDate);
		currentDateAsString = currentDate.format(formatter);
		System.out.println("Current Date: " + currentDateAsString);

		file = file + ".ods";
		System.out.println("FileName: " + file);

		u.waitWebElement(txtboxDate, 90, 2);
		u.highLight(txtboxDate);
		u.clear(txtboxDate);
		u.sendKeys(txtboxDate, currentDateAsString);
		
		sk.screen.type(Key.ENTER);
		u.hardWait(5);
		testCase.addTextEvidenceCurrentStep("");

		By fileNameReview = By.xpath(
				"(//td[starts-with(@id, 'pt1:contentAreaReg:0:tabrg') and substring(@id, string-length(@id) - string-length(':c3') + 1) = ':c3']/span)[1]");

		By statusReview = By.xpath(
				"(//td[starts-with(@id, 'pt1:contentAreaReg:0:tabrg') and substring(@id, string-length(@id) - string-length(':c8') + 1) = ':c8']/span)[1]");

		By dateReview = By.xpath(
				"(//td[starts-with(@id, 'pt1:contentAreaReg:0:tabrg') and substring(@id, string-length(@id) - string-length(':c7') + 1) = ':c7']/span)[1]");

		String status = "";

		for (int i = 0; i <= 100; i++) {
			String lblDate = u.getText(dateReview);
			String lblFileName = u.getText(fileNameReview);
			System.out.println(lblDate);
			System.out.println(lblFileName);

			if (lblFileName.equals(file) && lblDate.equals(currentDateAsString)) {
				u.highLight(fileNameReview);
				u.highLight(dateReview);
				System.out.println("Si se encuentra el registro");
				status = u.getText(statusReview);
				break;
			} else {
				u.hardWait(1);
			}
		}

		u.highLight(statusReview);

		System.out.println(status);

		testCase.addScreenShotCurrentStep(u, "ScreenReviewStatus");

		return status;

	}
	
	public String clicReviewStatus2(String file) {
		u.waitForLoadPage();
		u.waitWebElement(btnReviewStatus2, 90, 2);
		u.highLight(btnReviewStatus2);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "btnReviewStatus2");
		u.click(btnReviewStatus2);
		u.waitWebElement(btnViewIssues, 90, 2);

		By txtboxFileName = By.xpath(
				"//th/span/input[starts-with(@id, 'pt1:contentAreaReg:0:tabrg4:2:ap1:at1:_ATp:t1:_afrFltrMdlc3::content') and substring(@id, string-length(@id) - string-length('::content') + 1) = '::content']");

		By txtboxDate = By.xpath(
				"//th/span/input[starts-with(@id, 'pt1:contentAreaReg:0:tabrg') and substring(@id, string-length(@id) - string-length(':2:ap1:at1:_ATp:t1:id1::content') + 1) = ':2:ap1:at1:_ATp:t1:id1::content']");

		u.waitWebElement(txtboxFileName, 90, 2);
		u.clear(txtboxFileName);
		u.highLight(txtboxFileName);
		u.sendKeys(txtboxFileName, file);

		String formeteDate = u.getDriver().findElement(txtboxDate).getAttribute("placeholder");
		// Format the current date as needed
		if (formeteDate.equals("m/d/yy")) {
			formeteDate = "M/d/yy";
		} else if (formeteDate.equals("m/d/yyyy")) {
			formeteDate = "M/d/yyyy";
		}

		System.out.println(formeteDate);

		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formeteDate);
		currentDateAsString = currentDate.format(formatter);
		System.out.println("Current Date: " + currentDateAsString);

		file = file + ".ods";
		System.out.println("FileName: " + file);

		u.waitWebElement(txtboxDate, 90, 2);
		u.clear(txtboxDate);
		u.highLight(txtboxDate);
		u.sendKeys(txtboxDate, currentDateAsString);

		sk.screen.type(Key.ENTER);
		u.hardWait(5);

		By fileNameReview = By.xpath(
				"(//td[starts-with(@id, 'pt1:contentAreaReg:0:tabrg') and substring(@id, string-length(@id) - string-length(':c3') + 1) = ':c3']/span)[1]");

		By statusReview = By.xpath(
				"(//td[starts-with(@id, 'pt1:contentAreaReg:0:tabrg') and substring(@id, string-length(@id) - string-length(':c8') + 1) = ':c8']/span)[1]");

		By dateReview = By.xpath(
				"(//td[starts-with(@id, 'pt1:contentAreaReg:0:tabrg') and substring(@id, string-length(@id) - string-length(':c7') + 1) = ':c7']/span)[1]");

		String status = "";

		for (int i = 0; i <= 100; i++) {
			String lblDate = u.getText(dateReview);
			String lblFileName = u.getText(fileNameReview);
			System.out.println(lblDate);
			System.out.println(lblFileName);

			if (lblFileName.equals(file) && lblDate.equals(currentDateAsString)) {
				u.highLight(fileNameReview);
				u.highLight(dateReview);
				System.out.println("Si se encuentra el registro");
				status = u.getText(statusReview);
				break;
			} else {
				u.hardWait(1);
			}
		}

		u.highLight(statusReview);

		System.out.println(status);

		testCase.addScreenShotCurrentStep(u, "ScreenReviewStatus");

		return status;

	}
	
	public void cerrarReviewIssues() {
		u.waitForLoadPage();
		
		By cerrarReview = By.xpath("//*[text()='Cancel' or text()='Cancelar']");
		
		u.highLight(cerrarReview);
		u.click(cerrarReview);
	}
	
	/**
	 * Metodo para dar click en Review Status
	 * 
	 * @author 1000068915
	 */
	public boolean clicViewIssues() {
		try {
			u.waitForLoadPage();
			u.waitWebElement(btnViewIssues, 90, 2);
			u.highLight(btnViewIssues);
			u.hardWait(2);
			u.click(btnViewIssues);

			By tableViewIssues = By.xpath(
					"//div[starts-with(@id, 'pt1:contentAreaReg:0:tabrg') and substring(@id, string-length(@id) - string-length(':2:pc1') + 1) = ':2:pc1']");
			u.waitWebElement(tableViewIssues, 90, 2);
			testCase.addScreenShotCurrentStep(u, "btnReviewStatus");
			u.hardWait(2);

			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}