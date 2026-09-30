package WEB.cnt.om;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Login {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;

	/**
	 * Localizadores y metodos del Menu Principal o Home del portal CNT
	 * 
	 ******************************************************************************************/
	/*
	 * Localizadores login portal CNT login
	 *****************************************************************************************/
	
	By portal = By.xpath("//img[@src='/tiendas/image/cnt.gif']");
	By user = By.xpath("//input[@name=\"p_user\"]");
	By pass = By.xpath("//input[@name=\"p_pass\"]");
	By CONTINUAR = By.xpath("//input[@value=\"CONTINUAR\"]");

	/*
	 * Localizadores login portal CNT elegir Plaza
	 *****************************************************************************************/
	By plazas = By.xpath("//select[@name=\"p_cr_plaza\"]");
	By CONTINUAR2 = By.xpath("//input[@name=\"CONTINUAR\"]");
	
	By cargaMasivaSegTiendas = By.xpath("//center[4]/a[1]");
	
	/*
	 * Localizadores login portal CNT elegir Distrito, Tienda
	 *****************************************************************************************/
	By Distrito = By.xpath("//select[@name=\"vid_Campos1\"]");
	By Tienda = By.xpath("//select[@name=\"p_cr_tienda\"]");
	By SeleccionarPlaza = By.xpath("//center[3]/a[1]");

	/*
	 * Localizadores login portal CNT tienda ventana emergente
	 *****************************************************************************************/
	
	
	By TiendaE = By.xpath("//*");
//	By TiendaE = By.xpath("//*[text()=\"Seleccionar Tienda\"]");
	By SeleccionarTienda = By.xpath("//center[1]/a[1]");
	By DatosGenerales = By.xpath("//center[1]/a[2]");
	By DatosOperacion = By.xpath("//center[1]/a[3]");
	By PermisosContratos = By.xpath("//center[1]/a[4]");
	By FormatoEntorno = By.xpath("//center[1]/a[5]");
	By Planogramacion = By.xpath("//center[1]/a[6]");
	By CondicionesPropiedad = By.xpath("//center[1]/a[7]");
	By Sugerencias = By.xpath("//center[1]/a[8]/font");
	By Notificacion = By.xpath("//center[1]/a[9]/font");
	
 
	/*
	 * Localizadores contenedor principal
	 *****************************************************************************************/

	By xmlContainer = By.xpath("//*[@id=\"content-nav\"]");
	By xmlPEP = By.xpath("//*[@id=\"tituloInicio\"]");

	
	
	/**
	 * M�todo para iniciar sesi�n, ingresa usuario y contrase�a
	 * 
	 * @param dato  Nombre de usuario
	 * @param dato2 Contrase�a
	 * @author Baltazar Lugo
	 */
	public void logginInventarioEspacios(String dato, String dato2) {

		u.highLight(user);
		u.sendKeys(user, dato);

		u.highLight(pass);
		u.sendKeys(pass, dato2);
		u.highLight(CONTINUAR);
		testCase.addScreenShotCurrentStep("Login");
		u.click(CONTINUAR);
		u.waitForLoadPage();
		testCase.addScreenShotCurrentStep("Resultado");

	}
	
	 /**
	    * Metodo para validar que se muestren la seleccion de plaza y boton continuar
	    * @author Andrea Contreras
	    * @return true o false
	    */
	public boolean validarComboBoton() {
		boolean exist = false;
		if(me.existElement(plazas) && me.existElement(CONTINUAR)) {
			u.highLight(plazas);
			u.highLight(CONTINUAR);
			testCase.addScreenShotCurrentStep("validarCB");
			u.unhighLight(plazas);
			u.unhighLight(CONTINUAR);
			exist = true;
		}		
		return exist; 		
	}
	
	
	 /**
	    * Metodo para validar que se muestren los combos de distrito y tiendas
	    * @author Andrea Contreras
	    * @return true o false
	    */
	public boolean validarDistritoTiendas() {
		boolean exist = false;
		if(me.existElement(Distrito) && me.existElement(Tienda)) {
			u.highLight(Distrito);
			u.highLight(Tienda);
			testCase.addScreenShotCurrentStep("validarDT");
			u.unhighLight(Distrito);
			u.unhighLight(Tienda);
			exist = true;
		}		
		return exist; 		
	}
	
	 /**
	    * Metodo para seleccionar plaza de la pantalla despues del login
	    * @author Baltazar Lugo
	    * @param plazaE String (nombre de la plaza)
	    * @return N/A
	    */
	public void clickSeleccionarPlaza(String plazaE) {
		u.highLight(plazas);
		Select plazasS = new Select(u.getDriver().findElement(plazas));
		plazasS.selectByVisibleText(plazaE);
		testCase.addScreenShotCurrentStep(u, "Plaza");
		u.click(CONTINUAR);
	}
	
	/**
	 * Metodo para dar click en Carga Masiva Segmento Tienda
	 * @author Maria Due�as
	 */
	public void clickCargaMasivaSegmentoTienda() {
		u.highLight(cargaMasivaSegTiendas);
		testCase.addScreenShotCurrentStep(u, "CargaMasivaSegmentoTienda");
		u.unhighLight(cargaMasivaSegTiendas);
		u.click(cargaMasivaSegTiendas);
	}

	/**
	    * Metodo para seleccionar distrito y tienda
	    * @author Baltazar Lugo
	    * @param Distrito1 (String), Tienda( String)
	    * @return N/A
	    */
	public void clickSeleccionarDistritoTienda(String Distrito1, String Tienda1) {
		u.highLight(Distrito);
		Select DistritoS = new Select(u.getDriver().findElement(Distrito));
		DistritoS.selectByVisibleText(Distrito1);

		u.highLight(Tienda);
		Select TiendaS = new Select(u.getDriver().findElement(Tienda));
		TiendaS.selectByVisibleText(Tienda1);
		testCase.addScreenShotCurrentStep(u, "DyT");
		u.click(CONTINUAR);
	}

	/**
	 * Metodo para seleccionar tienda y no seleccionar distrito
	 * @author Mario De Le�n
	 * @param  Tienda( String)
	 * @return N/A
	 */
	public void clickSeleccionarTienda(String Tienda1) {
		u.highLight(Tienda);
		Select TiendaS = new Select(u.getDriver().findElement(Tienda));
		TiendaS.selectByVisibleText(Tienda1);
		testCase.addScreenShotCurrentStep(u, "DyT");
		u.click(CONTINUAR);
	}
	
	/**
	 * Metodo para dar click en la opcion Seleccionar Plaza 
	 * @author Maria Due�as
	 */
	public void clickRegresarSeleccionarPlaza() {
		u.highLight(SeleccionarPlaza);
		testCase.addScreenShotCurrentStep(u, "SeleccionarPalza");
		u.unhighLight(SeleccionarPlaza);
		u.click(SeleccionarPlaza);
		testCase.addScreenShotCurrentStep(u, "clickSeleccionarPlaza");
	}
	
	
	/**
	    * Metodo para validar que se muestren las opciones Seleccionar Tienda, Datos Generales, Datos de Operaci�n, Formato y Entorno, Planogramaci�n, Condiciones en Propiedad y Sugerencias
	    * despues de seleccionar distrito y plaza validando
	    * @author Andrea Contreras
	    * @param N/A
	    * @return true o false si se muestran las opciones
	    */
	public boolean validarOpcionesVentanaEmergente() {
		String winHandleBefore = u.getDriver().getWindowHandle();
    	// Switch to new window opened
		for(String winHandle : u.getDriver().getWindowHandles()){
			u.getDriver().switchTo().window(winHandle);
		}
		u.waitForLoadPage();
		u.hardWait(10);
		u.getDriver().manage().window().maximize();
		u.getDriver().switchTo().frame("framemenu");
		// Perform the actions on new window
		boolean validacion = false;
		
		if(me.existElement(SeleccionarTienda) && me.existElement(DatosGenerales) && me.existElement(DatosOperacion)
				&& me.existElement(CondicionesPropiedad) && me.existElement(Planogramacion) 
				&& me.existElement(FormatoEntorno) && me.existElement(Sugerencias)) {
			
		String st = u.getText(SeleccionarTienda).trim().toUpperCase();
		String dg = u.getText(DatosGenerales).trim().toUpperCase();
		String dt = u.getText(DatosOperacion).trim().toUpperCase();
		String fe = u.getText(FormatoEntorno).trim().toUpperCase();
		String p = u.getText(Planogramacion).trim().toUpperCase();
		//String pc = u.getText(PermisosContratos).trim().toUpperCase();
		//String n = u.getText(Notificacion).trim().toUpperCase();
		String cp = u.getText(CondicionesPropiedad).trim().toUpperCase();
		String s = u.getText(Sugerencias).trim().toUpperCase();
		//System.out.print(st+dg+dt+fe+p+pc+n+cp+s);
		
		/*if(st.equals("SELECCIONAR TIENDA") && dg.equals("DATOS GENERALES") && dt.equals("DATOS DE OPERACION") 
				&& pc.equals("PERMISOS Y CONTRATOS") && fe.equals("FORMATO Y ENTORNO")
				&& p.equals("PLANOGRAMACION") && cp.equals("CONDICIONES DE PROPIEDAD")
				&& s.equals("SUGERENCIAS") && n.equals("NOTIFICACION")) {*/
			
		if(st.equals("SELECCIONAR TIENDA") && dg.equals("DATOS GENERALES") && dt.equals("DATOS DE OPERACION") 
				    && fe.equals("FORMATO Y ENTORNO") && p.equals("PLANOGRAMACION") 
				    && cp.equals("CONDICIONES DE PROPIEDAD") && s.equals("SUGERENCIAS") ) {
			
			validacion = true;
		}
		
		}
		
		testCase.addScreenShotCurrentStep(u, "ventanaTienda");
		u.getDriver().switchTo().window(winHandleBefore);
        return validacion;
	}
	
	/**
	    * Metodo para validar pantalla emergente despues de seleccionar distrito y plaza validando con un highlight que se presente la pantalla
	    * @author Baltazar Lugo
	    * @param N/A
	    * @return N/A
	    */
	public void ScreenVentanaEmergente() {
		String winHandleBefore = u.getDriver().getWindowHandle();

		// Perform the click operation that opens new window

		// Switch to new window opened
		for(String winHandle : u.getDriver().getWindowHandles()){
			u.getDriver().switchTo().window(winHandle);
		}
		
		
//		u.waitForLoadPage();
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
		u.highLight(TiendaE);
		// Perform the actions on new window
		testCase.addScreenShotCurrentStep(u, "VentanaTienda");
		// Close the new window, if that window no more required
//		u.hardWait(6);
		u.getDriver().close();
//
//		// Switch back to original browser (first window)
		u.getDriver().switchTo().window(winHandleBefore);


	}
	
	/**
	 * Metodo para obtener Window Handle
	 * @return Retorna WindowHandle
	 * @author Maria Due�as
	 */
	public String getScreen() {
		String winHandleBefore = u.getDriver().getWindowHandle();
		return winHandleBefore;
	}
	
	/**
	 * Método para validar el acceso a la url de CNT
	 * @return Regresa falso o verdadero si encuentra la aplicacion
	 * @author Andrea Contreras
	 */
	public Boolean accesoCorrectoPortal() {
		Boolean exist = false;
		if (me.existElement(portal)) {
			u.highLight(portal);
			testCase.addScreenShotCurrentStep("accesoPortalCNT");
			exist = true;
			u.highLight(portal);
		}
		return exist;
	}

	
	/**
	 * Constructor
	 ******************************************************************************************/

	public Login(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}
	
	
	/*
	 * METODOS - Adicionales En este apartado estaran los métodos adicionales que
	 * podemos utilizar en otros metodos, como ejemplo agrego el método que valida
	 * si un elemento existe y regresa true o false
	 ******************************************************************************************/

	public boolean existElement(By element) {

		boolean exist = false;

		try {
			u.getDriver().findElement(element);
			exist = true;
		} catch (Exception e) {
			exist = false;
		}

		return exist;

	}

}