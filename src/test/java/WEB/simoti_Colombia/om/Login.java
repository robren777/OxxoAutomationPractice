package WEB.simoti_Colombia.om;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Login {
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	
	By advanced_connPrivate = By.xpath("//*[@id='details-button']");
	By proceed_connPrivate = By.xpath("//*[@id='proceed-link']");
	
	By username = By.xpath("//*[@id='login:userName']");
	By password = By.xpath("//*[@id='login:userPass']");
	By inicia_sesion_button = By.xpath("//*[@id='login:loginButton']");
	
	By simoti_option = By.partialLinkText("SISTEMA DE MONITOREO DE TIENDAS (COLOMBIA)");
	
	By trans_elect = By.xpath("//*[@id=\"ddtopmenubar\"]/ul/li[13]/a");		
	By transacciones = By.xpath("//*[@id=\"ddsubmenu20220\"]/li[10]/a");	
	By titulo = By.xpath("//*[@id=\"formRepEnvDineroTransacciones:pGEncabezado\"]/tbody/tr/td/b/label");
	
	By tipo_trans = By.id("formRepEnvDineroTransacciones:cmbTipo");
	By cliente_trans = By.id("formRepEnvDineroTransacciones:cmbCliente");
	By IFE_trans = By.id("formRepEnvDineroTransacciones:txtIfe");
	By num_trans = By.id("formRepEnvDineroTransacciones:txtNumTransaction");
	By button_consultar_trans = By.id("formRepEnvDineroTransacciones:btnBuscar");
	
	By trans_electr_to_edi = By.xpath("//*[@id=\"ddtopmenubar\"]/ul/li[14]/a");
	By button_Prov_Edi = By.xpath("//*[@id=\"ddsubmenu20222\"]/li[15]/a");
	
	By submenu_transacciones_TDC = By.xpath("//*[@id=\"ddtopmenubar\"]/ul/li[14]/a");
	By transacciones_TDC = By.xpath("//*[@id=\"ddsubmenu20222\"]/li[12]/a");

	By busqueda = By.xpath("//*[@id=\"formEnvDineroTdc:tipoBusqueda\"]/tbody/tr/td[1]/div/div[2]/span");

	By tipo_transaccion = By.xpath("//*[@id=\"formEnvDineroTdc:cmbTipoTransaccDatosFolio_label\"]");

	By tipo_transacciones = By.xpath("//*[@id=\"formEnvDineroTdc:cmbTipoTransaccDatosFolio_items\"]/li");

	By folio_webmethods = By.xpath("//*[@id=\"formEnvDineroTdc:txtfoliowm\"]");

	By consultarBtn = By.xpath("//a[contains(text(), 'Consultar')]");

	By informacion = By.xpath("//*[@id=\"formEnvDineroTdc:gridRepTransaccTdc\"]");
	
	
	String xmlDatosTabla = "//fieldset[2]/div/span/div/div/div/table/tbody/tr/td[%s]";
	

	By numberRows = By.xpath("//fieldset[2]/div/span/div/div/div/table/tbody/tr/td");

	/**
	 * Constructor
	 ******************************************************************************************/
	
	/** Constructor de la clase
	 * @author David Valdez
	 * @param SeleniumUtil, TestCase
	 * @return N/A
	 */
	public Login(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);

		u.getDriver().manage().window().maximize();
		u.click(advanced_connPrivate);
		u.click(proceed_connPrivate);
	}
	
	/** Metodo para identificarse en la plataforma
	 * @author David Valdez
	 * @param String: usuario, String, contrasena
	 * @return N/A
	 */
	public void logginPlatform(String user, String pass){
		u.sendKeys(username, user);
		u.sendKeys(password, pass);
		
		u.highLight(username);
		u.highLight(password);
		
		testCase.addScreenShotCurrentStep("Imagen de login");		
		u.click(inicia_sesion_button);
		
	}
	
	/** Metodo para seleccionar la opci�n de simoti colombia
	 * @author David Valdez
	 * @param N/A
	 * @return N/A
	 */
	public void click_on_simoti() {
		u.highLight(simoti_option);
		testCase.addScreenShotCurrentStep("Simoti");	
		u.click(simoti_option);
	}
	
	public void transaccionesElectronicas(String trans) {
		
	}
	
	/** Metodo para seleccionar el apartado de transacciones electronicas
	 * @author David Valdez
	 * @param N/A
	 * @return N/A
	 */
	public void transaccionesElectronicas() {
		u.mouseOver(trans_elect);
		u.mouseOver(transacciones);
		u.highLight(transacciones);
		testCase.addScreenShotCurrentStep("reporte");			
		
		me.clickJs(transacciones);
		u.highLight(titulo);
		testCase.addScreenShotCurrentStep("reporte");	
	}
	
	/** Metodo para seleccionar la opci�n de consulta de proveedores EDI
	 * @author David Valdez
	 * @param N/A
	 * @return N/A
	 */
	public void consultarProveedoresEDI() {
		u.mouseOver(trans_electr_to_edi);
		u.mouseOver(button_Prov_Edi);
		u.highLight(button_Prov_Edi);
		testCase.addScreenShotCurrentStep("reporte");			
		
		me.clickJs(button_Prov_Edi);
		testCase.addScreenShotCurrentStep("reporte");	
	}
	
	/** Metodo para seleccionar el apartado de transacciones TDC
	 * @author David Valdez
	 * @param N/A
	 * @return N/A
	 */
	public void transaccionesTDC() {
		u.mouseOver(submenu_transacciones_TDC);
		u.mouseOver(transacciones_TDC);
		u.highLight(transacciones_TDC);
		testCase.addScreenShotCurrentStep("reporte");			
		
		me.clickJs(transacciones_TDC);
		testCase.addScreenShotCurrentStep("reporte");
	}
	
	/** Metodo para capturar filtros de busqueda
	 * @author David Valdez
	 * @param String: INE
	 * @return N/A
	 */
	public void capturaFiltros(String INE) {
		u.highLight(tipo_trans);
		u.highLight(cliente_trans);
		u.highLight(IFE_trans);
		u.sendKeys(IFE_trans, INE);
		
		u.click(button_consultar_trans);
		testCase.addScreenShotCurrentStep("reporte");	
	}

	/** Constructor de la clase
	 * @author Pablo Pozos
	 * @param u, TestCase
	 * @return N/A
	 */
	public void busquedaFolio() {
		u.highLight(busqueda);
		u.click(busqueda);
		u.unhighLight(busqueda);
		u.hardWait(2);
	}

	/** Constructor de la clase
	 * @author Pablo Pozos
	 * @param u, TestCase
	 * @return N/A
	 */
	public Boolean capturaDatosFolio(String folio,String tran) {
		Boolean correct = false;

		u.click(tipo_transaccion);

		List<WebElement> allOptions = u.getDriver().findElements(tipo_transacciones);

		for(WebElement option:allOptions){
			if(option.getText().trim().equals(tran)) {
				option.click();
				correct = true;
				break;
			}
		}

		u.highLight(tipo_transaccion);
		u.sendKeys(folio_webmethods,folio);
		u.highLight(folio_webmethods);
		u.highLight(consultarBtn);
		u.click(consultarBtn);
		u.hardWait(8);
		u.highLight(informacion);
		testCase.addScreenShotCurrentStep(u,"capturaDatosFolio");
		return correct;
	}

	
	

	/**
	 * Metodo para mostrar la informacion en la tabla de indicadores por totales
	 * @return 
	 * @author Bernardo Moya
	 */
	public Boolean validarDatosTabla() {
		u.scrollToElement(informacion);
		u.hardWait(10);				
		
		List<WebElement> rows = u.getDriver().findElements(numberRows);
		
		
		int count = rows.size();
		
		String elementos [] = new String[count];
		
		System.out.println(count);
		
		for(int i = 0; i<count; i++) {			
			//elementos[i] = u.getText(By.xpath(String.format(xmlDatosTabla, Integer.toString(i+1)))).trim();
			//System.out.println(elementos[i]);			
			u.highLight(By.xpath(String.format(xmlDatosTabla, Integer.toString(i+1))));
			testCase.addScreenShotCurrentStep(u,"validarDatosTabla");
		}								
		
		return true;
	}
	
}
