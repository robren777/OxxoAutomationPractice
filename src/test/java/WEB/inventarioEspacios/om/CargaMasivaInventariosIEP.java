package WEB.inventarioEspacios.om;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.sikuli.script.FindFailed;

import WEB.om.MetodosExtra;
import config.Constants;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class CargaMasivaInventariosIEP {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	PedidosAdministracionIEP pedAdmin;
	BandejaPedidos bp;
	
	
	/**
	 * Localizadores Carga masiva de inventarios
	 ******************************************************************************************/
	
	By xmlFiltros = By.xpath("//mat-accordion/mat-expansion-panel/div");
	By xmlChooseFile = By.xpath("//*[@id=\"formFiltroInventario\"]/div[1]/div/div/input");
	By xmlSubirArchivoBtn = By.xpath("//*[@id=\"formFiltroInventario\"]/div[2]/div/div/button");
	By xmlFechaInicialBtn = By.xpath("//*[@id=\"formFiltroInventario2\"]/div[2]/div[1]/div/mat-form-field/div/div[1]/div[2]/mat-datepicker-toggle/button");
	By xmlFechaFinBtn = By.xpath("//*[@id=\"formFiltroInventario2\"]/div[2]/div[2]/div/mat-form-field/div/div[1]/div[2]/mat-datepicker-toggle/button");
	By xmlBuscarBtn = By.xpath("//*[@id=\"formFiltroInventario2\"]/div[3]/div[2]/button/span[2]");
	
	By xmlChooseFileBtn = By.xpath("//*[@id=\"formFiltroInventario\"]/div[1]/div/div/input");
	
	By xmlTable = By.xpath("//table");
	By xmlNumArchivo = By.xpath("//table/tbody/tr[1]/td[1]");
	By xmlNombreArchivo = By.xpath("//table/tbody/tr[1]/td[2]");
	By xmlTableTr1 = By.xpath("//table/tbody/tr[1]");
	String xmlTableTdText = "//table/tbody/tr[1]/td[%s]";
	By xmlTableTr = By.xpath("//table/tbody/tr");
	By xmlTableTh = By.xpath("//table/thead/tr[1]/th");
	By xmlLogVisible = By.xpath("//table/tbody/tr[1]/td[8]/mat-icon[1][@style='visibility: visible;']");
	By xmlLog = By.xpath("//table/tbody/tr[1]/td[8]/mat-icon[1]");
	By xmlEliminar = By.xpath("//table/tbody/tr[1]/td[8]/mat-icon[2]");
	By xmlMnjConfirm = By.xpath("//h5");
	By xmlConfirmarBtn = By.xpath("//app-dialog-confirm/div[2]/div/div/div/button");
	By xmlMensaje = By.xpath("//snack-bar-container/div/div/simple-snack-bar/span");
	
	String pathFileLoaded = Constants.PATTERN_PATH + File.separator + "IEP" + File.separator + "Carga de inventario.xlsx";
	String pathFileDownloaded = System.getProperty("user.home") + File.separator + "Downloads" + File.separator + "%s";
	
	/**
	 * Constructor
	 ******************************************************************************************/
	
	public CargaMasivaInventariosIEP(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
		this.pedAdmin = new PedidosAdministracionIEP(u, testCase);
		this.bp = new BandejaPedidos(u, testCase);
	}
	
	/**
	 * Metodos Carga masiva de inventarios
	 ******************************************************************************************/
	
	/**
	 * Metodo para validar que se muestren en pantalla los filtros 
	 * @return Retorna true si se visualizan correctamente o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean pantallaFiltros() {
		Boolean exist = false;
		
		try {
			if (me.validarVisualizacion(xmlChooseFile) && me.validarVisualizacion(xmlBuscarBtn) &&
					me.validarVisualizacion(xmlFechaFinBtn) && me.validarVisualizacion(xmlFechaInicialBtn) &&
					me.validarVisualizacion(xmlSubirArchivoBtn)) {
				exist = true;
				u.highLight(xmlFiltros);
				testCase.addScreenShotCurrentStep(u, "Filtros");
				u.unhighLight(xmlFiltros);
			}
		}catch(Exception e) {
			testCase.addScreenShotCurrentStep(u, "Error");
		}
		return exist;
	}
	
	/**
	 * Metodo para dar click en el boton de choose file
	 * @author Maria Due�as
	 */
	public void clickChooseFileBtn() {
		u.highLight(xmlChooseFileBtn);
		testCase.addScreenShotCurrentStep(u, "ChooseFileBtn");
		u.unhighLight(xmlChooseFileBtn);
		WebElement element = u.getDriver().findElement(xmlChooseFileBtn);
		Actions actions = new Actions(u.getDriver());
		actions.moveToElement(element).click().build().perform();
		//u.click(xmlChooseFileBtn);
		testCase.addScreenShotCurrentStep("clickChooseFileBtn");
	}
	
	/**
	 * Metodo para cargar archivo excel
	 * @return Retorna true si se cargo correctamente
	 * @throws FindFailed
	 * @author Maria Due�as
	 */
	public Boolean cargarArchivo() throws FindFailed {
		//Boolean correct = me.selectFile("IEP", "FileTextBoxBlack", "OpenBlack", "Carga de inventario");
		Boolean correct = false;
		try {
			String filePath = Constants.PATTERN_PATH + File.separator + "IEP" + File.separator + "Carga de inventario.xlsx";
			u.highLight(xmlChooseFile);
			testCase.addScreenShotCurrentStep(u, "cargarArchivo");
			u.sendKeys(xmlChooseFile, filePath);
			testCase.addScreenShotCurrentStep(u, "cargarArchivo");
			u.highLight(xmlChooseFile);
			correct = true;
		}catch(Exception e) {
			testCase.addScreenShotCurrentStep(u, "Error");
		}
		
		
		return correct;
	}
	
	/**
	 * Metodo para dar click en el boton Subir Archivo 
	 * @author Maria Due�as
	 */
	public void clickSubirArchivoBtn() {
		u.highLight(xmlSubirArchivoBtn);
		testCase.addScreenShotCurrentStep(u, "SubirArchivoBtn");
		u.unhighLight(xmlSubirArchivoBtn);
		u.click(xmlSubirArchivoBtn);
		testCase.addScreenShotCurrentStep(u, "clickSubirArchivoBtn");
	}
	
	/**
	 * Metodo para saber el numero del ultimo archivo excel en el sistema
	 * @return Retorna el numero de registro del archivo
	 * @author Maria Due�as
	 */
	public String getLastFile() {
		String num = "";
		
		List <WebElement> tablaTr = u.getDriver().findElements(xmlTableTr);
		Integer row_count = tablaTr.size();
		
		if (row_count >= 1) {
			num = u.getText(xmlNumArchivo).trim();
		}
		
		return num;
	}
	
	/**
	 * Metodo para validar que muestre la informacion de Archivo, fechas, conteo, porcentaje y estatus 
	 * de la carga del archivo Excel
	 * @return Retorna true si se muestra todo la informaci�n o false en caso de que exista un campo vac�o
	 * @author Mar�a Due�as
	 */
	public Boolean validarDatosExcelCargado() {
		Boolean correct = false;
		
		List <WebElement> tablaTh = u.getDriver().findElements(xmlTableTh);
		Integer col_count = tablaTh.size();
		List <WebElement> tablaTr = u.getDriver().findElements(xmlTableTr);
		Integer row_count = tablaTr.size();
		
		if (row_count >= 1 && col_count == 8) {
			me.scrollToElement(xmlTable);
			u.highLight(xmlTableTr1);
			testCase.addScreenShotCurrentStep(u, "RegistroExcel");
			u.unhighLight(xmlTableTr1);
			
			for (int i = 1; i <= 8; i++) {
				
				By td = By.xpath(String.format(xmlTableTdText, Integer.toString(i)));
				String text = u.getText(td).trim();
				
				if (i != 4 || i != 8) {
					
					if(text.equals("")) {
						break;
					}else {
						correct = true;
					}
					
				} else {
					u.highLight(td);
					testCase.addScreenShotCurrentStep(u, "Campo");
					u.unhighLight(td);
				}
				
			}
			
		}
		
		return correct;
	}
	
	/**
	 * Metodo para esperar que termine de cargar el los datos del archivo adjunto
	 * @author Mar�a Due�as
	 */
	public Boolean esperarCarga() {
		
		Boolean exist = false;
		
		for(int i = 0; i <= 10; i++) {
			u.hardWait(10);
			By td = By.xpath(String.format(xmlTableTdText, Integer.toString(7)));
			String text = u.getText(td).trim();
					
			if(text.equals("Procesado") || text.equals("Proceso con error") || text.equals("Validado con error")
					|| text.equals("Cancelado")) {
				u.highLight(xmlTableTr1);
				testCase.addScreenShotCurrentStep(u, "RegistroExcel");
				u.unhighLight(xmlTableTr1);
				exist = true;
				break;
			}
		}
		
		
		return exist;
	}
	
	/**
	 * Metodo para validar si el archivo adjunto tiene errores
	 * @author Maria Due�as
	 * @return Retorna true si hay errores en el archivo adjunto
	 */
	public Boolean existenErrores() {
		
		Boolean exist = false;
		
		By td = By.xpath(String.format(xmlTableTdText, Integer.toString(7)));
		String text = u.getText(td).trim();
					
		if(text.contains("error")) {
			exist = true;
		} else {
			testCase.addTextEvidenceCurrentStep("El archivo adjunto no contiene errores");
			u.highLight(td);
			testCase.addScreenShotCurrentStep(u, "Estatus");
			u.unhighLight(td);
		}
		
		return exist;
		
	}
	
	/**
	 * Metodo para validar que se genere el archivo Log en caso de haber errore
	 * @return Retorna true si se creo correctamente el archivo
	 * @author Mar�a Due�as
	 */
	public Boolean validarLog() {
		Boolean exist = false;
		
		try {
			if (u.getDriver().findElement(xmlLogVisible).isDisplayed()) {
				u.highLight(xmlLog);
				testCase.addTextEvidenceCurrentStep("Se gener� correctamente el archivo Log");
				testCase.addScreenShotCurrentStep(u, "Log");
				u.unhighLight(xmlLog);
				exist = true;
			}
		}catch(Exception e) {
			testCase.addScreenShotCurrentStep(u, "Error");
		}
		
		
		return exist;
	}
	
	/**
	 * Metodo para descargar el archivo Log
	 * @author Maria Due�as
	 */
	public void descargarLog() {
		u.highLight(xmlLog);
		testCase.addScreenShotCurrentStep(u, "descargarLog");
		u.unhighLight(xmlLog);
		u.click(xmlLog);
		me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		testCase.addScreenShotCurrentStep("descargarLog");
	}
	
	/**
	 * Metodo para crear fichero
	 * @return Retorna fichero
	 * @author Maria Due�as
	 */
	public File createFicheroLoaded() {
		File fichero = new File(pathFileLoaded);
		return fichero;
	}
	
	/**
	 * Metodo para crear fichero en Downloads
	 * @return Retorna fichero
	 * @author Maria Due�as
	 */
	public File createFicheroDownloaded(String fd) {
		String path = String.format(pathFileDownloaded, fd);
		File fichero = new File(path);
		return fichero;
	}
	
	/**
	 * Metodo para obtener el nombre de la carga
	 * @return Retorna nombre
	 * @author Maria Due�as
	 */
	public String getNombreArchivo() {
		String fd = u.getText(xmlNombreArchivo).trim();
		return fd;
	}
	
	/**
	 * Metodo para crear archivo Excel
	 * @return Retorna la primer pagina del archivo excel
	 * @throws IOException
	 * @author Maria Due�as
	 */
	public Sheet createFicheroLoaded2() throws IOException {
		FileInputStream inputStream1 = new FileInputStream(new File(pathFileLoaded));
        Workbook workbook1 = new XSSFWorkbook(inputStream1);
        Sheet firstSheetFL = workbook1.getSheetAt(0);
        return firstSheetFL;
	}
	
	/**
	 * Metodo para crear archivo Excel
	 * @return Retorna la primer pagina del archivo excel
	 * @throws IOException
	 * @author Maria Due�as
	 */
	public Sheet createFicheroDownloaded2(String fd) throws IOException {
		String path = String.format(pathFileDownloaded, fd);
		FileInputStream inputStream2 = new FileInputStream(new File(path));
        Workbook workbook2 = new XSSFWorkbook(inputStream2);
        Sheet firstSheet = workbook2.getSheetAt(0);
        return firstSheet;
	}
	
	/**
	 * Metodo para validar que los errores en el log sean correctos
	 * @param rowFL
	 * @param fd
	 * @return Retorna true si se encuentra el error en el log o false en caso contrario
	 * @throws IOException
	 * @author Maria Due�as
	 */
	public Boolean validarErrores(Row rowFL, String fd) throws IOException {
		Boolean exist = false;
		
        Sheet firstSheetFD = createFicheroDownloaded2(fd);
        
        String plaza_cve = rowFL.getCell(0).toString();
        String plaza = rowFL.getCell(1).toString(); 
        String subplaza = rowFL.getCell(2).toString();
        String cr_tienda = rowFL.getCell(3).toString();
        String tienda = rowFL.getCell(4).toString();
        String id_espacio = rowFL.getCell(5).toString();
        String espacio = rowFL.getCell(6).toString();
        String cantidad = rowFL.getCell(7).toString();
        
        for(int i = 1; i <= firstSheetFD.getLastRowNum(); i++) {
        	if (plaza_cve.equals(bp.datoCelda(firstSheetFD, i, 0)) && plaza.equals(bp.datoCelda(firstSheetFD, i, 1))
        			&& subplaza.equals(bp.datoCelda(firstSheetFD, i, 2)) && cr_tienda.equals(bp.datoCelda(firstSheetFD, i, 3))
        			&& tienda.equals(bp.datoCelda(firstSheetFD, i, 4)) && id_espacio.equals(bp.datoCelda(firstSheetFD, i, 5))
        			&& espacio.equals(bp.datoCelda(firstSheetFD, i, 6)) && cantidad.equals(bp.datoCelda(firstSheetFD, i, 7))) {
        		exist = true;
        		break;
        	}
        }
        
		return exist;
	}
	
	/**
	 * Metodo para validar que las celdas de la columna Error o Reglas no contenga errores sql o de codigo
	 * @param fd
	 * @return Retorna true si encuentra errores sql o de codigo
	 * @author Maria Due�as
	 * @throws IOException 
	 */
	public Boolean validarErrorReglas(String fd) throws IOException {
		Boolean exist = false;
		
		Sheet firstSheetFD = createFicheroDownloaded2(fd);
		
		String errores [] = {"sql", "error", "err", "code", "select", "from", "where", "ora", 
				"incorrect syntax", "not find", "if", "else", "while", "do", "switch", "for", "break"};
		
		
		for(int i = 1; i <= firstSheetFD.getLastRowNum(); i++) {
			for (String es: errores) {
				if(bp.datoCelda(firstSheetFD, i, 8).toLowerCase().contains(es) 
						|| bp.datoCelda(firstSheetFD, i, 9).toLowerCase().contains(es)) {
					exist = true;
					break;
				}
			}
		}
		
		return exist;
	}
	
	/**
	 * Metodo para dar click en el boton eliminar
	 * @author Maria Due�as
	 */
	public void clickEliminar() {
		u.highLight(xmlEliminar);
		testCase.addScreenShotCurrentStep(u, "eliminar");
		u.unhighLight(xmlEliminar);
		u.click(xmlEliminar);
		testCase.addScreenShotCurrentStep(u, "clickEliminar");
		
	}
	
	/**
	 * Metodo para validar que aparezca la ventana de confirmacion
	 * @return Retorna true si muestra correctamente el mensaje
	 * @author Maria Due�as
	 */
	public Boolean confirmarEliminacion() {
		Boolean correct = false;
		String mensaje = "";
	
		me.explicitWaitVisibilityOfElement(xmlMnjConfirm, Duration.ofSeconds(10));
		if (u.getDriver().findElement(xmlMnjConfirm).isDisplayed()) {
			
			mensaje = u.getText(xmlMnjConfirm).trim();
			
			u.highLight(xmlMnjConfirm);
			testCase.addScreenShotCurrentStep(u, "Mensaje");
			u.unhighLight(xmlMnjConfirm);
			
			if(mensaje.equals("�Esta seguro que desea eliminar el proceso?")) {
				correct = true;
			}
		}
		
		return correct;
	}
	
	/**
	 * Metodo para dar click en el boton confirmar
	 * @author Maria Due�as
	 */
	public void clickConfirmarBtn() {
		u.highLight(xmlConfirmarBtn);
		testCase.addScreenShotCurrentStep(u, "ConfirmarBtn");
		u.unhighLight(xmlConfirmarBtn);
		u.click(xmlConfirmarBtn);
		testCase.addScreenShotCurrentStep(u, "clickConfirmarBtn");
		
	}
	
	/**
	 * Metodo para validar que se muestre mensaje despues de eliminar un archivo
	 * @return Retorna true si se muestra correctamente o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean mensajeConfirmacion() {
		Boolean correct = false;
		String mensaje = "";
	
		me.explicitWaitVisibilityOfElement(xmlMensaje, Duration.ofSeconds(5));
		if (u.getDriver().findElement(xmlMensaje).isDisplayed()) {
			
			mensaje = u.getText(xmlMensaje);
			
			u.highLight(xmlMensaje);
			testCase.addScreenShotCurrentStep(u, "Mensaje");
			u.unhighLight(xmlMensaje);
			
			if(mensaje.equals("Se ha eliminado correctamente el registro")) {
				correct = true;
			}
		}
		
		return correct;
	}
}
