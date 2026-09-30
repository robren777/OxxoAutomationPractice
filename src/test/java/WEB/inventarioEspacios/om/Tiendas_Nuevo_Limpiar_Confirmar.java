package WEB.inventarioEspacios.om;

import java.time.Duration;
import org.openqa.selenium.By;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Tiendas_Nuevo_Limpiar_Confirmar {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;
	/*
	 * Localizadores Pantalla Detalle - Tiendas_Nuevo_Limpiar_Confirmar
	 ******************************************************************************************/
	By xmlUsuario = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/p");
    By xmlNegocio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[1]");
    By xmlPerfil = By.xpath("");  
    
//									PASO 6
    // Localizador del Menu Administracion
    By xmlMenusAdmon = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[1]/div/label");
    By xmlAdministracion = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[2]/div/label");
    // Localizador SubMenu Tiendas
    By xmlSbTiendas = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[6]/a/div/label");
    By xmlSbTdasMarca = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[6]/a/div/label");
    
    // Localizador de encabezado
    By xmlHeader = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/mat-icon");

//								LOCALIZADORES DEL PASO 7  
    // Plaza
    By xmlPlaza= By.xpath("//*[@id=\"formFiltro\"]/div[1]/div[1]/div/mat-form-field/div/div[1]/div");
    // Modelo
    By xmlModelo = By.xpath("//*[@id=\"formFiltro\"]/div[1]/div[2]/div/mat-form-field/div/div[1]/div");
    // Descripcion Tienda
    By xmlDescripTienda = By.xpath("//*[@id=\"formFiltro\"]/div[2]/div/div/mat-form-field/div/div[1]/div");
    // Boton Nuevo
    //By xmlBtnNuevo = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[2]/button"); este es para admor oxxo
    By xmlBtnNuevo = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[1]/button/span[1]"); // es para admor marca
    // Boton Limpiar
    By xmlBtnLimpiar = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[2]/button/span[1]");
    							//*[@id=\"formFiltro\"]/div[3]/div/div[3]/button
    // Boton Buscar
    By xmlBtnBuscar = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[3]/button/span[1]");
 //								LOCALIZADORES DEL PASO 8
    By xmlNombreTienda = By.xpath("//*[@id=\"mat-input-12\"]");
    
    By xmlPlazaNvo = By.xpath("//*[@id=\"mat-select-value-7\"]/span");
    By xmlRetekId = By.xpath("//*[@id=\"mat-input-13\"]");
    By xmlSbPlaza = By.xpath("//*[@id=\"mat-select-value-9\"]/span");
    By xmlAsesor = By.xpath("//*[@id=\"mat-input-14\"]");
    By xmlPlazaCCM = By.xpath("//*[@id=\"mat-select-value-11\"]/span");
    By xmlMtosCuadra = By.xpath("//*[@id=\"mat-input-15\"]");
    By xmlDistritos = By.xpath("//*[@id=\"mat-select-value-13\"]/span");
    By xmlSegmentacion = By.xpath("//*[@id=\"mat-select-value-15\"]/span");
    By xmlTiendaMode = By.xpath("//*[@id=\"mat-input-16\"]");
//    By xmlSegmentacionMtro = By.xpath("//*[@id=\"mat-select-value-17\"]/span");
    By xmlOpcionesTienda = By.xpath("//*[@id=\"mat-checkbox-12\"]/label/span[2]");
    By xmlTdaMixtaExc = By.xpath("//*[@id=\"mat-select-value-17\"]/span");
    By xmlTpoEmbot = By.xpath("//*[@id=\"mat-select-value-19\"]/span");
    By xmlRestriPopGas = By.xpath("//*[@id=\"mat-select-value-21\"]/span");
    By xmlCampoNvaSeg = By.xpath("//*[@id=\"mat-select-value-23\"]/span");
    By xmlTipoCinco = By.xpath("//*[@id=\"mat-select-value-25\"]/span");
    By xmlBtnCancelar = By.xpath("//*[@id=\"content-nav\"]/app-tienda-captura-edicion/div/div/div[2]/div/div[1]/button/span[1]");
    By xmlBtnLimpiarNvo = By.xpath("//*[@id=\"content-nav\"]/app-tienda-captura-edicion/div/div/div[2]/div/div[2]/button/span[1]");
    By xmlBtnGuardar = By.xpath("//*[@id=\"content-nav\"]/app-tienda-captura-edicion/div/div/div[2]/div/div[2]/button/span[1]");
    							
    By xmlConfirGuardar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");
    By xmlBotonCancelar = By.xpath("//*[@id=\"content-nav\"]/app-tienda-captura-edicion/div/div/div[2]/div/div[1]/button/span[1]");
    
      //table/tbody/tr/td[2]/a/span[contains(text(),'3501201')]----como tomar el puro numero?
    
    // Paso 6
 
 // Localizadores botones Eliminar y confirmar eliminacion
    By xmlBtonEliminar = By.xpath("//*[@id=\"formFiltroPop\"]/div[2]/div/div[2]/button/span[1]");
    By xmlConfEliminar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");
    By xmlTiendaModeM = By.xpath("//mat-sidenav-content[@id=\"content-nav\"]/app-tienda-captura-edicion/div/div/div/div[3]/div[5]/mat-form-field/div/div/div/input");
    /*
     * Variables que reciben un parametro
     * *******************************************************************************************/
 // Nombre Tienda
    String StrPlaza = ("//div[@id=\"mat-select-6-panel\"]/mat-option/span[contains(text(),' %s ')]");
    String StrSbPlaza = ("//div[@id=\"mat-select-8-panel\"]/mat-option/span[contains(text(),' %s ')]");  
    String StrPlazaCCM = ("//div[@id=\"mat-select-10-panel\"]/mat-option/span[contains(text(),' %s ')]");
    String StrDistritos = ("//div[@id=\"mat-select-12-panel\"]/mat-option/span[contains(text(),' %s ')]");
    String StrSegmentacion = ("//div[@id=\"mat-select-14-panel\"]/mat-option/span[contains(text(),' %s ')]");
    String StrTiendaMode = ("//div[@id=\"mat-autocomplete-10\"]/mat-option/span[contains(text(),' %s ')]");
    String StrSegmentacionMtro = ("//div[@id=\"mat-select-16-panel\"]/mat-option/span[contains(text(),' Clasico ')]");
    String StrOpcionesTienda = ("//mat-checkbox[@id=\"mat-checkbox-11\"]/label/span[2][contains(text(),'%s')]/preceding-sibling::span");
  //form[@id="opcionesTTienda"]/div/div/app-opciones-tienda/div/div/div/mat-checkbox/label/span[2][contains(text(),' Baja')]
    /**
	 * Constructor
	 ******************************************************************************************/
	public Tiendas_Nuevo_Limpiar_Confirmar(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);
    }
	
	/**
	 * Método: Da Clic al Menu Administracion, SubMenu Tiendas.
	 * 
	 * @author: Manuel Cuen
	 * @param: Ninguno 
	 * @return:Ninguno
	 */

	public void clicMenuAdmonTdas(String negocio) {

		if(negocio.toUpperCase().equals("MARCA")) {
			u.highLight(xmlAdministracion);
			u.click(xmlAdministracion);
			u.highLight(xmlSbTdasMarca);
			u.click(xmlSbTdasMarca);
		} else if(negocio.toUpperCase().equals("OXXO"))  {
			u.highLight(xmlMenusAdmon);
			u.click(xmlMenusAdmon);
			u.highLight(xmlSbTiendas);
			u.click(xmlSbTiendas);
		}
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.mouseOver(xmlHeader);
	    
	    testCase.addScreenShotCurrentStep(u, "clicMenuAdmonTdas");
		u.hardWait(2);
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.unhighLight(xmlMenusAdmon);
//		u.unhighLight(xmlSbTiendas);
	    
	}
	
	/**
	 * Método: Muestra los Filtros (Plaza, Modelo,Descripcion de Tienda, asi como Botones Nuevo,Limpiar y Buscar)
	 * 
	 * @author: Manuel Cuen
	 * @param: Ninguno 
	 * @return:Ninguno
	 */
	public void clickMuestraFiltros() {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		
		u.highLight(xmlPlaza);
		u.highLight(xmlModelo);
		u.highLight(xmlDescripTienda);
		u.highLight(xmlBtnNuevo);
		u.highLight(xmlBtnLimpiar);
		u.highLight(xmlBtnBuscar);
		
	    testCase.addScreenShotCurrentStep(u, "clickMuestraFiltros");
		u.hardWait(2);
		
        u.unhighLight(xmlPlaza);
        u.unhighLight(xmlModelo);
        u.unhighLight(xmlDescripTienda);
        u.unhighLight(xmlBtnNuevo);
        u.unhighLight(xmlBtnLimpiar);
        u.unhighLight(xmlBtnBuscar);
	}

	//* Da Clic al boton Nuevo
	//**********************************************
	
	public void clickBotonNuevo() {
		
		u.highLight(xmlBtnNuevo);
		u.click(xmlBtnNuevo);
		
		testCase.addScreenShotCurrentStep(u, "clickBotonNuevo");
		u.hardWait(2);
	}
	
	//* Da Muestra Campos al dar Clic en el Boton Nuevo
	//**********************************************
	public void clickMuestraCamposPNueve(Boolean band) {
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		
		u.highLight(xmlNombreTienda);
		u.highLight(xmlPlazaNvo);
		u.highLight(xmlRetekId);
		u.highLight(xmlSbPlaza);
		u.highLight(xmlAsesor);
		u.highLight(xmlPlazaCCM);
		u.highLight(xmlMtosCuadra);
		u.highLight(xmlDistritos);
		u.highLight(xmlSegmentacion);
		u.highLight(xmlTiendaMode);
//		u.highLight(xmlSegmentacionMtro);
		u.highLight(xmlOpcionesTienda);
		u.highLight(xmlTdaMixtaExc);
		u.highLight(xmlTpoEmbot);
		u.highLight(xmlRestriPopGas);
		u.highLight(xmlCampoNvaSeg);
		u.highLight(xmlTipoCinco);
		u.highLight(xmlBtnCancelar);
		if(band == true) {
			u.highLight(xmlBtnLimpiarNvo);
		}
		u.highLight(xmlBtnGuardar);
		
		testCase.addScreenShotCurrentStep(u, "clickMuestraCamposPNueve");
		u.hardWait(2);
		u.unhighLight(xmlNombreTienda);
		u.unhighLight(xmlPlazaNvo);
		u.unhighLight(xmlRetekId);
		u.unhighLight(xmlSbPlaza);
		u.unhighLight(xmlAsesor);
		u.unhighLight(xmlPlazaCCM);
		u.unhighLight(xmlMtosCuadra);
		u.unhighLight(xmlDistritos);
		u.unhighLight(xmlSegmentacion);
		u.unhighLight(xmlTiendaMode);
//		u.unhighLight(xmlSegmentacionMtro);
		u.unhighLight(xmlOpcionesTienda);
		u.unhighLight(xmlTdaMixtaExc);
		u.unhighLight(xmlTpoEmbot);
		u.unhighLight(xmlRestriPopGas);
		u.unhighLight(xmlCampoNvaSeg);
		u.unhighLight(xmlTipoCinco);
		u.unhighLight(xmlBtnCancelar);
		if(band == true) {
			u.unhighLight(xmlBtnLimpiarNvo);
		}
		u.unhighLight(xmlBtnGuardar);
	    
	}
	/**
	 * Método: Captura Datos y guarda nueva tienda
	 * 
	 * @author: Manuel Cuen
	 * @param: nombreTienda,plaza,RetekId,SubPlaza,Asesor,PlazaCCM,MetrosCuadrados,
	 * Distritos,Segmentacion,TiendaModelo,SegmentoMtro,Opcionestienda:   Es necesario llenarlos en Data Provider
	 * @return:Ninguno
	 */
	public void ClickLlenaCampos(String nombreTienda, String plaza, String RetekId, String SubPlaza, String Asesor, String PlazaCCM, String MetrosCuadrados, String Distritos, String Segmentacion, String TiendaModelo, String SegmentoMtro, String Opcionestienda) {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		
		u.highLight(xmlNombreTienda);
		u.sendKeys(xmlNombreTienda, nombreTienda);
		
		u.highLight(xmlPlazaNvo);
		u.click(xmlPlazaNvo);
		
		System.out.println(StrPlaza);
		System.out.println(nombreTienda);
		By LocPlaza = By.xpath(String.format(StrPlaza, plaza));
	    u.click(LocPlaza);
	    u.hardWait(2);
			
	    u.highLight(xmlRetekId);
		u.sendKeys(xmlRetekId, RetekId);
	
		u.highLight(xmlSbPlaza);
		u.click(xmlSbPlaza);
		
		System.out.println(StrSbPlaza);
		System.out.println(SubPlaza);
		By LocSbPlaza = By.xpath(String.format(StrSbPlaza, SubPlaza));
	    u.click(LocSbPlaza);
	    u.hardWait(2);
	    
	    u.highLight(xmlAsesor);
		u.sendKeys(xmlAsesor, Asesor);
		
		u.highLight(xmlPlazaCCM);
		u.click(xmlPlazaCCM);
		
		System.out.println(StrPlazaCCM);
		System.out.println(PlazaCCM);
		By LocPlazaCCM = By.xpath(String.format(StrPlazaCCM, PlazaCCM));
	    u.click(LocPlazaCCM);
	    u.hardWait(2);
		
	    u.highLight(xmlMtosCuadra);
		u.sendKeys(xmlMtosCuadra, MetrosCuadrados);
		
		u.highLight(xmlDistritos);
		u.click(xmlDistritos);
		
		System.out.println(StrDistritos);
		System.out.println(Distritos);
		By LocDistritos = By.xpath(String.format(StrDistritos, Distritos));
	    u.click(LocDistritos);
	    u.hardWait(2);
	    
	    u.highLight(xmlSegmentacion);
		u.click(xmlSegmentacion);
		
		System.out.println(StrSegmentacion);
		System.out.println(Segmentacion);
		By LocSegmentacion = By.xpath(String.format(StrSegmentacion, Segmentacion));
	    u.click(LocSegmentacion);
	    u.hardWait(2);

	    u.highLight(xmlTiendaMode);
		u.click(xmlTiendaMode);
		
		System.out.println(StrTiendaMode);
		System.out.println(TiendaModelo);
		By LocTiendaMode = By.xpath(String.format(StrTiendaMode, TiendaModelo));
	    u.click(LocTiendaMode);
	    u.hardWait(2);
	    
//	    u.highLight(xmlSegmentacionMtro);
//		u.click(xmlSegmentacionMtro);
//		
//		System.out.println(StrSegmentacionMtro);
//		System.out.println(SegmentoMtro);
//		By LocSegmentacionMtro = By.xpath(String.format(StrSegmentacionMtro, SegmentoMtro));
//	    u.click(LocSegmentacionMtro);
//	    u.hardWait(2);
	    
		System.out.println(StrOpcionesTienda);
		System.out.println(Opcionestienda);
		By LocOpcionesTienda = By.xpath(String.format(StrOpcionesTienda, Opcionestienda));
	    u.click(LocOpcionesTienda);
	    u.hardWait(2);
	    
	    testCase.addScreenShotCurrentStep(u, "ClickLlenaCampos");
		u.hardWait(2);
		u.unhighLight(xmlNombreTienda);
		u.unhighLight(xmlRetekId);
		u.unhighLight(xmlAsesor);
		u.unhighLight(xmlMtosCuadra);
		u.unhighLight(xmlPlazaCCM);
		u.unhighLight(xmlTiendaMode);
	}
	
	//* Da Clic al boton Limpiar
	//**********************************************
	public void Limpia() {
		
		u.highLight(xmlBtnLimpiarNvo);
		u.click(xmlBtnLimpiarNvo);
		
	    testCase.addScreenShotCurrentStep(u, "Limpia");
		u.hardWait(2);
		u.unhighLight(xmlBtnLimpiarNvo);
	}
	
	//* Da Clic al boton Guardar y Confirmar Guardar
	//**********************************************
	public void clickBotonGuardar() {
		
	    u.highLight(xmlBtnGuardar);
	    u.click(xmlBtnGuardar);
	    
	    u.highLight(xmlConfirGuardar);
	    u.click(xmlConfirGuardar);
	    u.hardWait(2);
	    
	    testCase.addScreenShotCurrentStep(u, "clickBotonGuardar");
		u.hardWait(2);


	}
	
	//* Da Clic al boton Cancelar
		//**********************************************
	public void clickBtnCancelar() {
		
		u.highLight(xmlBotonCancelar);
		u.click(xmlBotonCancelar);
		
	    testCase.addScreenShotCurrentStep(u, "clickBtnCancelar");
		u.hardWait(2);
	}
	
	/**
	 * Método: Modifica datos para actualizarlos en la base de datos
	 * 
	 * @author: Manuel Cuen
	 * @param: NombreTdaMod,AsesorMod: Es necesario llenarlos en su  Data Provider
	 * @return:Ninguno
	 */
	public void clickModificarDatos(String NombreTdaMod, String plaza, String RetekId, String SubPlaza, String Asesor, String PlazaCCM, String MetrosCuadrados, String Distritos, String Segmentacion,String TiendaModelo) {
		
		u.getDriver().findElement(xmlNombreTienda).clear();
		u.highLight(xmlNombreTienda);
		u.sendKeys(xmlNombreTienda, NombreTdaMod);
		
		u.highLight(xmlPlazaNvo);
		u.click(xmlPlazaNvo);
		
		System.out.println(StrPlaza);
		
		By LocPlaza = By.xpath(String.format(StrPlaza, plaza));
	    u.click(LocPlaza);
	    u.hardWait(2);
	    
	    u.getDriver().findElement(xmlRetekId).clear();
	    u.highLight(xmlRetekId);
		u.sendKeys(xmlRetekId, RetekId);
		
		u.highLight(xmlSbPlaza);
		u.click(xmlSbPlaza);
		
		System.out.println(StrSbPlaza);
		System.out.println(SubPlaza);
		By LocSbPlaza = By.xpath(String.format(StrSbPlaza, SubPlaza));
	    u.click(LocSbPlaza);
	    u.hardWait(2);
		
		System.out.println(xmlAsesor);
		System.out.println(Asesor);
		
		u.getDriver().findElement(xmlAsesor).clear();
		u.highLight(xmlAsesor);
		u.sendKeys(xmlAsesor, Asesor);
		
		u.highLight(xmlPlazaCCM);
		u.click(xmlPlazaCCM);
		
		System.out.println(StrPlazaCCM);
		System.out.println(PlazaCCM);
		By LocPlazaCCM = By.xpath(String.format(StrPlazaCCM, PlazaCCM));
	    u.click(LocPlazaCCM);
	    u.hardWait(2);
	    
	    u.getDriver().findElement(xmlMtosCuadra).clear();
	    u.highLight(xmlMtosCuadra);
		u.sendKeys(xmlMtosCuadra, MetrosCuadrados);
		
		u.highLight(xmlDistritos);
		u.click(xmlDistritos);
		
		System.out.println(StrDistritos);
		System.out.println(Distritos);
		By LocDistritos = By.xpath(String.format(StrDistritos, Distritos));
	    u.click(LocDistritos);
	    u.hardWait(2);
	    
//	    u.getDriver().findElement(xmlSegmentacion).clear();
	    u.highLight(xmlSegmentacion);
		u.click(xmlSegmentacion);

		System.out.println(StrSegmentacion);
		System.out.println(Segmentacion);
		By LocSegmentacion = By.xpath(String.format(StrSegmentacion, Segmentacion));
	    u.click(LocSegmentacion);
	    u.hardWait(2);
	    
	    u.getDriver().findElement(xmlTiendaModeM).clear();
	    u.highLight(xmlTiendaModeM);
		u.sendKeys(xmlTiendaModeM,TiendaModelo);
		

		testCase.addScreenShotCurrentStep(u, "clickModificarDatos");
		u.hardWait(2);
	}
}
