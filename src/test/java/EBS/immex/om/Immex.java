package EBS.immex.om;

import org.openqa.selenium.By;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class Immex {

	SeleniumUtil u;
	TestCase testCase;
	
	/*
	 * Localizadores Login
	 ******************************************************************************************/
	By userName = By.id("usernameField");
	By password = By.id("passwordField");
	By loginBtn = By.id("SubmitButton");
	
	/*
	 * Localizadores Home Page
	 ******************************************************************************************/
	
	By responsabilidadCompras = By.xpath("//a[contains(text(), 'MXIMMX-PO14MON-Compras')]");
	By ordenesCompra = By.xpath("//a[contains(text(), '�rdenes de Compra')]");
	By receipts = By.xpath("//a[contains(text(), 'Receipts')]");
	By returns = By.xpath("//a[contains(text(), 'Returns')]");
	
	/**
	 * Constructor
	 ******************************************************************************************/
	
	public Immex(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}
	
	/*
	 * METODOS 
	 ******************************************************************************************/
	
	/*
	 * Metodos login
	 ******************************************************************************************/
	
	/**
	 * Metodo para iniciar sesion a IMMEX
	 * @param user
	 * @param pass
	 * @return Retorna true si se ingreso correctamente o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean loginImmex(String user, String pass) {
		Boolean success = false;
		
		try {
			u.waitForLoadPage();
			try {
				u.clear(userName);
			}catch(Exception e) {
				
			}
			u.hardWait(3);
			u.highLight(userName);
			u.sendKeys(userName, user);
			u.highLight(password);
			u.sendKeys(password, pass);
			u.highLight(loginBtn);
			testCase.addScreenShotCurrentStep(u, "loginImmex");
			u.click(loginBtn);
			testCase.addScreenShotCurrentStep(u, "clickloginImmex");
			success = true;
		}catch(Exception e) {
			testCase.addScreenShotCurrentStep(u, "loginImmexError");
		}
		return success;
	}
	
	/*
	 * Metodos Home
	 ******************************************************************************************/
	
	/**
	 * Metodo para seleccionar la responsabilidad Compras y la opcion Receipts
	 * @return Retorna true si se seleccionaron correctamente las opciones
	 * @author Maria Due�as
	 */
	public Boolean selectResponsabilidadReceipts() {
		Boolean success = false;
		
		try {
			u.highLight(responsabilidadCompras);
			u.click(responsabilidadCompras);
			u.waitForLoadPage();
			u.highLight(receipts);
			testCase.addScreenShotCurrentStep(u, "selectResponsabilidadReceipts");
			u.click(receipts);
			success = true;
		}catch(Exception e) {
			testCase.addScreenShotCurrentStep("selectResponsabilidadReceiptsError");
		}
		
		return success;
	}
	
	/**
	 * Metodo para seleccionar la responsabilidad Compras y la opcion Returns
	 * @return Retorna true si se seleccionaron correctamente las opciones
	 * @author Maria Due�as
	 */
	public Boolean selectResponsabilidadReturns() {
		Boolean success = false;
		
		try {
			u.highLight(responsabilidadCompras);
			u.click(responsabilidadCompras);
			u.waitForLoadPage();
			u.highLight(returns);
			testCase.addScreenShotCurrentStep(u, "selectResponsabilidadReturns");
			u.click(returns);
			success = true;
		}catch(Exception e) {
			testCase.addScreenShotCurrentStep("selectResponsabilidadReturnsError");
		}
		
		return success;
	}
	
	/*
	 * Metodos Find Expected Receipt
	 ******************************************************************************************/
	
	/**
	 * Metodo para seleccionar proveedor
	 * @return Retorna true si se selecciono correctamente
	 * @throws FindFailed
	 * @author Maria Due�as
	 */
	public Boolean selectSupplier() throws FindFailed {
		Boolean success = false;
		
		success = clickElementSikuli("IMMEX", "source");
		if (success) {
			u.hardWait(5);
			success = clickElementSikuli("IMMEX", "Supplier");
		}
		
		return success;
	}
	
	/**
	 * Metodo para ingresar el numero de orden de compra
	 * @param orden
	 * @return Retorna true si se ingreso el texto correctamente
	 * @throws FindFailed
	 * @author Maria Due�as
	 */
	public Boolean typingPO(String orden, int tabs) throws FindFailed {
		Boolean success = false;
		
		try{
			typeKeyTab(tabs);
			SikuliUtil sikuli = new SikuliUtil(); 
			sikuli.screen.type(orden);
			testCase.addScreenShotCurrentStep("typingPO");
			success = true;
		}catch(Exception e) {
			testCase.addScreenShotCurrentStep("ErrortypingPO");
		}
		
		return success;
	}
	
	/**
	 * Metodo para dar click en Find
	 * @return Retorna true si se selecciono correctamente
	 * @throws FindFailed
	 * @author Maria Due�as
	 */
	public Boolean selectFind() throws FindFailed {
		Boolean success = false;
		
		success = clickElementSikuli("IMMEX", "Find");
		
		return success;
	}
	
	/**
	 * Metodo para encontrar recepciones esperadas
	 * @param orden
	 * @return Retorna true si se realizo correctamente la busqueda
	 * @throws FindFailed 
	 * @author Maria Due�as
	 */
	public Boolean findExpectedReceipts(String orden) throws FindFailed {
		Boolean success = false;
		
		u.hardWait(60);
		typeKeyEnter();
		u.hardWait(15);
		Boolean supplier = selectSupplier();
		Boolean order = typingPO(orden, 1);
		Boolean find = selectFind();
		
		if (supplier && order && find) {
			success = true;
		}
		
		return success;
	}
	
	/**
	 * Metodos ventana Receipts Header
	 *****************************************************************************/
	
	/**
	 * Metodo para ingresar los comentarios
	 * @param comentario
	 * @return Retorna true si se ingreso el comentario correctamente
	 * @throws FindFailed
	 * @author Maria Due�as
	 */
	public Boolean typingComments(String comentario) throws FindFailed {
		Boolean success = false;
		
		try{
			typeKeyTab(11);
			SikuliUtil sikuli = new SikuliUtil(); 
			sikuli.screen.type(comentario);
			testCase.addScreenShotCurrentStep("typingComments");
			success = true;
		}catch(Exception e) {
			testCase.addScreenShotCurrentStep("typingCommentsError");
		}
		
		return success;
	}
	
	/**
	 * Metodo para ingresar comentarios y factura en la ventana receipts header
	 * @param comentarios
	 * @param oc
	 * @return Retorna true si se ingresaron los datos correctamente
	 * @throws FindFailed
	 * @author Mar�a Due�as
	 */
	public Boolean receiptHeader(String comentarios, String oc) throws FindFailed {
		Boolean success = false;
		
		u.hardWait(5);
		
		Boolean c = typingComments(comentarios);
		typeKeyTab(1);
		u.hardWait(3);
		typeKeyEnter();
		u.hardWait(5);
		Boolean fac = typeTexto(oc); 
		typeKeyTab(1);
		typeKeyEnter();
		
		if (c && fac) {
			success = true;
		}
		
		return success;
	}
	
	/**
	 * Metodo para validar que se muestre la ventana Receipt Header
	 * @param Folder
	 * @param image
	 * @return Retorna true si se muestra correctamente
	 * @throws FindFailed
	 * @author Maria Due�as
	 */
	public Boolean showWindowReceiptHeader(String Folder, String image) throws FindFailed {
		
		   Boolean exist = false;
		   
		   u.hardWait(5);
	       SikuliUtil sikuli = new SikuliUtil(); 
	       String s1 = sikuli.PatternPath(Folder); 
	       Pattern p1 = sikuli.PatternImage("EBS"+s1 + image + ".png"); 
	       
	       if (sikuli.screen.exists(p1) != null) {
	    	   exist = true;
	    	   sikuli.screen.wait(p1, 10);
	           sikuli.screen.click(p1);
	       }else {
	    	   System.out.print("El elemento no existe\n");
	       }
	       
	       testCase.addScreenShotCurrentStep("showWindowReceiptHeader");
	       return exist;
	 }
	
	/**
	 * Metodos ventana Receipts 
	 *****************************************************************************/
	
	/**
	 * Metodo para hacer TAB
	 * @param num
	 * @author Maria Due�as
	 */
	public void typeKeyTab(int num) {
		
		 SikuliUtil sikuli = new SikuliUtil(); 
		 
		 for(int i = 0; i < num; i++) {
			 sikuli.screen.type(Key.TAB);
		 } 
	}
	
	/**
	 * Metodo para indicar la linea a recibir
	 * @param subinventory
	 * @return Retorna true en caso de no haber errores
	 * @throws FindFailed
	 * @author Maria Due�as
	 */
	public Boolean receipts(String subinventory) throws FindFailed {
		Boolean success = false;
		Boolean fac = false;
		
		u.hardWait(7);
		Boolean c = clickElementSikuli("IMMEX", "Casilla");
		u.hardWait(5);
		typeKeyEnter();
		u.hardWait(5);
		typeKeyTab(9);
		fac = typeTexto(subinventory);
		
		if (c && fac) {
			success = true;
		}
		
		return success;
	}
	
	/**
	 * Metodo para ingresar el numero de factura
	 * @param oc
	 * @return Retorna true si se ingreso correctamente
	 * @throws FindFailed
	 * @author Maria Due�as
	 */
	public Boolean typeReceivingTransaction(String oc) throws FindFailed {
		Boolean success = false;
		
		u.hardWait(5);
		Boolean c = clickTypingSikuli("IMMEX", "Casilla2", Key.BACKSPACE);
		u.hardWait(10);
		Boolean fac = typeTexto(oc);
		typeKeyTab(1);
		typeKeyEnter();
		
		if ( c && fac) {
			success = true;
		}
		
		return success;
	}
	
	/**
	 * Metodos ventana general
	 ************************************************************************/
	
	/**
	 * Metodo para guardar la recepcion y el boton Header
	 * @return Retorna true si selecciona los botones correctamente
	 * @throws FindFailed
	 * @author Maria Due�as
	 */
	public Boolean clickGuardarCabecera() throws FindFailed {
		Boolean success = false;
		
		u.hardWait(5);
		Boolean save = clickElementSikuli("IMMEX", "Save");
		u.hardWait(30);
		Boolean cabecera = clickElementSikuli("IMMEX", "Header");
		
		if (save && cabecera) {
			success = true;
		}
		
		return success;
		
	}
	
	
	/*
	 * Metodos para devoluciones
	 *******************************************************************************/
	
	/**
	 * Metodo para encontrar recepciones esperadas
	 * @param orden
	 * @return Retorna true si se realizo correctamente la busqueda
	 * @throws FindFailed 
	 * @author Maria Due�as
	 */
	public Boolean findExpectedReturns(String orden) throws FindFailed {
		Boolean success = false;
		
		u.hardWait(60);
		typeKeyEnter();
		u.hardWait(15);
		Boolean order = typingPO(orden, 2);
		Boolean find = selectFind();
		
		if (order && find) {
			success = true;
		}
		
		return success;
	}
	
	/**
	 * Metodo para ingresar la linea a devolver
	 * @return Retorna true si se selecciono correctamente la linea y la cantidad
	 * @throws FindFailed
	 * @author Mria Due�as 
	 */
	public Boolean receivingReturns() throws FindFailed {
		Boolean success = false;
		
		u.hardWait(10);
		Boolean c = clickElementSikuli("IMMEX", "Casilla");
		u.hardWait(5);
		Boolean cant = typeCantidad();
		
		if(c && cant) success = true;
		
		return success;
	}
	
	/**
	 * Metodo para ingresar la cantidad
	 * @author Maria Due�as
	 */
	public Boolean typeCantidad() {
		Boolean success = false;
		
		try {
			typeKeyTab(1);
			SikuliUtil sikuli = new SikuliUtil(); 
			sikuli.screen.type("1");
			testCase.addTextEvidenceCurrentStep("Se ingreso la cantidad");
			testCase.addScreenShotCurrentStep("typeCantidad");
			success = true;
		}catch (Exception e) {
			testCase.addScreenShotCurrentStep("Error");
		}
		return success;
	}
	
	/**
	 * Metodo para dar click en el boton guardar
	 * @return Retorna true si se dio click correctamente en el boton
	 * @throws FindFailed
	 * @author Maria Due�as
	 */
	public Boolean clickGuardar() throws FindFailed {
		Boolean success = false;
		
		u.hardWait(5);
		success = clickElementSikuli("IMMEX", "Save");
		u.hardWait(10);
		
		return success;
	}
	
	/*
	 * Metodos Extra
	 ******************************************************************************************/
	
	/**
	 * Metodo para dar click sobre elemento utilizando sikuli
	 * @param Folder: nombre de la carpeta dentro de Patterns
	 * @param image: nombre de la imagen
	 * @return true o false si encontro el elemento
	 * @author Maria Due�as
	 */
	public Boolean clickElementSikuli(String Folder, String image) throws FindFailed {
		
		   Boolean exist = false;
		   Screen s = new Screen();
		   
	       SikuliUtil sikuli = new SikuliUtil(); 
	       String s1 = sikuli.PatternPath(Folder); 
	       Pattern p1 = sikuli.PatternImage("EBS"+s1 + image + ".png"); 
	       
	       if (sikuli.screen.exists(p1) != null) {
	    	   exist = true;
	    	   sikuli.screen.wait(p1, 10);
	           sikuli.screen.click(p1);
	       }else {
	    	   System.out.print("El elemento no existe\n");
	    	   //sikuli.screen.wait(p1.similar((float).60),15).click();
	       }
	       
	       testCase.addScreenShotCurrentStep("clickElementSikuli");
	       return exist;
	 }
	
	/**
	 * Metodo para dar ingresar texto utilizando sikuli
	 * @param Folder: nombre de la carpeta dentro de Patterns
	 * @param image: nombre de la imagen
	 * @param text: texto a capturar
	 * @return true o false si encontro el elemento
	 * @author Maria Due�as
	 */
	public Boolean clickTypingSikuli(String Folder, String image, String text) throws FindFailed {
		
		   Boolean exist = false;
		   
	       SikuliUtil sikuli = new SikuliUtil(); 
	       String s1 = sikuli.PatternPath(Folder); 
	       Pattern p1 = sikuli.PatternImage("EBS"+s1 + image + ".png"); 
	       
	       if (sikuli.screen.exists(p1) != null) {
	    	   exist = true;
	    	   sikuli.screen.wait(p1, 10);
	           sikuli.screen.type(p1, text);
	       }else {
	    	   System.out.print("El elemento no existe\n");
	    	   //sikuli.screen.wait(p1.similar((float).60),15).click();
	       }
	       
	       testCase.addScreenShotCurrentStep("clickElementSikuli");
	       return exist;
	 }
	
	/**
	 * Metodo para hacer Enter
	 * @author Maria Due�as
	 */
	public void typeKeyEnter() {
		
		 SikuliUtil sikuli = new SikuliUtil(); 
		 sikuli.screen.type(Key.ENTER);
		
	}
	
	/**
	 * Metodo para ingresar texto en textField
	 * @param texto
	 * @author Maria Due�as
	 */
	public Boolean typeTexto(String texto) {
		Boolean success = false;
		
		try {
			SikuliUtil sikuli = new SikuliUtil(); 
			sikuli.screen.type(texto);
			testCase.addScreenShotCurrentStep("typeTexto");
			success = true;
		}catch (Exception e) {
			testCase.addScreenShotCurrentStep("typeTextoError");
		}
		return success;
	}
	
}






