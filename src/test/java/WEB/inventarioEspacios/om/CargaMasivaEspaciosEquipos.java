package WEB.inventarioEspacios.om;

import java.io.File;
import java.time.Duration;
import org.openqa.selenium.By;

import WEB.om.MetodosExtra;
import config.Constants;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class CargaMasivaEspaciosEquipos {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;
	/*
	 * Localizadores Pantalla Detalle - Tiendas
	 ******************************************************************************************/
	By xmlUsuario = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/p");
    By xmlNegocio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[1]");
    By xmlPerfil = By.xpath("");
  
    By xmlHeader       = By.xpath("//mat-sidenav-content[@id='content-nav']/app-header");
    By xmlMenuEspacio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[2]/div");
    By xmlSbCargaMasivaEspacios = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[4]/a/div/label");
    
    // Pantalla Carga Masiva de Espacios
    By xmlSeleccionarArchivo = By.xpath("//*[@id=\"formFiltroEspacios\"]/div[1]/div/div/input");
    By xmlBtnSubirArchivo = By.xpath("//*[@id=\"formFiltroEspacios\"]/div[2]/div/div/button/span[1]");
    By xmlLblNumero = By.xpath("//*[@id=\"content-nav\"]/app-spaces-load/div/div[2]/table/thead/tr/th[1]");
    By xmlLblArchivo = By.xpath("//*[@id=\"content-nav\"]/app-spaces-load/div/div[2]/table/thead/tr/th[2]");
    By xmlLblFechaIni = By.xpath("//*[@id=\"content-nav\"]/app-spaces-load/div/div[2]/table/thead/tr/th[3]");
    By xmlLblFechaFin = By.xpath("//*[@id=\"content-nav\"]/app-spaces-load/div/div[2]/table/thead/tr/th[4]");
    By xmlLblConteo = By.xpath("//*[@id=\"content-nav\"]/app-spaces-load/div/div[2]/table/thead/tr/th[5]");
    By xmlLblPorcentaje = By.xpath("//*[@id=\"content-nav\"]/app-spaces-load/div/div[2]/table/thead/tr/th[6]");
    By xmlLblEstatus = By.xpath("//*[@id=\"content-nav\"]/app-spaces-load/div/div[2]/table/thead/tr/th[7]");
    
    /*
     * Variables que reciben un parametro
     * *******************************************************************************************/
//    String SrtIdRegla = ("//table/tbody/tr/td[2][contains(text(),'%s')]//following-sibling::td[6]/mat-icon");
//    String SrtTienda = ("//section/span[2]/ul/div/li/mat-checkbox/label/span[2][contains(text(),' %s ')]//preceding-sibling::span");
//    String SrtCantidadEspacio = ("//tbody/tr/td[2][contains(text(),' %s ')]//following-sibling::td/input");
    
	/**
	 * Constructor
	 ******************************************************************************************/
	public CargaMasivaEspaciosEquipos(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);
    }




	/**
	 * Método: Para Dar Click en el menu Espacios-Carga Masiva Espacios/Equipos
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
	public void clickMenuEspaciosCargaMasivaEspacios() {
		
    	m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.highLight(xmlMenuEspacio);
		u.click(xmlMenuEspacio);
		u.highLight(xmlSbCargaMasivaEspacios);
		u.click(xmlSbCargaMasivaEspacios);
		
		u.mouseOverAndClick(xmlHeader);
		
		testCase.addScreenShotCurrentStep(u, "clickMenuEspaciosCargaMasivaEspacios"); 
	    u.hardWait(2);
	}

	/**
	 * Método: Resalta/Muestra Campos Pantalla Carga Masiva EspaciosEquipos
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
	public void clickMuestraCampos() {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		u.highLight(xmlSeleccionarArchivo);
		u.highLight(xmlBtnSubirArchivo);
		
		testCase.addScreenShotCurrentStep(u, "clickMuestraCampos"); 
	    u.hardWait(2);
	    
		u.unhighLight(xmlSeleccionarArchivo);
		u.unhighLight(xmlBtnSubirArchivo);
	}

	/**
	 * Método: Resalta/Muestra Etiquetas del Grid de Informacion
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
	public void clickMuestraGrid() {
		
		u.highLight(xmlLblNumero);
		u.highLight(xmlLblArchivo);
		u.highLight(xmlLblFechaIni);
		u.highLight(xmlLblFechaFin);
		u.highLight(xmlLblConteo);
		u.highLight(xmlLblPorcentaje);
		u.highLight(xmlLblEstatus);
		
		testCase.addScreenShotCurrentStep(u, "clickMuestraGrid"); 
	    u.hardWait(2);
	    
		u.unhighLight(xmlLblNumero);
		u.unhighLight(xmlLblArchivo);
		u.unhighLight(xmlLblFechaIni);
		u.unhighLight(xmlLblFechaFin);
		u.unhighLight(xmlLblConteo);
		u.unhighLight(xmlLblPorcentaje);
		u.unhighLight(xmlLblEstatus);
	}
	
	public void clickSelArchivo() {
		u.hardWait(2);
		u.highLight(xmlSeleccionarArchivo);
		String filePath = Constants.PATTERN_PATH + File.separator + "IEP" + File.separator + "Carga de inventario.xlsx";
		u.sendKeys(xmlSeleccionarArchivo,filePath);
		
		testCase.addScreenShotCurrentStep(u, "clickSelArchivo");

		u.unhighLight(xmlSeleccionarArchivo);

	}
	
	public void clickSubirArchivo() {
		
		u.highLight(xmlBtnSubirArchivo);
		u.click(xmlBtnSubirArchivo);
		
		u.hardWait(10);
		testCase.addScreenShotCurrentStep(u, "clickSubirArchivo");
	}
	
	public void clickLineaProcesamiento() {
		
		u.hardWait(30);
		testCase.addScreenShotCurrentStep(u, "clickLineaProcesamiento");
		
	}

}
