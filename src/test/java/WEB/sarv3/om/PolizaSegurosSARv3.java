package WEB.sarv3.om;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class PolizaSegurosSARv3 {
	
	TestCase testCase;
	SeleniumUtil u;
	MetodosExtra me;
	ReportesSARv3 r;
	
	/*
	 * Localizadores Poliza de Seguros
	 ******************************************************************************************/
	By xmlContenedor = By.id("contenedor");
	
	By xmlVerDetalleBtn = By.xpath("//*[@id=\"forma\"]/div[4]/a");
	By xmlCrearPoliza = By.xpath("//div[@id='crearPolizaBtn']/a");
	By xmlTable = By.xpath("//*[@id=\"resultadoDeBusqueda\"]");
	By xmlTableTd = By.xpath("//table/tbody/tr/td[4]");
	
	By xmlPoliza = By.xpath("//input[@id='polizanumPoliza']");
	By xmlCobertura = By.xpath("//input[@id='polizaCobertura']");
	By xmlSMDF = By.xpath("//input[@id='polizaSmdf']");
	By xmlDeducible = By.xpath("//input[@id='polizadeduciblePtr']");
	By xmlCalendario = By.xpath("//div[@id='contenedor_capa2']/p/button");
	By xmlGuardarBtn = By.xpath("//div[@class='grid1']/a[2]");
	By xmlEditarBtn = By.xpath("//*[@id=\"contenedor\"]/div[9]/div[1]/a[1]");
	
	
	/*
	 * Constructor
	 ******************************************************************************************/
	public PolizaSegurosSARv3(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		me = new MetodosExtra(u, testCase);
		r = new ReportesSARv3(u, testCase);
	}
	
	/*
	 * Metodos Poliza de Seguros
	 ******************************************************************************************/
	
	/**
	 * Metodo para dar click en el boton Crear Poliza
	 * @author Maria Due�as
	 */
	public void clickCrearPoliza() {
		u.highLight(xmlCrearPoliza);
		testCase.addScreenShotCurrentStep(u, "clickCrearPoliza");
		u.unhighLight(xmlCrearPoliza);
		u.click(xmlCrearPoliza);
	}
	
	/**
	 * Metodo para ingresar los datos en la ventana Detalle Poliza de seguros
	 * @param poliza
	 * @param cobertura
	 * @param smdf
	 * @param deducible
	 * @param fecha
	 * @return exist: true si se llenaron correctamente todos los datos o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean camposPoliza(String poliza, String cobertura, String smdf, String deducible, String fecha) {
		Boolean exist = false;
		
		try {
			u.sendKeys(xmlPoliza, poliza);
			u.highLight(xmlPoliza);
			u.sendKeys(xmlCobertura, cobertura);
			u.highLight(xmlCobertura);
			u.sendKeys(xmlSMDF, smdf);
			u.highLight(xmlSMDF);
			u.sendKeys(xmlDeducible, deducible);
			Boolean band = r.selectDate(fecha, xmlCalendario, xmlContenedor);
			if (band) exist = true;
		}catch(Exception e) {
			exist = false;
		}
		return exist;
	}
	
	/**
	 * Metodo para dar click en boton Guardar
	 * @author Maria Due�as
	 */
	public void clickGuardarBtn() {
		u.highLight(xmlGuardarBtn);
		testCase.addScreenShotCurrentStep(u, "clickGuardarBtn");
		u.unhighLight(xmlGuardarBtn);
		u.click(xmlGuardarBtn);
	}
	
	public Boolean seleccionarPoliza(String poliza) {
		Boolean existOption = false;
		
		List <WebElement> allOptions = u.getDriver().findElements(xmlTableTd);
		Integer row_count = allOptions.size();
		
		if(row_count >= 2) {
			for(WebElement option:allOptions){
				if(option.getText().equals(poliza)) {//busca la opcion que se pasa por parametro
				  //u.highLight((By) option);
				  
				  //u.unhighLight((By) option);
				  option.click(); //da clic a esa opcion 
				  u.hardWait(2);
				  testCase.addScreenShotCurrentStep(u, "selectOption");
				  existOption = true;
				  break;
				}
			}
		}
		
		
		
		return existOption;
	}
	
	public void clickVerDetalle() {
		u.highLight(xmlVerDetalleBtn);
		testCase.addScreenShotCurrentStep(u, "clickVerDetalle");
		u.unhighLight(xmlVerDetalleBtn);
		u.click(xmlVerDetalleBtn);
	}
	
	public void clickEditar() {
		me.scrollToElement(xmlEditarBtn);
		u.highLight(xmlEditarBtn);
		testCase.addScreenShotCurrentStep(u, "clickEditar");
		u.unhighLight(xmlEditarBtn);
		u.click(xmlEditarBtn);
		testCase.addScreenShotCurrentStep(u, "Editar");
	}
	
	public Boolean editarCamposPoliza(String poliza, String cobertura, String smdf, String deducible, String fecha) {
		Boolean correct = false;
		
		u.clear(xmlPoliza);
		u.highLight(xmlPoliza);
		u.clear(xmlCobertura);
		u.highLight(xmlCobertura);
		u.clear(xmlSMDF);
		u.highLight(xmlSMDF);
		u.clear(xmlDeducible);
		u.highLight(xmlDeducible);
		
		correct = camposPoliza(poliza, cobertura, smdf, deducible, fecha);
		
		return correct;
	}
	
	

}
