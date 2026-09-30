package IMA.MVP2_RST.om;

import java.io.File;

import org.sikuli.script.Pattern;

import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;




public class RstUtils {
	
	SeleniumUtil u;
	TestCase testCase;
	RstElements elements = new RstElements();
	private SikuliUtil sikuli = new SikuliUtil();	
	String pathImages = sikuli.PatternPath("IMA"+File.separator+"Microstrategy");
	Pattern textBox = sikuli.PatternImage(pathImages + "textBox.png");
	Pattern singIn = sikuli.PatternImage(pathImages + "singIn.png");
	Pattern plazaImagen = sikuli.PatternImage(pathImages + "plaza.png");

	
	public RstUtils(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;

	}
	
	/**
	 * Inicia sesion en el login de domino de MicroStrategy
	 * @param user
	 * @param pass
	 * @author Herbé Cauich
	 * @date 07/Febrero/2025
	 */
	
	public void loginDominio(String user, String pass) {
		try {
			sikuli.screen.wait(textBox,10);
			
			sikuli.screen.click(textBox);
			sikuli.screen.type(pass);
		
			sikuli.screen.click(textBox);
			sikuli.screen.type(user);
			
			sikuli.screen.click(singIn);

			
			
			
		
			
		}catch(Exception ex) {
			System.out.println(" -> " + ex);
            throw new AssertionError("Error en el login de dominio");
		}
	}
	
	/**
	 * Da click en el apartado de Informacion Comercial QA
	 * @return true si encuentra el elemento y da click
	 * @author Herbé Cauich
	 * @date 07/Febrero/2025
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
	 * @author Herbé Cauich
	 * @date 07/Febrero/2025
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
	 * @author Herbé Cauich
	 * @date 07/Febrero/2025
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
	 * @author Herbé Cauich
	 * @date 07/Febrero/2025
	 */
	public boolean clickInfoComercial() {
		try {
			u.hardWait(4);
			//u.scroll();
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
	 * @return true si encuentra el elemento y da click
	 * @author Herbé Cauich
	 * @date 07/Febrero/2025
	 */
	public boolean clickPorTema() {
		try {
			u.waitWebElement(elements.porTema, 10, 4);
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
	 * Da click en el apartado de Analisis de servicios 
	 * @return true si encuentra el elemento y da click
	 * @author Herbé Cauich
	 * @date 07/Febrero/2025
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
	 * Da click en el apartado de Telefonía y Servicios *
	 * @return true si encuentra el elemento y da click
	 * @author Herbé Cauich
	 * @date 07/Febrero/2025
	 */
	public boolean clickTelefoniaYServicios() {
		try {
			u.hardWait(4);
			u.scroll();
			u.highLight(elements.telefoniaServicios);
			testCase.addScreenShotCurrentStep(null);
			u.click(elements.telefoniaServicios);
			return true;
			
		}catch(Exception e) {
			System.out.println(" -> " + e);
			return false;
		}
		
	}
	
	
	
	/**
	 * Da click en el apartado de Resultados Servicios *
	 * @return true si encuentra el elemento y da click
	 * @author Herbé Cauich
	 * @date 07/Febrero/2025
	 */
	public boolean clickResultadosServicios() {
		try {
			u.hardWait(4);
			u.highLight(elements.resultadosServicios);
			testCase.addScreenShotCurrentStep(null);
			u.click(elements.resultadosServicios);
			return true;
			
		}catch(Exception e) {
			System.out.println(" -> " + e);
			return false;
		}
		
	}
	
	/**
	 * Da click en el apartado de Nacional *
	 * @return true si encuentra el elemento y da click
	 * @author Herbé Cauich
	 * @date 07/Febrero/2025
	 */
	public boolean clickNacional() {
		try {
			u.hardWait(4);
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
	 * Da click en el apartado de Nacional *
	 * @return true si encuentra el elemento y da click
	 * @author Herbé Cauich
	 * @date 07/Febrero/2025
	 */
	public boolean clickAnalisisSemanal() {
		try {
			u.hardWait(4);
			u.highLight(elements.analisisSemana);
			testCase.addScreenShotCurrentStep(null);
			u.click(elements.analisisSemana);
			return true;
			
		}catch(Exception e) {
			System.out.println(" -> " + e);
			return false;
		}
		
	}
	

		
	
	//===============================================================
	
	/**
	 * Realizar busqueda de una semana y agregar la semana al filtro de busqueda
	 * @return true si encuentra el elemento de busqueda 
	 * @param semana
	 * @author Herbé Cauich
	 * @date 07/Febrero/2025
	 */
	public void seleccionarYAgregarSemana(String semana) {
		
		try {
			u.hardWait(2);
			u.highLight(elements.inputBuscarSemana);
			u.sendKeys(elements.inputBuscarSemana, semana);
			u.hardWait(2);
			u.highLight(elements.btnBuscarSemana);
			u.click(elements.btnBuscarSemana);
			u.hardWait(4);
			u.click(elements.seleccionarSemana);
			u.hardWait(3);
			u.click(elements.btnAgregarSemana);
		
		}catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Error al seleccionar una semana");
		}
		
	}


	
	//===============================================================
	
	
	
	/**
	 * Seleccionar y agregar Semana
	 * @return true si encuentra el elemento y da click para seleccionar y agregarlo
	 * @author Herbé Cauich
	 * @date 07/Febrero/2025
	 */
	public boolean seleccionarYAgregarSemana2() {
		try {
			u.hardWait(4);
			u.highLight(elements.seleccionarSemana);
			testCase.addScreenShotCurrentStep(null);
			u.scrollToElement(elements.seleccionarSemana);
			u.click(elements.seleccionarSemana);
			u.click(elements.btnAgregarSemana);
			return true;
			
		}catch(Exception e) {
			System.out.println(" -> " + e);
			return false;
		}
		
	}
	
	/**
	 * Seleccionar y agregar tienda
	 * @return true si encuentra el elemento y da click para seleccionar y agregarlo
	 * @author Herbé Cauich
	 * @date 07/Febrero/2025
	 */
	public boolean seleccionarYAgregarTienda() {
		try {
			u.hardWait(4);
			u.highLight(elements.seleccionarTienda);
			testCase.addScreenShotCurrentStep(null);
			u.click(elements.seleccionarTienda);
			u.click(elements.btnAgregarTienda);
			return true;
			
		}catch(Exception e) {
			System.out.println(" -> " + e);
			return false;
		}
		
	}
	
	
	/**
	 * Da click en el boton Ejecutar informe *
	 * @return true si encuentra el elemento y da click
	 * @author Herbé Cauich
	 * @date 07/Febrero/2025
	 */
	public boolean clickEjecutarInforme() {
		try {
			u.hardWait(4);
			u.highLight(elements.btnEjecutarInforme);
			testCase.addScreenShotCurrentStep(null);
			u.click(elements.btnEjecutarInforme);
			return true;
			
		}catch(Exception e) {
			System.out.println(" -> " + e);
			
			return false;
		}
		
	}	
	
	/**
	 * Validar que la tabla del reporte este presente en la pantalla
	 * @return true si encuentra el elemento 
	 * @author Herbé Cauich
	 * @date 07/Febrero/2025
	 */
	
	public boolean validarGeneracionReporte() {
		try {
			u.hardWait(4);
			u.highLight(elements.lblTituloReporte);
			u.hardWait(4);
			testCase.addScreenShotCurrentStep(null);
			u.isDisplayed(elements.tblReport);
			u.highLight(elements.tblReport);
			
			return true;
			
		}catch(Exception e) {
			System.out.println(" -> " + e);
			return false;
		}
		
	}
	
	
	
	/**
	 * Da click en el apartado de Retiros *
	 * @return true si encuentra el elemento y da click
	 * @author Herbé Cauich
	 * @date 07/Febrero/2025
	 */
	public boolean clickRetiros() {
		try {
			u.hardWait(4);
			u.highLight(elements.retiros);
			testCase.addScreenShotCurrentStep(null);
			u.click(elements.retiros);
			return true;
			
		}catch(Exception e) {
			System.out.println(" -> " + e);
			return false;
		}
		
	}
	
	/**
	 * Da click en el apartado de Retiros *
	 * @return true si encuentra el elemento y da click
	 * @author Herbé Cauich
	 * @date 07/Febrero/2025
	 */
	public boolean clickResultadosComerciales() {
		try {
			u.hardWait(4);
			u.highLight(elements.resultadosComerciales);
			testCase.addScreenShotCurrentStep(null);
			u.click(elements.resultadosComerciales);
			return true;
			
		}catch(Exception e) {
			System.out.println(" -> " + e);
			return false;
		}
		
	}
	

}