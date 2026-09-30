package WEB.reporteCNT.om;

import org.openqa.selenium.By;
import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

/**
 * Localizadores y m�todos de la pantalla 'Catlogo de Entidades' del portal Reportes CNT
 ******************************************************************************************/
public class Catalogo_Entidades {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	
	
	By xmlPantalla = By.xpath("//span[@id='catlgEnt']");

	/*
	 * Localizadores tabla de Entidades
	 ******************************************************************************************/

	//Entidades
	By xmlEntidadPA = By.xpath("//span[text()='PLAZAS_ADMON']");
	By xmlEntidadTA = By.xpath("//span[text()='TIENDAS_ADMON']");	

	String strVerDominio = "//span[text()='%s']/ancestor::td/following-sibling::td[3]/div/a";
	
	//Selecciona los filtros a incluir en el reporte.
	By xmlSeccion3 = By.id("Paso3");
	
	
	
	
	/*
	 * Constructor
	 ******************************************************************************************/
	
	/**
	 * Constructor de la clase Catalogo de Entidades
	 * @author Andrea Contreras
	 * @param u (SeleniumUtil): objeto de la clase 
	 * @param testCase (testCase): objeto de la clase
	 */
	public Catalogo_Entidades(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}
	
	
	/**
	 * M�todo para validar que se muestre la pantalla 'Catalogo de Entidades'
	 * @return true o false si se muestra la pantalla
	 * @author Andrea Contreras
	 */
	public boolean visualizaPantallaCatalogosEntidades() {
		
		return me.existElement(xmlPantalla);	
		
	}
	
	
	/*
	 * M�todos tabla de Entidades
	 ******************************************************************************************	
	
	/**
	 * M�todo para validar que se muestren las entidades PLAZAS_ADMON y TIENDAS_ADMON
	 * @return true o false si se muestran las entidades
	 * @author Andrea Contreras
	 */
	public boolean visualizarEntidades() {
		
        boolean validacion = false, e1, e2;
		
        e1 = me.existElement(xmlEntidadPA); 
        e2 = me.existElement(xmlEntidadTA);

		if(e1 == true && e2 == true) {
			validacion = true;			
		}	
		
		u.highLight(xmlEntidadPA);
		u.highLight(xmlEntidadTA);
		testCase.addScreenShotCurrentStep(u, "verificarOpciones");
		u.unhighLight(xmlEntidadPA);
		u.unhighLight(xmlEntidadTA);
		
		return validacion;	
		
	}
	
	
    /** M�todo para dar click en 'Ver Dominio'
     * @param entidad (String): entidad de la cual se quiere ver el dominio
     * @author Andrea Contreras
     */
	public void clickVerDominio(String entidad) {
		
		By locator = By.xpath(String.format(strVerDominio, entidad));
		
		u.highLight(locator);
		testCase.addScreenShotCurrentStep(u, "vDominio1");
		u.click(locator);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "vDominio2");
		
	}
	

	

	

}
