package Mobile.cedis.om;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Mobile.util.GlobalVariables;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import modelo.BaseExecution;
import modelo.TestCase;
import oracle.jdbc.driver.SQLUtil;
import utils.AppiumUtil;
import utils.sql.SQLResult;

public class B05_DevolucionMercancia {
	private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;

	// Sección Encabezado
	private By CEDIS_TituloPantalla = By.xpath("//android.widget.TextView[@text=\"DEVOLUCIÓN A CEDIS\"]");
	private By CEDIS_Fecha = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvDateText\" and @text=\"Fecha: \"]");
	private By CEDIS_ValorFecha = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleDate\"]");
	private By CEDIS_Ruta = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleRute\"]");
	private By CEDIS_RutaDinamica = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleRuteD\"]");
	private By CEDIS_CRTienda = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleStore\"]");
	private By CEDIS_NombreTienda = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleNameStore\"]");
	private By CEDIS_Lider = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleLeaderStore\"]");
	private By CEDIS_Transferencia = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTransferNumber\"]");

	// Sección Devolucion Mercancia
	private By CEDIS_LeyendaDevolucionMercancia = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleReturned\" and @text=\"DEVOLUCIÓN DE MERCANCÍA\"]");
	private By CEDIS_Btn_SeleccionarBol = By.xpath(
			"//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnSelectBol\" and @text=\"SELECCIONAR BOL\"]");
	// Para la seleccion de un bol en especifo se puede usar la función de
	// SeleccionarBol
	private By CEDIS_AceptarBol = By
			.xpath("//android.widget.Button[@resource-id=\"android:id/button1\" and @text=\"ACEPTAR\"]");
	// Para la seleccion de un bol agregado en especifo se puede usar la función de
	//Indicador del botón ACEPTAR al seleccionar BOL por medio de ID
	private By CEDIS_NvoAceptarBol = By.id("android:id/button1");
	
	// ValidarBolAgregado

	// Sección Canastillas
	private By CEDIS_LeyendaDevolucionContenedor = By
			.xpath("//android.widget.TextView[@text=\"DEVOLUCIÓN DE CONTENEDORES\"]");
	private By CEDIS_LeyendaCanastillas = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleCanas\" and @text=\"CANASTILLAS\"]");
	private By CEDIS_CampoIngresarCantidadCanastilla = By
			.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/input\"]");
	private By CEDIS_CanastillaAzul = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/title_item_sku_azul\" and @text=\"AZUL\"]");
	private By CEDIS_MenosAzul = By.xpath(
			"//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnMenosAzul\"]");
	private By CEDIS_ValorAzul = By
			.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtAzul\"]");
	private By CEDIS_MasAzul = By.xpath(
			"//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnMasAzul\"]");

	private By CEDIS_CanastillaAmarilla = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/title_item_sku_ama\" and @text=\"AMARILLA\"]");
	private By CEDIS_MenosAmarilla = By.xpath(
			"//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnMenosAmarilla\"]");
	private By CEDIS_ValorAmarilla = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtAmarilla\"]");
	private By CEDIS_MasAmarilla = By.xpath(
			"//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnMasAmarilla\"]");

	private By CEDIS_CanastillaVerde = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/title_item_sku_ver\" and @text=\"VERDE\"]");
	private By CEDIS_MenosVerde = By.xpath(
			"//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnMenosVerde\"]");
	private By CEDIS_ValorVerde = By
			.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtVerde\"]");
	private By CEDIS_MasVerde = By.xpath(
			"//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnMasVerde\"]");

	private By CEDIS_CanastillaNaranja = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/title_item_sku_na\" and @text=\"NARANJA\"]");
	private By CEDIS_MenosNaranja = By.xpath(
			"//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnMenosNaranja\"]");
	private By CEDIS_ValorNaranja = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtNaranja\"]");
	private By CEDIS_MasNaranja = By.xpath(
			"//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnMasNaranja\"]");

	private By CEDIS_CanastillaOtro = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/title_item_sku_otro\" and @text=\"OTRO\"]");
	private By CEDIS_MenosOtro = By.xpath(
			"//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnMenosOtro\"]");
	private By CEDIS_ValorOtro = By
			.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtOtro\"]");
	private By CEDIS_MasOtro = By.xpath(
			"//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnMasOtro\"]");

	// Sección Contenedores
	private By CEDIS_LeyendaContenedores = By.xpath("//android.widget.TextView[@text=\"CONTENEDORES\"]");
	private By CEDIS_LeyendaTapas = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleCaps\" and @text=\"TAPAS DE CANASTILLAS\"]");
	private By CEDIS_MenosTapas = By.xpath(
			"//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnLessCaps\"]");
	private By CEDIS_ValorTapas = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/etCountCaps\"]");
	private By CEDIS_MasTapas = By.xpath(
			"//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnPlusCaps\"]");

	private By CEDIS_LeyendaEnvasesVacios = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleEmptyContainers\" and @text=\"ENVASES VACÍOS\"]");
	private By CEDIS_MenosEnvasesVacios = By.xpath(
			"//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnLessEmptyContainers\"]");
	private By CEDIS_ValorEnvasesVacios = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/etCountEmptyContainers\"]");
	private By CEDIS_MasEnvasesVacios = By.xpath(
			"//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnPlusEmptyContainers\"]");

	// Sección Foto Evidencia
	private By CEDIS_CampoEvidenciaPedido = By.xpath(
			"//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/etImageNamePhotoInclude\"]");
	private By CEDIS_FotoDevolucion = By.xpath(
			"//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ivCameraInclude\"]");
	private By CEDIS_TomarFotoDevolucion = By.xpath("//android.widget.ImageView[@content-desc=\"Obturador\"]");
	private By CEDIS_CancelarFotoDevolucion = By.xpath("//android.widget.ImageView[@content-desc=\"Cancelar\"]");
	private By CEDIS_MensajeCancelarFoto = By
			.xpath("//android.widget.Toast[@text=\"El usuario canceló la operación\"]");
	private By CEDIS_ConfirmarFotoDevolucion = By.xpath("//android.widget.ImageView[@content-desc=\"Listo\"]");
	private By CEDIS_NombreFotoDevolucion = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvNamePhoto\"]");
	private By CEDIS_EliminarFotoDevolucion = By
			.xpath("//android.widget.ImageView[@content-desc=\"ContentDescription\"]");
	private By CEDIS_MensajeUnaFoto = By.xpath(
			"//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"Solo se permite agregar máximo 1 foto\"]");
	private By CEDIS_AceptarMensajeUnaFoto = By
			.xpath("//android.widget.Button[@resource-id=\"android:id/button1\" and @text=\"ACEPTAR\"]");

	// Sección Canastilla con Devolución
	private By CEDIS_LeyendaCanastillaDevolucion = By
			.xpath("//android.widget.TextView[@text=\"CANASTILLAS CON DEVOLUCIÓN\"]");
	private By CEDIS_IngresarContenedorDevolucion = By.xpath(
			"//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/editTextNoScan\"]");
	private By CEDIS_ContenedorAgregado = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/title_item_description\"]");
	private By CEDIS_EliminarCodigo = By.xpath(
			"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/title_item_delete\" and @text=\" ELIMINAR CÓDIGO \"]");
	private By CEDIS_FotoContedor = By.xpath(
			"//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/imageButtonPhotoLayette\"]");
	private By CEDIS_TomarFotoContedor = By.xpath("//android.widget.ImageView[@content-desc=\"Obturador\"]");
	private By CEDIS_CancelarFotoContedor = By.xpath("//android.widget.ImageView[@content-desc=\"Cancelar\"]");
	private By CEDIS_ConfirmarFotoContedor = By.xpath("//android.widget.ImageView[@content-desc=\"Listo\"]");

	// Sección Continuar
	private By CEDIS_ContinuarDevolucion = By.xpath(
			"//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnFinishReturned\"]");

	// Sección de mensajes
	private By CEDIS_MensajeSeleccionarPrimeroBol = By.xpath(
			"//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"Es necesario primero seleccionar un BOL para registrar contenedores\"]");
	private By CEDIS_MensajeFotoCanastilla = By.xpath(
			"//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"No se cuenta con la fotografía de canastillas a devolver, favor de tomar la fotografía para continuar.\"]");
	private By CEDIS_MensajeFotoContenedor = By.xpath(
			"//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"No se cuenta con las fotografías de canastillas con devolución, favor de tomar la fotografía para continuar.\"]");
	private By CEDIS_MensajeCodigoPartePedido = By.xpath(
			"//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"El código escaneado es parte del pedido. No se permite registrarlo como Devolución de Mercancía.\"]");
	private By CEDIS_MensajeIndicarContenedor = By.xpath(
			"//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"Indica los contenedores a devolver antes de tomar la fotografía\"]");
	private By CEDIS_MensajeIndicarCanastilla = By.xpath(
			"//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"Es necesario indicar la cantidad de canastillas con devolución en el color de canastillas correspondiente\"]");
	private By CEDIS_MensajeContenedorNoValido = By.xpath(
			"//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"El código de contenedor ingresado no es válido\"]");
	private By CEDIS_AceptarMensaje = By
			.xpath("//android.widget.Button[@resource-id=\"android:id/button1\" and @text=\"ACEPTAR\"]");// usado para
																											// los
																											// mensajes
																											// mencionados
																											// previamente
	private By CEDIS_ConfirmarMensaje = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]");

	private By CEDIS_MensajeSeleccionarDevoluciones = By.xpath(
			"//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"Se seleccionaron devoluciones pero no se escanearon canastillas, ¿deseas continuar?\"]");
	private By CEDIS_SiMensajeSeleccionarDevoluciones = By
			.xpath("//android.widget.Button[@resource-id=\"android:id/button1\" and @text=\"SI\"]");
	private By CEDIS_NoMensajeSeleccionarDevoluciones = By
			.xpath("//android.widget.Button[@resource-id=\"android:id/button2\" and @text=\"NO\"]");

	private By CEDIS_MensajeSeleccionarBol = By.xpath(
			"//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"No ha seleccionado el o los BOL´s ¿Está seguro que desea continuar sin generar una devolución ?\"]");
	private By CEDIS_CancelarSeleccionarBol = By
			.xpath("//android.widget.Button[@resource-id=\"android:id/button2\" and @text=\"CANCELAR\"]");
	private By CEDIS_ConfirmarSeleccionarBol = By
			.xpath("//android.widget.Button[@resource-id=\"android:id/button1\" and @text=\"CONFIRMAR\"]");
	private By CEDIS_MensajeCanastillasConDevolucionSinFotografia = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\"]");
	
	public static ArrayList<String> listaBols = new ArrayList<>();
	private SQLUtil conn;

	/**
	 * 
	 * private By CEDIS_EliminarCodigo =
	 * By.xpath("com.femco.oxxo.operaciones.mitienda.debug:id/tvDeleteReturned","text:=ELIMINAR
	 * CÓDIGO"); private By CEDIS_LeyendaCodigo =
	 * By.xpath("com.femco.oxxo.operaciones.mitienda.debug:id/tvCodeReturned","text:=CÓDIGO.*");
	 * private By CEDIS_LeyendaTotalDevolucionMercancia =
	 * By.xpath("com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleTotal","text:=Total
	 * Devolución"); private By CEDIS_TotalDevolucionMercancia =
	 * By.xpath("com.femco.oxxo.operaciones.mitienda.debug:id/numTotalReturned","text:=.*");
	 * 
	 * 
	 * private By CEDIS_LeyendaTerminoDevolucion =
	 * By.xpath("resourceid:=android:id/message","text:=¿Está seguro que ha
	 * terminado de escanear los Contenedores de Mercancías a Devolver\?"); private
	 * By CEDIS_CancelarTerminoDevolucion =
	 * By.xpath("//android.widget.Button[@resource-id=\"android:id/button2\"]");,"text:=CANCELAR");
	 * private By CEDIS_ConfirmarTerminoDevolucion =
	 * By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]");,"text:=CONFIRMAR");
	 * 
	 * 
	 * /** Constructor
	 *****************************************************************************************
	 * Constructor de la clase B03_ResumenPedidosCEDIS
	 *
	 * @param au            (AppiumUtil): objeto de la clase
	 * @param testCase      (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 * @author Juan Ibarra
	 */

	public B05_DevolucionMercancia(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
		this.testCase = testCase;
		this.au = au;
		this.baseExecution = baseExecution;
	}

	public void ValidacionEncabezado() throws IOException {
		// Funcion que permite validar que se muestran los campo correspondientes al
		// encabezado de la pantalla Creado: Juan Ibarra
		if (au.driver.findElement(CEDIS_TituloPantalla).isEnabled() == false) {
			baseExecution.addStep("Se falla el caso");
			Assert.fail("No se ha encontrado el Titulo pantalla DEVOLUCIÓN A CEDIS en la parte de inicio", null);
		}
		if (au.driver.findElements(CEDIS_Fecha).isEmpty() != false) {
			baseExecution.addStep("Se falla el caso");
			Assert.fail(
					"No se ha encontrado el campo de Fecha de la pantalla de DEVOLUCIÓN A CEDIS en la parte de inicio",
					null);
		}
		if (au.driver.findElements(CEDIS_ValorFecha).isEmpty() != false) {
			baseExecution.addStep("Se falla el caso");
			Assert.fail(
					"No se ha encontrado el campo de Valor Fecha de la pantalla de DEVOLUCIÓN A CEDIS en la parte de inicio",
					null);
		}

		if (au.driver.findElements(CEDIS_Ruta).isEmpty() != false) {
			baseExecution.addStep("Se falla el caso");
			Assert.fail(
					"No se ha encontrado el campo de Ruta de la pantalla de DEVOLUCIÓN A CEDIS en la parte de inicio",
					null);
		}
		if (au.driver.findElements(CEDIS_RutaDinamica).isEmpty() != false) {
			baseExecution.addStep("Se falla el caso");
			Assert.fail(
					"No se ha encontrado el campo de Ruta Dinamica de la pantalla de DEVOLUCIÓN A CEDIS en la parte de inicio",
					null);
		}
		if (au.driver.findElements(CEDIS_CRTienda).isEmpty() != false) {
			baseExecution.addStep("Se falla el caso");
			Assert.fail(
					"No se ha encontrado el campo de CR Tienda de la pantalla de DEVOLUCIÓN A CEDIS en la parte de inicio",
					null);
		}
		if (au.driver.findElements(CEDIS_NombreTienda).isEmpty() != false) {
			baseExecution.addStep("Se falla el caso");
			Assert.fail(
					"No se ha encontrado el campo de Nombre Tienda de la pantalla de DEVOLUCIÓN A CEDIS en la parte de inicio",
					null);
		}
		if (au.driver.findElements(CEDIS_Lider).isEmpty() != false) {
			baseExecution.addStep("Se falla el caso");
			Assert.fail(
					"No se ha encontrado el campo de Lider de la pantalla de DEVOLUCIÓN A CEDIS en la parte de inicio",
					null);
		}
		if (au.driver.findElements(CEDIS_Transferencia).isEmpty() != false) {
			baseExecution.addStep("Se falla el caso");
			Assert.fail(
					"No se ha encontrado el campo de Transferencia de la pantalla de DEVOLUCIÓN A CEDIS en la parte de inicio",
					null);
		}
	}

	public void SeleccionarPrimerBol(String tomaEvidencia, String descPaso) throws IOException, InterruptedException {
		// Funcion para seleccionar el primer Bol mostrado en la pantalla Creado: Juan
		// Ibarra
		au.scrollToElement(CEDIS_Btn_SeleccionarBol);
		au.click(CEDIS_Btn_SeleccionarBol);
		By CEDIS_PrimerBol = By.xpath(
				"//android.widget.FrameLayout[@resource-id=\"android:id/custom\"]/android.widget.ScrollView/android.widget.LinearLayout/android.widget.RelativeLayout[1]/android.widget.CheckBox");
		if (tomaEvidencia.equals("tomaEvidencia")) {
			au.click(CEDIS_PrimerBol);
			baseExecution.addStep(descPaso);
			Thread.sleep(2000);
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.click(CEDIS_AceptarBol);
		} else {
			au.click(CEDIS_PrimerBol);
			au.click(CEDIS_AceptarBol);
		}
	}

	/**
	 * Método que selecciona el primer BOL sin marcar (verificando el atributo
	 * 'checked') y toma evidencia si se requiere. Creado: Marcelo Gtz Fecha
	 * creación: 31-01-2025
	 * 
	 * @param tomaEvidencia Indica si se debe tomar evidencia ("tomaEvidencia" para
	 *                      activar).
	 * @param descPaso      Descripción del paso que se registrará en la
	 *                      documentación.
	 * 
	 *                      Este método desplaza la vista hasta el botón para
	 *                      seleccionar un BOL, verifica el atributo 'checked' de
	 *                      cada checkbox dentro de los elementos RelativeLayout, y
	 *                      selecciona el primero que esté marcado como 'false'. Si
	 *                      se activa la toma de evidencia, registra el paso y toma
	 *                      un screenshot antes de confirmar la selección del BOL.
	 */
	public void SeleccionarBOLSinCheck(String tomaEvidencia, String descPaso) {
		try {
			// Desplazarse hasta el botón y hacer clic para abrir la lista de BOLs
			au.scrollToElement(CEDIS_Btn_SeleccionarBol);
			Thread.sleep(5000);
			au.click(CEDIS_Btn_SeleccionarBol);
			Thread.sleep(2000);

			// Localizar el contenedor padre que agrupa los RelativeLayouts
			String parentXPath = "//android.widget.FrameLayout[@resource-id=\"android:id/custom\"]/android.widget.ScrollView/android.widget.LinearLayout";
			WebElement parentElement = au.driver.findElement(By.xpath(parentXPath));

			// Obtener la lista de elementos RelativeLayout dentro del contenedor
			List<WebElement> relativeLayouts = parentElement.findElements(By.xpath(".//android.widget.RelativeLayout"));

			// Variable para saber si ya se seleccionó un CheckBox
			boolean checkBoxSeleccionado = false;

			// Iterar sobre cada RelativeLayout para buscar un CheckBox sin marcar
			for (WebElement relativeLayout : relativeLayouts) {
				try {
					// Obtener el CheckBox dentro del RelativeLayout
					WebElement checkBox = relativeLayout.findElement(By.xpath(".//android.widget.CheckBox"));

					// Verificar el atributo 'checked' del CheckBox
					String checkedValue = checkBox.getAttribute("checked");

					// Si el CheckBox tiene 'checked' en "false", proceder a seleccionarlo
					if (checkedValue == null || checkedValue.equals("false")) {
						checkBox.click();
						checkBoxSeleccionado = true;

						// Si se requiere tomar evidencia, se ejecuta la lógica correspondiente
						if ("tomaEvidencia".equals(tomaEvidencia)) {
							baseExecution.addStep(descPaso); // Registrar paso
							Thread.sleep(2000);
							testCase.addScreenShotCurrentStep(au, "testImage"); // Tomar screenshot
						}

						// Confirmar la selección del BOL
						au.click(CEDIS_AceptarBol);
						break; // Salir del bucle después de seleccionar el primer CheckBox sin marcar
					}
				} catch (NoSuchElementException e) {
					// Si no se encuentra un CheckBox dentro del RelativeLayout, continuar con el
					// siguiente
					continue;
				}
			}

			// Si no se encontró ningún CheckBox sin marcar
			if (!checkBoxSeleccionado) {
				System.out.println("✅ Todos los CheckBox ya estaban seleccionados. No se hizo ninguna acción.");
				au.click(CEDIS_AceptarBol);
			}

		} catch (NoSuchElementException e) {
			System.out.println("❌ Error: No se encontró el LinearLayout. Verifica el XPath.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("❌ Error inesperado.");
			e.printStackTrace();
		}
	}

	public void SeleccionarBol(String Bol, String tomaEvidencia, String descPaso)
			throws IOException, InterruptedException {
		// Funcion para seleccionar el check del bol especifico en el cual se define si
		// va a tomar evidencia o no Creado: Juan Ibarra
		au.scrollToElement(CEDIS_Btn_SeleccionarBol);
		au.click(CEDIS_Btn_SeleccionarBol);
		Thread.sleep(1000);
		By CEDIS_BolSeleccionar = By.xpath("//android.widget.TextView[@text=\"" + Bol + "\"]");
		au.scrollToElement(CEDIS_BolSeleccionar);
		Thread.sleep(1000);
		WebElement elemento = au.driver.findElement(CEDIS_BolSeleccionar);
		@SuppressWarnings("deprecation")
		String posicionobjeto = elemento.getAttribute("bounds");

		String x1Bulto = posicionobjeto.substring(1, 4); // obtiene la posición X1 del bol
		String y1Bulto = posicionobjeto.substring(5, 8); // obtiene la posición y1 del bol
		String x2Bulto = posicionobjeto.substring(10, 13); // obtiene la posición X2 del bol
		String y2Bulto = posicionobjeto.substring(14, 17); // obtiene la posición y2 del bol

		int x1Check = Integer.parseInt(x1Bulto) + 436;// valor que corresponde a la posición X1 del check del bol a
														// seleccionar
		int y1Check = Integer.parseInt(y1Bulto) - 13;// valor que corresponde a la posición y2 del check del bol a
														// seleccionar
		int x2Check = Integer.parseInt(x2Bulto) + 420;// valor que corresponde a la posición X2 del check del bol a
														// seleccionar
		int y2Check = Integer.parseInt(y2Bulto) + 13;// valor que corresponde a la posiciónyX2 del check del bol a
														// seleccionar

		By CEDIS_CheckSeleccionar = By.xpath("//android.widget.CheckBox[@bounds=\"[" + x1Check + "," + y1Check + "]["
				+ x2Check + "," + y2Check + "]\"]");
		if (tomaEvidencia.equals("tomaEvidencia")) {
			au.click(CEDIS_CheckSeleccionar);
			baseExecution.addStep(descPaso);
			Thread.sleep(2000);
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.click(CEDIS_AceptarBol);
		} else {
			Thread.sleep(1000);
			au.click(CEDIS_CheckSeleccionar);
			Thread.sleep(1000);
			au.click(CEDIS_AceptarBol);
			Thread.sleep(1000);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void NuevoSeleccionarBol(String Bol)
			throws IOException, InterruptedException {
		// Replica de funcion para seleccionar el check del bol especifico en el cual se define si
		// va a tomar evidencia o no (sin instrucción de click boton Seleccionar boton 
		//Creado por: Jorge Lugo 13/02/25
		By CEDIS_BolSeleccionar = By.xpath("//android.widget.TextView[@text=\"" + Bol + "\"]");
		au.scrollToElement(CEDIS_BolSeleccionar);
		Thread.sleep(1000);
		WebElement elemento = au.driver.findElement(CEDIS_BolSeleccionar);
		String posicionobjeto = elemento.getAttribute("bounds");

		String x1Bulto = posicionobjeto.substring(1, 4); // obtiene la posición X1 del bol
		String y1Bulto = posicionobjeto.substring(5, 8); // obtiene la posición y1 del bol
		String x2Bulto = posicionobjeto.substring(10, 13); // obtiene la posición X2 del bol
		String y2Bulto = posicionobjeto.substring(14, 17); // obtiene la posición y2 del bol

		int x1Check = Integer.parseInt(x1Bulto) + 436;// valor que corresponde a la posición X1 del check del bol a
														// seleccionar
		int y1Check = Integer.parseInt(y1Bulto) - 13;// valor que corresponde a la posición y2 del check del bol a
														// seleccionar
		int x2Check = Integer.parseInt(x2Bulto) + 436;// valor que corresponde a la posición X2 del check del bol a
														// seleccionar
		int y2Check = Integer.parseInt(y2Bulto) + 13;// valor que corresponde a la posiciónyX2 del check del bol a
														// seleccionar
		By CEDIS_CheckSeleccionar = By.xpath("//android.widget.CheckBox[@bounds=\"["+ x1Check + "," + y1Check + "]["
				+ x2Check + "," + y2Check + "]\"]");
		System.out.println(x1Check);
		System.out.println(y1Check);
		System.out.println(x2Check);
		System.out.println(y2Check);
		au.click(CEDIS_CheckSeleccionar);
		Thread.sleep(3000);	
	}
	
	@SuppressWarnings("deprecation")
	public void NuevoSeleccionarBolTABLET(String Bol)
			throws IOException, InterruptedException {
		// Replica de funcion para seleccionar el check del bol especifico en el cual se define si
		// va a tomar evidencia o no (sin instrucción de click boton Seleccionar boton 
		//FUNCIÓN EXCLUSIVA PARA TABLET Ya que toma diferentes coordenadas 
		//Creado por: Jorge Lugo 13/02/25
		By CEDIS_BolSeleccionar = By.xpath("//android.widget.TextView[@text=\"" + Bol + "\"]");
		au.scrollToElement(CEDIS_BolSeleccionar);
		Thread.sleep(1000);
		WebElement elemento = au.driver.findElement(CEDIS_BolSeleccionar);
		String posicionobjeto = elemento.getAttribute("bounds");

		String x1Bulto = posicionobjeto.substring(1, 4); // obtiene la posición X1 del bol
		String y1Bulto = posicionobjeto.substring(5, 9); // obtiene la posición y1 del bol
		String x2Bulto = posicionobjeto.substring(11, 14); // obtiene la posición X2 del bol
		String y2Bulto = posicionobjeto.substring(15, 19); // obtiene la posición y2 del bol

		int x1Check = Integer.parseInt(x1Bulto) + 912;// valor que corresponde a la posición X1 del check del bol a
														// seleccionar
		int y1Check = Integer.parseInt(y1Bulto) - 13;// valor que corresponde a la posición y2 del check del bol a
														// seleccionar
		int x2Check = Integer.parseInt(x2Bulto) + 912;// valor que corresponde a la posición X2 del check del bol a
														// seleccionar
		int y2Check = Integer.parseInt(y2Bulto) + 13;// valor que corresponde a la posiciónyX2 del check del bol a
														// seleccionar
		By CEDIS_CheckSeleccionar = By.xpath("//android.widget.CheckBox[@bounds=\"["+ x1Check + "," + y1Check + "]["
				+ x2Check + "," + y2Check + "]\"]");
		System.out.println(x1Check);
		System.out.println(y1Check);
		System.out.println(x2Check);
		System.out.println(y2Check);
		au.click(CEDIS_CheckSeleccionar);
		Thread.sleep(3000);	
	}
	
	
	//Función para dar click en el botón de ACEPTAR BOL con indicador By.id
	//Creado por: Jorge Lugo 19/02/2025
	public void BotonAceptarListaBOL() throws InterruptedException
	{
		if (au.driver.findElements(CEDIS_NvoAceptarBol).isEmpty() == true) {
			Assert.fail("No se ha encontrado el boton de ACEPTA del Listado de BOL´s.", null);
		}
		else
		{
		au.click(CEDIS_NvoAceptarBol);
		}
	}

	public void ValidarBolAgregado(String Bol) throws IOException {// FALTA CONCLUIRLO NO PERMITE FALLAR EL CASO AL NO
																	// ENCONTRAR EL BOL
		// Funcion para validar que se haya seleccionado un bol especifico Creado: Juan
		// Ibarra

		By CEDIS_BolAgregado = By.xpath(
				"//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtbol_item_list\" and @text=\""
						+ Bol + "\"]");

		for (int intentos = 0; intentos < 3; intentos++) {
			boolean element = au.driver.findElements(CEDIS_BolAgregado).isEmpty();
			if (element = false) {
				System.out.println("Se encontro el BOL");
				break;// se encontro el bol
			}
			// boolean verde = au.driver.findElements(CEDIS_CanastillaVerde).isEmpty();
			boolean verde = au.driver.findElements(CEDIS_CanastillaVerde).isEmpty();
			if (verde = false) {
				System.out.println("No se encontro el BOL llego hasta CanastillaVerde");
				break;// se encontro el bol
			}
			au.scrollToElement(CEDIS_CanastillaVerde);

		}
	}

	public void ValidarCantidadCanastillaColorAgregada(String color, String cantidadEsperada)
			throws IOException, InterruptedException {
		// Función para validar que la cantidad esperada sea la correcta según el color
		// de la canastilla
		// Creado: Jesus Marcelo Gutierrez Bravo
		// Fecha Creación: 06-02-2025

		// Obtener el valor numérico según la cantidad especificada
		int intCantidadEsperada = Integer.parseInt(cantidadEsperada);

		// Obtener el valor numérico según el color especificado
		int intCantidadActual = 0;

		switch (color.toLowerCase()) {
		case "azul":
			intCantidadActual = obtenerValorNumerico(CEDIS_ValorAzul);
			break;
		case "amarilla":
			intCantidadActual = obtenerValorNumerico(CEDIS_ValorAmarilla);
			break;
		case "verde":
			intCantidadActual = obtenerValorNumerico(CEDIS_ValorVerde);
			break;
		case "naranja":
			intCantidadActual = obtenerValorNumerico(CEDIS_ValorNaranja);
			break;
		case "otro":
			intCantidadActual = obtenerValorNumerico(CEDIS_ValorOtro);
			break;
		default:
			throw new IllegalArgumentException("Color no válido: " + color);
		}

		// Comparar la cantidad actual con la cantidad esperada
		if (intCantidadActual != intCantidadEsperada) {
			// Log del fallo
			baseExecution.addStep("Fallo validación de cantidad de canastillas color " + color + ": Esperada: "
					+ intCantidadEsperada + ", Actual: " + intCantidadActual);

			// Falla el test con un mensaje detallado
			Assert.fail("La cantidad de canastillas color " + color + " es incorrecta. \nEsperada: "
					+ intCantidadEsperada + "\nActual: " + intCantidadActual);
		}
	}

	public void AumentarCanastilla(int Cantidad, String Color) throws IOException {
		// Funcion que agrega la cantidad de canastillas por un color especifico Creado:
		// Juan Ibarra
		au.scrollToElement(CEDIS_CanastillaOtro);
		switch (Color) {
		case "Azul":
			for (int intentos = 0; intentos < Cantidad; intentos++) {
				au.click(CEDIS_MasAzul);
			}
			break;
		case "Amarilla":
			for (int intentos = 0; intentos < Cantidad; intentos++) {
				au.click(CEDIS_MasAmarilla);
			}
			break;
		case "Verde":
			for (int intentos = 0; intentos < Cantidad; intentos++) {
				au.click(CEDIS_MasVerde);
			}
			break;
		case "Naranja":
			for (int intentos = 0; intentos < Cantidad; intentos++) {
				au.click(CEDIS_MasNaranja);
			}
			break;
		case "Otro":
			for (int intentos = 0; intentos < Cantidad; intentos++) {
				au.click(CEDIS_MasOtro);
			}
			break;
		default:
			baseExecution.addStep("Se falla el caso");
			// Se agrega el paso a mostrar en el PDF
			Assert.fail("No se ha encontrado la canastilla del color " + Color + " mencionado", null);
			// Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria
			// leyenda de null
		}
	}

	public void TomarFotoDevolucion() throws IOException, InterruptedException {
		// Funcion que toma la foto de la Canastilla, toma en cuenta solo una Canastilla
		// de Devolucion, si se agregan mas el identificador cambiara Creado: Juan
		// Ibarra
		au.scrollToElement(CEDIS_FotoDevolucion);
		au.click(CEDIS_FotoDevolucion);
		/**
		 * boolean fotosmaximas=
		 * au.driver.findElements(CEDIS_TomarFotoDevolucion).isEmpty();
		 * Thread.sleep(300); if (fotosmaximas = false) {
		 * au.click(CEDIS_TomarFotoDevolucion); au.click(CEDIS_ConfirmarFotoDevolucion);
		 * }else { au.click(CEDIS_AceptarMensajeUnaFoto); }
		 */
		Thread.sleep(1000);
		au.click(CEDIS_TomarFotoDevolucion);
		Thread.sleep(1000);
		au.click(CEDIS_ConfirmarFotoDevolucion);
		Thread.sleep(25000);
	}

	public void SoloUnaFotoDevolucion(String descPaso) throws IOException, InterruptedException {
		// Funcion que valida que se muestre el mensaje de Solo se permite agregar
		// máximo 1 foto en la Foto de Devolucion Creado: Juan Ibarra
		au.scrollToElement(CEDIS_FotoDevolucion);
		au.click(CEDIS_FotoDevolucion);
		Thread.sleep(1000);
		if (au.driver.findElements(CEDIS_MensajeUnaFoto).isEmpty() != true) {
			baseExecution.addStep(descPaso);
			Thread.sleep(2000);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} else {
			baseExecution.addStep("Se falla el caso");
			Assert.fail("No se mostro el mensaje de Solo se permite agregar máximo 1 foto", null);
		}
	}

	public void MensajeBolNecesario(String descPaso) throws IOException, InterruptedException {
		// Funcion que valida que se muestre el mensaje de Es necesario primero
		// seleccionar un BOL para registrar contenedores Creado: Juan Ibarra
		if (au.driver.findElements(CEDIS_MensajeSeleccionarPrimeroBol).isEmpty() != true) {
			baseExecution.addStep(descPaso);
			Thread.sleep(2000);
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.click(CEDIS_AceptarMensaje);
		} else {
			baseExecution.addStep("Se falla el caso");
			Assert.fail(
					"No se mostro el mensaje de Es necesario primero seleccionar un BOL para registrar contenedores",
					null);
		}
	}

	public void MensajeContenedorNoValido(String descPaso) throws IOException, InterruptedException {
		// Funcion que valida que se muestre el mensaje de Es necesario primero
		// seleccionar un BOL para registrar contenedores Creado: Juan Ibarra
		if (au.driver.findElements(CEDIS_MensajeContenedorNoValido).isEmpty() != true) {
			baseExecution.addStep(descPaso);
			Thread.sleep(2000);
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.click(CEDIS_AceptarMensaje);
		} else {
			baseExecution.addStep("Se falla el caso");
			Assert.fail("No se mostro el mensaje de El código de contenedor ingresado no es válido", null);
		}
	}

	public void MensajeIndicaContenedor(String descPaso) throws IOException, InterruptedException {
		// Funcion que valida que se muestre el mensaje de Indica los contenedores a
		// devolver antes de tomar la fotografía Creado: Juan Ibarra
		if (au.driver.findElements(CEDIS_MensajeIndicarContenedor).isEmpty() != true) {
			baseExecution.addStep(descPaso);
			Thread.sleep(2000);
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.click(CEDIS_AceptarMensaje);
		} else {
			baseExecution.addStep("Se falla el caso");
			Assert.fail("No se mostro el mensaje de Indica los contenedores a devolver antes de tomar la fotografía",
					null);
		}
	}

	public void CancelarFotoDevolucion(String descPaso) throws IOException, InterruptedException {
		// Funcion que cancele la toma de foto de la Canastilla Creado: Juan Ibarra
		au.scrollToElement(CEDIS_FotoDevolucion);
		baseExecution.addStep(descPaso);
		au.click(CEDIS_FotoDevolucion);
		Thread.sleep(2000);
		testCase.addScreenShotCurrentStep(au, "testImage");
		Thread.sleep(2000);
		au.click(CEDIS_CancelarFotoDevolucion);
		Thread.sleep(400);
		if (au.driver.findElements(CEDIS_MensajeCancelarFoto).isEmpty() != true) {
			testCase.addScreenShotCurrentStep(au, "testImage");
		} else {
			System.out.println("No alcanzo a tomar el mensaje");
		}
	}

	public void TomarFotoCanastilla() throws IOException, InterruptedException {
		// Funcion que toma la foto de la Canastilla, toma en cuenta solo una Canastilla
		// de Contenedor, si se agregan mas el identificador del objeto de
		// CEDIS_FotoContedor cambiaria Creado: Juan Ibarra
		au.scrollToElement(CEDIS_FotoContedor);
		au.click(CEDIS_FotoContedor);
		/**
		 * boolean fotosmaximas=
		 * au.driver.findElements(CEDIS_TomarFotoContedor).isEmpty(); if (fotosmaximas =
		 * false) { au.click(CEDIS_TomarFotoContedor);
		 * au.click(CEDIS_ConfirmarFotoContedor); }
		 */
		au.click(CEDIS_TomarFotoContedor);
		au.click(CEDIS_ConfirmarFotoContedor);
		Thread.sleep(30000);
	}

	public void VariasFotoCanastilla(String tomaEvidencia, String descPaso) throws IOException, InterruptedException {
		// Funcion utilizada cuando se agregan varias canastillas para cuando se agregan
		// que toma la foto de la Canastilla, toma en cuenta solo una Canastilla de
		// Contenedor, si se agregan mas el identificador del objeto de
		// CEDIS_FotoContedor cambiaria Creado: Juan Ibarra
		Runtime.getRuntime().exec("adb shell input keyevent 4"); // oculta el teclado - 13/12/24 - cambio por Jorge Lugo
		au.scrollToElement(CEDIS_ContinuarDevolucion);
		Thread.sleep(3000);
		//Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");// Se desliza hacía abajo
		//Se da mantenimiento al método desactivando función swipe, por: Jorge Lugo 17/02/25
		By EvidenciaArticulo = By.xpath(
				"//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/et_photo_layette_header_product_by_package\" and @text=\"Evidencia del artículo\"]");
		// au.scrollToElement(EvidenciaArticulo);

		WebElement elemento = au.driver.findElement(EvidenciaArticulo);
		String posicionobjeto = elemento.getAttribute("bounds");

		String x1Campo = posicionobjeto.substring(1, 3); // obtiene la posición X1 del bol
		System.out.println("valor x1Bulto " + x1Campo);
		String y1Campo = posicionobjeto.substring(4, 7); // obtiene la posición y1 del bol
		System.out.println("valor y1Bulto " + y1Campo);
		String x2Campo = posicionobjeto.substring(9, 12); // obtiene la posición X2 del bol
		System.out.println("valor x2Bulto " + x2Campo);
		String y2Campo = posicionobjeto.substring(13, 16); // obtiene la posición y2 del bol
		System.out.println("valor y2Bulto " + y2Campo);

		int x1Icono = Integer.parseInt(x2Campo) + 16;// valor que corresponde a la posición X1 del check del bol a
														// seleccionar
		System.out.println("valor x1Icono " + x1Icono);
		int y1Icono = Integer.parseInt(y1Campo);// valor que corresponde a la posición y2 del check del bol a
												// seleccionar
		System.out.println("valor y1Icono " + y1Icono);
		int x2Icono = Integer.parseInt(x2Campo) + 96;// valor que corresponde a la posición X2 del check del bol a
														// seleccionar
		System.out.println("valor x2Icono " + x2Icono);
		int y2Icono = Integer.parseInt(y2Campo);// valor que corresponde a la posiciónyX2 del check del bol a
												// seleccionar
		System.out.println("valor y2Icono " + y2Icono);

		By IconoFoto = By.xpath("//android.widget.ImageButton[@bounds=\"[" + x1Icono + "," + y1Icono + "][" + x2Icono
				+ "," + y2Icono + "]\"]");
		if (tomaEvidencia.equals("tomaEvidencia")) {
			baseExecution.addStep(descPaso);
			testCase.addScreenShotCurrentStep(au, "testImage");
			Thread.sleep(3000);
			au.click(IconoFoto);
			Thread.sleep(2000);
			testCase.addScreenShotCurrentStep(au, "testImage");
			Thread.sleep(1000);
			au.click(CEDIS_TomarFotoContedor);
			au.click(CEDIS_ConfirmarFotoContedor);
		} else {
			au.click(IconoFoto);
			au.click(CEDIS_TomarFotoContedor);
			au.click(CEDIS_ConfirmarFotoContedor);
		}

	}

	public void VariasFotoCanastillaTablet(String tomaEvidencia, String descPaso)
			throws IOException, InterruptedException {
		// Funcion utilizada cuando se agregan varias canastillas para cuando se agregan
		// que toma la foto de la Canastilla, toma en cuenta solo una Canastilla de
		// Contenedor, si se agregan mas el identificador del objeto de
		// CEDIS_FotoContedor cambiaria Creado: Juan Ibarra
		Runtime.getRuntime().exec("adb shell input keyevent 4"); // oculta el teclado - 13/12/24 - cambio por Jorge Lugo
		au.scrollToElement(CEDIS_ContinuarDevolucion);
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");// Se desliza hacía abajo
		By EvidenciaArticulo = By.xpath(
				"//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/et_photo_layette_header_product_by_package\" and @text=\"Evidencia del artículo\"]");
		// au.scrollToElement(EvidenciaArticulo);

		WebElement elemento = au.driver.findElement(EvidenciaArticulo);
		String posicionobjeto = elemento.getAttribute("bounds");

		String x1Campo = posicionobjeto.substring(1, 3); // obtiene la posición X1 del bol
		System.out.println("valor x1Bulto " + x1Campo);
		String y1Campo = posicionobjeto.substring(4, 8); // obtiene la posición y1 del bol
		System.out.println("valor y1Bulto " + y1Campo);
		String x2Campo = posicionobjeto.substring(10, 14); // obtiene la posición X2 del bol
		System.out.println("valor x2Bulto " + x2Campo);
		String y2Campo = posicionobjeto.substring(15, 19); // obtiene la posición y2 del bol
		System.out.println("valor y2Bulto " + y2Campo);

		int x1Icono = Integer.parseInt(x2Campo) + 16;// valor que corresponde a la posición X1 del check del bol a
														// seleccionar
		System.out.println("valor x1Icono " + x1Icono);
		int y1Icono = Integer.parseInt(y1Campo);// valor que corresponde a la posición y2 del check del bol a
												// seleccionar
		System.out.println("valor y1Icono " + y1Icono);
		int x2Icono = Integer.parseInt(x2Campo) + 96;// valor que corresponde a la posición X2 del check del bol a
														// seleccionar
		System.out.println("valor x2Icono " + x2Icono);
		int y2Icono = Integer.parseInt(y2Campo);// valor que corresponde a la posiciónyX2 del check del bol a
												// seleccionar
		System.out.println("valor y2Icono " + y2Icono);

		By IconoFoto = By.xpath("//android.widget.ImageButton[@bounds=\"[" + x1Icono + "," + y1Icono + "][" + x2Icono
				+ "," + y2Icono + "]\"]");
		if (tomaEvidencia.equals("tomaEvidencia")) {
			baseExecution.addStep(descPaso);
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.click(IconoFoto);
			Thread.sleep(2000);
			testCase.addScreenShotCurrentStep(au, "testImage");
			Thread.sleep(1000);
			au.click(CEDIS_TomarFotoContedor);
			au.click(CEDIS_ConfirmarFotoContedor);
		} else {
			au.click(IconoFoto);
			au.click(CEDIS_TomarFotoContedor);
			au.click(CEDIS_ConfirmarFotoContedor);
		}
	}

	public void AumentarContenedores(int Cantidad, String Contenedor) throws IOException {
		// Funcion que agrega la cantidad de Contenedores por un contenedor especifico
		// Creado: Juan Ibarra
		au.scrollToElement(CEDIS_LeyendaEnvasesVacios);
		switch (Contenedor) {
		case "Tapas":
			for (int intentos = 0; intentos < Cantidad; intentos++) {
				au.click(CEDIS_MasTapas);
			}
			break;
		case "Envases":
			for (int intentos = 0; intentos < Cantidad; intentos++) {
				au.click(CEDIS_MasEnvasesVacios);
			}
			break;
		default:
			baseExecution.addStep("Se falla el caso");
			// Se agrega el paso a mostrar en el PDF
			Assert.fail("No se ha encontrado el Contenedor  " + Contenedor + " mencionado", null);
			// Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria
			// leyenda de null
		}
	}

	public void sendTextUsingKeyEvents(AppiumUtil au, String text) {
		// Usamos HashMap para permitir más de 10 elementos y ser mutable
		Map<Character, AndroidKey> keyMap = new HashMap<>();

		// Agregar dígitos
		keyMap.put('0', AndroidKey.DIGIT_0);
		keyMap.put('1', AndroidKey.DIGIT_1);
		keyMap.put('2', AndroidKey.DIGIT_2);
		keyMap.put('3', AndroidKey.DIGIT_3);
		keyMap.put('4', AndroidKey.DIGIT_4);
		keyMap.put('5', AndroidKey.DIGIT_5);
		keyMap.put('6', AndroidKey.DIGIT_6);
		keyMap.put('7', AndroidKey.DIGIT_7);
		keyMap.put('8', AndroidKey.DIGIT_8);
		keyMap.put('9', AndroidKey.DIGIT_9);

		// Agregar teclas especiales
		keyMap.put('B', AndroidKey.BACK); // 'B' para BACK
		keyMap.put('E', AndroidKey.ENTER); // 'E' para ENTER

		System.out.println("Enviando texto: " + text);

		// Iterar sobre cada carácter del texto
		for (char c : text.toCharArray()) {
			AndroidKey key = keyMap.get(c);
			if (key != null) {
				System.out.println("Presionando tecla: " + key);
				au.driver.pressKey(new KeyEvent(key));
			} else {
				System.out.println("Advertencia: Carácter '" + c + "' no mapeado.");
			}
		}
	}

	public void SeleccionarObjeto(String ObjetoPantalla) throws InterruptedException {
		// Funcion para buscar objeto y dar click especifico en la pantalla Creado: Juan
		// Ibarra
		switch (ObjetoPantalla) {
		case "Titulo":
			au.click(CEDIS_TituloPantalla);
			break;
		case "Cantidad Azul":
			au.scrollToElement(CEDIS_ValorAzul);
			au.click(CEDIS_ValorAzul);
			break;
		case "Menos Azul":
			au.scrollToElement(CEDIS_MenosAzul);
			au.click(CEDIS_MenosAzul);
			break;
		case "Mas Azul":
			au.scrollToElement(CEDIS_MasAzul);
			au.click(CEDIS_MasAzul);
			break;
		case "Cantidad Amarilla":
			au.scrollToElement(CEDIS_ValorAmarilla);
			au.click(CEDIS_ValorAmarilla);
			break;
		case "Menos Amarilla":
			au.scrollToElement(CEDIS_MenosAmarilla);
			au.click(CEDIS_MenosAmarilla);
			break;
		case "Mas Amarilla":
			au.scrollToElement(CEDIS_MasAmarilla);
			au.click(CEDIS_MasAmarilla);
			break;
		case "Cantidad Verde":
			au.scrollToElement(CEDIS_ValorVerde);
			au.click(CEDIS_ValorVerde);
			break;
		case "Menos Verde":
			au.scrollToElement(CEDIS_MenosVerde);
			au.click(CEDIS_MenosVerde);
			break;
		case "Mas Verde":
			au.scrollToElement(CEDIS_MasVerde);
			au.click(CEDIS_MasVerde);
			break;
		case "Cantidad Naranja":
			au.scrollToElement(CEDIS_ValorNaranja);
			au.click(CEDIS_ValorNaranja);
			break;
		case "Menos Naranja":
			au.scrollToElement(CEDIS_MenosNaranja);
			au.click(CEDIS_MenosNaranja);
			break;
		case "Mas Naranja":
			au.scrollToElement(CEDIS_MasNaranja);
			au.click(CEDIS_MasNaranja);
			break;
		case "Cantidad Otro":
			au.scrollToElement(CEDIS_ValorOtro);
			au.click(CEDIS_ValorOtro);
			break;
		case "Menos Otro":
			au.scrollToElement(CEDIS_MenosOtro);
			au.click(CEDIS_MenosOtro);
			break;
		case "Mas Otro":
			au.scrollToElement(CEDIS_MasOtro);
			au.click(CEDIS_MasOtro);
			break;
		case "Menos Tapas":
			au.scrollToElement(CEDIS_MenosTapas);
			au.click(CEDIS_MenosTapas);
			break;
		case "Mas Tapas":
			au.scrollToElement(CEDIS_MasTapas);
			au.click(CEDIS_MasTapas);
			break;
		case "Menos Envases":
			au.scrollToElement(CEDIS_MenosEnvasesVacios);
			au.click(CEDIS_MenosEnvasesVacios);
			break;
		case "Mas Envases":
			au.scrollToElement(CEDIS_MasEnvasesVacios);
			au.click(CEDIS_MasEnvasesVacios);
			break;
		case "Seleccionar Bol":
			au.scrollToElement(CEDIS_Btn_SeleccionarBol);
			au.click(CEDIS_Btn_SeleccionarBol);
			break;
		case "Aceptar Bol":
			au.scrollToElement(CEDIS_AceptarBol);
			au.click(CEDIS_AceptarBol);
			break;
		case "Continuar":
			au.scrollToElement(CEDIS_ContinuarDevolucion);
			au.click(CEDIS_ContinuarDevolucion);
			Thread.sleep(5000);
			break;
		case "Aceptar":
			au.scrollToElement(CEDIS_AceptarMensaje);
			au.click(CEDIS_AceptarMensaje);
			break;
		case "Confirmar":
			au.click(CEDIS_ConfirmarMensaje);
			break;
		case "Si":
			au.scrollToElement(CEDIS_SiMensajeSeleccionarDevoluciones);
			au.click(CEDIS_SiMensajeSeleccionarDevoluciones);
			break;
		case "No":
			au.scrollToElement(CEDIS_NoMensajeSeleccionarDevoluciones);
			au.click(CEDIS_NoMensajeSeleccionarDevoluciones);
			break;
		case "Cancelar Seleccionar Bol":
			au.scrollToElement(CEDIS_CancelarSeleccionarBol);
			au.click(CEDIS_CancelarSeleccionarBol);
			break;
		case "Confirmar Seleccionar Bol":
			au.scrollToElement(CEDIS_ConfirmarSeleccionarBol);
			au.click(CEDIS_ConfirmarSeleccionarBol);
			break;
		case "Foto Devolucion":
			au.scrollToElement(CEDIS_FotoDevolucion);
			au.click(CEDIS_FotoDevolucion);
			break;
		case "Tomar Foto Devolucion":
			au.scrollToElement(CEDIS_TomarFotoDevolucion);
			au.click(CEDIS_TomarFotoDevolucion);
			break;
		case "Confirmar Foto Devolucion":
			au.scrollToElement(CEDIS_ConfirmarFotoDevolucion);
			au.click(CEDIS_ConfirmarFotoDevolucion);
			break;
		case "Eliminar Foto Devolucion":
			au.scrollToElement(CEDIS_EliminarFotoDevolucion);
			au.click(CEDIS_EliminarFotoDevolucion);
			break;
		case "Aceptar MensajeUnaFoto":
			au.scrollToElement(CEDIS_AceptarMensajeUnaFoto);
			au.click(CEDIS_AceptarMensajeUnaFoto);
			break;
		case "Foto Contedor":
			au.scrollToElement(CEDIS_FotoContedor);
			au.click(CEDIS_FotoContedor);
			break;
		case "Ingresar Contenedor Devolucion":
			au.scrollToElement(CEDIS_IngresarContenedorDevolucion);
			au.click(CEDIS_IngresarContenedorDevolucion);
			break;
		default:
			baseExecution.addStep("Se falla el caso");
			// Se agrega el paso a mostrar en el PDF
			Assert.fail("No se ha encontrado el objeto " + ObjetoPantalla, null);
			// Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria
			// leyenda de null
		}
	}

	public void MoverSeccion(String Seccion) throws IOException {
		// Funcion que permite mover la pantalla a una sección especifica de la pantalla
		// Creado: Juan Ibarra
		switch (Seccion) {
		case "Canastillas":
			au.scrollToElement(CEDIS_CanastillaOtro);
			break;
		case "Contenedores":
			au.scrollToElement(CEDIS_LeyendaEnvasesVacios);
			break;
		case "Canastilla Devolucion":
			au.scrollToElement(CEDIS_IngresarContenedorDevolucion);
			break;
		case "Continuar":
			au.scrollToElement(CEDIS_ContinuarDevolucion);
			break;
		default:
			baseExecution.addStep("Se falla el caso");
			// Se agrega el paso a mostrar en el PDF
			Assert.fail("No se ha encontrado la sección " + Seccion + " mencionada", null);
			// Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria
			// leyenda de null
		}
	}

	public void IngresarCanastilla(String valor) throws IOException, InterruptedException {
		// Funcion que permite capturar información en el campo de Canastilla Creado:
		// Juan Ibarra
		//25/02/25 se eliminan lineas basura
		Thread.sleep(3000);
		au.scrollToElement(CEDIS_ContinuarDevolucion);
		au.driver.hideKeyboard();
		au.sendKeys(CEDIS_IngresarContenedorDevolucion, valor);
		au.click(CEDIS_IngresarContenedorDevolucion);
		Runtime.getRuntime().exec("adb shell input keyevent 66");// realiza el enter en la handheld

	}
	
	public void NuevoIngresarCanastilla(String valor) throws IOException, InterruptedException {
		// Funcion que permite capturar información en el campo de Canastilla sin deshabilitar el teclado al final
		//Creado por: Jorge Lugo 17/02/05
		Thread.sleep(3000);
		au.scrollToElement(CEDIS_ContinuarDevolucion);
		au.driver.hideKeyboard();
		au.sendKeys(CEDIS_IngresarContenedorDevolucion, valor);
		au.click(CEDIS_IngresarContenedorDevolucion);
		Runtime.getRuntime().exec("adb shell input keyevent 66");// realiza el enter en la handheld
	}

	public void MensajeIndicaCanastilla(String descPaso) throws IOException, InterruptedException {
		/*
		 * Funcion que valida que se muestre el mensaje de Es necesario indicar la
		 * cantidad de canastillas con devolución en el color de canastillas
		 * correspondiente. Creado: Jorge Lugo Fecha creación:18-10-2024
		 */
		if (au.driver.findElements(CEDIS_MensajeIndicarCanastilla).isEmpty() != true) {
			baseExecution.addStep(descPaso);
			Thread.sleep(2000);
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.click(CEDIS_AceptarMensaje);
		} else {
			baseExecution.addStep("Se falla el caso");
			Assert.fail(
					"No se mostro el mensaje de Es necesario indicar la cantidad de canastillas con devolución en el color de canastillas correspondiente.",
					null);
		}
	}

	public void MensajeSinEvidenciaContenedor(String descPaso) throws IOException, InterruptedException {
		/*
		 * Funcion que valida que se muestre el mensaje de No se cuenta con la
		 * fotografía de canastillas con devolucion, favor de tomar la fotografía para
		 * continuar. Creado: Jorge Lugo Fecha creación: 21-10-2024
		 */
		au.scrollToElement(CEDIS_ContinuarDevolucion);
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(au, "testImage");
		au.click(CEDIS_ContinuarDevolucion);
		Thread.sleep(3000);
		if (au.driver.findElements(CEDIS_MensajeFotoContenedor).isEmpty() != true) {
			baseExecution.addStep(descPaso);
			Thread.sleep(2000);
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.click(CEDIS_AceptarMensaje);
		} else {
			baseExecution.addStep("Se falla el caso");
			Assert.fail(
					"No se mostro el mensaje de No se cuenta con la fotografía de canastillas con devolución, favor de tomar la fotografía para continuar.",
					null);
		}
	}

	public void MensajeSinEvidenciaCanastilla(String descPaso) throws IOException, InterruptedException {
		/*
		 * Funcion que valida que se muestre el mensaje de No se cuenta con la
		 * fotografía de canastillas a devolver, favor de tomar la fotografía para
		 * continuar. Creado: Jorge Lugo Fecha creación: 21-10-2024
		 */
		au.scrollToElement(CEDIS_ContinuarDevolucion);
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(au, "testImage");
		au.click(CEDIS_ContinuarDevolucion);
		Thread.sleep(3000);
		if (au.driver.findElements(CEDIS_MensajeFotoCanastilla).isEmpty() != true) {
			baseExecution.addStep(descPaso);
			Thread.sleep(2000);
			testCase.addScreenShotCurrentStep(au, "testImage");
			au.click(CEDIS_AceptarMensaje);
		} else {
			baseExecution.addStep("Se falla el caso");
			Assert.fail(
					"No se mostro el mensaje de No se cuenta con la fotografía de canastillas a devolver, favor de tomar la fotografía para continuar.",
					null);
		}
	}

	public void VerificarIconoFotografia(String descPaso) throws IOException, InterruptedException {
		/*
		 * Funcion que valida que se muestre el ícono de la fotografía para la evidencia
		 * de canastillas. Creado: Jorge Lugo Fecha creación: 21-10-2024
		 */
		if (au.driver.findElements(CEDIS_FotoDevolucion).isEmpty() != true) {
			baseExecution.addStep(descPaso);
			Thread.sleep(2000);
			testCase.addScreenShotCurrentStep(au, "testImage");
		} else {
			baseExecution.addStep("Se falla el caso");
			Assert.fail("No se mostró el ícono de la fotografía despues del autoscroll.", null);
		}
	}
	
	public void ValidacionMensajeContinuarSinCanastillasConDevolucion() throws IOException, InterruptedException {
	    // Descripción: Método para validar el texto del mensaje de advertencia
	    // que indica que no hemos ingresado canastillas con devolución.
	    // Autor: Jesús Marcelo Gutiérrez Bravo
	    // Fecha: 12/02/2025
	    
	    // Esperar 1 segundo para la carga de elementos en la página
	    Thread.sleep(1000);
	    
	    // Obtener el mensaje desplegado en la página que indica falta de fotografía
	    WebElement Mensaje = au.driver.findElement(CEDIS_MensajeSeleccionarDevoluciones);
	    String MensajeDesplegado = Mensaje.getText(); // Capturamos el texto del mensaje actual en pantalla
	    String MensajeCorrecto = "Se seleccionaron devoluciones pero no se escanearon canastillas, ¿deseas continuar?"; // Texto esperado

	    // Imprimir ambos mensajes para la depuración
	    System.out.println("Desplegado: " + MensajeDesplegado); 
	    System.out.println("Correcto  : " + MensajeCorrecto);

	    // Validar si el mensaje desplegado no coincide con el mensaje esperado
	    if (!MensajeDesplegado.equals(MensajeCorrecto)) {
	        // Si los mensajes no coinciden, registrar el fallo y tomar captura de pantalla
	        baseExecution.addStep("No se ha desplegado correctamente el mensaje a validar.\n" 
	            + "Mensaje desplegado: " + MensajeDesplegado + "\n"
	            + "Mensaje correcto  : " + MensajeCorrecto); 
	        testCase.addScreenShotCurrentStep(au, "testImage");
	        Assert.fail("No se ha desplegado correctamente el mensaje a validar.\n"
	            + "Mensaje desplegado: " + MensajeDesplegado + "\n"
	            + "Mensaje correcto  : " + MensajeCorrecto, null);
	    }
	}
	
	public void ValidarDespliegueSeccionDeCanastillaConDevolucion() throws IOException, InterruptedException {
	    // Descripción: Método para validar el despliegue/visualización de la seccion: "Canastillas Con Devolucion".
	    // Autor: Jesús Marcelo Gutiérrez Bravo
	    // Fecha: 12/02/2025

	    // Esperar 1 segundo para permitir la carga de elementos en la pantalla
	    Thread.sleep(1000);
	    
	    // Verificar si el primer elemento está visible en pantalla
	    WebElement Elemento = au.driver.findElement(CEDIS_LeyendaCanastillaDevolucion);
	    System.out.println("Verificando si la seccion Canastillas Con Devolucion está visible...");
	    if (!Elemento.isDisplayed()) {
	        // Si el primer elemento no está visible, registrar el fallo y tomar captura de pantalla
	        baseExecution.addStep("No se ha desplegado correctamente la seccion Canastillas Con Devolucion");        
	        Assert.fail("No se ha desplegado correctamente la seccion Canastillas Con Devolucion", null);
	        testCase.addScreenShotCurrentStep(au, "testImage");
	    }
	}
	
	public void ValidacionMensajeDeCanastillaConDevolucionSinFotografia() throws IOException, InterruptedException {
	    // Descripción: Método para validar el texto del mensaje de advertencia
	    // que indica que no hemos ingresado la fotografía de la canastilla con devolución agregada.
	    // Autor: Jesús Marcelo Gutiérrez Bravo
	    // Fecha: 12/02/2025
	    
	    // Esperar 1 segundo para la carga de elementos en la página
	    Thread.sleep(1000);
	    
	    // Obtener el mensaje desplegado en la página que indica falta de fotografía
	    WebElement Mensaje = au.driver.findElement(CEDIS_MensajeCanastillasConDevolucionSinFotografia);
	    String MensajeDesplegado = Mensaje.getText(); // Capturamos el texto del mensaje actual en pantalla
	    String MensajeCorrecto = "No se cuenta con las fotografías de canastillas con devolución, favor de tomar la fotografía para continuar."; // Texto esperado

	    // Imprimir ambos mensajes para la depuración
	    System.out.println("Desplegado: " + MensajeDesplegado); 
	    System.out.println("Correcto  : " + MensajeCorrecto);

	    // Validar si el mensaje desplegado no coincide con el mensaje esperado
	    if (!MensajeDesplegado.equals(MensajeCorrecto)) {
	        // Si los mensajes no coinciden, registrar el fallo y tomar captura de pantalla
	        baseExecution.addStep("No se ha desplegado correctamente el mensaje a validar.\n" 
	            + "Mensaje desplegado: " + MensajeDesplegado + "\n"
	            + "Mensaje correcto  : " + MensajeCorrecto); 
	        testCase.addScreenShotCurrentStep(au, "testImage");
	        Assert.fail("No se ha desplegado correctamente el mensaje a validar.\n"
	            + "Mensaje desplegado: " + MensajeDesplegado + "\n"
	            + "Mensaje correcto  : " + MensajeCorrecto, null);
	    }
	}

	
	public void ValidarDespliegueRegistroDeCanastillaConDevolucion() throws IOException, InterruptedException {
	    // Descripción: Método para validar el despliegue/visualización de uno o varios elementos en pantalla.
	    // Autor: Jesús Marcelo Gutiérrez Bravo
	    // Fecha: 12/02/2025

	    // Esperar 1 segundo para permitir la carga de elementos en la pantalla
	    Thread.sleep(1000);
	    
	    // Verificar si el primer elemento está visible en pantalla
	    WebElement Elemento = au.driver.findElement(CEDIS_ContenedorAgregado);
	    System.out.println("Verificando si el primer elemento está visible...");
	    if (!Elemento.isDisplayed()) {
	        // Si el primer elemento no está visible, registrar el fallo y tomar captura de pantalla
	        baseExecution.addStep("No se ha desplegado correctamente el elemento a validar");        
	        Assert.fail("No se ha desplegado correctamente el elemento a validar", null);
	        testCase.addScreenShotCurrentStep(au, "testImage");
	    }
	    
	    // Verificar si el segundo elemento está visible en pantalla
	    WebElement Elemento2 = au.driver.findElement(CEDIS_EliminarCodigo);
	    System.out.println("Verificando si el segundo elemento está visible...");
	    if (!Elemento2.isDisplayed()) {
	        // Si el segundo elemento no está visible, registrar el fallo y tomar captura de pantalla
	        baseExecution.addStep("No se ha desplegado correctamente el elemento a validar");        
	        Assert.fail("No se ha desplegado correctamente el elemento a validar", null);
	        testCase.addScreenShotCurrentStep(au, "testImage");
	    }
	}


	private int obtenerValorNumerico(By locator) {

		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(10));
		// Esperar hasta que el elemento sea visible y obtener su texto
		WebElement elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		String texto = elemento.getText().trim();

		// Intentar convertir el texto a número
		try {
			return Integer.parseInt(texto);
		} catch (NumberFormatException e) {
			baseExecution.addStep("Error al convertir el valor: " + texto);
			Assert.fail("El valor obtenido no es un número válido: " + texto);
			return 0; // Este retorno nunca se ejecutará porque `Assert.fail()` detendrá la ejecución
		}
	}
	
	public void sendKeysUsingKeyEvents(AppiumUtil au, String text) { //Vanessa Torres 20/02/25
	    Map<Character, AndroidKey> keyMap = new HashMap<>();
	    keyMap.put('0', AndroidKey.DIGIT_0);
	    keyMap.put('1', AndroidKey.DIGIT_1);
	    keyMap.put('2', AndroidKey.DIGIT_2);
	    keyMap.put('3', AndroidKey.DIGIT_3);
	    keyMap.put('4', AndroidKey.DIGIT_4);
	    keyMap.put('5', AndroidKey.DIGIT_5);
	    keyMap.put('6', AndroidKey.DIGIT_6);
	    keyMap.put('7', AndroidKey.DIGIT_7);
	    keyMap.put('8', AndroidKey.DIGIT_8);
	    keyMap.put('9', AndroidKey.DIGIT_9);
	    keyMap.put('B', AndroidKey.BACK);
	    keyMap.put('E', AndroidKey.ENTER);
	 
	    for (char c : text.toCharArray()) {
	        AndroidKey key = keyMap.get(c);
	        if (key != null) {
	            au.driver.pressKey(new KeyEvent(key));
	        }
	    }
	}
	
	 public void subir () throws InterruptedException, IOException { //Vanessa Torres 20/02/25
	    	
	    	Runtime.getRuntime().exec("adb shell input swipe 100 180 100 950 200");
			Thread.sleep(2000);
	    }

	 
	 public void Bol14Dias(String IP) {
		 //Funcion que permite valida que la tienda cuente con BOL's que cuenten con 14 días de antiguedad	Creado: Juan Ibarra	Fecha creación: 05-Mar-2025
		 
		 // Se da formato de año mes y día a las fechas a obtener
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		 
		 // Obtener la fecha actual
		 LocalDate fechaActual = LocalDate.now();
		 System.out.println("Fecha actual: " +fechaActual.format(formatter));
		 
		 // Restar 14 días a la fecha actual
		 LocalDate fechaMenos14 = fechaActual.minusDays(14);
		 System.out.println("Fecha menos 14 días: " +fechaMenos14.format(formatter));
		
		 String Bols = null;//variable string con el cual se guarda la transferencia que se encuentre en el punto de venta
			try {
				System.out.println("Entro al Try ");
				
				Connection conn = DriverManager.getConnection("jdbc:sqlserver://"+IP+";databaseName=satelites;encrypt=true;trustServerCertificate=true", GlobalVariables.userXPOS,GlobalVariables.passwordXPOS);//Se define la conexión al punto de venta por la ip
				
				System.out.println("hace coneción");
				/*String query = "SELECT DISTINCT r.hc_Dev_No, r.hc_Dev_BOL as hc_BOL_ref, r.hc_Transfer_No as HC_TransferNo_Ref,r.hc_Dev_Type, r.hc_Post_Date\r\n" //Query de consulta para traer las transferencias que abarquen los 14 días 
		        		+ "FROM Satelites.hc.hc_devolucion r WITH(NOLOCK)\r\n"
		        		+ "WHERE cast(r.hc_Post_Date as Datetime) BETWEEN DATEADD(dd, -30 ,GETDATE()) AND GETDATE()\r\n"
		        		+ "ORDER BY r.hc_Post_Date DESC;";
		        */
		        
				String query = "SELECT DISTINCT r.hc_Dev_No, r.hc_Dev_BOL as hc_BOL_ref, r.hc_Transfer_No as HC_TransferNo_Ref,r.hc_Dev_Type, r.hc_Post_Date " //Query de consulta para traer las transferencias que abarquen los 14 días 
		        		+ " FROM Satelites.hc.hc_devolucion r WITH(NOLOCK) "
		        		+ " WHERE cast(r.hc_Post_Date as Datetime) BETWEEN DATEADD(dd, -30 ,GETDATE()) AND GETDATE() ORDER BY r.hc_Post_Date DESC; ";
				System.out.println("Lee el query");		
		        
		        System.out.println("Query que se envia : " +query);
		        
				SQLResult res = new SQLResult(conn, query);
				System.out.println("ejecuta el query");		
				Bols = String.valueOf(res.getFirstData().toString());//Se guarda el valor del numero de transferencia 			
				//Bols = String.valueOf(res.getAllData());//Se guarda el valor del numero de transferencia
				System.out.println("guarda valores del query");
		        conn.close();
		        System.out.println("cierra conexión");
		    } catch (Exception e) {	        	    	
		    	System.out.println("entro al catch");
		    	baseExecution.addStep("Se falla el caso");	
		        Assert.fail("No cuenta con BOL´s a los cuales selecionar dentro de los 14 días.");
		    }
			
			System.out.println("Valores consulta BOls: " + Bols);
			
	    }	
			
			
	 
//>>>FIN POM
}