package Mobile.cedis.om;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

@SuppressWarnings("deprecation")
public class Tablet_A03_Menus_y_submenus {
	private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;

	/**
	//Opcion Navegacion
	private By CEDIS_btn_navegacion = By.xpath("//android.widget.ImageButton[@content-desc=\"Open navigation drawer\"]");
	boolean btn_navegacion = au.driver.findElement(CEDIS_btn_navegacion).isDisplayed();
	//Gente
	private By Cedis_btn_Gente = By.xpath("(//android.widget.FrameLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/cvMenu\"])[1]");
	boolean btn_Gente = au.driver.findElement(Cedis_btn_Gente).isDisplayed();

	//Control
	private By CEDIS_btn_control = By.xpath("(//android.widget.FrameLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/cvMenu\"])[2]");
	boolean control = au.driver.findElement(CEDIS_btn_control).isDisplayed();

	//Preinventarios
	private By CEDIS_btn_preinventarios = By.xpath("(//android.widget.FrameLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/cvMenu\"])[1]");
	boolean btn_preinventarios = au.driver.findElement(CEDIS_btn_preinventarios).isDisplayed();
	//Preinventarios Inteligentes
	private By CEDIS_btn_preinvenInte = By.xpath("(//android.widget.FrameLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/cvMenu\"])[2]");
	boolean btn_preinvenInte = au.driver.findElement(CEDIS_btn_preinvenInte).isDisplayed();
	*/

	//Excelencia Operativa
	private By CEDIS_btn_exceOperativa = By.xpath("(//android.widget.FrameLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/cvMenu\"])[3]");
	//boolean btn_execOperativa = au.driver.findElement(CEDIS_btn_exceOperativa).isDisplayed();
	private By corte_Z = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvMenuName\" and @text=\"Corte Z y Control Fajillas\"]");
	
	/**
	//Etiquteo -Submenu de//Excelencia Operativa
	private By CEDIS_btn_etiqueteo = By.xpath("(//android.widget.FrameLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/cvMenu\"])[1]");
	boolean btn_etiqueteo = au.driver.findElement(CEDIS_btn_etiqueteo).isDisplayed();
	//Portales -Submenu de//Excelencia Operativa
	private By CEDIS_btn_portales = By.xpath("(//android.widget.FrameLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/cvMenu\"])[2]");
	boolean btn_portales = au.driver.findElement(CEDIS_btn_portales).isDisplayed();
	//Corte Z y Control Fajillas -Submenu de//Excelencia Operativa
	//private By CEDIS_btn_corteZ = pendiente que que apareca en pantalla
	  */

	//Herramienta de Compra -Submenu de//Excelencia Operativa
	private By CEDIS_txt_herramientaCompra = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvMenuName\" and @text=\"Herramienta de Compra\"]");
	private By CEDIS_btn_herramienta_Compras = By.xpath("(//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ibImage\"])[4]");


	
	//(//android.widget.ImageView[@elementIdd="00000000-0000-0620-ffff-ffff00000130"])
	//boolean btn_herramientaCompras = au.driver.findElement(CEDIS_btn_herramientaCompras).isDisplayed();
	
	/**		
	//Movilidad -Submenu de Herramienta de Compra
	private By CEDIS_btn_movilidad = By.xpath("(//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ibImage\" and @elementId=\"00000000-0000-04ca-ffff-ffff00000215\"])[3]");
	boolean btn_movilidad = au.driver.findElement(CEDIS_btn_movilidad).isDisplayed();
	*/
		
	//Cometa -Submenu de Herramienta de Compra
	private By CEDIS_btn_Cometa = By.xpath("(//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ibImage\"])[2]");
	
	private By CEDIS_btn_Cometa_TT = By.xpath("(//android.widget.LinearLayout[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/lyMenuItem'])[2]");
	//private By CEDIS_btn_Cometa = By.xpath("(//android.widget.FrameLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/cvMenu\"])[2]/android.view.ViewGroup");
	//boolean btn_Cometa = au.driver.findElement(CEDIS_btn_Cometa).isDisplayed();
	//(//android.widget.ImageView[@resource-id="com.femco.oxxo.operaciones.mitienda.debug:id/ibImage"])[2]
	/**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase Menus_y_submenus
	 *
	 * @param au            (AppiumUtil): objeto de la clase
	 * @param testCase      (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 * @author Juan Ibarra
	 */
	public Tablet_A03_Menus_y_submenus(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
		this.testCase = testCase;
		this.au = au;
		this.baseExecution = baseExecution;
	}

	public void logeoInicioHerramientas() throws IOException, InterruptedException {
		/**
		 * Método - Realizar el logeo de la aplicación llegando hasta la pantalla del menu de Herraminetas de CEDIS
		 * Menu de Herramientas de Compras de CEDIS
		 * submenu de Herramienta de Compras borrando cache
		 * Validar Pantalla Principal de Recepción de Pedido.
		 * 
		 * @author Juan Ibarra
		 * @param N/A
		 * @return N/A
		 */

		au.click(CEDIS_btn_exceOperativa); // Dar clic en el boton Excelencia Operativa

		WebElement elemento = au.driver.findElement(CEDIS_txt_herramientaCompra);
		// Obtener la posición del elemento en la pantalla (esquina superior izquierda)
		// y guardarla en la variable 'punto' de tipo Point
		Point punto = elemento.getLocation();
		// Obtener el tamaño del elemento (ancho y altura) y guardarlo en la variable
		// 'dimension' de tipo Dimension
		Dimension dimension = elemento.getSize();

		/*
		 * Calcular la coordenada X del centro del elemento
		 * 'punto.getX()' da la posición X de la esquina superior izquierda del elemento
		 * 'dimension.getWidth() / 2' calcula la mitad del ancho del elemento
		 * La suma de estos dos valores da la coordenada X del centro del elemento
		 */
		int x = punto.getX() + (dimension.getWidth() / 2);

		/*
		 * Calcular la coordenada Y para realizar el clic
		 * 'punto.getY()' da la posición Y de la esquina superior izquierda del elemento
		 * Restar 30 unidades ajusta la posición vertical del clic hacia arriba en la
		 * pantalla en este caso para alcanzar el boton de la HC
		 */
		int y = punto.getY() - 30;

		// Ejecutar un comando ADB para simular un clic en las coordenadas (x, y)
		Runtime.getRuntime().exec("adb -s 22165523600822 shell input tap " + x + " " + y);
		Thread.sleep(2000);
		// au.click(CEDIS_btn_herramientaCompras);

		au.click(CEDIS_btn_Cometa); // Dar clic en el boton Cometa
	}
	
	public void logeoInicioHerramientasTT() throws IOException, InterruptedException {
		/**
		 * Método - Realizar el logeo de la aplicación llegando hasta la pantalla del menu de Herraminetas de CEDIS
		 * Menu de Herramientas de Compras de CEDIS
		 * submenu de Herramienta de Compras borrando cache
		 * Validar Pantalla Principal de Recepción de Pedido.
		 * 
		 * @author Juan Ibarra
		 * @param N/A
		 * @return N/A
		 */

		au.click(CEDIS_btn_exceOperativa); // Dar clic en el boton Excelencia Operativa

		WebElement elemento = au.driver.findElement(CEDIS_txt_herramientaCompra);
		// Obtener la posición del elemento en la pantalla (esquina superior izquierda)
		// y guardarla en la variable 'punto' de tipo Point
		Point punto = elemento.getLocation();
		// Obtener el tamaño del elemento (ancho y altura) y guardarlo en la variable
		// 'dimension' de tipo Dimension
		Dimension dimension = elemento.getSize();

		/*
		 * Calcular la coordenada X del centro del elemento
		 * 'punto.getX()' da la posición X de la esquina superior izquierda del elemento
		 * 'dimension.getWidth() / 2' calcula la mitad del ancho del elemento
		 * La suma de estos dos valores da la coordenada X del centro del elemento
		 */
		int x = punto.getX() + (dimension.getWidth() / 2);

		/*
		 * Calcular la coordenada Y para realizar el clic
		 * 'punto.getY()' da la posición Y de la esquina superior izquierda del elemento
		 * Restar 30 unidades ajusta la posición vertical del clic hacia arriba en la
		 * pantalla en este caso para alcanzar el boton de la HC
		 */
		int y = punto.getY() - 30;

		// Ejecutar un comando ADB para simular un clic en las coordenadas (x, y)
		Runtime.getRuntime().exec("adb -s 22165523600822 shell input tap " + x + " " + y);
		Thread.sleep(2000);
		// au.click(CEDIS_btn_herramientaCompras);

		au.click(CEDIS_btn_Cometa_TT); // Dar clic en el boton Cometa
	}

}