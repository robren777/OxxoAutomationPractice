package Mobile.preinventariosinteligentes.om;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class ConteosInteligentes {
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;
	private ModalConteos modalConteos;
	private ConteosFront frontConteos;

	/**
	 * Constructor de la clase ConteosInteligentes
	 * @author Marcos Morales
	 * @param au (AppiumUtil): objeto de la clase 
	 * @param testCase (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 */
	public ConteosInteligentes(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
		this.testCase = testCase;
		this.au = au;
		this.baseExecution = baseExecution;
	}

	/*
	 * Localizadores
	 * */
	private By txtBarcode = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/et_barcode");
	private By firstBtnPlus = By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_state_visibility\"])[1]");
	private By firstSelectivo = By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_group_name_preinventory\"])[1]");
	private By products = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/ct_product");
	private By firstProduct = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tv_product");
	private By lblResponsable = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tv_responsible");
	private By btnCancelPreinv = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_cancel_preinventory");
	private By btnSavePreinv = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_save_preinventory");
	private By btnFinishPreinv = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_finish_preinventory");
	private By msjAlGuardar = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tv_saving");
	private By lblConteosAct = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tv_counter_current");
	private By lblPorcentaje = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tv_counter_percent");

	private By txtFisicos = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/edt_find_sku_product");
	private By btnCountProdSave = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_count_product_save");
	private By btnCountProdCancel = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_count_product_cancel");

	private By skuProd = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tv_pre_sku");
	private By teoricoVal = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/etTheoricalQty");
	private By QtyCurrent = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvQtyCurrent");


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
	 * Metodo para tomar capturas de pantalla
	 * @author Marcos Morales
	 * @param screenShot Bandera true/false
	 */
	public void takeScreenshot(boolean screenShot) {
		if (screenShot) {
			testCase.addScreenShotCurrentStep(au, "testImage");
		}
	}

	/**
	 * Hacer click en el Botón Guardar Conteo
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void clickBtnGuardarCto(boolean screenShot) {
		waitForVisibility(btnCountProdSave, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(btnCountProdSave);
	}

	/**
	 * Hacer click en el Botón Cancelar guardado de conteo
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void clickBtnCancelarCto(boolean screenShot) {
		waitForVisibility(btnCountProdCancel, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(btnCountProdCancel);
	}

	/**
	 * Scroll hasta encontrar el objeto en la direccion especificada
	 * @author Marcos Morales
	 * @param locator Identificador del elemento que se requiere encontrar
	 * @param objScrollable Identificador del elemento al que se le puede aplicar scroll
	 * @param direction Direccion a la cual ejecutar el scroll Up, Down, Left, Right
	 * @param percent Porcentaje de la pantalla que abarca el scroll
	 */
	public void scrollToElement(By locator, By objScrollable, String direction, double percent) {

		//Inicia identificando si el elemento buscado se encuentra presente
		boolean elementPresent = isElementPresent(locator);

		//Mientras no encuentre el elemento aplicara scroll en la direccion especificada
		while (!elementPresent) {
			scrolling(objScrollable, direction, percent);
			elementPresent = isElementPresent(locator);
		}
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
	 * @param objScrollable Identificador del elemento al que se le puede aplicar scroll
	 * @param direction	Up, Down, Left, Right
	 * @param percent Porcentaje de desplazamiento
	 */
	public void scrolling(By objScrollable, String direction, double percent) {
		//Elemento al que se le puede aplicar scroll
		WebElement element = au.driver.findElement(objScrollable);

		//Uso de scroll con JavaScriptExecutor
		JavascriptExecutor js = (JavascriptExecutor) au.driver;
		Map<String, Object> scrollObject = new HashMap<>();
		scrollObject.put("element", ((RemoteWebElement)element).getId());
		scrollObject.put("direction", direction);
		scrollObject.put("percent", percent);
		js.executeScript("mobile: scrollGesture", scrollObject);
	}

	/**
	 * Hacer click en el Botón Cancelar
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void clickBtnCancelar(boolean screenShot) {
		waitForVisibility(btnCancelPreinv, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(btnCancelPreinv);
	}

	/**
	 * Hacer click en el Botón Guardar
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void clickBtnGuardar(boolean screenShot) {
		waitForVisibility(btnSavePreinv, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(btnSavePreinv);
	}

	/**
	 * Hacer click en el Botón Finalizar
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void clickBtnFinalizarPreInv(boolean screenShot) {
		waitForVisibility(btnFinishPreinv, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(btnFinishPreinv);
	}

	/**
	 * Genera una cadena de texto para obtener el titulo de la ventana del detalle de un preinventario
	 * @author Marcos Morales
	 */
	public static String obtenerSemanaActual() {
		LocalDate hoy = LocalDate.now();
		WeekFields weekFields = WeekFields.of(Locale.getDefault());
		int numeroSemana = hoy.get(weekFields.weekOfWeekBasedYear());
		int año = hoy.getYear();

		return String.format("SEMANA %d. %d", numeroSemana, año);
	}

	/**
	 * Valida el estado de los elementos para el titulo y codigo de barras
	 * al ingresar a la pantalla del detalle de un preinventario
	 * @author Marcos Morales
	 */
	public boolean[] retornaEstadoTituloBarcode() {
		boolean[] status = new boolean[2];
		try {
			waitForVisibility(txtBarcode, Duration.ofSeconds(30));
			String semanaStr = obtenerSemanaActual();
			status[0] = au.driver.findElement(By.xpath("//android.widget.TextView[@text=\"" + semanaStr + "\"]")).isDisplayed();
			status[1] = au.driver.findElement(txtBarcode).isDisplayed();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * Ejecuta el flujo para seleccionar una Clasificación: Proveedor, Planograma, Categorías
	 * @author Marcos Morales
	 * @param user Usuario para ingresar a la aplicacion Oxxo al día
	 * @param pass Contraseña del usuario
	 * @param app Ej. com.femco.oxxo.operaciones.mitienda.debug
	 * @param clasificacion proveedor, planograma, categorias
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void prerequisitoClasificacion(String user, String pass, String app, String clasificacion, boolean screenShot) throws IOException, InterruptedException {
		Login login = new Login(testCase, au, baseExecution);
		Menus menus = new Menus(testCase, au, baseExecution);
		Comunes comunes = new Comunes(testCase, au, baseExecution);

		//Iniciar sesion
		login.login(user,pass);

		//Ingresar a Conteos Inteligentes
		menus.ingresarConteosInteligentes(screenShot);

		//Semana en curso
		comunes.seleccionarSemana(screenShot);

		//Continuar
		comunes.clickBtnContinuar(screenShot);

		//Seleccionar Clasificacion
		comunes.seleccionarClasificacion(clasificacion, screenShot);

		//Continuar
		comunes.clickBtnContinuar(screenShot);
		comunes.clickBtnAceptar(screenShot);
		comunes.esperaBtnCrearPlantilla();

		//Cerrar Aplicacion
		comunes.finalizarApp(app);
	}

	/**
	 * Obtiene todos los grupos asignados a los colaboradores de plantillas
	 * @author Marcos Morales
	 */
	public ResultSet obtieneGruposAsignadosBD() {

		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			String query = "declare @tabla as table (\r\n"
					+ "	OPERADOR_ID INT,\r\n"
					+ "	OPERADOR_NAME NVARCHAR(MAX),\r\n"
					+ "	DIA NVARCHAR(MAX),\r\n"
					+ "	TURNO NVARCHAR(MAX),\r\n"
					+ "	GRUPO_ASIGNADO INT,\r\n"
					+ "	SKUS_POR_GRUPO INT\r\n"
					+ ");\r\n"
					+ "\r\n"
					+ "declare @tabla2 as table (\r\n"
					+ "	OPERADOR_ID INT,\r\n"
					+ "	OPERADOR_NAME NVARCHAR(MAX),\r\n"
					+ "	DIA NVARCHAR(MAX),\r\n"
					+ "	TURNO NVARCHAR(MAX),\r\n"
					+ "	GRUPOS_ASIGNADOS_POR_TURNO INT,\r\n"
					+ "	SKUS_ASIGNADOS_POR_TURNO INT\r\n"
					+ ");\r\n"
					+ "\r\n"
					+ "declare @tabla3 as table (\r\n"
					+ "	OPERADOR_ID INT,\r\n"
					+ "	OPERADOR_NAME NVARCHAR(MAX),\r\n"
					+ "	DIAS INT,\r\n"
					+ "	TURNOS INT,\r\n"
					+ "	GRUPOS_ASIGNADOS INT,\r\n"
					+ "	TOTAL_SKUS_ASIGNADOS INT\r\n"
					+ ");\r\n"
					+ "\r\n"
					+ ";WITH cte as (\r\n"
					+ "select\r\n"
					+ "	 OPERADOR_ID = II_OPERATOR_ID\r\n"
					+ "	,OPERADOR_NAME = (select top 1 COLLABORATOR_NAME from hc.II_STORE_TEMPLATE_DETAIL as dt where dt.COLLABORATOR_ID = II_OPERATOR_ID order by Id DESC)\r\n"
					+ "	,DIA = DATENAME(weekday, ar.II_ASSIGNMENT_DATE)\r\n"
					+ "	,TURNO = CASE ar.II_TURN_ID WHEN 1 THEN 'MAÑANA' WHEN 2 THEN 'TARDE' WHEN 3 THEN 'NOCHE' END\r\n"
					+ "	,GRUPO_ASIGNADO = ar.IO_FOLIO\r\n"
					+ "	,SKUS_POR_GRUPO = (select count (*) from hc.io_detail as id where id.IO_Folio = ar.IO_FOLIO)\r\n"
					+ "	from hc.II_ASSIGNMENTROLE as ar\r\n"
					+ "	join hc.II_HEADER as ih on ih.ii_automatic_role_assignment_date = ar.TM_CRT\r\n"
					+ "	where YEAR(ih.II_STARTDATE) = YEAR(GETDATE())\r\n"
					+ "	and ih.II_WEEKNUMBER = DATEPART(ISO_WEEK, GETDATE())\r\n"
					+ "	and ih.II_TYPE = 'I'\r\n"
					+ "\r\n"
					+ ")\r\n"
					+ "INSERT INTO @tabla\r\n"
					+ "SELECT\r\n"
					+ "OPERADOR_ID\r\n"
					+ ",OPERADOR_NAME\r\n"
					+ ",DIA\r\n"
					+ ",TURNO\r\n"
					+ ",GRUPO_ASIGNADO\r\n"
					+ ",SKUS_POR_GRUPO\r\n"
					+ "FROM cte;\r\n"
					+ "\r\n"
					+ "INSERT @tabla2\r\n"
					+ "select\r\n"
					+ " OPERADOR_ID\r\n"
					+ ",OPERADOR_NAME\r\n"
					+ ",DIA\r\n"
					+ ",TURNO\r\n"
					+ ",GRUPOS_ASIGNADOS_POR_TURNO = COUNT(GRUPO_ASIGNADO)\r\n"
					+ ",SKUS_ASIGNADOS_POR_TURNO = SUM(SKUS_POR_GRUPO)\r\n"
					+ "FROM @tabla\r\n"
					+ "GROUP BY OPERADOR_ID, OPERADOR_NAME, DIA, TURNO\r\n"
					+ "\r\n"
					+ "INSERT @tabla3\r\n"
					+ "select\r\n"
					+ "OPERADOR_ID\r\n"
					+ ",OPERADOR_NAME\r\n"
					+ ",DIAS = COUNT(DIA)\r\n"
					+ ",TURNOS = COUNT(TURNO)\r\n"
					+ ",GRUPOS_ASIGNADOS = SUM(GRUPOS_ASIGNADOS_POR_TURNO)\r\n"
					+ ",TOTAL_SKUS_ASIGNADOS = SUM(SKUS_ASIGNADOS_POR_TURNO)\r\n"
					+ "from @tabla2\r\n"
					+ "group by OPERADOR_ID, OPERADOR_NAME\r\n"
					+ "\r\n"
					+ "select OPERADOR_NAME, TOTAL_SKUS_ASIGNADOS from @tabla3 order by TOTAL_SKUS_ASIGNADOS desc";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Devuelve el total de SKUS asignados
	 * @author Marcos Morales
	 */
	public int totalSKUSAsignados(ResultSet resultSet) {
		int totalSkusBD = 0;
		try {
			while (resultSet.next()) {
				String colaborador = resultSet.getString(1);
				int totalSkusAsignados = resultSet.getInt(2);
				System.out.println("Colaborador: " + colaborador);
				System.out.println("SKUS: " + totalSkusAsignados);
				totalSkusBD += totalSkusAsignados;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalSkusBD;
	}

	/**
	 * Ejecuta el flujo para realizar la asignación Automática en Conteos Inteligentes
	 * @author Lizbeth Hernandez
	 * @param user Usuario para ingresar a la aplicacion Oxxo al día
	 * @param pass Contraseña del usuario
	 * @param app Ej. com.femco.oxxo.operaciones.mitienda.debug
	 * @param clasificacion proveedor, planograma, categorias
	 * @param numColab Cantidad de colaboradores para agregar
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void prerequisitoAsigAutomatica(String user, String pass, String app, String clasificacion, int numColab, boolean screenShot) throws IOException, InterruptedException {
		Login login = new Login(testCase, au, baseExecution);
		Menus menus = new Menus(testCase, au, baseExecution);
		Comunes comunes = new Comunes(testCase, au, baseExecution);
		PlantillaTienda plantillaTda = new PlantillaTienda(testCase, au, baseExecution);

		//Iniciar sesion
		login.login(user,pass);

		//Ingresar a Conteos Inteligentes
		menus.ingresarConteosInteligentes(screenShot);

		//Semana en curso
		comunes.seleccionarSemana(screenShot);

		//Continuar
		comunes.clickBtnContinuar(screenShot);

		//Seleccionar Clasificacion
		comunes.seleccionarClasificacion(clasificacion, screenShot);

		//Continuar
		comunes.clickBtnContinuar(screenShot);
		comunes.clickBtnAceptar(screenShot);
		comunes.esperaBtnCrearPlantilla();

		//Tap en boton CREAR PLANTILLA
		comunes.clickBtnAceptar(screenShot);

		//Añadir colaboradores y seleccionar Turno
		plantillaTda.agregarColaborador(screenShot);
		plantillaTda.seleccionarColaborador(numColab, screenShot);
		plantillaTda.seleccionarDiaTurno(screenShot);

		//Tap en boton Guardar
		plantillaTda.clickBtnGuardarPlantilla(screenShot);

		//Tap en boton CREAR ASIGNACIONES
		comunes.clickBtnAceptar(screenShot);

		//Ingresa a la vista preliminar de las asignaciones Automaticas
		comunes.esperaSeOculteLoading();

		//Cerrar Aplicacion
		comunes.finalizarApp(app);
	}

	/**
	 * Metodo para capturar multiples conteos y seleccionar Cancelar o Guardar
	 * @author Marcos Morales
	 * @param resultSet Resultado de la consulta a bd con los SKUS a capturar
	 * @param valorConteo Conteo físico a capturar
	 * @param locator Identicador del boton para Guardar o Cancelar
	 * @param screenShot Toma captura de pantalla: true/false
	 */
	public void ingresarConteo(ResultSet resultSet, String valorConteo, By locator, boolean screenShot) throws SQLException {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		while (resultSet.next()) {
			String sku = resultSet.getString(1);
			baseExecution.addStep("Seleccionar o escanear el producto a contabilizar");
			au.sendKeys(txtBarcode, sku);
			takeScreenshot(screenShot);
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			waitForVisibility(txtFisicos, Duration.ofSeconds(30));
			baseExecution.addStep("Ingresar la cantidad Fisica");
			au.sendKeys(txtFisicos, valorConteo);
			takeScreenshot(screenShot);
			baseExecution.addStep("Dar clic en opcion GUARDAR");
			takeScreenshot(screenShot);
			au.click(locator);
		}
	}

	/**
	 * Metodo para capturar un conteo sin ejecutar accion de Guardado o Cancelacion
	 * @author Marcos Morales
	 * @param resultSet Resultado de la consulta a bd con los SKUS a capturar
	 * @param valorConteo Conteo físico a capturar
	 */
	public void ingresarConteo(ResultSet resultSet, String valorConteo) throws SQLException {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		while (resultSet.next()) {
			String sku = resultSet.getString(1);
			au.sendKeys(txtBarcode, sku);
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			waitForVisibility(txtFisicos, Duration.ofSeconds(30));
			au.sendKeys(txtFisicos, valorConteo);
		}
	}

	/**
	 * Metodo para capturar un conteo y seleccionar Guardar o Cancelar
	 * @author Marcos Morales
	 * @param resultSet Resultado de la consulta a bd con los SKUS a capturar
	 * @param valorConteo Conteo físico a capturar
	 * @param locator Elemento del boton Guardar o Cancelar
	 */
	public void ingresarConteo(ResultSet resultSet, String valorConteo, By locator) throws SQLException {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		while (resultSet.next()) {
			String sku = resultSet.getString(1);
			au.sendKeys(txtBarcode, sku);
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			waitForVisibility(txtFisicos, Duration.ofSeconds(30));
			au.sendKeys(txtFisicos, valorConteo);
			au.click(locator);
		}
	}

	/**
	 * Metodo para capturar TODOs los conteos del preinventario
	 * @author Marcos Morales
	 * @param resultSet Resultado de la consulta a bd con los SKUS a capturar
	 */
	public void ingresarTodosLosConteos(ResultSet resultSet) throws SQLException {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		while (resultSet.next()) {
			int conteo = ThreadLocalRandom.current().nextInt(0, 15);
			String valorConteo = Integer.toString(conteo);
			String sku = resultSet.getString(1);
			au.sendKeys(txtBarcode, sku);
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			waitForVisibility(txtFisicos, Duration.ofSeconds(30));
			au.sendKeys(txtFisicos, valorConteo);
			au.click(btnCountProdSave);
		}
	}

	/**
	 * Metodo para capturar multiples conteos
	 * @author Marcos Morales
	 * @param resultSet Resultado de la consulta a bd con los SKUS a capturar
	 * @param tipo Parametro para indicar si el valor capturado del conteo es entero o decimal: "entero", "decimal"
	 * @param conteoPrevio Bandera que identifica si el conteo es nuevo o no: true/false
	 * @param debeGuardar Bandera que define si guarda en memoria los datos de sku, teorico y fisico para el regitro actual
	 */
	public void ingresarConteos(ResultSet resultSet, String tipo, boolean conteoPrevio, boolean debeGuardar) throws SQLException {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		while (resultSet.next()) {
			int conteo = ThreadLocalRandom.current().nextInt(0, 15);
			String valorConteo = Integer.toString(conteo);
			String sku = resultSet.getString(1);
			au.sendKeys(txtBarcode, sku);
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			waitForVisibility(txtFisicos, Duration.ofSeconds(30));
			au.sendKeys(txtFisicos, valorConteo);
			switch (tipo.toLowerCase()) {
			case "entero":
				extraerValoresModalConteosEnteros(conteoPrevio, debeGuardar);
				break;
			case "decimal":
				extraerValoresModalConteosDecimales(conteoPrevio, debeGuardar);
				break;
			}
			au.click(btnCountProdSave);
		}
	}

	/**
	 * Metodo para capturar n conteos de un preinventario
	 * @author Marcos Morales
	 * @param numSKUS Cantidad de SKUs a los que se capturara un conteo
	 * @param valorConteo Conteo físico a capturar
	 * @param screenShot Toma captura de pantalla: true/false
	 */
	public void registrarMultiplesConteos(int numSKUS, String valorConteo, boolean screenShot) throws SQLException, AssertionError {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		ResultSet rs = obtieneSKUSBD(numSKUS);

		if (rs.isBeforeFirst()) {
			ingresarConteo(rs, valorConteo, btnCountProdSave, screenShot);
		} else {
			throw new AssertionError("No se encontraron registros al ejecutar la consulta del metodo: obtieneSKUSBD");
		}
	}

	/**
	 * Metodo para capturar n conteos de un preinventario sin generar la evidencia de captura
	 * @author Marcos Morales
	 * @param numSKUS Cantidad de SKUs a los que se capturara un conteo
	 * @param debeGuardar Bandera que define si guarda en memoria los datos de sku, teorico y fisico para el regitro actual
	 */
	public void registrarMultiplesConteos(int numSKUS, boolean debeGuardar) throws SQLException, AssertionError {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		ResultSet rs = obtieneSKUSBD(numSKUS);

		if (rs.isBeforeFirst()) {
			ingresarConteos(rs, "entero", false, debeGuardar);
		} else {
			throw new AssertionError("No se encontraron registros al ejecutar la consulta del metodo: obtieneSKUSBD");
		}
	}

	/**
	 * Metodo para capturar TODOs los conteos del preinventario
	 * @author Marcos Morales
	 */
	public void registrarTodosLosConteos() throws SQLException, AssertionError {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		ResultSet rs = obtieneTodosLosSKUsBD();

		if (rs.isBeforeFirst()) {
			ingresarTodosLosConteos(rs);
		} else {
			throw new AssertionError("No se encontraron registros al ejecutar la consulta del metodo: obtieneSKUSBD");
		}
	}

	/**
	 * Realiza el conteo de n productos tomando los SKUS desde la BD por colaborador
	 * @author Marcos Morales
	 * @param numSKUS Cantidad de skus a contar
	 * @param valorConteo Valor a capturar para cada conteo (físico)
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 * @throws SQLException 
	 */
	public void registrarConteoPorColaborador(int numSKUS, String valorConteo, boolean screenShot) throws SQLException {

		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		ResultSet rs = obtieneSkusPorColaboradorBD(numSKUS);

		if (rs.isBeforeFirst()) {
			ingresarConteo(rs, valorConteo, btnCountProdSave, screenShot);
		} else {
			throw new AssertionError("No se encontraron registros al ejecutar la consulta del metodo: obtieneSkusPorColaboradorBD");
		}
	}

	/**
	 * Despliega el primer selectivo
	 * @author Marcos Morales
	 */
	public void clickBtnPlus() {
		au.click(firstBtnPlus);
	}

	/**
	 * Despliega el primer selectivo
	 * @author Marcos Morales
	 */
	public void clickSelectivo() throws InterruptedException {
		waitForVisibility(firstSelectivo, Duration.ofSeconds(30));
		au.click(firstSelectivo);
		Thread.sleep(1000);
	}

	/**
	 * Obtiene el valor del elemento al desplegar el primer selectivo
	 * @author Marcos Morales
	 * @return txtBtnPlus Contiene el valor +/-
	 */
	public String obtieneTextoBtnPlus() {
		String txtBtnPlus = au.driver.findElement(firstBtnPlus).getText();
		return txtBtnPlus;
	}

	/**
	 * Metodo para validar el autoguardado
	 * @author Marcos Morales
	 * @param timeout Tiempo de espera para visualizar el mensaje de guardado
	 */
	public boolean retornaEstadoAutoGuardado(Duration timeout) {
		WebDriverWait wait = new WebDriverWait(au.driver, timeout);
		boolean isMessageDisplayed = false;
		try {
			WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(msjAlGuardar));
			isMessageDisplayed = messageElement.isDisplayed();
		} catch (TimeoutException e) {
			isMessageDisplayed = false;
		}
		return isMessageDisplayed;
	}

	/**
	 * Ejecuta la consulta que obtiene la cantidad de skus por selectivo
	 * @author Marcos Morales
	 * @param numReg Numero de registros a obtener
	 * @param tipoConsulta Todos los selectivos o por colaborador: "todos", "colaborador"
	 */
	public ResultSet consultaSkusPorSelectivoBD(String numReg, String tipoConsulta) {

		String colabName = getTextColaborador();

		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			switch (tipoConsulta.toLowerCase()) {
			case "todos":
				String queryTodos = ";WITH Selectivos AS (\r\n"
						+ "	select	 IOH.IO_Folio [Folio]\r\n"
						+ "			,UPPER(SUBSTRING(IOH.IO_Description, CHARINDEX('_', IOH.IO_Description) + 1, LEN(IOH.IO_Description))) [Selectivo]\r\n"
						+ "			,ROW_NUMBER() OVER (PARTITION BY IOH.IO_Description ORDER BY (SELECT NULL)) [NumReg]\r\n"
						+ "	from [Satelites].[hc].[II_HEADER_IO_HEADER] as R\r\n"
						+ "	inner join [Satelites].[hc].[II_HEADER] as IIH on (IIH.II_FOLIO = R.II_FOLIO)\r\n"
						+ "	inner join [Satelites].[hc].[io_header] as IOH on (IOH.IO_Folio = R.IO_FOLIO)\r\n"
						+ "	inner join [Satelites].[hc].[io_detail] as IOD on  (IOD.IO_Folio = IOH.IO_Folio)\r\n"
						+ "	where IIH.II_TYPE = 'I'\r\n"
						+ ")\r\n"
						+ "SELECT TOP " + numReg + " Folio\r\n"
						+ "		,Selectivo\r\n"
						+ "		,COUNT(*) AS Cantidad\r\n"
						+ "FROM Selectivos\r\n"
						+ "GROUP BY\r\n"
						+ "	Folio\r\n"
						+ "    ,Selectivo\r\n"
						+ "    ,((NumReg - 1) / 30)\r\n"
						+ "ORDER BY\r\n"
						+ "    Folio\r\n"
						+ "    ,((NumReg - 1) / 30);";

				resultSet = statement.executeQuery(queryTodos);
				break;
			case "colaborador":
				String queryColab = ";WITH Selectivos AS (\r\n"
						+ "	select	 IOH.IO_Folio [Folio]\r\n"
						+ "			,IISTD.COLLABORATOR_NAME [Colaborador]\r\n"
						+ "			,UPPER(SUBSTRING(IOH.IO_Description, CHARINDEX('_', IOH.IO_Description) + 1, LEN(IOH.IO_Description))) [Selectivo]\r\n"
						+ "			,ROW_NUMBER() OVER (PARTITION BY IOH.IO_Description ORDER BY (SELECT NULL)) [NumReg]\r\n"
						+ "	from [Satelites].[hc].[II_HEADER_IO_HEADER] as R\r\n"
						+ "	inner join [Satelites].[hc].[II_HEADER] as IIH on (IIH.II_FOLIO = R.II_FOLIO)\r\n"
						+ "	inner join [Satelites].[hc].[io_header] as IOH on (IOH.IO_Folio = R.IO_FOLIO)\r\n"
						+ "	inner join [Satelites].[hc].[io_detail] as IOD on  (IOD.IO_Folio = IOH.IO_Folio)\r\n"
						+ "	inner join Satelites.hc.II_ASSIGNMENTROLE IIA on (IOD.IO_Folio = IIA.IO_FOLIO)\r\n"
						+ "	inner join Satelites.hc.II_STORE_TEMPLATE_DETAIL IISTD on (IIA.II_OPERATOR_ID = IISTD.COLLABORATOR_ID)\r\n"
						+ "	where IIH.II_TYPE = 'I' and IISTD.COLLABORATOR_NAME like '" + colabName + "%'\r\n"
						+ ")\r\n"
						+ "SELECT TOP " + numReg + " Folio\r\n"
						+ "		,Selectivo\r\n"
						+ "		,COUNT(*) AS Cantidad\r\n"
						+ "FROM Selectivos\r\n"
						+ "GROUP BY\r\n"
						+ "	Folio\r\n"
						+ "    ,Colaborador\r\n"
						+ "    ,Selectivo\r\n"
						+ "    ,((NumReg - 1) / 30)\r\n"
						+ "ORDER BY\r\n"
						+ "    Folio\r\n"
						+ "    ,((NumReg - 1) / 30);";

				resultSet = statement.executeQuery(queryColab);
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Ejecuta la consulta que obtiene la cantidad de skus por selectivo por grupo
	 * @author Marcos Morales
	 * @param numReg Numero de registros a obtener
	 * @param tipoConsulta Todos los selectivos o por colaborador: "todos", "colaborador"
	 */
	public ResultSet consultaSkusPorSelectivoGpoBD(String numReg, String tipoConsulta) {

		String colabName = getTextColaborador();

		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			switch (tipoConsulta.toLowerCase()) {
			case "todos":
				String queryTodos = ";WITH Selectivos AS (\r\n"
						+ "	select	 IOH.IO_Folio [Folio]\r\n"
						+ "			,UPPER(SUBSTRING(IOH.IO_Description, CHARINDEX('_', IOH.IO_Description) + 1, LEN(IOH.IO_Description))) [Selectivo]\r\n"
						+ "			,ROW_NUMBER() OVER (PARTITION BY IOH.IO_Description ORDER BY (SELECT NULL)) [NumReg]\r\n"
						+ "	from [Satelites].[hc].[II_HEADER_IO_HEADER] as R\r\n"
						+ "	inner join [Satelites].[hc].[II_HEADER] as IIH on (IIH.II_FOLIO = R.II_FOLIO)\r\n"
						+ "	inner join [Satelites].[hc].[io_header] as IOH on (IOH.IO_Folio = R.IO_FOLIO)\r\n"
						+ "	inner join [Satelites].[hc].[io_detail] as IOD on  (IOD.IO_Folio = IOH.IO_Folio)\r\n"
						+ "	where IIH.II_TYPE = 'I'\r\n"
						+ ")\r\n"
						+ "SELECT TOP " + numReg + " Folio\r\n"
						+ "		,Selectivo\r\n"
						+ "		,COUNT(*) AS Cantidad\r\n"
						+ "FROM Selectivos\r\n"
						+ "GROUP BY\r\n"
						+ "	Folio\r\n"
						+ "    ,Selectivo\r\n"
						+ "    ,((NumReg - 1) / 30)\r\n"
						+ "HAVING\r\n"
						+ "	COUNT(*) = 30\r\n"
						+ "ORDER BY\r\n"
						+ "    Folio\r\n"
						+ "    ,((NumReg - 1) / 30);";

				resultSet = statement.executeQuery(queryTodos);
				break;
			case "colaborador":
				String queryColab = ";WITH Selectivos AS (\r\n"
						+ "	select	 IOH.IO_Folio [Folio]\r\n"
						+ "			,IISTD.COLLABORATOR_NAME [Colaborador]\r\n"
						+ "			,UPPER(SUBSTRING(IOH.IO_Description, CHARINDEX('_', IOH.IO_Description) + 1, LEN(IOH.IO_Description))) [Selectivo]\r\n"
						+ "			,ROW_NUMBER() OVER (PARTITION BY IOH.IO_Description ORDER BY (SELECT NULL)) [NumReg]\r\n"
						+ "	from [Satelites].[hc].[II_HEADER_IO_HEADER] as R\r\n"
						+ "	inner join [Satelites].[hc].[II_HEADER] as IIH on (IIH.II_FOLIO = R.II_FOLIO)\r\n"
						+ "	inner join [Satelites].[hc].[io_header] as IOH on (IOH.IO_Folio = R.IO_FOLIO)\r\n"
						+ "	inner join [Satelites].[hc].[io_detail] as IOD on  (IOD.IO_Folio = IOH.IO_Folio)\r\n"
						+ "	inner join Satelites.hc.II_ASSIGNMENTROLE IIA on (IOD.IO_Folio = IIA.IO_FOLIO)\r\n"
						+ "	inner join Satelites.hc.II_STORE_TEMPLATE_DETAIL IISTD on (IIA.II_OPERATOR_ID = IISTD.COLLABORATOR_ID)\r\n"
						+ "	where IIH.II_TYPE = 'I' and IISTD.COLLABORATOR_NAME like '" + colabName + "%'\r\n"
						+ ")\r\n"
						+ "SELECT TOP " + numReg + " Folio\r\n"
						+ "		,Selectivo\r\n"
						+ "		,COUNT(*) AS Cantidad\r\n"
						+ "FROM Selectivos\r\n"
						+ "GROUP BY\r\n"
						+ "	Folio\r\n"
						+ "    ,Colaborador\r\n"
						+ "    ,Selectivo\r\n"
						+ "    ,((NumReg - 1) / 30)\r\n"
						+ "HAVING\r\n"
						+ "	COUNT(*) = 30\r\n"
						+ "ORDER BY\r\n"
						+ "    Folio\r\n"
						+ "    ,((NumReg - 1) / 30);";

				resultSet = statement.executeQuery(queryColab);
				break;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Realiza la validacion de skus por selectivo para grupos con 30 o mas skus
	 * @author Marcos Morales
	 * @param tipoConsulta Todos los selectivos o por colaborador: "todos", "colaborador"
	 * @throws SQLException 
	 */
	public void validaSkusAgrupados30(String tipoConsulta) throws SQLException {

		String selectivoBD = "";

		//Busca en Base de datos el primer registro con 30 SKUS
		ResultSet rs = consultaSkusPorSelectivoGpoBD("1", tipoConsulta);

		//El resultado de la consulta, genera un string para utilizarlo en el xpath y localizar el elemento
		if (rs.next()) {
			String selectBD = rs.getString(2);
			int cantBD = rs.getInt(3);
			selectivoBD = selectBD + " - " + cantBD + " SKU";
		} else {
			throw new AssertionError("No se encontraron registros al ejecutar la consulta del metodo: consultaSkusPorSelectivoGpoBD");
		}

		//Crea el xpath con el string generado de la consulta
		By locator = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_group_name_preinventory\" and @text=\"" + selectivoBD + "\"]");
		By objScrollable = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/rv_group_products");

		//Se valida el elemento en pantalla y se hace scroll en caso de requerirlo
		scrollToElement(locator, objScrollable, "down", 0.9);
	}

	/**
	 * Obtiene el nombre del colaborador asignado a un selectivo
	 * @author Marcos Morales
	 */
	public String getTextColaborador() {
		String textName = au.driver.findElement(lblResponsable).getText();
		String colabName = textName.replace("Asignado a: ", "");
		return colabName;
	}

	/**
	 * Obtiene el primer SKU del segundo selectivo
	 * @author Marcos Morales
	 * @param tipoConsulta Todos los selectivos o por colaborador: "todos", "colaborador"
	 */
	public ResultSet obtienePrimerSKUSegundoSelectivo(String tipoConsulta) {

		String colabName = getTextColaborador();

		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			switch (tipoConsulta.toLowerCase()) {
			case "todos":
				String queryTodos = ";WITH foliosSKUS AS (\r\n"
						+ "    SELECT IO_Folio, ROW_NUMBER() OVER (ORDER BY IO_Folio) AS row_num\r\n"
						+ "    FROM (SELECT DISTINCT IO_Folio FROM hc.io_detail\r\n"
						+ "		  WHERE YEAR(CREATED_DATETIME) = YEAR(GETDATE())\r\n"
						+ "		  AND DATEPART(WEEK, CREATED_DATETIME) = DATEPART(WEEK, GETDATE())) AS folios_unicos\r\n"
						+ ")\r\n"
						+ "SELECT TOP 1 TRIM(IO_SKU_REF) [SKU]\r\n"
						+ "FROM hc.io_detail\r\n"
						+ "WHERE IO_Folio = (SELECT IO_Folio FROM foliosSKUS WHERE row_num = 2)";

				resultSet = statement.executeQuery(queryTodos);
				break;
			case "colaborador":
				String queryColab = ";WITH foliosSKUS AS (\r\n"
						+ "SELECT IO_Folio, ROW_NUMBER() OVER (ORDER BY IO_Folio) AS row_num\r\n"
						+ "FROM (select DISTINCT(d.IO_Folio)\r\n"
						+ "		from hc.io_detail d\r\n"
						+ "		inner join hc.II_ASSIGNMENTROLE ar on (d.IO_Folio = ar.IO_FOLIO)\r\n"
						+ "		inner join hc.II_STORE_TEMPLATE_DETAIL sd on (ar.II_OPERATOR_ID = sd.COLLABORATOR_ID)\r\n"
						+ "		WHERE YEAR(CREATED_DATETIME) = YEAR(GETDATE())\r\n"
						+ "		AND DATEPART(ISO_WEEK, CREATED_DATETIME)  = DATEPART(ISO_WEEK, GETDATE())\r\n"
						+ "		AND sd.COLLABORATOR_NAME LIKE '" + colabName + "%') AS folios_unicos\r\n"
						+ ")\r\n"
						+ "SELECT TOP 1 TRIM(IO_SKU_REF) [SKU]\r\n"
						+ "FROM hc.io_detail\r\n"
						+ "WHERE IO_Folio = (SELECT IO_Folio FROM foliosSKUS WHERE row_num = 2)";

				resultSet = statement.executeQuery(queryColab);
				break;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Espera a que desaparezca el mensaje de guardado
	 * @author Marcos Morales
	 */
	public void esperaSeOculteMensajeGuardado() {
		waitForHidden(msjAlGuardar, Duration.ofSeconds(30));
	}

	/**
	 * Devuelve el listado de productos a contar en una plantilla
	 * @author Marcos Morales
	 */
	public List<WebElement> listaProductos() {
		List<WebElement> productos = au.driver.findElements(products);
		return productos;
	}

	/**
	 * Devuelve la cantidad de SKUS mostrados en la App
	 * @author Marcos Morales
	 */
	public int retornaSkusIMA() {
		String skusIMA = au.driver.findElement(lblConteosAct).getText();
		int totalSkusIMA = Integer.parseInt(skusIMA.split(" de ")[1]);
		return totalSkusIMA;
	}

	/**
	 * Devuelve el porcentaje de cumplimiento mostrado en la App
	 * @author Marcos Morales
	 */
	public double obtienePorcentajeDeCumplimiento() {
		String porcentajeCob = au.driver.findElement(lblPorcentaje).getText();
		double porcentaje = Double.parseDouble(porcentajeCob.substring(1));
		return porcentaje;
	}

	/**
	 * Obtiene la cantidad de articulos a contar proporcionados por IMA para un Colaborador
	 * @author Marcos Morales
	 */
	public ResultSet obtieneSkusIMAColaboradorBD() {
		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			String query = "SET LANGUAGE Spanish;\r\n"
					+ ";WITH cte as (\r\n"
					+ "select	 OPERADOR_ID = II_OPERATOR_ID\r\n"
					+ "		,OPERADOR_NAME = (select top 1 COLLABORATOR_NAME from hc.II_STORE_TEMPLATE_DETAIL as dt where dt.COLLABORATOR_ID = II_OPERATOR_ID order by Id DESC)\r\n"
					+ "		,DIA = UPPER(DATENAME(weekday, ar.II_ASSIGNMENT_DATE))\r\n"
					+ "		,TURNO = CASE ar.II_TURN_ID WHEN 1 THEN 'MAÑANA' WHEN 2 THEN 'TARDE' WHEN 3 THEN 'NOCHE' END\r\n"
					+ "		,ar.II_TURN_ID\r\n"
					+ "		,GRUPO_ASIGNADO = ar.IO_FOLIO\r\n"
					+ "		,SKUS_POR_GRUPO = (select count (*) from hc.io_detail as id where id.IO_Folio = ar.IO_FOLIO)\r\n"
					+ "from hc.II_ASSIGNMENTROLE as ar\r\n"
					+ "inner join hc.II_HEADER as ih on ih.ii_automatic_role_assignment_date = ar.TM_CRT\r\n"
					+ "where YEAR(ih.II_STARTDATE) = YEAR(GETDATE())\r\n"
					+ "and ih.II_WEEKNUMBER = DATEPART(ISO_WEEK, GETDATE())\r\n"
					+ "and ih.II_TYPE = 'I'\r\n"
					+ ")\r\n"
					+ "select top 1 OPERADOR_ID\r\n"
					+ "		,OPERADOR_NAME\r\n"
					+ "		,DIA\r\n"
					+ "		,TURNO\r\n"
					+ "		,II_TURN_ID\r\n"
					+ "		,GRUPOS_ASIGNADOS_POR_TURNO = COUNT(GRUPO_ASIGNADO)\r\n"
					+ "		,SKUS_ASIGNADOS_POR_TURNO = SUM(SKUS_POR_GRUPO)\r\n"
					+ "FROM cte\r\n"
					+ "GROUP BY OPERADOR_ID, OPERADOR_NAME, DIA, TURNO, II_TURN_ID\r\n"
					+ "ORDER BY OPERADOR_NAME;";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Devuelve la cantidad de articulos a contar proporcionados por IMA para un Colaborador
	 * @author Marcos Morales
	 * @param resultSet Resultado de la consulta en BD
	 */
	public int skusIMAColaborador(ResultSet resultSet) {
		int totalSkusBD = 0;
		try {
			if (resultSet.next()) {
				totalSkusBD = resultSet.getInt(7);
			} else {
				throw new AssertionError("No se encontraron registros en el resultSet");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalSkusBD;
	}

	/**
	 * Obtiene los SKUS de un colaborador en especifico
	 * @author Marcos Morales
	 * @param numSKUS Cantidad de registros a obtener de la consulta
	 */
	public ResultSet obtieneSkusPorColaboradorBD(int numSKUS) {

		String colabName = getTextColaborador();

		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			String query = "select top " + numSKUS + " trim(d.IO_SKU_REF) [SKU]\r\n"
					+ "from hc.io_detail d\r\n"
					+ "inner join hc.II_ASSIGNMENTROLE ar on (d.IO_Folio = ar.IO_FOLIO)\r\n"
					+ "inner join hc.II_STORE_TEMPLATE_DETAIL sd on (ar.II_OPERATOR_ID = sd.COLLABORATOR_ID)\r\n"
					+ "WHERE YEAR(CREATED_DATETIME) = YEAR(GETDATE())\r\n"
					+ "AND DATEPART(ISO_WEEK, CREATED_DATETIME)  = DATEPART(ISO_WEEK, GETDATE())\r\n"
					+ "and sd.COLLABORATOR_NAME like '" + colabName + "%'\r\n"
					+ "order by d.IO_Folio";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Valida el focus en la caja de texto para ingresar skus
	 * @author Marcos Morales
	 */
	public boolean retornaCursorActivoEnCampoIngresarSKU() {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		WebElement focusBarcode = au.driver.findElement(txtBarcode);
		return Boolean.parseBoolean(focusBarcode.getAttribute("focused"));
	}

	/**
	 * Devuelve true si el teclado es visible en pantalla
	 * @author Marcos Morales
	 */
	public boolean retornaTecladoVisibleEnCampoIngresarSKU() {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		return au.driver.isKeyboardShown();
	}

	/**
	 * Valida el focus en la caja de texto para ingresar el conteo
	 * @author Marcos Morales
	 */
	public boolean retornaCursorActivoEnCampoIngresarConteo() {
		waitForVisibility(txtFisicos, Duration.ofSeconds(30));
		WebElement focusBarcode = au.driver.findElement(txtFisicos);
		return Boolean.parseBoolean(focusBarcode.getAttribute("focused"));
	}

	/**
	 * Devuelve true si el teclado es visible en pantalla
	 * @author Marcos Morales
	 */
	public boolean retornaTecladoVisibleEnCampoIngresarConteo() {
		waitForVisibility(txtFisicos, Duration.ofSeconds(30));
		return au.driver.isKeyboardShown();
	}

	/**
	 * Hacer click en la caja de texto para ingresar un SKU
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void clickTxtBarcode(boolean screenShot) {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(txtBarcode);
	}

	/**
	 * Obtiene n cantidad de SKUS para captura de conteos
	 * @author Marcos Morales para captura de conteos
	 * @param numReg Cantidad de registros a obtener de la consulta
	 */
	public ResultSet obtieneSKUSBD(int numReg) {

		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			String query = "SELECT TOP " + numReg + " TRIM(d.IO_SKU_REF) FROM HC.IO_DETAIL d\r\n"
					+ "inner join hc.II_HEADER_IO_HEADER hh on d.IO_Folio = hh.IO_FOLIO\r\n"
					+ "WHERE YEAR(CREATED_DATETIME) = YEAR(GETDATE())\r\n"
					+ "AND DATEPART(ISO_WEEK, CREATED_DATETIME) = DATEPART(WEEK, GETDATE())\r\n"
					+ "and hh.II_FOLIO = (SELECT II_FOLIO FROM [HC].[II_HEADER] WHERE YEAR(II_STARTDATE) = YEAR(GETDATE()) AND II_WEEKNUMBER = DATEPART(WEEK, GETDATE()) AND II_TYPE = 'I')\r\n"
					+ "order by d.IO_Folio, d.IO_SKU_Order";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Obtiene n cantidad de SKUS para captura de conteos
	 * @author Marcos Morales para captura de conteos
	 * @param numReg Cantidad de registros a obtener de la consulta
	 * @param offSet Numero de registros a descartar en la consulta
	 */
	public ResultSet obtieneSKUSBD(int numReg, int offSet) {

		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			String query = "SELECT TRIM(d.IO_SKU_REF) FROM HC.IO_DETAIL d\r\n"
					+ "inner join hc.II_HEADER_IO_HEADER hh on d.IO_Folio = hh.IO_FOLIO\r\n"
					+ "WHERE YEAR(CREATED_DATETIME) = YEAR(GETDATE())\r\n"
					+ "AND DATEPART(ISO_WEEK, CREATED_DATETIME) = DATEPART(WEEK, GETDATE())\r\n"
					+ "and hh.II_FOLIO = (SELECT II_FOLIO FROM [HC].[II_HEADER] WHERE YEAR(II_STARTDATE) = YEAR(GETDATE()) AND II_WEEKNUMBER = DATEPART(WEEK, GETDATE()) AND II_TYPE = 'I')\r\n"
					+ "order by d.IO_Folio, d.IO_SKU_Order\r\n"
					+ "OFFSET " + offSet + " ROWS\r\n"
					+ "FETCH NEXT " + numReg + " ROW ONLY;";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Obtiene TODOS los registros de SKUS para captura de conteos
	 * @author Marcos Morales para captura de conteos
	 */
	public ResultSet obtieneTodosLosSKUsBD() {

		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			String query = "SELECT TRIM(d.IO_SKU_REF) FROM HC.IO_DETAIL d\r\n"
					+ "inner join hc.II_HEADER_IO_HEADER hh on d.IO_Folio = hh.IO_FOLIO\r\n"
					+ "WHERE YEAR(CREATED_DATETIME) = YEAR(GETDATE())\r\n"
					+ "AND DATEPART(ISO_WEEK, CREATED_DATETIME) = DATEPART(WEEK, GETDATE())\r\n"
					+ "and hh.II_FOLIO = (SELECT II_FOLIO FROM [HC].[II_HEADER] WHERE YEAR(II_STARTDATE) = YEAR(GETDATE()) AND II_WEEKNUMBER = DATEPART(WEEK, GETDATE()) AND II_TYPE = 'I')\r\n"
					+ "order by d.IO_Folio, d.IO_SKU_Order";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Click sobre el primer SKU
	 * @author Marcos Morales
	 */
	public void clickFirstSKU() {
		au.click(firstProduct);
	}

	/**
	 * Realiza el conteo de n productos tomando los SKUS desde la BD
	 * @author Lizbeth Hernandez
	 * @param numConteos Cantidad de skus a contar asignados por colaborador
	 * @param valorConteo Valor a capturar para cada conteo (físico)
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void registrarConteoColb(String numConteos, String valorConteo, boolean screenShot) throws InterruptedException {

		waitForVisibility(txtBarcode, Duration.ofSeconds(30));

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DatabaseUtil.obtenerConexion();

			statement = connection.createStatement();

			String query = "DECLARE @Folio INT = (SELECT MAX(II_FOLIO) FROM hc.II_HEADER WHERE II_TYPE = 'I');\n"
					+ "DECLARE @tabla AS TABLE (\n"
					+ "SKU NVARCHAR(MAX)\n"
					+ ");\n"
					+ "INSERT INTO @tabla\n"
					+ "SELECT TOP " + numConteos + " TRIM(od.IO_SKU_REF) AS SKU\n"
					+ "FROM\n"
					+ "hc.II_ASSIGNMENTROLE AS ar\n"
					+ "JOIN\n"
					+ "hc.II_HEADER AS ih ON ih.ii_automatic_role_assignment_date = ar.TM_CRT\n"
					+ "JOIN\n"
					+ "hc.io_detail AS od ON od.io_folio = ar.IO_FOLIO\n"
					+ "WHERE\n"
					+ "ih.II_FOLIO = @Folio;\n"
					+ "SELECT * FROM @tabla;";

			resultSet = statement.executeQuery(query);

			ingresarConteo(resultSet, valorConteo, btnCountProdSave);

		} catch (SQLException e) {
			System.out.println("Error executing SQL query: " + e.getMessage());
		} finally {
			try {
				if (resultSet != null) resultSet.close();
				if (statement != null) statement.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {
				System.out.println("Error closing resources: " + e.getMessage());
			}
		}
	}

	/**
	 * Cumple el prerreqisito de realizar conteo al selectivo a reasignar
	 * @author Lizbeth Hernandez
	 */

	public void prerequisitoConteoSelectivo(String user, String pass, String app, String numConteos, String valorConteo, boolean screenShot) throws IOException, InterruptedException {
		Login login = new Login(testCase, au, baseExecution);
		Menus menus = new Menus(testCase, au, baseExecution);
		Comunes comunes = new Comunes(testCase, au, baseExecution);
		//PlantillaTienda plantillaTda = new PlantillaTienda(testCase, au, baseExecution);
		RolesInteligentes rolesInt = new RolesInteligentes(testCase, au, baseExecution);

		//Iniciar sesion
		login.login(user,pass);

		//Ingresar a Roles inteligentes
		menus.ingresarRolesInteligentes(screenShot);

		//Semana en curso
		comunes.seleccionarSemana(screenShot);

		//Continuar
		comunes.clickBtnContinuar(screenShot);

		//Ingresa a la vista preliminar de las asignaciones Automaticas
		rolesInt.scrollToColaboradorEnElCalendario();

		//Ingresar al selectivo a reasignar
		rolesInt.seleccionarColaborador(screenShot);

		//Aceptar Consolidacion
		comunes.clickBtnAceptar(screenShot);

		//Articulos por contar
		comunes.clickBtnAceptarConteosFaltantes(screenShot);

		//Ingresa a pantalla detalle de preinventario
		esperaCampoIngresarSKU();

		//Realiza el conteo de productos del selectivo a reasignar
		registrarConteoColb(numConteos, valorConteo, screenShot);

		//Guarda ls conteo realizados en el preinventario en curso
		au.click(btnSavePreinv);
		comunes.clickBtnAceptar(screenShot);
		esperaSeOculteMensajeGuardado();

		//Cerrar Aplicacion
		comunes.finalizarApp(app);
	}

	/**
	 * Registra un SKU obtenido de BD hasta mostrar la ventana de captura del conteo
	 * @author Marcos Morales
	 * @param numSKUS Numero de SKUS a contar
	 * @param screenShot Toma captura de pantalla: true/false
	 */
	public void capturarSKU(int numSKUS, boolean screenShot) throws SQLException, AssertionError {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		ResultSet rs = obtieneSKUSBD(numSKUS);

		if (rs.next()) {
			String sku = rs.getString(1);
			au.sendKeys(txtBarcode, sku);
			takeScreenshot(screenShot);
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			waitForVisibility(txtFisicos, Duration.ofSeconds(30));
			takeScreenshot(screenShot);
		} else {
			throw new AssertionError("No se encontraron registros al ejecutar la consulta del metodo: obtieneSKUSBD");
		}
	}

	/**
	 * Registra un SKU obtenido de BD hasta mostrar la ventana de captura del conteo
	 * @author Marcos Morales
	 * @param numSKUS Numero de SKUS a contar
	 * @param offSet Registros a descartar en la consulta
	 * @param screenShot Toma captura de pantalla: true/false
	 */
	public void capturarSKU(int numSKUS, int offSet, boolean screenShot) throws SQLException, AssertionError {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		ResultSet rs = obtieneSKUSBD(numSKUS, offSet);

		if (rs.next()) {
			String sku = rs.getString(1);
			au.sendKeys(txtBarcode, sku);
			takeScreenshot(screenShot);
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			waitForVisibility(txtFisicos, Duration.ofSeconds(30));
			takeScreenshot(screenShot);
		} else {
			throw new AssertionError("No se encontraron registros al ejecutar la consulta del metodo: obtieneSKUSBD");
		}
	}

	/**
	 * Registra un SKU obtenido de BD hasta mostrar la ventana de captura del conteo
	 * @author Marcos Morales
	 * @param numSKUS Numero de SKUS a contar
	 */
	public void capturarSKU(int numSKUS) throws SQLException, AssertionError {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		ResultSet rs = obtieneSKUSBD(numSKUS);

		if (rs.next()) {
			String sku = rs.getString(1);
			au.sendKeys(txtBarcode, sku);
		} else {
			throw new AssertionError("No se encontraron registros al ejecutar la consulta del metodo: obtieneSKUSBD");
		}
	}

	/**
	 * Registra el primer SKU obtenido del segundo selectivo
	 * @author Marcos Morales
	 * @param tipoConsulta Numero de SKUS a contar
	 */
	public void capturarSKU(String tipoConsulta) throws SQLException, AssertionError {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		ResultSet rs = obtienePrimerSKUSegundoSelectivo(tipoConsulta);

		if (rs.next()) {
			String sku = rs.getString(1);
			au.sendKeys(txtBarcode, sku);
		} else {
			throw new AssertionError("No se encontraron registros al ejecutar la consulta del metodo: obtienePrimerSKUSegundoSelectivo");
		}
	}

	/**
	 * Registra un SKU obtenido de BD para un colaborador en especifico
	 * @author Marcos Morales
	 * @param numSKUS Numero de SKUS a contar
	 */
	public void capturarSKUPorColaborador(int numSKUS) throws SQLException, AssertionError {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		ResultSet rs = obtieneSkusPorColaboradorBD(numSKUS);

		if (rs.next()) {
			String sku = rs.getString(1);
			au.sendKeys(txtBarcode, sku);
		} else {
			throw new AssertionError("No se encontraron registros al ejecutar la consulta");
		}
	}

	/**
	 * Metodo para capturar el valor del conteo
	 * @author Marcos Morales
	 * @param conteo Valor a capturar
	 */
	public void capturarConteo(String conteo) {
		waitForVisibility(txtFisicos, Duration.ofSeconds(30));
		au.sendKeys(txtFisicos, conteo);
	}

	/**
	 * Metodo para capturar el valor del conteo
	 * @author Marcos Morales
	 * @param conteo Valor a capturar
	 * @param conteoPrevio Bandera que identifica si el conteo es nuevo o no: true/false
	 * @param debeGuardar Bandera que define si guarda en memoria los datos de sku, teorico y fisico para el regitro actual
	 */
	public void capturarConteo(String tipo, String conteo, boolean conteoPrevio, boolean debeGuardar) {
		waitForVisibility(txtFisicos, Duration.ofSeconds(30));
		au.sendKeys(txtFisicos, conteo);
		switch (tipo.toLowerCase()) {
		case "entero":
			extraerValoresModalConteosEnteros(conteoPrevio, debeGuardar);
			break;
		case "decimal":
			extraerValoresModalConteosDecimales(conteoPrevio, debeGuardar);
			break;
		}
	}

	/**
	 * Metodo de espera para el modal de captura de conteo
	 * @author Marcos Morales
	 */
	public void esperaModalConteoVisible() {
		waitForVisibility(txtFisicos, Duration.ofSeconds(30));
	}

	/**
	 * Metodo para hacer click y mostrar el teclado posterior a ingresar un valor
	 * en el campo para ingresar SKUS
	 * @author Marcos Morales
	 * screenShot Toma captura de pantalla: true/false
	 * @throws InterruptedException 
	 */
	public void clickEnterBarcode(boolean screenShot) throws InterruptedException {
		au.click(txtBarcode);
		Thread.sleep(500);
		takeScreenshot(screenShot);
		((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
	}

	/**
	 * Metodo para hacer un breve scroll hacia arriba en el listado de SKUS
	 * @author Marcos Morales
	 */
	public void shortScrollUp() {
		By objScrollable = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/rv_group_products");
		scrolling(objScrollable, "up", 0.5);
	}

	/**
	 * Metodo para capturar un conteo Mayor, Menor o Igual al valor teorico
	 * @author Marcos Morales
	 * @param action igual, menor, mayor
	 * @param conteoPrevio Bandera que identifica si el conteo es nuevo o no: true/false
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void capturarFisico(String action, boolean conteoPrevio, boolean screenShot) {
		String conteo = "";
		String teoricoStr = au.driver.findElement(teoricoVal).getText();
		int teorico = Integer.parseInt(teoricoStr);

		switch (action.toLowerCase()) {
		case "igual":
			if (teorico < 0) {
				teorico = 1;
			}
			conteo = Integer.toString(teorico);
			au.sendKeys(txtFisicos, conteo);
			takeScreenshot(screenShot);
			break;
		case "menor":
			if (teorico > 0) {
				teorico = teorico-1;
			} else if (teorico <= 0) {
				teorico = 0;
			}
			conteo = Integer.toString(teorico);
			au.sendKeys(txtFisicos, conteo);
			takeScreenshot(screenShot);
			break;
		case "mayor":
			if (teorico >= 0) {
				teorico = teorico+1;
			} else if (teorico < 0) {
				teorico = 1;
			}
			conteo = Integer.toString(teorico);
			au.sendKeys(txtFisicos, conteo);
			takeScreenshot(screenShot);
			break;
		}
	}

	/**
	 * Metodo para capturar un conteo Mayor, Menor o Igual al valor teorico
	 * @author Marcos Morales
	 * @param action igual, menor, mayor
	 * @param tipo entero, decimal
	 * @param conteoPrevio Bandera que identifica si el conteo es nuevo o no: true/false
	 * @param debeGuardar Bandera que define si guarda en memoria los datos de sku, teorico y fisico para el regitro actual
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void capturarFisico(String action, String tipo, boolean conteoPrevio, boolean debeGuardar, boolean screenShot) {
		String conteo = "";
		String teoricoStr = au.driver.findElement(teoricoVal).getText();
		int teorico = Integer.parseInt(teoricoStr);

		switch (action.toLowerCase()) {
		case "igual":
			if (teorico < 0) {
				teorico = 1;
			}
			conteo = Integer.toString(teorico);
			au.sendKeys(txtFisicos, conteo);
			takeScreenshot(screenShot);
			break;
		case "menor":
			if (teorico > 0) {
				teorico = teorico-1;
			} else if (teorico <= 0) {
				teorico = 0;
			}
			conteo = Integer.toString(teorico);
			au.sendKeys(txtFisicos, conteo);
			takeScreenshot(screenShot);
			break;
		case "mayor":
			if (teorico >= 0) {
				teorico = teorico+1;
			} else if (teorico < 0) {
				teorico = 1;
			}
			conteo = Integer.toString(teorico);
			au.sendKeys(txtFisicos, conteo);
			takeScreenshot(screenShot);
			break;
		}

		switch (tipo.toLowerCase()) {
		case "entero":
			extraerValoresModalConteosEnteros(conteoPrevio, debeGuardar);
			break;
		case "decimal":
			extraerValoresModalConteosDecimales(conteoPrevio, debeGuardar);
			break;
		}
	}

	/**
	 * Metodo para capturar un conteo menor al teorico
	 * @author Marcos Morales
	 * @param resultSet Resultado de la consulta a bd con los SKUS a capturar
	 * @param locator Identicador del boton para Guardar o Cancelar
	 * @param conteoPrevio Bandera que identifica si el conteo es nuevo o no: true/false
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void ingresarConteoMenor(ResultSet resultSet, By locator, boolean conteoPrevio, boolean screenShot) throws SQLException {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		while (resultSet.next()) {
			String sku = resultSet.getString(1);
			au.sendKeys(txtBarcode, sku);
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			capturarFisico("menor", conteoPrevio, screenShot);
			takeScreenshot(screenShot);
			au.click(locator);
		}
	}

	/**
	 * Metodo para capturar un conteo menor al teorico
	 * @author Marcos Morales
	 * @param resultSet Resultado de la consulta a bd con los SKUS a capturar
	 * @param locator Identicador del boton para Guardar o Cancelar
	 * @param tipo "igual", "menor", "mayor"
	 * @param conteoPrevio Bandera que identifica si el conteo es nuevo o no: true/false
	 * @param debeGuardar Bandera que define si guarda en memoria los datos de sku, teorico y fisico para el regitro actual
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void ingresarConteoMenor(ResultSet resultSet, By locator, String tipo, boolean conteoPrevio, boolean debeGuardar, boolean screenShot) throws SQLException {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		while (resultSet.next()) {
			String sku = resultSet.getString(1);
			au.sendKeys(txtBarcode, sku);
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			capturarFisico("menor", tipo, conteoPrevio, debeGuardar, screenShot);
			takeScreenshot(screenShot);
			au.click(locator);
			guardarConteosFront();
		}
	}

	/**
	 * Metodo para capturar un conteo mayor al teorico
	 * @author Marcos Morales
	 * @param resultSet Resultado de la consulta a bd con los SKUS a capturar
	 * @param locator Identicador del boton para Guardar o Cancelar
	 * @param conteoPrevio Bandera que identifica si el conteo es nuevo o no: true/false
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void ingresarConteoMayor(ResultSet resultSet, By locator, boolean conteoPrevio, boolean screenShot) throws SQLException {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		while (resultSet.next()) {
			String sku = resultSet.getString(1);
			au.sendKeys(txtBarcode, sku);
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			capturarFisico("mayor", conteoPrevio, screenShot);
			takeScreenshot(screenShot);
			au.click(locator);
		}
	}

	/**
	 * Metodo para capturar un conteo mayor al teorico
	 * @author Marcos Morales
	 * @param resultSet Resultado de la consulta a bd con los SKUS a capturar
	 * @param locator Identicador del boton para Guardar o Cancelar
	 * @param tipo "igual", "menor", "mayor"
	 * @param conteoPrevio Bandera que identifica si el conteo es nuevo o no: true/false
	 * @param debeGuardar Bandera que define si guarda en memoria los datos de sku, teorico y fisico para el regitro actual
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void ingresarConteoMayor(ResultSet resultSet, By locator, String tipo, boolean conteoPrevio, boolean debeGuardar, boolean screenShot) throws SQLException {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		while (resultSet.next()) {
			String sku = resultSet.getString(1);
			au.sendKeys(txtBarcode, sku);
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			capturarFisico("mayor", tipo, conteoPrevio, debeGuardar, screenShot);
			takeScreenshot(screenShot);
			au.click(locator);
			guardarConteosFront();
		}
	}

	/**
	 * Ingresa un valor menor al teorico en la captura del conteo
	 * @author Marcos Morales
	 * @param numReg Cantidad de registros a obtener
	 * @param offSet Cantidad de registros a descartar en la consulta
	 * @param conteoPrevio Bandera que identifica si el conteo es nuevo o no: true/false
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 * @throws SQLException 
	 */
	public void registrarConteoMenor(int numReg, int offSet, boolean conteoPrevio, boolean screenShot) throws SQLException {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		ResultSet rs = obtieneSKUSBD(numReg, offSet);
		ingresarConteoMenor(rs, btnCountProdSave, conteoPrevio, screenShot);
	}

	/**
	 * Ingresa un valor menor al teorico en la captura del conteo
	 * @author Marcos Morales
	 * @param numReg Cantidad de registros a obtener
	 * @param offSet Cantidad de registros a descartar en la consulta
	 * @param tipo entero, decimal
	 * @param conteoPrevio Bandera que identifica si el conteo es nuevo o no: true/false
	 * @param debeGuardar Bandera que define si guarda en memoria los datos de sku, teorico y fisico para el regitro actual
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 * @throws SQLException 
	 */
	public void registrarConteoMenor(int numReg, int offSet, String tipo, boolean conteoPrevio, boolean debeGuardar, boolean screenShot) throws SQLException {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		ResultSet rs = obtieneSKUSBD(numReg, offSet);
		ingresarConteoMenor(rs, btnCountProdSave, tipo, conteoPrevio, debeGuardar, screenShot);
	}

	/**
	 * Ingresa un valor mayor al teorico en la captura del conteo
	 * @author Marcos Morales
	 * @param numReg Cantidad de registros a obtener
	 * @param offSet Cantidad de registros a descartar en la consulta
	 * @param conteoPrevio Bandera que identifica si el conteo es nuevo o no: true/false
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 * @throws SQLException 
	 */
	public void registrarConteoMayor(int numReg, int offSet, boolean conteoPrevio, boolean screenShot) throws SQLException {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		ResultSet rs = obtieneSKUSBD(numReg, offSet);
		ingresarConteoMayor(rs, btnCountProdSave, conteoPrevio, screenShot);
	}

	/**
	 * Ingresa un valor mayor al teorico en la captura del conteo
	 * @author Marcos Morales
	 * @param numReg Cantidad de registros a obtener
	 * @param offSet Cantidad de registros a descartar en la consulta
	 * @param tipo entero, decimal
	 * @param conteoPrevio Bandera que identifica si el conteo es nuevo o no: true/false
	 * @param debeGuardar Bandera que define si guarda en memoria los datos de sku, teorico y fisico para el regitro actual
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 * @throws SQLException 
	 */
	public void registrarConteoMayor(int numReg, int offSet, String tipo, boolean conteoPrevio, boolean debeGuardar, boolean screenShot) throws SQLException {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		ResultSet rs = obtieneSKUSBD(numReg, offSet);
		ingresarConteoMayor(rs, btnCountProdSave, tipo, conteoPrevio, debeGuardar, screenShot);
	}

	/**
	 * Obtiene del modal para captura del conteo los valores de SKU, Teorico y Fisico
	 * @author Marcos Morales
	 * @param conteoPrevio Bandera que identifica si el conteo es nuevo o no: true/false
	 * @param debeGuardar Bandera que define si guarda en memoria los datos de sku, teorico y fisico para el regitro actual
	 */
	public void extraerValoresModalConteosEnteros(boolean conteoPrevio, boolean debeGuardar) {
		int ctoActual = 0;
		if (conteoPrevio) {
			String ctoActualStr = au.driver.findElement(QtyCurrent).getText();
			ctoActual = (int) Double.parseDouble(ctoActualStr.replaceAll("[^\\d.]", ""));
		}
		String sku = au.driver.findElement(skuProd).getText();
		String skuStr = sku.replace("SKU: ", "").trim();
		String teoricoStr = au.driver.findElement(teoricoVal).getText();
		String fisicoStr = au.driver.findElement(txtFisicos).getText();
		int fisicoInt = Integer.parseInt(fisicoStr);
		int fisico = ctoActual + fisicoInt;
		String conteoStr = Integer.toString(fisico);
		//System.out.println("MODAL - Guardando en ArrayList... SKU: " + skuStr + ", Teorico: " + teoricoStr + ", Fisico: " + conteoStr);
		modalConteos = new ModalConteos(skuStr, teoricoStr, conteoStr, debeGuardar);
	}

	/**
	 * Obtiene del modal para captura del conteo los valores de SKU, Teorico y Fisico
	 * @author Marcos Morales
	 * @param conteoPrevio Bandera que identifica si el conteo es nuevo o no: true/false
	 * @param debeGuardar Bandera que define si guarda en memoria los datos de sku, teorico y fisico para el regitro actual
	 */
	public void extraerValoresModalConteosDecimales(boolean conteoPrevio, boolean debeGuardar) {
		DecimalFormat df = new DecimalFormat("#.##");
		int ctoActual = 0;
		if (conteoPrevio) {
			String ctoActualStr = au.driver.findElement(QtyCurrent).getText();
			ctoActual = (int) Double.parseDouble(ctoActualStr.replaceAll("[^\\d.]", ""));
		}
		String skuStr = au.driver.findElement(skuProd).getText().replace("SKU: ", "").trim();
		String teoricoStr = au.driver.findElement(teoricoVal).getText();
		String fisicoStr = au.driver.findElement(txtFisicos).getText();
		float fisicoFloat = Float.parseFloat(df.format(Float.parseFloat(fisicoStr)));
		float fisico = ctoActual + fisicoFloat;
		String conteoStr = Float.toString(fisico);
		//System.out.println("Modal - SKU: " + skuStr + ", Teorico: " + teoricoStr + ", Fisico: " + conteoStr);
		modalConteos = new ModalConteos(skuStr, teoricoStr, conteoStr, debeGuardar);
	}

	/**
	 * Devuelve el valor Teorico almacenado en memoria desde que se realiza
	 * la captura del conteo
	 * @author Marcos Morales
	 */
	public String obtieneTeoricoEnMemoria() {
		return modalConteos.getTeorico();
	}

	/**
	 * Devuelve el valor Fisico almacenado en memoria desde que se realiza
	 * la captura del conteo
	 * @author Marcos Morales
	 */
	public String obtieneFisicoEnMemoria() {
		return modalConteos.getFisico();
	}

	/**
	 * Devuelve el teorico y fisico mostrado en el detalle del preiventario
	 * para un sku especifico
	 * @author Marcos Morales
	 */
	public List<WebElement> getTeoricoFisico(int idxProd) {
		List<WebElement> productos = au.driver.findElements(products);
		WebElement productoN = productos.get(idxProd);
		List<WebElement> teoricoFisico = productoN.findElements(By.className("android.widget.TextView"));
		return teoricoFisico;
	}

	/**
	 * Valida el estado de los botones Cancelar, Guardar y Finalizar
	 * @author Marcos Morales
	 */
	public boolean[] retornaEstadoBtnsCancelarGuardarFinalizar() {
		boolean[] status = new boolean[3];
		try {
			status[0] = au.driver.findElement(btnCancelPreinv).isDisplayed();
			status[1] = au.driver.findElement(btnSavePreinv).isDisplayed();
			status[2] = au.driver.findElement(btnFinishPreinv).isDisplayed();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * Obtiene desde BD el registro de un conteo capturado
	 * @author Marcos Morales
	 * @param conteo Valor capturado
	 */
	public ResultSet obtieneRegistrosConteosBD(int conteo) {
		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			String query = "select	 det.II_COUNT_DATE\r\n"
					+ "		,det.IO_SKU_REF\r\n"
					+ "		,det.ONHAND\r\n"
					+ "		,det.ONHANDREAL\r\n"
					+ "		,det.DIF\r\n"
					+ "from hc.II_HEADER_IO_HEADER as hh \r\n"
					+ "join hc.II_DETAIL as det on det.IO_Folio = hh.IO_FOLIO\r\n"
					+ "where hh.II_FOLIO = (SELECT II_FOLIO FROM [HC].[II_HEADER] WHERE YEAR(II_STARTDATE) = YEAR(GETDATE()) AND II_WEEKNUMBER = DATEPART(WEEK, GETDATE()) AND II_TYPE = 'I')\r\n"
					+ "and det.ONHANDREAL = " + conteo + "\r\n"
					+ "order by det.IO_SKU_REF desc";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Obtiene desde BD multiples registros de conteos
	 * @author Marcos Morales
	 * @param conteos Valores capturados
	 */
	public ResultSet obtieneRegistrosConteosBD(String conteos) {
		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			String query = "select	 det.II_COUNT_DATE\r\n"
					+ "		,det.IO_SKU_REF\r\n"
					+ "		,det.ONHAND\r\n"
					+ "		,det.ONHANDREAL\r\n"
					+ "		,det.DIF\r\n"
					+ "from hc.II_HEADER_IO_HEADER as hh \r\n"
					+ "join hc.II_DETAIL as det on det.IO_Folio = hh.IO_FOLIO\r\n"
					+ "where hh.II_FOLIO = (SELECT II_FOLIO FROM [HC].[II_HEADER] WHERE YEAR(II_STARTDATE) = YEAR(GETDATE()) AND II_WEEKNUMBER = DATEPART(WEEK, GETDATE()) AND II_TYPE = 'I')\r\n"
					+ "and det.ONHANDREAL in (" + conteos + ")\r\n"
					+ "order by det.IO_SKU_REF desc";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Genera un String con el resultado de la consulta a BD para un conteo capturado
	 * @author Marcos Morales
	 * @param conteo Valor capturado
	 */
	public String generaStringRegistrosConteosBD(int conteo) throws SQLException, AssertionError {
		String resultado = "II_COUNT_DATE|IO_SKU_REF|ONHAND|ONHANDREAL|DIF\n";
		ResultSet rs = obtieneRegistrosConteosBD(conteo);
		if (rs.isBeforeFirst()) {
			while (rs.next()) {
				String col1 = rs.getString(1);
				String col2 = rs.getString(2);
				String col3 = rs.getString(3);
				String col4 = rs.getString(4);
				String col5 = rs.getString(5);
				resultado += col1 + "|" + col2 + "|" + col3 + "|" + col4 + "|" + col5 + "\n";
			}
			//System.out.println(resultado);
			return resultado;
		} else {
			throw new AssertionError("La consulta ejecutada del metodo obtieneRegistrosConteosBD no devolvio resultados");
		}
	}

	/**
	 * Genera un String con el resultado de la consulta a BD para multiples conteos capturados
	 * @author Marcos Morales
	 * @param conteos Valores capturados
	 */
	public String generaStringRegistrosConteosBD(String conteos) throws SQLException, AssertionError {
		String resultado = "II_COUNT_DATE|IO_SKU_REF|ONHAND|ONHANDREAL|DIF\n";
		ResultSet rs = obtieneRegistrosConteosBD(conteos);
		if (rs.isBeforeFirst()) {
			while (rs.next()) {
				String col1 = rs.getString(1);
				String col2 = rs.getString(2);
				String col3 = rs.getString(3);
				String col4 = rs.getString(4);
				String col5 = rs.getString(5);
				resultado += col1 + "|" + col2 + "|" + col3 + "|" + col4 + "|" + col5 + "\n";
			}
			//System.out.println(resultado);
			return resultado;
		} else {
			throw new AssertionError("La consulta ejecutada del metodo obtieneRegistrosConteosBD no devolvio resultados");
		}
	}

	/**
	 * Realiza el guardado en una lista de los skus y su valor respectivo
	 * de Teorico y Fisico para los registros contados que se muestran en el detalle
	 * de un preinventario
	 * @author Marcos Morales
	 */
	public void guardarConteosFront() {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		String skuModal = modalConteos.getSKU();
		By teorico = By.xpath("//android.view.ViewGroup[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ct_product\"][.//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_sku\" and @text=\"SKU: " + skuModal + "\"]]/*[2]");
		By fisico = By.xpath("//android.view.ViewGroup[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ct_product\"][.//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_sku\" and @text=\"SKU: " + skuModal + "\"]]/*[3]");
		String teoricoFront = au.driver.findElement(teorico).getText();
		String fisicoFront = au.driver.findElement(fisico).getText();
		//System.out.println("FRONT - Guardando en ArrayList... SKU: " + skuModal + ", Teorico: " + teoricoFront + ", Fisico: " + fisicoFront);
		frontConteos = new ConteosFront(skuModal, teoricoFront, fisicoFront, true);
	}

	/**
	 * Metodo de espera para el boton finalizar al ingresar al detalle de un preinventario
	 * @author Marcos Morales
	 */
	public void esperaBotonFinalizar() {
		waitForVisibility(btnFinishPreinv, Duration.ofSeconds(30));
	}

	/**
	 * Obtiene el numero de skus a contar desplegado en la parte inferior
	 * izquierda de la pantalla
	 * @author Marcos Morales
	 * @return totalSkusApp Total de SKUS a contar
	 */
	public int obtieneTotalSKUSenPantalla() {
		String totalSkusAppStr = au.driver.findElement(lblConteosAct).getText();
		int totalSkusApp = Integer.parseInt(totalSkusAppStr.split(" de ")[1]);
		return totalSkusApp;
	}

	/**
	 * Obtiene el numero de skus contabilizados desplegado en la parte inferior
	 * izquierda de la pantalla
	 * @author Marcos Morales
	 * @return totalSkusApp Total de SKUS a contar
	 */
	public int obtieneConteosRealizados() {
		String skusContados = au.driver.findElement(lblConteosAct).getText();
		int totalSkusCont = Integer.parseInt(skusContados.split(" de ")[0]);
		return totalSkusCont;
	}

	/**
	 * Metodo de espera para el campo Ingresar codigo de barras
	 * @author Marcos Morales
	 */
	public void esperaCampoIngresarSKU() {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
	}

	/**
	 * Metodo de espera para el campo Ingresar el conteo
	 * @author Marcos Morales
	 */
	public void esperaCampoCapturarConteo() {
		waitForVisibility(txtFisicos, Duration.ofSeconds(30));
	}
}