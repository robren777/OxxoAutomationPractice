package WEB.util;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

// NOTA: Utileria y scripts descontinuados debido a creacion de portal web proyecto Ruteo Dinamico MVP2

public class RuteoDinamicoWebApex {
	private SeleniumUtil u;
	private WebDriverWait wait;
	private TestCase testCase;
	
	private String user = GlobalVariables.usuarioRDApex;
	private String pass = GlobalVariables.contrasenaRDApex;

	// Your connection isn't private//
	private By btnAdvance = By.id("details-button");
	private By linkContinue = By.id("proceed-link");

	// LOGIN
	private By locatorUserInput = By.id("P9999_USERNAME");
	private By locatorPassInput = By.id("P9999_PASSWORD");
	private By locatorLoginButton = By.id("B55903703140604025");

	// INICIO PORTAL APLICACIONES
	private By locatorMenuInicio = By.id("t_TreeNav");
	private By tituloPaginaInicio = By.linkText("Carga Restriccion Tiendas - Ruteo Dinamico");
	private By home = By.linkText("Home");
	private By XXFC_CARGA_EXCEL = By.linkText("XXFC_CARGA_EXCEL");
	private By XXFC_HISTORICO_CARGAS = By.linkText("XXFC_HISTORICO_CARGAS");
	
	//	CARGA CATALOGO RESTRICCIONES DE TIENDAS
	private By tituloPaginaCargaArchivo= By.cssSelector("#R56461682514873871_heading");
	private By chooseFile = By.cssSelector("#P15_NEW_DROPZONE");
	private By choose_file = By.cssSelector("#P15_NEW");
	private By sizeMB = By.cssSelector("#SIZE_FILE_TXT");
	private By tipoArchivo = By.cssSelector("#TYPE_FILE_TXT");
	private By botonCargarArchivo = By.cssSelector("#B56462205387873876");
	
	//	RESUMEN REGISTROS CORRECTOS
	private By tituloResumenRegCorrectos= By.cssSelector("#R59942711800120809_heading");
	private By tituloTablaRegCorrectos= By.cssSelector("#R61023259841372795_heading");
	private By datosRegistrosTotales = By.cssSelector("#REGS_TOT_TXT_CONTAINER");
	private By datosRegsTotalesCorrectos = By.cssSelector("#REGS_OK_TXT_CONTAINER");
	private By datosRegsTotalesConError = By.cssSelector("#REGS_ERR_TXT_CONTAINER");
	private By botonCancelarPagRegCorrectos = By.cssSelector("#B59942575706120807");
	private By botonMostrarErrores = By.cssSelector("#B59943123841120813");
	private By botonEnviarRDM = By.cssSelector("#B59942418830120806");
	private By tablaInfoDatosCorrectos = By.cssSelector("#report_R61023259841372795");
	private By download = By.linkText("Download");
	
	//	DETALLE DE ERRORES
	private By tituloDetalleErrores= By.cssSelector("#R59943496592120816_heading");
	private By tituloTablaRegIncorrectos= By.cssSelector("#R61096993891690014_heading");
	private By datosRegistrosDescartados = By.cssSelector("#TOT_ERR_TXT_CONTAINER");
	private By botonRegresarPagDetallesErr = By.cssSelector("#B59943526582120817");
	private By tablaInfoDatosIncorrectos = By.cssSelector("#report_R61096993891690014");

	//	CONFIRMACION TIENDAS A ENVIAR
	private By tituloConfirmacion= By.cssSelector("#R61119771050532678_heading");
	private By tituloTablaTiendasEnviar= By.cssSelector("#R61120052310532681_heading");
	private By botonCancelarPagConfirmacion = By.cssSelector("#B61120649235532687");
	private By botonConfirmarEnvio = By.cssSelector("#B61120529026532686");
	private By tablaInfoDatosTiendas = By.cssSelector("#report_R61120052310532681");
	
	//	RESULTADO DE CARGA
	private By tituloResultadosCarga= By.cssSelector("#R61413046434609162_heading");
	private By datosFolioCarga = By.cssSelector("#ID_CARGA_TXT_CONTAINER");
	private By botonAceptar = By.cssSelector("#B61375763606800792");
	private By tablaInfoArchivoEnviado = By.cssSelector("#report_R61413046434609162");
	
	//	CARGAS HISTORICAS
	private By tituloCargasHistoricas = By.cssSelector("#R61258587685530566_heading");
	private By menuFiltroCargasH = By.cssSelector("#R61258587685530566_ig_toolbar");
	private By tituloArchivosEnviados = By.cssSelector("#R61264498563530547_heading");
	private By menuFiltroArchivosE = By.cssSelector("#R61264498563530547_ig_toolbar");
	private By actions = By.cssSelector("#R61258587685530566_ig_toolbar_actions_button");
	private By downloadCargasH = By.cssSelector("#R61258587685530566_ig_toolbar_11i");
	private By download_CargasH = By.xpath("//body/div[23]/div[11]/div[1]/button[3]");
	private By filtroPorColumna = By.cssSelector("#R61258587685530566_ig_toolbar_column_filter_button");
	private By opcionCaseSensitive = By.cssSelector("#R61258587685530566_ig_toolbar_0i");
	private By opcionAllTextColumns = By.cssSelector("#R61258587685530566_ig_toolbar_2_c0i");
	private By opcionFechaCarga = By.cssSelector("#R61258587685530566_ig_toolbar_2_c1i");
	private By opcionFechaUltEnvio = By.cssSelector("#R61258587685530566_ig_toolbar_2_c2i");
	private By opcionArchivoExcel = By.cssSelector("#R61258587685530566_ig_toolbar_2_c3i");
	private By inputBusqueda = By.cssSelector("#R61258587685530566_ig_toolbar_search_field");
	private By botonGo = By.xpath("//body/form[@id='wwvFlowForm']/div[1]/div[2]/div[2]/main[1]/div[2]"
			+ "/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[3]/div[1]/div[1]/div[1]/button[2]");
	private By botonXFiltro = By.xpath("//body/form[@id='wwvFlowForm']/div[1]/div[2]/div[2]/main[1]/div[2]"
			+ "/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[5]/div[1]/div[2]/ul[1]/li[1]/span[4]");
	private By infoPrimeraCelda = By.cssSelector("#R61258587685530566_ig_grid_vc_cur");
	private By opcionColumns = By.cssSelector("#R61258587685530566_ig_toolbar_0i");
	private By colIdCarga = By.xpath("//tbody/tr[1]/td[1]/span[1]");
	private By colFechaCarga = By.xpath("//tbody/tr[2]/td[1]/span[1]");
	private By colFechaUltEnvio = By.xpath("//tbody/tr[3]/td[1]/span[1]");
	private By colArchivoExcel = By.xpath("//tbody/tr[4]/td[1]/span[1]");
	private By col_IdCarga = By.xpath("//td[contains(text(),'Id Carga')]");
	private By col_FechaCarga = By.xpath("//td[contains(text(),'Fecha Carga')]");
	private By col_FechaUltEnvio = By.xpath("//td[contains(text(),'Fecha Ult. Envio a RDM')]");
	private By col_ArchivoExcel = By.xpath("//td[contains(text(),'Archivo Excel')]");
	private By botonSave = By.xpath("//body/div[23]/div[3]/div[1]/button[3]");
	private By inputColumnWidth = By.cssSelector("#R61258587685530566_ig_CD_COLUMN_WIDTH");
	private By columnasTabla = By.xpath("/html[1]/body[1]/form[1]/div[1]/div[2]/div[2]/main[1]/div[2]/div[1]"
			+ "/div[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[5]/div[3]/div[1]/div[3]/div[4]/table[1]/thead[1]");
	private By opcionFilter = By.cssSelector("#R61258587685530566_ig_toolbar_2i");
	private By tipoLista = By.cssSelector("#R61258587685530566_ig_FD_TYPE");
	private By columnasLista = By.cssSelector("#R61258587685530566_ig_FD_COLUMN");
	private By operadorLista = By.cssSelector("#R61258587685530566_ig_FD_OPERATOR");
	private By inputValue = By.cssSelector("#R61258587685530566_ig_FD_VALUE");
	private By opcionData = By.cssSelector("#R61258587685530566_ig_toolbar_3i");
	private By opcionSort = By.cssSelector("#R61258587685530566_ig_toolbar_3_0i");
	private By columnLista = By.cssSelector("#R61258587685530566_ig_SD_COLUMN");
	private By directionLista = By.cssSelector("#R61258587685530566_ig_SD_DIRECTION");
	private By nullsLista = By.cssSelector("#R61258587685530566_ig_SD_NULLS");
	
	public RuteoDinamicoWebApex(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		this.testCase = testCase;
	}
	
	/**
	 * Login Apex carga archivo restricciones de tiendas
	 * 
	 * @param user
	 * @param pass
	 * @return Boolean true si existen los elementos al hacer login exitoso, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean loginApexCargaArchivo() {
		Boolean existencia = false;
		try {
			Integer cont = 0;
			u.hardWait(1);
			u.getDriver().manage().deleteAllCookies();
			String url = GlobalVariables.URL_APEX_CARGA_RESTR_TIENDAS;
			testCase.addTextEvidenceCurrentStep(url);
			u.get(url);
			u.getDriver().manage().window().maximize();

			if (u.getDriver().findElements(btnAdvance).size() != 0) {
				u.click(btnAdvance);
				u.waitWebElement(linkContinue, 20, 2);
				u.click(linkContinue);
			}

			u.waitWebElement(locatorUserInput, 15, 2);
			testCase.addScreenShotCurrentStep("");

			do {
				cont += 1;
				if (cont < 4) {
					wait.until(ExpectedConditions.elementToBeClickable(locatorLoginButton));
					u.highLight(locatorUserInput);
					clearInput(locatorUserInput);
					u.sendKeys(locatorUserInput, user);
					u.highLight(locatorPassInput);
					clearInput(locatorPassInput);
					u.sendKeys(locatorPassInput, pass);
					u.highLight(locatorLoginButton);
					testCase.addScreenShotCurrentStep(u, "Credenciales");
					u.click(locatorLoginButton);
					try {
						existencia = validarPaginaInicio();
					} catch (Exception e) {
						existencia = false;
					}
				} else {
					refrescarPagina();
					cont = 0;
				}
			} while (!existencia);

			testCase.addScreenShotCurrentStep(u, "AccessControl");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se pudo iniciar sesion");
		}
		return existencia;
	}
	
	/**
	 * Limpia un input seleccionando el texto que tiene y borrandolo.
	 * 
	 * @param inputBy
	 * @author cristhian.m.gonzalez
	 */
	public void clearInput(By inputBy) {
		try {
			WebElement toClear = u.getDriver().findElement(inputBy);
			toClear.sendKeys(Keys.CONTROL + "a");
			toClear.sendKeys(Keys.DELETE);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al limpiar el campo");
		}
	}
	
	/**
	 * Espera a que el elemento se muestre, lo resalta, toma un screenshot, y le da
	 * click.
	 * 
	 * @param name      -> Nombre del boton
	 * @param elementBy -> Elemento al realizar accion
	 * @author cristhian.m.gonzalez
	 */
	public void clicOpcion(String name, By elementBy) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(elementBy));
			u.highLight(elementBy);
			testCase.addScreenShotCurrentStep(u, "Dar clic en " + name);
			u.unhighLight(elementBy);
			u.click(elementBy);
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar clic en: " + name);
		}
	}
	
	/**
	 * Regresa a la pagina de inicio (Home) con archivo cargado
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clicHomeAbandonarCarga() {
		try {
			clicOpcion("Home", home);
			Robot r = new Robot();
			r.keyPress(KeyEvent.VK_ENTER);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al realizar Choose file");
		}
	}
	
	/**
	 * Acceso al link de carga del archivo de restricciones de tiendas
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clicXxfcCargaExcel() {
		try {
			clicOpcion("XXFC_CARGA_EXCEL", XXFC_CARGA_EXCEL);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar click en XXFC_CARGA_EXCEL");
		}
	}
	
	/**
	 * Acceso al link de cargas historicas
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clicXxfcHistoricoCargas() {
		try {
			clicOpcion("XXFC_HISTORICO_CARGAS", XXFC_HISTORICO_CARGAS);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar click en XXFC_HISTORICO_CARGAS");
		}
	}
	
	/**
	 * Input para seleccionar archivo 
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clicChooseFile() {
		try {
			u.hardWait(5);
			Actions builder = new Actions(this.u.getDriver());
			WebElement element = this.u.getDriver().findElement(choose_file);
			builder.moveToElement(element).click().build().perform();
			testCase.addScreenShotCurrentStep(u, "realizar Choose file");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al realizar Choose file");
		}
	}
	
	/**
	 * Clic en el boton para cargar archivo
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clicCargarArchivo() {
		try {
			clicOpcion("CARGAR ARCHIVO", botonCargarArchivo);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al cargar archivo");
		}
	}
	
	/**
	 * Clic en el boton cancelar de la pagina de resumen de registros correctos
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clicCancelarResumen() {
		try {
			clicOpcion("CANCELAR", botonCancelarPagRegCorrectos);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al mostrar pagina");
		}
	}
	
	/**
	 * Clic en el boton cancelar de la pagina de confirmacion de tiendas a enviar
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clicCancelarConfirmacion() {
		try {
			clicOpcion("CANCELAR", botonCancelarPagConfirmacion);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al mostrar pagina");
		}
	}
	
	/**
	 * Clic en el boton regresar de la pagina de detalles de errores
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clicRegresarPagDetalle() {
		try {
			clicOpcion("REGRESAR", botonRegresarPagDetallesErr);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al mostrar pagina");
		}
	}
	
	/**
	 * Clic en el boton mostrar errores de la pagina de resumen de registros correctos
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clicMostrarErrores() {
		try {
			clicOpcion("MOSTRAR ERRORES", botonMostrarErrores);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al mostrar pagina");
		}
	}
	
	/**
	 * Clic en el boton enviar a rdm de la pagina de resumen de registros correctos
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clicEnviarRDMPagResumen() {
		try {
			clicOpcion("ENVIAR A RDM", botonEnviarRDM);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al mostrar pagina");
		}
	}
	
	/**
	 * Clic en el boton confirmar de la pagina de confirmacion de tiendas a enviar
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clicConfirmar() {
		try {
			clicOpcion("CONFIRMAR", botonConfirmarEnvio);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al mostrar pagina");
		}
	}
	
	/**
	 * Clic en el boton aceptar de la pagina de resultados de carga
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clicAceptar() {
		try {
			clicOpcion("ACEPTAR", botonAceptar);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al mostrar pagina");
		}
	}
	
	/**
	 * Clic en el boton Download
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clicDownload() {
		try {
			clicOpcion("Download", download);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al descargar archivo");
		}
	}
	
	/**
	 * Clic en el boton Download
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clicDownloadCargasHist() {
		try {
			clicOpcion("Download", downloadCargasH);
			clicOpcion("Download", download_CargasH);
			u.hardWait(5);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al descargar archivo");
		}
	}
	
	/**
	 * Clic en el boton Actions
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clicActions() {
		try {
			clicOpcion("Actions", actions);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar clic al boton Actions");
		}
	}
	
	/**
	 * Clic en la opcion Columns
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clicOpcionColumns() {
		try {
			clicOpcion("Columns", opcionColumns);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar clic a la opcion Columns");
		}
	}
	
	/**
	 * Clic en la opcion Filter
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clicOpcionFilter() {
		try {
			clicOpcion("Filter", opcionFilter);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar clic a la opcion Filter");
		}
	}
	
	/**
	 * Clic en la opcion Data
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clicOpcionData() {
		try {
			clicOpcion("Data", opcionData);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar clic a la opcion Data");
		}
	}
	
	/**
	 * Clic en la opcion Sort
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clicOpcionSort() {
		try {
			clicOpcion("Sort", opcionSort);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar clic a la opcion Sort");
		}
	}
	
	/**
	 * Clic en el boton save de ventana vista columna
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clicSave() {
		try {
			u.hardWait(3);
			clicOpcion("Save", botonSave);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar clic al boton Save");
		}
	}
	
	/**
	 * Clic en el boton filtro por columnas
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clicFiltroColumnas() {
		try {
			clicOpcion("filtro columnas", filtroPorColumna);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar clic al boton filtro columnas");
		}
	}
	
	/**
	 * Clic en la opcion de filtro por columna
	 * 
	 * @param opcionFiltro Elemento al realizar accion
	 * @param nombrefiltro Nombre del filtro
	 * @author cristhian.m.gonzalez
	 */
	public void clicOpcionFiltro(String opcionFiltro, String nombrefiltro) {
		try {
			switch (opcionFiltro) {
			case "opcionAllTextColumns":
				clicOpcion(nombrefiltro, opcionAllTextColumns);
				break;
			case "opcionFechaCarga":
				clicOpcion(nombrefiltro, opcionFechaCarga);
				break;
			case "opcionFechaUltEnvio":
				clicOpcion(nombrefiltro, opcionFechaUltEnvio);
				break;
			case "opcionArchivoExcel":
				clicOpcion(nombrefiltro, opcionArchivoExcel);
				break;
			case "opcionCaseSensitive":
				clicOpcion(nombrefiltro, opcionCaseSensitive);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar clic a la opcion de filtro " + nombrefiltro);
		}
	}
	/**
	 * Clic en el boton Go
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clicGo() {
		try {
			u.hardWait(5);
			clicOpcion("Go", botonGo);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar clic al boton Go");
		}
	}
	
	/**
	 * Clic en el boton eliminar ("X") del filtro realizado
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clicEliminarFiltro() {
		try {
			u.hardWait(10);
			clicOpcion("X", botonXFiltro);
			u.hardWait(5);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar clic al boton Go");
		}
	}
	
	/**
	 * Cargar archivo
	 * 
	 * @param path Ruta del archivo
	 * @author cristhian.m.gonzalez
	 */
	public void cargarArchivo(String path) {
		try {
			u.hardWait(5);
			WebElement element = this.u.getDriver().findElement(choose_file);
			element.sendKeys(path);
			testCase.addScreenShotCurrentStep(u, "archivo Cargado");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al cargar archivo");
		}
	}
	
	/**
	 * Refrescar pagina
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void refrescarPagina() {
		try {
			u.getDriver().navigate().refresh();
			u.hardWait(5);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al refrescar pagina");
		}
	}
	
	/**
	 * Establece una dato a filtrar en el campo de busqueda de filtro por columna
	 * 
	 * @param dato Dato para realizar la busqueda
	 * @author cristhian.m.gonzalez
	 */
	public void establecerDatoBusqueda(String dato) {
		try {
			u.hardWait(5);
			WebElement element = this.u.getDriver().findElement(inputBusqueda);
			element.clear();
			element.sendKeys(dato);
			testCase.addScreenShotCurrentStep(u, "estableciendo dato de busqueda");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al establecer dato");
		}
	}
	
	/**
	 * Selecciona la columna a quitar de la vista de la tabla
	 * 
	 * @param opcionColumna La columna a quitar
	 * @param nombreColumna Nombre de la columna a quitar
	 * @param tamColumna Ancho de la columna
	 * @author cristhian.m.gonzalez
	 */
	public void establecerOpcionColumna(String opcionColumna, String nombreColumna, String tamColumna) {
		try {
			switch (nombreColumna) {
			case "Id Carga":
				clicOpcion(nombreColumna, col_IdCarga);
				u.hardWait(2);
				u.sendKeys(inputColumnWidth, tamColumna);
				u.hardWait(2);
				clicOpcion(nombreColumna, colIdCarga);
				break;
			case "Fecha Carga":
				clicOpcion(nombreColumna, col_FechaCarga);
				u.hardWait(2);
				u.sendKeys(inputColumnWidth, tamColumna);
				u.hardWait(2);
				clicOpcion(nombreColumna, colFechaCarga);
				u.hardWait(2);
				clicOpcion(nombreColumna, colIdCarga);
				break;
			case "Fecha Ult. Envio a RDM":
				clicOpcion(nombreColumna, col_FechaUltEnvio);
				u.hardWait(2);
				u.sendKeys(inputColumnWidth, tamColumna);
				u.hardWait(2);
				clicOpcion(nombreColumna, colFechaUltEnvio);
				u.hardWait(2);
				clicOpcion(nombreColumna, colFechaCarga);
				break;
			case "Archivo Excel":
				clicOpcion(nombreColumna, col_ArchivoExcel);
				u.hardWait(2);
				u.sendKeys(inputColumnWidth, tamColumna);
				u.hardWait(2);
				clicOpcion(nombreColumna, colArchivoExcel);
				u.hardWait(2);
				clicOpcion(nombreColumna, colFechaUltEnvio);
				break;
			default:
				break;
			}
			testCase.addScreenShotCurrentStep(u, "estableciendo filtro");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al establecer dato");
		}
	}
	
	/**
	 * Selecciona el tipo, la columna y el operador a realizar como filtro 
	 * 
	 * @param opcionType Columna o fila a realizar filtro
	 * @param opcionColumna La columna a realizar filtro
	 * @param opcionOperador El operador a realizar sobre la columna
	 * @param valorInput Valor del filtro a realizar
	 * @author cristhian.m.gonzalez
	 */
	public void establecerFiltroColumna(String opcionType, String opcionColumna, String opcionOperador, String valorInput) {
		try {
			if(opcionType.equals("Row")) {
				u.sendKeys(tipoLista, opcionType);
				u.sendKeys(inputValue, valorInput);
			} else {
				if(opcionColumna.equals("Fecha Carga")) {
					u.sendKeys(columnasLista, opcionColumna);
					u.sendKeys(operadorLista, opcionOperador);
				} else {
					u.sendKeys(columnasLista, opcionColumna);
					u.sendKeys(operadorLista, opcionOperador);
					u.sendKeys(inputValue, valorInput);
				}
			}
			u.hardWait(5);
			testCase.addScreenShotCurrentStep(u, "estableciendo filtro");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al establecer dato");
		}
	}
	
	/**
	 * Selecciona la columna y la ordenacion a realizar como filtro 
	 * 
	 * @param opcionColumna La columna a realizar filtro
	 * @param opcionDirection El sentido de ordenamiento a realizar sobre la columna
	 * @param opcionNull Primero o ultimo como opciones de filtro a realizar
	 * @author cristhian.m.gonzalez
	 */
	public void establecerFiltroSort(String opcionColumna, String opcionDirection, String opcionNull) {
		try {
			u.sendKeys(columnLista, opcionColumna);
			u.sendKeys(directionLista, opcionDirection);
			u.sendKeys(nullsLista, opcionNull);
			testCase.addScreenShotCurrentStep(u, "estableciendo filtro");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al establecer dato");
		}
	}

	/**
	 * Valida que se vean los elementos principales de la p�gina de inicio
	 * 
	 * @return Boolean true si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarPaginaInicio () {
		Boolean existencia = false;
		try {
			u.hardWait(5);
			wait.until(ExpectedConditions.elementToBeClickable(locatorMenuInicio));
			wait.until(ExpectedConditions.elementToBeClickable(tituloPaginaInicio));
			wait.until(ExpectedConditions.elementToBeClickable(home));
			wait.until(ExpectedConditions.elementToBeClickable(XXFC_CARGA_EXCEL));
			wait.until(ExpectedConditions.elementToBeClickable(XXFC_HISTORICO_CARGAS));
			existencia = true;
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se ven los elementos de la p�gina de inicio");
		}
		return existencia;
	}
	
	/**
	 * Valida que se vean los elementos principales de la pagina de carga de 
	 * cat�logo de restricciones de tiendas
	 * 
	 * @return Boolean true si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarPaginaCargaCatRestrTiendas () {
		Boolean existencia = false;
		try {
			wait.until(ExpectedConditions.elementToBeClickable(tituloPaginaCargaArchivo));
			wait.until(ExpectedConditions.elementToBeClickable(chooseFile));
			wait.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(sizeMB)));
			wait.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(tipoArchivo)));
			wait.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(botonCargarArchivo)));
			existencia = true;
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se ven los elementos de la p�gina carga de cat�logo de restricciones tiendas");
		}
		return existencia;
	}
	
	/**
	 * Valida que se haya cargado el archivo y se establezca activo el boton de carga
	 * 
	 * @return Boolean true si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarCargaArchivoRestrTiendas () {
		Boolean existencia = false;
		try {
			u.hardWait(5);
			wait.until(ExpectedConditions.elementToBeClickable(botonCargarArchivo));
			existencia = true;
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se habilito boton de craga de archivo");
		}
		return existencia;
	}
	
	/**
	 * Valida que se vean los elementos principales de la pagina de resumen de registros correctos
	 * 
	 * @return Boolean true si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarPaginaResumenRegistrosCorrectos () {
		Boolean existencia = false;
		try {
			u.hardWait(25);
			wait.until(ExpectedConditions.elementToBeClickable(tituloResumenRegCorrectos));
			wait.until(ExpectedConditions.elementToBeClickable(tituloTablaRegCorrectos));
			wait.until(ExpectedConditions.elementToBeClickable(datosRegistrosTotales));
			wait.until(ExpectedConditions.elementToBeClickable(datosRegsTotalesCorrectos));
			wait.until(ExpectedConditions.elementToBeClickable(datosRegsTotalesConError));
			wait.until(ExpectedConditions.elementToBeClickable(botonCancelarPagRegCorrectos));
			wait.until(ExpectedConditions.elementToBeClickable(botonMostrarErrores));
			wait.until(ExpectedConditions.elementToBeClickable(botonEnviarRDM));
			wait.until(ExpectedConditions.elementToBeClickable(tablaInfoDatosCorrectos));
			wait.until(ExpectedConditions.elementToBeClickable(download));
			existencia = true;
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se ven los elementos de la p�gina resumen de registros correctos");
		}
		return existencia;
	}
	
	/**
	 * Valida que se vean los elementos principales de la pagina de detalles de errores
	 * 
	 * @return Boolean true si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarPaginaDetallesErrores () {
		Boolean existencia = false;
		try {
			u.hardWait(5);
			wait.until(ExpectedConditions.elementToBeClickable(tituloDetalleErrores));
			wait.until(ExpectedConditions.elementToBeClickable(tituloTablaRegIncorrectos));
			wait.until(ExpectedConditions.elementToBeClickable(datosRegistrosDescartados));
			wait.until(ExpectedConditions.elementToBeClickable(botonRegresarPagDetallesErr));
			wait.until(ExpectedConditions.elementToBeClickable(tablaInfoDatosIncorrectos));
			wait.until(ExpectedConditions.elementToBeClickable(download));
			existencia = true;
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se ven los elementos de la p�gina de detalles de errores");
		}
		return existencia;
	}
	
	/**
	 * Valida que se vean los elementos principales de la pagina de confirmacion de tiendas a enviar
	 * 
	 * @return Boolean true si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarPaginaConfirmacionTiendasEnviar () {
		Boolean existencia = false;
		try {
			u.hardWait(5);
			wait.until(ExpectedConditions.elementToBeClickable(tituloConfirmacion));
			wait.until(ExpectedConditions.elementToBeClickable(tituloTablaTiendasEnviar));
			wait.until(ExpectedConditions.elementToBeClickable(botonCancelarPagConfirmacion));
			wait.until(ExpectedConditions.elementToBeClickable(botonConfirmarEnvio));
			wait.until(ExpectedConditions.elementToBeClickable(tablaInfoDatosTiendas));
			existencia = true;
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se ven los elementos de la p�gina de confirmacion de envio");
		}
		return existencia;
	}
	
	/**
	 * Valida que se vean los elementos principales de la pagina de resultados de carga
	 * 
	 * @return Boolean true si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarPaginaResultadosCarga () {
		Boolean existencia = false;
		try {
			u.hardWait(5);
			wait.until(ExpectedConditions.elementToBeClickable(tituloResultadosCarga));
			wait.until(ExpectedConditions.elementToBeClickable(datosFolioCarga));
			wait.until(ExpectedConditions.elementToBeClickable(botonAceptar));
			wait.until(ExpectedConditions.elementToBeClickable(tablaInfoArchivoEnviado));
			wait.until(ExpectedConditions.elementToBeClickable(download));
			existencia = true;
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se ven los elementos de la p�gina de confirmacion de envio");
		}
		return existencia;
	}
	
	/**
	 * Valida que se vean los elementos principales de la pagina de cargas historicas
	 * 
	 * @return Boolean true si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarPaginaCargasHistoricas () {
		Boolean existencia = false;
		try {
			u.hardWait(5);
			wait.until(ExpectedConditions.elementToBeClickable(tituloCargasHistoricas));
			wait.until(ExpectedConditions.elementToBeClickable(menuFiltroCargasH));
			wait.until(ExpectedConditions.elementToBeClickable(tituloArchivosEnviados));
			wait.until(ExpectedConditions.elementToBeClickable(menuFiltroArchivosE));
			existencia = true;
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se ven los elementos de la p�gina de cargas historicas");
		}
		return existencia;
	}
	
	/**
	 * Valida que la primera celda de la tabla no este vacia
	 * 
	 * @return Boolean true si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarVacioPrimeraCelda () {
		Boolean vacio = true;
		try {
			u.hardWait(1);
			WebElement element = this.u.getDriver().findElement(infoPrimeraCelda);
			String info = element.getText();
			if(!info.isEmpty()) {
				vacio = false;
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se pudo obtener dato de celda");
		}
		return vacio;
	}
	
	/**
	 * Valida que la tabla solo tenga tres columnas
	 * 
	 * @return Boolean true si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarNumColTabla () {
		Boolean tresColumnas = false;
		try {
			u.hardWait(5);
			WebElement tabla = this.u.getDriver().findElement(columnasTabla);
			WebElement fila = tabla.findElement(By.tagName("tr"));
			List<WebElement> columnas = fila.findElements(By.tagName("th"));
			if(columnas.size()==3) {
				tresColumnas = true;
			}
			//System.out.println("Data elements: " + elements.size());
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se pudo obtener dato de celda");
		}
		return tresColumnas;
	}
}
