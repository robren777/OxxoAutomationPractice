package WorkFlows.util.SipreBara;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;

import modelo.TestCase;
import utils.ExcelUtil;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;



public class pomCargaArchivos {
	
	SeleniumUtil u;
	TestCase testCase;
	
	private SikuliUtil sikuli = new SikuliUtil();
	private Screen screen = new Screen();	
	
	private WebDriverWait wait;
	private Pattern p1;
	private String s1;
	private String folder = "sipreBara";
	private String mainTab = "";
	private String newTab = "";

	
	public pomCargaArchivos (SeleniumUtil u, TestCase testCase ) {
		this.u = u;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		this.testCase = testCase;


	}

	
	//LOCATORS
	By btnconfig = By.id("products");
	By btncargaArchivos = By.id("mod5_CA");
	By selectTipiArchivo = By.cssSelector("#tiposArchivos_cmb");
	
	By btnbuscaArchivo = By.id("excelFile");
	By btnSubirArchivo = By.cssSelector("a.boton:nth-child(4)");
	
	By tablaDataGriw = By.xpath("//table[contains(@id,'gridDetalleError')]//tr//td[2]");
	By btnCerrar = By.cssSelector("a.boton:nth-child(2)");
	By tablaCamnbios = By.xpath("//table[contains(@id,'gridCambiosMasivos')]//tr//td[2]");
	By mensajeSubirArchivo = By.xpath("/html/body/div[6]");
	
	/**
	 * Ingresa a la pantalla de Carga de archivos
	 * @author alicia.ceja
	 */
	
	public void selectCargaArchivo() {
		
		try {
				u.waitWebElement(btnconfig, 120, 3);
				u.mouseOver(btnconfig);
				testCase.addScreenShotCurrentStep(u, "");
	
				u.hardWait(2);
				u.highLight(btncargaArchivos);
				testCase.addScreenShotCurrentStep(u, "");
				u.hardWait(2);
				u.mouseOverAndClick(btncargaArchivos);
			
		}catch(Exception e) {
			
			System.out.println(" -> " + e);
			throw new AssertionError("Error al entrar a la pantalla de cargar archivos");
			
		}
	}
	
	/**
	 * Selecciona el timpo de carga dependiendo del archivo
	 * que se requiera subir
	 * @param opArchivo
	 */
	
	public void selecTipoArchivo(String opArchivo) {
		
	try {
			u.waitWebElement(selectTipiArchivo, 120, 3);
			u.highLight(selectTipiArchivo);
			u.hardWait(2);
			u.click(selectTipiArchivo);
		
			System.out.println("Opci�n ---> "+opArchivo);
			u.hardWait(2);
			
			Select dropdownSelecTipoArchivo =  new Select (u.getDriver().findElement(selectTipiArchivo));
			dropdownSelecTipoArchivo.selectByVisibleText(opArchivo);
			testCase.addScreenShotCurrentStep(u, "");
		
		}catch(Exception e) {
			
			System.out.println(" -> " + e);
			throw new AssertionError("Error al seleccionar la opci�n ---> "+opArchivo);
			
		}
				
		
	}
	
	/**
	 * Sube el archivo que se especifica en la ruta
	 * @param archivo
	 * @author alicia.ceja
	 */
	
	public void subirArchivo (String archivo) {
		
		try {
			
		
			
			testCase.addBoldTextEvidenceCurrentStep("Click en bot�n subir archivo seleccionando archivo correcto");
			u.highLight(btnbuscaArchivo);
			u.sendKeys(btnbuscaArchivo, archivo);
			testCase.addScreenShotCurrentStep(u, "");
			u.hardWait(2);
			u.click(btnSubirArchivo);
			testCase.addScreenShotCurrentStep(u, "");
			u.hardWait(1);
			testCase.addScreenShotCurrentStep(u, "");
			u.hardWait(1);
			testCase.addScreenShotCurrentStep(u, "");

			u.hardWait(5);
			
			u.scroll();;
			u.hardWait(1);
			//By mensajeCarga = By.cssSelector("div.blockUI:nth-child(17)");
			
			while (u.getDriver().findElements( By.id("div.blockUI:nth-child(17)") ).size() != 0) {
				System.out.println("Subiendo archivo");
			}

			
		}catch(Exception e) {
			
			System.out.println(" -> " + e);
			throw new AssertionError("Error al subir el archivo ---> "+archivo);
			
		}
		
		
	}
	
	/**
	 * Compara la cantidad de registros que hay en la tabla con los de la db
	 * @param path
	 * @param nombreHoja
	 * @author alicia.ceja
	 */
	public void validaRowsDataGridArchivo(String path, String nombreHoja) {
		
		try {
			
			testCase.addTextEvidenceCurrentStep("Compara los registros del archivo con los cargados en la interfaz");


			
			u.waitWebElement(tablaDataGriw, 120, 2);

			XSSFWorkbook workBook = new XSSFWorkbook(path);
			XSSFSheet sheet = workBook.getSheet(nombreHoja);
			int rowCount = sheet.getPhysicalNumberOfRows()-1;
			int registros;
			
			if(nombreHoja=="cambios") {
				List<WebElement> elementsTable = u.getDriver().findElements(tablaCamnbios);
				 registros = elementsTable.size()-1;
			}else {
				List<WebElement> elementsTable = u.getDriver().findElements(tablaDataGriw);
				 registros = elementsTable.size()-1;
			}
			
			testCase.addTextEvidenceCurrentStep("Numero de registros en archivo ---->"+rowCount);
			
			tablaArchivo(path, nombreHoja);
			
			testCase.addTextEvidenceCurrentStep("Numero de registros en interfaz ---->"+registros);
			testCase.addScreenShotCurrentStep(u, "");
			
			workBook.close();
			
			assertEquals(rowCount, registros, "Los registros del archivo cargado no coinciden con "
					+ "los registros de la tabla mostrada en la interfaz");
			
		}catch(Exception e) {

			System.out.println(" -> " + e);
			throw new AssertionError("Error al inspeccionar archivo ---> " + path);
		}
	}
	
	/**
	 * Inspecciona un archivo Excel y agrega en la evidencia 
	 * el nombre de las columnas y sus valores
	 * @param path
	 * @param nombreHoja
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 * @author alicia.ceja
	 */
	public void tablaArchivo (String path, String nombreHoja) throws EncryptedDocumentException, IOException {
		
		try {
			
			ExcelUtil excelutil = new ExcelUtil(path);

			HashMap<String, ArrayList<String>> hashtable  = excelutil.getRows(nombreHoja);

	   		for (Entry<String, ArrayList<String>> map : hashtable.entrySet() ) {
		    	
	       
		       // System.out.println(map.getValue());
		        
		        String NombreColumna =map.getKey().toString();
		        String Valores =map.getValue().toString();

				testCase.addTextEvidenceCurrentStep(NombreColumna);
				testCase.addTextEvidenceCurrentStep(Valores);
				
		        System.out.println(NombreColumna);
		        System.out.println(Valores);

		       // excelutil.close();
		  
		    }
			
		}catch(Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Error al inspeccionar los datos del archivo ---> " + path);
			
		}
	
	}
	
	/**
	 * Valida que los archivos con formato diferente a xlsx o vacios no
	 * se puedan subir
	 * @param docx
	 * @param xlsx
	 * @param txt
	 * @author alicia.ceja
	 */
	
	public void validaArchivosIncorrectos(String docx, String xlsx, String txt) {
		try {
			
			u.scrollToElement(btnSubirArchivo);
			
			testCase.addBoldTextEvidenceCurrentStep("Click en bot�n subir archivo sin seleccionar archivo");
			u.highLight(btnSubirArchivo);
			u.click(btnSubirArchivo);
			u.hardWait(1);
			testCase.addScreenShotCurrentStep(u, "");
			u.hardWait(5);
			
			testCase.addBoldTextEvidenceCurrentStep("Click en bot�n subir archivo seleccionando archivo docx");
			u.highLight(btnbuscaArchivo);
			u.sendKeys(btnbuscaArchivo,docx );
			u.hardWait(2);
			u.click(btnSubirArchivo);
			testCase.addScreenShotCurrentStep(u, "");
			u.hardWait(3);
			
			testCase.addBoldTextEvidenceCurrentStep("Click en bot�n subir archivo seleccionando archivo xls sin formato");
			u.highLight(btnbuscaArchivo);
			u.sendKeys(btnbuscaArchivo,xlsx);
			u.hardWait(2);
			u.click(btnSubirArchivo);
			u.hardWait(1);
			testCase.addScreenShotCurrentStep(u, "");
			u.highLight(btnCerrar);
			u.click(btnCerrar);
			u.hardWait(2);
			
			testCase.addBoldTextEvidenceCurrentStep("Click en bot�n subir archivo seleccionando archivo txt sin vac�o");
			u.highLight(btnbuscaArchivo);
			u.sendKeys(btnbuscaArchivo,txt);
			u.hardWait(2);
			u.click(btnSubirArchivo);
			u.hardWait(1);
			testCase.addScreenShotCurrentStep(u, "");
			u.highLight(btnCerrar);
			u.click(btnCerrar);
			u.hardWait(3);
			
		}catch(Exception e) {
			
			System.out.println(" -> " + e);
			throw new AssertionError("Error al verificar que no se suban archivos incorrectos");
			
		}
		
			
	}
	
	/**
	 * Regresa la fecha actual con el formato dd/MM/yyyy para buscar 
	 * registros en la db 
	 * @return
	 * @author alicia.ceja
	 */
	public String getDate() {
		
		   SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
	        Calendar calendar = Calendar.getInstance();

	        Date dateObj = calendar.getTime();
	        String formattedDate = dtf.format(dateObj);
	        System.out.println(formattedDate);
	        
	        return formattedDate;
	}
	
}
