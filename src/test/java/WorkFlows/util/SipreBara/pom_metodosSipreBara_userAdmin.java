package WorkFlows.util.SipreBara;

import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;



public class pom_metodosSipreBara_userAdmin {

	SeleniumUtil u;
	TestCase testCase;
	private WebDriverWait wait;
	Random random = new Random();
	
	private SikuliUtil sikuli = new SikuliUtil();
	private Screen screen = new Screen();	

	private Pattern p1;
	private String s1;
	private String folder = "sipreBara";
	private String mainTab = "";
	private String newTab = "";



	
	public pom_metodosSipreBara_userAdmin (SeleniumUtil u, TestCase testCase ) {
		this.u = u;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		this.testCase = testCase;


	}
	
	
	By inicio = By.id("home");
	By config = By.xpath("//*[@id=\"products\"]");
	By reportes = By.id("reportes");
	By btnArtSinRegla = By.id("mod26_RepAdArtSinRegla");
	
	//REPORTES
	By reglasPrecio = By.cssSelector("#mod7_ARP_Comp");
	By cambiosMasivos = By.id("mod41_RepAdCambiosMasivos");
	By costos = By.id("mod21_RepAdCostos");
	By listadoCategoria = By.id("mod23_RepAdListCat");
	By puntosEjecucion = By.id("mod24_RepAdPtoEj");
	By zonas = By.id("mod22_RepAdZonas");
	By excepciones = By.id("mod21_RepAdExcep");
	By folioRetek = By.id("mod37_RepAdFolioRetek");
	By mantenimientoPrecios = By.id("mod18_RepAdMantPrecios");
	By monitoreoPrecios = By.id("mod40_RepAdMonitoreo");
	By reglaPrecioReport = By.id("mod25_RepAdReglasPrecio");
	By cumplimiento = By.id("mod19_RepAdCumplimiento");
	By asignaReglas = By.cssSelector("#mod27_RepAdAsigRegla");
	By reglaNegocio = By.cssSelector("#mod20_RepAdReglasNeg");
	
	By btnGeneraReport = By.cssSelector("#reportesGral > a:nth-child(3)");
	By btGeneraReportCumplimientio =  By.cssSelector("#repCumplimiento > a:nth-child(3)");
	By btnReportMonitoreo = By.cssSelector("#repMonitoreo > a:nth-child(3)");
	By btnFechaInicio = By.id("idFechaIni");
	By selectPlaza = By.id("divPlazaId_cmb");
	By selectZona = By.id("divZona_cmb");
	By selectDiv = By.id("divisiones_cmb"); //SuperGrupo
	By selectCategoria = By.id("categorias_cmb");
	By selectStatus = By.id("divStatusPE");
	By selectTipoMonitoreo = By.id("tipoMonitoreo_cmb");
	By btnLimpiar = By.cssSelector("#repCumplimiento > a:nth-child(2)");
	By btnLimpiarMonitoreo = By.cssSelector("#repMonitoreo > a:nth-child(2)");
	By btnLimpiarRegPrecio = By.cssSelector("#repReglaPrecio > a:nth-child(2)");
	By btnGeneraProm = By.cssSelector("#repCumplimiento > a:nth-child(4)");
	By btnGeneraMonitoreo = By.cssSelector("#repMonitoreo > a:nth-child(4)");
	By btnGenRepEstructuraRegPre = By.cssSelector("#repReglaPrecio > a:nth-child(3)");
	By btnSKUregPrecio = By.cssSelector("#repReglaPrecio > a:nth-child(4)");
	
	By btnBuscar = By.cssSelector("a.boton:nth-child(1)");
	By tablaData = By.xpath("//table[contains(@id,'gridArtsSinRegla')]//tr//td[2]");
	By btnDescartar = By.cssSelector("a.boton:nth-child(3)");
	By btnAprobar = By.cssSelector("a.boton:nth-child(2)");
	
	
	
	/**
	 * Selecciona reglas de precio <Competencia>, en
	 * el apartado de configuraciones y da click en
	 * el botï¿½n buscar para llenar la tabla "Selecciï¿½n"
	 * 
	 * @author alicia.ceja
	 */
	public void selectReglasPrecio() {
		
	try {
		
		u.waitWebElement(config, 120, 2);	
		u.highLight(config);
		u.mouseOver(config);
		testCase.addScreenShotCurrentStep(u, "");
		u.unhighLight(config);

		u.hardWait(2);
		
		u.highLight(reglasPrecio);
		testCase.addScreenShotCurrentStep(u, "");
		u.mouseOverAndClick(reglasPrecio);
		u.unhighLight(reglasPrecio);

		u.waitWebElement(btnBuscar, 120, 2);	
		
		
		testCase.addTextEvidenceCurrentStep("Click en botón Buscar");
		u.highLight(btnBuscar);
		u.mouseOverAndClick(btnBuscar);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "");
		u.unhighLight(btnBuscar);

		
	}catch(Exception e) {
		
		System.out.println(" -> " + e);
		throw new AssertionError("Error en el proceso de ingresar a Reglas de precio y generar tabla");
		
	}
	

	
	}
	

	/**
	 * Selecciona de manera aleatora un elemento de la tabla generada
	 * y retorna el nombre del elemento
	 * @author alicia.ceja
	 * 
	 */
	public String selectArticulo() {
		u.hardWait(3);
		String articulo;
		try {
			
			List<WebElement> elementsTable = u.getDriver().findElements(tablaData);
			int registros = elementsTable.size()-1;
			System.out.println("Elementos de la tabla interfaz --->"+registros);
			
			int elemento = random.nextInt(elementsTable.size()-1);
			 articulo = elementsTable.get(4).getText();
			
			System.out.println("elemento --->"+elemento);
			System.out.println("arti --->"+articulo);
			
			for(WebElement ele: elementsTable) {
				
				String value = ele.getText();			
				if (value.contains(articulo)) {

				String arti= "//td[text( )=\""+articulo+"\"]";
				System.out.println("Path arti --->"+articulo);


				By selectArticulo = By.xpath("//td[text()='"+articulo+"']//preceding-sibling::td//input");
				System.out.println("Css arti --->"+selectArticulo);

				WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(arti)));
				scrollToElement(u.getDriver(),button);
				u.highLight(selectArticulo);
				
				u.click(selectArticulo);
				break;
					
				}
				

			}		

			
		}catch(Exception e) {
			
		System.out.println(" -> " + e);
		throw new AssertionError("Falló al seleccionar artículo");
	}
		
		return  articulo;
	}
	
	/**
	 * Regresa la cantidad de elementos que tiene la tabla 
	 * para comparar en la db
	 * @return
	 * @author alicia.ceja
	 */
	
	public int numeroElementosTabla() {
		By elementoTabla = By.id("jqg_gridArtsSinRegla_1");

		u.waitWebElement(elementoTabla, 120, 2);
		List<WebElement> elementsTable = u.getDriver().findElements(tablaData);
		int registros = elementsTable.size()-1;
	
		return registros;
	}
	
	
	/**
	 * Click en botones aprobar y descartar para validar que
	 * no funcionen mientras no se seleccione un artículo
	 * @author alicia.ceja
	 */
	
	public void botonesDescartarAprobar() {
		
		testCase.addTextEvidenceCurrentStep("Click en botón Aprobar");
		u.highLight(btnAprobar);
		u.click(btnAprobar);
		testCase.addScreenShotCurrentStep(u, "Click botón Aprobar");
		u.unhighLight(btnAprobar);

		u.hardWait(3);
		
		testCase.addTextEvidenceCurrentStep("Click en botón Descartar");
		u.highLight(btnDescartar);
		u.click(btnDescartar);
		testCase.addScreenShotCurrentStep(u, "Click botón Descartar");
		u.unhighLight(btnDescartar);
		u.hardWait(2);
		
	}
	
	/**
	 * Da click en el botón descartar para eliminar un artículo
	 * @author alicia.ceja
	 */
	
	
	public void descartarArticulo() {
		
		try {
			u.hardWait(3);
			u.highLight(btnDescartar);
			u.click(btnDescartar);
			testCase.addScreenShotCurrentStep(u, "Click botón Descartar");
			
			By elementoTabla = By.id("jqg_gridArtsSinRegla_1");
			u.waitWebElement(elementoTabla, 120, 2);
			
		}catch(Exception e) {
			
			System.out.println(" -> " + e);
			throw new AssertionError("Error al dar click en el Botón Descartar");
			
		}
		

	}

	
	/**
	 * Genera reported dependiendo de la opcion seleccionada
	 * @param op
	 * @throws SQLException
	 * @author alicia.ceja
	 */
	public void generaReporte(String op) throws SQLException {
		String opcionSelect;
		String consulta;
		try {
			
			u.waitWebElement(reportes, 120, 2);
			u.highLight(reportes);
    		testCase.addBoldTextEvidenceCurrentStep("Apartado de reportes");
			u.mouseOver(reportes);
			testCase.addScreenShotCurrentStep("");
			u.unhighLight(reportes);

			 
	        switch (op) {
	 
	        case "ArtSinRegla":
	        	u.highLight(btnArtSinRegla);
	    		testCase.addBoldTextEvidenceCurrentStep("Selecciona la opcion de articulos sin regla");
	    		testCase.addScreenShotCurrentStep("");
				u.highLight(btnArtSinRegla);

	    		u.hardWait(1);
	    		u.mouseOverAndClick(btnArtSinRegla);
	    			    		
	    		u.waitWebElement(btnGeneraReport, 120, 3);
	    		
	    		testCase.addBoldTextEvidenceCurrentStep("Click en botón Generar Reporte");
	    		u.highLight(btnGeneraReport);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.mouseOverAndClick(btnGeneraReport);
	    		testCase.addBoldTextEvidenceCurrentStep("El reporte se descarga correctamente");
	    		u.hardWait(3);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(3);
	    	
	    		
	            break;
	 
	        // Case 2
	        case "cambiosMasivos":
	        	
	        	u.highLight(cambiosMasivos);
	    		testCase.addBoldTextEvidenceCurrentStep("Selecciona la opción de Cambios Masivos");
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.mouseOverAndClick(cambiosMasivos);
	    		
	    		u.waitWebElement(btnGeneraReport, 120, 3);

	    		testCase.addBoldTextEvidenceCurrentStep("Click en botón Generar Reporte");
	    		u.highLight(btnGeneraReport);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.mouseOverAndClick(btnGeneraReport);
	    		testCase.addBoldTextEvidenceCurrentStep("El reporte se descarga correctamente");
	    		u.hardWait(3);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(3);
	    		cambiarVentana(1);
	    		u.hardWait(3);
	       
	            break;
	 
	        // Case 3
	        case "costos":
	 
	        	u.highLight(costos);
	    		testCase.addBoldTextEvidenceCurrentStep("Selecciona la opción de Costos");
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.mouseOverAndClick(costos);
	    			    		
	    		u.waitWebElement(btnGeneraReport, 120, 3);
	    	
	    		testCase.addBoldTextEvidenceCurrentStep("Click en botón Generar Reporte");
	    		u.highLight(btnGeneraReport);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.mouseOverAndClick(btnGeneraReport);
	    		testCase.addBoldTextEvidenceCurrentStep("El reporte se descarga correctamente");
	    		u.hardWait(3);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(5);
	    		
	            break;
	        case "listadoCategoria":
	       	 
	        	u.highLight(listadoCategoria);
	    		testCase.addBoldTextEvidenceCurrentStep("Selecciona la opción de Listado Categoría");
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.mouseOverAndClick(listadoCategoria);
	    		
	    		testCase.addBoldTextEvidenceCurrentStep("Click en generar Reporte");
	    		
	    		
	    		u.waitWebElement(btnGeneraReport, 120, 3);
	    	
	    		u.highLight(btnGeneraReport);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.mouseOverAndClick(btnGeneraReport);
	            break;
	            
	        case "puntosEjecucion":
		       	 
	        	u.highLight(puntosEjecucion);
	    		testCase.addBoldTextEvidenceCurrentStep("Selecciona la opcion de Puntos Ejecucion");
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.mouseOverAndClick(puntosEjecucion);
	    		
	    		testCase.addBoldTextEvidenceCurrentStep("Selecciona una Plaza");

	    		u.highLight(selectPlaza);
				Select dropdownSelecTipoArchivo =  new Select (u.getDriver().findElement((selectPlaza)));
				dropdownSelecTipoArchivo.selectByVisibleText("Leon Bara");
	    		testCase.addScreenShotCurrentStep("");
	    		u.unhighLight(selectPlaza);

				u.hardWait(2);
	    			    		
	    		u.waitWebElement(btnGeneraReport, 120, 3);
	    		
	    		testCase.addBoldTextEvidenceCurrentStep("Click en botón Generar Reporte");
	    		u.highLight(btnGeneraReport);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.mouseOverAndClick(btnGeneraReport);
	    		testCase.addBoldTextEvidenceCurrentStep("El reporte se descarga correctamente");
	    		u.hardWait(3);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(7);

	            break;
	            
	            
	 
	        case "zonas":
		       	 
	        	u.highLight(zonas);
	    		testCase.addBoldTextEvidenceCurrentStep("Selecciona la opcion de Zonas");
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.mouseOverAndClick(zonas);
	    		
	    		testCase.addBoldTextEvidenceCurrentStep("Click en generar Reporte");
	    		
	    		
	    		u.waitWebElement(btnGeneraReport, 120, 3);
	    	
	    		testCase.addBoldTextEvidenceCurrentStep("Click en botón Generar Reporte");
	    		u.highLight(btnGeneraReport);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.mouseOverAndClick(btnGeneraReport);
	    		testCase.addBoldTextEvidenceCurrentStep("El reporte se descarga correctamente");
	    		u.hardWait(3);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(2);
	            break;
	            
	        case "cumplimiento":
		       	 
	        	u.highLight(cumplimiento);
	    		testCase.addBoldTextEvidenceCurrentStep("Selecciona del menú 'Reportes' la opción 'Cumplimiento'");
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.mouseOverAndClick(cumplimiento);
	        	u.unhighLight(cumplimiento);
	        	
	    		testCase.addBoldTextEvidenceCurrentStep("Seleccionar una plaza del filtro Plaza");
	    		opcionSelect="Mexico Bara";
	    		u.highLight(selectPlaza);
	    		drop(opcionSelect, selectPlaza);
	    		testCase.addScreenShotCurrentStep("");
	        	u.unhighLight(selectPlaza);

	    		u.hardWait(1);
	    		
	    		
	    		testCase.addBoldTextEvidenceCurrentStep("Seleccionar un valor para el filtro 'Zona de Precio'");
	    		testCase.addBoldTextEvidenceCurrentStep("No se debe permitir la seleccion ya que no tiene valores que seleccionar");
	    		u.highLight(selectZona);
	    		u.click(selectZona);
	    		testCase.addScreenShotCurrentStep("");
	        	u.unhighLight(selectZona);

	    		u.hardWait(1);
	    		
	    		testCase.addBoldTextEvidenceCurrentStep("Seleccionar un valor del filtro 'Super Grupo'");
	    		opcionSelect ="Agua Purificada";
	    		u.highLight(selectDiv);
	    		drop(opcionSelect,selectDiv);
	    		testCase.addScreenShotCurrentStep("");
	    		u.unhighLight(selectDiv);
	    		
	    		
	    		    		
	    		testCase.addBoldTextEvidenceCurrentStep("Seleccionar un valor del filtro 'Categoria'");
	    		u.highLight(selectCategoria);
	    		opcionSelect ="Agua con Sabor";
	    		drop(opcionSelect,selectCategoria);
	    		testCase.addScreenShotCurrentStep("");
	    		u.unhighLight(selectCategoria);
	    		
	    		u.highLight(btGeneraReportCumplimientio);
	    		
	    		testCase.addBoldTextEvidenceCurrentStep("Click en botón Generar Reporte");
	    		u.highLight(btGeneraReportCumplimientio);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.mouseOverAndClick(btGeneraReportCumplimientio);
	    		testCase.addBoldTextEvidenceCurrentStep("El reporte se descarga correctamente");
	    		u.hardWait(3);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(5);
	    		
	    	
	    		break;

	        case "Excepciones":
		       	
	        	
	        	u.highLight(excepciones);
	    		testCase.addBoldTextEvidenceCurrentStep("Selecciona la opcion de Excepciones");
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.mouseOverAndClick(excepciones);
	    		
	    	
	    		testCase.addBoldTextEvidenceCurrentStep("Seleccionar una plaza del filtro Plaza");
	    		opcionSelect="Mexico Bara";
	    		u.highLight(selectPlaza);
	    		drop(opcionSelect, selectPlaza);
	    		testCase.addScreenShotCurrentStep("");
	        	u.unhighLight(selectPlaza);

	    		u.hardWait(1);
	    		
	    		testCase.addBoldTextEvidenceCurrentStep("Seleccionar un valor del filtro 'Super Grupo'");
	    		opcionSelect ="Agua Purificada";
	    		u.highLight(selectDiv);
	    		drop(opcionSelect,selectDiv);
	    		testCase.addScreenShotCurrentStep("");
	    		u.unhighLight(selectDiv);
	    			    		
	    		
	    		testCase.addBoldTextEvidenceCurrentStep("Seleccionar un valor del filtro 'Categoria'");
	    		u.highLight(selectCategoria);
	    		opcionSelect ="Agua con Sabor";
	    		drop(opcionSelect,selectCategoria);
	    		testCase.addScreenShotCurrentStep("");
	    		u.unhighLight(selectCategoria);
	    		
	    		testCase.addBoldTextEvidenceCurrentStep("Seleccionar un valor del filtro 'Status'");
	    		u.highLight(selectStatus);
	    		opcionSelect ="VIGENTE";
	    		drop(opcionSelect,selectStatus);
	    		testCase.addScreenShotCurrentStep("");
	    		u.unhighLight(selectStatus);
	    		
	    		testCase.addBoldTextEvidenceCurrentStep("Click en botón Generar Reporte");
	    		u.highLight(btnGeneraReport);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.mouseOverAndClick(btnGeneraReport);
	    		testCase.addBoldTextEvidenceCurrentStep("El reporte se descarga correctamente");
	    		u.hardWait(3);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(5);
	    	
	            break;
	            
	        case "folioRetek":
		       	 
	        	u.highLight(folioRetek);
	    		testCase.addBoldTextEvidenceCurrentStep("Selecciona la opcion de Folio Retek");
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.mouseOverAndClick(folioRetek);
	    	
	    		testCase.addBoldTextEvidenceCurrentStep("Seleccionar una plaza del filtro Plaza");
	    		opcionSelect="Mexico Bara";
	    		u.highLight(selectPlaza);
	    		drop(opcionSelect, selectPlaza);
	    		testCase.addScreenShotCurrentStep("");
	        	u.unhighLight(selectPlaza);

	    		u.hardWait(1);
	    			    		    	
	    		testCase.addBoldTextEvidenceCurrentStep("Click en botón Generar Reporte");
	    		u.highLight(btnGeneraReport);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.mouseOverAndClick(btnGeneraReport);
	    		testCase.addBoldTextEvidenceCurrentStep("El reporte se descarga correctamente");
	    		u.hardWait(3);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(3);
	    	

	            break;
	            
	        case "mantenimientoPrecios":
	       	 
	        	u.highLight(mantenimientoPrecios);
	    		testCase.addBoldTextEvidenceCurrentStep("Selecciona la opcion de 'Mantenimiento de Precios'");
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.mouseOverAndClick(mantenimientoPrecios);
	    		
	    		u.waitWebElement(btnGeneraReport, 120, 2);
	    		
	    		testCase.addBoldTextEvidenceCurrentStep("Seleccionar una plaza del filtro Plaza");
	    		opcionSelect="Mexico Bara";
	    		u.highLight(selectPlaza);
	    		drop(opcionSelect, selectPlaza);
	    		testCase.addScreenShotCurrentStep("");
	        	u.unhighLight(selectPlaza);
	        	
	        	testCase.addBoldTextEvidenceCurrentStep("Click en botón Generar Reporte");
	    		u.highLight(btnGeneraReport);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.mouseOverAndClick(btnGeneraReport);
	    		testCase.addBoldTextEvidenceCurrentStep("El reporte se descarga correctamente");
	    		u.hardWait(3);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(3);
	        	
	            break;
	        
	        case "monitoreoPrecios":
		       	 
	        	u.highLight(monitoreoPrecios);
	    		testCase.addBoldTextEvidenceCurrentStep("Selecciona la opcion de 'Monitoreo de Precios'");
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.mouseOverAndClick(monitoreoPrecios);
	    		
	    		u.waitWebElement(btnReportMonitoreo, 120, 2);

	    		testCase.addBoldTextEvidenceCurrentStep("Seleccionar una plaza del filtro Plaza");
	    		opcionSelect="Mexico Bara";
	    		u.highLight(selectPlaza);
	    		drop(opcionSelect, selectPlaza);
	    		testCase.addScreenShotCurrentStep("");
	        	u.unhighLight(selectPlaza);
	        	
	    		testCase.addBoldTextEvidenceCurrentStep("Seleccionar tipo monitoreo");
	        	opcionSelect="CANASTO 2013";
	    		u.highLight(selectTipoMonitoreo);
	    		drop(opcionSelect, selectTipoMonitoreo);
	    		testCase.addScreenShotCurrentStep("");
	        	u.unhighLight(selectTipoMonitoreo);
	    		
	        	
	    		testCase.addBoldTextEvidenceCurrentStep("Click en botón Generar Reporte");
	    		u.highLight(btnReportMonitoreo);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.mouseOverAndClick(btnReportMonitoreo);
	    		testCase.addBoldTextEvidenceCurrentStep("El reporte se descarga correctamente");
	    		u.hardWait(3);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(3);

	        	
	            break;
	            
	        case "reglasPrecio":
		       	 
	        	u.highLight(reglaPrecioReport);
	    		testCase.addBoldTextEvidenceCurrentStep("Selecciona la opción de Regla de Precio");
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.mouseOverAndClick(reglaPrecioReport);
	    		
	    		testCase.addBoldTextEvidenceCurrentStep("Dar clic en el botón 'Generar Reporte Estructura'");
	    		
	    		
	    		u.waitWebElement(btnGenRepEstructuraRegPre, 120, 3);
	    	
	    		u.highLight(btnGenRepEstructuraRegPre);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.mouseOverAndClick(btnGenRepEstructuraRegPre);
	    		u.unhighLight(btnGenRepEstructuraRegPre);
	    	
	    		u.hardWait(2);
	    		testCase.addScreenShotCurrentStep("");
	    		
	    		cambiarVentana(1);
	    		
	    		testCase.addBoldTextEvidenceCurrentStep("Dar clic en el botón Generar Reporte SKUS");
	    		
	    		
	    		u.waitWebElement(btnSKUregPrecio, 120, 3);
	    	
	    		u.highLight(btnSKUregPrecio);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.mouseOverAndClick(btnSKUregPrecio);
	    		u.unhighLight(btnSKUregPrecio);
	    		u.hardWait(2);
	    		testCase.addScreenShotCurrentStep("");

	            break;
	        
	        case "AsignaRegla":
	        
	    		testCase.addBoldTextEvidenceCurrentStep("Selecciona la opcion de Asignacion de Reglas");
				u.highLight(asignaReglas);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.mouseOverAndClick(asignaReglas);
	    			    		
	    		u.waitWebElement(btnGeneraReport, 120, 3);
	    		
	    		testCase.addBoldTextEvidenceCurrentStep("Click en botón Generar Reporte");
	    		u.highLight(btnGeneraReport);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.mouseOverAndClick(btnGeneraReport);
	    		testCase.addBoldTextEvidenceCurrentStep("El reporte se descarga correctamente");
	    		u.hardWait(3);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(3);
	    		cambiarVentana(1);
	    		u.hardWait(3);
	    		
	            break;
	            
	        case "ReglaNegocio":
		        
	    		testCase.addBoldTextEvidenceCurrentStep("Selecciona la opcion de Reglas de Negocio");
				u.highLight(reglaNegocio);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.mouseOverAndClick(reglaNegocio);

	    		u.waitWebElement(btnGeneraReport, 120, 3);

	    		
	    		testCase.addBoldTextEvidenceCurrentStep("Seleccionar una plaza del filtro Plaza");
	    		opcionSelect="Mexico Bara";
	    		u.highLight(selectPlaza);
	    		drop(opcionSelect, selectPlaza);
	    		testCase.addScreenShotCurrentStep("");
	        	u.unhighLight(selectPlaza);
	    			    		
	        	u.hardWait(1);
	        	
	    		testCase.addBoldTextEvidenceCurrentStep("Click en botón Generar Reporte");
	    		u.highLight(btnGeneraReport);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.mouseOverAndClick(btnGeneraReport);
	    		testCase.addBoldTextEvidenceCurrentStep("El reporte se descarga correctamente");
	    		u.hardWait(3);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(3);
	    		cambiarVentana(1);
	    		u.hardWait(3);
	    		
	            break;

	        default:
	        	
	        }
			
		}catch(Exception e) {
			System.out.println("Error---> " + e);

			throw new AssertionError("Error al generar reporte");

		}
 
	}
	
	
	public void drop(String op, By selectElement) {
		
		Select dropdown=  new Select (u.getDriver().findElement((selectElement)));
		dropdown.selectByVisibleText(op);
	}
	
	/**
	 * Scroll para encontrar elementos 
	 * @param driver
	 * @param element
	 * @author alicia.ceja
	 */
	
	public static void scrollToElement(WebDriver driver, WebElement element) {
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
		javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	

	/**
	 * Cambia el foco a la aventana indicada donde 0 = Pantalla inicial
	 * y 1 = Pantalla nueva
	 * @param pantalla
	 * @author alicia.ceja
	 */
	
	 public  void cambiarVentana(int pantalla) {
	        try {
	            ArrayList<String> tabs = new ArrayList<String> (u.getDriver().getWindowHandles());
	            u.getDriver().switchTo().window(tabs.get(pantalla));
	        } catch (Exception e) {
	            
	        }
	    }
}
