package Mobile.util.winium;

import java.awt.Graphics;
import java.awt.Window;
import java.io.BufferedReader;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.io.File;
import java.io.IOException;

//package utils.winium;

import java.io.InputStreamReader;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import config.Constants;
import exceptions.FrameworkException;

/**
 * Clase de utilería para funciones genéricas con Winium.
 * Provee métodos para simplificar el uso de Winium, incluyendo manejo automático del driver y fluent waits
 * 
 * @author Roberto Flores
 * @date 30/Mayo/2024
 */
public class WiniumUtil implements utils.TakesScreenshot {

	private static final Logger logger = LoggerFactory.getLogger(WiniumUtil.class);

	private static Window currentHighlightWindow = null;
    private static Timer currentTimer = null;
	
	private Duration totalWaitTimeSeconds;
	private Duration intervalWaitTimeMiliseconds;
	private int defaultHighlightSeconds;

	//private WebDriver driver;
	private WiniumDriver driver;

	private String driverPath;
	private int driverPort;

	private String applicationPath;

	private Process winiumProcess;
	

	/**
	 * Constructor WiniumUtil, se inicializará el driver automáticamente y programar para eliminarse al finalizar la ejecución
	 * @param applicationPath - Ruta de la aplicación que se automatizará 
	 * @param driverPort - Puerto donde se inicializar el driver de Winium en el localhost
	 * @param startApplication - Indicar si se abrir la aplicación o se reutilizará la aplicación previamente abierta
	 * @throws FrameworkException - Excepción en caso de que ocurra algún error iniciando el driver de winium
	 */
	public WiniumUtil(String applicationPath, int driverPort, boolean startApplication) throws FrameworkException {
		this.applicationPath = applicationPath;
		this.driverPort = driverPort;
		this.totalWaitTimeSeconds = Duration.ofSeconds(15);
		this.intervalWaitTimeMiliseconds = Duration.ofMillis(600);
		this.defaultHighlightSeconds = 3;

		this.driverPath = Constants.WINIUM_DRIVER_PATH;
		initializeDriver(driverPath, applicationPath, driverPort, startApplication);
	}

	/**
	 * Constructor WiniumUtil, se inicializará el driver automáticamente y programar para eliminarse al finalizar la ejecución
	 * @param applicationPath - Ruta de la aplicación que se automatizará 
	 * @param startApplication - Indicar si se abrir la aplicación o se reutilizará la aplicación previamente abierta
	 * @throws FrameworkException - Excepción en caso de que ocurra algún error iniciando el driver de winium
	 */
	public WiniumUtil(String applicationPath, boolean startApplication) throws FrameworkException {
		this(applicationPath, 9999, startApplication);
	}

	/**
	 * Inicializa el proceso de winium en localhost y el driver
	 * @param driverPath - Ruta del driver ejecutable de winium
	 * @param applicationPath - Ruta de la aplicación que se automatizará
	 * @param driverPort - Puerto en donde se inicializará el ejecutable de winium
	 * @param startApplication - boolean que indica si la aplicación se abrir o reutilizará una previamnete abierta
	 * @throws FrameworkException - Excepción en caso de que ocurra algún error iniciando el driver de winium
	 */
	private void initializeDriver(String driverPath, String applicationPath, int driverPort, boolean startApplication)
			throws FrameworkException {
		DesktopOptions options = new DesktopOptions();
		options.setApplicationPath(applicationPath);

		if (!startApplication) {
			options.setDebugConnectToRunningApp(true);
		}

		try {
			this.winiumProcess = Runtime.getRuntime().exec(driverPath + " --port " + this.driverPort);
			Thread.sleep(3000);
			this.driver = new WiniumDriver(new URL("http://localhost:" + this.driverPort), options);
			Runtime.getRuntime().addShutdownHook(new Thread(this::quitDriver));
		} catch (Exception e) {
			logger.error("Unable to start Winium driver", e);
			throw new FrameworkException("Error al iniciar el driver de winium", e);
		}
	}

	/**
	 * Método para cerrar el driver y sesión de Winium
	 */
	public void quitDriver() {
		if (driver != null) {
			try {
				driver.quit();
			} catch (Exception e) {
				System.out.println("Warning: driver previamente terminado");
			}
		}
		if (winiumProcess != null) {
			winiumProcess.destroy();
			try {
				winiumProcess.waitFor(5, TimeUnit.SECONDS); // Wait for the process to terminate
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Método para cerrar una aplicación usando comandos de windows
	 * @param processName - Nombre del proceso a cerrar
	 */
	public static void closeApplicationUsingCommand(String processName) {
		try {
			// Use tasklist to get the PID of the process
			Process listProcess = Runtime.getRuntime().exec("tasklist /FI \"IMAGENAME eq " + processName + "\"");
			BufferedReader reader = new BufferedReader(new InputStreamReader(listProcess.getInputStream()));
			String line;
			int pid = -1;
			while ((line = reader.readLine()) != null) {
				if (line.contains(processName)) {
					String[] parts = line.split("\\s+");
					pid = Integer.parseInt(parts[1]); // Get the PID
					break;
				}
			}
			reader.close();

			// If PID is found, kill the process
			if (pid != -1) {
				Process killProcess = Runtime.getRuntime().exec("taskkill /F /PID " + pid);
				killProcess.waitFor(); // Wait for the command to complete
				int exitCode = killProcess.exitValue();
				if (exitCode != 0) {
					System.out.println("Failed to close application. Exit code: " + exitCode);
				}
			} else {
				System.out.println("Process not found: " + processName);
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Espera a que un elemento esté visible utilizando un fluent wait
	 * @param by El localizador By del objeto que va a esperar
	 * @return El WebElement una vez que esté visible
	 * @throws TimeoutException si el elemento no es visible en el tiempo definido en la clase
	 */
	public WebElement waitWebElement(By by) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(totalWaitTimeSeconds)
				//.withTimeout(totalWaitTimeSeconds, TimeUnit.SECONDS)
				.pollingEvery(intervalWaitTimeMiliseconds)
				.ignoring(NoSuchElementException.class);

		WebElement element = wait.until(new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(by);
			}
		});

		return element;
	}

	/**
	 * Espera a que al menos un elemento esté visible utilizando un fluent wait
	 * @param by El localizador By del objeto que va a esperar
	 * @return Una lista de WebElement una vez que encuentre al menos un elemento que cumpla con el localizador
	 * @throws TimeoutException si el elemento no es visible en el tiempo definido en la clase
	 */
	public List<WebElement> waitWebElements(By by) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(totalWaitTimeSeconds)
				.pollingEvery(intervalWaitTimeMiliseconds)
				.ignoring(NoSuchElementException.class);

		List<WebElement> elements = wait.until(new ExpectedCondition<List<WebElement>>() {
			public List<WebElement> apply(WebDriver driver) {
				List<WebElement> elements = driver.findElements(by);
				if (elements.isEmpty()) {
					return null; // Return null to keep waiting
				}
				return elements; // Return the list of elements when they are found
			}
		});

		return elements;
	}

	/**
	 * Espera el tiempo indicado
	 * @param seg el tiempo en segundos de lo que debe esperar para seguir con la ejecución
	 */
	public void hardWait(int seg) {
		try {
			Thread.sleep(seg * 1000);
		} catch (InterruptedException e) {
			logger.error("Error en hardWait", e);
		}
	}

	/**
	 * Realiza un click al elemento definido
	 * @param by Identificador al cual se dará click
	 */
	public void click(By by) {
		waitWebElement(by).click();
	}
	

	/**
	 * Realiza la acción de escribir en un elemento 
	 * @param by Identificador en el cual se escribir el texto
	 * @param str texto que se escribirá
	 */
	public void sendKeys(By by, String str) {
		waitWebElement(by).sendKeys(str);
	}
	
	/**
	 * Revisa si un elemento existe actualmente en la pantalla
	 * @param by elemento a buscar
	 * @return true si encuentra el elemento
	 */
	public boolean elementExists(By by, Duration timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver,timeout);
			//wait.ignoring(Exception.class);
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
		} catch (Exception e) {
			return false;
		}
	return true;
	}
	
	/**
	 * Resalta un elemento en la pantalla por un periodo de tiempo (3 segundos por defecto)
	 * @param by Identificador del elemento a resaltar en la pantalla
	 */
	public void highlight(By by) {
		WebElement webElement = this.waitWebElement(by);
		WiniumUtil.highlightElement(webElement, defaultHighlightSeconds);
	}
	
	/**
	 * Resalta un elemento en la pantalla por un periodo de tiempo (3 segundos por defecto)
	 * @param element Identificador del elemento a resaltar en la pantalla
	 */
	public void highlight(WebElement webElement) {
		WiniumUtil.highlightElement(webElement, defaultHighlightSeconds);
	}

	/**
	 * Realiza una captura de pantalla utilizando el driver de winium
	 */
	@Override
	public void takeScreenshot(String path) {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(path));
		} catch (IOException e) {
			logger.error("Failed to take screenshot", e);
		}
	}

	/**
	 * Resalta un elemento en la pantalla por un periodo de tiempo definido
	 * @param element elemento el cual se resaltará
	 */
	public static void highlightElement(WebElement element, int highlightSeconds) {
		// Stop and dispose previous highlight if it exists
        if (currentHighlightWindow != null) {
        	currentHighlightWindow.setVisible(false);
        	currentHighlightWindow.dispose();
        }
        if (currentTimer != null) {
            currentTimer.cancel();
        }
		
		// Get the BoundingRectangle attribute
        String boundingRect = element.getAttribute("BoundingRectangle");
        int[] coordinates = parseBoundingRectangle(boundingRect);
        if (coordinates != null) {
            int x = coordinates[0];
            int y = coordinates[1];
            int width = coordinates[2];
            int height = coordinates[3];
		

	        currentHighlightWindow = drawRectangleOnTopScreen(x, y, width, height);
	        
	        currentTimer = new Timer();
	        currentTimer.schedule(new java.util.TimerTask() {
	            @Override
	            public void run() {
	            	currentHighlightWindow.setVisible(false);
	            	currentHighlightWindow.dispose();
	                currentTimer.cancel();
	            }
	        }, highlightSeconds*1000); 
        } else {
        	System.out.println("Failed to parse BoundingRectangle.");
        }
    }

	private static int[] parseBoundingRectangle(String boundingRect) {
        try {
            String[] parts = boundingRect.split(",");
            int x = Integer.parseInt(parts[0].trim());
            int y = Integer.parseInt(parts[1].trim());
            int width = Integer.parseInt(parts[2].trim());
            int height = Integer.parseInt(parts[3].trim());
            return new int[]{x, y, width, height};
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

	public static Window drawRectangleOnTopScreen(int x, int y, int width, int height) {
//		System.out.println(x);
//		System.out.println(y);
//		System.out.println(width);
//		System.out.println(height);
		Window w = new Window(null) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void paint(Graphics g) {
				Stroke stroke1 = new BasicStroke(3f);
				g.setColor(Color.RED);
				((Graphics2D) g).setStroke(stroke1);
				g.drawRect(x, y, width, height);

			}

			@Override
			public void update(Graphics g) {
				paint(g);
			}
		};
		w.setAlwaysOnTop(true);
		w.setBounds(w.getGraphicsConfiguration().getBounds());
		w.setBackground(new Color(0, true));
		w.setVisible(true);
		return w;
	}

	// Getters and setters

	public Duration getTotalWaitTimeSeconds() {
		return totalWaitTimeSeconds;
	}

	public void setTotalWaitTimeSeconds(Duration totalWaitTimeSeconds) {
		this.totalWaitTimeSeconds = totalWaitTimeSeconds;
	}

	public Duration getIntervalWaitTimeMiliseconds() {
		return intervalWaitTimeMiliseconds;
	}

	public void setIntervalWaitTimeMiliseconds(Duration intervalWaitTimeMiliseconds) {
		this.intervalWaitTimeMiliseconds = intervalWaitTimeMiliseconds;
	}

	public WiniumDriver getDriver() {
		return driver;
	}

	public void setDriver(WiniumDriver driver) {
		this.driver = driver;
	}

	public String getDriverPath() {
		return driverPath;
	}

	public void setDriverPath(String driverPath) {
		this.driverPath = driverPath;
	}

	public int getDriverPort() {
		return driverPort;
	}

	public void setDriverPort(int driverPort) {
		this.driverPort = driverPort;
	}

	public String getApplicationPath() {
		return applicationPath;
	}

	public void setApplicationPath(String applicationPath) {
		this.applicationPath = applicationPath;
	}

	public Process getWiniumProcess() {
		return winiumProcess;
	}

	public void setWiniumProcess(Process winiumProcess) {
		this.winiumProcess = winiumProcess;
	}

	public int getDefaultHighlightSeconds() {
		return defaultHighlightSeconds;
	}

	public void setDefaultHighlightSeconds(int defaultHighlightSeconds) {
		this.defaultHighlightSeconds = defaultHighlightSeconds;
	}

	
}
