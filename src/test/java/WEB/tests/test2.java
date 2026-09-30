package WEB.tests;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.om.MetodosExtra;
import WEB.om.PortalApps;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class test2 extends BaseExecution {
	SeleniumUtil u;	
	MetodosExtra m;
	
	By desCalFechaInicio = By.xpath("//form[@id='formFiltro']/div[1]/div[1]//mat-datepicker-toggle");
	By desCalFechaFin = By.xpath("//form[@id='formFiltro']/div[1]/div[2]//mat-datepicker-toggle");
	By desYear = By.xpath("//div[@class='mat-calendar-controls']/button[1]/span[1]");
	By mes = By.xpath("//table[@class='mat-calendar-table']/tbody/tr/td[@role='gridcell']");
	By dia = By.xpath("//table[@class='mat-calendar-table']/tbody/tr/td[@role='gridcell']");
	By izqBtnDesactivado = By.xpath("//div[@class='mat-calendar-controls']/button[2][@disabled='true']");
	By derBtnDesactivado = By.xpath("//div[@class='mat-calendar-controls']/button[3][@disabled='true']");
	By derBtn = By.xpath("//div[@class='mat-calendar-controls']/button[3]");

	String yearStr = "//mat-multi-year-view//table[@class='mat-calendar-table']/tbody/tr/td[@aria-label='%s']";
	String mesStr = "//table[@class='mat-calendar-table']/tbody/tr/td[@aria-label='%s']";
	String diaStr = "//table[@class='mat-calendar-table']/tbody/tr/td[@aria-label='%s']";
	
	@Test(dataProvider = "data-provider")

	public void pruebaIE_test(HashMap<String, String> data) throws Exception {
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String fecha1 = "12/12/2022";
		String fecha2 = "20/02/2023";
		
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal");
		
		u = new SeleniumUtil(new MEdgeTest(),true);
		PortalApps pa = new PortalApps(u, testCase);	

		u.get(pa.getUrl());
		
		u.waitForLoadPage();
		u.hardWait(5);
		pa.proceedToPortal();
		
		pa.cicloLogin(user, password);
		
		pa.clickPortalInventarioEspacios();

		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		m = new MetodosExtra(u, testCase);
		
		mp.clickMenus();
		By xmlMenusAdmon = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[2]/div/label"); 	// para admor marca	
		u.mouseOverAndClick(xmlMenusAdmon);
		By xmlConsultaMov = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div[1]/mat-list/div[13]/a/div/label");
		u.highLight(xmlConsultaMov);
		u.click(xmlConsultaMov);
		u.hardWait(5);
	    By xmlHeader = By.xpath("//mat-sidenav-content[@id='content-nav']/app-header");
		u.mouseOver(xmlHeader);
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		selectFecha(fecha1, desCalFechaInicio);
		
		u.hardWait(2);
		//selectFecha(fecha2, desCalFechaFin);

		

		
	}
	
	/**
	 * Metodo para seleccionar la fecha
	 * @param string, By
	 * @return nombre del mes
	 * @author Andrea Contreras
	 */
	public void selectFecha2(String fecha, By calendario) {		
		
		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(10));
		
		String[] a = fecha.split("/");
		System.out.print(a[0]+"-");
		System.out.print(a[1]+"-");
		System.out.print(a[2]);
		
		//desplegar calendario
		m.explicitWaitToBeClickable(calendario, Duration.ofSeconds(10));
		u.click(calendario);
		//desplegar a�os
		m.explicitWaitToBeClickable(desYear, Duration.ofSeconds(10));
		u.click(desYear);
		
        boolean existOption = false, ciclo = false;
		u.hardWait(1);		
        By elementYear = By.xpath(String.format(yearStr, a[2]));
       // System.out.print(String.format(yearStr, a[2]));
	
        //seleccion de a�o
		do {
		
			if (m.existElement(elementYear)) {
				u.hardWait(1);
				u.highLight(elementYear);
				u.mouseOverAndClick(elementYear);
				u.hardWait(1);
				existOption = true;
				ciclo = false;
		        System.out.print("lo encontro");
			}
			
		    if(existOption==false && m.existElement(derBtnDesactivado) == false) {
			 u.click(derBtn);
			 ciclo = true;
			 u.hardWait(1);
			 System.out.print("siguiente pagina");
		    }
		}
		while(ciclo);
		
		
		//seleccion de mes
		String mesNombre = getMesNombre(a[1]);
		
		try {
		
		List<WebElement> allMonth = u.getDriver().findElements(mes);
		for (WebElement option : allMonth) {
			String valor = option.getAttribute("aria-label");
			//System.out.print(valor);
			if ((valor.toUpperCase()).contains(mesNombre.toUpperCase())) {
				u.hardWait(1);
				m.highlight(option);
				option.click();
			}
		}
		
        }catch(StaleElementReferenceException se) {
			
		}

		
		//seleccion de dia	    
		u.hardWait(2);
		try {	
		
		m.explicitWaitToBeClickable(dia, Duration.ofSeconds(10));
		List<WebElement> allDays = u.getDriver().findElements(dia);
		for (WebElement option : allDays) {
			String valor = option.getAttribute("aria-label");
			//System.out.print(valor);
			if ((valor.toUpperCase()).contains(a[1]+" "+mesNombre))  {
				u.hardWait(1);
				m.highlight(option);
				option.click();
				//u.hardWait(1);
			}
		}
		
        }catch(StaleElementReferenceException se) {
			
		}
	}
	
	
	/**
	 * Metodo para seleccionar la fecha
	 * @param string, By
	 * @return nombre del mes
	 * @author Andrea Contreras
	 */
	public void selectFecha(String fecha, By calendario) {		
		
		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(10));
		
		String[] a = fecha.split("/");
		System.out.print(a[0]+"-");
		System.out.print(a[1]+"-");
		System.out.print(a[2]);
		
		//desplegar calendario
		m.explicitWaitToBeClickable(calendario, Duration.ofSeconds(10));
		u.click(calendario);
		//desplegar a�os
		m.explicitWaitToBeClickable(desYear, Duration.ofSeconds(10));
		u.click(desYear);
		
        boolean existOption = false, ciclo = false;
		u.hardWait(1);		
        By elementYear = By.xpath(String.format(yearStr, a[2]));
       // System.out.print(String.format(yearStr, a[2]));
        //seleccion de a�o
		do {
		
			if (m.existElement(elementYear)) {
				u.hardWait(1);
				u.highLight(elementYear);
				u.mouseOverAndClick(elementYear);
				u.hardWait(1);
				existOption = true;
				ciclo = false;
		        System.out.print("lo encontro");
			}
			
		    if(existOption==false && m.existElement(derBtnDesactivado) == false) {
			 u.click(derBtn);
			 ciclo = true;
			 u.hardWait(1);
			 System.out.print("siguiente pagina");
		    }
		}
		while(ciclo);
		
		
		//seleccion de mes
		String mesNombre = getMesNombre(a[1]);
		
		By elementMes = By.xpath(String.format(mesStr, mesNombre+" "+a[2]));
		m.explicitWaitToBeClickable(mes, Duration.ofSeconds(10));
		u.mouseOverAndClick(elementMes);
		
		u.hardWait(2);
		
		//seleccion del dia
		By elementDia = By.xpath(String.format(diaStr,a[0]+" "+mesNombre+" "+a[2]));
		m.explicitWaitToBeClickable(dia, Duration.ofSeconds(10));
		u.mouseOverAndClick(elementDia);
		

	}
	
	
	/**
	 * Metodo auxiliar para obtener el nombre del mes a partir de su numero
	 * @param string
	 * @return nombre del mes
	 * @author Andrea Contreras
	 */
	public String getMesNombre(String mes) {
		String name = null;
		switch(mes)
		{
			case "01":
			{
				name = "January";
			}
			case "02":
			{
				name = "February";
			}
			case "03":
			{
				name = "March";
			}
			case "04":
			{
				name = "April";
			}
			case "05":
			{
				name = "May";
			}
			case "06":
			{
				name = "June";
			}
			case "07":
			{
				name = "July";
			}
			case "08":
			{
				name = "August";
			}
			case "09":
			{
				name = "September";
			}
			case "10":
			{
				name = "October";
			}
			case "11":
			{
				name = "November";
			}
			case "12":
			{
				name = "December";
			}


		}
		return name;
	}

	
	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ConsultaUsuarios_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}
	
	@AfterMethod
	public void afterMethod() {
//		u.getDriver().quit();
	}

}
