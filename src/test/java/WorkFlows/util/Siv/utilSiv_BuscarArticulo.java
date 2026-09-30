package WorkFlows.util.Siv;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class utilSiv_BuscarArticulo {
	private SeleniumUtil u;
	private TestCase testCase;
	private pomSIV pom;

	/// IDENTIFICACION DE ARTICULOS Y SELECCION DE MOVIMIENTOS ///
	By inputClaveFA = By.id("cveFA");
	By imgBuscarClaveFA = By.cssSelector("table.tableSubGrupo:nth-child(3) > tbody:nth-child(2) > tr:nth-child(1) "
			+ "> td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) "
			+ "> a:nth-child(2) > img:nth-child(1)");
	By imgBorrarClaveFA = By.cssSelector("table.tableSubGrupo:nth-child(3) > tbody:nth-child(2) > tr:nth-child(1) "
			+ "> td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) "
			+ "> a:nth-child(3) > img:nth-child(1)");
	By inputPlaza = By.id("cvePlaza");
	By imgBuscarPlaza = By.cssSelector("table.tableSubGrupo:nth-child(3) > tbody:nth-child(2) > tr:nth-child(2) "
			+ "> td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) "
			+ "> a:nth-child(2) > img:nth-child(1)");
	By imgBorrarPlaza = By.cssSelector("table.tableSubGrupo:nth-child(3) > tbody:nth-child(2) > tr:nth-child(2) "
			+ "> td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) "
			+ "> a:nth-child(3) > img:nth-child(1)");
	By inputSubCategoria = By.id("cveSubCategorias");
	By imgBuscarSubCategoria = By.cssSelector("table.tableSubGrupo:nth-child(3) > tbody:nth-child(2) "
			+ "> tr:nth-child(3) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) "
			+ "> td:nth-child(2) > a:nth-child(2) > img:nth-child(1)");
	By imgBorrarSubCategoria = By.cssSelector("table.tableSubGrupo:nth-child(3) > tbody:nth-child(2) "
			+ "> tr:nth-child(3) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) "
			+ "> td:nth-child(2) > a:nth-child(3) > img:nth-child(1)");
	By selectEstatus = By.id("cveEstatus");

	By buttonBusquedaPrede = By.id("busquedaPredeterminada");
	By buttonCapturarBasicos = By.id("capturarDatosBasicos");

	By inputCB = By.id("cbPlu");
	By inputDescripcion = By.id("descripcion");

	/// ARTICULOS ENCONTRADOS ///
	By buttonAgregarTodos = By.id("chkArtEncontrados");
	By tableArticulosEncontrados = By.cssSelector("table.tableSubGrupo:nth-child(1) > tbody:nth-child(1) "
			+ "> tr:nth-child(3) > td:nth-child(1) > div:nth-child(1) > table > tbody > tr");

	/// ARTICULOS SELECCIONADOS ///
	By buttonEliminarTodos = By.id("chkArtSeleccionados");
	By tableArticulosSeleccionados = By.cssSelector("table.tableSubGrupo:nth-child(1) > tbody:nth-child(1) "
			+ "> tr:nth-child(3) > td:nth-child(3) > div:nth-child(1) > table > tbody > tr");

	/// BOTONES FINALES ///
	By buttonAlta = By.id("btnDbAlta");
	By buttonModificar = By.cssSelector("input[value=\"Modificar\"]");

	/**
	 * Constructor. Recibe el SeleniumUtil utilizado en el test.
	 * 
	 * @param SeleniumUtil
	 * @author Esmeralda Garcia Torres
	 */
	public utilSiv_BuscarArticulo(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.pom = new pomSIV(u, testCase);
	}

	/**
	 * Busca y selecciona la claveFA.
	 * 
	 * @param claveFA
	 * @author Esmeralda Garcia Torres
	 */
	public void enterClaveFA(String claveFA) {
		pom.buscarOpcionModal_1("ClaveFA", claveFA, imgBuscarClaveFA);
	}

	/**
	 * Busca y selecciona la plaza.
	 * 
	 * @param plaza
	 * @author Esmeralda Garcia Torres
	 */
	public void enterPlaza(String plaza) {
		pom.buscarOpcionModal_1("Plaza", plaza, imgBuscarPlaza);
	}

	/**
	 * Busca y selecciona la clave de la subCategoria.
	 * 
	 * @param cveSubCategoria
	 * @author Esmeralda Garcia Torres
	 */
	public void enterSubCategoria(String cveSubCategoria) {
		pom.buscarOpcionModal_1("SubCategoria", cveSubCategoria, imgBuscarSubCategoria);
	}

	/**
	 * Selecciona el estatus.
	 * 
	 * @param estatus -> Activo, Inactivo, Ambos
	 * @author Esmeralda Garcia Torres
	 */
	public void selectEstatus(String estatus) {
		pom.selectOption("Estatus", estatus, selectEstatus);
	}

	/**
	 * Ingresa en el campo CB/PLU el texto esperado.
	 * 
	 * @param cb -> Codigo de Barras
	 * @author Esmeralda Garcia Torres
	 */
	public void enterCB(String cb) {
		pom.enterOption("CB", cb, inputCB);
	}

	/**
	 * Ingresa en el campo Descripcion el texto esperado.
	 * 
	 * @param descripcion
	 * @author Esmeralda Garcia Torres
	 */
	public void enterDescripcion(String descripcion) {
		pom.enterOption("Descripcion", descripcion, inputDescripcion);
	}

	/**
	 * Espera a que se muestre el boton Busqueda Predeterminada y le da click.
	 * Tambien valida que no se muestren los mensajes de "Faltan campos requeridos"
	 * o "Codigo de barra invalido".
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void busquedaPredeterminada() {
		pom.clickOpcion("Busqueda Predeterminada", buttonBusquedaPrede);
		String msg = pom.msgModal();
		if (msg.contains("son requeridos!")) {
			throw new AssertionError("Faltan campos requeridos");
		} else if (msg.contains("C�digo de barra inv�lido")) {
			throw new AssertionError("Codigo de barra invalido");
		}

	}

	/**
	 * Espera a que se muestre el boton Capturar Datos Basicos y le da click.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void capturarDatos() {
		pom.clickOpcion("Capturar Datos Basicos", buttonCapturarBasicos);
	}

	/**
	 * Valida que en la tabla "Articulos Encontrados" existan datos.
	 * 
	 * @return -> true o false
	 * @author Esmeralda Garcia Torres
	 */
	public boolean seEncontraronArticulos() {
		Boolean aux = false;
		try {
			List<WebElement> elementsTable = u.getDriver().findElements(tableArticulosEncontrados);
			pom.logM("Elementos en la Tabla: " + elementsTable.size());
			String element = elementsTable.get(0).getAttribute("innerText");
			if (!element.contains("no contiene datos")) {
				aux = true;
			}
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al buscar en la tabla de Articulos encontrados");
		}
		return aux;
	}

	/**
	 * Recibe como parametro los codigos de barras a buscar y seleccionar en la
	 * tabla. Estos deben estar separados por una coma (,).
	 * 
	 * @param CBs -> cb1,cb2
	 * @author Esmeralda Garcia Torres
	 */
	public void seleccionarEncontrados(String CBs) {
		try {
			String[] cb = CBs.split(",");
			pom.logM("Cantidad de cb: " + cb.length);
			List<WebElement> elementsTable = u.getDriver().findElements(tableArticulosEncontrados);
			pom.logM("Elementos en la Tabla: " + elementsTable.size());
			Actions act = new Actions(u.getDriver());
			for (int e = 0; e < cb.length; e++) {
				pom.logM("Se busca: " + cb[e]);
				for (int i = 0; i < elementsTable.size(); i++) {
					String element = elementsTable.get(i).getAttribute("innerText");
					pom.logM("En: " + element);
					if (element.contains(cb[e])) {
						pom.logM("Se encontro");
						By selectElement = By.cssSelector("tr.ng-scope:nth-child(" + (i + 1) + ") > td:nth-child(1)");
						u.highLight(selectElement);
						WebElement ele = u.getDriver().findElement(selectElement);
						act.doubleClick(ele).perform();
						u.hardWait(1);
						break;
					}
				}
			}

		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Falló al buscar y seleccionar en la tabla de Articulos encontrados");
		}
	}

	/**
	 * Espera a que se muestre el boton Agregar Todos y le da click.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void agregarTodos() {
		pom.clickOpcion("Agregar Todos", buttonAgregarTodos);
	}

	/**
	 * Espera a que se muestre el boton Eliminar Todos y le da click.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void eliminarTodos() {
		pom.clickOpcion("Eliminar Todos", buttonEliminarTodos);
	}

	/**
	 * Espera a que se muestre el boton Alta y le da click.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void Alta() {
		pom.clickOpcion("Alta", buttonAlta);
	}

	/**
	 * Espera a que se muestre el boton Modificar y le da click.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void Modificar() {
		pom.clickOpcion("Modificar", buttonModificar);
	}

}
