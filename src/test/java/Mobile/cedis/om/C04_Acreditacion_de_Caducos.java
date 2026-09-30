package Mobile.cedis.om;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import junit.framework.Assert;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class C04_Acreditacion_de_Caducos {
	
    private TestCase testCase;
    private AppiumUtil au;
    private BaseExecution baseExecution;

	//-----Identificadores para información general--------------------------//
    private By CEDIS_InfoGeneralCaducosLogo = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/imgResume");
	private By CEDIS_InfoGeneralCaducosFecha = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleDate");
	private By CEDIS_InfoGeneralCaducosRuta = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleRute");
	private By CEDIS_InfoGeneralCaducosRutaDinamica = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleRuteD");
	private By CEDIS_InfoGeneralCaducosCrTienda = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleStore");
	private By CEDIS_InfoGeneralCaducosNombreTienda = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleNameStore");
	private By CEDIS_InfoGeneralCaducosTransferencia = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvTransferNumber");
	
	//-----Identificadores para boton de tutorial y ayuda--------------------//
	private By CEDIS_BotonDeAyuda = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/help");
	private By CEDIS_BotonDeTutorial = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tutorial");
	
	//-----Identificadores para contenido principal de pantalla--------------//
	private By CEDIS_CajaDeTextoInstrucciones = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/text_view_body_instruccions");
	private By CEDIS_TextoListadoCaducos = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/text_view_title_items_damage");
	
	//-----Identificadores para agregar un artículo caduco-------------------//
	private By CEDIS_BotonParaIngresarSkuManualmente = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/buttonEnterSkuCode");
	private By CEDIS_CajaDeTextoIngresarSkuArticulo = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/edtAddDamaged");
	private By CEDIS_ListadoArticulosFiltrados = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/rvDanadosRec");
	private By CEDIS_PrimerSKUdelListado = By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/rvDanadosRec\"]/android.widget.LinearLayout[1]");
	private By CEDIS_MensajeDeConfirmacionAlAgregarSku = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\"]");
	private By CEDIS_BotonNoParaMensaje = By.id("android:id/button2");
	private By CEDIS_BotonSiParaMensaje = By.id("android:id/button1");
	
	//-----Identificadores para modificar el articulo agregado----------------//
	private By CEDIS_EliminarRegistroDelArticulo = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/title_item_delete");
	private By CEDIS_SkuDelArticuloAgregado = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/title_item_sku");
	private By CEDIS_BotonDisminuirCantidadSku = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/imageMinusItems");
	private By CEDIS_CajaTextoCantidadRegistradaDelSku = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/textViewItemsCountVerify");
	private By CEDIS_BotonAumentarCantidadSku = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/imagePlusItems");
	private By CEDIS_CajaTextoFotografiaSku = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/et_photo_layette_header_product_by_package");
	private By CEDIS_BotonParaAbrirCamaraSku = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/imageButtonPhotoLayette");
	private By CEDIS_BotonParaTomarFotografia = By.id("org.codeaurora.snapcam:id/shutter_button");
	private By CEDIS_BotonParaCancelarTomaFotografia = By.id("org.codeaurora.snapcam:id/btn_cancel");
	private By CEDIS_BotonParaVolveraTomarFotografia = By.id("org.codeaurora.snapcam:id/btn_retake");
	private By CEDIS_BotonParaAceptarFotografiaTomada = By.id("org.codeaurora.snapcam:id/done_button");
	private By CEDIS_BotonParaTomarFotografiaTT = By.id("org.codeaurora.snapcam:id/shutter_button");
	private By CEDIS_BotonParaAceptarFotografiaTomadaTT = By.id("org.codeaurora.snapcam:id/done_button");

	
    private By Cedis_BotonParaAbrirCamaraSkuSegundoarticulo = By.xpath("(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/imageButtonPhotoLayette\"])[2]");
	
	
	//-----Identificadores para ver el total de articulos agregados------------//
	private By CEDIS_CajaDeTextoTotalPiezas = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvTotalItemsExpired");
	//------Identificador para texto el articulo no puede contener una cantidad mayor al numerode piezas recibidas--- ricardo//
	private By CEDIS_CONFIRMAR = By.id("android:id/button1");

	//-----Identificadores para confirmar el registro de articulos caducos-----//
	private By CEDIS_BotonContinuar = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/buttonFinishVerifyExpired");
	private By CEDIS_MensajeConfirmacion = By.id("android:id/message");
	private By CEDIS_ConfirmarNo = By.id("android:id/button2");
	private By CEDIS_ConfirmarSi = By.id("android:id/button1");
	
	//-----Identificador para ir a modificar dañados--------------------------//
	private By CEDIS_BotonModificarDanados = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/buttonEditVerifyDamage");
	
	//-----Identificador para ir a modificar dañados--------------------------//
	private By CEDIS_confirmarfaltaevidencia = By.id("android:id/button1");
		
	//-----Identificador para SKU--------------------------//
	private By CEDIS_descripcion = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textView\"]");
			//By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textView\"]");
	
	//----- Identificador agregados ultimamente por compañeros -----//
	private By CEDIS_PrimerSKUdelLista = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/textView");
	
	//-----Variables utilizadas para cantidad de SKU-----//
	private int cantidad = 8;
	private AppiumDriver driver;

	
	//------------ METODOS ------------//
	
	public C04_Acreditacion_de_Caducos(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
		this.testCase = testCase;
		this.au = au;
		this.baseExecution = baseExecution;
	}

	/* METODO CREADO 
	 * Descripción: Convierte los codigos de tecla de los digitos en numeros
	 * @autor Vanessa Torres
	 * @date 14/08/2024
	 */
	private static final Map<Character, AndroidKey> keyMap = new HashMap<>();    
	public void KeyEventHelper() {
//		keyMap.put('A', AndroidKey.A);
//      keyMap.put('B', AndroidKey.B);
		// Mapeos para números
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
        // También puedes agregar caracteres especiales si es necesario
    }

	/* METODO CREADO 
	 * Descripción: Simula la acción de teclear caracteres
	 * @autor Vanessa
	 * @date 14/08/2024
	 */
    public void sendTextUsingKeyEvents(String text) {
        for (char c : text.toCharArray()) {
            AndroidKey key = keyMap.get(Character.toUpperCase(c));
            if (key != null) {
                // Simula la pulsación de la tecla
                au.driver.pressKey(new KeyEvent(key));
            } else {
                System.err.println("Carácter no soportado: " + c);
            }
        }
    }
    
	/* METODO CREADO 
	 * Descripción: Simula la acción de teclear caracteres de texto
	 * @autor Marcelo
	 * @date 14/08/2024
	 */
	public void sendTextUsingKeyEventsT(String text) {
	    for (char c : text.toCharArray()) {
	       AndroidKey key = AndroidKey.valueOf(String.valueOf(c).toUpperCase());
	       au.driver.pressKey(new KeyEvent(key));
	    }
	}

	
	public void botondeayuda () {
		au.click(CEDIS_BotonDeAyuda);
		
	}
	
	public void botontutorial () {
		
		au.click(CEDIS_BotonDeTutorial);
	}
	
	
	public void presionarbotonIngresarCodigoSKU() throws InterruptedException {
		au.scrollToElement(CEDIS_BotonParaIngresarSkuManualmente);
		au.click(CEDIS_BotonParaIngresarSkuManualmente);
		Thread.sleep(1000);
	}
	
//	/* METODO CREADO 
//	 * Descripción: Valida si existe un elemento o no 
//	 * @autor Vanessa
//	 * @date 14/08/2024
//	 */
//	public boolean isCajaDeTextoPresente() {
//		return isElementPresent(CEDIS_CajaDeTextoIngresarSkuArticulo);
//	}
//
//	private boolean isElementPresent(By by) {
//		try {
//			au.driver.findElement(by);
//			return true;
//		} catch (NoSuchElementException e) {
//			return false;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//	
//	public void validarsiexisteTXT() {
//		if (isCajaDeTextoPresente()) {
//			System.out.println("La caja de texto existe.");
//		} else {
//			System.out.println("La caja de texto no existe.");
//		}
//	}
	//Fin del metodo

	/* METODO CREADO 
	 * Descripción: Valida si existe el botón o no 
	 * @autor Vanessa
	 * @date 15/08/2024
	 */
//	public boolean isBotonSKUManualPresente() {
//		return isElementPresent(CEDIS_BotonParaIngresarSkuManualmente);
//	}
//	
//	public void siexisteBoton() {
//		if (isBotonSKUManualPresente()) {
//			System.out.println("El botón exite.");
//		} else {
//			System.out.println("El botón no existe.");
//		}
//	}
//	
	
	/* METODO CREADO 
	 * Descripción: Valida si existe el botón o no 
	 * @autor Vanessa
	 * @date 15/08/2024
	 */
	public void siexisteTXT()
	{
		au.driver.findElement(CEDIS_CajaDeTextoIngresarSkuArticulo).isDisplayed();
		System.out.println("La caja de texto existe.");
	
	}
	
	public void siexisteBoton()
	{
		au.driver.findElement(CEDIS_BotonParaIngresarSkuManualmente).isDisplayed();
		System.out.println("El botón existe.");
	
	}
	
	/* METODO CREADO 
	 * Descripción: Comprueba que se está en la pantalla de CADUCOS y esta tienen la información 
	 * @autor Vanessa
	 * @date 15/08/2024
	 */
	public void pantallaCaducos() throws InterruptedException {
		
		System.out.println("validando elementos info general");
		Thread.sleep(3000);
		au.driver.findElement(CEDIS_InfoGeneralCaducosFecha).isDisplayed();
		au.driver.findElement(CEDIS_InfoGeneralCaducosRuta).isDisplayed();
		au.driver.findElement(CEDIS_InfoGeneralCaducosCrTienda).isDisplayed();
		au.driver.findElement(CEDIS_InfoGeneralCaducosNombreTienda).isDisplayed();
		au.driver.findElement(CEDIS_InfoGeneralCaducosTransferencia).isDisplayed();
		System.out.println("Información general se muestra.");
		au.scrollToElement(CEDIS_BotonParaIngresarSkuManualmente);
		System.out.println("Validando elementos: Instrucciones,boton INGRESAR SKU , Total Piezas");
		Thread.sleep(3000);
		au.driver.findElement(CEDIS_TextoListadoCaducos).isDisplayed();
		au.driver.findElement(CEDIS_CajaDeTextoInstrucciones).isDisplayed();
		au.driver.findElement(CEDIS_BotonParaIngresarSkuManualmente).isDisplayed();
		System.out.println("Información se muestra.");
		System.out.println("Validando botones y cajas de texto...");
		Thread.sleep(3000);
		au.scrollToElement(CEDIS_BotonModificarDanados);
		au.driver.findElement(CEDIS_CajaDeTextoTotalPiezas).isDisplayed();
		au.driver.findElement(CEDIS_BotonModificarDanados).isDisplayed();
		au.driver.findElement(CEDIS_BotonContinuar).isDisplayed();
		System.out.println("Caja de texto y botones se muestran.");

	}
	//Fin metodo
	
	public void ingresarcodigoSKUmanual() {

		au.click(CEDIS_BotonParaIngresarSkuManualmente);
		au.click(CEDIS_CajaDeTextoIngresarSkuArticulo);
		au.sendKeys(CEDIS_CajaDeTextoIngresarSkuArticulo, "7501023542209");
		au.click(CEDIS_PrimerSKUdelListado);
		au.click(CEDIS_BotonSiParaMensaje);

	}
	
	public void noingresarcodigoSKUmanual() {

		au.click(CEDIS_BotonParaIngresarSkuManualmente);
		au.click(CEDIS_CajaDeTextoIngresarSkuArticulo);
		au.sendKeys(CEDIS_CajaDeTextoIngresarSkuArticulo, "7501023542209");
		au.click(CEDIS_PrimerSKUdelListado);
		au.click(CEDIS_BotonNoParaMensaje);

	}

	public void ingresar3primerasletras() throws InterruptedException {
		Thread.sleep(1000);
		sendTextUsingKeyEventsT("JAM");
		Thread.sleep(1000);
	}
	
	public void ingresarskupor3letras() {
		au.sendKeys(CEDIS_CajaDeTextoIngresarSkuArticulo, "BUR");
		au.click(CEDIS_PrimerSKUdelListado);
		au.click(CEDIS_BotonSiParaMensaje);
	}

	public void validardescripcionarticulo() throws InterruptedException {
	    Thread.sleep(1000);
	    System.out.println("Esperando 1 segundo antes de obtener la descripción del artículo.");

	    // Obtener el texto de la descripción
	    String descripcion = au.driver.findElement(CEDIS_descripcion).getText();
	    System.out.println("Descripción obtenida: " + descripcion);

	    // Texto esperado
	    String textodescripcion = "7501057710155 JAMON VIRGINIA DE PAVO ZWAN 250 GRAMOS";
	    System.out.println("Texto esperado: " + textodescripcion);

	    // Validar si la descripción es correcta
	    if (descripcion.equals(textodescripcion)) {
	        System.out.println("Se muestra el mensaje de manera correcta.");
	    } else {
	        System.out.println("Se muestra el mensaje de manera incorrecta.");
	        testCase.addTextEvidenceCurrentStep("Se muestra mensaje de manera incorrecta");
	    }
	}

	    
	 public void ValidacionDescripcionArticulo() { //ricardo 30/08/2024
	        // Obtener el texto mostrado en la aplicación
	        WebElement elemento = au.driver.findElement(CEDIS_descripcion);
	        String mensajeMostrado = elemento.getText().trim(); // Eliminar espacios al inicio y al final
	        // Texto esperado
	        String txtWarning = "Ingresaste el siguiente artículo:\r\n"
	        		+ "\r\n"
	        		+ "7501057710155 JAMON VIRGINIA DE PAVO ZWAN 250 GRAMOS\r\n"
	        		+ "\r\n"
	        		+ "¿Deseas continuar?\r\n";
	                
	        // Normalizar los textos para comparar sin espacios adicionales
	        String mensajeMostradoNormalizado = mensajeMostrado.replaceAll("\\s+", " ").trim();
	        String txtWarningNormalizado = txtWarning.replaceAll("\\s+", " ").trim();

	        // Imprimir mensajes para depuración
	        System.out.println("Mensaje mostrado (normalizado): " + mensajeMostradoNormalizado);
	        System.out.println("Mensaje esperado (normalizado): " + txtWarningNormalizado);

	        // Validar si el mensaje mostrado no coincide con el mensaje esperado
	        if (!mensajeMostradoNormalizado.equals(txtWarningNormalizado)) {
	            System.out.println("El mensaje es incorrecto.");
	            // Registrar evidencia cuando el mensaje es incorrecto
	            testCase.addTextEvidenceCurrentStep("Se muestra el mensaje de manera incorrecta.");
	            // Usar un assertion para fallar la prueba si el mensaje es incorrecto
	            Assert.fail("El mensaje mostrado no coincide con el mensaje esperado.");
	        } else {
	            System.out.println("El mensaje es correcto.");
	            // Registrar evidencia cuando el mensaje es correcto
	            testCase.addTextEvidenceCurrentStep("Se muestra el mensaje de manera correcta.");
	        }
	    }
	public void ClickenPantalla() throws IOException {
		System.out.println("Dando clic en pantalla");
		 int x = 56;
         int y = 1126;
         // Construir el comando ADB
         String command = "adb shell input tap " + x + " " + y;
         // Ejecutar el comando
         Runtime.getRuntime().exec(command);
         System.out.println("Clic simulado en las coordenadas: (" + x + ", " + y + ")");		
	}
	
	/* METODO CREADO
     * Descripción: Método complementario para el envío de caracteres tipo números , no texto.
     * @autor Vanessa
     * @date 14/08/2024
     */
    public void sendTextUsingKeyEventsV(String text) {
        //Vanem
        for (char c : text.toCharArray()) {
            AndroidKey key = keyMap.get(Character.toUpperCase(c));
            if (key != null) {
                // Simula la pulsación de la tecla
                au.driver.pressKey(new KeyEvent(key));
            } else {
                System.err.println("Carácter no soportado: " + c);
            }
        }
    }
	
	public void ingresar3primerosnumeros() throws InterruptedException {
		//Vanem
		Thread.sleep(1000);
		System.out.println("Esperando 1 segundo antes de limpiar la caja de texto.");
		au.driver.findElement(CEDIS_CajaDeTextoIngresarSkuArticulo).clear();
		System.out.println("Caja de texto limpiada.");

		Thread.sleep(1000);
		System.out.println("Esperando 1 segundo antes de ejecutar 'KeyEventHelper'.");
		KeyEventHelper();
		System.out.println("'KeyEventHelper' ejecutado.");

		Thread.sleep(1000);
		System.out.println("Esperando 1 segundo antes de enviar texto '750' con 'sendTextUsingKeyEvents'.");
		sendTextUsingKeyEvents("750");
		System.out.println("Texto '750' enviado usando 'sendTextUsingKeyEvents'.");

		Thread.sleep(1000);
		System.out.println("Método completado.");

	}
	
	public void inrgesararticulox3num() {
		au.sendKeys(CEDIS_CajaDeTextoIngresarSkuArticulo, "750");
		au.click(CEDIS_PrimerSKUdelListado);
		au.click(CEDIS_BotonSiParaMensaje);	
	}

	public void tomarfotografiacaduco() {
		au.click(CEDIS_BotonParaAbrirCamaraSku);
		au.click(CEDIS_BotonParaTomarFotografia);
		au.click(CEDIS_BotonParaAceptarFotografiaTomada);
	}
	
	public void tomarfotografiacaducoTT() {
		au.click(CEDIS_BotonParaAbrirCamaraSku);
		au.click(CEDIS_BotonParaTomarFotografiaTT);
		au.click(CEDIS_BotonParaAceptarFotografiaTomadaTT);
	}

	public void cancelarfotografiacaduco() {

		au.click(CEDIS_BotonParaAbrirCamaraSku);
		au.click(CEDIS_BotonParaTomarFotografia);
		au.click(CEDIS_BotonParaCancelarTomaFotografia);

	}
	
	public void volveratomarfotografiacaduco() {

		au.click(CEDIS_BotonParaAbrirCamaraSku);
		au.click(CEDIS_BotonParaTomarFotografia);
		au.click(CEDIS_BotonParaVolveraTomarFotografia);
		au.click(CEDIS_BotonParaTomarFotografia);
		au.click(CEDIS_BotonParaAceptarFotografiaTomada);

	}

	public void aumentarcantidadskucaduco() {

		au.click(CEDIS_BotonAumentarCantidadSku);

	}

	public void disminuircantidadskucaduco() {

		au.click(CEDIS_BotonDisminuirCantidadSku);

	}
	
	public void ingresarmanualmentecantidadsku() {
		
		au.click(CEDIS_CajaTextoCantidadRegistradaDelSku);
		au.sendKeys(CEDIS_CajaTextoCantidadRegistradaDelSku, "2");
	}
	
	public void eliminarregistrocaduco() {

		au.click(CEDIS_EliminarRegistroDelArticulo);

	}

	public void iramodificardanados() {

		au.click(CEDIS_BotonModificarDanados);

	}

	public void finalizarcaducos() {

		au.click(CEDIS_BotonContinuar);

	}
	
	public void continuarsinevidencia() {

		ingresarcodigoSKUmanual();
		au.click(CEDIS_BotonContinuar);
		au.click(CEDIS_confirmarfaltaevidencia);

	}

	public void mensajecantidadmayordepieza() {

		ingresarcodigoSKUmanual();
		/* queda pendienteeee */

	}

	public void continuararesumen() {

		au.click(CEDIS_BotonContinuar);
		au.click(CEDIS_ConfirmarSi);
	}

	public void nocontinuararesumen() {

		au.click(CEDIS_BotonContinuar);
		au.click(CEDIS_ConfirmarNo);
	}
	
	/* METODO CREADO 
	 * Descripción: Toma el primer SKU de la lista 
	 * @autor Ricardo
	 * @date 14/08/2024
	 */
	public void clicprimerarticulodespegable() {
		au.click(CEDIS_PrimerSKUdelLista);
	}
	
	/* METODO CREADO 
	 * Descripción: Toma el primer SKU de la lista 
	 * @autor Ricardo
	 * @date 16/08/2024
	 */
	public void presionarbotonSiDescripcionArticulo() {
		au.click(CEDIS_BotonSiParaMensaje);	
		au.scrollToElement(CEDIS_BotonContinuar); 
	}
	
	public void presionarbotonNoDescripcionArticulo() {
		au.click(CEDIS_ConfirmarNo);	
		au.scrollToElement(CEDIS_BotonContinuar); 
	}
	
	/* METODO CREADO 
	 * Descripción: Busca si un elemento de la pantalla es visible
	 * @autor Jorge Lugo
	 * @date 14/08/2024
	 */
	public void cuadroTextoIngresarCodigoSKUVerificado()
	{
		boolean cuadroTexto_CodigoSKU = au.driver.findElement(CEDIS_CajaDeTextoIngresarSkuArticulo).isDisplayed();
		if (cuadroTexto_CodigoSKU != true) {
			testCase.addTextEvidenceCurrentStep("No se muestra el cuadro de texto para Ingresar el SKU Manualmente");
		}
	}
 
	public void botonIngresarCodigoSKUVerificado()
	{
		boolean btn_CodigoSKU = au.driver.findElement(CEDIS_BotonParaIngresarSkuManualmente).isDisplayed();
		if (btn_CodigoSKU != true) {
			testCase.addTextEvidenceCurrentStep("No se muestra boton de INGRESAR CÓDIGO SKU");
		}
	}
	
	/* METODO CREADO 
	 * Descripción: 
	 * @autor Jorge Lugo
	 * @date 16/08/2024
	 */
	public void validarMensajeArticuloAgregadoCaduco()
	{
		boolean mensajeArticuloAgregado = au.driver.findElement(CEDIS_MensajeDeConfirmacionAlAgregarSku).isDisplayed();
		if (mensajeArticuloAgregado != true) {
			System.out.println("No se muestra mensaje");	
			testCase.addTextEvidenceCurrentStep("No se muestra mensaje.");
		}
		System.out.println("Se muestra mensaje de manera correcta");
	}

	/*
	 * METODO CREADO Descripción: Metodo para realizar validacion de pantalla de
	 * caducos sin ingresar datos y regresar a pantalla de Dañados.
	 * @autor Ricardo Hdz
	 * @date 19/08/2024
	 */

	public void validarPantallaCaducos(String descPaso) throws InterruptedException {
		au.scrollToElement(CEDIS_InfoGeneralCaducosNombreTienda);
		Thread.sleep(1000);
		baseExecution.addStep(descPaso);
		testCase.addScreenShotCurrentStep(au, "testImage");
		au.scrollToElement(CEDIS_BotonModificarDanados);
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(au, "testImage");
		Thread.sleep(1000);
		au.click(CEDIS_BotonModificarDanados);
	}
	
	/*
	 * METODO CREADO Descripción: Valida instrucciones
	 * @autor Juan Ibarra
	 * @date 29/08/2024
	 */
	
	public void validacionInstruccionesCaducos() throws InterruptedException{
		/*Funcion que permite validar la descripción del texto en las intrucciones  	
    	Creado: Juan Ibarra		    	
    	 */    	    	
		Thread.sleep(1000);
        WebElement elemento = au.driver.findElement(CEDIS_CajaDeTextoInstrucciones);
        String mensajeMostrado1 = elemento.getText().trim(); // Eliminar espacios al inicio y al final
        // Texto esperado
        String txtWarning1 = "1. Escanea o da clic al botón \"Ingresar código SKU\" para registrar cada uno de los artículos caducos. 2. Coloca la cantidad de productos que recibiste. 3. Toma fotografía de cada uno de los artículos caducos.";

        // Normalizar los textos para comparar sin espacios adicionales
        String mensajeMostradoNormalizado = mensajeMostrado1.replaceAll("\\s+", " ").trim();
        String txtWarningNormalizado = txtWarning1.replaceAll("\\s+", " ").trim();

        // Imprimir mensajes para depuración
        System.out.println("Mensaje mostrado (normalizado): " + mensajeMostradoNormalizado);
        System.out.println("Mensaje esperado (normalizado): " + txtWarningNormalizado);

        // Validar si el mensaje mostrado no coincide con el mensaje esperado
        if (!mensajeMostradoNormalizado.equals(txtWarningNormalizado)) {
        	baseExecution.addStep("El mensaje es incorrecto");        
    		Assert.fail("El mensaje mostrado no coincide con el mensaje esperado, se muestra " +mensajeMostradoNormalizado);    		
        } else {
            System.out.println("El mensaje es correcto.");
        } 
        
    }
	
	public void validacionInstruccionesCaducosTablet() { //ricardo 30/08/2024
        // Obtener el texto mostrado en la aplicación
        WebElement elemento = au.driver.findElement(CEDIS_CajaDeTextoInstrucciones);
        String mensajeMostrado = elemento.getText().trim(); // Eliminar espacios al inicio y al final

        // Texto esperado
        String txtWarning =
              "1. Escanea o da clic al botón \"Ingresar código SKU\" para registrar cada uno de los artículos caducos.\r\n"
            + "2. Coloca la cantidad de productos que recibiste.\r\n"
            + "3. Toma fotografía de cada uno de los artículos caducos.";

        // Normalizar los textos para comparar sin espacios adicionales
        String mensajeMostradoNormalizado = mensajeMostrado.replaceAll("\\s+", " ").trim();
        String txtWarningNormalizado = txtWarning.replaceAll("\\s+", " ").trim();

        // Imprimir mensajes para depuración
        System.out.println("Mensaje mostrado (normalizado): " + mensajeMostradoNormalizado);
        System.out.println("Mensaje esperado (normalizado): " + txtWarningNormalizado);

        // Validar si el mensaje mostrado no coincide con el mensaje esperado
        if (!mensajeMostradoNormalizado.equals(txtWarningNormalizado)) {
            System.out.println("El mensaje es incorrecto.");
            // Registrar evidencia cuando el mensaje es incorrecto
            testCase.addTextEvidenceCurrentStep("Se muestra el mensaje de manera incorrecta.");
            // Usar un assertion para fallar la prueba si el mensaje es incorrecto
            Assert.fail("El mensaje mostrado no coincide con el mensaje esperado.");
        } else {
            System.out.println("El mensaje es correcto.");
            // Registrar evidencia cuando el mensaje es correcto
            testCase.addTextEvidenceCurrentStep("Se muestra el mensaje de manera correcta.");
        }
	}

	/* METODO CREADO
	 * Descripción: Funciona paradar clic en confirmar mensaje de texto
	 * @autor Ricardo
	 * @date 02/09/2024
	 */
	public void mensajecantidadmayordepiezAceptar() {
		au.click(CEDIS_CONFIRMAR);
	}

	/* METODO CREADO
	 * Descripción: Aumenta lacantidad maxima del articulo Burrito ChataDuo
	 * @autor Ricardo Hdz
	 * @date 02/09/2024
	 */
	public void aumentarCantidadSku8Veces() throws InterruptedException {
		// Número de veces quequeremos hacer clic
		int numeroDeClicks = 8;

		// Hacer clic en el botón el número deseado de veces
		for (int i = 0; i < numeroDeClicks; i++) {
			// Hacer clic en el botón para aumentar la cantidad
			Thread.sleep(4000);
			au.click(CEDIS_BotonAumentarCantidadSku);
		}
	}
	
	/* METODO CREADO 
	 * Descripción: Para ingresar los tres caracteres del articulo sandwich 
	 * @autor Ricardo
	 * @date 11/09/2024
	 */
	
	public void ingresar3primerasletrasSAN() {
		sendTextUsingKeyEventsT("TRI");
	}

	
	 /* METODO CREADO
    * Descripción: metodo agregado para realizar la toma de la fotografia del segundo articulo agregado
    * @autor Ricardo
    * @date 11/09/2024
    */
   
   public void tomarfotografiaDanadossegundoarticulo() throws InterruptedException {
   	Thread.sleep(3000);
   	au.click(Cedis_BotonParaAbrirCamaraSkuSegundoarticulo);
		au.click(CEDIS_BotonParaTomarFotografia);
		au.click(CEDIS_BotonParaAceptarFotografiaTomada);
   }
   
 //Autor: Jorge Alberto Lugo Martinez 11/02/2025
 	//Metodo para validar la información del encabezado
 	public void validarEncabezadoRutasCaducos() throws InterruptedException
 	{
 		if(au.driver.findElement(CEDIS_InfoGeneralCaducosLogo).isEnabled()==false) {
     		baseExecution.addStep("Se falla el caso");        
     		Assert.fail("No se ha encontrado el Logo de Oxxo de la pantalla de ARTÍCULOS CADUCOS en la parte de inicio");
     	}    	
     	if (au.driver.findElements(CEDIS_InfoGeneralCaducosFecha).isEmpty()!= false) {    		
     		baseExecution.addStep("Se falla el caso");        
     		Assert.fail("No se ha encontrado el dato de la Fecha de la pantalla de ARTÍCULOS CADUCOS en la parte de inicio");
     	}    	
     	if(au.driver.findElements(CEDIS_InfoGeneralCaducosRuta).isEmpty()!= false) {
     		baseExecution.addStep("Se falla el caso");        
     		Assert.fail("No se ha encontrado el dato de la Ruta de la pantalla de ARTÍCULOS CADUCOS en la parte de inicio");
     	}
     	if(au.driver.findElements(CEDIS_InfoGeneralCaducosRutaDinamica).isEmpty()!= false) {
     		baseExecution.addStep("Se falla el caso");        
     		Assert.fail("No se ha encontrado el dato de la Ruta Dinamica de la pantalla de ARTÍCULOS CADUCOS en la parte de inicio");
     	}
     	if(au.driver.findElements(CEDIS_InfoGeneralCaducosCrTienda).isEmpty()!= false) {
     		baseExecution.addStep("Se falla el caso");        
     		Assert.fail("No se ha encontrado el dato del CR de Tienda de la pantalla de ARTÍCULOS CADUCOS en la parte de inicio");
     	}
     	if(au.driver.findElements(CEDIS_InfoGeneralCaducosNombreTienda).isEmpty()!= false) {
     		baseExecution.addStep("Se falla el caso");        
     		Assert.fail("No se ha encontrado el dato del nombre de la Tienda de la pantalla de ARTÍCULOS CADUCOS en la parte de inicio");
     	}
     	if(au.driver.findElements(CEDIS_InfoGeneralCaducosTransferencia).isEmpty()!= false) {
     		baseExecution.addStep("Se falla el caso");        
     		Assert.fail("No se ha encontrado el dato del número de la transferencia de la pantalla de ARTÍCULOS CADUCOS en la parte de inicio");
     	}
 	}
 	
 	public void validarPantallaCaducos() throws InterruptedException {
    	// Descripción: Método para validar el la leyenda de articulos caducos
	    // Autor: Jesús Marcelo Gutiérrez Bravo
	    // Fecha: 12/02/2025
	    
	    // Esperar 1 segundo para la carga de elementos en la página
	    Thread.sleep(1000);
	    
	    // Obtener el mensaje desplegado en la página que indica falta de fotografía
	    WebElement Mensaje = au.driver.findElement(CEDIS_TextoListadoCaducos);
	    String MensajeDesplegado = Mensaje.getText(); // Capturamos el texto del mensaje actual en pantalla
	    String MensajeCorrecto = "LISTADO DE ARTÍCULOS CADUCOS"; // Texto esperado
 
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
	            + "Mensaje correcto  : " + MensajeCorrecto);
	    }
    }
 	
 	public void SeleccionarObjeto(String ObjetoPantalla)
    {  	
    	//Funcion para buscar objeto y dar click especifico en la pantalla Creado: Juan Ibarra    	
    	switch (ObjetoPantalla) {
    	case "Boton Modificar Danados":
    		au.scrollToElement(CEDIS_BotonModificarDanados);
    		au.click(CEDIS_BotonModificarDanados);
            break;    	    	            
        default:
            baseExecution.addStep("Se falla el caso");
            //Se agrega el paso a mostrar en el PDF
    		Assert.fail("No se ha encontrado el objeto "+ ObjetoPantalla);
    		//Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria leyenda de null
    	}    	
    }
 	
 	public void validarMensajeAlClickBotonIngresarCodigoSKU() throws InterruptedException {
    	// Descripción: Método para validar el texto del campo flotante para meter skus
	    // Autor: Jesús Marcelo Gutiérrez Bravo
	    // Fecha: 12/02/2025
	    
	    // Esperar 1 segundo para la carga de elementos en la página
	    Thread.sleep(1000);
	    
	    // Obtener el mensaje desplegado en la página que indica falta de fotografía
	    WebElement Mensaje = au.driver.findElement(CEDIS_CajaDeTextoIngresarSkuArticulo);
	    String MensajeDesplegado = Mensaje.getText(); // Capturamos el texto del mensaje actual en pantalla
	    String MensajeCorrecto = "Escribe el SKU o el nombre del artículo"; // Texto esperado

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
	            + "Mensaje correcto  : " + MensajeCorrecto);
	    }
    }
 	
 	public void validarmensajearticuloagregadoV() throws InterruptedException
    {
    	Thread.sleep(1000);
        String descripcion = au.driver.findElement(CEDIS_MensajeConfirmacion).getText();
        String textodescripcion = "Ingresaste el siguiente artículo:\n\n"
                + "7501057710155 JAMON VIRGINIA DE PAVO ZWAN 250 GRAMOS\n\n"
                + "¿Deseas continuar?\n";

        if (descripcion.equals(textodescripcion)) {
            System.out.println("Se muestra mensaje de manera correcta");
        } else {
            System.out.println("Se muestra mensaje de manera incorrecta");
            testCase.addTextEvidenceCurrentStep("Se muestra mensaje de manera incorrecta");
        }
    }
 	
 	public void presionarbotonSiDescripcionArticuloConMaximo() throws InterruptedException {
        Thread.sleep(1000);
        au.click(CEDIS_MensajeConfirmacion);
        Thread.sleep(1000);
        //Vanem
        String descripcion = au.driver.findElement(CEDIS_MensajeConfirmacion).getText();
        String textodescripcion = "El artículo no puede contener una cantidad mayor al número de piezas recibidas";

        if (descripcion.equals(textodescripcion)) {
            System.out.println("Se muestra mensaje de manera correcta");
        } else {
            System.err.println("Se muestra mensaje de manera incorrecta");
            testCase.addTextEvidenceCurrentStep("Se muestra mensaje de manera incorrecta");
        }
    }

 	//Metodo para ir directamente a la pantalla de Resumen de Verificación desde Caducos sin registrar artículos caducos
    //Creado por: Jorge Lugo 18/02/2025 
 	public void continuarAResumenSinEvidenciaCaducos() throws InterruptedException {
 	    Thread.sleep(1200);
 	    au.scrollToElement(CEDIS_BotonContinuar);
 	    Thread.sleep(1200);
 	    au.click(CEDIS_BotonContinuar);
 	    Thread.sleep(1200);
 	    au.click(CEDIS_ConfirmarSi);
 	}    
}