package WEB.updateNomina.om;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Pattern;

import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class Nomina{
	private SeleniumUtil u;
	private WebDriverWait wait;
	private TestCase testCase;
	
	//Menu SADEL
	By imgNomina = By.xpath("/html/body/div[2]/table/tbody/tr[2]/td[2]/table/tbody/tr[1]/td[2]/table/tbody/tr[1]/td[2]/table/tbody/tr[1]/td[2]/a/img");
	//Elementos Sección Nómina
	By liTabJornadas = By.xpath("//*[@id=\"tab2\"]");
	By liTabRecibos = By.id("tab3");
	By liTabFiniquitos = By.id("tab5");
	
		//Pestaña Jornadas
	By inputPlazaJornadas = By.id("idPlaza");  //sirve para cualquier pestaña de nomina
	By option10DCUColima = By.xpath("//*[@id='idPlaza']/option[16]");

	By inputTiendaJornadasId = By.id("idStore"); //sirve para cualquier pestaña de nomina
	
	By selectAccion = By.xpath("//select[@name='employee' and @onchange='selectAction(this)']");
	
	By imgBotonEnviarJornadas = By.id("Image36");
	
	By selectEmpleadoId = By.id("idAsignacion");
	By optionEmpleado1 = By.xpath("//*[@id=\"idAsignacion\"]/option[2]");
	
	By selectJornadaId = By.id("idWorkingDay");
	By optionTrabaja6Dias = By.xpath("//*[@id=\"idWorkingDay\"]/option[2]");
	
		//Checkboxes dias de la jornada
	By checkboxLunes= By.id("cbLunes");
	By checkboxMartes= By.id("cbMartes");
	By checkboxMiercoles= By.id("cbMiercoles");
	By checkboxJueves= By.id("cbJueves");
	By checkboxViernes= By.id("cbViernes");
	By checkboxSabado= By.id("cbSabado");
	By checkboxDomingo= By.id("cbDomingo");
	List<By> checkboxes = new ArrayList<>();
	
	By imgBotonGuardarJornada= By.xpath("//*[@id='btnGuardar']/img");

	//Pestana Recibos
	By selectTiendaRecibos = By.xpath("//*[@id='idStore']");
	By selectPeriodo = By.id("idPeriodo");
	By thColumnaReciboNominaFiniquito = By.id("6");
	By celdaPrimerFiniquito = By.xpath("//*[@id='idTablaPrincipal']/tbody/tr[1]/td[7]/p/b/i/a");
	
	//Pestana Solicitud de finiquitos
	By inputCheckboxSoliFiniquito = By.id("solicitudFiniq_0");
	By imgBotonSolicitarFiniquitos = By.xpath("//img[@name='Image36' and @alt='Solicitar']");
    
	private UpdateNomina updNom;
	private SikuliSADEL sikuliSADEL;
	
	SikuliUtil sk = new SikuliUtil();
    String pathImg = sk.PatternPath("WEB\\"+"SADEL");
    Pattern pathimg = new Pattern();
    ArrayList<String> ListImage = new ArrayList<String>();
    HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
    ArrayList<Pattern> patterns = new ArrayList<Pattern>();
    File Fol = new File(pathImg);
    File Folder = new File(pathImg);
    
	/**
	 * Constructor.
	 * 
	 * @param u
	 * @param testCase
	 *
	 * @author Mario A. De Leon Rdz.
	 */
	public Nomina(SeleniumUtil u, TestCase testCase) {
	    super(); // Llama al constructor de la clase padre (en este caso, Object)
	    this.u = u; // Asigna el objeto SeleniumUtil pasado como parámetro a la variable de instancia
	    this.wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(60)); // Inicializa WebDriverWait con el driver de SeleniumUtil y un timeout de 60 segundos
	    this.testCase = testCase; // Asigna el objeto TestCase pasado como parámetro a la variable de instancia
	    this.updNom = new UpdateNomina(u, testCase);
	    this.sikuliSADEL = new SikuliSADEL(u, testCase, sk, pathimg, ListImage, ImgPatter, patterns, Fol);
	}
	
	/**
	 * Método para hacer click en el botón Nomina del menú de SADEL (visible en todo el aplicativo)
	 * @author Mario A. De Leon Rdz.
	 */
	public void SeleccionarBotonNomina() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(imgNomina));
			testCase.addScreenShotCurrentStep(u, "seleccionar boton Nomina");
			u.click(imgNomina);

		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo dar click en enlace para ingresar a boton Realizar Movimientos");
		}
	}
	
	/**
	 * Método para hacer click en la pestaña Jornadas
	 * @author Mario A. De Leon Rdz.
	 */
	public void SeleccionarPestanaJornadas() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(liTabJornadas));
			u.click(liTabJornadas);
			testCase.addScreenShotCurrentStep(u, "Se muestra sección Jornadas");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo dar click pestaña Jornadas");
		}
	}
	
	/**
	 * Método para seleccionar plaza y tienda en la sección de Jornadas
	 * @author Mario A. De Leon Rdz.
	 */
	public void SeleccionarPlazaYTiendaJornadas() {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(inputPlazaJornadas));
			testCase.addScreenShotCurrentStep(u, "Se muestra plaza y tienda seleccionadas en sección Jornadas");
			u.click(inputPlazaJornadas);
			updNom.robotKeyDown(1);
			
			testCase.addScreenShotCurrentStep(u, "Se muestra plaza y tienda seleccionadas en seccion Jornadas");
			WebElement elementoWebInputTienda = u.getDriver().findElement(inputTiendaJornadasId);
			elementoWebInputTienda.click();
			elementoWebInputTienda.sendKeys("Manantiales HMO");
			System.out.println("inicia robot");
			Robot robot;
			try {
				robot = new Robot();
				robot.keyPress(KeyEvent.VK_ENTER);
				System.out.println("Press enter");
				robot.keyRelease(KeyEvent.VK_ENTER);
				System.out.println("Release enter");
			} catch (AWTException e) {
				e.printStackTrace();
			}
			System.out.println("termina robot");
			System.out.println("Exitoso: elementoWebInputTienda.click()");
			
			//updNom.robotKeyDown(42); //Con 42 se seleccion tienda Del Bosque HMO si previamente se selecciono plaza 10MXL Hermo Sierra
			//updNom.robotKeyDown(52);
			//updNom.robotKeyDown(71);
			

			testCase.addScreenShotCurrentStep(u, "Se muestra plaza y tienda seleccionadas en sección Jornadas");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo capturar plaza y tienda");
		}
	}
	
	/**
	 * Método para seleccionar Empleado y Edición en la sección de Jornadas
	 * @author Mario A. De Leon Rdz.
	 */
	public void SeleccionarEmpleado() {
		try {
			u.click(selectEmpleadoId);
			System.out.println("Exitoso: u.click(selectEmpleadoId);");
			
			updNom.robotKeyDown(2);
			
			testCase.addScreenShotCurrentStep(u, "Se muestra empleado y edicion seleccionados en seccion Jornadas");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo capturar plaza y tienda");
		}
	}
	
	/**
	 * Método para editar jornada del empleado. Se selecciona Jornada Trabaja 6 días y descansa 1 día, despues se deseleccionan todos los días y se selecciona Miercoles como día de descanso (si Miercoles ya era el día de descanso del empleado antes de la edición en su lugar se selecciona Martes como día de descanso)
	 * @author Mario A. De Leon Rdz.
	 */
	public void EditarJornada() {
		try {
			u.click(selectJornadaId);
			//u.click(optionTrabaja6Dias);
			//updNom.robotKeyDown(1);
			testCase.addScreenShotCurrentStep("jornada antes de los cambios");
			checkboxes.add(checkboxLunes);
		    checkboxes.add(checkboxMartes);
//		    checkboxes.add(checkboxMiercoles);
		    checkboxes.add(checkboxJueves);
		    checkboxes.add(checkboxViernes);
		    checkboxes.add(checkboxSabado);
		    checkboxes.add(checkboxDomingo);
		    WebElement elementoCheckboxMiercoles = u.getDriver().findElement(checkboxMiercoles);
		   
		    for (By checkboxBy : checkboxes) {
		    	//Se iteran todos los dias menos miercoles
		    	try {
		    		WebElement elementoCheckboxActual = u.getDriver().findElement(checkboxBy);
		    		if(elementoCheckboxActual.isSelected()) {
		    			elementoCheckboxActual.click(); //si el checkbox esta seleccionado se deselecciona
		    		} else {
		    			
		    		}
		    	} catch (Exception e) {
		    		System.out.println("Error al manejar checkbox " + checkboxBy + ": " + e.getMessage());
		    		}
		    }
		    
		    if(!elementoCheckboxMiercoles.isSelected()) {
		    	//Si miercoles no esta seleccionado, seleccionalo
		    	elementoCheckboxMiercoles.click();
		    } else {
		    	//Si miercoles esta seleccionado, deseleccionalo y selecciona martes
		    	elementoCheckboxMiercoles.click();
		    	u.click(checkboxMartes);
		    }
		    for (int i = 16; i <= 31; i++) {
		        u.click(By.id("{" + i + ",3}"));
		    }
		    testCase.addScreenShotCurrentStep(u, "Se muestra jornada del empleado editada");
		    System.out.println("Inicia Boton Guardar");
		    //u.scrollToElement(imgBotonGuardarJornada);
			u.click(imgBotonGuardarJornada);
			System.out.println("Se encuentra Boton Guardar");
			testCase.addScreenShotCurrentStep(u, "La jornada se modifico exitosamente");
			updNom.AceptarTodasLasAlertas();
		} catch (Exception e) {
			System.out.println(" -> " + e);
			//this.u.close();
			throw new AssertionError("No se pudo editar Jornada");
		}
	}

	public void EnviarJornadas() {
		wait.until(ExpectedConditions.presenceOfElementLocated(selectAccion));
		u.click(selectAccion);
		testCase.addScreenShotCurrentStep("se muestran las acciones");
		updNom.robotKeyDown(1);
		testCase.addScreenShotCurrentStep("se selecciona accion enviar");
	
		wait.until(ExpectedConditions.elementToBeClickable(imgBotonEnviarJornadas));
		u.click(imgBotonEnviarJornadas);
		updNom.AceptarTodasLasAlertas();
	
	}

	public void SeleccionarPestanaRecibos() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(liTabRecibos));
			u.click(liTabRecibos);
			testCase.addScreenShotCurrentStep(u, "Se muestra seccion Solicitud de Finiquitos");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo dar click pestaña Jornadas");
		}
	}

	public void SeleccionarTiendaYPeriodoRecibos() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(selectPeriodo));

			u.click(selectPeriodo);

			u.sendKeys(selectPeriodo, "31 - 2024");
			System.out.println("inicia robot");
			Robot robot;
			try {
				robot = new Robot();
				robot.keyPress(KeyEvent.VK_ENTER);
				System.out.println("Press enter");
				robot.keyRelease(KeyEvent.VK_ENTER);
				System.out.println("Release enter");
			} catch (AWTException e) {
				e.printStackTrace();
			}
			System.out.println("termina robot");
			testCase.addScreenShotCurrentStep(u, "Periodo seleccionado");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo seleccionar period");
		}
	}

	public void SeleccionarFiniquitoPrevioSolicitado(String nombre) {
		//Decidi utilizar esta solucion porque no encontré forma de utilizar un selector que sirviera para varios casos
		//debido a que tienen pocos atributos el elmento enlace para abrir la ventana del finiquito
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(celdaPrimerFiniquito));
			WebElement FiniquitoSolicitado = u.getDriver().findElement(By.xpath("//*[text()='"+nombre+"']"));
			FiniquitoSolicitado.click();
			System.out.println("inicia robot");
			Robot robot;
			try {
				robot  = new Robot();
				//Asi se llega al primer finiquito
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
				testCase.addScreenShotCurrentStep(u, "Se muestran finiquitos en pestana recibos");
				
				//Asi se abre el enlace y se abre ventana con finiquito
				robot.keyPress(KeyEvent.VK_ENTER);
				System.out.println("Press enter");
				robot.keyRelease(KeyEvent.VK_ENTER);
				System.out.println("Release enter");
				testCase.addScreenShotCurrentStep(u, "Se muestran finiquito");

			} catch (AWTException e) {
				e.printStackTrace();
			}
			System.out.println("termina robot");
			u.hardWait(1);
			sikuliSADEL.AceptarFiniquitoPrevio(Folder);
			testCase.addScreenShotCurrentStep(u, "Finiquito seleccionado");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			//this.u.close();
			throw new AssertionError("No se pudo aceptar el finiquito previo");
		}
	}
	/**
	 * Método para hacer click en pestaña finiquitos
	 * @author Mario A. De Leon Rdz.
	 */
	public void SeleccionarPestanaFiniquitos() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(liTabFiniquitos));
			u.click(liTabFiniquitos);
			testCase.addScreenShotCurrentStep(u, "Se muestra sección Solicitud de Finiquitos");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo dar click pestaña Jornadas");
		}
	}
	/**
	 * Método para seleccionar plaza y tienda en la sección de finiquitos.
	 * Espera a que el campo de entrada para la plaza sea visible, hace clic en él,
	 * luego selecciona la tienda usando la clase Robot para simular la pulsación de teclas.
	 * Toma capturas de pantalla en varios puntos para verificar la selección.
	 * @author Mario A. De Leon Rdz.
	 */
	public void SeleccionarPlazaYTiendaFiniquitos() {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(inputPlazaJornadas));
			testCase.addScreenShotCurrentStep(u, "Se muestra plaza y tienda seleccionadas en sección Jornadas");
			u.click(inputPlazaJornadas);
	

			updNom.robotKeyDown(1);

			testCase.addScreenShotCurrentStep(u, "Se muestra plaza y tienda seleccionadas en seccion Jornadas");
			WebElement elementoWebInputTienda = u.getDriver().findElement(inputTiendaJornadasId);
			elementoWebInputTienda.click();
			System.out.println("Exitoso: elementoWebInputTienda.click()");

			elementoWebInputTienda.sendKeys("Del Bosque HMO");
			System.out.println("inicia robot");
			Robot robot;
			try {
				robot = new Robot();
				robot.keyPress(KeyEvent.VK_ENTER);
				System.out.println("Press enter");
				robot.keyRelease(KeyEvent.VK_ENTER);
				System.out.println("Release enter");
			} catch (AWTException e) {
				e.printStackTrace();
			}
			System.out.println("termina robot");
			testCase.addScreenShotCurrentStep(u, "Se muestra plaza y tienda seleccionadas en sección Jornadas");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo capturar plaza y tienda");
		}
	}
	/**
	 * Método para solicitar finiquitos.
	 * Espera a que el checkbox de solicitud de finiquitos sea visible y clickeable,
	 * lo selecciona, acepta todas las alertas y hace clic en el botón para solicitar finiquitos.
	 * Toma una captura de pantalla después de realizar la solicitud.
	 * @author Mario A. De Leon Rdz.
	 */
	public void SolicitarFiniquitos() {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(inputCheckboxSoliFiniquito));
			u.click(inputCheckboxSoliFiniquito);
			updNom.AceptarTodasLasAlertas();
			u.click(imgBotonSolicitarFiniquitos);
			updNom.AceptarTodasLasAlertas();
			} catch (Exception e) {
				System.out.println(" -> " + e);
				this.u.close();
				throw new AssertionError("No se pudo solicitar Finiquitos");
			};
	}
	
	
} 