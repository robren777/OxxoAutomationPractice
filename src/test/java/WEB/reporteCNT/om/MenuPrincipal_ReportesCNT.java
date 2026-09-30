package WEB.reporteCNT.om;

import java.io.IOException;
import java.time.Duration;
import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.By;
import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

/**
 * Localizadores y m�todos del 'men� principal' del portal Reportes CNT
 ******************************************************************************************/
public class MenuPrincipal_ReportesCNT {
	
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	
    /*
    * Localizadores informaci�n del usuario y perfil.
    ******************************************************************************************/

	By xmlTituloPantalla = By.xpath("//div[@id='header']//h1[text()='REPORTES CNT']");
    By xmlInfoUsuario = By.id("saludoHeader");  
    By xmlSubmenu = By.xpath("//ul[@class='ddsubmenustyle']");
    By xmlLoadimage = By.xpath("//div[@id='loadimage']/div[@style='display: block;']");
	
	/*
	 * Localizadores men�
	 *****************************************************************************************/
	
	By xmlAdministradorFuncional = By.xpath("//div[@id='ddtopmenubar']/ul/li/a[text()='Administrador Funcional']");
	By xmlUsuariosConsulta = By.xpath("//div[@id='ddtopmenubar']/ul/li/a[text()='Usuario Consulta']");
	By xmlAdministradorTecnico = By.xpath("//div[@id='ddtopmenubar']/ul/li/a[text()='Administrador T�cnico']");
	
	By xmlAdministradorFuncional2 = By.xpath("//div[@id='ddtopmenubar']/ul/li/a[text()='Administrador funcional']");
	By xmlAdministradorTecnico2 = By.xpath("//div[@id='ddtopmenubar']/ul/li/a[text()='Administrador t�cnico']");
	
	/*
	 * Localizadores Submen� - Administrador Funcional 
	 ******************************************************************************************/

	By xmlAdministracionAF = By.xpath("//ul[@class='ddsubmenustyle']//a[text()='Administraci�n']");	
	By xmlConsultaDatosCNT = By.xpath("//ul[@class='ddsubmenustyle']//a[text()='Consulta datos CNT']");
	By xmlVolverFavoritos = By.xpath("//ul[@class='ddsubmenustyle']//a[text()='Volver a Favoritos']");
	By xmlAuditoriaDatosCNT = By.xpath("//ul[@class='ddsubmenustyle']//a[text()='Auditor�a de datos CNT']");
	By xmlReporteUtilizacion = By.xpath("//ul[@class='ddsubmenustyle']//a[text()='Reporte Utilizaci�n']");
	By xmlCatalogoUsuariosAF = By.xpath("//ul[@class='ddsubmenustyle']//a[text()='Catalogo de usuarios']");
	
	/*
	 * Localizadores Submen� - Usuarios Consulta
	 ******************************************************************************************/	
	
	By xmlReporteEstadistico = By.xpath("//ul[@class='ddsubmenustyle']//a[text()='Reportes Estadistico']");
	By xmlEstadistica = By.xpath("//ul[@class='ddsubmenustyle']//a[text()='Estad�stica']");
	
	
	/*
	 * Localizadores Submen� - Administrador Tecnico
	 ******************************************************************************************/
	
	By xmlAdministracionAT = By.xpath("//ul[@class='ddsubmenustyle']//a[text()='Administraci�n']");
	By xmlCatalogoEntidades = By.xpath("//ul[@class='ddsubmenustyle']//a[text()='Catalogo de entidades']");
	By xmlCatalogoUsuariosAT = By.xpath("//ul[@class='ddsubmenustyle']//a[text()='Catalogo de usuarios']");
	

	/*
	 * Constructor
	 ******************************************************************************************/
	
	/**
	 * Constructor de la clase Menu
	 * @author Andrea Contreras
	 * @param u (SeleniumUtil): objeto de la clase 
	 * @param testCase (testCase): objeto de la clase
	 */
	public MenuPrincipal_ReportesCNT(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}
	
	
	/*
	 * M�todos Header
	 ******************************************************************************************/
	
	/**
	 * Metodo que valida se muestren la pantalla principal del portal 'Reporte CNT'
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
	 * Metodo que valida se muestren las opciones del menu principal de 'Reportes CNT'
	 * @return True o false si se muestran las opciones
	 * @author Andrea Contreras
	 */
	public Boolean visualizarMenu() {

		boolean validacion = false, adminT, adminF, usuarioC;
		
		try {
			adminT = u.isDisplayed(xmlAdministradorFuncional);
		}catch(Exception e) {
			adminT = u.isDisplayed(xmlAdministradorFuncional2);
		}
		
		try {
			adminF = u.isDisplayed(xmlAdministradorTecnico);
		}catch(Exception e) {
			adminF = u.isDisplayed(xmlAdministradorTecnico2);
		}
		 
		usuarioC = u.isDisplayed(xmlUsuariosConsulta); 

		if(adminT == true && adminF == true && usuarioC == true) {
			validacion = true;
		}	
		
		testCase.addScreenShotCurrentStep(u, "verificarOpciones");

		return validacion;
	}
	
	/**
	 * Metodo que esperar hasta que no se muestre el simbolo de cargando
	 * @author Andrea Contreras
	 * @throws IOException 
	 * @throws EncryptedDocumentException 
	 */
	public void esperarImagenCarga() {
		
		boolean exist;
		do {
		       if (me.existElement(xmlLoadimage)) {
		    	   exist = true;
		       }else {
		    	   exist = false;
		       } 
	       }while(exist);
		
		u.hardWait(2);
	}
	

	
	
	/*
	 * M�todos Administrador Funcional
	 ******************************************************************************************/
	
    /** M�todo para dar click en men� 'Administrador Funcional'
     * @author Andrea Contreras
     */
    public void clickAdministradorFuncional() {
        
    	try {
        	u.highLight(xmlAdministradorFuncional); 
        	testCase.addScreenShotCurrentStep(u, "hlAdminFun");
        	u.mouseOver(xmlAdministradorFuncional); 
    	}catch(Exception e) {
    		u.highLight(xmlAdministradorFuncional2);    	
        	testCase.addScreenShotCurrentStep(u, "hlAdminFun");
        	u.mouseOver(xmlAdministradorFuncional2); 
    	}
    	
    	testCase.addScreenShotCurrentStep(u, "clickAdminFun");

    	//u.unhighLight(xmlAdministradorFuncional);
    		
    }
    
    /** M�todo para dar click en submen� 'Consulta datos CNT'
    * @author Andrea Contreras
    */
    public void clickConsultaDatosCNT() {
       
   	    u.highLight(xmlConsultaDatosCNT);
   	    testCase.addScreenShotCurrentStep(u, "hlDatosCnt");
   	    u.click(xmlConsultaDatosCNT); 
   	    testCase.addScreenShotCurrentStep(u, "clickDatosCnt");
   	    //u.unhighLight(xmlAdministradorFuncional);
   		
    }
    
    
	/*
	 * M�todos Usuarios Consulta
	 ******************************************************************************************/
   
    /** M�todo para dar click en men� 'Usuarios Consulta'
     * @author Andrea Contreras
     */
    public void clickUsuariosConsulta() {
        
    	u.highLight(xmlUsuariosConsulta);
    	testCase.addScreenShotCurrentStep(u, "hlUsuarioCons");
    	u.mouseOver(xmlUsuariosConsulta); 
    	testCase.addScreenShotCurrentStep(u, "clickUsuarioCons");
    	//u.unhighLight(xmlAdministradorFuncional);   		
    	me.explicitWaitVisibilityOfElement(xmlSubmenu, Duration.ofSeconds(10));
    }
    
    /** M�todo para dar click en submen� 'ReporteEstadistico'
     * @author Andrea Contreras
     */
    public void clickReporteEstadistico() {
        
    	u.highLight(xmlReporteEstadistico);
    	testCase.addScreenShotCurrentStep(u, "hlRepEstad");
    	u.click(xmlReporteEstadistico); 
    	testCase.addScreenShotCurrentStep(u, "clickRepEstad");
    	//u.unhighLight(xmlAdministradorFuncional);
    		
    	u.hardWait(2);
    }
    
    
   
	/*
	 * M�todos Administrador T�cnico
	 ******************************************************************************************/
   
    /** M�todo para dar click en men� 'Administrador T�cnico'
     * @author Andrea Contreras
     */
    public void clickAdministradorTecnico() {
        
        
     	try {
     		u.highLight(xmlAdministradorTecnico);
        	testCase.addScreenShotCurrentStep(u, "hlAdminTec");
        	u.mouseOver(xmlAdministradorTecnico); 
     	}catch(Exception e) {
     		u.highLight(xmlAdministradorTecnico2);
        	testCase.addScreenShotCurrentStep(u, "hlAdminTec");
        	u.mouseOver(xmlAdministradorTecnico2);
     	}
     	u.hardWait(1);
    	testCase.addScreenShotCurrentStep(u, "clickAdminTec");    	
    	//me.explicitWaitVisibilityOfElement(xmlSubmenu, 10);
    		
    }
    
    
    /** M�todo para dar click en men� 'Catalogo de Entidades'
     * @author Andrea Contreras
     */
    public void clickCatalogoEntidades() {
        
     		u.highLight(xmlCatalogoEntidades);
        	testCase.addScreenShotCurrentStep(u, "hlCatEnt");
        	u.click(xmlCatalogoEntidades); 
    	    testCase.addScreenShotCurrentStep(u, "clickCatEnt"); 
    		
    }
    
    

   
    

}
