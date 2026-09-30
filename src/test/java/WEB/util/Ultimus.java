package WEB.util;

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
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;

import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class Ultimus {
	private SeleniumUtil u;
	private TestCase testCase;
	private WebDriverWait wait;

	/// WEB ELEMENTS ULTIMUS LOGIN ///
	By conectaUltimus = By.cssSelector("#btnLogin__3");
	By userUltimus = By.id("igtxtUserNameBox");
	By passUltimus = By.id("igtxtPasswordBox");
	By loginUltimus = By.id("LoginBtn__3");

	/// WEB ELEMENTS ULTIMUS SIKULI - MENU VISTAS
	SikuliUtil sikuli = new SikuliUtil();
	String pathImages = sikuli.PatternPath("WorkFlows"+File.separator+"Ultimus2");
	Pattern iniciarImg = sikuli.PatternImage(pathImages + "iniciar.png");
	Pattern iniciarSelectedImg = sikuli.PatternImage(pathImages + "iniciarSelected.png");
	Pattern initiateImg = sikuli.PatternImage(pathImages + "initiate.PNG");
	Pattern inboxDeselectedImg = sikuli.PatternImage(pathImages + "InboxDeselected.png");
	Pattern inboxSelectedImg = sikuli.PatternImage(pathImages + "InboxSelected.png");
	Pattern bandejaEntradaSelectedImg = sikuli.PatternImage(pathImages + "bandejaEntradaSelected.png");
	Pattern bandejaEntradaDeselectedImg = sikuli.PatternImage(pathImages + "bandejaEntradaDeselected.png");
	Pattern completedImg = sikuli.PatternImage(pathImages + "completed.png");
	Pattern completadoImg = sikuli.PatternImage(pathImages + "completado.png");
	Pattern queueImg = sikuli.PatternImage(pathImages + "queue.png");
	Pattern colaImg = sikuli.PatternImage(pathImages + "cola.png");
	Pattern scrollDown = sikuli.PatternImage(pathImages + "scrollDown.png");
	Pattern siguiente = sikuli.PatternImage(pathImages + "siguiente.png");
	Pattern monitoreo = sikuli.PatternImage(pathImages + "monitoreo.png");

	/// WEB ELEMENTS ULTIMUS SIKULI - LOGIN EN PORTAL
	Pattern userImg = sikuli.PatternImage(pathImages + "user_inputLogin.png");
	Pattern passimg = sikuli.PatternImage(pathImages + "pass_inputLogin.png");
	Pattern loginImg = sikuli.PatternImage(pathImages + "iniciarSesion_button.png");
	Pattern loginInglesImg = sikuli.PatternImage(pathImages + "SingIn_button.PNG");

	/// ULTIMUS ///
	By tableUltimus = By.cssSelector("table#G_WebSplitter1xtmpl1xUltraWebGrid1 > tbody > tr");
	By buttonCerrarSesionUltimus = By
			.cssSelector("#HelpMenu_5 > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2)");
	By panelProcess = By.id("pnlProgress");

	/**
	 * Constructor. Recibe el SeleniumUtil utilizado en el test.
	 * 
	 * @param SeleniumUtil
	 * @author Esmeralda Garcia Torres
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
	 * @author Esmeralda Garcia Torres
	 */
	public void loginUltimus(String user, String pass) {
		try {
			u.hardWait(1);
			u.getDriver().manage().deleteAllCookies();
			String url = GlobalVariables.URL_ULTIMUS;
			testCase.addTextEvidenceCurrentStep(url);
			u.get(url);
			u.hardWait(3);
			if (u.getDriver().findElement(conectaUltimus).isDisplayed()) {
				clickOpcion("Conecta Ultimus", conectaUltimus);
			}
		} catch (Exception e) {

		}
		Boolean aux;
		do {
			try {
				aux = false;
				u.hardWait(1);
				wait.until(ExpectedConditions.elementToBeClickable(userUltimus));
				u.clear(userUltimus);
				u.sendKeys(userUltimus, user);
				u.clear(passUltimus);
				u.sendKeys(passUltimus, pass);
				u.hardWait(1);
				testCase.addScreenShotCurrentStep(u, "CredencialesUltimus");
				u.click(loginUltimus);
				u.hardWait(1);
			} catch (Exception e) {
				aux = true;
				System.out.println("+++> " + e);
				throw new AssertionError("Fallo al agregar las credenciales");
			}
		} while (aux);
	}

	/**
	 * Selecciona la opcion indicada del menu "Vistas".
	 * 
	 * @param vista -> Iniciar, Inbox, Completado, Cola
	 * @author Esmeralda Garcia Torres
	 */
	public void seleccionarVista(String vista) {
		try {
			u.hardWait(4);
			loadingUltimus();
			try {
				u.getDriver().switchTo().frame("tabMain__ctl0_fcovgleen01.comercio.fne-Predeterminado");
			} catch (Exception e1) {
				try {
					u.getDriver().switchTo().frame("tabMain__ctl0_fcovgleen01.comercio.fne-Default");
				} catch (Exception e2) {
					System.out.println(" -> " + e2);
					throw new AssertionError("No se encontro el frame de las vistas");
				}

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
			testCase.addScreenShotCurrentStep(u, "Vista");
		} catch (Exception e) {
			System.out.println("+++> " + e);
			throw new AssertionError("Fallo al dar clic en: " + vista);
		}
	}

	/**
	 * Busca la aplicacion esperada en la tabla y la abre. Es importante que
	 * previamente se haya seleccionado la vista "Inicio". Tambien cambia el focus a
	 * la nueva ventana.
	 * 
	 * @param appName -> Nombre de la aplicacion tal cual se muestra en la tabla.
	 * @author Esmeralda Garcia Torres
	 */
	private void openApp(String appName) {
		try {
			searchInTableUltimus(appName + "\t");
			loadingUltimus();
			u.hardWait(3);
			System.out.println("+++> ------CAMBIAR------");
			switchToAnotherWindow(2, "cambiar", true);
			u.hardWait(2);
			loadingUltimus();
		} catch (Exception e) {
			System.out.println("+++> " + e);
			throw new AssertionError("Fallo buscar la app: " + appName);
		}
	}

	/**
	 * Busca la aplicacion esperada en la tabla, la abre e ingresa las credenciales
	 * del portal. Es importante que previamente se haya seleccionado la vista
	 * "Inicio". Tambien cambia el focus a la nueva ventana.
	 * 
	 * @param appName -> Nombre de la aplicacion tal cual se muestra en la tabla.
	 * @param user
	 * @param pass
	 * @author Esmeralda Garcia Torres
	 */
	private void openAppAccessCredentials(String appName, String user, String pass) {
		try {
			searchInTableUltimus(appName + "\t");
			loadingUltimus();
			u.hardWait(3);
			System.out.println("+++> ------CAMBIAR------");
			switchToAnotherWindow(2, "cambiar", false);
			ingresarCredencialesPortal(user, pass);
			u.hardWait(2);
			loadingUltimus();
		} catch (Exception e) {
			System.out.println("+++> " + e);
			throw new AssertionError("Fallo buscar la app: " + appName);
		}
	}

	/**
	 * Busca la aplicacion de Siv_Net en la tabla, abre el portal y cambia el focus
	 * a la ventana de SIV. Es importante que previamente se haya seleccionado la
	 * vista "Iniciar".
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void openSivNet() {
		seleccionarVista("Iniciar");
		openApp("Siv_Net");
	}

	/**
	 * Busca la aplicacion de Sipre_Bara en la tabla, abre el portal y cambia el
	 * focus a la ventana de Sipre_Bara. Es importante que previamente se haya
	 * seleccionado la vista "Inicio".
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void openSipreBara(String user, String pass) {
		seleccionarVista("Iniciar");
		openAppAccessCredentials("SIPRE_BARA", user, pass);
	}

	/**
	 * Selecciona el aplicativo PromoBaraClone
	 * 
	 * @author alicia.ceja
	 */
	public void openPromoBara(String user, String pass) {
		seleccionarVista("Iniciar");
		openAppAccessCredentials("PromoBaraClone", user, pass);
	}

	/**
	 * Selecciona el aplicativo Monitoreo
	 * 
	 * @throws FindFailed
	 * @author alicia.ceja
	 */
	public void openMonitoreo() throws FindFailed {
		try {
			sikuli.screen.wait(siguiente, 30);
			sikuli.screen.click(siguiente);

			u.hardWait(1);

			sikuli.screen.doubleClick(monitoreo);
			u.hardWait(3);
			System.out.println("+++> ------CAMBIAR------");
			switchToAnotherWindow(2, "cambiar", false);
			u.hardWait(3);

		} catch (Exception e) {
			System.out.println("+++> " + e);
			throw new AssertionError("Fallo al entrar a Monitoreo");
		}

	}

	/**
	 * Busca la aplicacion de Megaworkflow en la tabla, abre el portal, ingresa las
	 * contrasenas y cambia el focus a la ventana de Megaworkflow. Es importante que
	 * previamente se haya seleccionado la vista "Inicio".
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void openMegaWF(String user, String pass) {
		seleccionarVista("Iniciar");
		openAppAccessCredentials("Megaworkflow_v1", user, pass);
	}

	/**
	 * Busca y selecciona de la tabla el incidente indicado.
	 * 
	 * @param incidente -> Numero de incidente
	 * @param bandeja   -> Inbox o Completado
	 * @author Esmeralda Garcia Torres
	 */
	public void selectIncidente(String incidente, String bandeja) {
		if (bandeja.equalsIgnoreCase("Inbox")) {
			seleccionarVista("INBOX");
		} else if (bandeja.equalsIgnoreCase("Completado")) {
			seleccionarVista("COMPLETADO");
		} else {
			throw new AssertionError("No se encontr� la vista: " + bandeja);
		}
		searchInTableUltimus(incidente);
		loadingUltimus();
		u.hardWait(1);
		System.out.println("+++> ------CAMBIAR------");
		switchToAnotherWindow(2, "cambiar", true);
		u.hardWait(1);
	}

	/**
	 * Busca en la tabla de Ultimus el valor esperado. Dependiendo del mudulo se
	 * recomienda buscar por: Inicio: portal. Inbox: Incidente. Completado:
	 * Incidente.
	 * 
	 * @param dato -> Portal, Incidente
	 * @return String[] posicion 0->fila. posicion 1->innerText de la fila.
	 * @author Esmeralda Garcia Torres
	 */
	public String[] searchInTableUltimus(String dato) {
		u.hardWait(2);
		String[] results = new String[2];
		results[0] = "";
		results[1] = "";
		Actions act = new Actions(u.getDriver());
		Boolean next = false;
		do {
			try {
				Boolean aux = false;
				Integer row = 0;
				List<WebElement> elementsTable = u.getDriver().findElements(tableUltimus);
				System.out.println("+> Elementos en la Tabla: " + elementsTable.size());
				for (int i = 0; i < elementsTable.size(); i++) {
					String element = elementsTable.get(i).getAttribute("innerText").toUpperCase();
					System.out.println("+> " + element + " : " + dato);
					if (element.toUpperCase().contains(dato.toUpperCase())) {
						row = i;
						results[0] = Integer.toString(row);
						results[1] = element;
						System.out.println("+> Se encontro en row: " + row);
						By selectElement = null;
						selectElement = By.cssSelector("#WebSplitter1xtmpl1xUltraWebGrid1_r_" + row);
						u.highLight(selectElement);
						testCase.addScreenShotCurrentStep(u, "seleccionTabla");
						u.unhighLight(selectElement);
						act.doubleClick(u.getDriver().findElement(selectElement)).build().perform();
						u.hardWait(1);
						aux = true;
						next = false;
						break;
					}
				}
				if (!aux) {
					try {
						if (elementsTable.size() == 20) {
							next = true;
						} else {
							next = false;
							break;
						}
						System.out.println("+> PATH>>> " + pathImages);
						sikuli.screen.wait(scrollDown, 20).click();
						sikuli.screen.wait(scrollDown, 20).click();
						sikuli.screen.wait(scrollDown, 20).click();

						sikuli.screen.wait(siguiente, 20).click();
						loadingUltimus();

					} catch (Exception e) {
						next = false;
						throw new AssertionError("No se encontro coincidencia en la tabla");
					}
				}
			} catch (Exception e) {
				System.out.println("+++> " + e);
				throw new AssertionError("Fallo al buscar en la tabla");
			}
		} while (next);

		return results;
	}

	/**
	 * Verifica que exista el modal de procesando y espera a que desaparezca.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void loadingUltimus() {
		try {
			u.hardWait(2);
			while (u.getDriver().findElement(panelProcess).isDisplayed()) {
				System.out.println("+> Procesando...");
				u.hardWait(2);
			}
		} catch (Exception e) {

		}
	}

	/**
	 * Cierra la sesion de Ultimus
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void cerrarSesionUltimus() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(buttonCerrarSesionUltimus));
			u.highLight(buttonCerrarSesionUltimus);
			testCase.addScreenShotCurrentStep(u, "cerrarSesionUltimus");
			u.click(buttonCerrarSesionUltimus);
			u.hardWait(1);
		} catch (Exception e) {
			System.out.println("+++> " + e);
			throw new AssertionError("Fallo al cerrar sesion en Ultimus");
		}
	}

	/**
	 * Se cambia de ventana o cierra una.
	 * 
	 * @param numWindow -> Numero de ventana
	 * @param opcion    -> cambiar o cerrar
	 * @param maximizar -> true al cambiar, false al cerrar
	 * @author Esmeralda Garcia Torres
	 */
	public void switchToAnotherWindow(int numWindow, String opcion, Boolean maximizar) {
		try {
			List<String> windowlist = null;
			Set<String> windows = u.getDriver().getWindowHandles();
			windowlist = new ArrayList<String>(windows);
			String currentWindow = u.getDriver().getWindowHandle();
			System.out.println("+> VENTANAS - " + windowlist.size());
			System.out.println("+> " + windowlist);
			System.out.println("+> ACTUAL: " + currentWindow);

			if (!currentWindow.equalsIgnoreCase(windowlist.get(numWindow - 1))) {
				u.getDriver().switchTo().window(windowlist.get(numWindow - 1));
				System.out.println("+> CAMBIAR: " + u.getDriver().getWindowHandle());
			}

			if (maximizar)
				u.getDriver().manage().window().maximize();

			if (opcion.equalsIgnoreCase("cerrar")) {
				System.out.println("+++> CERRAR: " + u.getDriver().getWindowHandle());
				u.getDriver().close();
			}
		} catch (Exception e) {
			System.out.println("+++> " + e);
			throw new AssertionError("Fallo al " + opcion + " la ventana");
		}
	}

	/**
	 * Cierra la ventana del portal y vuelve a la ventana de Ultimus.
	 * 
	 * @param windowUltimus -> WindowsHandle de la ventana de Ultimus.
	 * @author Esmeralda Garcia Torres
	 */
	public void cerrarVentanaPortal(String windowUltimus) {
		try {
			u.getDriver().manage().deleteAllCookies();
			System.out.println("+++> ------CERRAR------");
			switchToAnotherWindow(2, "cerrar", false);
			u.hardWait(2);
			System.out.println("+++> ------CAMBIAR AGAIN------");
			u.getDriver().switchTo().window(windowUltimus);
			System.out.println("+> " + u.getDriver().getWindowHandle());
			u.hardWait(1);
		} catch (Exception e) {
			System.out.println("+> " + e);
			throw new AssertionError("Fallo al cerrar la ventana de SIV");
		}

	}

	/**
	 * Ingresa las credenciales cuando son solicitadas al abrir un portal.
	 * 
	 * @param user
	 * @param pass
	 * @author Esmeralda Garcia Torres
	 */
	public void ingresarCredencialesPortal(String user, String pass) {
		try {
			sikuli.screen.wait(userImg, 30);
			sikuli.screen.type(userImg, user);
			sikuli.screen.type(passimg, pass);
			u.hardWait(1);
			testCase.addScreenShotCurrentStep(u, "CredencialesPortal");
			try {
				sikuli.screen.click(loginImg);
			} catch (Exception e) {
				sikuli.screen.click(loginInglesImg);
			}

			u.hardWait(1);
		} catch (Exception e) {
			System.out.println("+++> " + e);
			throw new AssertionError("Fallo al cerrar ingresar las credenciales del portal");
		}
	}

	/**
	 * Por medio de sikuli busca la opcion de Iniciar en el menu Vistas.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void iniciarSikuli() {
		try {
			System.out.println("+> PATH>>> " + pathImages);
			sikuli.screen.wait(iniciarImg, 20).click();
			loadingUltimus();
		} catch (Exception e) {
			try {
				sikuli.screen.wait(initiateImg, 20).click();
				loadingUltimus();
			} catch (Exception e1) {
				try {
					sikuli.screen.wait(iniciarSelectedImg, 20).click();
					loadingUltimus();
				} catch (Exception e2) {
					System.out.println("+++> " + e1);
					throw new AssertionError("Fallo al dar clic en Iniciar");
				}
			}
		}
	}

	/**
	 * Por medio de sikuli busca la opcion de Inbox/Bandeja de Entrada en el menu.
	 * Vistas.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void inboxSikuli() {
		try {
			System.out.println("+> PATH>>> " + pathImages);
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
						System.out.println("+++> " + e);
						throw new AssertionError("Fallo al dar clic en Inbox / Bandeja de Entrada");
					}
				}
			}
		}
	}

	/**
	 * Por medio de sikuli busca la opcion de Completed/Completado en el menu
	 * Vistas.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void completedSikuli() {
		try {
			System.out.println("+> PATH>>> " + pathImages);
			sikuli.screen.wait(completadoImg, 15).click();
			loadingUltimus();
		} catch (Exception e) {
			try {
				sikuli.screen.wait(completedImg, 15).click();
				loadingUltimus();
			} catch (Exception e2) {
				System.out.println("+++> " + e);
				throw new AssertionError("Fallo al dar clic en Completed / Completado");
			}
		}
	}

	/**
	 * Por medio de sikuli busca la opcion de Cola/Queue en el menu Vistas.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void colaSikuli() {
		try {
			System.out.println("+> PATH>>> " + pathImages);
			sikuli.screen.wait(colaImg, 15).click();
			loadingUltimus();
		} catch (Exception e) {
			try {
				sikuli.screen.wait(queueImg, 15).click();
				loadingUltimus();
			} catch (Exception e2) {
				System.out.println("+++> " + e);
				throw new AssertionError("Fallo al dar clic en Cola / Queue");
			}
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
			wait.until(ExpectedConditions.elementToBeClickable(elementBy));
			u.highLight(elementBy);
			testCase.addScreenShotCurrentStep(u, "" + opcion);
			u.unhighLight(elementBy);
			u.click(elementBy);
			u.hardWait(1);
		} catch (Exception e) {
			System.out.println("+++> " + e);
			throw new AssertionError("Fallo al dar clic en: " + opcion);
		}
	}

	/**
	 * Metodo para hacer click sobre Planchado
	 * 
	 * @author Misraim Hernandez
	 */
	public void openPlanchado() {
		seleccionarVista("Iniciar");
		openApp("Planchado");
	}

	/**
	 * Metodo para hacer click sobre SIPRE
	 * 
	 * @author Misraim Hernandez
	 */
	public void openSipreOXXO(String user, String pass) {
		seleccionarVista("Iniciar");
		openAppAccessCredentials("SIPRE", user, pass);
	}

	/**
	 * Metodo para hacer click sobre SIV_Net_Nucleo
	 * 
	 * @author Misraim Hernandez
	 */
	public void openSIVNetNucleo() {
		seleccionarVista("Iniciar");
		openApp("SIV_Net_Nucleo");
	}

}
