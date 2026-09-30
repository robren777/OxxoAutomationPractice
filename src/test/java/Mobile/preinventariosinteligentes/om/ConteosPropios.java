package Mobile.preinventariosinteligentes.om;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class ConteosPropios {
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;
	private ModalConteos modalConteos;
	private DetallePlantilla detailTemplate;

	/**
	 * Constructor de la clase ConteosPropios
	 * @author Marcos Morales
	 * @param au (AppiumUtil): objeto de la clase 
	 * @param testCase (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 */
	public ConteosPropios(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
		this.testCase = testCase;
		this.au = au;
		this.baseExecution = baseExecution;
	}

	/*
	 * Localizadores
	 * */
	private By lblTitulo = By.xpath("//android.widget.TextView[@text=\"CONTEOS PROPIOS\"]");
	private By opcion1 = By.xpath("//android.widget.TextView[@text=\"Crear Nueva Plantilla\"]");
	private By opcion2 = By.xpath("//android.widget.TextView[@text=\"Iniciar conteo\"]");

	private By txtBusqueda = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/search_src_text");
	private By btnBorrarBusqueda = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/search_close_btn");
	private By filtroPor = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/text_input_end_icon");
	private By btnBorrarFiltro = By.xpath("//android.widget.LinearLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/lt_filter\"]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.ImageView");
	private By lblAsignadoA =By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tv_assigned_to");
	private By lblPlantillas = By.xpath("//android.widget.TextView[@text=\"Plantillas creadas\"]");
	private By objScrollable = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/rv_all_sku_templates_assignment");
	private By btnPlusPlantilla = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/create_template");
	private By cbColaboradores = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/spinner_collaborator_sku_template");
	private By colaborador = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tv_gender_name");
	private By btnContinuar = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_continue");
	private By txtNombrePlantilla = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/et_template_name");
	private By txtBarcode = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/et_barcode");
	private By btnGuardar = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/save_template");
	private By btnCancelar = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/cancel_template");
	private By txtNomPlantilla = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tv_group_name_preinventory");
	private By lstSKUS = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tv_product_sku");
	private By btnGuardarPreInv = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_save_preinventory_own");
	private By tittleAsigEncargado = By.xpath("//android.widget.TextView[@text=\"Asignar un encargado\"]");
	private By subtitleAsigEncargado = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/step_one_title_create_template");
	private By lblMorning = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/turn_morning");
	private By lblAfternoon = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/turn_afternoon");
	private By lblNight = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/turn_night");
	private By lstSkus = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/rv_products");
	private By firstTemplate = By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_template_name\"])[1]");
	private By turnName = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tv_turn_name");
	private By templateName = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tv_template_name");
	private By btnDeleteSKU = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/ic_delete_product");
	private By groupRadios = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/radioGroupShifts");
	private By radioBtns = By.className("android.widget.RadioButton");
	private By plantillas = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/ct_item_template");
	private By allTemplates = By.xpath("(//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/rv_all_sku_templates\"]/android.view.ViewGroup)");
	private By btnPlusTemplate = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tv_state_visibility");
	private By primerProducto = By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_product\"])[1]");
	private By products = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/ct_product");
	private By lblConteosAct = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tv_counter_current");
	private By cronValue = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/cm_time");
	private By btnImprimir = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_print");

	private By modalColabTitle = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/title_assingment_template");
	private By modalSubtitle = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/labelRol");
	private By modalCbxColabs = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/sp_rols");
	private By modalBtnAceptar = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btnAcept");

	private By lblMensaje = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/snackbar_text");
	private By lblMsj = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tv_saving");

	private By descriptionProd = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tv_pre_title");
	private By skuProd = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tv_pre_sku");
	private By teoricoVal = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/etTheoricalQty");
	private By lblTeorico = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/textView");
	private By QtyCurrent = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvQtyCurrent");
	private By txtFisicos = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/edt_find_sku_product");
	private By lblFisicos = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/textView3");
	private By sobfalLabel = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvDescSum");
	private By btnCountProdSave = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_count_product_save");
	private By btnCountProdCancel = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_count_product_cancel");

	private By radioGuardarReiniciar = By.xpath("//android.widget.RadioButton[@text=\"Guardar conteo y reiniciar plantilla\"]");
	private By radioGuardarImprimir = By.xpath("//android.widget.RadioButton[@text=\"Guardar conteo e imprimir\"]");
	private By btnReinicioAceptar = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btnAccept");
	private By btnReinicioCancelar = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btnCancel");


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
	 * Devuelve el identificador del elemento para el nombre del turno
	 * @author Marcos Morales
	 */
	public By getNombreTurno() {
		return turnName;
	}

	/**
	 * Devuelve el identificador del elemento para el nombre de la plantilla
	 * @author Marcos Morales
	 */
	public By getNombrePlantilla() {
		return templateName;
	}

	/**
	 * Hacer click en el Botón + Para crear una plantilla
	 * @author Marcos Morales
	 */
	public void clickBtnPlus(boolean screenShot) {
		waitForVisibility(btnPlusPlantilla, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(btnPlusPlantilla);
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
	 * Hacer click en el combo para desplegar la lista de colaboradores
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void clickComboColaboradores(boolean screenShot) {
		waitForVisibility(cbColaboradores, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(cbColaboradores);
		waitForVisibility(colaborador, Duration.ofSeconds(30));
	}

	/**
	 * Hacer click en el combo para desplegar la lista de colaboradores
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void clickCboColaboradores(boolean screenShot) {
		waitForVisibility(modalCbxColabs, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(modalCbxColabs);
		waitForVisibility(colaborador, Duration.ofSeconds(30));
	}

	/**
	 * Hacer click en la opción Filtrar Por
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void clickOpcionFiltrarPor() {
		waitForVisibility(filtroPor, Duration.ofSeconds(30));
		au.click(filtroPor);
	}

	/**
	 * Selecciona uno a uno de la lista de colaboradores
	 * @author Marcos Morales
	 * @param index Indice del colaborador a seleccionar
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void seleccionarColaborador(int index, boolean screenShot) {

		List<WebElement> colaboradores = au.driver.findElements(colaborador);
		colaboradores.remove(0);

		int sizeColabs = colaboradores.size();
		int indexColab = index % sizeColabs;

		WebElement colaborador = colaboradores.get(indexColab);
		waitForVisibility(colaborador, Duration.ofSeconds(30));

		takeScreenshot(screenShot);
		colaborador.click();
	}

	/**
	 * Seleccionar un turno
	 * @author Marcos Morales
	 * @param idTurno Selecciona uno a uno los tres turnos disponibles: Mañana, Tarde, Noche
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void seleccionarTurno(String idTurno, boolean screenShot) {
		WebElement turno = au.driver.findElement(By.id("com.femco.oxxo.operaciones.mitienda.debug:id/turn_" + idTurno));
		waitForVisibility(turno, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		turno.click();
	}

	/**
	 * Hacer click en el Botón Continuar
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void clickBtnContinuar(boolean screenShot) {
		waitForVisibility(btnContinuar, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(btnContinuar);
	}

	/**
	 * Hacer click en el Botón Guardar Plantilla
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void clickBtnGuardar(boolean screenShot) {
		waitForVisibility(btnGuardar, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(btnGuardar);
	}

	/**
	 * Valida el estado del boton Guardar
	 * @author Marcos Morales
	 */
	public boolean[] retornaEstadoBotonGuardar() {
		boolean[] status = new boolean[1];
		try {
			WebElement edoBtnGuardar = au.driver.findElement(btnGuardar);
			status[0] = Boolean.parseBoolean(edoBtnGuardar.getAttribute("enabled"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * Hacer click en el Botón Cancelar
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void clickBtnCancelar(boolean screenShot) {
		waitForVisibility(btnCancelar, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(btnCancelar);
	}

	/**
	 * Hacer click en el Botón Guardar Conteos de la Plantilla
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void clickBtnGuardarPreInv(boolean screenShot) {
		waitForVisibility(btnGuardarPreInv, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(btnGuardarPreInv);
	}

	/**
	 * Hacer click en el Botón Guardar en el modal de Encargados
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void clickBtnGuardarModalColabs(boolean screenShot) {
		waitForVisibility(modalBtnAceptar, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(modalBtnAceptar);
	}

	/**
	 * Extrae el texto del elemento de un mensaje
	 * @author Marcos Morales
	 */
	public String obtenerMensaje() {
		waitForVisibility(lblMensaje, Duration.ofSeconds(30));
		return au.driver.findElement(lblMensaje).getText();
	}

	/**
	 * Extrae el texto del elemento de un mensaje de guardado
	 * @author Marcos Morales
	 */
	public String obtenerMensajeGuardado() {
		waitForVisibility(lblMsj, Duration.ofSeconds(30));
		return au.driver.findElement(lblMsj).getText();
	}

	/**
	 * Hacer click en el Botón Imprimir
	 * @author Marcos Morales
	 * @param screenShot Toma captura de pantalla: true/false 
	 */
	public void clickBtnImprimir(boolean screenShot) {
		waitForVisibility(btnImprimir, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(btnImprimir);
	}

	/**
	 * Hacer click en el Botón Guardar Conteo y Reiniciar Plantilla
	 * @author Marcos Morales
	 * @param screenShot Toma captura de pantalla: true/false 
	 */
	public void clickOpcionGuardarReiniciar(boolean screenShot) {
		waitForVisibility(radioGuardarReiniciar, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(radioGuardarReiniciar);
	}

	/**
	 * Hacer click en el Botón Guardar conteo e Imprimir
	 * @author Marcos Morales
	 * @param screenShot Toma captura de pantalla: true/false 
	 */
	public void clickOpcionGuardarImprimir(boolean screenShot) {
		waitForVisibility(radioGuardarImprimir, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(radioGuardarImprimir);
	}

	/**
	 * Hacer click en el Botón Aceptar
	 * @author Marcos Morales
	 * @param screenShot Toma captura de pantalla: true/false 
	 */
	public void clickBtnReinicioAceptar(boolean screenShot) {
		waitForVisibility(btnReinicioAceptar, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(btnReinicioAceptar);
	}

	/**
	 * Hacer click en el Botón Cancelar
	 * @author Marcos Morales
	 * @param screenShot Toma captura de pantalla: true/false 
	 */
	public void clickBtnReinicioCancelar(boolean screenShot) {
		waitForVisibility(btnReinicioCancelar, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(btnReinicioCancelar);
	}

	/**
	 * Obtiene skus utilizados para llenar una plantilla de conteos propios
	 * @author Marcos Morales
	 * @param numSkus Numero de registros a obtener
	 * @param offSet Cantidad de registros a descartar en la consulta para no repetir skus en las plantillas
	 */
	public ResultSet obtieneSkusParaCaptura(int numSkus, int offSet) {

		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			String query = "SELECT	 distinct prod.Item_Id\r\n"
					+ "		,prod.[Description]\r\n"
					+ "		,trim(prod.Sku) [SKU]\r\n"
					+ "		,kdx.QU_CUR [KARDEX]\r\n"
					+ "from XposStore.dbo.ProductCacheDataVw prod\r\n"
					+ "inner join XposStore.dbo.LE_INV_ITM kdx on prod.Item_Id = kdx.ID_ITM\r\n"
					+ "WHERE prod.Item_Status_Name = 'ACTIVE_ITEM_STATUS'\r\n"
					+ "AND prod.Item_Type_Value = 'PRODUCT_ITEM'\r\n"
					+ "AND prod.Product_Type_Code_Name = 'MREGUL_PRODUCT'\r\n"
					+ "AND kdx.QU_CUR > 0\r\n"
					+ "ORDER BY prod.Item_Id\r\n"
					+ "OFFSET " + offSet + " ROWS\r\n"
					+ "FETCH NEXT " + numSkus + " ROW ONLY;";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Obtiene skus utilizados para llenar una plantilla de conteos propios
	 * @author Marcos Morales
	 * @param tipoConsulta skusactivos, skusinactivos, skusactina, plusactivos, plusinactivos, plusactina
	 * @param numSkus Numero de registros a obtener
	 * @param offSet Cantidad de registros a descartar en la consulta para no repetir skus en las plantillas
	 */
	public ResultSet obtieneSkusParaCaptura(String tipoConsulta, int numSkus, int offSet) {
		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();
			switch (tipoConsulta.toLowerCase()) {
			case "skusactivos":
				String querySKUSAct = "SELECT distinct Item_Id\r\n"
						+ ",[Description]\r\n"
						+ ",trim(Sku) [SKU]\r\n"
						+ "from XposStore.dbo.ProductCacheDataVw\r\n"
						+ "WHERE Item_Status_Name='ACTIVE_ITEM_STATUS'\r\n"
						+ "AND Item_Type_Value = 'PRODUCT_ITEM'\r\n"
						+ "AND Product_Type_Code_Name = 'MREGUL_PRODUCT'\r\n"
						+ "ORDER BY Item_Id\r\n"
						+ "OFFSET " + offSet + " ROWS\r\n"
						+ "FETCH NEXT " + numSkus + " ROW ONLY;";

				resultSet = statement.executeQuery(querySKUSAct);
				break;
			case "skusinactivos":
				String querySKUSIna = "SELECT distinct Item_Id\r\n"
						+ ",[Description]\r\n"
						+ ",trim(Sku) [SKU]\r\n"
						+ "from XposStore.dbo.ProductCacheDataVw\r\n"
						+ "WHERE Item_Status_Name='INACTIVE_ITEM_STATUS'\r\n"
						+ "AND Item_Type_Value = 'PRODUCT_ITEM'\r\n"
						+ "AND Product_Type_Code_Name = 'MREGUL_PRODUCT'\r\n"
						+ "ORDER BY Item_Id\r\n"
						+ "OFFSET " + offSet + " ROWS\r\n"
						+ "FETCH NEXT " + numSkus + " ROW ONLY;";

				resultSet = statement.executeQuery(querySKUSIna);
				break;
			case "skusactina":
				String querySKUSActIna = "SELECT distinct Item_Id\r\n"
						+ ",[Description]\r\n"
						+ ",trim(Sku) [SKU]\r\n"
						+ "from XposStore.dbo.ProductCacheDataVw\r\n"
						+ "WHERE (Item_Status_Name='ACTIVE_ITEM_STATUS' OR Item_Status_Name='INACTIVE_ITEM_STATUS')\r\n"
						+ "AND Item_Type_Value = 'PRODUCT_ITEM'\r\n"
						+ "AND Product_Type_Code_Name = 'MREGUL_PRODUCT'\r\n"
						+ "ORDER BY Item_Id\r\n"
						+ "OFFSET " + offSet + " ROWS\r\n"
						+ "FETCH NEXT " + numSkus + " ROW ONLY;";

				resultSet = statement.executeQuery(querySKUSActIna);
				break;
			case "plusactivos":
				String queryPLUSAct = "SELECT distinct Item_Id\r\n"
						+ ",[Description]\r\n"
						+ ",trim(Sku) [SKU]\r\n"
						+ "from XposStore.dbo.ProductCacheDataVw\r\n"
						+ "WHERE Item_Status_Name='ACTIVE_ITEM_STATUS'\r\n"
						+ "AND Item_Type_Value = 'PRODUCT_ITEM'\r\n"
						+ "AND Product_Type_Code_Name = 'MREGUL_PRODUCT'\r\n"
						+ "AND LEN(Sku) < 6\r\n"
						+ "ORDER BY Item_Id\r\n"
						+ "OFFSET " + offSet + " ROWS\r\n"
						+ "FETCH NEXT " + numSkus + " ROW ONLY;";

				resultSet = statement.executeQuery(queryPLUSAct);
				break;
			case "plusinactivos":
				String queryPLUSIna = "SELECT distinct Item_Id\r\n"
						+ ",[Description]\r\n"
						+ ",trim(Sku) [SKU]\r\n"
						+ "from XposStore.dbo.ProductCacheDataVw\r\n"
						+ "WHERE Item_Status_Name='INACTIVE_ITEM_STATUS'\r\n"
						+ "AND Item_Type_Value = 'PRODUCT_ITEM'\r\n"
						+ "AND Product_Type_Code_Name = 'MREGUL_PRODUCT'\r\n"
						+ "AND LEN(Sku) < 6\r\n"
						+ "ORDER BY Item_Id\r\n"
						+ "OFFSET " + offSet + " ROWS\r\n"
						+ "FETCH NEXT " + numSkus + " ROW ONLY;";

				resultSet = statement.executeQuery(queryPLUSIna);
				break;
			case "plusactina":
				String queryPLUSActIna = "SELECT distinct Item_Id\r\n"
						+ ",[Description]\r\n"
						+ ",trim(Sku) [SKU]\r\n"
						+ "from XposStore.dbo.ProductCacheDataVw\r\n"
						+ "WHERE (Item_Status_Name='ACTIVE_ITEM_STATUS' OR Item_Status_Name='INACTIVE_ITEM_STATUS')\r\n"
						+ "AND Item_Type_Value = 'PRODUCT_ITEM'\r\n"
						+ "AND Product_Type_Code_Name = 'MREGUL_PRODUCT'\r\n"
						+ "AND LEN(Sku) < 6\r\n"
						+ "ORDER BY Item_Id\r\n"
						+ "OFFSET " + offSet + " ROWS\r\n"
						+ "FETCH NEXT " + numSkus + " ROW ONLY;";

				resultSet = statement.executeQuery(queryPLUSActIna);
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Realiza la captura de skus tomando como entrada un resulSet
	 * @author Marcos Morales
	 * @param resultSet Resultado de la consulta a bd con los SKUS a capturar
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 * @param showStep true/false Define si muestra o no la descripcion del paso
	 * @throws SQLException 
	 * @throws InterruptedException 
	 */
	public void capturarSKUS(ResultSet resultSet, boolean screenShot, boolean showStep) throws SQLException, InterruptedException {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		while (resultSet.next()) {
			String sku = resultSet.getString(3);
			au.sendKeys(txtBarcode, sku);
			if (showStep) {
				baseExecution.addStep("Ingresar manualmente SKUS");
			}
			takeScreenshot(screenShot);
			au.click(txtBarcode);
			Thread.sleep(500);
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
		}
	}

	/**
	 * Realiza la captura de skus tomando como entrada un resulSet
	 * @author Marcos Morales
	 * @param resultSet Resultado de la consulta a bd con los SKUS a capturar
	 * @throws SQLException 
	 * @throws InterruptedException 
	 */
	public void capturarSKUS(ResultSet resultSet) throws SQLException, InterruptedException {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		while (resultSet.next()) {
			String sku = resultSet.getString(2);
			au.sendKeys(txtBarcode, sku);
			au.click(txtBarcode);
			Thread.sleep(500);
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
		}
	}

	/**
	 * Realiza la captura de skus tomando como entrada un resulSet
	 * @author Marcos Morales
	 * @param resultSet Resultado de la consulta a bd con los SKUS a capturar
	 * @throws SQLException 
	 * @throws InterruptedException 
	 */
	public void cargaSkusEnPlantilla(ResultSet resultSet) throws SQLException, InterruptedException {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		while (resultSet.next()) {
			String sku = resultSet.getString(3);
			au.sendKeys(txtBarcode, sku);
			au.click(txtBarcode);
			Thread.sleep(500);
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
		}
	}

	/**
	 * Realiza el registro de las plantillas
	 * @author Marcos Morales
	 * @param numSkus Cantidad de skus por plantilla
	 * @param offSet Cantidad de registros a descartar en la consulta para no repetir skus en las plantillas
	 * @param nomPlantilla Nombre para la plantilla
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 * @param showStep true/false Define si muestra o no la descripcion del paso
	 * @throws AssertionError 
	 * @throws SQLException 
	 * @throws InterruptedException 
	 */
	public void registraPlantillas(int numSkus, int offSet, String nomPlantilla, boolean screenShot, boolean showStep) throws SQLException, AssertionError, InterruptedException {

		ResultSet rs = obtieneSkusParaCaptura(numSkus, offSet);

		if (rs.isBeforeFirst()) {
			waitForVisibility(txtNombrePlantilla, Duration.ofSeconds(30));
			au.sendKeys(txtNombrePlantilla, nomPlantilla);
			capturarSKUS(rs, screenShot, showStep);
			au.driver.hideKeyboard();
		} else {
			throw new AssertionError("No se encontraron registros al ejecutar la consulta");
		}
	}

	/**
	 * Realiza el registro de SKUS para una plantilla
	 * @author Marcos Morales
	 * @param numSkus Cantidad de skus por plantilla
	 * @param offSet Cantidad de registros a descartar en la consulta para no repetir skus en las plantillas
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 * @param showStep true/false Define si muestra o no la descripcion del paso
	 * @throws AssertionError 
	 * @throws SQLException 
	 * @throws InterruptedException 
	 */
	public void registraSKUS(int numSkus, int offSet, boolean screenShot, boolean showStep) throws SQLException, AssertionError, InterruptedException {

		ResultSet rs = obtieneSkusParaCaptura(numSkus, offSet);

		if (rs.isBeforeFirst()) {
			capturarSKUS(rs, screenShot, showStep);
			au.driver.hideKeyboard();
		} else {
			throw new AssertionError("No se encontraron registros al ejecutar la consulta del metodo: obtieneSkusParaCaptura");
		}
	}

	/**
	 * Realiza el registro de SKUS para una plantilla
	 * @author Marcos Morales
	 * @param numSkus Cantidad de skus por plantilla
	 * @param offSet Cantidad de registros a descartar en la consulta para no repetir skus en las plantillas
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 * @param showStep true/false Define si muestra o no la descripcion del paso
	 * @throws AssertionError 
	 * @throws SQLException 
	 * @throws InterruptedException 
	 */
	public void registraSKUS(String tipoConsulta, int numSkus, int offSet) throws SQLException, AssertionError, InterruptedException {

		ResultSet rs = obtieneSkusParaCaptura(tipoConsulta, numSkus, offSet);

		if (rs.isBeforeFirst()) {
			cargaSkusEnPlantilla(rs);
			au.driver.hideKeyboard();
		} else {
			throw new AssertionError("No se encontraron registros al ejecutar la consulta del metodo: obtieneSkusParaCaptura");
		}
	}

	/**
	 * Realiza el registro de SKUS para una plantilla tomando los SKUs de una plantilla previa
	 * @author Marcos Morales
	 * @param numSkus Cantidad de skus por plantilla
	 * @param nomPlantilla Nombre de la plantilla origen desde donde se obtienen los SKUs
	 * @throws AssertionError 
	 * @throws SQLException 
	 * @throws InterruptedException 
	 */
	public void registraSkusRepetidos(int numSkus, String nomPlantilla) throws SQLException, AssertionError, InterruptedException {

		ResultSet rs = obtieneSkusPorPlantillaBD(numSkus, nomPlantilla);

		if (rs.isBeforeFirst()) {
			capturarSKUS(rs);
			au.driver.hideKeyboard();
		} else {
			throw new AssertionError("No se encontraron registros al ejecutar la consulta del metodo: obtieneSkusPorPlantillaBD");
		}
	}

	/**
	 * Realiza el registro de las plantillas
	 * @author Marcos Morales
	 * @param tipoConsulta skusactivos, skusinactivos, skusactina, plusactivos, plusinactivos, plusactina
	 * @param numSkus Cantidad de skus por plantilla
	 * @param offSet Cantidad de registros a descartar en la consulta para no repetir skus en las plantillas
	 * @param nomPlantilla Nombre para la plantilla
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 * @param showStep true/false Define si muestra o no la descripcion del paso
	 * @throws AssertionError 
	 * @throws SQLException 
	 * @throws InterruptedException 
	 */
	public void registraPlantillas(String tipoConsulta, int numSkus, int offSet, String nomPlantilla, boolean screenShot, boolean showStep) throws SQLException, AssertionError, InterruptedException {

		ResultSet rs = obtieneSkusParaCaptura(tipoConsulta, numSkus, offSet);

		if (rs.isBeforeFirst()) {
			waitForVisibility(txtNombrePlantilla, Duration.ofSeconds(30));
			au.sendKeys(txtNombrePlantilla, nomPlantilla);
			if (showStep) {
				baseExecution.addStep("Ingresar un nombre para la plantilla");
			}
			takeScreenshot(screenShot);
			capturarSKUS(rs, screenShot, showStep);
			au.driver.hideKeyboard();
		} else {
			throw new AssertionError("No se encontraron registros al ejecutar la consulta");
		}
	}

	/**
	 * Realiza el registro de las plantillas
	 * @author Marcos Morales
	 * @param tipoConsulta skusactivos, skusinactivos, skusactina, plusactivos, plusinactivos, plusactina
	 * @param numSkus Cantidad de skus por plantilla
	 * @param offSet Cantidad de registros a descartar en la consulta para no repetir skus en las plantillas
	 * @param nomPlantilla Nombre para la plantilla
	 * @throws AssertionError 
	 * @throws SQLException 
	 * @throws InterruptedException 
	 */
	public void registraPlantillas(String tipoConsulta, int numSkus, int offSet, String nomPlantilla) throws SQLException, AssertionError, InterruptedException {

		ResultSet rs = obtieneSkusParaCaptura(tipoConsulta, numSkus, offSet);

		if (rs.isBeforeFirst()) {
			waitForVisibility(txtNombrePlantilla, Duration.ofSeconds(30));
			au.sendKeys(txtNombrePlantilla, nomPlantilla);
			capturarSKUS(rs, false, false);
			au.driver.hideKeyboard();
		} else {
			throw new AssertionError("No se encontraron registros al ejecutar la consulta");
		}
	}

	/**
	 * Realiza el registro de las plantillas
	 * @author Marcos Morales
	 * @param numSkus Cantidad de skus por plantilla
	 * @param nomPlantilla Nombre para la plantilla
	 * @param plantillaOrigen Nombre de la plantilla desde donde se toman los SKUs
	 * @throws AssertionError 
	 * @throws SQLException 
	 * @throws InterruptedException 
	 */
	public void registraPlantillas(int numSkus, String nomPlantilla, String plantillaOrigen) throws SQLException, AssertionError, InterruptedException {

		ResultSet rs = obtieneSkusPorPlantillaBD(numSkus, plantillaOrigen);

		if (rs.isBeforeFirst()) {
			waitForVisibility(txtNombrePlantilla, Duration.ofSeconds(30));
			au.sendKeys(txtNombrePlantilla, nomPlantilla);
			capturarSKUS(rs);
			au.driver.hideKeyboard();
		} else {
			throw new AssertionError("No se encontraron registros al ejecutar la consulta");
		}
	}

	/**
	 * Metodo para registrar el nombre de una plantilla
	 * @author Marcos Morales
	 * @param nomPlantilla Nombre de la Plantilla
	 */
	public void capturarNombrePlantilla(String nomPlantilla) {
		waitForVisibility(txtNombrePlantilla, Duration.ofSeconds(30));
		au.sendKeys(txtNombrePlantilla, nomPlantilla);
		au.driver.hideKeyboard();
	}

	/**
	 * Devuelve el listado de skus agregados a una plantilla
	 * @author Marcos Morales
	 */
	public List<WebElement> skusAgregados() {
		WebElement parentSKUS = au.driver.findElement(lstSkus);
		List<WebElement> skus = parentSKUS.findElements(lstSKUS);
		return skus;
	}

	/**
	 * Simplifica los pasos para la Creacion de plantillas de SKUS
	 * @author Marcos Morales
	 * @param numPlan Cantidad de plantillas a crear
	 * @param numSkus Cantidad de skus por plantilla
	 * @param numSem Numero de la semana
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 * @param showStep true/false Define si muestra o no la descripcion del paso
	 * @throws AssertionError 
	 * @throws SQLException 
	 * @throws InterruptedException 
	 */
	public void crearPlantillas(int numPlan, int numSkus, int numSem, boolean screenShot, boolean showStep) throws SQLException, AssertionError, InterruptedException {
		String[] turnos = {"morning", "afternoon", "night"};
		Comunes comunes = new Comunes(testCase, au, baseExecution);
		int offSet = 0;
		for (int i=0; i<numPlan; i++) {
			int noPl = i+1;
			String turnoActual = turnos[i % 3];
			clickBtnPlus(screenShot);
			clickComboColaboradores(screenShot);
			seleccionarColaborador(i, screenShot);
			seleccionarTurno(turnoActual, screenShot);
			clickBtnContinuar(screenShot);
			registraPlantillas(numSkus, offSet, "PlantillaQA" + noPl + "Semana" + numSem, screenShot, showStep);
			clickBtnGuardar(screenShot);
			comunes.clickBtnAceptar(screenShot);
			comunes.esperaSeOculteLoading();
			esperaSeOcultaMensajeGuardado();
			offSet += numSkus;
		}
	}

	/**
	 * Simplifica los pasos para la Creacion de plantillas de SKUS
	 * @author Marcos Morales
	 * @param tipoConsulta skusactivos, skusinactivos, skusactina, plusactivos, plusinactivos, plusactina
	 * @param numPlan Cantidad de plantillas a crear
	 * @param numSkus Cantidad de skus por plantilla
	 * @param numSem Numero de la semana
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 * @param showStep true/false Define si muestra o no la descripcion del paso
	 * @throws AssertionError 
	 * @throws SQLException 
	 * @throws InterruptedException 
	 */
	public void crearPlantillas(String tipoConsulta, int numPlan, int numSkus, int numSem, boolean screenShot, boolean showStep) throws SQLException, AssertionError, InterruptedException {
		String[] turnos = {"morning", "afternoon", "night"};
		Comunes comunes = new Comunes(testCase, au, baseExecution);
		int offSet = 0;
		for (int i=0; i<numPlan; i++) {
			int noPl = i+1;
			String turnoActual = turnos[i % 3];
			clickBtnPlus(screenShot);
			clickComboColaboradores(screenShot);
			seleccionarColaborador(i, screenShot);
			seleccionarTurno(turnoActual, screenShot);
			clickBtnContinuar(screenShot);
			registraPlantillas(tipoConsulta, numSkus, offSet, "Plantilla" + noPl + "Semana" + numSem, screenShot, showStep);
			clickBtnGuardar(screenShot);
			comunes.clickBtnAceptar(screenShot);
			comunes.esperaSeOculteLoading();
			esperaSeOcultaMensajeGuardado();
			offSet += numSkus;
		}
	}

	/**
	 * Simplifica los pasos para la Creacion de plantillas de SKUS
	 * @author Marcos Morales
	 * @param tipoConsulta skusactivos, skusinactivos, skusactina, plusactivos, plusinactivos, plusactina
	 * @param numPlan Cantidad de plantillas a crear
	 * @param numSkus Cantidad de skus por plantilla
	 * @param nomPlan Nombre para la plantilla
	 * @param numSem Numero de la semana
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 * @param showStep true/false Define si muestra o no la descripcion del paso
	 * @throws AssertionError 
	 * @throws SQLException 
	 * @throws InterruptedException 
	 */
	public void crearPlantillas(String tipoConsulta, int numPlan, int numSkus, String nomPlan, int numSem, boolean screenShot, boolean showStep) throws SQLException, AssertionError, InterruptedException {
		String[] turnos = {"morning", "afternoon", "night"};
		Comunes comunes = new Comunes(testCase, au, baseExecution);
		int offSet = 0;
		for (int i=0; i<numPlan; i++) {
			int noPl = i+1;
			String turnoActual = turnos[i % 3];
			clickBtnPlus(screenShot);
			clickComboColaboradores(screenShot);
			seleccionarColaborador(i, screenShot);
			seleccionarTurno(turnoActual, screenShot);
			clickBtnContinuar(screenShot);
			registraPlantillas(tipoConsulta, numSkus, offSet, nomPlan + noPl + "Semana" + numSem, screenShot, showStep);
			clickBtnGuardar(screenShot);
			comunes.clickBtnAceptar(screenShot);
			comunes.esperaSeOculteLoading();
			esperaSeOcultaMensajeGuardado();
			offSet += numSkus;
		}
	}

	/**
	 * Simplifica los pasos para la Creacion de plantillas de SKUS
	 * @author Marcos Morales
	 * @param tipoConsulta skusactivos, skusinactivos, skusactina, plusactivos, plusinactivos, plusactina
	 * @param numPlan Cantidad de plantillas a crear
	 * @param numSkus Cantidad de skus por plantilla
	 * @param nomPlan Nombre para la plantilla
	 * @throws AssertionError 
	 * @throws SQLException 
	 * @throws InterruptedException 
	 */
	public void crearPlantillas(String tipoConsulta, int numPlan, int numSkus, String nomPlan) throws SQLException, AssertionError, InterruptedException {

		String[] turnos = {"morning", "afternoon", "night"};
		Comunes comunes = new Comunes(testCase, au, baseExecution);

		int offSet = ThreadLocalRandom.current().nextInt(50, 100);

		for (int i=0; i<numPlan; i++) {
			String turnoActual = turnos[i % 3];
			clickBtnPlus(false);
			clickComboColaboradores(false);
			seleccionarColaborador(i, false);
			seleccionarTurno(turnoActual, false);
			clickBtnContinuar(false);
			registraPlantillas(tipoConsulta, numSkus, offSet, nomPlan);
			clickBtnGuardar(false);
			comunes.clickBtnAceptar(false);
			comunes.esperaSeOculteLoading();
			esperaSeOcultaMensajeGuardado();
			offSet += numSkus;
		}
	}

	/**
	 * Simplifica los pasos para la Creacion de plantillas de SKUS
	 * @author Marcos Morales
	 * @param numPlan Cantidad de plantillas a crear
	 * @param numSkus Cantidad de skus por plantilla
	 * @param nomPlan Nombre para la plantilla
	 * @param planOrg Nombre de la plantilla origen desde la que se obtienen los SKUs a capturar
	 * @throws AssertionError 
	 * @throws SQLException 
	 * @throws InterruptedException 
	 */
	public void crearPlantillas(int numPlan, int numSkus, String nomPlan, String planOrg) throws SQLException, AssertionError, InterruptedException {

		String[] turnos = {"morning", "afternoon", "night"};
		Comunes comunes = new Comunes(testCase, au, baseExecution);

		for (int i=0; i<numPlan; i++) {
			String turnoActual = turnos[i % 3];
			clickBtnPlus(false);
			clickComboColaboradores(false);
			seleccionarColaborador(i, false);
			seleccionarTurno(turnoActual, false);
			clickBtnContinuar(false);
			registraPlantillas(numSkus, nomPlan, planOrg);
			clickBtnGuardar(false);
			comunes.clickBtnAceptar(false);
			comunes.esperaSeOculteLoading();
			esperaSeOcultaMensajeGuardado();
		}
	}

	/**
	 * Seleccionar plantilla para edicion
	 * @author Marcos Morales
	 * @param idxPlan Indice de la plantilla a editar
	 * @param idxColab Indice del colaborador a seleccionar
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 * @throws AssertionError 
	 * @throws SQLException 
	 */
	public void editarPlantillaValidaColaboradores(int idxPlan, int idxColab, boolean screenShot) throws SQLException, AssertionError {

		String nomPlanOrg = au.driver.findElement(firstTemplate).getText();
		System.out.println("Nombre de Plantilla a editar: " + nomPlanOrg);

		seleccionarPlantillaEdicion(idxPlan, screenShot);
		clickComboColaboradores(screenShot);
		seleccionarColaborador(idxColab, screenShot);
	}

	/**
	 * Seleccionar encargado y turno
	 * @author Marcos Morales
	 * @param idxColab Indice del colaborador a seleccionar
	 * @param idxTurno Indice del turno a seleccionar
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void seleccionarEncargadoTurno(int idxColab, int idxTurno, boolean screenShot) {

		String[] turnos = {"morning", "afternoon", "night"};

		clickCboColaboradores(screenShot);
		seleccionarColaborador(idxColab, screenShot);
		seleccionarTurno(turnos[idxTurno], screenShot);
		clickBtnGuardarModalColabs(screenShot);
	}

	/**
	 * Obtiene el texto de un sku Eliminado
	 * @author Marcos Morales
	 * @param idxProd Indice del SKU a eliminar
	 */
	public String obtieneSkuAEliminar(int idxProd) {
		au.driver.hideKeyboard();
		String skuAEliminar = au.driver.findElement(By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_product_sku\"])[" + idxProd + "]")).getText();
		return skuAEliminar;
	}

	/**
	 * Metodo para hacer click en el SKU a borrar
	 * @author Marcos Morales
	 * @param idxBorrar Indice del SKU a eliminar
	 */
	public void eliminaSKUS(int idxBorrar) {
		au.driver.hideKeyboard();
		WebElement parentSKUS = au.driver.findElement(lstSkus);
		List<WebElement> deleteBtns = parentSKUS.findElements(btnDeleteSKU);
		WebElement borrar = deleteBtns.get(idxBorrar);
		borrar.click();
	}

	/**
	 * Devuelve la confirmacion que el SKU fue removido del listado en la pantalla
	 * @author Lizbeth Hernandez
	 * @param skuEliminado Indice del SKU a eliminar
	 */
	public boolean  retornaSKUEliminado(String skuEliminado) {
		au.driver.hideKeyboard();
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(5));
		boolean elementoNOVisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_product_sku\" and @text=\"" + skuEliminado + "\"]")));
		return elementoNOVisible;
	}

	/**
	 * Ejecuta el flujo para crear Plantillas de SKUS
	 * @author Marcos Morales
	 * @param user Usuario para ingresar a la aplicacion Oxxo al día
	 * @param pass Contraseña del usuario
	 * @param app Ej. com.femco.oxxo.operaciones.mitienda.debug
	 * @param numPlan Cantidad de plantillas a crear
	 * @param numSkus Cantidad de skus por plantilla
	 * @param semana actual/anterior
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 * @param showStep true/false Define si muestra o no la descripcion del paso
	 * @throws AssertionError 
	 * @throws SQLException 
	 */
	public void prerequisitoPlantillasSKUS(String user, String pass, String app, int numPlan, int numSkus, String semana, boolean screenShot, boolean showStep) throws IOException, InterruptedException, SQLException, AssertionError {

		Login login = new Login(testCase, au, baseExecution);
		Menus menus = new Menus(testCase, au, baseExecution);
		Comunes comunes = new Comunes(testCase, au, baseExecution);

		Calendar calendario = Calendar.getInstance();
		int numeroSemana;

		//Login
		login.login(user, pass);

		//Ingresar a Conteos Propios
		menus.ingresarConteosPropios(screenShot);

		//Seleccionar semana
		comunes.seleccionarSemana(screenShot);

		//Continuar
		comunes.clickBtnContinuar(screenShot);

		//Crear nueva plantilla
		menus.clickBtnNuevaPlantilla(screenShot);

		//Crear plantillas
		switch (semana.toLowerCase()) {
		case "actual":
			numeroSemana = calendario.get(Calendar.WEEK_OF_YEAR);
			crearPlantillas(numPlan, numSkus, numeroSemana, screenShot, showStep);
			break;
		case "anterior":
			numeroSemana = calendario.get(Calendar.WEEK_OF_YEAR)-1;
			crearPlantillas(numPlan, numSkus, numeroSemana, screenShot, showStep);
			break;
		}

		//Cerrar Aplicacion
		comunes.finalizarApp(app);
	}

	/**
	 * Ejecuta el flujo para crear Plantillas de SKUS
	 * @author Marcos Morales
	 * @param tipoConsulta skusactivos, skusinactivos, skusactina, plusactivos, plusinactivos, plusactina
	 * @param user Usuario para ingresar a la aplicacion Oxxo al día
	 * @param pass Contraseña del usuario
	 * @param app Ej. com.femco.oxxo.operaciones.mitienda.debug
	 * @param numPlan Cantidad de plantillas a crear
	 * @param numSkus Cantidad de skus por plantilla
	 * @param semana actual/anterior
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 * @param showStep true/false Define si muestra o no la descripcion del paso
	 * @throws AssertionError 
	 * @throws SQLException 
	 */
	public void prerequisitoPlantillasSKUS(String tipoConsulta, String user, String pass, String app, int numPlan, int numSkus, String semana, boolean screenShot, boolean showStep) throws IOException, InterruptedException, SQLException, AssertionError {

		Login login = new Login(testCase, au, baseExecution);
		Menus menus = new Menus(testCase, au, baseExecution);
		Comunes comunes = new Comunes(testCase, au, baseExecution);

		Calendar calendario = Calendar.getInstance();
		int numeroSemana;

		//Login
		login.login(user, pass);

		//Ingresar a Conteos Propios
		menus.ingresarConteosPropios(screenShot);

		//Seleccionar semana
		comunes.seleccionarSemana(screenShot);

		//Continuar
		comunes.clickBtnContinuar(screenShot);

		//Crear nueva plantilla
		menus.clickBtnNuevaPlantilla(screenShot);

		//Crear plantillas
		switch (semana.toLowerCase()) {
		case "actual":
			numeroSemana = calendario.get(Calendar.WEEK_OF_YEAR);
			crearPlantillas(tipoConsulta, numPlan, numSkus, numeroSemana, screenShot, showStep);
			break;
		case "anterior":
			numeroSemana = calendario.get(Calendar.WEEK_OF_YEAR)-1;
			crearPlantillas(tipoConsulta, numPlan, numSkus, numeroSemana, screenShot, showStep);
			break;
		}

		//Cerrar Aplicacion
		comunes.finalizarApp(app);
	}

	/**
	 * Ejecuta el flujo para crear Plantillas de SKUS
	 * @author Marcos Morales
	 * @param tipoConsulta skusactivos, skusinactivos, skusactina, plusactivos, plusinactivos, plusactina
	 * @param user Usuario para ingresar a la aplicacion Oxxo al día
	 * @param pass Contraseña del usuario
	 * @param app Ej. com.femco.oxxo.operaciones.mitienda.debug
	 * @param numPlan Cantidad de plantillas a crear
	 * @param numSkus Cantidad de skus por plantilla
	 * @param nomPlan Nombre para la plantilla
	 * @param semana actual/anterior
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 * @param showStep true/false Define si muestra o no la descripcion del paso
	 * @throws AssertionError 
	 * @throws SQLException 
	 */
	public void prerequisitoPlantillasSKUS(String tipoConsulta, String user, String pass, String app, int numPlan, int numSkus, String nomPlan, String semana, boolean screenShot, boolean showStep) throws IOException, InterruptedException, SQLException, AssertionError {

		Login login = new Login(testCase, au, baseExecution);
		Menus menus = new Menus(testCase, au, baseExecution);
		Comunes comunes = new Comunes(testCase, au, baseExecution);

		Calendar calendario = Calendar.getInstance();
		int numeroSemana;

		//Login
		login.login(user, pass);

		//Ingresar a Conteos Propios
		menus.ingresarConteosPropios(screenShot);

		//Seleccionar semana
		comunes.seleccionarSemana(screenShot);

		//Continuar
		comunes.clickBtnContinuar(screenShot);

		//Crear nueva plantilla
		menus.clickBtnNuevaPlantilla(screenShot);

		//Crear plantillas
		switch (semana.toLowerCase()) {
		case "actual":
			numeroSemana = calendario.get(Calendar.WEEK_OF_YEAR);
			crearPlantillas(tipoConsulta, numPlan, numSkus, nomPlan, numeroSemana, screenShot, showStep);
			break;
		case "anterior":
			numeroSemana = calendario.get(Calendar.WEEK_OF_YEAR)-1;
			crearPlantillas(tipoConsulta, numPlan, numSkus, nomPlan, numeroSemana, screenShot, showStep);
			break;
		}

		//Cerrar Aplicacion
		comunes.finalizarApp(app);
	}

	/**
	 * Ejecuta el flujo para crear Plantillas de SKUS
	 * @author Marcos Morales
	 * @param tipoConsulta skusactivos, skusinactivos, skusactina, plusactivos, plusinactivos, plusactina
	 * @param user Usuario para ingresar a la aplicacion Oxxo al día
	 * @param pass Contraseña del usuario
	 * @param app Ej. com.femco.oxxo.operaciones.mitienda.debug
	 * @param numPlan Cantidad de plantillas a crear
	 * @param numSkus Cantidad de skus por plantilla
	 * @param nomPlan Nombre para la plantilla
	 * @throws AssertionError
	 * @throws SQLException 
	 */
	public void prerequisitoPlantillasSKUS(String tipoConsulta, String user, String pass, String app, int numPlan, int numSkus, String nomPlan) throws IOException, InterruptedException, SQLException, AssertionError {

		Login login = new Login(testCase, au, baseExecution);
		Menus menus = new Menus(testCase, au, baseExecution);
		Comunes comunes = new Comunes(testCase, au, baseExecution);

		//Login
		login.login(user, pass);

		//Ingresar a Conteos Propios
		menus.ingresarConteosPropios(false);

		//Seleccionar semana
		comunes.seleccionarSemana(false);

		//Continuar
		comunes.clickBtnContinuar(false);

		//Crear nueva plantilla
		menus.clickBtnNuevaPlantilla(false);

		//Crear plantillas
		crearPlantillas(tipoConsulta, numPlan, numSkus, nomPlan);

		//Cerrar Aplicacion
		comunes.finalizarApp(app);
	}

	/**
	 * Ejecuta el flujo para crear Plantillas de SKUS
	 * @author Marcos Morales
	 * @param user Usuario para ingresar a la aplicacion Oxxo al día
	 * @param pass Contraseña del usuario
	 * @param app Ej. com.femco.oxxo.operaciones.mitienda.debug
	 * @param numPlan Cantidad de plantillas a crear
	 * @param numSkus Cantidad de skus por plantilla
	 * @param nomPlan Nombre para la plantilla
	 * @param planOrg Nombre de la plantilla de la que se obtienen los SKUs
	 * @throws AssertionError 
	 * @throws SQLException 
	 */
	public void prerequisitoPlantillasSKUS(String user, String pass, String app, int numPlan, int numSkus, String nomPlan, String planOrg) throws IOException, InterruptedException, SQLException, AssertionError {

		Login login = new Login(testCase, au, baseExecution);
		Menus menus = new Menus(testCase, au, baseExecution);
		Comunes comunes = new Comunes(testCase, au, baseExecution);

		//Login
		login.login(user, pass);

		//Ingresar a Conteos Propios
		menus.ingresarConteosPropios(false);

		//Seleccionar semana
		comunes.seleccionarSemana(false);

		//Continuar
		comunes.clickBtnContinuar(false);

		//Crear nueva plantilla
		menus.clickBtnNuevaPlantilla(false);

		//Crear plantillas
		crearPlantillas(numPlan, numSkus, nomPlan, planOrg);

		//Cerrar Aplicacion
		comunes.finalizarApp(app);
	}

	/**
	 * Selecciona una plantilla de acuerdo a su posicion en pantalla, primer registro = 1
	 * @author Marcos Morales
	 * @param index Indice de la plantilla a ingresar
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void seleccionarPlantilla(int index, boolean screenShot) {
		WebElement plantilla = au.driver.findElement(By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_template_name\"])[" + index +"]"));
		waitForVisibility(plantilla, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		plantilla.click();
	}

	/**
	 * Selecciona una plantilla para edicion
	 * @author Marcos Morales
	 * @param index Indice de la plantilla a editar
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void seleccionarPlantillaEdicion(int index, boolean screenShot) {
		WebElement plantilla = au.driver.findElement(By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_edit_template\"])[" + index + "]"));
		waitForVisibility(plantilla, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		plantilla.click();
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
	 * Devuelve el estado de los elementos opcion Crear Nueva Plantilla
	 * @author Lizbeth Hernandez 
	 */
	public boolean[] retornaEstadoElemPantallaNuevaPlantilla() {
		boolean[] status = new boolean[3];
		try {
			waitForVisibility(txtBusqueda, Duration.ofSeconds(30));
			String semanaStr = obtenerSemanaActual();
			status[0] = au.driver.findElement(By.xpath("//android.widget.TextView[@text=\"" + semanaStr + "\"]")).isDisplayed();
			status[1] = au.driver.findElement(txtBusqueda).isDisplayed();
			status[2] = au.driver.findElement(lblPlantillas).isDisplayed();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * Valida el estado del campo Buscar por Plantilla
	 * @author Marcos Morales
	 */
	public boolean[] retornaEstadoCampoDeBusqueda() {
		boolean[] status = new boolean[1];
		try {
			waitForVisibility(txtBusqueda, Duration.ofSeconds(30));
			status[0] = au.driver.findElement(txtBusqueda).isDisplayed();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * Valida el estado de los elementos de la pantalla
	 * Asignar encargado
	 * @author Marcos Morales
	 */
	public String[] retornaEtiquetasPantallaAsignarEncargado() {
		String[] etiquetas = new String[2];
		try {
			waitForVisibility(tittleAsigEncargado, Duration.ofSeconds(30));
			etiquetas[0] = au.driver.findElement(tittleAsigEncargado).getText();
			etiquetas[1] = au.driver.findElement(subtitleAsigEncargado).getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return etiquetas;
	}

	/**
	 * Valida el estado de los elementos del modal
	 * Asignar encargado
	 * @author Marcos Morales
	 */
	public String[] retornaEtiquetasModalAsignarEncargado() {
		String[] etiquetas = new String[2];
		try {
			waitForVisibility(modalBtnAceptar, Duration.ofSeconds(30));
			etiquetas[0] = au.driver.findElement(modalColabTitle).getText();
			etiquetas[1] = au.driver.findElement(modalSubtitle).getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return etiquetas;
	}

	/**
	 * Valida el estado de las opciones para crear una nueva plantilla
	 * @author Marcos Morales
	 */
	public boolean[] retornaEstadoElementosCrearNuevaPlantilla() {
		boolean[] status = new boolean[4];
		try {
			waitForVisibility(opcion1, Duration.ofSeconds(30));
			status[0] = au.driver.findElement(txtNombrePlantilla).isDisplayed();
			status[1] = au.driver.findElement(txtBarcode).isDisplayed();
			status[2] = au.driver.findElement(btnCancelar).isDisplayed();
			status[3] = au.driver.findElement(btnGuardar).isDisplayed();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * Valida el estado de los elementos al inmgresar al detalle de una plantilla
	 * @author Marcos Morales
	 */
	public boolean[] retornaEstadoElementosDetallePlantilla() {
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
	 * Crea una instancia de la clase detailTemplate para mantener en memoria
	 * el nombre de la plantilla al ingresar al detalle
	 * @author Marcos Morales
	 */
	public void instanciaDetailTemplate() {
		String textPlantilla = au.driver.findElement(txtNomPlantilla).getText();
		String plantilla = textPlantilla.split("-")[0].trim();
		detailTemplate = new DetallePlantilla(plantilla);
	}

	/**
	 * Obtiene TODOS los SKUS de la plantilla a la que se ingreso
	 * @author Marcos Morales
	 */
	public ResultSet obtieneSkusPorPlantillaBD() {

		String plantilla = detailTemplate.getPlantilla();

		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			String query = "select	 th.TEMPLATE_NAME\r\n"
					+ "		,trim(td.SKU_REF) [SKU]\r\n"
					+ "from hc.TEMPLATE_SELF_GENERATED_COUNTS_HEADER th\r\n"
					+ "inner join hc.TEMPLATE_SELF_GENERATED_COUNTS_DETAIL td on th.ID = td.TEMPLATE_HEADER_ID\r\n"
					+ "where th.TEMPLATE_NAME = '" + plantilla + "'\r\n"
					+ "and th.IS_ACTIVE = 1\r\n"
					+ "AND DATEPART(ISO_WEEK, th.TM_CRT) = DATEPART(ISO_WEEK, GETDATE())";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Obtiene un numero especifico de SKUS de la plantilla a la que se ingreso
	 * @author Marcos Morales
	 * @param numSKUS Cantidad de registros a obtener
	 */
	public ResultSet obtieneSkusPorPlantillaBD(int numSKUS) {

		String plantilla = detailTemplate.getPlantilla();

		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			String query = "select top "+ numSKUS +" th.TEMPLATE_NAME\r\n"
					+ "		,trim(td.SKU_REF) [SKU]\r\n"
					+ "from hc.TEMPLATE_SELF_GENERATED_COUNTS_HEADER th\r\n"
					+ "inner join hc.TEMPLATE_SELF_GENERATED_COUNTS_DETAIL td on th.ID = td.TEMPLATE_HEADER_ID\r\n"
					+ "where th.TEMPLATE_NAME like '%" + plantilla + "%'";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Obtiene un numero especifico de SKUS para una plantilla en particular
	 * @author Marcos Morales
	 * @param numSKUS Cantidad de registros a obtener
	 * @param plantilla Nombre de la plantilla de la que se extraen los SKUs
	 */
	public ResultSet obtieneSkusPorPlantillaBD(int numSKUS, String plantilla) {

		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			String query = "select top "+ numSKUS +" th.TEMPLATE_NAME\r\n"
					+ "		,trim(td.SKU_REF) [SKU]\r\n"
					+ "from hc.TEMPLATE_SELF_GENERATED_COUNTS_HEADER th\r\n"
					+ "inner join hc.TEMPLATE_SELF_GENERATED_COUNTS_DETAIL td on th.ID = td.TEMPLATE_HEADER_ID\r\n"
					+ "where th.TEMPLATE_NAME like '%" + plantilla + "%'";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Obtiene un numero especifico de SKUS con la opcion de descartar registros
	 * @author Marcos Morales
	 * @param numSKUS Cantidad de registros a obtener
	 * @param plantilla Nombre de la plantilla de la que se extraen los SKUs
	 */
	public ResultSet obtieneSkusPorPlantillaBD(int numSkus, int offSet) {

		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			String query = "select	 th.TEMPLATE_NAME\r\n"
					+ "		,trim(td.SKU_REF) [SKU]\r\n"
					+ "from hc.TEMPLATE_SELF_GENERATED_COUNTS_HEADER th\r\n"
					+ "inner join hc.TEMPLATE_SELF_GENERATED_COUNTS_DETAIL td on th.ID = td.TEMPLATE_HEADER_ID\r\n"
					+ "where th.TEMPLATE_NAME like '%PlantillaQA1%'\r\n"
					+ "and th.IS_ACTIVE = 1\r\n"
					+ "AND DATEPART(ISO_WEEK, th.TM_CRT) = DATEPART(ISO_WEEK, GETDATE())\r\n"
					+ "order by td.ID\r\n"
					+ "OFFSET " + offSet + " ROWS\r\n"
					+ "FETCH NEXT " + numSkus + " ROW ONLY;";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Obtiene un numero especifico de SKUS de la plantilla a la que se ingreso
	 * @author Marcos Morales
	 * @param numSKUS Cantidad de registros a obtener
	 */
	public ResultSet obtieneSkusPorPlantillaConExistenciaBD(int numSKUS) {

		String plantilla = detailTemplate.getPlantilla();

		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			String query = "select top " + numSKUS + " thd.TEMPLATE_NAME\r\n"
					+ "		,trim(tdt.SKU_REF) [SKU]\r\n"
					+ "		,det.IO_OnHand\r\n"
					+ "from hc.II_HEADER_IO_HEADER as hh \r\n"
					+ "join hc.io_detail as det on det.IO_Folio = hh.IO_FOLIO\r\n"
					+ "join hc.TEMPLATE_SELF_GENERATED_COUNTS_HEADER thd on hh.II_FOLIO = thd.II_FOLIO\r\n"
					+ "join hc.TEMPLATE_SELF_GENERATED_COUNTS_DETAIL tdt on thd.ID = tdt.TEMPLATE_HEADER_ID and det.IO_SKU_REF = tdt.SKU_REF\r\n"
					+ "where hh.II_FOLIO = (SELECT II_FOLIO FROM [HC].[II_HEADER] WHERE YEAR(II_STARTDATE) = YEAR(GETDATE()) AND II_WEEKNUMBER = DATEPART(WEEK, GETDATE()) AND II_TYPE = 'P')\r\n"
					+ "and thd.TEMPLATE_NAME like '%" + plantilla + "%'\r\n"
					+ "and thd.IS_ACTIVE = 1\r\n"
					+ "and det.IO_OnHand > 0\r\n"
					+ "order by det.IO_SKU_REF desc";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Obtiene el total de SKUs por Plantilla
	 * @author Marcos Morales
	 */
	public ResultSet obtieneCantidadSkusPorPlantillaBD() {

		String textPlantilla = au.driver.findElement(txtNomPlantilla).getText();
		String plantilla = textPlantilla.split("-")[0].trim();

		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			String query = "select count(td.SKU_REF) [SKUs]\r\n"
					+ "from hc.TEMPLATE_SELF_GENERATED_COUNTS_HEADER th\r\n"
					+ "inner join hc.TEMPLATE_SELF_GENERATED_COUNTS_DETAIL td on th.ID = td.TEMPLATE_HEADER_ID\r\n"
					+ "where th.TEMPLATE_NAME like '%" + plantilla + "%'";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Metodo para capturar un conteo y seleccionar Cancelar o Guardar
	 * @author Marcos Morales
	 * @param resultSet Resultado de la consulta a bd con los SKUS a capturar
	 * @param valorConteo Conteo físico a capturar
	 * @param locator Identicador del boton para Guardar o Cancelar
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void ingresarConteo(ResultSet resultSet, String valorConteo, By locator, boolean screenShot) throws SQLException {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		while (resultSet.next()) {
			String sku = resultSet.getString(2);
			baseExecution.addStep("Capturar el SKU a contabilizar");
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
	 * Metodo para capturar un conteo y seleccionar Cancelar o Guardar
	 * @author Marcos Morales
	 * @param resultSet Resultado de la consulta a bd con los SKUS a capturar
	 * @param valorConteo Conteo físico a capturar
	 * @param locator Identicador del boton para Guardar o Cancelar
	 */
	public void ingresarConteo(ResultSet resultSet, String valorConteo, By locator) throws SQLException {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		while (resultSet.next()) {
			String sku = resultSet.getString(2);
			au.sendKeys(txtBarcode, sku);
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			waitForVisibility(txtFisicos, Duration.ofSeconds(30));
			au.sendKeys(txtFisicos, valorConteo);
			au.click(locator);
		}
	}

	/**
	 * Metodo para capturar un conteo menor al teorico
	 * @author Marcos Morales
	 * @param resultSet Resultado de la consulta a bd con los SKUS a capturar
	 * @param locator Identicador del boton para Guardar o Cancelar
	 * @param conteoPrevio Bandera que identifica si el conteo es nuevo o no: true/false
	 * @param debeGuardar Bandera que define si guarda en memoria los datos de sku, teorico y fisico para el regitro actual
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void ingresarConteoMenor(ResultSet resultSet, By locator, boolean conteoPrevio, boolean debeGuardar, boolean screenShot) throws SQLException {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		while (resultSet.next()) {
			String sku = resultSet.getString(2);
			au.sendKeys(txtBarcode, sku);
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			capturarFisico("menor", conteoPrevio, debeGuardar, screenShot);
			takeScreenshot(screenShot);
			au.click(locator);
		}
	}

	/**
	 * Metodo para capturar un conteo mayor al teorico
	 * @author Marcos Morales
	 * @param resultSet Resultado de la consulta a bd con los SKUS a capturar
	 * @param locator Identicador del boton para Guardar o Cancelar
	 * @param conteoPrevio Bandera que identifica si el conteo es nuevo o no: true/false
	 * @param debeGuardar Bandera que define si guarda en memoria los datos de sku, teorico y fisico para el regitro actual
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void ingresarConteoMayor(ResultSet resultSet, By locator, boolean conteoPrevio, boolean debeGuardar, boolean screenShot) throws SQLException {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		while (resultSet.next()) {
			String sku = resultSet.getString(2);
			au.sendKeys(txtBarcode, sku);
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			capturarFisico("mayor", conteoPrevio, debeGuardar, screenShot);
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
			String sku = resultSet.getString(2);
			au.sendKeys(txtBarcode, sku);
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			waitForVisibility(txtFisicos, Duration.ofSeconds(30));
			au.sendKeys(txtFisicos, valorConteo);
		}
	}

	/**
	 * Realiza el conteo de TODOs los productos de una plantilla tomando los SKUS desde la BD
	 * @author Marcos Morales
	 * @param valorConteo Valor a capturar para cada conteo (físico)
	 * @throws SQLException 
	 */
	public void registrarConteoPorPlantilla(String valorConteo) throws SQLException {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		ResultSet rs = obtieneSkusPorPlantillaBD();
		ingresarConteo(rs, valorConteo, btnCountProdSave);
	}

	/**
	 * Realiza el conteo de TODOs los productos de una plantilla tomando los SKUS desde la BD
	 * @author Marcos Morales
	 * @param valorConteo Valor a capturar para cada conteo (físico)
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 * @throws SQLException 
	 */
	public void registrarConteoPorPlantilla(String valorConteo, boolean screenShot) throws SQLException {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		ResultSet rs = obtieneSkusPorPlantillaBD();
		ingresarConteo(rs, valorConteo, btnCountProdSave, screenShot);
	}

	/**
	 * Realiza el conteo de un numero especifico de skus para una plantilla tomando los SKUS desde la BD
	 * @author Marcos Morales
	 * @param numSKUS Numero de SKUS a registrar
	 * @param valorConteo Valor a capturar para cada conteo (físico)
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 * @throws SQLException 
	 */
	public void registrarConteoPorPlantilla(int numSKUS, String valorConteo, boolean screenShot) throws SQLException {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		ResultSet rs = obtieneSkusPorPlantillaBD(numSKUS);
		ingresarConteo(rs, valorConteo, btnCountProdSave, screenShot);
	}

	/**
	 * Realiza el conteo de un numero especifico de skus para una plantilla tomando los SKUS desde la BD
	 * @author Marcos Morales
	 * @param numSKUS Numero de SKUS a registrar
	 * @param valorConteo Valor a capturar para cada conteo (físico)
	 * @throws SQLException 
	 */
	public void registrarConteoPorPlantilla(int numSKUS, String valorConteo) throws SQLException {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		ResultSet rs = obtieneSkusPorPlantillaBD(numSKUS);
		ingresarConteo(rs, valorConteo, btnCountProdSave);
	}

	/**
	 * Ingresa un valor menor al teorico en la captura del conteo
	 * para los SKUS de una plantilla
	 * @author Marcos Morales
	 * @param conteoPrevio Bandera que identifica si el conteo es nuevo o no: true/false
	 * @param debeGuardar Bandera que define si guarda en memoria los datos de sku, teorico y fisico para el regitro actual
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 * @throws SQLException 
	 */
	public void registrarConteoMenorPorPlantilla(boolean conteoPrevio, boolean debeGuardar, boolean screenShot) throws SQLException {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		ResultSet rs = obtieneSkusPorPlantillaBD();
		ingresarConteoMenor(rs, btnCountProdSave, conteoPrevio, debeGuardar, screenShot);
	}

	/**
	 * Ingresa un valor menor al teorico en la captura del conteo
	 * para los SKUS de una plantilla
	 * @author Marcos Morales
	 * @param numSkus Cantidad de registros a obtener
	 * @param offSet Numero de registros a descartar en la consulta
	 * @param conteoPrevio Bandera que identifica si el conteo es nuevo o no: true/false
	 * @param debeGuardar Bandera que define si guarda en memoria los datos de sku, teorico y fisico para el regitro actual
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 * @throws SQLException 
	 */
	public void registrarConteoMenorPorPlantilla(int numSkus, int offSet, boolean conteoPrevio, boolean debeGuardar, boolean screenShot) throws SQLException {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		ResultSet rs = obtieneSkusPorPlantillaBD(numSkus, offSet);
		ingresarConteoMenor(rs, btnCountProdSave, conteoPrevio, debeGuardar, screenShot);
	}

	/**
	 * Ingresa un valor mayor al teorico en la captura del conteo
	 * para los SKUS de una plantilla
	 * @author Marcos Morales
	 * @param numSKUS Cantidad de registros a obtener
	 * @param conteoPrevio Bandera que identifica si el conteo es nuevo o no: true/false
	 * @param debeGuardar Bandera que define si guarda en memoria los datos de sku, teorico y fisico para el regitro actual
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 * @throws SQLException 
	 */
	public void registrarConteoMayorPorPlantilla(int numSKUS, boolean conteoPrevio, boolean debeGuardar, boolean screenShot) throws SQLException {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		ResultSet rs = obtieneSkusPorPlantillaBD(numSKUS);
		ingresarConteoMayor(rs, btnCountProdSave, conteoPrevio, debeGuardar, screenShot);
	}

	/**
	 * Ingresa un valor mayor al teorico en la captura del conteo
	 * para los SKUS de una plantilla
	 * @author Marcos Morales
	 * @param numSkus Cantidad de registros a obtener
	 * @param offSet Numero de registros a descartar en la consulta
	 * @param conteoPrevio Bandera que identifica si el conteo es nuevo o no: true/false
	 * @param debeGuardar Bandera que define si guarda en memoria los datos de sku, teorico y fisico para el regitro actual
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 * @throws SQLException 
	 */
	public void registrarConteoMayorPorPlantilla(int numSkus, int offSet, boolean conteoPrevio, boolean debeGuardar, boolean screenShot) throws SQLException {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		ResultSet rs = obtieneSkusPorPlantillaBD(numSkus, offSet);
		ingresarConteoMayor(rs, btnCountProdSave, conteoPrevio, debeGuardar, screenShot);
	}

	/**
	 * Devuelve el valor entero del turno seleccionado para una plantilla: 1=Mañana, 2=Tarde, 3=Noche
	 * @author Marcos Morales
	 */
	public int obtieneTurnoSeleccionado() {

		int idTurno = 0;
		WebElement parentContainer = au.driver.findElement(groupRadios);
		List<WebElement> radioButtons = parentContainer.findElements(radioBtns);

		for (int i = 0; i < radioButtons.size(); i++) {
			WebElement turno = radioButtons.get(i);
			boolean isChecked = Boolean.parseBoolean(turno.getAttribute("checked"));
			if (isChecked) {
				idTurno = i+1;
				break;
			}
		}
		return idTurno;
	}

	/**
	 * Valida en BD la edicion de una plantilla
	 * @author Marcos Morales
	 * @throws AssertionError 
	 * @throws SQLException 
	 */
	public void validarEdicionPlantilla() throws SQLException, AssertionError {

		String nomPlan = detailTemplate.getPlantilla();
		ResultSet rsOrg = registrosPlantillaOrgBD(nomPlan);

		if (rsOrg.next()) {
			int idPlan = rsOrg.getInt(1);
			String plantillaOrg = rsOrg.getString(2);
			String colabOrg = rsOrg.getString(7);
			int idTurnoOrg = rsOrg.getInt(8);
			System.out.println("Original BD: " + plantillaOrg + ", " + colabOrg + ", " + idTurnoOrg);

			ResultSet rsEdi = registrosPlantillaEditadaBD(idPlan);

			if (rsEdi.next()) {
				String plantillaEdi = rsEdi.getString(2);
				String colabEdi = rsEdi.getString(7);
				int idTurnoEdi = rsEdi.getInt(8);
				System.out.println("Editado BD: " + plantillaEdi + ", " + colabEdi + ", " + idTurnoEdi);
			} else {
				throw new AssertionError("No se encontraron registros editados para el Id: " + idPlan);
			}
		} else {
			throw new AssertionError("No se encontraron registros para: " + nomPlan);
		}
	}

	/**
	 * Devuelve el resultSet con la última plantilla creada
	 * @author Marcos Morales
	 */
	public ResultSet registrosUltimaPlantillaBD() {
		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			String query = "select distinct top 1 th.TM_CRT, th.TEMPLATE_NAME, th.TURN_ID\r\n"
					+ "from hc.TEMPLATE_SELF_GENERATED_COUNTS_HEADER th\r\n"
					+ "inner join hc.TEMPLATE_SELF_GENERATED_COUNTS_DETAIL td on th.ID = td.TEMPLATE_HEADER_ID\r\n"
					+ "and th.IS_ACTIVE = 1\r\n"
					+ "and TURN_ID = 3\r\n"
					+ "order by th.TM_CRT desc";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Devuelve el resultSet con los registros de una plantilla
	 * @author Marcos Morales
	 * @param nomPlan Nombre de la plantilla
	 */
	public ResultSet registrosPlantillaBD(String nomPlan) {
		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			String query = ";with cte as (\r\n"
					+ "select	p.PERSON_Id [ID]\r\n"
					+ "		,UPPER(TRIM(P.[NAME]) + ' ' + TRIM(P.LASTNAME_PAT) + ' ' + TRIM(P.LASTNAME_MAT)) [OPERATOR]\r\n"
					+ "from XposStore.dbo.PERSON as P\r\n"
					+ "inner join XposStore.dbo.OPERATOR as O ON (O.PERSON_ID = P.PERSON_ID AND O.IS_ACTIVE=1)\r\n"
					+ "where P.IS_ACTIVE = 1 and upper(P.LASTNAME_PAT) <> 'OXXO' and UPPER(P.LASTNAME_PAT) <> 'PROTECCION'\r\n"
					+ ")\r\n"
					+ "select	 th.ID\r\n"
					+ "   		,th.TEMPLATE_NAME\r\n"
					+ "   		,th.ORIGINAL_TEMPLATE_ID\r\n"
					+ "   		,th.PREVIOUS_TEMPLATE_ID\r\n"
					+ "   		,td.ITEM_NAME\r\n"
					+ "   		,trim(td.SKU_REF) [SKU]\r\n"
					+ "   		--,sd.COLLABORATOR_NAME\r\n"
					+ "		,cte.OPERATOR\r\n"
					+ "   		,th.TURN_ID\r\n"
					+ "from hc.TEMPLATE_SELF_GENERATED_COUNTS_HEADER th\r\n"
					+ "inner join hc.TEMPLATE_SELF_GENERATED_COUNTS_DETAIL td on th.ID = td.TEMPLATE_HEADER_ID\r\n"
					+ "inner join cte on cte.ID = th.COLABORATOR_ID\r\n"
					+ "where th.TEMPLATE_NAME = '" + nomPlan + "'\r\n"
					+ "and th.IS_ACTIVE = 1\r\n"
					+ "order by th.TM_CRT desc";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Devuelve el resultSet con los registros de la plantilla original
	 * @author Marcos Morales
	 * @param nomPlan Nombre de la plantilla
	 */
	public ResultSet registrosPlantillaOrgBD(String nomPlan) {
		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			String query = ";with cte as (\r\n"
					+ "		select	p.PERSON_Id [ID]\r\n"
					+ "				,UPPER(TRIM(P.[NAME]) + ' ' + TRIM(P.LASTNAME_PAT) + ' ' + TRIM(P.LASTNAME_MAT)) [OPERATOR]\r\n"
					+ "		from XposStore.dbo.PERSON as P\r\n"
					+ "		inner join XposStore.dbo.OPERATOR as O ON (O.PERSON_ID = P.PERSON_ID AND O.IS_ACTIVE=1)\r\n"
					+ "		where P.IS_ACTIVE = 1 and upper(P.LASTNAME_PAT) <> 'OXXO' and UPPER(P.LASTNAME_PAT) <> 'PROTECCION'\r\n"
					+ ")\r\n"
					+ "select	 th.ID\r\n"
					+ "		,th.TEMPLATE_NAME\r\n"
					+ "		,th.ORIGINAL_TEMPLATE_ID\r\n"
					+ "		,th.PREVIOUS_TEMPLATE_ID\r\n"
					+ "		,td.ITEM_NAME\r\n"
					+ "		,trim(td.SKU_REF) [SKU]\r\n"
					+ "		,cte.OPERATOR\r\n"
					+ "		,th.TURN_ID\r\n"
					+ "from hc.TEMPLATE_SELF_GENERATED_COUNTS_HEADER th\r\n"
					+ "inner join hc.TEMPLATE_SELF_GENERATED_COUNTS_DETAIL td on th.ID = td.TEMPLATE_HEADER_ID\r\n"
					+ "left join cte on cte.ID = th.COLABORATOR_ID\r\n"
					+ "where th.TEMPLATE_NAME = '" + nomPlan + "'\r\n"
					+ "and th.IS_ACTIVE = 0\r\n"
					+ "order by th.TM_CRT desc";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Devuelve un numero mayor a cero cuando se crea exitosamente una plantilla en BD
	 * @author Marcos Morales
	 * @param nomPlan Nombre de la plantilla
	 */
	public int retornaCreacionDePlantillaBD(String nomPlan) {
		int count = 0;
		ResultSet rs = registrosPlantillaBD(nomPlan);
		try {
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					count++;
				}
			} else {
				throw new AssertionError("La consulta ejecutada del metodo registrosPlantillaBD no devolvio resultados");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * Genera un String con el resultado de la consulta a BD
	 * @author Marcos Morales
	 * @param nomPlantilla Nombre de la plantilla reseteada
	 */
	public String generaStringCreacionPlantillaBD(String nomPlantilla) throws SQLException, AssertionError {
		String resultado = "";
		ResultSet rs = registrosPlantillaBD(nomPlantilla);
		if (rs.isBeforeFirst()) {
			while (rs.next()) {
				String col2 = rs.getString(2);
				String col5 = rs.getString(5);
				String col6 = rs.getString(6);
				String col7 = rs.getString(7);
				String col8 = rs.getString(8);
				resultado += col2 + "|" + col5 + "|" + col6 + "|" + col7 + "|" + col8 + "\n";
			}
			//System.out.println(resultado);
			return resultado;
		} else {
			throw new AssertionError("La consulta ejecutada del metodo obtieneRegistrosPlantillaReseteadaBD no devolvio resultados");
		}
	}

	/**
	 * Devuelve el resultSet con los registros de la plantilla editada
	 * @author Marcos Morales
	 * @param idPlantilla Id de la plantilla original
	 */
	public ResultSet registrosPlantillaEditadaBD(int idPlantilla) {
		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			String query = ";with cte as (\r\n"
					+ "		select	p.PERSON_Id [ID]\r\n"
					+ "				,UPPER(TRIM(P.[NAME]) + ' ' + TRIM(P.LASTNAME_PAT) + ' ' + TRIM(P.LASTNAME_MAT)) [OPERATOR]\r\n"
					+ "		from XposStore.dbo.PERSON as P\r\n"
					+ "		inner join XposStore.dbo.OPERATOR as O ON (O.PERSON_ID = P.PERSON_ID AND O.IS_ACTIVE=1)\r\n"
					+ "		where P.IS_ACTIVE = 1 and upper(P.LASTNAME_PAT) <> 'OXXO' and UPPER(P.LASTNAME_PAT) <> 'PROTECCION'\r\n"
					+ ")\r\n"
					+ "select	 th.ID\r\n"
					+ "		,th.TEMPLATE_NAME\r\n"
					+ "		,th.ORIGINAL_TEMPLATE_ID\r\n"
					+ "		,th.PREVIOUS_TEMPLATE_ID\r\n"
					+ "		,td.ITEM_NAME\r\n"
					+ "		,td.SKU_REF\r\n"
					+ "		,cte.OPERATOR\r\n"
					+ "		,th.TURN_ID\r\n"
					+ "from hc.TEMPLATE_SELF_GENERATED_COUNTS_HEADER th\r\n"
					+ "inner join hc.TEMPLATE_SELF_GENERATED_COUNTS_DETAIL td on th.ID = td.TEMPLATE_HEADER_ID\r\n"
					+ "left join cte on cte.ID = th.COLABORATOR_ID\r\n"
					+ "where th.PREVIOUS_TEMPLATE_ID = " + idPlantilla + "\r\n"
					+ "and th.IS_ACTIVE = 1";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Devuelve el resultSet con el numero de registros que coinciden con la busqueda
	 * @author Marcos Morales
	 * @param busqueda Valor a buscar en el filtrado
	 * @param tipoBusqueda Tipo de filtrado a realizar: plantilla, colaborador
	 */
	public ResultSet obtieneCantidadPlantillasBD(String busqueda, String tipoBusqueda) {
		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();
			switch (tipoBusqueda.toLowerCase()) {
			case "plantilla":
				String queryPlan = ";with cte as (\r\n"
						+ "select	p.PERSON_Id [ID]\r\n"
						+ "		,UPPER(TRIM(P.[NAME]) + ' ' + TRIM(P.LASTNAME_PAT) + ' ' + TRIM(P.LASTNAME_MAT)) [OPERATOR]\r\n"
						+ "from XposStore.dbo.PERSON as P\r\n"
						+ "inner join XposStore.dbo.OPERATOR as O ON (O.PERSON_ID = P.PERSON_ID AND O.IS_ACTIVE=1)\r\n"
						+ "where P.IS_ACTIVE = 1 and upper(P.LASTNAME_PAT) <> 'OXXO' and UPPER(P.LASTNAME_PAT) <> 'PROTECCION'\r\n"
						+ ")\r\n"
						+ "select count(distinct(th.TEMPLATE_NAME))\r\n"
						+ "from hc.TEMPLATE_SELF_GENERATED_COUNTS_HEADER th\r\n"
						+ "inner join hc.TEMPLATE_SELF_GENERATED_COUNTS_DETAIL td on th.ID = td.TEMPLATE_HEADER_ID\r\n"
						+ "left join cte on cte.ID = th.COLABORATOR_ID\r\n"
						+ "where th.TEMPLATE_NAME like '%" + busqueda + "%'\r\n"
						+ "and th.IS_ACTIVE = 1";

				resultSet = statement.executeQuery(queryPlan);
				break;
			case "colaborador":
				String queryColab = ";with cte as (\r\n"
						+ "select	p.PERSON_Id [ID]\r\n"
						+ "		,UPPER(TRIM(P.[NAME]) + ' ' + TRIM(P.LASTNAME_PAT) + ' ' + TRIM(P.LASTNAME_MAT)) [OPERATOR]\r\n"
						+ "from XposStore.dbo.PERSON as P\r\n"
						+ "inner join XposStore.dbo.OPERATOR as O ON (O.PERSON_ID = P.PERSON_ID AND O.IS_ACTIVE=1)\r\n"
						+ "where P.IS_ACTIVE = 1 and upper(P.LASTNAME_PAT) <> 'OXXO' and UPPER(P.LASTNAME_PAT) <> 'PROTECCION'\r\n"
						+ ")\r\n"
						+ "select count(distinct(th.TEMPLATE_NAME))\r\n"
						+ "from hc.TEMPLATE_SELF_GENERATED_COUNTS_HEADER th\r\n"
						+ "inner join hc.TEMPLATE_SELF_GENERATED_COUNTS_DETAIL td on th.ID = td.TEMPLATE_HEADER_ID\r\n"
						+ "left join cte on cte.ID = th.COLABORATOR_ID\r\n"
						+ "where OPERATOR like '%" + busqueda + "%'\r\n"
						+ "and th.IS_ACTIVE = 1";

				resultSet = statement.executeQuery(queryColab);
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Devuelve el resultSet con el numero de registros que coinciden con la busqueda
	 * @author Marcos Morales
	 * @param tipoBusqueda Define el tipo de filtrado a realizar: mañana, tarde, noche
	 */
	public ResultSet obtieneCantidadPlantillasBD(String tipoBusqueda) {
		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();
			switch (tipoBusqueda.toLowerCase()) {
			case "mañana":
				String queryM = ";with cte as (\r\n"
						+ "select	p.PERSON_Id [ID]\r\n"
						+ "		,UPPER(TRIM(P.[NAME]) + ' ' + TRIM(P.LASTNAME_PAT) + ' ' + TRIM(P.LASTNAME_MAT)) [OPERATOR]\r\n"
						+ "from XposStore.dbo.PERSON as P\r\n"
						+ "inner join XposStore.dbo.OPERATOR as O ON (O.PERSON_ID = P.PERSON_ID AND O.IS_ACTIVE=1)\r\n"
						+ "where P.IS_ACTIVE = 1 and upper(P.LASTNAME_PAT) <> 'OXXO' and UPPER(P.LASTNAME_PAT) <> 'PROTECCION'\r\n"
						+ ")\r\n"
						+ "select count(distinct(th.TEMPLATE_NAME))\r\n"
						+ "from hc.TEMPLATE_SELF_GENERATED_COUNTS_HEADER th\r\n"
						+ "inner join hc.TEMPLATE_SELF_GENERATED_COUNTS_DETAIL td on th.ID = td.TEMPLATE_HEADER_ID\r\n"
						+ "left join cte on cte.ID = th.COLABORATOR_ID\r\n"
						+ "where TURN_ID = 1\r\n"
						+ "and th.IS_ACTIVE = 1";

				resultSet = statement.executeQuery(queryM);
				break;
			case "tarde":
				String queryT = ";with cte as (\r\n"
						+ "select	p.PERSON_Id [ID]\r\n"
						+ "		,UPPER(TRIM(P.[NAME]) + ' ' + TRIM(P.LASTNAME_PAT) + ' ' + TRIM(P.LASTNAME_MAT)) [OPERATOR]\r\n"
						+ "from XposStore.dbo.PERSON as P\r\n"
						+ "inner join XposStore.dbo.OPERATOR as O ON (O.PERSON_ID = P.PERSON_ID AND O.IS_ACTIVE=1)\r\n"
						+ "where P.IS_ACTIVE = 1 and upper(P.LASTNAME_PAT) <> 'OXXO' and UPPER(P.LASTNAME_PAT) <> 'PROTECCION'\r\n"
						+ ")\r\n"
						+ "select count(distinct(th.TEMPLATE_NAME))\r\n"
						+ "from hc.TEMPLATE_SELF_GENERATED_COUNTS_HEADER th\r\n"
						+ "inner join hc.TEMPLATE_SELF_GENERATED_COUNTS_DETAIL td on th.ID = td.TEMPLATE_HEADER_ID\r\n"
						+ "left join cte on cte.ID = th.COLABORATOR_ID\r\n"
						+ "where TURN_ID = 2\r\n"
						+ "and th.IS_ACTIVE = 1";

				resultSet = statement.executeQuery(queryT);
				break;
			case "noche":
				String queryN = ";with cte as (\r\n"
						+ "select	p.PERSON_Id [ID]\r\n"
						+ "		,UPPER(TRIM(P.[NAME]) + ' ' + TRIM(P.LASTNAME_PAT) + ' ' + TRIM(P.LASTNAME_MAT)) [OPERATOR]\r\n"
						+ "from XposStore.dbo.PERSON as P\r\n"
						+ "inner join XposStore.dbo.OPERATOR as O ON (O.PERSON_ID = P.PERSON_ID AND O.IS_ACTIVE=1)\r\n"
						+ "where P.IS_ACTIVE = 1 and upper(P.LASTNAME_PAT) <> 'OXXO' and UPPER(P.LASTNAME_PAT) <> 'PROTECCION'\r\n"
						+ ")\r\n"
						+ "select count(distinct(th.TEMPLATE_NAME))\r\n"
						+ "from hc.TEMPLATE_SELF_GENERATED_COUNTS_HEADER th\r\n"
						+ "inner join hc.TEMPLATE_SELF_GENERATED_COUNTS_DETAIL td on th.ID = td.TEMPLATE_HEADER_ID\r\n"
						+ "left join cte on cte.ID = th.COLABORATOR_ID\r\n"
						+ "where TURN_ID = 3\r\n"
						+ "and th.IS_ACTIVE = 1";

				resultSet = statement.executeQuery(queryN);
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Busca un plantilla desde la caja de texto de busqueda
	 * @param busqueda Valor a buscar en el filtrado
	 * @author Marcos Morales
	 */
	public void buscarPlantilla(String busqueda) {
		waitForVisibility(txtBusqueda, Duration.ofSeconds(30));
		au.sendKeys(txtBusqueda, busqueda);
		au.click(txtBusqueda);
		((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
	}

	/**
	 * Busca un plantilla desde la opcion Filtro por
	 * @author Marcos Morales
	 */
	public void filtroPor() {
		waitForVisibility(filtroPor, Duration.ofSeconds(30));
		au.click(filtroPor);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Coordenadas para hacer clic (ajustar de acuerdo al dispositivo)
		int x = 620;
		int y = 895;

		// Realiza el clic en las coordenadas especificadas
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		Sequence tap = new Sequence(finger, 1);
		tap.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), x, y));
		tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		tap.addAction(new Pause(finger, Duration.ofMillis(200)));
		tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

		au.driver.perform(Arrays.asList(tap));
	}

	/**
	 * Busca un plantilla desde la opcion Filtro por Persona
	 * @author Marcos Morales
	 * @param idxColab Indice del colaborador a seleccionar
	 */
	public void filtroPorPersona(int idxColab, boolean screenShot) {

		By locator = By.xpath("(//android.widget.LinearLayout[@resource-id=\"android:id/content\"])[" + idxColab + "]");
		waitForVisibility(filtroPor, Duration.ofSeconds(30));

		baseExecution.addStep("Selecciona la opcion Filtrar por:");
		takeScreenshot(screenShot);
		au.click(filtroPor);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int x = 394;
		int y = 467;

		// Realiza el clic en las coordenadas especificadas
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		Sequence tap = new Sequence(finger, 1);
		tap.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), x, y));
		tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		tap.addAction(new Pause(finger, Duration.ofMillis(200)));
		tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

		baseExecution.addStep("Tap en la opcion Persona");
		takeScreenshot(screenShot);
		au.driver.perform(Arrays.asList(tap));

		waitForVisibility(locator, Duration.ofSeconds(30));
		baseExecution.addStep("Seleccionar persona");
		takeScreenshot(screenShot);
		au.click(locator);
	}

	/**
	 * Click en opcion Persona
	 * @author Marcos Morales
	 */
	public void clickOpcionPersona() {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int x = 394;
		int y = 467;

		// Realiza el clic en las coordenadas especificadas
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		Sequence tap = new Sequence(finger, 1);
		tap.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), x, y));
		tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		tap.addAction(new Pause(finger, Duration.ofMillis(200)));
		tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

		au.driver.perform(Arrays.asList(tap));
	}

	/**
	 * Seleccionar Persona
	 * @author Marcos Morales
	 * @param idxColab Indice de la persona a seleccionar
	 */
	public void clickPersona(int idxColab) {
		By locator = By.xpath("(//android.widget.LinearLayout[@resource-id=\"android:id/content\"])[" + idxColab + "]");
		waitForVisibility(locator, Duration.ofSeconds(30));
		au.click(locator);
	}

	/**
	 * Busca un plantilla desde la opcion Filtro por Turno
	 * @author Marcos Morales
	 * @param turno Mañana, Tarde, Noche
	 * @param screenShot Toma captura de pantalla: true/false
	 */
	public void filtroPorTurno(String turno, boolean screenShot) {

		String locator = "//android.widget.TextView[@resource-id=\"android:id/title\" and @text=\"Turno " + turno + "\"]";
		waitForVisibility(filtroPor, Duration.ofSeconds(30));

		baseExecution.addStep("Selecciona la opcion Filtrar por:");
		takeScreenshot(screenShot);
		au.click(filtroPor);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int x = 384;
		int y = 595;

		// Realiza el clic en las coordenadas especificadas
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		Sequence tap = new Sequence(finger, 1);
		tap.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), x, y));
		tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		tap.addAction(new Pause(finger, Duration.ofMillis(200)));
		tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

		baseExecution.addStep("Tap en la opcion Turno");
		takeScreenshot(screenShot);
		au.driver.perform(Arrays.asList(tap));

		waitForVisibility(By.xpath(locator), Duration.ofSeconds(30));
		baseExecution.addStep("Seleccionar turno: " + turno);
		takeScreenshot(screenShot);
		au.click(By.xpath(locator));
	}

	/**
	 * Click en opcion Turno
	 * @author Marcos Morales
	 */
	public void clickOpcionTurno() {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int x = 384;
		int y = 595;

		// Realiza el clic en las coordenadas especificadas
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		Sequence tap = new Sequence(finger, 1);
		tap.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), x, y));
		tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		tap.addAction(new Pause(finger, Duration.ofMillis(200)));
		tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

		au.driver.perform(Arrays.asList(tap));
	}

	/**
	 * Seleccionar Turno
	 * @author Marcos Morales
	 * @param turno Turno a seleccionar
	 */
	public void clickTurno(String turno) {
		String locator = "//android.widget.TextView[@resource-id=\"android:id/title\" and @text=\"Turno " + turno + "\"]";
		waitForVisibility(By.xpath(locator), Duration.ofSeconds(30));
		au.click(By.xpath(locator));
	}

	/**
	 * Devuelve el listado de plantillas
	 * @author Marcos Morales
	 */
	public List<WebElement> listaPlantillas() {
		List<WebElement> lstPlantillas = au.driver.findElements(plantillas);
		return lstPlantillas;
	}

	/**
	 * Devuelve el resultSet con el numero de registros de plantillas en BD
	 * @author Marcos Morales
	 */
	public ResultSet cantidadPlantillasBD() {
		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			String query = ";with cte as (\r\n"
					+ "select	p.PERSON_Id [ID]\r\n"
					+ "		,UPPER(TRIM(P.[NAME]) + ' ' + TRIM(P.LASTNAME_PAT) + ' ' + TRIM(P.LASTNAME_MAT)) [OPERATOR]\r\n"
					+ "from XposStore.dbo.PERSON as P\r\n"
					+ "inner join XposStore.dbo.OPERATOR as O ON (O.PERSON_ID = P.PERSON_ID AND O.IS_ACTIVE=1)\r\n"
					+ "where P.IS_ACTIVE = 1 and upper(P.LASTNAME_PAT) <> 'OXXO' and UPPER(P.LASTNAME_PAT) <> 'PROTECCION'\r\n"
					+ ")\r\n"
					+ "select count(distinct(th.TEMPLATE_NAME))\r\n"
					+ "from hc.TEMPLATE_SELF_GENERATED_COUNTS_HEADER th\r\n"
					+ "inner join hc.TEMPLATE_SELF_GENERATED_COUNTS_DETAIL td on th.ID = td.TEMPLATE_HEADER_ID\r\n"
					+ "left join cte on cte.ID = th.COLABORATOR_ID\r\n"
					+ "where th.IS_ACTIVE = 1\r\n";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Devuelve la cantidad de Plantillas en BD
	 * @author Marcos Morales
	 */
	public int retornaCantidadPlantillasBD() throws SQLException, AssertionError {
		ResultSet rs = cantidadPlantillasBD();
		int rows = 0;
		if (rs.next()) {
			rows = rs.getInt(1);
		} else {
			throw new AssertionError("No se encontraron registros al ejecutar la consulta: cantidadPlantillasBD");
		}
		return rows;
	}

	/**
	 * Devuelve el resultSet con las plantillas por turno
	 * @author Marcos Morales
	 * @throws AssertionError 
	 * @throws SQLException 
	 */
	public ResultSet plantillasPorTurnoBD() throws SQLException, AssertionError {
		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			String query = "select	 distinct th.TURN_ID\r\n"
					+ "		,case when th.TURN_ID = 1 then 'TURNO MAÑANA'\r\n"
					+ "			  when th.TURN_ID = 2 then 'TURNO TARDE'\r\n"
					+ "			  when th.TURN_ID = 3 then 'TURNO NOCHE'\r\n"
					+ "		 end [TURNOS]\r\n"
					+ "		,th.TEMPLATE_NAME\r\n"
					+ "from hc.TEMPLATE_SELF_GENERATED_COUNTS_HEADER th\r\n"
					+ "inner join hc.TEMPLATE_SELF_GENERATED_COUNTS_DETAIL td on th.ID = td.TEMPLATE_HEADER_ID\r\n"
					+ "order by th.TURN_ID";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (resultSet.isBeforeFirst()) {
			return resultSet;
		} else {
			throw new AssertionError("La consulta ejecutada en el metodo (plantillasPorTurnoBD) no devolvio resultados");
		}
	}

	/**
	 * Guarda en un ArrayList la consulta que obtiene las plantillas por turno
	 * @author Marcos Morales
	 * @throws AssertionError 
	 * @throws SQLException 
	 */
	public List<Map<String, Object>> obtienePlantillasPorTurnoBD() throws SQLException, AssertionError {
		List<Map<String, Object>> plantillasBD = new ArrayList<>();
		ResultSet rs = plantillasPorTurnoBD();

		try {
			while (rs.next()) {
				Map<String, Object> fila = new HashMap<>();
				fila.put("turno", rs.getString(2));
				fila.put("plantilla", rs.getString(3));
				plantillasBD.add(fila);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return plantillasBD;
	}

	/**
	 * Identifica si el objeto es localizable
	 * @author Marcos Morales
	 * @param locator Identificador del elemento
	 */
	public boolean isElementPresent(List<WebElement> childElements, int index, By locator) {
		try {
			childElements.get(index).findElement(locator);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Devuelve el listado de plantillas por Turno
	 * @author Marcos Morales
	 */
	public List<WebElement> listaPlantillasPorTurno() {
		List<WebElement> lstPlantillasTurno = au.driver.findElements(allTemplates);
		return lstPlantillasTurno;
	}

	/**
	 * Valida el estado para el boton + 
	 * @author Marcos Morales
	 */
	public boolean[] retornaEstadoBotonPlus() {
		boolean[] status = new boolean[1];
		try {
			WebElement edoBtnPlus = au.driver.findElement(btnPlusPlantilla);
			status[0] = Boolean.parseBoolean(edoBtnPlus.getAttribute("enabled"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * Devuelve el valor de cada etiqueta de turnos: Mañana, Tarde, Noche
	 * @author Marcos Morales
	 */
	public String[] retornaEtiquetasTurnos() {
		String[] turnos = new String[3];
		try {
			turnos[0] = au.driver.findElement(lblMorning).getText();
			turnos[1] = au.driver.findElement(lblAfternoon).getText();
			turnos[2] = au.driver.findElement(lblNight).getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return turnos;
	}

	/**
	 * Devuelve si un turno esta seleccionado o no
	 * @author Marcos Morales
	 */
	public boolean[] retornaTurnoSeleccionado(String idTurno) {
		boolean[] status = new boolean[1];
		try {
			WebElement turno = au.driver.findElement(By.id("com.femco.oxxo.operaciones.mitienda.debug:id/turn_" + idTurno));
			status[0] = Boolean.parseBoolean(turno.getAttribute("checked"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * Devuelve si el boton Continuar se encuentra habilitado
	 * @author Marcos Morales
	 */
	public boolean[] retornaBtnContinuarHabilitado() {
		boolean[] status = new boolean[1];
		try {
			WebElement edoBtnContinuar = au.driver.findElement(btnContinuar);
			status[0] = Boolean.parseBoolean(edoBtnContinuar.getAttribute("enabled"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * Devuelve el resultSet con los Colaboradores activos en tienda
	 * @author Marcos Morales
	 */
	public ResultSet colaboradoresActivosBD() {
		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			String query = "select UPPER(TRIM(P.[NAME]) + ' ' + TRIM(P.LASTNAME_PAT) + ' ' + TRIM(P.LASTNAME_MAT)) [Colaborador]\r\n"
					+ "from XposStore.dbo.PERSON as P\r\n"
					+ "inner join XposStore.dbo.OPERATOR as O ON (O.PERSON_ID = P.PERSON_ID AND O.IS_ACTIVE=1)\r\n"
					+ "where P.IS_ACTIVE = 1 and upper(P.LASTNAME_PAT) <> 'OXXO' and UPPER(P.LASTNAME_PAT) <> 'PROTECCION'\r\n"
					+ "order by Colaborador";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Devuelve el listado de colaboradores mostrados en el combo
	 * @author Marcos Morales
	 */
	public List<WebElement> listaColaboradores() {
		List<WebElement> lstColaboradores = au.driver.findElements(colaborador);
		return lstColaboradores;
	}

	/**
	 * Seleccionar Colaborador y Turno
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 * @throws AssertionError 
	 * @throws SQLException 
	 */
	public void validaColaboradores(boolean screenShot) throws SQLException, AssertionError {
		String[] turnos = {"morning", "afternoon", "night"};

		clickBtnPlus(screenShot);
		clickComboColaboradores(screenShot);

		seleccionarColaborador(0, screenShot);

		seleccionarTurno(turnos[0], screenShot);

		clickBtnContinuar(screenShot);
	}

	/**
	 * Metodo que identifica si existen registros en BD
	 * de una semana anterior para ejecutar CP de Heredacion de Plantillas
	 * @author Marcos Morales
	 */
	public void validaSemanaAnterior() {

		ResultSet resultSet = null;
		try {

			Connection connection = DatabaseUtil.obtenerConexion();
			Statement statement = connection.createStatement();

			String query = "SELECT COUNT(*) [Rows] FROM [HC].[II_HEADER]\r\n"
					+ "WHERE II_WEEKNUMBER = DATEPART(ISO_WEEK, GETDATE())-1;";

			resultSet = statement.executeQuery(query);

			if (resultSet.next()) {
				int records = resultSet.getInt(1);
				if (records == 0) {
					insertaSemanaAnterior();
				}
			} else {
				throw new AssertionError("No se encontraron registros al ejecutar la consulta");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Inserta en BD los registros requeridos para
	 * ejecutar CP de Heredación de Plantillas
	 * @author Marcos Morales
	 */
	public void insertaSemanaAnterior() {

		try {
			Connection connection = DatabaseUtil.obtenerConexion();
			Statement statement = connection.createStatement();

			String query = "BEGIN TRY\r\n"
					+ "	SET ANSI_NULLS ON;\r\n"
					+ "	SET QUOTED_IDENTIFIER ON;\r\n"
					+ "	SET NOCOUNT ON;\r\n"
					+ "	BEGIN TRANSACTION;\r\n"
					+ "\r\n"
					+ "	INSERT INTO [HC].[II_HEADER]\r\n"
					+ "	SELECT	 -2 [II_FOLIO]\r\n"
					+ "			,CONVERT(VARCHAR(23), CAST(DATEADD(DAY, -(DATEPART(WEEKDAY, GETDATE()) - 2) - 7, CAST(GETDATE() AS DATE)) AS DATETIME2(3)), 121) [II_STARTDATE]\r\n"
					+ "			,CONVERT(VARCHAR(23), CAST(DATEADD(DAY, 1 - DATEPART(WEEKDAY, GETDATE()), CAST(GETDATE() AS DATE)) AS DATETIME2(3)), 121) [II_ENDDATE]\r\n"
					+ "			,2 [II_STATUS]\r\n"
					+ "			,'Semana ' + CAST(DATEPART(ISO_WEEK, GETDATE()) - 1 AS VARCHAR(4)) [II_DESCRIPTION]\r\n"
					+ "			,DATEPART(ISO_WEEK, GETDATE())-1 [II_WEEKNUMBER]\r\n"
					+ "			,1 [II_USER]\r\n"
					+ "			,'' [II_CLASSIFICATION]\r\n"
					+ "			,'I' [II_TYPE]\r\n"
					+ "			,1 [II_ROL]\r\n"
					+ "			,1 [FL]\r\n"
					+ "			,1 [LCNT]\r\n"
					+ "			,1 [ID_OPR_CRT]\r\n"
					+ "			,CONVERT(VARCHAR(23), CAST(DATEADD(DAY, -(DATEPART(WEEKDAY, GETDATE()) - 2) - 7, CAST(GETDATE() AS DATE)) AS DATETIME2(3)), 121) [TM_CRT]\r\n"
					+ "			,NULL [ID_OPR_MDY]\r\n"
					+ "			,NULL [TM_MDY]\r\n"
					+ "			,NULL [II_AUTOMATIC_ROLE_ASSIGNMENT_DATE]\r\n"
					+ "			,NULL [II_STORE_TEMPLATE_ID]\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "	INSERT INTO [HC].[II_HEADER]\r\n"
					+ "	SELECT	 -1 [II_FOLIO]\r\n"
					+ "			,CONVERT(VARCHAR(23), CAST(DATEADD(DAY, -(DATEPART(WEEKDAY, GETDATE()) - 2) - 7, CAST(GETDATE() AS DATE)) AS DATETIME2(3)), 121) [II_STARTDATE]\r\n"
					+ "			,CONVERT(VARCHAR(23), CAST(DATEADD(DAY, 1 - DATEPART(WEEKDAY, GETDATE()), CAST(GETDATE() AS DATE)) AS DATETIME2(3)), 121) [II_ENDDATE]\r\n"
					+ "			,2 [II_STATUS]\r\n"
					+ "			,'Semana ' + CAST(DATEPART(ISO_WEEK, GETDATE()) - 1 AS VARCHAR(4)) [II_DESCRIPTION]\r\n"
					+ "			,DATEPART(ISO_WEEK, GETDATE())-1 [II_WEEKNUMBER]\r\n"
					+ "			,1 [II_USER]\r\n"
					+ "			,'' [II_CLASSIFICATION]\r\n"
					+ "			,'P' [II_TYPE]\r\n"
					+ "			,1 [II_ROL]\r\n"
					+ "			,1 [FL]\r\n"
					+ "			,1 [LCNT]\r\n"
					+ "			,1 [ID_OPR_CRT]\r\n"
					+ "			,CONVERT(VARCHAR(23), CAST(DATEADD(DAY, -(DATEPART(WEEKDAY, GETDATE()) - 2) - 7, CAST(GETDATE() AS DATE)) AS DATETIME2(3)), 121) [TM_CRT]\r\n"
					+ "			,NULL [ID_OPR_MDY]\r\n"
					+ "			,NULL [TM_MDY]\r\n"
					+ "			,NULL [II_AUTOMATIC_ROLE_ASSIGNMENT_DATE]\r\n"
					+ "			,NULL [II_STORE_TEMPLATE_ID]\r\n"
					+ "\r\n"
					+ "	COMMIT TRANSACTION;\r\n"
					+ "	SET NOCOUNT OFF;\r\n"
					+ "	SET QUOTED_IDENTIFIER OFF;\r\n"
					+ "	SET ANSI_NULLS OFF;\r\n"
					+ "END TRY\r\n"
					+ "BEGIN CATCH\r\n"
					+ "	ROLLBACK TRANSACTION;\r\n"
					+ "	THROW;\r\n"
					+ "END CATCH;";

			statement.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Actualiza en BD los registros creados de plantillas
	 * para setearlos con fecha de una semana anterior y poder
	 * ejecutar CP de Heredacion de Plantillas
	 * @author Marcos Morales
	 */
	public void actualizaPlantillasSemanaAnterior() {

		try {
			Connection connection = DatabaseUtil.obtenerConexion();
			Statement statement = connection.createStatement();

			String query = "BEGIN TRY\r\n"
					+ "	SET ANSI_NULLS ON;\r\n"
					+ "	SET QUOTED_IDENTIFIER ON;\r\n"
					+ "	SET NOCOUNT ON;\r\n"
					+ "	BEGIN TRANSACTION;\r\n"
					+ "\r\n"
					+ "		declare @semanaActual int;\r\n"
					+ "		declare @semanaAnterior int;\r\n"
					+ "		set @semanaActual = DATEPART(ISO_WEEK, GETDATE());\r\n"
					+ "		set @semanaAnterior = @semanaActual-1;\r\n"
					+ "\r\n"
					+ "		UPDATE HC.IO_DETAIL\r\n"
					+ "		SET  IO_DISDATE = DATEADD(WEEK, -1, IO_DISDATE)\r\n"
					+ "			,CREATED_DATETIME = DATEADD(WEEK, -1, CREATED_DATETIME)\r\n"
					+ "			,MODIFIED_DATETIME = DATEADD(WEEK, -1, MODIFIED_DATETIME)\r\n"
					+ "		WHERE YEAR(CREATED_DATETIME) = YEAR(GETDATE())\r\n"
					+ "		AND DATEPART(ISO_WEEK, CREATED_DATETIME)  = DATEPART(ISO_WEEK, GETDATE());\r\n"
					+ "\r\n"
					+ "		UPDATE [HC].[II_HEADER_IO_HEADER]\r\n"
					+ "		SET	 II_FOLIO = (SELECT II_FOLIO FROM [HC].[II_HEADER] where II_WEEKNUMBER = @semanaAnterior and II_TYPE = 'P')\r\n"
					+ "			,TM_CRT = DATEADD(WEEK, -1, TM_CRT)\r\n"
					+ "		WHERE YEAR(TM_CRT) =YEAR(GETDATE())\r\n"
					+ "		AND DATEPART(ISO_WEEK, TM_CRT) = DATEPART(ISO_WEEK, GETDATE());\r\n"
					+ "\r\n"
					+ "		UPDATE HC.IO_HEADER\r\n"
					+ "		SET  IO_Create = DATEADD(WEEK, -1, IO_Create)\r\n"
					+ "			,IO_Date = DATEADD(WEEK, -1, IO_Date)\r\n"
					+ "			,CREATED_DATETIME = DATEADD(WEEK, -1, CREATED_DATETIME)\r\n"
					+ "			,MODIFIED_DATETIME = DATEADD(WEEK, -1, MODIFIED_DATETIME)\r\n"
					+ "		WHERE YEAR(CREATED_DATETIME) = YEAR(GETDATE())\r\n"
					+ "		AND DATEPART(ISO_WEEK, CREATED_DATETIME)  = DATEPART(ISO_WEEK, GETDATE());\r\n"
					+ "\r\n"
					+ "		UPDATE [HC].[TEMPLATE_SELF_GENERATED_COUNTS_DETAIL]\r\n"
					+ "		SET  TM_CRT = DATEADD(WEEK, -1, TM_CRT)\r\n"
					+ "			,TM_MDY = DATEADD(WEEK, -1, TM_MDY)\r\n"
					+ "		WHERE YEAR(TM_CRT) =YEAR(GETDATE())\r\n"
					+ "		AND DATEPART(ISO_WEEK, TM_CRT) = DATEPART(ISO_WEEK, GETDATE());\r\n"
					+ "\r\n"
					+ "		UPDATE [HC].[TEMPLATE_SELF_GENERATED_COUNTS_HEADER]\r\n"
					+ "		SET	 II_FOLIO = (SELECT II_FOLIO FROM [HC].[II_HEADER] where II_WEEKNUMBER = @semanaAnterior and II_TYPE = 'P')\r\n"
					+ "			,TM_CRT = DATEADD(WEEK, -1, TM_CRT)\r\n"
					+ "			,TM_MDY = DATEADD(WEEK, -1, TM_MDY)\r\n"
					+ "		WHERE YEAR(TM_CRT) =YEAR(GETDATE())\r\n"
					+ "		AND DATEPART(ISO_WEEK, TM_CRT) = DATEPART(ISO_WEEK, GETDATE());\r\n"
					+ "\r\n"
					+ "		UPDATE [HC].[II_HEADER]\r\n"
					+ "		SET II_CLASSIFICATION = 'P'\r\n"
					+ "		WHERE II_FOLIO = (SELECT II_FOLIO FROM [HC].[II_HEADER] where II_WEEKNUMBER = @semanaAnterior and II_TYPE = 'P')\r\n"
					+ "\r\n"
					+ "		UPDATE [HC].[II_HEADER]\r\n"
					+ "		SET II_STATUS = 2\r\n"
					+ "		WHERE II_WEEKNUMBER = @semanaAnterior\r\n"
					+ "\r\n"
					+ "		UPDATE [HC].[II_HEADER]\r\n"
					+ "		SET II_CLASSIFICATION = ''\r\n"
					+ "		WHERE II_WEEKNUMBER = @semanaActual\r\n"
					+ "\r\n"
					+ "	COMMIT TRANSACTION;\r\n"
					+ "	SET NOCOUNT OFF;\r\n"
					+ "	SET QUOTED_IDENTIFIER OFF;\r\n"
					+ "	SET ANSI_NULLS OFF;\r\n"
					+ "END TRY\r\n"
					+ "BEGIN CATCH\r\n"
					+ "	ROLLBACK TRANSACTION;\r\n"
					+ "	THROW;\r\n"
					+ "END CATCH;";

			statement.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
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
	 * Despliega la plantilla para mostrar los skus a contar
	 * @author Marcos Morales
	 */
	public void clickBtnPlusTemplate() {
		waitForVisibility(btnPlusTemplate, Duration.ofSeconds(30));
		au.click(btnPlusTemplate);
	}

	/**
	 * Muestra la ventana para captura del conteo del primer sku
	 * @author Marcos Morales
	 */
	public void clickFirstSKU() {
		au.click(primerProducto);
	}

	/**
	 * Muestra la ventana para captura del conteo del primer sku
	 * @author Marcos Morales
	 * @param idxProd Indice del producto al que se hace click
	 */
	public void clickProducto(int idxProd) {
		By prod = By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_product\"])[" + idxProd + "]");
		au.click(prod);
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
	 * Devuelve el teorico y fisico de un registro en especifico
	 * @author Marcos Morales
	 */
	public List<WebElement> getTeoricoFisico(int idxProd) {
		List<WebElement> productos = au.driver.findElements(products);
		WebElement productoN = productos.get(idxProd);
		List<WebElement> teoricoFisico = productoN.findElements(By.className("android.widget.TextView"));
		return teoricoFisico;
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
	 * @param direction Direccion a la cual ejecutar el scroll Up, Down, Left, Right
	 * @param percent Porcentaje de la pantalla que abarca el scroll
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
	 * Método que realiza el scroll hacia abajo en el listado de plantillas
	 * @author Marcos Morales
	 * @throws AssertionError 
	 * @throws SQLException 
	 */
	public void validaScrollPlantillas() throws SQLException, AssertionError {

		//identifica la última plantilla creada
		ResultSet rs = registrosUltimaPlantillaBD();

		if (rs.next()) {
			String plantilla = rs.getString(2);
			By lastTemplate = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_template_name\" and @text=\"" + plantilla + "\"]");
			scrollToElement(lastTemplate, objScrollable, "Down", 0.8);
		} else {
			throw new AssertionError("No se encontraron registros al ejecutar la consulta del metodo: registrosUltimaPlantillaBD");
		}
	}

	/**
	 * Método que realiza un scroll vertical al final de la pantalla
	 * @author Marcos Morales
	 */
	public void scrollToEnd() {
		au.driver.findElement(AppiumBy.androidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true))" +
				".scrollToEnd(10)"));
	}

	/**
	 * Valida el estado de las opciones Crear Nueva Plantilla
	 * e Iniciar conteo al ingresar a Conteos Propios
	 * @author Marcos Morales
	 */
	public boolean[] retornaEstadoBotonesNuevaPlantillaEIniciarConteo() {
		boolean[] status = new boolean[2];
		try {
			waitForVisibility(lblTitulo, Duration.ofSeconds(30));
			status[0] = au.driver.findElement(opcion1).isDisplayed();
			status[1] = au.driver.findElement(opcion2).isDisplayed();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * Devuelve los mensajes de tipo toast
	 * @author Lizbeth Hernandez
	 * @param mensaje Mensaje a validar
	 */
	public Boolean retornaMensajeToast(String mensaje) {
		boolean msgToast = isElementPresent(By.xpath("//android.widget.Toast[@text=\"" + mensaje + "\"]"));
		return msgToast;

	}

	/**
	 * Hacer click en el X para borrar una busqueda
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void clickBtnBorrarBusqueda(boolean screenShot) {
		waitForVisibility(btnBorrarBusqueda, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(btnBorrarBusqueda);
	}

	/**
	 * Hacer click en el X para borrar la opcion Filtrar por
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void clickBtnBorrarFiltro(boolean screenShot) {
		waitForVisibility(btnBorrarFiltro, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(btnBorrarFiltro);
	}

	/**
	 * Obtiene el valor del elemento +/- para el nombre de una Plantilla
	 * @author Marcos Morales
	 * @return txtBtnPlus
	 */
	public String obtieneTextoBtnPlus() {
		String txtBtnPlus = au.driver.findElement(btnPlusTemplate).getText();
		return txtBtnPlus;
	}

	/**
	 * Hacer click en el Nombre de una plantilla para mostrar sus SKUS
	 * @author Marcos Morales
	 */
	public void clickNombrePlantilla() {
		waitForVisibility(txtNomPlantilla, Duration.ofSeconds(30));
		au.click(txtNomPlantilla);
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
	 * Crea un objeto de tipo Map con todos los elementos identificados
	 * en el footer de la aplicacion
	 * @author Marcos Morales
	 */
	public Map<String, String> obtieneFaltantesSobrantesApp() throws SQLException, AssertionError {

		int i = 0;
		int j = 0;

		Map<String, String> footerValues = new HashMap<>();
		List<WebElement> locators = au.driver.findElements(By.xpath("//android.widget.LinearLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ll_footer_container\"]/android.view.ViewGroup"));

		for (i = 0; i < locators.size(); i++) {
			WebElement locator = locators.get(i);
			List<WebElement> textsContent = locator.findElements(By.className("android.widget.TextView"));

			for (j = 0; j < textsContent.size(); j++) {
				String textVal = textsContent.get(j).getText();
				footerValues.put("valor" + i + j, textVal);
			}
		}
		return footerValues;
	}

	/**
	 * Obtiene la cantidad de Skus mostrada en front (Plantilla) 
	 * @author Lizbeth Hernandez
	 * @return totalSkusAppStr Total de Skus por plantilla 
	 */
	public String obtienetotalSkusApp() throws SQLException, AssertionError {
		String totalSkusAppStr = au.driver.findElement(lblConteosAct).getText();
		return totalSkusAppStr;
	}

	/**
	 * Obtiene la cantidad de Skus por plantilla de BD 
	 * @author Lizbeth Hernandez
	 * @return totalSkusBD Total de Skus por plantilla de BD
	 */
	public int obtieneTotalSKUSenBDPlantilla() throws SQLException, AssertionError {
		ResultSet rs = obtieneCantidadSkusPorPlantillaBD();
		if (rs.next()) {
			int totalSkusBD = rs.getInt(1);
			return totalSkusBD;
		} else {
			throw new AssertionError("No se encontraron registros al ejecutar la consulta");
		}
	}

	/**
	 * Obtiene el valor del cronometro
	 * @author Marcos Morales
	 */
	public String getCronValueTxt() {
		String cronValueTxt = au.driver.findElement(cronValue).getText();
		return cronValueTxt;
	}

	/**
	 * Valida que esten visibles los botones Imprimir y Guardar
	 * @author Marcos Morales
	 */
	public boolean[] retornaEstadoBtnsImprimirGuardar() {
		boolean[] status = new boolean[2];
		try {
			status[0] = au.driver.findElement(btnImprimir).isDisplayed();
			status[1] = au.driver.findElement(btnGuardarPreInv).isDisplayed();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * Permite capturar n SKUS en el campo INGRESA EL SKU DE UN ARTICULO
	 * @author Marcos Morales
	 * @param numSKUS Cantidad de SKUS a ingresar
	 * @param screenShot Toma captura de pantalla: true/false
	 */
	public void capturarSKU(int numSKUS, boolean screenShot) throws SQLException, AssertionError {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		ResultSet rs = obtieneSkusPorPlantillaBD(numSKUS);

		if (rs.next()) {
			String sku = rs.getString(2);
			au.sendKeys(txtBarcode, sku);
			takeScreenshot(screenShot);
			baseExecution.addStep("Dar clic en el botón -->l para aceptar el ingreso de sku");
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			waitForVisibility(txtFisicos, Duration.ofSeconds(30));
			takeScreenshot(screenShot);
		} else {
			throw new AssertionError("No se encontraron registros al ejecutar la consulta");
		}
	}

	/**
	 * Permite capturar un SKU en especifico en el campo INGRESA EL SKU DE UN ARTICULO
	 * @author Marcos Morales
	 * @param sku SKU a capturar
	 * @param screenShot Toma captura de pantalla: true/false
	 */
	public void capturarSKU(String sku, boolean screenShot) throws SQLException, AssertionError {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));

		au.sendKeys(txtBarcode, sku);
		takeScreenshot(screenShot);
		baseExecution.addStep("Dar clic en el botón -->l para aceptar el ingreso de sku");
		((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
		waitForVisibility(txtFisicos, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
	}

	/**
	 * Permite capturar un SKU incorrecto en el campo INGRESA EL SKU DE UN ARTICULO
	 * @author Marcos Morales
	 * @param sku SKU erroneo a capturar
	 * @param screenShot Toma captura de pantalla: true/false
	 */
	public void capturarSKUIncorrecto(String sku, boolean screenShot) throws SQLException, AssertionError {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));

		au.sendKeys(txtBarcode, sku);
		takeScreenshot(screenShot);
		((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
		takeScreenshot(screenShot);
	}

	/**
	 * Valida el estado de los elementos al ingresar a la ventana para captura de conteos
	 * @author Marcos Morales
	 */
	public boolean[] retornaEstadoElementosCapturaConteo() {
		boolean[] status = new boolean[8];
		try {
			waitForVisibility(txtFisicos, Duration.ofSeconds(30));
			status[0] = au.driver.findElement(descriptionProd).isDisplayed();
			status[1] = au.driver.findElement(skuProd).isDisplayed();
			status[2] = au.driver.findElement(teoricoVal).isDisplayed();
			status[3] = au.driver.findElement(lblTeorico).isDisplayed();
			status[4] = au.driver.findElement(txtFisicos).isDisplayed();
			status[5] = au.driver.findElement(lblFisicos).isDisplayed();
			status[6] = au.driver.findElement(btnCountProdSave).isDisplayed();
			status[7] = au.driver.findElement(btnCountProdCancel).isDisplayed();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * Metodo para capturar el valor fisico (conteo) de un SKU
	 * @author Marcos Morales
	 * @param conteo Conteo a capturar
	 */
	public void capturarConteo(String conteo) {
		waitForVisibility(txtFisicos, Duration.ofSeconds(30));
		au.sendKeys(txtFisicos, conteo);
	}

	/**
	 * Metodo para capturar el valor fisico (conteo) de un SKU
	 * @author Marcos Morales
	 * @param conteo Conteo a capturar
	 */
	public void capturarConteo(int conteo) {
		String conteoStr = Integer.toString(conteo);
		waitForVisibility(txtFisicos, Duration.ofSeconds(30));
		au.sendKeys(txtFisicos, conteoStr);
	}

	/**
	 * Metodo para capturar el valor fisico (conteo) de un SKU
	 * @author Marcos Morales
	 * @param conteo Conteo a capturar
	 * @param conteoPrevio Bandera que identifica si el conteo es nuevo o no: true/false
	 * @param debeGuardar Bandera que define si guarda en memoria los datos de sku, teorico y fisico para el regitro actual
	 */
	public void capturarFisico(int conteo, boolean conteoPrevio, boolean debeGuardar) {
		String conteoStr = Integer.toString(conteo);
		waitForVisibility(txtFisicos, Duration.ofSeconds(30));
		au.sendKeys(txtFisicos, conteoStr);
		extraerValoresModalConteos(conteoPrevio, debeGuardar);
	}

	/**
	 * Metodo para capturar el valor fisico (conteo) de un SKU
	 * @author Marcos Morales
	 * @param action igual, menor, mayor Considerando el valor teorico
	 * @param conteoPrevio Bandera que identifica si el conteo es nuevo o no: true/false
	 * @param debeGuardar Bandera que define si guarda en memoria los datos de sku, teorico y fisico para el regitro actual
	 * @param screenShot Toma captura de pantalla: true/false
	 */
	public void capturarFisico(String action, boolean conteoPrevio, boolean debeGuardar, boolean screenShot) {
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
		extraerValoresModalConteos(conteoPrevio, debeGuardar);
	}

	/**
	 * Devuelve el valor de la etiqueta Faltante o Sobrante
	 * @author Marcos Morales
	 */
	public String retornaEtiquetaFaltantesSobrantes() {
		return au.driver.findElement(sobfalLabel).getText();
	}

	/**
	 * Devuelve true si la etiqueta de Faltante o Sobrante no es visible
	 * @author Marcos Morales
	 * @param skuEliminado Indice del SKU a eliminar
	 */
	public boolean etqFaltanteSobrantesNOVisible() {
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(5));
		return wait.until(ExpectedConditions.invisibilityOfElementLocated(sobfalLabel));
	}

	/**
	 * Obtiene del modal para captura del conteo los valores de SKU, Teorico y Fisico
	 * @author Marcos Morales
	 * @param conteoPrevio Bandera que identifica si el conteo es nuevo o no: true/false
	 * @param debeGuardar Bandera que define si guarda en memoria los datos de sku, teorico y fisico para el regitro actual
	 */
	public void extraerValoresModalConteos(boolean conteoPrevio, boolean debeGuardar) {
		int ctoActual = 0;
		if (conteoPrevio) {
			String ctoActualStr = au.driver.findElement(QtyCurrent).getText();
			ctoActual = (int) Double.parseDouble(ctoActualStr.replaceAll("[^\\d.]", ""));
		}
		String skuStr = au.driver.findElement(skuProd).getText();
		String teoricoStr = au.driver.findElement(teoricoVal).getText();
		String fisicoStr = au.driver.findElement(txtFisicos).getText();
		int fisicoInt = Integer.parseInt(fisicoStr);
		int fisico = ctoActual + fisicoInt;
		String conteoStr = Integer.toString(fisico);
		modalConteos = new ModalConteos(skuStr, teoricoStr, conteoStr, debeGuardar);
	}

	/**
	 * Espera a que se oculte el mensaje de Guardado General del Preinventario
	 * @author Marcos Morales
	 */
	public void esperaSeOcultaMensajeGuardado() {
		waitForHidden(lblMensaje, Duration.ofSeconds(30));
	}

	/**
	 * Espera a que se oculte el mensaje de Guardado General de la Plantilla
	 * @author Marcos Morales
	 */
	public void esperaSeOcultaMensajeGuardadoConteos() {
		waitForHidden(lblMsj, Duration.ofSeconds(30));
	}

	/**
	 * Espera a que se oculte el mensaje al eliminar un SKU
	 * @author Marcos Morales
	 */
	public void esperaSeOcultaMensajeEliminado() {
		waitForHidden(lblMensaje, Duration.ofSeconds(30));
	}

	/**
	 * Obtiene faltantes y sobrantes desde BD
	 * @author Marcos Morales
	 * @param tipo Inteligentes = 'I', Propios = 'P'
	 */
	public ResultSet obtieneFaltantesSobrantesBD(String tipo) {

		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			String query = "declare @Folio int = (select max(II_FOLIO) from  hc.II_HEADER where II_TYPE = '" + tipo +"');\r\n"
					+ "declare @tabla as table (\r\n"
					+ "	GRUPO_ID int,\r\n"
					+ "	GRUPO_NAME nvarchar(max),\r\n"
					+ "	SKU nvarchar(max),\r\n"
					+ "	TEORICO INT,\r\n"
					+ "	FISICO INT,\r\n"
					+ "	SOBRANTE INT,\r\n"
					+ "	FALTANTE INT,\r\n"
					+ "	IO_DISABLED INT\r\n"
					+ ");\r\n"
					+ ";WITH cte as (\r\n"
					+ "	select \r\n"
					+ "	 GRUPO_ID = (select top 1 ih.IO_Folio from hc.io_header as ih where ih.io_folio = det.io_folio)\r\n"
					+ "	,GRUPO_NAME = (select top 1 ih.IO_Description from hc.io_header as ih where ih.io_folio = det.io_folio)\r\n"
					+ "	,SKU = det.IO_SKU_REF\r\n"
					+ "	,TEORICO = det.IO_OnHand\r\n"
					+ "	,FISICO = det.IO_OnHandReal\r\n"
					+ "	,SOBRANTE = CASE WHEN (det.IO_Dif > 0) THEN det.IO_DIF ELSE 0 END\r\n"
					+ "	,FALTANTE = CASE WHEN (det.IO_Dif < 0) THEN (det.IO_DIF * -1) ELSE 0 END\r\n"
					+ "	,IO_DISABLED = det.IO_DISABLED\r\n"
					+ "	from hc.II_HEADER_IO_HEADER as hh \r\n"
					+ "	join hc.io_detail as det on det.IO_Folio = hh.IO_FOLIO\r\n"
					+ "	where \r\n"
					+ "	hh.II_FOLIO = @Folio\r\n"
					+ ")\r\n"
					+ "INSERT INTO @tabla\r\n"
					+ "select * from cte\r\n"
					+ "declare @totalSkus int = (select count(*) from @tabla);\r\n"
					+ "declare @TotalMontoSobrante float = (select SUM(det.IO_Dif * IO_PRE_VTA) AS TOTAL_SOBRANTE_MONTO\r\n"
					+ "									from hc.II_HEADER_IO_HEADER as hh \r\n"
					+ "									join hc.io_detail as det on det.IO_Folio = hh.IO_FOLIO\r\n"
					+ "									where det.IO_Dif > 0  AND hh.II_FOLIO = @Folio)\r\n"
					+ "declare @TotalMontoFaltante float = (select SUM(-1*det.IO_Dif * IO_PRE_VTA) AS TOTAL_SOBRANTE_MONTO\r\n"
					+ "									from hc.II_HEADER_IO_HEADER as hh \r\n"
					+ "									join hc.io_detail as det on det.IO_Folio = hh.IO_FOLIO\r\n"
					+ "									where det.IO_Dif < 0  AND hh.II_FOLIO = @Folio)\r\n"
					+ "select \r\n"
					+ "TOTAL_FALTANTES = (SUM(FALTANTE))*1.00\r\n"
					+ ",TOTAL_MONTO_FALTANTE = FORMAT(ISNULL(@TotalMontoFaltante,0),'c')\r\n"
					+ ",TOTAL_SOBRANTES = (SUM(SOBRANTE))*1.00\r\n"
					+ ",TOTAL_MONTO_SOBRANTE = FORMAT(ISNULL(@TotalMontoSobrante,0),'c')\r\n"
					+ "from @tabla where IO_DISABLED in (0,1)";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Obtiene faltantes y sobrantes desde BD para una Plantilla
	 * @author Marcos Morales
	 */
	public ResultSet obtieneFaltantesSobrantesPorPlantillaBD() {

		String nomPlan = detailTemplate.getPlantilla();
		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			String query = "declare @Folio int = (select max(II_FOLIO) from  hc.II_HEADER where II_TYPE = 'P');\r\n"
					+ "\r\n"
					+ "declare @tabla as table (\r\n"
					+ "	TEMPLATE_NAME nvarchar(max),\r\n"
					+ "	SKU nvarchar(max),\r\n"
					+ "	TEORICO INT,\r\n"
					+ "	FISICO INT,\r\n"
					+ "	SOBRANTE INT,\r\n"
					+ "	FALTANTE INT,\r\n"
					+ "	PRECIO decimal(9,2),\r\n"
					+ "	IO_DISABLED INT\r\n"
					+ ");\r\n"
					+ "\r\n"
					+ ";WITH cte as (\r\n"
					+ "select	 TEMPLATE_NAME = thd.TEMPLATE_NAME\r\n"
					+ "		,SKU = det.IO_SKU_REF\r\n"
					+ "		,TEORICO = det.IO_OnHand\r\n"
					+ "		,FISICO = det.IO_OnHandReal\r\n"
					+ "		,SOBRANTE = CASE WHEN (det.IO_Dif > 0) THEN det.IO_DIF ELSE 0 END\r\n"
					+ "		,FALTANTE = CASE WHEN (det.IO_Dif < 0) THEN (det.IO_DIF * -1) ELSE 0 END\r\n"
					+ "		,PRECIO = det.IO_PRE_VTA\r\n"
					+ "		,IO_DISABLED = det.IO_DISABLED\r\n"
					+ "from hc.II_HEADER_IO_HEADER as hh \r\n"
					+ "join hc.io_detail as det on det.IO_Folio = hh.IO_FOLIO\r\n"
					+ "join hc.TEMPLATE_SELF_GENERATED_COUNTS_HEADER thd on hh.II_FOLIO = thd.II_FOLIO\r\n"
					+ "join hc.TEMPLATE_SELF_GENERATED_COUNTS_DETAIL tdt on thd.ID = tdt.TEMPLATE_HEADER_ID and det.IO_SKU_REF = tdt.SKU_REF\r\n"
					+ "where hh.II_FOLIO = (SELECT II_FOLIO FROM [HC].[II_HEADER] WHERE YEAR(II_STARTDATE) = YEAR(GETDATE()) AND II_WEEKNUMBER = DATEPART(WEEK, GETDATE()) AND II_TYPE = 'P')\r\n"
					+ "and thd.TEMPLATE_NAME = '" + nomPlan + "'\r\n"
					+ ") INSERT INTO @tabla select * from cte\r\n"
					+ "\r\n"
					+ "declare @totalSkus int = (select count(*) from @tabla);\r\n"
					+ "declare @TotalMontoSobrante float = (select SUM(SOBRANTE * PRECIO) AS TOTAL_SOBRANTE_MONTO from @tabla);\r\n"
					+ "declare @TotalMontoFaltante float = (select SUM(FALTANTE * PRECIO) AS TOTAL_SOBRANTE_MONTO from @tabla);\r\n"
					+ "\r\n"
					+ "select	 TOTAL_FALTANTES = (SUM(FALTANTE))*1.00\r\n"
					+ "		,TOTAL_MONTO_FALTANTE = FORMAT(ISNULL(@TotalMontoFaltante,0),'c')\r\n"
					+ "		,TOTAL_SOBRANTES = (SUM(SOBRANTE))*1.00\r\n"
					+ "		,TOTAL_MONTO_SOBRANTE = FORMAT(ISNULL(@TotalMontoSobrante,0),'c')\r\n"
					+ "from @tabla where IO_DISABLED in (0,1)";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Valida que los conteos no se guardaron en BD
	 * @author Marcos Morales
	 */
	public ResultSet obtieneSKUSContadosBD() {

		ResultSet resultSet = null;
		String textPlantilla = au.driver.findElement(txtNomPlantilla).getText();
		String plantilla = textPlantilla.split("-")[0].trim();

		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			String query = ";with cte as (\r\n"
					+ "SELECT	 hh.II_FOLIO\r\n"
					+ "		,hh.IO_FOLIO\r\n"
					+ "		,id.IO_SKU_Order\r\n"
					+ "		,id.IO_SKU_REF\r\n"
					+ "		,id.IO_OnHand\r\n"
					+ "		,id.IO_OnHandReal\r\n"
					+ "		,id.IO_Dif\r\n"
					+ "		,id.IO_PRE_VTA\r\n"
					+ "FROM [HC].[II_HEADER_IO_HEADER] hh\r\n"
					+ "inner join HC.IO_DETAIL id on hh.IO_FOLIO = id.IO_Folio\r\n"
					+ "WHERE YEAR(hh.TM_CRT) =YEAR(GETDATE())\r\n"
					+ "AND DATEPART(ISO_WEEK, hh.TM_CRT) = DATEPART(ISO_WEEK, GETDATE())\r\n"
					+ ")\r\n"
					+ "SELECT COUNT(cte.IO_OnHandReal) [Rows]\r\n"
					+ "FROM [HC].[TEMPLATE_SELF_GENERATED_COUNTS_HEADER] th\r\n"
					+ "inner join [HC].[TEMPLATE_SELF_GENERATED_COUNTS_DETAIL] td on td.TEMPLATE_HEADER_ID = th.ID\r\n"
					+ "inner join cte on th.II_FOLIO = cte.II_FOLIO and td.SKU_REF = cte.IO_SKU_REF\r\n"
					+ "WHERE YEAR(th.TM_CRT) =YEAR(GETDATE())\r\n"
					+ "AND DATEPART(ISO_WEEK, th.TM_CRT) = DATEPART(ISO_WEEK, GETDATE())\r\n"
					+ "and th.TEMPLATE_NAME like '%" + plantilla + "%'\r\n"
					+ "and cte.IO_OnHandReal is not null\r\n"
					+ "and th.IS_ACTIVE = 1";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Realiza el click largo sobre un producto
	 * @author Marcos Morales
	 * @param idxProd Indice del producto
	 */
	public void performLongClick(int idxProd) {
		WebElement element = au.driver.findElement(By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_product\"])[" + idxProd + "]"));

		// Cast driver to JavascriptExecutor
		JavascriptExecutor js = (JavascriptExecutor) au.driver;

		// Perform long click
		js.executeScript("mobile: longClickGesture", ImmutableMap.of(
				"elementId", ((RemoteWebElement) element).getId(),
				"duration", 2000
				));
	}

	/**
	 * Establece en un objeto accesible durante la ejecucion de un script
	 * el nombre de la plantilla con la que se este trabajando
	 * @author Marcos Morales
	 */
	public void setTemplateName() {
		waitForVisibility(txtNombrePlantilla, Duration.ofSeconds(30));
		String txtNomPlan = au.driver.findElement(txtNombrePlantilla).getText();
		detailTemplate = new DetallePlantilla(txtNomPlan);
	}

	/**
	 * Realiza la captura de skus
	 * @author Marcos Morales
	 * @param numSKUS Registros a obtener para realizar la captura del conteo
	 * @throws SQLException 
	 * @throws AssertionError 
	 */
	public void capturarSKU(int numSKUS) throws SQLException, AssertionError {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		ResultSet rs = obtieneSkusPorPlantillaBD(numSKUS);

		if (rs.next()) {
			String sku = rs.getString(2);
			au.sendKeys(txtBarcode, sku);
		} else {
			throw new AssertionError("No se encontraron registros al ejecutar la consulta");
		}
	}

	/**
	 * Realiza la captura de skus
	 * @author Marcos Morales
	 * @param numSkus Registros a obtener para realizar la captura del conteo
	 * @param offSet Registros a descartar de la consulta
	 * @throws SQLException 
	 * @throws AssertionError 
	 */
	public void capturarSKU(int numSkus, int offSet) throws SQLException, AssertionError {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		ResultSet rs = obtieneSkusParaCaptura(numSkus, offSet);

		if (rs.next()) {
			String sku = rs.getString(3);
			au.sendKeys(txtBarcode, sku);
		} else {
			throw new AssertionError("No se encontraron registros al ejecutar la consulta");
		}
	}

	/**
	 * Realiza la captura de skus
	 * @author Marcos Morales
	 * @param numSKUS Registros a obtener para realizar la captura del conteo
	 * @throws SQLException 
	 * @throws AssertionError 
	 */
	public void capturarSKUConExistencia(int numSKUS) throws SQLException, AssertionError {
		waitForVisibility(txtBarcode, Duration.ofSeconds(30));
		ResultSet rs = obtieneSkusPorPlantillaConExistenciaBD(numSKUS);

		if (rs.next()) {
			String sku = rs.getString(2);
			au.sendKeys(txtBarcode, sku);
		} else {
			throw new AssertionError("No se encontraron registros al ejecutar la consulta");
		}
	}

	/**
	 * Metodo para hacer click y mostrar el teclado posterior a ingresar un valor
	 * en el campor para ingresar SKUS
	 * @author Marcos Morales
	 * @param screenShot Toma captura de pantalla: true/false
	 * @throws InterruptedException 
	 */
	public void clickEnterBarcode(boolean screenShot) throws InterruptedException {
		au.click(txtBarcode);
		Thread.sleep(500);
		takeScreenshot(screenShot);
		((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
	}

	/**
	 * Obtiene los SKUS de una plantilla en especifico
	 * @author Marcos Morales
	 * @param nomPlantilla Nombre de la plantilla de la que se obtiene los SKUs
	 */
	public ResultSet obtieneRegistrosPlantillaInactivaBD(String nomPlantilla) {
		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			String query = "select	 PLANTILLA_ID = th.ID\r\n"
					+ "		,SEM.II_DESCRIPTION AS 'SEMANA'\r\n"
					+ "		,PLANTILLA_NOMBRE = th.TEMPLATE_NAME\r\n"
					+ "		,SKU = td.SKU_REF\r\n"
					+ "		,ITEM_NAME AS 'ARTICULO'\r\n"
					+ "		,COLABORADOR_ASIGNADO = (SELECT UPPER(COALESCE([NAME],'') + ' ' + COALESCE(LASTNAME_PAT, '') + ' ' + COALESCE(LASTNAME_MAT, '')) \r\n"
					+ "								FROM XposStore.dbo.PERSON where PERSON_ID = th.COLABORATOR_ID)\r\n"
					+ "		,TURNO = CASE th.TURN_ID WHEN 1 THEN 'MAÑANA' WHEN 2 THEN 'TARDE' WHEN 3 THEN 'NOCHE' END\r\n"
					+ "		,FECHA_CREACION = th.TM_CRT\r\n"
					+ "		,ESTATUS = CASE TH.IS_ACTIVE WHEN 1 THEN 'ACTIVO' ELSE 'INACTIVO' END\r\n"
					+ "from hc.TEMPLATE_SELF_GENERATED_COUNTS_HEADER as th\r\n"
					+ "left join hc.TEMPLATE_SELF_GENERATED_COUNTS_DETAIL as td on td.TEMPLATE_HEADER_ID = th.ID\r\n"
					+ "JOIN hc.II_HEADER AS SEM ON TH.II_FOLIO = SEM.II_FOLIO\r\n"
					+ "WHERE SEM.II_WEEKNUMBER = DATEPART(ISO_WEEK, GETDATE())\r\n"
					+ "AND th.TEMPLATE_NAME = '" + nomPlantilla + "'\r\n"
					+ "AND TH.IS_ACTIVE = 0";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Obtiene el nombre de una plantilla a eliminar
	 * @author Marcos Morales
	 * @param idxPlantilla Indice de la plantilla a eliminar
	 */
	public String obtienePlantillaAEliminar(int idxPlantilla) {
		String plantilla = au.driver.findElement(By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_template_name\"])[" + idxPlantilla + "]")).getText();
		return plantilla;
	}

	/**
	 * Identifica una plantilla a eliminar
	 * @author Marcos Morales
	 * @param idxPlantilla Indice de la plantilla a eliminar
	 */
	public void eliminarPlantillas(int idxPlantilla) {
		By objScrollable = By.xpath("(//android.view.ViewGroup[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ct_item_template\"])[" + idxPlantilla + "]");
		scrolling(objScrollable, "right", 1.0);
	}

	/**
	 * Devuelve Registro de plantilla inactiva en BD
	 * @author Lizbeth Hernandez
	 * @param nomPlantilla Nombre de la plantilla eliminada
	 */
	public int retornaregistroPlantillaEliminadaBD(String nomPlantilla) throws SQLException, AssertionError {
		int count = 0;
		ResultSet rs = obtieneRegistrosPlantillaInactivaBD(nomPlantilla);
		if (rs.isBeforeFirst()) {
			while (rs.next()) {
				count++;
			}
			System.out.println("No. Registros de la plantilla: " + nomPlantilla + " :" + count);
			return count;
		} else {
			throw new AssertionError("La consulta ejecutada del metodo obtieneRegistrosPlantillaInactivaBD no devolvio resultados");
		}
	}

	/**
	 * Devuelve true si la plantilla continua mostrandose en pantalla
	 * @author Marcos Morales
	 */
	public boolean[] retornaPlantillaNOEliminada(String nomPlantilla) {
		boolean[] status = new boolean[1];
		try {
			status[0] = au.driver.findElement(By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_template_name\" and @text=\""+ nomPlantilla + "\"]")).isDisplayed();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * Ejecuta el flujo para crear Plantillas de SKUS
	 * @author Marcos Morales
	 * @param tipoConsulta skusactivos, skusinactivos, skusactina, plusactivos, plusinactivos, plusactina
	 * @param user Usuario para ingresar a la aplicacion Oxxo al día
	 * @param pass Contraseña del usuario
	 * @param app Ej. com.femco.oxxo.operaciones.mitienda.debug
	 * @param nomPlan Nombre para la plantilla
	 * @param numSkus Cantidad de skus por plantilla
	 * @param valorConteo Valor a capturar como conteo
	 * @throws AssertionError 
	 * @throws SQLException 
	 */
	public void prerequisitoCapturaConteosPlantillas(String user, String pass, String app, String nomPlan, int numSkus, String valorConteo) throws IOException, InterruptedException, SQLException, AssertionError {

		Login login = new Login(testCase, au, baseExecution);
		Menus menus = new Menus(testCase, au, baseExecution);
		Comunes comunes = new Comunes(testCase, au, baseExecution);

		//Login
		login.login(user, pass);

		//Ingresar a Conteos Propios
		menus.ingresarConteosPropios(false);

		//Seleccionar semana
		comunes.seleccionarSemana(false);

		//Continuar
		comunes.clickBtnContinuar(false);

		//Iniciar Conteos
		menus.clickBtnIniciarConteo(false);

		//Seleccionar plantilla
		esperaCampoDeBusqueda();
		buscarPlantilla(nomPlan);
		seleccionarPlantilla(1, false);

		//ACEPTAR consolidación
		comunes.clickBtnAceptar(false);
		esperaCampoIngresarSKU();	//Implementado para espera de elementos en pantalla
		instanciaDetailTemplate();	//Implementado para tener en memoria el nombre de la plantilla

		//Registrar conteos
		registrarConteoPorPlantilla(numSkus, valorConteo);

		//Guardar conteos de la plantilla
		clickBtnGuardarPreInv(false);

		//Confirmar
		comunes.clickBtnAceptar(false);
		esperaSeOcultaMensajeGuardado();

		//Cerrar Aplicacion
		comunes.finalizarApp(app);
	}

	/**
	 * Click en la opcion resetear
	 * @author Marcos Morales
	 */
	public void clickOpcionResetear() {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int x = 580;	//HH
		int y = 630;	//HH
		//int x = 935;	//Emulado - pixel 5
		//int y = 935;	//Emulado - pixel 5

		// Realiza el clic en las coordenadas especificadas
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		Sequence tap = new Sequence(finger, 1);
		tap.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), x, y));
		tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		tap.addAction(new Pause(finger, Duration.ofMillis(200)));
		tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

		au.driver.perform(Arrays.asList(tap));
	}

	/**
	 * Click en la opcion resetear
	 * @author Marcos Morales
	 * @param xValue posicion x de la opcion Resetear
	 * @param yValue posicion y de la opion Resetear
	 */
	public void clickOpcionResetear(int xValue, int yValue) {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int x = xValue;	//HH
		int y = yValue;	//HH
		//int x = 935;	//Emulado - pixel 5
		//int y = 935;	//Emulado - pixel 5

		// Realiza el clic en las coordenadas especificadas
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		Sequence tap = new Sequence(finger, 1);
		tap.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), x, y));
		tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		tap.addAction(new Pause(finger, Duration.ofMillis(200)));
		tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

		au.driver.perform(Arrays.asList(tap));
	}

	/**
	 * Mostrar la opción Resetear de una plantilla
	 * @author Marcos Morales
	 * @param idxPlantilla Indice(identificador) de la plantilla a resetear
	 */
	public void resetearPlantillas(int idxPlantilla) {
		By objScrollable = By.xpath("(//android.view.ViewGroup[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ct_item_template\"])[" + idxPlantilla + "]");
		scrolling(objScrollable, "right", 1.0);
	}

	/**
	 * Obtiene los SKUS de una plantilla en especifico
	 * @author Marcos Morales
	 * @param nomPlantilla Nombre de la plantilla de la que se obtiene los SKUs
	 */
	public ResultSet obtieneRegistrosPlantillaReseteadaBD(String nomPlantilla) {
		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			String query = "DECLARE @iifolio int;\r\n"
					+ "\r\n"
					+ "SET @iifolio = (\r\n"
					+ "	select II_FOLIO from hc.II_HEADER\r\n"
					+ "	where DATEPART(YEAR, II_STARTDATE) = YEAR(GETDATE())\r\n"
					+ "	AND II_WEEKNUMBER = DATEPART(ISO_WEEK, GETDATE())\r\n"
					+ "	AND II_TYPE = 'P'\r\n"
					+ ");\r\n"
					+ "\r\n"
					+ ";WITH latestCountDates AS (\r\n"
					+ "	SELECT \r\n"
					+ "		MAX(detail.II_COUNT_DATE) AS maxCountDate\r\n"
					+ "		,templateId = MAX(detail.SELF_COUNT_TEMPLATE_ID)\r\n"
					+ "		,sku = detail.IO_SKU_REF\r\n"
					+ "	FROM HC.II_DETAIL as detail\r\n"
					+ "	WHERE detail.II_FOLIO = @iifolio \r\n"
					+ "	AND SELF_COUNT_TEMPLATE_ID IS NOT NULL\r\n"
					+ "	GROUP BY detail.SELF_COUNT_TEMPLATE_ID, detail.IO_SKU_REF\r\n"
					+ ")\r\n"
					+ ", conteos_plantillas as (\r\n"
					+ "	SELECT IDET.* \r\n"
					+ "	FROM HC.II_DETAIL as IDET\r\n"
					+ "	INNER JOIN latestCountDates LST \r\n"
					+ "	ON LST.sku = IDET.IO_SKU_REF \r\n"
					+ "	AND LST.maxCountDate = IDET.II_COUNT_DATE \r\n"
					+ "	AND LST.templateId = IDET.SELF_COUNT_TEMPLATE_ID\r\n"
					+ "	WHERE IDET.II_FOLIO = @iifolio and idet.TYPE_COUNT is null\r\n"
					+ ")\r\n"
					+ ", conteos_master_propios as (\r\n"
					+ "	select \r\n"
					+ "	det.*\r\n"
					+ "	from hc.io_detail as det\r\n"
					+ "	join hc.II_HEADER_IO_HEADER as hh on hh.IO_FOLIO = det.IO_Folio\r\n"
					+ "	where hh.II_FOLIO = @iifolio\r\n"
					+ "\r\n"
					+ ")\r\n"
					+ ", plantillas_propios as (\r\n"
					+ "	select \r\n"
					+ "	 TEMPLATE_ID = tp.ID \r\n"
					+ "	,tp.TEMPLATE_NAME\r\n"
					+ "	,IO_FOLIO = (select top 1 IO_Folio from conteos_master_propios as cmp where cmp.IO_SKU_REF = td.SKU_REF)\r\n"
					+ "	,SKU = td.SKU_REF\r\n"
					+ "	,SKU_NAME = (select itm.[NAME] from XposStore.dbo.ITEM as itm where itm.SKU = td.SKU_REF)\r\n"
					+ "	,FECHA_MODIFICACION = tp.TM_MDY\r\n"
					+ "	,COLABORADOR_ASIGNADO = (select p.[NAME] from XposStore.dbo.PERSON as p where p.PERSON_ID = tp.COLABORATOR_ID)\r\n"
					+ "	,MASTER_TEORICO = (select top 1 IO_OnHand from conteos_master_propios as cmp where cmp.IO_SKU_REF = td.SKU_REF)\r\n"
					+ "	,MASTER_FISICO = (select top 1 IO_OnHandReal from conteos_master_propios as cmp where cmp.IO_SKU_REF = td.SKU_REF)\r\n"
					+ "	,MASTER_DIFERENCIA = (select top 1 IO_Dif from conteos_master_propios as cmp where cmp.IO_SKU_REF = td.SKU_REF)\r\n"
					+ "	from hc.TEMPLATE_SELF_GENERATED_COUNTS_HEADER as tp\r\n"
					+ "	left join hc.TEMPLATE_SELF_GENERATED_COUNTS_DETAIL as td on td.TEMPLATE_HEADER_ID = tp.ID\r\n"
					+ "	where tp.II_FOLIO = @iifolio and tp.IS_ACTIVE = 1 and td.IS_ACTIVE = 1\r\n"
					+ ")\r\n"
					+ "select	 pp.FECHA_MODIFICACION\r\n"
					+ "		,pp.TEMPLATE_NAME\r\n"
					+ "		,trim(pp.SKU)\r\n"
					+ "		,pp.COLABORADOR_ASIGNADO\r\n"
					+ "		,pp.MASTER_TEORICO\r\n"
					+ "		,pp.MASTER_FISICO\r\n"
					+ "		,pp.MASTER_DIFERENCIA\r\n"
					+ "		,TEMPLATE_TEORICO = cp.ONHAND\r\n"
					+ "		,TEMPLATE_FISICO = cp.ONHANDREAL\r\n"
					+ "		,TEMPLATE_DIFERENCIA = cp.DIF\r\n"
					+ "from plantillas_propios as pp\r\n"
					+ "left join conteos_plantillas as cp \r\n"
					+ "on cp.SELF_COUNT_TEMPLATE_ID = pp.TEMPLATE_ID and cp.IO_SKU_REF = pp.SKU\r\n"
					+ "where TEMPLATE_NAME in ('" + nomPlantilla + "')\r\n"
					+ "and cp.ONHAND is null\r\n"
					+ "order by SKU";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Devuelve un valor mayor a cero cuando la plantilla se resetea correctamente
	 * @author Marcos Morales
	 * @param nomPlantilla Nombre de la plantilla reseteada
	 */
	public int retornaPlantillaReseteadaBD(String nomPlantilla) throws SQLException, AssertionError {
		int count = 0;
		ResultSet rs = obtieneRegistrosPlantillaReseteadaBD(nomPlantilla);
		if (rs.isBeforeFirst()) {
			while (rs.next()) {
				count++;
			}
		} else {
			throw new AssertionError("La consulta ejecutada del metodo obtieneRegistrosPlantillaReseteadaBD no devolvio resultados");
		}
		return count;
	}

	/**
	 * Genera un String con el resultado de la consulta a BD
	 * @author Marcos Morales
	 * @param nomPlantilla Nombre de la plantilla reseteada
	 */
	public String generaStringPlantillaReseteadaBD(String nomPlantilla) throws SQLException, AssertionError {
		String resultado = "";
		ResultSet rs = obtieneRegistrosPlantillaReseteadaBD(nomPlantilla);
		if (rs.isBeforeFirst()) {
			while (rs.next()) {
				String col1 = rs.getString(1);
				String col2 = rs.getString(2);
				String col3 = rs.getString(3);
				String col4 = rs.getString(4);
				String col5 = rs.getString(5);
				String col6 = rs.getString(6);
				String col7 = rs.getString(7);
				String col8 = rs.getString(8);
				String col9 = rs.getString(9);
				String col10 = rs.getString(10);
				resultado += col1 + "|" + col2 + "|" + col3 + "|" + col4 + "|" + col5 + "|" + col6 + "|" + col7 + "|" + col8 + "|" + col9 + "|" + col10 + "\n";
			}
			//System.out.println(resultado);
			return resultado;
		} else {
			throw new AssertionError("La consulta ejecutada del metodo obtieneRegistrosPlantillaReseteadaBD no devolvio resultados");
		}
	}

	/**
	 * Devuelve el listado de plantillas sin asignaciones
	 * @author Marcos Morales
	 */
	public List<WebElement> listaPlantillasSinAsignar() {
		List<WebElement> lstPlantillas = au.driver.findElements(lblAsignadoA);
		return lstPlantillas;
	}

	/**
	 * Obtiene los conteos de una plantilla en especifico
	 * @author Marcos Morales
	 * @param nomPlantilla Nombre de la plantilla de la que se obtiene los conteos
	 */
	public ResultSet obtieneRegistrosDeConteosPorPlantillaBD(String nomPlantilla) {
		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			String query = "DECLARE @iifolio int;\r\n"
					+ "\r\n"
					+ "SET @iifolio = (\r\n"
					+ "	select II_FOLIO from hc.II_HEADER\r\n"
					+ "	where DATEPART(YEAR, II_STARTDATE) = YEAR(GETDATE())\r\n"
					+ "	AND II_WEEKNUMBER = DATEPART(ISO_WEEK, GETDATE())\r\n"
					+ "	AND II_TYPE = 'P'\r\n"
					+ ");\r\n"
					+ "\r\n"
					+ ";WITH latestCountDates AS (\r\n"
					+ "	SELECT \r\n"
					+ "		MAX(detail.II_COUNT_DATE) AS maxCountDate\r\n"
					+ "		,templateId = MAX(detail.SELF_COUNT_TEMPLATE_ID)\r\n"
					+ "		,sku = detail.IO_SKU_REF\r\n"
					+ "	FROM HC.II_DETAIL as detail\r\n"
					+ "	WHERE detail.II_FOLIO = @iifolio \r\n"
					+ "	AND SELF_COUNT_TEMPLATE_ID IS NOT NULL\r\n"
					+ "	GROUP BY detail.SELF_COUNT_TEMPLATE_ID, detail.IO_SKU_REF\r\n"
					+ ")\r\n"
					+ ", conteos_plantillas as (\r\n"
					+ "	SELECT IDET.* \r\n"
					+ "	FROM HC.II_DETAIL as IDET\r\n"
					+ "	INNER JOIN latestCountDates LST \r\n"
					+ "	ON LST.sku = IDET.IO_SKU_REF \r\n"
					+ "	AND LST.maxCountDate = IDET.II_COUNT_DATE \r\n"
					+ "	AND LST.templateId = IDET.SELF_COUNT_TEMPLATE_ID\r\n"
					+ "	WHERE IDET.II_FOLIO = @iifolio and idet.TYPE_COUNT is null\r\n"
					+ ")\r\n"
					+ ", conteos_master_propios as (\r\n"
					+ "	select \r\n"
					+ "	det.*\r\n"
					+ "	from hc.io_detail as det\r\n"
					+ "	join hc.II_HEADER_IO_HEADER as hh on hh.IO_FOLIO = det.IO_Folio\r\n"
					+ "	where hh.II_FOLIO = @iifolio\r\n"
					+ "\r\n"
					+ ")\r\n"
					+ ", plantillas_propios as (\r\n"
					+ "	select \r\n"
					+ "	 TEMPLATE_ID = tp.ID \r\n"
					+ "	,tp.TEMPLATE_NAME\r\n"
					+ "	,IO_FOLIO = (select top 1 IO_Folio from conteos_master_propios as cmp where cmp.IO_SKU_REF = td.SKU_REF)\r\n"
					+ "	,SKU = td.SKU_REF\r\n"
					+ "	,SKU_NAME = (select itm.[NAME] from XposStore.dbo.ITEM as itm where itm.SKU = td.SKU_REF)\r\n"
					+ "	,FECHA_MODIFICACION = tp.TM_MDY\r\n"
					+ "	,COLABORADOR_ASIGNADO = (select p.[NAME] from XposStore.dbo.PERSON as p where p.PERSON_ID = tp.COLABORATOR_ID)\r\n"
					+ "	,MASTER_TEORICO = (select top 1 IO_OnHand from conteos_master_propios as cmp where cmp.IO_SKU_REF = td.SKU_REF)\r\n"
					+ "	,MASTER_FISICO = (select top 1 IO_OnHandReal from conteos_master_propios as cmp where cmp.IO_SKU_REF = td.SKU_REF)\r\n"
					+ "	,MASTER_DIFERENCIA = (select top 1 IO_Dif from conteos_master_propios as cmp where cmp.IO_SKU_REF = td.SKU_REF)\r\n"
					+ "	from hc.TEMPLATE_SELF_GENERATED_COUNTS_HEADER as tp\r\n"
					+ "	left join hc.TEMPLATE_SELF_GENERATED_COUNTS_DETAIL as td on td.TEMPLATE_HEADER_ID = tp.ID\r\n"
					+ "	where tp.II_FOLIO = @iifolio and tp.IS_ACTIVE = 1 and td.IS_ACTIVE = 1\r\n"
					+ ")\r\n"
					+ "select	 pp.FECHA_MODIFICACION\r\n"
					+ "		,pp.TEMPLATE_NAME\r\n"
					+ "		,trim(pp.SKU)\r\n"
					+ "		,pp.COLABORADOR_ASIGNADO\r\n"
					+ "		,TEMPLATE_TEORICO = cp.ONHAND\r\n"
					+ "		,TEMPLATE_FISICO = cp.ONHANDREAL\r\n"
					+ "		,TEMPLATE_DIFERENCIA = cp.DIF\r\n"
					+ "from plantillas_propios as pp\r\n"
					+ "left join conteos_plantillas as cp \r\n"
					+ "on cp.SELF_COUNT_TEMPLATE_ID = pp.TEMPLATE_ID and cp.IO_SKU_REF = pp.SKU\r\n"
					+ "where TEMPLATE_NAME in ('" + nomPlantilla + "')\r\n"
					+ "order by TEMPLATE_NAME, SKU";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Obtiene los conteos de una plantilla en especifico
	 * @author Marcos Morales
	 * @param nomPlantilla Nombre de la plantilla de la que se obtiene los conteos
	 */
	public ResultSet obtieneConteosPorPlantillaBD(String nomPlantilla) {
		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			String query = "DECLARE @iifolio int;\r\n"
					+ "\r\n"
					+ "SET @iifolio = (\r\n"
					+ "	select II_FOLIO from hc.II_HEADER\r\n"
					+ "	where DATEPART(YEAR, II_STARTDATE) = YEAR(GETDATE())\r\n"
					+ "	AND II_WEEKNUMBER = DATEPART(ISO_WEEK, GETDATE())\r\n"
					+ "	AND II_TYPE = 'P'\r\n"
					+ ");\r\n"
					+ "\r\n"
					+ ";WITH latestCountDates AS (\r\n"
					+ "	SELECT \r\n"
					+ "		MAX(detail.II_COUNT_DATE) AS maxCountDate\r\n"
					+ "		,templateId = MAX(detail.SELF_COUNT_TEMPLATE_ID)\r\n"
					+ "		,sku = detail.IO_SKU_REF\r\n"
					+ "	FROM HC.II_DETAIL as detail\r\n"
					+ "	WHERE detail.II_FOLIO = @iifolio \r\n"
					+ "	AND SELF_COUNT_TEMPLATE_ID IS NOT NULL\r\n"
					+ "	GROUP BY detail.SELF_COUNT_TEMPLATE_ID, detail.IO_SKU_REF\r\n"
					+ ")\r\n"
					+ ", conteos_plantillas as (\r\n"
					+ "	SELECT IDET.* \r\n"
					+ "	FROM HC.II_DETAIL as IDET\r\n"
					+ "	INNER JOIN latestCountDates LST \r\n"
					+ "	ON LST.sku = IDET.IO_SKU_REF \r\n"
					+ "	AND LST.maxCountDate = IDET.II_COUNT_DATE \r\n"
					+ "	AND LST.templateId = IDET.SELF_COUNT_TEMPLATE_ID\r\n"
					+ "	WHERE IDET.II_FOLIO = @iifolio and idet.TYPE_COUNT is null\r\n"
					+ ")\r\n"
					+ ", conteos_master_propios as (\r\n"
					+ "	select \r\n"
					+ "	det.*\r\n"
					+ "	from hc.io_detail as det\r\n"
					+ "	join hc.II_HEADER_IO_HEADER as hh on hh.IO_FOLIO = det.IO_Folio\r\n"
					+ "	where hh.II_FOLIO = @iifolio\r\n"
					+ "\r\n"
					+ ")\r\n"
					+ ", plantillas_propios as (\r\n"
					+ "	select \r\n"
					+ "	 TEMPLATE_ID = tp.ID \r\n"
					+ "	,tp.TEMPLATE_NAME\r\n"
					+ "	,IO_FOLIO = (select top 1 IO_Folio from conteos_master_propios as cmp where cmp.IO_SKU_REF = td.SKU_REF)\r\n"
					+ "	,SKU = td.SKU_REF\r\n"
					+ "	,SKU_NAME = (select itm.[NAME] from XposStore.dbo.ITEM as itm where itm.SKU = td.SKU_REF)\r\n"
					+ "	,FECHA_MODIFICACION = tp.TM_MDY\r\n"
					+ "	,COLABORADOR_ASIGNADO = (select p.[NAME] from XposStore.dbo.PERSON as p where p.PERSON_ID = tp.COLABORATOR_ID)\r\n"
					+ "	,MASTER_TEORICO = (select top 1 IO_OnHand from conteos_master_propios as cmp where cmp.IO_SKU_REF = td.SKU_REF)\r\n"
					+ "	,MASTER_FISICO = (select top 1 IO_OnHandReal from conteos_master_propios as cmp where cmp.IO_SKU_REF = td.SKU_REF)\r\n"
					+ "	,MASTER_DIFERENCIA = (select top 1 IO_Dif from conteos_master_propios as cmp where cmp.IO_SKU_REF = td.SKU_REF)\r\n"
					+ "	from hc.TEMPLATE_SELF_GENERATED_COUNTS_HEADER as tp\r\n"
					+ "	left join hc.TEMPLATE_SELF_GENERATED_COUNTS_DETAIL as td on td.TEMPLATE_HEADER_ID = tp.ID\r\n"
					+ "	where tp.II_FOLIO = @iifolio and tp.IS_ACTIVE = 1 and td.IS_ACTIVE = 1\r\n"
					+ ")\r\n"
					+ "select trim(pp.SKU) [SKU]\r\n"
					+ "		,pp.COLABORADOR_ASIGNADO\r\n"
					+ "		,TEMPLATE_TEORICO = cp.ONHAND\r\n"
					+ "		,TEMPLATE_FISICO = cp.ONHANDREAL\r\n"
					+ "from plantillas_propios as pp\r\n"
					+ "left join conteos_plantillas as cp \r\n"
					+ "on cp.SELF_COUNT_TEMPLATE_ID = pp.TEMPLATE_ID and cp.IO_SKU_REF = pp.SKU\r\n"
					+ "where TEMPLATE_NAME in ('" + nomPlantilla + "')\r\n"
					+ "order by TEMPLATE_NAME, SKU";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Genera un String con el resultado de la consulta a BD
	 * @author Marcos Morales
	 * @param nomPlantilla Nombre de la plantilla reseteada
	 */
	public String generaStringRegistrosDeConteosPorPlantilla(String nomPlantilla) throws SQLException, AssertionError {
		String resultado = "FECHA|PLANTILLA|SKU|COLABORADOR|TEORICO|FISICO|DIFERENCIA\n";
		ResultSet rs = obtieneRegistrosDeConteosPorPlantillaBD(nomPlantilla);
		if (rs.isBeforeFirst()) {
			while (rs.next()) {
				String col1 = rs.getString(1);
				String col2 = rs.getString(2);
				String col3 = rs.getString(3);
				String col4 = rs.getString(4);
				String col5 = rs.getString(5);
				String col6 = rs.getString(6);
				String col7 = rs.getString(7);
				resultado += col1 + "|" + col2 + "|" + col3 + "|" + col4 + "|" + col5 + "|" + col6 + "|" + col7 + "\n";
			}
			//System.out.println(resultado);
			return resultado;
		} else {
			throw new AssertionError("La consulta ejecutada del metodo obtieneRegistrosPlantillaReseteadaBD no devolvio resultados");
		}
	}

	/**
	 * Metodo de espera para el boton + Crear Nueva Plantilla
	 * @author Marcos Morales
	 */
	public void esperaBotonCrearPlantilla() {
		waitForVisibility(btnPlusPlantilla, Duration.ofSeconds(30));
	}

	/**
	 * Hacer click en el campo Buscar por plantilla
	 * @author Marcos Morales
	 */
	public void clickBusquedaPlantilla() {
		au.click(txtBusqueda);
	}

	/**
	 * Metodo de espera para el campo de Busqueda de plantilla
	 * @author Marcos Morales
	 */
	public void esperaCampoDeBusqueda() {
		waitForVisibility(txtBusqueda, Duration.ofSeconds(30));
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