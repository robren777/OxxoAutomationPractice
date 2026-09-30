package WEB.monitorObligacionesFiscales.om;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import WEB.om.MetodosExtra;
import WEB.sarv3.om.ReportesSARv3;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Contabilidad{

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	ReportesSARv3 r;

	
	By OpcionContabilidad = By.xpath("//*[text()='Contabilidad']");

	//Opciones menu
	By xmlContabilidadPrincipal = By.xpath("//*[text()='Principal']");
	By xmlContabilidadCalculoImpuestos = By.xpath("//*[text()='C�lculo de Impuestos']");
	By xmlContabilidadEnvioSolicitud = By.xpath("//*[text()='Env�o de Solicitud']");
	//Fin opciones menu
	
	//Inicio dentro opcion Contabilidad-Principal
	By xmlCPLiderActivo = By.xpath("//*[@id='formPrincipal:cbLiderPrincipal']");
	By xmlCPLiderActivoOption = By.xpath("//*[@id='formPrincipal:cbLiderPrincipal_panel']/ul/li");
	
	By xmlCPCIMsgElement = By.xpath("//*[@id='formPrincipal:dialogoConfirmacionCI']/div[2]/p");
	By xmlCPBotonMsg = By.xpath("//*[@id='formPrincipal:j_idt226']");

	By xmlCPsubMenu = By.xpath("//*[@id='formPrincipal2:mmPrincipal']/ul/li/a/span[@class='ui-menuitem-text']");
	By xmlCPproveedores = By.xpath("//*[@id=\"formPrincipal2:j_idt228\"]");
	By xmlCPIngresos = By.xpath ("//*[@id=\"formPrincipal2:j_idt230\"]");
	By xmlSolicitudImpuestos = By.xpath("//*[@id=\"formPrincipal2:j_idt245\"]");
	
	//Inicio dentro opcion Contabilidad-Principal-Proveedores
	By xmlCPPRazonSocial = By.xpath("//*[@id='formProveedores1:txtRazonP']");
	By xmlCPPRFC = By.xpath("//*[@id='formProveedores1:txtRFCP']");
	
	By xmlCPPEFederativa = By.xpath("//*[@id='formProveedores1:cbEntidadFedP']");
	By xmlCPPEFederativaOption = By.xpath("//*[@id='formProveedores1:cbEntidadFedP_panel']/ul/li");
	
	By xmlCPPDelegacion = By.xpath("//*[@id='formProveedores1:cbDelegMpioP']");
	By xmlCPPDelegacionOption = By.xpath("//*[@id='formProveedores1:cbDelegMpioP_panel']/ul/li");

	By xmlCPPColonia = By.xpath("//*[@id='formProveedores1:cbColoniaP']");
	By xmlCPPColoniaOption = By.xpath("//*[@id='formProveedores1:cbColoniaP_panel']/ul/li");
	
	By xmlCPPCodigoPostal = By.xpath("//*[@id='formProveedores1:txtCodPostalP']");
	By xmlCPPOtraColonia = By.xpath("//*[@id='formProveedores1:txtOtraColP']");
	By xmlCPPNoExterior = By.xpath("//*[@id='formProveedores1:txtNumExteriorP']");
	By xmlCPPCalle = By.xpath("//*[@id='formProveedores1:txtCalleP']");
	By xmlCPPNoInterior = By.xpath("//*[@id='formProveedores1:txtNumInteriorP']");
	By xmlCPPEntreCalle = By.xpath("//*[@id='formProveedores1:txtEntreCalleP']");
	By xmlCPPYCalle = By.xpath("//*[@id='formProveedores1:txtYCalleP']");
	
	By xmlCPPConceptosAplicados = By.xpath("//*[@id='formProveedores1:plConceptosP']/tbody/tr/td[1]/ul/li");
	By xmlCPPConceptosAplicables = By.xpath("//*[@id='formProveedores1:plConceptosP']/tbody/tr/td[2]/ul/li");

	By xmlCPPAdd = By.xpath("//*[@title='Add']");
	By xmlCPPAddAll = By.xpath("//*[@title='Add All']");
	By xmlCPPRemove = By.xpath("//*[@title=' Remove']");
	By xmlCPPRemoveAll = By.xpath("//*[@title='Remove All']");
	
	By xmlCPPBTNAgregar = By.xpath("//*[@id=\"formProveedores1:btnAgregarP\"]");
	By xmlCPPActualizar = By.xpath("//*[@id='formProveedores1:btnActualizarP']");
	By xmlCPPCancelar = By.xpath("//*[@id='formProveedores1:btnCancelarP']");
	
	By xmlContabilidadProveedoresTabla = By.xpath("//*[@id='formProveedores1:tablaProveedoresP']");
	By xmlContabilidadProveedoresTablaOption = By.xpath("//*[@id='formProveedores1:tablaProveedoresP']/table/tbody/tr/td[1]");
	
	//Fin dentro opcion Contabilidad-Principal-Proveedores

	//Inicio dentro opcion Contabilidad-Principal-Ingresos
	
	By xmlCIIngresos = By.xpath("//*[@id=\"formIngresos1:txtIngresosI\"]");
	By xmlCIIva = By.xpath("//*[@id=\"formIngresos1:txtIVAI\"]");
	By xmlCIIvaRetenido = By.xpath("//*[@id=\"formIngresos1:txtIVARetenidoI\"]");
	By xmlCIBtnGuardar = By.xpath("//*[@id=\"formIngresos1:btnGuardarI\"]");
	
	//Fin dentro opcion Contabilidad-Principal-Ingresos
	
	
	//Inicio dentro opcion Contabilidad-Principal-Deducciones
	
	String xmlCPMenuDeducciones = "//span[text()='%s']";
	
	By xmlCPDeduccionesb = By.xpath("/html/body/div[1]/div/div[1]/div/div[2]/div/div/form[2]/div/ul/li[3]");
	By xmlCPDEstado = By.xpath("/html/body/div[1]/div/div[1]/div/div[2]/div/div/form[2]/div/ul/li[3]/ul/table/tbody/tr/td/ul/li[3]/a/span");
	By xmlCPDOperativas = By.xpath("/html/body/div[1]/div/div[1]/div/div[2]/div/div/form[2]/div/ul/li[3]/ul/table/tbody/tr/td/ul/li[5]/a/span");
	By xmlCPDPersonales = By.xpath("/html/body/div[1]/div/div[1]/div/div[2]/div/div/form[2]/div/ul/li[3]/ul/table/tbody/tr/td/ul/li[6]/a/span");
	By xmlCPDEstadoBtnAgregar = By.xpath("//*[@id=\"formEstado1:btnAgregarDE\"]");
	
	
	//By xmlCPDEConcepto = By.xpath("//*[@id=\"formEstado1:cbConceptoDE\"]");
	By xmlCPDEConceptoOption = By.xpath("/html/body/div[1]/div/div[1]/div/div[2]/div/div/div/div/span/form/span[1]/table/tbody/tr[1]/td[3]/div/div[1]");
	By xmlCPDECuota = By.xpath("//*[@id=\"formEstado1:txtCuotaDE\"]");
	By xmlCPDEAmortizacion = By.xpath("//*[@id=\"formEstado1:txtAmortizacionDE\"]");
	
	
	By xmlCPDOConceptoDO = By.xpath("/html/body/div[1]/div/div[1]/div/div[2]/div/div/div/div/span/form/span[1]/table/tbody/tr[1]/td[1]/label");
	By xmlCPDOConceptoDOption = By.xpath("/html/body/div[1]/div/div[1]/div/div[2]/div/div/div/div/span/form/span[1]/table/tbody/tr[1]/td[3]/div/input");
	By xmlCPDOProveedorDO = By.xpath("/html/body/div[1]/div/div[1]/div/div[2]/div/div/div/div/span/form/span[1]/table/tbody/tr[2]/td[1]/label");
	By xmlCPDOProveedorDOption = By.xpath("/html/body/div[1]/div/div[1]/div/div[2]/div/div/div/div/span/form/span[1]/table/tbody/tr[2]/td[3]/div/input");
	By xmlCPDOFacturaDO = By.xpath("//*[@id=\"formDO1:gridOutputPanelDO\"]");
	By xmlCPDOSubtotalDO = By.xpath("//*[@id=\"formDO1:txtSubtotalDO\"]");
	By xmlCPDOIvaDO = By.xpath("//*[@id=\"formDO1:txtIVADO\"]");
	By xmlCPDOBtnActualizarDO = By.xpath("//*[@id=\"formDO1:btnActualizar\"]");
	
	By xmlCPDOConceptoPE = By.xpath("//*[@id=\"formDP1:cbConceptoDP\"]");
	By xmlCPDOConceptoPEoption = By.xpath("/html/body/div[1]/div/div[1]/div/div[2]/div/div/div/div/span/form/span[1]/table/tbody/tr[1]/td[3]/div/input");
	By xmlCPDOProveedorPE = By.xpath("//*[@id=\"formDP1:cbProveedorDP\"]");
	By xmlCPDOProveedorPEoption = By.xpath("/html/body/div[1]/div/div[1]/div/div[2]/div/div/div/div/span/form/span[1]/table/tbody/tr[2]/td[3]/div/input");
	By xmlCPDOFacturaPE = By.xpath("//*[@id=\"formDP1:txtFacturaDP\"]");
	By xmlCPDOSubtotalPE = By.xpath("//*[@id=\"formDP1:txtSubtotalDP\"]");
	By xmlCPDOIvaPE = By.xpath("//*[@id=\"formDP1:txtIVADP\"]");
	By xmlCPDOBtnActualizarPE = By.xpath("//*[@id=\"formDP1:btnAgregarDP\"]");
	
	By xmlCPDEConceptoOptionSI = By.xpath("//*[@id=\"formSI1:cbConceptoSI\"]");
	By xmlCPDECuotaSI = By.xpath("//*[@id=\"formSI1:txtMontoSI\"]");
	By xmlCPDEAmortizacionSI = By.xpath("//*[@id=\"formSI1:txtAmortizacionSI\"]");
	By xmlCPSIBtnAgregar = By.xpath("//*[@id=\"formSI1:btnAgregarSI\"]");
	
	
	
	//Fin dentro opcion Contabilidad-Principal-Deducciones
	
	
	
	//Fin dentro opcion Contabilidad-Principal
	
	/*
	 * Constructor
	 ********************************************************************************************/
	public Contabilidad(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}
	
	/*
	 * Metodos Menu Integridad de datos
	 ********************************************************************************************/
	/**
	 * Metodo para seleccionar valores en select
	 * @param By, option
	 * @return void
	 * @author Jorge Ruiz
	 */
	public Boolean selectLideres(By by, By byoption,String option)
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
	
	public void seleccionarContabilidadLiderActivo(String Lider)
	{
		if(Lider!="")
			selectLideres(xmlCPLiderActivo,xmlCPLiderActivoOption,Lider);
		u.hardWait(2);
		u.click(By.xpath("//*[@id='formPrincipal:j_idt226']"));
	}
	
	public Boolean MensajeAdvertenciaContabilidad(String msg)
	{
		if(validarMensaje(xmlCPCIMsgElement,msg))
		{
			u.click(xmlCPBotonMsg);
			u.hardWait(3);
			return true;
		}
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
	
	 public Boolean selectSubmenuContabilidad(String option)
	 {
		List<WebElement> options = u.getDriver().findElements(xmlCPsubMenu);
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
	 
	 /**
	 * Metodo para seleccionar el proveedor buscado
	 * @param numero
	 * @return void
	 * @author Jorge Ruiz
	 */

	public void selectProveedoresTabla(int cont)
	{
		u.scrollToElement(xmlContabilidadProveedoresTabla);
		u.hardWait(1);
		List<WebElement> proveedores = u.getDriver().findElements(xmlContabilidadProveedoresTablaOption);
		int i = 0;
		for(WebElement proveedor:proveedores)
		{
			String text = proveedor.getText();
			if(i == cont)
			{
				proveedor.click();
				u.scrollToElement(xmlCPPRazonSocial);
				break;
			}
			i++;
		}
	}
	/**
	 * Metodo para llenar los datos del proveedor 
	 * @param numero
	 * @return void
	 * @author Jorge Ruiz
	 */
	public void modificarDatosProveedor(String RazonSocial,String RFC, String Estado, String Delegacion, String Colonia, String Codigo_Postal, String Otra_Colonia, String No_Exterior, String No_Interior, String Calle, String Entre_Calle, String Y_Calle)
	{
		Actions act = new Actions(u.getDriver());
		if(RazonSocial != "")
		{
			act.moveToElement(u.getDriver().findElement(xmlCPPRazonSocial)).doubleClick().build().perform();
			//u.getDriver().findElement(xmlCPPRazonSocial).sendKeys(Keys.chord(Keys.CONTROL,"x"));
			
			u.sendKeys(xmlCPPRazonSocial, RazonSocial);
		}
		if(RFC != "")
			u.sendKeys(xmlCPPRFC, RFC);
		if(Estado != "")
			selectLideres(xmlCPPEFederativa,xmlCPPEFederativaOption,Estado);
		u.hardWait(3);
		if(Delegacion != "")
			selectLideres(xmlCPPDelegacion,xmlCPPDelegacionOption,Delegacion);
		u.hardWait(3);
		if(Colonia != "")
			selectLideres(xmlCPPColonia,xmlCPPColoniaOption,Colonia);
		u.hardWait(3);
		if(Codigo_Postal != "")
			u.sendKeys(xmlCPPCodigoPostal,Codigo_Postal);
		if(Otra_Colonia != "")
			u.sendKeys(xmlCPPOtraColonia,Colonia);
		if(No_Exterior != "")
			u.sendKeys(xmlCPPNoExterior,No_Exterior);
		if(No_Interior != "")
			u.sendKeys(xmlCPPNoInterior,No_Interior);
		if(Calle != "")
			u.sendKeys(xmlCPPCalle,Calle);
		if(Entre_Calle != "")
			u.sendKeys(xmlCPPEntreCalle,Entre_Calle);
		if(Y_Calle != "")
			u.sendKeys(xmlCPPYCalle,Y_Calle);
	}
	/**
	 * Metodo para obtener elementos de un string separado por ,
	 * @param data
	 * @return arraylist de elementos
	 * @author Jorge Ruiz
	 */
	public ArrayList<String> getElementOfStringData(String data)
	{
		String aux = "";
		ArrayList<String> As = new ArrayList<String>();
		while(data.indexOf(',')>0)
		{
			aux = data.substring(0,data.indexOf(','));
			As.add(aux);
			data = data.substring(data.indexOf(','+1),data.length());
		}
		As.add(data);
		return As;
	}
	
	public void ContabilidadPrincipalActualizar()
	{
		u.click(xmlCPPActualizar);
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
	/**
	 * Metodo para dar click en opcion Contabilidad-Impuestos
	 * @author Yair Lizarraga
	 */

	public void clicContabilidadPrincipal()
	{
	    	u.highLight(OpcionContabilidad);
			u.mouseOver(OpcionContabilidad);
		    u.highLight(xmlContabilidadPrincipal);
		    u.mouseOver(xmlContabilidadPrincipal);
			testCase.addScreenShotCurrentStep(u, "xmlContabilidadPrincipal");
			u.click(xmlContabilidadPrincipal);
	}
		public void clickContabilidadPproveedor()
	{
			u.highLight(xmlCPproveedores);
			testCase.addScreenShotCurrentStep(u, "Proveedor");
			u.unhighLight(xmlCPproveedores);
			u.mouseOver(xmlCPproveedores);
			u.click(xmlCPproveedores);
			testCase.addScreenShotCurrentStep(u, "Proveedor");
	}
	
		public void clicCPPBtnAgregar()
		{
			u.highLight(xmlCPPBTNAgregar);
			u.mouseOver(xmlCPPBTNAgregar);
			testCase.addScreenShotCurrentStep(u, "xmlCPPBTNAgregar");
			u.click(xmlCPPBTNAgregar);
		}
		
		public void clicContabilidadPIngresos()
		{
			u.highLight(xmlCPIngresos);
			testCase.addScreenShotCurrentStep(u, "xmlCPIngresos");
			u.unhighLight(xmlCPIngresos);
			u.click(xmlCPIngresos);

		}
		
		public void modificarDatosIngresos(String Ingresos,String Iva, String Iva_Retenido)
		{
			Actions act = new Actions(u.getDriver());
			if(Ingresos != "")
			{
				act.moveToElement(u.getDriver().findElement(xmlCIIngresos)).doubleClick().build().perform();
				u.sendKeys(xmlCIIngresos, Ingresos);
			}
			if(Iva != "")
				u.sendKeys(xmlCIIva, Iva);
			if(Iva_Retenido != "")
				u.sendKeys(xmlCIIvaRetenido, Iva_Retenido);
		}
		
		public void clicContabilidadPIBtnGuardar()
		{
			u.highLight(xmlCIBtnGuardar);
			testCase.addScreenShotCurrentStep(u, "xmlCIBtnGuardar");
			u.unhighLight(xmlCIBtnGuardar);
			u.click(xmlCIBtnGuardar);

		}
		
		public void clicCPDeduccionesEstado()
		{
			u.scrollToElement(xmlCPDeduccionesb);
		   	u.highLight(xmlCPDeduccionesb);
			u.mouseOver(xmlCPDeduccionesb);
			u.highLight(xmlCPDEstado);
			u.mouseOver(xmlCPDEstado);
			testCase.addScreenShotCurrentStep(u, "xmlCPDEstado");
			u.click(xmlCPDEstado);
		}
		
		public Boolean modificarDatosDeduccionEstado(String Concepto,String Cuota, String Amortizacion) {
			
			Boolean correct = false;
				
			try {
					
					Boolean c = r.selectOption(Concepto, xmlCPDEConceptoOption);
					u.hardWait(5);
					u.sendKeys(xmlCPDECuota, Cuota);
					u.sendKeys(xmlCPDEAmortizacion, Amortizacion);

					
					if(c) correct = true;
					
				}catch(Exception e) {
					testCase.addScreenShotCurrentStep(u, "NoSeIngresoInformacion");
				}
				
				return correct;
}
		public void clicContabilidadPDEBtnAgregar()
		{
			u.highLight(xmlCPDEstadoBtnAgregar);
			testCase.addScreenShotCurrentStep(u, "xmlCPDEstadoBtnAgregar");
			u.unhighLight(xmlCPDEstadoBtnAgregar);
			u.click(xmlCPDEstadoBtnAgregar);

		}
		
		public void clicCPDeduccionesOperativas()
		{
			u.scrollToElement(xmlCPDeduccionesb);
		   	u.highLight(xmlCPDeduccionesb);
			u.mouseOver(xmlCPDeduccionesb);
			u.highLight(xmlCPDOperativas);
			u.mouseOver(xmlCPDOperativas);
			testCase.addScreenShotCurrentStep(u, "xmlCPDOperativas");
			u.click(xmlCPDOperativas);
		}
		
		public void modificarDatosDeduccionOperativo(String ConceptoDO,String ProveedorDO, String SubtotalDO, String IvaDO)
		{
			Actions act = new Actions(u.getDriver());
			if(ConceptoDO != "")
			{
				act.moveToElement(u.getDriver().findElement(xmlCPDOConceptoDO)).doubleClick().build().perform();
				selectLideres(xmlCPDOConceptoDO,xmlCPDOConceptoDOption,ConceptoDO);
			}
			if(ProveedorDO != "")
				selectLideres(xmlCPDOProveedorDO,xmlCPDOProveedorDOption,ProveedorDO);
			if(SubtotalDO != "")
				u.sendKeys(xmlCPDOSubtotalDO, SubtotalDO);
			if(IvaDO != "")
				u.sendKeys(xmlCPDOIvaDO, IvaDO);
			testCase.addScreenShotCurrentStep(u, "modificarDatosDeduccionOperativosss");
		}
		
		public void clicContabilidadPDOBtnActualizar()
		{
			u.highLight(xmlCPDOBtnActualizarDO);
			testCase.addScreenShotCurrentStep(u, "xmlCPDOBtnActualizarDO");
			u.unhighLight(xmlCPDOBtnActualizarDO);
			u.click(xmlCPDOBtnActualizarDO);

		}
		
		public void clicCPDeduccionesPersonales()
		{
			u.scrollToElement(xmlCPDeduccionesb);
		   	u.highLight(xmlCPDeduccionesb);
			u.mouseOver(xmlCPDeduccionesb);
			u.highLight(xmlCPDPersonales);
			u.mouseOver(xmlCPDPersonales);
			testCase.addScreenShotCurrentStep(u, "xmlCPDPersonales");
			u.click(xmlCPDPersonales);
		}
		public void modificarDatosDeduccionPersonal(String ConceptoPE,String ProveedorPE, String SubtotalPE, String IvaPE)
		{
			Actions act = new Actions(u.getDriver());
			if(ConceptoPE != "")
			{
				act.moveToElement(u.getDriver().findElement(xmlCPDOConceptoPE)).doubleClick().build().perform();
				selectLideres(xmlCPDOConceptoPE,xmlCPDOConceptoPEoption,ConceptoPE);
			}
			if(ProveedorPE != "")
				selectLideres(xmlCPDOProveedorPE,xmlCPDOProveedorPEoption,ProveedorPE);
			if(SubtotalPE != "")
				u.sendKeys(xmlCPDOSubtotalDO, SubtotalPE);
			if(IvaPE != "")
				u.sendKeys(xmlCPDOIvaDO, IvaPE);
			testCase.addScreenShotCurrentStep(u, "modificarDatosDeduccionOperativosss");
		}
			
		public void clicContabilidadPPEBtnActualizar()
		{
			u.highLight(xmlCPDOBtnActualizarPE);
			testCase.addScreenShotCurrentStep(u, "xmlCPDOBtnActualizarPE");
			u.unhighLight(xmlCPDOBtnActualizarPE);
			u.click(xmlCPDOBtnActualizarPE);

		}
		
		public void clicContabilidadPSolicituddeImpuestos()
		{
				u.highLight(xmlSolicitudImpuestos);
				testCase.addScreenShotCurrentStep(u, "xmlSolicitudImpuestos");
				u.unhighLight(xmlSolicitudImpuestos);
				u.mouseOver(xmlSolicitudImpuestos);
				u.click(xmlSolicitudImpuestos);
				testCase.addScreenShotCurrentStep(u, "xmlSolicitudImpuestos");
		}
		
		public Boolean modificarDatosSolicitudeImpuestos(String ConceptoSI,String CuotaSI, String AmortizacionSI) {
			
			Boolean correct = false;
				
			try {
					
					Boolean c = r.selectOption(ConceptoSI, xmlCPDEConceptoOptionSI);
					u.hardWait(5);
					u.sendKeys(xmlCPDECuotaSI, CuotaSI);
					u.sendKeys(xmlCPDEAmortizacionSI, AmortizacionSI);

					
					if(c) correct = true;
					
				}catch(Exception e) {
					testCase.addScreenShotCurrentStep(u, "NoSeIngresoInformacion");
				}
				
				return correct;
}
		public void clicContabilidadPSIBtnAgregar()
		{
			u.highLight(xmlCPSIBtnAgregar);
			testCase.addScreenShotCurrentStep(u, "xmlCPSIBtnAgregar");
			u.unhighLight(xmlCPSIBtnAgregar);
			u.click(xmlCPSIBtnAgregar);

		}
		
	
}


