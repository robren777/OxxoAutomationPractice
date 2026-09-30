package WEB.inventarioEspacios.om;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class AsociarPopProveedorIEP {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;

	/*
	 * Localizadores Pantalla 'Asociar POP a Proveedor' 
	 * Administracion > Asociar POP a Proveedor
	 ******************************************************************************************/

	/**
	 * Localizadores - Header de la P�gina
	 ******************************************************************************************/
	By xmlTituloPantalla = By.xpath("//div[@id='tituloEscritorio']/span");

	/**
	 * Localizadores - Apartado 'Relacionar un POP con un proveeedor'
	 ******************************************************************************************/

	By xmlFiltros = By.xpath("//form[@id='formFiltroProveedorPop']/div");

	// Lista desplegable 'Seleccione el proveedor'
	By xmlProveedor = By.xpath("//form[@id='formFiltroProveedorPop']/div[1]//div[1]/mat-form-field/div/div[1]");
	By xmlProveedorPanel = By.xpath("//div[@id='mat-select-2-panel']");
	By xmlProveedorOp = By.xpath("//div[@id='mat-select-2-panel']/mat-option/span");

	// Lista desplegable 'Seleccione el tipo de POP'
	By xmlTipoPop = By.xpath("//form[@id='formFiltroProveedorPop']/div[1]//div[2]/mat-form-field/div/div[1]");
	By xmlTipoPopPanel = By.xpath("//div[@id='mat-select-0-panel']");
	By xmlTipoPopOp = By.xpath("//div[@id='mat-select-0-panel']/mat-option/span");
	 By xmlTipoPopSelected = By.xpath("//form[@id='formFiltroProveedorPop']/div/div/div[2]//mat-select//span/span");

	// Input 'Nombre de POP'
	By xmlNombrePop = By.xpath("//form[@id='formFiltroProveedorPop']/div[1]//div[3]/mat-form-field/div/div[1]");
	By xmlNombrePopInput = By.xpath("//form[@id='formFiltroProveedorPop']/div[1]//div[@class='form-group-input row'][3]//child::input");	 


	// Grid 'POPs filtrados'
	By xmlLbPopsFiltrados = By.xpath("//mat-label[contains(text(),'POPs filtrados')]");
	By xmlSectionPF = By.xpath("//*[@id='formFiltroProveedorPop']/div[2]/div[1]/div[2]/section");
	By xmlPopsFiltrados = By.xpath("//div[@id='cdk-accordion-child-0']/div/form/div[2]/div[1]");
	By xmlPFSelectAll = By.xpath("//div[@id='cdk-accordion-child-0']/div/form/div[2]/div[1]/div[2]/section/span[1]/mat-checkbox/label/span[1]/input");
	By xmlPFOptions = By.xpath("//div[@id='cdk-accordion-child-0']/div/form/div[2]/div[1]/div[2]/section/span[2]//li//span[@class='mat-checkbox-label']");
	By xmlPFOp = By.xpath("//div[@id='cdk-accordion-child-0']/div/form/div[2]/div[1]/div[2]/section/span[2]//li");
	By xmlPFOpScroll = By.xpath("//*[@id='formFiltroProveedorPop']/div[2]/div[1]/div[2]/section//ul/div");	
	
	String StrPanelPFOp = "//div[@id='cdk-accordion-child-0']/div/form/div[2]/div[1]/div[2]/section/span[2]/ul/div/li/mat-checkbox/label/span[2][contains(text(),'%s')]";
	
	
	// Grid 'POPs Asociados'
	// By xmlLbPlazasAsociadas = By.xpath("//mat-label[contains(text(),'POPs Asociados')]");
	By xmlSectionPA = By.xpath("//*[@id='formFiltroProveedorPop']/div[2]/div[2]/div[2]/section");
	By xmlPopsAsociados = By.xpath("//div[@id='cdk-accordion-child-0']/div/form/div[2]/div[2]");
	By xmlPAOptions = By.xpath("//div[@id='cdk-accordion-child-0']/div/form/div[2]/div[2]/div[2]/section//p");// en
	By xmlPAOpScroll = By.xpath("//*[@id='formFiltroProveedorPop']/div[2]/div[2]/div[2]/section//div");
	
	String StrPanelPAOp = "//div[@id='cdk-accordion-child-0']/div/form/div[2]/div[2]/div[2]/section//p[contains(text(),'%s')]";
	String StrPanelPAOpIcon = "//div[@id='cdk-accordion-child-0']/div/form/div[2]/div[2]/div[2]/section//p[contains(text(),'%s')]/mat-icon";

	
	// Botones 'Guardar', 'Cancelar' y 'Eliminar'
	
	By xmlDivBtn = By.xpath("//div[@class='row div-izquierda']");
	By xmlGuardarBtn = By.xpath("//form[@id='formFiltroProveedorPop']//span[contains(text(),'Guardar')]//parent::button");
	By xmlCancelarBtn = By.xpath("//form[@id='formFiltroProveedorPop']//span[contains(text(),'Cancelar')]//parent::button");
	By xmlEliminarBtn = By.xpath("//form[@id='formFiltroProveedorPop']//span[contains(text(),'Eliminar')]//parent::button");

	/**
	 * Localizadores - Apartado 'Consulta de POPs asociados'
	 ******************************************************************************************/
	By xmlLbConsultaPopAsociado = By.xpath("//mat-label[contains(text(),'Consulta de POPs asociados')]");

	// Localizadores del header de la tabla
	By xmlGridRows = By.xpath("//div[@class='contenedor']/div[2]/table/tbody/tr");
	
	String StrNumPops = "//div[@class='contenedor']/div[2]/table/tbody/tr/td[3][contains(text(),'%s')]/following-sibling::td[1]";
	String StrGridOp = "//div[@class='contenedor']/div[2]/table/tbody/tr/td[contains(text(),'%s')]";
	String StrGridOpEdit = "//div[@class='contenedor']/div[2]/table/tbody/tr/td[contains(text(),'%s')]/following-sibling::td/mat-icon";

	
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


	/**
	 * Localizadores - Ventana '�Desea guardar los cambios aplicados?'
	 ******************************************************************************************/

	By xmlPopUpGuardar = By.xpath("//mat-dialog-container//child::h5[contains(text(),'�Desea guardar los cambios aplicados?')]");
	By xmlCancelarCambios = By.xpath("//mat-dialog-container//app-dialog-confirm/div[2]/div/div/button");
	By xmlGuardarCambios = By.xpath("//mat-dialog-container//app-dialog-confirm/div[2]/div/div/div/button");

	By xmlMsgAsocExitosa = By.xpath("//div[@class='cdk-overlay-pane']//descendant::span[contains(text(),'El POP y Proveedor han sido asociados correctamente')]");

	/**
	 * Localizadores - Ventana '�Esta seguro que desea eliminar el/los registro(s) seleccionado(s)?'
	 ******************************************************************************************/

	By xmlPopUpEliminar = By.xpath("//mat-dialog-container//child::h5[contains(text(),'�Esta seguro que desea eliminar el/los registro(s) seleccionado(s)?')]");
	By xmlCancelarPopUpEliminar = By.xpath("//mat-dialog-container//app-dialog-confirm/div[2]/div/div/button");
	By xmlConfirmarPopUpEliminar = By.xpath("//mat-dialog-container//app-dialog-confirm/div[2]/div/div/div/button");

	By xmlMsgAsocEliminada = By.xpath("//div[@class='cdk-overlay-pane']//descendant::span[contains(text(),'La asociaci�n POP - Proveedor se ha eliminado correctamente')]");
	

	/*
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase AsociarPopProveedorIEP
	 * @author Andrea Contreras
	 * @param u (SeleniumUtil): objeto de la clase 
	 * @param testCase (testCase): objeto de la clase
	 */
	public AsociarPopProveedorIEP(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);
	}

	/*
	 * METODOS - Apartado 'Relacionar un POP con un proveeedor'
	 ******************************************************************************************/

	/**
	 * M�todo que valida se muestren los filtros de Relacionar un POP con sus
	 * respectivas plazas
	 * 
	 * @return Regresa true si se muestran los fitros o false si no se muestran
	 * @author Andrea Contreras
	 */
	public Boolean visualizarFiltros() {

		boolean validacion = false, prov, tipoPop, nomPop;

		u.mouseOver(xmlFiltros);
		prov = m.isEnable(xmlProveedor);
		tipoPop = m.isEnable(xmlTipoPop);
		nomPop = m.isEnable(xmlNombrePop);

		if (prov == true && tipoPop == true && nomPop == true) {
			validacion = true;
		}

		return validacion;
	}

	/**
	 * M�todo que valida se muestren las tablas de Relacionar un POP con sus
	 * respectivas plazas 'Plazas Filtradas' y 'Plazas Asociadas'
	 * 
	 * @return Regresa true si se muestran las tablas o false si no se muestran
	 * @author Andrea Contreras
	 */
	public Boolean visualizarTablas() {

		boolean validacion = false, areaPF, areaPA;

		// u.mouseOver(xmlPopsFiltrados);
		areaPF = m.isEnable(xmlSectionPF);
		areaPA = m.isEnable(xmlSectionPA);

		if (areaPF == true && areaPA == true) {
			validacion = true;
		}

		return validacion;
	}

	/**
	 * M�todo que valida se muestren la pantalla 'Asociar POP a Plazas'
	 * 
	 * @return Regresa true si se muestran la pantalla o false si no se muestran
	 * @author Andrea Contreras
	 */
	public Boolean visualizarPantalla() {

		boolean validacion = false;
		String pantallaActual = u.getText(xmlTituloPantalla);

		if (pantallaActual.contains("Asociar POP a Proveedor")) {
			validacion = true;
		}

		return validacion;
	}

	/**
	 * M�todo que valida se muestren los campos de Relacionar un POP con sus
	 * respectivas plazas y Relacionar un POP con sus respectivas plazas Consulta de
	 * POPs asociados con Selenium
	 * 
	 * @param option (String): opci�n de pop a seleccionar
	 * @return Regresa true si se muestran los campos o false si no se muestran
	 * @author Andrea Contreras
	 */
	public Boolean visualizarCampos() {

		m.explicitWaitToBeInvisible(Duration.ofSeconds(50));

		boolean validacion = false;
		boolean pantalla = visualizarPantalla();
		boolean filtros = visualizarFiltros();

		if (pantalla == true && filtros == true) {
			validacion = true;
		}

		u.highLight(xmlFiltros);
		testCase.addScreenShotCurrentStep(u, "verificarCampos");
		u.unhighLight(xmlFiltros);

		return validacion;
	}

	/**
	 * Metodo para seleccionar el proveedor
	 * 
	 * @param proveedor (String): proveedor a seleccionar
	 * @return Regresa true si se selecciono la opci�n o false si no se selecciono
	 * @author Andrea Contreras
	 */
	public boolean selectProveedor(String proveedor) {

		boolean existOption = false;
		WebElement element = m.explicitWaitToBeClickable(xmlProveedor, Duration.ofSeconds(30));
		u.hardWait(2);
		element.click();
		List<WebElement> allOptions = u.getDriver().findElements(xmlProveedorOp);
		for (WebElement option : allOptions) {
			if (option.getText().equals(proveedor)) {
				u.hardWait(1);
				option.click();
				u.hardWait(1);
				existOption = true;
				break;
			}
		}

		m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
		u.highLight(xmlProveedor);
		testCase.addScreenShotCurrentStep(u, "selectProveedor");
		u.unhighLight(xmlProveedor);
		return existOption;

	}

	/**
	 * Metodo para seleccionar el tipo de pop
	 * 
	 * @param tipoPop (String): tipo de pop a seleccionar
	 * @return Regresa true si se selecciono la opci�n o false si no se selecciono
	 * @author Andrea Contreras
	 */
	public boolean selectTipoPop(String tipoPop) {

		u.scrollToElement(xmlTipoPop);
		// u.mouseOver(xmlTituloPantalla);

		boolean existOption = false;
		WebElement element = m.explicitWaitToBeClickable(xmlTipoPop, Duration.ofSeconds(30));
		u.hardWait(2);
		element.click();
		List<WebElement> allOptions = u.getDriver().findElements(xmlTipoPopOp);
		for (WebElement option : allOptions) {
			if (option.getText().equals(tipoPop)) {
				u.hardWait(1);
				option.click();
				u.hardWait(1);
				existOption = true;
				break;
			}
		}

		m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
		u.highLight(xmlTipoPop);
		testCase.addScreenShotCurrentStep(u, "selectTipoPop");
		u.unhighLight(xmlTipoPop);
		return existOption;

	}
	
	/**
	 * Metodo para validar si el Tipo de POP selecionado es igual que el esperado
	 * @param tipoPop (String): tipo de pop esperado
	 * @return True si el tipo de POP seleccionado es igual al esperado o false de lo contrario
	 * @author Andrea Contreras
	 **/
	public boolean validarTipoPopSeleccionado(String tipoPop) {
		
		u.scrollToElement(xmlTipoPop);
		testCase.addScreenShotCurrentStep(u, "tipoPopSeleccionado");
		boolean validacion = u.getText(xmlTipoPopSelected).equals(tipoPop);
		System.out.print("\nvalidacion tipoPop-"+validacion);
	
		return validacion;
	}
	

	/**
	 * Metodo para ingresar un valor en el campo 'Nombre de POP'
	 * @param nomPop (String): nombre del pop a buscar
	 * @author Andrea Contreras
	 */
	public void typeNombrePop(String nomPop) {

		u.scrollToElement(xmlNombrePop);
		u.highLight(xmlNombrePop);
		u.sendKeys(xmlNombrePopInput, nomPop);
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "typePrecio");
		u.unhighLight(xmlNombrePop);
	}

	/**
	 * M�todo que valida si el Grid 'Consulta de POPs asociados' y los paneles 
	 * 'POPs Filtrados' y 'POPs Asociados' muestran resultados	 
	 * @param numPopsBD (int): cantidad de pops que se encuentran asociados al proveedor en la Base de Datos
	 * @return True si 
	 * @author Andrea Contreras
	 */
	public boolean validarGridPaneles(int cantPops) {
		
		boolean validacion = false;
		
		if (visualizarPaneles() == true && visualizarListaPopsAsocProveedor(cantPops) == true) {
			validacion = true;
		}
		
		return validacion;
		
	}

	/**
	 * Metodo para validar si se muestran resultados en los paneles despu�s de
	 * realizar una b�squeda
	 * @return Regresa true si se muestran resultados o false si no se muestran
	 * @author Andrea Contreras
	 */
	public boolean visualizarPaneles() {

		 m.scrollToElement(xmlLbPopsFiltrados);
		 u.click(xmlLbPopsFiltrados);
		boolean exists = false;
		if ((m.existMultipleElements(xmlPFOptions) == true && m.existMultipleElements(xmlPAOptions) == true)
				|| (m.existMultipleElements(xmlPFOptions) == true && m.existMultipleElements(xmlPAOptions) == false)) {
			exists = true;
		}
		System.out.print("\nValidar paneles: " + exists );
		testCase.addScreenShotCurrentStep(u, "visualizarPanelesResultados");
		return exists;
	}
	
	
	/**
	 * Metodo para validar que se muestren resultados del filtro 'Nombre de POP' en 
	 * el panel 'POPs Filtrados' y se muestren en orden alfabetico	 
	 * @return true si se muestran resultados o false si no se muestran
	 * @author Andrea Contreras
	 */
	public boolean visualizarPOPsFiltrados(String nomPop) {
       int cont = 0;
		boolean validacion = true;
		List<WebElement> popsResults = u.getDriver().findElements(xmlPFOptions);
		for (WebElement pop : popsResults) {
			if (!pop.getText().toLowerCase().contains(nomPop.toLowerCase())) {
				validacion = false;
			}
			System.out.print("-" + cont + "");
			cont++;
		}

		System.out.print("\nSe visualizan pops filtrados: " + validacion );
		testCase.addScreenShotCurrentStep(u, "visualizarPOPsFiltrados");
		return validacion;
	}
	
	 /**
		* M�todo para seleccionar N cantidad de POPs en la secci�n de 'POPs filtrados' 
		* @param valores(ArrayList<String>): ArrayList de las plazas obtenidas del data provider
		* @return Regresa true si se selecciono la opci�n o false si no se selecciono
		* @author Andrea Contreras
		*/
		public boolean selectPOPsFiltrados(ArrayList<String> valores) {

			boolean isSelected = false;	
			int contPlazas = 0;
			
			m.scrollToElement(xmlLbPopsFiltrados);
			u.click(xmlLbPopsFiltrados);
			u.mouseOver(xmlSectionPF);
			/*
			List<WebElement> popsResults = u.getDriver().findElements(xmlPFOptions);
			for(int i = 0; i < valores.size(); ++i){
			  for (WebElement pop : popsResults) {
				  if (pop.getText().toLowerCase().contains(valores.get(i).toLowerCase())) {
					    testCase.addScreenShotCurrentStep(u, "selectPF1_"+i);				
			        	pop.click();
			        	testCase.addScreenShotCurrentStep(u, "selectPF2_"+i);
			        	contPlazas++;
				  }
				  
			    }
			}
		*/
			
			for(int i = 0; i < valores.size(); ++i){
				String locator = String.format(StrPanelPFOp,valores.get(i));
				By xpath = By.xpath(locator);
			    if(m.existElement(xpath)) {
			    	m.scrollToElement(xpath);
			    	u.highLight(xpath);
					testCase.addScreenShotCurrentStep(u, "selectPF1_"+i);				
		        	u.click(xpath);
		        	//testCase.addScreenShotCurrentStep(u, "selectPF2_"+i);
		        	contPlazas++;
			      }
			}		
			
			if(contPlazas==valores.size()) {
				isSelected = true; 
			}
			
			System.out.print("\nSe seleccionaron las opciones: " + isSelected );
			
			return isSelected;	
			
		}
		
		   /**
		* Metodo para validar que los POPs se muestren asociadas en la secci�n 'POPs Asociados'
		* @param valores(ArrayList<String>): ArrayList de los POPs obtenidos del data provider
		* @return True si se muestran asociados o false si no se muestran asociados
		* @author Andrea Contreras
		*/
		public boolean validarPopsAsociados(ArrayList<String> valores) {
			
			boolean exist = false;
			int contPlazas = 0;
			u.mouseOver(xmlSectionPA);
			
			for(int i = 0; i < valores.size(); ++i){
				String locator = String.format(StrPanelPAOp,valores.get(i));
				By xpath = By.xpath(locator);
			    if(m.existElement(xpath)) {		    	
			    	 m.scrollToElement(xpath);
				     u.highLight(xpath);			      
				     testCase.addScreenShotCurrentStep(u, "validarPA");
				     contPlazas++;
			      }
			}
			
			if(contPlazas==valores.size()) {
				exist = true; 
			}
			
			return exist;	
			
		}
		
	
	   /**
		* Metodo para dar click en el boton 'Guardar'
		* @author Andrea Contreras
		*/
		public void clickGuardarBtn(){ 
				
			m.explicitWaitToBeClickable(xmlGuardarBtn,Duration.ofSeconds(10));
			u.highLight(xmlGuardarBtn);
			testCase.addScreenShotCurrentStep(u, "btnGuardar");
			u.click(xmlGuardarBtn);		
			testCase.addScreenShotCurrentStep(u, "clickGuardar");
			u.unhighLight(xmlGuardarBtn);
		
		}
		
		
		   /**
			* Metodo para dar click en el boton 'Eliminar'
			* @author Andrea Contreras
			*/
			public void clickEliminarBtn(){ 
					
				m.explicitWaitToBeClickable(xmlEliminarBtn,Duration.ofSeconds(10));
				u.highLight(xmlEliminarBtn);
				testCase.addScreenShotCurrentStep(u, "btnGuardar");
				u.click(xmlEliminarBtn);		
				testCase.addScreenShotCurrentStep(u, "clickGuardar");
				u.unhighLight(xmlEliminarBtn);
			
			}
		
	   /**
	    * Metodo para validar si el boton 'Eliminar' esta habilitado
	    * @return True si esta habilitado o False si no esta habilitado
	    * @author Andrea Contreras
	    */
		public boolean isEliminarBtnEnable() {
			u.scrollToElement(xmlDivBtn);
			u.mouseOver(xmlDivBtn);
			
			boolean isActive = false;	

			if(m.existElement(xmlEliminarBtn)) {
				u.highLight(xmlEliminarBtn);
				isActive = true;
			}
			
			testCase.addScreenShotCurrentStep(u, "isDeleteBtnActive");

			return isActive;	
			
		}
		

		public boolean validarActualizacionDatos(String popActuaizado) {
			
			boolean validacion = false;
			
			/*if(validarPopActualSeleccionado(popActuaizado)==true&&validarPaneles()==true) {
				validacion = true;
			}*/
			
			return validacion;
			
		}
		
		
		
		public boolean editarPopProveedor(ArrayList<String> valores, String accion) {
			
			u.scrollToElement(xmlLbPopsFiltrados);
			int cont = 0;	
			List<WebElement> allOptions; 

			WebElement element = null;
	       
			for(int i = 0; i < valores.size(); ++i){
				
				switch(accion.toUpperCase()) {
				
				  case "AGREGAR":
					  System.out.print("\nopcion agregar");
					  u.hardWait(1);
					  allOptions = u.getDriver().findElements(xmlPFOp);
					  u.hardWait(2);
					  System.out.print("\npop "+valores.get(i));
					  for(WebElement option:allOptions) {
						  System.out.print("\nentro al ciclo");
						  System.out.print("--"+option+" ="+ valores.get(i).replace(" ", ""));
						  if(option.getText().equals(valores.get(i).replace(" ", ""))) {
							  element = option;
							  System.out.print("\nse encontro el elemento");
							  break;
						  }
					  }
					  m.highlight(element);
					  element = element.findElement(By.xpath(".//span[@class='mat-checkbox-label']"));
					  break;
					  
				  case "ELIMINAR":
					  System.out.print("\nopcion eliminar");
					  u.hardWait(1);
					  allOptions = u.getDriver().findElements(xmlPAOptions);
					  u.hardWait(2);
					  System.out.print("\npop "+valores.get(i));
					  for(WebElement option:allOptions) {
						  System.out.print("\nentro al ciclo");
						  String op = option.getText().replace(" close", "");
						  System.out.print("--"+op+" ="+ valores.get(i));
						  if(op.equals(valores.get(i).replace(" ", ""))) {
							  element = option;
							  System.out.print("\nse encontro el elemento");
							  break;
						  }
					  }
					  m.highlight(element);
					  element = element.findElement(By.xpath("./mat-icon"));

					  break;
					  
				  default:
					  break;
				
				}				
				       
			    if(element != null) {
			    	System.out.print("\nElemento no es null ");		    	
					testCase.addScreenShotCurrentStep(u, "selectPF1_"+i);	
					element.click();
					cont++;
		        	testCase.addScreenShotCurrentStep(u, "selectPF2_"+i);
			      }
			}	
			
			System.out.print("\nPops seleccionados: "+cont);
			
			boolean validar = cont==valores.size();
		
			return validar;
			
		}
		

	/*
	 * METODOS - Apartado 'Consulta de POPs asociados'
	 ******************************************************************************************/

	/**
	 * Metodo para validar que se muestra el listado de las asociaciones asignadas a
	 * ese proveedor (consulta de proveedores)
	 * @param numPopsBD (int): cantidad de pops que se encuentran asociados al proveedor en la Base de Datos
	 * @return Regresa true si se muestra el listado y concuerda con la BD
	 * @author Andrea Contreras
	 **/
	public Boolean visualizarListaPopsAsocProveedor(int numPopsBD) {

		u.scrollToElement(xmlLbConsultaPopAsociado);
		u.mouseOver(xmlLbConsultaPopAsociado);

		boolean validacion = false;
		int numPopsActual = 0;
		List<WebElement> numPops;

		boolean exist = m.existMultipleElements(xmlGridRows);

		if (exist) {
			numPops = u.getDriver().findElements(xmlGridRows);
			numPopsActual = numPops.size();
		}

		if (numPopsActual == numPopsBD) {
			validacion = true;
		}

		testCase.addScreenShotCurrentStep(u, "visualizarPopsAsocProv");

		return validacion;
	}

	
	/**
	 * Metodo para obtener el numero actual de POPs asociados al tipo de POP y proveedor
	 * @param tipoPop (String): tipo de pop
	 * @return numeroPOP (int): numero de pops asociados al tipo de pop y proveedor 
	 * @author Andrea Contreras
	 **/
	public int obtenerNumeroPopsAsocProv(String tipoPop) {	
		m.explicitWaitInvisibilityOfElement(xmlMsgAsocExitosa, Duration.ofSeconds(10));				
		int numPlaza = 0;
		boolean click = false;
		By xpath = By.xpath(String.format(StrNumPops,tipoPop));		
		
		do {
			
			m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
			if(m.existElement(xpath)==true) {
			
			u.highLight(xpath);
			u.click(xpath);
			numPlaza = Integer.valueOf(u.getText(xpath));
			testCase.addScreenShotCurrentStep(u, "numPlazas");
			u.unhighLight(xpath);
			click = true;
			System.out.print("\n-encontro el pop-");			
			
			}
			
			if(m.existElement(xpath)==false) {
				
			if((m.existElement(xmlBtnNextPageDisable)==true)&&(m.existElement(xmlBtnPreviousPageDisable)==true)) {
				numPlaza = 0;
				click = true;
				System.out.print("\n-no existen plazas en el pop, se realiza break del while-");			
				break;				
				
			}else if(m.existElement(xmlBtnFirstPageDisable)==true && m.existElement(xmlBtnNextPageDisable)==false) {
				
    			  u.click(xmlBtnNextPage);
    			  m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
    			  click = false;
    			  System.out.print("\n-cambia a la siguiente pagina para seguir buscando-");
    			
    			}/*else if (m.existElement(xmlBtnLastPageDisable)==false) {

    				click = false;
    				System.out.print("-no se encontro el tipo de pop-");
    				break;
    				
    	        }*/
    		}		
			
		}while(!click);
		
		System.out.print("\nNUM PLA-"+numPlaza);
		return numPlaza;	
		
	}
	
	/**
	 * Metodo para seleccionar N cantidad de registros del Grid
	 * @param tipoPop (String): tipo de pop
	 * @return numeroPOP (int): numero de pops asociados al tipo de pop y proveedor 
	 * @author Andrea Contreras
	 **/
	public boolean selectTipoPopsAsocGrid(ArrayList<String> tipoPop) {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.scrollToElement(xmlLbConsultaPopAsociado);
		u.mouseOver(xmlLbConsultaPopAsociado);	
	
		boolean ciclo = false, validacion = false;	
		int selectedTP = 0;
		
		for(int i = 0; i < tipoPop.size(); ++i){
			
			String locator = String.format(StrGridOp, tipoPop.get(i));
			By element = By.xpath(locator);								
			
			do {
				
				if(m.existElement(element)==true) {
				
				u.highLight(element);
				String checkStr = locator+"/preceding-sibling::td/mat-checkbox";			
				u.click(By.xpath(checkStr));
				u.hardWait(1);
				testCase.addScreenShotCurrentStep(u, "checkPopAsociado");
				u.unhighLight(element);
				ciclo = true;
				selectedTP+=1;
				System.out.print("\n-encontro el pop-");			
				
				}
				
				if(m.existElement(element)==false) {
					
				if((m.existElement(xmlBtnNextPageDisable)==true)&&(m.existElement(xmlBtnPreviousPageDisable)==true)) {
					System.out.print("\n-no existen plazas en el pop, se realiza break del while-");			
					ciclo = true;				
					
				}else if(m.existElement(xmlBtnFirstPageDisable)==true && m.existElement(xmlBtnNextPageDisable)==false) {
					
	    			  u.click(xmlBtnNextPage);
	    			  m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
	    			  System.out.print("\n-cambia a la siguiente pagina para seguir buscando-");
	    			
	    		    }else {
	    		    	ciclo = true;
	    		    }
				}
				
				
			}while(!ciclo);
		
		}		
		
		if(selectedTP==tipoPop.size()) {
			validacion = true;
		}
		
		return validacion;	
		
	}
	
	/**
	 * Metodo para seleccionar 1 registros del Grid
	 * @param tipoPop (String): tipo de pop
	 * @return numeroPOP (int): numero de pops asociados al tipo de pop y proveedor 
	 * @author Andrea Contreras
	 **/
	public boolean selectTipoPopsAsocGrid(String tipoPop) {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.scrollToElement(xmlLbConsultaPopAsociado);
		u.mouseOver(xmlLbConsultaPopAsociado);	
	
		boolean validacion = false;

		String locator = String.format(StrGridOp, tipoPop);
		By element = By.xpath(locator);								
		
		do {
			
			if(m.existElement(element)==true) {
			
			u.highLight(element);
			String checkStr = locator+"/preceding-sibling::td/mat-checkbox";			
			u.click(By.xpath(checkStr));
			u.hardWait(1);
			testCase.addScreenShotCurrentStep(u, "checkPopAsociado");
			u.unhighLight(element);
			validacion = true;
			System.out.print("\n-encontro el pop-");			
			
			}
			
			if(m.existElement(element)==false) {
				
			if((m.existElement(xmlBtnNextPageDisable)==true)&&(m.existElement(xmlBtnPreviousPageDisable)==true)) {
				System.out.print("\n-no existen plazas en el pop, se realiza break del while-");			
				validacion = true;				
				
			}else if(m.existElement(xmlBtnFirstPageDisable)==true && m.existElement(xmlBtnNextPageDisable)==false) {
				
    			  u.click(xmlBtnNextPage);
    			  m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
    			  System.out.print("\n-cambia a la siguiente pagina para seguir buscando-");
    			
    		    }else {
    		    	break;
    		    }
			}
			
			
		}while(!validacion);	
			
		
		return validacion;	
		
	}
	
	/**
	 * Metodo para dar click en el icono 'Editar' de alguno de los registros del Grid
	 * @param tipoPop (String): tipo de pop
	 * @author Andrea Contreras
	 **/
	public void clickEditarRegistro(String tipoPop) {
		
		String locator = String.format(StrGridOp, tipoPop);
		By element = By.xpath(locator);
		boolean isSelected = false;
		
		do {
			
			if(m.existElement(element)==true) {
			
			u.highLight(element);		
			locator += "/following-sibling::td/mat-icon";
			u.highLight(By.xpath(locator));
			testCase.addScreenShotCurrentStep(u, "editIcon");
			u.click(By.xpath(locator));
			m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
			isSelected = true;
		    System.out.print("\n-encontro el pop-");			
			
			}
			
			if(m.existElement(element)==false) {
				
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
		
		u.scrollToElement(xmlTipoPop);
		testCase.addScreenShotCurrentStep(u, "clickEditPopAsoc");
			
		
	}
	

	
	
	
	/*
	 * METODOS - Ventana '�Desea guardar los cambios aplicados?'
	 ******************************************************************************************/

	/**
	* M�todo para validar que se muestra la ventana '�Desea guardar los cambios aplicados?'
	* @return True si se muestra la ventana o false si no se muestra
	* @author Andrea Contreras
	*/
	public boolean validarPopUpGuardarCambios(){
		
		boolean exist = m.existElement(xmlPopUpGuardar);
		testCase.addScreenShotCurrentStep(u, "msgGuardarCambios");
		return exist;
		
	}
	
	/**
	* Metodo para dar click en el bot�n 'Confirmar' de la ventana '�Desea guardar los cambios realizados?'
	* @author Andrea Contreras
	*/
	public void clickBtnConfirmarPopUpGuardar(){ 
	
		m.explicitWaitToBeClickable(xmlGuardarCambios,Duration.ofSeconds(10));
		u.highLight(xmlGuardarCambios);
		testCase.addScreenShotCurrentStep(u, "clickConfirmar");
		u.click(xmlGuardarCambios);		
		testCase.addScreenShotCurrentStep(u, "msgConfirmar");
	}
	
	
	/**
	* Metodo para validar que se muestra el mensaje 'El POP y Proveedor han sido asociados correctamente'
	* * @author Andrea Contreras
	*/
	public boolean validarMsgPOPyProveedorAsoc() {

		m.explicitWaitVisibilityOfElement(xmlMsgAsocExitosa, Duration.ofSeconds(20));
		boolean msgExist = false;
		if (m.existElement(xmlMsgAsocExitosa)==true) {				
			msgExist = true;
		}
		testCase.addScreenShotCurrentStep(u, "msgPOPyProvAsoc");
		System.out.print("\nExiste mensaje-"+msgExist);
		return msgExist;
	}
	
	
	
	/*
	 * METODOS - Ventana '�Esta seguro que desea eliminar el/los registro(s) seleccionado(s)?'
	 ******************************************************************************************/

	
	/**
	* M�todo para validar que se muestra la ventana '�Desea guardar los cambios aplicados?'
	* @return True si se muestra la ventana o false si no se muestra
	* @author Andrea Contreras
	*/
	public boolean validarPopUpEliminarRegistros(){
		
		boolean exist = m.existElement(xmlPopUpEliminar);
		testCase.addScreenShotCurrentStep(u, "msgEliminarRegistros");
		return exist;
		
	}
	
	/**
	* Metodo para dar click en el bot�n 'Confirmar' de la ventana '�Desea guardar los cambios realizados?'
	* @author Andrea Contreras
	*/
	public void clickBtnConfirmarPopUpEliminar(){ 
	
		m.explicitWaitToBeClickable(xmlConfirmarPopUpEliminar,Duration.ofSeconds(10));
		u.highLight(xmlConfirmarPopUpEliminar);
		testCase.addScreenShotCurrentStep(u, "clickConfirmarElim");
		u.click(xmlConfirmarPopUpEliminar);		
		testCase.addScreenShotCurrentStep(u, "msgConfirmarElim");
	}
	
	/**
	* Metodo para validar que se muestra el mensaje 'La asociaci�n POP - Proveedor se ha eliminado correctamente'
	* * @author Andrea Contreras
	*/
	public boolean validarMsgAsocEliminada() {

		m.explicitWaitVisibilityOfElement(xmlMsgAsocEliminada, Duration.ofSeconds(20));
		boolean msgExist = false;
		if (m.existElement(xmlMsgAsocEliminada)==true) {				
			msgExist = true;
		}
		testCase.addScreenShotCurrentStep(u, "msgAsocEliminada");
		System.out.print("\nExiste mensaje eliminacion exitosa -"+msgExist);
		return msgExist;
	}
}
