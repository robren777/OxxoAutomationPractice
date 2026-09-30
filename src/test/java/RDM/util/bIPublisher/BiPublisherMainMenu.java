package RDM.util.bIPublisher;

import org.openqa.selenium.By;

import utils.selenium.SeleniumUtil;

public class BiPublisherMainMenu {

	//Navigate to Logistica reports
	/**
	 * Navega a logistica reports
	 *
	 * @param u Driver
	 * 
	 * @author Franciscot2@hexaware.com
	 */
	public void navigateToLogistica(SeleniumUtil u) {
		try{
		u.getDriver().findElement(By.xpath("//*[@id=\"navigator\"]/table[2]/tbody/tr/td[2]/table/tbody/tr/td[1]/table/tbody/tr/td[2]/span[1]/a")).click();
		}catch(Exception e){
			throw new AssertionError("No se pudo navegar a la pantalla de logistica reports en BI Publisher");
		}
	}

	
	//Navigate to cumplimiento de plan y programacion
	/**
	 * Navega a cumplimiento de plan y programacion
	 *
	 * @param u Driver
	 * 
	 * @author Franciscot2@hexaware.com
	 */
	public void navigateToPlanYconteo(SeleniumUtil u) {
		try{
		u.getDriver().findElement(By.xpath("//*[@id=\"navigator\"]/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[2]/table/tbody/tr/td[2]/span[1]/a")).click();
		u.hardWait(10);
		u.getDriver().findElement(By.xpath("//*[@id=\"navigator\"]/table[2]/tbody/tr/td[2]/table/tbody/tr/td[1]/table/tbody/tr/td[2]/a")).click();
		u.hardWait(10);
		}catch(Exception e){
			throw new AssertionError("No se pudo navegar a la pantalla de cumplimiento de plan y programacion en BI Publisher");
		}
	}
	//Navigate to conteos ciclicos folder
	/**
	 * Navega a conteos ciclicos folder
	 *
	 * @param u Driver
	 * 
	 * @author Franciscot2@hexaware.com
	 */
	public void navigateToConteosCiclicos(SeleniumUtil u) {
		try{

		
		u.getDriver().findElement(By.xpath("//*[@id=\"navigator\"]/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[2]/table/tbody/tr/td[2]/span[1]/a")).click();
		u.hardWait(10);
		}catch(Exception e){
			throw new AssertionError("No se pudo navegar a la pantalla conteos ciclicos");
		}
	}
	//navigate from plan to almacen
	/**
	 * Navega a almacen
	 *
	 * @param u Driver
	 * 
	 * @author Franciscot2@hexaware.com
	 */
	public void navigateToPlanYAlmacen(SeleniumUtil u) {
		try{
		u.hardWait(10);
		u.getDriver().findElement(By.xpath("//*[@id=\"navigator\"]/table[2]/tbody/tr/td[2]/table/tbody/tr/td[1]/table/tbody/tr/td[2]/a")).click();
		u.hardWait(10);
		u.hardWait(5);
		u.getDriver().findElement(By.xpath("//*[@id=\"xdoControlPane\"]/table[3]/tbody/tr/td[1]/span/a[4]")).click();
		u.hardWait(10);
		u.getDriver().findElement(By.xpath("//*[@id=\"navigator\"]/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[1]/table/tbody/tr/td[2]/span[2]/a")).click();
		
		u.hardWait(10);
		}catch(Exception e){
			throw new AssertionError("No se pudo navegar a la pantalla de plan y almacen");
		}
	}
}
