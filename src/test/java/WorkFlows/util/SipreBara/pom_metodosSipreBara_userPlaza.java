package WorkFlows.util.SipreBara;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;

import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class pom_metodosSipreBara_userPlaza {


	SeleniumUtil u;
	TestCase testCase;
	private WebDriverWait wait;

	public pom_metodosSipreBara_userPlaza (SeleniumUtil u, TestCase testCase ) {
		this.u = u;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		this.testCase = testCase;

	}
	
	SikuliUtil sikuli = new SikuliUtil();
	String pathImages = sikuli.PatternPath("WorkFlows"+File.separator+"sipreBara");
	Pattern aceptar = sikuli.PatternImage(pathImages + "btnAceptaSesion.png");
	
	
	By btnReportes = By.id("reportes");
	By btnReglasNegocio = By.id("mod33_RepPlazaReglaNeg");
	By excepcionesProxVencer = By.id("mod31_RepPlazaExcepAVencer");
	By mantenimientoPrecios = By.id("mod35_RepPlazaMantPrecios");
	By puntosEjecucion = By.id("mod8_Descarga_PE");
	By btnLimpiar = By.cssSelector("#reportesGral > a:nth-child(2)");
	By btnGeneraReporte = By.cssSelector("#reportesGral > a:nth-child(3)");
	By cumplimiento = By.cssSelector("#mod32_RepPlazaCumplimiento");
	By folioRetek = By.cssSelector("#mod36_RepPlazaFolioRetek");
	By monitoreo = By.cssSelector("#mod39_RepPlazaMonitoreo");
	By ultimus = By.cssSelector("#tblMain");

	By exepcionesProxVencer = By.cssSelector("#mod31_RepPlazaExcepAVencer");
	By selectPlaza = By.id("divPlazaId_cmb");
	By selectSuperGrupo = By.cssSelector("#divisiones_cmb"); //SuperGrupo
	By selectCategoria = By.id("categorias_cmb");
	By selectTipoMonitoreo = By.cssSelector("#tipoMonitoreo_cmb");
	By btnGeneraReport = By.cssSelector("#reportesGral > a:nth-child(3)");
	By btnGeneraReportMonitoreo= By.cssSelector("#repMonitoreo > a:nth-child(3)");
	
	By config =  By.cssSelector("#products");
	By puntoEjecucion= By.cssSelector("#mod1_PE");
	By puntosPendientes= By.cssSelector("input.col2:nth-child(2)");
	
	//PUNTO EJECUCION
	By selectStatus_PE = By.cssSelector("#divStatusPE");
	By selectZonaPrecio_PE = By.cssSelector("#divZona");
	By selectSuperGrupo_PE = By.cssSelector("#divisiones_cmb");
	By selectCategoria_PE = By.cssSelector("#categorias_cmb");
	
	By btnBuscar_PE = By.cssSelector("a.boton:nth-child(4)");
	By tableZonaArticulo = By.cssSelector("#gbox_gridZonasItem");
	By ckbSelectArtiZonaPrecio = By.cssSelector("#cb_gridZonasItem");
	By inputNuevoPrecio = By.cssSelector("#precioPEId");
	
	By btnAplicaPE = By.cssSelector("a.boton:nth-child(2)");
	By inputPrecioMax = By.cssSelector("#precioMaxId");

	//SOLICITUD DE EXCEPCIONES
	By btnAceptaCrear = By.cssSelector("a.rojo:nth-child(3)");
	By selectRacional = By.cssSelector("#racionales_cmb");
	By tableFolio = By.xpath("//table[contains(@id,'gridSolicitudExcepciones')]//tr[2]//td[2]");
	By inputDuracion30d = By.cssSelector("#d30");
	By btnAceptaSolicitud  = By.cssSelector("a.rojo:nth-child(1)");
	
	//MANTENIMIENTO DE PRECIOS
	
		By btnMantPrecios = By.cssSelector("#mod2_MP");
		By inputItemAcep = By.cssSelector("#searchString");
		By btnBuscaArtiAcep = By.cssSelector("a.boton:nth-child(4)");
		By selectStatus =By.xpath("//*[@id=\"statusExcepcion_cmb\"]"); 
		
		By inputCalendar = By.cssSelector("#datepicker");
		By btnAplicarCambioP = By.cssSelector("a.rojo:nth-child(1)");
		By selectDecision = By.cssSelector("#xOperacion");
	

	/**
	 * Genera reporte dependiendo de la opción seleccionada
	 * @param op
	 * @author alicia.ceja
	 */
public void generarReporte(String op) {
	String opcionSelect;

			
		try {
			
			u.waitWebElement(btnReportes, 120, 2);
			u.highLight(btnReportes);
			testCase.addScreenShotCurrentStep(u, "");
			u.mouseOver(btnReportes);
			 
	        switch (op) {
	 
	        case "reglaNegocio":
	        	testCase.addBoldTextEvidenceCurrentStep("Seleccionar del menú ''reportes'' la opción Reglas de Negocio");
	    		u.hardWait(2);
	    		u.highLight(btnReglasNegocio);
	    		testCase.addScreenShotCurrentStep(u, "");
	    		u.mouseOverAndClick(btnReglasNegocio);
	    		
	    		u.waitWebElement(btnGeneraReporte, 120, 3);
	    		
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
	            
	        case "excepcionesProxVencer":
	        	testCase.addBoldTextEvidenceCurrentStep("Seleccionar del menú ''reportes'' la opción Excepciones por Vencer");
	    		u.hardWait(2);
	    		u.highLight(excepcionesProxVencer);
	    		testCase.addScreenShotCurrentStep(u, "");
	    		u.mouseOverAndClick(excepcionesProxVencer);
	    		
	    		u.waitWebElement(btnGeneraReporte, 120, 3);
	    		testCase.addBoldTextEvidenceCurrentStep("Click en generar Reporte");
	    		u.highLight(btnGeneraReporte);
	    		u.mouseOverAndClick(btnGeneraReporte);
	    		testCase.addScreenShotCurrentStep(u, "");
	    		
	    		u.hardWait(3);
	 
	            break;
	            
	        case "mantenimientoPrecios":
	        	testCase.addBoldTextEvidenceCurrentStep("Seleccionar del menú ''reportes'' la opción Mantenimiento de Precios");
	    		u.hardWait(2);
	    		u.highLight(mantenimientoPrecios);
	    		testCase.addScreenShotCurrentStep(u, "");
	    		u.mouseOverAndClick(mantenimientoPrecios);
	    		
	    		u.waitWebElement(btnGeneraReporte, 120, 3);
	    		testCase.addBoldTextEvidenceCurrentStep("Click en botón Generar Reporte");
	    		u.highLight(btnGeneraReport);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(2);
	    		u.mouseOverAndClick(btnGeneraReport);
	    		testCase.addBoldTextEvidenceCurrentStep("El reporte se descarga correctamente");
	    		u.hardWait(3);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(3);
	    		cambiarVentana(1);
	    		u.hardWait(3);
	 
	            break;
	            
	        case "puntosEjecucion":
	        	testCase.addBoldTextEvidenceCurrentStep("Seleccionar del menú ''reportes'' la opción Puntos Ejecución ");
	    		u.hardWait(2);
	    		u.highLight(puntosEjecucion);
	    		testCase.addScreenShotCurrentStep(u, "");
	    		u.mouseOverAndClick(puntosEjecucion);
	    		
	    		u.waitWebElement(btnGeneraReporte, 120, 3);
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
	            
	         case "Cumplimiento":
	        	 
	        	 	u.highLight(cumplimiento);
		    		testCase.addBoldTextEvidenceCurrentStep("Selecciona del menú 'Reportes' la opción 'Cumplimiento'");
		    		testCase.addScreenShotCurrentStep("");
		    		u.hardWait(1);
		    		u.mouseOverAndClick(cumplimiento);
		        	u.unhighLight(cumplimiento);
		        	
		    		testCase.addBoldTextEvidenceCurrentStep("Seleccionar una plaza del filtro Plaza");
		    		opcionSelect="Leon Bara";
		    		u.highLight(selectPlaza);
		    		//drop(opcionSelect, selectPlaza);
		    		testCase.addScreenShotCurrentStep("");
		    		//u.hardWait(3);
		        	//u.unhighLight(selectPlaza);

		    		u.hardWait(4);
		    
		    		
		    		testCase.addBoldTextEvidenceCurrentStep("Seleccionar un valor del filtro 'Super Grupo'");
		    		opcionSelect ="Botanas";
		    		u.highLight(selectSuperGrupo);
		    		drop(opcionSelect,selectSuperGrupo);
		    		testCase.addScreenShotCurrentStep("");
		    		u.unhighLight(selectSuperGrupo);
		    		
		    		u.hardWait(5);

		    		testCase.addBoldTextEvidenceCurrentStep("Seleccionar un valor del filtro 'Categoría'");
		    		u.highLight(selectCategoria);
		    		opcionSelect ="Frituras";
		    		drop(opcionSelect,selectCategoria);
		    		testCase.addScreenShotCurrentStep("");
		    		u.unhighLight(selectCategoria);
		    		
		    		u.hardWait(4);
		    		
		    		testCase.addBoldTextEvidenceCurrentStep("Click en botón Generar Reporte");
		    		u.highLight(btnGeneraReport);
		    		testCase.addScreenShotCurrentStep("");
		    		u.mouseOverAndClick(btnGeneraReport);
		    		testCase.addBoldTextEvidenceCurrentStep("El reporte se descarga correctamente");
		    		u.hardWait(3);
		    		testCase.addScreenShotCurrentStep("");
		    		u.hardWait(3);
		    		cambiarVentana(1);
		    		
	        	 
	        	 break;
	        	 
	         case "Exepciones":
	        	 
	        	 u.highLight(exepcionesProxVencer);
		    		testCase.addBoldTextEvidenceCurrentStep("Selecciona del menú 'Reportes' la opción 'Exepciones Proximas a vencer'");
		    		testCase.addScreenShotCurrentStep("");
		    		u.hardWait(1);
		    		u.mouseOverAndClick(exepcionesProxVencer);
		        	u.unhighLight(exepcionesProxVencer);
		        	
		    		testCase.addBoldTextEvidenceCurrentStep("Seleccionar una plaza del filtro Plaza");
		    		opcionSelect="Leon Bara";
		    		u.highLight(selectPlaza);
		    		drop(opcionSelect, selectPlaza);
		    		testCase.addScreenShotCurrentStep("");
		        	u.unhighLight(selectPlaza);

		    		u.hardWait(1);
		    
		    		
		    		testCase.addBoldTextEvidenceCurrentStep("Seleccionar un valor del filtro 'Super Grupo'");
		    		opcionSelect ="Botanas";
		    		u.highLight(selectSuperGrupo);
		    		drop(opcionSelect,selectSuperGrupo);
		    		testCase.addScreenShotCurrentStep("");
		    		u.unhighLight(selectSuperGrupo);
		    		

		    		testCase.addBoldTextEvidenceCurrentStep("Seleccionar un valor del filtro 'Categoría'");
		    		u.highLight(selectCategoria);
		    		opcionSelect ="Frituras";
		    		drop(opcionSelect,selectCategoria);
		    		testCase.addScreenShotCurrentStep("");
		    		u.unhighLight(selectCategoria);
		    		
		    		testCase.addBoldTextEvidenceCurrentStep("Click en botón Generar Reporte");
		    		u.highLight(btnGeneraReport);
		    		testCase.addScreenShotCurrentStep("");
		    		u.mouseOverAndClick(btnGeneraReport);
		    		testCase.addBoldTextEvidenceCurrentStep("El reporte se descarga correctamente");
		    		u.hardWait(3);
		    		testCase.addScreenShotCurrentStep("");
		    		u.hardWait(3);
		    		cambiarVentana(1);
	        	 
	        	 
	         	break;
	         	
	         case "folioRetek":
		       	 
		        	u.highLight(folioRetek);
		    		testCase.addBoldTextEvidenceCurrentStep("Selecciona la opcion de Folio Retek");
		    		testCase.addScreenShotCurrentStep("");
		    		u.hardWait(1);
		    		u.mouseOverAndClick(folioRetek);
		    	
		    		testCase.addBoldTextEvidenceCurrentStep("Seleccionar una plaza del filtro Plaza");
		    		opcionSelect="Leon Bara";
		    		u.highLight(selectPlaza);
		    		drop(opcionSelect, selectPlaza);
		    		testCase.addScreenShotCurrentStep("");
		        	u.unhighLight(selectPlaza);

		    		u.hardWait(1);
		    				    	
		    		u.waitWebElement(btnGeneraReport, 120, 3);
		    	
		    		testCase.addBoldTextEvidenceCurrentStep("Click en botón Generar Reporte");
		    		u.highLight(btnGeneraReport);
		    		testCase.addScreenShotCurrentStep("");
		    		u.mouseOverAndClick(btnGeneraReport);
		    		testCase.addBoldTextEvidenceCurrentStep("El reporte se descarga correctamente");
		    		u.hardWait(90);
		    		testCase.addScreenShotCurrentStep("");
		    		u.hardWait(3);
		    		cambiarVentana(1);
	        	 

		            break;
		            
	         case "Monitoreo":
		       	 
		        	u.highLight(monitoreo);
		    		testCase.addBoldTextEvidenceCurrentStep("Selecciona la opcion de Monitoreo");
		    		testCase.addScreenShotCurrentStep("");
		    		u.hardWait(1);
		    		u.mouseOverAndClick(monitoreo);
		    	
		    		testCase.addBoldTextEvidenceCurrentStep("Seleccionar una plaza del filtro Plaza");
		    		opcionSelect="Leon Bara";
		    		u.highLight(selectPlaza);
		    		drop(opcionSelect, selectPlaza);
		    		testCase.addScreenShotCurrentStep("");
		        	u.unhighLight(selectPlaza);
		        	
		        	testCase.addBoldTextEvidenceCurrentStep("Seleccionar Tipo de Monitoreo");
		    		opcionSelect="[Todos]";
		    		u.highLight(selectTipoMonitoreo);
		    		drop(opcionSelect, selectTipoMonitoreo);
		    		testCase.addScreenShotCurrentStep("");
		        	u.unhighLight(selectTipoMonitoreo);

		    		u.hardWait(1);
		    				    	
		    		u.waitWebElement(btnGeneraReport, 120, 3);
		    	
		    		testCase.addBoldTextEvidenceCurrentStep("Click en botón Generar Reporte");
		    		u.highLight(btnGeneraReportMonitoreo);
		    		testCase.addScreenShotCurrentStep("");
		    		u.mouseOverAndClick(btnGeneraReportMonitoreo);
		    		testCase.addBoldTextEvidenceCurrentStep("El reporte se descarga correctamente");
		    		u.hardWait(3);
		    		testCase.addScreenShotCurrentStep("");
		    		u.hardWait(3);
		    		cambiarVentana(1);
	        	 

		            break;
		            

	        // Default case
	        default:
	        	
	        }
			
		}catch(Exception e) {
			System.out.println("Error---> " + e);

			throw new AssertionError("Error al generar reporte");

		}
 
	}
	

	/**
	 * Selecciona una opción del DropDown
	 * @param op
	 * @param selectElement
	 */
		
	public void drop(String op, By selectElement) {
		
		Select dropdown=  new Select (u.getDriver().findElement((selectElement)));
		dropdown.selectByVisibleText(op);
	}
		

	
	/**
	  * Cambia el foco a la aventana indicada donde 0 = Pantalla inicial
	 * y 1 = Pantalla nueva
	 * @param pantalla
	 * @author alicia.ceja
	 * @param pantalla
	 */
	
	public  void cambiarVentana(int pantalla) {
        try {
            ArrayList<String> tabs = new ArrayList<String> (u.getDriver().getWindowHandles());
            u.getDriver().switchTo().window(tabs.get(pantalla));
        } catch (Exception e) {
            
        }
    }
	
	/**Ingresa a la opcion Puntos Ejecucion y valida
	 * que los puntos pendientes sean mayor a 0
	 * @author alicia.ceja
	 */
	
	public void puntosEjecucion() {
		
		try {
			
			testCase.addBoldTextEvidenceCurrentStep("Seleccionar el Menú Configuración");
			u.mouseOver(config);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(2);
			testCase.addBoldTextEvidenceCurrentStep("Seleccionar Puntos de Ejecución");

			u.highLight(puntoEjecucion);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(1);
			u.mouseOverAndClick(puntoEjecucion);
			testCase.addBoldTextEvidenceCurrentStep("Validar que el contador de Puntos de Ejecución Pendientes sea mayor a 0");

			u.mouseOverAndClick(puntosEjecucion);
			
			u.waitWebElement(puntosPendientes, 120, 3);
			

			u.highLight(puntosPendientes);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(2);
          

			int puntos = Integer.parseInt(u.getDriver().findElement(puntosPendientes).getAttribute("value"));

			
			if (puntos <= 0) {
				
				throw new AssertionError("No hay artículos pendientes disponibles");
			}
			
			System.out.println("Value ------------>"+puntos);
			u.unhighLight(puntosPendientes);
			
		


			
		   }catch(Exception e) {
			   
			System.out.println("Error---> " + e);

			throw new AssertionError("Error al validar los puntos pendientes de ejecución"+e);
		}

	}
	
	/**
	 * Selecciona los filtros necesarios para haceer la busqueda
	 * de articulos para aplicar una excepción
	 * @param status
	 * @param zona
	 * @param grupo
	 * @param cat
	 */
	public void selectFiltrosPE(String status, String zona, String grupo, String cat) {
		
	try {
		
		u.highLight(selectStatus_PE);
		drop(status, selectStatus_PE);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.unhighLight(selectStatus_PE);
		
		u.hardWait(2);
		
		u.highLight(selectZonaPrecio_PE);
		drop(zona, selectZonaPrecio_PE);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.unhighLight(selectZonaPrecio_PE);
		
		u.hardWait(2);
		
		u.highLight(selectSuperGrupo_PE);
		drop(grupo, selectSuperGrupo_PE);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.unhighLight(selectSuperGrupo_PE);
		
		u.hardWait(2);
		
		u.highLight(selectCategoria_PE);
		drop(cat, selectCategoria_PE);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.unhighLight(selectCategoria_PE);
		
		u.hardWait(2);


		drop("CONFIGURADO", selectStatus_PE);
		drop("-- Opcion--", selectZonaPrecio_PE);
		
		testCase.addBoldTextEvidenceCurrentStep("Dar click en el botón buscar");

		
		u.scrollToElement(btnBuscar_PE);
		u.hardWait(2);
		
		u.highLight(btnBuscar_PE);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.click(btnBuscar_PE);
		u.unhighLight(btnBuscar_PE);

		
	}catch(Exception e) {
		
		   
		System.out.println("Error---> " + e);
		throw new AssertionError("Error al seleccionar filtros y buscar artículos"+e);
			
		}
	}
	
	
	/**
	 * Selecciona un artículo y aplica una Excepcion
	 * @param nuevoPrecio
	 * @return
	 */
	
	public String  selectArtiPE (String nuevoPrecio, String itemSelec) {
		try {
			
			By item = By.xpath("//table[contains(@id,'gridItemsPlaza')]//tr//td[text( )='"+itemSelec+"']");

			String item_ = u.getDriver().findElement(item).getAttribute("title");
			u.waitWebElement(item, 10, 3);
			u.highLight(item);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(2);
			u.click(item);
			u.unhighLight(item);
			
			testCase.addBoldTextEvidenceCurrentStep("Seleccionar uno o más chekbox de Articulos Zona");
			
			u.highLight(ckbSelectArtiZonaPrecio);
			u.click(ckbSelectArtiZonaPrecio);
			u.hardWait(2);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(2);
			u.unhighLight(ckbSelectArtiZonaPrecio);
			

			return item_;
			
		}catch(Exception e)
		{
			System.out.println("Error---> " + e);
			throw new AssertionError("Error al seleccionar item"+e);
				
		}
		
		
				
	}
	
	
	public void aplicaPE(String nuevoP) {
		try {
			
			
			
			u.highLight(inputNuevoPrecio);
			u.sendKeys(inputNuevoPrecio, nuevoP);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(2);
			u.unhighLight(inputNuevoPrecio);
			
			testCase.addBoldTextEvidenceCurrentStep(" Dar clic en botón aplicar");
			u.highLight(btnAplicaPE);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(2);
			u.click(btnAplicaPE);
			
			
			testCase.addBoldTextEvidenceCurrentStep(" Dar clic en botón aplicar");
			u.waitWebElement(btnAceptaCrear, 120, 3);
			u.highLight(btnAceptaCrear);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(2);
			u.click(btnAceptaCrear);
			
			
			
			
			
		}catch(Exception e) {
			System.out.println("Error---> " + e);
			throw new AssertionError("Error al aplicar Punto de ejecucion "+e);
		}
	}
	
	public void solicitudExcepcion () {
		try {
			u.waitWebElement(selectRacional, 120, 4);
			u.scrollToElement(tableFolio);
			u.hardWait(2);
			u.highLight(tableFolio);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(2);
			
			testCase.addBoldTextEvidenceCurrentStep("Seleccionar el racional de : Excepción solicitada por categoria");

			u.highLight(selectRacional);
			drop("Excepción solicitada por la Categoría (OS)", selectRacional);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(2);
			u.unhighLight(selectRacional);
			
			testCase.addBoldTextEvidenceCurrentStep("Seleccionar la duración de 30 dias en el radio botón");
			u.highLight(inputDuracion30d);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(3);
			u.click(inputDuracion30d);
			u.unhighLight(inputDuracion30d);
			
			testCase.addBoldTextEvidenceCurrentStep("Dar clic en aplicar");
			u.highLight(btnAceptaSolicitud);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(3);
			u.click(btnAceptaSolicitud);
			u.unhighLight(btnAceptaSolicitud);
			u.hardWait(2);
			
	
			
		}catch(Exception e) {
			System.out.println("Error---> " + e);
			throw new AssertionError("Error al aceptar solicitud "+e);
		
		}
	}
	
	public void buscaExcAceptada (String item) {
		try {
			
			u.hardWait(5);
			testCase.addBoldTextEvidenceCurrentStep("Seleccionar el Menú Configuración");
			u.mouseOver(config);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(2);
			testCase.addBoldTextEvidenceCurrentStep("Seleccionar Mantenimiento de precios");

			u.highLight(btnMantPrecios);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(1);
			u.mouseOverAndClick(btnMantPrecios);
			u.hardWait(2);

			testCase.addBoldTextEvidenceCurrentStep("Ingresar item");
			
			u.waitWebElement(selectStatus, 120, 6);
			u.highLight(selectStatus);
			drop("TOTAL", selectStatus);
			u.mouseOverAndClick(btnBuscaArtiAcep);
			u.hardWait(3);

			u.sendKeys(inputItemAcep, item);
			
			testCase.addBoldTextEvidenceCurrentStep("Click en buscar");
			u.highLight(btnBuscaArtiAcep);
			u.hardWait(2);
			u.mouseOverAndClick(btnBuscaArtiAcep);
			u.unhighLight(btnBuscaArtiAcep);
			u.hardWait(3);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(2);

			

			
		}catch(Exception e) {
			System.out.println("Error---> " + e);
			throw new AssertionError("Error al buscar Excepciones aceptadas"+e);
		}		
	}
	
	
	public void cambioPrecio(String dia, String itemSelec) throws FindFailed {
		By diaCalendar = By.xpath("//table[contains(@class,'ui-datepicker-calendar')]//tr//td//a[text( )='"+dia+"']");
		By item = By.xpath("//table[contains(@id,'gridMantenimiento_frozen')]//tr//td[text( )='"+itemSelec+"']");
		By btnInicio = By.cssSelector("#home");
		u.hardWait(5);
		u.scrollToElement(btnInicio);
		u.hardWait(1);
		u.mouseOver(btnInicio);
		u.hardWait(1);
		u.mouseOverAndClick(btnInicio);
		
		u.hardWait(5);
		testCase.addBoldTextEvidenceCurrentStep("Seleccionar el Menú Configuración");
		u.mouseOver(config);
		u.unhighLight(puntoEjecucion);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		testCase.addBoldTextEvidenceCurrentStep("Seleccionar Mantenimiento de precios");

		u.highLight(btnMantPrecios);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.mouseOverAndClick(btnMantPrecios);
		u.hardWait(2);

		testCase.addBoldTextEvidenceCurrentStep("Ingresar item");
		
		u.waitWebElement(selectStatus, 120, 6);
		u.highLight(selectStatus);
		drop("TOTAL", selectStatus);
		u.mouseOverAndClick(btnBuscaArtiAcep);
		u.hardWait(3);

		u.sendKeys(inputItemAcep, itemSelec);
		
		testCase.addBoldTextEvidenceCurrentStep("Click en buscar");
		u.highLight(btnBuscaArtiAcep);
		u.hardWait(2);
		u.mouseOverAndClick(btnBuscaArtiAcep);
		u.unhighLight(btnBuscaArtiAcep);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);

		
		
		u.hardWait(3);
		
		testCase.addBoldTextEvidenceCurrentStep("Seleccionar el registro");
		u.highLight(item);
		u.click(item);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);

		
		testCase.addBoldTextEvidenceCurrentStep(" En la pantalla inferior de Configuración seleccionamos la fecha sysdate+1");
		u.scrollToElement(inputCalendar);
		u.highLight(inputCalendar);
		u.click(inputCalendar);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.click(diaCalendar);
		testCase.addScreenShotCurrentStep("");
		u.unhighLight(inputCalendar);
		u.hardWait(2);

		
		testCase.addBoldTextEvidenceCurrentStep("Seleccionamos la opción \"Aceptar cambio de precio\" del campo Desición");
		u.highLight(selectDecision);
		drop("Aceptar cambio de precio", selectDecision);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.unhighLight(selectDecision);


		testCase.addBoldTextEvidenceCurrentStep("Dar click en el botón aplicar");
		u.highLight(btnAplicarCambioP);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.click(btnAplicarCambioP);
		
		sikuli.screen.wait(aceptar, 30);
		sikuli.screen.click(aceptar);
		u.hardWait(3);

		testCase.addScreenShotCurrentStep("");

		u.hardWait(3);
		
		


		
	}
	
	
}
