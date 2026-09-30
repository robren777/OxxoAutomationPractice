package EBS.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

import modelo.TestCase;
import utils.SikuliUtil;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ControlM_HomologacionCxP {

	TestCase testCase;
	SeleniumUtil u;
	HashMap<String, String> data;
	int dato;
	int numeroEvidencia = 0;

	private By inputPass = By.id("login-user-password");
	private By txtLoginControlM = By.xpath("//*[@id=\"login-user-name\"]");

	SikuliUtil sk = new SikuliUtil();
	HashMap<String, org.sikuli.script.Pattern> ImgPattern;
	HashMap<String, Pattern> ImgPatter;

	public ControlM_HomologacionCxP(SeleniumUtil u, TestCase testCase, String ImgPattern) {
		super();
		this.testCase = testCase;
		this.u = u;
		this.ImgPattern = getPatternsImg(ImgPattern);
	}

	public ControlM_HomologacionCxP(SeleniumUtil u, TestCase testCase) {
		super();
		this.testCase = testCase;
		this.u = u;
	}

	/**
	 * Cierra la ventana enviando la combinacion de teclas 'Alt' + 'F4'
	 *
	 * @author JesusH@hexaware.com
	 */
	public void closeWindow() {
		sk.screen.keyDown(Key.ALT);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		sk.screen.keyUp(Key.ALT);
	}

	/**
	 * Espera el mensaje de RUN para iniciar el aplicativode EBS
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * @throws FindFailed 
	 */
	public boolean startEBS() throws FindFailed {
		try {
			waitImage(10, "btn_Keep");
			sk.screen.wait(ImgPattern.get("btn_Keep"), 1).click();
			u.hardWait(1);
			sk.screen.click();

			waitImage(10, "btn_DownloadUnverifiedFile");
			sk.screen.wait(ImgPattern.get("btn_DownloadUnverifiedFile"), 1).click();
			u.hardWait(1);

			waitImage(10, "btn_Download");
			sk.screen.wait(ImgPattern.get("btn_Download"), 1).click();
			u.hardWait(1);

			waitImage(10, "app");
			sk.screen.wait(ImgPattern.get("app"), 1).click();
			u.hardWait(1);

			waitImage(10, "checkboxStart");
			System.out.println("Se encontro el mensaje para iniciar el aplicativo de EBS");
			sk.screen.wait(ImgPattern.get("checkboxStart"), 1).click();
			u.hardWait(2);
			screenshot();
			u.hardWait(1);
			sk.screen.type(Key.ENTER);

			try {
				waitImage(10, "optArchivo");
				sk.screen.wait(ImgPattern.get("agrandar"), 3).click();
				System.out.println("Cargo correctamente EBS");
				testCase.addBoldTextEvidenceCurrentStep("Cargo correctamente EBS");
				screenshot();
				return true;
			} catch (Exception e) {
				return false;
			}
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al cargar el aplicativo de EBS");
		}
	}

	/**
	 * Busca la imagen del boton Submit a New Request y da clic en ella
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 */
	public boolean clicSubmitNewRequest() {
		try {
			waitImage(10, "btn_SubmitNewRequest");
			sk.screen.wait(ImgPattern.get("btn_SubmitNewRequest"), 1).highlight("red");
			screenshot();
			sk.screen.click();
			Screen.highlightAllOff();

			return waitImage(10, "lbl_Name");
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			screenshot();
			return false;
		}
	}

	/**
	 * Llena el apartado de Name en la pantalla para ejecutar concurrentes de EBS
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 */
	public boolean fillNameConcurrente(String concurrente) {
		try {
			waitImage(10, "lbl_Name");
			sk.screen.wait(ImgPattern.get("lbl_Name"), 1).highlight("red").right(30).click();
			screenshot();
			sk.screen.type(concurrente);
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);
			Screen.highlightAllOff();

			return waitImage(10, "btn_OK");
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			screenshot();
			return false;
		}
	}

	/**
	 * Da clic en el boton OK de la pantalla de Parameters
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 */
	public boolean clicBtnOk() {
		try {
			waitImage(10, "btn_OK");
			sk.screen.wait(ImgPattern.get("btn_OK"), 1).highlight("red");
			screenshot();
			sk.screen.click();
			u.hardWait(1);
			Screen.highlightAllOff();

			return waitDisImage(10, "parametersVoid");
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			screenshot();
			return false;
		}
	}

	/**
	 * Busca el boton Submit y da clic
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 */
	public boolean clicBtnSubmit() {
		try {
			waitImage(10, "btn_Submit");
			sk.screen.wait(ImgPattern.get("btn_Submit"), 1).highlight("red");
			screenshot();
			sk.screen.click();
			Screen.highlightAllOff();

			return waitImage(10, "btn_DesicionNo");
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			screenshot();
			return false;
		}
	}

	/**
	 * Obtiene el Id de la ventana emergente
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 */
	public String getRequestId() {
		try {

			waitImage(10, "popUp");
			u.hardWait(2);
			Region popUpRegion = sk.screen.wait(ImgPattern.get("popUp"), 1);

			int extraWidth = 28; // Ancho adicional para incluir el texto de la ventana
			int extraHeight = 40; // Altura adicional para incluir el texto de la ventana
			Region textRegion = popUpRegion.grow(extraWidth, extraHeight).highlight("red");
			u.hardWait(2);
			String popupText = textRegion.text();

			// Mostrar el texto capturado
			System.out.println("Texto de la ventana emergente: " + popupText);

			String regex = "\\(Request ID = (\\d+)\\)";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(popupText);

			String extractedId = "";
			if (matcher.find()) {
				extractedId = matcher.group(1); // Captura el texto del primer grupo de captura
				System.out.println("ID extraido: " + extractedId);
			} else {
				System.out.println("No se encontro un ID en el formato esperado.");
				return null;
			}

			return extractedId;
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			screenshot();
			return null;
		}
	}

	/**
	 * Busca el boton No en la pantalla Desicion y da clic
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 */
	public boolean clicBtnDesicionNo() {
		try {
			waitImage(10, "btn_DesicionNo");
			sk.screen.wait(ImgPattern.get("btn_DesicionNo"), 1).highlight("red");
			screenshot();
			sk.screen.click();
			Screen.highlightAllOff();

			return waitDisImage(10, "btn_DesicionNo");
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			screenshot();
			return false;
		}
	}

	/**
	 * Llena la pantalla de Specific REquest para buscar la ejecucion de un
	 * concurrente en especifico
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 */
	public boolean fillFindSpecificRequest(String id) {
		try {
			waitImage(10, "radio_specificRequest");
			sk.screen.wait(ImgPattern.get("radio_specificRequest"), 1).highlight("red").click();
			u.hardWait(2);

			waitImage(10, "lbl_RequestID");
			sk.screen.wait(ImgPattern.get("lbl_RequestID"), 1).highlight("red").right(30).click();
			u.hardWait(1);
			sk.screen.type(id);
			u.hardWait(1);

			waitImage(10, "btn_Find");
			sk.screen.wait(ImgPattern.get("btn_Find"), 1).highlight("red");
			screenshot();

			Screen.highlightAllOff();

			sk.screen.wait(ImgPattern.get("btn_Find"), 1).click();

			waitImage(15, "RequestSelected");

			for (int i = 0; i < 1000; i++) {
				try {
					sk.screen.wait(ImgPattern.get("statusConcurrente"), 10).highlight("red");
					Screen.highlightAllOff();
					return true;
				} catch (Exception e) {
					waitImage(10, "btn_RefreshData");
					sk.screen.wait(ImgPattern.get("btn_RefreshData"), 1).click();
					u.hardWait(1);
					sk.screen.wait(ImgPattern.get("RequestSelected"), 1).click();
					u.hardWait(5);
				}
			}
			testCase.addBoldTextEvidenceCurrentStep("No se muestra el concurrente con status Completed");
			return false;
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			screenshot();
			return false;
		}
	}

	/**
	 * Selecciona el concurrente ejecutado y da clic en el boton view Log
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 */
	public boolean clicBtnViewLog() {
		try {
			waitImage(10, "RequestSelected");
			sk.screen.wait(ImgPattern.get("RequestSelected"), 1).right(30).click();
			u.hardWait(1);

			waitImage(10, "btn_ViewLog");
			sk.screen.wait(ImgPattern.get("btn_ViewLog"), 1).highlight("red");
			screenshot();

			Screen.highlightAllOff();

			sk.screen.wait(ImgPattern.get("btn_ViewLog"), 1).click();

			waitImage(15, "logGoogleChrome");
			screenshot();
			return waitImage(15, "logGoogleChrome");
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			screenshot();
			return false;
		}
	}

	/**
	 * Busca el boton No en la pantalla Desicion y da clic
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 */
	public boolean clicCloseWindow() {
		try {
			waitImage(10, "closeWindow");
			sk.screen.wait(ImgPattern.get("closeWindow"), 1).click();
			u.hardWait(1);

			waitImage(10, "btn_star");
			sk.screen.wait(ImgPattern.get("btn_star"), 1).hover();

			return waitDisImage(3, "closeWindow");
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			screenshot();
			return false;
		}
	}

	public boolean searchServicio(String service) {

		By searchService = By.xpath("//span[@title = 'Servicio' and text() = '" + service + "']");
		try {

			try {
				u.waitWebElement(searchService, 5, 1);
				u.highLight(searchService);
				return true;

			} catch (Exception ex) {
				System.out.println("Aun no encuentra el item");

				boolean s = false;
				int i = 100;

				do {
					try {
						waitImage(5, "btn_ScrollDown");
						sk.screen.wait(ImgPattern.get("btn_ScrollDown"), 1).above(5).doubleClick();
						u.hardWait(1);
						u.waitWebElement(searchService, 2, 1);
						u.highLight(searchService);
						s = true;

					} catch (Exception e) {
						System.out.println("Aun no encuentra el item");
						i++;
					}

					if (i == 100) {
						s = true;
					}

				} while (s == false);
				return true;
			}
		} catch (Exception e) {
			// logger.error("No se muestra la lista de items del grupo " + e);
			System.out.println("No se muestra la lista de items del grupo " + e);
			return false;
		}

	}

	/**
	 * Hace un remarcado y da clic en el boton Aplicar para completar el proceso de
	 * actualizacion de un Item
	 *
	 * @author 1000068915 - Jorge Hernandez
	 */
	public boolean clicBtnAplicarUpdate() {
		By btnAplicarUpdate = By.xpath("//button[@id = 'btnApply']");
		try {
			u.waitWebElement(btnAplicarUpdate, 20, 1);
			u.highLight(btnAplicarUpdate);
			screenshot();
			u.click(btnAplicarUpdate);

			waitImage(10, "lbl_ItemActualizado");
			sk.screen.wait(ImgPattern.get("lbl_ItemActualizado"), 1).highlight("red");

			screenshot();

			Screen.highlightAllOff();
			return true;
		} catch (Exception e) {
			System.out.println("No se muestra el mensaje  de confirmacion " + e);
			return false;
		}
	}

	/**
	 * Hace un remarcado y da clic en el icono de basura para eliminar items En la
	 * pantalla de items de grupos
	 *
	 * @author 1000068915 - Jorge Hernandez
	 */
	public boolean clicBtnDelete(String service) {
		By btnDeleteItem = By.xpath("//span[@title = 'Servicio' and text() = '" + service
				+ "']/ancestor::tr[1]//a[contains(@id, 'DeleteItem')]");

		try {
			u.waitWebElement(btnDeleteItem, 20, 1);
			u.highLight(btnDeleteItem);
			screenshot();
			u.click(btnDeleteItem);

			waitImage(10, "lbl_ItemEliminado");
			sk.screen.wait(ImgPattern.get("lbl_ItemEliminado"), 1).highlight("red");

			screenshot();

			Screen.highlightAllOff();

			return true;
		} catch (Exception e) {
			System.out.println("No se muestra el mensaje preguntando si queremos eliminar el item " + e);
			return false;
		}
	}

	/**
	 * Hace un remarcado y da clic en el boton OK de la pantalla de confirmacion de
	 * Item Actualizados
	 *
	 * @author 1000068915 - Jorge Hernandez
	 */
	public boolean clicBtnOkUpdate(String group) {
		By viewItems = By.xpath("//a[contains(@onclick, '" + group + "')]");
		try {
			waitImage(10, "btn_OkUpdate");
			sk.screen.wait(ImgPattern.get("btn_OkUpdate"), 1).highlight("red");
			screenshot();
			sk.screen.wait(ImgPattern.get("btn_OkUpdate"), 1).doubleClick();

			Screen.highlightAllOff();
			u.waitWebElement(viewItems, 20, 1);

			return true;
		} catch (Exception e) {
			System.out.println("No se muestra la lista de items del grupo " + e);
			return false;
		}
	}

	/**
	 * Hace un remarcado y da clic en el boton OK de la pantalla de confirmacion de
	 * Item Actualizados
	 *
	 * @author 1000068915 - Jorge Hernandez
	 */
	public boolean clicYesDelete() {
		By txtRecordDeleted = By.xpath("//li[contains(text(), 'Record deleted Successfully')]");
		try {
			waitImage(10, "btn_YesDeleteItem");
			sk.screen.wait(ImgPattern.get("btn_YesDeleteItem"), 1).highlight("red");
			screenshot();
			sk.screen.wait(ImgPattern.get("btn_YesDeleteItem"), 1).doubleClick();

			Screen.highlightAllOff();
			u.waitWebElement(txtRecordDeleted, 20, 1);
			u.highLight(txtRecordDeleted);
			screenshot();

			return true;
		} catch (Exception e) {
			System.out.println("No se muestra el mensaje de confirmacion " + e);
			return false;
		}
	}

	/**
	 * Obtiene la fecha del dia actual con un formato dd-MMM-yyy Por ejemplo:
	 * 14-NOV-2024
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 */
	public String getActualDate() {

		LocalDate currentDate = LocalDate.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

		String date = currentDate.format(formatter);
		System.out.println(date);

		return date;
	}

	/**
	 * Se dirije al sitio de CTRL - M e inicia sesion con el usuario asignado en el
	 * data PRovider
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 */
	public boolean getControlM(String user, String password, String url) {
		try {
			
//			// Verificar si la imagen "site_not_secure" est � presente
//	        if (sk.screen.exists(ImgPatter.get("site_not_secure"), 5) != null) {
//	            // Hacer clic en la imagen "more_info"
//	            sk.screen.wait(ImgPatter.get("more_info"), 5).click();
//	            // Esperar 5 segundos
//	            u.hardWait(5);
//	            // Hacer clic en la imagen "go"
//	            sk.screen.wait(ImgPatter.get("go"), 5).click();
//	        }
	        
			Control_mInicio controlMInicio = new Control_mInicio(u, user, password);
			u.get(url);

			
			u.waitWebElement(inputPass, 200, 3);
			u.waitWebElement(txtLoginControlM, 120, 3);
			u.clear(txtLoginControlM);
			controlMInicio.logOn();
			testCase.addScreenShotCurrentStep("LOGIN");

			return true;

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			testCase.addBoldTextEvidenceCurrentStep("No se pudo ingresar a control-M");
			return false;
		}
	}
	
	public boolean getControlMChrome(String user, String password, String url) {
		try {
			u = new SeleniumUtil(new ChromeTest(), true);
			Control_mInicio controlMInicio = new Control_mInicio(u, user, password);
			u.get(url);

			
			u.waitWebElement(inputPass, 200, 3);
			u.waitWebElement(txtLoginControlM, 120, 3);
			u.clear(txtLoginControlM);
			controlMInicio.logOn();
			testCase.addScreenShotCurrentStep("LOGIN");

			return true;

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			testCase.addBoldTextEvidenceCurrentStep("No se pudo ingresar a control-M");
			return false;
		}
	}
	
	

	/**
	 * Ejecuta el JOB con y regresa un boolean en base al resultado de la ejecucion
	 * del JOB
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 */
	public boolean ejecutarJob(String nombreJob) throws FindFailed, InterruptedException {
		testCase.addTextEvidenceCurrentStep("Ejecuta Job: " + nombreJob);
		JSONObject obj = new JSONObject(nombreJob);

		By btnMsg = By.xpath("//button[contains(text(), 'OK')]");

		for (int i = 0; i <= 15; i++) {
			try {
				u.highLight(btnMsg);
				u.hardWait(1);
				u.click(btnMsg);
				u.hardWait(5);

				System.out.println("Aun se muestra mensaje de renovacion de password");
				u.hardWait(1);
			} catch (Exception e) {

				System.out.println("Ya no se muestra mensaje de renovacion de password");
				break;
			}
		}

		ControlM control = new ControlM(u, testCase, obj);
		try {
			control.searchJob();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			control.accionSinOutput();
		} catch (Exception e) {
			System.out.println("**************************************************************");
			System.out.println(e.getMessage());
			System.out.println("**************************************************************");
		}

		String resultadoEjecucion = getStartTimeJobStatus();
		System.out.println("Resultado de la ejecucion: " + resultadoEjecucion);
		String output = control.getOutput();
		System.out.println("Output: " + output);
		testCase.addTextEvidenceCurrentStep("Output: " + output);
		// control.closeViewpoint();

		if (resultadoEjecucion.equals("Ended OK")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Ejecuta el JOB con y regresa un boolean en base al resultado de la ejecucion
	 * del JOB
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 */
	public boolean ejecutarJob(String status, String nombreJob) throws FindFailed, InterruptedException {
		testCase.addTextEvidenceCurrentStep("Ejecuta Job: " + nombreJob);
		JSONObject obj = new JSONObject(nombreJob);

		ControlM control = new ControlM(u, testCase, obj);

		try {
			control.executeJob();
		} catch (Exception e) {
			System.out.println("**************************************************************");
			System.out.println(e.getMessage());
			System.out.println("**************************************************************");
		}

		String resultadoEjecucion = getCurrentJobStatus(status);
		System.out.println("Resultado de la ejecucion: " + resultadoEjecucion);
		String output = getOutput();
		System.out.println("Output: " + output);
		testCase.addTextEvidenceCurrentStep("Output: " + output);
		// control.closeViewpoint();

		if (resultadoEjecucion.equals("Ended OK")) {
			return true;
		} else {
			return false;
		}
	}

	By statusJob = By.xpath("//header[text() = 'Status ']/ancestor::main[1]//span[2]");
	By startTime = By.xpath("//header[contains(text(), 'Start Time ')]/ancestor::main[1]//span[2]");

	/**
	 * Obtiene el status del JOB
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 */
	public String getCurrentJobStatus(String startTimeI) {
		u.hardWait(2);
		String status = null;
		String startTimeS = null;

		u.waitWebElement(statusJob, 10, 1);
		u.highLight(statusJob);

		u.waitWebElement(startTime, 10, 1);
		u.highLight(startTime);

		for (int i = 0; i < 1000000; i++) {
			status = u.getText(statusJob);
			startTimeS = u.getText(startTime);

			if (startTimeS.equals(startTimeI)) {
				if (i % 30 == 0) {
					System.out.println("Esperando el cambio de Start Time");
				}
				u.hardWait(1);
			} else {
				if (status.contains("Executing")) {
					if (i % 30 == 0) {
						System.out.println("Esperando a que termine ejecucion");
					}
					u.hardWait(1);
				} else {
					System.out.println("******************************************");
					System.out.println("ESTATUS ACTUAL: " + status);
					System.out.println("******************************************");
					break;
				}
			}
		}

		return status;
	}

	/**
	 * Obtiene el status del JOB
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 */
	public boolean getCurrentJobStatus() {
		u.hardWait(5);
		String status = null;

		u.waitWebElement(statusJob, 10, 1);
		u.highLight(statusJob);

		boolean ejecucion = false;

		for (int i = 0; i < 10000; i++) {
			status = u.getText(statusJob);

			if (status.contains("Executing")) {
				if (i % 30 == 0) {
					System.out.println("Esperando a que termine ejecucion");
					ejecucion = true;
				}
				u.hardWait(1);
			} else {
				System.out.println("******************************************");
				System.out.println("ESTATUS ACTUAL: " + status);
				System.out.println("******************************************");

				break;
			}
		}

		return ejecucion;
	}

	/**
	 * Obtiene el status del JOB
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 */
	public String getStartTimeJobStatus() {
		u.hardWait(10);
		String status = null;
		String startTimeS = null;

		u.waitWebElement(statusJob, 10, 1);
		status = u.getText(statusJob);

		u.waitWebElement(startTime, 10, 1);
		startTimeS = u.getText(startTime);

		System.out.println("******************************************");
		System.out.println("ESTATUS DESPUES DEL RELEASE: " + status);
		System.out.println("START TIME DESPUES DEL RELEASE: " + startTimeS);
		System.out.println("******************************************");

		return startTimeS;
	}

	By outputJob = By.xpath("//span[contains(text(), 'Output')]");
	By outputText = By.xpath("//div[contains(@class, 'cdk-virtual-scroll-content-wrapper')]");
	By scroll = By.xpath("//cdk-virtual-scroll-viewport");

	
	/**
	 * Obtiene el status del JOB
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 */
	public String getOutput() {
		u.hardWait(5);
		String output = null;

		u.waitWebElement(outputJob, 20, 1);
		u.highLight(outputJob);
		u.click(outputJob);

		u.waitWebElement(outputText, 20, 1);
		u.highLight(outputText);
		
		output = u.getText(outputText);
//		u.hardWait(5);
//
//		WebElement scrollBar = u.getDriver().findElement(scroll);
//
//		Set<String> allText = new HashSet<>();
//
//		long currentScrollTop = 0;
//		long previousScrollTop = -1;
//		long scrollHeight = (long)((JavascriptExecutor) u.getDriver()).executeScript("return arguments[0].scrollHeight;",scrollBar);
//		
//		
//		while (currentScrollTop != previousScrollTop && currentScrollTop + (scrollHeight/4) <scrollHeight ) {
//			
//			previousScrollTop = currentScrollTop;
//			
//			
//			output = "****************************" + "\n" + output + u.getText(outputText) + "\n";
//			testCase.addTextEvidenceCurrentStep(output);
//
//			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollTop += "+scrollHeight/4+";",
//					scrollBar);
//
//			// Espera un momento para que el contenido se actualice
//			u.hardWait(2); // Ajusta el tiempo seg�n la velocidad de carga
//
//			currentScrollTop = (long)((JavascriptExecutor) u.getDriver()).executeScript("return arguments[0].scrollTop;",scrollBar);
//		}

		return output;
	}

	/**
	 * Da clic en la opcion Release del JOB a Ejecutar del JOB
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 */
	public String releaseJOB(String nombreJob) throws FindFailed, InterruptedException {
		testCase.addTextEvidenceCurrentStep("Ejecuta Job: " + nombreJob);
		JSONObject obj = new JSONObject(nombreJob);

		By btnMsg = By.xpath("//button[contains(text(), 'OK')]");

		for (int i = 0; i <= 8; i++) {
			try {
				u.highLight(btnMsg);
				u.hardWait(1);
				u.click(btnMsg);
				u.hardWait(5);

				System.out.println("Aun se muestra mensaje de renovacion de password");
				u.hardWait(1);
			} catch (Exception e) {

				System.out.println("Ya no se muestra mensaje de renovacion de password");
				break;
			}
		}

		ControlM control = new ControlM(u, testCase, obj);
		try {
			control.searchJob();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			control.accionSinOutput();
		} catch (Exception e) {
			System.out.println("**************************************************************");
			System.out.println(e.getMessage());
			System.out.println("**************************************************************");
		}
		u.hardWait(5);

		boolean ejecucion = getCurrentJobStatus();

		if (ejecucion == true) {
			System.out.println("SE EJECUTO SOLO EN EL RELEASE");
			return "Ejecutado";
		}

		String startTime = getStartTimeJobStatus();

		System.out.println("Status actual:" + startTime);

		return startTime;
	}

	/**
	 * Da clic en la opcion Release del JOB a Ejecutar
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 */
	public boolean ejecutarJOBStatus(String startTime, String actions1, String actions2)
			throws FindFailed, InterruptedException {
		JSONObject obj = null;
		u.waitWebElement(statusJob, 30, 1);
		String status = u.getText(statusJob);
		String resultadoEjecucion;

		if (startTime.equals("Ejecutado")) {
			resultadoEjecucion = getCurrentJobStatus(startTime);
			System.out.println("Resultado de la ejecucion: " + resultadoEjecucion);

			testCase.addScreenShotCurrentStep(resultadoEjecucion);
			String output = getOutput();
			System.out.println("Output: " + output);
			testCase.addTextEvidenceCurrentStep("Output: " + output);
		} else {

			for (int i = 0; i < 1000000; i++) {
				if (status.contains("Wait for Event")) {
					obj = new JSONObject(actions1);
					break;
				} else if (status.contains("Executing")) {
					System.out.println("Esperando a que termine ejecucion");
					u.hardWait(1);
				} else {
					obj = new JSONObject(actions2);
					break;
				}
				status = u.getText(statusJob);
			}

			ControlM control = new ControlM(u, testCase, obj);

			try {
				control.accionSinOutput();
			} catch (Exception e) {
				System.out.println("**************************************************************");
				System.out.println(e.getMessage());
				System.out.println("**************************************************************");
			}

			resultadoEjecucion = getCurrentJobStatus(startTime);
			System.out.println("Resultado de la ejecucion: " + resultadoEjecucion);
			testCase.addScreenShotCurrentStep(resultadoEjecucion);

			String output = getOutput();
			System.out.println("Output: " + output);
			//testCase.addTextEvidenceCurrentStep("Output: " + output);
			// control.closeViewpoint();
		}
		if (resultadoEjecucion.equals("Ended OK")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Da clic en la opcion RERUN del JOB a Ejecutar
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 */
	public boolean rerunJOB(String job) throws FindFailed, InterruptedException {
		try {
			JSONObject obj = new JSONObject(job);

			ControlM control = new ControlM(u, testCase, obj);

			try {
				control.accionSinOutput();
			} catch (Exception e) {
				System.out.println("**************************************************************");
				System.out.println(e.getMessage());
				System.out.println("**************************************************************");
			}

			return true;
		} catch (Exception e) {
			System.out.println("No se ha holdeado el JOB " + e);
			return false;
		}
	}

	/**
	 * Da clic en la opcion HOLD del JOB a Ejecutar
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 */
	public boolean holdJOB(String job) throws FindFailed, InterruptedException {
		try {
			JSONObject obj = new JSONObject(job);

			ControlM control = new ControlM(u, testCase, obj);

			try {
				control.accionSinOutput();
			} catch (Exception e) {
				System.out.println("**************************************************************");
				System.out.println(e.getMessage());
				System.out.println("**************************************************************");
			}

			control.closeViewpoint();
			return true;
		} catch (Exception e) {
			System.out.println("No se ha holdeado el JOB " + e);
			return false;
		}
	}

	/**
	 * Crea un HashMap con key = nombre de la imagen y value = obj Pattern(path,
	 * similarity) para utilizar con Sikuli
	 * 
	 * @param dir raiz en donde se van a buscar las imagenes
	 * @return HashMap<String, Pattern>: con el nombre y el obj Pattern de la imagen
	 * @author guerrero.vargas
	 */
	public HashMap<String, org.sikuli.script.Pattern> getPatternsImg(String path) {
		HashMap<String, org.sikuli.script.Pattern> map = new HashMap<String, org.sikuli.script.Pattern>();
		File file = new File(sk.PatternPath("EBS\\"+path));
		File[] files = file.listFiles();

		ArrayList<org.sikuli.script.Pattern> patterns = new ArrayList<org.sikuli.script.Pattern>();

		// agrega el obj Pattern al ArrayList patterns
		for (File f : files) {
			if (f.isDirectory()) {
				// si el file es una carpeta entra a buscar imgs
				map.putAll((getPatternsImg(path + "\\" + f.getName())));

			} else {
				patterns.add(new org.sikuli.script.Pattern(f.getAbsolutePath()).similar((float) 0.95));
			}
		}

		// recorre el ArrayList patters para obtener el path individual de las img
		for (org.sikuli.script.Pattern p : patterns) {
			String fname = p.getFilename();
			fname = fname.replace(".png", ""); // elimina la extension
			fname = fname.replace("\\", "-"); //
			String[] afname = fname.split("-");

			map.put(afname[afname.length - 1], p);
		}

		return map;
	}

	/*
	 * Espera un tiempo definido a que aparezca en la pantalla la imagen solicitada
	 * 
	 * @param Tiempo de espera para la imagen. Nombre de imagen
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 */
	public boolean waitImage(int time, String image) {
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
			sk.screen.wait(ImgPattern.get(image), 1);
			return true;
		} catch (Exception e) {
			screenshot();
			// testCase.addBoldTextEvidenceCurrentStep("No se muestra la imagen " + image);
			return false;
		}
	}

	/*
	 * Espera un tiempo definido a que desaparezca la imagen indicada
	 * 
	 * @param Tiempo de espera para la imagen. Nombre de imagen
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 */
	public boolean waitDisImage(int time, String image) {
		for (int i = 0; i < time; i++) {
			try {
				sk.screen.wait(ImgPattern.get(image), 2);
				System.out.println("Se muestra la imagen: " + image);
				// screenshot();
			} catch (Exception e) {
				if (i % 30 == 0) {
					System.out.println("Esperando imagen " + image + "...");
				}
				break;
			}
		}

		try {
			sk.screen.wait(ImgPattern.get(image), 1);
			testCase.addBoldTextEvidenceCurrentStep("Aun se muestra la imagen " + image);
			return false;
		} catch (Exception e) {
			return true;
		}
	}

	/*
	 * Toma un screenshot de la pantalla en que se encuentre el paso
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 */
	public void screenshot() {
		u.hardWait(1);
		String filename = "evidence" + numeroEvidencia;
		testCase.addScreenShotCurrentStep(filename);
		numeroEvidencia++;
		u.hardWait(1);
	}

	/*
	 * Realiza la conexion al servidor SFTP indicado
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 */
	@SuppressWarnings("finally")
	public boolean validarCSVGenerado(String host, int port, String user, String password, String ruta,
			String docName) {
		LocalDate currentDate = LocalDate.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

		String date = currentDate.format(formatter);
		System.out.println(date);

		docName = docName + date + ".csv";
		String path = "/" + ruta + docName;

		Session session = null;
		ChannelSftp channelSftp = null;

		try {
			// Crear y configurar sesion SSH
			JSch jsch = new JSch();
			session = jsch.getSession(user, host, port);
			session.setPassword(password);

			// Configurar propiedades SSH para evitar problemas de seguridad
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);

			// Conectar al servidor
			session.connect();
			System.out.println("Conexion SFTP exitosa");

			// Abrir canal SFTP
			channelSftp = (ChannelSftp) session.openChannel("sftp");
			channelSftp.connect();
			System.out.println("Canal SFTP abierto");

			ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
			sftpChannel.connect();

			// Verificar existencia del archivo
			boolean fileExists = checkFileExists(channelSftp, path);
			if (fileExists) {
				// Descargar el archivo y leerlo como InputStream
				InputStream inputStream = sftpChannel.get(path);
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

				// Obt�n los atributos del archivo
				SftpATTRS attrs = sftpChannel.stat(path);

				// Convierte la fecha de modificaci�n a un formato legible
				long mtime = attrs.getMTime() * 1000L; // La fecha se obtiene en segundos, as� que convertimos a
														// milisegundos
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String lastModified = sdf.format(new java.util.Date(mtime));

				testCase.addBoldTextEvidenceCurrentStep(
						"Fecha de ultima modificacion del archivo " + docName + ": " + lastModified);

				// Leer y procesar el CSV
				@SuppressWarnings("deprecation")
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
				int recordCount = 0;

				// Mostrar encabezados
				System.out.println("Encabezados: " + csvParser.getHeaderNames());
				testCase.addTextEvidenceCurrentStep("Encabezados: " + csvParser.getHeaderNames());

				// Leer filas y mostrar las primeras 20
				for (CSVRecord record : csvParser) {
					if (recordCount == 0) {
						testCase.addBoldTextEvidenceCurrentStep(
								"SE TOMA UNA MUESTRA DE 20 REGISTROS DEL ARCHIVO CSV GENERADO");
					}
					System.out.println(record);
					testCase.addTextEvidenceCurrentStep("" + record);
					recordCount++;

					if (recordCount >= 20)
						break;
				}

				// Verificar si solo tiene encabezados (sin registros)
				if (recordCount == 0) {
					System.out.println("Error: El archivo no tiene registros.");
				}

				// Cerrar recursos
				csvParser.close();
				reader.close();
				inputStream.close();
				return true;
			} else {
				System.out.println("El archivo no existe en la ruta especificada");
				return false;
			}

		} catch (Exception e) {
			System.out.println("Error en la conexion SFTP: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Error en la conexion SFTP");
			testCase.addBoldTextEvidenceCurrentStep("Datos de conexion usados:");
			testCase.addBoldTextEvidenceCurrentStep(host);
			testCase.addBoldTextEvidenceCurrentStep(user);
			testCase.addBoldTextEvidenceCurrentStep(password);
			testCase.addBoldTextEvidenceCurrentStep("" + port);
			return false;

		} finally {
			// Cerrar canal y session
			if (channelSftp != null && channelSftp.isConnected()) {
				channelSftp.disconnect();
			}
			if (session != null && session.isConnected()) {
				session.disconnect();
			}

			return true;
		}
	}

	public boolean checkFileExists(ChannelSftp channelSftp, String remoteFilePath) {
		try {
			channelSftp.lstat(remoteFilePath); // lstat lanza una excepci�n si el archivo no existe
			return true;
		} catch (SftpException e) {
			if (e.id == ChannelSftp.SSH_FX_NO_SUCH_FILE) {
				return false; // El archivo no existe
			} else {
				throw new RuntimeException("Error al verificar el archivo: " + e.getMessage());
			}
		}
	}
	
	/*
	 * Realiza la conexion al servidor SFTP indicado
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 */
	@SuppressWarnings("finally")
	public boolean validarConexion(String host, int port, String user, String password) {
		LocalDate currentDate = LocalDate.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

		String date = currentDate.format(formatter);
		System.out.println(date);

		Session session = null;
		ChannelSftp channelSftp = null;

		try {
			// Crear y configurar sesion SSH
			JSch jsch = new JSch();
			session = jsch.getSession(user, host, port);
			session.setPassword(password);

			// Configurar propiedades SSH para evitar problemas de seguridad
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);

			// Conectar al servidor
			session.connect();
			System.out.println("Conexion SFTP exitosa");

		} catch (Exception e) {
			System.out.println("Error en la conexion SFTP: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Error en la conexion SFTP");
			testCase.addBoldTextEvidenceCurrentStep("Datos de conexion usados:");
			testCase.addBoldTextEvidenceCurrentStep(host);
			testCase.addBoldTextEvidenceCurrentStep(user);
			testCase.addBoldTextEvidenceCurrentStep(password);
			testCase.addBoldTextEvidenceCurrentStep("" + port);
			return false;

		} finally {
			// Cerrar canal y session
			if (channelSftp != null && channelSftp.isConnected()) {
				channelSftp.disconnect();
			}
			if (session != null && session.isConnected()) {
				session.disconnect();
			}

			return true;
		}
	}

	@SuppressWarnings("finally")
	public boolean validarCSVGenerados(String host, int port, String user, String password, String ruta) {
		Session session = null;
		ChannelSftp channelSftp = null;

		try {
			// Crear y configurar sesion SSH
			JSch jsch = new JSch();
			session = jsch.getSession(user, host, port);
			session.setPassword(password);

			// Configurar propiedades SSH para evitar problemas de seguridad
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);

			// Conectar al servidor
			session.connect();
			System.out.println("Conexion SFTP exitosa");

			// Abrir canal SFTP
			channelSftp = (ChannelSftp) session.openChannel("sftp");
			channelSftp.connect();
			System.out.println("Canal SFTP abierto");

			ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
			sftpChannel.connect();

			// Listar archivos en el directorio remoto
			Vector<?> fileList = sftpChannel.ls(ruta);

			// Formato de fecha para la �ltima modificaci�n
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			// Iterar sobre la lista de archivos y mostrar el nombre con la fecha de
			// modificaci�n
			testCase.addBoldTextEvidenceCurrentStep("Archivos encontrados en la ruta: " + ruta);
			for (Object obj : fileList) {
				ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) obj;
				// Obtener la fecha de la �ltima modificaci�n
				long modifiedTime = entry.getAttrs().getMTime() * 1000L; // Convertir a milisegundos
				Date modifiedDate = new Date(modifiedTime);
				String formattedDate = sdf.format(modifiedDate);

				// Imprimir nombre del archivo y fecha de modificaci�n
				System.out.println("Archivo: " + entry.getFilename() + " | �ltima modificaci�n: " + formattedDate);

				testCase.addTextEvidenceCurrentStep(
						"Archivo: " + entry.getFilename() + " | �ltima modificaci�n: " + formattedDate);
			}

		} catch (Exception e) {
			System.out.println("Error en la conexion SFTP: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Error en la conexion SFTP");
			testCase.addBoldTextEvidenceCurrentStep("Datos de conexion usados:");
			testCase.addBoldTextEvidenceCurrentStep(host);
			testCase.addBoldTextEvidenceCurrentStep(user);
			testCase.addBoldTextEvidenceCurrentStep(password);
			testCase.addBoldTextEvidenceCurrentStep("" + port);
			return false;

		} finally {
			// Cerrar canal y session
			if (channelSftp != null && channelSftp.isConnected()) {
				channelSftp.disconnect();
			}
			if (session != null && session.isConnected()) {
				session.disconnect();
			}

			return true;
		}
	}
}
