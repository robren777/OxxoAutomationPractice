package Mobile.POSMovil.om;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalTime;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.apache.commons.io.FileUtils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;

import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;
import Mobile.util.GlobalVariables;
import antlr.collections.List;
import utils.sql.SQLResult;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import static org.testng.Assert.assertTrue;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;

public class LoginPM {
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;

	/*
	 * Localizadores de inicio de sesión - Tienda POS Movil
	 */
	private By Tienda = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/etCr");
	private By btn_NextLogin = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/btnSendUser");

	/*
	 * Localizadores de inicio de sesión
	 */
	private By Usuario = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/tvUserName");
	private By Contraseña = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/etPassword");
	private By btn_Login = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/btnSendLogin");
	private By listUsu = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/tvInputCr");
	private By usuario = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/tvUserName\" and @text=\"NoUsuario\"]");

	/* Localizadores Inicio de POS Móvil */
	private By saldoInicial = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/etAmount");
	private By btn_SaldoInicial = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/btnConfirm");
	private By Buscador = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/etSearchItem");
	private By Journal = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/rvJournal");
	private String val;
	public static String CRTienda;

	private By password = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/etPassword");
	private By loading = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/loadingAnimation");

	private By lbRetiroP = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/tvSaldoInicial\"]");
	private By btnConfirmarR = By
			.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/btnConfirm\"]");
	private By lbContraseñaRP = By
			.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/password\"]");
	private By lbMontoRetirar = By
			.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/amount\"]");
	private By etMontoRetirar = By
			.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/amountCut3\"]");
	private By msjDCRetiroParcial = By
			.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/amountCut8\"]");
	private By btnConfirmarDC = By
			.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/btnConfirm2\"]");
	private By msjRetiroExitoso = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/tvSaldoInicial\"]");

	// Campos de la configuración de Fecha y hora
	private By campoHora = By.xpath("//android.widget.TextView[@resource-id=\"android:id/title\" and @text=\"Hora\"]");
	private By campoAM = By.xpath("//android.widget.RadioButton[@resource-id=\"android:id/am_label\"]");
	private By campoPM = By.xpath("//android.widget.RadioButton[@resource-id=\"android:id/pm_label\"]");
	private By botonAceptar = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]");

	// Validación de carga de pantalla
	private By leyendaCargando = By.xpath(
			"//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/rvSearch\"]");

	/**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase Login
	 * 
	 * @author Cesar Guel
	 * @param au            (AppiumUtil): objeto de la clase
	 * @param testCase      (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 */
	public LoginPM(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
		this.testCase = testCase;
		this.au = au;
		this.baseExecution = baseExecution;
	}

	/**
	 * Método - Login CrPlaza y CRTienda
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la maquina a utilizar
	 * @date 06/09/2024
	 *
	 */
	public void loginTienda(String ipPv) {
		try {

			String CRtienda = ConsultaCR(ipPv);
			WebElement crTienda = au.driver.findElement(Tienda);
			testCase.addTextEvidenceCurrentStep("Se ingresa CR de tienda");
			crTienda.sendKeys(CRtienda);
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.driver.hideKeyboard();
			TimeUnit.SECONDS.sleep(5);
			testCase.addTextEvidenceCurrentStep("Tap en el boton Continuar");
			au.click(btn_NextLogin);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método - Login CrPlaza y CRTienda
	 * 
	 * @author Cesar Guel
	 * @param crTienda el nombre de la tienda a utilizar
	 * @date 06/09/2024
	 *
	 */
	public void loginTiendaCr(String crTienda) {
		try {

			TimeUnit.SECONDS.sleep(5);
			WebElement tienda = au.driver.findElement(Tienda);
			testCase.addTextEvidenceCurrentStep("Se ingresa el CR correspondiente");
			tienda.sendKeys(crTienda);
			testCase.addScreenShotCurrentStep(au, "testImage");

			au.driver.hideKeyboard();
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap boton flecha adelante");
			au.click(btn_NextLogin);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void loginTiendaCrV2(String crTienda) {
		try {

//			TimeUnit.SECONDS.sleep(5);
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(60));
			wait.until(ExpectedConditions.elementToBeClickable(Tienda));
			WebElement tienda = au.driver.findElement(Tienda);

			testCase.addTextEvidenceCurrentStep("Se ingresa el CR correspondiente");
			tienda.sendKeys(crTienda);
			testCase.addScreenShotCurrentStep(au, "testImage");

			au.driver.hideKeyboard();
//			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap boton flecha adelante");
			wait.until(ExpectedConditions.elementToBeClickable(btn_NextLogin));
			au.click(btn_NextLogin);
			testCase.addScreenShotCurrentStep(au, "testImage");
			wait.until(ExpectedConditions.elementToBeClickable(listUsu));
			TimeUnit.SECONDS.sleep(10);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	/**
	 * Metodo - login tienda con CR dinamico 
	 * 
	 * @author Cesar Guel 
	 * @date 04/05/2026
	 */
	public void loginTiendaCrV2D() {
		try {

			waitForVisibility(Tienda, Duration.ofSeconds(50));
			WebElement tienda = au.driver.findElement(Tienda);
			
			testCase.addTextEvidenceCurrentStep("Se ingresa el CR correspondiente");
			tienda.sendKeys(GlobalVariables.crTiendaD);
			testCase.addScreenShotCurrentStep(au, "testImage");

			au.driver.hideKeyboard();

			testCase.addTextEvidenceCurrentStep("Tap boton flecha adelante");
			waitForVisibility(btn_NextLogin, Duration.ofSeconds(50));
			au.click(btn_NextLogin);
			testCase.addScreenShotCurrentStep(au, "testImage");
			waitForVisibility(listUsu, Duration.ofSeconds(50));
			TimeUnit.SECONDS.sleep(10);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método - Login con user y password
	 * 
	 * @author Cesar Guel
	 * @param userPassword El password con el que se va a iniciar sesión
	 * @param salInic      el saldo inicial al ingresar al journal
	 * @date 06/09/2024
	 *
	 */
	public void loginUser(String userPassword, String salInic) {
		au.driver.hideKeyboard();
		testCase.addTextEvidenceCurrentStep("Se selecciona el usuario");
		au.scrollToElement(usuario);
		testCase.addScreenShotCurrentStep(au, "testImage");
		au.click(usuario);
		testCase.addTextEvidenceCurrentStep("Se ingresa la contraseña");
		au.sendKeys(Contraseña, userPassword);
		testCase.addScreenShotCurrentStep(au, "testImage");
		testCase.addTextEvidenceCurrentStep("Se presiona el boton para continuar");
		au.click(btn_Login);
		testCase.addScreenShotCurrentStep(au, "testImage");
		try {
			TimeUnit.SECONDS.sleep(15);
			if (isElementPresent(saldoInicial)) {
				TimeUnit.SECONDS.sleep(5);
				WebElement SaldoInicial = au.driver.findElement(saldoInicial);
				SaldoInicial.sendKeys(salInic);
				au.click(btn_SaldoInicial);
				System.out.println("Saldo Inicial ingresado correctamente.");
			} else if (isElementPresent(lbRetiroP)) {
				System.out.println("Se detectó elemento de retiro parcial");
				waitForVisibility(lbRetiroP, Duration.ofSeconds(30));
				realizarRetiroParcial(userPassword);
				System.out.println("Retiro parcial procesado correctamente.");
			} else {
				System.out.println("El campo de saldo inicial no está presente. Continuando con la ejecución.");
				System.out.println("El campo de Retiro Parcial no está presente. Continuando con la ejecución.");
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
			System.out.println("Error de interrupción: " + e.getMessage());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error inesperado al manejar el saldo inicial: " + e.getMessage());
		}

	}

	/**
	 * Método - Login con user y password validando cual se encuentra selecionado en
	 * XPOS
	 * 
	 * @author Cesar Guel
	 * @param userPassword El password con el que se va a iniciar sesión
	 * @param salInic      el saldo inicial al ingresar al journal
	 * @throws Exception
	 * @date 06/09/2024 Adecuacion: Cumplir parametros mencionados por BAU para
	 *       considerar usuario seleccionado en XPOS Realizado por: Juan Ibarra
	 *       Fecha: 20-Marzo-2026
	 *
	 */
	public void loginUserV2(String crTienda, String salInic) throws Exception {
		// Se realiza validación del usuario cajero seleccionado en XPOS
		String ip = obtenerIPTienda(crTienda);
		String usuarioCajero = null;
		// consulta para obtener el numero de usuario seleccionado
		String urlPuntoVentaConsultar = "jdbc:sqlserver://" + ip
				+ ";databaseName=XposStore;encrypt=true;trustServerCertificate=true";
		String query = "SELECT USER_NAME FROM [XposStore].[dbo].[OPERATOR] WHERE IS_ACTIVE = 1 and OPERATOR_STATUS_CODE_ID = 3 and FL_INTRN = 0  and PASSWORD is not null";
		String segundopass = null;
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(180));

		try (Connection conn = DriverManager.getConnection( // Creación de la conexión a la base de datos
				urlPuntoVentaConsultar, GlobalVariables.userPVGeneral, GlobalVariables.passPVGeneral);
				PreparedStatement ps = conn.prepareStatement(query)) { // realiza la consulta del Cajero que tiene
																		// seleccionado la tienda
			try (ResultSet rs = ps.executeQuery()) {// Ejecución del query y confirmación de información
				if (rs.next()) {
					usuarioCajero = rs.getString("USER_NAME");
					System.out.println("Usuario Cajero seleccionado: " + usuarioCajero);
					au.driver.hideKeyboard();
					wait.until(ExpectedConditions.elementToBeClickable(listUsu));
					testCase.addTextEvidenceCurrentStep("Se selecciona el usuario");
					scrollIntoViewByText(usuarioCajero);
					testCase.addScreenShotCurrentStep(au, "testImage");
					seleccionarUsuarioPorScroll(usuarioCajero);
					testCase.addTextEvidenceCurrentStep("Se ingresa la contraseña");
					wait.until(ExpectedConditions.elementToBeClickable(Contraseña));
					au.sendKeys(Contraseña, GlobalVariables.passCajero);
					segundopass = GlobalVariables.passCajero;
					testCase.addScreenShotCurrentStep(au, "testImage");
					testCase.addTextEvidenceCurrentStep("Se presiona el boton para continuar");
					wait.until(ExpectedConditions.elementToBeClickable(btn_Login));
					au.click(btn_Login);
					testCase.addScreenShotCurrentStep(au, "testImage");
				} else {
					System.out.println("No se encontro Usuario Cajero seleccionado, se maneja como No Usuario.");
					au.driver.hideKeyboard();
					testCase.addTextEvidenceCurrentStep("Se selecciona el usuario");
					au.scrollToElement(usuario);
					testCase.addScreenShotCurrentStep(au, "testImage");
					au.click(usuario);
					testCase.addTextEvidenceCurrentStep("Se ingresa la contraseña");
					wait.until(ExpectedConditions.elementToBeClickable(Contraseña));
					au.sendKeys(Contraseña, GlobalVariables.passNoCajero);
					testCase.addScreenShotCurrentStep(au, "testImage");
					testCase.addTextEvidenceCurrentStep("Se presiona el boton para continuar");
					wait.until(ExpectedConditions.elementToBeClickable(btn_Login));
					au.click(btn_Login);
					testCase.addScreenShotCurrentStep(au, "testImage");
					segundopass = GlobalVariables.passNoCajero;
				}
			}
		} catch (SQLException e) {
			System.err.println(
					"Catch de la consulta a base de datos local del metodo loginUserV2. Mensaje: " + e.getMessage());
			testCase.nextStep("No se encontró un Cajero seleccionado para la tienda " + crTienda
					+ ", favor de revisar ya que regresa el siguiente mensaje: " + e.getMessage());
		}

		// Continua con el flujo despues del logeo
		try {
			TimeUnit.SECONDS.sleep(10);
			if (isElementPresent(saldoInicial)) {
				TimeUnit.SECONDS.sleep(5);
				WebElement SaldoInicial = au.driver.findElement(saldoInicial);
				SaldoInicial.sendKeys(salInic);
				au.click(btn_SaldoInicial);
				System.out.println("Saldo Inicial ingresado correctamente.");
			} else if (isElementPresent(lbRetiroP)) {
				System.out.println("Se detectó elemento de retiro parcial");
				waitForVisibility(lbRetiroP, Duration.ofSeconds(30));
				realizarRetiroParcial(segundopass);
				System.out.println("Retiro parcial procesado correctamente.");
			} else {
				System.out.println("El campo de saldo inicial no está presente. Continuando con la ejecución.");
				System.out.println("El campo de Retiro Parcial no está presente. Continuando con la ejecución.");
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
			System.out.println("Error de interrupción: " + e.getMessage());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error inesperado al manejar el saldo inicial: " + e.getMessage());
		}
	}

	/**
	 * Método - Login con user y password validando cual se encuentra selecionado en
	 * XPOS con valores dinamicos
	 * 
	 * @author Cesar Guel
	 * @param userPassword El password con el que se va a iniciar sesión
	 * @param salInic      el saldo inicial al ingresar al journal
	 * @throws Exception
	 * @date 06/09/2024 Adecuacion: Cumplir parametros mencionados por BAU para
	 *       considerar usuario seleccionado en XPOS Realizado por: Juan Ibarra
	 *       Fecha: 20-Marzo-2026
	 *
	 */
	public void loginUserV2D(String salInic) throws Exception {
		// Se realiza validación del usuario cajero seleccionado en XPOS
		String usuarioCajero = null;
		// consulta para obtener el numero de usuario seleccionado
		String urlPuntoVentaConsultar = "jdbc:sqlserver://" + GlobalVariables.ipPvD
				+ ";databaseName=XposStore;encrypt=true;trustServerCertificate=true";
		String query = "SELECT USER_NAME FROM [XposStore].[dbo].[OPERATOR] WHERE IS_ACTIVE = 1 and OPERATOR_STATUS_CODE_ID = 3 and FL_INTRN = 0  and PASSWORD is not null"; 
		String segundopass = null;

		try (Connection conn = DriverManager.getConnection( // Creación de la conexión a la base de datos
				urlPuntoVentaConsultar, GlobalVariables.userPVGeneral, GlobalVariables.passPVGeneral);
				PreparedStatement ps = conn.prepareStatement(query)) { // realiza la consulta del Cajero que tiene
																		// seleccionado la tienda
			try (ResultSet rs = ps.executeQuery()) {// Ejecución del query y confirmación de información
				if (rs.next()) {
					usuarioCajero = rs.getString("USER_NAME");
					System.out.println("Usuario Cajero seleccionado: " + usuarioCajero);
					au.driver.hideKeyboard();
					waitForVisibility(listUsu, Duration.ofSeconds(45));
					testCase.addTextEvidenceCurrentStep("Se selecciona el usuario");
					scrollIntoViewByText(usuarioCajero);
					testCase.addScreenShotCurrentStep(au, "testImage");										
					seleccionarUsuarioPorScroll(usuarioCajero);
					testCase.addTextEvidenceCurrentStep("Se ingresa la contraseña");						
					waitForVisibility(Contraseña, Duration.ofSeconds(45));
					au.sendKeys(Contraseña, GlobalVariables.passCajero);
					segundopass = GlobalVariables.passCajero;
					testCase.addScreenShotCurrentStep(au, "testImage");
					testCase.addTextEvidenceCurrentStep("Se presiona el boton para continuar");					
					waitForVisibility(btn_Login, Duration.ofSeconds(30));
					au.click(btn_Login);					
					testCase.addScreenShotCurrentStep(au, "testImage");					
				} else {
					System.out.println("No se encontro Usuario Cajero seleccionado, se maneja como No Usuario.");
					au.driver.hideKeyboard();
					testCase.addTextEvidenceCurrentStep("Se selecciona el usuario");
					au.scrollToElement(usuario);
					testCase.addScreenShotCurrentStep(au, "testImage");
					au.click(usuario);
					testCase.addTextEvidenceCurrentStep("Se ingresa la contraseña");
					waitForVisibility(Contraseña, Duration.ofSeconds(30));
					au.sendKeys(Contraseña, GlobalVariables.passNoCajero);
					testCase.addScreenShotCurrentStep(au, "testImage");
					testCase.addTextEvidenceCurrentStep("Se presiona el boton para continuar");
					waitForVisibility(btn_Login, Duration.ofSeconds(30));
					au.click(btn_Login);
					testCase.addScreenShotCurrentStep(au, "testImage");
					segundopass = GlobalVariables.passNoCajero;
				}
			}
		} catch (SQLException e) {
			System.err.println(
					"Catch de la consulta a base de datos local del metodo loginUserV2. Mensaje: " + e.getMessage());
			testCase.nextStep("No se encontró un Cajero seleccionado para la tienda " + GlobalVariables.crTiendaD
					+ ", favor de revisar ya que regresa el siguiente mensaje: " + e.getMessage());
		}
		
		waitForVisibility(Buscador, Duration.ofSeconds(60));
		
		// Continua con el flujo despues del logeo
		try {			
			TimeUnit.SECONDS.sleep(10);
			if (isElementPresent(saldoInicial)) {
				TimeUnit.SECONDS.sleep(5);
				waitForVisibility(saldoInicial, Duration.ofSeconds(30));
				WebElement SaldoInicial = au.driver.findElement(saldoInicial);
				SaldoInicial.sendKeys(salInic);
				au.click(btn_SaldoInicial);
				System.out.println("Saldo Inicial ingresado correctamente.");
			} else if (isElementPresent(lbRetiroP)) {
				System.out.println("Se detectó elemento de retiro parcial");
				waitForVisibility(lbRetiroP, Duration.ofSeconds(30));
				realizarRetiroParcial(segundopass);
				System.out.println("Retiro parcial procesado correctamente.");
			} else {
				System.out.println("El campo de saldo inicial no está presente. Continuando con la ejecución.");
				System.out.println("El campo de Retiro Parcial no está presente. Continuando con la ejecución.");
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
			System.out.println("Error de interrupción: " + e.getMessage());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error inesperado al manejar el saldo inicial: " + e.getMessage());
		}
	}
	
	/**
	 * Método - Realizar retiro parcial si se encuentra la ventana en validación
	 * 
	 * @author Cesar Guel
	 * @param userPassword El password con el que se va a iniciar sesión
	 * @date 15/10/2025
	 */
	public void valVentanaRetiroP(String userPassword) {
		try {
			if (isElementPresent(lbRetiroP)) {
				System.out.println("Se detectó elemento de retiro parcial");
				waitForVisibility(lbRetiroP, Duration.ofSeconds(30));
				realizarRetiroParcial(userPassword);
				System.out.println("Retiro parcial procesado correctamente.");
			} else {
				System.out.println("No se encontro la ventan, puede seguir con la ejecución");
				continuarConEjecucion();
			}
		} catch (Exception e) {
			System.out.println("Ubo algun error al realizar el proceso de retiro parcial");
			e.printStackTrace();
		}
	}

	/**
	 * Método - verificar si un elemento está presente en la página
	 * 
	 * @author Cesar Guel
	 * @param locator el localizador del elemento
	 * @return Retorna true o false Si se encuentra el elemento
	 * @date 06/09/2024
	 */
	public boolean isElementPresent(By locator) {
		try {
			au.driver.findElement(locator);
			return true;
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Ingreso de credenciales
	 * 
	 * @author Cesar Guel
	 * @param ipPv         La ip de la maquina a utilizar
	 * @param userPassword La contraseña del usuario
	 * @param salInic      El saldo inicial de ventas
	 * @date 06/09/2024
	 *
	 */
	public void verificarLoginTiendaUser(String ipPv, String userPassword, String salInic) {
		try {
			WebElement indicadorBuscar = au.driver.findElement(Buscador);

			if (indicadorBuscar != null) {
				System.out.println("Indicador 'Buscador' encontrado. Continuando con la ejecución.");
				continuarConEjecucion();
			}
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println("Indicador 'Buscar' no encontrado. Verificando si está el indicador 'Tienda'");
			e.printStackTrace();

			try {
				WebElement indicadorTienda = au.driver.findElement(Tienda);

				if (indicadorTienda != null) {
					System.out.println("Indicador 'Tienda' encontrado. Realizando login de Tienda y Usuario");

					// Realiza los logins de Tienda y Usuario
					loginTienda(ipPv);
					loginUser(userPassword, salInic);
					continuarConEjecucion();
				}
			} catch (org.openqa.selenium.NoSuchElementException ex) {
				System.out.println("Indicador 'Tienda' no encontrado. No se puede continuar.");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Método - Ingreso de credenciales tipos de inicios
	 * 
	 * @author Cesar Guel
	 * @param crTienda      El nombre de la tienda a utilizar
	 * @param usserPassword La contrase{a edl usuario
	 * @param salInic       El saldo inicial de las ventas
	 * @date 19/08/2025
	 *
	 */
	public void verificarLoginTiendaUserCr(String crTienda, String userPassword, String salInic) {
		try {
			WebElement indicadorBuscar = au.driver.findElement(Buscador);

			if (indicadorBuscar != null) {
				System.out.println("Indicador 'Buscador' encontrado. Continuando con la ejecución.");
				continuarConEjecucion();
			}
		} catch (Exception e) {
			System.out.println("Indicador 'Buscar' no encontrado. Verificando si está el indicador 'Tienda'");
			e.printStackTrace();
			try {
				WebElement indicadorTienda = au.driver.findElement(Tienda);

				if (indicadorTienda != null) {
					System.out.println("Indicador 'Tienda' encontrado. Realizando login de Tienda y Usuario");

					// Realiza los logins de Tienda y Usuario
					loginTiendaCr(crTienda);
					loginUser(userPassword, salInic);
					continuarConEjecucion();
				}
			} catch (Exception ex) {
				System.out.println("Indicador 'Tienda' no encontrado. No se puede continuar.");
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Método - Ingreso de credenciales tipos de inicios
	 * 
	 * @author Cesar Guel
	 * @param crTienda      El nombre de la tienda a utilizar
	 * @param usserPassword La contrase{a edl usuario
	 * @param salInic       El saldo inicial de las ventas
	 * @date 19/08/2025
	 *
	 */
	public void verificarLoginTiendaUserCrV2(String crTienda, String salInic) {
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(120));
		try {
			WebDriverWait waitbuscador = new WebDriverWait(au.driver, Duration.ofSeconds(10));
			waitbuscador.until(ExpectedConditions.elementToBeClickable(Buscador));
			WebElement indicadorBuscar = au.driver.findElement(Buscador);

			if (indicadorBuscar != null) {
				System.out.println("Indicador 'Buscador' encontrado. Continuando con la ejecución.");
				continuarConEjecucion();
			}
		} catch (Exception e) {
			System.out.println("Indicador 'Buscar' no encontrado. Verificando si está el indicador 'Tienda'");
			e.printStackTrace();
			try {
				wait.until(ExpectedConditions.elementToBeClickable(Tienda));
				WebElement indicadorTienda = au.driver.findElement(Tienda);
				if (indicadorTienda != null) {
					System.out.println("Indicador 'Tienda' encontrado. Realizando login de Tienda y Usuario");

					// Realiza los logins de Tienda y Usuario
					loginTiendaCrV2(crTienda);
					loginUserV2(crTienda, salInic);
					continuarConEjecucionV2();
				}
			} catch (Exception ex) {
				System.out.println("Indicador 'Tienda' no encontrado. No se puede continuar.");
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * Método - Ingreso de credenciales tipos de inicios
	 * 
	 * @author Cesar Guel
	 * @param crTienda      El nombre de la tienda a utilizar
	 * @param usserPassword La contrase{a edl usuario
	 * @param salInic       El saldo inicial de las ventas
	 * @date 04/05/2026
	 *
	 */
	public void verificarLoginTiendaUserCrV2D(String salInic) {
		try {
			WebElement indicadorBuscar = au.driver.findElement(Buscador);

			if (indicadorBuscar != null) {
				System.out.println("Indicador 'Buscador' encontrado. Continuando con la ejecución.");
				continuarConEjecucion();
			}
		} catch (Exception e) {
			System.out.println("Indicador 'Buscar' no encontrado. Verificando si está el indicador 'Tienda' metodo");
			e.printStackTrace();
			try {
				WebElement indicadorTienda = au.driver.findElement(Tienda);
				if (indicadorTienda != null) {
					System.out.println("Indicador 'Tienda' encontrado. Realizando login de Tienda y Usuario");

					// Realiza los logins de Tienda y Usuario
					loginTiendaCrV2D();
					loginUserV2D(salInic);
					continuarConEjecucionV2();
				}
			} catch (Exception ex) {
				System.out.println("Indicador 'Tienda' no encontrado. No se puede continuar.");
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Método - Validar que se muestre pantalla de login
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return Retorna true o false Si el elemento esta Desplegado
	 * @date 24/01/2025
	 */
	public boolean validarPanLog() {
		try {

			WebElement panlog = au.driver.findElement(Tienda);

			if (panlog.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep("Se validar que se muestre la pantalla de login");
				System.out.println("Si se encontro la pantalla correspondiente");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(5);
				return true;
			} else {
				testCase.addScreenShotCurrentStep(au, "testImage");
				System.out.println("No se encontro la pantalla correspondiente");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Ingresar CR de Tienda
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la maquina a utilizar
	 * @date 24/01/2025
	 */
	public void ingresarCRTiendaC(String ipPv) {
		try {
			String CRtienda = ConsultaCR(ipPv);
			au.driver.hideKeyboard();
			WebElement crTienda = au.driver.findElement(Tienda);

			testCase.addTextEvidenceCurrentStep("Se ingresa el CR de la tienda");
			au.sendKeys(Tienda, CRtienda);
			testCase.addScreenShotCurrentStep(au, "testImage");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Presionar boton Enter
	 * 
	 * @author Cesar Guel
	 * @param salInic El saldo inicial de las ventas
	 * @date 24/01/2025
	 */
	public void presionarBtnEnt(String salInic) {
		try {
			testCase.addTextEvidenceCurrentStep("Presionar Enter");
			au.click(btn_Login);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			if (isElementPresent(saldoInicial)) {
				WebElement SaldoInicial = au.driver.findElement(saldoInicial);
				testCase.addTextEvidenceCurrentStep("Se ingresa Saldo Inicial");
				SaldoInicial.sendKeys(salInic);
				testCase.addScreenShotCurrentStep(au, "testImage");
				testCase.addTextEvidenceCurrentStep("Tap en el boton Confirmar Saldo Inicial");
				au.click(btn_SaldoInicial);
				testCase.addScreenShotCurrentStep(au, "testImage");
				System.out.println("Saldo Inicial ingresado correctamente.");
			} else {
				System.out.println("El campo de saldo inicial no está presente. Continuando con la ejecución.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Validar se muestre pantalla Seleccionar usuario
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return Retorna true o false Si el elemento esta Desplegado
	 * @date 24/01/2025
	 */
	public boolean validarLisUs() {
		try {

			WebElement listus = au.driver.findElement(listUsu);

			if (listus.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep("Se valida que se muestre el listado de usuarios");
				System.out.println("Si encontro exitosamente el listado de usuarios");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(5);
				return true;
			} else {
				testCase.addScreenShotCurrentStep(au, "testImage");
				System.out.println("No se pudo encontrar el listado");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Seleccionar usuario
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 24/01/2025
	 */
	public void selecUsuario() {
		try {

			TimeUnit.SECONDS.sleep(3);

			testCase.addTextEvidenceCurrentStep("Seleccionar usuario");
			au.scrollToElement(usuario);
			au.click(usuario);
			testCase.addScreenShotCurrentStep(au, "testImage");

			TimeUnit.SECONDS.sleep(5);

		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("ERROR: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que selecciona el Cajero que se tiene seleccionado dentro del punto de
	 * venta
	 * 
	 * @throws Exception
	 * @param crTienda
	 * @author Juan Ibarra Fecha: 06-Mayo-2026
	 */

	public void selecUsuarioV2(String crTienda) throws Exception {

		// Se realiza validación del usuario cajero seleccionado en XPOS
		String ip = obtenerIPTienda(crTienda);
		String usuarioCajero = null;
		// consulta para obtener el numero de usuario seleccionado
		String urlPuntoVentaConsultar = "jdbc:sqlserver://" + ip
				+ ";databaseName=XposStore;encrypt=true;trustServerCertificate=true";
		String query = "SELECT USER_NAME FROM [XposStore].[dbo].[OPERATOR] WHERE IS_ACTIVE = 1 and OPERATOR_STATUS_CODE_ID = 3 and FL_INTRN = 0  and PASSWORD is not null";
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(180));

		try (Connection conn = DriverManager.getConnection( // Creación de la conexión a la base de datos
				urlPuntoVentaConsultar, GlobalVariables.userPVGeneral, GlobalVariables.passPVGeneral);
				PreparedStatement ps = conn.prepareStatement(query)) { // realiza la consulta del Cajero que tiene
																		// seleccionado la tienda
			try (ResultSet rs = ps.executeQuery()) {// Ejecución del query y confirmación de información
				if (rs.next()) {
					usuarioCajero = rs.getString("USER_NAME");
					System.out.println("Usuario Cajero seleccionado: " + usuarioCajero);
					TimeUnit.SECONDS.sleep(3);
					wait.until(ExpectedConditions.elementToBeClickable(listUsu));
					testCase.addTextEvidenceCurrentStep("Seleccionar usuario");
					scrollIntoViewByText(usuarioCajero);
					Thread.sleep(200);
					testCase.addScreenShotCurrentStep(au, "testImage");

//					// sección donde resalta la opción por seleccionar
//					WebElement element = au.driver.findElement(AppiumBy.androidUIAutomator(
//							"new UiScrollable(new UiSelector().resourceId(\"com.femco.oxxo.operaciones.posmovil.qa:id/rvUsers\"))"
//									+ ".scrollIntoView(new UiSelector().text(\"" + usuarioCajero + "\"))"));
//
//					// Resaltar antes de hacer click
//					String ruta = resaltarElemento(au.driver, element, usuarioCajero);
//					System.out.println("valor de ruta: " + ruta);

					seleccionarUsuarioPorScroll(usuarioCajero);
					Thread.sleep(200);
					testCase.addScreenShotCurrentStep(au, "testImage");

					// Se elimina el archivo y la carpeta creada
//					Thread.sleep(200);
//					eliminarCarpetaDesdeArchivo(ruta);

				} else {
					System.out.println("No se encontro Usuario Cajero seleccionado, se maneja como No Usuario.");
					TimeUnit.SECONDS.sleep(3);
					testCase.addTextEvidenceCurrentStep("Seleccionar usuario");
					au.scrollToElement(usuario);
					Thread.sleep(200);

					testCase.addScreenShotCurrentStep(au, "testImage");
					
					// sección donde resalta la opción por seleccionar
//					WebElement element = au.driver.findElement(AppiumBy.androidUIAutomator(
//							"new UiScrollable(new UiSelector().resourceId(\"com.femco.oxxo.operaciones.posmovil.qa:id/rvUsers\"))"
//									+ ".scrollIntoView(new UiSelector().text(\"NoUsuario\"))"));

					// Resaltar antes de hacer click
//					String ruta = resaltarElemento(au.driver, element, "NoUsuario");
//					System.out.println("valor de ruta: " + ruta);				
					

					au.click(usuario);
					Thread.sleep(200);
					testCase.addScreenShotCurrentStep(au, "testImage");

					// Se elimina el archivo y la carpeta creada
//					Thread.sleep(200);
//					eliminarCarpetaDesdeArchivo(ruta);

				}
			}
		} catch (SQLException e) {
			System.err.println(
					"Catch de la consulta a base de datos local del metodo selecUsuarioV2. Mensaje: " + e.getMessage());
			testCase.nextStep("No se encontró un Cajero seleccionado para la tienda " + crTienda
					+ ", favor de revisar ya que regresa el siguiente mensaje: " + e.getMessage());
		}
	}

	/**
	 * Metodo que genera una imagen donde se resalta en un recuadro azul el elemento
	 * deseado REVISAR QUE SE PUEDA CAMBIAR A UN METODO SIMILAR A HIGHLIGHT
	 * 
	 * @param driver
	 * @param element
	 * @param nombreImagen
	 * @author Juan Ibarra Fecha: 06-Mayo-2026
	 */
	public String resaltarElemento(AppiumDriver driver, WebElement element, String nombreImagen) {
		String imagen = null;
		try {
			// Sanitizar nombre (MUY IMPORTANTE)
			nombreImagen = nombreImagen.replaceAll("[^a-zA-Z0-9-_]", "_");

			// Carpeta fija (ajústala a tu framework)
			String carpeta = System.getProperty("user.dir") + "\\evidenciastemp\\";
			new File(carpeta).mkdirs();

			// Nombre final
			String rutaCompleta = carpeta + "resaltado_" + nombreImagen + ".png";

			// Screenshot
			File srcFile = driver.getScreenshotAs(OutputType.FILE);
			BufferedImage fullImg = ImageIO.read(srcFile);

			// Coordenadas
			Point point = element.getLocation();
			int eleWidth = element.getSize().getWidth();
			int eleHeight = element.getSize().getHeight();

			// Dibujar rectángulo
			Graphics2D g = fullImg.createGraphics();
			g.setColor(Color.blue);
			g.setStroke(new BasicStroke(5));
			g.drawRect(point.getX(), point.getY(), eleWidth, eleHeight);
			g.dispose();

			// Guardar imagen
			File outputFile = new File(rutaCompleta);
			ImageIO.write(fullImg, "png", outputFile);

			System.out.println("Imagen guardada en: " + rutaCompleta);

			if (outputFile.exists()) {
				Runtime.getRuntime().exec(
						"powershell -command \"(new-object -com shell.application).minimizeall(); (new-object -com shell.application).undoMinimizeAll()\"");
				Runtime.getRuntime().exec("cmd /c start \"\" /max \"" + rutaCompleta + "\"");
//				Runtime.getRuntime()
//						.exec("powershell -command \"Start-Process '" + rutaCompleta + "' -WindowStyle Maximized\"");
//	        	Desktop.getDesktop().open(outputFile); //otra opción de como abriur 
//				Thread.sleep(1000);
//				testCase.addScreenShotCurrentStep("");

				boolean ventanaActiva = esperarVentanaActiva(new File(rutaCompleta).getName(), 10);
				if (ventanaActiva) {
					testCase.addScreenShotCurrentStep("");
				} else {
					System.out.println("La ventana NO quedó activa, no se logro tomar la pantalla");
				}

			} else {
				System.out.println("No se pudo adjuntar evidencia, archivo no existe");
			}
			imagen = rutaCompleta;
		} catch (Exception e) {
			System.err.println("Error al resaltar elemento: " + e.getMessage());
		}
		return imagen;
	}

	/**
	 * Metodo que realiza la validación que se encuentre activa una ventana de una
	 * aplicación especifica
	 * 
	 * @param nombreArchivo
	 * @param timeoutSegundos
	 * @return
	 * @throws InterruptedException
	 * @author Juan Ibarra Fecha: 06-Mayo-2026
	 */
	public static boolean esperarVentanaActiva(String nombreArchivo, int timeoutSegundos) throws InterruptedException {
		long inicio = System.currentTimeMillis();
		while ((System.currentTimeMillis() - inicio) < timeoutSegundos * 1000) {
			// Obtener ventana activa
			HWND hwnd = User32.INSTANCE.GetForegroundWindow();

			if (hwnd != null) {
				char[] buffer = new char[1024];
				User32.INSTANCE.GetWindowText(hwnd, buffer, 1024);
				String tituloVentana = Native.toString(buffer);
				System.out.println("Ventana activa: " + tituloVentana);
				if (tituloVentana != null && tituloVentana.toLowerCase().contains(nombreArchivo.toLowerCase())) {
					return true;
				}
			}
			Thread.sleep(500);
		}
		return false;
	}

	/**
	 * Metodo que se encarga de eliminar la carpeta y archivos de una ruta
	 * especifica
	 * 
	 * @param rutaArchivo
	 * @return
	 * @author Juan Ibarra Fecha: 08-Mayo-2026
	 */
	public boolean eliminarCarpetaDesdeArchivo(String rutaArchivo) {
		try {
			// Valida la ruta
			if (rutaArchivo == null || rutaArchivo.trim().isEmpty()) {
				System.out.println("La ruta del archivo es inválida");
				return false;
			}

			File archivo = new File(rutaArchivo);

			// Valida existencia del archivo
			if (!archivo.exists()) {
				System.out.println("El archivo no existe: " + rutaArchivo);
				return false;
			}

			// Obtiene la carpeta
			File carpeta = archivo.getParentFile();

			// Valida que exta la carpeta
			if (carpeta == null || !carpeta.exists()) {
				System.out.println("No se encontró la carpeta");
				return false;
			}

			// Elimina el contenido de la carpeta
			File[] archivos = carpeta.listFiles();
			if (archivos != null) {
				for (File f : archivos) {
					if (f.isDirectory()) {
						eliminarDirectorio(f);
					} else {
						f.delete();
					}
				}
			}

			// Elimina la carpeta principal
			boolean eliminada = carpeta.delete();

			// Valida que se haya eliminado la carpeta principal
			if (eliminada) {
				System.out.println("Carpeta eliminada correctamente: " + carpeta.getAbsolutePath());
			} else {
				System.out.println("No se pudo eliminar la carpeta");
			}

			return eliminada;

		} catch (Exception e) {
			System.out.println("Error al eliminar carpeta: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Metodo que se encarga de eliminar una carpeta, recibiendo la ruta en la cual
	 * se encuentra
	 * 
	 * @param directorio
	 * @author Juan Ibarra Fecha: 08-Mayo-2026
	 */
	private void eliminarDirectorio(File directorio) {
		File[] archivos = directorio.listFiles();

		// Elimina la carpeta principal
		if (archivos != null) {
			for (File archivo : archivos) {

				if (archivo.isDirectory()) {
					eliminarDirectorio(archivo);
				} else {
					archivo.delete();
				}
			}
		}

		directorio.delete();
	}

	/**
	 * Método - Ingresar contraseña
	 * 
	 * @author Cesar Guel
	 * @param usserPassword La contraseña del usuario
	 * @date 24/01/2025
	 */
	public void ingresarCtrs(String userPassword) {
		try {
			testCase.addTextEvidenceCurrentStep("Se ingresa contraseña");
			au.sendKeys(Contraseña, userPassword);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Presionar boton Enter
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 24/01/2025
	 */
	public void presBtnEnt(String salInic) {
		try {
			testCase.addTextEvidenceCurrentStep("Tap boton Enter");
			au.click(btn_NextLogin);
			testCase.addScreenShotCurrentStep(au, "testImage");

			if (isElementPresent(saldoInicial)) {
				WebElement SaldoInicial = au.driver.findElement(saldoInicial);
				testCase.addTextEvidenceCurrentStep("Se ingresa Saldo Inicial");
				SaldoInicial.sendKeys(salInic);
				testCase.addScreenShotCurrentStep(au, "testImage");
				testCase.addTextEvidenceCurrentStep("Tap en el boton aceptar Saldo Inicial");
				au.click(btn_SaldoInicial);
				testCase.addScreenShotCurrentStep(au, "testImage");
				System.out.println("Saldo Inicial ingresado correctamente.");
			} else {
				System.out.println("El campo de saldo inicial no está presente. Continuando con la ejecución.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Validar realice el login correcto
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return Retorna true o false Si el elemento esta Desplegado
	 * @date 24/01/2025
	 */
	public boolean validarLogCor() {
		try {
			WebElement journal = au.driver.findElement(Journal);

			if (journal.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep("Se valida que se muestre el journal");
				System.out.println("Se realizo correctamente el login");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(5);
				return true;
			} else {
				testCase.addScreenShotCurrentStep(au, "testImage");
				System.out.println("No se realizo correctamente el login");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Continuar con ejecucion
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 06/09/2024
	 *
	 */
	public void continuarConEjecucion() {
		try {
			TimeUnit.SECONDS.sleep(5);
			WebElement etTienda = au.driver.findElement(Buscador);
			TimeUnit.SECONDS.sleep(10);
			testCase.addTextEvidenceCurrentStep(
					"Se valida que se muerstre la pantalla de Journal para seguir con la ejecución");
			System.out.println("Puede seguir con la ejecución");
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			System.out.println("No se pudo continuar Elemento");
		}
	}

	
	/**
	 * Método - Continuar con ejecucion con mayor tiempo de espera
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 04/05/2026
	 *
	 */

	public void continuarConEjecucionV2() {
		try {
			waitForVisibility(Buscador, Duration.ofSeconds(60));
			WebElement etTienda = au.driver.findElement(Buscador);
			testCase.addTextEvidenceCurrentStep(
					"Se valida que se muerstre la pantalla de Journal para seguir con la ejecución ");
			System.out.println("Puede seguir con la ejecución");
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("No se pudo continuar Elemento");
		}
	}

	/**
	 * Método - Consulta CR punto de venta
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la caja que se esta utilizando
	 * @return Retorna el CR de la tienda cosnultado
	 * @date 06/09/2024
	 *
	 */
	public String ConsultaCR(String ipPv) {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + ipPv + ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV_116, GlobalVariables.passPV_116);
			String query = "select CODE from XposStore.dbo.Till where IS_ACTIVE = 1";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			CRTienda = val.substring(0);
		} catch (Exception e) {
			System.out.println("Error al consultar el CR en BD");
			e.printStackTrace();
			CRTienda = "";
		}

		return CRTienda;
	}

	/**
	 * Método - Realizar retiro parcial
	 * 
	 * @author Cesar Guel
	 * @param usserPassword La contraseña del usuario
	 * @date 22/08/2025
	 */
	public void realizarRetiroParcial(String userPassword) {
		try {
			waitForVisibility(lbRetiroP, Duration.ofSeconds(30));
			System.out.println("Se encontro mensaje retiro parcial exitosamente");

			testCase.addTextEvidenceCurrentStep("Tap en el boton Confirmar");
			au.click(btnConfirmarR);
			testCase.addScreenShotCurrentStep(au, "testImage");

			waitForVisibility(lbContraseñaRP, Duration.ofSeconds(30));
			System.out.println("Se encontro etiqueta de ingresar contraseña exitosamente");
			testCase.addTextEvidenceCurrentStep("Ingresar contraseña");
			au.sendKeys(lbContraseñaRP, userPassword);
			testCase.addScreenShotCurrentStep(au, "testImage");
			testCase.addTextEvidenceCurrentStep("Tap en el boton Confirmar");
			au.click(btnConfirmarR);
			testCase.addScreenShotCurrentStep(au, "testImage");

			waitForVisibility(lbMontoRetirar, Duration.ofSeconds(30));
			testCase.addTextEvidenceCurrentStep("Ingresar monto a retirar");
			au.sendKeys(etMontoRetirar, obtMontoRetiro());
			testCase.addScreenShotCurrentStep(au, "testImage");
			testCase.addTextEvidenceCurrentStep("Tap en el boton Confirmar");
			au.click(btnConfirmarR);
			testCase.addScreenShotCurrentStep(au, "testImage");

			waitForVisibility(msjDCRetiroParcial, Duration.ofSeconds(30));
			testCase.addTextEvidenceCurrentStep("Tap en el boton Confirmar");
			au.click(btnConfirmarDC);
			testCase.addScreenShotCurrentStep(au, "testImage");

			waitForVisibility(msjRetiroExitoso, Duration.ofSeconds(30));
			System.out.println("Se encuentra mensaje retiro parcial realizado exitosamente");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Realiza Retiro Parical si se muestra la ventan
	 * 
	 * @author Cesar Guel
	 * @param userPassword
	 * @date 05/09/2025
	 */
	public void realizarRetiroParcialSE(String userPassword) {
		try {

			if (isElementPresent(lbRetiroP)) {
				System.out.println("Se encontro ventan retiro parcial");
				realizarRetiroParcial(userPassword);
			} else {
				System.out.println("No se encontro ventan, continuar con ejecución");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Obtiener cifra monto retiro
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return Retorna la cifra monto retiro
	 * @date 22/08/2025
	 */
	public String obtMontoRetiro() {
		try {
			waitForVisibility(lbMontoRetirar, Duration.ofSeconds(30));
			String texto = au.driver.findElement(lbMontoRetirar).getText();
			String textoLimpio = texto.replace("Monto: $", "");
			double monto = Double.parseDouble(textoLimpio);
			return String.format("%.2f", monto);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	/*
	 * +++++++++++++++++++++++++++++
	 * 
	 * CAMBIOS MMORALES
	 * 
	 * ++++++++++++++++++++++++++++/
	 * 
	 * /** Espera a que un elemento este visible en pantalla dentro de un periodo de
	 * tiempo
	 * 
	 * @author Marcos Morales
	 * 
	 * @param locator Identificador del elemento
	 * 
	 * @param timeout Tiempo maximo de espera
	 * 
	 * @date 23/06/2025
	 */
	public void waitForVisibility(By locator, Duration timeout) {
		WebDriverWait wait = new WebDriverWait(au.driver, timeout);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception ex) {
			throw new RuntimeException("El elemento no se volvió visible dentro del tiempo esperado: " + locator, ex);
		}
	}

	/**
	 * Espera a que un elemento se oculte dentro de un periodo de tiempo
	 * 
	 * @author Marcos Morales
	 * @param locator Identificador del elemento
	 * @param timeout Tiempo maximo de espera
	 * @date 23/06/2025
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
	 * Método - Capturar CR de Tienda
	 * 
	 * @author Marcos Morales
	 * @param crTienda CR de Tienda
	 * @date 23/06/2025
	 */
	public void ingresarCRTienda(String crTienda) {
		try {
			waitForVisibility(Tienda, Duration.ofSeconds(30));
			testCase.addTextEvidenceCurrentStep("Ingresar CR tienda");
			au.sendKeys(Tienda, crTienda);
			testCase.addScreenShotCurrentStep(au, "testImage");
			testCase.addTextEvidenceCurrentStep("Tap en el boton Continuar");
			au.click(btn_NextLogin);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método - Click en boton Flecha Adelante
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 23/06/2025
	 */
	public void clickBotonFlechaAdelante() {
		try {
			waitForVisibility(btn_NextLogin, Duration.ofSeconds(30));
			testCase.addTextEvidenceCurrentStep("Tap en el boton Continuar");
			au.click(btn_NextLogin);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método - Click en boton Flecha Adelante para Login
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 23/06/2025
	 */
	public void clickBotonFlechaAdelanteLogin() {
		try {
			waitForVisibility(btn_Login, Duration.ofSeconds(30));
			testCase.addTextEvidenceCurrentStep("Tap en el boton Adelante");
			au.click(btn_Login);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método - Seleccionar usuario
	 * 
	 * @author Marcos Morales
	 * @param usuario Usuario con el que se inicia sesion
	 * @date 23/06/2025
	 */
	public void seleccionarUsuario(String usuario) {
		try {
			waitForVisibility(btn_NextLogin, Duration.ofSeconds(30));
			testCase.addTextEvidenceCurrentStep("Tap en el boton NextLogin");
			au.click(btn_NextLogin);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método - Seleccionar Servicio por Scroll
	 * 
	 * @author Marcos Morales
	 * @param texto Servicio a seleccionar
	 * @date 23/06/2025
	 */
	public void seleccionarUsuarioPorScroll(String texto) {
		By locator = By.xpath(
				"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/tvUserName\" and @text=\""
						+ texto + "\"]");
		try {
			// Identificar si ya se muestra en pantalla
			if (isElementPresent(locator)) {
				au.click(locator);
			} else {
				// Caso contrario, aplicar scroll hasta encontrarlo
				scrollIntoViewByText(texto);
				au.click(locator);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Hace un scroll vertical hasta encontrar el texto especificado
	 * 
	 * @author Marcos Morales
	 * @param texto Cadena de texto a identificar en el scroll
	 * @date 23/06/2025
	 */
	public void scrollIntoViewByText(String texto) {
		try {
			au.driver.findElement(AppiumBy.androidUIAutomator(
					"new UiScrollable(new UiSelector().resourceId(\"com.femco.oxxo.operaciones.posmovil.qa:id/rvUsers\"))"
							+ ".scrollIntoView(new UiSelector().text(\"" + texto + "\"))"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método - Capturar contraseña de usuario
	 * 
	 * @author Marcos Morales
	 * @param pass Contraseña del usuario
	 * @date 23/06/2025
	 */
	public void ingresarContrasena(String pass) {
		try {
			waitForVisibility(password, Duration.ofSeconds(30));
			testCase.addTextEvidenceCurrentStep("Ingresar contraseña");
			au.sendKeys(password, pass);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método - Espera se oculte el loading en las transiciones de pantalla
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 23/06/2025
	 */
	public void esperaSeOculteLoading() {
		try {
			waitForHidden(loading, Duration.ofSeconds(30));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método - Valida si se requiere iniciar sesion
	 * 
	 * @author Marcos Morales
	 * @param crTda    CR de Tienda
	 * @param usuario  Usuario que inicia sesion
	 * @param password Contraseña del usuario
	 * @date 23/06/2025
	 */
	public void validaSeRequiereLogin(String crTda, String usuario, String password) {
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(5)); // Timeout de 5 segundos
			WebElement crTienda = wait.until(ExpectedConditions.presenceOfElementLocated(Tienda));

			testCase.addTextEvidenceCurrentStep("Ingresar CR tienda");
			crTienda.sendKeys(crTda);
			testCase.addScreenShotCurrentStep(au, "testImage");
			clickBotonFlechaAdelante();
			seleccionarUsuarioPorScroll(usuario);
			ingresarContrasena(password);
			clickBotonFlechaAdelanteLogin();
			esperaSeOculteLoading();
			System.out.println("Inicio de sesion exitoso");
		} catch (TimeoutException e) {
			System.out.println("No se encontró el campo para Captura de CR. Sesión ya iniciada.");
			e.printStackTrace();
		}
	}

	/**
	 * Método - Valida si se requiere iniciar sesion con valores dinamicos
	 * 
	 * @author Cesar Guel
	 * @param usuario  Usuario que inicia sesion
	 * @param password Contraseña del usuario
	 * @date 21/04/2026
	 */
	public void validaSeRequiereLoginD(String usuario, String password) {
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(5)); // Timeout de 5 segundos
			WebElement crTienda = wait.until(ExpectedConditions.presenceOfElementLocated(Tienda));

			testCase.addTextEvidenceCurrentStep("Ingresar CR tienda");
			crTienda.sendKeys(GlobalVariables.crTiendaD);
			testCase.addScreenShotCurrentStep(au, "testImage");
			clickBotonFlechaAdelante();
			seleccionarUsuarioPorScroll(usuario);
			ingresarContrasena(password);
			clickBotonFlechaAdelanteLogin();
			esperaSeOculteLoading();
			System.out.println("Inicio de sesion exitoso");
		} catch (TimeoutException e) {
			System.out.println("No se encontró el campo para Captura de CR. Sesión ya iniciada.");
			e.printStackTrace();
		}
	}

	/**
	 * Método - Iniciar sesion en POS Movil
	 * 
	 * @author Marcos Morales
	 * @param crTda    CR de Tienda
	 * @param usuario  Usuario que inicia sesion
	 * @param password Contraseña del usuario
	 * @date 16/12/2025
	 */
	public void iniciarSesion(String crTda, String usuario, String password) {

		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(10));
		WebElement crTienda = null;
		try {
			crTienda = wait.until(ExpectedConditions.presenceOfElementLocated(Tienda));
		} catch (Exception ex) {
			throw new RuntimeException(
					"No se pudo iniciar sesion, No se encontro el campo para ingresar el CR de tienda", ex);
		}
		testCase.addTextEvidenceCurrentStep("Ingresar CR tienda");
		crTienda.sendKeys(crTda);
		testCase.addScreenShotCurrentStep(au, "testImage");
		clickBotonFlechaAdelante();
		seleccionarUsuarioPorScroll(usuario);
		ingresarContrasena(password);
		clickBotonFlechaAdelanteLogin();
		esperaSeOculteLoading();
		System.out.println("Inicio de sesion exitoso");
	}

	/**
	 * Metodo que obtiene el numero de store_ID del cr de la tienda usada
	 * 
	 * @author Juan Ibarra
	 * @param crStore .- Se recibe el cr de la tienda para obtener el id de la
	 *                tienda con la que cuenta en Evolución
	 * @return idTienda .- regresa el ID de la tienda con la que cuenta en la base
	 *         de datos de Evolución Fecha: 20-Marzo-2026
	 * @throws Exception
	 */
	public String obtenerIDTiendaNube(String crStore) throws Exception {
		System.out.println("Entra a metodo de obtenerIDTiendaNube");
//		validarArchivoVPN();
		String idTienda = null;
		String query = "SELECT store_ID FROM [XposCloudStoreQA].[dbo].[STORE] WHERE CR_STORE = ?";

		try (Connection conn = DriverManager.getConnection(GlobalVariables.urlEvolucion, GlobalVariables.userEvolucion2,
				GlobalVariables.passEvolucion2); PreparedStatement stmt = conn.prepareStatement(query)) {
			// Extrae del parametro crTienda el cr de la tienda y ejecuta la consulta
			stmt.setString(1, crStore.substring(crStore.length() - 5));
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					idTienda = rs.getString("store_ID");
					System.out.println("Valor de ID tienda en Base de datos de Evolución es: " + idTienda);
				} else {
					System.out.println("No se encontró tienda con CR_STORE: " + crStore);
					testCase.nextStep("No se encontró tienda con CR_STORE: " + crStore + ", favor de revisar");
				}
			}
		} catch (SQLException e) {
			System.err.println("Error al consultar ID de tienda: " + e.getMessage());
			testCase.nextStep(
					"Favor de revisar los datos de Tienda o la conexión con evolución. No se encontró tienda con CR_STORE: 50"
							+ crStore + ". Se obtuvo el mensaje de:  " + e.getMessage());
		}
		return idTienda;
	}

	/**
	 * Metodo que valida que si existe o no el archivo de conección a la VPN con
	 * evolución En caso de no existir genera un archivo .bat para realizar la
	 * comunicación con evolución
	 * 
	 * @author Juan Ibarra Fecha: 20-Marzo-2026
	 */

	public void validarArchivoVPN() {
		System.out.println("Entra a metodo validarArchivoVPN");
		String carpeta = "C:\\VPN";
		String ruta = carpeta + "\\qa.bat";
		String contenido = "c:\\\n" + "cd C:\\Users\\GTIM\\.ssh\n"
				+ "start ssh -N -v -i oxxo_qa_team oxxo_qa_team@20.225.47.232 -p 5022 -L 1433:oxzsqlmixposdev.529c1106f15d.database.windows.net:1433";
		try {
			File directorio = new File(carpeta);
			// Crear carpeta si no existe
			if (!directorio.exists()) {
				boolean creada = directorio.mkdirs();
				if (creada) {
					System.out.println("Carpeta VPN para comunicación con Evolución creada: " + carpeta);
				} else {
					System.out.println("No se pudo crear la carpeta VPN para comunicación con Evolución.");
					testCase.nextStep("No se pudo crear la carpeta VPN para comunicación con Evolución.");
					return;
				}
			}

			File archivo = new File(ruta);
			// Crear archivo qa.bat si no existe
			if (!archivo.exists()) {
				System.out.println("Creando archivo .bat...");
				try (FileWriter writer = new FileWriter(archivo)) {
					writer.write(contenido);
				}
				System.out.println("Archivo .bat creado correctamente.");
				encenderVPN();
			} else {
				System.out.println("El archivo .bat ya existe.");
				encenderVPN();
			}
		} catch (IOException e) {
			System.out.println("Error en validarArchivoVPN: " + e.getMessage());
			testCase.nextStep("Error en validarArchivoVPN: " + e.getMessage());
		}
	}

	/**
	 * Métodos para encender el archivo .bat para la VPN con evolución
	 * 
	 * @author Juan Ibarra
	 * @param N/A
	 * @return
	 * @return true o false con el cual se continua o falla el caso de prueba Fecha:
	 *         10-Junio -2025 Para encender la VPN se requiere la ruta en la cual se
	 *         encuentra el archivo .bat, este debe estar parameterizado en el
	 *         DataProviders del caso
	 * 
	 */
	public boolean encenderVPN() {
		String appNombre = "ssh.exe"; // Se captura el nombre de la aplicación que se abre al tener la VPN habilitada
		try {
			// Valida si ya esta en ejecución la VPN con evolución
			if (!aplicacionesAbiertas(appNombre)) {
				// ejecuta el archivo de la VPN
				Runtime.getRuntime().exec("cmd /c start C:\\VPN\\qa.bat");				
				System.out.println(
						"Se ejecuta el archivo .bat para comunicación con Evolución, archivo en la ruta: C:\\VPN\\qa.bat");				
			}
			
			//au.wait(12);

			if (aplicacionesAbiertas(appNombre)) {
				System.out.println(appNombre + " correspondiente a la VPN con evolución está ejecutandose.");
				return true;
			} else {
				System.out.println(appNombre + " correspondiente a la VPN con evolución NO está ejecutandose.");
				testCase.nextStep(
						"No se encontro el archivo qa.bat que realiza la comunicación con Evolución en la ruta C:\\VPN\\qa.bat, favor de revisar en la ruta mencionada que se encuentre el archivo");
				return false;
			}
		} catch (IOException e) {
			System.out.println("Entro al Catch del metodo de encenderVPN.");
			e.printStackTrace();
			testCase.nextStep(
					"Se registro una excepción dentro del metodo de encenderVPN, favor de revisar el siguiente mensaje: "
							+ e);
			return false;
		}
	}

	/**
	 * Método que Cierra el .bat que abre la VPN con Evolución
	 * 
	 * @author Juan Ibarra
	 * @param N/A
	 * @return N/A Fecha: 20-Marzo-2026
	 */
	public boolean cerrarVPN() {
		System.out.println("Entro al metodo de cerrarVPN");
		String appNombre = "ssh.exe"; // Se captura el nombre de la aplicación que se abre al tener la VPN con
										// evolución habilitada
		try {
			Runtime.getRuntime().exec("taskkill /F /IM ssh.exe");
			Runtime.getRuntime().exec("taskkill /F /IM cmd.exe");
		} catch (IOException e) {
			e.printStackTrace();
			testCase.nextStep(
					"Se registro una excepción dentro del metodo de cerrarVPN, favor de revisar el siguiente mensaje: "
							+ e);
			return false;
		}
		au.wait(5);
		if (aplicacionesAbiertas(appNombre)) {
			System.out.println(appNombre + " correspondiente a la VPN con evolución aun está ejecutandose.");
			testCase.nextStep(
					"La VPN con evolución aun está ejecutandose, favor de revisar por que no se ha detenido el proceso");
			return false;
		} else {
			System.out.println(appNombre + " correspondiente a la VPN con evolución No está ejecutandose.");
			return true;
		}
	}

	/**
	 * Metodo que se utiliza en cerrarVPN el cual busca las aplicaciónes abiertas en
	 * el equipo y cierra el proceso que corresnpode al arhivo qa.bat y ssh.exe
	 * 
	 * @param processName
	 * @return
	 * @author Juan Ibarra Fecha: 20-Marzo-2026
	 */
	public static boolean aplicacionesAbiertas(String processName) {
		try {
			// Ejecuta el comando 'tasklist' para obtener el proceso activos
			Process process = Runtime.getRuntime().exec("tasklist");
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.toLowerCase().contains(processName.toLowerCase())) {
					return true;
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Metodo para consultar la I.P. de la tienda a la cual se comunicara la
	 * aplicación
	 * 
	 * @param crStore .- Se reciben las 3 letras del cr de la tienda para obtener el
	 *                ip de la tienda con la que cuenta en evolución
	 * @return ip .- regresa la ip de la tienda a la que se conectara Oxxo al día
	 * @author Juan Ibarra Fecha: 20-Marzo-2026
	 * @throws Exception
	 */
	public String obtenerIPTienda(String crStore) throws Exception {
//		validarArchivoVPN();
		String ip = null;
		String query = "USE [XposCloudSecurityQA] select ip FROM [dbo].[DEVICE] where NAME like ?";
		try (Connection conn = DriverManager.getConnection( // Creación de la conexión a la base de datos
				GlobalVariables.urlEvolucion, GlobalVariables.userEvolucion2, GlobalVariables.passEvolucion2);
				PreparedStatement ps = conn.prepareStatement(query)) {
			// Extraer de la variable de CR el dato de los 3 datos alfanumericos del
			// CRTienda
			ps.setString(1, "%" + crStore.substring(crStore.length() - 3) + "%");
			try (ResultSet rs = ps.executeQuery()) {// Ejecución del query y confirmación de información
				if (rs.next()) {
					ip = rs.getString("ip");
					System.out.println("La ip de la tienda: " + crStore + " es: " + ip);
					return ip;// regresa la ip obtenida
				} else {
					System.out.println("No se encontró la ip para la tienda " + crStore);
					testCase.nextStep("No se encontró la ip para la tienda 50" + crStore
							+ ", favor de revisar que la tienda se encuentre ambientada con evolución");
					return "false";// regresa el string de false el cual se cambiara dentro del caso a booleano
									// para realizar su validación correspondiente
				}
			}
		} catch (SQLException e) {
			System.err.println("Catch del metodo obtenerIPTienda. Error al obtener la ip de la tienda en evolución: "
					+ e.getMessage());
			testCase.nextStep("No se encontró la ip para la tienda " + crStore
					+ ", favor de revisar ya que regresa el siguiente mensaje: " + e.getMessage());
			;
			return "false";// regresa el string de false el cual se cambiara dentro del caso a booleano
							// para realizar su validación correspondiente
		}
	}

	/**
	 * Metodo actualizar la hora del dispositivo a la misma hora del punto de venta
	 * 
	 * @param crStore .- Se reciben el cr de la tienda para obtener el ip de la
	 *                tienda con la que cuenta en evolución
	 * @author Juan Ibarra Fecha: 24-Marzo-2026
	 * @throws Exception
	 */
	public void actualizarHoraIgualalPV(String crTienda) throws Exception {
		// Se reafirma que se encuentre uso de red deshabilitado para actualizar la hora
		ejecutarADB("settings put global auto_time 0");

		String ip = obtenerIPTienda(crTienda);		
		String horaPuntoVenta = null;
		String urlPuntoVentaConsultar = "jdbc:sqlserver://" + ip
				+ ";databaseName=XposStore;encrypt=true;trustServerCertificate=true";
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(2));
		// consulta para obtener la hora del punto de venta		
		String query = "DECLARE @horaMinuto VARCHAR(5);\r\n" + "DECLARE @hora VARCHAR(2);\r\n"// Se declara variable de
																								// la hora del punto de
																								// venta
				+ "DECLARE @minutos VARCHAR(2);\r\n"// Se declara variable de los miutos del punto de venta
				+ "DECLARE @ampm VARCHAR(2);\r\n" // Se declara variable del valor de am o pm del punto de venta
				+ "\r\n" + "SET @horaMinuto = CONVERT(VARCHAR(5), GETDATE(), 108);\r\n"
				+ "SET @hora = FORMAT(GETDATE(), 'hh');\r\n" // Se le da formato de 12 horas a la hora así como la
																// variable donde se guardara
				+ "SET @minutos = FORMAT(GETDATE(), 'mm');\r\n" // Variable donde se guardaran los minutos
				+ "SET @ampm = FORMAT(GETDATE(), 'tt');\r\n"// Variable donde se guardaran el valor de am o pm
				+ "\r\n" + "SELECT @horaMinuto as HoraMinuto, @hora AS Hora, @minutos AS Minutos, @ampm AS AMPM;";		
		try (Connection conn = DriverManager.getConnection( // Creación de la conexión a la base de datos
				urlPuntoVentaConsultar, GlobalVariables.userPVGeneral, GlobalVariables.passPVGeneral);
				PreparedStatement ps = conn.prepareStatement(query)) { // realiza la consulta del Cajero que tiene
																		// seleccionado la tienda			
			try (ResultSet rs = ps.executeQuery()) {// Ejecución del query y confirmación de información
				if (rs.next()) {
					horaPuntoVenta = rs.getString("HoraMinuto");
					System.out.println("Hora en 24 horas del punto de venta: " + horaPuntoVenta);
					String hora = rs.getString("Hora");
					String minutos = rs.getString("Minutos");
					String ampm = rs.getString("AMPM");
					ejecutarADB("am start -a android.settings.DATE_SETTINGS");
//					au.wait(1);// Tiempo de espera para que realice la acción
					wait.until(ExpectedConditions.elementToBeClickable(campoHora));
					au.click(campoHora);
					wait.until(ExpectedConditions.elementToBeClickable(botonAceptar));
					if (au.driver.findElement(botonAceptar).isDisplayed()) {
						ejecutarADB("input text " + hora);
						au.wait(1);
						ejecutarADB("input text " + minutos);
						au.wait(1);
						if ("AM".equals(ampm)) {
							au.click(campoAM);
							au.wait(1);
						} else {
							au.click(campoPM);
							au.wait(1);
						}
						au.click(botonAceptar);
					}
				} else {
					System.out.println("No se pudo obtener la hora del punto de venta, favor de revisar.");
					testCase.nextStep("No se pudo obtener la hora del punto de venta para la tienda " + crTienda
							+ ", favor de revisar.");
				}
			}
		} catch (SQLException e) {
			System.err
					.println("Catch de la consulta a base de datos local del metodo actualizarHoraIgualalPV. Mensaje: "
							+ e.getMessage());
			testCase.nextStep("No se pudo obtener la hora del punto de venta para la tienda " + crTienda
					+ ", favor de revisar ya que regresa el siguiente mensaje: " + e.getMessage());
		}
		// realiza la acción de cerrar en el dispositivo la pantalla de configuración
		ejecutarADB("am force-stop com.android.settings");
	}

	/**
	 * Metodo que realiza la ejecución de comandos en adb shell
	 * 
	 * @param comando .- Se reciben el comando adb a ejecutar para se vea reflejado
	 *                en el dispositivo
	 * @author Juan Ibarra Fecha: 24-Marzo-2026
	 * @throws Exception
	 */
	private void ejecutarADB(String comando) throws IOException, InterruptedException {
		Process process = Runtime.getRuntime().exec("adb shell " + comando);
		process.waitFor(); // asegura ejecución
	}

	/**
	 * Metodo actualizar la hora del dispositivo a una fuera del horario del punto
	 * de venta
	 * 
	 * @param crStore .- Se reciben el cr de la tienda para obtener el ip de la
	 *                tienda con la que cuenta en evolución
	 * @author Juan Ibarra Fecha: 24-Marzo-2026
	 * @throws Exception
	 */
	public void actualizarHoraFueraHorario(String crTienda) throws Exception {
		// Se deshabilita red de la red para actualizar la hora del dispositivo
		ejecutarADB("settings put global auto_time 0");
		String horaPuntoVenta = null;
		String ip = obtenerIPTienda(crTienda);
		String urlPuntoVentaConsultar = "jdbc:sqlserver://" + ip
				+ ";databaseName=XposStore;encrypt=true;trustServerCertificate=true";
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(2));
		// consulta para obtener la hora del punto de venta fuera del horario en el dia
		// actual
		String query = "USE XposStore;\r\n"
				+ "\r\n"
				+ "DECLARE @dia VARCHAR(20);\r\n"
				+ "DECLARE @hora VARCHAR(2);\r\n"
				+ "DECLARE @minutos VARCHAR(2);\r\n"
				+ "DECLARE @ampm VARCHAR(2);\r\n"
				+ "DECLARE @horaMinuto VARCHAR(5);\r\n"
				+ "\r\n"
				+ "SET @horaMinuto = CONVERT(VARCHAR(5), GETDATE(), 108);\r\n"
				+ "SET @dia = DATENAME(WEEKDAY, GETDATE());\r\n"
				+ "\r\n"
				+ "SELECT \r\n"
				+ "    @hora = RIGHT('0' + CAST(\r\n"
				+ "        CASE \r\n"
				+ "            WHEN DATEPART(HOUR, DATEADD(SECOND, 1, START_TIME)) % 12 = 0 \r\n"
				+ "                THEN 12\r\n"
				+ "            ELSE DATEPART(HOUR, DATEADD(SECOND, 1, START_TIME)) % 12\r\n"
				+ "        END AS VARCHAR), 2),\r\n"
				+ "\r\n"
				+ "    @minutos = RIGHT(\r\n"
				+ "        '0' + CAST(\r\n"
				+ "            DATEPART(MINUTE, DATEADD(SECOND, 1, START_TIME)) AS VARCHAR\r\n"
				+ "        ),2),\r\n"
				+ "\r\n"
				+ "    @ampm = CASE\r\n"
				+ "                WHEN DATEPART(HOUR, DATEADD(SECOND, 1, START_TIME)) < 12 \r\n"
				+ "                    THEN 'AM'\r\n"
				+ "                ELSE 'PM'\r\n"
				+ "            END\r\n"
				+ "\r\n"
				+ "FROM [TIME_PERIOD]\r\n"
				+ "\r\n"
				+ "WHERE TIME_PERIOD_ID IN (\r\n"
				+ "    SELECT TIME_GROUP_PERIOD_LINK_ID \r\n"
				+ "    FROM [TIME_GROUP_PERIOD_LINK]  \r\n"
				+ "    WHERE TIME_GROUP_ID IN (\r\n"
				+ "        SELECT TIME_GROUP_ID\r\n"
				+ "        FROM [TIME_GROUP] \r\n"
				+ "        WHERE NAME LIKE '%ALCOHOL_RESTRICTION%' \r\n"
				+ "        AND IS_ACTIVE = '1'\r\n"
				+ "    )\r\n"
				+ ")\r\n"
				+ "\r\n"
				+ "AND CODE LIKE '%' + @dia + '%';\r\n"
				+ "\r\n"
				+ "SELECT  \r\n"
				+ "    @horaMinuto AS HoraMinuto,\r\n"
				+ "    @hora AS Hora,\r\n"
				+ "    @minutos AS Minutos,\r\n"
				+ "    @ampm AS AMPM;";

		try (Connection conn = DriverManager.getConnection( // Creación de la conexión a la base de datos
				urlPuntoVentaConsultar, GlobalVariables.userPVGeneral, GlobalVariables.passPVGeneral);
				PreparedStatement ps = conn.prepareStatement(query)) { // realiza la consulta del Cajero que tiene
																		// seleccionado la tienda
			try (ResultSet rs = ps.executeQuery()) {// Ejecución del query y confirmación de información
				if (rs.next()) {
					horaPuntoVenta = rs.getString("HoraMinuto");
					String hora = rs.getString("Hora");
					String minutos = rs.getString("Minutos");
					String ampm = rs.getString("AMPM");

					// Convertir el String de la consulta realizada al punto de venta a Int
					int horaInt = Integer.parseInt(hora);
					int minutosInt = Integer.parseInt(minutos);

					// Convertir a formato 24 horas el valor de horaInt
					if ("PM".equals(ampm) && horaInt != 12) {
						horaInt += 12;
					} else if ("AM".equals(ampm) && horaInt == 12) {
						horaInt = 0;
					}
					// obtiene la hora del dispositivo
					String horaDispositivo = obtenerHoraDispositivo();

					// Convertir a LocalTime las horas
					LocalTime horaActual = LocalTime.parse(horaDispositivo);
//					Considera la hora de inicio el valor obtenido del punto de venta
					LocalTime inicio = LocalTime.of(horaInt, minutosInt);
					LocalTime fin = LocalTime.of(9, 1); // 09:02

					// Si NO está en el rango actualiza la hora del dispositivo
					if (horaActual.isBefore(inicio) || horaActual.isAfter(fin)) {
						System.out.println(
								"Se realiza la actualización del dispositivo a un horario fuera de venta de alcohol");
						ejecutarADB("am start -a android.settings.DATE_SETTINGS");
						wait.until(ExpectedConditions.elementToBeClickable(campoHora));
						au.click(campoHora);
						wait.until(ExpectedConditions.elementToBeClickable(botonAceptar));
						if (au.driver.findElement(botonAceptar).isDisplayed()) {
							ejecutarADB("input text " + hora);
							au.wait(1);
							ejecutarADB("input text " + minutos);
							au.wait(1);
							if ("AM".equals(ampm)) {
								au.click(campoAM);
								au.wait(1);
							} else {
								au.click(campoPM);
								au.wait(1);
							}
							au.click(botonAceptar);
						}
					} else {
						System.out.println(
								"Se continua el caso sin afectación; el caso se esta ejecutando en un horario valido fuera de venta de alcohol.");
					}
				} else {
					System.out.println("No se pudo obtener la hora del punto de venta, favor de revisar.");
					testCase.nextStep("No se pudo obtener la hora del punto de venta para la tienda " + crTienda
							+ ", favor de revisar.");
				}
			}
		} catch (SQLException e) {
			System.err.println(
					"Catch de la consulta a base de datos local del metodo actualizarHoraFueraHorario. Mensaje: "
							+ e.getMessage());
			testCase.nextStep("No se pudo obtener la hora del punto de venta para la tienda " + crTienda
					+ ", favor de revisar ya que regresa el siguiente mensaje: " + e.getMessage());
		}
		// realiza la acción de cerrar en el dispositivo la pantalla de configuración
		ejecutarADB("am force-stop com.android.settings");
	}

	/**
	 * Metodo actualizar la hora del dispositivo a una fuera del horario del punto
	 * de venta
	 * 
	 * @param crStore .- Se reciben el cr de la tienda para obtener el ip de la
	 *                tienda con la que cuenta en evolución
	 * @author Juan Ibarra Fecha: 24-Marzo-2026
	 * @throws Exception
	 */
	public void actualizarHoraFueraHorarioD() throws Exception {
		// Se deshabilita red de la red para actualizar la hora del dispositivo
		ejecutarADB("settings put global auto_time 0");
		String horaPuntoVenta = null;
		String urlPuntoVentaConsultar = "jdbc:sqlserver://" + GlobalVariables.ipPvD
				+ ";databaseName=XposStore;encrypt=true;trustServerCertificate=true";
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(2));
		// consulta para obtener la hora del punto de venta fuera del horario en el dia
		// actual
		String query = "USE XposStore;\r\n" + "DECLARE @dia VARCHAR(20);\r\n" + "DECLARE @hora VARCHAR(2);\r\n"
				+ "DECLARE @minutos VARCHAR(2);\r\n" + "DECLARE @ampm VARCHAR(2);\r\n"
				+ "DECLARE @horaMinuto VARCHAR(5);\r\n" + "SET @horaMinuto = CONVERT(VARCHAR(5), GETDATE(), 108);\r\n"
				+ "SET @dia = DATENAME(WEEKDAY, GETDATE());\r\n" + "\r\n" + "SELECT \r\n"
				+ "    @hora = RIGHT('0' + CAST(\r\n" + "        CASE \r\n"
				+ "            WHEN DATEPART(HOUR, DATEADD(SECOND, 1, START_TIME)) % 12 = 0 THEN 12\r\n"
				+ "            ELSE DATEPART(HOUR, DATEADD(SECOND, 1, START_TIME)) % 12\r\n"
				+ "        END AS VARCHAR), 2),        \r\n"
				+ "    @minutos = RIGHT('0' + CAST(DATEPART(MINUTE, DATEADD(SECOND, 1, START_TIME)) AS VARCHAR),2),    \r\n"
				+ "    @ampm = CASE \r\n"
				+ "                WHEN DATEPART(HOUR, DATEADD(SECOND, 1, START_TIME)) < 12 THEN 'AM'\r\n"
				+ "                ELSE 'PM'\r\n" + "            END\r\n" + "FROM [TIME_PERIOD]\r\n"
				+ "WHERE TIME_PERIOD_ID IN (\r\n" + "    SELECT TIME_GROUP_PERIOD_LINK_ID \r\n"
				+ "    FROM [TIME_GROUP_PERIOD_LINK]  \r\n" + "    WHERE TIME_GROUP_ID = (\r\n"
				+ "        SELECT TIME_GROUP_ID \r\n" + "        FROM [TIME_GROUP] \r\n"
				+ "        WHERE NAME LIKE '%ALCOHOL_RESTRICTION%' \r\n" + "        AND IS_ACTIVE = '1')\r\n" + ")\r\n"
				+ "AND CODE LIKE '%' + @dia + '%';\r\n" + "\r\n"
				+ "SELECT  @horaMinuto as HoraMinuto, @hora AS Hora, @minutos AS Minutos, @ampm AS AMPM;";

		try (Connection conn = DriverManager.getConnection( // Creación de la conexión a la base de datos
				urlPuntoVentaConsultar, GlobalVariables.userPVGeneral, GlobalVariables.passPVGeneral);
				PreparedStatement ps = conn.prepareStatement(query)) { // realiza la consulta del Cajero que tiene
																		// seleccionado la tienda
			try (ResultSet rs = ps.executeQuery()) {// Ejecución del query y confirmación de información
				if (rs.next()) {
					horaPuntoVenta = rs.getString("HoraMinuto");					
					String hora = rs.getString("Hora");					
					String minutos = rs.getString("Minutos");					
					String ampm = rs.getString("AMPM");

					// Convertir el String de la consulta realizada al punto de venta a Int
					int horaInt = Integer.parseInt(hora);
					int minutosInt = Integer.parseInt(minutos);
					
					// Convertir a formato 24 horas el valor de  horaInt
					if ("PM".equals(ampm) && horaInt != 12) {
					    horaInt += 12;
					} else if ("AM".equals(ampm) && horaInt == 12) {
					    horaInt = 0;
					}
					//obtiene la hora del dispositivo
					String horaDispositivo  = obtenerHoraDispositivo();
					
					// Convertir a LocalTime las horas 
					LocalTime horaActual = LocalTime.parse(horaDispositivo);										
//					Considera la hora de inicio el valor obtenido del punto de venta
					LocalTime inicio = LocalTime.of(horaInt, minutosInt);  
					LocalTime fin = LocalTime.of(9, 1);      // 09:02

					// Si NO está en el rango actualiza la hora del dispositivo
					if (horaActual.isBefore(inicio) || horaActual.isAfter(fin)) {
					    System.out.println(
					        "Se realiza la actualización del dispositivo a un horario fuera de venta de alcohol");					    
					    ejecutarADB("am start -a android.settings.DATE_SETTINGS");
					    wait.until(ExpectedConditions.elementToBeClickable(campoHora));
					    au.click(campoHora);
					    wait.until(ExpectedConditions.elementToBeClickable(botonAceptar));
					    if (au.driver.findElement(botonAceptar).isDisplayed()) {
					        ejecutarADB("input text " + hora);
					        au.wait(1);
					        ejecutarADB("input text " + minutos);
					        au.wait(1);
					        if ("AM".equals(ampm)) {
					            au.click(campoAM);
					            au.wait(1);
					        } else {
					            au.click(campoPM);
					            au.wait(1);
					        }
					        au.click(botonAceptar);
					    }
					} else {
					    System.out.println(
					        "Se continua el caso sin afectación; el caso se esta ejecutando en un horario valido fuera de venta de alcohol.");
					}
				} else {
					System.out.println("No se pudo obtener la hora del punto de venta, favor de revisar.");
					testCase.nextStep("No se pudo obtener la hora del punto de venta para la tienda " + GlobalVariables.crTiendaD
							+ ", favor de revisar.");
				}
			}
		} catch (SQLException e) {
			System.err.println("Catch de la consulta a base de datos local del metodo actualizarHoraFueraHorario. Mensaje: "
					+ e.getMessage());
			testCase.nextStep("No se pudo obtener la hora del punto de venta para la tienda " + GlobalVariables.crTiendaD
					+ ", favor de revisar ya que regresa el siguiente mensaje: " + e.getMessage());
		}
		// realiza la acción de cerrar en el dispositivo la pantalla de configuración
		ejecutarADB("am force-stop com.android.settings");
	}
	
	/**
	 * Metodo - Actualiza horario dentro del horario permitido
	 * 
	 * @author Cesar Guel
	 * @date 15/04/2026
	 * @throws Exception
	 */
	public void actualizarHoraDentroHorarioD() throws Exception {

		// Se reafirma que se encuentre uso de red deshabilitado para actualizar la hora
		ejecutarADB("settings put global auto_time 0");
		String horaPuntoVenta = null;
		String urlPuntoVentaConsultar = "jdbc:sqlserver://" + GlobalVariables.ipPvD
				+ ";databaseName=XposStore;encrypt=true;trustServerCertificate=true";
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(2));
		// consulta para obtener la hora del punto de venta
		//aqui
		String query = "USE XposStore;\r\n"
				+ "\r\n"
				+ "DECLARE @dia VARCHAR(20);\r\n"
				+ "DECLARE @hora VARCHAR(2);\r\n"
				+ "DECLARE @minutos VARCHAR(2);\r\n"
				+ "DECLARE @ampm VARCHAR(2);\r\n"
				+ "DECLARE @horaMinuto VARCHAR(5);\r\n"
				+ "\r\n"
				+ "SET @horaMinuto = CONVERT(VARCHAR(5), GETDATE(), 108);\r\n"
				+ "SET @dia = DATENAME(WEEKDAY, GETDATE());\r\n"
				+ "\r\n"
				+ "SELECT  \r\n"
				+ "    @hora = RIGHT('0' + CAST(\r\n"
				+ "        CASE  \r\n"
				+ "            WHEN DATEPART(HOUR, DATEADD(SECOND, 1, START_TIME)) % 12 = 0 \r\n"
				+ "                THEN 12 \r\n"
				+ "            ELSE DATEPART(HOUR, DATEADD(SECOND, 1, START_TIME)) % 12 \r\n"
				+ "        END AS VARCHAR), 2),\r\n"
				+ "\r\n"
				+ "    @minutos = RIGHT(\r\n"
				+ "        '0' + CAST(\r\n"
				+ "            DATEPART(MINUTE, DATEADD(SECOND, 1, START_TIME)) AS VARCHAR\r\n"
				+ "        ), 2),\r\n"
				+ "\r\n"
				+ "    @ampm = CASE  \r\n"
				+ "                WHEN DATEPART(HOUR, DATEADD(SECOND, 1, START_TIME)) < 12 \r\n"
				+ "                    THEN 'AM' \r\n"
				+ "                ELSE 'PM'\r\n"
				+ "            END\r\n"
				+ "\r\n"
				+ "FROM [TIME_PERIOD]\r\n"
				+ "\r\n"
				+ "WHERE TIME_PERIOD_ID IN (\r\n"
				+ "    SELECT TIME_PERIOD_ID\r\n"
				+ "    FROM [TIME_GROUP_PERIOD_LINK]\r\n"
				+ "    WHERE TIME_GROUP_ID IN (\r\n"
				+ "        SELECT TIME_GROUP_ID\r\n"
				+ "        FROM [TIME_GROUP]\r\n"
				+ "        WHERE NAME LIKE '%ALCOHOL_RESTRICTION%'\r\n"
				+ "        AND IS_ACTIVE = '1'\r\n"
				+ "    )\r\n"
				+ ")\r\n"
				+ "\r\n"
				+ "AND CODE LIKE '%' + @dia + '%';\r\n"
				+ "\r\n"
				+ "SELECT  \r\n"
				+ "    @horaMinuto AS HoraMinuto,\r\n"
				+ "    @hora AS Hora,\r\n"
				+ "    @minutos AS Minutos,\r\n"
				+ "    @ampm AS AMPM;";

		try (Connection conn = DriverManager.getConnection( // Creación de la conexión a la base de datos
				urlPuntoVentaConsultar, GlobalVariables.userPVGeneral, GlobalVariables.passPVGeneral);
				PreparedStatement ps = conn.prepareStatement(query)) { // realiza la consulta del Cajero que tiene
																		// seleccionado la tienda
			try (ResultSet rs = ps.executeQuery()) {// Ejecución del query y confirmación de información
				if (rs.next()) {
					horaPuntoVenta = rs.getString("HoraMinuto");
					String hora = rs.getString("Hora");
					String minutos = rs.getString("Minutos");
					String ampm = rs.getString("AMPM");

					// Convertir el String de la consulta realizada al punto de venta a Int
					int horaInt = Integer.parseInt(hora);
					int minutosInt = Integer.parseInt(minutos);

					// Convertir a formato 24 horas el valor de horaInt
					if ("PM".equals(ampm) && horaInt != 12) {
						horaInt += 12;
					} else if ("AM".equals(ampm) && horaInt == 12) {
						horaInt = 0;
					}
					// obtiene la hora del dispositivo
					String horaDispositivo = obtenerHoraDispositivo();

					// Convertir a LocalTime las horas
					LocalTime horaActual = LocalTime.parse(horaDispositivo);
//					Considera la hora de inicio el valor obtenido del punto de venta
					LocalTime inicio = LocalTime.of(horaInt, minutosInt);
					LocalTime fin = LocalTime.of(9, 1); // 09:02

					// Si NO está en el rango actualiza la hora del dispositivo
					if (horaActual.isBefore(inicio) == horaActual.isAfter(fin)) {
						System.out
								.println("Se realiza la actualización del dispositivo a un horario de las 11:00 a.m.");
						String horaD = "11";
						String minutosD = "00";
						System.out.println("Abriendo configuración de fecha y hora");
						ejecutarADB("am start -a android.settings.DATE_SETTINGS");
//				au.wait(1);// Tiempo de espera para que realice la acción
						System.out.println("Esperando campo hora.");
						wait.until(ExpectedConditions.elementToBeClickable(campoHora));
						System.out.println("Campo hora encontrado");
						au.click(campoHora);
						wait.until(ExpectedConditions.elementToBeClickable(botonAceptar));
//				au.wait(1);
						if (au.driver.findElement(botonAceptar).isDisplayed()) {
							ejecutarADB("input text " + horaD);
							au.wait(1);
							ejecutarADB("input text " + minutosD);
							au.wait(1);
							au.click(campoAM);
							au.wait(1);
							au.click(botonAceptar);
						}
					} else {
						System.out.println(
								"Se continua el caso sin afectación; el caso se esta ejecutando en un horario valido dentro de venta de alcohol.");
					}
				} else {
					System.out.println("No se pudo obtener la hora del punto de venta, favor de revisar.");
					testCase.nextStep("No se pudo obtener la hora del punto de venta para la tienda "
							+ GlobalVariables.crTiendaD + ", favor de revisar.");
				}
			}
		} catch (SQLException e) {
			System.err
					.println("Catch de la consulta a base de datos local del metodo validarHoraVentaAlcohol. Mensaje: "
							+ e.getMessage());
			testCase.nextStep(
					"No se pudo obtener la hora del punto de venta para la tienda " + GlobalVariables.crTiendaD
							+ ", favor de revisar ya que regresa el siguiente mensaje: " + e.getMessage());
		}
		// realiza la acción de cerrar en el dispositivo la pantalla de configuración
		ejecutarADB("am force-stop com.android.settings");
	}

	/**
	 * MEtodo que obtiene la hora del dispositivo
	 * 
	 * @author Juan Ibarra
	 * @return hora del dispositivo para su posterior validación
	 * @throws Exception Fecha: 13-Abril-2026
	 */
	public String obtenerHoraDispositivo() throws Exception {
		String comando = "adb shell date +\"%H:%M\"";
		Process process = Runtime.getRuntime().exec(comando);

		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

		String hora = reader.readLine(); // aquí se guarda la hora
		System.out.println("Valor de hora en obtenerHoraDispositivo: " + hora);
		process.waitFor();

		return hora != null ? hora.trim() : null;
	}

	/**
	 * Metodo que valida y actualiza la hora del dispositivo en dado caso que la
	 * hora del punto de venta cuente con un horario fuera del horario de venta de
	 * alcohol, por lo general de 1:00 a.m. a 9:00 a.m.
	 * 
	 * @param crStore .- Se reciben el cr de la tienda para obtener el ip de la
	 *                tienda con la que cuenta en evolución
	 * @author Juan Ibarra Fecha: 25-Marzo-2026
	 * @throws Exception
	 */
	public void actualizarHoraDentroHorario(String crTienda) throws Exception {
		// Se reafirma que se encuentre uso de red deshabilitado para actualizar la hora
		ejecutarADB("settings put global auto_time 0");
		String ip = obtenerIPTienda(crTienda);
		String horaPuntoVenta = null;
		String urlPuntoVentaConsultar = "jdbc:sqlserver://" + ip
				+ ";databaseName=XposStore;encrypt=true;trustServerCertificate=true";
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(2));
		// consulta para obtener la hora del punto de venta
		String query = "USE XposStore;\r\n" + "DECLARE @dia VARCHAR(20);\r\n" + "DECLARE @hora VARCHAR(2);\r\n"
				+ "DECLARE @minutos VARCHAR(2);\r\n" + "DECLARE @ampm VARCHAR(2);\r\n"
				+ "DECLARE @horaMinuto VARCHAR(5);\r\n" + "SET @horaMinuto = CONVERT(VARCHAR(5), GETDATE(), 108);\r\n"
				+ "SET @dia = DATENAME(WEEKDAY, GETDATE());\r\n" + "\r\n" + "SELECT \r\n"
				+ "    @hora = RIGHT('0' + CAST(\r\n" + "        CASE \r\n"
				+ "            WHEN DATEPART(HOUR, DATEADD(SECOND, 1, START_TIME)) % 12 = 0 THEN 12\r\n"
				+ "            ELSE DATEPART(HOUR, DATEADD(SECOND, 1, START_TIME)) % 12\r\n"
				+ "        END AS VARCHAR), 2),        \r\n"
				+ "    @minutos = RIGHT('0' + CAST(DATEPART(MINUTE, DATEADD(SECOND, 1, START_TIME)) AS VARCHAR),2),    \r\n"
				+ "    @ampm = CASE \r\n"
				+ "                WHEN DATEPART(HOUR, DATEADD(SECOND, 1, START_TIME)) < 12 THEN 'AM'\r\n"
				+ "                ELSE 'PM'\r\n" + "            END\r\n" + "FROM [TIME_PERIOD]\r\n"
				+ "WHERE TIME_PERIOD_ID IN (\r\n" + "    SELECT TIME_GROUP_PERIOD_LINK_ID \r\n"
				+ "    FROM [TIME_GROUP_PERIOD_LINK]  \r\n" + "    WHERE TIME_GROUP_ID = (\r\n"
				+ "        SELECT TIME_GROUP_ID \r\n" + "        FROM [TIME_GROUP] \r\n"
				+ "        WHERE NAME LIKE '%ALCOHOL_RESTRICTION%' \r\n" + "        AND IS_ACTIVE = '1')\r\n" + ")\r\n"
				+ "AND CODE LIKE '%' + @dia + '%';\r\n" + "\r\n"
				+ "SELECT  @horaMinuto as HoraMinuto, @hora AS Hora, @minutos AS Minutos, @ampm AS AMPM;";

		try (Connection conn = DriverManager.getConnection( // Creación de la conexión a la base de datos
				urlPuntoVentaConsultar, GlobalVariables.userPVGeneral, GlobalVariables.passPVGeneral);
				PreparedStatement ps = conn.prepareStatement(query)) { // realiza la consulta del Cajero que tiene
																		// seleccionado la tienda
			try (ResultSet rs = ps.executeQuery()) {// Ejecución del query y confirmación de información
				if (rs.next()) {
					horaPuntoVenta = rs.getString("HoraMinuto");
					String hora = rs.getString("Hora");
					String minutos = rs.getString("Minutos");
					String ampm = rs.getString("AMPM");

					// Convertir el String de la consulta realizada al punto de venta a Int
					int horaInt = Integer.parseInt(hora);
					int minutosInt = Integer.parseInt(minutos);

					// Convertir a formato 24 horas el valor de horaInt
					if ("PM".equals(ampm) && horaInt != 12) {
						horaInt += 12;
					} else if ("AM".equals(ampm) && horaInt == 12) {
						horaInt = 0;
					}
					// obtiene la hora del dispositivo
					String horaDispositivo = obtenerHoraDispositivo();

					// Convertir a LocalTime las horas
					LocalTime horaActual = LocalTime.parse(horaDispositivo);
//					Considera la hora de inicio el valor obtenido del punto de venta
					LocalTime inicio = LocalTime.of(horaInt, minutosInt);
					LocalTime fin = LocalTime.of(9, 1); // 09:02

					// Si NO está en el rango actualiza la hora del dispositivo
					if (horaActual.isBefore(inicio) == horaActual.isAfter(fin)) {
						System.out
								.println("Se realiza la actualización del dispositivo a un horario de las 11:00 a.m.");
						String horaD = "11";
						String minutosD = "00";
						ejecutarADB("am start -a android.settings.DATE_SETTINGS");
//				au.wait(1);// Tiempo de espera para que realice la acción
						wait.until(ExpectedConditions.elementToBeClickable(campoHora));
						au.click(campoHora);
						wait.until(ExpectedConditions.elementToBeClickable(botonAceptar));
//				au.wait(1);
						if (au.driver.findElement(botonAceptar).isDisplayed()) {
							ejecutarADB("input text " + horaD);
							au.wait(1);
							ejecutarADB("input text " + minutosD);
							au.wait(1);
							au.click(campoAM);
							au.wait(1);
							au.click(botonAceptar);
						}
					} else {
						System.out.println(
								"Se continua el caso sin afectación; el caso se esta ejecutando en un horario valido dentro de venta de alcohol.");
					}
				} else {
					System.out.println("No se pudo obtener la hora del punto de venta, favor de revisar.");
					testCase.nextStep("No se pudo obtener la hora del punto de venta para la tienda " + crTienda
							+ ", favor de revisar.");
				}
			}
		} catch (SQLException e) {
			System.err
					.println("Catch de la consulta a base de datos local del metodo validarHoraVentaAlcohol. Mensaje: "
							+ e.getMessage());
			testCase.nextStep("No se pudo obtener la hora del punto de venta para la tienda " + crTienda
					+ ", favor de revisar ya que regresa el siguiente mensaje: " + e.getMessage());
		}
		// realiza la acción de cerrar en el dispositivo la pantalla de configuración
		ejecutarADB("am force-stop com.android.settings");
	}

	/**
	 * Revisión de metodo para actualizar de manera remota la hora del punto de
	 * venta. Se dejan fijos los datos de usuario y password al utilizar el mismo
	 * que se implementa en la ambientación. Se puede realizar la actualización en
	 * base a la opción que se requiera fueraHorario, dentroHorario o
	 * actualizarHorario
	 * 
	 * @param ip
	 * @param fechaHora
	 * @author Juan Ibarra
	 * @throws Exception Fecha: 07-Abril-20268
	 */

	public void cambiarHoraPV(String crTienda, String opcion) throws Exception {
		// Realiza la validación en la maquina local cuente con el ejecutable PsExe el
		// cual se requiere para mandar los comandos en cmd para la actualziación de la
		// hora
//		existePsExe();
		// Obtiene la ip del punto de venta al cual realizar la actualziación de la hora
		String ip = obtenerIPTienda(crTienda);
		String horaPuntoVenta = null;
		String urlPuntoVentaConsultar = "jdbc:sqlserver://" + ip
				+ ";databaseName=XposStore;encrypt=true;trustServerCertificate=true";

		// consulta para obtener la hora del punto de venta fuera del horario en el dia
		// actual
		String queryHorario = "USE XposStore;\r\n"
				+ "\r\n"
				+ "DECLARE @dia VARCHAR(20);\r\n"
				+ "DECLARE @hora VARCHAR(2);\r\n"
				+ "DECLARE @minutos VARCHAR(2);\r\n"
				+ "DECLARE @ampm VARCHAR(2);\r\n"
				+ "DECLARE @horaMinuto VARCHAR(5);\r\n"
				+ "\r\n"
				+ "SET @horaMinuto = CONVERT(VARCHAR(5), GETDATE(), 108);\r\n"
				+ "SET @dia = DATENAME(WEEKDAY, GETDATE());\r\n"
				+ "\r\n"
				+ "SELECT  \r\n"
				+ "    @hora = RIGHT('0' + CAST(\r\n"
				+ "        CASE  \r\n"
				+ "            WHEN DATEPART(HOUR, DATEADD(SECOND, 1, START_TIME)) % 12 = 0 \r\n"
				+ "                THEN 12 \r\n"
				+ "            ELSE DATEPART(HOUR, DATEADD(SECOND, 1, START_TIME)) % 12 \r\n"
				+ "        END AS VARCHAR), 2),\r\n"
				+ "\r\n"
				+ "    @minutos = RIGHT(\r\n"
				+ "        '0' + CAST(\r\n"
				+ "            DATEPART(MINUTE, DATEADD(SECOND, 1, START_TIME)) AS VARCHAR\r\n"
				+ "        ), 2),\r\n"
				+ "\r\n"
				+ "    @ampm = CASE  \r\n"
				+ "                WHEN DATEPART(HOUR, DATEADD(SECOND, 1, START_TIME)) < 12 \r\n"
				+ "                    THEN 'AM' \r\n"
				+ "                ELSE 'PM'\r\n"
				+ "            END\r\n"
				+ "\r\n"
				+ "FROM [TIME_PERIOD]\r\n"
				+ "\r\n"
				+ "WHERE TIME_PERIOD_ID IN (\r\n"
				+ "    SELECT TIME_PERIOD_ID\r\n"
				+ "    FROM [TIME_GROUP_PERIOD_LINK]\r\n"
				+ "    WHERE TIME_GROUP_ID IN (\r\n"
				+ "        SELECT TIME_GROUP_ID\r\n"
				+ "        FROM [TIME_GROUP]\r\n"
				+ "        WHERE NAME LIKE '%ALCOHOL_RESTRICTION%'\r\n"
				+ "        AND IS_ACTIVE = '1'\r\n"
				+ "    )\r\n"
				+ ")\r\n"
				+ "\r\n"
				+ "AND CODE LIKE '%' + @dia + '%';\r\n"
				+ "\r\n"
				+ "SELECT  \r\n"
				+ "    @horaMinuto AS HoraMinuto,\r\n"
				+ "    @hora AS Hora,\r\n"
				+ "    @minutos AS Minutos,\r\n"
				+ "    @ampm AS AMPM;";
//		String querydentroHorario = "DECLARE @horaMinuto VARCHAR(5);\r\n"
//				+ "SET @horaMinuto = CONVERT(VARCHAR(5), GETDATE(), 108);\r\n"
//				+ "SELECT @horaMinuto as HoraMinuto;"; // consulta para obtener la hora del punto de venta

		switch (opcion) {
		case "fueraHorario":
//			Esta opción coloca el punto de venta en un horario fuera de horario para no realizar la venta de alcohol en caso de que la prueba se ejecuta dentro del horario
			System.out.println("Entra al case de fueraHorario del metodo cambiarHoraPV");

			try (Connection conn = DriverManager.getConnection( // Creación de la conexión a la base de datos
					urlPuntoVentaConsultar, GlobalVariables.userPVGeneral, GlobalVariables.passPVGeneral);
					// realiza la consulta del Cajero que tiene seleccionado la tienda
					PreparedStatement ps = conn.prepareStatement(queryHorario)) {
				try (ResultSet rs = ps.executeQuery()) {// Ejecución del query y confirmación de información
					if (rs.next()) {
						horaPuntoVenta = rs.getString("HoraMinuto");
						System.out.println("Hora en 24 horas del punto de venta: " + horaPuntoVenta);
						String hora = rs.getString("Hora");
						String minutos = rs.getString("Minutos");
						String ampm = rs.getString("AMPM");

						// Convertir el String de la consulta realizada al punto de venta a Int
						int horaInt = Integer.parseInt(hora);
						int minutosInt = Integer.parseInt(minutos);

						// Convertir a formato 24 horas el valor de horaInt
						if ("PM".equals(ampm) && horaInt != 12) {
							horaInt += 12;
						} else if ("AM".equals(ampm) && horaInt == 12) {
							horaInt = 0;
						}
						System.out.println("Valor horaInt despues de validar si es en AM/PM " + horaInt);
						LocalTime horaActual = LocalTime.parse(horaPuntoVenta);
						LocalTime inicio = LocalTime.of(horaInt, minutosInt);
						LocalTime fin = LocalTime.of(9, 1);

//						Se revisa si el punto de venta esta dentro ofuera del horario necesario
//						if (!(horaPuntoVenta.compareTo(""+horaInt+":"+minutosInt+"") >= 0 && horaPuntoVenta.compareTo("09:01") <= 0)) {
						if (horaActual.isBefore(inicio) || horaActual.isAfter(fin)) {
							System.out.println(
									"Se realiza la actualización del horario del punto de venta a un horario fuera de venta de alcohol");
							try {
								String comando = "cmd /c C:\\PsExecAutomatizacion\\PsExec.exe \\\\" + ip
										+ " -u QA -p Comercio03 -s cmd.exe /c \"time " + horaInt + ":" + minutosInt
										+ "";
								Process process = Runtime.getRuntime().exec(comando);
								process.waitFor();
								System.out.println(
										"Hora actualizada correctamente en el punto de venta para que este fuera del horario");
							} catch (Exception e) {
								e.printStackTrace();
								System.out
										.println("No se pudo actualizar la hora del punto de venta, favor de revisar.");
								testCase.nextStep("No se pudo actualizar la hora del punto de venta para la tienda "
										+ crTienda + " en la linea opción \"fueraHorario\", favor de revisar: " + e);
							}

						} else {
							System.out.println(
									"Se continua el caso sin afectación; el caso se esta ejecutando en un horario valido fuera de venta de alcohol.");
						}
					} else {
						System.out.println("No se pudo obtener la hora del punto de venta, favor de revisar.");
						testCase.nextStep("No se pudo obtener la hora del punto de venta para la tienda " + crTienda
								+ ", favor de revisar.");
					}
				}
			} catch (SQLException e) {
				System.err.println(
						"Catch de la consulta a base de datos local del metodo cambiarHoraPV en la opción fueraHorario. Mensaje: "
								+ e.getMessage());
				testCase.nextStep("No se pudo obtener la hora del punto de venta para la tienda " + crTienda
						+ ", favor de revisar ya que regresa el siguiente mensaje: " + e.getMessage());
			}
			break;

		case "dentroHorario":
//			Esta opción coloca el punto de venta en un horario de 11 de la mañana para poder realizar la venta de alcohol en caso de que la prueba se ejecuta fuera de horario
			System.out.println("Entra al case de dentroHorario del metodo cambiarHoraPV");

			try (Connection conn = DriverManager.getConnection( // Creación de la conexión a la base de datos
					urlPuntoVentaConsultar, GlobalVariables.userPVGeneral, GlobalVariables.passPVGeneral);
					// realiza la consulta del Cajero que tiene seleccionado la tienda
					PreparedStatement ps = conn.prepareStatement(queryHorario)) {
				try (ResultSet rs = ps.executeQuery()) {// Ejecución del query y confirmación de información
					if (rs.next()) {
						horaPuntoVenta = rs.getString("HoraMinuto");
						System.out.println("Hora en 24 horas del punto de venta: " + horaPuntoVenta);
						String hora = rs.getString("Hora");
						String minutos = rs.getString("Minutos");
						// revisar de aquí
						String ampm = rs.getString("AMPM");

						// Convertir el String de la consulta realizada al punto de venta a Int
						int horaInt = Integer.parseInt(hora);
						int minutosInt = Integer.parseInt(minutos);

						// Convertir a formato 24 horas el valor de horaInt
						if ("PM".equals(ampm) && horaInt != 12) {
							horaInt += 12;
						} else if ("AM".equals(ampm) && horaInt == 12) {
							horaInt = 0;
						}

						System.out.println("Valor horaInt despues de validar si es en AM/PM " + horaInt);
						LocalTime horaActual = LocalTime.parse(horaPuntoVenta);
						LocalTime inicio = LocalTime.of(horaInt, minutosInt);
						LocalTime fin = LocalTime.of(9, 1);

//						Se revisa si el punto de venta esta dentro o fuera del horario necesario
						if (horaActual.isBefore(inicio) == horaActual.isAfter(fin)) {
//						if (!(horaPuntoVenta.compareTo(""+horaInt+":"+minutosInt+"") >= 0 && horaPuntoVenta.compareTo("09:01") <= 0)) {						
							System.out.println(
									"Se realiza la actualización del punto de venta a un horario de las 11:00 a.m.");
							String horaN = "11";
							String minutosN = "00";
							try {
								String comando = "cmd /c C:\\PsExecAutomatizacion\\PsExec.exe \\\\" + ip
										+ " -u QA -p Comercio03 -s cmd.exe /c \"time " + horaN + ":" + minutosN + "";
								Process process = Runtime.getRuntime().exec(comando);
								process.waitFor();
								System.out.println(
										"Hora actualizada correctamente en el punto de venta para que este dentro del horario de venta de alcohol");
							} catch (Exception e) {
								e.printStackTrace();
								System.out
										.println("No se pudo actualizar la hora del punto de venta, favor de revisar.");
								testCase.nextStep("No se pudo actualizar la hora del punto de venta para la tienda "
										+ crTienda + " en la linea opción \"dentroHorario\", favor de revisar: " + e);
							}
						} else {
							System.out.println(
									"Se continua el caso sin afectación; el caso se esta ejecutando en un horario valido de venta de alcohol.");
						}
					}
				}
			} catch (SQLException e) {
				System.err.println(
						"Catch de la consulta a base de datos local del metodo validarHoraVentaAlcohol. Mensaje: "
								+ e.getMessage());
				testCase.nextStep("No se pudo obtener la hora del punto de venta para la tienda " + crTienda
						+ ", favor de revisar ya que regresa el siguiente mensaje: " + e.getMessage());
			}
			break;

		case "actualizarHorario":
//			Esta opción coloca la hora correcta dentro del punto de venta 			
			System.out.println("Entra al case de actualizarHorario del metodo cambiarHoraPV");
			String comandoInterno = "net stop w32time && net start w32time && w32tm /resync";
			String comando = "cmd /c C:\\PsExecAutomatizacion\\PsExec.exe \\\\" + ip
					+ " -u QA -p Comercio03 -s cmd /c \"" + comandoInterno + "\"";
			Process process = Runtime.getRuntime().exec(comando);
			process.waitFor();//
			break;
		}
	}

	/**
	 * Revisión de metodo para actualizar de manera remota la hora del punto de
	 * venta. Se dejan fijos los datos de usuario y password al utilizar el mismo
	 * que se implementa en la ambientación. Se puede realizar la actualización en
	 * base a la opción que se requiera fueraHorario, dentroHorario o
	 * actualizarHorario
	 * 
	 * @param opcion
	 * @author Cesar Guel
	 * @throws Exception
	 * @date 07/04/2026
	 */

	public void cambiarHoraPVD(String opcion) throws Exception {
		// Realiza la validación en la maquina local cuente con el ejecutable PsExe el
		// cual se requiere para mandar los comandos en cmd para la actualziación de la
		// hora
		existePsExe();
		// Obtiene la ip del punto de venta al cual realizar la actualziación de la hora
		String horaPuntoVenta = null;
		String urlPuntoVentaConsultar = "jdbc:sqlserver://" + GlobalVariables.ipPvD
				+ ";databaseName=XposStore;encrypt=true;trustServerCertificate=true";

		// consulta para obtener la hora del punto de venta fuera del horario en el dia
		// actual
		String queryHorario = "USE XposStore;\r\n"
				+ "\r\n"
				+ "DECLARE @dia VARCHAR(20);\r\n"
				+ "DECLARE @hora VARCHAR(2);\r\n"
				+ "DECLARE @minutos VARCHAR(2);\r\n"
				+ "DECLARE @ampm VARCHAR(2);\r\n"
				+ "DECLARE @horaMinuto VARCHAR(5);\r\n"
				+ "\r\n"
				+ "SET @horaMinuto = CONVERT(VARCHAR(5), GETDATE(), 108);\r\n"
				+ "SET @dia = DATENAME(WEEKDAY, GETDATE());\r\n"
				+ "\r\n"
				+ "SELECT  \r\n"
				+ "    @hora = RIGHT('0' + CAST(\r\n"
				+ "        CASE  \r\n"
				+ "            WHEN DATEPART(HOUR, DATEADD(SECOND, 1, START_TIME)) % 12 = 0 \r\n"
				+ "                THEN 12 \r\n"
				+ "            ELSE DATEPART(HOUR, DATEADD(SECOND, 1, START_TIME)) % 12 \r\n"
				+ "        END AS VARCHAR), 2),\r\n"
				+ "\r\n"
				+ "    @minutos = RIGHT(\r\n"
				+ "        '0' + CAST(\r\n"
				+ "            DATEPART(MINUTE, DATEADD(SECOND, 1, START_TIME)) AS VARCHAR\r\n"
				+ "        ), 2),\r\n"
				+ "\r\n"
				+ "    @ampm = CASE  \r\n"
				+ "                WHEN DATEPART(HOUR, DATEADD(SECOND, 1, START_TIME)) < 12 \r\n"
				+ "                    THEN 'AM' \r\n"
				+ "                ELSE 'PM'\r\n"
				+ "            END\r\n"
				+ "\r\n"
				+ "FROM [TIME_PERIOD]\r\n"
				+ "\r\n"
				+ "WHERE TIME_PERIOD_ID IN (\r\n"
				+ "    SELECT TIME_PERIOD_ID\r\n"
				+ "    FROM [TIME_GROUP_PERIOD_LINK]\r\n"
				+ "    WHERE TIME_GROUP_ID IN (\r\n"
				+ "        SELECT TIME_GROUP_ID\r\n"
				+ "        FROM [TIME_GROUP]\r\n"
				+ "        WHERE NAME LIKE '%ALCOHOL_RESTRICTION%'\r\n"
				+ "        AND IS_ACTIVE = '1'\r\n"
				+ "    )\r\n"
				+ ")\r\n"
				+ "\r\n"
				+ "AND CODE LIKE '%' + @dia + '%';\r\n"
				+ "\r\n"
				+ "SELECT  \r\n"
				+ "    @horaMinuto AS HoraMinuto,\r\n"
				+ "    @hora AS Hora,\r\n"
				+ "    @minutos AS Minutos,\r\n"
				+ "    @ampm AS AMPM;";
//		String querydentroHorario = "DECLARE @horaMinuto VARCHAR(5);\r\n"
//				+ "SET @horaMinuto = CONVERT(VARCHAR(5), GETDATE(), 108);\r\n"
//				+ "SELECT @horaMinuto as HoraMinuto;"; // consulta para obtener la hora del punto de venta

		switch (opcion) {
		case "fueraHorario":
//			Esta opción coloca el punto de venta en un horario fuera de horario para no realizar la venta de alcohol en caso de que la prueba se ejecuta dentro del horario
			System.out.println("Entra al case de fueraHorario del metodo cambiarHoraPV");

			try (Connection conn = DriverManager.getConnection( // Creación de la conexión a la base de datos
					urlPuntoVentaConsultar, GlobalVariables.userPVGeneral, GlobalVariables.passPVGeneral);
					// realiza la consulta del Cajero que tiene seleccionado la tienda
					PreparedStatement ps = conn.prepareStatement(queryHorario)) {
				try (ResultSet rs = ps.executeQuery()) {// Ejecución del query y confirmación de información
					if (rs.next()) {
						horaPuntoVenta = rs.getString("HoraMinuto");
						System.out.println("Hora en 24 horas del punto de venta: " + horaPuntoVenta);
						String hora = rs.getString("Hora");
						String minutos = rs.getString("Minutos");
						String ampm = rs.getString("AMPM");

						// Convertir el String de la consulta realizada al punto de venta a Int
						int horaInt = Integer.parseInt(hora);
						int minutosInt = Integer.parseInt(minutos);

						// Convertir a formato 24 horas el valor de horaInt
						if ("PM".equals(ampm) && horaInt != 12) {
							horaInt += 12;
						} else if ("AM".equals(ampm) && horaInt == 12) {
							horaInt = 0;
						}
						System.out.println("Valor horaInt despues de validar si es en AM/PM " + horaInt);
						LocalTime horaActual = LocalTime.parse(horaPuntoVenta);
						LocalTime inicio = LocalTime.of(horaInt, minutosInt);
						LocalTime fin = LocalTime.of(9, 1);

//						Se revisa si el punto de venta esta dentro ofuera del horario necesario
//						if (!(horaPuntoVenta.compareTo(""+horaInt+":"+minutosInt+"") >= 0 && horaPuntoVenta.compareTo("09:01") <= 0)) {
						if (horaActual.isBefore(inicio) || horaActual.isAfter(fin)) {
							System.out.println(
									"Se realiza la actualización del horario del punto de venta a un horario fuera de venta de alcohol");
							try {
								String comando = "cmd /c C:\\PsExecAutomatizacion\\PsExec.exe \\\\"
										+ GlobalVariables.ipPvD + " -u QA -p Comercio03 -s cmd.exe /c \"time " + horaInt
										+ ":" + minutosInt + "";
								Process process = Runtime.getRuntime().exec(comando);
								process.waitFor();
								System.out.println(
										"Hora actualizada correctamente en el punto de venta para que este fuera del horario");
							} catch (Exception e) {
								e.printStackTrace();
								System.out
										.println("No se pudo actualizar la hora del punto de venta, favor de revisar.");
								testCase.nextStep("No se pudo actualizar la hora del punto de venta para la tienda "
										+ GlobalVariables.crTiendaD
										+ " en la linea opción \"fueraHorario\", favor de revisar: " + e);
							}

						} else {
							System.out.println(
									"Se continua el caso sin afectación; el caso se esta ejecutando en un horario valido fuera de venta de alcohol.");
						}
					} else {
						System.out.println("No se pudo obtener la hora del punto de venta, favor de revisar.");
						testCase.nextStep("No se pudo obtener la hora del punto de venta para la tienda "
								+ GlobalVariables.crTiendaD + ", favor de revisar.");
					}
				}
			} catch (SQLException e) {
				System.err.println(
						"Catch de la consulta a base de datos local del metodo cambiarHoraPV en la opción fueraHorario. Mensaje: "
								+ e.getMessage());
				testCase.nextStep(
						"No se pudo obtener la hora del punto de venta para la tienda " + GlobalVariables.crTiendaD
								+ ", favor de revisar ya que regresa el siguiente mensaje: " + e.getMessage());
			}
			break;

		case "dentroHorario":
//			Esta opción coloca el punto de venta en un horario de 11 de la mañana para poder realizar la venta de alcohol en caso de que la prueba se ejecuta fuera de horario
			System.out.println("Entra al case de dentroHorario del metodo cambiarHoraPV");

			try (Connection conn = DriverManager.getConnection( // Creación de la conexión a la base de datos
					urlPuntoVentaConsultar, GlobalVariables.userPVGeneral, GlobalVariables.passPVGeneral);
					// realiza la consulta del Cajero que tiene seleccionado la tienda
					PreparedStatement ps = conn.prepareStatement(queryHorario)) {
				try (ResultSet rs = ps.executeQuery()) {// Ejecución del query y confirmación de información
					if (rs.next()) {
						horaPuntoVenta = rs.getString("HoraMinuto");
						System.out.println("Hora en 24 horas del punto de venta: " + horaPuntoVenta);
						String hora = rs.getString("Hora");
						String minutos = rs.getString("Minutos");
						// revisar de aquí
						String ampm = rs.getString("AMPM");

						// Convertir el String de la consulta realizada al punto de venta a Int
						int horaInt = Integer.parseInt(hora);
						int minutosInt = Integer.parseInt(minutos);

						// Convertir a formato 24 horas el valor de horaInt
						if ("PM".equals(ampm) && horaInt != 12) {
							horaInt += 12;
						} else if ("AM".equals(ampm) && horaInt == 12) {
							horaInt = 0;
						}

						System.out.println("Valor horaInt despues de validar si es en AM/PM " + horaInt);
						LocalTime horaActual = LocalTime.parse(horaPuntoVenta);
						LocalTime inicio = LocalTime.of(horaInt, minutosInt);
						LocalTime fin = LocalTime.of(9, 1);

//						Se revisa si el punto de venta esta dentro o fuera del horario necesario
						if (horaActual.isBefore(inicio) == horaActual.isAfter(fin)) {
//						if (!(horaPuntoVenta.compareTo(""+horaInt+":"+minutosInt+"") >= 0 && horaPuntoVenta.compareTo("09:01") <= 0)) {						
							System.out.println(
									"Se realiza la actualización del punto de venta a un horario de las 11:00 a.m.");
							String horaN = "11";
							String minutosN = "00";
							try {
								String comando = "cmd /c C:\\PsExecAutomatizacion\\PsExec.exe \\\\"
										+ GlobalVariables.ipPvD + " -u QA -p Comercio03 -s cmd.exe /c \"time " + horaN
										+ ":" + minutosN + "";
								Process process = Runtime.getRuntime().exec(comando);
								process.waitFor();
								System.out.println(
										"Hora actualizada correctamente en el punto de venta para que este dentro del horario de venta de alcohol");
							} catch (Exception e) {
								e.printStackTrace();
								System.out
										.println("No se pudo actualizar la hora del punto de venta, favor de revisar.");
								testCase.nextStep("No se pudo actualizar la hora del punto de venta para la tienda "
										+ GlobalVariables.crTiendaD
										+ " en la linea opción \"dentroHorario\", favor de revisar: " + e);
							}
						} else {
							System.out.println(
									"Se continua el caso sin afectación; el caso se esta ejecutando en un horario valido de venta de alcohol.");
						}
					}
				}
			} catch (SQLException e) {
				System.err.println(
						"Catch de la consulta a base de datos local del metodo validarHoraVentaAlcohol. Mensaje: "
								+ e.getMessage());
				testCase.nextStep(
						"No se pudo obtener la hora del punto de venta para la tienda " + GlobalVariables.crTiendaD
								+ ", favor de revisar ya que regresa el siguiente mensaje: " + e.getMessage());
			}
			break;

		case "actualizarHorario":
//			Esta opción coloca la hora correcta dentro del punto de venta 			
			System.out.println("Entra al case de actualizarHorario del metodo cambiarHoraPV");
			String comandoInterno = "net stop w32time && net start w32time && w32tm /resync";
			String comando = "cmd /c C:\\PsExecAutomatizacion\\PsExec.exe \\\\" + GlobalVariables.ipPvD
					+ " -u QA -p Comercio03 -s cmd /c \"" + comandoInterno + "\"";
			Process process = Runtime.getRuntime().exec(comando);
			process.waitFor();//
			break;
		}
	}

	/**
	 * Metodo para valida que la maquina local cuente con el archivo PsExec
	 * utilizado en cambiarHoraPV En caso de que no existe copia el archivo en la
	 * maquina local creando una carpeta donde se coloca Para facilitar la copia del
	 * PsExec.exe se toma de la ruta Mobile.POSMovil.om
	 * 
	 * @author Juan Ibarra Fecha: 07-Abril-2026
	 * @throws Exception
	 */

	public void existePsExe() throws Exception {
//		ruta en la que se validara la existencia del archivo PsExec
		String ruta = "C:\\PsExecAutomatizacion\\PsExec.exe";
		if (validarExe(ruta)) {
			System.out.println("El ejecutable PsExec.exe existe y es válido");
		} else {
			System.out.println(
					"No existe o no es ejecutable el PsExec.exe, se valida que exista la carpeta C:\\PsExec y/o colocar el ejecutable PsExec.exe en la ruta C:\\PsExec");
			String carpeta = "C:\\PsExecAutomatizacion";
			String archivoPsExec = carpeta + "\\PsExec.exe";
			try {
				File directorio = new File(carpeta);
				// Crear carpeta si no existe
				if (!directorio.exists()) {
					boolean creada = directorio.mkdirs();
					if (creada) {
						System.out.println("Carpeta PsExecAutomatizacion creada de manera local: " + carpeta);
					} else {
						System.out.println("No se pudo crear la carpeta PsExecAutomatizacion.");
						testCase.nextStep("No se pudo crear la carpeta PsExecAutomatizacion, favor de revisar.");
						return;
					}
				}

				File archivo = new File(archivoPsExec);
				// Copia archivo PsExec.exe si no existe
				if (!archivo.exists()) {
					System.out.println("Se copia archivo PsExec.exe en la carpeta C:\\PsExecAutomatizacion..");
					try {
						File source = new File(
								System.getProperty("user.dir") + "\\src\\test\\java\\Mobile\\POSMovil\\om\\PsExec.exe");
						File dest = new File("C:\\PsExecAutomatizacion");
						FileUtils.copyFileToDirectory(source, dest);
					} catch (Exception e) {
						testCase.addCodeEvidenceCurrentStep(
								"Favor de revisar. Error realizar el copiado del archivo PsExec a la carpeta local C:\\PsExecAutomatizacion: "
										+ e.getMessage());
						throw new Exception("Error: " + e.getMessage());
					}
					System.out.println("Archivo PsExec.exe creado correctamente.");
				} else {
					System.out.println("El archivo PsExec.exe ya existe.");
				}
			} catch (IOException e) {
				System.out.println("Error en existePsExe: " + e.getMessage());
				testCase.nextStep("Favor de revisar. Error en existePsExe: " + e.getMessage());
			}
		}

	}

	/**
	 * Metodo que realiza la validación donde exita un ejecutable en una ruta
	 * especifica Se esta utilizando en el metodo existePsExe() pero puede ser
	 * reutilizado en validar otras rutas y exes
	 * 
	 * @param ruta
	 * @author Juan Ibarra Fecha: 07-Abril-2026
	 * 
	 */
	public boolean validarExe(String ruta) {
		Path path = Paths.get(ruta);
		return Files.exists(path) && Files.isRegularFile(path) && Files.isExecutable(path);
	}

	/**
	 * Metodo para consultar estado de banderas
	 * 
	 * @author Brandon Sanchez
	 * @param crTienda
	 * @return
	 * @throws Exception
	 */
//	public  List<String> consultaPromotionAndRestrictionsFlg(String crTienda) throws Exception {
//		//Query consulta banderas
//		String queryConsulta = "USE XposStore;\r\n"
//				+ "\r\n"
//				+ "BEGIN TRY\r\n"
//				+ "    SELECT \r\n"
//				+ "        SCAT.CODE, \r\n"
//				+ "        SCAT.NAME, \r\n"
//				+ "        SVAL.VALUE \r\n"
//				+ "    FROM SYSTEM_PARAM_CATALOG SCAT WITH(NOLOCK)\r\n"
//				+ "    INNER JOIN SYSTEM_PARAM_VALUE SVAL WITH(NOLOCK)\r\n"
//				+ "        ON SCAT.SYSTEM_PARAM_CATALOG_ID = SVAL.SYSTEM_PARAM_CATALOG_ID\r\n"
//				+ "    WHERE SCAT.CODE IN ('PROMOTIONS_FLG', 'RESTRICTIONS_FLG');\r\n"
//				+ "END TRY\r\n"
//				+ "BEGIN CATCH\r\n"
//				+ "    SELECT \r\n"
//				+ "        ERROR_NUMBER() AS ErrorNumber,\r\n"
//				+ "        ERROR_MESSAGE() AS ErrorMessage;\r\n"
//				+ "END CATCH;";
//		//Obtiene IP y declara URL
//		String ip = obtenerIPTienda(crTienda);
//		String urlPuntoVentaConsultar = "jdbc:sqlserver://" + ip
//				+ ";databaseName=XposStore;encrypt=true;trustServerCertificate=true";
//		//Establecer conexion a BD
//		Connection conn = DriverManager.getConnection( // Creación de la conexión a la base de datos
//				urlPuntoVentaConsultar, GlobalVariables.userPVGeneral, GlobalVariables.passPVGeneral);
//				PreparedStatement ps = conn.prepareStatement(queryConsulta);
//		//Ejecucion del query
//		ResultSet rs = ps.executeQuery();
//		//Declarar lista para almacenar valores
//		List<String> value = new ArrayList<>();
//		//While para recorrer consulta
//		while(rs.next()){
//			String bandera = rs.getString("CODE"); //Bandera
//			value.add(bandera + " = " + rs.getString("VALUE")); // Inserta bandera + valor			
//		}	
//		//Obtiene valores de primera y segunda bandera
//		String primeraBandera = value.get(0);
//		String segundaBandera = value.get(1);
//		//Imprime valores de bandera
//		System.out.println(primeraBandera + "\n" + segundaBandera);
//		testCase.addTextEvidenceCurrentStep("Consulta ejecutada: \n" + queryConsulta);
//		return value;
//	}
//
//	
//	/**
//	 * Meotod para validar los valores PROMOTIONS_FLG=FALSE, RESTRICTIONS_FLG = FALSE
//	 * @author Brandon Sanchez
//	 * @param valores
//	 * @return
//	 */
//	public boolean validaPromotionAndRestrictionsFlg(List <String> valores) {
//		String primeraBandera = valores.get(0);
//		String segundaBandera = valores.get(1);
//		testCase.addTextEvidenceCurrentStep(
//			    "=========================\n" + 
//			    "===RESULTADOS===\n" + 
//			    primeraBandera + "\n" + 
//			    segundaBandera
//			);
//		return primeraBandera.equals("PROMOTIONS_FLG = FALSE") && segundaBandera.equals("RESTRICTIONS_FLG = FALSE");
//	}

	/**
	 * Metodo para hacer el apagado de las banderas PROMOTIONS_FLG, RESTRICTIONS_FLG
	 * 
	 * @author Brandon Sanchez
	 * @param crTienda
	 * @param parche
	 * @param rutaParche
	 * @throws Exception
	 */
	public void ejecucionParches(String crTienda, String parche, String rutaParche, String valor) throws Exception {
		encenderVPN();
		// String ip = obtenerIPTienda(crTienda);
		if (rutaParche.endsWith("\\")) {
			rutaParche = rutaParche.substring(0, rutaParche.length() - 1);
		}
		String rutaCompleta = rutaParche + "\\" + parche + ".exe";
		String comando = "cmd /c C:\\\\PsExecAutomatizacion\\\\PsExec.exe \\\\" + GlobalVariables.ipPvD + " -u QA -p Comercio03 -s cmd.exe /c \""
				+ rutaCompleta + " ACTIVE=" + valor + "\"";
		System.out.println("Comando ejecutado: " + comando);
		Process process = Runtime.getRuntime().exec(comando);
		process.waitFor();
		System.out.println("PARCHE EJECUTADO");
	}

	/**
	 * 
	 */
	public void validarYLevantarAppium() {
		try {
			ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "tasklist");
			Process process = builder.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String linea;
			boolean appiumActivo = false;
			// Buscar si Appium está en ejecución
			while ((linea = reader.readLine()) != null) {
				if (linea.toLowerCase().contains("node.exe") || linea.toLowerCase().contains("appium")) {
					appiumActivo = true;
					break;
				}
			}

			if (appiumActivo) {
				System.out.println("Appium ya está en ejecución.");
			} else {
				System.out.println("Appium NO está en ejecución. Iniciando...");

				// Comando para iniciar Appium
				ProcessBuilder startAppium = new ProcessBuilder("cmd.exe", "/c", "start cmd /k appium");
				startAppium.start();
				System.out.println("Appium iniciado correctamente.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo - Reiniciar apk
	 * 
	 * @author Cesar Guel
	 * @param appPackage
	 * @throws Exception
	 * @date 21/04/2026
	 */
	public void reiniciarAPK(String appPackage) throws Exception {
		try {
			ejecutarADB("am force-stop " + appPackage);
			Thread.sleep(2000);
			System.out.println("Se cierra apk");
			ejecutarADB("monkey -p " + appPackage + " -c android.intent.category.LAUNCHER 1");
			Thread.sleep(2000);
			System.out.println("Se inicia apk");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Metodo que regresa la contraseña segun se tenga un cajero seleccionado o no
	 * regresa la contraseña estandar para cada caso
	 * 
	 * @author Juan Ibarra*
	 * @param crTienda
	 * @return contrasena
	 * @throws Exception
	 * @date 05-Mayo-2026
	 */
	public String passUser(String crTienda) throws Exception {
		// Se realiza validación del usuario cajero seleccionado en XPOS
		String ip = obtenerIPTienda(crTienda);
		String usuarioCajero = null;
		// consulta para obtener el numero de usuario seleccionado
		String urlPuntoVentaConsultar = "jdbc:sqlserver://" + ip
				+ ";databaseName=XposStore;encrypt=true;trustServerCertificate=true";
		String query = "SELECT USER_NAME FROM [XposStore].[dbo].[OPERATOR] WHERE IS_ACTIVE = 1 and OPERATOR_STATUS_CODE_ID = 3 and FL_INTRN = 0  and PASSWORD is not null";
		String contrasena = null;
		try (Connection conn = DriverManager.getConnection( // Creación de la conexión a la base de datos
				urlPuntoVentaConsultar, GlobalVariables.userPVGeneral, GlobalVariables.passPVGeneral);
				PreparedStatement ps = conn.prepareStatement(query)) { // realiza la consulta del Cajero que tiene
																		// seleccionado la tienda
			try (ResultSet rs = ps.executeQuery()) {// Ejecución del query y confirmación de información
				if (rs.next()) {
					usuarioCajero = rs.getString("USER_NAME");
					System.out.println("Usuario Cajero seleccionado: " + usuarioCajero);
					contrasena = GlobalVariables.passCajero;
//						return contrasena;
				} else {
					System.out.println("No se encontro Usuario Cajero seleccionado, se maneja como No Usuario.");
					contrasena = GlobalVariables.passNoCajero;
//						return contrasena;
				}
			}
		} catch (SQLException e) {
			System.err.println(
					"Catch de la consulta a base de datos local del metodo passUser. Mensaje: " + e.getMessage());
			testCase.nextStep("No se encontró un Cajero seleccionado para la tienda " + crTienda
					+ ", favor de revisar ya que regresa el siguiente mensaje: " + e.getMessage());
		}
		return contrasena;

	}

	/**
	 * Revisión de metodos de highlight
	 * PENDIENTE DE CONFIRMAR FUNCIONALIDAD 
	 * @author Juan Ibarra
	 */

	public String highlight(By by, String nombreImagen) {
		System.out.println("entra a String highlight(By by, String nombreImagen) { " );
		WebElement webElement = this.waitWebElement(by);

		return highlightElement(webElement, nombreImagen);
	}

	private WebElement waitWebElement(By by) {
		// TODO Auto-generated method stub
		System.out.println("entra a private WebElement waitWebElement(By by) {" );
		return null;
	}

	public String highlight(WebElement element, String nombreImagen) {

		return highlightElement(element, nombreImagen);
	}

	public String highlightElement(WebElement element, String nombreImagen) {
		System.out.println("entra a String highlightElement(WebElement element, String nombreImagen) {" );

		try {

			/**
			 * Sanitizar nombre archivo
			 */
			nombreImagen = nombreImagen.replaceAll("[^a-zA-Z0-9-_]", "_");

			/**
			 * Carpeta evidencias
			 */
			String carpeta = System.getProperty("user.dir") + "/evidencias/";

			File directorio = new File(carpeta);

			if (!directorio.exists()) {
				directorio.mkdirs();
			}

			/**
			 * Ruta final imagen
			 */
			String rutaCompleta = carpeta + nombreImagen + ".png";

			/**
			 * Tomar screenshot dispositivo
			 */
			File srcFile = au.driver.getScreenshotAs(OutputType.FILE);

			BufferedImage imagen = ImageIO.read(srcFile);

			/**
			 * Obtener coordenadas del elemento
			 */
			Point point = element.getLocation();

			/**
			 * Obtener tamaño del elemento
			 */
			Dimension size = element.getSize();

			int x = point.getX();

			int y = point.getY();

			int width = size.getWidth();

			int height = size.getHeight();

			/**
			 * Dibujar rectángulo rojo
			 */
			Graphics2D g2d = imagen.createGraphics();

			g2d.setColor(Color.YELLOW);

			g2d.setStroke(new BasicStroke(5));

			g2d.drawRect(x, y, width, height);

			/**
			 * Opcional: Fondo semitransparente
			 */
			g2d.setColor(new Color(255, 0, 0, 40));

			g2d.fillRect(x, y, width, height);

			g2d.dispose();

			/**
			 * Guardar imagen final
			 */
			ImageIO.write(imagen, "png", new File(rutaCompleta));

			System.out.println("Highlight generado: " + rutaCompleta);

			return rutaCompleta;

		} catch (Exception e) {

			e.printStackTrace();

			return null;
		}
	}

}