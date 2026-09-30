package WorkFlows.util.PlanogramasNexxo;

import java.io.File;
import java.time.Duration;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;

import WorkFlows.util.ultimus.pomUltimus;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class MonitoreoUtils {
	
	

	SeleniumUtil u;
	TestCase testCase;
	private WebDriverWait wait;
	Random random = new Random();
	
	private SikuliUtil sikuli = new SikuliUtil();
	private Screen screen = new Screen();	

	private Pattern p1;
	private String s1;
	private String folder = "sipreBara";
	private String mainTab = "";
	private String newTab = "";



	
	public MonitoreoUtils (SeleniumUtil u, TestCase testCase ) {
		this.u = u;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		this.testCase = testCase;


	}
	
	By menu = By.xpath("//*[@id=\"MenuG\"]");
	By planogramasAprobados = By.cssSelector("li.dynamic:nth-child(2) > a:nth-child(1)");
	By selectPlaza_Region = By.id("ContentPlaceHolder1_cphEngine_ddlPlazas");
	By inputExcel = By.id("ContentPlaceHolder1_cphEngine_butExportarExcel");
	By inputBusqueda = By.id("ContentPlaceHolder1_cphEngine_butBuscar");
	By tablaPlanogramasAprobados = By.id("ContentPlaceHolder1_cphEngine_grdPlanogramasAprobados");
	
	By selectPlazaInicio = By.id("ContentPlaceHolder1_cphEngine_DdlPlazas");
	By selectTienda = By.cssSelector("#ContentPlaceHolder1_cphEngine_DdlTienda");
	By selectOrigen = By.cssSelector("#ContentPlaceHolder1_cphEngine_DdlOrigen");
	By inputItem = By.cssSelector("#ContentPlaceHolder1_cphEngine_TxtArticulo");
	
	By btnBuscar = By.cssSelector("#ContentPlaceHolder1_cphEngine_ButBuscar");
	By procesando = By.cssSelector("#cargando > div:nth-child(1) > div:nth-child(1)");
	
	
	/**
	 * Descarga un archivo Excel con la informacion de los articulos
	 * de una plaza
	 */
	public void monitoreo() {
		
		try {
			u.hardWait(2);
			u.waitWebElement(menu, 120, 3); 
			u.getDriver().manage().window().maximize();
			u.hardWait(2);
			u.mouseOver(menu);
			testCase.addTextEvidenceCurrentStep("Entrar a la pantalla de Planogramas aprobados");
			testCase.addScreenShotCurrentStep(u, "");
			u.hardWait(2);
			u.mouseOverAndClick(planogramasAprobados);
			
			u.waitWebElement(inputExcel, 120, 3);
			
			testCase.addTextEvidenceCurrentStep("Selecciona una nueva region");
			u.highLight(selectPlaza_Region);
			
			selectOption("10CTL:Coatzacoalcos",selectPlaza_Region);
			testCase.addScreenShotCurrentStep(u, "");

			u.hardWait(2);
			testCase.addTextEvidenceCurrentStep("Click en boton Excel");
			u.highLight(inputExcel);
			testCase.addScreenShotCurrentStep(u, "");
			u.hardWait(2);
			u.click(inputExcel);
			
			testCase.addTextEvidenceCurrentStep("Se genera el reporte de planogramas");
			
			String BASE_PATH = System.getProperty("user.dir");
			File bas = new File("C:/Users/" + System.getProperty("user.name") + "/Downloads/");

			File rutaExcel =  new File ("\\PlanogramasAprobados_10CTL_"+ getDate()+".xls");

			String ruta = bas.toString()+rutaExcel;
			System.out.println(" -> "+ruta);
	        Runtime.getRuntime().exec("cmd /c start "+ruta);
	        
	        u.hardWait(15);
			testCase.addScreenShotCurrentStep("");
	        u.hardWait(2);



			
		}catch(Exception e) {
			
			System.out.println(" -> " + e);
			throw new AssertionError("Error al entrar a Monitoreo");
			
		}

		
	}
	
	/**
	 * Regresa la fecha actual con el formato dd/MM/yyyy para buscar 
	 * registros en la db 
	 * @return
	 * @author alicia.ceja
	 */
	public String getDate() {
		
		   SimpleDateFormat dtf = new SimpleDateFormat("dd-MMM-yy",  new Locale("es","ar"));
	        Calendar calendar = Calendar.getInstance();

	        Date dateObj = calendar.getTime();
	        String formattedDate = dtf.format(dateObj);
	        System.out.println(formattedDate);
	        
	        return formattedDate;
	        
	}
	
	
	
	/**
	 * Busca todos los items que pertenecen a una plaza
	 * y los muestra en una tabla
	 */
	
  public void buscar(String plaza) {
		try {
			
			u.waitWebElement(menu, 120, 3);
			u.getDriver().manage().window().maximize();
			u.hardWait(3);
			u.mouseOver(menu);
			testCase.addTextEvidenceCurrentStep("Entrar a la pantalla de Planogramas aprobados");
			testCase.addScreenShotCurrentStep(u, "");
			u.mouseOverAndClick(planogramasAprobados);
			
			u.waitWebElement(inputExcel, 120, 3);
			testCase.addTextEvidenceCurrentStep("Seleccionar una region");
			u.highLight(selectPlaza_Region);
			
			selectOption(plaza,selectPlaza_Region);
			u.hardWait(2);
			testCase.addScreenShotCurrentStep(u, "");
			
			testCase.addTextEvidenceCurrentStep("Realizar una busqueda");
			u.highLight(inputBusqueda);
			u.click(inputBusqueda);
			u.hardWait(1);
			testCase.addScreenShotCurrentStep(u, "");
			
			u.waitWebElement(tablaPlanogramasAprobados, 120, 2);
			
			By articulo = By.xpath("//table[contains(@id,'ContentPlaceHolder1_cphEngine_grdPlanogramasAprobados')]//tr//td");
			
			u.highLight(articulo);	

			u.hardWait(2);
		}catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Error al buscar los articulos de la plaza seleccionada");
		}
	
	}
	
  /**
   * Busca en el aplicativo que el articulo se muestre en la pantalla
   * de planogramas aprobados
   * @param item
   */
  
  public void buscarArticuloPlanoAprobados (String item) {
	
	  try {
		  u.mouseOver(menu);
		  u.mouseOverAndClick(planogramasAprobados);
		  
		  	u.highLight(selectPlaza_Region);
			
			selectOption("10CTL:Coatzacoalcos",selectPlaza_Region);
			u.hardWait(2);
			
			u.highLight(inputBusqueda);
			u.click(inputBusqueda);
			
			u.waitWebElement(tablaPlanogramasAprobados, 120, 2);
			
			By articulo = By.xpath("//table[contains(@id,'ContentPlaceHolder1_cphEngine_grdPlanogramasAprobados')]//tr//td[1][text( )=\""+item+"\"]");
			
			u.highLight(articulo);
			
	  }catch(Exception e) {
		  System.out.println(" -> " + e);
			throw new AssertionError("Fallo al encontrar el articulo");
	  }
	  

  }
	
	public void selectOption(String op, By selectElement) {
		
		Select dropdown=  new Select (u.getDriver().findElement((selectElement)));
		dropdown.selectByVisibleText(op);
	}
	
	
	/**
	 * Inicia Sesion en ultimus y selecciona el aplicativo
	 * monitoreo 
	 */
	
	public void iniciaSesionMonitoreo() {
		try {
			
			pomUltimus pom = new pomUltimus(u);
			String userUltimus = "1779064";
			pom.loginUltimus(userUltimus, "");
			testCase.addScreenShotCurrentStep(u, "");
			
		
			pom.seleccionarVista("INICIAR");
			testCase.addScreenShotCurrentStep(u, "");
				
			pom.openMonitoreo();
			testCase.addScreenShotCurrentStep(u, "");
			
			u.waitWebElement(menu, 120, 2);
			
		}catch(Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Error al iniciar sesion en ultimus");
		}
		
	
	}
	
	public String buscaArtiSembrado(String plaza, String tienda, String arti) {
		
		String fecha="";
		
		u.waitWebElement(menu, 120, 3);
		u.getDriver().manage().window().maximize();
		
		testCase.addTextEvidenceCurrentStep("Selecciona una Plaza");
		u.highLight(selectPlazaInicio);
		
		selectOption(plaza,selectPlazaInicio);
		testCase.addScreenShotCurrentStep(u, "");
		u.hardWait(2);
		u.unhighLight(selectPlazaInicio);
		
		testCase.addTextEvidenceCurrentStep("Selecciona Origen");
		u.highLight(selectOrigen);
		
		selectOption("Sembrado",selectOrigen);
		testCase.addScreenShotCurrentStep(u, "");
		u.hardWait(2);
		u.unhighLight(selectOrigen);

		
		testCase.addTextEvidenceCurrentStep("Selecciona una Tienda");
		u.highLight(selectTienda);
		
		selectOption(tienda,selectTienda);
		testCase.addScreenShotCurrentStep(u, "");
		u.hardWait(2);
		u.unhighLight(selectTienda);

		testCase.addTextEvidenceCurrentStep("Ingresa Artículo");
		u.highLight(inputItem);
		u.sendKeys(inputItem, arti);
		
		testCase.addScreenShotCurrentStep(u, "");
		u.hardWait(2);
		u.unhighLight(inputItem);
		
		testCase.addTextEvidenceCurrentStep("Busca Artículo");
		u.highLight(btnBuscar);
		u.click(btnBuscar);
		load();


		
		return fecha;
		
	}
	
	
	public void load() {
		try {
			u.hardWait(2);
			while (u.getDriver().findElement(procesando).isDisplayed()) {
				System.out.println("Procesando...");
				u.hardWait(2);
			}
		} catch (Exception e) {

		}
	}

	

}
