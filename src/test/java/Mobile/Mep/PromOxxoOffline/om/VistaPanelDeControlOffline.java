package Mobile.Mep.PromOxxoOffline.om;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

/**
 * DESCRIPCIÓN: Page Object Model que representa el Panel de Control (Dashboard)
 * en modo sin conexión. Contiene los localizadores y métodos de interacción/validación
 * para el banner de modo sin conexión, opciones de Evaluación MEP, cierre de sesión
 * y notificaciones de restablecimiento de conexión.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *   - Casos de prueba del módulo PromOXXO Offline relacionados con el Panel de Control.
 *
 * PREREQUISITOS:
 *   - El usuario ha iniciado sesión correctamente en modo sin conexión.
 *   - El usuario está en la pantalla "Panel de Control".
 *
 * INSUMOS:
 *   - N/A
 *
 * @author Mario A. De Leon Rdz.
 * @date 22/Diciembre/2025
 */
public class VistaPanelDeControlOffline {
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
    private AppiumUtil au;
    private BaseExecution baseExecution;
    
    //Localizador de OM VistaOAuth0Offline
    private final By btnSiguienteNuevoUsuario = AppiumBy.accessibilityId("Siguiente");
    /*
	 * Localizadores Panel de Control
	 ******************************************************************************************/
    private final By iconUsuario = AppiumBy.androidUIAutomator("new UiSelector().className(\"com.horcrux.svg.PathView\").instance(4)");
    private final By btnCerrarSesion = AppiumBy.accessibilityId("Cerrar Sesión");
    private final By btnModalCerrarSesion = AppiumBy.accessibilityId("Cerrar sesión");
	private final By textEstasTrabajandoSinConexion = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"collapsible-banner-title\")");
	private final By collapsibleBannerContainer = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"collapsible-banner-container\")");
	private final By nonCollapsedBannerSinConexion = AppiumBy.accessibilityId("Estás trabajando en modo sin conexión, Algunas funciones pueden estar limitadas hasta que se restablezca la conexión, pero guardaremos tu progreso.");
	private final By collapsedBannerSinConexion = AppiumBy.accessibilityId("Estás trabajando en modo sin conexión");
	private final By titleOtrasFunciones = By.xpath("//android.widget.TextView[@text=\"Otras funciones\"]");
	private final By opcionEvaluacionMep = AppiumBy.accessibilityId("Evaluación MEP");
	private final By opcionResultadoEvaluacionMep = AppiumBy.accessibilityId("Resultado Evaluación MEP");
	private final By textTuConexionHaSidoRestablecida =By.xpath("//android.widget.TextView[contains(@text,'restablecida')]");
	private final By btnIrAIniciarSesion = AppiumBy.accessibilityId("Ir a Iniciar Sesión");
	
    /**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase Panel De Control 
	 * @author Mario A. De Leon Rdz.
	 * @param au (AppiumUtil): objeto de la clase 
	 * @param testCase (TestCase): objeto de la clase
	 */
    public VistaPanelDeControlOffline(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
        this.testCase = testCase;
        this.au = au;
        this.baseExecution = baseExecution;
    }    
    
	/**
	 * Valida que se muestre el mensaje "Estas trabajando en modo sin conexión" en el dashboard principal.
	 * 
	 * @author Mario A. De León Rdz.
	 * @return boolean Retorna {@code true} si el mensaje es visible.
	 * @date 22/Diciembre/2025
	 */
	public boolean validarDashboardOffline() {
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOfElementLocated(textEstasTrabajandoSinConexion));
			testCase.addScreenShotCurrentStep(au, "Pantalla Inicial");
			testCase.addTextEvidenceCurrentStep(" la pantalla \"Offline Dashboard\", la cual tiene: - Encabezado con nombre de la tienda"
					+ " - Mensaje rojo \"Estás trabajando en modo sin conexión\" - Opción \"Evaluación MEP\"");
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * Valida que se muestre el mensaje "Estas trabajando en modo sin conexión" en el dashboard principal.
	 * 
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception El metodo falla el test en caso de que no se encuentre el elemento esperado.
	 * @date 22/Diciembre/2025
	 */
	public void validarDashboardOfflineSinEv() throws Exception {
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOfElementLocated(textEstasTrabajandoSinConexion));
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
    		e.printStackTrace();
	        throw new Exception("No se encuentra el titulo 'Estas trabajando en modo sin conexion' en el dashboard", e);
		}
	}
	/**
	 * Valida que se muestre el mensaje "Estás trabajando en modo sin conexión"
	 * en el dashboard principal.
	 * @param isCollapsed True para validar que el banner esté contraído | False para validar que el banner esté expandido
	 * @author Mario A. De León Rdz.
	 * @return boolean Retorna {@code true} si el mensaje es visible.
	 * @date 22/Diciembre/2025
	 */
	public boolean validarCollapsibleBannerEstasTrabajandoSinConexion(boolean isCollapsed) {
	    try {
	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));

	        By bannerLocator = isCollapsed
	                ? collapsedBannerSinConexion
	                : nonCollapsedBannerSinConexion;

	        wait.until(ExpectedConditions.visibilityOfElementLocated(bannerLocator));

	        testCase.addScreenShotCurrentStep(au, "Pantalla Inicial");
	        testCase.addTextEvidenceCurrentStep(
	                "El mensaje \"Estás trabajando en modo sin conexión\" está "
	                        + (isCollapsed ? "contraído" : "expandido")
	        );

	        return true;
	    } catch (Exception e) {
	        return false;
	    }
	}

	/**
	 * Valida que se muestre el dashboard principal de Al Dia
	 * 
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 18/Diciembre/2025
	 */
    public void validarVistaPrincipal() throws Exception {
    	try {
    		au.driver.findElement(titleOtrasFunciones).isDisplayed();
    		testCase.addScreenShotCurrentStep(au, "testImage");
    	} catch (Exception e) {
    		testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
    		e.printStackTrace();
	        throw new Exception("Error al dar clic en Actualizar (Offline)", e);
    	}
    }
    
	/**
	 * Valida que se muestre el dashboard principal de Al Dia
	 * 
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 18/Diciembre/2025
	 */
    public void validarVistaPrincipalSinEv() throws Exception {
    	try {
    		au.driver.findElement(titleOtrasFunciones);
    	} catch (Exception e) {
    		testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
    		e.printStackTrace();
	        throw new Exception("Error al dar clic en Actualizar (Offline)", e);
    	}
    }
    
	/**
	 * Valida que se muestre el mensaje "Estas trabajando en modo sin conexión" en el dashboard principal.
	 * 
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception El metodo falla el test en caso de que no se encuentre el elemento esperado.
	 * @date 22/Diciembre/2025
	 */
	public void validarTuConexionSeHaRestablecido() throws Exception {
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOfElementLocated(textTuConexionHaSidoRestablecida));
			testCase.addScreenShotCurrentStep(au, "");
			testCase.addTextEvidenceCurrentStep("Se muestra la notificación verde 'Tu conexión ha sido restablecida'");
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
    		e.printStackTrace();
	        throw new Exception("No se mostró la notificacion 'Tu conexión ha sido restablecida'", e);
		}
	};
	/**
	 * Valida que se muestre el mensaje "Estas trabajando en modo sin conexión" en el dashboard principal.
	 * 
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception El metodo falla el test en caso de que no se encuentre el elemento esperado.
	 * @date 22/Diciembre/2025
	 */
	public void validarModalYBtnIrAIniciarSesion() throws Exception {
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOfElementLocated(btnIrAIniciarSesion));
			testCase.addScreenShotCurrentStep(au, "");
			testCase.addTextEvidenceCurrentStep("Se muestra modal 'La conexion a internet se ha restablecido' y el boton 'Ir a Iniciar Sesion'");
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
    		e.printStackTrace();
	        throw new Exception("No se mostró modal 'La conexion a internet se ha restablecido' ni el boton 'Ir a Iniciar Sesion'", e);
		}
	};
    
	/**
	 * Realiza clic al botón actualizar de la pantalla No hay conexión a internet
	 * 
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 22/Diciembre/2025
	 */
	public void clickCollapsibleSinConexion() throws Exception {
	    try {
	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
	        wait.until(ExpectedConditions.elementToBeClickable(collapsibleBannerContainer)).click();;

	        testCase.addScreenShotCurrentStep(au, "Pantalla Inicial");
	        testCase.addTextEvidenceCurrentStep("Se realiza clic al banner Estas Trabajando Sin Conexión");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error al dar clic en Actualizar (Offline)", e);
	    }
	}
	
	/**
	 * Realiza clic al botón actualizar de la pantalla No hay conexión a internet
	 * 
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 22/Diciembre/2025
	 */
	public void clickCollapsibleSinConexionSinEv() throws Exception {
	    try {
	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
	        wait.until(ExpectedConditions.elementToBeClickable(collapsibleBannerContainer)).click();;
	    } catch (Exception e) {
	    	e.printStackTrace();
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error al dar clic en Actualizar (Offline)", e);
	    }
	}
	
    /***
     * Metodo para hacer click en la opcion 'Evaluacion MEP' dentro del apartado PromOxxo
     * NOTA: Se reutiliza logica del metodo de MVP1 adaptada al checklist actual, para no afectar métodos heredados.
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 23/Diciembre/2025
     */
    public void clickOpcionEvaluacionMep() throws Exception {
    	 try {
 	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
 	        au.scrollToElement(opcionEvaluacionMep);
 	        wait.until(ExpectedConditions.elementToBeClickable(opcionEvaluacionMep)).click();

 	        testCase.addScreenShotCurrentStep(au, "Pantalla Inicial");
 	        testCase.addTextEvidenceCurrentStep("Se realiza clic a opción Evaluación MEP");
 	    } catch (Exception e) {
 	    	e.printStackTrace();
 	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
 	        throw new Exception("Error al dar clic en Actualizar (Offline)", e);
 	    }
    }
    /***
     * Metodo para hacer click en la opcion 'Resultado Evaluacion MEP' dentro del apartado PromOxxo
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 23/Diciembre/2025
     */
    public void clickOpcionResultadoEvaluacionMep() throws Exception {
    	 try {
 	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
 	        au.scrollToElement(opcionResultadoEvaluacionMep);
 	        wait.until(ExpectedConditions.elementToBeClickable(opcionResultadoEvaluacionMep)).click();
 	        
 	        testCase.addTextEvidenceCurrentStep("Se realiza clic a opción Resultado Evaluación MEP");
 	        testCase.addScreenShotCurrentStep(au, "Pantalla Inicial");
 	    } catch (Exception e) {
 	    	e.printStackTrace();
 	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
 	        throw new Exception("Error al dar clic en Actualizar (Offline)", e);
 	    }
    }
    /***
     * Metodo para hacer click en la opcion 'Evaluacion MEP' dentro del apartado PromOxxo
     * NOTA: Se reutiliza logica del metodo de MVP1 adaptada al checklist actual, para no afectar métodos heredados.
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 23/Diciembre/2025
     */
    public void clickOpcionEvaluacionMepSinEv() throws Exception {
    	 try {
 	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
 	        au.scrollToElement(opcionEvaluacionMep);
 	        wait.until(ExpectedConditions.elementToBeClickable(opcionEvaluacionMep)).click();

 	    } catch (Exception e) {
 	    	e.printStackTrace();
 	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
 	        throw new Exception("Error al dar clic en Actualizar (Offline)", e);
 	    }
    }
    
    /***
     * Metodo para hacer click en la opcion 'Evaluacion MEP' dentro del apartado PromOxxo
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 23/Diciembre/2025
     */
    public void clickIrAIniciarSesion() throws Exception {
    	 try {
 	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
 	        wait.until(ExpectedConditions.elementToBeClickable(btnIrAIniciarSesion)).click();
 	        wait.until(ExpectedConditions.visibilityOfElementLocated(btnSiguienteNuevoUsuario));
 	        testCase.addScreenShotCurrentStep(au, "");
 	        testCase.addTextEvidenceCurrentStep("Se realiza clic a Ir a Iniciar Sesion y se muestra la pantalla \"Iniciar sesión\"");
 	    } catch (Exception e) {
 	    	e.printStackTrace();
 	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
 	        throw new Exception("Error al dar clic en Ir a Iniciar Sesion", e);
 	    }
    }
    
    /***
     * Metodo para hacer click en la opcion 'Evaluacion MEP' dentro del apartado PromOxxo
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 23/Diciembre/2025
     */
    public void cerrarSesionSinEv() throws Exception {
    	 try {
 	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
 	        wait.until(ExpectedConditions.elementToBeClickable(iconUsuario)).click();
 	        wait.until(ExpectedConditions.elementToBeClickable(btnCerrarSesion)).click();
 	       wait.until(ExpectedConditions.elementToBeClickable(btnModalCerrarSesion)).click();
 	    } catch (Exception e) {
 	    	e.printStackTrace();
 	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
 	        throw new Exception("Error al dar clic en Actualizar (Offline)", e);
 	    }
    }
	
}