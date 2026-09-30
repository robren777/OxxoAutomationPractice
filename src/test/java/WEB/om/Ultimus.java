package WEB.om;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Pattern;

import WEB.util.GlobalVariables;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class Ultimus {
	private SeleniumUtil u;
	private WebDriverWait wait;
	TestCase testCase;

	/// WEB ELEMENTS ULTIMUS LOGIN ///
	By conectaUltimus = By.id("btnLogin__3");
	By userUltimus = By.id("igtxtUserNameBox");
	By passUltimus = By.id("igtxtPasswordBox");
	By loginUltimus = By.id("LoginBtn__3");

	/// WEB ELEMENTS ULTIMUS SIKULI - MENU VISTAS
	SikuliUtil sikuli = new SikuliUtil();
	String pathImages = sikuli.PatternPath("WorkFlows"+File.separator+"Ultimus");
	Pattern iniciarImg = sikuli.PatternImage(pathImages + "iniciar.png");
	Pattern initiateImg = sikuli.PatternImage(pathImages + "initiate.PNG");
	Pattern inboxDeselectedImg = sikuli.PatternImage(pathImages + "InboxDeselected.png");
	Pattern inboxSelectedImg = sikuli.PatternImage(pathImages + "InboxSelected.png");
	Pattern bandejaEntradaSelectedImg = sikuli.PatternImage(pathImages + "bandejaEntradaSelected.png");
	Pattern bandejaEntradaDeselectedImg = sikuli.PatternImage(pathImages + "bandejaEntradaDeselected.png");
	Pattern completedImg = sikuli.PatternImage(pathImages + "completed.png");
	Pattern completadoImg = sikuli.PatternImage(pathImages + "completado.png");
	Pattern queueImg = sikuli.PatternImage(pathImages + "queue.png");
	Pattern colaImg = sikuli.PatternImage(pathImages + "cola.png");

	/// WEB ELEMENTS ULTIMUS SIKULI - LOGIN EN PORTAL
	Pattern userImg = sikuli.PatternImage(pathImages + "user_inputLogin.png");
	Pattern passimg = sikuli.PatternImage(pathImages + "pass_inputLogin.png");
	Pattern loginImg = sikuli.PatternImage(pathImages + "iniciarSesion_button.png");

	/// ULTIMUS ///
	By tableUltimus = By.cssSelector("table#G_WebSplitter1xtmpl1xUltraWebGrid1 > tbody > tr");
	By buttonCerrarSesionUltimus = By
			.cssSelector("#HelpMenu_5 > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2)");
	By panelProcess = By.id("pnlProgress");

	/**
	 * Constructor. Recibe el SeleniumUtil utilizado en el test.
	 * 
	 * @param SeleniumUtil
	 * @author 1000073289
	 */
	public Ultimus(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(30));
	}

	/**
	 * Login en Ultimus
	 * 
	 * @param user
	 * @param pass
	 * @author 1000073289
	 */
	public void loginUltimus(String user, String pass) {
		try {
			u.getDriver().manage().deleteAllCookies();
			u.get(GlobalVariables.URL_ULTIMUS);
			u.hardWait(3);
			if (u.getDriver().findElement(conectaUltimus).isDisplayed()) {
				u.click(conectaUltimus);
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
		}

		try {
			wait.until(ExpectedConditions.elementToBeClickable(userUltimus));
			u.clear(userUltimus);
			u.sendKeys(userUltimus, user);
			u.clear(passUltimus);
			u.sendKeys(passUltimus, pass);
			u.hardWait(1);
			u.click(loginUltimus);
			u.hardWait(1);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fall� al agregar las credenciales");
		}
	}

	/**
	 * Selecciona la opci�n indicada del men� "Vistas".
	 * 
	 * @param vista -> Iniciar, Inbox, Completado, Cola
	 * @author 1000073289
	 */
	public void seleccionarVista(String vista) {
		try {
			u.hardWait(2);
			loadingUltimus();
			try {
				u.getDriver().switchTo().frame("tabMain__ctl0_fcovgleen01.comercio.fne-Predeterminado");
				
			}catch(Exception e1) {
				u.getDriver().switchTo().frame("tabMain__ctl0_fcovgleen01.comercio.fne-Default");
			}
			
			switch (vista.toUpperCase()) {
			case "INICIAR":
				iniciarSikuli();
				break;
			case "INBOX":
				inboxSikuli();
				break;
			case "COMPLETADO":
				completedSikuli();
				break;
			case "COLA":
				colaSikuli();
				break;
			default:
				break;
			}
			loadingUltimus();
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fall� al dar clic en: " + vista);
		}
	}

	/**
	 * B�sca la aplicaci�n esperada en la tabla y la abre. Es importante que
	 * previamente se haya seleccionado la vista "Inicio". Tambi�n cambia el focus a
	 * la nueva ventana.
	 * 
	 * @param appName -> Nombre de la aplicaci�n tal cual se muestra en la tabla.
	 * @author 1000073289
	 */
	private void openApp(String appName) {
		try {
			searchInTableUltimus(appName);
			loadingUltimus();
			u.hardWait(3);
			System.out.println("------CAMBIAR------");
			switchToAnotherWindow(2, "cambiar", true);
			u.hardWait(2);
			loadingUltimus();
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fall� buscar la app: " + appName);
		}
	}

	/**
	 * B�sca la aplicaci�n esperada en la tabla, la abre e ingresa las credenciales
	 * del portal. Es importante que previamente se haya seleccionado la vista
	 * "Inicio". Tambi�n cambia el focus a la nueva ventana.
	 * 
	 * @param appName -> Nombre de la aplicaci�n tal cual se muestra en la tabla.
	 * @param user
	 * @param pass
	 * @author 1000073289
	 */
	private void openAppAccessCredentials(String appName, String user, String pass) {
		try {
			searchInTableUltimus(appName);
			loadingUltimus();
			u.hardWait(3);
			System.out.println("------CAMBIAR------");
			switchToAnotherWindow(2, "cambiar", false);
			ingresarCredencialesPortal(user, pass);
			u.hardWait(2);
			loadingUltimus();
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fall� buscar la app: " + appName);
		}
	}

	/**
	 * B�sca la aplicaci�n de Siv_Net en la tabla, abre el portal y cambia el focus
	 * a la ventana de SIV. Es importante que previamente se haya seleccionado la
	 * vista "Iniciar".
	 * 
	 * @author 1000073289
	 */
	public void openSivNet() {
		openApp("Siv_Net");
	}

	/**
	 * B�sca la aplicaci�n de Sipre_Bara en la tabla, abre el portal y cambia el
	 * focus a la ventana de Sipre_Bara. Es importante que previamente se haya
	 * seleccionado la vista "Inicio".
	 * 
	 * @author 1000073289
	 */
	public void openSipreBara(String user, String pass) {
		openAppAccessCredentials("SIPRE_BARA", user, pass);
	}

	/**
	 * B�sca la aplicaci�n de Megaworkflow en la tabla, abre el portal, ingresa las
	 * contrase�as y cambia el focus a la ventana de Megaworkflow. Es importante que
	 * previamente se haya seleccionado la vista "Inicio".
	 * 
	 * @author 1000073289
	 */
	public void openMegaWF(String user, String pass) {
		openAppAccessCredentials("Megaworkflow_v1", user, pass);
	}

	/**
	 * B�sca y selecciona de la tabla el incidente indicado. Es importante que
	 * previamente se haya seleccionado la vista en donde se har� la b�squeda ->
	 * Inbox, Completado.
	 * 
	 * @param incidente -> N�mero de incidente
	 * @author 1000073289
	 */
	public void selectIncidente(String incidente) {
		searchInTableUltimus(incidente);
		loadingUltimus();
		u.hardWait(1);
		System.out.println("------CAMBIAR------");
		switchToAnotherWindow(2, "cambiar", true);
		u.hardWait(1);
	}

	/**
	 * B�sca en la tabla de Ultimus el valor esperado. Dependiendo del m�dulo se
	 * recomienda buscar por: Inicio: Proceso. Inbox: Incidente. Completado:
	 * Incidente.
	 * 
	 * @param dato -> Proceso, Incidente
	 * @return String[] posici�n 0->fila. posici�n 1->innerText de la fila.
	 * @author 1000073289
	 */
	public String[] searchInTableUltimus(String dato) {
		String[] results = new String[2];
		results[0] = "";
		results[1] = "";
		Actions act = new Actions(u.getDriver());
		try {
			Boolean aux = false;
			Integer row = 0;
			List<WebElement> elementsTable = u.getDriver().findElements(tableUltimus);
			System.out.println("Elementos en la Tabla: " + elementsTable.size());
			for (int i = 0; i < elementsTable.size(); i++) {
				String element = elementsTable.get(i).getAttribute("innerText").toUpperCase();
				System.out.println("->" + element + " : " + dato);
				if (element.toUpperCase().contains(dato.toUpperCase())) {
					row = i;
					results[0] = Integer.toString(row);
					results[1] = element;
					System.out.println("Se encontr� en row: " + row);
					By selectElement = null;
					selectElement = By.cssSelector("#WebSplitter1xtmpl1xUltraWebGrid1_r_" + row);
					act.doubleClick(u.getDriver().findElement(selectElement)).build().perform();
					u.hardWait(1);
					aux = true;
					break;
				}
			}
			if (!aux) {
				throw new AssertionError("No se encontr� coincidencia en la tabla");
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fall� al buscar en la tabla");
		}
		return results;
	}

	/**
	 * Verifica que exista el modal de procesando y espera a que desaparezca.
	 * 
	 * @author 1000073289
	 */
	public void loadingUltimus() {
		try {
			u.hardWait(2);
			while (u.getDriver().findElement(panelProcess).isDisplayed()) {
				System.out.println("Procesando...");
				u.hardWait(2);
			}
		} catch (Exception e) {

		}
	}

	/**
	 * Cierra la sesi�n de Ultimus
	 * 
	 * @author 1000073289
	 */
	public void cerrarSesionUltimus() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(buttonCerrarSesionUltimus));
			u.highLight(buttonCerrarSesionUltimus);
			u.click(buttonCerrarSesionUltimus);
			u.hardWait(1);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fall� al cerrar sesi�n en Ultimus");
		}
	}

	/**
	 * Se cambia de ventana o cierra una.
	 * 
	 * @param numWindow -> N�mero de ventana
	 * @param opcion    -> cambiar o cerrar
	 * @param maximizar -> true al cambiar, false al cerrar
	 * @author 1000073289
	 */
	public void switchToAnotherWindow(int numWindow, String opcion, Boolean maximizar) {
		try {
			List<String> windowlist = null;
			Set<String> windows = u.getDriver().getWindowHandles();
			windowlist = new ArrayList<String>(windows);
			String currentWindow = u.getDriver().getWindowHandle();
			System.out.println("VENTANAS - " + windowlist.size());
			System.out.println("->" + windowlist);
			System.out.println("-> ACTUAL: " + currentWindow);

			if (!currentWindow.equalsIgnoreCase(windowlist.get(numWindow - 1))) {
				u.getDriver().switchTo().window(windowlist.get(numWindow - 1));
				System.out.println("-> CAMBIAR: " + u.getDriver().getWindowHandle());
			}

			if (maximizar)
				u.getDriver().manage().window().maximize();

			if (opcion.equalsIgnoreCase("cerrar")) {
				System.out.println("-> CERRAR: " + u.getDriver().getWindowHandle());
				u.getDriver().close();
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fall� al " + opcion + " la ventana");
		}
	}

	/**
	 * Cierra la ventana del portal y vuelve a la ventana de Ultimus.
	 * 
	 * @param windowUltimus -> WindowsHandle de la ventana de Ultimus.
	 * @author 1000073289
	 */
	public void cerrarVentanaPortal(String windowUltimus) {
		try {
			u.getDriver().manage().deleteAllCookies();
			System.out.println("------CERRAR------");
			switchToAnotherWindow(2, "cerrar", false);
			u.hardWait(2);
			System.out.println("------CAMBIAR AGAIN------");
			u.getDriver().switchTo().window(windowUltimus);
			System.out.println("-> " + u.getDriver().getWindowHandle());
			u.hardWait(1);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fall� al cerrar la ventana de SIV");
		}

	}

	/**
	 * Ingresa las credenciales cuando son solicitadas al abrir un portal.
	 * 
	 * @param user
	 * @param pass
	 * @author 1000073289
	 */
	public void ingresarCredencialesPortal(String user, String pass) {
		try {
			sikuli.screen.wait(userImg, 30);
			sikuli.screen.type(userImg, user);
			sikuli.screen.type(passimg, pass);
			u.hardWait(1);
			sikuli.screen.click(loginImg);
			u.hardWait(1);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fall� al cerrar ingresar las credenciales del portal");
		}
	}

	/**
	 * Por medio de sikuli busca la opci�n de Iniciar en el men� Vistas.
	 * 
	 * @author 1000073289
	 */
	public void iniciarSikuli() {
		try {
			System.out.println("PATH>>> " + pathImages);
			sikuli.screen.wait(iniciarImg, 20).click();
			loadingUltimus();
		} catch (Exception e) {
			try {
				sikuli.screen.wait(initiateImg, 20).click();
				loadingUltimus();
			}catch (Exception e1){
				System.out.println(" -> " + e1);
				throw new AssertionError("Fall� al dar clic en Iniciar");
			}
			
		}
	}

	/**
	 * Por medio de sikuli busca la opci�n de Inbox/Bandeja de Entrada en el men�.
	 * Vistas.
	 * 
	 * @author 1000073289
	 */
	public void inboxSikuli() {
		try {
			System.out.println("PATH>>> " + pathImages);
			sikuli.screen.wait(bandejaEntradaDeselectedImg, 10).click();
			loadingUltimus();
		} catch (Exception e) {
			try {
				sikuli.screen.wait(bandejaEntradaSelectedImg, 10).click();
				loadingUltimus();
			} catch (Exception e2) {
				try {
					sikuli.screen.wait(inboxDeselectedImg, 10).click();
					loadingUltimus();
				} catch (Exception e3) {
					try {
						sikuli.screen.wait(inboxSelectedImg, 10).click();
						loadingUltimus();
					} catch (Exception e4) {
						System.out.println(" -> " + e);
						throw new AssertionError("Fall� al dar clic en Inbox / Bandeja de Entrada");
					}
				}
			}
		}
	}

	/**
	 * Por medio de sikuli busca la opci�n de Completed/Completado en el men�
	 * Vistas.
	 * 
	 * @author 1000073289
	 */
	public void completedSikuli() {
		try {
			System.out.println("PATH>>> " + pathImages);
			sikuli.screen.wait(completadoImg, 15).click();
			loadingUltimus();
		} catch (Exception e) {
			try {
				sikuli.screen.wait(completedImg, 15).click();
				loadingUltimus();
			} catch (Exception e2) {
				System.out.println(" -> " + e);
				throw new AssertionError("Fall� al dar clic en Completed / Completado");
			}
		}
	}

	/**
	 * Por medio de sikuli busca la opci�n de Cola/Queue en el men� Vistas.
	 * 
	 * @author 1000073289
	 */
	public void colaSikuli() {
		try {
			System.out.println("PATH>>> " + pathImages);
			System.out.println("PATH>>> " + pathImages);
			sikuli.screen.wait(colaImg, 15).click();
			loadingUltimus();
		} catch (Exception e) {
			try {
				System.out.println("PATH>>> " + pathImages);
				sikuli.screen.wait(queueImg, 15).click();
				loadingUltimus();
			} catch (Exception e2) {
				System.out.println(" -> " + e);
				throw new AssertionError("Fall� al dar clic en Cola / Queue");
			}
		}
	}

	/**
	 * Espera a que el elemento se muestre, lo resalta y le da click.
	 * 
	 * @param opcion    -> Nombre del bot�n
	 * @param elementBy -> By Element
	 * @author 1000073289
	 */
	public void clickOpcion(String opcion, By elementBy) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(elementBy));
			u.highLight(elementBy);
			u.unhighLight(elementBy);
			u.click(elementBy);
			u.hardWait(1);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fall� al dar clic en: " + opcion);
		}
	}

	public void cerrarVentanaUltimus(String ventanaUltimus) {

	}

}
