package RMS.ProcEndtoEnd.om;

import java.io.File;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Pattern;

import modelo.TestCase;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.SeleniumUtil;

public class EndtoEnd {

    /** Utilidades **/
    private SeleniumUtil u;
    private TestCase testCase;
    private WebDriverWait wait;
    private SikuliUtil sk;
    private WebDriver driver;
    String pathImg;
	File FolderPath;

    /** Constantes **/
    private static final String URL_RMC = "https://fcportalqa.femcom.net:8443/BO_Variedoxxo";

    /** Objetos UI **/
    private By dropdw = By.xpath("//a[contains(@class, 'x18v') and text()='Usuario']");
    private By opcionConexion  = By.id("pt1:defGlobalMenu:0:i1:0:i2:1:cmi2");
    
   // Login 
    private By inputUsuario = By.id("pt1:r1:0:login_it1::content"); 
    private By inputContrasena = By.id("pt1:r1:0:login_it2::content"); 
    //private By inputContrasena = By.name("pt1:r1:0:login_it2");
    By btnConexion = By.xpath("//span[text()='Conexión']");
    
    //Tarea
     By btnTarea = By.id("pt1:eachSidebarItem:2:l1::icon"); 
     By btnOrders = By.id("pt1:showMenuPanelDynRegion:1:taskItr:2:l9::text"); 
     By btnCO = By.id("pt1:showMenuPanelDynRegion:1:taskItr:0:l6::text"); 
     //By inputArchivo = By.xpath("//input[@id='pt1:contentAreaReg:0:tabrg3:2:ap1:if1::content']");
      By inputArchivo = By.xpath("//input[@id='pt1:contentAreaReg:0:tabrg1:2:if1::content']");
     //By inputArchivo = By.xpath("//input[@type='file' and contains(@id,'if1::content')]");
     //By botonUpload = By.xpath("//a[span[text()='Upload']] | //button[span[text()='Upload']]");
     By botonUpload = By.xpath("//a[@role='button']//span[contains(text(),'Upload')]\r\n"
     		+ "");
     
    
     //Manager Orders
     By manageOrders = By.id("pt1:showMenuPanelDynRegion:1:taskItr:1:l6::text");
     By OrderNumero = By.xpath("//input[@id='pt1:contentAreaReg:0:tabrg1:2:pt1:ap1:qryId2:val00::content']");
     By btnSearch = By.xpath("//span[@class='xfx' and (text()='Buscar' or text()='Search')]/ancestor::a[1]");
     By btnActions = By.xpath("//a[contains(text(),'Actions') or contains(text(),'Acciones')]");
     By opcionCrear = By.xpath("//td[@class='x19c' and (normalize-space(text())='Create From Existing' or normalize-space(text())='Crear desde existente')]");
     private By selectNumero = By.xpath("//span[contains(@id, 'resId1') and normalize-space(text())='");
     
     
    //CreateOrder
     private By inputSuppliersite = By.id("pt1:contentAreaReg:0:tabrg1:2:supplierId::content"); 
     By btnOrderType = By.id("pt1:contentAreaReg:0:tabrg4:2:ordTypeId::content"); 
     private By selectOrderType = By.xpath("//select[@title='N/B']");
     private By inputOrdertype = By.id("pt1:contentAreaReg:0:tabrg1:2:ordTypeId::content");
     private By inputOrdertypeARN = By.xpath("//select[@id='pt1:contentAreaReg:0:tabrg1:2:ordTypeId::content']\r\n");
     
     
     //private By dropdwNB = By.xpath("//span[@class='x1z']//select[@title='N/B']");
     private By dropdwNB = By.xpath("//select[contains(@title, 'N/B') or contains(@title, 'No básico')]");
     //private By inputFechaInc = By.xpath("//input[contains(@id, 'notBeforeDate') and @placeholder='m/d/yy']");
     private By inputFechaInc = By.xpath("//input[contains(@id, 'notBeforeDate::content')]\r\n"
     		+ "");
     private By inputFechaFin1 = By.xpath("//a[contains(@id, 'notAfterDate') and @title='Select Date']");
     By iconoCalendarioFin = By.xpath("//a[contains(@id, 'notAfterDate') and @title='Select Date']");
     By btnCalendario = By.id("pt1:contentAreaReg:0:tabrg1:2:notAfterDate::pop"); 
     private By inputFechaFin3 = By.xpath("//input[contains(@id, 'notAfterDate') and contains(@id, '::content')]\r\n"
     + "");
    //private By inputFechaFin3 = By.xpath("//input[@id='pt1:contentAreaReg:0:tabrg1:2:notAfterDate::content']");		
     By chkSbc1 = By.xpath("//input[@type='checkbox' and contains(@id, 'sbc1::content')]");
     By selectLocation = By.xpath("//select[contains(@id, 'soc1::content')]");
     private By iconoBuscarLocation = By.xpath("//a[contains(@id, 'locationId::lovIconId') and @title='Search and Select: Location']");
     private By inputBuscarLocation = By.xpath("//input[contains(@id, 'locationId::content') and @name='pt1:contentAreaReg:0:tabrg1:2:locationId']\r\n"
     		+ "");
     //private By inputBuscarLocation = By.id("pt1:contentAreaReg:0:tabrg1:2:locationId::lovIconId");
     private By botonOkPopup = By.xpath("//a[@role='button']//span[text()='OK']");
     private By SelectTienda = By.xpath("//select[@id='pt1:contentAreaReg:0:tabrg1:2:soc1::content']");
     

     
     //Abre la orden
    
     private By iconoCalendario = By.xpath("//a[contains(@id, 'id3::glyph') and (contains(@title, 'Seleccionar') or contains(@title, 'Select'))]\r\n"
     		+ "");
     private By iconoOrderContext = By.xpath("//input[contains(@id, 'poTypeId::content')]\r\n"
     		+ "");
     
     // Seleccionar MoreAction
     private By btnMoreAction = By.xpath("//a[@id='pt1:contentAreaReg:0:tabrg2:2:pt1:ap1:b3::popEl']");
     private By OpcWorkOrders = By.xpath("//td[@class='x19i' and normalize-space()='Work Orders']"); 
     private By btnScroll = By.xpath("//td[@id='pt1:contentAreaReg:0:tabrg2:2:pt1:ap1:m2::sDwnBg' and contains(@class, 'x257')]");
     By rowLocator = By.xpath("//tr[td[text()='Other Attributes']]");
     By fila = By.id("pt1:contentAreaReg:0:tabrg2:2:pt1:ap1:j_id__ctru273pc8:2:j_id__ctru274pc8");
     By lovIcon = By.id("pt1:contentAreaReg:0:tabrg3:2:mr1:extEnt1:0:grpset1:0:grp1:0:attr1:0:job2::lovIconId");
     By filaPuntoTransbordo = By.xpath("//td[span[text()='PUNTO DE TRANSBORDO']]");
     By btnGuardarCerrar = By.xpath("//a[span[text()='Guardar y cerrar'] or span[text()='Save and Close']]");
     By filaUbicacion = By.xpath("//div[@id='pt1:contentAreaReg:0:tabrg2:2:pt1:ap1:cfa::ScrollBox']//td[contains(text(), 'Ubicacion PT Doble Pivote')]");
     //private By glassPane = By.className("AFModalGlassPane");
     private By btnDetalles = By.xpath("//a[.//span[normalize-space()='Details' or normalize-space()='Detalles']]");
     
     //Abrir Order Distribution
     //private By btnAction = By.xpath("//div[@class='x1a6']//a[contains(text(),'Acciones')]");
     private By btnAction = By.xpath("//div[@aria-label='Acciones']");
     //a[contains(@class, 'x18v') and contains(text(), 'Actions')]
     private By btnadd = By.xpath("//tr[@role='menuitem']//td[normalize-space(text())='Add']\r\n"
     		+ "");
     private By btnAdd = By.xpath("//tr[@role='menuitem' and .//td[text()='Add']]");
     private By inputItem = By.xpath("//input[@title='7501064120268']\r\n");
     		
  
     private By btnArt1 = By.xpath("//select[@title='Artículo']/option[@value='0']");
     private By inputArt = By.id("pt1:contentAreaReg:0:tabrg3:2:pt1:app1:at1:_ATp:itemLOV:it1::content");
     private By inputArt78 = By.id("pt1:contentAreaReg:0:tabrg4:2:pt1:app1:at1:_ATp:itemLOV:it1::content");
     private By inputArt2 = By.id("pt1:contentAreaReg:0:tabrg4:2:pt1:app1:at1:_ATp:itemLOV:it1::content");
     private By inputCant = By.xpath("//input[@id='pt1:contentAreaReg:0:tabrg3:2:pt1:app1:at1:_ATp:it7::content']");
     private By inputCant78 = By.xpath("//input[@id='pt1:contentAreaReg:0:tabrg4:2:pt1:app1:at1:_ATp:it7::content']\r\n"
     		+ "");
     
     private By inputCant2 = By.xpath("//input[@id='pt1:contentAreaReg:0:tabrg4:2:pt1:app1:at1:_ATp:it7::content']");
     
     
     private By buttonOk1 = By.id("pt1:contentAreaReg:0:tabrg3:2:pt1:app1:at1:_ATp:b5");
     private By buttonOk78 = By.id("pt1:contentAreaReg:0:tabrg4:2:pt1:app1:at1:_ATp:b5");
     private By buttonOk3 = By.id("pt1:contentAreaReg:0:tabrg4:2:pt1:app1:at1:_ATp:b5");                              
     //private By buttonSaveClose1 = By.xpath("//a[@role='button' and (.//span[text()='Guardar y cerrar'] or .//span[text()='Save and Close'])]");
     private By buttonSaveClose1 = By.xpath("//a[@role='button' and (.//span[text()='Guardar y cerrar'] or .//span[text()='Save and Close'])]");
     //private By buttonSaveClose1 = By.xpath("//span[@class='xfx' and (text()='Guardar y cerrar' or text()='Save and Close')]");
     //private By buttonSaveClose1 = By.xpath("//span[contains(text(),'Guardar y cerrar') or contains(text(),'Save and Close')]");
     private By buttonSaveClose2 = By.xpath("//span[text()='Guardar y cerrar' or text()='Save and Close'] ");
     private By buttonSaveClose3 = By.xpath("//span[text()='Guardar y cerrar' or text()='Save and Close'] ");
     private By buttonSubmit = By.id("pt1:contentAreaReg:0:tabrg2:2:pt1:ap1:submitButton");
     private By botonYeSiPopup1 = By.xpath("//span[text()='Sí' or text()='Yes']");
     private By botonApprove = By.xpath("//div[@id='pt1:contentAreaReg:0:tabrg2:2:pt1:ap1:approveButton']");
     private By botonYeSiPopup2 = By.xpath("//a[@role='button']//span[text()='Sí']");
     private By botonYeSiPopup3 = By.xpath("//div[@id='pt1:contentAreaReg:0:tabrg2:2:pt1:ap1:d3_yes']//span[normalize-space()='Sí']");
     //private By botonSaveandClose = By.xpath("//a[@role='button' and .//span[normalize-space()='Guardar y cerrar']]");
     //private By botonSaveandClose = By.xpath("//a[span[normalize-space()='Guardar y cerrar']]");
     private By botonSaveandClose = By.id("pt1:contentAreaReg:0:tabrg2:2:pt1:ap1:APscl");
     private By botonSaveandClose78 = By.id ("pt1:contentAreaReg:0:tabrg2:2:pt1:ap1:APscl");
     private By buttonSaveClose31 = By.id("pt1:contentAreaReg:0:tabrg3:2:pt1:mR:APscl");
     private By buttonSaveClose32 = By.id("pt1:contentAreaReg:0:tabrg2:2:pt1:ap1:APscl");
     private By buttonSubmit31 = By.id("pt1:contentAreaReg:0:tabrg1:2:pt1:ap1:APb");
                                            
     
     // Carga Masiva
     private By SelectOxxo = By.xpath("//span[starts-with(@id, 'pt1:showMenuPanelDynRegion') and contains(@id, ':l9::text') and text()='OXXO']");
     //private By SelectorOxxoPurchase = By.xpath("//span[normalize-space()='Oxxo Purchase Orders']");
     private By SelectorOxxoPurchase = By.xpath("//a[span[normalize-space(text())='Ordenes de compra Oxxo' or normalize-space(text())='Oxxo Purchase Orders']]\r\n"
     		+ "");
     By inputArchivo3 = By.xpath("//input[@type='file' and contains(@id, 'if1::content')]");
     private By SelectReviewStatus = By.id("pt1:contentAreaReg:0:tabrg1:2:j_id__ctru16pc7");
     private By BtnOrderlead = By.id("pt1:contentAreaReg:0:tabrg1:3:b1");
      
     By uploadOrdersLink = By.xpath("//a[span[text()='Upload Orders from File']]");
     By selectByClass = By.xpath("//select[contains(@class, 'x2h') and option[text()='Purchase Order']]");
     //private By btnSelectArch = By.xpath("//input[@id='pt1:contentAreaReg:0:tabrg1:2:ap1:if1::content']\r\n"
     //		+ "");
     By inputArchivo2 = By.id("pt1:contentAreaReg:0:tabrg1:2:ap1:if1::content");
     
     private By SelectRMSTables = By.xpath("//select[@id='pt1:contentAreaReg:0:tabrg3:2:ap1:soc1::content']/option[text()='RMS Tables']\r\n"
     		+ "");
     private By SelectStagingTable = By.xpath("//select[@id='pt1:contentAreaReg:0:tabrg3:2:ap1:soc1::content']/option[text()='Staging Tables']\r\n"
     		+ "");
     By spanContenedor = By.id("pt1:contentAreaReg:0:tabrg1:2:ap1:soc1");
     By dropdownLocator = By.id("pt1:contentAreaReg:0:tabrg1:2:ap1:soc1::content");
     By botonOk = By.xpath("//a[span[text()='OK']] | //button[span[text()='OK']]");
        
     
     
     private By iconoCalendario4 = By.xpath("//a[contains(@id, 'id3::glyph') and (contains(@title, 'Seleccionar') or contains(@title, 'Select'))]");
     
    		 
    // Constructor
    public EndtoEnd(SeleniumUtil u, TestCase testCase) {
        this.u = u;
        this.testCase = testCase;
        this.driver = u.getDriver();
        this.wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(60));
        this.sk = new SikuliUtil();
        this.pathImg = sk.PatternPath("RMSS");
		this.FolderPath = new File(pathImg);
    }

    /** Logs **/
    public static void logP(String text) {
        System.out.println("===> " + text);
    }

    /**
 	 * Log espera
 	 *
 	 * @author Isabel Rivera
 	 * @throws Exception
 	 * @date 01/07/2025
 	 */ 
    public void waitUntilVisible(By element) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        } catch (TimeoutException e) {
            throw new AssertionError("Elemento no visible dentro del tiempo esperado: " + element);
        }
    }
    /**
 	 * Log espera
 	 *
 	 * @author Isabel Rivera
 	 * @throws Exception
 	 * @date 01/07/2025
 	 * 
 	 * */
    
    public static void main(String[] args) {
		// TODO Auto-generated method stub

		PasswordUtil.generateEncryptPass();
		
		System.out.println(PasswordUtil.decryptPassword("5CE968754E38ACE80E7463B79522CBC4"));
		


	}

    
    /**
 	 * Método para ir a la URL de RMC
 	 *
 	 * @author Isabel Rivera
 	 * @throws Exception
 	 * @date 01/07/2025
 	 */ 
   
    public void goToRMC() {
    	u.get(URL_RMC);
        u.hardWait(1);
        u.getDriver().manage().window().maximize();
        testCase.addScreenShotCurrentStep("Pantalla inicial RMC");
        
    }

    
    
    /**
	 *  Metodo para Click Dropdown de Usuario
	 *
	 * @author Isabel Rivera
	 * @throws Exception
	 * @date 03/07/2025
	 */    
   
    public By getDropdw() {
        return dropdw;
    }
   
    
    /**
	 *  Metodo para Click opcion de conexio 
	 *
	 * @author Isabel Rivera
	 * @throws Exception
	 * @date 03/07/2025
	 */    
    
    public By getOpcionConexion() {
        return opcionConexion;
    }
    
    /**
	 *  Metodo generico de espera de tiempos 
	 *
	 * @author Isabel Rivera
	 * @throws Exception
	 * @date 03/07/2025
	 */     
    
    public WebElement waitForElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
  
    /**
	 *  Metodo para login
	 *
	 * @author Isabel Rivera
	 * @throws Exception
	 * @date 03/07/2025
	 */    
    
    public By getInputUsuario() {
        return inputUsuario;
      
    }
   
    
    
    /**
	 *  Metodo para ingresar la contraseña
	 *
	 * @author Isabel Rivera
	 * @throws Exception
	 * @date 03/07/2025
	 */    
    public By getInputContrasena() {
        return inputContrasena;
        
    }
    
    
    /**
	 *  Metodo para Click en la conexion 
	 *
	 * @author Isabel Rivera
	 * @throws Exception
	 * @date 03/07/2025
	 */    
    
    public void clickConexion() {
		clickOption("Conexion", btnConexion);
		testCase.addScreenShotCurrentStep("Pantalla inicial RMC");
	}
    
    
    
    
    /**
	 *  Metodo para que seleccione la opcion tarea
	 *
	 * @author Isabel Rivera
	 * @throws Exception
	 * @date 03/07/2025
	 */    
     public void clickTarea() {
		clickOption("Tarea", btnTarea);
		testCase.addScreenShotCurrentStep("clickTarea");
		
	}
    
     
     
     /**
 	 *  Metodo para seleccione la opcion ordenes
 	 * @author Isabel Rivera
 	 * @throws Exception
 	 * @date 03/07/2025
 	 */    
     public void clickOrders() {
 		clickOption("Orders", btnOrders );
 		testCase.addScreenShotCurrentStep("clickOrders");
 		
 	}
     
     /**
 	 *  Metodo que realiza el clic en Crear orden 
 	 *
 	 * @author Isabel Rivera
 	 * @throws Exception
 	 * @date 03/07/2025
 	 */     

     public void clickCrearOrden() {
  		clickOption("Orders", btnCO);
  		testCase.addScreenShotCurrentStep("clickCrearOrden");
  		
     }
     
   
     
    /*
	 * Inicia la creación de OC
	 *
	 * @author Isabel Rivera
	 * @throws Exception
	 * @date 01/07/2025
	 */
       
     public void ingresarSupplierSite() {
    	    WebElement input = wait.until(ExpectedConditions.elementToBeClickable(inputSuppliersite));
    	    input.clear();
    	    input.sendKeys("66861");
    	    testCase.addScreenShotCurrentStep("ingresarSupplierSite");
    	    u.hardWait(3);
    	    input.sendKeys(Keys.TAB); 
    	    u.hardWait(1); 
    	}
     
     
     /**
 	 *  Metodo para selccionar order type
 	 *
 	 * @author Isabel Rivera
 	 * @throws Exception
 	 * @date 03/07/2025
 	 */    
     
     public void clickOrderType() {
 		clickOption("Conexion", btnOrderType);
 		testCase.addScreenShotCurrentStep("clickOrderType");
 		
 		
     }

    

     /*
 	 * Selecciona campo OrderType
 	 *
 	 * @author Isabel Rivera
 	 * @throws Exception
 	 * @date 01/07/2025
 	 */
    
     public void seleccionarOrderTypeNB() {
    	    try {
    	        
    	        WebElement orderTypeSelect = wait.until(ExpectedConditions.presenceOfElementLocated(
    	            By.xpath("//select[contains(@title, 'N/B') or contains(@title, 'No básico')]")
    	        ));

    	        
    	        Select select = new Select(orderTypeSelect);

    	       
    	        try {
    	            select.selectByVisibleText("N/B"); 
    	        } catch (NoSuchElementException e) {
    	            select.selectByVisibleText("No básico"); 
    	        }

    	        testCase.addScreenShotCurrentStep("Order Type N/B o No básico seleccionado correctamente");

    	    } catch (Exception e) {
    	        testCase.addScreenShotCurrentStep("Error al seleccionar Order Type");
    	        throw new RuntimeException("No se pudo seleccionar el tipo de orden N/B o No básico", e);
    	    }
    	}
   
     
     /*
  	 * Selecciona la opcion de ARB 
  	 * @author Isabel Rivera
  	 * @throws Exception
  	 * @date 01/07/2025
  	 */
     
     public void seleccionarTipoOrdenARB() {
    	    try {
    	      
    	        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
    	            By.xpath("//select[contains(@id, 'ordTypeId::content')]")
    	        ));

    	        Select select = new Select(dropdown);

    	        try {
    	            select.selectByVisibleText("ARB");
    	            testCase.addScreenShotCurrentStep("Opción 'ARB' seleccionada correctamente.");
    	            System.out.println(" Opción 'ARB' seleccionada correctamente.");
    	        } catch (NoSuchElementException e1) {
    	            try {
    	                select.selectByVisibleText("OAB");
    	                System.out.println(" Opción 'OAB' seleccionada correctamente.");
    	                testCase.addScreenShotCurrentStep("seleccionarTipoOrdenARB");
    	            } catch (NoSuchElementException e2) {
    	                System.out.println(" No se encontró la opción 'ARB' ni 'OAB' en el dropdown.");
    	            }
    	        }

    	    } catch (Exception e) {
    	        System.out.println(" Error al interactuar con el dropdown: " + e.getMessage());
    	    }
    	}

    
     /**
  	 *  Metodo para agregar fecha actual  
  	 *
  	 * @author Isabel Rivera
  	 * @throws Exception
  	 * @date 18/07/2025
  	 */    
     
     public void ingresarFechaHoy() {
    	    String fechaHoyInc = LocalDate.now().format(DateTimeFormatter.ofPattern("d/M/yyyy"));   	    
    	    WebElement input = wait.until(ExpectedConditions.elementToBeClickable(inputFechaInc));
    	    input.clear();
    	    input.sendKeys(fechaHoyInc);
    	    testCase.addScreenShotCurrentStep("Fecha actual ingresada: " + fechaHoyInc);
    	    input.sendKeys(Keys.TAB);
    	    testCase.addScreenShotCurrentStep("ingresarFechaHoy");
    	}
      
     
     /**
   	 *  Metodo para agregar una fecha donde suma dos días despues a la de hoy 
   	 *
   	 * @author Isabel Rivera
   	 * @throws Exception
   	 * @date 02/07/2025
   	 */    
       
     public void ingresarFechaFinMasDosDias() {
    	    String fecha = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("d/M/yyyy"));
    	    testCase.addScreenShotCurrentStep("MAS DOS DIAS");
    	    u.hardWait(2);
    	    WebElement input = wait.until(ExpectedConditions.elementToBeClickable(inputFechaFin3));
    	    testCase.addScreenShotCurrentStep("Fecha Not After (+2 días): " + fecha);
    	    input.clear();
    	    input.sendKeys(fecha);
    	    u.hardWait(2);
    	    input.sendKeys(Keys.TAB); 
    	   
    	}
     
     
     
     /**
    	 *  Metodo para seelccionar uncheckbox
    	 * @author Isabel Rivera
    	 * @throws Exception
    	 * @date 02/07/2025
    	 */    
     
     public void marcarCheckboxSbc1() {
    	    WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(chkSbc1));
    	    if (!checkbox.isSelected()) {
    	        checkbox.click();
    	    }
    	    testCase.addScreenShotCurrentStep("Checkbox SBC1 marcado");
    	}
     
     
     
     /**
 	 *  Metodo para agregar el tipo de orden este es para CEDIS
 	 *
 	 * @author Isabel Rivera
 	 * @throws Exception
 	 * @date 15/07/2025
 	 */     
     public void seleccionarUbicacionPorNombre(String opcionEsp, String opcionEng) {
    	    WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(
    	        By.xpath("//select[contains(@id,'soc1::content')]")
    	    ));
    	    Select select = new Select(dropdown);
    	    try {
    	        select.selectByVisibleText(opcionEng);
    	        testCase.addScreenShotCurrentStep("Ubicación seleccionada: " + opcionEng);
    	    } catch (NoSuchElementException e) {
    	        select.selectByVisibleText(opcionEsp);
    	        testCase.addScreenShotCurrentStep("Ubicación seleccionada: " + opcionEsp);
    	        u.hardWait(4);
    	    }
    	}
     
     
     
     
     /**
    	 *  Metodo para agregar el tipo de orden este es para TIENDA 
    	 *
    	 * @author Isabel Rivera
    	 * @throws Exception
    	 * @date 02/07/2025
    	 */    
     
     public void seleccionarStore() {
    	    try {
    	        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
    	            By.xpath("//select[@id='pt1:contentAreaReg:0:tabrg1:2:soc1::content']")
    	        ));
    	        testCase.addScreenShotCurrentStep("seleccionarStore ");
    	        Select select = new Select(dropdown);
    	        try {
    	            select.selectByVisibleText("Store");   // Inglés
    	        } catch (Exception e) {
    	            select.selectByVisibleText("Tienda");  // Español
    	        }
    	    } catch (Exception e) {
    	        System.out.println("Error seleccionando opción: " + e.getMessage());
    	    }
    	}


     /**
 	 * busca la locacion
 	 *
 	 * @author Isabel Rivera
 	 * @throws Exception
 	 * @date 23/07/2025
 	 */      
     
     public void ingresarLocation(String locationValue) {
 	    try {
 	        
 	        //WebElement icono = wait.until(ExpectedConditions.elementToBeClickable(iconoBuscarLocation));
 	        //icono.click();
 	        By locator = inputBuscarLocation;
 	        WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
 	        input = wait.until(ExpectedConditions.elementToBeClickable(locator));
 	        //WebElement input = wait.until(ExpectedConditions.elementToBeClickable(inputBuscarLocation));
 
 	        input.clear();
 	        input.sendKeys(locationValue);
 	        input.sendKeys(Keys.ENTER); 

 	        testCase.addScreenShotCurrentStep("Location ingresado: " + locationValue);

 	    } catch (Exception e) {
 	        throw new AssertionError("Error al ingresar el Location: " + e.getMessage());
 	    }
 	}
  
     /**
  	 * Metodo para dar clc en el boton OK de la pantalla inicio de solicitud
  	 *
  	 * @author Isabel Rivera
  	 * @throws Exception
  	 * @date 23/07/2025
  	 */      
     
     public void clickBotonOK() {
    	    WebElement btnOk = wait.until(ExpectedConditions.elementToBeClickable(botonOkPopup));
    	    btnOk.click();
    	    testCase.addScreenShotCurrentStep("Botón OK presionado");
    	}
     
     

     /*
 	 * Muestra la orden, metodo calendario elija el proximo fin de semana 
 	 *
 	 * @author Isabel Rivera
 	 * @throws Exception
 	 * @date 01/07/2025
 	 */
    
	
     public void seleccionarProximoFinDeSemana() {
    	    WebElement iconoCalendario4 = wait.until(ExpectedConditions.elementToBeClickable(
    	        By.xpath("//a[contains(@id, 'id3::glyph') and (contains(@title, 'Seleccionar') or contains(@title, 'Select'))]")));
    	    iconoCalendario4.click();

    	    LocalDate hoy = LocalDate.now();
    	    DayOfWeek dia = hoy.getDayOfWeek();
    	    int diasHastaSabado = DayOfWeek.SATURDAY.getValue() - dia.getValue();
    	    if (diasHastaSabado <= 0) diasHastaSabado += 7;
    	    LocalDate sabado = hoy.plusDays(diasHastaSabado);
    	    String diaDelMes = String.valueOf(sabado.getDayOfMonth());

    	 
    	    if (sabado.getMonth() != hoy.getMonth()) {
    	        WebElement siguienteMes = wait.until(ExpectedConditions.elementToBeClickable(
    	            By.xpath("//a[contains(@title, 'Siguiente mes') or contains(@title, 'Next Month')]")));
    	        siguienteMes.click();
    	    }

    	    WebElement diaElemento = wait.until(ExpectedConditions.elementToBeClickable(
    	        By.xpath("//td[@role='gridcell' and contains(@class,'xov') and normalize-space(text())='" + diaDelMes + "']")));
    	    diaElemento.click();

    	    testCase.addScreenShotCurrentStep("Fecha seleccionada: Sábado siguiente (" + sabado.toString() + ")");
    	}
     
     
     
     /**
  	 * Método para que agregue 1 
  	 *
  	 * @author Isabel Rivera
  	 * @throws Exception
  	 * @date 01/07/2025
  	 */ 
     
     public void ingresarNumeroPoType() {
    	    WebElement inputPoType = wait.until(ExpectedConditions.visibilityOfElementLocated(
    	        By.xpath("//input[contains(@id, 'poTypeId::content')]")
    	    ));
    	    
    	    inputPoType.clear();          
    	    inputPoType.sendKeys("1");    
    	    
    	    testCase.addScreenShotCurrentStep("Ingresado número 1 en campo PoTypeId");
    	    u.hardWait(5);
    	    //inputPoType.sendKeys(Keys.TAB);
    	}
     
     
     
     
     /**
    	 *  Metodo para agregar el Po_type = 30 
    	 *
    	 * @author Isabel Rivera
    	 * @throws Exception
    	 * @date 14/07/2025
    	 */    
     
     public void ingresarNumeroPoType30() {
 	    WebElement inputPoType = wait.until(ExpectedConditions.visibilityOfElementLocated(
 	        By.xpath("//input[contains(@id, 'poTypeId::content')]")
 	    ));
 	    
 	    inputPoType.clear();          
 	    inputPoType.sendKeys("30");    
 	    
 	    testCase.addScreenShotCurrentStep("Ingresado número 1 en campo PoTypeId");
 	    u.hardWait(5);
 	    //inputPoType.sendKeys(Keys.TAB);
 	}
     
      
     /**
 	 *  Metodo para agregar el Po_type = 31
 	 *
 	 * @author Isabel Rivera
 	 * @throws Exception
 	 * @date 23/07/2025
 	 */    
     
     public void ingresarNumeroPoType31() {
  	    WebElement inputPoType = wait.until(ExpectedConditions.visibilityOfElementLocated(
  	        By.xpath("//input[contains(@id, 'poTypeId::content')]")
  	    ));
  	    
  	    inputPoType.clear();          
  	    inputPoType.sendKeys("31");    
  	    
  	    testCase.addScreenShotCurrentStep("Ingresado número 31 en campo PoTypeId");
  	    u.hardWait(5);
  	    //inputPoType.sendKeys(Keys.TAB);
  	}
     
     
     
     /**
    	 *  Metodo para seleccionar la opcion more action
    	 *
    	 * @author Isabel Rivera
    	 * @throws Exception
    	 * @date 06/08/2025
    	 */    
     
     public void clickMoreActions() {
    	    try {
    	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	        JavascriptExecutor js = (JavascriptExecutor) driver;

    	        
    	        By btnMoreAction = By.xpath("//a[@id='pt1:contentAreaReg:0:tabrg2:2:pt1:ap1:b3::popEl']");
    	        	           
    	        WebElement moreActions = wait.until(ExpectedConditions.elementToBeClickable(btnMoreAction));
    	        moreActions.click();
    	        testCase.addScreenShotCurrentStep("clickMoreActions");
    	       
    	        u.hardWait(6); 
    	    } catch (Exception e) {
    	        System.out.println("Error al desplegar el menú More Actions o al hacer scroll: " + e.getMessage());
    	    }

    	    }
     
     /**
 	 *  Metodo para seleccione la opcion otros atributos de la creación de orden PT
 	 *
 	 * @author Isabel Rivera
 	 * @throws Exception
 	 * @date 17/07/2025
 	 */    
    	    
     public void seleccionarFilaOtherAttributes() {
    	    try {
    	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    	        By rowLocator = By.xpath("//tr[td[text()='Other Attributes']]");
    	        WebElement fila = wait.until(ExpectedConditions.elementToBeClickable(rowLocator));
    	        fila.click();

    	        System.out.println(" Fila 'Other Attributes' seleccionada correctamente.");
    	        testCase.addScreenShotCurrentStep("selecciona otraos atributos");
    	    } catch (Exception e) {
    	        System.out.println(" No se pudo seleccionar la fila 'Other Attributes': " + e.getMessage());
    	        u.hardWait(6);
    	    }
    	}
            
     
     
     /**
    	 *  Metodo para agregar la opcion doble pivote
    	 *
    	 * @author Isabel Rivera
    	 * @throws Exception
    	 * @date 02/07/2025
    	 */    
     
     public void clickFilaPTporId() {
    	    try {
    	        By fila = By.id("pt1:contentAreaReg:0:tabrg2:2:pt1:ap1:j_id__ctru273pc8:2:j_id__ctru274pc8");	        
    	        WebElement elemento = wait.until(ExpectedConditions.elementToBeClickable(fila));
    	        testCase.addScreenShotCurrentStep("Click en fila:PT");
    	        elemento.click();
    	        u.hardWait(2);

    	    } catch (TimeoutException e) {
    	        System.out.println(" Tiempo de espera agotado: No se encontró la fila por ID.");
    	        testCase.addTextEvidenceCurrentStep("No se encontró la fila con ID esperado.");
    	    } catch (Exception e) {
    	        System.out.println("Error inesperado al hacer clic en la fila por ID.");
    	        e.printStackTrace();
    	        testCase.addTextEvidenceCurrentStep("Error inesperado al hacer clic en la fila con ID.");
    	        u.hardWait(2);
    	    }
    	}
     
     /**
 	 *  Metodo para agregar la opcion doble pivote
 	 *
 	 * @author Isabel Rivera
 	 * @throws Exception
 	 * @date 02/07/2025
 	 */       
     
     public void seleccionarUbicacionPuntoDeTransbordo() {
    	    try {
    	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
  	       
    	        By lovIcon = By.id("pt1:contentAreaReg:0:tabrg3:2:mr1:extEnt1:0:grpset1:0:grp1:0:attr1:0:job2::lovIconId");
    	        wait.until(ExpectedConditions.elementToBeClickable(lovIcon)).click();
    	        System.out.println("Clic en el ícono LOV de Ubicación");
    	        testCase.addScreenShotCurrentStep("seleccionar PT");
	        
    	        By filaPuntoTransbordo = By.xpath("//td[span[text()='PUNTO DE TRANSBORDO']]");
    	        wait.until(ExpectedConditions.visibilityOfElementLocated(filaPuntoTransbordo));
    	        
    	        wait.until(ExpectedConditions.elementToBeClickable(filaPuntoTransbordo)).click();
    	        System.out.println(" Se seleccionó la opción 'PUNTO DE TRANSBORDO'");
    	        testCase.addScreenShotCurrentStep("PT");
    	        

    	        By botonAceptar = By.xpath("//button[span[text()='Aceptar'] or span[text()='OK'] or span[text()='Aceptar']]");
    	        wait.until(ExpectedConditions.elementToBeClickable(botonAceptar)).click();
    	        System.out.println(" Clic en el botón Aceptar del LOV");
    	        u.hardWait(2);
    	        testCase.addScreenShotCurrentStep("Clic en el botón Aceptar");
    	            
    	        wait.until(ExpectedConditions.invisibilityOfElementLocated(filaPuntoTransbordo));
    	        System.out.println(" LOV cerrado exitosamente");
    	        testCase.addScreenShotCurrentStep("Cerrado exitosamente");
    	        testCase.addScreenShotCurrentStep("PT");

    	    } catch (Exception e) {
    	        System.out.println("Error al seleccionar ubicación en el LOV: " + e.getMessage());
    	    }
    	}

     
     
     
     /**
    	 *  Metodo para guardar y cerrar
    	 *
    	 * @author Isabel Rivera
    	 * @throws Exception
    	 * @date 02/07/2025
    	 */    
     public void clickGuardarOCerrar() {
    	    try {
    	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));  	       
    	        By btnEsp = By.xpath("//a[span[text()='Guardar y cerrar']]");
    	        By btnIng = By.xpath("//a[span[text()='Save and Close']]");
    	        testCase.addScreenShotCurrentStep("Guardar y cerrar' / 'Save and Close");
    	        WebElement boton;
    	       

    	        if (driver.findElements(btnEsp).size() > 0) {
    	            boton = wait.until(ExpectedConditions.elementToBeClickable(btnEsp));
    	            System.out.println("Idioma detectado: Español");
    	            testCase.addScreenShotCurrentStep("Guardar y cerrar");
    	        } else {
    	            boton = wait.until(ExpectedConditions.elementToBeClickable(btnIng));
    	            System.out.println("Idioma detectado: Inglés");
    	            testCase.addScreenShotCurrentStep("Save and Close");
    	        }

    	        boton.click();
    	        System.out.println("Se hizo clic en 'Guardar y cerrar' / 'Save and Close'");
    	        testCase.addScreenShotCurrentStep("Guardar y cerrar' / 'Save and Close");
    	        

    	    } catch (Exception e) {
    	        System.out.println("No se pudo hacer clic en el botón 'Guardar y cerrar' o 'Save and Close': " + e.getMessage());
    	        u.hardWait(2);
    	    }
    	}

     
     /**
    	 *  Metodo para seelccionar la opción DETALLES
    	 *
    	 * @author Isabel Rivera
    	 * @throws Exception
    	 * @date 03/07/2025
    	 */    
     
     public void clickEnDetalles() {
    	    By detailsBtn = By.xpath("//a[.//span[normalize-space()='Details' or normalize-space()='Detalles']]");
    	    WebElement boton = wait.until(ExpectedConditions.elementToBeClickable(detailsBtn));
    	    boton.click();
    	    u.hardWait(2);
    	    testCase.addScreenShotCurrentStep("Click en botón Details");
    	}
     
     
     /**
  	 * Inicia la creacion de Oc distribución
  	 *
  	 * @author Isabel Rivera
  	 * @throws Exception
  	 * @date 01/07/2025
  	 */ 
    	 
     
     
     public void clickAction() {
    	 u.hardWait(7);
    	 testCase.addScreenShotCurrentStep("Pantalla inicial RMC");
 		clickOption("Conexion", btnAction);
 		u.hardWait(3);
 		
 	}
 
     
     
     
     /**
 	 *  Metodo para seelccionar la opción ADD 
 	 *
 	 * @author Isabel Rivera
 	 * @throws Exception
 	 * @date 03/07/2025
 	 */    
    	 
     public void clickAdd() {
    	 u.hardWait(9);
  		clickOption("Conexion", btnAdd);
  		
     }
  		
     /** Metodo Get
  	 * 
  	 *
  	 * @author Isabel Rivera
  	 * @throws Exception
  	 * @date 02/07/2025
  	 */      			
     public By getInputItem() {
    	 u.hardWait(3);
    	 return inputItem;
       
     }	 
    	 
    
     
     /**
 	 *  Metodo para agregar diferetes articulos 
 	 *
 	 * @author Isabel Rivera
 	 * @throws Exception
 	 * @date 24/07/2025
 	 */    
    	 
     public void clickArticulo1() {
    	 u.hardWait(3);
    		clickOption("Conexion", btnArt1);
    	    u.hardWait(3); 
    	    ingresarArt("7501064198519", inputArt);
    	    u.hardWait(2);	
    	    ingresarCantidad("100", inputCant);
    	    u.hardWait(2);
    	    clickOption("Ok and Add Another", buttonOk1);
    	    u.hardWait(3);
    	     
    	}
     
     
     /**
  	 *  Metodo para agregar diferetes articulos 
  	 *
  	 * @author Isabel Rivera
  	 * @throws Exception
  	 * @date 06/08/2025
  	 */    
     
     public void clickArticuloreto() {
    	 u.hardWait(3);
    		clickOption("Conexion", btnArt1);
    	    u.hardWait(3); 
    	    ingresarArt("41966", inputArt78);
    	    u.hardWait(2);	
    	    ingresarCantidad("100", inputCant78);
    	    u.hardWait(2);
    	    clickOption("Ok and Add Another", buttonOk78);
    	    u.hardWait(3);
    	     
    	}
     
     /**
   	 *  Metodo para agregar el articulo ITEM Cerveza Gran Modelo Especial Retornable 1 Lt
   	 *
   	 * @author Isabel Rivera
   	 * @throws Exception
   	 * @date 06/08/2025
   	 */   
     public void clickArticulo2() {
    	 u.hardWait(3);
    		clickOption("Conexion", btnArt1);
    	    u.hardWait(3); 
    	    ingresarArt("7501064198519", inputArt2);
    	    u.hardWait(2);	
    	    ingresarCantidad("100", inputCant2);
    	    u.hardWait(2);
    	    clickOption("Ok and Add Another", buttonOk3);
    	    u.hardWait(3);
    	     
    	}
     
     
     
     
     /**
 	 *  Metodos para cerrar las ventanas save and close
 	 *  
 	 * @author Isabel Rivera
 	 * @throws Exception
 	 * @date 03/07/2025
 	 */    
     
     public void clickSaveClose1() {
    	 
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    	wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("AFModalGlassPane"))); 
 		clickOption("Save and close", buttonSaveClose1);
 		u.hardWait(7); 
   }
      
     /**
  	 *  Metodos para cerrar las ventanas save and close
  	 *  
  	 * @author Isabel Rivera
  	 * @throws Exception
  	 * @date 03/07/2025
  	 */    
       
     public void clickSaveClose2() {
    	 u.hardWait(3); 
  		clickOption("Save and close", buttonSaveClose2);
  		u.hardWait(3); 
    }
     
     /**
   	 *  Metodos para cerrar las ventanas save and close
   	 *  
   	 * @author Isabel Rivera
   	 * @throws Exception
   	 * @date 28/07/2025
   	 */     
     
     public void clickSaveClose32() {
    	 u.hardWait(3); 
  		clickOption("Save and close", buttonSaveClose2);
  		u.hardWait(3); 
    }
        
     
     /**
   	 *  Metodos para cerrar las ventanas save and close
   	 *  
   	 * @author Isabel Rivera
   	 * @throws Exception
   	 * @date 03/07/2025
   	 */    
     public void clickSaveClose31() {
    	 
     	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
     	wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("AFModalGlassPane"))); 
  		clickOption("Save and close", buttonSaveClose31);
  		u.hardWait(7); 
    }
     /*public void clickSaveClose3() {
   		clickOption("Save and close", buttonSaveClose3);
   		u.hardWait(3); 
     }
     */
        
     
     /**
   	 *  Metodos para cerrar las ventanas save and close
   	 *  
   	 * @author Isabel Rivera
   	 * @throws Exception
   	 * @date 03/07/2025
   	 */    
     
     public void clickSubmit() {
    		clickOption("Submit", buttonSubmit);
    		u.hardWait(10); 
      }
     
     /**
    	 *  Metodos para dar clic en la primera confirmación 
    	 *  
    	 * @author Isabel Rivera
    	 * @throws Exception
    	 * @date 07/07/2025
    	 */      
       
     public void clickConfirmacion1YesoSi1() {
    		clickOption("Yes o Si", botonYeSiPopup1);
    		u.hardWait(10); 
      }
      
     /**
 	 *  Metodos para dar clic en la opcion de approvacion 
 	 *  
 	 * @author Isabel Rivera
 	 * @throws Exception
 	 * @date 07/07/2025
 	 */     
     public void clickApprove() {
 		clickOption("Approve", botonApprove);
 		u.hardWait(10); 
   }
     
     /**
  	 *  Metodos para dar clic en la opcion de approvacion 
  	 *  
  	 * @author Isabel Rivera
  	 * @throws Exception
  	 * @date 07/07/2025
  	 */     
     
     public void clickConfirmacion2YesoSi2() {
 		clickOption("Yes o Si", botonYeSiPopup2);
 		u.hardWait(8); 
   }
     
     
     /**
 	 *  Metodos para dar clic en la segunda confirmación 
 	 *  
 	 * @author Isabel Rivera
 	 * @throws Exception
 	 * @date 07/07/2025
 	 */ 
     public void clickConfirmacion2YesoSi3() {
    	 u.hardWait(8);
  		clickOption("Yes o Si", botonYeSiPopup3);
  		u.hardWait(6); 
    }
     
     /**
 	 *  Metodos para dar clic en guardar y cerrar 
 	 * @author Isabel Rivera
 	 * @throws Exception
 	 * @date 07/07/2025
 	 */   
     public void clickGuardarYCerrar() {
   		clickOption("Guardar", botonSaveandClose);
   		u.hardWait(4); 
   		driver.close();
     }
     
     
     /**
 	 *  Metodos para dar clic en guardar y cerrar
 	 *  
 	 * @author Isabel Rivera
 	 * @throws Exception
 	 * @date 07/07/2025
 	 */ 
     public void clickGuardarYCerrar78() {
    		clickOption("Guardar", botonSaveandClose78);
    		u.hardWait(6); 
    		driver.close();
      }
     
     /**
 	 *  Metodos para dar clic en la primera confirmación 
 	 *  
 	 * @author Isabel Rivera
 	 * @throws Exception
 	 * @date 24/08/2025
 	 */ 
     
     public void clickSubmit31() {
 		clickOption("Submit", buttonSubmit31);
 		u.hardWait(10); 
   }
     /**
  	 * Inicia la creacion de OC Carga Masiva
  	 *
  	 * @author Isabel Rivera
  	 * @throws Exception
  	 * @date 01/07/2025
  	 */ 
     
     
     
     public void clickOrders2() {
  		clickOption("Orders", SelectOxxo );
  		
  		
  	}
     
     
     /**
 	 *  Metodos para dar clic en la oción de oxxo purchase para la oc en carga masiva
 	 *  
 	 * @author Isabel Rivera
 	 * @throws Exception
 	 * @date 07/07/2025
 	 */ 
      
      public void clickOxxoPurchase() {
   	   clickOption("Orders",SelectorOxxoPurchase);
   	   u.hardWait(4);
   	   
   	}
      
         
      /**
  	 *  Metodo subir archivo de la oc carga masiva 
  	 *
  	 * @author Isabel Rivera
  	 * @throws Exception
  	 * @date 04/07/2025
  	 */    
    
     public void subirArchivo() {
    	    try {
    	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    	        
    	        By inputArchivo = By.xpath("//input[@id='pt1:contentAreaReg:0:tabrg1:2:if1::content']");
    	       
    	        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(inputArchivo));

    	        String rutaArchivo = "C:\\Users\\GTIM\\Documents\\End to End\\TemplateCargasOrdenes_01_V1.ods";
    	        input.sendKeys(rutaArchivo);
    	        u.hardWait(4);

    	        System.out.println(" Archivo cargado: " + rutaArchivo);

    	    } catch (TimeoutException e) {
    	        System.out.println(" Timeout: No se encontró el input de archivo: " + e.getMessage());
    	    } catch (NoSuchElementException e) {
    	        System.out.println(" Elemento no encontrado: " + e.getMessage());
    	    } catch (WebDriverException e) {
    	        System.out.println(" WebDriver error: " + e.getMessage());
    	    } catch (Exception e) {
    	        System.out.println(" Error al cargar el archivo: " + e.getMessage());
    	    }
    	}


     
     /**
 	 *  Metodos para subir archivo de la oc carga masiva
 	 *  
 	 * @author Isabel Rivera
 	 * @throws Exception
 	 * @date 07/07/2025
 	 */ 
     
     public void subirArchivo(String rutaArchivo) {
    	    try {
    	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    	        // Localizar el input de tipo file
    	        WebElement inputArchivo = wait.until(
    	            ExpectedConditions.presenceOfElementLocated(
    	                By.xpath("//input[@type='file' and contains(@id,'if1::content')]")
    	                          //input[@id='pt1:contentAreaReg:0:tabrg1:2:if1::content']
    	            )
    	        );

    	        // Enviar la ruta completa del archivo
    	        inputArchivo.sendKeys("C:\\Users\\GTIM\\Documents\\End to End\\TemplateCargasOrdenes_01_V1.ods");

    	        System.out.println("✅ Archivo cargado: " + "C:\\Users\\GTIM\\Documents\\End to End\\TemplateCargasOrdenes_01_V1.ods");

    	    } catch (Exception e) {
    	        System.out.println("❌ Error al cargar archivo: " + e.getMessage());
    	    }
    	}
     
 
     
     /**
   	 * Inicia la creacion de OC Carga Masiva para PT
   	 *
   	 * @author Isabel Rivera
   	 * @throws Exception
   	 * @date 01/07/2025
   	 */ 
     
     
     
     public void subirArchivoPTARB() {
 	    try {
 	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
 	        
 	        
 	        By inputArchivo = By.xpath("//input[@id='pt1:contentAreaReg:0:tabrg1:2:if1::content']");
 	        testCase.addScreenShotCurrentStep("Archivo cargado");                            
 	       
 	        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(inputArchivo));
        
 	        String rutaArchivo = "C:\\Users\\GTIM\\OneDrive-Grupo TI Mexico\\Documentos\\End to End\\TemplateCargasOrdenes_01_V1_PTNB";
 	        input.sendKeys(rutaArchivo);
 	       testCase.addScreenShotCurrentStep("subirArchivoPTARB");

 	        System.out.println(" Archivo cargado: " + rutaArchivo);

 	    } catch (TimeoutException e) {
 	        System.out.println(" Timeout: No se encontró el input de archivo: " + e.getMessage());
 	    } catch (NoSuchElementException e) {
 	        System.out.println(" Elemento no encontrado: " + e.getMessage());
 	    } catch (WebDriverException e) {
 	        System.out.println(" WebDriver error: " + e.getMessage());
 	    } catch (Exception e) {
 	        System.out.println(" Error al cargar el archivo: " + e.getMessage());
 	    }
 	}
        
    
     
     /**
 	 *  Metodo para seleccionar archivo de carga masiva
 	 *
 	 * @author Isabel Rivera
 	 * @throws Exception
 	 * @date 03/07/2025
 	 */    
     
     public void subirArchivoCargaMasiva() {
    	    try {
    	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    	    
    	        By inputArchivo = By.xpath("//input[@id='pt1:contentAreaReg:0:tabrg1:2:if1::content']");
    	        WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(inputArchivo));

    	        String rutaArchivo = "C:\\Users\\GTIM\\OneDrive - Grupo TI Mexico\\Documentos\\End to End\\TemplateCargasOrdenes_01_V1.ods";
    	        
    	        input.sendKeys(rutaArchivo);
    	        testCase.addScreenShotCurrentStep("Archivo cargado");
    	        u.hardWait(2);

    	        System.out.println("Archivo cargado: " + rutaArchivo);
    	        //testCase.addScreenShotCurrentStep("Archivo cargado");

    	      
    	        By botonCargar = By.xpath("//button[@id='pt1:contentAreaReg:0:tabrg1:2:cb1']"); 
    	        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(botonCargar));
    	        btn.click();
    	        u.hardWait(3);

    	        System.out.println("Archivo enviado y botón 'Cargar' presionado.");

    	    } catch (TimeoutException e) {
    	        System.out.println("Timeout: No se encontró el elemento: " + e.getMessage());
    	    } catch (NoSuchElementException e) {
    	        System.out.println("Elemento no encontrado: " + e.getMessage());
    	    } catch (WebDriverException e) {
    	        System.out.println("WebDriver error: " + e.getMessage());
    	    } catch (Exception e) {
    	        System.out.println("Error al cargar el archivo: " + e.getMessage());
    	    }
    	}

     
     
     /**
 	 *  Metodos para cerrar la pantalla 
 	 *  
 	 * @author Isabel Rivera
 	 * @throws Exception
 	 * @date 07/07/2025
 	 */ 
     
     public void cerrarPagina() {
    	    if (driver != null) {
    	        driver.close(); // Cierra la ventana/pestaña actual
    	        System.out.println("Se cerró la pestaña actual.");
    	        testCase.addScreenShotCurrentStep("CerrarPagina");
    	    }
    	}
     
     /**
  	 *  Metodo para seleccionar archivo de carga masiva tipo ARB
  	 *
  	 * @author Isabel Rivera
  	 * @throws Exception
  	 * @date 03/07/2025
  	 */             
     public void subirArchivoCargaMasivaARB() {
   	    try {
   	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
   	        By inputArchivo = By.xpath("//input[@type='file' and contains(@id, 'if1::content')]");
   	        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(inputArchivo));
   	        String rutaArchivo = "C:\\Users\\GTIM\\OneDrive - Grupo TI Mexico\\Documentos\\End to End\\TemplateCargasOrdenes_01_V1_CMARB.ods";
   	        input.sendKeys(rutaArchivo);
   	     testCase.addScreenShotCurrentStep("subirArchivoCargaMasivaARB");

   	        System.out.println(" Archivo cargado: " + rutaArchivo);

   	    } catch (TimeoutException e) {
   	        System.out.println(" Timeout: No se encontró el input de archivo: " + e.getMessage());
   	    } catch (NoSuchElementException e) {
   	        System.out.println(" Elemento no encontrado: " + e.getMessage());
   	    } catch (WebDriverException e) {
   	        System.out.println(" WebDriver error: " + e.getMessage());
   	    } catch (Exception e) {
   	        System.out.println(" Error al cargar el archivo: " + e.getMessage());
   	    }
   	}
     
     
     
     
     
     public void subirArchivoCargaMasivatipoARB() {
 	    try {
 	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

 	    
 	        By inputArchivo = By.xpath("//input[@type='file' and contains(@id, 'if1::content')]");
 	        WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(inputArchivo));

 	        String rutaArchivo = "C:\\Users\\GTIM\\OneDrive - Grupo TI Mexico\\Documentos\\End to End\\TemplateCargasOrdenes_01_V1_CMARB.ods";
 	        
 	        input.sendKeys(rutaArchivo);
 	        testCase.addScreenShotCurrentStep("Archivo cargado");
 	        u.hardWait(2);

 	        System.out.println("Archivo cargado: " + rutaArchivo);
 	        //testCase.addScreenShotCurrentStep("Archivo cargado");

 	      
 	        By botonCargar = By.xpath("//button[@id='pt1:contentAreaReg:0:tabrg1:2:cb1']"); 
 	        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(botonCargar));
 	        btn.click();
 	        u.hardWait(3);

 	        System.out.println("Archivo enviado y botón 'Cargar' presionado.");

 	    } catch (TimeoutException e) {
 	        System.out.println("Timeout: No se encontró el elemento: " + e.getMessage());
 	    } catch (NoSuchElementException e) {
 	        System.out.println("Elemento no encontrado: " + e.getMessage());
 	    } catch (WebDriverException e) {
 	        System.out.println("WebDriver error: " + e.getMessage());
 	    } catch (Exception e) {
 	        System.out.println("Error al cargar el archivo: " + e.getMessage());
 	    }
 	}
        
     
     /**
 	 *  Metodo para seelccionar la opción de cargar archivo
 	 *
 	 * @author Isabel Rivera
 	 * @throws Exception
 	 * @date 21/07/2025
 	 */    

     public void clickBotonUpload() {
    	    try {
    	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	        By botonUpload = By.xpath("//a[@role='button']//span[contains(text(),'Upload')]\r\n"
    	        		+ "");
              
    	        WebElement uploadBtn = wait.until(ExpectedConditions.elementToBeClickable(botonUpload));
    	        uploadBtn.click();
    	        testCase.addScreenShotCurrentStep("clickBotonUpload");

    	        System.out.println("Se hizo clic en el botón 'Upload'");    	       
    	        Thread.sleep(2000);
    	        testCase.addScreenShotCurrentStep("subir archivo");

    	    } catch (Exception e) {
    	        System.out.println("Error al hacer clic en 'Upload': " + e.getMessage());
    	        System.out.println("se subio archivo de manera correcta");
    	    }
    	}

    
     
     /**
 	 *  Metodo para sconfirmas las ventanas de popup dialgo 1
 	 *
 	 * @author Isabel Rivera
 	 * @throws Exception
 	 * @date 03/07/2025
 	 */    
     
     public void confirmarDialogoOk() {
    	    try {
    	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	        By botonOk = By.xpath("//a[span[text()='OK']] | //button[span[text()='OK']]");

    	        WebElement okBtn = wait.until(ExpectedConditions.elementToBeClickable(botonOk));
    	        okBtn.click();

    	        System.out.println(" Se hizo clic en el botón 'OK' del diálogo");

  
    	        wait.until(ExpectedConditions.invisibilityOf(okBtn));

    	    } catch (Exception e) {
    	        System.out.println(" No se pudo confirmar con 'OK': " + e.getMessage());
    	    }
    	}
     
     
     /**
 	 * Inicia la creacion de Oc Espejo
 	 *
 	 * @author Isabel Rivera
 	 * @throws Exception
 	 * @date 01/07/2025
 	 */  
     
     public void clickManagerOrder() {
   		clickOption("Manager", manageOrders);
   		
   	}

     
     /**
 	 *  Metodo para agregar un input (OC) y realize la busqueda 
 	 *
 	 * @author Isabel Rivera
 	 * @throws Exception
 	 * @date 18/07/2025
 	 */ 
     public void ingresarOrderNumeroADF() {
    	    try {
    	        By locator = By.xpath("//input[contains(@id, 'qryId2:val00') and contains(@id, '::content')]");
    	        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(locator));

    	        input.click();
    	        input.clear();
    	        input.sendKeys("1036266");
    	        u.hardWait(1); 

    	      
    	        input.sendKeys(Keys.TAB);

    	        
    	        JavascriptExecutor js = (JavascriptExecutor) driver;
    	        js.executeScript("arguments[0].value='" + "10698454" + "';", input);
    	        js.executeScript("arguments[0].dispatchEvent(new Event('input'));", input);
    	        js.executeScript("arguments[0].dispatchEvent(new Event('change'));", input);

    	        testCase.addScreenShotCurrentStep("Número de orden ingresado: " + "10698454");
    	    } catch (Exception e) {
    	        throw new AssertionError(" Error al ingresar el número de orden: " + e.getMessage());
    	    }
    	}
     
     /**
 	 *  Metodo para dar clic boton buscar
 	 *
 	 * @author Isabel Rivera
 	 * @throws Exception
 	 * @date 09/07/2025
 	 */     

     public void clickBotonBuscar() {
    	    try {
    	        By btnBuscar = By.xpath("//span[@class='xfx' and (text()='Buscar' or text()='Search')]/ancestor::a[1]");
    	        WebElement boton = wait.until(ExpectedConditions.elementToBeClickable(btnBuscar));
    	        boton.click();
    	        testCase.addScreenShotCurrentStep("clickBotonBuscar");
    	        u.hardWait(2);
    	        testCase.addScreenShotCurrentStep(" Se hizo clic en el botón Buscar/Search");
    	    } catch (Exception e) {
    	        throw new AssertionError(" No se pudo hacer clic en el botón Buscar/Search: " + e.getMessage());
    	    }
    	}
     
     
     /**
  	 *  Metodo para agregar el numero de OC y hacer la ampliacion
  	 *
  	 * @author Isabel Rivera
  	 * @throws Exception
  	 * @date 17/07/2025
  	 */     

     public void darClickEnNumero() {
         String xpath = "//span[contains(@id, 'resId1') and normalize-space(text())='" + 10698454 + "']";
         testCase.addScreenShotCurrentStep("darClickEnNumero");

         try {
             WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
             WebElement elemento = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
             elemento.click();
             System.out.println("Se hizo clic en el número: " + 10698454);
         } catch (Exception e) {
             System.out.println("No se pudo hacer clic en el número " + 10698454 + ": " + e.getMessage());
         }
     }
 
     
     /**
  	 *  Metodo para que en el campo po_type agregue el numero 33 ampliacion 
  	 *
  	 * @author Isabel Rivera
  	 * @throws Exception
  	 * @date 09/07/2025
  	 */     
     public void ingresarNumeroPoType33() {
 	    WebElement inputPoType = wait.until(ExpectedConditions.visibilityOfElementLocated(
 	        By.xpath("//input[contains(@id, 'poTypeId::content')]")
 	    ));
 	    
 	    inputPoType.clear();          
 	    inputPoType.sendKeys("1");    
 	    
 	    testCase.addScreenShotCurrentStep("Ingresado número 1 en campo PoTypeId");
 	    u.hardWait(5);
 	    //inputPoType.sendKeys(Keys.TAB);
 	}
    
     
     /**
    	 *  Metodo Limpia un input seleccionando el texto que tiene y borrandolo.
    	 *
    	 * @author Isabel Rivera
    	 * @throws Exception
    	 * @date 18/07/2025
    	 */
	public void clearInput(By inputBy) {
		try {
			WebElement toClear = u.getDriver().findElement(inputBy);
			toClear.sendKeys(Keys.CONTROL + "a");
			toClear.sendKeys(Keys.DELETE);
		} catch (Exception e) {
			logP("Fallo al limpiar el campo: " + e);
		}
	}
    
	
	/**
 	 *  Metodo para que agregar el usuario
 	 *
 	 * @author Isabel Rivera
 	 * @throws Exception
 	 * @date 02/07/2025
 	 */     
    public void ingresarUsuario(String inputUsuario, By elementBy) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(elementBy));
			clearInput(elementBy);
			u.sendKeys(elementBy, inputUsuario);
			testCase.addScreenShotCurrentStep(u, "");
			u.hardWait(1);
		} catch (Exception e) {
			logP("" + e);
			throw new AssertionError("Fallo al ingresar en el campo: " + inputUsuario);
		}
	}
    
    /**
 	 *  Metodo para agregar contraseña
 	 *
 	 * @author Isabel Rivera
 	 * @throws Exception
 	 * @date 02/07/2025
 	 */     
    
    
    public void ingresarContrasena(String inputContasena, By elementBy) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(elementBy));
			clearInput(elementBy);
			u.sendKeys(elementBy, inputContasena);
			testCase.addScreenShotCurrentStep(u, "");
			u.hardWait(1);
		} catch (Exception e) {
			logP("" + e);
			throw new AssertionError("Fallo al ingresar en el campo: " + inputContasena);
		}
	}

       
    
    /**
   	 *  Método genérico para hacer clic en un option
   	 *
   	 * @author Isabel Rivera
   	 * @throws Exception
   	 * @date 18/07/2025
   	 */
    public void clickOption(String name, By elementBy) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(elementBy));
            u.click(elementBy);
            u.hardWait(1);
            testCase.addScreenShotCurrentStep("Click en: " + name);
        } catch (Exception e) {
            logP("Error al hacer clic en " + name + ": " + e.getMessage());
            throw new AssertionError("Fallo al dar clic en: " + name);
        }
    }
    /**
   	 * Método genérico para ingresar texto en un input
   	 *
   	 * @author Isabel Rivera
   	 * @throws Exception
   	 * @date 18/07/2025
   	 */
    
    public void ingresarArt(String inputArt, By elementBy) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(elementBy));
			clearInput(elementBy);
			u.sendKeys(elementBy, inputArt);
			testCase.addScreenShotCurrentStep(u, "");
			u.hardWait(1);
		} catch (Exception e) {
			logP("" + e);
			throw new AssertionError("Fallo al ingresar en el campo: " + inputArt);
		}
	}
    
    
    
    /**
   	 * Método genérico para ingresar texto en un input
   	 *
   	 * @author Isabel Rivera
   	 * @throws Exception
   	 * @date 18/07/2025
   	 */   
    public void ingresarCantidad(String inputCant, By elementBy) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(elementBy));
			clearInput(elementBy);
			u.sendKeys(elementBy, inputCant);
			testCase.addScreenShotCurrentStep(u, "");
			u.hardWait(1);
		} catch (Exception e) {
			logP("" + e);
			throw new AssertionError("Fallo al ingresar en el campo: " + inputCant);
		}
	}


    
    /** Validar existencia de un elemento **/
    public void existElement(String name, By locator, String evidencia, boolean tomarEvidencia) {
        try {
            u.getDriver().findElement(locator);
            if (tomarEvidencia) {
                u.highLight(locator);
                u.hardWait(1);
                testCase.addScreenShotCurrentStep(u, evidencia);
                u.unhighLight(locator);
            }
        } catch (Exception e) {
            logP("Elemento no encontrado: " + name);
            throw new AssertionError("No se encontró el elemento: " + name);
        }
    }
    
    /**
	 * Busca la imagen con Sikuli y da clic en botonde scroll down
	 *
	 * @author Roberto Rentería
	 * @throws Exception
	 * @date 18/07/2025
	 */
	public void clickScrollDown() throws Exception {

		try {
			
			 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
  	        wait.until(ExpectedConditions.presenceOfElementLocated(btnScroll));
  	        
  	        Actions action = new Actions(u.getDriver());
  	        action.moveToElement(driver.findElement(btnScroll)).perform();
  	        
			Pattern pattern = new Pattern(FolderPath +"\\scrollDown.png").similar((float).80);
			if (sk.screen.exists(pattern,20).highlight(2)!=null) {
				testCase.addScreenShotCurrentStep("14");
				sk.screen.click(pattern);
				u.hardWait(2);
				sk.screen.click(pattern);
			}else {
				throw new NoSuchElementException("No se detectó el botón scrollDown");
			}
		}catch(Exception e) {

			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
			
		}
	}
}


