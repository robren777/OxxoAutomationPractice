package IMA.util;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class IMAUtils {

	// variables
	private SeleniumUtil u;
	TestCase testCase;
	private String texto;

	SoftAssert softAsserts = new SoftAssert();

	By reporteU;

	By menuDatos = By.xpath("//*[@id=\"ribbonToolbarTabsListContainer\"]/div[3]/table/tbody/tr/td[3]");
	By opcionNavegacion = By.xpath("//*[@id=\"RptDataMenu_\"]/tbody/tr/td/div/div[3]/a");

	// Exportar
	By menuInformes = By.xpath("//*[@id=\"ribbonToolbarTabsListContainer\"]/div[1]/table/tbody/tr/td[3]");
	By opcionExportar = By.xpath("//*[@id=\"RptHomeMenu_\"]/tbody/tr/td/div/div[18]");
	By exportarExcelConFormato = By.xpath(
			"//*[@id=\"RptHomeExportMenu_WEB-INFxmllayoutsblocksHomeExportMenuLayoutxml\"]/tbody/tr/td/div/div[8]");

	public IMAUtils(SeleniumUtil u, TestCase testCase) {
		this.u = u;
		this.testCase = testCase;
	}

	// login en la pagina de MicroStrategy
	public void login() {

		u.highLight(By.id("Uid"));
		u.sendKeys(By.id("Uid"), GlobalVariables.URL_USER);
		u.highLight(By.id("Pwd"));
		u.sendKeys(By.id("Pwd"), GlobalVariables.URL_PASSWORD);
		testCase.addScreenShotCurrentStep(u, "Pagina Login");
		u.highLight(By.id("3054"));
		u.click(By.id("3054"));

	}

	// metodo para buscar mediante palabras en la pagina que se usa en el meotod de
	// abajo
	public void clickjs(By by) {
		WebElement element = u.getDriver().findElement(by);
		JavascriptExecutor executor = (JavascriptExecutor) u.getDriver();
		executor.executeScript("arguments[0].click();", element);
	}

	// metodo para seleccionar mediante palabras
	public void SelectOption(String option) {
		// System.out.println("opcion: " + option);
		u.highLight(By.partialLinkText(option));
		testCase.addScreenShotCurrentStep(u, "Seleccion de folders");
		clickjs(By.partialLinkText(option));
	}

	// metodo para seleccionar mediante xpath
	public void SelectOptionByXpath(String xpath) {
		// System.out.println("opcion seleccionada: " + xpath);
		u.highLight(By.xpath(xpath));
		testCase.addScreenShotCurrentStep(u, "Seleccion de folders");
		u.click(By.xpath(xpath));
	}

	public void clickOnReporte(String reporte) {
		u.highLight(By.partialLinkText(reporte));
		testCase.addScreenShotCurrentStep(u, "Reporte Seleccionado");
		clickjs(By.partialLinkText(reporte));
	}

	/*
	 * metodo para introducir una palabra en un textBox de busqueda y seleccionarlo
	 * directo como filtro este metodo solo funciona para seleccionar 1 filtro
	 */
//orden param: id de TextBoxBusqueda, valor a introducir, xpath de boton buscar, xpath de boton mover a la derecha
public void capturarPrompts(String idTextBoxBusqueda, String valorAIntroducir, String confirmarSeleccion, String btnBuscar, String btnMoverDerecha) {
	u.highLight(By.id(idTextBoxBusqueda));
	u.click(By.id(idTextBoxBusqueda));
	u.sendKeys(By.id(idTextBoxBusqueda), valorAIntroducir);
	testCase.addScreenShotCurrentStep(u, "Captura de filtro " + valorAIntroducir);
	u.unhighLight(By.id(idTextBoxBusqueda));
	u.click(By.xpath(btnBuscar));
	u.hardWait(4);
	u.click(By.xpath(confirmarSeleccion));
	u.hardWait(3);
	u.click(By.xpath(btnMoverDerecha));
	u.scroll();
}

	/*
	 * metodo para primero seleccionar una opcion y luego introducir una palabra en
	 * un textBox de busqueda y seleccionarlo directo como filtro este metodo solo
	 * funciona para seleccionar 1 filtro
	 */
	// orden param: xpath filtroSeleccionado, id de TextBoxBusqueda, valor a
	// introducir, xpath de boton buscar, xpath opcion resultante de la busqueda,
	// boton mover a la derecha
	public void capturarPromptsConSeleccion(String filtroSeleccionado, String TextBoxBusqueda, String valorAIntroducir,
			String btnBuscar, String opResBusqueda, String btnMoverDerecha) {
		u.highLight(By.xpath(filtroSeleccionado));
		u.click(By.xpath(filtroSeleccionado));
		// u.unhighLight(By.xpath(filtroSeleccionado));
		u.hardWait(3);
		u.highLight(By.id(TextBoxBusqueda));
		u.click(By.id(TextBoxBusqueda));
		u.unhighLight(By.id(TextBoxBusqueda));
		u.sendKeys(By.id(TextBoxBusqueda), valorAIntroducir);
		u.click(By.xpath(btnBuscar));
		u.hardWait(3);
		u.highLight(By.xpath(opResBusqueda));
		u.click(By.xpath(opResBusqueda));
		testCase.addScreenShotCurrentStep(u, "Captura de filtro " + valorAIntroducir);
		u.unhighLight(By.xpath(opResBusqueda));
		u.click(By.xpath(btnMoverDerecha));
		u.scroll();
	}

	public void clickBtnEjecutarInforme(String btn) {
		u.highLight(By.id(btn));
		u.click(By.id(btn));
	}

	public void CompararCadena(By element, String textoEsperado) {
		/*
		 * Created by: mhernandez85 Date: 01-07-2022 Description: Funcoion para
		 * comprobar si una cadena contiene un caracter ".
		 */
		u.highLight(element);
		texto = u.getText(element);
		if (texto.indexOf(textoEsperado) != -1) {
			System.out.println("La cadena " + texto + " Contiene" + textoEsperado);
			testCase.addScreenShotCurrentStep(u, "La cadena " + texto + " Contiene" + textoEsperado);
		} else {
			System.out.println("La cadena  " + texto + " No Contiene " + textoEsperado);
			testCase.addScreenShotCurrentStep(u, "La cadena  " + texto + " No Contiene " + textoEsperado);
		}

	}

	public void exportInforme(String informe) {
		List<WebElement> s = u.getDriver().findElements(By.xpath("//*[@id=\"FolderIcons\"]/tbody/tr/td"));
		int j = 1, k = 1, count = s.size();
		for (int i = 0; i < count; i++) {
			if (k > 2) {
				j++;
				k = 1;
			}

			String xpath = "//*[@id=\"FolderIcons\"]/tbody/tr[" + j + "]/td[" + k
					+ "]/div/table/tbody/tr/td[2]/div[1]/span/a[contains(.,'" + informe + "')]";

			try {
				WebElement elem = u.getDriver().findElement(By.xpath(xpath));
				if (elem != null) {
					clickjs(By.xpath("//*[@id=\"FolderIcons\"]/tbody/tr[" + j + "]/td[" + k
							+ "]/div/table/tbody/tr/td[2]/div[6]/span[3]/a"));
					break;
				}
			} catch (Exception ex) {

			}
			k++;

			u.click(By.xpath(xpath));
			System.out.println("opcion seleccionada: " + xpath);
		}
	}

	public void SelectOptionById(String id) {
		// System.out.println("opcion seleccionada: " + id);
		u.highLight(By.id(id));
		testCase.addScreenShotCurrentStep(u, "Seleccion de folders");
		u.click(By.id(id));
	}

	public void locators(String reporte) {
		reporteU = By.linkText(reporte);
	}

	public void capturarPrompts(By searchBox, By searchBtn, By opcion, By btnAgregar, String busqueda) {

		u.highLight(searchBox);
		u.sendKeys(searchBox, busqueda);
		u.highLight(searchBtn);
		u.click(searchBtn);
		u.hardWait(3);

		u.highLight(opcion);
		u.click(opcion);
		u.highLight(btnAgregar);
		u.click(btnAgregar);

	}

	public void validarPromt(By plaza, By nivelJerarquico, By opcionPlaza, By jerarquiaTienda, By opcionTienda,
			By btnAgregarPlaza) {

		u.highLight(plaza);
		u.highLight(nivelJerarquico);
		testCase.addScreenShotCurrentStep(u, "Nivel jerarquico: Plaza");
		u.hardWait(2);
		u.unhighLight(nivelJerarquico);

		u.click(plaza);
		u.hardWait(3);
		u.highLight(opcionPlaza);
		u.click(opcionPlaza);

		u.highLight(jerarquiaTienda);
		u.click(jerarquiaTienda);

		u.highLight(opcionTienda);
		u.click(opcionTienda);

		u.highLight(btnAgregarPlaza);
		u.click(btnAgregarPlaza);
		u.hardWait(3);
	}

	public void compararTituloColumnas(By element, String textoEsperado) {
		if (element == null) {
			// System.out.println("Elemento no encontrado: " + textoEsperado);
		} else {
			u.highLight(element);
			texto = u.getText(element);
			// System.out.println(texto);
			// System.out.println(textoEsperado);
			assertEquals(texto, textoEsperado);
		}

	}

	public void goToFiltros() {
		u.highLight(menuDatos);
		u.click(menuDatos);

		u.highLight(opcionNavegacion);
		u.click(opcionNavegacion);
	}

	public void aplicarFiltros(By campo, By btn) {
		u.click(campo);

		u.highLight(btn);
		testCase.addScreenShotCurrentStep(u, "Filtro aplicado");
		u.click(btn);
	}

	// validacion de columnas, primero el id o xpath y luego el texto esperado para
	// validacion
	public void validarColumnas(By element, String textoEsperado) {
		u.highLight(element);
		texto = u.getText(element);
		System.out.println("TEXTO validacion col: " + texto);
		u.unhighLight(element);
		assertEquals(texto, textoEsperado, "La validacion es correcta");
	}

	// validacion de formato, primero el id o xpath y luego el texto esperado para
	// validacion
	// si la validacion se cumple = true, si no se cumple es false
	public boolean validarFormatoColumnas(By element, String charEsperado) {
		// boolean pasoLaValidacion = false;
		u.highLight(element);
		texto = u.getText(element);
		// System.out.println("formato validacion: "+ texto);
		u.unhighLight(element);
		if (texto.contains(charEsperado)) {
			System.out.println(texto + " SI cumple el formato " + charEsperado);
			return true;
		}
		System.out.println(texto + " NO cumple el formato " + charEsperado);
		return false;
	}

	public List<String> ObtenerVariacionDev() { // Se genero este metodo nuevo
		u.waitForLoadPage();
		List<String> vals = new ArrayList<String>();
		int i = 6;
		try {
			WebElement element = u.getDriver()
					.findElement(By.xpath("//*[@id=\"table_UniqueReportID\"]/tbody/tr[" + i + "]/td[8]"));
			while (element != null) {
				vals.add(element.getText());
				i++;
				element = u.getDriver()
						.findElement(By.xpath("//*[@id=\"table_UniqueReportID\"]/tbody/tr[" + i + "]/td[8]"));
			}
		} catch (Exception ex) {
		}

		for (String j : vals) {
			System.out.println(j);
		}
		return vals;
	}

	public void waitToLoad() {
		WebElement searchForm = u.getDriver().findElement(By.className("mstrIcon-wait"));
		while (searchForm.isDisplayed()) {
			try {
				Thread.sleep(500);
			} catch (Exception ex) {

			}
		}
	}

	public void capturarPrompts2(String idTextBoxBusqueda, String valorAIntroducir, String btnBuscar,
			String btnMoverDerecha) {
		u.highLight(By.id(idTextBoxBusqueda));
		u.click(By.id(idTextBoxBusqueda));
		u.sendKeys(By.id(idTextBoxBusqueda), valorAIntroducir);
		testCase.addScreenShotCurrentStep(u, "Captura de filtro " + valorAIntroducir);
		u.unhighLight(By.id(idTextBoxBusqueda));
		u.click(By.xpath(btnBuscar));
		u.hardWait(4);
		u.click(By.xpath(btnMoverDerecha));
		u.scroll();
	}
	
	public void seleccionarPlazaReporteRankeoPorGastos(String iconoDesplegarPlaza, String btnPlaza, String idTextBoxBusqueda,
			String valorAIntroducir, String btnBuscar, String clickEnSeleccionado, String btnMoverDerecha) {
		u.highLight(By.xpath(iconoDesplegarPlaza));
		u.click(By.xpath(iconoDesplegarPlaza));
		u.click(By.xpath(btnPlaza));
		u.click(By.id(idTextBoxBusqueda));
		u.highLight(By.id(idTextBoxBusqueda));
		u.sendKeys(By.id(idTextBoxBusqueda), valorAIntroducir);
		testCase.addScreenShotCurrentStep(u, "Captura de filtro " + valorAIntroducir);
		u.unhighLight(By.id(idTextBoxBusqueda));
		u.click(By.xpath(btnBuscar));
		u.hardWait(4);
		u.highLight(By.xpath(clickEnSeleccionado));
		u.click(By.xpath(clickEnSeleccionado));
		u.click(By.xpath(btnMoverDerecha));
		u.scroll();
	}
}
