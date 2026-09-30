package EBS.util.pageObjectModel;

import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.sikuli.hotkey.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class EBSNuevoSistemaICM {

	SeleniumUtil u;
	TestCase testCase;
	private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);

	/**
	 * @param u        Instancia de SeleniumUtil
	 * @param testCase Instancia de TestCase
	 * @author JessicaR@hexaware.com
	 */
	public EBSNuevoSistemaICM(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}

	By nombreComisionista = By.xpath("//input[@id='ComisionistaNombre']");
	By btnIr = By.xpath("//button[@id='btnGo']");
	By checkDocumentos = By.xpath("//input[@id='tblCrtlGenDoctosReversa:GeneraDocto:0']");
	By btnGenerarDocumentos = By.xpath("//button[@id='btnGenerarDocts']");
	By btnPaginaInicial = By.xpath("//div/img[@title='P�gina Inicial']");
	By btnCrear = By.xpath("//button[@id='btnCrear']");
	By rfc = By.xpath("//tbody/tr[1]/td[1]/div/span/input[@id='advTblGenAnticipos:Rfc:0']");
	By btnAplicar = By.xpath("//button[@id='btnAplicar']");

	public void sendNombreComisionista(String nombreDeComisionista) {
		u.highLight(nombreComisionista);
		u.sendKeys(nombreComisionista, nombreDeComisionista);
	}

	public void clickCheckBoxDocumentos() {
		u.highLight(checkDocumentos);
		u.click(checkDocumentos);
	}

	public By boton(String Boton) {
		switch (Boton.toLowerCase(Locale.ROOT)) {
		case "ir":
			return btnIr;
		case "generar documentos":
			return btnGenerarDocumentos;
		case "pagina inicial":
			return btnPaginaInicial;
		case "crear":
			return btnCrear;
		case "aplicar":
			return btnAplicar;
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

	public void sendRFC(String rfcLider) {
		u.highLight(rfc);
		u.sendKeys(rfc, rfcLider);
	}
	
	public void typeAndEnter(String text) {
		u.highLight(rfc);
		u.sendKeys(rfc, text);
		u.getDriver().findElement(rfc).sendKeys(Keys.TAB);
	
	}

}
