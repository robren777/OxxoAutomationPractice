package WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo;

import java.time.Duration;
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

public class ElementosObligatoriosDeCapturaPorEstructura {
	SeleniumUtil u;
	TestCase testCase;
	By botonBuscar = By.xpath("//*[@id=\"bBuscar\"]/span");
	By checkAplicarExcepcion = By.xpath("//*[@id='sbcAplicarExcepcion']/div[2]/span");
	By comboCategoria = By.xpath("//*[@id=\"sCategoria\"]/div[3]/span");
	By comboSubCategoria = By.xpath("//*[@id=\"sSubCategoria\"]/div[3]/span");
	By comboSuperGrupo = By.xpath("//*[@id=\"sSupergrupo\"]/div[3]/span");
	By comboDatoRequerido = By.xpath("//*[@id=\"sDatoRequerido\"]/div[3]/span");
	By configuracionSubCategoria = By.xpath("//*[@id=\"seccion\"]/tbody/tr[3]/td[2]/span");
	By configuracionCategoria = By.xpath("//*[@id=\"seccion\"]/tbody/tr[2]/td[2]/span");
	By configuracionSuperGrupo = By.xpath("//*[@id=\"seccion\"]/tbody/tr[1]/td[2]/span");
	By textoElementosObligatorios = By.xpath("//*[@id=\"titulo\"]/tbody/tr/td/h2");
	By ventanaConfiguracion = By.xpath("//*[@id=\"j_idt122_title\"]");
	By checkNuevaEstructura = By.xpath("//*[@id=\"j_idt123\"]/div[2]/span");
	By totalRegistrosPaginado = By.xpath(
			"//div[@id=\"registrosProductos_paginator_top\"]/select[@id=\"registrosProductos_rppDD\"]/option[@selected=\"selected\"]");
	// Seleccionar Combo
	private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);

	/**
	 * Constructor para la pagina Elementos Obligatorios De Captura Por Estructura
	 * del Portal SAT
	 * 
	 * @param u
	 * @param testCase
	 * @author nellyoliviab@hexaware.com
	 */
	public ElementosObligatoriosDeCapturaPorEstructura(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}

	/**
	 * Metodo para agregar captura a la evidencia generada
	 * 
	 * @param elemento----------------elemento web al que se enfocara pa captura de
	 *                                         pantalla
	 * @param descripcionAdicional----nombre   para la captura de la evidencia
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public void agregarEvidencia(By elemento, String descripcionAdicional) {
		u.highLight(elemento);
		testCase.addScreenShotCurrentStep("Elemento" + descripcionAdicional);
		u.unhighLight(elemento);
	}

	/**
	 * Metodo para esperar a que la imagen de carga desaparezca para continuar
	 * interactuando con los elementos web
	 * 
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public void esperarImagenCargaDeDatos() {
		boolean continuar = false;
		do {
			By elemento = By.xpath("//div[@id='j_idt12']");
			if (u.getDriver().findElement(elemento).getAttribute("aria-hidden").compareToIgnoreCase("true") == 0) {
				continuar = true;
			}
		} while (!continuar);
	}

	/**
	 * Metodo para capturar informacion en la caja de texto esperada
	 * 
	 * @param campoActual-------------elemento web que se espera localizar para
	 *                                         enviar la informacion
	 * @param valorDefault------------valor    que se espera capturar en la casilla
	 * @param captura-----------------true     si la accion se requiere agregar a la
	 *                                         evidencia y false cuando no
	 * 
	 * @return regresa el valor true cuando es exitoso y false cuando no lo es
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean capturarCasilla(String campoActual, String valorDefault, boolean captura) {
		try {
			By elemento = obtenerElemento(campoActual, "");
			u.getDriver().findElement(elemento).clear();
			esperarImagenCargaDeDatos();
			int contador = 0;
			do {
				u.getDriver().findElement(elemento).sendKeys(String.valueOf(valorDefault.charAt(contador)));
				esperarImagenCargaDeDatos();
				contador++;
			} while (valorDefault.length() > contador);

			if (captura) {
				agregarEvidencia(elemento, valorDefault);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Metodo para consultar el estatus en el apartado de Valor Default
	 * 
	 * @param nombreDelCampo-------------elemento web que se espera localizar para
	 *                                            enviar la informacion
	 * @param captura-----------------true        si la accion se requiere agregar a
	 *                                            la evidencia y false cuando no
	 * @param estatusEsperado------------valor    que se espera capturar en la
	 *                                            casilla
	 * 
	 * @returnregresa el valor true si el elemento esta activo y false si no
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean consultarEstatusValorDefault(String nombreDelCampo, boolean captura, boolean estatusEsperado) {
		By elemento = obtenerElemento(nombreDelCampo, "");
		if (captura) {
			agregarEvidencia(elemento, "Estatus" + elemento);
		}
		u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		String disabled = u.getDriver().findElement(elemento).getAttribute("aria-disabled");
		if (disabled.compareTo("false") == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Metodo para consultar el estatus de un elemento web especifico
	 * 
	 * @param nombreDelCampo-------------elemento web que se espera localizar para
	 *                                            enviar la informacion
	 * @param captura-----------------true        si la accion se requiere agregar a
	 *                                            la evidencia y false cuando no
	 * @param estatusEsperado------------valor    que se espera capturar en la
	 *                                            casilla
	 * 
	 * @return regresa el valor true si el elemento esta activo y false si no
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean consultarEstatusElemento(String nombreDelCampo, boolean captura, boolean estatusEsperado) {
		By elemento = obtenerElemento(nombreDelCampo, "");
		if (elemento != null) {
			if (captura) {
				agregarEvidencia(elemento, "Estatus" + elemento);
			}
			u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
			String disabled = u.getDriver().findElement(elemento).getAttribute("aria-disabled");
			if (disabled != null) {
				if (disabled.compareTo("false") == 0) {
					return false;
				} else {
					return true;
				}
			}
		}

		return !estatusEsperado;
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
		By elemento = obtenerElemento(campoActual, "");
		String nombreClase = u.getDriver().findElement(elemento).getAttribute("class");
		String separanombre[] = nombreClase.split("-");
		boolean estatus = false;
		if (separanombre[(separanombre.length - 1)].compareTo("disabled") == 0) {
			estatus = true;
		}
		agregarEvidencia(elemento, "");
		return estatus;
	}

	/**
	 * Metodo para esperar a que la imagen de carga desaparezca para continuar con
	 * otras acciones en la pantalla
	 * 
	 * 
	 * @author nellyoliviab@hexaware.com
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
	public By obtenerElemento(String nombreDelCampo, String adicional) {
		By elemento = null;
		switch (nombreDelCampo) {
		case "botonAtras":
			elemento = By.xpath("//*[@id=\"dtRegistros_paginator_bottom\"]/span[3]");
			break;
		case "botonAtras2":
			elemento = By.xpath("//*[@id=\"registrosProductos_paginator_bottom\"]/span[3]");
			break;
		case "botonBuscar":
			elemento = botonBuscar;
			break;
		case "botonGuardarCambios":
			elemento = By.xpath("//*[@id=\"cbGuardar\"]/span");
			break;
		case "botonPrimerPagina":
			elemento = By.xpath("//*[@id=\"dtRegistros_paginator_bottom\"]/span[6]");
			break;
		case "botonSiguiente":
			elemento = By.xpath("//*[@id=\"dtRegistros_paginator_bottom\"]/span[5]");
			break;
		case "botonSiguiente2":
			elemento = By.xpath("//*[@id=\"registrosProductos_paginator_bottom\"]/span[5]");
			break;
		case "botonUltimaPagina":
			elemento = By.xpath("//*[@id=\"dtRegistros_paginator_bottom\"]/span[2]");
			break;
		case "casillaValorDefault":
			elemento = By.xpath("//*[@id='itValorPorDefecto']");
			break;
		case "checkAplicarExcepcion":
			elemento = checkAplicarExcepcion;
			break;
		case "checkNuevaEstructura":
			elemento = checkNuevaEstructura;
			break;
		case "comboCategoria":
			elemento = comboCategoria;
			break;
		case "comboSubCategoria":
			elemento = comboSubCategoria;
			break;
		case "configuracionSubCategoria":
			elemento = configuracionSubCategoria;
			break;
		case "configuracionSuperGrupo":
			elemento = configuracionSuperGrupo;
			break;
		case "configuracionCategoria":
			elemento = configuracionCategoria;
			break;
		case "comboSuperGrupo":
			elemento = comboSuperGrupo;
			break;
		case "comboDatoRequerido":
			elemento = comboDatoRequerido;
			break;
		case "estatusBotonBuscar":
			elemento = By.xpath("//*[@id=\"bBuscar\"]");
			break;
		case "estatusComboCategoria":
			elemento = By.xpath("//*[@id=\"sCategoria_focus\"]");
			break;
		case "estatusComboDatoRequerido":
			elemento = By.xpath("//*[@id=\"sDatoRequerido_focus\"]");
			break;
		case "estatusComboSubCategoria":
			elemento = By.xpath("//*[@id=\"sSubCategoria_focus\"]");
			break;
		case "listacomboCategoria":
			elemento = By.xpath("//*[@id=\"sCategoria_" + adicional + "\"]");
			break;
		case "listacomboSubCategoria":
			elemento = By.xpath("//*[@id=\"sSubCategoria_" + adicional + "\"]");
			break;
		case "listacomboSuperGrupo":
			elemento = By.xpath("//*[@id=\"sSupergrupo_" + adicional + "\"]");
			break;
		case "listacomboDatoRequerido":
			elemento = By.xpath("//*[@id=\"sDatoRequerido_" + adicional + "\"]");
			break;
		case "totalRegistrosPaginado":
			elemento = totalRegistrosPaginado;
			break;
		case "registro":
			elemento = By.xpath("//div[@id=\"dtRegistros\"] //table/tbody/tr[" + adicional + "]/td[6]");
			break;
		case "textoElementosObligatorios":
			elemento = textoElementosObligatorios;
			break;
		case "ventanaConfiguracion":
			elemento = ventanaConfiguracion;
			break;

		}
		return elemento;
	}

	/**
	 * Metodo para obtener el id de la opción seleccionada en los combos
	 * 
	 * @param opcion --------------Texto enviado para obtener solo numeros
	 * 
	 * @return resultadoTexto---- Regresa solo numeros del texto enviado
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public String obtenerIdSeleccion(String opcion) {
		String resultadoTexto = "";
		opcion = opcion.replace(" ", "").toLowerCase();
		String textoSeparado[] = opcion.split("-");
		if (textoSeparado != null) {
			resultadoTexto = textoSeparado[0];
		}
		return resultadoTexto;
	}

	/**
	 * Metodo para obtener un numero aleatorio desde el 1 hasta el valor indicado
	 * 
	 * @param limite -------------Valor máximo del numero aleatorio que se generara
	 * 
	 * @return int---- Regresa el valor generado según el rango especificado
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public int obtenerNumeroAleatorio(int limite) {
		Random generaAleatorio = new Random();
		int limiteInt = 1 + generaAleatorio.nextInt(limite);
		return limiteInt;
	};

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
		// By elementoSeleccion=obtenerElemento("totalRegistrosPaginado","");
		String limiteString = obtenerTextoDeUnElemento("totalRegistrosPaginado", false);
		if (captura) {
			agregarEvidencia(By.xpath("//*[@id=\"registrosProductos_rppDD\"]"), "NumeroPaginadoActual");
		}
		return Integer.parseInt(limiteString);
	}

	/**
	 * Metodo para obtener el total de registros mostrados en el grid
	 * 
	 * 
	 * @return int---- Regresa el valor del total de registros seleccionado
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public int obtenerTotalRegistros() {
		List<WebElement> listaRegistros = u.getDriver()
				.findElements(By.xpath("//div[@id=\"dtRegistros\"] //table/tbody/tr"));
		return listaRegistros.size();
	}

	/**
	 * Metodo para obtener un registro aleatorio si el total de registros es mayor a
	 * 1
	 * 
	 * 
	 * @return int---- Regresa el número de registro aleatorio
	 * 
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
		if (elementoSeleccion != null) {
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(elementoSeleccion));
				if (captura) {
					agregarEvidencia(elementoSeleccion, "Seleccion" + campoActual);
				}
				return u.getDriver().findElement(elementoSeleccion).getText();
			} catch (Exception e) {
				logger.error("Error" + e);
				throw new AssertionError("Error al obtener el texto del elemento '" + campoActual + "'");
			}
		}
		return null;
	}

	/**
	 * Metodo para obtener solo letras de un texto, lo convierte en minuúsculas y
	 * reemplaza los espacios
	 * 
	 * @param opcion --------------Texto enviado para obtener solo letras
	 * 
	 * @return resultadoTexto---- Regresa solo letras del texto enviado
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public String obtenerTextoSinIdDeOpciones(String opcion) {
		String resultadoTexto = "";
		opcion = opcion.replace(" ", "").toLowerCase();
		String textoSeparado[] = opcion.split("-");
		if (textoSeparado != null) {
			for (int i = 1; i < textoSeparado.length; i++) {
				resultadoTexto += textoSeparado[i];
			}
		}
		return resultadoTexto;
	}

	/**
	 * Metodo para seleccionar el checkbox de la pantalla de Edición Aplicar
	 * Excepcion
	 * 
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public void seleccionarAplicarExcepcion() {
		By elemento = obtenerElemento("checkAplicarExcepcion", "");
		String estatus = u.getDriver().findElement(By.xpath("//*[@id='sbcAplicarExcepcion']/div[2]")).getAttribute("class");
		System.out.println("estatus: " + estatus);
		if (estatus.contains("active") == false) {
			u.getDriver().findElement(By.xpath("//*[@id='sbcAplicarExcepcion']/div[2]/span")).click();
		}
		agregarEvidencia(elemento, "");
	}

	/**
	 * Metodo para seleccionar el campo a aplicar un valor default
	 * 
	 * 
	 * @return boolean---- Regresa true si la acción fue exitosa y false si no
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean seleccionarCheckValorDefault(String datoRequerido, boolean captura) {
		System.out.println("DatoRequerido " + datoRequerido);
		List<WebElement> listaChecks = u.getDriver()
				.findElements(By.xpath("//table[@id='j_idt151']/tbody/tr/td/div/div/input"));
		int contador = 1;
		System.out.println("contador " + contador);
		for (WebElement elemento : listaChecks) {
			String textoElemento = elemento.getAttribute("value");
			System.out.println("textoElemento " + textoElemento);
			if (textoElemento.compareTo(datoRequerido) == 0) {
				By elementoActivo = By.xpath("//table[@id='j_idt151']/tbody/tr[" + contador + "]/td/div/div[2]/span");
				if (captura) {
					agregarEvidencia(elementoActivo, "");
				}
				u.getDriver().findElement(elementoActivo).click();
				return true;
			}
			contador++;
		}

		return false;
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

	/**
	 * Metodo para seleccionar de guardar cambios en la sección de edición del valor
	 * default
	 * 
	 * 
	 * @return String con el mensaje de actualización exitoso esperado o con el
	 *         mensaje de error esperado en caso de que la actualización no sea
	 *         exitosa
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public String seleccionarElementoGuardarCambios() {
		String alert = "";
		try {
			By elementoSeleccion = obtenerElemento("botonGuardarCambios", "");
			WebDriverWait wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(30));
			agregarEvidencia(elementoSeleccion, "");
			u.getDriver().findElement(elementoSeleccion).click();
			esperarCargaDeDatos();
			By alerta = By.xpath("//*[@id=\"messages_container\"]/div/div/div[2]/p");
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"messages_container\"]/div/div/div[2]/p")));
			alert = u.getDriver().findElement(alerta).getText();
		} catch (Exception e) {
			throw new AssertionError("Error al seleccionar el boton Guardar Cambios");
		}
		return alert;
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
	public String seleccionarOpcionCombo(String campoActual, boolean captura, String opcionCombo) {
		By elementoSeleccion = obtenerElemento(campoActual, "");
		opcionCombo = opcionCombo.toLowerCase().replace(" ", "");
		String textoEncontrado = "ERROR";
		String texto = "";
		
		if (elementoSeleccion != null) {
			try {
				u.getDriver().findElement(elementoSeleccion).click();
				int contador = 1;
				By opcionDesplegada = obtenerElemento("lista" + campoActual, String.valueOf(contador));
				do {
					texto = u.getDriver().findElement(opcionDesplegada).getText();
					String textoLetras = obtenerTextoSinIdDeOpciones(texto);
					if (textoLetras.compareToIgnoreCase(opcionCombo) == 0) {
						u.getDriver().findElement(opcionDesplegada).click();
						if (captura) {
							agregarEvidencia(elementoSeleccion, "Seleccion" + opcionCombo);
						}
						textoEncontrado = texto;
						return textoEncontrado;
					} else {
						contador++;
						opcionDesplegada = obtenerElemento("lista" + campoActual, String.valueOf(contador));
					}
				} while (opcionDesplegada != null);

			} catch (Exception e) {
				logger.error("Error" + e);
				throw new AssertionError(
						"Fallo al seleccionar la opción '" + opcionCombo + "' de '" + campoActual + "'");
			}
		}
		return textoEncontrado;
	}

	/**
	 * Metodo para seleccionar ina opcion del combo que contiene los campos de valor
	 * default a modificar
	 * 
	 * @param campoActual                  ------------elemento a buscar
	 * @param captura-----------------true para obtener la captura en la evidencia
	 * @param opcionCombo------------La    opcion desplegada del combo que se
	 *                                     requiere seleccionar
	 * 
	 * @return String con el valor seleccionado o el mensaje de eeror esperado en
	 *         caso de no ser exitosa la busqueda de la opcion
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public String seleccionarOpcionComboValorD(String campoActual, boolean captura, String opcionCombo) {
		By elementoSeleccion = obtenerElemento(campoActual, "");
		String textoEncontrado = "ERROR";
		String texto = "";
		if (elementoSeleccion != null) {
			try {
				u.getDriver().findElement(elementoSeleccion).click();
				int contador = 1;
				By opcionDesplegada = obtenerElemento("lista" + campoActual, String.valueOf(contador));
				do {
					texto = u.getDriver().findElement(opcionDesplegada).getText();
					// String textoLetras=obtenerTextoSinIdDeOpciones(texto);
					if (texto.compareToIgnoreCase(opcionCombo) == 0) {
						u.getDriver().findElement(opcionDesplegada).click();
						if (captura) {
							agregarEvidencia(elementoSeleccion, "Seleccion" + opcionCombo);
						}
						textoEncontrado = texto;
						return textoEncontrado;
					} else {
						contador++;
						opcionDesplegada = obtenerElemento("lista" + campoActual, String.valueOf(contador));
					}
				} while (opcionDesplegada != null);

			} catch (Exception e) {
				logger.error("Error" + e);
				throw new AssertionError(
						"Fallo al seleccionar la opción '" + opcionCombo + "' de '" + campoActual + "'");
			}
		}
		return textoEncontrado;
	}

	public boolean validarEstatusCasillasValorDefault() {
		By elementoCasilla = obtenerElemento("casillaValorDefault", "");
		String claseCheck = u.getDriver().findElement(By.xpath("//*[@id='sbcAplicarExcepcion']/div[2]")).getAttribute("class");
		String estatusCasilla = u.getDriver().findElement(elementoCasilla).getAttribute("aria-disabled");
		System.out.println("claseCheck " + claseCheck);
		System.out.println("estatusCasilla " + estatusCasilla);
		if (claseCheck.contains("active") && estatusCasilla.compareTo("false") == 0) {
			return true;
		} else {
			if (claseCheck.contains("default") && estatusCasilla.compareTo("true") == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Metodo para validar la informacion del registro esperado
	 * 
	 * @param captura-----------------true  para obtener la captura en la evidencia
	 * @param numeroRegistro---------numero de registro del grid mostrado en
	 *                                      pantalla
	 * @param numeroColumna---------numero  de columna que se reguiere obtener la
	 *                                      información
	 * 
	 * @return String con el valor del registro y columna indicados
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public String validarInformacionRegistro(boolean captura, int numeroRegistro, int numeroColumna) {
		try {
			By elementoRegistro = By.xpath("//div[@id=\"dtRegistros\"] //table/tbody/tr[" + numeroRegistro
					+ "]/td[" + numeroColumna + "]");
			if (captura) {
				By elementoRegistroCaptura = By
						.xpath("//div[@id='dtRegistros'] //table/tbody/tr[" + numeroRegistro + "]");
				agregarEvidencia(elementoRegistroCaptura, "RegistroSeleccionado");
			}
			String texto = u.getDriver().findElement(elementoRegistro).getText();
			return texto;
		} catch (Exception e) {
			throw new AssertionError(
					"Error al validar la información del registro " + numeroRegistro + "," + numeroColumna + ". Excepción original: "+e.getMessage(), e);
		}
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
		if (numeroPaginado == numeroResultados || numeroPaginado > numeroResultados) {
			resultado = true;
		}
		testCase.addTextEvidenceCurrentStep("La cantidad de registros mostrados actualmente es: " + numeroResultados
				+ "\n" + "Esta cantidad coincide con el maximo numero de registros esperados: " + numeroPaginado);
		return resultado;
	}

	/**
	 * Metodo para validar que se encuentre seleccionado el elemento en pantalla
	 * segun la informacion obtenida del grid
	 * 
	 * @param datoRequerido-----------------dato que se espera se encuentre
	 *                                           seleccionado
	 * 
	 * @return True si la accion es exitosa y false si no
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean validarLaSeleccionInicialDeConfiguracion(String datoRequerido) {
		boolean validacion = false;
		List<WebElement> listaChecks = u.getDriver()
				.findElements(By.xpath("//table[@id='j_idt151']/tbody/tr/td/div/div/input"));
		int contador = 1;
		for (WebElement elemento : listaChecks) {
			if (elemento.getAttribute("value").compareTo(datoRequerido) == 0) {
				By elementoActivo = By.xpath("//table[@id='j_idt151']/tbody/tr[" + contador + "]/td/div/div[2]");
				String nombreDeClaseElelemto = u.getDriver().findElement(elementoActivo).getAttribute("class");
				if (nombreDeClaseElelemto.contains("active")) {
					validacion = true;
					break;
				}
			}
			contador++;
		}
		if (datoRequerido.compareTo("") == 0 && validacion == false) {
			validacion = true;
		}
		System.out.println(validacion);
		return validacion;
	}
}