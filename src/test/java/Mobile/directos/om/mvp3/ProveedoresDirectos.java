package Mobile.directos.om.mvp3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Mobile.util.GlobalVariables;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;
import utils.sql.SQLResult;

public class ProveedoresDirectos {
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;

	/*
	 * Localizadores de Pantalla Principal de App Proveedores Directos.
	 */

	private By logoOxxo = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/oxxo_logo");
	private By lb_Bienvenido = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/bienvenido_mesange_tv");
	private By bt_ComenzarProcesoRecepcion = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/comenzar_proceso_btn");
	private By bt_CapacitacionVisitaGuiada = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/capacitacion_btn");

	/*
	 * Localizadores generales de la APP
	 */

	private By Warnings = By.id("android:id/message");
	private By bt_No = By.id("android:id/button2");
	private By bt_Si = By.id("android:id/button1");
	private By bt_NoGral = By.id("android:id/button2");
	private By msgGral = By.id("android:id/message");

	/*
	 * Localizadores de Pantalla Inicial de Recibo Agil.
	 */

	private By logoOxxoReciboAgil = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/logoImage");
	private By lb_BienvenidoRA = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/welcomeActivationLabel");
	private By logoTruckRA = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/imgTruck");
	private By lb_ActivacionPasos = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/instructionsActivationLabel");
	private By lb_Paso1 = By.xpath("//android.widget.TextView[@text=\"Cargar Archivo de Validación\"]");
	private By lb_Paso2 = By.xpath("//android.widget.TextView[@text=\"Captura el Código de Activación\"]");
	private By bt_ComenzarRA = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/startButton");
	private By lb_CargarArchivo = By.xpath("//android.widget.TextView[@text=\"Cargar Archivo de Validación\"]");
	private By bt_CargarArchivo = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/txt_message_load_file");
	private By bt_ContinuarRA = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/continueButton");
	private By bt_DesplegarAlmacenamiento = By.xpath("//android.widget.ImageButton[@content-desc=\"Mostrar raíces\"]");
	private By bt_DescargasAlmacenamiento = By
			.xpath("//android.widget.TextView[@resource-id=\"android:id/title\" and @text=\"Descargas\"]");
	private By lb_CapturarCodigo = By.xpath("//android.widget.TextView[@text=\"Captura el Código de Activación\"]");
	private By edt_IngresarCodigo = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/activationCodeAutoComplete");
	private By bt_Activar = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/ActivateButton");
	private By bt_Help = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/txt_help");
	private By LogoHelp = By.xpath(
			"//android.widget.FrameLayout[@resource-id=\"android:id/custom\"]/android.widget.LinearLayout/android.widget.ImageView");
	private By lb_Helptext = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/tvAlertMessage");
	private By bt_AceptarHelp = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/btnClose");
	private By msg_ArchivoNoSoportado = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/txt_message_error");
	private By msg_ArchivoCargado = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/txt_message_load_file");
	private By msg_ArchivoErroneo = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/txt_message_error");
	private By archivo_valido_encriptado = By
			.xpath("//android.widget.TextView[@resource-id=\"android:id/title\" and @text=\"archivo_encriptado.aes\"]");
	private By archivo_valido_SinEncriptar = By.xpath(
			"//android.widget.TextView[@resource-id=\"android:id/title\" and @text=\"Valido_sinEncriptar.csv\"]");
	private By archivo_vacio_encriptado = By
			.xpath("//android.widget.TextView[@resource-id=\"android:id/title\" and @text=\"archivo_encriptado.aes\"]");

	/*
	 * Localizadores Mensaje Superior Visita Guiada.
	 */
	private By lb_textoSuperiorVisitaGuiada = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/visita_guiada_aviso_tv");
	private By bt_SalirVisitaGuiada = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/salir_vg_btn");

	/*
	 * Localizadores de Pantalla de Proceso de Recepción de Mercancia o Generación
	 * de pedido.
	 */

	private By lb_GenerarPedido = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/tv_title");
	private By bt_ActualizarCatalogo = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/loadCatalogButton");
	private By bt_AsistiATiendaSinEntrega = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/rbtn_qr_visita");
	private By rb_GenerarPedido_Preventa = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/preinventaristaRadioButton");
	private By rb_GenerarPedidoyEntregaTradicional = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/entregaRadioButton");
	private By rb_EscanearQRPedidoQRdeHC = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/scanExistingOrderRadioButton");
	private By tb_FuentesDeAbasto = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/supplySourceAutoComplete");
	private By tb_CodigoDeBarras = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/barcodeAutoComplete");
	private By bt_EscanearCodigoBarras = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/barcodeImageButton");
	private By bt_IngresarSku = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/barcodeInputImageButton");
	private By btnContinuar = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/continueButton");
	private By btnContinuarPHC = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/continueOrderExistButton");
	private By editEscanearCodigoQR = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/edt_qr_captured");
	private By btnCapturarQR = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/btn_capture_qr");
	private By lb_NoArticulos = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/skuTotalTitleText");
	private By totl_Articulos = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/skuTotalField");
	private By totl_Piezas = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/grandTotalField");
	private By lbResumenArticulos = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/tvw_titulo\"]");
	private By radiobtnChecarTodosArticulos = By.xpath(
			"//android.widget.CheckBox[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/chk_todo_entregado\"]");
	private By tbArticulo = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/tvw_sku\" and @text=\"ARTÍCULO\"]");
	private By tbDescripcion = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/tvw_descripcion\" and @text=\"DESCRIPCIÓN\"]");
	private By tbCant = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/tvw_cantidad\" and @text=\"CANT.\"]");
	private By btnModificarPedido = By.xpath(
			"//android.widget.Button[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/btn_modificar_pedido\"]");
	private By NoArticulosCap = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/edt_articulos_capturados_value");
	private By NoArticulosEntre = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/edt_articulos_entregados_value");
	private By cantCapturada = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/edt_cantidad_capturada_value");
	private By cantEntregada = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/edt_cantidad_entregada_value");
	private By btnGenerarQR = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/btn_generar_qr");
	private By cantidadArticulo = By.xpath(
			"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/tvw_cantidad\"])[2]");
	private By editQrGenerado = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/tvw_qr_code");
	private By lblCodigoDisponible = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/counterTextViewOne");
	private By rb_EscanearQrHtaCompra = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/scanExistingOrderRadioButton");
	private By rb_generarPedPreventa = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/preinventaristaRadioButton");
	private By rb_generarPedYEntrega = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/entregaRadioButton");
	private By bt_ContinuarGP = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/continueButton");
	private By tb_capturarQrHtaCompraPedPreventa = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/edt_qr_captured");
	private By bt_capturarQr = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/btn_capture_qr");
	private By bt_BackGeneracionPedido = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/back_button");
	private By bt_SalirGeneracionPedido = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/exitButton");

	/*
	 * Localizadores de Pantalla Capacitación con Visita Guiada.
	 */

	private By lb_SeleccionaElTipoSurtido = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/textView2");
	private By bt_Preventa = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/vg_preventa_btn");
	private By bt_SurtidoTradicional = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/vg_surtido_tradicion_btn");
	private By bt_PedidoPrecargado = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/vg_pedido_precargado_btn");

	/*
	 * Localizadores de Pantalla Capacitación Surtido Tradicional (Venta a Bordo).
	 */

	private By bt_porCapturaManual = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/vg_captura_manual_btn");
	private By bt_porHerramientaDeCompra = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/vg_herramienta_compra_btn");

	/*
	 * Localizadores de Pantalla de Visita Guiada por Captura Manual Surtido
	 * Tradicional.
	 */

	private By lb_textoResaltadoGenerarPedidoyEntregaTradicional = By.xpath(
			"/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TextView");

	/*
	 * Localizadores flujo Surtido Tradicional Visita Guiada Sin Evidencia
	 */

	private By rb_GenerarPedidoyEntregaAuto = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/entregaRadioButton");
	private By bt_FuenteDeAbastoAuto = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/supplySourceAutoComplete");
	private By bt_CodigoDeBarrasAuto = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/barcodeAutoComplete");
	private By bt_IngresarSkuAuto = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/barcodeInputImageButton");
	private By bt_IngresarAumentarSkuAuto2 = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/addProductButton");
	private By bt_ContinuarAuto = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/continueButton");
	private By bt_SiMensajeEscaneoProductosAuto = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/positive_btn");
	private By rb_CheckboxSkusAuto = By.xpath(
			"(//android.widget.CheckBox[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/chk_entregado\"])[2]");
	private By bt_GenerarQrAuto = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/btn_generar_qr");
	private By contenedorQrVisitaGuiada = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/tvw_qr_code");

	/*
	 * Localizadores pantalla Detalle de Ventas
	 */

	private By lb_DetalleVentas = By.xpath("//android.widget.TextView[@text=\"DETALLE DE VENTAS\"]");
	private By lb_Domingo = By.xpath("//android.widget.TextView[@text=\"Do\"]");
	private By lb_Lunes = By.xpath("//android.widget.TextView[@text=\"Lu\"]");
	private By lb_Martes = By.xpath("//android.widget.TextView[@text=\"Ma\"]");
	private By lb_Miercoles = By.xpath("//android.widget.TextView[@text=\"Mi\"]");
	private By lb_Jueves = By.xpath("//android.widget.TextView[@text=\"Ju\"]");
	private By lb_Viernes = By.xpath("//android.widget.TextView[@text=\"Vi\"]");
	private By lb_Sabado = By.xpath("//android.widget.TextView[@text=\"Sa\"]");
	private By lb_Semana = By.xpath("//android.widget.TextView[@text=\"Sem\"]");
	private By lb_Semana1 = By.xpath("//android.widget.TextView[@text=\"-1\"]");
	private By lb_Semana2 = By.xpath("//android.widget.TextView[@text=\"-2\"]");
	private By lb_Semana3 = By.xpath("//android.widget.TextView[@text=\"-3\"]");
	private By lb_Semana4 = By.xpath("//android.widget.TextView[@text=\"-4\"]");
	private By lb_Semana0 = By.xpath("//android.widget.TextView[@text=\"0\"]");
	private By lb_asteriscos = By.xpath("(//android.widget.TextView[@text=\"***\"])[1]");
	private By bt_Terminar = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/qrCodeFinish");
	private By bt_ModificarQR = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/qrModificar");

	/*
	 * Localizadores de Pantalla de Carga de Visita Guiada
	 */
	private By oxxoLogo = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/oxxo_logo");
	private By textoComenzando = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/titulo_tv");

	/*
	 * Localizadores de Pantalla de Visita Guiada por Pedido
	 * Precargado(Sinergia/Reple)
	 */
	private By rBtn_EscanearQRPedido = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/scanExistingOrderRadioButton");
	private By btnVerPasosParaAbrirQR = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/btn_text");
	private By campoSkuPedPre = By.xpath(
			"(//android.view.ViewGroup[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/containerItemProducts\"])[1]");
	private By contenidoQrVistaGuiada = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/tvw_qr_code");

	/*
	 * Localizadores de Pantalla Inicial de Recibo Agil.
	 */

	private By msg_NecesitasAyudaRA = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/tvAlertMessage");
	private By bt_AceptarNecesitasAyuda = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/btnClose");
	private By msg_codActivacionErrorneoRA = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/txt_message_error");
	private By msg_codActivacionCorrectoRA = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/txt_message_success");
	private By titleActivacionExitosa = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/welcomeActivationLabel");
	private By checkActivacionExitosa = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/icon_success");
	private By lbProcesoTerminado = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/txtSubtitle");
	private By btComenzarAUsarLaApp = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/StartButton");

	/*
	 * Localizadores pantalla - Generacion de Pedido Hta Compra
	 */
	private By title_GeneracionPedidoHC = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/tv_title");
	private By flechaSiguienteQr = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/btnNextPage");
	private By flechaAnteriorQr = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/btnPreviousPage");
	private By totalPags = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/tvwIndicatorPage");
	private By tb_terminarGPHtaCompra = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/finishOrderButton");
	private By lb_noArticulosGP = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/skuTotalTitleText");
	private By bt_continuarGP = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/continueButton");
	private By bt_salirGP = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/exitButton");
	private By tb_noArticulosGP = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/skuTotalField");
	private By tb_totalPiezas = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/grandTotalField");
	private By bt_ConservarGP = By.id("android:id/button2");
	private By bt_EliminarGP = By.id("android:id/button1");
	private By bt_confirmarAgrCatalogo = By.id("android:id/button1");
	private By tb_buscarSkuGP = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/edtFiltrado");
	private By bt_detalleVentaGP = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/btnDetail");
	private By lb_popUpDetalleVentas = By.xpath("//android.widget.TextView[@text=\"DETALLE DE VENTAS\"]");
	private By bt_cerrarDetalleVentas = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/btnCerrar");
	private By bt_limpiarFiltroDetVta = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/btn_limpiar_filtro");

	/*
	 * Localizadores pantalla - Resumen de Pedido
	 */
	private By titleResArtADesc = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/tvw_titulo");
	private By th_RbArticuloRP = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/chk_todo_entregado");
	private By th_descripcionRP = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/tvw_descripcion\" and @text=\"DESCRIPCIÓN\"]");
	private By th_cantRP = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/tvw_cantidad\" and @text=\"CANT.\"]");
	private By lb_noArticulosCapRP = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/tvw_articulos_capturados_title");
	private By tb_noArticulosCapRP = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/edt_articulos_capturados_value");
	private By lb_noArticulosEntrRP = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/tvw_articulos_entregados_title");
	private By tb_noArticulosEntrRP = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/edt_articulos_entregados_value");
	private By lb_cantidadCapturadaRP = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/tvw_cantidad_capturada_title");
	private By tb_cantidadCapturadaRP = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/edt_cantidad_capturada_value");
	private By lb_cantidadEntregadaRP = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/tvw_cantidad_entregada_title");
	private By tb_cantidadEntregadaRP = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/edt_cantidad_entregada_value");
	private By bt_ModificarPedidoRP = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/btn_modificar_pedido");
	private By bt_GenerarQrRP = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/btn_generar_qr");

	/*
	 * Localizadores pantalla - QR Generado
	 */
	private By titleDuracionQr = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/counterTextViewOne");
	private By textoQrGenerado = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/tvw_qr_code");
	private By bt_terminar_QrGenerado = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/qrCodeFinish");
	private By bt_modificarQr_QrGenerado = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/qrModificar");
	private By bt_cancelar = By.id("android:id/button2");
	private By bt_confirmar = By.id("android:id/button1");

	// ########## mike
	/*
	 * Localizadores app proveedores vista guiada
	 */
	// pantalla Bienvenida
	private By bt_vg_si = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/positive_btn");
	private By bt_vg_CapacitacionVisitaGuiada = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/capacitacion_btn");
	// pantalla Selecionar tipo de surtido
	private By bt_vg_SurtidoTradicional = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/vg_surtido_tradicion_btn");
	private By bt_vg_PedidoPrecargado = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/vg_pedido_precargado_btn");
	// pantalla formas para realizar el pedido
	private By bt_vg_CapturaManual = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/vg_captura_manual_btn");
	private By bt_vg_HerramientaCompra = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/vg_herramienta_compra_btn");
	private By bt_vg_continuar = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/continueButton");
	private By lb_vg_GenerarPedidoEntrega = By.xpath(
			"/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TextView");
	// **VG Aviso
	private By lb_vg_Aviso = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/visita_guiada_aviso_tv");
	private By lb_vg_Aviso_OAD = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/visita_guiada_aviso_tv"); // com.femco.oxxo.operaciones.mitienda.debug:id/visita_guiada_aviso_tv
	private By bt_vg_AvisoSalir = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/salir_vg_btn");
	private By bt_vg_AvisoSalirAOD = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/salir_vg_btn");
	private By lb_mensajePasosQR = By.xpath(
			"/hierarchy/android.widget.FrameLayout[3]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TextView");
	private By mensajeVisitaguiadaGeneral = By.xpath(
			"/hierarchy/android.widget.FrameLayout[3]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TextView");
	// VG fuente de abasto
	private By lb_vg_FuenteAbasto = By.xpath(
			"//android.widget.TextView[@text=\"Captura tu Fuente de abasto, mejor conocido como número de proveedor.\"]");
	private By lb_mensajePasosAbrirQR = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/pasos_a_hh_titulo");
	private By lb_mensajePaso1 = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/texto_paso\" and @text=\"Excelencia Operativa >\"]");
	private By lb_mensajePaso2 = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/texto_paso\" and @text=\"Herramienta de Compra >\"]");
	private By lb_mensajePaso3 = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/texto_paso\" and @text=\"Herramienta de Compra COMETA >\"]");
	private By lb_mensajePaso4 = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/texto_paso\" and @text=\"Verificación de Mercancía Pedidos Proveedor Directo >\"]");
	private By lb_mensajePaso5 = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/texto_paso\" and @text=\"Generación de Herramienta de compra\"]");
	private By bt_mensajeSiguiete = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/btn_text");
	private By bt_continiarPantallaQR = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/full_btn");
	private By success_logo = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/success_logo");
	private By Titulo_VG = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/vg_titulo");
	private By lb_mensaje = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/vg_mensaje");
	private By bt_Comenzar = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/btn_terminar");
	private By lb_PedidoGenerado = By.xpath(
			"/hierarchy/android.widget.FrameLayout[3]/android.widget.FrameLayout/android.widget.LinearLayout//android.widget.TextView[@class='android.widget.TextView' or @enabled='true' or @displayed='true']");
	private By lb_CodigoDeBarras1 = By.xpath(
			"//android.widget.TextView[@text=\"Inicia con el registro del pedido, ya sea por medio de escaneo o por ingreso manual de código de barras. Da clic sobre el ícono del código de barras para agregar los artículos.\"]");
	private By lb_vg_CodigoDeBarras = By.xpath(
			"//android.widget.TextView[@text=\"Inicia con el registro del pedido, ya sea por medio de escaneo o por ingreso manual de código de barras. Da clic sobre el ícono de la palomita para agregar los artículos.\"]");
	private By tb_vg_CodigoDeBarras = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/barcodeAutoComplete");
	private By bt_vg_paloma = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/barcodeInputImageButton");
	private By lb_vg_paso14 = By.xpath(
			"/hierarchy/android.widget.FrameLayout[3]/android.widget.FrameLayout/android.widget.LinearLayout//android.widget.TextView[@class='android.widget.TextView' or enabled='true' or displayed='true']");
	private By bt_vg_mas = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/addProductButton");
	private By lb_vg_mas = By.xpath(
			"/hierarchy/android.widget.FrameLayout[3]/android.widget.FrameLayout/android.widget.LinearLayout//android.widget.TextView[@class='android.widget.TextView' or @enabled='true' or @text=\"Al agregar artículos o editar las unidades de cada uno, se actualiza en tiempo real los campos de resumen en la parte inferior.\"]");
	private By pantalla = By.id("android:id/content");
	private By lb_vg_continuar = By.xpath(
			"/hierarchy/android.widget.FrameLayout[3]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TextView");

	/*
	 * lozalizadores App Recivo agil Activacion
	 */
	private By campoCodigoActivacion = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/activationCodeAutoComplete");
	private By bt_comenzarActivacion = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/startButton");
	private By alerta = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/alerta_mensaje");
	private By bt_cargarArchivo = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/ln_load_file");
	private By lb_ValidacionArchivoCargado = By
			.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/txt_message_load_file");
	private By icono_success = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/icon_success");
	private By lb_procesoTerminado = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/txtSubtitle");
	private By bt_ContinuarActivacion = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/continueButton");
	private By bt_usarApp = By.xpath(
			"//android.widget.Button[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/StartButton\"]");
	private By bt_activar = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/ActivateButton");
	private By lb_codigoCorrecto = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/txt_message_success");
	private By bt_vg_check = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/chk_todo_entregado");
	private By lb_vg_generarQR = By.xpath(
			"/hierarchy/android.widget.FrameLayout[3]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TextView");
	private By bt_vg_generarQR = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/btn_generar_qr");
	private By bt_vg_PasosLecturaQR = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/btn_text");
	private By lb_vg_QR = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/pasos_a_hh_body");

	// ----------------------------------------------------------------------------------------------------------

	public static String QrGeneradoVisitaGuiada;
	private String val, SkuVenta;
	public static String faValida;
	public static ArrayList<String> listaSkus = new ArrayList<>();
	private int cont = 0;
	private int cont2 = 0;
	public static String QrGenerado;

	/**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase Proveedores Directos
	 * 
	 * @author Alejandro Valdez
	 * @param au            (AppiumUtil): objeto de la clase
	 * @param testCase      (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 */
	public ProveedoresDirectos(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
		this.testCase = testCase;
		this.au = au;
		this.baseExecution = baseExecution;
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validafAbastoyNombProveedor - valida el header de la pantalla de Cambios
	 * Fisiscos Programados Pendientes(Fuente de Abasto, Proveedor y Et. resumen de
	 * cambios programados)
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void opcionPedidoPrecargadoSinergiaReple() {
		au.click(bt_PedidoPrecargado);
		testCase.addTextEvidenceCurrentStep("Se selecciona la opcion: Pedido Precargado (Sinergia/Reple)");
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaPantallaCargaCapacitacionVisitaGuiada - valida la pantalla de carga
	 * antes de empezar la capacitacion de la visita guidada
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaPantallaCargaCapacitacionVisitaGuiada() {
		boolean logo = au.driver.findElement(oxxoLogo).isDisplayed();
		boolean texto = (au.driver.findElement(textoComenzando).getText())
				.equalsIgnoreCase("Comenzando Capacitación con visita guiada");
		if ((logo || texto) == false) {
			return false;
		} else {
			testCase.addTextEvidenceCurrentStep("Se valida la pantalla de Carga de la Capacitacion de vista guiada");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * PantallaGeneracionDePedidoCapaciationGuiada - valida existe pantalla de
	 * Generacion de Pedido de la Capacitacion de visita Guiada
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean pantallaGeneracionDePedidoCapaciationGuiada() {
		boolean pantalla = au.driver.findElement(rBtn_EscanearQRPedido).isDisplayed();
		if (pantalla == false) {
			return false;
		} else {
			testCase.addTextEvidenceCurrentStep("Se muestra pantalla de Generacion de Pedido de la Capacitacion de visita Guiada");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * PantallaGeneracionDePedidoCapaciationGuiada - valida que la aplicacion pasa a
	 * segundo plano durante la Capacitacion de visita Guiada
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaAppSegundoPlanoCapacitacionGuiada() {
		au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		boolean segundo;
		try {
			segundo = au.driver.findElement(btnVerPasosParaAbrirQR).isDisplayed();
		} catch (Exception e) {
			segundo = false;
		}
		if (segundo == true) {
			return false;
		} else {
			testCase.addTextEvidenceCurrentStep(
					"Se valida que la aplicacion para a segundo plano cuando se comienza la Capacitacion Guiada");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * PantallaGeneracionDePedidoCapaciationGuiada - valida que la aplicacion pasa a
	 * segundo plano durante la Capacitacion de visita Guiada
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void enviarAplicacionASegundoPlano() {
		au.driver.pressKey(new KeyEvent(AndroidKey.HOME));
		au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		testCase.addTextEvidenceCurrentStep("Se envia la aplicacion a segundo plano");
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * llegarPantallaDetallePedidoVisitaGuiadaRepleSinEV - inicia el proceso de
	 * visita guiada reple para llegar a pantalla detalle pedido
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void llegarPantallaDetallePedidoVisitaGuiadaRepleSinEV() {
		au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
		au.click(rBtn_EscanearQRPedido);
		au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		au.click(rBtn_EscanearQRPedido);
		au.click(rBtn_EscanearQRPedido);
		au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		au.click(bt_ContinuarAuto);
		au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		au.click(campoSkuPedPre);
		au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		au.click(btnContinuarPHC);
		au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		QrGeneradoVisitaGuiada = au.driver.findElement(contenidoQrVistaGuiada).getText();
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * tapBtnContinuarRA - selecciona Boton Continuar Pantalla Recibo Agil
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapBtnContinuarRA() {
		au.click(bt_ContinuarRA);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaExistePantallaCapturaCodigoAct - valida los elementos de la pantalla
	 * cambios fisicos
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaExistePantallaCapturaCodigoAct() {
		boolean pantalla;
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.presenceOfElementLocated(lb_Paso2));
			pantalla = au.driver.findElement(lb_Paso2).isDisplayed();
		} catch (Exception e) {
			pantalla = false;
		}
		if (pantalla == false) {
			return false;
		} else {
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaPantallaCapturaCodigoActivacion - valida los elementos de la pantalla
	 * cambios fisicos
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaPantallaCapturaCodigoActivacion() {
		boolean LogoOxxo = au.driver.findElement(logoOxxoReciboAgil).isDisplayed();
		boolean titulo = au.driver.findElement(lb_BienvenidoRA).isDisplayed();
		boolean captura = au.driver.findElement(lb_CapturarCodigo).isDisplayed();
		boolean ingresa = au.driver.findElement(edt_IngresarCodigo).isDisplayed();
		boolean activar = au.driver.findElement(bt_Activar).isEnabled();
		if ((LogoOxxo || titulo || captura || ingresa || activar) == false) {
			return false;
		} else {
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * tapBtnNecesitasAyuda - valida los elementos de la pantalla cambios fisicos
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapBtnNecesitasAyuda() {
		au.scrollToElement(bt_Help);
		au.click(bt_Help);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaMsgNecesitasAyuda_RA - Valida la existencia del mensaje Necesitas ayuda
	 * en pantalla Recibo Agil
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaMsgNecesitasAyuda_RA() {
		boolean msg = au.driver.findElement(msg_NecesitasAyudaRA).getText().equals(
				"Contacta a tu administrador de TI para que active el APP en tu dispositivo o al equipo comercial para que te proporcione el código de activación.");
		boolean aceptar = au.driver.findElement(bt_AceptarNecesitasAyuda).isDisplayed();
		if ((aceptar || msg) == false) {
			return false;
		} else {
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * tapBtnAcpetarNecesitasAyuda - Selecciona el boton de ¿NecesitasAyuda? en
	 * proceso de activacion de la apk
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapBtnAcpetarNecesitasAyuda() {
		au.click(bt_AceptarNecesitasAyuda);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * ingresarCodigoDeActivacion_RA -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void ingresarCodigoDeActivacion_RA(String codAct) {
		au.sendKeys(edt_IngresarCodigo, codAct);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaCodigoActivacionIngresado -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaCodigoActivacionIngresado(String codAct) {
		boolean cod = au.driver.findElement(edt_IngresarCodigo).getText().equals(codAct);
		if (cod == false) {
			return false;
		} else {
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaMsgCodActIncorrecto_RA -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaMsgCodActIncorrecto_RA() {
		boolean msg = au.driver.findElement(msg_codActivacionErrorneoRA).getText()
				.equals("El código no coincide. Verificalo e ingrésalo nuevamente.");
		if (msg == false) {
			return false;
		} else {
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaMsgCodActCorrecto_RA -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaMsgCodActCorrecto_RA() {
		boolean msg = au.driver.findElement(msg_codActivacionCorrectoRA).getText().equals("Código correcto");
		if (msg == false) {
			return false;
		} else {
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaBtnActivarHabilitadoRA -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaBtnActivarHabilitadoRA() {
		boolean act = au.driver.findElement(bt_Activar).isEnabled();
		if (act == false) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaMsgCodActCorrecto_RA -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapBtnActivar_RA() {
		au.click(bt_Activar);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaPantallaActivacionDeAppExitosa -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaPantallaActivacionDeAppExitosa() {
		boolean title = au.driver.findElement(titleActivacionExitosa).isDisplayed();
		boolean check = au.driver.findElement(checkActivacionExitosa).isDisplayed();
		boolean texto = au.driver.findElement(lbProcesoTerminado).isDisplayed();
		boolean comenzar = au.driver.findElement(btComenzarAUsarLaApp).isEnabled();
		if ((title || check || texto || comenzar) == false) {
			return false;
		} else {
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * tapBtnComenzarAUsarLaApp -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapBtnComenzarAUsarLaApp() {
		au.click(btComenzarAUsarLaApp);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaMsgCodActCorrecto_RA -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validarSeEliminaArchivoActivacionAES() {
		boolean aes = false;
		try {
			aes = au.driver.findElement(archivo_valido_encriptado).isDisplayed();
		} catch (Exception e) {
			aes = false;
		}
		if (aes == true) {
			return false;
		} else {
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaMsgCodActCorrecto_RA - Codigo de Activacion Correcto
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void seleccionarCarpetaDescargas() {
		au.click(bt_DesplegarAlmacenamiento);
		au.click(bt_DescargasAlmacenamiento);
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * tapRdBtnEscanearQrPedOHtaCompra -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapRdBtnEscanearQrPedOHtaCompra() {
		au.click(rb_EscanearQrHtaCompra);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * tapBtnContinuarGP -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapBtnContinuarGP() {
		au.scrollToElement(bt_ContinuarGP);
		au.click(bt_ContinuarGP);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * capturarQrGenHtaCompra - ingresa los Skus obtenidos para la generacion del
	 * pedido
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void capturarQrGenHtaCompra(List<String> lista) {
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(45));
		WebElement campo = wait.until(ExpectedConditions.presenceOfElementLocated(tb_capturarQrHtaCompraPedPreventa));
		for (int i = 0; i < lista.size(); i++) {
			campo.sendKeys(lista.get(i));
			au.click(bt_capturarQr);
			if (i < lista.size() - 1) {
				campo.clear();
			}
		}
		boolean aplicarSugerido;
		try {
			WebDriverWait wait2 = new WebDriverWait(au.driver, Duration.ofSeconds(10));
			WebElement sugerido = wait2.until(ExpectedConditions.presenceOfElementLocated(bt_NoGral));
			aplicarSugerido = sugerido.isDisplayed();
		} catch (Exception e) {
			aplicarSugerido = false;
		}
		if (aplicarSugerido == true) {
			au.click(bt_NoGral);
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaExistePantallaGenPedidoHtaCompra -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaExistePantallaGenPedidoHtaCompra() {
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(45));
		wait.until(ExpectedConditions.presenceOfElementLocated(title_GeneracionPedidoHC));
		boolean title;
		try {
			title = au.driver.findElement(title_GeneracionPedidoHC).isDisplayed();
		} catch (Exception e) {
			title = false;
		}
		if (title == false) {
			return false;
		} else {
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * mostrarDistintasPagDeQrs -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void mostrarDistintasPagDeQrs() {
		swipeCoord(500, 950, 500, 750, 1000);
		String num = au.driver.findElement(totalPags).getText();
		int pags = Integer.parseInt(num.substring(num.indexOf("de") + 3, num.indexOf("(") - 1)) - 1;
		for (int i = 1; i <= pags; i++) {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(10));
			WebElement sig = wait.until(ExpectedConditions.presenceOfElementLocated(flechaSiguienteQr));
			testCase.addScreenShotCurrentStep(au, "testImage");
			if (sig.isDisplayed()) {
				sig.click();
			}
		}
		testCase.addScreenShotCurrentStep(au, "testImage");
		au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		for (int j = 1; j <= pags; j++) {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(10));
			WebElement ant = wait.until(ExpectedConditions.presenceOfElementLocated(flechaAnteriorQr));
			if (ant.isDisplayed()) {
				ant.click();
			}
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validarBtnDetalleDeVentasGenPed -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validarBtnDetalleDeVentasGenPed() {
		au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1500));
		for (int i = 1; i <= 2; i++) {
			boolean btnDetalle = au.driver.findElement(By.xpath(
					"(//android.widget.Button[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/btnDetail\"])["
							+ i + "]"))
					.isDisplayed();
			if (btnDetalle == false) {
				return false;
			}
		}
		testCase.addScreenShotCurrentStep(au, "testImage");
		au.scrollToElement(tb_terminarGPHtaCompra);
		return true;
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * tapRdBtnGenPedidoPreventa -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapRdBtnGenPedidoPreventa() {
		au.click(rb_GenerarPedido_Preventa);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * tapRdBtnGenPedidoPreventa -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapRdBtnGenPedidoYEntrega() {
		au.click(rb_GenerarPedidoyEntregaTradicional);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaRbGenerarPedPreventaChecked -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaRbGenerarPedPreventaChecked() {
		System.out.println(au.driver.findElement(rb_generarPedPreventa).getAttribute("checked"));
		if (au.driver.findElement(rb_generarPedPreventa).getAttribute("checked").equals("false")) {
			return false;
		} else {
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaRbGenerarPedyEntregaChecked -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaRbGenerarPedyEntregaChecked() {
		System.out.println(au.driver.findElement(rb_generarPedYEntrega).getAttribute("checked"));
		if (au.driver.findElement(rb_generarPedYEntrega).getAttribute("checked").equals("false")) {
			return false;
		} else {
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * capturarFaValidaEvo -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void capturarFaValidaEvo(String FaEvo) {
		au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		au.sendKeys(tb_FuentesDeAbasto, FaEvo);
		testCase.addScreenShotCurrentStep(au, "testCase");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaFACapturadaPantallaGenerarPedido -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaFACapturadaPantallaGenerarPedido(String FaEvo) {
		boolean texto = au.driver.findElement(tb_FuentesDeAbasto).getText().equals(FaEvo);
		if (texto == false) {
			return false;
		} else {
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * capturarSkusPantallaGenerarPedido -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void capturarSkusPantallaGenerarPedido(List<String> lista) {
		au.scrollToElement(lb_noArticulosGP);
		for (int i = 0; i < lista.size(); i++) {
			au.sendKeys(tb_CodigoDeBarras, lista.get(i));
			au.click(bt_IngresarSkuAuto);
		}
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaPantallaGenPedConArtAgregados -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaPantallaGenPedConArtAgregados() {
		au.scrollToElement(lb_noArticulosGP);
		for (int i = 1; i <= OAD.SkusEvo.size() && i < 4; i++) {
			String obj = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/productPageListRecycler\"]/android.view.ViewGroup["
					+ i
					+ "]//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/descriptionProductText\"]";
			boolean desc;
			try {
				desc = au.driver.findElement(By.xpath(obj)).isDisplayed();
			} catch (Exception e) {
				desc = false;
			}
			if (desc == false) {
				return false;
			}
		}
		testCase.addScreenShotCurrentStep(au, "testImage");
		return true;
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * incrementarCantPantallaGP -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void incrementarCantPantallaGP(int numClicks) {
		au.scrollToElement(lb_noArticulosGP);
		for (int i = 1; i <= OAD.SkusEvo.size() && i < 4; i++) {
			String obj = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/productPageListRecycler\"]/android.view.ViewGroup["
					+ i
					+ "]//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/addProductButton\"]";
			for (int j = 1; j <= numClicks; j++) {
				WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(10));
				WebElement mas = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(obj)));
				mas.click();
			}
		}
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaNoArtYTotalPiezasGP -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaNoArtYTotalPiezasGP() {
		au.scrollToElement(bt_continuarGP);
		int sumaCant = 0;
		for (int i = 1; i <= OAD.SkusEvo.size(); i++) {
			String obj = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/productPageListRecycler\"]/android.view.ViewGroup["
					+ i
					+ "]//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/numberProductText\"]";
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(10));
			WebElement mas = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(obj)));
			sumaCant = Integer.parseInt(mas.getText()) + sumaCant;
		}
		if (Integer.parseInt(au.driver.findElement(tb_noArticulosGP).getText()) != OAD.SkusEvo.size()) {
			return false;
		}
		if (Integer.parseInt(au.driver.findElement(tb_totalPiezas).getText()) != sumaCant) {
			return false;
		} else {
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaNoArtYTotalPiezasGP -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void eliminarArticulosGP(int cantArt) {
		for (int i = 1; i <= cantArt; i++) {
			String obj = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/productPageListRecycler\"]/android.view.ViewGroup["
					+ i
					+ "]//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/removeProductButton\"]";
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(10));
			WebElement mas = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(obj)));
			mas.click();
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaNoArtYTotalPiezasGP -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void eliminar1ArticuloGP() {
		String obj = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/productPageListRecycler\"]/android.view.ViewGroup[1]//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/removeProductButton\"]";
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(10));
		WebElement mas = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(obj)));
		mas.click();
		testCase.addScreenShotCurrentStep(au, "testImaage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaMsgSeguroEliminarArticulo -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaMsgSeguroEliminarArticulo() {
		boolean msg = au.driver.findElement(msgGral).getText().contains("¿Está seguro que desea eliminar el artículo");
		boolean conservar = au.driver.findElement(bt_ConservarGP).isDisplayed();
		boolean eliminar = au.driver.findElement(bt_EliminarGP).isDisplayed();
		if ((msg || conservar || eliminar) == false) {
			return false;
		} else {
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * tapBtnConservarMsgEliminarArt_GP -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapBtnConservar_MsgEliminarArtGP() {
		au.click(bt_ConservarGP);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * tapBtnConservarGP -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapBtnEliminar_MsgEliminarArtGP() {
		au.click(bt_EliminarGP);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * tapBtnSalirGP -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapBtnSalirGP() {
		au.click(bt_salirGP);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaMsgSeguroTerminarEscaneo -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaMsgSeguroTerminarEscaneo() {
		boolean msg = au.driver.findElement(msgGral).getText()
				.contains("¿Está seguro que desea terminar el escaneo de productos?");
		boolean no = au.driver.findElement(bt_No).isDisplayed();
		boolean si = au.driver.findElement(bt_Si).isDisplayed();
		if ((msg || no || si) == false) {
			return false;
		} else {
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * tapBtnSiGral -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapBtnSiGral() {
		au.click(bt_Si);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * msgAgregarArtAlCatalogo_SE -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void msgAgregarArtAlCatalogo_SE() {
		boolean msg;
		try {
			msg = au.driver.findElement(bt_confirmarAgrCatalogo).isDisplayed();
		} catch (Exception e) {
			msg = false;
		}
		if (msg == true) {
			au.click(bt_confirmarAgrCatalogo);
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validarPantallaResumenDePedido -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validarPantallaResumenDePedido() {
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(45));
		wait.until(ExpectedConditions.presenceOfElementLocated(titleResArtADesc));
		try {
			boolean title = au.driver.findElement(titleResArtADesc).isDisplayed();
			boolean articulos = au.driver.findElement(th_RbArticuloRP).isDisplayed();
			boolean descripcion = au.driver.findElement(th_descripcionRP).isDisplayed();
			boolean cant = au.driver.findElement(th_cantRP).isDisplayed();
			if ((title || articulos || descripcion || cant) == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.scrollToElement(bt_GenerarQrAuto);
			boolean lbArtCap = au.driver.findElement(lb_noArticulosCapRP).isDisplayed();
			boolean tbArtCap = au.driver.findElement(tb_noArticulosCapRP).isDisplayed();
			boolean lbArtEnt = au.driver.findElement(lb_noArticulosEntrRP).isDisplayed();
			boolean tbArtEnt = au.driver.findElement(tb_noArticulosEntrRP).isDisplayed();
			boolean lbCantCap = au.driver.findElement(lb_cantidadCapturadaRP).isDisplayed();
			boolean tbCantCap = au.driver.findElement(tb_cantidadCapturadaRP).isDisplayed();
			boolean lbCantEnt = au.driver.findElement(lb_cantidadEntregadaRP).isDisplayed();
			boolean tbCantEnt = au.driver.findElement(tb_cantidadEntregadaRP).isDisplayed();
			boolean modificar = au.driver.findElement(bt_ModificarPedidoRP).isDisplayed();
			boolean generar = au.driver.findElement(bt_GenerarQrRP).isDisplayed();
			if ((lbArtCap || tbArtCap || lbArtEnt || tbArtEnt || lbCantCap || tbCantCap || lbCantEnt || tbCantEnt
					|| modificar || generar) == false) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		testCase.addScreenShotCurrentStep(au, "testImage");
		return true;
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * tapBtnModificarPedidoRP -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapBtnModificarPedidoRP() {
		au.click(bt_ModificarPedidoRP);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * tapRbCheckAll_RP -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapRbCheckAll_RP() {
		ScrollToTop_xTexto("Resumen de artículos a descargar del camión");
		au.click(th_RbArticuloRP);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaTodosArticulosSeleccionados_RP -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaTodosArticulosSeleccionados_RP(int cantArt) {
		for (int i = 1; i <= cantArt; i++) {
			String obj = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/rvw_productos\"]/android.view.ViewGroup["
					+ i
					+ "]//android.widget.CheckBox[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/chk_entregado\"]";
			boolean art;
			try {
				art = au.driver.findElement(By.xpath(obj)).isDisplayed();
			} catch (Exception e) {
				art = false;
			}
			if (art == false) {
				return false;
			}
		}
		testCase.addScreenShotCurrentStep(au, "testImage");
		return true;
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaBtGenerarPedidoHabilitado_RP -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaBtGenerarPedidoHabilitado_RP() {
		au.scrollToElement(bt_GenerarQrRP);
		boolean estado;
		try {
			estado = au.driver.findElement(bt_GenerarQrRP).isDisplayed();
		} catch (Exception e) {
			estado = false;
		}
		if (estado == false) {
			return false;
		} else {
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * tapBtGenerarQr_RP -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapBtGenerarQr_RP() {
		au.click(bt_GenerarQrRP);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * ScrollToTop_xTexto - Se dezliza para encontrar el texto solicitado
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void ScrollToTop_xTexto(String texto) {
		au.driver.findElement(AppiumBy.androidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"" + texto
						+ "\"))"));
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaPantallaQrGenerado - valida elementos en pantalla QR generado
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaPantallaQrGenerado() {
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(45));
		wait.until(ExpectedConditions.presenceOfElementLocated(textoQrGenerado));
		ScrollToTop_xTexto("El CÓDIGO QR ESTARÁ DISPONIBLE");
		boolean title = au.driver.findElement(titleDuracionQr).isDisplayed();
		au.scrollToElement(bt_modificarQr_QrGenerado);
		boolean textoQr = au.driver.findElement(textoQrGenerado).isDisplayed();
		boolean terminar = au.driver.findElement(bt_terminar_QrGenerado).isDisplayed();
		boolean modificar = au.driver.findElement(bt_modificarQr_QrGenerado).isDisplayed();
		if ((title || textoQr || terminar || modificar) == false) {
			return false;
		} else {
			testCase.addTextEvidenceCurrentStep(
					"Se valida existencia de pantalla de QR generado con: Vigencia de QR, QR, Boton Terminar, Boton ModificarQr,**Pantalla Protegida contra SS");
			return true;
		}

	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * tapModificarQR_pantallaQRGenerado -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapModificarQR_pantallaQRGenerado() {
		au.click(bt_modificarQr_QrGenerado);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaMsgSeguroEditarPedido -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaMsgSeguroEditarPedido() {
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.presenceOfElementLocated(msgGral));
		boolean msg = au.driver.findElement(msgGral).getText().equals("¿Está seguro de regresar a editar el pedido?");
		boolean cancelar = au.driver.findElement(bt_cancelar).isDisplayed();
		boolean confirmar = au.driver.findElement(bt_confirmar).isDisplayed();
		if ((msg || cancelar || confirmar) == false) {
			return false;
		} else {
			testCase.addScreenShotCurrentStep(au, "testIamge");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaMsgSeguroEditarPedido -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapConfirmarRegresarAEditarPedido() {
		au.click(bt_confirmar);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * buscarSkuConVentasYAbrirDetalleDeVentas -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void buscarSkuConVentasYAbrirDetalleDeVentas(String sku) {
		au.sendKeys(tb_buscarSkuGP, sku);
		((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
		au.click(bt_detalleVentaGP);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaVentanaDetalleDeVentas -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaVentanaDetalleDeVentas() {
		boolean popUp;
		try {
			popUp = au.driver.findElement(lb_popUpDetalleVentas).isDisplayed();
		} catch (Exception e) {
			popUp = false;
		}
		if (popUp == false) {
			return false;
		} else {
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaPromediosDetalleDeVenta -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaPromediosDetalleDeVenta() {
		List<String> dias = new ArrayList<>(Arrays.asList("Do","Lu","Ma","Mi","Ju","Vi","Sa"));
		for (int i = 0; i <= 6; i++) {
			Float suma = 0.0f;
			int cont = 0;
			for (int j = 2; j <= 6; j++) {
				String path = "//android.widget.TableLayout[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/tableLayout\"]/android.widget.TableRow["
						+ j + "]//android.widget.TextView[@index=\"" + i + "\"]";
				String valor = au.driver.findElement(By.xpath(path)).getText();
				if (valor.equals("") || valor.equals("HOY") || valor.equals("***")) {
					continue;
				} else {
					suma = suma + Float.parseFloat(valor);
					cont++;
				}
			}
			String path = "//android.widget.TableLayout[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/tableLayout\"]/android.widget.TableRow[7]//android.widget.TextView[@index=\""
					+ i + "\"]";
			Float promedio = Float.parseFloat(au.driver.findElement(By.xpath(path)).getText());
			 System.out.println("Dias Vendidos: "+ cont);
			 System.out.println(dias.get(i)+" Total: "+suma);
			 System.out.println(dias.get(i)+" Promedio: "+promedio);
			 System.out.println("################################");
			if (promedio != suma / cont) {
				return false;
			}
		}
		testCase.addScreenShotCurrentStep(au, "testImage");
		return true;
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaPromediosDetalleDeVenta -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaSemanasConVentas(int semanas) {
		for (int i = 0; i <= 6; i++) {
			int cont = 0;
			for (int j = 2; j <= 6; j++) {
				String path = "//android.widget.TableLayout[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/tableLayout\"]/android.widget.TableRow["
						+ j + "]//android.widget.TextView[@index=\"" + i + "\"]";
				String valor = au.driver.findElement(By.xpath(path)).getText();
				if (valor.equals("") || valor.equals("HOY") || valor.equals("***")) {
					continue;
				} else {
					cont++;
				}
			}
			if (cont < semanas) {
				return false;
			}
		}
		testCase.addScreenShotCurrentStep(au, "testImage");
		return true;
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * cerrarDetalleDeVentasGP_SE -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void cerrarDetalleDeVentasGP_SE() {
		au.click(bt_cerrarDetalleVentas);
		au.click(bt_limpiarFiltroDetVta);
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * cerrarDetalleDeVentasGP_SE -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean eliminarVentasSimuladas(List<String> articulos) {
		try {
			for (int i = 0; i < articulos.size(); i++) {
				Connection conn = DriverManager.getConnection(GlobalVariables.urlSatelitesPV10_17,
						GlobalVariables.userPV10_17, GlobalVariables.passwordPV10_17);
				String query1 = "DELETE FROM [XposStore].[dbo].[ITM_STCS_OX] where SKU = '" + articulos.get(i)
						+ "' AND CD_STCS_ID = 340";
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(query1);
				stmt.close();
				conn.close();
			}
		} catch (Exception e) {
			return false;
		}
		System.out.println("Ventas Eliminadas.....");
		return true;
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validación de pantalla principal de APP proveedores Directos.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean pantallaPrincipalProveedoresDirectos() {
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
			wait.until(ExpectedConditions.presenceOfElementLocated(logoOxxo));
			boolean logOxxo = au.driver.findElement(logoOxxo).isEnabled();
			if (logOxxo == false) {
				testCase.addTextEvidenceCurrentStep("El Logotipo de Oxxo no se muestra de manera correcta.");
			}
			String lbBienvenido = au.driver.findElement(lb_Bienvenido).getText();
			if (lbBienvenido.contains("¡Bienvenido!") == false) {
				testCase.addTextEvidenceCurrentStep("No se muestra el texto de manera correcta.");
			}
			boolean ComenzarProcesoRecepcion = au.driver.findElement(bt_ComenzarProcesoRecepcion).isEnabled();
			if (ComenzarProcesoRecepcion == false) {
				testCase.addTextEvidenceCurrentStep("No se muestra el botón de manera correcta.");
			}
			boolean CapacitacionVisitaGuiada = au.driver.findElement(bt_CapacitacionVisitaGuiada).isEnabled();
			if (CapacitacionVisitaGuiada == false) {
				testCase.addTextEvidenceCurrentStep("No se muestra el botón de manera correcta.");
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep(
					"La pantalla principal de la App Proveedores Directos no se muestra de manera correcta.");
			return false;
		}

	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validación de POP-UP de botón ¿Necesitas Ayuda?.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean popUp_NecesitasAyuda() {
		try {

			boolean LHelp = au.driver.findElement(LogoHelp).isEnabled();
			if (LHelp == false) {
				return false;
			}

			String Helptext = au.driver.findElement(lb_Helptext).getText();
			if (Helptext.contains("Contacta a tu administrador de TI") == false) {
				return false;
			}

			boolean AceptarHelp = au.driver.findElement(bt_AceptarHelp).isEnabled();
			if (AceptarHelp == false) {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}

	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validación de pantalla de Ingreso de Código de Recibo Agil de APP proveedores
	 * Directos.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean pantallaReciboAgilIngresoCodigo() {
		try {

			String GenerarPedido = au.driver.findElement(lb_BienvenidoRA).getText();
			if (GenerarPedido.contains("Bienvenido a Recibo Ágil") == false) {
				return false;
			}

			boolean LogoOxxo = au.driver.findElement(logoOxxoReciboAgil).isEnabled();
			if (LogoOxxo == false) {
				return false;
			}

			String CapturarCodigo = au.driver.findElement(lb_CapturarCodigo).getText();
			if (CapturarCodigo.contains("Captura el Código de Activación") == false) {
				return false;
			}

			boolean IngresaCodigo = au.driver.findElement(edt_IngresarCodigo).isEnabled();
			if (IngresaCodigo == false) {
				return false;
			}

			boolean ActivarRA = au.driver.findElement(bt_Activar).isEnabled();
			if (ActivarRA == true) {
				return false;
			}

			au.scrollToElement(bt_Help);

			String HELP = au.driver.findElement(bt_Help).getText();
			if (HELP.contains("¿Necesitas ayuda?") == false) {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}

	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Leer código(s) QR generados en Herramienta de Compra de HH.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean leerQRHandHeldHC() {

		try {

			au.scrollToElement(btnContinuar);
			au.click(btnContinuar);

			for (int i = 0; i < Integer.parseInt(OAD.TotalQR); i++) {
				au.sendKeys(editEscanearCodigoQR, OAD.codigosQr.get(i));
				au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
				testCase.addScreenShotCurrentStep(au, "testImage");
				au.click(btnCapturarQR);
			}
			au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
			opcionNoSE();

			return true;

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	public boolean leerQRHandHeldHCTradicional() {

		try {
			for (int i = 0; i < Integer.parseInt(OAD.TotalQR); i++) {
				au.sendKeys(editEscanearCodigoQR, OAD.codigosQr.get(i));
				au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
				testCase.addScreenShotCurrentStep(au, "testImage");
				au.click(btnCapturarQR);
			}
			au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
			opcionNoSE();

			return true;

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	public boolean leerQRHandHeldHCTradicionalSE() {

		try {

			for (int i = 0; i < Integer.parseInt(OAD.TotalQR); i++) {
				au.sendKeys(editEscanearCodigoQR, OAD.codigosQr.get(i));
				au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
				au.click(btnCapturarQR);
			}

			au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
			opcionNoSE();

			return true;

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	public boolean leerQRHandHeldHCT() {
		try {

			au.scrollToElement(btnContinuar);
			au.click(btnContinuar);

			for (int i = 0; i < Integer.parseInt(OAD.TotalQR); i++) {
				au.sendKeys(editEscanearCodigoQR, OAD.codigosQr.get(i));

				au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
				testCase.addScreenShotCurrentStep(au, "testImage");
				au.click(btnCapturarQR);
			}
			au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
			return true;

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validar articulos de pedido Repartidor HC capturado.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean validarSkusQr_HC() {

		String XpathPadre = "(//android.view.ViewGroup[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/containerItemProducts\"])";
		String XpathPadre1 = "(//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/tvw_inventario\"])";
		String XpathPadre2 = "(//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/tvw_sugerido\"])";
		String XpathPadre3 = "(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/btn_restar\"])";
		String XpathPadre4 = "(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/btn_sumar\"])";

		try {

			for (int i = 1; i <= 2; i++) {
				String XpDesc1 = XpathPadre + "[" + i + "]";
				String XpDesc2 = XpathPadre1 + "[" + i + "]";
				String XpDesc3 = XpathPadre2 + "[" + i + "]";
				String XpDesc4 = XpathPadre3 + "[" + i + "]";
				String XpDesc5 = XpathPadre4 + "[" + i + "]";

				boolean p1 = au.driver.findElement(By.xpath(XpDesc1)).isDisplayed();
				boolean p2 = au.driver.findElement(By.xpath(XpDesc2)).isDisplayed();
				boolean p3 = au.driver.findElement(By.xpath(XpDesc3)).isDisplayed();
				boolean p4 = au.driver.findElement(By.xpath(XpDesc4)).isDisplayed();
				boolean p5 = au.driver.findElement(By.xpath(XpDesc5)).isDisplayed();

				if ((p1 != true) || (p2 != true) || (p3 != true) || (p4 != true) || (p5 != true)) {
					return false;
				}
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validar botón Detalle de Ventas dentro de la pantalla.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean validarBtnDetalleVentas() {

		String XpathPadre = "(//android.widget.Button[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/btnDetail\"])";

		try {

			for (int i = 1; i <= 2; i++) {
				String XpDesc1 = XpathPadre + "[" + i + "]";

				boolean p1 = au.driver.findElement(By.xpath(XpDesc1)).isDisplayed();

				if ((p1 != true)) {
					return false;
				}

			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validación de que se muestra pantalla de Contenido del Dispositivo.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean existenciaContenidoDispositivo() {
		try {

			boolean Almacenamiento = au.driver.findElement(bt_DesplegarAlmacenamiento).isEnabled();
			if (Almacenamiento == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}

	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validación de botón Continuar Habilitado..
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean validarContinuarHabilitado() {
		try {

			au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));

			boolean ContinuarRA = au.driver.findElement(bt_ContinuarRA).isEnabled();
			if (ContinuarRA == false) {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}

	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validación de botón Continuar Deshabilitado.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean validarContinuarDeshabilitado() {
		try {
			au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
			au.scrollToElement(bt_ContinuarRA);
			boolean ContinuarRA = au.driver.findElement(bt_ContinuarRA).isEnabled();
			if (ContinuarRA == true) {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}

	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validación de pantalla carga de archivos Recibo Agil de APP proveedores
	 * Directos.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean pantallaReciboAgilCargaArchivos() {
		try {

			String GenerarPedido = au.driver.findElement(lb_BienvenidoRA).getText();
			if (GenerarPedido.contains("Bienvenido a Recibo Ágil") == false) {
				return false;
			}

			boolean LogoOxxo = au.driver.findElement(logoOxxoReciboAgil).isEnabled();
			if (LogoOxxo == false) {
				return false;
			}

			String CargarArchivo = au.driver.findElement(lb_CargarArchivo).getText();
			if (CargarArchivo.contains("Cargar Archivo de Validación") == false) {
				return false;
			}

			boolean CargarArchivoRA = au.driver.findElement(bt_CargarArchivo).isEnabled();
			if (CargarArchivoRA == false) {
				return false;
			}

			boolean ContinuarRA = au.driver.findElement(bt_ContinuarRA).isEnabled();
			if (ContinuarRA == true) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}

	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validación de que se muestra pantalla de Recibo Agil.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean existenciaReciboAgil() {
		try {

			String GenerarPedido = au.driver.findElement(lb_BienvenidoRA).getText();
			if (GenerarPedido.contains("Bienvenido a Recibo Ágil") == false) {
				return false;
			}

			boolean LogoOxxo = au.driver.findElement(logoOxxoReciboAgil).isEnabled();
			if (LogoOxxo == false) {
				return false;
			}

			boolean LogoTruck = au.driver.findElement(logoTruckRA).isEnabled();
			if (LogoTruck == false) {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}

	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validación de pantalla principal Recibo Agil de APP proveedores Directos.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean pantallaReciboAgil() {
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
			wait.until(ExpectedConditions.presenceOfElementLocated(lb_BienvenidoRA));
			String GenerarPedido = au.driver.findElement(lb_BienvenidoRA).getText();
			if (GenerarPedido.contains("Bienvenido a Recibo Ágil") == false) {
				return false;
			}

			boolean LogoOxxo = au.driver.findElement(logoOxxoReciboAgil).isEnabled();
			if (LogoOxxo == false) {
				return false;
			}

			boolean LogoTruck = au.driver.findElement(logoTruckRA).isEnabled();
			if (LogoTruck == false) {
				return false;
			}

			String ActivacionPasos = au.driver.findElement(lb_ActivacionPasos).getText();
			if (ActivacionPasos.contains("Para activar la aplicación, favor de seguir") == false) {
				return false;
			}

			boolean Paso1 = au.driver.findElement(lb_Paso1).isEnabled();
			if (Paso1 == false) {
				return false;
			}

			boolean Paso2 = au.driver.findElement(lb_Paso2).isEnabled();
			if (Paso2 == false) {
				return false;
			}
			au.scrollToElement(bt_ComenzarRA);
			boolean ComenzarRA = au.driver.findElement(bt_ComenzarRA).isEnabled();
			if (ComenzarRA == false) {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}

	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validación de Estructura de pantalla Detalle de Ventas.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean pantallaDetalleDeVentasEstructura() {
		try {

			boolean titulo = au.driver.findElement(lb_DetalleVentas).isDisplayed();
			boolean dom = au.driver.findElement(lb_Domingo).isDisplayed();
			boolean lun = au.driver.findElement(lb_Lunes).isDisplayed();
			boolean mar = au.driver.findElement(lb_Martes).isDisplayed();
			boolean mi = au.driver.findElement(lb_Miercoles).isDisplayed();
			boolean jue = au.driver.findElement(lb_Jueves).isDisplayed();
			boolean vie = au.driver.findElement(lb_Viernes).isDisplayed();
			boolean sab = au.driver.findElement(lb_Sabado).isDisplayed();
			boolean sem = au.driver.findElement(lb_Semana).isDisplayed();
			boolean sem1 = au.driver.findElement(lb_Semana1).isDisplayed();
			boolean sem2 = au.driver.findElement(lb_Semana2).isDisplayed();
			boolean sem3 = au.driver.findElement(lb_Semana3).isDisplayed();
			boolean sem4 = au.driver.findElement(lb_Semana4).isDisplayed();
			boolean sem0 = au.driver.findElement(lb_Semana0).isDisplayed();

			if ((titulo != true) || (dom != true) || (lun != true) || (mar != true) || (mi != true) || (jue != true)
					|| (vie != true) || (sab != true) || (sem != true) || (sem1 != true) || (sem2 != true)
					|| (sem3 != true) || (sem4 != true) || (sem0 != true)) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validación asteriscos en semana de pantalla Detalla de Ventas.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean asteriscos_DetalleVentas() {
		try {

			boolean asterisco = au.driver.findElement(lb_asteriscos).isDisplayed();

			if ((asterisco != true)) {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;

		} catch (Exception e) {

			return false;
		}

	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validación de pantalla principal de APP proveedores Directos.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean pantallaPrincipalGeneraciondePedidoT() {
		try {

			au.driver.findElement(AppiumBy.androidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"ACTUALIZAR CATÁLOGO\"))"));

			String GenerarPedido = au.driver.findElement(lb_GenerarPedido).getText();
			if (GenerarPedido.contains("Generar Pedido") == false) {
				return false;
			}

			boolean ActualizarCatalogo = au.driver.findElement(bt_ActualizarCatalogo).isEnabled();
			if (ActualizarCatalogo == false) {
				return false;
			}

			boolean AsistiATiendaSinEntrega = au.driver.findElement(bt_AsistiATiendaSinEntrega).isEnabled();
			if (AsistiATiendaSinEntrega == false) {
				return false;
			}

			boolean GenerarPedido_Preventa = au.driver.findElement(rb_GenerarPedido_Preventa).isEnabled();
			if (GenerarPedido_Preventa == false) {
				return false;
			}

			boolean GenerarPedidoyEntregaTradicional = au.driver.findElement(rb_GenerarPedidoyEntregaTradicional)
					.isEnabled();
			if (GenerarPedidoyEntregaTradicional == false) {
				return false;
			}

			boolean EscanearQRPedidoQRdeHC = au.driver.findElement(rb_EscanearQRPedidoQRdeHC).isEnabled();
			if (EscanearQRPedidoQRdeHC == false) {
				return false;
			}

			boolean FuentesDeAbasto = au.driver.findElement(tb_FuentesDeAbasto).isDisplayed();
			if (FuentesDeAbasto == false) {
				return false;
			}

			boolean CodigoDeBarras = au.driver.findElement(tb_CodigoDeBarras).isDisplayed();
			if (CodigoDeBarras == false) {
				return false;
			}

			boolean EscanearCodigoBarras = au.driver.findElement(bt_EscanearCodigoBarras).isDisplayed();
			if (EscanearCodigoBarras == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}

	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validación de pantalla principal de APP proveedores Directos.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean pantallaPrincipalGeneraciondePedido() {
		try {

			au.driver.findElement(AppiumBy.androidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"ACTUALIZAR CATÁLOGO\"))"));

			String GenerarPedido = au.driver.findElement(lb_GenerarPedido).getText();
			if (GenerarPedido.contains("Generar Pedido") == false) {
				return false;
			}

			boolean ActualizarCatalogo = au.driver.findElement(bt_ActualizarCatalogo).isEnabled();
			if (ActualizarCatalogo == false) {
				return false;
			}

			boolean AsistiATiendaSinEntrega = au.driver.findElement(bt_AsistiATiendaSinEntrega).isEnabled();
			if (AsistiATiendaSinEntrega == false) {
				return false;
			}

			boolean GenerarPedido_Preventa = au.driver.findElement(rb_GenerarPedido_Preventa).isEnabled();
			if (GenerarPedido_Preventa == false) {
				return false;
			}

			boolean GenerarPedidoyEntregaTradicional = au.driver.findElement(rb_GenerarPedidoyEntregaTradicional)
					.isEnabled();
			if (GenerarPedidoyEntregaTradicional == false) {
				return false;
			}

			boolean EscanearQRPedidoQRdeHC = au.driver.findElement(rb_EscanearQRPedidoQRdeHC).isEnabled();
			if (EscanearQRPedidoQRdeHC == false) {
				return false;
			}

			boolean FuentesDeAbasto = au.driver.findElement(tb_FuentesDeAbasto).isDisplayed();
			if (FuentesDeAbasto == false) {
				return false;
			}

			boolean CodigoDeBarras = au.driver.findElement(tb_CodigoDeBarras).isDisplayed();
			if (CodigoDeBarras == false) {
				return false;
			}

			boolean EscanearCodigoBarras = au.driver.findElement(bt_EscanearCodigoBarras).isEnabled();
			if (EscanearCodigoBarras == false) {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}

	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validación de pantalla principal de Capacitación Visita Guiada.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean pantallaPrincipalCapacitacionVisitaGuiada() {
		try {

			boolean logOxxo = au.driver.findElement(logoOxxo).isEnabled();
			if (logOxxo == false) {
				testCase.addTextEvidenceCurrentStep("El Logotipo de Oxxo no se muestra de manera correcta.");
			}

			String SeleccionaElTipoSurtido = au.driver.findElement(lb_SeleccionaElTipoSurtido).getText();
			if (SeleccionaElTipoSurtido.contains("tipo de surtido") == false) {
				testCase.addTextEvidenceCurrentStep("No se muestra el texto de manera correcta.");
			}

			boolean Preventa = au.driver.findElement(bt_Preventa).isEnabled();
			if (Preventa == false) {
				testCase.addTextEvidenceCurrentStep("No se muestra el botón de manera correcta.");
			}

			boolean SurtidoTradicional = au.driver.findElement(bt_SurtidoTradicional).isEnabled();
			if (SurtidoTradicional == false) {
				testCase.addTextEvidenceCurrentStep("No se muestra el botón de manera correcta.");
			}

			boolean PedidoPrecargado = au.driver.findElement(bt_PedidoPrecargado).isEnabled();
			if (PedidoPrecargado == false) {
				testCase.addTextEvidenceCurrentStep("No se muestra el botón de manera correcta.");
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep(
					"La pantalla principal de la sección Capacitación de Visita Guiada no se muestra de manera correcta.");
			return false;
		}

	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validación de pantalla Surtido Tradicional (Venta a Bordo).
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean pantallaSurtidoTradicional() {
		try {

			boolean logOxxo = au.driver.findElement(logoOxxo).isEnabled();
			if (logOxxo == false) {
				testCase.addTextEvidenceCurrentStep("El Logotipo de Oxxo no se muestra de manera correcta.");
			}

			boolean porCapturaManual = au.driver.findElement(bt_porCapturaManual).isEnabled();
			if (porCapturaManual == false) {
				testCase.addTextEvidenceCurrentStep("No se muestra el botón de manera correcta.");
			}

			boolean porHerramientaDeCompra = au.driver.findElement(bt_porHerramientaDeCompra).isEnabled();
			if (porHerramientaDeCompra == false) {
				testCase.addTextEvidenceCurrentStep("No se muestra el botón de manera correcta.");
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep(
					"La pantalla principal de Surtido Tradicional no se muestra de manera correcta.");
			return false;
		}

	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validación de Mensaje en pantalla de Visita Guiada, el mensaje debe de estar
	 * fijo durante todo el flujo.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean mensajeProcesoDeVisitaGuiada() {
		try {

			String textoSuperiorVisitaGuiada = au.driver.findElement(lb_textoSuperiorVisitaGuiada).getText();
			if (textoSuperiorVisitaGuiada
					.contains("Estás realizando un proceso en la visita guiada... Ver más") == false) {
				testCase.addTextEvidenceCurrentStep("No se muestra el texto de manera correcta.");
			}

			boolean SalirVisitaGuiada = au.driver.findElement(bt_SalirVisitaGuiada).isEnabled();
			if (SalirVisitaGuiada == false) {
				testCase.addTextEvidenceCurrentStep("El botón no se muestra de manera correcta.");
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep(
					"El mensaje que se encuentra en el flujo de Visita Guiada no se muestra de manera correcta.");
			return false;
		}

	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validación de la ausencia de la pantalla principal de Capacitación Visita
	 * Guiada.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean ausenciaPantallaCapacitacionVisitaGuiada() {
		System.out.println("Esperando...");
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(bt_Preventa));
		try {
			boolean logOxxo = au.driver.findElement(logoOxxo).isDisplayed();
			System.out.println("logo: "+logOxxo);
			if (logOxxo == true) {
				return false;
			}
			boolean Preventa = au.driver.findElement(bt_Preventa).isDisplayed();
			System.out.println("preventa: "+Preventa);
			if (Preventa == true) {
				return false;
			}
			boolean SurtidoTradicional = au.driver.findElement(bt_SurtidoTradicional).isDisplayed();
			System.out.println("SurtidoTradicional: "+SurtidoTradicional);
			if (SurtidoTradicional == true) {
				return false;
			}
			boolean PedidoPrecargado = au.driver.findElement(bt_PedidoPrecargado).isDisplayed();
			System.out.println("PedidoPrecargado: "+PedidoPrecargado);
			if (PedidoPrecargado == true) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // La pantalla está presente, por lo tanto no hay ausencia
		} catch (Exception e) {
			return false; // La pantalla está ausente
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validación de la ausencia de la pantalla Visita Guiada Surtido Tradicional
	 * Por Captura Manual.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean ausenciaPantallaVisitaGuiadaSurtidoTradicionalPorCapturaManual() {
		try {
			boolean textoSuperiorVisitaGuiada = au.driver.findElement(lb_textoSuperiorVisitaGuiada).isDisplayed();
			if (textoSuperiorVisitaGuiada == true) {
				return false;
			}

			boolean textoResaltadoGenerarPedidoyEntregaTradicional = au.driver
					.findElement(lb_textoResaltadoGenerarPedidoyEntregaTradicional).isDisplayed();
			if (textoResaltadoGenerarPedidoyEntregaTradicional == true) {
				return false;
			}
			boolean SalirVisitaGuiada = au.driver.findElement(bt_SalirVisitaGuiada).isDisplayed();
			if (SalirVisitaGuiada == true) {
				return false;
			}
			return false; // La pantalla está presente, por lo tanto no hay ausencia
		} catch (Exception e) {
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // La pantalla está ausente
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validación de que la pantalla Visita Guiada Surtido Tradicional Por Captura
	 * Manual esta activa.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean existenciaPantallaVisitaGuiadaSurtidoTradicionalPorCapturaManual() {
		try {
			boolean textoSuperiorVisitaGuiada = au.driver.findElement(lb_textoSuperiorVisitaGuiada).isDisplayed();
			if (textoSuperiorVisitaGuiada == false) {
				return false;
			}
			boolean textoResaltadoGenerarPedidoyEntregaTradicional = au.driver
					.findElement(lb_textoResaltadoGenerarPedidoyEntregaTradicional).isDisplayed();
			if (textoResaltadoGenerarPedidoyEntregaTradicional == false) {
				return false;
			}
			boolean SalirVisitaGuiada = au.driver.findElement(bt_SalirVisitaGuiada).isDisplayed();
			if (SalirVisitaGuiada == false) {
				return false;
			}
			return true; // La pantalla está presente, por lo tanto no hay ausencia
		} catch (Exception e) {
			testCase.addScreenShotCurrentStep(au, "testImage");
			return false; // La pantalla está ausente
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validar Mensaje de Warning ¿Estás seguro que deseas salir de la Visita
	 * Guiada?
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean mensajeSalirDeVisitaGuiada() {
		try {

			au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
			String mensajeMostrado = au.driver.findElement(Warnings).getText();
			String txtWarning = "¿Estás seguro que deseas salir de la Visita Guiada?";

			String btSi = au.driver.findElement(bt_Si).getText();
			String Si = "SI";

			String btNo = au.driver.findElement(bt_No).getText();
			String No = "NO";

			if (mensajeMostrado.equals(txtWarning)) {
			} else {
				testCase.addTextEvidenceCurrentStep("Se muestra mensaje de manera incorrecta");
			}

			if (btSi.equals(Si)) {
			} else {
				testCase.addTextEvidenceCurrentStep("Se muestra mensaje de manera incorrecta");
			}

			if (btNo.equals(No)) {
			} else {
				testCase.addTextEvidenceCurrentStep("Se muestra mensaje de manera incorrecta");
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("El mensaje de Warning no se muestra de manera correcta.");
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validar Mensaje de Warning "Al regresar al menú inicial se perderán los datos
	 * ya capturados".
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean mensajeRegresarMenuVisitaGuiada() {
		try {

			au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
			String mensajeMostrado = au.driver.findElement(Warnings).getText();
			String txtWarning = "Al regresar al menú inicial se perderán los datos ya capturados";

			String btSi = au.driver.findElement(bt_Si).getText();
			String Si = "SI";

			String btNo = au.driver.findElement(bt_No).getText();
			String No = "NO";

			if (mensajeMostrado.contains(txtWarning)) {
			} else {
				testCase.addTextEvidenceCurrentStep("Se muestra mensaje de manera incorrecta");
			}

			if (btSi.equals(Si)) {
			} else {
				testCase.addTextEvidenceCurrentStep("Se muestra mensaje de manera incorrecta");
			}

			if (btNo.equals(No)) {
			} else {
				testCase.addTextEvidenceCurrentStep("Se muestra mensaje de manera incorrecta");
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("El mensaje de Warning no se muestra de manera correcta.");
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar Botón “Escanear QR de pedido o QR de Hta. de compra”.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean boton_EscanearQrHC() {
		try {
			au.click(rb_EscanearQRPedidoQRdeHC);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	public boolean boton_EscanearQrHCSE() {
		try {
			au.click(rb_EscanearQRPedidoQRdeHC);
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar Botón “Generar Pedido y Entrega (Tradicional)”.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean boton_GenPedidoEntrega() {
		try {
			au.click(rb_GenerarPedidoyEntregaTradicional);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar Botón Detalle de Ventas del primer Sku en pantalla.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean boton_DetalleVentas1Sku() {
		try {
			au.click(By.xpath(
					"(//android.widget.Button[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/btnDetail\"])[1]"));
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar Botón “CONTINUAR” en la pantalla de generación de pedido.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean boton_ContinuarGeneracionPedido() {
		try {
			au.scrollToElement(btnContinuar);
			au.click(btnContinuar);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	public boolean boton_ContinuarGeneracionPedidoSE() {
		try {
			au.scrollToElement(btnContinuar);
			au.click(btnContinuar);
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar Botón “CONTINUAR” en la pantalla de generación de pedido de un
	 * pedido Repartidor HC.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean boton_ContinuarGeneracionPedidoHCTradicional() {
		try {
			au.scrollToElement(btnContinuarPHC);
			au.click(btnContinuarPHC);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar todos los articulos ingresados en el pedido.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean checkAllSkus() {

		try {
			au.driver.findElement(AppiumBy.androidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"Resumen de artículos a descargar del camión\"))"));

			au.click(radiobtnChecarTodosArticulos);
			au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validar que se deseleccionaron los articulos.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean validarSkusUnCheck() {
		try {
			au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

			String xpathRb = "//android.widget.CheckBox[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/chk_entregado\"]";
			boolean todosNoSeleccionados = true;

			for (int i = 1; i <= 3; i++) {
				String xpathCompleto = "(" + xpathRb + ")[" + i + "]";
				String rbSku = au.driver.findElement(By.xpath(xpathCompleto)).getAttribute("checked");

				if (rbSku.equalsIgnoreCase("true")) {
					todosNoSeleccionados = false;
					break;
				}
			}

			if (todosNoSeleccionados) {
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validar que se seleccionaron los articulos.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean validarSkusCheck() {
		try {
			au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

			String xpathRb = "//android.widget.CheckBox[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/chk_entregado\"]";
			boolean todosSeleccionados = true;

			for (int i = 1; i <= 3; i++) {
				String xpathCompleto = "(" + xpathRb + ")[" + i + "]";
				String rbSku = au.driver.findElement(By.xpath(xpathCompleto)).getAttribute("checked");

				if (!rbSku.equalsIgnoreCase("true")) {
					todosSeleccionados = false;
					break;
				}
			}

			if (todosSeleccionados) {
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validar pantalla resumen con articulos sin seleccionar.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean pantallaResumenUnCheck() {
		try {
			String xpathRb = "(//android.widget.CheckBox[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/chk_entregado\"])";
			for (int i = 1; i <= 3; i++) {
				String xpathCompleto = xpathRb + "[" + i + "]";
				boolean rbSku = au.driver.findElement(By.xpath(xpathCompleto)).isSelected();
				if (rbSku != false) {
					return false;
				}
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.scrollToElement(btnModificarPedido);

			String ttlPiezas = au.driver.findElement(NoArticulosCap).getText();
			if (!ttlPiezas.equals(String.valueOf(listaSkus.size()))) {
				return false;
			}
			String ttlArticulos = au.driver.findElement(cantCapturada).getText();
			if (!ttlArticulos.equals(String.valueOf(cont))) {
				return false;
			}
			String ttlArticulosEntreg = au.driver.findElement(NoArticulosEntre).getText();
			if (ttlArticulosEntreg.equals(String.valueOf(listaSkus.size()))) {
				return false;
			}
			String ttlCantEntrega = au.driver.findElement(cantEntregada).getText();
			if (ttlCantEntrega.equals(String.valueOf(cont))) {
				return false;
			}

			au.scrollToElement(btnGenerarQR);
			String op = au.driver.findElement(btnGenerarQR).getAttribute("enabled");
			if (op.contentEquals("false")) {
				testCase.addScreenShotCurrentStep(au, "testImage");

			} else {
				return false;

			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Selección del primer articulo del listado.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean checkFirstSku() {

		try {

			au.driver.findElement(AppiumBy.androidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"Resumen de artículos a descargar del camión\"))"));

			au.click(By.xpath(
					"(//android.widget.CheckBox[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/chk_entregado\"])[1]"));
			au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validar pantalla resumen las cantidades en especifico con un checkbox
	 * seleccionado.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean validarCantidades1Checkbox() {

		try {
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.scrollToElement(btnModificarPedido);

			String ttlPiezas = au.driver.findElement(NoArticulosCap).getText();
			System.out.println(ttlPiezas);
			if (!ttlPiezas.equals(String.valueOf(listaSkus.size()))) {
				return false;
			}

			System.out.println("art capturados");

			String ttlArticulos = au.driver.findElement(cantCapturada).getText();
			System.out.println(ttlArticulos);
			if (!ttlArticulos.equals(String.valueOf(cont))) {
				return false;
			}

			System.out.println("art entregados");
			String ttlArticulosEntreg = au.driver.findElement(NoArticulosEntre).getText();
			System.out.println(ttlArticulosEntreg);
			if (!ttlArticulosEntreg.equals("1")) {
				return false;
			}

			System.out.println("art entregados");
			String ttlCantEntrega = au.driver.findElement(cantEntregada).getText();
			String cantArticulo = au.driver.findElement(cantidadArticulo).getText();
			System.out.println(ttlCantEntrega);
			System.out.println(cantArticulo);
			if (!ttlCantEntrega.equals(cantArticulo)) {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");

			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validar pantalla resumen las cantidades en especifico todos 3 checkbox.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean validarCantidades3Checkbox() {

		try {

			String cantArticulo = au.driver.findElement(By.xpath(
					"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/tvw_cantidad\"])[2]"))
					.getText();
			String cantArticulo1 = au.driver.findElement(By.xpath(
					"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/tvw_cantidad\"])[3]"))
					.getText();
			String cantArticulo2 = au.driver.findElement(By.xpath(
					"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/tvw_cantidad\"])[4]"))
					.getText();

			int cantart1 = Integer.parseInt(cantArticulo);
			int cantart2 = Integer.parseInt(cantArticulo1);
			int cantart3 = Integer.parseInt(cantArticulo2);

			System.out.println(cont);
			int ttlCantidad = cantart1 + cantart2 + cantart3;
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.scrollToElement(btnModificarPedido);

			String ttlPiezas = au.driver.findElement(NoArticulosCap).getText();
			if (!ttlPiezas.equals(String.valueOf(listaSkus.size()))) {
				return false;
			}

			String ttlArticulos = au.driver.findElement(cantCapturada).getText();
			if (!ttlArticulos.equals(String.valueOf(cont))) {
				return false;
			}

			String ttlArticulosEntreg = au.driver.findElement(NoArticulosEntre).getText();
			if (!ttlArticulosEntreg.equals("3")) {
				return false;
			}

			String ttlCantEntrega = au.driver.findElement(cantEntregada).getText();
			if (!ttlCantEntrega.equals(String.valueOf(ttlCantidad))) {
				return false;
			}

			au.scrollToElement(btnGenerarQR);
			String op = au.driver.findElement(btnGenerarQR).getAttribute("enabled");
			if (op.contentEquals("true")) {
				testCase.addScreenShotCurrentStep(au, "testImage");
			} else {
				return false;

			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validar pantalla resumen las cantidades en especifico todos 3 checkbox.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean validarCantidades3CheckboxDePedidoTradicionalHC() {

		try {

			String cantArticulo = au.driver.findElement(By.xpath(
					"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/tvw_cantidad\"])[2]"))
					.getText();
			String cantArticulo1 = au.driver.findElement(By.xpath(
					"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/tvw_cantidad\"])[3]"))
					.getText();
			String cantArticulo2 = au.driver.findElement(By.xpath(
					"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/tvw_cantidad\"])[4]"))
					.getText();

			int cantart1 = Integer.parseInt(cantArticulo);
			int cantart2 = Integer.parseInt(cantArticulo1);
			int cantart3 = Integer.parseInt(cantArticulo2);

			System.out.println(cont);
			System.out.println(cont2);
			int ttlCantidad = cantart1 + cantart2 + cantart3;
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.scrollToElement(btnModificarPedido);

			String ttlPiezas = au.driver.findElement(NoArticulosCap).getText();
			if (!ttlPiezas.equals(String.valueOf(cont2))) {
				return false;
			}

			String ttlArticulos = au.driver.findElement(cantCapturada).getText();
			if (!ttlArticulos.equals(String.valueOf(cont))) {
				return false;
			}

			String ttlArticulosEntreg = au.driver.findElement(NoArticulosEntre).getText();
			if (!ttlArticulosEntreg.equals("3")) {
				return false;
			}

			String ttlCantEntrega = au.driver.findElement(cantEntregada).getText();

			if (!ttlCantEntrega.equals(String.valueOf(ttlCantidad))) {
				return false;
			}

			au.scrollToElement(btnGenerarQR);
			String op = au.driver.findElement(btnGenerarQR).getAttribute("enabled");
			if (op.contentEquals("true")) {
				testCase.addScreenShotCurrentStep(au, "testImage");

			} else {
				return false;

			}

			;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar opción Generar QR en pantalla generación de pedido.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean selecGenerarQR() {
		try {
			au.scrollToElement(btnGenerarQR);
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.click(btnGenerarQR);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar opción Modifcar QR en pantalla generación de pedido.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean selecModificarQR() {
		try {
			au.scrollToElement(bt_ModificarQR);
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.click(bt_ModificarQR);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validar que se genera el código QR.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean validarGenQR() {
		try {

			au.driver.findElement(AppiumBy.androidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"El CÓDIGO QR ESTARÁ DISPONIBLE\"))"));
			String TDisponiblePor = au.driver.findElement(lblCodigoDisponible).getText();
			if (TDisponiblePor.contains("El CÓDIGO QR ESTARÁ DISPONIBLE") != true) {
				return false;
			}
			QrGenerado = au.driver.findElement(editQrGenerado).getText();
			System.out.println(QrGenerado);
			if (QrGenerado == "") {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.scrollToElement(bt_ModificarQR);
			boolean terminar = au.driver.findElement(bt_Terminar).isEnabled();
			if (terminar == false) {
				return false;
			}
			boolean modificar = au.driver.findElement(bt_ModificarQR).isEnabled();
			if (modificar == false) {
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Quitar la selección a todos los articulos ingresados en el pedido.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public void UncheckAllSkus() {

		au.driver.findElement(AppiumBy.androidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"Resumen de artículos a descargar del camión\"))"));

		au.click(radiobtnChecarTodosArticulos);
		au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		testCase.addScreenShotCurrentStep(au, "testImage");

	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validar pantalla Resumen de Articulos a Descargar por Camión con Generar QR.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean pantallaResumenArticulosADescargar2() {
		try {
			String tlResumen = au.driver.findElement(lbResumenArticulos).getText();
			if (!tlResumen.equals("Resumen de artículos a descargar del camión")) {
				return false;
			}

			/*
			 * Validación de los registros de Skus.
			 */

			String xpathBase = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/rvw_productos\"]/android.view.ViewGroup";
			for (int i = 1; i <= 3; i++) {
				String xpathCompleto = xpathBase + "[" + i + "]";
				System.out.println(xpathCompleto);
				boolean lstSku = au.driver.findElement(By.xpath(xpathCompleto)).isDisplayed();
				if (lstSku != true) {
					return false;
				}
			}

			/*
			 * Validación de los radio buttons de los productos.
			 */

			String xpathRb = "(//android.widget.CheckBox[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/chk_entregado\"])";
			for (int i = 1; i <= 3; i++) {
				String xpathCompleto = xpathRb + "[" + i + "]";
				System.out.println(xpathCompleto);
				boolean rbSku = au.driver.findElement(By.xpath(xpathCompleto)).isDisplayed();
				if (rbSku != true) {
					return false;
				}
			}

			/*
			 * Validación del radio button para seleccionar todos los productos.
			 */

			boolean checkAll = au.driver.findElement(radiobtnChecarTodosArticulos).isDisplayed();
			if (checkAll != true) {
				return false;
			}

			/*
			 * Validación de que se encuentran visibles los campos Articulo, Descripción y
			 * Cant.
			 */

			boolean articulo = au.driver.findElement(tbArticulo).isDisplayed();
			boolean descripcion = au.driver.findElement(tbDescripcion).isDisplayed();
			boolean cant = au.driver.findElement(tbCant).isDisplayed();
			if ((articulo != true) || (descripcion != true) || (cant != true)) {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			au.scrollToElement(btnModificarPedido);

			/*
			 * Validar: Campo No. De Artículos capturados Campo No. De artículos entregados
			 * Cantidad capturada Cantidad entregada Botón Modificar pedido Botón Generar QR
			 * (deshabilitado)
			 */

			boolean NoArticulosCa = au.driver.findElement(NoArticulosCap).isDisplayed();
			boolean NoArticulosEntr = au.driver.findElement(NoArticulosEntre).isDisplayed();
			boolean cantCapturad = au.driver.findElement(cantCapturada).isDisplayed();
			boolean cantEntregad = au.driver.findElement(cantEntregada).isDisplayed();
			if ((NoArticulosCa != true) || (NoArticulosEntr != true) || (cantCapturad != true)
					|| (cantEntregad != true)) {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			au.scrollToElement(btnGenerarQR);

			boolean modificarPedido = au.driver.findElement(btnModificarPedido).isDisplayed();
			if (modificarPedido != true) {
				return false;
			}
			boolean btGenerarQR = au.driver.findElement(btnGenerarQR).isEnabled();
			if (btGenerarQR != true) {
				return false;
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validar pantalla Resumen de Articulos a Descargar por Camión con Generar QR
	 * Deshabilitado.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean pantallaResumenArticulosADescargar() {
		try {
			String tlResumen = au.driver.findElement(lbResumenArticulos).getText();
			if (!tlResumen.equals("Resumen de artículos a descargar del camión")) {
				return false;
			}

			/*
			 * Validación de los registros de Skus.
			 */

			String xpathBase = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/rvw_productos\"]/android.view.ViewGroup";
			for (int i = 1; i <= 3; i++) {
				String xpathCompleto = xpathBase + "[" + i + "]";
				System.out.println(xpathCompleto);
				boolean lstSku = au.driver.findElement(By.xpath(xpathCompleto)).isDisplayed();
				if (lstSku != true) {
					return false;
				}
			}

			/*
			 * Validación de los radio buttons de los productos.
			 */

			String xpathRb = "(//android.widget.CheckBox[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/chk_entregado\"])";
			for (int i = 1; i <= 3; i++) {
				String xpathCompleto = xpathRb + "[" + i + "]";
				System.out.println(xpathCompleto);
				boolean rbSku = au.driver.findElement(By.xpath(xpathCompleto)).isDisplayed();
				if (rbSku != true) {
					return false;
				}
			}

			/*
			 * Validación del radio button para seleccionar todos los productos.
			 */

			boolean checkAll = au.driver.findElement(radiobtnChecarTodosArticulos).isDisplayed();
			if (checkAll != true) {
				return false;
			}

			/*
			 * Validación de que se encuentran visibles los campos Articulo, Descripción y
			 * Cant.
			 */

			boolean articulo = au.driver.findElement(tbArticulo).isDisplayed();
			boolean descripcion = au.driver.findElement(tbDescripcion).isDisplayed();
			boolean cant = au.driver.findElement(tbCant).isDisplayed();
			if ((articulo != true) || (descripcion != true) || (cant != true)) {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			au.scrollToElement(btnModificarPedido);

			/*
			 * Validar: Campo No. De Artículos capturados Campo No. De artículos entregados
			 * Cantidad capturada Cantidad entregada Botón Modificar pedido Botón Generar QR
			 * (deshabilitado)
			 */

			boolean NoArticulosCa = au.driver.findElement(NoArticulosCap).isDisplayed();
			boolean NoArticulosEntr = au.driver.findElement(NoArticulosEntre).isDisplayed();
			boolean cantCapturad = au.driver.findElement(cantCapturada).isDisplayed();
			boolean cantEntregad = au.driver.findElement(cantEntregada).isDisplayed();
			if ((NoArticulosCa != true) || (NoArticulosEntr != true) || (cantCapturad != true)
					|| (cantEntregad != true)) {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			au.scrollToElement(btnGenerarQR);

			boolean modificarPedido = au.driver.findElement(btnModificarPedido).isDisplayed();
			if (modificarPedido != true) {
				return false;
			}
			boolean btGenerarQR = au.driver.findElement(btnGenerarQR).isEnabled();
			if (btGenerarQR != false) {
				return false;
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validar y aceptar mensaje de Terminar Escaneo.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean msgTerminarEscaneo() {
		try {
			String displayedWarning = au.driver.findElement(Warnings).getText();
			String txtWarning = "¿Está seguro que desea terminar el escaneo de productos?";
			if (displayedWarning.equals(txtWarning)) {
			} else {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.click(bt_Si);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validar y aceptar mensaje de ¿Está seguro de regresar a editar el pedido?.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean msgEditarPedido() {
		try {
			String displayedWarning = au.driver.findElement(Warnings).getText();
			String txtWarning = "¿Está seguro de regresar a editar el pedido?";
			if (displayedWarning.equals(txtWarning)) {
			} else {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.click(bt_Si);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validar mensaeje "Faltan 2 artículos por seleccionarse del pedido original.
	 * En caso de continuar, estos artículos no serán considerados dentro del
	 * pedido. ¿Desea continuar?"
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean msgFaltan2Articulos() {
		try {
			String displayedWarning = au.driver.findElement(Warnings).getText();
			String txtWarning = "Faltan 2 artículos por seleccionarse del pedido original.";
			if (displayedWarning.contains(txtWarning)) {
			} else {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Ingresar Fuente de Abasto válida del punto de venta.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean ingresar_FaValidaPV() {
		try {
			au.driver.findElement(tb_FuentesDeAbasto).sendKeys(faValida);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Ingresar los Skus obtenidos del punto de venta.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean ingresar_Skus() {
		try {
			for (int i = 0; i < listaSkus.size(); i++) {
				au.driver.findElement(tb_CodigoDeBarras).sendKeys(listaSkus.get(i));
				au.click(bt_IngresarSku);
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validar Sku´s ingresados en la generación de pedido.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean ValidarSkusIngresados() {

		try {

			au.scrollToElement(lb_NoArticulos);

			String xpathBase = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/productPageListRecycler\"]/android.view.ViewGroup";
			String xpathSku = "//android.widget.EditText[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/skuNumberField\" and @index=\"1\"]";

			List<String> skusIngresados = new ArrayList<>();

			for (int i = 1; i <= 3; i++) {
				String xpathCompleto = xpathBase + "[" + i + "]" + xpathSku;

				System.out.println(xpathCompleto);
				String skuActual = au.driver.findElement(By.xpath(xpathCompleto)).getText();
				skusIngresados.add(skuActual);

			}

			for (int i = 0; i < 3; i++) {

				Collections.reverse(skusIngresados);
				String skuIngresado = skusIngresados.get(i);
				String skuEsperado = listaSkus.get(i);
				System.out.println(skuIngresado);
				System.out.println(skuEsperado);
				if (skuIngresado.equals(skuEsperado)) {
				} else {
					testCase.addTextEvidenceCurrentStep("No se muestra Sku de manera correcta");
				}
			}

			String XpathPadre = "(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/removeProductButton\"])";
			String XpathPadre1 = "(//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/numberProductText\"])";
			String XpathPadre2 = "(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/addProductButton\"])";

			for (int i = 1; i <= 2; i++) {
				String XpDesc1 = XpathPadre + "[" + i + "]";
				String XpDesc2 = XpathPadre1 + "[" + i + "]";
				String XpDesc3 = XpathPadre2 + "[" + i + "]";

				boolean p1 = au.driver.findElement(By.xpath(XpDesc1)).isDisplayed();
				boolean p2 = au.driver.findElement(By.xpath(XpDesc2)).isDisplayed();
				boolean p3 = au.driver.findElement(By.xpath(XpDesc3)).isDisplayed();

				if ((p1 != true) || (p2 != true) || (p3 != true)) {
					return false;
				}

			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/********************************************************************************************************************************
	 * Método - incrementarCantidades - incrementa las cantidad 3 la cantidad de los
	 * acticulos
	 * 
	 * @param N/A
	 * @return N/A
	 * 
	 * @author Alex Aquino
	 */
	public boolean incrementarCantidades() {
		au.scrollToElement(lb_NoArticulos);
		try {
			au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
			for (int i = 1; i <= 3; i++) {
				for (int j = 1; j <= 3; j++) {
					String p = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/productPageListRecycler\"]/android.view.ViewGroup"
							+ "[" + i + "]";
					By XpathDesc = By.xpath(p
							+ "//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/addProductButton\" and @index]");
					WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(10));
					// WebElement padre =
					// wait.until(ExpectedConditions.presenceOfElementLocated(By.className("android.view.ViewGroup")));
					WebElement sumar = wait.until(ExpectedConditions.visibilityOfElementLocated(XpathDesc));
					sumar.click();
					cont++;
				}
			}
			cont = cont + listaSkus.size();
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/********************************************************************************************************************************
	 * Método - incrementarCantidades - incrementa las cantidad 2 la cantidad de 2
	 * acticulos
	 * 
	 * @param N/A
	 * @return N/A
	 * 
	 * @author Alex Aquino
	 */
	public boolean incrementarCantidades2() {
		au.scrollToElement(lb_NoArticulos);
		try {
			au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
			for (int i = 1; i <= 2; i++) {
				for (int j = 1; j <= 3; j++) {
					String p = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/productPageListRecycler\"]/android.view.ViewGroup"
							+ "[" + i + "]";
					By XpathDesc = By.xpath(p
							+ "//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/addProductButton\" and @index]");
					WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(10));
					// WebElement padre =
					// wait.until(ExpectedConditions.presenceOfElementLocated(By.className("android.view.ViewGroup")));
					WebElement sumar = wait.until(ExpectedConditions.visibilityOfElementLocated(XpathDesc));
					sumar.click();
					cont++;
				}
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/********************************************************************************************************************************
	 * Método - incrementarCantidades - incrementa las cantidad 3 la cantidad de los
	 * acticulos de un pedido Tradicional de HC
	 * 
	 * @param N/A
	 * @return N/A
	 * 
	 * @author Alex Aquino
	 */
	public boolean incrementarCantidadesTradicionalHC() {

		try {
			swipeCoord(200, 660, 198, 402, 3000);
			au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
			for (int i = 1; i <= 3; i++) {
				for (int j = 1; j <= 3; j++) {
					String p = "//android.view.ViewGroup[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/containerItemProducts\"]"
							+ "[" + i + "]";

					By XpathDesc = By.xpath(p
							+ "//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/btn_sumar\"]");

					WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(10));
					WebElement sumar = wait.until(ExpectedConditions.visibilityOfElementLocated(XpathDesc));
					sumar.click();
					cont++;
				}
				cont2++;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/********************************************************************************************************************************
	 * Método - skus_postIncremento - Valida los Skus, sus botones y la suma de las
	 * cantidades.
	 * 
	 * @param N/A
	 * @return N/A
	 * 
	 * @author Alejandro Valdez
	 */
	public boolean skus_postIncremento() {

		try {

			swipeCoord(200, 575, 198, 402, 3000);

			au.scrollToElement(lb_NoArticulos);

			String xpathBase = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/productPageListRecycler\"]/android.view.ViewGroup";
			String xpathSku = "//android.widget.EditText[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/skuNumberField\" and @index=\"1\"]";

			List<String> skusIngresados = new ArrayList<>();

			for (int i = 1; i <= 3; i++) {
				String xpathCompleto = xpathBase + "[" + i + "]" + xpathSku;

				System.out.println(xpathCompleto);
				String skuActual = au.driver.findElement(By.xpath(xpathCompleto)).getText();
				skusIngresados.add(skuActual);

			}

			for (int i = 0; i < 3; i++) {
				Collections.reverse(skusIngresados);
				String skuIngresado = skusIngresados.get(i);
				String skuEsperado = listaSkus.get(i);
				if (skuIngresado.equals(skuEsperado)) {
				} else {
					testCase.addTextEvidenceCurrentStep("No se muestra Sku de manera correcta");
				}
			}

			String XpathPadre = "(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/removeProductButton\"])";
			String XpathPadre1 = "(//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/numberProductText\"])";
			String XpathPadre2 = "(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/addProductButton\"])";
			String XpathPadre3 = "(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/minusProductButton\"])";

			for (int i = 1; i <= 2; i++) {
				String XpDesc1 = XpathPadre + "[" + i + "]";
				String XpDesc2 = XpathPadre1 + "[" + i + "]";
				String XpDesc3 = XpathPadre2 + "[" + i + "]";
				String XpDesc4 = XpathPadre3 + "[" + i + "]";

				boolean p1 = au.driver.findElement(By.xpath(XpDesc1)).isDisplayed();
				boolean p2 = au.driver.findElement(By.xpath(XpDesc2)).isDisplayed();
				boolean p3 = au.driver.findElement(By.xpath(XpDesc3)).isDisplayed();
				boolean p4 = au.driver.findElement(By.xpath(XpDesc4)).isDisplayed();

				if ((p1 != true) || (p2 != true) || (p3 != true) || (p4 != true)) {
					return false;
				}

			}

			String totalPiezas = au.driver.findElement(totl_Piezas).getText();
			if (!totalPiezas.equals(String.valueOf(cont))) {

				testCase.addTextEvidenceCurrentStep("El total de piezas no coincide con las ingresadas.");
			}
			String totalArticulos = au.driver.findElement(totl_Articulos).getText();
			if (!totalArticulos.equals(String.valueOf(listaSkus.size()))) {
				testCase.addTextEvidenceCurrentStep("El total de articulos no coincide con los ingresados.");
			}

			return true;

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/******************************************************************************************/
	/*
	 * Método - swipeChido - permite el dezplamiento el pantalla
	 * 
	 * @param startX - punto de partida en x
	 * 
	 * @param startY - punto de partida en y
	 * 
	 * @param endX - punto final en x
	 * 
	 * @param endY - punto final en y
	 * 
	 * @param durationMs - duracion de la accion
	 * 
	 * @return N/A
	 * 
	 * @author Alex Aquino
	 */
	public void swipeCoord(int startX, int startY, int endX, int endY, int durationMs) {
		// Crear un PointerInput para los gestos de toque
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		Sequence swipe = new Sequence(finger, 1);

		// Añadir acciones a la secuencia
		swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
		swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		swipe.addAction(
				finger.createPointerMove(Duration.ofMillis(durationMs), PointerInput.Origin.viewport(), endX, endY));
		swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

		// Ejecutar la secuencia de acciones
		au.driver.perform(Arrays.asList(swipe));
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaOpcCumplioProCambFis - valida que la opcion: Generar Pedido y Entrega
	 * (Tradicional) esta seleccionada
	 * 
	 * @autor Alejandro Valdez
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaOpcCumplioProCambFis() {
		au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		String op = au.driver.findElement(rb_GenerarPedidoyEntregaTradicional).getAttribute("checked");
		if (op.contentEquals("true")) {
			testCase.addScreenShotCurrentStep(au, "tesTImage");
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaOpcCumplioProCambFis - valida que la opcion: Escanear QR de pedido o QR
	 * de Hta. de compra esta seleccionada
	 * 
	 * @autor Alejandro Valdez
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaOpcEscanearQRHC() {
		au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		String op = au.driver.findElement(rb_EscanearQRPedidoQRdeHC).getAttribute("checked");
		if (op.contentEquals("true")) {
			testCase.addScreenShotCurrentStep(au, "tesTImage");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar Botón “Comenzar proceso de Recepcion de mercancia”.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean opcionComenzarProcesoRepecionMercancia() {
		try {
			au.click(bt_ComenzarProcesoRecepcion);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	public boolean opcionComenzarProcesoRepecionMercanciaSE() {
		try {
			au.click(bt_ComenzarProcesoRecepcion);
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar Botón “Capacitación con Visita Guiada”.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean opcionCapacitacionVisitaGuiada() {
		try {
			au.click(bt_CapacitacionVisitaGuiada);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("El botón no se encuentra disponible");
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar Botón “Surtido Tradicional”.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean opcionSurtidoTradicional() {
		try {
			au.click(bt_SurtidoTradicional);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("El botón no se encuentra disponible");
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar Botón "SALIR" de Visita Guiada.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean opcionSalirDeVisitaGuiada() {
		try {
			au.click(bt_SalirVisitaGuiada);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("El botón no se encuentra disponible");
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar Botón "SI" de cualquier mensaje.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean opcionSiGeneral() {
		try {
			au.click(bt_Si);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso

			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar Botón "SI" sin evidencia.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean opcionSiGeneralSE() {
		try {
			if (au.driver.findElement(bt_Si).isDisplayed()) {
				au.click(bt_Si);
			}
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar Botón "No" de cualquier mensaje sin Evidencia.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean opcionNoSE() {
		try {
			au.click(bt_No);
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar Botón "No" de cualquier mensaje.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean opcionNo() {
		try {
			au.click(bt_No);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar Botón "Salir" de pantalla Generación de Pedido.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean opcionSalirGeneracionPedido() {
		try {

			au.scrollToElement(bt_SalirGeneracionPedido);
			au.click(bt_SalirGeneracionPedido);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar Botón "Continuar" de pantalla Inicial Recibo Agil.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean seleccionarContinuarRASE() {
		try {
			au.click(bt_ContinuarRA);
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar Botón "Comenzar" de pantalla Inicial Recibo Agil.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean seleccionarComenzarRA() {
		try {
			au.click(bt_ComenzarRA);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	// Sin evidencia
	public boolean seleccionarComenzarRASE() {
		try {
			au.scrollToElement(bt_ComenzarRA);
			au.click(bt_ComenzarRA);
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar Botón "Necesitas Ayuda" de pantalla Inicial de Ingreso de Código
	 * de Activacion en Recibo Agil.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean seleccionarNeedHelp() {
		try {
			au.click(bt_Help);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Verificar que se encuentra VACIO Campo de Código de Activación.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean validacionCampoVacioCodigoActivacion() {
		try {

			String camVacio = au.driver.findElement(edt_IngresarCodigo).getText();
			if (camVacio.isEmpty()) {
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Verificar que se encuentra LLENO Campo de Código de Activación.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean validacionCampoLlenoCodigoActivacion() {
		try {

			String camVacio = au.driver.findElement(edt_IngresarCodigo).getText();
			if (camVacio.isEmpty()) {
				return false;

			} else {
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			}

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Verificar que se encuentra LLENO Campo de Fuente de Abasto de Generación de
	 * Pedido.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean validacionCampoLlenoFA() {
		try {

			String camVacio = au.driver.findElement(tb_FuentesDeAbasto).getText();
			if (camVacio.isEmpty()) {
				return false;
			} else {

				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			}

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Ingresar NUMEROS en Campo de Código de Activación mediante el TECLADO.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean ingresarNumeros() {
		try {

			au.click(edt_IngresarCodigo);

			// Ingresar numeros del 1 - 0
			au.click(By.id("com.google.android.inputmethod.latin:id/key_pos_password_header_number_1"));

			au.click(By.id("com.google.android.inputmethod.latin:id/key_pos_password_header_number_2"));

			au.click(By.id("com.google.android.inputmethod.latin:id/key_pos_password_header_number_3"));

			au.click(By.id("com.google.android.inputmethod.latin:id/key_pos_password_header_number_4"));

			au.click(By.id("com.google.android.inputmethod.latin:id/key_pos_password_header_number_5"));

			au.click(By.id("com.google.android.inputmethod.latin:id/key_pos_password_header_number_6"));

			testCase.addScreenShotCurrentStep(au, "testImage");

			au.click(By.id("com.google.android.inputmethod.latin:id/key_pos_password_header_number_7"));

			au.click(By.id("com.google.android.inputmethod.latin:id/key_pos_password_header_number_8"));

			au.click(By.id("com.google.android.inputmethod.latin:id/key_pos_password_header_number_9"));

			au.click(By.id("com.google.android.inputmethod.latin:id/key_pos_password_header_number_0"));

			au.driver.hideKeyboard();

			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Ingresar caracteres especiales en Campo de Código de Activación mediante el
	 * TECLADO.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean ingresarCaracteresEspeciales() {
		try {

			au.click(edt_IngresarCodigo);

			// Abrir apartado de simbolos y caracteres en el teclado.
			au.click(By.id("com.google.android.inputmethod.latin:id/key_pos_switch_to_symbol"));

			// Ingresar caracteres especiales del teclado.
			// #
			au.click(By.id("com.google.android.inputmethod.latin:id/key_pos_1_1"));
			// $
			au.click(By.id("com.google.android.inputmethod.latin:id/key_pos_1_2"));
			// (
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.click(By.id("com.google.android.inputmethod.latin:id/key_pos_1_7"));
			// )
			au.click(By.id("com.google.android.inputmethod.latin:id/key_pos_1_8"));
			///
			au.click(By.id("com.google.android.inputmethod.latin:id/key_pos_1_9"));

			au.driver.hideKeyboard();

			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Ingresar Espacios en Campo de Código de Activación mediante el TECLADO.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean ingresarEspacios() {
		try {

			au.click(edt_IngresarCodigo);

			// Ingresar Espacios.

			au.click(By.id("com.google.android.inputmethod.latin:id/key_pos_space"));

			au.click(By.id("com.google.android.inputmethod.latin:id/key_pos_space"));

			testCase.addScreenShotCurrentStep(au, "testImage");
			au.click(By.id("com.google.android.inputmethod.latin:id/key_pos_space"));

			au.driver.hideKeyboard();

			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Ingresar emojis en Campo de Código de Activación mediante el TECLADO.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean ingresarEmojis() {
		try {

			au.click(edt_IngresarCodigo);

			// Abrir apartado de simbolos y caracteres en el teclado.
			au.click(By.id("com.google.android.inputmethod.latin:id/key_pos_switch_to_symbol"));

			// Ingresar emojis.

			// :
			au.click(By.id("com.google.android.inputmethod.latin:id/key_pos_2_4"));

			// (

			// :
			au.click(By.id("com.google.android.inputmethod.latin:id/key_pos_2_4"));

			testCase.addScreenShotCurrentStep(au, "testImage");
			au.click(By.id("com.google.android.inputmethod.latin:id/key_pos_1_7"));
			// )

			au.driver.hideKeyboard();

			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validar Mensaje de Warning "Archivo cargado correctamente"
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean mensajeArchivoCargado() {
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
			wait.until(ExpectedConditions.presenceOfElementLocated(msg_ArchivoCargado));
			String mensajeMostrado = au.driver.findElement(msg_ArchivoCargado).getText();
			String txtWarning = "Archivo cargado correctamente";
			System.out.println(mensajeMostrado.contentEquals(txtWarning));
			if (!mensajeMostrado.contentEquals(txtWarning)) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validar Mensaje de Warning "Formato de archivo no soportado."
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean mensajeFormatoNoSoportado() {
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
			wait.until(ExpectedConditions.presenceOfElementLocated(msg_ArchivoNoSoportado));
			String mensajeMostrado = au.driver.findElement(msg_ArchivoNoSoportado).getText();
			String txtWarning = "Formato de archivo no soportado.";

			if (mensajeMostrado.equals(txtWarning)) {
			} else {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validar Mensaje de Warning "Error al leer el archivo, ingresa un archivo
	 * válido"
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean mensajeErrorAlLeerElArchivo() {
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
			wait.until(ExpectedConditions.presenceOfElementLocated(msg_ArchivoErroneo));
			String mensajeMostrado = au.driver.findElement(msg_ArchivoErroneo).getText();
			String txtWarning = "Error al leer el archivo, ingresa un archivo válido";

			if (mensajeMostrado.equals(txtWarning)) {
			} else {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar Botón "Cargar Archivo" de pantalla Inicial Recibo Agil.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean seleccionarCargarArchivoRA() {
		try {
			au.click(bt_CargarArchivo);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	// Sin evidencia
	public boolean seleccionarCargarArchivoRASE() {
		try {
			au.click(bt_CargarArchivo);
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar el archivo encriptado pero vacio de contenido. Es necesario haber
	 * realizado el pre requisito de generar el archivo encriptado de manera manual.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean seleccionarArchivoVacioyEncriptado() {
		try {

			au.click(bt_DesplegarAlmacenamiento);
			au.click(bt_DescargasAlmacenamiento);
			au.click(archivo_vacio_encriptado);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar el archivo sin encriptar y valido para cargarlo. Es necesario
	 * haber realizado el pre requisito de generar el archivo encriptado de manera
	 * manual.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean seleccionarArchivoValidoSinEncriptar() {
		try {

			au.click(bt_DesplegarAlmacenamiento);
			au.click(bt_DescargasAlmacenamiento);
			au.click(archivo_valido_SinEncriptar);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar el archivo encriptado y valido para cargarlo.. Es necesario haber
	 * realizado el pre requisito de generar el archivo encriptado de manera manual.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean seleccionarArchivoValidoyEncriptado() {
		try {

			au.click(bt_DesplegarAlmacenamiento);
			au.click(bt_DescargasAlmacenamiento);
			au.click(archivo_valido_encriptado);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	// Sin evidencia
	public boolean seleccionarArchivoValidoSE() {
		try {
			au.click(archivo_valido_encriptado);
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar la opcion "Por Captura Manual Escaneando artículo por artículo".
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean opcionCapturaManualSurtidoTradicional() {
		try {
			au.click(bt_porCapturaManual);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("El botón no se encuentra disponible");
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar Botón BACK nativo del dispostivo.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean botonBACK() {
		try {

			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.BACK));
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("El botón no se encuentra disponible");
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar Botón <- de la pantalla Generación de Pedido.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean botonBACKGeneracionPedido() {
		try {

			au.click(bt_BackGeneracionPedido);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("El botón no se encuentra disponible");
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar Botón Multitareas nativo del dispostivo.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean botonMultitareas() {
		try {

			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.APP_SWITCH));
			au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar Botón HOME nativo del dispostivo.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean botonHOME() {
		try {
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.HOME));
			au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	public boolean botonHOMESE() {
		try {

			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.HOME));
			au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Flujo sin evidencia dedse Surtido Tradicional (Venta a bordo) de visita
	 * guiada hasta generación de QR.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean flujoSurtidoTradicionalVisitaGuiadaSinEvidencia() {
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
		try {
			au.click(bt_porCapturaManual);
			// Tiempo de espera de carga de pantalla.
			wait.until(ExpectedConditions.visibilityOfElementLocated(rb_generarPedYEntrega));
			au.click(rb_GenerarPedidoyEntregaAuto);
			au.click(rb_GenerarPedidoyEntregaAuto);
			wait.until(ExpectedConditions.presenceOfElementLocated(bt_FuenteDeAbastoAuto));
			au.click(bt_FuenteDeAbastoAuto);
			wait.until(ExpectedConditions.presenceOfElementLocated(bt_CodigoDeBarrasAuto));
			au.click(bt_CodigoDeBarrasAuto);
			wait.until(ExpectedConditions.presenceOfElementLocated(bt_IngresarSkuAuto));
			au.click(bt_IngresarSkuAuto);
			wait.until(ExpectedConditions.presenceOfElementLocated(bt_IngresarAumentarSkuAuto2));
			au.click(bt_IngresarAumentarSkuAuto2);
			wait.until(ExpectedConditions.presenceOfElementLocated(bt_IngresarAumentarSkuAuto2));
			au.click(bt_IngresarAumentarSkuAuto2);
			wait.until(ExpectedConditions.presenceOfElementLocated(bt_ContinuarAuto));
			au.click(bt_ContinuarAuto);
			wait.until(ExpectedConditions.presenceOfElementLocated(bt_SiMensajeEscaneoProductosAuto));
			au.click(bt_SiMensajeEscaneoProductosAuto);
			wait.until(ExpectedConditions.presenceOfElementLocated(rb_CheckboxSkusAuto));
			au.click(rb_CheckboxSkusAuto);
			wait.until(ExpectedConditions.presenceOfElementLocated(bt_GenerarQrAuto));
			au.click(bt_GenerarQrAuto);
			wait.until(ExpectedConditions.presenceOfElementLocated(contenedorQrVisitaGuiada));
			String QR = au.driver.findElement(contenedorQrVisitaGuiada).getText();
			QrGeneradoVisitaGuiada = QR;
			System.out.println(QrGeneradoVisitaGuiada);
			return true;
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("El botón no se encuentra disponible");
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Obtener fuente de Abasto Válida
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A Se le asigna un valor a la variable faValida.
	 */

	public boolean obtenerFuenteAbastoValida(){
		try {
		Connection conn = DriverManager.getConnection(GlobalVariables.urlPV10_212_Satelites,
				GlobalVariables.userDB10_212, GlobalVariables.passDB10_212);
		String query1 = "DROP TABLE IF EXISTS #FAvalida";
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(query1);
		String query2 = "CREATE TABLE #FAvalida ( " + "	[SUPPLIER_NUM] [varchar](10) NOT NULL, "
				+ "	[SUPPLIER_NAME] [varchar](100) NOT NULL, " + "	[STATUS] [bit] NOT NULL, "
				+ "	[Can_Create] [bit] NOT NULL, " + "	[Can_Receive] [bit] NOT NULL " + " )";
		stmt.executeUpdate(query2);
		String query3 = "INSERT INTO #FAvalida EXEC Satelites.hc.GET_SUPPLIER_LIST_MOB";
		stmt.executeUpdate(query3);
		String FaVa = "Select top 1 SUPPLIER_NUM from #FAvalida where SUPPLIER_NUM not LIKE '9999999%' order by NEWID()";
		SQLResult res = new SQLResult(conn, FaVa);
		faValida = res.getFirstData().toString();
		return true;
		}catch(Exception e) {
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Eliminar las ventas de un Sku.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A Se le asigna un valor a la variable faValida.
	 */

	public boolean eliminar_Ventas(){
		try {
		Connection conn = DriverManager.getConnection(GlobalVariables.urlPV10_212_XposStore,
				GlobalVariables.userDB10_212, GlobalVariables.passDB10_212);

		String query1 = "use XposStore DELETE FROM [XposStore].[dbo].[ITM_STCS_OX] where SKU = '" + SkuVenta
				+ "' AND CD_STCS_ID = 340";

		Statement stmt = conn.createStatement();
		stmt.executeUpdate(query1);
		return true;
		}catch(Exception e) {
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Realizar Ventas a un Sku dependiendo de la fecha que se ingrese.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A Se le asigna un valor a la variable faValida.
	 */

	public boolean ventas_Articulos(int año, int mes, int dia){
		try {
		Connection conn = DriverManager.getConnection(GlobalVariables.urlPV10_212_XposStore,
				GlobalVariables.userDB10_212, GlobalVariables.passDB10_212);

		LocalDate fechaInicial = LocalDate.of(año, mes, dia);
		// Obtener la fecha actual
		LocalDate fechaActualObtenida = LocalDate.now();

		// Calcular la cantidad de días entre las dos fechas
		long diasEntre = ChronoUnit.DAYS.between(fechaInicial, fechaActualObtenida);
		System.out.println(diasEntre);

			for (int i = 0; i < diasEntre; i++) {
	
				/*
				 * Obtener la fecha donde se aplicara la venta
				 */
	
				LocalDate fechaActual = fechaInicial.plusDays(i);
				String fechaFormateada3 = fechaActual.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				System.out.println(fechaFormateada3);
	
				/*
				 * Obtener el dia de la semana en base a que el Domingo es 1
				 */
	
				LocalDate DiaSem = fechaInicial.plusDays(i);
				DayOfWeek diaSemana = DiaSem.getDayOfWeek();
				// Convertir a formato donde Domingo = 1 y Sábado = 7
				int diaPersonalizado = (diaSemana.getValue() % 7) + 1;
				System.out.println(diaPersonalizado);
	
				LocalDate fecha = LocalDate.parse(fechaFormateada3);
				WeekFields weekFields = WeekFields.ISO;
				int semana = fecha.get(weekFields.weekOfWeekBasedYear());
	
				if (diaPersonalizado == 1) {
					semana = semana + 1;
				}
				System.out.println(semana);
	
				String query = "DECLARE @NextID INT; "
						+ "SELECT @NextID = MAX([ID_ITM_STCS]) + 1 FROM [XposStore].[dbo].[ITM_STCS_OX]; "
						+ "PRINT @NextID " + "INSERT INTO [dbo].[ITM_STCS_OX] " + "([ID_ITM_STCS], [DY_BSN] " + ",[YR] "
						+ ",[CW] " + ",[DY] " + ",[CD_STCS_ID] " + ",[SKU] " + ",[VL_STCS] " + ",[FL] " + ",[LCNT] "
						+ ",[ID_OPR_CRT] " + ",[TM_CRT] " + ",[ID_OPR_MDY] " + ",[TM_MDY]) " + "VALUES " + "(@NextID, "
						+ "'" + fechaFormateada3 + "' " // Fecha en la que quieres que se realice la venta
						+ ",'2024' " + "," + semana + " " // Cambiar por el dia --Domingo es el 1, Lunes 2,
						+ "," + diaPersonalizado + " " + ",340 " + ",'" + SkuVenta + "' " + ",2 " + ",1 " + ",1 " + ",0 "
						+ ",GETDATE() " + ",null " + ",null);";
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(query);
			}
		return true;
		}catch(Exception e) {
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Obtener el Sku para realizar la venta.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean obtener_Sku() {
		try {

			String xpathCompleto = "(//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/tvw_producto_sku\"])[1]";

			SkuVenta = au.driver.findElement(By.xpath(xpathCompleto)).getText();
			System.out.println(SkuVenta);

			return true;

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}

	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Obtener Sku´s relacionados con Fuente de Abasto Válida.
	 * 
	 * @author Alejandro Valdez
	 * @param cantidadSkus toma el valor de la cantidad de Skus que se requieren
	 *                     ingresar.
	 * @return N/A
	 */

	public boolean obtenerSkusRelacionadosFaValida(int cantidadSKus){
		try {
		Connection conn = DriverManager.getConnection(GlobalVariables.urlPV10_212_Satelites,
				GlobalVariables.userDB10_212, GlobalVariables.passDB10_212);
		String query1 = "DROP TABLE IF EXISTS  #ReturnTable";
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(query1);
		String query2 = "CREATE TABLE #ReturnTable ( " + "	[SKU] [varchar](60) NOT NULL, "
				+ "	[Nombre] [nvarchar](100) NOT NULL, " + "	[PrecioVenta] [decimal](16, 4) NOT NULL, "
				+ "	[PrecioCosto] [decimal](16, 4) NOT NULL, " + "	[InventarioOperativo] [decimal](16, 2) NOT NULL, "
				+ "	[InventarioTeorico] [decimal](16, 2) NOT NULL, " + "	[MA_EXH] [numeric](16, 4) NULL, "
				+ "	[Ma_Exh_Prm] [numeric](16, 4) NULL, " + "	[MA_EXH_INI] [date] NULL, "
				+ "	[MA_EXH_END] [date] NULL, " + "	[MA_STK_NEW] [numeric](16, 4) NULL, "
				+ "	[MA_NEW_INI] [date] NULL, " + "	[MA_NEW_END] [date] NULL, " + "	[MA_FS] [numeric](16, 4) NULL, "
				+ "	[MA_FS_PROM] [numeric](16, 4) NULL, " + "	[MA_FS_INI] [date] NULL, "
				+ "	[MA_FS_END] [date] NULL, " + "	[MA_IM] [numeric](16, 4) NULL, " + "	[HFP] [varchar](5) NULL, "
				+ "	[SUMA_HCU] [decimal](38, 2) NULL, " + "	[DIAS_HCU] [int] NULL, "
				+ "	[SUMA_HCI] [decimal](38, 2) NULL, " + "	[DIAS_HCI] [int] NULL, " + "	[DESVEST] [float] NULL, "
				+ "	[MA_VTA_PRD] [numeric](16, 4) NOT NULL, " + "	[MA_DECIMAL] [bit] NOT NULL, "
				+ "	[nombre_proveedor] [varchar](100) NULL, " + "	[maLinSur] [int] NULL " + ")";
		stmt.executeUpdate(query2);
		String query3 = "INSERT INTO #ReturnTable EXEC Satelites.hc.Get_ItemsBySupplier_MOB " + faValida;
		stmt.executeUpdate(query3);
		String skuRelacionados = "Select top " + cantidadSKus + " SKU from #ReturnTable";
		boolean encontrados = false;

		SQLResult res;
			while (!encontrados) {
				res = new SQLResult(conn, skuRelacionados);
				if (res.getRowCount() > cantidadSKus - 1) {
					for (int i = 0; i < cantidadSKus; i++) {
						val = res.getRow(i).toString();
						val = (val.replace("]", "")).replace("[", "");
						listaSkus.add(val);
					}
					encontrados = true;
	
				} else {
					obtenerFuenteAbastoValida();
					obtenerSkusRelacionadosFaValida(cantidadSKus);
					encontrados = true;
				}
			}
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	// ########### mike2
	/*
	 * Metodo
	 ******************************************************************************************
	 * Selecciona el boton Comenzar
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void opcionComerzarActivacion() {
		au.scrollToElement(bt_comenzarActivacion);
		au.click(bt_comenzarActivacion);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * Selecciona el boton Cargar Archivo
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void opcionCargarArchivo() {
		au.click(bt_cargarArchivo);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida que se haya cargado el carchivo correctamente
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean validarArchivoCargadoCorrectamente() {
		try {
			String msg_validacion = au.driver.findElement(lb_ValidacionArchivoCargado).getText();
			if (msg_validacion.equals("Archivo cargado correctamente") == false) {
				return false;
			}

			boolean ContinuarRA = au.driver.findElement(bt_ContinuarRA).isEnabled();
			if (ContinuarRA == false) {
				return false;
			}

			testCase.addTextEvidenceCurrentStep("archivo cargado correctamente");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona el boton contnuar
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void opcionContinuarActivacion() {
		au.click(bt_ContinuarActivacion);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * ingresa codigo de activacion de la app recivo agil
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @param code obtiene el codigo de activacion
	 * 
	 * @date 10/octubre/2024
	 */
	public void ingresarCodigoActivacion(String code) {
		au.sendKeys(campoCodigoActivacion, code);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validacion que se muetre la activacion en pantalla al ingresar el codigo de
	 * activacion
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean validacionCodigoCorrecto() {
		try {
			boolean codigoCorrecto = au.driver.findElement(lb_codigoCorrecto).isEnabled();
			if (codigoCorrecto == false) {
				return false;
			}
			String textoCorrecto = au.driver.findElement(lb_codigoCorrecto).getText();
			if (textoCorrecto.equals("Código correcto") == false) {
				return false;
			}
			boolean habilitado = au.driver.findElement(bt_activar).isEnabled();
			if (habilitado == false) {
				return false;
			}

			testCase.addTextEvidenceCurrentStep("Se muestra mensaje de confirmación de Codigo Correcto");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona y valida que el boton activar este habilitado
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean opcionActivar() {
		try {
			boolean habilitado = au.driver.findElement(bt_activar).isEnabled();

			if (habilitado) {
				testCase.addTextEvidenceCurrentStep("opcion Activar se encuentra habilitado");
				au.click(bt_activar);
				au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
				testCase.addScreenShotCurrentStep(au, "testImage");
			} else {
				return false;
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida que la pantalla de activacion se muestre correctamente
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean pantallaReciboAgilActivacionExitosa() {
		try {
			boolean LogoOxxo = au.driver.findElement(logoOxxoReciboAgil).isEnabled();
			if (LogoOxxo == false) {
				return false;
			}
			String activacionExitosa = au.driver.findElement(lb_BienvenidoRA).getText();
			if (activacionExitosa.contains("Activación de la App Exitosa") == false) {
				return false;
			}

			boolean iconoPaloma = au.driver.findElement(icono_success).isEnabled();
			if (iconoPaloma == false) {
				return false;
			}

			String procesoTerminado = au.driver.findElement(lb_procesoTerminado).getText();
			if (procesoTerminado.contains("Proceso terminado") == false) {
				return false;
			}
			String botonComenzarUs = au.driver
					.findElement(By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/StartButton")).getText();
			System.out.println("5.0 " + botonComenzarUs);
			boolean botonComenzarUsarApp = au.driver
					.findElement(By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/StartButton")).isEnabled();
			System.out.println("5.1");
			if (botonComenzarUsarApp == false) {
				System.out.println("fallado");
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona el boton [Usar App]
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void opcionUsarApp() {
		au.click(bt_usarApp);
		au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona boton Capacitacion Visita Guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean VGCapacitacion() {
		try {
			au.click(bt_vg_CapacitacionVisitaGuiada);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception E) {
			testCase.addTextEvidenceCurrentStep("00 El botón no se encuentra disponible");
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona boton Preventa
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void opcionPreventa() {
		au.click(bt_Preventa);
		testCase.addScreenShotCurrentStep(au, "testImage");

	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona boton Herramienta de Compra
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void OpcionHerramientaCompra() {
		au.click(bt_vg_HerramientaCompra);
		testCase.addScreenShotCurrentStep(au, "testImage");
		au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida que se muestre mensaje Estás realizando un proceso en la visita
	 * guiada... Ver más
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean validarAvisoVisitaGuiada() {
		try {
			String texto = au.driver.findElement(lb_vg_Aviso).getText();
			String contenido = "Estás realizando un proceso en la visita guiada... Ver más";
			if (texto.equals(contenido) != true) {
				return false;
			}
			boolean salir = au.driver.findElement(bt_vg_AvisoSalir).isEnabled();
			if (salir != true) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona boton [Salir] visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void salirVisitaGuiada() {
		au.click(bt_vg_AvisoSalir);
		testCase.addScreenShotCurrentStep(au, "testImage");

	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona boton [aceptar] visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */

	public void opcionSi() {
		au.click(bt_Si);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona boton [Siguiente] visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void opcionSiguiente() {
		au.click(bt_mensajeSiguiete);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona boton [Continuar] visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void VGcontinuar() {
		au.click(bt_vg_continuar);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona boton [SI] de warning de visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	

	public void VGsi() {
		au.click(bt_vg_si);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona boton [+] de visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void clicSumar() {
		au.click(By.xpath("(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/btn_sumar\"])[1]"));
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona boton [continuar] de visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void clicContiuar() {
		au.click(By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/continueOrderExistButton"));
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona boton [continuar] de visita guiada de pantalla de qr
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void opcionContinuarQR() {
		au.scrollToElement(bt_continiarPantallaQR);
		au.click(bt_continiarPantallaQR);
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona boton [Comenzar] de visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void opcionComenzar() {
		au.click(bt_Comenzar);
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * clic sobre pantalla para pasar a la siguiente pantalla de visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void clicPantalla() {
		au.click(By.xpath("(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/btn_sumar\"])[1]"));
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona boton [pasos para lectura de QR]
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void VGPasosLecturaQR() {
		au.scrollToElement(bt_vg_PasosLecturaQR);
		au.click(bt_vg_PasosLecturaQR);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida que se muestre mensaje Estás realizando un proceso en la visita
	 * guiada... Ver más mostrado en aplicacion oxxo al dia
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean validarAvisoVisitaGuiadaOAD() {
		try {
			String texto = au.driver.findElement(lb_vg_Aviso_OAD).getText();
			String contenido = "Estás realizando un proceso en la visita guiada... Ver más";
			if (texto.equals(contenido) != true) {
				return false;
			}
			boolean salir = au.driver.findElement(bt_vg_AvisoSalirAOD).isEnabled();
			if (salir != true) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida pasos abrir herramienta de compra mostrados en visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean MensajePasosAbrirHerramientaCompra() {
		try {
			Object[] pasos = { lb_mensajePasosAbrirQR, lb_mensajePaso1, lb_mensajePaso2, lb_mensajePaso3,
					lb_mensajePaso4, lb_mensajePaso5, bt_mensajeSiguiete };
			String[] TextoPasos = { "Pasos en HH para abrir Herramienta de Compra", "Excelencia Operativa >",
					"Herramienta de Compra >", "Herramienta de Compra COMETA >",
					"Verificación de Mercancía Pedidos Proveedor Directo >", "Generación de Herramienta de compra",
					"Siguiente" };

			for (int i = 0; i < pasos.length; i++) {
				String texto = au.driver.findElement((By) pasos[i]).getText();
				if (texto.contains(TextoPasos[i]) == false) {
					return false;
				} else {
					System.out.println("Correcto : " + texto + "\t:\t" + TextoPasos[i]);
				}
			}
			boolean boton = au.driver.findElement(bt_mensajeSiguiete).isEnabled();
			if (boton == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensaje mostrado en el boton continuar de visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean MensajeBotonContinuar() {
		try {
			String texto = au.driver.findElement(mensajeVisitaguiadaGeneral).getText();
			String contenido = "Dar clic en botón \"Continuar\" para ir a Herramienta de Compra.\n\nNota: Para fines de este proceso de capacitación, se estará abriendo un pedido meramente ilustrativo al dar clic en continuar, como si se hubiera escaneado el código QR.";
			if (texto.contains(contenido) == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensaje de visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean MensajeWarning() {
		try {
			String texto = au.driver.findElement(mensajeVisitaguiadaGeneral).getText();
			String contenido = "Al abrir la herramienta de compra en el celular podrás aplicar el sugerido a "
					+ "todos los artículos activos en la tienda o aplicarlo de manera manual.";
			if (texto.contains(contenido) == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensaje de visita guiada de Herramienta de compra
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean MensajeVisualizarHC() {
		try {
			String texto = au.driver.findElement(mensajeVisitaguiadaGeneral).getText();
			String contenido = "Al visualizar la herramienta de compra podrás capturar la cantidad de pedido para cada artículo, en donde podrás visualizar también el inventario de cada uno y el sugerido de compra.";
			if (texto.contains(contenido) == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensaje de visita guiada mostrado en el boton mas
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean MensajeBotonMas() {
		try {
			String texto = au.driver.findElement(mensajeVisitaguiadaGeneral).getText();
			String contenido = "Presiona el botón \"+\" para incrementar la cantidad de pedido del artículo.\n\nTambién podrás capturar de manera manual la cantidad en caso que así lo requieras.";
			if (texto.contains(contenido) == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensaje de visita guiada mostrado en los articulos
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean MensajeModificadoArticulos() {
		try {
			String texto = au.driver.findElement(mensajeVisitaguiadaGeneral).getText();
			String contenido = "Depues de haber modificado los artículos para la generación del pedido, "
					+ "daremos clic en el botón \"Continuar\" en la parte inferior de la pantalla. "
					+ "Al dar clic se generará el QR, el cual debe ser escaneado con la Hand Held.";
			if (texto.contains(contenido) == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensaje de visita guiada mostrado en la pantalla de QR
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarMensajeAlGenerarseCodigoQR() {
		try {
			au.scrollToElement(bt_continiarPantallaQR);

			boolean icono = au.driver.findElement(By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/ic_img"))
					.isEnabled();
			if (icono == false) {
				return false;
			}

			String texto = au.driver.findElement(lb_vg_QR).getText();
			String contenido = "Al generarse este código QR y ser escaneado con la Hand Held, es cuando se genera una Orden de Compra para que pueda ser entregada al día siguiente por el equipo de reparto del proveedor. Hasta aquí la actividad del preventista finaliza.";
			if (texto.contains(contenido) == false) {
				return false;
			}

			boolean boton = au.driver.findElement(bt_continiarPantallaQR).isEnabled();
			if (boton == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensaje de visita guiada mostrado en la pantalla de QR
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarMensajeAlGenerarseCodigoQR2() {
		try {
			au.scrollToElement(bt_continiarPantallaQR);

			boolean icono = au.driver.findElement(By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/ic_img"))
					.isEnabled();
			if (icono == false) {
				return false;
			}

			String texto = au.driver.findElement(lb_vg_QR).getText();
			String contenido = "";
			if (texto.contains(contenido) == false) {
				return false;
			}

			boolean boton = au.driver.findElement(bt_continiarPantallaQR).isEnabled();
			if (boton == false) {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensaje de visita guiada mostrado en la ventana muy Bien
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarMensajeMuyBien() {
		try {
			boolean icono = au.driver.findElement(success_logo).isEnabled();
			if (icono == false) {
				return false;
			}

			String texto = au.driver.findElement(Titulo_VG).getText();
			String contenido = "¡Muy bien!";
			if (texto.contains(contenido) == false) {
				return false;
			}

			texto = au.driver.findElement(lb_mensaje).getText();
			contenido = "Al día siguiente, llega el equipo de reparto a la tienda, y confirma las cantidades disponibles "
					+ "a entregar en el punto de venta del pedido que realizó el preventista un día anterior.";
			if (texto.contains(contenido) == false) {
				return false;
			}

			boolean boton = au.driver.findElement(bt_Comenzar).isEnabled();
			if (boton == false) {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensaje de visita guiada mostrado en generacion de pedido
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarMensajePedidoGenerado() {
		try {
			String texto = au.driver.findElement(lb_PedidoGenerado).getText();
			String contenido = "Este es el Pedido generado por el preventista a entregar en el punto de venta, en donde podrás confirmar las cantidades disponibles para la entrega. \n\nEn dado caso que no cuentes con disponibilidad de algún SKU para su entrega, se tendrá que modificar la cantidad.";
			if (texto.contains(contenido) == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensaje de visita guiada mostrado en el boton continuar
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarMensajeContinuar() {
		try {
			String texto = au.driver.findElement(lb_PedidoGenerado).getText();
			String contenido = "Una vez confirmado los artículos disponibles para la entrega, se dará clic en el botón \"Continuar\" para generar el Código QR, el cual será escaneado con la Hand Held.";
			if (texto.contains(contenido) == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensaje de visita guiada mostrado en la pantalla de QR
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean VGpantallaQR() {
		try {
			au.scrollToElement(bt_vg_PasosLecturaQR);

			String lb_continuar = au.driver.findElement(lb_vg_QR).getText();
			String contenido = "Se genera un código QR el cual incluye los artículos del pedido realizado, este mismo será leído con la Hand Held por parte del equipo de la tienda OXXO. Continúa la visita guiada directamente en la HandHeld:";
			if (lb_continuar.contains(contenido) == false) {
				return false;
			}
			boolean PasosLecturaQR = au.driver.findElement(bt_vg_PasosLecturaQR).isEnabled();
			if (PasosLecturaQR == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona la opcion captura manual de herramienta de compra
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean VGCapturaManual() {
		try {
			au.click(bt_vg_CapturaManual);
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));
			return true;
		} catch (Exception E) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensaje de visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarMensajePedidoEntrega() {
		try {
			String texto = au.driver.findElement(By.xpath(
					"/hierarchy/android.widget.FrameLayout[3]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TextView"))
					.getText();
			String contenido = "Selecciona la opción \"Generar Pedido/Preventa\" para iniciar el proceso de recepción de mercancía.";
			if (texto.contains(contenido) == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona la opcion pedido entrega
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void opcionPedidoEntrega() {
		au.click(rb_GenerarPedido_Preventa);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensaje de fuente de abasto de visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean VGFuenteAbastoTB() {
		try {
			String lbFA = au.driver.findElement(lb_vg_FuenteAbasto).getText();
			String contenido = "Captura tu Fuente de abasto, mejor conocido como número de proveedor.";
			if (lbFA.contains(contenido) == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception E) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * ingresa fuente de abasto de visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @param fuente de abasto
	 * 
	 * @date 10/octubre/2024
	 */
	public void ingresarFuenteAbasto(String fa) {
		au.click(tb_FuentesDeAbasto);
		au.sendKeys(tb_FuentesDeAbasto, fa);
		testCase.addScreenShotCurrentStep(au, "testImage");
		au.click(By.xpath("//android.widget.FrameLayout[@content-desc=\"Listo\"]/android.widget.FrameLayout"));
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona el codigo de barras de visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @param true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean VGCodigoDeBarras() {
		try {
			au.click(tb_vg_CodigoDeBarras);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona el paloma de visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @param true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean VGpaloma() {
		try {
			au.click(bt_vg_paloma);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona boton sumar de visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void VGsumar() {
		au.click(bt_vg_mas);
		testCase.addScreenShotCurrentStep(au, "testImage");

	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona pantalla de visita guiada para pasar siguiente mensaje
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void VGClicPantalla() {
		au.click(pantalla);
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensaje de codigo de barras de visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean VGTxtoCodigoDeBarras1() {
		try {
			String lbCodigoDeBarras = au.driver.findElement(lb_CodigoDeBarras1).getText();
			String txtCom = "Inicia con el registro del pedido, ya sea por medio de escaneo o por ingreso manual de código de barras. Da clic sobre el ícono del código de barras para agregar los artículos.";
			if (lbCodigoDeBarras.contains(txtCom) == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception E) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensaje de visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean VGtxtPaso14() {
		try {
			String texto = au.driver.findElement(lb_vg_paso14).getText();
			String contenido = "Por cada artículo puedes visualizar el identificador, descripción y código. También puedes editar la cantidad, así como eliminarlo del listado. Haz clic en el botón \"+\" o \"-\" para editar la cantidad.";
			if (texto.contains(contenido) == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception E) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensaje de visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean VGtxtPaso15() {
		try {

			String txt_paso16 = au.driver.findElement(lb_vg_mas).getText();
			String contenido = "Al agregar artículos o editar las unidades de cada uno, se actualiza en tiempo real los campos de resumen en la parte inferior de la pantalla.";
			if (txt_paso16.contains(contenido) == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception E) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensaje del boton continuar de visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean VGmensajeContunuar() {
		try {
			String lb_continuar = au.driver.findElement(lb_vg_continuar).getText();
			String contenido = "Haz clic en \"Continuar\" cuando finalices la orden de compra.";
			if (lb_continuar.contains(contenido) == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensaje confirma captura de visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean validaMensajeConfirmaCaptura() {
		try {
			String texto = au.driver.findElement(By.xpath(
					"//android.widget.TextView[@text=\"Confirma la captura del pedido en la ventana emergente.\"]"))
					.getText();
			String contenido = "Confirma la captura del pedido en la ventana emergente.";
			if (texto.contains(contenido) == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona la opcion surtido tradicional
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean VGSutidoTradicional() {
		try {
			au.click(bt_vg_SurtidoTradicional);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception E) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensaje de generacion de pedido y entrega de visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarMensajeGeneracionPedidoEntrega() {
		try {
			String lbGenerarPedidoEntrega = au.driver.findElement(lb_vg_GenerarPedidoEntrega).getText();
			String txtGenerarPedidoEntrega = "Selecciona la opción \"Generar Pedido y Entrega (Tradicional)\" para iniciar el proceso de recepción de mercancía.";
			if (lbGenerarPedidoEntrega.contains(txtGenerarPedidoEntrega) == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception E) {
			return false;
		}

	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona la opcion pedido y entrega
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void VGGenerarPedidoyEntrega() {
		au.click(rb_GenerarPedidoyEntregaTradicional);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona la opcion fuentes de abasto
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void precionaFuenteAbasto() {
		au.click(tb_FuentesDeAbasto);
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensaje de visita guiada mostrada en el codigo de barras
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean VGTextoCodigoDeBarras() {
		try {

			String lbCodigoDeBarras = au.driver.findElement(lb_vg_CodigoDeBarras).getText();
			String txtCom = "Inicia con el registro del pedido, ya sea por medio de escaneo o por ingreso manual de código de barras. Da clic sobre el ícono de la palomita para agregar los artículos.";
			if (lbCodigoDeBarras.contains(txtCom) == false) {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception E) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona boton
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void VGcheck() {
		au.click(bt_vg_check);
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensade visita guiada generar QR
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean VGmensajeGenerarQR() {
		try {
			String lb_continuar = au.driver.findElement(lb_vg_generarQR).getText();
			String contenido = "Haz clic en \"Generar QR\" cuando finalices la descarga de los artículos del pedido y te encuentres con el equipo de tienda para la verificación de la mercancía. \n\nIMPORTANTE: Si en dado caso requieres modificar el pedido, da clic en el botón \"Modificar pedido\".";
			if (lb_continuar.contains(contenido) == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * selecion boton Generar QR
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void VGgenerarQR() {
		au.click(bt_vg_generarQR);
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida pasos del mesaje lectura de QR
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean MensajePasosLecturaQR() {
		try {

			Object[] pasos = { lb_mensajePasosAbrirQR, lb_mensajePaso1, lb_mensajePaso2, lb_mensajePaso3,
					lb_mensajePaso4, lb_mensajePaso5, bt_mensajeSiguiete };
			String[] TextoPasos = { "Pasos para lectura de QR en Hand Held", "Excelencia Operativa >",
					"Herramienta de Compra >", "Herramienta de Compra COMETA >",
					"Verificación de Mercancía Pedidos Proveedor Directo >", "Generación de Herramienta de compra",
					"Siguiente" };

			for (int i = 0; i < pasos.length; i++) {
				String texto = au.driver.findElement((By) pasos[i]).getText();
				if (texto.contains(TextoPasos[i]) == false) {
					return false;
				} else {
					continue;
				}
			}

			boolean boton = au.driver.findElement(bt_mensajeSiguiete).isEnabled();
			if (boton == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensaje del boton continuar
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean validarMensajeContunuar() {
		try {
			String lb_continuar = au.driver.findElement(mensajeVisitaguiadaGeneral).getText();
			String contenido = "Selecciona la opción \"Continuar\" para escanear el código QR de la herramienta de compra.";

			if (lb_continuar.contains(contenido) == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensaje de visita guiada aplicara el sugerido
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidacionWarning() {
		try {
			String texto = au.driver.findElement(alerta).getText();
			String contenido = "¿Desea aplicar el sugerido a todos los artículos?";
			if (texto.contains(contenido) == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona boton pedido precargado
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void OpcionPedidoPrecargado() {
		au.click(bt_vg_PedidoPrecargado);
		testCase.addScreenShotCurrentStep(au, "testImage");
		au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona boton Surtio Tradicional
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void OpcionSurtidoTradicional() {
		au.click(bt_vg_SurtidoTradicional);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * Valida menaje de visitra guiada escanear QR
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean mensajeEscanearQR() {
		try {
			String texto = au.driver.findElement(lb_mensajePasosQR).getText();
			String contenido = "Seleccionar la opción de escanear QR de pedido para leer la orden de compra precargada.\n\nEl QR a escanear te lo debe proporcionar el equipo de la tienda, este mismo lo va a encontrar en el apartado de pedidos precargados.";
			if (texto.contains(contenido) == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona opcion pedido QR
	 * 
	 * @author Miguel Bautista
	 * 
	 * @return N/A
	 * 
	 * @param N/A
	 */
	public void opcionPedidoQR() {
		au.click(rb_EscanearQRPedidoQRdeHC);
		testCase.addScreenShotCurrentStep(au,"testImage");
	}
	
	
	/*
	 * Metodo
	 ******************************************************************************************
	 * valida informacion del mensaja de visita guiada del boton continuar
	 * 
	 * @author Miguel Bautista
	 * 
	 * @return true/false
	 * 
	 * @param N/A
	 */
	public boolean VGmensajeresumenArticulos() {
		try {
			String lb_continuar = au.driver.findElement(lb_vg_continuar).getText();
			String contenido = "Este es el resumen de pedido que se debe entregar en la tienda, en el cual se deben marcar los artículos una vez descargados del camión, ya sea uno por uno o todos a la vez. \n\nIMPORTANTE: Si no seleccionas uno de los SKU's de este resumen, no se considerarán dentro del pedido a entregar en la tienda.";
			if (lb_continuar.contains(contenido) == false) {
				testCase.addTextEvidenceCurrentStep("No se muestra el texto de manera correcta. " + contenido);
				return false;
			}
			return true;
		}catch(Exception e) {
			return false;
		}
	}
}
