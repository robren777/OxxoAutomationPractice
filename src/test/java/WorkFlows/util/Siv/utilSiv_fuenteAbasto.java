package WorkFlows.util.Siv;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class utilSiv_fuenteAbasto {
	private pomSIV pom;

	// TABLA DE ITEMS
	By tablabuscaItem = By.xpath("//table[contains(@id,'tbl')]//tr//td[2]");
	By inputFuenteAbasto = By.cssSelector("th.tablatdth:nth-child(8) > input:nth-child(1)");

	/**
	 * Pantalla buscar fuente abasto
	 */
	By inputBuscaAbasto = By
			.cssSelector(".head-white > tbody:nth-child(2) > tr:nth-child(2) > td:nth-child(1) > input:nth-child(1)");
	By inputBuscaAbasto2 = By.cssSelector("input[name=\"btnclickBuscarFA\"]");
	By radioBtnAbastoReg = By.id("rdoModalFARegSelenium");
	By inputRazonSocial = By
			.xpath("/html/body/div[1]/div[2]/div/div[3]/div/div/div[2]/div[2]/div/table/tbody/tr[2]/td[1]/p/input");
	By inputBusqPredeter = By.cssSelector(
			".label-in-modal > tbody:nth-child(1) > tr:nth-child(4) > td:nth-child(2) > input:nth-child(1)");
	By tableRazonSocial = By.xpath("//table[contains(@name,'tblFuenteAbastosSelenium')]//tr//td[2]");
	By tableRazonSocial2 = By.cssSelector("table.table[name=\"tblFuenteAbastosSelenium\"] > tbody > tr");
	By inputAceptaFuenteAbasto = By.cssSelector(".modal-footer > button:nth-child(1)");
	By checkPlazaDestinos = By.id("chkPlazasDestinoDiponibles");

	/**
	 * Crear relaciones a partir de la fuente abasto seleccionada
	 */
	By tableFuenteAbasto = By.cssSelector(
			".head-white > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > table>tbody>tr>td:nth-child(3)");
	By tablePlazaDispDestino = By.cssSelector(
			".head-white > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(3) > div:nth-child(1)  table>tbody>tr>td:nth-child(2)");
	By inputCrearRelacion = By.id("btnCrearRelacionSelenium");

	By tableRelacionesCreadas = By.cssSelector("table.relacionesCreadas > tbody > tr");

	By inputConfigTecnica = By.id("btnConfigurarInfTecnicaSelenium");

	// Volumetria

	// EACH
	By eachAlto = By.id("txtvolEachAlto");
	By eachAncho = By.id("txtvolEachAncho");
	By eachFondo = By.id("txtvolEachFondo");
	By eachPesoNeto = By.id("txtvolEachPesoNeto");
	By eachPesoBruto = By.id("txtvolEachPesoBruto");
	// INNER
	By innerAlto = By.id("txtvolInnerAlto");
	By innerAncho = By.id("txtvolInnerAncho");
	By innerFondo = By.id("txtvolInnerFondo");
	By innerPesoNeto = By.id("txtvolInnerPesoNeto");
	By innerPesoBruto = By.id("txtvolInnerPesoBruto");
	By innerEach = By.id("txtvolInnerUnidadesVta");

	// CASE
	By caseAlto = By.id("txtvolCaseAlto");
	By caseAncho = By.id("txtvolCaseAncho");
	By caseFondo = By.id("txtvolCaseFondo");
	By casePesoNeto = By.id("txtvolCasePesoNeto");
	By casePesoBruto = By.id("txtvolCasePesoBruto");
	By caseEach = By.id("txtvolCaseUnidadesVta");

	By inputCama = By.id("txtvolEachtI");
	By inputAltura = By.id("txtvolEachhI");


	// TABLA --Fuentes de Abasto Configuradas en este Ciclo para el Articulo
	By tableFuenteAbasConfig = By.cssSelector("div.row-fluid:nth-child(2) > div:nth-child(1) > table");
	By inputCostoBrutoUni = By.cssSelector("td.text-center:nth-child(10) > input");
	By checkboxEncabezado = By.xpath("//table[contains(@class, 'relacionesCreadas')]//thead/tr/th/input[@type='checkbox']");
    
	By inputLigarDoc = By.id("btnCargaDocumentosSelenium");
	By examinaCartaCompr = By.id("fu_CartaCompromiso");
	By enviaCartaCompr = By.id("btnfu_CartaCompromisoSelenium");
	By aceptaAlertCartaCompro = By.xpath("/html/body/div[1]/div/div/div[3]/button");
	By examinaFichaTec = By.id("fu_FichaTecnica");
	By enviaFichaTec = By.id("btnfu_FichaTecnicaSelenium");
	By aceptaAlertFichaTec = By.cssSelector("div.noneSelect:nth-child(3) > button");

	By btnSalirLigarDoc = By.id("btnModalCargarDocumentosSalirSelenium");

	By btnGuardarInfoTec = By.id("btnGuardarInfoTecnicaSelenium");
	By aceptaGuardarInfoTec = By.xpath("/html/body/div[1]/div/div/div[3]/button");
	By btnSifuienteInfoTec = By.id("btnSigInfoTecnicaSelenium");

	// PARAMETROS DE INDICADORES
	By inputObjVenta = By.id("objetivoVentas");
	By inputVentMinimaExi = By.id("ventaMinExitosa");


	// Calculo de Pronostico de Ventas

	By btnBuscaArti = By.id("btnBuscarArticuloSelenium");
	// --> Busqueda de articulos
	By inputCB_PLU = By.id("cb_plu");
	By btnBusquedePrede = By.id("BtnBusquedaPredeterminadaSelenium");
	By radioBtnCB_PLU = By.cssSelector(
			"#tblArticulosSelenium > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(1) > input:nth-child(1)");
	By aceptarBuscaArti = By.id("btnDismissBAAceptar");

	By selectPlaza = By.id("plaza");
	By inputPorcentajeRepVenta = By.id("porcentajeVentas");
	By btnGuartarPronosVenta = By.id("btnGuardarInfoComercial");
	By btnSiguiente = By.cssSelector("input.btnSubGrupo:nth-child(2)");
	By btnSalirFuenteAbasto = By.cssSelector("input.btnSubGrupo[value=\"Salir\"]");
	By btnGuardarCatalogos = By.cssSelector("input[value=\"Guardar\"]");

	By btnAceptarAprob = By.cssSelector("input[value=\"Aceptar\"]");
	By btnRechazarApro = By.cssSelector("input[value=\"Rechazar\"]");
	By textareaComentario = By.cssSelector("textarea[ng-model=\"modal.comentarios.text\"]");
	By btnAceptarModalComment = By.cssSelector(
			"div.modal:nth-child(5) > div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > button:nth-child(1)");
	
	By checkAllFAPlaza = By.cssSelector("input[ng-model=\"chkGvFAConfAll\"]");
	By buttonAceptarRechazo = By.cssSelector("input[value=\"Aceptar Rechazo\"]");
	By buttonRegresar = By.cssSelector("input[value=\"Regresar\"]");

	private SeleniumUtil u;
	private TestCase testCase;
	private WebDriverWait wait;

	public utilSiv_fuenteAbasto(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.pom = new pomSIV(u, testCase);
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
	}

	/**
	 * Seleccina checkBox del item deseado
	 * 
	 * @param item
	 * @author alicia.ceja
	 */

	public void selectItem(String item) {
		By checkBoxItem = By.xpath("//span[text( )='" + item + "']//preceding::input[@name='chkboxSelenium'][1]");
		try {
			pom.waitLoadingModal();
			List<WebElement> elementsTable = u.getDriver().findElements(tablabuscaItem);
			pom.logM("Elementos en la Tabla: " + elementsTable.size());
			if (elementsTable.size() == 0) {
				u.hardWait(2);
			}
			for (WebElement ele : elementsTable) {
				String value = ele.getText();
				if (value.contains(item)) {
					u.highLight(checkBoxItem);
					u.click(checkBoxItem);
					u.highLight(inputFuenteAbasto);
					u.click(inputFuenteAbasto);
					u.hardWait(1);
					pom.waitLoadingModal();
					break;
				}
			}
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar el item: \"" + item);
		}

	}

	/**
	 * Ingresa el id de una razon social para realizar una busqueda predeterminada
	 * de N razones sociales
	 * 
	 * @author alicia.ceja
	 * 
	 */

	public void buscaRazonSocial(String razonSocial) {
		u.highLight(inputRazonSocial);
		clearElement(inputRazonSocial);
		u.sendKeys(inputRazonSocial, razonSocial);
		u.unhighLight(inputRazonSocial);
		u.hardWait(1);
		pom.waitLoadingModal();

		u.highLight(radioBtnAbastoReg);
		u.click(radioBtnAbastoReg);
		u.unhighLight(radioBtnAbastoReg);
		u.hardWait(1);
		pom.waitLoadingModal();

		u.highLight(inputBusqPredeter);
		u.click(inputBusqPredeter);
		u.unhighLight(inputBusqPredeter);
		u.hardWait(1);
		pom.waitLoadingModal();

	}

	public void buscarFuenteAbasto() {
		pom.waitLoadingModal();
		pom.clickOpcion("Buscar Fuente de Abasto", inputBuscaAbasto2);
		/*
		 * u.highLight(inputBuscaAbasto2); u.click(inputBuscaAbasto2); u.hardWait(1);
		 */
		pom.waitLoadingModal();
	}

	/**
	 * Selecciona las N razones sociales que el caso necesite
	 * 
	 * @author alicia.ceja
	 */

	public void selectRazonSocial(String buscaRazonSocial[]) {
		String razonDATA = "";
		try {
			u.hardWait(5);

			List<WebElement> elementsTable = u.getDriver().findElements(tableRazonSocial);
			pom.logM("Elementos en la Tabla RazonSocial: " + elementsTable.size());
			if (elementsTable.size() == 0) {
				u.hardWait(2);
			}
			for (int i = 0; i < buscaRazonSocial.length; i++) {
				razonDATA = buscaRazonSocial[i].trim();
				String razonS = "//table[contains(@name,'tblFuenteAbastosSelenium')]//tr//td[text( )=\"" + razonDATA
						+ "\"]";
				pom.logM("RazonS------>" + razonS);
				By razonSelec = By.xpath(razonS);
				WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(razonS)));
				scrollToElement(u.getDriver(), button);
				u.highLight(razonSelec);

				u.hardWait(1);
				Actions act = new Actions(u.getDriver());
				act.doubleClick(button).perform();
				u.hardWait(1);
				pom.waitLoadingModal();
			}

		} catch (Exception e) {

			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar la razon social: " + razonDATA);
		}

	}

	/**
	 * Selecciona una razon social de Fuente Abasto para habilitar las plazas
	 * disponibles
	 * 
	 * @param razonS
	 */
	public void seleccionaRadioFuenteAbasto(String razonS) {

		By checkBoxRazonS = By.xpath("//td[text() = '" + razonS + "']// preceding-sibling::td//input");
		try {
			List<WebElement> elementsTable = u.getDriver().findElements(tableFuenteAbasto);
			pom.logM("Elementos en la Tabla: " + elementsTable.size());
			if (elementsTable.size() == 0) {
				u.hardWait(2);
			}
			for (WebElement ele : elementsTable) {
				String value = ele.getText();
				if (value.contains(razonS)) {
					u.highLight(checkBoxRazonS);
					u.click(checkBoxRazonS);
					u.hardWait(1);
					pom.waitLoadingModal();
					if (pom.msgModal().contains("Error"))
						pom.optionMsgModal("OK");
					break;
				}
			}
		} catch (Exception e) {

			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar fuente abasto");
		}

	}
	
	/**
	 * Selecciona una razon social de Fuente Abasto para habilitar las plazas
	 * disponibles
	 * Se agrega funcionalidad:
	 * Metodo para seleccionar razon social
	 * @param razonS
	 */
	public void seleccionaRadioFuenteAbasto2(String razonS) {
		String razon[] = razonS.split(":");
		By checkBoxRazonS = By.xpath("//td[text() = '" + razon[1] + "']// preceding-sibling::td//input");
		try {
			u.hardWait(5);
			List<WebElement> elementsTable = u.getDriver().findElements(tableFuenteAbasto);
			pom.logM("Elementos en la Tabla: " + elementsTable.size());
			if (elementsTable.size() == 0) {
				u.hardWait(2);
			}
			for (WebElement ele : elementsTable) {
				String value = ele.getText();
				if (value.contains(razon[1])) {
					u.highLight(checkBoxRazonS);
					u.click(checkBoxRazonS);
					u.hardWait(1);
					pom.waitLoadingModal();
					if (pom.msgModal().contains("Error"))
						pom.optionMsgModal("OK");
					break;
				}
			}
		} catch (Exception e) {
 
			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar fuente abasto");
		}
 
	}

	/**
	 * Selecciona las n plazas destino que se necesiten para crear la relacion con
	 * fuente de abasto
	 * 
	 * @param plazaDestino
	 */
	public void selectPlazaDestino(String plazaDestino[]) {

		try {

			for (int i = 0; i < plazaDestino.length; i++) {
				String plazaDestinoDisp = plazaDestino[i].trim();
				String plazaDestinoPath = "//td[text( )=\"" + plazaDestinoDisp + "\"]";
				By checkBoxPlaza = By.xpath("//td[text( )=\"" + plazaDestinoDisp + "\"]//preceding-sibling::td//input");
				By plazaSelec = By.xpath(plazaDestinoPath);
				if (plazaDestino.length > 3) {
					WebElement button = wait
							.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(plazaDestinoPath)));
					scrollToElement(u.getDriver(), button);
				}
				u.click(checkBoxPlaza);
				u.hardWait(1);
				crearRelacion();
				if (pom.msgModal().contains("ya tiene(n) una relacion")) {
					pom.optionMsgModal("OK");
				}
				u.click(checkBoxPlaza);
			}
		} catch (Exception e) {
			pom.logL(" " + e);
			throw new AssertionError("Fallo al seleccionar las plazas disponibles");
		}
	}

	/**
	 * Llena los campos de inner (alto, ancho, fondo, peso neto, peso bruto
	 * 
	 * @param vol_each
	 * @author alicia.ceja
	 */
	public void vol_each(String vol_each[]) {
		pom.logL("Aqui---> " + vol_each[0]);
		pom.waitLoadingModal();
		u.hardWait(3);
		if (u.getDriver().findElement(eachAlto).isEnabled()) {
			u.highLight(eachAlto);
			clearElement(eachAlto);
			u.sendKeys(eachAlto, vol_each[0]);
			u.unhighLight(eachAlto);
		}

		if (u.getDriver().findElement(eachAncho).isEnabled()) {
			u.highLight(eachAncho);
			clearElement(eachAncho);
			u.sendKeys(eachAncho, vol_each[1]);
			u.unhighLight(eachAncho);
		}

		if (u.getDriver().findElement(eachFondo).isEnabled()) {
			u.highLight(eachFondo);
			clearElement(eachFondo);
			u.sendKeys(eachFondo, vol_each[2]);
			u.unhighLight(eachFondo);
		}

		if (u.getDriver().findElement(eachPesoNeto).isEnabled()) {
			u.highLight(eachPesoNeto);
			clearElement(eachPesoNeto);
			u.sendKeys(eachPesoNeto, vol_each[3]);
			u.unhighLight(eachPesoNeto);

		}

		if (u.getDriver().findElement(eachPesoBruto).isEnabled()) {
			u.highLight(eachPesoBruto);
			clearElement(eachPesoBruto);
			u.sendKeys(eachPesoBruto, vol_each[4]);
			u.unhighLight(eachPesoBruto);
		}

	}

	public void vol_inner(String vol_inner[]) {
		pom.logL("Aqui---> " + vol_inner[0]);

		if (u.getDriver().findElement(innerAlto).isEnabled()) {
			u.highLight(innerAlto);
			clearElement(innerAlto);
			u.sendKeys(innerAlto, vol_inner[0]);
			u.unhighLight(innerAlto);
		}

		if (u.getDriver().findElement(innerAncho).isEnabled()) {
			u.highLight(innerAncho);
			clearElement(innerAncho);
			u.sendKeys(innerAncho, vol_inner[1]);
			u.unhighLight(innerAncho);
		}

		if (u.getDriver().findElement(innerFondo).isEnabled()) {
			u.highLight(innerFondo);
			clearElement(innerFondo);
			u.sendKeys(innerFondo, vol_inner[2]);
			u.unhighLight(innerFondo);
		}

		if (u.getDriver().findElement(innerPesoNeto).isEnabled()) {
			u.highLight(innerPesoNeto);
			clearElement(innerPesoNeto);
			u.sendKeys(innerPesoNeto, vol_inner[3]);
			u.unhighLight(innerPesoNeto);
		}

		if (u.getDriver().findElement(innerPesoBruto).isEnabled()) {
			u.highLight(innerPesoBruto);
			clearElement(innerPesoBruto);
			u.sendKeys(innerPesoBruto, vol_inner[4]);
			u.unhighLight(innerPesoBruto);
		}

		if (u.getDriver().findElement(innerEach).isEnabled()) {
			u.highLight(innerEach);
			clearElement(innerEach);
			u.sendKeys(innerEach, vol_inner[5]);
			u.unhighLight(innerEach);
		}

	}

	public void vol_case(String vol_case[]) {
		pom.logL("Aqui---> " + vol_case[0]);

		if (u.getDriver().findElement(caseAlto).isEnabled()) {
			u.highLight(caseAlto);
			clearElement(caseAlto);
			u.sendKeys(caseAlto, vol_case[0]);
			u.unhighLight(caseAlto);
		}

		if (u.getDriver().findElement(caseAncho).isEnabled()) {
			u.highLight(caseAncho);
			clearElement(caseAncho);
			u.sendKeys(caseAncho, vol_case[1]);
			u.unhighLight(caseAncho);
		}

		if (u.getDriver().findElement(caseFondo).isEnabled()) {
			u.highLight(caseFondo);
			clearElement(caseFondo);
			u.sendKeys(caseFondo, vol_case[2]);
			u.unhighLight(caseFondo);
		}

		if (u.getDriver().findElement(casePesoNeto).isEnabled()) {
			u.highLight(casePesoNeto);
			clearElement(casePesoNeto);
			u.sendKeys(casePesoNeto, vol_case[3]);
			u.unhighLight(casePesoNeto);
		}

		if (u.getDriver().findElement(casePesoBruto).isEnabled()) {
			u.highLight(casePesoBruto);
			clearElement(casePesoBruto);
			u.sendKeys(casePesoBruto, vol_case[4]);
			u.unhighLight(casePesoBruto);
		}

		if (u.getDriver().findElement(caseEach).isEnabled()) {
			u.highLight(caseEach);
			clearElement(caseEach);
			u.sendKeys(caseEach, vol_case[5]);
			u.unhighLight(caseEach);
		}

	}

	public void tarima(String cama, String altura) {
		if (u.getDriver().findElement(inputCama).isEnabled()) {
			u.highLight(inputCama);
			clearElement(inputCama);
			u.sendKeys(inputCama, cama);
			u.unhighLight(inputCama);
		}

		if (u.getDriver().findElement(inputAltura).isEnabled()) {
			u.highLight(inputAltura);
			clearElement(inputAltura);
			u.sendKeys(inputAltura, altura);
			u.unhighLight(inputAltura);
		}

	}

	/**
	 * Ingresa el costo bruto unitario en la tabla Fuentes de abasto configuradas y
	 * da click en el boton ligar documentos
	 * 
	 * @param costroBruto
	 */

	public void abastoConfigArticulo(String costroBruto) {
		WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(tableFuenteAbasConfig));
		scrollToElement(u.getDriver(), table);

		u.highLight(inputCostoBrutoUni);
		clearElement(inputCostoBrutoUni);
		u.sendKeys(inputCostoBrutoUni, costroBruto);
		u.hardWait(1);
		pom.waitLoadingModal();
		testCase.addScreenShotCurrentStep(u, "Costo bruto");
		u.unhighLight(inputCostoBrutoUni);

		WebElement inputLigaDoc = wait.until(ExpectedConditions.visibilityOfElementLocated(inputLigarDoc));
		scrollToElement(u.getDriver(), inputLigaDoc);
		u.highLight(inputLigarDoc);
		testCase.addScreenShotCurrentStep(u, "Ligar Documentos");
		u.unhighLight(inputLigarDoc);
		u.click(inputLigarDoc);
		u.hardWait(1);
		pom.waitLoadingModal();

	}

	/**
	 * Sube los archivos de carta compromiso y ficha tecnica, guarda la informacion
	 * para continuar con el proceso
	 * 
	 * @author alicia.ceja
	 * @param cartaCompro
	 * @param fichaTec
	 */
	public void subirArchivos(String cartaCompro, String fichaTec) {

		u.highLight(examinaCartaCompr);
		u.sendKeys(examinaCartaCompr, cartaCompro);
		u.unhighLight(examinaCartaCompr);
		u.click(enviaCartaCompr);
		u.hardWait(1);
		pom.waitLoadingModal();

		u.click(aceptaAlertCartaCompro);

		u.hardWait(1);
		pom.waitLoadingModal();
		u.highLight(examinaFichaTec);
		u.sendKeys(examinaFichaTec, fichaTec);
		u.unhighLight(examinaFichaTec);
		u.click(enviaFichaTec);
		u.hardWait(1);
		pom.waitLoadingModal();

		u.click(aceptaAlertFichaTec);
		u.hardWait(2);

		pom.clickOpcion("Salir de Subir Archivos", btnSalirLigarDoc);
		/*
		 * u.click(btnSalirLigarDoc); u.hardWait(1); pom.waitLoadingModal();
		 */
		testCase.addTextEvidenceCurrentStep("Guardar Configuracion Tecnica");
		pom.clickOpcion("Guardar Config Tectica", btnGuardarInfoTec);

		/*
		 * u.click(btnGuardarInfoTec); u.hardWait(1); pom.waitLoadingModal();
		 */

		pom.clickOpcion("Aceptar Guardar Conf Tec", aceptaGuardarInfoTec);
		/*
		 * u.click(aceptaGuardarInfoTec); u.hardWait(1); pom.waitLoadingModal();
		 */

		pom.clickOpcion("Siguiente Conf Tec", btnSifuienteInfoTec);
		/*
		 * u.click(btnSifuienteInfoTec); u.hardWait(1); pom.waitLoadingModal();
		 */

	}

	/**
	 * Ingresa los parametros de indicadores --> obketivo de venta --> Ventas
	 * minimas exitosas
	 * 
	 * @author alicia.ceja
	 * @param objVenta
	 * @param ventMinEx
	 */

	public void paramIndicadores(String objVenta, String ventMinEx) {
		u.highLight(inputObjVenta);
		clearElement(inputObjVenta);
		u.sendKeys(inputObjVenta, objVenta);

		u.highLight(inputVentMinimaExi);
		clearElement(inputVentMinimaExi);
		u.sendKeys(inputVentMinimaExi, ventMinEx);
		testCase.addScreenShotCurrentStep(u, "Parametros de indicadores");
		u.unhighLight(inputObjVenta);
		u.unhighLight(inputVentMinimaExi);

	}

	/**
	 * Realiza busqueda de articulos con CB o PLU
	 * 
	 * @param CBPLU
	 */
	public void buscaArticulo(String CBPLU) {
		pom.clickOpcion("Buscar Articulo", btnBuscaArti);

		u.highLight(inputCB_PLU);
		u.sendKeys(inputCB_PLU, CBPLU);

		u.click(btnBusquedePrede);
		u.hardWait(1);
		pom.waitLoadingModal();

		By checkBoxArti = By.xpath("//td[text( )='" + CBPLU + "']//preceding::input[@name='rdbSelect'][1]");

		u.click(checkBoxArti);
		u.hardWait(1);
		pom.waitLoadingModal();

		u.highLight(aceptarBuscaArti);
		testCase.addScreenShotCurrentStep(u, "Articulo espejo");
		u.unhighLight(aceptarBuscaArti);
		u.click(aceptarBuscaArti);
		u.hardWait(1);
		pom.waitLoadingModal();
	}

	/**
	 * Selecciona una plaza e ingresa el porcentaje que representa la venta
	 * 
	 * @author alicia.ceja
	 * @param plaza
	 * @param porcentaje
	 */

	public void calPronostVenta(String plaza, String porcentaje) {

		Select dropdownSelecTipoArchivo = new Select(u.getDriver().findElement((selectPlaza)));
		dropdownSelecTipoArchivo.selectByVisibleText(plaza);

		u.highLight(inputPorcentajeRepVenta);
		u.sendKeys(inputPorcentajeRepVenta, porcentaje);
		u.unhighLight(inputPorcentajeRepVenta);
		testCase.addScreenShotCurrentStep(u, "Pronostico de Ventas");
		testCase.addTextEvidenceCurrentStep("Guardar Parametros");
		pom.scrollToElement(btnGuartarPronosVenta);
		u.highLight(btnGuartarPronosVenta);
		testCase.addScreenShotCurrentStep(u, "Guardar Parametros");
		u.click(btnGuartarPronosVenta);
		u.hardWait(1);
		if (!pom.msgModal().contains("guardada correctamente")) {
			throw new AssertionError("No se pudo guardar la informacion");
		}
		u.hardWait(1);
		pom.waitLoadingModal();

		By btnAceptaGuardarPronost = By.cssSelector("div.noneSelect:nth-child(3) > button");
		u.highLight(btnAceptaGuardarPronost);
		testCase.addScreenShotCurrentStep(u, "Aceptar Guardar");
		u.unhighLight(btnAceptaGuardarPronost);
		u.click(btnAceptaGuardarPronost);
		u.hardWait(1);
		pom.waitLoadingModal();

		u.highLight(btnSiguiente);
		pom.scrollToElement(btnSiguiente);
		testCase.addScreenShotCurrentStep(u, "Siguiente FA");
		u.unhighLight(btnSiguiente);
		u.click(btnSiguiente);
		u.hardWait(1);
		pom.waitLoadingModal();

		testCase.addTextEvidenceCurrentStep("Salir de Fuente de Abasto");
		u.hardWait(3);
		wait.until(ExpectedConditions.elementToBeClickable(btnSalirFuenteAbasto));
		scrollToElement(u.getDriver(), u.getDriver().findElement(btnSalirFuenteAbasto));
		u.hardWait(1);
		u.highLight(btnSalirFuenteAbasto);
		pom.scrollToElement(btnSalirFuenteAbasto);
		testCase.addScreenShotCurrentStep(u, "Salir de FA");
		u.unhighLight(btnSalirFuenteAbasto);
		u.click(btnSalirFuenteAbasto);
		u.hardWait(1);
		pom.waitLoadingModal();

	}

	/**
	 * Elimina valores predeterminados en inputs
	 * 
	 * @author alicia.ceja
	 * @param element
	 */
	public void clearElement(By element) {
		WebElement clear = u.getDriver().findElement(element);
		clear.sendKeys(Keys.CONTROL, "a");
		clear.sendKeys(Keys.DELETE);

	}

	/**
	 * Scroll para encontrar elementos
	 * 
	 * @param driver
	 * @param element
	 */

	public static void scrollToElement(WebDriver driver, WebElement element) {
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
		javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	/**
	 * ---------------------------------------------------------------------------------------------------------------------
	 */

	public void seleccionarFuentesAbasto(String buscaRazonSocial[]) {
		try {
			for (int i = 0; i < buscaRazonSocial.length; i++) {
				String razonDATA = buscaRazonSocial[i];
				String razonS = "//table[contains(@name,'tblFuenteAbastosSelenium')]//tr//td[text( )=\"" + razonDATA
						+ "\"]";
				By razonSelec = By.xpath(razonS);

				WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(razonS)));
				scrollToElement(u.getDriver(), button);
				u.highLight(razonSelec);

				u.hardWait(1);
				Actions act = new Actions(u.getDriver());
				act.doubleClick(button).perform();
				u.hardWait(1);
				pom.waitLoadingModal();

			}
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar las Fuentes de Abasto");
		}
	}

	public void aceptarFuenteAbasto() {
		pom.clickOpcion("Aceptar Fuentes de Abasto", inputAceptaFuenteAbasto);
	}

	private void buscarFuentesAbasto_opcion(String razonSocial) {
		try {
			pom.waitLoadingModal();
			List<WebElement> elementsTable = u.getDriver().findElements(tableRazonSocial2);
			pom.logM("Elementos en la Tabla RazonSocial2: " + elementsTable.size());
			if (elementsTable.size() == 0) {
				u.hardWait(2);
			}
			By razonS;
			String innetElement;
			for (int i = 0; i < elementsTable.size(); i++) {
				innetElement = elementsTable.get(i).getAttribute("innerText");
				pom.logM("Inner " + i + " ------>" + innetElement);
				if (innetElement.contains(razonSocial)) {
					pom.logM("Encontro coincidencia");
					razonS = By.cssSelector(
							"table.table[name=\"tblFuenteAbastosSelenium\"] > tbody > tr:nth-child(" + (i + 1) + ")");
					scrollToElement(u.getDriver(), u.getDriver().findElement(razonS));
					u.highLight(razonS);
					u.hardWait(1);
					Actions act = new Actions(u.getDriver());
					act.doubleClick(u.getDriver().findElement(razonS)).perform();
					u.hardWait(1);
					pom.waitLoadingModal();
					break;
				}
			}
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al buscar la Fuente de Abasto");
		}
	}
	
	public void checkTodasPlazasDestino(Boolean opcion) {
		try {
			pom.waitLoadingModal();
			u.click(checkPlazaDestinos);
			pom.waitLoadingModal();

			if (opcion && !u.getDriver().findElement(checkPlazaDestinos).isSelected()) {
				u.click(checkPlazaDestinos);
				u.hardWait(1);
				pom.waitLoadingModal();
			} else if (!opcion && u.getDriver().findElement(checkPlazaDestinos).isSelected()) {
				u.click(checkPlazaDestinos);
				u.hardWait(1);
				pom.waitLoadingModal();
			}
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar o desceleccionar el check de todas las plazas disponibles");
		}
	}

	public void quitarPlazasYaRelacionadas() {
		try {
			u.hardWait(1);
			pom.waitLoadingModal();
			By tablaPlazasDestinos = By.cssSelector("table.table > tbody[ng-show*=\"gvPlazasDestino\"] > tr");
			List<WebElement> elementsTable = u.getDriver().findElements(tablaPlazasDestinos);
			pom.logM("Elementos en la Tabla Plaza-R: " + elementsTable.size());
			if (elementsTable.size() == 0) {
				u.hardWait(2);
			}
			List<WebElement> plazaRelacionada;
			By checkElement;
			for (int i = 0; i < elementsTable.size(); i++) {
				plazaRelacionada = u.getDriver()
						.findElements(By.cssSelector("table.table > tbody[ng-show*=\"gvPlazasDestino\"] > tr:nth-child("
								+ (i + 1) + ") > td.faConfigurada2:nth-child(1)"));
				if (plazaRelacionada.size() > 0) {
					checkElement = By.cssSelector("table.table > tbody[ng-show*=\"gvPlazasDestino\"] > tr:nth-child("
							+ (i + 1) + ") > td.faConfigurada2:nth-child(1) > input");
					if (u.getDriver().findElement(checkElement).isSelected()) {
						if (elementsTable.size() > 3) {
							WebElement element = u.getDriver().findElement(checkElement);
							Coordinates coordinate = ((Locatable) element).getCoordinates();
							coordinate.onPage();
							coordinate.inViewPort();
						}
						u.highLight(checkElement);
						u.unhighLight(checkElement);
						u.click(checkElement);
					}
				}
			}
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al deseleccionar las plazas disponibles que ya tienen relacion");
		}
	}

	public void crearRelacion() {
		pom.clickOpcion("Crear Relacion", inputCrearRelacion);
	}

	public void configTecnica() {
		pom.clickOpcion("Configuracion Tecnica", inputConfigTecnica);
		pom.waitLoadingModal();
	}

	public void seleccionarFuentesAbasto(String arrayFuentesAbasto) {
		try {
			pom.logM("ArrayEntrada: " + arrayFuentesAbasto);
			String[] elements = arrayFuentesAbasto.split("\n");
			for (int i = 0; i < elements.length; i++) {
				String[] element = elements[i].split(":");
				String razonSocial = element[0].trim();
				String[] fuentesAbasto = element[1].split(",");
				pom.logM("RZ: " + razonSocial);
				buscaRazonSocial(razonSocial);
				selectRazonSocial(fuentesAbasto);
			}
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar las fuentes de abasto");
		}
	}

	public void relacionarPlazas(String arrayPlazas) {
		try {
			pom.logM("ArrayEntradaPlazas: " + arrayPlazas);
			String[] elements = arrayPlazas.split("\n");
			for (int i = 0; i < elements.length; i++) {
				String[] element = elements[i].split(":");
				String fuenteAbasto = element[0].trim();
				String[] plazas = element[1].split(",");
				pom.logM("FuenteAbasto: " + fuenteAbasto);
				seleccionaRadioFuenteAbasto(fuenteAbasto);
				checkTodasPlazasDestino(false);
				selectPlazaDestino(plazas);

			}
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar las plazas");
		}
	}

	public void guardarCatalogos() {
		pom.clickOpcion("Guardar Fuente de Abasto", btnGuardarCatalogos);
		pom.optionMsgModal("SI RECHAZO");
		if (pom.msgModal().contains("un error")) {
			throw new AssertionError("Fallo al guardar la FA");
		} else {
			pom.optionMsgModal("OK");
		}
		
	}

	public void AceptarAprobacion() {
		pom.clickOpcion("Aceptar Aprobacion", btnAceptarAprob);
		pom.waitLoadingModal();
	}

	public void agregarComentario(String comentario) {
		try {
			WebElement textArea = u.getDriver().findElement(textareaComentario);
			textArea.click();
			textArea.sendKeys(Keys.TAB);
			textArea.clear();
			textArea.sendKeys(comentario);
			u.hardWait(1);
			pom.clickOpcion("Aceptar Comentario Aprobacion", btnAceptarModalComment);
			pom.optionMsgModal("OK");
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al ingresar el comentario");
		}
	}

	public void RechazarAprobacion() {
		pom.clickOpcion("Rechazar Aprobacion", btnRechazarApro);
		pom.waitLoadingModal();
	}

	public Integer conteoRelaciones() {
		Integer conteo = 0;
		try {
			pom.waitLoadingModal();
			List<WebElement> elementsTable = u.getDriver().findElements(tableRelacionesCreadas);
			pom.logM("Elementos en la Tabla Relaciones Creadas: " + elementsTable.size());
			conteo = elementsTable.size();
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al buscar tabla de relaciones creadas");
		}
		return conteo;
	}
	
	public void checkTodosFuenteAbastoValidacionPlaza(Boolean opcion) {
		try {
			u.hardWait(2);
			pom.waitLoadingModal();
			if (u.getDriver().findElement(checkAllFAPlaza).isEnabled()) {
				if (opcion && !u.getDriver().findElement(checkAllFAPlaza).isSelected()) {
					u.click(checkAllFAPlaza);
				} else if (!opcion && u.getDriver().findElement(checkAllFAPlaza).isSelected()) {
					pom.logM("VAMOS A DAR CLICK");
					u.click(checkAllFAPlaza);
				}
				pom.waitLoadingModal();
			}
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar: \"" + opcion
					+ "\" en el checkBox de Todos en validacion de Fuente de Abastos");
		}
	}
	
	public void aceptarRechazo() {
		pom.clickOpcion("Aceptar Rechazo FA", buttonAceptarRechazo);
		if (pom.msgModal().contains("desea Aceptar Rechazo")) {
			pom.optionMsgModal("SI RECHAZO");
			if (pom.msgModal().contains("El rechazo se realizo con exito")) {
				pom.optionMsgModal("OK");
			} else {
				throw new AssertionError("No se acepto el rechazo en FA");
			}
		}
	}
	
	public void regresar() {
		pom.clickOpcion("Regresar de FA", buttonRegresar);
	}

	public void quitarDuplicidadRelaciones(String arrayFuentesAbasto) {
		try {
			pom.logM("ArrayEntrada: " + arrayFuentesAbasto);
			String[] elements = arrayFuentesAbasto.split("\n");
			for (int i = 0; i < elements.length; i++) {
				String[] element = elements[i].split(":");
				String[] fuentesAbasto = element[1].split(",");
				pom.logM("FAs: " + fuentesAbasto);
				try {
					List<WebElement> elementsTable = u.getDriver().findElements(tableRelacionesCreadas);
					pom.logM("Elementos en la Tabla Relaciones Creadas: " + elementsTable.size());
					for (int j = 0; j < fuentesAbasto.length; j++) {
						String FA = fuentesAbasto[j].trim();
						String innetElement;
						Integer conteo = 0;
						String indexFind = "";
						for (int y = 0; y < elementsTable.size(); y++) {
							innetElement = elementsTable.get(y).getAttribute("innerText");
							pom.logM("Inner:" + y + " - " + innetElement);
							if (innetElement.contains(FA)) {
								pom.logM("Encontro coincidencia");
								conteo++;
								indexFind = indexFind + (y + 1) + ",";
							}
						}
						if (conteo > 1) {
							pom.logL("Hay duplicados para: " + FA + " En: " + indexFind);
							String[] index = indexFind.split(",");
							for (int z = 0; z < index.length; z++) {
								pom.logM("Revizando index: " + (Integer.parseInt(index[z]) - 1));
								pom.logM("Clase: "
										+ elementsTable.get(Integer.parseInt(index[z]) - 1).getAttribute("class"));
								if (!elementsTable.get(Integer.parseInt(index[z]) - 1).getAttribute("class")
										.contains("cedis")) {
									By checkRC = By.cssSelector("table.relacionesCreadas > tbody > tr:nth-child("
											+ index[z] + ") > td:nth-child(1) > input");
									pom.clickOpcion("Quitar relacion duplicada", checkRC);
								}
							}
						}
					}
				} catch (Exception e) {
					pom.logL("" + e);
					throw new AssertionError("Fallo al buscar y quitar las relaciones duplicadas");
				}
			}
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al obtener las FA");
		}
	}

	public void seleccionarRelacion(int index) {
		By checkRC = By
				.cssSelector("table.relacionesCreadas > tbody > tr:nth-child(" + index + ") > td:nth-child(1) > input");
		pom.logM("Class de checkRC: " + u.getDriver().findElement(checkRC).getAttribute("class"));
		if(u.getDriver().findElement(checkRC).getAttribute("class").contains("ng-empty")) {
			pom.logM("Click a la relacion");
			pom.clickOpcion("Seleccionar relacion", checkRC);
		}
//		pom.clickOpcion("Seleccionar relacion", checkRC);
	}
	
	/**
	* Selecciona o deselecciona el checkbox del encabezado de la tabla de Relacioines Creadas
	*
	* @param seleccionar Si es true, selecciona el checkbox; si es false, lo deselecciona.
	* @author Isaac E. García
	* @throws NoSuchElementException si el checkbox no se encuentra.
	*/
	public void seleccionarCheckboxEncabezado(boolean seleccionar) {
	    WebElement checkbox = u.getDriver().findElement(checkboxEncabezado);
	    boolean estaSeleccionado = checkbox.isSelected();
	    if (estaSeleccionado != seleccionar) {
	    	checkbox.click();
	    }
	}
}