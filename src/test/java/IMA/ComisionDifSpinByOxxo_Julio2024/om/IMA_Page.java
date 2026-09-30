package IMA.ComisionDifSpinByOxxo_Julio2024.om;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import IMA.util.IMAUtils;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class IMA_Page {
	private SeleniumUtil u;
	TestCase testCase;
	ChromeDriver driver;
	SikuliUtil sk;
	String pathImg;
	File FolderPath;
	private IMAUtils ima;
	
	
	//Se define el Objeto 
	By btnDashboardQA = By.xpath("//*[@id=\"projects_ProjectsStyle\"]/table/tbody/tr[3]/td[2]/div/table/tbody/tr/td[2]/a");
    By btnContinuar = By.id("3054");
    By ReporteMes = By.xpath("//*[@id=\"FolderIcons\"]/tbody/tr[4]/td[1]/div/table/tbody/tr/td[2]/a");
	By EjecutarReport = By.id("id_mstr108");
	By AnioReport = By.xpath("//*[@id=\"id_mstr100\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]");
	public IMA_Page(SeleniumUtil u, TestCase testCase) {
		this.u = u;
		this.testCase = testCase;
		this.driver = driver;
		this.sk = new SikuliUtil();
		this.pathImg = sk.PatternPath("IMA\\"+"IMA");
		this.FolderPath = new File(pathImg);
	}
	
	
	 /*
     * metodo generado para ingresar a la pagina con el usuario y contraseña de empleado
     * 
     * @param user, ps 
     * @author Alan Perez
     */
	public boolean GotoPage(String user, String ps) throws InterruptedException {
		//URL de IMA
				if(user!=null &ps !=null) {
				
					String contra = "http://" + user + ":" + ps + "@" + "10.185.176.16/MicroStrategy/asp/Main.aspx";
					System.out.println(contra);
					u.get(contra);
					//u.get("http://10.185.176.16/MicroStrategy/asp/Main.aspx");
					Thread.sleep(5000);
					return true;
				}
				else {
					return false;
				}
	}
	
	 /*
     * metodo generado para realizar el login de la pagina
     * 
     * 
     * @author Alan Perez
     */
	
	public boolean Login() {
		
		
		u.highLight(By.id("Uid"));
		u.sendKeys(By.id("Uid"), "qabi");
		u.highLight(By.id("Pwd"));
		u.sendKeys(By.id("Pwd"), "Comercio.1");
		testCase.addScreenShotCurrentStep(u, "Pagina Login");
		u.highLight(By.id("3054"));
		u.click(By.id("3054"));
	return true;
	}
	
	
	
	
	 /*
     * metodo generado para escribir cualquier texto
     * 
     * @param text
     * @author Alan Perez
     */
	
	public void Escribir(String text) throws AWTException {
		
            // Crear una instancia de la clase Robot
            Robot robot = new Robot();
            
            // Esperar un momento antes de empezar (dar tiempo al sistema para estar listo)
            robot.delay(2000);  // Espera de 2 segundos

            for (char c : text.toCharArray()) {
                // Determinar el código de la tecla correspondiente al carácter
                int keyCode = getKeyCode(c);
                System.out.println(c);
                if (keyCode != -1) {  // Si la tecla es válida (es un carácter reconocido)
                    // Simular la pulsación y liberación de la tecla
                    robot.keyPress(keyCode);
                    robot.keyRelease(keyCode);
                    
                    // Pausa entre cada tecla (simula el tiempo entre las pulsaciones de teclas)
                    robot.delay(1000);
                }
            }
    }
	
	
	
	
	 /*
     * metodo generado para obtener cualquier tecla correspondiente
     * 
     * @param text
     * @author Alan Perez
     */
	// Método para obtener el código de la tecla correspondiente a un carácter
    private static int getKeyCode(char c) {
        // Verificar si es una letra (mayúscula o minúscula)
        if (Character.isLetter(c)) {
            // Convertir a mayúscula para obtener el código de la tecla correspondiente
            return KeyEvent.getExtendedKeyCodeForChar(c);
        }
        if (Character.isWhitespace(c)) {
            return -1;  // Ignorar espacios
        }
        // Verificar si es un número
        else if (Character.isDigit(c)) {
            return KeyEvent.getExtendedKeyCodeForChar(c);  // Los números se manejan directamente
        }
        
        // Verificar si es un carácter especial
        switch (c) {
            case '!' : return KeyEvent.VK_EXCLAMATION_MARK;
            case '@' : return KeyEvent.VK_AT;
            case '#' : return KeyEvent.VK_NUMBER_SIGN;
            case '$' : return KeyEvent.VK_DOLLAR;
            case '%' : return KeyEvent.VK_P;
            case '^' : return KeyEvent.VK_CIRCUMFLEX;
            case '&' : return KeyEvent.VK_AMPERSAND;
            case '*' : return KeyEvent.VK_ASTERISK;
            case '(' : return KeyEvent.VK_LEFT_PARENTHESIS;
            case ')' : return KeyEvent.VK_RIGHT_PARENTHESIS;
            case '-' : return KeyEvent.VK_MINUS;
            case '=' : return KeyEvent.VK_EQUALS;
            case '+' : return KeyEvent.VK_PLUS;
            case '[' : return KeyEvent.VK_OPEN_BRACKET;
            case ']' : return KeyEvent.VK_CLOSE_BRACKET;
            case '\\': return KeyEvent.VK_BACK_SLASH;
            case ';' : return KeyEvent.VK_SEMICOLON;
            case '\'' : return KeyEvent.VK_QUOTE;
            case ',' : return KeyEvent.VK_COMMA;
            case '.' : return KeyEvent.VK_PERIOD;
            case '/' : return KeyEvent.VK_SLASH;
            case ':' : return KeyEvent.VK_COLON;
            case '"' : return KeyEvent.VK_QUOTEDBL;
            case '<' : return KeyEvent.VK_LESS;
            case '>' : return KeyEvent.VK_GREATER;
            case '?' : return KeyEvent.VK_SLASH;
            default: return -1; // Caracter no soportado
        }
    }
    
   

    /*
     * metodo generado para ingresar al menu correspodiente
     * 
     * @throws InterruptedException
     * @author Alan Perez
     */
	public void Menu() throws InterruptedException {
		// TODO Auto-generated method stub
	
		String textoBuscado = "Información Comercial QA";

        // Encontrar el elemento que contiene el texto
      
		u.highLight(btnDashboardQA);
		WebElement dashboard = driver.findElement(btnDashboardQA);
			
        // Crear una instancia de Actions (simular el mouse)
        Actions acciones = new Actions(driver);

        // Mover el mouse al elemento y hacer clic
        acciones.moveToElement(dashboard).click().perform();

	}
	
	
	/*
     * metodo generado para obtener el indice de la fila 
     * 
     * @param fila
     * @author Alan Perez
     */
	
	 // Función para obtener el índice de la fila dentro de la tabla
    public static int obtenerIndiceFila(WebElement fila) {
        // Localiza la tabla
        WebElement tabla = fila.findElement(By.xpath("ancestor::table"));
        
        // Obtiene todas las filas de la tabla
        java.util.List<WebElement> filas = tabla.findElements(By.tagName("tr"));

        // Itera sobre las filas para encontrar la posición de la fila
        for (int i = 0; i < filas.size(); i++) {
            if (filas.get(i).equals(fila)) {
                return i + 1; // El índice de las filas es 1-based
            }
        }
        return -1; // Si no encuentra la fila (lo cual no debería suceder)
    }
   
    
    
    /*
     * metodo generado para ingresar a la ruta completa del folder 
     * 
     * @throws AWTException, InterruptedException
     * @param proyecto
     * @author Alan Perez
     */
    
    //Ruta completa de Comision Diferenciada Reporte
    public boolean goToRouteSubFolder(String proyecto) throws AWTException, InterruptedException {
        System.out.println(proyecto);
     // System.out.println("opcion: " + option);
     		u.highLight(By.partialLinkText(proyecto));
     		testCase.addScreenShotCurrentStep(u, "Seleccion de folders");
     		clickjs(By.partialLinkText(proyecto));
        //ima.SelectOption(proyecto);  // Seleccionamos el proyecto
 
       
     		 // Crear una instancia de la clase Robot
            Robot robot = new Robot();
           Thread.sleep(7000);
           try {
            // Simular la pulsación de la tecla "Enter"
            robot.keyPress(KeyEvent.VK_ENTER);  // Presionar la tecla Enter
            robot.keyRelease(KeyEvent.VK_ENTER); // Liberar la tecla
           }
           catch(Exception e){
           	System.out.println(e);
           	return false;
           }
           
            try {
            //Se ingresa a Informes compartidos
            u.highLight(By.partialLinkText("Informes compartidos"));
            testCase.addScreenShotCurrentStep(u, "Informes compartidos");
            clickjs(By.partialLinkText("Informes compartidos"));
        	
            }
            catch(Exception e){
            	System.out.println(e);
            	return false;
            }
            
            
            try {
            //Se ingresa a Reportes por liberar
            u.highLight(By.partialLinkText("A Reportes por Liberar"));
        	testCase.addScreenShotCurrentStep(u, "Reportes por liberar");
            clickjs(By.partialLinkText("A Reportes por Liberar"));
            }
            catch(Exception e){
            	System.out.println(e);
            	return false;
            }
            
            
            
            try {
            //Se ingresa a Información comercial* 
            u.highLight(By.partialLinkText("Informacion Comercial *"));
            testCase.addScreenShotCurrentStep(u, "Informacion Comercial *");
            clickjs(By.partialLinkText("Informacion Comercial *"));
            }
            catch(Exception e){
            	System.out.println(e);
            	return false;
            }
            
            
            try {
            	//Se ingresa a Información comercial* 
                u.highLight(By.partialLinkText("Por Tema"));
                testCase.addScreenShotCurrentStep(u, "Por Tema");
                clickjs(By.partialLinkText("Por Tema"));
            }catch(Exception e){
            	System.out.println(e);
            	return false;
            }
            try {
            	//Se ingresa a Información comercial* 
                u.highLight(By.partialLinkText("Análisis de Servicios"));
                testCase.addScreenShotCurrentStep(u, "Análisis de Servicios");
                clickjs(By.partialLinkText("Análisis de Servicios"));
            }catch(Exception e){
            	System.out.println(e);
            	return false;
            }
            
            try {
            	//Se ingresa a Información comercial* 
                u.highLight(By.partialLinkText("Nacional"));
                testCase.addScreenShotCurrentStep(u, "Nacional");
                clickjs(By.partialLinkText("Nacional"));
            }catch(Exception e){
            	System.out.println(e);
            	return false;
            }
            try {
            	//Se ingresa a Información comercial* 
                u.highLight(By.partialLinkText("Análisis Servicios Nacional (MES)"
                		+ "propietario: BIQA1"));
                clickjs(By.partialLinkText("Análisis Servicios Nacional (MES)"
                		+ "propietario: BIQA1"));
            }catch(Exception e){
            	System.out.println(e);
            	return false;
            }
            
           
            u.highLight( ReporteMes);
            testCase.addScreenShotCurrentStep(u, "Nacional");
            clickjs( ReporteMes);
            return true;
        }
    
    
    
    
    /*
     * metodo generado para buscar con javascript executor mediante texto y hacer clic 
     * 
     * 
     * @param by
     * @author Alan Perez
     */
    

 	public void clickjs(By by) {
 		WebElement element = u.getDriver().findElement(by);
 		JavascriptExecutor executor = (JavascriptExecutor) u.getDriver();
 		executor.executeScript("arguments[0].click();", element);
 	}
 	
 	
 	
 	
 	
 	/*
     * metodo generado para seleccionar con el metodo clickjs
     * 
     * 
     * @param option
     * @author Alan Perez
     */
 	
 	public void SelectOption(String option) {
 		// System.out.println("opcion: " + option);
 		u.highLight(By.partialLinkText(option));
 		testCase.addScreenShotCurrentStep(u, "Seleccion de folders");
 		clickjs(By.partialLinkText(option));
 	}


 	/*
     * metodo generado para ejecutar reporte
     * 
     * @throws InterruptedException
     * @author Alan Perez
     */
	public boolean EjecucionReporte() throws InterruptedException {
		// TODO Auto-generated method stub
		  
          u.highLight(EjecutarReport );
          testCase.addScreenShotCurrentStep(u, "Clic en ejecutar reporte");
          clickjs( EjecutarReport );
          Thread.sleep(5000);
          return true;
	}


	
	/*
     * metodo generado para filtrar el año del reporte
     * 
     * @author Alan Perez
     */
	public boolean filtroAnio() {
		// TODO Auto-generated method stub
		 
        u.highLight(AnioReport );
        
        clickjs( AnioReport );
        return true;
	}
    
}
    
    
	

