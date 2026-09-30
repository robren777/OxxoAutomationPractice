package WorkFlows.util.TestExample;

import java.time.Duration;
import java.util.List;

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
import utils.selenium.SeleniumUtil;

public class pomTestExample {
	private SeleniumUtil u;
	private TestCase testCase;
	private WebDriverWait wait;

	public pomTestExample(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
	}

	/**
	 * Log Principal: imprime en consola.
	 * 
	 * @param text
	 * @author Esmeralda Garcia Torres
	 */
	public void logP(String text) {
		String code = "+++>  ";
		System.out.println(code + text);
	}

	/**
	 * Log Secundario: imprime en consola.
	 * 
	 * @param text
	 * @author Esmeralda Garcia Torres
	 */
	public void logS(String text) {
		String code = "+++++>  ";
		System.out.println(code + text);
	}

	/**
	 * Espera a que el elemento se muestre, lo resalta, toma un screenshot, y le da
	 * click.
	 * 
	 * @param name      -> Nombre del boton
	 * @param elementBy -> By Element
	 * @author Esmeralda Garcia Torres
	 */
	public void clickOpcion(String name, By elementBy) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(elementBy));
			u.highLight(elementBy);
			testCase.addScreenShotCurrentStep(u, "Dar clic en " + name);
			u.unhighLight(elementBy);
			u.click(elementBy);
			u.hardWait(1);
		} catch (Exception e) {
			logS("" + e);
			throw new AssertionError("Fallo al dar clic en: " + name);
		}
	}

	/**
	 * Espera a que el elemento se muestre, limpia el campo e ingresa el valor
	 * esperado.
	 * 
	 * @param name      -> Nombre del campo
	 * @param value     -> Valor a ingresar
	 * @param elementBy -> By Input
	 * @author Esmeralda Garcia Torres
	 */
	public void enterOption(String name, String value, By elementBy) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(elementBy));
			clearInput(elementBy);
			u.sendKeys(elementBy, value);
			u.hardWait(1);
		} catch (Exception e) {
			logS("" + e);
			throw new AssertionError("Fallo al ingresar en el campo: " + name);
		}
	}

	/**
	 * Selecciona en el select el valor esperado.
	 * 
	 * @param name      -> Nombre del select
	 * @param value     -> Valor a seleccionar
	 * @param elementBy -> By Select
	 * @author Esmeralda Garcia Torres
	 */
	public void selectOption(String name, String value, By elementBy) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(elementBy));
			Select select = new Select(u.getDriver().findElement(elementBy));
			select.selectByVisibleText(value);
			u.hardWait(1);
		} catch (Exception e) {
			logS("" + e);
			throw new AssertionError("Fallo al seleccionar: " + name);
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
			logS("Fallo al limpiar el campo: " + e);
		}
	}

	/**
	 * Muestra el elemento en la pantalla visible.
	 * 
	 * @param ByElement
	 * @author Esmeralda Garcia Torres
	 */
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
				logS("No se pudo scrollear: " + e);
			}
		}
	}

	/**
	 * Realiza la busqueda de un dato dentro de una tabla. Si en las columnas de un
	 * registro se encuentra el dato solicitado regresa un String[] en donde la
	 * posicion 0 es la fila en que se encontro y la posicion 1 es el innerText de
	 * la fila.
	 * 
	 * @param tableName -> Nombre de la tabla
	 * @param tableBy
	 * @param data      -> Dato a buscar
	 * @return String[]
	 * @author Esmeralda Garcia Torres
	 */
	public String[] searchInTableGeneral(String tableName, By tableBy, String data) {
		String[] results = new String[2];
		results[0] = "";
		results[1] = "";
		try {
			Boolean aux = false;
			Integer row = 0;
			List<WebElement> elementsTable = u.getDriver().findElements(tableBy);
			if (elementsTable.size() == 0) {
				u.hardWait(2);
			}
			logS("Elementos en la Tabla " + tableName + ": " + elementsTable.size());
			for (int i = 0; i < elementsTable.size(); i++) {
				String element = elementsTable.get(i).getAttribute("innerText").toUpperCase();
				logS("Row: " + i + " -> " + element + " : " + data);
				if (element.toUpperCase().contains(data.toUpperCase())) {
					row = i + 1;
					results[0] = Integer.toString(row);
					results[1] = element;
					logS("Se encontr� en row: " + row);
					aux = true;
					break;
				}
			}
			if (!aux) {
				throw new AssertionError("No se encontro coincidencia en la tabla");
			}
		} catch (Exception e) {
			logS("" + e);
			throw new AssertionError("Fallo al buscar en la tabla");
		}
		return results;
	}

	/**
	 * Selecciona o Deselecciona un checkBox
	 * 
	 * @param name    -> Nombre del checkbox
	 * @param checkBy -> By element
	 * @param value   -> True or False
	 * @author Esmeralda Garcia Torres
	 */
	public void selectCheck(String name, By checkBy, Boolean value) {
		try {
			if (u.getDriver().findElement(checkBy).isEnabled()) {
				if (value && !u.getDriver().findElement(checkBy).isSelected()) {
					u.click(checkBy);
				} else if (!value && u.getDriver().findElement(checkBy).isSelected()) {
					u.click(checkBy);
				}
			}
		} catch (Exception e) {
			logS("" + e);
			throw new AssertionError("Fallo al seleccionar: \"" + value + "\" en el checkbox: " + name);
		}
	}

}