package WEB.sarv3.om;

import org.openqa.selenium.By;
import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class AltaSiniestrosSARv3 {
	
	TestCase testCase;
	SeleniumUtil u;
	ReportesSARv3 r;
	MetodosExtra me;
	
	/*
	 * Localizadores Datos Generales
	 ********************************************************************************/
	
	By xmlNegocio = By.id("sourceNegocio");
	By xmlPlaza = By.id("idPlaza");
	By xmlEstablecimiento = By.id("listaIdTienda");
	By xmlSiniestro = By.id("idTipoSiniestro");
	By xmlSubtipo = By.id("idTipoSubSiniestro");
	By xmlRelato = By.id("quePaso");
	By xmlDelincuentes = By.id("noDelincuentesCecon");
	By xmlGrabar = By.name("grabar");
	By xmlEnvNotificacion = By.id("envNotificacion");
	
	
	
	/*
	 * Constructor
	 ********************************************************************************/
	
	public AltaSiniestrosSARv3(SeleniumUtil u, TestCase testCase) {
		super();
		this.testCase = testCase;
		this.u = u;
		this.r = new ReportesSARv3(u, testCase);
		this.me = new MetodosExtra(u, testCase);
	}
	
	/*
	 * Metodos Datos Generales
	 ********************************************************************************/

	/**
	 * Metodo para ingresar la informacion de campos en ventana Datos Generales para alta de siniestro
	 * @param negocio
	 * @param plaza
	 * @param establecimiento
	 * @param siniestro
	 * @param subtipo
	 * @param relato
	 * @param noDelinc
	 * @return Retorna true si se ingresaron correctamente los datos o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean camposDatosGenerales(String negocio, String plaza, String establecimiento, 
			String siniestro, String subtipo, String relato, String noDelinc) {
		
		Boolean correct = false;
		
		try {
			
			Boolean n = r.selectOption(negocio, xmlNegocio);
			Boolean p = r.selectOption(plaza, xmlPlaza);
			Boolean e = r.selectOption(establecimiento, xmlEstablecimiento);
			Boolean s = r.selectOption(siniestro, xmlSiniestro);
			Boolean sb = r.selectOption(subtipo, xmlSubtipo);
			
			u.sendKeys(xmlRelato, relato);
			u.highLight(xmlRelato);
			testCase.addScreenShotCurrentStep(u, "QuePaso");
			u.unhighLight(xmlRelato);
			
			u.sendKeys(xmlDelincuentes, noDelinc);
			u.highLight(xmlDelincuentes);
			testCase.addScreenShotCurrentStep(u, "noDelincuentes");
			u.unhighLight(xmlDelincuentes);
			
			if(n && p && e && s && sb) correct = true;
			
		}catch(Exception e) {
			testCase.addScreenShotCurrentStep(u, "NoSeIngresoInformacion");
		}
		
		return correct;
	}
	
	/**
	 * Metodo para dar click en el bot�n guardar de la ventana Datos generales de Alta de siniestros
	 * @author Maria Due�as
	 */
	public void clickGrabar() {
		me.scrollToElement(xmlGrabar);
		u.highLight(xmlGrabar);
		testCase.addScreenShotCurrentStep(u, "Grabar");
		u.click(xmlGrabar);
		testCase.addScreenShotCurrentStep(u, "clickGrabar");
	}
	
	public Boolean validarMensaje() {
		Boolean correct = false;
		
		
		
		return correct;
	}
	
	/**
	 * Metodo para dar click en el bot�n Enviar Notificacion de la ventana Datos generales 
	 * de Alta de siniestros
	 * @author Maria Due�as
	 */
	public void clickEnvNotificacion() {
		me.scrollToElement(xmlEnvNotificacion);
		u.highLight(xmlEnvNotificacion);
		testCase.addScreenShotCurrentStep(u, "EnvNotificacion");
		u.click(xmlEnvNotificacion);
		testCase.addScreenShotCurrentStep(u, "clickEnvNotificacion");
	}
	
	
}
