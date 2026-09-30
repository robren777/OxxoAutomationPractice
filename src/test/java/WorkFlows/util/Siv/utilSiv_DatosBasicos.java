package WorkFlows.util.Siv;

import static org.testng.Assert.assertEquals;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import modelo.TestCase;
import utils.ApiMethodsUtil;
import utils.selenium.SeleniumUtil;

public class utilSiv_DatosBasicos {
	private SeleniumUtil u;
	private TestCase testCase;
	private pomSIV pom;
	private WebDriverWait wait;

	/// WEB ELEMENTS ///
	// IDENTIFICACION DEL ARTICULO //
	By labelTipoID = By.id("tipoNumId");
	By labelCodigoBarras = By.id("codigoBarras");
	By inputPLU = By.cssSelector("input.ng-valid:nth-child(2)");
	By inputDescripcionCorta = By.id("descripcionCorta");
	By inputDescripcionLarga = By.id("descripcionLarga");

	// ESTRUCTURA DE CATEGORIA //
	By selectSuperGrupo = By.id("superGrupo");
	By selectCategoria = By.id("categoria ");
	By selectCategoriav2 = By.id("categoria");
	By selectSubCategoria = By.id("subCategoria");
	By selectSegmento = By.id("segmento ");
	By selectSegmentov2 = By.id("segmento");
	By selectSubSegmento = By.id("subSegmento");
	By labelCodigoSAT = By.id("codigoSAT");
	By labelDatosAdicionales = By.id("additionalData");

	// DATOS ADICIONALES SAT
	By inputGradosAlcoholSAT = By.id("catGradosAlcohol");
	By selectGradosToxicidadSAT = By.id("catGradosToxicidad");
	By selectTipoEnvasadoSAT = By.id("catTipoEnvasado");
	By inputGramosSAT = By.id("gramos");
	By inputKilocaloriasSAT = By.id("kilocalorias");

	By inputRangoPorcentajeAlcoholSAT = By.id("catRangoIEPS");
	By inputDescripcionSAT = By.id("catDescripcionIEPS");
	By inputIepsSAT = By.id("catIepsEncontrado");

	// IMPUESTOS ENCONTRADOS
	By tableImpuestosVenta = By.id("vatCodesTable");
	String strRowTableImpuestosVenta = "//*[@id='vatCodesTable']/tbody/tr";// elemento para localizar todas las filas de
																			// la tabla
	By impuestosNoEncontrados = By.id("noDataInput"); // Cuadro de "No se encontraron impuestos"

	// PROPIEDADES DE VENTA //
	By selectStoreOrderMultiple = By.id("storeOrderMultiple");
	By selectUnidadMedida = By.id("unidadMedida");
	By selectUnidadVenta = By.id("unidadVenta");
	By selectFactorConversion = By.id("factorConversion");
	By selectFondosPiezas = By.id("fondosPiezas");

	// INFORMACION COMERCIAL PARA LA CADENA //
	By selectCodigoFiscal = By.id("codigoFiscal");
	By inputPrecioSugerido = By.id("precioSugerido");
	By checkBoxOxxo = By.id("isOxxo");
	By checkBoxBara = By.id("isBara");
	By buttonExpandir = By.cssSelector("#tblExpandir > td:nth-child(2) > input:nth-child(1)");

	By inputBase = By.id("base");
	By imgBuscarBase = By.id("ahrefbase");
	By inputExcluir = By.id("excluir");
	By imgBuscarExcliur = By.id("ahrefexcluir");
	By inputPeatonal = By.id("peatonal");
	By imgBuscarPeatonal = By.id("ahrefpeatonal");
	By inputTurOasis = By.id("tur/oasis");
	By imgBuscarTurOasis = By.id("ahreftur/oasis");
	By inputMixtas = By.id("mixtas");
	By imgBuscarMixtas = By.id("ahrefmixtas");
	By inputBara = By.id("bara");
	By imgBuscarBara = By.id("ahrefbara");
	By imgBuscarExcluir = By.id("ahrefexcluir");

	// INFORMACION DE CONTENIDO //
	By inputPresentacion = By.id("presentacion");
	By imgBuscarPresentacion = By.id("ahrefpresentacion");
	By inputContenido = By.id("contenido");
	By selectUnidadContenido = By.id("unidadMedidaContenido");

	/// EXTRAS ///
	By inputGradosAlcohol = By.id("gradosAlcohol");
	By imgBuscarGradosAlcohol = By.id("ahrefgradosAlcohol");
	By selectDiasPublicacion = By.id("diasPublicacion");
	By inputFechaPublicacion = By.id("fechaPublicacion");
	By imgFechaPublicacion = By.cssSelector("input#fechaPublicacion ~ a > img");

	// MARCAS DESCRIPTIVAS DE LA MUESTRA FISICA
	By inputMarcaProveedor = By.id("marcaProveedor");
	By imgBuscarMarcaProveedor = By.id("ahrefmarcaProveedor");
	By inputMarcaArticulo = By.id("marcaArticulo");
	By imgBuscarMarcaArticulo = By.id("ahrefmarcaArticulo");
	By radioButtonMermaSi = By.id("rbMermaSi");
	By radioButtonMermaNo = By.id("rbMermaNo");
	By radioButtonCandadoSi = By.id("rbCandadoSi");
	By radioButtonCandadoNo = By.id("rbCandadoNo");
	By inputUdaLibre = By.id("udaLibre");

	// SIGUIENTE //
	By buttonSiguiente = By.id("btnSaveFormSelenium");
	By buttonSiguienteCatalogos = By.cssSelector("input[value=\"Siguiente\"]");

	By buttonAceptarRechazo = By.cssSelector("input[value=\"Aceptar Rechazo\"]");

	/**
	 * Constructor. Recibe el SeleniumUtil utilizado en el test.
	 * 
	 * @param SeleniumUtil
	 * @author Esmeralda Garcia Torres
	 */
	public utilSiv_DatosBasicos(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.pom = new pomSIV(this.u, testCase);
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
	}

	/**
	 * Ingresa el texto esperado en el campo.
	 * 
	 * @param descripcionCorta
	 * @author Esmeralda Garcia Torres
	 */
	public void enterDescripcionCorta(String descripcionCorta) {

		if (u.getDriver().findElement(inputDescripcionCorta).isEnabled()) {
			pom.enterOption("Descripcion Corta", descripcionCorta, inputDescripcionCorta);
		}
	}

	/**
	 * Ingresa el texto esperado en el campo.
	 * 
	 * @param descripcionLarga
	 * @author Esmeralda Garcia Torres
	 */
	public void enterDescripcionLarga(String descripcionLarga) {
		if (u.getDriver().findElement(inputDescripcionLarga).isEnabled()) {
			pom.enterOption("Descripcion Larga", descripcionLarga, inputDescripcionLarga);
		}
	}

	/**
	 * Selecciona el valor esperado en el combo.
	 * 
	 * @param superGrupo
	 * @author Esmeralda Garcia Torres
	 */
	public void selectSuperGrupo(String superGrupo) {
		if (u.getDriver().findElement(selectSuperGrupo).isEnabled()) {
			pom.selectOption("Super Grupo", superGrupo, selectSuperGrupo);
			pom.waitLoadingModal();
		}
	}

	/**
	 * Selecciona el valor esperado en el combo.
	 * 
	 * @param categoria
	 * @author Esmeralda Garcia Torres
	 */
	public void selectCategoria(String categoria) {

		if (u.getDriver().findElement(selectCategoria).isEnabled()) {
			pom.selectOption("Categoria", categoria, selectCategoria);
			pom.waitLoadingModal();
		}
	}

	public void selectCategoriaPlaza(String categoria) {
		if (u.getDriver().findElement(selectCategoriav2).isEnabled()) {
			pom.selectOption("Categoria", categoria, selectCategoriav2);
			pom.waitLoadingModal();
		}
	}

	/**
	 * Selecciona el valor esperado en el combo.
	 * 
	 * @param subCategoria
	 * @author Esmeralda Garcia Torres
	 */
	public void selectSubCategoria(String subCategoria) {
		if (u.getDriver().findElement(selectSubCategoria).isEnabled()) {
			pom.selectOption("SubCategoria", subCategoria, selectSubCategoria);
			pom.waitLoadingModal();
		}

	}

	/**
	 * Selecciona el valor esperado en el combo.
	 * 
	 * @param segmento
	 * @author Esmeralda Garcia Torres
	 */
	public void selectSegmento(String segmento) {
		if (u.getDriver().findElement(selectSegmento).isEnabled()) {
			pom.selectOption("Segmento", segmento, selectSegmento);
			pom.waitLoadingModal();
		}
	}

	public void selectSegmentoPlaza(String segmento) {
		if (u.getDriver().findElement(selectSegmentov2).isEnabled()) {
			pom.selectOption("Segmento", segmento, selectSegmentov2);
			pom.waitLoadingModal();
		}
	}

	/**
	 * Selecciona el valor esperado en el combo.
	 * 
	 * @param subSegmento
	 * @author Esmeralda Garcia Torres
	 */
	public void selectSubSegmento(String subSegmento) {
		if (u.getDriver().findElement(selectSubSegmento).isEnabled()) {
			pom.selectOption("SubSegmento", subSegmento, selectSubSegmento);
			pom.waitLoadingModal();
		}
	}

	/**
	 * Selecciona el valor esperado en el combo.
	 * 
	 * @param storeOrderMultible
	 * @author Esmeralda Garcia Torres
	 */
	public void selectStoreOrderMultiple(String storeOrderMultible) {
		if (u.getDriver().findElement(selectStoreOrderMultiple).isEnabled()) {
			pom.selectOption("Store Order Multiple", storeOrderMultible, selectStoreOrderMultiple);
			pom.waitLoadingModal();
		}
	}

	/**
	 * Selecciona el valor esperado en el combo.
	 * 
	 * @param unidadMedida
	 * @author Esmeralda Garcia Torres
	 */
	public void selectUnidadMedida(String unidadMedida) {
		if (u.getDriver().findElement(selectUnidadMedida).isEnabled()) {
			pom.selectOption("Unidad de Medida", unidadMedida, selectUnidadMedida);
			pom.waitLoadingModal();
		}
	}

	/**
	 * Selecciona el valor esperado en el combo.
	 * 
	 * @param unidadVenta
	 * @author Esmeralda Garcia Torres
	 */
	public void selectUnidadVenta(String unidadVenta) {
		if (u.getDriver().findElement(selectUnidadVenta).isEnabled()) {
			pom.selectOption("Unidad de Venta", unidadVenta, selectUnidadVenta);
			pom.waitLoadingModal();
		}
	}

	/**
	 * Selecciona el valor esperado en el combo.
	 * 
	 * @param codigoFiscal
	 * @author Esmeralda Garcia Torres
	 */
	public void selectCodigoFiscal(String codigoFiscal) {
		if (u.getDriver().findElement(selectCodigoFiscal).isEnabled()) {
			pom.selectOption("Codigo Fiscal", codigoFiscal, selectCodigoFiscal);
			pom.waitLoadingModal();
		}

	}

	/**
	 * Ingresa el valor esperado en el campo.
	 * 
	 * @param precioSugerido
	 * @author Esmeralda Garcia Torres
	 */
	public void enterPrecioSugerido(String precioSugerido) {
		if (u.getDriver().findElement(inputPrecioSugerido).isEnabled()) {
			pom.enterOption("Precio Sugerido", precioSugerido, inputPrecioSugerido);
		}
	}

	/**
	 * Busca y selecciona el valor esperado.
	 * 
	 * @param selectValor -> valor a seleccionar en el combo del modal
	 * @param valor       -> valor a buscar
	 * @author Esmeralda Garcia Torres
	 */
	public void enterPresentacion(String selectValor, String valor) {
		if (u.getDriver().findElement(imgBuscarPresentacion).isDisplayed()) {
			pom.buscarOpcionModal_2("Presentacion", imgBuscarPresentacion, inputPresentacion, selectValor, valor);
		}
	}

	/**
	 * Ingresa el valor esperado en el campo.
	 * 
	 * @param contenido
	 * @author Esmeralda Garcia Torres
	 */
	public void enterContenido(String contenido) {
		if (u.getDriver().findElement(inputContenido).isEnabled()) {
			pom.enterOption("Contenido", contenido, inputContenido);
		}
	}

	/**
	 * Selecciona el valor esperado en el combo.
	 * 
	 * @param unidadContenido
	 * @author Esmeralda Garcia Torres
	 */
	public void selectUnidadContenido(String unidadContenido) {
		if (u.getDriver().findElement(selectUnidadContenido).isEnabled()) {
			pom.selectOption("Unidad de Medida de Contenido", unidadContenido, selectUnidadContenido);
			pom.waitLoadingModal();
		}
	}

	public void enterGradosAlcohol(String selectValor, String valor) {
		if (u.getDriver().findElement(imgBuscarGradosAlcohol).isDisplayed()) {
			pom.buscarOpcionModal_2("Grados de Alcohol", imgBuscarGradosAlcohol, inputGradosAlcohol, selectValor,
					valor);
		}
	}

	/**
	 * Espera a que se muestre el boton Expandir y le da click.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void expandir() {
		pom.clickOpcion("Expandir", buttonExpandir);
	}

	/**
	 * Busca y selecciona el valor esperado.
	 * 
	 * @param base
	 * @author Esmeralda Garcia Torres
	 */
	public void enterBase(String base) {
		if (u.getDriver().findElement(imgBuscarBase).isDisplayed()) {
			pom.buscarOpcionModal_1("Base", base, imgBuscarBase);
		} else {
			pom.logL("Base no esta habilitada");
		}
	}

	/**
	 * Busca y selecciona el valor esperado.
	 * 
	 * @param excluir
	 * @author Esmeralda Garcia Torres
	 */
	public void enterExcluir(String excluir) {
		if (u.getDriver().findElement(imgBuscarExcliur).isDisplayed()) {
			pom.buscarOpcionModal_1("Excluir", excluir, imgBuscarExcliur);
		} else {
			pom.logL("Excluir no esta habilitada");
		}
	}

	/**
	 * Busca y selecciona el valor esperado.
	 * 
	 * @param peatonal
	 * @author Esmeralda Garcia Torres
	 */
	public void enterPeatonal(String peatonal) {
		if (u.getDriver().findElement(imgBuscarPeatonal).isDisplayed()) {
			pom.buscarOpcionModal_1("Peatonal", peatonal, imgBuscarPeatonal);
		} else {
			pom.logL("Peatonal no esta habilitada");
		}
	}

	/**
	 * Busca y selecciona el valor esperado.
	 * 
	 * @param turOasis
	 * @author Esmeralda Garcia Torres
	 */
	public void enterTurOasis(String turOasis) {
		if (u.getDriver().findElement(imgBuscarTurOasis).isDisplayed()) {
			pom.buscarOpcionModal_1("Tur/Oasis", turOasis, imgBuscarTurOasis);
		} else {
			pom.logL("TurOasis no esta habilitada");
		}
	}

	/**
	 * Busca y selecciona el valor esperado.
	 * 
	 * @param mixtas
	 * @author Esmeralda Garcia Torres
	 */
	public void enterMixtas(String mixtas) {
		if (u.getDriver().findElement(imgBuscarMixtas).isDisplayed()) {
			pom.buscarOpcionModal_1("Mixtas", mixtas, imgBuscarMixtas);
		} else {
			pom.logL("Mixtas no esta habilitada");
		}
	}

	/**
	 * Busca y selecciona el valor esperado.
	 * 
	 * @param mixtas
	 * @author Misraim Hernandez
	 */
	public void enterTurMixtas(String mixtas) {
		pom.buscarOpcionModal_1("mixtas", mixtas, imgBuscarMixtas);
	}

	/**
	 * Busca y selecciona el valor esperado.
	 * 
	 * @param bara
	 * @author Esmeralda Garcia Torres
	 */
	public void enterBara(String bara) {
		if (u.getDriver().findElement(imgBuscarBara).isDisplayed()) {
			pom.buscarOpcionModal_1("Bara", bara, imgBuscarBara);
		} else {
			pom.logL("Bara no esta habilitada");
		}
	}

	public void selectDiasPublicacion(String diasPublicacion) {
		if (u.getDriver().findElement(selectDiasPublicacion).isDisplayed()) {
			pom.selectOption("Dias Publicacion", diasPublicacion, selectDiasPublicacion);
		}
	}

	public void selectFechaPublicacion(String fecha) {
		if (!fecha.equalsIgnoreCase("NA") || fecha.equalsIgnoreCase("")) {
			if (u.getDriver().findElement(imgFechaPublicacion).isDisplayed()) {
				seleccionarFecha(imgFechaPublicacion, fecha);
			}
		}
	}

	/**
	 * Busca y selecciona el valor esperado.
	 * 
	 * @param selectValor -> valor a seleccionar en el combo del modal
	 * @param valor       -> valor a buscar
	 * @author Esmeralda Garcia Torres
	 */
	public void enterMarcaProveedor(String selectValor, String valor) {
		pom.buscarOpcionModal_2("Marca Proveedor", imgBuscarMarcaProveedor, inputMarcaProveedor, selectValor, valor);
	}

	/**
	 * Busca y selecciona el valor esperado.
	 * 
	 * @param selectValor -> valor a seleccionar en el combo del modal
	 * @param valor       -> valor a buscar
	 * @author Esmeralda Garcia Torres
	 */
	public void enterMarcaArticulo(String selectValor, String valor) {
		pom.buscarOpcionModal_2("Marca Articulo", imgBuscarMarcaArticulo, inputMarcaArticulo, selectValor, valor);
	}

	/**
	 * Selecciona o deselecciona el checkbox Oxxo.
	 * 
	 * @param opcion -> true o false
	 * @author Esmeralda Garcia Torres
	 */
	public void selectOxxo(Boolean opcion) {
		try {
			if (u.getDriver().findElement(checkBoxOxxo).isEnabled()) {
				if (opcion && !u.getDriver().findElement(checkBoxOxxo).isSelected()) {
					u.click(checkBoxOxxo);
				} else if (!opcion && u.getDriver().findElement(checkBoxOxxo).isSelected()) {
					u.click(checkBoxOxxo);
				}
			}
			pom.waitLoadingModal();
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar: \"" + opcion + "\" en el checkBox de Oxxo");
		}
	}

	/**
	 * Selecciona o deselecciona el checkbox Bara.
	 * 
	 * @param opcion -> true o false
	 * @author Esmeralda Garcia Torres
	 */
	public void selectBara(Boolean opcion) {
		try {
			if (u.getDriver().findElement(checkBoxBara).isEnabled()) {
				if (opcion && !u.getDriver().findElement(checkBoxBara).isSelected()) {
					u.click(checkBoxBara);
				} else if (!opcion && u.getDriver().findElement(checkBoxBara).isSelected()) {
					u.click(checkBoxBara);
				}
			}
			pom.waitLoadingModal();
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar: \"" + opcion + "\" en el checkBox de Bara");
		}
	}

	/**
	 * Selecciona el valor esperado para Merma.
	 * 
	 * @param opcion -> SI o NO
	 * @author Esmeralda Garcia Torres
	 */
	public void selectMerma(String opcion) {
		try {
			if (u.getDriver().findElement(radioButtonMermaSi).isEnabled()) {
				if (opcion.toUpperCase().equals("SI")) {
					if (!u.getDriver().findElement(radioButtonMermaSi).isSelected()) {
						u.click(radioButtonMermaSi);
					}
				} else {
					if (!u.getDriver().findElement(radioButtonMermaNo).isSelected()) {
						u.click(radioButtonMermaNo);
					}
				}
				pom.waitLoadingModal();
			} else {
				pom.logM("Merma deshabilitado");
			}
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar: \"" + opcion + "\" en Merma");
		}
	}

	/**
	 * Selecciona el valor esperado para Candado.
	 * 
	 * @param opcion -> SI o NO
	 * @author Esmeralda Garcia Torres
	 */
	public void selectCandado(String opcion) {
		try {
			if (u.getDriver().findElement(radioButtonCandadoSi).isEnabled()) {
				if (opcion.toUpperCase().equals("SI")) {
					if (!u.getDriver().findElement(radioButtonCandadoSi).isSelected()) {
						u.click(radioButtonCandadoSi);
					}
				} else {
					if (!u.getDriver().findElement(radioButtonCandadoNo).isSelected()) {
						u.click(radioButtonCandadoNo);
					}
				}
				pom.waitLoadingModal();
			} else {
				pom.logM("Candado deshabilitado");
			}
		} catch (Exception e) {
			pom.logL("" + e);
			throw new AssertionError("Fallo al seleccionar: \"" + opcion + "\" en Candado Venta");
		}
	}

	/**
	 * Ingresa el valor esperado en el campo.
	 * 
	 * @param udaLibre
	 * @author Esmeralda Garcia Torres
	 */
	public void enterUdaLibre(String udaLibre) {
		if (u.getDriver().findElement(inputUdaLibre).isEnabled()) {
			pom.enterOption("Uda Libre", udaLibre, inputUdaLibre);
		}
	}

	public void enterPLU() {
		try {
			String PLU = "";
			PLU = pom.generacionCB();
			pom.enterOption("PLU", PLU, inputPLU);
			u.click(inputDescripcionCorta);
			u.hardWait(2);
		} catch (Exception e) {
			pom.logL("" + e);
		}
	}

	/**
	 * Ingresa el PLU generado en la ventana de aprobacion de Datos Basicos
	 * desde el usuario Catalogos
	 * @author Angel Del Rio
	 * @param plu Numero de 4 o 5 digitos
	 * @date 16/Enero/2025
	 */
	public void enterPLU(String plu) {
		try {
			pom.enterOption("PLU", plu, inputPLU);
			u.click(inputDescripcionCorta);
			u.hardWait(2);
		} catch (Exception e) {
			pom.logL("" + e);
		}
	}
	
	/**
	 * Espera a que se muestre el boton Siguiente y le da click. Tambien valida que
	 * se muestre el mensaje de que se guardo con exito la informacion.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void siguiente() {
		pom.clickOpcion("Siguente", buttonSiguiente);
		String textModal = pom.msgModal();
		if (!textModal.contains("guardado correctamente")) {
			throw new AssertionError("Fallo al guardar la informacion");
		}
		pom.optionMsgModal("OK");
		u.hardWait(1);
		pom.waitLoadingModal();
	}

	public void siguienteCatalogos() {
		pom.clickOpcion("Siguente", buttonSiguienteCatalogos);
		pom.optionMsgModal("OK");
		u.hardWait(1);
		pom.waitLoadingModal();
	}

	/**
	 * Valida que la informacion extraida del input coincida con la esperada.
	 * 
	 * @param descripcionCorta
	 * @return true o false
	 * @author Esmeralda Garcia Torres
	 */
	public boolean validateDescripcionCorta(String descripcionCorta) {
		Boolean result = false;
		if (pom.getDataInput(inputDescripcionCorta).equalsIgnoreCase(descripcionCorta)) {
			result = true;
		}
		return result;
	}

	/**
	 * Valida que la informacion extraida del input coincida con la esperada.
	 * 
	 * @param descripcionLarga
	 * @return true o false
	 * @author Esmeralda Garcia Torres
	 */
	public boolean validateDescripcionLarga(String descripcionLarga) {
		Boolean result = false;
		if (pom.getDataInput(inputDescripcionLarga).equalsIgnoreCase(descripcionLarga)) {
			result = true;
		}
		return result;
	}

	/**
	 * Valida que la informacion extraida del select coincida con la esperada.
	 * 
	 * @param superGrupo
	 * @return true o false
	 * @author Esmeralda Garcia Torres
	 */
	public boolean validateSuperGrupo(String superGrupo) {
		Boolean result = false;
		String aux = pom.getDataSelect(selectSuperGrupo);
		String label = u.getDriver().findElement(By.cssSelector("select#superGrupo > option[value=\"" + aux + "\"]"))
				.getAttribute("label");
		pom.logM("Label: " + label);
		if (label.equalsIgnoreCase(superGrupo)) {
			result = true;
		}
		return result;
	}

	/**
	 * Valida que la informacion extraida del select coincida con la esperada.
	 * 
	 * @param categoria
	 * @return true o false
	 * @author Esmeralda Garcia Torres
	 */
	public boolean validateCategoria(String categoria) {
		Boolean result = false;
		String aux = pom.getDataSelect(selectCategoriav2);
		String label = u.getDriver().findElement(By.cssSelector("select#categoria > option[value=\"" + aux + "\"]"))
				.getAttribute("label");
		pom.logM("Label: " + label);
		if (label.equalsIgnoreCase(categoria)) {
			result = true;
		}
		return result;
	}

	/**
	 * Valida que la informacion extraida del select coincida con la esperada.
	 * 
	 * @param subCategoria
	 * @return true o false
	 * @author Esmeralda Garcia Torres
	 */
	public boolean validateSubCategoria(String subCategoria) {
		Boolean result = false;
		String aux = pom.getDataSelect(selectSubCategoria);
		String label = u.getDriver().findElement(By.cssSelector("select#subCategoria > option[value=\"" + aux + "\"]"))
				.getAttribute("label");
		pom.logM("Label: " + label);
		if (label.equalsIgnoreCase(subCategoria)) {
			result = true;
		}
		return result;
	}

	/**
	 * Valida que la informacion extraida del select coincida con la esperada.
	 * 
	 * @param segmento
	 * @return true o false
	 * @author Esmeralda Garcia Torres
	 */
	public boolean validateSegmento(String segmento) {
		Boolean result = false;
		String aux = pom.getDataSelect(selectSegmentov2);
		String label = u.getDriver().findElement(By.cssSelector("select#segmento > option[value=\"" + aux + "\"]"))
				.getAttribute("label");
		pom.logM("Label: " + label);
		if (label.equalsIgnoreCase(segmento)) {
			result = true;
		}
		return result;
	}

	/**
	 * Valida que la informacion extraida del select coincida con la esperada.
	 * 
	 * @param subSegmento
	 * @return true o false
	 * @author Esmeralda Garcia Torres
	 */
	public boolean validateSubSegmento(String subSegmento) {
		Boolean result = false;
		String aux = pom.getDataSelect(selectSubSegmento);
		String label = u.getDriver().findElement(By.cssSelector("select#subSegmento > option[value=\"" + aux + "\"]"))
				.getAttribute("label");
		pom.logM("Label: " + label);
		if (label.equalsIgnoreCase(subSegmento)) {
			result = true;
		}
		return result;
	}

	/**
	 * Valida que la informacion extraida del select coincida con la esperada.
	 * 
	 * @param storeOrderMultiple
	 * @return true o false
	 * @author Esmeralda Garcia Torres
	 */
	public boolean validateStoreOrderMultiple(String storeOrderMultiple) {
		Boolean result = false;
		String aux = pom.getDataSelect(selectStoreOrderMultiple);
		String label = u.getDriver()
				.findElement(By.cssSelector("select#storeOrderMultiple > option[value=\"" + aux + "\"]"))
				.getAttribute("label");
		pom.logM("Label: " + label);
		if (label.equalsIgnoreCase(storeOrderMultiple)) {
			result = true;
		}
		return result;
	}

	/**
	 * Valida que la informacion extraida del select coincida con la esperada.
	 * 
	 * @param unidadMedida
	 * @return true o false
	 * @author Esmeralda Garcia Torres
	 */
	public boolean validateUnidadMedida(String unidadMedida) {
		Boolean result = false;
		String aux = pom.getDataSelect(selectUnidadMedida);
		String label = u.getDriver().findElement(By.cssSelector("select#unidadMedida > option[value=\"" + aux + "\"]"))
				.getAttribute("label");
		pom.logM("Label: " + label);
		if (label.equalsIgnoreCase(unidadMedida)) {
			result = true;
		}
		return result;
	}

	/**
	 * Valida que la informacion extraida del select coincida con la esperada.
	 * 
	 * @param unidadVenta
	 * @return true o false
	 * @author Esmeralda Garcia Torres
	 */
	public boolean validateUnidadVenta(String unidadVenta) {
		Boolean result = false;
		String aux = pom.getDataSelect(selectUnidadVenta);
		String label = u.getDriver().findElement(By.cssSelector("select#unidadVenta > option[value=\"" + aux + "\"]"))
				.getAttribute("label");
		pom.logM("Label: " + label);
		if (label.equalsIgnoreCase(unidadVenta)) {
			result = true;
		}
		return result;
	}

	/**
	 * Valida que la informacion extraida del select coincida con la esperada.
	 * 
	 * @param codigoFiscal
	 * @return true o false
	 * @author Esmeralda Garcia Torres
	 */
	public boolean validateCodigoFiscal(String codigoFiscal) {
		Boolean result = false;
		String aux = pom.getDataSelect(selectCodigoFiscal);
		String label = u.getDriver().findElement(By.cssSelector("select#codigoFiscal > option[value=\"" + aux + "\"]"))
				.getAttribute("label");
		pom.logM("Label: " + label);
		if (label.equalsIgnoreCase(codigoFiscal)) {
			result = true;
		}
		return result;
	}

	/**
	 * Valida que la informacion extraida del input coincida con la esperada.
	 * 
	 * @param precioSugerido
	 * @return true o false
	 * @author Esmeralda Garcia Torres
	 */
	public boolean validatePrecioSugerido(String precioSugerido) {
		Boolean result = false;
		precioSugerido = precioSugerido.replace(" ", "");
		if (pom.getDataInput(inputPrecioSugerido).equalsIgnoreCase(precioSugerido)) {
			result = true;
		}
		return result;
	}

	/**
	 * Valida que la informacion extraida del input coincida con la esperada.
	 * 
	 * @param presentacion
	 * @return true o false
	 * @author Esmeralda Garcia Torres
	 */
	public boolean validatePresentacion(String presentacion) {
		Boolean result = false;
		if (pom.getDataInput(inputPresentacion).equalsIgnoreCase(presentacion)) {
			result = true;
		}
		return result;
	}

	/**
	 * Valida que la informacion extraida del input coincida con la esperada.
	 * 
	 * @param contenido
	 * @return true o false
	 * @author Esmeralda Garcia Torres
	 */
	public boolean validateContenido(String contenido) {
		Boolean result = false;
		if (pom.getDataInput(inputContenido).equalsIgnoreCase(contenido)) {
			result = true;
		}
		return result;
	}

	/**
	 * Valida que la informacion extraida del select coincida con la esperada.
	 * 
	 * @param unidadContenido
	 * @return true o false
	 * @author Esmeralda Garcia Torres
	 */
	public boolean validateUnidadContenido(String unidadContenido) {
		Boolean result = false;
		String aux = pom.getDataSelect(selectUnidadContenido);
		String label = u.getDriver()
				.findElement(By.cssSelector("select#unidadMedidaContenido > option[value=\"" + aux + "\"]"))
				.getAttribute("label");
		pom.logM("Label: " + label);
		if (label.equalsIgnoreCase(unidadContenido)) {
			result = true;
		}
		return result;
	}

	/**
	 * Valida que la informacion extraida del input coincida con la esperada.
	 * 
	 * @param marcaProveedor
	 * @return true o false
	 * @author Esmeralda Garcia Torres
	 */
	public boolean validateMarcaProveedor(String marcaProveedor) {
		Boolean result = false;
		if (pom.getDataInput(inputMarcaProveedor).equalsIgnoreCase(marcaProveedor)) {
			result = true;
		}
		return result;
	}

	/**
	 * Valida que la informacion extraida del input coincida con la esperada.
	 * 
	 * @param marcaArticulo
	 * @return true o false
	 * @author Esmeralda Garcia Torres
	 */
	public boolean validateMarcaArticulo(String marcaArticulo) {
		Boolean result = false;
		if (pom.getDataInput(inputMarcaArticulo).equalsIgnoreCase(marcaArticulo)) {
			result = true;
		}
		return result;
	}

	/**
	 * Valida que la informacion extraida del input coincida con la esperada.
	 * 
	 * @param udaLibre
	 * @return true o false
	 * @author Esmeralda Garcia Torres
	 */
	public boolean validateUdaLibre(String udaLibre) {
		Boolean result = false;
		if (pom.getDataInput(inputUdaLibre).equalsIgnoreCase(udaLibre)) {
			result = true;
		}
		return result;
	}

	/**
	 * Valida que este seleccionada la opcion esperada.
	 * 
	 * @param merma -> SI o NO
	 * @return true o false
	 * @author Esmeralda Garcia Torres
	 */
	public boolean validateMerma(String merma) {
		Boolean result = false;
		if (merma.equalsIgnoreCase("SI")) {
			if (u.getDriver().findElement(radioButtonMermaSi).isSelected()) {
				result = true;
			}
		} else {
			if (u.getDriver().findElement(radioButtonMermaNo).isSelected()) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * Valida que este seleccionada la opcion esperada.
	 * 
	 * @param candado -> SI o NO
	 * @return true o false
	 * @author Esmeralda Garcia Torres
	 */
	public boolean validateCandadoVenta(String candado) {
		Boolean result = false;
		if (candado.equalsIgnoreCase("SI")) {
			if (u.getDriver().findElement(radioButtonCandadoSi).isSelected()) {
				result = true;
			}
		} else {
			if (u.getDriver().findElement(radioButtonCandadoNo).isSelected()) {
				result = true;
			}
		}
		return result;
	}

	public void aceptarRechazoValidacion() {
		pom.clickOpcion("Aceptar Rechazo", buttonAceptarRechazo);
		u.hardWait(3);
		if (pom.msgModal().contains("Esta seguro que desea aceptar el rechazo")) {
			pom.optionMsgModal("SI RECHAZO");
			u.hardWait(1);
			if (pom.msgModal().contains("El rechazo fue aceptado")) {
				pom.optionMsgModal("OK");
				pom.waitLoadingModal();
			} else {
				throw new AssertionError("No se acepto el rechazo de Datos Basicos");
			}
		}
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

			String dayElement = "//td[contains(@class,'uib-day')]//button//span[not(contains(@class,'text-muted')) and text()=\""
					+ expectedDay + "\"]";
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
	 * Obtener el valor del campo Codigo SAT.
	 * 
	 * @return codigo SAT
	 * @author Angel del Rio
	 * @date 30/Septiembre/2024
	 */
	public String getCodigoSAT() {
		return pom.waitInputValue(labelCodigoSAT);
	}

	/**
	 * Obtener el valor del campo Additional Data.
	 * 
	 * @return adittionalData
	 * @author Angel del Rio
	 * @date 30/Septiembre/2024
	 */
	public String getAdditionalData() {
		return pom.waitInputValue(labelDatosAdicionales);
	}

	/**
	 * Ejecutar request API /portalsat/getIeps
	 * 
	 * @param uri
	 * @param value
	 * @param catalog
	 * @return String[] respuesta - Arreglo con datos obtenidos del request
	 * @author Angel Del Rio
	 * @date 30/Septiembre/2024
	 */
	public String[] getIepsAPI(String uri, String value, String catalog) {
		RestAssured.baseURI = uri;
		ApiMethodsUtil api = new ApiMethodsUtil(uri);
		String uriCompleta = uri + value + "/" + catalog;

		testCase.addCodeEvidenceCurrentStep("Request: " + uriCompleta);
		pom.logM("Request: " + uriCompleta);
		Response jsonRequest = api.getRequestMethod(uriCompleta);

		String jsonResponse = String.valueOf(jsonRequest.asPrettyString());
		String statusCode = String.valueOf(jsonRequest.getStatusCode());

		testCase.addCodeEvidenceCurrentStep("Respuesta JSON: \n" + jsonResponse);
		System.out.println("JsonResponse: \n" + jsonResponse);
		testCase.addCodeEvidenceCurrentStep("Codigo de respuesta: " + statusCode);
		System.out.println("StatusCode: " + statusCode);
		assertEquals(statusCode, "200", "El código de respuesta no es 200");
		try {

			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(jsonResponse);

			String alcoholDegrees = jsonNode.get("alcoholDegrees").asText();
			String calories = jsonNode.get("calories").asText();
			String packagingType = jsonNode.get("packagingType").asText();
			String toxicity = jsonNode.get("toxicity").asText();
			String shortDescription = jsonNode.get("shortDescription").asText();
			String description = jsonNode.get("description").asText();
			String from = jsonNode.get("from").asText();
			String until = jsonNode.get("until").asText();
			String ieps = jsonNode.get("ieps").asText();

			String[] respuesta = { alcoholDegrees, calories, packagingType, toxicity, shortDescription, description,
					from, until, ieps };
			return respuesta;
		} catch (Exception e) {
			throw new AssertionError("Error al obtener los datos de la respuesta");
		}
	}

	/**
	 * Endpoint de API SAT para obtener el codigo SAT de un articulo, basado en su
	 * estructura de categoria
	 * 
	 * @author Angel Del Rio
	 * @param uri          Base para la peticion del endpoint getSatCode/ en QA
	 * @param supergrupo   Numerico
	 * @param categoria    Numerico
	 * @param subcategoria Numerico
	 * @param segmento     Numerico
	 * @param subsegmento  Numerico
	 * @return Codigo SAT de la estructura especificada
	 * @date 15/Enero/2025
	 */
	public String getSatCodeAPI(String uri, String supergrupo, String categoria, String subcategoria, String segmento,
			String subsegmento) {
		RestAssured.baseURI = uri;
		ApiMethodsUtil api = new ApiMethodsUtil(uri);
		String uriCompleta = (uri + supergrupo + "/" + categoria + "/" + subcategoria + "/" + segmento + "/"
				+ subsegmento);

		testCase.addCodeEvidenceCurrentStep("Request: " + uriCompleta);
		pom.logM("Request: " + uriCompleta);
		Response jsonRequest = api.getRequestMethod(uriCompleta);

		String jsonResponse = String.valueOf(jsonRequest.asPrettyString());
		String statusCode = String.valueOf(jsonRequest.getStatusCode());

		testCase.addCodeEvidenceCurrentStep("Respuesta JSON: \n" + jsonResponse);
		pom.logM("JsonResponse: \n" + jsonResponse);
		testCase.addCodeEvidenceCurrentStep("Codigo de respuesta: " + statusCode);
		pom.logM("StatusCode: " + statusCode);
		assertEquals(statusCode, "200", "El código de respuesta no es 200");

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(jsonResponse);

			String codigoSATAPI = jsonNode.get("satCode").asText();
			testCase.addCodeEvidenceCurrentStep("satCode: " + codigoSATAPI);
			pom.logM("satCode: " + codigoSATAPI);
			return codigoSATAPI;
		} catch (Exception e) {
			throw new AssertionError("Error al obtener los datos de la respuesta");
		}
	}

	/**
	 * Endpoint de API SAT para obtener los datos adicionales de un articulo, basado
	 * en su estructura de categoria
	 * 
	 * @author Angel Del Rio
	 * @param uri          Base para la peticion del endpoint getAdditionalData/ en
	 *                     QA
	 * @param supergrupo   Numerico
	 * @param categoria    Numerico
	 * @param subcategoria Numerico
	 * @return Datos adicionales de la estructura especificada
	 * @date 15/Enero/2025
	 */
	public String getAdditionalDataAPI(String uri, String supergrupo, String categoria, String subcategoria) {
		String uriCompleta = (uri + supergrupo + "/" + categoria + "/" + subcategoria);

		RestAssured.baseURI = uri;
		ApiMethodsUtil api = new ApiMethodsUtil(uri);

		testCase.addCodeEvidenceCurrentStep("Request: " + uriCompleta);
		pom.logM("Request: " + uriCompleta);
		Response jsonRequest = api.getRequestMethod(uriCompleta);

		String jsonResponse = String.valueOf(jsonRequest.asPrettyString());
		String statusCode = String.valueOf(jsonRequest.getStatusCode());

		testCase.addCodeEvidenceCurrentStep("Respuesta JSON: \n" + jsonResponse);
		pom.logM("JsonResponse: \n" + jsonResponse);
		testCase.addCodeEvidenceCurrentStep("Codigo de respuesta: " + statusCode);
		pom.logM("StatusCode: " + statusCode);
		assertEquals(statusCode, "200", "El código de respuesta no es 200");

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(jsonResponse);

			String datosAdicionalesAPI = jsonNode.get("catalogValue").asText();
			String datosAdicionalesAPIArr[] = datosAdicionalesAPI.split("\\|");
			testCase.addCodeEvidenceCurrentStep("catalogValue: " + datosAdicionalesAPIArr[0]);
			pom.logM("catalogValue: " + datosAdicionalesAPIArr[0]);
			return datosAdicionalesAPIArr[0];
		} catch (Exception e) {
			throw new AssertionError("Error al obtener los datos de la respuesta");
		}
	}

	/**
	 * Ingresa el valor esperado en el campo Grados de Alcohol.
	 * 
	 * @param gradosAlcohol
	 * @author Angel del Rio
	 * @date 30/Septiembre/2024
	 */
	public void enterGradosAlcoholSAT(String gradosAlcohol) {
		if (u.getDriver().findElement(inputGradosAlcoholSAT).isEnabled()) {
			pom.enterOption("Grados de Alcohol", gradosAlcohol, inputGradosAlcoholSAT);
		}
		// Click en otro elemento para que cargue el input
		u.getDriver().findElement(inputRangoPorcentajeAlcoholSAT).click();
		scrollSiguiente();
	}

	/**
	 * Selecciona el valor esperado en el combo Grados de Toxicidad.
	 * 
	 * @param gradosToxicidad
	 * @author Andrea Contreras
	 * @date 03/Octubre/2024
	 */
	public void selectGradosToxicidadSAT(String gradosToxicidad) {
		if (gradosToxicidad.equals("1")) {
			gradosToxicidad = "9% Categoria 1";
		} else if (gradosToxicidad.equals("2")) {
			gradosToxicidad = "9% Categoria 2";
		} else if (gradosToxicidad.equals("3")) {
			gradosToxicidad = "7% Categoria 3";
		} else if (gradosToxicidad.equals("4")) {
			gradosToxicidad = "6% Categoria 4";
		}

		if (u.getDriver().findElement(selectGradosToxicidadSAT).isEnabled()) {
			pom.selectOption("Grados de Toxicidad", gradosToxicidad, selectGradosToxicidadSAT);
			pom.waitLoadingModal();
		}
	}

	/**
	 * Selecciona el valor esperado en el combo Tipo de Envasado.
	 * 
	 * @param tipoEnvasado
	 * @author Andrea Contreras
	 * @date 03/Octubre/2024
	 */
	public void selectTipoEnvasadoSAT(String tipoEnvasado) {
		if (tipoEnvasado.equals("1")) {
			tipoEnvasado = "VACIO";
		} else if (tipoEnvasado.equals("2")) {
			tipoEnvasado = "CONGELADO";
		} else if (tipoEnvasado.equals("3")) {
			tipoEnvasado = "ATMOSFERICA MODIFICADA";
		}

		if (u.getDriver().findElement(selectTipoEnvasadoSAT).isEnabled()) {
			pom.selectOption("Tipo de Envasado", tipoEnvasado, selectTipoEnvasadoSAT);
			pom.waitLoadingModal();
		}
	}

	/**
	 * Obtener el valor del campo Descripcion IEPS.
	 * 
	 * @return descripcion IEPS
	 * @author Andrea Contreras
	 * @date 03/Octubre/2024
	 */
	public String getDescripcionSATieps() {
		return pom.waitInputValue(inputDescripcionSAT);
	}

	/**
	 * Obtener el valor del campo IEPS.
	 * 
	 * @return IEPS
	 * @author Andrea Contreras
	 * @date 03/Octubre/2024
	 */
	public String getIepsSAT() {
		return pom.waitInputValue(inputIepsSAT);
	}

	/**
	 * Obtener el valor del campo Rango Porcentaje Alcohol.
	 * 
	 * @return rango
	 * @author Maria Dueñas
	 * @date 04/Octubre/2024
	 */
	public String getRangoPorcentajeAlcoholSAT() {
		return pom.waitInputValue(inputRangoPorcentajeAlcoholSAT);
	}

	/**
	 * Ingresa el valor esperado en el campo Gramos.
	 * 
	 * @param gramos
	 * @author Maria Dueñas
	 * @date 04/Octubre/2024
	 */
	public void enterGramosSAT(String gramos) {
		if (u.getDriver().findElement(inputGramosSAT).isEnabled()) {
			pom.enterOption("Gramos", gramos, inputGramosSAT);
		}
	}

	/**
	 * Ingresa el valor esperado en el campo Kilocalorias.
	 * 
	 * @param kilocalorias
	 * @author Maria Dueñas
	 * @date 04/Octubre/2024
	 */
	public void enterKilocaloriasSAT(String kilocalorias) {
		if (u.getDriver().findElement(inputKilocaloriasSAT).isEnabled()) {
			pom.enterOption("Kilocalorias", kilocalorias, inputKilocaloriasSAT);
		}
		// Click en otro elemento para que cargue el input
		u.getDriver().findElement(inputDescripcionSAT).click();
		scrollSiguiente();
	}

	/**
	 * Obtener arreglo con los valores de la tabla Impuesto a la Venta.
	 * 
	 * @return String[][]valores
	 * @author Andrea Contreras
	 * @date 15/Octubre/2024
	 */
	public String[][] getValoresImpuestosVenta() {

		By element = By.xpath(strRowTableImpuestosVenta);
		int noFilas = u.getDriver().findElements(element).size();
		String[][] valores = new String[noFilas][3];

		for (int i = 0; i < noFilas; i++) {

			valores[i][0] = u.getText(By.xpath(strRowTableImpuestosVenta + "[" + (i + 1) + "]/td[1]"));
			valores[i][1] = u.getText(By.xpath(strRowTableImpuestosVenta + "[" + (i + 1) + "]/td[2]"));
			valores[i][2] = u.getText(By.xpath(strRowTableImpuestosVenta + "[" + (i + 1) + "]/td[3]"));
		}

		return valores;

	}

	/**
	 * Obtener arreglo con los valores de la tabla Impuesto a la Venta.
	 * 
	 * @return String[][]valores
	 * @author Andrea Contreras
	 * @date 15/Octubre/2024
	 */
	public List<String[]> getListaValoresImpuestosVenta() {

		By element = By.xpath(strRowTableImpuestosVenta);
		List<String[]> data = new ArrayList<String[]>();

		for (int i = 0; i < u.getDriver().findElements(element).size(); i++) {
			String[] fila = { u.getText(By.xpath(strRowTableImpuestosVenta + "[" + (i + 1) + "]/td[1]")),
					u.getText(By.xpath(strRowTableImpuestosVenta + "[" + (i + 1) + "]/td[2]")),
					u.getText(By.xpath(strRowTableImpuestosVenta + "[" + (i + 1) + "]/td[3]")) };
			data.add(fila);
		}

		return data;
	}

	/**
	 * Hace scroll al boton de Siguiente para ver el final de la pantalla
	 * 
	 * @author Angel Del Rio
	 */
	public void scrollSiguiente() {
		u.scrollToElement(buttonSiguiente);
	}

	/**
	 * Devuelve true si no se encontraron impuestos
	 * 
	 * @return boolean
	 */
	public boolean impuestosNoEncontradosDisplayed() {
		return u.getDriver().findElement(impuestosNoEncontrados).isDisplayed();
	}

	/**
	 * Agrega un .0 al valor especificado
	 * 
	 * @param valor
	 * @return String con decimal
	 * @author Angel Del Rio
	 */
	public String agregarDecimal(String valor) {
		if (!valor.contains(".")) {
			valor = valor + ".0";
		}
		return valor;
	}

	/**
	 * Elimina un .0 al valor especificado
	 * 
	 * @param valor
	 * @return String sin decimal
	 * @author Angel Del Rio
	 */
	public String removerDecimal(String valor) {
		if (valor.contains(".0")) {
			valor = valor.substring(0, valor.length() - 2);
		}
		return valor;
	}

}
