package IMA.limiteDispersionRemesas.om;

import org.openqa.selenium.By;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Admin_remesas {
		
	SeleniumUtil u;
	TestCase testCase;
	
	//***************CONSTRUCTOR********************************
	public Admin_remesas(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
}
    
	
    /**
   	 * Metodo para dar click en la opcion remesas
   	 * @author Andrea Cano
   	 */
 public void clickRemesas() {
    	u.highLight(By.xpath("//*[@id=\"pathBean_ReportPathStyle\"]/div[2]/div[1]/div[1]/span[1]/a"));
    	testCase.addScreenShotCurrentStep(u, "clickRemesas");
    	u.click(By.xpath("//*[@id=\"pathBean_ReportPathStyle\"]/div[2]/div[1]/div[1]/span[1]/a"));  	
    }
 
 
 /**
	 * Metodo para dar click en analisis servicion nac (semana)- remesas internacionales
	 * @author Andrea Cano
	 */
public void clickAnaSerNacSem() {
 	
 	u.highLight(By.xpath("//*[@id=\"FolderList\"]/tbody/tr[4]/td[2]/span[1]/a"));
 	u.click(By.xpath("//*[@id=\"FolderList\"]/tbody/tr[4]/td[2]/span[1]/a"));
 	testCase.addScreenShotCurrentStep(u, "click en analisis servicion nac (semana)- remesas internacionales"); 	
 	
 }

public void verReporteConFiltroSeleccionado() {
 	
	u.hardWait(25);
 	u.scroll();
 	testCase.addScreenShotCurrentStep(u, "reporte con filtro seleccionado"); 	
 	
 }


}