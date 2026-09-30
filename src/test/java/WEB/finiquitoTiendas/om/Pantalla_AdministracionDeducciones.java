package WEB.finiquitoTiendas.om;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import WEB.om.MetodosExtra;
import WEB.sarv3.om.ReportesSARv3;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Pantalla_AdministracionDeducciones {
		
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
	 * Localizadores Administracion administracion
	 ******************************************************************************************/
	
	By xmlAdministracion = By.xpath("//*[@id=\"e0_6i\"]");
	By xmlConceptoDeducciones = By.xpath("//*[@id=\"e0_7i\"]");
	
	/*
	 * Localizadores Concepto de deducciones
	 ******************************************************************************************/
	
	By xmlBtnModificar = By.xpath("	/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table[4]/tbody/tr/td/input");
	By xmlTxtAnticipoDC = By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table[3]/tbody/tr[1]/td[2]/input");
	By xmlTxtPapeleria = By.xpath("	/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table[3]/tbody/tr[64]/td[2]/input");
	By xmlBtnGuardar = By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table[4]/tbody/tr/td/input[2]");
	

	
		/**
		 * Constructor
		 ******************************************************************************************/
		/**
		 * Constructor de la clase MenuPrincipalIEP
		 * @author Yair Lizarraga
	 	* @param u (SeleniumUtil): objeto de la clase 
	 	* @param testCase (testCase): objeto de la clase
	 	*/
	
		public Pantalla_AdministracionDeducciones(SeleniumUtil u, TestCase testCase) {
			super();
			this.u = u;
			this.testCase = testCase;
			this.me = new MetodosExtra(u, testCase);
			this.r = new ReportesSARv3(u, testCase);
		
	}
	/**
	 * Metodo para dar click en la opcion Finiquito en tienda
	 * @author Yair Lizarraga
	 */
		public void clickContainer() {
 			u.highLight(xmlFiniquitotiendas);
 			u.click(xmlFiniquitotiendas);
 			testCase.addScreenShotCurrentStep(u, "clickContainer");
	 

 }
		
		/**
		 *  Metodo para posicionar el mouse sobre la barra que despliega el menu administracion y clic en Concepto de deducciones
		 * @author Yair Lizarraga
		 */
		public void MostrarConceptoDeduccion() {
		    u.highLight(xmlAdministracion);
			u.mouseOver(xmlAdministracion);
		    u.highLight(xmlConceptoDeducciones);
		    u.mouseOver(xmlConceptoDeducciones);
			testCase.addScreenShotCurrentStep(u, "xmlConceptoDeducciones");
			u.click(xmlConceptoDeducciones);
				
		}

		/**
		 * Metodo para dar click en el boton Modificar de la pantalla Concepto de deducciones
		 * @author Yair Lizarraga
		 */
		public void clicBtnModificar() {
			u.highLight(xmlBtnModificar);
			u.click(xmlBtnModificar);
			testCase.addScreenShotCurrentStep(u, "clicBtnModificar");
		
		
}
		/**
		 * Metodo para ingresar los datos en la ventana Detalle Poliza de seguros
		 * @param poliza
		 * @param cobertura
		 * @return exist: true si se llenaron correctamente todos los datos o false en caso contrario
		 * @author Maria Due�as
		 */
		public Boolean camposConceptosDeducciones(String AnticipoDC, String Papeleria) {
			Boolean exist = false;
			
			try {
				u.click(xmlTxtAnticipoDC);
				u.clear(xmlTxtAnticipoDC);
				u.sendKeys(xmlTxtAnticipoDC, AnticipoDC);
				u.highLight(xmlTxtAnticipoDC);
				u.click(xmlTxtPapeleria);
				u.clear(xmlTxtPapeleria);
				u.sendKeys(xmlTxtPapeleria, Papeleria);
				u.highLight(xmlTxtPapeleria);
				testCase.addScreenShotCurrentStep(u, "camposConceptosDeducciones");
			}catch(Exception e) {
				exist = false;
			}
			return exist;
		}
		/**
		 * Metodo para dar click en el boton Modificar de la pantalla Concepto de deducciones
		 * @author Yair Lizarraga
		 */
		public void clicBtnGuardar() {
			u.highLight(xmlBtnGuardar);
			u.click(xmlBtnGuardar);

		}
		/**
		 * Metodo para validar el texto del cuadro emergente
		 * @param poliza
		 * @return 
		 * @author Yair Lizarraga
		 */
		public Boolean validarAlerta(){
		Alert alert = u.getDriver().switchTo().alert();
		String print = alert.getText();
	    System.out.print(print);	
		boolean validacion = false;
		
		if (print.contains("La modificaci�n se realiz� exitosamente.")) {
			validacion = true;
		}
		return validacion;
		
	}
		
 }
	
