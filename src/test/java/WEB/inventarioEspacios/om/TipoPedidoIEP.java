package WEB.inventarioEspacios.om;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class TipoPedidoIEP {
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;
	/*
	 * Localizadores Pantalla 'Tipos de pedido' 
     * Pedidos Admin > Tipos de pedido
	 ******************************************************************************************/

	/**
	 * Localizadores - Header de la P�gina
	 ******************************************************************************************/
	By xmlTituloPantalla = By.xpath("//div[@id='tituloEscritorio']/span");

	/**
	 * Localizadores - Apartado 'Filtros'
	 ******************************************************************************************/ 
	By xmlFiltros = By.xpath("//form[@id='formFiltroPop']/div[1]");
	By xmlBotones = By.xpath("//form[@id='formFiltroPop']/div[2]/div");
	
	
    // Localizador  Nombre Tipo de Pedido
    By xmlNombreTipoPed= By.xpath("//input[@name='nombre']");
 
    // Localizador del Boton Buscar
    By xmlBtnNuevo = By.xpath("//form[@id='formFiltroPop']/div[2]/div/div[1]/button");
    By xmlBtnLimpiar = By.xpath("//form[@id='formFiltroPop']/div[2]/div/div[3]/button");
    By xmlBtnBuscar = By.xpath("//form[@id='formFiltroPop']/div[2]/div/div[4]/button");
    
  
 
    // Localizadores botones Eliminar y confirmar eliminacion
    By xmlBtnEliminar = By.xpath("//*[@id=\"formFiltroPop\"]/div[2]/div/div[2]/button/span[1]");
    By xmlConfEliminar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");
    

    // Localizador checkbox Eliminacion Tipo de Pedido
    String StrCheckTipoPed = ("//div/table/tbody/tr/td[2]/a[contains(text(),'%s')]/ancestor::td/preceding-sibling::td/mat-checkbox");
 
    
	/**
	 * Localizadores - Apartado 'Grid Principal'
	 ******************************************************************************************/
	
	By xmlGrid = By.xpath("//table");
	
	By xmlIdHdr = By.xpath("//table/thead/tr/th[2]");
	By xmlNombreHdr = By.xpath("//table/thead/tr/th[4]");
	By xmlTipoPopHdr = By.xpath("//table/thead/tr/th[5]");
	By xmlPeriodVisHdr = By.xpath("//table/thead/tr/th[6]");
	By xmlExportarHdr = By.xpath("//table/thead/tr/th[7]");
	By xmlAutorizacionHdr = By.xpath("//table/thead/tr/th[8]");
	By xmlProvEmpHdr = By.xpath("//table/thead/tr/th[9]");
	By xmlActivoHdr = By.xpath("//table/thead/tr/th[10]");
	
	By xmlIdPlazaCcmCheckR = By.xpath("//table//tbody/tr/td[1]/mat-checkbox//input");
	
	
	By xmlGridResults = By.xpath("//table/tbody/tr");
	
	By xmlIdCheckR = By.xpath("//table//tbody/tr/td[1]/mat-checkbox//input");
	By xmlIdR = By.xpath("//table//tbody/tr/td[2]/a");	
	By xmlComentarioR = By.xpath("//textarea[@placeholder=\"Comentario\"]");
	By xmlNombreR = By.xpath("//table//tbody/tr/td[4]");
	By xmlEditR = By.xpath("//table/tbody/tr/td/mat-icon[contains(text(),'edit')]");
	By xmlImageR = By.xpath("//table/tbody/tr/td/mat-icon[contains(text(),'image')]");
	By xmlComentarioDesplegadoR = By.xpath("//table/tbody/tr/td/div[@style='']//textarea");
	
	String StrNomTipoPedGrid = "//table/tbody/tr/td[4][contains(text(),'%s')]";
	String StrIdTipoPedGrid = "//table/tbody/tr/td[2]/a[contains(text(),'%s')]";
	String StrIdTipoPedGrid2 = "//table//tbody/tr/td[3]/a/mat-icon/ancestor::td/preceding-sibling::td[1]/a[contains(text(),'%s')]";
	//String StrComentTipoPedGrid = "//table//tbody/tr/td[3]/a/mat-icon";
	String StrImagenPed = "//table/tbody/tr/td[4][contains(text(),'%s')]/following-sibling::td[7]/mat-icon[2]";
	String StrEditPed = "//table/tbody/tr/td/a[contains(text(),'%s')]/parent::td/following-sibling::td[9]/mat-icon[1]";
	
	
	
	
	// Opciones para cambiar de pagina en el Grid 
	By xmlBtnFirstPageDisable = By.xpath("//div[@class='mat-paginator-container']/div[2]/button[1][@disabled='true']");

	By xmlBtnPreviousPageDisable = By.xpath("//div[@class='mat-paginator-container']/div[2]/button[2][@disabled='true']");
	
	By xmlBtnNextPage = By.xpath("//div[@class='mat-paginator-container']/div[2]/button[3]");
	By xmlBtnNextPageDisable = By.xpath("//div[@class='mat-paginator-container']/div[2]/button[3][@disabled='true']");

	By xmlBtnLastPageDisable = By.xpath("//div[@class='mat-paginator-container']/div[2]/button[4][@disabled='true']");

	
	
	/**
	 * Localizadores - Ventana 'Imagen tipo de pedido'
	 ******************************************************************************************/

	
	By XmlVentanaImagen = By.xpath("//mat-dialog-container[@id='mat-dialog-0']");////h5[contains(text(),'Imagen del POP')]/ancestor::mat-dialog-container	
	By XmlLblSel_Archivo = By.xpath("//mat-dialog-container[@id='mat-dialog-0']/app-carga-imagen/form/div[1]/div/div[2]/input");
    By XmlImg = By.xpath("//mat-dialog-container[@id='mat-dialog-0']/app-carga-imagen//img");
	
	By XmlLblNombreImagen = By.xpath("//*[@id='mat-dialog-0']/app-carga-imagen/form/div[1]/div/div[3]/mat-label[1]");
	By XmlLblFechaImagen = By.xpath("//*[@id='mat-dialog-0']/app-carga-imagen/form/div[1]/div/div[3]/mat-label[2]");
	By XmlLblTamanoImagen = By.xpath("//*[@id='mat-dialog-0']/app-carga-imagen/form/div[1]/div/div[3]/mat-label[3]");
	By XmlBtnCancelar = By.xpath("//*[@id='mat-dialog-0']/app-carga-imagen/form/div[2]/div/div[2]/button");
	By XmlBtnAceptar = By.xpath("//*[@id='mat-dialog-0']/app-carga-imagen/form/div[2]/div/div[3]/button");

	
	By xmlMsgCargaImg = By.xpath("//div[@class='cdk-overlay-pane']//descendant::span[contains(text(),'Imagen cargada exitosamente')]");
	
	
    /**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase TipoPedidoIEP
	 * @author Andrea Contreras
	 * @param u (SeleniumUtil): objeto de la clase 
	 * @param testCase (testCase): objeto de la clase
	 */
    public TipoPedidoIEP(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);
    }
    
    /*
	 * METODOS - Apartado 'Filtros'
	 ******************************************************************************************/
	
	//*************************************** VALIDACIONES *************************************
	
	/**
	 * Metodo que valida se muestren la pantalla 'Tipos de pedido'
	 * @return True o false si se muestran la pantalla
	 * @author Andrea Contreras
	 */
	public Boolean visualizarPantalla() {

		boolean validacion = false;
		String pantallaActual = u.getText(xmlTituloPantalla);

		if (pantallaActual.contains("Tipos de pedido")) {
			validacion = true;
		}

		return validacion;
	}
	
	/**
	 * Metodo que valida se muestren los filtros de la pantalla 'Tipos de pedido'
	 * @return True o false si se muestran los fitros
	 * @author Andrea Contreras
	 */
	public Boolean visualizarFiltros() {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));

		boolean validacion = false;

		u.mouseOver(xmlFiltros);
		validacion = m.isEnable(xmlNombreTipoPed);
		
		u.highLight(xmlFiltros);
		testCase.addScreenShotCurrentStep(u, "verificarFiltro");
		u.unhighLight(xmlFiltros);

		return validacion;
	}
	
	/**
	 * Metodo que valida se muestren los botones de la pantalla 'Tipos de pedido'
	 * @return True o false si se muestran los botones
	 * @author Andrea Contreras
	 */
	public Boolean visualizarBotones() {

		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		
		boolean validacion = false, nuevo, limpiar, buscar;

		u.mouseOver(xmlFiltros);
		nuevo = m.existElement(xmlBtnNuevo);
		limpiar = m.isEnable(xmlBtnLimpiar);
		buscar = m.isEnable(xmlBtnBuscar);

		if (nuevo == true && limpiar == true && buscar == true ) {
			validacion = true;
		}
		
		u.highLight(xmlBotones);
		testCase.addScreenShotCurrentStep(u, "verificarBotones");
		u.unhighLight(xmlBotones);

		return validacion;
	}
	
	/**
	 * Metodo que valida se muestren el Grid principal. 
	 * ID Tipo de Pedido/Nombre/Tipo de POP/Periodos Visibles/Editar/Carga de imagen/
	 * Comentarios (Se mostrar� un checkbox por registro)
	 * @return True o false si se muestran el Grid
	 * @author Andrea Contreras
	 */
	public Boolean visualizarGridPrincipal() {

		boolean id, comentario, nombre, tipoPop, periodoVis, exportar, autorizacion, provEmp, 
		activo, editar, imagen, validacion = false;
		
		u.scrollToElement(xmlGrid);	
		
		id = u.isDisplayed(xmlIdHdr);
		System.out.print("id: "+id);
		comentario = m.existElement(xmlComentarioR);
		System.out.print("comentario: "+comentario);
		nombre = u.isDisplayed(xmlNombreHdr);
		System.out.print("nombre: "+nombre);
		tipoPop = u.isDisplayed(xmlTipoPopHdr);
		System.out.print("tipoPop: "+tipoPop);
		periodoVis = u.isDisplayed(xmlPeriodVisHdr);
		System.out.print("periodoVis: "+periodoVis);
		exportar = u.isDisplayed(xmlExportarHdr);
		System.out.print("exportar: "+exportar);
		autorizacion = u.isDisplayed(xmlAutorizacionHdr);
		System.out.print("autorizacion: "+autorizacion);
		provEmp = u.isDisplayed(xmlProvEmpHdr);
		System.out.print("provEmp: "+provEmp);
		activo = u.isDisplayed(xmlActivoHdr);
		System.out.print("activo: "+activo);
		editar = m.existElement(xmlEditR);
		System.out.print("editar: "+editar);
		imagen = m.existElement(xmlImageR);
		System.out.print("imagen: "+imagen);
		

		if (id == true && comentario == true && nombre == true && tipoPop == true && periodoVis == true 
				&& exportar == true && autorizacion == true && provEmp == true && activo == true
				     && editar == true && imagen == true) {
			validacion = true;
		}
		
		testCase.addScreenShotCurrentStep(u, "verificarGrid");

		return validacion;
		
	}


	/**
	 * Metodo que valida se muestren los filtros y botones de la pantalla 'Tipos de pedido'
	 * @return True o false si se muestran los filtro y botones
	 * @author Andrea Contreras
	 */
	public Boolean visualizarCampos() {

		m.explicitWaitToBeInvisible(Duration.ofSeconds(50));

		boolean validacion = false;
		boolean pantalla = visualizarPantalla();
		System.out.print("pantalla: "+pantalla);
		boolean filtros = visualizarFiltros();
		System.out.print("filtros: "+filtros);
		boolean botones = visualizarBotones();
		System.out.print("botones: "+botones);
		boolean grid = visualizarGridPrincipal();
		System.out.print("grid: "+grid);

		if (pantalla == true && filtros == true && botones == true && grid == true) {
			validacion = true;
		}

		return validacion;
	}
    
    
	//*************************************** FILTROS *************************************
	
	/**
	 * Metodo para ingresar un valor en el campo 'Nombre del Tipo de Pedido'
	 * @param nomTipoPed (String): nombre del tipo de pedido a ingresar
	 * @author Andrea Contreras
	 */
	public void typeNombreTipoPedido(String nomTipoPed) {

		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));		
		u.highLight(xmlNombreTipoPed);
		u.sendKeys(xmlNombreTipoPed, nomTipoPed);
		testCase.addScreenShotCurrentStep(u, "typeNombrePlaza");
		u.unhighLight(xmlNombreTipoPed);	
	}
	
	/**
	 * Metodo para validar que se ingreso un dato en el campo 'Nombre del Tipo de Pedido' y que sea alfanumerico
	 * @param nomTipoPed (String): nombre del tipo de pedido
	 * @author Andrea Contreras
	 */
	public boolean validarNombreTipoPedido(String nomTipoPed) {

		boolean val = false;
		u.highLight(xmlNombreTipoPed);
		testCase.addScreenShotCurrentStep(u, "typeNombrePlaza");
		u.unhighLight(xmlNombreTipoPed);
		WebElement element = u.getDriver().findElement(xmlNombreTipoPed);
		String valorInput = element.getAttribute("value");
		boolean val1 = valorInput.equals(nomTipoPed);
		boolean val2 = m.isAlphaNumeric(valorInput);
		System.out.print(valorInput+"***"+val1 +"***"+val2);
		
		if(val1 == true && val2 == true) {
			val = true;
		}
			
		
		return val;
	}
	
	
	/**
	 * Metodo que valida si estan vacios los filtros
	 * @return True o false si estan vacios los filtros
	 * @author Andrea Contreras
	 */
	public Boolean validarFiltrosVacios() {
		
		//u.scrollToElement(xmlFiltros);	

		WebElement nombreTipoPed = u.getDriver().findElement(xmlNombreTipoPed);
		
		System.out.print("\nvalor input nombre esta vacio: "+nombreTipoPed.getAttribute("value").isEmpty());
		boolean validacion = nombreTipoPed.getAttribute("value").isEmpty();
		
		u.highLight(xmlFiltros);	
		testCase.addScreenShotCurrentStep(u, "validarFiltrosVacios");
		u.unhighLight(xmlFiltros);

		return validacion;
		
			
	}

	
	//*************************************** BOTONES *************************************

	/**
     * Metodo para dar click en el boton 'Nuevo'
	 * @author Andrea Contreras
	 */
    public void clickNuevoBtn(){ 
    	u.scrollToElement(xmlBotones);
    	m.explicitWaitToBeClickable(xmlBtnNuevo,Duration.ofSeconds(10));
	    u.highLight(xmlBtnNuevo);
	    testCase.addScreenShotCurrentStep(u, "btnNuevo");
	    u.click(xmlBtnNuevo);	
	    m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
	    testCase.addScreenShotCurrentStep(u, "clickNuevo");
		
   }
	/**
     * Metodo para dar click en el boton 'Limpiar'
	 * @author Andrea Contreras
	 */
    public void clickLimpiarBtn(){ 
    	
    	u.scrollToElement(xmlTituloPantalla);
    	m.explicitWaitToBeClickable(xmlBtnLimpiar,Duration.ofSeconds(10));
	    u.highLight(xmlBtnLimpiar);
	    testCase.addScreenShotCurrentStep(u, "btnLimpiar");
	    u.click(xmlBtnLimpiar);	
	    m.explicitWaitToBeClickable(xmlBtnLimpiar,Duration.ofSeconds(10));
	    testCase.addScreenShotCurrentStep(u, "clickLimpiar");
	    u.unhighLight(xmlBtnLimpiar);
		
   }
    
	/**
     * Metodo para dar click en el boton 'Buscar'
	 * @author Andrea Contreras
	 */
    public void clickBuscarBtn(){ 
    	
    	m.explicitWaitToBeClickable(xmlBtnBuscar,Duration.ofSeconds(10));
	    u.highLight(xmlBtnBuscar);
	    testCase.addScreenShotCurrentStep(u, "btnBuscar");
	    u.click(xmlBtnBuscar);	
	    m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
	    testCase.addScreenShotCurrentStep(u, "clickBuscar");
	    u.unhighLight(xmlBtnBuscar);
		
   }
    
    
	/*
	 * METODOS - Apartado 'Grid Principal'
	 ******************************************************************************************/
    
    
	/**
	 * Metodo que valida se muestren los resultados de la busqueda en el Grid
	 * @param tipoPed (String): valor ingresado en el campo 'Nombre del tipo de pedido'
	 * @return True o false si se muestran los resultados de la busqueda
	 * @author Andrea Contreras
	 */
	public Boolean visualizarResultadosBusqueda(String tipoPed) {

		boolean valResults = true, validacion = false, resultados;
		tipoPed = tipoPed.toLowerCase();
		
		u.scrollToElement(xmlGrid);			
		resultados = m.existMultipleElements(xmlGridResults);
		List<WebElement> allOptions = u.getDriver().findElements(xmlNombreR);
		System.out.print("\nValor de parametro: "+tipoPed);			

		for (WebElement option : allOptions) {
			System.out.print("\nEntro al for");			
			String op = option.getText().toLowerCase();
			System.out.print("\nop: "+op);
			if (!tipoPed.contains(op) && !op.contains(tipoPed) ) {
				valResults = false;
				break;
			}
		}
		
		if (resultados == true && valResults == true ) {
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
		
		u.scrollToElement(xmlGrid);			
		boolean resultados = m.existMultipleElements(xmlGridResults);		
		testCase.addScreenShotCurrentStep(u, "verificarGridResultados");

		return resultados;
		
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
	 * Metodo para obtener el ID del tipo de pedido
	 * @param nomTipoPed (String): nombre del tipo de pedido a obtener el ID
	 * @return String con el ID del tipo de pedido
	 * @author Andrea Contreras
	 */
	public String obtenerIdTipoPedidoGrid(String nomTipoPed) {

		boolean exist = false;
		
		String locator = String.format(StrNomTipoPedGrid, nomTipoPed);
		By xpath = By.xpath(locator);
		WebElement element = u.getDriver().findElement(xpath);		
		exist = existeRegistroGrid(xpath);
		String id = ""; 
		
		if(exist == true) {
			element = element.findElement(By.xpath(".//preceding-sibling::td[2]/a"));
			id = element.getText();
		}
			
		return id;
		
	}
	


	
	/**
	 * Metodo que valida se encuentre el tipo de pedido en el Grid Principal
	 * @param nomTipoPed (String): nombre del tipo de pedido a buscar
	 * @return True o false si se encuentra el tipo de pedido
	 * @author Andrea Contreras
	 */
	public Boolean existeNombreTipoPedidoGrid(String nomTipoPed) {

		boolean exist = false;
		u.hardWait(2);
		String locator = String.format(StrNomTipoPedGrid, nomTipoPed);
		By xpath = By.xpath(locator);		
		exist = existeRegistroGrid(xpath);	
		
		if(exist == true) {
			WebElement element = u.getDriver().findElement(xpath);
			element = element.findElement(By.xpath(".//ancestor::tr"));
			
			m.highlight(element);
		}
		
		testCase.addScreenShotCurrentStep(u, "existeTipoPedidoGrid");
		
		return exist;
		
	}
	
	
	/**
	 * Metodo que valida se encuentre el tipo de pedido en el Grid Principal
	 * @param nomTipoPed (String): nombre del tipo de pedido a buscar
	 * @return True o false si se encuentra el tipo de pedido
	 * @author Andrea Contreras
	 */
	public Boolean existeIdTipoPedidoGrid(String id) {

		boolean exist = false;
		
		String locator = String.format(StrIdTipoPedGrid, id);
		By element = By.xpath(locator);
		
		exist = existeRegistroGrid(element);
		
		if(exist == true) {
			u.highLight(element);
		}
		
		testCase.addScreenShotCurrentStep(u, "existeTipoPedidoGrid");
		
		return exist;
		
	}
	
	
	/**
	 * Metodo que dar click en la flecha que desplega el comentario del tipo de pedido
	 * @param id (String): id del tipo de pedido
	 * @author Andrea Contreras
	 */
	public void desplegarComentarios(String id) {
	
		String locator = String.format(StrIdTipoPedGrid2, id);
		By element = By.xpath(locator);
		
		boolean exist = existeRegistroGrid(element);
		
		if (exist == true) {
			u.highLight(element);
			testCase.addScreenShotCurrentStep(u,"comentario2");
			u.click(element);
			u.hardWait(1);
			testCase.addScreenShotCurrentStep(u,"comentario2");
			u.unhighLight(element);
		}
		
	}
	
	
	/**
	 * Metodo para validar que exista el campo 'Comentario'
	 * @param id (String): id del tipo de pedido
	 * @author Andrea Contreras
	 */
	public boolean visualizarCampoComentario() {
	
		boolean visualiza = m.existElement(xmlComentarioDesplegadoR);
		
		if (visualiza == true) {
			u.highLight(xmlComentarioDesplegadoR);
		}
		
		testCase.addScreenShotCurrentStep(u,"visComentario");
		
		return visualiza;
		
	}
	
	
	/**
	 * Metodo para dar click en el icono 'Imagen' del tipo de pedido indicado
	 * @param pop (String): nombre del tipo de pedido
	 * @author Andrea Contreras
	 */
	public void  clickImagenIconPedido(String pedido) {

		String locator = String.format(StrImagenPed, pedido);
		By element = By.xpath(locator);									
		
		boolean exist = existeRegistroGrid(element);
		
		if (exist == true) {
			u.highLight(element);	
			testCase.addScreenShotCurrentStep(u, "clickImg");
			u.click(element);
			testCase.addScreenShotCurrentStep(u, "clickImg");
			u.unhighLight(element);
		}	
		
	}
	
	/**
	 * Metodo para dar click en el icono 'Imagen' del tipo de pedido indicado
	 * @param pop (String): nombre del tipo de pedido
	 * @author Andrea Contreras
	 */
	public void  clickImagenIconPedido() {

		boolean exist = existeRegistroGrid(xmlImageR);
		
		if (exist == true) {
			u.highLight(xmlImageR);	
			testCase.addScreenShotCurrentStep(u, "clickImg");
			u.click(xmlImageR);
			testCase.addScreenShotCurrentStep(u, "clickImg");
			u.unhighLight(xmlImageR);
		}	
		
	}
	
	
	/**
	 * Metodo para dar click en el icono 'Editar' de alguno de los registros del Grid
	 * @param tipoPop (String): tipo de pop
	 * @author Andrea Contreras
	 **/
	public void clickEditarRegistro(String id) {
	
		String locator = String.format(StrEditPed, id);
		By element = By.xpath(locator);		
		
		boolean exist = existeRegistroGrid(element);
		
		if (exist == true) {
			
			u.highLight(element);	
			testCase.addScreenShotCurrentStep(u, "clickEdit");
			u.click(element);
			m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
			testCase.addScreenShotCurrentStep(u, "pantallaEdit");

		}	
	
	}
	
	
	
	
	/*
	 * METODOS - 'Filtros' y 'Grid Principal'
	 ******************************************************************************************/
    
	
	/**
	 * Metodo que valida si se limpiaron los filtros y los resultados de la busqueda
	 * @param tipoPed (String): nombre del tipo de pedido que se ingreso en el filtro
	 * @return True o false si se limpiaron los filtros y los resultados de la busqueda
	 * @author Andrea Contreras
	 */
	public Boolean validarLimpiezaFiltrosGrid(String tipoPed) {
	
		boolean validacion = false, filtros, grid;

		filtros = validarFiltrosVacios();
		grid = visualizarResultadosBusqueda(tipoPed);
				
		if (filtros == true && grid == false) {
			validacion = true;
		}
		
		System.out.print("\nvalidacion limpieza filtros y grid: "+validacion);

		return validacion;		
			
	}
	
	
	/*
	 * METODOS - Ventana 'Imagen tipo de pedido' 
	 ******************************************************************************************/
   
	
	/**
	 * Metodo para validar la informaci�n de la ventana modal al subir una imagen
	 * @return Regresa true si se muestra la ventana y la informaci�n o false si no se muestra
	 * @author Andrea Contreras
	 */
	public boolean validarVentanaSubirImagen() {
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		boolean validacion = false, ventana, imagen, btnCancelar, btnAceptar;

		ventana = m.existElement(XmlVentanaImagen);
		imagen = m.existElement(XmlImg);
		
		btnCancelar = m.existElement(XmlBtnCancelar);
		btnAceptar = m.existElement(XmlBtnAceptar);

		if (ventana == true && imagen == true && btnAceptar == true 
				&& btnCancelar == true) {
			validacion = true;
		}
		
		testCase.addScreenShotCurrentStep(u, "validarVentanaSubirImagen");

		return validacion;
	}
	
	
	/**
	* Metodo para cargar una imagen
	* @param path (String): Ruta donde se encuentra la imagen a cargar
	* @author Andrea Contreras
	*/
	public void uploadImg(String path) {
		
		testCase.addScreenShotCurrentStep(u, "clickSelArchivo");
		u.sendKeys(XmlLblSel_Archivo, path);
		testCase.addScreenShotCurrentStep(u, "clickSelArchivo");
	
	}
	
	/**
	 * Metodo para validar la informaci�n de la ventana modal despues de subir la imagen
	 * @return Regresa true si se muestra la ventana y la informaci�n o false si no se muestra
	 * @author Andrea Contreras
	 */
	public boolean validarImagenCargada(String imgNueva) {
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		boolean valInfo = false, ventana, imagen, nom, fecha, tam, btnCancelar, btnAceptar,
		valImg = false, validacion = false;
		String imgActual = "";
		String [] array = null;
		
		ventana = m.existElement(XmlVentanaImagen);
		imagen = m.existElement(XmlImg);
		
		testCase.addScreenShotCurrentStep(u, "validarVentanaImagen1");
		
		nom = m.existElement(XmlLblNombreImagen);
		fecha = m.existElement(XmlLblFechaImagen);
		tam = m.existElement(XmlLblTamanoImagen);
		
		btnCancelar = m.existElement(XmlBtnCancelar);
		btnAceptar = m.existElement(XmlBtnAceptar);	
		
		
		testCase.addScreenShotCurrentStep(u, "validarVentanaImagen2");
		
		if (ventana == true && imagen == true && nom == true && fecha == true 
				&& tam == true && btnAceptar == true && btnCancelar == true) {
			valInfo = true;
			array = u.getText(XmlLblNombreImagen).split(": ");
			imgActual = array[array.length-1];
			u.highLight(XmlImg);
		}
		
		if(imgNueva.equals(imgActual)) {
			valImg = true;
		}
		
		if(valImg == true && valInfo == true) {
			validacion = true;
		}
		
		

		return validacion;
	}
	
	
	/**
	 * Metodo para dar click en el boton 'Aceptar' de la ventana 'Imagen tipo de pedido'
	 * @author Andrea Contreras
	 */
	public void clickBtnAceptarImagen() {
		
		u.highLight(XmlBtnAceptar);
		testCase.addScreenShotCurrentStep(u, "clickAceptarImg");
		u.click(XmlBtnAceptar);		
		u.unhighLight(XmlBtnAceptar);

	}
	
	
	/**
	* Metodo para validar mensaje 'Imagen cargada exitosamente'
	* @return true si se muestra la imagen o false si no se muestra
	* @author Andrea Contreras
	*/
	public boolean validarMsgImgCargada() {
		
		m.explicitWaitVisibilityOfElement(xmlMsgCargaImg, Duration.ofSeconds(10));
		
		boolean msgExist = false;
		if (m.existElement(xmlMsgCargaImg)==true) {				
			msgExist = true;
		}
		
		testCase.addScreenShotCurrentStep(u, "msgImagenCargada");
		return msgExist;
	}
	

    


}
