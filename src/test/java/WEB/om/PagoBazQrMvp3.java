package WEB.om;

import java.io.File;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.sikuli.script.Pattern;

import modelo.TestCase;
//import util.winium.WiniumUtil;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCION: Clase de utileria para manejo de Corresponsalias menu
 * Financieros.
 * 
 * @author Roberto Renteria
 * @date 18/Julio/2024
 */
public class PagoBazQrMvp3 {

	// WiniumUtil u1;
	SeleniumUtil u;
	TestCase testCase;
	SikuliUtil sk;
	String pathImg;
	File FolderPath;
	// SeleniumUtil u2;

	public PagoBazQrMvp3(SeleniumUtil u, TestCase testCase) {// SeleniumUtil u = new SeleniumUtil(new ChromeTest(),
																// true);
		this.u = u;
		this.testCase = testCase;

		// this.xp = new XposUtils(u,testCase);
		this.sk = new SikuliUtil();
		this.pathImg = sk.PatternPath("ControlM");// cambiar la ruta de la carpeta de skuLI
		this.FolderPath = new File(pathImg);

	}

	// Objects
	By btnMenuFinancieros = By.name("Financieros");
	By btndepositocorresponsalias = By.id("btnMenu");
	By btndepositotarjeta = By.id("btnMenu");
	By txtCajeros = By.name("Oxxo.Xpos.Commons.Security.DTO.Info.OperatorInfo");
	By txtPassCajero = By.id("pwdConfirm");
	By txtCajerosName = By.className("TextBlock");
	By btnAceptarCajeros = By.name("Aceptar");
	By txtNumTarjeta = By.id("txtMaskedCardNumber");
	By txtConfNumTarjeta = By.id("txtMaskedConfirmCardNumber");
	By txtMontoDep = By.id("txtCaptureAmmount");
	By txtConfMontoDep = By.id("txtConfirmAmmount");
	By btnsicorresp = By.name("SI");
	By itemListView = By.id("lvTransactionJournalLine");
	By itemListValue = By.xpath(
			"//*[@Name='Oxxo.Xpos.Modules.Sale.Presentation.Model.TxnJournalLine' and @ClassName='ListBoxItem']");
	By txtItemJournalList = By.className("TextBlock");
	By btnNoDeseoAcumularPuntos = By.id("btnExit");
	By btnTotalizar = By.id("btnTotalizar");
	By btnPagar = By.id("btn1");
	By aviso = By.className("PopUpMessageWindow");
	By msjAvisoBy = By.className("TextBlock");
	By btnAceptar = By.id("acceptButton");
	By msjReimprimirticket = By.name("¿Reimprimir Comp. en línea?");
	By btnNo = By.id("Btn2");
	By aviso_lb = By.className("PopUpMessageWindow");
	By mensajeexitoso_lb = By.className("TextBlock");
	By aceptar_btn = By.id("acceptButton");
	By aceptar_btn2 = By.id("accpetButton");
	By no_btn = By.id("Btn2");
	By siimprimirreimpresioncorres_btn = By.name("Sí");
	By txtJournal = By.xpath("//*[@AutomationId='txtCode' and @ClassName='TextBox' and @IsEnabled='True']");
	By subtotalizar_btn = By.id("btnTotalizar");
	By tiempoaireno_btn = By.xpath("//*[@ClassName = 'Button' and @AutomationId = 'Btn2']");
	By identificarme_btn = By.name("IDENTIFICARME");
	By nodeseoacumularpuntos_btn = By.id("btnExit");
	By redondeono_btn = By.xpath("//*[@ClassName = 'Button' and @AutomationId = 'btnNo']");
	By otras_btn = By.name("OTRAS");
	By pagoQR_btn = By.name("Pago con QR");
	By spin_btn = By.xpath("(//*[@ClassName = 'Image' and @AutomationId = 'btnImage'])[2]");

	// Control M prd
	By BtnRerun = By.xpath("//button[contains(@class, 'type-button') and .//span[text()='Rerun']]");
	By BtnRunNow = By.xpath("//button[contains(@class, 'type-button') and .//span[text()='Run Now']]"); // RunNow
	By BtnList = By.xpath("//span[contains(text(),'List')]"); // BtnList
	By BtnMonitoring = By.xpath("//a[normalize-space()='Monitoring']");
	By BtnAddViewpoint = By.xpath("//span[normalize-space()='Add Viewpoint']");
	By InputFolderName = By.xpath(
			"/html[1]/body[1]/modal-container[1]/div[2]/div[1]/viewpoint-new-editor[1]/ctm-common-modal[1]/div[1]/div[2]/div[2]/div[2]/div[2]/div[1]/viewpoint-editor-filter-wrapper[1]/section[1]/div[2]/div[1]/viewpoint-editor-filter-fields-sets[1]/ctm-common-more-expander[1]/div[2]/section[1]/div[1]/viewpoint-editor-filter-field-set[1]/div[1]/div[1]/ctm-common-more-expander[1]/div[2]/div[1]/div[1]/div[1]/viewpoint-editor-filter-field-wrapper[1]/div[2]/viewpoint-editor-filter-field-value[1]/div[1]/ctm-common-tag-input[1]/ng-select[1]/div[1]/div[1]/div[2]/input[1]");
	By JobName = By.xpath(
			"/html[1]/body[1]/modal-container[1]/div[2]/div[1]/viewpoint-new-editor[1]/ctm-common-modal[1]/div[1]/div[2]/div[2]/div[2]/div[2]/div[1]/viewpoint-editor-filter-wrapper[1]/section[1]/div[2]/div[1]/viewpoint-editor-filter-fields-sets[1]/ctm-common-more-expander[1]/div[2]/section[1]/div[1]/viewpoint-editor-filter-field-set[1]/div[1]/div[1]/ctm-common-more-expander[1]/div[2]/div[1]/div[1]/div[5]/viewpoint-editor-filter-field-wrapper[1]/div[2]/viewpoint-editor-filter-field-value[1]/div[1]/input[1]");
	By BtnOpen = By.xpath("//button[@class='common-modal-confirm btn-size-lg theme-primary btn-spot-normal']");
	By Job = By.xpath(
			"/html/body/app-app-main/div/div/div/monitoring-domain/div[2]/ng-component/div/div/div[2]/as-split/as-split-area[2]");

	/**
	 * Metodo Sub - METODO PARA CORRER JOB
	 * 
	 * @param FolderName nombre de la carpeta del job, NameJob nombre del job
	 * @author antonio.martinez
	 * @throws Exception
	 * @date 16/12/2024
	 */
	public void buscarJobPrd(String FolderName, String NameJob) throws Exception {
		try {
			// --- Monitoring ---
			u.highLight(BtnMonitoring);
			if (u.getDriver().findElement(BtnMonitoring) != null) {
				u.click(BtnMonitoring);
			} else {
				throw new Exception("No se encontró el botón 'Monitoring'");
			}

			// --- Add Viewpoint ---
			u.highLight(BtnAddViewpoint);
			if (u.getDriver().findElement(BtnAddViewpoint) != null) {
				u.click(BtnAddViewpoint);
			} else {
				throw new Exception("No se encontro el boton 'Add Viewpoint'");
			}

			u.waitForLoadPage();

			// --- Input Folder Name ---
			u.highLight(InputFolderName);
			if (u.getDriver().findElement(InputFolderName) != null) {
				u.click(InputFolderName);
				u.sendKeys(InputFolderName, FolderName);
			} else {
				throw new Exception("No se encontro el campo 'Input Folder Name'");
			}

			// --- Job Name ---
			u.highLight(JobName);
			if (u.getDriver().findElement(JobName) != null) {
				u.click(JobName);
				u.sendKeys(JobName, NameJob);
			} else {
				throw new Exception("No se encontro el campo 'Job Name'");
			}

			// --- Botón Open ---
			u.highLight(BtnOpen);
			if (u.getDriver().findElement(BtnOpen) != null) {
				u.click(BtnOpen);
			} else {
				throw new Exception("No se encontro el boton 'Open'");
			}

			// --- Validar imagen del job ---
			checkTwoPatternNoEvidenceClick("JobCap", "JobCapGris");

			// --- Botón Rerun ---
			u.highLight(BtnRerun);
			testCase.addScreenShotCurrentStep(u, "inputs");

			if (u.getDriver().findElement(BtnRerun) != null) {
				u.click(BtnRerun);
				u.waitForLoadPage();
			} else {
				throw new Exception("No se encontro el boton 'BtnRerun'");
			}

			validatePatternAndClick("OkJob");

			// --- Botón List ---
			u.highLight(BtnList);
			testCase.addScreenShotCurrentStep(u, "inputs");

			if (u.getDriver().findElement(BtnList) != null) {
				u.click(BtnList);
				u.waitForLoadPage();
			} else {
				throw new Exception("No se encontro el boton 'Rerun'");
			}

			validatePattern("EndOk");

		} catch (Exception e) {
			// Mensaje general de error con traza detallada
			testCase.addTextEvidenceCurrentStep("ERROR: " + e.getMessage());
			System.out.println("Ocurrió un error al buscar el job: " + e.getMessage());
			throw e; // Lanza la excepción para que se vea en el log del test
		}
	}

	/**
	 * Metodo donde se valida que una imagen cualquiera exista y se presiona
	 * 
	 * @param patternName nombre de patron generalizado
	 * @author monica.soto
	 * @throws Exception
	 * @date 16/12/2024
	 */
	public void validatePatternAndClick(String patternName) throws Exception {
		try {
			u.hardWait(5);

			Pattern pattern = new Pattern(FolderPath + "\\" + patternName + ".png").similar((float) 0.70);
			if (sk.screen.exists(pattern, 20) != null) {
				testCase.addTextEvidenceCurrentStep("Se presiona el boton con imagen: " + patternName);
				testCase.addScreenShotCurrentStep(patternName);
				sk.screen.click(pattern);
				u.hardWait(5);
			} else {
				testCase.addTextEvidenceCurrentStep("No se encontro la imagen: " + patternName);
				throw new NoSuchElementException("No se detecto la imagen: " + patternName);
			}
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error en validatePatternAndClick: " + e.getMessage());
			throw new Exception("Error en validatePatternAndClick: " + e.getMessage());
		}
	}

	/**
	 * Metodo donde se valida que una imagen cualquiera exista y se presiona, sin
	 * dar evidencia, si no existe la primera imagen valida la segunda
	 *
	 * @param patternName nombre de patron generalizado patternSecondName nombre de
	 *                    patron generalizado
	 * @author monica.soto
	 * @throws Exception
	 * @date 25/04/2025
	 */
	public void checkTwoPatternNoEvidenceClick(String patternName, String patternSecondName) throws Exception {

		try {
			Pattern pattern = new Pattern(FolderPath + "\\" + patternName + ".png").similar((float) .70);
			Pattern patternSecond = new Pattern(FolderPath + "\\" + patternSecondName + ".png").similar((float) .70);

			if (sk.screen.exists(pattern, 20) != null) {
				sk.screen.click(pattern);
				u.hardWait(5);

			} else if (sk.screen.exists(patternSecond, 20) != null) {
				sk.screen.click(patternSecond);
				u.hardWait(5);
			} else {
				// cerrarApplet();
				throw new NoSuchElementException("No se detectó " + patternName);
			}
			throw new NoSuchElementException("");
		} catch (Exception e) {

			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			// throw new Exception("Error: " + e.getMessage());
		}
	}

	/**
	 * Metodo donde se valida que una imagen cualquiera exista
	 *
	 * @param patternName nombre de patron generalizado
	 * @author monica.soto
	 * @throws Exception
	 * @date 16/12/2024
	 */
	public void validatePattern(String patternName) throws Exception {
		u.hardWait(12);
		try {
			Pattern pattern = new Pattern(FolderPath + "\\" + patternName + ".png").similar((float) .70);
			u.hardWait(5);
			if (sk.screen.exists(pattern, 20) != null) {
				testCase.addTextEvidenceCurrentStep("Si existe.");
				testCase.addScreenShotCurrentStep(patternName);
			} else {

				throw new NoSuchElementException("No se detectó " + patternName);
			}
		} catch (Exception e) {

			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}

}
