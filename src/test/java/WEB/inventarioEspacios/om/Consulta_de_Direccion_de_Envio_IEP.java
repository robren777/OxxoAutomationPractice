package WEB.inventarioEspacios.om;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;


public class Consulta_de_Direccion_de_Envio_IEP {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;
	PedidosAdministracionIEP pedAdmin;
	/*
	 * Localizadores Pantalla Detalle - Consulta_de_Direccion_de_Envio_IEP
	 ******************************************************************************************/
	By xmlUsuario = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/p");
    By xmlNegocio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[1]");
    By xmlPerfil = By.xpath("");  
          
    
    By xmlMenusAd = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[2]/div");  // para admor oxxo
    By xmlhome = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[2]/div/mat-icon");
    By xmlimagen = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/img");
    					 
     By xmlMenusAdmon = By.xpath("//div[@class= \"texto-menu\"]/a[2]/div"); 	// para admor marca	
     							    //*[@id="sidenav"]/div/app-sidebar/mat-nav-list/div[3]/a[2]/div/label
    By xmlHeader = By.xpath("//mat-sidenav-content[@id='content-nav']/app-header");
  
    // Paso 6
    By xmlSmenuConsMov = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[3]/a/div/label");
    By xmlPlazaFlecha = By.xpath("//*[@id=\"mat-select-6\"]/div/div[2]");
       
    By xmlBtnNuevo = By.xpath("//*[@id=\"formFiltroDireccion\"]/div[2]/div/div[1]/button");
    By xmlPlaza = By.xpath("//*[@id=\"mat-select-value-7\"]/span");
    
    By xmlNomDir = By.xpath("//*[@id=\"mat-input-51\"]");
    By xmlCodigoP = By.xpath("//*[@id=\"mat-input-52\"]");
    By xmlCalle = By.xpath("//*[@id=\"mat-input-56\"]");
    By xmlNumero = By.xpath("//*[@id=\"mat-input-57\"]");
 
  
    By xmlEntreCalle = By.xpath("//*[@id=\"mat-input-58\"]");
    By xmlyEntreCalle = By.xpath("//*[@id=\"mat-input-59\"]");
    By xmlNomContacto = By.xpath("//*[@id=\"mat-input-60\"]");
    By xmlTelContacto = By.xpath("//*[@id=\"mat-input-61\"]");
    By xmlComentario = By.xpath("//*[@id=\"mat-input-62\"]");
    By xmlBtnGuardar = By.xpath("//*[@id=\"mat-tab-content-10-0\"]/div/button[1]/span[1]");
    By xmlConfirBtnGuardar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");
    By xmlMenuPedAdmin = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[3]/div");
    By xmlMenuPedAdminM = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[9]/div/label");
    By xmlConsultaMov = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div[1]/mat-list/div[13]/a/div/label");
    By xmlConsMovMarc = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[13]/a/div/label");
    								   //*[@id="sidenav"]/div/app-sidebar/mat-nav-list/div[3]/div[2]/mat-list/div[13]/a/div/label
    /*
     * Variables que reciben un parametro
     * *******************************************************************************************/
    String StrPlaza = ("//div[@id=\"mat-select-6-panel\"]/mat-option/span[contains(text(),'%s')]");
	
    /*
	 * Localizadores Pantalla Consulta de direcciones de env�o
	 ******************************************************************************************/
    
    By xmlTituloPrincipal = By.xpath("//*[@id=\"titulo_gral\"]");
    By xmlNombreDireccion = By.name("nombre");
    By xmlPlazaDireccion = By.xpath("//div[1]/div/div[2]/mat-form-field/div/div[1]/div/child::mat-select");
    By xmlListadoPlazas = By.xpath("//div[1]/div/div[2]/mat-form-field/div/div[1]/div/mat-select/div/div/span/span");
    By xmlTiendas = By.xpath("//div[1]/div/div[3]/mat-form-field/div/div[1]/div/child::mat-select");
    By xmlMatSelectOp = By.xpath("//div[@role='listbox']/mat-option/span");
    
    By xmlLimpiarBtn = By.xpath("//*[@id=\"formFiltroDireccion\"]/div[2]/div/div[2]/button");
    By xmlBuscarBtn = By.xpath("//*[@id=\"formFiltroDireccion\"]/div[2]/div/div[3]/button");
    By xmlEliminarBtn = By.xpath("//button/span[contains(text(), 'Eliminar')]");
    
    By xmlTable = By.xpath("//*[@id=\"content-nav\"]/app-address-list/div/div/table");
    By xmlTableTh = By.xpath("//*[@id=\"content-nav\"]/app-address-list/div/div/table/thead/tr/th");
    By xmlTableTr = By.xpath("//*[@id=\"content-nav\"]/app-address-list/div/div/table/tbody/tr");
    String tableTr = "//*[@id='content-nav']/app-address-list/div/div/table/tbody/tr[%s]/td/a";
    String xmlTablaTr = "//*[@id='content-nav']/app-address-list/div/div/table/tbody/tr[%s]";
    String xmlEditarTr = "//*[@id=\"content-nav\"]/app-address-list/div/div/table/tbody/tr[%s]/td[10]/mat-icon";
    String xmlSeleccionarTr = "//*[@id=\"content-nav\"]/app-address-list/div/div/table/tbody/tr[%s]/td[1]/mat-checkbox";
    By xmlDatosGenerales = By.xpath("//div[contains(text(), 'Datos generales')]");
    By xmlTiendasAsociadas = By.xpath("//div[contains(text(), 'Tiendas asociadas')]");
    
	String editarTr = "//table/tbody/tr[%s]/td[5]/mat-icon";
	String seleccionarTr = "//table/tbody/tr[%s]/td[1]/mat-checkbox";
    
	By xmlConfirmacionEliminar = By.xpath("//app-dialog-confirm/h5");
	
    /*
     * Localizadores Pantalla Nuevo
     ******************************************************************************************/
    
    By xmlPlazaNuevo = By.xpath("//div/mat-select");
    String xmlMatOption = "//mat-option/span[contains(text(), '%s')]";
    By xmlOpcionesMat = By.xpath("//mat-option/span");
    By xmlNomDireccion = By.xpath("//div[2]/mat-form-field/div/div/div/input");    
    By xmlCP = By.xpath("//div[3]/mat-form-field/div/div/div/input");
    By xmlEstado  = By.xpath("//fieldset[1]/mat-form-field");/*/div/div/div/input*/
    By xmlCiudad = By.xpath("//fieldset[2]/mat-form-field");
    By xmlColonia = By.xpath("//fieldset[3]/mat-form-field");
    
    By xmlCallePrinc = By.xpath("//div[1]/mat-form-field/div/div/div/input");
    By xmlEntCalle = By.xpath("//div[2]/div[3]/mat-form-field/div/div/div/input");
    By xmlYEntreCalle = By.xpath("//div[4]/mat-form-field/div/div/div/input");
    By xmlNomCont = By.xpath("//div[5]/mat-form-field/div/div/div/input");
    By xmlNumContacto = By.xpath("//div[6]/mat-form-field/div/div/div/input");
    
    By xmlElementos = By.xpath("//*[@id=\"mat-tab-content-10-0\"]/div/div");
    
    By xmlGuardarNuevoBtn = By.xpath("//div/button[1]");
    By xmlConfirmar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button");
    
    By xmlMensaje = By.xpath("//snack-bar-container/div/div/simple-snack-bar/span");
    
    //Tiendas asociadas
    
    By xmlTiendasExistentes = By.xpath("//*[@id=\"mat-tab-content-10-1\"]/div/div/div[1]");
    By xmlTiendaAsociadas = By.xpath("//*[@id=\"mat-tab-content-10-1\"]/div/div/div[2]");
    By xmlListaTiendasExistentes = By.xpath("//ul/div/li");
    String xmlTE = "//ul/div/li[%s]";
    String xmlTiendaExistente = "//ul/div/li[%s]/mat-checkbox";
    By xmlListaTiendasAsociadas = By.xpath("//section/span/div/p");
    String xmlTA = "//section/span/div/p[%s]";
    By xmlSeleccionarTodo = By.xpath("//span[contains(text(), 'Seleccionar todas')]");
    By xmlQuitarTodo = By.xpath("//span[contains(text(), 'Quitar todas')]");

    
    
    
	/**
	 * Constructor
	 ******************************************************************************************/
	public Consulta_de_Direccion_de_Envio_IEP(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);
		this.pedAdmin = new PedidosAdministracionIEP(u, testCase);
    }
	
	/**
	 * Método para llenar los datos de asignacion de direccion de envio y dar de alta
	 * 
	 * @author Manuel CUen
	 * @param Plaza, Direccion, Codigo Postal, Calle, Numero, Entre Calle, y Entre Calle, Nombre y Telefono del Contacto, Comentario
	 * @return ninguno
	 */
	public void clickMenuPedidoAdmin(String plaza,String direcc, String cp, String calle, String numero, String entreCalle, String yEntreCalle, String nomContacto, String telContacto, String comentario, String negocio) {

		if(negocio.toUpperCase().equals("MARCA")) {
			u.highLight(xmlMenuPedAdminM);
			u.click(xmlMenuPedAdminM);
		} else if(negocio.toUpperCase().equals("OXXO")) {
			u.highLight(xmlMenuPedAdmin);
			u.click(xmlMenuPedAdmin);
		}
		u.highLight(xmlSmenuConsMov);
		u.click(xmlSmenuConsMov);
		u.mouseOver(xmlHeader);
		u.hardWait(5);

		
		testCase.addScreenShotCurrentStep(u, "clickMenuPedidoAdmin");       
		u.hardWait(5);
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.highLight(xmlBtnNuevo);
		u.click(xmlBtnNuevo);

		// Datos Generales

		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.highLight(xmlPlazaFlecha);
		u.click(xmlPlazaFlecha);
		
		// Concatena La variable con el de dp
		By LocPlaza = By.xpath(String.format(StrPlaza, plaza));
		System.out.println(LocPlaza);
	    u.click(LocPlaza);
	    m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.highLight(xmlNomDir);
		u.sendKeys(xmlNomDir, direcc);
		u.highLight(xmlCodigoP);
		u.sendKeys(xmlCodigoP, cp);
		u.highLight(xmlCalle);
		u.sendKeys(xmlCalle, calle);
		u.getDriver().findElement(xmlNumero).clear();
		u.highLight(xmlNumero);
		u.sendKeys(xmlNumero, numero);
		u.highLight(xmlEntreCalle);
		u.sendKeys(xmlEntreCalle, entreCalle);
		u.highLight(xmlyEntreCalle);
		u.sendKeys(xmlyEntreCalle, yEntreCalle);
		u.highLight(xmlNomContacto);
		u.sendKeys(xmlNomContacto, nomContacto);
		u.highLight(xmlTelContacto);
		u.sendKeys(xmlTelContacto, telContacto);
		u.highLight(xmlComentario);
		u.sendKeys(xmlComentario, comentario);
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.highLight(xmlBtnGuardar);
		u.click(xmlBtnGuardar);
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.highLight(xmlConfirBtnGuardar);
		u.click(xmlConfirBtnGuardar);
		u.hardWait(5);
		
		testCase.addScreenShotCurrentStep(u, "clickMenuPedidoAdmin");
		u.hardWait(2);	
	}
	
	
	/**
	 * Método para seleccionar y dar click al Menu Admin y SubMenu Consulta de Movtos
	 * 
	 * @author Manuel CUen
	 * @param ninguno
	 * @return ninguno
	 */
	public void clickSeleccionMenuAdmon(String negocio) {
		
//		u.scrollToElement(xmlhome);
//		u.highLight(xmlhome);
		if(negocio.toUpperCase().equals("MARCA")) {
			u.mouseOver(xmlMenusAdmon);
//			u.highLight(xmlMenusAdmon);
			u.click(xmlMenusAdmon);
			
		} else if(negocio.toUpperCase().equals("OXXO")) {
			u.mouseOver(xmlMenusAd);
			u.highLight(xmlMenusAd);
			u.hardWait(2);
			u.click(xmlMenusAd);
		}
		
		u.highLight(xmlConsultaMov);
		u.click(xmlConsultaMov);
		u.hardWait(5);
		testCase.addScreenShotCurrentStep(u, "clickSeleccionMenuAdmon");
		u.mouseOver(xmlHeader);
		testCase.addScreenShotCurrentStep(u, "clickSeleccionMenuAdmon");

	}
	
	/**
	 * Método para seleccionar y dar click al SubMenu Consulta de Movtos, cuando ya esta visible el menu admon
	 * 
	 * @author Manuel CUen
	 * @param ninguno
	 * @return ninguno
	 */
	public void clickSbMenuConsultaMov(String negocio) {
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		
		// USOS DE OPCIONES
		//	Eliminaci�n de Tienda	
		
		System.out.println(negocio);
		
		if(negocio.toUpperCase().equals("BARA")) {
			u.scrollToElement(xmlMenusAdmon);
//			u.mouseOverAndClick(xmlMenusAdmon);
			
			u.highLight(xmlConsMovMarc);
			u.click(xmlConsMovMarc);
		} else if(negocio.toUpperCase().equals("OXXO") || negocio.toUpperCase().equals("MARCA")) {
			u.highLight(xmlConsultaMov);
			u.click(xmlConsultaMov);
//			u.mouseOverAndClick(xmlConsultaMov);
		}
		u.hardWait(5);
		testCase.addScreenShotCurrentStep(u, "clickSeleccionMenuAdmon");
		u.mouseOver(xmlHeader);
		testCase.addScreenShotCurrentStep(u, "clickSeleccionMenuAdmon");

		
	}

	/*
	 * Metodos Pantalla Consulta de direcciones de env�o
	 ******************************************************************************************/
	
	/**Metodo para validar que los componentes de la pantalla consulta de direcciones esten en pantalla
	 * @return Retorna true si se ven correctamente o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean pantallaConsultaDireccionEnvio() {
		Boolean correct = false;
		
		try {
			
			u.highLight(xmlNombreDireccion);
			u.highLight(xmlPlazaDireccion);
			u.highLight(xmlTiendas);
			u.highLight(xmlBtnNuevo);
			u.highLight(xmlLimpiarBtn);
			u.highLight(xmlBuscarBtn);
			u.highLight(xmlTable);
			testCase.addScreenShotCurrentStep(u, "pantallaConsultaDireccionEnvio");
			testCase.addTextEvidenceCurrentStep("Se muestran correctamente los componentes en pantalla");
			
			u.unhighLight(xmlNombreDireccion);
			u.unhighLight(xmlPlazaDireccion);
			u.unhighLight(xmlTiendas);
			u.unhighLight(xmlBtnNuevo);
			u.unhighLight(xmlLimpiarBtn);
			u.unhighLight(xmlBuscarBtn);
			u.unhighLight(xmlTable);
			
			correct = true;
			
		}catch(Exception e) {
			testCase.addScreenShotCurrentStep(u, "pantallaConsultaDireccionEnvio");
		}
		
		return correct;
	}
	
	/**
	 * Metodo para ingresar el nombre de la direcci�n y seleccionar multiples plazas 
	 * @param nombre
	 * @param plaza1
	 * @param plaza2
	 * @param plaza3
	 * @return Retorna true si se ingresaron correctamente los datos o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean buscarDireccion(String nombre, String plaza1, String plaza2, String plaza3) {
		Boolean correct = false;
		
		try {
			u.highLight(xmlNombreDireccion);
			u.sendKeys(xmlNombreDireccion, nombre);
			testCase.addScreenShotCurrentStep(u, "Direccion");
			u.unhighLight(xmlNombreDireccion);
			
			u.highLight(xmlPlazaDireccion);
			Boolean p1 = pedAdmin.selectOption(xmlPlazaDireccion, xmlMatSelectOp, plaza1);
			u.mouseOverAndClick(xmlTituloPrincipal);
			Boolean p2 = pedAdmin.selectOption(xmlPlazaDireccion, xmlMatSelectOp, plaza2);
			u.mouseOverAndClick(xmlTituloPrincipal);
			Boolean p3 = pedAdmin.selectOption(xmlPlazaDireccion, xmlMatSelectOp, plaza3);
			u.mouseOverAndClick(xmlTituloPrincipal);
			u.unhighLight(xmlPlazaDireccion);
			
			if (p1 && p2 && p3) correct = true;
			
			testCase.addTextEvidenceCurrentStep("Se ingresaron correctamente el nombre y las plazas");
			
		}catch(Exception e) {
			testCase.addScreenShotCurrentStep(u, "Error");
		}
		
		return correct;
	}
	
	/**
	 * Metodo para dar click en el boton buscar
	 * @author Maria Due�as
	 */
	public void clickBuscar() {
		u.highLight(xmlBuscarBtn);
		testCase.addScreenShotCurrentStep(u, "Buscar");
		u.unhighLight(xmlBuscarBtn);
		u.click(xmlBuscarBtn);
		testCase.addScreenShotCurrentStep(u, "clickBuscar");
		
	}
	
	/**
	 * Metodo para dar click en el boton Limpiar
	 * @author Maria Due�as
	 */
	public void clickLimpiar() {
		u.highLight(xmlLimpiarBtn);
		testCase.addScreenShotCurrentStep(u, "Limpiar");
		u.unhighLight(xmlLimpiarBtn);
		u.click(xmlLimpiarBtn);
		testCase.addScreenShotCurrentStep(u, "clickLimpiar");
		
	}
	
	/**
	 * Metodo para validar que se muestre el listado de las plazas seleccionadas
	 * @return Retorna true si se muestran correctamente o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean listadoPlazas() {
		Boolean correct = false;
		
		String listado = u.getText(xmlListadoPlazas);
		u.highLight(xmlListadoPlazas);
		testCase.addScreenShotCurrentStep(u, "Listado");
		u.unhighLight(xmlListadoPlazas);
		
		if(listado.length() > 1) {
			correct = true;
			testCase.addTextEvidenceCurrentStep("Se muestran correctamente el listado de las plazas seleccionadas");
		}
		
		return correct;
	}
	
	/**
	 * Metodo para seleccionar una tienda
	 * @param tienda
	 * @return Retorna true si se selecciono correctamente o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean seleccionarTienda(String tienda) {
		Boolean correct = false;
		
		try {
			
			
			Boolean t = pedAdmin.selectOption(xmlTiendas, xmlMatSelectOp, tienda);
			u.mouseOverAndClick(By.xpath("//*[@id=\"titulo_gral\"]"));
			
			if (t) correct = true;
			
			testCase.addTextEvidenceCurrentStep("Se ingreso correctamente la tienda");
			
		}catch(Exception e) {
			testCase.addScreenShotCurrentStep(u, "Error");
		}
		
		return correct;
	}
	
	/**
	 * Metodo para verificar que se muestren resultados despues de una busqueda
	 * @return Retorna true en caso de encontrar resultados
	 * @author Maria Due�as
	 */
	public Boolean resultadosBusqueda() {
		Boolean correct = pedAdmin.resultadosBusqueda(10, xmlTableTh, xmlTableTr, xmlTable);
		return correct;
	}
	
	/**
	 * Metodo para ver la informacion general y las tiendas asociadas
	 * @return Retorna true si se pueden ver correctamente los datos o falso en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean flechaInformacion() {
		Boolean exist = false;
		
		try {
			m.scrollToElement(xmlTable);
			for(int i = 1; i < 2; i++) {
				
				By by = By.xpath(String.format(tableTr, Integer.toString(i)));
				u.highLight(by);
				testCase.addScreenShotCurrentStep(u, "Flecha informacion");
				u.click(by);
				
				u.highLight(xmlTiendasAsociadas);
				testCase.addScreenShotCurrentStep(u, "TiendasAsociadas");
				u.unhighLight(xmlTiendasAsociadas);
				
				u.highLight(xmlDatosGenerales);
				u.click(xmlDatosGenerales);
				testCase.addScreenShotCurrentStep(u, "TiendasAsociadas");
				u.unhighLight(xmlDatosGenerales);
				
				u.click(xmlTiendasAsociadas);
				
				u.unhighLight(by);
				u.click(by);
			}
			
			exist = true;
		}catch(Exception e) {
			testCase.addScreenShotCurrentStep(u, "Error");
		}
		
		return exist;
	}
	
	/**
	 * Metodo para realizar busqueda de direccion
	 * @param nombre
	 * @param plaza1
	 * @param tienda
	 * @return Retorna true si se ingresaron correctamente los datos o false en caso contrario
	 * @author Mar�a Due�as
	 */
	public Boolean buscarDireccion(String nombre, String plaza1, String tienda) {
		Boolean correct = false;
		
		try {
			m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
			u.highLight(xmlNombreDireccion);
			u.sendKeys(xmlNombreDireccion, nombre);
			testCase.addScreenShotCurrentStep(u, "Direccion");
			u.unhighLight(xmlNombreDireccion);
			
			Boolean p1 = pedAdmin.selectOption(xmlPlazaDireccion, xmlMatSelectOp, plaza1);
			u.mouseOverAndClick(xmlTituloPrincipal);
			Boolean t = pedAdmin.selectOption(xmlTiendas, xmlMatSelectOp, tienda);
			u.mouseOverAndClick(By.xpath("//*[@id=\"titulo_gral\"]"));
			
			if (p1 && t) correct = true;
			
			testCase.addTextEvidenceCurrentStep("Se ingresaron correctamente los datos a buscar");
			
		}catch(Exception e) {
			testCase.addScreenShotCurrentStep(u, "Error");
		}
		
		return correct;
	}
	
	/**
	 * Metodo para dar click en el bot�n Nuevo
	 * @author Maria Due�as
	 */
	public void clickNuevoBtn() {
		m.scrollToElement(xmlBtnNuevo);
		u.highLight(xmlBtnNuevo);
		testCase.addScreenShotCurrentStep(u, "NuevoBtn");
		u.unhighLight(xmlBtnNuevo);
		u.click(xmlBtnNuevo);
		testCase.addScreenShotCurrentStep(u, "clickNuevoBtn");
	}
	
	/*
	 * Metodos Pantalla Nuevo
     ******************************************************************************************/
	
	/**
	 * Metodo para validar que se visualicen los componentes de Nueva Direccioon de Env�o
	 * @return Retorna True si se muestran correctamente o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean pantallaNuevoDireccionEnvio() {
		
		Boolean correct = false;
		try {
			if(m.validarVisualizacion(xmlPlazaNuevo) && m.validarVisualizacion(xmlNomDireccion) && m.validarVisualizacion(xmlCP)
					&& m.validarVisualizacion(xmlEstado) && m.validarVisualizacion(xmlCiudad) && m.validarVisualizacion(xmlColonia)
					&& m.validarVisualizacion(xmlCallePrinc) && m.validarVisualizacion(xmlEntCalle)  && m.validarVisualizacion(xmlYEntreCalle)
					&& m.validarVisualizacion(xmlNomCont) && m.validarVisualizacion(xmlNumContacto)) {
				
				u.highLight(xmlElementos);
				testCase.addTextEvidenceCurrentStep("Se muestran los componentes");
				testCase.addScreenShotCurrentStep(u, "pantallaNuevoDireccionEnvio");
				u.unhighLight(xmlElementos);
				
				correct = true;
			}
		}catch(Exception e) {
			testCase.addScreenShotCurrentStep(u, "Error");
		}
		
		
		return correct;
		
	}
	
	/**
	 * Metodo para ingresar la informacion de la nueva Direccion de env�o
	 * @param plaza
	 * @param nomDir
	 * @param cp
	 * @param calle
	 * @param entre
	 * @param yEntre
	 * @param nomCont
	 * @param telContac
	 * @return Retorna true si se ingresan correctamente los datos o false en caso contrario
	 * @author Mar�a Due�as
	 */
	public Boolean capturarDatosNuevaDireccion(String plaza, String nomDir, String cp, 
			String calle, String entre, String yEntre, String nomCont, String telContac){
		
		Boolean correct = false;
		
		try {
			
			Boolean p = pedAdmin.selectOption(xmlPlazaNuevo, xmlOpcionesMat, plaza);
			
			u.highLight(xmlNomDireccion);
			u.sendKeys(xmlNomDireccion, nomDir);
			testCase.addScreenShotCurrentStep(u, "NombreDireccion");
			u.unhighLight(xmlNomDireccion);
			
			u.highLight(xmlCP);
			u.sendKeys(xmlCP, cp);
			testCase.addScreenShotCurrentStep(u, "CP");
			u.unhighLight(xmlCP);
			
			u.click(xmlCallePrinc);
			m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
			
			//Boolean e = pedAdmin.selectOption(xmlEstado, xmlOpcionesMat, String.format(xmlMatOption, estado));
			//Boolean c = pedAdmin.selectOption(xmlCiudad, xmlOpcionesMat, String.format(xmlMatOption, ciudad));
			//Boolean col = pedAdmin.selectOption(xmlColonia, xmlOpcionesMat, String.format(xmlMatOption, colonia));
			
			u.highLight(xmlCallePrinc);
			u.sendKeys(xmlCallePrinc, calle);
			testCase.addScreenShotCurrentStep(u, "calle");
			u.unhighLight(xmlCallePrinc);
			
			u.highLight(xmlEntCalle);
			u.sendKeys(xmlEntCalle, entre);
			testCase.addScreenShotCurrentStep(u, "entre");
			u.unhighLight(xmlEntCalle);
			
			u.highLight(xmlYEntreCalle);
			u.sendKeys(xmlYEntreCalle, yEntre);
			testCase.addScreenShotCurrentStep(u, "yEntre");
			u.unhighLight(xmlYEntreCalle);
			
			u.highLight(xmlNomCont);
			u.sendKeys(xmlNomCont, nomCont);
			testCase.addScreenShotCurrentStep(u, "nomCont");
			u.unhighLight(xmlNomCont);
			
			u.highLight(xmlNumContacto);
			u.sendKeys(xmlNumContacto, telContac);
			testCase.addScreenShotCurrentStep(u, "telContac");
			u.unhighLight(xmlNumContacto);
			
			/*&& e && c && col*/
			if (p) {
				correct = true;
				testCase.addTextEvidenceCurrentStep("Se ingreso correctamente la informaci�n");
			}
			
		}catch(Exception e) {
			testCase.addScreenShotCurrentStep(u, "Error");
		}
		
		return correct;
		
	}
	
	/**
	 * Metodo para dar click en TiendasAsociadas
	 * @author Maria Due�as
	 */
	public void clickTiendasAsociadas() {
		u.highLight(xmlTiendasAsociadas);
		testCase.addScreenShotCurrentStep(u, "TiendasAsociadas");
		u.unhighLight(xmlTiendasAsociadas);
		u.click(xmlTiendasAsociadas);
		testCase.addScreenShotCurrentStep(u, "clickTiendasAsociadas");
	}
	
	/**
	 * Metodo para validar que se visualicen los componentes en la pesta�a Tiendas
	 * @return Retorna true si se visualizan o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean pestanaTiendas() {
		Boolean correct = false;
		
		try {
			if(u.getDriver().findElement(xmlTiendaAsociadas).isDisplayed() 
					&& u.getDriver().findElement(xmlTiendasExistentes).isDisplayed()) {
				
				if(u.getDriver().findElement(xmlSeleccionarTodo).isDisplayed()) {
					u.highLight(xmlSeleccionarTodo);
					testCase.addScreenShotCurrentStep(u, "SeleccionarTodas");
					u.unhighLight(xmlSeleccionarTodo);
				}
				
				Boolean tiendasExistentes = numTiendas(xmlListaTiendasExistentes, xmlTE);
				if (!tiendasExistentes) testCase.addTextEvidenceCurrentStep("No hay tiendas existentes");
				
				if(u.getDriver().findElement(xmlQuitarTodo).isDisplayed()) {
					u.highLight(xmlQuitarTodo);
					testCase.addScreenShotCurrentStep(u, "QuitarTodas");
					u.unhighLight(xmlQuitarTodo);
				}
				
				Boolean tiendasAsociadas = numTiendas(xmlListaTiendasAsociadas, xmlTA);
				if (!tiendasAsociadas) testCase.addTextEvidenceCurrentStep("No hay tiendas asociadas");
				
				correct = true;
				
			}
		}catch(Exception e) {
			testCase.addScreenShotCurrentStep(u, "Error");
		}
		
		return correct;
	}
	
	/**
	 * Metodo para obtener el la lista de tiendas y hacerles highlight
	 * @param by
	 * @return Retorna true si encontro tiendas o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean numTiendas(By by, String tienda) {
		Boolean exist = false;
		Integer tiendas = 0;
		
		List<WebElement> allOptions = u.getDriver().findElements(by);
		tiendas = allOptions.size();
		
		if (tiendas > 0) {
			
			for(int i = 1; i <= tiendas; i++){
				u.highLight(By.xpath(String.format(tienda, Integer.toString(i))));
			}
			
			testCase.addScreenShotCurrentStep(u, "ListaTiendas");
			
			for(int i = 1; i <= tiendas; i++){
				u.unhighLight(By.xpath(String.format(tienda, Integer.toString(i))));
			}
			
			exist = true;
		}
		
		return exist;
	}
	
	/**
	 * Metodo para seleccionar 1 o dos tiendas existentes
	 * @return Retorna true si selecciono las tiendas o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean seleccionMultipleTiendas() {
		Boolean exist = false;
		Integer tiendas = 0;
		
		List<WebElement> allOptions = u.getDriver().findElements(xmlListaTiendasExistentes);
		tiendas = allOptions.size();
		
		if (tiendas > 0) {
			u.highLight(By.xpath(String.format(xmlTiendaExistente, "1")));
			testCase.addScreenShotCurrentStep(u, "tienda");
			u.unhighLight(By.xpath(String.format(xmlTiendaExistente, "1")));
			u.click(By.xpath(String.format(xmlTiendaExistente, "1")));
			
			if (tiendas >= 1) {
				u.highLight(By.xpath(String.format(xmlTiendaExistente, "2")));
				testCase.addScreenShotCurrentStep(u, "tienda");
				u.unhighLight(By.xpath(String.format(xmlTiendaExistente, "2")));
				u.click(By.xpath(String.format(xmlTiendaExistente, "2")));
			}
			
			exist = true;
		}
		
		return exist;
	}
	
	/**
	 * Metodo para validar que se muestren las tiendas seleccionadas en tiendas existentes
	 * @return Retorna true si estan en el apartado de tiendas asociadas
	 * @author Maria Due�as
	 */
	public Boolean tiendasSeleccionadas() {
		
		Boolean tiendasAsociadas = numTiendas(xmlListaTiendasAsociadas, xmlTA);
		return tiendasAsociadas;
		
	}
	
	/**
	 * Metodo para dar click en Datos Generales
	 * @author Maria Due�as
	 */
	public void clickDatosGenerales() {
		u.highLight(xmlDatosGenerales);
		testCase.addScreenShotCurrentStep(u, "DatosGenerales");
		u.unhighLight(xmlDatosGenerales);
		u.click(xmlDatosGenerales);
		testCase.addScreenShotCurrentStep(u, "clickDatosGenerales");
	}
	
	/**
	 * Metodo para validar que se muestran los datos ingresados en la pantalla de Datos generales
	 * @param plaza
	 * @param nomDir
	 * @param cp
	 * @param calle
	 * @param entre
	 * @param yEntre
	 * @param nomCont
	 * @param telContac
	 * @return Retorna true si se muestran correctamente los datos o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean datosCapturados(String plaza, String nomDir, String cp, String calle, 
			String entre, String yEntre, String nomCont, String telContac){
		
		Boolean correct = false;
		u.hardWait(5);
		//m.explicitWaitToBeInvisible(10);
		try {
		
			String plazaTxt = u.getText(xmlPlazaNuevo).trim();
			String noDirTxt = u.getText(xmlNomDireccion).trim();
			String cpTxt = u.getText(xmlCP).trim();
			String calleTxt = u.getText(xmlCallePrinc).trim();
			String entCalleTxt = u.getText(xmlEntCalle).trim();
			String YentreCalleTxt = u.getText(xmlYEntreCalle).trim();
			String nomContTxt = u.getText(xmlNomCont).trim();
			String NumContactoTxt = u.getText(xmlNumContacto).trim();
			System.out.print(plaza+"-"+plazaTxt+"-\n");
			System.out.print(nomDir+"-"+noDirTxt+"-\n");
			System.out.print(cp+"-"+cpTxt+"-\n");
			System.out.print(calle+"-"+calleTxt+"-\n");
			System.out.print(entre+"-"+entCalleTxt+"-\n");
			System.out.print(yEntre+"-"+YentreCalleTxt+"-\n");
			System.out.print(nomCont+"-"+nomContTxt+"-\n");
			System.out.print(telContac+"-"+NumContactoTxt+"-\n");
			if (plaza.equals(plazaTxt) && nomDir.equals(noDirTxt) && cp.equals(cpTxt) && calle.equals(calleTxt)
					&& entre.equals(entCalleTxt) && yEntre.equals(YentreCalleTxt) && nomCont.equals(nomContTxt)
					&& telContac.equals(NumContactoTxt)) {
				correct = true;
			}
			
		}catch(Exception e) {
			testCase.addScreenShotCurrentStep(u, "Error");
		}
		
		return correct;
		
	}
	
	/**
	 * Metodo para dar click en Guardar
	 * @author Maria Due�as
	 */
	public void clickGuardarNuevoBtn() {
		u.highLight(xmlGuardarNuevoBtn);
		testCase.addScreenShotCurrentStep(u, "GuardarNuevoBtn");
		u.unhighLight(xmlGuardarNuevoBtn);
		u.click(xmlGuardarNuevoBtn);
		testCase.addScreenShotCurrentStep(u, "clickGuardarNuevoBtn");
	}
	
	/**
	 * Metodo para dar click en Confirmar
	 * @author Maria Due�as
	 */
	public void clickConfirmar() {
		u.highLight(xmlConfirmar);
		testCase.addScreenShotCurrentStep(u, "Confirmar");
		u.unhighLight(xmlConfirmar);
		u.click(xmlConfirmar);
		testCase.addScreenShotCurrentStep(u, "clickConfirmar");
	}
	
	/**
	 * Metodo para validar que se guarden correctamente los datos 
	 * @return
	 */
	public Boolean mensajeConfirmacionNuevo() {
		Boolean correct = false;
		String mensaje = "";
	
		m.explicitWaitVisibilityOfElement(xmlMensaje, Duration.ofSeconds(10));
		if (u.getDriver().findElement(xmlMensaje).isDisplayed()) {
			
			mensaje = u.getText(xmlMensaje);
			
			u.highLight(xmlMensaje);
			testCase.addScreenShotCurrentStep(u, "Mensaje");
			u.unhighLight(xmlMensaje);
			//La informaci�n ha sido actualizada correctamente y el detalle se ha enviado por correo electr�nico
			if(mensaje.equals("Ya existe una direcci�n de env�o con el mismo nombre")) {
				correct = false;
			} else if (mensaje.equals("La informaci�n ha sido guardada correctamente y el detalle se ha enviado por correo electr�nico")) {
				correct = true;
			}
		}
		
		return correct;
	}
	
	/**
	 * Metodo para validar que se actualicen correctamente los datos 
	 * @return
	 */
	public Boolean mensajeConfirmacionEditar() {
		Boolean correct = false;
		String mensaje = "";
	
		m.explicitWaitVisibilityOfElement(xmlMensaje, Duration.ofSeconds(10));
		if (u.getDriver().findElement(xmlMensaje).isDisplayed()) {
			
			mensaje = u.getText(xmlMensaje);
			
			u.highLight(xmlMensaje);
			testCase.addScreenShotCurrentStep(u, "Mensaje");
			u.unhighLight(xmlMensaje);
			//
			if(mensaje.equals("Ya existe una direcci�n de env�o con el mismo nombre")) {
				correct = false;
			} else if (mensaje.equals("La informaci�n ha sido actualizada correctamente y el detalle se ha enviado por correo electr�nico")) {
				correct = true;
			}
		}
		
		return correct;
	}
	
	/**
	 * Metodo para buscar la direccion registrada en ventana direcciones de envio y dar click en Editar
	 * @param nombre de la direccion
	 * @return Retorna true si encuentra la direccion o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean clickEditarDireccion(String nombre) {
		Boolean exist = false;
		
		String direccion = "";
		
		u.hardWait(5);
		m.scrollToElement(xmlTable);
		List <WebElement> tablaTr = u.getDriver().findElements(xmlTableTr);
		Integer row_count = tablaTr.size();
		
		//System.out.print(row_count + "\n");
		
		if (row_count >= 1) {
			for (int i=1; i <= row_count; i++) {
				direccion = String.format(tableTr, Integer.toString(i)); 
				String f = u.getText(By.xpath(direccion)).trim();
				//System.out.print("-" + f + "-\n");
				if (nombre.equals(f)) {
					exist = true;
					direccion = String.format(xmlTablaTr, Integer.toString(i)); 
					u.highLight(By.xpath(direccion));
					testCase.addScreenShotCurrentStep(u, "buscardireccion");
					u.unhighLight(By.xpath(direccion));
					
					u.highLight(By.xpath(String.format(xmlEditarTr, Integer.toString(i))));
					testCase.addScreenShotCurrentStep(u, "editardireccion");
					u.unhighLight(By.xpath(String.format(xmlEditarTr, Integer.toString(i))));
					u.click(By.xpath(String.format(xmlEditarTr, Integer.toString(i))));
					testCase.addScreenShotCurrentStep(u, "clickEditardireccion");
					break;
				}
			}
		}
		testCase.addScreenShotCurrentStep(u, "buscardireccion");
		return exist;
	}
	
	/**
	 * Metodo par editar la informacion de la direccion
	 * @param nomDir
	 * @param calle
	 * @param entre
	 * @param yEntre
	 * @param nomCont
	 * @param telContac
	 * @return Retorna true si edito correctamente todos los datos o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean editarDireccion(String nomDir, String calle, String entre, 
			String yEntre, String nomCont, String telContac){
		
		Boolean correct = false;
		
		try {
			
			testCase.addScreenShotCurrentStep(u, "Direccion");
			
			u.clear(xmlNomDireccion);
			u.clear(xmlCallePrinc);
			u.clear(xmlEntCalle);
			u.clear(xmlNomCont);
			u.clear(xmlNumContacto);
			
			testCase.addScreenShotCurrentStep(u, "DireccionClear");
			
			u.highLight(xmlNomDireccion);
			u.sendKeys(xmlNomDireccion, nomDir);
			testCase.addScreenShotCurrentStep(u, "NombreDireccion");
			u.unhighLight(xmlNomDireccion);
			
			u.highLight(xmlCallePrinc);
			u.sendKeys(xmlCallePrinc, calle);
			testCase.addScreenShotCurrentStep(u, "calle");
			u.unhighLight(xmlCallePrinc);
			
			u.highLight(xmlEntCalle);
			u.sendKeys(xmlEntCalle, entre);
			testCase.addScreenShotCurrentStep(u, "entre");
			u.unhighLight(xmlEntCalle);
			
			u.highLight(xmlYEntreCalle);
			u.sendKeys(xmlYEntreCalle, yEntre);
			testCase.addScreenShotCurrentStep(u, "yEntre");
			u.unhighLight(xmlYEntreCalle);
			
			u.highLight(xmlNomCont);
			u.sendKeys(xmlNomCont, nomCont);
			testCase.addScreenShotCurrentStep(u, "nomCont");
			u.unhighLight(xmlNomCont);
			
			u.highLight(xmlNumContacto);
			u.sendKeys(xmlNumContacto, telContac);
			testCase.addScreenShotCurrentStep(u, "telContac");
			u.unhighLight(xmlNumContacto);
			
			correct = true;
			
		}catch(Exception e) {
			testCase.addScreenShotCurrentStep(u, "Error");
		}
		
		return correct;
		
	}
	
	/**
	 * Metodo para seleccionar direccion
	 * @param nombre
	 * @return Retorna true si encuentra la direccion y la selecciona o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean clickSeleccionarDireccion(String nombre) {
		Boolean exist = false;
		
		String direccion = "";
		
		u.hardWait(5);
		m.scrollToElement(xmlTable);
		List <WebElement> tablaTr = u.getDriver().findElements(xmlTableTr);
		Integer row_count = tablaTr.size();
		
		//System.out.print(row_count + "\n");
		
		if (row_count >= 1) {
			for (int i=1; i <= row_count; i+=2) {
				direccion = String.format(tableTr, Integer.toString(i)); 
				String f = u.getText(By.xpath(direccion)).trim();
				//System.out.print("-" + f + "-\n");
				if (nombre.equals(f)) {
					exist = true;
					direccion = String.format(xmlTablaTr, Integer.toString(i)); 
					u.highLight(By.xpath(direccion));
					testCase.addScreenShotCurrentStep(u, "buscardireccion");
					u.unhighLight(By.xpath(direccion));
					
					u.highLight(By.xpath(String.format(xmlSeleccionarTr, Integer.toString(i))));
					testCase.addScreenShotCurrentStep(u, "seleccionardireccion");
					u.unhighLight(By.xpath(String.format(xmlSeleccionarTr, Integer.toString(i))));
					u.click(By.xpath(String.format(xmlSeleccionarTr, Integer.toString(i))));
					testCase.addScreenShotCurrentStep(u, "clickSeleccionardireccion");
					break;
				}
			}
		}
		testCase.addScreenShotCurrentStep(u, "buscardireccion");
		return exist;
	}
	
	/**
	 * Metodo para dar click en Eliminar
	 * @author Maria Due�as
	 */
	public void clickEliminarBtn() {
		m.scrollToElement(xmlEliminarBtn);
		u.highLight(xmlEliminarBtn);
		testCase.addScreenShotCurrentStep(u, "EliminarBtn");
		u.unhighLight(xmlEliminarBtn);
		u.click(xmlEliminarBtn);
		testCase.addScreenShotCurrentStep(u, "clickEliminarBtn");
	}
	
	/**
	 * Metodo para validar que se muestre mensaje de confimacion de eliminacion
	 * @return Retorna true si muestra el elemento o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean confirmarEliminacion() {
		Boolean correct = false;
		String mensaje = "";
	
		m.explicitWaitVisibilityOfElement(xmlConfirmacionEliminar, Duration.ofSeconds(10));
		if (u.getDriver().findElement(xmlConfirmacionEliminar).isDisplayed()) {
			
			mensaje = u.getText(xmlConfirmacionEliminar).trim();
			
			u.highLight(xmlConfirmacionEliminar);
			testCase.addScreenShotCurrentStep(u, "Mensaje");
			u.unhighLight(xmlConfirmacionEliminar);
			//Se han eliminado los registros exitosamente.
			if (mensaje.equals("�Esta seguro que desea eliminar el/los registro(s) seleccionado(s)?")) {
				correct = true;
			}
		}
		
		return correct;
	}
	
	/**
	 * Metodo para validar que se elimine la direccion
	 * @return Retorna true si se muestra el mensaje o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean mensajeConfirmacionEliminacion() {
		Boolean correct = false;
		String mensaje = "";
	
		m.explicitWaitVisibilityOfElement(xmlMensaje, Duration.ofSeconds(10));
		if (u.getDriver().findElement(xmlMensaje).isDisplayed()) {
			
			mensaje = u.getText(xmlMensaje).trim();
			
			u.highLight(xmlMensaje);
			testCase.addScreenShotCurrentStep(u, "Mensaje");
			u.unhighLight(xmlMensaje);
			//
			if (mensaje.equals("Se han eliminado los registros exitosamente.")) {
				correct = true;
			}
		}
		
		return correct;
	}
}
