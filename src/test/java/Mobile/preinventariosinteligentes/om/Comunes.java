package Mobile.preinventariosinteligentes.om;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.AppiumBy;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class Comunes {
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;

	/**
	 * Constructor de la clase Comunes
	 * @author Marcos Morales
	 * @param au (AppiumUtil): objeto de la clase 
	 * @param testCase (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 */
	public Comunes(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
		this.testCase = testCase;
		this.au = au;
		this.baseExecution = baseExecution;
	}

	/*
	 * Localizadores
	 * */
	private By lblSemana = By.xpath("//android.widget.TextView[@text=\"En curso\"]");
	private By btnContinuar = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_wkl_continue");
	private By lblProveedor = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/rbtn_preinventory_supplier");
	private By lblPlanograma = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/rbtn_preinventory_planogram");
	private By lblCategorias = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/rbtn_preinventory_categories");
	private By lblMensaje = By.id("android:id/message");
	private By btnAceptar = By.id("android:id/button1");
	private By btnCancelar = By.id("android:id/button2");
	private By btnX = By.xpath("//android.widget.ImageButton[@content-desc=\"Navegar hacia arriba\"]");
	private By loading = By.xpath("//android.widget.TextView[@text=\"Cargando\"]");


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
	 * Identifica si un elemento es localizable
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
	 * Seleccionar semana En Curso
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void seleccionarSemana(boolean screenShot) {
		waitForVisibility(lblSemana, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(lblSemana);
	}

	/**
	 * Seleccionar Clasificacion
	 * @author Marcos Morales
	 * @param clasificacion Tipo de clasificacion: proveedor, planograma, categorias
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void seleccionarClasificacion(String clasificacion, boolean screenShot) {
		switch (clasificacion.toLowerCase()) {
		case "proveedor":
			waitForVisibility(lblProveedor, Duration.ofSeconds(30));
			takeScreenshot(screenShot);
			au.click(lblProveedor);
			break;
		case "planograma":
			waitForVisibility(lblPlanograma, Duration.ofSeconds(30));
			takeScreenshot(screenShot);
			au.click(lblPlanograma);
			break;
		case "categorias":
			waitForVisibility(lblCategorias, Duration.ofSeconds(30));
			takeScreenshot(screenShot);
			au.click(lblCategorias);
			break;
		default:
			throw new IllegalArgumentException("Opcion Invalida: " + clasificacion);
		}
	}

	/**
	 * Accion de espera para interactuar con el boton Crear Plantilla
	 * @author Marcos Morales
	 */
	public void esperaBtnCrearPlantilla() {
		waitForVisibility(btnAceptar, Duration.ofSeconds(30));
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
	 * Hacer click en el Botón Aceptar
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void clickBtnAceptar(boolean screenShot) {
		waitForVisibility(btnAceptar, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(btnAceptar);
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
	 * Hacer click en el Botón X de la parte superior izquierda de la pantalla
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void clickBtnX(boolean screenShot) {
		waitForVisibility(btnX, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(btnX);
	}

	/**
	 * Hacer click en el Botón Aceptar de ventana emergente Conteos Faltantes (solo se muestra en viernes, sabado y domingo)
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void clickBtnAceptarConteosFaltantes(boolean screenShot) {
		boolean isWeekend = isWeekend();
		if (isWeekend) {
			waitForVisibility(btnAceptar, Duration.ofSeconds(30));
			takeScreenshot(screenShot);
			au.click(btnAceptar);
		}
	}

	/**
	 * Metodo para validar los mensajes de tipo toast
	 * @author Marcos Morales
	 * @param mensaje Mensaje a validar
	 */
	public boolean validarMensajeToast(String mensaje) {
		return isElementPresent(By.xpath("//android.widget.Toast[@text=\"" + mensaje + "\"]"));
	}

	/**
	 * Espera a que desaparezca el mensaje toast
	 * @author Marcos Morales
	 */
	public void esperaSeOculteMensajeToast(String mensaje) {
		waitForHidden(By.xpath("//android.widget.Toast[@text=\"" + mensaje + "\"]"), Duration.ofSeconds(30));
	}

	/**
	 * Metodo para validar el mensaje mostrado en la ventana para Aceptar la Consolidación
	 * @author Marcos Morales
	 */
	public String obtenerMensajeConsolidacion() {
		return au.driver.findElement(lblMensaje).getText();
	}

	/**
	 * Identifica si el dia actual es Viernes, Sabado o Domingo
	 * @author Marcos Morales
	 * @return true/false 
	 */
	public static boolean isWeekend() {
		Calendar cal = Calendar.getInstance();
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

		switch (dayOfWeek) {
		case Calendar.FRIDAY:
		case Calendar.SATURDAY:
		case Calendar.SUNDAY:
			return true;
		default:
			return false;
		}
	}

	/**
	 * Realiza la limpieza de todas las tablas para Preinventarios Inteligentes MVP3
	 * @param semana Parametros validos: "actual", "anterior"
	 * @author Marcos Morales
	 */
	public void limpiarTablasMVP3(String semana) {
		Connection connection = null;
		Statement statement = null;

		try {
			connection = DatabaseUtil.obtenerConexion();

			statement = connection.createStatement();

			String queryActual = "BEGIN TRY\r\n"
					+ "	SET ANSI_NULLS ON;\r\n"
					+ "	SET QUOTED_IDENTIFIER ON;\r\n"
					+ "	SET NOCOUNT ON;\r\n"
					+ "	BEGIN TRANSACTION;\r\n"
					+ "\r\n"
					+ "		declare @numSemana int;\r\n"
					+ "		set @numSemana = DATEPART(ISO_WEEK, GETDATE());\r\n"
					+ "\r\n"
					+ "		UPDATE [HC].[II_HEADER] SET II_CLASSIFICATION ='' ,II_STATUS = 1, II_AUTOMATIC_ROLE_ASSIGNMENT_DATE = NULL\r\n"
					+ "		WHERE YEAR(II_STARTDATE) = YEAR(GETDATE())\r\n"
					+ "		AND II_WEEKNUMBER = @numSemana;\r\n"
					+ "\r\n"
					+ "		DELETE FROM [HC].[II_DETAIL]\r\n"
					+ "		WHERE YEAR(TM_CRT) = YEAR(GETDATE())\r\n"
					+ "		AND DATEPART(ISO_WEEK, TM_CRT) = @numSemana;\r\n"
					+ "\r\n"
					+ "		DELETE FROM HC.IO_DETAIL\r\n"
					+ "		WHERE YEAR(CREATED_DATETIME) = YEAR(GETDATE())\r\n"
					+ "		AND DATEPART(ISO_WEEK, CREATED_DATETIME)  = @numSemana;\r\n"
					+ "\r\n"
					+ "		DELETE FROM [HC].[II_HEADER_IO_HEADER]\r\n"
					+ "		WHERE YEAR(TM_CRT) = YEAR(GETDATE())\r\n"
					+ "		AND DATEPART(ISO_WEEK, TM_CRT) = @numSemana;\r\n"
					+ "\r\n"
					+ "		DELETE FROM HC.IO_HEADER\r\n"
					+ "		WHERE YEAR(CREATED_DATETIME) = YEAR(GETDATE())\r\n"
					+ "		AND DATEPART(ISO_WEEK, CREATED_DATETIME)  = @numSemana;\r\n"
					+ "\r\n"
					+ "		DELETE FROM [HC].[II_ASSIGNMENTROLE]\r\n"
					+ "		WHERE YEAR(TM_CRT) = YEAR(GETDATE())\r\n"
					+ "		AND DATEPART(ISO_WEEK, TM_CRT) = @numSemana;\r\n"
					+ "\r\n"
					+ "		DELETE FROM [HC].TEMPLATE_COLLABORATOR_RELATIONSHIP\r\n"
					+ "		WHERE YEAR(TM_CRT) = YEAR(GETDATE())\r\n"
					+ "		AND DATEPART(ISO_WEEK, TM_CRT) = @numSemana;\r\n"
					+ "\r\n"
					+ "		DELETE FROM [HC].[II_STORE_TEMPLATE_DETAIL]\r\n"
					+ "		WHERE YEAR(TM_CRT) = YEAR(GETDATE())\r\n"
					//+ "		AND DATEPART(ISO_WEEK, TM_CRT) = @numSemana;\r\n"
					+ "\r\n"
					+ "		DELETE FROM [HC].[II_STORE_TEMPLATE_HEADER]\r\n"
					+ "		WHERE YEAR(TM_CRT) = YEAR(GETDATE())\r\n"
					//+ "		AND DATEPART(ISO_WEEK, TM_CRT) = @numSemana;\r\n"
					+ "\r\n"
					+ "		DELETE FROM [HC].[TEMPLATE_SELF_GENERATED_COUNTS_DETAIL]\r\n"
					//+ "		WHERE YEAR(TM_CRT) = YEAR(GETDATE())\r\n"
					//+ "		AND DATEPART(ISO_WEEK, TM_CRT) = @numSemana;\r\n"
					+ "\r\n"
					+ "		DELETE FROM [HC].[TEMPLATE_SELF_GENERATED_COUNTS_HEADER]\r\n"
					//+ "		WHERE YEAR(TM_CRT) = YEAR(GETDATE())\r\n"
					//+ "		AND DATEPART(ISO_WEEK, TM_CRT) = @numSemana;\r\n"
					+ "\r\n"
					+ "		DELETE FROM [HC].[II_ASSIGNMENT_HISTORY]\r\n"
					+ "		WHERE YEAR(TM_CRT) =YEAR(GETDATE()) \r\n"
					+ "		AND DATEPART(ISO_WEEK, TM_CRT) = @numSemana;\r\n"
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

			String queryAnterior = "BEGIN TRY\r\n"
					+ "	SET ANSI_NULLS ON;\r\n"
					+ "	SET QUOTED_IDENTIFIER ON;\r\n"
					+ "	SET NOCOUNT ON;\r\n"
					+ "	BEGIN TRANSACTION;\r\n"
					+ "\r\n"
					+ "		declare @numSemana int;\r\n"
					+ "		set @numSemana = DATEPART(ISO_WEEK, GETDATE())-1;\r\n"
					+ "\r\n"
					+ "		UPDATE [HC].[II_HEADER] SET II_CLASSIFICATION ='' ,II_STATUS = 2, II_AUTOMATIC_ROLE_ASSIGNMENT_DATE = NULL\r\n"
					+ "		WHERE YEAR(II_STARTDATE) = YEAR(GETDATE())\r\n"
					+ "		AND II_WEEKNUMBER = @numSemana;\r\n"
					+ "\r\n"
					+ "		DELETE FROM [HC].[II_DETAIL]\r\n"
					+ "		WHERE YEAR(TM_CRT) = YEAR(GETDATE())\r\n"
					+ "		AND DATEPART(ISO_WEEK, TM_CRT) = @numSemana;\r\n"
					+ "\r\n"
					+ "		DELETE FROM HC.IO_DETAIL\r\n"
					+ "		WHERE YEAR(CREATED_DATETIME) = YEAR(GETDATE())\r\n"
					+ "		AND DATEPART(ISO_WEEK, CREATED_DATETIME)  = @numSemana;\r\n"
					+ "\r\n"
					+ "		DELETE FROM [HC].[II_HEADER_IO_HEADER]\r\n"
					+ "		WHERE YEAR(TM_CRT) = YEAR(GETDATE())\r\n"
					+ "		AND DATEPART(ISO_WEEK, TM_CRT) = @numSemana;\r\n"
					+ "\r\n"
					+ "		DELETE FROM HC.IO_HEADER\r\n"
					+ "		WHERE YEAR(CREATED_DATETIME) = YEAR(GETDATE())\r\n"
					+ "		AND DATEPART(ISO_WEEK, CREATED_DATETIME)  = @numSemana;\r\n"
					+ "\r\n"
					+ "		DELETE FROM [HC].[II_ASSIGNMENTROLE]\r\n"
					+ "		WHERE YEAR(TM_CRT) = YEAR(GETDATE())\r\n"
					+ "		AND DATEPART(ISO_WEEK, TM_CRT) = @numSemana;\r\n"
					+ "\r\n"
					+ "		DELETE FROM [HC].TEMPLATE_COLLABORATOR_RELATIONSHIP\r\n"
					+ "		WHERE YEAR(TM_CRT) = YEAR(GETDATE())\r\n"
					+ "		AND DATEPART(ISO_WEEK, TM_CRT) = @numSemana;\r\n"
					+ "\r\n"
					+ "		DELETE FROM [HC].[II_STORE_TEMPLATE_DETAIL]\r\n"
					+ "		WHERE YEAR(TM_CRT) = YEAR(GETDATE())\r\n"
					//+ "		AND DATEPART(ISO_WEEK, TM_CRT) = @numSemana;\r\n"
					+ "\r\n"
					+ "		DELETE FROM [HC].[II_STORE_TEMPLATE_HEADER]\r\n"
					+ "		WHERE YEAR(TM_CRT) = YEAR(GETDATE())\r\n"
					//+ "		AND DATEPART(ISO_WEEK, TM_CRT) = @numSemana;\r\n"
					+ "\r\n"
					+ "		DELETE FROM [HC].[TEMPLATE_SELF_GENERATED_COUNTS_DETAIL]\r\n"
					//+ "		WHERE YEAR(TM_CRT) = YEAR(GETDATE())\r\n"
					//+ "		AND DATEPART(ISO_WEEK, TM_CRT) = @numSemana;\r\n"
					+ "\r\n"
					+ "		DELETE FROM [HC].[TEMPLATE_SELF_GENERATED_COUNTS_HEADER]\r\n"
					//+ "		WHERE YEAR(TM_CRT) = YEAR(GETDATE())\r\n"
					//+ "		AND DATEPART(ISO_WEEK, TM_CRT) = @numSemana;\r\n"
					+ "\r\n"
					+ "		DELETE FROM [HC].[II_ASSIGNMENT_HISTORY]\r\n"
					+ "		WHERE YEAR(TM_CRT) =YEAR(GETDATE()) \r\n"
					+ "		AND DATEPART(ISO_WEEK, TM_CRT) = @numSemana;\r\n"
					+ "\r\n"
					+ "		DELETE FROM [HC].[II_HEADER] WHERE II_FOLIO < 0\r\n"
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

			switch (semana.toLowerCase()) {
			case "actual":
				statement.executeUpdate(queryActual);
				System.out.println("FINALIZA LIMPIEZA DE TABLAS - SEMANA ACTUAL");
				break;
			case "anterior":
				statement.executeUpdate(queryAnterior);
				System.out.println("FINALIZA LIMPIEZA DE TABLAS - SEMANA ANTERIOR");
				break;
			}
		} catch (SQLException e) {
			System.out.println("Error executing SQL query: " + e.getMessage());
		} finally {
			try {
				if (statement != null) statement.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {
				System.out.println("Error closing resources: " + e.getMessage());
			}
		}
	}

	/**
	 * Realiza un UPDATE a NULOS para todos los conteos de la Semana en Conteos Inteligentes
	 * @author Marcos Morales
	 */
	public void updateConteosInteligentes() {
		Connection connection = null;
		Statement statement = null;

		try {
			connection = DatabaseUtil.obtenerConexion();

			statement = connection.createStatement();

			String query = "update d\r\n"
					+ "set d.IO_OnHandReal = null, d.IO_Dif = null, d.IO_DISABLED = 2, d.IO_PRE_VTA = 0.00\r\n"
					+ "FROM HC.IO_DETAIL d\r\n"
					+ "inner join hc.II_HEADER_IO_HEADER hh on d.IO_Folio = hh.IO_FOLIO\r\n"
					+ "WHERE YEAR(CREATED_DATETIME) = YEAR(GETDATE())\r\n"
					+ "AND DATEPART(ISO_WEEK, CREATED_DATETIME) = DATEPART(WEEK, GETDATE())\r\n"
					+ "and hh.II_FOLIO = (SELECT II_FOLIO FROM [HC].[II_HEADER] WHERE YEAR(II_STARTDATE) = YEAR(GETDATE()) AND II_WEEKNUMBER = DATEPART(WEEK, GETDATE()) AND II_TYPE = 'I')\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "delete hc.II_DETAIL\r\n"
					+ "where IO_FOLIO in (\r\n"
					+ "SELECT d.IO_FOLIO FROM hc.II_DETAIL d\r\n"
					+ "inner join hc.II_HEADER_IO_HEADER hh on d.IO_Folio = hh.IO_FOLIO\r\n"
					+ "WHERE YEAR(d.TM_CRT) = YEAR(GETDATE())\r\n"
					+ "AND DATEPART(ISO_WEEK, d.TM_CRT) = DATEPART(WEEK, GETDATE())\r\n"
					+ "and hh.II_FOLIO = (SELECT II_FOLIO FROM [HC].[II_HEADER] WHERE YEAR(II_STARTDATE) = YEAR(GETDATE()) AND II_WEEKNUMBER = DATEPART(WEEK, GETDATE()) AND II_TYPE = 'I')\r\n"
					+ ")";

			statement.executeUpdate(query);

		} catch (SQLException e) {
			System.out.println("Error executing SQL query: " + e.getMessage());
		} finally {
			try {
				if (statement != null) statement.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {
				System.out.println("Error closing resources: " + e.getMessage());
			}
		}
	}

	/**
	 * Realiza el borrado de las Plantillas y conteos creados con Automatizacion
	 * @author Marcos Morales
	 */
	public void borraPlantillasConteos() {
		Connection connection = null;
		Statement statement = null;

		try {
			connection = DatabaseUtil.obtenerConexion();

			statement = connection.createStatement();

			String query = "delete hc.io_detail\r\n"
					+ "where IO_FOLIO in (SELECT distinct(d.IO_Folio) FROM HC.IO_DETAIL d\r\n"
					+ "inner join hc.II_HEADER_IO_HEADER hh on d.IO_Folio = hh.IO_FOLIO\r\n"
					+ "inner join hc.TEMPLATE_SELF_GENERATED_COUNTS_HEADER thd on hh.II_FOLIO = thd.II_FOLIO\r\n"
					+ "inner join hc.TEMPLATE_SELF_GENERATED_COUNTS_DETAIL tdt on thd.ID = tdt.TEMPLATE_HEADER_ID and d.IO_SKU_REF = tdt.SKU_REF\r\n"
					+ "WHERE YEAR(CREATED_DATETIME) = YEAR(GETDATE())\r\n"
					+ "AND DATEPART(ISO_WEEK, CREATED_DATETIME) = DATEPART(WEEK, GETDATE())\r\n"
					+ "and hh.II_FOLIO = (SELECT II_FOLIO FROM [HC].[II_HEADER] WHERE YEAR(II_STARTDATE) = YEAR(GETDATE()) AND II_WEEKNUMBER = DATEPART(WEEK, GETDATE()) AND II_TYPE = 'P')\r\n"
					+ "and thd.TEMPLATE_NAME like '%ATC%')\r\n"
					+ "\r\n"
					+ "delete hc.II_DETAIL\r\n"
					+ "where IO_FOLIO in (SELECT distinct(d.IO_Folio) FROM HC.IO_DETAIL d\r\n"
					+ "inner join hc.II_HEADER_IO_HEADER hh on d.IO_Folio = hh.IO_FOLIO\r\n"
					+ "inner join hc.TEMPLATE_SELF_GENERATED_COUNTS_HEADER thd on hh.II_FOLIO = thd.II_FOLIO\r\n"
					+ "inner join hc.TEMPLATE_SELF_GENERATED_COUNTS_DETAIL tdt on thd.ID = tdt.TEMPLATE_HEADER_ID and d.IO_SKU_REF = tdt.SKU_REF\r\n"
					+ "WHERE YEAR(CREATED_DATETIME) = YEAR(GETDATE())\r\n"
					+ "AND DATEPART(ISO_WEEK, CREATED_DATETIME) = DATEPART(WEEK, GETDATE())\r\n"
					+ "and hh.II_FOLIO = (SELECT II_FOLIO FROM [HC].[II_HEADER] WHERE YEAR(II_STARTDATE) = YEAR(GETDATE()) AND II_WEEKNUMBER = DATEPART(WEEK, GETDATE()) AND II_TYPE = 'P')\r\n"
					+ "and thd.TEMPLATE_NAME like '%ATC%')\r\n"
					+ "\r\n"
					+ "delete from hc.TEMPLATE_SELF_GENERATED_COUNTS_DETAIL\r\n"
					+ "where TEMPLATE_HEADER_ID in (select ID from hc.TEMPLATE_SELF_GENERATED_COUNTS_HEADER\r\n"
					+ "where TEMPLATE_NAME like '%ATC%')\r\n"
					+ "\r\n"
					+ "delete from hc.TEMPLATE_SELF_GENERATED_COUNTS_HEADER\r\n"
					+ "where TEMPLATE_NAME like '%ATC%'";

			statement.executeUpdate(query);

		} catch (SQLException e) {
			System.out.println("Error executing SQL query: " + e.getMessage());
		} finally {
			try {
				if (statement != null) statement.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {
				System.out.println("Error closing resources: " + e.getMessage());
			}
		}
	}

	/**
	 * Realiza el borrado de las Plantillas y conteos creados con Automatizacion por plantilla
	 * @author Marcos Morales
	 * @param nomPlantilla Nombre de la plantilla
	 */
	public void borraPlantillasConteos(String nomPlantilla) {
		Connection connection = null;
		Statement statement = null;

		try {
			connection = DatabaseUtil.obtenerConexion();

			statement = connection.createStatement();

			String query = "delete hc.io_detail\r\n"
					+ "where IO_FOLIO in (SELECT distinct(d.IO_Folio) FROM HC.IO_DETAIL d\r\n"
					+ "inner join hc.II_HEADER_IO_HEADER hh on d.IO_Folio = hh.IO_FOLIO\r\n"
					+ "inner join hc.TEMPLATE_SELF_GENERATED_COUNTS_HEADER thd on hh.II_FOLIO = thd.II_FOLIO\r\n"
					+ "inner join hc.TEMPLATE_SELF_GENERATED_COUNTS_DETAIL tdt on thd.ID = tdt.TEMPLATE_HEADER_ID and d.IO_SKU_REF = tdt.SKU_REF\r\n"
					+ "WHERE YEAR(CREATED_DATETIME) = YEAR(GETDATE())\r\n"
					+ "AND DATEPART(ISO_WEEK, CREATED_DATETIME) = DATEPART(WEEK, GETDATE())\r\n"
					+ "and hh.II_FOLIO = (SELECT II_FOLIO FROM [HC].[II_HEADER] WHERE YEAR(II_STARTDATE) = YEAR(GETDATE()) AND II_WEEKNUMBER = DATEPART(WEEK, GETDATE()) AND II_TYPE = 'P')\r\n"
					+ "and thd.TEMPLATE_NAME like '%" + nomPlantilla + "%')\r\n"
					+ "\r\n"
					+ "delete hc.II_DETAIL\r\n"
					+ "where IO_FOLIO in (SELECT distinct(d.IO_Folio) FROM HC.IO_DETAIL d\r\n"
					+ "inner join hc.II_HEADER_IO_HEADER hh on d.IO_Folio = hh.IO_FOLIO\r\n"
					+ "inner join hc.TEMPLATE_SELF_GENERATED_COUNTS_HEADER thd on hh.II_FOLIO = thd.II_FOLIO\r\n"
					+ "inner join hc.TEMPLATE_SELF_GENERATED_COUNTS_DETAIL tdt on thd.ID = tdt.TEMPLATE_HEADER_ID and d.IO_SKU_REF = tdt.SKU_REF\r\n"
					+ "WHERE YEAR(CREATED_DATETIME) = YEAR(GETDATE())\r\n"
					+ "AND DATEPART(ISO_WEEK, CREATED_DATETIME) = DATEPART(WEEK, GETDATE())\r\n"
					+ "and hh.II_FOLIO = (SELECT II_FOLIO FROM [HC].[II_HEADER] WHERE YEAR(II_STARTDATE) = YEAR(GETDATE()) AND II_WEEKNUMBER = DATEPART(WEEK, GETDATE()) AND II_TYPE = 'P')\r\n"
					+ "and thd.TEMPLATE_NAME like '%" + nomPlantilla + "%')\r\n"
					+ "\r\n"
					+ "delete from hc.TEMPLATE_SELF_GENERATED_COUNTS_DETAIL\r\n"
					+ "where TEMPLATE_HEADER_ID in (select ID from hc.TEMPLATE_SELF_GENERATED_COUNTS_HEADER\r\n"
					+ "where TEMPLATE_NAME like '%" + nomPlantilla + "%')\r\n"
					+ "\r\n"
					+ "delete from hc.TEMPLATE_SELF_GENERATED_COUNTS_HEADER\r\n"
					+ "where TEMPLATE_NAME like '%" + nomPlantilla + "%'";

			statement.executeUpdate(query);

		} catch (SQLException e) {
			System.out.println("Error executing SQL query: " + e.getMessage());
		} finally {
			try {
				if (statement != null) statement.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {
				System.out.println("Error closing resources: " + e.getMessage());
			}
		}
	}

	/**
	 * Lanza la aplicacion
	 * @author Marcos Morales
	 * @param appPackage Ej. com.femco.oxxo.operaciones.mitienda.debug
	 * @param appActivity Ej. com.femco.oxxo.operaciones.mitienda.ui.login.LoginActivity
	 */
	public void iniciarApp(String appPackage, String appActivity) throws InterruptedException {
		au.driver.activateApp(appPackage + "/" + appActivity);
		Thread.sleep(2000);
	}

	/**
	 * Cierra la aplicación
	 * @author Marcos Morales
	 * @param appPackage Ej. com.femco.oxxo.operaciones.mitienda.debug
	 */
	public void finalizarApp(String appPackage) throws IOException, InterruptedException {
		Runtime.getRuntime().exec("adb shell am force-stop \"" + appPackage + "\"");
		Thread.sleep(2000);
	}

	/**
	 * Genera la cadena de texto que se muestra en el mensaje para Aceptar la Consolidación
	 * @author Marcos Morales
	 */
	public static String generarCadenaSemanaActual() {
		Locale locale = new Locale("es", "ES");
		LocalDate hoy = LocalDate.now();

		// Obtener el número de la semana actual
		WeekFields weekFields = WeekFields.of(locale);
		int numeroSemana = hoy.get(weekFields.weekOfWeekBasedYear());

		// Obtener el lunes de la semana actual
		LocalDate lunes = hoy.with(weekFields.dayOfWeek(), 1);

		// Obtener el domingo de la semana actual
		LocalDate domingo = lunes.plusDays(6);

		// Formatear las fechas
		DateTimeFormatter formateador = DateTimeFormatter.ofPattern("d 'de' MMM. yyyy", locale);
		String fechaDomingo = domingo.format(formateador);

		// Construir la cadena final
		return String.format("¿Deseas consolidar los cambios en el Preinventario de la Semana %d %d - %s?", numeroSemana, lunes.getDayOfMonth(), fechaDomingo);
	}

	/**
	 * Espera a que desaparezca el loading
	 * @author Marcos Morales
	 */
	public void esperaSeOculteLoading() {
		waitForHidden(loading, Duration.ofSeconds(30));
	}

	/**
	 * Compara dos ResultSets y determina si contienen los mismos valores
	 * @param rs1 Primer ResultSet a comparar
	 * @param rs2 Segundo ResultSet a comparar
	 * @return true si los ResultSets son diferentes, false si son iguales
	 * @throws SQLException si hay un error al acceder a los ResultSets
	 */
	public static boolean areResultSetsDifferent(ResultSet rs1, ResultSet rs2) throws SQLException {
		if (rs1 == null || rs2 == null) {
			throw new IllegalArgumentException("Los ResultSets no pueden ser null");
		}

		// Obtener los metadatos de ambos ResultSets
		ResultSetMetaData rsmd1 = rs1.getMetaData();
		ResultSetMetaData rsmd2 = rs2.getMetaData();

		// Verificar si tienen el mismo número de columnas
		if (rsmd1.getColumnCount() != rsmd2.getColumnCount()) {
			return true;
		}

		// Convertir ResultSets a List de Maps para facilitar la comparación
		List<Map<String, Object>> list1 = resultSetToList(rs1);
		List<Map<String, Object>> list2 = resultSetToList(rs2);

		// Verificar si tienen el mismo número de filas
		if (list1.size() != list2.size()) {
			return true;
		}

		// Comparar cada fila
		for (int i = 0; i < list1.size(); i++) {
			Map<String, Object> row1 = list1.get(i);
			Map<String, Object> row2 = list2.get(i);

			if (!row1.equals(row2)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Convierte un ResultSet en una Lista de Maps
	 * @param rs ResultSet a convertir
	 * @return Lista de Maps donde cada Map representa una fila
	 * @throws SQLException si hay un error al acceder al ResultSet
	 */
	private static List<Map<String, Object>> resultSetToList(ResultSet rs) throws SQLException {
		List<Map<String, Object>> rows = new ArrayList<>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();

		while (rs.next()) {
			Map<String, Object> row = new HashMap<>();
			for (int i = 1; i <= columnCount; i++) {
				String columnName = rsmd.getColumnName(i);
				Object value = rs.getObject(i);
				row.put(columnName, value);
			}
			rows.add(row);
		}

		return rows;
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
	 * Método que realiza un scroll vertical al inicio de la pantalla
	 * @author Marcos Morales
	 */
	public void scrollToBeginning() {
		au.driver.findElement(AppiumBy.androidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true))" +
				".scrollToBeginning(10)"));
	}
}