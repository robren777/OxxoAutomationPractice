package WEB.util.PortalEquipamiento;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;


public class PomPortalEquipamientoBandejaHistoricoV {
	
	SeleniumUtil u;
	private WebDriverWait wait;
	TestCase testCase;
	PomPortalEquipamiento PPE;
	PomPortalEquipamientoBandejaAprobador PPEBA;
	
	/*
	 * Localizadores Menu
	 ******************************************************************************************/
	private By consultar = By.xpath("//button[normalize-space()='Consultar']");
	private By mainPagina = By.xpath("//div[@id='main']");

	
	/*
	 * Constructor
	 ******************************************************************************************/
	public PomPortalEquipamientoBandejaHistoricoV(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		this.testCase = testCase;
		this.PPE = new PomPortalEquipamiento(u,testCase); //<-- para utilizar los metodos del POM PomPortalEquipamiento
		this.PPEBA = new PomPortalEquipamientoBandejaAprobador(u,testCase);
	
	}
	
	/*
	 * METODOS 
	 ******************************************************************************************/
	
	/**
	 * Al seleccionar algun estatus busca en pantalla todos los que se encuentren  
	 * con dicho estatus seleccionado 
	 * @param Estatus
	 * @date 14/05/2025
	 * @author Elberth Ruiz
	 */
	public void BandejaAprob(String Estatus) {
		Boolean bandera = false;
		
	    PPE.cargandoMsg();
	    By StatusApro = By.xpath("//Select/option[contains(text(),'"+Estatus+"')]");
		
	    //u.hardWait(1);
	    u.click(StatusApro);
	    u.hardWait(1);
	    u.highLight(consultar);
		u.click(consultar);
		testCase.addScreenShotCurrentStep("");
		u.unhighLight(consultar);
		PPE.cargandoMsg();
		
		int count = 0;
		do {
			count =count+1;
			By StatusTabla = By.xpath("//table/tbody/tr["+count+"]/td[contains(text(),'"+Estatus.toUpperCase()+"')]");
			if(u.getDriver().findElements(StatusTabla).size() != 0) {
				bandera = true;
				u.scrollToElement(StatusTabla);
				u.highLight(StatusTabla);
				testCase.addScreenShotCurrentStep("");
				u.unhighLight(StatusTabla); 
			}else{
				bandera=false;
			}
			
		}while(bandera);
		u.scrollToElement(mainPagina);
		testCase.addScreenShotCurrentStep("");		
    }
	
	/**
	 * Busca en la pantalla y va almacenando en variable todos los registros
	 *   
	 * @param variable
	 * @date 14/05/2025
	 * @return variable <-- regresa el valor de un grid para que en el script se construya en conjunto con la consulta de la tabla en BD
	 * @author Elberth Ruiz
	 */
	public String extrae (String Estatus) {
		Boolean bandera = false;
		int count = 0;
		String variable = "";
		do {
			count =count+1;
			By StatusTabla = By.xpath("//table/tbody/tr["+count+"]/td[contains(text(),'"+Estatus.toUpperCase()+"')]/preceding-sibling::td[2]");
			if(u.getDriver().findElements(StatusTabla).size() != 0) {
				bandera = true;
				
				String extIDLE = u.getText(StatusTabla);
				variable = variable + extIDLE + ","; 
			}else{
				bandera=false;
			}
			
		}while(bandera);
		variable = variable.substring(0, variable.length() - 1);
		return variable;
	}
}
