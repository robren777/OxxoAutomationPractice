package WorkFlows.util.Siv;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.TestCase;
import utils.ExcelUtil;
import utils.selenium.SeleniumUtil;

public class pomSIV {
	private SeleniumUtil u;
	private TestCase testCase;
	private WebDriverWait wait;

	/// LOADING MODAL ///
	By loadingModal = By.id("loadingModal");

	/// POPUP MODAL ///
	By popupModal = By.cssSelector(".modal-content");
	By buttonOKmodal = By.cssSelector(".modal-footer > button[name=\"dismisSeleneiumOK\"]");
	By buttonAceptar2Modal = By.cssSelector(".modal-footer > button[value=\"Aceptar\"]");
	By buttonSalir2Modal = By.cssSelector(".modal-footer > button[value=\"Salir\"]");
	By buttonSiAceptarRModal = By.cssSelector("div.ng-scope:nth-child(3) > button:nth-child(1)");

	/// WEB ELEMENTS MODAL - Solo tabla ///
	By tableModal = By.cssSelector(".modal-body > table.table > tbody > tr");
	By buttonAceptarModal = By.cssSelector("button[name=\"btnOkModalSearchSelenium\"]");
	By buttonCancelarModal = By.cssSelector("button[name=\"btnCancelModalSearchSelenium\"]");
	By inputModal = By.cssSelector("input.ng-valid:nth-child(2)");
	By imgBuscarModal = By.cssSelector("form.ng-valid > a:nth-child(3) > img:nth-child(1)");

	/// WEB ELEMENTS MODAL 2 - Con select e input al inicio///
	By inputDescripcionModal_2 = By.id("textSearch");
	By imgBuscarDescripcionModal_2 = By.id("ahreftextSearch");
	By tableModal_2 = By.cssSelector("table[name=\"tableSearchWithFiltersReutilizable\"] > tbody > tr");
	By buttonAceptarModal_2 = By.id("btnOkModalSearchWithFiltersReutilizableSelenium");

	/// MODAL DE RECHAZO
	By selectRechazoModal = By.id("motivoRechazoExistente");
	By buttonMoverModal = By.cssSelector("input[value=\">>\"]");
	By buttonSiModal = By.cssSelector(".modal-footer > button:nth-child(1)");
	By buttonAceptarFAModal = By.cssSelector(".modal-lg > div:nth-child(1) > div:nth-child(3) > button:nth-child(1)");

	/**
	 * Constructor. Recibe el SeleniumUtil utilizado en el test.
	 * 
	 * @param SeleniumUtil
	 * @author Esmeralda Garcia Torres
	 */
	public pomSIV(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
	}

	/**
	 * Reconoce el modal de procesando en el portal de SIV y espera a que
	 * desaparezca.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void waitLoadingModal() {
		try {
			u.hardWait(1);
			int cont = 0;
			while (u.getDriver().findElement(loadingModal).isDisplayed()) {
				u.hardWait(2);
				cont = cont + 2;
			}
			if (cont != 0) {
				logM("loading for: " + cont + " Seconds");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * Espera a que el elemento se muestre, lo resalta y le da click.
	 *
	 * @param opcion    -> Nombre del boton
	 * @param elementBy -> By Element
	 * @author Esmeralda Garcia Torres
	 */
	public void clickOpcion(String opcion, By elementBy) {
		try {
			waitLoadingModal();
			scrollToElement(elementBy);
			wait.until(ExpectedConditions.elementToBeClickable(elementBy));
			u.highLight(elementBy);
			testCase.addScreenShotCurrentStep(u, "Dar clic en " + opcion);
			u.unhighLight(elementBy);
			u.click(elementBy);
			u.hardWait(1);
			waitLoadingModal();
		} catch (Exception e) {
			logL("" + e);
			throw new AssertionError("Fallo al dar clic en: " + opcion);
		}
	}

	/**
	 * Espera a que el elemento se muestre, limpia el campo e ingresa el valor
	 * esperado.
	 * @param opcion    -> Nombre del campo
	 * @param valor     -> Valor a ingresar
	 * @param elementBy -> By Input
	 * @author Esmeralda Garcia Torres
	 */
	public void enterOption(String opcion, String valor, By elementBy) {
		try {
			waitLoadingModal();
			wait.until(ExpectedConditions.elementToBeClickable(elementBy));
			clearInput(elementBy);
			u.sendKeys(elementBy, valor);
			u.hardWait(1);
		} catch (Exception e) {
			logL("" + e);
			throw new AssertionError("Fallo al ingresar en el campo: " + opcion);
		}
	}

	/**
	 * Selecciona en el combo el valor esperado.
	 * @param opcion    -> Nombre del select
	 * @param valor     -> Valor a seleccionar
	 * @param elementBy -> By Select
	 * @author Esmeralda Garcia Torres
	 */
	public void selectOption(String opcion, String valor, By elementBy) {
		try {
			waitLoadingModal();
			Select select = new Select(u.getDriver().findElement(elementBy));
			select.selectByVisibleText(valor);
			u.hardWait(1);
		} catch (Exception e) {
			logL("" + e);
			throw new AssertionError("Fallo al seleccionar: " + opcion);
		}
	}

	/**

	 * Busca el valor esperado en el modal que se muestra al dar click en la opcion
	 * buscar del campo en cuestion. Esta opcion realiza la busqueda cuando el modal
	 * solo muestra una tabla.
	 * @param opcion    -> Nombre del campo
	 * @param valor     -> Valor a seleccionar
	 * @param imgSearch -> By Boton buscar
	 * @author Esmeralda Garcia Torres
	 */
	public void buscarOpcionModal_1(String opcion, String valor, By imgSearch) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(imgSearch));
			u.click(imgSearch);
			waitLoadingModal();
			searchTableModal(valor);
		} catch (Exception e) {
			logL("" + e);
			throw new AssertionError("Fallo al buscar en el modal: " + opcion);
		}
	}

	/**

	 * Busca el valor esperado en el modal que se muestra al dar click en la opcion
	 * buscar del campo en cuestion. Esta opcion realiza la busqueda cuando el modal
	 * muestra un select y un campo de descripcion.
	 * @param opcion        -> Nombre del campo
	 * @param imgSearch     -> By Boton buscar
	 * @param selectElement -> By Select del modal
	 * @param selectValor   -> Valor a seleccionar del Select
	 * @param valor         -> Valor a seleccionar
	 * @author Esmeralda Garcia Torres
	 */
	public void buscarOpcionModal_2(String opcion, By imgSearch, By selectElement, String selectValor, String valor) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(imgSearch));
			u.click(imgSearch);
			waitLoadingModal();
			searchTableModal_2(selectElement, selectValor, valor);
		} catch (Exception e) {
			logL("" + e);
			throw new AssertionError("Fallo al buscar en el modal: " + opcion);

		}
	}

	/**
	 * Busca y selecciona el texto esperado en la tabla que se muestra en el modal,
	 * cuando solo muestra una tabla.
	 * 
	 * @param texto
	 * @author Esmeralda Garcia Torres
	 */
	private void searchTableModal(String texto) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(inputModal));
			u.clear(inputModal);
			u.sendKeys(inputModal, texto);
			u.hardWait(1);
			u.click(imgBuscarModal);
			u.hardWait(1);

			Integer row = 0;
			List<WebElement> elementsTable = u.getDriver().findElements(tableModal);
			if (elementsTable.size() == 0) {
				u.hardWait(2);
			}
			logM("Elementos en la Tabla: " + elementsTable.size());
			for (int i = 0; i < elementsTable.size(); i++) {
				String element = elementsTable.get(i).getAttribute("innerText").toUpperCase();
				logM(element + " : " + texto);
				if (element.toUpperCase().contains(texto.toUpperCase())) {
					row = i + 1;
					logM("Se encontro en row: " + row);
					break;
				}
			}
			if (row != 0) {
				By selectElement = By
						.cssSelector(".modal-body > table.table > tbody > tr:nth-child(" + row + ") > td:nth-child(1)");
				u.highLight(selectElement);
				u.click(selectElement);
				u.hardWait(1);

				u.click(buttonAceptarModal);
				u.hardWait(2);
			} else {

				throw new AssertionError("No se encontro coincidencia en la tabla");
			}
		} catch (Exception e) {
			logL("" + e);
			throw new AssertionError("Fallo al buscar en la tabla");
		}
	}

	/**
	 * Selecciona del modal el valor esperado en el combo para despues buscar y
	 * selecciona el texto esperado en la tabla.
	 * 
	 * @param selectBy    -> By Select del modal
	 * @param selectValor -> Valor a seleccionar
	 * @param texto       -> Texto a buscar en la tabla.
	 * @author Esmeralda Garcia Torres
	 */
	private void searchTableModal_2(By selectBy, String selectValor, String texto) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(selectBy));
			Select select = new Select(u.getDriver().findElement(selectBy));
			select.selectByVisibleText(selectValor);
			u.hardWait(1);
			waitLoadingModal();

			wait.until(ExpectedConditions.elementToBeClickable(inputDescripcionModal_2));
			u.clear(inputDescripcionModal_2);
			u.sendKeys(inputDescripcionModal_2, texto);
			u.hardWait(1);
			u.click(imgBuscarDescripcionModal_2);
			u.hardWait(1);

			Integer row = 0;
			List<WebElement> elementsTable = u.getDriver().findElements(tableModal_2);
			if (elementsTable.size() == 0) {
				u.hardWait(2);
			}
			logM("Elementos en la Tabla: " + elementsTable.size());
			for (int i = 0; i < elementsTable.size(); i++) {
				String element = elementsTable.get(i).getAttribute("innerText").toUpperCase();
				logM(element + " : " + texto);
				if (element.toUpperCase().contains(texto.toUpperCase())) {
					row = i + 1;
					logM("Se encontro en row: " + row);
					break;
				}
			}
			if (row != 0) {
				u.hardWait(1);
				By selectElementTable = By
						.cssSelector("table[name=\"tableSearchWithFiltersReutilizable\"] > tbody > tr:nth-child(" + row
								+ ") > td:nth-child(1)");
				u.highLight(selectElementTable);
				u.click(selectElementTable);
				u.hardWait(1);

				u.click(buttonAceptarModal_2);
				u.hardWait(2);
			} else {

				throw new AssertionError("No se encontro coincidencia en la tabla");
			}
		} catch (Exception e) {
			logL("" + e);
			throw new AssertionError("Fallo al buscar en la tabla");
		}
	}

	/**
	 * Regresa el texto mostrado en el Modal PopUp
	 * 
	 * @return String
	 * @author Esmeralda Garcia Torres
	 */
	public String msgModal() {
		String textModal = "";
		try {
			u.hardWait(2);
			waitLoadingModal();
			textModal = u.getDriver().findElement(By.cssSelector("div.modal-body:nth-child(2)"))
					.getAttribute("innerText");
			// System.out.println(" - Texto en Modal: " + textModal);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return textModal;
	}

	/**
	 * Selecciona del PopUp Modal la opcion esperada.
	 * 
	 * @param opcion -> OK
	 * @author Esmeralda Garcia Torres
	 */
	public void optionMsgModal(String opcion) {
		try {
			switch (opcion.toUpperCase()) {
			case "OK":
				clickOpcion("OK", buttonOKmodal);
				break;
			case "ACEPTAR":
				clickOpcion("Aceptar", buttonAceptar2Modal);
				break;
			case "SALIR":
				clickOpcion("Salir", buttonSalir2Modal);
				break;
			case "SI RECHAZO":
				clickOpcion("Si", buttonSiAceptarRModal);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void msgEjecucion() {
		try {
			waitLoadingModal();
			scrollToElement(buttonSalir2Modal);
			wait.until(ExpectedConditions.elementToBeClickable(buttonSalir2Modal));
			u.highLight(buttonSalir2Modal);
			testCase.addScreenShotCurrentStep(u, "Dar clic en Salir");
			u.unhighLight(buttonSalir2Modal);
			u.click(buttonSalir2Modal);
			u.hardWait(1);
			waitLoadingModal();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * Limpia un input seleccionando el texto que tiene y borrandolo.
	 * 
	 * @param inputBy
	 * @author Esmeralda Garcia Torres
	 */
	public void clearInput(By inputBy) {
		try {
			WebElement toClear = u.getDriver().findElement(inputBy);
			toClear.sendKeys(Keys.CONTROL + "a");
			toClear.sendKeys(Keys.DELETE);
		} catch (Exception e) {
			logL("" + e);
		}
	}

	/**
	 * Extrae el valor que se muestra dentro de un input.
	 * 
	 * @param inputBy
	 * @return String valor
	 * @author Esmeralda Garcia Torres
	 */
	public String getDataInput(By inputBy) {
		// wait.until(ExpectedConditions.elementToBeClickable(inputBy));
		String result = "";
		try {
			result = u.getDriver().findElement(inputBy).getAttribute("value");
			logM("Se obtuvo: " + result);
		} catch (Exception e) {

			logL("" + e);
			throw new AssertionError("Fallo al obtener el valor");
		}
		return result;
	}

	/**
	 * Extrae el atributo value de un select
	 * 
	 * @param selectBy
	 * @return String value
	 * @author Esmeralda Garcia Torres
	 */
	public String getDataSelect(By selectBy) {
		String result = "";
		try {
			result = u.getDriver().findElement(selectBy).getAttribute("value");
			// System.out.println("Se obtuvo: " + result);
		} catch (Exception e) {
			logL("" + e);
			throw new AssertionError("Fallo al obtener el valor");
		}
		return result;
	}

	/**
	 * Genera un codigo de barras aleatorio.
	 * 
	 * @return String cb
	 * @author Esmeralda Garcia Torres
	 */
	public String generacionCB() {
		String cb = "";
		Integer[][] matriz = new Integer[2][12];
		matriz[0][0] = 1;
		matriz[0][1] = 3;
		matriz[0][2] = 1;
		matriz[0][3] = 3;
		matriz[0][4] = 1;
		matriz[0][5] = 3;
		matriz[0][6] = 1;
		matriz[0][7] = 3;
		matriz[0][8] = 1;
		matriz[0][9] = 3;
		matriz[0][10] = 1;
		matriz[0][11] = 3;
		Double suma = 0.0;
		Double aux = 0.0;
		Double aux2 = 0.0;
		Double ultimo = 0.0;
		try {
			Random r = new Random();
			for (int i = 0; i < 12; i++) {
				matriz[1][i] = r.nextInt(10);
				suma = suma + (matriz[0][i] * matriz[1][i]);
				cb = cb + Integer.toString(matriz[1][i]);
				//logM(matriz[0][i] + " " + matriz[1][i]);
			}
			aux = suma / 10;
			aux2 = Math.ceil(aux);
			//logM("Suma: " + suma + " | /10: " + aux + " | ceil: " + aux2);
			ultimo = (aux2 - aux) * 10;
			//logM("Ultimo digito: " + ultimo);
			Long aux3 = Math.round(ultimo);
			ultimo = aux3.doubleValue();
			cb = cb + ultimo.intValue();
			logM("CB: " + cb);
		} catch (Exception e) {

			logL("" + e);
			throw new AssertionError("Fallo al generar el CB random");

		}
		return cb;
	}

	/**

	 * Agrega un dato en la primera celda vacia de la columna elegida.
	 * 
	 * @param path   -> Ruta del archivo.xlsx
	 * @param sheet  -> Hoja
	 * @param column -> Base 0
	 * @param data   -> Valor a guardar
	 * @author Esmeralda Garcia Torres
	 */
	public void addDataToDataProviderFile(String path, String sheet, int column, String data) {
		try {
			ExcelUtil dataProvider = new ExcelUtil(path, false);
			Object[][] dataExcel = dataProvider.getRowsObject(sheet);
			if (dataExcel.length > 0) {
				dataProvider.open();
				int sheetNum = dataProvider.getWorkbook().getSheetIndex(sheet);
				for (int i = 1; i <= dataExcel.length; i++) {
					try {
						String celTextString = dataProvider.getWorkbook().getSheetAt(sheetNum).getRow(i).getCell(column)
								.getStringCellValue();
						logM("Hay: " + celTextString);
						if (celTextString.equalsIgnoreCase("")) {
							dataProvider.setCell(sheet, i, column, data);
							logM("Se guardo en Try: " + data + " en la fila: " + i + " en la columna: " + column);
						}
					} catch (Exception e) {
						dataProvider.setCell(sheet, i, column, data);
						logM("Se guardo en catch: " + data + " en la fila: " + i + " en la columna: " + column);
						break;
					}
				}
				dataProvider.close();
			} else {
				throw new AssertionError("El archivo de excel no tiene filas");
			}
		} catch (Exception e) {

			logL("" + e);
			throw new AssertionError("Fallo al abrir el archivo de excel");

		}
	}

	public void scrollToElement(By ByElement) {
		try {
			WebElement element = u.getDriver().findElement(ByElement);
			Coordinates coordinate = ((Locatable) element).getCoordinates();
			coordinate.onPage();
			coordinate.inViewPort();
		} catch (Exception e) {
			try {
				WebElement m = u.getDriver().findElement(ByElement);
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView(true);", m);
			} catch (Exception e2) {
				logL("No se pudo scrollear");
			}
		}

	}

	public void msgRechazar(String modulo, String opcion) {
		try {
			selectOption("Rechazar", opcion, selectRechazoModal);
			u.click(buttonMoverModal);
			if (modulo.equalsIgnoreCase("Datos Basicos")) {
				clickOpcion("Aceptar Rechazo", buttonSiModal);
				optionMsgModal("OK");
			} else if (modulo.equalsIgnoreCase("FA")) {
				clickOpcion("Aceptar Rechazo", buttonAceptarFAModal);
				optionMsgModal("OK");
			} else if (modulo.equalsIgnoreCase("Conjuntos")) {
				clickOpcion("Aceptar Rechazo", buttonAceptarFAModal);
				if (msgModal().contains("correctamente")) {
					optionMsgModal("OK");
				} else {
					throw new AssertionError("Fallo al rechazar");
				}
			}

		} catch (Exception e) {

			logL("" + e);
			throw new AssertionError("Fallo al ingresar las opciones del rechazo");
		}
	}

	/**
	 * Espera a que el elemento tenga un valor valido
	 * @param input
	 * @return El valor del campo
	 * @author Isaac Garcia
	 */
	public String waitInputValue(By input) {
		WebElement inputElement = wait.until(driver -> {
			WebElement element = u.getDriver().findElement(input);
			String value = element.getAttribute("value");
			return (value != null && !value.isEmpty()) ? element : null;
		});

		return inputElement.getAttribute("value");
	}
	
	public void logL(String text) {
		String code = "+++> ";
		System.out.println(code + text);
	}

	public void logM(String text) {
		String code = "+> ";
		//String code = "+++++>  ";
		System.out.println(code + text);
	}

}
