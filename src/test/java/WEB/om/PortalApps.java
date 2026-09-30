package WEB.om;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class PortalApps {
	
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	
	String url = "https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf";
	String url2 = "https://10.182.92.88:8443/AccessControl/pages/login_form.jsf"; 
	
	/*
	 * Localizadores conexion no privada
	 ******************************************************************************************/
	By xmlMensaje = By.xpath("//*[@id=\"main-message\"]/h1");                      
	By xmlAdvanced = By.xpath("//*[@id=\"details-button\"]");
	By xmlProceed = By.xpath("//*[@id=\"proceed-link\"]");
	
	//By xmlMoreInformation = By.cssSelector("span#moreInfoContainer > a");//By.xpath("//*[@id='moreInfoContainer']/a");
	//By xmlGoOnPage = By.xpath("//*[@id='overridelink']");
	
	By xmlMoreInformation = By.xpath("//*[text()='More information']");
	By xmlGoOnPage = By.xpath("//*[text()='Go on to the webpage (not recommended)']");
	
	/*
	 * Localizadores Inicio de sesion Inventarios Espacios
	 ******************************************************************************************/
	
	By xmlUser = By.xpath("//*[@id=\"login:userName\"]");
	By xmlPassword = By.xpath("//*[@id=\"login:userPass\"]");
	By xmlLogginButton = By.xpath("//*[@id=\"login:loginButton\"]");
	By xmlUserPassInv = By.className("mensaje_error");
	
	By xmlTitulo = By.xpath("//div[@id='header']/div[1]/div/h1[contains(text(),'Portal de Aplicaciones')]");
	By xmlNameUser = By.xpath("//*[@id=\"header\"]/div[2]/div[2]");
	By xmlCerrarSesion = By.xpath("//*[@id='sesion']/li[3]/a");
	
	By xmlLogout = By.xpath("//*[@id='menuHeader']/a[2]");
	By xmlCerrarSesionSIMOTI = By.xpath("//*[@id='header']//ul/li[2]/a");
	
	/* 
	 * Localizadores Menu Principal Portal Aplicaciones
	 ******************************************************************************************/
	//Modulos
	
    By xmlFinYadm= By.xpath("//h3[contains(text(),'Finanzas y Administraci�n')]"); 
	
	
	By xmlContainer = By.xpath("/html/body/div/div");
	By xmlPortalIEP = By.xpath("//a[contains(text(),'Inventario de Espacios Publicitarios V2')]");
	By xmlPortalSARv3 = By.xpath("//a[contains(text(),'Sistema de administracion de riesgos V3')]");
	By xmlPortalRemesas = By.xpath("//a[contains(text(),'Portal Remesas')]");
	By xmlPortalEdi = By.xpath("//*[@id=\"homeSubview:j_idt61\"]/div/div[2]/div[1]/div[1]/div[2]/div/ul/li[18]/a");	
	By xmlSIMOTI = By.xpath("//*[text()='SISTEMA DE MONITOREO DE TIENDAS']");
	By xmlSIMOTI_Peru = By.xpath("//*[text()='SISTEMA DE MONITOREO DE TIENDAS (PERU)']");
	By xmlSIMOTI_Chile= By.xpath("//*[text()='SISTEMA DE MONITOREO DE TIENDAS (CHILE)']");
	By xmlSIMOTI_Colombia= By.xpath("//*[text()='SISTEMA DE MONITOREO DE TIENDAS (COLOMBIA)']");
	By xmlPortalCatalogosSegmentosTienda = By.xpath("//div[@class='envelope modulo btop_azul']//a[text()='PORTAL DE CAT�LOGOS DE SEGMENTOS DE TIENDA']");
	By xmlPortalReporteCNT = By.xpath("//div[@class='envelope modulo btop_azul']//a[text()='Reporte CNT']");
	By xmlCobroServiciosPerfiles = By.xpath("//*[text()='Portal de Cobro de Servicios Perfiles']");
	By xmlMonitorObligacionesFiscales = By.xpath("//*[text()='Monitor de Obligaciones Fiscales']");
	By xmlFiniquitoTiendas = By.xpath("//li/a[contains(text(),'FINIQUITO EN TIENDA')]");
	
	
	/*
	 * Localizadores para IE Monitor de Obligaciones Fiscales
	 */
	By xmlMoreInformationIE = By.xpath("//*[text()='More information']");
	By xmlGoOnPageIE = By.xpath("//*[text()='Go on to the webpage (not recommended)']");
	
	/*
	 * Constructor
	 ******************************************************************************************/
	
	public PortalApps(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}
	
	/*
	 * METODOS 
	 ******************************************************************************************/
	
	/**
	 * Método para obtener url de Portal de aplicaciones
	 * @return url Regresa la url del portal
	 * @author María Dueñas
	 */
	public String getUrl() {
		return url;
	}
	
	
	/**
	 * Metodo para validar el acceso al Portal de Aplicaciones
	 * @return url Regresa la url del portal
	 * @author Andrea Contreras
	 */
	public boolean validarAccesoPortalAplicaciones() {
		boolean exist = me.existElement(xmlTitulo);
		
		if(exist == true) {
			u.highLight(xmlTitulo);
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("accesoPortalAplicaciones");
			u.unhighLight(xmlTitulo);
		}
		
		return exist;
	}
	
	
	
	/**
	 * Metodo para validar el acceso al Portal de Aplicaciones
	 * @return url Regresa la url del portal
	 * @author Andrea Contreras
	 */
	public boolean visualizarPortalAplicaciones() {
		boolean exist = me.existElement(xmlTitulo);
	    u.hardWait(1);		
		return exist;
	}

	
	/**
	 * Método para obtener url de Portal de aplicaciones
	 * @return url Regresa la url del portal
	 * @author María Dueñas
	 */
	public boolean validarModuloFinanzasAdministracion() {
		boolean exist = me.existElement(xmlFinYadm);
		u.hardWait(3);
		if(exist == true) {
			u.scrollToElement(xmlFinYadm);
			u.highLight(xmlFinYadm);
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("Finanzas_y_administracion");
		}
		
		return exist;
	}
	
	
	
	/**
	 * Método para obtener xpath de mensaje de "Usuario y/o xontraseña invalido"
	 * @return xpath
	 * @author María Dueñas
	 */
	public By getxmlUserPassInv() {
		
		u.highLight(xmlUserPassInv);
		testCase.addScreenShotCurrentStep(u, "alerta");
		u.unhighLight(xmlUserPassInv);
		
		return xmlUserPassInv;
		
	}

	/**
	 * Método para saltar ventana de "Conexión no privada"
	 * @author María Dueñas
	 */
	public void proceedToPortal() {
    	
    	Boolean bandera = false;
    	try {
    		u.getDriver().findElement(xmlMensaje);
    		bandera = true;
    	}catch (Exception e) {
    		bandera = false;
    	}
    	
    	if (bandera) {
    		u.click(xmlAdvanced);
        	u.click(xmlProceed);
    	}
    	
    }
	

	/**
	 * Metodo para saltar ventana de "Conexion no privada" con Internet Explorer v2
	 * @author Maria Due�as
	 */
	public void proceedToPortalIE() {
    	
    	Boolean bandera = false;
    	try {
    		u.getDriver().findElement(xmlMoreInformation);
    		bandera = true;
    	}catch (Exception e) {
    		bandera = false;
    	}
    	
    	if (bandera) {
    		u.click(xmlMoreInformation);
    		//u.hardWait(2);   		
        	u.click(xmlGoOnPage);
    	}
    	
    }


	/**
	 * Método para iniciar sesión, ingresa usuario y contrasñea
	 * @param dato Nombre de usuario
	 * @param dato2 Contraseña
	 * @author María Dueñas
	 */
	public void logginInventarioEspacios(String dato, String dato2) {
		
		u.highLight(xmlUser);
		u.clear(xmlUser);
		u.sendKeys(xmlUser,dato);
		
		u.highLight(xmlPassword);
		u.clear(xmlPassword);
		u.sendKeys(xmlPassword,dato2);
		
		u.highLight(xmlLogginButton);
		testCase.addScreenShotCurrentStep(u,"inputs");
		u.click(xmlLogginButton);
		//u.hardWait(2);
		u.waitForLoadPage();
		//testCase.addScreenShotCurrentStep(u, "fin");
		
	}
	
	
	/**
	 * Método para iniciar sesión, ingresa usuario y contrasñea
	 * @param dato Nombre de usuario
	 * @param dato2 Contraseña
	 * @author María Dueñas
	 */
	public void logginSinCaptura(String dato, String dato2) {
		
		u.highLight(xmlUser);
		u.clear(xmlUser);
		u.sendKeys(xmlUser,dato);
		//u.sendKeys(xmlUser,dato);
		
		u.highLight(xmlPassword);
		u.sendKeys(xmlPassword,dato2);
		u.highLight(xmlLogginButton);
		u.click(xmlLogginButton);
		//u.hardWait(2);
		u.waitForLoadPage();
		//testCase.addScreenShotCurrentStep(u, "fin");
		
	}
    
    /**
     * Método para intentar iniciar sesión, si el sistema no permite el acceso se intenta tres veces y luego
     * se recarga la pagina para evitar bloquear el usuario 
     * @param dato Usuario
     * @param dato2 Contraseña
     * @author María Dueñas
     */
	 public void cicloLogin(String dato, String dato2) {
	    	
	    	Boolean bandera = false;
	    	Integer cont = 0;
	    	
	    	do {
	    		
	    		cont += 1;
	    		if (cont < 4) {
	    			
	    			logginInventarioEspacios(dato, dato2);
	        		try {
	            		u.getDriver().findElement(xmlNameUser);
	            		bandera = true;
	            	}catch (Exception e) {
	            		bandera = false;
	            	}
	    		}else {
	    			//u.getDriver().navigate().refresh();
	    			u.getDriver().navigate().to(getUrl());
	    			cont = 0;
	    		}
	    		
	    	}while(!bandera);
	    	u.hardWait(3);
	    	testCase.addScreenShotCurrentStep("fin");
	    	
	    }
	 
	    /**
	     * Método para intentar iniciar sesión, si el sistema no permite el acceso se intenta tres veces y luego
	     * se recarga la pagina para evitar bloquear el usuario 
	     * @param dato Usuario
	     * @param dato2 Contraseña
	     * @author María Dueñas
	     */
		 public void cicloLogin2(String dato, String dato2) {
		    	
		    	Boolean bandera = false;
		    	Integer cont = 0;
		    	
		    	do {
		    		
		    		cont += 1;
		    		if (cont < 4) {
		    			
		    			logginSinCaptura(dato, dato2);
		        		try {
		            		u.getDriver().findElement(xmlNameUser);
		            		bandera = true;
		            	}catch (Exception e) {
		            		bandera = false;
		            	}
		    		}else {
		    			//u.getDriver().navigate().refresh();
		    			u.getDriver().navigate().to(getUrl());
		    			cont = 0;
		    		}
		    		
		    	}while(!bandera);
		    	
		    	
		    }
    
    /**
     * Metodo para validar que se ingrese correctamente al portal de aplicaciones,
     * valida el nombre del usuario
     * @param usuario
     * @return Retorna true o false si encontro los elementos que se muestran al ingresar al portal.
     * @author Maria Due�as
     */
    public Boolean accesoCorrectoPortal(String usuario) {
		 Boolean exist = false;
		 String user =  "HOLA, " + usuario.toUpperCase().trim();
		 if (me.validarVisualizacion(xmlContainer) == true && u.getText(xmlNameUser).toUpperCase().equals(user.toUpperCase())) {
			 exist = true;
		 }
		 return exist;
	 }
    
    /**
     * Metodo para seleccionar la opcion 'Inventario de Espacios Publicitarios V2'
     * @author Andrea Contreras
     */
    public void clickPortalInventarioEspacios() {
    	u.waitWebElement(xmlPortalIEP, 6, 5);
    	u.scrollToElement(xmlPortalIEP);
    	u.highLight(xmlPortalIEP);
    	testCase.addScreenShotCurrentStep("hlPortalIEP");
		u.click(xmlPortalIEP);	
    	me.explicitWaitToBeInvisible(Duration.ofSeconds(15));
    	testCase.addScreenShotCurrentStep("clickPortalIEP");
    }
    
    /**
     * Metodo para seleccionar la opcion 'Inventario de Espacios Publicitarios V2'
     * @author Andrea Contreras
     */
    public void clickPortalInventarioEspaciosSinScreen() {
    	u.waitWebElement(xmlPortalIEP, 6, 5);
    	u.scrollToElement(xmlPortalIEP);
    	u.highLight(xmlPortalIEP);
		u.click(xmlPortalIEP);	
    	me.explicitWaitToBeInvisible(Duration.ofSeconds(15));
    }
    
    /**
     * Metodo para seleccionar la opcion 'Finiquito de Tiendas'
     * @author Andrea Contreras
     */
    public void clickFiniquitoTiendas() {
    	u.waitWebElement(xmlFiniquitoTiendas, 6, 5);
    	u.scrollToElement(xmlFiniquitoTiendas);
    	u.highLight(xmlFiniquitoTiendas);
    	testCase.addScreenShotCurrentStep("hlPortalFT");
		u.click(xmlFiniquitoTiendas);	
    	u.waitForLoadPage();
    	u.hardWait(2);
    	testCase.addScreenShotCurrentStep("clickPortalFT");
    }
    
    /**
     * Metodo para seleccionar la opcion 'Monitor Obligaciones Fiscales'
     * @author Jorge Ruiz
     */
    public void clickPortalMonitorObligacionesFiscales(int op) {
    	u.waitWebElement(xmlMonitorObligacionesFiscales, 6, 5);
    	u.scrollToElement(xmlMonitorObligacionesFiscales);
    	List<WebElement> monitores = u.getDriver().findElements(xmlMonitorObligacionesFiscales);
    	int cont = 0;
    	for(WebElement i: monitores)
    	{
    		if(cont == op)
    		{
    	    	testCase.addScreenShotCurrentStep(u, "clickPortalMOF");
    			i.click();	
    	    	testCase.addScreenShotCurrentStep(u, "clickPortalMOF");
    	    	break;
    		}
    		cont++;
    	}
    	u.hardWait(3);
    }

    /**
���� * Metodo para saltar ventana de "Conexion no privada" con Internet Explorer en Monito de Obligaciones Fiscales
���� * @author Jorge Ruiz
���� */
    public void proceedToPortalIEsecureSite()
    {
    	Boolean bandera = false;
    	try 
    	{
    		 u.getDriver().findElement(xmlMoreInformationIE);
    		 bandera = true;
    	}
    	catch(Exception e)
    	{
    		bandera = false;
    	}
    	if(bandera)
    	{
    		u.click(xmlMoreInformationIE);
    		u.click(xmlGoOnPageIE);
    	}
    }

    public void clickSARv3() {
    	u.waitWebElement(xmlPortalSARv3, 6, 5);
    	u.highLight(xmlPortalSARv3);
    	testCase.addScreenShotCurrentStep(u, "hlPortalSARv3");
		u.click(xmlPortalSARv3);		
    	u.waitForLoadPage();
    	u.hardWait(2);
    	testCase.addScreenShotCurrentStep(u, "clickPortalSARv3");
    }
    public void clickPortalRemesas() {
    	u.waitWebElement(xmlPortalRemesas, 6, 5);
    	u.highLight(xmlPortalRemesas);
    	testCase.addScreenShotCurrentStep(u, "xmlPortalRemesass");
		u.click(xmlPortalRemesas);		
    	u.waitForLoadPage();
    	u.hardWait(2);
    	testCase.addScreenShotCurrentStep(u, "xmlPortalRemesas2");
    }
    
    public void clickReporteConciliacionRemesas() {

        u.waitWebElement(xmlPortalEdi, 6, 5);
        u.highLight(xmlPortalEdi);
        testCase.addScreenShotCurrentStep(u, "PortalEdi");
        u.click(xmlPortalEdi);      
        u.waitForLoadPage();
        u.hardWait(2);
        testCase.addScreenShotCurrentStep(u, "clickPortalEdi");
    }
	
    public void clickPortalSIMOTI() {
        u.waitWebElement(xmlSIMOTI, 6, 5);
        u.highLight(xmlSIMOTI);
        testCase.addScreenShotCurrentStep(u, "xmlSIMOTI");
        u.click(xmlSIMOTI);        
        u.waitForLoadPage();
        u.hardWait(2);
        testCase.addScreenShotCurrentStep(u, "xmlSIMOTI2");
    }
    
    public void clickPortalSIMOTI_PERU() {
        u.waitWebElement(xmlSIMOTI_Peru, 6, 5);
        u.highLight(xmlSIMOTI_Peru);
        testCase.addScreenShotCurrentStep(u, "xmlSIMOTI_Peru");
        u.click(xmlSIMOTI_Peru);        
        u.waitForLoadPage();
        u.hardWait(2);
        testCase.addScreenShotCurrentStep(u, "xmlSIMOTI_Peru3");
    }
    
    public void clickPortalCobroServiciosPerfiles() {
        u.waitWebElement(xmlCobroServiciosPerfiles, 6, 5);
        u.highLight(xmlCobroServiciosPerfiles);
        testCase.addScreenShotCurrentStep(u, "xmlCobroServicios");
        u.click(xmlCobroServiciosPerfiles);        
        u.waitForLoadPage();
        u.hardWait(2);
        testCase.addScreenShotCurrentStep(u, "xmlCobroServicios2");
    }
    
    /**
     * Metodo para seleccionar la opcion 'PORTAL DE CAT�LOGOS DE SEGMENTOS DE TIENDA'
     * @author Andrea Contreras
     */
    public void clickPortalCatalogosSegmentosTienda() {
        u.waitWebElement(xmlPortalCatalogosSegmentosTienda, 6, 5);
        u.highLight(xmlPortalCatalogosSegmentosTienda);
        testCase.addScreenShotCurrentStep(u, "hlPortalCST");
        u.click(xmlPortalCatalogosSegmentosTienda);        
        u.waitForLoadPage();
        u.hardWait(2);
        testCase.addScreenShotCurrentStep(u, "clickPortalCST");
    }
    
    /**
     * Metodo para seleccionar la opcion 'Reporte CNT'
     * @author Andrea Contreras
     */
    public void clickPortalReporteCNT() {
    	
        u.waitWebElement(xmlPortalReporteCNT, 6, 5);
        u.scrollToElement(xmlPortalReporteCNT);
        u.highLight(xmlPortalReporteCNT);
        testCase.addScreenShotCurrentStep(u, "hlPortalReporteCNT");
        u.click(xmlPortalReporteCNT);        
        u.waitForLoadPage();
        u.hardWait(2);
        testCase.addScreenShotCurrentStep(u, "clickPortalReporteCNT");
   
    }
    
    /**
     * Metodo para dar click en aplicativo Shimoti Chile
     * @author Maria Due�as
     */
    public void clickSimotiChile() {
    	u.waitWebElement(xmlSIMOTI_Chile, 6, 5);
    	u.highLight(xmlSIMOTI_Chile);
    	testCase.addScreenShotCurrentStep(u, "hlPortalSimotiChile");
		u.click(xmlSIMOTI_Chile);		
    	u.waitForLoadPage();
    	u.hardWait(2);
    	testCase.addScreenShotCurrentStep(u, "clickSimotiChile");
    }
    
    /**
     * Metodo para dar click en aplicativo Shimoti Chile
     * @author Maria Due�as
     */
    public void clickMonitorObligacionesFiscales() {
    	u.waitWebElement(xmlMonitorObligacionesFiscales, 6, 5);
    	u.highLight(xmlMonitorObligacionesFiscales);
    	testCase.addScreenShotCurrentStep(u, "hlPortalmonitor");
		u.click(xmlMonitorObligacionesFiscales);		
    	u.waitForLoadPage();
    	u.hardWait(2);
    	testCase.addScreenShotCurrentStep(u, "clickmonitor");
    }
    
    /**
     * Metodo para dar click en aplicativo Shimoti Colombia
     * @author Maria Due�as
     */
    public void clickSimotiColombia() {
    	u.waitWebElement(xmlSIMOTI_Colombia, 6, 5);
    	u.scrollToElement(xmlSIMOTI_Colombia);
    	u.highLight(xmlSIMOTI_Colombia);
    	testCase.addScreenShotCurrentStep(u, "hlPortalSimotiColombia");
		u.click(xmlSIMOTI_Colombia);		
    	u.waitForLoadPage();
    	u.hardWait(2);
    	testCase.addScreenShotCurrentStep(u, "clickSimotiColombia");
    }
    
    /**
     * Metodo para cerrar sesion
     * @author Maria Due�as
     */
    public void logout() {
    	me.scrollToElement(xmlCerrarSesion);
    	u.highLight(xmlCerrarSesion);
    	testCase.addScreenShotCurrentStep(u, "CerrarSesion");
    	u.unhighLight(xmlCerrarSesion);
    	u.mouseOverAndClick(xmlCerrarSesion);
    	testCase.addScreenShotCurrentStep(u, "clickCerrarSesion");
    }
    
    /**
     * Metodo para cerrar sesion
     * @author Maria Due�as
     */
    public void logoutSimoti() {
    	me.scrollToElement(xmlCerrarSesionSIMOTI);
    	u.highLight(xmlCerrarSesionSIMOTI);
    	testCase.addScreenShotCurrentStep(u, "CerrarSesion");
    	u.unhighLight(xmlCerrarSesionSIMOTI);
    	u.mouseOverAndClick(xmlCerrarSesionSIMOTI);
    	u.hardWait(3);
    	testCase.addScreenShotCurrentStep(u, "clickCerrarSesion");
    }
    
    /**
     * Metodo para seleccionar el apartado administraci�n
     * @author David Valdez
     * @param String:submenu - opci�n a seleccionar
     */
    public void finiquitoSelectAdministracion(String submenu) {
        By adminis = By.xpath("//div[.='ADMINISTRACION']");
        By submenuF = By.xpath("//div[.='"+submenu+"']");

        u.mouseOver(adminis);
        u.highLight(submenuF);        
        testCase.addScreenShotCurrentStep(u, "Administraci�n");
        u.click(submenuF);
    }

    /**
     * Metodo para seleccionar el apartado administraci�n
     * @author David Valdez
     */
    public void modificarSueldoEquipo() {
        By buttonModify = By.xpath("//input[@value='Modificar']");

        u.highLight(buttonModify);        
        testCase.addScreenShotCurrentStep(u, "Administraci�n");
        u.click(buttonModify);
    }

    /**
     * Metodo para seleccionar el apartado administraci�n
     * @author David Valdez
     * @param String:newvalue - nuevo valor
     */
    public void modificarSueldoEquipoyGuardar(String newvalue) {
        By campo = By.xpath("//*[@id=\"ay0\"]");
        By buttonModify = By.xpath("//input[@value='Guardar']");

        u.highLight(campo);
        testCase.addScreenShotCurrentStep(u, "Valor anterior");
        u.clear(campo);
        u.sendKeys(campo, newvalue);

        u.highLight(buttonModify);         
        testCase.addScreenShotCurrentStep(u, "Valor nuevo");
        u.click(buttonModify);
    }
    
    /**
	 * Metodo para obtener url2 (con host) de Portal de aplicaciones
	 * @return url Regresa la url del portal
	 * @author Andrea Cano
	 */
	public String getUrl2() {
		return url2;
	}
    
}
