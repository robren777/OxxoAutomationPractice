package WEB.sarv3.om;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class CentroControlSARv3 {
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	MenuPrincipalSAR mp;
	ReportesSARv3 r;
	
	/*
	 * Localizadores Catalogo Autoridades
	 *******************************************************************************************/
	
	By xmlNegocio = By.id("descAut");
	By xmlPlaza = By.id("cmb_descPlaza");
	By xmlTable = By.xpath("//*[@id='gview_resultadoDeBusqueda']/div[3]");
	By xmlTableTr = By.xpath("//table[@id='resultadoDeBusqueda']/tbody/child::tr");
	By xmlBuscarBtn = By.xpath("//*[@id=\"forma\"]/div[5]/div/div[2]/a");
	
	/*
	 * Localizadores Horarios a Capturar
	 *******************************************************************************************/
	
	By xmlNegocioHC = By.id("sourceNegocios");
	By xmlMensaje = By.xpath("//*[@id=\"msjError\"]");
	By xmlEsperar = By.xpath("//div[contains(text(), 'Espere por favor...')]");
	
	/*
	 * Localizadores Consulta de Asistencias
	 *******************************************************************************************/
	
	By xmlBuscarBtnCA = By.xpath("//*[@id=\"hcapturados\"]/div[13]/table/tbody/tr/td[3]/a");
	
	/*
	 * Constructor
	 *******************************************************************************************/
	
	public CentroControlSARv3(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		me = new MetodosExtra(u, testCase);
		mp = new MenuPrincipalSAR(u, testCase);
		r = new ReportesSARv3(u, testCase);
		
	}
	
	/*
	 * Metodos Catalogo Autoridades
	 *******************************************************************************************/
	
	/**
	 * Metodo para ingresar los campos en Catalogo de Autoridades
	 * @param negocio
	 * @param Plaza
	 * @return correct: true si se ingresaron correctamente los datos o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean camposCatalogoAutoridades(String negocio, String Plaza) {
		Boolean correct = false;
		u.waitForLoadPage();
		try {
			Boolean n = r.selectOption(negocio, xmlNegocio);
			Boolean p = r.selectOption(Plaza, xmlPlaza);
			if(n == true && p == true) {
				correct = true;
			}
		}catch(Exception e) {
			correct = false;
		}
		
		return correct;
	}
	
	/**
	 * Metodo para dar click en el boton Buscar
	 * @author Maria Due�as
	 */
	public void clickBuscarBtn() {
		u.highLight(xmlBuscarBtn);
		testCase.addScreenShotCurrentStep(u, "clickBuscarBtn");
		u.unhighLight(xmlBuscarBtn);
		u.click(xmlBuscarBtn);
	}
	/**
	 * Metodo para validar que se muestren resultados de la busqueda
	 * @return exist: true si muestra resultados o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean resultadosBusqueda() {
		
		Boolean exist = r.resultadosBusqueda(xmlTableTr, xmlTable);
		
		return exist;
	}
	
	/*
	 * Localizadores Horarios a Capturar
	 *******************************************************************************************/
	
	/**
	 * Metodo para ingresar los campos en Horarios Capturar
	 * @param negocio
	 * @return correct: true si se ingresaron correctamente los datos o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean camposHorariosCapturar(String negocio) {
		Boolean correct = false;
		u.waitForLoadPage();
		try {
			u.highLight(xmlNegocioHC);
			testCase.addScreenShotCurrentStep(u, "selectOption");
			u.unhighLight(xmlNegocioHC);
			Boolean n = r.selectOption(negocio, xmlNegocioHC);
			if(n) correct = true;
		}catch(Exception e) {
			correct = false;
		}
		
		return correct;
	}
	
	/**
	 * Metodo para esperera que desaparezca el mensaje de carga
	 * @author Maria Due�as
	 */
	public void esperePorFavor() {
		//me.explicitWaitInvisibilityOfElement(xmlEsperar, 60);
		Boolean element = false;
		element = new WebDriverWait(u.getDriver(), Duration.ofSeconds(60)).until(ExpectedConditions.invisibilityOfElementLocated(xmlEsperar));
	}
	
	/**
	 * Metodo para validar que se muestre mensaje de No existen registros por mostrar
	 * @return exist: true si se muestra el mensaje, false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean mensajeSinRegistros() {
		Boolean exist = false;
		if(u.getDriver().findElement(xmlMensaje).isDisplayed()) {
			me.scrollToElement(xmlMensaje);
			String mensaje = u.getText(xmlMensaje);
			if (mensaje.equals("No existen registros por mostrar")) {
				exist = true;
				u.highLight(xmlMensaje);
				testCase.addScreenShotCurrentStep(u, "mensajeSinRegistros");
				u.unhighLight(xmlMensaje);
			}
		}
		
		return exist;
	}
	
	/*
	 * Localizadores Consulta de Asistencias
	 *******************************************************************************************/
	
	/**
	 * Metodo para dar click en el boton Buscar
	 * @author Maria Due�as
	 */
	public void clickBuscarBtnCA() {
		me.scrollToElement(xmlBuscarBtnCA);
		u.highLight(xmlBuscarBtnCA);
		testCase.addScreenShotCurrentStep(u, "clickBuscarBtnCA");
		u.unhighLight(xmlBuscarBtnCA);
		u.click(xmlBuscarBtnCA);
	}
}
