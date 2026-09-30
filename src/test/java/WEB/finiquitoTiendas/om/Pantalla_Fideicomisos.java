package WEB.finiquitoTiendas.om;

import org.openqa.selenium.By;
import WEB.om.MetodosExtra;
import WEB.sarv3.om.ReportesSARv3;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Pantalla_Fideicomisos {
		
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
	By xmlEstadoFideicomiso = By.xpath("//*[@id=\"e0_3i\"]");

	
	
	/*
	 * Localizadores Consultar Fideicomisos  TEST2
	 ******************************************************************************************/
	By xmlPlazaFideicomiso = By.xpath("//*[@id=\"plazafideicomisos\"]");
	By xmlDistritoFideicomiso = By.xpath("//*[@id=\"distritofideicomisos\"]");
	By xmlComisionistaFideicomiso = By.xpath("//*[@id=\"comisionistafideicomisos\"]");
	
	/**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase MenuPrincipalIEP
	 * @author Yair Lizarraga
	 * @param u (SeleniumUtil): objeto de la clase 
	 * @param testCase (testCase): objeto de la clase
	 */
	
	public Pantalla_Fideicomisos(SeleniumUtil u, TestCase testCase) {
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
	 *  Metodo para posicionar el mouse sobre la barra que despliega el menu paso 2
	 * @author Yair Lizarraga
			 */
	 public void MostrarEstadoFideicomiso() {
		u.highLight(xmlFiniquito);
		u.mouseOver(xmlFiniquito);
		u.highLight(xmlEstadoFideicomiso);
		u.mouseOver(xmlEstadoFideicomiso);
		testCase.addScreenShotCurrentStep(u, "xmlConsultarFiniquitos");
		u.click(xmlEstadoFideicomiso);
			}
		
		
	/**
	* Metodo: Para capturar los campos del paso en el menu Estado cuenta Fideicomisos
	* 
	* @author: Yair Lizarraga
	* @param:  Plaza, Distrito, Tienda son necesarios capturar desde su data provider
	* @return: Ninguno
	*/
	public Boolean capturaCamposFideicomiso(String Plaza, String Distrito, String Comisionista ) {
			
		Boolean correct = false;
			
		try {
				
				Boolean pf = r.selectOption(Plaza, xmlPlazaFideicomiso);
				Boolean df = r.selectOption(Distrito, xmlDistritoFideicomiso);
				//Boolean cf = r.selectOption(Comisionista, xmlComisionistaFideicomiso);
				u.highLight(xmlComisionistaFideicomiso);
				u.unhighLight(xmlComisionistaFideicomiso);
				u.click(xmlComisionistaFideicomiso);
				u.mouseOver(xmlComisionistaFideicomiso);
				testCase.addScreenShotCurrentStep(u, "Resultados Comisionistas");
				if(pf && df) correct = true;
				
		}catch(Exception e) {
				testCase.addScreenShotCurrentStep(u, "No hay comisionistas");
			}
			
		return correct;
}
}