package WEB.inventarioEspacios.om;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.ExcelUtil;
import utils.selenium.SeleniumUtil;

public class ConsultaPlazasIEP {

	SeleniumUtil u;
	TestCase testCase;
	ExcelUtil eu;
	MetodosExtra m;
	Date date;

	/*
	 * Localizadores Pantalla 'Consulta de Plazas' 
     * Administracion > Consulta de plazas
	 ******************************************************************************************/

	/**
	 * Localizadores - Header de la P�gina
	 ******************************************************************************************/
	By xmlTituloPantalla = By.xpath("//div[@id='tituloEscritorio']/span");

	/**
	 * Localizadores - Apartado 'Filtros'
	 ******************************************************************************************/

	By xmlFiltros = By.xpath("//form[@id='formFiltroPlaza']/div[1]");
	By xmlBotones = By.xpath("//form[@id='formFiltroPlaza']/div[2]/div");

	// Lista desplegable 'Seleccione el proveedor'
	By xmlNombrePlaza = By.name("nombrePlaza");
	By xmlClavePlaza = By.name("clavePlaza");
	
	// Botones 'Limpiar', 'Buscar'
	By xmlLimpiarBtn = By.xpath("//form[@id='formFiltroPlaza']/div[2]/div/div[1]/button");
	By xmlBuscarBtn = By.xpath("//form[@id='formFiltroPlaza']/div[2]/div/div[2]/button");
	

	/**
	 * Localizadores - Apartado 'Grid - Resultados de la Busqueda'
	 ******************************************************************************************/
	
	By xmlGrid = By.xpath("//table");
	
	By xmlIdPlazaHdr = By.xpath("//table/thead/tr/th[1]");
	By xmlNombrePlazaHdr = By.xpath("//table/thead/tr/th[2]");
	By xmlClavePlazaHdr = By.xpath("//table/thead/tr/th[3]");
	
	By xmlGridResults = By.xpath("//table/tbody/tr");
	
	By xmlIdPlazaR = By.xpath("//table//tbody/tr/td[1]");
	By xmlNombrePlazaR = By.xpath("//table//tbody/tr/td[2]");
	By xmlClavePlazaR = By.xpath("//table//tbody/tr/td[3]");
	

	/*
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase ConsultaPlazasIEP
	 * @author Andrea Contreras
	 * @param u (SeleniumUtil): objeto de la clase 
	 * @param testCase (testCase): objeto de la clase
	 */
	public ConsultaPlazasIEP(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.date = new Date();
		this.m = new MetodosExtra(u, testCase);
		
	}
	
	/*
	 * METODOS - Apartado 'Filtros'
	 ******************************************************************************************/
	
	//*************************************** VALIDACIONES *************************************
	
	/**
	 * Metodo que valida se muestren la pantalla 'Consulta de plazas'
	 * @return True o false si se muestran la pantalla
	 * @author Andrea Contreras
	 */
	public Boolean visualizarPantalla() {

		boolean validacion = false;
		String pantallaActual = u.getText(xmlTituloPantalla);

		if (pantallaActual.contains("Consulta de plazas")) {
			validacion = true;
		}

		return validacion;
	}
	
	/**
	 * Metodo que valida se muestren los filtros de la pantalla 'Consulta de plazas'
	 * @return True o false si se muestran los fitros
	 * @author Andrea Contreras
	 */
	public Boolean visualizarFiltros() {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));

		boolean validacion = false, nomPlaza, clavePlaza;

		u.mouseOver(xmlFiltros);
		nomPlaza = m.isEnable(xmlNombrePlaza);
		clavePlaza = m.isEnable(xmlClavePlaza);

		if (nomPlaza == true && clavePlaza == true) {
			validacion = true;
		}
		
		u.highLight(xmlFiltros);
		testCase.addScreenShotCurrentStep(u, "verificarFiltros");
		u.unhighLight(xmlFiltros);

		return validacion;
	}
	
	/**
	 * Metodo que valida se muestren los botones de la pantalla 'Consulta de plazas'
	 * @return True o false si se muestran los botones
	 * @author Andrea Contreras
	 */
	public Boolean visualizarBotones() {

		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		
		boolean validacion = false, limpiar, buscar;

		u.mouseOver(xmlFiltros);
		limpiar = m.isEnable(xmlLimpiarBtn);
		buscar = m.isEnable(xmlBuscarBtn);

		if (limpiar == true && buscar == true ) {
			validacion = true;
		}
		
		u.highLight(xmlBotones);
		testCase.addScreenShotCurrentStep(u, "verificarBotones");
		u.unhighLight(xmlBotones);

		return validacion;
	}


	/**
	 * Metodo que valida se muestren los filtros y botones de la pantalla 'Consulta de plazas'
	 * @return True o false si se muestran los filtro y botones
	 * @author Andrea Contreras
	 */
	public Boolean visualizarCampos() {

		m.explicitWaitToBeInvisible(Duration.ofSeconds(50));

		boolean validacion = false;
		boolean pantalla = visualizarPantalla();
		boolean filtros = visualizarFiltros();
		boolean botones = visualizarBotones();

		if (pantalla == true && filtros == true && botones == true) {
			validacion = true;
		}

		return validacion;
	}
	
	//*************************************** FILTROS *************************************
	
	/**
	 * Metodo para ingresar un valor en el campo 'Nombre de plaza'
	 * @param nombrePlaza (String): nombre de la plaza a ingresar
	 * @author Andrea Contreras
	 */
	public void typeNombrePlaza(String nombrePlaza) {

		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));		
		u.highLight(xmlNombrePlaza);
		u.sendKeys(xmlNombrePlaza, nombrePlaza);
		testCase.addScreenShotCurrentStep(u, "typeNombrePlaza");
		u.unhighLight(xmlNombrePlaza);	
	}
	
	/**
	 * Metodo para ingresar un valor en el campo 'Clave de plaza'
	 * @param clavePlaza (String): clave de la plaza a ingresar
	 * @author Andrea Contreras
	 */
	public void typeClavePlaza(String clavePlaza) {

		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));		
		u.highLight(xmlClavePlaza);
		u.sendKeys(xmlClavePlaza, clavePlaza);
		testCase.addScreenShotCurrentStep(u, "typeClavePlaza");
		u.unhighLight(xmlClavePlaza);
		
	}
	
	/**
	 * Metodo que valida si estan vacios los filtros
	 * @return True o false si estan vacios los filtros
	 * @author Andrea Contreras
	 */
	public Boolean validarFiltrosVacios() {
		
		//u.scrollToElement(xmlFiltros);	
		
		boolean validacion = false;

		WebElement nombre = u.getDriver().findElement(xmlNombrePlaza);
		WebElement clave = u.getDriver().findElement(xmlClavePlaza);
		
		System.out.print("\nvalor input nombre: "+nombre.getAttribute("value").isEmpty());
		System.out.print("\nvalor input clave: "+clave.getAttribute("value").isEmpty());
			
		if (nombre.getAttribute("value").isEmpty() == true 
				&& clave.getAttribute("value").isEmpty() == true) {
			validacion = true;
		}
		
		u.highLight(xmlFiltros);	
		testCase.addScreenShotCurrentStep(u, "validarFiltrosVacios");
		u.unhighLight(xmlFiltros);

		return validacion;
		
			
	}

	


	//*************************************** BOTONES *************************************
	
    
	/**
     * Metodo para dar click en el boton 'Limpiar'
	 * @author Andrea Contreras
	 */
    public void clickLimpiarBtn(){ 
    	
    	u.scrollToElement(xmlTituloPantalla);
    	m.explicitWaitToBeClickable(xmlLimpiarBtn,Duration.ofSeconds(10));
	    u.highLight(xmlLimpiarBtn);
	    testCase.addScreenShotCurrentStep(u, "btnLimpiar");
	    u.click(xmlLimpiarBtn);	
	    m.explicitWaitToBeClickable(xmlLimpiarBtn,Duration.ofSeconds(10));
	    testCase.addScreenShotCurrentStep(u, "clickLimpiar");
	    u.unhighLight(xmlLimpiarBtn);
		
   }
    
	/**
     * Metodo para dar click en el boton 'Buscar'
	 * @author Andrea Contreras
	 */
    public void clickBuscarBtn(){ 
    	
    	m.explicitWaitToBeClickable(xmlBuscarBtn,Duration.ofSeconds(10));
	    u.highLight(xmlBuscarBtn);
	    testCase.addScreenShotCurrentStep(u, "btnBuscar");
	    u.click(xmlBuscarBtn);	
	    m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
	    testCase.addScreenShotCurrentStep(u, "clickBuscar");
	    u.unhighLight(xmlBuscarBtn);
		
   }
	
	
	/*
	 * METODOS - Apartado 'Grid - Resultados de la Busqueda'
	 ******************************************************************************************/	

	/**
	 * Metodo que valida se muestren los resultados de la busqueda en el Grid
	 * @param valor (String): valor ingresado en el campo 'Nombre de plaza/Clave de plaza'
	 * @param opcion (String): se ingreso 'NOMBRE' o 'CLAVE' de la plaza como filtro
	 * @return True o false si se muestran los resultados de la busqueda
	 * @author Andrea Contreras
	 */
	public Boolean visualizarResultadosBusqueda(String valor, String opcion) {

		boolean idPlaza, nomPlaza, clvPlaza, resultados;
		boolean valResults = true, validacion = false;
		
		u.scrollToElement(xmlGrid);	
		
		idPlaza = u.isDisplayed(xmlIdPlazaHdr);
		nomPlaza = u.isDisplayed(xmlNombrePlazaHdr);
		clvPlaza = u.isDisplayed(xmlClavePlazaHdr);
		resultados = m.existMultipleElements(xmlGridResults);
		List<WebElement> allOptions = null;
		
		switch (opcion.toUpperCase()){
			case "NOMBRE":
				allOptions = u.getDriver().findElements(xmlNombrePlazaR);
		        break;
				
		    case "CLAVE":
		    	allOptions = u.getDriver().findElements(xmlClavePlazaR);
		        break;			
		}

		for (WebElement option : allOptions) {
			String op = option.getText().toLowerCase();
			if (!op.contains(valor.toLowerCase())) {
				valResults = false;
				break;
			}
		}
		
		if (idPlaza == true && nomPlaza == true && clvPlaza == true && resultados == true
				&& valResults == true ) {
			validacion = true;
		}
		
		testCase.addScreenShotCurrentStep(u, "verificarGruposDePlaza");

		return validacion;
		
	}
	
	
	/**
	 * Metodo que valida si se limpiaron los filtros y los resultados de la busqueda
	 * @return True o false si se limpiaron los filtros y los resultados de la busqueda
	 * @author Andrea Contreras
	 */
	public Boolean validarLimpiezaFiltrosGrid(String valor, String opcion) {
	
		boolean validacion = false, filtros, grid;

		filtros = validarFiltrosVacios();
		grid = visualizarResultadosBusqueda(valor, opcion);
				
		if (filtros == true 
				&& grid == false) {
			validacion = true;
		}
		
		System.out.print("\nvalidacion limpieza filtros y grid: "+validacion);

		return validacion;		
			
	}
	
	
	

	
	

}
