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

public class TC_000_ConfiguracionPlazaPlaza_CatalogoABCorreo extends BaseExecution {
	private SeleniumUtil u;
	private pomSembradoALM util;
	private WebDriverWait wait;

	/// CATALOGO AB CORREO WEB ELEMENTS AB
	private By locationNombreInput = By.id("nombre");
	private By locationCorreoInput = By.id("correo");
	private By locationPlazaSelect = By.id("plaza");
	private By locationAgregarButton = By
			.cssSelector("tr.tablaDatos:nth-child(6) > td:nth-child(1) > table:nth-child(1)"
					+ " > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > input:nth-child(1)");
	private By locationCancelarButton = By
			.cssSelector("tr.tablaDatos:nth-child(6) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > "
					+ "tr:nth-child(1) > td:nth-child(4) > input:nth-child(1)");
	private By locationActualizarButton = By
			.cssSelector("tr.tablaDatos:nth-child(6) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) "
					+ "> tr:nth-child(1) > td:nth-child(2) > input:nth-child(1)");

	@Test(dataProvider = "data-provider")

	public void ATC_FT_000_ConfiguracionPlazaPlaza_CatalogoABCorreo(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Framework WorkFlows");

		/// GET DATA FROM INPUT FILE ///
		String accion = data.get("accion");
		String nombre = data.get("nombre");
		String email = data.get("email");
		String plaza = data.get("plaza");
		String code = data.get("codigo");
		String newNombre = data.get("newNombre");
		String newEmail = data.get("newEmail");
		String newPlaza = data.get("newPlaza");
		System.out.println("Nombre: " + nombre + " Email: " + email + " Plaza: " + plaza);

		/// REVISION OF MANDATORY DATA IN THE INPUT FILE ///

		switch (accion.toUpperCase()) {
		case "EDITAR":
			if ((newNombre.equals("")) || (newEmail.equals("")) || (newPlaza.equals(""))) {
				testCase.failStep("Faltan datos mandatorios en el archivo de entrada");
			}
		case "AGREGAR":
		case "ELIMINAR":
			if ((nombre.equals("")) || (email.equals("")) || (plaza.equals("")) || (code.equals(""))) {
				testCase.failStep("Faltan datos mandatorios en el archivo de entrada");
			}
			break;
		default:
			throw new AssertionError("La opci�n de la accion no es v�lida");
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

		/// SELECT CATALOGO AB CORREO OPTION ///
		addStep("Ingresar a Cat�logo AB Correo");
		util.goToCatalogoABcorreo();
		testCase.addScreenShotCurrentStep(u, "Catalogo AB correo");

		/// ACCIONES ///
		String[] resultTable;
		switch (accion.toUpperCase()) {
		case "AGREGAR": // ---------------------------------------------------------------------------------------------
						// AGREGAR
			addStep("Ingresar datos");
			testCase.addTextEvidenceCurrentStep(
					"Nombre: \"" + nombre + "\" - Email: \"" + email + "\" - Plaza: \"" + plaza + "\"");
			enterDataCatalogo(nombre, email, plaza);
			testCase.addScreenShotCurrentStep(u, "Datos ingresados");

			addStep("Agregar AB correo");
			addCatalogoAB();
			String resultText = util.getTextDialogBox();
			if (resultText.contains("con exito")) {
				testCase.addTextEvidenceCurrentStep("Se agreg� exitosamente");
				testCase.addScreenShotCurrentStep(u, "Se agreg� AB Correo");
				util.selectOptionDialogBox("aceptar");
			} else if (resultText.contains("requerido")) {
				testCase.failStep("Falt� agregar un dato obligatorio");
			} else {
				testCase.failStep("Agregar AB Correo: no se reconoce el texto del cuadro de di�logo");
			}
			break;
		case "EDITAR": // ----------------------------------------------------------------------------------------------
						// EDITAR
			addStep("Buscar en la tabla el registro a Editar");
			testCase.addTextEvidenceCurrentStep(
					"Nombre: \"" + nombre + "\" - Email: \"" + email + "\" - C�digo: \"" + code + "\"");
			resultTable = searchInTable(nombre, email, code);
			if (Boolean.parseBoolean(resultTable[0])) {
				testCase.addScreenShotCurrentStep(u, "Registro encontrado en la tabla");
			} else {
				testCase.failStep("No se encontr� el registro en la tabla");
			}

			addStep("Editar los campos y cancelar");
			editCatalogoAB(Integer.parseInt(resultTable[1]));
			testCase.addTextEvidenceCurrentStep("Datos actuales");
			testCase.addScreenShotCurrentStep(u, "Datos actuales-cancelar");
			enterDataCatalogo(newNombre, newEmail, newPlaza);
			testCase.addTextEvidenceCurrentStep("Datos nuevos");
			testCase.addScreenShotCurrentStep(u, "Datos nuevos-cancelar");
			cancelEditCatalogoAB();
			testCase.addTextEvidenceCurrentStep("Al cancelar no se actualiza el registro");
			testCase.addScreenShotCurrentStep(u, "Cambios cancelados");

			addStep("Editar los campos y actualizar");
			editCatalogoAB(Integer.parseInt(resultTable[1]));
			testCase.addTextEvidenceCurrentStep("Datos actuales");
			testCase.addScreenShotCurrentStep(u, "Datos actuales-actualizar");
			enterDataCatalogo(newNombre, newEmail, newPlaza);
			testCase.addTextEvidenceCurrentStep("Datos nuevos");
			testCase.addScreenShotCurrentStep(u, "Datos nuevos-actualizar");
			updateCatalogoAB();
			util.getTextDialogBox();
			testCase.addTextEvidenceCurrentStep("Confirmacion");
			testCase.addScreenShotCurrentStep(u, "Datos Actualizados");
			util.selectOptionDialogBox("aceptar");
			break;
		case "ELIMINAR": // --------------------------------------------------------------------------------------------
							// ELIMINAR
			addStep("Buscar en la tabla el registro a Eliminar");
			testCase.addTextEvidenceCurrentStep(
					"Nombre: \"" + nombre + "\" - Email: \"" + email + "\" - C�digo: \"" + code + "\"");
			resultTable = searchInTable(nombre, email, code);
			if (Boolean.parseBoolean(resultTable[0])) {
				testCase.addScreenShotCurrentStep(u, "Registro encontrado en la tabla");
			} else {
				testCase.failStep("No se encontr� el registro en la tabla");
			}

			addStep("Eliminar pero en la confirmacion seleccionar \"No\"");
			deleteCatalogoAB(Integer.parseInt(resultTable[1]), "no");

			addStep("Eliminar pero en la confirmacion seleccionar \"Salir\"");
			deleteCatalogoAB(Integer.parseInt(resultTable[1]), "salir");

			addStep("Eliminar pero en la confirmacion seleccionar \"Si\"");
			deleteCatalogoAB(Integer.parseInt(resultTable[1]), "si");
			util.selectOptionDialogBox("aceptar");
			break;
		default:
			throw new AssertionError("La opci�n de la accion no es v�lida");
		}
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Ingresa los datos en los campos correspondientes para agregar un registro.
	 * 
	 * @param nombre
	 * @param email
	 * @param plaza
	 * @author 73289
	 */
	public void enterDataCatalogo(String nombre, String email, String plaza) {
		try {
			u.hardWait(4);
			wait.until(ExpectedConditions.elementToBeClickable(locationNombreInput));
			u.clear(locationNombreInput);
			u.sendKeys(locationNombreInput, nombre);

			wait.until(ExpectedConditions.elementToBeClickable(locationCorreoInput));
			u.clear(locationCorreoInput);
			u.sendKeys(locationCorreoInput, email);

			wait.until(ExpectedConditions.elementToBeClickable(locationPlazaSelect));
			Select plazaSelect = new Select(u.getDriver().findElement(locationPlazaSelect));
			plazaSelect.selectByVisibleText(plaza);

		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se pudo ingresar alguna de las opciones");
		}
	}

	/**
	 * Da clic en "Agregar".
	 * 
	 * @author 73289
	 */
	public void addCatalogoAB() {
		try {
			u.highLight(locationAgregarButton);
			u.hardWait(1);
			u.click(locationAgregarButton);
			u.hardWait(1);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se encontr� el bot�n \"Agregar\"");
		}
	}

	/**
	 * Selecciona el icono de "Editar" del registro correspondiente.
	 * 
	 * @param row
	 * @author 73289
	 */
	public void editCatalogoAB(int row) {
		try {
			By editOption = By
					.cssSelector("#tblCatalogo > tr:nth-child(" + row + ") > td:nth-child(1) > img:nth-child(1)");
			u.highLight(editOption);
			u.click(editOption);
			u.hardWait(1);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se encontr� el bot�n \"Editar\"");
		}
	}

	/**
	 * Da clic en "Cancelar" cuando se est� en edici�n.
	 * 
	 * @author 73289
	 */
	public void cancelEditCatalogoAB() {
		try {
			u.highLight(locationCancelarButton);
			u.unhighLight(locationCancelarButton);
			u.click(locationCancelarButton);
			u.hardWait(1);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se encontr� el bot�n \"Cancelar\"");
		}
	}

	/**
	 * Da clic en "Actualizar" cuando se est� en edici�n.
	 * 
	 * @author 73289
	 */
	public void updateCatalogoAB() {
		try {
			u.highLight(locationActualizarButton);
			u.click(locationActualizarButton);
			u.unhighLight(locationActualizarButton);
			u.hardWait(1);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se encontr� el bot�n \"Actualizar\"");
		}
	}

	/**
	 * Selecciona el icono de "Eliminar" del registro correspondiente y selecciona
	 * la opci�n esperada en la confirmaci�n.
	 * 
	 * @param row
	 * @param option
	 * @author 73289
	 */
	public void deleteCatalogoAB(int row, String option) {
		try {
			By deleteOption = By
					.cssSelector("#tblCatalogo > tr:nth-child(" + row + ") > td:nth-child(2) > img:nth-child(1)");
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

	/**
	 * Busca en la tabla el registro esperado.
	 * 
	 * @param nombre
	 * @param email
	 * @param code
	 * @return String[]; 0: true si se encontr� el registro, false si no se
	 *         encontr�. String[1]: fila en la que se encontr� el registro.
	 * @author 73289
	 */
	public String[] searchInTable(String nombre, String email, String code) {
		String[] results = new String[2];
		results[0] = "false";
		results[1] = "";
		try {
			u.hardWait(4);
			List<WebElement> elementsTable = u.getDriver().findElements(By.cssSelector("#tblCatalogo > tr"));
			System.out.println("Elementos en la Tabla: " + elementsTable.size());
			for (int i = 0; i < elementsTable.size(); i++) {
				String element = elementsTable.get(i).getAttribute("innerText").toUpperCase();
				String[] dato = element.split("\t");
				String elementName = dato[2];
				String elementEmail = dato[3];
				String elementCode = dato[4];
				System.out.println("Fila :" + i + " | Nombre: " + elementName + " | Email: " + elementEmail.toString()
						+ " | Code: " + elementCode.toString());
				if (elementName.toString().equalsIgnoreCase(nombre)) {
					System.out.println("Se encontr� el nombre = " + nombre);
					By hName = By.cssSelector("#tblCatalogo > tr:nth-child(" + (i + 1) + ") > td:nth-child(3)");
					if (elementEmail.toString().equalsIgnoreCase(email)) {
						System.out.println("Se encontr� el email = " + email);
						By hEmail = By.cssSelector("#tblCatalogo > tr:nth-child(" + (i + 1) + ") > td:nth-child(4)");
						WebElement m = u.getDriver().findElement(hName);
						((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView(true);", m);
						if (elementCode.toString().equalsIgnoreCase(code)) {
							System.out.println("Se encontr� el c�digo de plaza! = " + code);
							By hCode = By.cssSelector("#tblCatalogo > tr:nth-child(" + (i + 1) + ") > td:nth-child(5)");
							u.highLight(hCode);
							u.highLight(hEmail);
							u.highLight(hName);
							wait.until(ExpectedConditions.elementToBeClickable(hName));
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
		return "TC_000_ConfiguracionPlazaPlaza_CatalogoABCorreo";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Esmeralda Garcia Torres";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_000_ConfiguracionPlazaPlaza_CatalogoABCorreo";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
