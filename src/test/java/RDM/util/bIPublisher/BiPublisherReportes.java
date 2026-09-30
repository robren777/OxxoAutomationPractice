package RDM.util.bIPublisher;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import utils.selenium.SeleniumUtil;

public class BiPublisherReportes {
	WebElement selectElement;

	//Seleccionar ceids para bara
	/**
	 * Selecciona el cedis de bara
	 *
	 * @param u Driver
	 * 
	 * @author Franciscot2@hexaware.com
	 */
	public void selectCedis(SeleniumUtil u) {
		try{
		selectElement = u.getDriver().findElement(By.id("P_CEDIS"));
		u.hardWait(5);
		Select select = new Select(selectElement);
		u.hardWait(5);
		select.selectByValue("9999999059");
		u.hardWait(5);
		}catch(Exception e){
			throw new AssertionError("No se pudo validar la seleccion del cedis por medio del filtro");
		}
	}
	
	//Selecionar mes 
	/**
	 * Selecciona el mes para un reporte
	 *
	 * @param u Driver
	 * @param mes Mes a seleccionar
	 * @author Franciscot2@hexaware.com
	 */
	public void selectMes(SeleniumUtil u, String mes) {
		try{
		selectElement = u.getDriver().findElement(By.id("P_MONTH"));
		u.hardWait(5);
		Select select = new Select(selectElement);
		u.hardWait(5);
		select.selectByValue(mes);
		u.hardWait(5);
		}catch(Exception e){
			throw new AssertionError("No se pudo validar la seleccion de la fecha por medio del filtro mes");
		}
	}
	//Select anio
	/**
	 * Selecciona el anio para un reporte
	 *
	 * @param u Driver
	 * @param anio Anio a seleccionar
	 * @author Franciscot2@hexaware.com
	 */
	public void selectanio(SeleniumUtil u, String anio) {
		try{
		
		selectElement = u.getDriver().findElement(By.id("P_YEAR"));
		u.hardWait(5);
		Select select = new Select(selectElement);
		u.hardWait(5);
		select.selectByValue(anio);
		u.hardWait(5);	
		}catch(Exception e){
			throw new AssertionError("No se pudo validar la seleccion de la fecha por medio del filtro anio");
		}
	}
	//validate turno
	/**
	 * Valida el filtro turno
	 *
	 * @param u Driver
	 * 
	 * @author Franciscot2@hexaware.com
	 */
	public void validateFilterTurno(SeleniumUtil u) {
		try{
		u.getDriver().findElement(By.id("P_TURNO")).click();
	    u.hardWait(5);
		}catch(Exception e){
			throw new AssertionError("No se pudo validar que el filtro turno exista");
		}
	}
	//generate reporte
	/**
	 * Genera el reporte con un boton 
	 *
	 * @param u Driver
	 * 
	 * @author Franciscot2@hexaware.com
	 */
	public void generateReporte(SeleniumUtil u) {
		try{
		u.getDriver().findElement(By.xpath("//*[@id=\"xdoRptForm\"]/table[3]/tbody/tr/td[1]/button[1]")).click();
	    u.hardWait(60);
		}catch(Exception e){
			throw new AssertionError("No se pudo validar que el boton de gennerar reporte exista");
		}
	}
	//generate reporte by turno
	/**
	 * Selecciona el turno que se enviara al filtro
	 *
	 * @param u Driver
	 * @param turno Turno a seleccionar
	 * 
	 * @author Franciscot2@hexaware.com
	 */
	public void generateReporteByTurno(SeleniumUtil u, String turno) {
		try{
		u.getDriver().findElement(By.id("P_TURNO")).clear();
	    u.hardWait(5);
		u.getDriver().findElement(By.id("P_TURNO")).sendKeys(turno);
	    u.hardWait(5);
		u.hardWait(5);
		u.getDriver().findElement(By.xpath("//*[@id=\"xdoRptForm\"]/table[3]/tbody/tr/td[1]/button[1]")).click();
		u.hardWait(60);
		}catch(Exception e){
			throw new AssertionError("No se pudo seleccionar el turno por medio del filtro turno");
		}
	}
}
