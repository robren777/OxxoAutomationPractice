package Mobile.preinventariosinteligentes.om;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.google.common.collect.ImmutableMap;

import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class RolesInteligentes {
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;

	/**
	 * Constructor de la clase RolesInteligentes
	 * @author Marcos Morales
	 * @param au (AppiumUtil): objeto de la clase 
	 * @param testCase (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 */
	public RolesInteligentes(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
		this.testCase = testCase;
		this.au = au;
		this.baseExecution = baseExecution;
	}

	/*
	 * Localizadores
	 * */
	private By scrTitle = By.xpath("//android.widget.TextView[@text=\"ROLES INTELIGENTES\"]");
	private By turno3 = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tv_roles_turn_3");
	private By btnRolesContinue = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_roles_continue");
	private By cbRoles = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/sp_rols");
	private By cbSelectivos = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/sp_group");
	private By rol = By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_gender_name\"])[2]");
	private By selectivo = By.xpath("//android.widget.ListView/android.view.ViewGroup[4]");
	private By btnContinuar = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_continue");
	private By msgTitle = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tv_title_template");
	private By msgTemplate = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tv_message_template");
	private By msgPlantilla = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tv_message_automatic_assignment");
	private By btnchangeAssignment = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_change_assignment");
	private By btnhistory = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_history");


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
	 * Hacer click en botón Continuar
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void clickBtnContinuar(boolean screenShot) {
		waitForVisibility(btnRolesContinue, Duration.ofSeconds(30));
		if (screenShot) {
			testCase.addScreenShotCurrentStep(au, "testImage");
		}
		au.click(btnRolesContinue);
	}

	/**
	 * Extrae el texto del elemento de un mensaje en Roles Inteligentes
	 * @author Marcos Morales
	 */
	public String obtenerMensaje() {
		waitForVisibility(msgPlantilla, Duration.ofSeconds(30));
		return au.driver.findElement(msgPlantilla).getText();
	}

	/**
	 * Devuelve el contenido de los mensajes desplegados en los Pasos 1 y 2 del flujo para ingresar a un conteo
	 * @author Lizbeth Hernández
	 */

	public String[] retornaMensajePasos(boolean screenShot) {
		String msjTitle = au.driver.findElement(msgTitle).getText();
		String msjTemplate = au.driver.findElement(msgTemplate).getText();

		if (screenShot) {
			testCase.addScreenShotCurrentStep(au, "testImage");
		}

		return new String[]{msjTitle, msjTemplate};
	}

	/**
	 * Método para ocultar el teclado
	 * @author Marcos Morales 
	 */
	public void ocultarTeclado() {
		au.driver.hideKeyboard();
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/**
	 * Hacer Click en el combo SELECCIONAR EL ENCARGADO
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void clickCbRoles(boolean screenShot) {
		waitForVisibility(cbRoles, Duration.ofSeconds(30));
		if (screenShot) {
			testCase.addScreenShotCurrentStep(au, "testImage");
		}
		au.click(cbRoles);
	}

	/**
	 * Hacer Click en el combo Selecciona el selectivo 
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void clickCbSelectivos(boolean screenShot) {
		waitForVisibility(cbSelectivos, Duration.ofSeconds(30));
		if (screenShot) {
			testCase.addScreenShotCurrentStep(au, "testImage");
		}
		au.click(cbSelectivos);
	}

	/**
	 * SELECCIONA AL ENCARGADO
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void seleccionarRol(boolean screenShot) {
		waitForVisibility(rol, Duration.ofSeconds(30));
		if (screenShot) {
			testCase.addScreenShotCurrentStep(au, "testImage");
		}
		au.click(rol);
	}

	/**
	 * Selecciona el Selectivo a reasignar 
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void seleccionarSelectivo(boolean screenShot) {
		waitForVisibility(selectivo, Duration.ofSeconds(30));
		if (screenShot) {
			testCase.addScreenShotCurrentStep(au, "testImage");
		}
		au.click(selectivo);
	}

	/**
	 * Selecciona los Selectivo a reasignar 
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void seleccionarSelectivos(int n, boolean screenShot) {
		waitForVisibility(selectivo, Duration.ofSeconds(30));
		for (int i = 0; i < n; i++) {

			boolean existeSelectivo = isElementPresent(By.xpath("//android.widget.ListView/android.view.ViewGroup[" + n + "]"));

			if (existeSelectivo) {
				System.out.println("Selectivo visible en pantalla" );
				if (screenShot) {
					testCase.addScreenShotCurrentStep(au, "testImage");
				}
				WebElement element = au.driver.findElement(By.xpath("//android.widget.ListView/android.view.ViewGroup[" + n + "]"));
				element.click();     
			} else {
				System.out.println("No se encuentra selectivo" );
			}
		}
	}

	/**
	 * Hacer click en botón Continuar con el cambio de selectivo
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void clickBtnContinuarCambioSelectivo(boolean screenShot) {
		waitForVisibility(btnContinuar, Duration.ofSeconds(30));
		if (screenShot) {
			testCase.addScreenShotCurrentStep(au, "testImage");
		}
		au.click(btnContinuar);
	}

	/**
	 * Simplificacion de los pasos par llevar a cabo la asignacion de un rol
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void asignarRol(boolean screenShot) {
		clickCbRoles(screenShot);
		seleccionarRol(screenShot);
		clickCbSelectivos(screenShot);
		seleccionarSelectivo(screenShot);
		clickBtnContinuarCambioSelectivo(screenShot);
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
	public boolean isElementPresent(By locator) {
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
	 * Ejecuta la consulta en BD para obtenr los Roles Asignados
	 * @author Marcos Morales
	 */
	public ResultSet obtieneRolesAsignadosBD() {
		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			String query = "SET LANGUAGE Spanish;\r\n"
					+ ";WITH cte as (\r\n"
					+ "select	 OPERADOR_ID = II_OPERATOR_ID\r\n"
					+ "		,OPERADOR_NAME = (select top 1 COLLABORATOR_NAME from hc.II_STORE_TEMPLATE_DETAIL as dt where dt.COLLABORATOR_ID = II_OPERATOR_ID order by Id DESC)\r\n"
					+ "		,DIA = UPPER(DATENAME(weekday, ar.II_ASSIGNMENT_DATE))\r\n"
					+ "		,DIA_ID = (DATENAME(DAY, ar.II_ASSIGNMENT_DATE))\r\n"
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
					+ "select	 OPERADOR_ID\r\n"
					+ "		,OPERADOR_NAME\r\n"
					+ "		,DIA\r\n"
					+ "		,DIA_ID\r\n"
					+ "		,TURNO\r\n"
					+ "		,II_TURN_ID\r\n"
					+ "		,GRUPOS_ASIGNADOS_POR_TURNO = COUNT(GRUPO_ASIGNADO)\r\n"
					+ "		,SKUS_ASIGNADOS_POR_TURNO = SUM(SKUS_POR_GRUPO)\r\n"
					+ "FROM cte\r\n"
					+ "GROUP BY OPERADOR_ID, OPERADOR_NAME, DIA, DIA_ID, TURNO, II_TURN_ID\r\n"
					+ "ORDER BY DIA_ID, II_TURN_ID;";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Retorna true si el dia solcitado se encuentra en pantalla
	 * @param dia Dia en el calendario de asignaciones que se desea validar
	 * @author Marcos Morales
	 */
	public boolean validaDia(String dia) {
		return isElementPresent(By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitle\" and @text=\"" + dia + "\"]"));
	}

	/**
	 * Retorna true si el colaborador se encuentra en pantalla
	 * @param colaborador Colaborador en el calendario de asignaciones que se desea validar
	 * @author Marcos Morales
	 */
	public boolean validaColaborador(String colaborador) {
		return au.driver.findElement(By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvOwner\" and @text=\"" + colaborador + "\"])[1]")).isDisplayed();
	}

	/**
	 * Ejecuta un scroll en el calendario de asignaciones hasta el dia especificado
	 * @param dia Dia en el calendario hasta donde se realiza el scroll
	 * @author Marcos Morales
	 */
	public void scrollToDiaEnElCalendario(String dia) {
		By locator = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitle\" and @text=\"" + dia + "\"]");
		By objScrollable = By.className("android.widget.HorizontalScrollView");
		scrollToElement(locator, objScrollable, "right", 0.5);
	}

	/**
	 * Ejecuta un scroll en el calendario de asignaciones hasta un Colaborador
	 * @author Marcos Morales
	 */
	public void scrollToColaboradorEnElCalendario() {
		ResultSet resultSet = obtieneRolesAsignadosBD();
		try {
			while (resultSet.next()) {
				String dia = resultSet.getString(3);

				boolean existeDia = validaDia(dia);

				if (existeDia) {
					break;
				} else {
					scrollToDiaEnElCalendario(dia);
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo para ingresar a un rol en el calendario semanal
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void seleccionarColaborador(boolean screenShot) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DatabaseUtil.obtenerConexion();

			// Create a statement object
			statement = connection.createStatement();

			// Execute a SQL query
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

			if (resultSet.next()) {
				WebElement element;
				String colaborador = resultSet.getString(2);

				boolean existeColab = isElementPresent(By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvOwner\" and @text=\"" + colaborador + "\"])[1]"));
				if (existeColab) {
					element = au.driver.findElement(By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvOwner\" and @text=\"" + colaborador + "\"])[1]"));
					//Colaborador visible en pantalla
					if (screenShot) {
						testCase.addScreenShotCurrentStep(au, "testImage");
					}
					element.click();
				} else {
					//Colaborador NO visible en pantalla, debe hacer Scroll
					By locator = By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvOwner\" and @text=\"" + colaborador + "\"])[1]");
					By objScrollable = By.className("android.widget.HorizontalScrollView");
					scrollToElement(locator, objScrollable, "right", 0.5);

					element = au.driver.findElement(By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvOwner\" and @text=\"" + colaborador + "\"])[1]"));
					if (screenShot) {
						testCase.addScreenShotCurrentStep(au, "testImage");
					}
					element.click();
				}
			} else {
				throw new AssertionError("No se encontraron registros al ejecutar la consulta");
			}

		} catch (SQLException e) {
			System.out.println("Error executing SQL query: " + e.getMessage());
		} finally {
			// Close resources in the reverse order they were created
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
	 * Metodo para ingresar a un rol que cuente con un selectivo con 30 o mas skus
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void seleccionarColaborador30(boolean screenShot) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DatabaseUtil.obtenerConexion();

			// Create a statement object
			statement = connection.createStatement();

			// Execute a SQL query
			String query = ";WITH Selectivos AS (\r\n"
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
					+ "	where IIH.II_TYPE = 'I'\r\n"
					+ ")\r\n"
					+ "SELECT TOP 1 Folio\r\n"
					+ "		,Colaborador\r\n"
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
			resultSet = statement.executeQuery(query);

			if (resultSet.next()) {
				WebElement element;
				String colaborador = resultSet.getString(2);

				boolean existeColab = isElementPresent(By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvOwner\" and @text=\"" + colaborador + "\"])[1]"));
				if (existeColab) {
					element = au.driver.findElement(By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvOwner\" and @text=\"" + colaborador + "\"])[1]"));
					//Colaborador visible en pantalla
					if (screenShot) {
						testCase.addScreenShotCurrentStep(au, "testImage");
					}
					element.click();
				} else {
					//Colaborador NO visible en pantalla, debe hacer Scroll
					By locator = By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvOwner\" and @text=\"" + colaborador + "\"])[1]");
					By objScrollable = By.className("android.widget.HorizontalScrollView");
					scrollToElement(locator, objScrollable, "right", 0.5);

					element = au.driver.findElement(By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvOwner\" and @text=\"" + colaborador + "\"])[1]"));
					if (screenShot) {
						testCase.addScreenShotCurrentStep(au, "testImage");
					}
					element.click();
				}
			} else {
				throw new AssertionError("No se encontraron registros al ejecutar la consulta");
			}

		} catch (SQLException e) {
			System.out.println("Error executing SQL query: " + e.getMessage());
		} finally {
			// Close resources in the reverse order they were created
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
	 * Metodo para hacer click largo a un rol de los mostrados en el calendario semanal
	 * @author Marcos Morales
	 */
	public void clickLargoColaborador(boolean screenShot) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DatabaseUtil.obtenerConexion();

			// Create a statement object
			statement = connection.createStatement();

			// Execute a SQL query
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

			if (resultSet.next()) {
				String colaborador = resultSet.getString(2);
				if (screenShot) {
					testCase.addScreenShotCurrentStep(au, "testImage");
				}
				performLongClick(colaborador);
			} else {
				throw new AssertionError("No se encontraron registros al ejecutar la consulta");
			}

		} catch (SQLException e) {
			System.out.println("Error executing SQL query: " + e.getMessage());
		} finally {
			// Close resources in the reverse order they were created
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
	 * Realiza el click largo sobre un colaborador
	 * @author Marcos Morales
	 * @param colaborador Nombre del colaborador
	 */
	public void performLongClick(String colaborador) {
		// Find the element
		WebElement element = au.driver.findElement(By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvOwner\" and @text=\"" + colaborador + "\"])[1]"));

		// Cast driver to JavascriptExecutor
		JavascriptExecutor js = (JavascriptExecutor) au.driver;

		// Perform long click
		js.executeScript("mobile: longClickGesture", ImmutableMap.of(
				"elementId", ((RemoteWebElement) element).getId(),
				"duration", 2000
				));
	}

	/**
	 * Espera a que cargue la pantalla de Roles Inteligentes
	 * @author Marcos Morales
	 */
	public void esperaCarguePantallaRolesInt() {
		waitForVisibility(scrTitle, Duration.ofSeconds(30));
		waitForVisibility(turno3, Duration.ofSeconds(30));
	}

	/**
	 * Ejecuta el flujo para realizar la asignación Automática en Roles Inteligentes
	 * @author Lizbeth Hernandez
	 * @param user Usuario para ingresar a la aplicacion Oxxo al día
	 * @param pass Contraseña del usuario
	 * @param app Ej. com.femco.oxxo.operaciones.mitienda.debug
	 * @param clasificacion proveedor, planograma, categorias
	 * @param numColab 
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
		menus.ingresarRolesInteligentes(screenShot);

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
		//Seleccionar Dia y Turno
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
	 * Metodo para validar se muestre la opción Asignar Rol
	 * @author Lizbeth Hernandez
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public boolean retornaEstadoOpcionReasignar() {
		return au.driver.findElement(btnchangeAssignment).isDisplayed();
	}

	/**
	 * Identifica el selectivo original asignado a un colaborador
	 * @author Marcos Morales
	 */
	public ResultSet obtieneSelectivoOriginalBD() {
		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			String query = "SELECT	 ah.II_OPERATOR_ID_OLD\r\n"
					+ "		,p.NAME + ' ' + p.LASTNAME_PAT + ' ' + p.LASTNAME_MAT AS COLLABORATOR_NAME\r\n"
					+ "		,ah.II_IOFOLIO_NEW\r\n"
					+ "		,ioh.IO_Description\r\n"
					+ "FROM hc.II_ASSIGNMENT_HISTORY ah\r\n"
					+ "INNER JOIN XposStore.dbo.PERSON p ON ah.II_OPERATOR_ID_OLD = p.PERSON_ID\r\n"
					+ "INNER JOIN Satelites.HC.io_header ioh ON ah.II_IOFOLIO_OLD = ioh.IO_Folio;";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Genera la cadena de texto con el valor del selectivo original cuando
	 * se realizar un cambio de selectivo
	 * @author Marcos Morales
	 */
	public String selectivoOriginal(ResultSet resultSet) {
		String selectivoOLD = "";
		try {
			if (resultSet.next()) {
				selectivoOLD = resultSet.getString(4);
				System.out.println("Selectivo Original: " + selectivoOLD);
			} else {
				throw new AssertionError("No se encontraron registros al ejecutar la consulta");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return selectivoOLD;
	}

	/**
	 * Identifica el nuevo selectivo asignado a un colaborador
	 * @author Marcos Morales
	 */
	public ResultSet obtieneNuevoSelectivoBD() {
		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			String query = "SELECT	 ah.II_OPERATOR_ID_OLD\r\n"
					+ "		,p.NAME + ' ' + p.LASTNAME_PAT + ' ' + p.LASTNAME_MAT AS COLLABORATOR_NAME\r\n"
					+ "		,ah.II_IOFOLIO_NEW\r\n"
					+ "		,ioh.IO_Description\r\n"
					+ "FROM hc.II_ASSIGNMENT_HISTORY ah\r\n"
					+ "INNER JOIN XposStore.dbo.PERSON p ON ah.II_OPERATOR_ID_OLD = p.PERSON_ID\r\n"
					+ "INNER JOIN Satelites.HC.io_header ioh ON ah.II_IOFOLIO_NEW = ioh.IO_Folio;";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Genera la cadena de texto con el valor del nuevo selectivo cuando
	 * se realizar un cambio de selectivo
	 * @author Marcos Morales
	 */
	public String selectivoNuevo(ResultSet resultSet) {
		String selectivoNEW = "";
		try {
			if (resultSet.next()) {
				selectivoNEW = resultSet.getString(4);
				System.out.println("Selectivo Nuevo (Cambio): " + selectivoNEW);
			} else {
				throw new AssertionError("No se encontraron registros al ejecutar la consulta");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return selectivoNEW;
	}

	/**
	 * Ejecuta el flujo para realizar la reasignación de selectivo 
	 * @author Lizbeth Hernandez
	 *  @param user Usuario para ingresar a la aplicacion Oxxo al día
	 * @param pass Contraseña del usuario
	 * @param app Ej. com.femco.oxxo.operaciones.mitienda.debug
	 * @param clasificacion proveedor, planograma, categorias
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void prerequisitoReasignacion(String user, String pass, String app, String clasificacion, boolean screenShot) throws IOException, InterruptedException {
		Login login = new Login(testCase, au, baseExecution);
		Menus menus = new Menus(testCase, au, baseExecution);
		Comunes comunes = new Comunes(testCase, au, baseExecution);
		RolesInteligentes rolesInt = new RolesInteligentes(testCase, au, baseExecution);

		//Iniciar sesion
		login.login(user,pass);

		//Ingresar a Conteos Inteligentes
		menus.ingresarRolesInteligentes(screenShot);

		//Semana en curso
		comunes.seleccionarSemana(screenShot);

		//Continuar
		comunes.clickBtnContinuar(screenShot);
		rolesInt.scrollToColaboradorEnElCalendario();

		//Long press sobre un colaborador
		rolesInt.clickLargoColaborador(false);

		//Seleccionar encargado
		rolesInt.clickCbRoles(false);
		rolesInt.seleccionarRol(false);

		//Seleccionar el selectivo a reasignar
		rolesInt.clickCbSelectivos(false);
		rolesInt.seleccionarSelectivo(false);

		//Continuar
		rolesInt.clickBtnContinuarCambioSelectivo(false);

		//Cerrar Aplicacion
		comunes.finalizarApp(app);
	}

	/**
	 * Hacer Click en la opción HISTORIAL
	 * @author Lizbeth Hernandez
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void clicHistory(boolean screenShot) {
		waitForVisibility(btnhistory, Duration.ofSeconds(30));
		if (screenShot) {
			testCase.addScreenShotCurrentStep(au, "testImage");
		}
		au.click(btnhistory);
	}

	/**
	 * Obtiene las reasignaciones del selectivo en BD
	 * @author Lizbeth Hernandez
	 */
	public ResultSet consultaReasignacionesBD() {
		ResultSet resultSet = null;
		try {
			Connection connection = DatabaseUtil.obtenerConexion();

			Statement statement = connection.createStatement();

			// Execute a SQL query
			String query = "SELECT hc.II_ASSIGNMENT_HISTORY.II_OPERATOR_ID_OLD,\r\n" +
					"    XposStore.dbo.PERSON.NAME + ' ' + XposStore.dbo.PERSON.LASTNAME_PAT + ' ' + XposStore.dbo.PERSON.LASTNAME_MAT AS COLLABORATOR_NAME,\r\n" +
					"    hc.II_ASSIGNMENT_HISTORY.II_IOFOLIO_NEW,\r\n" +
					"    CASE hc.II_ASSIGNMENT_HISTORY.II_TURN_ID_OLD \r\n" +
					"        WHEN 1 THEN 'MAÑANA' \r\n" +
					"        WHEN 2 THEN 'TARDE' \r\n" +
					"        WHEN 3 THEN 'NOCHE' \r\n" +
					"    END AS TURNO,\r\n" +
					"    SUBSTRING(\r\n" +
					"        Satelites.HC.io_header.IO_Description, \r\n" +
					"        CHARINDEX('_', Satelites.HC.io_header.IO_Description) + 1, \r\n" +
					"        LEN(Satelites.HC.io_header.IO_Description) - CHARINDEX('_', Satelites.HC.io_header.IO_Description)\r\n" +
					"    ) AS IO_Description_Part\r\n" +
					"FROM \r\n" +
					"    hc.II_ASSIGNMENT_HISTORY\r\n" +
					"INNER JOIN \r\n" +
					"    XposStore.dbo.PERSON \r\n" +
					"    ON hc.II_ASSIGNMENT_HISTORY.II_OPERATOR_ID_OLD = XposStore.dbo.PERSON.PERSON_ID\r\n" +
					"INNER JOIN \r\n" +
					"    Satelites.HC.io_header \r\n" +
					"    ON hc.II_ASSIGNMENT_HISTORY.II_IOFOLIO_OLD = Satelites.HC.io_header.IO_Folio;";

			resultSet = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Realiza el formato del nombre del colaborador
	 * @author Lizbeth Hernandez
	 * @param parametro1
	 */

	public static String formatearNombre(String nombreCompleto) {
		if (nombreCompleto == null || nombreCompleto.isEmpty()) {
			return nombreCompleto;
		}

		String[] palabras = nombreCompleto.toLowerCase().split("\\s+");
		StringBuilder resultado = new StringBuilder();

		for (String palabra : palabras) {
			if (!palabra.isEmpty()) {
				resultado.append(Character.toUpperCase(palabra.charAt(0)))
				.append(palabra.substring(1))
				.append(" ");
			}
		}

		return resultado.toString().trim();
	}

	/**
	 * Realiza N reasignaciones al selectivo
	 * @author Lizbeth Hernandez
	 * @param n Int define la cantidad de reasignaciones a realizar
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 */
	public void Reasignaciones(int n, boolean screenShot) throws InterruptedException {
		for (int i = 0; i < n; i++) {

			// Long press sobre un colaborador
			clickLargoColaborador(screenShot);

			// Seleccionar encargado
			clickCbRoles(screenShot);
			seleccionarRol(screenShot);

			// Seleccionar el selectivo a reasignar
			clickCbSelectivos(screenShot);

			seleccionarSelectivos(n, screenShot);

			// Continuar
			clickBtnContinuarCambioSelectivo(screenShot);

			// Agregar una pausa si es necesario
			Thread.sleep(1000); // Pausa de 1 segundo entre cada iteración
		}
	}

	/**
	 * Ejecuta el flujo completo para realizar N reasignaciones de selectivo 
	 * @author Lizbeth Hernandez
	 *  @param user Usuario para ingresar a la aplicacion Oxxo al día
	 * @param pass Contraseña del usuario
	 * @param app Ej. com.femco.oxxo.operaciones.mitienda.debug
	 * @param clasificacion proveedor, planograma, categorias
	 * @param screenShot true/false Define si toma o no captura de pantalla
	 * @param n Int define la cantidad de reasignaciones a realizar
	 */
	public void prerequisitoReasignacion(String user, String pass, String app, String clasificacion, boolean screenShot, int n) throws IOException, InterruptedException {
		Login login = new Login(testCase, au, baseExecution);
		Menus menus = new Menus(testCase, au, baseExecution);
		Comunes comunes = new Comunes(testCase, au, baseExecution);

		//Iniciar sesion
		login.login(user,pass);

		//Ingresar a Conteos Inteligentes
		menus.ingresarRolesInteligentes(screenShot);

		//Semana en curso
		comunes.seleccionarSemana(screenShot);

		//Continuar
		comunes.clickBtnContinuar(screenShot);
		scrollToColaboradorEnElCalendario();

		//Realiza los movimientos de reasignación del selectivo
		Reasignaciones(n, screenShot);

		//Cerrar Aplicacion
		comunes.finalizarApp(app);
	}
}
