package WEB.inventarioEspacios.om;

import java.time.Duration;
import org.openqa.selenium.By;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class MenuPrincipalIEP {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	
    /**
    * Localizadores y mÃƒÂ©todos del MenÃƒÂº Principal o Home 
    * del portal Inventario de Espacios Publicitarios v2
    ******************************************************************************************/
	
	/*
	 * Localizadores contenedor principal
	 *****************************************************************************************/
	
	By xmlContainer = By.xpath("//*[@id=\"content-nav\"]");
	By xmlPEP = By.xpath("//*[@id=\"tituloInicio\"]");
	
	
    /*
    * Localizadores informaciÃƒÂ³n del usuario, negocio y perfil.
    ******************************************************************************************/

    By xmlUsuario = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/p");
  //*[@id="content-nav"]/app-header/mat-toolbar/div/div[1]/div[3]/p
    By xmlNegocio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[1]");
    By xmlPerfilAdm = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[1]"); 
    By xmlPerfilAdmMarca = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[2]");
    By xmlPeriodo = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[2]");   
	
	/*
	 * Localizadores MenÃƒÂº Principal Inventarios Espacios
	 ******************************************************************************************/

	By xmlMenus = By.xpath("//mat-sidenav[@id = 'sidenav']");	
	By xmlHeader = By.xpath("//mat-sidenav-content[@id='content-nav']/app-header");
	
	/*
	 * Localizadores Apartado del MenÃƒÂº - AdministraciÃƒÂ³n
	 ******************************************************************************************/	
	By xmlAdministracion = By.xpath("//div[@class= \"texto-menu\"]/a[1]/div");
	By xmlAdministracionM = By.xpath("//div[@class= \"texto-menu\"]/a[4]/div");
	
	//OPCIONES
	By xmlAsociarPopPlazaOp = By.xpath("//label[contains(text(),'Asociar POP a Plazas')]");
	By xmlAsociarPopProveedorOp = By.xpath("//label[contains(text(),'Asociar POP a Proveedor')]");
	By xmlPeriodoOp = By.xpath("//label[contains(text(),'Periodo')]");
	By xmlConsultaPlazasOp = By.xpath("//label[contains(text(),'Consulta de plazas')]");
	By xmlConsultaPlazasCCMop = By.xpath("//label[contains(text(),'Consulta de plazas CCM')]");
	By xmlProveedores = By.xpath("//label[contains(text(),'Proveedores')]");
	By xmlUsuariosOp = By.xpath("//label[contains(text(),'Usuarios')]");
	By xmlReglasOp = By.xpath("//label[contains(text(),'Reglas')]");
	
	/*
	 * Localizadores Apartado del MenÃƒÂº - Espacios
	 ******************************************************************************************/
	
	By xmlEspacios = By.xpath("//div[@class= \"texto-menu\"]/a[2]/div");
	By xmlEspaciosPO = By.xpath("//div[@class= \"texto-menu\"]/a[1]/div"); //Plaza Oxxo
	
	//Opciones
	
	By xmlCargaMasivaInv = By.xpath("//label[contains(text(),'Carga masiva de inventarios')]");
	By xmlConsultaEspEq = By.xpath("//label[contains(text(),'Consulta de Espacios/Equipos')]");
	By xmlConsultaInv = By.xpath("//label[contains(text(),'Consulta de inventarios')]");
	
	/*
	 * Localizadores Apartado del MenÃƒÂº - Pedidos
	 ******************************************************************************************/
	
	By xmlPedidos = By.xpath("//div[@class= \"texto-menu\"]/a[3]/div");
	By xmlPedidosPO = By.xpath("//div[@class= \"texto-menu\"]/a[2]/div"); //Plaza Oxxo
	By xmlPedidosE = By.xpath("//div[@class= \"texto-menu\"]/a[1]/div"); //Estafeta
	By xmlPedidosM = By.xpath("//div[@class= \"texto-menu\"]/a[9]/div"); //Admin Marca
	
	//OPCIONES
	By xmlCrearPedidosOp = By.xpath("//label[contains(text(),'Crear Pedidos')]");
	By xmlBandejaPedidos = By.xpath("//label[contains(text(), 'Bandeja de Pedidos')]");

	By xmlTipoPedidoOp = By.xpath("//label[contains(text(), 'Tipos de pedido')]");

	By xmlConsultaDirEnvio = By.xpath("//label[contains(text(), 'Consulta de dirección de envío')]");

	
	
	/*
	 * Localizadores Apartado del MenÃƒÂº - POPs
	 ******************************************************************************************/
	

	//By xmlPops = By.xpath("//div[@class= \"texto-menu\"]/a[4]/div");
  //div[@class= \"texto-menu\"]/a[4]/div

	//By xmlPops = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[11]/div/label");
	By xmlPops = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[4]/div/span");
  
  By xmlPopsM = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div[1]/mat-list/div[3]");
  //By xmlPopsM = By.xpath("//div[@class= \"texto-menu\"]/a[10]/div");
  
	By xmlPopsMAdmin = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[10]/div");
	
	//
	//OPCIONES
	By xmlPopsOp = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[1]/a/div");
	By xmlTipoPopsOp = By.xpath("//label[contains(text(),'Tipos de POP')]");
	By xmlCargaMasivaPopsOp = By.xpath("//label[contains(text(),'Carga masiva pops')]");
	
	/*
	 * Localizadores Apartado del MenÃƒÂº - Reportes
	 ******************************************************************************************/
	
	By xmlReportes = By.xpath("//div[@class= \"texto-menu\"]/a[5]/div");
	//By xmlReportesPO = By.xpath("//div[@class= \"texto-menu\"]/a[3]/div"); //Plaza OXXO
	By xmlReportesPO = By.xpath("//div[@class= \"texto-menu\"]/a[4]/div"); //Plaza OXXO
	By xmlReportesM = By.xpath("//div[@class= \"texto-menu\"]/a[12]/div"); //Admin Marca

	//OPCIONES
	By xmlReporteCGE = By.xpath("//label[contains(text(),'Reporte de cobertura de grupo de espacios')]");
	
	/**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase MenuPrincipalIEP
	 * @author Andrea Contreras
	 * @param u (SeleniumUtil): objeto de la clase 
	 * @param testCase (testCase): objeto de la clase
	 */
	public MenuPrincipalIEP(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}
	
	/*
	 * METODOS - Acceso correcto a Inventario de espacios Publicitarios V2
	 ******************************************************************************************/
	
	/**
	 * Metodo para validar el acceso correcto al Inventario de Espacios Publicitarios
	 * @return Regresa falso o verdadero si encuentra los elementos
	 * @author Maria Dueñas
	 */
	public Boolean accesoCorrectoIEP(){
		 Boolean exist = false;
		 
		 me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		 String pep = "Portal Espacios Publicitarios";
		 if (me.validarVisualizacion(xmlContainer) == true && u.getText(xmlPEP).equals(pep)) {
					 exist = true; 			 	 
		 }
		 return exist;
	 }
	
	/*
	 * METODOS - InformaciÃƒÂ³n del usuario, negocio y perfil.
	 ******************************************************************************************/
	 
	/**
	 * MÃƒÂ©todo para validar la informacion del usuario, negocio y perfil que se muestran en el sistema, 
	 * coincidan con el data provider
	 * @param dato, dato2, dato3 Recibe como parametro el usuario, negocio y perfil
	 * @return bandera Regresa un true o false si la informacion coincide
	 * @author Maria Dueñas
	 */
	 public Boolean verificarInformacion(String dato, String dato2, String dato3){

		 me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		 
		 Boolean marca = false;
		 
		 if (dato2.equals("Marca")) {
			 dato2 = "Oxxo";
			 marca = true;
		 }
		 
         String usuario = u.getText(xmlUsuario).toUpperCase();
         clickMenus();
         u.highLight(xmlUsuario);
         
         String negocio = u.getText(xmlNegocio).toUpperCase();
         u.highLight(xmlNegocio);

         Boolean bandera = false;
         
        /* System.out.print("-" + usuario + "-" + dato.toUpperCase() + "-\n");
         System.out.print("-" + negocio + "-" + dato2.toUpperCase() + "-\n");*/
         
         if(dato.toUpperCase().trim().equals(usuario) && dato2.toUpperCase().trim().equals(negocio)) {
             bandera = true;
         }               

         switch(dato3.toUpperCase()) {
         	case "ADMINISTRADOR": 
         						  if (!marca) {
         							  u.highLight(xmlPerfilAdm);
            						  testCase.addScreenShotCurrentStep("verificarInformacion");
            						  u.unhighLight(xmlPerfilAdm);
         						  } else {
         							  u.highLight(xmlPerfilAdmMarca);
	           						  testCase.addScreenShotCurrentStep("verificarInformacion");
	           						  u.unhighLight(xmlPerfilAdmMarca);
         						  }
         						  
         						  break;
         	case "PLAZA": 
		         		  u.highLight(xmlPerfilAdm);
						  testCase.addScreenShotCurrentStep("verificarInformacion");
						  u.unhighLight(xmlPerfilAdm);
						  
     					  break;
     					  
         	case "ESTAFETA": 
			         		  u.highLight(xmlPerfilAdm);
							  testCase.addScreenShotCurrentStep("verificarInformacion");
							  u.unhighLight(xmlPerfilAdm);
							  
     						 break;
         	default:
     		        testCase.addScreenShotCurrentStep("verificarInformacion");
     		        break;
         }
         
         u.unhighLight(xmlUsuario);	
         u.unhighLight(xmlNegocio);	
         
         return bandera;

    }
	 
	 /**
	  * MÃƒÂ©todo para validar la informacion megocio y perfil que se muestran en el sistema, 
	  * coincidan con el data provider
	  * @param dato Recibe como parametro el negocio
	  * @return bandera Regresa un true o false si la informacion es correcta
	  * @author Maria Dueñas
	  */
	 public Boolean verificarEncabezado(String dato){

         clickMenus();
         
         if (dato.equals("Marca")) {
			 dato = "Oxxo";
		 }
         
         String negocio = u.getText(xmlNegocio).trim();
         u.highLight(xmlNegocio);
         String periodo = "";
         periodo = u.getText(xmlPeriodo);
         u.highLight(xmlPeriodo);

         Boolean bandera = false;
         if(dato.trim().equals(negocio) && periodo !=  "") {
             bandera = true;
         }               

         testCase.addScreenShotCurrentStep("verificarEncabezado");
         u.unhighLight(xmlNegocio);	
         u.unhighLight(xmlPeriodo);
         
         return bandera;

    }
	
	 /*
	  * METODOS - Contenedor Principal
	  ******************************************************************************************/
	/**
	 * Metodo para dar click en el contenedor principal del sistema
	 * @author Maria Dueñas	
	 */
	 public void clickContainer() {
	    	
	    u.click(xmlContainer);	
	    testCase.addScreenShotCurrentStep(u, "clickContainer");
	    	
	 }
	    
	/*
	 * METODOS - MenÃƒÂº Principal
	 ******************************************************************************************/
	/**
	* Metodo para posicionar el mouse sobre la barra que despliega el menu
	* @author Andrea Contreras
	*/	
    public void clickMenus() {
    	me.explicitWaitToBeInvisible(Duration.ofSeconds(20));
    	do { 		
    		u.mouseOver(xmlMenus);		
    	}while (u.waitWebElement(xmlMenus, 5, 5).isEnabled()==false); 	
    	testCase.addScreenShotCurrentStep("clickMenuIEP");
    	
    }
    
    
	/*
	 * METODOS - Apartado AdministraciÃƒÂ³n
	 ******************************************************************************************/
    /**
     * Metodo para dar click en opcion 'Administracion' para Admin OXXO y Admin BARA
     * @author Andrea Contreras
     */
    public void clickAdministracion() {
        	
    	    u.highLight(xmlAdministracion);
        	testCase.addScreenShotCurrentStep(u, "hlAdmin");
        	u.click(xmlAdministracion); 
        	testCase.addScreenShotCurrentStep(u, "clickAdmin");
        	u.unhighLight(xmlAdministracion);
    		
    }
     
    /**
     * Metodo para dar click en opcion 'Administracion' en Admin Marca
     * @author Maria Dueñas
     */
    public void clickAdministracionM() {
        
    	me.scrollToElement(xmlAdministracionM);
    	u.highLight(xmlAdministracionM);
    	u.click(xmlAdministracionM); 
    	testCase.addScreenShotCurrentStep("clickAdministracionM");
    	u.unhighLight(xmlAdministracionM);
    		
    }
    
    
    //---------------------------------------------------------------------------------------------
    /**
     * Metodo para seleccionar la opcion 'Asociar POP a Plaza' del menu 'Administracion'
     * @author Andrea Contreras
     */
    public void clickAsociarPopPlazaOp() {
        
    	me.scrollToElement(xmlAsociarPopPlazaOp);
    	u.highLight(xmlAsociarPopPlazaOp);
    	testCase.addScreenShotCurrentStep(u, "hlAsociarPopPlaza");
    	u.click(xmlAsociarPopPlazaOp);  	
    	me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
    	testCase.addScreenShotCurrentStep(u, "clickAsociarPopPlaza");
    	u.unhighLight(xmlAsociarPopPlazaOp);
    	u.mouseOver(xmlHeader);
    		
    }
    
    /**
     * Metodo para seleccionar la opcion 'Asociar POP a Proveedor' del menu 'Administracion'
     * @author Andrea Contreras
     */
    public void clickAsociarPopProveedorOp() {
        
    	me.scrollToElement(xmlAsociarPopProveedorOp);
    	u.highLight(xmlAsociarPopProveedorOp);
    	testCase.addScreenShotCurrentStep(u, "hlAsociarPopProv");
    	u.click(xmlAsociarPopProveedorOp);  
    	me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
    	testCase.addScreenShotCurrentStep(u, "clickAsociarPopProv");
    	u.unhighLight(xmlAsociarPopProveedorOp);
    	u.mouseOver(xmlHeader);
    		
    }
    
    /**
     * Metodo para seleccionar la opcion 'Periodo' del menu 'Administracion'
     * @author Maria Dueñas
     */
    public void clickPeriodoOp() {
    	
    	u.highLight(xmlPeriodoOp);
    	u.click(xmlPeriodoOp);
    	testCase.addScreenShotCurrentStep(u, "clickPeriodoOp");
    	u.mouseOver(xmlHeader);
    	u.unhighLight(xmlPeriodoOp);
    		
    }
    
    
    /**
     * Metodo para seleccionar la opcion 'Consulta de Plazas' del menu 'Administracion'
     * @author Andrea Contreras
     */
    public void clickConsultaPlazasOp() {
    	  	
    	me.scrollToElement(xmlConsultaPlazasOp);
    	u.highLight(xmlConsultaPlazasOp);  	
    	testCase.addScreenShotCurrentStep(u, "hlCP");
    	u.click(xmlConsultaPlazasOp);
    	me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
    	testCase.addScreenShotCurrentStep(u, "clickCP");
    	u.mouseOver(xmlHeader);
    		
    }
    
    
    /**
     * Metodo para seleccionar la opcion 'Consulta de Plazas CCM' del menu 'Administracion'
     * @author Andrea Contreras
     */
    public void clickConsultaPlazasCCMop() {
    		
    	me.scrollToElement(xmlConsultaPlazasCCMop);
    	u.highLight(xmlConsultaPlazasCCMop);  	
    	testCase.addScreenShotCurrentStep(u, "hlCPccm");
    	u.click(xmlConsultaPlazasCCMop);
    	me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
    	testCase.addScreenShotCurrentStep(u, "clickCPccm");
    	u.mouseOver(xmlHeader);
    		
    }
      
    /**
     * Metodo para seleccionar la opcion 'Proveedores' del menu 'Administracion'
     * @author Maria Dueñas
     */
    public void clickProveedoresOp() {
    	
    	me.scrollToElement(xmlProveedores);
    	u.highLight(xmlProveedores);  	
    	testCase.addScreenShotCurrentStep(u, "Proveedores");
    	u.click(xmlProveedores);
    	me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
    	testCase.addScreenShotCurrentStep(u, "clickProveedores");
    	u.mouseOver(xmlHeader);
    		
    }
    
    /**
     * Metodo para seleccionar la opcion 'Usuarios' del menu 'Administracion'
     * @author Andrea Contreras
     */
    public void clickUsuarios() {
        
    	me.scrollToElement(xmlUsuariosOp);
    	u.highLight(xmlUsuariosOp);
    	testCase.addScreenShotCurrentStep(u, "hlUsuarios");
    	u.click(xmlUsuariosOp);  	
    	me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
    	testCase.addScreenShotCurrentStep(u, "clickUsuarios");
    	u.unhighLight(xmlUsuariosOp);
    	u.mouseOver(xmlHeader);
    		
    }
    
    /**
     * Metodo para dar click en opcion Reglas de menu Administrador
     * @author Maria Duenas
     */
    public void clickReglas() {
        
    	me.scrollToElement(xmlReglasOp);
    	u.highLight(xmlReglasOp);
    	testCase.addScreenShotCurrentStep(u, "hlReglas");
    	u.click(xmlReglasOp);  	
    	me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
    	testCase.addScreenShotCurrentStep(u, "clickReglas");
    	u.unhighLight(xmlReglasOp);
    	u.mouseOver(xmlHeader);
    		
    }
    
	/*
	 * METODOS - Apartado Espacios
	 ******************************************************************************************/
    /**
     * Metodo para seleccionar la opcion 'Espacios' 
     * @author Andrea Contreras
     */
    public void clickEspacios() {
    	
    	u.highLight(xmlEspacios);
    	u.click(xmlEspacios);
    	testCase.addScreenShotCurrentStep(u, "clickEspacios");
    	u.unhighLight(xmlEspacios);
    	
    }
    
    /**
     * Metodo para seleccionar la opcion 'Espacios' en Plaza Oxxo
     * @author Maria Dueñas
     */
    public void clickEspaciosPO() {
    	
    	u.highLight(xmlEspaciosPO);
    	u.click(xmlEspaciosPO);
    	testCase.addScreenShotCurrentStep("clickEspaciosPO");
    	u.unhighLight(xmlEspaciosPO);
    	
    }
    
    /**
     * Metodo para seleccionar la opcion 'Carga Masiva Inventarios' en Plaza Oxxo
     * @author Maria Dueñas
     */
    public void clickCargaMasivaInventarios() {
    	
    	u.highLight(xmlCargaMasivaInv);
    	testCase.addScreenShotCurrentStep(u, "CargaMasivaInventarios");
    	u.unhighLight(xmlCargaMasivaInv);
    	u.click(xmlCargaMasivaInv);
    	testCase.addScreenShotCurrentStep(u, "clickCargaMasivaInventarios");
    	u.mouseOver(xmlHeader);
    }
    
    /**
     * Metodo para dar click en la opcion Consulta Espacios Equipos
     * @author Maria Duenas
     */
    public void clickConsultaEspaciosEquipos() {
    	
		u.highLight(xmlConsultaEspEq);
		testCase.addScreenShotCurrentStep(u, "ConsultaEspaciosEquipos");
		u.unhighLight(xmlConsultaEspEq);
		u.click(xmlConsultaEspEq);
		testCase.addScreenShotCurrentStep(u, "clickConsultaEspaciosEquipos"); 
		u.mouseOver(xmlHeader);
    }
    
    /**
     * Metodo para hacer click en la opcion Consulta de inventarios 
     * @author Maria Duenas
     */
    public void clickConsultaInventarios() {
    	
		u.highLight(xmlConsultaInv);
		testCase.addScreenShotCurrentStep(u, "ConsultaInventarios");
		u.unhighLight(xmlConsultaInv);
		u.click(xmlConsultaInv);
		testCase.addScreenShotCurrentStep(u, "clickConsultaInventarios"); 
		u.mouseOver(xmlHeader);
    }
    
	/*
	 * METODOS - Apartado Pedidos
	 ******************************************************************************************/
	
    /**
     * Metodo para seleccionar la opcion 'Pedidos'
     * @author Maria Dueñas
     */
    public void clickPedidos() {
    	
    	u.highLight(xmlPedidos);
    	u.click(xmlPedidos);
    	testCase.addScreenShotCurrentStep("clickPedidos");
    	u.unhighLight(xmlPedidos);
    	
    }
    
    /**
     * Metodo para seleccionar la opcion 'Pedidos' en Plaza Oxxo
     * @author Maria Dueñas
     */
    public void clickPedidosOP() {
    	
    	u.highLight(xmlPedidosPO);
    	u.click(xmlPedidosPO);
    	testCase.addScreenShotCurrentStep("clickPedidosPO");
    	u.unhighLight(xmlPedidosPO);
    	
    }
    
    /**
     * Metodo para seleccionar la opcion 'Pedidos' en Estafeta
     * @author Maria Dueñas
     */
    public void clickPedidosE() {
    	
    	u.highLight(xmlPedidosE);
    	u.click(xmlPedidosE);
    	testCase.addScreenShotCurrentStep("clickPedidosE");
    	u.unhighLight(xmlPedidosE);
    	
    }
    
    /**
     * Metodo para seleccionar la opcion 'Pedidos' en Marca
     * @author Maria Dueñas
     */
    public void clickPedidosM() {
    	
    	me.scrollToElement(xmlPedidosM);
    	u.highLight(xmlPedidosM);
    	u.click(xmlPedidosM);
    	testCase.addScreenShotCurrentStep("clickPedidosM");
    	u.unhighLight(xmlPedidosM);
    	
    }
    
    //---------------------------------------------------------------------------------------------
    
    /**
     * Metodo para seleccionar opcion 'Crear Pedidos' del menu 'Pedidos'
     * @author Maria Dueñas
     */
    public void clickCrearPedidosOp() {

    	me.scrollToElement(xmlCrearPedidosOp);
    	u.highLight(xmlCrearPedidosOp);
    	u.click(xmlCrearPedidosOp);
    	testCase.addScreenShotCurrentStep("clickCrearPedidosOp");
    	u.mouseOver(xmlHeader);
    	u.unhighLight(xmlCrearPedidosOp);

    }
    
    /**
     * MÃƒÂ©todo para seleccionar la opcion 'Bandeja Pedidos' del menu 'Pedidos'
     * @author Maria Dueñas
     */
    public void clickBandejaPedidos() {
    	
    	u.highLight(xmlBandejaPedidos);
    	u.click(xmlBandejaPedidos);
    	testCase.addScreenShotCurrentStep("clickBandejaPedidos");
    	u.mouseOver(xmlHeader);
    	u.unhighLight(xmlBandejaPedidos);

    }
    
    /**
     * Metodo para seleccionar la opcion 'Tipo Pedido' del menu 'Pedidos'
     * @author Andrea Contreras
     */
    public void clickTipoPedido() {
    	
    	me.scrollToElement(xmlTipoPedidoOp);
    	u.highLight(xmlTipoPedidoOp);  	
    	testCase.addScreenShotCurrentStep("hlTP");
    	u.click(xmlTipoPedidoOp);
    	me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
    	testCase.addScreenShotCurrentStep("clickTP");
    	u.mouseOver(xmlHeader);
      
      }

    /*	
     * Metodo para seleccionar la opcion 'Consulta de direcciÃ³n de envÃ­os' del menu 'Pedidos'
     * @author Maria Dueñas
     */
    public void clickConsultaDirEnvios() {
    	
    	u.highLight(xmlConsultaDirEnvio);
    	u.click(xmlConsultaDirEnvio);
    	testCase.addScreenShotCurrentStep(u, "clickConsultaDirEnvios");
    	u.mouseOver(xmlHeader);
    	u.unhighLight(xmlConsultaDirEnvio);


    }
    
	/*
	 * METODOS - Apartado POPs
	 ******************************************************************************************/
    /**
     * Metodo para seleccionar la opcion 'POPs'
     * @author Andrea Contreras
     */
    public void clickPops() { 
        
	    u.highLight(xmlPops);
	    testCase.addScreenShotCurrentStep(u, "hlPops");
	    u.click(xmlPops);
	    testCase.addScreenShotCurrentStep(u, "clickPops");
	    u.unhighLight(xmlPops);
    
    }
    
    /**
     * Metodo para seleccionar la opcion 'POPs' con admin marca
     * @author Maria Dueñas
     */
    public void clickPopsM() { 
    	
    	me.scrollToElement(xmlPopsMAdmin);
    	u.highLight(xmlPopsMAdmin);
    	u.mouseOver(xmlPopsMAdmin);
    	u.click(xmlPopsMAdmin);
	    u.highLight(xmlPopsM);
	    testCase.addScreenShotCurrentStep(u, "hlPopsM");
	    u.click(xmlPopsM);
	    testCase.addScreenShotCurrentStep(u, "clickPopsM");
	    u.unhighLight(xmlPopsM);
    
    }
    //---------------------------------------------------------------------------------------------
    /**
     * Metodo para seleccionar la opcion 'POPs' del menu 'POPs'
     * @author Andrea Contreras
     */
    public void clickPopsOp() {

    	me.scrollToElement(xmlPopsOp);
	    u.highLight(xmlPopsOp);
	    testCase.addScreenShotCurrentStep(u, "hlPopsOp");
	    u.click(xmlPopsOp);   	    
	    me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
	    testCase.addScreenShotCurrentStep(u, "clickPopsOp");
	    u.unhighLight(xmlPopsOp);
	    u.mouseOver(xmlHeader);
    
    } 

    /**
     * Metodo para seleccionar la opcion 'Tipo POPs' del menu 'POPs'
     * @author Andrea Contreras
     */
    public void clickTipoPopsOp() { 
    
    	me.scrollToElement(xmlTipoPopsOp);
	    u.highLight(xmlTipoPopsOp);
	    testCase.addScreenShotCurrentStep(u, "hlTipoPopsOp");
	    u.click(xmlTipoPopsOp);   
	    u.mouseOver(xmlHeader);
	    testCase.addScreenShotCurrentStep(u, "clickTipoPopsOp");
	    u.unhighLight(xmlTipoPopsOp);
    
    }
    
    /**
     * Metodo para seleccionar la opcion 'Cargas Masivas' del menu 'POPs'
     * @author Andrea Contreras
     */
    public void clickCargasMasivasPopsOp() {
    
    	me.scrollToElement(xmlCargaMasivaPopsOp);
	    u.highLight(xmlCargaMasivaPopsOp);
	    testCase.addScreenShotCurrentStep(u, "hlCmPopsOp");
	    u.click(xmlCargaMasivaPopsOp);   
	    u.mouseOver(xmlHeader);
	    testCase.addScreenShotCurrentStep(u, "clickCmPopsOp");
	    u.unhighLight(xmlCargaMasivaPopsOp);
    
    }

    
	/*
	 * METODOS - Apartado Reportes
	 ******************************************************************************************/
    /**
     * Metodo para dar click en la opcion Reportes de AdminOxxo y AdminBara
     * @author Andrea Contreras
     */
    public void clickReportes() {
    	boolean a = existElement(xmlReportes);
    	if (a == true)
    	   u.click(xmlReportes);
           testCase.addScreenShotCurrentStep(u, "clickReportes");
    	
    }
    
    /**
     * Metodo para dar click en la opcion Reportes de Plaza OXXO
     * @author Maria Dueñas
     */
    public void clickReportesPO() {
    	boolean a = existElement(xmlReportesPO);
    	if (a == true)
    	   u.click(xmlReportesPO);
           testCase.addScreenShotCurrentStep("clickReportesPO");
    	
    }
    
    /**
     * Metodo para dar click en la opcion Reportes de AdminMarca
     * @author Maria Dueñas
     */
    public void clickReportesM() {
    	boolean a = existElement(xmlReportesM);
    	if (a == true)
    	   u.click(xmlReportesM);
           testCase.addScreenShotCurrentStep("clickReportesM");
    	
    }
    
    //---------------------------------------------------------------------------------------------
    
    
    /**
     * Metodo para dar click en la opcion Reporte de cobertura de grupo de espacios
     * @author Maria Dueñas
     */
    public void clickReporteCoberturaGrupoEspacios() {
	
    	boolean a = existElement(xmlReporteCGE);
    	if (a == true)
    		me.scrollToElement(xmlReporteCGE);
    	    u.highLight(xmlReporteCGE);
    	    testCase.addScreenShotCurrentStep(u, "hlReporteCGE");
    	    u.click(xmlReporteCGE);  
    	    me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
    	    testCase.addScreenShotCurrentStep(u, "clickReporteCGE");
    	    //u.unhighLight(xmlReporteCGE);
    	    u.mouseOver(xmlHeader);
    	
    }
    
    
    
	/*
	 * METODOS - Adicionales
	 * En este apartado estaran los mÃƒÂ©todos adicionales que podemos utilizar en otros metodos,
	 * como ejemplo agrego el mÃƒÂ©todo que valida si un elemento existe y regresa true o false 
	 ******************************************************************************************/
    
	public boolean existElement(By element) {
    	
    	boolean exist = false;
    	
    	try {
    		u.getDriver().findElement(element);
    		exist = true;
    	}catch (Exception e) {
    		exist = false;
    	}
    	
		return exist;
     	
    }
    
}
