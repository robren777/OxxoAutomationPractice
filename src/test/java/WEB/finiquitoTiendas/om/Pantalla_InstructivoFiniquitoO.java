package WEB.finiquitoTiendas.om;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Pantalla_InstructivoFiniquitoO {
		
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;

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
	 * Localizadores Administracion de finiquitos
	 ******************************************************************************************/
	
	By xmlAdministracion = By.xpath("//*[@id=\"e0_6i\"]");
	By xmlInstructivoFO = By.xpath("//*[@id=\"e0_11i\"]");
	
	/*
	 * Localizadores Instructivo Finiquito Optimo
	 ******************************************************************************************/
	By xmlBtnModificar = By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table/tbody/tr[12]/td[1]/input");
	By xmlTxtMensajeCFRO = By.xpath("//*[@id=\"gastosDeducTxt_ifr\"]");
	By xmlBtnGuardar = By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table/tbody/tr[9]/td[1]/input[2]");

	
	/**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase Pantalla_AdministracionMRecomendacion
	 * @author Yair Lizarraga
	 * @param u (SeleniumUtil): objeto de la clase 
	 * @param testCase (testCase): objeto de la clase
	 */
	
				public Pantalla_InstructivoFiniquitoO(SeleniumUtil u, TestCase testCase) {
					super();
					this.u = u;
					this.testCase = testCase;
					this.me = new MetodosExtra(u, testCase);
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
				 *  Metodo para posicionar el mouse sobre la barra que despliega el menu y dar clic menu Instructivo Finiquito Optimo
				 * @author Yair Lizarraga
									 */
							 public void MostrarMensajeDeInstructivoFO() {
								u.highLight(xmlAdministracion);
								u.mouseOver(xmlAdministracion);
								u.highLight(xmlInstructivoFO);
								u.mouseOver(xmlInstructivoFO);
								testCase.addScreenShotCurrentStep(u, "xmlInstructivoFO");
								u.click(xmlInstructivoFO);
								}
				/**
				 * Metodo para dar click en el boton modificar
				 * @author Yair Lizarraga
				 */
							public void clicBtnModificar() {
								u.highLight(xmlBtnModificar);
								u.click(xmlBtnModificar);
								testCase.addScreenShotCurrentStep(u, "xmlBtnModificar");
								}
							
		
				/**
				 * Metodo para modificar el campo Mensaje Pantalla Comparativo Finiquito Real vs Optimo
				 * @author Yair Lizarraga
				 */
							public Boolean camposMensajeCFRO(String MensajeCFRO) {
								Boolean exist = false;
								
								try {
									u.clear(xmlTxtMensajeCFRO);
									u.sendKeys(xmlTxtMensajeCFRO, MensajeCFRO);
									u.highLight(xmlTxtMensajeCFRO);
									testCase.addScreenShotCurrentStep(u, "llenadoCampo");

								}catch(Exception e) {
									exist = false;
									testCase.addScreenShotCurrentStep(u, "errorLlenadoCampo");
								}
								return exist;
							}
							
				/**
				 * Metodo para dar click en el boton guardar
				 * @author Yair Lizarraga
				 */
							public void clicBtnGuardar() {
								u.highLight(xmlBtnGuardar);
								testCase.addScreenShotCurrentStep(u, "btnGuardar");
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
							testCase.addScreenShotCurrentStep("capturaAlerta", false);
							return validacion;
			}
							
}