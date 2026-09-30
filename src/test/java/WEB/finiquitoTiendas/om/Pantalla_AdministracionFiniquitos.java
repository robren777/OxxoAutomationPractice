package WEB.finiquitoTiendas.om;

import org.openqa.selenium.By;
import WEB.om.MetodosExtra;
import WEB.sarv3.om.ReportesSARv3;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Pantalla_AdministracionFiniquitos {
		
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
	By xmlConsultarFiniquitos = By.xpath("//*[@id=\"e0_2i\"]");
	
	/*
	 * Localizadores Consultar finiquitos  TEST1
	 ******************************************************************************************/
	
	By xmlPlaza = By.xpath("//*[@id=\"plazaCmb\"]");
	By xmlDistrito = By.xpath("//*[@id=\"distritoCmb\"]");
	By xmlRadioTienda = By.xpath("//*[@id=\"tblFiltros\"]/tbody/tr[6]/td[1]/input");
	By xmlTienda = By.xpath("//*[@id=\"tiendaCmb\"]");
	By xmlBtnBuscarF = By.xpath("//*[@id=\"tblFiltros\"]/tbody/tr[8]/td[2]/input");
	By xmlBtnConsultarF = By.xpath("//*[@id=\"tblResultados\"]/tbody/tr[4]/td/input");
	By xmlResultado = By.xpath("//*[@id=\"tblResultados\"]/tbody/tr[2]");
	
	
	/**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase Administracio_Finiquitos
	 * @author Yair Lizarraga
	 * @param u (SeleniumUtil): objeto de la clase 
	 * @param testCase (testCase): objeto de la clase
	 */
	
	public Pantalla_AdministracionFiniquitos(SeleniumUtil u, TestCase testCase) {
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
		public void MostrarFiniquito() {
		    	u.highLight(xmlFiniquito);
				u.mouseOver(xmlFiniquito);
		     	u.highLight(xmlConsultarFiniquitos);
		     	u.mouseOver(xmlConsultarFiniquitos);
				testCase.addScreenShotCurrentStep(u, "xmlConsultarFiniquitos");
				u.click(xmlConsultarFiniquitos);
				
		}
				
			/**
			 * Metodo: Para capturar los campos del paso 3
			 * 
			 * @author: Yair Lizarraga
			 * @param:  Plaza, Distrito, Tienda son necesarios capturar desde su data provider
			 * @return: Ninguno
			 */
			public Boolean capturaCamposFiniquito(String Plaza, String Distrito, String Tienda ) {
					
				Boolean correct = false;
					
				try {
						
						Boolean p = r.selectOption(Plaza, xmlPlaza);
						u.click(xmlRadioTienda);
						u.hardWait(5);
						Boolean d = r.selectOption(Distrito, xmlDistrito);
						Boolean t = r.selectOption(Tienda, xmlTienda);
						
						u.click(xmlBtnBuscarF);
						u.highLight(xmlBtnBuscarF);
						testCase.addScreenShotCurrentStep(u, "Buscar");
						u.unhighLight(xmlBtnBuscarF);
					
						u.highLight(xmlResultado);
						testCase.addScreenShotCurrentStep(u, "Resultados");
						u.unhighLight(xmlResultado);
						
						if(p && d && t ) correct = true;
						
					}catch(Exception e) {
						testCase.addScreenShotCurrentStep(u, "NoSeIngresoInformacion");
					}
					
					return correct;
	}
				
				/**
				 * Metodo para dar click en la opcion Finiquito en tienda paso 4
				 * @author Yair Lizarraga
				 */
				 public void clickConsultar() {
					 u.highLight(xmlBtnConsultarF);
					 u.click(xmlBtnConsultarF);
					 testCase.addScreenShotCurrentStep(u, "ClickConsultar");
				 	}
					 
					
}