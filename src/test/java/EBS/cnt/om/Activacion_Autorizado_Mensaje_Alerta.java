package EBS.cnt.om;


import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import EBS.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;


public class Activacion_Autorizado_Mensaje_Alerta {

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
	By user = By.xpath("//input[@name=\"p_user\"]");
	By pass = By.xpath("//input[@name=\"p_pass\"]");
	By CONTINUAR = By.xpath("//input[@value=\"CONTINUAR\"]");

	/*
	 * Localizadores login portal CNT elegir Plaza
	 *****************************************************************************************/
	By plazas = By.xpath("//select[@name=\"p_cr_plaza\"]");
	By CONTINUAR2 = By.xpath("//input[@name=\"CONTINUAR\"]");
	/*
	 * Localizadores login portal CNT elegir Distrito, Tienda
	 *****************************************************************************************/
	By Distrito = By.xpath("//select[@name=\"vid_Campos1\"]");
	By Tienda = By.xpath("//select[@name=\"p_cr_tienda\"]");

	/*
	 * Localizadores login portal CNT tienda ventana emergente
	 *****************************************************************************************/
	
	
	By TiendaE = By.xpath("//*");
//	By TiendaE = By.xpath("//*[text()=\"Seleccionar Tienda\"]");
	/*
	 * Localizadores contenedor principal
	 *****************************************************************************************/

	By xmlContainer = By.xpath("//*[@id=\"content-nav\"]");
	By xmlPEP = By.xpath("//*[@id=\"tituloInicio\"]");
	
	
	By xmlPermisosContra = By.xpath("//html/body/center/a[4]");		

	
	
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
		testCase.addScreenShotCurrentStep(u, "Login");
		u.click(CONTINUAR);
		u.waitForLoadPage();
		testCase.addScreenShotCurrentStep(u, "Resultado");

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

	/*
	 * Localizadores información del plazas
	 ******************************************************************************************/

	By xmlUsuario = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/p");
	By xmlNegocio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[1]");
	By xmlPerfilAdm = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[1]");
	By xmlPeriodo = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[2]");

	/*
	 * Localizadores Menú Principal Inventarios Espacios
	 ******************************************************************************************/

	By xmlMenus = By.xpath("//mat-sidenav[@id = 'sidenav']");
	By xmlHeader = By.xpath("//mat-sidenav-content[@id='content-nav']/app-header");

	/*
	 * Localizadores Apartado del Menú - Administración
	 ******************************************************************************************/
	By xmlAdministracion = By.xpath("//div[@class= \"texto-menu\"]/a[1]/div");

	// OPCIONES
	By xmlAsociarPopPlazaOp = By.xpath("//label[contains(text(),'Asociar POP a Plazas')]");
	By xmlAsociarPopProveedorOp = By.xpath("//label[contains(text(),'Asociar POP a Proveedor')]");
	By xmlPeriodoOp = By.xpath("//label[contains(text(),'Periodo')]");

	/*
	 * Localizadores Apartado del Menú - Espacios
	 ******************************************************************************************/

	By xmlEspacios = By.xpath("//div[@class= \"texto-menu\"]/a[2]/div");

	/*
	 * Localizadores Apartado del Menú - Pedidos
	 ******************************************************************************************/

	By xmlPedidos = By.xpath("//div[@class= \"texto-menu\"]/a[3]/div");

	// OPCIONES
	By xmlCrearPedidosOp = By.xpath("//label[contains(text(),'Crear Pedidos')]");
	By xmlBandejaPedidos = By.xpath("//label[contains(text(), 'Bandeja de Pedidos')]");

	/*
	 * Localizadores Apartado del Menú - POPs
	 ******************************************************************************************/

	By xmlPops = By.xpath("//div[@class= \"texto-menu\"]/a[4]/div");

	// OPCIONES
	By xmlPopsOp = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[1]/a/div");
	By xmlTipoPopsOp = By.xpath("//label[contains(text(),'Tipos de POP')]");
	By xmlCargaMasivaPopsOp = By.xpath("//label[contains(text(),'Carga masiva pops')]");

	/*
	 * Localizadores Apartado del Menú - Reportes
	 ******************************************************************************************/

	By xmlReportes = By.xpath("//div[@class= \"texto-menu\"]/a[5]/div");

	/**
	 * Constructor
	 ******************************************************************************************/

	public Activacion_Autorizado_Mensaje_Alerta(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}

	/*
	 * METODOS - Acceso correcto a Inventario de espacios Publicitarios V2
	 ******************************************************************************************/

	/**
	 * Método para validar el acceso correcto al Inventario de Espacios
	 * Publicitarios
	 * 
	 * @return Regresa falso o verdadero si encuentra los elementos
	 * @author María Dueñas
	 */
	public Boolean accesoCorrectoIEP() {
		Boolean exist = false;
		String pep = "Portal Espacios Publicitarios";
		if (me.validarVisualizacion(xmlContainer) == true && u.getText(xmlPEP).equals(pep)) {
			exist = true;
		}
		return exist;
	}

	/*
	 * METODOS - Información del usuario, negocio y perfil.
	 ******************************************************************************************/

	/**
	 * Método para validar la informacion del usuario, negocio y perfil que se
	 * muestran en el sistema, coincidan con el data provider
	 * 
	 * @param dato, dato2, dato3 Recibe como parametro el usuario, negocio y perfil
	 * @return bandera Regresa un true o false si la informacion coincide
	 * @author María Dueñas
	 */
	public Boolean verificarInformacion(String dato, String dato2, String dato3) {

		me.explicitWaitToBeInvisible(Duration.ofSeconds(10));

		String usuario = u.getText(xmlUsuario).toUpperCase();
		clickMenus();
		u.highLight(xmlUsuario);

		String negocio = u.getText(xmlNegocio).toUpperCase();
		u.highLight(xmlNegocio);

		Boolean bandera = false;

		if (dato.toUpperCase().equals(usuario) && dato2.toUpperCase().equals(negocio)) {
			bandera = true;
		}

		switch (dato3.toUpperCase()) {
		case "ADMINISTRADOR":
			u.highLight(xmlPerfilAdm);
			testCase.addScreenShotCurrentStep(u, "verificarInformacion");
			u.unhighLight(xmlPerfilAdm);
			break;
		case "PLAZA":
			break;
		case "ESTAFETA":
			break;
		}

		u.unhighLight(xmlUsuario);
		u.unhighLight(xmlNegocio);

		return bandera;

	}

	/**
	 * Método para validar la informacion megocio y perfil que se muestran en el
	 * sistema, coincidan con el data provider
	 * 
	 * @param dato Recibe como parametro el negocio
	 * @return bandera Regresa un true o false si la informacion es correcta
	 * @author María Dueñas
	 */
	public Boolean verificarEncabezado(String dato) {

		clickMenus();
		String negocio = u.getText(xmlNegocio);
		u.highLight(xmlNegocio);
		String periodo = "";
		periodo = u.getText(xmlPeriodo);
		u.highLight(xmlPeriodo);

		Boolean bandera = false;
		if (dato.equals(negocio) && periodo != "") {
			bandera = true;
		}

		testCase.addScreenShotCurrentStep(u, "verificarEncabezado");
		u.unhighLight(xmlNegocio);
		u.unhighLight(xmlPeriodo);

		return bandera;

	}

	/*
	 * METODOS - Contenedor Principal
	 ******************************************************************************************/
	/**
	 * Metodo para dar click en el contenedor principal del sistema
	 * 
	 * @author María Dueñas
	 */
	public void clickContainer() {

		u.click(xmlContainer);
		testCase.addScreenShotCurrentStep(u, "clickContainer");

	}

	/*
	 * METODOS - Menú Principal
	 ******************************************************************************************/

	public void clickMenus() {

		do {
			u.mouseOver(xmlMenus);
		} while (u.waitWebElement(xmlMenus, 5, 5).isEnabled() == false);
		testCase.addScreenShotCurrentStep(u, "clickMenuIEP");

	}

	/*
	 * METODOS - Apartado Administración
	 ******************************************************************************************/

	public void clickAdministracion() {

		u.highLight(xmlAdministracion);
		testCase.addScreenShotCurrentStep(u, "hlAdmin");
		u.click(xmlAdministracion);
		testCase.addScreenShotCurrentStep(u, "clickAdmin");
		u.unhighLight(xmlAdministracion);

	}

	// ---------------------------------------------------------------------------------------------

	public void clickAsociarPopPlazaOp() {

		u.highLight(xmlAsociarPopPlazaOp);
		testCase.addScreenShotCurrentStep(u, "hlAsociarPopPlaza");
		u.click(xmlAsociarPopPlazaOp);
		u.mouseOver(xmlHeader);
		testCase.addScreenShotCurrentStep(u, "clickAsociarPopPlaza");
		u.unhighLight(xmlAsociarPopPlazaOp);

	}

	public void clickAsociarPopProveedorOp() {

		u.highLight(xmlAsociarPopProveedorOp);
		testCase.addScreenShotCurrentStep(u, "hlAsociarPopProv");
		u.click(xmlAsociarPopProveedorOp);
		u.mouseOver(xmlHeader);
		testCase.addScreenShotCurrentStep(u, "clickAsociarPopProv");
		u.unhighLight(xmlAsociarPopProveedorOp);

	}

	/**
	 * Método para seleccionar la opción Periodo del menú Administración
	 * 
	 * @author María Dueñas
	 */
	public void clickPeriodoOp() {

		u.highLight(xmlPeriodoOp);
		u.click(xmlPeriodoOp);
		testCase.addScreenShotCurrentStep(u, "clickPeriodoOp");
		u.mouseOver(xmlHeader);
		u.unhighLight(xmlPeriodoOp);

	}

	/*
	 * METODOS - Apartado Espacios
	 ******************************************************************************************/

	public void clickEspacios() {

		u.click(xmlEspacios);
		testCase.addScreenShotCurrentStep(u, "clickEspacios");

	}

	/*
	 * METODOS - Apartado Pedidos
	 ******************************************************************************************/

	/**
	 * Método para seleccionar la opción Pedidos
	 * 
	 * @author María Dueñas
	 */
	public void clickPedidos() {

		u.highLight(xmlPedidos);
		u.click(xmlPedidos);
		testCase.addScreenShotCurrentStep(u, "clickPedidos");
		u.unhighLight(xmlPedidos);

	}

	// ---------------------------------------------------------------------------------------------

	/**
	 * Método para seleccionar opcion Crear Pedidos del menu Pedidos
	 * 
	 * @author María Dueñas
	 */
	public void clickCrearPedidosOp() {

		u.highLight(xmlCrearPedidosOp);
		u.click(xmlCrearPedidosOp);
		testCase.addScreenShotCurrentStep(u, "clickCrearPedidosOp");
		u.mouseOver(xmlHeader);
		u.unhighLight(xmlCrearPedidosOp);

	}

	/**
	 * Método para seleccionar la opcion Bandeja Pedidos del menú Pedidos
	 * 
	 * @author María Dueñas
	 */
	public void clickBandejaPedidos() {

		u.highLight(xmlBandejaPedidos);
		u.click(xmlBandejaPedidos);
		testCase.addScreenShotCurrentStep(u, "clickBandejaPedidos");
		u.mouseOver(xmlHeader);
		u.unhighLight(xmlBandejaPedidos);

	}

	/*
	 * METODOS - Apartado POPs
	 ******************************************************************************************/

	public void clickPops() {

		u.highLight(xmlPops);
		testCase.addScreenShotCurrentStep(u, "hlPops");
		u.click(xmlPops);
		testCase.addScreenShotCurrentStep(u, "clickPops");
		u.unhighLight(xmlPops);

	}

	// ---------------------------------------------------------------------------------------------

	public void clickPopsOp() {

		u.highLight(xmlPopsOp);
		testCase.addScreenShotCurrentStep(u, "hlPopsOp");
		u.click(xmlPopsOp);
		u.mouseOver(xmlHeader);
		testCase.addScreenShotCurrentStep(u, "clickPopsOp");
		u.unhighLight(xmlPopsOp);

	}

	public void clickTipoPopsOp() {

		u.highLight(xmlTipoPopsOp);
		testCase.addScreenShotCurrentStep(u, "hlTipoPopsOp");
		u.click(xmlTipoPopsOp);
		u.mouseOver(xmlHeader);
		testCase.addScreenShotCurrentStep(u, "clickTipoPopsOp");
		u.unhighLight(xmlTipoPopsOp);

	}

	public void clickCargasMasivasPopsOp() {

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

	public void clickReportes() {
		boolean a = existElement(xmlReportes);
		if (a == true)
			u.click(xmlReportes);
		testCase.addScreenShotCurrentStep(u, "clickReportes");

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

	public void clickPermisos() {
		
		u.highLight(xmlPermisosContra);
		u.hardWait(2);
		u.mouseOver(xmlPermisosContra);
		u.hardWait(2);
		u.click(xmlPermisosContra);
		
		
	}
}
