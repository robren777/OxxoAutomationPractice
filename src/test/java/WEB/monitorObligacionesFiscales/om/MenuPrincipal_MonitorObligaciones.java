package WEB.monitorObligacionesFiscales.om;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class MenuPrincipal_MonitorObligaciones{

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	
	
	/*
	 * Localizadores Menu Monitor de Obligaciones Fiscales
	 ********************************************************************************************/
	
	

	By OpcionCatalogos = By.xpath("//div/ul/li/a[contains(text(), 'Cat�logos')]");
	
	//Opciones menu
	
	By OpcionDespachos = By.xpath("//a[contains(text(), 'Despachos')]");
	By OpcionParametros = By.xpath("//a[contains(text(), 'Par�metros')]");
	By OpcionDeducciones = By.xpath("//a[contains(text(), 'Deducciones')]");

	//Fin opciones menu
	
	By OpcionTablas = By.xpath("//*[text()='Tablas']");
	
	//Opciones menu
	By xmlTablasTarifas = By.xpath("//*[text()='Tarifas']");
	//Fin opciones menu
	
	//Dentro opcion Tablas Tarifas
	By xmlTarifasTipoTabla = By.xpath("//*[@id='form1:tipoTarifaId']");
	//Fin dentro opcion tablas tarifas

	By OpcionLideres = By.xpath("//*[text()='L�deres']");

	//Opciones menu
	By xmlLideresBusqueda = By.xpath("//*[text()='B�squeda de L�deres']");
	//Fin opciones menu

	By OpcionContabilidad = By.xpath("//*[text()='Contabilidad']");

	//Opciones menu
	By xmlContabilidadPrincipal = By.xpath("//*[text()='Principal']");
	By xmlContabilidadCalculoImpuestos = By.xpath("//*[text()='C�lculo de Impuestos']");
	By xmlContabilidadEnvioSolicitud = By.xpath("//*[text()='Env�o de Solicitud']");
	//Fin opciones menu
	
	
	/*
	 * Constructor
	 ********************************************************************************************/
	public MenuPrincipal_MonitorObligaciones(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}
	
	/*
	 * Metodos Menu Integridad de datos
	 ********************************************************************************************/
	
	/**
	 * Metodo para dar click en opcion catalogos
	 * @author Herbe Cauich
	 */
	public void clickCatalogos() {
		u.scrollToElement(OpcionCatalogos);
		u.highLight(OpcionCatalogos);
		testCase.addScreenShotCurrentStep(u, "Catalogos");
		u.unhighLight(OpcionCatalogos);
		u.mouseOver(OpcionCatalogos);
		testCase.addScreenShotCurrentStep(u, "clickOpcionCatalogos");
	}
	
	/**
	 * Metodo para dar click en opcion despachos
	 * @author Herbe Cauich
	 */
	public void clickOpcionDespachos() {
		u.highLight(OpcionDespachos);
		testCase.addScreenShotCurrentStep(u, "Despachos");
		u.unhighLight(OpcionDespachos);
		u.click(OpcionDespachos);
		testCase.addScreenShotCurrentStep(u, "ClickOpcionDespachos");
	}
	
	/**
	 * Metodo para dar click en opcion parametros
	 * @author Herbe Cauich
	 */
	public void clickOpcionParametros() {
		u.highLight(OpcionParametros);
		testCase.addScreenShotCurrentStep(u, "parametros");
		u.unhighLight(OpcionParametros);
		u.click(OpcionParametros);
		testCase.addScreenShotCurrentStep(u, "clickOpcionParametros");
	}
	
	/**
	 * Metodo para dar click en opcion deducciones
	 * @author Herbe Cauich
	 */
	public void clickOpcionDeducciones() {
		u.highLight(OpcionDeducciones);
		testCase.addScreenShotCurrentStep(u, "Deducciones");
		u.unhighLight(OpcionDeducciones);
		u.click(OpcionDeducciones);
		testCase.addScreenShotCurrentStep(u, "clickOpcionDeducciones");
	}

	/**
	 * Metodo para dar click en opcion tablas
	 * @author Jorge Ruiz
	 */
	public void clickTablas(String opc) 
	{
		u.click(OpcionTablas);
		u.hardWait(1);
		List<WebElement> options = u.getDriver().findElements(xmlTarifasTipoTabla);
		for(WebElement opt:options)
		{
			String text = opt.getText();
			if(text.equals(xmlTarifasTipoTabla))
				opt.click();
		}
	}
	
	/**
	 * Metodo para dar click en opcion Tarifas de Tablas
	 * @author Jorge Ruiz
	 */
	public void clickOpcionTarifas() 
	{	
		u.highLight(xmlTablasTarifas);
		testCase.addScreenShotCurrentStep(u, "Tarifas");
		u.click(xmlTablasTarifas);
	}
	
	
	/**
	 * Metodo para dar click en opcion lideres
	 * @author Jorge Ruiz
	 */
	public void clickLideres() 
	{
		u.highLight(OpcionLideres);
		testCase.addScreenShotCurrentStep(u, "Tablas");
		u.unhighLight(OpcionLideres);
		u.mouseOver(OpcionLideres);
		testCase.addScreenShotCurrentStep(u, "clickOpcionTablas");
		clickOpcionBusqueda();
		u.hardWait(3);
	}
	
	/**
	 * Metodo para dar click en opcion Lideres Busqueda
	 * @author Jorge Ruiz
	 */
	public void clickOpcionBusqueda() 
	{	
		u.highLight(xmlLideresBusqueda);
		testCase.addScreenShotCurrentStep(u, "Tarifas");
		u.click(xmlLideresBusqueda);
	}
	
	/**
	 * Metodo para dar click en opcion Contabilidad
	 * @author Jorge Ruiz
	 */
	public void clickContabilidad() 
	{
		u.highLight(OpcionContabilidad);
		testCase.addScreenShotCurrentStep(u, "Contabilidad");
		u.unhighLight(OpcionContabilidad);
		u.mouseOver(OpcionContabilidad);
		testCase.addScreenShotCurrentStep(u, "clickOpcionTablas");
	}
	/**
	 * Metodo para dar click en opcion Contabilidad-Impuestos
	 * @author Jorge Ruiz
	 */
	public void clickContabilidadImpuestos()
	{
		u.highLight(xmlContabilidadCalculoImpuestos);
		testCase.addScreenShotCurrentStep(u, "Contabilidad");
		u.unhighLight(xmlContabilidadCalculoImpuestos);
		u.mouseOver(xmlContabilidadCalculoImpuestos);
		testCase.addScreenShotCurrentStep(u, "clickOpcionTablas");
	}
	/**
	 * Metodo para dar click en opcion Contabilidad-Envio de Solicitud
	 * @author Jorge Ruiz
	 */
	public void clickContabilidadEnvioSolicitud()
	{
		u.highLight(xmlContabilidadEnvioSolicitud);
		testCase.addScreenShotCurrentStep(u, "Contabilidad");
		u.unhighLight(xmlContabilidadEnvioSolicitud);
		u.mouseOver(xmlContabilidadEnvioSolicitud);
		testCase.addScreenShotCurrentStep(u, "clickOpcionTablas");
	}
	/**
	 * Metodo para dar click en opcion Contabilidad-Principal
	 * @author Jorge Ruiz
	 */
	public void clickContabilidadPrincipal()
	{
		u.highLight(xmlContabilidadPrincipal);
		testCase.addScreenShotCurrentStep(u, "Contabilidad");
		u.unhighLight(xmlContabilidadPrincipal);
		u.click(xmlContabilidadPrincipal);
		testCase.addScreenShotCurrentStep(u, "clickOpcionTablas");
	}
	/**
	 * Metodo para validar que aparezca el mensaje <msg> 
	 * @param string
	 * @return retorna true si se muestra el mensaje o false en caso contrario
	 * @author Jorge Ruiz
	 */
	 public Boolean validarMensaje(By xmlMensaje,String msg) {
		Boolean exist = false;
		String mensaje = "";
		
		if(u.isDisplayed(xmlMensaje)) {
			me.scrollToElement(xmlMensaje);
			mensaje = u.getText(xmlMensaje).trim();
			u.highLight(xmlMensaje);
			testCase.addScreenShotCurrentStep(u, "validarMensaje");
			u.unhighLight(xmlMensaje);
			if (mensaje.equals(msg)) {
				exist = true;
			}
			
		} else {
			testCase.addScreenShotCurrentStep(u, "validarMensaje");
		}
		
		return exist;
	}
	 
	/**
	* Metodo para cortar informacion de un elemento y pegarlo en el mismo elemento
	* @param from elemento a trabajar, step paso en el que se esta trabajando
	* @return NA
	* @author Jorge Ruiz
	*/
	 
	public void cutAndPasteFrom(By from,String step)
	{
		Actions act = new Actions(u.getDriver());
		act.moveToElement(u.getDriver().findElement(from)).doubleClick().build().perform();
		testCase.addScreenShotCurrentStep(u,step+"Inicio");
		u.getDriver().findElement(from).sendKeys(Keys.chord(Keys.CONTROL,"x"));
		testCase.addScreenShotCurrentStep(u,step+"Cut");
		u.getDriver().findElement(from).sendKeys(Keys.chord(Keys.CONTROL,"v"));
		testCase.addScreenShotCurrentStep(u,step+"Paste");
	}
	 
}
