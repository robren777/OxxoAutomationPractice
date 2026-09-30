package Mobile.POSMovil.om;

import org.openqa.selenium.By;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.util.Properties;

import org.openqa.selenium.WebElement;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import Mobile.util.GlobalVariables;
import utils.sql.SQLResult;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertTrue;

import io.appium.java_client.android.nativekey.AndroidKey;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.common.collect.ImmutableMap;

import java.util.regex.*;

public class PM {
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;

	/*
	 * Localizadores Journal
	 */
	private By Journal = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/rvJournal");
	private By Buscador = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/etSearchItem");
	private By MBuscador = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/llZoneTrigger");
	private By BuscadorS = By
			.xpath("//android.view.ViewGroup[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/cnstEtSearch\"]");
	private By Articulo = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/tvNameItem");
	private By LBPRM = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/tvPromo");
	private By btn_Buscar = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/rvSearch");
	private By btn_cantidad = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/btnAdddItem");
	private By btn_calcular = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/btnCalcular");
	private By btn_total = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/btnTotal");
	private By btn_efectivo = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/btnCash");
	private By btn_tarjeta = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/btnCard");
	private By btn_aplicar = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/btnProcess");
	private By msgArticuloBEDH = By.xpath("//android.widget.Toast[@text=\"Solicita identificación oficial.\"]");
	private By msgArticuloBEFO = By.xpath("//android.widget.Toast[@text=\"Venta fuera de horario.\"]");
	private By montoVenta = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/tvPaymentTotal");
	private By artJournal = By
			.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/tvITem\"]");
	private By msjPagoExitoso = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/tvLabelTittle\"]");
	private By pantTicketDigital = By.xpath(
			"//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/rvJournal\"]");
	private By mjgvtaExitosa = By.xpath("//android.widget.Toast[@text=\"Venta completada con éxito\"]");

	/* Localizadores Fast Food */
	private By btn_fastFood = By.xpath(
			"//android.widget.FrameLayout[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/btnFastFood\"]/android.view.ViewGroup");
	private By btn_FFBebidas = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"Fast Food Bebidas\"]");
	private By btn_FFAlimentos = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"Fast Food Alimentos\"]");
	private By pas1_FFA = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"Comida caliente\"]");
	private By pas2_FFA = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"OSABOR\"]");
	private By pas3_FFA = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"BURRITOS\"]");
	private By pas4_FFA = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"BURRITOS CLASICOS\"]");
	private By pas1_FFB = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"Americano\"]");
	private By pas2_FFB = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"Americano andatti\"]");
	private By pas3_FFB = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"Intenso\"]");
	private By pas4_FFB = By.xpath(
			"//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/rvService\"]/android.widget.FrameLayout");
	private By artFFA = By.xpath(
			"(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/btnAdddItem\"])[1]");
	private By artFFA2 = By.xpath(
			"(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/btnAdddItem\"])[2]");
	private By artFFB = By.xpath(
			"//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/btnAdddItem\"]");
	private By btn_ctnFF = By
			.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/btnContinue\"]");
	private By cat1FFA = By.xpath(
			"//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/rvService\"]/android.widget.FrameLayout[1]");
	private By cat2FFA = By.xpath(
			"//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/rvService\"]/android.widget.FrameLayout[2]");
	private By cat3FFA = By.xpath(
			"//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/rvService\"]/android.widget.FrameLayout[3]");
	private By cat1CCFFA = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"Insumos c.caliente\"]");
	private By cat2CCFFA = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"OSABOR\"]");
	private By cat3CCFFA = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"Pollos\"]");
	private By txtTopFF = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/tv_most");
	private By top1FFA = By.xpath(
			"//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/recyclerViewHorizontal\"]/android.widget.LinearLayout[1]");
	private By top2FFA = By.xpath(
			"//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/recyclerViewHorizontal\"]/android.widget.LinearLayout[2]");

	/* Localizadores Servicios No Financieros */
	private By etServicios = By.xpath(
			"//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/etSearchItem\"]");
	private By btn_servicios = By.xpath(
			"//android.widget.FrameLayout[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/btnService\"]/android.view.ViewGroup");
	private By modServicios = By.xpath("//android.widget.TextView[@text=\"Servicios\"]");
	private By categorias = By.xpath(
			"//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/rvService\"]");
	private By btn_catTVPaga = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/txtTitle\" and @text=\"TV Paga\"]");
	private By tvPaga = By.xpath("");
	private By btn_catTeleCom = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/txtTitle\" and @text=\"Telecomunicaciones\"]");
	private By btn_catEnvExp = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/txtTitle\" and @text=\"Envio Estandar\"]");
	private By servIzzi = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/txtTitle\" and @text=\"IZZI EN LINEA\"]");
	private By servIzziD = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/txtTitle\" and @text=\"IZZI\"]");
	private By servSky = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/txtTitle\" and @text=\"SKY EN LINEA\"]");
	private By servSkyN = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/txtTitle\" and @text=\"SKY ONLINE NAC\"]");
	private By servTelmex = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/txtTitle\" and @text=\"TELMEX EN LINEA\"]");
	private By servMercLibre = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/txtTitle\" and @text=\"Mercado Libre en Linea\"]");
	private By servDish = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/txtTitle\" and @text=\"DISH EN LINEA\"]");
	private By servDishCB = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/txtTitle\" and @text=\"DISH EN LINEA CB\"]");
	private By EdTeServicios = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/editTextIdentifier");
	private By btn_ConsCliente = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/btnConsult");
	private By btn_pagarServ = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/btnConfirmar\" and @text=\"Pagar\"]");
	private By btn_cancelarServ = By
			.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/btnCancelar\"]");
	private By txtServMasConsultados = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/textView20");
	private By topServ1 = By.xpath(
			"//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/rvTop\"]/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup");
	private By topServ2 = By.xpath(
			"//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/rvTop\"]/android.view.ViewGroup[2]/android.widget.FrameLayout/android.view.ViewGroup");
	private By topServ3 = By.xpath(
			"//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/rvTop\"]/android.view.ViewGroup[3]/android.widget.FrameLayout/android.view.ViewGroup");
	private By txtYaExisteServicio = By
			.xpath("//android.widget.Toast[@text=\"Ya existe un servicio en línea previo\"]");
	private By btnSiCtnServicios = By.xpath(
			"//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/btnConfirm\" and @text=\"Sí, continuar\"]");

	/* Localizadores Cortes Administrativos */
	private By btn_Opciones = By
			.xpath("//android.widget.ImageButton[@content-desc=\"Abrir panel lateral de navegación\"]");
	private By btn_CortesAdm = By.xpath(
			"//android.widget.CheckedTextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/design_menu_item_text\" and @text=\"Cortes administrativos\"]");
	private By tipCortes = By
			.xpath("//android.widget.Spinner[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/spCortes\"]");
	private By cortHEF = By
			.xpath("//android.widget.CheckedTextView[@resource-id=\"android:id/text1\" and @text=\"HEF POS Móvil\"]");
	private By cortX = By
			.xpath("//android.widget.CheckedTextView[@resource-id=\"android:id/text1\" and @text=\"Corte X\"]");
	private By cortZ = By
			.xpath("//android.widget.CheckedTextView[@resource-id=\"android:id/text1\" and @text=\"Corte Z\"]");
	private By btn_consCorteHEF = By
			.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/btnFinDia\"]");
	private By btn_consCorteX = By
			.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/btnCorteX\"]");
	private By btn_consCorteZ = By
			.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/btnCorteZ\"]");
	private By btn_consCerrar = By
			.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/btnCancel\"]");
	private By pestCortAdm = By.xpath("//android.widget.TextView[@text=\"Cortes Administrativos\"]");
	private By btn_NavAtras = By.xpath("//android.widget.ImageButton[@content-desc=\"Navegar hacia arriba\"]");
	private By totCorte = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/tvTotalOperaLabel\"]");
	private By efecCorte = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/tvSalesCashLabel\"]");
	private By efecEntregado = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/tvSalesCardLabel\"]");
	private By efecPorEntr = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/tvTotalSalesLabel\"]");

	/* Localizadores TAE */
	private By btn_TAE = By
			.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textView16\"]");
	private By cpaTelcel = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/txtTitle\" and @text=\"Telcel\"]");
	private By cpaMovistar = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/txtTitle\" and @text=\"Movistar\"]");
	private By tae20T = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/txtTitle\" and @text=\"TAE TELCEL $20\"]");
	private By tae20M = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/txtTitle\" and @text=\"TAE MOVISTAR $20\"]");
	private By txtRec = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textView111123\" and @text=\"Recarga de tiempo aire\"]");
	private By etNum1 = By.xpath(
			"//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/etPhoneNumber\"]");
	private By etNum2 = By.xpath(
			"//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/etPhoneNumberTwo\"]");
	private By btn_aceptarNumero = By
			.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/btnCalcular\"]");
	private By lbRecargaExi = By
			.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textView13\"]");
	private By btn_finalizar = By
			.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/btnFinish\"]");
	private By txtRecMasVendidas = By
			.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textView18\"]");
	private By topRec1 = By
			.xpath("//android.widget.FrameLayout[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/cvItem1\"]");
	private By topRec2 = By
			.xpath("//android.widget.FrameLayout[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/cvItem2\"]");
	private By msjNumInc = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/tvTitleError\"]");

	/* Localizadores Spin Premia */
	private By etCliente = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/etPhoneNumber");
	private By btn_idCliente = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/clFiendClient");
	private By btn_AplicarSpin = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/btnProcess");
	private By btn_aplicarNTIC = By
			.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/btnProcess\"]");

	/* Variables Globales y Publicas */
	private String val;
	public static String prmSimple, skuSimple, articulo, articuloBA, CRTienda, tktprml, tktprmn, prmMulti, skuMulti,
	prmPromP, skuPromP, prmHide, skuHide, prmMBG, skuMBG1, skuMBG2, prmPivote, skuPivote1, skuPivote2, inftkt,
	tktsalesprmNube, tktsalesprm, montoVentaE, valormontoventa, montoVentanube, StoreIdP, artTamal, artFFAG,valPrmD,
	montoComServ,montoComServN,montoComCons,montoComConsServ,promFlag,restFlag,spDate,patchDate,urlCoal;
	private int statusCode;

	/* Cambios MMORALES */
	private By taeMasVendido1 = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/txtTop1");
	private By taeMasVendido2 = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/txtTop2");
	private By taeMasVendido3 = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/txtTop3");
	private By phone1 = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/etPhoneNumber");
	private By phone2 = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/etPhoneNumberTwo");
	private By btnAceptarRecarga = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/btnCalcular");
	private By loading = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/tvLoading");
	private By importeTotal = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/tvValue");
	private By msgVtaTae = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/textView13");
	private By btnFinalizar = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/btnFinish");
	private By titleRecargaTAE = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/textView111123");
	private By listaProvTAE = By.xpath(
			"((//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/rvService\"]/android.view.ViewGroup)/android.widget.FrameLayout/android.view.ViewGroup)");
	private By titleTAEService = By.xpath("//android.widget.TextView[@text=\"TAE Service\"]");
	private By cantidadProdJournal = By.xpath(
			"//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/rvJournal\"]/android.view.ViewGroup");
	private By totalVta = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/tvLabelPoints");
	private By titleRetiro = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/tvTitle");
	private By msjRetiro = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/tvSaldoInicial");
	private By graciasCompra = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/tvLabelHeader");
	private By btnFinishTicket = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/btnFinishTicket");
	private By btnCancelar = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/btnCancel");
	private By btnConfirmar = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/btnConfirm");
	private By btnConfirmar2 = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/btnConfirm2");
	private By password = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/password");
	private By montoRetiro = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/amount");
	private By inputMontoRetiro = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/amountCut3");
	private By mensajeRetiro = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/amountCut8");
	private By limpiarBusqueda = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/btnCleanSearch");
	private By listaRecargas = By.xpath(
			"//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/rvService\"]/android.view.ViewGroup");
	private By lblMetodoPago = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/tvLabelSelect");
	private By msjErrorTelefonos = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/tvTitleError");
	private By btnFastFood = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/btnFastFood");
	private By listaFastFood = By.xpath(
			"(//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/rvService\"]/android.widget.FrameLayout)/android.widget.LinearLayout");
	private By btnContinue = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/btnContinue");
	private By listaProdFastFood = By
			.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/tvITem\"]");
	private By settings = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/btnSettings");
	private By btnSettingsConfirmar = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/btnCheckPws");
	private By passSettings = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/etPwsd");
	private By titleSettings = By.xpath("//android.widget.TextView[@text=\"Configuraciones\"]");
	private By settingsPrinters = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/tvTitle\" and @text=\"Impresoras\"]");
	private By scrSplash = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/wvPosGif");
	private By btnServicios = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/btnService");
	private By montoServicio = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/txtAmount");
	private By montoPagarServicio = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/editAmountPaid");
	private By comisionServicio = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/txtCommission");
	private By montoTotalServicio = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/txtTotal");
	private By btnPagarServicio = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/btnConfirmar");

	/* Cambios Irving Orozco */
	private By Btn_TAE = By.xpath(
			"//android.widget.FrameLayout[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/btnSpin\"]/android.view.ViewGroup");
	private By TAE_Telcel = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/txtTitle\" and @text=\"Telcel\"]");
	private By TAE_Movistar = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/txtTitle\" and @text=\"Movistar\"]");
	private By btn_TAEtop = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/ivIcSeg1");
	private By Telcel_TAE30 = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/txtTitle\" and @text=\"TAE TELCEL $30\"]");
	private By Movistar_TAE30 = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/txtTitle\" and @text=\"TAE MOVISTAR $30\"]");
	private By articuloJournal = By
			.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/tvITem\"]");
	private By btn_fastfood = By.xpath(
			"//android.widget.FrameLayout[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/btnFastFood\"]/android.view.ViewGroup");
	private By btn_fastfoodALI = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"Fast Food Alimentos\"]");
	private By btn_fastfoodBEB = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"Fast Food Bebidas\"]");
	private By btn_fastfoodBEBAmericano = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"Americano\"]");
	private By btn_fastfoodBEBCapuchino = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"Capuchino\"]");
	private By btn_capuchinoAndatti = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"Capuchino andatti\"]");
	private By btn_BebidaTOP = By.xpath(
			"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/addButton\"])[1]");
	private By btn_AlimentoTOP = By.xpath(
			"(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/addButton\"])[1]");
	private By btn_ComidaCaliente = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"Comida caliente\"]");
	private By btn_ComidaDeshidratada = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"Comida Deshidratada\"]");
	private By btn_ComidaOSABOR = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\"]");
	private By btn_CapuchinoAndatti = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"Capuchino andatti\"]");
	private By btn_CapuchinoNescafe = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"Capuchino Nescafe\"]");
	private By btn_LATTE = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"Latte\"]");
	private By btn_Chocolate = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"Chocolate\"]");
	private By btn_MOKA = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"Moka\"]");
	private By btn_12oz = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"12oz\"]");
	private By btn_12OZ = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"12OZ\"]");
	private By btn_16oz = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"16oz\"]");
	private By btn_Agregar = By.xpath(
			"(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/btnAdddItem\"])[1]");
	private By btn_continuar = By
			.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/btnContinue\"]");
	private By btn_AmericanoAndatti = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\"]");
	private By btn_cafedeolla = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"Cafe de Olla\"]");
	private By btn_intenso = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"Intenso\"]");
	private By btn_MezMexicana = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"Mezclas Mexicanas\"]");
	private By btn_Quesera = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"Quesera\"]");
	private By btn_Quesera1 = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"Quesera\"]");
	private By btn_Nachos = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"Nachos\"]");
	private By btn_Elote = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"Elote\"]");
	private By btn_BurritosClasicos = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"BURRITOS CLASICOS\"]");
	private By btn_BurritosEspeciales = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"BURRITOS ESPECIALES\"]");
	private By btn_Otacos = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"TACOS\"]");
	private By btn_Otortas = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"TORTAS\"]");
	private By btn_OHarina = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"QUESADILLAS HARINA\"]");
	private By btn_OChilaquiles = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"CHILAQUILES\"]");
	private By btn_Otorta = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\"]");
	private By btn_OBurritos = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"BURRITOS\"]");
	private By btn_OQuesadillas = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"QUESADILLAS\"]");
	private By btn_OOtros = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"OTROS\"]");
	private By btn_TacosClasicos = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"TACOS CLASICOS\"]");
	private By btn_TacosEspeciales = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"TACOS ESPECIALES\"]");
	private By btn_Extras = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"EXTRAS\"]");
	private By btn_Molletes = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"MOLLETES\"]");
	private By btn_Pizzas = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"Pizzas\"]");
	private By TxtRec = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textView111123\" and @text=\"Recarga de tiempo aire\"]");
	private By EtNum1 = By.xpath(
			"//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/etPhoneNumber\"]");
	private By EtNum2 = By.xpath(
			"//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/etPhoneNumberTwo\"]");
	private By Btn_aceptarNumero = By
			.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/btnCalcular\"]");
	private By LbRecargaExi = By
			.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textView13\"]");
	private By Btn_finalizar = By
			.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/btnFinish\"]");
	private By PantPago = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/layoutPayments");

	/* Cambios Cesar Delgado */
	private By btn_siContinuar = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/btnConfirm");
	private By btn_noContinuar = By
			.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/btnCancel\"]");
	private By btn_siContSalir = By
			.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/btnConfirm\"]");
	private By btn_noContSalir = By
			.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/btnCancel\"]");
	private By btn_comXConsulta = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/tvITem\" and @text=\"Comisión\"]");
	private By btn_comServicio = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/tvITem\" and @text=\"Comisión\"]");
	private By lb_leyCom = By
			.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textView15\"]");
	private By lb_clienteServ = By
			.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/txtName\"]");
	private By lb_refClient = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/txtReference\"]");
	private By lb_salAf = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/txtBalanceFavor\"]");
	private By lb_edoServ = By
			.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/txtService\"]");
	private By lb_monto = By
			.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textView23\"]");
	private By lb_montoP = By
			.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textView27\"]");
	private By lb_com = By
			.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textView28\"]");
	private By lb_montoT = By
			.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textView30\"]");
	private By btn_Vainilla = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"Vainilla\"]");
	private By btn_Canela = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"Canela\"]");
	private By btn_Chai = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"Chai\"]");
	private By btn_R16oz = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"R16oz\"]");
	private By btn_R20oz = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\"R20oz\"]");
	private By btn_CapAndatti = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\"]");
	private By btn_burritoFrijoles = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/tvITem\" and @text=\"BURRITO DE FRIJOLES      \"]");
	private By btn_burritoOp1 = By.xpath(
			"(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/btnAdddItem\"])[1]");
	private By btn_burritoOp2 = By.xpath(
			"(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/btnAdddItem\"])[2]");
	private By lb_susSinAdeudo = By
			.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/txtError\"]");
	private By btn_navAtrServ = By.xpath("//android.widget.ImageButton[@content-desc=\"Navegar hacia arriba\"]");
	private By modServ = By.xpath("//android.widget.TextView[@text=\"Servicios\"]");

	/**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase PM
	 * 
	 * @author Cesar Guel
	 * @param au            (AppiumUtil): objeto de la clase
	 * @param testCase      (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 */
	public PM(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
		this.testCase = testCase;
		this.au = au;
		this.baseExecution = baseExecution;
	}

	/**
	 * Método - Ingresar al Journal
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si se encuentra el elemento
	 * @date 06/09/2024
	 */
	public boolean ingresarJournal() {
		try {
			testCase.addTextEvidenceCurrentStep("Ingresar al Journal");
			TimeUnit.SECONDS.sleep(10);
			WebElement ingresarJournal = au.driver.findElement(Journal);
			if (ingresarJournal.isDisplayed()) {
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(10);
				return true;
			} else {
				testCase.addTextEvidenceCurrentStep("Journal no encontrado.");
				return false;
			}
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error: No se pudo ingresar al Journal. " + e.getMessage());
			return false;
		}
	}

	public boolean ingresarJournalV2() {
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(180));
			testCase.addTextEvidenceCurrentStep("Ingresar al Journal ingresarJournalV2");
			WebElement ingresarJournal = au.driver.findElement(Journal);
			wait.until(ExpectedConditions.visibilityOfElementLocated(Journal));
			if (ingresarJournal.isDisplayed()) {
				testCase.addScreenShotCurrentStep(au, "testImage");
				au.wait(10);
//				TimeUnit.SECONDS.sleep(10);				
				return true;
			} else {
				testCase.addTextEvidenceCurrentStep("Journal no encontrado. Favor de revisar");
				return false;
			}
//		} catch (Exception e) {
		} catch (NoSuchElementException e) {
//			testCase.addTextEvidenceCurrentStep("Error: No se pudo ingresar al Journal. " + e.getMessage());//dato otignal
			testCase.nextStep("Error: No se pudo ingresar al Journal. Se obtiene el siguiente mensaje de Exception: "
					+ e.getMessage());
			return false;
		}
	}

	/**
	 * Método - Ingresar al buscador
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 22/01/2025
	 */
	public void ingresarBuscador() {
		try {
			testCase.addTextEvidenceCurrentStep("Ingresar al buscador");
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Ingresar Sku de articulo en el Journal
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @date 06/09/2024
	 */
	public void ingresarSkuArticuloJournal(String ipPv) {
		try {
			String articulo = ConsultaArticulo(ipPv);
			WebElement buscador = au.driver.findElement(Buscador);
			testCase.addTextEvidenceCurrentStep("Tap en Botón Buscador de articulos");
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Ingresar el valor al buscador ");
			buscador.sendKeys(articulo);
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");

		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error, Ingresar el artiulo al Journal" + e.getMessage());
		}
	}
	
	/**
	 * Método - Ingresar Sku de articulo en el Journal con valor dinamico
	 * 
	 * @author Cesar Guel
	 * @date 06/09/2024
	 */
	public void ingresarSkuArticuloJournalD() {
		try {
			String articulo = ConsultaArticuloD();
			WebElement buscador = au.driver.findElement(Buscador);
			testCase.addTextEvidenceCurrentStep("Tap en Botón Buscador de articulos");
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Ingresar el valor al buscador ");
			buscador.sendKeys(articulo);
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");

		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, Ingresar el artiulo al Journal" + e.getMessage());
		}
	}

	/**
	 * Método - Ingresar Sku de articulo en promocion
	 * 
	 * @author Cesar Guel
	 * @param crTienda El nombre de la plaza y tienda utilizado
	 * @date 09/09/2024
	 */
	public void ingresarSkuArticuloPrmJournal(String crTienda) {
		try {
			String articulo = consultaSkuArtPrmN(crTienda);
			WebElement buscador = au.driver.findElement(Buscador);
			testCase.addTextEvidenceCurrentStep("Tap en Botón Buscador de articulos");
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(3);

			testCase.addTextEvidenceCurrentStep("Ingresar el valor al buscador ");
			System.out.println(articulo);
			buscador.sendKeys(articulo);
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error, Ingresar el artiulo al Journal" + e.getMessage());
		}
	}
	
	/**
	 * Método - Ingresar Sku de articulo en promoción MultiUnit
	 * 
	 * @author Cesar Guel
	 * @param nomTienda El nombre de la tienda utilizada
	 * @date 06/04/2026
	 */
	public void ingresarSkuArticuloPrmMultiU(String nomTienda) {
		try {
			String articuloMu = consultaSkuPrmMulti(nomTienda);
			
			WebElement buscador = au.driver.findElement(Buscador);
			testCase.addTextEvidenceCurrentStep("Tap en Botón Buscador de articulos");
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Ingresar el valor al buscador ");
			au.driver.hideKeyboard();
			buscador.sendKeys(articuloMu);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(3);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Ingresar Sku de articulo en promoción MultiUnit
	 * 
	 * @author Cesar Guel
	 * @param nomTienda El nombre de la tienda utilizada
	 * @date 06/04/2026
	 */
	public boolean ingresarSkuArticuloPrmMulti(String nomTienda) {
		try {
			String articuloMu = consultaSkuPrmMulti(nomTienda);

			WebElement buscador = au.driver.findElement(Buscador);
			testCase.addTextEvidenceCurrentStep("Tap en Botón Buscador de articulos");
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Ingresar el valor al buscador ");
			au.driver.hideKeyboard();
			buscador.sendKeys(articuloMu);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(3);

			testCase.addTextEvidenceCurrentStep("Tap boton Buscar");
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap boton Articulo");
			au.click(Articulo);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap boton Cantidad");
			au.click(btn_cantidad);
			au.click(btn_cantidad);
			au.click(btn_cantidad);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap boton calcular");
			au.click(btn_calcular);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			WebElement tvPrm = au.driver.findElement(LBPRM);
			String textoLBPRM = tvPrm.getText();

			if (textoLBPRM.isEmpty()) {
				System.out.println("No se encontro la promoción exitosamente");
				return false;
			} else {
				System.out.println("Se encontro la promoción exitosamente");
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Ingresar Sku de articulo en promoción MultiUnit
	 * 
	 * @author Cesar Guel
	 * @return True o Flase si se muestra el folio de la promocion
	 * @date 13/04/2026
	 */
	public boolean ingresarSkuArticuloPrmMultiD() {
		try {
			String articuloMu = consultaSkuPrmMultiD();

			WebElement buscador = au.driver.findElement(Buscador);
			testCase.addTextEvidenceCurrentStep("Tap en Botón Buscador de articulos");
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Ingresar el valor al buscador ");
			au.driver.hideKeyboard();
			buscador.sendKeys(articuloMu);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(3);

			testCase.addTextEvidenceCurrentStep("Tap boton Buscar");
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap boton Articulo");
			au.click(Articulo);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap boton Cantidad");
			au.click(btn_cantidad);
			au.click(btn_cantidad);
			au.click(btn_cantidad);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap boton calcular");
			au.click(btn_calcular);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			WebElement tvPrm = au.driver.findElement(LBPRM);
			String textoLBPRM = tvPrm.getText();

			if (textoLBPRM.isEmpty()) {
				System.out.println("No se encontro la promoción exitosamente");
				return false;
			} else {
				System.out.println("Se encontro la promoción exitosamente");
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Ingresar articulo en promoción MultiUnit
	 * 
	 * @author Cesar Guel
	 * @param crTienda El nombre de la plaza y tienda utilizado
	 * @date 26/03/2025
	 */
	public void ingresarArticuloPrmMultiJournal(String crTienda) {
		try {
			String articuloM = consultaSkuPrmMulti(crTienda);
			WebElement buscador = au.driver.findElement(Buscador);
			testCase.addTextEvidenceCurrentStep("Tap en Botón Buscador de articulos");
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Ingresar el valor al buscador ");
			au.driver.hideKeyboard();
			buscador.sendKeys(articuloM);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Ingresar Sku de articulo en promoción PromoPack
	 * 
	 * @author Cesar Guel
	 * @param crTienda El nombre de la plaza y tienda utilizado
	 * @date 26/03/2025
	 */
	public void ingresarSkuArticuloPrmPromoP(String nomTienda) {
		try {
			String articuloM = consultaSkuPrmPromoP(nomTienda);
			WebElement buscador = au.driver.findElement(Buscador);
			testCase.addTextEvidenceCurrentStep("Tap en Botón Buscador de articulos");
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Ingresar el valor al buscador ");
			au.driver.hideKeyboard();
			buscador.sendKeys(articuloM);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Ingresar articulo en promoción PromoPack a journal
	 * 
	 * @author Cesar Guel
	 * @param nomTienda El nombre de la tienda a utilizar
	 * @return True o False si se muestra el folio de la promocion
	 * @date 07/04/2026
	 */
	public boolean ingresarArticuloPrmPromoPackJournal(String nomTienda) {
		try {
			String articuloPp = consultaSkuPrmPromoP(nomTienda);

			WebElement buscador = au.driver.findElement(Buscador);
			testCase.addTextEvidenceCurrentStep("Tap en Botón Buscador de articulos");
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Ingresar el valor al buscador ");
			au.driver.hideKeyboard();
			buscador.sendKeys(articuloPp);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(3);

			testCase.addTextEvidenceCurrentStep("Tap boton Buscar");
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap boton Articulo");
			au.click(Articulo);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap boton Cantidad");
			au.click(btn_cantidad);
			au.click(btn_cantidad);
			au.click(btn_cantidad);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap boton calcular");
			au.click(btn_calcular);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			WebElement tvPrm = au.driver.findElement(LBPRM);
			String textoLBPRM = tvPrm.getText();

			if (textoLBPRM.isEmpty()) {
				System.out.println("No se encontro la promoción exitosamente");
				return false;
			} else {
				System.out.println("Se encontro la promoción exitosamente");
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean ingresarArticuloPrmPromoPackJournalV2(String nomTienda) {
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(60));
		try {
			String articuloPp = consultaSkuPrmPromoP(nomTienda);

			WebElement buscador = au.driver.findElement(Buscador);
			testCase.addTextEvidenceCurrentStep("Tap en Botón Buscador de articulos");
			wait.until(ExpectedConditions.elementToBeClickable(MBuscador));
			au.click(MBuscador);
			wait.until(ExpectedConditions.elementToBeClickable(Buscador));
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Ingresar el valor al buscador ");
			au.driver.hideKeyboard();
			buscador.sendKeys(articuloPp);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(3);

			testCase.addTextEvidenceCurrentStep("Tap boton Buscar");
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap boton Articulo");
			wait.until(ExpectedConditions.elementToBeClickable(Articulo));
			au.click(Articulo);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap boton Cantidad");
			wait.until(ExpectedConditions.elementToBeClickable(btn_cantidad));
			au.click(btn_cantidad);
			au.click(btn_cantidad);
			au.click(btn_cantidad);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap boton calcular");
			wait.until(ExpectedConditions.elementToBeClickable(btn_calcular));
			au.click(btn_calcular);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			wait.until(ExpectedConditions.elementToBeClickable(LBPRM));
			WebElement tvPrm = au.driver.findElement(LBPRM);
			String textoLBPRM = tvPrm.getText();

			if (textoLBPRM.isEmpty()) {
				System.out.println("No se encontro la promoción exitosamente");
				return false;
			} else {
				System.out.println("Se encontro la promoción exitosamente");
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Método - Ingresar articulo en promoción PromoPack a journal parametro
	 * dinamico
	 * 
	 * @author Cesar Guel
	 * @date 13/04/2026
	 */
	public void ingresarArticuloPrmPromoPackJournalD() {
		try {
			String articuloPp = consultaSkuPrmPromoPD();

			WebElement buscador = au.driver.findElement(Buscador);
			testCase.addTextEvidenceCurrentStep("Tap en Botón Buscador de articulos");
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Ingresar el valor al buscador ");
			au.driver.hideKeyboard();
			buscador.sendKeys(articuloPp);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(3);

			testCase.addTextEvidenceCurrentStep("Tap boton Buscar");
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap boton Articulo");
			au.click(Articulo);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap boton Cantidad");
			au.click(btn_cantidad);
			au.click(btn_cantidad);
			au.click(btn_cantidad);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap boton calcular");
			au.click(btn_calcular);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Ingresar Sku de articulo en promoción Hide
	 * 
	 * @author Cesar Guel
	 * @param crTienda El nombre de la plaza y tienda utilizado
	 * @date 26/03/2025
	 */
	public void ingresarSkuArticuloPrmHide(String nomTienda) {
		try {
			WebElement buscador = au.driver.findElement(Buscador);
			String articuloM = consultaSkuPrmHide(nomTienda);
			testCase.addTextEvidenceCurrentStep("Tap en Botón Buscador de articulos");
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Ingresar el valor al buscador ");
			au.driver.hideKeyboard();
			buscador.sendKeys(articuloM);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Ingresar articulo en promocion Hide al Journal
	 * 
	 * @author Cesar Guel
	 * @param nomTienda El nombre de la tienda a utilizar
	 * @return Retorna true si se encontro la promocion
	 * @date 07/04/2026
	 */
	public boolean ingresarArticuloPrmHideJournal(String nomTienda) {
		try {

			String ArtPrm = consultaSkuPrmHide(nomTienda);
			WebElement buscador = au.driver.findElement(Buscador);

			testCase.addTextEvidenceCurrentStep("Tap boton Buscador");
			au.click(MBuscador);
			au.click(Buscador);
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Se ingresa dato Articulo en Promoción");
			buscador.sendKeys(ArtPrm);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap boton Buscar");
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap boton Articulo");
			au.click(Articulo);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap boton Cantidad");
			au.click(btn_cantidad);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap boton calcular");
			au.click(btn_calcular);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			WebElement tvPrm = au.driver.findElement(LBPRM);
			String textoLBPRM = tvPrm.getText();

			if (textoLBPRM.isEmpty()) {
				System.out.println("No se encontro la promoción exitosamente");
				return false;
			} else {
				System.out.println("Se encontro la promoción exitosamente");
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean ingresarArticuloPrmHideJournalV2(String nomTienda) {
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(60));
		try {

			String ArtPrm = consultaSkuPrmHide(nomTienda);
			WebElement buscador = au.driver.findElement(Buscador);

			testCase.addTextEvidenceCurrentStep("Tap boton Buscador");
			wait.until(ExpectedConditions.elementToBeClickable(MBuscador));
			au.click(MBuscador);
			wait.until(ExpectedConditions.elementToBeClickable(Buscador));
			au.click(Buscador);
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Se ingresa dato Articulo en Promoción");
			buscador.sendKeys(ArtPrm);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap boton Buscar");
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap boton Articulo");
			wait.until(ExpectedConditions.elementToBeClickable(Articulo));
			au.click(Articulo);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap boton Cantidad");
			wait.until(ExpectedConditions.elementToBeClickable(btn_cantidad));
			au.click(btn_cantidad);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap boton calcular");
			wait.until(ExpectedConditions.elementToBeClickable(btn_calcular));
			au.click(btn_calcular);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			wait.until(ExpectedConditions.elementToBeClickable(LBPRM));
			WebElement tvPrm = au.driver.findElement(LBPRM);
			String textoLBPRM = tvPrm.getText();

			if (textoLBPRM.isEmpty()) {
				System.out.println("No se encontro la promoción exitosamente");
				return false;
			} else {
				System.out.println("Se encontro la promoción exitosamente");
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Ingresar articulo en promocion Hide al Journal parametros dinamicos
	 * 
	 * @author Cesar Guel
	 * @return Retorna true si se encontro la promocion
	 * @date 13/04/2026
	 */
	public boolean ingresarArticuloPrmHideJournalD() {
		try {

			String ArtPrm = consultaSkuPrmHideD();
			WebElement buscador = au.driver.findElement(Buscador);

			testCase.addTextEvidenceCurrentStep("Tap boton Buscador");
			au.click(MBuscador);
			au.click(Buscador);
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Se ingresa dato Articulo en Promoción");
			buscador.sendKeys(ArtPrm);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap boton Buscar");
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap boton Articulo");
			au.click(Articulo);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap boton Cantidad");
			au.click(btn_cantidad);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap boton calcular");
			au.click(btn_calcular);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			WebElement tvPrm = au.driver.findElement(LBPRM);
			String textoLBPRM = tvPrm.getText();

			if (textoLBPRM.isEmpty()) {
				System.out.println("No se encontro la promoción exitosamente");
				return false;
			} else {
				System.out.println("Se encontro la promoción exitosamente");
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Ingresar Sku de articulo en promoción MBG
	 * 
	 * @author Cesar Guel
	 * @param crTienda El nombre de la plaza y tienda utilizado
	 * @date 26/03/2025
	 */
	public void ingresarSkuArticuloPrmMBG(String nomTienda) {
		try {
			WebElement buscador = au.driver.findElement(Buscador);
			String articuloM = consultaSkuPrmMBG(nomTienda);
			testCase.addTextEvidenceCurrentStep("Tap en Botón Buscador de articulos");
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Ingresar el valor al buscador ");
			au.driver.hideKeyboard();
			buscador.sendKeys(articuloM);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Ingresar articulo en promocion MBG al Journal
	 * 
	 * @author Cesar Guel
	 * @param nomTienda El nombre de la tienda a utilizar
	 * @return Retorna true si se encontro la promocion
	 * @date 07/04/2026
	 */
	public boolean ingresarArticuloPrmMBGJournal(String nomTienda) {
		try {

			String ArtPrm = consultaSkuPrmMBG(nomTienda);
			WebElement buscador = au.driver.findElement(Buscador);

			testCase.addTextEvidenceCurrentStep("Tap boton Buscador");
			au.click(MBuscador);
			au.click(Buscador);
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Se ingresa dato Articulo en Promoción");
			buscador.sendKeys(ArtPrm);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap boton Buscar");
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap boton Articulo");
			au.click(Articulo);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap boton Cantidad");
			au.click(btn_cantidad);
			au.click(btn_cantidad);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap boton calcular");
			au.click(btn_calcular);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			WebElement tvPrm = au.driver.findElement(LBPRM);
			String textoLBPRM = tvPrm.getText();

			if (textoLBPRM.isEmpty()) {
				System.out.println("No se encontro la promoción exitosamente");
				return false;
			} else {
				System.out.println("Se encontro la promoción exitosamente");
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Ingresar articulo en promocion MBG al Journal parametro dinamico
	 * 
	 * @author Cesar Guel
	 * @return Retorna true si se encontro la promocion
	 * @date 13/04/2026
	 */
	public boolean ingresarArticuloPrmMBGJournalD() {
		try {

			String ArtPrm = consultaSkuPrmMBGD();
			WebElement buscador = au.driver.findElement(Buscador);

			testCase.addTextEvidenceCurrentStep("Tap boton Buscador");
			au.click(MBuscador);
			au.click(Buscador);
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Se ingresa dato Articulo en Promoción");
			buscador.sendKeys(ArtPrm);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap boton Buscar");
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap boton Articulo");
			au.click(Articulo);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap boton Cantidad");
			au.click(btn_cantidad);
			au.click(btn_cantidad);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap boton calcular");
			au.click(btn_calcular);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			WebElement tvPrm = au.driver.findElement(LBPRM);
			String textoLBPRM = tvPrm.getText();

			if (textoLBPRM.isEmpty()) {
				System.out.println("No se encontro la promoción exitosamente");
				return false;
			} else {
				System.out.println("Se encontro la promoción exitosamente");
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Ingresar Sku de articulo en promoción Pivote
	 * 
	 * @author Cesar Guel
	 * @param nomTienda El nombre de la tienda a utilizar
	 * @date 19/06/2025
	 */
	public void ingresarArticuloPrmPivote(String nomTienda) {
		try {
			String articuloM = consultaSkuPrmPivote(nomTienda);
			String articuloM2 = consultaSkuPrmPivote2(nomTienda);
			WebElement buscador = au.driver.findElement(Buscador);

			testCase.addTextEvidenceCurrentStep("Tap en Botón Buscador de articulos");
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Ingresar el valor al buscador ");
			buscador.sendKeys(articuloM);
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap en el boton Buscar");
			TimeUnit.SECONDS.sleep(5);
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap en Botón Buscador de articulos");
			au.driver.hideKeyboard();
			au.click(btn_cantidad);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap en Botón Buscador de articulos");
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Ingresar el valor al buscador ");
			buscador.sendKeys(articuloM2);
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap en el boton Buscar");
			TimeUnit.SECONDS.sleep(5);
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap en Botón Buscador de articulos");
			au.driver.hideKeyboard();
			au.click(btn_cantidad);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Ingresar Sku de articulo en promoción Pivote
	 * 
	 * @author Cesar Guel
	 * @param nomTienda El nombre de la tienda a utilizar
	 * @date 15/04/2026
	 */
	public void ingresarArticuloPrmPivoteD() {
		try {
			String articuloM = consultaSkuPrmPivoteD();
			String articuloM2 = consultaSkuPrmPivote2D();
			WebElement buscador = au.driver.findElement(Buscador);

			testCase.addTextEvidenceCurrentStep("Tap en Botón Buscador de articulos");
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Ingresar el valor al buscador ");
			buscador.sendKeys(articuloM);
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap en el boton Buscar");
			TimeUnit.SECONDS.sleep(5);
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap en Botón Buscador de articulos");
			au.driver.hideKeyboard();
			au.click(btn_cantidad);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap en Botón Buscador de articulos");
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Ingresar el valor al buscador ");
			buscador.sendKeys(articuloM2);
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap en el boton Buscar");
			TimeUnit.SECONDS.sleep(5);
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap en Botón Buscador de articulos");
			au.driver.hideKeyboard();
			au.click(btn_cantidad);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Ingresar sku de articulo bebida alcoholica
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @date 10/09/2024
	 *
	 */
	public void ingresarSkuArticuloBAJournal(String ipPv) {
		try {
			String articuloBA = ConsultaArticuloBA(ipPv);
			WebElement buscador = au.driver.findElement(Buscador);

			testCase.addTextEvidenceCurrentStep("Tap boton Buscador");
			au.click(MBuscador);
			au.driver.hideKeyboard();
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Ingresar SKU del articulo");
			buscador.sendKeys(articuloBA);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método - Ingresar sku de articulo bebida alcoholica con valor dinamico
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @date 05/05/2026
	 *
	 */
	public void ingresarSkuArticuloBAJournalD() {
		try {
			String articuloBA = ConsultaArticuloBADB();
			WebElement buscador = au.driver.findElement(Buscador);

			testCase.addTextEvidenceCurrentStep("Tap boton Buscador");
			au.click(MBuscador);
			au.driver.hideKeyboard();
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Ingresar SKU del articulo");
			buscador.sendKeys(articuloBA);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Métdo - Ingresar articulo al journal
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @date 28/01/2025
	 */
	public void ingresarArtJournal(String ipPv) {
		try {
			String articulo = ConsultaArticulo(ipPv);
			WebElement buscador = au.driver.findElement(Buscador);

			testCase.addTextEvidenceCurrentStep("Tap en Botón Buscador de articulos");
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Ingresar el valor al buscador ");
			au.driver.hideKeyboard();
			buscador.sendKeys(articulo);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap en el boton Buscar");
			TimeUnit.SECONDS.sleep(5);
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap en el articulo");
			au.click(btn_cantidad);
			testCase.addScreenShotCurrentStep(au, "testImage");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Ingresar articulo bebida alcoholica al journal
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @date 28/01/2025
	 */
	public void ingresarArtBaJournal(String ipPv) {
		try {
			String articuloBA = ConsultaArticuloBA(ipPv);
			WebElement buscador = au.driver.findElement(Buscador);

			testCase.addTextEvidenceCurrentStep("Tap boton Buscador");
			au.click(MBuscador);
			au.driver.hideKeyboard();
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Ingresar SKU del articulo");
			buscador.sendKeys(articuloBA);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap en el boton Buscar");
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap en el articulo");
			au.click(btn_cantidad);
			testCase.addScreenShotCurrentStep(au, "testImage");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Ingresar articulo en promoción al journal
	 * 
	 * @author Cesar Guel
	 * @param nomTienda El nombre de la tienda a utilizar
	 * @date 28/01/2025
	 */
	public void ingresarArtPrmJournal(String nomTienda) {
		try {
			WebElement buscador = au.driver.findElement(Buscador);
			String articuloprm = consultaSkuArtPrmN(nomTienda);

			testCase.addTextEvidenceCurrentStep("Tap boton Buscador");
			waitForVisibility(MBuscador, Duration.ofSeconds(20));
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Ingresar SKU del articulo");
			buscador.sendKeys(articuloprm);
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap en el boton Buscar");
			au.driver.executeScript("mobile:performEditorAction", ImmutableMap.of("action", "search"));
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap en el articulo");
			au.click(btn_cantidad);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Validar mensaje bebida alcoholica dentro del horario permitido
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si el elemento esta Desplegado o Habilitado
	 * @date 10/09/2024
	 *
	 */
	public boolean validarMensajeBebidaAlcoholica() {
		try {
			WebElement msgArticuloBE = au.driver.findElement(msgArticuloBEDH);

			if (msgArticuloBE.isDisplayed() || msgArticuloBE.isEnabled()) {
				testCase.addTextEvidenceCurrentStep("Ingresar articulo bebida alcohólica al Journal");
				System.out.println("Se encontró el mensaje de manera exitosa");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} else {
				testCase.addScreenShotCurrentStep(au, "testImage");
				System.out.println("No se encontró el mensaje a tiempo.");
				return false;
			}

		} catch (Exception e) {
			System.out.println("Hubo algun error al realizar la busqueda");
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Método - Validar mensaje bebida alcoholica fuera del horario permitido
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si el elemento esta Desplegado
	 * @date 10/09/2024
	 */
	public boolean validarMensajeBbeidaAlcoholicaFO() {
		try {
			WebElement msgArticuloBAFO = au.driver.findElement(msgArticuloBEFO);
			if (msgArticuloBAFO.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep("Se valida mensaje bebida alcoholica fuera de horario");
				System.out.println("Se encontro de forma exitosa");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} else {
				testCase.addScreenShotCurrentStep(au, "testImage");
				return false;
			}
		} catch (Exception e) {
			testCase.addScreenShotCurrentStep(au, "testImage");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Busqueda de elemento con Presencia Bebida alcoholica Fuera de
	 * Horario
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si se encuentra el elemento
	 * @date 07/08/2025
	 */
	public boolean busquedaConPresenciaBA() {
		try {
			System.out.println("Inicia busqueda con presencia (sin verificar visibilidad)");

			Thread.sleep(100);

			System.out.println("Buscando elemento Toast presente por 3 segundos");
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(10));

			WebElement msjYaExisteServicio = wait.until(ExpectedConditions.presenceOfElementLocated(msgArticuloBEFO));

			System.out.println("Busqueda con presencia exitosa, mensaje Toast encontrado");

			System.out.println("Elemento Toast detectado");
			testCase.addTextEvidenceCurrentStep("Se detectó el mensaje Toast exitosamente");
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);
			return true;

		} catch (TimeoutException e) {
			System.out.println(
					"Error al realizar la busqueda por presencia, elemento Toast no presente después de 3 segundos");
			e.printStackTrace();
			return false;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Busqueda de elemento con Presencia Bebida alcoholica dentro de
	 * horario
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si se encuentra el elemento
	 * @date 06/04/2026
	 */
	public boolean busquedaConPresenciaBADE() {
		try {
			System.out.println("Inicia busqueda con presencia (sin verificar visibilidad)");

			Thread.sleep(100);

			System.out.println("Buscando elemento Toast presente por 3 segundos");
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(3));

			WebElement msjYaExisteServicio = wait.until(ExpectedConditions.presenceOfElementLocated(msgArticuloBEDH));

			System.out.println("Busqueda con presencia exitosa, mensaje Toast encontrado");

			System.out.println("Elemento Toast detectado");
			testCase.addTextEvidenceCurrentStep("Se detectó el mensaje Toast exitosamente");
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);
			return true;

		} catch (TimeoutException e) {
			System.out.println(
					"Error al realizar la busqueda por presencia, elemento Toast no presente después de 3 segundos");
			e.printStackTrace();
			return false;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Validar que no se agregue articulo Bebida Alcoholica en el journal
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si no se agrega un articulo
	 * @date 29/01/2025
	 */
	public boolean validarNoArtJournal() {
		try {

			List<WebElement> articulos = au.driver.findElements(Articulo);

			if (articulos.size() == 1) {
				testCase.addTextEvidenceCurrentStep("Se valida que no se agregue articulo al journal");
				System.out.println("No se agrego articulo al journal");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} else {
				System.out.println("Se agrego " + articulos.size() + " BA al journal");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			testCase.addScreenShotCurrentStep(au, "testImage");
			return false;
		}
	}

	/**
	 * Método - Buscar articulo
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 06/09/2024
	 *
	 */
	public void buscarArticulo() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Buscar");
			au.click(Buscador);
			TimeUnit.SECONDS.sleep(5);
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error, Ingresar el artiulo al Journal");
			e.printStackTrace();
		}
	}

	/**
	 * Método - Buscar articulo
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 06/09/2024
	 *
	 */
	public void buscarArticuloP() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Buscar");
			au.click(Buscador);
			TimeUnit.SECONDS.sleep(5);
			au.driver.executeScript("mobile:performEditorAction", ImmutableMap.of("action", "search"));
			testCase.addScreenShotCurrentStep(au, "testImage");

		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error, Ingresar el artiulo al Journal");
			e.printStackTrace();
		}
	}

	/**
	 * Método - Buscar articulo
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 06/09/2024
	 *
	 */
	public void buscarArticuloFF() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Buscar");
			au.click(Buscador);
			TimeUnit.SECONDS.sleep(5);
			au.driver.executeScript("mobile:performEditorAction", ImmutableMap.of("action", "search"));
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error, Ingresar el artiulo al Journal");
			e.printStackTrace();
		}
	}

	/**
	 * Método - Seleccionar articulo
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 06/09/2024
	 *
	 */
	public void seleccionarArticulo() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el articulo");
			waitForVisibility(btn_cantidad, Duration.ofSeconds(20));
			au.click(btn_cantidad);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error, Ingresar el artiulo al Journal");
			e.printStackTrace();
		}
	}

	/**
	 * Método - Ingresar cantidad articulos
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 06/09/2024
	 *
	 */
	public void seleccionarCantidadArticulo() {

		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton cantidad");
			au.click(btn_cantidad);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, Ingresar el artiulo al Journal");
		}
	}

	/**
	 * Método - Ingresar cantidad articulos Promoción MultiUnit
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 11/06/2025
	 *
	 */
	public void seleccionarCantidadArticuloMultiUnit() {

		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton cantidad");
			au.click(btn_cantidad);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap en el boton cantidad");
			au.click(btn_cantidad);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, Ingresar el artiulo al Journal");
		}
	}

	/*
	 * Método - Ingresar cantidad articulos Promoción PromoPack
	 * 
	 * @author Cesar Guel
	 * 
	 * @param N/A
	 * 
	 * @date 11/06/2025
	 *
	 */
	public void seleccionarCantidadArticuloPromoP() {

		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton cantidad");
			au.click(btn_cantidad);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap en el boton cantidad");
			au.click(btn_cantidad);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, Ingresar el artiulo al Journal");
		}
	}

	/**
	 * Método - Validar Promoción de articulo en journal
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si el elemento tiene el texto de la promoción
	 * @date 09/09/2024
	 *
	 */
	public boolean validarArticuloPrmJournal() {
		try {
			WebElement tvPrm = au.driver.findElement(LBPRM);
			String textoLBPRM = tvPrm.getText();
			if (textoLBPRM.isEmpty()) {
				testCase.addTextEvidenceCurrentStep("Error: El elemento LBPRM no tiene texto.");
				return false;
			} else {
				testCase.addTextEvidenceCurrentStep("Validación exitosa: LBPRM tiene texto -> " + textoLBPRM);
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(10);
				return true;
			}

		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error al validar LBPRM: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Método - seleccionar boton calcular
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 06/09/2024
	 *
	 */
	public void seleccionarBotonCalcular() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Calcular");
			au.click(btn_calcular);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, Ingresar el artiulo al Journal");
		}
	}

	/**
	 * Método - Seleccionar boton total a pagar
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 06/09/2024
	 *
	 */
	public void seleccionarBotonTotalPagar() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton total");
			TimeUnit.SECONDS.sleep(10);
			au.click(btn_total);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, Ingresar el artiulo al Journal");
		}
	}

	/**
	 * Método - Totalizar articulo de venta
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 06/09/2024
	 *
	 */
	public void totalizarArticulo() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Calcular");
			waitForVisibility(btn_calcular, Duration.ofSeconds(20));
			au.click(btn_calcular);
			System.out.println("Se presiona el boton calcular");
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);

			testCase.addTextEvidenceCurrentStep("Tap en el boton total");
			waitForVisibility(btn_total, Duration.ofSeconds(20));
			au.click(btn_total);
			System.out.println("Se presiona el boton Total a pagar");
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);

		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, Ingresar el artiulo al Journal");
		}
	}

	/**
	 * Método - Ingresar Articulo al Journal
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @date 06/09/2024
	 *
	 */
	public void ingresarArticuloJournal(String ipPv) {
		try {

			String articulo = ConsultaArticulo(ipPv);
			WebElement buscador = au.driver.findElement(Buscador);
			testCase.addTextEvidenceCurrentStep("Tap en Botón Buscador de articulos");
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Ingresar el valor al buscador ");
			au.driver.hideKeyboard();
			buscador.sendKeys(articulo);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap en el boton Buscar");
			TimeUnit.SECONDS.sleep(5);
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap en el articulo");
			au.click(Articulo);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap en el boton cantidad");
			au.click(btn_cantidad);
			testCase.addScreenShotCurrentStep(au, "testImage");

		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error, Ingresar el artiulo al Journal");
		}

	}
	
	/**
	 * Método - Ingresar Articulo al Journal con valor dinamico
	 * 
	 * @author Cesar Guel
	 * @date 04/05/2026
	 *
	 */
	public void ingresarArticuloJournalD() {
		try {

			String articulo = ConsultaArticuloD();
			WebElement buscador = au.driver.findElement(Buscador);
			testCase.addTextEvidenceCurrentStep("Tap en Botón Buscador de articulos");
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Ingresar el valor al buscador ");
			au.driver.hideKeyboard();
			buscador.sendKeys(articulo);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap en el boton Buscar");
			TimeUnit.SECONDS.sleep(5);
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap en el articulo");
			au.click(Articulo);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap en el boton cantidad");
			au.click(btn_cantidad);
			testCase.addScreenShotCurrentStep(au, "testImage");

		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error, Ingresar el artiulo al Journal");
		}

	}

	/**
	 * Método - Seleccionar metodo de pago efectivo
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 06/09/2024
	 *
	 */
	public void seleccionarMetodoDePagoEfectivo() {
		try {

			testCase.addTextEvidenceCurrentStep("Tap en el boton Efectivo");
			TimeUnit.SECONDS.sleep(10);
			au.click(btn_efectivo);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Confirmar metodo de pago efectivo
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 06/09/2024
	 *
	 */
	public void confirmarMetodoDePagoEfectivo() {
		try {

			TimeUnit.SECONDS.sleep(10);

			testCase.addTextEvidenceCurrentStep("Tap en el boton Aplicar");
			au.click(btn_aplicar);
			testCase.addScreenShotCurrentStep(au, "testImage");

			TimeUnit.SECONDS.sleep(30);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Confirmar metodo de pago efectivo con menos espera
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 06/09/2024
	 *
	 */
	public void confirmarMetodoDePagoEfectivoC() {
		try {

			TimeUnit.SECONDS.sleep(10);

			testCase.addTextEvidenceCurrentStep("Tap en el boton Aplicar");
			au.click(btn_aplicar);
			testCase.addScreenShotCurrentStep(au, "testImage");

			TimeUnit.SECONDS.sleep(13);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Validar venta con metodo de pago efectivo exitosa
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si el elemento esta Desplegado
	 * @date 06/09/2024
	 *
	 */
	public boolean validarVentaExitosa() {
		try {
			waitForVisibility(Journal, Duration.ofSeconds(30));

			if (isElementPresent(Journal)) {
				System.out.println("La venta fue exitosa");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(10);
				return true;

			} else {
				System.out.println("La venta no fue exitosa");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Validar ticket digital de venta exitosa
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si el elemento esta Desplegado
	 * @date 28/01/2025
	 */
	public boolean validarMsjVtaExitosa() {
		try {

			WebElement msjvtaexi = au.driver.findElement(pantTicketDigital);
			if (msjvtaexi.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep("Se valida que se muestre la pantalla de ticket digital");
				System.out.println("Se encontro el ticket de manera correcta");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} else {
				testCase.addScreenShotCurrentStep(au, "testImage");
				System.out.println("No se encontro la pantalla de venta exitosa");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addScreenShotCurrentStep(au, "testImage");
			return false;
		}
	}

	/**
	 * Método -Busqueda de elemento con Presencia Venta exitosa
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si se encuentra el elemento
	 * @date 18/08/2025
	 */
	public boolean busquedaConPresenciaVentaExitosa() {
		try {
			System.out.println("Inicia busqueda con presencia");
			System.out.println("Buscando elemento ventana ticket digital presente por 3 segundos");
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(3));

			WebElement msjYaExisteServicio = wait.until(ExpectedConditions.presenceOfElementLocated(pantTicketDigital));

			System.out.println("Busqueda con presencia exitosa, pantalla encontrada");
			testCase.addTextEvidenceCurrentStep("Se detectó el mensaje Toast exitosamente");
			testCase.addScreenShotCurrentStep(au, "testImage_estrategia2");
			return true;

		} catch (TimeoutException e) {
			System.out.println(
					"Error al realizar la busqueda por presencia, pantallat no presente después de 3 segundos");
			e.printStackTrace();
			return false;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Validar mensaje donde indica que no se puede agregar otro servicio
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si se encuentra el elemento por visibilidad o presencia
	 * @date 06/08/2025
	 */
	public boolean validarMsjNoAgrServicio() {
		System.out.println("Iniciando validación de mensaje 'YA EXISTE SERVICIO'");

		try {
			// Tipo de validación por visibilidad
			if (busquedaConVisibilidad()) {
				return true;
			}

			// Tipo de validación por presencia
			if (busquedaConPresencia()) {
				return true;
			}

			// Si ambas fallan
			System.out.println("El elemento no fue encontrado exitosamente");
			return false;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Ocurrio un error al validar el mensaje: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Método - Busqueda de elemento por Visibilidad
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si se encuentra el elemento
	 * @date 06/08/2025
	 */
	private boolean busquedaConVisibilidad() {
		try {
			System.out.println("Inicia busqueda con visibilidad");

			System.out.println("Ejecutando click en btn_servicios");
			au.click(btn_servicios);
			System.out.println("Click ejecutado exitosamente");

			Thread.sleep(300);

			System.out.println("Buscando elemento Toast visible por 3 segundos");
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(3));

			WebElement msjYaExisteServicio = wait
					.until(ExpectedConditions.visibilityOfElementLocated(txtYaExisteServicio));

			System.out.println("Busqueda por visibilidad exitosa, Mensaje Toast encontrado y visible");
			testCase.addTextEvidenceCurrentStep("Se valida que se muestra el mensaje Toast exitosamente");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;

		} catch (TimeoutException e) {
			System.out.println("Busqueda por visibilidad no exitosa, Toast no visible después de 3 segundos");
			e.printStackTrace();
			return false;

		} catch (Exception e) {
			System.out.println("Error al realizar la busqueda por visibilidad " + e.getMessage());
			return false;
		}
	}

	/**
	 * Método -Busqueda de elemento con Presencia
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si se encuentra el elemento
	 * @date 06/08/2025
	 */
	private boolean busquedaConPresencia() {
		try {
			System.out.println("Inicia busqueda con presencia (sin verificar visibilidad)");

			System.out.println("Ejecutando click en btn_servicios");
			au.click(btn_servicios);
			System.out.println("Click ejecutado exitosamente");

			Thread.sleep(300);

			System.out.println("Buscando elemento Toast presente por 3 segundos");
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(3));

			WebElement msjYaExisteServicio = wait
					.until(ExpectedConditions.presenceOfElementLocated(txtYaExisteServicio));

			System.out.println("Busqueda con presencia exitosa, mensaje Toast encontrado");

			System.out.println("Elemento Toast detectado");
			testCase.addTextEvidenceCurrentStep("Se detectó el mensaje Toast exitosamente");
			testCase.addScreenShotCurrentStep(au, "testImage_estrategia2");
			return true;

		} catch (TimeoutException e) {
			System.out.println(
					"Error al realizar la busqueda por presencia, elemento Toast no presente después de 3 segundos");
			e.printStackTrace();
			return false;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Seleccionar boton identificar cliente spin premia por numero de
	 * telefono
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 09/09/2024
	 *
	 */
	public void seleccionarBotonSpinPremiaNT() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap boton Identificar cliente");
			TimeUnit.SECONDS.sleep(5);
			au.driver.findElement(btn_idCliente);
			au.click(btn_idCliente);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Identificación de usuario Spin Premia por medio de numero de
	 * telefono
	 * 
	 * @author Cesar Guel
	 * @param telefono El numero de telefono del cliente
	 * @date 09/09/2024
	 *
	 */
	public void identificacionSpinNT(String telefono) {
		try {

			testCase.addTextEvidenceCurrentStep("Ingresar el numero de usuario");
			WebElement btn_idCliente = au.driver.findElement(etCliente);
			btn_idCliente.sendKeys(telefono);
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Se presiona el boton aplicar");
			au.click(btn_aplicarNTIC);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(15);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Confirmar el uso del numero de telefono
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 09/09/2024
	 *
	 */
	public void confirmarNT() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap boton aplicarSpin");
			au.click(btn_AplicarSpin);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Validar si el usuario Spin Premia se Logeo de manera correcta
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si el elemento esta Habilitado
	 * @date 09/09/2024
	 *
	 */
	public boolean identificacionSpinNTSE() {
		try {
			testCase.addTextEvidenceCurrentStep("Identificar estado del botón 'etCliente'");
			WebElement btnNumeroCliente = au.driver.findElement(btn_idCliente);

			if (!btnNumeroCliente.isEnabled()) {
				System.out.println("El botón está deshabilitado como se esperaba.");
				testCase.addTextEvidenceCurrentStep("El botón está deshabilitado como se esperaba.");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(10);
				return true;
			} else {
				System.err.println("El botón está habilitado.");
				testCase.addTextEvidenceCurrentStep("El botón está habilitado.");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return false;
			}

		} catch (NoSuchElementException e) {
			System.err.println("El botón no se encontró: " + e.getMessage());
			testCase.addTextEvidenceCurrentStep("Error: El botón no se encontró. Detalles: " + e.getMessage());
			testCase.addScreenShotCurrentStep(au, "testImage");
			return false;
		} catch (Exception e) {
			System.err.println("Ocurrió un error inesperado: " + e.getMessage());
			testCase.addTextEvidenceCurrentStep("Error inesperado: " + e.getMessage());
			testCase.addScreenShotCurrentStep(au, "testImage");
			return false;
		}
	}

	/**
	 * Método - Ingresar Articulo en promoción Simple al buscador
	 * 
	 * @author Cesar Guel
	 * @param nomTienda El nombre de la tienda a utilizar
	 * @date 09/09/2024
	 *
	 */
	public void ingresarSkuArtPrmJournal(String nomTienda) {
		try {

			String ArtPrm = consultaSkuArtPrmN(nomTienda);
			WebElement buscador = au.driver.findElement(Buscador);
			System.out.println("Se encontro el buscador exitosamente");

			testCase.addTextEvidenceCurrentStep("Tap boton Buscador");
			au.driver.hideKeyboard();
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Se ingresa dato Articulo en Promoción");
			buscador.sendKeys(ArtPrm);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Ingresar Articulo en promoción Simple al buscador
	 * 
	 * @author Cesar Guel
	 * @param nomTienda El nombre de la tienda a utilizar
	 * @date 09/09/2024
	 *
	 */
	public void ingresarSkuArtPrmJournalCiclos(String nomTienda) {
		try {

			String ArtPrm = consultaSkuArtPrmCiclo(nomTienda);
			WebElement buscador = au.driver.findElement(Buscador);
			System.out.println("Se encontro el buscador exitosamente");

			testCase.addTextEvidenceCurrentStep("Tap boton Buscador");
			au.driver.hideKeyboard();
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Se ingresa dato Articulo en Promoción");
			buscador.sendKeys(ArtPrm);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Ingresar Sku de articulo en promoción Simple al Journal
	 * 
	 * @author Cesar Guel
	 * @param nomTienda El nombre de la tienda a utilizar
	 * @return Retorna true si se encontro la promocion
	 * @date 10/06/2025
	 */
	public boolean ingresarArticuloPrmJournal(String nomTienda) {
		try {

			String ArtPrm = consultaSkuArtPrmN(nomTienda);
			WebElement buscador = au.driver.findElement(Buscador);

			testCase.addTextEvidenceCurrentStep("Tap boton Buscador");
			au.click(MBuscador);
			au.click(Buscador);
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Se ingresa dato Articulo en Promoción");
			buscador.sendKeys(ArtPrm);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap boton Buscar");
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap boton Articulo");
			au.click(Articulo);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap boton Cantidad");
			au.click(btn_cantidad);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap en el boton Calcular");
			au.click(btn_calcular);
			testCase.addScreenShotCurrentStep(au, "testImage");

			WebElement tvPrm = au.driver.findElement(LBPRM);
			String textoLBPRM = tvPrm.getText();

			if (textoLBPRM.isEmpty()) {
				System.out.println("No se encontro la promoción exitosamente");
				return false;
			} else {
				System.out.println("Se encontro la promoción exitosamente");
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean ingresarArticuloPrmJournalV2(String nomTienda) {
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(40));
		try {
			String ArtPrm = consultaSkuArtPrmN(nomTienda);
			WebElement buscador = au.driver.findElement(Buscador);

			testCase.addTextEvidenceCurrentStep("Tap boton Buscador");
			wait.until(ExpectedConditions.elementToBeClickable(MBuscador));
			au.click(MBuscador);
			wait.until(ExpectedConditions.elementToBeClickable(Buscador));
			au.click(Buscador);
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Se ingresa dato Articulo en Promoción");
			buscador.sendKeys(ArtPrm);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap boton Buscar");
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap boton Articulo");
			wait.until(ExpectedConditions.elementToBeClickable(Articulo));
			au.click(Articulo);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap boton Cantidad");
			wait.until(ExpectedConditions.elementToBeClickable(btn_cantidad));
			au.click(btn_cantidad);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap en el boton Calcular");
			wait.until(ExpectedConditions.elementToBeClickable(btn_calcular));
			au.click(btn_calcular);
			testCase.addScreenShotCurrentStep(au, "testImage");

			wait.until(ExpectedConditions.elementToBeClickable(LBPRM));
			WebElement tvPrm = au.driver.findElement(LBPRM);
			String textoLBPRM = tvPrm.getText();

			if (textoLBPRM.isEmpty()) {
				System.out.println("No se encontro la promoción exitosamente");
				return false;
			} else {
				System.out.println("Se encontro la promoción exitosamente");
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			testCase.nextStep(
					"Error: No se pudo ingresar Articulo Promocion al Journal. se obtiene el siguiente mensaje de Exception"
							+ e.getMessage());
			return false;
		}
	}

	/**
	 * Método - Ingresar Sku de articulo en promoción Simple al Journal Dinamico
	 * 
	 * @author Cesar Guel
	 * @param nomTienda El nombre de la tienda a utilizar
	 * @return Retorna true si se encontro la promocion
	 * @date 13/04/2026
	 */
	public boolean ingresarArticuloPrmJournalD() {
		try {

			String ArtPrm = consultaSkuArtPrmND();
			WebElement buscador = au.driver.findElement(Buscador);

			testCase.addTextEvidenceCurrentStep("Tap boton Buscador");
			au.click(MBuscador);
			au.click(Buscador);
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Se ingresa dato Articulo en Promoción");
			buscador.sendKeys(ArtPrm);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap boton Buscar");
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap boton Articulo");
			au.click(Articulo);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap boton Cantidad");
			au.click(btn_cantidad);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap en el boton Calcular");
			au.click(btn_calcular);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			WebElement tvPrm = au.driver.findElement(LBPRM);
			String textoLBPRM = tvPrm.getText();

			if (textoLBPRM.isEmpty()) {
				System.out.println("No se encontro la promoción exitosamente");
				return false;
			} else {
				System.out.println("Se encontro la promoción exitosamente");
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Ingresar Articulo bebida alcoholica en journal
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @date 06/04/2026
	 *
	 */
	public void ingresarSkuArtBAJournal(String ipPv) {
		try {

			String articuloBA = ConsultaArticuloBA(ipPv);
			WebElement buscador = au.driver.findElement(Buscador);
			System.out.println("Se encontro el buscador exitosamente");

			testCase.addTextEvidenceCurrentStep("Tap boton Buscador");
			au.driver.hideKeyboard();
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Se ingresa dato articulo bebida alcoholica");
			buscador.sendKeys(articuloBA);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap boton Buscar");
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			TimeUnit.SECONDS.sleep(5);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap boton Cantidad");
			au.click(btn_cantidad);
			testCase.addScreenShotCurrentStep(au, "testImage");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Ingresar Articulo bebida alcoholica en journal fuera de horario No
	 * se espera ver el botón de Cantidad
	 * 
	 * @author Juan Ibarra
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @date 15/04/2026
	 *
	 */

	public void ingresarSkuArtBAJournalMensaje(String ipPv) {
		try {

			String articuloBA = ConsultaArticuloBA(ipPv);
			WebElement buscador = au.driver.findElement(Buscador);
			System.out.println("Se encontro el buscador exitosamente");

			testCase.addTextEvidenceCurrentStep("Tap boton Buscador");
			au.driver.hideKeyboard();
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Se ingresa dato articulo bebida alcoholica");
			buscador.sendKeys(articuloBA);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap boton Buscar");
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Ingresar Articulo bebida alcoholica en journal con IP Dinamica
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @date 13/04/2026
	 *
	 */
	public void ingresarSkuArtBAJournalD() {
		try {

			String articuloBA = ConsultaArticuloBAD();
			WebElement buscador = au.driver.findElement(Buscador);
			System.out.println("Se encontro el buscador exitosamente");

			testCase.addTextEvidenceCurrentStep("Tap boton Buscador");
			au.driver.hideKeyboard();
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Se ingresa dato articulo bebida alcoholica");
			buscador.sendKeys(articuloBA);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap boton Buscar");
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			TimeUnit.SECONDS.sleep(5);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap boton Cantidad");
			au.click(btn_cantidad);
			testCase.addScreenShotCurrentStep(au, "testImage");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Ingresar Bebida Alcoholica al buscador fuera de horario permitido
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @date 10/09/2024
	 *
	 */
	public void ingresarBebidaAlcoholicaFO(String ipPv) {
		try {

			String articuloBA = ConsultaArticuloBA(ipPv);
			WebElement buscador = au.driver.findElement(Buscador);

			testCase.addTextEvidenceCurrentStep("Tap boton Buscador");
			au.click(MBuscador);
			au.driver.hideKeyboard();
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Ingresar articulo bebdida alcoholica");
			buscador.sendKeys(articuloBA);
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap boton Buscar");
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			TimeUnit.SECONDS.sleep(10);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Validación journal");
			waitForVisibility(Journal, Duration.ofSeconds(15));
			System.out.print("Caso exitoso, la bebida alcoholica respeto las restricciones de horario");
			testCase.addScreenShotCurrentStep(au, "testImage");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Validar los valores monto venta exitosa
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @return true o false Si el valor de las consultas es Igual
	 * @date 21/01/2025
	 *
	 */
	public boolean validarVentaExitosaArtPrmLocal(String ipPv) {
		try {

			String montoConsulta = consultaMontoVentaLocal(ipPv);
			String montoVentaLocal = obtenerDato();

			if (montoVentaLocal.equals(montoConsulta)) {
				System.out.println("La validación fue exitosa: el monto coincide.");
				return true;
			} else {
				System.err.println("La validación falló: el monto no coincide.");
				return false;
			}
		} catch (NoSuchElementException e) {
			System.err.println("El elemento montoVenta no fue encontrado: " + e.getMessage());
			return false;
		} catch (Exception e) {
			System.err.println("Error al realizar la validación: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Método - Validar los valores monto venta exitosa Nube
	 * 
	 * @author Cesar Guel
	 * @param crTienda El nombre de la plaza y tienda utilizado
	 * @param ipPv     La ip de la maquina que se esta utilizando
	 * @return true o false Si el valor de las consultas es Igual
	 * @date 21/01/2025
	 *
	 */
	public boolean validarVentaExitosaArtPrmNube(String crTienda, String ipPv) {
		try {

			String montoVentaNube = consultaMontoVentaNube(crTienda);
			String montoVentaLocal = consultaMontoVentaLocal(ipPv);

			if (montoVentaLocal.equals(montoVentaNube)) {
				System.out.println("La validación fue exitosa: el monto coincide.");
				return true;
			} else {
				System.err.println("La validación falló: el monto no coincide.");
				return false;
			}
		} catch (NoSuchElementException e) {
			System.err.println("El elemento montoVenta no fue encontrado: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Error al realizar la validación: " + e.getMessage());
		}
		return false;
	}

	/**
	 * Método - Consulta articulo vendido en base de datos local
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @return true o false Si arroja los datos esperados
	 * @date 11/06/2025
	 */
	public boolean validarArtVendidoLocal(String ipPv) {
		try {
			String conLocal = consultaMontoVentaLocal(ipPv);

			if (conLocal != null && !conLocal.trim().isEmpty()) {
				System.out.println("La consulta arrojo los datos esperados");
				TimeUnit.SECONDS.sleep(5);
				return true;
			} else {
				System.out.println("La consulta no devolvió datos.");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Consulta articulo vendido en base de datos local
	 * 
	 * @author Cesar Guel
	 * @param crTienda El nombre de la plaza y tienda utilizado
	 * @return true o false Si arroja los datos esperados
	 * @date 11/06/2025
	 */
	public boolean validarArtVendidoNube(String crTienda) {
		try {
			String conNube = consultaMontoVentaNube(crTienda);

			if (conNube != null && !conNube.trim().isEmpty()) {
				System.out.println("La consulta arrojo los datos esperados");
				TimeUnit.SECONDS.sleep(5);
				return true;
			} else {
				System.out.println("La consulta no devolvió datos.");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Validar el ticket de la venta exitosa Nube
	 * 
	 * @author Cesar Guel
	 * @param crTienda El nombre de la plaza y tienda utilizado
	 * @param ipPv     La ip de la maquina que se esta utilizando
	 * @return true o false Si el ticket es correcto
	 * @date 21/01/2025
	 *
	 */
	public boolean validarTktNube(String crTienda, String ipPv) {
		try {
			boolean resTktNube = consultaTktPrmNube(crTienda, ipPv);
			if (resTktNube) {
				System.out.println("El ticket de la venta es correcto");
				return true;
			} else {
				System.out.println("El ticket de la venta no es correcto");
				return false;
			}
		} catch (Exception e) {
			System.err.println("" + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Guardar valor monto
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 21/01/2025
	 *
	 */
	public void valorMontoVentaE() {
		try {

			WebElement valorMonto = au.driver.findElement(montoVenta);
			String monto = valorMonto.getText();
			valormontoventa = monto;
			System.out.println("Valor Guardado: " + monto);

		} catch (Exception e) {
			System.err.println("" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Método - Mandar llamar el valor del monto de la venta
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return valormontoventa Regresa el valor del monto de la venta
	 * @date 21/01/2025
	 */
	public String obtenerDato() {
		if (valormontoventa != null) {
			System.out.println("Dato obtenido: " + valormontoventa);
			return valormontoventa;
		} else {
			System.out.println("No hay ningún dato guardado.");
			return null;
		}
	}

	/**
	 * Método - Validar apartado servicios
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si el elemento esta Desplegado
	 * @date 21/01/2025
	 */
	public boolean validarApartadoServicios() {
		try {
			testCase.addTextEvidenceCurrentStep("Se busca el apartado de Servicios");
			WebElement apartadoServicios = au.driver.findElement(btn_servicios);

			if (apartadoServicios.isDisplayed()) {
				System.out.println("Se encontro el apartado de servicios");
				TimeUnit.SECONDS.sleep(5);
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} else {
				System.out.println("No se encuentra disponible");
				return false;
			}

		} catch (Exception e) {
			System.out.println("no se pudo realizar la validación");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return false;
		}
	}

	/**
	 * Método - Validar que se muestre el catalogo de servicios no financieros
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si el elemento esta Desplegado
	 * @date 22/01/2025
	 */
	public boolean validarCatalogoServicios() {
		try {
			WebElement catalogoServ = au.driver.findElement(modServicios);

			if (catalogoServ.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep("Se valida que se muestre el catalogo de servicios");
				System.out.println("Se encontro el catalogo de servicios");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(10);
				return true;
			} else {
				System.out.println("No se encontro el catalogo de servicios");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Hubo un error al validar el catalogo");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return false;
		}
	}

	/**
	 * Método - ingresar al buscador de servicios
	 * 
	 * @author Cesar Guel
	 * @param nomCat El nombre de la categoria de servicio
	 * @date 22/01/2025
	 */
	public void ingresarCatSer(String nomCat) {
		try {
			WebElement etCatServ = au.driver.findElement(Buscador);
			testCase.addTextEvidenceCurrentStep("Se ingresa el nombre de categoria");
			au.click(MBuscador);
			au.sendKeys(Buscador, nomCat);
			testCase.addScreenShotCurrentStep(au, "testImage");
			testCase.addTextEvidenceCurrentStep("Se presiona enter para buscar categoria");
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);

			testCase.addBoldTextEvidenceCurrentStep("Tap categoría de servicio");
			au.click(btn_catTVPaga);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Hubo un error al ingresar al buscador de servicios");
		}
	}

	/**
	 * Método - Ingresar nombre servicio
	 * 
	 * @author Cesar Guel
	 * @param nomServ El nombre del servicio
	 * @date 22/01/2025
	 */
	public void ingresarNomServ(String nomServ) {
		try {

			WebElement etServTVPaga = au.driver.findElement(Buscador);
			testCase.addTextEvidenceCurrentStep("Se ingresa el nombre del servicio");
			etServTVPaga.sendKeys(nomServ);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Se da enter al ingresar el nombre del servicio");
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			TimeUnit.SECONDS.sleep(5);
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Métdodo - Validar que se muestre el servicio correspondiente
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si el elemento esta Desplegado
	 * @date 22/01/2025
	 */
	public boolean validarServCons() {
		try {
			WebElement servIEL = au.driver.findElement(servDish);

			if (servIEL.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep("Se valida que se encuentre el servicio");
				System.out.println("Si se encontro el servicio correspondiente");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} else {
				System.out.println("No se encontro servicio");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Ingresar modulo FastFood
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 22/01/2025
	 */
	public void ingresarModFastFood() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el apartado de FastFood");
			au.click(btn_fastFood);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(20);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo - validar que no se muestre apartado FastFood
	 * 
	 * @author Cesar Guel
	 * @date 21/04/2026
	 */
	public boolean validaModFastFoodDesh() {
		try {
			testCase.addTextEvidenceCurrentStep("Se valida que se muestre apartado de FastFood deshabilitado");
			
			if(isElementPresent(btn_fastFood)) {
				System.out.println("El elemento FastFood se mostro en el journal");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return false;
			}else {
				System.out.println("El elemento FastFood no se mostro en el journal");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Metodo - validar que no se muestre apartado TAE
	 * 
	 * @author Cesar Guel
	 * @date 21/04/2026
	 */
	public boolean validaModTAEDesh() {
		try {
			testCase.addTextEvidenceCurrentStep("Se valida que se muestre apartado de FastFood deshabilitado");
			
			if(isElementPresent(btn_TAE)) {
				System.out.println("El elemento TAE se mostro en el journal");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return false;
			}else {
				System.out.println("El elemento TAE no se mostro en el journal");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Metodo - validar que se muestre apartado FastFood habilitado
	 * 
	 * @author Cesar Guel
	 * @date 21/04/2026
	 */
	public boolean validaModFastFoodhab() {
		try {
			testCase.addTextEvidenceCurrentStep("Se valida que se muestre apartado de FastFood deshabilitado");
			
			if(isElementPresent(btn_fastFood)) {
				System.out.println("El elemento FastFood se mostro en el journal");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			}else {
				System.out.println("El elemento FastFood no se mostro en el journal");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return false;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Validar que se muestren las categorias de FastFood Alimento y
	 * Bebidas
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si los elementos estan Desplegados
	 * @date 28/01/2025
	 */
	public boolean validarCatsFFAYB() {
		try {
			WebElement catFFA = au.driver.findElement(btn_FFAlimentos);
			WebElement catFFB = au.driver.findElement(btn_FFBebidas);

			if (catFFA.isDisplayed() && catFFB.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep("Se valida que se encuentren las categorias de FastFood");
				System.out.println("Se encontraron de forma correcta las categorias");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(10);
				return true;
			} else {
				testCase.addScreenShotCurrentStep(au, "testImage");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addScreenShotCurrentStep(au, "testImage");
			return false;
		}
	}

	/**
	 * Método - Validar el boton Fast Food Bebidas
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si el elemento esta Desplegado
	 * @date 22/01/2025
	 */
	public boolean validarBotonFFBebidas() {
		try {
			WebElement btnffBebidas = au.driver.findElement(btn_FFBebidas);

			if (btnffBebidas.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep("Se encontro el boton FastFood Bebidas");
				System.out.println("Si se encontro el boton fast food bebidas");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(10);
				return true;
			} else {
				testCase.addScreenShotCurrentStep(au, "testImage");
				System.out.println("No se encontro el boton fast food bebidas");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			testCase.addScreenShotCurrentStep(au, "testImage");
			return false;
		}
	}

	/**
	 * Método - Validar el boton Fast Food Alimentos
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si el elemento esta Desplegado
	 * @date 22/01/2025
	 */
	public boolean validarBotonFFAlimentos() {
		try {
			WebElement btnffAlimentos = au.driver.findElement(btn_FFAlimentos);

			if (btnffAlimentos.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep("Se busca el boton Fast food Alimentos");
				System.out.println("Si se encontro el boton fast food alimentos");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(5);
				return true;
			} else {
				testCase.addScreenShotCurrentStep(au, "testImage");
				System.out.println("No se encontro el boton fast food alimentos");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			testCase.addScreenShotCurrentStep(au, "testImage");
			return false;
		}
	}

	/**
	 * Método - Presionar boton FastFood Bebidas
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 24/01/2025
	 */
	public void presionarBtnFFBebidas() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap boton FastFood Bebidas");
			au.click(btn_FFBebidas);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Presionar boton Fast Food Alimento
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 24/01/2025
	 */
	public void presionarBtnFFAlimentos() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap boton FastFood Alimentos");
			au.click(btn_FFAlimentos);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Validar se muestren opciones de categoria FastFood Alimento
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si los elementos estan Desplegados
	 * @date 28/01/2025
	 */
	public boolean validarCatsFFA() {
		try {
			WebElement cat1 = au.driver.findElement(cat1FFA);
			WebElement cat2 = au.driver.findElement(cat2FFA);
			WebElement cat3 = au.driver.findElement(cat3FFA);

			if (cat1.isDisplayed() && cat2.isDisplayed() && cat3.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep("Se valida que se encuentren las categorias del menu de FastFood");
				System.out.println("Se encontraron los elementos de manera correcta");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(5);
				return true;
			} else {
				testCase.addScreenShotCurrentStep(au, "testImage");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			testCase.addScreenShotCurrentStep(au, "testImage");
			return false;
		}
	}

	/**
	 * Método - Ingresar a la categoria Comida Caliente
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 28/01/2025
	 */
	public void ingresarCatComCalFFAlimento() {
		try {
			testCase.addTextEvidenceCurrentStep("Se ingresa a la categoria Comida Caliente");
			au.click(cat1FFA);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Validar se muestren las categorias FastFood Alimento comida caliente
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si el elemento esta Desplegado
	 * @date 28/01/2025
	 */
	public boolean validarCatComCalFFA() {
		try {

			WebElement cat = au.driver.findElement(cat2CCFFA);

			if (cat.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep(
						"Se valida que se encuentren las categorias del menu de FastFood Comida Caliente");
				System.out.println("Se encontraron los elementos de manera correcta");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(5);
				return true;
			} else {
				testCase.addScreenShotCurrentStep(au, "testImage");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			testCase.addScreenShotCurrentStep(au, "testImage");
			return false;
		}
	}

	/**
	 * Método - Validar se muestre el top FastFood Alimento comida caliente
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si los elementos estan Desplegados
	 * @date 28/01/2025
	 */
	public boolean validarTopCCFFA() {
		try {
			WebElement top1 = au.driver.findElement(top1FFA);
			WebElement top2 = au.driver.findElement(top2FFA);
			WebElement txttop = au.driver.findElement(txtTopFF);
			au.scrollToElement(top1FFA);

			if (top1.isDisplayed() && top2.isDisplayed() && txttop.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep("Se valida que se encuentre el top articulos del menu de FastFood");
				System.out.println("Se encontraron los elementos de manera correcta");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(10);
				return true;
			} else {
				testCase.addScreenShotCurrentStep(au, "testImage");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			testCase.addScreenShotCurrentStep(au, "testImage");
			return false;
		}
	}

	/**
	 * Método - Validar que se muestren articulos Catalogo FastFood Alimentos Comida
	 * Caliente
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si los elementos estan Desplegados
	 * @date 28/01/2025
	 */
	public boolean validarArtCatCCFFA() {
		try {

			testCase.addTextEvidenceCurrentStep("Tap categoria 1");
			au.click(pas1_FFA);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);

			testCase.addTextEvidenceCurrentStep("Tap categoria 2");
			au.click(pas2_FFA);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);

			testCase.addTextEvidenceCurrentStep("Tap categoria 3");
			au.click(pas3_FFA);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);

			testCase.addTextEvidenceCurrentStep("Tap categoria 4");
			au.click(pas4_FFA);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);

			WebElement artccffa = au.driver.findElement(artFFA);
			WebElement artccffa2 = au.driver.findElement(artFFA2);

			if (artccffa.isDisplayed() && artccffa2.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep(
						"Se valida que se muestren articulos en el catalogo de FastFood Alimentos");
				System.out.println("Se encontraron los articulos con exito");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(5);
				return true;
			} else {
				testCase.addScreenShotCurrentStep(au, "testImage");
				System.out.println("No se pudieron encontrar los articulos con exito");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			testCase.addScreenShotCurrentStep(au, "testImage");
			return false;
		}
	}

	/**
	 * Método - Validar que se muestren articulos Catalogo FastFood Bebidas
	 * Americano
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si los elementos estan Desplegados
	 * @date 28/01/2025
	 */
	public boolean validarArtCatCFFB() {
		try {

			testCase.addTextEvidenceCurrentStep("Tap categoria 1");
			au.click(pas1_FFB);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap categoria 2");
			au.click(pas2_FFB);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap categoria 3");
			au.click(pas3_FFB);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap categoria 4");
			au.click(pas4_FFB);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			WebElement artcffb = au.driver.findElement(artFFA);
			WebElement artcffb2 = au.driver.findElement(artFFA2);

			if (artcffb.isDisplayed() && artcffb2.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep(
						"Se valida que se muestren articulos en el catalogo de FastFood Bebidas");
				System.out.println("Se encontraron los articulos con exito");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} else {
				testCase.addScreenShotCurrentStep(au, "testImage");
				System.out.println("No se pudieron encontrar los articulos con exito");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			testCase.addScreenShotCurrentStep(au, "testImage");
			return false;
		}
	}

	/**
	 * Método - Ingresar al catalogo de articulos FFAlimentos
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 24/01/2025
	 */
	public void ingresarCatFFAli() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap categoria 1");
			au.click(pas1_FFA);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap categoria 2");
			au.click(pas2_FFA);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap categoria 3");
			au.click(pas3_FFA);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap categoria 4");
			au.click(pas4_FFA);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Ingresar al catalogo de articulos FFBebidas
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 24/01/2025
	 */
	public void ingresarCatFFBeb() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap categoria 1");
			au.click(pas1_FFB);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);

			testCase.addTextEvidenceCurrentStep("Tap categoria 2");
			au.click(pas2_FFB);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);

			testCase.addTextEvidenceCurrentStep("Tap categoria 3");
			au.click(pas3_FFB);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);

			testCase.addTextEvidenceCurrentStep("Tap categoria 4");
			au.click(pas4_FFB);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Validar que se muestren articulos en el menu de Fastfood Alimentos
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si el elemento esta Desplegado
	 * @date 24/01/2025
	 */
	public boolean validarArtFFAli() {
		try {
			WebElement artffa = au.driver.findElement(artFFA);

			if (artffa.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep("Se valida que se encuentre el articulo de FastFood Alimentos");
				System.out.println("Se encontro el articulo exitosamente");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(5);
				return true;
			} else {
				System.out.println("No se pudo encontrar el articulo");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Validar que se muestren articulos en el menu de Fastfood Bebidas
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si el elemento esta Desplegado
	 * @date 24/01/2025
	 */
	public boolean validarArtFFBeb() {
		try {
			WebElement artffb = au.driver.findElement(artFFB);

			if (artffb.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep("Se valida que se encuentre el articulo de FastFood Bebidas");
				System.out.println("Se encontro el articulo exitosamente");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(5);
				return true;
			} else {
				System.out.println("No se pudo encontrar el articulo");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Seleccionar articulo FastFood Alimentos del catalogo de articulso
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 24/01/2025
	 */
	public void seleccArtFFA() {
		try {
			testCase.addTextEvidenceCurrentStep("Seleccionar articulo");
			au.click(artFFA);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(15);

			testCase.addTextEvidenceCurrentStep("Tap boton continuar");
			au.click(btn_ctnFF);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(15);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Seleccionar articulo FastFood Bebidas del catalogo de articulso
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 24/01/2025
	 */
	public void seleccArtFFB() {
		try {
			testCase.addTextEvidenceCurrentStep("Seleccionar articulo");
			au.click(artFFB);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Tap boton continuar");
			au.click(btn_ctnFF);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Cancelar venta FastFood Bebida
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 24/01/2025
	 */
	public void cancelVenFFB() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap boton Navegar Atras");
			au.click(btn_NavAtras);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Ingresar artuculo FastFood Bebidas
	 * 
	 * @author Cesar Guel
	 * @param artFFB El articulo FastFood Bebida a utilizar
	 * @date 13/05/2025
	 */
	public void ingresarArtFFBB(String artFFB) {
		try {
			WebElement buscador = au.driver.findElement(Buscador);
			testCase.addTextEvidenceCurrentStep("Tap en Botón Buscador de articulos");
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Ingresar el valor al buscador ");
			buscador.sendKeys(artFFB);
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(3);
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error, Ingresar el artiulo al Journal");
			e.printStackTrace();
		}
	}

	/**
	 * Método - Ingresar artuculo FastFood Alimentos
	 * 
	 * @author Cesar Guel
	 * @param artFFA El articulo FastFood Alimentos a utilizar
	 * @date 13/05/2025
	 */
	public void ingresarArtFFAB(String artFFA) {
		try {
			WebElement buscador = au.driver.findElement(Buscador);
			testCase.addTextEvidenceCurrentStep("Tap en Botón Buscador de articulos");
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Ingresar el valor al buscador ");
			au.driver.hideKeyboard();
			buscador.sendKeys(artFFA);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error, Ingresar el artiulo al Journal");
			e.printStackTrace();
		}
	}

	/**
	 * Método - Validar que se muestre articulo por nombre
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si el elemento esta Desplegado
	 * @date 13/05/2025
	 */
	public boolean validarArtFFBPN() {
		try {
			WebElement artjou = au.driver.findElement(Articulo);
			TimeUnit.SECONDS.sleep(10);

			if (artjou.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep("Se valida que se encuentre el articulo en el journal");
				System.out.println("Si se encontro el articulo correspondiente");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(5);
				return true;
			} else {
				System.out.println("No se encontro el articulo correspondiente");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Validar que se muestre articulo en journal
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si el elemento esta Desplegado
	 * @date 24/01/2025
	 */
	public boolean validarArtJou() {
		try {
			waitForVisibility(artJournal, Duration.ofSeconds(20));

			if (isElementPresent(artJournal)) {
				testCase.addTextEvidenceCurrentStep("Se valida que se encuentre el articulo en el journal");
				System.out.println("Si se encontro el articulo correspondiente");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(15);
				return true;
			} else {
				System.out.println("No se pudo encontrar el articulo");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Metodo - Validar que se cancele la venta y se muestre el articulo en el
	 * journal
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si el elemento esta Desplegado
	 * @date 24/01/2025
	 */
	public boolean validarCanVenFFB() {
		try {
			WebElement valcanvenffb = au.driver.findElement(artJournal);

			if (valcanvenffb.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep("Se valida que se encuentre el articulo en el journal");
				System.out.println("Se encontro el articulo en el journal");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(5);
				return true;
			} else {
				testCase.addScreenShotCurrentStep(au, "testImage");
				System.out.println("No se encontro el articulo");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			testCase.addScreenShotCurrentStep(au, "testImage");
			return false;
		}
	}

	/**
	 * Método - Ingresar al apartado Opciones
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 23/01/2025
	 */
	public void ingresarApOpciones() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap boton Opciones");
			au.click(btn_Opciones);
			TimeUnit.SECONDS.sleep(1);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Ingresar al apartado Cortes Administrativos
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 23/01/2025
	 */
	public void ingresarApCortesAdmin() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap boton Cortes Administrativos");
			au.click(btn_CortesAdm);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Ingresar al apartado tipo de cortes
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 23/01/2025
	 */
	public void ingresarApTipoCort() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap apartado Tipos de cortes");
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.click(tipCortes);
			TimeUnit.SECONDS.sleep(1);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Validar se muestre el tipo de corte HEF
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si el elemento esta Desplegado
	 * @date 23/01/2025
	 */
	public boolean validarTipoCorteHef() {
		try {
			WebElement corthef = au.driver.findElement(cortHEF);

			if (corthef.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep("Se valida que si se encuentre el tipo de reporte HEF");
				System.out.println("Si se encontro el tipo de corte HEF");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(5);

				testCase.addTextEvidenceCurrentStep("Se presiona boton Atras");
				((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.BACK));
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(5);

				return true;
			} else {
				System.out.println("No se pudo encontrar el tipo de corte HEF");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			testCase.addScreenShotCurrentStep(au, "testImage");
			return false;
		}
	}

	/**
	 * Método - Seleccionar tipo de corte HEF
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 23/01/2025
	 */
	public void seleccionarTipCortHef() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap tipo de corte HEF");
			au.click(cortHEF);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo - Presionar boton Consultar corte HEF
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 23/01/2025
	 */
	public void consultarCortHEF() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap boton consulta corte HEF");
			au.click(btn_consCorteHEF);
			TimeUnit.SECONDS.sleep(2);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(15);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Validar que se muestren lo elementos del reporte
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si los elementos estan Desplegados
	 * @date 23/01/2025
	 */
	public boolean validarElemCorteHEF() {
		try {
			WebElement totcorte = au.driver.findElement(totCorte);
			WebElement efeccorte = au.driver.findElement(efecCorte);
			WebElement efecentregado = au.driver.findElement(efecEntregado);
			WebElement efecporentr = au.driver.findElement(efecPorEntr);

			if (totcorte.isDisplayed() && efeccorte.isDisplayed() && efecentregado.isDisplayed()
					&& efecporentr.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep("Se valida que se muestren los elementos");
				System.out.println("Si se encontraron los elementos correspondientes");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(10);
				testCase.addTextEvidenceCurrentStep("Se presiona boton Atras");
				((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.BACK));
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} else {
				System.out.println("No se pudieron encontrar los elementos");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Seleccionar boton Cerrar Pantalla Cortes
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 23/01/2025
	 */
	public void cerrReporteHef() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap boton cerrar reporte HEF");
			au.click(btn_consCerrar);
			TimeUnit.SECONDS.sleep(1);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Validar que se muestre el modulo de Cortes Administrativos
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si el elemento esta Desplegado
	 * @date 23/01/2025
	 */
	public boolean validarPestCortAdm() {
		try {
			WebElement pestcoradm = au.driver.findElement(pestCortAdm);

			if (pestcoradm.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep("Se valida que se encuentre la pestaña de Cortes Administrativos");
				System.out.println("Si se encontro el apartado cortes administrativos");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(10);
				testCase.addTextEvidenceCurrentStep("Tap boton Atras");
				au.click(btn_NavAtras);
				TimeUnit.SECONDS.sleep(2);
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} else {
				System.out.println("No se pudo encontrar la pestaña Cortes Administrativos");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			testCase.addScreenShotCurrentStep(au, "testImage");
			return false;
		}
	}

	/**
	 * Método - Regresar Journal apartado Cortes Administrativos
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 23/01/2025
	 */
	public void regresarJournalCortAdm() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap boton Atras");
			au.click(btn_NavAtras);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Ingresar al apartado de servicios
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 27/01/2025
	 */
	public void ingresarModServ() {
		try {
			testCase.addTextEvidenceCurrentStep("Se ingresa al apartado de servicios");
			au.click(btn_servicios);
			testCase.addScreenShotCurrentStep(au, "testImage");
			System.out.println("Se ingreso al modulo de servicios exitosamente");
			TimeUnit.SECONDS.sleep(15);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Ingresar al apartado de servicios
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 27/01/2025
	 */
	public void presionarModServ() {
		try {
			testCase.addTextEvidenceCurrentStep("Se ingresa al apartado de servicios");
			au.click(btn_servicios);
			testCase.addScreenShotCurrentStep(au, "testImage");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Validar que se muestre el top de servicios
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si los elementos estan Desplegados
	 * @date 28/01/2025
	 */
	public boolean validarTopServicios() {
		try {
			WebElement tts = au.driver.findElement(txtServMasConsultados);
			WebElement ts1 = au.driver.findElement(topServ1);
			WebElement ts2 = au.driver.findElement(topServ2);
			WebElement ts3 = au.driver.findElement(topServ3);

			if (tts.isDisplayed() && ts1.isDisplayed() && ts2.isDisplayed() && ts3.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep("Se valida que se encuentren los elementos del top servicios");
				System.out.println("Se encontraron de forma correcta");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(10);
				return true;
			} else {
				testCase.addScreenShotCurrentStep(au, "testImage");
				System.out.println("No se encontraron de forma correcta");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			testCase.addScreenShotCurrentStep(au, "testImage");
			return false;
		}
	}

	/**
	 * Método - Seleccionar el tipo de servicio TV Paga
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 27/01/2025
	 */
	public void seleccionarCatTV() {
		try {
			testCase.addTextEvidenceCurrentStep("Se selecciona la categoria TV de paga");
			au.click(btn_catTVPaga);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Seleccionar el tipo de servicio Telecomunicaciones
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 27/01/2025
	 */
	public void seleccionarCatTelecom() {
		try {
			testCase.addTextEvidenceCurrentStep("Se selecciona la categoria Telecomunicaciones");
			au.click(btn_catTeleCom);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Seleccionar tipo de servicio Izzi en linea
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 27/01/2025
	 */
	public void seleccionarServIzzi() {
		try {
			testCase.addTextEvidenceCurrentStep("Se selecciona la categoria TV de paga");
			au.scrollToElement(btn_catTVPaga);
			au.click(btn_catTVPaga);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Se selecciona el servicio Izzi en línea");
			au.click(servIzzi);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Seleccionar tipo de servicio Sky en linea
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 27/01/2025
	 */
	public void seleccionarServSky() {
		try {
			testCase.addTextEvidenceCurrentStep("Se selecciona la categoria TV de paga");
			TimeUnit.SECONDS.sleep(5);
			au.scrollToElement(btn_catTVPaga);
			au.click(btn_catTVPaga);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Se selecciona el servicio Sky en línea");
			au.click(servSky);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Seleccionar Servicio Sky en línea
	 * 
	 * @author Cesar Guel
	 * @param servicio El nombre del servicio a seleccionar
	 * @date 31/07/2025
	 */
	public void seleccionarServicioSky(String servicio) {
		try {

			testCase.addTextEvidenceCurrentStep("Se selecciona el buscador de servicios");
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "Abrir buscador");
			TimeUnit.SECONDS.sleep(2);

			testCase.addTextEvidenceCurrentStep("Se ingresa el nombre del servicio: " + servicio);
			au.sendKeys(Buscador, servicio);
			testCase.addScreenShotCurrentStep(au, "Servicio ingresado");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Se presiona ENTER para buscar");
			au.click(MBuscador);
			au.click(Buscador);
			au.driver.executeScript("mobile:performEditorAction", ImmutableMap.of("action", "search"));

			TimeUnit.SECONDS.sleep(4);
			au.driver.hideKeyboard();

			testCase.addTextEvidenceCurrentStep("Se selecciona el servicio");
			if (isElementPresent(servSky)) {
				au.click(servSky);
				System.out.println("Se encontro exitosamente el boton SKY EN LINEA");
				testCase.addScreenShotCurrentStep(au, "Servicio seleccionado");
				TimeUnit.SECONDS.sleep(5);
			} else if (isElementPresent(servSkyN)) {
				au.click(servSkyN);
				System.out.println("Se encontro exitosamente el boton SKY ONLINE NAC");
				testCase.addScreenShotCurrentStep(au, "Servicio seleccionado");
				TimeUnit.SECONDS.sleep(5);
			} else {
				System.out.println("No se encontro el boton exitosamente");
			}

		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error al seleccionar el servicio: " + e.getMessage());
		}
	}

	/**
	 * Método - Seleccionar Servicio Sky en línea
	 * 
	 * @author Cesar Guel
	 * @param servicio El nombre del servicio a seleccionar
	 * @date 31/07/2025
	 */
	public void seleccionarServicioPB(String servicio) {
		try {

			testCase.addTextEvidenceCurrentStep("Se selecciona el buscador de servicios");
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "Abrir buscador");
			TimeUnit.SECONDS.sleep(2);

			testCase.addTextEvidenceCurrentStep("Se ingresa el nombre del servicio: " + servicio);
			au.sendKeys(Buscador, servicio);
			testCase.addScreenShotCurrentStep(au, "Servicio ingresado");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Se presiona ENTER para buscar");
			au.click(MBuscador);
			au.click(Buscador);
			au.driver.executeScript("mobile:performEditorAction", ImmutableMap.of("action", "search"));

			TimeUnit.SECONDS.sleep(4);
			au.driver.hideKeyboard();

			testCase.addTextEvidenceCurrentStep("Se selecciona el servicio");
			waitForVisibility(servDish, Duration.ofSeconds(30));
			testCase.addScreenShotCurrentStep(au, "Servicio seleccionado");
			System.out.println("Servicio encontrado exitosamente");
			TimeUnit.SECONDS.sleep(5);

		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error al seleccionar el servicio: " + e.getMessage());
		}
	}

	/**
	 * Método - Seleccionar tipo de servicio Dish en linea
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 27/01/2025
	 */
	public void seleccionarServDish() {
		try {
			testCase.addTextEvidenceCurrentStep("Se selecciona la categoria TV de paga");
			au.scrollToElement(btn_catTVPaga);
			au.click(btn_catTVPaga);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Se selecciona el servicio Dish en línea");
			au.click(servDish);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Seleccionar tipo de servicio Dish en linea CB
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 27/01/2025
	 */
	public void seleccionarServDishCB() {
		try {
			testCase.addTextEvidenceCurrentStep("Se selecciona la categoria TV de paga");
			au.scrollToElement(btn_catTVPaga);
			au.click(btn_catTVPaga);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Se selecciona el servicio Dish en línea CB");
			au.click(servDishCB);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Seleccionar tipo de servicio Telmex en linea
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 27/01/2025
	 */
	public void seleccionarServTelmex() {
		try {
			testCase.addTextEvidenceCurrentStep("Se selecciona la categoria Telecomunicaciones");
			au.scrollToElement(btn_catTeleCom);
			au.click(btn_catTeleCom);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Se selecciona el servicio Telmex en línea");
			au.click(servTelmex);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Seleccionar tipo de servicio Telmex en linea
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 27/01/2025
	 */
	public void seleccionarServMercLibre() {
		try {
			testCase.addTextEvidenceCurrentStep("Se selecciona la categoria Envio Expres");
			au.scrollToElement(btn_catEnvExp);
			au.click(btn_catEnvExp);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Se selecciona el servicio Mercado Libre en línea");
			au.click(servMercLibre);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Ingresar información del cliente
	 * 
	 * @author Cesar Guel
	 * @param infCliente El folio del cliente
	 * @date 27/01/2025
	 */
	public void ingresarInfCliente(String infCliente) {
		try {

			testCase.addTextEvidenceCurrentStep("Se ingresa la información del cliente");
			au.sendKeys(EdTeServicios, infCliente);
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Se presiona el boton consultar información");
			au.click(btn_ConsCliente);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(20);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Validar suscriptor sin adeudo
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return True si el elemento esta presente
	 * @date 19/01/2026
	 */
	public boolean validarSusSinAde() {
		try {
			if (isElementPresent(lb_susSinAdeudo)) {
				testCase.addTextEvidenceCurrentStep("Se valida suscriptor sin adeudo");
				System.out.println("Se encontro la leyenda exitosamente");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} else {
				System.out.println("No se encontro la leyenda exitosamente");
				return false;
			}
		} catch (Exception e) {
			System.out.println("Hubo algun problema en la validacion");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Presionar nav atras
	 * 
	 * @author Cesar Guel}
	 * @param N/A
	 * @date 19/01/2026
	 */
	public void presionarNavAtras() {
		try {
			testCase.addTextEvidenceCurrentStep("Se presiona boton nav atras");
			waitForVisibility(btn_navAtrServ, Duration.ofSeconds(20));
			System.out.println("Se encontro el boton nav atras exitosamente");
			au.click(btn_navAtrServ);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Validar modulo servicios
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return Retorna True si el elemento esta presente
	 * @date 19/01/2026
	 */
	public boolean valModServicios() {
		try {
			if (isElementPresent(modServ)) {
				testCase.addTextEvidenceCurrentStep("Se valida que se muestre modulo servicios");
				System.out.println("Se encontro el modulo exitosamente");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} else {
				System.out.println("No se encontro el modulo exitosamente");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Hubo algun problema al realizar la validacion");
			return false;
		}
	}

	/**
	 * Método - Confirmar información del cliente
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 27/01/2025
	 */
	public void confirmarInfCliente() {
		try {

			testCase.addTextEvidenceCurrentStep("Se confirma la información del cliente a pagar");
			waitForVisibility(btn_pagarServ, Duration.ofSeconds(30));
			System.out.println("Se encontro el boton para confirmar el pago exitosamente");
			au.click(btn_pagarServ);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Métdodo - Tap boton cancelar servicio
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 17/12/2025
	 */
	public void cancelarPagServ() {
		try {

			testCase.addTextEvidenceCurrentStep("Se confirma la información del cliente a pagar");
			waitForVisibility(btn_cancelarServ, Duration.ofSeconds(30));
			System.out.println("Se encontro el boton para cancelar el pago exitosamente");
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.click(btn_cancelarServ);
			TimeUnit.SECONDS.sleep(10);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Cancelar pago de servicio
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 27/01/2025
	 */
	public void cancelarServ() {
		try {
			au.click(btn_cancelarServ);
			TimeUnit.SECONDS.sleep(5);
			au.click(btn_NavAtras);
			TimeUnit.SECONDS.sleep(5);
			au.click(btn_NavAtras);
			TimeUnit.SECONDS.sleep(5);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Ingresar al apartado de TAE
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 27/01/2025
	 */
	public void ingresarApTAE() {
		try {
			testCase.addTextEvidenceCurrentStep("Se presiona el boton de TAE");
			au.click(btn_TAE);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Seleccionar compañia TAE
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 28/01/2025
	 */
	public void selecCompTae() {
		try {
			testCase.addTextEvidenceCurrentStep("Se selecciona la compañia");
			au.click(cpaTelcel);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Seleccionar compañia TAE Telcel
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 28/01/2025
	 */
	public void selecCompTaeTelcel() {
		try {
			testCase.addTextEvidenceCurrentStep("Se selecciona la compañia Telcel");
			au.click(cpaTelcel);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Seleccionar compañia Movistar
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 28/01/2025
	 */
	public void selecComTaeMovistar() {
		try {
			testCase.addTextEvidenceCurrentStep("Se selecciona la compañia Movistar");
			au.click(cpaMovistar);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Seleccionar cantidad de recarga
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 28/01/2025
	 */
	public void selecRecTae() {
		try {
			testCase.addTextEvidenceCurrentStep("Se selecciona el servicio TAE");
			au.scrollToElement(tae20T);
			au.click(tae20T);
			testCase.addScreenShotCurrentStep(au, "testImage");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Seleccionar cantidad de recarga Movistar
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 28/01/2025
	 */
	public void selecRecTaeMovistar() {
		try {
			testCase.addTextEvidenceCurrentStep("Se selecciona el servicio TAE Movistar");
			au.scrollToElement(tae20M);
			au.click(tae20M);
			testCase.addScreenShotCurrentStep(au, "testImage");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Seleccionar servicio TAE
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 27/01/2025
	 */
	public void seleccionarComTAE() {
		try {
			testCase.addTextEvidenceCurrentStep("Se selecciona la compañia");
			au.click(cpaTelcel);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Se selecciona el servicio TAE");
			au.scrollToElement(tae20T);
			au.click(tae20T);
			testCase.addScreenShotCurrentStep(au, "testImage");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Seleccionar servicio TAE
	 * 
	 * @author Cesar Guel
	 * @param compTel El nombre de la compañia TAE
	 * @date 27/01/2025
	 */
	public void seleccionarComTAENom(String compTel) {
		try {
			testCase.addTextEvidenceCurrentStep("Se selecciona el buscador");
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Se ingresa el nombre de la compañia");
			au.sendKeys(Buscador, compTel);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap en el boton Enter");
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.driver.hideKeyboard();

			testCase.addTextEvidenceCurrentStep("Se selecciona la compañia");
			au.click(cpaTelcel);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Se selecciona el servicio TAE");
			au.scrollToElement(tae20T);
			au.click(tae20T);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Validar se muestre pantalla de ingreso de numero de cliente
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si el elemento esta Desplegado
	 * @date 28/01/2025
	 */
	public boolean validarPantIngNumCliente() {
		try {

			TimeUnit.SECONDS.sleep(5);
			WebElement msjrec = au.driver.findElement(txtRec);

			if (msjrec.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep("Se valida que se encuentre la pantalla de ingreso de numero");
				System.out.println("Se encontro pantalla de manera exitosa");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(10);
				return true;
			} else {
				System.out.println("No se pudo encontrar");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addScreenShotCurrentStep(au, "testImage");
			return false;
		}
	}

	/**
	 * Método - Ingresar numero de cliente
	 * 
	 * @author Cesar Guel
	 * @param numCliente El numero del cliente
	 * @date 27/01/2025
	 */
	public void ingresarNumCliente(String numCliente) {
		try {
			testCase.addTextEvidenceCurrentStep("Se ingresa el numero del cliente");
			au.click(etNum1);
			au.sendKeys(etNum1, numCliente);
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Se ingresa nuevamente el numero del cliente");
			au.click(etNum2);
			au.sendKeys(etNum2, numCliente);
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Ingresar numero de cliente Incorrecto
	 * 
	 * @author Cesar Guel
	 * @param numCliente   El numero del cliente
	 * @param numClienteIn El numero incorrecto del cliente
	 * @date 27/01/2025
	 */
	public void ingresarNumClienteInc(String numCliente, String numClienteIn) {
		try {
			testCase.addTextEvidenceCurrentStep("Se ingresa el numero del cliente");
			au.click(etNum1);
			au.sendKeys(etNum1, numCliente);
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Se ingresa nuevamente el numero del cliente");
			au.click(etNum2);
			au.sendKeys(etNum2, numClienteIn);
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Confirmar Numero del Cliente
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 27/01/2025
	 */
	public void confirmarNumCliente() {
		try {
			testCase.addTextEvidenceCurrentStep("Se confirma el numero de telefono del cliente");
			au.click(btn_aceptarNumero);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - confirmar Numero del Cliente con numero incorrecto
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 27/01/2025
	 */
	public void confirmarNumClienteInc() {
		try {
			testCase.addTextEvidenceCurrentStep("Se confirma el numero de telefono del cliente");
			au.click(btn_aceptarNumero);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Validar Mensaje de numero incorrecto
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si se encuentra el elemento
	 * @date 02/09/2025
	 */
	public boolean validarMsjNumIncorrecto() {
		try {

			testCase.addTextEvidenceCurrentStep("Validar que se muestre el mensaje");
			waitForVisibility(msjNumInc, Duration.ofSeconds(30));
			System.out.println("Se encontro el mensaje exitosamente");
			testCase.addScreenShotCurrentStep(au, "testImage");
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("No se encontro el mensaje exitosamente");
			return false;
		}
	}

	/**
	 * Método - Ingresar los datos de la primera recarga
	 * 
	 * @author Cesar Guel
	 * @param numCliente El numero del cliente
	 * @date 18/06/2025
	 */
	public void ingresarDatCliente1(String numCliente) {
		try {
			testCase.addTextEvidenceCurrentStep("Se ingresa el numero del cliente");
			au.click(etNum1);
			au.sendKeys(etNum1, numCliente);
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Se ingresa nuevamente el numero del cliente");
			au.click(etNum2);
			au.sendKeys(etNum2, numCliente);
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Se confirma el numero de telefono del cliente");
			au.click(btn_aceptarNumero);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Ingresar los datos de la segunda recarga
	 * 
	 * @author Cesar Guel
	 * @param numCliente2 El numero del cliente de la segunda recarga
	 * @date 18/06/2025
	 */
	public void ingresarDatCliente2(String numCliente2) {
		try {
			testCase.addTextEvidenceCurrentStep("Se ingresa el numero del cliente");
			au.click(etNum1);
			au.sendKeys(etNum1, numCliente2);
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Se ingresa nuevamente el numero del cliente");
			au.click(etNum2);
			au.sendKeys(etNum2, numCliente2);
			au.driver.hideKeyboard();
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Se confirma el numero de telefono del cliente");
			au.click(btn_aceptarNumero);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Validar que la recarga sea exitosa
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si el elemento esta Desplegado
	 * @date 27/01/2025
	 */
	public boolean validarRecExitosa() {
		try {
			WebElement msjRecExitosa = au.driver.findElement(lbRecargaExi);

			if (msjRecExitosa.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep("Se valida que la recarga sea exitosa");
				System.out.println("La recarga fue exitosa");
				testCase.addScreenShotCurrentStep(au, "testImage");

				testCase.addTextEvidenceCurrentStep("Tap en el boton Finalizar");
				au.click(btn_finalizar);
				testCase.addScreenShotCurrentStep(au, "testImage");

				return true;
			} else {
				System.out.println("La recarga no fue exitosa");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Validar que la recarga sea exitosa
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si el elemento esta Desplegado
	 * @date 18/08/2025
	 */
	public boolean validarServRecExitosa() {
		try {
			waitForVisibility(Journal, Duration.ofSeconds(30));
			WebElement servicioTaeExitoso = au.driver.findElement(Journal);

			if (servicioTaeExitoso.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep("Se valida que la recarga sea exitosa");
				System.out.println("La recarga fue exitosa");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(15);
				return true;
			} else {
				System.out.println("La recarga no fue exitosa");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Validar que se muestren las compañias
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si los elementos estan Desplegados
	 * @date 29/01/2025
	 */
	public boolean validarCompTAE() {
		try {
			WebElement comTelc = au.driver.findElement(cpaTelcel);
			WebElement comMov = au.driver.findElement(cpaMovistar);

			if (comTelc.isDisplayed() && comMov.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep("Se valida que se muestren las compañias TAE");
				System.out.println("Se visualizan de forma correcta");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} else {
				testCase.addScreenShotCurrentStep(au, "testImage");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			testCase.addScreenShotCurrentStep(au, "testImage");
			return false;
		}
	}

	/**
	 * Método - Validar que se muestre el top recargas
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si los elementos estan Desplegados
	 * @date 29/01/2025
	 */
	public boolean validarTopTae() {
		try {
			WebElement msgtoprec = au.driver.findElement(txtRecMasVendidas);
			WebElement toprec1 = au.driver.findElement(topRec1);
			WebElement toprec2 = au.driver.findElement(topRec2);

			if (msgtoprec.isDisplayed() && toprec1.isDisplayed() && toprec2.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep("Se valida que se muestre el top recargas");
				System.out.println("Se encontro de forma exitosa");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} else {
				testCase.addScreenShotCurrentStep(au, "testImage");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			testCase.addScreenShotCurrentStep(au, "testImage");
			return false;
		}
	}

	/**
	 * Método - Validar que se muestre el articulo de Top recargas en el journal
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return true o false Si el elemento esta Desplegado
	 * @date 29/01/2025
	 */
	public boolean validarArtTopRecJou() {
		try {
			WebElement arttoprecjou = au.driver.findElement(Articulo);

			au.click(topRec1);

			if (arttoprecjou.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep("Se valida que se muestre el articulo en el journal");
				System.out.println("Se agrego el articulo correctamente");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} else {
				System.out.println("No se agrego el articulo correctamente");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			testCase.addScreenShotCurrentStep(au, "testImage");
			return false;
		}
	}

	/**
	 * Método - Validación de duplicidad de datos en las promociones en la nube
	 * 
	 * @author Cesar Guel
	 * @param crTienda El nombre de la plaza y tienda utilizado
	 * @return true o false Si los datos solo se encuentran 1 vez
	 * @date 01/08/2025
	 */
	public boolean validarDuplicidadPrm(String crTienda) {
		try {
			String valorConsultaDuplicidad = consultaDuplicidadPromocionesNube(crTienda);
			System.out.println("Valor consulta: " + valorConsultaDuplicidad);
			TimeUnit.SECONDS.sleep(5);

			if (valorConsultaDuplicidad.equals("1")) {
				testCase.addTextEvidenceCurrentStep("Se valida que los datos de la promoción no sean duplicados");
				System.out.println("Se valida que la consulta es exitosa");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(5);
				return true;
			} else {
				System.out.println("Se valida que los valores están duplicados");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/*****************************
	 * Scripts de cosnulta a base de datos
	 ***********************************************/

	/**
	 * Método - Consulta Articulo
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @return El articulo consultado en la base de datos local
	 * @date 06/09/2024
	 */
	public String ConsultaArticulo(String ipPv) {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + ipPv + ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV, GlobalVariables.passPV);
			String query = "select top 5 SKU from XposStore.dbo.ITEM order by CHANGE_DATE, sku desc ";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			System.out.println(val);
			articulo = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			System.out.println("Error al consultar el Articulo en BD");
			articulo = "";
		}

		return articulo;
	}
	
	/**
	 * Método - Consulta Articulo con valor dinamico
	 * 
	 * @author Cesar Guel
	 * @return El articulo consultado en la base de datos local
	 * @date 06/09/2024
	 */
	public String ConsultaArticuloD() {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + GlobalVariables.ipPvD + ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV, GlobalVariables.passPV);
			String query = "SELECT TOP 1 A.SKU FROM ITEM as A\r\n"
					+ "INNER JOIN ITEM_BUSINESS_UNIT_LINK AS B ON A.ITEM_ID = B.ITEM_ID \r\n"
					+ "INNER JOIN PRICE AS C ON B.ITEM_BUSINESS_UNIT_LINK_ID = C.ITEM_BUSINESS_UNIT_LINK_ID\r\n"
					+ "WHERE NOT EXISTS (SELECT 1 FROM ITEM_BU_SALE_RESTRICTION WHERE A.ITEM_ID = ITEM_BUSINESS_UNIT_LINK_ID)\r\n"
					+ "AND IS_SALEABLE = 1 AND A.ITEM_STATUS_CODE_ID IN (SELECT [CODE_ID] FROM [XposStore].[dbo].[CODE] WHERE\r\n"
					+ "CODE_TYPE_ID =(SELECT [CODE_TYPE_ID] FROM [XposStore].[dbo].[CODE_TYPE] WHERE CODE ='ITEM_STATUS_CODE') AND NAME='ACTIVE_ITEM_STATUS') \r\n"
					+ "AND A.ITEM_TYPE_CODE_ID =(SELECT [CODE_ID] FROM [XposStore].[dbo].[CODE] WHERE CODE_TYPE_ID =\r\n"
					+ "(SELECT [CODE_TYPE_ID] FROM [XposStore].[dbo].[CODE_TYPE] WHERE CODE ='ITEM_TYPE_CODE') AND NAME='PRODUCT_ITEM')\r\n"
					+ "AND PRICE_TYPE_CODE_ID = ((SELECT [CODE_ID] FROM [XposStore].[dbo].[CODE] WHERE CODE_TYPE_ID =\r\n"
					+ "(SELECT [CODE_TYPE_ID] FROM [XposStore].[dbo].[CODE_TYPE] WHERE CODE ='PRICE_TYPE_CODE') AND NAME='SALE_PRICE'))\r\n"
					+ "AND (ID_PVR_ALIAS IS NULL OR ID_PVR_ALIAS = '')\r\n"
					+ "AND NAME NOT LIKE '%TAE%'\r\n"
					+ "AND NAME NOT LIKE '%TARJETA%'\r\n"
					+ "AND SKU LIKE '%750%'\r\n"
					+ "AND C.AMOUNT BETWEEN 5.0000 AND 249.0000\r\n"
					+ "AND C.IS_ACTIVE = 1\r\n"
					+ "ORDER BY NEWID()";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			System.out.println(val);
			articulo = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			System.out.println("Error al consultar el Articulo en BD");
			articulo = "";
		}

		return articulo;
	}

	/**
	 * Método - Consulta Articulo
	 * 
	 * @author Cesar Guel
	 * @param ipPV La ip de la maquina
	 * @return El articulo consultado en la base de datos local
	 * @date 02/09/2025
	 */
	public String ConsultaArticulol(String ipPV) {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + ipPV + ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV, GlobalVariables.passPV);
			String query = "select top 5 SKU from XposStore.dbo.ITEM order by CHANGE_DATE desc ";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			System.out.println(val);
			articulo = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			System.out.println("Error al consultar el Articulo en BD");
			articulo = "";
		}

		return articulo;
	}

	/**
	 * Método - Consulta promoción Simple
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @return El articulo en promoción consultado en la base de datos local
	 * @date 09/09/2024
	 */
	public String consultaArticuloPrmSimple(String ipPv) {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + ipPv + ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV, GlobalVariables.passPV);
			String query = "select ID_PRM from XposStore.dbo.co_prm where ID_PRM_MCH_TYP = 1 and IS_ACTIVE = 1 order by CREATED_DATETIME desc ";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			System.out.println(val);
			prmSimple = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			System.out.println("Error al consultar el Articulo en Promoción");
			prmSimple = "";
		}

		return prmSimple;
	}

	/**
	 * Método - Consulta Articulo Promoción Simple
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @return El articulo en promoción consultado en la base de datos local
	 * @date 26/03/2025
	 */
	public String consultaSkuArtPrmSimple(String ipPv) {
		try {
			String idPrmSimple = consultaArticuloPrmSimple(ipPv);
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + ipPv + ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV, GlobalVariables.passPV);
			String query = "select top 5 CD_SKU from XposStore.dbo.CO_PRM_TGR_ITM_OX where ID_PRM = " + idPrmSimple
					+ "";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			skuSimple = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			e.printStackTrace();
			skuSimple = "";
		}

		return skuSimple;
	}

	/**
	 * Método - Consulta Articulo Promoción Nube
	 * 
	 * @author Cesar Guel
	 * @param nomTienda El nombre de la tienda a utilizar
	 * @return El articulo en promoción consultado en la base de datos nube
	 * @date 12/05/2025
	 */
	public String consultaSkuArtPrmN(String nomTienda) {
		try {
			String idPrmN = consultaStoreIdPrm(nomTienda);
			Connection conn = DriverManager.getConnection(GlobalVariables.urlOxxoCoudQA, GlobalVariables.userQANubeP,
					GlobalVariables.passwordQANubeP);
//			String query = "select top 1 p.CD_SKU FROM XposCloudPromotionsQA.dbo.PM_CO_PRM_STORE t "
//					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM_TGR_ITM_OX p on p.ID_PRM = t.ID_PRM "
//					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM tr on t.ID_PRM = TR.ID_PRM "
//					+ "where t.STORE_ID = '" + idPrmN
//					+ "' and tr.NM_PRM_CT like '%Simple%' order by tr.DC_PRM_EP desc ";
			String query = "select top 1 p.CD_SKU FROM XposCloudPromotionsQA.dbo.PM_CO_PRM_STORE t\r\n"
					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM_TGR_ITM_OX p on p.ID_PRM = t.ID_PRM \r\n"
					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM tr on t.ID_PRM = TR.ID_PRM\r\n"
					+ "inner join XposCloudItemQA.dbo.ITEM It on It.sku = p.cd_Sku\r\n"
					+ "inner join XposCloudItemQA.dbo.ITEM_GROUP gr on gr.CODE = It.ID_ITM_GP_SBCAT\r\n"
					+ "where t.STORE_ID = '" + idPrmN + "' and tr.NM_PRM_CT like '%Simple%'\r\n"
					+ "and gr.NAME not like '%alcoh%' and  gr.NAME not like '%cerve%' and gr.NAME not like '%vino%'\r\n"
					+ "order by tr.DC_PRM_EP DESC";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			System.out.println(val);
			skuSimple = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al realizar la consulta del articulo en BD");
			skuSimple = "";
		}

		return skuSimple;
	}

	/**
	 * Método - Consulta Articulo Promoción Nube
	 * 
	 * @author Cesar Guel
	 * @return El articulo en promoción consultado en la base de datos nube
	 * @date 13/04/2026
	 */
	public String consultaSkuArtPrmND() {
		try {
			String idPrmN = consultaStoreIdPrmD();
			Connection conn = DriverManager.getConnection(GlobalVariables.urlOxxoCoudQA, GlobalVariables.userQANubeP,
					GlobalVariables.passwordQANubeP);
//			String query = "select top 1 p.CD_SKU FROM XposCloudPromotionsQA.dbo.PM_CO_PRM_STORE t "
//					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM_TGR_ITM_OX p on p.ID_PRM = t.ID_PRM "
//					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM tr on t.ID_PRM = TR.ID_PRM "
//					+ "where t.STORE_ID = '" + idPrmN
//					+ "' and tr.NM_PRM_CT like '%Simple%' order by tr.DC_PRM_EP desc ";
			String query = "select top 1 p.CD_SKU FROM XposCloudPromotionsQA.dbo.PM_CO_PRM_STORE t\r\n"
					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM_TGR_ITM_OX p on p.ID_PRM = t.ID_PRM\r\n"
					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM tr on t.ID_PRM = TR.ID_PRM\r\n"
					+ "inner join XposCloudItemQA.dbo.ITEM It on It.sku = p.cd_Sku\r\n"
					+ "inner join XposCloudItemQA.dbo.ITEM_GROUP gr on gr.CODE = It.ID_ITM_GP_SBCAT\r\n"
					+ "where t.STORE_ID = '" + idPrmN + "' and tr.NM_PRM_CT like '%Simple%'\r\n"
					+ "and gr.NAME not like '%alcoh%' and  gr.NAME not like '%cerve%' and gr.NAME not like '%vino%'\r\n"
					+ "order by tr.DC_PRM_EP DESC";
			
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			System.out.println(val);
			skuSimple = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al realizar la consulta del articulo en BD");
			skuSimple = "";
		}

		return skuSimple;
	}

	/**
	 * Metodo - Consulta Tipo de promoción MultiUnit
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @return El articulo en promoción consultado en la base de datos local
	 * @date 26/03/2025
	 */
	public String consultaTipPrmMultiUnit(String ipPv) {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + ipPv + ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV, GlobalVariables.passPV);
			String query = "select ID_PRM from XposStore.dbo.co_prm where ID_PRM_MCH_TYP = 2 and IS_ACTIVE = 1 order by CREATED_DATETIME desc";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			prmMulti = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			System.out.println("Error al consultar el Articulo en Promoción");
			e.printStackTrace();
			prmMulti = "";
		}

		return prmMulti;
	}

	/**
	 * Método - Consulta Articulo Promoción MultiUnit
	 * 
	 * @author Cesar Guel
	 * @param nomTienda El nombre de la tienda utilizado
	 * @return El articulo en promoción consultado en la base de datos nube
	 * @date 26/03/2025
	 */
	public String consultaSkuPrmMulti(String nomTienda) {
		try {
			String idPrmMulti = consultaStoreIdPrm(nomTienda);

			Connection conn = DriverManager.getConnection(GlobalVariables.urlOxxoCoudQA, GlobalVariables.userQANubeP,
					GlobalVariables.passwordQANubeP);
//			String query = "select top 5 p.CD_SKU FROM XposCloudPromotionsQA.dbo.PM_CO_PRM_STORE t "
//					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM_TGR_ITM_OX p on p.ID_PRM = t.ID_PRM "
//					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM tr on t.ID_PRM = TR.ID_PRM "
//					+ "where t.STORE_ID = '" + idPrmMulti + "' and tr.NM_PRM_CT like '%Multi%' "
//					+ "order by tr.DC_PRM_EF DESC ";
			String query = "select top 5 p.CD_SKU FROM XposCloudPromotionsQA.dbo.PM_CO_PRM_STORE t "
					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM_TGR_ITM_OX p on p.ID_PRM = t.ID_PRM "
					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM tr on t.ID_PRM = TR.ID_PRM "
					+ "where t.STORE_ID = '" + idPrmMulti + "' and tr.NM_PRM_CT like '%Multi%' "
					+ "order by tr.DC_PRM_EF DESC ";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			System.out.println(val);
			skuMulti = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			System.out.println("Error al consultar el Articulo en Promoción");
			e.printStackTrace();
			skuMulti = "";
		}

		return skuMulti;
	}

	/**
	 * Método - Consulta Articulo Promoción MultiUnit
	 * 
	 * @author Cesar Guel
	 * @param nomTienda El nombre de la tienda utilizado
	 * @return El articulo en promoción consultado en la base de datos nube
	 * @date 13/04/2026
	 */
	public String consultaSkuPrmMultiD() {
		try {
			String idPrmMulti = consultaStoreIdPrmD();

			Connection conn = DriverManager.getConnection(GlobalVariables.urlOxxoCoudQA, GlobalVariables.userQANubeP,
					GlobalVariables.passwordQANubeP);
//			String query = "select top 5 p.CD_SKU FROM XposCloudPromotionsQA.dbo.PM_CO_PRM_STORE t "
//					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM_TGR_ITM_OX p on p.ID_PRM = t.ID_PRM "
//					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM tr on t.ID_PRM = TR.ID_PRM "
//					+ "where t.STORE_ID = '" + idPrmMulti + "' and tr.NM_PRM_CT like '%Multi%' "
//					+ "order by tr.DC_PRM_EF DESC ";
			String query = "select top 5 p.CD_SKU FROM XposCloudPromotionsQA.dbo.PM_CO_PRM_STORE t\r\n"
					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM_TGR_ITM_OX p on p.ID_PRM = t.ID_PRM \r\n"
					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM tr on t.ID_PRM = TR.ID_PRM\r\n"
					+ "inner join XposCloudItemQA.dbo.ITEM It on It.sku = p.cd_Sku\r\n"
					+ "inner join XposCloudItemQA.dbo.ITEM_GROUP gr on gr.CODE = It.ID_ITM_GP_SBCAT\r\n"
					+ "where t.STORE_ID = '" + idPrmMulti + "' and tr.NM_PRM_CT like '%Multi%'\r\n"
					+ "and gr.NAME not like '%alcoh%' and  gr.NAME not like '%cerve%' and gr.NAME not like '%vino%'\r\n"
					+ "order by tr.DC_PRM_EF DESC";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			System.out.println(val);
			skuMulti = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			System.out.println("Error al consultar el Articulo en Promoción");
			e.printStackTrace();
			skuMulti = "";
		}

		return skuMulti;
	}

	/**
	 * Método - Consulta tipo de promoción PromoPack
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @return El ID del tipo de la promoción
	 * @date 26/03/2025
	 */
	public String consultaTipPrmPromP(String ipPv) {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + ipPv + ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV, GlobalVariables.passPV);
			String query = "select ID_PRM from XposStore.dbo.co_prm where ID_PRM_MCH_TYP = 3 and IS_ACTIVE = 1 order by CREATED_DATETIME desc";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			prmPromP = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			e.printStackTrace();
			prmPromP = "";
		}

		return prmPromP;
	}

	/**
	 * Método - Consulta Sku de articulo en promoción PromoPack
	 * 
	 * @author Cesar Guel
	 * @param nomTienda El nombre de la tienda utilizado
	 * @return El articulo en promoción consultado en la base de datos nube
	 * @date 26/03/2025
	 */
	public String consultaSkuPrmPromoP(String nomTienda) {
		try {
			String idPrmPromoP = consultaStoreIdPrm(nomTienda);
			Connection conn = DriverManager.getConnection(GlobalVariables.urlOxxoCoudQA, GlobalVariables.userQANubeP,
					GlobalVariables.passwordQANubeP);
//			String query = "select top 5 p.CD_SKU FROM XposCloudPromotionsQA.dbo.PM_CO_PRM_STORE t "
//					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM_TGR_ITM_OX p on p.ID_PRM = t.ID_PRM "
//					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM tr on t.ID_PRM = TR.ID_PRM "
//					+ "where t.STORE_ID = ' " + idPrmPromoP + " ' and tr.NM_PRM_CT like '%PromoPack%' "
//					+ "order by p.TM_CRT DESC ";
			String query = "select top 5 p.CD_SKU FROM XposCloudPromotionsQA.dbo.PM_CO_PRM_STORE t\r\n"
					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM_TGR_ITM_OX p on p.ID_PRM = t.ID_PRM \r\n"
					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM tr on t.ID_PRM = TR.ID_PRM\r\n"
					+ "inner join XposCloudItemQA.dbo.ITEM It on It.sku = p.cd_Sku\r\n"
					+ "inner join XposCloudItemQA.dbo.ITEM_GROUP gr on gr.CODE = It.ID_ITM_GP_SBCAT\r\n"
					+ "where t.STORE_ID = '" + idPrmPromoP + "' and tr.NM_PRM_CT like '%PromoPack%'\r\n"
					+ "and gr.NAME not like '%alcoh%' and  gr.NAME not like '%cerve%' and gr.NAME not like '%vino%'\r\n"
					+ "order by p.TM_CRT DESC";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			System.out.println(val);
			skuMulti = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			System.out.println("Error al consultar el Articulo en Promoción");
			e.printStackTrace();
			skuMulti = "";
		}

		return skuMulti;
	}

	/**
	 * Método - Consulta Sku de articulo en promoción PromoPack
	 * 
	 * @author Cesar Guel
	 * @param nomTienda El nombre de la tienda utilizado
	 * @return El articulo en promoción consultado en la base de datos nube
	 * @date 26/03/2025
	 */
	public String consultaSkuPrmPromoPD() {
		try {
			String idPrmPromoP = consultaStoreIdPrmD();
			Connection conn = DriverManager.getConnection(GlobalVariables.urlOxxoCoudQA, GlobalVariables.userQANubeP,
					GlobalVariables.passwordQANubeP);
//			String query = "select top 5 p.CD_SKU FROM XposCloudPromotionsQA.dbo.PM_CO_PRM_STORE t "
//					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM_TGR_ITM_OX p on p.ID_PRM = t.ID_PRM "
//					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM tr on t.ID_PRM = TR.ID_PRM "
//					+ "where t.STORE_ID = ' " + idPrmPromoP + " ' and tr.NM_PRM_CT like '%PromoPack%' "
//					+ "order by tr.DC_PRM_EF DESC ";
			String query = "select top 5 p.CD_SKU FROM XposCloudPromotionsQA.dbo.PM_CO_PRM_STORE t "
					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM_TGR_ITM_OX p on p.ID_PRM = t.ID_PRM "
					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM tr on t.ID_PRM = TR.ID_PRM "
					+ "where t.STORE_ID = ' " + idPrmPromoP + " ' and tr.NM_PRM_CT like '%PromoPack%' "
					+ "order by tr.DC_PRM_EF DESC ";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			System.out.println(val);
			skuMulti = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			System.out.println("Error al consultar el Articulo en Promoción");
			e.printStackTrace();
			skuMulti = "";
		}

		return skuMulti;
	}

	/**
	 * Método - Consulta tipo de promoción Hide
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @return El ID del tipo de la promoción
	 * @date 26/03/2025
	 */
	public String consultaTipPrmHide(String ipPv) {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + ipPv + ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV, GlobalVariables.passPV);
			String query = "select ID_PRM from XposStore.dbo.co_prm where ID_PRM_MCH_TYP = 4 and IS_ACTIVE = 1 order by CREATED_DATETIME desc";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			prmHide = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			e.printStackTrace();
			prmHide = "";
		}

		return prmHide;
	}

	/**
	 * Método - Consulta Sku de articulo en promoción Hide
	 * 
	 * @author Cesar Guel
	 * @param nomTienda El nombre de la tienda utilizado
	 * @return El articulo en promoción consultado en la base de datos nube
	 * @date 26/03/2025
	 */
	public String consultaSkuPrmHide(String nomTienda) {
		try {
			String idPrmHide = consultaStoreIdPrm(nomTienda);
			Connection conn = DriverManager.getConnection(GlobalVariables.urlOxxoCoudQA, GlobalVariables.userQANubeP,
					GlobalVariables.passwordQANubeP);
//			String query = "select top 5 p.CD_SKU FROM XposCloudPromotionsQA.dbo.PM_CO_PRM_STORE t "
//					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM_TGR_ITM_OX p on p.ID_PRM = t.ID_PRM  "
//					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM tr on t.ID_PRM = TR.ID_PRM "
//					+ "where t.STORE_ID = '" + idPrmHide + "' and tr.NM_PRM_CT like '%Hide%' "
//					+ "order by tr.DC_PRM_EP DESC ";
			String query = "select top 5 p.CD_SKU FROM XposCloudPromotionsQA.dbo.PM_CO_PRM_STORE t\r\n"
					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM_TGR_ITM_OX p on p.ID_PRM = t.ID_PRM \r\n"
					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM tr on t.ID_PRM = TR.ID_PRM\r\n"
					+ "inner join XposCloudItemQA.dbo.ITEM It on It.sku = p.cd_Sku\r\n"
					+ "inner join XposCloudItemQA.dbo.ITEM_GROUP gr on gr.CODE = It.ID_ITM_GP_SBCAT\r\n"
					+ "where t.STORE_ID = '" + idPrmHide + "' and tr.NM_PRM_CT like '%Hide%'\r\n"
					+ "and gr.NAME not like '%alcoh%' and  gr.NAME not like '%cerve%' and gr.NAME not like '%vino%'\r\n"
					+ "order by tr.DC_PRM_EP DESC";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			skuHide = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			e.printStackTrace();
			skuHide = "";
		}

		return skuHide;
	}

	/**
	 * Método - Consulta Sku de articulo en promoción Hide parametros dinamicos
	 * 
	 * @author Cesar Guel
	 * @param nomTienda El nombre de la tienda utilizado
	 * @return El articulo en promoción consultado en la base de datos nube
	 * @date 13/04/2026
	 */
	public String consultaSkuPrmHideD() {
		try {
			String idPrmHide = consultaStoreIdPrmD();
			Connection conn = DriverManager.getConnection(GlobalVariables.urlOxxoCoudQA, GlobalVariables.userQANubeP,
					GlobalVariables.passwordQANubeP);
//			String query = "select top 5 p.CD_SKU FROM XposCloudPromotionsQA.dbo.PM_CO_PRM_STORE t "
//					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM_TGR_ITM_OX p on p.ID_PRM = t.ID_PRM  "
//					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM tr on t.ID_PRM = TR.ID_PRM "
//					+ "where t.STORE_ID = '" + idPrmHide + "' and tr.NM_PRM_CT like '%Hide%' "
//					+ "order by tr.DC_PRM_EP DESC ";
			String query = "select top 5 p.CD_SKU FROM XposCloudPromotionsQA.dbo.PM_CO_PRM_STORE t\r\n"
					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM_TGR_ITM_OX p on p.ID_PRM = t.ID_PRM \r\n"
					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM tr on t.ID_PRM = TR.ID_PRM\r\n"
					+ "inner join XposCloudItemQA.dbo.ITEM It on It.sku = p.cd_Sku\r\n"
					+ "inner join XposCloudItemQA.dbo.ITEM_GROUP gr on gr.CODE = It.ID_ITM_GP_SBCAT\r\n"
					+ "where t.STORE_ID = '" + idPrmHide + "' and tr.NM_PRM_CT like '%Hide%'\r\n"
					+ "and gr.NAME not like '%alcoh%' and  gr.NAME not like '%cerve%' and gr.NAME not like '%vino%'\r\n"
					+ "order by tr.DC_PRM_EP DESC";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			skuHide = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			e.printStackTrace();
			skuHide = "";
		}

		return skuHide;
	}

	/**
	 * Método - Consulta Sku de articulo en promoción MBG
	 * 
	 * @author Cesar Guel
	 * @param nomTienda El nombre de la tienda utilizado
	 * @return El articulo en promoción consultado en la base de datos nube
	 * @date 26/03/2025
	 */
	public String consultaSkuPrmMBG(String nomTienda) {
		try {
			String idPrmMBG = consultaStoreIdPrm(nomTienda);
			Connection conn = DriverManager.getConnection(GlobalVariables.urlOxxoCoudQA, GlobalVariables.userQANubeP,
					GlobalVariables.passwordQANubeP);
//			String query = "select top 5 p.CD_SKU FROM XposCloudPromotionsQA.dbo.PM_CO_PRM_STORE t "
//					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM_TGR_ITM_OX p on p.ID_PRM = t.ID_PRM  "
//					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM tr on t.ID_PRM = TR.ID_PRM "
//					+ "where t.STORE_ID = '" + idPrmMBG + "' and tr.NM_PRM_CT like '%MBG%' "
//					+ "order by tr.DC_PRM_EP DESC ";
			String query = "select top 5 p.CD_SKU FROM XposCloudPromotionsQA.dbo.PM_CO_PRM_STORE t\r\n"
					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM_TGR_ITM_OX p on p.ID_PRM = t.ID_PRM \r\n"
					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM tr on t.ID_PRM = TR.ID_PRM\r\n"
					+ "inner join XposCloudItemQA.dbo.ITEM It on It.sku = p.cd_Sku\r\n"
					+ "inner join XposCloudItemQA.dbo.ITEM_GROUP gr on gr.CODE = It.ID_ITM_GP_SBCAT\r\n"
					+ "where t.STORE_ID = '" + idPrmMBG + "' and tr.NM_PRM_CT like '%MBG%'\r\n"
					+ "and gr.NAME not like '%alcoh%' and  gr.NAME not like '%cerve%' and gr.NAME not like '%vino%'\r\n"
					+ "order by tr.DC_PRM_EP DESC";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			skuHide = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			e.printStackTrace();
			skuHide = "";
		}

		return skuHide;
	}

	/**
	 * Método - Consulta Sku de articulo en promoción MBG
	 * 
	 * @author Cesar Guel
	 * @return El articulo en promoción consultado en la base de datos nube
	 * @date 13/04/2026
	 */
	public String consultaSkuPrmMBGD() {
		try {
			String idPrmMBG = consultaStoreIdPrmD();
			Connection conn = DriverManager.getConnection(GlobalVariables.urlOxxoCoudQA, GlobalVariables.userQANubeP,
					GlobalVariables.passwordQANubeP);
//			String query = "select top 5 p.CD_SKU FROM XposCloudPromotionsQA.dbo.PM_CO_PRM_STORE t "
//					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM_TGR_ITM_OX p on p.ID_PRM = t.ID_PRM  "
//					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM tr on t.ID_PRM = TR.ID_PRM "
//					+ "where t.STORE_ID = '" + idPrmMBG + "' and tr.NM_PRM_CT like '%MBG%' "
//					+ "order by tr.DC_PRM_EP DESC ";
			String query = "select top 5 p.CD_SKU FROM XposCloudPromotionsQA.dbo.PM_CO_PRM_STORE t\r\n"
					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM_TGR_ITM_OX p on p.ID_PRM = t.ID_PRM \r\n"
					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM tr on t.ID_PRM = TR.ID_PRM\r\n"
					+ "inner join XposCloudItemQA.dbo.ITEM It on It.sku = p.cd_Sku\r\n"
					+ "inner join XposCloudItemQA.dbo.ITEM_GROUP gr on gr.CODE = It.ID_ITM_GP_SBCAT\r\n"
					+ "where t.STORE_ID = '" + idPrmMBG + "' and tr.NM_PRM_CT like '%MBG%'\r\n"
					+ "and gr.NAME not like '%alcoh%' and  gr.NAME not like '%cerve%' and gr.NAME not like '%vino%'\r\n"
					+ "order by tr.DC_PRM_EP DESC";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			skuHide = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			e.printStackTrace();
			skuHide = "";
		}

		return skuHide;
	}

	/**
	 * Método - Consulta Sku de articulo en promoción Pivote
	 * 
	 * @author Cesar Guel
	 * @param nomTienda El nombre de la tienda a utilizar
	 * @return El articulo en promoción consultado en la base de datos nube
	 * @date 26/03/2025
	 */
	public String consultaSkuPrmPivote(String nomTienda) {
		try {
			String idPrmPivote = consultaStoreIdPrm(nomTienda);
			Connection conn = DriverManager.getConnection(GlobalVariables.urlOxxoCoudQA, GlobalVariables.userQANubeP,
					GlobalVariables.passwordQANubeP);
//			String query = "select top 5 p.CD_SKU FROM XposCloudPromotionsQA.dbo.PM_CO_PRM_STORE t "
//					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM_TGR_ITM_OX p on p.ID_PRM = t.ID_PRM  "
//					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM tr on t.ID_PRM = TR.ID_PRM "
//					+ "where t.STORE_ID = '" + idPrmPivote + "' and tr.NM_PRM_CT like '%Pivote%' "
//					+ "order by tr.DC_PRM_EP DESC ";
			String query = "select top 5 p.CD_SKU FROM XposCloudPromotionsQA.dbo.PM_CO_PRM_STORE t\r\n"
					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM_TGR_ITM_OX p on p.ID_PRM = t.ID_PRM \r\n"
					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM tr on t.ID_PRM = TR.ID_PRM\r\n"
					+ "inner join XposCloudItemQA.dbo.ITEM It on It.sku = p.cd_Sku\r\n"
					+ "inner join XposCloudItemQA.dbo.ITEM_GROUP gr on gr.CODE = It.ID_ITM_GP_SBCAT\r\n"
					+ "where t.STORE_ID = '" + idPrmPivote + "' and tr.NM_PRM_CT like '%Pivote%'\r\n"
					+ "and gr.NAME not like '%alcoh%' and  gr.NAME not like '%cerve%' and gr.NAME not like '%vino%'\r\n"
					+ "order by tr.DC_PRM_EP DESC";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			System.out.println(idPrmPivote);
			System.out.println(val);
			skuPivote1 = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			e.printStackTrace();
			skuPivote1 = "";
		}

		return skuPivote1;
	}

	/**
	 * Método - Consulta Sku de articulo en promoción Pivote con datos dinamicos
	 * 
	 * @author Cesar Guel
	 * @return El articulo en promoción consultado en la base de datos nube
	 * @date 15/04/2026
	 */
	public String consultaSkuPrmPivoteD() {
		try {
			String idPrmPivote = consultaStoreIdPrmD();
			Connection conn = DriverManager.getConnection(GlobalVariables.urlOxxoCoudQA, GlobalVariables.userQANubeP,
					GlobalVariables.passwordQANubeP);
//			String query = "select top 5 p.CD_SKU FROM XposCloudPromotionsQA.dbo.PM_CO_PRM_STORE t "
//					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM_TGR_ITM_OX p on p.ID_PRM = t.ID_PRM  "
//					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM tr on t.ID_PRM = TR.ID_PRM "
//					+ "where t.STORE_ID = '" + idPrmPivote + "' and tr.NM_PRM_CT like '%Pivote%' "
//					+ "order by tr.DC_PRM_EP DESC ";
			String query = "select top 5 p.CD_SKU FROM XposCloudPromotionsQA.dbo.PM_CO_PRM_STORE t\r\n"
					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM_TGR_ITM_OX p on p.ID_PRM = t.ID_PRM \r\n"
					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM tr on t.ID_PRM = TR.ID_PRM\r\n"
					+ "inner join XposCloudItemQA.dbo.ITEM It on It.sku = p.cd_Sku\r\n"
					+ "inner join XposCloudItemQA.dbo.ITEM_GROUP gr on gr.CODE = It.ID_ITM_GP_SBCAT\r\n"
					+ "where t.STORE_ID = '" + idPrmPivote + "' and tr.NM_PRM_CT like '%Pivote%'\r\n"
					+ "and gr.NAME not like '%alcoh%' and  gr.NAME not like '%cerve%' and gr.NAME not like '%vino%'\r\n"
					+ "order by tr.DC_PRM_EP DESC";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			System.out.println(idPrmPivote);
			System.out.println(val);
			skuPivote1 = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			e.printStackTrace();
			skuPivote1 = "";
		}

		return skuPivote1;
	}

	/**
	 * Método - Consulta Sku de articulo en promoción Pivote numero 2
	 * 
	 * @author Cesar Guel
	 * @param nomTienda El nombre de la tienda a utilizar
	 * @return El Segundo articulo en promoción consultado en la base de datos nube
	 * @date 26/03/2025
	 */
	public String consultaSkuPrmPivote2(String nomTienda) {
		try {
			String idPrmPivote = consultaStoreIdPrm(nomTienda);
			Connection conn = DriverManager.getConnection(GlobalVariables.urlOxxoCoudQA, GlobalVariables.userQANubeP,
					GlobalVariables.passwordQANubeP);
//			String query = "SELECT p.CD_SKU FROM XposCloudPromotionsQA.dbo.PM_CO_PRM_STORE t "
//					+ "INNER JOIN XposCloudPromotionsQA.dbo.PM_CO_PRM_TGR_ITM_OX p ON p.ID_PRM = t.ID_PRM "
//					+ "INNER JOIN XposCloudPromotionsQA.dbo.PM_CO_PRM tr ON t.ID_PRM = tr.ID_PRM "
//					+ "WHERE t.STORE_ID = '" + idPrmPivote + "' AND tr.NM_PRM_CT LIKE '%Pivote%' "
//					+ "ORDER BY tr.DC_PRM_EP DESC " + "OFFSET 1 ROWS FETCH NEXT 1 ROWS ONLY ";
//			SQLResult res = new SQLResult(conn, query);
			String query = "SELECT p.CD_SKU FROM XposCloudPromotionsQA.dbo.PM_CO_PRM_STORE t\r\n"
					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM_TGR_ITM_OX p on p.ID_PRM = t.ID_PRM \r\n"
					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM tr on t.ID_PRM = TR.ID_PRM\r\n"
					+ "inner join XposCloudItemQA.dbo.ITEM It on It.sku = p.cd_Sku\r\n"
					+ "inner join XposCloudItemQA.dbo.ITEM_GROUP gr on gr.CODE = It.ID_ITM_GP_SBCAT\r\n"
					+ "where t.STORE_ID = '" + idPrmPivote + "' and tr.NM_PRM_CT like '%Pivote%'\r\n"
					+ "and gr.NAME not like '%alcoh%' and  gr.NAME not like '%cerve%' and gr.NAME not like '%vino%'\r\n"
					+ "ORDER BY tr.DC_PRM_EP DESC " + "OFFSET 1 ROWS FETCH NEXT 1 ROWS ONLY";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			System.out.println(val);
			skuPivote2 = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			e.printStackTrace();
			skuPivote2 = "";
		}

		return skuPivote2;
	}

	/**
	 * Método - Consulta Sku de articulo en promoción Pivote numero 2 con datos
	 * dinamicos
	 * 
	 * @author Cesar Guel
	 * @return El Segundo articulo en promoción consultado en la base de datos nube
	 * @date 15/04/2026
	 */
	public String consultaSkuPrmPivote2D() {
		try {
			String idPrmPivote = consultaStoreIdPrmD();
			Connection conn = DriverManager.getConnection(GlobalVariables.urlOxxoCoudQA, GlobalVariables.userQANubeP,
					GlobalVariables.passwordQANubeP);
//			String query = "SELECT p.CD_SKU FROM XposCloudPromotionsQA.dbo.PM_CO_PRM_STORE t "
//					+ "INNER JOIN XposCloudPromotionsQA.dbo.PM_CO_PRM_TGR_ITM_OX p ON p.ID_PRM = t.ID_PRM "
//					+ "INNER JOIN XposCloudPromotionsQA.dbo.PM_CO_PRM tr ON t.ID_PRM = tr.ID_PRM "
//					+ "WHERE t.STORE_ID = '" + idPrmPivote + "' AND tr.NM_PRM_CT LIKE '%Pivote%' "
//					+ "ORDER BY tr.DC_PRM_EP DESC " + "OFFSET 1 ROWS FETCH NEXT 1 ROWS ONLY ";
			String query = "SELECT p.CD_SKU FROM XposCloudPromotionsQA.dbo.PM_CO_PRM_STORE t\r\n"
					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM_TGR_ITM_OX p on p.ID_PRM = t.ID_PRM \r\n"
					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM tr on t.ID_PRM = TR.ID_PRM\r\n"
					+ "inner join XposCloudItemQA.dbo.ITEM It on It.sku = p.cd_Sku\r\n"
					+ "inner join XposCloudItemQA.dbo.ITEM_GROUP gr on gr.CODE = It.ID_ITM_GP_SBCAT\r\n"
					+ "where t.STORE_ID = '" + idPrmPivote + "' and tr.NM_PRM_CT like '%Pivote%'\r\n"
					+ "and gr.NAME not like '%alcoh%' and  gr.NAME not like '%cerve%' and gr.NAME not like '%vino%'\r\n"
					+ "ORDER BY tr.DC_PRM_EP DESC " + "OFFSET 1 ROWS FETCH NEXT 1 ROWS ONLY";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			System.out.println(val);
			skuPivote2 = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			e.printStackTrace();
			skuPivote2 = "";
		}

		return skuPivote2;
	}

	/**
	 * Método - Consulta tipo de promoción MBG
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @return El ID del tipo de la promoción
	 * @date 26/03/2025
	 */
	public String consultaTipPrmMBG(String ipPv) {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + ipPv + ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV, GlobalVariables.passPV);
			String query = "select ID_PRM from XposStore.dbo.co_prm where ID_PRM_MCH_TYP = 9 and IS_ACTIVE = 1 order by CREATED_DATETIME desc";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			prmMBG = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			e.printStackTrace();
			prmMBG = "";
		}

		return prmMBG;
	}

	/**
	 * Método - Consulta Sku de articulo 1 en promoción MBG
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @return El primer articulo en promoción consultado en la base de datos local
	 * @date 26/03/2025
	 */
	public String consultaSkuPrmMBG1(String ipPv) {
		try {
			String idPrmMBG1 = consultaTipPrmMBG(ipPv);
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + ipPv + ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV, GlobalVariables.passPV);
			String query = "select top 5 CD_SKU from XposStore.dbo.CO_PRM_TGR_ITM_OX where  = '" + idPrmMBG1 + "'";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			skuMBG1 = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			e.printStackTrace();
			skuMBG1 = "";
		}

		return skuMBG1;
	}

	/**
	 * Método - Consulta Sku de articulo 2 en promoción MBG
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @return El segundo articulo en promoción consultado en la base de datos local
	 * @date 26/03/2025
	 */
	public String consultaSkuPrmMBG2(String ipPv) {
		try {
			String idPrmMBG2 = consultaTipPrmMBG(ipPv);
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + ipPv + ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV, GlobalVariables.passPV);
			String query = "select CD_SKU from XposStore.dbo.CO_PRM_TGR_ITM_OX where  = '" + idPrmMBG2 + "'";
			SQLResult res = new SQLResult(conn, query);
			testCase.addQueryEvidenceCurrentStep(res);
			if (res.getRowCount() >= 2) {
				val = res.getData(1, 0).toString();
				skuMBG2 = val.substring(0);
			} else {
				System.out.println("No se encontró un segundo resultado para el ID: " + idPrmMBG2);
				skuMBG2 = "";
			}

		} catch (Exception e) {
			e.printStackTrace();
			skuMBG2 = "";
		}

		return skuMBG2;
	}

	/**
	 * Método - Consulta tipo de promoción Pivote
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @return El ID del tipo de la promoción
	 * @date 26/03/2025
	 */
	public String consultaTipPrmPivote(String ipPv) {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + ipPv + ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV, GlobalVariables.passPV);
			String query = "select ID_PRM from XposStore.dbo.co_prm where ID_PRM_MCH_TYP = 10 and IS_ACTIVE = 1 order by CREATED_DATETIME desc";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			prmPivote = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			e.printStackTrace();
			prmPivote = "";
		}

		return prmPivote;
	}

	/**
	 * Método - Consulta Articulo Promoción Nube
	 * 
	 * @author Cesar Guel
	 * @param nomTienda El nombre de la tienda a utilizar
	 * @return El articulo en promoción consultado en la base de datos nube
	 * @date 12/05/2025
	 */
	public String consultaSkuArtPrmCiclo(String nomTienda) {
		try {
			String idPrmN = consultaStoreIdPrm(nomTienda);
			Connection conn = DriverManager.getConnection(GlobalVariables.urlOxxoCoudQA, GlobalVariables.userQANubeP,
					GlobalVariables.passwordQANubeP);
//			String query = "select p.CD_SKU FROM XposCloudPromotionsQA.dbo.PM_CO_PRM_STORE t "
//					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM_TGR_ITM_OX p on p.ID_PRM = t.ID_PRM "
//					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM tr on t.ID_PRM = TR.ID_PRM "
//					+ "where t.STORE_ID = '" + idPrmN
//					+ "' and tr.NM_PRM_CT like '%PRM CICLO%' order by tr.DC_PRM_EP desc ";
			String query = "SELECT p.CD_SKU FROM XposCloudPromotionsQA.dbo.PM_CO_PRM_STORE t\r\n"
					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM_TGR_ITM_OX p on p.ID_PRM = t.ID_PRM \r\n"
					+ "inner join XposCloudPromotionsQA.dbo.PM_CO_PRM tr on t.ID_PRM = TR.ID_PRM\r\n"
					+ "inner join XposCloudItemQA.dbo.ITEM It on It.sku = p.cd_Sku\r\n"
					+ "inner join XposCloudItemQA.dbo.ITEM_GROUP gr on gr.CODE = It.ID_ITM_GP_SBCAT\r\n"
					+ "where t.STORE_ID = '" + idPrmN + "' and tr.NM_PRM_CT like '%PRM CICLO%'\r\n"
					+ "and gr.NAME not like '%alcoh%' and  gr.NAME not like '%cerve%' and gr.NAME not like '%vino%'\r\n"
					+ "order by tr.DC_PRM_EP desc";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			System.out.println(val);
			skuSimple = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			e.printStackTrace();
			skuSimple = "";
		}

		return skuSimple;
	}

	/**
	 * Método - Consulta Articulo Bebida Alcoholica
	 * 
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @return El articulo bebida alcoholica cosnultado en las base de datos local
	 * @date 10/09/2024
	 */
	public String ConsultaArticuloBA(String ipPv) {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + ipPv + ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV, GlobalVariables.passPV);
			String query = "select top 5 SKU from XposStore.dbo.ITEM where DESCRIPTION like '%CERVEZA%'";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			articuloBA = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			System.out.println("Error al consultar el Articulo BA en BD");
			e.printStackTrace();
			articuloBA = "";
		}

		return articuloBA;
	}

	/**
	 * Método - Consulta Articulo Bebida Alcoholica con valor dinamico 
	 * 
	 * 
	 * @author Cesar Guel
	 * @return El articulo bebida alcoholica cosnultado en las base de datos local
	 * @date 10/09/2024
	 */
	public String ConsultaArticuloBAD() {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + GlobalVariables.ipPvD
							+ ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV, GlobalVariables.passPV);
			String query = "select top 5 SKU from XposStore.dbo.ITEM where DESCRIPTION like '%CERVEZA%'";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			articuloBA = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			System.out.println("Error al consultar el Articulo BA en BD");
			e.printStackTrace();
			articuloBA = "";
		}

		return articuloBA;
	}
	
	/**
	 * Método - Consulta Articulo Bebida Alcoholica con valor dinamico
	 * 
	 * 
	 * @author Cesar Guel
	 * @return El articulo bebida alcoholica cosnultado en las base de datos local
	 * @date 05/05/2026
	 */
	public String ConsultaArticuloBADB() {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + GlobalVariables.ipPvD
							+ ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV, GlobalVariables.passPV);
			String query = "SELECT TOP 1 A.SKU FROM ITEM A\r\n"
					+ "INNER JOIN ITEM_BUSINESS_UNIT_LINK B ON A.ITEM_ID = B.ITEM_ID \r\n"
					+ "INNER JOIN PRICE C ON B.ITEM_BUSINESS_UNIT_LINK_ID = C.ITEM_BUSINESS_UNIT_LINK_ID\r\n"
					+ "WHERE EXISTS (SELECT 1 FROM ITEM_BU_SALE_RESTRICTION R WHERE R.ITEM_BUSINESS_UNIT_LINK_ID = B.ITEM_BUSINESS_UNIT_LINK_ID)\r\n"
					+ "AND A.IS_SALEABLE = 1 AND C.IS_ACTIVE = 1 AND A.DESCRIPTION LIKE '%CERVEZA%'AND C.AMOUNT BETWEEN 10 AND 1000 ORDER BY NEWID()";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			articuloBA = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			System.out.println("Error al consultar el Articulo BA en BD");
			e.printStackTrace();
			articuloBA = "";
		}

		return articuloBA;
	}

	/**
	 * Método - Consulta ticket de la venta realizada en base de datos local -
	 * articulo en promocion
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @return El folio del ticket de la venta realizada desde la base de datos
	 *         local
	 * @date 09/09/2024
	 */
	public String Consultatktprml(String ipPv) {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + ipPv + ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV_116, GlobalVariables.passPV_116);
			String query = "select top 1 txn_number from XposStore.dbo.TXN_RETAIL order by CREATED_DATETIME desc";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			tktprml = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			System.out.println("Error al consultar el Articulo TKTPRM en BD Local");
			e.printStackTrace();
			tktprml = "";
		}

		return tktprml;
	}

	/**
	 * Método - Consulta ticket de la venta realizada en base de datos nube -
	 * articulo en promocion
	 * 
	 * @author Cesar Guel
	 * @param crTienda El nombre de la plaza y tienda utilizado
	 * @return El folio del ticket de la venta realizada desde la base de datos Nube
	 * @date 09/09/2024
	 */
	public String Consultatktprmn(String crTienda) {
		try {
			Connection conn = DriverManager.getConnection(GlobalVariables.urlOxxoCoudQA, GlobalVariables.userQANube,
					GlobalVariables.passwordQANube);
			String query = "select top 1 TICKET from SALES where CR = '" + crTienda
					+ "' order by CREATED_DATETIME desc";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			tktprmn = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			System.out.println("Error al consultar el Articulo TKTPRM en BD Nube");
			e.printStackTrace();
			tktprmn = "";
		}

		return tktprmn;

	}

	/**
	 * Método - Consulta ticket de la promoción en la local
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @return La información del ticket desde la base de datos local validando con
	 *         nube si es correcto
	 * @date 09/09/2024
	 */
	public String consultaCajaPrm(String ipPv) {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + ipPv + ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV, GlobalVariables.passPV);
			String query = "use XposStore" + "SELECT TOP 5 TXN_RETAIL_LINE.ITEM_SKU " + "FROM TXN_RETAIL_LINE "
					+ "INNER JOIN TXN_RETAIL ON TXN_RETAIL.TXN_RETAIL_ID = TXN_RETAIL_LINE.TXN_RETAIL_ID "
					+ "INNER JOIN TXN_HEADER ON TXN_HEADER.TXN_HEADER_ID = TXN_RETAIL.TXN_HEADER_ID "
					+ "WHERE TXN_RETAIL_LINE.ITEM_SKU IS NOT NULL " + "ORDER BY TXN_HEADER.CREATED_DATETIME DESC";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			inftkt = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			System.out.println("Error al consultar inftktl");
			e.printStackTrace();
			inftkt = "";
		}

		if (inftkt.equals(tktprml)) {
			return "Validación exitosa: inftkt es igual a tktprml.";
		} else {
			return "Validación fallida: inftkt y tktprml no coinciden.";
		}
	}

	/**
	 * Método - Consulta ticket de la promoción en la nube
	 * 
	 * @author Cesar Guel
	 * @param crTienda El nombre de la plaza y tienda utilizado
	 * @return El valor del ticket de la venta desde la nube
	 * @date 09/09/2024
	 */
	public String consultaTktPrmN(String crTienda) {
		try {
			Connection conn = DriverManager.getConnection(GlobalVariables.urlOxxoCoudQA, GlobalVariables.userQANube,
					GlobalVariables.passwordQANube);
			String query = "select top 1 TICKET from SALES where CR = '" + crTienda
					+ "' order by CREATED_DATETIME desc";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			tktsalesprmNube = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			System.out.println("Error al consultar el Articulo TKTPRM en BD Nube");
			e.printStackTrace();
			tktsalesprmNube = "";
		}
		return tktsalesprmNube;
	}

	/**
	 * Método - Consulta ticket de la promoción en la nube
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @return La información del ticket desde la base de datos nube validando con
	 *         local si es correcto
	 * @date 09/09/2024
	 */
	public boolean consultaTktPrmNube(String crTienda, String ipPv) {
		try {
			Connection conn = DriverManager.getConnection(GlobalVariables.urlOxxoCoudQA, GlobalVariables.userQANube,
					GlobalVariables.passwordQANube);
			String query = "select top 1 TICKET from SALES where CR = '" + crTienda
					+ "' order by CREATED_DATETIME desc";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			System.out.println(val);
			tktsalesprm = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			System.out.println("Error al consultar el Articulo TKTPRM en BD Nube");
			e.printStackTrace();
			tktsalesprm = "";
		}
		String tktlocal = Consultatktprml(ipPv);
		if (tktsalesprm.equals(tktlocal)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Método - Consulta monto de la venta realizada en base de datos local
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @return El monto de pago de la venta consultado en la base de datos local
	 * @date 21/01/2025
	 */
	public String consultaMontoVentaLocal(String ipPv) {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + ipPv + ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV, GlobalVariables.passPV);
			String query = "select top 1 FINAL_PRICE from XposStore.dbo.TXN_RETAIL_LINE order by CREATED_DATETIME desc ";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			montoVentaE = val.substring(0);
			System.out.println("El valor de la consulta es: " + montoVentaE);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			System.out.println("Error al consultar el Articulo monto venta en BD Local");
			e.printStackTrace();
			montoVentaE = "";
		}

		return montoVentaE;
	}

	/**
	 * Método - Consulta monto de la venta realizada en base de datos Nube
	 * 
	 * @author Cesar Guel
	 * @param crTienda El nombre de la plaza y tienda a utilizar
	 * @return El monto de pago de la venta consultado en la base de datos nube
	 * @date 21/01/2025
	 */
	public String consultaMontoVentaNube(String crTienda) {
		try {
			Connection conn = DriverManager.getConnection(GlobalVariables.urlOxxoCoudSalesQA,
					GlobalVariables.userQANube, GlobalVariables.passwordQANube);
			System.out.println(crTienda);
			String query = "SELECT TOP 1 p.PAYED_AMOUNT FROM OxxoCloudSalesQA.dbo.PAYMENT_INFO p "
					+ "INNER JOIN OxxoCloudSalesQA.dbo.SALES_RESUME sr ON p.SALE_ID = sr.SALE_ID "
					+ "INNER JOIN OxxoCloudSalesQA.dbo.SALES s ON sr.SALE_ID = s.SALE_ID " + "WHERE s.CR = '" + crTienda
					+ "' ORDER BY s.CREATED_DATETIME DESC ";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			System.out.println(val);
			montoVentanube = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			System.out.println("Error al consultar el Articulo monto venta en BD Nube");
			e.printStackTrace();
			montoVentanube = "";
		}

		return montoVentanube;
	}

	/**
	 * Método - Consulta Store Id
	 * 
	 * @author Cesar Guel
	 * @param nomTienda El nombre de la tienda a utilizar
	 * @return El Store Id de la tienda utilizada
	 * @date 11/06/2025
	 */
	public String consultaStoreIdPrm(String nomTienda) {
		try {
			Connection conn = DriverManager.getConnection(GlobalVariables.urlOxxoCoudQA, GlobalVariables.userQANubeP,
					GlobalVariables.passwordQANubeP);
			System.out.println(nomTienda);
			String query = "select top 1 STORE_ID FROM XposCloudPromotionsQA.dbo.PM_STORE where CR_STORE like '%"
					+ nomTienda + "%' ";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			System.out.println(val);
			StoreIdP = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			e.printStackTrace();
			StoreIdP = "";
		}

		return StoreIdP;
	}

	/**
	 * Método - Consulta Store Id valor dinamico
	 * 
	 * @author Cesar Guel
	 * @param nomTienda El nombre de la tienda a utilizar
	 * @return El Store Id de la tienda utilizada
	 * @date 11/06/2025
	 */
	public String consultaStoreIdPrmD() {
		try {

			Connection conn = DriverManager.getConnection(GlobalVariables.urlOxxoCoudQA, GlobalVariables.userQANubeP,
					GlobalVariables.passwordQANubeP);
			System.out.println(GlobalVariables.nomTiendaD);
			String query = "select top 1 STORE_ID FROM XposCloudPromotionsQA.dbo.PM_STORE where CR_STORE like '%"
					+ GlobalVariables.nomTiendaD + "%'";
			System.out.println("CR TIENDA D: " + GlobalVariables.nomTiendaD);
			System.out.println("QUERY: " + query);
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			System.out.println(val);
			StoreIdP = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			e.printStackTrace();
			StoreIdP = "";
		}

		return StoreIdP;
	}

	/**
	 * Método - Consulta duplicidad de promociones en la nube
	 * 
	 * @author Cesar Guel
	 * @param crTienda El nombre de la plaza y tienda utilizado
	 * @return Retorna 1 o 0 si los datos de la consulta son duplicados
	 * @date 31/07/2025
	 */
	public String consultaDuplicidadPromocionesNube(String crTienda) {
		try {
			String idPrmV = consultaStoreIdPrm(crTienda);
			Connection conn = DriverManager.getConnection(GlobalVariables.urlOxxoCoudQA, GlobalVariables.userQANubeP,
					GlobalVariables.passwordQANubeP);
			String query = "SELECT CASE WHEN EXISTS (" + "SELECT p.ID_PRM, p.CD_SKU "
					+ "FROM XposCloudPromotionsQA.dbo.PM_CO_PRM_STORE t "
					+ "INNER JOIN XposCloudPromotionsQA.dbo.PM_CO_PRM_TGR_ITM_OX p ON p.ID_PRM = t.ID_PRM "
					+ "INNER JOIN XposCloudPromotionsQA.dbo.PM_CO_PRM tr ON t.ID_PRM = tr.ID_PRM "
					+ "WHERE t.STORE_ID = '" + idPrmV + "' AND tr.NM_PRM_CT LIKE '%SIMPLE%' "
					+ "GROUP BY p.ID_PRM, p.CD_SKU " + "HAVING COUNT(*) > 1" + ") THEN CAST(0 AS BIT) " // FALSE: Hay
																										// duplicados
					+ "ELSE CAST(1 AS BIT) " // TRUE: No hay duplicados
					+ "END AS NoHayDuplicados";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			System.out.println(val);
			valPrmD = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);

		} catch (Exception e) {
			e.printStackTrace();
			valPrmD = "";
		}

		return valPrmD;
	}

	/*
	 * +++++++++++++++++++++++++++++
	 * 
	 * CAMBIOS MMORALES
	 * 
	 * ++++++++++++++++++++++++++++/
	 * 
	 * 
	 * /** Método - Seleccionar Categoria del Servicio por Scroll
	 * 
	 * @author Marcos Morales
	 * 
	 * @param texto Categoria a seleccionar
	 * 
	 * @date 25/06/2025
	 */
	public void seleccionarCategoriaServicioPorScroll(String texto) {
		By locator = By.xpath(
				"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/txtTitle\" and @text=\""
						+ texto + "\"]");
		try {
			// Identificar si ya se muestra en pantalla
			if (isElementPresent(locator)) {
				au.click(locator);
			} else {
				// Caso contrario, aplicar scroll hasta encontrarlo
				scrollIntoViewByText(texto);
				au.click(locator);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Seleccionar Servicio por Scroll
	 * 
	 * @author Marcos Morales
	 * @param texto Servicio a seleccionar
	 * @date 25/06/2025
	 */
	public void seleccionarServicioPorScroll(String texto) {
		By locator = By.xpath(
				"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/txtTitle\" and @text=\""
						+ texto + "\"]");
		try {
			// Identificar si ya se muestra en pantalla
			if (isElementPresent(locator)) {
				au.click(locator);
				testCase.addScreenShotCurrentStep(au, "testImage");
			} else {
				// Caso contrario, aplicar scroll hasta encontrarlo
				scrollIntoViewByText(texto);
				au.click(locator);
				testCase.addScreenShotCurrentStep(au, "testImage");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Hace un scroll vertical hasta encontrar el texto especificado
	 * 
	 * @author Marcos Morales
	 * @param texto Cadena de texto a identificar en el scroll
	 * @date 25/06/2025
	 */
	public void scrollIntoViewByText(String texto) {
		try {
			au.driver.findElement(AppiumBy.androidUIAutomator(
					"new UiScrollable(new UiSelector().resourceId(\"com.femco.oxxo.operaciones.posmovil.qa:id/rvService\"))"
							+ ".scrollIntoView(new UiSelector().text(\"" + texto + "\"))"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Espera a que un elemento este visible en pantalla dentro de un periodo de
	 * tiempo
	 * 
	 * @author Marcos Morales
	 * @param locator Identificador del elemento
	 * @param timeout Tiempo maximo de espera
	 * @date 25/06/2025
	 */
	public void waitForVisibility(By locator, Duration timeout) {
		WebDriverWait wait = new WebDriverWait(au.driver, timeout);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception ex) {
			throw new RuntimeException("El elemento no se volvió visible dentro del tiempo esperado: " + locator, ex);
		}
	}

	/**
	 * Espera a que un elemento se oculte dentro de un periodo de tiempo
	 * 
	 * @author Marcos Morales
	 * @param locator Identificador del elemento
	 * @param timeout Tiempo maximo de espera
	 * @date 25/06/2025
	 */
	public void waitForHidden(By locator, Duration timeout) {
		WebDriverWait wait = new WebDriverWait(au.driver, timeout);
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
		} catch (Exception ex) {
			throw new RuntimeException("El elemento todavía es visible después del tiempo de espera: " + locator, ex);
		}
	}

	/**
	 * Método - Verificar si un elemento está presente en la página
	 * 
	 * @author Cesar Guel
	 * @date 06/09/2024
	 * @param locator Identificador a validar
	 */
	public boolean isElementPresent(By locator) {
		try {
			au.driver.findElement(locator);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * Método - Ingresar al apartado de FastFood
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 25/06/2025
	 */
	public void clickOpcionFastFood() {
		try {
			waitForVisibility(btnFastFood, Duration.ofSeconds(30));
			au.click(btnFastFood);
			esperaSeOculteLoading();
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método - Ingresar al apartado de TAE
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 25/06/2025
	 */
	public void clickOpcionTAE() {
		try {
			waitForVisibility(btn_TAE, Duration.ofSeconds(30));
			au.click(btn_TAE);
			esperaSeOculteLoading();
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método - Seleccionar la recarga mas vendida 1, 2 o 3
	 * 
	 * @author Marcos Morales
	 * @param idx Indice del elemento (1, 2 o 3)
	 * @date 25/06/2025
	 */
	public void clickOpcionTAEMasVendido(String idx) {
		try {
			By taeMasVendido = By.id("com.femco.oxxo.operaciones.posmovil.qa:id/txtTop" + idx);
			waitForVisibility(taeMasVendido, Duration.ofSeconds(30));
			testCase.addTextEvidenceCurrentStep("Tap en el boton Tae mas vendido");
			au.click(taeMasVendido);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método - Seleccionar boton calcular
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 25/06/2025
	 */
	public void clickBotonCalcular() {
		try {
			waitForVisibility(btn_calcular, Duration.ofSeconds(30));
			testCase.addTextEvidenceCurrentStep("Tap en el boton Calcular");
			au.click(btn_calcular);
			testCase.addScreenShotCurrentStep(au, "testImage");
			esperaSeOculteLoading();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método - Seleccionar boton total a pagar
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 25/06/2025
	 */
	public void clickBotonTotalPagar() {
		try {
			waitForVisibility(importeTotal, Duration.ofSeconds(30));
			waitForVisibility(btn_total, Duration.ofSeconds(30));
			testCase.addTextEvidenceCurrentStep("Tap en el boton Total");
			au.click(btn_total);
			testCase.addScreenShotCurrentStep(au, "testImage");
			esperaSeOculteLoading();
			waitForVisibility(lblMetodoPago, Duration.ofSeconds(30));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método - Confirmar metodo de pago efectivo
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 25/06/2025
	 */
	public void clickMetodoDePagoEfectivo() {
		try {
			waitForVisibility(btn_efectivo, Duration.ofSeconds(30));
			testCase.addTextEvidenceCurrentStep("Tap en el boton Efectivo");
			au.click(btn_efectivo);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método - Obtiene el importe de venta de cada transaccion
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 25/06/2025
	 */
	public String obtieneImporteDeVenta() {
		try {
			waitForVisibility(btn_aplicar, Duration.ofSeconds(30));
			String venta = au.driver.findElement(totalVta).getText();
			return venta;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	/**
	 * Método - Confirmar metodo de pago efectivo
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 25/06/2025
	 */
	public void confirmarPagoEnEfectivo() {
		try {
			waitForVisibility(btn_aplicar, Duration.ofSeconds(30));
			testCase.addTextEvidenceCurrentStep("Tap en el boton Aplicar");
			au.click(btn_aplicar);
			testCase.addScreenShotCurrentStep(au, "testImage");
			esperaSeOculteLoading();
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método - Espera ventana Gracias por su compra
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 25/06/2025
	 */
	public void esperaVentanaTicketDigital() {
		try {
			waitForVisibility(graciasCompra, Duration.ofSeconds(30));
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método - Espera se oculte la ventana Gracias por su compra
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 25/06/2025
	 */
	public void esperaSeOculteVentanaTicketDigital() {
		try {
			waitForHidden(graciasCompra, Duration.ofSeconds(30));
			waitForHidden(btnFinishTicket, Duration.ofSeconds(30));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método - Ingresar numero de telefono
	 * 
	 * @author Marcos Morales
	 * @param phone El numero del cliente
	 * @date 25/06/2025
	 */
	public void ingresarNumeroTelefono(String phone) {
		try {
			waitForVisibility(phone1, Duration.ofSeconds(30));
			testCase.addTextEvidenceCurrentStep("Se ingresa el numero de telefono");
			au.sendKeys(phone1, phone);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método - Confirmar numero de telefono
	 * 
	 * @author Marcos Morales
	 * @param phone El numero del cliente
	 * @date 25/06/2025
	 */
	public void confirmarNumeroTelefono(String phone) {
		try {
			waitForVisibility(phone2, Duration.ofSeconds(30));
			testCase.addTextEvidenceCurrentStep("Se ingresa el numero de telefono");
			au.sendKeys(phone2, phone);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método - Aceptar recarga
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 25/06/2025
	 */
	public void clickBotonAceptarRecarga() {
		try {
			waitForVisibility(btnAceptarRecarga, Duration.ofSeconds(30));
			testCase.addTextEvidenceCurrentStep("Tap en el boton Aceptar Recarga");
			au.click(btnAceptarRecarga);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Aceptar recarga
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 25/06/2025
	 */
	public void clickBtnAceptarRecarga() {
		try {
			waitForVisibility(btnAceptarRecarga, Duration.ofSeconds(30));
			testCase.addTextEvidenceCurrentStep("Tap en el boton Aceptar Recarga");
			au.click(btnAceptarRecarga);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Espera se oculte el loading en las transiciones de pantalla
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 25/06/2025
	 */
	public void esperaSeOculteLoading() {
		try {
			waitForHidden(loading, Duration.ofSeconds(30));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método - Obtiene el titulo de la ventana al finalizar una venta tae
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @return Retorna el titulo de la ventana de Recarga
	 * @date 25/06/2025
	 */
	public String obtieneTituloVentanaRecargaTAE() {
		try {
			waitForVisibility(titleRecargaTAE, Duration.ofSeconds(30));
			testCase.addScreenShotCurrentStep(au, "testImage");
			return au.driver.findElement(titleRecargaTAE).getText();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	/**
	 * Método - Obtiene el mensaje de texto al finalizar una venta tae
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @return Retorna el mensaje de texto al finalizar la venta de TAE
	 * @date 25/06/2025
	 */
	public String obtieneMensajeVentaTAE() {
		try {
			waitForVisibility(msgVtaTae, Duration.ofSeconds(30));
			testCase.addScreenShotCurrentStep(au, "testImage");
			return au.driver.findElement(msgVtaTae).getText();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	/**
	 * Método - Click en boton finalizar
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 25/06/2025
	 */
	public void clickBotonFinalizar() {
		try {
			waitForVisibility(btnFinalizar, Duration.ofSeconds(30));
			testCase.addTextEvidenceCurrentStep("Tap en el boton Finalizar");
			au.click(btnFinalizar);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Devuelve el listado de proveedores tae
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @return Retorna El listado de proveedores TAE
	 * @date 25/06/2025
	 */
	public List<String> obtieneListaProveedoresTAE() {
		List<String> proveedores = new ArrayList<>();
		List<WebElement> provTAE = au.driver.findElements(listaProvTAE);

		if (provTAE != null) {
			for (WebElement locator : provTAE) {
				try {
					WebElement textValue = locator.findElement(By.className("android.widget.TextView"));
					proveedores.add(textValue.getText());
				} catch (Exception e) {
					System.err.println("Error al obtener proveedor: " + e.getMessage());
				}
			}
		}
		testCase.addScreenShotCurrentStep(au, "testImage");
		return proveedores;
	}

	/**
	 * Devuelve el estado de las opciones Recargas mas vendidas
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @return Retorna el estado de las opciones Recargas mas vendidas
	 * @date 25/06/2025
	 */
	public boolean[] retornaEstadoRecargasMasVendidas() {
		boolean[] status = new boolean[3];
		try {
			waitForVisibility(titleTAEService, Duration.ofSeconds(30));
			status[0] = au.driver.findElement(taeMasVendido1).isDisplayed();
			status[1] = au.driver.findElement(taeMasVendido2).isDisplayed();
			status[2] = au.driver.findElement(taeMasVendido3).isDisplayed();
		} catch (Exception e) {
			e.printStackTrace();
		}
		testCase.addScreenShotCurrentStep(au, "testImage");
		return status;
	}

	/**
	 * Devuelve la cantidad de productos en el Journal
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @return Retorna la cantidad de productas en el journal
	 * @date 25/06/2025
	 */
	public int retornaCantidadProductosJournal() {
		try {
			List<WebElement> prodJournal = au.driver.findElements(cantidadProdJournal);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return prodJournal.size();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

	/**
	 * Obtiene listado de skus de forma aleatoria
	 * 
	 * @author Marcos Morales
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @return Retorna un lista de Sku aleatorios
	 * @date 25/06/2025
	 */
	public ResultSet obtieneSKUSBD(String ipPv) {

		ResultSet resultSet = null;
		try {
			Connection connection = DriverManager.getConnection(
					"jdbc:sqlserver://" + ipPv + ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV, GlobalVariables.passPV);
			;

			Statement statement = connection.createStatement();

			String query = ";with skusactivos as (\r\n" + "	select	 distinct Item_Id as Id\r\n"
					+ "			,[Name] as ProductName\r\n" + "			,[Description] as ProductDescription\r\n"
					+ "			,Sku\r\n" + "			,SalePrice = CAST(coalesce(Amount, 0) as numeric(16, 4))\r\n"
					+ "	from XposStore.dbo.ProductCacheDataVw \r\n"
					+ "	where Item_Status_Name = 'ACTIVE_ITEM_STATUS' \r\n"
					+ "	and Item_Type_Value = 'PRODUCT_ITEM' \r\n"
					+ "	and Product_Type_Code_Name = 'MREGUL_PRODUCT'\r\n" + "	and Amount >= 2\r\n"
					+ "	and CatalogId not in (1526, 5280, 5544, 6080)\r\n" + "), skusaleatorios as (\r\n"
					+ "	select	 Id\r\n" + "			,ProductName\r\n" + "			,ProductDescription\r\n"
					+ "			,Sku\r\n" + "			,SalePrice\r\n"
					+ "			,ROW_NUMBER() OVER (ORDER BY NEWID()) AS RandomRow\r\n"
					+ "			,COUNT(*) OVER () AS TotalRows\r\n" + "	from skusactivos\r\n" + ") select	 Sku\r\n"
					+ "			,SalePrice\r\n" + "from skusaleatorios\r\n" + "where RandomRow <= TotalRows * 0.002\r\n"
					+ "and ProductName not like '%[?]%'\r\n" + "and ProductName not like '%[%]%'\r\n"
					+ "and ProductName not like '%[+]%'\r\n" + "and ProductName not like '%[áéíóúÁÉÍÓÚñÑ]%'\r\n"
					+ "and ProductDescription not like '%[?]%'\r\n" + "and ProductDescription not like '%[%]%'\r\n"
					+ "and ProductDescription not like '%[+]%'\r\n"
					+ "and ProductDescription not like '%[áéíóúÁÉÍÓÚñÑ]%'";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error en la ejecución del método: obtieneSKUSBD\n\n" + e.getMessage());
		}
		return resultSet;
	}

	/**
	 * Obtiene los permimos en la NUBE para un CR
	 * 
	 * @author Marcos Morales
	 * @param crTienda CR de Tienda
	 * @return Retorna los permisos en la nube para un CR
	 * @date 25/06/2025
	 */
	public ResultSet obtienePermisosBDNube(String crTienda) {

		ResultSet resultSet = null;
		try {
			Connection connection = DriverManager.getConnection(GlobalVariables.urlOxxoCoudQA,
					GlobalVariables.userQANubeP, GlobalVariables.passwordQANubeP);

			Statement statement = connection.createStatement();

			String query = "select	 c.CATEGORY\r\n" + "		,m.MENU\r\n" + "		,mcr.CR\r\n"
					+ "		,mcr.BIT_ACTIVO\r\n" + "from XposCloudPOSMovilQA.dbo.CATEGORY c\r\n"
					+ "inner join XposCloudPOSMovilQA.dbo.MENU m on c.CATEGORY_ID = m.CATEGORY_ID\r\n"
					+ "inner join XposCloudPOSMovilQA.dbo.MENU_CR mcr on m.MENU_ID = mcr.MENU_ID\r\n"
					+ "where mcr.CR = '" + crTienda + "'";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}
	
	
	/**
	 * Obtiene los permimos en la NUBE para un CR para un menu especifico 
	 * 
	 * @author Juan Ibarra
	 * @param crTienda CR de Tienda
	 * @param i relacionado al Menú ID del menú 
	 * @return Retorna los permisos en la nube para un CR
	 * @date 25/06/2026
	 */
	public ResultSet obtienePermisosBDNubeEspecifico(String crTienda, int i) {
		ResultSet resultSet = null;
		try {
			Connection connection = DriverManager.getConnection(GlobalVariables.urlOxxoCoudQA,
					GlobalVariables.userQANubeP, GlobalVariables.passwordQANubeP);

			Statement statement = connection.createStatement();

			String query = "select	 c.CATEGORY\r\n" + "		,m.MENU\r\n" + "		,mcr.CR\r\n"
					+ "		,mcr.BIT_ACTIVO\r\n" + "from XposCloudPOSMovilQA.dbo.CATEGORY c\r\n"
					+ "inner join XposCloudPOSMovilQA.dbo.MENU m on c.CATEGORY_ID = m.CATEGORY_ID\r\n"
					+ "inner join XposCloudPOSMovilQA.dbo.MENU_CR mcr on m.MENU_ID = mcr.MENU_ID\r\n"
					+ "where mcr.CR = '" + crTienda + "' and mcr.MENU_ID ="+ i +"";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;	
	}

		
		/**
		 * Obtiene los permimos en la NUBE para un CR con valor dinamico
		 * 
		 * @author Cesar Guel
		 * @return Retorna los permisos en la nube para un CR
		 * @date 21/04/2026
		 */
		public ResultSet obtienePermisosBDNubeD() {

			ResultSet resultSet = null;
			try {
				Connection connection = DriverManager.getConnection(GlobalVariables.urlOxxoCoudQA,
						GlobalVariables.userQANubeP, GlobalVariables.passwordQANubeP);

				Statement statement = connection.createStatement();

				String query = "select	 c.CATEGORY\r\n" + "		,m.MENU\r\n" + "		,mcr.CR\r\n"
						+ "		,mcr.BIT_ACTIVO\r\n" + "from XposCloudPOSMovilQA.dbo.CATEGORY c\r\n"
						+ "inner join XposCloudPOSMovilQA.dbo.MENU m on c.CATEGORY_ID = m.CATEGORY_ID\r\n"
						+ "inner join XposCloudPOSMovilQA.dbo.MENU_CR mcr on m.MENU_ID = mcr.MENU_ID\r\n"
						+ "where mcr.CR = '" + GlobalVariables.crTiendaD + "'";

				resultSet = statement.executeQuery(query);

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return resultSet;
		}

	
	/**
	 * Eliminar los permimos en la NUBE para un CR para un menu especifico  
	 * 
	 * @author Juan Ibarra
	 * @param crTienda CR de Tienda
	 * @param i relacionado al Menú ID del menú 
	 * @return 
	 * @return Retorna los permisos en la nube para un CR
	 * @date 25/06/2026
	 */
	public void eliminarPermisosBDNubeEspecifico(String crTienda, int i) {		
		try {
			Connection connection = DriverManager.getConnection(GlobalVariables.urlOxxoCoudQA,
					GlobalVariables.userQANubeP, GlobalVariables.passwordQANubeP);
			Statement statement = connection.createStatement();
			String query = "delete XposCloudPOSMovilQA.dbo.MENU_CR \r\n"
					+ "where CR = '" + crTienda + "' and MENU_ID ="+ i +"";
			statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Eliminar los permimos en la NUBE para un CR para un menu especifico  
	 * 
	 * @author Juan Ibarra
	 * @param crTienda CR de Tienda
	 * @param i relacionado al Menú ID del menú 
	 * @return 
	 * @return Retorna los permisos en la nube para un CR
	 * @date 25/06/2026
	 */
	public void eliminarPermisosBDNube(String crTienda) {		
		try {
			Connection connection = DriverManager.getConnection(GlobalVariables.urlOxxoCoudQA,
					GlobalVariables.userQANubeP, GlobalVariables.passwordQANubeP);
			Statement statement = connection.createStatement();
			String query = "delete XposCloudPOSMovilQA.dbo.MENU_CR \r\n"
					+ "where CR = '" + crTienda + "'";
			statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	


	/**
	 * Obtiene las ventas totales en la NUBE a partir de una fecha
	 * 
	 * @author Marcos Morales
	 * @param crTienda CR de Tienda
	 * @param fecha    Fecha a partir de la que se calculan las ventas
	 * @return Retorna el monto de las ventas totale spor fecha
	 * @date 25/06/2025
	 */
	public double obtieneVentasTotales(String crTienda, String fecha) {

		double monto = 0;
		ResultSet resultSet = null;
		try {
			Connection connection = DriverManager.getConnection(GlobalVariables.urlOxxoCoudQA,
					GlobalVariables.userQANubeP, GlobalVariables.passwordQANubeP);

			Statement statement = connection.createStatement();

			String query = "SELECT sum(AMOUNT) [TotalVentas] FROM OxxoCloudSalesQA.dbo.PAYMENT_INFO p\r\n"
					+ "INNER JOIN OxxoCloudSalesQA.dbo.SALES_RESUME sr ON p.SALE_ID = sr.SALE_ID\r\n"
					+ "INNER JOIN OxxoCloudSalesQA.dbo.SALES s ON sr.SALE_ID = s.SALE_ID\r\n" + "WHERE s.CR = '"
					+ crTienda + "'\r\n" + "and SALE_DATETIME > '" + fecha + "'";

			resultSet = statement.executeQuery(query);

			if (resultSet.next()) {
				monto = resultSet.getDouble(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return monto;
	}

	/**
	 * Obtiene la ultima fecha de retiro en la NUBE
	 * 
	 * @author Marcos Morales
	 * @param crTienda CR de Tienda
	 * @return Retorna el valor de la ultima fecha de retiro en la nube
	 * @date 25/06/2025
	 */
	public String obtieneUltimaFechaDeRetiro(String crTienda) {

		String fechaRetiro = "";
		ResultSet resultSet = null;
		try {
			Connection connection = DriverManager.getConnection(GlobalVariables.urlOxxoCoudQA,
					GlobalVariables.userQANubeP, GlobalVariables.passwordQANubeP);

			Statement statement = connection.createStatement();

			String query = "select	top 1\r\n" + "		RP_FECHA_RETIRO\r\n"
					+ "from XposCloudPOSMovilQA.dbo.RETIRO_PARCIAL\r\n" + "where RP_CR = '" + crTienda + "'\r\n"
					+ "order by RP_FECHA_RETIRO desc";

			resultSet = statement.executeQuery(query);

			if (resultSet.next()) {
				fechaRetiro = resultSet.getString(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fechaRetiro;
	}

	/**
	 * Agregar multiples productos al journal
	 * 
	 * @author Marcos Morales
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @date 25/06/2025
	 */
	public void agregarMultiplesProductosAlJournal(String ipPv) {
		ResultSet resultSet = obtieneSKUSBD(ipPv);
		try {
			while (resultSet.next()) {
				String sku = resultSet.getString(1);
				String price = resultSet.getString(2);
				System.out.println("SKU: " + sku + " | Precio: " + price);
				ingresarSKUEnBuscador(sku);
				clickEnterEnCampoBuscador();
				agregarProductoAlJournal();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep(
					"Error en la ejecución del método: agregarMultiplesProductosAlJournal\n\n" + e.getMessage());
		}
	}

	/**
	 * Método - Capturar un SKU en el buscador
	 * 
	 * @author Marcos Morales
	 * @param sku SKU del articulo
	 * @date 25/06/2025
	 */
	public void ingresarSKUEnBuscador(String sku) {
		try {
			waitForVisibility(Buscador, Duration.ofSeconds(30));
			testCase.addTextEvidenceCurrentStep("Se ingresa Sku en el buscador");
			au.sendKeys(Buscador, sku);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método - Realizar busqueda de SKU
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 25/06/2025
	 */
	public void clickEnterEnCampoBuscador() {
		try {
			au.click(Buscador);
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			esperaSeOculteLoading();
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("No se realizo la busqueda del SKU");
		}
	}

	/**
	 * Método - Agregar producto al Journal
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 25/06/2025
	 */
	public void agregarProductoAlJournal() {
		try {
			waitForVisibility(btn_cantidad, Duration.ofSeconds(30));
			testCase.addTextEvidenceCurrentStep("Tap en el boton Cantidad");
			au.click(btn_cantidad);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método - Agregar producto al Journal a traves del indice cuando se tienen
	 * varios articulos
	 * 
	 * @author Marcos Morales
	 * @param idx Indice del articulo en pantalla (1, 2, 3, ...)
	 * @date 25/06/2025
	 */
	public void agregarProductoAlJournal(int idx) {
		try {
			By locator = By.xpath(
					"(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/btnAdddItem\"])["
							+ idx + "]");
			waitForVisibility(locator, Duration.ofSeconds(15));
			au.click(locator);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Espera a que desaparezca el mensaje toast
	 * 
	 * @author Marcos Morales
	 * @param mensaje Mensaje toast que se espera desaparezca de la pantalla
	 * @date 25/06/2025
	 */
	public void esperaSeOculteMensajeToast(String mensaje) {
		try {
			waitForHidden(By.xpath("//android.widget.Toast[@text=\"" + mensaje + "\"]"), Duration.ofSeconds(30));
			TimeUnit.SECONDS.sleep(8);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Realiza multiples ventas hasta mostrar el mensaje de retiro
	 * 
	 * @author Marcos Morales
	 * @param crTienda CR de Tienda
	 * @param ipPv     La ip de la maquina que se esta utilizando
	 * @date 26/06/2025
	 */
	public void realizarMultiplesVentasMostrarRetiros(String crTienda, String ipPv) throws InterruptedException {

		String fecha = obtieneUltimaFechaDeRetiro(crTienda);

		int contador = 1;
		double vtas = 0;

		// Identificando si el elemento buscado se encuentra presente
		boolean elementPresent = isElementPresent(msjRetiro);

		// Mientras no encuentre el elemento realizara las ventas
		while (!elementPresent) {
			elementPresent = isElementPresent(msjRetiro);
			if (vtas <= 3500) { // Revisar de donde tomar este importe para tenerlo parametrizado
				agregarMultiplesProductosAlJournal(ipPv);
				clickBotonCalcular();
				clickBotonTotalPagar();
				clickMetodoDePagoEfectivo();
				confirmarPagoEnEfectivo();
				esperaSeOculteVentanaTicketDigital();
				esperaSeOculteMensajeToast("Venta completada con éxito");
				Thread.sleep(20000); // Espera fija necesaria para identificar el elemento de Retiro al realizar las
				// ventas
				vtas = obtieneVentasTotales(crTienda, fecha);
				System.out.println("Venta TOTALES Nube: " + contador + " " + vtas);
			} else {
				break;
			}
			elementPresent = isElementPresent(titleRetiro);
			contador++;
		}
	}

	/**
	 * Método - Obtiene el mensaje de texto para relizar retiros
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @return Retorna el mensaje de texto de retiro
	 * @date 26/06/2025
	 */
	public String obtieneMensajeAvisoRetiro() {
		try {
			waitForVisibility(titleRetiro, Duration.ofSeconds(30));
			testCase.addScreenShotCurrentStep(au, "testImage");
			return au.driver.findElement(titleRetiro).getText();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	/**
	 * Método - Obtiene el estado del mensaje de texto para relizar retiros
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @return Retorna el estado del mensaje de texto de retiro
	 * @date 26/06/2025
	 */
	public boolean obtieneEstadoMensajeAvisoRetiro() {
		try {
			testCase.addScreenShotCurrentStep(au, "testImage");
			return isElementPresent(titleRetiro);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Método - Obtiene el estatus del elemento Gracias por su compra
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @return Retorna el estatus del elemento final de la venta
	 * @date 26/06/2025
	 */
	public boolean validarMensajeVentaExitosa() {
		try {
			waitForVisibility(graciasCompra, Duration.ofSeconds(30));
			testCase.addScreenShotCurrentStep(au, "testImage");
			return isElementPresent(graciasCompra);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Método - Seleccionar un proveedor TAE
	 * 
	 * @author Marcos Morales
	 * @param texto Proveedor a seleccionar
	 * @date 26/06/2025
	 */
	public void seleccionarProveedorTAE(String texto) {
		try {
			By locator = By.xpath(
					"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/txtTitle\" and @text=\""
							+ texto + "\"]");
			isElementPresent(locator);
			au.click(locator);
			esperaSeOculteLoading();
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método - Validar mensaje toast
	 * 
	 * @author Marcos Morales
	 * @param mensaje              Cadena de texto con el mensaje toast a validar
	 * @param maxWaitTimeInSeconds Time out de espera para identificar el
	 *                             mensaje(segundos)
	 * @return true o false Si se encuentra el elemento
	 * @date 26/06/2025
	 */
	public boolean validarMensajeToast(String mensaje, int maxWaitTimeInSeconds) {
		By locator = By.xpath("//android.widget.Toast[@text=\"" + mensaje + "\"]");
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(maxWaitTimeInSeconds));
			wait.pollingEvery(Duration.ofMillis(200)) // Revisa cada 200ms
					.ignoring(NoSuchElementException.class).until(driver -> {
						WebElement toast = au.driver.findElement(locator);
						testCase.addScreenShotCurrentStep(au, "testImage");
						return true;
					});
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	/**
	 * Método - Click al boton Cancelar Retiro
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 26/06/2025
	 */
	public void clickBotonCancelar() {
		try {
			waitForVisibility(btnCancelar, Duration.ofSeconds(30));
			testCase.addTextEvidenceCurrentStep("Tap en el boton Cancelar");
			au.click(btnCancelar);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método - Click al boton Confirmar Retiro
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 26/06/2025
	 */
	public void clickBotonConfirmar() {
		try {
			waitForVisibility(btnConfirmar, Duration.ofSeconds(30));
			testCase.addTextEvidenceCurrentStep("Tap en el boton Confirmar");
			au.click(btnConfirmar);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método - Click al boton Confirmar Retiro despues de ingresar el importe
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 26/06/2025
	 */
	public void clickBotonConfirmar2() {
		try {
			waitForVisibility(btnConfirmar2, Duration.ofSeconds(30));
			testCase.addTextEvidenceCurrentStep("Tap en el boton Confirmar");
			au.click(btnConfirmar2);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método - Capturar contraseña para retiro
	 * 
	 * @author Marcos Morales
	 * @param pass Contraseña del usuario
	 * @date 26/06/2025
	 */
	public void ingresarContrasenaRetiro(String pass) {
		try {
			waitForVisibility(password, Duration.ofSeconds(30));
			testCase.addTextEvidenceCurrentStep("Se ingresa contraseña");
			au.sendKeys(password, pass);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método - Obtiene el monto total de retiro
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @return Retorna el monto total del retiro
	 * @date 26/06/2025
	 */
	public String obtieneMontoRetiro() {
		try {
			waitForVisibility(montoRetiro, Duration.ofSeconds(30));
			String texto = au.driver.findElement(montoRetiro).getText();
			String textoLimpio = texto.replace("Monto: $", "");
			double monto = Double.parseDouble(textoLimpio);
			return String.format("%.2f", monto);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	/**
	 * Método - Ingresar el monto de retiro
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 26/06/2025
	 */
	public void ingresarMontoDeRetiro() {
		try {
			waitForVisibility(inputMontoRetiro, Duration.ofSeconds(30));
			testCase.addTextEvidenceCurrentStep("Se ingresa monto de retiro");
			au.sendKeys(inputMontoRetiro, obtieneMontoRetiro());
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método - Obtiene el estatus del elemento Contraseña
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @return Retorna el estatus del elemento Contraseña
	 * @date 26/06/2025
	 */
	public boolean validarCampoContrasena() {
		try {
			waitForVisibility(password, Duration.ofSeconds(30));
			testCase.addScreenShotCurrentStep(au, "testImage");
			return isElementPresent(password);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Método - Obtiene el estatus del titulo en el modal de Retiro
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @return Retorna el estatus del titulo en el modal de retiro
	 * @date 26/06/2025
	 */
	public boolean validarTituloModalRetiro() {
		try {
			waitForVisibility(msjRetiro, Duration.ofSeconds(30));
			testCase.addScreenShotCurrentStep(au, "testImage");
			return isElementPresent(msjRetiro);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Método - Obtiene el mensaje de Retiro
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @return Retorna el mensaje del retiro
	 * @date 26/06/2025
	 */
	public String obtieneMensajeDeRetiro() {
		try {
			waitForVisibility(msjRetiro, Duration.ofSeconds(30));
			return au.driver.findElement(msjRetiro).getText();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	/**
	 * Método - Obtiene el estatus del mensaje en el modal de Retiro
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @return Retorna el estatus del mensaje en el modal de Retiro
	 * @date 26/06/2025
	 */
	public boolean validarMensajeDeRetiro() {
		try {
			waitForVisibility(mensajeRetiro, Duration.ofSeconds(30));
			testCase.addScreenShotCurrentStep(au, "testImage");
			return isElementPresent(mensajeRetiro);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Método - Espera ventana del Journal
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 26/06/2025
	 */
	public void esperaVentanaJournalInicial() {
		try {
			waitForVisibility(Journal, Duration.ofSeconds(30));
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método - Click al boton X para limpiar una busqueda de SKU
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 26/06/2025
	 */
	public void clickBotonLimpiarBusqueda() {
		try {
			waitForVisibility(limpiarBusqueda, Duration.ofSeconds(30));
			testCase.addTextEvidenceCurrentStep("Tap en el boton Limpiar Busqueda");
			au.click(limpiarBusqueda);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método - Obtiene la cantidad de Recargas mostradas en pantalla
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @return Retorna la cantidad de Recargas mostradas en pantalla
	 * @date 26/06/2025
	 */
	public int muestraListadoDeRecargas() {
		try {
			List<WebElement> recargas = au.driver.findElements(listaRecargas);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return recargas.size();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

	/**
	 * Método - Obtiene el token a traves de una peticion POST para realizar el
	 * cambio de permisos
	 * 
	 * @author Marcos Morales
	 * @param usuario  Usuario de la aplicacion
	 * @param password Contraseña del usuario
	 * @param crTienda CR de Tienda
	 * @return Retorna el token a traves de una peticion POST para realizar el
	 *         cambio de permisos
	 * @date 26/06/2025
	 */
	public String obtieneToken(String usuario, String password, String crTienda) {
		String token = "";

		// Construir el cuerpo de la petición
		String requestBody = String.format("{\"User\": \"%s\", \"Password\": \"%s\", \"CR\": \"%s\"}", usuario, password, crTienda);

		try {
			// Realizar la petición POST
			Response response = RestAssured.given().baseUri("https://svcs-dev.xposcloud.com.mx").header("CR", crTienda) // Header
					// parametrizado
					.contentType(ContentType.JSON).body(requestBody).when()
					.post("/sales/qa/api/v1/Authentication/LoginUser");

			// Validar la respuesta
			response.then().statusCode(200).body("token", notNullValue());

			// Extraer el token
			token = response.jsonPath().getString("token");
			System.out.println("Token: " + token);
		} catch (Exception e) {
			System.err.println("Error en la petición: " + e.getMessage());
			throw e;
		}
		return token;
	}

	/**
	 * Método - Deshabilita o Habilita Permisos a traves de una peticion POST
	 * 
	 * @author Marcos Morales
	 * @param usuario    Usuario de la aplicacion
	 * @param password   Contraseña del usuario
	 * @param crTienda   CR de Tienda
	 * @param categoryId ID de la Catagoria
	 * @param category   Cadena de texto con el nombre de la categoria
	 * @param menuId     ID del menu
	 * @param menu       Cadena de texto con el nombre del menu
	 * @param bitActivo  1 o 0 en funcion de Habilitar/Deshabilitar
	 * @date 26/06/2025
	 */
	public void deshabilitarHabilitarPermiso(String usuario, String password, String crTienda, int categoryId,
			String category, int menuId, String menu, int bitActivo) {

		String authToken = obtieneToken(usuario, password, crTienda);

		// Validar el token
		if (authToken == null || authToken.isEmpty()) {
			throw new RuntimeException("No se obtuvo el token de autenticación");
		}

		// Construir el cuerpo de la petición
		String requestBody = String.format(
				"{\"permissions\": [{" + "\"categoryId\": %d, " + "\"category\": \"%s\", " + "\"menuId\": %d, "
						+ "\"menu\": \"%s\", " + "\"cr\": \"%s\", " + "\"bit_Activo\": %d" + "}]}",
				categoryId, category, menuId, menu, crTienda, bitActivo);

		try {
			// Realizar la petición POST con el token Bearer
			Response response = RestAssured.given().baseUri("https://svcs-qa.xposcloud.com.mx")
					.header("Authorization", "Bearer " + authToken) // Token Bearer
					.header("CR", crTienda) // Header parametrizado
					.contentType(ContentType.JSON).body(requestBody).when()
					.post("/posmovil/qa/api/v1/Configuration/SavePermissions");

			// Imprimir toda la respuesta en consola
			System.out.println("Response Body:\n" + response.getBody().asPrettyString());

			// Validación de la respuesta
			response.then().statusCode(200);
		} catch (Exception e) {
			System.err.println("Error en la petición: " + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * Método - Deshabilita o Habilita Permisos a traves de una peticion POST con valores dinamicos
	 * 
	 * @author Cesar Guel
	 * @param usuario    Usuario de la aplicacion
	 * @param password   Contraseña del usuario
	 * @param categoryId ID de la Catagoria
	 * @param category   Cadena de texto con el nombre de la categoria
	 * @param menuId     ID del menu
	 * @param menu       Cadena de texto con el nombre del menu
	 * @param bitActivo  1 o 0 en funcion de Habilitar/Deshabilitar
	 * @date 21/04/2026
	 */
	public void deshabilitarHabilitarPermisoD(String usuario, String password, int categoryId,
			String category, int menuId, String menu, int bitActivo) {

		String authToken = obtieneToken(usuario, password, GlobalVariables.crTiendaD);

		// Validar el token
		if (authToken == null || authToken.isEmpty()) {
			throw new RuntimeException("No se obtuvo el token de autenticación");
		}

		// Construir el cuerpo de la petición
		String requestBody = String.format(
				"{\"permissions\": [{" + "\"categoryId\": %d, " + "\"category\": \"%s\", " + "\"menuId\": %d, "
						+ "\"menu\": \"%s\", " + "\"cr\": \"%s\", " + "\"bit_Activo\": %d" + "}]}",
						categoryId, category, menuId, menu, GlobalVariables.crTiendaD, bitActivo);

		try {
			// Realizar la petición POST con el token Bearer
			Response response = RestAssured.given().baseUri("https://svcs-qa.xposcloud.com.mx")
					.header("Authorization", "Bearer " + authToken) // Token Bearer
					.header("CR", GlobalVariables.crTiendaD) // Header parametrizado
					.contentType(ContentType.JSON).body(requestBody).when()
					.body(requestBody)
	                .when()
					.post("/posmovil/qa/api/v1/Configuration/SavePermissions");

			statusCode = response.getStatusCode();

	        System.out.println("Response Body:\n" + response.getBody().asPrettyString());

			// Validación de la respuesta
			response.then().statusCode(200);
		} catch (Exception e) {
			System.err.println("Error en la petición: " + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * 
	 */
	public boolean actualizarPermisoD(String usuario, String password, int categoryId,
			String category, int menuId, String menu, int bitActivo) {

		String authToken = obtieneToken(usuario, password, GlobalVariables.crTiendaD);

		// Validar el token
		if (authToken == null || authToken.isEmpty()) {
			throw new RuntimeException("No se obtuvo el token de autenticación");
		}

		// Construir el cuerpo de la petición
		String requestBody = String.format(
				"{\"permissions\": [{" + "\"categoryId\": %d, " + "\"category\": \"%s\", " + "\"menuId\": %d, "
						+ "\"menu\": \"%s\", " + "\"cr\": \"%s\", " + "\"bit_Activo\": %d" + "}]}",
						categoryId, category, menuId, menu, GlobalVariables.crTiendaD, bitActivo);

		try {
			// Realizar la petición POST con el token Bearer
			Response saveResponse = RestAssured.given().baseUri("https://svcs-qa.xposcloud.com.mx")
					.header("Authorization", "Bearer " + authToken) // Token Bearer
					.header("CR", GlobalVariables.crTiendaD) // Header parametrizado
					.contentType(ContentType.JSON).body(requestBody).when()
					.body(requestBody)
	                .when()
					.post("/posmovil/qa/api/v1/Configuration/SavePermissions");

			statusCode = saveResponse.getStatusCode();
			if (saveResponse.getStatusCode() != 200) {
	            System.out.println("No fue posible actualizar el permiso.");
	            return false;
	        }

	        System.out.println("Response Body:\n" + saveResponse.getBody().asPrettyString());

			// Validación de la respuesta
	        saveResponse.then().statusCode(200);
			return true;
		} catch (Exception e) {
			System.err.println("Error en la petición: " + e.getMessage());
//			throw e;
			return false;
		}
	}
	
	/**
	 * Metodo- valida mensaje 200
	 * 
	 * @author Cesar Guel
	 * @param statusCode
	 * @date 21/04/2026
	 */
	public boolean validarRespuesta200() {
		try {
			
			if(statusCode == 200) {
				System.out.println("La respuesta del estatus es 200");
				testCase.addTextEvidenceCurrentStep("Status Code: " + statusCode);
				return true;
			}else {
				System.out.println("La respuesta del estatus no es 200");
				testCase.addTextEvidenceCurrentStep("Status Code: " + statusCode);
				return false;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Status Code: " + statusCode);
			System.out.println("Hubo algun problema al validar la respuesta del estatus");
			return false;
		}
	    
	}

	/**
	 * Método - Para Deshabilitar o Habilita a traves de una peticion POST para
	 * ModulosMVP1 de FasFood y TAE;
	 * 
	 * @author Juan Ibarra
	 * @param usuario    Usuario de la aplicacion
	 * @param password   Contraseña del usuario
	 * @param crTienda   CR de Tienda
	 * @param categoryId ID de la Catagoria
	 * @param category   Cadena de texto con el nombre de la categoria
	 * @param menuId     ID del menu
	 * @param menu       Cadena de texto con el nombre del menu
	 * @param bitActivo  1 o 0 en funcion de Habilitar/Deshabilitar
	 * @date 23/04/2026
	 */
	public ResultadoServicio enviarPeticionModulos(String usuario, String password, String crTienda, int categoryId,
			String category, int menuId, String menu, int bitActivo) {

		String authToken = obtieneToken(usuario, password, crTienda);
		System.out.println("Valor de authToken: " + authToken);

		if (authToken == null || authToken.isEmpty()) {
			throw new RuntimeException("No se obtuvo el token de autenticación");
		}
		
		//Generación del request a enviar en el POST
		String requestBody = String.format(
				"{\"permissions\": [{" + "\"categoryId\": %d, " + "\"category\": \"%s\", " + "\"menuId\": %d, "
						+ "\"menu\": \"%s\", " + "\"cr\": \"%s\", " + "\"bit_Activo\": %d" + "}]}",
						categoryId, category, menuId, menu, crTienda, bitActivo);		
		System.out.println("Información del REQUEST:\n" + requestBody);
		
		//Generación del Response recibido en el POST
		try {

			Response response = RestAssured.given().baseUri("https://svcs-qa.xposcloud.com.mx")
					.header("Authorization", "Bearer " + authToken).header("CR", crTienda).contentType(ContentType.JSON)
					.body(requestBody).when().post("/posmovil/qa/api/v1/Configuration/SavePermissions");
			String responseBody = response.getBody().asPrettyString();
			System.out.println("Información del RESPONSE:\n" + responseBody);
			
			int statusCode = response.getStatusCode();
			System.out.println("Información del STATUS CODE: " + statusCode);

			//En caso de que sea un estatus diferente a 200 falla el caso			
			if (statusCode != 200) {
				testCase.nextStep("Error en la petición del servicio , se obtuvo el Status Code " + statusCode +" , Favor de revisar." );
				throw new AssertionError("Error en el servicio.\nStatusCode: " + statusCode + "\nRequest:\n"
						+ requestBody + "\nResponse:\n" + responseBody);
			}
			
			//Se regresa el resultado obtenido de la ejecución del POST, regresando el request, response y estatus obtenido
			return new ResultadoServicio(requestBody, responseBody, statusCode);

		} catch (Exception e) {
			testCase.nextStep("Error en la petición POST, se obtiene la siguiente Excepción: " + e.getMessage());
			System.err.println("Error en la petición POST: " + e.getMessage());
			throw e;
		}
	}
	
	/**Clase utilizada para el metodo enviarPeticionPOST en el cual se obtiene de 
	 * manera separada los resultados de Request, Response y Status 
	 * @author Juan Ibarra
	 * @date 23/04/2026
	 */
	public class ResultadoServicio {
		private String request;
		private String response;
		private int statusCode;

		public ResultadoServicio(String request, String response, int statusCode) {
			this.request = request;
			this.response = response;
			this.statusCode = statusCode;
		}

		public String getRequest() {
			return request;
		}

		public String getResponse() {
			return response;
		}

		public int getStatusCode() {
			return statusCode;
		}
	}

	/**
	 * Valida el estado de los elementos para las dos formas de pago
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @return el estado de los elementos para las dos formas de pago
	 * @date 26/06/2025
	 */
	public boolean[] retornaEstadoFormasDePago() {
		boolean[] status = new boolean[2];
		try {
			waitForVisibility(lblMetodoPago, Duration.ofSeconds(10));
			status[0] = au.driver.findElement(btn_tarjeta).isDisplayed();
			status[1] = au.driver.findElement(btn_efectivo).isDisplayed();
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * Método - Obtiene el mensaje Numero telefonico no coincide
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @return Retorna el mensaje Numero telefonico no coincide
	 * @date 26/06/2025
	 */
	public String obtieneMensajeTelefonoNoCoincide() {
		try {
			waitForVisibility(msjErrorTelefonos, Duration.ofSeconds(30));
			return au.driver.findElement(msjErrorTelefonos).getText();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	/**
	 * Devuelve el listado de opciones FastFood
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @return Retorna el listado de opciones FastFood
	 * @date 26/06/2025
	 */
	public List<String> obtieneListaOpcionesFastFood() {
		List<String> fastFood = new ArrayList<>();
		List<WebElement> opciones = au.driver.findElements(listaFastFood);

		if (opciones != null) {
			for (WebElement locator : opciones) {
				try {
					WebElement textValue = locator.findElement(By.className("android.widget.TextView"));
					fastFood.add(textValue.getText());
				} catch (Exception e) {
					System.err.println("Error al obtener los datos: " + e.getMessage());
				}
			}
		}
		testCase.addScreenShotCurrentStep(au, "testImage");
		return fastFood;
	}

	/**
	 * Método - Seleccionar una de las opciones de fastfood
	 * 
	 * @author Marcos Morales
	 * @param texto Cadena de texto con la opcion fastfood a seleccionar
	 * @date 26/06/2025
	 */
	public void seleccionarFastFood(String texto) {
		try {
			By locator = By.xpath(
					"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/textViewTitle\" and @text=\""
							+ texto + "\"]");
			isElementPresent(locator);
			testCase.addTextEvidenceCurrentStep("Tap en el boton FastFood");
			au.click(locator);
			testCase.addScreenShotCurrentStep(au, "testImage");
			esperaSeOculteLoading();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Valida el estado del boton Continuar
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @return Retorna el estado del boton Continuar
	 * @date 26/06/2025
	 */
	public boolean retornaEstadoBotonContinuar() {
		boolean status = false;
		try {
			WebElement edoBtnContinuar = au.driver.findElement(btnContinue);
			status = Boolean.parseBoolean(edoBtnContinuar.getAttribute("enabled"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		testCase.addScreenShotCurrentStep(au, "testImage");
		return status;
	}

	/**
	 * Método - Click al boton Continuar
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 26/06/2025
	 */
	public void clickBotonContinuar() {
		try {
			waitForVisibility(btnContinue, Duration.ofSeconds(30));
			testCase.addTextEvidenceCurrentStep("Tap en el boton Continuar");
			au.click(btnContinue);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método - Obtiene la cantidad de productos en pantalla para una seleccion de
	 * fastfood
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @return Retorna la cantidad de productos en pantalla para una seleccion de
	 *         fastfood
	 * @date 26/06/2025
	 */
	public int listadoDeProductosFastFood() {
		try {
			List<WebElement> prodFastFood = au.driver.findElements(listaProdFastFood);
			testCase.addScreenShotCurrentStep(au, "testImage");
			return prodFastFood.size();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

	/**
	 * Método - Obtiene la descripcion de un articulo en pantalla
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @return Retorna la descripcion de un articulo en pantalla
	 * @date 26/06/2025
	 */
	public String obtieneDescripcionDeArticulo() {
		try {
			waitForVisibility(Articulo, Duration.ofSeconds(30));
			return au.driver.findElement(Articulo).getText();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	/*
	 * +++++++++++++++++++++++++++++
	 * 
	 * CAMBIOS Irving Orozco
	 * 
	 * ++++++++++++++++++++++++++++/
	 */

	/**
	 * Método - Seleccionar Boton TAE
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 23/01/2025
	 */
	public void seleccionarBotonTAE() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton TAE");
			au.click(btn_TAE);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton TAE");
		}
	}

	/**
	 * Método - Seleccionar Boton Telcel
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 12/06/2025
	 *
	 */
	public void seleccionarBotonTelcel() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Telcel");
			au.click(TAE_Telcel);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton Telcel");
		}
	}

	/**
	 * Método - Seleccionar Boton Movistar
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 12/06/2025
	 *
	 */
	public void seleccionarBotonMovistar() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Movistar");
			au.click(TAE_Movistar);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton Movistar");
		}
	}

	/**
	 * Método - Seleccionar Boton Telcel TAE30
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 12/06/2025
	 *
	 */
	public void seleccionarBotonTelcelTAE30() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Telcel TAE 30");
			au.scrollToElement(Telcel_TAE30);
			au.click(Telcel_TAE30);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton Telcel TAE 30");
		}
	}

	/**
	 * Método - Seleccionar Boton Movistar TAE30
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 12/06/2025
	 *
	 */
	public void seleccionarBotonMovistar30() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Movistar TAE 30");
			au.scrollToElement(Movistar_TAE30);
			au.click(Movistar_TAE30);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton Movistar TAE 30");
		}
	}

	/**
	 * Método - Valida Apartado TAE
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @return Retorna true o false si los elementos estan Desplegados
	 * @date 23/01/2025
	 */
	public boolean ingresarTAE() {
		try {
			testCase.addTextEvidenceCurrentStep("Se valida diferentes compañias");
			WebElement ingresarTAETel = au.driver.findElement(TAE_Telcel);
			WebElement ingresarTAEMov = au.driver.findElement(TAE_Movistar);
			if (ingresarTAETel.isDisplayed() && ingresarTAEMov.isDisplayed()) {
				System.out.println("Se encontro las diferentes compañias");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(5);
				return true;
			} else {
				System.out.println("No se encontro las diferentes compañias");
				testCase.addTextEvidenceCurrentStep("No se muestra las compañias de TAE.");
				return false;
			}
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error: No se pudo ingresar a Apartado TAE. " + e.getMessage());
			return false;
		}
	}

	/**
	 * Método - Valida Apartado recargas telcel
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @return Retorna true o false Si el elemento esta Desplegado
	 * @date 12/06/2025
	 */
	public boolean ingresarTelcel() {
		try {
			WebElement TAETel30 = au.driver.findElement(Telcel_TAE30);
			if (TAETel30.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep("Se valida opciones de recarga");
				System.out.println("Se encontro las diferentes compañias");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} else {
				System.out.println("No se encontro las diferentes compañias");
				testCase.addTextEvidenceCurrentStep("No se muestra las opciones de recarga");
				return false;
			}
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error: No se pudo ingresar a Apartado TAE. " + e.getMessage());
			return false;
		}
	}

	/**
	 * Método - Valida Apartado recargas Movistar
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @return Retorna true o false Si el elemento esta Desplegado
	 * @date 12/06/2025
	 */
	public boolean ingresarMovistar() {
		try {
			testCase.addTextEvidenceCurrentStep("Se valida opciones de recarga");

			WebElement ingresarTAETel30 = au.driver.findElement(Movistar_TAE30);
			if (ingresarTAETel30.isDisplayed()) {
				System.out.println("Se encontro las diferentes compañias");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} else {
				System.out.println("No se encontro las diferentes compañias");
				testCase.addTextEvidenceCurrentStep("No se muestra las opciones de recarga");
				return false;
			}
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error: No se pudo ingresar a Apartado TAE. " + e.getMessage());
			return false;
		}
	}

	/**
	 * Método - Seleccionar Boton TAE top
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 23/01/2025
	 *
	 */
	public void seleccionarBotonTAEtop() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton + TAE top");
			au.click(btn_TAEtop);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton TAE top");
		}
	}

	/**
	 * Método - Valida TOP TAE
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @return Retorna true o false Si el elemento esta Desplegado
	 * @date 23/01/2025
	 */
	public boolean TopTAE() {
		try {
			testCase.addTextEvidenceCurrentStep("Se muestra las TOP de TAE ");
			WebElement topventastae = au.driver.findElement(btn_TAEtop);
			if (topventastae.isDisplayed()) {
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(5);
				return true;
			} else {
				testCase.addTextEvidenceCurrentStep("No se muestra las TOP de TAE.");
				return false;
			}
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error: No se pudo ingresar a Apartado TAE. " + e.getMessage());
			return false;
		}
	}

	/**
	 * Método - Valida articulo en journal
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @return Retorn true o false Si el elemento esta Desplegado
	 * @date 23/01/2025
	 */
	public boolean articuloJournal() {
		try {
			testCase.addTextEvidenceCurrentStep("Valida articulo en Journal");
			WebElement articuloenJournal = au.driver.findElement(articuloJournal);
			if (articuloenJournal.isDisplayed()) {
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(5);
				return true;
			} else {
				testCase.addTextEvidenceCurrentStep("No se muestra articulo en Journal.");
				return false;
			}
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error: No se pudo agrega articulo al Journal. " + e.getMessage());
			return false;
		}
	}

	/**
	 * Método - Ingresar a fast food
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 23/01/2025
	 *
	 */
	public void seleccionarBotonFastFood() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Fast Food");
			au.click(btn_fastfood);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton FastFood");
		}
	}

	/**
	 * Método - Validar opciones Fast food
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @return Retorna true o false Si los elementos estan Desplegados
	 * @date 23/01/2025
	 */
	public boolean OpcionFastFood() {
		try {
			TimeUnit.SECONDS.sleep(5);
			WebElement ingresarALIMENTOS = au.driver.findElement(btn_fastfoodALI);
			WebElement ingresarBEBIDAS = au.driver.findElement(btn_fastfoodBEB);
			if (ingresarALIMENTOS.isDisplayed() && ingresarBEBIDAS.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep("Se muestran opciones de fast food");
				System.out.println("Se encontro las opciones de fast food");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(5);
				return true;
			} else {
				System.out.println("No se encontro las opciones de fast food");
				testCase.addTextEvidenceCurrentStep("No se muestran opciones de fast food.");
				return false;
			}
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error: No se pudo ingresar a apartado fast food. " + e.getMessage());
			return false;
		}
	}

	/**
	 * Método - Seleccionar boton Bebidas
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 23/01/2025
	 *
	 */
	public void seleccionarBotonBebidas() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Bebidas");
			au.click(btn_fastfoodBEB);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton FastFood Bebidas");
		}
	}

	/**
	 * Método - Ingresar a fast food Alimentos
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 23/01/2025
	 *
	 */
	public void seleccionarBotonAlimentos() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Alimentos");
			au.click(btn_fastfoodALI);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton FastFood Alimentos");
		}
	}

	/**
	 * Método - Validar opciones Fast food Bebidas
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @return Retorn true o false Si los elementos estan Desplegados
	 * @date 23/01/2025
	 */
	public boolean OpcionFastFoodBebidas() {
		try {
			testCase.addTextEvidenceCurrentStep("Se muestran opciones de fast food");
			WebElement ingresarBEBAme = au.driver.findElement(btn_fastfoodBEBCapuchino);
			WebElement ingresarBEBCap = au.driver.findElement(btn_fastfoodBEBAmericano);
			if (ingresarBEBCap.isDisplayed() && ingresarBEBAme.isDisplayed()) {
				System.out.println("Se encontro las opciones de fast food bebidas");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(5);
				return true;
			} else {
				System.out.println("No se encontro las opciones de fast food bebidas");
				testCase.addTextEvidenceCurrentStep("No se muestran opciones de fast food bebidas.");
				return false;
			}
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep(
					"Error: No se pudo ingresar a apartado fast food bebidas. " + e.getMessage());
			return false;
		}
	}

	/**
	 * Método - Validar opciones Comida Osabor
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @return Retorna true o false Si los elementos estan Desplegados
	 * @date 23/01/2025
	 */
	public boolean OpcionesComidaOsabor() {
		try {
			testCase.addTextEvidenceCurrentStep("Se muestran opciones de Comida Osabor");
			WebElement OsaborTacos = au.driver.findElement(btn_Otacos);
			WebElement OsaborTortas = au.driver.findElement(btn_Otortas);
			WebElement OsaborBurritos = au.driver.findElement(btn_OBurritos);
			WebElement OsaborOtros = au.driver.findElement(btn_OOtros);
			if (OsaborTacos.isDisplayed() && OsaborTortas.isDisplayed() && OsaborBurritos.isDisplayed()
					&& OsaborOtros.isDisplayed()) {
				System.out.println("Se encontro las opciones de Comida Osabor");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(10);
				return true;
			} else {
				System.out.println("No se encontro las opciones de Comida Osabor");
				testCase.addTextEvidenceCurrentStep("No se muestran opciones de Comida Osabor.");
				return false;
			}
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep(
					"Error: No se pudo ingresar a apartado Comida Osabor. " + e.getMessage());
			return false;
		}
	}

	/**
	 * Método - Validar opciones Comida Caliente
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @return Retorna true o false Si el elemento esta Desplegado
	 * @date 22/05/2025
	 */
	public boolean OpcionesComidaCaliente() {
		try {
			testCase.addTextEvidenceCurrentStep("Se muestran opciones de Comida Caliente");
			WebElement comidaOSABOR = au.driver.findElement(btn_ComidaOSABOR);
			if (comidaOSABOR.isDisplayed()) {
				System.out.println("Se encontro las opciones de Comida Caliente");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(5);
				return true;
			} else {
				System.out.println("No se encontro las opciones de Comida Caliente");
				testCase.addTextEvidenceCurrentStep("No se muestran opciones de Comida Caliente.");
				return false;
			}
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep(
					"Error: No se pudo ingresar a apartado Comida Caliente. " + e.getMessage());
			return false;
		}
	}

	/**
	 * Método - Validar opciones de burritos O sabor
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @return Retorna true o false Si los elementos estan Desplegados
	 * @date 28/01/2025
	 */
	public boolean TipoBurritos() {
		try {
			testCase.addTextEvidenceCurrentStep("Se muestran opciones de burritos");
			WebElement BurritosCla = au.driver.findElement(btn_BurritosClasicos);
			WebElement BurritosEsp = au.driver.findElement(btn_BurritosEspeciales);
			if (BurritosCla.isDisplayed() || BurritosEsp.isDisplayed()) {
				System.out.println("Se encontro las opciones Burritos");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(5);
				return true;
			} else {
				System.out.println("No se encontro las opciones de burritos");
				testCase.addTextEvidenceCurrentStep("No se muestran opciones de burritos.");
				return false;
			}
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error: No se pudo ingresar a apartado burritos. " + e.getMessage());
			return false;
		}
	}

	/**
	 * Método - Validar opciones de tacos Osabor
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @return Retorna true o false Si los elementos estan Desplegados
	 * @date 28/01/2025
	 */
	public boolean TipoTacos() {
		try {
			testCase.addTextEvidenceCurrentStep("Se muestran opciones de tacos");
			WebElement TacosCla = au.driver.findElement(btn_TacosClasicos);
			WebElement TacosEsp = au.driver.findElement(btn_TacosEspeciales);
			if (TacosCla.isDisplayed() && TacosEsp.isDisplayed()) {
				System.out.println("Se encontro las opciones tacos\"");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} else {
				System.out.println("No se encontro las opciones de tacos");
				testCase.addTextEvidenceCurrentStep("No se muestran opciones de tacos.");
				return false;
			}
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error: No se pudo ingresar a apartado tacos. " + e.getMessage());
			return false;
		}
	}

	/**
	 * Método - Validar bebidas TOP
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @return Retorna true o false Si el elemento esta Desplegado
	 * @date 23/01/2025
	 */
	public boolean BebidasTOP() {
		try {
			testCase.addTextEvidenceCurrentStep("Se muestran opciones de fast food TOP");
			au.scrollToElement(btn_BebidaTOP);
			WebElement BebidaTOP = au.driver.findElement(btn_BebidaTOP);
			if (BebidaTOP.isDisplayed()) {
				System.out.println("Se encontro las opciones de fast food bebidas TOP");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(5);
				return true;
			} else {
				System.out.println("No se encontro las opciones de fast food bebidas TOP");
				testCase.addTextEvidenceCurrentStep("No se muestran opciones de fast food bebidas TOP.");
				return false;
			}
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep(
					"Error: No se pudo ingresar a apartado fast food bebidas. " + e.getMessage());
			return false;
		}
	}

	/**
	 * Método - Validar Alimentos TOP
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @return Retorna true o false Si el elemento esta Desplegado
	 * @date 23/01/2025
	 */
	public boolean AlimentosTOP() {
		try {
			testCase.addTextEvidenceCurrentStep("Se muestran opciones de fast food TOP");
			WebElement alimentoTOP = au.driver.findElement(btn_AlimentoTOP);
			if (alimentoTOP.isDisplayed()) {
				System.out.println("Se encontro las opciones de fast food alimentos TOP");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(5);
				return true;
			} else {
				System.out.println("No se encontro las opciones de fast food alimentos TOP");
				testCase.addTextEvidenceCurrentStep("No se muestran opciones de fast food alimentos TOP.");
				return false;
			}
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep(
					"Error: No se pudo ingresar a apartado fast food alimentos. " + e.getMessage());
			return false;
		}
	}

	/**
	 * Método - Ingresar a Comida Caliente
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 23/01/2025
	 *
	 */
	public void seleccionarComidaCaliente() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Comida Caliente");
			au.click(btn_ComidaCaliente);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton Comida Caliente");
		}
	}

	/**
	 * Método - Ingresar a Quesera
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 12/06/2025
	 *
	 */
	public void seleccionarQuesera() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Quesera");
			au.click(btn_Quesera);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton Quesera");
		}
	}

	/**
	 * Método - Ingresar a TIPO Quesera
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 12/06/2025
	 *
	 */
	public void seleccionarTIPOQuesera() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Quesera");
			au.click(btn_Quesera1);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton Quesera");
		}
	}

	/**
	 * Método - Ingresar a Nachos
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 12/06/2025
	 *
	 */
	public void seleccionarNachos() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Nachos");
			au.click(btn_Nachos);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton Nachos");
		}
	}

	/**
	 * Método - Ingresar a Elote
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 12/06/2025
	 *
	 */
	public void seleccionarElote() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Elote");
			au.click(btn_Elote);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton Elote");
		}
	}

	/**
	 * Método - Ingresar Osabor
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 28/01/2025
	 *
	 */
	public void seleccionarOsabor() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Osabor");
			au.click(btn_ComidaOSABOR);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton Osabor");
		}
	}

	/**
	 * Método - Ingresar Tortas Osabor
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 28/01/2025
	 *
	 */
	public void seleccionarOTortas() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Tortas");
			au.click(btn_Otortas);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton Tortas");
		}
	}

	/**
	 * Método - Ingresar Quesadillas Osabor
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 11/06/2025
	 *
	 */
	public void seleccionarOQuesadillas() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Quesadillas");
			au.click(btn_OQuesadillas);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton Quesadillas");
		}
	}

	/**
	 * Método - Ingresar Quesadillas Osabor
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 11/06/2025
	 *
	 */
	public void seleccionarOOtros() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Otros");
			au.click(btn_OOtros);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton Otros");
		}
	}

	/**
	 * Método - Ingresar tipo de torta Osabor
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 28/01/2025
	 *
	 */
	public void seleccionartipoOTortas() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Tortas");
			au.click(btn_Otorta);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton Tortas");
		}
	}

	/**
	 * Método - Ingresar tipo de Quesadillas Osabor
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 11/06/2025
	 *
	 */
	public void seleccionartipoOQuesadillas() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Quesadillas");
			au.click(btn_OHarina);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton Quesadillas");
		}
	}

	/**
	 * Método - Ingresar Chilaquiles Osabor
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 11/06/2025
	 *
	 */
	public void seleccionarChilaquiles() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Chilaquiles");
			au.click(btn_OChilaquiles);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton Chilaquiles");
		}
	}

	/**
	 * Método - Ingresar a Molletes
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 31/01/2025
	 *
	 */
	public void seleccionarMolletes() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Molletes");
			au.scrollToElement(btn_Molletes);
			au.click(btn_Molletes);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton Molletes");
		}
	}

	/**
	 * Método - Ingresar a Pizzas
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 12/06/2025
	 *
	 */
	public void seleccionarPizzas() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Pizzas");
			au.scrollToElement(btn_Pizzas);
			au.click(btn_Pizzas);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton Pizzas");
		}
	}

	/**
	 * Método - Ingresar a Extras
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 12/06/2025
	 *
	 */
	public void seleccionarExtras() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Extras");
			au.scrollToElement(btn_Extras);
			au.click(btn_Extras);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton Extras");
		}
	}

	/**
	 * Método - Ingresar a Burritos Osabor
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 29/01/2025
	 *
	 */
	public void seleccionarOBurritos() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Burritos");
			au.click(btn_OBurritos);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton Burritos");
		}
	}

	/**
	 * Método - Ingresar a Tacos Osabor
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 29/01/2025
	 *
	 */
	public void seleccionarOTacos() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Tacos");
			au.click(btn_Otacos);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton Tacos");
		}
	}

	/**
	 * Método - Ingresar a burritos clasicos
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 28/01/2025
	 *
	 */
	public void seleccionarburritosclasicos() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton burritos clasicos");
			au.click(btn_BurritosClasicos);
			testCase.addScreenShotCurrentStep(au, "testImage");
			System.out.println("Se selecciono el boton Burritos Clasicos Exitosamente");
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton burritos clasicos");
		}
	}

	/**
	 * Método - Ingresar a tacos clasicos
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 29/01/2025
	 *
	 */
	public void seleccionartacosclasicos() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton tacos clasicos");
			au.click(btn_TacosClasicos);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton tacos clasicos");
		}
	}

	/**
	 * Método - Agregar Bebida TOP
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 23/01/2025
	 *
	 */
	public void agregartopbebida() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton + Bebida TOP");
			au.click(btn_BebidaTOP);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton + Bebida TOP");
		}
	}

	/**
	 * Método - Agregar Alimento TOP
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 23/01/2025
	 *
	 */
	public void agregartopalimento() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton + Alimento TOP");
			au.click(btn_AlimentoTOP);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton + Alimento TOP");
		}
	}

	/**
	 * Método - Selecciona Capuchino
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 24/01/2025
	 *
	 */
	public void seleccionarCapuchino() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Capuchino");
			au.click(btn_fastfoodBEBCapuchino);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton Capuchino");
		}
	}

	/**
	 * Método - Validar Opciones Capuchino
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @return Retorna true o false Si los elementos estan Desplegados
	 * @date 24/01/2025
	 */
	public boolean OpcionesCapuchino() {
		try {
			testCase.addTextEvidenceCurrentStep("Se muestran opciones de Capuchino");
			WebElement Opcioncapuchino = au.driver.findElement(btn_CapuchinoAndatti);
			WebElement OpcionNescafe = au.driver.findElement(btn_CapuchinoNescafe);
			if (Opcioncapuchino.isDisplayed() && OpcionNescafe.isDisplayed()) {
				System.out.println("Se encontro las opciones de Capuchino");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} else {
				System.out.println("No se encontro las opciones de Capuchino");
				testCase.addTextEvidenceCurrentStep("No se muestran opciones de Capuchino.");
				return false;
			}
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error: No se pudo ingresar a apartado Capuchino. " + e.getMessage());
			return false;
		}
	}

	/**
	 * Método - Validar Opcion Capuchino
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return Retorna true o false Si el elemento esta Desplegado
	 * @date 19/08/2025
	 */
	public boolean OpcionCapuchino() {
		try {
			testCase.addTextEvidenceCurrentStep("Se muestran opciones de Capuchino");
			WebElement Opcioncapuchino = au.driver.findElement(btn_CapuchinoAndatti);

			if (Opcioncapuchino.isDisplayed()) {
				System.out.println("Se encontro la opcion de Capuchino");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(5);
				return true;
			} else {
				System.out.println("No se encontro las opciones de Capuchino");
				testCase.addTextEvidenceCurrentStep("No se muestran opciones de Capuchino.");
				return false;
			}
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error: No se pudo ingresar a apartado Capuchino. " + e.getMessage());
			return false;
		}
	}

	/**
	 * Método - Selecciona opcion Capuchino
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 24/01/2025
	 *
	 */
	public void seleccionaropcionCapuchino() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Capuchino Andatti");
			au.click(btn_CapuchinoAndatti);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(15);
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton Capuchino Andatti");
		}
	}

	/**
	 * Método - Validar tipo Capuchino
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @return Retorna true o false Si el elemento esta Desplegado
	 * @date 24/01/2025
	 */
	public boolean TIPOCapuchino() {
		try {

			WebElement CapuchinoLatte = au.driver.findElement(btn_LATTE);
			au.scrollToElement(btn_LATTE);
			TimeUnit.SECONDS.sleep(3);
			if (CapuchinoLatte.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep("Se muestran opciones de Capuchino");
				System.out.println("Se encontro las opciones de Capuchino");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} else {
				System.out.println("No se encontro los tipos de Capuchino");
				testCase.addTextEvidenceCurrentStep("No se muestran tipos de Capuchino.");
				return false;
			}
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep(
					"Error: No se pudo ingresar a apartado tipos Capuchino. " + e.getMessage());
			return false;
		}
	}

	/**
	 * Método - Selecciona capuchino MOKA
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 24/01/2025
	 *
	 */
	public void seleccionarCapuchinoMOKA() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton MOKA");
			au.scrollToElement(btn_MOKA);
			au.click(btn_MOKA);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton MOKA");
		}
	}

	/**
	 * Método - Selecciona capuchino Chocolate
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 28/01/2025
	 *
	 */
	public void seleccionarCapuchinoChocolate() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Chocolate");
			au.click(btn_Chocolate);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton Chocolate");
		}
	}

	/**
	 * Método - Selecciona capuchino LATTE
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 28/01/2025
	 *
	 */
	public void seleccionarCapuchinoLATTE() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton LATTE");
			au.scrollToElement(btn_LATTE);
			au.click(btn_LATTE);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton LATTE");
		}
	}

	/**
	 * Método - Validar tamaño de bebida
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @return Retorna true o false Si los elementos estan Desplegados
	 * @date 24/01/2025
	 */
	public boolean ValidamedidaBebida() {
		try {
			testCase.addTextEvidenceCurrentStep("Se muestran Medidas de Bebidas");

			if (isElementPresent(btn_12oz) || isElementPresent(btn_12OZ) || isElementPresent(btn_16oz)) {
				System.out.println("Se encontro las Medidas de Bebidas");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(7);
				return true;
			} else {
				System.out.println("No se encontro los tipos de Capuchino");
				testCase.addTextEvidenceCurrentStep("No se muestran Medidas de Bebidas.");
				return false;
			}
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep(
					"Error: No se pudo ingresar a apartado Medidas de Bebidas. " + e.getMessage());
			return false;
		}
	}

	/**
	 * Método - Seleccionar medida de bebida
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 24/01/2025
	 *
	 */
	public void seleccionarmedidabebida() {
		try {
			TimeUnit.SECONDS.sleep(10);
			if (isElementPresent(btn_12oz)) {
				testCase.addTextEvidenceCurrentStep("Tap en el boton 12oz");
				System.out.println("No es encontro la medida");
				au.click(btn_12oz);
				testCase.addScreenShotCurrentStep(au, "testImage");
			} else if (isElementPresent(btn_12OZ)) {
				testCase.addTextEvidenceCurrentStep("Tap en el boton 12OZ");
				System.out.println("No es encontro la medida");
				au.click(btn_12OZ);
				testCase.addScreenShotCurrentStep(au, "testImage");
			} else if (isElementPresent(btn_16oz)) {
				testCase.addTextEvidenceCurrentStep("Tap en el boton 16oz");
				System.out.println("No es encontro la medida");
				au.click(btn_16oz);
				testCase.addScreenShotCurrentStep(au, "testImage");
			} else {
				System.out.println("No es encontro la medida");
			}
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton medida esperado");
		}
	}

	/**
	 * Método - Agregar articulo
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 24/01/2025
	 *
	 */
	public void agregarART() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton +");
			au.click(btn_Agregar);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton +");
		}
	}

	/**
	 * Método - Boton Continuar
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 24/01/2025
	 *
	 */
	public void BotonContinuar() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton continuar");
			au.click(btn_continuar);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton continuar");
		}
	}

	/**
	 * Método - Selecciona Americano
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 27/01/2025
	 *
	 */
	public void seleccionarAmericano() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Americano");
			au.click(btn_fastfoodBEBAmericano);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton Americano");
		}
	}

	/**
	 * Método - Validar Opciones Americano
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @return Retorna true o false Si el elemento esta Desplegado
	 * @date 27/01/2025
	 */
	public boolean OpcionesAmericano() {
		try {
			testCase.addTextEvidenceCurrentStep("Se muestran opciones de Americano");
			WebElement Opcioname = au.driver.findElement(btn_AmericanoAndatti);
			if (Opcioname.isDisplayed()) {
				System.out.println("Se encontro las opciones de Americano");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} else {
				System.out.println("No se encontro las opciones de Americano");
				testCase.addTextEvidenceCurrentStep("No se muestran opciones de Americano.");
				return false;
			}
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error: No se pudo ingresar a apartado Americano. " + e.getMessage());
			return false;
		}
	}

	/**
	 * Método - Selecciona opcion Americano
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 27/01/2025
	 *
	 */
	public void seleccionaropcionAmericano() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Americano Andatti");
			au.click(btn_AmericanoAndatti);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton Americano Andatti");
		}
	}

	/**
	 * Método - Validar tipo Americano
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @return Retorna true o false Si los elementos estan Desplegados
	 * @date 27/01/2025
	 */
	public boolean TIPOAmericano() {
		try {
			testCase.addTextEvidenceCurrentStep("Se muestran opciones de Americano");
			WebElement cafedeolla = au.driver.findElement(btn_cafedeolla);
			WebElement Intenso = au.driver.findElement(btn_intenso);
			WebElement MezMexicana = au.driver.findElement(btn_MezMexicana);
			if (cafedeolla.isDisplayed() && Intenso.isDisplayed() && MezMexicana.isDisplayed()) {
				System.out.println("Se encontro las opciones de Americano");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} else {
				System.out.println("No se encontro los tipos de Americano");
				testCase.addTextEvidenceCurrentStep("No se muestran tipos de Americano.");
				return false;
			}
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep(
					"Error: No se pudo ingresar a apartado tipos Americano. " + e.getMessage());
			return false;
		}
	}

	/**
	 * Método - Selecciona intenso
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 27/01/2025
	 *
	 */
	public void seleccionarintenso() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton intenso");
			au.click(btn_intenso);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton intenso");
		}
	}

	/**
	 * Método - Selecciona Cafe de olla
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 27/01/2025
	 *
	 */
	public void seleccionarcafeolla() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton intenso");
			au.click(btn_cafedeolla);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton Cafe de olla");
		}
	}

	/**
	 * Método - Selecciona Mezcla Mexicana
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @date 27/01/2025
	 *
	 */
	public void seleccionarMezclaMexicana() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Mezcla Mexicana");
			au.click(btn_MezMexicana);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton Mezcla Mexicana");
		}
	}

	/**
	 * Método - Consulta Articulo Fast Food Tamales
	 * 
	 * @author Irving Ali
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @param nFF  Nombre o descripción del artículo a buscar
	 * @return Retorna el Sku del articulo consultado
	 * @date 19/06/2025
	 */
	public String ConsultaFFATamal(String ipPv, String nFF) {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + ipPv + ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV, GlobalVariables.passPV);

			String query = "select top 5 SKU from XposStore.dbo.ITEM where DESCRIPTION like '%" + nFF + "%'";

			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			System.out.println(val);
			artTamal = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			System.out.println("Error al consultar el Articulo Tamal en BD");
			e.printStackTrace();
			artTamal = "";
		}

		return artTamal;
	}

	/**
	 * Método - Consulta Articulo FastFood
	 * 
	 * @author Irving Ali
	 * @param nFF  Nombre o descripción del artículo a buscar
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @return Retorna el SJU del articulo FastFood consultado
	 * @date 19/06/2025
	 */
	public String ConsultaFFASKU(String nFF, String ipPv) {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + ipPv + ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV, GlobalVariables.passPV);
			String query = "select top 5 SKU from XposStore.dbo.ITEM where DESCRIPTION like '%" + nFF + "%'";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			System.out.println("Se obtuvo el valor: " + val);
			artFFAG = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			System.out.println("Error al realizar la consulta en base de datos");
			e.printStackTrace();
			artFFAG = "";
		}

		return artFFAG;
	}

	/**
	 * Método - Ingresar Articulo fast food por nombre
	 * 
	 * @author Irving Ali
	 * @param nFF  Nombre o descripción del artículo a buscar
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @date 13/06/2025
	 */
	public void ingresarNombreFF(String nFF, String ipPv) {
		try {

			WebElement buscador = au.driver.findElement(Buscador);
			String arttamal = ConsultaFFASKU(nFF, ipPv);

			testCase.addTextEvidenceCurrentStep("Tap en Botón Buscador de articulos");
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Ingresar el valor al buscador ");
			au.driver.hideKeyboard();
			buscador.sendKeys(arttamal);
			testCase.addScreenShotCurrentStep(au, "testImage");

			testCase.addTextEvidenceCurrentStep("Tap en el boton Buscar");
			TimeUnit.SECONDS.sleep(5);
			((AndroidDriver) au.driver).pressKey(new KeyEvent(AndroidKey.ENTER));
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);

			testCase.addTextEvidenceCurrentStep("Tap en el articulo");
			au.click(btn_cantidad);
			testCase.addScreenShotCurrentStep(au, "testImage");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Validar se muestre pantalla de pago exitoso
	 * 
	 * @author Irving Ali Orozco
	 * @param N/A
	 * @return Retorna true o false Si el elemento esta Desplegado
	 * @date 16/06/2025
	 */
	public boolean validarPantPagoExitoso() {
		try {

			TimeUnit.SECONDS.sleep(5);
			WebElement msjrec = au.driver.findElement(PantPago);

			if (msjrec.isDisplayed()) {
				testCase.addTextEvidenceCurrentStep("Se valida que se encuentre la pantalla de pago exitoso");
				System.out.println("Se encontro pantalla de manera exitosa");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} else {
				System.out.println("No se pudo encontrar");
				testCase.addScreenShotCurrentStep(au, "testImage");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addScreenShotCurrentStep(au, "testImage");
			return false;
		}
	}

	/*
	 * +++++++++++++++++++++++++++++
	 * 
	 * CAMBIOS Cesar Delgado
	 * 
	 * ++++++++++++++++++++++++++++/
	 */

	/**
	 * Metodo para Seleccionar Tipo Corte X
	 * 
	 * @author Cesar Delgado
	 * @param N/A
	 * @date 05/08/2025
	 * 
	 */
	public void seleccionarTipoCorteX() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap tipo de corte X");
			au.click(cortX);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo para consultar Corte X
	 * 
	 * @author Cesar Delgado
	 * @param N/A
	 * @date 05/08/2025
	 */
	public void consultarCorteX() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap boton consulta corte X");
			au.click(btn_consCorteX);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Seleccionar tipo de servicio Izzi linea
	 * 
	 * @author Cesar Delgado
	 * @param servicio El nombre del servicio a seleccionar
	 * @date 07/08/2025
	 */
	public void seleccionarServicioIzzi(String servicio) {
		try {

			testCase.addTextEvidenceCurrentStep("Se selecciona el buscador de servicios");
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "Abrir buscador");
			TimeUnit.SECONDS.sleep(2);

			testCase.addTextEvidenceCurrentStep("Se ingresa el nombre del servicio: " + servicio);
			au.sendKeys(Buscador, servicio);
			testCase.addScreenShotCurrentStep(au, "Servicio ingresado");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Se presiona ENTER para buscar");
			au.click(MBuscador);
			au.click(Buscador);
			au.driver.executeScript("mobile:performEditorAction", ImmutableMap.of("action", "search"));
			TimeUnit.SECONDS.sleep(4);

			testCase.addTextEvidenceCurrentStep("Se selecciona el servicio");
			au.click(servIzziD);
			testCase.addScreenShotCurrentStep(au, "Servicio seleccionado");
			TimeUnit.SECONDS.sleep(5);

		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error al seleccionar el servicio: " + e.getMessage());
		}
	}

	/**
	 * Método - Seleccionar tipo de servicio Telmex en linea
	 * 
	 * @author Cesar Delgado
	 * @param servicio El nombre del servicio a seleccionar
	 * @date 07/08/2025
	 */
	public void seleccionarServicioTelmex(String servicio) {
		try {

			testCase.addTextEvidenceCurrentStep("Se selecciona el buscador de servicios");
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "Abrir buscador");
			TimeUnit.SECONDS.sleep(2);

			testCase.addTextEvidenceCurrentStep("Se ingresa el nombre del servicio: " + servicio);
			au.sendKeys(Buscador, servicio);
			testCase.addScreenShotCurrentStep(au, "Servicio ingresado");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Se presiona ENTER para buscar");
			au.click(MBuscador);
			au.click(Buscador);
			au.driver.executeScript("mobile:performEditorAction", ImmutableMap.of("action", "search"));

			TimeUnit.SECONDS.sleep(4);
			au.driver.hideKeyboard();

			testCase.addTextEvidenceCurrentStep("Se selecciona el servicio");
			au.click(servTelmex);
			testCase.addScreenShotCurrentStep(au, "Servicio seleccionado");
			TimeUnit.SECONDS.sleep(5);

		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error al seleccionar el servicio: " + e.getMessage());
		}
	}

	/**
	 * Método - Seleccionar tipo de servicio Dish en linea
	 * 
	 * @author Cesar Delgado
	 * @param servicio El nombre del servicio a seleccionar
	 * @date 06/08/2025
	 */
	public void seleccionarServicioDish(String servicio) {
		try {

			testCase.addTextEvidenceCurrentStep("Se selecciona el buscador de servicios");
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "Abrir buscador");
			TimeUnit.SECONDS.sleep(2);

			testCase.addTextEvidenceCurrentStep("Se ingresa el nombre del servicio: " + servicio);
			au.sendKeys(Buscador, servicio);
			testCase.addScreenShotCurrentStep(au, "Servicio ingresado");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Se presiona ENTER para buscar");
			au.click(MBuscador);
			au.click(Buscador);
			au.driver.executeScript("mobile:performEditorAction", ImmutableMap.of("action", "search"));
			au.driver.hideKeyboard();
			TimeUnit.SECONDS.sleep(4);

			testCase.addTextEvidenceCurrentStep("Se selecciona el servicio");
			au.click(servDish);
			testCase.addScreenShotCurrentStep(au, "Servicio seleccionado");
			TimeUnit.SECONDS.sleep(15);

		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error al seleccionar el servicio: " + e.getMessage());
		}
	}

	/**
	 * Método - Seleccionar tipo de servicio Mercado Libre en linea
	 * 
	 * @author Cesar Delgado
	 * @param servicio El nombre del servicio a seleccionar
	 * @date 06/08/2025
	 */
	public void seleccionarServicioMercadoLibre(String servicio) {
		try {

			testCase.addTextEvidenceCurrentStep("Se selecciona el buscador de servicios");
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "Abrir buscador");
			TimeUnit.SECONDS.sleep(2);

			testCase.addTextEvidenceCurrentStep("Se ingresa el nombre del servicio: " + servicio);
			au.sendKeys(Buscador, servicio);
			testCase.addScreenShotCurrentStep(au, "Servicio ingresado");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Se presiona ENTER para buscar");
			au.click(MBuscador);
			au.click(Buscador);
			au.driver.executeScript("mobile:performEditorAction", ImmutableMap.of("action", "search"));
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.driver.hideKeyboard();

			TimeUnit.SECONDS.sleep(10);

			testCase.addTextEvidenceCurrentStep("Se selecciona el servicio");
			waitForVisibility(servMercLibre, Duration.ofSeconds(20));
			au.click(servMercLibre);
			testCase.addScreenShotCurrentStep(au, "Servicio seleccionado");
			TimeUnit.SECONDS.sleep(15);

		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error al seleccionar el servicio: " + e.getMessage());
		}
	}

	/**
	 * Método - Seleccionar tipo de servicio Dish CB en linea
	 * 
	 * @author Cesar Delgado
	 * @param servicio El nombre del servicio a seleccionar
	 * @date 07/08/2025
	 */
	public void seleccionarServicioDishCB(String servicio) {
		try {

			testCase.addTextEvidenceCurrentStep("Se selecciona el buscador de servicios");
			au.click(MBuscador);
			au.click(Buscador);
			testCase.addScreenShotCurrentStep(au, "Abrir buscador");
			TimeUnit.SECONDS.sleep(2);

			testCase.addTextEvidenceCurrentStep("Se ingresa el nombre del servicio: " + servicio);
			au.sendKeys(Buscador, servicio);
			testCase.addScreenShotCurrentStep(au, "Servicio ingresado");
			TimeUnit.SECONDS.sleep(5);

			testCase.addTextEvidenceCurrentStep("Se presiona ENTER para buscar");
			au.click(MBuscador);
			au.click(Buscador);
			au.driver.executeScript("mobile:performEditorAction", ImmutableMap.of("action", "search"));
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.driver.hideKeyboard();

			TimeUnit.SECONDS.sleep(10);

			testCase.addTextEvidenceCurrentStep("Se selecciona el servicio");
			au.click(servDishCB);
			testCase.addScreenShotCurrentStep(au, "Servicio seleccionado");
			TimeUnit.SECONDS.sleep(15);

		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error al seleccionar el servicio: " + e.getMessage());
		}
	}

	/**
	 * Metodo para dar clic al boton "Si, Continuar"
	 * 
	 * @author Cesar Delgado
	 * @param N/A
	 * @date 06/08/2025
	 */
	public void siContinuarServ() {
		try {
			testCase.addTextEvidenceCurrentStep("Se presiona el boton Si, Continuar ");
			au.click(btn_siContinuar);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(15);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo para dar clic al boton "No, Continuar"
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 16/12/2025
	 */
	public void noContinuarServ() {
		try {
			testCase.addTextEvidenceCurrentStep("Se presiona el boton No, Continuar ");
			au.click(btn_noContinuar);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(15);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Si cancelar pago de servicio
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @date 16/12/2025
	 */
	public void siCancelarServ() {
		try {
			testCase.addTextEvidenceCurrentStep("Se presiona el boton Si, Cancelar ");
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.click(btn_siContinuar);
			TimeUnit.SECONDS.sleep(15);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Validar Opciones de Capuchino
	 * 
	 * @author Cesar Delgado
	 * @param N/A
	 * @return Retorna true o false Si el elemento esta Desplegado
	 * @date 01/08/2025
	 */
	public boolean OpcionesDeCapuchino() {
		try {
			testCase.addTextEvidenceCurrentStep("Se muestran opciones de Capuchino");
			WebElement Opcioncapuchino = au.driver.findElement(btn_CapAndatti);
			if (Opcioncapuchino.isDisplayed()) {
				System.out.println("Se encontro las opciones de Capuchino");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(5);
				return true;
			} else {
				System.out.println("No se encontro las opciones de Capuchino");
				testCase.addTextEvidenceCurrentStep("No se muestran opciones de Capuchino.");
				return false;
			}
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error: No se pudo ingresar a apartado Capuchino. " + e.getMessage());
			return false;
		}
	}

	/**
	 * Método - Selecciona capuchino Vainilla
	 *
	 * @autor César Delgado
	 * @param N/A
	 * @date 01/08/2025
	 */
	public void seleccionarCapuchinoVainilla() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Vainilla");
			au.scrollToElement(btn_Vainilla);
			au.click(btn_Vainilla);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton Vainilla");
		}
	}

	/**
	 * Método - Selecciona capuchino Canela
	 *
	 * @autor César Delgado
	 * @param N/A
	 * @date 01/08/2025
	 */
	public void seleccionarCapuchinoCanela() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Canela");
			au.scrollToElement(btn_Canela);
			au.click(btn_Canela);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton Canela");
		}
	}

	/**
	 * Método - Selecciona capuchino Chai
	 *
	 * @autor César Delgado
	 * @param N/A
	 * @date 01/08/2025
	 */
	public void seleccionarCapuchinoChai() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton Chai");
			au.scrollToElement(btn_Chai);
			au.click(btn_Chai);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton Chai");
		}
	}

	/**
	 * Método - Validar tamaño de bebida
	 * 
	 * @author Cesar Delgado
	 * @param N/A
	 * @return Retorna true o false Si los elementos estan Desplegados
	 * @date 04/08/2025
	 */
	public boolean ValidamedidaBebidaRoz() {
		try {
			testCase.addTextEvidenceCurrentStep("Se muestran Medidas de Bebidas");
			WebElement MedidaR16oz = au.driver.findElement(btn_R16oz);
			WebElement MedidaR20oz = au.driver.findElement(btn_R20oz);
			if (MedidaR16oz.isDisplayed() && MedidaR20oz.isDisplayed()) {
				System.out.println("Se encontro las Medidas de Bebidas");
				testCase.addScreenShotCurrentStep(au, "testImage");
				TimeUnit.SECONDS.sleep(5);
				return true;
			} else {
				System.out.println("No se encontro los tipos de Capuchino");
				testCase.addTextEvidenceCurrentStep("No se muestran Medidas de Bebidas.");
				return false;
			}
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep(
					"Error: No se pudo ingresar a apartado Medidas de Bebidas. " + e.getMessage());
			return false;
		}
	}

	/**
	 * Método - Seleccionar medida de bebida
	 * 
	 * @author Cesar Delgado
	 * @param N/A
	 * @date 04/08/2025
	 *
	 */
	public void seleccionarmedidabebidaR16oz() {
		try {
			testCase.addTextEvidenceCurrentStep("Tap en el boton R16oz");
			au.click(btn_R16oz);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra boton R16oz");
		}
	}

	/**
	 * Método - Seleccionar burrito frijoles
	 * 
	 * @author Cesar Delgado
	 * @param N/A
	 * @date 20/08/2025
	 *
	 */
	public void agregarBurritoFrijoles() {
		try {
			TimeUnit.SECONDS.sleep(5);
			testCase.addTextEvidenceCurrentStep("Selecciona burrito frijoles");
			au.scrollToElement(btn_burritoFrijoles);
			au.click(btn_Agregar);
			testCase.addScreenShotCurrentStep(au, "testImage");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addTextEvidenceCurrentStep("Error, No se muestra burrito frijoles");
		}
	}

	/**
	 * Método - Consulta monto de la comisión del servicio en base de datos local
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @return El monto de la comisión del servicio consultado en la base de datos
	 *         local
	 * @date 12/12/2025
	 */
	public String consultaMontoComisionServicioLocal(String ipPv, String idServCode) {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + ipPv + ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV, GlobalVariables.passPV);

			String query = "select top 1 VALUE from XposStore.dbo.SERVICE_PARAMETER_VALUES_VW "
					+ "WHERE SERVICE_CODE = '" + idServCode + "' and PARAMETER_CODE = 'MS_VALCOM' ";
			SQLResult res = new SQLResult(conn, query);
			String val = res.getFirstData().toString();
			BigDecimal decimal = new BigDecimal(val);
			montoComServ = decimal.stripTrailingZeros().toPlainString();
			System.out.println("El valor de la consulta es: " + montoComServ);
			testCase.addQueryEvidenceCurrentStep(res);

		} catch (Exception e) {
			System.out.println("Error al consultar el monto en BD Local");
			e.printStackTrace();
			montoComServ = "";
		}

		return montoComServ;
	}

	/**
	 * Método - Consulta monto de la comisión por consulta del servicio en base de
	 * datos local
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @return El monto de la comisión del servicio consultado en la base de datos
	 *         local
	 * @date 15/12/2025
	 */
	public String consultaMontoComisionConsServicioLocal(String ipPv, String idServCode) {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + ipPv + ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV, GlobalVariables.passPV);

			String query = "select top 1 VALUE from XposStore.dbo.SERVICE_PARAMETER_VALUES_VW "
					+ "WHERE SERVICE_CODE = '" + idServCode + "' and PARAMETER_CODE = 'MS_COM_CON' ";
			SQLResult res = new SQLResult(conn, query);
			String val = res.getFirstData().toString();
			BigDecimal decimal = new BigDecimal(val);
			montoComCons = decimal.stripTrailingZeros().toPlainString();
			System.out.println("El valor de la consulta es: " + montoComCons);
			testCase.addQueryEvidenceCurrentStep(res);

		} catch (Exception e) {
			System.out.println("Error al consultar el monto en BD Local");
			e.printStackTrace();
			montoComCons = "";
		}

		return montoComCons;
	}

	/**
	 * Método - Validación monto de consulta de servicio en base de datos local
	 * 
	 * @author Cesar Guel
	 * @param ipPv
	 * @param idServCode
	 * @param montoEsperado
	 * @return Retorna true o false Si el monto es correcto
	 * @date 12/12/2025
	 */
	public boolean validaMontoComisionServicio(String ipPv, String idServCode, String montoEsperado) {
		try {
			String montoConsulta = consultaMontoComisionServicioLocal(ipPv, idServCode);
			TimeUnit.SECONDS.sleep(5);
			System.out.println(montoConsulta);

			if (montoConsulta.equals(montoEsperado)) {
				System.out.println("El monto de consulta fue el esperado");
				return true;
			} else {
				System.out.println("El monto de la consulta no fue el esperado");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Hubo algun problema al realizar la validación");
			return false;
		}

	}

	/**
	 * Método - Validación monto de cobro por consulta de servicio en base de datos
	 * local
	 * 
	 * @author Cesar Guel
	 * @param ipPv
	 * @param idServCode
	 * @param montoEsperado
	 * @return Retorna true o false Si el monto es correcto
	 * @date 15/12/2025
	 */
	public boolean validaMontoComisionConsServicio(String ipPv, String idServCode, String montoEsperado) {
		try {
			String montoConsultaS = consultaMontoComisionConsServicioLocal(ipPv, idServCode);
			TimeUnit.SECONDS.sleep(5);
			System.out.println(montoConsultaS);

			if (montoConsultaS.equals(montoEsperado)) {
				System.out.println("El monto de consulta fue el esperado");
				return true;
			} else {
				System.out.println("El monto de la consulta no fue el esperado");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Hubo algun problema al realizar la validación");
			return false;
		}

	}

	/**
	 * Método - Consulta Store Id de la base de datos nube Settings QA
	 * 
	 * @author Cesar Guel
	 * @param nomTienda El nombre de la tienda a utilizar
	 * @return El Store Id de la tienda utilizada
	 * @date 12/12/2025
	 */
	public String consultaStoreIdSettings(String nomTienda) {
		try {
			Connection conn = DriverManager.getConnection(GlobalVariables.urlOxxoCoudQA, GlobalVariables.userQANubeG,
					GlobalVariables.passwordQANubeG);
			System.out.println(nomTienda);
			String query = "select top 1 STORE_PLACE_ID FROM XposCloudSettingQA.dbo.STORE_PLACE where CR_STORE like '%"
					+ nomTienda + "%'  ";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			System.out.println(val);
			StoreIdP = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			e.printStackTrace();
			StoreIdP = "";
		}

		return StoreIdP;
	}

	/**
	 * Método - Consulta monto de la comisión del servicio en base de datos de la
	 * nube
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @return El monto de la comisión del servicio consultado en la base de datos
	 *         local
	 * @date 12/12/2025
	 */
	public String consultaMontoComisionServicioNube(String idServCode, String nomTienda) {
		try {
			String storeIdSet = consultaStoreIdSettings(nomTienda);

			Connection conn = DriverManager.getConnection(GlobalVariables.urlOxxoCloudQASett,
					GlobalVariables.userQANubeG, GlobalVariables.passwordQANubeG);

			String query = "SELECT TOP 1 sv.VALUE "
					+ "FROM XposCloudSettingQA.DBO.SERVICE_PARAM_CONF spc WITH (NOLOCK) "
					+ "INNER JOIN XposCloudSettingQA.DBO.SERVICES_SKU_VALUE ssv WITH (NOLOCK) "
					+ "    ON spc.SERVICES_SKU_VALUE_ID = ssv.SERVICES_SKU_VALUE_ID "
					+ "INNER JOIN XposCloudSettingQA.DBO.SERVICES_VALUE sv WITH (NOLOCK) "
					+ "    ON sv.SERVICES_VALUE_ID = ssv.SERVICES_VALUE_ID "
					+ "INNER JOIN XposCloudSettingQA.DBO.SERVICE_PARAMETER sp WITH (NOLOCK) "
					+ "    ON sp.SERVICE_PARAMETER_ID = ssv.SERVICE_PARAMETER_ID " + "WHERE spc.STORE_PLACE_ID = "
					+ storeIdSet + " " + "  AND ssv.SKU = '" + idServCode + "' " + "  AND sp.CODE = 'MS_VALCOM' "
					+ "ORDER BY sp.CODE, sv.TM_CRT DESC;";
			SQLResult res = new SQLResult(conn, query);
			String val = res.getFirstData().toString();
			BigDecimal decimal = new BigDecimal(val);
			montoComServN = decimal.stripTrailingZeros().toPlainString();
			System.out.println("El valor de la consulta es: " + montoComServN);
			testCase.addQueryEvidenceCurrentStep(res);

		} catch (Exception e) {
			System.out.println("Error al consultar el monto en BD de la nube");
			e.printStackTrace();
			montoComServN = "";
		}

		return montoComServN;
	}

	/**
	 * Método - Consulta monto de la comisión por consulta del servicio en base de
	 * datos de la nube
	 * 
	 * @author Cesar Guel
	 * @param ipPv La ip de la maquina que se esta utilizando
	 * @return El monto de la comisión del servicio consultado en la base de datos
	 *         local
	 * @date 15/12/2025
	 */
	public String consultaMontoComisionConsServicioNube(String idServCode, String nomTienda) {
		try {
			String storeIdSet = consultaStoreIdSettings(nomTienda);

			Connection conn = DriverManager.getConnection(GlobalVariables.urlOxxoCloudQASett,
					GlobalVariables.userQANubeG, GlobalVariables.passwordQANubeG);

			String query = "SELECT TOP 1 sv.VALUE "
					+ "FROM XposCloudSettingQA.DBO.SERVICE_PARAM_CONF spc WITH (NOLOCK) "
					+ "INNER JOIN XposCloudSettingQA.DBO.SERVICES_SKU_VALUE ssv WITH (NOLOCK) "
					+ "    ON spc.SERVICES_SKU_VALUE_ID = ssv.SERVICES_SKU_VALUE_ID "
					+ "INNER JOIN XposCloudSettingQA.DBO.SERVICES_VALUE sv WITH (NOLOCK) "
					+ "    ON sv.SERVICES_VALUE_ID = ssv.SERVICES_VALUE_ID "
					+ "INNER JOIN XposCloudSettingQA.DBO.SERVICE_PARAMETER sp WITH (NOLOCK) "
					+ "    ON sp.SERVICE_PARAMETER_ID = ssv.SERVICE_PARAMETER_ID " + "WHERE spc.STORE_PLACE_ID = "
					+ storeIdSet + " " + "  AND ssv.SKU = '" + idServCode + "' " + "  AND sp.CODE = 'MS_COM_CON' "
					+ "ORDER BY sp.CODE, sv.TM_CRT DESC;";
			SQLResult res = new SQLResult(conn, query);
			String val = res.getFirstData().toString();
			BigDecimal decimal = new BigDecimal(val);
			montoComConsServ = decimal.stripTrailingZeros().toPlainString();
			System.out.println("El valor de la consulta es: " + montoComConsServ);
			testCase.addQueryEvidenceCurrentStep(res);

		} catch (Exception e) {
			System.out.println("Error al consultar el monto en BD de la nube");
			e.printStackTrace();
			montoComConsServ = "";
		}

		return montoComConsServ;
	}

	/**
	 * Método - Validación monto de consulta de servicio en base de datos de la nube
	 * 
	 * @author Cesar Guel
	 * @param idServCode    El id del servicio a consultar
	 * @param nomTienda     El nombre de la tienda a utilizar
	 * @param montoEsperado El monto que se espera de respuesta
	 * @return Retorna true o false Si el monto es correcto
	 * @date 12/12/2025
	 */
	public boolean validaMontoComisionServicioN(String idServCode, String nomTienda, String montoEsperado) {
		try {
			String montoConsulta = consultaMontoComisionServicioNube(idServCode, nomTienda);
			TimeUnit.SECONDS.sleep(5);
			System.out.println(montoConsulta);

			if (montoConsulta.equals(montoEsperado)) {
				System.out.println("El monto de consulta fue el esperado");
				return true;
			} else {
				System.out.println("El monto de la consulta no fue el esperado");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Hubo algun problema al realizar la validación");
			return false;
		}

	}

	/**
	 * Método - Validación monto de consulta de servicio en base de datos de la nube
	 * 
	 * @author Cesar Guel
	 * @param idServCode    El id del servicio a consultar
	 * @param nomTienda     El nombre de la tienda a utilizar
	 * @param montoEsperado El monto que se espera de respuesta
	 * @return Retorna true o false Si el monto es correcto
	 * @date 12/12/2025
	 */
	public boolean validaMontoComisionConsultaN(String idServCode, String nomTienda, String montoEsperado) {
		try {
			String montoConsultaC = consultaMontoComisionConsServicioNube(idServCode, nomTienda);
			TimeUnit.SECONDS.sleep(5);
			System.out.println(montoConsultaC);

			if (montoConsultaC.equals(montoEsperado)) {
				System.out.println("El monto de consulta fue el esperado");
				return true;
			} else {
				System.out.println("El monto de la consulta no fue el esperado");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Hubo algun problema al realizar la validación");
			return false;
		}

	}

	/**
	 * Método - Validación de cobro de comisión por servicio
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return Retorna true o false si es que se muestra el cobro de la comisión
	 * @date 16/12/2025
	 */
	public boolean validarCobroComServ() {
		try {
			if (isElementPresent(btn_comServicio)) {
				System.out.println("Se encontro exitosamente el elemento");
				return true;
			} else {
				System.out.println("No se pudo encontrar exitosamente el elemento");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("Hubo problema al realizar la validación");
			return false;
		}
	}

	/**
	 * Método - Validación de cobro de comisión por consulta del servicio
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return Retorna true o false si es que se muestra el cobro de la comisión por
	 *         consulta
	 * @date 16/12/2025
	 */
	public boolean validarCobroComConsServ() {
		try {
			if (isElementPresent(btn_comXConsulta)) {
				System.out.println("Se encontro exitosamente el elemento");
				return true;
			} else {
				System.out.println("No se pudo encontrar exitosamente el elemento");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("Hubo problema al realizar la validación");
			return false;
		}
	}

	/**
	 * Método - Validación cobro de comisión de servicios no duplicadas
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return Retorna true o false si es que el cobro fue duplicado
	 */
	public boolean validarCobroComServD() {
		try {
			waitForVisibility(btn_comServicio, Duration.ofSeconds(10));
			List<WebElement> elementos = au.driver.findElements(btn_comServicio);

			if (elementos.size() == 1) {
				System.out.println("Elemento encontrado correctamente (sin duplicados)");
				return true;

			} else if (elementos.size() > 1) {
				System.out.println("Elemento duplicado. Total encontrados: " + elementos.size());
				return false;

			} else {
				System.out.println("Elemento no encontrado");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Hubo problema al realizar la validación");
			return false;
		}
	}

	/**
	 * Método - Validación de leyenda comisión de servicio
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return Retorna true o false si la leyenda esta presente
	 * @date 18/12/2025
	 */
	public boolean validarLeyComServ() {
		try {
			if (isElementPresent(lb_leyCom)) {
				System.out.println("Se encontro exitosamente el elemento");
				return true;
			} else {
				System.out.println("No se pudo encontrar exitosamente el elemento");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("Hubo problema al realizar la validación");
			return false;
		}
	}

	/**
	 * Método - Validar tiempo de espera de la pantalla de servicios
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return Retorna true o false si el tiempo de espera es minimo
	 * @date 19/12/2025
	 */
	public boolean validarTiemEspServ() {
		try {
			long inicio = System.currentTimeMillis();

			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.presenceOfElementLocated(lb_clienteServ));

			long fin = System.currentTimeMillis();
			long tiempo = (fin - inicio) / 1000;

			System.out.println("Elemento encontrado en " + tiempo + " segundos");

			if (tiempo <= 10) {
				System.out.println("Elemento dentro del tiempo permitido");
				return true;
			} else {
				System.out.println("Elemento tardó más de 10 segundos");
				return false;
			}

		} catch (TimeoutException te) {
			System.out.println("Elemento NO apareció en menos de 10 segundos");
			return false;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Hubo problema al realizar la validación");
			return false;
		}
	}

	/**
	 * Método - Validar que se muestren los elementos de servicio presentes
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return Retorna true o false si es que los elementos de la lista son
	 *         presentes
	 * @date 19/12/2025
	 */
	public boolean validarElementosServ() {
		try {
			By[] elementos = { lb_clienteServ, lb_refClient, lb_salAf, lb_edoServ, lb_monto, lb_montoP, lb_com,
					lb_montoT };

			for (By elemento : elementos) {
				if (!isElementPresent(elemento)) {
					System.out.println("Elemento NO encontrado: " + elemento);
					return false;
				} else {
					System.out.println("Elemento encontrado: " + elemento);
					return true;
				}
			}

			System.out.println("Todos los elementos están presentes correctamente");
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Hubo problema al validar los elementos de cobro de servicio");
			return false;
		}
	}

	/**
	 * Método - Validar que no se muestre la comisión al no tener adeudo
	 * 
	 * @author Cesar Guel
	 * @param N/A
	 * @return Retorna true o false si la comisión no se muestra
	 * @date 19/12/2025
	 */
	public boolean validarNoCobroComConsServ() {
		try {
			TimeUnit.SECONDS.sleep(10);
			if (!isElementPresent(btn_comXConsulta)) {
				testCase.addTextEvidenceCurrentStep("Se valida que no se muestre el cobro por consulta");
				System.out.println("Se encontro exitosamente el elemento");
				testCase.addScreenShotCurrentStep(au, "testImage");

				return true;
			} else {
				System.out.println("No se pudo encontrar exitosamente el elemento");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("Hubo problema al realizar la validación");
			return false;
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	/*
	 * 
	 * Metodos para los CP de la Matriz Matriz POS Movil Remediaciones MVP1 Cambios
	 * MMorales
	 * 
	 */

	/**
	 * Método - Dar click al boton de Configuraciones
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 07/02/2026
	 */
	public void clickBtnConfiguraciones() {
		testCase.addScreenShotCurrentStep(au, "testImage");
		Wait<AndroidDriver> wait = new FluentWait<>(au.driver).withTimeout(Duration.ofSeconds(3))
				.pollingEvery(Duration.ofMillis(50)).ignoring(org.openqa.selenium.NoSuchElementException.class);

		try {
			WebElement botonConfig = (WebElement) wait.until(d -> d.findElement(settings));
			botonConfig.click();
		} catch (Exception e) {
			System.out.println("No se encontró el botón Configuraciones");
		}
	}

	/**
	 * Método - Capturar la contraseña en el modulo inicial de Configuraciones
	 * 
	 * @author Marcos Morales
	 * @param pass Contraseña
	 * @date 15/12/2025
	 */
	public void ingresarPasswordSettings(String pass) {
		try {
			waitForVisibility(passSettings, Duration.ofSeconds(30));
			au.sendKeys(passSettings, pass);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Click al boton confirmar contraseña en el modulo de Configuraciones
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 15/12/2025
	 */
	public void clickBotonConfirmarSettings() {
		try {
			testCase.addScreenShotCurrentStep(au, "testImage");
			waitForVisibility(btnSettingsConfirmar, Duration.ofSeconds(30));
			au.click(btnSettingsConfirmar);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Valida el ingreso correcto al modulo de Configuraciones
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 15/12/2025
	 */
	public boolean validaModuloConfiguraciones() {
		try {
			waitForVisibility(titleSettings, Duration.ofSeconds(30));
			waitForVisibility(settingsPrinters, Duration.ofSeconds(30));
			testCase.addScreenShotCurrentStep(au, "testImage");
			return au.driver.findElement(settingsPrinters).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Método - Espera se oculte la ventana del Splash incial cuando carga la
	 * aplicacion
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 16/12/2025
	 */
	public void esperaSeOcultePantallaInicial() {
		try {
			waitForHidden(scrSplash, Duration.ofSeconds(30));
			waitForHidden(settings, Duration.ofSeconds(30));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Valida que el Journal se muestre en pantalla
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 16/12/2025
	 */
	public boolean validaJournalInicial() {
		try {
			waitForVisibility(Journal, Duration.ofSeconds(10));
			testCase.addScreenShotCurrentStep(au, "testImage");
			return au.driver.findElement(Journal).isDisplayed();
		} catch (Exception e) {
			System.out.println("No se mostró el Journal");
			return false;
		}
	}

	/**
	 * Método - Ingresar al apartado de Servicios
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 16/12/2025
	 */
	public void clickOpcionServicios() {
		try {
			waitForVisibility(btnServicios, Duration.ofSeconds(30));
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.click(btnServicios);
			esperaSeOculteLoading();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Capturar texto en el campo Buscador
	 * 
	 * @author Marcos Morales
	 * @param texto Cadena de texto capturada
	 * @date 16/12/2025
	 */
	public void ingresarTextoEnBuscador(String texto) {
		try {
			waitForVisibility(Buscador, Duration.ofSeconds(30));
			au.sendKeys(Buscador, texto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Hacer clic en el campo Buscador
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 16/12/2025
	 */
	public void clickEnCampoBuscador() {
		try {
			au.click(Buscador);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Hacer clic en el icono de busqueda del teclado
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 16/12/2025
	 */
	public void clickBuscarConTeclado() {
		try {
			au.driver.executeScript("mobile:performEditorAction", ImmutableMap.of("action", "search"));
			au.driver.hideKeyboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Hace un scroll vertical hasta encontrar el texto especificado
	 * despues de realizar una busqueda
	 * 
	 * @author Marcos Morales
	 * @param texto Cadena de texto a identificar en el scroll
	 * @date 16/12/2025
	 */
	public void scrollIntoViewServiceSearch(String texto) {
		try {
			au.driver.findElement(AppiumBy.androidUIAutomator(
					"new UiScrollable(new UiSelector().resourceId(\"com.femco.oxxo.operaciones.posmovil.qa:id/rvServiceSearch\"))"
							+ ".scrollIntoView(new UiSelector().text(\"" + texto + "\"))"));
		} catch (Exception e) {
			throw new NoSuchElementException("Elemento no encontrado después de scroll: " + texto);
		}
	}

	/**
	 * Método - Seleccionar Servicio por Scroll despues de realizar una busqueda
	 * 
	 * @author Marcos Morales
	 * @param texto Servicio a seleccionar
	 * @date 16/12/2025
	 */
	public void seleccionarResultadoBusquedaServicioPorScroll(String texto) {
		By locator = By.xpath(
				"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.posmovil.qa:id/txtTitle\" and @text=\""
						+ texto + "\"]");
		try {
			// Identificar si ya se muestra en pantalla
			if (isElementPresent(locator)) {
				au.click(locator);
			} else {
				// Caso contrario, aplicar scroll hasta encontrarlo
				scrollIntoViewServiceSearch(texto);
				au.click(locator);
			}
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("No se encontró el servicio: " + texto + "\n" + e.getMessage());
			throw new RuntimeException("Error en seleccionarResultadoBusquedaServicioPorScroll", e);
		}
	}

	/**
	 * Método - Captura la referencia de un servicio
	 * 
	 * @author Marcos Morales
	 * @param referencia Referencia del servicio
	 * @date 16/12/2025
	 */
	public void capturarReferencia(String referencia) {
		try {
			waitForVisibility(EdTeServicios, Duration.ofSeconds(30));
			au.sendKeys(EdTeServicios, referencia);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Clic en el boton Consultar Servicio
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 16/12/2025
	 */
	public void clickBtnConsultarServicio() {
		try {
			waitForVisibility(btn_ConsCliente, Duration.ofSeconds(30));
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.click(btn_ConsCliente);
			esperaSeOculteLoading();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Obtiene el monto de un servicio
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @return Retorna como cadena el monto de un servicio
	 * @date 16/12/2025
	 */
	public String obtieneMontoServicio() {
		try {
			waitForVisibility(montoServicio, Duration.ofSeconds(30));
			return au.driver.findElement(montoServicio).getText();
		} catch (Exception e) {
			System.err.println("Error al obtener el monto del servicio");
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Método - Obtiene el monto a pagar de un servicio
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @return Retorna como cadena el monto a pagar de un servicio
	 * @date 16/12/2025
	 */
	public String obtieneMontoPagarServicio() {
		try {
			waitForVisibility(montoPagarServicio, Duration.ofSeconds(30));
			return au.driver.findElement(montoPagarServicio).getText();
		} catch (Exception e) {
			System.err.println("Error al obtener el monto del pago del servicio");
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Método - Obtiene la comision del servicio
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @return Retorna como cadena la comision de un servicio
	 * @date 16/12/2025
	 */
	public String obtieneComisionServicio() {
		try {
			waitForVisibility(comisionServicio, Duration.ofSeconds(30));
			return au.driver.findElement(comisionServicio).getText();
		} catch (Exception e) {
			System.err.println("Error al obtener el monto de la Comision del servicio");
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Método - Obtiene el monto total de un servicio
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @return Retorna como cadena el monto total de un servicio
	 * @date 16/12/2025
	 */
	public String obtieneMontoTotalServicio() {
		try {
			waitForVisibility(montoTotalServicio, Duration.ofSeconds(30));
			return au.driver.findElement(montoTotalServicio).getText();
		} catch (Exception e) {
			System.err.println("Error al obtener el monto total del servicio");
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Método - Valida que los importes de un servicio se muestren en pantalla
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 16/12/2025
	 */
	public Boolean validaImportesDeUnServicio() {
		try {
			String monto = obtieneMontoServicio();
			String montoPagar = obtieneMontoPagarServicio();
			String comision = obtieneComisionServicio();
			String montoTotal = obtieneMontoTotalServicio();

			if (monto != "" && montoPagar != "" && comision != "" && montoTotal != "") {
				testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Clic en el boton Pagar Servicio
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 16/12/2025
	 */
	public void clickBotonPagarServicio() {
		try {
			waitForVisibility(btnPagarServicio, Duration.ofSeconds(30));
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.click(btnPagarServicio);
			waitForVisibility(btnConfirmar, Duration.ofSeconds(30));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Click al boton Si, Continuar
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 16/12/2025
	 */
	public void clickBotonSiContinuar() {
		try {
			waitForVisibility(btnConfirmar, Duration.ofSeconds(30));
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.click(btnConfirmar);
			esperaSeOculteLoading();
			waitForVisibility(btn_calcular, Duration.ofSeconds(30));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Clic al boton Calcular
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 16/12/2025
	 */
	public void clickBtnCalcular() {
		try {
			waitForVisibility(btn_calcular, Duration.ofSeconds(30));
			au.click(btn_calcular);
			esperaSeOculteLoading();
			waitForVisibility(importeTotal, Duration.ofSeconds(30));
			waitForVisibility(btn_total, Duration.ofSeconds(30));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Clic en el boton Total a pagar
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 16/12/2025
	 */
	public void clickBtnTotalPagar() {
		try {
			waitForVisibility(btn_total, Duration.ofSeconds(30));
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.click(btn_total);
			esperaSeOculteLoading();
			waitForVisibility(btn_efectivo, Duration.ofSeconds(30));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Clic forma de pago Efectivo
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 16/12/2025
	 */
	public void clickFormaDePagoEfectivo() {
		try {
			waitForVisibility(btn_efectivo, Duration.ofSeconds(30));
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.click(btn_efectivo);
			waitForVisibility(btn_aplicar, Duration.ofSeconds(30));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método - Aplicar(confirmar) forma de pago Efectivo
	 * 
	 * @author Marcos Morales
	 * @param N/A
	 * @date 16/12/2025
	 */
	public void confirmarPagoEfectivo() {
		try {
			waitForVisibility(btn_aplicar, Duration.ofSeconds(30));
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.click(btn_aplicar);
			esperaSeOculteLoading();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Obtiene informacion de 1 producto de forma aleatoria en la BD Local del PV
	 * 
	 * @author Marcos Morales
	 * @param ipPV La ip de la maquina que se esta utilizando
	 * @return Retorna la informacion de 1 producto de forma aleatoria
	 * @date 22/12/2025
	 */
	public static ResultSet obtieneUnSkuBDLocal(String ipPV) {

		ResultSet resultSet = null;
		try {
			Connection connection = DriverManager.getConnection(
					"jdbc:sqlserver://" + ipPV + ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV, GlobalVariables.passPV);
			;

			Statement statement = connection.createStatement();

			String query = ";with skusactivos as (select distinct Item_Id as Id\r\n"
					+ "					,[Name] as ProductName\r\n"
					+ "					,[Description] as ProductDescription\r\n" + "					,Sku\r\n"
					+ "					,SalePrice = CAST(coalesce(Amount, 0) as numeric(16, 2))\r\n"
					+ "				from XposStore.dbo.ProductCacheDataVw\r\n"
					+ "				where Item_Status_Name = 'ACTIVE_ITEM_STATUS'\r\n"
					+ "				and Item_Type_Value = 'PRODUCT_ITEM'\r\n"
					+ "				and Product_Type_Code_Name = 'MREGUL_PRODUCT' and Amount >= 2\r\n"
					+ "				and CatalogId not in (1526, 5280, 5544, 6080))\r\n"
					+ ",skusaleatorios as (select Id\r\n" + "					,ProductName\r\n"
					+ "					,ProductDescription\r\n" + "					,Sku\r\n"
					+ "					,SalePrice\r\n"
					+ "					,ROW_NUMBER() OVER (ORDER BY NEWID()) AS RandomRow\r\n"
					+ "					,COUNT(*) OVER () AS TotalRows from skusactivos)\r\n"
					+ "select top 1 ProductName\r\n" + "		,ProductDescription\r\n" + "		,Sku\r\n"
					+ "		,SalePrice\r\n" + "from skusaleatorios where RandomRow <= TotalRows * 0.002\r\n"
					+ "and ProductName not like '%[?]%'\r\n" + "and ProductName not like '%[%]%'\r\n"
					+ "and ProductName not like '%[+]%'\r\n" + "and ProductName not like '%[áéíóúÁÉÍÓÚñÑ]%'\r\n"
					+ "and ProductDescription not like '%[?]%'\r\n" + "and ProductDescription not like '%[%]%'\r\n"
					+ "and ProductDescription not like '%[+]%'\r\n"
					+ "and ProductDescription not like '%[áéíóúÁÉÍÓÚñÑ]%'";

			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Método - Guarda nombre, sku y precio de un producto en un archivo de
	 * propiedades
	 * 
	 * @author Marcos Morales
	 * @param ipPV IP de punto de venta local
	 * @throws SQLException
	 * @date 22/12/2025
	 */
	public static void saveItemProperties(String ipPV) throws SQLException {
		Properties props = new Properties();
		ResultSet rs = obtieneUnSkuBDLocal(ipPV);

		if (rs.next()) {
			String nombre = rs.getString(1);
			String sku = rs.getString(3);
			String precio = rs.getString(4);
			props.setProperty("itemName", nombre);
			props.setProperty("itemSku", sku);
			props.setProperty("itemPrice", precio);
		} else {
			throw new RuntimeException(
					"No se encontraron registros al ejecutar la consulta del metodo: obtieneUnSkuBDLocal");
		}

		try (OutputStream output = new FileOutputStream("item.properties")) {
			props.store(output, "Item Data");
			System.out.println("Item guardado en: " + new File("item.properties").getAbsolutePath());
		} catch (IOException e) {
			System.err.println("Error guardando Item: " + e.getMessage());
		}
	}

	/**
	 * Método - Valida si el item es valido dentro del archivo de propiedades
	 * 
	 * @author Marcos Morales
	 * @return Retorna true/false
	 * @date 22/12/2025
	 */
	public static boolean isItemValid() {
		Properties props = new Properties();
		try (InputStream input = new FileInputStream("item.properties")) {
			props.load(input);
			String nombre = props.getProperty("itemName");
			String sku = props.getProperty("itemSku");
			String precio = props.getProperty("itemPrice");

			if (nombre == null || sku == null || precio == null) {
				return false;
			}
		} catch (IOException e) {
			System.out.println("✗ No se encontró archivo de Item");
			return false;
		}
		return true;
	}

	/**
	 * Método - Obtiene el nombre guardado desde el archivo de propiedades
	 * 
	 * @author Marcos Morales
	 * @return Retorna el nombre como cadena
	 * @date 22/12/2025
	 */
	public static String getItemName() {
		Properties props = new Properties();
		try (InputStream input = new FileInputStream("item.properties")) {
			props.load(input);
			return props.getProperty("itemName");
		} catch (IOException e) {
			System.err.println("Error leyendo nombre: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Método - Obtiene el sku guardado desde el archivo de propiedades
	 * 
	 * @author Marcos Morales
	 * @return Retorna el sku como cadena
	 * @date 22/12/2025
	 */
	public static String getItemSKU() {
		Properties props = new Properties();
		try (InputStream input = new FileInputStream("item.properties")) {
			props.load(input);
			return props.getProperty("itemSku");
		} catch (IOException e) {
			System.err.println("Error leyendo sku: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Método - Obtiene el precio guardado desde el archivo de propiedades
	 * 
	 * @author Marcos Morales
	 * @return Retorna el precio como cadena
	 * @date 22/12/2025
	 */
	public static String getItemPrice() {
		Properties props = new Properties();
		try (InputStream input = new FileInputStream("item.properties")) {
			props.load(input);
			return props.getProperty("itemPrice");
		} catch (IOException e) {
			System.err.println("Error leyendo precio: " + e.getMessage());
			return null;
		}
	}


	/**
	 * Metodo - Consulta bandera promociones 
	 * 
	 * @author Cesar Guel
	 * @return Retorna el valor de la bandera
	 * @param ipPV IP de punto de venta local
	 * @date 03/04/2026
	 */
	public String ConsultaBanderaP(String ipPv) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlserver://"+ipPv+";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV, GlobalVariables.passPV);
			String query = "SELECT SVAL.VALUE "
					+ "FROM SYSTEM_PARAM_CATALOG SCAT WITH(NOLOCK) "
					+ "INNER JOIN SYSTEM_PARAM_VALUE SVAL WITH(NOLOCK) "
					+ "ON SCAT.SYSTEM_PARAM_CATALOG_ID = SVAL.SYSTEM_PARAM_CATALOG_ID "
					+ "WHERE SCAT.CODE = 'PROMOTIONS_FLG' ";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			System.out.println(val);
			promFlag = val;
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			System.out.println("Error al consultar la bandera en BD");
			e.printStackTrace();
			promFlag = "";
		}

		return promFlag;
	}

	/**
	 * Metodo - Consulta bandera promociones
	 * 
	 * @author Cesar Guel
	 * @return Retorna el valor de la bandera
	 * @date 13/04/2026
	 */
	public String ConsultaBanderaPD() {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + GlobalVariables.ipPvD
							+ ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV, GlobalVariables.passPV);
			String query = "SELECT SVAL.VALUE " + "FROM SYSTEM_PARAM_CATALOG SCAT WITH(NOLOCK) "
					+ "INNER JOIN SYSTEM_PARAM_VALUE SVAL WITH(NOLOCK) "
					+ "ON SCAT.SYSTEM_PARAM_CATALOG_ID = SVAL.SYSTEM_PARAM_CATALOG_ID "
					+ "WHERE SCAT.CODE = 'PROMOTIONS_FLG' ";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			System.out.println(val);
			promFlag = val;
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			System.out.println("Error al consultar la bandera en BD");
			e.printStackTrace();
			promFlag = "";
		}

		return promFlag;
	}

	/**
	 * Metodo - Consulta bandera restricciones
	 * 
	 * @author Cesar Guel
	 * @return Retorna el valor de la bandera
	 * @param ipPV IP de punto de venta local
	 * @date 03/04/2026
	 */
	public String ConsultaBanderaR(String ipPv) {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + ipPv + ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV, GlobalVariables.passPV);
			String query = "SELECT SVAL.VALUE " + "FROM SYSTEM_PARAM_CATALOG SCAT WITH(NOLOCK) "
					+ "INNER JOIN SYSTEM_PARAM_VALUE SVAL WITH(NOLOCK) "
					+ "ON SCAT.SYSTEM_PARAM_CATALOG_ID = SVAL.SYSTEM_PARAM_CATALOG_ID "
					+ "WHERE SCAT.CODE = 'RESTRICTIONS_FLG' ";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			System.out.println(val);
			restFlag = val;
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			System.out.println("Error al consultar la bandera en BD");
			e.printStackTrace();
			restFlag = "";
		}

		return restFlag;
	}

	/**
	 * Metodo - Consulta bandera restricciones
	 * 
	 * @author Cesar Guel
	 * @return Retorna el valor de la bandera
	 * @date 13/04/2026
	 */
	public String ConsultaBanderaRD() {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + GlobalVariables.ipPvD
							+ ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV, GlobalVariables.passPV);
			String query = "SELECT SVAL.VALUE " + "FROM SYSTEM_PARAM_CATALOG SCAT WITH(NOLOCK) "
					+ "INNER JOIN SYSTEM_PARAM_VALUE SVAL WITH(NOLOCK) "
					+ "ON SCAT.SYSTEM_PARAM_CATALOG_ID = SVAL.SYSTEM_PARAM_CATALOG_ID "
					+ "WHERE SCAT.CODE = 'RESTRICTIONS_FLG' ";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			System.out.println(val);
			restFlag = val;
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			System.out.println("Error al consultar la bandera en BD");
			e.printStackTrace();
			restFlag = "";
		}

		return restFlag;
	}

	/**
	 * Metodo - Validar bandera promociones encendida
	 * 
	 * @author Cesar Guel
	 * @return Retorna Verdadero o Falso si la bandera esta encendida
	 * @param ipPV IP de punto de venta local
	 * @date 03/04/2026
	 */
	public boolean validarBanderaPromocionesEncendida(String ipPv) {
		try {
			String promotionFlag = ConsultaBanderaP(ipPv);

			if (promotionFlag.equals("TRUE")) {
				System.out.println("La bandera de promociones se encuentra encendida");
				return true;
			} else if (promotionFlag.equals("FALSE")) {
				System.out.println("La bandera de promociones se encuentra apagada");
				return false;

			} else {
				System.out.println("Hubo algun problema al consultar la bandera");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Metodo - Validar bandera promociones encendida IP dinamica
	 * 
	 * @author Cesar Guel
	 * @return Retorna Verdadero o Falso si la bandera esta encendida
	 * @date 13/04/2026
	 */
	public boolean validarBanderaPromocionesEncendidaD() {
		try {
			String promotionFlag = ConsultaBanderaPD();

			if (promotionFlag.equals("TRUE")) {
				System.out.println("La bandera de promociones se encuentra encendida");
				return true;
			} else if (promotionFlag.equals("FALSE")) {
				System.out.println("La bandera de promociones se encuentra apagada");
				return false;

			} else {
				System.out.println("Hubo algun problema al consultar la bandera");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Metodo - Validar bandera restricciones encendida
	 * 
	 * @author Cesar Guel
	 * @return Retorna Verdadero o Falso si la bandera esta encendida
	 * @param ipPV IP de punto de venta local
	 * @date 03/04/2026
	 */
	public boolean validarBanderaRestriccionesEncendida(String ipPv) {
		try {
			String restrictionFlag = ConsultaBanderaR(ipPv);

			if (restrictionFlag.equals("TRUE")) {
				System.out.println("La bandera de restricciones se encuentra encendida");
				return true;
			} else if (restrictionFlag.equals("FALSE")) {
				System.out.println("La bandera de restricciones se encuentra apagada");
				return false;

			} else {
				System.out.println("Hubo algun problema al consultar la bandera");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Metodo - Validar bandera restricciones encendida con datos dinamicos
	 * 
	 * @author Cesar Guel
	 * @return Retorna Verdadero o Falso si la bandera esta encendida
	 * @date 13/04/2026
	 */
	public boolean validarBanderaRestriccionesEncendidaD() {
		try {
			String restrictionFlag = ConsultaBanderaRD();

			if (restrictionFlag.equals("TRUE")) {
				System.out.println("La bandera de restricciones se encuentra encendida");
				return true;
			} else if (restrictionFlag.equals("FALSE")) {
				System.out.println("La bandera de restricciones se encuentra apagada");
				return false;

			} else {
				System.out.println("Hubo algun problema al consultar la bandera");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Validar banderas promociones y restricciones con IP dinamica
	 * 
	 * @author Cesar Guel
	 * @return True o False si las banderas vienen apagadas
	 * @date 15/04/2026
	 */
	public boolean validarBanderasApagadasD() {
		try {
			String restrictionFlag = ConsultaBanderaRD();
			String promotionFlag = ConsultaBanderaPD();

			if (restrictionFlag.equals("FALSE") && promotionFlag.equals("FALSE")) {
				System.out.println("La bandera de restricciones se encuentra encendida y la de promociones apagada");
				return true;
			} else if (restrictionFlag.equals("TRUE") && promotionFlag.equals("FALSE")) {
				System.out.println("La bandera de restricciones se encuentra encendida y la de promociones apagada");
				return false;
			} else if (restrictionFlag.equals("FALSE") && promotionFlag.equals("TRUE")) {
				System.out.println("La bandera de restricciones se encuentra apagada y la de promociones encendida");
				return false;
			} else if (restrictionFlag.equals("TRUE") && promotionFlag.equals("TRUE")) {
				System.out.println("La banderas de restricciones se encuentra encendida y la de promociones encendida");
				return false;
			} else {
				System.out.println("Hubo algun problema al consultar la bandera");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método - Consulta fecha de modificación SP GET_PROMOTIONS_INFO
	 * 
	 * @author Cesar Guel
	 * @return La fecha de modificación del SP de promociones
	 * @param ipPV IP de punto de venta local
	 * @date 08/04/2026
	 */
	public String consultaFechaModSP(String ipPv) {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + ipPv
							+ ";databaseName=XposManagementCloud;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV, GlobalVariables.passPV);
			String query = "SELECT " + "modify_date FROM sys.objects "
					+ "WHERE type = 'P' AND name = 'GET_PROMOTIONS_INFO' ";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			System.out.println("La fehc a de modificacion del SP es: " + val);
			spDate = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al realizar la consulta de la fecha en BD");
			spDate = "";
		}

		return spDate;
	}

	/**
	 * Método - Consulta fecha de modificación SP GET_PROMOTIONS_INFO
	 * 
	 * @author Cesar Guel
	 * @return La fecha de modificación del SP de promociones
	 * @date 13/04/2026
	 */
	public String consultaFechaModSPD() {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + GlobalVariables.ipPvD
							+ ";databaseName=XposManagementCloud;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV, GlobalVariables.passPV);
			String query = "SELECT " + "modify_date FROM sys.objects "
					+ "WHERE type = 'P' AND name = 'GET_PROMOTIONS_INFO' ";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			System.out.println("La fehc a de modificacion del SP es: " + val);
			spDate = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al realizar la consulta de la fecha en BD");
			spDate = "";
		}

		return spDate;
	}

	/**
	 * Método - Consulta fecha de modificación/Instalacion CloudMiddModule
	 * 
	 * @author Cesar Guel
	 * @return La fecha de instalacion del parche Middleware
	 * @param ipPV IP de punto de venta local
	 * @date 08/04/2026
	 */
	public String consultaFechaPatCloudMid(String ipPv) {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + ipPv + ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV, GlobalVariables.passPV);
			String query = "select MODIFIED_DATETIME FROM [XposStore].[dbo].[RELEASE_VERSION_CONTROL] "
					+ "WHERE DESCRIPTION LIKE '%Cloud%' and VERSION_CODE = '2.0.9.4' ";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			System.out.println("La fehca de instalacion del parche es: " + val);
			patchDate = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al realizar la consulta de la fecha en BD");
			patchDate = "";
		}

		return patchDate;
	}

	/**
	 * Método - Consulta fecha de modificación/Instalacion CloudMiddModule valores
	 * dinamicos
	 * 
	 * @author Cesar Guel
	 * @return La fecha de instalacion del parche Middleware
	 * @date 13/04/2026
	 */
	public String consultaFechaPatCloudMidD() {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + GlobalVariables.ipPvD
							+ ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV, GlobalVariables.passPV);
			String query = "select MODIFIED_DATETIME FROM [XposStore].[dbo].[RELEASE_VERSION_CONTROL] "
					+ "WHERE DESCRIPTION LIKE '%Cloud%' and VERSION_CODE = '2.0.9.4' ";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			System.out.println("La fehca de instalacion del parche es: " + val);
			patchDate = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al realizar la consulta de la fecha en BD");
			patchDate = "";
		}

		return patchDate;
	}

	/**
	 * Metodo - Validar fecha de modificacion del SP de promociones
	 * 
	 * @author Cesar Guel
	 * @return Retorna Verdadero o Falso si la fecha es igual o mayor
	 * @param ipPV IP de punto de venta local
	 * @date 08/04/2026
	 */
	public boolean validarFechaModSP(String ipPv) {
		try {
			String SP = consultaFechaModSP(ipPv);
			String PATCH = consultaFechaPatCloudMid(ipPv);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss[.SSS][.SS][.S]");

			LocalDateTime SPDATE = LocalDateTime.parse(SP, formatter);
			LocalDateTime PATCHDATE = LocalDateTime.parse(PATCH, formatter);

			if (!SPDATE.isBefore(PATCHDATE)) {
				System.out.println("La fecha de modificacion es la esperada");
				return true;
			} else {
				System.out.println("La fecha de la modificacion no fue la esperada");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Hubo un error al realizar la validacion");
			return false;
		}

	}

	/**
	 * Meto para obtener el dato de la tienda 50DHA enviado en el partametro de
	 * crTienda Ejemplo, se recibe 10MON50DHA y se regresa solo el 50DHA
	 * 
	 */
	public static String obtenerCRTienda(String crStore) throws Exception {
//		if (crStore == null) return null;
//		return crStore.replaceFirst("^\\d+[A-Z]+", "");

		if (crStore == null) {
			System.out.println("El texto es null");
			return null;
		}

		Matcher m = Pattern.compile("(\\d+[A-Z]+)$").matcher(crStore);

		if (m.find()) {
			String resultado = m.group(1);
			System.out.println("Resultado obtenido en el metodo obtenerCRTienda: " + resultado);
			return resultado;
		} else {
			System.out.println("No se encontró un formato válido en: " + crStore);
			return null;
		}

	}

	/**
	 * Metodo - Validar fecha de modificacion del SP de promociones valores
	 * dinamicos
	 * 
	 * @author Cesar Guel
	 * @return Retorna Verdadero o Falso si la fecha es igual o mayor
	 * @date 13/04/2026
	 */
	public boolean validarFechaModSPD() {
		try {
			String SP = consultaFechaModSPD();
			String PATCH = consultaFechaPatCloudMidD();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss[.SSS][.SS][.S]");

			LocalDateTime SPDATE = LocalDateTime.parse(SP, formatter);
			LocalDateTime PATCHDATE = LocalDateTime.parse(PATCH, formatter);

			if (PATCHDATE.isBefore(SPDATE)) {
				System.out.println("La fecha de modificacion es la esperada");
				return true;
			} else {
				System.out.println("La fecha de la modificacion no fue la esperada");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Hubo un error al realizar la validacion");
			return false;
		}

	}

	/**
	 * Método - Tomar dato crTienda desde YAML o dataProvider
	 * 
	 * @author Cesar Guel
	 * @param crTiendaDataProvider el cr de tienda tomado de dataProvider
	 * @return crTiendaPipeline retorna el cr de la tienda tomado desde pipeline
	 * @date 13/04/2026
	 */
	public static String getCrTienda(String crTienda) {

		// Intentar obtener desde pipeline
		String crTiendaPipeline = System.getProperty("crTiendaD");

		if (crTiendaPipeline != null && !crTiendaPipeline.isEmpty()) {
			System.out.println("CR Tienda desde Pipeline: " + crTiendaPipeline);
			return crTiendaPipeline;
		}

		// Si no existe, usar DataProvider
		if (crTienda != null && !crTienda.isEmpty()) {
			System.out.println("CR Tienda desde DataProvider: " + crTienda);
			return crTienda;
		}

		throw new RuntimeException("No se encontró crTienda en Pipeline ni DataProvider");
	}

	/**
	 * Método - Tomar dato ip desde YAML o dataProvider
	 * 
	 * @author Cesar Guel
	 * @param ipPv la direccion IP del PDV tomada de YAML o dataProvider
	 * @return IpPipeline retorna la IP de PDV tomado de Pipeline
	 * @return ipPv retorna la IP de PDV tomado desde dataProvider
	 * @date 13/04/2026
	 */
	public static String getIpPvD(String ipPv) {

		// Intentar obtener desde pipeline
		String IpPipeline = System.getProperty("ipPvD");

		if (IpPipeline != null && !IpPipeline.isEmpty()) {
			System.out.println("ip de PDV desde Pipeline: " + IpPipeline);
			return IpPipeline;
		}

		// Si no existe, usar DataProvider
		if (ipPv != null && !ipPv.isEmpty()) {
			System.out.println("ip de PDV desde DataProvider: " + ipPv);
			return ipPv;
		}

		throw new RuntimeException("No se encontró ip de PDV en Pipeline ni DataProvider");
	}

	/**
	 * Método - Tomar dato ip desde YAML o dataProvider
	 * 
	 * @author Cesar Guel
	 * @param nomTienda el nombre de la tienda tomada de YAML o dataProvider
	 * @return crIpPipeline retorna la IP de PDV tomado de Pipeline
	 * @return ipPv retorna la IP de PDV tomado desde dataProvider
	 * @date 13/04/2026
	 */
	public static String getNomTiendaD(String nomTienda) {

		// Intentar obtener desde pipeline
		String NomTiendaD = System.getProperty("nomTiendaD");

		if (NomTiendaD != null && !NomTiendaD.isEmpty()) {
			System.out.println("Nombre de la tienda desde Pipeline: " + NomTiendaD);
			return NomTiendaD;
		}

		// Si no existe, usar DataProvider
		if (nomTienda != null && !nomTienda.isEmpty()) {
			System.out.println("Nombre de la tienda desde DataProvider: " + nomTienda);
			return nomTienda;
		}

		throw new RuntimeException("No se encontró el nombre de la tienda en Pipeline ni DataProvider");
	}
	
	/**
	 * Método - Consulta Articulo
	 * 
	 * @author Cesar Guel
	 * @return El valor de la URL del ambiente de coalicion
	 * @date 06/09/2024
	 */
	public String consultaUrlCoalicion() {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + GlobalVariables.ipPvD + ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
					GlobalVariables.userPV, GlobalVariables.passPV);
			String query = "select VALUE from SYSTEM_PARAM_VALUE where SYSTEM_PARAM_CATALOG_ID = 648 ";
			SQLResult res = new SQLResult(conn, query);
			val = res.getFirstData().toString();
			System.out.println(val);
			urlCoal = val.substring(0);
			testCase.addQueryEvidenceCurrentStep(res);
		} catch (Exception e) {
			System.out.println("Error al consultar la URL en BD");
			urlCoal = "";
		}

		return urlCoal;
	}
	
	/**
	 * Metodo cambio de URL coalicion
	 * 
	 * @author Cesar Guel
	 * @param UrlCoal
	 */
	public void cambiarURL(String UrlCoal) {
	    Connection conn = null;
	    PreparedStatement ps = null;

	    try {
	        conn = DriverManager.getConnection(
	                "jdbc:sqlserver://" + GlobalVariables.ipPvD +
	                ";databaseName=XposStore;encrypt=true;trustServerCertificate=true",
	                GlobalVariables.userPV, GlobalVariables.passPV);

	        String queryUpdate = "UPDATE SYSTEM_PARAM_VALUE SET VALUE = ? WHERE SYSTEM_PARAM_CATALOG_ID = 648";

	        ps = conn.prepareStatement(queryUpdate);
	        ps.setString(1, UrlCoal);

	        int rows = ps.executeUpdate();

	        System.out.println("Registros actualizados: " + rows);
	        testCase.addTextEvidenceCurrentStep("URL actualizada a: " + UrlCoal);

	    } catch (Exception e) {
	        System.out.println("Error al actualizar la URL en BD");
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Metodo - Validar URL Coalicion
	 * 
	 * @author Cesar Guel
	 * @date 04/05/2026
	 */
	public void cambioUrlCoal(String UrlCoal) {
		try {
			String ConUrl = consultaUrlCoalicion();
			if(ConUrl.equals(UrlCoal)) {
				System.out.println("La URL" + ConUrl +"es la esperada");
				TimeUnit.SECONDS.sleep(5);
			}else {
				cambiarURL(UrlCoal);
				System.out.println("El valor de la URL modificada es: " + ConUrl);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	

}
