package WorkFlows.Sembrado.MatrizALM.ConfigPlazaPlaza;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Sembrado.pomSembradoALM;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class TC_000_ConfiguracionPlazaPlaza_Combinabilidad extends BaseExecution {
	private SeleniumUtil u;
	private pomSembradoALM util;
	private WebDriverWait wait;

	/// COMBINABILIDAD WEB ELEMENTS - CB
	private By locationCBgrupo1Select = By.id("idCmbPlanogrupo1");
	private By locationCBgrupo2Select = By.id("idCmbPlanogrupo2");
	private By locationCBAgregarButton = By.cssSelector(".boton");

	@Test(dataProvider = "data-provider")

	public void ATC_FT_000_ConfiguracionPlazaPlaza_Combinabilidad(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Framework WorkFlows");

		/// GET DATA FROM INPUT FILE ///
		String accion = data.get("accion");
		String grupo1 = data.get("grupo1");
		String grupo2 = data.get("grupo2");
		System.out.println(" Grupo1: " + grupo1 + " Grupo2: " + grupo2);

		/// REVISION OF MANDATORY DATA IN THE INPUT FILE ///
		if ((accion.equals("")) || (grupo1.equals("")) || (grupo2.equals(""))) {
			testCase.failStep("Faltan datos mandatorios en el archivo de entrada");
		}

		/// LOGIN ///
		addStep("Iniciar sesi�n");
		u = new SeleniumUtil(new FirefoxTest(), true);
		util = new pomSembradoALM(u);
		wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(10));
		util.loginSembrado(GlobalVariables.urlSembrado, GlobalVariables.userAdminSembrado,
				GlobalVariables.passSembrado);
		testCase.addScreenShotCurrentStep(u, "Inicio de sesion exitosa");

		/// SEARCH SEMBRADO PLAZA OPTION ///
		addStep("Ingresar a Sembrado Plaza");
		util.goToSembradoPlaza();
		testCase.addScreenShotCurrentStep(u, "Pagina Sembrado Plaza");

		/// SELECT COMBINABILIDAD OPTION ///
		addStep("Ingresar a Combinabilidad");
		util.goToCombinabilidad();
		testCase.addScreenShotCurrentStep(u, "Combinabilidad");

		/// ACCIONES ///
		String[] resultTable;
		switch (accion.toUpperCase()) {
		case "AGREGAR":
			addStep("Agregar Combinabilidad");
			testCase.addTextEvidenceCurrentStep("Grupo 1: \"" + grupo1 + "\" - Grupo 2: \"" + grupo2 + "\"");
			enterDataCombinabilidad(grupo1, grupo2);
			testCase.addScreenShotCurrentStep(u, "Datos ingresados");

			addCombinabilidad();
			String resultText = util.getTextDialogBox();
			if (resultText.contains("Registro Almacenado")) {
				testCase.addTextEvidenceCurrentStep("Se agreg� exitosamente");
				testCase.addScreenShotCurrentStep(u, "Se agreg� Combinabilidad");
				util.selectOptionDialogBox("aceptar");
			} else if (resultText.contains("ya existen")) {
				testCase.addTextEvidenceCurrentStep("La combinabilidad de Grupo 1 y Grupo 2 ya existe");
				testCase.addScreenShotCurrentStep(u, "Ya existe Combinabilidad");
				util.selectOptionDialogBox("aceptar");
			} else {
				testCase.failStep("Agregar Combinabilidad: no se reconoce el texto del cuadro de di�logo");
			}

			addStep("Verificar que se encuentre agregada la combinabilidad: Grupo 1: \"" + grupo1 + "\" - Grupo 2: \""
					+ grupo2 + "\"");
			resultTable = searchInTableCombinabilidad(grupo1, grupo2);
			if (Boolean.parseBoolean(resultTable[0])) {
				testCase.addScreenShotCurrentStep(u, "Grupo1-Grupo2 guardado correctamente");
			} else {
				testCase.failStep("No se encontr� el registro en la tabla");
			}
			break;
		default:
			throw new AssertionError("La opci�n de la accion no es v�lida");
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void enterDataCombinabilidad(String grupo1, String grupo2) {
		try {
			u.hardWait(3);
			wait.until(ExpectedConditions.elementToBeClickable(locationCBgrupo1Select));
			Select grupo1Select = new Select(u.getDriver().findElement(locationCBgrupo1Select));
			grupo1Select.selectByVisibleText(grupo1);

			u.hardWait(1);
			wait.until(ExpectedConditions.elementToBeClickable(locationCBgrupo2Select));
			Select grupo2Select = new Select(u.getDriver().findElement(locationCBgrupo2Select));
			grupo2Select.selectByVisibleText(grupo2);

		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se pudo ingresar alguna de las opciones");
		}
	}

	public void addCombinabilidad() {
		try {
			u.highLight(locationCBAgregarButton);
			u.hardWait(1);
			u.click(locationCBAgregarButton);
			u.hardWait(1);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se encontr� el bot�n \"Agregar\"");
		}
	}

	public String[] searchInTableCombinabilidad(String grupo1, String grupo2) {
		String[] results = new String[2];
		results[0] = "false";
		results[1] = "";
		try {
			u.hardWait(3);
			List<WebElement> elementsTable = u.getDriver()
					.findElements(By.cssSelector(".tableContainer > table > tbody > tr"));
			System.out.println("Elementos en la Tabla: " + elementsTable.size());
			for (int i = 0; i < elementsTable.size(); i++) {
				String element = elementsTable.get(i).getAttribute("innerText").toUpperCase().trim();
				String[] dato = element.split("\t");
				String g1 = dato[0];
				String g2 = dato[1];
				System.out.println("Fila " + i + ": Grupo1: " + g1 + "Grupo2: " + g2);
				if (g1.equalsIgnoreCase(grupo1)) {
					System.out.println("SI ESTA GRUPO 1! " + g1 + " = " + grupo1);
					if (g2.equalsIgnoreCase(grupo2)) {
						System.out.println("SI ESTA GRUPO 2! " + g2 + " = " + grupo2);
						u.highLight(By.cssSelector(
								".tableContainer > table > tbody > tr:nth-child(" + (i + 1) + ") > td:nth-child(1)"));
						u.highLight(By.cssSelector(
								".tableContainer > table > tbody > tr:nth-child(" + (i + 1) + ") > td:nth-child(2)"));
						results[0] = "true";
						results[1] = Integer.toString(i + 1);
						break;
					}
				}
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Error al realizar la b�squeda en la tabla");
		}
		return results;
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Ingresar los datos en el data-provider correspondiente";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "TC_000_ConfiguracionPlazaPlaza_Combinabilidad";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Esmeralda Garcia Torres";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_000_ConfiguracionPlazaPlaza_Combinabilidad";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
