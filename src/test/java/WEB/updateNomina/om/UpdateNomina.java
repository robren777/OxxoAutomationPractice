package WEB.updateNomina.om;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import WEB.util.AccessControl;
import modelo.TestCase;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.SeleniumUtil;

public class UpdateNomina{
	private SeleniumUtil u;
	private WebDriverWait wait;
	private TestCase testCase;
	private AccessControl AC;
	
	//Elementos SADEL
	By imgNomina = By.xpath("/html/body/div[2]/table/tbody/tr[2]/td[2]/table/tbody/tr[1]/td[2]/table/tbody/tr[1]/td[2]/table/tbody/tr[1]/td[2]/a/img");
	//By imgRealizarMovimientos = By.xpath("/html/body/div[2]/table/tbody/tr[2]/td[2]/table/tbody/tr[1]/td[2]/table/tbody/tr[1]/td[2]/table/tbody/tr[1]/td[3]/a/img");
	By imgRealizarMovimientos = By.xpath("//img[@name='Image44' and @alt='Realizar Movimientos']");
	By imgEmpleados = By.xpath("/html/body/div[2]/table/tbody/tr[2]/td[2]/table/tbody/tr[1]/td[2]/table/tbody/tr[1]/td[2]/table/tbody/tr[1]/td[5]/a/img");
	By imgPendientesAnteIMSS = By.xpath("//img[@name='Image47' and @alt='Pendientes ante IMSS']");
	
	By thDiasTranscurridos = By.xpath("//*[@id=\"8\"]");
	
	//Elementos Control M
	By buttonAvanzado = By.xpath("//*[@id='details-button']");
	By aContinuarASitioNoSeguro = By.xpath("//*[@id='proceed-link']");
	By aServices = By.xpath("//*[@class='ViewpointsHome tab-item--link my-children-show-key-navigation_outline-width-0']");
	
	/**
	 * Constructor.
	 * 
	 * @param u
	 * @param testCase
	 * @author Mario Alberto De Leon Rdz.
	 */
	public UpdateNomina(SeleniumUtil u, TestCase testCase) {
	    super(); // Llama al constructor de la clase padre (en este caso, Object)
	    this.u = u; // Asigna el objeto SeleniumUtil pasado como parámetro a la variable de instancia
	    this.wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(60)); // Inicializa WebDriverWait con el driver de SeleniumUtil y un timeout de 60 segundos
	    this.testCase = testCase; // Asigna el objeto TestCase pasado como parámetro a la variable de instancia
	}
	
	/**
	 * Metodo para constantemente detectar si alertas activas y aceptarlas automaticamente
	 * @author Mario A. De Leon Rdz.
	 */
	public void AceptarTodasLasAlertas() {
		boolean alertActiva = true;
        while(alertActiva) {
        	try {
				testCase.addScreenShotCurrentStep("alerta pendiente");
				u.hardWait(1);
        		u.getDriver().switchTo().alert().accept();
        		//TODO: Considerar a�adir  Thread.sleep(500); para esperar 0.5 segundos a que salgan nuevas alertas
        	}catch(Exception e){
        		alertActiva = false;
        	}
        }
	}
	
	/**
	 * Metodo para activar la tecla Flecha Abajo segun el numero de iteraciones que se indiquen, despues de terminar las iteraciones se activa la tecla enter
	 * @iteraciones numero de veces que se va a presionar la tecla
	 * @author Mario A. De Leon Rdz.
	 */
	public void robotKeyDown(int iteraciones) {
		System.out.println("inicia robot");
		Robot robot;
		try {
			robot = new Robot();
			for(int i = iteraciones; i > 0; i--) {
				robot.keyPress(KeyEvent.VK_DOWN);
				System.out.println("Press down Numero: "+ i);
				robot.keyRelease(KeyEvent.VK_DOWN);
				System.out.println("Release down Numero:"+ i);
			}
			
			robot.keyPress(KeyEvent.VK_ENTER);
			System.out.println("Press enter");
			robot.keyRelease(KeyEvent.VK_ENTER);
			System.out.println("Release enter");
			
		} catch (AWTException e) {
			e.printStackTrace();
		}
		System.out.println("termina robot");
	}
	
	/* 
	 * M�todo que valida la entrada a la secci�n de "Pendientes Ante IMSS" y toma una captura de pantalla.
	 * 
	 * Este m�todo espera a que el elemento de la imagen "Pendientes Ante IMSS" est� disponible para hacer clic,
	 * luego espera que la columna "D�as Transcurridos" tambi�n est� disponible, y finalmente toma una captura de pantalla.
	 * En caso de fallar, cierra la sesi�n y lanza un error.
	 * 
	 * @author Mario A. De Leon Rdz.
	 */
	public void ValidarEnPendientesAnteIMSS() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(imgPendientesAnteIMSS));
			u.click(imgPendientesAnteIMSS);
			wait.until(ExpectedConditions.elementToBeClickable(thDiasTranscurridos));
			u.click(thDiasTranscurridos);
			testCase.addScreenShotCurrentStep(u, "imgPendientesAnteIMSS");
			
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo dar click en enlace para ingresar a Pendientes Ante IMSS");
		}
	}
	
	/* 
	 * M�todo que realiza el login en la plataforma "Control M" y ejecuta un job en el sistema.
	 * 
	 * @param CM       instancia de la clase Control_mInicio que maneja el login en la plataforma
	 * @param controlM instancia de la clase ControlM que realiza la b�squeda y ejecuci�n del job
	 * 
	 * El m�todo navega hacia la URL del sistema Control M, realiza el login usando el m�todo logOn() de Control_mInicio,
	 * toma una captura de pantalla despu�s del login, busca el job a ejecutar y, si lo encuentra, procede a ejecutarlo.
	 * Finalmente, se verifican los resultados del job y se almacenan las evidencias del resultado y la salida generada.
	 * 
	 * @author Mario A. De Leon Rdz.
	 */
	public void logInControlMyEjecutarJob(Control_mInicio CM, ControlM controlM) throws Exception {
		try {
	        // Navegar a la URL de Control M
	        u.get("https://172.26.177.68:8446/ControlM/");
	        u.waitForLoadPage();
	        
	        // Click en "Avanzado" y continuar en sitio no seguro
	        u.click(buttonAvanzado);
	        u.click(aContinuarASitioNoSeguro);
	        
	        // Realizar el login
	        CM.logOn();
	        testCase.addScreenShotCurrentStep(u, "control m inicio");
	        System.out.println("Login exitoso en Control M.");
	        
	        // TERMINA LOGIN
	        // INICIA BUSQUEDA Y EJECUCION DEL JOB
	        boolean flag = controlM.searchJob();
	        assertFalse(!flag, "No se encontr� el job");
	        System.out.println("Estado del Job: " + controlM.getCurrentStatus());

	        // Ejecutar el job y obtener el resultado
	        String resultadoDelJob = controlM.executeJob();
	        testCase.addTextEvidenceCurrentStep("Resultado de la ejecuci�n: " + resultadoDelJob);
	        System.out.println("Resultado de la ejecuci�n -->" + resultadoDelJob);

	        // Guardar el output del job
	        testCase.addTextEvidenceCurrentStep("Output: " + controlM.getOutput());
	        System.out.println("Output: " + controlM.getOutput());

	        // Verificar si la ejecuci�n fue exitosa
	        boolean testResult = !resultadoDelJob.equals("Failure");
	        if (!testResult) {
	            testCase.addTextEvidenceCurrentStep("Fall� la ejecuci�n del job.");
	            System.out.println("Fall� la ejecuci�n del job.");
	        }

	        assertTrue(testResult, "La ejecuci�n del job fue exitosa");

	        // Cerrar Viewpoint
	        controlM.closeViewpoint();
	    } catch (Exception e) {
	        System.out.println("Error en la ejecuci�n de logInControlMyEjecutarJob: " + e.getMessage());
	        throw new Exception("Error al ejecutar el job despu�s del login en Control M", e);
	    }
	}
	/* 
	 * M�todo que ejecuta un job en la plataforma "Control M" a partir de una pesta�a abierta en "Services".
	 * !!Se utiliza despues del metodo logInControlMyEjecutarJob() para no volver a ingresar y ejeuctar un job distinto en la misma sesi�n 
	 * 
	 * @param controlM instancia de la clase ControlM que maneja la b�squeda y ejecuci�n del job
	 * 
	 * El m�todo hace clic en la pesta�a "Services" para abrir una nueva vista y buscar el job a ejecutar.
	 * Luego verifica si el job fue encontrado y procede con su ejecuci�n. 
	 * Al final, se almacenan las evidencias del resultado y la salida generada.
	 * 
	 * @author Mario A. De Leon Rdz.
	 */
	public void ejecutarJob( ControlM controlM) throws Exception {
		try {
	        // CLICK EN PESTA�A SERVICES PARA PODER ABRIR UN NUEVO VIEWPOINT
	        wait.until(ExpectedConditions.elementToBeClickable(aServices));
	        u.click(aServices);
	        
	        // INICIA BUSQUEDA Y EJECUCION DEL JOB
	        boolean flag = controlM.searchJob();
	        assertFalse(!flag, "No se encontr� el job");
	        System.out.println("Job Status " + controlM.getCurrentStatus());
	        
	        String resultadoDelJob = controlM.executeJob();
	        testCase.addTextEvidenceCurrentStep("Resultado de la ejecuci�n " + resultadoDelJob);
	        System.out.println("Resultado de la ejecuci�n " + resultadoDelJob);

	        testCase.addTextEvidenceCurrentStep("Output " + controlM.getOutput());
	        System.out.println("Output " + controlM.getOutput());

	        boolean testResult = !resultadoDelJob.equals("Failure");
	        if (!testResult) {
	            testCase.addTextEvidenceCurrentStep("Fall� la ejecuci�n del job ");
	            System.out.println("Fall� la ejecuci�n del job ");
	        }

	        assertTrue(testResult, "La ejecuci�n del job fue exitosa");
	    } catch (Exception e) {
	        System.out.println("Error al ejecutar el job: " + e.getMessage());
	        throw new Exception("Error en la ejecuci�n del job", e);
	    }
	}
	
	
} 