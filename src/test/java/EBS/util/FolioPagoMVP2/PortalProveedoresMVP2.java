package EBS.util.FolioPagoMVP2;

import java.io.File;
import java.time.Duration;
import java.time.Instant;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import EBS.util.GlobalVariables;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCION: Clase que incluye los metodos para validaciones en la pantalla
 * de gestion de porveedores
 *
 * @date 01/Febrero/2025
 */

public class PortalProveedoresMVP2 {

	private static SeleniumUtil u;
	private WebDriverWait wait;
	private static TestCase testCase;

	// INICIO DE SESION
	private By usuarioInput = By.xpath("//div//input[@id='email']");
	private By contrasenaInput = By.xpath("//div//input[@id='password']");
	private By botonInicio = By.xpath("//div//button[@id='login']");

	private By mensajeuno = By.xpath("//span[text()= 'Archivo cargado exitosamente y en proceso de validacion']");
	private By mensajeerrorrfc = By
			.xpath("//span[text()= 'El folio de pago o RFC en la fila: 2 es incorrecto favor de revisarlo.']");
	private By mensajecampoSerie = By
			.xpath("//span[text()= 'El campo SERIE debe tener de 1 a 25 caracteres: 2 del archivo.']");
	private By mensajeUUIDExcedente = By.xpath("//*[contains(text(),'campo UUID Relacionado excede')]");

	// INICIO PORTAL APLICACIONES
	private By gestionProveedores = By
			.xpath("//div//a[@class='text-decoration-none card_portal ng-tns-c1818153300-0']");
	private By Finanzas = By.linkText("Finanzas");
	private By CargaArchivo = By.linkText("Carga archivo relacional");
	private By botonActualizar = By.xpath("//div//input[@id='form:verRefresh']");
	private By mensajeValidacion = By.xpath("//span[@id='form:resultado']");
	private By descargarErrores = By.xpath("//td//a[text()='Descargar Errores']");
	// private By uploadInput = By.name("frmCargaAccount:uploader_input");
	private By uploadInput = By.xpath("//table//input[@type='file']"); // Input para poder seleccionar el archivo cuando
																		// se ingresa por acces control
	@FindBy(xpath = "//input[@type='file']")
	public WebElement uploadInputElement;
	private By enviarBtn = By.xpath("//*[@id='form:chargeBtn']"); // Boton para poder enviar el archivo

	public PortalProveedoresMVP2(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}

	/**
	 * Método para ingresar al portal proveedores
	 * 
	 * @author ricardor4@hexaware.com
	 * @param usuario    : obtiene usuario para portal de proveedores
	 * @param contrasena : obtiene la contrasena de el usuario para portal de
	 *                   proveedores
	 * 
	 * @return returna valor boleano para validacion en script principal
	 * @date 10/Marzo/2025
	 * 
	 */
	public boolean loginPortalProveedores(String usuario, String contrasena) {

		// Navegar a la URL del portal
		u.get(GlobalVariables.URL_PORTAL_PROVEEDORES_QA);
		u.waitForLoadPage();
		u.highLight(usuarioInput);
		u.sendKeys(usuarioInput, usuario);
		u.hardWait(3);
		u.highLight(contrasenaInput);
		u.sendKeys(contrasenaInput, contrasena);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(" ");
		u.highLight(botonInicio);
		u.click(botonInicio);
		u.waitForLoadPage();
		return true;

	}

	/**
	 * Método para acceder a gestion de proveedores
	 * 
	 * @author ricardor4@hexaware.com
	 * @return devuelve valor boleano para validacion en script principal
	 * @date 10/Marzo/2025
	 * 
	 */
	public boolean gestionDeProveedores() {

		u.hardWait(3);
		u.highLight(gestionProveedores);
		testCase.addScreenShotCurrentStep(" ");
		u.click(gestionProveedores);
		u.waitForLoadPage();
		return true;

	}

	/**
	 * Método para acceder a menu finanzas
	 * 
	 * @author ricardor4@hexaware.com
	 * @return devuelve valor boleano para validacion en script principal
	 * @date 10/Marzo/2025
	 */
	public boolean finanzas() {

		u.highLight(Finanzas);
		u.mouseOver(Finanzas);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(3);
		return true;

	}

	/**
	 * Método para dar click a la pantalla de archivo relacional
	 * 
	 * @author ricardor4@hexaware.com
	 * @return devuelve valor boleano para validacion en script principal
	 * @date 10/Marzo/2025
	 */
	public boolean linkAchivoRelacional() {

		u.highLight(CargaArchivo);
		u.click(CargaArchivo);
		u.waitForLoadPage();
		u.hardWait(5);
		testCase.addScreenShotCurrentStep(" ");
		return true;

	}

	/**
	 * Método para cargar archivos relacionales
	 * 
	 * @author ricardor4@hexaware.com
	 * @param nombreArchivo toma el valor de el nombre del archivo
	 * @param pathCSV       toma el valor de el path donde esta el csv
	 * @return devuelve el tiempo en segundos que tarda en subir el archivo el
	 *         concurrente
	 * @date 10/Marzo/2025
	 */
	public long subirArchivoRelacional(String nombreArchivo, String pathCSV) {

		String currentProject = System.getProperty("user.dir"); // Obtiene la ruta del Proyecto actual
		// Se junta la ruta del proyecto,la ubicacion de resources y el nombre del
		// archivo
		String srcFilePath = currentProject + "\\" + pathCSV + "\\" + nombreArchivo;
		System.out.println(srcFilePath);
		u.getDriver().switchTo().frame(0);
		u.highLight(uploadInput);
		u.sendKeys(uploadInput, srcFilePath);
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(4);
		u.highLight(enviarBtn);
		u.click(enviarBtn);
		u.hardWait(4);

		// tiempo que se tarda en procesar el archivo

		// Captura el tiempo inicial
		long startTime = System.currentTimeMillis();

		// Espera hasta que el elemento sea visible (puedes ajustar el tiempo máximo de
		// espera)
		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(70)); // 30 segundos de espera máxima
		WebElement elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(mensajeuno));

		// Captura el tiempo final
		long endTime = System.currentTimeMillis();

		// Calcula el tiempo en segundos
		long tiempoEnSegundos = (endTime - startTime) / 1000;

		// Imprime el tiempo en pantalla
		System.out.println("El elemento tardó " + tiempoEnSegundos + " segundos en aparecer.");
		testCase.addScreenShotCurrentStep(" ");
		return tiempoEnSegundos;

	}

	/**
	 * Método para cargar archivos relacionales
	 * 
	 * @author ricardor4@hexaware.com
	 * @return devuelve valor boleano para validacion en script principal
	 * @param nombre archivo : toma el valor de el nombre de el archivo
	 * @date 10/Marzo/2025
	 */
	public boolean subirArchivoRelacional2(String nombreArchivo) {

		String currentProject = System.getProperty("user.dir"); // Obtiene la ruta del Proyecto actual
		// Se junta la ruta del proyecto,la ubicacion de resources y el nombre del
		// archivo
		String srcFilePath = currentProject + "\\Config\\Input\\recursos\\AchivosRelacionales\\" + nombreArchivo;
		System.out.println(srcFilePath);
		u.getDriver().switchTo().frame(0);
		u.highLight(uploadInput);
		u.sendKeys(uploadInput, srcFilePath);
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(4);
		u.highLight(enviarBtn);
		u.click(enviarBtn);
		u.hardWait(4);
		return true;

	}

	/**
	 * Método para validar mensaje de error en el RFC para el portal de proveedores
	 * 
	 * @author ricardor4@hexaware.com
	 * @return devuelve valor boleano para validacion en script principal
	 * @date 10/Marzo/2025
	 */
	public boolean validarErrorRfc() {

		u.hardWait(10);
		if (u.isDisplayed(mensajeerrorrfc) != null) {
			testCase.addScreenShotCurrentStep(" ");
		}
		u.hardWait(4);
		return true;

	}

	/**
	 * Método para revisar error camposerie
	 * 
	 * @author ricardor4@hexaware.com
	 * @return devuelve valor boleano para validacion en script principal
	 * @date 10/Marzo/2025
	 */
	public boolean validarErrorCampoSerie() {

		u.hardWait(10);
		if (u.isDisplayed(mensajecampoSerie) != null) {
			testCase.addScreenShotCurrentStep(" ");
		}
		u.hardWait(4);
		return true;

	}

	/**
	 * Método para revisar error UUID EXCEDE LA CANTIDAD PERMITIDA
	 * 
	 * @author ricardor4@hexaware.com
	 * @return devuelve valor boleano para validacion en script principal
	 * @date 10/Marzo/2025
	 */
	public boolean mensajeUUIDExcedente() {

		u.hardWait(10);
		if (u.isDisplayed(mensajeUUIDExcedente) != null) {
			testCase.addScreenShotCurrentStep(" ");
		}
		u.hardWait(4);
		return true;

	}

	/**
	 * Metodo para dar click en boton de actualizar archivo en portal de proveedores
	 * 
	 * @author ricardor4@hexaware.com
	 * @date 10/Marzo/2025
	 */
	public void clickActualizarArchivo() {

		if (u.isDisplayed(mensajeuno) != null) {
			u.hardWait(15);
			u.click(botonActualizar);
			u.waitForLoadPage();
			u.scroll();
			u.hardWait(4);
			testCase.addScreenShotCurrentStep(" ");
		}
	}

	/**
	 * Metodo que valida mensaje de error de tipo relacion en portal de proveedores
	 * 
	 * @author ricardor4@hexaware.com
	 * @return devuelve valor boleano para validacion en script principal
	 * @date 10/Marzo/2025
	 */
	public boolean validarMesajeErrorTipoRelacion(String nombreArchivo) {

		u.hardWait(5);
		By etiquetaArchivo = By.xpath("//div//span[text()=" + "'" + nombreArchivo + " - Rechazado']");
		if (u.isDisplayed(etiquetaArchivo) != null) {
			u.hardWait(10);
			u.click(descargarErrores);
			u.hardWait(10);
			return true;
		}
		return false;

	}

	/**
	 * Metodo que valida mensaje correcto en portal de proveedores
	 * 
	 * @author ricardor4@hexaware.com
	 * @return devuelve valor boleano para validacion en script principal
	 * @date 10/Marzo/2025
	 */
	public boolean validarMesajeCorrectoArchivo() {

		String validacion = "";
		boolean a = false;

		if (u.getText(mensajeValidacion).toLowerCase().contains(validacion.toLowerCase())) {

			a = true;

		}
		return true;
	}

	public void cargarArchivoRelacional(String nombreArchivo, String pathCSV) {
        String currentProject = System.getProperty("user.dir"); // Obtiene la ruta del Proyecto actual

        // Imprime informaciï¿½n para depuraciï¿½n
        System.out.println("Dentro de mï¿½todo ....");
        System.out.println("Nombre archivo: " + nombreArchivo);
        System.out.println("Path csv: " + pathCSV);
        System.out.println("Current project: " + currentProject);

        // Forma la ruta del archivo correctamente
        String srcFilePath = currentProject + File.separator + pathCSV + File.separator + nombreArchivo;
        System.out.println("Path de archivo en mï¿½todo: "+srcFilePath);

        // Lï¿½gica de Selenium
        u.getDriver().switchTo().frame(0);
        u.highLight(uploadInput);
        u.sendKeys(uploadInput, srcFilePath);
        testCase.addScreenShotCurrentStep(" ");
        u.hardWait(4);
        u.highLight(enviarBtn);
        u.click(enviarBtn);
        u.hardWait(4);
    }

	public boolean validarMensajeError2(String nombreArchivo) {
	    Instant startTime = Instant.now(); // Inicia el temporizador
	    System.out.println("Iniciando el método validarMensajeError...");
	    System.out.println("Nombre del archivo recibido: " + nombreArchivo);

	    try {
	        // Espera inicial
	        System.out.println("Esperando 5 segundos antes de verificar el mensaje...");
	        u.hardWait(5);

	        // Localizador dinámico del mensaje de error
	        By etiquetaArchivo = By.xpath("//div//span[text()=" + "'" + nombreArchivo + " - Rechazado']");
	        System.out.println("Localizador construido: " + etiquetaArchivo.toString());

	        // Verificar si el mensaje está presente
	        System.out.println("Verificando si el mensaje de error está presente...");
	        if (u.isDisplayed(etiquetaArchivo) != null) {
	            System.out.println("Mensaje de error encontrado. Tiempo transcurrido: " +
	                Duration.between(startTime, Instant.now()).toSeconds() + " segundos");

	            // Captura de pantalla
	            System.out.println("Capturando pantalla del paso actual...");
	            testCase.addScreenShotCurrentStep(" ");

	            // Espera antes de descargar errores
	            System.out.println("Esperando 10 segundos antes de hacer clic en 'Descargar errores'...");
	            u.hardWait(10);

	            // Hacer clic en el botón de descarga
	            System.out.println("Haciendo clic en el botón 'Descargar errores'...");
	            u.click(descargarErrores);

	            // Espera adicional después del clic
	            System.out.println("Esperando 10 segundos después de descargar errores...");
	            u.hardWait(10);

	            // Retornar verdadero si todo salió bien
	            System.out.println("Validación completada exitosamente.");
	            return true;
	        } else {
	            System.out.println("Mensaje de error no encontrado. Retornando 'false'.");
	        }
	    } catch (Exception e) {
	        // Manejo de excepciones
	        System.out.println("Error durante la validación del mensaje de error.");
	        System.out.println("Detalles del error: " + e.getMessage());
	    } finally {
	        // Mostrar el tiempo total transcurrido
	        System.out.println("Tiempo total transcurrido: " +
	            Duration.between(startTime, Instant.now()).toSeconds() + " segundos");
	    }

	    // Retornar falso si no se encontró el mensaje
	    return false;
	}
}
