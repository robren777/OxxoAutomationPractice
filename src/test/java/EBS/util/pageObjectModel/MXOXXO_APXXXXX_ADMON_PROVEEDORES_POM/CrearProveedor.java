package EBS.util.pageObjectModel.MXOXXO_APXXXXX_ADMON_PROVEEDORES_POM;

import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class CrearProveedor {
	SeleniumUtil u;
	TestCase testCase;
	private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);

	/**
	 * @param u        Instancia de SeleniumUtil
	 * @param testCase Instancia de TestCase
	 * @author edwin.ramirez@neoris.com
	 */
	public CrearProveedor(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}

	By nombreOrganizacion = By.xpath("//input[@id='organization_name']");
	By persona = By.xpath(
			"//table[@id='supplierDff']/tbody/tr/td/span/span[text()='*']/parent::span/parent::td/parent::tr/td[3]/div/span/input");
	By numProveedor = By.xpath("//input[@id='suppNum']");
	By personaObligatorio = By.xpath("//table[@id='supplierDff']/tbody/tr/td/span/span[text()='*']");
	By btnAplicar = By.xpath("//button[@id='applyBtn']");
	By tipoProveedor = By.xpath("//select[@id='SuppType']");
	By cajaDeBusqueda = By.xpath("//input[@id='supplierDff0']");
	By lupaBusqueda = By.xpath("//tbody/tr[1]/td[3]/div/span/a/img");
	By newFrame = By.xpath("//frame[@title='Content']");
	By btnIr = By.xpath("//button[normalize-space()='Ir']");
	By seleccionRapida = By.xpath("//tbody/tr[2]/td[2]/a[1]/img[1]");
	By idContribuyente = By.xpath(" //input[@id='taxPayId']");
	By mensaje = By.xpath("//tbody/tr/td[3]/table/tbody/tr/td/h1");
	By fechaInactividad = By.xpath("//input[@id= 'InactiveOn']");
	By btnGrabar = By.xpath("//button[@id='btnSave']");
	By btnConfirmar = By.xpath("//button[@id='btnConfirm']");
	
	/**
	 * Envia el texto al campo Nombre de Organizacion
	 * 
	 * @param nombreDeOrganizacion un String con el texto para Nombre de
	 *                             Organizacion
	 * @author edwin.ramirez@neoris.com
	 */
	public void sendNombreOrganizacion(String nombreDeOrganizacion) {
		u.highLight(nombreOrganizacion);
		u.sendKeys(nombreOrganizacion, nombreDeOrganizacion);
	}

	/**
	 * Envia el texto al campo Persona
	 * 
	 * @param tipoDePersona un String con el texto para Persona
	 * @author edwin.ramirez@neoris.com
	 */
	public void sendPersona(String tipoDePersona) {
		u.highLight(persona);
		u.sendKeys(persona, tipoDePersona);
	}

	/**
	 * Envia el texto al campo Numero de Proveedor
	 * 
	 * @param numDeProveedor
	 * @author edwin.ramirez@neoris.com
	 */
	public void sendNumProveedore(String numDeProveedor) {
		u.highLight(numProveedor);
		u.sendKeys(numProveedor, numDeProveedor);
	}

	/**
	 * Da click en el boton Aplicar
	 * 
	 * 
	 * @author edwin.ramirez@neoris.com
	 */
	public void clickAplicar() {
		u.highLight(btnAplicar);
		u.click(btnAplicar);
		u.waitForLoadPage();
	}

	/**
	 * Selecciona la opción del dropdown (tipo de Proveedor)
	 * 
	 * @author JessicaR@hexaware.com
	 * @param x es el valor de la opción seleccionada
	 */
	public void seleccionarTipoProveedor(String x) {
		try {
			Select tipo = new Select(u.getDriver().findElement(tipoProveedor));
			u.highLight(tipoProveedor);
			tipo.selectByVisibleText(x);
		} catch (Exception e) {
			logger.error("Error" + e);
			System.out.println("Error" + e);
		}

	}

	/**
	 * Se envía el parámetro de caja de búsqueda
	 * 
	 * @author JessicaR@hexaware.com
	 * @return cajaDeBusqueda
	 */
	public By cajaDeBusqueda() {
		u.highLight(cajaDeBusqueda);
		return cajaDeBusqueda;
	}

	/**
	 * Hace un remarcado y da click en la imagen de la lupa
	 * 
	 * @author JessicaR@hexaware.com
	 */
	public void clickLupaBusqueda() {
		u.highLight(lupaBusqueda);
		u.click(lupaBusqueda);
	}

	/**
	 * Se envía el parámetro del New Frame
	 * 
	 * @author JessicaR@hexaware.com
	 * @return newFrame
	 */
	public By newFrame() {
		return newFrame;
	}

	/**
	 * Hace un remarcado y click en el botón Ir
	 * 
	 * @author JessicaR@hexaware.com
	 */
	public void clickIr() {
		u.highLight(btnIr);
		u.click(btnIr);
	}

	/**
	 * Hace un remarcado y click en la opción de selección rápida
	 * 
	 * @author JessicaR@hexaware.com
	 */
	public void clickSeleccionRapida() {
		u.highLight(seleccionRapida);
		u.click(seleccionRapida);
	}

	/**
	 * Envia el texto al campo ID Contribuyente
	 * 
	 * @author JessicaR@hexaware.com
	 * @param idDeContribuyente un String con el texto para el ID del Contribuyente
	 */
	public void sendIDContribuyente(String idDeContribuyente) {
		u.highLight(idContribuyente);
		u.sendKeys(idContribuyente, idDeContribuyente);
	}
	
	/**
	 * Se toma el mensaje de advertencia
	 * 
	 * @author JessicaR@hexaware.com
	 * @return advertencia el mensaje de advertencia
	 */
	public String getTextMensaje() {

		u.highLight(mensaje);
		return u.getText(mensaje);
	}
	
	public void sendFechaInactividad(String fechaIna) {
		u.highLight(fechaInactividad);
		u.sendKeys(fechaInactividad, fechaIna);
	}
	
	public By boton(String Boton) {
		switch (Boton.toLowerCase(Locale.ROOT)) {
		case "grabar":
			return btnGrabar;
		case "confirmar":
			return btnConfirmar;
		}

		return null;
	}
	
	public void clickBoton(String boton) {
		try {
			By obj = boton(boton);
			u.scroll();
			Actions action = new Actions(u.getDriver());
			action.moveToElement(u.getDriver().findElement(obj));
			action.perform();
			u.hardWait(1);
			u.highLight(obj);
			u.click(obj);
		} catch (Exception e) {
			logger.error("Error Carpeta no encontrada " + e);
			System.out.println("Error Carpeta no encontrada " + e);
		}
	}

}
