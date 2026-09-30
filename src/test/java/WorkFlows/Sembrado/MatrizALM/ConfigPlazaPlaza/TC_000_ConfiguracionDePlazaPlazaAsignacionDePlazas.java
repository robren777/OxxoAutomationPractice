package WorkFlows.Sembrado.MatrizALM.ConfigPlazaPlaza;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Sembrado.pomSembradoALM;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class TC_000_ConfiguracionDePlazaPlazaAsignacionDePlazas extends BaseExecution {
	private SeleniumUtil u;
	private pomSembradoALM util;

	/// ASIGNACI�N DE PLAZAS WEB ELEMENTS - AP
	private By locationConjuntoSelect = By.id("idCmbConjunto");
	private By locationPlazaSelect = By.id("idCmbPlaza");
	private By locationAgregarButton = By.cssSelector("#TABLE_9 > tbody > tr:nth-child(6) > td > input");
	private By locationConjuntoTableSelect = By.cssSelector("th.table-filterable:nth-child(1) > select:nth-child(2)");
	private By locationPlazaTableSelect = By.cssSelector("th.table-filterable:nth-child(2) > select:nth-child(2)");

	@Test(dataProvider = "data-provider")

	public void ATC_FT_000_ConfiguracionDePlazaPlazaAsignacionDePlazas(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Framework WorkFlows");

		/// GET DATA FROM INPUT FILE ///
		String accion = data.get("Accion");
		String conjunto = data.get("Conjunto");
		String plaza = data.get("Plaza");
		String code = data.get("Codigo");
		System.out.println("Accion: " + accion + " Conjunto: " + conjunto + " Plaza: " + plaza + " Code: " + code);

		/// REVISION OF MANDATORY DATA IN THE INPUT FILE ///
		if ((accion.equals("")) || (conjunto.equals("")) || (plaza.equals("")) || (code.equals(""))) {
			testCase.failStep("Faltan datos mandatorios en el archivo de entrada");
		}

		/// LOGIN ///
		addStep("Iniciar sesi�n");
		u = new SeleniumUtil(new FirefoxTest(), true);
		util = new pomSembradoALM(u);
		util.loginSembrado(GlobalVariables.urlSembrado, GlobalVariables.userAdminSembrado,
				GlobalVariables.passSembrado);
		testCase.addScreenShotCurrentStep(u, "Inicio de sesion exitosa");

		/// SEARCH SEMBRADO PLAZA OPTION ///
		addStep("Ingresar a Sembrado Plaza");
		util.goToSembradoPlaza();
		testCase.addScreenShotCurrentStep(u, "Pagina Sembrado Plaza");

		/// SELECT ASIGNACION DE PLAZAS OPTION ///
		addStep("Ingresar a Asignaci�n de Plazas");
		util.goToAsignacionPlazas();
		testCase.addScreenShotCurrentStep(u, "Asignaci�n de Plazas");

		/// ACCIONES
		String[] resultTable;
		switch (accion.toUpperCase()) {
		case "AGREGAR": // ------------------------------------------------------------ AGREGAR
			addStep("Seleccionar Conjunto: \"" + conjunto + "\" y Plaza: \"" + plaza + "\"");
			selectConjuntoPlaza(conjunto, plaza);
			testCase.addScreenShotCurrentStep(u, "Conjunto y Plaza seleccionados");

			addStep("Agregar Conjunto-Plaza");
			addConjuntoPlaza();
			String resultText = util.getTextDialogBox();
			if (resultText.contains("Correctamente")) {
				testCase.addTextEvidenceCurrentStep("Se agrego exitosamente");
				testCase.addScreenShotCurrentStep(u, "Agregar Conjunto-Plaza");
				util.selectOptionDialogBox("aceptar");
			} else if (resultText.contains("ya existen:")) {
				testCase.addTextEvidenceCurrentStep("El Conjunto-Plaza ya existe");
				testCase.addScreenShotCurrentStep(u, "El Conjunto-Plaza ya existe");
				util.selectOptionDialogBox("aceptar");
			} else {
				testCase.failStep("Agregar Conjunto-Plaza: no se reconoce el texto del PopUp");
			}

			addStep("Verificar que se encuentre agregado el conjunto: \"" + conjunto + "\" y codigo: \"" + code + "\"");
			resultTable = searchInTable(conjunto, code);
			if (Boolean.parseBoolean(resultTable[0])) {
				testCase.addScreenShotCurrentStep(u, "Conjunto-Plaza encontrado en la tabla");
			} else {
				testCase.failStep("No se encontr� el registro en la tabla");
			}
			break;
		case "MOSTRAR": // ------------------------------------------------------------ MOSTRAR
			addStep("Seleccionar Conjunto: \"" + conjunto + "\"");
			selectConjuntoPlazaTabla(conjunto, "");
			testCase.addScreenShotCurrentStep(u, "Conjunto seleccionado");
			break;
		case "ELIMINAR": // ----------------------------------------------------------- ELIMINAR
			addStep("Seleccionar Conjunto: \"" + conjunto + "\" y Plaza: \"" + code + "\"");
			selectConjuntoPlazaTabla(conjunto, code);
			testCase.addScreenShotCurrentStep(u, "Conjunto y Plaza seleccionados");

			addStep("Buscar el registro en la tabla: \"" + conjunto + "\" y Plaza: \"" + code + "\"");
			testCase.addTextEvidenceCurrentStep("Se busca el codigo tomado del archivo de entrada");
			resultTable = searchInTable(conjunto, code);
			if (Boolean.parseBoolean(resultTable[0])) {
				testCase.addScreenShotCurrentStep(u, "Conjunto-Plaza encontrado en la tabla");
			} else {
				testCase.failStep("No se encontr� el registro en la tabla");
			}

			addStep("Eliminar pero en la confirmacion seleccionar \"No\"");
			deletedConjuntoPlaza(Integer.parseInt(resultTable[1]), "no");

			addStep("Eliminar pero en la confirmacion seleccionar \"Salir\"");
			deletedConjuntoPlaza(Integer.parseInt(resultTable[1]), "salir");

			addStep("Eliminar pero en la confirmacion seleccionar \"Si\"");
			deletedConjuntoPlaza(Integer.parseInt(resultTable[1]), "si");
			util.selectOptionDialogBox("aceptar");

			addStep("Verificar eliminaci�n");
			selectConjuntoPlazaTabla(conjunto, code);
			resultTable = searchInTable(conjunto, code);
			if (!Boolean.parseBoolean(resultTable[0])) {
				testCase.addScreenShotCurrentStep(u, "Conjunto-Plaza no se encontro en la tabla");
			} else {
				testCase.failStep("Se encontr� el registro en la tabla");
			}
			break;
		default:
			throw new AssertionError("La opci�n de la accion no es v�lida");
		}
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Selecciona el conjunto y plaza en los combos correspondientes para agregar.
	 * 
	 * @param conjunto
	 * @param plaza
	 * @author 73289
	 */
	private void selectConjuntoPlaza(String conjunto, String plaza) {
		try {
			Select conjuntoSelect = new Select(u.getDriver().findElement(locationConjuntoSelect));
			conjuntoSelect.selectByVisibleText(conjunto);
			u.hardWait(1);
			Select plazaSelect = new Select(u.getDriver().findElement(locationPlazaSelect));
			plazaSelect.selectByVisibleText(plaza);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se encontr� alguna de las opciones");
		}
	}

	/**
	 * Selecciona el conjunto y el c�digo en los combos correspondientes para buscar
	 * en la tabla.
	 * 
	 * @param conjunto
	 * @param code
	 * @author 73289
	 */
	private void selectConjuntoPlazaTabla(String conjunto, String code) {
		try {
			if (!conjunto.equalsIgnoreCase("")) {
				u.hardWait(3);
				Select conjuntoTableSelect = new Select(u.getDriver().findElement(locationConjuntoTableSelect));
				conjuntoTableSelect.selectByVisibleText(conjunto);
			}
			if (!code.equalsIgnoreCase("")) {
				u.hardWait(1);
				Select plazaTableSelect = new Select(u.getDriver().findElement(locationPlazaTableSelect));
				plazaTableSelect.selectByVisibleText(code);
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se encontr� alguna de las opciones");
		}
	}

	/**
	 * Da clic en el bot�n de "Agregar"
	 * 
	 * @author 73289
	 */
	private void addConjuntoPlaza() {
		try {
			u.highLight(locationAgregarButton);
			u.hardWait(1);
			u.click(locationAgregarButton);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se encontr� el bot�n \"Agregar\"");
		}
	}

	/**
	 * Da clic en el icono de eliminar del registro correspondiente y selecciona la
	 * opci�n esperada en el cuadro de di�logo de la confirmaci�n.
	 * 
	 * @param row
	 * @param option
	 * @author 73289
	 */
	private void deletedConjuntoPlaza(int row, String option) {
		try {
			u.hardWait(2);
			By deletedImg = By.cssSelector(".tableContainer > table > tbody:nth-child(2) > tr.tablaDatos:nth-child("
					+ (row) + ") > td:nth-child(1) > img:nth-child(1)");
			u.highLight(deletedImg);
			u.click(deletedImg);
			u.hardWait(2);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se encontr� el bot�n eliminar");
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

	/**
	 * Busca en la tabla el conjunto y c�digo correspondientes.
	 * 
	 * @param conjunto
	 * @param code
	 * @return String[]; 0: true si se encontr� el registro, false si no se
	 *         encontr�. String[1]: fila en la que se encontr� el registro.
	 * @author 73289
	 */
	private String[] searchInTable(String conjunto, String code) {
		String numRow = "";
		String conjElement = "";
		String codeElement = "";
		String[] results = new String[2];
		results[0] = "false";
		results[1] = "";
		try {
			u.hardWait(3);
			List<WebElement> elementsTable = u.getDriver()
					.findElements(By.cssSelector(".tableContainer > table > tbody:nth-child(2) > tr.tablaDatos"));
			System.out.println("Elementos en la Tabla: " + elementsTable.size());
			if (elementsTable.size() == 0) {
				u.hardWait(2);
				elementsTable = u.getDriver()
						.findElements(By.cssSelector(".tableContainer > table > tbody:nth-child(2) > tr.tablaDatos"));
			}
			for (int i = 0; i < elementsTable.size(); i++) {
				String element = elementsTable.get(i).getAttribute("innerText").toUpperCase().replaceAll("\\s", "");
				System.out.println("Fila " + i + ": " + element);
				if (element.equalsIgnoreCase(conjunto + code)) {
					System.out.println("SI ESTA! " + element + " = " + conjunto + "/" + code);
					numRow = Integer.toString(i + 1);
					conjElement = ".tableContainer > table > tbody:nth-child(2) > tr.tablaDatos:nth-child(" + (i + 1)
							+ ") > td:nth-child(1)";
					codeElement = ".tableContainer > table > tbody:nth-child(2) > tr.tablaDatos:nth-child(" + (i + 1)
							+ ") > td:nth-child(2)";
					u.highLight(By.cssSelector(codeElement));
					u.highLight(By.cssSelector(conjElement));
					u.hardWait(1);
					WebElement m = u.getDriver().findElement(By.cssSelector(conjElement));
					((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView(true);", m);
					results[0] = "true";
					results[1] = numRow;
					break;
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
		return "TC_000_ConfiguracionDePlazaPlazaAsignacionDePlazas";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Esmeralda Garcia Torres";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_000_ConfiguracionDePlazaPlazaAsignacionDePlazas";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
