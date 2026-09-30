package WEB.util.PortalEquipamiento;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class PomPortalEquipamientoBandejaAprobador {
	
	SeleniumUtil u;
	private WebDriverWait wait;
	TestCase testCase;
	PomPortalEquipamiento PPE;
	
	/*
	 * Localizadores Menu
	 ******************************************************************************************/
	private By menuPortalEquipamiento = By.xpath("//img[@title='Navigation']");
	private By tablaIdLE = By.xpath("//th[normalize-space()='IdLE']");
	private By modalRechCan = By.xpath("//div[@class='modal-dialog modal-lg']//div[@class='modal-content']");
	private By modalTextRech = By.xpath("//div[@id='modalRechazo']//textarea[@id='Textarea0']");
	private By modalGuardarRech = By.xpath("//div[@id='modalRechazo']//button[@id='aceptar']");
	private By modalTextCanc = By.xpath("//div[@id='modalCancelo']//textarea[@id='Textarea0']");
	private By modalGuardarCanc = By.xpath("//div[@id='modalCancelo']//button[@id='aceptar']");
	
	private By modalAceptar = By.xpath("//button[@class='swal2-confirm swal2-styled swal2-default-outline']");
										
	
	
	//Modal de carga
	private By modalCargaArchivo = By.xpath("//div[@role='document']//div[@class='modal-content']");
	private By modalbttnCargaArchivo = By.xpath("//input[@id='input-b5']");
	private By modalprogressBar = By.xpath("//div[@id='progreso']//div[contains(text(),'100')]");
	private By modalbttnAceptar = By.xpath("//div[@role='document']//button[@id='aceptar']");
	
	private By modalExitoso = By.xpath("//div[@class='swal2-popup swal2-modal swal2-icon-success swal2-show']");
	private By modalBttnExitoso = By.xpath("//button[@class='swal2-confirm swal2-styled swal2-default-outline']");
	/*
	 * Constructor
	 ******************************************************************************************/
	public PomPortalEquipamientoBandejaAprobador(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		this.testCase = testCase;
		this.PPE = new PomPortalEquipamiento(u,testCase); //<-- para utilizar los metodos del POM PomPortalEquipamiento
	
	}
	
	/*
	 * METODOS 
	 ******************************************************************************************/
	/**
	 * para dar clic en la opcion del MENU, ya que con el metodo
	 * "menuNiv" nos movemos en las diferentes opciones, las cuales se capturan en el dataprovider
	 * @param N1, N2
	 * @date 14/05/2025
	 * @author Elberth Ruiz
	 */
	public void menuApp(String N1, String N2) {
		u.highLight(menuPortalEquipamiento);
		testCase.addScreenShotCurrentStep("");
		u.click(menuPortalEquipamiento);
		menuNiv(N1,N2);
		
	}
	
	/**
	 * nos movemos en las diferentes opciones, las cuales se capturan en el dataprovider
	 * @param N1, N2
	 * @date 14/05/2025
	 * @author Elberth Ruiz
	 */
	public void menuNiv(String N1, String N2) {
		By menuN1 = By.xpath("//nav/ul/li//span[contains(text(),'"+ N1 +"')]");
		By menuN2 = By.xpath("//li/a[contains(text(),'"+ N2 +"')]");
		
		u.waitWebElement(menuN1, 5, 800);
		
		u.highLight(menuN1);
		testCase.addScreenShotCurrentStep("");
		u.click(menuN1);
		
		u.scrollToElement(menuN2);
		testCase.addScreenShotCurrentStep("");
		
		u.waitWebElement(menuN2, 5, 800);
		u.click(menuN2);
		
		PPE.cargandoMsg();
		testCase.addScreenShotCurrentStep("");
		
	}
	
	/**
	 * Para mostrar en pantalla el registro a seleccionar
	 * @param Id_LE 
	 * @date 14/05/2025
	 * @author Elberth Ruiz
	 */
	public void BandejaAprob(String Id_LE) {
	    
	    PPE.cargandoMsg();
	  
	    By accion1 = By.xpath("//table/tbody/tr/td[contains(text(),'"+Id_LE+"')]");
		u.hardWait(1);
		testCase.addScreenShotCurrentStep("");
		try {
			if(u.getDriver().findElements(accion1).size() != 0) {
				u.scrollToElement(accion1);
				u.highLight(accion1);
				testCase.addScreenShotCurrentStep("");
				u.unhighLight(accion1); 
			}
		}catch(Exception e) {
			System.out.println("-> " + e);
			System.out.println("No se encontro registro");
		}
	}
	
	/**
	 * Realiza la accion entre los registros que se encuentren en la secuencia 1, 3 y 4,
	 * ya que con el metodo "cargaArchivos" se realizara la accion de subir un archivo
	 * @param Id_LE 
	 * @date 14/05/2025
	 * @author Elberth Ruiz
	 */
	public void accionSecuencia1_3(String Id_LE, String Carga1) {
		//Comparar el registro de secuencia y ver en que parte de las condicionales va a entrar
		By Id_secuencia = By.xpath("//table/tbody/tr/td[contains(text(),'"+Id_LE+"')]/following-sibling::td[2]");
		By bttnAdjDoc1 =  By.xpath("//table/tbody/tr/td[contains(text(),'"+Id_LE+"')]/following-sibling::td[14]/button[1]");
		
		String variable = u.getText(Id_secuencia);
		System.out.println(variable);
		
		if(variable != null ){
			switch(variable) {
			case "1":
				u.scrollToElement(bttnAdjDoc1);
				u.highLight(bttnAdjDoc1);
				u.click(bttnAdjDoc1);
				testCase.addScreenShotCurrentStep("");
				//metodo para hacer la carga de los archivos
				cargaArchivos(Carga1);
				u.unhighLight(bttnAdjDoc1);
				
				break;

			case "3":
				u.scrollToElement(bttnAdjDoc1);
				u.highLight(bttnAdjDoc1);
				testCase.addScreenShotCurrentStep("");
				u.click(bttnAdjDoc1);
				//metodo para hacer la carga de los archivos
				cargaArchivos(Carga1);
				u.unhighLight(bttnAdjDoc1);
				break;
			case "4":
				
				break;
			default:
 		        testCase.addScreenShotCurrentStep("verificarInformacion");
 		        break;
			}
			
			
		}
	}
	
	/**
	 * Realiza la accion entre los registros que se encuentren en la secuencia 2(se separo este metodo, ya que en est secuencia 
	 * se suben 2 archivos),
	 * con el metodo "cargaArchivos" se realizara la accion de subir un archivo,
	 * 
	 * @param Id_LE, Carga1, Carga2
	 * @date 14/05/2025
	 * @author Elberth Ruiz
	 */
	public void accionSecuencia2(String Id_LE, String Carga1, String Carga2) {
		//Comparar el registro de secuencia y ver en que parte de las condicionales va a entrar
		By Id_secuencia = By.xpath("//table/tbody/tr/td[contains(text(),'"+Id_LE+"')]/following-sibling::td[2]");
		By bttnAdjDoc1 =  By.xpath("//table/tbody/tr/td[contains(text(),'"+Id_LE+"')]/following-sibling::td[14]/button[1]");
		By bttnAdjDoc2 =  By.xpath("//table/tbody/tr/td[contains(text(),'"+Id_LE+"')]/following-sibling::td[14]/button[2]");
		
		String variable = u.getText(Id_secuencia);
		System.out.println(variable);
		
		if(variable != null ){
			switch(variable) {
			
			case "2":
				u.scrollToElement(bttnAdjDoc1);
				u.highLight(bttnAdjDoc1);
				testCase.addScreenShotCurrentStep("");
				u.click(bttnAdjDoc1);
				//metodo para hacer la carga de los archivos
				cargaArchivos(Carga1);
				u.unhighLight(bttnAdjDoc1);
				
				testCase.addScreenShotCurrentStep("");
				u.highLight(bttnAdjDoc2);
				//metodo para hacer la carga de los archivos
				u.click(bttnAdjDoc2);
				cargaArchivos(Carga2);
				u.unhighLight(bttnAdjDoc2);
				break;
			
			default:
 		        testCase.addScreenShotCurrentStep("verificarInformacion");
 		        break;
			}
			
			
		}
	}
	
	/**
	 * Realiza la accion de "Aprobar", "Rechazar" o "Cancelar"
	 * se a�ade el metodo modalRechCan a los casos de RECHAZAR y CANCELAR, ya que tiene el mismo comportamiento
	 * se a�ade el metodo modalMsjExitoso a las 3 acciones, ya que tiene el mismo comportamiento
	 * 
	 * @param Id_LE, accionButton, comentario
	 * @date 14/05/2025
	 * @author Elberth Ruiz
	 */
	public void BttnAprobRechCan(String Id_LE, String accionButton,String comentario) {
	    		
		By accion2 = By.xpath("//table/tbody/tr/td[contains(text(),'"+Id_LE+"')]/following-sibling::td[button[contains(text(),'"+accionButton+"')]]/button");
		By comentarios = By.xpath("//table/tbody/tr/td[contains(text(),'"+Id_LE+"')]/following-sibling::td[16]/input[@type='text']");
		
		String varbttn = u.getText(accion2);
				
		if(varbttn != null ){
			switch(varbttn) {
			case "Aprobar":
				u.click(comentarios);
				u.sendKeys(comentarios, comentario);
				u.scrollToElement(accion2);
				u.highLight(accion2);
				u.hardWait(1);
				testCase.addScreenShotCurrentStep("");
				u.click(accion2);
				break;
			case "Rechazar":
				u.scrollToElement(accion2);
				u.highLight(accion2);
				u.hardWait(1);
				testCase.addScreenShotCurrentStep("");
				u.click(accion2);
				u.waitWebElement(modalRechCan, 5, 800);
				u.highLight(modalTextRech);
				u.sendKeys(modalTextRech, comentario);
				u.highLight(modalGuardarRech);
				u.click(modalGuardarRech);
				testCase.addScreenShotCurrentStep("");			
				break;
			case "Cancelar":
				u.scrollToElement(accion2);
				u.highLight(accion2);
				u.hardWait(1);
				testCase.addScreenShotCurrentStep("");
				u.click(accion2);
				u.waitWebElement(modalRechCan, 5, 800);
				u.highLight(modalTextCanc);
				u.sendKeys(modalTextCanc, comentario);
				u.highLight(modalGuardarCanc);
				u.click(modalGuardarCanc);
				testCase.addScreenShotCurrentStep("");				
				break;
			
			default:
 		        testCase.addScreenShotCurrentStep("verificarInformacion");
 		        break;
			}
		}
		modalMsjExitoso();
		u.scrollToElement(tablaIdLE);
		u.hardWait(1);
		testCase.addScreenShotCurrentStep("");
		
    }
	

	
	/**
	 * Valida que se muestre el mensaje "Exitoso" al reailzar la accion "Aprobar", "Rechazar" o "Cancelar",
	 * ademas de que espera a que se actualice la pantalla
	 * @date 14/05/2025
	 * @author Elberth Ruiz
	 */
	public void modalMsjExitoso() {
		//Falta validar el mensaje "EXITOSO" y el refrescar de la pantalla donde no se muestre el registro, posiblemente va ser un METODO
		
		PPE.cargandoMsg();
		u.waitWebElement(modalExitoso, 5, 1000);
		wait.until(ExpectedConditions.elementToBeClickable(modalBttnExitoso));
		u.click(modalBttnExitoso);
		PPE.cargandoMsg();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep("");
	}
	
	/**
	 * Realiza la accion de cargar archivos para las secuencias 1, 2 y 3 
	 * @param subirArchivo
	 * @date 14/05/2025
	 * @author Elberth Ruiz
	 */
	public void cargaArchivos(String subirArchivo) {
		Boolean bandera = false;
		
		u.waitWebElement(modalCargaArchivo, 10, 5);
		u.highLight(modalbttnCargaArchivo);
		u.sendKeys(modalbttnCargaArchivo, subirArchivo);
		
		do {
			try {
			wait.until(ExpectedConditions.elementToBeClickable(modalbttnAceptar));
			bandera = true;
			u.hardWait(1);
			}catch(Exception e) {
				bandera = false;
			}
		}while(!bandera);
			
		
		u.hardWait(1);
		testCase.addScreenShotCurrentStep("");
		u.click(modalbttnAceptar);
		u.hardWait(1);
	}
	
}
