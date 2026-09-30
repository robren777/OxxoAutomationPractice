package WEB.util.PreguntasF;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import WEB.util.GlobalVariables;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class AdmonPreguntas {
	private SeleniumUtil u;
	private WebDriverWait wait;
	private TestCase testCase;

	// Your connection isn't private//
	private By btnAdvance = By.id("details-button");
	private By linkContinue = By.id("proceed-link");

	// LOGIN
	private By locationUserInput = By.id("login:userName");
	private By locationPassInput = By.id("login:userPass");
	private By locationLoginButton = By.id("login:loginButton");

	// INICIO PORTAL APLICACIONES
	private By locationMenuInicio = By.id("menu_cnt");

//	private By Submenu Preguntas Frecuentes = By.linkText("Preguntas Frecuentes");
	private By preguntasFrec = By.linkText("Preguntas Frecuentes");
	private By Administracion = By.linkText("Administración");
	private By AdmonPreg = By.linkText("Administrador de Preguntas");
	private By locationSelectema = By.cssSelector("#question > div.envelope.bg_gris.titulo > div > div > div > a");
	private By locationTemaF = By.cssSelector("#j_idt19 > div:nth-child(2) > select");
	private By locationTemaEdit = By
			.cssSelector("#j_idt19 > div:nth-child(2) > select > optgroup:nth-child(2) > option:nth-child(3)");

//	Boton Agregar Pregunta
	private By locationAddTopic = By.cssSelector(
			"#question > div:nth-child(6) > div > div:nth-child(1) > table > tbody > tr > td:nth-child(1) > table > tbody > tr > td:nth-child(1)");
//	Seleccionar Editar Fila 	
	private By locationFila = By.cssSelector("#dataGrid > tbody > tr:nth-child(2)");
//	Seleccionar Eliminar Fila 	
	private By locationFila1 = By.cssSelector("#dataGrid > tbody > tr:nth-child(5)");
//	Boton Editar Pregunta	
	private By locationEditTopic = By.cssSelector(
			"#question > div:nth-child(6) > div > div:nth-child(1) > table > tbody > tr > td:nth-child(1) > table > tbody > tr > td:nth-child(2)");
//	Boton Eliminar Pregunta	
	private By locationEliminarTopic = By.cssSelector(
			"#question > div:nth-child(6) > div > div:nth-child(1) > table > tbody > tr > td:nth-child(2)");
//	Fila fin
	private By locationFila2 = By.cssSelector("#dataGrid > tbody > tr:nth-child(7)");
//	Footer
	private By locationFooter = By.cssSelector("#footer > div.grid7.right > div > p:nth-child(1)");
//	Titulo
	private By locationTit = By.cssSelector(
			"#homeSubview\\:j_idt61 > div > div.envelope.mtop10 > div:nth-child(3) > div:nth-child(4) > div.envelope.modulo_titulo");
//	Nombre Pregunta	
	private By locationNomPreg = By.cssSelector("#j_idt88\\:ques");
//	Nombre Respuesta		
	private By locationResp = By.cssSelector("#j_idt88\\:ans");
//	Numero de Orden	
	private By locationOrden = By.cssSelector("#j_idt88\\:orden");
//	Estaus "Activo"
	private By locationEstatus = By.cssSelector("#j_idt88\\:status");
	private By locationEstatusinput = By.cssSelector("#j_idt88\\:status > option:nth-child(3)");
//	SELECCION DE TEMA"	
	private By locationTemas = By.cssSelector("#j_idt88\\:top");
//	Tema "Prueba"
	private By locationTemasinput = By.cssSelector("#j_idt88\\:top > optgroup:nth-child(2) > option:nth-child(4)");
//	Tema "Prueba EDITAR"	
	private By locationTemas1input = By.cssSelector("#j_idt88\\:top > optgroup:nth-child(2) > option:nth-child(1)");
//	Boton Guardar
	private By locationGuardarinput = By.cssSelector("#j_idt88 > span > a.boton.rojo");
//	Boton Guardar_frame
	private By locationGuardarFrameinput = By.cssSelector("#j_idt19 > div.envelope.txt-right.pv10 > a.boton.rojo");
//	Boton Cerrar_frame	
	private By locationCerrarFrame = By.cssSelector("#cerrar_iframe > img");
//	Mensaje Exitoso
	private By locationExito = By.cssSelector("#question > div:nth-child(6) > div > div.grid5.mensaje_ok > ul > li");

	public AdmonPreguntas(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		this.testCase = testCase;
	}

	/**
	 * Login en Access Control
	 * 
	 * @param user
	 * @param ps
	 */
	public void loginAccessControl(String user, String ps) {
		try {
			Boolean bandera = false;
			Integer cont = 0;
			u.hardWait(1);
			u.getDriver().manage().deleteAllCookies();
			String url = GlobalVariables.URL_PORTAL_APPSG;
			testCase.addTextEvidenceCurrentStep(url);
			u.get(url);
			u.getDriver().manage().window().maximize();

			if (u.getDriver().findElements(btnAdvance).size() != 0) {
				u.click(btnAdvance);
				u.waitWebElement(linkContinue, 20, 2);
				u.click(linkContinue);
			}

			u.waitWebElement(locationUserInput, 15, 2);
			testCase.addScreenShotCurrentStep("");

			do {
				cont += 1;
				if (cont < 4) {
					wait.until(ExpectedConditions.elementToBeClickable(locationLoginButton));
					u.highLight(locationUserInput);
					clearInput(locationUserInput);
					u.sendKeys(locationUserInput, user);
					u.highLight(locationPassInput);
					clearInput(locationPassInput);
					u.sendKeys(locationPassInput, ps);
					u.highLight(locationLoginButton);
					testCase.addScreenShotCurrentStep(u, "Credenciales");
					u.click(locationLoginButton);
					try {
						wait.until(ExpectedConditions.elementToBeClickable(locationMenuInicio));
						bandera = true;
					} catch (Exception e) {
						bandera = false;
					}
				} else {
					u.getDriver().navigate().refresh();
					cont = 0;
				}
			} while (!bandera);

			testCase.addScreenShotCurrentStep(u, "AccessControl");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se pudo iniciar sesion");
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
			System.out.println(" -> " + e);
			System.out.println("Fallo al limpiar el campo");
		}
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
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar clic en: " + name);
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
	public void changeAttributeValue(By locationFinicialInput, String value, Boolean takeScreenShot) {
		try {
			u.highLight(locationFinicialInput);
			WebElement element = u.getDriver().findElement(locationFinicialInput);
			JavascriptExecutor jse = (JavascriptExecutor) u.getDriver();
			jse.executeScript("arguments[0].value='" + value + "';", element);
			if (takeScreenShot) {
				testCase.addScreenShotCurrentStep(u, "Campo");
			}
			u.unhighLight(locationFinicialInput);
		} catch (Exception e) {
//           logS("" + e);
			throw new AssertionError("No se pudo asignar el atributo");
		}
	}

	/**
	 * Acceso al link de Preguntas Frecuentes click.
	 * 
	 * @author David Perez Couto
	 */

	public void clickPregFrec() {
		try {
			u.highLight(preguntasFrec);
			u.click(locationTit);
			clickOpcion("Preguntas Frecuentes", preguntasFrec);
			testCase.addScreenShotCurrentStep("u, Preguntas Frecuentes");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar click en Preguntas Frecuentes");
		}
	}

	/**
	 * Acceso al link de Administracion de Temas del menu Administracion sobre menu
	 * desplegable click.
	 * 
	 * @author David Perez Couto
	 */

	public void clickMenuAdminPreg() {
		try {

			Actions builder = new Actions(this.u.getDriver());
			WebElement element = this.u.getDriver().findElement(By.linkText("Administración"));
			builder.moveToElement(element).build().perform();
			clickOpcion("Admnistrador de Preguntas", AdmonPreg);
			testCase.addScreenShotCurrentStep("u, Administracion de Temas");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar click en Administración");
		}

	}

	/**
	 * Ingresar Tema para validar Filtro en el Administrador de Preguntas click.
	 * 
	 * @param name      ->
	 * @param elementBy ->
	 * @author David Perez Couto
	 */

	public void validarFiltro() {

		try {

			// Activar el link para seleccionar Tema
			u.highLight(locationSelectema);
			testCase.addScreenShotCurrentStep("u, Seleccion de Tema a Filtrar");
			u.unhighLight(locationSelectema);
			u.click(locationSelectema);
			testCase.addScreenShotCurrentStep("u, Seleccion de Tema a Filtrar");
			u.hardWait(1);
			// Cambiar al frame del para seleccionar Tema
			u.waitWebElement(By.cssSelector("#TB_iframeContent"), 20, 2);
			WebElement iframe = this.u.getDriver().findElement(By.cssSelector("#TB_iframeContent"));
			// Switch to the frame para llenar formulario
			u.getDriver().switchTo().frame(iframe);
			testCase.addScreenShotCurrentStep("u, Llenado de Formulario");
//			Seleccionar Tema
			u.highLight(locationTemaF);
			testCase.addScreenShotCurrentStep("u, Seleccion de Tema a Filtrar");
			u.unhighLight(locationTemaF);
			u.click(locationTemaF);
			clickOpcion("TEMA PRUEBA QA EDITADA", locationTemaEdit);
			u.highLight(locationTemaF);
			u.unhighLight(locationTemaF);
			u.hardWait(1);
//			Guarda			
			u.highLight(locationGuardarFrameinput);
			testCase.addScreenShotCurrentStep("u, Formulario");
			u.unhighLight(locationGuardarFrameinput);
			u.click(locationGuardarFrameinput);
			u.hardWait(5);
			testCase.addScreenShotCurrentStep("u, El filtro se guardo con Exito");
			u.highLight(locationCerrarFrame);
			u.click(locationCerrarFrame);
			u.hardWait(5);
			testCase.addScreenShotCurrentStep("u, La operacion se realizo con Exito");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar click en Formulario");
		}

	}

	/**
	 * Ingresar Pregunta en el Administrador de Preguntas click.
	 * 
	 * @author David Perez Couto
	 */

	public void crearPregunta() {

		try {

			// Activar el boton para crear nuevo pregunta y poblar datos
			u.highLight(locationAddTopic);
			u.click(locationAddTopic);
			testCase.addScreenShotCurrentStep("u, Agregar Pregunta");
//			Pregunta
			u.highLight(locationNomPreg);
			u.sendKeys(locationNomPreg, "¿Como registrar una pregunta?");
			u.hardWait(1);
//			Respuesta
			u.highLight(locationResp);
			u.sendKeys(locationResp, "Ingresar los datos generales, orden, estatus, temas");
			u.hardWait(1);
//			Orden
			u.highLight(locationOrden);
			u.sendKeys(locationOrden, "1");
			u.hardWait(1);
//			Estatus			
			u.highLight(locationEstatus);
			u.click(locationEstatus);
			clickOpcion("Activo", locationEstatusinput);
			u.hardWait(1);
//			Temas			
			u.highLight(locationTemas);
			u.click(locationTemas);
			clickOpcion("Pruebas", locationTemasinput);
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("u, Formulario");
//			Guarda			
			u.highLight(locationGuardarinput);
			u.click(locationFooter);
			testCase.addScreenShotCurrentStep("u, Formulario");
			u.click(locationGuardarinput);
			u.hardWait(1);
//			Operacion con exito
			u.highLight(locationExito);
			testCase.addScreenShotCurrentStep("u, La operacion se realizo con Exito");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar click en Formulario");
		}

	}

	/**
	 * Editar Pregunta en el Administrador de Preguntas click.
	 * 
	 * @author David Perez Couto
	 */

	public void editarPregunta() {

		try {

//			 Seleciona Fila para editar tema
			u.highLight(locationFila);
			u.click(locationFila);
			testCase.addScreenShotCurrentStep("u, Editar Pregunta");
//			 Activar el boton para editar tema y poblar datos
			u.highLight(locationEditTopic);
			testCase.addScreenShotCurrentStep("u, Editar Pregunta");
			u.click(locationEditTopic);
			testCase.addScreenShotCurrentStep("u, Editar Pregunta");
//			Pregunta
			u.highLight(locationNomPreg);
			clearInput(locationNomPreg);
			u.sendKeys(locationNomPreg, "¿Como puedo ver los servicios cobrados en Tienda?");
			u.hardWait(1);
//			Respuesta
			u.highLight(locationResp);
			clearInput(locationResp);
			u.sendKeys(locationResp,
					"1.-Debes entrar al Portal de Cobro de Servicios, en la opcion Consulta de Cobros Correctos");
			u.hardWait(1);
//			Orden
			u.highLight(locationOrden);
			clearInput(locationOrden);
			u.sendKeys(locationOrden, "2");
			u.hardWait(1);
//			Estatus			
			u.highLight(locationEstatus);
			u.click(locationEstatus);
			clickOpcion("Activo", locationEstatusinput);
			u.hardWait(1);
//			Temas			
			u.highLight(locationTemas);
			u.click(locationTemas);
			clickOpcion("Prueba EDITAR", locationTemas1input);
			u.click(locationTemas);
//			Guarda			
			u.highLight(locationGuardarinput);
			u.click(locationFooter);
			testCase.addScreenShotCurrentStep("u, Formulario");
			u.click(locationGuardarinput);
			u.hardWait(1);
//			Operacion con exito
			u.highLight(locationExito);
			u.click(locationFila);
			testCase.addScreenShotCurrentStep("u, La operacion se realizo con Exito");

		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar click en Formulario");
		}
	}

	/**
	 * Eliminar Pregunta en el Administrador de Preguntas click.
	 * 
	 * @author David Perez Couto
	 */

	public void eliminarPregunta() {

		try {

//			 Seleciona Fila para eliminar pregunta
			u.highLight(locationFila1);
			u.click(locationFila1);
			testCase.addScreenShotCurrentStep("u, Eliminar Pregunta");
//			 Activar el boton para eliminar pregunta
			u.highLight(locationEliminarTopic);
			u.click(locationEliminarTopic);
			u.hardWait(2);
			testCase.addScreenShotCurrentStep("u, Eliminar Pregunta");
//			Se cierra ventana de Aceptar
			Alert alert = u.getDriver().switchTo().alert();
			u.hardWait(2);
			alert.accept();
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("u, Eliminar Pregunta");
//			Operacion con exito
			u.highLight(locationExito);
			u.click(locationFila1);
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("u, Mensaje de Exito");

		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar click en Eliminar pregunta");
		}
	}

}
