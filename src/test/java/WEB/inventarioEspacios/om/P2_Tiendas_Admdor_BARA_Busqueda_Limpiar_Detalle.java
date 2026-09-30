package WEB.inventarioEspacios.om;

import org.openqa.selenium.By;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class P2_Tiendas_Admdor_BARA_Busqueda_Limpiar_Detalle {

	SeleniumUtil u;
	TestCase testCase;
	/*
	 * Localizadores Pantalla Detalle - Tiendas
	 ******************************************************************************************/
	By xmlUsuario = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/p");
    By xmlNegocio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[1]");
    By xmlPerfil = By.xpath("");  
    
    By xmlTiendas = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[6]/a/div");
    
    By xmlHeader = By.xpath("//mat-sidenav-content[@id='content-nav']/app-header");
  
    By xmlPlaza = By.xpath("//*[@id=\"formFiltro\"]/div[1]/div[1]/div/mat-form-field/div/div[1]/div");
    By xmlModelo = By.xpath("//*[@id=\"formFiltro\"]/div[1]/div[2]/div/mat-form-field/div/div[1]/div");
    By xmlDescripcionTda = By.xpath("//*[@id=\"formFiltro\"]/div[2]/div/div/mat-form-field/div/div[1]/div");
    By xmlBtonCajaZ = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[1]/button");
    By xmlBtonNuevo = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[2]/button");
    By xmlBtonLimpiar = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[3]/button");
    By xmlBtonBuscaR = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[4]/button");
    
    // Localizadores ingresar filtros PASO 8
    By xmlPlazaFlecha = By.xpath("//*[@id=\"mat-select-0\"]/div/div[2]");
    By xmlModeloFlecha = By.xpath("//*[@id=\"mat-select-2\"]/div/div[2]");
  
    By xmlPlazaSelec = By.xpath("//div[@id=\"mat-select-0-panel\"]/mat-option/span[contains(text() =\'Adapter.Connections:%s\')]");

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
    By xmlBtonEditar = By.xpath("//*[@id=\"content-nav\"]/app-tiendas/div/div/div/table/tbody/tr[1]/td[11]/mat-icon");

    // Localizadores del Boton Limpiar PASO 10
    By xmlBotonLimpiar = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[3]/button/span[1]");
    
    // Localizadores  PASO 13 Muestra resultados diferentes
    By xmlTotalPag = By.xpath("//*[@id=\"content-nav\"]/app-tiendas/div/mat-paginator/div/div/div[2]/div");
    By xmlOcultaScroll = By.xpath("//*[@id=\"mat-expansion-panel-header-0\"]/span[2]");
  
  

	
	
	/**
	 * Constructor
	 ******************************************************************************************/
	public P2_Tiendas_Admdor_BARA_Busqueda_Limpiar_Detalle(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;

    }
	
public void clickAdmorTiendas() {
	
    u.highLight(xmlTiendas);
    u.click(xmlTiendas);
    testCase.addScreenShotCurrentStep(u, "admorTiendas");
    
    u.unhighLight(xmlTiendas);
    u.mouseOver(xmlHeader);
    
}

public void clickFiltrosTiendas() {
	u.hardWait(3);
	u.highLight(xmlPlaza);
	u.highLight(xmlModelo);
	u.highLight(xmlDescripcionTda);
	u.highLight(xmlBtonCajaZ);
	u.highLight(xmlBtonNuevo);
	u.highLight(xmlBtonLimpiar);
	u.highLight(xmlBtonBuscaR);
	testCase.addScreenShotCurrentStep(u, "FiltrosTiendas");
	
	u.unhighLight(xmlPlaza);
	u.unhighLight(xmlModelo);
	u.unhighLight(xmlDescripcionTda);
	u.unhighLight(xmlBtonCajaZ);
	u.unhighLight(xmlBtonNuevo);
	u.unhighLight(xmlBtonLimpiar);
	u.unhighLight(xmlBtonBuscaR);
	
}

public void clickIngresaFiltrosTda() {
	
	u.highLight(xmlPlazaFlecha);
	u.click(xmlPlazaFlecha);
	u.highLight(xmlPlazaSelec);
	u.click(xmlPlazaSelec);
	u.highLight(xmlModeloFlecha);
	u.click(xmlModeloFlecha);
	u.click(xmlModeloSelec);
	testCase.addScreenShotCurrentStep(u, "clickIngresaFiltrosTda");
	
	u.unhighLight(xmlPlazaFlecha);
	u.unhighLight(xmlModeloFlecha);
		
}

public void clickDescripTda(String descripcion) {
	
	u.highLight(xmlDescripSelec);
	u.sendKeys(xmlDescripSelec, descripcion);
	testCase.addScreenShotCurrentStep(u, "clickDescripTda");
	
	u.unhighLight(xmlDescripSelec);
	
	u.highLight(xmlBtonBuscaR);
	u.click(xmlBtonBuscaR);
	testCase.addScreenShotCurrentStep(u, "clickDescripTda");
	
	u.unhighLight(xmlBtonBuscaR);
	
}

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

public void clickBotonLimpiar() {
	
	u.highLight(xmlBotonLimpiar);
	u.click(xmlBotonLimpiar);
	testCase.addScreenShotCurrentStep(u, "clickBotonLimpiar");
	
	u.unhighLight(xmlBotonLimpiar);
	
}

public void clickFltosTdasPasoNueve() {
	u.highLight(xmlPlaza);
	u.highLight(xmlModelo);
	u.highLight(xmlDescripcionTda);
	testCase.addScreenShotCurrentStep(u, "FiltrosTiendas");
	
	u.unhighLight(xmlPlaza);
	u.unhighLight(xmlModelo);
	u.unhighLight(xmlDescripcionTda);
	
}

public void clickIngresaFiltrosTdaDos() {
	
	u.highLight(xmlPlazaFlecha);
	u.click(xmlPlazaFlecha);
	u.highLight(xmlPlazaSelec);
	u.click(xmlPlazaSelec);
	u.highLight(xmlModeloFlecha);
	u.click(xmlModeloFlecha);
	u.click(xmlModeloSelec);
	testCase.addScreenShotCurrentStep(u, "clickIngresaFiltrosTda");
	
	u.unhighLight(xmlPlazaFlecha);
	u.unhighLight(xmlModeloFlecha);
		
}

public void clickDescripTdaDos(String descripcionDos) {
	
	u.highLight(xmlDescripSelec);
	u.sendKeys(xmlDescripSelec, descripcionDos);
	testCase.addScreenShotCurrentStep(u, "clickDescripTda");
	
	u.unhighLight(xmlDescripSelec);
	
	u.highLight(xmlBtonBuscaR);
	u.click(xmlBtonBuscaR);
	testCase.addScreenShotCurrentStep(u, "clickDescripTda");
	
	u.unhighLight(xmlBtonBuscaR);
	
}

public void clickResultadosDiferentes() {
	u.highLight(xmlOcultaScroll);
	u.click(xmlOcultaScroll);
	u.hardWait(3);
	
	u.highLight(xmlTotalPag);
	testCase.addScreenShotCurrentStep(u, "ResultadosDiferentes");
	
	u.unhighLight(xmlTotalPag);
	u.unhighLight(xmlOcultaScroll);
	
}

}
