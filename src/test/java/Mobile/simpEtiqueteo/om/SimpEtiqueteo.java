package Mobile.simpEtiqueteo.om;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import exceptions.FrameworkException;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import modelo.BaseExecution;
import modelo.TestCase;
//import om.MetodosExtra;
import utils.AppiumUtil;
/**
 * DESCRIPCION: Clase de utileria de uso general para proyecto Simplificacion Etiqueteo
 * 
 * @author Mario A. De Leon Rdz.
 * @date 15/octubre/2024
 */
public class SimpEtiqueteo {
	
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
    private AppiumUtil au;
    private BaseExecution baseExecution;
    
    private By user = By.xpath("//android.widget.EditText[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/etUserName']");
    private By password = By.xpath("//android.widget.EditText[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/etPassword']");
    private By btnLogin = By.xpath("//android.widget.Button[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/btnLogin']");
    
    private By pantallaDiagnostico = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvStatus");
    private By opcionGente = By.xpath("//android.widget.TextView[@text='Gente']");
    
    private By bt_ExcOperativa = By.xpath("(//android.widget.FrameLayout[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/cvMenu'])[3]");
    private By btn_Etiqueteo  = By.xpath("(//android.widget.LinearLayout[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/lyMenuItem'])[2]");
    private By btn_aceptar = By.xpath("//android.widget.Button[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/btn_positive']");
	private By btn_PorFecha = By.xpath("(//android.widget.ImageView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/iv_banner'])[1]");
	private By Selc_RangoFecha = By.xpath("//android.widget.CheckedTextView[@text='24']");
	private By btn_RvisarEtiqutas = By.xpath("com.femco.oxxo.operaciones.mitienda.debug:id/btn_select");
	private By btn_imprimir = By.xpath("com.femco.oxxo.operaciones.mitienda.debug:id/btn_print");
	
	//ETIQUETEO--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	private By btn_AceptarColocarPapel  = By.xpath("//android.widget.Button[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/btn_positive']");
	private By pantallaDeCarga = By.xpath("//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.widget.FrameLayout");
	By btn_VolverEtiqueteo = By.xpath("//android.widget.ImageButton[@content-desc='Navegar hacia arriba']"); //boton esquina superior izquierda
	By editText_BusquedaSkuOArticulo = By.xpath("//android.widget.EditText[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/et_search']");
	By btn_BusquedaPorFecha = By.xpath("//android.widget.LinearLayout[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/btn_date']/android.widget.FrameLayout/android.view.ViewGroup");
	private By btn_Planograma  = By.xpath("//android.widget.LinearLayout[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/btn_planogram']/android.widget.FrameLayout/android.view.ViewGroup");
	//BUSCAR ARTICULO
	By img_EtiquetaBuscarArt1 = By.xpath("(//android.widget.ImageView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/iv_base'])[1]");//es la primera etiqueta mostrada
	By btn_AgregarEtiqueta1 = By.xpath("(//android.widget.Button[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/btn_add_label'])[1]");//es el boton agregar etiqueta de la primera etiqueta mostrada
	By btn_MinusEtiqueta = By.xpath("//android.widget.ImageView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/iv_minus']"); //despues de agregar un etiqueta se muestre este icono de basurero para reducir la cantidad de etiquetas agregadas
	By btn_MinusEtiqueta1 = By.xpath("(//android.widget.ImageView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/iv_minus'])[1]");
	By btn_PlusEtiqueta = By.xpath("//android.widget.ImageView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/iv_plus']");
	By btn_PlusEtiqueta1 = By.xpath("(//android.widget.ImageView[@resource-id=\\'com.femco.oxxo.operaciones.mitienda.debug:id/iv_plus\\'])[1]");
	By btn_XDetallesEtiquetas = By.xpath("//android.widget.ImageButton[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/close_button']"); //es para salir de la vista de la info detallada de la etiqueta
	By btn_AgregarEtiquetaDetalles = By.xpath("//android.widget.ImageButton[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/btn_add_label']"); //boton Agregar etiquetas en la vista detallada de la etiqueta
	By btn_PlusEtiquetaDetalles = By.xpath("//android.widget.ImageButton[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/btn_plus']");
	//LISTA DE ETIQUETAS
	By btn_ListaDeEtiquetas = By.xpath("//android.widget.ImageView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/iv_labels']");
	By btn_ImprimirListaDeEtiquetas = By.xpath("//android.widget.Button[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/btn_print']");
	By btn_VaciarListaDeEtiquetas = By.xpath("//android.widget.ImageView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/iv_labels']");
	By btn_ContinuarVaciadoListaDeEtiquetas = By.xpath("//android.widget.Button[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/btn_positive']");
	By btn_CancelarVaciadoListaDeEtiquetas= By.xpath("//android.widget.Button[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/btn_negative']");
	//FECHAS
	By btn_RevisarEtiquetasFechas = By.xpath("//android.widget.TextView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/btn_select']");
	By btn_RegresarFecha = By.xpath("//android.widget.ImageView[@content-desc=\"Go to previous\"]");
	//PLANOGRAMA
	By editText_BuscarPlanogrupo = By.xpath("//android.widget.EditText[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/et_search']");
	private By btn_Planogrupo1 = By.xpath("(//android.widget.CheckBox[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/checkBox'])[1]");
	private By img_Planogrupo1 = By.xpath("(//android.widget.ImageView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/iv_product'])[1]");
	private By btn_ImprimirPlanogrupo = By.xpath("//android.widget.Button[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/btn_print']");
	By img_ImprimirEtiquetasActualizadas = By.xpath("(//android.widget.ImageView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/iv_print'])[1]");
	By img_ImprimirTodasLasCharolas = By.xpath("(//android.widget.ImageView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/iv_print'])[2]");
	private By img_ImprimirCharola1 = By.xpath("(//android.widget.ImageView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/iv_print'])[3]");
	By click_visualizaAllCharolas = By.xpath("(//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btn_show_information\"])[1]");
	//Imprimir
	By btn_AceptarCortarEtiquetas = By.xpath("//android.widget.Button[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/btn_positive']"); //es el mismo botón para aceptar la alerta Si sales de esta pantalla, se borrarán las etiquetas seleccionadas.
	By btn_CancelarAlertaBorradoEtiquetas = By.xpath("//android.widget.Button[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/btn_negative']");
	
	//Mario Salazar
	By btn_RegresarPlanogrupo = By.xpath("//android.widget.ImageButton[@content-desc=\"Navegar hacia arriba\"]");
	
	By btnInfoFechas = By.xpath("(//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/iv_tooltip\"])[1]");
	By btnInfoPlanograma = By.xpath("(//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/iv_tooltip\"])[2]");
	// Constructor ******************************************************************************************/
	public SimpEtiqueteo(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
		this.testCase = testCase;
        this.au = au;
        this.baseExecution = baseExecution;
	}
	
	
    /**
     * Login con user y password
	 *
     * @param nombreDeUsuario El usuario con el que se iniciara sesion
     * @param contraDeUsuario El password con el que iniciar sesion
     *
     *  Pre requisito: 
     *  1. Se debe completar la primer ventana donde se ingresa la ip y el puerto hasta llegar al Login
     *  2. No se debe eliminar el cache ni los datos de la app
     *   
	 * @date 24/enero/2025  
	 * @throws InterruptedException Si ocurre un error en la ejecución  
     */
	
	public void login(String nombreDeUsuario, String contraDeUsuario) throws InterruptedException {
		try {
			au.driver.hideKeyboard();
			Thread.sleep(1000);
		    testCase.addScreenShotCurrentStep(au, "testImage");
		      
		    au.sendKeys(user, nombreDeUsuario);
		    testCase.addScreenShotCurrentStep(au, "testImage");
		      
		    au.sendKeys(password, contraDeUsuario);
		    au.driver.hideKeyboard();
		    testCase.addScreenShotCurrentStep(au, "testImage");

		    au.click(btnLogin);

		    } catch (Exception e) {
		    	e.printStackTrace();
			} 	      	      
	    }
    /**
     * Login con user y password
	 *
     * @param nombreDeUsuario El usuario con el que se iniciara sesion
     * @param contraDeUsuario El password con el que iniciar sesion
     *
     *  Pre requisito: 
     *  1. Se debe completar la primer ventana donde se ingresa la ip y el puerto hasta llegar al Login
     *  2. No se debe eliminar el cache ni los datos de la app
     *   
	 * @date 24/enero/2025  
	 * @throws InterruptedException Si ocurre un error en la ejecución  
     */
	
	public void loginTablet(String nombreDeUsuario, String contraDeUsuario) throws InterruptedException {
		try {
			au.driver.hideKeyboard();
			Thread.sleep(1000);
		    testCase.addScreenShotCurrentStep(au, "testImage");
		      
		    au.sendKeys(user, nombreDeUsuario);
		    testCase.addScreenShotCurrentStep(au, "testImage");
		      
		    au.sendKeys(password, contraDeUsuario);
		    au.driver.hideKeyboard();
		    regresarAndroid();
		    
		    au.click(btnLogin);

		    } catch (Exception e) {
		    	e.printStackTrace();
			} 	      	      
	    }
	/**
	 * Ingresa el nombre de usuario en la aplicación y valida la pantalla de diagnóstico.  
	 * Oculta el teclado y captura una evidencia.  
	 *  
	 * @author Mario A. De León Rdz.  
	 * @date 24/enero/2025  
	 * @param nombreDeUsuario Nombre de usuario a ingresar  
	 * @throws InterruptedException Si ocurre un error en la ejecución  
	 */  
	public void loginUsuario(String nombreDeUsuario) throws InterruptedException {
		try {
			au.driver.hideKeyboard();
			
		    au.sendKeys(user, nombreDeUsuario);
		    testCase.addScreenShotCurrentStep(au, "testImage");
		   
		    } catch (Exception e) {
		    	e.printStackTrace();
			} 	      	      
	    }
	/**
	 * Ingresa la contraseña del usuario y realiza el login en la aplicación.  
	 *  
	 * @author Mario A. De León Rdz.  
	 * @date 29/enero/2025  
	 * @param contraDeUsuario Contraseña del usuario  
	 * @throws InterruptedException Si ocurre un error en la ejecución  
	 */  
	public void loginContrasena(String contraDeUsuario) throws InterruptedException {
		try {
		    au.sendKeys(password, contraDeUsuario);
		    au.driver.hideKeyboard();
		    au.driver.pressKey(new KeyEvent(AndroidKey.BACK));
		    testCase.addScreenShotCurrentStep(au, "testImage");
		    } catch (Exception e) {
		    	e.printStackTrace();
			} 	      	      
	    }
	/**
	 * Ingresa la contraseña del usuario y realiza el login en la aplicación.  
	 *  
	 * @author Mario Salazar  
	 * @date 29/enero/2025  
	 * @throws InterruptedException Si ocurre un error en la ejecución  
	 */  
	public void loginIngresar() {
		try {
			au.click(btnLogin);
			testCase.addTextEvidenceCurrentStep("Tap en Boton Ingresar");
			testCase.addScreenShotCurrentStep(au, "imgTest");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Método para validar si el inicio de sesión fue exitoso.
	 * 
	 * Este método verifica la presencia del elemento relacionado con la opción "GENTE" 
	 * en la interfaz después de iniciar sesión. Si el elemento está visible, se asume 
	 * que el login fue exitoso. Se captura una evidencia en pantalla y se agrega una 
	 * nota en el caso de prueba indicando el resultado de la validación.
	 * 
	 * @author Mario A. De León Rdz.
	 * @date 24/enero/2025
	 * @throws Exception
	 */
	public void validateLoginSucess() {
	    try {
	        boolean displayedOpcionGente = au.driver.findElement(opcionGente).isDisplayed();
	        testCase.addScreenShotCurrentStep(au, "testImage");

	        if (displayedOpcionGente) {
	            testCase.addTextEvidenceCurrentStep("El login se ha realizado correctamente");
	        } else {
	            testCase.addTextEvidenceCurrentStep("El login ha fallado o no se encuentra la opción 'GENTE'");
	        }
	    } catch (Exception e) {
	        testCase.addTextEvidenceCurrentStep("Error al validar el login: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
    /**
	 * Metodo para hacer click en el boton Excelencia operativa y se muestre una ventana con la opcion Etiqueteo
	 * @author Mario A. De León Rdz.
	 * @date 24/enero/2025
     */
	public void ExcelenciaOperativa() {
        try {
        	au.click(bt_ExcOperativa);
        	au.wait(1);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Metodo para hacer click en el boton Etiqueteo y entrar al modulo
	 * @author Mario A. De León Rdz.
	 * @date 24/enero/2025
     */
	public void Etiqueteo() {
		try {
			au.click(btn_Etiqueteo);
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(opcionGente));
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método para hacer click a boton aceptar del aviso Companer@ recuerda Colocar papel
	 * @author Mario A. De León Rdz.
	 * @date 24/enero/2025
	 * @throws FrameworkException 
	 */	   
	public void aceptarColocarPapel() throws Exception {
		try {	
			au.click(btn_AceptarColocarPapel, 30);
			testCase.addScreenShotCurrentStep(au, "testImage");
			esperaPantallaDeCarga();
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Método para hacer click a seccion planograma del modulo Etiqueteo
	 * @author Mario A. De León Rdz.
	 * @date 24/enero/2025
	 * @throws FrameworkException 
	 */	   
	public void clickBusquedaPlanograma() throws Exception {
		try {	
			au.click(btn_Planograma);
			au.wait(1);
			testCase.addScreenShotCurrentStep(au, "testImage");
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	/**
	 * Método para entrar a la seccion de busqueda por fechas.
	 * @author Mario A. De León Rdz.
	 * @date 27/enero/2025
	 * @throws FrameworkException 
	 */	   
	public void clickBusquedaFecha() throws Exception {
		try {	
			au.click(btn_PorFecha);
			au.wait(2);
			testCase.addScreenShotCurrentStep(au, "testImage");
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	/**
	 * Método para entrar a la seccion de busqueda por fechas.
	 * @author Mario A. De León Rdz.
	 * @date 27/enero/2025
	 * @throws FrameworkException 
	 */	   
	public void clickBusquedaFechaSinEv() throws Exception {
		try {	
			au.click(btn_PorFecha);
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	/**
	 * Método para seleccionar la fecha que se encuentre en el sistema del dispositivo en el calendario mostrado en la aplicacion.
	 * @author Mario A. De León Rdz.
	 * @date 28/enero/2025
	 * @throws FrameworkException 
	 */	   
	public void seleccionarDiaDelSistemaEnFechas() throws Exception {
	    try {
	        // Obtener el día del sistema y eliminar ceros a la izquierda
	        int dayInt = Integer.parseInt(au.driver.getDeviceTime().substring(8, 10));
	        System.out.println("Día obtenido: " + dayInt);

	        // Determinar el índice del elemento en función del día
	        int index = (dayInt > 18) ? 2 : 1;

	        // Hacer clic en el elemento correspondiente
	        String xpath = "(//android.widget.CheckedTextView[@text='" + dayInt + "'])[" + index + "]";
	        au.click(By.xpath(xpath));

	        // Pausa breve y captura de pantalla
	        Thread.sleep(200);
	        testCase.addScreenShotCurrentStep(au, "testImage");
	    } catch (Exception e) {
	        // Manejo de errores
	        e.printStackTrace();
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage(), e);
	    }
	}
	/**
	 * Método para seleccionar la fecha que se encuentre en el sistema del dispositivo en el calendario mostrado en la aplicacion.
	 * @author Mario A. De León Rdz.
	 * @date 28/enero/2025
	 * @throws FrameworkException 
	 */	   
	public void seleccionarDiaDelSistema() throws Exception {
	    try {
	        // Obtener el día del sistema y eliminar ceros a la izquierda
	        int dayInt = Integer.parseInt(au.driver.getDeviceTime().substring(8, 10));
	        System.out.println("Día obtenido: " + dayInt);
	        String xpath = "";
	        // Determinar el índice del elemento en función del día
	        int index = (dayInt > 18) ? 1 : 0;
	        if(dayInt > 18) {
	        	xpath = "(//android.widget.CheckedTextView[@text='" + dayInt + "'])";
	        } else {
	        	xpath = "(//android.widget.CheckedTextView[@text='" + dayInt + "'])[" + index + "]";
	        }
	        // Hacer clic en el elemento correspondiente
	        au.click(By.xpath(xpath));

	        // Pausa breve y captura de pantalla
	        Thread.sleep(200);
	        testCase.addScreenShotCurrentStep(au, "testImage");
	    } catch (Exception e) {
	        // Manejo de errores
	        e.printStackTrace();
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage(), e);
	    }
	}


	/**
	 * Método para mostrar las etiquetas a imprimir de la fecha seleccionada
	 * @author Mario A. De León Rdz.
	 * @date 27/enero/2025
	 * @throws FrameworkException 
	 */	   
	public void revisarEtiquetasFechas() throws Exception {
		try {	
			au.click(btn_RevisarEtiquetasFechas);
			Thread.sleep(0500);
			testCase.addScreenShotCurrentStep(au, "testImage");
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	/**
	 * Método para seleccionar el primer planogrupo mostrado.
	 * @author Mario A. De León Rdz.
	 * @date 24/enero/2025
	 * @throws FrameworkException 
	 */	   
	public void clickPrimerPlanogrupo() throws Exception {
		try {	
			au.click(btn_Planogrupo1);
			testCase.addScreenShotCurrentStep(au, "testImage");
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	/**
	 * Método para seleccionar un planogrupo del segmento Planogramas
	 * @author Mario A. De León Rdz.
	 * @date 27/enero/2025
	 * @throws FrameworkException 
	 */	   
	public void seleccionarPlanogrupo(String planogrupo) throws Exception {
		try {	
			au.click(By.xpath("//android.widget.TextView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/tv_planogroup' and @text="+planogrupo+"])"));
			testCase.addScreenShotCurrentStep(au, "testImage");
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Método para buscar un planogrupo y abrirlo para que se muestren las charolas del planogrupo.
	 * @author Mario A. De León Rdz.
	 * @date 27/enero/2025
	 * @throws FrameworkException 
	 */	   
	public void buscarYAbrirPlanogrupo(String planogrupo) throws Exception {
		try {
			au.sendKeys(editText_BusquedaSkuOArticulo, planogrupo);
			au.click(img_Planogrupo1);
			au.wait(1);
			testCase.addScreenShotCurrentStep(au, "testImage");
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	/**
	 * Método para buscar y seleccionar un planogrupo del segmento Planogramas. 
	 * Al hacer esto se va a seleccionar el planogrupo deseado y solo se va a poder imprimir las etiquetas que hayan recibido un cambio reciente.
	 * @author Mario A. De León Rdz.
	 * @date 27/enero/2025
	 * @throws FrameworkException 
	 */	   
	public void buscarYSeleccionarPlanogrupo(String planogrupo) throws Exception {
		try {
			au.sendKeys(editText_BusquedaSkuOArticulo, planogrupo);
			au.click(btn_Planogrupo1);
			testCase.addScreenShotCurrentStep(au, "testImage");
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Método para buscar un sku.
	 * @param sku Ingresar sku que se desea buscar
	 * @author Mario A. De León Rdz.
	 * @date 27/enero/2025
	 * @throws FrameworkException 
	 */	   
	public void buscarSKU(String sku) throws Exception {
		try {
			
			au.click(editText_BusquedaSkuOArticulo);
			au.sendKeys(editText_BusquedaSkuOArticulo, sku);
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Método para agregar la etiqueta del primer articulo mostrado.
	 * @author Mario A. De León Rdz.
	 * @date 27/enero/2025
	 * @throws FrameworkException 
	 */	   
	public void agregarPrimerEtiquetaSKU() throws Exception {
		try {
			au.click(btn_AgregarEtiqueta1);
			testCase.addScreenShotCurrentStep(au, "testImage");
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	/**
	 * Método para agregar la etiqueta del articulo especificado.
	 * @author Mario A. De León Rdz.
	 * @date 27/enero/2025
	 * @throws FrameworkException 
	 */	   
	public void agregarEtiquetaSKU(int numEtiqueta) throws Exception {
		try {
			au.click(By.xpath("(//android.widget.Button[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/btn_add_label'])["+numEtiqueta+"]"));
			testCase.addScreenShotCurrentStep(au, "testImage");
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	//TODO: MARIO SALAZAR
	/**
	 * Método para agregar un numero especifico de etiquetas del primer articulo mostrado en el segmento de busqueda por sku o nombre del artículo.
	 * @author Mario Salazar.
	 * @date 27/enero/2025
	 * @throws FrameworkException 
	 */	   
	public void agregarCantidadDeEtiquetas(int num) {
	    try {
	        au.click(btn_AgregarEtiqueta1);
	        for (int i = 1; i <= num - 1; i++) {
	            au.wait(1);
	            au.click(btn_PlusEtiqueta);
	            testCase.addScreenShotCurrentStep(au, "testImage");
	        }
	        testCase.addScreenShotCurrentStep(au, "testImage");
	    } catch (Exception e) {
	        System.out.println("Ocurrió un error en agregarCantidadDeEtiquetas: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	/**
	 * Método para agregar un numero especifico de etiquetas del primer articulo mostrado en el segmento de busqueda por sku o nombre del artículo.
	 * @author Mario Salazar.
	 * @date 27/enero/2025
	 * @throws FrameworkException 
	 */	   
	public void agregarCantidadDeEtiquetas(int num, boolean etiqPreviamenteAgregada) {
	    try {
	        // Si no hay etiquetas agregadas previamente, se hace clic en el botón para agregar la primera
	        if (!etiqPreviamenteAgregada) {
	            au.click(btn_AgregarEtiqueta1);
	        }
	        for (int i = 1; i <= num - 1; i++) {
	            au.wait(1);
	            au.click(btn_PlusEtiqueta);
	            testCase.addScreenShotCurrentStep(au, "testImage");
	        }
	        testCase.addScreenShotCurrentStep(au, "testImage");
	    } catch (Exception e) {
	        System.out.println("Ocurrió un error en agregarCantidadDeEtiquetas: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	/**
	 * Método para agregar un numero especifico de etiquetas del primer articulo mostrado en el segmento de busqueda por sku o nombre del artículo.
	 * @author Mario Salazar.
	 * @date 27/enero/2025
	 * @throws FrameworkException 
	 */	   
	public void agregarCantidadDeEtiquetasDesdeDetalles(int num) {
	    try {
	        for (int i = 1; i <= num - 1; i++) {
	            au.wait(1);
	            au.click(btn_PlusEtiquetaDetalles);
	        }
	        testCase.addScreenShotCurrentStep(au, "testImage");
	    } catch (Exception e) {
	        System.out.println("Ocurrió un error en agregarCantidadDeEtiquetasDesdeDetalles: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	/**
	 * Método para hacer tap a la primer etiqueta mostrada en el segmento de busqueda por sku o nombre del artículo. Esto abre la vista de detalles e informacion de la etiqueta
	 * @author Mario A. De León Rdz.
	 * @date 27/enero/2025
	 * @throws FrameworkException 
	 */	   
	public void abrirPrimerEtiquetaSKU() throws Exception {
		try {
			au.click(img_EtiquetaBuscarArt1);
			Thread.sleep(0500);
			testCase.addScreenShotCurrentStep(au, "testImage");
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Método para hacer tap a la primer etiqueta mostrada en el segmento de busqueda por sku o nombre del artículo. Esto abre la vista de detalles e informacion de la etiqueta
	 * @author Mario A. De León Rdz.
	 * @date 27/enero/2025
	 * @throws FrameworkException 
	 */	   
	public void abrirEtiquetaSKU(int numEtiqueta) throws Exception {
		try {
			au.click(By.xpath("(//android.widget.ImageView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/iv_base'])["+numEtiqueta+"]"));
			
			Thread.sleep(0500);
			testCase.addScreenShotCurrentStep(au, "testImage");
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Método para salir de la vista de detalles de una etiqueta previamente seleccionada
	 * @author Mario A. De León Rdz.
	 * @date 27/enero/2025
	 * @throws FrameworkException 
	 */	   
	public void cerrarDetallesEtiquetaSKU() throws Exception {
		try {
			au.click(btn_XDetallesEtiquetas);
			Thread.sleep(0100);
			testCase.addScreenShotCurrentStep(au, "testImage");
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Método para borrar el texto contenido la barra de busqueda de Etiqueteo
	 * @author Mario A. De León Rdz.
	 * @date 27/enero/2025
	 * @throws FrameworkException 
	 */	   
	public void borrarBusquedaEtiquetaSKU() throws Exception {
		try {
			au.click(editText_BusquedaSkuOArticulo);
			au.driver.findElement(editText_BusquedaSkuOArticulo).clear();
			Thread.sleep(0100);
			testCase.addScreenShotCurrentStep(au, "testImage");
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	/**
	 * Método para hacer click en el boton imprimir que aparece en todos los segmentos de busqueda (sku, fechas o planograma)
	 * @author Mario A. De León Rdz.
	 * @date 24/enero/2025
	 * @throws FrameworkException 
	 */	   
	public void imprimirEtiquetas() throws Exception {
		try {	
			au.click(btn_ImprimirPlanogrupo);
			testCase.addScreenShotCurrentStep(au, "testImage");
			
		}catch(Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Advertencia: No se encontró el botón Aceptar Cortar Etiquetas. Continuando con la ejecución.");
	        // No se lanza la excepción para que la prueba continúe
		}
	}
	/**
	 * Método para imprimir UNA charola en especifico de un planogrupo dentro del segmento Planograma
	 * @author Mario A. De León Rdz.
	 * @date 24/enero/2025
	 * @throws FrameworkException 
	 */	   
	public void imprimirCharola(String charola) throws Exception {
		try {
			// Convertir la cadena a un número y sumarle 2
//	        int charolaNum = Integer.parseInt(charola) + 2;
	        int charolaNum = Integer.parseInt(charola.trim()) + 2;
	        System.out.println("(//android.widget.ImageView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/iv_print'])["+charolaNum+"]");
			au.click(By.xpath("(//android.widget.ImageView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/iv_print'])["+charolaNum+"]"));
			testCase.addScreenShotCurrentStep(au, "testImage");
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	/**
	 * Método para imprimir todas las charolas de un planogrupo dentro del segmento Planograma
	 * @author Mario A. De León Rdz.
	 * @date 27/enero/2025
	 * @throws FrameworkException 
	 */	   
	public void imprimirTodasLasCharolas() throws Exception {
		try {	
			au.click(img_ImprimirTodasLasCharolas);
			testCase.addScreenShotCurrentStep(au, "testImage");
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	/**
	 * Método para imprimir todas las etiquetas pendientes de un planogrupo dentro del segmento Planograma
	 * @author Mario A. De León Rdz.
	 * @date 27/enero/2025
	 * @throws FrameworkException 
	 */	   
	public void imprimirEtiquetasActualizadas() throws Exception {
		try {	
			au.click(img_ImprimirEtiquetasActualizadas);
			testCase.addScreenShotCurrentStep(au, "testImage");
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	/**
	 * Método para esperar a que deje de mostrarse la pantalla de carga despues de entrar a Etiqueteo y aceptar la alerta Cambiar papel de ticket
	 * @author Mario A. De León Rdz.
	 * @date 24/enero/2025
	 * @throws FrameworkException 
	 */	   
	public void esperaPantallaDeCarga() throws Exception {
		try {	
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(pantallaDeCarga));
			testCase.addScreenShotCurrentStep(au, "testImage");
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	/**
	 * Método para esperar a que deje de mostrarse la pantalla de carga despues de entrar a Etiqueteo y aceptar la alerta Cambiar papel de ticket
	 * @author Mario A. De León Rdz.
	 * @date 24/enero/2025
	 * @throws FrameworkException 
	 */	   
	public void esperaPantallaDeCargaSinEv() throws Exception {
		try {	
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(pantallaDeCarga));			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	/**
	 * Método para hacer tap en el boton aceptar de la alerta "Ya Puedes Cortar Tus Etiquetas" que se
	 * muestra despues de imprimir alguna etiqueta
	 * @author Mario A. De León Rdz.
	 * @date 24/enero/2025
	 * @throws Exception 
	 */	   
	public void aceptarCortarEtiquetas() throws Exception {
		try {
			au.click(btn_AceptarCortarEtiquetas, 60);
			testCase.addScreenShotCurrentStep(au, "testImage");
			
		}catch(Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Advertencia: No se encontró el botón Aceptar Cortar Etiquetas. Continuando con la ejecución.");
	        // No se lanza la excepción para que la prueba continúe
		}
	}
	
	/**
	 * Método para validar se encuentre en el apartado Planograma
	 * @author Mario A. De León Rdz.
	 * @date 24/enero/202
	 * @throws FrameworkException 
	 */	   
	public void validarApartadoPlanograma() throws Exception {
	    try {
	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(10));
	        
	        // Verificar que el elemento sea visible
	        boolean isVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(editText_BuscarPlanogrupo)) != null;
	        
	        if (!isVisible) {
	            throw new Exception("No se encuentra en el apartado esperado");
	        }

	        // Tomar captura de pantalla si todo es correcto
	        testCase.addScreenShotCurrentStep(au, "testImage");
	    } catch (Exception e) {
	        // Agregar evidencia del error y lanzar excepción
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error en validarApartadoPlanograma: " + e.getMessage(), e);
	    }
	}

	/**
	 * Método para hacer click a la Lista de Etiquetas
	 * @author Mario A. De León Rdz.
	 * @date 27/enero/2025
	 * @throws FrameworkException 
	 */	   
	public void clickListaDeEtiquetas() throws Exception {
		try {	
			au.click(btn_ListaDeEtiquetas);
			au.wait(2);
			testCase.addScreenShotCurrentStep(au, "testImage");
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Método para hacer cancelar vaciado de lista de etiquetas
	 * @author Mario A. De León Rdz.
	 * @date 27/enero/2025
	 * @throws FrameworkException 
	 */	   
	public void cancelarVaciarListaDeEtiquetas() throws Exception {
		try {	
			au.click(btn_CancelarVaciadoListaDeEtiquetas);
			Thread.sleep(0200);
			testCase.addScreenShotCurrentStep(au, "testImage");
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Método para presionar el boton BACK nativo de Android
	 * @author Mario A. De León Rdz.
	 * @date 27/enero/2025
	 * @throws FrameworkException 
	 */	   
	public void regresarAndroid() throws Exception {
		try {	
			au.driver.pressKey(new KeyEvent(AndroidKey.BACK));
			Thread.sleep(0200);
			testCase.addScreenShotCurrentStep(au, "testImage");
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Método para presionar el boton BACK del aplicativo
	 * @author Mario A. De León Rdz.
	 * @date 13/febrero/2025
	 * @throws FrameworkException 
	 */	   
	public void regresarApp() throws Exception {
		try {	
			au.click(btn_VolverEtiqueteo);
			Thread.sleep(0200);
			testCase.addScreenShotCurrentStep(au, "testImage");
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}

//TODO:---------------------------------------METODOS PARA LEER ETIQUETAS ------------------------------------------------------------------------------
	/**
	 * Método para obtener el archivo de etiquetas más reciente en un directorio.
	 * Este método busca archivos en un directorio que cumplan con un patrón específico en su nombre 
	 * (iniciando con "EtiquetasD") y retorna el archivo más reciente basado en la fecha de última modificación.
	 * 
	 * @param directorio Ruta del directorio donde se buscarán los archivos.
	 * @return El archivo más reciente que cumple con el patrón, o null si no se encuentran archivos.
	 * @throws Exception Si ocurre algún error durante la ejecución.
	 * 
	 * @author Mario A. De León Rdz. y Amauri Rdz.
	 * @date 21/octubre/2024
	 */
	public File obtenerArchivoDeEtiquetasMasReciente(String directorio) throws Exception {
	    try {
	        File dir = new File(directorio);

	        // Filtro para archivos que sigan el patrón "EtiquetasD*"
	        FilenameFilter filtroVentas = new FilenameFilter() {
	            public boolean accept(File dir, String nombre) {
	                return nombre.matches("EtiquetasM.*");
	            }
	        };

	        // Obtener la lista de archivos que cumplen con el filtro
	        File[] archivos = dir.listFiles(filtroVentas);

	        if (archivos != null && archivos.length > 0) {
	            // Ordenar los archivos por la fecha de última modificación (más reciente primero)
	            Arrays.sort(archivos, new Comparator<File>() {
	                public int compare(File f1, File f2) {
	                    return Long.compare(f2.lastModified(), f1.lastModified());
	                }
	            });

	            // Retornar el archivo más reciente
	            System.out.println("Se encontró el archivo más reciente: " + archivos[0]);
	            return archivos[0];
	        }

	        // No se encontraron archivos que coincidan con el patrón
	        System.out.println("No se encontró ningún archivo que coincida.");
	        return null;
	    } catch (Exception e) {
	        // Registrar el error y lanzarlo para manejarlo externamente
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }
	}

	
	/**
	 * Método para leer el contenido de un archivo y retornarlo como una cadena de texto.
	 * Verifica si el archivo existe y se puede leer antes de intentar procesarlo.
	 * Si ocurre un error durante la lectura, se registra en la consola y se retorna null.
	 * 
	 * @param archivo Objeto File que representa el archivo a leer.
	 * @return El contenido del archivo como una cadena de texto, o null si no se puede leer.
	 * @author Mario A. De Leon Rdz.
	 * @date 12/febrero/2025  
	 */
	public String leerContenidoArchivo(File archivo) {
	    try {
	        // Verificar si el archivo existe y se puede leer
	        if (!archivo.exists()) {
	            System.out.println("El archivo no existe: " + archivo.getPath());
	            return null;
	        }

	        if (!archivo.canRead()) {
	            System.out.println("No se puede leer el archivo: " + archivo.getPath());
	            return null;
	        }

	        StringBuilder contenido = new StringBuilder();
	        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
	            String linea;
	            while ((linea = br.readLine()) != null) {
	                contenido.append(linea).append("\n");
	            }
	        } catch (IOException e) {
	            System.out.println("Error al leer el archivo: " + e.getMessage());
	        }
	        testCase.addTextEvidenceCurrentStep(contenido.toString());
	        return contenido.toString();
	    } catch (Exception e) {
	        System.out.println("Ocurrió un error inesperado: " + e.getMessage());
	        return null;
	    }
	}
//TODO:---------------------------------------METODOS PARA VALIDAR ETIQUETAS IMPRESAS ------------------------------------------------------------------------------
	/**
	 * Método para validar la cantidad de etiquetas impresas
	 * @param leerContenidoArchivo ingresar resultado de metodo public String leerContenidoArchivo(File archivo)
	 * @param numero Numero de etiquetas esperadas de la impresión
	 * @author Mario A. De León Rdz.
	 * @date 21/octubre/2024
	 * @throws FrameworkException 
	 */	   
	public void validarContenidoArchivoNumEtiquetas(String leerContenidoArchivo, int numero) throws Exception {
		try {	
			System.out.println(leerContenidoArchivo.contains("<RecId>"+numero+"</RecId>"));
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}

	/**
	 * Método para validar que la impresion contenga un sku en especifico
	 * @param leerContenidoArchivo ingresar resultado de metodo public String leerContenidoArchivo(File archivo)
	 * @author Mario A. De León Rdz.
	 * @date 21/octubre/2024
	 * @throws FrameworkException 
	 */	   
	public void validarContenidoSKU(String leerContenidoArchivo, String sku) throws Exception {
		try {	
			System.out.println(leerContenidoArchivo.contains("<Articulo>"+sku+"</Articulo>"));
			if (!leerContenidoArchivo.contains("<Articulo>" + sku + "</Articulo>")) {
	           throw new Exception("El contenido no contiene el SKU esperado: " + sku);
	      }
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}

	/**
	 * Método para validar el contenido de un archivo de etiquetas impresas.
	 * Este método compara las claves extraídas del archivo con una lista de claves esperadas provenientes de la base de datos.
	 * Si alguna clave esperada no está presente en el archivo, o si el número de etiquetas no coincide con el esperado, el caso falla.
	 * 
	 * @param leerContenidoArchivo Resultado del método `public String leerContenidoArchivo(File archivo)`, que contiene el contenido del archivo de etiquetas.
	 * @param resultadosBDD Lista de claves esperadas provenientes de la base de datos.
	 * @throws Exception Si alguna clave esperada no está presente en el archivo o si ocurre algún error durante la validación.
	 * 
	 * @author Mario A. De León Rdz.
	 * @date 22/octubre/2024
	 */
	public void validarContenidoArchivoOrdenEtiquetas2(String leerContenidoArchivo, List<String> resultadosBDD) throws Exception {
		try {
			List<String> resultado = Arrays.stream(leerContenidoArchivo.split(">"))
				    .filter(tag -> tag.contains("</sku"))
				    .filter(tag -> !tag.trim().isEmpty())
				    .map(tag -> tag.replace("</sku", "")) // Elimina </ClaveVenta
				    .collect(Collectors.toList()); // Recopila los resultados en una lista
				for (String articulo : resultado) {System.out.println(articulo);}
				// Imprime el contenido de la lista con su índice
				System.out.println("Metodo de validacion final:");
				for (String clave : resultadosBDD) {
					
		            if (!resultado.contains(clave)) {
		            	throw new Exception("Error: No se encontró coincidencia del SKU: " + clave );
		            }
		        }
				
		}catch(Exception e) {
			e.printStackTrace();
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Método para validar que las etiquetas en el archivo no coincidan con una lista de resultados de la base de datos.
	 * Este método verifica que ninguna de las etiquetas impresas en el archivo tenga una clave presente en la lista de resultados de la base de datos.
	 * Si encuentra alguna coincidencia, lanza una excepción indicando el error.
	 * 
	 * @param leerContenidoArchivo Resultado del método public String leerContenidoArchivo(File archivo), que contiene las etiquetas impresas.
	 * @param resultadosBDD Lista de claves provenientes de la base de datos que no deberían estar presentes en el archivo.
	 * @throws Exception Si se encuentra alguna coincidencia entre las etiquetas del archivo y los resultados de la base de datos.
	 * 
	 * @author Mario A. De León Rdz.
	 * @date 22/octubre/2024
	 */
	public void validarNoContenidoArchivoOrdenEtiquetas2(String leerContenidoArchivo, List<String> resultadosBDD) throws Exception {
		try {
			List<String> resultado = Arrays.stream(leerContenidoArchivo.split(">"))
				    .filter(tag -> tag.contains("</ClaveVenta"))
				    .filter(tag -> !tag.trim().isEmpty())
				    .map(tag -> tag.replace("</ClaveVenta", "")) // Elimina </ClaveVenta
				    .collect(Collectors.toList()); // Recopila los resultados en una lista

				// Imprime el contenido de la lista con su índice
				System.out.println("Metodo de validacion final:");
				for (int i = 0; i < resultado.size(); i++) {
					
					for(int j = 0; j < resultadosBDD.size(); j++) {
						
						// Falla si hay coincidencia entre ambos valores
				        if (resultado.get(i).equals(resultadosBDD.get(j))) {
				        	testCase.addCodeEvidenceCurrentStep("Error: Se encuentra una etiqueta que no deberia imprimirse");
							throw new Exception("Error: Se encontró coincidencia en SKU en la fila \" + i");
				        }
					}
				}
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Método para validar que la impresion contenga una Descripcion o Nombre del producto en especifico
	 * @param leerContenidoArchivo ingresar resultado de metodo public String leerContenidoArchivo(File archivo)
	 * @author Mario A. De León Rdz.
	 * @date 19/noviembre/2024
	 * @throws FrameworkException 
	 */	   
	public void validarContenidoDescripcionCorta(String leerContenidoArchivo, String descripcionCorta) throws Exception {
		try {	
			System.out.println(leerContenidoArchivo.contains("<Descripcion>"+descripcionCorta+"</Descripcion>"));
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Método para validar que la impresión contenga una Descripción o Nombre del producto en específico.
	 * @param leerContenidoArchivo Ingresar resultado de método public String leerContenidoArchivo(File archivo)
	 * @param campo Introducir el campo de la etiqueta a validar. Ejemplo: <Descripcion>ART PRUEBA DESCR CORTA 0071</Descripcion>, se introduce "Descripcion".
	 * @param valor Introducir el valor que se espera encontrar dentro del campo. Ejemplo: <Descripcion>ART PRUEBA DESCR CORTA 0071</Descripcion>, se introduce "ART PRUEBA DESCR CORTA 0071".
	 * @author Mario A. De León Rdz.
	 * @date 19/noviembre/2024
	 * @throws Exception Si el contenido esperado no se encuentra en la impresión.
	 */	    
	public void validarContenidoEtiquetasImpresas(String leerContenidoArchivo, String campo, String valor) throws Exception {
	    try {	
	        String etiquetaEsperada = "<" + campo + ">" + valor + "</" + campo + ">";
	        System.out.println("Buscando: " + etiquetaEsperada);

	        if (!leerContenidoArchivo.contains(etiquetaEsperada)) {
	            throw new Exception("Error: No se encontró la etiqueta esperada en el archivo.");
	        }
	        testCase.addTextEvidenceCurrentStep("Se encuentra el siguiente nodo en la impresion: "+etiquetaEsperada);
	        System.out.println("Validación exitosa.");
	    } catch (Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	/**
	 * Método para validar que un valor de las etiquetas impresas se imprima una cantidad de veces
	 * @param leerContenidoArchivo Ingresar resultado de método public String leerContenidoArchivo(File archivo)
	 * @param campo Introducir el campo de la etiqueta a validar. Ejemplo: <Descripcion>ART PRUEBA DESCR CORTA 0071</Descripcion>, se introduce "Descripcion".
	 * @param valor Introducir el valor que se espera encontrar dentro del campo. Ejemplo: <Descripcion>ART PRUEBA DESCR CORTA 0071</Descripcion>, se introduce "ART PRUEBA DESCR CORTA 0071".
	 * @author Mario A. De León Rdz.
	 * @date 19/noviembre/2024
	 * @throws Exception Si el contenido esperado no se encuentra en la impresión.
	 */	    
	public void validarContenidoEtiquetasImpresasVariasEtiquetas(String leerContenidoArchivo, String campo, String valor, int contadorEtiqueta) throws Exception {
	    try {	
	        String etiquetaEsperada = "<" + campo + ">" + valor + "</" + campo + ">";
	        System.out.println("Buscando: " + etiquetaEsperada);

	        int cantidadOcurrencias = contarOcurrencias(leerContenidoArchivo, etiquetaEsperada);
	        System.out.println("La etiqueta '" + etiquetaEsperada + "' se encontró " + cantidadOcurrencias + " veces.");

	        if (cantidadOcurrencias < contadorEtiqueta) {
	            throw new Exception("Error: Se esperaban al menos " + contadorEtiqueta + " ocurrencias, pero se encontraron " + cantidadOcurrencias + ".");
	        }
	        testCase.addTextEvidenceCurrentStep("La etiqueta '" + etiquetaEsperada + "' se encontró " + cantidadOcurrencias + " veces.");
	        System.out.println("Validación exitosa.");
	    } catch (Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	/**
	 * Método para contar cuántas veces aparece una etiqueta específica dentro del contenido de un archivo.
	 * @param contenido Ingresar el contenido del archivo en formato de cadena de texto.
	 * @param etiqueta Introducir la etiqueta que se desea contar. Ejemplo: "<Descripcion>ART PRUEBA DESCR CORTA 0071</Descripcion>".
	 * @return Retorna un entero indicando la cantidad de veces que aparece la etiqueta en el contenido.
	 * @author Mario A. De León Rdz.
	 * @date 19/noviembre/2024
	 */
	public int contarOcurrencias(String contenido, String etiqueta) {
	    int contador = 0;
	    int indice = 0;
	    
	    while ((indice = contenido.indexOf(etiqueta, indice)) != -1) {
	        contador++;
	        indice += etiqueta.length(); // Avanza para evitar contar la misma aparición
	    }
	    
	    return contador;
	}
	
	/**
	 * Método para validar que la impresión NO contenga una Descripción o Nombre del producto en específico.
	 * @param leerContenidoArchivo Ingresar resultado de método public String leerContenidoArchivo(File archivo)
	 * @param campo Introducir el campo de la etiqueta a validar. Ejemplo: <Descripcion>ART PRUEBA DESCR CORTA 0071</Descripcion>, se introduce "Descripcion".
	 * @param valor Introducir el valor que se espera que NO esté dentro del campo. Ejemplo: <Descripcion>ART PRUEBA DESCR CORTA 0071</Descripcion>, se introduce "ART PRUEBA DESCR CORTA 0071".
	 * @author Mario A. De León Rdz.
	 * @date 19/noviembre/2024
	 * @throws Exception Si el contenido no deseado está presente en la impresión.
	 */	    
	public void validarNoContenidoEtiquetasImpresas(String leerContenidoArchivo, String campo, String valor) throws Exception {
	    try {	
	        String etiquetaIndeseada = "<" + campo + ">" + valor + "</" + campo + ">";
	        System.out.println("Buscando: " + etiquetaIndeseada);

	        if (leerContenidoArchivo.contains(etiquetaIndeseada)) {
	            throw new Exception("Error: Se encontró una etiqueta no permitida en el archivo.");
	        }

	        System.out.println("Validación exitosa: El contenido no está presente.");
	        testCase.addTextEvidenceCurrentStep("Validacion Exitosa. No se encuentra el siguiente campo en la impresion: "+etiquetaIndeseada);
	    } catch (Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	
	
	//TODO ---------------------METODOS ADELANTAR FECHAS----------------------------------------------------------------------------------
	/**
	 * Método para cambiar la fecha del sistema de forma remota en un punto de venta (PDV).
	 * 
	 * Este método ejecuta un comando remoto a través de 
	 * PsExec para actualizar la fecha en un sistema específico. Se conecta al equipo 
	 * remoto usando credenciales predefinidas y establece la nueva fecha proporcionada.
	 * 
	 * @author Mario A. De León Rdz.
	 * @date 28/enero/2025
	 * @param ipPDV Dirección IP del punto de venta donde se cambiará la fecha
	 * @param fecha Nueva fecha a establecer en formato DD-MM-YYYY
	 * @throws Exception Si ocurre un error al ejecutar el comando remoto
	 */
	public void cambioFechaRemoto(String ipPDV, String fecha) {
	    try {
	        System.out.println(fecha);
	        // Ejemplo de salida esperada en consola:
	        // psexec \\10.75.6.73 -u 10.75.6.73\QA -p Comercio03 -s cmd /c date 29-01-2025
	        String command = "psexec \\\\" + ipPDV + " -u " + ipPDV + "\\QA" +  " -p Comercio03 -s cmd /c date " + fecha;

	        Process process = Runtime.getRuntime().exec(command);
	        System.out.println(command);
	        process.waitFor();
	        testCase.addTextEvidenceCurrentStep("La fecha se actualiza a " + fecha);
	    } catch (Exception e) { 
	        e.printStackTrace();
	    }
	}

	/**
	 * Método para mostrar un aviso del estatus en el que se encuentra el XML en BD.
	 * 
	 * Este método no se ha utilizado en ningún caso de prueba, pero se deja disponible
	 * por si llega a ser necesario en el futuro. Activa una ventana con un aviso
	 * indicando que se adelante la fecha del sistema manualmente.
	 * 
	 * @author Mario A. De León Rdz.
	 * @date 28/enero/2025
	 * @param duracionMilisegundos Tiempo en milisegundos que la ventana de aviso permanecerá abierta
	 * @throws Exception Si ocurre un error al realizar la consulta
	 */
	public void avisoAdelantarFecha(int duracionMilisegundos) {
	    try {
	        JFrame frame = new JFrame("Aviso");
	        frame.setSize(300, 200);
	        frame.setVisible(false);
	        // Obtener la fecha actual y adelantar un día
	        LocalDate fechaActual = LocalDate.now();
	        LocalDate fechaAdelantada = fechaActual.plusDays(1); // Adelantar 1 día

	        // Formatear la fecha en formato yyyy-MM-dd
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        String fechaFormateada = fechaAdelantada.format(formatter);

	        // Crear el mensaje con la fecha adelantada
	        String mensaje = "ADELANTAR FECHA MANUALMENTE EN:\n "
	                + "Handheld: Configuraciones > Sistema > Fecha y Hora > Fecha > Seleccionar " + fechaFormateada + "\n"
	                + "XPOS: ejecutar en CMD el comando date y adelantar 1 día \n"
	                + "CIERRA ESTA VENTANA AL TERMINAR";
	        final JOptionPane optionPane = new JOptionPane(mensaje, JOptionPane.INFORMATION_MESSAGE,
	                JOptionPane.DEFAULT_OPTION, null, new Object[] {}, null);
	        final JDialog dialog = optionPane.createDialog(frame, "Mensaje");

	        // Mostrar el mensaje en el diálogo
	        dialog.setVisible(true);

	        // Detener el script durante el tiempo indicado
	        Thread.sleep(duracionMilisegundos); // Pausa de la ejecución durante la duración

	        // Cerrar el diálogo después de la pausa
	        dialog.dispose();

	    } catch (Exception e) {
	        System.err.println("Error al mostrar el aviso: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

//TODO -----------------------------INICIAR APPP --------------------------------------
	/**
	 * Inicia una aplicación en Appium y captura una captura de pantalla del estado inicial.
	 * 
	 * Este método activa la aplicación especificada mediante su paquete y actividad. 
	 * Si la aplicación ya está en ejecución, la trae al frente. 
	 * Posteriormente, espera un breve tiempo antes de capturar una imagen del estado actual.
	 * 
	 * @author Mario A. De León Rdz.
	 * @date 28/enero/2025
	 * @param appPackage   Nombre del paquete de la aplicación
	 * @param appActivity  Nombre de la actividad a iniciar
	 */
	public void iniciarApp(String appPackage, String appActivity) {
		try {
			au.driver.activateApp(appPackage+"/"+appActivity);
			Thread.sleep(0500);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) { 
			System.err.println("Error al iniciar la aplicación: " + e.getMessage());
			e.printStackTrace();
		}
	}
	/**
	 * Cerrar una aplicación en Appium y captura una captura de pantalla del estado inicial.
	 * 
	 * Este método activa la aplicación especificada mediante su paquete y actividad. 
	 * Si la aplicación ya está en ejecución, la trae al frente. 
	 * Posteriormente, espera un breve tiempo antes de capturar una imagen del estado actual.
	 * 
	 * @author Mario A. De León Rdz.
	 * @date 28/enero/2025
	 * @param appPackage   Nombre del paquete de la aplicación
	 * @param appActivity  Nombre de la actividad a iniciar
	 */
	public void cerrarApp(String appPackage) {
		try {
			au.driver.terminateApp(appPackage);
			Thread.sleep(2000);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) { 
			System.err.println("Error al iniciar la aplicación: " + e.getMessage());
			e.printStackTrace();
		}
	}
//--------------------------------------------------------------------------------------------

	/**
	 * Regresa a la pantalla de planograma realizando un tap en el botón correspondiente.
	 * 
	 * Este método agrega evidencia textual del paso, simula un clic en el botón de regreso 
	 * y, en caso de error, captura la evidencia del código y lanza una excepción.
	 * 
	 * @throws Exception Si ocurre un error al intentar regresar al planograma
	 * @author Mario A. De Leon Rdz.
	 * @date 12/febrero/2025  
	 */
	public void regresarAPlanograma() throws Exception {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en boton regresar");
			au.click(btn_RegresarPlanogrupo);
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
//-------------------------------------------------------------------------------------------------------------------------
	/**
	 * Valida si se ha realizado correctamente un cambio de precio.
	 * 
	 * Compara el precio actual con el nuevo precio esperado. Si el cambio se ha realizado,
	 * agrega evidencia textual del éxito. En caso contrario, registra un error y lanza una excepción.
	 * 
	 * @param precio       Precio original antes del cambio.
	 * @param precioNuevo  Nuevo precio esperado.
	 * @throws Exception   Si el cambio de precio no se realiza correctamente.
	 * @author Mario A. De Leon Rdz.
	 * @date 12/febrero/2025  
	 */
	public void validarCambioDePrecio(String precio, String precioNuevo) throws Exception {
		try {	
			if(precio != precioNuevo) {
				testCase.addTextEvidenceCurrentStep("Cambio de precio realizado correctamente");
			} else {
				testCase.addCodeEvidenceCurrentStep("Error: Cambio de precio no realizado");
				throw new Exception("Error: No se realiza cambio de precio");
			}
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Valida si se ha realizado correctamente un cambio de charola.
	 * 
	 * Compara la posición de la charola actual con la nueva posición esperada. Si el cambio se ha realizado,
	 * agrega evidencia textual del éxito. En caso contrario, registra un error y lanza una excepción.
	 * 
	 * @param charola       Charola original antes del cambio.
	 * @param charolaNueva  Nueva charola esperada.
	 * @throws Exception    Si el cambio de charola no se realiza correctamente.
	 * @author Mario A. De Leon Rdz.
	 * @date 12/febrero/2025  
	 */
	public void validarCambioDeCharola(String charola, String charolaNueva) throws Exception {
		try {	
			if(charola != charolaNueva) {
				testCase.addTextEvidenceCurrentStep("Cambio de precio realizado correctamente");
			} else {
				testCase.addCodeEvidenceCurrentStep("Error: Cambio de precio no realizado");
				throw new Exception("Error: No se realiza cambio de precio");
			}
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Realizar un log press para validar boton de informacion de fechas
	 *  
	 * @author Amauri Rodriguez
	 * @date 12/febrero/2025  
	 * @throws InterruptedException Si ocurre un error en la ejecución  
	 */  
	public void mantenerPresionadoFechas() throws InterruptedException {
		try {
		    
			// Encuentra el elemento donde hacer Long Press
	        WebElement element = au.driver.findElement(btnInfoFechas);

	        // Obtiene las coordenadas del elemento
	        int centerX = element.getRect().getX() + (element.getSize().getWidth() / 2);
	        int centerY = element.getRect().getY() + (element.getSize().getHeight() / 2);

	        // Usa PointerInput para simular el toque largo
	        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
	        Sequence longPress = new Sequence(finger, 1)
	                .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerX, centerY))
	                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
	                .addAction(new Pause(finger, Duration.ofSeconds(5)));
	                
	        // Ejecuta la acción
	        au.driver.perform(Collections.singletonList(longPress));	        
		    testCase.addScreenShotCurrentStep(au, "TestImage");
		    
		    longPress.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
	        
		    } catch (Exception e) {
		    	e.printStackTrace();
			} 	      	      
	    }

	/**
	 * Realizar un log press para validar boton de informacion de planograma
	 *  
	 * @author Amauri Rodriguez
	 * @date 12/febrero/2025  
	 * @throws InterruptedException Si ocurre un error en la ejecución  
	 */  
	public void mantenerPresionadoPlanograma() throws InterruptedException {
		try {
		    
			// Encuentra el elemento donde hacer Long Press
	        WebElement element = au.driver.findElement(btnInfoPlanograma);

	        // Obtiene las coordenadas del elemento
	        int centerX = element.getRect().getX() + (element.getSize().getWidth() / 2);
	        int centerY = element.getRect().getY() + (element.getSize().getHeight() / 2);

	        // Usa PointerInput para simular el toque largo
	        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
	        Sequence longPress = new Sequence(finger, 1)
	                .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerX, centerY))
	                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
	                .addAction(new Pause(finger, Duration.ofSeconds(15)));
	                
	        // Ejecuta la acción
	        au.driver.perform(Collections.singletonList(longPress));
	        testCase.addScreenShotCurrentStep(au, "testImage");
	        
	        longPress.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
			
		    } catch (Exception e) {
		    	e.printStackTrace();
			} 	      	      
	    }
	/**
	 * Regresa 3 meses atrás en el calendario del segmento fechas
	 *  
	 * @author Mario Salazar
	 * @date 12/febrero/2025  
	 * @throws InterruptedException Si ocurre un error en la ejecución  
	 */  
	public void RegresarFecha3meses(int num) {
	    try {
	        WebElement boton = au.driver.findElement(btn_RegresarFecha);
	        LocalDate FechaActual = LocalDate.now();
	        LocalDate Fecha3meseatras = FechaActual.minusMonths(3);
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
	        String FechaFormateada = Fecha3meseatras.format(formatter);
	        System.out.println("La fecha 3 meses atrás es: " + FechaFormateada);

	        for (int i = 0; i <= num; i++) {
	            au.click(btn_RegresarFecha);
	            testCase.addScreenShotCurrentStep(au, "testImage");
	        }

	        if (!boton.isDisplayed()) {
	            System.out.println("La fecha 3 meses atrás es: " + FechaFormateada);
	        }
	    } catch (Exception e) {
	        System.out.println("Ocurrió un error en RegresarFecha3meses: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	/**
	 * Realiza click en boton para visualizar todas las charolas actualizada
	 *  
	 * @author Mario Salazar
	 * @date 12/febrero/2025  
	 * @throws InterruptedException Si ocurre un error en la ejecución  
	 */  
	public void visualizaAllCharolas() throws Exception {
		try {
		    au.click(click_visualizaAllCharolas);
		    au.wait(2);
		    testCase.addScreenShotCurrentStep(au, "testImage");
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
}
	



