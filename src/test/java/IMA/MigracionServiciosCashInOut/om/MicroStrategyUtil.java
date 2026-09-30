package IMA.MigracionServiciosCashInOut.om;

import java.io.File;

import org.openqa.selenium.support.ui.Select;
import org.sikuli.script.Pattern;

import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;




public class MicroStrategyUtil {
	
	SeleniumUtil u;
	TestCase testCase;
	MicroStrategyElements elements = new MicroStrategyElements();
	private SikuliUtil sikuli = new SikuliUtil();	
	String pathImages = sikuli.PatternPath("IMA"+File.separator+"Microstrategy");
	Pattern plazaImagen = sikuli.PatternImage(pathImages + "plaza.png");

	
	public MicroStrategyUtil(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;

	}
	

	
	/**
	 * Da click en el apartado de Informacion Comercial QA
	 * @return true si encuentra el elemento y da click
	 * 
	 * @author alicia.ceja@gtim.mx
	 */
	public boolean clickInfoComercialQa () {
		try {
			u.waitWebElement(elements.infoComercialQa, 10, 4);
			u.highLight(elements.infoComercialQa);
			testCase.addScreenShotCurrentStep(null);
			u.click(elements.infoComercialQa);
			return true;
			
		}catch(Exception e) {
			System.out.println(" -> " + e);
			return false;


		}
	
	}
	

	
	
	/**
	 * Da click en el apartado de Informes Compartidos
	 *@return true si encuentra el elemento y da click
	 *
	 * @author alicia.ceja@gtim.mx
	 */
	public boolean clickInformeCompartido() {
		try {
			u.waitWebElement(elements.informesCompartidos, 10, 4);
			u.highLight(elements.informesCompartidos);
			testCase.addScreenShotCurrentStep(null);
			u.click(elements.informesCompartidos);
			return true;
			
		}catch(Exception e) {
			System.out.println(" -> " + e);
			return false;
		}
		
	}
	
	/**
	 * Da click en el apartado de Reportes a Liberar
	 * @return true si encuentra el elemento y da click
	 * 
	 * @author alicia.ceja@gtim.mx
	 */
	public boolean clickReportLiberar() {
		try {
			u.waitWebElement(elements.reportesLiberar, 10, 4);
			u.highLight(elements.reportesLiberar);
			testCase.addScreenShotCurrentStep(null);
			u.click(elements.reportesLiberar);
			return true;
			
		}catch(Exception e) {
			System.out.println(" -> " + e);
			return false;
		}
	
	}

	
	/**
	 * Da click en el apartado de Informacion Comercial *
	 * @return true si encuentra el elemento y da click
	 * 
	 * @author alicia.ceja@gtim.mx
	 */
	public boolean clickInfoComercial() {
		try {
			u.hardWait(4);
			u.scroll();
			u.highLight(elements.inforComercial);
			testCase.addScreenShotCurrentStep(null);
			u.click(elements.inforComercial);
			return true;
			
		}catch(Exception e) {
			System.out.println(" -> " + e);
			return false;
		}
		
	}
	
	
	/**
	 * Da click en el apartado de Por Tema 
	 * 
	 * @return true si encuentra el elemento y da click
	 * 
	 * @author alicia.ceja@gtim.mx
	 */
	public boolean clickPorTema() {
		try {
			u.waitWebElement(elements.porTema, 15, 4);
			u.highLight(elements.porTema);
			testCase.addScreenShotCurrentStep(null);
			u.click(elements.porTema);
			return true;
		}catch(Exception e) {
			System.out.println(" -> " + e);
			return false;
		}
		
	}
	
	
	/**
	 * Da click en el apartado de analisis de servicios
	 * 
	 *@return true si encuentra el elemento y da click
	 *
	 * @author alicia.ceja@gtim.mx
	 */
	
	public boolean analisisServicio() {
		try {
			u.waitWebElement(elements.analisisServicios, 10, 4);
			u.highLight(elements.analisisServicios);
			testCase.addScreenShotCurrentStep(null);
			u.click(elements.analisisServicios);
			return true;
			
		}catch(Exception e) {
			System.out.println(" -> " + e);
			return false;
		}
		
	}
	
	/**
	 * Da click en el apartado de Nacional (reporte)
	 * 
	 * @return true si encuentra el elemento y da click
	 * 
	 * @author alicia.ceja@gtim.mx
	 */
	
	public boolean clickNacional() {
		try {
			u.waitWebElement(elements.nacional, 10, 4);
			u.highLight(elements.nacional);
			testCase.addScreenShotCurrentStep(null);
			u.click(elements.nacional);
			return true;
			
		}catch(Exception e) {
			System.out.println(" -> " + e);
			return false;
		}
		
	}
	
	/**
	 * Da click en el apartado de Plaza (reporte)
	 * @return true si encuentra el elemento y da click
	 * 
	 * @author alicia.ceja@gtim.mx
	 */
	
	public boolean clickPlaza() {
		try {
			u.waitWebElement(elements.plaza, 10, 4);
			u.highLight(elements.plaza);
			testCase.addScreenShotCurrentStep(null);
			u.click(elements.plaza);
			return true;
			
		}catch(Exception e) {
			System.out.println(" -> " + e);
			return false;
		}
		
	}
	
	
	/**
	 * Da click en el reporte por Semana 
	 * @return true si encuentra el elemento y da click
	 * 
	 * @author alicia.ceja@gtim.mx
	 */
	public boolean clickReportSemana() {

		try {
			u.waitWebElement(elements.reportSemana, 10, 4);
			u.highLight(elements.reportSemana);
			testCase.addScreenShotCurrentStep(null);
			u.click(elements.reportSemana);
			return true;
			
			
		}catch(Exception e) {
			System.out.println(" -> " + e);
			return false;
		}
		
	}
	
	/**
	 * selecciona la semana del reporte a generar segun el tipo seleccionado
	 * Nacional o Plaza
	 * @param op - Tipo de reporte a seleccionar Plaza / Nacional
	 * @return true si encuentra el elemento y da click
	 * 
	 * @author alicia.ceja@gtim.mx
	 */
	public boolean selectSemana (String op) {
		
		try {
			
			switch (op) {
			
				case "NACIONAL":
					u.scrollToElement(elements.semana45);
					u.click(elements.semana45);
					u.click(elements.agregaSemana);
					testCase.addScreenShotCurrentStep(null);
					break;
				case "PLAZA":
					u.scrollToElement(elements.semana45Plaza);
					u.click(elements.semana45Plaza);
					u.click(elements.agregaSemanaPlaza);
					testCase.addScreenShotCurrentStep(null);
					break;
					
			}
		
				return true;
			
		}catch(Exception e) {
			System.out.println(" -> " + e);
			return false;
		}
		
	}
	
	/**
	 * Selecciona todas las plazas para generar el reporte
	 * @return true si encuentra el elemento y da click
	 * 
	 * @author alicia.ceja@gtim.mx
	 */
	public void selectSemanaPlaza() {
		try {
			u.scrollToElement(elements.semana45);
			u.click(elements.semana45);
			u.click(elements.agregaSemana);
			testCase.addScreenShotCurrentStep(null);

			
		}catch(Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Error al seleccionar semana");
		}
	}
	
	/**
	 * Selecciona la plaza del reporte a generar
	 * segun la opcion enviada Nacional o Plaza
	 *@return true si encuentra el elemento y da click
	 * @param op - Selecciona la tienda Nacional/ Plaza
	 * 
	 * @author alicia.ceja@gtim.mx

	 */
	public boolean selectTienda(String op) {
		try {
			
			switch(op) {
			case "NACIONAL":
				u.scrollToElement(elements.disponible);
				u.scrollToElement(elements.tiendasTodas);
				u.click(elements.tiendasTodas);
				u.click(elements.agregaTienda);
				testCase.addScreenShotCurrentStep(null);
				break;
			case "PLAZA":
				u.scrollToElement(elements.disponiblePlaza);
				u.scrollToElement(elements.tiendasTodasPlaza);
				u.click(elements.tiendasTodasPlaza);
				u.click(elements.btnAgregaTiendaPlaza);
				testCase.addScreenShotCurrentStep(null);
				break;
			
			}
		
				return true;
			
		}catch(Exception e) {
			System.out.println(" -> " + e);
			return false;
		}
	
	}
	
	/**
	 * Ejecuta el informe Nacional o Plaza
	 * segun la opcion enviada
	 * 
	 * @param op 
	 * @return true si encuentra el elemento y da click
	 * 
	 * 
	 * @author alicia.ceja@gtim.mx
	 */
	public boolean ejecutaInforme(String op) {
		
		try {
			
			switch (op) {
			
			case "NACIONAL":
				u.highLight(elements.btnEjecutaInforme);
				testCase.addScreenShotCurrentStep(null);
				u.click(elements.btnEjecutaInforme);
				break;
			case "PLAZA":
				u.highLight(elements.btnEjecutaInformePlaza);
				testCase.addScreenShotCurrentStep(null);
				u.click(elements.btnEjecutaInformePlaza);;
				break;
				
		}
		
			return true;
			
		}catch(Exception e) {
			System.out.println(" -> " + e);
			return false;
		}
		
	}
	
	/**
	 * LLena los filtros del reporte ya sea Nacional o Plaza
	 * @param op
	 * @param selectElement
	 * @param report
	 * 
	 * @return true si encuentra el elemento y selecciona la opcion 
	 * @author alicia.ceja@gtim.mx
	 */
	public boolean selectOp(String op, String selectElement, String report) {
		
		try {
			u.waitWebElement(elements.selectSubCat, 30, 4);
			switch(selectElement) {
			case "SubCategoria":
				
				if(report.equals("PLAZA")) {
					Select dropdownSubCat=  new Select (u.getDriver().findElement((elements.selectSubCatPlaza)));
					dropdownSubCat.selectByVisibleText(op);
				}else {
					Select dropdownSubCat=  new Select (u.getDriver().findElement((elements.selectSubCat)));
					dropdownSubCat.selectByVisibleText(op);
				}
			
		
				break;
			case "Segmento":
				if(report.equals("PLAZA")) {
					Select dropdownSegmento=  new Select (u.getDriver().findElement((elements.selectSegmentPlaza)));
					dropdownSegmento.selectByVisibleText(op);
				}else {
					Select dropdownSegmento=  new Select (u.getDriver().findElement((elements.selectSegmento)));
					dropdownSegmento.selectByVisibleText(op);
				}
				
				break;
				
			case "SubSegmento":
				
				if(report.equals("PLAZA")) {
					Select dropdownSubSegmento=  new Select (u.getDriver().findElement((elements.selectSubSegPlaza)));
					dropdownSubSegmento.selectByVisibleText(op);
					u.hardWait(2);
					u.scroll();
				}else {
					Select dropdownSubSegmento=  new Select (u.getDriver().findElement((elements.selectSubSegmento)));
					dropdownSubSegmento.selectByVisibleText(op);
					u.hardWait(2);
					u.scroll();
				}
			
				break;
		
			
			}
			return true;
			
		}catch(Exception e) {
			System.out.println(" -> " + e);
			return false;
		}
		
	}
	
	/**
	 * Selecciona una organizacion para generar el reporte Plaza
	 * @param plaza
	 * 
	 * @author alicia.ceja@gtim.mx
	 */
	public void selectOrganizacion(String plaza) {
		
		try {
			u.scrollToElement(elements.plazaSeleccionada);
			u.click(elements.plazaSeleccionada);
			sikuli.screen.wait(plazaImagen,10);
			
			sikuli.screen.click(plazaImagen);
			u.hardWait(2);
			u.sendKeys(elements.inputPlaza, plaza);
			u.hardWait(2);
			u.click(elements.btnBuscaPlaza);
			u.scrollToElement(elements.tiendaPlaza);
			u.click(elements.tiendaPlaza);

			u.hardWait(1);
			u.click(elements.agregaPlaza);
		
		}catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Error al seleccionar una organizacion");
		}
		
	}
	
	

	

}
