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

public class ConsultaDeImpuestosAsignados {
	SeleniumUtil u;
	TestCase testCase;
	// obtener texto
	By textoConsultaDeImpuestosAsignados = By.xpath("//*[@id=\"titulo\"]/tbody/tr/td/h2");
	By totalRegistrosPaginado = By.xpath(
			"//div[@id=\"registrosProductos_paginator_top\"]/select[@id=\"registrosProductos_rppDD\"]/option[@selected=\"selected\"]");
	// Seleccionar Combo
	By comboSuperGrupo = By.xpath("//*[@id=\"sgrp\"]/div[3]/span");
	By comboCategoria = By.xpath("//*[@id=\"cat\"]/div[3]/span");
	By comboSubCategoria = By.xpath("//*[@id=\"subcat\"]/div[3]/span");
	By comboSegmento = By.xpath("//*[@id=\"seg\"]/div[3]/span");
	By comboSubSegmento = By.xpath("//*[@id=\"subseg\"]/div[3]/span");
	// Botones
	By seleccionaFechaAtras = By.xpath("//*[@id=\"ui-datepicker-div\"]/div/a[1]/span");

	By botonBuscar = By.xpath("//*[@id=\"buscar\"]/span");
	By botonBuscarEstatus = By.xpath("//table[@id='filtro']/tbody/tr[8]/td/button");
	// CASILLA
	By casillaClaveArticulo = By.xpath("//input[@id='cveArt']");
	By casillaFechaInicio = By.xpath("//*[@id=\"fechaInicio_input\"]");
	By casillaDescripcion = By.xpath("//input[@id='desc']");
	By casillaClaveSAT = By.xpath("//input[@id='cveSat']");

	By imagenCarga = By.xpath("//*[@id=\"j_idt13\"]");
	private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);

	/**
	 * Constructor para la pagina Consulta De Impuestos Asignados del Portal SAT
	 * 
	 * @param u
	 * @param testCase
	 * @author nellyoliviab@hexaware.com
	 */
	public ConsultaDeImpuestosAsignados(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
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
	public void agregarEvidencia(By elemento, String descripcionAdicional) {
		u.highLight(elemento);
		u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		testCase.addScreenShotCurrentStep("Elemento" + descripcionAdicional);
		u.unhighLight(elemento);
	}

	/**
	 * Metodo para cambiar el formato de mes de numero a abreviacion de letras
	 * 
	 * @param fecha--------------------- mes en formato de numero
	 * 
	 * @return regresa ----------------- el equivalente de mes en numero a formato
	 *         abreviado de letras
	 * 
	 * @author angeld1@hexaware.com
	 */
	public String cambiarFormatoFecha(String fecha) {
		String anio = fecha.substring(2, 4);
		String mes = fecha.substring(5, 7);
		String dia = fecha.substring(8, 10);
		switch (mes) {
		case "01":
			mes = "JAN";
			break;
		case "02":
			mes = "FEB";
			break;
		case "03":
			mes = "MAR";
			break;
		case "04":
			mes = "APR";
			break;
		case "05":
			mes = "MAY";
			break;
		case "06":
			mes = "JUN";
			break;
		case "07":
			mes = "JUL";
			break;
		case "08":
			mes = "AUG";
			break;
		case "09":
			mes = "SEP";
			break;
		case "10":
			mes = "OCT";
			break;
		case "11":
			mes = "NOV";
			break;
		case "12":
			mes = "DEC";
			break;
		}
		return dia + "-" + mes + "-" + anio;
	}

	/**
	 * Metodo para capturar informacion en un elemento web de caja de texto, se
	 * agrega metodo para capturar letra por letra debido a que se muestra una
	 * imagen de espera cada que se captura informacion
	 * 
	 * @param campoActual---------------------texto   para identificar el elemento
	 *                                                web al que se le enviara
	 *                                                informacion
	 * @param valor---------------------------el      valor que puede ser capturado
	 *                                                en el elemento web
	 * @param captura-------------------------bandera true cuando se requiera
	 *                                                agregar evidencia de esta
	 *                                                acción y false para que solo
	 *                                                se realice la accion
	 * 
	 * @return boolean ----------------------- se regresa un true cuando la acción
	 *         es exitosa y false cuando no se detecto el elemento o hubo un
	 *         problema
	 * 
	 * @author angeld1@hexaware.com
	 */
	public boolean capturarCasilla(String campoActual, String valor, boolean captura) {
		By elemento = obtenerElemento(campoActual, "");
		u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(9));
		u.getDriver().findElement(elemento).click();
		esperarImagenCargaDeDatos();

		if (elemento != null) {
			int contador = 0;
			u.getDriver().findElement(elemento).clear();
			esperarImagenCargaDeDatos();
			do {
				u.getDriver().findElement(elemento).sendKeys(String.valueOf(valor.charAt(contador)));
				esperarImagenCargaDeDatos();
				contador++;
			} while (valor.length() > contador);
			if (captura) {
				agregarEvidencia(elemento, campoActual);
			}
			return true;
		}
		return false;
	}

	/**
	 * Metodo para consultar el estatus de activo o inactivo de un elemento web,
	 * principalmente los combos que se muestran en pantalla
	 * 
	 * @param campoActual---------------------texto   para identificar el elemento
	 *                                                web al que se le enviara
	 *                                                informacion
	 * @param estatusEsperado-----------------estatus que se espera encontrar en el
	 *                                                elemento web buscado, true
	 *                                                para activo y false para
	 *                                                inactivo
	 * @param captura-------------------------bandera true cuando se requiera
	 *                                                agregar evidencia de esta
	 *                                                acción y false para que solo
	 *                                                se realice la accion
	 * 
	 * @return boolean ----------------------- se regresa un true cuando la acción
	 *         es exitosa y false cuando no se detecto el elemento o hubo un
	 *         problema
	 * 
	 * @author angeld1@hexaware.com
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
	 * Metodo para consultar el estatus de activo o inactivo de los elementos del
	 * paginado
	 * 
	 * @param campoActual---------------------texto   para identificar el elemento
	 *                                                web al que se le enviara
	 *                                                informacion
	 * @param captura-------------------------bandera true cuando se requiera
	 *                                                agregar evidencia de esta
	 *                                                acción y false para que solo
	 *                                                se realice la accion
	 * 
	 * @return boolean ----------------------- se regresa un true cuando la acción
	 *         es exitosa y false cuando no se detecto el elemento o hubo un
	 *         problema
	 * 
	 * @author angeld1@hexaware.com
	 */
	public boolean consultarEstatusPaginado(String campoActual, boolean captura) {
		u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
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
	 * Metodo para encontrar un elemento web esperado
	 * 
	 * @param campoActual---------------------texto   para identificar el elemento
	 *                                                web que se encontrara en
	 *                                                pantalla
	 * @param captura-------------------------bandera true cuando se requiera
	 *                                                agregar evidencia de esta
	 *                                                acción y false para que solo
	 *                                                se realice la accion
	 * 
	 * @return boolean ----------------------- se regresa un true cuando la acción
	 *         es exitosa y false cuando no se detecto el elemento o hubo un
	 *         problema
	 * 
	 * @author angeld1@hexaware.com
	 */
	public boolean encontrarElemento(String campoActual, boolean captura) {
		By elemento = obtenerElemento(campoActual, "");
		if (u.getDriver().findElement(elemento).isDisplayed()) {
			if (captura) {
				agregarEvidencia(elemento, campoActual);
			}
			return true;
		}
		return false;
	}

	/**
	 * Metodo que espera a que la imagen de carga de datos desaparezca para
	 * continuar con las siguientes acciones del test
	 * 
	 * 
	 * @author angeld1@hexaware.com
	 */
	public void esperarCargaDeDatos() {
		boolean continuar = false;
		do {
			By elemento = By.xpath("//*[@id='j_idt13']");
			if (u.getDriver().findElement(elemento).getAttribute("aria-hidden").compareToIgnoreCase("true") == 0) {
				continuar = true;
			}
		} while (!continuar);
	}

	/**
	 * Metodo que espera a que la imagen de carga de datos desaparezca para
	 * continuar con las siguientes acciones del test
	 * 
	 * @author angeld1@hexaware.com
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
		case "textoPaginas":
			elemento = By.xpath("//*[@id=\"registrosProductos_paginator_bottom\"]/span[1]");
			break;
		case "textoConsultaDeImpuestosAsignados":
			elemento = textoConsultaDeImpuestosAsignados;
			break;
		case "comboSuperGrupo":
			elemento = comboSuperGrupo;
			break;
		case "comboCategoria":
			elemento = comboCategoria;
			break;
		case "comboSubCategoria":
			elemento = comboSubCategoria;
			break;
		case "comboSegmento":
			elemento = comboSegmento;
			break;
		case "comboSubSegmento":
			elemento = comboSubSegmento;
			break;
		case "imagenCarga":
			elemento = imagenCarga;
			break;
		case "listacomboSuperGrupo":
			elemento = By.xpath("//*[@id=\"sgrp_" + adicional + "\"]");
			break;
		case "listacomboCategoria":
			elemento = By.xpath("//*[@id=\"cat_" + adicional + "\"]");
			break;
		case "listacomboSubCategoria":
			elemento = By.xpath("//*[@id=\"subcat_" + adicional + "\"]");
			break;
		case "listacomboSegmento":
			elemento = By.xpath("//*[@id=\"seg_" + adicional + "\"]");
			break;
		case "listacomboSubSegmento":
			elemento = By.xpath("//*[@id=\"subseg_" + adicional + "\"]");
			break;
		case "estatusComboSuperGrupo":
			elemento = By.xpath("//*[@id=\"sgrp_focus\"]");
			break;
		case "estatusComboCategoria":
			elemento = By.xpath("//*[@id=\"cat_focus\"]");
			break;
		case "estatusComboSubCategoria":
			elemento = By.xpath("//*[@id=\"subcat_focus\"]");
			break;
		case "estatusComboSegmento":
			elemento = By.xpath("//*[@id=\"seg_focus\"]");
			break;
		case "estatusComboSubSegmento":
			elemento = By.xpath("//*[@id=\"subseg_focus\"]");
			break;
		case "botonBuscar":
			elemento = botonBuscar;
			break;
		case "botonBuscarEstatus":
			elemento = botonBuscarEstatus;
			break;
		case "botonAtras":
			elemento = By.xpath("//*[@id='registrosProductos_paginator_bottom']/span[3]");
			break;
		case "botonAtras2":
			elemento = By.xpath("//*[@id=\"registrosProductos_paginator_bottom\"]/span[3]");
			break;
		case "botonSiguiente":
			elemento = By.xpath("//*[@id=\"registrosProductos_paginator_bottom\"]/span[5]");
			break;
		case "botonSiguiente2":
			elemento = By.xpath("//*[@id=\"registrosProductos_paginator_bottom\"]/span[5]");
			break;
		case "botonUltimaPagina":
			elemento = By.xpath("//*[@id=\"registrosProductos_paginator_bottom\"]/span[6]/span");
			break;
		case "botonPrimerPagina":
			elemento = By.xpath("//*[@id=\"registrosProductos_paginator_bottom\"]/span[2]");
			break;
		case "totalRegistrosPaginado":
			elemento = totalRegistrosPaginado;
			break;
		case "casillaClaveArticulo":
			elemento = casillaClaveArticulo;
			break;
		case "casillaDescripcion":
			elemento = casillaDescripcion;
			break;
		case "casillaClaveSAT":
			elemento = casillaClaveSAT;
			break;
		case "fechaInicio":
			elemento = casillaFechaInicio;
			break;
		case "fechaAtras":
			elemento = seleccionaFechaAtras;
			break;
		case "diaFechaInicio":
			elemento = By.xpath("//*[@id=\"ui-datepicker-div\"]/table/tbody/tr/td/a[text()='" + adicional + "']");
			break;

		}

		return elemento;
	}

	/**
	 * Metodo para separar la fecha por dia, mes y año en un arreglo separado por --
	 * 
	 * @param fechaRegistro-------------------- fecha a la que se le requiere
	 *                                          aplicar el cambio de formato
	 * 
	 * @return cadena de string --------------- arreglo de string que contiene el
	 *         valor de año mes y dia
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public String[] obtenerFormatoFechaDelRegistro(String fechaRegistro) {
		// 2021-02-11 00:00:00.0
		String partirFecha[] = fechaRegistro.split(" ");
		// 2021-02-11
		String fechaAPorcesar = partirFecha[0];
		String separarInformacionFecha[] = fechaAPorcesar.split("-");
		return separarInformacionFecha;
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
	 * Metodo para obtener el numero del mes al que correcponde el texto enviado
	 * 
	 * @param mesActual ------------- Numero del mes a buscar
	 * 
	 * @return int ------------------ Se envia el número del mes al que corresponde
	 *         el texto
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public int obtenerMes(String mesActual) {
		switch (mesActual) {
		case "January":
			return 1;
		case "February":
			return 2;
		case "March":
			return 3;
		case "April":
			return 4;
		case "May":
			return 5;
		case "June":
			return 6;
		case "July":
			return 7;
		case "August":
			return 8;
		case "September":
			return 9;
		case "October":
			return 10;
		case "November":
			return 11;
		case "December":
			return 12;
		}
		return 0;
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
	 * Se obtiene el valor actual del paginado mostrado en el grid
	 * 
	 * @param captura -------------Se recibe true cuando se requiere agregar la
	 *                acción a la evidencia y false cuando no es necesario agregar
	 *                esta accion a la evidencia
	 * 
	 * @return int --------------- Se regresa el numero maximo de elementos a
	 *         mostrar en el grid de resultados *
	 * @author nellyoliviab@hexaware.com
	 */
	public int obtenerNumeroDeRegistrosDelPaginado(boolean captura) {
		// By elementoSeleccion=obtenerElemento("totalRegistrosPaginado","");
		String limiteString = obtenerTextoDeUnElemento("totalRegistrosPaginado", false);
		if (captura) {
			agregarEvidencia(By.xpath("//*[@id='registrosProductos_rppDD']"), "NumeroPaginadoActual");
		}
		return Integer.parseInt(limiteString);
	}

	/**
	 * Se obtiene el total de registros mostrados en el grid
	 * 
	 * @return int --------------- El valor total de registros mostrado en el grid
	 * @author nellyoliviab@hexaware.com
	 */
	public int obtenerTotalRegistros() {
		List<WebElement> listaRegistros = u.getDriver()
				.findElements(By.xpath("//div[@class='ui-datatable-tablewrapper']/table/tbody/tr"));
		return listaRegistros.size();
	}

	
	/**
	 * Se obtiene el total de registros mostrados en el grid
	 * 
	 * @return int --------------- El valor total de registros mostrado en el grid
	 * @author nellyoliviab@hexaware.com
	 */
	public int obtenerTotalRegistrosAsignados() {
		List<WebElement> listaRegistros = u.getDriver()
				.findElements(By.xpath("//div/table/tbody/tr"));
		return listaRegistros.size();
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
		int totalRegistros = obtenerTotalRegistrosAsignados();
		if (obtenerTotalRegistros() > 1) {
			numeroRegistro = obtenerNumeroAleatorio(totalRegistros);
		} else {
			numeroRegistro = totalRegistros;
		}
		return numeroRegistro;
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
	 * Metodo para obtener el valor de un elemento web esperado
	 * 
	 * @param campoActual --------------elemento a buscar
	 * @param captura     ------------true para obtener la captura en la evidencia
	 * 
	 * @return regresa un string que contiene la informacion del elemento web que se
	 *         recibe.
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public String obtenerValorDeUnElemento(String campoActual, boolean captura) {
		By elementoSeleccion = obtenerElemento(campoActual, "");
		WebDriverWait wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		if (elementoSeleccion != null) {
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(elementoSeleccion));
				if (captura) {
					agregarEvidencia(elementoSeleccion, "Seleccion" + campoActual);
				}
				return u.getDriver().findElement(elementoSeleccion).getAttribute("value");
			} catch (Exception e) {
				logger.error("Error" + e);
				return null;
			}
		}
		return null;
	}

	/**
	 * Metodo para obtener el texto al que corresponde el caracter enviado
	 * 
	 * @param obtenerDatoVatTBD --------------Caracter encontrado el cual se espera
	 *                          obtener su valor equivalente
	 * 
	 * @return regresa un string que corresponde al equivalente del caracter
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public String obtenerVatType(String obtenerDatoVatTBD) {
		switch (obtenerDatoVatTBD) {
		case "B":
			obtenerDatoVatTBD = "COMPRA Y VENTA";
			break;
		case "R":
			obtenerDatoVatTBD = "VENTA";
			break;
		case "C":
			obtenerDatoVatTBD = "COMPRA/VENTA";
			break;
		}
		return obtenerDatoVatTBD;
	}

	/**
	 * Metodo para seleccionar el año o mes segun la informacion enviada
	 * 
	 * @param datoEnviado --------------valor del año o mes
	 * @param ruta        ---------------------ruta para complementar el valor del
	 *                    año o mes
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public void seleccionaAnioOMes(String datoEnviado, String ruta) {
		By etiquetaElementoActual = null;
		// datoEnviado="2023";
		int enteroEtiquetaActual = 0;
		int enteroEnviado = Integer.parseInt(datoEnviado);
		do {
			etiquetaElementoActual = By.xpath(ruta);
			String numeroActual = u.getDriver().findElement(etiquetaElementoActual).getText();
			if (datoEnviado.length() <= 2) {
				enteroEtiquetaActual = obtenerMes(numeroActual);
			} else {
				enteroEtiquetaActual = Integer.parseInt(numeroActual);
			}
			if (enteroEtiquetaActual > enteroEnviado) {
				seleccionarElemento("fechaAtras", false, "");
			}
		} while (enteroEnviado != enteroEtiquetaActual);

	}

	/**
	 * Metodo para seleccionar un elemento web
	 * 
	 * @param campoActual --------------nombre del elemento que se requiere
	 *                    seleccionar
	 * @param captura     ------------------true si la accion se requiere agregar a
	 *                    la evidencia y false cuando no
	 * @param adicional   ----------------valor adicional cuando el elemento web lo
	 *                    requiere
	 * 
	 * @return true si la accion es exitosa o false si no
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
	 * Metodo para seleccionar la fecha de Inicio contemplando año, mes y dia
	 * 
	 * @param anio    ------------------valor del año de la fecha a seleccionar
	 * @param mes     -------------------valor del mes de la fecha a seleccionar
	 * @param dia     -------------------valor del dia de la fecha a seleccionar
	 * @param captura ---------------true cuando se requiere agregar a la evidencia
	 *                y false cuando no
	 * 
	 * @return true si la accion es exitosa o false si no
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean seleccionarFechaInicio(String anio, String mes, String dia, boolean captura) {
		seleccionaAnioOMes(anio, "//*[@id=\"ui-datepicker-div\"]/div/div/span[2]");
		seleccionaAnioOMes(mes, "//*[@id=\"ui-datepicker-div\"]/div/div/span[1]");
		boolean diaSeleccion = seleccionarElemento("diaFechaInicio", captura, dia);
		return diaSeleccion;
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
	 * Metodo para validar el total de resultados mostrados en el grid de la
	 * pantalla
	 * 
	 * @param captura------------------true para obtener la captura en la evidencia
	 * 
	 * @return regresa true si fue exitosa la accion y false si no
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
	 * Metodo para validar la informacion del registro segun su numero y columna
	 * 
	 * @param captura------------------true   para obtener la captura en la
	 *                                        evidencia
	 * @param numeroRegistro-----------numero de registro mostrado en el grid actual
	 * @param numeroColumna------------numero de columna de la cual se va a obtener
	 *                                        su valor
	 * 
	 * @return regresa el valor correspondiente al registro y columna del elemento
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public String validarInformacionRegistro(boolean captura, int numeroRegistro, int numeroColumna) {
		try {
			By elementoRegistro = By.xpath("//*[@id=\"registrosProductos_data\"]/tr[@data-ri=\"" + numeroRegistro + "\"]/td[" + numeroColumna + "]");
			if (captura) {
				By elementoRegistroCaptura = By.xpath("//*[@id=\"registrosProductos_data\"]/tr[@data-ri=\"" + numeroRegistro + "\"]");
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
	 * Metodo para validar la informacion del registro que no contenga información
	 * 
	 * @param captura------------------true   para obtener la captura en la
	 *                                      su valor
	 * 
	 * @return regresa el valor correspondiente al registro y columna del elemento
	 * 
	 * @author isaacemmanuelg@hexaware.com
	 */
	public String validarInformacionRegistroVacio(boolean captura) {
		try {
			By elementoRegistro = By.xpath("//*[@id=\"registrosProductos_data\"]/tr[1]/td[1]");
			if (captura) {
				By elementoRegistroCaptura = By.xpath("//*[@id=\"registrosProductos_data\"]/tr[1]/td[1]");
				agregarEvidencia(elementoRegistroCaptura, "RegistroSeleccionado");
			}
			String texto = u.getDriver().findElement(elementoRegistro).getText();
			return texto;
		} catch (Exception e) {
			throw new AssertionError(
					"Error al validar la información del registro 1,1");
		}
	}

	/**
	 * Metodo para validar las iniciales del registro
	 * 
	 * @param registroTexto-----------texto     que se requiere validar
	 * @param itemIniciales-----------iniciales esperadas
	 * 
	 * @return regresa el valor true cuando es exitoso y false cuando no lo es
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean validarInicialesDato(String registroTexto, String itemIniciales) {
		String textoComparacion = "";
		if (registroTexto.length() != itemIniciales.length()) {
			textoComparacion = registroTexto.substring(0, (itemIniciales.length() - 1));
		} else {
			textoComparacion = registroTexto;
		}

		if (textoComparacion.compareToIgnoreCase(itemIniciales) == 0) {
			return true;
		}
		return false;
	}

}