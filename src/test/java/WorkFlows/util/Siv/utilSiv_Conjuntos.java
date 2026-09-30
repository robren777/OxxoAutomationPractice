package WorkFlows.util.Siv;

import java.time.Duration;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class utilSiv_Conjuntos {
	private SeleniumUtil u;
	private TestCase testCase;
	private pomSIV pom;
	private WebDriverWait wait;

	/// WEB ELEMENTS CONJUNTOS ///
	By checkTodosPlanogrupos = By.id("chkTodos_clPlanogrupos");

	By flechaBuscarSegmentos = By.cssSelector("input[name=\"btnBuscarSegmentosSelenium\"]");
	By checkTodosSegmentos = By.id("chkTodos_clSegmentos");

	By flechaBuscarMuebles = By.cssSelector("input[name=\"btnBuscarMueblesSelenium\"]");
	By checkTodosMuebles = By.id("chkTodos_clMuebles");

	By flechaBuscarPlazas = By.cssSelector("input[name=\"btnBuscarPlazasSelenium\"]");
	By checkTodosPlazas = By.id("chkTodos_clPlazas");

	By flechaBuscarConjuntos = By.cssSelector("input[name=\"btnBuscarConjuntosSelenium\"]");
	By checkTodosConjuntos = By.id("chkTodos_clConjuntos");

	By buttonBuscar = By.id("btnBuscar");
	By buttonConfigurar = By.id("btnConfigurar");
	By buttonSalir = By.id("btnSalir");
	By buttonIncrementarCobertura = By.id("btnConfigurarAgregar");
	By buttonReducirCobertura = By.id("btnConfigurarQuitar");

	By pestanaCoberturaObjetivo = By.cssSelector("a[name=\"ahref_coberturaFase_2\"]");
	By tableObjetivo = By.cssSelector("table#tblCO > tbody > tr");

	By sliderPorcentaje = By.id("rngPorcentajeSeleccionado");
	By buttonGuardarConfigCoberturas = By.cssSelector("button[name=\"btnguardarAltasConfiguradasSelenium\"]");
	
	By buttonAceptarAprob = By.cssSelector("input[value=\"Aceptar\"]");
	By buttonRechazarAprob = By.cssSelector("input[value=\"Rechazar\"]");
	By buttonSalirAprob = By.cssSelector("input#btnSalirValidacion");
	By buttonAceptarRechazo = By.cssSelector("input[value=\"Aceptar Rechazo\"]");
	
	By selectFormaExpulsion = By.id("cboModalFrmExpulsion");
	By buttonAceptarFechaInactivacion = By.cssSelector("div.modal-footer > button.btnSubGrupo[ng-click=\"modal.guardadoPrimeraEtapa()\"]");
	By commentsInactivacion = By.id("txtModalComent");
	

	/**
	 * Constructor. Recibe el SeleniumUtil utilizado en el test.
	 * 
	 * @param SeleniumUtil
	 * @author Esmeralda Garcia Torres
	 */
	public utilSiv_Conjuntos(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		this.pom = new pomSIV(u, testCase);
	}

	public void todosPlanogrupos(Boolean activo) {
		try {
			if (activo && !u.getDriver().findElement(checkTodosPlanogrupos).isSelected()) {
				u.click(checkTodosPlanogrupos);
			} else if (!activo && u.getDriver().findElement(checkTodosPlanogrupos).isSelected()) {
				u.click(checkTodosPlanogrupos);
			}
			pom.waitLoadingModal();
		} catch (Exception e) {
			pom.logL("" + e);

			throw new AssertionError("Fallo al seleccionar o deseleccionar el check Todos en Planogrupos");
		}
	}

	public void buscarSegmentos() {
		try {
			pom.waitLoadingModal();
			wait.until(ExpectedConditions.elementToBeClickable(flechaBuscarSegmentos));
			u.click(flechaBuscarSegmentos);
			u.hardWait(1);
			pom.waitLoadingModal();
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al dar clic en: Buscar segmentos");
		}
	}

	public void todosSegmentos(Boolean activo) {
		try {
			if (activo && !u.getDriver().findElement(checkTodosSegmentos).isSelected()) {
				u.click(checkTodosSegmentos);
			} else if (!activo && u.getDriver().findElement(checkTodosSegmentos).isSelected()) {
				u.click(checkTodosSegmentos);
			}
			pom.waitLoadingModal();
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar o deseleccionar el check Todos en Segmentos");
		}
	}

	public void buscarMuebles() {
		try {
			pom.waitLoadingModal();
			wait.until(ExpectedConditions.elementToBeClickable(flechaBuscarMuebles));
			u.click(flechaBuscarMuebles);
			u.hardWait(1);
			pom.waitLoadingModal();
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al dar clic en: Buscar Muebles");
		}
	}

	public void todosMuebles(Boolean activo) {
		try {
			if (activo && !u.getDriver().findElement(checkTodosMuebles).isSelected()) {
				u.click(checkTodosMuebles);
			} else if (!activo && u.getDriver().findElement(checkTodosMuebles).isSelected()) {
				u.click(checkTodosMuebles);
			}
			pom.waitLoadingModal();
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar o deseleccionar el check Todos en Muebles");
		}
	}

	public void buscarPlazas() {
		try {
			pom.waitLoadingModal();
			wait.until(ExpectedConditions.elementToBeClickable(flechaBuscarPlazas));
			u.click(flechaBuscarPlazas);
			u.hardWait(1);
			pom.waitLoadingModal();
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al dar clic en: Buscar Plazas");
		}
	}

	public void todosPlazas(Boolean activo) {
		try {
			if (activo && !u.getDriver().findElement(checkTodosPlazas).isSelected()) {
				u.click(checkTodosPlazas);
			} else if (!activo && u.getDriver().findElement(checkTodosPlazas).isSelected()) {
				u.click(checkTodosPlazas);
			}
			pom.waitLoadingModal();
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar o deseleccionar el check Todos en Plazas");
		}
	}

	public void buscarConjuntos() {
		try {
			pom.waitLoadingModal();
			wait.until(ExpectedConditions.elementToBeClickable(flechaBuscarConjuntos));
			u.click(flechaBuscarConjuntos);
			u.hardWait(1);
			pom.waitLoadingModal();
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al dar clic en: Buscar Conjuntos");
		}
	}

	public void todosConjuntos(Boolean activo) {
		try {
			if (activo && !u.getDriver().findElement(checkTodosConjuntos).isSelected()) {
				u.click(checkTodosConjuntos);
			} else if (!activo && u.getDriver().findElement(checkTodosConjuntos).isSelected()) {
				u.click(checkTodosConjuntos);
			}
			pom.waitLoadingModal();
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar o deseleccionar el check Todos en Conjuntos");
		}
	}

	public void seleccionarOpcion(String seccion, String opciones) {
		String plaza = "";
		try {
			u.hardWait(1);
			if (opciones.trim().equalsIgnoreCase("Todos")) {
				switch (seccion.toUpperCase()) {
				case "PLANOGRUPOS":
					todosPlanogrupos(true);
					break;
				case "SEGMENTOS":
					todosSegmentos(true);
					break;
				case "MUEBLES":
					todosMuebles(true);
					break;
				case "PLAZAS":
					todosPlazas(true);
					break;
				case "REGIONES":
					todosPlazas(true);
					break;
				case "CONJUNTOS":
					todosConjuntos(true);
					break;
				default:
					throw new AssertionError("La seccion: \"" + seccion + "\" no es una opcion valida");
				}
				u.hardWait(1);
			} else {
				switch (seccion.toUpperCase()) {
				case "PLANOGRUPOS":
					todosPlanogrupos(false);
					break;
				case "SEGMENTOS":
					todosSegmentos(false);
					break;
				case "MUEBLES":
					todosMuebles(false);
					break;
				case "PLAZAS":
					todosPlazas(false);
				case "REGIONES":
					todosPlazas(false);
					break;
				case "CONJUNTOS":
					todosConjuntos(false);
					break;
				default:
					throw new AssertionError("La seccion: \"" + seccion + "\" no es una opcion valida");
				}
				u.hardWait(1);
				String[] listaOpciones = opciones.split(",");
				for (int i = 0; i < listaOpciones.length; i++) {
					plaza = listaOpciones[i].trim();
					pom.logM("Seccion: " + seccion + " - Opcion: " + plaza);
					By elementSegmento = By.xpath("//label[text()=\"" + seccion + "\"]/following-sibling::div/table/tbody/tr/td/label[text()=\"" + plaza + "\"]/preceding-sibling::input");
					/*
					 * By elementSegmento =
					 * By.xpath("//div//label[contains(@class,'tituloGrupo') and text()=\"" +
					 * seccion +
					 * "\"]//following-sibling::div//table//tbody//tr//td//label[text()=\"" + plaza
					 * + "\"]//preceding-sibling::input");
					 */

					pom.scrollToElement(elementSegmento);
					u.highLight(elementSegmento);
					u.click(elementSegmento);
					u.unhighLight(elementSegmento);
					pom.waitLoadingModal();
				}
			}
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar la opcion: " + plaza + " en la seccion: " + seccion);
		}
	}
	

	public void buscar() {
		pom.clickOpcion("Buscar", buttonBuscar);
	}

	public void configurar() {
		pom.clickOpcion("Configurar", buttonConfigurar);
	}

	public void salir() {
		pom.clickOpcion("Salir", buttonSalir);
	}
	
	public void incrementar() {
		pom.clickOpcion("Incrementar Cobertura", buttonIncrementarCobertura);
	}
	
	public void reducir() {
		pom.clickOpcion("Reducir Cobertura", buttonReducirCobertura);
	}

	public void coberturaObjetivo() {
		pom.clickOpcion("Pestana Cobertura Objetivo", pestanaCoberturaObjetivo);
	}
	
	public void AceptarAprobacion() {
		pom.clickOpcion("Aceptar aprobacion", buttonAceptarAprob);
		pom.optionMsgModal("OK");
		pom.clickOpcion("Salir Aprobacion", buttonSalirAprob);
	}
	
	public void RechazarAprobacion() {
		pom.clickOpcion("Rechazar aprobacion", buttonRechazarAprob);
	}
	public void salirConjuntosAprob() {
		pom.clickOpcion("Salir Conjuntos Aprob", buttonSalirAprob);
	}
	public void aceptarRechazoConjuntos() {
		pom.clickOpcion("Aceptar Rechazo Conjuntos", buttonAceptarRechazo);
	}

	public void seleccionarObjetivos() {
		try {
			List<WebElement> elementsTable = u.getDriver().findElements(tableObjetivo);
			pom.logM("Elementos en la Tabla: " + elementsTable.size());
			if (elementsTable.size() == 0) {
				u.hardWait(2);
			}
			String[] columns = elementsTable.get(0).getAttribute("innerText").split("\t");
			pom.logM("Columnas: " + columns.length);

			for (int i = 4; i < columns.length; i++) {
				u.click(By.cssSelector("table#tblCO > tbody > tr > td:nth-child(" + (i + 1) + ")"));
			}
			u.hardWait(1);
			pom.waitLoadingModal();
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar los objetivos");
		}
	}

	public void porcentaje() {
		try {
			Actions act = new Actions(u.getDriver());
			act.dragAndDropBy(u.getDriver().findElement(sliderPorcentaje), 280, 0).build().perform();
			u.hardWait(1);
			pom.waitLoadingModal();
		} catch (Exception e) {

			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar el porcentaje en la configuracion");
		}
	}
	
	public void porcentajeReducir() {
		u.hardWait(3);
		try {
			Actions act = new Actions(u.getDriver());
			act.dragAndDropBy(u.getDriver().findElement(sliderPorcentaje), -280, 0).build().perform();
			u.hardWait(1);
			pom.waitLoadingModal();
		} catch (Exception e) {
			pom.logL("" + e);

			throw new AssertionError("Fallo al seleccionar el porcentaje en la configuracion");
		}
	}

	public void guardarConfiguracion() {
		try {
			pom.scrollToElement(buttonGuardarConfigCoberturas);
			u.highLight(buttonGuardarConfigCoberturas);
			u.click(buttonGuardarConfigCoberturas);
			testCase.addScreenShotCurrentStep(u, "Guardar Coberturas");
			u.hardWait(1);
			pom.waitLoadingModal();

			Boolean band = true;
			do {
				if (pom.msgModal().contains("se ha guardado Correctamente")) {
					pom.optionMsgModal("OK");
					band = false;
				}
			} while (band);
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al dar click en Guardar en la configuracion de los objetivos");
		}
	}
	
	public void guardarConfigMod(String formaExpulsion) {
		try {
			pom.scrollToElement(buttonGuardarConfigCoberturas);
			u.highLight(buttonGuardarConfigCoberturas);
			u.click(buttonGuardarConfigCoberturas);
			testCase.addScreenShotCurrentStep(u, "Guardar Coberturas");
			u.hardWait(1);
			pom.waitLoadingModal();
			seleccionarFechaInactivacion();
			u.hardWait(1);
			formaExpulsion(formaExpulsion);
			u.hardWait(1);
			co();
			aceptarInactivacion();
			Boolean band = true;
			do {
				if (pom.msgModal().contains("se ha guardado Correctamente")) {
					pom.optionMsgModal("OK");
					band = false;
				}
			} while (band);
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al dar click en Guardar en la configuracion de los objetivos");
		}
	}
	
	public void seleccionarFechaInactivacion() {
		try {
			u.hardWait(2);
			Calendar c = new GregorianCalendar();
			int day = c.get(Calendar.DATE) + 1;
			int month = c.get(Calendar.MONTH) + 1;
			int year = c.get(Calendar.YEAR);


			By titleMonth = By.xpath("//tr//th//button[contains(@class,'uib-title')]//strong");
			String title = u.getDriver().findElement(titleMonth).getAttribute("innerText");
			pom.logM("Title: " + title);

			String[] txtMonth = { "enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto",
					"septiembre", "octubre", "noviembre", "diciembre" };
			String monthYearAux = txtMonth[month - 1] + " " + year;
			pom.logM("Comp: " + monthYearAux);

			while (!title.contains(monthYearAux)) {
				u.hardWait(1);
				By nextMonth = By.xpath("//tr//th//button[contains(@class,'uib-right')]");
				u.highLight(nextMonth);
				u.click(nextMonth);
				u.hardWait(1);

				title = u.getDriver().findElement(titleMonth).getAttribute("innerText");
				pom.logM("Title N: " + title);
			}
			String titleLocation = "//tr//th//button[contains(@class,'uib-title')]//strong[text()=\"" + monthYearAux
					+ "\"]";
			pom.logM(titleLocation);
			By monthElement = By.xpath(titleLocation);
			u.highLight(monthElement);
			String dday = "";
			if (day < 10) {
				dday = "0" + String.valueOf(day);
			} else {
				dday = String.valueOf(day);

			}

			String dayElement = "//td[contains(@class,'uib-day')]//button//span[not(contains(@class,'text-muted')) and text()=\""
					+ dday + "\"]";
			pom.logM("xpath Day------>" + dayElement);
			By dayE = By.xpath(dayElement);
			u.highLight(dayE);
			u.hardWait(1);
			u.click(dayE);

		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar la fecha");
		}
	}
	
	public void formaExpulsion(String forma) {
		pom.selectOption("Forma Expulsion", forma, selectFormaExpulsion);
	}
	
	public void aceptarInactivacion() {
		pom.clickOpcion("Aceptar Inactivacion", buttonAceptarFechaInactivacion);
	}
	
	public void co () {
		try {
			WebElement m = u.getDriver().findElement(commentsInactivacion);
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].setAttribute('style', 'width: 158px; height: 30px;');", m);
		} catch (Exception e) {
			pom.logL("No se pudo redimencionar: " + e);

		}
	}

}