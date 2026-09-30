package WEB.inventarioEspacios.om;

import java.io.File;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ibm.icu.text.SimpleDateFormat;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.ExcelUtil;
import utils.selenium.SeleniumUtil;

public class ReporteCgeIEP {
	
	SeleniumUtil u;
	TestCase testCase;
	ExcelUtil eu;
	MetodosExtra m;
	Date date;

	/*
	 * Localizadores Pantalla 'Reporte de cobertura de grupo de espacios' 
	 * Reportes > Reporte de cobertura de grupo de espacios
	 ******************************************************************************************/

	/**
	 * Localizadores - Header de la P�gina
	 ******************************************************************************************/
	By xmlTituloPantalla = By.xpath("//div[@id='tituloEscritorio']/span");

	/**
	 * Localizadores - Apartado 'Filtros'
	 ******************************************************************************************/

	By xmlFiltros = By.xpath("//form[@id='formFiltroPlaza']/div[1]");
	By xmlBotones = By.xpath("//form[@id='formFiltroPlaza']/div[2]");

	// Lista desplegable 'Seleccione el proveedor'
	By xmlSistema = By.xpath("//form[@id='formFiltroPlaza']/div[1]//div[1]/mat-form-field/div/div[1]");
	By xmlSistemaPanel = By.id("mat-select-0-panel");
	By xmlSistemaOp = By.xpath("//div[@id='mat-select-0-panel']/mat-option/span");

	// Lista desplegable 'Seleccione el proveedor'
	By xmlPlaza = By.xpath("//form[@id='formFiltroPlaza']/div[1]//div[2]/mat-form-field/div/div[1]");
	By xmlPlazaPanel = By.id("mat-select-2-panel");
	By xmlPlazaOp = By.xpath("//div[@id='mat-select-2-panel']/mat-option/span");

	// Lista desplegable 'Seleccione el proveedor'
	By xmlSubplaza = By.xpath("//form[@id='formFiltroPlaza']/div[1]//div[3]/mat-form-field/div/div[1]");
	By xmlSubplazaPanel = By.id("mat-select-4-panel");
	By xmlSubplazaOp = By.xpath("//div[@id='mat-select-4-panel']/mat-option/span");

	// Lista desplegable 'Seleccione el proveedor'
	By xmlPeriodoA = By.xpath("//form[@id='formFiltroPlaza']/div[1]/div[2]/div[1]/mat-form-field/div/div[1]");
	By xmlPeriodoAPanel = By.id("mat-select-6-panel");
	By xmlPeriodoAOp = By.xpath("//div[@id='mat-select-6-panel']/mat-option/span");

	// Lista desplegable 'Seleccione el proveedor'
	By xmlPeriodoB = By.xpath("//form[@id='formFiltroPlaza']/div[1]/div[2]/div[2]/mat-form-field/div/div[1]");
	By xmlPeriodoBPanel = By.id("mat-select-8-panel");
	By xmlPeriodoBOp = By.xpath("//div[@id='mat-select-8-panel']/mat-option/span");

	
	// Botones 'Exportar', 'Limpiar', 'Guardar'
	By xmlExportarBtn = By.xpath("//form[@id='formFiltroPlaza']/div[2]/div/div[1]/button");
	By xmlLimpiarBtn = By.xpath("//form[@id='formFiltroPlaza']/div[2]/div/div[2]/button");
	By xmlBuscarBtn = By.xpath("//form[@id='formFiltroPlaza']/div[2]/div/div[3]/button");
	
	// Mensaje 'Favor de llenar todos los filtros'
	By xmlMsgFaltaLlenarFiltros = By.xpath("//div[@class='cdk-overlay-pane']//descendant::span[contains(text(),'Favor de llenar todos los filtros')]");
	
	/**
	 * Localizadores - Apartado 'Grid - Resultados de la Busqueda'
	 ******************************************************************************************/
	
	By xmlGrid = By.xpath("//app-ge-report/div/div");
	
	By xmlPlazasHdr = By.xpath("//table[@multitemplatedatarows]/thead/tr/th[1]");
	By xmlTotalTiendasHdr = By.xpath("//table[@multitemplatedatarows]/thead/tr/th[3]");
	By xmlTotalGecHdr = By.xpath("//table[@multitemplatedatarows]/thead/tr/th[4]");
	
	By xmlGridResults = By.xpath("//table[@multitemplatedatarows]/tbody/tr");
	
	By xmlPlazaR = By.xpath("//table[@multitemplatedatarows]/tbody/tr[1]/td[1]");
	By xmlPlazaDisplay = By.xpath("//table[@multitemplatedatarows]/tbody/tr[1]/td[2]/a/mat-icon");
	By xmlTotalTiendas = By.xpath("//table[@multitemplatedatarows]/tbody/tr[1]/td[3]");
	By xmlTotalGec = By.xpath("//table[@multitemplatedatarows]/tbody/tr[1]/td[4]");
	
	By xmlGridGrupoEspacios = By.xpath("//table[@id='grupoEspacio']");
	
	By xmlGrupoEspaciosHdr = By.xpath("//table[@id='grupoEspacio']/thead/tr/th[1]");
	By xmlCoberturaPeriodoAhdr = By.xpath("//table[@id='grupoEspacio']/thead/tr/th[2]");
	By xmlCoberturaPeriodoBhdr = By.xpath("//table[@id='grupoEspacio']/thead/tr/th[3]");
	
	By xmlGridGrupoEspaciosResults = By.xpath("//table[@id='grupoEspacio']/tbody/tr");
	
	By xmlAllGrupoEspacios = By.xpath("//table[@id='grupoEspacio']/tbody/tr/td[1]");
	By xmlAllCoberturaPeriodoA = By.xpath("//table[@id='grupoEspacio']/tbody/tr/td[2]");
	By xmlAllCoberturaPeriodoB = By.xpath("//table[@id='grupoEspacio']/tbody/tr/td[3]");
	
	
	SimpleDateFormat getYearFormat = new SimpleDateFormat("yyyy");
	SimpleDateFormat getDateFormat = new SimpleDateFormat("ddMMyyyyhhmmss");
	//yyyyMMddHHmmss
	/*
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase AsociarPopPlazasIEP
	 * 
	 * @author Andrea Contreras
	 * @param objeto (SeleniumUtil), objeto (testCase)
	 */
	public ReporteCgeIEP(SeleniumUtil u, TestCase testCase) {
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
	 * Metodo que valida se muestren la pantalla 'Reporte de cobertura de grupo de espacios'
	 * @return True o false si se muestran la pantalla
	 * @author Andrea Contreras
	 */
	public Boolean visualizarPantalla() {

		boolean validacion = false;
		String pantallaActual = u.getText(xmlTituloPantalla);

		if (pantallaActual.contains("Reporte de cobertura de grupo de espacios")) {
			validacion = true;
		}

		return validacion;
	}
	
	/**
	 * Metodo que valida se muestren los filtros de la pantalla 'Reporte de cobertura de grupo de espacios'
	 * @return True o false si se muestran los fitros
	 * @author Andrea Contreras
	 */
	public Boolean visualizarFiltros() {

		boolean validacion = false, sistema, plaza, subplaza, peridoA, peridoB;

		u.mouseOver(xmlFiltros);
		sistema = m.isEnable(xmlSistema);
		plaza = m.isEnable(xmlPlaza);
		subplaza = m.isEnable(xmlSubplaza);
		peridoA = m.isEnable(xmlPeriodoA);
		peridoB = m.isEnable(xmlPeriodoB);

		if (sistema == true && plaza == true && subplaza == true && peridoA == true && peridoB == true) {
			validacion = true;
		}
		
		u.highLight(xmlFiltros);
		testCase.addScreenShotCurrentStep(u, "verificarFiltros");
		u.unhighLight(xmlFiltros);

		return validacion;
	}
	
	/**
	 * Metodo que valida se muestren los botones de la pantalla 'Reporte de cobertura de grupo de espacios'
	 * @return True o false si se muestran los botones
	 * @author Andrea Contreras
	 */
	public Boolean visualizarBotones() {

		boolean validacion = false, limpiar, exportar, buscar;

		u.mouseOver(xmlFiltros);
		limpiar = m.isEnable(xmlLimpiarBtn);
		exportar = m.isEnable(xmlExportarBtn);
		buscar = m.isEnable(xmlBuscarBtn);

		if (limpiar == true && exportar == true && buscar == true ) {
			validacion = true;
		}
		
		u.highLight(xmlBotones);
		testCase.addScreenShotCurrentStep(u, "verificarBotones");
		u.unhighLight(xmlBotones);

		return validacion;
	}


	/**
	 * Metodo que valida se muestren los filtros y botones de la pantalla 'Reporte de cobertura de grupo de espacios'
	 * @return True o false si se muestran los filtros y los botones
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
	 * Metodo para seleccionar el Sistema
	 * @param sistema (String): sistema a seleccionar
	 * @return True o false si se selecciono la opcion
	 * @author Andrea Contreras
	 */
	public boolean selectSistema(String sistema) {

		boolean existOption = false;
		WebElement element = m.explicitWaitToBeClickable(xmlSistema, Duration.ofSeconds(30));
		u.hardWait(2);
		element.click();
		List<WebElement> allOptions = u.getDriver().findElements(xmlSistemaOp);
		for (WebElement option : allOptions) {
			if (option.getText().equals(sistema)) {
				u.hardWait(1);
				option.click();
				u.hardWait(1);
				existOption = true;
				break;
			}
		}

		m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
		u.highLight(xmlSistema);
		testCase.addScreenShotCurrentStep(u, "selectSistema");
		u.unhighLight(xmlSistema);
		return existOption;

	}
	
	
	/**
	 * Metodo para seleccionar el Plaza
	 * @param plaza (String): plaza a seleccionar
	 * @return True o false si se selecciono la opcion
	 * @author Andrea Contreras
	 */
	public boolean selectPlaza(String plaza) {

		boolean existOption = false;
		WebElement element = m.explicitWaitToBeClickable(xmlPlaza, Duration.ofSeconds(30));
		u.hardWait(2);
		element.click();
		List<WebElement> allOptions = u.getDriver().findElements(xmlPlazaOp);
		for (WebElement option : allOptions) {
			if (option.getText().equals(plaza)) {
				u.hardWait(1);
				option.click();
				u.hardWait(1);
				existOption = true;
				break;
			}
		}

		m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
		u.highLight(xmlPlaza);
		testCase.addScreenShotCurrentStep(u, "selectPlaza");
		u.unhighLight(xmlPlaza);
		return existOption;

	}
	
	/**
	 * Metodo para seleccionar el SubPlaza
	 * @param subplaza (String): subplaza a seleccionar
	 * @return True o false si se selecciono la opcion
	 * @author Andrea Contreras
	 */
	public boolean selectSubplaza(String subplaza) {

		boolean existOption = false;
		WebElement element = m.explicitWaitToBeClickable(xmlSubplaza, Duration.ofSeconds(30));
		u.hardWait(2);
		element.click();
		List<WebElement> allOptions = u.getDriver().findElements(xmlSubplazaOp);
		for (WebElement option : allOptions) {
			if (option.getText().equals(subplaza)) {
				u.hardWait(1);
				option.click();
				u.hardWait(1);
				existOption = true;
				break;
			}
		}

		m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
		u.highLight(xmlSubplaza);
		testCase.addScreenShotCurrentStep(u, "selectSubplaza");
		u.unhighLight(xmlSubplaza);
		return existOption;

	}
	
	
	
	/**
	 * Metodo para seleccionar el PeriodoA
	 * @param periodoA (String): periodoA a seleccionar
	 * @return True o false si se selecciono la opcion
	 * @author Andrea Contreras
	 */
	public boolean selectPeriodoA(String periodoA) {

		boolean existOption = false;
		WebElement element = m.explicitWaitToBeClickable(xmlPeriodoA, Duration.ofSeconds(30));
		u.hardWait(2);
		element.click();
		List<WebElement> allOptions = u.getDriver().findElements(xmlPeriodoAOp);
		for (WebElement option : allOptions) {
			if (option.getText().equals(periodoA)) {
				u.hardWait(1);
				option.click();
				u.hardWait(1);
				existOption = true;
				break;
			}
		}

		m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
		u.highLight(xmlPeriodoA);
		testCase.addScreenShotCurrentStep(u, "selectPeriodoA");
		u.unhighLight(xmlPeriodoA);
		return existOption;

	}

	
	/**
	 * Metodo para seleccionar el PeriodoB
	 * @param periodoB (String): periodoB a seleccionar
	 * @return True o false si se selecciono la opcion
	 * @author Andrea Contreras
	 */
	public boolean selectPeriodoB(String periodoB) {

		boolean existOption = false;
		WebElement element = m.explicitWaitToBeClickable(xmlPeriodoB, Duration.ofSeconds(30));
		u.hardWait(2);
		element.click();
		List<WebElement> allOptions = u.getDriver().findElements(xmlPeriodoBOp);
		for (WebElement option : allOptions) {
			if (option.getText().equals(periodoB)) {
				u.hardWait(1);
				option.click();
				u.hardWait(1);
				existOption = true;
				break;
			}
		}

		m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
		u.highLight(xmlPeriodoB);
		testCase.addScreenShotCurrentStep(u, "selectPeriodoB");
		u.unhighLight(xmlPeriodoB);
		return existOption;

	}
	
	
	/**
	 * Metodo para validar que las opciones de 'PeriodoA' sean hasta dos a�os anteriores
	 * @return True o false si las opciones son hasta dos a�os anteriores
	 * @author Andrea Contreras
	 */
	public boolean validarPeriodoA() {
		
		u.scrollToElement(xmlFiltros);
		
	    String currentYear = getYearFormat.format(date);
	    String oneYearBeforeCY = String.valueOf(Integer.parseInt(getYearFormat.format(date))-1);
	    String twoYearsBeforeCY = String.valueOf(Integer.parseInt(getYearFormat.format(date))-2);
      
		boolean validacion = true;
		WebElement element = m.explicitWaitToBeClickable(xmlPeriodoA, Duration.ofSeconds(30));
		u.hardWait(2);
		//u.mouseOverAndClick(xmlPeriodoA);;
		element.click();
		
		List<WebElement> allOptions = u.getDriver().findElements(xmlPeriodoAOp);
		
		for (WebElement option : allOptions) {
			if (!option.getText().contains(currentYear)){
				validacion = false;
				break;
			}
			if (!option.getText().contains(oneYearBeforeCY)){
				validacion = false;
				break;
			}
			if (!option.getText().contains(twoYearsBeforeCY)){
				validacion = false;
				break;
			}	
		}
		
		System.out.print("\nValidacion periodo A: "+validacion);

		testCase.addScreenShotCurrentStep(u, "validarPeriodoA");
		u.mouseOverAndClick(xmlTituloPantalla);
		
		return validacion;

	}
	
	
	/**
	 * Metodo para validar que las opciones de 'PeriodoB' sean hasta dos a�os anteriores
	 * @return True o false si las opciones son hasta dos a�os anteriores
	 * @author Andrea Contreras
	 */
	public boolean validarPeriodoB() {
		
		u.scrollToElement(xmlFiltros);
		
	    String currentYear = getYearFormat.format(date);
	    String oneYearBeforeCY = String.valueOf(Integer.parseInt(getYearFormat.format(date))-1);
	    String twoYearsBeforeCY = String.valueOf(Integer.parseInt(getYearFormat.format(date))-2);
      
		boolean validacion = true;
		WebElement element = m.explicitWaitToBeClickable(xmlPeriodoB, Duration.ofSeconds(30));
		u.hardWait(2);
		element.click();
		
		List<WebElement> allOptions = u.getDriver().findElements(xmlPeriodoBOp);
		
		for (WebElement option : allOptions) {
			if (!option.getText().contains(currentYear)){
				validacion = false;
				break;
			}
			if (!option.getText().contains(oneYearBeforeCY)){
				validacion = false;
				break;
			}
			if (!option.getText().contains(twoYearsBeforeCY)){
				validacion = false;
				break;
			}			

		}

		System.out.print("\nValidacion periodo B: "+validacion);
		
		testCase.addScreenShotCurrentStep(u, "validarPeriodoB");
		u.mouseOverAndClick(xmlTituloPantalla);
		
		return validacion;
		
	}
	
	//*************************************** BOTONES *************************************
	
	/**
     * Metodo para dar click en el boton 'Exportar'
	 * @author Andrea Contreras
	 */
    public void clickExportarBtn(){ 
    	   	
    	m.explicitWaitToBeClickable(xmlExportarBtn,Duration.ofSeconds(10));
    	u.scrollToElement(xmlExportarBtn);
    	u.hardWait(1);
	    u.highLight(xmlExportarBtn);
	    testCase.addScreenShotCurrentStep("btnExportar");
	    System.out.println(date);
	    
	    u.click(xmlExportarBtn);
	    
	    String currentDate = getDateFormat.format(date);
		System.out.print("\nFecha para el archivo: "+currentDate);
		
	    testCase.addScreenShotCurrentStep("clickExportar");
	    u.unhighLight(xmlExportarBtn);
	    
	    m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		
   }
    
	/**
     * Metodo para dar click en el boton 'Limpiar'
	 * @author Andrea Contreras
	 */
    public void clickLimpiarBtn(){ 
    	
    	m.explicitWaitToBeClickable(xmlLimpiarBtn,Duration.ofSeconds(10));
	    u.highLight(xmlLimpiarBtn);
	    testCase.addScreenShotCurrentStep(u, "btnLimpiar");
	    u.click(xmlLimpiarBtn);		
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
	
	
	//*************************************** MENSAJES *************************************
	
	/**
	* Metodo para validar que se muestra el mensaje 'Favor de llenar todos los filtros'
	* * @author Andrea Contreras
	*/
	public boolean validarMsgFaltaLlenarFiltros() {

		boolean msgExist = false;
		if (m.existElement(xmlMsgFaltaLlenarFiltros)==true) {				
			msgExist = true;
		}
		testCase.addScreenShotCurrentStep(u, "msgFaltaLlenarFiltros");
		System.out.print("\nExiste mensaje Falta Llenar Filtros -"+msgExist);
		return msgExist;
		
	}
		
	/*
	 * METODOS - Apartado 'Grid - Resultados de la Busqueda'
	 ******************************************************************************************/	

	/**
	 * Metodo que valida se muestre en 'Grid - Resultados de la Busqueda' la informacion de la plaza
	 * @return True o false si se muestra la informacion de la plaza
	 * @author Andrea Contreras
	 */
	public Boolean visualizarResultadosPlaza() {

		boolean validacion = false, plazas, tiendas, gec, resultados;

		u.scrollToElement(xmlGrid);
		plazas = m.isEnable(xmlPlazasHdr);
		tiendas = u.isDisplayed(xmlTotalTiendasHdr);
		gec = m.isEnable(xmlTotalGecHdr);
		resultados = m.existMultipleElements(xmlGridResults);

		if (plazas == true && tiendas == true && gec == true && resultados == true) {
			validacion = true;
		}
		
		//u.highLight(xmlFiltros);
		testCase.addScreenShotCurrentStep(u, "verificarPlaza");
		//u.unhighLight(xmlFiltros);

		return validacion;
	}

	/**
	 * Metodo que valida se muestre en 'Grid - Resultados de la Busqueda' la informacion de los grupos de espacios/equipos de la plaza
	 * @param hideResults (boolean): Se requiere esconder la lista de resultados desplegados
	 * @return True o false si se muestra la informacion de la plaza
	 * @author Andrea Contreras
	 */
	public Boolean visualizarResultadosGrupoEspacios(boolean hideResults) {

		boolean validacion = false, grupo, periodoA, periodoB, resultados;

		if(!u.isDisplayed(xmlGridGrupoEspacios)){
        	u.click(xmlPlazaDisplay);
    		m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
        }	
		
		u.scrollToElement(xmlGridGrupoEspacios);	
		
		grupo = m.isEnable(xmlGrupoEspaciosHdr);
		periodoA = u.isDisplayed(xmlCoberturaPeriodoAhdr);
		periodoB = m.isEnable(xmlCoberturaPeriodoBhdr);
		resultados = m.existMultipleElements(xmlGridGrupoEspaciosResults);

		if (grupo == true && periodoA == true && periodoB == true && resultados == true) {
			validacion = true;
		}
		
		testCase.addScreenShotCurrentStep(u, "verificarGruposDePlaza");
		
		if(hideResults) {
			u.click(xmlPlazaDisplay);
		}

		return validacion;
	}

	/**
	 * Metodo que valida se muestren los campos de Relacionar un POP con sus
	 * respectivas plazas y Relacionar un POP con sus respectivas plazas Consulta de
	 * POPs asociados con Selenium

	 * @param option (String): opci�n de pop a seleccionar
	 * @return Regresa true si se muestran los campos o false si no se muestran
	 * @author Andrea Contreras
	 */
	public Boolean visualizarResultados() {

		m.explicitWaitToBeInvisible(Duration.ofSeconds(50));

		boolean validacion = false;
		boolean resultsPlaza = visualizarResultadosPlaza();
		System.out.print("\nplazas: "+resultsPlaza);
		boolean resultsGrupos = visualizarResultadosGrupoEspacios(true);
		System.out.print("\ngrupos: "+resultsGrupos);

		if (resultsPlaza == true && resultsGrupos == true) {
			validacion = true;
		}

		return validacion;
	}	
	
	
	/**
	 * Metodo para dar click en el hipervinculo de la plaza
	 * @author Andrea Contreras
	 */
	public void clickPlazaLink() {

		u.scrollToElement(xmlPlaza);
		u.highLight(xmlPlazaR);
	    testCase.addScreenShotCurrentStep(u, "verificarGruposDePlaza");
	    u.click(xmlPlazaDisplay);
		m.explicitWaitToBeInvisible(Duration.ofSeconds(15));		
		testCase.addScreenShotCurrentStep(u, "verificarGruposDePlaza");
		u.highLight(xmlPlazaR);
		
	}
	
	
	/*
	 * METODOS - Validaciones al Exportar Reporte
	 ******************************************************************************************/			
	public Boolean validarDescarga() {
		
		
		Boolean exist = false;
		
		String pathYourSystem = System.getProperty("user.home") + "\\Downloads\\Reporte_Plaza_Grupos_Espacios_Tiendas_19072022053039.xlsx";
		File f = new File(pathYourSystem);
		
		if (f.exists()) {
			exist = true;
		}
		
		return exist;
	}
	

}
