package WEB.finiquitoTiendas.om;

import org.openqa.selenium.By;
import WEB.om.MetodosExtra;
import WEB.sarv3.om.ReportesSARv3;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Pantalla_AdministracionMRecomendacion {
		
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
	 * Localizadores Administracion de finiquitos
	 ******************************************************************************************/
	
	By xmlAdministracion = By.xpath("//*[@id=\"e0_6i\"]");
	By xmlMRecomendacion = By.xpath("//*[@id=\"e0_10i\"]");
	
	/*
	 * Localizadores Pantalla de mensajes de recomendacion
	 ******************************************************************************************/
	//
	By xmlConcepto = By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr[2]/td[1]/select");
	By xmlEstatus = By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr[2]/td[3]/select");
	By xmlMensajeRecomendacion = By.xpath("//*[@id=\"msj_recomenda\"]");
	By xmlBtnAgregar = By.xpath("//*[@id=\"add\"]");
	By xmlTxtComparacion = By.xpath("//*[@id=\"tblMsjsRecomendacion\"]");
	
	
	/**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase Pantalla_AdministracionMRecomendacion
	 * @author Yair Lizarraga
	 * @param u (SeleniumUtil): objeto de la clase 
	 * @param testCase (testCase): objeto de la clase
	 */
	
				public Pantalla_AdministracionMRecomendacion(SeleniumUtil u, TestCase testCase) {
					super();
					this.u = u;
					this.testCase = testCase;
					this.r = new ReportesSARv3(u, testCase);
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
	 *  Metodo para posicionar el mouse sobre la barra que despliega el menu y dar clic en Mensaje de recomendacion
	 * @author Yair Lizarraga
						 */
				 public void MostrarMensajeDeRecomendacion() {
					u.highLight(xmlAdministracion);
					u.mouseOver(xmlAdministracion);
					u.highLight(xmlMRecomendacion);
					u.mouseOver(xmlMRecomendacion);
					testCase.addScreenShotCurrentStep(u, "xmlMRecomendacion");
					u.click(xmlMRecomendacion);
						}
				 
		/**
		 * Metodo: Para capturar los campos del paso en el menu Mensajes de recomendacion, dar clic en el boton agregar y hacer la validacion
		 * 
		 * @author: Yair Lizarraga
		 * @param:  Concepto,Estatus,Mensaje son necesarios capturar desde su data provider
		 * @return: Ninguno
		 */
				public Boolean capturaCamposMensajeRecomendacion(String Concepto, String Estatus, String Mensaje) {
						
					Boolean correct = false;
						
					try {
							
						Boolean cr = r.selectOption(Concepto, xmlConcepto);
						Boolean er = r.selectOption(Estatus, xmlEstatus);
						String txt1 = u.getText(xmlTxtComparacion);
						u.hardWait(3);
						u.sendKeys(xmlMensajeRecomendacion, Mensaje);
						u.highLight(xmlMensajeRecomendacion);
						testCase.addScreenShotCurrentStep(u, "Mensajes Recomendacion Campos");
						
						u.hardWait(3);
						u.highLight(xmlBtnAgregar);
						testCase.addScreenShotCurrentStep(u, "xmlBtnAgregar");
						u.unhighLight(xmlBtnAgregar);
						u.click(xmlBtnAgregar);
						u.hardWait(3);
						String txt2 = u.getText(xmlTxtComparacion);
						
						if (txt1.equals(txt2) == false && cr && er == true) 
							correct = true;
							me.scrollToElement(xmlTxtComparacion);
							u.highLight(xmlTxtComparacion);
							testCase.addScreenShotCurrentStep(u, "Se Realizo el cambio!");
							
						
							
						}catch(Exception e) {
							testCase.addScreenShotCurrentStep(u, "No se realizo el cambio!");
						}
					
					return correct;
					
					}
					
				}
	/*	/**
		 * Metodo para dar click en boton Agregar
		 * @author Yair Lizarraga
		 */
			/*	public void clicBtnAgregar() {
					u.highLight(xmlBtnAgregar);
					testCase.addScreenShotCurrentStep(u, "xmlBtnAgregar");
					u.unhighLight(xmlBtnAgregar);
					u.click(xmlBtnAgregar);
					}*/
