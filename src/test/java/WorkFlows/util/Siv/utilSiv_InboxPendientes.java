package WorkFlows.util.Siv;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class utilSiv_InboxPendientes {
	private SeleniumUtil u;
	private TestCase testCase;
	private WebDriverWait wait;
	private pomSIV pom;
	private int waitTime;
	// private WebDriverWait wait;


	/// WEB ELEMENTS - INBOX PENDIENTES CONFIGURACION
	By tablePendientes = By.cssSelector("#tblInboxPendiente > tbody > tr");
	By buttonMenuInbox = By.id("button-template-urlMenu");
	By listMenuInbox = By.cssSelector("ul[role=\"menu\"] > li");
	By checkTable = By.cssSelector("td:nth-child(1) > input");

	By buttonDatosBasicos = By.cssSelector("input[name=\"btnDatosBasicosSelenium\"]");
	By buttonConjuntos = By.cssSelector("input[name=\"btnConjuntosSelenium\"]");
	By buttonFuentesAbasto = By.cssSelector("input[name=\"btnFuenteAbastoSelenium\"]");
	By buttonEnvioMuestraFisica = By.cssSelector("input[name=\"btnEnvioMuestraFisicaSelenium\"]");
	By tituloVentana = By.xpath("//span[@class='TituloVentana' and text()='Inbox Pendientes Configuración']");
	 
	By buttonRedirigir = By.cssSelector("input[value=\"Redirigir a Otro Usuario\"]");
	By buttonAgregarItem = By.id("btnAgregarItem");
	By buttonAgregarItemPlaza = By.cssSelector("input[value=\"Agregar �TEM\"]");
	By buttonDesecharSeleccion = By.cssSelector("input[value=\"Desechar Selecci�n\"]");
	By buttonEnviarSeleccion = By.cssSelector("input[value=\"Enviar Selecci�n\"]");

	/// WEB ELEMENTS - INBOX PENDIENTES CATALOGOS
	By tablePendienteCatalogos = By.cssSelector("table.table > tbody > tr");
	By buttonMuestraFisicaCategoria = By.cssSelector("input[value=\"Muestra F�sica\"]");
	By selectEstatusCatalogos = By.id("cboEstatus");
	By buttonExportarExcelCatalogos = By.cssSelector("input[value=\"Exportar a Excel\"]");
	By buttonAceptarCatalogos = By.cssSelector("input[value=\"Aceptar\"]");
	By buttonRechazarCatalogos = By.cssSelector("input[value=\"Rechazar\"]");
	By buttonDesecharCatalogos = By.cssSelector("input[value=\"Desechar\"]");
	By buttonSalirCatalogos = By.cssSelector("input[value=\"Salir\"]");
	By buttonRechazadosCatalogos = By.cssSelector("input[value=\"Rechazados\"]");

	/// WEB ELEMENTS - INBOX ALTAS CATALOGOS
	By buttonAltasCatalogos = By.cssSelector("input[value=\"Altas\"]");
	By buttonModCatalogos = By.cssSelector("input[value=\"Modificaciones\"]");
	By buttonDatosBasicosInboxAltas = By.cssSelector("input[value=\"Datos B�sicos\"]");
	By buttonConjuntosPlaza = By.cssSelector("input[value=\"Conjuntos\"]");
	By buttonFuenteAbastoInboxAltas = By.cssSelector("input[value=\"Fuente Abasto\"]");
	By buttonEnvioMuestraFPlaza = By.cssSelector("input[value=\"Env�o Muestra F�sica\"]");
	By buttonVaciarInboxAltas = By.cssSelector("input.btnSubGrupo[value=\"Vaciar Inbox\"]");
	By buttonAceptarRechazoDatosBasicos = By.cssSelector("input[value=\"Aceptar Rechazo\"]");
	By buttonFuenteDeAbastoInboxCatalogos = By.cssSelector("input[value=\"Fuente de Abastos\"]");

	///
	// By checkTodosFuenteAbasto = By.cssSelector("input.ng-touched");
	By checkTodasFAplaza = By.xpath("//*[@id=\"chkGvFAConfAllSelenium\"]");
	By checkTodosFuenteAbasto = By.cssSelector("input[ng-click=\"clickChkAllFAConfig()\"]");
	//By checkTodosFuenteAbasto = By
	//		.xpath("/html/body/div[1]/div[2]/div/div[1]/div/div/div[3]/div/div/table/thead/tr[1]/th[1]/input");
	By buttonAceptarFuenteAbasto = By.cssSelector("input.btnSubGrupo[value=\"Aceptar\"]");
	By buttonRechazarFuenteAbasto = By.cssSelector("input.btnSubGrupo[value=\"Rechazar\"]");
	By inputComentariosFuenteAbasto = By.cssSelector("textarea.ng-touched");
	By buttonAceptarComentarioFuenteAbasto = By.cssSelector(
			"div.modal:nth-child(5) > div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > button:nth-child(1)");
	By buttonRegresarFuenteAbasto = By.cssSelector("input.btnSubGrupo[value=\"Regresar\"]");

	/// WEB ELEMENTS INBOX ENVIO DE MUESTRA FISICA ///

	By tableEnvioMuestraFisica = By.cssSelector("table.table > tbody > tr");
	By capturarNuevoEnvio = By.cssSelector("input[value=\"Capturar Nuevo Env�o\"]");
	By enviarMuestraFisica = By.cssSelector("input[value=\"Enviar\"]");

	/// ADMIN ///
	By inputUserAlterno = By.id("txtUsuarioAlterno");
	By buttonChangeUser = By.id("btnCambiarUsuario");

	/**
	 * Constructor. Recibe el SeleniumUtil utilizado en el test.
	 * 
	 * @param SeleniumUtil
	 * @author Esmeralda Garcia Torres
	 */
	public utilSiv_InboxPendientes(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(20));
		this.pom = new pomSIV(u, testCase);
		this.waitTime = 120;
	}

	/**
	 * Espera a que se muestre el boton Agregar item y le da click.

	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void agregarItem() {
		pom.waitLoadingModal();
		u.waitWebElement(buttonAgregarItem, 120, 60);
		pom.clickOpcion("Agregar Item", buttonAgregarItem);
	}

	public void agregarItemPlaza() {
		pom.waitLoadingModal();
		u.hardWait(1);
		pom.clickOpcion("Agregar Item", buttonAgregarItemPlaza);
	}

	/**
	 * Espera a que se muestre el boton Datos Basicos y le da click.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void datosBasicos() {
		pom.clickOpcion("Datos Basicos", buttonDatosBasicos);
	}

	/**
	 * Espera a que se muestre el boton Conjuntos y le da click.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void conjuntos() {
		pom.clickOpcion("Conjuntos", buttonConjuntos);
		u.hardWait(1);
		pom.waitLoadingModal();
	}

	public void conjuntosPlaza() {
		pom.clickOpcion("Conjuntos", buttonConjuntosPlaza);
		u.hardWait(1);
		pom.waitLoadingModal();
	}

	/**
	 * Espera a que se muestre el boton Fuente Abasto y le da click.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void fuenteAbasto() {
		pom.clickOpcion("Fuente Abasto", buttonFuentesAbasto);
	}

	public void fuenteAbastoPlaza() {
		pom.clickOpcion("Fuente Abasto", buttonFuenteAbastoInboxAltas);
	}

	/**
	 * Espera a que se muestre el boton Envio Muestra Fisica y le da click.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void envioMuestraFisica() {
		pom.clickOpcion("Envio Muestra Fisica", buttonEnvioMuestraFisica);

	}

	public void envioMuestraFisicaPlaza() {
		pom.clickOpcion("Envio Muestra Fisica Plaza", buttonEnvioMuestraFPlaza);
	}

	/**
	 * Espera a que se muestre el boton Redirigir a otro usuario y le da click.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void redirigir() {
		pom.clickOpcion("Redirigir a Otro Usuario", buttonRedirigir);
	}

	/**
	 * Espera a que se muestre el boton Desechar Seleccion y le da click.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void desecharSeleccion() {
		pom.clickOpcion("Desechar Seleccion", buttonDesecharSeleccion);
	}

	/**
	 * Espera a que se muestre el boton Enviar Seleccion y le da click.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void enviarSeleccion() {
		pom.clickOpcion("Enviar Seleccion", buttonEnviarSeleccion);
	}

	/**
	 * Espera a que se muestre el boton Muestra Fisica en Visualizacion de MF
	 * pendientes catalogos y le da click.

	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void muestraFisicaCat() {
		pom.clickOpcion("Muestra Fisica en Categoria", buttonMuestraFisicaCategoria);
	}

	/**
	 * Espera a que se muestre el boton Exportar Excel en Inbox de Catalogos Muestra
	 * Fisica y le da click.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void exportarExcelCatalogos() {
		pom.clickOpcion("Exportar Excel Catalogos", buttonExportarExcelCatalogos);
	}

	/**
	 * Espera a que se muestre el boton Aceptar en Inbox de Catalogos Muestra Fisica
	 * y le da click.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void aceptarItemEnCatalogos() {
		pom.clickOpcion("Aceptar item en Catalogos", buttonAceptarCatalogos);
	}

	public void aceptarInfoBasicaCatalogos() {
		pom.clickOpcion("Aceptar info basica en Catalogos", buttonAceptarCatalogos);
	}

	public void rechazarInfoBasicaCatalogos() {
		pom.clickOpcion("Rechazar info basica en Catalogos", buttonRechazarCatalogos);
		pom.waitLoadingModal();
	}

	/**
	 * Espera a que se muestre el boton Rechazar en Inbox de Catalogos Muestra
	 * Fisica y le da click.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void rechazarItemEnCatalogos() {

		pom.clickOpcion("Rechazar item en Catalogos", buttonRechazarCatalogos);
	}

	/**
	 * Espera a que se muestre el boton Deshechar en Inbox de Catalogos Muestra
	 * Fisica y le da click.

	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void desecharItemEnCatalogos() {

		pom.clickOpcion("Desechar item en Catalogos", buttonDesecharCatalogos);
	}

	/**
	 * Espera a que se muestre el boton Salir en Inbox de Catalogos Muestra Fisica y
	 * le da click.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void salirInboxCatalogos() {
		pom.clickOpcion("Salir inbox Catalogos", buttonSalirCatalogos);
	}

	/**
	 * Espera a que se muestre el boton Altas en Visualizacion de Pendientes de
	 * Catalogos y le da click.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void altasCatalogos() {
		pom.clickOpcion("Altas Catalogos", buttonAltasCatalogos);
	}
	
	public void modificacioesCatalogos() {
		pom.clickOpcion("Modificaciones Catalogos", buttonModCatalogos);
	}

	/**
	 * Espera a que se muestre el boton Datos Basicos en Inbox Altas Pendientes de
	 * Validar y le da click.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void datosBasicosAltas() {

		pom.clickOpcion("Datos Basicos en inbox Altas", buttonDatosBasicosInboxAltas);
	}

	/**
	 * Espera a que se muestre el boton Fuente Abasto en Inbox Altas Pendientes de
	 * Validar y le da click.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void fuenteAbastoAltas() {
		pom.clickOpcion("Fuente Abasto en inbox Altas", buttonFuenteAbastoInboxAltas);
		pom.waitLoadingModal();
	}

	public void fuenteAbastoCatalogos() {
		pom.clickOpcion("Fuente de Abasto en Catalogos", buttonFuenteDeAbastoInboxCatalogos);
	}

	/**

	 * Selecciona el checkbox "Todos" en la seccion de Fuente de Abasto
	 * Configuracion en este ciclo para el Articulo.

	 * 
	 * @param opcion -> true o false
	 * @author Esmeralda Garcia Torres
	 */
	public void checkTodosFuenteAbastoValidacion(Boolean opcion) {
		try {
			u.hardWait(2);
			pom.waitLoadingModal();
			if (u.getDriver().findElement(checkTodosFuenteAbasto).isEnabled()) {
				if (opcion && !u.getDriver().findElement(checkTodosFuenteAbasto).isSelected()) {
					u.click(checkTodosFuenteAbasto);
				} else if (!opcion && u.getDriver().findElement(checkTodosFuenteAbasto).isSelected()) {
					u.click(checkTodosFuenteAbasto);
				}
				pom.waitLoadingModal();
			}
		} catch (Exception e) {

			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar: \"" + opcion
					+ "\" en el checkBox de Todos en validacion de Fuente de Abastos");
		}
	}
	
	public void checkTodosFuenteAbastoValidacionPlaza(Boolean opcion) {
		try {
			u.hardWait(3);
			pom.waitLoadingModal();
			if (u.getDriver().findElement(checkTodasFAplaza).isEnabled()) {
				if (opcion && !u.getDriver().findElement(checkTodasFAplaza).isSelected()) {
					u.click(checkTodasFAplaza);
				} else if (!opcion && u.getDriver().findElement(checkTodasFAplaza).isSelected()) {
					pom.logM("VAMOS A DAR CLICK");
					u.click(checkTodasFAplaza);
				}
				pom.waitLoadingModal();
			}
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar: \"" + opcion
					+ "\" en el checkBox de Todos en validacion de Fuente de Abastos");
		}
	}

	/**

	 * Espera a que se muestre el boton Aceptar en la validacion y confirmacion de
	 * Fuente de Abasto.
	 * 
	 * @param opcion -> true o false
	 * @author Esmeralda Garcia Torres
	 */
	public void aceptarFuenteAbastoValidacion() {
		pom.clickOpcion("Aceptar validacion Fuente Abasto", buttonAceptarFuenteAbasto);
		pom.waitLoadingModal();
	}

	public void rechazarFuenteAbastoValidacion() {
		pom.clickOpcion("Rechazar validacion Fuente Abasto", buttonRechazarFuenteAbasto);
		pom.waitLoadingModal();
	}

	/**
	 * Ingresa un comentario en el modal que se muestra al aceptar la validacion de
	 * la Fuente de Abasto.
	 * 
	 * @param valor
	 * @author Esmeralda Garcia Torres
	 */
	public void enterComentarioFuenteAbastoValidacion(String valor) {
		pom.enterOption("Ingresar comentarios en validacion Fuente Abasto", valor, inputComentariosFuenteAbasto);
		pom.waitLoadingModal();
	}

	/**
	 * Da click en el boton de aceptar que se muestra en el modal que se muestra al
	 * aceptar la validacion de la Fuente de Abasto. Espera a que se muestre el
	 * mensaje "Fuente(s) de Abasto Aceptada(s) correctamente", y da click en OK.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void aceptarComentarioFuenteAbastoValidacion() {
		pom.clickOpcion("Aceptar comentario en validacion Fuente Abasto", buttonAceptarComentarioFuenteAbasto);
		String textModal = pom.msgModal();
		if (!textModal.contains("Fuente(s) de Abasto Aceptada(s) correctamente")) {
			throw new AssertionError("Fallo al aceptar la informacion de Fuente de Abasto");
		}
		pom.optionMsgModal("OK");
		u.hardWait(1);
		pom.waitLoadingModal();
	}

	/**
	 * Da click en el boton "Regresar" que se encuentra en la pantalla de validacion
	 * de Fuente de Abasto.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void regresarFuenteAbastoValidacion() {
		pom.clickOpcion("Regresar en validacion Fuente Abasto", buttonRegresarFuenteAbasto);
		pom.waitLoadingModal();
	}

	/**
	 * Da click en el boton de "Vaciar Inbox" en la pantalla de Inbox Altas.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void vaciarInboxAltas() {
		pom.clickOpcion("Vaciar Inbox en Altas", buttonVaciarInboxAltas);
		pom.waitLoadingModal();
	}

	/**
	 * Busca en la tabla de Pendientes el codigo de Barras esperado.
	 * 
	 * @param cb -> Codigo de Barras
	 * @return String[2] posicion 0 contiene la fila en la que se encontro. Posicion
	 *         1 contiene el innerText de la fila.
	 * @author Esmeralda Garcia Torres
	 */
	private String[] searchInTable(String cb) {
		String[] results = new String[2];
		results[0] = "";
		results[1] = "";
		try {
			Boolean aux = false;
			Integer row = 0;
			List<WebElement> elementsTable = u.getDriver().findElements(tablePendientes);
			if (elementsTable.size() == 0) {
				u.hardWait(3);
			}
			pom.logM("Elementos en la Tabla: " + elementsTable.size());
			for (int i = 0; i < elementsTable.size(); i++) {
				String element = elementsTable.get(i).getAttribute("innerText").toUpperCase();
				pom.logM(element + " : " + cb);
				if (element.toUpperCase().contains(cb.toUpperCase())) {
					row = i + 1;
					results[0] = Integer.toString(row);
					results[1] = element;
					pom.logM("Se encontro en row: " + row);
					By selectElement = By
							.cssSelector("#tblInboxPendiente > tbody > tr:nth-child(" + row + ") > td:nth-child(2)");
					u.hardWait(1);
					aux = true;
					break;
				}
			}
			if (!aux) {
				throw new AssertionError("No se encontro coincidencia en la tabla");
			}
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al buscar en la tabla");
		}

		return results;
	}

	/**
	 * Selecciona en la tabla de Pendientes el checkbox del codigo de barras
	 * esperado.
	 * 
	 * @param cb -> Codigo de barras
	 * @author Esmeralda Garcia Torres
	 */
	public void seleccionarItemPendiente(String cb) {
		String[] result = searchInTable(cb);
		By selectElement = By.cssSelector(
				"#tblInboxPendiente > tbody > tr:nth-child(" + Integer.parseInt(result[0]) + ") > td > input");
		u.click(selectElement);
		pom.waitLoadingModal();
	}

	/**
	 * Verifica en la tabla de Pendientes para un cb esperado, el valor de una
	 * columna especifica.
	 * 
	 * @param cb      ->Codigo de barras
	 * @param columna ->Numero de la columna
	 * @param valor   -> Valor esperado
	 * @return true o false
	 * @author Esmeralda Garcia Torres
	 */
	private boolean verificarColumna(String cb, Integer columna, String valor) {
		Boolean aux = false;
		try {
			String[] result = searchInTable(cb);
			String[] datos = result[1].split("\t");
			if (datos[columna].equalsIgnoreCase(valor)) {
				pom.logM("Se encontro: " + datos[columna]);
				aux = true;
			} else {
				pom.logM("Envio Muestra - " + datos[columna]);
			}
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al buscar la columna");
		}
		return aux;
	}

	/**
	* Espera hasta que la pantalla "Inbox Pendientes Configuración" esté lista.
	*
	* @param elemento ->El selector del elemento que confirma que la pantalla está cargada.
	* @author Isaac E. García
	*/
	public void esperarPantallaLista(By elemento) {
	    // Esperar hasta que el elemento específico sea visible
	    wait.until(ExpectedConditions.visibilityOfElementLocated(elemento));
	}
	
	/**
	 * Revisa que en el Inbox de Pendientes el registro esperado tenga "OK" en las
	 * columnas de Conjuntos, Fuente Abasto y Envio Muestra Fisica.
	 * 
	 * @param cb -> Codigo de barras
	 * @author Esmeralda Garcia Torres
	 */
	private void todoOK(String cb) {
		try {
			String[] result = searchInTable(cb);
			String[] datos = result[1].split("\t");
			if (datos[6].equalsIgnoreCase("OK") && datos[7].equalsIgnoreCase("OK") && datos[8].equalsIgnoreCase("OK")) {
				pom.logM("TODO OK");
			} else {
				throw new AssertionError(
						"La columna de Conjuntos o Fuente Abasto o Envio Muestra Fisica, no esta en OK");
			}
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al buscar la columna");
		}
	}

	private void todoOKPlaza(String cb) {
		try {
			// String[] result = searchInTable(cb);
			String[] result = searchInTableGeneral("TablaPendientes", tablePendienteCatalogos, cb);
			String[] datos = result[1].split("\t");
			if (datos[6].equalsIgnoreCase("OK") && datos[7].equalsIgnoreCase("OK") && datos[8].equalsIgnoreCase("OK")) {
				pom.logM("TODO OK");
			} else {
				throw new AssertionError(
						"La columna de Conjuntos o Fuente Abasto o Envio Muestra Fisica, no esta en OK");
			}
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al buscar la columna");
		}
	}

	/**
	 * Revisa que en el Inbox de Pendientes el registro esperado tenga "OK" en las
	 * columnas de Conjuntos, Fuente Abasto y Envio Muestra Fisica.
	 * 
	 * @param cb -> Codigo de barras
	 * @author Esmeralda Garcia Torres
	 */
	public void verificarColumnasEnOK(String cb) {
		todoOK(cb);
	}

	public void verificarColumnasEnOKPlaza(String cb) {
		todoOKPlaza(cb);
	}

	/**
	 * Verifica en la tabla de Pendientes que para un cb en especifico se muestre un
	 * texto esperado en la columna de Conjuntos.
	 * 
	 * @param cb    -> Codigo de barras
	 * @param texto -> Valor esperado
	 * @return true o false
	 * @author Esmeralda Garcia Torres
	 */
	public boolean verificarConjuntos(String cb, String texto) {
		return verificarColumna(cb, 6, texto);
	}

	/**
	 * Verifica en la tabla de Pendientes que para un cb en especifico se muestre un
	 * texto esperado en la columna de Fuente Abasto.
	 * 
	 * @param cb    -> Codigo de barras
	 * @param texto -> Valor esperado
	 * @return true o false
	 * @author Esmeralda Garcia Torres
	 */
	public boolean verificarFuenteAbasto(String cb, String texto) {
		return verificarColumna(cb, 7, texto);
	}

	/**
	 * Verifica en la tabla de Pendientes que para un cb en especifico se muestre un
	 * texto esperado en la columna de Envio Muestra Fisica.
	 * 
	 * @param cb    -> Codigo de barras
	 * @param texto -> Valor esperado
	 * @return true o false
	 * @author Esmeralda Garcia Torres
	 */
	public boolean verificarEnvioMuestraFisica(String cb, String texto) {
		return verificarColumna(cb, 8, texto);
	}

	/**
	 * Selecciona la opcion esperada del select Estatus en la pantalla de Inbox
	 * Muestra Fisica.
	 * 
	 * @param valor -> Enviado, Todas,
	 * @author Esmeralda Garcia Torres
	 */
	public void selectEstatusInboxMuestraFisica(String valor) {
		pom.selectOption("Estatus Inbox Catalogos Muestra Fisica", valor, selectEstatusCatalogos);
	}

	/**
	 * Busca y selecciona en el menu la opcion "Inbox Catalogo Muestra Fisica".
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void menuInboxCatalogoMuestraFisica() {
		try {
			u.hardWait(1);
			pom.waitLoadingModal();
			wait.until(ExpectedConditions.elementToBeClickable(buttonMenuInbox));
			u.highLight(buttonMenuInbox);
			u.click(buttonMenuInbox);
			u.hardWait(1);
			pom.waitLoadingModal();

			// pom.clickOpcion("Menu", buttonMenuInbox);
			u.hardWait(1);
			buscarEnMenu("Inbox de Catálogos de Muestra Física");
			pom.waitLoadingModal();
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar la opcion Menu/Inbox de Catalogos de Muestra Fisica");
		}
	}

	/**
	 * Busca y selecciona en el menu la opcion "Inbox de Pendientes de Catalogos".
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void menuInboxPendientesCatalogos() {
		try {
			u.hardWait(1);
			pom.waitLoadingModal();
			wait.until(ExpectedConditions.elementToBeClickable(buttonMenuInbox));
			u.highLight(buttonMenuInbox);
			u.click(buttonMenuInbox);
			u.hardWait(1);
			pom.waitLoadingModal();

			// pom.clickOpcion("Menu", buttonMenuInbox);
			u.hardWait(1);
			buscarEnMenu("Pendientes de Catálogos");
			pom.waitLoadingModal();
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar la opcion Menu/Inbox Pendientes de Catalogos");

		}
	}

	public void menuInboxMovimientosOrdenados() {
		try {
			u.hardWait(1);
			pom.waitLoadingModal();
			wait.until(ExpectedConditions.elementToBeClickable(buttonMenuInbox));
			u.highLight(buttonMenuInbox);
			u.click(buttonMenuInbox);
			u.hardWait(2);
			pom.waitLoadingModal();

			buscarEnMenu("Movimientos Ordenados");
			pom.waitLoadingModal();
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar la opcion Menu/Inbox Pendientes de Catalogos");
		}
	}
	
	public void menuInboxValidacionCategory() {
		try {
			u.hardWait(2);
			pom.waitLoadingModal();
			wait.until(ExpectedConditions.elementToBeClickable(buttonMenuInbox));
			u.highLight(buttonMenuInbox);
			u.click(buttonMenuInbox);
			u.hardWait(1);
			pom.waitLoadingModal();

			buscarEnMenu("de Category");
			pom.waitLoadingModal();
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar la opcion Menu/Inbox de Validacion de Category");
		}
	}

	/**
	 * Busca la opcion solicitada en el menu.
	 * 
	 * @param opcion
	 * @author Esmeralda Garcia Torres
	 */
	private void buscarEnMenu(String opcion) {
		try {
			pom.waitLoadingModal();
			String[] result = searchInTableGeneral("Menu inbox", listMenuInbox, opcion);
			By selectElement = By.cssSelector("li.ng-scope:nth-child(" + result[0] + ")");
			pom.clickOpcion("Opcion Menu", selectElement);
			u.hardWait(1);
			pom.waitLoadingModal();
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar la opcion del Menu");
		}
	}

	/**

	 * Realiza la busqueda de un dato dentro de una tabla. Si en las columnas de un
	 * registro se encuentra el dato solicitado regresa un String[] en donde la
	 * posicion 0 es la fila en que se encontro y la posicion 1 es el innerText de
	 * la fila.
	 * 
	 * @param tableName -> Nombre de la tabla
	 * @param tableBy
	 * @param data      -> Dato a buscar
	 * @return String[]
	 * @author Esmeralda Garcia Torres
	 */
	private String[] searchInTableGeneral(String tableName, By tableBy, String data) {
		pom.waitLoadingModal();
		String[] results = new String[2];
		results[0] = "";
		results[1] = "";
		try {
			Boolean aux = false;
			Integer row = 0;
			List<WebElement> elementsTable = u.getDriver().findElements(tableBy);
			pom.logM("Elementos en la Tabla: " + elementsTable.size());
			if (elementsTable.size() == 0) {
				u.hardWait(2);
			}
			for (int i = 0; i < elementsTable.size(); i++) {
				String element = elementsTable.get(i).getAttribute("innerText").toUpperCase();
				pom.logM(element + " : " + data);
				if (element.toUpperCase().contains(data.toUpperCase())) {
					row = i + 1;
					results[0] = Integer.toString(row);
					results[1] = element;
					pom.logM("Se encontro en row: " + row);
					aux = true;
					break;
				}
			}
			if (!aux) {
				throw new AssertionError("No se encontro coincidencia en la tabla");
			}
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al buscar en la tabla");
		}

		return results;
	}

	/**

	 * Selecciona la categoria esperada en la Tabla de Catalogos.
	 * 
	 * @param categoria
	 * @author Esmeralda Garcia Torres
	 */
	public void seleccionarCategoriaTablaCatalogos(String categoria) {
		try {
			pom.waitLoadingModal();

			String[] result = searchInTableGeneral("Categoria Catalogos", tablePendienteCatalogos, categoria);
			By selectElement = By.cssSelector("tr.ng-scope:nth-child(" + result[0] + ") > td:nth-child(1) > input");
			u.highLight(selectElement);
			u.unhighLight(selectElement);
			u.click(selectElement);
			u.hardWait(1);
			pom.waitLoadingModal();
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar la categoria");
		}
	}

	/**
	 * Selecciona el codigo de barras esperado en la tabla de Inbox Catalogos.
	 * 
	 * @param cb -> Codigo de barras
	 * @author Esmeralda Garcia Torres
	 */
	public void seleccionarItemInboxCatalogos(String cb) {
		try {
			pom.waitLoadingModal();
			String[] result = searchInTableGeneral("Item Inbox Catalogos", tablePendienteCatalogos, cb);
			By selectElement = By.cssSelector("tr.ng-scope:nth-child(" + result[0] + ") > td:nth-child(1) > input");
			u.highLight(selectElement);
			u.unhighLight(selectElement);
			u.click(selectElement);
			u.hardWait(1);
			pom.waitLoadingModal();
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar el Item en inbox catalogos");
		}
	}

	/**
	 * Selecciona el codigo de barras esperado en la tabla de Inbox Altas Catalogos.
	 * 
	 * @param cb -> Codigo de barras
	 * @author Esmeralda Garcia Torres
	 */
	public void seleccionarItemInboxAltasCatalogos(String cb) {
		try {
			pom.waitLoadingModal();
			String[] result = searchInTableGeneral("Item Inbox Altas Catalogos", tablePendienteCatalogos, cb);
			By selectElement = By.cssSelector("tr.ng-scope:nth-child(" + result[0] + ") > td:nth-child(1) > input");
			u.highLight(selectElement);
			u.unhighLight(selectElement);
			u.click(selectElement);
			u.hardWait(1);
			pom.waitLoadingModal();
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar el Item en inbox altas catalogos");
		}
	}

	public void seleccionarItemInboxEnvioMuestraFisica(String cb) {
		try {
			pom.waitLoadingModal();
			String[] result = searchInTableGeneral("Item Inbox Envio Muestra Fisica", tableEnvioMuestraFisica, cb);
			By selectElement = By.cssSelector("tr.ng-scope:nth-child(" + result[0] + ") > td:nth-child(1) > input");
			u.highLight(selectElement);
			u.unhighLight(selectElement);
			u.click(selectElement);
			u.hardWait(1);
			pom.waitLoadingModal();
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar el Item en inbox Envio Muestra Fisica");
		}
	}

	public void rechazadosCatalogos() {
		pom.clickOpcion("Rechazados de Catalogos", buttonRechazadosCatalogos);
	}

	public void aceptarRechazoDatosBasicos() {
		pom.clickOpcion("Aceptar Rechazo en Datos Basicos", buttonAceptarRechazoDatosBasicos);
		pom.optionMsgModal("SI RECHAZO");
		pom.optionMsgModal("OK");
	}

	public void aceptarRechazoFA() {
		pom.clickOpcion("Aceptar Rechazo en Fuente Abasto", buttonAceptarRechazoDatosBasicos);
		pom.optionMsgModal("SI RECHAZO");
		pom.optionMsgModal("OK");
	}

	public void changeUserAlterno(String user) {
		String dominio = u.getDriver().findElement(inputUserAlterno).getAttribute("value");
		pom.enterOption("Usuario Alterno", dominio + user, inputUserAlterno);

		pom.clickOpcion("Cambiar Usuario", buttonChangeUser);
	}

	/**
	 * Busca en la tabla de Pendientes el codigo de Barras esperado.
	 * 
	 * @param cb -> Codigo de Barras
	 * @param descripcionCorta -> Descripcón corta
	 * @return String[2] posicion 0 contiene la fila en la que se encontro. Posicion
	 *         1 contiene el innerText de la fila.
	 * @author Isaac Emmanuel García García
	 */
	private String[] searchInTableDouble(String cb, String descripcionCorta) {
		String[] results = new String[2];
		results[0] = "";
		results[1] = "";
		try {
			Boolean aux = false;
			Integer row = 0;
			List<WebElement> elementsTable = u.getDriver().findElements(tablePendientes);
			if (elementsTable.size() == 0) {
				u.hardWait(3);
			}
			pom.logM("Elementos en la Tabla: " + elementsTable.size());
			for (int i = 0; i < elementsTable.size(); i++) {
				String element = elementsTable.get(i).getAttribute("innerText").toUpperCase();
				pom.logM(element + " : " + cb);
				if (element.toUpperCase().contains(cb.toUpperCase()) && element.toUpperCase().contains(descripcionCorta.toUpperCase())) {
					row = i + 1;
					results[0] = Integer.toString(row);
					results[1] = element;
					pom.logM("Se encontro en row: " + row);
					By selectElement = By
							.cssSelector("#tblInboxPendiente > tbody > tr:nth-child(" + row + ") > td:nth-child(2)");
					u.hardWait(1);
					aux = true;
					break;
				}
			}
			if (!aux) {
				throw new AssertionError("No se encontro coincidencia en la tabla");
			}
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al buscar en la tabla");
		}

		return results;
	}
	/**
	* Selecciona el checkbox en la tabla de Envío de Muestra Física basándose en el
	* código de barras (plu) y la descripción corta proporcionados.
	*
	* @param cb -> Código de barras esperado
	* @param descripcionCorta -> Descripción corta esperada
	* @author Isaac E. García
	*/
	public void seleccionarItemPendientePLU(String cb, String descripcionCorta) {
		u.waitWebElement(tituloVentana, waitTime, 60);
		pom.waitLoadingModal();
		esperarPantallaLista(tituloVentana);
		String[] result = searchInTableDouble(cb, descripcionCorta);
		By selectElement = By.cssSelector(
				"#tblInboxPendiente > tbody > tr:nth-child(" + Integer.parseInt(result[0]) + ") > td > input");
		u.click(selectElement);
		pom.waitLoadingModal();
	}
	
	/**
	 * Selecciona el codigo de barras esperado en la tabla de Inbox Altas Catalogos.
	 * 
	 * @param cb -> Codigo de barras
	 * @author Isaac E. García
	 */
	public void seleccionarItemInboxAltasCatalogosPLU(String cb, String descripcionCorta) {
		try {
			pom.waitLoadingModal();
			String[] result = searchInTableDouble(cb, descripcionCorta);
			By selectElement = By.cssSelector("tr.ng-scope:nth-child(" + result[0] + ") > td:nth-child(1) > input");
			u.highLight(selectElement);
			u.unhighLight(selectElement);
			u.click(selectElement);
			u.hardWait(1);
			pom.waitLoadingModal();
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar el Item en inbox altas catalogos");
		}
	}
	
	/**
	 * Busca en la tabla Inbox de Catálogos Muestra Física el item con PLU 0 y la descripcionCorta especificada.
	 * @param descripcionCorta
	 * @return String[]
	 * @author Angel Del Rio
	 */
	private String[] searchInTableMF(String descripcionCorta) {
		pom.waitLoadingModal();
		String[] results = new String[2];
		results[0] = "";
		results[1] = "";
		try {
			Boolean aux = false;
			Integer row = 0;
			List<WebElement> elementsTable = u.getDriver().findElements(tablePendienteCatalogos);
			pom.logM("Elementos en la Tabla: " + elementsTable.size());
			if (elementsTable.size() == 0) {
				u.hardWait(2);
			}
			for (int i = 0; i < elementsTable.size(); i++) {
				String element = elementsTable.get(i).getAttribute("innerText").toUpperCase();
				pom.logM(element + " : " + descripcionCorta);
				if (element.toUpperCase().contains("0\t" + descripcionCorta.toUpperCase())) {
					pom.logM("Coincidencia con la descripcion");
					row = i + 1;
					results[0] = Integer.toString(row);
					results[1] = element;
					pom.logM("Se encontro en row: " + row);
					aux = true;
					break;
				}
			}
			if (!aux) {
				throw new AssertionError("No se encontro coincidencia en la tabla");
			}
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al buscar en la tabla");
		}

		return results;
	}
	
	/**
	 * Selecciona en la tabla Inbox de Catálogos Muestra Física el item con PLU 0 y la descripcionCorta especificada.
	 * @param descripcionCorta
	 * @author Angel Del Rio
	 */
	public void seleccionarItemInboxCatalogosPLU(String descripcionCorta) {
		try {
			pom.waitLoadingModal();
			String[] result = searchInTableMF(descripcionCorta);
			System.out.println(result);
			By selectElement = By.cssSelector("tr.ng-scope:nth-child(" + result[0] + ") > td:nth-child(1) > input");
			u.highLight(selectElement);
			u.unhighLight(selectElement);
			u.click(selectElement);
			u.hardWait(1);
			pom.waitLoadingModal();
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar el Item en inbox catalogos");
		}
	}
}
