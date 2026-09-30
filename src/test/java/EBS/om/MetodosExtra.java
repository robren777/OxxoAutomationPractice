package EBS.om;



import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;


public class MetodosExtra {
	
	SeleniumUtil u;
	TestCase testCase;
	
	/**
	 * Localizador de simbolo que aparece cuando esta cargando la pantalla
	 ******************************************************************************************/
	By xmlLoading = By.xpath("//*[@id=\"content-nav\"]/app-loader/div/div/mat-progress-spinner");
    By xmlLoading2 = By.xpath("//div[@class = 'progress-loader'][@hidden]");
    
    /*[@id="j_idt12"]/div
	/**
	 * Constructor
	 ******************************************************************************************/
	
	public MetodosExtra(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}
	
	/*
	 * METODOS - Adicionales
	 * En este apartado estaran los metodos adicionales que podemos utilizar en otras clases
	 ******************************************************************************************/
	
	//metodo que valida si un elemento existe y regresa true o false 
	public boolean existElement(By element) {
   	
		boolean exist = false;
   	
		try {
			u.getDriver().findElement(element);
			exist = true;
		}catch (Exception e) {
			exist = false;
		}
   	
		return exist;
    	
   }
	
	// Espera hasta que el elemento este disponible para hacer click 
	public WebElement explicitWaitToBeClickable(By by, Duration time) {
		WebElement element = new WebDriverWait(u.getDriver(), time).until(ExpectedConditions.elementToBeClickable(by));
		
		return element;
	}
	
	
	/**
	 * Metodo para validar que se visualice un elemento en pantalla 
	 * @param element Recibe el xpath del elemento a validar
	 * @return Retorna true o false si existe el elemento en pantalla
	 * @author Maria Due�as
	 */
	public Boolean validarVisualizacion(By element) {
		
		u.waitForLoadPage();
		Boolean exist = false;
		
		if (existElement(element)) {
			exist = true;
	        u.highLight(element);
	        testCase.addScreenShotCurrentStep("validarVisualizacion");
		    u.unhighLight(element);
		} 
	       
		return exist;
	}	
	
	/**
	 * Metodo para bajar el Scroll hasta el elemento deseado
	 * @param by Xpath del elemento deseado
	 * @author Maria Due�as
	 */
	public void scrollToElement(By by) {
		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(10));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
	}
		
	/**
	 * Metodo para esperar que la pagina termine de cargar. Espera a que el simbolo de carga
	 * propio del sistema desaparezca.
	 * @param time
	 * @return Regresa verdadero cuando el elemento ya no aparece
	 * @author Maria Due�as
	 */
	public Boolean explicitWaitToBeInvisible(Duration time) {
		Boolean element = false;
		Duration t;
		Boolean visible = false;
		
		visible = u.getDriver().findElement(xmlLoading).isDisplayed();
		
		if (visible) {
			//System.out.print("entro al primer if");
			while(element == false) {
				t = time;
				
				try {
					u.mouseOver(xmlLoading);
					element = new WebDriverWait(u.getDriver(), t).until(ExpectedConditions.invisibilityOfElementLocated(xmlLoading));
					
				} catch (Exception e) {
					element = false;
				}
				
			}
		}		
			
		return element;
	}
	
	/**
	 * Metodo para esperar que la pagina termine de cargar. Espera a que el simbolo de carga
	 * propio del sistema desaparezca.
	 * @param time
	 * @return Regresa verdadero cuando el elemento ya no aparece
	 * @author Maria Due�as
	 */
	public Boolean explicitWaitToBeInvisible2(Duration time) {
		Boolean element = false;
		Duration t;
		Boolean visible = false;
		
		visible = existElement(xmlLoading2);
		System.out.print(visible);
		
		if (visible == false) {
			while(element == false) {
				t = time;
				
				try {
					//u.mouseOver(xmlLoading);
					element = new WebDriverWait(u.getDriver(), t).until(ExpectedConditions.visibilityOfElementLocated(xmlLoading2)).isDisplayed();
					
				} catch (Exception e) {
					element = false;
				}
				
			}
		}
			
			
		return element;
	}
	
	//Metodo que valida si existen varios elementos y regresa true o false 
	public boolean existMultipleElements(By element) {
	  
		boolean exist = false;
		    
		try {
			u.getDriver().findElements(element);
			exist = true;
		}catch (Exception e) {
			exist = false;
		}
		    
		return exist;
	    
	 }
	
	//Espera hasta que el elemento indicado sea visible
	public void explicitWaitVisibilityOfElement(By by, Duration timeout) {
		WebDriverWait wait = new WebDriverWait(u.getDriver(), timeout); 
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}
	
	//Espera hasta que el elemento indicado sea invisible
	public void explicitWaitInvisibilityOfElement(By by, Duration timeout) {
		WebDriverWait wait = new WebDriverWait(u.getDriver(), timeout); 
		wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}
	
    /**
	 * Metodo que valida si el elemento esta habilitado
	 * @param by (By): localizador del elemento
	 * @return Regresa true si el elemento esta habilitado o false si no lo esta
	 * @author Andrea Contreras
	 */
	public boolean isEnable(By by) {
		
		boolean enable = false;
		
		WebElement element = u.getDriver().findElement(by);
		
		if (element.isEnabled()) {		
			enable = true;		
		}
		
		return enable;
	}
	
	/**
	 * Metodo para validar que los elementos de la pantalla no estan habilitados
	 * @author Herbe Cauich
	 * @param mes
	 * @return N/A
	 */
	public boolean isNotEnable(By by) {
		
		boolean notEnable = true;
		
		WebElement element = u.getDriver().findElement(by);
		
		if (!element.isEnabled()) {		
			notEnable = false;		
		}
		
		return notEnable;
	}
	
	
    /**
	 * Metodo que valida si el elemento esta habilitado
	 * @param by (By): localizador del elemento
	 * @return Regresa true si el elemento esta habilitado o false si no lo esta
	 * @author Andrea Contreras
	 */
	public boolean isSelected(By by) {
		
		boolean enable = false;
		
		WebElement element = u.getDriver().findElement(by);
		
		if (element.isSelected()) {		
			enable = true;		
		}
		
		return enable;
	}

	/**
	 * Metodo para dar click sobre elemento utilizando sikuli
	 * @param Folder: nombre de la carpeta dentro de Patterns
	 * @param image: nombre de la imagen
	 * @return true o false si encontro el elemento
	 * @author Maria Dueñas
	 */
	public Boolean clickElementSikuli(String Folder, String image) throws FindFailed {
		
		   //u.waitForLoadPage();
		   Boolean exist = false;
		   Screen s = new Screen();
		   
	       SikuliUtil sikuli = new SikuliUtil(); //Crea un elemento del tipo Sikuli
	       String s1 = sikuli.PatternPath(Folder); //Si la carpeta donde se indica que esta la imagen no ha sido creada, la crea
	       Pattern p1 = sikuli.PatternImage(s1 + image + ".png"); // Crea la imagen del tipo Patter
	       
	       //System.out.print("Entrando al metodo \n");
	       
	       //Pregunta si existe la imagen en pantalla
	       if (sikuli.screen.exists(p1) != null) {
	    	   //System.out.print("Entrando al if \n");
	    	   exist = true;
	           sikuli.screen.click(p1);
	       }else {
	    	   System.out.print("El elemento no existe");
	    	   //sikuli.screen.wait(p1.similar((float).60),15).click();
	       }
	       
	       testCase.addScreenShotCurrentStep("clickElementSikuli");
	       return exist;
	 }
	
	
	/**
	 * Metodo para highlight un elemento 
	 * @param element (WebElement): elemento
	 * @author Andrea Contreras
	 */
	public void highlight(WebElement element)  {
		
		JavascriptExecutor jsExecutor = (JavascriptExecutor) u.getDriver();  
		jsExecutor.executeScript("arguments[0].style.border='3px solid red'", element);
		
	}
	
	/**
	 * Metodo para elegir archivo a subir desde pestaña de windows
	 * @param Folder: Carpeta
	 * @param caja: Caja de texto
	 * @param boton: Open
	 * @param nombre: Archivo
	 * @return Retorna true si se cargo el archivo correctamente
	 * @throws FindFailed
	 * @author Maria Dueñas
	 */
	public Boolean selectFile(String Folder, String caja, String boton, String nombre) throws FindFailed {
		
		   Boolean exist = false;
		   Screen s = new Screen();
		   
	       SikuliUtil sikuli = new SikuliUtil(); //Crea un elemento del tipo Sikuli
	       String s1 = sikuli.PatternPath(Folder); //Si la carpeta donde se indica que esta la imagen no ha sido creada, la crea
	       Pattern textBox = sikuli.PatternImage(s1 + caja + ".png"); // Crea la imagen del tipo Patter
	       Pattern open = sikuli.PatternImage(s1 + boton + ".png"); // Crea la imagen del tipo Patter
	       
	       //Pregunta si existe la imagen en pantalla
	       if (sikuli.screen.exists(textBox) != null && sikuli.screen.exists(open) != null) {
	    	   exist = true;
	    	   sikuli.screen.type(textBox, s1 + nombre + ".xlsx");
	    	   testCase.addScreenShotCurrentStep("typingFile");
	           sikuli.screen.click(open);
	       }else {
	    	   System.out.print("Los elementos no existen\n");
	       }
	       
	       testCase.addScreenShotCurrentStep("selectFile");
	       return exist;
	 }
	
	/**
	 * Metodo para dar un click simulado con javascript
	 * @param By: selector
	 * @author David Valdez
	 */
	public void clickJs(By by) {
		WebElement m = u.getDriver().findElement(by).findElement(by);
		JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
		js.executeScript("arguments[0].click();", m);
	}
	
	public void click_on_ok_alert() {
		u.getDriver().switchTo().alert().accept();
	}
	
	/**
	 * Metodo para obtener el selector a partir de un webelement
	 * @param WebElement: elemento
	 * @return By: selector
	 * @author David Valdez
	 */
	public By getByFromElement(WebElement element) {
	    By by = null;
	    String[] selectorWithValue= (element.toString().split("->")[1].replaceFirst("(?s)(.*)\\]", "$1" + "")).split(":");

	    String selector = selectorWithValue[0].trim();
	    String value = selectorWithValue[1].trim();

	    switch (selector) {
	        case "id":
	            by = By.id(value);
	            break;
	        case "className":
	            by = By.className(value);
	            break;
	        case "tagName":
	            by = By.tagName(value);
	            break;
	        case "xpath":
	            by = By.xpath(value);
	            break;
	        case "cssSelector":
	            by = By.cssSelector(value);
	            break;
	        case "linkText":
	            by = By.linkText(value);
	            break;
	        case "name":
	            by = By.name(value);
	            break;
	        case "partialLinkText":
	            by = By.partialLinkText(value);
	            break;
	        default:
	            throw new IllegalStateException("locator : " + selector + " not found!!!");
	    }
	    return by;
	}
	
	/**
	 * Metodo para obtener el WebElement
	 * @param By: selector
	 * @return WebElement: elemento
	 * @author David Valdez
	 */
	public WebElement getElementBy(By by) {
		return u.getDriver().findElement(by).findElement(by);
	}
	
	/**
	 * Metodo para habilitar un elemento desactivado con 'disabled' por medio de javascript
	 * @param By: selector
	 * @author David Valdez
	 */
	public void enableElementJs(By by) {
		WebElement element = getElementBy(by);
		JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
		js.executeScript("arguments[0].removeAttribute('disabled')", element);
	}
	
	/**
	 * Metodo para deshabilitar un elemento por medio de javascript y que tenga la propiedad 'disabled'
	 * @param By: selector
	 * @author David Valdez
	 */
	public void disableElementJs(By by) {
		WebElement element = getElementBy(by);
		JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
		js.executeScript("arguments[0].setAttribute('disabled','disabled')", element);
	}
	
	/**
	 * Metodo para validar que un elemento tenga la propiedad 'disabled'
	 * @param By: selector
	 * @return true o false si tiene la propiedad disable
	 * @author Andrea Contreras
	 */
	public boolean hasDisabledAttributeJs(By by) {
		boolean disabled = false;
		WebElement element = getElementBy(by);
		//element.getAttribute("disabled");
		JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
		disabled = Boolean.parseBoolean(js.executeScript("arguments[0].hasAttribute('disabled');", element).toString());
	    return disabled;
	}
	
	/**
	 * Metodo para agregar una nueva pestaña en el navegador y cambiar el control del driver
	 * @author Andrea Contreras
	 */
	public ArrayList<String> addNewTab(){
		
		((JavascriptExecutor) u.getDriver()).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(u.getDriver().getWindowHandles()); 
		
		return tabs;
	}
	

	/**
     * Metodo para descargar y abrir un archivo desde el navegador
     * @return correct: true si abrio el archivo
     * @param String: partofname - Nombre o parte de nombre del archivo
     * @param String: browser - Navegador (Edge or Chrome)
     * @author Andrea Contreras & David Valdez
     */
    public Boolean abrirArchivo(String partofname,String browser){
        boolean correct = false;
        
        System.out.println("abrir archivo");
        ArrayList<String> tabs = addNewTab();
        u.getDriver().switchTo().window(tabs.get(tabs.size()-1));    
        
        String xpathFile = "";
        switch(browser) {
        	case "Edge":
        		u.get("edge://downloads/all");
        		xpathFile = "//*[@id=\"show1\"]";
        	break;
        	
        	case "Chrome":
        		u.get("chrome://downloads/");
        	break;
        }       
        
        u.waitForLoadPage();        
        testCase.addScreenShotCurrentStep("downloads"); 
        
        try {
        	WebElement done = (new WebDriverWait(u.getDriver(), Duration.ofSeconds(100)))
        			   .until(ExpectedConditions.elementToBeClickable(By.xpath(xpathFile)));
        	WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(355));
        	wait.until(ExpectedConditions.visibilityOf(done));
        	System.out.println("archivo descargado");
        	
        	//u.click(By.xpath("//*[contains(text(),'" + partofname + "')]"));
        	u.click(By.xpath("//*[@id=\"open_file1\"]"));
        	
        	correct = true;
        }catch(Exception ex) {
        	ex.printStackTrace();
        	System.out.println("No se encontro el archivo buscado");
        }
        
        u.hardWait(15);        
        testCase.addScreenShotCurrentStep("captura archivo");
        return correct;
    }	
    
    /**
	 * Metodo para buscar en los nodos hijos de una hoja y dar click en el seleccionado
	 * @return correct: true si fue posible dar click
	 * @param String: xpath_parent - Nodo padre
     * @param String: nameofchildren - Nombre del nodo hijo
     * @author David Valdez
	 */
	public Boolean click_on_child(String xpath_parent,String child){
		List<WebElement> elem = u.listElements(By.xpath(xpath_parent+"/descendant::*[.='"+child+"']"));
		
		for(WebElement i : elem) {
			if(i.getText().contains(child)) {
				i.click();
				return true;
			}
		}
		return false;		
	}
	
	/**
	 * Metodo para subrayar, tomar captura y dar click en xpath
	 * @param xpath
	 * @return 
	 * @author Andrea Cano
	 */
	public void clickByXpath(String xpath) {
		u.highLight(By.xpath(xpath));
		testCase.addScreenShotCurrentStep(u, "clickopcion");
		u.unhighLight(By.xpath(xpath));
		u.click(By.xpath(xpath));
	}
	
	/**
	 * Metodo para seleccionar la ipcion de un select
	 * @param 
	 * @return 
	 * @author Yair Lizarriaga
	 */
	public Boolean selectOption(By select, By opciones, String opcion) {
		Boolean existOption = false;

		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(select));
		u.hardWait(2);
		element.click();
		List<WebElement> allOptions = u.getDriver().findElements(opciones);

		for (WebElement option : allOptions) {
			if (option.getText().equals(opcion)) {// busca la opcion que se pasa por parametro
				u.hardWait(3);
				option.click(); // da clic a esa opcion
				u.hardWait(1);
				existOption = true;
				break;
			}
		}

		u.highLight(select);
		testCase.addScreenShotCurrentStep(u, "selectOption");
		u.unhighLight(select);
		return existOption;
	}

	/**
	 * Metodo para ingresar un valor en un input
	 * @param 
	 * @return 
	 * @author Yair Lizarriaga
	 */
	public void typingInput(By by, String dato) {
		u.sendKeys(by, dato);
		u.highLight(by);
		testCase.addScreenShotCurrentStep(u, "typingInput");
		u.unhighLight(by);
	}

	/**
	 * Metodo para dar click en un boton/opcion
	 * @param 
	 * @return 
	 * @author Yair Lizarriaga
	 */
	public void clickBoton(By by) {
		u.highLight(by);
		testCase.addScreenShotCurrentStep(u, "clickBoton");
		u.unhighLight(by);
		u.click(by);
	}
	
	/**
	 * Metodo para obtener fecha actual con el formato indicado
	 * @param 
	 * @return fecha
	 * @author Andrea Contreras
	 */
	public String obtenerFechaActual(String formato) {
		
		SimpleDateFormat formatFecha = new SimpleDateFormat(formato);
		//Date date = Calendar.getInstance().getTime();
		//String fecha = formatFecha.format(date);
		
		LocalDateTime datetime = LocalDateTime.now();
		
		String fecha = DateTimeFormatter.ofPattern(formato).format(datetime); 
		
		return fecha;
	}
	
	public boolean isAlphaNumeric(String text) {
        return text != null && text.matches("^[a-zA-Z0-9]*$");
    }
 

	public static File getLastModified(String directoryFilePath)
	{
	    File directory = new File(directoryFilePath);
	    File[] files = directory.listFiles(File::isFile);
	    long lastModifiedTime = Long.MIN_VALUE;
	    File chosenFile = null;

	    if (files != null)
	    {
	        for (File file : files)
	        {
	            if (file.lastModified() > lastModifiedTime)
	            {
	                chosenFile = file;
	                lastModifiedTime = file.lastModified();
	            }
	        }
	    }

	    return chosenFile;
	}
	
	public static boolean openLastFileDownloadFolder() {
		String home = System.getProperty("user.home");
		home = home + "\\Downloads";
//		System.out.println(home);
//		System.out.println(getLastModified(home));
		
		File file = new File(getLastModified(home).getAbsolutePath());
        
        //first check if Desktop is supported by Platform or not
        if(!Desktop.isDesktopSupported()){
            System.out.println("Desktop is not supported");
            return false;
        }
        
        Desktop desktop = Desktop.getDesktop();
        if(file.exists()) {
        	try {
				desktop.open(file);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
        } else {
        	return false;
        }
			
        
		return true;
	}

	public static void main(String[] args) throws IOException {
		String home = System.getProperty("user.home");
		home = home + "\\Downloads";
		System.out.println(home);
		System.out.println(getLastModified(home));
		
		File file = new File(getLastModified(home).getAbsolutePath());
        
        //first check if Desktop is supported by Platform or not
        if(!Desktop.isDesktopSupported()){
            System.out.println("Desktop is not supported");
            return;
        }
        
        Desktop desktop = Desktop.getDesktop();
        if(file.exists()) desktop.open(file);
        
        
	}
	
	
	/**
	 * Metodo para validar si existe una alerta con un mensaje especifico 
	 * @author Andrea Contreras
	 * @return boolean
	 */
	public boolean comprobarMensajeAlerta(String mensaje) {
		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(15));
		wait.until(ExpectedConditions.alertIsPresent());
		String ventanaE = u.getDriver().switchTo().alert().getText();
		System.out.println("Texto en mensaje de alerta " + ventanaE);
		if (ventanaE.equals(mensaje)) {
			u.screenShotFull("captura");
			testCase.addScreenShotCurrentStep("captura", false);			
			return true;
		}
		return false;
	}
	
	/**
	 * Metodo para cargar archivo excel
	 * @return Retorna true si se cargo correctamente
	 * @throws FindFailed
	 * @author Andrea Contreras
	 */
	public Boolean cargarArchivo(By localizador, String filePath) throws FindFailed {
		
		Boolean correct = false;
		try {
			u.highLight(localizador);
			testCase.addScreenShotCurrentStep(u, "cargarArchivo");
			u.sendKeys(localizador, filePath);
			testCase.addScreenShotCurrentStep(u, "cargarArchivo");
			u.unhighLight(localizador);
			correct = true;
		}catch(Exception e) {
			e.printStackTrace();
			testCase.addScreenShotCurrentStep(u, "Error");
		}
		return correct;

    }
}

