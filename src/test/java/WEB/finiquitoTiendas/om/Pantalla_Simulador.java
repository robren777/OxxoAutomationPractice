package WEB.finiquitoTiendas.om;

import org.openqa.selenium.By;
import WEB.om.MetodosExtra;
import WEB.sarv3.om.ReportesSARv3;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Pantalla_Simulador {
		
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
	By xmlSimuladorFiniquito= By.xpath("//*[@id=\"e0_4i\"]");
	
	/*
	 * Localizadores Consultar Fideicomisos  TEST3
	 ******************************************************************************************/
	By xmlBtnSimulador = By.xpath("	/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table/tbody/tr[4]/td/input");
	By xmlBtnInstruccionesSimulador = By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table/tbody/tr[2]/td[1]/input");
	By xmlPlazaSimulador = By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table[1]/tbody/tr[1]/td[3]/select");
	By xmlDistritoSimulador = By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table[1]/tbody/tr[3]/td[3]/select");
	By xmlComisionistaSimulador = By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table[1]/tbody/tr[5]/td[3]/select");
	By xmlTiendaSimulador = By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table[1]/tbody/tr[7]/td[3]/select");
	
		/**
		* Constructor
		******************************************************************************************/
		/**
		 * Constructor de la clase MenuPrincipalIEP
		 * @author Yair Lizarraga
		 * @param u (SeleniumUtil): objeto de la clase 
		 * @param testCase (testCase): objeto de la clase
		 */

	public Pantalla_Simulador(SeleniumUtil u, TestCase testCase) {
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
	 *  Metodo para posicionar el mouse sobre la barra que despliega el menu y clic en Consultar Finiquitos paso 2
	 * @author Yair Lizarraga
	 */
	public void MostrarSimulador() {
		   	u.highLight(xmlFiniquito);
			u.mouseOver(xmlFiniquito);
		   	u.highLight(xmlSimuladorFiniquito);
		   	u.mouseOver(xmlSimuladorFiniquito);
			testCase.addScreenShotCurrentStep(u, "xmlSimuladorFiniquito");
			u.click(xmlSimuladorFiniquito);
			u.hardWait(3);
			testCase.addScreenShotCurrentStep(u, "xmlSimuladorFiniquitoPantalla");
				
		}
		
	/**
	 *  Metodo para dar clic en la pantalla del simulador de comisionistas mercantil
	 * @author Yair Lizarraga
	 */
	public void clicBtnSimulador() {
		   	u.highLight(xmlBtnSimulador);
			u.mouseOver(xmlBtnSimulador);
			testCase.addScreenShotCurrentStep(u, "xmlSimuladorFiniquito");
			u.click(xmlBtnSimulador);
				
	}
		
	/**
	 *  Metodo para dar clic en la pantalla del simulador de comisionistas mercantil
	 * @author Yair Lizarraga
	 */
	public void clicBtnInstruccionesSimulador() {
		   	u.highLight(xmlBtnInstruccionesSimulador);
			u.mouseOver(xmlBtnInstruccionesSimulador);
			testCase.addScreenShotCurrentStep(u, "xmlBtnInstruccionesSimulador");
			u.click(xmlBtnInstruccionesSimulador);
				
		}
		
	/**
	 * Metodo: Para capturar los campos del paso en el menu Simulador de finiquitos
	 * 
	 * @author: Yair Lizarraga
	 * @param:  Plaza, Distrito,Comisionista, Tienda son necesarios capturar desde su data provider
	 * @return: Ninguno
	 */
	public Boolean capturaCamposSimuladorFiniquitos(String Plaza, String Distrito, String Comisionista,String Tienda ) {
		
		Boolean correct = false;
		
		try {
			
			Boolean ps = r.selectOption(Plaza, xmlPlazaSimulador);
			Boolean ds = r.selectOption(Distrito, xmlDistritoSimulador);
			Boolean cs = r.selectOption(Comisionista, xmlComisionistaSimulador);
			Boolean ts = r.selectOption(Tienda, xmlTiendaSimulador);
			
			u.highLight(xmlComisionistaSimulador);
			u.unhighLight(xmlComisionistaSimulador);
			u.click(xmlComisionistaSimulador);
			u.mouseOver(xmlComisionistaSimulador);
			testCase.addScreenShotCurrentStep(u, "Resultados Comisionistas");
			if(ps && ds && cs && ts) correct = true;
			
		}catch(Exception e) {
			testCase.addScreenShotCurrentStep(u, "No hay comisionistas");
		}
		
		return correct;
}
	
}