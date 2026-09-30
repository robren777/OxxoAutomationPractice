package WorkFlows.util.PlanogramasNexxo;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;

import WorkFlows.util.GlobalVariables;
import modelo.TestCase;
import utils.FileUtil;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class sembradoUtils {
	

	SeleniumUtil u;
	TestCase testCase;
	private WebDriverWait wait;
	SikuliUtil sikuli = new SikuliUtil();
	
	String pathImages = sikuli.PatternPath("WorkFlows"+File.separator+"Planogramas");
	
	@SuppressWarnings("unused")
	public sembradoUtils() {
		
	}
	public sembradoUtils (SeleniumUtil u,TestCase testCase ) {
		this.u = u;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		this.testCase = testCase;
	}
	
	Pattern imgMoreInfo = sikuli.PatternImage(pathImages + "moreInfo.png");
	Pattern imgGoOn = sikuli.PatternImage(pathImages + "goOn.png");
	Pattern imgSaveDoc = sikuli.PatternImage(pathImages + "save.png");
	Pattern imgSi = sikuli.PatternImage(pathImages + "si.png");
	Pattern imgSelectSi = sikuli.PatternImage(pathImages + "si_.png");
	Pattern imgClose = sikuli.PatternImage(pathImages + "close.png");
	Pattern imgLoad = sikuli.PatternImage(pathImages + "load.png");

	//Sembrado nexxo Bloque 2
	Pattern imgCombinabilidad = sikuli.PatternImage(pathImages+"combinabilidad.png");
	Pattern aceptarComb = sikuli.PatternImage(pathImages+"aceptarComb.png");
	Pattern aceptar = sikuli.PatternImage(pathImages+"aceptar.png");
	Pattern selArchivo = sikuli.PatternImage(pathImages+"selecArchivo.png");
	Pattern fileName = sikuli.PatternImage(pathImages+"fileName.png");
	Pattern fileName2 = sikuli.PatternImage(pathImages+"fileName2.png");
	Pattern btnOpen = sikuli.PatternImage(pathImages+"open.png");
	Pattern btnOpen2 = sikuli.PatternImage(pathImages+"open2.png");
// Pattern imgExcel= sikuli.PatternImage(pathImages+"closeEx.png");
	Pattern imgExcel =sikuli.PatternImage(pathImages+"discard.png");
	Pattern closeExcel = sikuli.PatternImage(pathImages+"closeFile.png");
	Pattern imgArchivoSubido = sikuli.PatternImage(pathImages+"archCargado.png");
	
	By cargando = By.xpath("/html/body/div[5]/div[2]/strong/h1[1]");
	
	By advance = By.cssSelector("#details-button");
	By contin = By.cssSelector("#proceed-link");
	
	By inputUser = By.xpath("//*[@id=\"login:userName\"]");
	By inputPass = By.xpath("//*[@id=\"login:userPass\"]");
	By btnIniciaSesion = By.xpath("//*[@id=\"login:loginButton\"]");
	
	//INICIO PORTAL OXXO
	By btnInicio = By.cssSelector(".espacio");
	By btnSembradoPlaza = By.xpath("//a[text()='Sembrado Plaza']");

	//SEMBRADO PLAZA
	
	By opSembradoPlaza = By.cssSelector("#e0_74o");
	By opSembrado = By.cssSelector("#e0_76i");  //no utilizar .click
	
	By aceptaNavegador = By.cssSelector(".ui-button-text");
	
	//SEMBRADO DE LEGOS
	
	By selectPlaza = By.id("cmbPlazaId");
	By selectTienda = By.id("idCmbTienda");
	By selectSeccion = By.id("idCmbSeccion");
	By selectFase = By.name("idFaseSembrado");
	
	By btnEnviar = By.id("butEnviar");
	
	//By btnAceptar = By.xpath("/html/body/div[3]/div[3]/div/button/span[2]"); //
	By btnAceptar = By.xpath("//html//body//div[2]//div[3]//div//button[1]//span"); //
	By mensajeSembradoInventario = By.id("popupContainerInv2");
	//INVENTARIO ARTICULOS
	
	By fecha = By.xpath("//table[contains(@class,'newTable6')]//td[3]");
	By btnAceptaInventario = By.id("btnAceptarId");
	By btnAceptaInventarioSem = By.id("ui-button ui-widget ui-state-default ui-corner-all ui-button-text-icon-primary"); //

	By mensajeSembrado = By.id("divMsjSem");
	
	
	/**
	 * Metodo para abrir archivo y validar columnas para sembrado plaza,
	 * el nombre de la columna es el nombre que le ponga a la imagen de sikuli
	 * correspondiente a la columna a validar, para usar este metodo se puede 
	 * utilizar el metodo constructor de pomSembradoNexxo(TestCase testCase)
	 * @throws FindFailed 
	 *///Tania Dena
	public void validaColArchivo(String nameArchivo, String[] columnaV) throws FindFailed{
        
		String path = GlobalVariables.FILE_PATH + nameArchivo;
		System.out.println("Busca archivo en "+path);
		
		Boolean existe=FileUtil.fileExists(path);
		
		if (existe) {
			FileUtil.openFile(path);
			//sikuli.screen.wait(imgExcel,20).click();
			System.out.println("ya abrio el archivo");
			
		} 
		assertTrue(existe,"error, no se pudo abrir el archivo");
		
			 for(int i=0;i<columnaV.length;i++) {
				 System.out.println("Validando columna en archivo "+columnaV[i]);
				 List <String> columnas= new ArrayList<>(Arrays.asList(columnaV));
				
				String name=columnas.get(i);
				String rename=  name.replaceAll("\"", "");
				 
					String pathColumna = pathImages+rename;
					System.out.println(pathColumna);
					Pattern columna = sikuli.PatternImage(pathColumna);
					sikuli.screen.wait(columna, 20).click();
			 }
			 sikuli.screen.wait(closeExcel, 20).click();
	}
	
	/**
	 * metodo para cargar archivo en 
	 * sembrado plaza>administrador>carga de archivos
	 *///Tania Dena
	public void cargarArchivoVal(String opArchivo,String nameArchivo) throws FindFailed {
		String path = GlobalVariables.FILE_PATH + nameArchivo;
		 sikuli.screen.wait(selArchivo).click();
		 sikuli.screen.wait(fileName).click();
		 sikuli.screen.type(path);
		 sikuli.screen.wait(btnOpen).click();
	}
	
	/**
	 * metodo para aceptar mensaje de archivo cargado
	 * en sembrado plaza>administrador>carga de archivos
	 * 
	 *///Tania Dena
	public void msgArchivoCargado() throws FindFailed{
			sikuli.screen.wait(imgArchivoSubido, 20);
			//sikuli.screen.click(aceptarComb);
			try {
				sikuli.screen.click(aceptarComb);
				} catch (FindFailed e) {
					sikuli.screen.click(aceptar);
				}
	}
	
	/**
	 * metodo para aceptar mensaje de combinacion creada
	 * en sembrado plaza>administrador>combinabilidad
	 * 
	 *///Tania Dena
	public void msgCombinabilidad() throws FindFailed{
			sikuli.screen.wait(imgCombinabilidad, 20);
			sikuli.screen.click(aceptarComb);
	}
	
	
	public void login (String user, String ps) {
		try {
			/*u.waitWebElement(advance, 120, 3); 

			u.click(advance);
			u.hardWait(2);
			u.click(contin);*/
			u.waitWebElement(inputUser, 120, 3);
			u.getDriver().manage().window().maximize();

			u.highLight(inputUser);
			u.sendKeys(inputUser, user);
			u.hardWait(2);
			System.out.print(ps);

			u.highLight(inputPass);
			u.sendKeys(inputPass, ps);
					
			u.highLight(btnIniciaSesion);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(3);
			u.mouseOverAndClick(btnIniciaSesion);
			
			u.waitWebElement(btnInicio, 120, 4);
			
		}catch(Exception e) {
			System.out.println("Error -> " + e);
			throw new AssertionError("Error al iniciar sesion ");
		}
		
	}
	
	
	public void ingresaSembradoTienda () {
		
		try {
			
			u.highLight(btnSembradoPlaza);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(2);
			
			u.click(btnSembradoPlaza);
			
			u.hardWait(4);
			
			u.scrollToElement(opSembradoPlaza);
			u.mouseOver(opSembradoPlaza);
			u.highLight(opSembrado);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(3);
			
			u.mouseOverAndClick(opSembrado);
			u.hardWait(3);
			testCase.addScreenShotCurrentStep("");
			
		}catch(Exception e) {
			System.out.println("Error -> " + e);
			throw new AssertionError("Error al ingresar a Sembrado tienda ");
		}			
	}
	
	
	public void sembrarTienda(String plaza, String tienda, String seccion, String fase){
	try {
		
		u.waitWebElement(selectPlaza, 120, 3);
		
		testCase.addBoldTextEvidenceCurrentStep("Plaza");
		u.highLight(selectPlaza);
		selectOption(plaza,selectPlaza);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(3);
		u.unhighLight(selectPlaza);
		
		testCase.addBoldTextEvidenceCurrentStep("Tienda");
		u.highLight(selectTienda);
		selectOption(tienda,selectTienda);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(3);
		u.unhighLight(selectTienda);
		
		testCase.addBoldTextEvidenceCurrentStep("Sección");
		u.highLight(selectSeccion);
		selectOption(seccion,selectSeccion);
	//	load();
		u.hardWait(7);
		
		testCase.addScreenShotCurrentStep("");
		u.hardWait(3);
		u.unhighLight(selectSeccion);
		
		testCase.addBoldTextEvidenceCurrentStep("Fase");
		u.highLight(selectFase);
		selectOption(fase,selectFase);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(3);
		u.unhighLight(selectFase);
		
		u.hardWait(3);
		
		testCase.addBoldTextEvidenceCurrentStep("Click en boton enviar");
		u.highLight(btnEnviar);
		u.click(btnEnviar);
		
		u.waitWebElement(mensajeSembrado, 12, 4);

		String mensaje = u.getDriver().findElement(mensajeSembrado).getText();
		System.out.println("mensaje ----> "+mensaje); 
		
		if (mensaje.equals("La tienda ya ha sido enviada, ¿desea cambiar la fecha de envio?")) {
			testCase.addScreenShotCurrentStep("");
			u.hardWait(2);		
			System.out.print("entre 1"); 
			if(sikuli.screen.exists(imgSi)!= null) {
				
				sikuli.screen.wait(imgSi, 20).click();
				
			}else {
				sikuli.screen.wait(imgSelectSi, 20).click();

			}				
		}
		
		u.hardWait(3);
		u.waitWebElement(mensajeSembrado, 12, 4);

		mensaje = u.getDriver().findElement(mensajeSembrado).getText();
		u.hardWait(2);
		System.out.println("mensaje ----> "+mensaje); 

		
		if (mensaje.equals("¿Desea Enviar los Articulos?")) {
			testCase.addScreenShotCurrentStep("");
			u.hardWait(2);		
			System.out.print("entre 1"); 
			if(sikuli.screen.exists(imgSi)!= null) {
				
				sikuli.screen.wait(imgSi, 20).click();
				
			}else {
				sikuli.screen.wait(imgSelectSi, 20).click();

			}				 
		}
		
		u.hardWait(3);

		u.waitWebElement(mensajeSembrado, 1000, 2);
		mensaje = u.getDriver().findElement(mensajeSembrado).getText();
		
		while(!mensaje.equals("¿Desea Iniciar el Reporte de Inventario?")) {
			u.hardWait(3);

			System.out.println("Cargando.."); 
			System.out.println(mensaje); 

			mensaje = u.getDriver().findElement(mensajeSembrado).getText();

		}
	
		
		if (mensaje.equals("¿Desea Iniciar el Reporte de Inventario?")) {
			System.out.print("mensaje ----> "+mensaje); 

			System.out.print("entre 2"); 

			testCase.addScreenShotCurrentStep("");

			u.hardWait(2);		
			
			if(sikuli.screen.exists(imgSi)!= null) {
				
				sikuli.screen.wait(imgSi, 20).click();
				
			}else {
				sikuli.screen.wait(imgSelectSi, 20).click();

			}

		}

		
	}catch(Exception e) {
		System.out.println("Error -> " + e);
		throw new AssertionError("Error al Sembrar tienda ");
	}
	}
	
	/**
	 * Selecciona una opción del elemento select
	 * @param op
	 * @param selectElement
	 */

	public void selectOption(String op, By selectElement) {
		
		Select dropdown=  new Select (u.getDriver().findElement((selectElement)));
		dropdown.selectByVisibleText(op);
	}
	
	public void load() throws FindFailed{
		try {
			u.hardWait(2);
			while (sikuli.screen.exists(imgLoad, 0)!=null) {
				System.out.println("Cargando...");
				
				u.hardWait(15);
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void goOn() throws FindFailed {
		sikuli.screen.wait(imgMoreInfo, 20).click();
		sikuli.screen.wait(imgGoOn, 20).click();
		
		//while (sikuli.screen.findText(""))


		
	}
	
	public void saveDownloadDoc() throws FindFailed {
		sikuli.screen.wait(imgSaveDoc, 20).click();
	}
	
	/**
	 * Cambia el foco a la aventana indicada donde 0 = Pantalla inicial
	 * y 1 = Pantalla nueva
	 * @param pantalla
	 * @author alicia.ceja
	 */
	
	 public  void cambiarVentana(int pantalla) {
	        try {
	            ArrayList<String> tabs = new ArrayList<String> (u.getDriver().getWindowHandles());
	            u.getDriver().switchTo().window(tabs.get(pantalla));
	        } catch (Exception e) {
	            
	        }
	    }
	 
	 
	 public void inventarioArticulos() throws FindFailed {

			goOn();
			cambiarVentana(1);
			
			u.waitWebElement(btnAceptaInventario, 30, 2);
			testCase.addBoldTextEvidenceCurrentStep("Selección de día para el sembrado");
			testCase.addScreenShotCurrentStep("");
			u.hardWait(2);
			u.click(fecha);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(3);

			
			testCase.addBoldTextEvidenceCurrentStep("Selección de [Aceptar]");
			u.highLight(btnAceptaInventario);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(3);
			u.click(btnAceptaInventario);
			goOn();
			testCase.addScreenShotCurrentStep("");

			saveDownloadDoc();
			testCase.addScreenShotCurrentStep("");
			u.hardWait(3);
			sikuli.screen.wait(imgClose, 20).click();
		//	cambiarVentana(1);
			u.waitWebElement(mensajeSembradoInventario, 120, 4);
			u.highLight(mensajeSembradoInventario);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(3);
	 }
}
