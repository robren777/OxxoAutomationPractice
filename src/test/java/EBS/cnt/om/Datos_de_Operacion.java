
package EBS.cnt.om;


import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import EBS.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Datos_de_Operacion {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;

	/**
	 * Localizadores y métodos del Menú Principal o Home del portal Catalogo
	 * Nacional de Tiendas
	 ******************************************************************************************/
	/*
	 * Localizadores login portal CNT Captura de Datos
	 */
	By TiendaE = By.xpath("//*[text()=\"Seleccionar Tienda\"]");
	
	/*
	 * Localizadores login portal CNT Captura de Datos Menu >Datos de operacion
	 */
	By DatosOp = By.xpath("//*[@target=\"framedatos\"][2]");
	By frame = By.xpath("//frame[contains(@src,'app')]");
	By framed = By.xpath("//frame[contains(@src,'/tiendas')][@name=\"framedatos\"]");// frameoper
	
	
	/*
	 * Localizadores Formulario Datos de OP
	 *****************************************************************************************/
	By cot = By.xpath("//select[@name=\"p_lid_caracteristica_operacion\"]");
	By BtnActualizar = By.xpath("//input[@name=\"BtnActualiza\"]");
	By xmlContainer = By.xpath("//*[@id=\"content-nav\"]");
	By xmlPEP = By.xpath("//*[@id=\"tituloInicio\"]");

	By SelectBankDolar = By.xpath("//*[@name=\"p_lid_banco_venta_diaria_usd\"]");
//	By SelectBankDolar = By.xpath("//*[@name=\"p_lid_banco_personal\"]");
//	p_lid_banco_venta_diaria_mxn
	By BANCO = By.xpath("//select[@name='p_lid_banco_venta_diaria_mxn']");
	
	By empresa = By.xpath("//select[@name='p_lid_empresa_pertenece']");
	
	//p_retek_district
	By asesor = By.xpath("//select[@name='p_retek_district']");
	/**
	 * Metodo para seleccionar el Frame correcto y dar clic a datos de operacion
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return N/A
	 */
	public void DatoOp() {
//		u.getDriver().switchTo().frame("framemenu");
		String winHandleBefore = u.getDriver().getWindowHandle();

		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

//		u.waitForLoadPage();
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
//		u.highLight(TiendaE);

		u.highLight(frame);
		u.getDriver().switchTo().frame("framemenu");
		u.highLight(DatosOp);
		testCase.addScreenShotCurrentStep(u, "DatosOp");
		u.click(DatosOp);

		u.getDriver().switchTo().window(winHandleBefore);
		u.waitForLoadPage();

	}

	/**
	 * Metodo para seleccionar el select de proveedores (Campo caracteristicas de
	 * operacion de la tienda) 
	 * @author Baltazar Lugo
	 * @param cara String (Caracteristicas del proveedor)
	 * @return N/A
	 */
	public void capturaDatosOp(String cara) {
		String winHandleBefore = u.getDriver().getWindowHandle();

		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

//		u.waitForLoadPage();
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
//		u.highLight(TiendaE);

		u.hardWait(10);
		u.getDriver().manage().window().maximize();
		u.highLight(framed);
		u.getDriver().switchTo().frame("framedatos");
		u.highLight(empresa);		
		Select DistritoS1 = new Select(u.getDriver().findElement(empresa));
		DistritoS1.selectByValue("CC_OXXO");
		u.hardWait(1);
		u.highLight(cot);
		Select DistritoS = new Select(u.getDriver().findElement(cot));
		DistritoS.selectByVisibleText(cara);
		testCase.addScreenShotCurrentStep(u, "valor2");
		u.hardWait(1);
		MetodosExtra ME = new MetodosExtra(u, testCase);
		ME.scrollToElement(asesor);
		u.highLight(asesor);	
		Select DistritoS3 = new Select(u.getDriver().findElement(asesor));
		DistritoS3.selectByIndex(2);
		testCase.addScreenShotCurrentStep(u, "valor3");
		u.hardWait(1);
//		u.getDriver().switchTo().window(winHandleBefore);
		u.waitForLoadPage();
	}
	
	/**
	 * Metodo para seleccionar banco mexico pesos
	 * 
	 * @author Baltazar Lugo
	 * @param NA
	 * @return N/A
	 */
	public void BANCOMEX() {
		
		MetodosExtra ME = new MetodosExtra(u, testCase);
		ME.scrollToElement(BANCO);
		u.highLight(BANCO);
			Select tama = new Select(u.getDriver().findElement(BANCO));
			tama.selectByValue("1");
			testCase.addScreenShotCurrentStep(u, "valor4");
		
	}

	/**
	 * Metodo dar click boton actualizar en el formulario
	 * 
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return N/A
	 */
	public void clickBotonActualizar() {
		MetodosExtra ME = new MetodosExtra(u, testCase);
		ME.scrollToElement(BtnActualizar);
		u.highLight(BtnActualizar);
		testCase.addScreenShotCurrentStep(u, "DatosOpAC");
		u.click(BtnActualizar);
//		u.waitForLoadPage();

		u.hardWait(10);
	
	}

	/**
	 * Metodo para validar que se haya presentado alert de guardado
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return  msjexist (true si se presento, false si no)
	 */
	public boolean validacion() {
		u.hardWait(10);
		String ventanaE = u.getDriver().switchTo().alert().getText();
		System.out.println("Texto en mensaje de alerta" + ventanaE);
		testCase.addScreenShotCurrentStep("alerta1");
		boolean msjexist = false;
		String str2 = "La informacion de \"DATOS DE OPERACION\" ha sido grabada.";
		if (ventanaE.equals(str2)) {
//			testCase.addScreenShotCurrentStep(u, "RespuestaPOP");
			msjexist = true;
			u.getDriver().switchTo().alert().accept();
		} else {
			System.out.println("Fallo Datos de Operacion Grabados");
			msjexist = false;
		}
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("alerta2");
		return msjexist;
	}
	public void ScreenVentanaEmergente() {
		String winHandleBefore = u.getDriver().getWindowHandle();

		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

//		u.waitForLoadPage();
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
		u.highLight(TiendaE);
		// Perform the actions on new window
		testCase.addScreenShotCurrentStep(u, "VentanaTienda");
		// Close the new window, if that window no more required
//		u.hardWait(6);
		u.getDriver().close();
//
//		// Switch back to original browser (first window)
		u.getDriver().switchTo().window(winHandleBefore);

	}

	
	
	
	
	/**obtener el texto seleccionado de banco que recibe dolares sin screenshots
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return 
	 * @return N/A
	 */
	public String TextBancoDolares() {
		String bank="";
		String winHandleBefore = u.getDriver().getWindowHandle();

		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

//		u.waitForLoadPage();
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
//		u.highLight(TiendaE);

		u.hardWait(10);
		u.getDriver().manage().window().maximize();
		u.highLight(framed);
		u.getDriver().switchTo().frame("framedatos");
		u.highLight(cot);
		MetodosExtra ME = new MetodosExtra(u, testCase);
		ME.scrollToElement(SelectBankDolar);
		Select DistritoS = new Select(u.getDriver().findElement(SelectBankDolar));
	String	options=	 DistritoS.getFirstSelectedOption().getText();
//		List <WebElement> op = DistritoS.getOptions();
//	      int size = op.size();
//	      for(int i =0; i<size ; i++){
//	         String options = op.get(i).getText();
//	         System.out.println(options);
//	        
//	        
//	      
//	      if (options.contains("*")==false &&  options.isEmpty()==false && options.trim().isEmpty()==false) {
////	     string.isEmpty() || string.trim().isEmpty()
//	    	//  && !options.contains("some")==true
//	 	        	 System.out.println("entro if opciones select");
//	 	        	 System.out.println(options);
//	 	        	 System.out.println(options);
//	 	        	 i=size;
//	 	        	 bank=options;
//		} else {
//			 System.out.println("no entro pciones select ");
//		}
//	      
//	      
//	      
//	      }
	   
		 bank=options;
//		testCase.addScreenShotCurrentStep(u, "cot");
//		u.getDriver().switchTo().window(winHandleBefore);
		u.waitForLoadPage();
		
		if (bank!=null) {
			return bank;
		}else {
			return bank="No esta seleccionado el banco de Dolares";
}
	}

	
	/**obtener el texto seleccionado de banco pesos
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return 
	 * @return N/A
	 */
	public String TextBancoPesos() {
		String bank="";
		String winHandleBefore = u.getDriver().getWindowHandle();

		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

//		u.waitForLoadPage();
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
//		u.highLight(TiendaE);

		u.hardWait(10);
		u.getDriver().manage().window().maximize();
		u.highLight(framed);
		u.getDriver().switchTo().frame("framedatos");
		u.highLight(cot);
		MetodosExtra ME = new MetodosExtra(u, testCase);
		ME.scrollToElement(BANCO);
		Select DistritoS = new Select(u.getDriver().findElement(BANCO));
	String	options=	 DistritoS.getFirstSelectedOption().getText();
//		List <WebElement> op = DistritoS.getOptions();
//	      int size = op.size();
//	      for(int i =0; i<size ; i++){
//	         String options = op.get(i).getText();
//	         System.out.println(options);
//	        
//	        
//	      
//	      if (options.contains("*")==false &&  options.isEmpty()==false && options.trim().isEmpty()==false) {
////	     string.isEmpty() || string.trim().isEmpty()
//	    	//  && !options.contains("some")==true
//	 	        	 System.out.println("entro if opciones select");
//	 	        	 System.out.println(options);
//	 	        	 System.out.println(options);
//	 	        	 i=size;
//	 	        	 bank=options;
//		} else {
//			 System.out.println("no entro pciones select ");
//		}
//	      
//	      
//	      
//	      }
	   
		 bank=options;
		 
		 String string = bank;
		 String[] parts = string.split("-");
		 String part1 = parts[0]; // 123
	//	 String part2 = parts[1]; // 654321
//		testCase.addScreenShotCurrentStep(u, "cot");
//		u.getDriver().switchTo().window(winHandleBefore);
		u.waitForLoadPage();
		System.out.print(part1+" parte 1");
		
		bank=part1;
		if (bank!=null) {
			return bank;
		}else {
			return bank="No esta seleccionado el banco";
}
	}

	/**obtener el texto seleccionado de banco que recibe dolares CON screenshots
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return 
	 * @return N/A
	 */
	public String TextBancoDolaresSC() {
		String bank="";
		String winHandleBefore = u.getDriver().getWindowHandle();

		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

//		u.waitForLoadPage();
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
//		u.highLight(TiendaE);

		u.hardWait(10);
		u.getDriver().manage().window().maximize();
		u.highLight(framed);
		u.getDriver().switchTo().frame("framedatos");
		u.highLight(cot);
		MetodosExtra ME = new MetodosExtra(u, testCase);
		ME.scrollToElement(SelectBankDolar);
		Select DistritoS = new Select(u.getDriver().findElement(SelectBankDolar));
	String	options=	 DistritoS.getFirstSelectedOption().getText();
//		List <WebElement> op = DistritoS.getOptions();
//	      int size = op.size();
//	      for(int i =0; i<size ; i++){
//	         String options = op.get(i).getText();
//	         System.out.println(options);
//	        
//	        
//	      
//	      if (options.contains("*")==false &&  options.isEmpty()==false && options.trim().isEmpty()==false) {
////	     string.isEmpty() || string.trim().isEmpty()
//	    	//  && !options.contains("some")==true
//	 	        	 System.out.println("entro if opciones select");
//	 	        	 System.out.println(options);
//	 	        	 System.out.println(options);
//	 	        	 i=size;
//	 	        	 bank=options;
//		} else {
//			 System.out.println("no entro pciones select ");
//		}
//	      
//	      
//	      
//	      }
	   
		 bank=options;
		testCase.addScreenShotCurrentStep(u, "cot");
	
//		u.getDriver().switchTo().window(winHandleBefore);
		u.waitForLoadPage();
		
		if (bank!=null) {
			testCase.addTextEvidenceCurrentStep(" Banco USD y Cuenta USD se encuentren seleccionados y/o capturados.");
			return bank;
			
		}else {
			return bank="No esta seleccionado el banco de Dolares";
}
	}
	
	

	/**
	 * Constructor
	 ******************************************************************************************/

	public Datos_de_Operacion(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}

	/*
	 * METODOS - Acceso correcto a Inventario de espacios Publicitarios V2
	 ******************************************************************************************/

	

}