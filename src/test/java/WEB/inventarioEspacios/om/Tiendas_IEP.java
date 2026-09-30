package WEB.inventarioEspacios.om;

import java.time.Duration;
import org.openqa.selenium.By;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Tiendas_IEP {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;
	/*
	 * Localizadores Pantalla Detalle - Tiendas
	 ******************************************************************************************/
	By xmlUsuario = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/p");
    By xmlNegocio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[1]");
    By xmlPerfil = By.xpath("");  
    
    By xmlTiendas = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[6]/a/div");
//	By xmlAdministracion = By.xpath("//div[@class= \"texto-menu\"]/a[1]/div");
	By xmlAdministracion = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[2]/div/label");
	
	By xmlAdministracionM = By.xpath("//div[@class= \"texto-menu\"]/a[4]/div");
    By xmlSbTdasMarca = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[1]/a/div/label");
    
    By xmlHeader = By.xpath("//mat-sidenav-content[@id='content-nav']/app-header");
  
    By xmlPlaza = By.xpath("//*[@id=\"formFiltro\"]/div[1]/div[1]/div/mat-form-field/div/div[1]/div");
    By xmlModelo = By.xpath("//*[@id=\"formFiltro\"]/div[1]/div[2]/div/mat-form-field/div/div[1]/div");
    By xmlDescripcionTda = By.xpath("//*[@id=\"formFiltro\"]/div[2]/div/div/mat-form-field/div/div[1]/div");
    By xmlBtonNuevo = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[1]/button");
    By xmlBtonLimpiar = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[2]/button");
    By xmlBtonBuscaR = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[3]/button");
//    By xmlBtonBuscaR = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[4]/button");
    
    // Localizadores ingresar filtros PASO 8
    By xmlPlazaFlecha = By.xpath("//*[@id=\"mat-select-0\"]/div/div[2]");
    By xmlModeloFlecha = By.xpath("//*[@id=\"mat-select-2\"]/div/div[2]");
  
    String StrPlazaSelec = ("//div[@id=\"mat-select-0-panel\"]/mat-option/span[contains(text(),'%s')]");
    String StrModelo = ("//div[@id=\"mat-select-2-panel\"]/mat-option/span[contains(text(),' %s ')]");
  //div[@id="mat-select-2-panel"]/mat-option/span[contains(text(),' Actual ')]
    //By xmlPlazaSelec = By.xpath("//div[@id=\"mat-select-0-panel\"]/mat-option/span[contains(text(),\" Acapulco - 10APO \")]");
    By xmlModeloSelec = By.xpath("//div[@id=\"mat-select-2-panel\"]/mat-option/span[contains(text(),\" Actual \")]");
    By xmlDescripSelec = By.xpath("//*[@id=\"mat-input-0\"]");

    // Localizadores Resultados del Boton Buscar PASO 9 
    By xmlLblId = By.xpath("//*[@id=\"content-nav\"]/app-tiendas/div/div/div/table/thead/tr/th[2]");
    By xmlLblNombre = By.xpath("//*[@id=\"content-nav\"]/app-tiendas/div/div/div/table/thead/tr/th[3]");
    By xmlLblPlaza = By.xpath("//*[@id=\"content-nav\"]/app-tiendas/div/div/div/table/thead/tr/th[4]");
    By xmlLblSubPlaza = By.xpath("//*[@id=\"content-nav\"]/app-tiendas/div/div/div/table/thead/tr/th[5]");
    By xmlLblPlazaCcm = By.xpath("//*[@id=\"content-nav\"]/app-tiendas/div/div/div/table/thead/tr/th[6]");
    By xmlLblCr = By.xpath("//*[@id=\"content-nav\"]/app-tiendas/div/div/div/table/thead/tr/th[7]");
    By xmlLblAsesor = By.xpath("//*[@id=\"content-nav\"]/app-tiendas/div/div/div/table/thead/tr/th[8]");
    By xmlLblSegmento = By.xpath("//*[@id=\"content-nav\"]/app-tiendas/div/div/div/table/thead/tr/th[9]");
    By xmlLblSegmentoMtro = By.xpath("//*[@id=\"content-nav\"]/app-tiendas/div/div/div/table/thead/tr/th[10]");
    By xmlBtonEditar = By.xpath("//*[@id=\"content-nav\"]/app-tiendas/div/div/div/table/tbody/tr[1]/td[10]/mat-icon");
    								//*[@id="content-nav"]/app-tiendas/div/div/div/table/tbody/tr[1]/td[11]/mat-icon

    // Localizadores del Boton Limpiar PASO 10
    By xmlBotonLimpiar = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[3]/button/span[1]");
    
    // Localizadores  PASO 13 Muestra resultados diferentes
    By xmlTotalPag = By.xpath("//*[@id=\"content-nav\"]/app-tiendas/div/mat-paginator/div/div/div[2]/div");
    By xmlOcultaScroll = By.xpath("//*[@id=\"mat-expansion-panel-header-0\"]/span[2]");
  
  //div[@id="mat-select-2-panel"]/mat-option/span[contains(text(),' Actual ')]
    
	
	
	/**
	 * Constructor
	 ******************************************************************************************/
	public Tiendas_IEP(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);
    }
	
	/**
	 * Método para dar clic el Menu Tiendas
	 * 
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
	public void clickAdmorTiendas(String negocio) {
	
    	    u.highLight(xmlAdministracion);
        	testCase.addScreenShotCurrentStep(u, "clickAdmorTiendas");
        	u.click(xmlAdministracion); 
        	testCase.addScreenShotCurrentStep(u, "clickAdmorTiendas");
        	u.unhighLight(xmlAdministracion);
			
			u.highLight(xmlTiendas);
			u.click(xmlTiendas);
//		}
    testCase.addScreenShotCurrentStep(u, "admorTiendas");
    
	if(negocio.toUpperCase().equals("MARCA")) {
		u.unhighLight(xmlSbTdasMarca);
	} else  {
		u.unhighLight(xmlTiendas);
	}
    
    u.mouseOver(xmlHeader);
    
}

	/**
	 * Método: Para Mostrar los filtros PLaza,Modelo, y descripsion, asi como los botones de Nuevo,Limpiar y Buscar
	 * 
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
public void clickFiltrosTiendas() {
	m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
	u.highLight(xmlPlaza);
	u.highLight(xmlModelo);
	u.highLight(xmlDescripcionTda);
//	u.highLight(xmlBtonCajaZ);
	u.highLight(xmlBtonNuevo);
	u.highLight(xmlBtonLimpiar);
	u.highLight(xmlBtonBuscaR);
	testCase.addScreenShotCurrentStep(u, "FiltrosTiendas");
	
	u.unhighLight(xmlPlaza);
	u.unhighLight(xmlModelo);
	u.unhighLight(xmlDescripcionTda);
//	u.unhighLight(xmlBtonCajaZ);
	u.unhighLight(xmlBtonNuevo);
	u.unhighLight(xmlBtonLimpiar);
	u.unhighLight(xmlBtonBuscaR);
	
}

/**
 * Método: Para capturar los filtros PLaza,Modelo
 * 
 * @author: Manuel Cuen
 * @param:  nomPlaza,modelo, Son necesarios capturar desde su data provider para realizar prueba 
 * @return: Ninguno
 */
public void clickIngresaFiltrosTda(String nomPlaza, String modelo, String descripTienda) {
	
	u.highLight(xmlPlazaFlecha);
	u.click(xmlPlazaFlecha);
	
	System.out.println(StrPlazaSelec);
	System.out.println(nomPlaza);
	m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
	By LocPlaza = By.xpath(String.format(StrPlazaSelec, nomPlaza));
    u.click(LocPlaza);
	
    System.out.println(StrModelo);
	System.out.println(modelo);
	u.highLight(xmlModeloFlecha);
	u.click(xmlModeloFlecha);
	By LocModelo = By.xpath(String.format(StrModelo, modelo));
    u.click(LocModelo);

	u.highLight(xmlDescripSelec);
	u.sendKeys(xmlDescripSelec, descripTienda);
	
	testCase.addScreenShotCurrentStep(u, "clickIngresaFiltrosTda");
	
	u.unhighLight(xmlPlazaFlecha);
	u.unhighLight(xmlModeloFlecha);
	u.unhighLight(xmlDescripSelec);
		
}

/**
 * Método: Capturar la descripcion del filtro tienda
 * 
 * @author: Manuel Cuen
 * @param:  descripTienda, Es necesario capturar desde su Data Provider para su prueba existosa
 * @return: Ninguno
 */
public void clickBusquedaTda() {
	
	u.highLight(xmlBtonBuscaR);
	u.click(xmlBtonBuscaR);
	u.hardWait(2);
	
	testCase.addScreenShotCurrentStep(u, "clickDescripTda");
	u.hardWait(2);
	u.unhighLight(xmlBtonBuscaR);
	
}

/**
 * Método: Muestra la informacion de los filtros capturados
 * 
 * @author: Manuel Cuen
 * @param:  Niguno
 * @return: Ninguno
 */
public void clickResultadosBtonBuscar() {
	
	u.highLight(xmlLblId);
	u.highLight(xmlLblNombre);
	u.highLight(xmlLblPlaza);
	u.highLight(xmlLblSubPlaza);
	u.highLight(xmlLblPlazaCcm);
	u.highLight(xmlLblCr);
	u.highLight(xmlLblAsesor);
	u.highLight(xmlLblSegmento);
	u.highLight(xmlLblSegmentoMtro);
	u.highLight(xmlBtonEditar);
	testCase.addScreenShotCurrentStep(u, "ResultadosBtonBuscar");
	u.hardWait(2);
	
	u.unhighLight(xmlLblId);
	u.unhighLight(xmlLblNombre);
	u.unhighLight(xmlLblPlaza);
	u.unhighLight(xmlLblSubPlaza);
	u.unhighLight(xmlLblPlazaCcm);
	u.unhighLight(xmlLblCr);
	u.unhighLight(xmlLblAsesor);
	u.unhighLight(xmlLblSegmento);
	u.unhighLight(xmlLblSegmentoMtro);
	u.unhighLight(xmlBtonEditar);
}

/**
 * Método: Reinicia todos los elementos de la pantalla
 * 
 * @author: Manuel Cuen
 * @param:  Niguno
 * @return: Ninguno
 */
public void clickBotonLimpiar() {
	m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
	
	u.unhighLight(xmlLblId);
	u.unhighLight(xmlLblNombre);
	u.unhighLight(xmlLblPlaza);
	u.unhighLight(xmlLblSubPlaza);
	u.unhighLight(xmlLblPlazaCcm);
	u.unhighLight(xmlLblCr);
	u.unhighLight(xmlLblAsesor);
	u.unhighLight(xmlLblSegmento);
	u.unhighLight(xmlLblSegmentoMtro);
	u.unhighLight(xmlBtonEditar);
	
	u.highLight(xmlBtonLimpiar);
	u.click(xmlBtonLimpiar);
	testCase.addScreenShotCurrentStep(u, "clickBotonLimpiar");
	
	u.unhighLight(xmlBtonLimpiar);
	
}

/**
 * Método: Muestra todos los elementos de la pantalla, cuando se dio clic al boton Limpiar
 * 
 * @author: Manuel Cuen
 * @param:  Niguno
 * @return: Ninguno
 */
public void clickFltosTdasPasoNueve() {
	m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
	u.highLight(xmlPlaza);
	u.highLight(xmlModelo);
	u.highLight(xmlDescripcionTda);
	testCase.addScreenShotCurrentStep(u, "FiltrosTiendas");
	
	u.unhighLight(xmlPlaza);
	u.unhighLight(xmlModelo);
	u.unhighLight(xmlDescripcionTda);
	
}

/**
 * Método: Capturar los filtros PLaza,Modelo con datos diferentes a la primera vez, en la misma prueba
 * 
 * @author: Manuel Cuen
 * @param:  nomPlazaDos,modeloDos: Son necesarios capturar desde su data provider para realizar prueba
 * @return: Ninguno
 */
public void clickIngresaFiltrosTdaDos(String nomPlazaDos, String modeloDos) {
	m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
	
	u.highLight(xmlPlazaFlecha);
	u.click(xmlPlazaFlecha);

	m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
	By LocPlaza = By.xpath(String.format(StrPlazaSelec, nomPlazaDos));
    u.click(LocPlaza);
    
	u.highLight(xmlModeloFlecha);
	u.click(xmlModeloFlecha);
	By LocModelo = By.xpath(String.format(StrModelo, modeloDos));
    u.click(LocModelo);

	testCase.addScreenShotCurrentStep(u, "clickIngresaFiltrosTda");
	
	u.unhighLight(xmlPlazaFlecha);
	u.unhighLight(xmlModeloFlecha);
		
}

/**
 * Método: Capturar la descripcion del filtro tienda, para su segunda busqueda en la misma prueba
 * 
 * @author: Manuel Cuen
 * @param:  descripTiendaDos, Es necesario capturar desde su Data Provider para su prueba existosa
 * @return: Ninguno
 */
public void clickDescripTdaDos(String descripTiendaDos) {
	m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
	
	u.highLight(xmlDescripSelec);
	u.sendKeys(xmlDescripSelec, descripTiendaDos);
	testCase.addScreenShotCurrentStep(u, "clickDescripTdaDos");
	
	u.unhighLight(xmlDescripSelec);
	
	u.highLight(xmlBtonBuscaR);
	u.click(xmlBtonBuscaR);
	testCase.addScreenShotCurrentStep(u, "clickDescripTda");
	
	u.unhighLight(xmlBtonBuscaR);
	
}

/**
 * Método: Muestra y limpia los datos que se capturar para su segundo filtro de busqueda
 * 
 * @author: Manuel Cuen
 * @param:  Ninguno
 * @return: Ninguno
 */
public void clickResultadosDiferentes() {
	m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
	
	clickResultadosBtonBuscar();
	testCase.addScreenShotCurrentStep(u, "ResultadosDiferentes");
	clickBotonLimpiar();
	
}

/**
 * Método: Da clic en el boton editar
 * 
 * @author: Manuel Cuen
 * @param:  Ninguno
 * @return: Ninguno
 */
public void clickBotonEditar() {
	
	m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
	
	u.unhighLight(xmlLblId);
	u.unhighLight(xmlLblNombre);
	u.unhighLight(xmlLblPlaza);
	u.unhighLight(xmlLblSubPlaza);
	u.unhighLight(xmlLblPlazaCcm);
	u.unhighLight(xmlLblCr);
	u.unhighLight(xmlLblAsesor);
	u.unhighLight(xmlLblSegmento);
	u.unhighLight(xmlLblSegmentoMtro);
	u.unhighLight(xmlBtonEditar);
	testCase.addScreenShotCurrentStep(u, "ResultadosDiferentes");
	u.click(xmlBtonEditar);
	testCase.addScreenShotCurrentStep(u, "ResultadosDiferentes");
	u.hardWait(2);
}

}
