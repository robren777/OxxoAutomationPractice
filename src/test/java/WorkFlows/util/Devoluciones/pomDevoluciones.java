package WorkFlows.util.Devoluciones;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class pomDevoluciones {
	private SeleniumUtil u;
	private TestCase testCase;
	private WebDriverWait wait;
	private String log;
	
	public pomDevoluciones(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		log = "+++++++> ";
	}
	/**
	 * Ingresar usuario en PEGA
	 * @param opcion
	 * @param valor
	 * @param elementBy
	 * @author Karim Duran
	 */
	public void enterText(String opcion, String valor, By elementBy) {
		try {
			//wait.until(ExpectedConditions.elementToBeClickable(elementBy));
			//wait.until(ExpectedConditions.visibilityOfElementLocated(elementBy));
			//clearInput(elementBy);
			u.sendKeys(elementBy, valor);
			//u.hardWait(1);
		} catch (Exception e) {
			System.out.println(log + e);
			throw new AssertionError("Fallo al ingresar en el campo: " + opcion);
		}
	}
	/**
	 * Dar click en botón deseado
	 * @param opcion
	 * @param elementBy
	 * @author Karim Duran
	 */
	public void clickButton(String opcion, By elementBy) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(elementBy));
			u.highLight(elementBy);
			testCase.addScreenShotCurrentStep(u, "Dar clic en " + opcion);
			u.unhighLight(elementBy);
			u.click(elementBy);
		} catch (Exception e) {
			System.out.println(log + e);
			throw new AssertionError("Fallo al dar clic en: " + opcion);
		}
	}
	/**
	 * Comprobación de los aplicativos disponibles para el usuario (solo screenshot)
	 * @param opcion
	 * @param elementBy
	 * @author Karim Duran
	 */
	public void viewApps(String opcion, By elementBy) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(elementBy));
			testCase.addScreenShotCurrentStep(u, opcion);
		} catch (Exception e) {
			System.out.println(log + e);
			throw new AssertionError("No hay elemento presente");
		}
	}
	
//	public void viewButton(String opcion, By elementBy) {
//		try {
//			wait.until(ExpectedConditions.visibilityOfElementLocated(elementBy));
//			u.highLight(elementBy);
//			testCase.addScreenShotCurrentStep(u, "Aplicativo presente!: " + opcion);
//			u.unhighLight(elementBy);
//		} catch (Exception e) {
//			System.out.println(log + e);
//			throw new AssertionError("No se encontro el aplicativo esperado: " + opcion);
//		}
//	}
	/**
	 * Comprobación de los elementos presentes dentro de la página
	 * @param opcion
	 * @param elementBy
	 * @param elementBy2
	 * @author Karim Duran
	 */
	public void viewButton(String opcion, By elementBy, By elementBy2) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(elementBy));
			u.highLight(elementBy); u.highLight(elementBy2);
			testCase.addScreenShotCurrentStep(u, "Botones y filtros presentes" + opcion);
			u.unhighLight(elementBy); u.unhighLight(elementBy2);
		} catch (Exception e) {
			System.out.println(log + e);
			throw new AssertionError("No se encontraron botones ni filtros:" + opcion);
		}
	}
	/**
	 * Mouse hover para seleccionar una opción dentro de un menu
	 * @param opcion
	 * @param elementBy
	 * @param elementBy2
	 * @author Karim Duran
	 */
	public void dropDownMouse(String opcion, By elementBy, By elementBy2) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(elementBy));
			u.highLight(elementBy);
			//u.hardWait(1);
			u.mouseOver(elementBy);
		} catch (Exception e) {
			System.out.println(log + e);
			throw new AssertionError("No se encontro el menu deseado");
		}
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(elementBy2));
			u.highLight(elementBy2);
			testCase.addScreenShotCurrentStep(u, "Menu y opcion deseados: Opcion " + opcion);
			u.unhighLight(elementBy); u.unhighLight(elementBy2);
			u.hardWait(1);
			u.click(elementBy2);
		} catch (Exception e) {
			System.out.println(log + e);
			throw new AssertionError("No se encontro la opcion deseada: " + opcion);
		}
	}
	/**
	 * Limpiar text field para ingresar nueva información
	 * @param inputBy
	 * @author Karim Duran
	 */
	public void clearInput(By inputBy) {
		try {
			WebElement toClear = u.getDriver().findElement(inputBy);
			toClear.sendKeys(Keys.CONTROL + "a");
			toClear.sendKeys(Keys.DELETE);
		} catch (Exception e) {
			System.out.println(log + "Fallo al limpiar el campo: " + e);
		}
	}
	/**
	 * Selecciona un DropDown junto al valor deseado por seleccionar 
	 * @param ddLocator
	 * @param value
	 * @author Karim Duran
	 */
	public void dropDownUtil(By ddLocator, String value) {
		try {
			WebElement ddArchivo = u.getDriver().findElement(ddLocator);
			Select selectTipoDev = new Select(ddArchivo);
			selectTipoDev.selectByValue(value);
			//u.highLight(ddLocator);
			//testCase.addScreenShotCurrentStep(u, "Tipo de Devolución");
			//u.unhighLight(ddLocator);
		} catch (Exception e) {
			System.out.println(log + "Fallo al seleccionar Tipo de Devolución: " + e);
		}
	}
	/**
	 * Selecciona un DropDown dependiendo del index seleccionado 
	 * @param ddLocator
	 * @param value
	 */
	public void dropDownUtilIndex(By ddLocator, int value) {
		try {
			WebElement ddArchivo = u.getDriver().findElement(ddLocator);
			Select selectTipoDev = new Select(ddArchivo);
			selectTipoDev.selectByIndex(value);
		} catch (Exception e) {
			System.out.println(log + "Fallo al seleccionar Tipo de Devolución: " + e);
		}
	}
}
