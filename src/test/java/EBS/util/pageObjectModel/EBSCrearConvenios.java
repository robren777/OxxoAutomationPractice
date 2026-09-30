package EBS.util.pageObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.sikuli.hotkey.Keys;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;

import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class EBSCrearConvenios {

	SeleniumUtil u;
	TestCase testCase;
	String emptyTable = "No se realizó búsqueda.";

	public EBSCrearConvenios(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		PageFactory.initElements(u.getDriver(), this);
	}

	By TableNegocio = By.xpath("//div[@id='contentDiv:tblNegocio']");
	@FindBy(xpath = "//div[@id='contentDiv:tblNegocio']")
	public WebElement TableNegocioElement;

	By chckboxOXXO = By.xpath("//input[@id='tblNegocio:cbSelectFlag:0']");
	@FindBy(xpath = "//input[@id='tblNegocio:cbSelectFlag:0']")
	public WebElement chckboxOXXOElement;

	By inputRFC = By.xpath("//input[@id='lovRFC']");
	@FindBy(xpath = "//input[@id='lovRFC']")
	public WebElement inputRFCElement;

	By inputCorreo = By.xpath("//input[@id='txtEmailComercial']");
	@FindBy(xpath = "//input[@id='txtEmailComercial']")
	public WebElement inputCorreoElement;

	By CboTipoNegociacion = By.xpath("//select[@id='choiceTipoNegociacion']");
	@FindBy(xpath = "//select[@id='choiceTipoNegociacion']")
	public WebElement CboTipoNegociacionElement;

	By CboTipoNegocPOPPROMOOXXO = By
			.xpath("//select[@id='choiceTipoNegociacion']//following-sibling::option[contains(text(),'POP PROMOXXO')]");
	@FindBy(xpath = "//select[@id='choiceTipoNegociacion']//following-sibling::option[contains(text(),'POP PROMOXXO')]")
	public WebElement CboTipoNegocPOPPROMOOXXOElement;

	By CboChoiceConcepto = By.xpath("//select[@id='choiceConcepto']");
	@FindBy(xpath = "//select[@id='choiceConcepto']")
	public WebElement CboChoiceConceptoElement;

	By CboChoiceConceptoBalconBotanas = By
			.xpath("//select[@id='choiceConcepto']//following-sibling::option[contains(text(),'Balcon Botanas')]");
	@FindBy(xpath = "//select[@id='choiceConcepto']//following-sibling::option[contains(text(),'Balcon Botanas')]")
	public WebElement CboChoiceConceptoBalconBotanasElement;

	By CboChoiceAdminSuperGrupo = By.xpath("//select[@id='choiceAdminSuperGrupo']");
	@FindBy(xpath = "//select[@id='choiceAdminSuperGrupo']")
	public WebElement CboChoiceAdminSuperGrupoElement;

	By CboChoiceAdminSuperGrupoAntojo = By
			.xpath("//select[@id='choiceAdminSuperGrupo']/option[contains(text(),'Antojo')]");
	@FindBy(xpath = "//select[@id='choiceAdminSuperGrupo']/option[contains(text(),'Antojo')]")
	public WebElement CboChoiceAdminSuperGrupoAntojoElement;

	By CboChoiceSuperGrupo = By.xpath("//select[@id='choiceSuperGrupo']");
	@FindBy(xpath = "//select[@id='choiceSuperGrupo']")
	public WebElement CboChoiceSuperGrupoElement;

	By CboChoiceCategorias = By.xpath("//select[@id='choiceCategorias']");
	@FindBy(xpath = "//select[@id='choiceCategorias']")
	public WebElement CboChoiceCategoriasElement;

	By CboChoiceSubCategorias = By.xpath("//select[@id='choiceSubCategorias']");
	@FindBy(xpath = "//select[@id='choiceSubCategorias']")
	public WebElement CboChoiceSubCategoriasElement;

	By txtAreaMecanica = By.xpath("//textarea[@id='txtMecanica']");
	@FindBy(xpath = "//textarea[@id='txtMecanica']")
	public WebElement txtAreaMecanicaElement;

	By listBancoPlazas = By.xpath("//select[@id='listBancoPlazas']");
	@FindBy(xpath = "//select[@id='listBancoPlazas']")
	public WebElement listBancoPlazasElement;

	By OptionDCUColimaBancoPlazas = By
			.xpath("//select[@id='listBancoPlazas']/option[contains(text(),'10DCU Colima-O')]");
	@FindBy(xpath = "//select[@id='listBancoPlazas']/option[contains(text(),'10DCU Colima-O')]")
	public WebElement OptionDCUColimaBancoPlazasElement;

	By btnMoverAOtraLista = By.xpath("//button[@title='Mover los elementos seleccionados a otra lista']");
	@FindBy(xpath = "//button[@title='Mover los elementos seleccionados a otra lista']")
	public WebElement btnMoverAOtraListaElement;

	By listBancoSemgmentos = By.xpath("//select[@id='listBancoSegmentos']");
	@FindBy(xpath = "//select[@id='listBancoSegmentos']")
	public WebElement listBancoSemgmentosElement;

	By OptionOxxoSuperBancoSemgmentos = By
			.xpath("//select[@id='listBancoSegmentos']/option[@value='S_OXXO_SUPER|Oxxo Super']");
	@FindBy(xpath = "//select[@id='listBancoSegmentos']/option[@value='S_OXXO_SUPER|Oxxo Super']")
	public WebElement OptionOxxoSuperBancoSemgmentosElement;

	By btnMoverSegmentosAOtraLista = By
			.xpath("//table[@id='shuttleSegmentos']//button[@title='Mover los elementos seleccionados a otra lista']");
	@FindBy(xpath = "//table[@id='shuttleSegmentos']//button[@title='Mover los elementos seleccionados a otra lista']")
	public WebElement btnMoverSegmentosAOtraListaElement;

	By tableTipoTiendas = By.xpath("//table[@id='region21317']");
	@FindBy(xpath = "//table[@id='region21317']")
	public WebElement tableTipoTiendasElement;

	By OptionSinAlcoholTipoTiendas = By.xpath("//table[@id='region21317']//input[@name='N21:selected:2']");
	@FindBy(xpath = "//table[@id='region21317']//input[@name='N21:selected:2']")
	public WebElement OptionSinAlcoholTipoTiendasElement;

	By VigenciaCalendario = By.xpath("//select[@id='choiceTiposCalendario']");
	@FindBy(xpath = "//select[@id='choiceTiposCalendario']")
	public WebElement VigenciaCalendarioElement;

	By PeriodicidadFacturacion = By
			.xpath("//select[@id='txtPeriodicidadFacturacion']/option[contains(text(),'Periodo Oxxo')]");
	@FindBy(xpath = "//select[@id='txtPeriodicidadFacturacion']/option[contains(text(),'Periodo Oxxo')]")
	public WebElement PeriodicidadFacturacionElement;

	By choiceInicioPeriodo = By.xpath("//select[@id='choiceInicioPeriodo']/option[contains(text(),'P1 2022')]");
	@FindBy(xpath = "//select[@id='choiceInicioPeriodo']/option[contains(text(),'P1 2022')]")
	public WebElement choiceInicioPeriodoElement;

	By choiceFinPeriodo = By.xpath("//select[@id='choiceFinPeriodo']/option[contains(text(),'P12 2022')]");
	@FindBy(xpath = "//select[@id='choiceFinPeriodo']/option[contains(text(),'P12 2022')]")
	public WebElement choiceFinPeriodoElement;

	By choiceAportacion = By.xpath("//select[@id='choiceAportacion']/option[contains(text(),'Monto Fijo X Tienda')]");
	@FindBy(xpath = "//select[@id='choiceAportacion']/option[contains(text(),'Monto Fijo X Tienda')]")
	public WebElement choiceAportacionElement;

	By DocumentoFiscal = By.xpath("//span[text()='Factura']");
	@FindBy(xpath = "//span[text()='Factura']")
	public WebElement DocumentoFiscalElement;

	By TasaImpuesto = By.xpath("//select[@id='choiceTasaImpuesto']/option[text()='Exento']");
	@FindBy(xpath = "//select[@id='choiceTasaImpuesto']/option[text()='Exento']")
	public WebElement TasaImpuestoElement;

	By FormaDePago = By.xpath("//span[contains(text(),'Folio de Pago')]");
	@FindBy(xpath = "//span[contains(text(),'Folio de Pago')]")
	public WebElement FormaDePagoElement;

	By btnAplicar = By.xpath("//button[@id='btnAplicar_uixr']");
	@FindBy(xpath = "//button[@id='btnAplicar_uixr']")
	public WebElement btnAplicarElement;

	By numConvenio = By.xpath("//pre[@class='OraErrorHeader MessageComponentLayoutText']");
	@FindBy(xpath = "//pre[@class='OraErrorHeader MessageComponentLayoutText']")
	public WebElement numConvenioElement;

	/*
	 * OTROS
	 * 
	 */

	By txtNumConvenio = By.xpath("//input[@id='msgFolioConvenio']");
	@FindBy(xpath = "//input[@id='msgFolioConvenio']")
	public WebElement txtNumConvenioElement;

	By btnIr = By.xpath("//button[@id='btnIr']");
	@FindBy(xpath = "//button[@id='btnIr']")
	public WebElement btnIrElement;

	By btnEmitirPDF = By.xpath("//img[@title='Emitir']");
	@FindBy(xpath = "//img[@title='Emitir']")
	public WebElement btnEmitirPDFElement;

	/*
	 * REPORTE DE CONVENIOS
	 */

	By ReporteConveniostxtNumConvenio = By.xpath("//input[@id='msgFolioConvenio']");
	@FindBy(xpath = "//input[@id='msgFolioConvenio']")
	public WebElement ReporteConveniostxtNumConvenioElement;

	By ReporteConveniosbtnIr = By.xpath("//button[@id='btnIr']");
	@FindBy(xpath = "//button[@id='btnIr']")
	public WebElement ReporteConveniosbtnIrElement;

	By txtClaveUsuario = By.xpath("//input[@id='lovUsuario']");
	@FindBy(xpath = "//input[@id='lovUsuario']")
	public WebElement txtClaveUsuarioElement;

	By txtTipoNegocio = By.xpath("//select[@id='mcTipoNegociacion']/option[text()='POP PROMOXXO']");
	@FindBy(xpath = "//select[@id='mcTipoNegociacion']/option[text()='POP PROMOXXO']")
	public WebElement txtTipoNegocioElement;

	By tableFirstValueColumnaConvenio = By.xpath("//tr/td[@class='x1t x50']");
	@FindBy(xpath = "//tr/td[@class='x1t x50']")
	public WebElement tableFirstValueColumnaConvenioElement;

	By FirstValueEncounteredColumnaConvenio = By.xpath("//tr/td[text()='No se realizó búsqueda.']");
	@FindBy(xpath = "//tr/td[text()='No se realizó búsqueda.']")
	public WebElement FirstValueEncounteredColumnaConvenioElement;

	By btnExportar = By.xpath("//button[@id='btnExportar']");
	@FindBy(xpath = "//button[@id='btnExportar']")
	public WebElement btnExportarElement;

	/*
	 * Reportes IC
	 * 
	 */

	By cboSegmentoBase = By.xpath("//select[@id='mcSegmento']/option[text()='Base']");
	@FindBy(xpath = "//select[@id='mcSegmento']/option[text()='Base']")
	public WebElement cboSegmentoBaseElement;

	/**
	 * Selecciona el checkbox con la opcion OXXO para la tabla de Negocio
	 * 
	 * @author JoseO@Hexaware.com
	 */
	public void selectNegocio() {

		u.waitForLoadPage();
		u.highLight(TableNegocio);
		u.highLight(chckboxOXXO);
		u.hardWait(5);
		u.click(chckboxOXXO);

	}

	/**
	 * Selecciona el textbox de campo RFC Facturacion e ingresa el RFC
	 * 
	 * @author JoseO@Hexaware.com
	 * @param rfc
	 */
	public void typeRFC(String rfc) {

		u.highLight(inputRFC);
		u.hardWait(3);
		u.sendKeys(inputRFC, rfc);
		u.hardWait(3);
		u.sendKeys(inputRFC, Key.TAB);

	}

	/**
	 * Seleccion el textbox de campo Correo y escribe el correo
	 * 
	 * @author JoseO@Hexaware.com
	 * @param correo
	 */
	public void typeCorreo(String correo) {

		u.highLight(inputCorreo);
		u.hardWait(3);
		u.sendKeys(inputCorreo, correo);
		u.hardWait(3);
		u.sendKeys(inputCorreo, Key.TAB);

	}

	/**
	 * Se encarga de llenar todos los campos requeridos por el formulario de Unico
	 * Convenio
	 * 
	 * @author JoseO@Hexaware.com
	 */
	public void fillTipoNegociacion() {

		u.highLight(CboTipoNegociacion);
		u.click(CboTipoNegociacion);
		u.sendKeys(CboTipoNegociacion, "p");
		u.sendKeys(CboTipoNegociacion, "p");
		u.hardWait(3);
		u.highLight(CboTipoNegocPOPPROMOOXXO);
		u.click(CboTipoNegocPOPPROMOOXXO);
		u.hardWait(3);
		u.highLight(CboChoiceConcepto);
		u.click(CboChoiceConcepto);
		u.hardWait(3);
		u.waitForLoadPage();
		u.highLight(CboChoiceConceptoBalconBotanas);
		u.click(CboChoiceConceptoBalconBotanas);
		u.hardWait(5);
		u.waitForLoadPage();
		u.highLight(CboChoiceAdminSuperGrupo);
		u.click(CboChoiceAdminSuperGrupo);
		u.hardWait(5);
		u.waitForLoadPage();
		u.highLight(CboChoiceAdminSuperGrupo);
		u.click(CboChoiceAdminSuperGrupoAntojo);
		u.hardWait(5);
		u.sendKeys(CboChoiceAdminSuperGrupo, Key.ALT);
		u.hardWait(5);
		u.sendKeys(CboChoiceSuperGrupo, "r");
		u.hardWait(5);
		u.sendKeys(CboChoiceCategorias, Key.ALT);
		u.hardWait(5);
		u.sendKeys(CboChoiceCategorias, "s");
		u.hardWait(5);
		u.sendKeys(CboChoiceSubCategorias, Key.ALT);
		u.hardWait(5);
		u.sendKeys(CboChoiceSubCategorias, "s");
		u.hardWait(5);
		u.highLight(txtAreaMecanica);
		u.click(txtAreaMecanica);
		u.hardWait(3);
		u.sendKeys(txtAreaMecanica, "Mecanica de Prueba");
		u.hardWait(5);
		u.highLight(listBancoPlazas);
		u.click(OptionDCUColimaBancoPlazas);
		u.hardWait(5);
		u.highLight(btnMoverAOtraLista);
		u.click(btnMoverAOtraLista);
		u.hardWait(5);
		u.highLight(listBancoSemgmentos);
		u.click(OptionOxxoSuperBancoSemgmentos);
		u.hardWait(5);
		u.highLight(btnMoverSegmentosAOtraLista);
		u.click(btnMoverSegmentosAOtraLista);
		u.hardWait(5);
		u.highLight(tableTipoTiendas);
		u.hardWait(3);
		u.click(OptionSinAlcoholTipoTiendas);
		u.hardWait(5);
		u.highLight(VigenciaCalendario);
		u.click(VigenciaCalendario);
		u.hardWait(5);
		u.sendKeys(VigenciaCalendario, "p");
		u.sendKeys(VigenciaCalendario, Keys.TAB);
		u.hardWait(5);
		u.click(PeriodicidadFacturacion);
		u.hardWait(5);
		u.highLight(choiceInicioPeriodo);
		u.click(choiceInicioPeriodo);
		u.hardWait(8);
		u.highLight(choiceFinPeriodo);
		u.click(choiceFinPeriodo);
		u.hardWait(5);
		u.highLight(choiceAportacion);
		u.click(choiceAportacion);
		u.hardWait(10);
		u.scroll(800);
		u.hardWait(5);
		u.click(DocumentoFiscal);
		u.hardWait(5);
		u.click(TasaImpuesto);
		u.hardWait(5);
		u.scroll(800);
		u.hardWait(5);
		u.highLight(FormaDePago);
		u.click(FormaDePago);
		u.hardWait(5);

	}

	public String GenerarConvenio() {

		u.hardWait(5);
		u.click(btnAplicar);
		u.waitForLoadPage();
		String convenio = u.getText(numConvenio);

		return convenio;
	}

	public void IngresarConvenio(String convenio) {

		u.highLight(txtNumConvenio);
		u.click(txtNumConvenio);
		u.hardWait(5);
		u.sendKeys(txtNumConvenio, convenio);
		testCase.addScreenShotCurrentStep(u, "Convenio");
		u.hardWait(5);
		u.click(btnIr);
		testCase.addScreenShotCurrentStep(u, "clickBtnIr");
	}

	public void clickOnEmitirPDF() {

		u.hardWait(10);
		u.highLight(btnEmitirPDF);
		testCase.addScreenShotCurrentStep(u, "EmitirPDF");
		u.click(btnEmitirPDF);
		testCase.addScreenShotCurrentStep(u, "clickEmitirPDF");
	}

	public void IngresarReporteConvenio(String tipoNegociacion, String claveUsuario) {

		u.highLight(txtTipoNegocio);
		u.click(txtTipoNegocio);
		u.hardWait(5);
		u.sendKeys(txtTipoNegocio, tipoNegociacion);
		u.hardWait(5);
		u.highLight(txtClaveUsuario);
		u.click(txtClaveUsuario);
		u.hardWait(5);
		u.sendKeys(txtClaveUsuario, claveUsuario);
		u.hardWait(5);
		testCase.addScreenShotCurrentStep(u, "IngresarReporteConvenio");
		u.click(btnIr);
		testCase.addScreenShotCurrentStep(u, "clickIngresarReporteConvenio");

	}

	public boolean GetConvenioResult() {

		String result = u.getText(tableFirstValueColumnaConvenio);

		if (result == emptyTable) {

			return true;
		} else
			return false;

	}

	public boolean GetFirstValueConvenio() {
		
		try { 
			String numConvenio = u.getText(FirstValueEncounteredColumnaConvenio);

			if (emptyTable != numConvenio) {

				return true;
			} else
				return false;
		} catch(Exception e) {
			return true;
		}
		
	}

	public void clickExportar() {

		u.hardWait(5);
		u.scrollToElement(btnExportar);
		u.highLight(btnExportar);
		testCase.addScreenShotCurrentStep(u, "Exportar");
		u.unhighLight(btnExportar);
		u.click(btnExportar);
		testCase.addScreenShotCurrentStep("clickExportar");
		u.hardWait(5);

	}

	public void ReporteIC() {

		u.highLight(cboSegmentoBase);
		u.click(cboSegmentoBase);
		u.hardWait(5);
		u.click(btnIr);
	}
	
	/**
	 * Metodo para dar click en el boton Ir
	 * @author MariaD1@hexaware.com
	 */
	public void clickBtnIr() {
		u.scrollToElement(btnIr);
		u.highLight(btnIr);
		testCase.addScreenShotCurrentStep(u, "BtnIr");
		u.click(btnIr);
		testCase.addScreenShotCurrentStep(u, "clickBtnIr");
		u.waitForLoadPage();
	}
	
	/**
	 * Metodo para seleccionar la opcion base del filtro segmento
	 * @author MariaD1@hexaware.com
	 */
	public void filtrosReporteIC() {

		u.highLight(cboSegmentoBase);
		u.click(cboSegmentoBase);
		testCase.addScreenShotCurrentStep(u, "filtrosReporteIC");
		u.hardWait(5);
	}
	
	/**
	 * Metodo para abrir archivo excel 
	 * @return Retorna true si se abre correctamente
	 * @author MariaD1@hexaware.com
	 */
	public Boolean abrirExcel() {
		Boolean success = false;
		
		try {
			
			SikuliUtil sikuli = new SikuliUtil(); 
		    String s1 = sikuli.PatternPath("EBS"); 
		    Pattern p1 = sikuli.PatternImage(s1 + "OpenIE.png"); 
		      
		    if (sikuli.screen.exists(p1) != null) {
		    	sikuli.screen.wait(p1, 10);
			    sikuli.screen.click(p1);
			    testCase.addScreenShotCurrentStep("clickAbrirExcel");
			    success = true;
		    }else {
		    	System.out.println("El elemento no existe");
		    }
		    
		}catch(Exception e) {
			System.out.println("El elemento no existe -- " + e);
			testCase.addScreenShotCurrentStep("Error");
		}
		
		return success;
	}

}
