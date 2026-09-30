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

public class AdmonEncargados {
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

//	Submenu Preguntas Frecuentes
	private By preguntasFrec = By.linkText("Preguntas Frecuentes");
	private By Administracion = By.linkText("Administración");
//	Submenu Administrador de Avisos
	private By AdmonEncargado = By.linkText("Administrador de Encargado de Preguntas");

//	Seleccion de Aplicacion	en Frame
	private By locationAplic = By.cssSelector("#manager > div.envelope.bg_gris.titulo > div > div > div > a");
	private By locationAplicF = By.cssSelector("#j_idt19 > div:nth-child(2) > select");
	private By locationAplicEdit = By.cssSelector("#j_idt19 > div:nth-child(2) > select > option:nth-child(20)");
//	Boton Guardar_frame
	private By locationGuardarFrameinput = By.cssSelector("#j_idt19 > div.envelope.txt-right.pv10 > a.boton.rojo");
//	Boton Cerrar_frame	
	private By locationCerrarFrame = By.cssSelector("#cerrar_iframe > img");

//	Boton Agregar Aplicacion
	private By locationAddTopic = By.cssSelector(
			"#manager > div:nth-child(6) > div > div:nth-child(1) > table > tbody > tr > td:nth-child(1) > table > tbody > tr > td:nth-child(1)");

//	Seleccionar Editar Fila 	
	private By locationFila = By.cssSelector("#dataGrid > tbody > tr:nth-child(1)");
//	Seleccionar Eliminar Fila 	
	private By locationFila1 = By.cssSelector("#dataGrid > tbody > tr:nth-child(6)");
//	Boton Editar Aplicacion	
	private By locationEditTopic = By.cssSelector(
			"#manager > div:nth-child(6) > div > div:nth-child(1) > table > tbody > tr > td:nth-child(1) > table > tbody > tr > td:nth-child(2)");
//	Boton Eliminar Aplicacion	
	private By locationEliminarTopic = By
			.cssSelector("#manager > div:nth-child(6) > div > div:nth-child(1) > table > tbody > tr > td:nth-child(2)");
//	Fila fin
	private By locationFila2 = By.cssSelector("#dataGrid > tbody > tr:nth-child(7)");
//	Footer
	private By locationFooter = By.cssSelector("#footer > div.grid7.right > div > p:nth-child(1)");
//	Titulo
	private By locationTit = By.cssSelector(
			"#homeSubview\\:j_idt61 > div > div.envelope.mtop10 > div:nth-child(3) > div:nth-child(4) > div.envelope.modulo_titulo");
	private By locationDetalle = By.cssSelector("#j_idt88\\:mail");
//	Fecha Inicial	
	private By locationFinicial = By.cssSelector("#j_idt89\\:initDate");
//	Fecha Final	
	private By locationFfinal = By.cssSelector("#j_idt89\\:endDate");
//	SELECCION DE APLICACION"	
	private By locationAplicacion = By.cssSelector("#j_idt88\\:app");
//	Aplicacion "Folio de Pago Colombia"
	private By locationFolio = By.cssSelector("#j_idt88\\:app > option:nth-child(12)");
//	Aplicacion "Portal de Investigaciones"
	private By locationPortal = By.cssSelector("#j_idt88\\:app > option:nth-child(20)");
//	Correo		
	private By locationCorreo = By.cssSelector("#j_idt88\\:mail");
//	Tema "Control de Pagos"
	private By locationPagosinput = By.cssSelector("#j_idt89\\:app > option:nth-child(54)");
//	Tema "Prueba EDITAR"	
	private By locationTemas1input = By.cssSelector("#j_idt88\\:top > optgroup:nth-child(2) > option:nth-child(1)");
//	Boton Guardar
	private By locationGuardarinput = By.cssSelector("#j_idt88 > span > a.boton.rojo");
//	Seleccionar Fila Agregada
	private By locationFila7 = By.cssSelector("#dataGrid > tbody > tr:nth-child(7)");
//	Seleccionar Fila Editada
	private By locationFila01 = By.cssSelector("#dataGrid > tbody > tr:nth-child(1)");
//	Mensaje Exitoso
	private By locationExito = By.cssSelector("#manager > div:nth-child(6) > div > div.grid5.mensaje_ok > ul > li");

	public AdmonEncargados(SeleniumUtil u, TestCase testCase) {
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
	 * Acceso al link de Administracion de Encargados de las Aplicaciones del menu
	 * Administracion sobre menu desplegable click.
	 * 
	 * @author David Perez Couto
	 */

	public void clickMenuAdminEncargado() {
		try {

			Actions builder = new Actions(this.u.getDriver());
			WebElement element = this.u.getDriver().findElement(By.linkText("Administración"));
			builder.moveToElement(element).build().perform();
			clickOpcion("Admnistrador de Encargado", AdmonEncargado);
			testCase.addScreenShotCurrentStep("u, Administrador de Encargado");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar click en Administración de Encargado");
		}

	}

	/**
	 * Ingresar Tema para validar Filtro en el Administrador de Preguntas click.
	 * 
	 * @author David Perez Couto
	 */

	public void validarFiltroEncargados() {

		try {

			// Activar el link para seleccionar Aplicacion
			u.highLight(locationAplic);
			testCase.addScreenShotCurrentStep("u, Seleccion de Aplicacion a Filtrar");
			u.unhighLight(locationAplic);
			u.click(locationAplic);
			testCase.addScreenShotCurrentStep("u, Seleccion de Aplicacion a Filtrar");
			u.hardWait(1);

			// Cambiar al frame para seleccionar Tema

			u.waitWebElement(By.cssSelector("#TB_iframeContent"), 20, 2);
			WebElement iframe = this.u.getDriver().findElement(By.cssSelector("#TB_iframeContent"));

			// Switch to the frame para llenar formulario
			u.getDriver().switchTo().frame(iframe);
			testCase.addScreenShotCurrentStep("u, Llenado de Formulario");

//			Seleccionar Tema
			u.highLight(locationAplicF);
			testCase.addScreenShotCurrentStep("u, Seleccion de Aplicacion a Filtrar");
			u.unhighLight(locationAplicF);
			u.click(locationAplicF);
			clickOpcion("Reporte Visibilidad de Articulos", locationAplicEdit);
			u.highLight(locationAplicF);
			u.unhighLight(locationAplicF);
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
	 * Agregar Aplicacion en el Administrador de Encargado de Preguntas click.
	 * 
	 * @author David Perez Couto
	 */

	public void crearAdmonEncargado() {

		try {

//			 Activar el boton para crear nueva aplicacion y poblar datos
			u.highLight(locationAddTopic);
			u.click(locationAddTopic);
			testCase.addScreenShotCurrentStep("u, Agregar Aplicacion");
//			Aplicacion
			u.highLight(locationAplicacion);
			u.click(locationAplicacion);
			clickOpcion("Folio de Pago Colombia", locationFolio);
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("u, Formulario");
//			Correo
			u.highLight(locationCorreo);
			u.sendKeys(locationCorreo, "aplicaciones@oxxo.com");
			u.hardWait(1);
//			Guarda			
			u.highLight(locationGuardarinput);
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
	 * Editar Reporte en el Administrador Encargado de Preguntas click.
	 * 
	 * @author David Perez Couto
	 */

	public void editarAdmonEncargado() {

		try {

//			 Seleciona Fila para editar Reporte
			u.highLight(locationFila);
			u.click(locationFila);
			testCase.addScreenShotCurrentStep("u, Editar Reporte");
//			 Activar el boton para editar Aviso y poblar datos
			u.highLight(locationEditTopic);
			testCase.addScreenShotCurrentStep("u, Editar Reporte");
			u.click(locationEditTopic);
			testCase.addScreenShotCurrentStep("u, Editar Reporte");
//			Aplicacion
			u.highLight(locationAplicacion);
			u.click(locationAplicacion);
			clickOpcion("Portal de Investigaciones", locationPortal);
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("u, Formulario");
//			Correo
			u.highLight(locationCorreo);
			clearInput(locationCorreo);
			u.sendKeys(locationCorreo, "pruebas1@oxxo.com");
			u.hardWait(1);

//			Guarda			
			u.highLight(locationGuardarinput);
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
	 * Eliminar Reporte en el Administrador Encargado de Preguntas click.
	 *
	 * @author David Perez Couto
	 */

	public void eliminarAdmonEncargado() {

		try {

//			 Seleciona Fila para eliminar aviso
			u.highLight(locationFila1);
			u.click(locationFila1);
			testCase.addScreenShotCurrentStep("u, Eliminar Reporte seleccionado");
//			 Activar el boton para eliminar pregunta
			u.highLight(locationEliminarTopic);
			u.click(locationEliminarTopic);
			u.hardWait(2);
			testCase.addScreenShotCurrentStep("u, Eliminar Reporte");
//			Se cierra ventana de Aceptar
			Alert alert = u.getDriver().switchTo().alert();
			u.hardWait(2);
			alert.accept();
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("u, Eliminar Reporte");
//			Operacion con exito
			u.highLight(locationExito);
			u.click(locationFooter);
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("u, Mensaje de Exito");

		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar click en Eliminar Reporte");
		}
	}

}
