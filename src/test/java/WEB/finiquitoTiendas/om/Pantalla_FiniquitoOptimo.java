package WEB.finiquitoTiendas.om;

import org.openqa.selenium.By;
import WEB.om.MetodosExtra;
import WEB.sarv3.om.ReportesSARv3;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Pantalla_FiniquitoOptimo {
		
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	ReportesSARv3 r;
	
	String url = "https://10.182.92.88:8443/AccessControl/pages/login_form.jsf";
	
	/*
	 * Localizadores conexion no privada
	 ******************************************************************************************/
	By xmlMensaje = By.xpath("//*[@id=\"main-message\"]/h1");
	By xmlAdvanced = By.xpath("//*[@id=\"details-button\"]");
	By xmlProceed = By.xpath("//*[@id=\"proceed-link\"]");
	
	/*
	 * Localizadores Inicio de sesion Inventarios Espacios
	 ******************************************************************************************/
	
	By xmlUser = By.xpath("//*[@id=\"login:userName\"]");
	By xmlPassword = By.xpath("//*[@id=\"login:userPass\"]");
	By xmlLogginButton = By.xpath("//*[@id=\"login:loginButton\"]");
	By xmlUserPassInv = By.className("mensaje_error");
	By xmlNameUser = By.xpath("//*[@id=\"header\"]/div[2]/div[2]");
	
	/*
	 * Localizadores Menu Principal Portal Aplicaciones
	 ******************************************************************************************/
	
	By xmlFiniquitotiendas = By.xpath("//*[@id=\"homeSubview:j_idt61\"]/div/div[2]/div[1]/div[1]/div[2]/div/ul/li[7]/a");
	
	/*
	 * Localizadores Administracion de finiquitos paso 2
	 ******************************************************************************************/
	
	By xmlFiniquito = By.xpath("//*[@id=\"e0_1i\"]");
	By xmlFiniquitoOptimo= By.xpath("//*[@id=\"e0_5i\"]");
	
	/*
	/*
	 * Localizadores Finiquito Optimo  TEST4
	 ******************************************************************************************/
	By xmlBtnFiniquitoOptimo = By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table/tbody/tr[4]/td/input");
	By xmlBtnInstruccionesFiniquitoOptimo = By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table/tbody/tr[2]/td[1]/input");
	By xmlPlazaFOptimo = By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table[1]/tbody/tr[1]/td[3]/select");
	By xmlDistritoFOptimo = By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table[1]/tbody/tr[3]/td[3]/select");
	By xmlComisionistaFOptimo = By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table[1]/tbody/tr[5]/td[3]/select");
	By xmlTiendaFOptimo = By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table[1]/tbody/tr[7]/td[3]/select");
	By xmlBtnSiguienteFO = By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table[2]/tbody/tr/td[2]/input[2]");
	By xmlBtnSiguienteDeducir = By.xpath("//*[@id=\"next\"]");
	By xmlBtnSiguienteGAD = By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table[5]/tbody/tr/td/input[2]");
	By xmlTxtAyudante = By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[2]/input");
	
	/**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase Administracio_Finiquitos
	 * @author Yair Lizarraga
	 * @param u (SeleniumUtil): objeto de la clase 
	 * @param testCase (testCase): objeto de la clase
	 */
	public Pantalla_FiniquitoOptimo(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
		this.r = new ReportesSARv3(u, testCase);
		}
	
	/**
	 * Metodo para dar click en la opcion Finiquito en tienda paso 1
	 * @author Yair Lizarraga
	 */
 	public void clickContainer() {
 		u.highLight(xmlFiniquitotiendas);
 		u.click(xmlFiniquitotiendas);
 		testCase.addScreenShotCurrentStep(u, "clickContainer");
	 	}
	/**
	 *  Metodo para posicionar el mouse sobre la barra que despliega el menu y clic Finiquito Optimo
	 * @author Yair Lizarraga
	 */
	public void MostrarFiniquitoOptimo() {
		   	u.highLight(xmlFiniquito);
			u.mouseOver(xmlFiniquito);
		   	u.highLight(xmlFiniquitoOptimo);
		   	u.mouseOver(xmlFiniquitoOptimo);
			testCase.addScreenShotCurrentStep(u, "xmlFiniquitoOptimo");
			u.click(xmlFiniquitoOptimo);
			u.hardWait(3);
			testCase.addScreenShotCurrentStep(u, "xmlFiniquitoOptimo2");
	}
	
	/**
	 *  Metodo para dar clic en la pantalla de Finiquito Optimo
	 * @author Yair Lizarraga
	 */
	public void clicBtnFiniquitoOptimo() {
		   	u.highLight(xmlBtnFiniquitoOptimo);
			u.mouseOver(xmlBtnFiniquitoOptimo);
			testCase.addScreenShotCurrentStep(u, "xmlBtnFiniquitoOptimo");
			u.click(xmlBtnFiniquitoOptimo);
				
		}
	
	/**
	 *  Metodo para dar clic en la pantalla de Instrucciones Finiquito Optimo
	 * @author Yair Lizarraga
	 */
	public void clicBtnInstruccionesFiniquitoOptimo() {
		   	u.highLight(xmlBtnInstruccionesFiniquitoOptimo);
			u.mouseOver(xmlBtnInstruccionesFiniquitoOptimo);
			testCase.addScreenShotCurrentStep(u, "xmlBtnInstruccionesFiniquitoOptimo");
			u.click(xmlBtnInstruccionesFiniquitoOptimo);
				
		}
	/**
	 * Metodo: Para capturar los campos del paso en el menu Simulador de finiquitos
	 * 
	 * @author: Yair Lizarraga
	 * @param:  Plaza, Distrito,Comisionista, Tienda son necesarios capturar desde su data provider
	 * @return: Ninguno
	 */
	public Boolean capturaCamposFiniquitoOptimo(String Plaza, String Distrito, String Comisionista,String Tienda ) {
		
		Boolean correct = false;
		
		try {
			
			Boolean po = r.selectOption(Plaza, xmlPlazaFOptimo);
			Boolean dfo = r.selectOption(Distrito, xmlDistritoFOptimo);
			Boolean co = r.selectOption(Comisionista, xmlComisionistaFOptimo);
			Boolean to = r.selectOption(Tienda, xmlTiendaFOptimo);
			testCase.addScreenShotCurrentStep(u, "Resultados Comisionistas");
			if(po && dfo && co && to) correct = true;
			
		}catch(Exception e) {
			testCase.addScreenShotCurrentStep(u, "No hay comisionistas");
		}
		
		return correct;
		
}
	/**
	 *  Metodo para dar clic en el boton siguiente al ingresar datos en finiquito optimo
	 * @author Yair Lizarraga
	 */
	public void clicBtnSiguienteFO() {
		u.highLight(xmlPlazaFOptimo);
		u.highLight(xmlDistritoFOptimo);
		u.highLight(xmlComisionistaFOptimo);
		u.highLight(xmlTiendaFOptimo);
		   	u.highLight(xmlBtnSiguienteFO);
			u.mouseOver(xmlBtnSiguienteFO);
			testCase.addScreenShotCurrentStep(u, "xmlBtnSiguienteFO");
			u.click(xmlBtnSiguienteFO);
				
		}
	
	/**
	 *  Metodo para cambiar un campo en los gastos a deducir
	 * @author Yair Lizarraga
	 */
	public Boolean campoAyudanteFiniquito(String Ayudante) {
		Boolean exist = false;
		
		try {
			u.click(xmlTxtAyudante);
			u.clear(xmlTxtAyudante);
			u.sendKeys(xmlTxtAyudante, Ayudante);
			u.highLight(xmlTxtAyudante);

		}catch(Exception e) {
			exist = false;
		}
		return exist;
	}
	public void clicBtnSiguienteDeducir() {

		   	u.highLight(xmlBtnSiguienteDeducir);
			u.mouseOver(xmlBtnSiguienteDeducir);
			testCase.addScreenShotCurrentStep(u, "xmlBtnSiguienteDeducir");
			u.click(xmlBtnSiguienteDeducir);
				
		}
	/**
	 *  Metodo para dar clic en siguiente en el menu de gastos a deducir
	 * @author Yair Lizarraga
	 */
	public void clicBtnSiguienteGAD() {
		   	u.highLight(xmlBtnSiguienteGAD);
			u.mouseOver(xmlBtnSiguienteGAD);
			testCase.addScreenShotCurrentStep(u, "clicBtnSiguienteGAD");
			u.click(xmlBtnSiguienteGAD);
			u.hardWait(5);
			testCase.addScreenShotCurrentStep(u, "comparativo");
		}

		
}
	
	
