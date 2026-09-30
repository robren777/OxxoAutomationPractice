package WEB.inventarioEspacios.om;

import java.time.Duration;
import org.openqa.selenium.By;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class UsuariosIEP {
	
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;
	
	/*Localizadores Pantalla Asociar POP a Plazas
	 * Administraci�n > Asociar POP a Plazas
	 ******************************************************************************************/
	
	/*
	 * Localizadores - Header de la P�gina
	 ******************************************************************************************/
	By xmlTituloPantalla = By.xpath("//div[@id='tituloEscritorio']/span");
	
	/*
	 * Localizadores - Apartado 'Relacionar un POP con sus respectivas plazas'
	 ******************************************************************************************/
	
	By xmlFiltros = By.xpath("//mat-expansion-panel");
	
	//Localizadores de input Usuario
	By xmlUsuario = By.xpath("//form[@id='formFiltroUsuario']/div[1]/div[1]/div[1]//input");
	By xmlUsuarioPanel = By.xpath("//form[@id='formFiltroUsuario']/div[1]/div[1]/div[@class='form-group-input row'][1]");

	
	//Localizadores de lista desplegable Plaza
	//By xmlLbPlaza = By.xpath("//mat-select[@name='pop']");
	//By xmlPlazaList = By.xpath("//form[@id='formFiltroPlazasPop']/div[1]/div/div[2]/mat-form-field/div/div/div/mat-select/div/div[2]");
	By xmlPlazaPanel = By.xpath("//form[@id='formFiltroUsuario']/div[1]/div[1]/div[@class='form-group-input row'][2]");
	
	//Localizadores de input Nombre Empleado
	By xmlNomEmp = By.xpath("//form[@id='formFiltroUsuario']/div[1]/div[1]/div[2]//input");
	By xmlNomEmpPanel = By.xpath("//form[@id='formFiltroUsuario']/div[1]/div[2]/div[@class='form-group-input row'][1]");

	
	//Localizadores de lista desplegable Cargo
	By xmlCargoPanel = By.xpath("//form[@id='formFiltroUsuario']/div[1]/div[2]/div[@class='form-group-input row'][2]");
	
	//Localizadores de los Botones 'Guardar' y 'Limpiar'
	By xmlBtnGuardar = By.xpath("//div[@class='row div-izquierda']/div[2]/button");
	By xmlBtnLimpiar = By.xpath("//div[@class='row div-izquierda']/div[1]/button");

	/*
	 * Localizadores - Apartado 'Grid Resultados'
	 ******************************************************************************************/
	By xmlTabla = By.xpath("//table");
	
	By xmlUsuarioHdr = By.xpath("//table/thead/tr/th[1]");
	By xmlNomEmpHdr = By.xpath("//table/thead/tr/th[2]");
	By xmlPlazaHdr = By.xpath("//table/thead/tr/th[3]");
	By xmlContactoHdr = By.xpath("//table/thead/tr/th[4]");
	By xmlCargoHdr = By.xpath("//table/thead/tr/th[5]");
	
	
	//Localizadores del header de la tabla
    //By xmlPopsAsColumns = By.xpath("//div[@class='contenedor']/div[2]/table/thead/tr/th");
    By xmlResultadosFilas = By.xpath("//table/tbody/tr");
	//By xmlPopsAsChecks = By.xpath("//div[@class='contenedor']/div[2]/table/tbody/tr/td[1]/mat-checkbox/label/span[1]/input");
    //By xmlPopsAsNumPlazas = By.xpath("//div[@class='contenedor']/div[2]/table/tbody/tr/td[4]");
	
    //Localizador para obtener el nombre de los empleados de todos los registros
    By xmlRegistroNombreEmp = By.xpath("//table/tbody/tr/td[2]");
    
/*
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase UsuariosIEP
	 * @author Andrea Contreras
	 * @param u (SeleniumUtil): objeto de la clase 
	 * @param testCase (testCase): objeto de la clase
	 */
	public UsuariosIEP(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u,testCase);
	}
	
	
	/*
	 * METODOS
	 ******************************************************************************************/
	/**
	 * Metodo que valida se muestren la pantalla 'Usuarios'
	 * @return True o false si se muestran la pantalla
	 * @author Andrea Contreras
	 */
	public Boolean visualizarPantalla() {

		boolean validacion = false;
		String pantallaActual = u.getText(xmlTituloPantalla);

		if (pantallaActual.contains("Usuarios")) {
			validacion = true;
		}

		return validacion;
	}
	
	 /**
	 * Metodo que valida se muestren los filtros de la pantalla
	 * @author Andrea Contreras
	 * @return Regresa true si se muestran los fitros o false si no se muestran
	 */ 
	public Boolean visualizarFiltros(){
		 
		boolean validacion = false, usuario, plaza, numEmp, cargo, pantalla;
		 
		u.mouseOver(xmlFiltros);
		pantalla = visualizarPantalla();
		usuario = m.isEnable(xmlUsuarioPanel);
		plaza = m.isEnable(xmlPlazaPanel);
		numEmp = m.isEnable(xmlNomEmpPanel);
        cargo = m.isEnable(xmlCargoPanel);
        
        u.highLight(xmlTituloPantalla);
        u.highLight(xmlFiltros);
        
        
        testCase.addScreenShotCurrentStep(u, "verificarCampos");  
        
        if(usuario == true && plaza == true && numEmp == true  && cargo == true
        		&& pantalla == true) {     
        	 validacion = true; 
        	 u.unhighLight(xmlTituloPantalla);
        	 u.unhighLight(xmlFiltros);     	 
        }
        
        return validacion;
   }
	 /**
	 * Metodo que valida que se muestren registros existendes ordenados de manera ascendente por el campo Nombre de Empleado
	 * @author Andrea Contreras
	 * @return Regresa true si se muestran las tablas o false si no se muestran
	 */ 
	public Boolean visualizarDatosTabla(){
		 
		boolean validacion = false, resultados, orden;
        
        u.mouseOver(xmlTabla);
        resultados = m.existMultipleElements(xmlResultadosFilas);
        
        /*
		List<WebElement> allOptions = u.getDriver().findElements(xmlRegistroNombreEmp);

		for (WebElement option : allOptions) {
			String op = option.getText().toLowerCase();
			if (!op.contains(valor.toLowerCase())) {
				valResults = false;
				break;
			}
		}*/
        
        if(resultados == true) {
       	 validacion = true; 
        }
        
        testCase.addScreenShotCurrentStep(u,"resultadoExistentes");
        
        return validacion;
   }

	
	//*************************************** FILTROS *************************************
	
	
	/**
	 * Metodo para ingresar un valor en el campo 'Usuario'
	 * @param usuario (String): usuario a ingresar
	 * @author Andrea Contreras
	 */
	public void typeUsuario(String usuario) {

		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));		
		u.highLight(xmlUsuario);
		u.sendKeys(xmlUsuario, usuario);
		testCase.addScreenShotCurrentStep(u, "typeUsuario");
		u.unhighLight(xmlUsuario);	
	}
	
	/**
	 * Metodo para ingresar un valor en el campo 'Nombre de empleado'
	 * @param nomEmp (String): nombre de empleado a ingresar
	 * @author Andrea Contreras
	 */
	public void typeNombreEmpleado(String nomEmp) {

		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));		
		u.highLight(xmlNomEmp);
		u.sendKeys(xmlNomEmp, nomEmp);
		testCase.addScreenShotCurrentStep(u, "typeNomEmp");
		u.unhighLight(xmlNomEmp);
		
	}
	

	//*************************************** BOTONES *************************************
	
    
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
     * Metodo para dar click en el boton 'Guardar'
	 * @author Andrea Contreras
	 */
    public void clickGuardarBtn(){ 
    	
    	m.explicitWaitToBeClickable(xmlBtnGuardar,Duration.ofSeconds(10));
	    u.highLight(xmlBtnGuardar);
	    testCase.addScreenShotCurrentStep(u, "btnBuscar");
	    u.click(xmlBtnGuardar);	
	    m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
	    testCase.addScreenShotCurrentStep(u, "clickBuscar");
	    u.unhighLight(xmlBtnGuardar);
		
   }

	
	/*
	 * METODOS - Apartado 'Grid - Resultados'
	 ******************************************************************************************/	

    
	/**
	 * Metodo que valida se muestren los resultados de la busqueda en la tabla y lo campos: Nombre del Usuario, Numero de empleado, Plaza, Contacto y Cargo
	 * @return True o false si se muestran los resultados de la busqueda
	 * @author Andrea Contreras
	 */
	public Boolean visualizarResultadosBusqueda() {

		boolean usuario, nomEmp, plaza, contacto, cargo, resultados, validacion = false;
		
		u.scrollToElement(xmlTabla);	
		
		By xmlUsuarioHdr = By.xpath("//table/thead/tr/th[1]");
		By xmlNomEmpHdr = By.xpath("//table/thead/tr/th[2]");
		By xmlPlazaHdr = By.xpath("//table/thead/tr/th[3]");
		By xmlContactoHdr = By.xpath("//table/thead/tr/th[4]");
		By xmlCargoHdr = By.xpath("//table/thead/tr/th[5]");
		
		usuario = u.isDisplayed(xmlUsuarioHdr);
		nomEmp = u.isDisplayed(xmlNomEmpHdr);
		plaza = u.isDisplayed(xmlPlazaHdr);
		contacto = u.isDisplayed(xmlContactoHdr);
		cargo = u.isDisplayed(xmlCargoHdr);
		resultados = m.existMultipleElements(xmlResultadosFilas);		
		
		if (usuario == true && nomEmp == true && plaza == true && contacto == true
				&& cargo == true && resultados== true){
			validacion = true;
			u.highLight(xmlTabla);
		}
		
		testCase.addScreenShotCurrentStep(u, "verificarResultados");
		

		return validacion;
		
	}
}
