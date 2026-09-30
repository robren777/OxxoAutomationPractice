package Mobile.preinventariosinteligentes.om;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class PlantillaTienda {
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;

	/**
	 * Constructor de la clase PlantillaTienda
	 * @author Marcos Morales
	 * @param au (AppiumUtil): objeto de la clase 
	 * @param testCase (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 */
	public PlantillaTienda(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
		this.testCase = testCase;
		this.au = au;
		this.baseExecution = baseExecution;
	}

	/*
	 * Localizadores
	 * */
	private By txtColaborador = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tv_gender_name");
	private By lstColaboradores = By.className("android.widget.ListView");
	private By lstColaboradoresPadre = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/rv_collaborator_schedule");
	private By btnSaveTemplate = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/save_template");
	private By lblMensaje = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/snackbar_text");


	/**
	 * Espera a que un elemento este visible en pantalla dentro de un periodo de tiempo
	 * @author Marcos Morales
	 * @param e Identificador del elemento
	 * @param timeout Tiempo maximo de espera
	 */
	public void waitForVisibility(WebElement e, Duration timeout) {
		WebDriverWait wait = new WebDriverWait(au.driver, timeout);
		try {
			wait.until(ExpectedConditions.visibilityOf(e));
		} catch (Exception ex) {
			throw new RuntimeException("El elemento no se volvió visible dentro del tiempo esperado: ", ex);
		}
	}

	/**
	 * Espera a que un elemento este visible en pantalla dentro de un periodo de tiempo
	 * @author Marcos Morales
	 * @param locator Identificador del elemento
	 * @param timeout Tiempo maximo de espera
	 */
	public void waitForVisibility(By locator, Duration timeout) {
		WebDriverWait wait = new WebDriverWait(au.driver, timeout);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception ex) {
			throw new RuntimeException("El elemento no se volvió visible dentro del tiempo esperado: ", ex);
		}
	}

	/**
	 * Espera a que un elemento se oculte dentro de un periodo de tiempo
	 * @author Marcos Morales
	 * @param locator Identificador del elemento
	 * @param timeout Tiempo maximo de espera
	 */
	public void waitForHidden(By locator, Duration timeout) {
		WebDriverWait wait = new WebDriverWait(au.driver, timeout);
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
		} catch (Exception ex) {
			throw new RuntimeException("El elemento todavía es visible después del tiempo de espera: " + locator, ex);
		}
	}

	/**
	 * Despliega el combo para seleccionar colaboradores
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void agregarColaborador(boolean screenShot) {
		waitForVisibility(txtColaborador, Duration.ofSeconds(30));
		if (screenShot) {
			testCase.addScreenShotCurrentStep(au, "testImage");
		}
		au.click(txtColaborador);
	}

	/**
	 * Hacer click en el Botón Guardar
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void clickBtnGuardarPlantilla(boolean screenShot) {
		waitForVisibility(btnSaveTemplate, Duration.ofSeconds(30));
		if (screenShot) {
			testCase.addScreenShotCurrentStep(au, "testImage");
		}
		au.click(btnSaveTemplate);
	}

	/**
	 * Selecciona el numero de colaboradores para agregar
	 * @author Marcos Morales
	 * @param cntcolab Numero de colaboradores que se agregaran
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void seleccionarColaborador(int cntcolab, boolean screenShot) {

		List<WebElement> colaboradoresList = au.driver.findElements(lstColaboradores);

		for (WebElement parentElement : colaboradoresList) {
			List<WebElement> childElements = parentElement.findElements(By.className("android.widget.TextView"));

			for (int i = 0; i < childElements.size(); i++) {
				if (i == 0) {
					// Condicion para saltar el primer elemento
					continue;
				}
				WebElement childElement = childElements.get(i);
				waitForVisibility(childElement, Duration.ofSeconds(30));
				childElement.click();

				if (i == cntcolab) {
					// Condicion para no hacer click en la caja de texto y volver a desplegar el combo al finalizar
					break;
				}
				au.click(txtColaborador);

				// Se vuelven a crear los objetos para identificar a los elementos padre-hijos debido a que el DOM se refresca
				parentElement = au.driver.findElement(By.className("android.widget.ListView"));
				waitForVisibility(parentElement, Duration.ofSeconds(30));
				childElements = parentElement.findElements(By.className("android.widget.TextView"));
			}
		}
		if (screenShot) {
			testCase.addScreenShotCurrentStep(au, "testImage");
		}
	}

	/**
	 * Selecciona el numero de colaboradores para agregar
	 * @author Marcos Morales
	 * @param indexIni Indica el indice a partir del cual seleccionar el colaborador
	 * @param numColab Cantidad de colaboradores para agregar
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void seleccionaColaboradorAgregaTurno(int indexIni, int numColab, boolean screenShot) {

		int index = indexIni;
		for(int i = 1; i <= numColab; i++) {
			WebElement colabSelect = au.driver.findElement(By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_gender_name\"])[" + index + "]"));
			waitForVisibility(colabSelect, Duration.ofSeconds(30));
			String nameColab = colabSelect.getText();
			colabSelect.click();

			WebElement colabTurn = au.driver.findElement(By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_collaborator_name\" and @text=\"" + nameColab + "\"]"));
			colabTurn.click();
			selectRadioButtonDayTurn(nameColab, 1);
			if (i == numColab) {
				// Condicion para no hacer click en la caja de texto y volver a desplegar el combo al finalizar
				break;
			}
			au.click(txtColaborador);
			index++;
		}
		if (screenShot) {
			testCase.addScreenShotCurrentStep(au, "testImage");
		}
	}

	/**
	 * Seleccionar un día y turno aletorio para cada colaborador
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void seleccionarDiaTurno(boolean screenShot) {

		List<WebElement> colaboradoresDiasList = au.driver.findElements(lstColaboradoresPadre);

		for (int i = 0; i < colaboradoresDiasList.size(); i++) {
			WebElement parentElement = colaboradoresDiasList.get(i);
			List<WebElement> childElements = parentElement.findElements(By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tv_collaborator_name"));

			for (int j = 0; j < childElements.size(); j++) {
				WebElement childElement = childElements.get(j);
				String childText = childElement.getText();
				childElement.click();
				int turno = j % 3;
				selectRadioButtonDayTurn(childText, turno);

				// Se vuelven a crear los objetos para identificar a los elementos hijo debido a que el DOM se refresca
				childElements = parentElement.findElements(By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tv_collaborator_name"));
			}
		}
		if (screenShot) {
			testCase.addScreenShotCurrentStep(au, "testImage");
		}
	}

	/**
	 * Selecciona el radiobutton correspondiente al turno: MAÑANA, TARDE, NOCHE
	 * @author Marcos Morales
	 * @param nameColab Colaborador
	 * @param turnoId Identificador para el turno
	 */
	public void selectRadioButtonDayTurn(String nameColab, int turnoId) {
		// Crear una instancia de Calendar
		Calendar calendario = Calendar.getInstance();

		// Obtener el día de la semana actual
		int diaSemana = calendario.get(Calendar.DAY_OF_WEEK);

		// Ajustar el índice para que lunes sea 0, martes sea 1, y así sucesivamente
		int indiceDia = (diaSemana + 5) % 7;

		// Obtener un número aleatorio según el día actual
		int randomDayIndex = getRandomDayIndex(indiceDia);

		List<List<String>> radioButtonIds = new ArrayList<>();
		radioButtonIds.add(List.of(
				"com.femco.oxxo.operaciones.mitienda.debug:id/radio_morning_monday",
				"com.femco.oxxo.operaciones.mitienda.debug:id/radio_afternoon_monday",
				"com.femco.oxxo.operaciones.mitienda.debug:id/radio_night_monday"
				));
		radioButtonIds.add(List.of(
				"com.femco.oxxo.operaciones.mitienda.debug:id/radio_morning_tuesday",
				"com.femco.oxxo.operaciones.mitienda.debug:id/radio_afternoon_tuesday",
				"com.femco.oxxo.operaciones.mitienda.debug:id/radio_night_tuesday"
				));
		radioButtonIds.add(List.of(
				"com.femco.oxxo.operaciones.mitienda.debug:id/radio_morning_wednesday",
				"com.femco.oxxo.operaciones.mitienda.debug:id/radio_afternoon_wednesday",
				"com.femco.oxxo.operaciones.mitienda.debug:id/radio_night_wednesday"
				));
		radioButtonIds.add(List.of(
				"com.femco.oxxo.operaciones.mitienda.debug:id/radio_morning_thursday",
				"com.femco.oxxo.operaciones.mitienda.debug:id/radio_afternoon_thursday",
				"com.femco.oxxo.operaciones.mitienda.debug:id/radio_night_thursday"
				));
		radioButtonIds.add(List.of(
				"com.femco.oxxo.operaciones.mitienda.debug:id/radio_morning_friday",
				"com.femco.oxxo.operaciones.mitienda.debug:id/radio_afternoon_friday",
				"com.femco.oxxo.operaciones.mitienda.debug:id/radio_night_friday"
				));
		radioButtonIds.add(List.of(
				"com.femco.oxxo.operaciones.mitienda.debug:id/radio_morning_saturday",
				"com.femco.oxxo.operaciones.mitienda.debug:id/radio_afternoon_saturday",
				"com.femco.oxxo.operaciones.mitienda.debug:id/radio_night_saturday"
				));
		radioButtonIds.add(List.of(
				"com.femco.oxxo.operaciones.mitienda.debug:id/radio_morning_sunday",
				"com.femco.oxxo.operaciones.mitienda.debug:id/radio_afternoon_sunday",
				"com.femco.oxxo.operaciones.mitienda.debug:id/radio_night_sunday"
				));

		List<String> dayRadioButtonIds = radioButtonIds.get(randomDayIndex);
		String turno = dayRadioButtonIds.get(turnoId);

		scrollToElementByText(turno);
		scrolling("id", "com.femco.oxxo.operaciones.mitienda.debug:id/rv_collaborator_schedule", "up", 0.5);

		//Se crea el objeto para colapsar el registro correspondiente
		WebElement element = au.driver.findElement(By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_collaborator_name\" and @text=\"" + nameColab + "\"]"));
		element.click();
	}

	/**
	 * Scroll hacia abajo hasta encontrar el objeto
	 * @author Marcos Morales
	 * @param idLocator Identificador del elemento
	 */
	public void scrollToElementByText(String idLocator) {
		By locator = By.id(idLocator);
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));

		boolean dato = isElementPresent(locator);

		while (!dato) {
			scrolling("id", "com.femco.oxxo.operaciones.mitienda.debug:id/rv_collaborator_schedule", "down", 0.5);
			dato = isElementPresent(locator);
		}

		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		element.click();

	}

	/**
	 * Identifica si el objeto es localizable
	 * @author Marcos Morales
	 * @param locator Identificador del elemento
	 */
	private boolean isElementPresent(By locator) {
		try {
			au.driver.findElement(locator);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Método para hacer scroll arriba, abajo, izquierda, derecha
	 * @author Marcos Morales
	 * @param locatorType Tipo de localizador: id, xpath, className, name, cssSelector
	 * @param locatorValue Identificador del elemento
	 * @param direction	Up, Down, Left, Right
	 * @param percent Porcentaje de desplazamiento
	 */
	public void scrolling(String locatorType, String locatorValue, String direction, double percent) {
		//Elemento al que se le puede aplicar scroll
		WebElement element = findElement(locatorType, locatorValue);

		//Uso de scroll con JavaScriptExecutor
		JavascriptExecutor js = (JavascriptExecutor) au.driver;
		Map<String, Object> scrollObject = new HashMap<>();
		scrollObject.put("element", ((RemoteWebElement)element).getId());
		scrollObject.put("direction", direction);
		scrollObject.put("percent", percent);
		js.executeScript("mobile: scrollGesture", scrollObject);
	}

	/**
	 * Retorna un elemento localizable
	 * @author Marcos Morales
	 * @param locatorType Tipo de localizador: id, xpath, className, name, cssSelector
	 * @param locatorValue Identificador del elemento
	 * @return 
	 */
	private WebElement findElement(String locatorType, String locatorValue) {
		switch (locatorType.toLowerCase()) {
		case "id":
			return au.driver.findElement(By.id(locatorValue));
		case "xpath":
			return au.driver.findElement(By.xpath(locatorValue));
		case "classname":
			return au.driver.findElement(By.className(locatorValue));
		case "name":
			return au.driver.findElement(By.name(locatorValue));
		case "cssselector":
			return au.driver.findElement(By.cssSelector(locatorValue));
		default:
			throw new IllegalArgumentException("Invalid locator type: " + locatorType);
		}
	}

	/**
	 * Espera a que se oculte el mensaje
	 * @author Marcos Morales
	 */
	public void esperaSeOculteMensaje() {
		waitForHidden(lblMensaje, Duration.ofSeconds(30));
	}

	/**
	 * Obtener un número aleatorio entre el día actual y los dos siguientes
	 * @author Marcos Morales
	 * @param dayIndex Indice del día considerando Lunes = 0, Martes = 1, ...
	 * @return days Valor entero correspondiente al día
	 */
	private static int getRandomDayIndex(int dayIndex) {
		Random random = new Random();
		int[] days;
		if (dayIndex <= 4) { // de lunes a viernes
			days = new int[]{dayIndex, (dayIndex + 1), (dayIndex + 2)};
		} else if (dayIndex == 5) { // sábado
			days = new int[]{5, 6}; // solo sábado y domingo
		} else { // domingo
			days = new int[]{6}; // solo domingo
		}
		return days[random.nextInt(days.length)];
	}

	/**
	 * Ejecuta el flujo para crear Plantillas de Colaboradores
	 * @author Marcos Morales
	 * @param user Usuario para ingresar a la aplicacion Oxxo al día
	 * @param pass Contraseña del usuario
	 * @param app Ej. com.femco.oxxo.operaciones.mitienda.debug
	 * @param cntcolab Numero de colaboradores para agregar
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void prerequisitoPlantillasColaboradores(String user, String pass, String app, int cntcolab, boolean screenShot) throws IOException, InterruptedException {

		Login login = new Login(testCase, au, baseExecution);
		Menus menus = new Menus(testCase, au, baseExecution);
		Comunes comunes = new Comunes(testCase, au, baseExecution);

		//Login
		login.login(user,pass);

		//Ingresar a Plantilla de Tienda
		menus.ingresarPlantillaTda(screenShot);

		//Agregar Colaborador
		agregarColaborador(screenShot);

		//Seleccionar Colaborador
		seleccionarColaborador(cntcolab, screenShot);

		//Seleccionar Dia y Turno
		seleccionarDiaTurno(screenShot);

		//Guardar Plantillas
		clickBtnGuardarPlantilla(screenShot);

		//Espera a que se oculte el mensaje de guardado
		esperaSeOculteMensaje();

		//Cerrar Aplicacion
		comunes.finalizarApp(app);
	}

	/**
	 * Valida el ingreso a la Pantalla de Plantilla de Tiendas
	 * @author Marcos Morales
	 */
	public void validaPantallaPlantillaTda() {
		waitForVisibility(txtColaborador, Duration.ofSeconds(30));
		waitForVisibility(btnSaveTemplate, Duration.ofSeconds(30));
		testCase.addScreenShotCurrentStep(au, "testImage");
	}
}