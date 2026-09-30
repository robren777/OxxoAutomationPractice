package WorkFlows.Sembrado.MatrizALM.ConfigPlazaPlaza;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Sembrado.pomSembradoALM;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class TC_000_ConfiguracionPlazaPlaza_Conjunto extends BaseExecution {
	private SeleniumUtil u;
	private pomSembradoALM util;
	private WebDriverWait wait;

	/// CONJUNTOS WEB ELEMENTS - CJ
	private By locationCJconjuntosInput = By.id("txtNombreConjunto");
	private By locationCJabreviaturaInput = By.id("txtAbreviaConjunto");
	private By locationCJcrearButton = By.cssSelector(".boton");
	private By locationCJconjuntoSelect = By.cssSelector("select.table-autofilter");

	@Test(dataProvider = "data-provider")

	public void ATC_FT_000_ConfiguracionPlazaPlaza_Conjunto(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Framework WorkFlows");

		// GET DATA FROM INPUT FILE
		// --------------------------------------------------------------------
		String accion = data.get("accion");
		String conjunto = data.get("conjunto");
		String abreviacion = data.get("abreviacion");
		System.out.println("Conjunto: " + conjunto + " Abreviacion: " + abreviacion);

		// REVISION OF MANDATORY DATA IN THE INPUT FILE ///
		if ((conjunto.equals("")) || (abreviacion.equals(""))) {
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

		/// SELECT CONJUNTOS OPTION ///
		addStep("Ingresar a Conjuntos");
		util.goToConjuntos();
		testCase.addScreenShotCurrentStep(u, "Conjuntos");

		/// ACCIONES ///
		String[] resultTable;
		switch (accion.toUpperCase()) {
		case "AGREGAR": // ------------------------------------------------------------------------------------------------
						// ALTA
			addStep("Ingresar datos");
			testCase.addTextEvidenceCurrentStep(
					"Conjunto: \"" + conjunto + "\" - Abreviatura: \"" + abreviacion + "\"");
			enterDataConjuntoAbreviatura(conjunto, abreviacion);
			testCase.addScreenShotCurrentStep(u, "Ingresar Conjunto-Abreviatura");

			addStep("Agregar Conjunto-Abreviatura");
			addConjuntoAbreviatura();
			String resultText = util.getTextDialogBox();
			if (resultText.contains("ya existe")) {
				testCase.addTextEvidenceCurrentStep("El conjunto-abreviacion ya existe");
				testCase.addScreenShotCurrentStep(u, "Ya existe el conjunto-abreviacion");
				util.selectOptionDialogBox("aceptar");
			}

			addStep("Verificar que se encuentre agregado el conjunto: \"" + conjunto + "\" - \"" + abreviacion + "\"");
			selectConjunto(conjunto);
			testCase.addScreenShotCurrentStep(u, "Seleccionar conjunto");
			resultTable = searchInTableConjuntoAbreviatura(conjunto, abreviacion);
			if (Boolean.parseBoolean(resultTable[0])) {
				testCase.addScreenShotCurrentStep(u, "Conjunto-Abreviaci�n se guardado correctamente");
			} else {
				testCase.failStep("No se encontr� el registro en la tabla");
			}

			break;
		case "ELIMINAR": // --------------------------------------------------------------------------------------------
							// ELIMINAR
			addStep("Buscar en la tabla el registro a Eliminar");
			testCase.addTextEvidenceCurrentStep("Seleccionar el conjunto: \"" + conjunto + "\"");
			selectConjunto(conjunto);
			testCase.addScreenShotCurrentStep(u, "Seleccionar conjunto");
			resultTable = searchInTableConjuntoAbreviatura(conjunto, abreviacion);
			if (Boolean.parseBoolean(resultTable[0])) {
				testCase.addScreenShotCurrentStep(u, "Conjunto-Abreviaci�n se guardado correctamente");
			} else {
				testCase.failStep("No se encontr� el registro en la tabla");
			}

			addStep("Eliminar pero en la confirmacion seleccionar \"No\"");
			deleteConjuntoAbr(Integer.parseInt(resultTable[1]), "no");

			addStep("Eliminar pero en la confirmacion seleccionar \"Salir\"");
			deleteConjuntoAbr(Integer.parseInt(resultTable[1]), "salir");

			addStep("Eliminar pero en la confirmacion seleccionar \"Si\"");
			deleteConjuntoAbr(Integer.parseInt(resultTable[1]), "si");
			util.selectOptionDialogBox("aceptar");
			break;
		case "MOSTRAR": // ---------------------------------------------------------------------------------------------
						// MOSTRAR
			addStep("Seleccionar el conjunto: \"" + conjunto + "\"");
			selectConjunto(conjunto);
			testCase.addScreenShotCurrentStep(u, "Seleccionar conjunto");
			break;
		default:
			throw new AssertionError("La opci�n de la accion no es v�lida");
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void enterDataConjuntoAbreviatura(String conjunto, String abreviatura) {
		try {
			u.hardWait(4);
			wait.until(ExpectedConditions.elementToBeClickable(locationCJconjuntosInput));
			u.clear(locationCJconjuntosInput);
			u.sendKeys(locationCJconjuntosInput, conjunto);

			wait.until(ExpectedConditions.elementToBeClickable(locationCJabreviaturaInput));
			u.clear(locationCJabreviaturaInput);
			u.sendKeys(locationCJabreviaturaInput, abreviatura);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se pudo ingresar alguna de las opciones");
		}
	}

	public void addConjuntoAbreviatura() {
		try {
			u.highLight(locationCJcrearButton);
			u.hardWait(1);
			u.click(locationCJcrearButton);
			u.hardWait(1);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se encontr� el bot�n \"Crear\"");
		}
	}

	public void selectConjunto(String conjunto) {
		try {
			u.hardWait(3);
			Select conjuntoSelect = new Select(u.getDriver().findElement(locationCJconjuntoSelect));
			conjuntoSelect.selectByVisibleText(conjunto);
			u.hardWait(3);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se pudo seleccionar el conjunto");
		}
	}

	public void deleteConjuntoAbr(int row, String option) {
		try {
			By deleteOption = By.cssSelector(".tableContainer > table > tbody > tr.tablaDatos:nth-child(" + row
					+ ") > td:nth-child(1) > img:nth-child(1)");
			u.highLight(deleteOption);
			u.click(deleteOption);
			u.hardWait(1);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se encontr� el bot�n \"Eliminar\"");
		}
		try {
			util.getTextDialogBox();
			testCase.addScreenShotCurrentStep(u, "Eliminar - " + option.toUpperCase());
			util.selectOptionDialogBox(option.toUpperCase());
			u.hardWait(1);
			testCase.addScreenShotCurrentStep(u, "Result - " + option.toUpperCase());
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fall� al seleccionar la opci�n");
		}
	}

	public String[] searchInTableConjuntoAbreviatura(String conjunto, String abreviatura) {
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
				element.replaceAll("\\s", "").replaceAll("\\n\\r", "");
				String conjuntoElement = element.substring(0, conjunto.length());
				String abreviaturaElement = element.substring(element.length() - 3);
				System.out.println(
						"Fila " + i + ": Conjunto: " + conjuntoElement + " Abreviatura: " + abreviaturaElement);
				if (conjuntoElement.equalsIgnoreCase(conjunto)) {
					System.out.println("SI ESTA CONJUNTO! " + conjuntoElement + " = " + conjunto);
					if (abreviaturaElement.equalsIgnoreCase(abreviatura)) {
						System.out.println("SI ESTA ABREVIATURA! " + abreviaturaElement + " = " + abreviatura);
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
	
	@AfterTest
	public void afterTest() {
		u.getDriver().quit();
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
		return "TC_000_ConfiguracionPlazaPlaza_Conjunto";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Esmeralda Garcia Torres";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_000_ConfiguracionPlazaPlaza_Conjunto";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
