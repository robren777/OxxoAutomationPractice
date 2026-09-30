package WEB.util.MOF;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.TestCase;
import WEB.util.AuxMethods;
import utils.selenium.SeleniumUtil;

public class MOF {
	private SeleniumUtil u;
	private WebDriverWait wait;
	private TestCase testCase;
	private AuxMethods aux;
	
	
	public MOF(SeleniumUtil u, TestCase testCase, AuxMethods aux) {
		super();
		this.u = u;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		this.testCase = testCase;
		this.aux = aux;
	}
	
	private By FinanzasAdministracion = By.xpath("//*[text()[normalize-space()='Finanzas y Administraci�n']]");
	
	private By monitorObligacionesFiscales = By.xpath("//*[text()[normalize-space()='Monitor de Obligaciones Fiscales']]");
	
	private By SubTitleMonitorObligacionesFiscales = By.xpath("//h1[text()='Monitor de Obligaciones Fiscales']");
	
	//MONITOREO DE OBLIGACIONES FISCALES
		
	//-----CATALOGOS / DESPACHOS
	private By MOFCatalogos = By.xpath("//*[text()[normalize-space()='Cat�logos']]");
	private By CatalogosDespachos = By.xpath("//*[text()[normalize-space()='Despachos']]");
	private By DespachoPlaza = By.xpath("//input[@name='form1:plazaId_editableInput']");
	private By DespachoEstado = By.xpath("//input[@name='form1:cbEstado_editableInput']");
	private By DespachoTableResult = By.xpath("//*[@id='form1:tablaDespachos_data']/tr/td/div");
	private By DespachoCiudad = By.xpath("//input[@name='form1:cbCiudad_editableInput']");
	private By DespachoColonia = By.xpath("//input[@name='form1:cbColonia_editableInput']");
	private By DespachoCalle = By.xpath("//input[@id='form1:txtCalle']");
	private By DespachoNumero = By.xpath("//input[@id='form1:txtNumero']");
	private By DespachoTelefono = By.xpath("//input[@id='form1:txtTelefono']");
	
	private By BtnActualizarDespacho = By.xpath("//input[@id='form1:btnActualizarDespacho']");
	private By MsgActualizaExitosa = By.xpath("//*[text()[normalize-space()='La operaci�n fue exitosa.']]");
	
	//-----TABLAS / Tarifas
	private By MOFTablas = By.xpath("//*[text()[normalize-space()='Tablas']]");
	private By TablasTarifas = By.xpath("//*[text()[normalize-space()='Tarifas']]");
	
	private By MOFIndicadores = By.xpath("//*[text()[normalize-space()='Indicadores']]");
	private By IndicadoresTasasIETU = By.xpath("//*[text()[normalize-space()='TASAS IETU']]");
	private By tableTasasIETU = By.cssSelector("tbody[id=\"form1:tablaIndicadore_data\"] > tr[data-ri=\"1\"]");
	private By inputTasa = By.cssSelector("input[id=\"form1:txtIETU\"]");
	private By buttonGuardarTasa = By.cssSelector("input[id=\"form1:btnGuardar\"]");
	private By msn = By.className("ui-messages-info-summary");
	private By buttonDescargarExcel = By.cssSelector("input[id=\"form1:btnExcel\"]");
	
	private By MOFReportesSADEL = By.xpath("//*[text()[normalize-space()='Reportes SADEL']]");
	private By ReportesSADELJornadaTrabajo = By.xpath("//*[text()[normalize-space()='Jornada de Trabajo']]");
	private By selectPlazaJT = By.cssSelector("div[id=\"form1:plazaId\"] > div.ui-selectonemenu-trigger");
	private By selectLiderJT = By.cssSelector("div[id=\"form1:liderId\"] > div.ui-selectonemenu-trigger");
	private By buttonDescargarJT = By.cssSelector("input[id=\"form1:btnReporte\"]");
	
	private By blockCargando = By.cssSelector("div[id=\"form1:j_idt237\"]");
	private By radioExcelJT = By.cssSelector("table[id=\"form1:salidaReporte\"] > tbody > tr > td:nth-child(3)");
	private By buttonDescargarExcelJT = By.cssSelector("input[id=\"form1:btnExcel\"]");
	
	
	/**
	 * Metodo para comprobar que el apartado de 'Finanzas y Administracion' esta activo
	 * 
	 * @return ExpON
	 * @author Jose Onofre
	 */
	public boolean IsFinanzasAdministracionActive() {
		
		aux.scrollToElement(FinanzasAdministracion);
		boolean ExpON = false;
		u.hardWait(3);
		if("Finanzas y Administraci�n".equals(u.getText(FinanzasAdministracion))) {
			
			ExpON = true;
			u.highLight(FinanzasAdministracion);
			u.hardWait(2);
			testCase.addTextEvidenceCurrentStep("Se encontro el Modulo: Finanzas y Administracion "   );
			
			
			return ExpON;
		}
		else {
			
			testCase.addTextEvidenceCurrentStep("No se encontro el Modulo: Finanzas y Administracion "   );

		}
		
		
		return ExpON;
	
	}
	
	
	/**
	 * Metodo para hacer click en 'Monitor de Obligaciones Fiscales'
	 * 
	 * @author Jose Onofre
	 */
	
	public void ClickMOF() {
		
		u.hardWait(3);
		u.highLight(monitorObligacionesFiscales);
		u.hardWait(5);
		aux.clickOpcion("monitorObligacionesFiscales", monitorObligacionesFiscales, true);
		u.hardWait(5);
		
	}
	
	
	
	/**
	 * Comprueba que el texto del Subtitulo sea igual al esperado
	 * 
	 * @return true si el texto del Subtitle coincide con el esperado 'Monitor de Obligaciones Fiscales'
	 * @author Jose Onofre
	 */
	public boolean IsMonitorObligacionesFiscalesActive() {
		
		boolean subtitle = false; 
		//aux.scrollToElement(monitorObligacionesFiscales);
		
		u.waitForLoadPage();
		
		if("Monitor de Obligaciones Fiscales".equals(u.getText(SubTitleMonitorObligacionesFiscales))) {
			
			subtitle = true;
			testCase.addTextEvidenceCurrentStep("Se encontro el Modulo: Monitor de Obligaciones Fiscales "   );
			aux.clickOpcion("Proveedores", SubTitleMonitorObligacionesFiscales, true);
			u.hardWait(2);
			
			
			return subtitle;
		}
		
		testCase.addTextEvidenceCurrentStep("No se encontro el Modulo: Monitor de Obligaciones Fiscales "   );
		return subtitle;
	}
	
	/**
	 * Metodo que sirve para hacer click sobre el apartado 'Monitor de Obligaciones Fiscales'
	 * Busca en toda la pagina los elementos con el mismo nombre, los guarda en una lista de WebElements
	 * y hace click sobre el elemento en la posicion dada por la entrada op
	 * 
	 * @param op
	 * @author Jose Onofre
	 */
	public void clickPortalMonitorObligacionesFiscales(int op) {
    	u.waitWebElement(monitorObligacionesFiscales, 6, 5);
    	u.scrollToElement(monitorObligacionesFiscales);
    	List<WebElement> monitores = u.getDriver().findElements(monitorObligacionesFiscales);
    	int cont = 0;
    	for(WebElement i: monitores)
    	{
    		if(cont == op)
    		{
    	    	testCase.addScreenShotCurrentStep(u, "clickPortalMOF");
    			i.click();	
    	    	testCase.addScreenShotCurrentStep(u, "clickPortalMOF");
    	    	break;
    		}
    		cont++;
    	}
    	u.hardWait(3);
    }
	
	/**
	 * Metodo que hace resalta y coloca el mouse sobre el boton de 'Catalogos'
	 * 
	 * @author Jose Onofre
	 */
	public void SelectMOFCatalogos() {
		
		u.highLight(MOFCatalogos);
		u.mouseOver(MOFCatalogos);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("Catalogos");
		u.hardWait(2);
		
	}
	
	/**
	 * Metodo que resalta y selecciona la opcion 'Despachos' del menu 'Catalogos'
	 * 
	 * @author Jose Onofre
	 */
	public void OpCatalogosDespachos() {
		
		u.highLight(CatalogosDespachos);
		aux.clickOpcion("CatalogosDespachos", CatalogosDespachos, true);
		u.hardWait(3);
	}
	
	
	/**
	 * Metodo que hace resalta y coloca el mouse sobre el boton de 'Tablas'
	 * 
	 * @author Jose Onofre
	 */
	public void SelectMOFTablas() {
		
		u.highLight(MOFTablas);
		u.mouseOver(MOFTablas);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("Tablas");
		u.hardWait(2);
		
	}
	
	/**
	 * Metodo que resalta y selecciona la opcion 'Despachos' del menu 'Catalogos'
	 * 
	 * @author Jose Onofre
	 */
	public void OpTablasTarifas() {
		
		u.highLight(TablasTarifas);
		aux.clickOpcion("TablasTarifas", TablasTarifas, true);
		u.hardWait(3);
	}
	
	
	/**
	 * Metodo que sirve para escribir en el campo Plaza la plaza a buscar usando un String que sirve como entrada al identificador de la plaza
	 * 
	 * @param idPlaza
	 * @return idPlazaName
	 * @author Jose Onofre
	 */
	public By SetInputDespachoPlaza(String idPlaza) {
		
		String SXpathPlazaName = "//div[@id='form1:plazaId_panel']/ul/li[text()[normalize-space()='%s']]";
		
		String XpathPlazaName = String.format(SXpathPlazaName, idPlaza);
		
		By idPlazaName = By.xpath(XpathPlazaName);
		
		return idPlazaName;
	}
	
	/**
	 * Metodo que resalta, hace click y busca un elemento de la lista de opciones de Plaza en base al string dado.
	 * El string plaza se agrega al locator para ubicar el elemento con ese nombre
	 * 
	 * @param plaza
	 * @author Jose Onofre
	 */
	public void OpDespachoPlaza(String plaza) {
		
		By LocatorPlaza = SetInputDespachoPlaza(plaza);
		
		u.highLight(DespachoPlaza);
		u.click(DespachoPlaza);
		u.hardWait(5);
		u.highLight(LocatorPlaza);
		u.click(LocatorPlaza);
		u.waitForLoadPage();	
	}
	
	
	/**
	 * Metodo que sirve para escribir en el campo Estado el estado a buscar usando un String que sirve como entrada al identificador del Estado
	 * 
	 * @param idEstado
	 * @return IdEstadoName
	 * @author Jose Onofre
	 */
	public By SetInputDespachoEstado(String idEstado) {
		
		String SXpathEstadoName = "//div[@id='form1:cbEstado_panel']/ul/li[text()='%s']";
		
		String XpathEstadoName = String.format(SXpathEstadoName, idEstado);
		
		By idEstadoName = By.xpath(XpathEstadoName);
		
		return idEstadoName;
	}
	
	
	/**
	 * Metodo que resalta, hace click y busca un elemento de la lista de opciones de Estado en base al string dado.
	 * El string estado se agrega al locator para ubicar el elemento con ese nombre
	 * 
	 * @param estado
	 * @author Jose Onofre
	 */
	public void OpDespachoEstado(String estado) {
		
		By LocatorEstado = SetInputDespachoEstado(estado);
		
		u.highLight(DespachoEstado);
		u.click(DespachoEstado);
		u.hardWait(5);
		u.highLight(LocatorEstado);
		u.click(LocatorEstado);
		u.waitForLoadPage();	
	}
	
	/**
	 * Metodo que resalta, hace click sobre el primer elemento de la tabla de resultado de 'Despacho'
	 * 
	 * @author Jose Onofre
	 */
	public void ClickOnDespachoTableResult() {
		
		u.waitForLoadPage();
		u.highLight(DespachoTableResult);
		u.click(DespachoTableResult);
		u.hardWait(5);
		u.unhighLight(DespachoTableResult);
		u.hardWait(5);
		
	}
	
	/**
	 * Metodo que sirve para escribir en el campo Estado el estado a buscar usando un String que sirve como entrada al identificador del Estado
	 * 
	 * @param idEstado
	 * @return idCiudadName
	 * @author Jose Onofre
	 */
	public By SetInputDespachoCiudad(String idCiudad) {
		
		String SXpathCiudadName = "//*[@id='form1:cbCiudad_panel']/ul/li[text()='%s']";
		
		String XpathCiudadName = String.format(SXpathCiudadName, idCiudad);
		
		By idCiudadName = By.xpath(XpathCiudadName);
		
		return idCiudadName;
	}
	
	/**
	 * Metodo que resalta, hace click y busca un elemento de la lista de opciones de Ciudad en base al string dado.
	 * El string ciudad se agrega al locator para ubicar el elemento con ese nombre
	 * 
	 * @param ciudad
	 * @author Jose Onofre
	 */
	public void OpDespachoCiudad(String ciudad) {
		
		By LocatorCiudad = SetInputDespachoCiudad(ciudad);
		
		u.highLight(DespachoCiudad);
		u.click(DespachoCiudad);
		u.hardWait(5);
		u.highLight(LocatorCiudad);
		u.click(LocatorCiudad);
		u.waitForLoadPage();	
	}
	
	
	/**
	 * Metodo que sirve para escribir en el campo Estado el estado a buscar usando un String que sirve como entrada al identificador del Estado
	 * 
	 * @param idEstado
	 * @return idColoniaName
	 * @author Jose Onofre
	 */
	public By SetInputDespachoColonia(String idColonia) {
		
		String SXpathColoniaName = "//*[@id='form1:cbColonia_panel']/ul/li[text()='%s']";
		
		String XpathColoniaName = String.format(SXpathColoniaName, idColonia);
		
		By idColoniaName = By.xpath(XpathColoniaName);
		
		return idColoniaName;
	}
	
	/**
	 * Metodo que resalta, hace click y busca un elemento de la lista de opciones de Colonia en base al string dado.
	 * El string colonia se agrega al locator para ubicar el elemento con ese nombre
	 * 
	 * @param colonia
	 * @author Jose Onofre
	 */
	public void OpDespachoColonia(String colonia) {
		
		By LocatorColonia = SetInputDespachoColonia(colonia);
		
		u.highLight(DespachoColonia);
		u.click(DespachoColonia);
		u.hardWait(5);
		u.highLight(LocatorColonia);
		u.click(LocatorColonia);
		u.waitForLoadPage();	
	}
	
	/**
	 * Metodo que resalta y envia el string al campo de input 'Calle'
	 * 
	 * @param calle
	 * @author Jose Onofre
	 */
	public void InputDespachoCalle(String calle) {
		
		u.highLight(DespachoCalle);
		u.sendKeys(DespachoCalle, calle);
		u.hardWait(2);
		
		
	}
	
	/**
	 * Metodo que resalta y envia el string al campo de input 'Numero'
	 * 
	 * @param numero
	 * @author Jose Onofre
	 */
	public void InputDespachoNumero(String numero) {
		
		u.highLight(DespachoNumero);
		u.sendKeys(DespachoNumero, numero);
		u.hardWait(2);
		
	}
	
	/**
	 * Metodo que resalta y envia el string al campo de input 'Telefono'
	 * 
	 * @param telefono
	 * @author Jose Onofre
	 */
	public void InputDespachoTelefono(String telefono) {
		
		u.highLight(DespachoTelefono);
		u.sendKeys(DespachoTelefono, telefono);
		u.hardWait(2);
		
		
	}

	
	/**
	 * Metodo que hace click sobre el boton 'Actualizar' en el modulo de Despachos
	 * 
	 * @author Jose Onofre
	 */
	public void ClickBtnActualizarDespacho() {
		
		u.highLight(BtnActualizarDespacho);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "ActualizarDespacho");
		u.click(BtnActualizarDespacho);
	}
	
	/**
	 * Metodo que valida que el texto de 'La operaci�n fue exitosa.' sea visible al modificar o guardar un nuevo registro
	 * 
	 * @return Active
	 * @author Jose Onofre
	 */
	public boolean IsActualizacionCompletada() {
		
		boolean Active = false;
		
		if(u.isDisplayed(MsgActualizaExitosa)) {
			
			u.highLight(MsgActualizaExitosa);
			testCase.addScreenShotCurrentStep(u, "Se actualizo la informacion Correctamente");
			testCase.addTextEvidenceCurrentStep("Se actualizo la informacion Correctamente");
			Active = true;
			
			return Active;
		}
		else {
			
			testCase.addScreenShotCurrentStep(u, "No se pudo actualizar la informacion");
			testCase.addTextEvidenceCurrentStep("No se pudo actualizar la informacion");
			
			return Active;
		}
		
	}
	
	/**
	 * Selecciona el menu indicadores
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void SelectMOFIndicadores() {

		u.highLight(MOFIndicadores);
		u.mouseOver(MOFIndicadores);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("Indicadores");
		u.hardWait(2);

	}

	/**
	 * Selecciona el submenu "Tasas IETU"
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void OpIndicadoresTasasIETU() {

		u.highLight(IndicadoresTasasIETU);
		aux.clickOpcion("TasasIETU", IndicadoresTasasIETU, true);
		u.hardWait(3);
	}

	/**
	 * Selecciona el menu Reportes SADEL
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void SelectMOFReportesSADEL() {

		u.highLight(MOFReportesSADEL);
		u.mouseOver(MOFReportesSADEL);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("Reportes SADEL");
		u.hardWait(2);

	}

	/**
	 * Selecciona el submenu "Jornadas de Trabajo"
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void OpReportesSADELJornadaTrabajo() {
		u.highLight(ReportesSADELJornadaTrabajo);
		aux.clickOpcion("JornadaTrabajo", ReportesSADELJornadaTrabajo, true);
		u.hardWait(3);
	}

	/**
	 * Selecciona el segundo registro de la tabla.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void seleccionarTasa() {
		aux.clickOpcion("Registro en Tabla", tableTasasIETU, true);
	}

	/**
	 * Ingresa el nuevo valor de la tasa.
	 * 
	 * @param tasa
	 * @author Esmeralda Garcia Torres
	 */
	public void ingresarTasa(String tasa) {
		aux.enterOption("Tasa", tasa, inputTasa, false);
		aux.clickOpcion("Guardar", buttonGuardarTasa, true);
		u.hardWait(3);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(msn));
			String mensajeString = u.getDriver().findElement(msn).getAttribute("innerText");
			aux.logS("Mensaje: " + mensajeString);
			if (mensajeString.contains("fue exitosa.")) {
				testCase.addScreenShotCurrentStep(u, "Exitoso");
			} else {
				throw new AssertionError("No se actualiz� correctamente el registro");
			}
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Fallo al validar el mensaje de confirmacion");
		}
	}

	/**
	 * Valida que la tasa se actualizo en la tabla.
	 * 
	 * @param tasa
	 * @author Esmeralda Garcia Torres
	 */
	public void validarCambioTasa(String tasa) {
		try {
			u.highLight(tableTasasIETU);
			String inner = u.getDriver().findElement(tableTasasIETU).getText();
			aux.logS("innerText: " + inner);
			if (inner.contains(tasa + " %")) {
				testCase.addScreenShotCurrentStep(u, "Se actualizo");
			} else {
				throw new AssertionError("No se actualiz� correctamente el registro");
			}
			u.unhighLight(tableTasasIETU);
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Fallo al validar la actualizacion");
		}
	}

	/**
	 * Da click en el boton descargar Excel.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void descargarExcel() {
		aux.clickOpcion("Descargar Excel", buttonDescargarExcel, true);
	}

	/**
	 * Selecciona la plaza y el lider.
	 * 
	 * @param plaza
	 * @param lider
	 * @author Esmeralda Garcia Torres
	 */
	public void ingresarDatosJornadaTrabajo(String plaza, String lider) {
		try {
			aux.clickOpcion("Plaza", selectPlazaJT, false);
			By opPlaza = By.xpath("//*[@id=\"form1:plazaId_panel\"]/ul/li[text()=\"" + plaza + "\"]");
			aux.clickOpcion("Opcion Plaza", opPlaza, true);
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Fallo al ingresar la Plaza");
		}
		cargando();
		try {
			aux.clickOpcion("Lider", selectLiderJT, false);
			By opLider = By.xpath("//*[@id=\"form1:liderId_panel\"]/ul/li[text()=\"" + lider + "\"]");
			aux.clickOpcion("Opcion Lider", opLider, true);
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Fallo al ingresar el Lider");
		}
		cargando();
	}

	/**
	 * Descargar reporte en pantalla.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void descargarReporteJornadasTrabajo() {
		try {
			aux.clickOpcion("Descargar", buttonDescargarJT, true);
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Fallo al seleccionar Descargar");
		}

		try {
			while (!u.getDriver().findElement(By.cssSelector("div[id=\"form1:panelReportePantalla\"]")).isDisplayed()) {
				u.hardWait(2);
				aux.logS("Esperando reporte en pantalla");
			}
			aux.scrollToElement(By.cssSelector(".ui-paginator-bottom"));
			testCase.addScreenShotCurrentStep(u, "Reporte en Pantalla");
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Fallo al esperar reporte");
		}
	}

	/**
	 * Espera hasta que desaparezca el mensade de "Cargando..."
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	private void cargando() {
		try {
			u.hardWait(2);
			while (u.getDriver().findElement(blockCargando).isDisplayed()) {
				u.hardWait(2);
				aux.logS("Cargando...");
			}
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Fallo Cargando...");
		}
	}

	/**
	 * Descarga el reporte de Excel.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void DescargarExcelJornadasTrabajo() {
		try {
			aux.clickOpcion("RadioButton Excel", radioExcelJT, true);
			u.hardWait(2);
			u.highLight(buttonDescargarExcelJT);
			WebElement ele = u.getDriver().findElement(buttonDescargarExcelJT);
			Actions action = new Actions(u.getDriver());
			testCase.addScreenShotCurrentStep(u, "DescargarExcel");
			u.unhighLight(buttonDescargarExcelJT);
			action.click(ele).perform();
			testCase.addScreenShotCurrentStep(u, "DescargarExcel2");
			testCase.addTextEvidenceCurrentStep(
					"Para obtener los archivos debe ir a la carpeta de descargas del equipo.");
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Fallo al generar Excel");
		}
	}
	
}