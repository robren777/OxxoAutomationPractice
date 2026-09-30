package WEB.updateNomina.om;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

/**
 * Clase con Elementos y Metodos provenientes de la sección Realizar Movimientos de SADEL y alguna subsecciones derivadas de esta."
 *
 * @author Mario Alberto De León Rodríguez
 * @version 1.0
 * 
 */
public class SikuliSADEL {
	private SeleniumUtil u;
	private WebDriverWait wait;
	private TestCase testCase;
	private SikuliUtil sk;
	private Pattern pathimg;
	private ArrayList<String> ListImage;
	private HashMap<String, Pattern> ImgPattern;
	private ArrayList<Pattern> patterns;
	private File Fol;



	/**
	 * Constructor de la clase SikuliSADEL.
	 * 
	 * @param u Objeto SeleniumUtil para manejar las interacciones con Selenium.
	 * @param testCase Objeto TestCase para manejar los casos de prueba.
	 * @param sk Objeto SikuliUtil para manejar las funciones de Sikuli.
	 * @param pathimg Patr�n inicial de im�genes.
	 * @param ListImage Lista de nombres de im�genes.
	 * @param ImgPattern Mapa de patrones de im�genes.
	 * @param patterns Lista de patrones de Sikuli.
	 * @param Fol Archivo que representa la carpeta de las im�genes.
	 * @author Mario A. De Le�n Rdz.
	 */

	public SikuliSADEL(SeleniumUtil u, TestCase testCase, SikuliUtil sk, Pattern pathimg, ArrayList<String> ListImage,
					   HashMap<String, Pattern> ImgPattern, ArrayList<Pattern> patterns, File Fol) {
	    super();
	    this.u = u; // Asigna el objeto SeleniumUtil pasado como parámetro a la variable de instancia
	    this.wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(60)); // Inicializa WebDriverWait con el driver de SeleniumUtil y un timeout de 60 segundos
	    this.testCase = testCase; // Asigna el objeto TestCase pasado como parámetro a la variable de instancia
		this.sk = sk;
		this.pathimg = pathimg;
		this.ListImage = ListImage;
		this.ImgPattern = ImgPattern;
		this.patterns = patterns;
		this.Fol = Fol;
	}

	public HashMap<String, Pattern> getPatternsImg(File Fol) {
		File[] files = Fol.listFiles();
		for (File s : files) {
			patterns.add(new Pattern(s.getAbsolutePath()).similar((float) .95));
		}
		for (Pattern p : patterns) {
			String fname = p.getFilename().toString();
			fname = fname.replace("\\", "-");
			fname = fname.replace(".png", "");
			fname = fname.replace(".PNG", "");
			String[] afname = fname.split("-");
			ImgPattern.put(afname[afname.length - 1], p);
		}

		return ImgPattern;
	}
	
	/**
	 * M�todo para buscar y seleccionar un empleado al realizar un movimiento (baja, cambio de sueldo, cambio de puesto).
	 * Utiliza im�genes almacenadas para interactuar con la interfaz gr�fica de usuario a trav�s de Sikuli.
	 * 
	 * @param folder Carpeta donde se encuentran las im�genes necesarias para el m�todo.
	 * @param nss N�mero de Seguro Social (NSS) del empleado que se desea buscar.
	 * @throws FindFailed Si no se encuentra alguna de las im�genes esperadas en la pantalla.
	 */
	public void FiltroVentanaEmpleado(File folder, String nss) throws FindFailed {
	    try {
	        ImgPattern = this.getPatternsImg(folder);
	        sk.screen.wait(ImgPattern.get("campoNombres"), 35).highlight();
	        sk.screen.type(Key.TAB);
	        sk.screen.type(Key.TAB);
	        sk.screen.type(Key.TAB);
	        sk.screen.type(nss);
	        testCase.addScreenShotCurrentStep("se busca empleado con nss");
	        sk.screen.wait(ImgPattern.get("botonBuscarEmpleados"), 35).click();
	        sk.screen.wait(ImgPattern.get("radioBotonBuscarEmpleados"), 35).click();
	        sk.screen.wait(ImgPattern.get("botonAceptarEmpleados"), 35).click();
	        u.hardWait(1); //esperar a que cierre sikulix correctamente y no se interponga en el siguiente comando
	    } catch (FindFailed e) {
	        System.out.println("Error en FiltroVentanaEmpleado: " + e.getMessage());
	        throw e;
	    }
	}
	
	/**
	 * M�todo para aceptar el salario diario bruto al realizar un movimiento de salario.
	 * Utiliza im�genes almacenadas para interactuar con la interfaz gr�fica de usuario a trav�s de Sikuli.
	 * 
	 * @param folder Carpeta que contiene las im�genes necesarias para el m�todo.
	 * @throws FindFailed Si no se encuentra alguna de las im�genes esperadas en la pantalla.
	 */
	public void FiltroVentanaSalarioDiarioBruto(File folder) throws FindFailed {
	    try {
	        ImgPattern = this.getPatternsImg(folder);
	        testCase.addScreenShotCurrentStep("sueldo");
	        sk.screen.wait(ImgPattern.get("botonAceptarSueldo2"), 35).click();
	        u.hardWait(1); //esperar a que cierre sikulix correctamente y no se interponga en el siguiente comando
	    } catch (FindFailed e) {
	        System.out.println("Error en FiltroVentanaSalarioDiarioBruto: " + e.getMessage());
	        throw e;
	    }
	}


	/**
	 * M�todo para hacer click en el boton cancelar dentro de la ventana para buscar empleados en el movimiento tipo Alta, esto es debido a que previamente
	 * se ingres� el NSS del empleado en otro campo y al hacer esto se autorellenan los datos del empleado.
	 * Utiliza im�genes almacenadas para interactuar con la interfaz gr�fica de usuario a trav�s de Sikuli.
	 * 
	 * @param folder Carpeta que contiene las im�genes necesarias para el m�todo.
	 * @throws FindFailed Si no se encuentra alguna de las im�genes esperadas en la pantalla.
	 */
	public void FiltroVentanaAltaNSSEmpleado(File folder) throws FindFailed {
	    try {
	        ImgPattern = this.getPatternsImg(folder);
	        sk.screen.click();
	        //sk.screen.wait(ImgPattern.get("radioBotonBuscarEmpleados2"), 35).click();
	        testCase.addScreenShotCurrentStep("empleado seleccionado");
	        sk.screen.wait(ImgPattern.get("botonCancelarBuscarEmpleados"), 35).click();
	        u.hardWait(1); //esperar a que cierre sikulix correctamente y no se interponga en el siguiente comando
	    } catch (FindFailed e) {
	        System.out.println("Error en FiltroVentanaAltaNSSEmpleado: " + e.getMessage());
	        throw e;
	    }
	}


	/**
	 * M�todo para seleccionar un puesto vac�o durante el movimiento tipo Alta. Es necesario A�adir captura de pantalla
	 * del puesto vac�o a la carpeta Patterns del proyecto.
	 * Utiliza im�genes almacenadas para interactuar con la interfaz gr�fica de usuario a trav�s de Sikuli.
	 * 
	 * @param folder Carpeta que contiene las im�genes necesarias para el m�todo.
	 * @throws FindFailed Si no se encuentra alguna de las im�genes esperadas en la pantalla.
	 */
	public void FiltroVentanaAltaPuestoEmpleado(File folder) throws FindFailed {
	    try {
	        ImgPattern = this.getPatternsImg(folder);
	        sk.screen.click();
	        sk.screen.wait(ImgPattern.get("radioBotonSeleccionarPuesto4"), 35).click();
	        testCase.addScreenShotCurrentStep("puesto seleccionado");
	        sk.screen.wait(ImgPattern.get("botonSeleccionarPuesto3"), 35).click();
	        u.hardWait(1); //esperar a que cierre sikulix correctamente y no se interponga en el siguiente comando
	    } catch (FindFailed e) {
	        System.out.println("Error en FiltroVentanaAltaPuestoEmpleado: " + e.getMessage());
	        throw e;
	    }
	}


	/**
	 * M�todo para seleccionar un puesto vac�o durante el movimiento tipo Cambio de puesto. Es necesario A�adir captura de pantalla
	 * del puesto vac�o a la carpeta Patterns del proyecto.
	 * Utiliza im�genes almacenadas para interactuar con la interfaz gr�fica de usuario a trav�s de Sikuli.
	 * 
	 * @param folder Carpeta que contiene las im�genes necesarias para el m�todo.
	 * @throws FindFailed Si no se encuentra alguna de las im�genes esperadas en la pantalla.
	 */
	public void FiltroVentanaCambioDePuestoEmpleado(File folder) throws FindFailed {
	    try {
	        ImgPattern = this.getPatternsImg(folder);
	        sk.screen.click();
	        sk.screen.wait(ImgPattern.get("radioBotonSeleccionarPuesto5"), 35).click(); //Este boton de aqui siempre hay que cambiarlo, porque varia en cada tienda
	        testCase.addScreenShotCurrentStep("puesto seleccionado");
	        sk.screen.wait(ImgPattern.get("botonSeleccionarPuesto4"), 35).click();
	        u.hardWait(1); //esperar a que cierre sikulix correctamente y no se interponga en el siguiente comando
	    } catch (FindFailed e) {
	        System.out.println("Error en FiltroVentanaCambioDePuestoEmpleado: " + e.getMessage());
	        throw e;
	    }
	}

	/**
	 * M�todo para aceptar un finiquito previo. Es el paso final para enviar la solicitud de un finiquito previo desde SADEL.
	 * Utiliza im�genes almacenadas para interactuar con la interfaz gr�fica a trav�s de Sikuli.
	 * 
	 * @param folder Carpeta que contiene las im�genes necesarias para el m�todo.
	 * @throws FindFailed Si no se encuentra alguna de las im�genes esperadas en la pantalla.
	 */
	public void AceptarFiniquitoPrevio(File folder) throws FindFailed {
	    try {
	        ImgPattern = this.getPatternsImg(folder);
	        sk.screen.wait(ImgPattern.get("botonAceptarFiniquitoPrevio"), 35).click(); //Este boton de aqui siempre hay que cambiarlo, porque varia en cada tienda
	        testCase.addScreenShotCurrentStep("Finiquito aceptado");
	        u.getDriver().switchTo().alert().accept();
	        testCase.addScreenShotCurrentStep("Finiquito aceptado2");
	        u.hardWait(1); //esperar a que cierre sikulix correctamente y no se interponga en el siguiente comando
	    } catch (FindFailed e) {
	        System.out.println("Error en AceptarFiniquitoPrevio: " + e.getMessage());
	        throw e;
	    }
	}
	
} 