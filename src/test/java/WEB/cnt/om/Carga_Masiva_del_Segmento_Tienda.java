package WEB.cnt.om;

import java.io.File;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import WEB.om.MetodosExtra;
import config.Constants;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Carga_Masiva_del_Segmento_Tienda {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	
	/**
	 * Localizadores de la ventana Carga Masiva del Segmento Tienda
	 ******************************************************************************************/
	
	 By seleccionArchivoTable = By.xpath("//form/table");
	 By archivo = By.id("archivo_mxp");
	 By cargarBtn = By.xpath("//form/table/tbody/tr[2]/td[3]/input");
	 By mensajeCarga = By.xpath("/html/body/table/tbody/tr[2]/td");
	 By regresarSelTienda = By.xpath("//center/a");
	 
	/**
	 * Localizadores de la ventana Formato y Entorno
	 ******************************************************************************************/
	 
	 By concepto_diferenciador = By.xpath("//*[@id=\"p_concepto_diferenciador_id\"]");
	 By segmento_maestro= By.xpath("//input[@name=\"p_segmento_maestro_id\"]");
	 By potencial_hogar = By.xpath("//input[@id=\"p_potencial_id_1\"]");
	 By potencial_comida = By.xpath("//input[@id=\"p_potencial_id_2\"]");
	 By potencial_desayuno = By.xpath("//input[@id=\"p_potencial_id_3\"]");
	 By overlay_hogar = By.xpath("//input[@id=\"p_overlay_id_1\"]");
	 By overlay_comida = By.xpath("//input[@id=\"p_overlay_id_2\"]");
	 By overlay_desayuno = By.xpath("//input[@id=\"p_overlay_id_3\"]");
	 
	/**
	 * Constructor
	 ******************************************************************************************/

	public Carga_Masiva_del_Segmento_Tienda(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}
	
	/**
	 * Metodos de la ventana Carga Masiva del Segmento Tienda
	 ******************************************************************************************/
	
	/**
	 * Metodo para validar pantalla de Carga Masiva
	 * @author Maria Due�as
	 */
	public Boolean pantallaCargaMasivaSegTienda() {
		Boolean exist = false;
		try {
			for (String winHandle : u.getDriver().getWindowHandles()) {
				u.getDriver().switchTo().window(winHandle);
			}

			u.hardWait(4);
			u.getDriver().manage().window().maximize();

			u.highLight(seleccionArchivoTable);
			testCase.addScreenShotCurrentStep(u, "pantallaCargaMasivaSegTienda");
			u.unhighLight(seleccionArchivoTable);
			exist = true;
		}catch(Exception e) {
			testCase.addScreenShotCurrentStep(u, "Error");
		}
		
		return exist;
		
	}
	
	/**
	 * Metodo para subir archivo
	 * @param nombre
	 * @author Maria Due�as
	 */
	public void cargarArchivo(String nombre) {
		String pathFile = Constants.PATTERN_PATH + File.separator + "WEB" + File.separator + "CNT" + File.separator + nombre;
		u.sendKeys(archivo, pathFile);
		u.highLight(archivo);
		testCase.addScreenShotCurrentStep(u, "cargarArchivo");
		u.unhighLight(archivo);
		
		u.highLight(cargarBtn);
		testCase.addScreenShotCurrentStep(u, "clickCargarArchivo");
		u.unhighLight(cargarBtn);
		u.click(cargarBtn);
	}
	
	/**
	 * Metodo para aceptar alerta
	 * @return Retorna true si se acepto correctamente
	 * @author Maria Due�as
	 */
	public boolean alerta() {
		Boolean exist = false;

		try {

			Alert alert = u.getDriver().switchTo().alert();

			String text = alert.getText();
			System.out.print(text + "\n");
			testCase.addScreenShotCurrentStep("alerta");

			alert.accept();
			testCase.addScreenShotCurrentStep("confirmarAlerta");
			exist = true;

		} catch (Exception e) {
			exist = false;
		}
		return exist;
	}
	
	/**
	 * Metodo para validar que los mensajes sean correctos
	 * @param mnj: Mensaje a validar
	 * @return Retorna true si son correctos
	 * @author Maria Due�as
	 */
	public Boolean validarMensaje(String mnj) {
		Boolean correct = false;
		
		try {
			
			u.getDriver().switchTo().frame("frameoper");
			
			String cadena [] = u.getText(mensajeCarga).split(":");
			String mensaje = cadena[1].trim();
	
			u.highLight(mensajeCarga);
			testCase.addScreenShotCurrentStep(u, "mensajeCarga");
			u.unhighLight(mensajeCarga);
			
			if(mensaje.equals(mnj)) {
				correct = true;
			}
			
		}catch(Exception e) {
			
		}
		
		return correct;
	}
	
	/**
	 * Metodo para validar que los mensajes sean correctos
	 * @param mnj: Mensaje a validar
	 * @return Retorna true si son correctos
	 * @author Maria Due�as
	 */
	public Boolean validarMensajeContains(String mnj) {
		Boolean correct = false;
		
		try {
			
			u.getDriver().switchTo().frame("frameoper");
			
			String cadena [] = u.getText(mensajeCarga).split(":");
			String mensaje = cadena[1].trim();
	
			u.highLight(mensajeCarga);
			testCase.addScreenShotCurrentStep(u, "mensajeCarga");
			u.unhighLight(mensajeCarga);
			
			if(mensaje.contains(mnj)) {
				correct = true;
			}
			
		}catch(Exception e) {
			
		}
		
		return correct;
	}
	
	/**
	 * Metodo para validar que los mensajes sean correctos
	 * @param mnj: Mensaje a validar
	 * @return Retorna true si son correctos
	 * @author Maria Due�as
	 */
	public Boolean validarMensajeCorrecto(String mnj) {
		Boolean correct = false;
		
		try {
			
			u.getDriver().switchTo().frame("frameoper");
			
			String mensaje  = u.getText(mensajeCarga).trim();
	
			u.highLight(mensajeCarga);
			testCase.addScreenShotCurrentStep(u, "mensajeCarga");
			u.unhighLight(mensajeCarga);
			
			if(mensaje.contains(mnj)) {
				correct = true;
			}
			
		}catch(Exception e) {
			testCase.addScreenShotCurrentStep(u, "mensajeCarga");
		}
		
		return correct;
	}
	
	/**
	 * Metodo para validar mensaje de alerta
	 * @return Retorna true si es correcto el mensaje
	 * @author Maria Due�as
	 */
	public boolean validarMensajeAlerta() {
		Boolean exist = false;

		try {

			Alert alert = u.getDriver().switchTo().alert();

			String text = alert.getText().trim();
			System.out.print("-" + text + "-\n");
			testCase.addScreenShotCurrentStep("alerta");
			
			if(text.equals("*Formato de archivo inv�lido. Favor de subir un archivo v�lido (.csv)")) {
				alert.accept();
				testCase.addScreenShotCurrentStep("confirmarAlerta");
				exist = true;
			}
			
		} catch (Exception e) {
			exist = false;
		}
		return exist;
	}
	
	/**
	 * Metodo para dar click en el boton REGRESAR A SELECCIONAR TIENDA
	 * @author Maria Due�as
	 */
	public void clickRegresar(String window) {
		
		u.getDriver().switchTo().window(window);
		
	}
	
	/**
	 * Metodo para cambiar de pantalla
	 * @author Maria Due�as
	 */
	public void changeWindowFormatoyEntorno() {
		
		u.waitForLoadPage();
		u.hardWait(5);
		
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}
		u.getDriver().switchTo().frame("framedatos");
		
		testCase.addScreenShotCurrentStep(u, "Carga");
	}
	
	/**
	 * Metodo para comparar la informacion del aplicativo y BD
	 * @param diferenciador
	 * @param segmento
	 * @param pHogar
	 * @param pComida
	 * @param pDesayuno
	 * @param oHogar
	 * @param oComida
	 * @param oDesayuno
	 * @return Retorna true si la informacion coincide
	 * @author Maria Due�as
	 */
	public Boolean compararInformacion(String diferenciador, String segmento, String pHogar, String pComida, 
			String pDesayuno, String oHogar, String oComida, String oDesayuno) {
		Boolean correct = false;
		
		Select select = new Select(u.getDriver().findElement(concepto_diferenciador));
		WebElement option = select.getFirstSelectedOption();
		
		String dif = option.getText().trim();
		String seg = u.getDriver().findElement(segmento_maestro).getAttribute("value").trim();
		String pHog = u.getDriver().findElement(potencial_hogar).getAttribute("value").trim();
		String pCom = u.getDriver().findElement(potencial_comida).getAttribute("value").trim();
		String pDes = u.getDriver().findElement(potencial_desayuno).getAttribute("value").trim();
		String oHog = u.getDriver().findElement(overlay_hogar).getAttribute("value").trim();
		String oCom = u.getDriver().findElement(overlay_comida).getAttribute("value").trim();
		String oDes = u.getDriver().findElement(overlay_desayuno).getAttribute("value").trim();
		
		u.highLight(concepto_diferenciador);
		u.highLight(segmento_maestro);
		u.highLight(potencial_hogar);
		u.highLight(potencial_comida);
		u.highLight(potencial_desayuno);
		u.highLight(overlay_hogar);
		u.highLight(overlay_comida);
		u.highLight(overlay_desayuno);
		testCase.addScreenShotCurrentStep(u, "Datos");
		
		if (diferenciador.equals(dif) && segmento.equals(seg) && pHogar.equals(pHog) && pComida.equals(pCom) &&
				pDesayuno.equals(pDes) && oHogar.equals(oHog) && oComida.equals(oCom) && oDesayuno.equals(oDes)) {
			correct = true;
		}
		
		u.unhighLight(concepto_diferenciador);
		u.unhighLight(segmento_maestro);
		u.unhighLight(potencial_hogar);
		u.unhighLight(potencial_comida);
		u.unhighLight(potencial_desayuno);
		u.unhighLight(overlay_hogar);
		u.unhighLight(overlay_comida);
		u.unhighLight(overlay_desayuno);
		
		return correct;
	}
}
