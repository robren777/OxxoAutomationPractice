package WEB.inventarioEspacios.om;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class AdmonConsultaMovtos {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;
	
	/*
	 * Localizadores Pantalla Detalle - AdmonConsultaMovtos
	 ******************************************************************************************/
	// Localizadores de la pantalla loguin
	By xmlUsuario = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/p");
    By xmlNegocio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[1]");
    By xmlPerfil = By.xpath("");
    
    // Localizador(es) de la pantalla AdmonConsultaMovtos
    By xmlpantalla = By.xpath("//*[@id=\"content-nav\"]");
    
    // Localizadores de la fecha inicial
    
    By xmlFechaIni = By.xpath("//*[@id=\"mat-input-0\"]");
    By xmlFechaFin = By.xpath("//*[@id=\"mat-input-1\"]");
    By xmlCalendario = By.xpath("//*[@id=\"formFiltro\"]/div[1]/div[1]/div/mat-form-field/div/div[1]/div[2]/mat-datepicker-toggle/button");
    By xmlCalendario2 = By.xpath("//*[@id=\"formFiltro\"]/div[1]/div[2]/div/mat-form-field/div/div[1]/div[2]/mat-datepicker-toggle/button");
    By xmlDespAnio = By.xpath("//*[@id=\"mat-datepicker-0\"]/mat-calendar-header/div/div/button[1]/span[1]/div");
    By xmlDespAnio2 = By.xpath("//*[@id=\"mat-datepicker-1\"]/mat-calendar-header/div/div/button[1]/span[1]/div");
    By xmlAvanceAnio = By.xpath("//*[@id=\"mat-datepicker-0\"]/mat-calendar-header/div/div/button[3]");
    By xmlRetroAnio = By.xpath("//*[@id=\"mat-datepicker-1\"]/mat-calendar-header/div/div/button[2]");
    
    // Localizador del campo tipo de movto
    By xmlTipoMov = By.xpath("//*[@id=\"formFiltro\"]/div[1]/div[3]/div/mat-form-field/div/div[1]/div");                              
    By xmlEe = By.xpath("//*[@id=\"mat-option-9\"]/span");
    
    // Localizador del campo plaza
    By xmlPlaza = By.xpath("//*[@id=\"mat-select-8\"]/div/div[2]/div");
    By xmlPlazaSel = By.xpath("//div[@id=\"mat-select-8-panel\"]/mat-option/span[contains(text(), \" Tijuana - 10TIJ \")]");
    
    // Localizador del campo usuario
    By xmlUsuarioConsulta = By.xpath("//*[@id=\"mat-input-53\"]");
   
    // Localizador del boton buscar
    By xmlBtnBuscar = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[2]/button/span[1]");
    
 // Localizador del resultado de los campos 
    By xmlTipoMovto = By.xpath("//div[@role=\"listbox\"]/mat-option/span");
    By xmlLblId = By.xpath("//*[@id=\"content-nav\"]/app-consulta-movimientos/div/div/div/table/thead/tr/th[1]");
    By xmlLbFecha = By.xpath("//*[@id=\"content-nav\"]/app-consulta-movimientos/div/div/div/table/thead/tr/th[2]");
    By xmlLblNomTipoMov = By.xpath("//*[@id=\"content-nav\"]/app-consulta-movimientos/div/div/div/table/thead/tr/th[3]");
    By xmlLblCvePlza = By.xpath("//*[@id=\"content-nav\"]/app-consulta-movimientos/div/div/div/table/thead/tr/th[4]");
    By xmlLblNomPlza = By.xpath("//*[@id=\"content-nav\"]/app-consulta-movimientos/div/div/div/table/thead/tr/th[5]");
    By xmlLblNomSPlza = By.xpath("//*[@id=\"content-nav\"]/app-consulta-movimientos/div/div/div/table/thead/tr/th[6]");
    By xmlLblCrTda = By.xpath("//*[@id=\"content-nav\"]/app-consulta-movimientos/div/div/div/table/thead/tr/th[7]");
    By xmlLblNomTda = By.xpath("//*[@id=\"content-nav\"]/app-consulta-movimientos/div/div/div/table/thead/tr/th[8]");
    By xmlLblDirecc = By.xpath("//*[@id=\"content-nav\"]/app-consulta-movimientos/div/div/div/table/thead/tr/th[9]");
    By xmlLblEspacio = By.xpath("//*[@id=\"content-nav\"]/app-consulta-movimientos/div/div/div/table/thead/tr/th[10]");
    By xmlLblNomEspa = By.xpath("//*[@id=\"content-nav\"]/app-consulta-movimientos/div/div/div/table/thead/tr/th[11]");
    By xmlLblElimId = By.xpath("//*[@id=\"content-nav\"]/app-consulta-movimientos/div/div/div/table/thead/tr/th[12]");
    By xmlLblCampo = By.xpath("//*[@id=\"content-nav\"]/app-consulta-movimientos/div/div/div/table/thead/tr/th[13]");
    By xmlLblValor = By.xpath("//*[@id=\"content-nav\"]/app-consulta-movimientos/div/div/div/table/thead/tr/th[14]");
    By xmlLblUsuario = By.xpath("//*[@id=\"content-nav\"]/app-consulta-movimientos/div/div/div/table/thead/tr/th[15]");
    By xmlLblNombre = By.xpath("//*[@id=\"content-nav\"]/app-consulta-movimientos/div/div/div/table/thead/tr/th[16]");
    By xmlLblPaterno = By.xpath("//*[@id=\"content-nav\"]/app-consulta-movimientos/div/div/div/table/thead/tr/th[17]");
    By xmlLblMaterno = By.xpath("//*[@id=\"content-nav\"]/app-consulta-movimientos/div/div/div/table/thead/tr/th[18]");
    By xmlLblPosicion = By.xpath("//*[@id=\"content-nav\"]/app-consulta-movimientos/div/div/div/table/thead/tr/th[19]");
	
    /*
     * Variable que reciben un parametro
     * ***************************************************************************************/                              	  
  //div[@id="mat-select-0-panel"]/mat-option/span[contains(text(),' Modificaci�n de campo de Direcci�n de env�o ')]  
    String StrAltaDirEnvio = ("//div[@id=\"mat-select-6-panel\"]/mat-option[1]/span[contains(text(),'%s')]");
    String StrTipoMovSel = ("//div[@id=\"mat-select-4-panel\"]/mat-option[2]/span[contains(text(),'%s')]");
    String StrAsigDirEnvT = ("//div[@id=\"mat-select-14-panel\"]/mat-option[3]/span[contains(text(),'%s')]");
    String StrElimCamTienda = ("//div[@id=\"mat-select-0-panel\"]/mat-option[4]/span[contains(text(),'%s')]");
    String StrElimDirEnvios = ("//div[@id=\"mat-select-6-panel\"]/mat-option[5]/span[contains(text(),'%s')]");
    String StrElimEspacios = ("//div[@id=\"mat-select-4-panel\"]/mat-option[6]/span[contains(text(),'%s')]");
    String StrElimEstatusMsj = ("//div[@id=\"mat-select-4-panel\"]/mat-option[7]/span[contains(text(),'%s')]");
    String StrElimGpoEspacios = ("//div[@id=\"mat-select-4-panel\"]/mat-option[8]/span[contains(text(),'%s')]");
    String StrElimPlazaCCM = ("//div[@id=\"mat-select-4-panel\"]/mat-option[10]/span[contains(text(),'%s')]");
    String StrElimProv = ("//div[@id=\"mat-select-2-panel\"]/mat-option[11]/span[contains(text(),'%s')]");
    String StrElimRegla = ("//div[@id=\"mat-select-4-panel\"]/mat-option[12]/span[contains(text(),'%s')]");
    String StrElimTienda = ("//div[@id=\"mat-select-4-panel\"]/mat-option[14]/span[contains(text(),'%s')]");
    String StrElimPopPop = ("//div[@id=\"mat-select-2-panel\"]/mat-option[15]/span[contains(text(),'%s')]");                        
    String StrElimTipPed = ("//div[@id=\"mat-select-2-panel\"]/mat-option[16]/span[contains(text(),'%s')]");
    String StrModCantEspTien = ("//div[@id=\"mat-select-26-panel\"]/mat-option[17]/span[contains(text(),'%s')]");
    String StrModcampoDirEnv = ("//div[@id=\"mat-select-8-panel\"]/mat-option[18]/span[contains(text(),'%s')]");
    String StrModifTienda = ("//div[@id=\"mat-select-4-panel\"]/mat-option[19]/span[contains(text(),'%s')]");
                         
    /*
     * Variable que reciben un parametro
     * ***************************************************************************************/ 
	By desCalFechaInicio = By.xpath("//form[@id='formFiltro']/div[1]/div[1]//mat-datepicker-toggle");
	By desCalFechaFin = By.xpath("//form[@id='formFiltro']/div[1]/div[2]//mat-datepicker-toggle");
	By desYear = By.xpath("//div[@class='mat-calendar-controls']/button[1]/span[1]");
	By mes = By.xpath("//table[@class='mat-calendar-table']/tbody/tr/td[@role='gridcell']");
	By dia = By.xpath("//table[@class='mat-calendar-table']/tbody/tr/td[@role='gridcell']");
	By izqBtnDesactivado = By.xpath("//div[@class='mat-calendar-controls']/button[2][@disabled='true']");
	By derBtnDesactivado = By.xpath("//div[@class='mat-calendar-controls']/button[3][@disabled='true']");
	By derBtn = By.xpath("//div[@class='mat-calendar-controls']/button[3]");

	String yearStr = "//mat-multi-year-view//table[@class='mat-calendar-table']/tbody/tr/td[@aria-label='%s']";
	String mesStr = "//table[@class='mat-calendar-table']/tbody/tr[2]/td[@aria-label='%s']";
	String diaStr = "//table[@class='mat-calendar-table']/tbody/tr/td[@aria-label='%s']";
    /**
	 * Constructor
	 * 
	 ******************************************************************************************/
	public AdmonConsultaMovtos(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);
    }
	
	/**
	 * Método para llenar filtros de la consulta de movtos, se alimenta desde el data provider
	 * 
	 * @author Manuel CUen
	 * @param user: son necesarios llenarlos en su data provider para la consulta
	 * tipoMovto: 
	 * fechaInicio: 
	 * fechaFinal:  
	 * @return ninguno
	 */
	public void clickIngresaFiltros(String user, String tipoMovto, String fechaInicio, String fechaFinal) {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));

		selectFecha(fechaInicio, desCalFechaInicio);
		
		 boolean existOption = false;
		 WebElement element = m.explicitWaitToBeClickable(xmlTipoMov, Duration.ofSeconds(30));
		 u.hardWait(2);
		 element.click();
		 List<WebElement> allOptions = u.getDriver().findElements(xmlTipoMovto);
		 for (WebElement option : allOptions) {
			 if (option.getText().equals(tipoMovto)) {
				 u.hardWait(1);
				 option.click();
				 u.hardWait(1);
				 existOption = true;
		    	 break;
			 }
		 }
			
	//   Seleccion de Boton de Buscar
		u.highLight(xmlBtnBuscar);
		u.click(xmlBtnBuscar);
	
		testCase.addScreenShotCurrentStep(u, "clickIngresaFiltros");	
		u.unhighLight(xmlBtnBuscar);
		u.unhighLight(xmlTipoMov);
//		u.unhighLight(xmlFechaIni);
//		u.unhighLight(xmlFechaFin);
		
	}
	
	/**
	 * Método para mostrar los campos de la busqueda
	 * 
	 * @author Manuel CUen
	 * @param 
	 * @return ninguno
	 */
	public void clickMuestraCamposPasoDiez() {
	
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		
//  Seleccion de etiquetas de campos
		u.highLight(xmlLblId);
		u.click(xmlLblId);
		u.highLight(xmlLbFecha);
		u.click(xmlLbFecha);
		u.highLight(xmlLblNomTipoMov);
		u.click(xmlLblNomTipoMov);
		u.highLight(xmlLblCvePlza);
		u.click(xmlLblCvePlza);
		u.highLight(xmlLblNomPlza);
		u.click(xmlLblNomPlza);
		u.highLight(xmlLblNomSPlza);
		u.click(xmlLblNomSPlza);
		u.highLight(xmlLblCrTda);
		u.click(xmlLblCrTda);
		u.highLight(xmlLblNomTda);
		u.click(xmlLblNomTda);
		u.highLight(xmlLblDirecc);
		u.click(xmlLblDirecc);
		u.highLight(xmlLblEspacio);
		u.click(xmlLblEspacio);
		u.highLight(xmlLblNomEspa);
		u.highLight(xmlLblElimId);
		u.click(xmlLblElimId);
		u.highLight(xmlLblCampo);
		u.click(xmlLblCampo);
		u.highLight(xmlLblValor);
		u.click(xmlLblValor);
		u.highLight(xmlLblUsuario);
		u.click(xmlLblUsuario);
		u.highLight(xmlLblNombre);
		u.click(xmlLblNombre);
		u.highLight(xmlLblPaterno);
		u.click(xmlLblPaterno);
		u.highLight(xmlLblMaterno);
		u.click(xmlLblMaterno);
		u.highLight(xmlLblPosicion);
		u.click(xmlLblPosicion);
		testCase.addScreenShotCurrentStep(u, "clickMuestraCamposPasoDiez");
		
		u.unhighLight(xmlLblId);
		u.unhighLight(xmlLbFecha);
		u.unhighLight(xmlLblNomTipoMov);
		u.unhighLight(xmlLblCvePlza);
		u.unhighLight(xmlLblNomPlza);
		u.unhighLight(xmlLblNomSPlza);
		u.unhighLight(xmlLblCrTda);
		u.unhighLight(xmlLblNomTda);
		u.unhighLight(xmlLblDirecc);
		u.unhighLight(xmlLblEspacio);
		u.unhighLight(xmlLblNomEspa);
		u.unhighLight(xmlLblElimId);
		u.unhighLight(xmlLblCampo);
		u.unhighLight(xmlLblValor);
		u.unhighLight(xmlLblUsuario);
		u.unhighLight(xmlLblNombre);
		u.unhighLight(xmlLblPaterno);
		u.unhighLight(xmlLblMaterno);
		u.unhighLight(xmlLblPosicion);
		testCase.addScreenShotCurrentStep(u, "clickMuestraCamposPasoDiez");
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
//			String mesNombre = getMesNombre(a[1]);
			By elementMes = By.xpath(String.format(mesStr, a[1]+" "+a[2]));
			System.out.println("Localizador del Mes: "+mesStr);
			u.mouseOverAndClick(elementMes);
			
			u.hardWait(2);
			
			//seleccion del dia
			By elementDia = By.xpath(String.format(diaStr,a[0]+" "+a[1]+" "+a[2]));
			m.explicitWaitToBeClickable(dia, Duration.ofSeconds(10));
			u.mouseOverAndClick(elementDia);
            u.hardWait(2);
			

		}




		/**
		 * Metodo auxiliar para obtener el nombre del mes a partir de su numero
		 * @param string
		 * @return nombre del mes
		 * @author Andrea Contreras
		 */
		public String getMesNombre(String mes) {
			String name = "";
			System.out.println("Dentro del metodo: "+mes);
			switch(mes)
			{
				case "01":
				{
					name = "January";
				}
				case "02":
				{
					
					name = "February";
					System.out.println("Entro por Febrero: "+name);
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

}
