package WEB.sarv3.om;

import org.openqa.selenium.By;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class BusquedaSiniestroSARv3 {
	
	TestCase testCase;
	SeleniumUtil u;
	ReportesSARv3 r;
	MetodosExtra me;
	
	/*
	 * Localizadores Busqueda de Siniestros
	 ********************************************************************************/
	
	By xmlEstatus = By.id("sourceEstatus");
	By xmlNegocio = By.id("sourceNegocio");
	By xmlCalInicioBtn = By.xpath("//*[@id=\"cajaPrincipal\"]/div[2]/div[1]/table/tbody/tr/td[2]/button;");
	By xmlCalFinBtn = By.xpath("//*[@id=\"cajaPrincipal\"]/div[2]/div[1]/table/tbody/tr/td[3]/button");
	By xmlAnio = By.xpath("//*[@id=\"ui-datepicker-div\"]/div/div/select[1]");
	By xmlMes = By.xpath("//*[@id=\"ui-datepicker-div\"]/div/div/select[2]");
	String xmlDia = "//table/tbody/tr/td/a[contains(text(), '%s')]";
	By xmlBuscarBtn = By.xpath("//div/a[contains(text(), 'Buscar')]");
	
	By xmlTable = By.xpath("//*[@id=\"gview_resultadoDeBusqueda\"]/div[3]");
	By xmlTableTr = By.xpath("//table[@id='resultadoDeBusqueda']/tbody/child::tr");
	
	
	/*
	 * Constructor
	 ********************************************************************************/
	
	public BusquedaSiniestroSARv3(SeleniumUtil u, TestCase testCase) {
		super();
		this.testCase = testCase;
		this.u = u;
		this.r = new ReportesSARv3(u, testCase);
		this.me = new MetodosExtra(u, testCase);
	}
	
	/*
	 * Metodos Busqueda de Siniestros
	 ********************************************************************************/
	
	/**
	 * Metodo para ingresar la fecha a buscar
	 * @param fecha
	 * @param calendario
	 * @return correct: true si selecciona correctamente la fecha o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean selectDate(String fecha, By calendario) {
		
		Boolean correct = false;
		
		u.highLight(calendario);
		testCase.addScreenShotCurrentStep(u, "clickCalendario");
		u.unhighLight(calendario);
		u.click(calendario);
		
		String [] f = fecha.split("\\*");
		//System.out.print(f + "\n");
		Boolean anio = r.selectOption(f[2], xmlAnio);
		
		Boolean mes = r.selectOption(f[1], xmlMes);
		
		String dia = String.format(xmlDia, f[0]);
		u.highLight(By.xpath(dia));
		testCase.addScreenShotCurrentStep(u, "clickDia");
		u.unhighLight(By.xpath(dia));
		u.click(By.xpath(dia));
		
		if(mes == true && anio == true) {
			correct = true;
		}
		
		return correct;
		
	}
	
	/**
	 * Metodo para ingresar los datos en los campos de Busqueda de siniestros
	 * @param estatus
	 * @param negocio
	 * @param fecha1
	 * @param fecha2
	 * @return Retorna true si se ingresaron correctamente los datos o false en caso contrario
	 * @author Maria Due�as
	 * @throws FindFailed 
	 */
	public Boolean camposBusquedaSiniestros(String estatus, String negocio, String fecha1, String fecha2) throws FindFailed {
		Boolean correct = false;
		
		try {
			me.clickElementSikuli("SARv3", "Estatus");
			String imagen = "";
			
			switch(estatus.toUpperCase()) {
			case "SELECCIONE":
					imagen = "Seleccione";
				break;
			case "INCOMPLETO":
					imagen = "Incompleto";
				break;
			default: imagen = "Seleccione";
			}
			
			me.clickElementSikuli("SARv3", imagen);
			
			me.clickElementSikuli("SARv3", "Negocio");
			
			switch(negocio.toUpperCase()) {
			case "OXXO":
					imagen = "Oxxo";
				break;
			default: imagen = "Oxxo";
			
			}
			
			me.clickElementSikuli("SARv3", imagen);
			
			me.clickElementSikuli("SARv3", "CalendarioBtn");
			me.clickElementSikuli("SARv3", "Mes");
			me.clickElementSikuli("SARv3", "Enero");
			me.clickElementSikuli("SARv3", "1");
			
			correct = true;
			
		}catch(Exception e) {
			correct = false;
		}
		
		
		return correct;
	}
	
	/**
	 * Metodo para dar click el boton buscar
	 * @author Maria Due�as
	 * @throws FindFailed 
	 */
	public void clickBuscarBtn() throws FindFailed {
		me.clickElementSikuli("SARv3", "Buscar");
	}
	
	/**
	 * Metodo para validar que se muestren resultados en el grid
	 * @return Retorna true si encontro resultados o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean resultadoBusqueda() {
		Boolean correct = r.resultadosBusqueda(xmlTableTr, xmlTable);
		return correct;
	}
	
	/**
	 * Metodo para dar click en Reporte estadistico simple 
	 * @author Maria Due�as
	 * @throws FindFailed 
	 */
	public Boolean clickReporteEstadisticoSimpleBtn() throws FindFailed {
		Boolean correct = me.clickElementSikuli("SARv3", "ReporteEstadisticoSimple");
		return correct;
	}
	
	/**
	 * Metodo para dar click en Reporte de seguimiento
	 * @author Maria Due�as
	 * @throws FindFailed 
	 */
	public Boolean clickReporteSeguimientoBtn() throws FindFailed {
		Boolean correct = me.clickElementSikuli("SARv3", "ReporteSeguimiento");
		return correct;
	}
	
	/**
	 * Metodo para dar click en Reporte seguimiento segmentado
	 * @author Maria Due�as
	 * @throws FindFailed 
	 */
	public Boolean clickReporteSegSegmentadoBtn() throws FindFailed {
		Boolean correct = me.clickElementSikuli("SARv3", "ReporteSegSegmentado");
		return correct;
	}
	
	/**
	 * Metodo para dar click en Reporte de productos
	 * @author Maria Due�as
	 * @throws FindFailed 
	 */
	public Boolean clickReporteProductosBtn() throws FindFailed {
		Boolean correct = me.clickElementSikuli("SARv3", "ReporteProductos");
		return correct;
	}

	/**
	 * Metodo para esperar que se deje de ver el mensaje de Espere por favor
	 * @param Folder
	 * @param image
	 * @throws FindFailed
	 * @author Maria Due�as
	 */
	public void esperePorFavor(String Folder, String image) throws FindFailed {
		
		   //u.waitForLoadPage();
		   Boolean exist = false;
		   Screen s = new Screen();
		   
	       SikuliUtil sikuli = new SikuliUtil(); //Crea un elemento del tipo Sikuli
	       String s1 = sikuli.PatternPath(Folder); //Si la carpeta donde se indica que esta la imagen no ha sido creada, la crea
	       Pattern p1 = sikuli.PatternImage(s1 + image + ".png"); // Crea la imagen del tipo Patter
	       
	       
	       do {
	    	 //Pregunta si existe la imagen en pantalla
		       if (sikuli.screen.exists(p1) != null) {
		    	   //System.out.print("Entrando al if \n");
		    	   exist = true;
		       }else {
		    	   exist = false;
		       } 
	       }while(exist);
	       
	       
	       testCase.addScreenShotCurrentStep("clickElementSikuli");
	      
	 }
}
