package WEB.simoti_Colombia.om;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class ConsultarEditarProveedoresEDI_SimotiColombia {
	
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	MenuPrincipal_SimotiColombia mp;
	String Replace = "";
	
	/*Localizadores Header*/
	By titulo = By.xpath("//label[contains(text(), 'Consultar y Editar Proveedores EDI')]");
	By seccionFiltros = By.xpath("//form[@name='formConsultarEditarProvEdi']/fieldset[1]");
	By seccionResultados = By.xpath("//form[@name='formConsultarEditarProvEdi']/fieldset[2]");
	
	/*Localizadores Filtros*/	
	By plazaSelect = By.xpath("//div[@id='formConsultarEditarProvEdi:cmbPlaza']");
	By plazaOptions = By.xpath("//ul[@id='formConsultarEditarProvEdi:cmbPlaza_items']/li");
	
	By cedisSelect = By.xpath("//div[@id='formConsultarEditarProvEdi:cmbCedis']");
	By cedisOptions = By.xpath("//ul[@id='formConsultarEditarProvEdi:cmbCedis_items']/li");
		
	By proveedorSelect = By.xpath("//div[@id='formConsultarEditarProvEdi:cmbProveedor']");
	By proveedorOptions = By.xpath("//ul[@id='formConsultarEditarProvEdi:cmbProveedor_items']/li");
	
	/*Localizadores Botones*/
	By xmlConsultar = By.id("formConsultarEditarProvEdi:btnBuscar");
	By xmlExportar = By.id("formConsultarEditarProvEdi:btnExprotar");
	By AgregarProvBtn = By.id("formConsultarEditarProvEdi:btnAgregarProveedor");

	/*Localizadores Seccion Resultados*/	
	By tablaResultados = By.xpath("//*[@id=\"formConsultarEditarProvEdi:repProveedEDI\"]//table");
	String tablaHeaders = "//*[@id='formConsultarEditarProvEdi:repProveedEDI']//table/thead/tr/th[%s]"; 
	By resultados = By.xpath("//tbody[@id='formConsultarEditarProvEdi:repProveedEDI_data']/tr/td");

	String editarStr = "";
	String verStr = "";
	String eliminarStr = "";
	String editarPlBuzStr = "";

	
	String xmltableHead = "//*[@id='formEnvDineroTdc:gridRepTransaccTdc']/div/table/thead/tr/th[%s]"; //th [15]
	String xmltableBody = "//*[@id='formEnvDineroTdc:gridRepTransaccTdc']/div/table/tbody/tr[%1$s]/td[%2$s]"; //tr[15]
	By xmlRegistros = By.xpath("//*[@id='formEnvDineroTdc:gridRepTransaccTdc_paginator_bottom']/span[@class='ui-paginator-current']");
	
	
	/*Localizadores Mensajes*/
	//By mensaje = By.xpath("");
	
	/*
	 * Constructor
	 ******************************************************************************************/
	
	/** Constructor de la clase
	 * @author David Valdez
	 * @param u, TestCase
	 * @return N/A
	 */
	public ConsultarEditarProveedoresEDI_SimotiColombia(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
		this.mp = new MenuPrincipal_SimotiColombia(u, testCase);	
	}

	public void clickBoton(By by) {
		u.highLight(by);
		testCase.addScreenShotCurrentStep(u, "clickBoton");
		u.unhighLight(by);
		u.click(by);	
	}

	public void clickConsultarBtn() {
		u.highLight(xmlConsultar);
		testCase.addScreenShotCurrentStep(u, "Consultar");
		u.unhighLight(xmlConsultar);
		u.mouseOverAndClick(xmlConsultar);
		mp.waitLoading();
		testCase.addScreenShotCurrentStep(u, "clickConsultar");
	}
	
	/** Metodo para seleccionar los filtros de la busqueda
	 * @author Andrea Contreras
	 * @param 
	 */
	public void capturaFiltros(String plaza, String cedis, String proveedor) {
 
		boolean f1 = false, f2 = false, f3 = false;
		if(plaza!="") {
			f1 = selectOption(plazaSelect,plazaOptions,plaza);
			u.hardWait(2);
			mp.waitLoading();
            if(f1==false) {
				Assert.fail("No se pudo seleccionar la Plaza: "+plaza);
			}
		}
		System.out.println(plaza);
		
		
		if(cedis!="") {
			f2 = selectOption(cedisSelect,cedisOptions,cedis);
			u.hardWait(2);
			mp.waitLoading();
            if(f2==false) {
            	Assert.fail("No se pudo seleccionar la Cedis: "+cedis);
			}
		}
		System.out.println(cedis);
		
		
		if(proveedor!="") {
			f3 = selectOption(proveedorSelect,proveedorOptions,proveedor);
			u.hardWait(2);
			mp.waitLoading();
			if(f3==false) {
				Assert.fail("No se pudo seleccionar el Proveedor: "+proveedor);
			}
		}
		System.out.println(proveedor);
		
		u.hardWait(2);
	
	}

	/**
	 * Metodo para seleccionar la una opcion si el modo de visualizacion es un SELECT
	 * @param opcion
	 * @return Retorna true si se selecciono correctamente la opcion indicada
	 * @author Andrea Contreras
	 */
	public Boolean selectOption(By list, By listElements, String opcion) {
		Boolean correct = false;
		
		WebElement element = me.explicitWaitToBeClickable(list, Duration.ofSeconds(30));
		u.hardWait(1);
		element.click();
		List<WebElement> allOptions = u.getDriver().findElements(listElements);
		
		for(WebElement option:allOptions){
			if(option.getText().trim().equals(opcion)) {
				option.click();
				correct = true;
				u.mouseOver(titulo);
				break;
			}
		}
		
		mp.waitLoading();
		
		testCase.addScreenShotCurrentStep(u, opcion+"Seleccionado");
		
		return correct;
	}

	/**
	 * Metodo para validar que los campos cedis y proveedor esten deshabilitados
	 * @return Retorna true o false si estan deshabilitados
	 * @author Andrea Contreras
	 */
	public Boolean camposDeshabilitadosCedisProveedor() {
		Boolean validacion = false; 
		String cedis, proveedor;
		
		cedis = u.getDriver().findElement(cedisSelect).getAttribute("class");
		proveedor = u.getDriver().findElement(proveedorSelect).getAttribute("class");
		
		if(cedis.contains("disabled") && proveedor.contains("disabled")) {
			validacion = true;
			u.highLight(cedisSelect);	
			u.highLight(proveedorSelect);	
			testCase.addScreenShotCurrentStep(u, "validarCampos");
			u.unhighLight(cedisSelect);
			u.unhighLight(proveedorSelect);
		}
		
		return validacion;

	}
	
	
	
	/**
	 * Metodo para validar que los campos cedis y proveedor esten deshabilitados
	 * @return Retorna true o false si estan deshabilitados
	 * @param 
	 * @author Andrea Contreras
	 */
	public Boolean validacionCampos(String opcionValidacion) {
		Boolean validacion = false; 
		String plaza,cedis, proveedor;
		
		plaza = u.getDriver().findElement(plazaSelect).getAttribute("class");
		cedis = u.getDriver().findElement(cedisSelect).getAttribute("class");
		proveedor = u.getDriver().findElement(proveedorSelect).getAttribute("class");		
		
		switch(opcionValidacion) {
		
		//El sistema deshabilita los campos Cedis y  Proveedor
		case "1":				
			if(cedis.contains("disabled") && proveedor.contains("disabled")) {
				System.out.println("opcion 1");
				validacion = true;
				u.highLight(cedisSelect);	
				u.highLight(proveedorSelect);	
				testCase.addScreenShotCurrentStep(u, "validarCampos1");
				u.unhighLight(cedisSelect);
				u.unhighLight(proveedorSelect);
			}
			
			break;
			
		//El sistema habilita el campo Cedis y el campo Proveedor continua deshabilitado
		case "2":
			
			if(((cedis.contains("disabled")) == false) && proveedor.contains("disabled")) {
				System.out.println("opcion 2");
				validacion = true;
				u.highLight(cedisSelect);	
				u.highLight(proveedorSelect);	
				testCase.addScreenShotCurrentStep(u, "validarCampos2");
				u.unhighLight(cedisSelect);
				u.unhighLight(proveedorSelect);
			}
			break;
			
		//El sistema habilita el campo Cedis, deshabilita el campo Plaza y el campo Proveedor sigue deshabilitado	
		case "3":
			if((cedis.contains("disabled"))==false && plaza.contains("disabled") && proveedor.contains("disabled") ) {
				System.out.println("opcion 3");
				validacion = true;
				u.highLight(plazaSelect);
				//u.highLight(cedisSelect);	
				u.highLight(proveedorSelect);	
				testCase.addScreenShotCurrentStep(u, "validarCampos3");
				u.unhighLight(plazaSelect);
				//u.unhighLight(cedisSelect);
				u.unhighLight(proveedorSelect);
			}
			break;
			
		//El sistema habilita el campo Proveedor y el campo Plaza	
		case "4":			
			if((proveedor.contains("disabled"))==false && (plaza.contains("disabled"))==false) {
				System.out.println("opcion 4");
				validacion = true;
				u.highLight(plazaSelect);
				u.highLight(proveedorSelect);	
				testCase.addScreenShotCurrentStep(u, "validarCampos4");
				u.unhighLight(plazaSelect);
				u.unhighLight(proveedorSelect);
			}
			break;
		
		}
		
		return validacion;

	}
	
	/**
	 * Metodo para validar los encabezados
	 * @return Retorna true o false si estan deshabilitados
	 * @author Andrea Contreras
	 */
	public Boolean validarEncabezadosTablaResultados() {
		Boolean correct = false;
		
		u.scrollToElement(tablaResultados);
			
		String no = u.getText(By.xpath(String.format(tablaHeaders, "1"))).trim();
		String prov = u.getText(By.xpath(String.format(tablaHeaders, "2"))).trim();
		String nom = u.getText(By.xpath(String.format(tablaHeaders, "3"))).trim();
		String tipoArch = u.getText(By.xpath(String.format(tablaHeaders, "4"))).trim();
		String estado = u.getText(By.xpath(String.format(tablaHeaders, "5"))).trim();
		String editar = u.getText(By.xpath(String.format(tablaHeaders, "6"))).trim();
		String ver = u.getText(By.xpath(String.format(tablaHeaders, "7"))).trim();
		String editarPyB = u.getText(By.xpath(String.format(tablaHeaders, "8"))).trim();
		String eliminar = u.getText(By.xpath(String.format(tablaHeaders, "9"))).trim();

		if(no.equals("No.") && prov.equals("Proveedor") && nom.equals("Nombre")
				&& tipoArch.equals("Tipo Archivo") && estado.equals("Estado") && editar.equals("EDITAR")
				&& ver.equals("VER") && editarPyB.equals("Editar plazas y buzones") && eliminar.equals("Eliminar")) 
		{
			correct = true;
			for(int i = 1;i<=9;i++) {
				//System.out.print(By.xpath(String.format(tablaHeaders,i)));
				u.scrollToElement(By.xpath(String.format(tablaHeaders,i)));
				u.highLight(By.xpath(String.format(tablaHeaders,i)));
			}
			testCase.addScreenShotCurrentStep(u, "encabezados");
			for(int i = 1;i<=9;i++) {
				u.unhighLight(By.xpath(String.format(tablaHeaders,i)));
			}
		}
		
		return correct;

	}
	
	/**
	 * Metodo para validar que se muestre resultados en la tabla de resultados
	 * @return Retorna true o false si se muestran resultados
	 * @author Andrea Contreras
	 */
	public Boolean validarExistenResultados() {
		Boolean validacion = false;
		String resultado = "";
		
		if(me.existElement(resultados)) {
			resultado = u.getText(resultados);	
			u.highLight(resultados);	
			testCase.addScreenShotCurrentStep(u, "validarExistanResultados");
			u.unhighLight(resultados);	
		}
		
		if(!resultado.equals("Sin resultados")) {
			validacion = true;		
		}
				
		return validacion;


	}
	
	
	/**
	 * Metodo seleccionar tipo archivo  y estado, click en btn actualizar
	 * @return 
	 * @author Andrea Cano
	 */
	public void seleccionarTipoArchivoYEstado() {
		this.clickBoton(By.xpath("//*[@id=\"formAgregaProv:tipoArchivoAgrP\"]/div[3]")); //dropdown tipo archivo
		this.clickBoton(By.xpath("//*[@id=\"formAgregaProv:tipoArchivoAgrP_1\"]")); //EDIFACT
		
		this.clickBoton(By.xpath("//*[@id=\"formAgregaProv:estadoAgrP\"]/div[3]/span")); //dropdown estado 
		this.clickBoton(By.xpath("//*[@id=\"formAgregaProv:estadoAgrP_1\"]")); //P Produccion
		
		this.clickBoton(By.xpath("//*[@id=\"formAgregaProv:btnUpdateProv\"]")); //btn actualizar
	}

	
	/**
	 * Metodo consultar proveedor 
	 * @return 
	 * @author Andrea Cano
	 */
	public void consultarProveedor(String proveedor) {
		u.sendKeys(By.xpath("//*[@id=\"formAgregaProv:j_idt548\"]"), proveedor);
		testCase.addScreenShotCurrentStep(u, proveedor);
		this.clickBoton(By.xpath("//*[@id=\"formAgregaProv:btnBuscar\"]")); //btn consultar
	}
  
   /**
	 * Metodo para que le de click al link de alguna de las opciones del proveedor indicado
	 * @return Retorna true o false si da click en el link
	 * @param
	 * @author Andrea Contreras
	 */
	public Boolean clickLinkOpcion(String provId, String opcion) {
		Boolean click = false;
		By xpath = null;
		
		switch(opcion.toUpperCase()) {
	       case "EDITAR":	
	    	   xpath = By.xpath(String.format(editarStr, provId));    	   
		   break;
		   
	       case "VER":	
	    	   xpath = By.xpath(String.format(verStr, provId));  		
		   break;
			   
	       case "EDITAR_PLAZA_Y_BUZONES":	
	    	   xpath = By.xpath(String.format(editarPlBuzStr, provId));
		   break;
		   
	       case "ELIMINAR":
	    	   xpath = By.xpath(String.format(eliminarStr, provId));
			   break;
		}
		
		try {
				
	    u.hardWait(2);
	    if(me.existElement(xpath)) {
			 u.highLight(xpath);
			 testCase.addScreenShotCurrentStep(u, "clickLink");
			 u.click(xpath);
			 u.unhighLight(xpath);
			 click = true;
		}
	    
		}catch(Exception e) {
			Assert.fail("No se encontro la opcion del link a seleccionar");
		}
				
		return click;


	}
	
	
}
