package WM.om;

import java.time.Duration;

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
	 * En este apartado estaran los métodos adicionales que podemos utilizar en otras clases
	 ******************************************************************************************/
	
	//Método que valida si un elemento existe y regresa true o false 
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
	 * Método para validar que se visualice un elemento en pantalla 
	 * @param element Recibe el xpath del elemento a validar
	 * @return Retorna true o false si existe el elemento en pantalla
	 * @author María Dueñas
	 */
	public Boolean validarVisualizacion(By element) {
		
		u.waitForLoadPage();
		Boolean exist = false;
		
		if (existElement(element)) {
			exist = true;
	        u.highLight(element);
	        testCase.addScreenShotCurrentStep(u, "validarVisualizacion");
		    u.unhighLight(element);
		} 
	       
		return exist;
	}	
	
	/**
	 * Método para bajar el Scroll hasta el elemento deseado
	 * @param by Xpath del elemento deseado
	 * @author María Dueñas
	 */
	public void scrollToElement(By by) {
		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(120));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
	}
		
	/**
	 * Método para esperar que la pagina termine de cargar. Espera a que el simbolo de carga
	 * propio del sistema desaparezca.
	 * @param time
	 * @return Regresa verdadero cuando el elemento ya no aparece
	 * @author María Dueñas
	 */
	public Boolean explicitWaitToBeInvisible(int time) {
		Boolean element = false;
		Duration t;
		Boolean visible = false;
		
		visible = u.getDriver().findElement(xmlLoading).isDisplayed();
		
		if (visible) {
			while(element == false) {
				t = Duration.ofSeconds(time);
				
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
	 * @author Maria Due�as
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
	    	   System.out.print("El elemento no existe\n");
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
	 * Metodo para elegir archivo a subir desde pesta�a de windows
	 * @param Folder: Carpeta
	 * @param caja: Caja de texto
	 * @param boton: Open
	 * @param nombre: Archivo
	 * @return Retorna true si se cargo el archivo correctamente
	 * @throws FindFailed
	 * @author Maria Due�as
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
}
