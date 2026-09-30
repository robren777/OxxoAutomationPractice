package WEB.util.GDP_Ventas;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import modelo.TestCase;
import WEB.util.AuxMethods;
import utils.selenium.SeleniumUtil;

public class utilGDP_Ventas {
	private SeleniumUtil u;
	private AuxMethods aux;
	private TestCase testCase;

	/// WEB ELEMENTS ///
	By portalGestionProveedores = By.linkText("Gesti�n de Proveedores");
	By menuAbasto = By.cssSelector("#ddtopmenubar > ul > li > a[rel=\"ddsubmenu8894\"]");
	By menuVentas = By.cssSelector("#ddtopmenubar > ul > li > a[rel=\"ddsubmenu20193\"]");
	By opcionStockoutVentaP = By.id("j_idt140");
	By opcionDesempenioProductos = By.id("j_idt204");

	By frameAvPo = By.cssSelector("#contentFrame > iframe");

	By op = By.cssSelector("[data-label=\"valo\"]");

	By labelAcomodo = By.cssSelector("[id=\"report-form:options_label\"]");
	By opArticulo = By.cssSelector("[data-label=\"Articulo\"]");
	By opPlaza = By.cssSelector("[data-label=\"Plaza\"]");
	By opCategoria = By.cssSelector("[data-label=\"Categoria\"]");

	By labelAnio = By.cssSelector("[id=\"report-form:year_label\"]");
	By op23 = By.cssSelector("[data-label=\"2023\"]");
	By op22 = By.cssSelector("[data-label=\"2022\"]");

	By labelTipoTienda = By.cssSelector("[id=\"report-form:tienda_label\"]");
	By opTiendasTotales = By.cssSelector("[data-label=\"Tiendas Totales\"]");
	By opTiendasMaduras = By.cssSelector("[data-label=\"Tiendas Maduras\"]");

	By labelPeriodo = By.cssSelector("[id=\"report-form:period_label\"]");
	By opMensual = By.cssSelector("[data-label=\"Mensual\"]");
	By opSemanal = By.cssSelector("[data-label=\"Semanal\"]");

	By labelMeses = By.cssSelector("[id=\"report-form:options_label\"]");

	// By buttonBuscar = By.cssSelector("button[id=\"report-form:j_idt74\"]");
	By buttonBuscar = By.cssSelector("button");

	By paginator = By.cssSelector(".ui-paginator-bottom");
	By pagNext = By.cssSelector(".ui-paginator-next");

	By downloadExcel = By.cssSelector("img[src*=\"Excel\"]");
	By downloadPDF = By.cssSelector("img[src*=\"adobe\"]");

	/**
	 * Constructor. Recibe el SeleniumUtil y el testCase utilizado en el test.
	 * 
	 * @param SeleniumUtil
	 * @param testCase
	 * @author Esmeralda Garcia Torres
	 */
	public utilGDP_Ventas(SeleniumUtil u, TestCase testCase) {
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
	 * Selecciona el menu Abastos y la opcion Stockout y ventas perdidas.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void goToStockoutVentaPerdida() {
		try {
			WebElement ele = u.getDriver().findElement(menuAbasto);
			Actions action = new Actions(u.getDriver());
			action.moveToElement(ele).perform();
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Fallo el over");
		}
		aux.clickOpcion("Abasto", opcionStockoutVentaP, true);
		u.hardWait(1);
	}

	/**
	 * Selecciona el menu Ventas y la opcion Desempe�o de Productos.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void goToDesempenioProductos() {
		try {
			WebElement ele = u.getDriver().findElement(menuVentas);
			Actions action = new Actions(u.getDriver());
			action.moveToElement(ele).perform();
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Fallo el over");
		}
		aux.clickOpcion("Abasto", opcionDesempenioProductos, true);
		u.hardWait(1);
	}

	/**
	 * Cambia de frame.
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
	 * Ingresa los datos en la pagina de Stockout Ventas Perdidas y da clic en
	 * buscar. Toma screenshot de cada pagina que hay en la tabla.
	 * 
	 * @param acomodo
	 * @param anio
	 * @param tipoTienda
	 * @param tipoPeriodo
	 * @param mes
	 * @author Esmeralda Garcia Torres
	 */
	public void buscarStockoutVentasPerdidas(String acomodo, String anio, String tipoTienda, String tipoPeriodo,
			String mes) {
		try {
			aux.clickOpcion("Acomodo", labelAcomodo, false);
			switch (acomodo.toUpperCase()) {
			case "ARTICULO":
				aux.clickOpcion(acomodo, opArticulo, false);
				break;
			case "PLAZA":
				aux.clickOpcion(acomodo, opPlaza, false);
				break;
			case "CATEGORIA":
				aux.clickOpcion(acomodo, opCategoria, false);
				break;
			default:
				throw new AssertionError("No es un valor valido: " + acomodo);
			}
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al seleccionar el acomodo");
		}

		try {
			aux.clickOpcion("Anio", labelAnio, false);
			switch (anio.toUpperCase()) {
			case "2022":
				aux.clickOpcion(anio, op22, false);
				break;
			case "2023":
				aux.clickOpcion(anio, op23, false);
				break;
			default:
				throw new AssertionError("No es un valor valido: " + anio);
			}
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al seleccionar el anio");
		}

		try {
			aux.clickOpcion("Tipo Tienda", labelTipoTienda, false);
			switch (tipoTienda.toUpperCase()) {
			case "TIENDAS TOTALES":
				aux.clickOpcion(tipoTienda, opTiendasTotales, false);
				break;
			case "TIENDAS MADURAS":
				aux.clickOpcion(tipoTienda, opTiendasMaduras, false);
				break;
			default:
				throw new AssertionError("No es un valor valido: " + tipoTienda);
			}
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al seleccionar el tipo de tienda");
		}

		try {
			aux.clickOpcion("Periodo", labelPeriodo, false);
			switch (tipoPeriodo.toUpperCase()) {
			case "MENSUAL":
				aux.clickOpcion(tipoPeriodo, opMensual, false);
				break;
			case "SEMANAL":
				aux.clickOpcion(tipoPeriodo, opSemanal, false);
				break;
			default:
				throw new AssertionError("No es un valor valido: " + tipoPeriodo);
			}
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al seleccionar el tipo de periodo");
		}

		try {
			By opMes = By.xpath("//li[text()=\"" + mes + "\"]");
			aux.clickOpcion("Mes", opMes, false);
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al seleccionar el mes");
		}
		testCase.addScreenShotCurrentStep(u, "Datos ingresados");

		try {
			u.hardWait(2);
			aux.clickOpcion("Buscar", buttonBuscar, false);
			aux.scrollToElement(paginator);
			testCase.addScreenShotCurrentStep(u, "Buscar Stockout Venta Perdida");
			while (u.getDriver().findElement(pagNext).getAttribute("tabindex").contains("0")) {
				aux.logS("Siguiente pagina en tabla");
				aux.clickOpcion("Next page", pagNext, false);
				u.hardWait(2);
				testCase.addScreenShotCurrentStep(u, "Buscar Stockout Venta Perdida");
			}
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al buscar");
		}
	}

	/**
	 * Busca el icono de descargar excel y le da clic.
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
	 * Busca el icono de descargar PFD y le da clic.
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
