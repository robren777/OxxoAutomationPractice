package WEB.inventarioEspacios.om;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.ExcelUtil;
import utils.selenium.SeleniumUtil;

public class ConsultaPlazasCcmIEP {
	/**
	 * Localizadores - Pantalla 'Consulta de plazas CCM > Nuevo'
	 ******************************************************************************************/

	By xmlNombrePlazaNuevaCCM = By.id("mat-input-1");

	// Lista desplegable 'Plaza'
	By xmlPlazaNueva = By.xpath("//div[@class='form-div divDetalle']/div/div[2]/mat-form-field/div/div[1]");
	By xmlPlazaNuevaPanel = By.xpath("//div[@id='mat-select-4-panel']");
	By xmlPlazaNuevaOp = By.xpath("//div[@id='mat-select-4-panel']/mat-option/span");

	// Botones 'Cancelar', 'Limpiar', 'Guardar'
	By xmlCancelarNuevoBtn = By.xpath("//form[@id='formFiltroPlaza']/div[2]/div/div[1]/button");
	By xmlLimpiarNuevoBtn = By.xpath("//form[@id='formFiltroPlaza']/div[2]/div/div[3]/button");
	By xmlGuardarNuevoBtn = By.xpath("//form[@id='formFiltroPlaza']/div[2]/div/div[4]/button");
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

	By xmlNombrePlazaCCM = By.name("nombrePlazaccm");

	// Lista desplegable 'Plaza'
	By xmlPlaza = By.xpath("//form[@id='formFiltroPlaza']/div[1]/div[2]//mat-form-field/div/div[1]");
	By xmlPlazaPanel = By.xpath("//div[@class='cdk-overlay-pane']");
	By xmlPlazaOp = By.xpath("//div[@class='cdk-overlay-pane']/div/div/mat-option");
	By xmlPlazaValorActual = By.xpath("//form[@id='formFiltroPlaza']/div[1]/div[2]//mat-select//span/span");
	
	// Botones 'Nuevo', 'Eliminar', 'Limpiar', 'Buscar'
	By xmlNuevoBtn = By.xpath("//form[@id='formFiltroPlaza']/div[2]/div/div[1]/button");
	By xmlEliminarBtn = By.xpath("//form[@id='formFiltroPlaza']/div[2]/div/div[2]/button");
	By xmlLimpiarBtn = By.xpath("//form[@id='formFiltroPlaza']/div[2]/div/div[3]/button");
	By xmlBuscarBtn = By.xpath("//form[@id='formFiltroPlaza']/div[2]/div/div[4]/button");
	

	/**
	 * Localizadores - Apartado 'Grid - Resultados de la Busqueda'
	 ******************************************************************************************/
	
	By xmlGrid = By.xpath("//table");
	
	By xmlIdPlazaCcmHdr = By.xpath("//table/thead/tr/th[2]");
	By xmlNombrePlazaHdr = By.xpath("//table/thead/tr/th[3]");
	By xmlPlazaHdr = By.xpath("//table/thead/tr/th[4]");
	
	By xmlGridResults = By.xpath("//table/tbody/tr");
	
	By xmlIdPlazaCcmCheckR = By.xpath("//table//tbody/tr/td[1]/mat-checkbox//input");
	By xmlIdPlazaCcmR = By.xpath("//table//tbody/tr/td[2]");
	By xmlNombrePlazaR = By.xpath("//table//tbody/tr/td[3]");
	By xmlPlazaR = By.xpath("//table//tbody/tr/td[4]");
	By xmlPlazaEditR = By.xpath("//table//tbody/tr/td[5]/mat-icon");
	
	// Opciones para cambiar de pagina en el Grid 'Consulta de POPs asociados'
	
	By xmlBtnFirstPage = By.xpath("//div[@class='mat-paginator-container']/div[2]/button[1]");
	By xmlBtnFirstPageDisable = By.xpath("//div[@class='mat-paginator-container']/div[2]/button[1][@disabled='true']");
	// By xmlBtnPreviousPage =
	// By.xpath("//div[@class='mat-paginator-container']/div[2]/button[2]");
	By xmlBtnPreviousPageDisable = By.xpath("//div[@class='mat-paginator-container']/div[2]/button[2][@disabled='true']");
	By xmlBtnNextPage = By.xpath("//div[@class='mat-paginator-container']/div[2]/button[3]");
	By xmlBtnNextPageDisable = By.xpath("//div[@class='mat-paginator-container']/div[2]/button[3][@disabled='true']");
	// By xmlBtnLastPage =
	// By.xpath("//div[@class='mat-paginator-container']/div[2]/button[4]");
	By xmlBtnLastPageDisable = By.xpath("//div[@class='mat-paginator-container']/div[2]/button[4][@disabled='true']");

    String StrOpNomEdit = "//table/tbody/tr/td[3][contains(text(),'%s')]";
	String StrOpPlazaEdit = "//table/tbody/tr/td[4][contains(text(),'%s')]";
	
	By xmlMsg = By.xpath("//snack-bar-container//span");
	
	By xmlConfirmacion = By.xpath("//mat-dialog-container//h5");
	By xmlCancelarConfirmacion = By.xpath("//mat-dialog-container//div[2]/div/div/button");
	By xmlAceptarConfirmacion = By.xpath("//mat-dialog-container//div[2]/div/div/div/button");

	/*
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase ConsultaPlazasCcmIEP
	 * @author Andrea Contreras
	 * @param u (SeleniumUtil): objeto de la clase 
	 * @param testCase (testCase): objeto de la clase
	 */
	public ConsultaPlazasCcmIEP(SeleniumUtil u, TestCase testCase) {
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
	 * Metodo que valida se muestren la pantalla 'Consulta de plazas CCM'
	 * @return True o false si se muestra la pantalla
	 * @author Andrea Contreras
	 */
	public Boolean visualizarPantalla() {

		boolean validacion = false;
		String pantallaActual = u.getText(xmlTituloPantalla);

		if (pantallaActual.contains("Consulta de plazas CCM")) {
			validacion = true;
		}

		return validacion;
	}
	
	/**
	 * Metodo que valida se muestren los filtros de la pantalla 'Consulta de plazas CCM'
	 * @return True o false si se muestran los fiLtros
	 * @author Andrea Contreras
	 */
	public Boolean visualizarFiltros() {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));

		boolean validacion = false, nomPlazaCCM, plaza;

		u.mouseOver(xmlFiltros);
		nomPlazaCCM = m.isEnable(xmlNombrePlazaCCM);
		plaza = m.isEnable(xmlPlaza);

		if (nomPlazaCCM == true && plaza == true) {
			validacion = true;
		}
		
		u.highLight(xmlFiltros);
		testCase.addScreenShotCurrentStep(u, "verificarFiltros");
		u.unhighLight(xmlFiltros);

		return validacion;
	}
	
	/**
	 * Metodo que valida se muestren los botones de la pantalla 'Consulta de plazas CCM'
	 * @return True o false si se muestran los botones
	 * @author Andrea Contreras
	 */
	public Boolean visualizarBotones() {

		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		
		boolean validacion = false, nuevo, limpiar, buscar;

		u.mouseOver(xmlFiltros);
		nuevo = m.isEnable(xmlNuevoBtn);
		limpiar = m.isEnable(xmlLimpiarBtn);
		buscar = m.isEnable(xmlBuscarBtn);

		if (nuevo == true && limpiar == true && buscar == true ) {
			validacion = true;
		}
		
		u.highLight(xmlBotones);
		testCase.addScreenShotCurrentStep(u, "verificarBotones");
		u.unhighLight(xmlBotones);

		return validacion;
	}


	/**
	 * Metodo que valida se muestren los filtros y botones de la pantalla 'Consulta de plazas CCM'
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
	 * Metodo para ingresar un valor en el campo 'Nombre de plaza CCM'
	 * @param nombrePlaza (String): nombre de la plaza a ingresar
	 * @author Andrea Contreras
	 */
	public void typeNombrePlazaCCM(String nombrePlaza) {

		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));		
		u.highLight(xmlNombrePlazaCCM);
		u.sendKeys(xmlNombrePlazaCCM, nombrePlaza);
		testCase.addScreenShotCurrentStep(u, "typeNombrePlazaCCM");
		u.unhighLight(xmlNombrePlazaCCM);	
	}
	
	/**
	 * Metodo para seleccionar una plaza
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
			System.out.print("ingresa al ciclo");
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
	 * Metodo que valida si estan vacios los filtros
	 * @return True o false si estan vacios los filtros
	 * @author Andrea Contreras
	 */
	public Boolean validarFiltrosVacios() {
		
		//u.scrollToElement(xmlFiltros);	
		
		boolean validacion = false;

		WebElement nombrePlazaCCM = u.getDriver().findElement(xmlNombrePlazaCCM);
		boolean plazaExist = m.existElement(xmlPlazaValorActual);
		
		System.out.print("\nvalor input nombre esta vacio: "+nombrePlazaCCM.getAttribute("value").isEmpty());
		System.out.print("\nexiste un valor input clave: "+plazaExist);
			
		if (nombrePlazaCCM.getAttribute("value").isEmpty() == true 
				&& plazaExist == false) {
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
     * Metodo para dar click en el boton 'Nuevo'
	 * @author Andrea Contreras
	 */
    public void clickNuevoBtn(){ 
    	
    	m.explicitWaitToBeClickable(xmlNuevoBtn,Duration.ofSeconds(10));
	    u.highLight(xmlNuevoBtn);
	    testCase.addScreenShotCurrentStep(u, "btnNuevo");
	    u.click(xmlNuevoBtn);	
	    m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
	    testCase.addScreenShotCurrentStep(u, "clickNuevo");
		
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
    
	/**
     * Metodo para dar click en el boton 'Eliminar'
	 * @author Andrea Contreras
	 */
    public void clickEliminarBtn(){ 
    	
    	m.explicitWaitToBeClickable(xmlEliminarBtn,Duration.ofSeconds(10));
	    u.highLight(xmlEliminarBtn);
	    testCase.addScreenShotCurrentStep(u, "btnEliminar");
	    u.click(xmlEliminarBtn);	
	    m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
	    testCase.addScreenShotCurrentStep(u, "clickEliminar");
	    u.unhighLight(xmlEliminarBtn);
		
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

		boolean idPlazaCMM, nomPlaza, plaza, resultados;
		boolean valResults = true, validacion = false;
		valor = valor.toLowerCase();
		
		u.scrollToElement(xmlGrid);	
		
		idPlazaCMM = u.isDisplayed(xmlIdPlazaCcmHdr);
		nomPlaza = u.isDisplayed(xmlNombrePlazaHdr);
		plaza = u.isDisplayed(xmlPlazaHdr);
		resultados = m.existMultipleElements(xmlGridResults);
		List<WebElement> allOptions = null;
		
		switch (opcion.toUpperCase()){
			case "NOMBRE_PLAZA_CCM":
				allOptions = u.getDriver().findElements(xmlNombrePlazaR);
		        break;
				
		    case "PLAZA":
		    	allOptions = u.getDriver().findElements(xmlPlazaR);
		        break;			
		}
		System.out.print("\nValor de parametro: "+valor);			

		for (WebElement option : allOptions) {
			System.out.print("\nEntro al for");			
			String op = option.getText().toLowerCase();
			System.out.print("\nop: "+op);
			if (!valor.contains(op) && !op.contains(valor) ) {
				valResults = false;
				break;
			}
		}
		
		if (idPlazaCMM == true && nomPlaza == true && plaza == true && resultados == true
				&& valResults == true ) {
			validacion = true;
		}
		
		testCase.addScreenShotCurrentStep(u, "verificarGridResultados");

		return validacion;
		
	}
	
	/**
	 * Metodo que valida se muestren los resultados de la busqueda en el Grid
	 * @return True o false si se muestran los resultados de la busqueda
	 * @author Andrea Contreras
	 */
	public Boolean visualizarResultadosBusqueda() {

		boolean idPlazaCMM, nomPlaza, plaza, resultados, validacion = false;
		
		u.scrollToElement(xmlGrid);	
		
		idPlazaCMM = u.isDisplayed(xmlIdPlazaCcmHdr);
		nomPlaza = u.isDisplayed(xmlNombrePlazaHdr);
		plaza = u.isDisplayed(xmlPlazaHdr);
		resultados = m.existMultipleElements(xmlGridResults);
		
		if (idPlazaCMM == true && nomPlaza == true && plaza == true && resultados == true) {
			validacion = true;
		}
		
		testCase.addScreenShotCurrentStep(u, "verificarGridResultados");

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
	
	/**
	 * Metodo para dar click en el icono 'Editar' de alguno de los registros del Grid
	 * @param tipoPop (String): tipo de pop
	 * @author Andrea Contreras
	 **/
	public String clickEditarRegistro() {
	
		String id = "";
		u.click(xmlPlazaEditR);

		boolean isSelected = false;
	    //table//tbody/tr/td[5]/mat-icon/parent::td/preceding-sibling::td[3]
		do {
			
			if(m.existElement(xmlPlazaEditR)==true) {
			
			u.highLight(xmlPlazaEditR);		
			testCase.addScreenShotCurrentStep(u, "editIconPlazaCCM");
			id = u.getText(xmlIdPlazaCcmR);
			u.click(xmlPlazaEditR);
			m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
			isSelected = true;
		    System.out.print("\n-encontro el pop-");			
			
			}
			
			if(m.existElement(xmlPlazaEditR)==false) {
				
			if((m.existElement(xmlBtnNextPageDisable)==true)&&(m.existElement(xmlBtnPreviousPageDisable)==true)) {
				System.out.print("\n-no existen plazas en el pop, se realiza break del while-");			
				isSelected = true;				
				
			}else if(m.existElement(xmlBtnFirstPageDisable)==true && m.existElement(xmlBtnNextPageDisable)==false) {
				
    			  u.click(xmlBtnNextPage);
    			  m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
    			  System.out.print("\n-cambia a la siguiente pagina para seguir buscando-");
    			
    		    }else {
    		    	break;
    		    }
			}
			
			
		}while(!isSelected);
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(5));
		testCase.addScreenShotCurrentStep(u, "ventanaEditarPlazaCCM");
			
		return id;
	}
	
	
	/**
	 * Metodo para dar click en el icono 'Editar' de alguno de los registros del Grid
	 * @param tipoPop (String): tipo de pop
	 * @author Andrea Contreras
	 **/
	public void clickEditarRegistro(String valor) {
		
		String locator = String.format(StrOpNomEdit, valor);
		By element = By.xpath(locator);
		
        boolean exist = existeRegistroGrid(element);
		
		if (exist == true) {
			
			u.highLight(element);	
			locator += "/following-sibling::td/mat-icon";
			u.highLight(By.xpath(locator));
			testCase.addScreenShotCurrentStep(u, "clickEdit");
			u.click(By.xpath(locator));
			m.explicitWaitToBeInvisible(Duration.ofSeconds(15));

		}	else {
			Assert.fail("No se encontro el registro");
		}
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(5));
		testCase.addScreenShotCurrentStep(u, "pantallaEdit");	
					
	}
	
	
	/**
	 * Metodo para seleccionar un registro del grid de resultados
	 * @param String valor(nombre de la plaza)
	 * @author Andrea Contreras
	 **/
	public void seleccionarRegistro(String valor) {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.scrollToElement(xmlGrid);
		String locator = String.format(StrOpNomEdit, valor);
		By element = By.xpath(locator);
		
        boolean exist = existeRegistroGrid(element);
		
		if (exist == true) {
			//table/tbody/tr/td[3][contains(text(),'Acapulco Editado')]/preceding-sibling::td[2]/mat-checkbox/label
			u.highLight(element);
			String checkStr = locator+"/preceding-sibling::td[2]/mat-checkbox";			
			u.click(By.xpath(checkStr));
			u.hardWait(1);
			testCase.addScreenShotCurrentStep(u, "checkRegistro");
			//u.unhighLight(element);

		}else {
			Assert.fail("No se encontro el registro");
		}
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(5));
		testCase.addScreenShotCurrentStep(u, "eliminarBtn");	
					
	}
	
	/**
	 * Metodo para seleccionar uno o varios registro del grid de resultados
	 * @param ArrayList<String> plazas
	 * @author Andrea Contreras
	 **/
	public void seleccionarRegistro(ArrayList<String> plazas) {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.scrollToElement(xmlGrid);
		
		for(int i = 0; i < plazas.size(); ++i){
				
		String locator = String.format(StrOpNomEdit, plazas.get(i));
		By element = By.xpath(locator);
		
        boolean exist = existeRegistroGrid(element);
		
		if (exist == true) {
			//table/tbody/tr/td[3][contains(text(),'Acapulco Editado')]/preceding-sibling::td[2]/mat-checkbox/label
			u.highLight(element);
			String checkStr = locator+"/preceding-sibling::td[2]/mat-checkbox";			
			u.click(By.xpath(checkStr));
			u.hardWait(1);
			testCase.addScreenShotCurrentStep(u, "checkRegistro");
			//u.unhighLight(element);

		}else {
			Assert.fail("No se encontro el registro");
		}
		
		}
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(5));
		testCase.addScreenShotCurrentStep(u, "registroSeleccionado");	
					
	}
	
	
	/**
	 * Metodo que valida se encuentra un elemento en el Grid Principal
	 * @param element (By): locator del elemento 
	 * @return True o false si se encuentra el elemento
	 * @author Andrea Contreras
	 */
	public Boolean existeRegistroGrid(By element) {

		boolean exist = false, ciclo = false;
		
		u.scrollToElement(xmlGrid);
		
		do {
			
			if(m.existElement(element)==true) {
							
			ciclo = false;
			exist = true;
		    System.out.print("-se encontro en el Grid\n");			
			
			}
			
			if(m.existElement(element)==false) {
				
			if((m.existElement(xmlBtnNextPageDisable)==true)&&(m.existElement(xmlBtnPreviousPageDisable)==true)) {
				ciclo = false;
				System.out.print("-no existen el registro en el Grid\n");
				//testCase.addTextEvidenceCurrentStep("No se encontro el tipo de pedido especificado");	
				Assert.fail("No se encontro el tipo de pedido especificado en el Grid");
				
			}else if(m.existElement(xmlBtnNextPageDisable)==false) {
				  ciclo = true;
    			  u.click(xmlBtnNextPage);
    			  m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
    			  System.out.print("-cambia a la siguiente pagina para seguir buscando\n");
    			
    		    }else {	
    		    	ciclo = false;
    		    	System.out.print("-no existen el registro en el Grid\n");	
    		    	//testCase.addTextEvidenceCurrentStep("No se encontro el tipo de pedido especificado");
    		    	Assert.fail("No se encontro el tipo de pedido especificado en el Grid");
    		    }
			}
			
		}while(ciclo);
		
		return exist;
		
	}
	
	/**
	 * Metodo para validar que se muestre el boton eliminar
	 * @return true o false si se muestra el boton
	 * @author Andrea Contreras
	 **/
	public boolean visualizarBotonEliminar() {
		
		boolean validacion = false;
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		u.scrollToElement(xmlBotones);
		
		if(m.existElement(xmlEliminarBtn)) {
			validacion = true;
			u.highLight(xmlEliminarBtn);
			testCase.addScreenShotCurrentStep("botonEliminar");
		}
		
		return validacion;
		
	}
	
	
	/**
	* Metodo para validar que se muestra el mensaje indicado en el Confirm Box
	* @param
	* @return true o false si se muestra el mensaje con el texto indicado
	* @author Andrea Contreras
	*/
	public boolean validarMsjConfirmacionTexto(String texto) {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
	    texto = texto.replaceAll("(\n|\r)", "");	
		boolean msgExist = false;
		
		if (m.existElement(xmlConfirmacion)==true) {	
			String textoScreen = u.getText(xmlConfirmacion).replaceAll("(\n|\r)", "");
			u.highLight(xmlConfirmacion);
			if(textoScreen.equals(texto)) {
				msgExist = true;
			}
		}
		
		testCase.addScreenShotCurrentStep(u, "confirmBox");
		System.out.print("\nExiste mensaje  -"+msgExist);
		return msgExist;
	}
	
	/**
	* Metodo para seleccionar una opcion del Mensaje de Confirmacion
	* @param opcion(String)
	* @author Andrea Contreras
	*/
	public boolean seleccinarOpcionMsjConfirmacion(String opcion) {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		boolean msgExist = false;
		opcion = opcion.replaceAll("(\n|\r)", "").toUpperCase();
		By element = null;
		
		switch(opcion) {
		case "CONFIRMAR":
			element = xmlAceptarConfirmacion;
			break;
		case "CANCELAR":
			element = xmlAceptarConfirmacion;
			break;
		}
			
		if (m.existElement(element)==true) {	
				msgExist = true;
				u.highLight(element);
				testCase.addScreenShotCurrentStep(u, "confirmationBox1");
				u.click(element);		
		}else {
			Assert.fail("No se encontro la opcion");
		}
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		testCase.addScreenShotCurrentStep(u, "confirmationBox2");
		return msgExist;
	}
	
	/**
	* Metodo para validar que se muestra el mensaje con el texto indicado
	* @param
	* @return true o false si se muestra el mensaje con el texto indicado
	* @author Andrea Contreras
	*/
	public boolean validarMsgTexto(String texto) {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
	    texto = texto.replaceAll("(\n|\r)", "");	
		boolean msgExist = false;
		
		if (m.existElement(xmlMsg)==true) {	
			String textoScreen = u.getText(xmlMsg).replaceAll("(\n|\r)", "");
			u.highLight(xmlMsg);
			if(textoScreen.equals(texto)) {
				msgExist = true;
			}
		}
		
		testCase.addScreenShotCurrentStep(u, "msg");
		System.out.print("\nExiste mensaje  -"+msgExist);
		return msgExist;
	}
		
	

}
