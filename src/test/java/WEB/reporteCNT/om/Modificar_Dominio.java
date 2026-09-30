package WEB.reporteCNT.om;

import org.openqa.selenium.By;
import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Modificar_Dominio {
	
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	
	
	By xmlPantalla = By.id("nombreModficar");
	
	/*
	 * Localizadores del apartado - Datos Generales
	 ******************************************************************************************/

	By xmlProbarConexion = By.id("ptt");
	/*
	 * Localizadores del apartado - Consulta
	 ******************************************************************************************/

	//Query
	
	By xmlQuery = By.id("query");
	
	By xmlProbarQuery = By.id("prueba_query_link");
	
	By xmlBtnSiguiente = By.xpath("//a[@type='submit']");
			
	
	
	
	
	//Tabla
	By xmlTablaConsultas = By.xpath("//tbody[@id='queryList_data']");
	
	//Resultados de la tabla
	By xmlTablaDomResult = By.xpath("//tbody[@id='catalogodominioform:domainList_data']/tr");	
    String strConsultaModificar = "//tbody/tr/td[2]//span[contains(text(),'%s')]/ancestor::td/following-sibling::td[2]//a[1]";
    String strConsultaEliminar = "//tbody/tr/td[2]//span[contains(text(),'%s')]/ancestor::td/following-sibling::td[2]//a[2]";
	
	
	
	/*
	 * Constructor
	 ******************************************************************************************/
	
	/**
	 * Constructor de la clase Modificar_Dominio
	 * @author Andrea Contreras
	 * @param u (SeleniumUtil): objeto de la clase 
	 * @param testCase (testCase): objeto de la clase
	 */
	public Modificar_Dominio(SeleniumUtil u, TestCase testCase) {
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
	public boolean visualizaPantallaModificarDominio() {
		
		return me.existElement(xmlPantalla);	
		
	}

	
    /** Metodo para dar click en la opcion 'Modificar' de la consulta a elegir
     * @param consulta (String): consulta a modificar
     * @author Andrea Contreras
     */
	public void modificarConsulta(String consulta) {
		
		u.scrollToElement(xmlTablaConsultas);
		
		By locator = By.xpath(String.format(strConsultaModificar, consulta));
		
		u.highLight(locator);
		testCase.addScreenShotCurrentStep(u, "mConsulta1");
		u.click(locator);
		u.waitForLoadPage();
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "mConsulta2");
		
	}
	
	
	/**
	 * Metodo para visualizar el query de la consulta
	 * @return true o false si se muestra el query
	 * @author Andrea Contreras
	 */
	public boolean visualizarQuery() {
		 
		u.scrollToElement(xmlQuery);
		String consultaActual = u.getText(xmlQuery);
		boolean validacion = consultaActual.isEmpty();
		testCase.addScreenShotCurrentStep(u, "queryActual");
		testCase.addTextEvidenceCurrentStep(consultaActual);	
		
		return validacion;
		
	}
	
	
	/**
	 * Metodo para modificar el query de la consulta
	 * @param query (String): query por el que se modificara
	 * @author Andrea Contreras
	 */
	public void modificarQuery(String query) {
		 
		u.scrollToElement(xmlQuery);		
		u.clear(xmlQuery);
		testCase.addScreenShotCurrentStep(u, "limpiarQuery");
		u.sendKeys(xmlQuery, query);
		testCase.addScreenShotCurrentStep(u, "nuevoQuery");		
		
	}
	
	
	/**
	 * Metodo para dar click en 'Probar Conexion' y 'Probar Query'
	 * @author Andrea Contreras
	 */
	public void probarConexion_ProbarQuery() {
		
		
		u.scrollToElement(xmlPantalla);		
		u.click(xmlProbarConexion);
		testCase.addScreenShotCurrentStep(u, "probarConexion");
		
		u.scrollToElement(xmlQuery);		
		u.click(xmlProbarQuery);
		testCase.addScreenShotCurrentStep(u, "probarQuery");		
		
	}


}
