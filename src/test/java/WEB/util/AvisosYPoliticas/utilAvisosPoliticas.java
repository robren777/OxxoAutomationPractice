package WEB.util.AvisosYPoliticas;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import modelo.TestCase;
import WEB.util.AuxMethods;
import utils.selenium.SeleniumUtil;

public class utilAvisosPoliticas {
	private SeleniumUtil u;
	private AuxMethods aux;
	private TestCase testCase;

	/// WEB ELEMENTS ///
	By portalGestionProveedores = By.linkText("Gesti�n de Proveedores");
	// By menuAvisosPoliticas = By.linkText("Avisos y politicas");
	By menuAvisosPoliticas = By.cssSelector("#ddtopmenubar > ul > li > a[rel=\"ddsubmenu20240\"]");
	By menuAdministracion = By.cssSelector("#ddtopmenubar > ul > li > a[rel=\"ddsubmenu20184\"]");
	By opcionAvisosPoliticas = By.id("j_idt194");
	By opcionAltaAvisosPoliticas = By.id("j_idt190");
	By opcionReportesAvisosPoliticas = By.id("j_idt198");

	By menuFinanzas = By.cssSelector("#ddtopmenubar > ul > li > a[rel=\"ddsubmenu10004\"]");
	By opcCargaArchivoR = By.id("j_idt160");
	By inputCarga = By.cssSelector("input[name=\"form:file\"]");
	By buttonCargar = By.cssSelector("input[name=\"form:chargeBtn\"]");

	By listaAvPo = By.className("ui-accordion-header");
	By frameAvPo = By.cssSelector("#contentFrame > iframe");

	By fechaIni = By.id("massive-cost-change-form:start-date_input");
	By fechaFin = By.id("massive-cost-change-form:final-date_input");
	By selectEstatus = By.id("massive-cost-change-form:status_input");
	By buttonBuscar = By.cssSelector("[name=\"massive-cost-change-form:j_idt31\"]");
	By tablaBuscarAP = By.cssSelector("div.ui-datatable-tablewrapper > table > tbody > tr");

	By labelEstatus = By.cssSelector(".ui-selectonemenu-label");
	By opTodos = By.cssSelector("[data-label=\"Todos\"]");
	By opBorrador = By.cssSelector("[data-label=\"Borrador\"]");
	By opExpirado = By.cssSelector("[data-label=\"Expirado\"]");
	By opPublicado = By.cssSelector("[data-label=\"Publicado\"]");
	By opPausado = By.cssSelector("[data-label=\"Pausado\"]");
	By paginator = By.cssSelector(".ui-paginator-bottom");

	By buttonCrearNuevo = By.cssSelector("button[name=\"massive-cost-change-form:j_idt33\"]");
	By inputNombreAV = By.cssSelector("input[name=\"massive-cost-change-form:form-policy-name\"]");
	By inputContenidoAV = By.cssSelector("textarea[name=\"massive-cost-change-form:form-policy-contents\"]");

	By buttonPublicarAV = By.cssSelector("button[name=\"massive-cost-change-form:j_idt89\"]");
	By buttonEliminarAV = By.cssSelector("button[name=\"massive-cost-change-form:j_idt90\"]");
	By buttonConfirAceptar = By.id("j_idt100");

	By inputUsuarioAV = By.cssSelector("input[name=\"massive-cost-change-form:user\"]");
	By opReportesTodos = By.cssSelector("[data-label=\"Todos\"]");
	By opReportesAceptado = By.cssSelector("[data-label=\"Aceptado\"]");
	By opReportesRechazado = By.cssSelector("[data-label=\"Rechazado\"]");
	By opReportesPendiente = By.cssSelector("[data-label=\"Pendiente\"]");
	By buttonReportesBuscar = By.cssSelector("[name=\"massive-cost-change-form:j_idt40\"]");

	/**
	 * Constructor. Recibe el SeleniumUtil y el testCase utilizado en el test.
	 * 
	 * @param SeleniumUtil
	 * @param testCase
	 * @author Esmeralda Garcia Torres
	 */
	public utilAvisosPoliticas(SeleniumUtil u, TestCase testCase) {
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
	}

	/**
	 * Selecciona el menu "Avisos y Politicas" y el submenu "Avisos y Politicas".
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void goToAvisosPoliticas() {
		try {
			WebElement ele = u.getDriver().findElement(menuAvisosPoliticas);
			Actions action = new Actions(u.getDriver());
			action.moveToElement(ele).perform();
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Fallo el over");
		}
		aux.clickOpcion("Avisos y Politicas", opcionAvisosPoliticas, true);
		u.hardWait(1);
	}

	/**
	 * Selecciona el menu "Administracion" y el submenu "Alta de Avisos y
	 * Politicas".
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void goToAltaAvisosPoliticas() {
		try {
			WebElement ele = u.getDriver().findElement(menuAdministracion);
			Actions action = new Actions(u.getDriver());
			action.moveToElement(ele).perform();
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Fallo el over");
		}
		aux.clickOpcion("Administracion", opcionAltaAvisosPoliticas, true);
		u.hardWait(1);
	}

	/**
	 * Selecciona el menu "Administracion" y el submenu "Reportes de Avisos y
	 * Politicas"
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void goToReportesAvisosPoliticas() {
		try {
			WebElement ele = u.getDriver().findElement(menuAdministracion);
			Actions action = new Actions(u.getDriver());
			action.moveToElement(ele).perform();
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Fallo el over");
		}
		aux.clickOpcion("Administracion", opcionReportesAvisosPoliticas, true);
		u.hardWait(1);
	}

	/**
	 * Obtiene los nombres y toma evidencia de los avisos y politicas que se
	 * muestran en pantalla.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void getAvisosPoliticas() {
		try {
			WebElement fr = u.getDriver().findElement(frameAvPo);
			u.getDriver().switchTo().frame(fr);

			String aviPoli = "";
			Integer cont = 1;
			List<WebElement> list = u.getDriver().findElements(listaAvPo);
			aux.logS("Tamanio de la lista: " + list.size());
			for (WebElement inputElement : list) {
				aux.logS(inputElement.getAttribute("innerText"));
				aviPoli = aviPoli + inputElement.getAttribute("innerText") + "\n";
				inputElement.click();
				u.hardWait(1);
				if (cont % 8 == 0) {
					testCase.addScreenShotCurrentStep(u, "Lista");
				}
				cont++;
			}
			testCase.addScreenShotCurrentStep(u, "AP");
			testCase.addBoldTextEvidenceCurrentStep("Lista de Avisos y Politicas");
			testCase.addTextEvidenceCurrentStep(aviPoli);
			u.hardWait(2);
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Fallo al minimizar las politicas");
		}
	}

	/**
	 * Ingresa la fecha de Inicio y la Final para realizar el reporte.
	 * 
	 * @param fechaInicio
	 * @param fechaFinal
	 * @author Esmeralda Garcia Torres
	 */
	public void asignarFechas(String fechaInicio, String fechaFinal) {
		try {
			aux.changeAttributeValue(fechaIni, fechaInicio, false);
			aux.changeAttributeValue(fechaFin, fechaFinal, false);
			u.hardWait(1);
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al ingresar las fechas");
		}
	}

	/**
	 * Hace la busqueda de los Avisos y Politicas con el estatus indicado.
	 * 
	 * @param estatus
	 * @author Esmeralda Garcia Torres
	 */
	public void buscarAvisosPoliticas(String estatus) {
		try {
			aux.clickOpcion("estatus", labelEstatus, false);
			switch (estatus.toUpperCase()) {
			case "TODOS":
				aux.clickOpcion("Estatus Todos", opTodos, false);
				break;
			case "BORRADOR":
				aux.clickOpcion("Estatus Borrador", opBorrador, false);
				break;
			case "EXPIRADO":
				aux.clickOpcion("Estatus Expirado", opExpirado, false);
				break;
			case "PUBLICADO":
				aux.clickOpcion("Estatus Publicado", opPublicado, false);
				break;
			case "PAUSADO":
				aux.clickOpcion("Estatus Pausado", opPausado, false);
				break;
			default:
				throw new AssertionError("No es un valor valido: " + estatus);
			}
			u.hardWait(1);
			aux.clickOpcion("Buscar", buttonBuscar, false);
			u.hardWait(5);
			aux.scrollToElement(paginator);
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Fallo al buscar por estatus: " + estatus);
		}
	}

	/**
	 * Ingresa el usuario y el estatus para realizar la busqueda del reporte.
	 * 
	 * @param nombre
	 * @param estatus
	 * @author Esmeralda Garcia Torres
	 */
	public void buscarReporteAvisosPoliticas(String nombre, String estatus) {
		try {
			aux.changeAttributeValue(inputUsuarioAV, nombre, false);

			aux.clickOpcion("estatus", labelEstatus, false);
			switch (estatus.toUpperCase()) {
			case "TODOS":
				aux.clickOpcion("Estatus Todos", opReportesTodos, false);
				break;
			case "ACEPTADO":
				aux.clickOpcion("Estatus Aceptado", opReportesAceptado, false);
				break;
			case "RECHAZADO":
				aux.clickOpcion("Estatus Rechazado", opReportesRechazado, false);
				break;
			case "PENDIENTE":
				aux.clickOpcion("Estatus Pendiente", opReportesPendiente, false);
				break;
			default:
				throw new AssertionError("No es un valor valido: " + estatus);
			}
			u.hardWait(1);
			aux.clickOpcion("Buscar", buttonReportesBuscar, false);
			u.hardWait(2);
			aux.scrollToElement(paginator);
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Fallo al buscar por estatus: " + estatus);
		}
	}

	/**
	 * Crea un aviso y politica.
	 * 
	 * @param nombre
	 * @param contenido
	 * @author Esmeralda Garcia Torres
	 */
	public void crearAvisoPolitica(String nombre, String contenido) {
		try {
			aux.clickOpcion("CrearAvPo", buttonCrearNuevo, true);
			aux.enterOption("NombreAvPo", nombre, inputNombreAV, false);
			aux.enterOption("ContenidoAvPo", contenido, inputContenidoAV, false);
			aux.clickOpcion("Publicar", buttonPublicarAV, true);
			u.hardWait(1);
			aux.clickOpcion("Aceptar", buttonConfirAceptar, true);
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Ocurrio un error al crear un Aviso o Politica");
		}
	}

	/**
	 * Hace cambio al frame en el que estan los elementos para poder localizarlos.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void switchFrame() {
		try {
			WebElement fr = u.getDriver().findElement(frameAvPo);
			u.getDriver().switchTo().frame(fr);
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("No se pudo cambiar de frame");
		}
	}

	/**
	 * Busca en la tabla de Avisos y Politicas un registro que tenga un valor y
	 * estatus especifico.
	 * 
	 * @param valor
	 * @param estatus
	 * @return la posicion en la que se encontro el registro
	 * @author Esmeralda Garcia Torres
	 */
	public String buscarTablaAvPo(String valor, String estatus) {
		try {
			By rowBuscarAP;
			String[] finRow = aux.searchInTableGeneral("Avisos y Politicas", tablaBuscarAP, valor, estatus);
			if (finRow[0] != null) {
				rowBuscarAP = By.cssSelector(
						"div.ui-datatable-tablewrapper > table > tbody > tr[data-ri=\"" + finRow[0] + "\"]");
				u.highLight(rowBuscarAP);
				u.hardWait(1);
				testCase.addScreenShotCurrentStep(u, "Row");
				u.unhighLight(rowBuscarAP);
				return finRow[0];
			} else {
				testCase.addScreenShotCurrentStep(u, "No se encontro");
				return null;
			}
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Hubo un error al ubicar el elemento en la tabla");
		}
	}

	/**
	 * Valida que todos los registros de una tabla tengan uno o dos valores
	 * especificos.
	 * 
	 * @param valor1
	 * @param valor2
	 * @return true o false
	 * @author Esmeralda Garcia Torres
	 */
	public Boolean validarRegistrosTabla(String valor1, String valor2) {
		return aux.validateInTableGeneral("Avisos y Politicas", tablaBuscarAP, valor1, valor2);
	}

	/**
	 * Edita la informacion del aviso o politica indicado.
	 * 
	 * @param row
	 * @param nombre
	 * @param contenido
	 * @author Esmeralda Garcia Torres
	 */
	public void editarAvPo(String row, String nombre, String contenido) {
		try {
			By iconoEditar;
			iconoEditar = By.cssSelector("div.ui-datatable-tablewrapper > table > tbody > tr[data-ri=\"" + row
					+ "\"] > td:nth-child(6) > a");
			aux.clickOpcion("Editar", iconoEditar, true);
			if (!nombre.equalsIgnoreCase("")) {
				aux.enterOption("NombreAvPo", nombre, inputNombreAV, false);
			}
			if (!contenido.equalsIgnoreCase("")) {
				aux.enterOption("ContenidoAvPo", contenido, inputContenidoAV, false);
			}
			aux.clickOpcion("Publicar", buttonPublicarAV, true);
			u.hardWait(1);
			aux.clickOpcion("Aceptar", buttonConfirAceptar, true);
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Hubo un error al editar el registro: " + nombre);
		}
	}

	/**
	 * Selecciona el registro indicado y lo clona.
	 * 
	 * @param row
	 * @author Esmeralda Garcia Torres
	 */
	public void clonarAvPo(String row) {
		try {
			By iconoEditar;
			iconoEditar = By.cssSelector("div.ui-datatable-tablewrapper > table > tbody > tr[data-ri=\"" + row
					+ "\"] > td:nth-child(7) > a");
			aux.clickOpcion("Clonar", iconoEditar, true);
			u.hardWait(1);
			aux.clickOpcion("Aceptar", buttonConfirAceptar, true);
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Hubo un error al clonar el registro");
		}
	}

	/**
	 * Selecciona el registro indicado y lo elimina.
	 * 
	 * @param row
	 * @author Esmeralda Garcia Torres
	 */
	public void eliminarAvPo(String row) {
		try {
			By iconoEditar;
			iconoEditar = By.cssSelector("div.ui-datatable-tablewrapper > table > tbody > tr[data-ri=\"" + row
					+ "\"] > td:nth-child(6) > a");
			aux.clickOpcion("Editar", iconoEditar, true);
			aux.clickOpcion("Eliminar", buttonEliminarAV, true);
			u.hardWait(1);
			aux.clickOpcion("Aceptar", buttonConfirAceptar, true);
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Hubo un error al eliminar el registro");
		}
	}

	/**
	 * Selecciona el menu "Finanzas" y el submenu "Carga de Archivos".
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void goToCargaArchivo() {
		try {
			WebElement ele = u.getDriver().findElement(menuFinanzas);
			Actions action = new Actions(u.getDriver());
			action.moveToElement(ele).perform();
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Fallo el over");
		}
		aux.clickOpcion("Carga Archivo Relacional", opcCargaArchivoR, true);
		u.hardWait(1);
	}

}
