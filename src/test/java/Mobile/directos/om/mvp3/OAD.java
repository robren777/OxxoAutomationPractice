package Mobile.directos.om.mvp3;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File; //firma
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files; //firma
import java.nio.file.Paths;
import java.security.MessageDigest; //firma
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
import java.util.List;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.io.FileUtils; //firma
import org.apache.tools.ant.filters.StringInputStream;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType; //firma
import org.openqa.selenium.TakesScreenshot; // firma
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.jcraft.jzlib.GZIPInputStream;

import Mobile.util.GlobalVariables;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;
import utils.password.PasswordUtil;
import utils.sql.SQLResult;

public class OAD {
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;

	/*
	 * Localizadores Generales de la App.
	 */
	private By bt_Regresar = By.xpath("//android.widget.ImageButton[@content-desc=\"Navegar hacia arriba\"]");
	private By bt_Tutoriales = By.xpath(
			"//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ic_tutto\"]");
	private By bt_PregFrecuentes = By.xpath(
			"//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ic_question\"]");
	private By Title_CambiosFisicos = By.xpath("//android.widget.TextView[@text=\"CAMBIOS FÍSICOS\"]");
	private By Warnings = By.id("android:id/message");
	private By btnAceptarGral = By.id("android:id/button1");
	private By bt_NoGral = By.id("android:id/button2");
	private By tit_CambiosFisicos = By.xpath("//android.widget.TextView[@text=\"CAMBIOS FÍSICOS\"]");

	// ##############
	private By Tit_CambiosFisicos = By.xpath("//android.widget.TextView[@text=\"CAMBIOS FÍSICOS\"]");
	private By Tit_CambiosFisicos2 = By.xpath("//android.widget.TextView[@text=\"CAMBIOS FÍSICOS\"]");

	/*
	 * Localizadores de pantalla
	 */
	private By txt_Usuario = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tilUserName");
	private By mnu_MenuPrincipal = By.xpath("//android.widget.ImageButton[@content-desc=\"Open navigation drawer\"]");
	private By mnu_ExcelenciaOperativa = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvModule");
	private By mnu_HerramientaDeCompra = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvModule");

	/*
	 * Localizadores de ingreso a modulo Herramientas (Cometa)
	 */
	private By bt_ExcOperativa = By.xpath(
			"(//android.widget.FrameLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/cvMenu\"])[3]");
	private By bt_HerrCompra = By.xpath(
			"(//android.widget.FrameLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/cvMenu\"])[4]");
	private By bt_HerrCompra2 = By.xpath(
			"(//android.widget.FrameLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/cvMenu\"])[4]");
	private By bt_Cometa = By.xpath(
			"(//android.widget.FrameLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/cvMenu\"])[2]");
	// ###################
	private By bt_PedPrecargados = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/operation_buscar_pedidos_precargados");
	private By ttl_VerificacionM = By.xpath("//android.widget.TextView[@text=\"PROVEEDORES DIRECTOS\"]");
	private By bt_GenHC = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/operation_generacion_herramienta_compra");
	private By bt_LeerQrPreventaoPedido = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/operation_leer_qr_preventa_pedido");
	private By lblRPCedis = By.xpath("//android.widget.TextView[@text=\"Recepción de Pedidos\n" + "CEDIS\"]");
	private By lblVMCedis = By
			.xpath("//android.widget.TextView[@text=\"Verificación de Mercancía\n" + "Pedidos CEDIS\"]");
	private By lbltirasEntrega = By.xpath("//android.widget.TextView[@text=\"Consultar Tiras de\n" + "Entrega\"]");

	/*
	 * Localizadores de ingreso a modulo Herramientas
	 */
	private By btn_ExcelenciaOperativa = By.xpath(
			"(//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ibImage\"])[3]");
	private By btn_Cometa = By.xpath(
			"(//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ibImage\"])[2]");
	private By btn_Movilidad = By.xpath(
			"(//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ibImage\"])[1]");

	// Localizadores de botones de modulo Herramientas (Cometa)
	private By titleHerramientas = By.xpath("//android.widget.TextView[@text=\"Herramientas\"]");
	private By bt_VerMercancia = By
			.xpath("//android.widget.TextView[@text=\"Verificación de Mercancía\n" + "Pedidos Proveedor Directo\"]");
	private By bt_CambFisicos = By
			.xpath("//android.widget.TextView[@text=\"Cambios físicos de productos\n" + "(Proveedores Directos)\"]");
	private By bt_DevoProductos = By
			.xpath("//android.widget.TextView[@text=\"Devoluciones de producto\n" + "(Proveedores Directos)\"]");
	private By btn_RecepcionCedis = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/cedisRepBtn");
	private By btn_VerificacionCedis = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/cedisVeriPed");
	private By btn_ConsultaTirasDeEntrega = By.xpath(
			"//android.widget.FrameLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnConsultarTiras\"]/android.widget.LinearLayout");
	private By btn_VerificacionProveedorDirecto = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/directosVerificar");
	private By btn_CambiosFisicosProveedores = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/CambiosFisicos");
	private By btn_DevolucionDeProductos = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/DevolucionesArticulos");

	/*
	 * Localizadores de Pantalla Cambios Físicos.
	 */
	private By bt_CapCambiosFisicos = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/item_name\" and @text=\"Capturar cambios físicos\"]");
	private By bt_ConsultCambiosFisicos = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/item_name\" and @text=\"Consultar cambios físicos pendientes\"]");
	private By bt_SiguienteCCF = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btnResultadosSiguiente");
	private By bt_Siguiente = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btnSiguiente");
	// #####################
	private By bt_No = By.id("android:id/button2");
	private By tb_IngresarQr = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/edt_qr_captured");
	private By bt_CapturarQR = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_capture_qr");
	private By bt_AceptarCapturaQrVisitaGuiada = By.id("android:id/button1");
	private By lb_CaptureNumRemisionVisitaGuiada = By.xpath(
			"//android.widget.TextView[@text=\"Capture el número de remisión, este mismo te lo proporciona el proveedor en el paso previo a la verificación de la mercancía.\"]");
	private By bt_LeerQR = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/item_name\" and @text=\"Leer QR para\nPreventa o Pedido\"]");
	/*
	 * Localizadores pantalla Consulta cambios físicos.
	 */
	private By lb_BuscarPor = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo");
	private By rb_FuenteDeAbastosProveedor = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/optFuentaAbasto");
	private By rb_NumeroDeRemision = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/optNumeroRemision");
	private By lb_FAyNumRemision = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwFuenteDeAbastoLabel");
	private By tb_CapFAoNumRemision = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/edtCapturaFuenteAbastoONumeroRemision");
	private By bt_BuscarProveedorRemision = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/btnBuscarProveedorRemision");
	// ###################################
	private By lb_AcuerdoDeEntrega = By.xpath("//android.widget.TextView[@text=\"ACUERDO DE ENTREGA\"]");
	private By bt_ImprimirCCF = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btnResultadosImprimir");
	private By correoBarra = By.id("android:id/notification_main_column");

	/*
	 * Localizadores Mensaje Superior Visita Guiada.
	 */
	private By lb_textoSuperiorVisitaGuiada = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/visita_guiada_aviso_tv");
	private By bt_SalirVisitaGuiada = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/salir_vg_btn");

	/*
	 * Localizadores de Pantalla de Captura de Cambios Físicos.
	 */
	private By tb_CapturaFuenteAbasto = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/edtCapturaFuenteDeAbasto");
	private By bt_Buscar = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btnBuscarProveedor");
	private By tb_CapturaSku = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/edtCapturaSku");
	private By bt_CapturaSku = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btntCapturaSku");
	private By bt_IngresaSku = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btntIngresaSku");
	private By lb_FuenteAbasto = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwFuenteDeAbastoLabel");
	private By lb_Proveedor = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwProveedorLabel");
	private By lb_CantidadSku = By.xpath(
			"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edtCantidadCambiosFisicos\"])");
	private By editIngresarCantManual = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/amountInsertedDialog");
	private By btnConfirmarCantManual = By.id("android:id/button1");
	private By btnCancelarCantManual = By.id("android:id/button2");
	private By popUpIngresaUnaCantidad = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/titleDialog");
	private By mensajeNoMenosDeUnaUnidad = By.id("android:id/message");
	private By btnAceptarMensajeNoMenosDeUnaUnidad = By.id("android:id/button1");
	private By lb_ArtPendientes = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo\" and @text=\"Art. pendientes:\"]");
	private By contadorArtPendientes = By.xpath(
			"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvw_valor\"])[1]");
	private By lb_ArtConCambios = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo\" and @text=\"Art. con cambios:\"]");
	private By contadorArtConCambios = By.xpath(
			"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvw_valor\"])[2]");
	private By lb_PiezasPendientes = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo\" and @text=\"Piezas pendientes:\"]");
	private By contadorPiezasPendientes = By.xpath(
			"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvw_valor\"])[3]");
	private By lb_PiezasConCambios = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo\" and @text=\"Piezas con cambios:\"]");
	private By contadorPiezasConCambios = By.xpath(
			"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvw_valor\"])[4]");
	private By btn_Siguiente = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btnSiguiente");
	private By bt_EliminarSku = By.xpath(
			"(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnDeleteCambioFisico\"])[1]");
	private By bt_Coma = By.xpath("//android.widget.FrameLayout[@content-desc=\",\"]");
	private By bt_Punto = By.xpath("//android.widget.FrameLayout[@content-desc=\".\"]");
	// ###########################
	private By bt_SumarSku = By.xpath(
			"(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnAddCambioFisico\"])[1]");
	private By bt_ConfirmarWarning = By.id("android:id/button1");
	private By tb_CFRealizado = By.xpath(
			"(//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edt_cfisico_realizado\"])[1]");
	private By tb_CFProgramado = By.xpath(
			"(//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edt_cfisico_programado\"])[1]");

	/*
	 * Localizadores de pantalla Principal de Verificación de Mercancia Pedidos
	 * Proveedor Directo
	 */

	private By lb_ProveedoresDirectos = By.xpath("//android.widget.TextView[@text=\"PROVEEDORES DIRECTOS\"]");
	private By lb_BusqPedidosPrecargados = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/item_name\" and @text=\"Búsqueda pedidos\n"
					+ "Precargados\"]");
	private By lb_LeerQR = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/item_name\" and @text=\"Leer QR para\n"
					+ "Preventa o Pedido\"]");

	/*
	 * Localizadores - eliminar un articulo en cambios fisicos
	 */
	private By btn_AceptarEliminarSku = By.id("android:id/button1");
	private By btn_CancelarElimnarSku = By.id("android:id/button2");
	private By lb_TotalArtConCambios = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvw_valor\" and @text=\"3\"]");
	private By WarningEliminar = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\"]");
	private By bt_EliminarSkuDOS = By.xpath(
			"(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnDeleteCambioFisico\"])[2]");
	private String lbl_Articulotres = "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvwCambioFisicoDescription\"]";
	private By xpathPadreArt3 = By.xpath(
			"//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/rvwListaCambiosFisicos\"]/android.view.ViewGroup[3]"
					+ lbl_Articulotres);
	private By xpathPadreArt2 = By.xpath(
			"//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/rvwListaCambiosFisicos\"]/android.view.ViewGroup[2]"
					+ lbl_Articulotres);

	/*
	 * Localizadores - Pantalla Acuerdo de Entrega
	 */
	private By titleAcuerdoDeEntrega = By.xpath("//android.widget.TextView[@text=\"ACUERDO DE ENTREGA\"]");
	private By lb_ResumenCambiosProgPendientes = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo_cf_resumen_programados");
	private By tb_ResumenCambiosProgPend = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/rv_acuerdo_entrega_cf_pendientes");
	private By lb_ResumenCambiosRealizados = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo_cf_resumen_realizados");
	private By tb_ResumenCambiosRealizados = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/rv_acuerdo_entrega_cf_realizados");
	// ####################
	private By campoOtros = By
			.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"OTROS (ESPECIFICA)\"]");
	private By ckb_SeCumplioProcesoEntrega = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/chk_cumplio_proceso_entrega_detalle_cf");

	// Tabla Resumen Cambios Programados Pendientes
	private By hd_idCambProg = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_id_cf_programado_header");
	private By hd_artCambProg = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_articulo_cf_programado_header");
	private By hd_descCambProg = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_descripcion_cf_programado_header");
	private By AECFPPfirmaTienda = By.xpath(
			"//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/img_valor\"]");

	// Tabla Resumen Cambios Realizados Pendientes
	private By hd_idCambRealiz = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_id_header");
	private By hd_artCambRealiz = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_articulo_header");
	private By hd_descCambRealiz = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_descripcion_header");
	private By hd_cantCambRealiz = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_cantidad_header");
	private By AEObservacionesProveedor = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/edt_valor");
	private By AEfirmaProveedor = By.xpath(
			"//android.widget.LinearLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/signature_pad_display_proveedor\"]//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/img_valor\"]");
	private By lbObsProv = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo\" and @text=\"Observaciones Personal Tienda:\"]");
	private By AEObservacionesTienda = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/edt_valor_bis");
	private By AEfirmaTienda = By.xpath(
			"//android.widget.LinearLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/signature_pad_display_personal_tienda\"]//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/img_valor\"]");
	private By AERdBtnCumpProcCambFis = By.xpath(
			"//android.widget.CheckBox[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/chk_cumplio_proceso_entrega_detalle_cf\"]");
	private By AEmsgAgObsProveedor = By.id("android:id/message");
	private By AEmsgAgObsTienda = By.xpath(
			"//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"Favor de agregar Observaciones del Personal de Tienda\"]");
	private By AEbtnAceptar = By.xpath("//android.widget.Button[@text=\"ACEPTAR\"]");
	private By AEmsgAgFirTienda = By.id("android:id/message");
	private By AEavisoCorreo = By.id("android:id/message");
	private By AEBtnSiguiente = By.xpath("//android.widget.Button[@text=\"SIGUIENTE\"]");
	private By AEBtnSiguiente_J = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_siguiente");
	private By AEOpcionSinComentarios = By
			.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"SIN COMENTARIOS\"]");
	private By AEOpcionOtros = By
			.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"OTROS (ESPECIFICA)\"]");
	private By AEfirma = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/signature_pad");
	private By AEbtnContinuar = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_continuar");
	private By AEMsjCapturaCFExitosa = By.id("android:id/message");
	private By tit_FirmaDeAcuerdo = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo_firma_de_acuerdo_detalle_cf");
	private By lbl_ObservacionesDelProveedor = By
			.xpath("//android.widget.TextView[@content-desc=\"Título del campo\"]");
	private By lbl_firmaProveedor = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo\" and @text=\"Firma Proveedor:\"]");
	private By cbo_SinComentarios = By
			.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"SIN COMENTARIOS\"]");
	private By cbo_Otros = By
			.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"OTROS (ESPECIFICA)\"]");
	private By AEmsgAgFirPersonalTienda = By.xpath(
			"//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"Favor de agregar Firma del Personal de Tienda\"]");
	private By MsgCapturaExitosa = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\"]");
	private By SSinComentarios = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/edt_valor_bis");
	private By campoObservacionesProveedor = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/edt_valor");
	private By campoSinComentarios = By
			.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"SIN COMENTARIOS\"]");
	private By campoObservacionesPersonalTienda = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/edt_valor_bis");
	private By edt_FirmaProveedor = By.xpath(
			"(//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/img_valor\"])[1]");
	private By edt_IngresarFirmaProveedor = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/signature_pad");
	private By bt_Continuar = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_continuar");
	private By edt_FirmaPersonalTiendaCCF = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/img_valor");
	private By edt_IngresarFirmaPersonalTienda = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/signature_pad");
	private By bt_Si = By.id("android:id/button1");
	private By bt_Finalizar = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_finalizar_enviar");

	/*
	 * Localizadores - Pantalla Tira de Entrega
	 */
	private By titleTiraEntrega = By.xpath("//android.widget.TextView[@text=\"TIRA DE ENTREGA\"]");
	private By lb_numRemisionCF = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwRemision");
	private By TE_btnFinalizar = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_finalizar_enviar");
	private By imgOxxo = By.xpath(
			"//android.widget.FrameLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/header_tira\"]/android.widget.LinearLayout/android.widget.ImageView");
	private By lbl_Proveedor = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwProveedor");
	private By lbl_ProveedorTira = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwNombreProveedor");
	private By lbl_CrTienda = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwCrTienda");
	private By lbl_CrPlaza = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwPlaza");
	private By lbl_FechaADMVA = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwFecha");
	private By lbl_Hora = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwHora");
	private By lbl_CDirectas = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwComprasDirectas");
	private By lbl_Remision = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwRemision");
	private By lbl_TiempoCF = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwTiempoEstadia");
	private By lbl_ST = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwSt");
	private By lbl_namePlaza = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvCrPlaza");
	private By mnu_lider = By.xpath("//android.widget.ImageButton[@content-desc=\"Open navigation drawer\"]");
	private By btn_BackA = By.xpath("//android.widget.ImageView[@content-desc=\"Atrás\"]");
	private By lbl_ResumenRealizado = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo_cf_resumen_realizados");
	private By lbl_ResumenProgramados = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo_cf_tira_programados");
	private By txa_Leyenda = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvw_leyenda");
	private By tbl_IdRealizado = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_id_header");
	private By tbl_ARTRealizado = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_articulo_header");
	private By tbl_DescRealziado = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_descripcion_header");
	private By tbl_CantRealizado = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_cantidad_header");
	private By tbl_IdProgramado = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_id_cf_programado_header");
	private By tbl_ArtProgramado = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_articulo_cf_programado_header");
	private By tbl_DescProgramado = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_descripcion_cf_programado_header");
	private By tbl_FechaProgramado = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_fecha_cf_programado_header");
	private By tbl_DiasProgramado = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_dias_cf_programado_header");
	private By tbl_CantProgramado = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_cantidad_cf_programado_header");
	private By tbl_CostoProgramado = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_costo_cf_programado_header");
	private By imgAutorizado = By.xpath("//android.widget.ScrollView/android.view.ViewGroup/android.widget.ImageView");
	private By lbl_FirmaAc = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo_firma_de_acuerdo");
	private By lbl_ObsProveedor = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo\" and @text=\"Observaciones del Proveedor:\"]");
	private By lbl_FirmaProveedor = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo\" and @text=\"Firma Proveedor:\"]");
	private By lbl_ObsTienda = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo\" and @text=\"Observaciones Personal Tienda:\"]");
	private By lbl_FirmaTienda = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo\" and @text=\"Firma Personal Tienda:\"]");
	private By rdo_Acuerdo = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/chk_cumplio_proceso_entrega_tira_cf");
	private By btn_Finalizar = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_finalizar_enviar");
	private By btn_Imprimir = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_imprimir");
	private By btn_Sumar = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_sumar");
	private By btn_Restar = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_restar");
	// #########################
	private By lb_TiraDeEntrega = By.xpath("//android.widget.TextView[@text=\"TIRA DE ENTREGA\"]");
	private By lb_ResumenCambiosProgramados = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo_cf_resumen_programados");
	private By cp_ObservacionesProveedorTiraEntrega = By.xpath(
			"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvw_valor\"])[1]");
	private By cp_ObservacionesPersonalTiendaTiraEntrega = By.xpath(
			"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvw_valor\"])[2]");
	private By lb_SelloAutorizado = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvw_leyenda");
	private By ck_ProcesoDeEntregaTiraDeEntrega = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/chk_cumplio_proceso_entrega_tira_cf");
	private By bt_Imprimir = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_imprimir");
	private By lb_Prove = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwNombreProveedor");
	private By lb_CrTienda = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwCrTienda");
	private By lb_Plaza = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwPlaza");
	private By lb_Fecha = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwFecha");
	private By lb_TiempoCF = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwTiempoEstadia");
	private By lb_Hora = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwHora");

	/*
	 * Localizadores para Validar Pantalla Generación de Herramienta de Compra.
	 */

	private By TxtProveedor = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo");
	private By rbGenPedyPrev = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/preinventaristaRadioButton");
	private By rbGenPedyEntrega = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/entregaRadioButton");
	private By edtNumeroProveedor = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/supplyProv");
	private By btnGenerarCodigosQR = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/generateQrButton");
	private By btnVolver = By.xpath("//android.widget.ImageButton[@content-desc=\"Navegar hacia arriba\"]");

	/*
	 * Localizadores - Pantalla Consulta Cambios Fisicos Programados Pendientes
	 */
	private By lbResumenCFPrgramados = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwResultadoTituloTabla");
	private By hd_FuenteAbasto = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwResultadoFuenteDeAbastoLabel");
	private By hd_Proveedor = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwResultadoProveedorLabel");
	private By numR = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwResultadoRemisionLabel");
	private By faValue = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvwResultadoFuenteDeAbastoValue\"]");
	private By numRemValue = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvwResultadoRemisionValue\"]");
	private By CFPP_btnSiguiente = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btnResultadosSiguiente");
	private By CFPP_btnImprimir = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btnResultadosImprimir");
	private By CFPP_tipoCambFis = By.xpath("(//android.widget.TextView[@resource-id=\"android:id/text1\"])[1]");
	private By CFPP_cambRealizados = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwCantidadEditadaLabel");
	private By CFPP_piezasADevol = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwCantidadEditadaLabel");
	private By CFPP_btnRestar = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_restar");
	private By CFPP_btnSumar = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_sumar");
	private By CFPP_cantEditada = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwCantidadEditadaValue");
	private By CFPP_lbMonTotalPend = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwResultadoMontoTotalPendientesLabel");
	private By CFPP_lbMonTotalCambRealiz = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwResultadoMontoTotalCambiosRealizadosLabel");
	private By CFPP_edtMontTotalPend = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwResultadoMontoTotalPendientesValue");
	private By CFPP_edtMontTotalCamb = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwResultadoMontoTotalCambiosRealizadosValue");
	private By CFPP_hdResumen = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_resumen_header");
	private By CFPP_hdArticulos = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_articulos_header");
	private By CFPP_hdPiezas = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_piezas_header");
	private By CFPP_rPendientes = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_nombre\" and @text=\"PENDIENTES\"]");
	private By CFPP_rCambiosARealiz = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_nombre\" and @text=\"CAMBIOS A REALIZAR\"]");
	private By CFPP_rDevoluciones = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_nombre\" and @text=\"DEVOLUCIONES\"]");
	private By CFPP_msgCFExedido = By.xpath(
			"//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"Se ha excedido la cantidad de productos con cambios físicos.\"]");
	private By CFPP_cantPendArt = By.xpath(
			"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_articulos_cantidad\"])[1]");
	private By CFPP_cantPendPiezas = By.xpath(
			"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_piezas_cantidad\"])[1]");
	private By CFPP_cantRealizArt = By.xpath(
			"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_articulos_cantidad\"])[2]");
	private By CFPP_cantRealizPiezas = By.xpath(
			"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_piezas_cantidad\"])[2]");
	private By CFPP_cantDevoArt = By.xpath(
			"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_articulos_cantidad\"])[3]");
	private By CFPP_cantDevoPiezas = By.xpath(
			"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_piezas_cantidad\"])[3]");
	private By CFPP_filtroFechas = By.xpath("(//android.widget.TextView[@resource-id=\"android:id/text1\"])[1]");
	private By txt_FACapturada = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/edtCapturaFuenteAbastoONumeroRemision");
	private By txb_CambioProgramado = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/edt_cfisico_programado");
	private By dtp_Articulo = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_articulo_cf_programado");
	private By lbl_RemisionMostrada = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwNumeroRemisionValue");
	private By cbo_TipodeCambio = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/imgSpnrTipoDevolucion");
	private By lbl_ArticuloProgramado = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwCambioFisicoCodigo");
	private By btn_SiguienteConsulta = By.xpath(
			"//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnResultadosSiguiente\"]");
	private By ddl_Realizado = By.xpath("//android.widget.TextView[@index='1']");
	private By ddl_Pendiente = By.xpath("//*[@index='0'and @text='PENDIENTE']");
	private By ddl_AplicarDevolucion = By.xpath("//android.widget.TextView[@index='2']");
	private By lbl_CambiosRealziados = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwCantidadEditadaLabel");
	private By txb_unidadesRealziadas = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwCantidadEditadaValue");
	private By txb_CantidadesPendientes = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwCantidadValue");
	private By lbl_ArticulosDev = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwCantidadEditadaLabel");
	private By msg_EnvioADev = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\"]");
	private By rb_UnidadesProgramadas = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_cantidad_cf_programado");

	/*
	 * Localizadores de Tabla de Cambios Fisicos
	 */

	private By tbId = By.xpath(
			"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_id_cf_programado_header\"])[1]");
	private By tbArticulo = By.xpath(
			"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_articulo_cf_programado_header\"])[1]");
	private By tbDescripcion = By.xpath(
			"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_descripcion_cf_programado_header\"])[1]");
	private By tbRemision = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_remision_cf_programado_header\"]");
	private By tbfecha = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_fecha_cf_programado_header\"]");
	private By tbDias = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_dias_cf_programado_header\"]");
	private By tbCant = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_cantidad_cf_programado_header\"]");
	private By tbCosto = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_costo_cf_programado_header\"]");
	private By tbIdCP = By.xpath(
			"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_id_cf_programado_header\"])[2]");
	private By tbArticuloCP = By.xpath(
			"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_articulo_cf_programado_header\"])[2]");
	private By tbDescripcionCP = By.xpath(
			"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_descripcion_cf_programado_header\"])[2]");
	private By rb_GenPedidoPreventa = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/preinventaristaRadioButton");
	private By rb_GenPedidoRepartidor = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/entregaRadioButton");
	private By edt_NumProveedor = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/supplyProv");
	private By bt_GenCodigosQR = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/generateQrButton");
	private By QRGenHC = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvw_qr_code");
	private By tbPId = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_id_cf_programado_header\"]");
	private By tbPArticulo = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_articulo_cf_programado_header\"]");
	private By tbPDescripcion = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_descripcion_cf_programado_header\"]");
	private By lb_FirmadeAcuerdoCCF = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo_firma_de_acuerdo");
	private By cp_ObsProvedor = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo\" and @text=\"Observaciones del Proveedor:\"]");
	private By lb_FirmaProovedor = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo\" and @text=\"Firma Proveedor:\"]");
	private By lb_ObsPersonal = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo\" and @text=\"Observaciones Personal Tienda:\"]");
	private By lb_FirmaPersonal = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo\" and @text=\"Firma Personal Tienda:\"]");

	/*
	 * Localizadores - Proceso VisistaGuiada Precargado/Reple
	 */
	private By btn_leerQrParaPreventa = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/operation_leer_qr_preventa_pedido");
	private By btnNoGral = By.id("android:id/button2");
	private By btnSiGral = By.id("android:id/button1");
	private By edt_leerQrParaPreventa = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/edt_qr_captured");
	private By capturarQrParaPreventa = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_capture_qr");
	private By aceptarCodigoPrueba = By.id("android:id/button1");
	private By msjProcesoVisitaGuiada = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/visita_guiada_aviso_tv");
	private By salirVisitaGuiada = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/salir_vg_btn");
	private By msjSalirVisitaGuiada = By.id("android:id/message");

	/*
	 * Localizadores - Pantalla Inventario Operativo/PROVEEDORES DIRECTOS
	 */
	private By PDtitle = By.xpath("//android.widget.TextView[@text=\"PROVEEDORES DIRECTOS\"]");
	private By PDbtn_regresar = By.xpath("//android.widget.ImageButton[@content-desc=\"Navegar hacia arriba\"]");
	private By PDiconoInterrogacion = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/ic_question");
	private By PDiconoAdmiracion = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/ic_tutto");
	private By PDPredevolucionAProv = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/operation_predevolucion_a_proveedor");
	private By PDDevolucionAProv = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/operation_devolucion_a_proveedor");

	/*
	 * Localizadores - Predevolucion a Proveedores
	 */
	private By PP_Cr = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwCrTienda");
	private By PP_FolioGoma = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwFolioGoma");
	private By PP_Fecha = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwFechaDevolucion");
	private By PP_OpcionesF = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btnOpciones");
	private By PP_lblDescripDev = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwTitlePredevolucion");
	private By PP_edtDescripDev = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/edtCapturaDevolucion");
	private By PP_lblDevAProv = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvw_title_devolucion");
	private By PP_edtCodigoB = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/edtCapturaSku");
	private By PP_btnCodigoB = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btntCapturaSku");
	private By PP_ArtADevol = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo");
	private By PP_PiezasADev = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo2");
	private By PP_TotalPredev = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo_total_predevolucion");
	private By PP_btnCerrar = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btnCerrar");
	private By PP_btnImprimir = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btnImprimir");
	private By PP_btnSalir = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btnSalir");

	/*
	 * Localizadores Pantalla - Verificacion de Mercancia/Proveedores Directos
	 */
	private By titleProveedoresDirectos = By.xpath("//android.widget.TextView[@text=\"PROVEEDORES DIRECTOS\"]");
	private By bt_genHtaCompra = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/operation_generacion_herramienta_compra");
	private By bt_busquedaPedPrecargados = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/operation_buscar_pedidos_precargados");

	/*
	 * Localizadores Pantalla -Busqueda De Pedido
	 */
	private By titleBusquedaPedidos = By.xpath("//android.widget.TextView[@text=\"BÚSQUEDA DE PEDIDO\"]");
	private By lb_busquedaPorPP = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/txtSutitle");
	private By lb_pedidosPP = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/txtSutitlePedidos");

	/*
	 * Localizadores Pantalla - Generacion Herramienta de Compra
	 */
	private By rb_genPedidoPreventa = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/preinventaristaRadioButton");
	private By bt_genCodigosQR = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/generateQrButton");
	private By tb_numProveedor = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/supplyProv");
	private By lb_cantQrs = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/subtitle");
	private By tb_QrGeneradoHtaCompra = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvw_qr_code");
	private By bt_siguienteQr = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_siguiente");

	//// mike
	private By btn_limpiar = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_limpiar");
	private By lb_otros = By.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"OTROS (ESPECIFICA)\"]");
	private By campoFA = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwResultadoFuenteDeAbastoValue");
	private By campoRemision = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwResultadoRemisionValue");
	private By campoCF = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwCambioFisicoCodigo");
	private String skuDummy = "//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/tvw_producto_sku\"]";
	private String ProductoDummy = "//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/txv_producto_nombre\"]";
	private String DummyPadre = "(//android.view.ViewGroup[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/containerItemProducts\"])";
	private By opcion_pendiente = By.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"PENDIENTE\"]");
	private By opcion_realizado = By.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"REALIZADO\"]");
	private By bt_Sumar = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_sumar");
	private By tit_resumenRealizdos = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo_cf_resumen_realizados");
	private By tit_cambiosProgramados = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo_cf_resumen_programados");
	private By lb_FirmaAcuerdo = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo_firma_de_acuerdo_detalle_cf");
	private By lb_observacionesPorveedor = By.xpath("//android.widget.TextView[@content-desc=\"Título del campo\"]");
	private By lb_firmaProveedor = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo\" and @text=\"Firma Proveedor:\"]");
	private By lb_observacionesTienda = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo\" and @text=\"Observaciones Personal Tienda:\"]");
	private By lb_selecionaOpcionTienda = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/edt_valor_bis");
	private By lb_firmaTienda = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo\" and @text=\"Firma Personal Tienda:\"]");
	private By lb_remision = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvwRemision");
	private By tit_AcuerdoEntrega = By.xpath("//android.widget.TextView[@text=\"ACUERDO DE ENTREGA\"]");
	private By bt_vg_PasosLecturaQR = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/btn_text");
	private By lb_mensajeContinuarArticulos = By.xpath("/hierarchy/android.widget.FrameLayout[3]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TextView");
	private By lb_mensajeSeGeneraQR = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/pasos_a_hh_body");
	private By lb_mensajeTerminar = By.xpath("/hierarchy/android.widget.FrameLayout[3]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TextView");
	private By lb_mensajeComentarioProveedor = By.xpath("/hierarchy/android.widget.FrameLayout[4]/android.widget.FrameLayout/android.widget.LinearLayout//android.widget.TextView[@checkable='false' or @checked='false' or @class='android.widget.LinearLayout' or @text=\"PROVEEDOR\r\n"
					+ "Escribe un comentario a modo de observaciones respecto al acuerdo de entrega de la OC.\"]");
	private By compoNoRemision = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/edt_num_remision");
	private By bt_CapturarQRp = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/btn_capture_qr");
	private By lb_lecturaQRpaso1 = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/texto_paso\" and @text=\"Excelencia Operativa >\"]");
	private By lb_lecturaQRpaso2 = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/texto_paso\" and @text=\"Herramienta de Compra >\"]");
	private By lb_lecturaQRpaso3 = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/texto_paso\" and @text=\"Herramienta de Compra COMETA >\"]");
	private By lb_lecturaQRpaso4 = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/texto_paso\" and @text=\"Verificación de Mercancía Pedidos Proveedor Directo >\"]");
	private By lb_lecturaQRpaso5 = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/texto_paso\" and @text=\"Leer QR para Preventa o Pedido\"]");
	private By cadenaQR = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/tvw_qr_code");
	private By compoCapturarQR = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/edt_qr_captured");
	private By compoCapturarQRp = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/edt_qr_captured");
	private By firma = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/signature_pad");
	private By lb_mensajeFirmaContinuar = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/intrucciones_btn_continuar");
	private By lb_mensajeObservacionesTienda = By.xpath("/hierarchy/android.widget.FrameLayout[3]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TextView");
	private By lb_mensajeRecuadroFirmaTienda = By.xpath("//android.widget.TextView[@text=\"Haz clic sobre el recuadro destinado para la firma del personal de tienda.\"]");
	private By campoProveedor = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_proveedor");
	private By campoFolioPedido = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_pedido");
	private By campoFechaPedido = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_fecha");
	private By campoCantidadPedido = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_unidades");
	private By bt_LeerQRPreventaOPredido = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/operation_leer_qr_preventa_pedido");
	private By tit_Felicidades = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/vg_titulo");
	private By lb_mensajeFelicitaciones = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/vg_mensaje");
	private By bt_opcionTerminar = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_terminar");
	private By lb_mensajeTiraEntregaPedido = By.xpath("/hierarchy/android.widget.FrameLayout[3]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TextView");
	private By bt_ContinuarMensaje = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_text");
	private By lb_mensajeSeCumplioProcesoEntrega = By.xpath("//android.widget.TextView[@text=\"Selecciona la opción para confirmar que se cumplió con el proceso de entrega.\"]");
	private By chk_SeCumplioProcesoEntrega = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/chk_cumplio_proceso_entrega");
	private By lb_mensajeFirmaProveedor = By.xpath("/hierarchy/android.widget.FrameLayout[3]/android.widget.FrameLayout/android.widget.LinearLayout//android.widget.TextView[@checkable='false' or @checked='false' or @class='android.widget.TextView' or @text=\"PROVEEDOR\r\n"
					+ "Haz clic sobre el recuadro destinado para capturar tu firma.\"]");
	private By CFirmaProveedor = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/img_valor");
	private By lb_mensajeDentroFirma = By.xpath("//android.widget.TextView[@class='android.widget.LinearLayout' or @text=\"Firma dentro del recuadro para el acuerdo de entrega.\"]");
	private By CFprogramado = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/edt_cfisico_programado");
	private By lb_mensajeRemision = By.xpath("//android.widget.TextView[@text=\"Capture el número de remisión, este mismo te lo proporciona el proveedor en el paso previo a la verificación de la mercancía.\"]");
	private By lb_mensajeArticulos = By.xpath("/hierarchy/android.widget.FrameLayout[3]/android.widget.FrameLayout/android.widget.LinearLayout//android.widget.TextView[@class='android.widget.TextView' or @text=\"Se visualiza los artículos a verificar. En caso de haber incidencias, se clasifica en dañado, caduco o faltante.\r\n"
					+ "\r\n" + "Ejemplo: Agrega 1 unidad en Caducos.\r\n" + "\r\n"
					+ "IMPORTANTE: Si en dado caso se detectan incidencias durante la verificación, se solicitará que se verifique un porcentaje mayor del pedido original\"]");
	private By lb_mensajePasosAbrirQR = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/pasos_a_hh_titulo");
	private By lb_mensajePaso1 = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/texto_paso\" and @text=\"Excelencia Operativa >\"]");
	private By lb_mensajePaso2 = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/texto_paso\" and @text=\"Herramienta de Compra >\"]");
	private By lb_mensajePaso3 = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/texto_paso\" and @text=\"Herramienta de Compra COMETA >\"]");
	private By lb_mensajePaso4 = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/texto_paso\" and @text=\"Verificación de Mercancía Pedidos Proveedor Directo >\"]");
	private By lb_mensajePaso5 = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/texto_paso\" and @text=\"Pedidos Precargados\"]");
	private By bt_mensajeSiguiete = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/btn_text");
	private By lb_mensajeContinuar = By.xpath("/hierarchy/android.widget.FrameLayout[3]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TextView");
	private By lb_prueba = By.xpath("/hierarchy/android.widget.FrameLayout[3]/android.widget.FrameLayout/android.widget.LinearLayout//android.widget.TextView[@checkable='false' or @checked='false' or @index=0 or @class='android.widget.TextView' or @text=\"Este es el Pedido Precargado a entregar en la tienda, en donde podrás confirmar las cantidades disponibles para la entrega.\r\n"
					+ "\r\n"
					+ "En dado caso que no cuentes con disponibilidad de algún SKU para su entrega, se tendrá que modificar la cantidad.\"]");
	private By lb_mensajeDespuesdeQR = By.xpath("//android.widget.TextView[@text=\"Una vez escaneado el QR con la Hand Held, nos solicitará capturar el número de remisión, este mismo te lo proporciona el proveedor.\"]");
	private By lb_mensajeNoRemision = By.xpath("//android.widget.TextView[@text=\"Una vez capturado el número de remisión, estaremos realizando la verificación de mercancía del pedido generado.\"]");
	private By lb_mensajeVisualizacionArticulo = By.xpath("/hierarchy/android.widget.FrameLayout[3]/android.widget.FrameLayout/android.widget.LinearLayout//android.widget.TextView[@checkable='false' or @checked='false' or @index=0 or @class='android.widget.TextView' or @text=\"Se visualiza los artículos a verificar. En caso de haber incidencias, se clasifica en dañado, caduco o faltante.\r\n"
					+ "\r\n" + "Ejemplo: Resta 1 pieza y en automático se clasificará como faltante.\r\n" + "\r\n"
					+ "IMPORTANTE: Si en dado caso se detectan incidencias durante la verificación, el sistema te solicitará verificar más artículos del pedido original\"]");
	private By lb_mensajeDespuesMercancia = By.xpath("//android.widget.TextView[@text=\"Después de haber verificado la mercancía, captura la cifra de proveedor. Esta cifra te la comparte directamente el proveedor.\"]");
	private By campoCifraProveedor = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/edt_cifra_proveedor");
	private By lb_mensajeTerminarV = By.xpath("/hierarchy/android.widget.FrameLayout[3]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TextView");
	private By lb_mensajeMuestraResumen = By.xpath("//android.widget.TextView[@text=\"Se muestra el resumen del pedido de los artículos.\"]");
	private String tablaDummiePadre = "//android.widget.LinearLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/items_dummies_list\"]/android.widget.LinearLayout";
	private String tablaDummiePadreTiraEntrega = "//android.widget.LinearLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/lista_productos_dummy\"]/android.widget.LinearLayout";
	private String dummiArticulo = "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_articulo\"]";
	private String dummiDescripcion = "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_descripcion\"]";
	private By lb_mensajeSelecionarProveedor = By.xpath("/hierarchy/android.widget.FrameLayout[3]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TextView");
	private By lb_SinComentarios = By.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"SIN COMENTARIOS\"]"); // "
	private By lb_Otros = By.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"OTROS (ESPECIFICA)\"]");
	private By bt_SiguienteRCPP = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btnResultadosSiguiente");

	/*
	 * Localizadores Ordenes de Compra Recibidas
	 */
	private By bt_OrdenesCompraRecibidas = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/ordenes_compras_recibidas");
	private By titulo_OrdenesCompraRecibidas = By
			.xpath("//android.widget.TextView[@text=\"ÓRDENES DE COMPRA RECIBIDAS\"]");
	private By lb_busquedaPor = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/txtSutitle");
	private By rb_NumeroPedido = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/opt_numero_pedido");
	private By rb_Proveedor = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/opt_fuente_abasto");
	private By lb_NumeroPedidoProveedor = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo_busqueda");
	private By campoBusquedaOrdenesRecivibas = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/edt_busqueda");
	private By bt_busqueda = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_busqueda");
	private By lb_TextoPedidos = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/txtSutitlePedidos");

	private By bt_GeneracionHeramientaCompra = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/operation_generacion_herramienta_compra");
	private By bt_BusquedaPedidoPrecargado = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/operation_buscar_pedidos_precargados");
	/*
	 * localizadores generales
	 */
	private By bt_aceptar = By.id("android:id/button1");
	private By bt_cancelar = By.id("android:id/button2");
	private By Mensaje = By.id("android:id/message");

	/*
	 * Variables Publicas o Globales
	 */
	public static String faValida, faInvalida, crPlaza, crTienda, storePlaceID, val, FA_CCFP, FaEvo, numRemisionCF,
			CCFPP_sku;
	public static String stringRem, pendArt, pendPiezas, realizArt, realizPiezas, devoArt, devoPiezas;
	public static int CantArtInicial_CCFPP, cantArtAntes, cantArtDespues, costoInicial_CCFPP, cantEdAntes,
			cantEdDespues, totalQRs;
	public static float precioVentaCCFPP, costoAntes, costoDespues, montoTotalCambRealiz;
	public static ArrayList<String> listaSkus = new ArrayList<>();
	public static List<String> SkusEvo = new ArrayList<>();
	public static List<String> indicesDescripcion = new ArrayList<>();
	public static List<String> CFMultiplesFAs = new ArrayList<>();
	public static List<String> CFMultiplesNumRem = new ArrayList<>();
	public static List<String> stringArtModifCF = new ArrayList<>();
	public static List<String> codigosQR = new ArrayList<>();
	public static List<String> skusConVentas = new ArrayList<>();
	public static String nameProveedor, namePlaza, remisionEliminar, articuloCapturado1, cantidadProgramada;
	public static String FAInvalidNumber, RemisionNumber;
	public static List<String> codigosQr = new ArrayList<>();
	public static String TotalQR;
	public static String QR, FANumber, remision, NoPedido, NoProveedor;

	/**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase Login
	 * 
	 * @author Alejandro Valdez
	 * @param au            (AppiumUtil): objeto de la clase
	 * @param testCase      (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 */
	public OAD(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
		this.testCase = testCase;
		this.au = au;
		this.baseExecution = baseExecution;
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validar Pantalla Principal de Recepción de Pedido.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */
	public void ingresarModuloHerramientas() {
		testCase.addTextEvidenceCurrentStep("Tap en Botón Excelencia Operativa");
		au.click(bt_ExcOperativa);
		testCase.addScreenShotCurrentStep(au, "testImage");

		testCase.addTextEvidenceCurrentStep("Tap en Botón Herramienta de Compra");
		au.click(bt_HerrCompra);
		testCase.addScreenShotCurrentStep(au, "testImage");

		testCase.addTextEvidenceCurrentStep("Tap en Botón Cometa");
		au.click(bt_Cometa);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar Botón Verificación de Mercancía Pedidos Proveedor Directo.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */
	public boolean opcionVerificacionMercancia() {
		try {
			au.click(bt_VerMercancia);
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
	 * Seleccionar Botón Botón Cambios físicos de productos (Proveedores Directos).
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */
	public boolean opcionCambiosFisicos() {
		try {
			au.click(bt_CambFisicos);
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
	 * ******************************************************************************************
	 * opcionCambiosFisicos_sinEv -
	 * 
	 * @author Alex Aquino
	 * @param N/A
	 * @return N/A
	 */
	public void opcionCambiosFisicos_sinEv() {
		au.click(bt_CambFisicos);
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar Botón Botón Cambios físicos de productos (Proveedores Directos).
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */
	public boolean devoProductos() {
		try {
			au.click(bt_DevoProductos);
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
	 * Validación de Pantalla Cambios Físicos.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */
	public boolean pantallaCambiosFisicos() {
		try {

			boolean Regresar = au.driver.findElement(bt_Regresar).isEnabled();
			if (Regresar == false) {
				testCase.addTextEvidenceCurrentStep("El botón para Regresar no esta habilitado");
			}

			boolean TitCambiosFisicos = au.driver.findElement(Title_CambiosFisicos).isEnabled();
			if (TitCambiosFisicos == false) {
				testCase.addTextEvidenceCurrentStep("El Titulo de Cambios Fisicos no se encuentra visible.");
			}

			boolean PregFrecuentes = au.driver.findElement(bt_PregFrecuentes).isEnabled();
			if (PregFrecuentes == false) {
				testCase.addTextEvidenceCurrentStep("El botón de Preguntas Frecuenes no esta habilitado");
			}

			boolean Tutoriales = au.driver.findElement(bt_Tutoriales).isEnabled();
			if (Tutoriales == false) {
				testCase.addTextEvidenceCurrentStep("El botón de Tutotiales no esta habilitado");
			}

			boolean CambiosFisicos = au.driver.findElement(bt_CapCambiosFisicos).isEnabled();
			if (CambiosFisicos == false) {
				testCase.addTextEvidenceCurrentStep("El botón de Cambios Físicos no esta habilitado");
			}

			boolean ConsultCambiosFisicos = au.driver.findElement(bt_ConsultCambiosFisicos).isEnabled();
			if (ConsultCambiosFisicos == false) {
				testCase.addTextEvidenceCurrentStep("El botón de Consulta Cambios Físicos no esta habilitado");
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("La pantalla de Cambios Físicos no se muestra de manera correcta.");
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar Botón Capturar cambios físicos.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean opcionCapturarCambiosFisicos() {
		try {
			au.click(bt_CapCambiosFisicos);
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
	 * Seleccionar Botón Consulta cambios físicos.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean opcionConsultaCambiosFisicos() {
		try {
			au.click(bt_ConsultCambiosFisicos);
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
	 * Seleccionar Radio Button Número de Remisión y validar su selección.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean opcionNumeroRemision() {
		try {
			au.click(rb_NumeroDeRemision);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("El botón no se encuentra disponible");
			return false;
		}
	}

	public boolean validarSeleccionRbNumeroRemision() {
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
			wait.until(ExpectedConditions.presenceOfElementLocated(rb_NumeroDeRemision));
			boolean NumeroDeRemision = au.driver.findElement(rb_NumeroDeRemision).isSelected();
			if (NumeroDeRemision == true) {
				testCase.addTextEvidenceCurrentStep("El botón Fuente de Abastos Proveedor no esta seleccionado.");
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("El Radio Button no se encuentra seleccionado.");
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar Radio Button Fuente de Abasto Proveedor y validar su selección.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean opcionFuenteAbastoProveedor() {
		try {
			au.click(rb_FuenteDeAbastosProveedor);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("El botón no se encuentra disponible");
			return false;
		}
	}

	public boolean validarSeleccionRbFA() {
		try {
			boolean FuenteDeAbastosProveedor = au.driver.findElement(rb_FuenteDeAbastosProveedor).isSelected();
			if (FuenteDeAbastosProveedor == true) {
				testCase.addTextEvidenceCurrentStep("El botón Fuente de Abastos Proveedor no esta seleccionado.");
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("El Radio Button no se encuentra seleccionado.");
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validación de Pantalla Cambios Físicos.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean pantallaCapturaCambiosFisicos() {
		try {

			boolean Regresar = au.driver.findElement(bt_Regresar).isEnabled();
			if (Regresar == false) {
				testCase.addTextEvidenceCurrentStep("El botón para Regresar no esta habilitado");
			}

			boolean TitCambiosFisicos = au.driver.findElement(Title_CambiosFisicos).isEnabled();
			if (TitCambiosFisicos == false) {
				testCase.addTextEvidenceCurrentStep("El Titulo de Cambios Fisicos no se encuentra visible.");
			}

			boolean PregFrecuentes = au.driver.findElement(bt_PregFrecuentes).isEnabled();
			if (PregFrecuentes == false) {
				testCase.addTextEvidenceCurrentStep("El botón de Preguntas Frecuenes no esta habilitado");
			}

			boolean Tutoriales = au.driver.findElement(bt_Tutoriales).isEnabled();
			if (Tutoriales == false) {
				testCase.addTextEvidenceCurrentStep("El botón de Tutotiales no esta habilitado");
			}

			boolean CapturaFuenteAbasto = au.driver.findElement(tb_CapturaFuenteAbasto).isEnabled();
			if (CapturaFuenteAbasto == false) {
				testCase.addTextEvidenceCurrentStep("El Cuadro de Texto para la captura de FA no esta habilitado");
			}

			boolean Buscar = au.driver.findElement(bt_Buscar).isEnabled();
			if (Buscar == false) {
				testCase.addTextEvidenceCurrentStep("El botón de Búsqueda no esta habilitado");
			}

			boolean TbCapturaSku = au.driver.findElement(tb_CapturaSku).isDisplayed();
			if (TbCapturaSku == false) {
				testCase.addTextEvidenceCurrentStep("El Cuadro de Texto para la captura de Sku's no esta habilitado");
			}

			boolean CapturaSku = au.driver.findElement(bt_CapturaSku).isEnabled();
			if (CapturaSku == false) {
				testCase.addTextEvidenceCurrentStep("El botón de Captura de Códigos no esta habilitado");
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep(
					"La pantalla de Captura de Cambios Físicos no se muestra de manera correcta.");
			return false;
		}
	}

	/******************************************************************************************/
	/**
	 * Capturar Fuente de Abasto Válida y Validar su Captura.
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 13/09/2024
	 */

	public boolean opcionCapturarFAValida() {
		try {

			au.sendKeys(tb_CapturaFuenteAbasto, FaEvo);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * opcionCapturarFAValidaNube - Obtiene Skus de una FA valida en Nube Evolucion
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param CantSkus - cantidad minima de skus a obtener
	 * 
	 * @param CantInv - cantidad minima a tener en Inv por Sku
	 * 
	 * @return N/A
	 */

	public boolean opcionCapturarFAValidaNubeEvo() {
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(100));
			wait.until(ExpectedConditions.presenceOfElementLocated(bt_Buscar));
			au.sendKeys(tb_CapturaFuenteAbasto, FaEvo);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("El campo no se encuentra disponible");
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * opcionCapturarFAValidaNubeEvo_CCFPP - Obtiene Skus de una FA valida en Nube
	 * Evolucion
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param CantSkus - cantidad minima de skus a obtener
	 * 
	 * @param CantInv - cantidad minima a tener en Inv por Sku
	 * 
	 * @return N/A
	 */

	public boolean opcionCapturarFAValidaNubeEvo_CCFPP() {
		try {
			au.sendKeys(tb_CapFAoNumRemision, FaEvo);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("El campo no se encuentra disponible");
			return false;
		}
	}

	/**
	 * Metodo
	 ****************************************************************************/
	/**
	 * opcionCapturarFAValidaInv- Capturar Fuente de Abasto Válida y Validar su
	 * Captura.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean opcionCapturarFAValidaInventario() {
		try {
			obtenerSkusInventarioActual(3, 3);
			au.sendKeys(tb_CapturaFuenteAbasto, faValida);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("El campo no se encuentra disponible");
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Capturar Fuente de Abasto Invalida y Validar su Captura.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean opcionCapturarFAInValidaPantallaConsultaCambiosFisicos() {
		try {
			obtenerFuenteAbastoInvalida();
			au.sendKeys(tb_CapFAoNumRemision, faInvalida);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("El campo no se encuentra disponible");
			return false;
		}
	}

	public boolean validarCapturarFAInvalida() {
		try {
			String camFA = au.driver.findElement(tb_CapFAoNumRemision).getText();
			if (camFA.isEmpty()) {
				testCase.addTextEvidenceCurrentStep("El campo para ingresar número de proveedor está vacío.");
				return false;
			} else {
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			}
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error al capturar la FA: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Capturar Fuente de Abasto Válida y Validar su Captura.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean opcionCapturarFAValidaConsultaCambiosFisicos() {
		try {
			obtenerSkusRelacionadosFaValida(3);
			au.sendKeys(tb_CapFAoNumRemision, faValida);
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
	 * Seleccionar Botón Buscar.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean opcionBuscar() {
		try {
			au.click(bt_Buscar);
			// ---------------------------
			boolean aviso;
			try {
				aviso = au.driver.findElement(By.xpath(
						"//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"Ya se han realizado cambios físicos para este proveedor el día de hoy. ¿Desea continuar?\"]"))
						.isDisplayed();
			} catch (Exception e) {
				aviso = false;
			}
			if (aviso == true) {
				au.click(By.id("android:id/button1"));
				au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
			}
			// ---------------------------
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
	 * Seleccionar Botón Buscar.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean btnIngresoSku() {
		try {
			au.click(bt_IngresaSku);
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
	 * Capturar Manualmente Sku´s y validar su captura.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean capturarSkus(int cantSkus) {
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(100));
			wait.until(ExpectedConditions.presenceOfElementLocated(lb_Proveedor));
			for (int i = 0; i < listaSkus.size(); i++) {
				au.driver.findElement(tb_CapturaSku).sendKeys(listaSkus.get(i));
				btnIngresoSku();
			}
			;
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("El botón no se encuentra disponible");
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * capturarSkusNubeEvo - Obtiene Skus de una FA valida en Nube Evolucion
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param CantSkus - cantidad minima de skus a obtener
	 * 
	 * @param CantInv - cantidad minima a tener en Inv por Sku
	 * 
	 * @return N/A
	 */
	public boolean capturarSkusNubeEvo(int cantSkus) {
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(100));
			wait.until(ExpectedConditions.presenceOfElementLocated(lb_Proveedor));
			for (int i = 0; i < SkusEvo.size(); i++) {
				au.driver.findElement(tb_CapturaSku).sendKeys(SkusEvo.get(i));
				btnIngresoSku();
			}
			;
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("El botón no se encuentra disponible");
			return false;
		}
	}

	public boolean validarCapturarSku() {
		try {
			String camFA = au.driver.findElement(tb_CapturaSku).getText();
			if (camFA.isEmpty()) {
				testCase.addTextEvidenceCurrentStep("El campo para ingresar Sku´s está vacío.");
				return false;
			} else {
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			}
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error al capturar el Sku: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validación de pantalla Consulta Cambios Físicos.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean pantallaConsultaCambiosFisicos() {
		try {

			boolean Regresar = au.driver.findElement(bt_Regresar).isEnabled();
			if (Regresar == false) {
				testCase.addTextEvidenceCurrentStep("El botón para Regresar no esta habilitado");
			}

			boolean TitCambiosFisicos = au.driver.findElement(Title_CambiosFisicos).isEnabled();
			if (TitCambiosFisicos == false) {
				testCase.addTextEvidenceCurrentStep("El Titulo de Cambios Fisicos no se encuentra visible.");
			}

			boolean PregFrecuentes = au.driver.findElement(bt_PregFrecuentes).isEnabled();
			if (PregFrecuentes == false) {
				testCase.addTextEvidenceCurrentStep("El botón de Preguntas Frecuenes no esta habilitado");
			}

			boolean Tutoriales = au.driver.findElement(bt_Tutoriales).isEnabled();
			if (Tutoriales == false) {
				testCase.addTextEvidenceCurrentStep("El botón de Tutotiales no esta habilitado");
			}

			boolean lbBuscarPor = au.driver.findElement(lb_BuscarPor).isEnabled();
			if (lbBuscarPor == false) {
				testCase.addTextEvidenceCurrentStep("El botón de BuscarPor no esta habilitado");
			}

			boolean FuenteDeAbastosProveedor = au.driver.findElement(rb_FuenteDeAbastosProveedor).isEnabled();
			if (FuenteDeAbastosProveedor == false) {
				testCase.addTextEvidenceCurrentStep("El radio botón de Fuente De Abastos Proveedo no esta habilitado");
			}

			boolean NumeroDeRemision = au.driver.findElement(rb_NumeroDeRemision).isEnabled();
			if (NumeroDeRemision == false) {
				testCase.addTextEvidenceCurrentStep(
						"El radio botón de Numero De Remision proveedor no esta habilitado");
			}

			boolean FAyNumRemision = au.driver.findElement(lb_FAyNumRemision).isEnabled();
			if (FAyNumRemision == false) {
				testCase.addTextEvidenceCurrentStep(
						"El radio botón de Numero De Remision proveedor no esta habilitado");
			}

			boolean CapFAoNumRemision = au.driver.findElement(tb_CapFAoNumRemision).isDisplayed();
			if (CapFAoNumRemision == false) {
				testCase.addTextEvidenceCurrentStep(
						"El radio botón de Numero De Remision proveedor no esta habilitado");
			}

			boolean btBuscar = au.driver.findElement(bt_BuscarProveedorRemision).isEnabled();
			if (btBuscar == false) {
				testCase.addTextEvidenceCurrentStep("El botón de Buscar Proveedor Remision no esta habilitado");
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep(
					"La pantalla de Consulta Cambios Físicos no se muestra de manera correcta.");
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar botón Buscar en la pantalla de Consulta Cambios Fisicos.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean opcionBuscarConsultaCambiosFisicos() {
		try {
			au.click(bt_BuscarProveedorRemision);
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
	 * Validar Mensaje de Warning No se ha seleccionado una casilla.Favor de
	 * seleccionar la casilla correspondiente para visualizar los cambios fisicos
	 * correspondientes".
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean mensajeNoSeSeleccionoCasilla() {
		try {
			String mensajeMostrado = au.driver.findElement(Warnings).getText();
			String txtWarning = "No se ha seleccionado una casilla. Favor de seleccionar la casilla correspondiente para visualizar los cambios físicos correspondientes.";

			if (mensajeMostrado.equals(txtWarning)) {
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
	 * Validar Mensaje de Warning "No se a capturado un numero de proveedor. Favor
	 * de capturar un numero de proveedor para visualizar los cambios fisicos
	 * pendientes".
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean mensajeNoSeCapturoNumProveedor() {
		try {
			String mensajeMostrado = au.driver.findElement(Warnings).getText();
			String txtWarning = "No se ha capturado un número de proveedor. Favor de capturar un número de proveedor para visualizar los cambios físicos pendientes.";

			if (mensajeMostrado.equals(txtWarning)) {
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
	 * Validar Mensaje de Warning "No se a capturado un numero de remision. Favor de
	 * capturar un numero de remision para visualizar los cambios fisicos
	 * pendientes".
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean mensajeNoSeCapturoNumRemision() {
		try {
			String mensajeMostrado = au.driver.findElement(Warnings).getText();
			String txtWarning = "El campo de remisión se encuentra vacío favor de capturar el número de remisión que deseas buscar.";

			if (mensajeMostrado.equals(txtWarning)) {
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
	 * Validar Mensaje de Warning "Fuente de abastos no encontrada. Favor de validar
	 * que la fuente de abastos del proveedor sea la correcta".
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean mensajeFANoEncontrada() {
		try {
			String mensajeMostrado = au.driver.findElement(Warnings).getText();
			String txtWarning = "Fuente de Abasto no encontrada. Favor de validar que la fuente de abasto del proveedor sea la correcta.";

			if (mensajeMostrado.equals(txtWarning)) {
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
	 * Validación de Posterior de Pantalla Cambios Físicos.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean pantallaPosteriorCapturaCambiosFisicos() {
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(50));
			wait.until(ExpectedConditions.presenceOfElementLocated(lb_Proveedor));
			String lbFuenteAbasto = au.driver.findElement(lb_FuenteAbasto).getText();
			if (lbFuenteAbasto.contains("Fuente de Abasto:") != true) {
				testCase.addTextEvidenceCurrentStep("No se encontro texto 'El CÓDIGO QR ESTARÁ DISPONIBLE'");
			}

			String lbProveedor = au.driver.findElement(lb_Proveedor).getText();
			if (lbProveedor.contains("Proveedor:") != true) {
				testCase.addTextEvidenceCurrentStep("No se encontro texto 'El CÓDIGO QR ESTARÁ DISPONIBLE'");
			}

			boolean TbCapturaSku = au.driver.findElement(tb_CapturaSku).isDisplayed();
			if (TbCapturaSku == false) {
				testCase.addTextEvidenceCurrentStep("El Cuadro de Texto para la captura de Sku's no esta habilitado");
			}

			boolean CapturaSku = au.driver.findElement(bt_CapturaSku).isEnabled();
			if (CapturaSku == false) {
				testCase.addTextEvidenceCurrentStep("El botón de Captura de Códigos no esta habilitado");
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep(
					"La pantalla Posterior de Captura de Cambios Físicos no se muestra de manera correcta.");
			return false;
		}

	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validación de pantalla al tener Articulos Agregados y diferentes Componentes
	 * que la componen.
	 * 
	 * @author Alex Aquino
	 * @param N/A
	 * @return True or False
	 */

	public boolean pantallaArticulosAgregados() {
		try {
			String xpathPadre = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/rvwListaCambiosFisicos\"]/android.view.ViewGroup";
			String xpathDesc = "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvwCambioFisicoDescription\" and @index=\"0\"]";
			List<String> indicesDescripcion = new ArrayList<>();

			// Recolectar todas las descripciones
			for (int i = 2; i <= 3; i++) {
				String xpathDescripcion = xpathPadre + "[" + i + "]" + xpathDesc;
				String descripcionActual = au.driver.findElement(By.xpath(xpathDescripcion)).getText();
				indicesDescripcion.add(descripcionActual);
			}
			// Verificar consecutividad
			for (int l = 0; l < indicesDescripcion.size(); l++) {
				boolean descripcionCorrecta = indicesDescripcion.get(l).contains((l + 1 + "-"));
				if (!descripcionCorrecta) {
					return false;
				}
			}

			// valida elementios por articulo
			for (int i = 1; i <= 2; i++) {
				au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
				boolean CSku = au.driver.findElement(By.xpath(xpathPadre + "[" + (i + 1) + "]"
						+ "//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edtCambioFisicoSku\"]"))
						.isDisplayed();
				if (CSku == false) {
					testCase.addTextEvidenceCurrentStep("El botón de Captura de Códigos no esta habilitado");
					return false;
				}
				boolean eliminarSku = au.driver.findElement(By.xpath(
						"(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnDeleteCambioFisico\"])"
								+ "[" + i + "]"))
						.isEnabled();
				if (eliminarSku == false) {
					testCase.addTextEvidenceCurrentStep("El botón de Captura de Códigos no esta habilitado");
					return false;
				}
				boolean restarSku = au.driver.findElement(By.xpath(
						"(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnMinusCambioFisico\"])"
								+ "[" + i + "]"))
						.isEnabled();
				if (restarSku == false) {
					testCase.addTextEvidenceCurrentStep("El botón de Captura de Códigos no esta habilitado");
					return false;
				}
				boolean sumarSku = au.driver.findElement(By.xpath(
						"(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnAddCambioFisico\"])"
								+ "[" + i + "]"))
						.isEnabled();
				if (sumarSku == false) {
					testCase.addTextEvidenceCurrentStep("El botón de Captura de Códigos no esta habilitado");
					return false;
				}
				boolean cFisico = au.driver.findElement(By.xpath(
						"(//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edt_cfisico_realizado\"])"
								+ "[" + i + "]"))
						.isEnabled();
				if (cFisico == false) {
					testCase.addTextEvidenceCurrentStep("No se encontro el campo de Texto Cambio Fisico Realizado");
					return false;
				}

				String cantidadCFisicoRealizado = au.driver.findElement(By.xpath(
						"(//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edt_cfisico_realizado\"])"
								+ "[" + i + "]"))
						.getText();
				if (!cantidadCFisicoRealizado.equalsIgnoreCase("1")) {
					testCase.addTextEvidenceCurrentStep(
							"La cantidad mostrada en el campo Cambios fisicos realizados es diferente de '1' " + i);
					return false;
				}
				boolean cProgramado = au.driver.findElement(By.xpath(
						"(//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edt_cfisico_programado\"])"
								+ "[" + i + "]"))
						.isEnabled();
				if (cProgramado == false) {
					testCase.addTextEvidenceCurrentStep("No se encontro el campo de Texto Cambio Fisico Programado");
					return false;
				}
			}

			// Validacion de Art Pend, Art con cambios, Piezas Pendientes y Piezas con
			// cambios
			au.scrollToElement(btn_Siguiente);
			boolean artPend = au.driver.findElement(lb_ArtPendientes).isDisplayed();
			if (artPend != true) {
				testCase.addTextEvidenceCurrentStep("No se encontro la etiqueta Art. Pendientes");
			}
			String cantArtPend = au.driver.findElement(contadorArtPendientes).getText();
			if (cantArtPend == "0") {
				testCase.addTextEvidenceCurrentStep(
						"El contador de Art. Pendientes No conicide con la cantidad de articulos agregados");
			}
			boolean artConCamb = au.driver.findElement(lb_ArtConCambios).isDisplayed();
			if (artConCamb != true) {
				testCase.addTextEvidenceCurrentStep("No se encontro la etiqueta Art. con Cambios");
			}
			String cantArtConCamb = au.driver.findElement(contadorArtConCambios).getText();
			if (cantArtConCamb == "0") {
				testCase.addTextEvidenceCurrentStep(
						"El contador de Art. con Cambios No conicide con la cantidad de articulos agregados");
			}
			boolean piezasPend = au.driver.findElement(lb_PiezasPendientes).isDisplayed();
			if (piezasPend != true) {
				testCase.addTextEvidenceCurrentStep("No se encontro la etiqueta Piezas pendientes");
			}
			String cantPiezasPend = au.driver.findElement(contadorPiezasPendientes).getText();
			if (cantPiezasPend == "0") {
				testCase.addTextEvidenceCurrentStep(
						"El contador de Peizas Pendientes No conicide con la cantidad de articulos agregados");
			}
			boolean piezasConCamb = au.driver.findElement(lb_PiezasConCambios).isDisplayed();
			if (piezasConCamb != true) {
				testCase.addTextEvidenceCurrentStep("No se encontro la etiqueta Piezas con Cambios");
			}
			String cantPiezasConCamb = au.driver.findElement(contadorPiezasConCambios).getText();
			if (cantPiezasConCamb == "0") {
				testCase.addTextEvidenceCurrentStep(
						"El contador de Piezas con Cambios No conicide con la cantidad de articulos agregados");
			}
			testCase.addTextEvidenceCurrentStep(
					"Se valida que los articulos incluidos muestran un indices se muestra de manera consecutiva e incremental");
			testCase.addTextEvidenceCurrentStep(
					"Se valida que los articulos agregados cuentan con los elementos de: Descripcion, ID contador, SKU, Boton Eliminar, Boton Restar, Boton Aumentar, Campo C.Fisicos Realizados y C.Fisicos Programados");
			testCase.addTextEvidenceCurrentStep(
					"Se valida que se muestran las Etiquetas: Art.Piendientes, Art. Con Cambios, Piezas Pendientes y Piezas con Cambios con sus respectivos contadores");
			au.driver.findElement(AppiumBy.androidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"Fuente de Abasto:\"))"));
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.scrollToElement(btn_Siguiente);
			testCase.addScreenShotCurrentStep(au, "textImage");
			return true;
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep(
					"La pantalla Posterior de Captura de Cambios Físicos no se muestra de manera correcta.");
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * Cambia manualmente la cantidad de un articulo y valida que se aplique el
	 * cambio.
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 */
	public boolean CambiayValidaCantidadManualmente() {
		au.driver.findElement(AppiumBy.androidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"Fuente de Abasto:\"))"));
		// Actualiza la cantidad de manera manual
		au.click(lb_CantidadSku);
		au.sendKeys(editIngresarCantManual, "2");
		au.click(btnConfirmarCantManual);
		// Valida que se actualizo la cant fue actuakizada
		au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		String cantidad = au.driver.findElement(lb_CantidadSku).getText();
		if (!cantidad.equalsIgnoreCase("2")) {
			testCase.addTextEvidenceCurrentStep("Error, La cantidad no se actualizo correctamente");
			return false;
		} else {
			testCase.addTextEvidenceCurrentStep(
					"Se modifica el valor de la canidad manualmente y se valida que el campo se llena con la cantidad elegida");
			testCase.addScreenShotCurrentStep(au, "testImage");
		}
		return true;
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * Seleccioan el campo cantidad de un articulo.
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void SeleccionaCampoCantidad() {
		au.driver.findElement(AppiumBy.androidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"Fuente de Abasto:\"))"));
		// Actualiza la cantidad de manera manual
		au.click(lb_CantidadSku);
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * Valida se muestra el popUp, Ingrese una cantidad al modificar una cantidad
	 * manualmente
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 */
	public boolean PopUpIngreseUnaCantidad() {
		boolean popUp = au.driver.findElement(popUpIngresaUnaCantidad).isDisplayed();
		boolean cancelar = au.driver.findElement(btnCancelarCantManual).isDisplayed();
		boolean confirmar = au.driver.findElement(btnConfirmarCantManual).isDisplayed();
		if (popUp == false || cancelar == false || confirmar == false) {
			return false;
		} else {
			testCase.addTextEvidenceCurrentStep(
					"Se muestra PopUp 'Ingrese una cantidad' junto a los botones Cancelar y Confirmar");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * Se ingresa '0' como cantidad elegida y se valida que esta queda ingresada en
	 * el campo
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 */
	public boolean CambiaACeroCantidadDeArticuloYValidaCambio() {
		au.sendKeys(editIngresarCantManual, "0");
		au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		String cantidad = au.driver.findElement(editIngresarCantManual).getText();
		if (!cantidad.equalsIgnoreCase("0")) {
			System.out.println("Nel");
			return false;
		} else {
			testCase.addTextEvidenceCurrentStep(
					"Se ingresa '0' como cantidad elegida y se valida que esta queda ingresada en el campo");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * Se ingresa '0' como cantidad elegida y se valida que esta queda ingresada en
	 * el campo
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 */
	public void SeleccionarConfirmarPopUpIngCantidad() {
		au.click(btnConfirmarCantManual);
		testCase.addTextEvidenceCurrentStep("Se hace tap en la opcion: Confirmar");
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * Valida existencia de mensaje - No puede especificar menos de una unidad para
	 * cambio fisico
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 */
	public boolean ValidaMsjNoEspecificarMenosDeUnaUnidad() {
		boolean mensaje = au.driver.findElement(mensajeNoMenosDeUnaUnidad).isDisplayed();
		if (mensaje != true) {
			return false;
		} else {
			testCase.addTextEvidenceCurrentStep(
					"Se valida existencia de Mensaje: No puede especificar menos de una unidad");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * Valida existencia de mensaje - No puede especificar menos de una unidad para
	 * cambio fisico
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 */
	public void SeleccionaConfirmarMsjNoMenosDeUnaUnidad() {
		au.click(btnAceptarMensajeNoMenosDeUnaUnidad);
		testCase.addTextEvidenceCurrentStep("Se selecciona la opcion de aceptar");
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * Valida existencia de mensaje - No puede especificar menos de una unidad para
	 * cambio fisico
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 */
	public boolean ValidaPantallaCambiosFisicosConArticulos() {
		String xpathPadre = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/rvwListaCambiosFisicos\"]/android.view.ViewGroup";
		String lbFuenteAbasto = au.driver.findElement(lb_FuenteAbasto).getText();
		if (lbFuenteAbasto.contains("Fuente de Abasto:") != true) {
			testCase.addTextEvidenceCurrentStep("No se encontro texto 'El CÓDIGO QR ESTARÁ DISPONIBLE'");
			return false;
		} else {
			// valida elementos por articulo
			for (int i = 1; i <= 2; i++) {
				au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
				boolean CSku = au.driver.findElement(By.xpath(xpathPadre + "[" + (i + 1) + "]"
						+ "//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edtCambioFisicoSku\"]"))
						.isDisplayed();
				if (CSku == false) {
					testCase.addTextEvidenceCurrentStep("El botón de Captura de Códigos no esta habilitado");
					return false;
				}
				boolean eliminarSku = au.driver.findElement(By.xpath(
						"(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnDeleteCambioFisico\"])"
								+ "[" + i + "]"))
						.isEnabled();
				if (eliminarSku == false) {
					testCase.addTextEvidenceCurrentStep("El botón de Captura de Códigos no esta habilitado");
					return false;
				}
				boolean restarSku = au.driver.findElement(By.xpath(
						"(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnMinusCambioFisico\"])"
								+ "[" + i + "]"))
						.isEnabled();
				if (restarSku == false) {
					testCase.addTextEvidenceCurrentStep("El botón de Captura de Códigos no esta habilitado");
					return false;
				}
				boolean sumarSku = au.driver.findElement(By.xpath(
						"(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnAddCambioFisico\"])"
								+ "[" + i + "]"))
						.isEnabled();
				if (sumarSku == false) {
					testCase.addTextEvidenceCurrentStep("El botón de Captura de Códigos no esta habilitado");
					return false;
				}
				boolean cFisico = au.driver.findElement(By.xpath(
						"(//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edt_cfisico_realizado\"])"
								+ "[" + i + "]"))
						.isEnabled();
				if (cFisico == false) {
					testCase.addTextEvidenceCurrentStep("No se encontro el campo de Texto Cambio Fisico Realizado");
					return false;
				}

				String cantidadCFisicoRealizado = au.driver.findElement(By.xpath(
						"(//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edt_cfisico_realizado\"])"
								+ "[" + i + "]"))
						.getText();
				if (cantidadCFisicoRealizado.equalsIgnoreCase("0")) {
					testCase.addTextEvidenceCurrentStep(
							"La cantidad mostrada en el campo Cambios fisicos realizados es diferente de '1' " + i);
					return false;
				}
				boolean cProgramado = au.driver.findElement(By.xpath(
						"(//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edt_cfisico_programado\"])"
								+ "[" + i + "]"))
						.isEnabled();
				if (cProgramado == false) {
					testCase.addTextEvidenceCurrentStep("No se encontro el campo de Texto Cambio Fisico Programado");
					return false;
				}
			}
			testCase.addTextEvidenceCurrentStep("Se valida pantalla Cambios Fisicos con FA y SKU's capturados");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * incrementarBtnMas - No puede especificar menos de una unidad para cambio
	 * fisico
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 */
	public void incrementarBtnMas(int numArticulos, int numClicks) { // numArticulos = 1-2, numClicks = X
		for (int i = 1; i <= numArticulos && i < 3; i++) {
			for (int j = 1; j <= numClicks; j++) {
				WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(10));
				WebElement incrementar = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
						"(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnAddCambioFisico\"])"
								+ "[" + i + "]")));
				incrementar.click();
			}
		}
		testCase.addTextEvidenceCurrentStep("Se hace click en boton aumentar '+'");
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	// =======================================Consultas
	// SQL========================================================//
	// ============================================================================================================//

	/*
	 * Metodo
	 ******************************************************************************************
	 * Obtener Fuente de Abasto Válida.
	 * 
	 * @author Alex Aquino - Alejandro Valdez
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 */
	public boolean obtenerFuenteAbastoValida() {
		try {
			Connection conn = DriverManager.getConnection(GlobalVariables.urlSatelitesPV10_17,
					GlobalVariables.userPV10_17, GlobalVariables.passwordPV10_17);
			String query1 = "DROP TABLE IF EXISTS #FAvalida";
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(query1);
			String query2 = "CREATE TABLE #FAvalida ( " + "	[SUPPLIER_NUM] [varchar](10) NOT NULL, "
					+ "	[SUPPLIER_NAME] [varchar](100) NOT NULL, " + "	[STATUS] [bit] NOT NULL, "
					+ "	[Can_Create] [bit] NOT NULL, " + "	[Can_Receive] [bit] NOT NULL " + " )";
			stmt.executeUpdate(query2);
			String query3 = "INSERT INTO #FAvalida EXEC Satelites.hc.GET_SUPPLIER_LIST_MOB";
			stmt.executeUpdate(query3);
			String FaVa = "Select top 1 SUPPLIER_NUM from #FAvalida where SUPPLIER_NUM not LIKE'9999999%' and SUPPLIER_NUM != '1940' order by NEWID()";
			SQLResult res = new SQLResult(conn, FaVa);
			faValida = res.getFirstData().toString();
			return true;
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("No se pudo obtener una fuente de abasto valida, favor de validar\n");
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * obtenerFuenteAbastoInvalida - Obtiene una fuente de abasto Invalida
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 * 
	 * @author Alex Aquino
	 */
	public boolean obtenerFuenteAbastoInvalida() {
		try {
			Connection conn = DriverManager.getConnection(GlobalVariables.urlSatelitesPV10_17,
					GlobalVariables.userPV10_17, GlobalVariables.passwordPV10_17);
			String query1 = "DROP TABLE IF EXISTS #FAinvalida";
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(query1);
			String query2 = "CREATE TABLE #FAinvalida ( " + "	[SUPPLIER_NUM] [varchar](10) NOT NULL, "
					+ "	[SUPPLIER_NAME] [varchar](100) NOT NULL, " + "	[STATUS] [bit] NOT NULL, "
					+ "	[Can_Create] [bit] NOT NULL, " + "	[Can_Receive] [bit] NOT NULL " + " )";
			stmt.executeUpdate(query2);
			String query3 = "INSERT INTO #FAinvalida EXEC Satelites.hc.GET_SUPPLIER_LIST_MOB";
			stmt.executeUpdate(query3);
			String FaVa = "Select top 1 SUPPLIER_NUM from #FAinvalida where SUPPLIER_NUM not LIKE '9999999%'";
			SQLResult res = new SQLResult(conn, FaVa);
			faInvalida = String.valueOf(Integer.parseInt(res.getFirstData().toString()) - 1);
			return true;
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("No se pudo obtener una fuente de abasto invalida, favor de validar\n");
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * Obtener Sku´s relacionados de la Fuente de Abasto.
	 * 
	 * @author Alex Aquino
	 * 
	 * @param cantSkus - Cantidad de Sku´s a Ingresar en el listado.
	 * 
	 * @return True or False
	 */

	public boolean obtenerSkusRelacionadosFaValida(int cantidadSKus) {
		try {
			Connection conn = DriverManager.getConnection(GlobalVariables.urlSatelitesPV10_17,
					GlobalVariables.userPV10_17, GlobalVariables.passwordPV10_17);
			String query1 = "DROP TABLE IF EXISTS  #ReturnTable";
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(query1);
			String query2 = "CREATE TABLE #ReturnTable ( " + "	[SKU] [varchar](60) NOT NULL, "
					+ "	[Nombre] [nvarchar](100) NOT NULL, " + "	[PrecioVenta] [decimal](16, 4) NOT NULL, "
					+ "	[PrecioCosto] [decimal](16, 4) NOT NULL, "
					+ "	[InventarioOperativo] [decimal](16, 2) NOT NULL, "
					+ "	[InventarioTeorico] [decimal](16, 2) NOT NULL, " + "	[MA_EXH] [numeric](16, 4) NULL, "
					+ "	[Ma_Exh_Prm] [numeric](16, 4) NULL, " + "	[MA_EXH_INI] [date] NULL, "
					+ "	[MA_EXH_END] [date] NULL, " + "	[MA_STK_NEW] [numeric](16, 4) NULL, "
					+ "	[MA_NEW_INI] [date] NULL, " + "	[MA_NEW_END] [date] NULL, " + "	[MA_FS] [numeric](16, 4) NULL, "
					+ "	[MA_FS_PROM] [numeric](16, 4) NULL, " + "	[MA_FS_INI] [date] NULL, "
					+ "	[MA_FS_END] [date] NULL, " + "	[MA_IM] [numeric](16, 4) NULL, "
					+ "	[HFP] [varchar](5) NULL, " + "	[SUMA_HCU] [decimal](38, 2) NULL, " + "	[DIAS_HCU] [int] NULL, "
					+ "	[SUMA_HCI] [decimal](38, 2) NULL, " + "	[DIAS_HCI] [int] NULL, "
					+ "	[DESVEST] [float] NULL, " + "	[MA_VTA_PRD] [numeric](16, 4) NOT NULL, "
					+ "	[MA_DECIMAL] [bit] NOT NULL, " + "	[nombre_proveedor] [varchar](100) NULL, "
					+ "	[maLinSur] [int] NULL " + ")";
			stmt.executeUpdate(query2);
			String query3 = "INSERT INTO #ReturnTable EXEC Satelites.hc.Get_ItemsBySupplier_MOB " + faValida;
			stmt.executeUpdate(query3);
			String skuRelacionados = "Select top " + cantidadSKus + " SKU from #ReturnTable where InventarioTeorico>0";
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
					testCase.addTextEvidenceCurrentStep(
							"Se muestran los SKUs Relacionados y FA valida usada en el pedido ");
					testCase.addQueryEvidenceCurrentStep(res);
				} else {
					obtenerFuenteAbastoValida();
					obtenerSkusRelacionadosFaValida(cantidadSKus);
					encontrados = true;
				}
			}
			return true;
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error al obtener los SKUs Relacionados a la Fuente de Abasto");
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * obtenerSkusInventarioActual - Obtener Sku´s relacionados de la Fuente de
	 * Abasto que tengan un inventario mayor a X
	 * 
	 * @author Alex Aquino
	 * 
	 * @param cantSkus - Cantidad de Sku´s a obtener para el caso.
	 * 
	 * @param inventario - cantidad minima que debe tener cada SKU obtendio
	 * 
	 * @return True or False
	 */

	public boolean obtenerSkusInventarioActual(int cantidadSKus, int inventario) {
		try {
			Connection conn = DriverManager.getConnection(GlobalVariables.urlSatelitesPV10_17,
					GlobalVariables.userPV10_17, GlobalVariables.passwordPV10_17);
			String query1 = "DROP TABLE IF EXISTS  #ReturnTable";
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(query1);
			String query2 = "CREATE TABLE #ReturnTable ( " + "	[SKU] [varchar](60) NOT NULL, "
					+ "	[Nombre] [nvarchar](100) NOT NULL, " + "	[PrecioVenta] [decimal](16, 4) NOT NULL, "
					+ "	[PrecioCosto] [decimal](16, 4) NOT NULL, "
					+ "	[InventarioOperativo] [decimal](16, 2) NOT NULL, "
					+ "	[InventarioTeorico] [decimal](16, 2) NOT NULL, " + "	[MA_EXH] [numeric](16, 4) NULL, "
					+ "	[Ma_Exh_Prm] [numeric](16, 4) NULL, " + "	[MA_EXH_INI] [date] NULL, "
					+ "	[MA_EXH_END] [date] NULL, " + "	[MA_STK_NEW] [numeric](16, 4) NULL, "
					+ "	[MA_NEW_INI] [date] NULL, " + "	[MA_NEW_END] [date] NULL, " + "	[MA_FS] [numeric](16, 4) NULL, "
					+ "	[MA_FS_PROM] [numeric](16, 4) NULL, " + "	[MA_FS_INI] [date] NULL, "
					+ "	[MA_FS_END] [date] NULL, " + "	[MA_IM] [numeric](16, 4) NULL, "
					+ "	[HFP] [varchar](5) NULL, " + "	[SUMA_HCU] [decimal](38, 2) NULL, " + "	[DIAS_HCU] [int] NULL, "
					+ "	[SUMA_HCI] [decimal](38, 2) NULL, " + "	[DIAS_HCI] [int] NULL, "
					+ "	[DESVEST] [float] NULL, " + "	[MA_VTA_PRD] [numeric](16, 4) NOT NULL, "
					+ "	[MA_DECIMAL] [bit] NOT NULL, " + "	[nombre_proveedor] [varchar](100) NULL, "
					+ "	[maLinSur] [int] NULL " + ")";
			stmt.executeUpdate(query2);
			String query3 = "INSERT INTO #ReturnTable EXEC Satelites.hc.Get_ItemsBySupplier_MOB " + faValida;
			stmt.executeUpdate(query3);
			String skuRelacionados = "Select SKU from #ReturnTable where InventarioTeorico>0";
			boolean encontrados = false;
			SQLResult res = new SQLResult(conn, skuRelacionados);
			;
			while (encontrados != true) {
				listaSkus.clear();
				// res = new SQLResult(conn, skuRelacionados);
				if (res.getRowCount() > cantidadSKus) {
					System.out.println("Fuente de Abasto: " + faValida);
					String valor = "";
					int index = 0;
					for (int i = 0; listaSkus.size() < cantidadSKus; i++) {
						val = res.getRow(i).toString();
						val = (val.replace("]", "")).replace("[", "");
						valor = obtenerInventarioActual(val);
						if (valor == "" || Integer.parseInt(valor) < inventario) {
							if (i >= res.getRowCount() - 1) {
								obtenerFuenteAbastoValida();
								obtenerSkusInventarioActual(cantidadSKus, inventario);
								encontrados = true;
							}
							continue;
						} else {
							System.out.println(index + ": " + val + "(" + Integer.parseInt(valor) + ")");
							listaSkus.add(index, val);
							index++;
						}
					}
					encontrados = true;
				} else {
					obtenerFuenteAbastoValida();
					obtenerSkusInventarioActual(cantidadSKus, inventario);
					encontrados = true;
				}
			}
			return true;
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error al obtener los SKUs Relacionados a la Fuente de Abasto");
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * obtenerInventarioActual - consulta el inventario actual en pv del Skus
	 * proporcionado
	 * 
	 * @author Alex Aquino
	 * 
	 * @param sku - articulo del cual se busca obtener su inventario
	 * 
	 * @return Cantidad(inventario actual del sku ingresado)
	 */
	public String obtenerInventarioActual(String sku) {
		String cantidad = "";
		try {
			Connection conn = DriverManager.getConnection(GlobalVariables.urlXposStorePV10_17,
					GlobalVariables.userPV10_17, GlobalVariables.passwordPV10_17);
			String query = "SELECT CAST(li.QU_CUR AS INT) from [XposStore].[dbo].[ITEM] i "
					+ "Left join [XposStore].[dbo].LE_INV_ITM li on li.ID_ITM = i.ITEM_ID " + "WHERE i.SKU = '" + sku
					+ "'";
			SQLResult res = new SQLResult(conn, query);
			cantidad = String.valueOf(Integer.parseInt(res.getFirstData().toString()));
			return cantidad;
		} catch (Exception e) {
			return cantidad;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * ingresarFuenteAbastoInventario - Ingresa la FA valida en el campo
	 * 'tb_CapturaFuenteAbasto'
	 * 
	 * @author Alex Aquino
	 * 
	 * @param cantSkus - Cantidad de Sku´s a Ingresar en el listado.
	 * 
	 * @return True or False
	 */
	public boolean ingresarFuenteAbastoInventario() {
		au.sendKeys(tb_CapturaFuenteAbasto, faValida);
		String FA;
		try {
			FA = au.driver.findElement(tb_CapturaFuenteAbasto).getText();
		} catch (Exception e) {
			FA = "";
		}
		if (FA != "") {
			testCase.addTextEvidenceCurrentStep("Se valida que se ingresa la FA y la informacion queda capturada");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * incrementarBtnMasInventario - Hace click en btnIncrementar Segun Inventario
	 * 
	 * @author Alex Aquino
	 * 
	 * @param numArticulos - cant Articulos a modificar(1-2)
	 * 
	 * @return N/A
	 */
	public void incrementarBtnMasInventario(int numArticulos) { // numArticulos = 1-2
		String xpathPadre = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/rvwListaCambiosFisicos\"]/android.view.ViewGroup";
		for (int i = 1; i <= numArticulos && i < 3; i++) {
			String ArtActual = au.driver.findElement(By.xpath(xpathPadre + "[" + (i + 1) + "]"
					+ "//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edtCambioFisicoSku\"]"))
					.getText();
			int numClick = Integer.parseInt(obtenerInventarioActual(ArtActual));
			for (int j = 1; j < numClick; j++) {
				WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(10));
				WebElement incrementar = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
						"(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnAddCambioFisico\"])"
								+ "[" + i + "]")));
				incrementar.click();
			}
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaIncrementoCantidad - valida que el valor de la cantidad aumenta segun
	 * los articulos agregados
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaIncrementoCantidad(int cantAValidar) {
		int validacion = Integer.parseInt(au.driver.findElement(By.xpath(
				"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edtCantidadCambiosFisicos\"])[1]"))
				.getText());
		if (validacion < cantAValidar) {
			return false;
		} else {
			testCase.addTextEvidenceCurrentStep(
					"Se valida que la cantidad aumenta en segun los clicks dados" + "(" + cantAValidar + ")");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * disminuirBtnMenos - No puede especificar menos de una unidad para cambio
	 * fisico
	 * 
	 * @author Alex Aquino
	 * 
	 * @param numArticulos - cant Articulos a modificar(1-2)
	 * 
	 * @param clicks - cantidad de vecer a presionar boton disminuir
	 * 
	 * @return N/A
	 */
	public void disminuirBtnMenos(int numArticulos, int clicks) { // numArticulos = 1-2
		for (int i = 1; i <= numArticulos; i++) {
			String ArtActual = au.driver.findElement(By.xpath(
					"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edtCantidadCambiosFisicos\"])["
							+ i + "]"))
					.getText();
			int numClick = Integer.parseInt(ArtActual);
			for (int j = numClick; j > clicks; j--) {
				WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(10));
				WebElement incrementar = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
						"(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnMinusCambioFisico\"])"
								+ "[" + i + "]")));
				incrementar.click();
			}
		}
		testCase.addTextEvidenceCurrentStep("Se hace click en boton disminur '-'");
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaDisminuyeCantidad - valida que el valor de la cantidad disminuye segun
	 * los articulos agregados
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaDisminuyeCantidad(int cantAValidar) {
		int validacion = Integer.parseInt(au.driver.findElement(By.xpath(
				"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edtCantidadCambiosFisicos\"])[1]"))
				.getText());
		if (validacion > cantAValidar) {
			return false;
		} else {
			testCase.addTextEvidenceCurrentStep(
					"Se valida que la cantidad disminuye en segun los clicks dados" + "(" + cantAValidar + ")");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * AgregarArt_EditCFisicoProgramado3 - ingresa en el campo C. Fisicos Pendientes
	 * la cantidad deseada
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public void Mofid_EditCFProgramado3(int cantArt, int cantidad) {
		for (int i = 1; i <= cantArt; i++) {
			String path = "(//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edt_cfisico_programado\"])["
					+ i + "]";
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
			WebElement valor = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(path)));
			valor.click();
			valor.clear();
			au.driver.hideKeyboard();
			valor.sendKeys(String.valueOf(cantidad));
		}
	}

//	/*
//     * Metodo
//     ******************************************************************************************
//     * AgregarArt_EditCFisicoProgramado - ingresa en el campo  C. Fisicos Pendientes la cantidad deseada
//     * @author Alex Aquino
//     * @param N/A
//     * @return true/false
//     */
//	public void Mofid_EditCFProgramado(int cantArt,int cantidad) {
//		for(int i = 1; i<= cantArt; i++) {
//		au.sendKeys(By.xpath("(//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edt_cfisico_programado\"])["+i+"]"), String.valueOf(cantidad));
//		}
//	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaDisminuyeCantidad - valida que el valor de la cantidad disminuye segun
	 * los articulos agregados
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public void tapBtnSiguienteCambiosFisicos() {
		au.scrollToElement(btn_Siguiente);
		au.click(btn_Siguiente);
		testCase.addTextEvidenceCurrentStep("Se hace click en opcion: Siguiente");
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaDisminuyeCantidad - valida que el valor de la cantidad disminuye segun
	 * los articulos agregados
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaExistePantallaAcuerdoDeEntrega() {
		boolean validacion;
		try {
			validacion = au.driver.findElement(titleAcuerdoDeEntrega).isDisplayed();
		} catch (Exception e) {
			return false;
		}
		if (validacion == true) {
			testCase.addTextEvidenceCurrentStep("Se valida Existencia de Pantalla Acuerdo de Entrega");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} else {
			return false;
		}
	}

	/********************************************************************************************************************************
	 * Método - swipeCoord - permite el dezplamiento el pantalla
	 * 
	 * @param startX     - punto de partida en x
	 * @param startY     - punto de partida en y
	 * @param endX       - punto final en x
	 * @param endY       - punto final en y
	 * @param durationMs - duracion de la accion
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
	 * validaExisteTablaResumenCambiosProgPendientes - valida se muestra la tabla
	 * Resumen Cambios Programados Pendientes
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void ExisteTablaResumenCambiosProgPendientes() {
		String padre = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/rv_acuerdo_entrega_cf_pendientes\"]/android.widget.LinearLayout[2]";
		au.scrollToElement(By.xpath(padre
				+ "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_articulo_cf_programado\"]"));
		boolean nombreTabla = au.driver.findElement(lb_ResumenCambiosProgPendientes).isDisplayed();
		boolean tabla = au.driver.findElement(tb_ResumenCambiosProgPend).isDisplayed();
		if (nombreTabla == true && tabla == true) {
			testCase.addTextEvidenceCurrentStep(
					"Se valida existencia de tabla: Resumen Cambios Programados Pendientes");
			testCase.addScreenShotCurrentStep(au, "testImage");
		} else {
			testCase.addTextEvidenceCurrentStep(
					"Error, No se encontro la tabla: Resumen Cambios Programados Pendientes ");
			testCase.addScreenShotCurrentStep(au, "testImage");
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaElementosTablaResumenCamProgPend - valida existencia de elementos
	 * dentro de la tabla Resumen Cambios Programados Pendientes
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaElementosTablaResumenCamProgPend() {
		// encabezado
		boolean id = au.driver.findElement(hd_idCambProg).isDisplayed();
		boolean art = au.driver.findElement(hd_artCambProg).isDisplayed();
		boolean desc = au.driver.findElement(hd_descCambProg).isDisplayed();
		if (id != true || art != true || desc != true) {
			return false;
		} else {
			for (int i = 1; i <= 2; i++) {
				String padre = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/rv_acuerdo_entrega_cf_pendientes\"]/android.widget.LinearLayout["
						+ i + "]";
				String sku = au.driver.findElement(By.xpath(padre
						+ "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_articulo_cf_programado\"]"))
						.getText();
				if (!SkusEvo.contains(sku)) {
					return false;
				}
			}
			testCase.addTextEvidenceCurrentStep(
					"Se valida existencia de articulos y tabla: Resumen de Cambios Programados");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaTablaVaciaResumenCambiosProgPendientes - valida se muestra la tabla
	 * Resumen Cambios Programados Pendientes vacia
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void validaTablaVaciaResumenCambiosProgPendientes() {
		boolean nombreTabla = au.driver.findElement(lb_ResumenCambiosProgPendientes).isDisplayed();
		boolean tabla;
		try {
			tabla = au.driver.findElement(tb_ResumenCambiosProgPend).isDisplayed();
		} catch (Exception e) {
			tabla = false;
		}
		System.out.println("Existencia de tabla?: " + tabla);
		if (nombreTabla == true && tabla == true) {
			testCase.addTextEvidenceCurrentStep("Error, Existencia de tabla: Resumen Cambios Programados Pendientes");
			testCase.addScreenShotCurrentStep(au, "testImage");
		} else {
			testCase.addTextEvidenceCurrentStep(
					"Se valida que la tabla: Resumen Cambios Programados Pendientes se encuentra vacia");
			testCase.addScreenShotCurrentStep(au, "testImage");
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaNoSkusTablaResumenCambProgPendientes - valida que no existen articulos
	 * en la tabla Resumen Cambios Programados Pendientes
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaNoSkusTablaResumenCambProgPendientes() {
		boolean skus;
		try {
			skus = au.driver.findElement(By.xpath(
					"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_articulo_cf_programado\"]"))
					.isDisplayed();
		} catch (Exception e) {
			skus = false;
		}
		System.out.println("existencia articulos?: " + skus);
		if (skus == true) {
			return false;
		}
		testCase.addTextEvidenceCurrentStep(
				"Se valida ausencia de tabla y articulos: Resumen Cambios Programados Pendientes");
		testCase.addScreenShotCurrentStep(au, "testImage");
		return true;
	}
	// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&

	/*
	 * Metodo
	 ******************************************************************************************
	 * ExisteTablaResumenCambiosRealizados - valida se muestra la tabla Resumen
	 * Cambios Realizados
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void ExisteTablaResumenCambiosRealizados() {
		au.driver.findElement(AppiumBy.androidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"RESUMEN CAMBIOS REALIZADOS\"))"));
		boolean nombreTabla = au.driver.findElement(lb_ResumenCambiosRealizados).isDisplayed();
		boolean tabla = au.driver.findElement(tb_ResumenCambiosRealizados).isDisplayed();
		if (nombreTabla == true && tabla == true) {
			testCase.addTextEvidenceCurrentStep("Se valida existencia de tabla: Resumen Cambios Realizados");
			testCase.addScreenShotCurrentStep(au, "testImage");
		} else {
			testCase.addTextEvidenceCurrentStep("Error, No se encontro la tabla: Resumen Cambios Realizados");
			testCase.addScreenShotCurrentStep(au, "testImage");
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaElementosTablaResumenCambiosRealizados - valida existencia de elementos
	 * dentro de la tabla Resumen Cambios Realizados
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaElementosTablaResumenCambiosRealizados() {
		// encabezado
		boolean id = au.driver.findElement(hd_idCambRealiz).isDisplayed();
		boolean art = au.driver.findElement(hd_artCambRealiz).isDisplayed();
		boolean desc = au.driver.findElement(hd_descCambRealiz).isDisplayed();
		boolean cant = au.driver.findElement(hd_cantCambRealiz).isDisplayed();
		if (id != true || art != true || desc != true || cant != true) {
			return false;
		} else {
			for (int i = 1; i <= 2; i++) {
				String padre = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/rv_acuerdo_entrega_cf_realizados\"]/android.widget.LinearLayout["
						+ i + "]";
				String sku = au.driver.findElement(By.xpath(padre
						+ "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_articulo_cf_realizado\"]"))
						.getText();
				if (!SkusEvo.contains(sku)) {
					return false;
				}
			}
			testCase.addTextEvidenceCurrentStep(
					"Se valida existencia de articulos y tabla: Resumen de Cambios Programados");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaTablaVaciaResumenCambiosRealizados - valida se muestra la tabla Resumen
	 * Cambios Realizados vacia
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void validaTablaVaciaResumenCambiosRealizados() {
		boolean nombreTabla = au.driver.findElement(lb_ResumenCambiosRealizados).isDisplayed();
		boolean tabla;
		try {
			tabla = au.driver.findElement(tb_ResumenCambiosRealizados).isDisplayed();
		} catch (Exception e) {
			tabla = false;
		}
		System.out.println("Existencia de tabla?: " + tabla);
		if (nombreTabla == true && tabla == true) {
			testCase.addTextEvidenceCurrentStep("Error, Existencia de tabla: Resumen Cambios Realizados");
			testCase.addScreenShotCurrentStep(au, "testImage");
		} else {
			testCase.addTextEvidenceCurrentStep(
					"Se valida que la tabla: Resumen Cambios Realizados se encuentra vacia");
			testCase.addScreenShotCurrentStep(au, "testImage");
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaNoSkusTablaResumenCambRealizados - valida que no existen articulos en
	 * la tabla Resumen Cambios Realizados
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaNoSkusTablaResumenCambRealizados() {
		boolean skus;
		try {
			skus = au.driver.findElement(By.xpath(
					"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_articulo_cf_realizado\"]"))
					.isDisplayed();
		} catch (Exception e) {
			skus = false;
		}
		System.out.println("existencia articulos?: " + skus);
		if (skus == true) {
			return false;
		}
		testCase.addTextEvidenceCurrentStep("Se valida ausencia de tabla y articulos: Resumen cambios realizados");
		testCase.addScreenShotCurrentStep(au, "testImage");
		return true;
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaSkuTieneCambiosRealizados - Valida si un articulo tiene Cambios
	 * Realizados
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 */
	public boolean validaSkuTieneCambiosRealizados() {
		for (int i = 1; i <= 2; i++) {
			String padre = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/rv_acuerdo_entrega_cf_realizados\"]/android.widget.LinearLayout["
					+ i + "]";
			String sku = au.driver.findElement(By.xpath(padre
					+ "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_articulo_cf_realizado\"]"))
					.getText();
			if (!listaSkus.contains(sku)) {
				return false;
			}
		}
		testCase.addTextEvidenceCurrentStep("Se valida que los/el articulo Tiene Cambios Realizados");
		testCase.addScreenShotCurrentStep(au, "testImage");
		return true;
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaSkuTieneCambiosProgramadosPend - Valida si un articulo tiene Cambios
	 * Programados Pendientes
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 */
	public boolean validaSkuTieneCambiosProgramadosPend() {
		for (int i = 1; i <= 2; i++) {
			String padre = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/rv_acuerdo_entrega_cf_pendientes\"]/android.widget.LinearLayout["
					+ i + "]";
			String sku = au.driver.findElement(By.xpath(padre
					+ "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_articulo_cf_programado\"]"))
					.getText();
			if (!SkusEvo.contains(sku)) {
				return false;
			}
		}
		testCase.addTextEvidenceCurrentStep("Se valida que los/el articulo Tiene Cambios Programados Pendientes");
		testCase.addScreenShotCurrentStep(au, "testImage");
		return true;
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * ScrollToObservacionesProveedor - Se dezplaza hasta el apartado Observaciones
	 * del proveedor
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void ScrollToObservacionesProveedor() {
		swipeCoord(50, 600, 50, 100, 500);
		testCase.addTextEvidenceCurrentStep("Se muestra el apartado: Observaciones de Proveedor");
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * ScrollToObservacionesProveedor - Se dezplaza hasta el apartado Observaciones
	 * del proveedor
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void ScrollToObservacionesTienda() {
		au.scrollToElement(AEObservacionesTienda);
		testCase.addTextEvidenceCurrentStep("Se muestra el apartado: Observaciones de Proveedor");
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaApartadoObservacionesProveedor - Valida existencia de apartado
	 * Observaciones de Proveedor
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 */
	public boolean validaApartadoObservacionesProveedor() {
		String valida;
		try {
			valida = au.driver.findElement(AEObservacionesProveedor).getText();
		} catch (Exception e) {
			valida = "";
		}
		if (valida.equalsIgnoreCase("SELECCIONA UNA OPCIÓN")) {
			testCase.addTextEvidenceCurrentStep("Se muestra el apartado: Observaciones de Proveedor");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaApartadoFirmaProveedor - Valida existencia de apartado Fira del
	 * Proveedor
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 */
	public boolean validaApartadoFirmaProveedor() {
		boolean valida;
		try {
			valida = au.driver.findElement(AEfirmaProveedor).isDisplayed();
		} catch (Exception e) {
			valida = false;
		}
		if (valida == true) {
			testCase.addTextEvidenceCurrentStep("Se muestra el apartado: Firma de Proveedor");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaApartadoObservacionesTienda - Valida existencia de apartado
	 * Observaciones de Personal de tienda
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 */
	public boolean validaApartadoObservacionesTienda() {
		String valida;
		try {
			valida = au.driver.findElement(AEObservacionesTienda).getText();
		} catch (Exception e) {
			valida = "";
		}
		if (valida.equalsIgnoreCase("SELECCIONA UNA OPCIÓN")) {
			testCase.addTextEvidenceCurrentStep("Se muestra el apartado: Observaciones del Personal de tienda");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaApartadoFirmaTienda - Valida existencia de apartado Fira del Personal
	 * de tienda
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 */
	public boolean validaApartadoFirmaTienda() {
		boolean valida;
		try {
			valida = au.driver.findElement(AEfirmaTienda).isDisplayed();
		} catch (Exception e) {
			valida = false;
		}
		if (valida == true) {
			testCase.addTextEvidenceCurrentStep("Se muestra el apartado: Firma del Personal de tienda");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaObservacionesTiendaVacio - valida apartado Observaciones del Personal
	 * de tienda esta Vacio
	 * 
	 * @author Alex Aquino
	 * 
	 * @param Opcion - Sin(Sin Comentarios)/ Otros(Otros-Especifica)
	 * 
	 * @return True or False
	 */
	public boolean validaObservacionesTiendaVacio() {
		String valida;
		try {
			valida = au.driver.findElement(AEObservacionesTienda).getText();
		} catch (Exception e) {
			valida = "";
		}
		if (valida.equalsIgnoreCase("SELECCIONA UNA OPCIÓN")) {
			testCase.addTextEvidenceCurrentStep(
					"Valida que el apartado Observaciones del Personal de tienda se encuentra Vacio");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaObservacionesProveedorVacio - valida apartado Observaciones del
	 * Proveedor esta Vacio
	 * 
	 * @author Alex Aquino
	 * 
	 * @param Opcion - Sin(Sin Comentarios)/ Otros(Otros-Especifica)
	 * 
	 * @return True or False
	 */
	public boolean validaObservacionesProveedorVacio() {
		String valida;
		try {
			valida = au.driver.findElement(AEObservacionesProveedor).getText();
		} catch (Exception e) {
			valida = "";
		}
		if (valida.equalsIgnoreCase("SELECCIONA UNA OPCIÓN")) {
			testCase.addTextEvidenceCurrentStep("Valida que el apartado Observaciones de Proveedor se encuentra Vacio");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaFirmaProveedorVacio - valida apartado Firma del Proveedor esta Vacio
	 * 
	 * @author Alex Aquino
	 * 
	 * @param Opcion - Sin(Sin Comentarios)/ Otros(Otros-Especifica)
	 * 
	 * @return True or False
	 */
	public boolean validaFirmaProveedorVacio() {
		String valida;
		try {
			valida = au.driver.findElement(AEfirmaProveedor).getText();
		} catch (Exception e) {
			valida = "";
		}
		if (valida.equalsIgnoreCase("")) {
			System.out.println("true(vacio): " + valida);
			testCase.addTextEvidenceCurrentStep("Valida que el apartado Firma de Proveedor se encuentra Vacio");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} else {
			System.out.println(valida);
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * screenShotFirma - toma un screenshot de la pantalla, se usa para comparar las
	 * firmas
	 * 
	 * @author Alex Aquino
	 * 
	 * @param opcion- nombre de la imagen screenshot guardado (Ej: antes,
	 * despues,1,2)
	 * 
	 * @return N/A
	 */
	public void screenShotFirma(String opcion) {
		try {
			au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
			String path = "src\\test\\java\\directos\\mobile\\" + opcion + ".png";
			File viejo = new File(path);
			if (viejo.exists()) {
				viejo.delete();
			}
			File scrFile = ((TakesScreenshot) au.driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(path));
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error, No se pudo tomar la captura de pantalla/Validar las firmas");
		}

	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaCambio - toma un screenshot de la pantalla, se usa para comparar las
	 * firmas
	 * 
	 * @author Alex Aquino
	 * 
	 * @param opcion- nombre del screenshot tomado
	 * 
	 * @return N/A
	 */
	public boolean validaCampoFirmaVacio(String antes, String despues) {
		byte[] originalDigest;
		byte[] newDigest;
		try {
			byte[] originalImageBytes = Files
					.readAllBytes(new File("src\\test\\java\\directos\\mobile\\" + antes + ".png").toPath());
			byte[] newImageBytes = Files
					.readAllBytes(new File("src\\test\\java\\directos\\mobile\\" + despues + ".png").toPath());

			MessageDigest md = MessageDigest.getInstance("MD5");
			originalDigest = md.digest(originalImageBytes);
			newDigest = md.digest(newImageBytes);

		} catch (Exception e) {
			return false;
		}
		try {
			Files.deleteIfExists(Paths.get("src\\test\\java\\directos\\mobile\\" + antes + ".png"));
			Files.deleteIfExists(Paths.get("src\\test\\java\\directos\\mobile\\" + despues + ".png"));
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep(" ");
		}
		boolean wasEdited = !MessageDigest.isEqual(originalDigest, newDigest);
		if (wasEdited == true) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaCampoFirmaCapturado - toma un screenshot de la pantalla, se usa para
	 * comparar las firmas
	 * 
	 * @author Alex Aquino
	 * 
	 * @param opcion- nombre del screenshot tomado
	 * 
	 * @return N/A
	 */
	public boolean validaCampoFirmaCapturado(String antes, String despues) {
		byte[] originalDigest;
		byte[] newDigest;
		try {
			byte[] originalImageBytes = Files
					.readAllBytes(new File("src\\test\\java\\directos\\mobile\\" + antes + ".png").toPath());
			byte[] newImageBytes = Files
					.readAllBytes(new File("src\\test\\java\\directos\\mobile\\" + despues + ".png").toPath());

			MessageDigest md = MessageDigest.getInstance("MD5");
			originalDigest = md.digest(originalImageBytes);
			newDigest = md.digest(newImageBytes);

		} catch (Exception e) {
			return false;
		}
		try {
			Files.deleteIfExists(Paths.get("src\\test\\java\\directos\\mobile\\" + antes + ".png"));
			Files.deleteIfExists(Paths.get("src\\test\\java\\directos\\mobile\\" + despues + ".png"));
		} catch (Exception e) {
			return false;
		}
		boolean wasEdited = !MessageDigest.isEqual(originalDigest, newDigest);
		if (wasEdited == true) {
			testCase.addTextEvidenceCurrentStep("Se valida que la firma queda capturada");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleccionarObservacionesTienda - Selecciona opcion: Observaciones del
	 * personal de tienda
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void seleccionarObservacionesTienda() {
		au.scrollToElement(AEObservacionesTienda);
		au.click(AEObservacionesTienda);
		testCase.addTextEvidenceCurrentStep("Se seleccionar el campo: Observaciones de la Tienda");
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleccionarObservacionesProveedor - Selecciona opcion: Observaciones del
	 * personal de tienda
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void seleccionarObservacionesProveedor() {
		au.scrollToElement(AEObservacionesProveedor);
		au.click(AEObservacionesProveedor);
		testCase.addTextEvidenceCurrentStep("Se selecciona el campo: Observaciones del Proeedor");
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaOpcionesObservacionesTienda - valida existencia de opciones Sin
	 * comentarios y otros (Observaciones Tienda)
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaOpcionesObservacionesTienda() {
		boolean sinC = au.driver.findElement(AEOpcionSinComentarios).isDisplayed();
		boolean otros = au.driver.findElement(AEOpcionOtros).isDisplayed();
		if ((sinC || otros) != true) {
			System.out.println("No encontre las opciones");
			return false;
		} else {
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaOpcionesObservacionesProveedor - valida existencia de opciones Sin
	 * comentarios y otros (Observaciones Tienda)
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaOpcionesObservacionesProveedor() {
		boolean sinC = au.driver.findElement(AEOpcionSinComentarios).isDisplayed();
		boolean otros = au.driver.findElement(AEOpcionOtros).isDisplayed();
		if ((sinC || otros) != true) {
			System.out.println("No encontre las opciones");
			return false;
		} else {
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaCambio - elije opccion: Sin Comentarios
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapOpcionSinComentarios() {
		au.click(AEOpcionSinComentarios);
		testCase.addTextEvidenceCurrentStep("Se selecciona opcion: SIN COMENTARIOS");
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * tapOpcionOtros - elije opccion: Otros
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapOpcionOtros() {
		au.click(AEOpcionOtros);
		testCase.addTextEvidenceCurrentStep("Se selecciona opcion: Otros");
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaObservacionesTiendaCapturada - valida apartado Observaciones del
	 * personal Tienda queda Capturado
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param Opcion - Sin(Sin Comentarios)/ Otros(Otros-Especifica)
	 * 
	 * @return True or False
	 */
	public boolean validaObservacionesTiendaCapturada(String opcion) {
		String valida;
		try {
			valida = au.driver.findElement(AEObservacionesTienda).getText();
		} catch (Exception e) {
			valida = "";
		}
		if (opcion == "sin") {
			if (valida.equalsIgnoreCase("SIN COMENTARIOS")) {
				testCase.addTextEvidenceCurrentStep("Se valida que la opcion 'SIN COMENTARIOS' se queda seleccionada");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} else {
				return false;
			}
		}
		if (opcion == "otros") {
			if (!valida.equalsIgnoreCase("")) {
				testCase.addTextEvidenceCurrentStep("Se valida que la opcion 'SIN COMENTARIOS' se queda seleccionada");
				testCase.addScreenShotCurrentStep(au, "testImage");
				System.out.println("opcion otros seleccionada");
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaObservacionesProveedorCapturada - valida apartado Observaciones del
	 * Proveedor queda Capturado
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param Opcion - Sin(Sin Comentarios)/ Otros(Otros-Especifica)
	 * 
	 * @return True or False
	 */
	public boolean validaObservacionesProveedorCapturada(String opcion) {
		String valida;
		try {
			valida = au.driver.findElement(AEObservacionesProveedor).getText();
		} catch (Exception e) {
			valida = "";
		}
		if (opcion == "sin") {
			if (valida.equalsIgnoreCase("SIN COMENTARIOS")) {
				testCase.addTextEvidenceCurrentStep("Se valida que la opcion 'SIN COMENTARIOS' se queda seleccionada");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} else {
				return false;
			}
		}
		if (opcion == "otros") {
			if (!valida.equalsIgnoreCase("")) {
				testCase.addTextEvidenceCurrentStep("Se valida que la opcion 'SIN COMENTARIOS' se queda seleccionada");
				testCase.addScreenShotCurrentStep(au, "testImage");
				System.out.println("opcion otros seleccionada");
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * scrollToAEbtnSiguiente - Se dezplaza hasta el boton Siguiente en pantalla
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void scrollToAEbtnSiguiente() {
		au.scrollToElement(AEBtnSiguiente);
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * ingresaFirmaTienda - inserta firma en campo, Firma de persoanl de tienda
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void ingresaFirmaTienda() {
		au.scrollToElement(AEBtnSiguiente);
		au.click(AEfirmaTienda);
		au.click(AEfirma);
		au.click(AEbtnContinuar);
		testCase.addTextEvidenceCurrentStep("Se ingresa la firma del Personal de tienda");
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * ingresaFirmaProveedor - inserta firma en campo, Firma de Firma Proveedor
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void ingresaFirmaProveedor() {
		au.scrollToElement(lbObsProv);
		au.click(AEfirmaProveedor);
		au.click(AEfirma);
		au.click(AEbtnContinuar);
		testCase.addTextEvidenceCurrentStep("Se ingresa la firma del Personal de tienda");
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * ingresaFirmaProveedor - inserta firma en campo, Firma de Firma Proveedor
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void ingresaFirmaProveedor_J() {
		au.scrollToElement(AEObservacionesTienda);
		au.click(AEfirmaProveedor);
		au.click(AEfirma);
		testCase.addTextEvidenceCurrentStep("Resultado esperado: Se ingresar la firma");
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * scrollToAEbtnSiguiente - Se dezplaza hasta el boton Siguiente en pantalla
	 * Acuerdo de Entrega
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapOpcionSeCumpProcesoCambFis() {
		au.scrollToElement(AEBtnSiguiente);
		au.click(AERdBtnCumpProcCambFis);
		testCase.addTextEvidenceCurrentStep("Se selecciona la opcion: ");
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaOpcCumplioProCambFis - valida que la opcion: Se cumplio con el proceso
	 * de cambios fisicos esta seleccionada
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaOpcCumplioProCambFis() {
		au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		String op = au.driver.findElement(AERdBtnCumpProcCambFis).getAttribute("checked");
		if (op.contentEquals("true")) {
			testCase.addTextEvidenceCurrentStep(
					"Se valida que la opcion: Se cumplio con el proceso de cambios fisicos, queda seleccionada");
			testCase.addScreenShotCurrentStep(au, "tesTImage");
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * tapOpcionSiguienteAE - Selecciona boton Siguiente en pantalla Acuerdo de
	 * Entrega
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapOpcionSiguienteAE() {
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
		WebElement incrementar = wait.until(ExpectedConditions.presenceOfElementLocated(AEBtnSiguiente));
		incrementar.click();
		testCase.addTextEvidenceCurrentStep("Se hace click en boton: Siguiente");
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * tapOpcionSiguienteAE - Selecciona boton Siguiente en pantalla Acuerdo de
	 * Entrega
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapOpcionSiguienteAE_J() {
		au.scrollToElement(AEBtnSiguiente_J);
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
		WebElement incrementar = wait.until(ExpectedConditions.presenceOfElementLocated(AEBtnSiguiente));
		incrementar.click();
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaMsgAgrObsProveedor - valida que la opcion: Se cumplio con el proceso de
	 * cambios fisicos esta seleccionada
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaMsgAgrObsProveedor() {
		boolean val;
		try {
			val = au.driver.findElement(AEmsgAgObsProveedor).isDisplayed();
		} catch (Exception e) {
			val = false;
		}
		if (val == true) {
			testCase.addTextEvidenceCurrentStep("Se valida existencia de Mensaje:Agregar Observaciones del Proveedor");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaMsgAgrObsTienda - valida que la opcion: Se cumplio con el proceso de
	 * cambios fisicos esta seleccionada
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaMsgAgrObsTienda() {
		boolean val;
		try {
			val = au.driver.findElement(AEmsgAgObsTienda).isDisplayed();
		} catch (Exception e) {
			val = false;
		}
		if (val == true) {
			testCase.addTextEvidenceCurrentStep(
					"Se valida existencia de Mensaje:Agregar Observaciones del Personal de tienda");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * tapOpcionAceptarAE - selecciona opcion Aceptar
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapOpcionAceptarAE() {
		au.click(AEbtnAceptar);
		testCase.addTextEvidenceCurrentStep("Se hace click en boton: Siguiente");
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaMsgAgrFirmaTienda - valida existecia del mensaje: Agregar Firma del
	 * Personal de Tienda
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaMsgAgrFirmaTienda() {
		boolean val;
		try {
			val = au.driver.findElement(AEmsgAgFirTienda).isDisplayed();
		} catch (Exception e) {
			val = false;
		}
		if (val == true) {
			testCase.addTextEvidenceCurrentStep("Se valida existencia de Mensaje:Agregar Firma del personal de tienda");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaMsgAgrFirmaProveedor - valida existecia del mensaje: Agregar Firma del
	 * Proveedor
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaMsgAgrFirmaProveedor() {
		boolean val;
		try {
			val = au.driver.findElement(AEmsgAgFirTienda).isDisplayed();
		} catch (Exception e) {
			val = false;
		}
		if (val == true) {
			testCase.addTextEvidenceCurrentStep("Se valida existencia de Mensaje:Agregar Firma del Proveedor");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaAvisoCorreo - Se valida existencia del mensaje: El correo se está
	 * enviando al proveedor, revise la barra de notificaciones
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaAvisoCorreo() {
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(2));
		wait.until(ExpectedConditions.presenceOfElementLocated(AEavisoCorreo));
		boolean val;
		try {
			val = au.driver.findElement(AEavisoCorreo).isDisplayed();
		} catch (Exception e) {
			val = false;
		}
		if (val == true) {
			String texto = au.driver.findElement(AEavisoCorreo).getAttribute("text");
			if (texto.equalsIgnoreCase("El correo se está enviando al proveedor, revise la barra de notificaciones.")) {
				testCase.addTextEvidenceCurrentStep(
						"Se valida existencia del mensaje: El correo se está enviando al proveedor, revise la barra de notificaciones");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaExistePantallaTiraEntrega - valida existecia de pantalla 'Tira de
	 * Entrega'
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaExistePantallaTiraEntrega() {
		boolean val;
		try {
			val = au.driver.findElement(titleTiraEntrega).isDisplayed();
		} catch (Exception e) {
			val = false;
		}
		if (val == true) {
			testCase.addTextEvidenceCurrentStep("Se valida existencia de pantalla Tira de Entrega");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaCampoTextoLibre - valida que se habilita un campo para la entrada de
	 * texto libre
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaCampoTextoLibre() {
		boolean val = au.driver.isKeyboardShown();
		if (val == true) {
			testCase.addTextEvidenceCurrentStep("Se valida que el campo se habilita para texto libre");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaSeIngresaTextoYQuedaCapturado - valida que se habilita un campo para la
	 * entrada de texto libre
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaSeIngresaTextoYQuedaCapturado() {
		au.sendKeys(AEObservacionesProveedor, "texto libre");
		au.driver.hideKeyboard();
		String texto = au.driver.findElement(AEObservacionesProveedor).getAttribute("text");
		if (texto.equalsIgnoreCase("texto libre")) {
			testCase.addTextEvidenceCurrentStep(
					"Se Ingresa un texto libre en el campo y se valida que queda capturado");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * tapCampoFirmaProveedorAE - hace tap en el campo firma del proveedor
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapCampoFirmaProveedorAE() {
		au.click(AEfirmaProveedor);
		testCase.addTextEvidenceCurrentStep("Se hace tap en el campo de Firma del Proveedor");
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * tapCampoFirmaTiendaAE - hace tap en el campo firma del proveedor
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapCampoFirmaTiendaAE() {
		au.scrollToElement(AEfirmaTienda);
		au.click(AEfirmaTienda);
		testCase.addTextEvidenceCurrentStep("Se hace tap en el campo de Firma del Proveedor");
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * tapBotonAtras - hace tap en el btn regresar (flecha)
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapBotonAtras() {
		au.click(bt_Regresar);
		au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		testCase.addTextEvidenceCurrentStep("Se selecciona el boton 'atras'");
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * tapBotonAtras - hace tap en el btn regresar (flecha)
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean cancelaObsPersTienda() {
		au.driver.navigate().back();
		if (validaObservacionesTiendaVacio()) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * cancelaFirmaPersTienda - hace tap en el btn regresar (flecha)
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void cancelaFirmaPersTienda() {
		au.driver.navigate().back();
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * cancelaFirmaAreaProveedor - hace tap en el btn regresar (flecha)
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void cancelaFirmaAreaProveedor() {
		au.driver.navigate().back();
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * obtieneCrPlaza - obtiene el CrPlaza del PV
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return val(CrPlaza)
	 */
	public static void obtieneCrPlaza(String IpPV) {
		try {
			System.out.println(IpPV);
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + IpPV + ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV10_17, GlobalVariables.passwordPV10_17);
			String query = "select CODE from XposStore.dbo.Till where IS_ACTIVE = 1";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			crPlaza = val.substring(0, 5);
		} catch (Exception e) {
			System.out.println("Error al consultar el CR Plaza en BD");
			crPlaza = "";
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * obtieneCrPlaza - obtiene el CrPlaza del PV
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return val(CrPlaza)
	 */
	public void obtieneCrTienda(String IpPV) {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + IpPV + ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV10_17, GlobalVariables.passwordPV10_17);
			String query = "select CODE from XposStore.dbo.Till where IS_ACTIVE = 1";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			crTienda = val.substring(5);
		} catch (Exception e) {
			System.out.println("Error al consultar el CR Tienda en BD");
			crTienda = "";
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * obtieneStorePlaceID - obtiene el CrPlaza del PV
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void obtieneStorePlaceID() {
		try {
			Connection conn = DriverManager.getConnection(GlobalVariables.urlEvolucion, GlobalVariables.userEvolucion,
					PasswordUtil.decryptPassword(GlobalVariables.passEvolucion));
			String query = "SELECT STORE_PLACE_ID"
					+ " FROM [XposCloudPurchaseToolQA].[dbo].[STORE_PLACE] where CR_PLACE = '" + crPlaza
					+ "' AND CR_STORE = '" + crTienda + "'";
			SQLResult res = new SQLResult(conn, query);
			storePlaceID = res.getFirstData().toString();
		} catch (Exception e) {
			System.out.println("Error al consultar el StoreplaceID de la Tienda en BD:" + crPlaza + " " + crTienda);
			storePlaceID = "";
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaCrEnNubeEvolucion - valida si el PV/CR existe en la nube de evolucion
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return val(CrPlaza)
	 */
	public boolean validaCrEnNubeEvolucion(String IpPV) {
		int val = 0;
		try {
			obtieneCrPlaza(IpPV);
			obtieneCrTienda(IpPV);
			obtieneStorePlaceID();
			Connection conn = DriverManager.getConnection(GlobalVariables.urlEvolucion, GlobalVariables.userEvolucion,
					PasswordUtil.decryptPassword(GlobalVariables.passEvolucion));
			String query = "SELECT IS_ACTIVE"
					+ "  FROM [XposCloudPurchaseToolQA].[dbo].[STORE_PLACE] where CR_PLACE = '" + crPlaza
					+ "' AND CR_STORE = '" + crTienda + "'";
			SQLResult res = new SQLResult(conn, query);
			val = Integer.parseInt(res.getFirstData().toString());
			System.out.println(val + ", " + crPlaza + ", " + crTienda + ", " + storePlaceID);
		} catch (Exception e) {
			System.out.println("Error, Al intentar conectarse a la Nube de Evolucion, favor de validar: " + crPlaza
					+ " " + crTienda);
			return false;
		}
		if (val != 1) {
			System.out.println("Error, El CR utilizado no existe en la NUBE de Evolucion, favor de validar: " + crPlaza
					+ " " + crTienda);
			return false;
		}else {
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * obtieneFAConCambioFisProgNubeEvo - obtiene el CrPlaza del PV
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean obtieneFAConCambioFisProgNubeEvo() {
		try {
			obtieneStorePlaceID();
			Connection conn = DriverManager.getConnection(GlobalVariables.urlEvolucion, GlobalVariables.userEvolucion,
					PasswordUtil.decryptPassword(GlobalVariables.passEvolucion));
			String query = "SELECT top 1 t.CD_NMB_SPR "
					+ "FROM [XposCloudPurchaseToolQA].[dbo].[DI_PHYSICAL_CHANGES_HEADER] h "
					+ "LEFT JOIN [XposCloudPurchaseToolQA].[dbo].[DI_PHYSICAL_CHANGES_DETAIL] d ON h.DI_PHYSICAL_CHANGES_HEADER_ID = d.DI_PHYSICAL_CHANGES_HEADER_ID "
					+ "INNER JOIN [XposCloudPurchaseToolQA].[dbo].[PA_SPR] t ON t.PA_SPR_ID = h.PA_SPR_ID "
					+ "WHERE h.STORE_PLACE_ID = '" + storePlaceID + "' "
					+ "GROUP BY h.PA_SPR_ID, t.CD_NMB_SPR, h.REMISION_NUMBER, h.DI_PHYSICAL_CHANGES_HEADER_ID "
					+ "HAVING SUM(CASE WHEN d.QUANTITY_PENDING >= 1 THEN 1 ELSE 0 END) > 0";
			SQLResult res = new SQLResult(conn, query);
			FA_CCFP = res.getFirstData().toString();
		} catch (Exception e) {
			return false;
		}
		System.out.println("FA con CF Prog: " + FA_CCFP);
		return true;
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * capturarFAconCambiosFisicosProg - Captura una FA/Prov con Cambios Fisicos
	 * Programados
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void capturarFAconCambiosFisicosProg() {
		au.sendKeys(tb_CapFAoNumRemision, FA_CCFP);
		testCase.addTextEvidenceCurrentStep("Se captura una FA/Prov con Cambios Fisicos Programados");
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaExistePantallaResCambiosFisProgPend - valida que existe la pantalla de
	 * Cambios Fisiscos Programados Pendientes
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return ture/false
	 */
	public boolean validaExistePantallaResCambiosFisProgPend() {
		ScrollToTop_xTexto("Fuente de Abasto:");
		boolean title = au.driver.findElement(lbResumenCFPrgramados).isDisplayed();
		boolean Regresar = au.driver.findElement(bt_Regresar).isEnabled();
		boolean PregFrecuentes = au.driver.findElement(bt_PregFrecuentes).isEnabled();
		boolean Tutoriales = au.driver.findElement(bt_Tutoriales).isEnabled();
		if ((title || Regresar || PregFrecuentes || Tutoriales) == false) {
			return false;
		} else {
			testCase.addTextEvidenceCurrentStep(
					"Se valida existencia de pantalla Resumen Cambios Fisicos Programados Pendientes");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaHeaderPantallaResCambiosFisProgPend - valida el header de la pantalla
	 * de Cambios Fisiscos Programados Pendientes(Fuente de Abasto, Proveedor y Et.
	 * resumen de cambios programados)
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaHeaderPantallaResCambiosFisProgPend() {
		boolean title = au.driver.findElement(lbResumenCFPrgramados).isDisplayed();
		boolean fAbasto = au.driver.findElement(hd_FuenteAbasto).isEnabled();
		boolean proveedor = au.driver.findElement(hd_Proveedor).isEnabled();
		if ((title || fAbasto || proveedor) == false) {
			return true;
		} else {
			testCase.addTextEvidenceCurrentStep(
					"Se valida existencia de: Fuente de Abasto, Proveedor y Title en pantalla: Cambios Fisicos Programados Pendientes");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
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
	 * @return truee/false
	 */
	/*
	 * public boolean validafAbastoyNombProveedor() { boolean fabasto =
	 * !(au.driver.findElement(valorFAbasto).getAttribute("text")).contains(FA_CCFP)
	 * ; boolean nomfabasto =
	 * !(au.driver.findElement(nombreFAbasto).getAttribute("text")).contains(FA_CCFP
	 * );
	 * 
	 * }
	 */

	/*
	 * Metodo
	 ******************************************************************************************
	 * llegarPantallaDetallePedidoVisitaGuiadaRepleSinEV2 - inicia el proceso de
	 * visita guiada reple para llegar a pantalla detalle pedido
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void llegarPantallaDetallePedidoVisitaGuiadaRepleSinEV2() {
		au.click(bt_ExcOperativa);
		au.click(bt_HerrCompra);
		au.click(bt_Cometa);
		au.click(bt_VerMercancia);
		au.click(btn_leerQrParaPreventa);
		//au.click(btnNoGral);
		au.sendKeys(edt_leerQrParaPreventa, ProveedoresDirectos.QrGeneradoVisitaGuiada);
		au.click(capturarQrParaPreventa);
		au.click(aceptarCodigoPrueba);
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaMensajeProcesoVisitaGuiada - valida existencia de mensaje: relizando
	 * proceso de visita guiada
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaMensajeProcesoVisitaGuiada() {
		boolean msj = (au.driver.findElement(msjProcesoVisitaGuiada).getText())
				.contains("Estás realizando un proceso en la visita guia");
		if (msj == false) {
			return false;
		} else {
			testCase.addTextEvidenceCurrentStep(
					"Se valida existencia de Mensaje: Estás realizando un proceso en la visita guiada de capacitación, estos datos no serán guardados o almacenados en el punto de venta");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * enviarAplicacionASegundoPlano - valida que la aplicacion pasa a segundo plano
	 * durante la Capacitacion de visita Guiada
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void enviarAplicacionASegundoPlano() {
		au.driver.pressKey(new KeyEvent(AndroidKey.HOME));
		au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		testCase.addTextEvidenceCurrentStep("Se envia la aplicacion a segundo plano");
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaAppSegundoPlanoCapacitacionGuiada - valida que la aplicacion pasa a
	 * segundo plano durante la Capacitacion de visita Guiada
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaAppSegundoPlanoCapacitacionGuiada() {
		au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		boolean segundo;
		try {
			segundo = au.driver.findElement(msjProcesoVisitaGuiada).isDisplayed();
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
	 * validaProcesoVisitaGuiadaEnProceso - valida que la aplicacion permance en el
	 * proceso Capacitacion de visita Guiada al volver de segundo plano
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaProcesoVisitaGuiadaEnProceso() {
		au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		boolean segundo;
		try {
			segundo = au.driver.findElement(msjProcesoVisitaGuiada).isDisplayed();
		} catch (Exception e) {
			segundo = false;
		}
		if (segundo == false) {
			return false;
		} else {
			testCase.addTextEvidenceCurrentStep(
					"Se valida que la aplicacion permanece en el proceso Capacitacion Guiada");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * opcionSalirVisitaGuiada - se selecciona Salir para dejar la el proceso de
	 * visita Guiada
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void opcionSalirVisitaGuiada() {
		au.click(salirVisitaGuiada);
		testCase.addTextEvidenceCurrentStep("Se selecciona la opcion Salir");
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaMensajeSalirVisitaGuiada - valida existe el mensaje para salir de la
	 * visita guiada
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaMensajeSalirVisitaGuiada() {
		boolean msj = (au.driver.findElement(msjSalirVisitaGuiada).getText())
				.equalsIgnoreCase("¿Estás seguro que deseas salir de la Visita Guiada?");
		boolean no = au.driver.findElement(btnNoGral).isDisplayed();
		boolean si = au.driver.findElement(btnSiGral).isDisplayed();
		if ((msj || si || no) == false) {
			return false;
		} else {
			testCase.addTextEvidenceCurrentStep(
					"Se valida existencia de mensaje:¿Estás seguro que deseas salir de la Visita Guiada?");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * opcionSiSalirMensajevisitaGuiada - se selecciona la opcion SI en el mensaje
	 * salir de visita guiada
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void opcionSiSalirMensajevisitaGuiada() {
		au.click(btnSiGral);
		testCase.addTextEvidenceCurrentStep("Se se selecciona la opcion: Si");
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaSalidaProcesoCapacitacionDeVisitaGuiada - valida ya no se encuentra
	 * activo el proceso de la visita guiada
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaSalidaProcesoCapacitacionDeVisitaGuiada() {
		boolean provD = au.driver.findElement(btn_leerQrParaPreventa).isDisplayed();
		boolean vg;
		try {
			vg = (au.driver.findElement(msjProcesoVisitaGuiada).getText())
					.contains("Estás realizando un proceso en la visita guia");
		} catch (Exception e) {
			vg = false;
		}
		if (provD == false || vg == true) {
			return false;
		} else {
			testCase.addTextEvidenceCurrentStep("Se valida la aplicacion termino el proceso de Visita Guiada");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * ObtenerFAValidaNubeEvo - Obtiene una FA valida en Nube Evolucion
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param CantSkus - cantidad minima de skus a obtener
	 * 
	 * @param CantInv - cantidad minima a tener en Inv por Sku
	 * 
	 * @return N/A
	 */
	public boolean ObtenerFAValidaNubeEvo(int CantSkus, int CantInv) {
		try {
			Connection conn2 = DriverManager.getConnection(GlobalVariables.urlEvolucion, GlobalVariables.userEvolucion,
					PasswordUtil.decryptPassword(GlobalVariables.passEvolucion));
			String query3 = "USE XposCloudKardexBalanceQA\r\n" + "DROP TABLE IF EXISTS #InventarioQAScripts\r\n"
					+ "CREATE TABLE #InventarioQAScripts (\r\n" + "[SKU] [varchar](60),\r\n"
					+ "[STOCK] [DECIMAL](16,4),\r\n" + "[PRICE] [DECIMAL](16,4),\r\n"
					+ "[MODIFIED_DATETIME] [DATETIME],\r\n" + "[NAME] [varchar](200)\r\n" + ")\r\n"
					+ "INSERT INTO #InventarioQAScripts\r\n" + "EXEC GET_STOCK_INVENTORY '" + crPlaza + "','" + crTienda
					+ "', 0\r\n" + "SELECT top 1 SPR.CD_NMB_SPR\r\n"
					+ "FROM XposCloudItemQA.dbo.PA_SPR AS SPR WITH(NOLOCK)\r\n"
					+ "INNER JOIN XposCloudItemQA.dbo.ST_ITM_SPR_STK AS IPS WITH(NOLOCK)  ON IPS.ID_SPR = SPR.PA_SPR_ID AND IPS.IS_ACTIVE = 1 \r\n"
					+ "INNER JOIN  XposCloudItemQA.dbo.ITEM ITM WITH(NOLOCK) on ITM.ITEM_ID =  IPS.ID_ITM\r\n"
					+ "INNER JOIN XposCloudItemQA.dbo.ITEM_DETAIL AS ID WITH(NOLOCK) ON ID.ITEM_ID = IPS.ID_ITM AND SPR.STORE_PLACE_ID = ID.STORE_PLACE_ID\r\n"
					+ "INNER JOIN XposCloudItemQA.dbo.STORE_PLACE as SP ON SP.STORE_PLACE_ID = ID.STORE_PLACE_ID\r\n"
					+ "LEFT JOIN #InventarioQAScripts AS INV ON INV.SKU = ITM.SKU\r\n" + "WHERE SP.CR_PLACE = '"
					+ crPlaza + "' AND SP.CR_STORE = '" + crTienda + "'\r\n"
					+ "AND SPR.CD_NMB_SPR IN (SELECT SPR.CD_NMB_SPR\r\n"
					+ "FROM [XposCloudPurchaseToolQA].[dbo].[PA_SPR] SPR\r\n" + "WHERE SPR.STORE_PLACE_ID = '"
					+ storePlaceID + "'\r\n"
					+ "AND NOT EXISTS (SELECT 1 FROM [XposCloudPurchaseToolQA].[dbo].[DI_PHYSICAL_CHANGES_HEADER] DI\r\n"
					+ "WHERE DI.PA_SPR_ID = SPR.PA_SPR_ID AND DI.STORE_PLACE_ID = SPR.STORE_PLACE_ID))\r\n"
					+ "AND INV.STOCK > 0 AND SPR.CD_NMB_SPR not like '999999%'\r\n" + "GROUP BY SPR.CD_NMB_SPR\r\n"
					+ "HAVING (count(INV.SKU) >=" + CantSkus + " AND count(INV.STOCK)>=" + CantInv + ")\r\n"
					+ "order by NEWID()";
			SQLResult res2 = new SQLResult(conn2, query3);
			FaEvo = res2.getFirstData().toString();
			System.out.println("FA: " + FaEvo);
			conn2.close();
			return true;
		} catch (Exception e) {
			System.out.println("No FA");
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * obtenerSkusValidosxFANubeEvo - Obtiene Skus de una FA valida en Nube
	 * Evolucion
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param CantSkus - cantidad minima de skus a obtener
	 * 
	 * @param CantInv - cantidad minima a tener en Inv por Sku
	 * 
	 * @return N/A
	 */
	public boolean obtenerSkusValidosxFANubeEvo(int CantSkus, int CantInv) {
		try {
			Connection conn = DriverManager.getConnection(GlobalVariables.urlEvolucion, GlobalVariables.userEvolucion,
					PasswordUtil.decryptPassword(GlobalVariables.passEvolucion));
			if (FaEvo == null || FaEvo == "") {
				ObtenerFAValidaNubeEvo(CantSkus, CantInv);
			}
			List<String> Cantidades = new ArrayList<>();
			String query = "use XposCloudKardexBalanceQA\r\n" + "DECLARE @CR_PLACE NVARCHAR(40) = '" + crPlaza + "'\r\n"
					+ "DECLARE @CR_STORE NVARCHAR(40) ='" + crTienda + "' \r\n"
					+ "DECLARE @SUPPLY_SOURCE NVARCHAR(40) ='" + FaEvo + "' \r\n"
					+ "DECLARE @REPORTING_PERIOD_ID INT = 0\r\n" + "DECLARE @LAST_DATETIME DATETIME     \r\n"
					+ "DECLARE @BUSINESS_DATE_INIT DATE\r\n" + "DECLARE @BUSINESS_DATE_END DATE\r\n"
					+ "DECLARE @PREVIOUS_KARDEX INT = 0\r\n" + "DECLARE @STORE_PLACE_ID int\r\n" + "\r\n"
					+ "SELECT @STORE_PLACE_ID = SP.STORE_PLACE_ID \r\n" + "FROM STORE_PLACE SP WITH(NOLOCK) \r\n"
					+ "WHERE SP.CR_PLACE = @CR_PLACE \r\n" + "AND SP.CR_STORE = @CR_STORE\r\n" + "\r\n"
					+ "EXEC dbo.SP_GET_INVENTORY_DATE_RANGE @STORE_PLACE_ID, @REPORTING_PERIOD_ID OUT,  @PREVIOUS_KARDEX, @BUSINESS_DATE_INIT OUT, @BUSINESS_DATE_END OUT\r\n"
					+ "\r\n" + "DECLARE @ITM_FOR_STOCK TABLE (\r\n" + "Number INT IDENTITY(1,1) ,\r\n"
					+ "ITEM_ID UNIQUEIDENTIFIER, \r\n" + "STORE_PLACE_ID INT, \r\n" + "SKU VARCHAR(60),\r\n"
					+ "CD_NMB_SPR VARCHAR(60),\r\n" + "NAME VARCHAR(200), \r\n" + "PRICE DECIMAL(19,6), \r\n"
					+ "IS_ACTIVE BIT, \r\n" + "ITEM_TYPE_CODE VARCHAR(100), \r\n" + "total int )\r\n" + "\r\n"
					+ "INSERT INTO @ITM_FOR_STOCK (ITEM_ID , STORE_PLACE_ID , SKU, CD_NMB_SPR , NAME , PRICE , IS_ACTIVE , ITEM_TYPE_CODE, total)\r\n"
					+ "SELECT  I.ITEM_ID, I.STORE_PLACE_ID, I.SKU, SPR.CD_NMB_SPR, I.NAME, I.SALE_PRICE AS PRICE, I.IS_ACTIVE, I.ITEM_TYPE_CODE,0\r\n"
					+ "FROM ITEM I WITH(NOLOCK)\r\n"
					+ "INNER JOIN [XposCloudItemQA].[dbo].[ITEM] IQ WITH(NOLOCK) ON IQ.SKU = I.SKU\r\n"
					+ "INNER JOIN [XposCloudItemQA].[dbo].[ST_ITM_SPR_STK] ITMSPR WITH(NOLOCK) ON IQ.ITEM_ID = ITMSPR.ID_ITM\r\n"
					+ "INNER JOIN [XposCloudItemQA].[dbo].[PA_SPR] SPR WITH(NOLOCK) ON ITMSPR.ID_SPR = SPR.PA_SPR_ID\r\n"
					+ "WHERE I.STORE_PLACE_ID = @STORE_PLACE_ID\r\n" + "\r\n"
					+ "DROP TABLE IF EXISTS #InventarioQAScripts2\r\n" + "CREATE TABLE #InventarioQAScripts2 (\r\n"
					+ "[NO] [Int],\r\n" + "[SKU] [varchar](60),\r\n" + "[SALEPRICE] [DECIMAL](16,4),\r\n"
					+ "[DESCRIPTION] [varchar](200),\r\n" + "[QUANTITY] [int]\r\n" + ")\r\n"
					+ "INSERT INTO #InventarioQAScripts2\r\n"
					+ "SELECT i.Number as no,I.SKU, I.PRICE as salePrice, I.NAME as description,\r\n"
					+ "CAST(ISNULL(SUM(CASE WHEN MT.CODE IN ('IN_UNIT_VALUE_MOVEMENT','INITIAL_MOVEMENT') THEN IVJE.RETAIL_UNIT_COUNT WHEN MT.CODE IN ('OUT_UNIT_VALUE_MOVEMENT') THEN IVJE.RETAIL_UNIT_COUNT * -1 END), 0) AS int) AS quantity\r\n"
					+ "FROM STOCK_BALANCE SB WITH(NOLOCK)\r\n"
					+ "INNER JOIN ITEM_INVENTORY_JOURNAL_ENTRY IVJE WITH(NOLOCK) ON SB.STOCK_BALANCE_ID = IVJE.STOCK_BALANCE_ID AND SB.STORE_PLACE_ID = @STORE_PLACE_ID\r\n"
					+ "INNER JOIN MOVEMENT WITH(NOLOCK) ON MOVEMENT.MOVEMENT_ID = IVJE.MOVEMENT_ID \r\n"
					+ "INNER JOIN MOVEMENT_TYPE MT WITH(NOLOCK) ON MT.MOVEMENT_TYPE_ID = MOVEMENT.MOVEMENT_TYPE_ID\r\n"
					+ "INNER JOIN @ITM_FOR_STOCK I ON I.ITEM_ID = IVJE.ITEM_ID AND I.STORE_PLACE_ID = @STORE_PLACE_ID\r\n"
					+ "WHERE SB.BUSSINESS_DATE >= @BUSINESS_DATE_INIT\r\n"
					+ "AND MT.VALUE IN  ('0','1','2') AND I.CD_NMB_SPR = @SUPPLY_SOURCE\r\n"
					+ "GROUP BY I.Number, I.SKU, I.PRICE, I.NAME";
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(query);
			String query2 = "SELECT TOP " + CantSkus + " SKU, QUANTITY, SALEPRICE, DESCRIPTION\r\n" + "FROM (\r\n"
					+ "    SELECT DISTINCT SKU, QUANTITY, SALEPRICE, DESCRIPTION\r\n"
					+ "    FROM #InventarioQAScripts2\r\n" + "    WHERE QUANTITY > " + CantInv + "\r\n"
					+ ") AS subquery\r\n" + "ORDER BY NEWID()";
			SQLResult res = new SQLResult(conn, query2);
			// Guardado en lista
			SkusEvo.clear();
			for (int i = 0; i < res.getRowCount(); i++) {
				SkusEvo.add(res.getData(i, "SKU"));
				Cantidades.add(res.getData(i, "QUANTITY"));
			}
			// Impresion/uso/vaciado de lista
			System.out.println("Skus de la FA(" + FaEvo + "): ");
			for (int i = 0; i < SkusEvo.size(); i++) {
				System.out.println(SkusEvo.get(i) + " (Op:" + Cantidades.get(i) + ")");
			}
			stmt.close();
			conn.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaMsgCapturaCambioFisicoExitosa - Valida existencia de mensaje: Captura
	 * de Cambio Fisico Exitosa
	 * 
	 * @autor Alex Aquino param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaMsgCapturaCambioFisicoExitosa() {
		boolean msj;
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(7));
		wait.until(ExpectedConditions.presenceOfElementLocated(AEMsjCapturaCFExitosa));
		try {
			msj = (au.driver.findElement(AEMsjCapturaCFExitosa).getText())
					.equalsIgnoreCase("La captura de cambios físicos se ha realizado de manera exitosa.");
		} catch (Exception e) {
			msj = false;
		}
		if (msj == false) {
			return false;
		} else {
			testCase.addTextEvidenceCurrentStep(
					"Se valida existencia de mensaje: La captura de cambios físicos se ha realizado de manera exitosa.");
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.click(AEbtnAceptar);
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * obtenerNumRemCfPantallaTiraEntrega - Obtiene el numero de remision de la
	 * pantalla tira de entrega
	 * 
	 * @autor Alex Aquino param N/A
	 * 
	 * @return N/A
	 */
	public void obtenerNumRemCfPantallaTiraEntrega() {
		if (au.driver.findElement(titleTiraEntrega).isDisplayed()
				&& au.driver.findElement(lb_numRemisionCF).isDisplayed()) {
			numRemisionCF = (au.driver.findElement(lb_numRemisionCF).getText()).substring(10);
			System.out.println("numRem:" + numRemisionCF);
		} else {
			System.out.println("Pantalla tira de entrega no encontrada, no se pudo obtener el numero de remision");
			numRemisionCF = "";
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * eliminarCF_NubeEvo - Elimina el ultimo Cambio Fisico realizado
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void eliminarCF_NubeEvo() {
		if (OAD.numRemisionCF != null && OAD.numRemisionCF != "") {
			try {
				au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
				Connection conn = DriverManager.getConnection(GlobalVariables.urlEvolucion,
						GlobalVariables.userEvolucion, PasswordUtil.decryptPassword(GlobalVariables.passEvolucion));
				String query1 = "DELETE FROM [XposCloudPurchaseToolQA].[dbo].[DI_PHYSICAL_CHANGES_DETAIL] \r\n"
						+ "WHERE DI_PHYSICAL_CHANGES_HEADER_ID = (Select DI_PHYSICAL_CHANGES_HEADER_ID FROM [XposCloudPurchaseToolQA].[dbo].[DI_PHYSICAL_CHANGES_HEADER] \r\n"
						+ "WHERE REMISION_NUMBER = '" + OAD.numRemisionCF + "' AND STORE_PLACE_ID = '"
						+ OAD.storePlaceID + "')";
				String query2 = "DELETE FROM [XposCloudPurchaseToolQA].[dbo].[DI_PHYSICAL_CHANGES_HEADER] \r\n"
						+ "WHERE REMISION_NUMBER = '" + OAD.numRemisionCF + "' AND STORE_PLACE_ID = '"
						+ OAD.storePlaceID + "'";
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(query1);
				stmt.executeUpdate(query2);
				System.out.println("CF eliminado (" + OAD.numRemisionCF + ")");
				stmt.close();
				conn.close();
			} catch (Exception e) {
				System.out.println("Error, no se encontro el numero de remision");
			}
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaPantallaProveedoresDirectosDevAProv -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaPantallaProveedoresDirectosDevAProv() {
		boolean titulo = (au.driver.findElement(PDtitle).getText()).equalsIgnoreCase("Inventario Operativo");
		boolean regresar = au.driver.findElement(PDbtn_regresar).isDisplayed();
		boolean ayuda = au.driver.findElement(PDiconoInterrogacion).isDisplayed();
		boolean tutorial = au.driver.findElement(PDiconoAdmiracion).isDisplayed();
		boolean predevolucion = (au.driver.findElement(PDPredevolucionAProv).getText())
				.equalsIgnoreCase("Predevolución a proveedor");
		boolean devolucion = (au.driver.findElement(PDDevolucionAProv).getText())
				.equalsIgnoreCase("Devolucion a proveedor");
		if ((titulo || regresar || ayuda || tutorial || predevolucion || devolucion) == false) {
			return false;
		} else {
			testCase.addTextEvidenceCurrentStep(
					"Se valida existencia de pantalla Inventario Operativo con sus elementos");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * eliminarCF_NubeEvo - Elimina el ultimo Cambio Fisico realizado
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void opcionPredevolucionAProveedor() {
		au.click(PDPredevolucionAProv);
		testCase.addTextEvidenceCurrentStep("Se presiona el boton: Predevolucion a proveedor");
		testCase.addScreenShotCurrentStep(au, "testIamge");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaPantallaPredevolucionAProv - Elimina el ultimo Cambio Fisico realizado
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaPantallaPredevolucionAProv() {
		boolean regresar = au.driver.findElement(PDbtn_regresar).isDisplayed();
		boolean ayuda = au.driver.findElement(PDiconoInterrogacion).isDisplayed();
		boolean tutorial = au.driver.findElement(PDiconoAdmiracion).isDisplayed();
		boolean cr = au.driver.findElement(PP_Cr).isDisplayed();
		boolean goma = au.driver.findElement(PP_FolioGoma).isDisplayed();
		boolean fecha = au.driver.findElement(PP_Fecha).isDisplayed();
		boolean opfrec = au.driver.findElement(PP_OpcionesF).getText().equalsIgnoreCase("OPCIONES FRECUENTES");
		boolean descdev = au.driver.findElement(PP_lblDescripDev).getText()
				.equalsIgnoreCase("Descripción de la devolución");
		boolean textoDesc = au.driver.findElement(PP_edtDescripDev).getText().contains("Predevolución por excepción");
		boolean lblDevoluciones = au.driver.findElement(PP_lblDevAProv).getText()
				.equalsIgnoreCase("DEVOLUCIONES A PROVEEDOR");
		boolean edtCodigoB = au.driver.findElement(PP_edtCodigoB).isDisplayed();
		boolean btnCodigB = au.driver.findElement(PP_btnCodigoB).isDisplayed();
		if ((regresar || ayuda || tutorial || cr || goma || fecha || opfrec || descdev || textoDesc || lblDevoluciones
				|| edtCodigoB || btnCodigB) == false) {
			return false;
		} else {
			testCase.addTextEvidenceCurrentStep("Se valida existencia de pantalla Predevolucion a Proveedores(1)");
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.scrollToElement(PP_btnSalir);
		}
		boolean artADev = au.driver.findElement(PP_ArtADevol).getText().contains("Artículos a devolver:");
		boolean piezas = au.driver.findElement(PP_PiezasADev).getText().contains("Piezas a devolver:");
		boolean total = au.driver.findElement(PP_TotalPredev).getText().contains("Total predevolución:");
		boolean cerrar = au.driver.findElement(PP_btnCerrar).isDisplayed();
		boolean imprimir = au.driver.findElement(PP_btnImprimir).isDisplayed();
		boolean salir = au.driver.findElement(PP_btnSalir).isDisplayed();
		if ((artADev || piezas || total || cerrar || imprimir || salir) == false) {
			return false;
		} else {
			testCase.addTextEvidenceCurrentStep("Se valida existencia de pantalla Predevolucion a Proveedores(2)");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * eliminarCF_NubeEvo - Elimina el ultimo Cambio Fisico realizado
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void ingresarTextoNuevaDevolucion() {
		au.driver.findElement(AppiumBy.androidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().textContains(\"CR Tienda\"))"));
		WebElement dev = au.driver.findElement(PP_edtDescripDev);
		dev.click();
		dev.clear();
		au.sendKeys(PP_edtDescripDev, "NUEVA DEVOLUCION");
		au.driver.hideKeyboard();
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaCampoDescDevolucion - Elimina el ultimo Cambio Fisico realizado
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return false/true
	 */
	public boolean validaCampoDescDevolucion(String desc) {
		boolean texto = au.driver.findElement(PP_edtDescripDev).getText().contains(desc);
		System.out.println(au.driver.findElement(PP_edtDescripDev).getText());
		System.out.println(desc);
		System.out.println(texto);
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
	 * eliminarCF_NubeEvo - Elimina el ultimo Cambio Fisico realizado
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void opcionSalirPredevolucionAProv() {
		au.scrollToElement(PP_btnSalir);
		au.click(PP_btnSalir);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * opcionOpcionesFrecuentes - Selecciona el boton Opciones Frecuentes
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void opcionOpcionesFrecuentes() {
		au.driver.findElement(AppiumBy.androidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().textContains(\"CR Tienda\"))"));
		au.click(PP_OpcionesF);
		testCase.addTextEvidenceCurrentStep("Se presiona el boton: Predevolucion a proveedor");
		testCase.addScreenShotCurrentStep(au, "testIamge");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * llegarAConsultaCamFisicos
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void llegarAConsultaCamFisicos() {
		au.click(bt_ExcOperativa);
		au.click(bt_HerrCompra);
		au.click(bt_Cometa);
		au.click(bt_CambFisicos);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * tapOpcionConsultarCamFis - Selecciona el boton Consultar Cambios Fisicos -Sin
	 * EV
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapOpcionConsultarCamFis() {
		au.click(bt_ConsultCambiosFisicos);
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaPantallaCambiosFisicos - valida los elementos de la pantalla cambios
	 * fisicos
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaPantallaCambiosFisicos() {
		boolean titulo = au.driver.findElement(Title_CambiosFisicos).isDisplayed();
		boolean capturar = au.driver.findElement(bt_CapCambiosFisicos).isDisplayed();
		boolean consultar = au.driver.findElement(bt_ConsultCambiosFisicos).isDisplayed();
		if ((titulo || capturar || consultar) == false) {
			return false;
		} else {
			testCase.addTextEvidenceCurrentStep(
					"Se valida existencia de pantalla CAMBIOS FISICOS con opciones de capturar y consultar");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * tapOpcionConsultarCamFis - Selecciona el boton Consultar Cambios Fisicos -Sin
	 * EV
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapRdBtnFuenteAbProv() {
		au.click(rb_FuenteDeAbastosProveedor);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * tapOpcionConsultarCamFis - Selecciona el boton Consultar Cambios Fisicos -Sin
	 * EV
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapRdBtnNumeroRemision() {
		au.click(rb_NumeroDeRemision);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * tapOpcionConsultarCamFis - Selecciona el boton Consultar Cambios Fisicos -Sin
	 * EV
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapBtnFinalizarTE_sinEv() {
		au.scrollToElement(TE_btnFinalizar);
		au.click(TE_btnFinalizar);
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * capturarNumRemisionCF - Selecciona el boton Consultar Cambios Fisicos -Sin EV
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void capturarNumRemisionCF() {
		// numRemisionCF = "50CMH-6";
		au.sendKeys(tb_CapFAoNumRemision, numRemisionCF);
		testCase.addTextEvidenceCurrentStep("Se ingresa un numero de Remision con cambios fisicos");
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * capturarFaCF - Selecciona el boton Consultar Cambios Fisicos -Sin EV
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void capturarFaCF() {
		// FaEvo = "4514";
		au.sendKeys(tb_CapFAoNumRemision, FaEvo);
		testCase.addTextEvidenceCurrentStep("Se ingresa un numero una FA que haya tenga CF");
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * tapBtnBuscarConsultaCF - Selecciona el boton BUSCAR Cambios Fisicos -Sin EV
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapBtnBuscarConsultaCF() {
		au.click(bt_BuscarProveedorRemision);
		testCase.addTextEvidenceCurrentStep("Se hace selecciona boton: Buscar");
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaHeaderCambioFisProgPedxRemision - Selecciona el boton BUSCAR Cambios
	 * Fisicos -Sin EV
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaHeaderCambioFisProgPedxRemision() {
		boolean fa = au.driver.findElement(hd_FuenteAbasto).isDisplayed();
		boolean prov = au.driver.findElement(hd_Proveedor).isDisplayed();
		boolean numrem = au.driver.findElement(numR).isDisplayed();
		boolean resumen = au.driver.findElement(lbResumenCFPrgramados).isDisplayed();
		if ((fa || prov || numrem || resumen) == false) {
			return false;
		} else {
			testCase.addTextEvidenceCurrentStep(
					"Se valida opciones: Fuente de Abasto, Proveedor, Numero de Remision de forma correcta");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaHeaderCambioFisProgPedxFA - Selecciona el boton BUSCAR Cambios Fisicos
	 * -Sin EV
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaHeaderCambioFisProgPedxFA() {
		boolean fa = au.driver.findElement(hd_FuenteAbasto).isDisplayed();
		boolean prov = au.driver.findElement(hd_Proveedor).isDisplayed();
		boolean resumen = au.driver.findElement(lbResumenCFPrgramados).isDisplayed();
		if ((fa || prov || resumen) == false) {
			return false;
		} else {
			testCase.addTextEvidenceCurrentStep(
					"Se valida opciones: Fuente de Abasto, Proveedor, Numero de Remision de forma correcta");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaFAyNumRemResumenCambiosProgPend - Selecciona el boton BUSCAR Cambios
	 * Fisicos -Sin EV
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaFAyNumRemResumenCambiosProgPend() {
		// FaEvo = "20820";
		boolean fa = au.driver.findElement(faValue).getText().equalsIgnoreCase(FaEvo);
		boolean num = au.driver.findElement(numRemValue).getText().equalsIgnoreCase(numRemisionCF);
		if ((fa || num) == false) {
			return false;
		} else {
			testCase.addTextEvidenceCurrentStep(
					"Se valida que el Numero de Remision y el Proveedor son los utilizados en la carga del cambio fisico");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * pantallaResumenCambFisBtnSigDisabled -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean pantallaResumenCambFisBtnSigDisabled() {
		au.scrollToElement(CFPP_btnSiguiente);
		boolean btn = au.driver.findElement(CFPP_btnSiguiente).isEnabled();
		if (btn == true) {
			return false;
		} else {
			testCase.addTextEvidenceCurrentStep("Se valida que el boton siguiente se encuentra deshabilitado");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaDatosArtProgPendientes -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaDatosArtProgPendientes(int Cant) {
		au.driver.findElement(AppiumBy.androidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"Fuente de Abasto:\"))"));
		for (int i = 1; i <= Cant; i++) {
			String padre = "//android.widget.LinearLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/lstCCambiosFisicos\"]/android.view.ViewGroup["
					+ i + "]/android.view.ViewGroup";
			if (!au.driver.findElement(By.xpath(padre
					+ "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvwCambioFisicoDescription\"]"))
					.isDisplayed()) {
				System.out.println("No halle la desc");
				return false;
			}
			if (!au.driver.findElement(By.xpath(padre
					+ "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvwCambioFisicoCodigo\"]"))
					.isDisplayed()) {
				System.out.println("No esta el sku");
				return false;
			}
			if (!au.driver.findElement(By.xpath(padre
					+ "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvwNumeroRemisionValue\"]"))
					.isDisplayed()) {
				System.out.println("No esta el num de remision");
				return false;
			}
			if (!au.driver.findElement(By.xpath(padre
					+ "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvwFechaProgramacionLabel\"]"))
					.isDisplayed()) {
				System.out.println("No esta la fecha de programacion");
				return false;
			}
			if (!au.driver.findElement(By.xpath(padre
					+ "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvwDiasTranscurridosLabel\"]"))
					.isDisplayed()) {
				System.out.println("No estan los dias transcurridos");
				return false;
			}
			if (!au.driver.findElement(By.xpath(padre
					+ "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvwCantidadLabel\"]"))
					.isDisplayed()) {
				System.out.println("No esta la cantidad de art");
				return false;
			}
			if (!au.driver.findElement(By.xpath(padre
					+ "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvwCostoLabel\"]"))
					.isDisplayed()) {
				System.out.println("No esta el costo de los art");
				return false;
			}
		}
		testCase.addTextEvidenceCurrentStep("La informacion mostrada es la correcta");
		testCase.addScreenShotCurrentStep(au, "testImage");
		return true;
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaTipoDeCambioFisicoXnumRem -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param tipo - posibles valores : PENDIENTE/REALIZADO/APLICAR
	 * 
	 * @return true/false
	 */
	public boolean validaTipoDeCambioFisicoXnumRem(String tipo) {
		String padre = "//android.widget.LinearLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/lstCCambiosFisicos\"]/android.view.ViewGroup[1]/android.view.ViewGroup";
		boolean tipoCF = au.driver
				.findElement(By.xpath(padre + "//android.widget.TextView[@resource-id=\"android:id/text1\"]")).getText()
				.contains(tipo);
		System.out.println("---- " + au.driver.findElement(CFPP_tipoCambFis).getText());
		if (tipoCF == false) {
			return false;
		}
		switch (tipo) {
		case "REALIZADO":
			boolean realizado = au.driver.findElement(CFPP_cambRealizados).isEnabled();
			boolean menos = au.driver.findElement(CFPP_btnRestar).isEnabled();
			boolean mas = au.driver.findElement(CFPP_btnSumar).isEnabled();
			boolean cant = au.driver.findElement(CFPP_cantEditada).isEnabled();
			if ((realizado || menos || mas || cant) == false) {
				return false;
			} else {
				testCase.addTextEvidenceCurrentStep(
						"Se valida que el campo muestra 'Realizado' como tipo de cambio fisico' junto a los botones de Sumar, Restar y el campo de Cantidad Editada");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			}
		case "APLICAR":
			boolean aplicar = au.driver.findElement(CFPP_piezasADevol).isEnabled();
			boolean restar = au.driver.findElement(CFPP_btnRestar).isEnabled();
			boolean sumar = au.driver.findElement(CFPP_btnSumar).isEnabled();
			boolean canti = au.driver.findElement(CFPP_cantEditada).isEnabled();
			if ((aplicar || restar || sumar || canti) == false) {
				return false;
			} else {
				testCase.addTextEvidenceCurrentStep(
						"Se valida que el campo muestra 'APLICAR DEVOLUCION' como tipo de cambio fisico' junto a los botones de Sumar, Restar y el campo de Cantidad Editada");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			}
		default:
			testCase.addTextEvidenceCurrentStep(
					"Se valida que el tipo de cambio fisico aplicado esta como 'PENDIENTE'");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaTipoDeCambioFisicoXFA -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param tipo - posibles valores : PENDIENTE/REALIZADO/APLICAR
	 * 
	 * @return true/false
	 */
	public boolean validaTipoDeCambioFisicoXFA(String tipo) {
		boolean tipoCF;
		try {
			tipoCF = au.driver.findElement(By.xpath(
					"//android.widget.TextView[@resource-id=\"android:id/text1\" and contains(@text,'" + tipo + "')]"))
					.isDisplayed();
		} catch (Exception e) {
			tipoCF = false;
		}
		if (tipoCF == false) {
			return false;
		}
		switch (tipo) {
		case "REALIZADO":
			boolean realizado = au.driver.findElement(CFPP_cambRealizados).isEnabled();
			boolean menos = au.driver.findElement(CFPP_btnRestar).isEnabled();
			boolean mas = au.driver.findElement(CFPP_btnSumar).isEnabled();
			boolean cant = au.driver.findElement(CFPP_cantEditada).isEnabled();
			if ((realizado || menos || mas || cant) == false) {
				return false;
			} else {
				testCase.addTextEvidenceCurrentStep(
						"Se valida que el campo muestra 'Realizado' como tipo de cambio fisico' junto a los botones de Sumar, Restar y el campo de Cantidad Editada");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			}
		case "APLICAR":
			boolean aplicar = au.driver.findElement(CFPP_piezasADevol).isEnabled();
			boolean restar = au.driver.findElement(CFPP_btnRestar).isEnabled();
			boolean sumar = au.driver.findElement(CFPP_btnSumar).isEnabled();
			boolean canti = au.driver.findElement(CFPP_cantEditada).isEnabled();
			if ((aplicar || restar || sumar || canti) == false) {
				return false;
			} else {
				testCase.addTextEvidenceCurrentStep(
						"Se valida que el campo muestra 'APLICAR DEVOLUCION' como tipo de cambio fisico' junto a los botones de Sumar, Restar y el campo de Piezas a Devolver");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			}
		default:
			testCase.addTextEvidenceCurrentStep(
					"Se valida que el tipo de cambio fisico aplicado esta como 'PENDIENTE'");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * cambiarTipoDeCambioFisicoXnumRem -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param tipo - posibles valores : PENDIENTE/REALIZADO/APLICAR
	 * 
	 * @return true/false
	 */
	public void cambiarTipoDeCambioFisicoXnumRem(String tipo) {
		au.click(CFPP_tipoCambFis);
		String padre = "/hierarchy/android.widget.FrameLayout[3]/android.widget.FrameLayout";
		au.click(By.xpath(padre + "//android.widget.TextView[@resource-id=\"android:id/text1\" and contains(@text, '"
				+ tipo + "')]"));
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * cambiarTipoDeCambioFisicoXFA -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param tipo - posibles valores : PENDIENTE/REALIZADO/APLICAR
	 * 
	 * @return true/false
	 */
	public void cambiarTipoDeCambioFisicoXFA(int cantArt, String tipo) {
		swipeCoord(200, 500, 200, 350, 300);
		for (int i = 1; i <= cantArt; i++) {
			String padre = "//android.widget.LinearLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/lstCCambiosFisicos\"]/android.view.ViewGroup["
					+ i + "]/android.view.ViewGroup";
			au.click(By.xpath(padre + "//android.widget.TextView[@resource-id=\"android:id/text1\"]"));
			au.click(By.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and contains(@text, '" + tipo
					+ "')]"));
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaTablaTotalesCambiosFisicos -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param tipo - posibles valores : PENDIENTE/REALIZADO/APLICAR
	 * 
	 * @return true/false
	 */
	public boolean validaTablaTotalesCambiosFisicos() {
		au.scrollToElement(CFPP_btnImprimir);
		boolean res = au.driver.findElement(CFPP_hdResumen).isDisplayed();
		boolean art = au.driver.findElement(CFPP_hdArticulos).isDisplayed();
		boolean pieza = au.driver.findElement(CFPP_hdPiezas).isDisplayed();
		boolean pend = au.driver.findElement(CFPP_rPendientes).isDisplayed();
		boolean camb = au.driver.findElement(CFPP_rCambiosARealiz).isDisplayed();
		boolean dev = au.driver.findElement(CFPP_rDevoluciones).isDisplayed();
		if ((res || art || pieza || pend || camb || dev) == false) {
			return false;
		} else {
			for (int i = 1; i <= 3; i++) {
				boolean arti = au.driver.findElement(By.xpath(
						"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_articulos_cantidad\"])["
								+ i + "]"))
						.isDisplayed();
				boolean pzs = au.driver.findElement(By.xpath(
						"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_piezas_cantidad\"])["
								+ i + "]"))
						.isDisplayed();
				if ((arti || pzs) == false) {
					break;
				}
			}
			testCase.addTextEvidenceCurrentStep(
					"Se validan los elementos: resumen articulos, piezas, pendientes, cambios,devoluciones a modo de tabla con sus campos de articulos y piezas");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaTextoMontostotalCFPP -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param tipo - posibles valores : PENDIENTE/REALIZADO/APLICAR
	 * 
	 * @return true/false
	 */
	public boolean validaCamposMontostotalesCFPP() {
		au.scrollToElement(CFPP_btnImprimir);
		boolean totalPend = au.driver.findElement(CFPP_lbMonTotalPend).getText()
				.equalsIgnoreCase("Monto total pendiente:");
		boolean totalCamb = au.driver.findElement(CFPP_lbMonTotalCambRealiz).getText()
				.equalsIgnoreCase("Monto total de cambios realizados:");
		boolean edtPend = au.driver.findElement(CFPP_edtMontTotalPend).isDisplayed();
		boolean edtCamb = au.driver.findElement(CFPP_edtMontTotalCamb).isDisplayed();
		if ((totalPend || totalCamb || edtPend || edtCamb) == false) {
			return false;
		} else {
			testCase.addTextEvidenceCurrentStep(
					"Se valida existencia de textos: Monto total Pendiente y Monto total de cambios realizados");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaSiguienteEImprimir -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param tipo - posibles valores : PENDIENTE/REALIZADO/APLICAR
	 * 
	 * @return true/false
	 */
	public boolean validaSiguienteEImprimir() {
		boolean sig = au.driver.findElement(CFPP_btnSiguiente).isDisplayed();
		boolean sigDisabled = !au.driver.findElement(CFPP_btnSiguiente).isEnabled();
		boolean imp = au.driver.findElement(CFPP_btnImprimir).isDisplayed();
		boolean impEnabled = au.driver.findElement(CFPP_btnImprimir).isEnabled();
		if ((sig || sigDisabled || imp || impEnabled) == false) {
			return false;
		} else {
			testCase.addTextEvidenceCurrentStep(
					"Se valida existencia de boton Siguiente-Deshabilitado y de boton Imprimir");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * incrementaCambiosRelizados_CCFPP -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param tipo - posibles valores : PENDIENTE/REALIZADO/APLICAR
	 * 
	 * @return true/false
	 */
	public void incrementaCambiosRelizados_CCFPP(int Cant) {
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.presenceOfElementLocated(CFPP_cambRealizados));
		for (int i = 1; i <= Cant; i++) {
			wait = new WebDriverWait(au.driver, Duration.ofSeconds(5));
			WebElement incrementar = wait.until(ExpectedConditions.presenceOfElementLocated(CFPP_btnSumar));
			incrementar.click();
		}
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * disminuyeCambiosRelizados_CCFPP -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param tipo - posibles valores : PENDIENTE/REALIZADO/APLICAR
	 * 
	 * @return true/false
	 */
	public void disminuyeCambiosRelizados_CCFPP(int Cant) {
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.presenceOfElementLocated(CFPP_cambRealizados));
		for (int i = 1; i <= Cant; i++) {
			wait = new WebDriverWait(au.driver, Duration.ofSeconds(5));
			WebElement incrementar = wait.until(ExpectedConditions.presenceOfElementLocated(CFPP_btnRestar));
			incrementar.click();
		}
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validarCantidadArticulosEditadaAumenta -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param tipo - posibles valores : PENDIENTE/REALIZADO/APLICAR
	 * 
	 * @return true/false
	 */
	public boolean validarCantidadArticulosEditadaAumenta(int Cantidad) {
		if (cantEdAntes == cantEdDespues + Cantidad) {
			return false;
		} else {
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validarCantidadArticulosAumenta -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param tipo - posibles valores : PENDIENTE/REALIZADO/APLICAR
	 * 
	 * @return true/false
	 */
	public boolean validarCantidadArticulosAumenta(int Cantidad) {
		if (cantArtDespues == cantArtAntes + Cantidad) {
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validarCantidadArticulosDisminuye -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param tipo - posibles valores : PENDIENTE/REALIZADO/APLICAR
	 * 
	 * @return true/false
	 */
	public boolean validarCantidadArticulosDisminuye(int Cantidad) {
		if (cantArtAntes == cantArtDespues + Cantidad) {
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * obtenerCantidadArtInicial -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void obtenerCantidadArtInicial() {
		String padre = "//android.widget.LinearLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/lstCCambiosFisicos\"]/android.view.ViewGroup[1]/android.view.ViewGroup";
		CantArtInicial_CCFPP = Integer.parseInt(au.driver.findElement(By.xpath(padre
				+ "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvwCantidadValue\"]"))
				.getText());
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * obtenerCantidadArt-
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void obtenerCantidadArt(String opcion) {
		String padre = "//android.widget.LinearLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/lstCCambiosFisicos\"]/android.view.ViewGroup[1]/android.view.ViewGroup";
		switch (opcion) {
		case "antes":
			cantArtAntes = Integer.parseInt(au.driver.findElement(By.xpath(padre
					+ "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvwCantidadValue\"]"))
					.getText());
			break;

		case "despues":
			cantArtDespues = Integer.parseInt(au.driver.findElement(By.xpath(padre
					+ "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvwCantidadValue\"]"))
					.getText());
			break;

		default:
			break;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * obtenerCostoInicial -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void obtenerCostoInicial() {
		String obj = "//android.widget.LinearLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/lstCCambiosFisicos\"]/android.view.ViewGroup[1]/android.view.ViewGroup//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvwCostoValue\"]";
		String cant = au.driver.findElement(By.xpath(obj)).getText();
		costoInicial_CCFPP = Integer.parseInt(cant.substring(2, cant.length() - 3));
		System.out.println("cantidad orig: " + costoInicial_CCFPP);
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * obtenerCosto -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void obtenerCosto(String opcion) {
		String obj = "//android.widget.LinearLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/lstCCambiosFisicos\"]/android.view.ViewGroup[1]/android.view.ViewGroup//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvwCostoValue\"]";
		String cant = au.driver.findElement(By.xpath(obj)).getText();
		switch (opcion) {
		case "antes":
			costoAntes = Float.parseFloat(cant.substring(2));
			break;

		case "despues":
			costoDespues = Float.parseFloat(cant.substring(2));
			break;

		default:
			break;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * obtenerCantidadEditada -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param opcion - "antes"/"despues"
	 * 
	 * @return N/A
	 */
	public void obtenerCantidadEditada(String opcion) {
		String padre = "//android.widget.LinearLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/lstCCambiosFisicos\"]/android.view.ViewGroup[1]/android.view.ViewGroup";
		switch (opcion) {
		case "antes":
			cantEdAntes = Integer.parseInt(au.driver.findElement(By.xpath(padre
					+ "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvwCantidadEditadaValue\"]"))
					.getText());
			break;

		case "despues":
			cantEdDespues = Integer.parseInt(au.driver.findElement(By.xpath(padre
					+ "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvwCantidadEditadaValue\"]"))
					.getText());
			break;

		default:
			break;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaCantidadArticulosDismuye -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaCantidadArticulosDismuye(int numero) {
		String obj = "//android.widget.LinearLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/lstCCambiosFisicos\"]/android.view.ViewGroup[1]/android.view.ViewGroup//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvwCantidadValue\"]";
		int actual = Integer.parseInt(au.driver.findElement(By.xpath(obj)).getText());
		if (actual == CantArtInicial_CCFPP - numero) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaCantidadArticulosDismuye -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaCostoDisminuyeCCFPP() {
		if (costoDespues < costoAntes) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaCostoAumentaCCFPP -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaCostoAumentaCCFPP() {
		if (costoDespues > costoAntes) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * obtienePrecioVentaXarticulo -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean obtenerPrecioVentaXarticulo(String IpPV) {
		try {
			String obj = "//android.widget.LinearLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/lstCCambiosFisicos\"]/android.view.ViewGroup[1]/android.view.ViewGroup//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvwCambioFisicoCodigo\"]";
			String sku = au.driver.findElement(By.xpath(obj)).getText();
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + IpPV
							+ ":1433;databaseName=Satelites;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV10_17, GlobalVariables.passwordPV10_17);
			String query = "EXEC Satelites.hc.GetInfoSku '" + sku + "'";
			SQLResult res = new SQLResult(conn, query);
			precioVentaCCFPP = Float.parseFloat(res.getData(0, "PrecioVenta"));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * obtenerPrecioVentaXarticulo_sku -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean obtenerPrecioVentaXarticulo_sku(String IpPV, String art) {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + IpPV
							+ ":1433;databaseName=Satelites;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV10_17, GlobalVariables.passwordPV10_17);
			String query = "EXEC Satelites.hc.GetInfoSku '" + art + "'";
			SQLResult res = new SQLResult(conn, query);
			precioVentaCCFPP = Float.parseFloat(res.getData(0, "PrecioVenta"));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaCostoTotal_CCFPP - valida que la cantidad mostrada en el campo 'Costo'
	 * sea la adecuada (costoVenta * canti articulos)
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaCostoTotal_CCFPP(String IpPV) {
		String padre = "//android.widget.LinearLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/lstCCambiosFisicos\"]/android.view.ViewGroup[1]/android.view.ViewGroup";
		Float cantArt = Float.parseFloat(au.driver.findElement(By.xpath(padre
				+ "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvwCantidadValue\"]"))
				.getText());
		String obj = "//android.widget.LinearLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/lstCCambiosFisicos\"]/android.view.ViewGroup[1]/android.view.ViewGroup//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvwCostoValue\"]";
		String cant = au.driver.findElement(By.xpath(obj)).getText();
		Float costoActual = Float.parseFloat(cant.substring(2));
		obtenerPrecioVentaXarticulo(IpPV);
		if (cantArt * precioVentaCCFPP == costoActual) {
			testCase.addTextEvidenceCurrentStep("Se valida que la cantidad mostrada en el campo COSTO es la correcta");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * incrementarBtnMas_CCFPP - incrementa la cantidad de cambios realizados CCFPP
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void incrementarBtnMas_CCFPP(int cantArt, int numClicks) {
		stringArtModifCF.clear();
		for (int j = 1; j <= cantArt && j < 3; j++) {
			String padre = "//android.widget.LinearLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/lstCCambiosFisicos\"]/android.view.ViewGroup["
					+ j + "]/android.view.ViewGroup";
			String completo = padre
					+ "//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btn_sumar\"]";
			listaDeCambiosRealizados(j);
			for (int i = 1; i <= numClicks; i++) {
				WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(5));
				WebElement incrementar = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(completo)));
				incrementar.click();
			}
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaMsgExedidoCantidadCambFis - valida existe el msg: Se ha excedido la
	 * cantidad de productos con cambios físicos
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaMsgExedidoCantidadCambFis() {
		boolean msg = au.driver.findElement(CFPP_msgCFExedido).isDisplayed();
		if (msg == false) {
			return false;
		} else {
			testCase.addTextEvidenceCurrentStep(
					"Se valida se muestra el warning 'Se ha excedido la cantidad de productos con cambios físicos' con la opción <ACEPTAR>");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * btnAceptarGral -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void btnAceptarGral() {
		au.click(btnAceptarGral);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	public void btnAceptarGral_sinEv() {
		au.click(btnAceptarGral);
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * btnAceptarGral -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaCampoCantidadTieneLaCantMax() {
		boolean cant = Integer.parseInt(au.driver.findElement(CFPP_cantEditada).getText()) == CantArtInicial_CCFPP;
		if (cant == false) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * cargaMultipleCambiosFisicos -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void cargaMultipleCambiosFisicos(int cantCF, int cantArt, int cant, int cantSuma, boolean mismaFA,
			boolean mismosArt) {
		for (int k = 1; k <= cantCF; k++) {
			au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
			boolean herr;
			try {
				herr = au.driver.findElement(titleHerramientas).isDisplayed();
			} catch (Exception e) {
				herr = false;
			}
			if (herr == true) {
				au.click(bt_CambFisicos);
				System.out.println("si esta");
			} else {
				System.out.println("no estaba");
			}
			au.click(bt_CapCambiosFisicos);
			// -----
			if ((FaEvo == null || FaEvo == "") || (mismaFA == false && k > 1)) {
				ObtenerFAValidaNubeEvo(cantArt, cant);
			}
			// ------
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
			wait.until(ExpectedConditions.presenceOfElementLocated(tb_CapturaFuenteAbasto));
			au.sendKeys(tb_CapturaFuenteAbasto, FaEvo);
			// -----
			au.click(bt_Buscar);
			// ---- Opcion Continuar aun que la FA ya tenga otro CF realizado
			boolean msg;
			try {
				au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
				msg = au.driver.findElement(By.xpath(
						"//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"Ya se han realizado cambios físicos para este proveedor el día de hoy. ¿Desea continuar?\"]"))
						.isDisplayed();
			} catch (Exception e) {
				msg = false;
			}
			if (msg == true) {
				au.click(By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]"));
				au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
			}
			// -----
			if ((mismosArt == false && k > 1) || SkusEvo.isEmpty()) {
				obtenerSkusValidosxFANubeEvo(cantArt, cant);
			}
			// -----
			wait.until(ExpectedConditions.presenceOfElementLocated(tb_CapturaSku));
			for (int i = 0; i < SkusEvo.size(); i++) {
				au.driver.findElement(tb_CapturaSku).sendKeys(SkusEvo.get(i));
				btnIngresoSku();
				au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			}
			// -----
			incrementarBtnMas_varios(cantSuma, cantArt);
			au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			Mofid_EditCFProgramado_varios(cantArt, 2);
			// -----
			au.scrollToElement(btn_Siguiente);
			au.click(btn_Siguiente);
			// -----
			au.scrollToElement(AEObservacionesProveedor);
			au.click(AEObservacionesProveedor);
			au.click(AEOpcionSinComentarios);
			// -----
			au.scrollToElement(lbObsProv);
			au.click(AEfirmaProveedor);
			au.click(AEfirma);
			au.click(AEbtnContinuar);
			// -----
			au.scrollToElement(AEObservacionesTienda);
			au.click(AEObservacionesTienda);
			au.click(AEOpcionSinComentarios);
			// -----
			au.scrollToElement(AEBtnSiguiente);
			au.click(AEfirmaTienda);
			au.click(AEfirma);
			au.click(AEbtnContinuar);
			// -----
			au.scrollToElement(AEBtnSiguiente);
			au.click(AERdBtnCumpProcCambFis);
			// -----
			wait = new WebDriverWait(au.driver, Duration.ofSeconds(20));
			WebElement siguiente = wait.until(ExpectedConditions.presenceOfElementLocated(AEBtnSiguiente));
			siguiente.click();
			// -----
			wait = new WebDriverWait(au.driver, Duration.ofSeconds(20));
			WebElement aceptar = wait.until(ExpectedConditions.presenceOfElementLocated(AEbtnAceptar));
			aceptar.click();
			try {
				WebDriverWait wait2 = new WebDriverWait(au.driver, Duration.ofSeconds(45));
				WebElement aceptar2 = wait2.until(ExpectedConditions.presenceOfElementLocated(AEbtnAceptar));
				aceptar2.click();
			} catch (Exception e) {

			}
			// -----
			obtenerNumRemCfPantallaTiraEntrega();
			CFMultiplesFAs.add(k - 1, FaEvo);
			CFMultiplesNumRem.add(k - 1, numRemisionCF);
			tapBtnFinalizarTE_sinEv();
			System.out.println(CFMultiplesFAs.size());
			System.out.println(CFMultiplesNumRem.size());
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * modificarFechaCF_atras -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean modificarFechaCF_atras(int dias, String numRemision) {
		try {
			String fecha = LocalDate.now().minusDays(dias).toString();
			Connection conn = DriverManager.getConnection(GlobalVariables.urlEvolucion, GlobalVariables.userEvolucion,
					PasswordUtil.decryptPassword(GlobalVariables.passEvolucion));
			String query = "UPDATE [XposCloudPurchaseToolQA].[dbo].[DI_PHYSICAL_CHANGES_DETAIL] \r\n"
					+ "SET CREATED_DATETIME_BUSINESS = '" + fecha
					+ " 10:00:00.680' WHERE DI_PHYSICAL_CHANGES_HEADER_ID = (SELECT TOP (1000) [DI_PHYSICAL_CHANGES_HEADER_ID]\r\n"
					+ "FROM [XposCloudPurchaseToolQA].[dbo].[DI_PHYSICAL_CHANGES_HEADER]WHERE STORE_PLACE_ID = '"
					+ storePlaceID + "' and REMISION_NUMBER = '" + numRemision + "'\r\n"
					+ "Order by CREATED_DATETIME desc)";
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(query);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * modificarFechaCF_atras -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void modificarFechaCF_filtrosDias(int cant) {
		if (cant == 1) {
			modificarFechaCF_atras(1, CFMultiplesNumRem.get(0));
		} else {
			modificarFechaCF_atras(1, CFMultiplesNumRem.get(0));
			modificarFechaCF_atras(15, CFMultiplesNumRem.get(1));
			modificarFechaCF_atras(22, CFMultiplesNumRem.get(2));
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * modificarFechaCF_atras -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void obtenerCantPendTablaUnidadesTotales() {
		au.scrollToElement(CFPP_btnImprimir);
		pendArt = au.driver.findElement(CFPP_cantPendArt).getText();
		pendPiezas = au.driver.findElement(CFPP_cantPendPiezas).getText();
		realizArt = au.driver.findElement(CFPP_cantRealizArt).getText();
		realizPiezas = au.driver.findElement(CFPP_cantRealizPiezas).getText();
		devoArt = au.driver.findElement(CFPP_cantDevoArt).getText();
		devoPiezas = au.driver.findElement(CFPP_cantDevoPiezas).getText();
		System.out.println(pendArt + ", " + pendPiezas + ", " + realizArt + ", " + realizPiezas + ", " + devoArt + ", "
				+ devoPiezas);
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * stringDeRemisiones -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void stringDeRemisiones() {
		String remisiones = "";
		for (int i = 1; i <= OAD.CFMultiplesNumRem.size(); i++) {
			if (i < OAD.CFMultiplesNumRem.size()) {
				remisiones = remisiones + CFMultiplesNumRem.get(i - 1) + "','";
			} else {
				remisiones = remisiones + CFMultiplesNumRem.get(i - 1);
			}
			stringRem = remisiones;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validarCantidadesTablaUnidadesTotalesBD -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validarCantidadesTablaUnidadesTotalesBD(String remisiones) {
		au.scrollToElement(CFPP_btnImprimir);
		String b = "", c = "";
		try {
			Connection conn = DriverManager.getConnection(GlobalVariables.urlEvolucion, GlobalVariables.userEvolucion,
					PasswordUtil.decryptPassword(GlobalVariables.passEvolucion));
			String queryTotalArt = "SELECT Count([DI_PHYSICAL_CHANGES_HEADER_ID])\r\n"
					+ "FROM [XposCloudPurchaseToolQA].[dbo].[DI_PHYSICAL_CHANGES_DETAIL] WHERE DI_PHYSICAL_CHANGES_HEADER_ID in (SELECT TOP (1000) [DI_PHYSICAL_CHANGES_HEADER_ID]\r\n"
					+ "FROM [XposCloudPurchaseToolQA].[dbo].[DI_PHYSICAL_CHANGES_HEADER]WHERE STORE_PLACE_ID = '"
					+ OAD.storePlaceID + "' AND REMISION_NUMBER in ('" + remisiones + "'))";
			String queryTotalPiezas = "SELECT cast (sum(QUANTITY_PENDING)AS int)\r\n"
					+ "FROM [XposCloudPurchaseToolQA].[dbo].[DI_PHYSICAL_CHANGES_DETAIL] WHERE DI_PHYSICAL_CHANGES_HEADER_ID in (SELECT TOP (1000) [DI_PHYSICAL_CHANGES_HEADER_ID]\r\n"
					+ "FROM [XposCloudPurchaseToolQA].[dbo].[DI_PHYSICAL_CHANGES_HEADER]WHERE STORE_PLACE_ID = '"
					+ OAD.storePlaceID + "' AND REMISION_NUMBER in ('" + remisiones + "'))";
			System.out.println("Remisiones: '" + remisiones + "'");
			SQLResult res = new SQLResult(conn, queryTotalArt);
			b = res.getFirstData().toString();
			SQLResult res2 = new SQLResult(conn, queryTotalPiezas);
			c = res2.getFirstData().toString();
			conn.close();
		} catch (Exception e) {
			return false;
		}
		if (!pendArt.equals(b)) {
			return false;
		} else if (!pendPiezas.equals(c)) {
			return false;
		} else {
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * ModificarPeriodoFechas_CCFPP -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param String opcion - "0 A 14" / "15 A 21" / "DE 21" / "TODOS"
	 * 
	 * @return N/A
	 */
	public void ModificarPeriodoFechas_CCFPP(String opcion) {
		au.driver.findElement(AppiumBy.androidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"Fuente de Abasto:\"))"));
		au.click(CFPP_filtroFechas);
		au.click(By.xpath(
				"//android.widget.TextView[@resource-id=\"android:id/text1\" and contains(@text, '" + opcion + "')]"));
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validarFiltroAplicado -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param String opcion - "0 A 14" / "15 A 21" / "DE 21" / "TODOS"
	 * 
	 * @return N/A
	 */
	public boolean validarFiltroAplicado(String opcion) {
		boolean filtro = au.driver.findElement(CFPP_filtroFechas).getText().contains(opcion);
		if (filtro == false) {
			System.out.println("nel");
			return false;
		} else {
			System.out.println("smn");
			return true;
		}
	}

	public void decompress(String txtQr) {
		try {
			au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
			String ruta = "src\\test\\java\\directos\\mobile\\";
			String extTxt = "hehe.txt";

			Files.deleteIfExists(Paths.get(ruta + extTxt));
			File archivoTxt = new File(ruta + extTxt);
			// Crear el archivo y escribir contenido
			FileWriter writer = new FileWriter(archivoTxt);
			writer.write(getDecompressedStringOutput(txtQr));
			writer.close();

		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep(" ");
		}
	}

	public String getDecompressedStringOutput(final String base64CompressedInput) throws IOException {
		final StringInputStream inputStream = new StringInputStream(base64CompressedInput);
		final Base64InputStream base64InputStream = new Base64InputStream(inputStream);
		final GZIPInputStream gzipInputStream = new GZIPInputStream(base64InputStream);

		final ByteArrayOutputStream out = new ByteArrayOutputStream();

		final int BUFFER_SIZE = 2048;
		final byte[] buffer = new byte[BUFFER_SIZE];
		int n = 0;
		while (-1 != (n = gzipInputStream.read(buffer))) {
			out.write(buffer, 0, n);
		}
		out.close();
		gzipInputStream.close();
		base64InputStream.close();
		inputStream.close();
		final String decompressedString = out.toString("UTF-8");
		return decompressedString;
	}

	public boolean buscarActivCodeYDeviceId(String cadenaABuscar, int finString) {
		String ruta = "src\\test\\java\\directos\\mobile\\";
		String extTxt = "hehe.txt";
		try (BufferedReader br = new BufferedReader(new FileReader(ruta + extTxt))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains(cadenaABuscar)) {
					int empieza = line.indexOf(cadenaABuscar);
					System.out.println("Si esta: " + line.substring(empieza, empieza + finString));
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("No esta :c");
		return false;
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validarCantTadUnidadesTotalesDespuesCambios -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validarCantTadUnidadesTotalesDespuesCambios(String remisiones) {
		au.scrollToElement(CFPP_btnImprimir);
		int totalArtBD = 0, totalPiezasBD = 0;
		try {
			Connection conn = DriverManager.getConnection(GlobalVariables.urlEvolucion, GlobalVariables.userEvolucion,
					PasswordUtil.decryptPassword(GlobalVariables.passEvolucion));
			String queryTotalArt = "SELECT Count([DI_PHYSICAL_CHANGES_HEADER_ID])\r\n"
					+ "FROM [XposCloudPurchaseToolQA].[dbo].[DI_PHYSICAL_CHANGES_DETAIL] WHERE DI_PHYSICAL_CHANGES_HEADER_ID in (SELECT TOP (1000) [DI_PHYSICAL_CHANGES_HEADER_ID]\r\n"
					+ "FROM [XposCloudPurchaseToolQA].[dbo].[DI_PHYSICAL_CHANGES_HEADER]WHERE STORE_PLACE_ID = '"
					+ storePlaceID + "' AND REMISION_NUMBER in ('" + remisiones + "'))";
			String queryTotalPiezas = "SELECT cast (sum(QUANTITY_PENDING)AS int)\r\n"
					+ "FROM [XposCloudPurchaseToolQA].[dbo].[DI_PHYSICAL_CHANGES_DETAIL] WHERE DI_PHYSICAL_CHANGES_HEADER_ID in (SELECT TOP (1000) [DI_PHYSICAL_CHANGES_HEADER_ID]\r\n"
					+ "FROM [XposCloudPurchaseToolQA].[dbo].[DI_PHYSICAL_CHANGES_HEADER]WHERE STORE_PLACE_ID = '"
					+ storePlaceID + "' AND REMISION_NUMBER in ('" + remisiones + "'))";
			System.out.println("Remisiones: '" + remisiones + "'");
			SQLResult res = new SQLResult(conn, queryTotalArt);
			totalArtBD = Integer.parseInt(res.getFirstData().toString());
			SQLResult res2 = new SQLResult(conn, queryTotalPiezas);
			totalPiezasBD = Integer.parseInt(res2.getFirstData().toString());
			conn.close();
		} catch (Exception e) {
			return false;
		}
		int totalArtTabla = Integer.parseInt(pendArt) + Integer.parseInt(realizArt) + Integer.parseInt(devoArt);
		int totalPiezasTabla = Integer.parseInt(pendPiezas) + Integer.parseInt(realizPiezas)
				+ Integer.parseInt(devoPiezas);
		System.out.println(totalArtTabla + " " + totalArtBD + " " + totalPiezasTabla + " " + totalPiezasBD);
		if (totalArtBD != totalArtTabla) {
			return false;
		}
		if (totalPiezasBD != totalPiezasTabla) {
			return false;
		} else {
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * listaDeCambiosRealizados -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void listaDeCambiosRealizados(int indice) {
		String obj = "//android.widget.LinearLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/lstCCambiosFisicos\"]/android.view.ViewGroup["
				+ indice
				+ "]/android.view.ViewGroup//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvwCambioFisicoCodigo\"]";
		String sku = au.driver.findElement(By.xpath(obj)).getText();
		stringArtModifCF.add(sku);
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * ScrollToTop_xTexto -
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
	 * validaCantidadesMontosTotalesCCFPP -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaCantidadesMontosTotalesCCFPP(String IpPV, int cantArt, int cantModif) {
		try {
			au.scrollToElement(CFPP_btnImprimir);
			for (int i = 0; i < cantArt; i++) {
				String art = stringArtModifCF.get(i);
				Connection conn = DriverManager.getConnection(
						"jdbc:sqlserver://" + IpPV
								+ ":1433;databaseName=Satelites;encrypt=true;trustServerCertificate=true",
						GlobalVariables.userPV10_17, GlobalVariables.passwordPV10_17);
				String query = "EXEC Satelites.hc.GetInfoSku '" + art + "'";
				System.out.println(art);
				SQLResult res = new SQLResult(conn, query);
				System.out.println(res.getData(0, "PrecioVenta"));
				stringArtModifCF.set(i, res.getData(0, "PrecioVenta"));
			}
		} catch (Exception e) {
			return false;
		}
		for (int i = 0; i < cantArt; i++) {
			montoTotalCambRealiz = (Float.parseFloat(stringArtModifCF.get(i)) * cantModif) + montoTotalCambRealiz;
		}
		if (Float.parseFloat(
				(au.driver.findElement(CFPP_edtMontTotalCamb).getText()).substring(2)) == montoTotalCambRealiz) {
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaTablaUnidadesTotalesCambARealiz -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaTablaUnidadesTotalesCambARealiz(int cantArt, int piezas) {
		if (Integer.parseInt(realizArt) != cantArt || Integer.parseInt(realizPiezas) != piezas) {
			return false;
		} else {
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaTablaUnidadesTotalesDevoluciones -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean validaTablaUnidadesTotalesDevoluciones(int cantArt, int piezas) {
		if (Integer.parseInt(devoArt) != cantArt || Integer.parseInt(devoPiezas) != piezas) {
			return false;
		} else {
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * AgregarArt_EditCFisicoProgramado - ingresa en el campo C. Fisicos Pendientes
	 * la cantidad deseada
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public void Mofid_EditCFProgramado2(int cantArt, int cantidad) {
		for (int i = 1; i <= cantArt; i++) {
			// String padre =
			// "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/rvwListaCambiosFisicos\"
			// and @index\""+i+2+"\"]/android.view.ViewGroup["+i+"]";
			// String completo =
			// padre+"//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edt_cfisico_programado\"]";
			String completo = "(//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edt_cfisico_programado\"])["
					+ i + "]";
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(5));
			WebElement campo = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(completo)));
			campo.click();
			campo.clear();
			au.driver.hideKeyboard();
			campo.sendKeys(String.valueOf(cantidad));
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * llenaYValidaComentariosYFirmas_AE -
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void llenaYValidaComentariosYFirmas_AE() {
		au.scrollToElement(AEObservacionesProveedor);
		au.click(AEObservacionesProveedor);
		au.click(AEOpcionSinComentarios);
		// ---
		validaObservacionesProveedorCapturada("sin");
		// ---
		au.scrollToElement(lbObsProv);
		screenShotFirma("antes");
		au.click(AEfirmaProveedor);
		au.click(AEfirma);
		au.click(AEbtnContinuar);
		screenShotFirma("despues");
		// ---
		validaCampoFirmaCapturado("antes", "despues");
		// ---
		au.scrollToElement(AEObservacionesTienda);
		au.click(AEObservacionesTienda);
		au.click(AEOpcionSinComentarios);
		// ---
		validaObservacionesTiendaCapturada("sin");
		//
		au.scrollToElement(AEBtnSiguiente);
		screenShotFirma("antes");
		au.click(AEfirmaTienda);
		au.click(AEfirma);
		au.click(AEbtnContinuar);
		screenShotFirma("despues");
		// ---
		validaCampoFirmaCapturado("antes", "despues");
		// ---
		au.scrollToElement(AEBtnSiguiente);
		au.click(AERdBtnCumpProcCambFis);
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * tapBtnSiguienteCCFPP -
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapBtnSiguienteCCFPP() {
		au.scrollToElement(CFPP_btnImprimir);
		au.click(CFPP_btnSiguiente);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * incrementarBtnMas_varios -
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void incrementarBtnMas_varios(int cantClicks, int cantArt) {
		ScrollToTop_xTexto("Fuente de Abasto:");
		if (cantArt <= 2) {
			for (int i = 1; i <= cantArt; i++) {
				for (int j = 1; j < cantClicks; j++) {
					WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(40));
					WebElement incrementar = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
							"(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnAddCambioFisico\"])"
									+ "[" + i + "]")));
					incrementar.click();
				}
			}
		} else {
			for (int i = 1; i <= 2; i++) {
				for (int j = 1; j < cantClicks; j++) {
					WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(10));
					WebElement incrementar = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
							"(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnAddCambioFisico\"])"
									+ "[" + i + "]")));
					incrementar.click();
				}
			}
			for (int i = 1; i <= cantArt - 2; i++) {
				swipeCoord(200, 850, 200, 520, 500);
				for (int j = 1; j < cantClicks; j++) {
					WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(10));
					WebElement incrementar = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
							"(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnAddCambioFisico\"])[3]")));
					incrementar.click();
				}
			}
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * Mofid_EditCFProgramado_varios -
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void Mofid_EditCFProgramado_varios(int cantArt, int cantidad) {
		ScrollToTop_xTexto("Fuente de Abasto:");
		swipeCoord(200, 700, 200, 550, 1000);
		if (cantArt <= 2) {
			for (int i = 1; i <= cantArt; i++) {
				String path = "(//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edt_cfisico_programado\"])["
						+ i + "]";
				WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
				WebElement valor = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(path)));
				valor.click();
				valor.clear();
				au.driver.hideKeyboard();
				valor.sendKeys(String.valueOf(cantidad));
			}
		} else {
			for (int i = 1; i <= 2; i++) {
				String path = "(//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edt_cfisico_programado\"])["
						+ i + "]";
				WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
				WebElement valor = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(path)));
				valor.click();
				valor.clear();
				au.driver.hideKeyboard();
				valor.sendKeys(String.valueOf(cantidad));
			}
			for (int i = 1; i <= cantArt - 2; i++) {
				String path = "(//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edt_cfisico_programado\"])[3]";
				WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
				WebElement valor = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(path)));
				valor.click();
				valor.clear();
				au.driver.hideKeyboard();
				valor.sendKeys(String.valueOf(cantidad));
			}
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * llenaYValidaComentariosYFirmas_AE -
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void genearQrPedidoPreventaHtaCompra(int art, int inv) {
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(7));
		wait.until(ExpectedConditions.presenceOfElementLocated(bt_ExcOperativa));
		au.click(bt_ExcOperativa);
		au.click(bt_HerrCompra);
		au.click(bt_Cometa);
		au.click(bt_VerMercancia);
		au.click(bt_genHtaCompra);
		//au.click(bt_NoGral);
		au.click(rb_genPedidoPreventa);
		if (FaEvo == "" | FaEvo == null) {
			ObtenerFAValidaNubeEvo(art, inv);
		}
		// FaEvo = "20805";
		au.sendKeys(tb_numProveedor, FaEvo);
		au.click(bt_genCodigosQR);
		boolean multipleQr;
		try {
			multipleQr = au.driver.findElement(lb_cantQrs).isDisplayed();
		} catch (Exception e) {
			multipleQr = false;
		}
		if (multipleQr == true) {
			String num = au.driver.findElement(lb_cantQrs).getText();
			totalQRs = Integer.parseInt(num.substring(num.indexOf("DE") + 3));
			WebDriverWait wait2 = new WebDriverWait(au.driver, Duration.ofSeconds(45));
			wait2.until(ExpectedConditions.presenceOfElementLocated(bt_siguienteQr));
			for (int i = 0; i <= totalQRs - 1; i++) {
				String qrActual = au.driver.findElement(tb_QrGeneradoHtaCompra).getText();
				OAD.codigosQR.add(i, qrActual);
				au.click(bt_siguienteQr);
			}
			for (int i = 0; i < codigosQR.size(); i++) {
				System.out.println(codigosQR.get(i));
			}
		} else {
			String qrActual = au.driver.findElement(tb_QrGeneradoHtaCompra).getText();
			OAD.codigosQR.add(qrActual);
			System.out.println(OAD.codigosQR.get(0));
		}

	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * llegarAPantallaVerfiMercancia -
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void llegarAPantallaVerfiMercancia() {
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(7));
		wait.until(ExpectedConditions.presenceOfElementLocated(bt_ExcOperativa));
		au.click(bt_ExcOperativa);
		au.click(bt_HerrCompra);
		au.click(bt_Cometa);
		au.click(bt_VerMercancia);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaExistePantallaProveedoresDirectos -
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaExistePantallaProveedoresDirectos() {
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.presenceOfElementLocated(titleProveedoresDirectos));
		boolean title = au.driver.findElement(titleProveedoresDirectos).isDisplayed();
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
	 * tapBtnBusquedaPedPrecargados -
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void tapBtnBusquedaPedPrecargados() {
		au.click(bt_busquedaPedPrecargados);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaExistePantallaBusquedaDePedido_PP -
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaExistePantallaBusquedaDePedido_PP() {
		boolean title = au.driver.findElement(titleBusquedaPedidos).isDisplayed();
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
	 * validaExisteTextoPedidosPrecargados_PP -
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaEncabezadoPedidosPrecargados_PP() {
		boolean title = au.driver.findElement(lb_pedidosPP).isDisplayed();
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
	 * validaExisteTextoBusquedaPor_PP -
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaEncabezadoBusquedaPor_PP() {
		boolean title = au.driver.findElement(lb_busquedaPorPP).isDisplayed();
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
	 * validaExisteTextoPedidos_PP -
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean validaEncabezadoPedidos_PP() {
		boolean title = au.driver.findElement(lb_pedidosPP).isDisplayed();
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
	 * simularVentas - Simula la venta de una articulo todos los dias x cantidad de
	 * semanas
	 * 
	 * @author Alex Aquino
	 * 
	 * @param semanas - numero de semanas hacia atras desde donde se quiere aplicar
	 * la venta
	 * 
	 * @param art - sku al que se le aplicaran las ventas
	 * 
	 * @param cantVta - cantidad de veces que se vendio el articulo durante el dia
	 * 
	 * @return N/A
	 */
	public boolean simularVentas(int semanas, String art, int cantVta, String IpPV) {
		int semanaTarget = semanas;
		LocalDate fechaPartida = semanaTarget == 0 ? LocalDate.now() : LocalDate.now().minusDays(semanaTarget * 7);
		LocalDate fechaHastaDomingo = fechaPartida.with(DayOfWeek.SUNDAY);
		LocalDate inicioSemana = fechaPartida.isBefore(fechaHastaDomingo) ? fechaHastaDomingo.minusDays(7)
				: fechaHastaDomingo;
		long rango = ChronoUnit.DAYS.between(inicioSemana, LocalDate.now());
		int ventas = cantVta;
		String sku = art;
		String fechaVenta = inicioSemana.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		for (int i = 1; i <= rango; i++) {
			LocalDate fecha = LocalDate.parse(fechaVenta);
			int diaSemana = fecha.getDayOfWeek().getValue() == 7 ? 1 : fecha.getDayOfWeek().getValue() + 1;
			int semana = diaSemana == 1 ? fecha.get(WeekFields.ISO.weekOfWeekBasedYear()) + 1
					: fecha.get(WeekFields.ISO.weekOfWeekBasedYear());
			int ano = fecha.getYear();
			try {
				String query = "USE [XposStore] \r\n" + "DECLARE @NextID INT; \r\n"
						+ "SELECT @NextID = MAX([ID_ITM_STCS]) + 1 FROM [XposStore].[dbo].[ITM_STCS_OX]; \r\n"
						+ "PRINT @NextID \r\n" + " \r\n" + "INSERT INTO [dbo].[ITM_STCS_OX] \r\n"
						+ "([ID_ITM_STCS], [DY_BSN],[YR],[CW],[DY],[CD_STCS_ID],[SKU],[VL_STCS],[FL],[LCNT],[ID_OPR_CRT],[TM_CRT],[ID_OPR_MDY],[TM_MDY]) \r\n"
						+ "VALUES(@NextID \r\n" + ",'" + fechaVenta + "' \r\n" + ",'" + ano + "' \r\n" + "," + semana
						+ " \r\n" + "," + diaSemana + " \r\n" + ",340\r\n" + ",'" + sku + "'\r\n" + "," + ventas
						+ " \r\n" + ",1,1,0,GETDATE(),null,null)\r\n";
				Connection conn = DriverManager.getConnection("jdbc:sqlserver://"+IpPV+";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
						GlobalVariables.userPV10_17, GlobalVariables.passwordPV10_17);
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(query);
				stmt.close();
				conn.close();
			} catch (Exception e) {
				return false;
			}
			fechaVenta = fecha.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		return true;
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validación de Pantalla Usuario y contraseña.
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 09/08/2024
	 */

	public boolean PantallaUsuarioContraseña() {
		try {

			boolean Usuario = au.driver.findElement(txt_Usuario).isEnabled();
			if (Usuario == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/********************************************************************************************************/
	/**
	 * Validación de Muestra Menú Principal Oxxo al Dia.
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 09/08/2024
	 */
	public boolean PantallaPrincipalOxxoAlDia() {
		try {
			boolean Menuprincipal = au.driver.findElement(mnu_MenuPrincipal).isEnabled();
			if (Menuprincipal == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			return false;
		}
	}

	/********************************************************************************************************
	 * Método - Click boton Excelencia Operativa
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 09/08/2024
	 */
	public void ClickExcelenciaOperativa() {
		au.click(btn_ExcelenciaOperativa);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/********************************************************************************************************
	 * Método - Pantalla de Menú Excelencia Operativa
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 09/08/2024
	 */
	public boolean MenuExcelenciaOperativa() {
		try {

			boolean MenuexcelenciaO = au.driver.findElement(mnu_ExcelenciaOperativa).isEnabled();
			if (MenuexcelenciaO == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			return false;
		}
	}

	/********************************************************************************************************
	 * Método - Click boton Herramienta de Compra
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 09/08/2024
	 */
	public void ClickHerramientadeCompra() {
		au.click(bt_HerrCompra);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/********************************************************************************************************
	 * Método - Pantalla de Menú Excelencia Operativa
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 09/08/2024
	 */
	public boolean MenuHerramientadeCompra() {
		try {

			boolean Menuherramienta = au.driver.findElement(mnu_HerramientaDeCompra).isEnabled();
			if (Menuherramienta == false) {
				return false;
			}
			boolean botonMovilidad = au.driver.findElement(btn_Movilidad).isEnabled();
			if (botonMovilidad == false) {
				return false;
			}
			boolean botonCometa = au.driver.findElement(btn_Cometa).isEnabled();
			if (botonCometa == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			return false;
		}
	}

	/********************************************************************************************************
	 * Método - Click en el boton Cometa
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 09/08/2024
	 */
	public void ClickCometa() {
		au.click(btn_Cometa);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/********************************************************************************************************
	 * Método - Menu Herramientas
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 09/08/2024
	 */
	public boolean MenuHerramientas() {
		testCase.addTextEvidenceCurrentStep("Muestra Menú \"Herramientas\" con las  opciones:\n"
				+ "\"Recepción de Pedidos CEDIS\"\n" + "\"Verificación de Mercancía Pedido CEDIS\"\n"
				+ "\"Consultar Tiras de Entrega\"\n" + "\"Verificación de Mercancía Pedidos Proveedor Directo\"\n"
				+ "\"Cambios Físicos de Producto (Proveedores Directos)\".\n"
				+ "\"Devoluciones de productos (Proveedores Directos)\".\n");
		try {

			boolean RecepcionCedis = au.driver.findElement(btn_RecepcionCedis).isEnabled();
			if (RecepcionCedis == false) {
				return false;
			}
			boolean VerificacionCedis = au.driver.findElement(btn_VerificacionCedis).isEnabled();
			if (VerificacionCedis == false) {
				return false;
			}
			boolean ConsultaTirasdeEntrega = au.driver.findElement(btn_ConsultaTirasDeEntrega).isEnabled();
			if (ConsultaTirasdeEntrega == false) {
				return false;
			}
			boolean VerificacionDirectos = au.driver.findElement(btn_VerificacionProveedorDirecto).isEnabled();
			if (VerificacionDirectos == false) {
				return false;

			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.scrollToElement(btn_DevolucionDeProductos);
			boolean CambiosFisicos = au.driver.findElement(btn_CambiosFisicosProveedores).isEnabled();
			if (CambiosFisicos == false) {
				return false;

			}
			boolean Devolucion = au.driver.findElement(btn_DevolucionDeProductos).isEnabled();
			if (Devolucion == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			return false;
		}
	}

	/********************************************************************************************************
	 * Método - boton Cambios fisicos deproductos (Proveedores Directos)
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 09/08/2024
	 */
	public void BotonCambiosFisicos() {
		au.click(btn_ExcelenciaOperativa);
		au.click(bt_HerrCompra);
		au.click(btn_Cometa);
		au.scrollToElement(btn_DevolucionDeProductos);
		au.click(btn_CambiosFisicosProveedores);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/********************************************************************************************************
	 * Método - Click Captura Cambios Fisicos
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 09/08/2024
	 */
	public void ClickCapturacambiosfisicos() {
		au.click(bt_CapCambiosFisicos);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/********************************************************************************************************
	 * Método - Pantalla Captura Cambios Fisicos
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 13/09/2024
	 */
	public boolean pantallaConsultaCambiosFisiscos() {
		testCase.addTextEvidenceCurrentStep("Muestra pantalla \"CAMBIOS FÍSICOS\" con las siguientes opciones:\n"
				+ "Ícono [<-]\n" + "Texto  \"CAMBIOS FÍSICOS\"\n" + "Ícono [?]\n" + "Ícono <i>\n"
				+ "Cuadro de texto (\"Fuente de abasto\")\n" + "Botón <Buscar>\n"
				+ "Cuadro de texto (\"Código de Barras\")\n" + "Botón [||||] para leer codigos");
		try {

			boolean BotonBack = au.driver.findElement(bt_Regresar).isEnabled();
			if (BotonBack == false) {
				return false;
			}
			boolean TituloCambiosFisicos = au.driver.findElement(tit_CambiosFisicos).isEnabled();
			if (TituloCambiosFisicos == false) {
				return false;
			}
			boolean BotonPreguntasFrecuentes = au.driver.findElement(bt_PregFrecuentes).isEnabled();
			if (BotonPreguntasFrecuentes == false) {
				return false;
			}
			boolean BotonTutoriales = au.driver.findElement(bt_Tutoriales).isEnabled();
			if (BotonTutoriales == false) {
				return false;
			}

			String OpcionCapturaCambiosFisicos = au.driver.findElement(lb_FuenteAbasto).getText();
			if (OpcionCapturaCambiosFisicos.equals("Fuente de Abasto:")) {
			} else {
				return false;
			}
			boolean TexboxFuentedeAbasto = au.driver.findElement(tb_CapturaFuenteAbasto).isEnabled();
			if (TexboxFuentedeAbasto == false) {
				return false;
			}
			boolean BotonBuscarFuentedeAbasto = au.driver.findElement(bt_Buscar).isEnabled();
			if (BotonBuscarFuentedeAbasto == false) {
				return false;
			}
			boolean TexBoxCodigodeBarras = au.driver.findElement(tb_CapturaSku).isDisplayed();
			if (TexBoxCodigodeBarras == false) {
				return false;
			}
			boolean BotonCapturaSKU = au.driver.findElement(bt_CapturaSku).isEnabled();
			if (BotonCapturaSKU == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			return false;
		}
	}

	/********************************************************************************************************
	 * Método - Ingresar valores no numericos al texbos Fuente de Abasto
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 13/09/2024
	 */

	public void ValoresNoNumericos() {
		testCase.addTextEvidenceCurrentStep("Resultado esperado no agrega valores");
		au.click(tb_CapturaFuenteAbasto);
		au.click(bt_Coma);
		testCase.addScreenShotCurrentStep(au, "testImage");
		au.click(bt_Punto);

	}

	/********************************************************************************************************
	 * Método - Validar si el campo fuente de abastos contiene informacion
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 13/09/2024
	 */
	public boolean validarnopermitaletrasenFA() {
		try {
			String LetrasFA = au.driver.findElement(tb_CapturaFuenteAbasto).getText();
			if (LetrasFA.isEmpty()) {
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/********************************************************************************************************
	 * Método - boton Captura Cambios fisicos
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 13/09/2024
	 */
	public void botonCapturaCambiosFisicos() {
		au.click(btn_ExcelenciaOperativa);
		au.click(bt_HerrCompra);
		au.click(btn_Cometa);
		au.scrollToElement(btn_DevolucionDeProductos);
		au.click(btn_CambiosFisicosProveedores);
		au.click(bt_CapCambiosFisicos);
		testCase.addScreenShotCurrentStep(au, "testImage");

	}

	/********************************************************************************************************
	 * Método - Click en el boton Back de Captura cambios fisicos
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 13/09/2024
	 */
	public void clickBackCapturacambiosfisicos() {
		testCase.addScreenShotCurrentStep(au, "testImage");
		au.click(bt_Regresar);

	}

	public boolean opcionCapturarFAValidaSiEvidencia() {
		try {
			System.out.println(FaEvo);
			au.sendKeys(tb_CapturaFuenteAbasto, FaEvo);
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			return false;
		}
	}

	/********************************************************************************************************
	 * Método - Click para eliminar articulo uno en Cambios Fisicos
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 20/09/2024
	 */
	public boolean clickEliminararticuloCF() {
		try {
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.click(bt_EliminarSku);
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/********************************************************************************************************
	 * Método - Validacion de Warning al precionar elimar articulo CF
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 20/09/2024
	 */
	public boolean warningEliminarArticulo() {

		try {

			String warningEliminar = au.driver.findElement(WarningEliminar).getText();
			String actualWarningEliminar = "¿Está seguro de eliminar este artículo?";
			if (warningEliminar.equals(actualWarningEliminar)) {
			} else {
				return false;
			}
			boolean BotonCancelarWarning = au.driver.findElement(btn_CancelarElimnarSku).isEnabled();
			if (BotonCancelarWarning == false) {
				return false;
			}
			boolean BotonAceptarWarning = au.driver.findElement(btn_AceptarEliminarSku).isEnabled();
			if (BotonAceptarWarning == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			return false;
		}
	}

	/********************************************************************************************************
	 * Método - Click para cancelar eliminar articulos en Cambios Fisicos
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 20/09/2024
	 */
	public boolean clickCancelarEliminararticuloCF() {
		testCase.addTextEvidenceCurrentStep(
				"Resultado esperado:Regresa a pantalla \"Cambios Físicos\" (no elimina el SKU)");
		try {
			au.click(btn_CancelarElimnarSku);
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
	 * Validacion Cancelar articulo
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 20/09/2024
	 */

	public boolean validacionDelTotaldeCapturarSku() {
		au.scrollToElement(lb_TotalArtConCambios);
		try {
			String totalDeArticulos = au.driver.findElement(lb_TotalArtConCambios).getText();
			if (totalDeArticulos.equals("3")) {
				testCase.addScreenShotCurrentStep(au, "testImage");

			} else {
				return false;

			}
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	/********************************************************************************************************
	 * Método - Click para eliminar articulo dos articulos en Cambios Fisicos
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 20/09/2024
	 */
//			String articuloTres = au.driver.findElement(lbl_Articulotres).getText();
//			String cambiodePosicionArticuloTres = articuloTres.substring(3);
	String vGlobal;

	public boolean clickEliminararticuloDosCF() {
		try {

			au.driver.findElement(AppiumBy.androidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"Fuente de Abasto:\"))"));
			testCase.addScreenShotCurrentStep(au, "testImage");
			swipeCoord(50, 500, 50, 100, 3000);
			String articuloTres = au.driver.findElement(xpathPadreArt3).getText();
			System.out.println(articuloTres);
			vGlobal = articuloTres;

			au.click(bt_EliminarSkuDOS);

			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/********************************************************************************************************
	 * Método - Eliminar y validar el cambio de posicion del articulo 3
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 20/09/2024
	 */
	public boolean validarPosicionArticuloTres() {

		try {
			// String CSku = au.driver.findElement(By.xpath(xpathPadre )+
			// articuloTres).getText();
			String cambiodePosicionArticuloTres = vGlobal.substring(3, vGlobal.length());
			System.out.println(cambiodePosicionArticuloTres);

			au.click(btn_AceptarEliminarSku);
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.presenceOfElementLocated(xpathPadreArt2));
			testCase.addScreenShotCurrentStep(au, "testImage");
			String articuloDos = au.driver.findElement(xpathPadreArt2).getText();
			if (articuloDos.equals("2- " + cambiodePosicionArticuloTres)) {
				return true; // Si todo se ejecuta sin problemas, retorna verdadero
			} else {
				return false;
			}
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/********************************************************************************************************
	 * Método - Presionar siguiente en la pantalla Cambios Físicos
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 20/09/2024
	 */
	public boolean clickSiguiente() {
		swipeCoord(50, 1215, 000, 000, 1003);
		try {
			au.click(btn_Siguiente);
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/*
	 * Metodo
	 */
	/********************************************************************************************************
	 * Método - Presionar SELECCIONA UNA OPCION y valida que existan las opciones
	 * SIN COMENTARIOS Y OTROS(ESPECIFICA)
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 20/09/2024
	 */
	public boolean clickSeleccionaUnaOpcion() {

		au.click(AEObservacionesProveedor);
		testCase.addScreenShotCurrentStep(au, "testImage");
		try {

			String OpcionSinComentarios = au.driver.findElement(cbo_SinComentarios).getText();
			if (OpcionSinComentarios.equals("SIN COMENTARIOS")) {
			} else {
				return false;
			}

			String OpcionOtros = au.driver.findElement(cbo_Otros).getText();
			if (OpcionOtros.equals("OTROS (ESPECIFICA)")) {
			} else {
				return false;
			}
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("El List Texbox Observaciones Del Proveedor no esta correcto");
			return false;
		}

	}

	/*
	 * Metodo
	 */
	/********************************************************************************************************
	 * Método - Valida que este correcto los Textos del Modulo Acuerdo de Proveedor
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 20/09/2024
	 */
	public boolean moduloFirmaDeAcuerdoProveedor() {

		try {

			String TituloSeleccionaUnaOpcion = au.driver.findElement(AEObservacionesProveedor).getText();
			if (TituloSeleccionaUnaOpcion.equals("SELECCIONA UNA OPCIÓN")) {
			} else {
				return false;
			}
			au.scrollToElement(tit_FirmaDeAcuerdo);
			String TituloFirmaDeAcuerdo = au.driver.findElement(tit_FirmaDeAcuerdo).getText();
			if (TituloFirmaDeAcuerdo.equals("FIRMA DE ACUERDO")) {
			} else {
				return false;
			}
			au.scrollToElement(lbl_ObservacionesDelProveedor);
			String ObservacionesDeProveedor = au.driver.findElement(lbl_ObservacionesDelProveedor).getText();
			if (ObservacionesDeProveedor.equals("Observaciones del Proveedor:")) {
			} else {
				return false;
			}
			au.scrollToElement(lbl_firmaProveedor);
			String FirmaProveedor = au.driver.findElement(lbl_firmaProveedor).getText();
			if (FirmaProveedor.equals("Firma Proveedor:")) {
			} else {
				return false;
			}
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * screenShotFirma - Click en continuar
	 * 
	 * @author Jessica Bautista
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 * 
	 * @date 20/09/2024
	 */
	public boolean clickContinuarFirma() {
		try {

			au.click(AEbtnContinuar);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleccionarObservacionesPersonalTienda - Click selecciona una opcion del
	 * personal de tienda
	 * 
	 * @author Jessica Bautista
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 * 
	 * @date 20/09/2024
	 */

	public boolean seleccionarObservacionesPersonalTienda() {
		swipeCoord(50, 1215, 000, 000, 1003);
		try {
			au.click(AEObservacionesTienda);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/********************************************************************************************************
	 * Método - valida que existan las opciones SIN COMENTARIOS Y OTROS(ESPECIFICA)
	 * Observaciones de Personal De Tienda
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 20/09/2024
	 */
	public boolean opcionesdeObservacionesPersonalTienda() {

		try {

			String OpcionSinComentarios = au.driver.findElement(cbo_SinComentarios).getText();
			if (OpcionSinComentarios.equals("SIN COMENTARIOS")) {
			} else {
				return false;
			}

			String OpcionOtros = au.driver.findElement(cbo_Otros).getText();
			if (OpcionOtros.equals("OTROS (ESPECIFICA)")) {
			} else {
				return false;
			}
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("El List Texbox Observaciones Del Proveedor no esta correcto");
			return false;
		}

	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * screenShotFirma - Click Seleccionar opción Sin comentarios
	 * 
	 * @author Jessica Bautista
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 * 
	 * @date 20/09/2024
	 */
	public boolean clickSinComentarios() {
		try {

			au.click(cbo_SinComentarios);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * Se valida que no contenga comentarios de personal de tienda
	 * 
	 * @author Jessica Bautista
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 * 
	 * @date 20/09/2024
	 */

	public boolean validaSinComentariosPersonalTienda() {

		try {
			String OpcionSinComentario = au.driver.findElement(SSinComentarios).getAttribute("text");
			if (OpcionSinComentario.equals("SIN COMENTARIOS")) {
				testCase.addScreenShotCurrentStep(au, "testImage");
			} else {
				return false;
			}
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			return false;
		}

	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * Ingresa a la opcion de firmas del personal de tienda pero no ingresa Firma
	 * 
	 * @author Jessica Bautista
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 * 
	 * @date 20/09/2024
	 */
	public void seleccionarFirmaPersonalTienda() {
		// swipeCoord(50, 1215, 000, 000, 1003);

		au.click(AEfirmaTienda);
		testCase.addScreenShotCurrentStep(au, "testImage");

	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * Click En Boton Back
	 * 
	 * @author Jessica Bautista
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 * 
	 * @date 20/09/2024
	 */
	public boolean clickBack() {
		try {

			au.click(bt_Regresar);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * Selecciona el radio button "Se cumplió con el proceso de cambios físicos"
	 * 
	 * @author Jessica Bautista
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 * 
	 * @date 20/09/2024
	 */
	public void seleccionaCumplimiento() {
		swipeCoord(50, 1215, 000, 000, 1003);

		au.click(AERdBtnCumpProcCambFis);
		testCase.addScreenShotCurrentStep(au, "testImage");

	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * Validar se muestre mensaje: Favor de agregar Observaciones area proveedor
	 * 
	 * @author Jessica Bautista
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 * 
	 * @date 20/09/2024
	 */

	public boolean validaMensajeFavorAgregarObservacioesProveedor() {
		au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		testCase.addTextEvidenceCurrentStep(
				"Validar se muestre mensaje: Favor de agregar  Observaciones area proveedor");
		testCase.addScreenShotCurrentStep(au, "testImage");

		try {

			String MensajeAgregaComentariosProveedor = au.driver.findElement(AEmsgAgObsProveedor).getText();
			if (MensajeAgregaComentariosProveedor.equals("Favor de agregar Observaciones del Proveedor")) {
			} else {
				return false;
			}

			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			return false;
		}

	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * Selecciona Aceptar en el mensaje Favor de agregar Observaciones area
	 * proveedor
	 * 
	 * @author Jessica Bautista
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 * 
	 * @date 20/09/2024
	 */
	public void seleccionaAceptarAgregarObservacionesProveedor() {

		au.click(AEbtnAceptar);
		testCase.addScreenShotCurrentStep(au, "testImage");

	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * Llena el campo faltante con un comentario en Observaciones de Proveedor y
	 * Valida que se agregó el Texto
	 * 
	 * @author Jessica Bautista
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 * 
	 * @date 20/09/2024
	 */
	public boolean LLenaComentarioDeProveedor() {

		au.click(AEObservacionesProveedor);
		testCase.addScreenShotCurrentStep(au, "testImage");
		au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		au.click(cbo_Otros);
		testCase.addScreenShotCurrentStep(au, "testImage");
		au.sendKeys(AEObservacionesProveedor, "12345ABCDabcd");
		au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		testCase.addScreenShotCurrentStep(au, "testImage");
		au.driver.hideKeyboard();

		try {

			String ComentarioProveedor = au.driver.findElement(AEObservacionesProveedor).getText();
			if (ComentarioProveedor.equals("12345ABCDabcd")) {
			} else {
				return false;
			}
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			return false;
		}

	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * Llena el campo faltante con un comentario en Observaciones de Proveedor y
	 * Valida que se agregó el Texto
	 * 
	 * @author Jessica Bautista
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 * 
	 * @date 20/09/2024
	 */
	public boolean AgregaComentarioProveedor(String comentario) {
		au.click(AEObservacionesProveedor);
		au.click(cbo_Otros);
		// au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		// testCase.addScreenShotCurrentStep(au, "testImage");
		au.sendKeys(AEObservacionesProveedor, comentario);
		au.driver.hideKeyboard();
		try {

			String ComentarioProveedor = au.driver.findElement(AEObservacionesProveedor).getText();
			if (ComentarioProveedor.equals(comentario)) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			return false;
		}

	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * Validar se muestre mensaje: Favor de agregar Firma del Personal de tienda
	 * 
	 * @author Jessica Bautista
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 * 
	 * @date 20/09/2024
	 */

	public boolean validaMensajeFavorAgregarFirmaPersonalTienda() {
		testCase.addTextEvidenceCurrentStep("Validar se muestre mensaje:Favor de agregar Firma del Personal de Tiend");
		testCase.addScreenShotCurrentStep(au, "testImage");
		try {

			String MensajeAgregaFirmaPersonalTienda = au.driver.findElement(AEmsgAgFirPersonalTienda).getText();
			if (MensajeAgregaFirmaPersonalTienda.equals("Favor de agregar Firma del Personal de Tienda")) {
			} else {
				return false;
			}

			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			return false;
		}

	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * ingresaFirmaTienda - inserta firma en campo, Firma de persoanl de tienda
	 * 
	 * @author Jessica Bautista
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 * 
	 * @date 20/09/2024
	 */
	public void ingresaFirmaPersonalTienda() {
		au.click(AEfirmaTienda);
		au.click(AEfirma);
		testCase.addScreenShotCurrentStep(au, "testImage");

	}

	/********************************************************************************************************
	 * Método - valida que existan le mensaje "La captura de cambios físicos se ha
	 * realizado de manera exitosa."
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 20/09/2024
	 */
	public boolean ValidaCapturaCambiosFisicosExitosa() {
		au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		testCase.addScreenShotCurrentStep(au, "testImage");
		try {

			String OpcionCapturaExitosa = au.driver.findElement(MsgCapturaExitosa).getText();
			if (OpcionCapturaExitosa.equals("La captura de cambios físicos se ha realizado de manera exitosa.")) {
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true; // Si todo se ejecuta sin problemas, retorna verdadero
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
	 * Capturar Manualmente Sku´s y validar su captura.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */
	public boolean capturarSkusSinEvidencia() {
		try {

			for (int i = 0; i < SkusEvo.size(); i++) {
				au.driver.findElement(tb_CapturaSku).sendKeys(SkusEvo.get(i));
				// validarCapturarSku();
				btnIngresoSku();
			}
			;
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {

			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 20/09/2024
	 */
	public boolean capturarSkusConEvidencia() {
		try {
			for (int i = 0; i < SkusEvo.size(); i++) {
				au.driver.findElement(tb_CapturaSku).sendKeys(SkusEvo.get(i));
				// validarCapturarSku();
				btnIngresoSku();
			}
			;
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * Valida que no esté seleccionada la Casilla [Se cumplió con el proceso de
	 * entrega]
	 * 
	 * @author Jessica Bautista
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 * 
	 * @date 20/09/2024
	 */
	public boolean opcionCasillaDeCumplimientoNoSeleccionada() {

		try {
			String Casillas = au.driver.findElement(AERdBtnCumpProcCambFis).getAttribute("checked");
			if (Casillas.equals("false")) {
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
	 */
	/********************************************************************************************************
	 * Método - Valida la existencia de la pantalla tira de entrega y sus encabezado
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 20/09/2024
	 */
	public boolean pantallaTiraDeEntregaComponentesEncabezado() {
		au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		testCase.addScreenShotCurrentStep(au, "testImage");
		try {

			String ImagenOxxo = au.driver.findElement(imgOxxo).getAttribute("enabled");
			if (ImagenOxxo.equals("true")) {
			} else {
				return false;
			}
			String NombreProveedor = au.driver.findElement(lbl_ProveedorTira).getText();
			if (NombreProveedor.equals("PROVEEDOR: " + nameProveedor)) {
			} else {
				return false;
			}
			String validaCrTienda = au.driver.findElement(lbl_CrTienda).getText();
			String vCrTienda = validaCrTienda.substring(0, 16);
			if (vCrTienda.equals("CR Tienda: " + crTienda)) {
			} else {
				return false;
			}
			String validaCrPlaza = au.driver.findElement(lbl_CrPlaza).getText();
			String vCrPlaza = validaCrPlaza.substring(0, 23);
			if (vCrPlaza.equals("Plaza: " + crPlaza + "    " + namePlaza)) {
			} else {
				return false;

			}
			String fechaADMVA = au.driver.findElement(lbl_FechaADMVA).getText();
			String vFechaADMVA = fechaADMVA.substring(0, 12);
			if (vFechaADMVA.equals("Fecha ADMVA:")) {
			} else {
				return false;

			}
			String validaHora = au.driver.findElement(lbl_Hora).getText();
			String vHora = validaHora.substring(0, 5);
			if (vHora.equals("Hora:")) {
			} else {
				return false;

			}
			String validaMsjCDirectas = au.driver.findElement(lbl_CDirectas).getText();
			if (validaMsjCDirectas.equals("Compras Directas !")) {
			} else {
				return false;
			}
			String validaRemision = au.driver.findElement(lbl_Remision).getText();
			String vRemision = validaRemision.substring(0, 15);
			if (vRemision.equals("Remisión: " + crTienda)) {
			} else {
				return false;
			}
			String ValidaTiempoCF = au.driver.findElement(lbl_TiempoCF).getText();
			String vTiempoCF = ValidaTiempoCF.substring(0, 21);
			if (vTiempoCF.equals("Tiempo de captura CF:")) {
			} else {
				return false;
			}
			String ValidaST = au.driver.findElement(lbl_ST).getText();
			if (ValidaST.equals("ST: <<<Sujeto a Revisión de Costos>>>")) {
				System.out.println("Se valido Modulo de Encabezado");
			} else {
				return false;

			}
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Guarda el nombre del Proveedor
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 25/09/2024
	 */
	public void nombreProveedor() {
		nameProveedor = au.driver.findElement(lbl_Proveedor).getText();
		System.out.println(nameProveedor);
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Guarda el nombre del Proveedor
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return N/A
	 */
	public void nombrePlaza() {
		au.click(mnu_lider);
		au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		namePlaza = au.driver.findElement(lbl_namePlaza).getText();
		System.out.println(namePlaza);
		au.click(btn_BackA);

	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Eliminar datos generados de cambios fisicos.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */
	public boolean borrarUltimaRemision(){
		try {
		obtenerHeaderRemision();
		Connection conn = DriverManager.getConnection(GlobalVariables.urlEvolucion, GlobalVariables.userEvolucion,
				PasswordUtil.decryptPassword(GlobalVariables.passEvolucion));
		String query1 = "DELETE FROM [XposCloudPurchaseToolQA].[dbo].[DI_PHYSICAL_CHANGES_DETAIL] WHERE DI_PHYSICAL_CHANGES_HEADER_ID = '"
				+ remisionEliminar + "'";
		String query2 = "DELETE FROM [XposCloudPurchaseToolQA].[dbo].[DI_PHYSICAL_CHANGES_HEADER] WHERE DI_PHYSICAL_CHANGES_HEADER_ID = '"
				+ remisionEliminar + "'";
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(query1);
		stmt.executeUpdate(query2);
		return true;
		}catch(Exception e){
			return false;
		}
	}

	public boolean obtenerHeaderRemision() {
		try {
			Connection conn = DriverManager.getConnection(GlobalVariables.urlEvolucion, GlobalVariables.userEvolucion,
					PasswordUtil.decryptPassword(GlobalVariables.passEvolucion));
			String query = "SELECT TOP 1 [DI_PHYSICAL_CHANGES_HEADER_ID]"
					+ "FROM [XposCloudPurchaseToolQA].[dbo].[DI_PHYSICAL_CHANGES_HEADER]WHERE STORE_PLACE_ID = '"
					+ storePlaceID + "'" + "Order by CREATED_DATETIME desc";
			SQLResult res = new SQLResult(conn, query);
			remisionEliminar = res.getFirstData().toString();
			return true;
		} catch (Exception e) {
			remisionEliminar = "";
			return false;
		}
	}

	/*
	 * Metodo
	 */
	/********************************************************************************************************
	 * Método - Valida la tabla de Resumen Realizados
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 25/09/2024
	 */
	public boolean pantallaTiraDeEntregaTablaRealizados() {
		au.scrollToElement(lbl_ResumenProgramados);
		testCase.addScreenShotCurrentStep(au, "testImage");
		try {

			String ValidaResumenRealizados = au.driver.findElement(lbl_ResumenRealizado).getText();
			if (ValidaResumenRealizados.equals("RESUMEN CAMBIOS REALIZADOS")) {
			} else {
				return false;
			}
			String ValidaCampoId = au.driver.findElement(tbl_IdRealizado).getText();
			if (ValidaCampoId.equals("ID")) {
				;
			} else {
				return false;
			}
			String ValidaCampoArt = au.driver.findElement(tbl_ARTRealizado).getText();
			if (ValidaCampoArt.equals("ART.")) {
			} else {
				return false;
			}
			String ValidaCampoDesc = au.driver.findElement(tbl_DescRealziado).getText();
			if (ValidaCampoDesc.equals("DESC.")) {
			} else {
				return false;
			}
			String ValidaCampoCant = au.driver.findElement(tbl_CantRealizado).getText();
			if (ValidaCampoCant.equals("CANT.")) {
				System.out.println("Se valido Resumen de Realizados");
			} else {
				return false;
			}
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 */
	/********************************************************************************************************
	 * Método - Valida la tabla de Resumen Realizados
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 25/09/2024
	 */
	public boolean pantallaTiraDeEntregaTablaProgramados() {
		au.scrollToElement(txa_Leyenda);
		testCase.addScreenShotCurrentStep(au, "testImage");
		try {

			String ValidaResumenProgramados = au.driver.findElement(lbl_ResumenProgramados).getText();
			if (ValidaResumenProgramados.equals("RESUMEN CAMBIOS PROGRAMADOS PENDIENTES")) {
			} else {
				return false;
			}
			String ValidaCampoId = au.driver.findElement(tbl_IdProgramado).getText();
			if (ValidaCampoId.equals("ID")) {
			} else {
				return false;
			}
			String ValidaCampoArt = au.driver.findElement(tbl_ArtProgramado).getText();
			if (ValidaCampoArt.equals("ART.")) {
			} else {
				return false;
			}
			String ValidaCampoDesc = au.driver.findElement(tbl_DescProgramado).getText();
			if (ValidaCampoDesc.equals("DESC.")) {
			} else {
				return false;

			}
			swipeCoord(662, 846, 46, 882, 3000);
			String ValidaCampoFecha = au.driver.findElement(tbl_FechaProgramado).getText();
			if (ValidaCampoFecha.equals("FECHA")) {
			} else {
				return false;
			}
			String ValidaCampoDias = au.driver.findElement(tbl_DiasProgramado).getText();
			if (ValidaCampoDias.equals("DÍAS")) {
			} else {
				return false;
			}
			String ValidaCampoCant = au.driver.findElement(tbl_CantProgramado).getText();
			if (ValidaCampoCant.equals("CANT.")) {
			} else {
				return false;
			}
			swipeCoord(662, 846, 46, 882, 3000);
			testCase.addScreenShotCurrentStep(au, "testImage");
			String ValidaCampoCosto = au.driver.findElement(tbl_CostoProgramado).getText();
			if (ValidaCampoCosto.equals("COSTO")) {
				System.out.println("Se valido Resumen de Programados");
			} else {
				return false;

			}
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			return false;
		}

	}

	/*
	 * Metodo
	 */
	/********************************************************************************************************
	 * Método - Valida la tabla de Resumen Realizados
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 25/09/2024
	 */
	public boolean pantallaTiraDeEntregaModuloSello() {
		au.scrollToElement(txa_Leyenda);
		testCase.addScreenShotCurrentStep(au, "testImage");
		try {

			String ValidaLeyendaAutorizado = au.driver.findElement(txa_Leyenda).getText();
			if (ValidaLeyendaAutorizado
					.equals("ESTE DOCUMENTO ES UN COMPROBANTE DE CAMBIOS FÍSICOS Y NO ES VÁLIDO COMO FACTURA.")) {
			} else {
				return false;
			}
			String ImagenAutorizado = au.driver.findElement(imgAutorizado).getAttribute("enabled");
			if (ImagenAutorizado.equals("true")) {
				System.out.println("Se valido Modulo sello");
			} else {
				return false;

			}
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 */
	/********************************************************************************************************
	 * Método - Valida Tira de entrega campo firmas
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 25/09/2024
	 */
	public boolean pantallaTiraDeEntregaFirmas() {
		swipeCoord(50, 500, 50, 50, 3000);
		testCase.addScreenShotCurrentStep(au, "testImage");
		try {

			String ValidaTituloFirmas = au.driver.findElement(lbl_FirmaAc).getText();
			if (ValidaTituloFirmas.equals("FIRMA DE ACUERDO")) {
			} else {
				return false;
			}
			swipeCoord(50, 500, 50, 50, 3000);
			String ValidaObservacionesP = au.driver.findElement(lbl_ObsProveedor).getText();
			if (ValidaObservacionesP.equals("Observaciones del Proveedor:")) {
			} else {
				return false;
			}
			au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
			String ValidaFirmaP = au.driver.findElement(lbl_FirmaProveedor).getText();
			if (ValidaFirmaP.equals("Firma Proveedor:")) {
			} else {
				return false;
			}
			String ValidaObservacionesT = au.driver.findElement(lbl_ObsTienda).getText();
			if (ValidaObservacionesT.equals("Observaciones Personal Tienda:")) {
			} else {
				return false;
			}
			swipeCoord(50, 500, 50, 100, 3000);
			testCase.addScreenShotCurrentStep(au, "testImage");
			String ValidaFirmaT = au.driver.findElement(lbl_FirmaTienda).getText();
			if (ValidaFirmaT.equals("Firma Personal Tienda:")) {
			} else {
				return false;
			}
			au.scrollToElement(btn_Imprimir);
			String ValidaCumplimientoDeCF = au.driver.findElement(rdo_Acuerdo).getText();
			if (ValidaCumplimientoDeCF.equals("Se cumplió con el proceso de cambios físicos")) {
			} else {
				return false;
			}
			String ValidaBotonFinalizar = au.driver.findElement(btn_Finalizar).getText();
			if (ValidaBotonFinalizar.equals("FINALIZAR")) {
			} else {
				return false;
			}
			swipeCoord(50, 500, 50, 100, 3000);
			String ValidaBotonImprimir = au.driver.findElement(btn_Imprimir).getText();
			if (ValidaBotonImprimir.equals("IMPRIMIR")) {
				System.out.println("Se valido Modulo Firmas");
			} else {
				return false;
			}
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 */
	/********************************************************************************************************
	 * Método - Posicionarse en Frima personal tienda
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 25/09/2024
	 */

	public boolean posicionarseFirmaPersonalTienda() {
		try {

			au.scrollToElement(btn_Siguiente);
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	public void ingresarModuloHerramientasSinEvidencia() {
		au.click(bt_ExcOperativa);
		au.click(bt_HerrCompra);
		au.click(bt_Cometa);
	}

	public boolean opcionCambiosFisicosSinEviencia() {
		try {
			au.click(bt_CambFisicos);
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	public boolean opcionCapturarCambiosFisicosSE() {
		try {
			au.click(bt_CapCambiosFisicos);
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Capturar un CF Programado.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */
	public boolean capturarCFProgramado() {
		try {
			au.sendKeys(By.id("com.femco.oxxo.operaciones.mitienda.debug:id/edt_cfisico_programado"), "2");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Selecciona el campo de Observaciones Proveedor sin generar evidencia.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */
	public boolean seleccionarCampoObservacionesProveedorSwipe() {
		try {
			au.scrollToElement(campoObservacionesProveedor);
			au.click(campoObservacionesProveedor);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	public boolean seleccionarCampoObservacionesProveedorSwipeSE() {
		try {
			au.scrollToElement(campoObservacionesProveedor);
			au.click(campoObservacionesProveedor);
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
	 * Selecciona la opcion Sin Comentarios.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */
	public boolean seleccionarOpcionSinComentarios() {
		try {
			au.click(campoSinComentarios);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	public boolean seleccionarOpcionSinComentariosSE() {
		try {
			au.click(campoSinComentarios);
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
	 * Selecciona el campo para Ingresar Firma de Proveedor e Ingresarla.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */
	public boolean seleccionarCampoFirmaProveedoreIngresarFirma() {
		try {
			au.scrollToElement(campoObservacionesPersonalTienda);
			screenShotFirma("antes");
			au.click(edt_FirmaProveedor);
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
			wait.until(ExpectedConditions.presenceOfElementLocated(edt_IngresarFirmaProveedor));
			au.click(edt_IngresarFirmaProveedor);
			au.click(bt_Continuar);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	public boolean seleccionarCampoFirmaProveedoreIngresarFirmaSE() {
		try {
			au.scrollToElement(campoObservacionesPersonalTienda);
			screenShotFirma("antes");
			au.click(edt_FirmaProveedor);
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
			wait.until(ExpectedConditions.presenceOfElementLocated(edt_IngresarFirmaProveedor));
			au.click(edt_IngresarFirmaProveedor);
			au.click(bt_Continuar);
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
	 * Selecciona el campo de Observaciones Personal Tienda.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */
	public boolean seleccionarCampoObservacionesPersonalTienda() {
		try {
			// au.scrollToElement(campoObservacionesPersonalTienda);//lo cambieeeee
			au.click(campoObservacionesPersonalTienda);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("El campo no se encuentra disponible");
			return false;
		}
	}

	public boolean seleccionarCampoObservacionesPersonalTiendaSE() {
		try {
			au.scrollToElement(campoObservacionesPersonalTienda);
			au.click(campoObservacionesPersonalTienda);

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
	 * Selecciona el campo para Ingresar Firma de Personal Tienda e Ingresarla.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */
	public boolean seleccionarCampoFirmaPersonalTiendaIngresarFirma() {
		try {
			screenShotFirma("antes");
			// au.scrollToElement(edt_FirmaPersonalTiendaCCF);//lo agregue no estaba
			au.click(edt_FirmaPersonalTiendaCCF);
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
			wait.until(ExpectedConditions.presenceOfElementLocated(edt_IngresarFirmaPersonalTienda));
			au.click(edt_IngresarFirmaPersonalTienda);
			au.click(bt_Continuar);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("El campo no se encuentra disponible");
			return false;
		}
	}

	public boolean seleccionarCampoFirmaPersonalTiendaIngresarFirmaSE() {
		try {
			screenShotFirma("antes");
			au.click(edt_FirmaPersonalTiendaCCF);
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
			wait.until(ExpectedConditions.presenceOfElementLocated(edt_IngresarFirmaPersonalTienda));
			au.click(edt_IngresarFirmaPersonalTienda);
			au.click(bt_Continuar);
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

	public boolean opcionSiGeneralSE() {
		try {
			au.click(bt_Si);
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
	 * Seleccionar Botón "FINALIZAR" de un cambio fisico.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */
	public boolean opcionFinalizarSE() {
		try {
			au.scrollToElement(bt_Finalizar);
			au.click(bt_Finalizar);
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
	 * Selecciona botón "SIGUIENTE".
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */
	public boolean seleccionarSiguiente() {
		try {
			au.scrollToElement(bt_Siguiente);
			au.click(bt_Siguiente);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	public boolean seleccionarSiguienteConsultaCF() {
		try {
			au.scrollToElement(bt_SiguienteCCF);
			au.click(bt_SiguienteCCF);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	public boolean seleccionarSiguienteSE() {
		try {
			au.scrollToElement(bt_Siguiente);
			au.click(bt_Siguiente);
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	public boolean seleccionarSiguienteSE2() {
		try {
			au.scrollToElement(By.xpath(
					"//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btn_siguiente\"]"));
			au.click(By.xpath(
					"//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btn_siguiente\"]"));
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
	 * PENDIENTEEEEEEEEEEE DEBE DE TOMAR LA FA A LA QUE SE LE REALIZO CAMBIOS
	 * FISICOS
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */
	public void capturarFAConCambiosFisicos() {
		au.sendKeys(tb_CapFAoNumRemision, FaEvo);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	public void capturarFuenteSinCambiosFisicos() {
		au.sendKeys(tb_CapFAoNumRemision, FaEvo);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Valida que permita ingresar Fuente de Abastos
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 04/10/2024
	 */
	public boolean validaIngresoFA() {
		try {
			String CapturaFATXT = au.driver.findElement(txt_FACapturada).getText();
			if (CapturaFATXT.equals(FaEvo)) {
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
	 * Seleccionar Radio Button Fuente de Abasto Proveedor
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 04/10/2024
	 */
	public boolean opcionRadioButtonFA() {
		try {
			au.click(bt_ConsultCambiosFisicos);
			au.click(rb_FuenteDeAbastosProveedor);
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
	 * Capturar un CF Programado.
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 04/10/2024
	 */
	public boolean capturarUnidadesProgramadas() {
		au.click(txb_CambioProgramado);
		au.sendKeys(txb_CambioProgramado, "1");
		try {
			String unidadesProgrmadas = au.driver.findElement(txb_CambioProgramado).getText();
			if (unidadesProgrmadas.equals("2")) {
			} else {
				return false;
			}
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Valida que el articulo mostrado coincida con el usado en el flujo de captura
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 04/10/2024
	 */
	public boolean validaSKUIngresado() {
		try {
			String SkuMostrado = au.driver.findElement(lbl_ArticuloProgramado).getText();
			if (SkuMostrado.equals(articuloCapturado1)) {
			} else {
				return false;
			}
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
	 * Guarda el articulo capturado para cambio fisico programado
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 04/10/2024
	 */
	public void articuloProgramado() {
		au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		articuloCapturado1 = au.driver.findElement(dtp_Articulo).getText();
		System.out.println(articuloCapturado1);
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Valida que el articulo mostrado coincida con el usado en el flujo de captura
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 04/10/2024
	 */
	public boolean validaRemisionMostrada() {
		try {
			String SkuMostrado = au.driver.findElement(lbl_RemisionMostrada).getText();
			if (SkuMostrado.equals(numRemisionCF)) {
			} else {
				return false;
			}
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida que el botón siguiente no se encuentre disponible
	 * 
	 * @author Jessica Bautista
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 * 
	 * @date 04/10/2024
	 */
	public boolean validaBotonSiguienteNoDisponible() {
		swipeCoord(360, 1114, 321, 196, 3000);
		try {
			String BotonSiguienteFalse = au.driver.findElement(btn_SiguienteConsulta).getAttribute("enabled");
			if (BotonSiguienteFalse.equals("false")) {
				testCase.addScreenShotCurrentStep(au, "testImage");
			} else {
				return false;
			}
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * Selecciona y valida las opciones del Combo Box
	 * 
	 * @author Jessica Bautista
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 * 
	 * @date 04/10/2024
	 */
	public boolean validaComboBox() {
		swipeCoord(384, 406, 377, 996, 3000);
		au.click(cbo_TipodeCambio);
		testCase.addScreenShotCurrentStep(au, "testImage");
		try {
			String etiquetaRealizado = au.driver.findElement(ddl_Realizado).getAttribute("text");
			if (etiquetaRealizado.equals("REALIZADO")) {
			} else {
				return false;
			}
			String etiquetaPendiente = au.driver.findElement(ddl_Pendiente).getAttribute("text");
			if (etiquetaPendiente.equals("PENDIENTE")) {
			} else {
				return false;
			}
			String etiquetaAplicarDevolucion = au.driver.findElement(ddl_AplicarDevolucion).getAttribute("text");
			if (etiquetaAplicarDevolucion.equals("APLICAR DEVOLUCIÓN")) {
			} else {
				return false;
			}
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * Selecciona La opción Realizado del Combo Box y Valida el campo
	 * "Cambios realizados"
	 * 
	 * @author Jessica Bautista
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 * 
	 * @date 04/10/2024
	 */
	public boolean SeleccionaRealizado() {
		au.click(ddl_Realizado);
		testCase.addScreenShotCurrentStep(au, "testImage");
		try {
			String etiquetaCambiosRealizados = au.driver.findElement(lbl_CambiosRealziados).getAttribute("text");
			if (etiquetaCambiosRealizados.equals("Cambios realizados:")) {
			} else {
				return false;
			}
			boolean campoUnidades = au.driver.findElement(txb_unidadesRealziadas).isEnabled();
			if (campoUnidades == false) {
				return false;
			}
			boolean botonMas = au.driver.findElement(btn_Sumar).isEnabled();
			if (botonMas == false) {
				return false;
			}
			boolean botonMenos = au.driver.findElement(btn_Restar).isEnabled();
			if (botonMenos == false) {
				return false;
			}
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {;
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Suma Artículos a Cambios Realizados y Valida que se registre en el texbox
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 04/10/2024
	 */
	public boolean SumaArticulosVerificaRegistroEnTextBox() {
		au.click(btn_Sumar);
		testCase.addScreenShotCurrentStep(au, "testImage");
		try {
			String unidadesParaModificar = au.driver.findElement(txb_unidadesRealziadas).getText();
			if (unidadesParaModificar.equals("1")) {
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
	 * valida que el botón siguiente si se encuentre disponible
	 * 
	 * @author Jessica Bautista
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 * 
	 * @date 04/10/2024
	 */
	public boolean validaBotonSiguienteDisponible() {
		swipeCoord(360, 1114, 321, 196, 3000);
		try {
			String BotonSiguienteFalse = au.driver.findElement(btn_SiguienteConsulta).getAttribute("enabled");
			if (BotonSiguienteFalse.equals("true")) {
				testCase.addScreenShotCurrentStep(au, "testImage");
			} else {
				return false;
			}
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * Selecciona la opción pendiente y valida que la cantidad programada regrese al
	 * texbox cantidad
	 * 
	 * @author Jessica Bautista
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 * 
	 * @date 04/10/2024
	 */
	public boolean SeleccionaPendiente() {
		au.click(ddl_Pendiente);
		testCase.addScreenShotCurrentStep(au, "testImage");
		try {
			boolean campoRealizados;
			try {
				campoRealizados = au.driver.findElement(lbl_CambiosRealziados).isDisplayed();
			} catch (Exception e) {
				campoRealizados = false;
			}
			if (campoRealizados == true) {
				return false;
			}

			String campoCantidad = au.driver.findElement(txb_CantidadesPendientes).getText();
			if (campoCantidad.equals(cantidadProgramada)) {

			} else {
				return false;
			}
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * obtener numero de unidades programadas articulo 1 - Obtiene el numero de
	 * remision de la pantalla tira de entrega
	 * 
	 * @author Jessica Bautista
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 * 
	 * @date 04/10/2024
	 */
	public void obtenerUnidadesProgramadasArtUnoAcuerdoDeEntrega() {
		swipeCoord(653, 643, 129, 615, 3000);
		cantidadProgramada = au.driver.findElement(rb_UnidadesProgramadas).getText();
		System.out.println("Cantidades programadas " + cantidadProgramada);
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * Selecciona La opción Aplicar Devolucion del Combo Box y Valida el campo
	 * "Piezas a devolver:"
	 * 
	 * @author Jessica Bautista
	 * 
	 * @param N/A
	 * 
	 * @return True or False
	 * 
	 * @date 04/10/2024
	 */
	public boolean SeleccionaAplicarDevolucion() {
		au.click(ddl_AplicarDevolucion);
		testCase.addScreenShotCurrentStep(au, "testImage");
		try {
			String etiquetaPiezasDevolver = au.driver.findElement(lbl_ArticulosDev).getAttribute("text");
			if (etiquetaPiezasDevolver.equals("Piezas a devolver:")) {
			} else {
				return false;
			}
			boolean campoUnidades = au.driver.findElement(txb_unidadesRealziadas).isEnabled();
			if (campoUnidades == false) {
				return false;
			}
			boolean botonMas = au.driver.findElement(btn_Sumar).isEnabled();
			if (botonMas == false) {
				return false;
			}
			boolean botonMenos = au.driver.findElement(btn_Restar).isEnabled();
			if (botonMenos == false) {
				return false;
			}
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Presiona el botón SIGUIENTE y valida el mensaje “Se han enviado artículos al
	 * apartado de predevoluciones. Para concretar la devolución, favor ir al
	 * apartado de predevoluciones.”
	 * 
	 * @author Jessica Bautista
	 * @param N/A
	 * @return True or False
	 * @date 04/10/2024
	 */
	public boolean PresionaSiguienteConDevoluciones() {
		au.click(btn_SiguienteConsulta);
		testCase.addScreenShotCurrentStep(au, "testImage");
		try {
			String mensajeArticulosDevoluciones = au.driver.findElement(msg_EnvioADev).getText();
			if (mensajeArticulosDevoluciones.equals(
					"Se han enviado artículos al apartado de predevoluciones. Para concretar la devolución, favor de ir al apartado de predevoluciones.")) {
				testCase.addScreenShotCurrentStep(au, "testImage");
			} else {
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean opcionBuscarSE() {
		try {
			au.click(bt_Buscar);
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}
	
	public void ingresarModuloHerramientasOff() {

		testCase.addTextEvidenceCurrentStep("Tap en Botón Excelencia Operativa");
		au.click(bt_ExcOperativa);
		testCase.addScreenShotCurrentStep(au, "testImage");

		testCase.addTextEvidenceCurrentStep("Tap en Botón Herramienta de Compra");
		au.click(bt_HerrCompra2);
		testCase.addScreenShotCurrentStep(au, "testImage");

		testCase.addTextEvidenceCurrentStep("Tap en Botón Cometa");
		au.click(bt_Cometa);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validar Ausencia de Mensaje de cambios fisicos pendientes.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean validarMensajePendientesOff() {
		try {

			boolean Pendientes;
			try {
				Pendientes = au.driver.findElement(By.id("android:id/message")).isDisplayed();
			} catch (Exception e) {
				Pendientes = true;
			}
			if (Pendientes == false) {
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
	 * Validar Pantalla de lectura de QR.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean pantalla_Qr() {
		try {

			boolean TituloQR;
			try {
				TituloQR = au.driver.findElement(By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo"))
						.isDisplayed();
			} catch (Exception e) {
				TituloQR = true;
			}
			if (TituloQR == false) {
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
	 * Validar Pantalla Principal de Verificacion de Mercancia.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean validarPantallaVerificacionMercancia() {
		try {

			boolean ttl = au.driver.findElement(ttl_VerificacionM).isDisplayed();
			if (ttl == false) {
				return false;
			}

			boolean bt1 = au.driver.findElement(bt_PedPrecargados).isDisplayed();
			if (bt1 == false) {
				return false;
			}

			boolean bt2 = au.driver.findElement(bt_LeerQrPreventaoPedido).isDisplayed();
			if (bt2 == false) {
				return false;
			}

			boolean bt3 = au.driver.findElement(bt_GenHC).isDisplayed();
			if (bt3 == false) {
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
	 * Validar Pantalla Principal de Herramientas.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean validarPantallaHerramientasCFyDevOff() {
		try {

			String rpcedis = au.driver.findElement(lblRPCedis).getText();
			if (rpcedis.contains("Recepción de Pedidos") != true) {
				return false;
			}

			String vmcedis = au.driver.findElement(lblVMCedis).getText();
			if (vmcedis.contains("Verificación de Mercancía") != true) {
				return false;
			}
			String tirasE = au.driver.findElement(lbltirasEntrega).getText();
			if (tirasE.contains("Consultar Tiras de") != true) {
				return false;
			}
			String ppDirecto = au.driver.findElement(bt_VerMercancia).getText();
			if (ppDirecto.contains("Pedidos Proveedor Directo") != true) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			swipeCoord(694, 680, 57, 680, 3000);

			boolean CF;
			try {
				CF = au.driver.findElement(By.xpath("//android.widget.TextView[@text=\"Cambios físicos de productos\n"
						+ "(Proveedores Directos)\"]")).isDisplayed();
			} catch (Exception e) {
				CF = true;
			}
			if (CF == false) {
				return false;
			}

			boolean DEV;
			try {
				DEV = au.driver.findElement(By.xpath(
						"//android.widget.TextView[@text=\"Devoluciones de producto\n" + "(Proveedores Directos)\"]"))
						.isDisplayed();
			} catch (Exception e) {
				DEV = true;
			}
			if (DEV == false) {
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
	 * opcionDevolucionesDeProductoProvDir - Seleccionar opcion Devoluciones de
	 * Producto (Proveedores Directos) ->Excelencia Operativa/Herramienta de
	 * compra/Cometa/Devoluciones de producto (Proveedores Directos
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void opcionDevolucionesDeProductoProvDir() {
		au.click(bt_ExcOperativa);
		au.click(bt_HerrCompra);
		au.click(bt_Cometa);
		au.scrollToElement(bt_DevoProductos);
		au.click(bt_DevoProductos);
		testCase.addTextEvidenceCurrentStep("Se selecciona la opcion Devoluciones de Producto(Proveedores Directos)");
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validar Pantalla Generación de Herramienta de Compra.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean PantallaGeneracionDeHerramientaCompra() {
		try {
			String pantallaGHC = au.driver.findElement(TxtProveedor).getText();
			if (pantallaGHC.contains("PROVEEDOR") != true) {
				return false;
			}
			boolean GenPedyPrev = au.driver.findElement(rbGenPedyPrev).isEnabled();
			if (GenPedyPrev != true) {
				return false;
			}
			boolean GenPedyEntrega = au.driver.findElement(rbGenPedyEntrega).isEnabled();
			if (GenPedyEntrega != true) {
				return false;
			}
			String camNumProv = au.driver.findElement(edtNumeroProveedor).getText();
			if (camNumProv.isEmpty()) {
			} else {
				return false;
			}
			boolean GenerarCodigosQR = au.driver.findElement(btnGenerarCodigosQR).isEnabled();
			if (GenerarCodigosQR != true) {
				return false;
			}
			boolean btVolver = au.driver.findElement(btnVolver).isEnabled();
			if (btVolver != true) {
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
	 * Seleccionar botón LeerQrPreventaoPedido
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */
	public void selec_LeerQrPreventaoPedido() {
		au.click(bt_LeerQrPreventaoPedido);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar botón Verificación de Mercancia Proveedor Directo
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */
	public void selec_VerificacionMercanciaSE() {
		au.click(bt_VerMercancia);
	}

	public void selec_VerificacionMercancia() {
		au.click(bt_VerMercancia);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar botón Generación de Herramienta de Compra
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public void selec_GenHerramientaCompraSE() {
		au.click(bt_GenHC);
	}

	public void selec_GenHerramientaCompra() {
		au.click(bt_GenHC);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar botón "NO" de cualquier mensaje mostrado.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */
	public void selec_NoGeneralSE() {
		au.click(bt_No);
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Realizar la generación de un código QR de Preventa HC sin evidencia.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */
	public void generacionQR_HC_Preventa_SE(){
		au.click(rb_GenPedidoPreventa);
		au.sendKeys(edt_NumProveedor, FaEvo);// FaEvo
		au.click(bt_GenCodigosQR);
		au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(4000));
		boolean multipleQr;
		try {
			multipleQr = au.driver.findElement(By.xpath(
					"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/subtitle\" and @text[contains(., '1 DE')] ]"))
					.isDisplayed();
		} catch (Exception e) {
			multipleQr = false;
		}
		if (multipleQr == true) {
			System.out.println("si lo encontre");
			String total = au.driver.findElement(By.xpath(
					"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/subtitle\" and @text[contains(., '1 DE')] ]"))
					.getText();

			TotalQR = total.substring(total.length() - 1);
			System.out.println(TotalQR);
			System.out.println("-----------------------");
			for (int i = 0; i < Integer.parseInt(TotalQR); i++) {
				au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
				codigosQr.add(au.driver.findElement(QRGenHC).getText());
				codigosQr.get(i);
				au.click(By.xpath(
						"//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btn_siguiente\"]"));
			}
		} else {
			TotalQR = "1";
			codigosQr.add(au.driver.findElement(QRGenHC).getText());
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Realizar la generación de un código QR de HC Repartidor sin evidencia.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */
	public void generacionQR_HC_Repartidor_SE(){
		au.click(rb_GenPedidoRepartidor);
		au.sendKeys(edt_NumProveedor, FaEvo);// FaEvo
		au.click(bt_GenCodigosQR);
		au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(4000));
		boolean multipleQr;
		try {
			multipleQr = au.driver.findElement(By.xpath(
					"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/subtitle\" and @text[contains(., '1 DE')] ]"))
					.isDisplayed();
		} catch (Exception e) {
			multipleQr = false;
		}

		if (multipleQr == true) {
			System.out.println("si lo encontre");
			String total = au.driver.findElement(By.xpath(
					"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/subtitle\" and @text[contains(., '1 DE')] ]"))
					.getText();

			TotalQR = total.substring(total.length() - 1);
			System.out.println(TotalQR);
			System.out.println("-----------------------");
			for (int i = 0; i < Integer.parseInt(TotalQR); i++) {
				au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
				codigosQr.add(au.driver.findElement(QRGenHC).getText());
				codigosQr.get(i);
				au.click(By.xpath(
						"//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btn_siguiente\"]"));
			}
		} else {
			TotalQR = "1";
			codigosQr.add(au.driver.findElement(QRGenHC).getText());
		}
	}

	public void generacionQR_HC_Repartidor_SEVenta(){
		au.click(rb_GenPedidoRepartidor);
		au.sendKeys(edt_NumProveedor, FaEvo);// FaEvo
		au.click(bt_GenCodigosQR);
		au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(4000));
		codigosQr.clear();
		boolean multipleQr;
		try {
			multipleQr = au.driver.findElement(By.xpath(
					"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/subtitle\" and @text[contains(., '1 DE')] ]"))
					.isDisplayed();
		} catch (Exception e) {
			multipleQr = false;
		}

		if (multipleQr == true) {
			System.out.println("si lo encontre");
			String total = au.driver.findElement(By.xpath(
					"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/subtitle\" and @text[contains(., '1 DE')] ]"))
					.getText();

			TotalQR = total.substring(total.length() - 1);
			System.out.println(TotalQR);
			System.out.println("-----------------------");
			for (int i = 0; i < Integer.parseInt(TotalQR); i++) {
				au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
				codigosQr.add(au.driver.findElement(QRGenHC).getText());
				codigosQr.get(i);
				au.click(By.xpath(
						"//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btn_siguiente\"]"));
			}
		} else {
			TotalQR = "1";
			codigosQr.add(au.driver.findElement(QRGenHC).getText());
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Realizar proceso hasta llegar a pantalla Acuerdo de Entrega con FA Válida e
	 * ingresar Skus Relacionados sin generar evidencia.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */
	public void ingresarPantallaAcuerdoEntregaFAValidaSkusRelacionados(int cantidadSKus, int inventario) {
		capturarSkusSinEvidencia();
		au.scrollToElement(bt_Siguiente);
		au.click(bt_Siguiente);
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Posicionarse en el campo Observaciones de Proveedor de Pantalla "Acuerdo de
	 * Entrega".
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */
	public boolean posicionarseObservacionesDeProveedor() {
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
			wait.until(ExpectedConditions.presenceOfElementLocated(campoObservacionesProveedor));
			au.scrollToElement(campoObservacionesProveedor);
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
	 * Posicionarse en el campo Observaciones de Personal Tienda de Pantalla
	 * "Acuerdo de Entrega".
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */
	public boolean posicionarseObservacionesDePersonalTienda() {
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
			wait.until(ExpectedConditions.presenceOfElementLocated(campoObservacionesPersonalTienda));
			au.scrollToElement(campoObservacionesPersonalTienda);
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
	 * Validar texto predeterminado de campo Observaciones de Proveedor de Pantalla
	 * "Acuerdo de Entrega".
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean validarTextoPredeterminadoObservacionesDeProveedor() {
		try {
			String mensajeMostrado = au.driver.findElement(campoObservacionesProveedor).getText();
			String txt = "SELECCIONA UNA OPCIÓN";

			if (mensajeMostrado.equals(txt)) {
			} else {
				testCase.addTextEvidenceCurrentStep("Se muestra mensaje predeterminado de manera incorrecta.");
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("Se muestra mensaje predeterminado de manera incorrecta.");
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validar texto predeterminado de campo Observaciones de Personal Tienda de
	 * Pantalla "Acuerdo de Entrega".
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */
	public boolean validarTextoPredeterminadoObservacionesPersonalTienda() {
		try {
			String mensajeMostrado = au.driver.findElement(campoObservacionesPersonalTienda).getText();
			String txt = "SELECCIONA UNA OPCIÓN";
			if (mensajeMostrado.equals(txt)) {
			} else {
				testCase.addTextEvidenceCurrentStep("Se muestra mensaje predeterminado de manera incorrecta.");
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("Se muestra mensaje predeterminado de manera incorrecta.");
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validar opciones predeterminadas de campo Observaciones de Proveedor de
	 * Pantalla "Acuerdo de Entrega".
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */
	public boolean validarOpcionesObservaciones() {
		try {

			boolean sinComentarios = au.driver.findElement(campoSinComentarios).isDisplayed();
			if (sinComentarios == false) {
				testCase.addTextEvidenceCurrentStep("El botón para Sin Comentarios no esta habilitado");
			}

			boolean otros = au.driver.findElement(campoOtros).isDisplayed();
			if (otros == false) {
				testCase.addTextEvidenceCurrentStep("El botón para Otros no esta habilitado");
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("No se muestran las opciones de manera correcta.");
			return false;
		}
	}

	public boolean seleccionarOpcionSinComentariosSEO() {
		try {
			au.click(campoSinComentarios);
			au.scrollToElement(By.xpath(
					"//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btn_siguiente\"]"));
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;

		}
	}

	public boolean validarOpcionSinComentariosProveedor() {
		try {
			String mensajeMostrado = au.driver.findElement(campoObservacionesProveedor).getText();
			String txt = "SIN COMENTARIOS";
			if (mensajeMostrado.equals(txt)) {
				testCase.addScreenShotCurrentStep(au, "testImage");
			} else {
			}
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	public boolean validarOpcionSinComentariosPersonal() {
		try {

			String mensajeMostrado = au.driver.findElement(campoObservacionesPersonalTienda).getText();
			String txt = "SIN COMENTARIOS";

			if (mensajeMostrado.equals(txt)) {
				testCase.addScreenShotCurrentStep(au, "testImage");
			} else {
				return false;
			}

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
	 * Selecciona la opcion OTROS.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean seleccionarOpcionOtros() {
		try {
			au.click(campoOtros);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	public boolean seleccionarOpcionOtrosSE() {
		try {
			au.click(campoOtros);
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
	 * Selecciona botón "CONTINUAR".
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */
	public boolean seleccionarContinuar() {
		try {
			au.click(bt_Continuar);
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
	 * Seleccionar Casilla [Se cumplió con el proceso de cambios fisicos].
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */
	public boolean seleccionarCasillaSeCumplioConCambiosFisicos() {
		try {
			au.scrollToElement(ckb_SeCumplioProcesoEntrega);
			au.click(ckb_SeCumplioProcesoEntrega);
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
	 * Selecciona el campo de Observaciones Proveedor.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean seleccionarCampoObservacionesProveedor() {
		try {
			au.click(campoObservacionesProveedor);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("El campo no se encuentra disponible");
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Ingresar texto en el campo de Observaciones de Proveedor.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean ingresarTextoObservacionesProveedor() {
		try {

			au.sendKeys(campoObservacionesProveedor, "Identificación de texto para casos automatizados ñ");
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("El campo no se encuentra disponible");
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validar que se muestre pantalla Acuerdo de Entrega.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean validarExistenciaAcuerdoEntrega() {
		try {

			boolean Regresar = au.driver.findElement(bt_Regresar).isEnabled();
			if (Regresar == false) {
				testCase.addTextEvidenceCurrentStep("El botón para Regresar no esta habilitado");
			}

			boolean TitAcuerdoEntrega = au.driver.findElement(lb_AcuerdoDeEntrega).isEnabled();
			if (TitAcuerdoEntrega == false) {
				testCase.addTextEvidenceCurrentStep("El Titulo de Cambios Fisicos no se encuentra visible.");
			}

			boolean PregFrecuentes = au.driver.findElement(bt_PregFrecuentes).isEnabled();
			if (PregFrecuentes == false) {
				testCase.addTextEvidenceCurrentStep("El botón de Preguntas Frecuenes no esta habilitado");
			}

			boolean Tutoriales = au.driver.findElement(bt_Tutoriales).isEnabled();
			if (Tutoriales == false) {
				testCase.addTextEvidenceCurrentStep("El botón de Tutotiales no esta habilitado");
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
	 * Visualizar que no se realizo el ingreso de ninguna firma o comentario.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean NoIngresoDeFirmasyComentarios() {
		try {

			posicionarseObservacionesDeProveedor();
			validarTextoPredeterminadoObservacionesDeProveedor();
			screenShotFirma("antes");
			screenShotFirma("despues");
			validaCampoFirmaVacio("antes", "despues");

			posicionarseObservacionesDePersonalTienda();
			validarTextoPredeterminadoObservacionesPersonalTienda();
			screenShotFirma("antes");
			screenShotFirma("despues");
			validaCampoFirmaVacio("antes", "despues");

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
	 * Ingresar texto en el campo de Observaciones de Personal Tienda.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean ingresarTextoObservacionesPersonalTienda() {
		try {

			au.sendKeys(campoObservacionesPersonalTienda, "Identificación de texto para casos automatizados ñ");
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("El campo no se encuentra disponible");
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validar el ingreso de texto en el campo de Observaciones de Proveedor.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean validarIngresoTextoObservacionesProveedor() {
		try {

			String mensajeMostrado = au.driver.findElement(campoObservacionesProveedor).getText();
			String txt = "Identificación de texto para casos automatizados ñ";

			if (mensajeMostrado.equals(txt)) {
				testCase.addScreenShotCurrentStep(au, "testImage");
			} else {
				return false;
			}

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
	 * Validar el ingreso de texto en el campo de Observaciones Personal Tienda.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean validarIngresoTextoObservacionesPersonalTienda() {
		try {

			String mensajeMostrado = au.driver.findElement(campoObservacionesPersonalTienda).getText();
			String txt = "Identificación de texto para casos automatizados ñ";

			if (mensajeMostrado.equals(txt)) {
				testCase.addScreenShotCurrentStep(au, "testImage");
			} else {
				return false;
			}

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
	 * Seleccionar Botón Buscar y mitiga error de Binarios.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean opcionBuscarConError() {
		try {
			au.click(bt_Buscar);

			// -----Error Servicio no disponible (404)------
			au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
			if (au.driver.findElement(By.id("android:id/button1")).isDisplayed()) {
				au.click(By.id("android:id/button1"));
				au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
			}
			// ----------------------------------------------

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
	 * Validación de Pantalla Tira de Entrega con Opción Otros en ambas y firmas
	 * ingresadas.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean pantallaTiraDeEntregaOpciónOtrosConFirmas() {

		try {

			// Tiempo de espera para que cargue la pantalla.

			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
			wait.until(ExpectedConditions.presenceOfElementLocated(lb_TiraDeEntrega));

			// Válida los Headers de la pantalla de Tira de Entrega

			boolean TitTiraDeEntrega = au.driver.findElement(lb_TiraDeEntrega).isEnabled();
			if (TitTiraDeEntrega == false) {
				return false;
			}

			boolean PregFrecuentes = au.driver.findElement(bt_PregFrecuentes).isEnabled();
			if (PregFrecuentes == false) {
				return false;
			}

			boolean Tutoriales = au.driver.findElement(bt_Tutoriales).isEnabled();
			if (Tutoriales == false) {
				return false;
			}

			// Desplazarse hacia la ubicación de la primera Tabla.

			au.scrollToElement(By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo_cf_tira_programados"));
			wait.until(ExpectedConditions.presenceOfElementLocated(lb_ResumenCambiosRealizados));

			// Válida la existencia de la tabla de Resumen Cambios Realizados y que esta
			// contenga Sku´s.

			boolean nombreTabla = au.driver.findElement(lb_ResumenCambiosRealizados).isDisplayed();
			if (nombreTabla == true) {
				testCase.addTextEvidenceCurrentStep("Se valida existencia de tabla: Resumen Cambios Realizados");
				testCase.addScreenShotCurrentStep(au, "testImage");
			} else {
				return false;
			}

			boolean id = au.driver.findElement(hd_idCambRealiz).isDisplayed();
			boolean art = au.driver.findElement(hd_artCambRealiz).isDisplayed();
			boolean desc = au.driver.findElement(hd_descCambRealiz).isDisplayed();
			boolean cant = au.driver.findElement(hd_cantCambRealiz).isDisplayed();
			if (id != true || art != true || desc != true || cant != true) {
				return false;
			} else {
				for (int i = 1; i <= 2; i++) {
					String padre = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/rv_tira_entrega_cf_realizados\"]/android.widget.LinearLayout["
							+ i + "]";
					String sku = au.driver.findElement(By.xpath(padre
							+ "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_articulo_cf_realizado\"]"))
							.getText();
					if (!SkusEvo.contains(sku)) {
						return false;
					}
				}
				testCase.addTextEvidenceCurrentStep("Se valida existencia de articulos.");
				testCase.addScreenShotCurrentStep(au, "testImage");

			}

			// Desplazarse hacia la ubicación de la segunda Tabla de Cambios Fisicos
			// Programados.

			au.scrollToElement(By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvw_leyenda"));

			// Se válida que la segunda tabla este vacia ya que no se programaron cambios
			// fisicos.

			boolean skus;
			try {
				skus = au.driver.findElement(By.xpath(
						"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_articulo_cf_programado\"]"))
						.isDisplayed();
			} catch (Exception e) {
				skus = false;
			}

			if (skus == true) {
				return false;
			}

			testCase.addTextEvidenceCurrentStep(
					"Se valida ausencia de tabla y articulos: Resumen Cambios Programados Pendientes");
			testCase.addScreenShotCurrentStep(au, "testImage");

			boolean SelloAutorizado = au.driver.findElement(lb_SelloAutorizado).isEnabled();
			if (SelloAutorizado == false) {
				testCase.addTextEvidenceCurrentStep("El Sello no se encuentra visible.");
			}

			testCase.addScreenShotCurrentStep(au, "testImage");

			// Desplazarse hacia el area de Observaciones y Firmas para realizar su
			// validación.

			au.scrollToElement(By.xpath(
					"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo\" and @text=\"Firma Proveedor:\"]"));

			String ObservacionesProvedor = au.driver.findElement(cp_ObservacionesProveedorTiraEntrega).getText();
			String txtProveedor = "Identificación de texto para casos automatizados ñ";

			if (ObservacionesProvedor.equals(txtProveedor)) {
			} else {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");

			au.scrollToElement(By.xpath(
					"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo\" and @text=\"Firma Personal Tienda:\"]"));

			String ObservacionesPersonalTienda = au.driver.findElement(cp_ObservacionesPersonalTiendaTiraEntrega)
					.getText();
			String txtPersonal = "Identificación de texto para casos automatizados ñ";

			if (ObservacionesPersonalTienda.equals(txtPersonal)) {
			} else {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");

			// Desplazarse hacia ultimo apartado de la pantalla Tira de Entrega y validar
			// sus ultimos botones.

			au.scrollToElement(bt_Imprimir);

			boolean ProcesoDeEntregaTiraDeEntrega = au.driver.findElement(ck_ProcesoDeEntregaTiraDeEntrega)
					.isDisplayed();
			if (ProcesoDeEntregaTiraDeEntrega == false) {
				return false;
			}

			boolean Finalizar = au.driver.findElement(bt_Finalizar).isEnabled();
			if (Finalizar == false) {
				return false;
			}

			boolean Imprimir = au.driver.findElement(bt_Imprimir).isEnabled();
			if (Imprimir == false) {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");

			return true;

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/******************************************************************************************/
	/*
	 * Método - pantallaTiraDeEntregaConsultaCF - Realiza la validación de la
	 * pantalla Tira de Entrega
	 * 
	 * @return True/False
	 * 
	 * @author Alejandro Valdez
	 */
	public boolean pantallaTiraDeEntregaConsultaCF() {
		try {
			/*
			 * Tiempo de espera para que cargue la pantalla.
			 */

			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
			wait.until(ExpectedConditions.presenceOfElementLocated(lb_TiraDeEntrega));

			/*
			 * Válida los Headers de la pantalla de Tira de Entrega
			 */
			boolean TitTiraDeEntrega = au.driver.findElement(lb_TiraDeEntrega).isEnabled();
			if (TitTiraDeEntrega == false) {
				return false;
			}

			boolean PregFrecuentes = au.driver.findElement(bt_PregFrecuentes).isEnabled();
			if (PregFrecuentes == false) {
				return false;
			}

			boolean Tutoriales = au.driver.findElement(bt_Tutoriales).isEnabled();
			if (Tutoriales == false) {
				return false;
			}

			/*
			 * Validar encabezados de Datos.
			 */

			boolean proveedor = au.driver.findElement(lb_Prove).isEnabled();
			if (proveedor == false) {
				return false;
			}

			boolean crTienda = au.driver.findElement(lb_CrTienda).isEnabled();
			if (crTienda == false) {
				return false;
			}

			boolean Plaza = au.driver.findElement(lb_Plaza).isEnabled();
			if (Plaza == false) {
				return false;
			}

			boolean Fecha = au.driver.findElement(lb_Fecha).isEnabled();
			if (Fecha == false) {
				return false;
			}

			boolean Tiempo = au.driver.findElement(lb_TiempoCF).isEnabled();
			if (Tiempo == false) {
				return false;
			}

			boolean Hora = au.driver.findElement(lb_Hora).isEnabled();
			if (Hora == false) {
				return false;
			}

			/*
			 * Desplazarse hacia la ubicación de la primera Tabla.
			 */
			testCase.addScreenShotCurrentStep(au, "testImage");

			au.scrollToElement(lb_ResumenCambiosProgramados);
			WebDriverWait wait2 = new WebDriverWait(au.driver, Duration.ofSeconds(15));
			wait2.until(ExpectedConditions.presenceOfElementLocated(lb_ResumenCambiosRealizados));

			String Realizados = au.driver.findElement(lb_ResumenCambiosRealizados).getText();
			if (Realizados.contains("RESUMEN CAMBIOS REALIZADOS") != true) {
				return false;
			}
			String Programados = au.driver.findElement(lb_ResumenCambiosProgramados).getText();
			if (Programados.contains("RESUMEN CAMBIOS PROGRAMADOS") != true) {
				return false;
			}

			/*
			 * Primera tabla "Resumen Cambios Realizados"
			 */

			boolean id = au.driver.findElement(tbId).isDisplayed();
			boolean articulo = au.driver.findElement(tbArticulo).isDisplayed();
			boolean descripcion = au.driver.findElement(tbDescripcion).isDisplayed();

			if ((id != true) || (articulo != true) || (descripcion != true)) {
				return false;
			}

			for (int i = 0; i < SkusEvo.size(); i++) {
				String sku = au.driver.findElement(By.xpath(
						"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_articulo_cf\"]"))
						.getText();
				if (!SkusEvo.contains(sku)) {
					return false;
				}
			}

			swipeCoord(694, 680, 57, 680, 3000);
			wait.until(ExpectedConditions.presenceOfElementLocated(tbRemision));
			testCase.addScreenShotCurrentStep(au, "testImage");

			boolean remision = au.driver.findElement(tbRemision).isDisplayed();
			boolean fecha = au.driver.findElement(tbfecha).isDisplayed();
			boolean dias = au.driver.findElement(tbDias).isDisplayed();

			if ((remision != true) || (fecha != true) || (dias != true)) {
				return false;
			}

			swipeCoord(694, 680, 57, 680, 3000);

			boolean cant = au.driver.findElement(tbCant).isDisplayed();
			boolean costo = au.driver.findElement(tbCosto).isDisplayed();

			if ((cant != true) || (costo != true)) {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			/*
			 * Segunda tabla "Resumen cambios programados pendientes"
			 */

			boolean ids = au.driver.findElement(tbPId).isDisplayed();
			boolean articulos = au.driver.findElement(tbPArticulo).isDisplayed();
			boolean descripciones = au.driver.findElement(tbPDescripcion).isDisplayed();

			if ((ids != true) || (articulos != true) || (descripciones != true)) {
				return false;
			}

			/*
			 * Desplazarse hacia la ubicación de las firmas y comentarios.
			 */

			testCase.addScreenShotCurrentStep(au, "testImage");
			au.scrollToElement(By.xpath(
					"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo\" and @text=\"Firma Proveedor:\"]"));
			au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

			boolean FirmadeAcuerdoCCF = au.driver.findElement(lb_FirmadeAcuerdoCCF).isEnabled();
			if (FirmadeAcuerdoCCF == false) {
				return false;
			}

			boolean ObsProvedor = au.driver.findElement(cp_ObsProvedor).isEnabled();
			if (ObsProvedor == false) {
				return false;
			}

			boolean FirmaProovedor = au.driver.findElement(lb_FirmaProovedor).isEnabled();
			if (FirmaProovedor == false) {
				return false;
			}

			au.scrollToElement(lb_ObsPersonal);
			testCase.addScreenShotCurrentStep(au, "testImage");

			boolean ObsPersonal = au.driver.findElement(lb_ObsPersonal).isEnabled();
			if (ObsPersonal == false) {
				return false;
			}

			boolean FirmaPersonal = au.driver.findElement(lb_FirmaPersonal).isEnabled();
			if (FirmaPersonal == false) {
				return false;
			}

			au.scrollToElement(bt_Imprimir);

			boolean Finalizar = au.driver.findElement(bt_Finalizar).isEnabled();
			if (Finalizar == false) {
				return false;
			}

			boolean Imprimir = au.driver.findElement(bt_Imprimir).isEnabled();
			if (Imprimir == false) {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");

			return true;

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/******************************************************************************************/
	/*
	 * Método - pantallaTiraDeEntregaConsultaCF - Realiza la validación de la
	 * pantalla Tira de Entrega
	 * 
	 * @return True/False
	 * 
	 * @author Alejandro Valdez
	 */

	public boolean pantallaAcuerdoDeEntregaCPCFRealizados() {

		try {

			/*
			 * Desplazarse hacia la ubicación de la primera Tabla.
			 */
			au.scrollToElement(lb_ResumenCambiosProgramados);
			au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

			String Realizados = au.driver.findElement(lb_ResumenCambiosRealizados).getText();
			if (Realizados.contains("RESUMEN CAMBIOS REALIZADOS") != true) {
				return false;
			}
			String Programados = au.driver.findElement(lb_ResumenCambiosProgramados).getText();
			if (Programados.contains("RESUMEN CAMBIOS PROGRAMADOS") != true) {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");

			/*
			 * Se valida apartado: Resumen de Cambios programados pendientes con la
			 * informacion de los articulos a modo de tabla(ID, Art, Desc, Fecha, Dias,
			 * Cant, Costo)
			 */

			/*
			 * Primera tabla "Resumen de Cambios programados"
			 */

			boolean id = au.driver.findElement(tbIdCP).isDisplayed();
			boolean articulo = au.driver.findElement(tbArticuloCP).isDisplayed();
			boolean descripcion = au.driver.findElement(tbDescripcionCP).isDisplayed();

			if ((id != true) || (articulo != true) || (descripcion != true)) {
				return false;
			}

			// Validar que no existen registros en esta tabla de Programados.

			validaNoSkusTablaResumenCambProgPendientes();

			System.out.println("Voy a validar la segunda tabla, ya valide la de programados pai");
			/*
			 * Segunda tabla "Resumen de Cambios Realizados"
			 */
			au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
			boolean ids = au.driver.findElement(tbPId).isDisplayed();
			boolean articulos = au.driver.findElement(tbPArticulo).isDisplayed();
			boolean descripcions = au.driver.findElement(tbPDescripcion).isDisplayed();

			if ((ids != true) || (articulos != true) || (descripcions != true)) {
				return false;
			}

			System.out.println("Valide Ids a descripción");

			// Validar Sku

			au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
			for (int i = 0; i < SkusEvo.size(); i++) {
				String sku = au.driver.findElement(By.xpath(
						"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_articulo_cf\"]"))
						.getText();
				if (!SkusEvo.contains(sku)) {
					return false;
				}
			}

			System.out.println("Valide los Skus");

			// Se muestran los articulos un identificador(ID) del lado izq.Ejemplo:

			String indices = au.driver.findElement(By.xpath(
					"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_id_cf\" and @text=\"1\"]"))
					.getText();
			String txts = "1";

			if (indices.equals(txts)) {
			} else {
				return false;
			}

			String indice2 = au.driver.findElement(By.xpath(
					"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_id_cf\" and @text=\"2\"]"))
					.getText();
			String txt2 = "2";

			if (indice2.equals(txt2)) {
			} else {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");

			// Obtener el precio del articulo que se puso como realizado.
			obtenerPrecioVentaXarticulosRealizados();

			// Se desplaza la tabla para validar la remisión.

			swipeCoord(680, 352, 53, 347, 3000);
			String lbRemisions = au.driver.findElement(By.xpath(
					"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_remision_cf\"])[1]"))
					.getText();

			if (lbRemisions.equals(RemisionNumber)) {
			} else {
				return false;
			}

			String lbRemision2 = au.driver.findElement(By.xpath(
					"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_remision_cf\"])[2]"))
					.getText();

			if (lbRemision2.equals(RemisionNumber)) {
			} else {
				return false;
			}

			boolean remision = au.driver.findElement(tbRemision).isDisplayed();
			boolean fecha = au.driver.findElement(tbfecha).isDisplayed();
			boolean dias = au.driver.findElement(tbDias).isDisplayed();

			if ((remision != true) || (fecha != true) || (dias != true)) {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");

			// Se desplaza la tabla para validar los costos.

			swipeCoord(680, 352, 53, 347, 3000);

			boolean cant = au.driver.findElement(tbCant).isDisplayed();
			boolean costo = au.driver.findElement(tbCosto).isDisplayed();

			if ((cant != true) || (costo != true)) {
				return false;
			}

			// Se toman tanto la cantidad como el costo y las multiplica para obtener el
			// valor total y verificarlo.

			String costoSku = au.driver.findElement(By.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_costo_cf"))
					.getText();
			System.out.println(costoSku);

			String cantidad = au.driver.findElement(By.xpath(
					"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_cant_cf\"])[1]"))
					.getText();
			System.out.println(cantidad);

			int precioTotal = (int) (precioVentaCCFPP * Integer.parseInt(cantidad));

			if (String.valueOf(precioTotal).contains(costoSku)) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");

			return true;

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/******************************************************************************************/
	/*
	 * Método - pantallaTiraDeEntregaConsultaCF - Realiza la validación de la
	 * pantalla Tira de Entrega
	 * 
	 * @return True/False
	 * 
	 * @author Alejandro Valdez
	 */

	public boolean pantallaAcuerdoDeEntregaCPCFRealizadoyProgramado() {

		try {

			/*
			 * Desplazarse hacia la ubicación de la primera Tabla.
			 */
			au.scrollToElement(lb_ResumenCambiosProgramados);
			au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

			String Realizados = au.driver.findElement(lb_ResumenCambiosRealizados).getText();
			if (Realizados.contains("RESUMEN CAMBIOS REALIZADOS") != true) {
				return false;
			}
			String Programados = au.driver.findElement(lb_ResumenCambiosProgramados).getText();
			if (Programados.contains("RESUMEN CAMBIOS PROGRAMADOS") != true) {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");

			/*
			 * Se valida apartado: Resumen de Cambios programados pendientes con la
			 * informacion de los articulos a modo de tabla(ID, Art, Desc, Fecha, Dias,
			 * Cant, Costo)
			 */

			/*
			 * Primera tabla "Resumen de Cambios programados"
			 */

			boolean id = au.driver.findElement(tbIdCP).isDisplayed();
			boolean articulo = au.driver.findElement(tbArticuloCP).isDisplayed();
			boolean descripcion = au.driver.findElement(tbDescripcionCP).isDisplayed();

			if ((id != true) || (articulo != true) || (descripcion != true)) {
				return false;
			}

			// Validar Sku

			for (int i = 0; i < SkusEvo.size(); i++) {
				String sku = au.driver.findElement(By.xpath(
						"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_articulo_cf\"]"))
						.getText();
				if (!SkusEvo.contains(sku)) {
					return false;
				}
			}

			// Se muestran los articulos un identificador(ID) del lado izq.Ejemplo:

			String indice = au.driver.findElement(By.xpath(
					"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_id_cf\"])[2]"))
					.getText();
			String txt = "1";

			if (indice.equals(txt)) {
			} else {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");

			// Se desplaza la tabla superior para obtener el articulo inferior y su precio
			swipeCoord(673, 469, 74, 473, 3000);
			obtenerPrecioVentaXarticulo2();

			// Se desplaza la tabla inferior para obtener el precio mostrado en la tabla
			swipeCoord(694, 680, 57, 680, 3000);
			au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(4000));

			// Los numeros de remision pertenecen a la FA

			String lbRemision = au.driver.findElement(By.xpath(
					"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_remision_cf\"]"))
					.getText();

			if (lbRemision.equals(RemisionNumber)) {
			} else {
				return false;
			}

			boolean remision = au.driver.findElement(tbRemision).isDisplayed();
			boolean fecha = au.driver.findElement(tbfecha).isDisplayed();
			boolean dias = au.driver.findElement(tbDias).isDisplayed();

			if ((remision != true) || (fecha != true) || (dias != true)) {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			swipeCoord(694, 680, 57, 680, 3000);

			boolean cant = au.driver.findElement(tbCant).isDisplayed();
			boolean costo = au.driver.findElement(tbCosto).isDisplayed();

			if ((cant != true) || (costo != true)) {
				return false;
			}

			// Se toman tanto la cantidad como el costo y las multiplica para obtener el
			// valor total y verificarlo.

			String costoSku = au.driver.findElement(By.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_costo_cf"))
					.getText();
			String cantidad = au.driver.findElement(By.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_cant_cf"))
					.getText();

			int precioTotal = (int) (precioVentaCCFPP * Integer.parseInt(cantidad));

			if (String.valueOf(precioTotal).contains(costoSku)) {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			// Se desplaza la tabla superior a su posición original
			swipeCoord(74, 473, 673, 469, 3000);

			/*
			 * Segunda tabla "Resumen de Cambios Realizados"
			 */

			boolean ids = au.driver.findElement(tbPId).isDisplayed();
			boolean articulos = au.driver.findElement(tbPArticulo).isDisplayed();
			boolean descripcions = au.driver.findElement(tbPDescripcion).isDisplayed();

			if ((ids != true) || (articulos != true) || (descripcions != true)) {
				return false;
			}

			// Validar Sku

			for (int i = 0; i < SkusEvo.size(); i++) {
				String sku = au.driver.findElement(By.xpath(
						"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_articulo_cf\"]"))
						.getText();
				if (!SkusEvo.contains(sku)) {
					return false;
				}
			}

			// Se muestran los articulos un identificador(ID) del lado izq.Ejemplo:

			String indices = au.driver.findElement(By.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_id_cf"))
					.getText();
			String txts = "1";

			if (indices.equals(txts)) {
			} else {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			// Obtener el precio del articulo que se puso como realizado.
			obtenerPrecioVentaXarticulo2();

			// Se desplaza la tabla inferior para obtener el precio mostrado en la tabla
			swipeCoord(655, 469, 99, 460, 3000);

			// Los numeros de remision pertenecen a la FA

			String lbRemisions = au.driver.findElement(By.xpath(
					"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_remision_cf\"])[1]"))
					.getText();

			if (lbRemisions.equals(RemisionNumber)) {
			} else {
				return false;
			}

			swipeCoord(678, 471, 30, 469, 3000);
			swipeCoord(41, 882, 653, 880, 3000);
			testCase.addScreenShotCurrentStep(au, "testImage");
			boolean remisions = au.driver.findElement(tbRemision).isDisplayed();
			boolean fechas = au.driver.findElement(tbfecha).isDisplayed();
			boolean diass = au.driver.findElement(tbDias).isDisplayed();

			if ((remisions != true) || (fechas != true) || (diass != true)) {
				return false;
			}

			boolean cants = au.driver.findElement(tbCant).isDisplayed();
			boolean costos = au.driver.findElement(tbCosto).isDisplayed();

			if ((cants != true) || (costos != true)) {
				return false;
			}

			// Se toman tanto la cantidad como el costo y las multiplica para obtener el
			// valor total y verificarlo.

			String costoSkus = au.driver.findElement(By.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_costo_cf"))
					.getText();
			String cantidads = au.driver.findElement(By.id("com.femco.oxxo.operaciones.mitienda.debug:id/txv_cant_cf"))
					.getText();

			int precioTotals = (int) (precioVentaCCFPP * Integer.parseInt(cantidads));

			if (String.valueOf(precioTotals).contains(costoSkus)) {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");

			return true;

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * obtienePrecioVentaXarticulo -
	 * 
	 * @autor Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean obtenerPrecioVentaXarticulo1() {
		try {
			String obj = "//android.widget.HorizontalScrollView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/resumen_cambios_realizados\"]//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_articulo_cf\"";
			String sku = au.driver.findElement(By.xpath(obj)).getText();
			Connection conn = DriverManager.getConnection(GlobalVariables.urlSatelitesPV10_17,
					GlobalVariables.userPV10_17, GlobalVariables.passwordPV10_17);
			String query = "EXEC Satelites.hc.GetInfoSku '" + sku + "'";
			SQLResult res = new SQLResult(conn, query);
			precioVentaCCFPP = Float.parseFloat(res.getData(0, "PrecioVenta")); // precioVentaCCFPP (variable global)
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean obtenerPrecioVentaXarticulo2() {
		try {
			String obj = "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_articulo_cf\"]";
			String sku = au.driver.findElement(By.xpath(obj)).getText();
			Connection conn = DriverManager.getConnection(GlobalVariables.urlSatelitesPV10_17,
					GlobalVariables.userPV10_17, GlobalVariables.passwordPV10_17);
			String query = "EXEC Satelites.hc.GetInfoSku '" + sku + "'";
			SQLResult res = new SQLResult(conn, query);
			precioVentaCCFPP = Float.parseFloat(res.getData(0, "PrecioVenta")); // precioVentaCCFPP (variable global)
			System.out.println(precioVentaCCFPP);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean obtenerPrecioVentaXarticulosRealizados() {
		try {
			String obj = "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_articulo_cf\"]";
			String sku = au.driver.findElement(By.xpath(obj)).getText();
			Connection conn = DriverManager.getConnection(GlobalVariables.urlSatelitesPV10_17,
					GlobalVariables.userPV10_17, GlobalVariables.passwordPV10_17);
			String query = "EXEC Satelites.hc.GetInfoSku '" + sku + "'";
			SQLResult res = new SQLResult(conn, query);
			precioVentaCCFPP = Float.parseFloat(res.getData(0, "PrecioVenta")); // precioVentaCCFPP (variable global)
			System.out.println(precioVentaCCFPP);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validación de Pantalla Tira de Entrega con Opción Sin Comentarios en ambas y
	 * firmas ingresadas.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean pantallaTiraDeEntregaOpciónSinComentariosConFirmas() {

		try {

			// Tiempo de espera para que cargue la pantalla.

			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
			wait.until(ExpectedConditions.presenceOfElementLocated(lb_TiraDeEntrega));

			// Válida los Headers de la pantalla de Tira de Entrega

			boolean TitTiraDeEntrega = au.driver.findElement(lb_TiraDeEntrega).isEnabled();
			if (TitTiraDeEntrega == false) {
				return false;
			}

			boolean PregFrecuentes = au.driver.findElement(bt_PregFrecuentes).isEnabled();
			if (PregFrecuentes == false) {
				return false;
			}

			boolean Tutoriales = au.driver.findElement(bt_Tutoriales).isEnabled();
			if (Tutoriales == false) {
				return false;
			}

			// Desplazarse hacia la ubicación de la primera Tabla.

			au.scrollToElement(By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo_cf_tira_programados"));
			wait.until(ExpectedConditions.presenceOfElementLocated(lb_ResumenCambiosRealizados));

			// Válida la existencia de la tabla de Resumen Cambios Realizados y que esta
			// contenga Sku´s.

			boolean nombreTabla = au.driver.findElement(lb_ResumenCambiosRealizados).isDisplayed();
			if (nombreTabla == true) {
				testCase.addTextEvidenceCurrentStep("Se valida existencia de tabla: Resumen Cambios Realizados");
				testCase.addScreenShotCurrentStep(au, "testImage");
			} else {
				return false;
			}

			boolean id = au.driver.findElement(hd_idCambRealiz).isDisplayed();
			boolean art = au.driver.findElement(hd_artCambRealiz).isDisplayed();
			boolean desc = au.driver.findElement(hd_descCambRealiz).isDisplayed();
			boolean cant = au.driver.findElement(hd_cantCambRealiz).isDisplayed();
			if (id != true || art != true || desc != true || cant != true) {
				return false;
			} else {
				for (int i = 1; i <= 2; i++) {
					String padre = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/rv_tira_entrega_cf_realizados\"]/android.widget.LinearLayout["
							+ i + "]";
					String sku = au.driver.findElement(By.xpath(padre
							+ "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_articulo_cf_realizado\"]"))
							.getText();
					if (!SkusEvo.contains(sku)) {
						return false;
					}
				}
				testCase.addTextEvidenceCurrentStep("Se valida existencia de articulos.");
				testCase.addScreenShotCurrentStep(au, "testImage");

			}

			// Desplazarse hacia la ubicación de la segunda Tabla de Cambios Fisicos
			// Programados.

			au.scrollToElement(By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvw_leyenda"));

			// Se válida que la segunda tabla este vacia ya que no se programaron cambios
			// fisicos.

			boolean skus;
			try {
				skus = au.driver.findElement(By.xpath(
						"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_articulo_cf_programado\"]"))
						.isDisplayed();
			} catch (Exception e) {
				skus = false;
			}

			if (skus == true) {
				return false;
			}

			testCase.addTextEvidenceCurrentStep(
					"Se valida ausencia de tabla y articulos: Resumen Cambios Programados Pendientes");
			testCase.addScreenShotCurrentStep(au, "testImage");

			boolean SelloAutorizado = au.driver.findElement(lb_SelloAutorizado).isEnabled();
			if (SelloAutorizado == false) {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");

			// Desplazarse hacia el area de Observaciones y Firmas para realizar su
			// validación.

			au.scrollToElement(By.xpath(
					"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo\" and @text=\"Firma Proveedor:\"]"));

			String ObservacionesProvedor = au.driver.findElement(cp_ObservacionesProveedorTiraEntrega).getText();
			String txtProveedor = "SIN COMENTARIOS";

			if (ObservacionesProvedor.equals(txtProveedor)) {
			} else {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");

			au.scrollToElement(By.xpath(
					"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo\" and @text=\"Firma Personal Tienda:\"]"));

			String ObservacionesPersonalTienda = au.driver.findElement(cp_ObservacionesPersonalTiendaTiraEntrega)
					.getText();
			String txtPersonal = "SIN COMENTARIOS";

			if (ObservacionesPersonalTienda.equals(txtPersonal)) {
			} else {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");

			// Desplazarse hacia ultimo apartado de la pantalla Tira de Entrega y validar
			// sus ultimos botones.

			au.scrollToElement(bt_Imprimir);

			boolean Finalizar = au.driver.findElement(bt_Finalizar).isEnabled();
			if (Finalizar == false) {
				return false;
			}

			boolean Imprimir = au.driver.findElement(bt_Imprimir).isEnabled();
			if (Imprimir == false) {
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
	 * Seleccionar Botón Imprimir.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */
	public boolean opcionImprimir() {
		try {
			au.click(bt_Imprimir);
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
	 * Validación de que Text Box para captura de fuente de abasto se encuentre
	 * vacio.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */
	public boolean validarCapturaFAoRemisionVacia() {
		try {
			String camFA = au.driver.findElement(tb_CapFAoNumRemision).getText();
			if (camFA.isEmpty()) {
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public boolean validarCapturarFAValida() {
		try {
			String camFA = au.driver.findElement(tb_CapturaFuenteAbasto).getText();
			if (camFA.isEmpty()) {
				return false;
			} else {
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public boolean validarCapturarFAoNumRemision() {
		try {
			String camFA = au.driver.findElement(tb_CapFAoNumRemision).getText();
			if (camFA.isEmpty()) {
				return false;
			} else {
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Capturar Número de Remisión Invalida y Validar su Captura.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean opcionCapturarNumRemisionInValidaPantallaConsultaCambiosFisicos() {
		try {
			au.sendKeys(tb_CapFAoNumRemision, "00000");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("El campo no se encuentra disponible");
			return false;
		}
	}

	public boolean validarCapturarNumRemisionInvalida() {
		try {
			String camFA = au.driver.findElement(tb_CapFAoNumRemision).getText();
			if (camFA.isEmpty()) {
				testCase.addTextEvidenceCurrentStep("El campo para ingresar número de proveedor está vacío.");
				return false;
			} else {
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			}
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error al capturar la FA: " + e.getMessage());
			return false;
		}
	}

	public void capturarRemisionConCambiosFisicos() {
		au.sendKeys(tb_CapFAoNumRemision, RemisionNumber); // RemisionNumber
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	public boolean opcionBuscarSEB() {
		try {
			au.click(bt_Buscar);
			au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
			if (au.driver.findElement(By.xpath(
					"//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"Ya se han realizado cambios físicos para este proveedor el día de hoy. ¿Desea continuar?\"]"))
					.isDisplayed()) {
				au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
				au.click(By.id("android:id/button1"));
				au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(4000));
			}
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			return false;
		}
	}

	public boolean capturarCFProgramado2() {
		try {

			au.sendKeys(By.xpath(
					"(//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edt_cfisico_programado\"])[2]"),
					"1");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Capturar un CF Programado.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean colocarRealizadoCF() {
		try {

			au.click(By.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"PENDIENTE\"]"));
			au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
			au.click(By.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"REALIZADO\"]"));

			String mensajeMostrado = au.driver
					.findElement(By.xpath(
							"//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"REALIZADO\"]"))
					.getText();
			String txtWarning = "REALIZADO";

			if (mensajeMostrado.equals(txtWarning)) {
			} else {
				return false;
			}

			au.click(By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_sumar"));

			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			return false;
		}
	}

	public boolean colocarRealizadoCF2Articulos2() {
		try {

			swipeCoord(340, 935, 340, 723, 3000);

			au.click(By.xpath("(//android.widget.TextView[@resource-id=\"android:id/text1\"])[2]"));
			au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
			au.click(By.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"REALIZADO\"]"));
			String mensajeMostrado = au.driver.findElement(By.xpath("(//android.widget.TextView[@resource-id=\"android:id/text1\"])[2]")).getText();
			String txtWarning = "REALIZADO";
			if (mensajeMostrado.equals(txtWarning)) {
			} else {
				return false;
			}
			au.click(By.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"PENDIENTE\"]"));
			au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
			au.click(By.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"REALIZADO\"]"));
			String mensajeMostrados = au.driver.findElement(By.xpath("(//android.widget.TextView[@resource-id=\"android:id/text1\"])[1]")).getText();
			String txtWarnings = "REALIZADO";

			if (mensajeMostrados.equals(txtWarnings)) {
			} else {
				return false;
			}
			au.click(By.xpath("(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btn_sumar\"])[1]"));
			au.click(By.xpath("(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btn_sumar\"])[2]"));
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			return false;
		}
	}

	public boolean colocarRealizadoCF2Articulos() {
		try {

			swipeCoord(340, 935, 340, 723, 3000);

			au.click(By.xpath("(//android.widget.TextView[@resource-id=\"android:id/text1\"])[2]"));
			au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
			au.click(By.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"REALIZADO\"]"));
			String mensajeMostrado = au.driver.findElement(By.xpath("(//android.widget.TextView[@resource-id=\"android:id/text1\"])[2]")).getText();
			String txtWarning = "REALIZADO";
			if (mensajeMostrado.equals(txtWarning)) {
			} else {
				return false;
			}
			au.click(By.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"PENDIENTE\"]"));
			au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
			au.click(By.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"REALIZADO\"]"));
			String mensajeMostrados = au.driver.findElement(By.xpath("(//android.widget.TextView[@resource-id=\"android:id/text1\"])[3]")).getText();
			String txtWarnings = "REALIZADO";
			if (mensajeMostrados.equals(txtWarnings)) {
			} else {
				return false;
			}
			au.click(By.xpath("(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btn_sumar\"])[1]"));
			au.click(By.xpath("(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btn_sumar\"])[2]"));
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			return false;
		}
	}

	public boolean opcionBuscarConsultaCambiosFisicosSinEvidencia() {
		try {
			au.click(bt_BuscarProveedorRemision);
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("El botón no se encuentra disponible");
			return false;
		}
	}

	public boolean validarNumRemisionDeFuentedeAbasto() {
		try {
			String lbRemision = au.driver.findElement(By.xpath(
					"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvwNumeroRemisionValue\"])[1]"))
					.getText();

			if (lbRemision.equals(RemisionNumber)) {
			} else {
				testCase.addTextEvidenceCurrentStep("No coinciden las remisiones.");
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("No coinciden las remisiones.");
			return false;
		}
	}

	public boolean validarNumFuentedeAbasto() {
		try {

			au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1500));
			String lbFA = au.driver.findElement(By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvwResultadoFuenteDeAbastoValue\"]")).getText();
			if (lbFA.equals(FaEvo)) {
			} else {
				testCase.addTextEvidenceCurrentStep("No coinciden las FA.");
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("No coinciden las FA.");
			return false;
		}
	}

	public boolean validarBotonSiguienteImprimir() {
		try {

			au.scrollToElement(bt_ImprimirCCF);

			boolean Siguiente = au.driver.findElement(bt_SiguienteCCF).isDisplayed();
			if (Siguiente == false) {
				return false;
			}

			boolean Imprimir = au.driver.findElement(bt_ImprimirCCF).isDisplayed();
			if (Imprimir == false) {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");

			au.driver.findElement(AppiumBy.androidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"Fuente de Abasto:\"))"));

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
	 * Validar Mensaje de Warning "Favor de agregar Observaciones del Proveedor"
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean mensajeNoSeAgrearonObservacionesProveedor() {
		try {
			String mensajeMostrado = au.driver.findElement(Warnings).getText();
			String txtWarning = "Favor de agregar Observaciones del Proveedor";

			String mensajeAceptar = au.driver.findElement(bt_Si).getText();
			String Aceptar = "ACEPTAR";

			if (mensajeMostrado.equals(txtWarning)) {
			} else {
				return false;
			}

			if (mensajeAceptar.equals(Aceptar)) {
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
	 * Validar Mensaje de Warning "Favor de agregar Firma del Proveedor"
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean mensajeNoSeAgregoFirmaProveedor() {
		try {
			String mensajeMostrado = au.driver.findElement(Warnings).getText();
			String txtWarning = "Favor de agregar Firma del Proveedor";

			String mensajeAceptar = au.driver.findElement(bt_Si).getText();
			String Aceptar = "ACEPTAR";

			if (mensajeMostrado.equals(txtWarning)) {
			} else {
				return false;
			}

			if (mensajeAceptar.equals(Aceptar)) {
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
	 * Validar Mensaje de Warning "Favor de agregar Firma del Personal de Tienda"
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean mensajeNoSeAgregoFirmaPersonalTienda() {
		try {
			String mensajeMostrado = au.driver.findElement(Warnings).getText();
			String txtWarning = "Favor de agregar Firma del Personal de Tienda";

			String mensajeAceptar = au.driver.findElement(bt_Si).getText();
			String Aceptar = "ACEPTAR";

			if (mensajeMostrado.equals(txtWarning)) {
			} else {
				return false;
			}

			if (mensajeAceptar.equals(Aceptar)) {
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
	 * Validar Mensaje de Warning "Favor de agregar Firma del Proveedor"
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean mensajeNoSeAgrearonObservacionesPersonalTienda() {
		try {
			String mensajeMostrado = au.driver.findElement(Warnings).getText();
			String txtWarning = "Favor de agregar Observaciones del Personal de Tienda";

			String mensajeAceptar = au.driver.findElement(bt_Si).getText();
			String Aceptar = "ACEPTAR";

			if (mensajeMostrado.equals(txtWarning)) {
			} else {
				return false;
			}

			if (mensajeAceptar.equals(Aceptar)) {
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
	 * Validar Mensaje de Warning "Se muestra el warning "Numero de remision no
	 * encontrado. Favor de validar que el numero de remision sea el correcto"
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean mensajeNumRemisionNoEncontrado() {
		try {
			au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1500));
			String mensajeMostrado = au.driver.findElement(Warnings).getText();
			String txtWarning = "Número de remisión no encontrado. Favor de validar que el número de remisión sea el correcto.";

			if (mensajeMostrado.equals(txtWarning)) {
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
	 * Validar Mensaje de Warning "Se muestra el warning "Este proveedor no cuenta
	 * con cambios físicos pendientes."
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean mensajeProveedorSinCambiosFisicos() {
		try {
			au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1500));
			String mensajeMostrado = au.driver.findElement(Warnings).getText();
			String txtWarning = "Este proveedor no cuenta con cambios físicos pendientes.";

			if (mensajeMostrado.equals(txtWarning)) {
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
	 * Validar Mensaje de Warning "El correo se está enviando al proveedor, revise
	 * la barra de notificaciones" ACEPTAR.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean mensajeEnviandoCorreo(){
		au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1500));
		try {
			String mensajeMostrado = au.driver.findElement(Warnings).getText();
			String txtWarning = "El correo se está enviando al proveedor, revise la barra de notificaciones.";
			String mensajeAceptar = au.driver.findElement(bt_Si).getText();
			String Aceptar = "ACEPTAR";
			if (mensajeMostrado.equals(txtWarning)) {
			} else {
				return false;
			}

			if (mensajeAceptar.equals(Aceptar)) {
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

	public boolean mensajeCorreoBarraNotificaciones() {
		try {

			boolean btcorreoBarra = au.driver.findElement(correoBarra).isDisplayed();
			if (btcorreoBarra == false) {
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
	 * Validar Mensaje de Warning "La captura de cambios físicos se ha realizado de
	 * manera exitosa." ACEPTAR.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean mensajeCapturaCFExitosa() {
		try {

			String mensajeMostrado = au.driver.findElement(Warnings).getText();
			String txtWarning = "La captura de cambios físicos se ha realizado de manera exitosa.";

			String mensajeAceptar = au.driver.findElement(bt_Si).getText();
			String Aceptar = "ACEPTAR";

			if (mensajeMostrado.equals(txtWarning)) {
			} else {
				return false;
			}

			if (mensajeAceptar.equals(Aceptar)) {
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
	 * Realiza el proceso necesario hasta llegar a pantalla Detalle de Pedido
	 * leyendo QR de Visita Guiada sin generar evidencia.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public void ingresarDetallePedidoVisitaGuiadaSinEvidencia() {

		au.click(bt_ExcOperativa);

		au.click(bt_HerrCompra);

		au.click(bt_Cometa);

		au.click(bt_VerMercancia);

		au.click(bt_LeerQR);

		//au.click(bt_No);

		String textQr = ProveedoresDirectos.QrGeneradoVisitaGuiada;

		au.sendKeys(tb_IngresarQr, textQr);

		au.click(bt_CapturarQR);

		au.click(bt_AceptarCapturaQrVisitaGuiada);
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
	 * Validación de la ausencia de la pantalla Visita Guiada Surtido Tradicional
	 * Por Captura Manual.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean ausenciaPantallaVisitaGuiada() {
		try {
			boolean textoSuperiorVisitaGuiada = au.driver.findElement(lb_textoSuperiorVisitaGuiada).isDisplayed();
			if (textoSuperiorVisitaGuiada == false) {
				return false;
			}

			boolean textoResaltadoGenerarPedidoyEntregaTradicional = au.driver
					.findElement(lb_CaptureNumRemisionVisitaGuiada).isDisplayed();
			if (textoResaltadoGenerarPedidoyEntregaTradicional == false) {
				return false;
			}

			boolean SalirVisitaGuiada = au.driver.findElement(bt_SalirVisitaGuiada).isDisplayed();
			if (SalirVisitaGuiada == false) {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
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

	public boolean existenciaPantallaVisita() {

		try {
			boolean textoSuperiorVisitaGuiada = au.driver.findElement(lb_textoSuperiorVisitaGuiada).isDisplayed();
			if (textoSuperiorVisitaGuiada == false) {
				return false;
			}
			boolean textoResaltadoGenerarPedidoyEntregaTradicional = au.driver
					.findElement(lb_CaptureNumRemisionVisitaGuiada).isDisplayed();
			if (textoResaltadoGenerarPedidoyEntregaTradicional == false) {
				return false;
			}
			boolean SalirVisitaGuiada = au.driver.findElement(bt_SalirVisitaGuiada).isDisplayed();
			if (SalirVisitaGuiada == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
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
	 * Validación de que la pantalla Principal de Verificación de Mercancia Pedidos
	 * Proveedor Directo.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean existenciaPantallaVerificacióndeMercanciaPedidos() {

		try {
			au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
			boolean ProveedoresDirectos = au.driver.findElement(lb_ProveedoresDirectos).isDisplayed();

			if (ProveedoresDirectos == false) {
				return false;
			}
			boolean BusqPedidosPrecargados = au.driver.findElement(lb_BusqPedidosPrecargados).isDisplayed();

			if (BusqPedidosPrecargados == false) {
				return false;
			}
			boolean LeerQR = au.driver.findElement(lb_LeerQR).isDisplayed();

			if (LeerQR == false) {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
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
	 * Validar Mensaje de Warning ¿Estás seguro que deseas salir de la Visita
	 * Guiada?
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return N/A
	 */

	public boolean mensajeSalirDeVisitaGuiada() {
		try {
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
	 * Seleccionar Botón HOME nativo del dispostivo.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean botonHOME() {
		try {
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.HOME));
			au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1500));
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("El botón no se encuentra disponible");
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * obtenerFAConCambiosFisicos - Retorna la ultima fuente de abasto a la que se
	 * le realizo un cambio fisico.
	 * 
	 * @author Alejandro Valdez
	 * 
	 * @param storePlace - Cantidad de Sku´s a Ingresar en el listado.
	 * 
	 * @return FANumber(Fuente de Abasto con Cambio Fisico)
	 */
	public String obtenerFAConCambiosFisicos() {
		try {
			Connection conn = DriverManager.getConnection(GlobalVariables.urlEvolucion, GlobalVariables.userEvolucion,
					PasswordUtil.decryptPassword(GlobalVariables.passEvolucion));
			String query = "SELECT TOP 1 t.CD_NMB_SPR "
					+ "FROM [XposCloudPurchaseToolQA].[dbo].[DI_PHYSICAL_CHANGES_HEADER] h "
					+ "LEFT JOIN [XposCloudPurchaseToolQA].[dbo].[DI_PHYSICAL_CHANGES_DETAIL] d "
					+ "    ON h.DI_PHYSICAL_CHANGES_HEADER_ID = d.DI_PHYSICAL_CHANGES_HEADER_ID "
					+ "INNER JOIN [XposCloudPurchaseToolQA].[dbo].[PA_SPR] t " + "    ON t.PA_SPR_ID = h.PA_SPR_ID "
					+ "WHERE h.STORE_PLACE_ID = '" + storePlaceID + "' "
					+ "GROUP BY h.PA_SPR_ID, t.CD_NMB_SPR, h.REMISION_NUMBER, h.DI_PHYSICAL_CHANGES_HEADER_ID "
					+ "HAVING SUM(CASE WHEN d.QUANTITY_PENDING >= 1 THEN 1 ELSE 0 END) > 0";
			SQLResult res = new SQLResult(conn, query);
			FaEvo = String.valueOf(Integer.parseInt(res.getFirstData().toString()));

			System.out.println(FaEvo);
			System.out.println(FaEvo);
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return FaEvo;
	}

	public String obtenerRemisionRelacionadaconFA() {
		try {
			Connection conn = DriverManager.getConnection(GlobalVariables.urlEvolucion, GlobalVariables.userEvolucion,
					PasswordUtil.decryptPassword(GlobalVariables.passEvolucion));
			String query = "SELECT TOP 1 h.REMISION_NUMBER "
					+ "FROM [XposCloudPurchaseToolQA].[dbo].[DI_PHYSICAL_CHANGES_HEADER] h "
					+ "LEFT JOIN [XposCloudPurchaseToolQA].[dbo].[DI_PHYSICAL_CHANGES_DETAIL] d "
					+ "    ON h.DI_PHYSICAL_CHANGES_HEADER_ID = d.DI_PHYSICAL_CHANGES_HEADER_ID "
					+ "INNER JOIN [XposCloudPurchaseToolQA].[dbo].[PA_SPR] t " + "    ON t.PA_SPR_ID = h.PA_SPR_ID "
					+ "WHERE h.STORE_PLACE_ID = '" + storePlaceID + "' AND t.CD_NMB_SPR = '" + FaEvo + "' "
					+ "GROUP BY h.PA_SPR_ID, t.CD_NMB_SPR, h.REMISION_NUMBER, h.DI_PHYSICAL_CHANGES_HEADER_ID "
					+ "HAVING SUM(CASE WHEN d.QUANTITY_PENDING >= 1 THEN 1 ELSE 0 END) > 0";
			SQLResult res = new SQLResult(conn, query);
			RemisionNumber = String.valueOf(res.getFirstData().toString());

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return RemisionNumber;
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * ObtenerFAValidaNubeEvo - Obtiene una FA en Nube Evolucion
	 *
	 * @autor Alex Aquino
	 * 
	 * @return N/A
	 */
	public boolean ObtenerFANubeEvo() {
		try {
			Connection conn = DriverManager.getConnection(GlobalVariables.urlEvolucion, GlobalVariables.userEvolucion,
					PasswordUtil.decryptPassword(GlobalVariables.passEvolucion));
			String query = "SELECT TOP 1 SPR.CD_NMB_SPR\r\n" + "FROM [XposCloudPurchaseToolQA].[dbo].[PA_SPR] SPR\r\n"
					+ "WHERE SPR.STORE_PLACE_ID = '" + storePlaceID + "'\r\n" + "AND NOT EXISTS (\r\n"
					+ "    SELECT 1 \r\n"
					+ "    FROM [XposCloudPurchaseToolQA].[dbo].[DI_PHYSICAL_CHANGES_HEADER] DI\r\n"
					+ "    WHERE DI.PA_SPR_ID = SPR.PA_SPR_ID \r\n"
					+ "    AND DI.STORE_PLACE_ID = SPR.STORE_PLACE_ID\r\n" + ")\r\n" + "ORDER BY NEWID()";
			SQLResult res2 = new SQLResult(conn, query);
			FaEvo = res2.getFirstData().toString();
			System.out.println("FA: " + FaEvo);
			conn.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void obtenerSkusValidosxFANubeEvob(int CantSkus, int CantInv) {
		try {
			Connection conn = DriverManager.getConnection(GlobalVariables.urlEvolucion, GlobalVariables.userEvolucion,
					PasswordUtil.decryptPassword(GlobalVariables.passEvolucion));
			if (FaEvo == null || FaEvo == "") {
				ObtenerFAValidaNubeEvo(CantSkus, CantInv);
			}
			List<String> Cantidades = new ArrayList<>();
			String query = "use XposCloudKardexBalanceQA\r\n" + "DECLARE @CR_PLACE NVARCHAR(40) = '" + crPlaza + "'\r\n"
					+ "DECLARE @CR_STORE NVARCHAR(40) ='" + crTienda + "' \r\n"
					+ "DECLARE @SUPPLY_SOURCE NVARCHAR(40) ='" + FaEvo + "' \r\n"
					+ "DECLARE @REPORTING_PERIOD_ID INT = 0\r\n" + "DECLARE @LAST_DATETIME DATETIME     \r\n"
					+ "DECLARE @BUSINESS_DATE_INIT DATE\r\n" + "DECLARE @BUSINESS_DATE_END DATE\r\n"
					+ "DECLARE @PREVIOUS_KARDEX INT = 0\r\n" + "DECLARE @STORE_PLACE_ID int\r\n" + "\r\n"
					+ "SELECT @STORE_PLACE_ID = SP.STORE_PLACE_ID \r\n" + "FROM STORE_PLACE SP WITH(NOLOCK) \r\n"
					+ "WHERE SP.CR_PLACE = @CR_PLACE \r\n" + "AND SP.CR_STORE = @CR_STORE\r\n" + "\r\n"
					+ "EXEC dbo.SP_GET_INVENTORY_DATE_RANGE @STORE_PLACE_ID, @REPORTING_PERIOD_ID OUT,  @PREVIOUS_KARDEX, @BUSINESS_DATE_INIT OUT, @BUSINESS_DATE_END OUT\r\n"
					+ "\r\n" + "DECLARE @ITM_FOR_STOCK TABLE (\r\n" + "Number INT IDENTITY(1,1) ,\r\n"
					+ "ITEM_ID UNIQUEIDENTIFIER, \r\n" + "STORE_PLACE_ID INT, \r\n" + "SKU VARCHAR(60),\r\n"
					+ "CD_NMB_SPR VARCHAR(60),\r\n" + "NAME VARCHAR(200), \r\n" + "PRICE DECIMAL(19,6), \r\n"
					+ "IS_ACTIVE BIT, \r\n" + "ITEM_TYPE_CODE VARCHAR(100), \r\n" + "total int )\r\n" + "\r\n"
					+ "INSERT INTO @ITM_FOR_STOCK (ITEM_ID , STORE_PLACE_ID , SKU, CD_NMB_SPR , NAME , PRICE , IS_ACTIVE , ITEM_TYPE_CODE, total)\r\n"
					+ "SELECT  I.ITEM_ID, I.STORE_PLACE_ID, I.SKU, SPR.CD_NMB_SPR, I.NAME, I.SALE_PRICE AS PRICE, I.IS_ACTIVE, I.ITEM_TYPE_CODE,0\r\n"
					+ "FROM ITEM I WITH(NOLOCK)\r\n"
					+ "INNER JOIN [XposCloudItemQA].[dbo].[ITEM] IQ WITH(NOLOCK) ON IQ.SKU = I.SKU\r\n"
					+ "INNER JOIN [XposCloudItemQA].[dbo].[ST_ITM_SPR_STK] ITMSPR WITH(NOLOCK) ON IQ.ITEM_ID = ITMSPR.ID_ITM\r\n"
					+ "INNER JOIN [XposCloudItemQA].[dbo].[PA_SPR] SPR WITH(NOLOCK) ON ITMSPR.ID_SPR = SPR.PA_SPR_ID\r\n"
					+ "WHERE I.STORE_PLACE_ID = @STORE_PLACE_ID\r\n" + "\r\n"
					+ "DROP TABLE IF EXISTS #InventarioQAScripts2\r\n" + "CREATE TABLE #InventarioQAScripts2 (\r\n"
					+ "[NO] [Int],\r\n" + "[SKU] [varchar](60),\r\n" + "[SALEPRICE] [DECIMAL](16,4),\r\n"
					+ "[DESCRIPTION] [varchar](200),\r\n" + "[QUANTITY] [int]\r\n" + ")\r\n"
					+ "INSERT INTO #InventarioQAScripts2\r\n"
					+ "SELECT i.Number as no,I.SKU, I.PRICE as salePrice, I.NAME as description,\r\n"
					+ "CAST(ISNULL(SUM(CASE WHEN MT.CODE IN ('IN_UNIT_VALUE_MOVEMENT','INITIAL_MOVEMENT') THEN IVJE.RETAIL_UNIT_COUNT WHEN MT.CODE IN ('OUT_UNIT_VALUE_MOVEMENT') THEN IVJE.RETAIL_UNIT_COUNT * -1 END), 0) AS int) AS quantity\r\n"
					+ "FROM STOCK_BALANCE SB WITH(NOLOCK)\r\n"
					+ "INNER JOIN ITEM_INVENTORY_JOURNAL_ENTRY IVJE WITH(NOLOCK) ON SB.STOCK_BALANCE_ID = IVJE.STOCK_BALANCE_ID AND SB.STORE_PLACE_ID = @STORE_PLACE_ID\r\n"
					+ "INNER JOIN MOVEMENT WITH(NOLOCK) ON MOVEMENT.MOVEMENT_ID = IVJE.MOVEMENT_ID \r\n"
					+ "INNER JOIN MOVEMENT_TYPE MT WITH(NOLOCK) ON MT.MOVEMENT_TYPE_ID = MOVEMENT.MOVEMENT_TYPE_ID\r\n"
					+ "INNER JOIN @ITM_FOR_STOCK I ON I.ITEM_ID = IVJE.ITEM_ID AND I.STORE_PLACE_ID = @STORE_PLACE_ID\r\n"
					+ "WHERE SB.BUSSINESS_DATE >= @BUSINESS_DATE_INIT\r\n"
					+ "AND MT.VALUE IN  ('0','1','2') AND I.CD_NMB_SPR = @SUPPLY_SOURCE\r\n"
					+ "GROUP BY I.Number, I.SKU, I.PRICE, I.NAME";
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(query);
			String query2 = "select DISTINCT top " + CantSkus
					+ " (SKU),QUANTITY,SALEPRICE,DESCRIPTION from #InventarioQAScripts2\r\n"
					+ "group by SKU,QUANTITY,SALEPRICE,DESCRIPTION\r\n" + "Having QUANTITY >= " + CantInv + "";
			SQLResult res = new SQLResult(conn, query2);
			// Guardado en lista
			for (int i = 0; i < res.getRowCount(); i++) {
				SkusEvo.add(res.getData(i, "SKU"));
				Cantidades.add(res.getData(i, "QUANTITY"));
			}
			// Impresion/uso/vaciado de lista
			System.out.println("Skus de la FA(" + FaEvo + "): ");
			for (int i = 0; i < SkusEvo.size(); i++) {
				System.out.println(SkusEvo.get(i) + " (Op:" + Cantidades.get(i) + ")");
			}
			stmt.close();
			conn.close();
		} catch (Exception e) {

		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar Botón Botón Cambios físicos de productos (Proveedores Directos)
	 * Sin Evidencia.
	 * 
	 * @author Octavio Hernandez
	 * @param N/A
	 * @return True or False
	 */

	public boolean opcionCambiosFisicosSinEvidencia() {
		try {
			au.click(bt_CambFisicos);
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
	 * Validación de Pantalla Cambios Físicos.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean pantallaCambiosFisicos2() {
		try {

			boolean Regresar = au.driver.findElement(bt_Regresar).isEnabled();
			if (Regresar == false) {
				return false;
			}

			boolean TitCambiosFisicos = au.driver.findElement(Tit_CambiosFisicos2).isEnabled();
			if (TitCambiosFisicos == false) {
				return false;
			}

			boolean PregFrecuentes = au.driver.findElement(bt_PregFrecuentes).isEnabled();
			if (PregFrecuentes == false) {
				return false;
			}

			boolean Tutoriales = au.driver.findElement(bt_Tutoriales).isEnabled();
			if (Tutoriales == false) {
				return false;
			}

			boolean CambiosFisicos = au.driver.findElement(bt_CapCambiosFisicos).isEnabled();
			if (CambiosFisicos == false) {
				return false;
			}

			boolean ConsultCambiosFisicos = au.driver.findElement(bt_ConsultCambiosFisicos).isEnabled();
			if (ConsultCambiosFisicos == false) {
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
	 * Validación de Pantalla Cambios Físicos.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean pantallaCambiosFisicosSinEvidencia() {
		try {

			boolean Regresar = au.driver.findElement(bt_Regresar).isEnabled();
			if (Regresar == false) {
				testCase.addTextEvidenceCurrentStep("El botón para Regresar no esta habilitado");
			}

			boolean TitCambiosFisicos = au.driver.findElement(Tit_CambiosFisicos).isEnabled();
			if (TitCambiosFisicos == false) {
				testCase.addTextEvidenceCurrentStep("El Titulo de Cambios Fisicos no se encuentra visible.");
			}

			boolean PregFrecuentes = au.driver.findElement(bt_PregFrecuentes).isEnabled();
			if (PregFrecuentes == false) {
				testCase.addTextEvidenceCurrentStep("El botón de Preguntas Frecuenes no esta habilitado");
			}

			boolean Tutoriales = au.driver.findElement(bt_Tutoriales).isEnabled();
			if (Tutoriales == false) {
				testCase.addTextEvidenceCurrentStep("El botón de Tutotiales no esta habilitado");
			}

			boolean CambiosFisicos = au.driver.findElement(bt_CapCambiosFisicos).isEnabled();
			if (CambiosFisicos == false) {
				testCase.addTextEvidenceCurrentStep("El botón de Cambios Físicos no esta habilitado");
			}

			boolean ConsultCambiosFisicos = au.driver.findElement(bt_ConsultCambiosFisicos).isEnabled();
			if (ConsultCambiosFisicos == false) {
				testCase.addTextEvidenceCurrentStep("El botón de Consulta Cambios Físicos no esta habilitado");
			}

			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("La pantalla de Cambios Físicos no se muestra de manera correcta.");
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar Botón Capturar cambios físicos Sin Evdencia.
	 * 
	 * @author Octavio Hernandez
	 * @param N/A
	 * @return True or False
	 */

	public boolean opcionCapturarCambiosFisicosSinEvidencia() {
		try {
			au.click(bt_CapCambiosFisicos);
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
	 * Validar las capturas de las FA Válidas.
	 * 
	 * @author Octavio Hernandez
	 * @param N/A
	 * @return True or False
	 */

	public boolean opcionCapturarFAValida2() {
		try {
			au.click(tb_CapturaFuenteAbasto);
			au.sendKeys(tb_CapturaFuenteAbasto, FaEvo);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	public boolean opcionCapturarFAValidaEvo() {
		try {
			obtenerSkusRelacionadosFaValida(3);
			au.sendKeys(tb_CapturaFuenteAbasto, faValida);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("El campo no se encuentra disponible");
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Capturar Fuente de Abasto Válida y Validar su Captura.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */
	public boolean opcionCapturarFAValida2(int cantidadSKus, int inventario) {
		;
		try {
			obtenerSkusInventarioActual(cantidadSKus, inventario);
			au.sendKeys(tb_CapturaFuenteAbasto, faValida);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean validarCapturarFAValida2() {
		try {
			String camFA = au.driver.findElement(tb_CapturaFuenteAbasto).getText();
			if (camFA.isEmpty()) {
				testCase.addTextEvidenceCurrentStep("El campo para ingresar número de proveedor está vacío.");
				return false;
			} else {
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			}
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error al capturar la FA: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Capturar Fuente de Abasto Invalida y Validar su Captura.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean opcionCapturarFAInValidaPantallaCapturaCambiosFisicos() {
		try {
			obtenerFuenteAbastoInvalida();
			au.sendKeys(tb_CapturaFuenteAbasto, faInvalida);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("El campo no se encuentra disponible");
			return false;
		}
	}

	public boolean opcionCapturarFAInValidaPantallaCapturaCambiosFisicosB() {
		try {
			obtenerFuenteAbastoInvalida();
			au.sendKeys(tb_CapturaFuenteAbasto, FAInvalidNumber);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("El campo no se encuentra disponible");
			return false;
		}
	}

	public boolean validarCapturarFAInvalidaPantallaCapturaCambiosFisicos() {
		try {
			String camFA = au.driver.findElement(tb_CapturaFuenteAbasto).getText();
			if (camFA.isEmpty()) {
				testCase.addTextEvidenceCurrentStep("El campo para ingresar número de proveedor está vacío.");
				return false;
			} else {
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			}
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error al capturar la FA: " + e.getMessage());
			return false;
		}
	}

	public boolean opcionBuscarCFPP() {
		try {
			au.click(bt_BuscarProveedorRemision);
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
	 * Validación de Pantalla Cambios Físicos FA Buscada
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean pantallaCapturaCambiosFisicosFABuscada() {
		try {
			au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
			System.out.println("Fuente de abasto");
			boolean FuenteAbastos = au.driver.findElement(lb_FuenteAbasto).isEnabled();
			if (FuenteAbastos == false) {
				return false;

			}
			System.out.println("Proveedor");
			boolean Proveedor = au.driver.findElement(lb_Proveedor).isEnabled();
			if (Proveedor == false) {
				return false;
			}
			System.out.println("CapturaSKU");
			boolean TbCapturaSku = au.driver.findElement(tb_CapturaSku).isDisplayed();
			if (TbCapturaSku == false) {
				return false;
			}
			System.out.println("Boton capturaSKU");
			boolean CapturaSku = au.driver.findElement(bt_CapturaSku).isEnabled();
			if (CapturaSku == false) {
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
	 * Capturar Manualmente Sku´s y validar su captura.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean capturarSkus() {
		try {
			for (int i = 0; i < SkusEvo.size(); i++) {
				au.driver.findElement(tb_CapturaSku).sendKeys(SkusEvo.get(i));
				validarCapturarSku();
				btnIngresoSku();
			}
			;
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
	 * Capturar Manualmente Sku´s y validar su captura.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public void capturarSkus1x1(int posicion) {
		au.driver.findElement(tb_CapturaSku).sendKeys(SkusEvo.get(posicion));
		validarCapturarSku();
		btnIngresoSku();

	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validación de Pantalla Cambios Físicos FA Buscada
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean pantallaCapturaCambiosFisicosSkusCapturado1(int i) {
		String xpathPadre = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/rvwListaCambiosFisicos\"]/android.view.ViewGroup";

		au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		boolean CSku = au.driver.findElement(By.xpath(xpathPadre + "[" + (i + 1) + "]"
				+ "//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edtCambioFisicoSku\"]"))
				.isDisplayed();
		System.out.println(CSku);
		if (CSku == false) {
			return false;

		}
		boolean eliminarSku = au.driver.findElement(By.xpath(
				"(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnDeleteCambioFisico\"])"
						+ "[" + i + "]"))
				.isEnabled();
		if (eliminarSku == false) {
			return false;

		}
		boolean restarSku = au.driver.findElement(By.xpath(
				"(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnMinusCambioFisico\"])"
						+ "[" + i + "]"))
				.isEnabled();
		if (restarSku == false) {
			return false;

		}
		boolean sumarSku = au.driver.findElement(By.xpath(
				"(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnAddCambioFisico\"])"
						+ "[" + i + "]"))
				.isEnabled();
		if (sumarSku == false) {
			return false;

		}
		boolean cFisico = au.driver.findElement(By.xpath(
				"(//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edt_cfisico_realizado\"])"
						+ "[" + i + "]"))
				.isEnabled();
		if (cFisico == false) {
			return false;

		}

		String cantidadCFisicoRealizado = au.driver.findElement(By.xpath(
				"(//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edt_cfisico_realizado\"])"
						+ "[" + i + "]"))
				.getText();
		if (cantidadCFisicoRealizado.equalsIgnoreCase("0")) {
			testCase.addTextEvidenceCurrentStep(
					"La cantidad mostrada en el campo Cambios fisicos realizados es diferente de '1' " + i);

		}
		boolean cProgramado = au.driver.findElement(By.xpath(
				"(//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edt_cfisico_programado\"])"
						+ "[" + i + "]"))
				.isEnabled();
		if (cProgramado == false) {
			return false;
		}
		return true;
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validación de Pantalla Cambios Físicos FA Buscada
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public void pantallaCapturaCambiosFisicosSkusCapturado2(int i) {
		String xpathPadre = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/rvwListaCambiosFisicos\"]/android.view.ViewGroup";

		au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		boolean CSku = au.driver.findElement(By.xpath(xpathPadre + "[" + (i) + "]"
				+ "//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edtCambioFisicoSku\"]"))
				.isDisplayed();
		System.out.println(CSku);
		if (CSku == false) {
			testCase.addTextEvidenceCurrentStep("El botón de Captura de Códigos no esta habilitado");

		}
		boolean eliminarSku = au.driver.findElement(By.xpath(
				"(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnDeleteCambioFisico\"])"
						+ "[" + i + "]"))
				.isEnabled();
		if (eliminarSku == false) {
			testCase.addTextEvidenceCurrentStep("El botón de Captura de Códigos no esta habilitado");

		}
		boolean restarSku = au.driver.findElement(By.xpath(
				"(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnMinusCambioFisico\"])"
						+ "[" + i + "]"))
				.isEnabled();
		if (restarSku == false) {
			testCase.addTextEvidenceCurrentStep("El botón de Captura de Códigos no esta habilitado");

		}
		boolean sumarSku = au.driver.findElement(By.xpath(
				"(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnAddCambioFisico\"])"
						+ "[" + i + "]"))
				.isEnabled();
		if (sumarSku == false) {
			testCase.addTextEvidenceCurrentStep("El botón de Captura de Códigos no esta habilitado");

		}
		boolean cFisico = au.driver.findElement(By.xpath(
				"(//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edt_cfisico_realizado\"])"
						+ "[" + i + "]"))
				.isEnabled();
		if (cFisico == false) {
			testCase.addTextEvidenceCurrentStep("No se encontro el campo de Texto Cambio Fisico Realizado");

		}

		String cantidadCFisicoRealizado = au.driver.findElement(By.xpath(
				"(//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edt_cfisico_realizado\"])"
						+ "[" + i + "]"))
				.getText();
		if (cantidadCFisicoRealizado.equalsIgnoreCase("0")) {
			testCase.addTextEvidenceCurrentStep(
					"La cantidad mostrada en el campo Cambios fisicos realizados es diferente de '1' " + i);

		}
		boolean cProgramado = au.driver.findElement(By.xpath(
				"(//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edt_cfisico_programado\"])"
						+ "[" + i + "]"))
				.isEnabled();
		if (cProgramado == false) {
			testCase.addTextEvidenceCurrentStep("No se encontro el campo de Texto Cambio Fisico Programado");

		}
	}

	/********************************************************************************************************************************
	 * Método - swipeChido - permite el dezplamiento el pantalla
	 * 
	 * @param startX       - punto de partida en x
	 * @param ststartYartX - punto de partida en x
	 * @param endX         - punto final en y
	 * @param endY         - punto final en y
	 * @param durationMs   - duracion de la accion
	 * @return N/A
	 *
	 * @author Alex Aquino
	 */
	public void swipeChido(int startX, int startY, int endX, int endY, int durationMs) {
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

	public void swipeChido2() {
		au.driver.findElement(AppiumBy.androidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"Fuente de Abasto:\"))"));
	}

	public void swipeChido3() {
		au.scrollToElement(bt_Siguiente);
	}

	public void swipeChido4() {
		au.scrollToElement(CFPP_btnSiguiente);
	}

	public void Siguiente_CFPP() {
		au.click(CFPP_btnSiguiente);
	}

	/********************************************************************************************************************************
	 * Método - obtenerSkusNoRelacionadosFaValida - Obtiene skus no relacionados a
	 * una fuente de abasto valida
	 * 
	 * @param cantSkus - cantidad de skus a ingresar al pedido
	 * @return N/A
	 *
	 * @author Alex Aquino
	 */
	public boolean obtenerSkusNoRelacionadosFaValida(int cantSkus){
		try {
		Connection conn = DriverManager.getConnection(GlobalVariables.urlSatelitesPV10_17, GlobalVariables.userPV10_17,
				GlobalVariables.passwordPV10_17);
		String skuNoRelacionados = "select top " + cantSkus + " SKU from tgo.ItmsDtl where SUPPLIER_NUMBER != '"
				+ faValida + ".00' order by NEWID()";
		SQLResult res = new SQLResult(conn, skuNoRelacionados);
		for (int i = 0; i < cantSkus; i++) {
			val = res.getRow(i).toString();
			val = (val.replace("]", "")).replace("[", "");
			listaSkus.clear();
			listaSkus.add(val);
		}
		testCase.addTextEvidenceCurrentStep("Se muestran los SKUs NO Relacionados y FA valida usada en el pedido ");
		testCase.addQueryEvidenceCurrentStep(res);
		return true;
		}catch(Exception e){
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar Botón Confirmar Warning
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean btnConfirmar() {
		try {
			au.click(bt_ConfirmarWarning);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("El botón no se encuentra disponible");
			return false;
		}
	}

	/**
	 * Intenta hacer clic en el botón definido por bt_SumarSku un número
	 * especificado de veces y captura una imagen del estado actual de la prueba.
	 * 
	 * @author Octavio Hernandez
	 * @param N/A
	 * @return True or False
	 */
	public boolean sumarSkuMas(int veces) {
		for (int i = 0; i < veces; i++) {
			try {
				au.click(bt_SumarSku); // Intenta hacer clic en el botón
				testCase.addScreenShotCurrentStep(au, "testImage_" + i); // Captura una imagen del estado actual
			} catch (Exception e) {
				// Captura cualquier otra excepción inesperada
				testCase.addTextEvidenceCurrentStep(
						"Ocurrió un error inesperado en el intento " + (i + 1) + ": " + e.getMessage());
				return false; // Retorna falso si ocurre un error inesperado
			}
		}
		return true; // Retorna verdadero si todos los clics fueron exitosos
	}

	public boolean sumarSkuMasVariosSkus(int veces) {
		for (int i = 0; i < veces; i++) {
			try {
				au.click(By.xpath(
						"(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnAddCambioFisico\"])"
								+ "[" + (i + 1) + "]"));
				au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(4000));
				testCase.addScreenShotCurrentStep(au, "testImage_" + i); // Captura una imagen del estado actual
			} catch (Exception e) {
				// Captura cualquier otra excepción inesperada
				testCase.addTextEvidenceCurrentStep(
						"Ocurrió un error inesperado en el intento " + (i + 1) + ": " + e.getMessage());
				return false; // Retorna falso si ocurre un error inesperado
			}
		}
		return true; // Retorna verdadero si todos los clics fueron exitosos
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar Botón Confirmar Warning
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean SumarSku() {
		try {
			au.click(bt_SumarSku);
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
	 * Validación de campo C. fisico Realizado.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean ValidarcampoCFRealizado() {
		try {

			boolean Regresar = au.driver.findElement(tb_CFRealizado).isEnabled();
			if (Regresar == false) {
				testCase.addTextEvidenceCurrentStep("El botón para Regresar no esta habilitado");
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep(
					"La pantalla de Consulta Cambios Físicos no se muestra de manera correcta.");
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Seleccionar Boton C.fifico programado.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean CFProgramado() {
		try {
			au.sendKeys(tb_CFProgramado, "2");
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
	 * Validación de campo C. fisico Programado.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean ValidarcampoCFProgramado() {
		try {

			boolean Regresar = au.driver.findElement(tb_CFProgramado).isEnabled();
			if (Regresar == false) {
				testCase.addTextEvidenceCurrentStep("El botón para Regresar no esta habilitado");
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep(
					"La pantalla de Consulta Cambios Físicos no se muestra de manera correcta.");
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************/
	/**
	 * Validación de actualizacion del campo C. fisico Programados.
	 * 
	 * @author Alejandro Valdez
	 * @param N/A
	 * @return True or False
	 */

	public boolean ValidarActualizacioncampoCFProgramado() {
		try {
			// Obtener el valor actual del campo
			String valorActual = au.driver.findElement(tb_CFProgramado).getText();
			System.out.println(valorActual);
			// Validar si el valor actual es diferente de 0
			if (!valorActual.equals("0")) {
				testCase.addTextEvidenceCurrentStep(
						"El campo CF Programado ha sido actualizado correctamente con un valor diferente de 0: "
								+ valorActual);
			} else {
				testCase.addTextEvidenceCurrentStep(
						"El campo CF Programado no se actualizó correctamente. El valor sigue siendo 0.");
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true; // Si todo se ejecuta sin problemas y el valor es diferente de 0, retorna
							// verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep(
					"La pantalla de Consulta Cambios Físicos no se muestra de manera correcta.");
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * obtenerSkusInventarioActual - Obtener Sku´s relacionados de la Fuente de
	 * Abasto que tengan un inventario mayor a X
	 * 
	 * @author Marco Aquino
	 * 
	 * @param cantSkus - Cantidad de Sku´s a obtener para el caso.
	 * 
	 * @param inventario - cantidad minima que debe tener cada SKU obtendio
	 * 
	 * @return True or False
	 */

	public boolean obtenerSkusInventarioActual2(int cantidadSKus, int inventario) {
		try {
			Connection conn = DriverManager.getConnection(GlobalVariables.urlSatelitesPV10_17,
					GlobalVariables.userPV10_17, GlobalVariables.passwordPV10_17);
			String query1 = "DROP TABLE IF EXISTS  #ReturnTable";
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(query1);
			String query2 = "CREATE TABLE #ReturnTable ( " + "	[SKU] [varchar](60) NOT NULL, "
					+ "	[Nombre] [nvarchar](100) NOT NULL, " + "	[PrecioVenta] [decimal](16, 4) NOT NULL, "
					+ "	[PrecioCosto] [decimal](16, 4) NOT NULL, "
					+ "	[InventarioOperativo] [decimal](16, 2) NOT NULL, "
					+ "	[InventarioTeorico] [decimal](16, 2) NOT NULL, " + "	[MA_EXH] [numeric](16, 4) NULL, "
					+ "	[Ma_Exh_Prm] [numeric](16, 4) NULL, " + "	[MA_EXH_INI] [date] NULL, "
					+ "	[MA_EXH_END] [date] NULL, " + "	[MA_STK_NEW] [numeric](16, 4) NULL, "
					+ "	[MA_NEW_INI] [date] NULL, " + "	[MA_NEW_END] [date] NULL, " + "	[MA_FS] [numeric](16, 4) NULL, "
					+ "	[MA_FS_PROM] [numeric](16, 4) NULL, " + "	[MA_FS_INI] [date] NULL, "
					+ "	[MA_FS_END] [date] NULL, " + "	[MA_IM] [numeric](16, 4) NULL, "
					+ "	[HFP] [varchar](5) NULL, " + "	[SUMA_HCU] [decimal](38, 2) NULL, " + "	[DIAS_HCU] [int] NULL, "
					+ "	[SUMA_HCI] [decimal](38, 2) NULL, " + "	[DIAS_HCI] [int] NULL, "
					+ "	[DESVEST] [float] NULL, " + "	[MA_VTA_PRD] [numeric](16, 4) NOT NULL, "
					+ "	[MA_DECIMAL] [bit] NOT NULL, " + "	[nombre_proveedor] [varchar](100) NULL, "
					+ "	[maLinSur] [int] NULL " + ")";
			stmt.executeUpdate(query2);
			String query3 = "INSERT INTO #ReturnTable EXEC Satelites.hc.Get_ItemsBySupplier_MOB " + faValida;
			stmt.executeUpdate(query3);
			String skuRelacionados = "Select SKU from #ReturnTable";
			boolean encontrados = false;
			SQLResult res;
			while (encontrados != true) {
				listaSkus.clear();
				res = new SQLResult(conn, skuRelacionados);
				if (res.getRowCount() > cantidadSKus) {
					System.out.println(faValida);
					String valor = "";
					int index = 0;
					for (int i = 0; listaSkus.size() < cantidadSKus; i++) {
						val = res.getRow(i).toString();
						val = (val.replace("]", "")).replace("[", "");
						valor = obtenerInventarioActual(val);
						if (valor == "" || Integer.parseInt(valor) < inventario) {
							if (i >= res.getRowCount() - 1) {
								obtenerFuenteAbastoValida();
								obtenerSkusInventarioActual(cantidadSKus, inventario);
								encontrados = true;
							}
							continue;
						} else {
							System.out.println(index + ": " + val + "(" + Integer.parseInt(valor) + ")");
							listaSkus.add(index, val);
							index++;
						}
					}
					testCase.addTextEvidenceCurrentStep(
							"Se muestran los SKUs Relacionados y FA valida usada en el pedido ");
					testCase.addQueryEvidenceCurrentStep(res);
					encontrados = true;
				} else {
					obtenerFuenteAbastoValida();
					obtenerSkusInventarioActual(cantidadSKus, inventario);
					encontrados = true;
				}
			}
			return true;
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error al obtener los SKUs Relacionados a la Fuente de Abasto");
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * AgregarArt_EditCFisicoProgramado - ingresa en el campo C. Fisicos Pendientes
	 * la cantidad deseada
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public void Mofid_EditCFProgramado(int cantArt, int cantidad) {
		for (int i = 1; i <= cantArt; i++) {
			String path = "(//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edt_cfisico_programado\"])["
					+ i + "]";
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
			WebElement valor = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(path)));
			valor.click();
			valor.clear();
			au.driver.hideKeyboard();
			valor.sendKeys(String.valueOf(cantidad));
		}
	}

	public boolean seleccionarCampoObservacionesProveedorSwipeSEO() {
		try {
			au.scrollToElement(campoObservacionesProveedor);
			au.click(campoObservacionesProveedor);
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	public boolean seleccionarSiguienteSE3() {
		try {

			au.scrollToElement(By.xpath(
					"//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btn_siguiente\"]"));
			au.click(By.xpath(
					"//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btn_siguiente\"]"));
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			return false;
		}
	}

	public boolean CambiosFisicosPrerequisito(int CantSkus, int CantInv) {
		try {

			ObtenerFAValidaNubeEvo(CantSkus, CantInv);
			System.out.println("Se obtiene la FA");
			for (int i = 0; i < 2; i++) {
				opcionCambiosFisicosSinEviencia();
				au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2500));
				opcionCapturarCambiosFisicosSE();
				opcionCapturarFAValidaSiEvidencia();
				System.out.println("Se captura la FA");

				opcionBuscarSEB();
				System.out.println("Se da click en boton buscar FA");

				SkusEvo.clear();
				obtenerSkusValidosxFANubeEvob(CantSkus, CantInv);
				System.out.println("Se obtienen Skus validos");

				capturarSkusSinEvidencia();
				System.out.println("Se capturan los skus");

				capturarCFProgramado();
				System.out.println("Se capturan los CF");

				seleccionarSiguienteSE();
				System.out.println("Se selecciona el boton siguiente");

				seleccionarCampoObservacionesProveedorSwipeSEO();
				System.out.println("Se selecciona campo observaciones");

				seleccionarOpcionSinComentariosSE();
				System.out.println("se selecciona la opcion sin comentarios");

				seleccionarCampoFirmaProveedoreIngresarFirmaSE();
				System.out.println("se ingresa la firma proveedor");

				seleccionarCampoObservacionesPersonalTiendaSE();
				System.out.println("Se selecciona campo observaciones");

				seleccionarOpcionSinComentariosSE();
				System.out.println("se selecciona la opcion sin comentarios");

				au.scrollToElement(By.xpath(
						"//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btn_siguiente\"]"));
				seleccionarCampoFirmaPersonalTiendaIngresarFirmaSE();
				System.out.println("se ingresa la firma personal de tienda");

				seleccionarSiguienteSE3();
				System.out.println("se selecciona el boton siguiente");

				opcionSiGeneralSE();
				System.out.println("Se selecciona SI");

				opcionSiGeneralSE();
				System.out.println("Se selecciona SI");

				au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
				opcionFinalizarSE();
				System.out.println("se selecciona finalizar");
			}
			opcionCambiosFisicosSinEviencia();
			return true;
		} catch (Exception e) {
			return false;

		}
	}

	public boolean CuatroCambiosFisicos(int CantSkus, int CantInv) {
		try {

			ObtenerFAValidaNubeEvo(CantSkus, CantInv);
			System.out.println("Se obtiene la FA");
			for (int i = 0; i < 4; i++) {
				opcionCambiosFisicosSinEviencia();
				au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
				opcionCapturarCambiosFisicosSE();
				opcionCapturarFAValidaSiEvidencia();
				System.out.println("Se captura la FA");

				opcionBuscarSEB();
				System.out.println("Se da click en boton buscar FA");

				SkusEvo.clear();
				obtenerSkusValidosxFANubeEvo(CantSkus, CantInv);
				System.out.println("Se obtienen Skus validos");

				capturarSkusSinEvidencia();
				System.out.println("Se capturan los skus");

				capturarCFProgramado();
				System.out.println("Se capturan los CF");

				seleccionarSiguienteSE();
				System.out.println("Se selecciona el boton siguiente");

				seleccionarCampoObservacionesProveedorSwipeSEO();
				System.out.println("Se selecciona campo observaciones");

				seleccionarOpcionSinComentariosSE();
				System.out.println("se selecciona la opcion sin comentarios");

				seleccionarCampoFirmaProveedoreIngresarFirmaSE();
				System.out.println("se ingresa la firma proveedor");

				seleccionarCampoObservacionesPersonalTiendaSE();
				System.out.println("Se selecciona campo observaciones");

				seleccionarOpcionSinComentariosSE();
				System.out.println("se selecciona la opcion sin comentarios");

				au.scrollToElement(By.xpath(
						"//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btn_siguiente\"]"));
				seleccionarCampoFirmaPersonalTiendaIngresarFirmaSE();
				System.out.println("se ingresa la firma personal de tienda");

				seleccionarSiguienteSE3();
				System.out.println("se selecciona el boton siguiente");

				opcionSiGeneralSE();
				System.out.println("Se selecciona SI");

				opcionSiGeneralSE();
				System.out.println("Se selecciona SI");

				au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
				opcionFinalizarSE();
				System.out.println("se selecciona finalizar");
			}
			opcionCambiosFisicosSinEviencia();
			return true;
		} catch (Exception e) {
			return false;

		}
	}

	public void incrementarBtnMasCCF(int numArticulos, int numClicks) { // numArticulos = 1-2, numClicks = X
		for (int i = 1; i <= numArticulos && i < 3; i++) {
			for (int j = 1; j <= numClicks; j++) {
				WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(10));
				WebElement incrementar = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
						"(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btn_sumar\"])"
								+ "[" + i + "]")));
				incrementar.click();
			}
		}
		testCase.addTextEvidenceCurrentStep("Se hace click en boton aumentar '+'");
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	public boolean IngresarNumeroRemision() {
		try {
			au.sendKeys(tb_CapFAoNumRemision, RemisionNumber);
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("El botón no se encuentra disponible");
			return false;
		}
	}

	public boolean IngresarFA() {
		try {
			au.sendKeys(tb_CapFAoNumRemision, FaEvo);
			return true; // Si todo se ejecuta sin problemas, retorna verdadero

		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, retorna falso
			testCase.addTextEvidenceCurrentStep("El botón no se encuentra disponible");
			return false;
		}
	}

	public boolean validarCapturaFAoNumeroRemision() {
		try {
			String camFA = au.driver.findElement(tb_CapFAoNumRemision).getText();
			if (camFA.isEmpty()) {
				return false;
			} else {
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			}
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error al capturar el Numero de Remision: " + e.getMessage());
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * llenaYValidaComentariosYFirmas_AE -
	 * 
	 * @author Alex Aquino
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void llenaYValidaComentariosYFirmas_AE2() {
		au.scrollToElement(AEObservacionesProveedor);
		au.click(AEObservacionesProveedor);
		au.click(AEOpcionSinComentarios);
		// ---
		validaObservacionesProveedorCapturada("sin");
		// ---
		au.scrollToElement(lbObsProv);
		screenShotFirma("antes");
		au.click(AEfirmaProveedor);
		au.click(AEfirma);
		au.click(AEbtnContinuar);
		screenShotFirma("despues");
		// ---
		validaCampoFirmaCapturado("antes", "despues");
		// ---
		au.scrollToElement(AEObservacionesTienda);
		au.click(AEObservacionesTienda);
		au.click(AEOpcionSinComentarios);
		// ---
		validaObservacionesTiendaCapturada("sin");
		//
		au.scrollToElement(AEBtnSiguiente);
		screenShotFirma("antes");
		au.click(AECFPPfirmaTienda);
		au.click(AEfirma);
		au.click(AEbtnContinuar);
		screenShotFirma("despues");
		// ---
		validaCampoFirmaCapturado("antes", "despues");
		// ---
		au.scrollToElement(AEBtnSiguiente);
		au.click(AERdBtnCumpProcCambFis);
	}

	/// mike2
	public boolean validacionOrdenRecibida() {
		try {
			// String dummy = "1107318";
			String Folio = au.driver.findElement(campoFolioPedido).getText();
			System.out.println("fo: " + Folio);
			if (Folio.contains(NoPedido) == false) {
				return false;
			}

			boolean proveedor = au.driver.findElement(campoProveedor).isDisplayed();
			if (proveedor == false) {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			swipeCoord(600, 1000, 100, 1000, 1000);

			boolean fecha = au.driver.findElement(campoFechaPedido).isDisplayed();
			if (fecha == false) {
				return false;
			}

			boolean cantidad = au.driver.findElement(campoCantidadPedido).isDisplayed();
			if (cantidad == false) {
				return false;
			}

			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * ***************************funciones de ordenes de
	 * compra**********************+
	 */

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida que se visualize los botones correspondientes de la pantalla
	 * proveedores directos
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean pantallaProveedoresDirectos() {
		try {
			swipeCoord(300, 950, 300, 350, 1000);

			boolean BusquedaPedidoPrecargado = au.driver.findElement(bt_BusquedaPedidoPrecargado).isDisplayed();
			if (BusquedaPedidoPrecargado == false) {
				return false;
			}
			boolean LeerQRPreventaOPredido = au.driver.findElement(bt_LeerQRPreventaOPredido).isDisplayed();
			if (LeerQRPreventaOPredido == false) {
				return false;
			}
			boolean GeneracionHeramientaCompra = au.driver.findElement(bt_GeneracionHeramientaCompra).isDisplayed();
			if (GeneracionHeramientaCompra == false) {
				return false;
			}
			boolean OrdenesCompraRecibidas = au.driver.findElement(bt_OrdenesCompraRecibidas).isDisplayed();
			if (OrdenesCompraRecibidas == false) {
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
	 * valida que el boton ordenes de compras recibidas este habilitado
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean validarOpcionOrdenesCompraRecibidas() {
		try {
			au.scrollToElement(bt_OrdenesCompraRecibidas);
			boolean habilitado = au.driver.findElement(bt_OrdenesCompraRecibidas).isEnabled();
			if (habilitado == false) {
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
	 * seleciona la opcion Ordenes de Compra Recibidas
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean opcionOrdenesCompraRecibidas() {
		try {
			au.scrollToElement(bt_OrdenesCompraRecibidas);
			au.click(bt_OrdenesCompraRecibidas);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida que se visualize los elementos correspondientes de la pantalla Ordenes
	 * de Compra Recibidas
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean pantallaOrdenesCompraRecibidas() {
		try {
			String titulo = au.driver.findElement(titulo_OrdenesCompraRecibidas).getText();
			if (titulo.equals("ÓRDENES DE COMPRA RECIBIDAS") == false) {
				System.out.println("1.1");
				return false;
			}

			boolean tutorirales = au.driver.findElement(bt_Tutoriales).isEnabled();
			if (tutorirales == false) {
				return false;
			}

			String busquedaPor = au.driver.findElement(lb_busquedaPor).getText();
			if (busquedaPor.equals("BÚSQUEDA POR:") == false) {
				return false;
			}

			String numeroPedido = au.driver.findElement(rb_NumeroPedido).getText();
			if (numeroPedido.equals("Número de Pedido") == false) {
				return false;
			}

			String Proveedor = au.driver.findElement(rb_Proveedor).getText();
			if (Proveedor.equals("Proveedor") == false) {
				return false;
			}

			String PedidoProveedor = au.driver.findElement(lb_NumeroPedidoProveedor).getText();
			if (PedidoProveedor.equals("Número de Pedido / Proveedor:") == false) {
				return false;
			}

			boolean campoBusqueda = au.driver.findElement(campoBusquedaOrdenesRecivibas).isDisplayed();
			if (campoBusqueda == false) {
				return false;
			}

			boolean buscar = au.driver.findElement(bt_busqueda).isEnabled();
			if (buscar == false) {
				return false;
			}

			String pedidos = au.driver.findElement(lb_TextoPedidos).getText();
			if (pedidos.equals("PEDIDOS") == false) {
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
	 * seleciona la opcion tutoriales
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean opcionTutoriales() {
		try {
			au.click(bt_Tutoriales);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida informacion del mensaje informativo de la pantalla Ordenes de Compra
	 * Recibidas
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean mensajeOrdenesCompraRecibidas() {
		try {
			String mensaje = au.driver.findElement(Warnings).getText();
			String contenido = "En este módulo podrás visualizar las órdenes de compra recibidas de cada proveedor.\n\n"
					+ "Las podrás consultar por número de proveedor o por número de orden de compra.";
			if (mensaje.contains(contenido) == false) {
				return false;
			}

			String aceptar = au.driver.findElement(bt_aceptar).getText();
			if (aceptar.contains("ACEPTAR") == false) {
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
	 * seleciona el boton buscar de la pantalla Ordenes de Compra Recibidas
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean opcionBuscarOrdenesCompra() {
		try {
			au.click(bt_busqueda);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida informacion del warning al hacer una busqueda sin selecion en la
	 * pantalla Ordenes de Compra Recibidas
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean mensajeOrdenesRecibidasSinSelecion() {
		try {
			String mensaje = au.driver.findElement(Warnings).getText();
			String contenido = "Para buscar, primero selecciona un tipo de busqueda.";

			if (mensaje.equals(contenido) == false) {
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
	 * seleciona la opcion Numero de pedido de la pantalla Ordenes de Compra
	 * Recibidas
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean opcionNumeroPedido() {
		try {
			au.click(rb_NumeroPedido);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida que se encuentre selecionada la opcion Numero de pedido de la pantalla
	 * Ordenes de Compra Recibidas
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean validarcheckNumeroPedido() {
		try {
			String validacion = au.driver.findElement(rb_NumeroPedido).getAttribute("checked");
			if (!validacion.equals("true")) {
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
	 * valida informacion del warning al hacer una busqueda sin capturar numero de
	 * pedido en la pantalla Ordenes de Compra Recibidas
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean mensajeOrdenesRecibidasSinCapturarNumero() {
		try {
			String mensaje = au.driver.findElement(Warnings).getText();
			String contenido = "Por favor captura un número de pedido para continuar.";

			if (mensaje.equals(contenido) == false) {
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
	 * ingresa orden de compra incorrecto
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ingresarOrdenCompraIncorrecta() {
		try {
			au.click(campoBusquedaOrdenesRecivibas);
			au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
			for (int i = 0; i < 9; i++) {
				au.click(By.id("com.google.android.inputmethod.latin:id/key_pos_number_line3_3"));
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.driver.hideKeyboard();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida informacion del warning al hacer una busqueda con numero de pedido
	 * Inexistente en la pantalla Ordenes de Compra Recibidas
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean mensajeOrdenesRecibidasNoSeEncuentra() {
		try {
			String mensaje = au.driver.findElement(Warnings).getText();
			String contenido = "El Número de Pedido capturado no se encuentra.";

			if (mensaje.equals(contenido) == false) {
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
	 * seleciona la opcion Proveedor de la pantalla Ordenes de Compra Recibidas
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean opcionProveedor() {
		try {
			au.click(rb_Proveedor);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida que se encuentre selecionada la opcion Proveedor de la pantalla
	 * Ordenes de Compra Recibidas
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean validarcheckProveedor() {
		try {
			String validacion = au.driver.findElement(rb_Proveedor).getAttribute("checked");
			if (!validacion.equals("true")) {
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
	 * valida informacion del warning al hacer una busqueda sin capturar numero de
	 * proveedor en la pantalla Ordenes de Compra Recibidas
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean mensajeOrdenesRecibidasSinProveedor() {
		try {
			String mensaje = au.driver.findElement(Warnings).getText();
			String contenido = "Por favor captura un número de proveedor para continuar.";

			if (mensaje.equals(contenido) == false) {
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
	 * valida informacion del warning al hacer una busqueda con numero de proveedor
	 * incorrecto en la pantalla Ordenes de Compra Recibidas
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean mensajeOrdenesRecibidasProveedorNoSeEncuentra() {
		try {
			String mensaje = au.driver.findElement(Warnings).getText();
			String contenido = "El Proveedor capturado no se encuentra.";

			if (mensaje.equals(contenido) == false) {
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
	 * obtine un numero del pedido de la bd local
	 *
	 * @autor Miguel Bautista
	 *
	 * @return retorna un numero de pedido
	 *
	 * @date 10/octubre/2024
	 */
	public String obtenerNumeroPedido(String ip) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlserver://"+ip+":1433;databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userDB10_212, GlobalVariables.passDB10_212);
			String query = "SELECT [HC_PO] FROM [Satelites].[hc].[hc_purchaseorder] "
					+ "WHERE [Hc_PO_Additional] <> 0 ORDER BY [HC_PO_Date] DESC";
			SQLResult res = new SQLResult(conn, query);
			NoPedido = String.valueOf(Integer.parseInt(res.getFirstData().toString()));
			System.out.println("No. Pedido: " + NoPedido);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NoPedido;
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * obtine un numero del pedido de la bd local
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ingresarNoPedido() {
		try {
			au.sendKeys(campoBusquedaOrdenesRecivibas, NoPedido);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * obtine un numero del pedido de la bd local
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ingresarNoProveedor() {
		try {
			au.sendKeys(campoBusquedaOrdenesRecivibas, NoProveedor);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private By lb_mensajePasosQR = By.xpath(
			"/hierarchy/android.widget.FrameLayout[3]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TextView");
	/// hierarchy/android.widget.FrameLayout[3]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TextView
	private By bt_pasosAbrirQR = By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/btn_text");

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona el boton [Ver Pasos para Generar QR]
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean opcionVerPasosGenerarQR() {
		try {
			au.click(bt_pasosAbrirQR);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * ingresa QR de prueba para visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @param QR de prueba
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean pegarQRp() {
		try {
			au.driver.hideKeyboard();
			QR = "{\"c\":\"H4sIAAAAAAAAAJWXW2+jOBzFv4rFc2fkK5e8ucQh3gKmBnrJzmhVtVlttKO2SpOVRqP57vsngVwgpCFvAfdXn+PjY+eX87F+f//xM39bL5/nzsghAcfOlfO2fJkvi5/v8IheOc/Lv1aL+evLUzUAR+VY4Ed1A8NWMOJ2CU//gS/vy7f/5vOXt+p7rqNEIhnrRKWFyVFoEmVDLWOk0wLGfvy7jhcfK2f05y/nA8Z7AhPMMQ584RN4/wrPMvn4aLJY6gLdlio3qPnA+7Uz4lfOwhnhrxj+M4x2fl8do5ig1KtR16W1ujAqDuXMZDqUxyi8QQnay/IJ9xrWbW4kiNHhnY5jXUjiiajDYr0oylzWoCILCLT1yuU4aikk/SiGhWh8qk26kzoFnCQYR/aAQj/xiWDfYzUqnBqrZwbFKjEpyhAbZjmggkZbLmOZaAQGxVqmZijKo0TwZvV0oXJUAxlG0UAUJgI3AuVEIRMrFOtoWiDqkyTuLB7pXz0PC987ZIUmHl9bdV+hToWK8F5WAGHwd2sYTUtboCbwBMXFAImA8vEetcNMrMolIjdDUIRQ3jMrxnA36u4ZFMNtgZm0El2ra2XR44CQBpS4wR51OCnK6VHet7M6Yzv1dksIjLHOJ7Ys5MYqDqwOqr8WAur77n5WsJ1VZEDc1vZs1q2YfhQjTOxnFVmlI5MYK3O+03epV5yQve1ZKovSyjiysAvjSuGAsgo43YUh1mEpxyaRaaYs93BrF9YCvTMsgPX4zrA3ZEcHghygat9BJ7xvd6i3QX05I1Ew3s5753Mk0e9necI7iFYVz03Ut9nycId1EsWgSLlgZJfSptlDmZVh1TpVgXWK5uQ+bFgB35VWxdCpqTlVA16WrRpFcVM0ibk5mtLlqI1ZnAu/iWmmZzM5Kcc6HU/lvWwbvz0IyRmUy4MWimYyvMlUlqkTa9jfWVwQTrqzKkz4bQ197cIpFA7ZjMATJ1TCxLa8P2SChvGo8HqkHhM/vUPU5ywNGr3WVBcaA0RgmPS2PBX/k9a5hHkNZUzH9X0NQChP5QPqVg7pV4gDKPv9JSKc6nAKZSrvDOInrzbnrm0EN6jtlDKZqliijWODfPep30IZ+QDxj+8QpU3rXFb1PnNJs4RWXaOjmVWeXX4A+WC7e0pglQp8dFizT4PFhWgFCxX35sCpS+sZ7kis8WqTJHSnbQQdBsYHQ73yfX4kEM6fcAp1jyrrJh1U0EVxTIgrSHXbagQWMi8rEmCo/5U15+ulAsEr3mzq+qcFuLVxLWvdAnsj6lJKCRYugfNn/8sitCqR2VTHcqxQawU/28rQguRE1STqQbcLtSPwOzx4XawWq6cf46fV3FQ//+DFZLlA5nmFqECEj4g/EgRFSfEFuyOMYX6by8Hfi9fBf/X7f2Ih6pF5DgAA\",\"i\":\"9b5d19cd-6601-4270-b72a-f5d78da16b14\",\"o\":0,\"n\":1,\"N\":1}";
			System.out.println("se pega qr " + QR);
			au.sendKeys(compoCapturarQRp, QR);
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.click(bt_CapturarQRp);
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * copia QR mostrado en visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean copiarQR() {
		try {
			QR = au.driver.findElement(cadenaQR).getText();
			System.out.println("se copia qr  " + QR);
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona boton [Leer QR] y seleciona NO del Warning
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean opcionleerQR() {
		try {
			testCase.addTextEvidenceCurrentStep("Se selecciona opcion Leer QR para Preventa o Pedido");
			au.click(bt_LeerQRPreventaOPredido);
			testCase.addScreenShotCurrentStep(au, "testImage");
			testCase.addTextEvidenceCurrentStep("Se selecciona: NO");
			au.click(bt_No);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * pega qr copiado de visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean pegarQR() {
		try {
			System.out.println("se pega qr " + QR);
			au.sendKeys(compoCapturarQR, QR);
			au.click(bt_CapturarQR);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona aceptar de visita guiada sin evidencia
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void aceptar() {
		au.click(bt_Si);
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida informacion de mensaje con los pasos para lectura de qr
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarMensajeEscanearQRpedidoT() {
		try {
			String texto = au.driver.findElement(lb_mensajePasosQR).getText();
			System.out.println(texto);
			String contenido = "Selecciona la opción \"Escanear QR de pedido o QR de Hta. de compra\" para iniciar el proceso de recepción de mercancía.\n\nSolicita al equipo de tienda que te proporcione el código QR de la herramienta de compra correspondiente a tu fuente de abasto.";
			System.out.println(contenido);
			if (texto.equalsIgnoreCase(contenido) == false) {
				return false;
			}

			boolean boton = au.driver.findElement(bt_pasosAbrirQR).isEnabled();
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
	 * valida informacion de mensaje con los pasos para lectura de qr
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarMensajePasosLecturaQR() {
		try {
			au.scrollToElement(lb_lecturaQRpaso5);
			Object[] pasos = { lb_mensajePasosAbrirQR, lb_lecturaQRpaso1, lb_lecturaQRpaso2, lb_lecturaQRpaso3,
					lb_lecturaQRpaso4, lb_lecturaQRpaso5 };
			String[] TextoPasos = { "Pasos para lectura de QR en Hand Held", "Excelencia Operativa >",
					"Herramienta de Compra >", "Herramienta de Compra COMETA >",
					"Verificación de Mercancía Pedidos Proveedor Directo >", "Leer QR para Preventa o Pedido" };

			for (int i = 0; i < pasos.length; i++) {
				String texto = au.driver.findElement((By) pasos[i]).getText();
				if (texto.contains(TextoPasos[i]) == false) {
					return false;
				} else {
					System.out.println("Correcto : " + texto + "\t:\t" + TextoPasos[i]);
				}
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
	 * valida informacion de mensaje con los pasos para lectura de qr
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarMensajeQRPrueba() {
		try {

			String texto = au.driver.findElement(Warnings).getText();
			String contenido = "Este código QR contiene información de prueba, por lo cual no se estaría generando una Orden de Compra";
			if (texto.contains(contenido) == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Se selecciona Aceptar");
			au.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
			aceptar();
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida informacion de mensaje de visita guiada mostrado el escanear qr
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarMensajeDespuesdeQR() {
		try {

			String texto = au.driver.findElement(lb_mensajeDespuesdeQR).getText();
			String contenido = "Una vez escaneado el QR con la Hand Held, nos solicitará capturar el número de remisión, este mismo te lo proporciona el proveedor.";
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
	 * ingresa numero de prueba en el campo de numero remision
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @param se ingresa numero aleatorio
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ingresarNoRemision(String noRemision) {
		try {
			au.sendKeys(compoNoRemision, noRemision);
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.click(compoNoRemision);
			au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
			if (au.driver.findElement(By.id("com.google.android.inputmethod.latin:id/key_pos_ime_action"))
					.isDisplayed()) {
				au.click(By.id("com.google.android.inputmethod.latin:id/key_pos_ime_action"));
				au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensaje de visita guiada mostrado enel campo numero de remision
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @param se ingresa numero aleatorio
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarMensajeNoRemision() {
		try {

			String texto = au.driver.findElement(lb_mensajeNoRemision).getText();
			String contenido = "Una vez capturado el número de remisión, estaremos realizando la verificación de mercancía del pedido generado.";
			if (texto.contains(contenido) == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.click(compoNoRemision);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida informacion de mensaje de visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarVisualizacionArticulo() {
		try {
			String texto = au.driver.findElement(lb_mensajeVisualizacionArticulo).getText();
			String contenido = "Se visualiza los artículos a verificar. En caso de haber incidencias, se clasifica en dañado, caduco o faltante.\n\nEjemplo: Resta 1 pieza y en automático se clasificará como faltante.\n\nIMPORTANTE: Si en dado caso se detectan incidencias durante la verificación, el sistema te solicitará verificar más artículos del pedido original";
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
	 * seleciona el boton restar en visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */

	private By bt_restar = By.xpath(
			"(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btn_restar\"])[1]");

	public boolean opcionRestar() {
		try {
			au.click(bt_restar);
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.click(bt_restar);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensaje de visita guiada al capturar visita
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarMensajeDespuesMercancia() {
		try {
			au.driver.hideKeyboard();
			String texto = au.driver.findElement(lb_mensajeDespuesMercancia).getText();
			String contenido = "Después de haber verificado la mercancía, captura la cifra de proveedor. Esta cifra te la comparte directamente el proveedor.";
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
	 * valida mensaje de visita guiada al capturar visita
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public void opcionTerminar() {
		au.click(bt_opcionTerminar);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensaje de visita guiada al capturar visita
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarMensajeResumenPedido() {
		try {
			String texto = au.driver.findElement(lb_mensajeMuestraResumen).getText();
			String contenido = "Se muestra el resumen del pedido de los artículos.";
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
	 * selecionar tabla pasar a la siguiente ventana de visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void clicTablaSinEvidencia() {
		au.click(By.id("com.femco.oxxo.operaciones.mitienda.debug:id/lista_articulos_aux_view"));
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * selecionar valida mensaje de visita guiada proveedor directo
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarMensajeSelecionarProveedor() {
		try {

			String texto = au.driver.findElement(lb_mensajeSelecionarProveedor).getText();
			String contenido = "PROVEEDOR\nEl siguiente paso es seleccionar una opción en la sección \"Observaciones Proveedor\".\n\nEjemplo: Selecciona \"Otros\" para un comentario de captura libre.";
			if (texto.contains(contenido) == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
//			au.click(lb_mensajeSelecionarProveedor);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * selecionar campo observaciones proveedor
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void CampoObservacionesProveedor() {
		au.click(campoObservacionesProveedor);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona la opcion Otros del campo observaciones
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void LlenarObservacionesOtros() {
		au.click(lb_Otros);
		testCase.addScreenShotCurrentStep(au, "testImage");
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
	public boolean ValidarMensajeComentarioProveedor() {
		try {
			String texto = au.driver.findElement(lb_mensajeComentarioProveedor).getText();
			String contenido = "PROVEEDOR\nEscribe un comentario a modo de observaciones respecto al acuerdo de entrega de la OC.";
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
	 * ingresa comentario en el campo observaciones para visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @param comentario para visita guiada
	 * 
	 * @date 10/octubre/2024
	 */
	public void ingresarComentarioProveedor(String cProveedor) {
		au.sendKeys(campoObservacionesProveedor, cProveedor);
		testCase.addScreenShotCurrentStep(au, "testImage");
		au.click(campoObservacionesProveedor);
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensaje de visita guiada en el campo de firma proveedor
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarMensajeFirmaProveedor() {
		try {
			String texto = au.driver.findElement(lb_mensajeFirmaProveedor).getText();
			String contenido = "PROVEEDOR\nHaz clic sobre el recuadro destinado para capturar tu firma.";
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
	 * seleciona el campo firma de proveedor
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void campoFirmaPrveedor() {
		au.click(CFirmaProveedor);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensaje Firma dentro del recuadro para el acuerdo de entrega
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarMensajeDentroFirma() {
		try {
			String texto = au.driver.findElement(lb_mensajeDentroFirma).getText();
			String contenido = "Firma dentro del recuadro para el acuerdo de entrega.";
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
	 * seleciona el campo firma de visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void clicfirma() {
		au.click(firma);
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * ingresa firma en el recuadro de firma
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ingresarFirma() {
		try {
			au.click(firma);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensaje de firma de visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarMensajeFirmaContinuar() {
		try {
			String texto = au.driver.findElement(lb_mensajeFirmaContinuar).getText();
			String contenido = "Da clic en \"Continuar\" para seguir con el proceso.";
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
	 * valida mensaje de firma de visita guiada de observaciones tienda
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarMensajeObservacionesTienda() {
		try {
			String texto = au.driver.findElement(lb_mensajeObservacionesTienda).getText();
			String contenido = "TIENDA\nEl siguiente paso es seleccionar una opción en la sección \"Observaciones Personal Tienda\".\n\nEjemplo: Selecciona \"Sin Comentarios\" en esta ocasión.";
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
	 * seleciona el campo observaciones tienda
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void CampoObservacionesTienda() {
		au.click(campoObservacionesPersonalTienda);
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona la opcion sin comentarios del campo Observaciones
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void LlenarObservacionesSinComentarios() {
		au.click(lb_SinComentarios);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensaje del campo firma tienda
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarMensajeRecuadroFirmaTienda() {
		try {
			String texto = au.driver.findElement(lb_mensajeRecuadroFirmaTienda).getText();
			String contenido = "Haz clic sobre el recuadro destinado para la firma del personal de tienda.";
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
	 * seleciona el recuadro firma tienda
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void clicRecuadroFrimaTienda() {
		au.click(By.xpath("(//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/img_valor\"])[2]"));
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensade de visita guiada Se Cumplio Proceso Entrega
	 * 
	 * @autor Miguel Bautista return true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarMensajeSeCumplioProcesoEntrega() {
		try {
			String texto = au.driver.findElement(lb_mensajeSeCumplioProcesoEntrega).getText();
			String contenido = "Selecciona la opción para confirmar que se cumplió con el proceso de entrega.";
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
	 * seleciona Se Cumplio Proceso Entrega
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void CheckSeCumplioProcesoEntrega() {
		au.click(chk_SeCumplioProcesoEntrega);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mesaneje de boton finalizar
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarMensajeTerminarParaFinalizar() {
		try {
			String texto = au.driver.findElement(lb_mensajeTerminar).getText();
			String contenido = "Haz clic en \"Terminar\" para finalizar el proceso de acuerdo de entrega.";
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
	 * valida mesaneje de tira de entrega de visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarMensajeTiraEntregaPedido() {
		try {
			String texto = au.driver.findElement(lb_mensajeTiraEntregaPedido).getText();
			String contenido = "Se genera la Tira de Entrega del pedido, posteriormente da clic en el botón \"Finalizar\" en la parte inferior "
					+ "de la pantalla para terminar con el proceso.\n\nLos comprobantes oficiales se visualizarán directamente en el portal de proveedores COMETA.";
			if (texto.contains(contenido) == false) {
				return false;
			}
			boolean continuar = au.driver.findElement(bt_ContinuarMensaje).isEnabled();
			if (continuar == false) {
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
	 * seleciona boton continuar
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void selecionarContinuar() {
		au.click(bt_ContinuarMensaje);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona boton Finalizar
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void opcionFinalizar() {
		au.scrollToElement(bt_Finalizar);
		testCase.addScreenShotCurrentStep(au, "testImage");
		au.click(bt_Finalizar);
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida pantalla de felicitaciones de visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarPantallaFelicitaciones() {
		try {
			String titulo = au.driver.findElement(tit_Felicidades).getText();
			String mensaje = au.driver.findElement(lb_mensajeFelicitaciones).getText();
			String contenidoTitulo = "¡Felicidades!";
			String contenidoMensaje = "Se ha concluido con la Capacitación de Visita Guiada.\n\n"
					+ "Todos los datos capturados dentro de esta capacitación fueron de prueba, por lo que no se verán afectados los inventarios de la tienda.";
			if (titulo.contains(contenidoTitulo) == false) {
				return false;
			}

			if (mensaje.contains(contenidoMensaje) == false) {
				return false;
			}

			boolean Finalizar = au.driver.findElement(bt_opcionTerminar).isEnabled();
			if (Finalizar == false) {
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
	 * ingresa cifra de proveedor
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @param si fra de proveedor (puede ser un numero random)
	 * 
	 * @date 10/octubre/2024
	 */
	public void ingresarCifraProveedor(String cProveedor) {
		au.sendKeys(campoCifraProveedor, cProveedor);
		testCase.addScreenShotCurrentStep(au, "testImage");
		au.click(campoCifraProveedor);
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensaje del boton terminar de visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarMensajeTerminarVG() {
		try {
			String texto = au.driver.findElement(lb_mensajeTerminarV).getText();
			String contenido = "Da clic en el bóton \"Terminar\" para visualizar el resumen e ir al paso de captura de firmas y comentarios.";
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
	 * validacion de datos dummies de visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean validarDummiesResumenPedido() {
		String[] contenido = { "1- DONAS 7506306416574", "2- PALETA 7506306417571", "7506306417519 AGUA EMBOTELLADA" };
		try {
			for (int i = 0; i < contenido.length; i++) {
				String producto = au.driver
						.findElement(By.xpath(tablaDummiePadre + "[" + (i + 1) + "]" + dummiArticulo)).getText();
				String sku = au.driver.findElement(By.xpath(tablaDummiePadre + "[" + (i + 1) + "]" + dummiDescripcion))
						.getText();
				if ((contenido[i].contains(producto) && contenido[i].contains(sku)) == false) {
					return false;
				} else {
					testCase.addTextEvidenceCurrentStep("Validacion Artiulos Dummies");
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona tabla de articulos de visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void clicTabla() {
		au.click(By.id("com.femco.oxxo.operaciones.mitienda.debug:id/lista_articulos_aux_view"));
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida mensaje enel campo numero de remision de visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarMensajeRemision() {
		try {
			String texto = au.driver.findElement(lb_mensajeRemision).getText();
			String contenido = "Capture el número de remisión, este mismo te lo proporciona el proveedor en el paso previo a la verificación de la mercancía.";
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
	 * valida informacion de mensaje de visita guiada al mostrar los articulos
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarMensajeArticulos() {
		try {
			String texto = au.driver.findElement(lb_mensajeArticulos).getText();
			String contenido = "Se visualiza los artículos a verificar. En caso de haber incidencias, se clasifica en dañado, caduco o faltante.\n\nEjemplo: Agrega 1 unidad en Caducos.\n\nIMPORTANTE: Si en dado caso se detectan incidencias durante la verificación, se solicitará que se verifique un porcentaje mayor del pedido original";
			if (texto.contains(contenido) == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			testCase.addTextEvidenceCurrentStep("Clic Cadudos");
			au.click(By.id("com.femco.oxxo.operaciones.mitienda.debug:id/edt_cantidad_recibida"));
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida informacion de mensaje de seleccion de QR
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarMensajeSelecionarQR() {
		try {
			String texto = au.driver.findElement(lb_mensajePasosQR).getText();
			String contenido = "Selecciona la opción \"Escanear QR de pedido o QR de Hta. de compra\" para leer la orden de compra precargada.\n\nEl QR a escanear te lo debe proporcionar el equipo de la tienda, este mismo lo va a encontrar en el apartado de pedidos precargados.";
			if (texto.equalsIgnoreCase(contenido) == false) {
				return false;
			}

			boolean boton = au.driver.findElement(bt_pasosAbrirQR).isEnabled();
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
	 * valida informacion de mensaje ver pasos para abrir qr
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */

	public boolean ValidarMensajePasosAbrirQR() {
		try {

			Object[] pasos = { lb_mensajePasosAbrirQR, lb_mensajePaso1, lb_mensajePaso2, lb_mensajePaso3,
					lb_mensajePaso4, lb_mensajePaso5, bt_mensajeSiguiete };
			String[] TextoPasos = { "Pasos en HH para abrir QR de Pedido Precargado", "Excelencia Operativa >",
					"Herramienta de Compra >", "Herramienta de Compra COMETA >",
					"Verificación de Mercancía Pedidos Proveedor Directo >", "Pedidos Precargados", "Siguiente" };

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
	 * valida informacion de mensaje del boton continuar
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarMensajeContinuar() {
		try {
			String texto = au.driver.findElement(lb_mensajeContinuar).getText();
			String contenido = "Dar clic en botón \"Continuar\" para leer QR de Pedido Precargado.\n\nNota: Para fines de este proceso de capacitación, se estará abriendo un pedido meramente ilustrativo al dar clic en continuar, como si se hubiera escaneado el código QR.";
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
	 * valida informacion de mensaje de visita guiada
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarMensajeArticulosPedido() {
		try {
			String contenido = "Este es el Pedido Precargado a entregar en la tienda, en donde podrás confirmar las cantidades disponibles para la entrega.\n\nEn dado caso que no cuentes con disponibilidad de algún SKU para su entrega, se tendrá que modificar la cantidad.";
			String texto2 = au.driver.findElement(lb_prueba).getText();
			// String texto = au.driver.findElement(lb_prueba).getAttribute("text");
			if (texto2.contains(contenido) == false) {
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
	 * valida informacion de mensaje del botton continuar
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarMensajeContinuarArticulos() {
		try {
			String texto = au.driver.findElement(lb_mensajeContinuarArticulos).getText();
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
	 * valida informacion de mensaje al generarse qr
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @return retorna true/false
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean ValidarMensajeSeGeneraQR() {
		try {
			au.scrollToElement(bt_vg_PasosLecturaQR);
			String texto = au.driver.findElement(lb_mensajeSeGeneraQR).getText();
			String contenido = "Se genera un código QR el cual incluye los artículos del pedido realizado, este mismo será leído con la Hand Held por parte del equipo de la tienda OXXO. Continúa la visita guiada directamente en la HandHeld:";
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
	 * valida informacion dummy de la pantalla tira de entrega
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public boolean validarDummiesTiraEntrega() {
		String[] contenido = { "1- DONAS 7506306416574", "2- PALETA 7506306417571", "7506306417519 AGUA EMBOTELLADA" };
		au.scrollToElement(By.xpath(
				"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txv_descripcion\" and @text=\"AGUA EMBOTELLADA\"]"));
		try {
			for (int i = 0; i < contenido.length; i++) {
				System.out.println("impresion");
				String producto = au.driver.findElement(By.xpath(tablaDummiePadreTiraEntrega + "[" + (i + 1) + "]" + dummiArticulo)).getText();
				String sku = au.driver.findElement(By.xpath(tablaDummiePadreTiraEntrega + "[" + (i + 1) + "]" + dummiDescripcion)).getText();
				if ((contenido[i].contains(producto) && contenido[i].contains(sku)) == false) {
					return false;
				} else {
					testCase.addTextEvidenceCurrentStep("Validacion Artiulos Dummies");
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * Seleciona boton Continuar
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 10/octubre/2024
	 */
	public void continuar() {
		au.click(By.id("com.femco.oxxo.reciboentiendaproveedores.debug:id/continueButton"));
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validarCampos - valida campos "SIN COMENTARIOS" "OTROS ESPECIFICA"
	 * 
	 * @author Miguel Bautista
	 * 
	 * @return true/false
	 * 
	 * @date 12/agosto/2024
	 */
	public boolean validarCampos() {
		String Sincomentarios, Otros;
		try {
			Sincomentarios = au.driver.findElement(lb_SinComentarios).getText();
			Otros = au.driver.findElement(lb_otros).getText();
		} catch (Exception E) {
			testCase.addTextEvidenceCurrentStep("no se extrajo texto");
			Otros = null;
			Sincomentarios = null;
		}

		if (Sincomentarios.equalsIgnoreCase("SIN COMENTARIOS") && Otros.equalsIgnoreCase("OTROS (ESPECIFICA)")) {
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida que el campo observaciones contenga informacion
	 * 
	 * @autor Miguel Bautista return true/false
	 * 
	 * @date 12/agosto/2024
	 */
	public boolean validaObservacionesProveedorlleno() {
		String valida;
		try {
			valida = au.driver.findElement(AEObservacionesProveedor).getText();
		} catch (Exception E) {
			testCase.addTextEvidenceCurrentStep("el texto no se muestra de la manera correcta");
			valida = null;
		}

		if (valida.equalsIgnoreCase("SIN COMENTARIOS")) {
			testCase.addTextEvidenceCurrentStep("Se muestra la opcion selecionada");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} else {
			testCase.addTextEvidenceCurrentStep("no se muestra selecionado correctamente \"SIN COMENTARIOS\"");
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * validaCampoFirmaCapturado - toma un screenshot de la pantalla, se usa para
	 * comparar las firmas
	 * 
	 * @author Alex Aquino
	 * 
	 * @param opcion- nombre del screenshot tomado
	 * 
	 * @return N/A
	 */
	public void limpiar() {
		au.click(btn_limpiar);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida que este selecionado el boton de Se Cumplio Proceso Entrega
	 * 
	 * @autor Miguel Bautista return true/false
	 * 
	 * @date 12/septiembre/2024
	 */
	public boolean validarSeCumplioProcesoCF() {
		try {
			String validacion = au.driver.findElement(ckb_SeCumplioProcesoEntrega).getAttribute("checked");
			System.out.println("validacion: " + validacion);
			if (!validacion.equals("true")) {
				return true;
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
	 * valida que NO este selecionado el boton de Se Cumplio Proceso Entrega
	 * 
	 * @autor Miguel Bautista return true/false
	 * 
	 * @date 12/septiembre/2024
	 */
	public boolean validarSinSelecionProcesoCF() {
		try {
			String validacion = au.driver.findElement(ckb_SeCumplioProcesoEntrega).getAttribute("checked");
			System.out.println("validacion: " + validacion);
			if (!validacion.equals("false")) {
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
	 * valida que se muestra la pantalla acuerdo de entrega
	 * 
	 * @autor Miguel Bautista return true/false
	 * 
	 * @date 12/septiembre/2024
	 */
	public boolean validaExistenciaPantallaAcerdoEntrega() {
		try {
			String titAcuerdoEntrga = au.driver.findElement(tit_AcuerdoEntrega).getText();
			if (titAcuerdoEntrga.equalsIgnoreCase("ACUERDO DE ENTREGA") == false) {
				return false;
			}

			boolean PregFrecuentes = au.driver.findElement(bt_PregFrecuentes).isEnabled();
			if (PregFrecuentes == false) {
				return false;
			}

			boolean Tutoriales = au.driver.findElement(bt_Tutoriales).isEnabled();
			if (Tutoriales == false) {
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
	 * valida pantalla acuerdo de entrega
	 * 
	 * @autor Miguel Bautista return true/false
	 * 
	 * @date 12/septiembre/2024
	 */

//		private By lb_observacionesTienda = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvw_titulo\" and @text=\"Observaciones Personal Tienda:\"]");
	public boolean PantallaAcuerdoEntrega() {

		try {
			boolean Regresar = au.driver.findElement(bt_Regresar).isEnabled();
			if (Regresar == false) {
				return false;

			}
			String titAcuerdoEntrga = au.driver.findElement(tit_AcuerdoEntrega).getText();
			if (titAcuerdoEntrga.equalsIgnoreCase("ACUERDO DE ENTREGA") == false) {
				return false;
			}
			boolean PregFrecuentes = au.driver.findElement(bt_PregFrecuentes).isEnabled();
			if (PregFrecuentes == false) {
				return false;
			}
			boolean Tutoriales = au.driver.findElement(bt_Tutoriales).isEnabled();
			if (Tutoriales == false) {
				return false;
			}
			String TituloResumenRealizados = au.driver.findElement(tit_resumenRealizdos).getText();
			if (TituloResumenRealizados.equalsIgnoreCase("RESUMEN CAMBIOS REALIZADOS") == false) {
				return false;
			}
			String tituloCambiosProgramados = au.driver.findElement(tit_cambiosProgramados).getText();
			if (tituloCambiosProgramados.equalsIgnoreCase("RESUMEN CAMBIOS PROGRAMADOS PENDIENTES") == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.scrollToElement(CFirmaProveedor);
			String firmaAcuerdo = au.driver.findElement(lb_FirmaAcuerdo).getText();
			if (firmaAcuerdo.equalsIgnoreCase("FIRMA DE ACUERDO") == false) {
				return false;
			}
			String observacionesProveedor = au.driver.findElement(lb_observacionesPorveedor).getText();
			if (observacionesProveedor.equalsIgnoreCase("Observaciones del Proveedor:") == false) {
				return false;
			}
			String campoObservacionesP = au.driver.findElement(campoObservacionesProveedor).getText();
			if (campoObservacionesP.equalsIgnoreCase("SELECCIONA UNA OPCIÓN") == false) {
				return false;
			}
			String firmaProveedor = au.driver.findElement(lb_firmaProveedor).getText();
			if (firmaProveedor.equalsIgnoreCase("Firma Proveedor:") == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.scrollToElement(lb_firmaTienda);
			String ObservacionesPersonalTienda = au.driver.findElement(lb_observacionesTienda).getText();
			if (ObservacionesPersonalTienda.equalsIgnoreCase("Observaciones Personal Tienda:") == false) {
				return false;
			}
			String campoSelecionaOpcion = au.driver.findElement(lb_selecionaOpcionTienda).getText();
			if (campoSelecionaOpcion.equalsIgnoreCase("SELECCIONA UNA OPCIÓN") == false) {
				return false;
			}
			String firmaTienda = au.driver.findElement(lb_firmaTienda).getText();
			if (firmaTienda.equalsIgnoreCase("Firma Personal Tienda:") == false) {
				return false;
			}
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.scrollToElement(bt_Siguiente);
			String chkSeCumplioProceso = au.driver.findElement(ckb_SeCumplioProcesoEntrega).getText();
			if (chkSeCumplioProceso.equalsIgnoreCase("Se cumplió con el proceso de cambios físicos") == false) {
				return false;
			}
			boolean siguiente = au.driver.findElement(bt_Siguiente).isEnabled();
			if (siguiente == false) {
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
	 * ScrollToFirmaProveedor - Se dezplaza hasta el apartado forma de proveedor
	 * 
	 * @author Miguel Bautista
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void ScrollToFirmaProveedor() {
		swipeCoord(50, 600, 50, 300, 500);
	}

	private By Siguiente = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_siguiente");

	/*
	 * Metodo
	 ******************************************************************************************
	 * ScrollToPersonalTienda - Se dezplaza hasta el apartado personal tienda
	 * 
	 * @author Miguel Bautista
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void ScrollToPersonalTienda() {
		au.scrollToElement(Siguiente);
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * obtiene el numero de remision de la pantalla
	 * 
	 * @author Miguel Bautista
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public String ObtenerNoRemision() {
		remision = au.driver.findElement(lb_remision).getText();
		remision = remision.replaceAll("Remisión: ", "");
		return remision;
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona el boton continuar
	 * 
	 * @author Miguel Bautista
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void Finalizar() {
		au.scrollToElement(bt_Finalizar);
		au.click(bt_Finalizar);
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * ingresa numero de remision
	 * 
	 * @author Miguel Bautista
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void ingresarRemision() {
		au.sendKeys(tb_CapFAoNumRemision, remision);
		testCase.addScreenShotCurrentStep(au, "remision");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida que se muestra pantalla
	 * 
	 * @author Miguel Bautista
	 * 
	 * @param N/A
	 * 
	 * @return true/false
	 */
	public boolean pantallaResumenCambiosProgramados() {
		String ResumenCambiosProgramados = null;
		try {
			ResumenCambiosProgramados = au.driver.findElement(lb_ResumenCambiosProgramados).getText();
		} catch (Exception E) {
			return false;
		}
		if (ResumenCambiosProgramados.equalsIgnoreCase("RESUMEN CAMBIOS PROGRAMADOS PENDIENTES")) {
			testCase.addTextEvidenceCurrentStep("Se muestra la pantalla Resumen de Cambios Programados pendientes");
			testCase.addScreenShotCurrentStep(au, "testImage");
		} else {
			return false;
		}
		return true;
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * realiza cambio fisico
	 * 
	 * @author Miguel Bautista
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void RealizarCambioPantallaRCPP() {
		au.click(opcion_pendiente);
		au.click(opcion_realizado);
		au.click(bt_Sumar);
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona el boton siguiente
	 * 
	 * @author Miguel Bautista
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void PresionaSiguiente() {
		au.scrollToElement(bt_SiguienteRCPP);
		au.click(bt_SiguienteRCPP);
		testCase.addScreenShotCurrentStep(au, "testImage");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * agrega cambio fisico a fuente de abasto
	 * 
	 * @author Miguel Bautista
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void agregarCFpendiente() {
		au.sendKeys(CFprogramado, "1");
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona el boton siguiente
	 * 
	 * @author Miguel Bautista
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean ValidarMensajeExistenCambiosFisicos() {
		String msg, btn_aceptar, btn_cancelar;
		try {
			// valida = au.driver.findElement(AEObservacionesProveedor).getText();
			msg = au.driver.findElement(Mensaje).getText();
			btn_cancelar = au.driver.findElement(bt_cancelar).getText();
			btn_aceptar = au.driver.findElement(bt_aceptar).getText();
		} catch (Exception e) {
			msg = null;
			btn_cancelar = null;
			btn_aceptar = null;
			return false;
		}

		if (msg.equalsIgnoreCase(
				"Ya se han realizado cambios físicos para este proveedor el día de hoy. ¿Desea continuar?")) {
			testCase.addTextEvidenceCurrentStep("texto mostrado correctamente");
		} else {
			testCase.addTextEvidenceCurrentStep("el texto no se muestra correctamente");
			return false;
		}

		if (btn_cancelar.equalsIgnoreCase("CANCELAR") && btn_aceptar.equalsIgnoreCase("ACEPTAR")) {
			testCase.addTextEvidenceCurrentStep("botones mostrados correctamente");
		} else {
			testCase.addTextEvidenceCurrentStep("no se muestran correctamente los botones CANCELAR - ACEPTAR");
			return false;
		}

		testCase.addScreenShotCurrentStep(au, "testImage");
		return true;

	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * captura FA nube
	 * 
	 * @autor mabm
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */

	public boolean ingresarFuenteAbastoNube() {
		System.out.println("faEVO: " + FaEvo);
		au.sendKeys(tb_CapturaFuenteAbasto, FaEvo);
		String FA;
		try {
			FA = au.driver.findElement(tb_CapturaFuenteAbasto).getText();
		} catch (Exception e) {
			FA = "";
		}
		if (FA != "") {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Metodo
	 ******************************************************************************************
	 * Capturar Manualmente Sku´s y validar su captura.
	 * 
	 * @author Miguel Butista
	 * @param N/A
	 * @return N/A
	 */
	public void LimiarCampoFA() {
		WebElement bprrar = au.driver.findElement(tb_CapturaFuenteAbasto);
		bprrar.clear();
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * captura FA con cambios fidicos programados
	 * 
	 * @author Miguel Bautista
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public boolean ingresarFuenteAbastoCambioFisico() {
		System.out.println("faEVO: " + FaEvo);
		au.sendKeys(tb_CapFAoNumRemision, FaEvo);
		String FA;
		try {
			FA = au.driver.findElement(tb_CapFAoNumRemision).getText();
		} catch (Exception e) {
			FA = "";
		}
		if (FA != "") {
			testCase.addTextEvidenceCurrentStep("Se valida que se ingresa la FA y la informacion queda capturada");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * Metodo
	 ******************************************************************************************
	 * obtenerNumeroProveedor - Retorna numero de proveedor existente en la base de datos
	 * 
	 * @author Miguel Bautista
	 * 
	 * @return Numero de proveedor
	 * 
	 * @param N/A
	 */
	
	public String obtenerNumeroProveedor() {
		try {
			Connection conn = DriverManager.getConnection(GlobalVariables.urlPV10_212_Satelites, GlobalVariables.userDB10_212,
					GlobalVariables.passDB10_212);
			String query = "SELECT [HC_Vendor] FROM [Satelites].[hc].[hc_purchaseorder] "
					+ "WHERE [Hc_PO_Additional] <> 0 ORDER BY [HC_PO_Date] DESC" ;
			SQLResult res = new SQLResult(conn, query);
			NoProveedor = String.valueOf(Integer.parseInt(res.getFirstData().toString()));
			System.out.println("No. Proveedor: " + NoProveedor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NoProveedor;
	}
	
	
	/*
	 * Metodo
	 ******************************************************************************************
	 * seleciona boton Cancelar
	 * 
	 * @author Miguel Bautista
	 * 
	 * @return N/A
	 * 
	 * @param N/A
	 */
	public void Cancelar() {
		au.click(bt_cancelar);
	}
	
	
	/*
	 * Metodo
	 ******************************************************************************************
	 * selecciona boton buscar sin agregarr evidencia
	 * 
	 * @author Miguel Bautista
	 * 
	 * @return N/A
	 * 
	 * @param N/A
	 */
	public boolean opcionBuscarSinEvidencia() {
	    try {
	        au.click(bt_Buscar);
	        //-----Error Servicio no disponible (404)------
	        au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
	        if (au.driver.findElement(By.id("android:id/button1")).isDisplayed()) {
				au.click(By.id("android:id/button1"));
				au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
			}
	        return true; // Si todo se ejecuta sin problemas, retorna verdadero
	        
	    } catch (Exception e) {
	        return false;
	    }
	}
	
	
	/*
	 * Metodo
	 ******************************************************************************************
	 * valida que el campo numero de remision no se encuetre vacio
	 * 
	 * @author Miguel Bautista
	 * 
	 * @return true/false
	 * 
	 * @param N/A
	 */
	public boolean validarCapturarRemisionFA() {
		try {
			String camFA = au.driver.findElement(tb_CapFAoNumRemision).getText();
			if (!camFA.isEmpty()) {
				testCase.addTextEvidenceCurrentStep("El se agrego elemento al campo");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} else {
				testCase.addTextEvidenceCurrentStep("El campo se encuentra vacio");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Metodo
	 ******************************************************************************************
	 * valida que se muestre numero de remision
	 * 
	 * @author Miguel Bautista
	 * 
	 * @return true/false
	 * 
	 * @param N/A
	 */
	public boolean validacionCFremision() {
		try {
			String cfa = au.driver.findElement(campoFA).getText();
			System.out.println(cfa + " --- " + FaEvo);
			if (!cfa.equals(FaEvo)) {
				return false;
			}

			String cRemision = au.driver.findElement(campoRemision).getText();

			System.out.println(cRemision + " --- " + remision);
			if (!cRemision.equals(remision)) {
				return false;
			}

			String skuCF = au.driver.findElement(campoCF).getText();
			System.out.println(skuCF + " --- " + SkusEvo.get(0));
			if (!skuCF.equals(SkusEvo.get(0))) {
				return false;
			}

			testCase.addTextEvidenceCurrentStep("se muestren los cambios fisicos asociados al Numero de Remision");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	
	/*
	 * Metodo
	 ******************************************************************************************
	 * valida datos dummy 
	 * 
	 * @author Miguel Bautista
	 * 
	 * @return N/A
	 * 
	 * @param N/A
	 */
	public boolean DummyPrecargado() {
		String[] contenido = {"1- DONAS 7506306416574","2- PALETA 7506306417571"};
		try {
			for(int i = 0; i<contenido.length;i++){
				String producto = au.driver.findElement(By.xpath(DummyPadre+"["+(i+1)+"]"+ProductoDummy)).getText();
				String sku = au.driver.findElement(By.xpath(DummyPadre+"["+(i+1)+"]"+skuDummy)).getText();

				if((contenido[i].contains(producto) && contenido[i].contains(sku))==false) {
					return false;
				}
				else {
					testCase.addTextEvidenceCurrentStep("Validacion Artiulos Dummies");
				}	
			}
			au.click(By.xpath("(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.reciboentiendaproveedores.debug:id/btn_sumar\"])[1]"));
			return true;
		}catch(Exception e) {
			return false;
		}
		
	}
}
