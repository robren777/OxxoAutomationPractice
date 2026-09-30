package WEB.portal_Remesas.om;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Portal_Remesas {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;

	/**
	 * Localizadores y  metodos de portal remesas 
	 * 
	 ******************************************************************************************/
	/*
	 * Localizadores del menu principal de remesas
	 */

	By EnviosDinero= By.xpath("//*[text()=\"Env�os de Dinero\"]");
	By EDineroReportes= By.xpath("//*[text()='Reportes'][@rel='subsubmenu1']");
	By EDineroMontos= By.xpath("//a[contains(@href,'PortalRemesas-web/pages/enviosDeDinero')][text()='Montos']");
	By PortalRemesas = By.xpath("//*[@id=\"homeSubview:j_idt61\"]/div/div[2]/div[1]/div[1]/div[2]/div/ul/li[18]");
	By envioMontosPrin = By.xpath("//*[@id=\"index:tablaFav_data\"]/tr[1]/td/a");
	
	////a[contains(@href,'PortalRemesas-web/pages/enviosDeDinero')][text()='Montos']
	/*
	 * Localizadores login portal CNT Captura de Datos Menu >Formato y Entorno
	 */
	By FyE = By.xpath("//*[@target=\"framedatos\"][4]");
	By TamTienda = By.xpath("//select[@name=\"p_lid_tamanio_tienda\"]");
	By frame = By.xpath("//frame[contains(@src,'app')]");
	By framed = By.xpath("//frame[contains(@src,'/tiendas')][@name=\"framedatos\"]");// frameoper


	/**
	 * Metodo para entrar a EnviosDinero_Reportes_Montos
	 * 
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return
	 */
	public void EnviosDinero_Reportes_Montos() {
//	//	u.getDriver().switchTo().frame("framemenu");
//		String winHandleBefore = u.getDriver().getWindowHandle();
//
//		// Perform the click operation that opens new window
//
//		// Switch to new window opened
//		for (String winHandle : u.getDriver().getWindowHandles()) {
//			u.getDriver().switchTo().window(winHandle);
//		}
//
//		u.hardWait(4);
		u.getDriver().manage().window().maximize();
//		u.highLight(frame);
//		u.getDriver().switchTo().frame("framemenu");
//
		u.highLight(EnviosDinero);
//		testCase.addScreenShotCurrentStep(u, "EnviosDinero");
//		Actions action = new Actions(u.getDriver());
//		action.moveToElement(EnviosDinero).click(EnviosDinero).build().perform();
//		u.mouseOverAndClick(EnviosDinero);
		u.mouseOver(EnviosDinero);
//		u.click(EnviosDinero);
		u.highLight(EDineroReportes);
//		testCase.addScreenShotCurrentStep(u, "EnviosDinero");
		u.mouseOver(EDineroReportes);
//		u.click(EDineroReportes);
     	u.highLight(EDineroMontos);
     	u.mouseOver(EDineroMontos);
		testCase.addScreenShotCurrentStep(u, "EDineroMontos");
		u.click(EDineroMontos);
//		u.getDriver().switchTo().window(winHandleBefore);
//		u.waitForLoadPage();
//		testCase.addScreenShotCurrentStep(u, "Plano2");
		
	}
	/**
	 * Metodo para poner metros en el estacionamiento del oxxo
	 * 
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return
	 */
	public void Estacionamiento(String Esta) {
		String si = "SI";
		String winHandleBefore = u.getDriver().getWindowHandle();

		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

//		u.waitForLoadPage();
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
//		u.highLight(TiendaE);

		u.hardWait(10);
		u.getDriver().manage().window().maximize();
		u.highLight(framed);
		u.getDriver().switchTo().frame("framedatos");
		
//		u.getDriver().switchTo().window(winHandleBefore);
//		u.waitForLoadPage();
		MetodosExtra ME = new MetodosExtra(u, testCase);
//		ME.scrollToElement(Estacionamiento);
//		u.highLight(Estacionamiento);
//		u.clear(Estacionamiento);
//		u.sendKeys(Estacionamiento, Esta);
//		testCase.addScreenShotCurrentStep(u, "Estacionamiento");
//	
	}
	/**
	 * Metodo para seleccionar congelador  para cerveza en NO
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return
	 */
	public void conge() {
		String si = "SI";
		String winHandleBefore = u.getDriver().getWindowHandle();

		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

//		u.waitForLoadPage();
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
//		u.highLight(TiendaE);

		u.hardWait(10);
		u.getDriver().manage().window().maximize();
		u.highLight(framed);
		u.getDriver().switchTo().frame("framedatos");
		
		MetodosExtra ME = new MetodosExtra(u, testCase);
//		ME.scrollToElement(Congelador);
//		u.highLight(Congelador);
//		u.click(Congelador);
//
//        Select tama1 = new Select(u.getDriver().findElement(Congelador));
//        tama1.selectByValue("N");
		testCase.addScreenShotCurrentStep(u, "Cong");
	
		
	}
	
	/**
	 * Metodo para dar clic en alerts despues de notificacion
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return
	 */
	public boolean NotificacionAlert() {
		boolean existenoti=true;
		u.hardWait(4);
//	//	u.getDriver().switchTo().frame("framemenu")
////		WebDriverWait w = new WebDriverWait(u.getDriver(),20);
		
		int x=0;
//		
//		WebDriverWait w = new WebDriverWait(u.getDriver(),20);
do {
	u.hardWait(10);
	if(isAlertPresent()==false) {
		System.out.println("Alert not exists");
	}else { if(isAlertPresent()==true) {
		System.out.println("Alert exists");
		testCase.addScreenShotCurrentStep("foto3");
		u.getDriver().switchTo().alert().accept();
	}
	}
	x++;

//	for (int i = 0; i < 9; i++) {
//			if ((ExpectedConditions.alertIsPresent()) != null) {
//				testCase.addScreenShotCurrentStep("foto3");
//				u.getDriver().switchTo().alert().accept();
//				i = i + 1;
//				System.out.println("for"+i);
//				
//			}
//		}
////			x=x+1;
//	System.out.print("x: "+x);

} while (x<=6);

u.hardWait(15);
//existenoti=me.existElement(notificacion);

return existenoti;

//testCase.addScreenShotCurrentStep("foto3");

//		u.click(Plano);
//		u.getDriver().switchTo().window(winHandleBefore);
//		u.waitForLoadPage();
//		testCase.addScreenShotCurrentStep(u, "Plano2");
		
	}
	public boolean isAlertPresent() 
	{ 
	    try 
	    { 
	    	u.getDriver().switchTo().alert();
	        return true; 
	    }   // try 
	    catch (NoAlertPresentException Ex) 
	    { 
	        return false; 
	    }   // catch 
	}   // isAlertPresent()
		
	
	
	public void seleccionaPortalRemesas() {
		u.highLight(PortalRemesas);
		testCase.addScreenShotCurrentStep("Portal remesas");
		u.click(PortalRemesas);
	}
	
	public void RetiroDinero_reporte_AnalisisDia_notSubmenu() {
		By analisis_por_dia = By.xpath("//*[@id=\"index:tablaFav_data\"]/tr[2]/td/a/span");
		
		u.highLight(analisis_por_dia);
		testCase.addScreenShotCurrentStep("Portal remesas");
		u.click(analisis_por_dia);
	}
	
	public void EnviosDinero_Reportes_Montos_notSubmenu() {
		u.highLight(envioMontosPrin);
		testCase.addScreenShotCurrentStep("Portal remesas");
		u.click(envioMontosPrin);
	}
	
	public void EnviosDinero_Reportes_Rutas_Submenu() {
		By submenu = By.xpath("//*[@id=\"ddtopmenubar\"]/ul/li[2]/a");
		By reportes = By.xpath("//*[@id=\"submenu1\"]/li/a");
		By rutas = By.xpath("//*[@id=\"subsubmenu1\"]/li[2]/a");
		
		u.mouseOver(submenu);
		u.hardWait(1);
		u.mouseOver(reportes);
		u.hardWait(1);
		u.mouseOver(rutas);
		
		
		u.highLight(rutas);
		testCase.addScreenShotCurrentStep("Portal remesas");
		u.click(rutas);
	}
	
	public void RetiroDinero_Reportes_Montos_Submenu() {
		By submenu = By.xpath("//*[@id=\"ddtopmenubar\"]/ul/li[3]/a");
		By reportes = By.xpath("//*[@id=\"submenu2\"]/li[1]/a");
		By rutas = By.xpath("//*[@id=\"subsubmenu2\"]/li[1]/a");
		
		u.mouseOver(submenu);
		u.hardWait(1);
		u.mouseOver(reportes);
		u.hardWait(1);
		u.mouseOver(rutas);
		
		
		u.highLight(rutas);
		testCase.addScreenShotCurrentStep("Portal remesas");
		u.click(rutas);
	}
	
	public void Configuracion_EnvioyRetiro_Atributos_Submenu() {
		By submenu = By.xpath("//*[@id=\"menu3\"]");
		By reportes = By.xpath("//*[@id=\"submenu3\"]/li[1]/a");
		By horas = By.xpath("//*[@id=\"subsubmenu3\"]/li[4]/a");
		
		u.mouseOver(submenu);
		u.hardWait(1);
		u.mouseOver(reportes);
		u.hardWait(1);
		u.mouseOver(horas);
		
		
		u.highLight(horas);
		testCase.addScreenShotCurrentStep("Portal remesas");
		u.click(horas);
	}
	
	public void Configuracion_EnvioyRetiro_Perfiles_Submenu() {
		By submenu = By.xpath("//*[@id=\"menu3\"]");
		By reportes = By.xpath("//*[@id=\"submenu3\"]/li[1]/a");
		By perfiles_usuario = By.xpath("//*[@id=\"subsubmenu3\"]/li[3]/a");
		
		u.mouseOver(submenu);
		u.hardWait(1);
		u.mouseOver(reportes);
		u.hardWait(1);
		u.mouseOver(perfiles_usuario);
		
		
		u.highLight(perfiles_usuario);
		testCase.addScreenShotCurrentStep("Portal remesas");
		u.click(perfiles_usuario);
	}
	
	public void EditarPerfilesUsuario(String nombre) {
		By nombre_user = By.xpath("//*[.='"+ nombre +"']");
		By buttonEditar = By.xpath("//*[@id=\"form1\"]/table[3]/tbody/tr/td[1]/a[3]");
		
		u.click(nombre_user);
		u.highLight(buttonEditar);
		testCase.addScreenShotCurrentStep("Portal remesas");
		u.click(buttonEditar);
	}
	
	public void EliminarPerfilUsuario(String nombre) {
		By nombre_user = By.xpath("//*[.='"+ nombre +"']");
		By buttonEliminar = By.xpath("//*[@id=\"form1\"]/table[3]/tbody/tr/td[1]/a[2]");
		
		u.click(nombre_user);
		u.highLight(buttonEliminar);
		testCase.addScreenShotCurrentStep("Portal remesas");
		u.click(buttonEliminar);
	}
	
	public void ModificaDatosEditar() {
		By atributos_compartidos = By.xpath("//*[@id=\"form1:arbolPerfiles:2_2\"]/span/div/div");
		
		u.click(atributos_compartidos);
		u.highLight(atributos_compartidos);
		testCase.addScreenShotCurrentStep("Portal remesas");		
	}
	
	public void presionaGuardar() {
		By buttonGuardar = By.xpath("//*[@id=\"form1:gridOutputPanel_content\"]/table[4]/tbody/tr/td[4]/a[2]");
		
		u.highLight(buttonGuardar);
		testCase.addScreenShotCurrentStep("Portal remesas");
		u.click(buttonGuardar);
	}
	
	public void VerificaTablaNombrePerfil() {
		By buttonGuardar = By.xpath("//*[@id=\"form1:tablaPerfiles\"]/div[1]/table");
		
		u.scrollToElement(buttonGuardar);
		u.highLight(buttonGuardar);
		testCase.addScreenShotCurrentStep("Portal remesas");
	}
	
	public boolean verify_not_name_in_table(String name) {
		By tabla = By.xpath("//*[@id=\"form1:tablaPerfiles\"]/div[1]/table");
		u.scrollToElement(tabla);
		u.highLight(tabla);
		testCase.addScreenShotCurrentStep("Portal remesas");
		
		try {
			By existElement = By.xpath("//*[@id=\"form1:tablaPerfiles\"]/div[1]/table/*[contains(text(),'"+ name +"')]"); // Nodo padre: //*[@id="form1:tablaPerfiles"]/div[1]/table
			return true;
		}
		catch(Exception ex) {
			return false;
		}
	}
	
	public void Agregar_perfilesUsuario() {
		By buttonAgregar = By.xpath("//*[@id=\"form1\"]/table[3]/tbody/tr/td[1]/a[1]");
		
		u.highLight(buttonAgregar);
		testCase.addScreenShotCurrentStep("Portal remesas");
		u.click(buttonAgregar);
	}
	
	public void SeleccionaProveedores_perfilesUsuario() {
		By proveedores = By.xpath("//*[@id=\"form1:perfilesCheckbox\"]/option[1]");
		
		u.highLight(proveedores);
		testCase.addScreenShotCurrentStep("Portal remesas");
		u.click(proveedores);
	}
	
	public void EscribeNombrePerfil(String nombre) {
		By nombrePerfil = By.xpath("//*[@id=\"form1:nombreperfil\"]");
		
		u.highLight(nombrePerfil);
		u.sendKeys(nombrePerfil, nombre);
		u.hardWait(1);
		testCase.addScreenShotCurrentStep("Portal remesas");
	}
	
	public void seleccionaOpcionesPerfil() {
		By opciones = By.className("ui-chkbox");
		By recuadro_opc = By.xpath("//*[@id=\"form1:arbolPerfiles\"]/ul");
		
		List<WebElement> miLista = u.listElements(opciones);
		for(WebElement b : miLista){
			try {
				b.click();			
			}catch(Exception ex) {
				System.out.println("No se ha podido dar click a una opci�n");
			}
		}
		
		u.highLight(recuadro_opc);
		testCase.addScreenShotCurrentStep("Portal remesas");
	}
	
	public void seleccionaGuardarPerfil() {
		By buttonGuardar = By.xpath("//*[@id=\"form1:gridOutputPanel_content\"]/table[4]/tbody/tr/td[4]/a[2]");
		
		u.highLight(buttonGuardar);
		testCase.addScreenShotCurrentStep("Portal remesas");
		u.click(buttonGuardar);
	}
	
	public void SeleccionaElementoResumen() {
		By elemento_resumen = By.xpath("//*[@id=\"form1:tablaAtributos_data\"]/tr[1]/td[1]");
		
		u.highLight(elemento_resumen);
		testCase.addScreenShotCurrentStep("Portal remesas");
		u.click(elemento_resumen);
	}
	
	public void ModificarElementoResumen() {
		u.hardWait(5);
		By buttonModificarResumen = By.xpath("//*[@id=\"form1:j_idt147\"]");
		By valor_de_atributo = By.xpath("//*[@id=\"form1:valorAtr\"]");
		
		u.click(buttonModificarResumen);
		u.hardWait(1);
		u.highLight(buttonModificarResumen);
		u.highLight(valor_de_atributo);
		testCase.addScreenShotCurrentStep("Portal remesas");
	}
	
	public void ModificarDatos(String newValue) {
		By valorAtributo = By.xpath("//*[@id=\"form1:valorAtr\"]");
		
		u.clear(valorAtributo);
		u.sendKeys(valorAtributo, newValue);
		testCase.addScreenShotCurrentStep("Portal remesas");
	}
	
	public void PresionaActualizar() {
		By ButtonActualizar = By.xpath("//*[@id=\"form1:j_idt141\"]");
		
		u.highLight(ButtonActualizar);
		testCase.addScreenShotCurrentStep("Portal remesas");
		u.click(ButtonActualizar);
	}
	
	public void MuestraTablaResumen() {
		By tabla = By.xpath("//*[@id=\"form1:tablaAtributos\"]/div[1]");

		u.scrollToElement(tabla);
		u.highLight(tabla);
		testCase.addScreenShotCurrentStep("Portal remesas");
	}
	
	public void EnviosDinero_Reportes_AnalisisHoras_Submenu() {
		By submenu = By.xpath("//*[@id=\"ddtopmenubar\"]/ul/li[2]/a");
		By reportes = By.xpath("//*[@id=\"submenu1\"]/li/a");
		By horas = By.xpath("//*[@id=\"subsubmenu1\"]/li[4]/a");
		
		u.mouseOver(submenu);
		u.hardWait(1);
		u.mouseOver(reportes);
		u.hardWait(1);
		u.mouseOver(horas);
		
		
		u.highLight(horas);
		testCase.addScreenShotCurrentStep("Portal remesas");
		u.click(horas);
	}
	
	public void llenarReportePortalMontos(String fechaini,String fechafin/*,String zonaOxxo,String CrPlaza,String CrTienda,String Proveedor*/) {
		By fecha_ini = By.xpath("//*[@id=\"form1:fechaInicio_input\"]");
		By fecha_fin = By.xpath("//*[@id=\"form1:fechaFin_input\"]");
		By zona_Oxxo = By.xpath("//*[@id=\"form1:selectedZonas\"]/ul/li[1]");
		By cr_plaza = By.xpath("//*[@id=\"form1:selectedPlazas\"]/ul/li");
		By cr_tienda = By.xpath("//*[@id=\"form1:selectedTiendas\"]/ul/li");
		By proveedor = By.xpath("//*[@id=\"form1:selectedProveedor\"]/ul/li[1]");
		
		u.sendKeys(fecha_ini, fechaini);
		u.sendKeys(fecha_fin, fechafin);
		u.click(zona_Oxxo);
		u.hardWait(3);
		u.click(cr_plaza);
		u.hardWait(5);
		u.click(cr_tienda);
		u.click(proveedor);
		
		u.highLight(fecha_ini);
		u.highLight(fecha_fin);
		u.highLight(zona_Oxxo);
		u.hardWait(3);
		u.highLight(cr_plaza);
		u.highLight(cr_tienda);
		u.highLight(proveedor);
		testCase.addScreenShotCurrentStep("Portal");
	}
	
	public void generaReporte(String tipo) {
		By usa_excel = By.xpath("//*[@id=\"form1:salidaReporte\"]/tbody/tr/td[4]/label");
		By generar_reporte_excel = By.xpath("//*[@id=\"form1:btnExcel\"]");
		By generar_reporte_pant = By.xpath("//*[@id=\"form1:btnReporte\"]");
		By tabla_reporte_pantal = By.xpath("//*[contains(text(),'Resultados')]");
		//By tabla_reporte_pantal = By.xpath("//*[@id=\"form1:tablaMontos\"]");
		
		if(tipo.contains("excel")) 
			u.click(usa_excel);
		
		u.hardWait(1);
		if(tipo.contains("excel")) 
			u.click(generar_reporte_excel);
		else {
			u.click(generar_reporte_pant);
			u.hardWait(1);
			try {
				u.getDriver().switchTo().alert().accept();
			}catch(Exception ex) {
				
			}
			u.waitForLoadPage();
			u.hardWait(1);
			u.scrollToElement(tabla_reporte_pantal);
			u.highLight(tabla_reporte_pantal);
		}
		
		testCase.addScreenShotCurrentStep("reporte");
	}
	/**
	 * Constructor
	 ******************************************************************************************/

	public Portal_Remesas(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
		
	}

	/*
	 * METODOS - Acceso correcto a Inventario de espacios Publicitarios V2
	 ******************************************************************************************/

}