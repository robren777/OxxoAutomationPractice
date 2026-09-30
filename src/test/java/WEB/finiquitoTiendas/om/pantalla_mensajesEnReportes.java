package WEB.finiquitoTiendas.om;

import org.openqa.selenium.By;
import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class pantalla_mensajesEnReportes {
		
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	
	public pantalla_mensajesEnReportes(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
}
    
    /**
   	 * Metodo para dar click en la opcion mensaje en reportes en el menu administracion de finiquitos
   	 * @author Andrea Cano
   	 */
 public void clickAdminiYMensajesEnReportes() {
    	
    	u.highLight(By.xpath("//*[@id=\"e0_6i\"]"));
    	u.mouseOver(By.xpath("//*[@id=\"e0_6i\"]"));
    	//u.unhighLight(By.xpath("//*[@id=\\\"e0_6i\\\"]"));
    	u.hardWait(1);
    	u.highLight(By.xpath("//*[@id=\"e0_9i\"]"));
    	testCase.addScreenShotCurrentStep(u, "click Mensajes En Reportes");
    	u.click(By.xpath("//*[@id=\"e0_9i\"]"));  	
    	u.unhighLight(By.xpath("//*[@id=\"e0_8i\"]"));
    	
    }
 
 
 /**
	 * Metodo para dar click en boton modificar pantalla mensajes en reportes
	 * @author Andrea Cano
	 */
public void clicBtnkModificar() {
 	
	u.scroll();
 	u.highLight(By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table/tbody/tr[4]/td[1]/input"));
 	testCase.addScreenShotCurrentStep(u, "clickInstructivo"); 	
 	u.click(By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table/tbody/tr[4]/td[1]/input"));
 	u.hardWait(2);
 	testCase.addScreenShotCurrentStep(u, "Instructivo habilitado"); 	
 //	u.unhighLight(By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table/tbody/tr[4]/td[1]/input"));
 	
 }

/**
	 * Metodo para dar modificar pantalla mensaje en reporte
	 * @author Andrea Cano
	 */
public void modificarMensajeEnReportes() {
	
	u.highLight(By.xpath("/html/body"));  
	u.click(By.xpath("/html/body"));
	u.click(By.xpath("/html/body"));
	//u.click(By.xpath("/html/body/p"));
	u.hardWait(1);
	u.sendKeys(By.xpath("/html/body"), "Prueba enero 20"); //no escribe en la pantalla 
	u.hardWait(2); 
	testCase.addScreenShotCurrentStep(u, "click mensaje en reporte"); 
	u.unhighLight(By.xpath("/html/body"));
	
	//click btn guardar
	u.hardWait(2);
	u.highLight(By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table/tbody/tr[3]/td[1]/input[2]"));
	testCase.addScreenShotCurrentStep(u, "click en guardar");
	u.click(By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table/tbody/tr[3]/td[1]/input[2]"));
	
	
}
//*[@id="tinymce"]/p[2]
}