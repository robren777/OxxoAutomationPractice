package EBS.util;

import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.sikuli.script.FindFailed;

import modelo.TestCase;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.SeleniumUtil;

public class CTRL_M_UTIL {


//	 private static final TestCase TestCase = null;
	static SeleniumUtil u;
	static TestCase testCase;

	private String urlControlM = GlobalVariables.CtrlM_URL;
	private String userControlM = GlobalVariables.CtrlM_User;
	private String passControlM = GlobalVariables.CtrlM_Pss;

	By inputPass = By.id("login-user-password");
	By txtLoginControlM = By.xpath("//*[@id=\"login-user-name\"]");
	By Monitoring = By.xpath("//*[text()='Monitoring']");
	By AddViewPoint = By.xpath("//*[text()='Add Viewpoint']");
	By FolderName = By.xpath("//*[contains(text(),'Folder Name')]/following::input[1]");
	By JobName = By.xpath("//*[contains(text(),'Job Name')]/following::input[1]");
	// ExecuteQuery e = new ExecuteQuery(testCase);

	@SuppressWarnings("static-access")
	public CTRL_M_UTIL(SeleniumUtil u, TestCase testcase) throws SQLException {
		this.u = u;
		this.testCase = testcase;
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
					new File(System.getProperty("user.dir") + "/Config/Input/DataProviders/Jobs.xlsx"));
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


}
