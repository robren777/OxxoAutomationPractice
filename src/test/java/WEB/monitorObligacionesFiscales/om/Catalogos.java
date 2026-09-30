package WEB.monitorObligacionesFiscales.om;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Catalogos{

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;

	By OpcionCatalogos = By.xpath("//div/ul/li/a[contains(text(), 'Cat�logos')]");
	
	//Opciones menu
	
	By OpcionDespachos = By.xpath("//div/ul/li/a[contains(text(), 'Despachos')]");
	By OpcionParametros = By.xpath("//div/ul/li/a[contains(text(), 'Par�metros')]");
	By OpcionDeducciones = By.xpath("//div/ul/li/a[contains(text(), 'Deducciones')]");

	//Fin opciones menu
	
	//Inicio dentro de Despachos
	By xmlDespachosPlaza = By.xpath("//*[@id='form1:plazaId']");
	By xmlDespachosPlazaOption = By.xpath("//*[@id='form1:plazaId_panel']/ul/li");
	By xmlDespachosRFC = By.xpath("//*[@id='form1:txtRFC']");
	By xmlDespachosRazonSocial = By.xpath("//*[@id='form1:txtRazonSocial']");
	By xmlDespachosEstado = By.xpath("//*[@id='form1:cbEstado']");
	By xmlDespachosEstadoOption = By.xpath("//*[@id='form1:cbEstado_panel']/ul/li");
	By xmlDespachosCiudad = By.xpath("//*[@id='form1:cbCiudad']");
	By xmlDespachosCiudadOption = By.xpath("//*[@id='form1:cbCiudad_panel']/ul/li");
	By xmlDespachosColonia = By.xpath("//*[@id='form1:cbColonia']");
	By xmlDespachosColoniaOption = By.xpath("//*[@id='form1:cbColonia_panel']/ul/li");
	By xmlDespachosCalle = By.xpath("//*[@id='form1:txtCalle']");
	By xmlDespachosNumero = By.xpath("//*[@id='form1:txtNumero']");
	By xmlDespachosEmail = By.xpath("//*[@id='form1:txtEmail']");
	By xmlDespachosTelefono = By.xpath("//*[@id='form1:txtTelefono']");
	By xmlDespachoActualizar = By.xpath("//*[@id='form1:btnActualizarDespacho']");
	By xmlTablaDespachos = By.xpath("//*[@id='form1:tablaDespachos']");
	By xmlTablaDespachosRegistros = By.xpath("//*[@id='form1:tablaDespachos']/table/tbody/tr");

	By xmlDespachosMsg = By.xpath("//*[@id='form1:messages']/div/ul/li/span");
	//Fin dentro de Despachos
	
	//Inicio dentro de Parametros
	By xmlParametrosParametro = By.xpath("//*[@id='form1:txtParametro']");
	By xmlParametrosDato = By.xpath("//*[@id='form1:txtDato']");
	By xmlTablaParametros = By.xpath("//*[@id='form1:tablaParametros']");
	By xmlParametrosAgregar = By.xpath("//*[@id='form1:btnParametro']");
	By xmlTablaParametrosRegistros = By.xpath("//*[@id='form1:tablaParametros']/table/tbody/tr");
	By xmlTablaParametrosHeader = By.xpath("//*[@id='form1:tablaParametros']/table/thead/tr/th");
	//Fin dentro de Parametros

	//Inicio dentro de Deducciones
	By xmlDeduccionesConcepto = By.xpath("//*[@id='form1:txtNombreConcepto']");
	By xmlDeduccionesTipoConcepto = By.xpath("//*[@id='form1:cbTipoConcepto']");
	By xmlDeduccionesTipoConceptoOption = By.xpath("//*[@id='form1:cbTipoConcepto_panel']");
	By xmlDeduccionesRequisito = By.xpath("//*[@id='form1:txtRequisito']");
	By xmlDeduccionesGastoAplicable = By.xpath("//*[@id='form1:txtGastoAplicable']");
	By xmlDeduccionesFrecuencia = By.xpath("//*[@id='form1:cbfrecuenciaAplicable_panel']");
	By xmlDeduccionesFrecuenciaOption = By.xpath("//*[@id='form1:cbAplicablePara_panel']");
	By xmlDeduccionesISR = By.xpath("//*[@id='form1:j_idt224']"); //ISR
	By xmlDeduccionesIETU = By.xpath("//*[@id='form1:j_idt229']"); //IETU
	By xmlDeduccionesActivo = By.xpath("//*[@id='form1:j_idt236']"); //Activo
	
	By xmlDeduccionesAgregar = By.xpath("//*[@id='form1:btnAgregarDeduccion']");
	By xmlDeduccionesTabla = By.xpath("//*[@id='form1:tblDeduccion']");
	By xmlDeduccionesTablaRegistros = By.xpath("//*[@id='form1:tblDeduccion']/table/tbody/tr");
	By xmlDeduccionesTablaHeader = By.xpath("//*[@id='form1:tblDeduccion']/table/thead/tr/th");
	
	//Fin dentro de Deducciones	
	
	By xmlContabilidadCancelar = By.xpath("//[@id='form1:btnCancelar']");
	/*
	 * Constructor
	 ********************************************************************************************/
	public Catalogos(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}
	

	public void llenarInformacionDespachos(String Plaza, String RazonSocial)
	{
		if(Plaza!="")
			selectCatalogos(xmlDespachosPlaza,xmlDespachosPlazaOption, Plaza);
		u.hardWait(3);
		selectfromTable(xmlTablaDespachos,xmlTablaDespachosRegistros,RazonSocial);
		
		cutAndPasteFrom(xmlDespachosCalle,"Calle");
		cutAndPasteFrom(xmlDespachosNumero,"Numero");
		cutAndPasteFrom(xmlDespachosEmail,"Email");
		cutAndPasteFrom(xmlDespachosTelefono,"Telefono");
		u.hardWait(2);
		u.click(xmlDespachoActualizar);
	}
	
	/**
	 * Metodo para seleccionar valores en select
	 * @param By, option
	 * @return void
	 * @author Jorge Ruiz
	 */
	public Boolean selectCatalogos(By by, By byoption,String option)
	{
		u.click(by);
		u.hardWait(1);
		List<WebElement> options = u.getDriver().findElements(byoption);
		int i = 0;
		for(WebElement opt:options)
		{
			String text = opt.getText();
			//System.out.println(text + " _ " + option);
			if(text.equals(option))
			{
				opt.click();
				return true;
			}
			i++;
		}
		return false;
	}
	
	public void selectfromTable(By table,By registro,String name)
	{
		u.scrollToElement(table);
		List<WebElement> despachos = u.getDriver().findElements(registro);
		for(WebElement despacho : despachos)
		{
			String text = despacho.getText();
			if(text.contains(name))
			{
				despacho.click();
				break;
			}
		}
		u.hardWait(3);
	}
	
	public void crearParametros(String Parametro, String Dato)
	{
		selectfromTable(xmlTablaParametros,xmlTablaParametrosRegistros,Parametro);
		//cutAndPasteFrom(xmlParametrosParametro, Parametro);
		cutAndPasteFrom(xmlParametrosDato,Dato);
		u.hardWait(3);
		u.click(xmlParametrosAgregar);
	}
	
	public void crearDeduccion(String Concepto/*, String TipoConcepto,Boolean ISR, Boolean IETU, Boolean Activo*/)
	{

		selectCatalogos(xmlDeduccionesTabla,xmlDeduccionesTablaRegistros,Concepto);
		cutAndPasteFrom(xmlDeduccionesConcepto,"Concepto");
		/*
		if(Concepto!="")
			u.sendKeys(xmlDeduccionesConcepto, Concepto);
		if(TipoConcepto!="")
			selectCatalogos(xmlDeduccionesTipoConcepto,xmlDeduccionesTipoConceptoOption,TipoConcepto);
		if(ISR && !(u.getDriver().findElement(xmlDeduccionesISR).getCssValue("checked").equals("checked")))
			u.click(xmlDeduccionesISR);
		else if(!ISR && u.getDriver().findElement(xmlDeduccionesISR).getCssValue("checked").equals("checked"))
			u.click(xmlDeduccionesISR);
		if(IETU && !(u.getDriver().findElement(xmlDeduccionesIETU).getCssValue("checked").equals("checked")))
			u.click(xmlDeduccionesIETU);
		else if(!IETU && u.getDriver().findElement(xmlDeduccionesIETU).getCssValue("checked").equals("checked"))
			u.click(xmlDeduccionesIETU);
		if(Activo && !(u.getDriver().findElement(xmlDeduccionesActivo).getCssValue("checked").equals("checked")))
			u.click(xmlDeduccionesActivo);
		else if(!Activo && u.getDriver().findElement(xmlDeduccionesActivo).getCssValue("checked").equals("checked"))
			u.click(xmlDeduccionesActivo);
		*/
		
		u.hardWait(3);
		u.click(xmlDeduccionesAgregar);
	}
	
	public void modificarDeduccion(String Concepto, String TipoConcepto,Boolean ISR, Boolean IETU, Boolean Activo)
	{
		if(TipoConcepto!="")
			selectCatalogos(xmlDeduccionesTipoConcepto,xmlDeduccionesTipoConceptoOption,TipoConcepto);
		if(ISR && !(u.getDriver().findElement(xmlDeduccionesISR).getCssValue("checked").equals("checked")))
			u.click(xmlDeduccionesISR);
		else if(!ISR && u.getDriver().findElement(xmlDeduccionesISR).getCssValue("checked").equals("checked"))
			u.click(xmlDeduccionesISR);
		if(IETU && !(u.getDriver().findElement(xmlDeduccionesIETU).getCssValue("checked").equals("checked")))
			u.click(xmlDeduccionesIETU);
		else if(!IETU && u.getDriver().findElement(xmlDeduccionesIETU).getCssValue("checked").equals("checked"))
			u.click(xmlDeduccionesIETU);
		if(Activo && !(u.getDriver().findElement(xmlDeduccionesActivo).getCssValue("checked").equals("checked")))
			u.click(xmlDeduccionesActivo);
		else if(!Activo && u.getDriver().findElement(xmlDeduccionesActivo).getCssValue("checked").equals("checked"))
			u.click(xmlDeduccionesActivo);
		u.hardWait(3);
		u.click(xmlDeduccionesAgregar);
	}

	/**
	* Metodo para cortar informacion de un elemento y pegarlo en el mismo elemento
	* @param from elemento a trabajar, step paso en el que se esta trabajando
	* @return NA
	* @author Jorge Ruiz
	*/
	 
	public void cutAndPasteFrom(By from,String step)
	{
		u.scrollToElement(from);
		Actions act = new Actions(u.getDriver());
		act.moveToElement(u.getDriver().findElement(from)).doubleClick().build().perform();
		testCase.addScreenShotCurrentStep(u,step+"Inicio");
		u.getDriver().findElement(from).sendKeys(Keys.chord(Keys.CONTROL,"x"));
		testCase.addScreenShotCurrentStep(u,step+"Cut");
		u.getDriver().findElement(from).sendKeys(Keys.chord(Keys.CONTROL,"v"));
		testCase.addScreenShotCurrentStep(u,step+"Paste");
	}
	
	public Boolean validarMensajeSuccess(String msg)
	{
		if(validarMensaje(xmlDespachosMsg,msg))
			return true;
		return false;
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
}
