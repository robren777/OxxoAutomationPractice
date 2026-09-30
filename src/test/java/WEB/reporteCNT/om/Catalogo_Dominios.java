package WEB.reporteCNT.om;

import org.openqa.selenium.By;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Catalogo_Dominios {
	
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	
	By xmlPantalla = By.xpath("//form[@id='catalogodominioform']/div[5][contains(text(),'Catlogo de Dominios')]");
	String strEntidad = "//form[@id='catalogodominioform']/div[4]//span[contains(text(),'%s')]";
	
	By xmlPantalla2 = By.id("nombreModficar");
	/*
	 * Localizadores tabla de Dominios
	 ******************************************************************************************/

	//Tabla
	By xmlTablaDom = By.xpath("//tbody[@id='catalogodominioform:domainList_data']");
	
	//Resultados de la tabla
	By xmlTablaDomResult = By.xpath("//tbody[@id='catalogodominioform:domainList_data']/tr");	
    String strDominioModificar = "//tr/td[2]//span[contains(text(),'%s')]/ancestor::td/following-sibling::td[2]//a[1]";
    String strDominioEliminar = "//tr/td[2]//span[contains(text(),'%s')]/ancestor::td/following-sibling::td[2]//a[1}2]";

	
	
	/*
	 * Constructor
	 ******************************************************************************************/
	
	/**
	 * Constructor de la clase Catalogo de Dominios
	 * @author Andrea Contreras
	 * @param u (SeleniumUtil): objeto de la clase 
	 * @param testCase (testCase): objeto de la clase
	 */
	public Catalogo_Dominios(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}
	
	
	/**
	 * M�todo para validar que se muestre la pantalla 'Catalogo de Dominios'
	 * @return true o false si se muestra la pantalla
	 * @author Andrea Contreras
	 */
	public boolean visualizaPantallaCatalogoDominios() {
		
		return me.existElement(xmlPantalla);	
		
	}
	
	
	/**
	 * M�todo para validar que se muestren informacion de la entidad
	 * @param entidad (String): entidad a validar
	 * @return true o false si se muestra la pantalla
	 * @author Andrea Contreras
	 */
	public boolean validarInformacionEntidad(String entidad) {
		
		By locator = By.xpath(String.format(strEntidad, entidad));	
		return me.existElement(locator);
		
	}
	
	
	/**
	 * M�todo para validar que se muestre la pantalla 'Catalogo de Dominios'
	 *  y la informaci�n de los dominios de una entidad en especifico
	 * @param entidad (String): entidad a validar
	 * @return true o false si se muestra la pantalla
	 * @author Andrea Contreras
	 */
	public boolean visualizarDominios(String entidad) {
		
		boolean validacion = false,v1,v2,v3;
		
		v1 = visualizaPantallaCatalogoDominios();
		v2 = validarInformacionEntidad(entidad);
		v3 = me.existMultipleElements(xmlTablaDomResult);
		if(v1 == true && v2 == true && v3 == true) {
			validacion = true;
		}
		
		testCase.addScreenShotCurrentStep(u, "vDom");
		
		return validacion;
		
	}
	
	
	
    /** Metodo para dar click en la opcion 'Modificar' de un dominio en especifico
     * @param dominio (String): dominio a modificar
     * @author Andrea Contreras
     */
	public void modificarDominio(String dominio) {
		
		By locator = By.xpath(String.format(strDominioModificar, dominio));
		
		u.highLight(locator);
		testCase.addScreenShotCurrentStep(u, "mDominio1");
		u.click(locator);
		u.waitForLoadPage();
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "mDominio2");
		
	}


}
