package WEB.inventarioEspacios.om;

import org.openqa.selenium.By;
import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Consulta_Movtos_Admdor_OXXO_Alta_tienda {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;
	/*
	 * Localizadores Pantalla Detalle - Consulta_Movtos_Asignacion_direccion_envio
	 ******************************************************************************************/
	By xmlUsuario = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/p");
    By xmlNegocio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[1]");
    By xmlPerfil = By.xpath("");  
    
    By xmlHeader = By.xpath("//mat-sidenav-content[@id='content-nav']/app-header");
  
    // Paso 6
    By xmlnomTda = By.xpath("//*[@id=\"mat-input-11\"]");
    By xmlPlaza = By.xpath("//*[@id=\"mat-select-value-7\"]/span/span");
    By xmlretekId = By.xpath("//*[@id=\"mat-input-12\"]");
    By xmlSubPlaza = By.xpath("//*[@id=\"mat-select-value-9\"]");
    By xmlAsesor = By.xpath("//*[@id=\"mat-input-13\"]");
    By xmlPlazaCCM = By.xpath("//*[@id=\"mat-select-value-11\"]");
    By xmlmetros = By.xpath("//*[@id=\"mat-input-14\"]");
    By xmldistritos = By.xpath("//*[@id=\"mat-select-value-13\"]");
    By xmlsegmentacion = By.xpath("//*[@id=\"mat-select-value-15\"]");
    By xmltdaModelo = By.xpath("//*[@id=\"mat-select-value-13\"]");
    By xmlSegmentoM = By.xpath("//*[@id=\"mat-select-value-17\"]");
    By xmlOpTda = By.xpath("//*[@id=\"mat-checkbox-11\"]/label");
  
/*
 * Variables que reciben un parametro
 **********************************************************************************************/
    String StrNomDirEdit = ("//table/tbody/tr/td[2]/a[contains(text(),' %s ')]//following::td[8]/mat-icon");
    String StrSelTda = ("//div[@class=\"form-group-input row lista-inventario lista-cuadro\"]/section/span[2]/ul/div/li/mat-checkbox/label/span[2][contains(text(),\'%s')]");
    
    
	/**
	 * Constructor
	 ******************************************************************************************/
	public Consulta_Movtos_Admdor_OXXO_Alta_tienda(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);
    }
	
	/**
	 * Método para llenar los datos de asignacion de direccion de envio y dar de alta
	 * 
	 * @author Manuel CUen
	 * @param Plaza, Direccion, Codigo Postal, Calle, Numero, Entre Calle, y Entre Calle, Nombre y Telefono del Contacto, Comentario
	 * @return ninguno
	 */
	public void ClickAltaTienda() {
		
		
	}
}
