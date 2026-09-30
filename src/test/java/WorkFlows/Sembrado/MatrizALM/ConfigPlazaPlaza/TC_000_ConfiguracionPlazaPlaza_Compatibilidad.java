package WorkFlows.Sembrado.MatrizALM.ConfigPlazaPlaza;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class TC_000_ConfiguracionPlazaPlaza_Compatibilidad extends BaseExecution {
	private SeleniumUtil u;
	private pomSembradoALM util;
	private WebDriverWait wait;

	/// COMPATIBILIDAD WEB ELEMENTS - CP
	private By locationCPplazaSelect = By.id("idCmbPlaza");
	private By locationCPmuebleOrigSelect = By.id("idCmbMuebleOrigen");
	private By locationCPmuebleCompSelect = By.id("idCmbMuebleCompatible");
	private By locationCPtamanioSelect = By.id("idCmbTamanoMuebleCompatible");
	private By locationCPagregarButton = By.cssSelector("#TABLE_9 > tbody > tr:nth-child(12) > td > input");
	private By locationCPrecuperarButton = By
			.cssSelector("#TABLE_9 > tbody:nth-child(1) > tr:nth-child(4) > td:nth-child(3) > input:nth-child(1)");

	@Test(dataProvider = "data-provider")

	public void ATC_FT_000_ConfiguracionPlazaPlaza_Compatibilidad(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Framework WorkFlows");

		/// GET DATA FROM INPUT FILE ///
		String accion = data.get("accion");
		String plaza = data.get("plaza");
		String muebleOrigen = data.get("muebleOrigen");
		String muebleCompatible = data.get("muebleCompatible");
		String tamanio = data.get("tamanio");
		System.out.println(" Plaza: " + plaza + " Mueble Origen: " + muebleOrigen + " Mueble Compatible: "
				+ muebleCompatible + " Tama�o: " + tamanio);

		/// REVISION OF MANDATORY DATA IN THE INPUT FILE ///
		if ((accion.equals("")) || (plaza.equals("")) || (muebleOrigen.equals("")) || (muebleCompatible.equals(""))
				|| (tamanio.equals(""))) {
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

		/// SELECT COMPATIBILIDAD OPTION ///
		addStep("Ingresar a Compatibilidad");
		util.goToCompatibilidad();
		testCase.addScreenShotCurrentStep(u, "Compatibilidad");

		/// ACCIONES ///
		String[] resultTable;
		switch (accion.toUpperCase()) {
		case "AGREGAR": // ------------------------------------------------------------------------------------------------
						// ALTA
			addStep("Ingresar datos");
			testCase.addTextEvidenceCurrentStep("Plaza: \"" + plaza + "\" - Mueble Original: \"" + muebleOrigen
					+ "\" - Mueble Compatible: \"" + muebleCompatible + "\" - Tama�o: \"" + tamanio + "\"");
			enterDataPlace(plaza);
			testCase.addScreenShotCurrentStep(u, "Seleccionar plaza");
			enterDataCompatibilidad(muebleOrigen, muebleCompatible, tamanio);
			testCase.addScreenShotCurrentStep(u, "Datos ingresados");

			addStep("Agregar Compatibilidad");
			addCompatibilidad();
			String resultText = util.getTextDialogBox();
			if (resultText.contains("ya existe")) {
				testCase.addTextEvidenceCurrentStep("La compatibilidad ya existe");
				testCase.addScreenShotCurrentStep(u, "Ya existe la Compatibilidad");
				util.selectOptionDialogBox("aceptar");
			}

			addStep("Verificar que se encuentre agregada la compatibilidad: Mueble: \"" + muebleOrigen
					+ "\" - Mueble Compatible: \"" + muebleCompatible + "\" - Tama�o: \"" + tamanio + "\"");
			resultTable = searchInTableCompatibilidad(plaza, muebleOrigen, muebleCompatible, tamanio);
			if (Boolean.parseBoolean(resultTable[0])) {
				testCase.addScreenShotCurrentStep(u, "Mueble-Tama�o guardado correctamente");
			} else {
				testCase.failStep("No se encontr� el registro en la tabla");
			}
			break;
		case "ELIMINAR": // --------------------------------------------------------------------------------------------
							// ELIMINAR
			addStep("Seleccionar la plaza");
			enterDataPlace(plaza);
			testCase.addScreenShotCurrentStep(u, "Seleccionar plaza");

			addStep("Buscar el registro en la tabla");
			resultTable = searchInTableCompatibilidad(plaza, muebleOrigen, muebleCompatible, tamanio);
			if (Boolean.parseBoolean(resultTable[0])) {
				testCase.addScreenShotCurrentStep(u, "Se encontr� el registro");
			} else {
				testCase.failStep("No se encontr� el registro en la tabla");
			}

			addStep("Eliminar pero en la confirmacion seleccionar \"No\"");
			deleteCompatibilidad(Integer.parseInt(resultTable[1]), "no");

			addStep("Eliminar pero en la confirmacion seleccionar \"Salir\"");
			deleteCompatibilidad(Integer.parseInt(resultTable[1]), "salir");

			addStep("Eliminar pero en la confirmacion seleccionar \"Si\"");
			deleteCompatibilidad(Integer.parseInt(resultTable[1]), "si");
			util.selectOptionDialogBox("aceptar");
			break;
		case "RECUPERAR": // -------------------------------------------------------------------------------------------
							// RECUPERAR
			addStep("Seleccionar la plaza");
			enterDataPlace(plaza);
			testCase.addScreenShotCurrentStep(u, "Seleccionar plaza");

			addStep("Recuperar pero en la confirmacion seleccionar \"No\"");
			recoverPreviousValuesCompatibilidad("no");

			addStep("Recuperar pero en la confirmacion seleccionar \"Salir\"");
			recoverPreviousValuesCompatibilidad("salir");

			addStep("Recuperar pero en la confirmacion seleccionar \"Si\"");
			recoverPreviousValuesCompatibilidad("si");
			if (util.getTextDialogBox().contains("Error")) {
				throw new AssertionError("Error al recuperar");
			}
			util.selectOptionDialogBox("aceptar");
			break;
		default:
			throw new AssertionError("La opci�n de la accion no es v�lida");
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void enterDataPlace(String place) {
		try {
			u.hardWait(3);
			wait.until(ExpectedConditions.elementToBeClickable(locationCPplazaSelect));
			Select placeSelect = new Select(u.getDriver().findElement(locationCPplazaSelect));
			placeSelect.selectByVisibleText(place);
			u.highLight(locationCPplazaSelect);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se pudo seleccionar la plaza");
		}
	}

	public void enterDataCompatibilidad(String muebleOrigen, String muebleCompatible, String tamanio) {
		try {
			u.hardWait(1);
			wait.until(ExpectedConditions.elementToBeClickable(locationCPmuebleOrigSelect));
			Select furnitureOriginSelect = new Select(u.getDriver().findElement(locationCPmuebleOrigSelect));
			furnitureOriginSelect.selectByVisibleText(muebleOrigen);
			u.highLight(locationCPmuebleOrigSelect);

			u.hardWait(1);
			wait.until(ExpectedConditions.elementToBeClickable(locationCPmuebleCompSelect));
			Select furnitureCompatibleSelect = new Select(u.getDriver().findElement(locationCPmuebleCompSelect));
			furnitureCompatibleSelect.selectByVisibleText(muebleCompatible);
			u.highLight(locationCPmuebleCompSelect);

			u.hardWait(1);
			wait.until(ExpectedConditions.elementToBeClickable(locationCPtamanioSelect));
			Select sizeSelect = new Select(u.getDriver().findElement(locationCPtamanioSelect));
			sizeSelect.selectByVisibleText(tamanio);
			u.highLight(locationCPtamanioSelect);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se pudo ingresar alguna de las opciones");
		}
	}

	public void addCompatibilidad() {
		try {
			u.highLight(locationCPagregarButton);
			u.hardWait(1);
			u.click(locationCPagregarButton);
			u.hardWait(1);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se encontr� el bot�n \"Agregar\"");
		}
	}

	public void deleteCompatibilidad(int row, String option) {
		try {
			By deleteOption = By
					.cssSelector("tr.tablaDatos:nth-child(" + row + ") > td:nth-child(1) > img:nth-child(1)");
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

	public void recoverPreviousValuesCompatibilidad(String option) {
		try {
			u.highLight(locationCPrecuperarButton);
			u.click(locationCPrecuperarButton);
			u.hardWait(1);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se encontr� el bot�n \"Recuperar Valores Previos\"");
		}
		try {
			util.getTextDialogBox();
			testCase.addScreenShotCurrentStep(u, "Recuperar - " + option.toUpperCase());
			util.selectOptionDialogBox(option.toUpperCase());
			u.hardWait(1);
			testCase.addScreenShotCurrentStep(u, "Result - " + option.toUpperCase());
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fall� al seleccionar la opci�n");
		}
	}

	public String[] searchInTableCompatibilidad(String plaza, String muebleOrigen, String muebleCompatible,
			String tamanio) {
		String[] results = new String[2];
		results[0] = "false";
		results[1] = "";
		try {
			u.hardWait(4);
			List<WebElement> elementsTable = u.getDriver()
					.findElements(By.cssSelector(".tableContainer > table > tbody > tr"));
			System.out.println("Elementos en la Tabla: " + elementsTable.size());
			for (int i = 0; i < elementsTable.size(); i++) {
				String element = elementsTable.get(i).getAttribute("innerText").toUpperCase();
				String[] dato = element.split("\t");
				String furnitureOrigElement = dato[0].trim();
				String furnitureCompElement = dato[2].trim().substring((dato[2].length() - muebleCompatible.length()));
				String sizeElement = dato[3].trim().substring(0, tamanio.length());
				System.out.println("Fila " + i + ": Mueble Orig: " + furnitureOrigElement + ": Mueble Comp: "
						+ furnitureCompElement + " Tama�o: " + sizeElement);
				if (furnitureOrigElement.equalsIgnoreCase(muebleOrigen)) {
					System.out.println("SI ESTA MUEBLE ORIGEN! " + furnitureOrigElement + " = " + muebleOrigen);
					if (furnitureCompElement.equalsIgnoreCase(muebleCompatible)) {
						System.out.println(
								"SI ESTA MUEBLE COMPATIBLE! " + furnitureCompElement + " = " + muebleCompatible);
						if (sizeElement.equalsIgnoreCase(tamanio)) {
							System.out.println("SI ESTA TAMA�O! " + sizeElement + " = " + tamanio);
							By furnitureOrigLocation = By.cssSelector(".tableContainer > table > tbody > tr:nth-child("
									+ (i + 1) + ") > td:nth-child(1)");
							By furnitureCompLocation = By.cssSelector(".tableContainer > table > tbody > tr:nth-child("
									+ (i + 1) + ") > td:nth-child(3)");
							By sizeLocation = By.cssSelector(".tableContainer > table > tbody > tr:nth-child(" + (i + 1)
									+ ") > td:nth-child(4)");
							u.highLight(furnitureOrigLocation);
							u.highLight(furnitureCompLocation);
							u.highLight(sizeLocation);
							WebElement m = u.getDriver().findElement(furnitureOrigLocation);
							((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView(true);", m);
							u.hardWait(3);
							results[0] = "true";
							results[1] = Integer.toString(i + 1);
							break;
						}
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
		return "TC_000_ConfiguracionPlazaPlaza_Compatibilidad";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Esmeralda Garcia Torres";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_000_ConfiguracionPlazaPlaza_Compatibilidad";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
