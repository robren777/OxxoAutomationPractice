package WorkFlows.util.Siv;

import java.time.Duration;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class utilSiv_EnvioMuestraFisica {
	private SeleniumUtil u;
	private TestCase testCase;
	private pomSIV pom;
	private WebDriverWait wait;

	/// WEB ELEMENTS INBOX ENVIO DE MUESTRA FISICA ///
	By tableEnvioMuestraFisica = By.cssSelector("table.table > tbody > tr");
	By buttonCapturarNuevoEnvio = By.cssSelector("input[value=\"Capturar Nuevo Env�o\"]");
	By buttonEnviarMuestraFisica = By.cssSelector("input[value=\"Enviar\"]");
	By buttonSalirMuestraFisica = By.cssSelector("input[value=\"Salir\"]");

	By selectMetodoEnvio = By.id("ddlMetodoEnvio"); // Entrega Personal, Paqueteria, Sin Muestra Fisica, Upload
													// Fotografia
	By inputPersonaQueEnvia = By.id("txtPersonaEnvia");
	By inputEmpresaPaqueteria = By.id("txtEmpresaPaqueteria");
	By inputDireccionRecepcion = By.id("txtDireccionRecepcionMF");
	By inputInfoAdicional = By.id("txtInformacionAdicional");
	By inputTelefono = By.id("txtTelefono");
	By inputNumGuia = By.id("txtNumeroGuia");
	By inputFechaEnvio = By.id("txtFechaEnvio");
	By imgFechaEnvio = By.cssSelector("input#txtFechaEnvio ~ a > img");
	By inputFechaEsperada = By.id("txtFechaEsperadaArribo");
	By imgFechaEsperada = By.cssSelector("input#txtFechaEsperadaArribo ~ a > img");
	By buttonExaminar = By.id("fileFotografia");
	By inputFileFotografia = By.id("fileFotografia");
	By buttonCargaArchivo = By.cssSelector("input[value=\"Carga Archivo\"]");
	By buttonGrabarEnvio = By.id("btnGrabarEnvio");
	By buttonSalir = By.id("btnSalir");

	/**
	 * Constructor. Recibe el SeleniumUtil utilizado en el test.
	 * 
	 * @param SeleniumUtil
	 * @author Esmeralda Garcia Torres
	 */
	public utilSiv_EnvioMuestraFisica(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		this.pom = new pomSIV(u, testCase);
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

	/**
	 * - Entrega personal: Persona que envia y Telefono - Paqueteria: Persona que
	 * envia, Empresa Paqueteria, Direccion de Recepcion, Info Adicional, Telefono,
	 * NumGuia, Fecha envio, Fecha Esperada. - Sin Muestra fisica: NA - Upload
	 * Fotografia: Persona que envia, Empresa Paqueteria, Direccion de Recepcion,
	 * Info Adicional, Telefono, NumGuia, Fecha envio, Fecha Esperada, Examinar,
	 * Cargar Envio.
	 */

	public void capturarNuevoEnvio() {
		pom.clickOpcion("Capturar Nuevo Envio", buttonCapturarNuevoEnvio);
	}

	public void enviarMuestraFisica() {
		pom.clickOpcion("Enviar Muestra Fisica", buttonEnviarMuestraFisica);
	}

	public void salirMuestraFisica() {
		pom.clickOpcion("Salir Muestra Fisica", buttonSalirMuestraFisica);
	}

	public void entregaPersonal(String personaEnvia, String telefono) {
		pom.selectOption("Metodo de envio: Entrega Personal", "Entrega Personal", selectMetodoEnvio);
		pom.enterOption("Persona que Envia", personaEnvia, inputPersonaQueEnvia);
		pom.enterOption("Telefono", telefono, inputTelefono);
	}

	public void paqueteria(String personaEnvia, String empresaPaqueteria, String direccionRecepcion,
			String infoAdicional, String telefono, String numGuia, String fechaEnvio, String fechaEsperada) {
		pom.selectOption("Metodo de envio: Paqueteria", "Paqueter�a", selectMetodoEnvio);
		pom.enterOption("Persona que envia", personaEnvia, inputPersonaQueEnvia);
		pom.enterOption("Empresa Paqueteria", empresaPaqueteria, inputEmpresaPaqueteria);
		pom.enterOption("Direccion de Recepcion", direccionRecepcion, inputDireccionRecepcion);
		pom.enterOption("Informacion Adicional", infoAdicional, inputInfoAdicional);
		pom.enterOption("Telefono", telefono, inputTelefono);
		pom.enterOption("Numero de Guia", numGuia, inputNumGuia);
		
		seleccionarFecha(imgFechaEnvio, fechaEnvio);
		seleccionarFecha(imgFechaEsperada, fechaEsperada);
	}

	public void sinMuestraFisica() {
		pom.selectOption("Metodo de envio: Sin Muestra Fisica", "Sin Muestra F�sica", selectMetodoEnvio);
	}

	public void uploadFotografia(String personaEnvia, String empresaPaqueteria, String direccionRecepcion,
			String infoAdicional, String telefono, String numGuia, String fechaEnvio, String fechaEsperada,
			String rutaImagen) {
		u.hardWait(1);
		pom.selectOption("Metodo de envio: Upload Fotografia", "Upload Fotograf�a", selectMetodoEnvio);
		u.hardWait(2);
		pom.enterOption("Persona que envia", personaEnvia, inputPersonaQueEnvia);
		pom.enterOption("Empresa Paqueteria", empresaPaqueteria, inputEmpresaPaqueteria);
		pom.enterOption("Direccion de Recepcion", direccionRecepcion, inputDireccionRecepcion);
		pom.enterOption("Informacion Adicional", infoAdicional, inputInfoAdicional);
		pom.enterOption("Telefono", telefono, inputTelefono);
		pom.enterOption("Nmero de Guia", numGuia, inputNumGuia);

		seleccionarFecha(imgFechaEnvio, fechaEnvio);
		seleccionarFecha(imgFechaEsperada, fechaEsperada);

		pom.enterOption("Ruta del archivo", rutaImagen, inputFileFotografia);
		pom.clickOpcion("Cargar Archivo", buttonCargaArchivo);
		u.hardWait(1);
		if (pom.msgModal().contains("Imagen Cargada Correctamente")) {
			pom.optionMsgModal("OK");
		} else {
			throw new AssertionError("Fallo al guardar la imagen");
		}

	}

	public void grabarEnvio() {
		pom.clickOpcion("Grabar Envio", buttonGrabarEnvio);
		if (pom.msgModal().contains("Falta capturar")) {
			throw new AssertionError("Falto Capturar Informacion");
		} else {
			pom.optionMsgModal("OK");
		}

	}

	public void salir() {
		pom.clickOpcion("Salir de Enviar Muestra Fisica", buttonSalir);
	}

	public void seleccionarFecha(By imgCalendario, String fecha) {
		try {
			int expectedDay;
			int expectedMonth;
			int expectedYear;

			Calendar c = new GregorianCalendar();
			int day = c.get(Calendar.DATE);
			int month = c.get(Calendar.MONTH) + 1;
			int year = c.get(Calendar.YEAR);

			String[] expectedDate = fecha.split("/");
			expectedDay = Integer.parseInt(expectedDate[0]);
			expectedMonth = Integer.parseInt(expectedDate[1]);
			expectedYear = Integer.parseInt(expectedDate[2]);

			pom.logM("Dias: " + expectedDay + " : " + day);
			pom.logM("Mes: " + expectedMonth + " : " + month);
			pom.logM("Anio: " + expectedYear + " : " + year);

			if (expectedYear <= year && expectedMonth <= month && expectedDay <= day) {
				throw new AssertionError("La fecha no puede ser menor a la fecha actual");
			}

			wait.until(ExpectedConditions.elementToBeClickable(imgCalendario));
			u.click(imgCalendario);
			u.hardWait(1);

			By titleMonth = By.xpath("//tr//th//button[contains(@class,'uib-title')]//strong");
			String title = u.getDriver().findElement(titleMonth).getAttribute("innerText");
			pom.logM("Title: " + title);

			String[] txtMonth = { "enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto",
					"septiembre", "octubre", "noviembre", "diciembre" };
			String monthYearAux = txtMonth[expectedMonth - 1] + " " + expectedYear;
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
			if (expectedDay < 10) {
				dday = "0" + String.valueOf(expectedDay);
			} else {
				dday = String.valueOf(expectedDay);
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
			List<WebElement> elementsTable = u.getDriver().findElements(tableEnvioMuestraFisica);
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
	 * Busca en la tabla de Envío de Muestra Física el codigo de Barras esperado.
	 * 
	 * @param cb -> Codigo de Barras
	 * @param descripcionCorta -> Descripcón corta
	 * 
	 * @author Isaac Emmanuel García García
	 */
	public void seleccionarItemInboxEnvioMuestraFisicaPLU(String cb, String descripcionCorta) {
		try {
			pom.waitLoadingModal();
			String[] result = searchInTableDouble(cb, descripcionCorta);
			//String[] result = searchInTableGeneral("Item Inbox Envio Muestra Fisica", tableEnvioMuestraFisica, cb);
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
	
}
