package WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class CatalogoGeneralDeImpuestos {
	SeleniumUtil u;
	TestCase testCase;
	// Texto
	By textoCatalogoDeEstructura = By.xpath("//*[@id='titulo']/tbody/tr/td/h2");
	By totalRegistrosPaginado = By.xpath(
			"//div[@id='registrosEstructuraImps_paginator_top']/select[@id='registrosEstructuraImps_rppDD']/option[@selected='selected']");
	// TextBox
	By textboxCodigoPostal = By.xpath("//input[@id='imCodigoPostal']");
	By labelCodigoPostal = By.xpath("//*[@id=\"j_idt112\"]");

	By divGeneral = By.xpath("//div[@id=\"contenido\"]");

	// ComboBox
	By comboEstado = By.xpath("//*[@id='sEstado']/div[3]/span");
	By comboTienda = By.xpath("//*[@id='sTienda']/div[3]/span");
	By comboSuperGrupo = By.xpath("//*[@id='sSupergrupo']/div[3]/span");
	By comboCategoria = By.xpath("//*[@id='sCategoria']/div[3]/span");

	By selectEstado = By.xpath("//select[@id='sEstado_input']");

	// Botones
	By botonBuscar = By.xpath("//*[@id='bBuscar']/span");

	// Formulario de edicion
	By comboCodigoImp = By.xpath("//*[@id='sVatCodeNuevo']");
	By iva16 = By.xpath("//li[@id='sVatCodeNuevo_1']");
	By valorIva = By.xpath("//*[@id='opConfiguracionImpuestos']//span[@id='itIvaSeleccionado']");
	By valorIeps = By.xpath("//*[@id='opConfiguracionImpuestos']//span[@id='itIepsSeleccionado']");
	By valorIvaIeps = By.xpath("//*[@id='opConfiguracionImpuestos']//span[@id='itIvaIepsSeleccionado']");
	By botonGuardarCambios = By.xpath("//*[@id='cbGuardar']");
	By botonCancelar = By.xpath("//*[@id='j_idt253']");

	private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);

	/**
	 * Constructor para la pagina Catalogo General De Impuestos del Portal SAT
	 * 
	 * @param u
	 * @param testCase
	 * @author AngelD1@hexaware.com
	 */
	public CatalogoGeneralDeImpuestos(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}

	/**
	 * Metodo para obtener el texto del elemento
	 * 
	 * @param campoActual --------------elemento a buscar
	 * @param captura     ------------true para obtener la captura en la evidencia
	 * 
	 * @return regresa true si se encontro el elemento
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public String obtenerTextoDeUnElemento(String campoActual, boolean captura) {
		By elementoSeleccion = obtenerElemento(campoActual, "");
		WebDriverWait wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(elementoSeleccion));
			if (captura) {
				agregarEvidencia(elementoSeleccion, campoActual);
			}
			if (campoActual.equals("textboxCodigoPostal")) {
				return u.getDriver().findElement(elementoSeleccion).getAttribute("value");
			}
			return u.getDriver().findElement(elementoSeleccion).getText();
		} catch (Exception e) {
			logger.error("Error" + e);
			throw new AssertionError("Error al obtener el texto del elemento '" + campoActual + "'");
		}
	}

	/**
	 * Metodo para seleccionar el elemento web
	 * 
	 * @param campoActual --------------elemento a buscar
	 * @param captura     ------------------true para obtener la captura en la
	 *                    evidencia
	 * @param opcionCombo --------------opcion de Combo a seleccionar
	 * 
	 * @return regresa true si se encontro el elemento
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean seleccionarOpcionCombo(String campoActual, String opcionCombo, boolean captura) {
		By elementoSeleccion = obtenerElemento(campoActual, "");

		try {
			u.getDriver().findElement(elementoSeleccion).click();
			int contador = 1;
			By opcionDesplegada = obtenerElemento("lista" + campoActual, String.valueOf(contador));
			do {
				String textoLista = u.getDriver().findElement(opcionDesplegada).getText();
				if (textoLista.compareTo(opcionCombo) == 0) {
					u.getDriver().findElement(opcionDesplegada).click();
					esperarCargaDeDatos();
					if (captura) {
						agregarEvidencia(elementoSeleccion, campoActual);
					}
					return true;
				} else {
					contador++;
					opcionDesplegada = obtenerElemento("lista" + campoActual, String.valueOf(contador));
				}
			} while (opcionDesplegada != null);

		} catch (Exception e) {
			logger.error("Error" + e);
			throw new AssertionError("Fallo al seleccionar la opción '" + opcionCombo + "' de '" + campoActual + "'");
		}

		throw new AssertionError("Fallo al seleccionar la opción '" + opcionCombo + "' de '" + campoActual + "'");
	}

	/**
	 * Metodo para asignar el elemento By con el que se realizara interaccion en el
	 * paso actual
	 * 
	 * @param nombreDelCampo --------------es el campo de la pagina actual que se
	 *                       asignara al By
	 * @param adicional      ------------------- información adicional par aobtener
	 *                       el elemento web esperado
	 * @return regresa el elemento By con el que se realizaran las futuras
	 *         interacciones (captura, seleccion, obtener texto, etc)
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	private By obtenerElemento(String nombreDelCampo, String adicional) {
		By elemento = null;
		switch (nombreDelCampo) {
		case "textoCatalogoDeEstructura":
			elemento = textoCatalogoDeEstructura;
			break;
		case "estatuscomboEstado":
			elemento = By.xpath("//*[@id=\"sEstado_focus\"]");
			break;
		case "estatuscomboTienda":
			elemento = By.xpath("//*[@id=\"sTienda_focus\"]");
			break;
		case "estatuscomboSuperGrupo":
			elemento = By.xpath("//*[@id=\"sSupergrupo_focus\"]");
			break;
		case "estatuscomboCategoria":
			elemento = By.xpath("//*[@id=\"sCategoria_focus\"]");
			break;
		case "textboxCodigoPostal":
			elemento = textboxCodigoPostal;
			break;
		case "comboEstado":
			elemento = comboEstado;
			break;
		case "listacomboEstado":
			elemento = By.xpath("//li[@id=\"sEstado_" + adicional + "\"]");
			break;
		case "comboTienda":
			elemento = comboTienda;
			break;
		case "listacomboTienda":
			elemento = By.xpath("//li[@id=\"sTienda_" + adicional + "\"]");
			break;
		case "comboSuperGrupo":
			elemento = comboSuperGrupo;
			break;
		case "listacomboSuperGrupo":
			elemento = By.xpath("//li[@id=\"sSupergrupo_" + adicional + "\"]");
			break;
		case "comboCategoria":
			elemento = comboCategoria;
			break;
		case "listacomboCategoria":
			elemento = By.xpath("//li[@id=\"sCategoria_" + adicional + "\"]");
			break;

		case "botonBuscar":
			elemento = botonBuscar;
			break;
		case "totalRegistrosPaginado":
			elemento = totalRegistrosPaginado;
			break;
		case "botonAtras":
			elemento = By.xpath("//*[@id='registrosEstructuraImps_paginator_bottom']/span[3]");
			break;
		case "botonSiguiente":
			elemento = By.xpath("//*[@id='registrosEstructuraImps_paginator_bottom']/span[5]");
			break;
		case "botonUltimaPagina":
			elemento = By.xpath("//*[@id='registrosEstructuraImps_paginator_bottom']/span[6]/span");
			break;
		case "botonPrimerPagina":
			elemento = By.xpath("//*[@id='registrosEstructuraImps_paginator_bottom']/span[2]");
			break;
		case "textoPaginas":
			elemento = By.xpath("//*[@id='registrosEstructuraImps_paginator_bottom']/span[1]");
			break;
		case "registro":
			elemento = By
					.xpath("//a[@id=\"registrosEstructuraImps:" + adicional + ":j_idt157\"]/span");
			break;
		case "etiquetaEstado":
			elemento = By.xpath("//*[@id='j_idt162']/div/div/div[1]/div[2]/span");
			break;
		case "etiquetaSuperGrupo":
			elemento = By.xpath("//*[@id='j_idt162']/div/div/div[2]/div[2]/span");
			break;
		case "etiquetaSubCategoria":
			elemento = By.xpath("//*[@id='j_idt162']/div/div/div[3]/div[2]/span");
			break;
		case "etiquetaIVAnueva":
			elemento = By.xpath("//*[@id='itIvaSeleccionado']");
			break;
		case "etiquetaRegion":
			elemento = By.xpath("//*[@id='j_idt162']/div/div/div[4]/div[2]/span");
			break;
		case "etiquetaVATCODE":
			elemento = By.xpath("//*[@id='j_idt162']/div/div/div[5]/div[2]/span");
			break;
		case "etiquetaIEPS":
			elemento = By.xpath("//*[@id='j_idt162']/div/div/div[6]/div[2]/span");
			break;
		case "etiquetaImpuestoEstatal":
			elemento = By.xpath("//*[@id='j_idt162']/div/div/div[7]/div[2]/span");
			break;
		case "etiquetaInicioVigencia":
			elemento = By.xpath("//*[@id='j_idt162']/div/div/div[8]/div[2]/span");
			break;
		case "etiquetaTienda":
			elemento = By.xpath("//*[@id='j_idt162']/div/div/div[1]/div[4]/span");
			break;
		case "etiquetaCategoria":
			elemento = By.xpath("//*[@id='j_idt162']/div/div/div[2]/div[4]/span");
			break;
		case "etiquetaSegmento":
			elemento = By.xpath("//*[@id='j_idt162']/div/div/div[3]/div[4]/span");
			break;
		case "etiquetaCompraV":
			elemento = By.xpath("//*[@id='j_idt162']/div/div/div[4]/div[4]/span");
			break;
		case "etiquetaIVA":
			elemento = By.xpath("//*[@id='j_idt162']/div/div/div[5]/div[4]/span");
			break;
		case "etiquetaIVAIEPS":
			elemento = By.xpath("//*[@id='j_idt162']/div/div/div[6]/div[4]/span");
			break;
		case "etiquetaIVAImpuestoEstatal":
			elemento = By.xpath("//*[@id='j_idt162']/div/div/div[7]/div[4]/span");
			break;
		case "comboImpuesto":
			elemento = By.xpath("//*[@id=\"sVatCodeNuevo\"]/div[3]/span");
			break;
		case "listacomboImpuesto":
			elemento = By.xpath("//*[@id=\"sVatCodeNuevo_1\"]");
			break;
		case "botonGuardarCambios":
			elemento = By.xpath("//*[@id='cbGuardar']/span");
			break;
		case "botonCancelarCambios":
			elemento = By.xpath("//*[@id='j_idt251']/span");
			break;
		default:
			throw new AssertionError("No se encontró la opción '" + nombreDelCampo + "'");
		}

		return elemento;
	}

	/**
	 * Hace click en el elemento indicado
	 * 
	 * @param campoActual
	 * @param captura
	 * @return Regresa true si se encontró el elemento
	 * @author AngelD1@hexaware.com
	 */
	public boolean clickElemento(String campoActual, boolean captura) {
		try {
			By elemento = obtenerElemento(campoActual, "");
			if (captura) {
				agregarEvidencia(elemento, campoActual);
			}
			u.getDriver().findElement(elemento).click();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			throw new AssertionError("Error al hacer click en el elemento " + campoActual);
		}
	}

	/**
	 * Metodo para validar la informacion del registro segun su numero y columna
	 * 
	 * @param captura------------------true   para obtener la captura en la
	 *                                        evidencia
	 * @param numeroRegistro-----------numero de registro mostrado en el grid actual
	 * @param numeroColumna------------numero de columna de la cual se va a obtener
	 *                                        su valor
	 * 
	 * @return regresa el valor correcpondiente al registro y columna del elemento
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public String validarInformacionRegistro(int numeroRegistro, int numeroColumna, boolean captura) {
		try {
			By noExistenDatos = By.xpath("//tr[@class=\"ui-widget-content ui-datatable-empty-message\"]/td");
			if (!u.getDriver().findElements(noExistenDatos).isEmpty()){
				return u.getDriver().findElement(noExistenDatos).getText();
			}
			By elementoRegistro = By.xpath("//*[@id=\"registrosEstructuraImps_data\"]/tr[@data-ri=\"" + numeroRegistro + "\"]/td[" + numeroColumna + "]");
			if (captura) {
				By elementoRegistroCaptura = By.xpath("//*[@id=\"registrosEstructuraImps_data\"]/tr[@data-ri=\"" + numeroRegistro + "\"]");
				agregarEvidencia(elementoRegistroCaptura, "RegistroSeleccionado");
			}
			String texto = u.getDriver().findElement(elementoRegistro).getText();
			return texto;
		} catch (Exception e) {
			throw new AssertionError(
					"Error al validar la información del registro " + numeroRegistro + "," + numeroColumna);
		}
	}

	/**
	 * Se obtiene un registro aleatorio del grid considerando el numero maximo de
	 * registros desplegado por pagina
	 * 
	 * @return int --------------- El numero de registro aleatorio
	 * @author nellyoliviab@hexaware.com
	 */
	public int obtenerRegistroAleatorio() {
		int numeroRegistro = 0;
		int totalRegistros = obtenerTotalRegistros();
		if (obtenerTotalRegistros() > 1) {
			numeroRegistro = obtenerNumeroAleatorio(totalRegistros);
		} else {
			numeroRegistro = totalRegistros;
		}
		return numeroRegistro;
	}

	/**
	 * Se obtiene el total de registros mostrados en el grid
	 * 
	 * @return int --------------- El valor total de registros mostrado en el grid
	 * @author nellyoliviab@hexaware.com
	 */
	private int obtenerTotalRegistros() {
		List<WebElement> listaRegistros = u.getDriver().findElements(By.xpath("//*[@id=\"registrosEstructuraImps_data\"]/tr"));
		return listaRegistros.size();
	}

	/**
	 * Se obtieme un numero aleatorio desde 1 al limite que se reciba
	 * 
	 * @param limite ------------- Limite para generar el numero aleatorio desde 1
	 *               hasta este valor
	 * 
	 * @return int --------------- Se regresa un valor aleatorio dentro del rango
	 *         establecido
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public int obtenerNumeroAleatorio(int limite) {
		Random generaAleatorio = new Random();
		int limiteInt = 1 + generaAleatorio.nextInt(limite);
		return limiteInt;
	};

	/**
	 * Llena un textbox con el texto indicado y hace click en un espacio en blanco
	 * para hacer la carga
	 * 
	 * @param campo
	 * @param texto
	 * @param captura
	 * @return Regresa true si se llenó el campo
	 * @author AngelD1@hexaware.com
	 */
	public boolean llenarTextbox(String campo, String texto, boolean captura) {
		try {
			By elemento = obtenerElemento(campo, "");
			u.scrollToElement(elemento);
			u.getDriver().findElement(elemento).clear();
			esperarCargaDeDatos();
			new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(elemento)).click();
			u.getDriver().findElement(elemento).sendKeys(texto);
			if (captura) {
				agregarEvidencia(elemento, campo);
			}
			u.getDriver().findElement(divGeneral).click();
			esperarCargaDeDatos();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			throw new AssertionError("Fallo al llenar el textbox " + campo + "\n" + e);
		}
	}

	/**
	 * Indica si un elemento está activo
	 * 
	 * @param campo
	 * @param captura
	 * @return Regresa el estatus del campo
	 * @author AngelD1@hexaware.com
	 */
	public boolean isEnabled(String campo, boolean captura) {
		By elemento = obtenerElemento("estatus" + campo, "");
		if (captura) {
			agregarEvidencia(elemento, campo);
		}
		boolean enabled = u.getDriver().findElement(elemento).isEnabled();
		return enabled;
	}

	/**
	 * Hace una espera a que deje de mostrarse la barra de carga
	 * 
	 * @author AngelD1@hexaware.com
	 */
	public void esperarCargaDeDatos() {
		boolean continuar = false;
		do {
			By elemento = By.xpath("//*[@id=\"j_idt12\"]");
			if (u.getDriver().findElement(elemento).getAttribute("aria-hidden").compareToIgnoreCase("true") == 0) {
				continuar = true;
			}
		} while (!continuar);
	}

	/**
	 * Metodo para agregar captura de pantalla a la evidencia de la prueba
	 * 
	 * @param elemento                      --------------es el elemento en pantalla
	 *                                      al cual se enfocara la captura de
	 *                                      pantalla
	 * @param descripcionAdicional---nombre para la imagen generada
	 * 
	 * @author angeld1@hexaware.com
	 */
	private void agregarEvidencia(By elemento, String descripcionAdicional) {
		u.highLight(elemento);
		testCase.addScreenShotCurrentStep("Elemento " + descripcionAdicional);
		u.unhighLight(elemento);
	}

	/**
	 * Divide el texto indicado por un - en un array
	 * 
	 * @param texto
	 * @return Regresa el array del texto separado
	 * @throws IndexOutOfBoundsException
	 * @author AngelD1@hexaware.com
	 */
	public String[] obtenerSubtexto(String texto) throws IndexOutOfBoundsException {
		String[] textoArr = texto.split(" - ", 2);
		System.out.println(Arrays.toString(textoArr));
		return textoArr;
	}

	/**
	 * Metodo para obtener el total del paginado de resultados
	 * 
	 * @param captura-----------------true para obtener la captura en la evidencia
	 * 
	 * @return True si la accion es exitosa
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean validarElTotalDeResultadosPaginado(boolean captura) {
		int numeroResultados = obtenerTotalRegistros();
		int numeroPaginado = obtenerNumeroDeRegistrosDelPaginado(captura);
		boolean resultado = false;
		if (numeroPaginado >= numeroResultados) {
			resultado = true;
			testCase.addTextEvidenceCurrentStep("La cantidad de registros mostrados actualmente es: " + numeroResultados
					+ "\n" + "Esta cantidad coincide con el maximo numero de registros esperados: " + numeroPaginado);
		} else {
			throw new AssertionError("La cantidad de registros mostrados: " + numeroResultados + " es mayor al permitido: " + numeroPaginado);
		}
		return resultado;
	}

	/**
	 * Metodo para obtener el numero máximo de registros mostrados por pagina en el
	 * grid
	 * 
	 * @param limite -------------Valor máximo del numero aleatorio que se generara
	 * 
	 * @return int---- Regresa el valor generado según el rango especificado
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public int obtenerNumeroDeRegistrosDelPaginado(boolean captura) {
		String limiteString = obtenerTextoDeUnElemento("totalRegistrosPaginado", false);
		if (captura) {
			agregarEvidencia(By.xpath("//*[@id='registrosEstructuraImps_rppDD']"), "NumeroPaginadoActual");
		}
		return Integer.parseInt(limiteString);
	}

	/**
	 * Metodo para consultar el estatus de los elementos que controlan el paginado
	 * del grid de la pantalla
	 * 
	 * @param campoActual-------------elemento del grid que se espera localizar para
	 *                                         enviar la informacion
	 * @param captura-----------------true     si la accion se requiere agregar a la
	 *                                         evidencia y false cuando no
	 * 
	 * @return regresa el valor true si el elemento esta activo y false si no
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean consultarEstatusPaginado(String campoActual, boolean captura) {
		u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		esperarCargaDeDatos();
		By elemento = obtenerElemento(campoActual, "");
		String nombreClase = u.getDriver().findElement(elemento).getAttribute("class");
		String nombreClaseArr[] = nombreClase.split("-");
		boolean estatus;
		if (nombreClaseArr[(nombreClaseArr.length - 1)].compareTo("disabled") == 0) {
			estatus = false;
		} else {
			estatus = true;
		}
		agregarEvidencia(elemento, "Boton de paginador");
		return estatus;
	}

	/**
	 * Metodo para obtener el texto del elemento
	 * 
	 * @param campoActual                  ------------elemento a buscar
	 * @param captura-----------------true para obtener la captura en la evidencia
	 * @param adicional--------------      informacion adicional del elemento web a
	 *                                     buscar
	 * 
	 * @return regresa true si se encontro el elemento y falso si no
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean seleccionarElemento(String campoActual, boolean captura, String adicional) {
		u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		By elemento = obtenerElemento(campoActual, adicional);
		if (elemento != null) {
			if (captura) {
				agregarEvidencia(elemento, campoActual);
			}
			u.getDriver().findElement(elemento).click();
			return true;
		}
		return false;
	}

}