package WorkFlows.util.SipreOXXO;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;

import com.ibm.icu.util.Calendar;

import WorkFlows.util.GlobalVariables;
import modelo.TestCase;
import utils.ExcelUtil;
import utils.FileUtil;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class pomSipreOXXO {
	SeleniumUtil u;
	TestCase testCase;
	private WebDriverWait wait;
	SikuliUtil sikuli = new SikuliUtil();
	String pathImages = sikuli.PatternPath("WorkFlows"+File.separator+"SipreOXXO");
	
	/// WEB ELEMENTS SIPRE Menu y Sub-Menu///
		By menuReportes = By.id("reportes");
		By menuConfiguracion = By.id("products");
		By loadiCargaArchivo = By.xpath("//img[@src='images/loadingAnimation.gif']");
		By subMenuArticulosDeEnfoque = By.id("mod47_RepAdEnfoque");
		By subMenuCumplimiento = By.id("mod19_RepAdCumplimiento");
		By subMenuCumplimientoPega = By.id("mod19_RepAdCumplimiento");
		By subMenuEstrategiaPrecios = By.id("mod44_RepAdEstrategiaPrecios");
		By subMenuExcepciones = By.id("mod21_RepAdExcep");
		By subMenuExcepcionesPega = By.id("mod31_RepPlazaExcepAVencer");
		By subMenuFolioRetek = By.id("mod37_RepAdFolioRetek");
		By subMenuFolioRetekPega = By.id("mod36_RepPlazaFolioRetek");
		By subMenuMantenimientoPrecios = By.id("mod18_RepAdMantPrecios");
		By subMenuMantenimientoPreciosPega = By.id("mod18_RepAdMantPrecios");
		By subMenuMantenimientoPreciosPega2 = By.id("mod35_RepPlazaMantPrecios");
		By subMenuPlazaMonitoreo = By.id("mod39_RepPlazaMonitoreo");
		By subMenuPuntosEjecucion = By.id("mod8_Descarga_PE");
		By subMenuReglasNegocio = By.id("mod33_RepPlazaReglaNeg");
		By subMenuExcepcionesVigentes = By.id("mod28_RepCatExcepAVencer");
		By subMenuCategoryCumplimiento = By.id("mod29_RepCatCumplimiento");
		By subMenuCategoryCumplimientoPega = By.id("mod32_RepPlazaCumplimiento");
		By subMenuCategoryReglasNegocio = By.id("mod30_RepCatReglaNeg");
		By subMenuCategoryReglasNegocioPega = By.id("mod33_RepPlazaReglaNeg");
		By subMenuAdminArticulosSinRegla = By.id("mod26_RepAdArtSinRegla");
		By subMenuAdminAsignacionDeReglas = By.id("mod27_RepAdAsigRegla");
		By subMenuAdminCambiosMasivos = By.id("mod41_RepAdCambiosMasivos");
		By subMenuAdminCategoriaExcluir = By.id("mod23_RepAdListCat");
		By subMenuAdminCostos = By.id("mod21_RepAdCostos");
		By AdminEstrategiaPrecios = By.id("mod44_RepAdEstrategiaPrecios");
		By subMenuAdminExcepciones = By.id("mod21_RepAdExcep");
		By subMenuAdminExcepcionesPega = By.id("mod31_RepPlazaExcepAVencer");
		By subMenuAdminFolioRetek = By.id("mod37_RepAdFolioRetek");
		By subMenuAdminFolioRetekpega = By.id("mod36_RepPlazaFolioRetek");
		By subMenuAdminMantenimientoPrecios = By.id("mod40_RepAdMonitoreo");
		By subMenuAdminReglasNegocio = By.id("mod20_RepAdReglasNeg");
		By subMenuAdminReglasNegocioPega = By.id("mod33_RepPlazaReglaNeg");
		By subMenuAdminReglasPrecio = By.id("mod25_RepAdReglasPrecio");
		By subMenuAdminReportePlazaPrecios = By.id("mod52_ReportePlazaPrecio");
		By subMenuAdminPuntosEjecucion = By.id("mod24_RepAdPtoEj");
		By subMenuAdminSeguimientoEstrategiaPrecios = By.id("mod45_SeguimientoEstrategiaPrecio");
		By subMenuAdminZonasPrecio = By.id("mod22_RepAdZonas");
		By subMenuPuntoEjecucion = By.id("mod1_PE");
		By subMenuCargaArchivos = By.id("mod5_CA");
		By subMenuRespuestaExcepciones = By.id("mod4_RE");
		
	/// WEB ELEMENTS SIPRE Filtros Articulos de Enfoque///
		By filtroRegion = By.id("divPlazaId_cmb");
		By filtroSuperGrupo = By.id("divisiones_cmb");
		By filtroVariacion = By.id("Variacion_cmb");
		By botonGenerarReporte = By.linkText("Generar Reporte");
		By filtroTipoRegla = By.id("tipoRegla_cmb");
		By filtroPlazaPrecioId = By.id("divPlazaPreciosId_cmb");					
		By filtroZonaPrecio = By.id("zonaPrecio_cmb");
		By filtroCategorias = By.id("categorias_cmb");					 
		By filtroEstatus = By.id("divStatusPE");
		By txtFechaIni = By.id("fechaIni");
		By txtFechaFin = By.id("fechaFin");
		By diaFechaIni = By.xpath("//*[@id='ui-datepicker-div']//child::a[1][contains(text(),'10')]");
		By diaFechaFin = By.xpath("//*[@id='ui-datepicker-div']//child::a[1][contains(text(),'28')]");
		By fitroPlazaMonitoreo = By.id("tipoMonitoreo_cmb");
		By botonGenerarReporteEstructura = By.linkText("Generar Reporte Estructura");
		By botonGenerarReporteSKUS = By.linkText("Generar Reporte SKUS");

	/// WEB ELEMENTS SIPRE Login PEGA///
		By UsuarioPega = By.id("txtUserID");

		By contrasenaPega = By.id("txtPassword");
		By btnLoginPega = By.id("sub");
		By btnMostrarAplicacionesWeb = By.xpath("//*[contains(text(),'Mostrar aplicaciones web')]");
		By lnkSistemaPrecios = By.xpath("//*[contains(text(),'Sistema de Precios')]");
		By headerSistemaPrecios = By.xpath("//*[contains(text(),'Sistema de Precios')]");
		
	/// WEB ELEMENTS Carga de Archivos///
		By lblCargaDeArchivos = By.id("icon_mis_aplicaciones");
		By filtroSeleccionarArchivo = By.id("tiposArchivos_cmb");
		By linkSeleccionarArchivo = By.cssSelector("a.boton:nth-child(1)");
		By txtSeleccionarArchivo = By.id("archivoExcel");
		By btnSubirArchivo = By.linkText("Subir archivo");
		By txtnuevoPuntoEjecucion = By.id("puntoId");
		By txtPuntoEjecucionsolicitado = By.id("txtPE");
		By filtroSelecRespuesta = By.id("selecRespuesta");
		By item = By.xpath("//*[@id='gview_gridItemsPlaza']//tr[@id='1']");
		By chekboxItem = By.id("jqg_gridZonasItem_1");
		By btnAplicar = By.linkText("Aplicar");

		/// WEB ELEMENTS Configuracion///
		By txtarticulosPendientes = By.xpath("//input[@name='articulosPendientes']");
		By botonBuscar = By.linkText("Buscar");
		By txtBuscar = By.id("searchString");
		By filtroRegionConf = By.id("region_cmb");
		By filtroZonaPrecioConf = By.id("divZona");
		By filtroPlazaPrecioConf = By.id("divPlazaPreciosId_cmb");	
		By filtroSuperGrupoConf = By.id("divisiones_cmb");
		By filtroCategoriasConfi = By.id("categorias_cmb");
		By filtroSubCategoriasConfi = By.id("subCategorias_cmb");
		By filtroSegmentosConfi = By.id("segmentos_cmb");
		By filtroSubSegmentosConfi = By.id("subSegmentos_cmb");
		By filtroEstatusConfi = By.id("divStatusPE");
		By botonAceptar = By.linkText("Aceptar");
		By txtPuntoEjecMax = By.id("redMaxId");
		By txtBuscarConf = By.id("searchString");
		By CampoFolio = By.id("gridSolicitudExcepciones");
		By filtroRacional = By.id("racionales_cmb");
		By radio30Dias = By.id("d30_lbl");
		By botonAplicarSolicitudExcepciones = By.cssSelector("a.rojo:nth-child(1)");
		By botonBuscarConfi = By.cssSelector("a.boton:nth-child(4)");
		
		
		// WEB ELEMENTS ULTIMUS SIKULI
		Pattern CajaTXTCargarArchivo = sikuli.PatternImage(pathImages + "txtCargarArchivo.PNG");
		
		
	public pomSipreOXXO(SeleniumUtil u, TestCase testCase ) {
		super();
		this.u = u;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		this.testCase = testCase;
	}
	
	/**
	 * Se cambia de ventana o cierra una.
	 * 
	 * @param numWindow -> Numero de ventana
	 * @param opcion    -> cambiar o cerrar
	 * @param maximizar -> true al cambiar, false al cerrar
	 * @author Esmeralda Garcia Torres
	 */
	public void switchToAnotherWindow(int numWindow, String opcion, Boolean maximizar) {
		try {
			List<String> windowlist = null;
			Set<String> windows = u.getDriver().getWindowHandles();
			windowlist = new ArrayList<String>(windows);
			String currentWindow = u.getDriver().getWindowHandle();
			System.out.println("VENTANAS - " + windowlist.size());
			System.out.println("->" + windowlist);
			System.out.println("-> ACTUAL: " + currentWindow);

			if (!currentWindow.equalsIgnoreCase(windowlist.get(numWindow - 1))) {
				u.getDriver().switchTo().window(windowlist.get(numWindow - 1));
				System.out.println("-> CAMBIAR: " + u.getDriver().getWindowHandle());
			}

			if (maximizar)
				u.getDriver().manage().window().maximize();

			if (opcion.equalsIgnoreCase("cerrar")) {
				System.out.println("-> CERRAR: " + u.getDriver().getWindowHandle());
				u.getDriver().close();
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al " + opcion + " la ventana");
		}
	}
	/**
	 * Metodo para hacer un scrollTo hasta un objeto determido
	 * @param ByElement -> By Element
	 * @author Misraim Hernandez
	 */
	public void scrollToElement(By ByElement) {
        try {
            WebElement element = u.getDriver().findElement(ByElement);
            Coordinates coordinate = ((Locatable) element).getCoordinates();
            coordinate.onPage();
            coordinate.inViewPort();
        } catch (Exception e) {
            try {
                WebElement m = u.getDriver().findElement(ByElement);
                ((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView(true);", m);
            } catch (Exception e2) {
                System.out.println(" -> No se pudo scrollear: " + e);
            }
        }
        
    }
	
	
	/**
	 * Metodo para Seleccionar submenu dentro del menu Reportes
	 * @param subMenu- Valor del submenu a seleccionar dentro del Menu Reportes
	 * @author Misraim Hernandez 
	 */
	public void clickMenuReportes(String subMenu) {
		try {
			u.waitForLoadPage();
			u.waitWebElement(menuReportes, 120, 60);
			u.highLight(menuReportes);
			u.mouseOver(menuReportes);
			u.hardWait(2);
			switch (subMenu.toUpperCase()) {
			case "ARTICULOS DE ENFOQUE":
				u.mouseOverAndClick(subMenuArticulosDeEnfoque);
				break;
			case "CUMPLIMIENTO":
				u.mouseOverAndClick(subMenuCumplimiento);
				break;
			case "ESTRATEGIA DE PRECIOS":
				u.mouseOverAndClick(subMenuEstrategiaPrecios);
				break;
			case "EXCEPCIONES":
				u.mouseOverAndClick(subMenuExcepciones);
				break;
			case "FOLIO RETEK":
				u.mouseOverAndClick(subMenuFolioRetek);
				break;
			case "MANTENIMIENTO DE PRECIOS":
				u.mouseOverAndClick(subMenuMantenimientoPrecios);
				break;
			case "MONITOREO":
				u.mouseOverAndClick(subMenuPlazaMonitoreo);
				break;
			case "PUNTOS DE EJECUCION":
				u.mouseOverAndClick(subMenuPuntosEjecucion);
				break;
			case "REGLAS DE NEGOCIO":
				u.mouseOverAndClick(subMenuReglasNegocio);
				break;
			case "EXCEPCIONES VIGENTES":
				u.mouseOverAndClick(subMenuExcepcionesVigentes);
				break;
			case "CATEGORY CUMPLIMIENTO":
				u.mouseOverAndClick(subMenuCategoryCumplimiento);
				break;
			case "CATEGORY REGLAS DE NEGOCIO":
				u.mouseOverAndClick(subMenuCategoryReglasNegocio);
				break;
			case "ADMIN ARTICULOS SIN REGLA":
				u.mouseOverAndClick(subMenuAdminArticulosSinRegla);
				break;
			case "ASIGNACION DE REGLAS":
				u.mouseOverAndClick(subMenuAdminAsignacionDeReglas);
				break;
			case "ADMIN CAMBIOS MASIVOS":
				u.mouseOverAndClick(subMenuAdminCambiosMasivos);
				break;
			case "ADMIN CATEGORIAS A EXCLUIR":
				u.mouseOverAndClick(subMenuAdminCategoriaExcluir);
				break;
			case "ADMIN COSTOS":
				u.mouseOverAndClick(subMenuAdminCostos);
				break;
			case "ADMIN ESTRATEGIA DE PRECIOS":
				u.mouseOverAndClick(subMenuEstrategiaPrecios);
				break;
			case "ADMIN EXCEPCIONES":
				u.mouseOverAndClick(subMenuAdminExcepciones);
				break;
			case "ADMIN FOLIO RETEK":
				u.mouseOverAndClick(subMenuAdminFolioRetek);
				break;
			case "ADMIN MANTENIMIENTO DE PRECIOS":
				u.mouseOverAndClick(subMenuMantenimientoPrecios);
				break;
			case "ADMIN REGLAS DE NEGOCIO":
				u.mouseOverAndClick(subMenuAdminReglasNegocio);
				break;
			case "ADMIN REGLAS DE PRECIO":
				u.mouseOverAndClick(subMenuAdminReglasPrecio);
				break;
			case "ADMIN REPORTE PLAZA PRECIOS":
				u.mouseOverAndClick(subMenuAdminReportePlazaPrecios);
				break;
			case "ADMIN PUNTOS DE EJECUCION":
				u.mouseOverAndClick(subMenuAdminPuntosEjecucion);
				break;
			case "ADMIN SEGUIMIENTO ESTRATEGIA PRECIOS":
				u.mouseOverAndClick(subMenuAdminSeguimientoEstrategiaPrecios);
				break;
			case "ADMIN ZONAS DE PRECIO":
				u.mouseOverAndClick(subMenuAdminZonasPrecio);
				break;
			default:
				break;
			}

		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al Selecionar menu reportes");
		}
	
	}
	
	
	/**
	 * Metodo para Seleccionar submenu dentro del menu Configuracion
	 * @param subMenu- Valor del submenu a seleccionar dentro del Menu Configuracion
	 * @author Misraim Hernandez 
	 */
	public void clickMenuConfiguracion(String subMenu) {
		try {
			
			switchToAnotherWindow(2, "cambiar", false);
			u.waitWebElement(menuConfiguracion, 120, 60);
			u.highLight(menuConfiguracion);
			u.mouseOver(menuConfiguracion);
			u.hardWait(2);
			switch (subMenu.toUpperCase()) {
			case "CARGA DE ARCHIVOS":
				u.mouseOverAndClick(subMenuCargaArchivos);
				break;
				
			case "PUNTO EJECUCIÓN":
				u.mouseOverAndClick(subMenuPuntoEjecucion);
				break;
				
			case "RESPUESTA DE EXCEPCIONES":
				u.mouseOverAndClick(subMenuRespuestaExcepciones);
				break;
			
			default:
				break;
			}

		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al Selecionar menu Configuracion");
		}
	
	}
	
	/**
	 * Metodo para generar el informe "Articulos de enfoque"
	 * @param region - Valor del Filtro "region"
	 * @param supergrupo - Valor del Filtro "super grupo"
	 * @param variacion - Valor del Filtro "variacion"
	 * @author Misraim Hernandez 
	 */
	public void GenerarReporteArticulosEnfoque(String region, String plazaPrecios, String supergrupo, String variacion) {
		try {
			u.waitWebElement(filtroRegion, 60, 60);
			u.highLight(filtroRegion);
			selectOption("Filtro_Region", region,filtroRegion);
			u.hardWait(1);
			u.highLight(filtroPlazaPrecioId);
			selectOption("Filtro_Plaza_Precios", plazaPrecios,filtroPlazaPrecioId);
			u.hardWait(1);
			u.highLight(filtroSuperGrupo);
			selectOption("Filtro_super_Grupo", supergrupo, filtroSuperGrupo);
			u.hardWait(1);
			u.highLight(filtroVariacion);
			selectOption("filtro_Variacion", variacion, filtroVariacion);
			u.highLight(botonGenerarReporte);
			u.click(botonGenerarReporte);
			
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al Selecionar menu reportes");
		}
	
	}	
	/**
	 * Metodo para seleccionar un valor de una lista web
	 * @param opcion - Nombre para identificar el campo 
	 * @param valor - Valor a seleccionara dentro de la lista
	 * @param elementBy - Identificador de lista web
	 * @author Misraim Hernandez 
	 */
	public void selectOption(String opcion, String valor, By elementBy) {
        try {
            Select select = new Select(u.getDriver().findElement(elementBy));
            select.selectByVisibleText(valor);
            u.hardWait(1);
        } catch (Exception e) {
            System.out.println(" -> " + e);
            throw new AssertionError("Fallo al seleccionar: " + opcion);
        }
    }
	/**
	 * metodo para abrir un archivo de excel
	 * @param pathExcel  En esta variable pasas la ruta y el nombre del archivo con extension
	 * @author Misraim Hernandez 
	 */
	public void AbrirExcelExistente(String pathExcel) {
        try {
        	int i = 0;
        	  while(i < 120) {
        		  if (FileUtil.fileExists(pathExcel))
  		        {
        			u.hardWait(1);  
  		            FileUtil.openFile(pathExcel);
  		            i=121;
  		        }
        	      i++;
        	  }
        		
        } catch (Exception e) {
            System.out.println(" -> " + e);
            throw new AssertionError("No existe el Excel: "+pathExcel);
        }
    }
	/**
	 * Metodo para leer los encabezados de un documento excel
	 * @param pathExcel - Se debe pasar la ruta y el nombre del archivo con su extencion elmplo "C:\Users\74871\Downloads\ReporteExcepciones.xlsx"
	 * @param columnas - Colocar el numero de columnas que se leeran
	 * @author Misraim Hernandez
	 */
	public void leerCeldasExcel(String pathExcel, int columnas) {
        try {
        	
			ExcelUtil hoja = new ExcelUtil(pathExcel);
             hoja.open();
             for(int i=0;i<columnas;i++){
            	 String contenidoCelda = hoja.getCell(0, 0, i);
            	 System.out.println("Contenido: "+contenidoCelda); 
            	 testCase.addTextEvidenceCurrentStep("Campo "+contenidoCelda+ "del reporte");
     		}
        } catch (Exception e) {
            System.out.println(" -> " + e);
            throw new AssertionError("Error al leer encabezados de Excel");
        }
    }
	/**
	 * metodo para hacer login en portal Pega
	 * @param usuario - Usuario para ingresar
	 * @param pass -  contrasena para ingresar 
	 * @author Misraim Hernandez 
	 */
	public void loginPega(String usuario, String pass ) {
        try {
        	u.getDriver().manage().deleteAllCookies();
			u.get(GlobalVariables.urlSipreOXXOPEGA);
			u.hardWait(3);
        	u.waitWebElement(UsuarioPega, 60, 60);
			u.highLight(UsuarioPega);
			u.clear(UsuarioPega);
			u.sendKeys(UsuarioPega, usuario);

			u.highLight(contrasenaPega);
			u.clear(contrasenaPega);
			u.sendKeys(contrasenaPega, pass);
			u.highLight(btnLoginPega);
			u.click(btnLoginPega);
        } catch (Exception e) {
            System.out.println(" -> " + e);
            throw new AssertionError("Error en Funcion Login PEGA");
        }
    }
	/**
	 * Metodo para ingresar al menu Sistema de Precios desde el portar de PEGA
	 * @author Misraim Hernandez 
	 */
	public void selectMenuSistemaPrecios() {
        try {
        	u.waitWebElement(btnMostrarAplicacionesWeb, 60, 60);
        	u.highLight(btnMostrarAplicacionesWeb);
        	u.click(btnMostrarAplicacionesWeb);
        	try {
        		u.waitWebElement(lnkSistemaPrecios, 60, 60);
            	u.highLight(lnkSistemaPrecios);
            	u.click(lnkSistemaPrecios);
            	u.waitWebElement(headerSistemaPrecios, 60, 60);
			} catch (Exception e) {
				System.out.println("-> De nuevo Click");
				u.click(btnMostrarAplicacionesWeb);
				u.waitWebElement(lnkSistemaPrecios, 60, 60);
	        	u.highLight(lnkSistemaPrecios);
	        	u.click(lnkSistemaPrecios);
	        	u.waitWebElement(headerSistemaPrecios, 60, 60);
			}
        
        } catch (Exception e) {
            System.out.println(" -> " + e);
            throw new AssertionError("Fallo al seleccionar: sub menus Sistema de Precios ");
        }
    }
	
	/**
	 * Metodo para generar el reporte de Cumplimiento
	 * @param tipoRegla - Valor del Filtro "Tipo de Regla"
	 * @param region - Valor del Filtro "region"
	 * @param plazaPrecio- Valor del Filtro "Plaza Precios"
	 * @param zonaPrecio - Valor del Filtro "Zona de Precio"
	 * @param supergrupo - Valor del Filtro "Super Grupo"
	 * @param categorias - Valor del Filtro "Categorias"
	 * @author Misraim Hernandez 
	 */
	public void GenerarReporteCumplimiento(String tipoRegla, String region,String plazaPrecio,String zonaPrecio, String supergrupo, String categorias) {
		try {
			u.waitWebElement(filtroTipoRegla, 60, 60);
			u.highLight(filtroTipoRegla);
			selectOption("Filtro_TipoRegla", tipoRegla,filtroTipoRegla);
			u.hardWait(1);
			u.highLight(filtroRegion );
			selectOption("Filtro_Region ", region, filtroRegion );
			u.hardWait(1);
			u.highLight(filtroPlazaPrecioId);
			selectOption("Filtro_Plaza_Precio", plazaPrecio, filtroPlazaPrecioId);
			u.hardWait(20);
			u.highLight(filtroZonaPrecio);
			//selectOption("Filtro_Zona_Precio", zonaPrecio, filtroZonaPrecio);
			u.hardWait(1);
			u.highLight(filtroSuperGrupo);
			selectOption("Filtro_Super_Grupo", supergrupo, filtroSuperGrupo);
			u.hardWait(1);
			u.highLight(filtroCategorias);
			selectOption("Filtro_Super_Grupo", categorias, filtroCategorias);
			u.highLight(botonGenerarReporte);
			u.click(botonGenerarReporte);
			
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al Generar Reporte Cumplimiento");
		}

	}
	/**
	 * Metodo para generar el reporte de Estrategia de Precios
	 * @param region - Valor del Filtro "region"
	 * @param plazaPrecio- Valor del Filtro "Plaza Precios"
	 * @param zonaPrecio - Valor del Filtro "Zona de Precio"
	 * @param supergrupo - Valor del Filtro "Super Grupo"
	 * @author Misraim Hernandez 
	 */
	public void GenerarReporteEstrategiaPrecios(String region,String plazaPrecio,String zonaPrecio, String supergrupo) {
        try {
        	u.waitWebElement(filtroRegion, 60, 60);
			u.highLight(filtroRegion );
			selectOption("Filtro_Region ", region, filtroRegion );
			u.hardWait(1);
			u.highLight(filtroPlazaPrecioId);
			selectOption("Filtro_Plaza_Precio", plazaPrecio, filtroPlazaPrecioId);
			u.hardWait(10);
			u.highLight(filtroZonaPrecio);
			//selectOption("Filtro_Zona_Precio", zonaPrecio, filtroZonaPrecio);
			u.hardWait(1);
			u.highLight(filtroSuperGrupo);
			selectOption("Filtro_Super_Grupo", supergrupo, filtroSuperGrupo);
			u.highLight(botonGenerarReporte);
			u.click(botonGenerarReporte);
        	
        
        } catch (Exception e) {
            System.out.println(" -> " + e);
            throw new AssertionError("Fallo al Generar Reporte Estrategia de Precios");
        }
    }
	
	/**
	 * Metodo para generar el reporte de Excepciones
	 * @param region - Valor del Filtro "Region"
	 * @param plazaPrecio - Valor del Filtro "Plaza Precio"
	 * @param superGrupo - Valor del Filtro "Super Grupo "
	 * @param categorias - Valor del Filtro "Categorias"
	 * @param estatus - Valor del Filtro "Estatus"
	 * @author Misraim Hernandez 
	 */
	public void GenerarReporteExcepciones(String region,String plazaPrecio, String superGrupo, String categorias, String estatus) {
        try {
        	u.waitWebElement(filtroRegion, 60, 60);
			u.highLight(filtroRegion );
			selectOption("Filtro_Region ", region, filtroRegion );
			u.hardWait(1);
			u.highLight(filtroPlazaPrecioId);
			selectOption("Filtro_Plaza_Precio", plazaPrecio, filtroPlazaPrecioId);
			u.hardWait(1);
			u.highLight(filtroSuperGrupo);
			selectOption("Filtro_Super_Grupo", superGrupo, filtroSuperGrupo);
			u.hardWait(1);
			u.highLight(filtroCategorias);
			selectOption("Filtro_Categoria", categorias, filtroCategorias);
			u.hardWait(1);
			u.highLight(filtroEstatus);
			selectOption("Filtro_Estatus", estatus, filtroEstatus);
			u.hardWait(1);
			u.highLight(txtFechaIni);
			u.click(txtFechaIni);
			u.hardWait(1);
			u.highLight(diaFechaIni);
			u.click(diaFechaIni);
			
			u.hardWait(1);
			u.highLight(txtFechaFin);
			u.click(txtFechaFin);
			u.hardWait(1);
			u.highLight(diaFechaFin);
			u.click(diaFechaFin);
			
			u.highLight(botonGenerarReporte);
			u.click(botonGenerarReporte);
        	
        
        } catch (Exception e) {
            System.out.println(" -> " + e);
            throw new AssertionError("Fallo al Generar Reporte Estrategia de Precios");
        }
    }
	/**
	 * Metodo para generar el reporte de Folio Retek
	 * @param region - Valor del Filtro "Region"
	 * @param plazaPrecio - Valor del Filtro "Plaza Precio"
	 * @author Misraim Hernandez 
	 */
	public void GenerarReporteFolioRetek(String region,String plazaPrecio) {
        try {
        	u.waitWebElement(filtroRegion, 60, 60);
			u.highLight(filtroRegion );
			selectOption("Filtro_Region ", region, filtroRegion );
			u.hardWait(10);
			u.highLight(filtroPlazaPrecioId);
			selectOption("Filtro_Plaza_Precio", plazaPrecio, filtroPlazaPrecioId);
		
			u.highLight(botonGenerarReporte);
			u.click(botonGenerarReporte);
        	
        } catch (Exception e) {
            System.out.println(" -> " + e);
            throw new AssertionError("Fallo al Generar Reporte Folio Retek");
        }
    }
	/**
	 * Metodo para generar el reporte de Mantenimiento de Precios
	 * @param region - Valor del Filtro "Region"
	 * @param plazaPrecio - Valor del Filtro "Plaza Precio"
	 * @author Misraim Hernandez 
	 */
	public void GenerarReporteMantenimientoPrecios(String region,String plazaPrecio) {
        try {
        	u.waitWebElement(filtroRegion, 60, 60);
			u.highLight(filtroRegion );
			selectOption("Filtro_Region ", region, filtroRegion );
			u.hardWait(10);
			u.highLight(filtroPlazaPrecioId);
			selectOption("Filtro_Plaza_Precio", plazaPrecio, filtroPlazaPrecioId);
		
			u.highLight(botonGenerarReporte);
			u.click(botonGenerarReporte);
        	
        } catch (Exception e) {
            System.out.println(" -> " + e);
            throw new AssertionError("Fallo al Generar Reporte Mantenimiento de Precios");
        }
    }
	/**
	 * Metodo para generar el reporte de Monitoreo
	 * @param region - Valor del Filtro "Region"
	 * @param plazaPrecio - Valor del Filtro "Plaza Precio"
	 * @param tipoMonitoreo - Valor del Filtro "Tipo Monitoreo"
	 * @author Misraim Hernandez 
	 */
	public void GenerarReportePlazaMonitoreo(String region,String plazaPrecio, String tipoMonitoreo) {
        try {
        	u.waitWebElement(filtroRegion, 60, 60);
			u.highLight(filtroRegion );
			selectOption("Filtro_Region ", region, filtroRegion );
			u.hardWait(10);
			u.highLight(filtroPlazaPrecioId);
			selectOption("Filtro_Plaza_Precio", plazaPrecio, filtroPlazaPrecioId);
			u.hardWait(1);
			u.highLight(fitroPlazaMonitoreo);
			selectOption("Fitro_Plaza_Monitoreo", tipoMonitoreo, fitroPlazaMonitoreo);
		
			u.highLight(botonGenerarReporte);
			u.click(botonGenerarReporte);
        	
        } catch (Exception e) {
            System.out.println(" -> " + e);
            throw new AssertionError("Fallo al Generar Reporte Plaza Monitoreo");
        }
    }	
	
	
	/**
	 * Metodo para generar el reporte de Puntos de Ejecucion
	 * @param region - Valor del Filtro "Region"
	 * @param plazaPrecio - Valor del Filtro "Plaza Precio"
	 * @author Misraim Hernandez 
	 */

	public void GenerarReportePuntosEjecucion(String region,String plazaPrecio) {
        try {
        	u.waitWebElement(filtroRegion, 60, 60);
			u.highLight(filtroRegion );
			selectOption("Filtro_Region ", region, filtroRegion );
			u.hardWait(10);
			u.highLight(filtroPlazaPrecioId);
			selectOption("Filtro_Plaza_Precio", plazaPrecio, filtroPlazaPrecioId);
			u.hardWait(1);
			
			u.highLight(botonGenerarReporte);
			u.click(botonGenerarReporte);
        	
        } catch (Exception e) {
            System.out.println(" -> " + e);

            throw new AssertionError("Fallo al Generar Reporte Puntos de Ejecucion");
        }
    }	

	/**
	 * Metodo para generar el reporte de Reglas de Negocio
	 * @param region - Valor del Filtro "Region"
	 * @param plazaPrecio - Valor del Filtro "Plaza Precio"
	 * @author Misraim Hernandez 
	 */
	public void GenerarReporReglasNegocion(String region,String plazaPrecio) {
        try {
        	u.waitWebElement(filtroRegion, 60, 60);
			u.highLight(filtroRegion );
			selectOption("Filtro_Region ", region, filtroRegion );
			u.hardWait(10);
			u.highLight(filtroPlazaPrecioId);
			selectOption("Filtro_Plaza_Precio", plazaPrecio, filtroPlazaPrecioId);
			u.hardWait(1);
			
			u.highLight(botonGenerarReporte);
			u.click(botonGenerarReporte);
        	
        } catch (Exception e) {
            System.out.println(" -> " + e);

            throw new AssertionError("Fallo al Generar Reporte Reglas de Negocio");
        }
    }	

	
	/**
	 * Metodo para generar el reporte de Excepciones Vigentes
	 * @param region - Valor del Filtro "Region"
	 * @param plazaPrecio - Valor del Filtro "Plaza Precio"
	 * @param superGrupo - Valor del Filtro "Super Grupo "
	 * @param categorias - Valor del Filtro "Categorias"
	 * @author Misraim Hernandez 
	 */
	public void GenerarReporteExcepcionesVigentes(String region,String plazaPrecio, String superGrupo, String categorias) {
        try {
        	
        	u.waitWebElement(filtroRegion, 60, 60);
			u.highLight(filtroRegion );
			selectOption("Filtro_Region ", region, filtroRegion );
			u.hardWait(1);
			u.highLight(filtroPlazaPrecioId);
			selectOption("Filtro_Plaza_Precio", plazaPrecio, filtroPlazaPrecioId);
			u.hardWait(1);
			u.highLight(filtroSuperGrupo);
			selectOption("Filtro_Super_Grupo", superGrupo, filtroSuperGrupo);
			u.hardWait(1);
			u.highLight(filtroCategorias);
			selectOption("Filtro_Categoria", categorias, filtroCategorias);
			
			u.hardWait(1);
			u.highLight(txtFechaIni);
			u.click(txtFechaIni);
			u.hardWait(1);
			u.highLight(diaFechaIni);
			u.click(diaFechaIni);
			
			u.hardWait(1);
			u.highLight(txtFechaFin);
			u.click(txtFechaFin);
			u.hardWait(1);
			u.highLight(diaFechaFin);
			u.click(diaFechaFin);
			
			u.highLight(botonGenerarReporte);
			u.click(botonGenerarReporte);
        
        } catch (Exception e) {
            System.out.println(" -> " + e);

            throw new AssertionError("Fallo al Generar Reporte Excepciones Vigentes");
        }
    }
	
	/**
	 * Metodo para generar el reporte Category Reglas de Negocio
	 * @param region - Valor del Filtro "Region"
	 * @param plazaPrecio - Valor del Filtro "Plaza Precio"
	 * @param superGrupo - Valor del Filtro "Super Grupo "
	 * @param categorias - Valor del Filtro "Categorias"
	 * @author Misraim Hernandez 
	 */
	public void GenerarReporteCategoryReglasNegocio(String region,String plazaPrecio, String superGrupo, String categorias) {
        try {
        	
        	u.waitWebElement(filtroRegion, 60, 60);
			u.highLight(filtroRegion );
			selectOption("Filtro_Region ", region, filtroRegion );
			u.hardWait(1);
			u.highLight(filtroPlazaPrecioId);
			selectOption("Filtro_Plaza_Precio", plazaPrecio, filtroPlazaPrecioId);
			u.hardWait(1);
			/*u.highLight(filtroSuperGrupo);
			selectOption("Filtro_Super_Grupo", superGrupo, filtroSuperGrupo);
			u.hardWait(3);
			u.highLight(filtroCategorias);
			selectOption("Filtro_Categoria", categorias, filtroCategorias);
			*/
			u.highLight(botonGenerarReporte);
			u.click(botonGenerarReporte);
        
        } catch (Exception e) {
            System.out.println(" -> " + e);

            throw new AssertionError("Fallo al Generar Reporte Category Reglas de Negocio");
        }
    }
	
	/**
	 * Metodo para generar el reporte de Admin Articulos SinRegla
	 * @author Misraim Hernandez 
	 */
	public void GenerarReporteAdminArticulosSinRegla() {
        try {
        	
        	u.waitWebElement(botonGenerarReporte, 60, 60);
			u.highLight(botonGenerarReporte);
			u.click(botonGenerarReporte);
        	
        } catch (Exception e) {
            System.out.println(" -> " + e);

            throw new AssertionError("Fallo al Generar Reporte Admin Articulos SinRegla");
        }
    }	
	
	/**
	 * Metodo para generar el reporte de Admin Articulos SinRegla
	 * @author Misraim Hernandez 
	 */
	public void GenerarReporteAdminAsignacionDeReglas() {
        try {
        	
        	u.waitWebElement(botonGenerarReporte, 60, 60);
			u.highLight(botonGenerarReporte);
			u.click(botonGenerarReporte);
        	
        } catch (Exception e) {
            System.out.println(" -> " + e);
            throw new AssertionError("Fallo al Generar Reporte Admin Asignacion De Reglas");
        }
    }
	
	/**
	 * Metodo para generar el reporte Cambios Masivos
	 * @param region - Valor del Filtro "Region"
	 * @param plazaPrecio - Valor del Filtro "Plaza Precio"
	 * @author Misraim Hernandez 
	 */
	public void GenerarReporteAdminCambiosMasivos(String region,String plazaPrecio) {
        try {
        	
        	u.waitWebElement(filtroRegion, 60, 60);
			u.highLight(filtroRegion );
			selectOption("Filtro_Region ", region, filtroRegion );
			u.hardWait(1);
			u.highLight(filtroPlazaPrecioId);
			selectOption("Filtro_Plaza_Precio", plazaPrecio, filtroPlazaPrecioId);		
			u.highLight(botonGenerarReporte);
			u.click(botonGenerarReporte);
        
        } catch (Exception e) {
            System.out.println(" -> " + e);
            throw new AssertionError("Fallo al Generar Reporte Admin Cambios Masivoso");
        }
    }
	
	/**
	 * Metodo para generar el reporte de Excepciones
	 * @param region - Valor del Filtro "Region"
	 * @param plazaPrecio - Valor del Filtro "Plaza Precio"
	 * @param superGrupo - Valor del Filtro "Super Grupo "
	 * @param zonaPrecio - Valor del Filtro "Zona Precio"
	 * @author Misraim Hernandez 
	 */
	public void GenerarReporteAdminCostos(String region,String plazaPrecio, String superGrupo,String zonaPrecio) {
        try {
        	u.waitWebElement(filtroRegion, 60, 60);
			u.highLight(filtroRegion );
			selectOption("Filtro_Region ", region, filtroRegion );
			u.hardWait(1);
			u.highLight(filtroPlazaPrecioId);
			selectOption("Filtro_Plaza_Precio", plazaPrecio, filtroPlazaPrecioId);
			u.hardWait(1);
			u.highLight(filtroSuperGrupo);
			selectOption("Filtro_Super_Grupo", superGrupo, filtroSuperGrupo);
			u.hardWait(1);
			u.highLight(filtroZonaPrecio);
			//selectOption("Filtro_Zona_Precio", zonaPrecio, filtroZonaPrecio);
			
			u.highLight(botonGenerarReporte);
			u.click(botonGenerarReporte);
        	
        
        } catch (Exception e) {
            System.out.println(" -> " + e);
            throw new AssertionError("Fallo al Generar Reporte Estrategia de Precios");
        }
    }
	
	/**
	 * Metodo para generar el reporte de Estrategia de Precios
	 * @param region - Valor del Filtro "region"
	 * @param plazaPrecio- Valor del Filtro "Plaza Precios"
	 * @param zonaPrecio - Valor del Filtro "Zona de Precio"
	 * @param supergrupo - Valor del Filtro "Super Grupo"
	 * @author Misraim Hernandez 
	 */
	public void GenerarReporteAdminEstrategiaPrecios(String region,String plazaPrecio,String zonaPrecio, String supergrupo) {
        try {
        	u.waitWebElement(filtroRegion, 60, 60);
			u.highLight(filtroRegion );
			selectOption("Filtro_Region ", region, filtroRegion );
			u.hardWait(1);
			u.highLight(filtroPlazaPrecioId);
			selectOption("Filtro_Plaza_Precio", plazaPrecio, filtroPlazaPrecioId);
			u.hardWait(10);
			u.highLight(filtroZonaPrecio);
			//selectOption("Filtro_Zona_Precio", zonaPrecio, filtroZonaPrecio);
			u.hardWait(1);
			u.highLight(filtroSuperGrupo);
			selectOption("Filtro_Super_Grupo", supergrupo, filtroSuperGrupo);
			u.highLight(botonGenerarReporte);
			u.click(botonGenerarReporte);
        	
        
        } catch (Exception e) {
            System.out.println(" -> " + e);
            throw new AssertionError("Fallo al Generar Reporte Estrategia de Precios");
        }
    }
	
	/**
	 * Metodo para generar el reporte de Excepciones
	 * @param region - Valor del Filtro "Region"
	 * @param plazaPrecio - Valor del Filtro "Plaza Precio"
	 * @param superGrupo - Valor del Filtro "Super Grupo "
	 * @param categorias - Valor del Filtro "Categorias"
	 * @param estatus - Valor del Filtro "Estatus"
	 * @author Misraim Hernandez 
	 */
	public void GenerarReporteAdminExcepciones(String region,String plazaPrecio, String superGrupo, String categorias, String estatus) {
        try {
        	u.waitWebElement(filtroRegion, 60, 60);
			u.highLight(filtroRegion );
			selectOption("Filtro_Region ", region, filtroRegion );
			u.hardWait(1);
			u.highLight(filtroPlazaPrecioId);
			selectOption("Filtro_Plaza_Precio", plazaPrecio, filtroPlazaPrecioId);
			u.hardWait(1);
			u.highLight(filtroSuperGrupo);
			selectOption("Filtro_Super_Grupo", superGrupo, filtroSuperGrupo);
			u.hardWait(1);
			u.highLight(filtroCategorias);
			selectOption("Filtro_Categoria", categorias, filtroCategorias);
			u.hardWait(1);
			u.highLight(filtroEstatus);
			selectOption("Filtro_Estatus", estatus, filtroEstatus);
			u.hardWait(1);
			u.highLight(txtFechaIni);
			u.click(txtFechaIni);
			u.hardWait(1);
			u.highLight(diaFechaIni);
			u.click(diaFechaIni);
			
			u.hardWait(1);
			u.highLight(txtFechaFin);
			u.click(txtFechaFin);
			u.hardWait(1);
			u.highLight(diaFechaFin);
			u.click(diaFechaFin);
			
			u.highLight(botonGenerarReporte);
			u.click(botonGenerarReporte);
        	
        
        } catch (Exception e) {
            System.out.println(" -> " + e);
            throw new AssertionError("Fallo al Generar Reporte Estrategia de Precios");
        }
    }
	/**
	 * Metodo para generar el reporte de Folio Retek
	 * @param region - Valor del Filtro "Region"
	 * @param plazaPrecio - Valor del Filtro "Plaza Precio"
	 * @author Misraim Hernandez 
	 */
	public void GenerarReporteAdminFolioRetek(String region,String plazaPrecio) {
        try {
        	u.waitWebElement(filtroRegion, 60, 60);
			u.highLight(filtroRegion );
			selectOption("Filtro_Region ", region, filtroRegion );
			u.hardWait(10);
			u.highLight(filtroPlazaPrecioId);
			selectOption("Filtro_Plaza_Precio", plazaPrecio, filtroPlazaPrecioId);
		
			u.highLight(botonGenerarReporte);
			u.click(botonGenerarReporte);
        	
        } catch (Exception e) {
            System.out.println(" -> " + e);
            throw new AssertionError("Fallo al Generar Reporte Folio Retek");
        }
    }
	
	/**
	 * Metodo para generar el reporte de Mantenimiento de Precios
	 * @param region - Valor del Filtro "Region"
	 * @param plazaPrecio - Valor del Filtro "Plaza Precio"
	 * @author Misraim Hernandez 
	 */
	public void GenerarReporteAdminMantenimientoPrecios(String region,String plazaPrecio) {
        try {
        	u.waitWebElement(filtroRegion, 60, 60);
			u.highLight(filtroRegion );
			selectOption("Filtro_Region ", region, filtroRegion );
			u.hardWait(10);
			u.highLight(filtroPlazaPrecioId);
			selectOption("Filtro_Plaza_Precio", plazaPrecio, filtroPlazaPrecioId);
		
			u.highLight(botonGenerarReporte);
			u.click(botonGenerarReporte);
        	
        } catch (Exception e) {
            System.out.println(" -> " + e);
            throw new AssertionError("Fallo al Generar Reporte Mantenimiento de Precios");
        }
    }
	
	
	/**
	 * Metodo para generar el reporte de Reglas de Precio
	 * @author Misraim Hernandez 
	 */
	public void GenerarReporReglasNegocion() {
        try {
        	u.waitWebElement(botonGenerarReporteEstructura, 60, 60);
			u.highLight(botonGenerarReporteEstructura);
			u.click(botonGenerarReporteEstructura);
			u.highLight(botonGenerarReporteSKUS);
			u.click(botonGenerarReporteSKUS);
        	
        } catch (Exception e) {
            System.out.println(" -> " + e);
            throw new AssertionError("Fallo al Generar Reporte Reglas de Precio");
        }
    }	
	
	
	/**
	 * Metodo para generar el Reporte Plaza Precios
	 * @param region - Valor del Filtro "Region"
	 * @param plazaPrecio - Valor del Filtro "Plaza Precio"
	 * @author Misraim Hernandez 
	 */
	public void GenerarReporteAdminPlazaPrecios(String region,String plazaPrecio) {
        try {
        	u.waitWebElement(filtroRegion, 60, 60);
			u.highLight(filtroRegion );
			selectOption("Filtro_Region ", region, filtroRegion );
			u.hardWait(10);
			u.highLight(filtroPlazaPrecioId);
			selectOption("Filtro_Plaza_Precio", plazaPrecio, filtroPlazaPrecioId);
		
			u.highLight(botonGenerarReporte);
			u.click(botonGenerarReporte);
        	
        } catch (Exception e) {
            System.out.println(" -> " + e);
            throw new AssertionError("Fallo al Generar Reporte Plaza Precios");
        }
    }
	
	
	/**
	 * Metodo para generar el reporte de Estrategia de Precios
	 * @param region - Valor del Filtro "region"
	 * @param plazaPrecio- Valor del Filtro "Plaza Precios"
	 * @param zonaPrecio - Valor del Filtro "Zona de Precio"
	 * @param supergrupo - Valor del Filtro "Super Grupo"
	 * @author Misraim Hernandez 
	 */
	public void GenerarReporteAdminSeguimientoEstrategiaPrecios(String region,String plazaPrecio,String zonaPrecio, String supergrupo,String categorias) {
        try {
        	u.waitWebElement(filtroRegion, 60, 60);
			u.highLight(filtroRegion );
			selectOption("Filtro_Region ", region, filtroRegion );
			u.hardWait(1);
			u.highLight(filtroPlazaPrecioId);
			selectOption("Filtro_Plaza_Precio", plazaPrecio, filtroPlazaPrecioId);
			u.hardWait(10);
			u.highLight(filtroZonaPrecio);
			//selectOption("Filtro_Zona_Precio", zonaPrecio, filtroZonaPrecio);
			u.hardWait(1);
			u.highLight(filtroSuperGrupo);
			selectOption("Filtro_Super_Grupo", supergrupo, filtroSuperGrupo);
			u.hardWait(1);
			u.highLight(filtroCategorias);
			selectOption("Filtro_Categoria", categorias, filtroCategorias);
			
			u.highLight(botonGenerarReporte);
			u.click(botonGenerarReporte);
   
        } catch (Exception e) {
            System.out.println(" -> " + e);
            throw new AssertionError("Fallo al Generar Reporte Seguimiento Estrategia Precios");
        }
    }
	
	/**
	 * Metodo para generar el reporte de Zonas de Precio
	 * @param region - Valor del Filtro "Region"
	 * @param plazaPrecio - Valor del Filtro "Plaza Precio"
	 * @author Misraim Hernandez 
	 */
	public void GenerarReporteZonasPrecio(String region,String plazaPrecio) {
        try {
        	u.waitWebElement(filtroRegion, 60, 60);
			u.highLight(filtroRegion );
			selectOption("Filtro_Region ", region, filtroRegion );
			u.hardWait(10);
			u.highLight(filtroPlazaPrecioId);
			selectOption("Filtro_Plaza_Precio", plazaPrecio, filtroPlazaPrecioId);
		
			u.highLight(botonGenerarReporte);
			u.click(botonGenerarReporte);
        	
        } catch (Exception e) {
            System.out.println(" -> " + e);
            throw new AssertionError("Fallo al Generar Reporte Zonas de Precio");
        }
    }
	
	/**
	 * Funcion para seleccionar un archivo para carga de archivos
	 * @param tipoArchivo - Valor del Filtro "Tipo de Archivo"
	 * @author Misraim Hernandez 
	 */
	public void SeleccionarArchivo(String tipoArchivo,String nomArchivo) {
		try {
			
			final String BASE_PATH = System.getProperty("user.dir");
			String pathFileX = BASE_PATH + "\\Config\\Input\\Files\\SipreOXXO\\REPORTES CARGA A SIPRE\\"+nomArchivo;
			
			u.waitWebElement(lblCargaDeArchivos, 120, 60);
			u.highLight(lblCargaDeArchivos);
			u.highLight(filtroSeleccionarArchivo);
			selectOption("filtro_Seleccionar_Archivo ",tipoArchivo, filtroSeleccionarArchivo );
			u.highLight(linkSeleccionarArchivo);
			u.mouseOverAndClick(linkSeleccionarArchivo);
			u.hardWait(5);
			sikuli.screen.click(CajaTXTCargarArchivo);
			sikuli.screen.type(CajaTXTCargarArchivo,pathFileX);
			sikuli.screen.type(Key.ENTER);
			u.hardWait(5);
			testCase.addScreenShotCurrentStep("Seleccionar Archivo");
			u.highLight(btnSubirArchivo);
			u.click(btnSubirArchivo);
			loadingUltimusCargaArchivo();
			testCase.addScreenShotCurrentStep("Subir Archivo");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo en funcion Seleccionar Archivo");
		}
	
	}
	
	/**
	 * Funcion para Validar que el contador de Puntos de Ejecucion Pendientes sea mayor a 0
	 *  @author Misraim Hernandez 
	 */
	public void ValidarContadorPuntosEjecucionPendientes() {
        try {
        	u.waitWebElement(txtarticulosPendientes, 60, 60);
			u.highLight(txtarticulosPendientes);
			String typeValue= u.getDriver().findElement(txtarticulosPendientes).getAttribute("value");
			int puntosEjecucion = Integer.valueOf(typeValue);
			System.out.println(" -> " + puntosEjecucion);
			if (puntosEjecucion > 0) {
				System.out.println("Puntos de Ejecucion Pendientes mayor a 0");
			} else {
				System.out.println("Puntos de Ejecucion Pendientes igual a 0");
				testCase.failStep();
			}
			
        } catch (Exception e) {
            System.out.println(" -> " + e);
            throw new AssertionError("Fallo en funcion Validar que el contador de Puntos de Ejecucion Pendientes");
        }
    }	
	
	/**
	 * Metodo para seleccionara los filtrsio de Puntos de Ejecucion
	 * @param plazaPrecio - Valor del Filtro "Plaza Precio"
	 * @param superGrupo - Valor del Filtro "Super Grupo"
	 * @param categorias - Valor del Filtro "Categorias"
	 * @param estatus - Valor del Filtro "Estatus"
	 * @param zonaPrecio - Valor del Filtro "Zona Precio"
	 * @param buscar - Valor del campo de texto "Buscar"
	 * @author Misraim Hernandez 
	 */
	public void filtrosPuntoEjecucion(String plazaPrecio, String superGrupo, String categorias, String estatus,String zonaPrecio, String buscar) {
        try {
        	u.waitWebElement(filtroPlazaPrecioConf, 60, 60);
			u.highLight(filtroPlazaPrecioConf);
			selectOption("Filtro_Plaza_Precio", plazaPrecio, filtroPlazaPrecioConf);
			u.hardWait(1);
			u.highLight(filtroEstatusConfi);
			selectOption("Filtro_Estatus", estatus, filtroEstatusConfi);
			u.hardWait(10);
			u.highLight(filtroZonaPrecioConf);
			selectOption("Filtro_Zona_Precio", zonaPrecio, filtroZonaPrecioConf);
			u.hardWait(1);
			u.highLight(filtroSuperGrupoConf);
			selectOption("Filtro_Super_Grupo", superGrupo, filtroSuperGrupoConf);
			u.hardWait(1);
			u.highLight(filtroCategoriasConfi);
			selectOption("Filtro_Categoria", categorias, filtroCategoriasConfi);
			u.hardWait(1);
			u.highLight(txtBuscar);
			u.sendKeys(txtBuscar, buscar);
			testCase.addScreenShotCurrentStep("Seleccionar filtros");
			u.highLight(botonBuscar);
			u.click(botonBuscar);
        	
        
        } catch (Exception e) {
            System.out.println(" -> " + e);
            throw new AssertionError("Fallo al Generar Reporte Estrategia de Precios");
        }
    }
	
	
	/**
	 * Metodo para Aplicar Cambio de Precio de Punto de Ejecuion
	 * @param racional - Valor del Filtro "Racional"
	 * @param nuevoPuntoEjecuccion - Valor del campo de texto "Nuevo Punto de Ejecuccion"
	 * @author Misraim Hernandez 
	 */
	public void AplicarCambioPrecio(String racional,String nuevoPuntoEjecuccion) {
        try {
        	u.waitWebElement(txtarticulosPendientes, 60, 60);
        	u.scroll();
        	u.hardWait(5);
        	u.highLight(item);
			u.mouseOverAndClick(item);
        	u.hardWait(5);
			u.mouseOverAndClick(chekboxItem);
			u.hardWait(5);
			u.sendKeys(txtnuevoPuntoEjecucion, nuevoPuntoEjecuccion);
			u.hardWait(5);
			testCase.addScreenShotCurrentStep("Aplicar");
			u.highLight(btnAplicar);
			u.scrollToElement(btnAplicar);
			u.click(btnAplicar);
			testCase.addScreenShotCurrentStep("Aceptar");
			u.highLight(botonAceptar);
			u.click(botonAceptar);
			u.highLight(CampoFolio);
			u.scrollToElement(CampoFolio);
			u.highLight(filtroRacional);
			u.hardWait(3);
			selectOption("Filtro_Racional", racional,filtroRacional);
			u.hardWait(3);
			u.highLight(radio30Dias );
			u.click(radio30Dias );
			
			testCase.addScreenShotCurrentStep("Aplicar Racional");
			u.highLight(botonAplicarSolicitudExcepciones );
			u.click(botonAplicarSolicitudExcepciones );
			
        } catch (Exception e) {
            System.out.println(" -> " + e);
            throw new AssertionError("Fallo en funcion Validar que el contador de Puntos de Ejecucion Pendientes");
        }
    }	
	
	
	
	/**
	 * Espera a que desaparezca  modal de procesando .
	 * @author Misraim Hernandez 
	 */
	public void loadingUltimusCargaArchivo() {
		try {
			u.hardWait(2);
			while (u.getDriver().findElement(loadiCargaArchivo).isDisplayed()) {
				System.out.println("loading...");
				u.hardWait(2);
			}
		} catch (Exception e) {

		}
	}
	
	/**
	 * Obtiene la fecha actual del sistema
	 * @return Regresa la fecha del sistema 
	 * @author Misraim Hernandez 
	 */
	public String getDate() {
		SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar = Calendar.getInstance();
		Date dateObj = calendar.getTime();
		String formattedDate = dtf .format(dateObj);
		System.out.println(formattedDate);
		return formattedDate;
		
	}
	/**
	 * Metodo para seleccionara los filtrsio de Respuesta de Excepciones
	 * @param region - Valor del Filtro "Region"
	 * @param superGrupo - Valor del Filtro "super Grupo"
	 * @param categorias - Valor del Filtro "Categorias"
	 * @param buscar - Valor del campo de texto "Buscar"
	 * @author Misraim Hernandez 
	 */
	public void filtrosRespuestaExcepciones(String region, String superGrupo, String categorias, String buscar) {
        try {
        	u.waitWebElement(filtroRegionConf, 60, 60);
			u.highLight(filtroRegionConf);
			selectOption("Region", region, filtroRegionConf);
			u.hardWait(1);
			u.highLight(filtroPlazaPrecioConf);
			
			u.highLight(filtroSuperGrupoConf);
			selectOption("SuperGrupo", superGrupo, filtroSuperGrupoConf);
			u.hardWait(1);
			u.highLight(filtroCategoriasConfi);
			selectOption("Filtro_Categoria", categorias, filtroCategoriasConfi);
			u.hardWait(1);
			u.highLight(txtBuscarConf);
			u.sendKeys(txtBuscarConf, buscar);
			testCase.addScreenShotCurrentStep("Seleccionar filtros");
			u.highLight(botonBuscarConfi);
			u.click(botonBuscarConfi);
        	
        
        } catch (Exception e) {
            System.out.println(" -> " + e);
            throw new AssertionError("Fallo en funcion filtrosRespuestaExcepciones");
        }
    }
	/**
	 * Metodo para Aplicar Cambio de Precio de RespuestaExcepciones
	 * @param respuesta - Valor del Filtro "Respuesta"
	 * @param nuevoPuntoEjecuccion - Valor del campo de texto "Buscar"
	 * @author Misraim Hernandez 
	 */
	public void AplicarCambioPrecioRespuestaExcepciones(String respuesta,String nuevoPuntoEjecuccion) {
        try {
        
        	u.highLight(item);
			u.mouseOverAndClick(item);
        	u.hardWait(5);
        	u.scroll();
        	u.highLight(txtPuntoEjecucionsolicitado);
			u.sendKeys(txtPuntoEjecucionsolicitado, nuevoPuntoEjecuccion);
			u.hardWait(5);
			u.highLight(filtroSelecRespuesta);
			selectOption("selecRespuesta", respuesta, filtroSelecRespuesta);
			testCase.addScreenShotCurrentStep("Aplicar");
			u.highLight(btnAplicar);
			u.scrollToElement(btnAplicar);
			testCase.addScreenShotCurrentStep("Aceptar");
			
			
        } catch (Exception e) {
            System.out.println(" -> " + e);
            throw new AssertionError("Fallo en funcion Validar que el contador de Puntos de Ejecucion Pendientes");
        }
    }	
	
	
	/**
	 * Metodo para Seleccionar submenu dentro del menu Reportes
	 * @param subMenu- Valor del submenu a seleccionar dentro del Menu Reportes
	 * @author Misraim Hernandez 
	 */
	public void clickMenuReportesPega(String subMenu) {
		try {
			switchToAnotherWindow(2, "cambiar", false);
			u.waitWebElement(menuReportes, 120, 60);
			u.highLight(menuReportes);
			u.mouseOver(menuReportes);
			u.hardWait(2);
			switch (subMenu.toUpperCase()) {
			case "ARTICULOS DE ENFOQUE":
				u.mouseOverAndClick(subMenuArticulosDeEnfoque);
				break;
			case "CUMPLIMIENTO":
				u.mouseOverAndClick(subMenuCumplimientoPega);
				break;
			case "ESTRATEGIA DE PRECIOS":
				u.mouseOverAndClick(subMenuEstrategiaPrecios);
				break;
			case "EXCEPCIONES":
				u.mouseOverAndClick(subMenuExcepcionesPega);
				break;
			case "FOLIO RETEK":
				u.mouseOverAndClick(subMenuFolioRetekPega);
				break;
			case "MANTENIMIENTO DE PRECIOS":
				u.mouseOverAndClick(subMenuMantenimientoPreciosPega2);
				break;
			case "MONITOREO":
				u.mouseOverAndClick(subMenuPlazaMonitoreo);
				break;
			case "PUNTOS DE EJECUCIÓN":
				u.mouseOverAndClick(subMenuPuntosEjecucion);
				break;
			case "REGLAS DE NEGOCIO":
				u.mouseOverAndClick(subMenuReglasNegocio);
				break;
			case "EXCEPCIONES VIGENTES":
				u.mouseOverAndClick(subMenuExcepcionesVigentes);
				break;
			case "CATEGORY CUMPLIMIENTO":
				u.mouseOverAndClick(subMenuCategoryCumplimiento);
				break;
			case "CATEGORY REGLAS DE NEGOCIO":
				u.mouseOverAndClick(subMenuCategoryReglasNegocio);
				break;
			case "ADMIN ARTICULOS SIN REGLA":
				u.mouseOverAndClick(subMenuAdminArticulosSinRegla);
				break;
			case "ASIGNACION DE REGLAS":
				u.mouseOverAndClick(subMenuAdminAsignacionDeReglas);
				break;
			case "ADMIN CAMBIOS MASIVOS":
				u.mouseOverAndClick(subMenuAdminCambiosMasivos);
				break;
			case "ADMIN CATEGORIAS A EXCLUIR":
				u.mouseOverAndClick(subMenuAdminCategoriaExcluir);
				break;
			case "ADMIN COSTOS":
				u.mouseOverAndClick(subMenuAdminCostos);
				break;
			case "ADMIN ESTRATEGIA DE PRECIOS":
				u.mouseOverAndClick(subMenuEstrategiaPrecios);
				break;
			case "ADMIN EXCEPCIONES":
				u.mouseOverAndClick(subMenuAdminExcepciones);
				break;
			case "ADMIN FOLIO RETEK":
				u.mouseOverAndClick(subMenuAdminFolioRetek);
				break;
			case "ADMIN MANTENIMIENTO DE PRECIOS":
				u.mouseOverAndClick(subMenuMantenimientoPrecios);
				break;
			case "ADMIN REGLAS DE NEGOCIO":
				u.mouseOverAndClick(subMenuAdminReglasNegocio);
				break;
			case "ADMIN REGLAS DE PRECIO":
				u.mouseOverAndClick(subMenuAdminReglasPrecio);
				break;
			case "ADMIN REPORTE PLAZA PRECIOS":
				u.mouseOverAndClick(subMenuAdminReportePlazaPrecios);
				break;
			case "ADMIN PUNTOS DE EJECUCIÓN":
				u.mouseOverAndClick(subMenuAdminPuntosEjecucion);
				break;
			case "ADMIN SEGUIMIENTO ESTRATEGIA PRECIOS":
				u.mouseOverAndClick(subMenuAdminSeguimientoEstrategiaPrecios);
				break;
			case "ADMIN ZONAS DE PRECIO":
				u.mouseOverAndClick(subMenuAdminZonasPrecio);
				break;
			default:
				break;
			}

		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al Selecionar menu reportes");
		}
	
	}
	
	/**
	 * Metodo para Seleccionar submenu dentro del menu Configuracion
	 * @param subMenu- Valor del submenu a seleccionar dentro del Menu Configuracion
	 * @author Misraim Hernandez 
	 */
	public void clickMenuConfiguracionPega(String subMenu) {
		try {
			
			switchToAnotherWindow(2, "cambiar", false);
			u.waitWebElement(menuConfiguracion, 120, 60);
			u.highLight(menuConfiguracion);
			u.mouseOver(menuConfiguracion);
			u.hardWait(2);
			switch (subMenu.toUpperCase()) {
			case "CARGA DE ARCHIVOS":
				u.mouseOverAndClick(subMenuCargaArchivos);
				break;
				
			case "PUNTO EJECUCIÓN":
				u.mouseOverAndClick(subMenuPuntoEjecucion);
				break;
				
			case "RESPUESTA DE EXCEPCIONES":
				u.mouseOverAndClick(subMenuRespuestaExcepciones);
				break;
			
			default:
				break;
			}

		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al Selecionar menu Configuracion");
		}
	
	}

	
}