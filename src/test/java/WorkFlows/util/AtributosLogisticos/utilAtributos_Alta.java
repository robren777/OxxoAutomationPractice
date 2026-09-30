package WorkFlows.util.AtributosLogisticos;

import org.openqa.selenium.By;

import utils.selenium.SeleniumUtil;

public class utilAtributos_Alta {
	private SeleniumUtil u;
	
	/// Informacion del articulo///
	
	By altaproductos = By.id("j_idt88");
	By buttonCb = By.cssSelector("#form\\:chkTipoCodigo > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > div:nth-child(2)");
	By inputCb = By.id("form:txtCodigoBarras");
	By inputDescripcionCorta = By.id("form:txtDescCorta");
	By inputDescripcion = By.id("form:txtDesc");
	By selectSuperGrupo = By.id("form:cmbSuperGpo_label");
	By selectCategoria = By.id("form:cmbCategoria_label");
	By inputArticuloEspejo = By.id("form:txtArtEspejoSimi");
	
	/// Informacion Logistica///
	By selectTipoNegocio = By.id("form:cmbTipoNegocio_label");
	By selectAlmacenado = By.id("form:cmbAlmacenadoProducto_label");
	By selectTipoProducto = By.id("form:cmbTipoProducto_label");
	By inputVida = By.id("form:txtVidaProd");
	By checkBoxDescuento = By.cssSelector("#form\\:chkDevolucion > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > div:nth-child(2)");
	By inputCostoNeto = By.id("form:txtCostoNetoPieza");
	By inputVentaTienda = By.id("form:txtVentaProm");
	By inputCostoCaja = By.className("form:txtCostoNetoCaja");
	
	///Informacion comercial y de contenido///
	By selectFrecuenciaVisita = By.id("form:cmbfrecuencia_label");
	By selectPresentacion = By.id("form:cmbPresentacion_label");
	By inputContenido = By.id("form:txtContenido");
	By selectMedidaContenido = By.id("form:cmbUnidad_label");
	
	/// Propuesta logistica por articulo///
	By inputPlazoPago = By.id("form:txtPlazoPagoDias");
	By inputClausula = By.id("form:txtClausulas");
	By inputComentarios = By.id("form:txtComentarios");

	///Marcas descriptivas de la muestra fisica////
	By selectMarcaProveedor = By.id("form:cmbMcaProv_label");
	By selectMarcaArticulo = By.id("form:cmbMcaArt_label");
	By buttonFoto = By.id("form:btnFoto");
	By buttonContinuarFoto = By.id("loadFile:j_idt329");
	By buttonCbFoto = By.id("form:btnCod");
	By buttonContinuarCb = By.id("loadFile:j_idt340");
	By buttonCarta = By.id("form:btnCarta");
	
	/// Guardar y continuar///
	By buttonGuardarProducto = By.id("form:btnGrdDatosBas"); 
	By buttonSigProducto = By.id("form:btnSiguiente");
	
	/**
	 * Constructor. Recibe el SeleniumUtil utilizado en el test.
	 * 
	 * @param SeleniumUtil
	 * @author 1000073289
	 */
	public utilAtributos_Alta(SeleniumUtil u) {
		super();
		this.u = u;
		
	}
	

}
