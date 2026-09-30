package EBS.util.pageObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

/**
 * Clase con los locators y acciones de la pagina de "EBS inicio"
 *
 * @author JesusH@hexaware.com
 * @version 1.0
 * @since 5/23/2022
 */
public class EBSCargarArchivos {

	SeleniumUtil u;
	TestCase testCase;

	// Generacion del constructor

	/**
	 * @param u        Instancia de SeleniumUtil
	 * @param testCase Instancia de TestCase
	 * @author JesusH@hexaware.com
	 */
	public EBSCargarArchivos(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		PageFactory.initElements(u.getDriver(), this);
	}

	/*
	 * Responsabilidades
	 */

	By MXOXXOGLXXXXXSuperUsuario = By.xpath("//a[normalize-space()='MXOXXO-GLXXXXX-Super Usuario']");
	By MXOXXOFNDXXXXXUsuarioCargaMasiva = By.xpath("//a[normalize-space()='MXOXXO-FNDXXXXX-Usuario carga masiva']");
	By MXOXXOEAM10VCZEQUIPOMANTO = By.xpath("//div[normalize-space()='MXOXXO-EAM10VCZ-EQUIPO-MANTO']");

	/*
	 * Carpetas
	 */

	/*
	 * Formularios
	 */

	/*
	 * Applets
	 */

	/*
	 * Elementos generales (botones, cajas de texto, etc)
	 */
	By celdasinarticulos = By.xpath("//td[normalize-space()='No search conducted.']");
	By cargaarchivosnombre = By.xpath("//h1[@type='OraHeader']");
	By cargaarchivoscon = By.xpath("//div[normalize-space()='Carga de Archivos']");
	By cargaaddenda = By.xpath(
			"//div//li//ul//li[@id= 'LIPedidos_de_TrabajoPedidos_de_TrabajoCerrar_Pedidos_de_TrabajoMateriales_y_Mano_de_Obra_-_Fase_2']");
	By seleccionaraddenda = By.xpath("//input[@id='MessageFileUpload_oafileUpload']");
	By aplicaraddenda = By.xpath("//button[@id='Submit']");
	By home = By.xpath("//img[@title='Página Inicial']");
	By cajadebusqueda = By.xpath("//input[@id='lovProceso']");
	By cajadebusqueda1 = By.xpath("//input[@id='msgFileUpload_oafileUpload']");
	By lupabusqueda = By.xpath("//table[@id='CargaArchivosRN']//input[@id=\"lovProceso\"]/following-sibling::a");
	By ir = By.xpath("//button[normalize-space()='Ir']");
	By newFrame = By.xpath("//frame[@title='Content']");
	By creacionresponsabilidad = By.xpath("//tbody/tr[2]/td[2]/a[1]/img[1]");
	By inactivacionresponsabilidad = By.xpath("//img[@title='Selección Rápida']");
	By botoncargar = By.xpath("//button[@id='btnSubmit']");
	By proceso = By.xpath("//input[@id='lovProceso']");
	By nombremigracion = By.xpath("//span[@id='N1:msgConcurrente:0']");
	By tipoproceso = By.xpath("//input[@title='Término de Búsqueda']");
	By segundaceldatablacargararchivos = By.xpath("//tr[@class='xfn']//td[2]");

	/**
	 * envio parametro de caja de busqueda
	 * 
	 * @author ricardo.palacios@neoris.com
	 * @return By
	 */
	public String getTextCeldasSinArticulos() {
		u.highLight(celdasinarticulos);
		return u.getText(celdasinarticulos);
	}

	/**
	 * envio parametro de caja de busqueda
	 * 
	 * @author ricardo.palacios@neoris.com
	 * @return By
	 */
	public By cajadebusqueda() {
		return cajadebusqueda;
	}

	/**
	 * click en boton cargar
	 * 
	 * @author ricardo.palacios@neoris.com
	 */
	public void clickCargar() {

		u.highLight(botoncargar);
		u.click(botoncargar);
		u.waitForLoadPage();

	}

	/**
	 * envio parametro de frame
	 * 
	 * @author ricardo.palacios@neoris.com
	 * @return By
	 */
	public By newframe() {
		return newFrame;
	}

	/**
	 * Hace un remarcado y click en boton ir
	 *
	 * @author ricardo.palacios@neoris.com
	 */
	public void clickIr() {
		u.highLight(ir);
		u.click(ir);
	}

	/**
	 * Hace un remarcado y click en la lupa de busqueda
	 *
	 * @author ricardo.palacios@neoris.com
	 */
	public void clicklupabusqueda() {
		u.highLight(lupabusqueda);
		u.click(lupabusqueda);
	}

	/**
	 * Obtiene el texto del elemento
	 * 
	 * @return cargaarchivosnombre
	 * @author ricardo.palacios@neoris.com
	 */
	public String getTipoText() {
		u.highLight(cargaarchivosnombre);
		return u.getText(cargaarchivosnombre);
	}

	/**
	 * Obtiene el texto del elemento
	 * 
	 * @return nombremigracion
	 * @author ricardo.palacios@neoris.com
	 */
	public String getTextNombreMigracion() {
		u.highLight(nombremigracion);
		return u.getText(nombremigracion);
	}

	/**
	 * Obtiene el texto del elemento de la segunda celda de cargar archivos
	 * 
	 * @return segundaceldatablacargararchivos
	 * @author ricardo.palacios@neoris.com
	 */
	public String getTextSegundaCeldaTablaCargarArchivos() {
		u.highLight(segundaceldatablacargararchivos);
		return u.getText(segundaceldatablacargararchivos);
	}

	/**
	 * Hace un remarcado y click en XXFC - Carga de Gastos Addenda
	 *
	 * @author ricardo.palacios@neoris.com
	 */
	public void cargaaddenda() {
		u.highLight(cargaaddenda);
		u.click(cargaaddenda);
		u.waitForLoadPage();
	}

	/**
	 * Hace un remarcado y click en creacion de responsabilidad
	 *
	 * @author ricardo.palacios@neoris.com
	 */
	public void clickCreacionResponsabilidad() {
		u.highLight(creacionresponsabilidad);
		u.click(creacionresponsabilidad);
	}

	/**
	 * Hace un remarcado y click en inactivacion de responsabilidad
	 *
	 * @author ricardo.palacios@neoris.com
	 */
	public void clickInactivacionResponsabilidad() {
		u.highLight(inactivacionresponsabilidad);
		u.click(inactivacionresponsabilidad);
	}

	/**
	 * Hace un remarcado y click en XXFC - Carga de Gastos Addenda
	 *
	 * @author ricardo.palacios@neoris.com
	 */
	public void aplicaraddenda() {
		u.highLight(aplicaraddenda);
		u.click(aplicaraddenda);
		u.waitForLoadPage();
	}

	/**
	 * Hace un remarcado y click para seleccionar un archivo desde Carga de Gastos
	 * Addenda
	 *
	 * @param ruta
	 * @author ricardo.palacios@neoris.com
	 */
	public void seleccionaraddenda(String ruta) {
		WebElement uploadElement = u.getDriver().findElement(cajadebusqueda1);
		// enter the file path onto the file-selection input field
		uploadElement.sendKeys(ruta);
	}

	/**
	 * Hace un remarcado y click para seleccionar un proceso
	 *
	 * @param nombre
	 * @author ricardo.palacios@neoris.com
	 */
	public void seleccionarproceso(String nombre) {
		WebElement uploadElement = u.getDriver().findElement(proceso);
		// enter the file path onto the file-selection input field
		uploadElement.sendKeys(nombre);
	}

	/**
	 * Hace un remarcado y click en la carpeta carga de archivos
	 *
	 * @author JesusH@hexaware.com
	 */
	public void clickcargaarchivosr() {
		u.highLight(cargaarchivoscon);
		u.click(cargaarchivoscon);
	}

	/**
	 * Envio parametro numero de tipo de proceso
	 * 
	 * @author ricardo.palacios@neoris.com
	 * @return By
	 */
	public By tipoProceso() {
		return tipoproceso;
	}

	/**
	 * Hace un remarcado y click en ventana home en la paguina ebs
	 *
	 * @author ricardo.palacios@neoris.com
	 */
	public void home() {
		u.highLight(home);
		u.click(home);
	}
}
