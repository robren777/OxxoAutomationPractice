package WEB.util;

import java.time.Duration;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
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

public class AuxMethods {
	private SeleniumUtil u;
	private TestCase testCase;
	private WebDriverWait wait;

	/**
	 * Constructor.
	 * 
	 * @param u
	 * @param testCase
	 * @author Esmeralda Garcia Torres
	 */
	public AuxMethods(SeleniumUtil u, TestCase testCase) {
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
	public void logS(String text) {
		String code = "+++>  ";
		System.out.println(code + text);
	}

	/**
	 * Log Secundario: imprime en consola.
	 * 
	 * @param text
	 * @author Esmeralda Garcia Torres
	 */
	public void logP(String text) {
		String code = "+++++>  ";
		System.out.println(code + text);
	}

	/**
	 * Espera a que el elemento se muestre, lo resalta, toma un screenshot, y le da
	 * click.
	 * 
	 * @param name          -> Nombre del boton
	 * @param elementBy     -> By Element
	 * @param takeScreeShot -> true or false
	 * @author Esmeralda Garcia Torres
	 */
	public void clickOpcion(String name, By elementBy, Boolean takeScreenShot) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(elementBy));
			u.highLight(elementBy);
			if (takeScreenShot) {
				testCase.addScreenShotCurrentStep(u, "Dar clic en " + name);
			}
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
	 * @param name          -> Nombre del campo
	 * @param value         -> Valor a ingresar
	 * @param elementBy     -> By Input
	 * @param takeScreeShot -> true or false
	 * @author Esmeralda Garcia Torres
	 */
	public void enterOption(String name, String value, By elementBy, Boolean takeScreenShot) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(elementBy));
			u.highLight(elementBy);
			clearInput(elementBy);
			u.sendKeys(elementBy, value);
			u.hardWait(1);
			if (takeScreenShot) {
				testCase.addScreenShotCurrentStep(u, "Enter: " + name);
			}
			u.unhighLight(elementBy);
		} catch (Exception e) {
			logS("" + e);
			throw new AssertionError("Fallo al ingresar en el campo: " + name);
		}
	}

	/**
	 * Selecciona en el select el valor esperado.
	 * 
	 * @param name          -> Nombre del select
	 * @param value         -> Valor a seleccionar
	 * @param elementBy     -> By Select
	 * @param takeScreeShot -> true or false
	 * @author Esmeralda Garcia Torres
	 */
	public void selectOption(String name, String value, By elementBy, Boolean takeScreenShot) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(elementBy));
			u.highLight(elementBy);
			Select select = new Select(u.getDriver().findElement(elementBy));
			select.selectByVisibleText(value);
			u.hardWait(1);
			if (takeScreenShot) {
				testCase.addScreenShotCurrentStep(u, "Select: " + name);
			}
			u.unhighLight(elementBy);
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
					row = i;
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
	 * Realiza la busqueda de dos datos dentro de una fila en una tabla. Si hay
	 * coincidencia en un registro se regresa un String[] en donde la posicion 0 es
	 * la fila en que se encontro y la posicion 1 es el innerText de la fila. Si no
	 * se encontro coincidencia en la tabla regresa null en ambas posiciones.
	 * 
	 * @param tableName -> Nombre de la tabla
	 * @param tableBy
	 * @param valor1    -> Dato a buscar
	 * @param valor2    -> Segundo dato a buscar
	 * @return String[]
	 * @author Esmeralda Garcia Torres
	 */
	public String[] searchInTableGeneral(String tableName, By tableBy, String valor1, String valor2) {
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
				logS("Row: " + i + " -> " + element + " : " + valor1);
				if (!element.contains("\t")) {
					testCase.addBoldTextEvidenceCurrentStep("La tabla no mostro registros");
					logS("La tabla no mostro registros");
					aux = true;
					break;
				}
				if (element.toUpperCase().contains(valor1.toUpperCase())) {
					logS("Se encontr� primer valor");
					if (element.toUpperCase().contains(valor2.toUpperCase())) {
						row = i;
						results[0] = Integer.toString(row);
						results[1] = element;
						logS("Se encontr� segundo valor en row: " + row);
						aux = true;
						break;
					}
				}
			}
			if (!aux) {
				logS("No se encontro coincidencia en la tabla");
				testCase.addBoldTextEvidenceCurrentStep("No se encontro coincidencia en la tabla");
				results[0] = null;
				results[1] = null;
			}
		} catch (Exception e) {
			logS("" + e);
			throw new AssertionError("Fallo al buscar en la tabla");
		}
		return results;
	}

	/**
	 * Valida que todos los registros de una tabla tengan uno o dos valores
	 * especificos.
	 * 
	 * @param tableName
	 * @param tableBy
	 * @param valor1
	 * @param valor2
	 * @return true or false
	 * @author Esmeralda Garcia Torres
	 */
	public Boolean validateInTableGeneral(String tableName, By tableBy, String valor1, String valor2) {
		logS("Validando Tabla");
		Boolean aux;
		try {
			aux = false;
			Integer row = 0;
			List<WebElement> elementsTable = u.getDriver().findElements(tableBy);
			if (elementsTable.size() == 0) {
				u.hardWait(2);
			}
			logS("Elementos en la Tabla " + tableName + ": " + elementsTable.size());
			for (int i = 0; i < elementsTable.size(); i++) {
				String element = elementsTable.get(i).getAttribute("innerText").toUpperCase();
				logS("Row: " + i + " -> " + element + " : " + valor1);
				if (element.toUpperCase().contains(valor1.toUpperCase())) {
					logS("Se encontr� primer valor");
					if (element.toUpperCase().contains(valor2.toUpperCase())) {
						logS("Se encontr� segundo valor en row: " + row);
					} else {
						aux = true;
						break;
					}
				} else {
					if (!element.contains("\t")) {
						testCase.addBoldTextEvidenceCurrentStep("La tabla no mostro registros");
						logS("La tabla no mostro registros");
						aux = true;
						break;
					} else {
						logS("No hubo coincidencia en el primer valor");
						aux = false;
						break;
					}
				}
			}
			if (!aux) {
				if (elementsTable.size() == 0) {
					logS("La tabla no mostro registros");
					testCase.addBoldTextEvidenceCurrentStep("La tabla no mostro registros");
				} else {
					logS("No se encontro coincidencia");
					testCase.addBoldTextEvidenceCurrentStep("No se encontro coincidencia");
				}
			}
		} catch (Exception e) {
			logS("" + e);
			throw new AssertionError("Fallo al buscar en la tabla");
		}
		return aux;
	}

	/**
	 * Selecciona o Deselecciona un checkBox
	 * 
	 * @param name          -> Nombre del checkbox
	 * @param checkBy       -> By element
	 * @param value         -> True or False
	 * @param takeScreeShot -> true or false
	 * @author Esmeralda Garcia Torres
	 */
	public void selectCheck(String name, By checkBy, Boolean value, Boolean takeScreenShot) {
		try {
			u.highLight(checkBy);
			if (u.getDriver().findElement(checkBy).isEnabled()) {
				if (value && !u.getDriver().findElement(checkBy).isSelected()) {
					u.click(checkBy);
				} else if (!value && u.getDriver().findElement(checkBy).isSelected()) {
					u.click(checkBy);
				}
			}
			if (takeScreenShot) {
				testCase.addScreenShotCurrentStep(u, "Select Check " + name);
			}
			u.unhighLight(checkBy);
		} catch (Exception e) {
			logS("" + e);
			throw new AssertionError("Fallo al seleccionar: \"" + value + "\" en el checkbox: " + name);
		}
	}

	/**
	 * Genera un valor alfanumerico aleatorio de la longitud especificada.
	 * 
	 * @param longitud
	 * @return String
	 * @author Esmeralda Garcia Torres
	 */
	public String cadenaAleatoria(Integer longitud) {
		try {
			String randomizedString = RandomStringUtils.randomAlphanumeric(longitud);
			return randomizedString;
		} catch (Exception e) {
			logS("" + e);
			throw new AssertionError("Error al generar cadena aleatoria");
		}
	}
	
	/**
	 * Genera un valor numerico aleatorio de la longitud especificada.
	 * 
	 * @param longitud
	 * @return String
	 * @author Esmeralda Garcia Torres
	 */
	public String cadenaAleatoriaNumerica(Integer longitud) {
		try {
			String randomizedString = RandomStringUtils.randomNumeric(longitud);
			return randomizedString;
		} catch (Exception e) {
			logS("" + e);
			throw new AssertionError("Error al generar cadena aleatoria");
		}
	}

	/**
	 * Este metodo cambia el atributo "value" de un webElement tipo input. Se puede
	 * usar cuando el input no acepta el sendKeys para asignar un valor.
	 * 
	 * @param elementBy      -> Localizador del input
	 * @param value          -> Valor que se quiere ingresar
	 * @param takeScreenShot -> Si se requiere tomar una screenshot del elemento
	 * @author Esmeralda Garcia
	 */
	public void changeAttributeValue(By elementBy, String value, Boolean takeScreenShot) {
		try {
			u.highLight(elementBy);
			WebElement element = u.getDriver().findElement(elementBy);
			JavascriptExecutor jse = (JavascriptExecutor) u.getDriver();
			jse.executeScript("arguments[0].value='" + value + "';", element);
			if (takeScreenShot) {
				testCase.addScreenShotCurrentStep(u, "Campo");
			}
			u.unhighLight(elementBy);
		} catch (Exception e) {
			logS("" + e);
			throw new AssertionError("No se pudo asignar el atributo");
		}
	}

	/**
	 * Cambia el frame.
	 * 
	 * @param frameBy
	 * @author Esmeralda Garcia Torres
	 */
	public void switchFrame(By frameBy) {
		try {
			WebElement fr = u.getDriver().findElement(frameBy);
			u.getDriver().switchTo().frame(fr);
		} catch (Exception e) {
			logS("" + e);
			throw new AssertionError("No se pudo cambiar de frame");
		}
	}

}