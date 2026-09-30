package WEB.util.GDP_Abastos;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import modelo.TestCase;
import WEB.util.AuxMethods;
import utils.selenium.SeleniumUtil;

public class utilGDP_Abastos {
	private SeleniumUtil u;
	private AuxMethods aux;
	private TestCase testCase;

	/// WEB ELEMENTS ///
	By portalGestionProveedores = By.linkText("Gesti�n de Proveedores");
	By menuAbasto = By.cssSelector("#ddtopmenubar > ul > li > a[rel=\"ddsubmenu8894\"]");
	By opcionReportesOrdenesCompra = By.id("j_idt106");
	By opcionInventarioExistente = By.id("j_idt112");
	By opcionInventarioExcedente = By.id("j_idt116");
	By opcionNivelesServicioCedis = By.id("j_idt120");
	By opcionReporteDevoluciones = By.id("j_idt128");

	By frameAvPo = By.cssSelector("#contentFrame > iframe");

	By fechaIni = By.cssSelector("input[id=\"purchase-order-report-form:start-date_input\"]");
	By fechaFin = By.cssSelector("input[id=\"purchase-order-report-form:final-date_input\"]");
	By buttonLupa = By.cssSelector("img[id=\"purchase-order-report-form:j_idt44\"]");
	By buttonLupaRexistente = By.cssSelector("img[id=\"purchase-order-report-form:j_idt29\"]");
	By buttonLupaRNiveles = By.cssSelector("img[id=\"purchase-order-report-form:j_idt34\"]");
	By buttonLupaRDevoluciones = By.cssSelector("img[id=\"return-report-form:j_idt38\"]");

	By buttonBuscarReporte = By.cssSelector("button[id=\"purchase-order-report-form:j_idt65\"]");
	By buttonBuscarReporteInvExist = By.cssSelector("button[id=\"purchase-order-report-form:j_idt51\"]");
	By buttonBuscarReporteNiveles = By.cssSelector("button[id=\"purchase-order-report-form:j_idt56\"]");
	By buttonBuscarReporteDevoluciones = By.cssSelector("button[id=\"return-report-form:j_idt64\"]");
	By tableCedis = By.cssSelector("tbody[id=\"purchase-order-report-form:table-by-cedis_data\"] > tr");

	By paginator = By.cssSelector(".ui-paginator-bottom");
	By pagNext = By.cssSelector(".ui-paginator-next");

	By downloadExcel = By.cssSelector("img[src*=\"Excel\"]");
	By downloadPDF = By.cssSelector("img[src*=\"adobe\"]");

	By inputNombreRFC = By.cssSelector("input[id=\"purchase-order-report-form:vendor-name\"]");
	By inputNombreRFCDevoluciones = By.cssSelector("input[id=\"return-report-form:vendor-name\"]");
	By buttonBuscarRFC = By.cssSelector("button[id=\"purchase-order-report-form:j_idt53\"]");
	By buttonBuscarRFCInvExist = By.cssSelector("button[id=\"purchase-order-report-form:j_idt38\"]");
	By buttonBuscarRFCNiveles = By.cssSelector("button[id=\"purchase-order-report-form:j_idt43\"]");
	By buttonBuscarRFCDevoluciones = By.cssSelector("button[id=\"return-report-form:j_idt47\"]");
	By tableProveedores = By.cssSelector(
			"div[id=\"purchase-order-report-form:table-proveedores\"] > table > tbody[id=\"purchase-order-report-form:table-proveedores_data\"] > tr");
	By buttonAceptarRFC = By.cssSelector("button[id=\"purchase-order-report-form:j_idt61\"]");
	By buttonAceptarRFCInvExist = By.cssSelector("button[id=\"purchase-order-report-form:j_idt46\"]");
	By buttonAceptarRFCNiveles = By.cssSelector("button[id=\"purchase-order-report-form:j_idt51\"]");
	By buttonAceptarRFCDevoluciones = By.cssSelector("button[id=\"return-report-form:j_idt55\"]");

	By labelAnio = By.cssSelector("label[id=\"purchase-order-report-form:year_label\"]");
	By labelMes = By.cssSelector("label[id=\"return-report-form:return-month_label\"]");

	/**
	 * Constructor. Recibe el SeleniumUtil y el testCase utilizado en el test.
	 * 
	 * @param SeleniumUtil
	 * @param testCase
	 * @author Esmeralda Garcia Torres
	 */
	public utilGDP_Abastos(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		aux = new AuxMethods(u, testCase);
	}

	/**
	 * Selecciona el portal de Gestion de Proveedores
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void goToPortalGestionProveedores() {
		aux.clickOpcion("Gestion de Proveedores", portalGestionProveedores, true);
		u.hardWait(2);
		// testCase.addScreenShotCurrentStep(u, "Portal");
	}

	/**
	 * Selecciona el menu "Abasto" y el submenu "Reporte de Ordenes de Compra"
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void goToReportesOrdenesCompra() {
		try {
			WebElement ele = u.getDriver().findElement(menuAbasto);
			Actions action = new Actions(u.getDriver());
			action.moveToElement(ele).perform();
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al poner el mouse sobre el menu");
		}
		aux.clickOpcion("Ordenes Compra", opcionReportesOrdenesCompra, true);
		u.hardWait(1);
	}

	/**
	 * Selecciona el menu "Abasto" y el submenu "Inventario Existente"
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void goToInventarioExistente() {
		try {
			WebElement ele = u.getDriver().findElement(menuAbasto);
			Actions action = new Actions(u.getDriver());
			action.moveToElement(ele).perform();
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al poner el mouse sobre el menu");
		}
		aux.clickOpcion("InventarioExistente", opcionInventarioExistente, true);
		u.hardWait(1);
	}

	/**
	 * Selecciona el menu "Abasto" y el submenu "Inventario Excedente"
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void goToInventarioExcedente() {
		try {
			WebElement ele = u.getDriver().findElement(menuAbasto);
			Actions action = new Actions(u.getDriver());
			action.moveToElement(ele).perform();
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al poner el mouse sobre el menu");
		}
		aux.clickOpcion("InventarioExcedente", opcionInventarioExcedente, true);
		u.hardWait(1);
	}

	/**
	 * Selecciona el menu "Abasto" y el submenu "Niveles de Servicio CEDIS"
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void goToNivelesServicioCedis() {
		try {
			WebElement ele = u.getDriver().findElement(menuAbasto);
			Actions action = new Actions(u.getDriver());
			action.moveToElement(ele).perform();
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al poner el mouse sobre el menu");
		}
		aux.clickOpcion("InventarioExcedente", opcionNivelesServicioCedis, true);
		u.hardWait(1);
	}

	/**
	 * Selecciona el menu "Abasto" y el submenu "Reporte de Devoluciones"
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void goToReporteDevoluciones() {
		try {
			WebElement ele = u.getDriver().findElement(menuAbasto);
			Actions action = new Actions(u.getDriver());
			action.moveToElement(ele).perform();
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al poner el mouse sobre el menu");
		}
		aux.clickOpcion("Reporte Devoluciones", opcionReporteDevoluciones, true);
		u.hardWait(1);
	}

	/**
	 * Selecciona el a�o
	 * 
	 * @param year
	 * @author Esmeralda Garcia Torres
	 */
	public void ingresarAnio(String year) {
		try {
			aux.clickOpcion("Label Anio", labelAnio, false);
			By opAnio = By.xpath("//li[text()=\"" + year + "\"]");
			aux.clickOpcion(year, opAnio, true);
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al ingresar el a�o");
		}
	}

	/**
	 * Ingresa las fechas
	 * 
	 * @param fechaInicio
	 * @param fechaFinal
	 * @author Esmeralda Garcia Torres
	 */
	public void ingresarFechas(String fechaInicio, String fechaFinal) {
		try {
			aux.changeAttributeValue(fechaIni, fechaInicio, false);
			aux.changeAttributeValue(fechaFin, fechaFinal, false);
			u.hardWait(1);
			testCase.addScreenShotCurrentStep(u, "Fechas");
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al ingresar las fechas");
		}
	}

	/**
	 * Selecciona el mes
	 * 
	 * @param mes
	 * @author Esmeralda Garcia Torres
	 */
	public void ingresarMes(String mes) {
		try {
			aux.clickOpcion("Label mes", labelMes, false);
			By opMes = By.xpath("//li[text()=\"" + mes + "\"]");
			aux.clickOpcion(mes, opMes, true);
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al ingresar el mes");
		}
	}

	/**
	 * Selecciona el RFC dependiendo de la pagina en la que se encuentre: Ordenes,
	 * Existente, Excedente, Niveles y Devoluciones.
	 * 
	 * @param rfc
	 * @param vista
	 * @author Esmeralda Garcia Torres
	 */
	public void seleccionarRFC(String rfc, String vista) {
		By lupa = null;
		By nombreRFC = inputNombreRFC;
		By buscarRFC = null;
		By aceptarRFC = null;
		switch (vista.toUpperCase()) {
		case "ORDENES":
			aux.logS("Ordenes de Compra");
			lupa = buttonLupa;
			buscarRFC = buttonBuscarRFC;
			aceptarRFC = buttonAceptarRFC;
			break;

		case "EXISTENTE":
			aux.logS("Inventario Existente");
			lupa = buttonLupaRexistente;
			buscarRFC = buttonBuscarRFCInvExist;
			aceptarRFC = buttonAceptarRFCInvExist;
			break;

		case "EXCEDENTE":
			aux.logS("Inventario Excedente");

			break;

		case "NIVELES":
			aux.logS("Niveles");
			lupa = buttonLupaRNiveles;
			buscarRFC = buttonBuscarRFCNiveles;
			aceptarRFC = buttonAceptarRFCNiveles;
			break;

		case "DEVOLUCIONES":
			aux.logS("Devoluciones");
			lupa = buttonLupaRDevoluciones;
			nombreRFC = inputNombreRFCDevoluciones;
			buscarRFC = buttonBuscarRFCDevoluciones;
			aceptarRFC = buttonAceptarRFCDevoluciones;
			break;

		default:
			break;
		}
		try {
			u.highLight(lupa);
			testCase.addScreenShotCurrentStep(u, "Lupa");
			WebElement element = u.getDriver().findElement(lupa);
			Actions actions = new Actions(u.getDriver());
			actions.moveToElement(element).click().perform();
			u.unhighLight(lupa);
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Fallo al dar clic en la Lupa");
		}

		try {
			aux.enterOption("RFC", rfc, nombreRFC, false);
			aux.clickOpcion("Buscar RFC", buscarRFC, false);
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al buscar el RFC");
		}

		try {
			By row = By.cssSelector("tr[data-rk=\"" + rfc + "\"] > td > div > div.ui-radiobutton-box > span");
			aux.clickOpcion("RadioRFC", row, false);
			aux.clickOpcion("Aceptar RFC", aceptarRFC, true);
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al seleccionar el RFC");
		}
	}

	/**
	 * Da click en el boton buscar dependiendo de la pagina en la que se encuentre:
	 * Ordenes, Existente, Excedente, Niveles y Devoluciones. Y valida en la tabla
	 * que se muestren los reportes del rfc especifico.
	 * 
	 * @param rfc
	 * @param vista
	 * @author Esmeralda Garcia Torres
	 */
	public void buscarReporte(String rfc, String vista) {
		By buscar = null;
		switch (vista.toUpperCase()) {
		case "ORDENES":
			aux.logS("Ordenes de Compra");
			buscar = buttonBuscarReporte;
			break;

		case "EXISTENTE":
			aux.logS("Inventario Existente");
			buscar = buttonBuscarReporteInvExist;
			break;

		case "EXCEDENTE":
			aux.logS("Inventario Excedente");

			break;

		case "NIVELES":
			aux.logS("Niveles");
			buscar = buttonBuscarReporteNiveles;
			break;

		case "DEVOLUCIONES":
			aux.logS("Devoluciones");
			buscar = buttonBuscarReporteDevoluciones;
			break;

		default:
			break;
		}
		try {
			aux.clickOpcion("Buscar Reporte", buscar, true);
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al buscar el reporte");
		}

		try {
			aux.scrollToElement(paginator);
			aux.validateInTableGeneral("Tabla Cedis", tableCedis, rfc, "");
			testCase.addScreenShotCurrentStep(u, "Resultado");
			while (u.getDriver().findElement(pagNext).getAttribute("tabindex").contains("0")) {
				aux.logS("Siguiente pagina en tabla");
				aux.clickOpcion("Next page", pagNext, false);
				u.hardWait(2);
				testCase.addScreenShotCurrentStep(u, "Resultado");
			}
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al buscar en la tabla");
		}
	}

	/**
	 * Hace cambio al frame en el que estan los elementos para poder localizarlos.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void changeFrame() {
		try {
			WebElement fr = u.getDriver().findElement(frameAvPo);
			u.getDriver().switchTo().frame(fr);
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al cambiar de frame");
		}
	}

	/**
	 * Selecciona el icono para descargar el reporte en Excel
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void descargarExcel() {
		try {
			aux.scrollToElement(downloadExcel);
			aux.clickOpcion("Descargar Excel", downloadExcel, true);
			u.hardWait(1);
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al descargar el Excel");
		}
	}

	/**
	 * Selecciona el icono para descargar el reporte en PDF
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void descargarPDF() {
		try {
			aux.scrollToElement(downloadPDF);
			aux.clickOpcion("Descargar PDF", downloadPDF, true);
			u.hardWait(1);
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al descargar el PDF");
		}
	}

}
