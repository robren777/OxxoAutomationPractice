package Mobile.cedis.om;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import exceptions.FrameworkException;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;



public class A03_Menus_y_submenus {
	private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;

	/**
	 * //Opcion Navegacion private By CEDIS_btn_navegacion =
	 * By.xpath("//android.widget.ImageButton[@content-desc=\"Open navigation
	 * drawer\"]"); boolean btn_navegacion =
	 * au.driver.findElement(CEDIS_btn_navegacion).isDisplayed(); //Gente private By
	 * Cedis_btn_Gente =
	 * By.xpath("(//android.widget.FrameLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/cvMenu\"])[1]");
	 * boolean btn_Gente = au.driver.findElement(Cedis_btn_Gente).isDisplayed();
	 * 
	 * //Control private By CEDIS_btn_control =
	 * By.xpath("(//android.widget.FrameLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/cvMenu\"])[2]");
	 * boolean control = au.driver.findElement(CEDIS_btn_control).isDisplayed();
	 * 
	 * //Preinventarios private By CEDIS_btn_preinventarios =
	 * By.xpath("(//android.widget.FrameLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/cvMenu\"])[1]");
	 * boolean btn_preinventarios =
	 * au.driver.findElement(CEDIS_btn_preinventarios).isDisplayed();
	 * //Preinventarios Inteligentes private By CEDIS_btn_preinvenInte =
	 * By.xpath("(//android.widget.FrameLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/cvMenu\"])[2]");
	 * boolean btn_preinvenInte =
	 * au.driver.findElement(CEDIS_btn_preinvenInte).isDisplayed();
	 */

	// Boton para desplegar el Menú lateral tipo Hamburguesa
	private By CEDIS_Boton_MenuHamburguesa = By
			.xpath("//android.widget.ImageButton[@content-desc=\"Open navigation drawer\"]");

	// Control
	private By CEDIS_Leyenda_Control = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvMenuName\" and @text=\"Control\"]");

	// Experiencia del Cliente (CX)\
	private By CEDIS_Leyenda_ExperienciaDelCliente = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvMenuName\" and @text=\"Experiencia del Cliente (CX)\"]");

	// Gente
	private By CEDIS_Leyenda_Gente = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvMenuName\" and @text=\"Gente\"]");

	// Excelencia Operativa
	private By CEDIS_Leyenda_ExcelenciaOperativa = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvMenuName\" and @text=\"Excelencia Operativa\"]");

	// Excelencia Operativa
	private By CEDIS_btn_exceOperativa = By.xpath(
			"(//android.widget.FrameLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/cvMenu\"])[3]");

	// SECCION MENU LATERAL
	// Leyenda del CR PLAZA
	private By CEDIS_Leyenda_CRPlaza = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvCrPlaza\"]");
	// Leyenda del CR TIENDA
	private By CEDIS_Leyenda_CRTienda = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvCrTienda\"]");
	// Boton Pantalla de Diagnostico
	private By CEDIS_Leyenda_PantallaDeDiagnostico = By.xpath(
			"//android.widget.CheckedTextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/design_menu_item_text\" and @text=\"Pantalla de diagnóstico\"]");
	// Boton Catalogo
	private By CEDIS_Leyenda_Catalogo = By.xpath(
			"//android.widget.CheckedTextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/design_menu_item_text\" and @text=\"Catálogos\"]");
	// Boton Seleccion de Impresoras
	private By CEDIS_Leyenda_SeleccionDeImpresoras = By.xpath(
			"//android.widget.CheckedTextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/design_menu_item_text\" and @text=\"Selección de impresoras\"]");
	// Boton Salir
	private By CEDIS_Leyenda_Salir = By.xpath("//android.widget.TextView[@text=\"Salir\"]");

	// Boton Salir
	private By CEDIS_Leyenda_Salir2 = By.xpath("//android.widget.TextView[@text=\"Salir2\"]");

	// boolean btn_execOperativa =
	// au.driver.findElement(CEDIS_btn_exceOperativa).isDisplayed();
	private By corte_Z = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvMenuName\" and @text=\"Corte Z y Control Fajillas\"]");

	/**
	 * //Etiquteo -Submenu de//Excelencia Operativa private By CEDIS_btn_etiqueteo =
	 * By.xpath("(//android.widget.FrameLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/cvMenu\"])[1]");
	 * boolean btn_etiqueteo =
	 * au.driver.findElement(CEDIS_btn_etiqueteo).isDisplayed(); //Portales -Submenu
	 * de//Excelencia Operativa private By CEDIS_btn_portales =
	 * By.xpath("(//android.widget.FrameLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/cvMenu\"])[2]");
	 * boolean btn_portales =
	 * au.driver.findElement(CEDIS_btn_portales).isDisplayed(); //Corte Z y Control
	 * Fajillas -Submenu de//Excelencia Operativa //private By CEDIS_btn_corteZ =
	 * pendiente que que apareca en pantalla
	 */

	// Herramienta de Compra -Submenu de//Excelencia Operativa
	private By CEDIS_txt_herramientaCompra = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvMenuName\" and @text=\"Herramienta de Compra\"]");
	private By CEDIS_btn_herramientaCompras = By.xpath(
			"(//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ibImage\"])[3]");

	// (//android.widget.ImageView[@elementIdd="00000000-0000-0620-ffff-ffff00000130"])
	// boolean btn_herramientaCompras =
	// au.driver.findElement(CEDIS_btn_herramientaCompras).isDisplayed();
	

	/**
	 * //Movilidad -Submenu de Herramienta de Compra private By CEDIS_btn_movilidad
	 * =
	 * By.xpath("(//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ibImage\"
	 * and @elementId=\"00000000-0000-04ca-ffff-ffff00000215\"])[3]"); boolean
	 * btn_movilidad = au.driver.findElement(CEDIS_btn_movilidad).isDisplayed();
	 */

	// Cometa -Submenu de Herramienta de Compra
	// private By CEDIS_btn_Cometa =
	// By.xpath("(//android.widget.FrameLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/cvMenu\"])[2]");
	private By CEDIS_btn_Cometa = By.xpath(
			"(//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ibImage\"])[2]");
	// boolean btn_Cometa = au.driver.findElement(CEDIS_btn_Cometa).isDisplayed();

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
	public A03_Menus_y_submenus(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
		this.testCase = testCase;
		this.au = au;
		this.baseExecution = baseExecution;
	}

	public void logeoInicioHerramientas() throws IOException, InterruptedException {
		/**
		 * Método - Realizar el logeo de la aplicación llegando hasta la pantalla del
		 * menu de Herraminetas de CEDIS Menu de Herramientas de Compras de CEDIS
		 * submenu de Herramienta de Compras borrando cache Validar Pantalla Principal
		 * de Recepción de Pedido.
		 * 
		 * @author Juan Ibarra
		 * @param N/A
		 * @return N/A
		 */

		au.click(CEDIS_btn_exceOperativa); // Dar clic en el boton Excelencia Operativa
		Thread.sleep(800);

		WebElement elemento = au.driver.findElement(CEDIS_txt_herramientaCompra);
		// Obtener la posición del elemento en la pantalla (esquina superior izquierda)
		// y guardarla en la variable 'punto' de tipo Point
		Point punto = elemento.getLocation();
		// Obtener el tamaño del elemento (ancho y altura) y guardarlo en la variable
		// 'dimension' de tipo Dimension
		Dimension dimension = elemento.getSize();

		/*
		 * Calcular la coordenada X del centro del elemento 'punto.getX()' da la
		 * posición X de la esquina superior izquierda del elemento
		 * 'dimension.getWidth() / 2' calcula la mitad del ancho del elemento La suma de
		 * estos dos valores da la coordenada X del centro del elemento
		 */
		int x = punto.getX() + (dimension.getWidth() / 2);

		/*
		 * Calcular la coordenada Y para realizar el clic 'punto.getY()' da la posición
		 * Y de la esquina superior izquierda del elemento Restar 30 unidades ajusta la
		 * posición vertical del clic hacia arriba en la pantalla en este caso para
		 * alcanzar el boton de la HC
		 */
		int y = punto.getY() - 30;

		// Ejecutar un comando ADB para simular un clic en las coordenadas (x, y)
		Runtime.getRuntime().exec("adb shell input tap " + x + " " + y);
		Thread.sleep(1500);
		// au.click(CEDIS_btn_herramientaCompras);

		au.click(CEDIS_btn_Cometa); // Dar clic en el boton Cometa
	}

	public void SeleccionarObjeto(String ObjetoPantalla) throws IOException, InterruptedException {
		// Funcion para buscar objeto y dar click especifico en la pantalla Creado: Juan
		// Ibarra
		switch (ObjetoPantalla) {
		case "Excelencia":
			au.click(CEDIS_btn_exceOperativa);
			break;
		case "Herramienta":
			WebElement elemento = au.driver.findElement(CEDIS_txt_herramientaCompra);
			Point punto = elemento.getLocation();
			Dimension dimension = elemento.getSize();
			int x = punto.getX() + (dimension.getWidth() / 2);
			int y = punto.getY() - 30;
			Runtime.getRuntime().exec("adb shell input tap " + x + " " + y);
			Thread.sleep(1500);
			break;
		case "Cometa":
			au.click(CEDIS_btn_Cometa);
			break;
		default:
			baseExecution.addStep("Se falla el caso");
			// Se agrega el paso a mostrar en el PDF
			Assert.fail("No se ha encontrado el objeto " + ObjetoPantalla, null);
			// Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria
			// leyenda de null
		}
	}

	/**
	 * Método Validacion de pantalla del menu principal
	 * 
	 * La función valida que los botones estén habilitados en la pantalla del menú principal,
	 * si no lo está, falla la prueba, registra el fallo y toma una captura de pantalla.
	 *
	 * @author Juna Ibarra
	 * @date 
	 * @throws FrameworkException
	 */
	public void ValidacionPantallaMenuPrincipal() throws IOException, InterruptedException {
		Thread.sleep(1000);

		if (au.driver.findElement(CEDIS_Leyenda_Gente).isEnabled() == false) {
			baseExecution.addStep("Se falla el caso");
			Assert.fail("No se ha encontrado el botón: Gente", null);
			testCase.addScreenShotCurrentStep(au, "testImage");
		
		}

		if (au.driver.findElement(CEDIS_Leyenda_Control).isEnabled() == false) {
			baseExecution.addStep("Se falla el caso");
			Assert.fail("No se ha encontrado el botón: Control", null);
			testCase.addScreenShotCurrentStep(au, "testImage");
		}

		if (au.driver.findElement(CEDIS_Leyenda_ExcelenciaOperativa).isEnabled() == false) {
			baseExecution.addStep("Se falla el caso");
			Assert.fail("No se ha encontrado el botón: Excelencia Operativa", null);
			testCase.addScreenShotCurrentStep(au, "testImage");
		}

		if (au.driver.findElement(CEDIS_Leyenda_ExperienciaDelCliente).isEnabled() == false) {
			baseExecution.addStep("Se falla el caso");
			Assert.fail("No se ha encontrado el botón: Experincia del Cliente (CX)", null);
			testCase.addScreenShotCurrentStep(au, "testImage");
		}

		au.click(CEDIS_Boton_MenuHamburguesa);
		Thread.sleep(1000);

		if (au.driver.findElement(CEDIS_Leyenda_CRPlaza).isEnabled() == false) {
			baseExecution.addStep("Se falla el caso");
			Assert.fail("No se ha encontrado la leyenda: CR Plaza", null);
			testCase.addScreenShotCurrentStep(au, "testImage");
		}

		if (au.driver.findElement(CEDIS_Leyenda_CRTienda).isEnabled() == false) {
			baseExecution.addStep("Se falla el caso");
			Assert.fail("No se ha encontrado la leyenda: CR Tienda", null);
			testCase.addScreenShotCurrentStep(au, "testImage");
		}

		if (au.driver.findElement(CEDIS_Leyenda_PantallaDeDiagnostico).isEnabled() == false) {
			baseExecution.addStep("Se falla el caso");
			Assert.fail("No se ha encontrado el botón: Pantalla de Diagnostico", null);
			testCase.addScreenShotCurrentStep(au, "testImage");
		}

		if (au.driver.findElement(CEDIS_Leyenda_Catalogo).isEnabled() == false) {
			baseExecution.addStep("Se falla el caso");
			Assert.fail("No se ha encontrado el botón: Catalogo", null);
			testCase.addScreenShotCurrentStep(au, "testImage");
		}

		if (au.driver.findElement(CEDIS_Leyenda_SeleccionDeImpresoras).isEnabled() == false) {
			baseExecution.addStep("Se falla el caso");
			Assert.fail("No se ha encontrado el botón: Seleccion de Impresoras", null);
			testCase.addScreenShotCurrentStep(au, "testImage");
		}

		if (au.driver.findElement(CEDIS_Leyenda_Salir).isEnabled() == false) {
			baseExecution.addStep("Se falla el caso");
			Assert.fail("No se ha encontrado el botón: Salir", null);
			testCase.addScreenShotCurrentStep(au, "testImage");
		}

		sendKeysUsingKeyEvents(au, "B");

	}
	/**
	 * Método Validacion de pantalla excelecncia operativa
	 * 
	 *El método realiza una única validación: comprueba que el botón o texto "Herramienta de Compra" esté disponible y habilitado en la pantalla de Excelencia Operativa,
	 *si no está, falla el caso de prueba, lo documenta y captura una imagen del error. 
	 *
	 * @author Juna Ibarra
	 * @date 
	 * @throws FrameworkException
	 */

	public void ValidacionPantallaExcelenciaOperativa() throws IOException, InterruptedException {
		Thread.sleep(1000);

		if (au.driver.findElement(CEDIS_txt_herramientaCompra).isEnabled() == false) {
			baseExecution.addStep("Se falla el caso");
			Assert.fail("No se ha encontrado el botón: Herramienta de Compra", null);
			testCase.addScreenShotCurrentStep(au, "testImage");
		}

	}

	/**
	 * Método Validacion de pantalla herramienta de compra
	 * 
	 * Este método automatizado valida que el botón "Cometa" esté disponible y habilitado en la pantalla "Herramienta de Compra", 
	 * si no se encuentra activo, la prueba falla, se documenta el error y se captura una imagen del estado actual.
	 *
	 * @author Juna Ibarra
	 * @date 
	 * @throws FrameworkException
	 */
	public void ValidacionPantallaHerramientaCompra() throws IOException, InterruptedException {
		Thread.sleep(1000);

		if (au.driver.findElement(CEDIS_btn_Cometa).isEnabled() == false) {
			baseExecution.addStep("Se falla el caso");
			Assert.fail("No se ha encontrado el botón: Cometa", null);
			testCase.addScreenShotCurrentStep(au, "testImage");
		}

	}
	/**
	 * Método validacion sendKeysUsingKeyEvents
	 * 
	 * Valida los botones del teclado de el dispositivo android.
	 * 
	 * @author Juna Ibarra
	 * @date 
	 * @throws FrameworkException
	 */
	public static void sendKeysUsingKeyEvents(AppiumUtil au, String text) {
		Map<Character, AndroidKey> keyMap = new HashMap<>();
		keyMap.put('0', AndroidKey.DIGIT_0);
		keyMap.put('1', AndroidKey.DIGIT_1);
		keyMap.put('2', AndroidKey.DIGIT_2);
		keyMap.put('3', AndroidKey.DIGIT_3);
		keyMap.put('4', AndroidKey.DIGIT_4);
		keyMap.put('5', AndroidKey.DIGIT_5);
		keyMap.put('6', AndroidKey.DIGIT_6);
		keyMap.put('7', AndroidKey.DIGIT_7);
		keyMap.put('8', AndroidKey.DIGIT_8);
		keyMap.put('9', AndroidKey.DIGIT_9);
		keyMap.put('B', AndroidKey.BACK);
		keyMap.put('E', AndroidKey.ENTER);

		for (char c : text.toCharArray()) {
			AndroidKey key = keyMap.get(c);
			if (key != null) {
				au.driver.pressKey(new KeyEvent(key));

			}
		}
	}

	/**
	 * Método Validacion tiempo de espera de pantalla de carga
	 * 
	 * Revisa si la pantalla ha terminado de cargar buscando un elemento clave, espera hasta 30 segundos, si el elemento aparece sale del bucle y continúa, }
	 * si no aparece tras los 30 segundos falla el caso de prueba.
	 * 
	 * @author Juna Ibarra
	 * @date 
	 * @throws FrameworkException
	 */
	public void Cargando() throws InterruptedException{  	
    	//Funcion que permite dar un tiempo de espera en la carga de la pantalla	Creado: Juan Ibarra		Fecha Creación: 28-Febrero-2025    	
    	int intentos = 0;    	
    	while (intentos < 31 ) {
    		if(au.driver.findElements(CEDIS_Leyenda_ExcelenciaOperativa).isEmpty()!= true) {
    			System.out.println("La pantalla ya no muestra la barra de carga en la pantalla de Menú principal de Oxxo al día");
    			break;
        	}
    		intentos++;    		
    		try {
    			// Espera el tiempo determinado antes de volver a intentar
                System.out.println("Campo no encontrado. Intentando nuevamente, esperando que deje de cargar la pantalla");
                Thread.sleep(500);                
            } catch (InterruptedException e) {
                System.err.println("Error al esperar entre intentos: " + e.getMessage());               
                System.out.println("La pantalla no muestra la pantalla de Menú principal de Oxxo al día");
    			break;  		    
            }    	
    	if (intentos>30) {
			baseExecution.addStep("Se falla el caso");
			Assert.fail("Se ha superado los 30 segundos de tolerancia en cargar la pantalla de Menú de principal", null);    		  		
		}    	
    	}    	
	}
	
	
}