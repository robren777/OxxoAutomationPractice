package WorkFlows.util.Planchado;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;

import modelo.TestCase;
import utils.ExcelUtil;
import utils.FileUtil;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;


public class pomPlanchado {
	SeleniumUtil u;
	TestCase testCase;
	private WebDriverWait wait;
	SikuliUtil sikuli = new SikuliUtil();
	String pathImages = sikuli.PatternPath("WorkFlows"+File.separator+"SipreOXXO");
	
	public pomPlanchado(SeleniumUtil u, TestCase testCase ) {
		super();
		this.u = u;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		this.testCase = testCase;
		
	}
	
	//Objetos 
	/// WEB ELEMENTS PLANCHADO ///
		By btnMenu = By.id("button-template-urlMenu");
		By subMenuPlanchados = By.cssSelector("li.ng-scope:nth-child(2) > a:nth-child(1)");
		By subMenuPaquetes = By.cssSelector("li.ng-scope:nth-child(3) > a:nth-child(1)");
		By subMenuHistoriales = By.cssSelector("li.ng-scope:nth-child(4) > a:nth-child(1)");
		By txtNombre = By.id("txtNombre");
		By txtDescripcion = By.id("txtDescripcion");
		By btnBuscra = By.cssSelector("input.btn:nth-child(1)");
		By btnOK= By.name("dismisSeleneiumOK");
		By panelProcess = By.id("pnlProgress");
		By btnNuevo = By.cssSelector("div.col-lg-11:nth-child(3) > input:nth-child(1)");
		By btnInsertar = By.id("btnAceptarPlanchadoModal");
		By txtNombreNuevoPlanchado = By.cssSelector("input.ng-valid-maxlength");
		By txtDescripcionNuevoPlanchado = By.cssSelector("textarea.largoTexbox2");
		By filtroPlazaNuevoPlanchado = By.cssSelector("div.modal:nth-child(13) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(6) > div:nth-child(2) > select:nth-child(1)");
		By filtroActivoNuevoPlanchado = By.cssSelector("div.modal:nth-child(13) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(8) > div:nth-child(2) > select:nth-child(1)");
		By lblParametros = By.cssSelector("div.mostrarCustom:nth-child(1) > div:nth-child(1) > div:nth-child(1) > span:nth-child(1) > span:nth-child(1)");
		By lblTiendas = By.xpath("//*[contains(text(),'Tiendas')]");
		By lblLegos = By.xpath("//*[contains(text(),'Legos')]");
		By lblPaquetes = By.xpath("//span[contains(text(),'Paquetes')]");
		By btnActualizar = By.xpath("//*[@id='btnModalParametros']");
		By btnCancelar = By.xpath("//*[@type='button'and @ng-show='modal.Planchado.Parametros.showCancelar']");
		By btnSalirPantallaParametrosHistorial = By.xpath("//input[@class='btnSubGrupo botonAzul'and @ng-click='modal.Historiales.Parametros.visible = false']");
		By lblProgramacion = By.cssSelector("div.modal:nth-child(19) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > span:nth-child(1) > span:nth-child(1)");
		By lblFechaProgramacion = By.xpath("//th[contains(text(),'Fecha')]");
		By lblTipoProgramacion = By.xpath("//th[contains(text(),'Tipo')]");
		By lblAccionProgramacion = By.xpath("//th[contains(text(),'Accion')]");
		By lblFechaInicioProgramacion = By.xpath("//th[contains(text(),'Inicio')]");
		By lblFechaFinProgramacion = By.xpath("//th[contains(text(),'Fin')]");
		By lblMensajeProgramacion = By.xpath("//th[contains(text(),'Mensaje')]");
		By lblPProgramacion = By.xpath("//th[contains(text(),'P.')]");
		By lblDProgramacion = By.xpath("//th[contains(text(),'D.')]");
		By lblEProgramacion = By.xpath("//th[contains(text(),'E.')]");
		By IconoDetalleParametros = By.cssSelector("tr.ng-scope:nth-child(1) > td:nth-child(9) > img:nth-child(1)");
		By IconoDetallePlanchado = By.cssSelector("tr.ng-scope:nth-child(1) > td:nth-child(10) > img:nth-child(1)");
		By btnActualizarProgramacion = By.xpath("//*[@type='button'and @ng-show='modal.Planchado.Programacion.showAceptar']");
		By btnCancelarProgramacion = By.xpath("//*[@type='button'and @ng-show='modal.Planchado.Programacion.showCancelar']");
		By lblLoc = By.xpath("//th[contains(text(),'Loc')]");
		By lblNoTienda = By.xpath("//th[contains(text(),'# Tienda')]");
		By lblNombreTienda = By.xpath("//th[contains(text(),'Nombre Tienda')]");
		By lblNoArticulo = By.xpath("//th[contains(text(),'# Art')]");
		By lblDescripcionArticulo = By.xpath("//th[contains(text(),'Descripci')]");
		By lblSuperFrupo = By.xpath("//th[contains(text(),'Super grupo')]");
		By lblNoFA = By.xpath("//th[contains(text(),'# FA')]");
		By lblNombreFA = By.xpath("//th[contains(text(),'Nombre FA')]");
		By lblTipoFA = By.xpath("//th[contains(text(),'Tipo FA')]");
		By lblEstatusActual = By.xpath("//th[contains(text(),'Estatus Actual')]");
		By lblEstatusPlanchar = By.xpath("//th[contains(text(),'Estatus Planchar')]");
		By lblLego = By.xpath("//th[contains(text(),'Lego')]");
		By lblPaqueteExclusion = By.xpath("//th[contains(text(),'Paquete Exclusi')]");
		By btnExcel = By.cssSelector("input.btnThin:nth-child(1)");
		By btnTexto = By.cssSelector("input.btnThin:nth-child(2)");
		By btnSalir = By.cssSelector("input.btnSubGrupo:nth-child(3)");
		By loadingModal = By.cssSelector("div#loadingModal");
		
		
		/// WEB ELEMENTS ULTIMUS SIKULI
		Pattern imgFlechaVerde = sikuli.PatternImage(pathImages + "FlechaVerde.PNG");
		Pattern imgPreviewsHojaBlanco = sikuli.PatternImage(pathImages + "PreviewsHojaBlanco.PNG");
		Pattern imgEditarRegistroPlanchado = sikuli.PatternImage(pathImages + "Editar.PNG");
		Pattern imgBotonImageControlParametros = sikuli.PatternImage(pathImages + "ImageControlParametros.PNG");
		Pattern imgBotonImageControlParametrosHistorial = sikuli.PatternImage(pathImages + "ImageControlParametrosHistorial.PNG");
		Pattern imgBotonControlColumnaExcluidos = sikuli.PatternImage(pathImages + "imagenControlColumnaExcluidos.PNG");
		Pattern imgBotonImageControlProgramacion = sikuli.PatternImage(pathImages + "Programacion.PNG");
		Pattern CajaTXTCargarArchivo = sikuli.PatternImage(pathImages + "txtCargarArchivo.PNG");
		
		/// WEB ELEMENTS Historico ///
		By lblSubirCSV = By.cssSelector("div.modal-header:nth-child(2)");
		By lblArchivo = By.cssSelector("div.modal-body:nth-child(3) > div:nth-child(2) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > span:nth-child(1)");
		By btnSeleccionarArchivo = By.cssSelector("#fu_CSV_FiltrosPlaza");
		By btnSubir = By.cssSelector("div.modal-footer:nth-child(4) > div:nth-child(1) > div:nth-child(3) > input:nth-child(1)");
		By btnCerrar = By.cssSelector("div.modal-footer:nth-child(4) > div:nth-child(1) > div:nth-child(3) > input:nth-child(2)");
		By lblHistorialPlanchado = By.xpath("//*[@class='TituloVentana'and contains(text(),'Historial del planchado')]");
		By btnBuscraHistoriales = By.cssSelector(".btn-group-lg");
		By btnExcelHistoriales = By.cssSelector("div.col-lg-6:nth-child(2) > div:nth-child(1) > input:nth-child(2)");
		By btnLimpiarHistoriales = By.cssSelector("div.col-lg-6:nth-child(2) > div:nth-child(1) > input:nth-child(3)");
		By filtroPlaza = By.cssSelector("div.redondeado:nth-child(2) > div:nth-child(2) > div:nth-child(2) > select:nth-child(1)");
		By filtroTienda = By.cssSelector("div.tablaGris:nth-child(4) > div:nth-child(2) > select:nth-child(1)");
		By filtroNombre = By.cssSelector("#txtNombre");
		By filtroDescripcion = By.cssSelector("#txtDescripcion");
		By filtroFechaInicio = By.cssSelector("#txt1");
		By filtroFechaFin = By.cssSelector("#txt2");
		By lblExcluidosGridHistorial = By.xpath("//*[@class='ui-grid-header-cell-label ng-binding'and contains(text(),'Excluidos')]");
		By lblParametrosGridHistorial = By.xpath("//*[@class='ui-grid-header-cell-label ng-binding'and contains(text(),'Par')]");
		By lblPlazaGridHistorial = By.xpath("//*[@class='ui-grid-header-cell-label ng-binding'and contains(text(),'Plaza')]");
		By lblFechaGridHistorial = By.xpath("//*[@class='ui-grid-header-cell-label ng-binding'and contains(text(),'Fecha')]");
		By lblPlanchadoGridHistorial = By.xpath("//*[@class='ui-grid-header-cell-label ng-binding'and contains(text(),'Planchado')]");
		By lblTiendaGridHistorial = By.xpath("//*[@class='ui-grid-header-cell-label ng-binding'and contains(text(),'Tienda')]");
		By lblLigar1FAGridHistorial = By.xpath("//*[@class='ui-grid-header-cell-label ng-binding'and contains(text(),'Ligar: 1 F A')]");
		By lblLigarMas1FAGridHistorial = By.xpath("//*[@class='ui-grid-header-cell-label ng-binding'and contains(text(),'Ligar: +1 F A')]");
		By lblLigarMas1FANoEstandarGridHistorial = By.xpath("//*[@class='ui-grid-header-cell-label ng-binding'and contains(text(),'Ligar: +1 F A, No Est')]");
		By lblLigarNoEstandarGridHistorial = By.xpath("//*[@class='ui-grid-header-cell-label ng-binding'and contains(text(),'Ligar: No Est')]");
		By lblActivar1FAGridHistorial = By.xpath("//*[@class='ui-grid-header-cell-label ng-binding'and contains(text(),'Activar: 1 F A')]");
		By lblActivarMas1FAGridHistorial = By.xpath("//*[@class='ui-grid-header-cell-label ng-binding'and contains(text(),'Activar: +1 F A')]");
		By lblActivarMas1FANoEstandarGridHistorial = By.xpath("//*[@class='ui-grid-header-cell-label ng-binding'and contains(text(),'Activar: +1 F A, No Est')]");
		By lblActivarNoEstandarGridHistorial = By.xpath("//*[@class='ui-grid-header-cell-label ng-binding'and contains(text(),'Activar: No Est')]");
		By lblInactivarActivoRMSGridHistorial = By.xpath("//*[@class='ui-grid-header-cell-label ng-binding'and contains(text(),'Inactivar: Activo R M S')]");
		By lblInactivarNoExisteEnLegoGridHistorial = By.xpath("//*[@class='ui-grid-header-cell-label ng-binding'and contains(text(),'Inactivar: No Existe En Lego')]");
		By lblMensajeGridHistorial = By.xpath("//*[@class='ui-grid-header-cell-label ng-binding'and contains(text(),'Mensaje')]");
		
		
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
	            throw new AssertionError("Fall� al seleccionar: " + opcion);
	        }
	    }
		
		
		/**
		 * Espera a que desaparezca  modal de procesando .
		 * @author Misraim Hernandez 
		 */
		public void loadingUltimus() {
			try {
				u.hardWait(2);
				while (u.getDriver().findElement(panelProcess).isDisplayed()) {
					System.out.println("Procesando...");
					u.hardWait(2);
				}
			} catch (Exception e) {

			}
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
		 * Metodo para ingresar en el menu de planchado
		 * @author Misraim Hernandez 
		 */
		public void selectMenuPlanchados() {
	        try {
	        	u.waitWebElement(btnMenu, 60, 60);
	        	u.highLight(btnMenu);
	        	u.hardWait(2);
	        	u.click(btnMenu);
	        	u.waitWebElement(subMenuPlanchados, 60, 60);
	        	u.highLight(subMenuPlanchados);
	        	u.hardWait(2);
	        	u.mouseOverAndClick(subMenuPlanchados);
	        	loadingUltimus();
	        	u.waitWebElement(filtroPlaza, 60, 60);
	        	u.highLight(filtroPlaza);
	        } catch (Exception e) {
	            System.out.println(" -> " + e);
	            throw new AssertionError("Fallo al seleccionar: sub menu Planchados");
	        }
	    }
		/**
		 * Metodo para hacer in scroll hasta un objeto determinado
		 * @param ByElement - selector para hacer el scroll
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
		 * Metodo para ingresar en el menu de Paquetes
		 * @author Misraim Hernandez 
		 */
		public void selectMenuPaquetes() {
	        try {
	        	u.waitWebElement(btnMenu, 60, 60);
	        	u.highLight(btnMenu);
	        	u.hardWait(2);
	        	u.click(btnMenu);
	        	u.waitWebElement(subMenuPaquetes, 60, 60);
	        	u.highLight(subMenuPaquetes);
	        	u.hardWait(2);
	        	u.mouseOverAndClick(subMenuPaquetes);
	        	loadingUltimus();
	        	u.waitWebElement(filtroPlaza, 60, 60);
	        	u.highLight(filtroPlaza);
	        } catch (Exception e) {
	            System.out.println(" -> " + e);
	            throw new AssertionError("Fallo al seleccionar: sub menu Planchados");
	        }
	    }
		
		/**
		 * Metodo para la busqueda del planchado
		 * @param plaza - Valor del Filtro "Plaza"
		 * @param nombre - Valor del Filtro "Nombre"
		 * @param descripcion -  Valor del Filtro "Descripcion" 
		 * @author Misraim Hernandez
		 */
		public void buscarSeleccionarPlanchado(String plaza,String nombre, String descripcion ) {
	        try {
	        	loadingUltimus();
	        	u.highLight(filtroPlaza);
	        	selectOption("Filtro_Plaza",plaza, filtroPlaza);
	        	u.highLight(txtNombre);
	        	u.sendKeys(txtNombre, nombre);
	        	u.highLight(txtDescripcion);
	        	u.sendKeys(txtDescripcion, descripcion);
	        	u.highLight(btnBuscra);
	        	u.click(btnBuscra);
	        } catch (Exception e) {
	            System.out.println(" -> " + e);
	            throw new AssertionError("Fallo en funcion BuscarSeleccionarPlanchado");
	        }
	    }
		/**
		 * Metodo para hacer clic sobre icono (Flecha verde)
		 * @author Misraim Hernandez
		 */
		public void seleccionarRegistroGrid() {
	        try {
	        	loadingUltimus();
	        	u.waitWebElement(filtroPlaza, 60, 60);
	        	u.highLight(filtroPlaza);
	        	testCase.addScreenShotCurrentStep("Flecha Verde");
	        	sikuli.screen.click(imgFlechaVerde);
			
	        } catch (Exception e) {
	            System.out.println(" -> " + e);
	            throw new AssertionError("Fallo en funcion Seleccionar un registro del grid");
	        }
	    }
	
		/**
		 * Metodo para hacer clic en el boton "OK" del mensaje de confirmacion
		 * @author Misraim Hernandez
		 */
		public void clickBotonConfirmar() {
	        try {
	        	loadingUltimus();
				u.waitWebElement(btnOK, 120, 60);
				u.highLight(btnOK);
				testCase.addScreenShotCurrentStep("Confirmacion");
				u.click(btnOK);

	        } catch (Exception e) {
	            System.out.println(" -> " + e);
	            throw new AssertionError("Fallo en funcion click Boton Confirmar");
	        }
	    }
		
		
		/**
		 * Metodo para hacer clic sobre icono (Hoja en Blanco columna Previews)
		 * @author Misraim Hernandez
		 */
		public void seleccionarPreviewsHojaBlanco() {
	        try {
	        	loadingUltimus();
	        	sikuli.screen.click(imgPreviewsHojaBlanco);
			
	        } catch (Exception e) {
	            System.out.println(" -> " + e);
	            throw new AssertionError("Fallo en funcion Seleccionar Hoja en Blanco columna Previews");
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
	            	 String contenidoCelda = hoja.getCell(0,2, i);
	            	 System.out.println("Contenido: "+contenidoCelda); 
	            	 testCase.addTextEvidenceCurrentStep("Campo "+contenidoCelda+ "del reporte");
	     		}
	        } catch (Exception e) {
	            System.out.println(" -> " + e);
	            throw new AssertionError("Error al leer encabezados de Excel");
	        }
	    }
		/**
		 * Metodo para insertar un nuevo planchado
		 * @param plaza - Valor del Filtro "-plaza"
		 * @param nombre - Valor del Filtro "nombre"
		 * @param descripcion - Valor del Filtro "descripcion"
		 * @param activo - Valor del Filtro "activo"
		 * @author Misraim Hernandez
		 */
		public void insertarNuevoPlanchado(String plaza, String nombre, String descripcion, String activo) {
	        try {
	        	u.waitWebElement(btnNuevo , 60, 60);
				u.highLight(btnNuevo);
				u.click(btnNuevo);
				u.waitWebElement(txtNombreNuevoPlanchado , 60, 60);
				u.highLight(txtNombreNuevoPlanchado );
				u.sendKeys(txtNombreNuevoPlanchado, nombre);
				u.highLight(txtDescripcionNuevoPlanchado);
				u.sendKeys(txtDescripcionNuevoPlanchado, descripcion);
				u.highLight(filtroPlazaNuevoPlanchado);
				selectOption("Filtro_Plaza_Nuevo_Planchado",plaza, filtroPlazaNuevoPlanchado);
				u.highLight(filtroActivoNuevoPlanchado);
				selectOption("Filtro_Activo_Nuevo_Planchado",activo, filtroActivoNuevoPlanchado);
				
				u.highLight(btnInsertar);
				u.click(btnInsertar);
				
	        } catch (Exception e) {
	            System.out.println(" -> " + e);
	            throw new AssertionError("Fallo en funcion insertar nuevo planchado");
	        }
	    }
		
		/**
		 * Metodo para Editar un Registro de Planchado
		 * @param plaza - Valor del Filtro "plaza"
		 * @param nombre - Valor del Filtro "nombre"
		 * @param descripcion - Valor del Filtro "descripcion"
		 * @param activo - Valor del campo "activo"
		 * @author Misraim Hernandez
		 */
		public void EditarRegistroPlanchado(String plaza, String nombre, String descripcion, String activo) {
	        try {
	        	u.hardWait(1);
	        	loadingPlanchado();
	        	sikuli.screen.click(imgEditarRegistroPlanchado);
	        	loadingPlanchado();
	        	testCase.addScreenShotCurrentStep(u, "editar");
	        	
	        	u.waitWebElement(txtNombreNuevoPlanchado , 60, 60);
				u.highLight(txtNombreNuevoPlanchado );
				clearInput(txtNombreNuevoPlanchado);
				u.sendKeys(txtNombreNuevoPlanchado, nombre);
				
				u.highLight(txtDescripcionNuevoPlanchado);
				clearInput(txtDescripcionNuevoPlanchado);
				u.sendKeys(txtDescripcionNuevoPlanchado, descripcion);
				
				u.highLight(filtroPlazaNuevoPlanchado);
				selectOption("Filtro_Plaza_Nuevo_Planchado",plaza, filtroPlazaNuevoPlanchado);
				
				u.highLight(filtroActivoNuevoPlanchado);
				selectOption("Filtro_Activo_Nuevo_Planchado",activo, filtroActivoNuevoPlanchado);
				loadingPlanchado();
				
	        } catch (Exception e) {
	            System.out.println(" -> " + e);
	            throw new AssertionError("Fallo en funcion Editar Registro Planchado ");
	        }
	    }
		
		/**
		 * Metodo para validar elementos de Pantalla parametros en menu Planchado
		 * @author Misraim Hernandez
		 */
		public void ValidaPlanchadoParametrosPantalla() {
	        try {
	  
	        	loadingUltimus();
	        	sikuli.screen.click(imgBotonImageControlParametros);
	        	u.waitWebElement(lblParametros , 60, 60);
				u.highLight(lblParametros);
				u.highLight(lblTiendas);
				u.highLight(lblLegos);
				u.highLight(lblPaquetes);
				u.highLight(btnActualizar);
				u.highLight(btnCancelar);
				testCase.addScreenShotCurrentStep("Validar Parametros Pantalla Planchado");
				u.click(btnCancelar); 
				
	        } catch (Exception e) {
	            System.out.println(" -> " + e);
	            throw new AssertionError("Fallo en funcion Valida los Parametros de Pantalla Parametros");
	        }
	    }
		
		/**
		 * Metodo para validar elementos de Pantalla Programacion
		 * @author Misraim Hernandez
		 */
		public void ValidaPlanchadoProgramacionPantalla() {
	        try {
	  
	        	loadingUltimus();
	        	sikuli.screen.click(imgBotonImageControlProgramacion);
	        	u.waitWebElement(lblProgramacion , 60, 60);
				u.highLight(lblProgramacion);
				u.highLight(lblFechaProgramacion);
				u.highLight(lblTipoProgramacion);
				u.highLight(lblAccionProgramacion);
				u.highLight(lblFechaInicioProgramacion);
				u.highLight(lblFechaFinProgramacion);
				u.highLight(lblMensajeProgramacion);
				u.highLight(lblPProgramacion);
				u.highLight(lblDProgramacion);
				u.highLight(lblEProgramacion);
				//u.highLight(IconoDetalleParametros);
				//u.highLight(IconoDetallePlanchado);
				u.highLight(btnActualizarProgramacion);
				u.highLight(btnCancelarProgramacion);
				testCase.addScreenShotCurrentStep("Validar Parametros Pantalla Programacion");
				u.click(btnCancelarProgramacion); 
				
	        } catch (Exception e) {
	            System.out.println(" -> " + e);
	            throw new AssertionError("Fallo en funcion Valida los Parametros de Pantalla Programacion");
	        }
	    }
		/**
		 * Metodo para validar elementos de Pantalla Subir CSV
		 * @author Misraim Hernandez
		 */
		public void validarElementosPantallaSubirCSV() {
	        try {
	        	loadingUltimus();
	        	u.waitWebElement(lblSubirCSV, 120, 60);
	        	u.highLight(lblSubirCSV);
	        	u.highLight(lblArchivo);
	        	u.highLight(btnSeleccionarArchivo);
	        	u.highLight(btnSubir);
	        	u.highLight(btnCerrar);
	        	
	        } catch (Exception e) {
	            System.out.println(" -> " + e);
	            throw new AssertionError("Fallo en funcion valida Elementos en Pantalla Subir CSV");
	        }
	    }
		/**
		 * Metodo para seleccionar un archivo de uan ruta especifica 
		 * @param nomArchivo - nombre del documento incluyendo extension
		 * @author Misraim Hernandez
		 */
		public void SubirArchivo(String nomArchivo) {
			try {
				
			final String BASE_PATH = System.getProperty("user.dir");
			String pathFileX = BASE_PATH + "\\Config\\Input\\Files\\Planchado\\"+nomArchivo;
			u.mouseOverAndClick(btnSeleccionarArchivo);
			u.hardWait(4);
			sikuli.screen.type(CajaTXTCargarArchivo,pathFileX );
			sikuli.screen.type(Key.ENTER);
			u.hardWait(5);
			testCase.addScreenShotCurrentStep("Archivo");
			u.highLight(btnSubir);
			u.click(btnSubir);
				
			} catch (Exception e) {
				System.out.println(" -> " + e);
				throw new AssertionError("Fallo en funcion Seleccionar Archivo");
			}
		
		}
		
		
		/**
		 * Metodo para ingresar en el menu de Historiales
		 * @author Misraim Hernandez 
		 */
		public void selectMenuHistoriales() {
	        try {
	        	u.waitWebElement(btnMenu, 60, 60);
	        	u.highLight(btnMenu);
	        	u.hardWait(2);
	        	u.click(btnMenu);
	        	u.waitWebElement(subMenuHistoriales, 60, 60);
	        	u.highLight(subMenuHistoriales);
	        	u.hardWait(2);
	        	u.mouseOverAndClick(subMenuHistoriales);
	        	loadingUltimus();
	        	u.waitWebElement(filtroPlaza, 60, 60);
	        	u.highLight(filtroPlaza);
	        } catch (Exception e) {
	            System.out.println(" -> " + e);
	            throw new AssertionError("Fallo al seleccionar: sub menu Historiales");
	        }
	    }
		
		/**
		 * Metodo para la busqueda del Historial
		 * @param plaza - Valor del Filtro "Plaza"
		 * @param tienda - Valor del Filtro "Tienda"
		 * @author Misraim Hernandez 
		 */
		public void buscarSeleccionarHistoriales(String plaza,String tienda, String nombre, String descripcion) {
	        try {
	        	loadingPlanchado();
	        	u.highLight(lblHistorialPlanchado);
	        	u.highLight(filtroPlaza);
	        	selectOption("Filtro_Plaza",plaza, filtroPlaza);
	        	loadingPlanchado();
	        	u.highLight(filtroTienda);
	        	selectOption("Filtro_Tienda",tienda, filtroTienda);
	        	loadingUltimus();
	        	u.highLight(filtroNombre);
	        	u.sendKeys(filtroNombre, nombre);
	        	u.hardWait(3);
	        	u.highLight(filtroDescripcion);
	        	u.sendKeys(filtroDescripcion, descripcion);
	        	u.highLight(filtroFechaInicio);
	        	u.highLight(filtroFechaFin);
	        	u.highLight(btnBuscraHistoriales);
	        	u.highLight(btnExcelHistoriales);
	        	u.highLight(btnLimpiarHistoriales);
	        	u.click(btnBuscraHistoriales);
	        	loadingPlanchado();
	        } catch (Exception e) {
	            System.out.println(" -> " + e);
	            throw new AssertionError("Fallo en funcion BuscarSeleccionarPlanchado");
	        }
	    }
		
		
		/**
		 * Metodo para validar elementos pantalla Historial/ Excluidos
		 * @author Misraim Hernandez 
		 */
		public void ValidaHistorialesParametrosPantalla() {
	        try {
	        	u.hardWait(1);
	        	loadingPlanchado();
	        	sikuli.screen.click(imgBotonControlColumnaExcluidos);
	        	loadingPlanchado();
	        	u.waitWebElement(lblLoc , 60, 60);
				u.highLight(lblLoc);
				u.highLight(lblNoTienda);
				u.highLight(lblNombreTienda);
				u.highLight(lblNoArticulo);
				u.highLight(lblDescripcionArticulo);
				u.highLight(lblSuperFrupo);
				u.highLight(lblNoFA);
				u.highLight(lblNombreFA);
				u.highLight(lblTipoFA);
				u.highLight(lblEstatusActual);
				u.highLight(lblEstatusPlanchar);
				u.highLight(lblLego);
				u.highLight(lblPaqueteExclusion);
				u.highLight(btnExcel);
				u.highLight(btnTexto);
				u.highLight(btnSalir);
				u.click(btnExcel);
				loadingPlanchado();
				testCase.addScreenShotCurrentStep("Validar Parametros Pantalla Historiales");
				
	        } catch (Exception e) {
	            System.out.println(" -> " + e);
	            throw new AssertionError("Fallo en funcion Valida los Parametros de Pantalla Parametros");
	        }
	    }
		
		/**
		 * Metodo para validar elementos pantalla Historial
		 * @author Misraim Hernandez 
		 */
		public void ValidaParametrosPantallaHistoriales() {
	        try {
	        	u.hardWait(1);
	        	loadingPlanchado();
	        	u.highLight(lblExcluidosGridHistorial);
	        	u.highLight(lblParametrosGridHistorial);
	        	u.highLight(lblPlazaGridHistorial);
	        	u.highLight(lblPlanchadoGridHistorial);
	        	u.highLight(lblFechaGridHistorial);
	        	u.highLight(lblTiendaGridHistorial);
	        	u.highLight(lblLigar1FAGridHistorial);
	        	u.highLight(lblLigarMas1FAGridHistorial);
	        	testCase.addScreenShotCurrentStep("Validar el grid");
	        	scrollToElement(lblActivar1FAGridHistorial);
	        	u.highLight(lblLigarMas1FANoEstandarGridHistorial);
	        	u.highLight(lblLigarNoEstandarGridHistorial);
	        	u.highLight(lblActivar1FAGridHistorial);
	        	u.highLight(lblActivarMas1FAGridHistorial);
	        	u.highLight(lblActivarMas1FANoEstandarGridHistorial);
	        	scrollToElement(lblActivarMas1FANoEstandarGridHistorial);
	        	testCase.addScreenShotCurrentStep("Validar el grid 2 parte");
	        	u.highLight(lblActivarNoEstandarGridHistorial);
	        	u.highLight(lblInactivarActivoRMSGridHistorial);
	        	u.highLight(lblInactivarNoExisteEnLegoGridHistorial);
	        	u.highLight(lblInactivarActivoRMSGridHistorial);
	        	u.highLight(lblInactivarNoExisteEnLegoGridHistorial);
	        	scrollToElement(lblMensajeGridHistorial);
	        	u.highLight(lblMensajeGridHistorial);
	        	testCase.addScreenShotCurrentStep("Validar el grid 3 parte");
	        	u.click(btnExcelHistoriales);
	        	loadingPlanchado();
	        	
	        } catch (Exception e) {
	            System.out.println(" -> " + e);
	            throw new AssertionError("Fallo en funcion Valida Parametros de Pantalla Historiales");
	        }
	    }
		
		/**
		 * Metodo para validar elementos pantalla parametros en menu Historial
		 * @author Misraim Hernandez 
		 */
		public void ValidaElementosPantallaParametrosHitorial() {
	        try {
	        	u.hardWait(1);
	        	loadingPlanchado();
	        	sikuli.screen.click(imgBotonImageControlParametrosHistorial);
	        	loadingPlanchado();
	        	u.waitWebElement(lblParametros , 60, 60);
				u.highLight(lblParametros);
				u.highLight(lblTiendas);
				u.highLight(lblLegos);
				u.highLight(lblPaquetes);
				u.highLight(btnSalirPantallaParametrosHistorial);
				testCase.addScreenShotCurrentStep("Validar Parametros Pantalla Planchado");
				u.click(btnSalirPantallaParametrosHistorial); 
				loadingPlanchado();
				
	        } catch (Exception e) {
	            System.out.println(" -> " + e);
	            throw new AssertionError("Fallo en funcion Valida los Parametros de Pantalla Parametros");
	        }
	    }
		
		/**
		 * Método para leer los encabezados de un documento de Excel indicando la fila y el número de columnas
		 * @param pathExcel - Se debe pasar la ruta y el nombre del archivo con su extencion elmplo "C:\Users\74871\Downloads\ReporteExcepciones.xlsx"
		 * @param fila - Fila donde iniciara la lectura 
		 * @param columnas - Colocar el numero de columnas que se leeran
		 * @author Misraim Hernandez
		 */
		public void leerCeldasExcel_fila_Columna(String pathExcel, int fila, int columnas) {
	        try {
	        	
				ExcelUtil hoja = new ExcelUtil(pathExcel);
	             hoja.open();
	             for(int i=0;i<columnas;i++){
	            	 String contenidoCelda = hoja.getCell(0,fila, i);
	            	 System.out.println("Contenido: "+contenidoCelda); 
	            	 testCase.addTextEvidenceCurrentStep("Campo "+contenidoCelda+ "del reporte");
	     		}
	        } catch (Exception e) {
	            System.out.println(" -> " + e);
	            throw new AssertionError("Error al leer encabezados de Excel");
	        }
	    }
		
		/**
		 * Genera un valor alfanumerico aleatorio de la longitud especificada.
		 * @param longitud
		 * @return
		 * @author Esmeralda Garcia Torres
		 */
		public String cadenaAleatoria(Integer longitud) {
			try {
				String randomizedString = RandomStringUtils.randomAlphanumeric(longitud);
				return randomizedString;
			} catch (Exception e) {
				System.out.println(" -> " + e);
	            throw new AssertionError("Error al generar cadena aleatoria");
			}
		}
		
		/**
		 * Limpia un input seleccionando el texto que tiene y borrandolo.
		 * 
		 * @param inputBy
		 * @author Esmeralda Garcia Torres
		 */
		public void clearInput(By inputBy) {
			try {
				WebElement toClear = u.getDriver().findElement(inputBy);
				toClear.sendKeys(Keys.CONTROL + "a");
				toClear.sendKeys(Keys.DELETE);
			} catch (Exception e) {
				System.out.println(" -> " + e);
			}
		}
		
		/**
		 * Espera hasta que desaparezca el modal de Procesando
		 */
		public void loadingPlanchado() {
			try {
				do {
					u.hardWait(2);
				} while (u.getDriver().findElement(loadingModal).isDisplayed());
			} catch (Exception e) {
				System.out.println(" -> " + e);
			}
		}
}
