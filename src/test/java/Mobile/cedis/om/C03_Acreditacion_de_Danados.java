package Mobile.cedis.om;



import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import junit.framework.Assert;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;


public class C03_Acreditacion_de_Danados {

    private TestCase testCase;
    private AppiumUtil au;
    private BaseExecution baseExecution;


    //============== Identificadores para entrar al modulo acreditación dañados ============//
    //Botones de tutorial y ayuda
    private By CEDIS_BotonDeTutorial = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tutorial");
    private By CEDIS_BotonDeAyuda = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/help");

    // -------------------------------- Identificadores -------------------------------- //
    private By CEDIS_LeyendaListadoDeArticulosDanados = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/text_view_title_items_damage");
    private By CEDIS_LeyendaInstruccionesDeArticulosDanados = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/text_view_title_instructions");
    private By CEDIS_InformacionInstruccionesDeArticulosDanados = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/text_view_body_instruccions");
    private By CEDIS_LeyendaTotalPiezasDeArticulosDanados = By.xpath("//android.widget.TextView[@text=\"Total Piezas:\"]");
    private By CEDIS_InformacionTotalPiezasDeArticulosDanados = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvTotalItems");

    // -------------------------------- Identificadores de información general -------------------------------- //
    private By CEDIS_InfoGeneralDanadosImagenTienda = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/imgResume");
    //Se agregan datos de Ruta y Ruta Dinámica
    private By CEDIS_InfoGeneralDanadosDetalleFecha = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvDateText");
    private By CEDIS_InfoGeneralDanadosFecha = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleDate");
    private By CEDIS_InfoGeneralDanadosRuta = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleRute");
    private By CEDIS_InfoGeneralDanadosRutaDinamica = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleRuteD");
    private By CEDIS_InfoGeneralDanadosCrTienda = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleStore");
    private By CEDIS_InfoGeneralDanadosNombreTienda = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleNameStore");
    private By CEDIS_InfoGeneralDanadosTransferencia = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvTransferNumber");

    //-----Identificador para SKU--------------------------//
    //Vanem
    private By CEDIS_descripcion = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textView\"]");

    // ---- Identificadores para el campo "Ingresar código SKU"  ---- //
    private By CEDIS_BotonIngresarCodigoSKUDeArticulosDanados = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/buttonEnterSkuCode");
    private By CEDIS_CampoIngresarCodigoSKUDeArticulosDanados = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/edtAddDamaged");
    private By CEDIS_PrimerArticuloListadoIngresarCodigoSKUDeArticulosDanados = By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/rvDanadosRec\"]/android.widget.LinearLayout[1]");
    private By CEDIS_MensajeConfirmacionIngresarArticuloDeArticulosDanados = By.id("android:id/message");
    private By CEDIS_BotonSiIngresarArticuloDeArticulosDanados = By.id("android:id/button1");
    private By CEDIS_BotonNoIngresarArticuloDeArticulosDanados = By.id("android:id/button2");

    // ---- Identificadores para los artículos ingresados como "Dañados"  ---- //
    private By CEDIS_NombreArticuloDanadoDeArticulosDanados = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/title_item_description");
    private By CEDIS_BotonEliminarArticuloDanadoDeArticulosDanados = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/title_item_delete");
    private By CEDIS_SKUArticuloDanadoDeArticulosDanados = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/title_item_sku");
    private By CEDIS_BotonDisminuirArticuloDanadoDeArticulosDanados = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/imageMinusItems");
    private By CEDIS_BotonAumentarArticuloDanadoDeArticulosDanados = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/imagePlusItems");
    private By CEDIS_LeyendaEvidenciaDelArticuloDanadoDeArticulosDanados = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/et_photo_layette_header_product_by_package");
    private By CEDIS_MensajeArticulosNoPuedeCantidadMayorPiezasRecibidas = By.id("android:id/message");

    // ---- Identificadores para el campo de ingreso manual de cantidad de articulos dañados  ---- //
    private By CEDIS_CampoIngresaCantidadArticuloDanadoDeArticulosDanados = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/textViewItemsCountVerify");
    private By CEDIS_LeyendaIngresaCantidadArticuloDanadoDeArticulosDanados = By.xpath("//android.widget.TextView[@text=\"Ingresar una cantidad\"]");
    private By CEDIS_CampoIngresaValorCantidadArticuloDanadoDeArticulosDanados = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/input");
    private By CEDIS_BotonConfirmarValorCantidadArticuloDanadoDeArticulosDanados = By.id("android:id/button1");

    // ---- Identificadores de la camara para cuando tomamos foto de los articulos dañados listados ---- //
    private By CEDIS_BotonTomaFotografiaArticuloDeArticulosDanados = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/imageButtonPhotoLayette");
    private By CEDIS_BotonActivarLaTomaDeFotografiaArticuloDanadoDeArticulosDanadosAPK3_10_9_Up = By.id("org.codeaurora.snapcam:id/shutter_button");
    private By CEDIS_BotonActivarLaTomaDeFotografiaArticuloDanadoDeArticulosDanados = By.id("com.android.camera2:id/shutter_button");
    private By CEDIS_BotonActivarLaTomaDeFotografiaArticuloDanadoDeArticulosDanadosTT = By.id("org.codeaurora.snapcam:id/shutter_button");
    private By CEDIS_BotonConfirmarLaTomaDeFotografiaArticuloDanadoDeArticulosDanadosAPK3_10_9_Up = By.id("org.codeaurora.snapcam:id/done_button");
    private By CEDIS_BotonConfirmarLaTomaDeFotografiaArticuloDanadoDeArticulosDanados = By.id("com.android.camera2:id/done_button");
    private By CEDIS_BotonConfirmarLaTomaDeFotografiaArticuloDanadoDeArticulosDanadosTT = By.id("org.codeaurora.snapcam:id/done_button");
    private By CEDIS_BotonCancelarLaTomaDeFotografiaArticuloDanadoDeArticulosDanados = By.id("com.android.camera2:id/cancel_button");
    private By CEDIS_BotonRetomarLaTomaDeFotografiaArticuloDanadoDeArticulosDanados = By.id("com.android.camera2:id/retake_button");

    //--Identificadores que cambiaron en la apk .9 para realizar la accion de tomar la fotografia de articulo dañado se agregan el dia 10/09/2024
    private By CEDIS_ClicBotonTomaFotografiaArticuloDeArticulosDanados = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/imageButtonPhotoLayette");
    private By CEDIS_ClicBotonActivarLaTomaDeFotografiaArticuloDanadoDeArticulosDanados = By.id("org.codeaurora.snapcam:id/shutter_button");
    private By CEDIS_ClicBotonConfirmarLaTomaDeFotografiaArticuloDanadoDeArticulosDanados = By.id("org.codeaurora.snapcam:id/done_button");
    private By Cedis_ClicBotonTomaFotografiaArticuloDeArticulosDanadossegundoarticulo = By.xpath("(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/imageButtonPhotoLayette\"])[2]");

    
    // ---- Identificadores para el botón "Continuar" y pasar a artículos "Caducos" ---- //
    private By CEDIS_BotonContinuarDeArticulosDanados = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/buttonFinishVerifyDamage");
    private By CEDIS_MensajeDeFinalizarArticulosDanadosDeArticulosDanados = By.id("android:id/message");
    private By CEDIS_BotonSiDeMensajeDeFinalizarArticulosDanadosDeArticulosDanados = By.id("android:id/button1");
    private By CEDIS_BotonNoDeMensajeDeFinalizarArticulosDanadosDeArticulosDanados = By.id("android:id/button2");

    private By CEDIS_MensajePreguntandoSiHayArticulosCaducos = By.id("android:id/message");
    private By CEDIS_BotonSiDeMensajePreguntandoSiHayArticulosCaducos = By.id("android:id/button1");
    private By CEDIS_BotonNoDeMensajePreguntandoSiHayArticulosCaducos = By.id("android:id/button2");

    //Vanem
    private By CEDIS_MensajeAlAgregarAritculoDanadoXPath = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\"]");
    private By CEDIS_MensajeAlAgregarArticuloDanado = By.id("android:id/message");
    private By CEDIS_RegistroDanadoAgregado = By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/rvProductByPackageDamage\"]");

    //Ricas
    private
    By CEDIS_BotonTomaSegundaFotografiaArticuloDeArticulosDanados = By.xpath("(//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/imageButtonPhotoLayette\"])[2]");
    
    /*
	 * Constructor de la clase 'C03_Acreditacion_de_Danados'
	 * @param au (AppiumUtil): objeto de la clase 
	 * @param testCase (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 * 
	 * @author Marcelo Gutierrez
	 */

    
    public C03_Acreditacion_de_Danados(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
		this.testCase = testCase;
		this.au = au;
		this.baseExecution = baseExecution;
	}

    private static final Map<Character, AndroidKey> keyMap = new HashMap<>();
    public void KeyEventHelper() {
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
    // Métodos para enviar texto
    public void sendTextUsingKeyEvents(String text) {
            for (char c : text.toCharArray()) {
                AndroidKey key = AndroidKey.valueOf(String.valueOf(c).toUpperCase());
                au.driver.pressKey(new KeyEvent(key));
            }
    }
    public void ValidacionArticulosDesplegados(String text) {
    	
    }
    public void clickEnBotonDeAyuda () {
        au.click(CEDIS_BotonDeAyuda);

    }

    public void clickEnBotonDeTutorial () {

        au.click(CEDIS_BotonDeTutorial);
    }


    public void presionarbotonIngresarCodigoSKU() {
        au.scrollToElement(CEDIS_BotonIngresarCodigoSKUDeArticulosDanados);
        au.click(CEDIS_BotonIngresarCodigoSKUDeArticulosDanados);
    }

//    public void ingresarcodigoSKUmanual() {
//
//        au.click(CEDIS_BotonIngresarCodigoSKUDeArticulosDanados);
//        au.click(CEDIS_CampoIngresarCodigoSKUDeArticulosDanados);
//        au.sendKeys(CEDIS_CajaDeTextoIngresarSkuArticulo, "7501023542209");
//        au.click(CEDIS_PrimerSKUdelListado);
//        au.click(CEDIS_BotonSiParaMensaje);
//
//    }

//    public void noingresarcodigoSKUmanual() {
//
//        au.click(CEDIS_BotonParaIngresarSkuManualmente);
//        au.click(CEDIS_CajaDeTextoIngresarSkuArticulo);
//        au.sendKeys(CEDIS_CajaDeTextoIngresarSkuArticulo, "7501023542209");
//        au.click(CEDIS_PrimerSKUdelListado);
//        au.click(CEDIS_BotonNoParaMensaje);
//
//    }

    public void ingresar3primerasletras(String nombreArticulo) throws InterruptedException {
        au.scrollToElement(CEDIS_BotonIngresarCodigoSKUDeArticulosDanados);
        Thread.sleep(1000);
        au.click(CEDIS_BotonIngresarCodigoSKUDeArticulosDanados);
        Thread.sleep(1000);
        sendTextUsingKeyEvents(nombreArticulo);
    }

//    public void ingresarskupor3letras() {
//        au.sendKeys(CEDIS_CajaDeTextoIngresarSkuArticulo, "BUR");
//        au.click(CEDIS_PrimerSKUdelListado);
//        au.click(CEDIS_BotonSiParaMensaje);
//    }

    public void validardescripcionarticulo () throws IOException {

        String descripcion = au.driver.findElement(CEDIS_NombreArticuloDanadoDeArticulosDanados).getText();
        String textodescripcion = "7501023542209 BURRITO CHATA DUO CHILORIO 140G";

        if (descripcion.equals(textodescripcion)) {
        } else {
            System.out.println("Se muestra mensaje de manera incorrecta");
            //testCase.addTextEvidenceCurrentStep("Se muestra mensaje de manera incorrecta");
        }

//        public void ClickenPantalla(){
//            System.out.println("Dando clic en pantalla");
////            int x = 56;
////            int y = 1126;
////            // Construir el comando ADB
////            String command = "adb shell input tap " + x + " " + y;
////            // Ejecutar el comando
////            Runtime.getRuntime().exec(command);
////            System.out.println("Clic simulado en las coordenadas: (" + x + ", " + y + ")");
}

        public void azulRojo() {
            au.driver.findElement(CEDIS_CampoIngresarCodigoSKUDeArticulosDanados).clear();
            System.out.println("Limpiado caja de texto");
            KeyEventHelper();
            sendTextUsingKeyEvents("750");
        }

        public void inrgesararticulox3num() {
            au.sendKeys(CEDIS_CampoIngresarCodigoSKUDeArticulosDanados, "750");
            au.click(CEDIS_PrimerArticuloListadoIngresarCodigoSKUDeArticulosDanados);
            au.click(CEDIS_BotonSiIngresarArticuloDeArticulosDanados);
        }

    public void tomarfotografiaDanados() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15)); // Espera de 15 segundos

        // Primer click para tomar la fotografía de artículos dañados
        au.click(CEDIS_BotonTomaFotografiaArticuloDeArticulosDanados); // Identificador del botón para tomar fotografía

        try {
            System.out.println("Se intentará usar el identificador de la cámara nuevo");
            Thread.sleep(1000);

            // Intentar hacer clic en el botón para activar la toma de fotografía para versiones anteriores a 3.10.9
            au.click(CEDIS_BotonActivarLaTomaDeFotografiaArticuloDanadoDeArticulosDanadosAPK3_10_9_Up); // Identificador antiguo para activar la toma de foto
        } catch (TimeoutException | NoSuchElementException e) {
            // Si no se encuentra el botón antiguo, mostrar el mensaje y usar el nuevo identificador
            System.out.println("Se intentara con el antiguo.");

            // Intentar esperar y hacer clic en el botón para activar la toma de fotografía para versiones 3.10.9 en adelante
            au.click(CEDIS_BotonActivarLaTomaDeFotografiaArticuloDanadoDeArticulosDanados);
        }

        // Confirmar la toma de la fotografía
        try {
            System.out.println("Se intentará confirmar la toma de la fotografía usando el identificador antiguo.");

            // Intentar hacer clic en el botón para confirmar la toma de fotografía usando el identificador antiguo
            au.click(CEDIS_BotonConfirmarLaTomaDeFotografiaArticuloDanadoDeArticulosDanados); // Identificador antiguo para confirmar la toma de foto
        } catch (TimeoutException | NoSuchElementException e) {
            // Si no se encuentra el botón antiguo, mostrar el mensaje y usar el nuevo identificador
            System.out.println("Se intentó usar el identificador de confirmación anterior, pero no se encontró, se usará el nuevo del Apk 3.10.9 en adelante.");

            // Intentar esperar y hacer clic en el botón de confirmación para versiones 3.10.9 en adelante
            au.click(CEDIS_BotonConfirmarLaTomaDeFotografiaArticuloDanadoDeArticulosDanadosAPK3_10_9_Up);
        }
    }


    public void cancelarfotografiaDanados() {
            au.click(CEDIS_BotonTomaFotografiaArticuloDeArticulosDanados);
            au.click(CEDIS_BotonActivarLaTomaDeFotografiaArticuloDanadoDeArticulosDanados);
            au.click(CEDIS_BotonCancelarLaTomaDeFotografiaArticuloDanadoDeArticulosDanados);
        }

        public void volveratomarfotografiaDanados() {
            au.click(CEDIS_BotonTomaFotografiaArticuloDeArticulosDanados);
            au.click(CEDIS_BotonActivarLaTomaDeFotografiaArticuloDanadoDeArticulosDanados);
            au.click(CEDIS_BotonRetomarLaTomaDeFotografiaArticuloDanadoDeArticulosDanados);
            au.click(CEDIS_BotonActivarLaTomaDeFotografiaArticuloDanadoDeArticulosDanados);
            au.click(CEDIS_BotonConfirmarLaTomaDeFotografiaArticuloDanadoDeArticulosDanados);
        }

        public void aumentarcantidadskuDanados() {

            au.click(CEDIS_BotonAumentarArticuloDanadoDeArticulosDanados);

        }

        public void disminuircantidadskuDanados() {

            au.click(CEDIS_BotonDisminuirArticuloDanadoDeArticulosDanados);

        }

        public void ingresarmanualmentecantidadsku(String cantidad) {

            au.click(CEDIS_CampoIngresaCantidadArticuloDanadoDeArticulosDanados);
            au.sendKeys(CEDIS_CampoIngresarCodigoSKUDeArticulosDanados, cantidad);
        }

        public void eliminarregistroDanados() {

            au.click(CEDIS_BotonEliminarArticuloDanadoDeArticulosDanados);

        }

        public void finalizarDanados() throws InterruptedException {
            au.scrollToElement(CEDIS_BotonContinuarDeArticulosDanados);
            Thread.sleep(3000);
            au.click(CEDIS_BotonContinuarDeArticulosDanados);
            Thread.sleep(3000);
            au.click(CEDIS_BotonSiDeMensajeDeFinalizarArticulosDanadosDeArticulosDanados);
            Thread.sleep(3000);
            au.click(CEDIS_BotonNoDeMensajePreguntandoSiHayArticulosCaducos);
        }

        public void continuarsinevidencia() {

//            ingresarcodigoSKUmanual();
//            au.click(CEDIS_BotonContinuar);
//            au.click(CEDIS_confirmarfaltaevidencia);

        }

        public void mensajecantidadmayordepieza() {

            //ingresarcodigoSKUmanual();
            /* queda pendienteeee */

        }


        public void continuarACaducos() throws InterruptedException {
            Thread.sleep(1200);
            au.click(CEDIS_BotonContinuarDeArticulosDanados);
            Thread.sleep(1200);
            au.click(CEDIS_BotonSiDeMensajeDeFinalizarArticulosDanadosDeArticulosDanados);
            Thread.sleep(1200);
            au.click(CEDIS_BotonSiDeMensajePreguntandoSiHayArticulosCaducos);
        }
        
	//Metodo para ir directamente a la pantalla de Caducos desde Dañados sin registrar artículos dañados
        //Creado por: Jorge Lugo 18/02/2025
        public void continuarACaducosSinEvidenciaDanados() throws InterruptedException {
	    Thread.sleep(1200);
	    au.scrollToElement(CEDIS_BotonContinuarDeArticulosDanados);
	    Thread.sleep(1200);
	    au.click(CEDIS_BotonContinuarDeArticulosDanados);
	    Thread.sleep(1200);
	    au.click(CEDIS_BotonSiDeMensajeDeFinalizarArticulosDanadosDeArticulosDanados);
	    Thread.sleep(1200);
	    au.click(CEDIS_BotonSiDeMensajePreguntandoSiHayArticulosCaducos);
	}    

    public void clickBotonContinuarDanados() throws InterruptedException {
        Thread.sleep(800);
        au.scrollToElement(CEDIS_BotonContinuarDeArticulosDanados);
        au.click(CEDIS_BotonContinuarDeArticulosDanados);
        Thread.sleep(800);
    }

    public void clickBotonSiConfirmarFinalizacionDeDanados() throws InterruptedException {
        Thread.sleep(800);
        au.click(CEDIS_BotonSiDeMensajeDeFinalizarArticulosDanadosDeArticulosDanados);
        Thread.sleep(800);
    }

    public void clickBotonSiPasarACaducosDesdeDanados() throws InterruptedException {
        Thread.sleep(800);
        au.click(CEDIS_BotonSiDeMensajePreguntandoSiHayArticulosCaducos);
        Thread.sleep(800);
    }

    public void validarPantallaDanados() throws InterruptedException {
    	// Descripción: Método para validar el la leyenda de articulos dañados
	    // Autor: Jesús Marcelo Gutiérrez Bravo
	    // Fecha: 12/02/2025
	    
	    // Esperar 1 segundo para la carga de elementos en la página
	    Thread.sleep(1000);
	    
	    // Obtener el mensaje desplegado en la página que indica falta de fotografía
	    WebElement Mensaje = au.driver.findElement(CEDIS_LeyendaListadoDeArticulosDanados);
	    String MensajeDesplegado = Mensaje.getText(); // Capturamos el texto del mensaje actual en pantalla
	    String MensajeCorrecto = "LISTADO DE ARTÍCULOS DAÑADOS"; // Texto esperado
 
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
    	case "Boton Ingresar Codigo SKU":
    		au.scrollToElement(CEDIS_BotonIngresarCodigoSKUDeArticulosDanados);
    		au.click(CEDIS_BotonIngresarCodigoSKUDeArticulosDanados);
            break; 
    	case "Boton U":
    		au.scrollToElement(CEDIS_BotonIngresarCodigoSKUDeArticulosDanados);
    		au.click(CEDIS_BotonIngresarCodigoSKUDeArticulosDanados);
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
	    WebElement Mensaje = au.driver.findElement(CEDIS_CampoIngresarCodigoSKUDeArticulosDanados);
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
    
    public void ValidarMensajeAlClickBotonContinuar() throws InterruptedException {
    	// Descripción: Método para validar el texto del mensaje para confirmar terminar el proceso de dañados
	    // Autor: Jesús Marcelo Gutiérrez Bravo
	    // Fecha: 12/02/2025
	    
	    // Esperar 1 segundo para la carga de elementos en la página
	    Thread.sleep(1000);
	    
	    // Obtener el mensaje desplegado en la página que indica falta de fotografía
	    WebElement Mensaje = au.driver.findElement(CEDIS_MensajeConfirmacionIngresarArticuloDeArticulosDanados);
	    String MensajeDesplegado = Mensaje.getText(); // Capturamos el texto del mensaje actual en pantalla
	    String MensajeCorrecto = "¿Ha terminado la Devolución de Artículo Dañados?"; // Texto esperado

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
    
    public void ValidarMensajeAlClickBotonConfirmarPasarACaducos() throws InterruptedException {
    	// Descripción: Método para validar el texto del mensaje para confirmar terminar el proceso de dañados
	    // Autor: Jesús Marcelo Gutiérrez Bravo
	    // Fecha: 12/02/2025
	    
	    // Esperar 1 segundo para la carga de elementos en la página
	    Thread.sleep(1000);
	    
	    // Obtener el mensaje desplegado en la página que indica falta de fotografía
	    WebElement Mensaje = au.driver.findElement(CEDIS_MensajeConfirmacionIngresarArticuloDeArticulosDanados);
	    String MensajeDesplegado = Mensaje.getText(); // Capturamos el texto del mensaje actual en pantalla
	    String MensajeCorrecto = "¿Hay artículos Caducos en este Pedido?"; // Texto esperado

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

    public void validacionInstruccionesDanados() throws InterruptedException{
    	/*Funcion que permite validar la descripción del texto en las intrucciones  	
    	Creado: Juan Ibarra		    	
    	 */    	
    	Thread.sleep(1000);
        WebElement elemento = au.driver.findElement(CEDIS_InformacionInstruccionesDeArticulosDanados);
        String mensajeMostrado1 = elemento.getText().trim(); // Eliminar espacios al inicio y al final
        // Texto esperado
        String txtWarning1 = "1. Escanea o da clic al botón \"Ingresar código SKU\" para registrar cada uno de los artículos dañados. 2. Coloca la cantidad de productos que recibiste. 3. Toma fotografía de cada uno de los artículos dañados.";

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

    /* METODO CREADO
     * Descripción: Comprueba que se está en la pantalla de Dañados* y esta tiene la información
     * @autor Vanessa
     * @date 15/08/2024
     */
    public void pantallaDanados() throws InterruptedException {
        //Vanem
        System.out.println("Validando elementos info general...");
        au.driver.findElement(CEDIS_InfoGeneralDanadosFecha).isDisplayed();
        au.driver.findElement(CEDIS_InfoGeneralDanadosRuta).isDisplayed();
        au.driver.findElement(CEDIS_InfoGeneralDanadosCrTienda).isDisplayed();
        au.driver.findElement(CEDIS_InfoGeneralDanadosNombreTienda).isDisplayed();
        au.driver.findElement(CEDIS_InfoGeneralDanadosTransferencia).isDisplayed();
        System.out.println("Información general se muestra.");
        au.scrollToElement(CEDIS_BotonIngresarCodigoSKUDeArticulosDanados);
        System.out.println("Validando elementos: Instrucciones,boton INGRESAR SKU , Total Piezas...");
        au.driver.findElement(CEDIS_LeyendaListadoDeArticulosDanados).isDisplayed();
        au.driver.findElement(CEDIS_InformacionInstruccionesDeArticulosDanados).isDisplayed();
        au.driver.findElement(CEDIS_BotonIngresarCodigoSKUDeArticulosDanados).isDisplayed();
        System.out.println("Información se muestra.");
        System.out.println("Validando botones y cajas de texto...");
        au.scrollToElement(CEDIS_BotonContinuarDeArticulosDanados);
        au.driver.findElement(CEDIS_InformacionTotalPiezasDeArticulosDanados).isDisplayed();
        au.driver.findElement(CEDIS_BotonContinuarDeArticulosDanados).isDisplayed();
        System.out.println("Caja de texto y botones se muestran.");
    }

    /* METODO CREADO
     * Descripción: Comprueba que se despliega el botón: "Ingresar Código SKU"
     * @autor Vanessa
     * @date 15/08/2024
     */
    public void siexisteTXT() throws InterruptedException {
        //Vanem
    	Thread.sleep(1000);
        au.driver.findElement(CEDIS_CampoIngresarCodigoSKUDeArticulosDanados).isDisplayed();
        System.out.println("La caja de texto existe.");
    }

    /* METODO CREADO
     * Descripción: Ingresa las tres primeras letras
     * @autor Vanessa
     * @date 15/08/2024
     */
    public void ingresar3primerasletrasV() {
        //Vanem
        sendTextUsingKeyEvents("TRI");
    }
    public void ingresar3primerasletrasJAM() {
        //Vanem
        sendTextUsingKeyEvents("JAM");
    }

    /* METODO CREADO
     * Descripción: Validar descripcion de articulos ingresados como dañados, no cuando estan listados
     * @autor Vanessa
     * @date 15/08/2024
     */
    public void validardescripcionarticuloV() throws InterruptedException {
        //Vanem
    	Thread.sleep(1000);
        String descripcion = au.driver.findElement(CEDIS_descripcion).getText();
        String textodescripcion = "7501057710155 JAMON VIRGINIA DE PAVO ZWAN 250 GRAMOS";

        if (descripcion.equals(textodescripcion)) {
            System.out.println("Se muestra mensaje de manera correcta");
        } else {
            System.out.println("Se muestra mensaje de manera incorrecta");
            testCase.addTextEvidenceCurrentStep("Se muestra mensaje de manera incorrecta");
        }
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

    /* METODO CREADO
     * Descripción: Limpiar campo de ingreso de SKUs y Enviar números , no texto.
     * @autor Vanessa
     * @date 14/08/2024
     */
    public void ingresar3primerosnumerosV() {
        //Vanem
        au.driver.findElement(CEDIS_CampoIngresarCodigoSKUDeArticulosDanados).clear();
        System.out.println("Limpiado caja de texto");
        KeyEventHelper();
        sendTextUsingKeyEventsV("750");
    }

    /* METODO CREADO
     * Descripción: Ingresa nombre del producto
     * @autor Vanessa
     * @date 15/08/2024
     */
    public void ingresarnombrearticuloV() throws InterruptedException {
        //Vanem
    	Thread.sleep(1000);
        sendTextUsingKeyEvents("JAMON");
    }

    public void validarmensajearticuloagregadoV() throws InterruptedException
    {
    	Thread.sleep(1000);
        String descripcion = au.driver.findElement(CEDIS_MensajeAlAgregarArticuloDanado).getText();
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
    
    public void ValidarMensajeArticuloConMaximoIngresado() throws InterruptedException
    {
    	Thread.sleep(1000);
        String descripcion = au.driver.findElement(CEDIS_MensajeAlAgregarArticuloDanado).getText();
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

    public void presionarbotonIngresarCodigoSkuV() throws InterruptedException {
    	Thread.sleep(1000);
        au.scrollToElement(CEDIS_BotonIngresarCodigoSKUDeArticulosDanados);
        au.click(CEDIS_BotonIngresarCodigoSKUDeArticulosDanados);
    }

    public void seleccionarsiV() throws InterruptedException {
    	Thread.sleep(1000);
        au.click(CEDIS_BotonSiIngresarArticuloDeArticulosDanados);

    }

    public void validadregistroV() {
        au.driver.findElement(CEDIS_RegistroDanadoAgregado).isDisplayed();
        System.out.println("Se muestra registro");
        au.driver.findElement(CEDIS_BotonTomaFotografiaArticuloDeArticulosDanados).isDisplayed();
        System.out.println("Se muestra boton para evidencia");
        au.driver.findElement(CEDIS_CampoIngresaCantidadArticuloDanadoDeArticulosDanados).isDisplayed();
        System.out.println("Se muestra campo para ingresar cantidad");
    }

    /* METODO CREADO
     * Descripción: Método para dar clic sobre el primer articulo dañado listado
     * @autor Ricardo Hdz
     * @date 19/08/2024
     */
    public void clicPrimerArticuloListado() throws InterruptedException {
        // Ricardo
        Thread.sleep(1000);
        au.click(CEDIS_PrimerArticuloListadoIngresarCodigoSKUDeArticulosDanados);
        Thread.sleep(1000);
    }

    /* METODO CREADO
     * Descripción: Presionar SI para dar ingreso a articulo dañado
     * @autor Ricardo
     * @date 19/08/2024
     */
    public void presionarbotonSiDescripcionArticulo() {
        au.click(CEDIS_BotonSiIngresarArticuloDeArticulosDanados);
        au.scrollToElement(CEDIS_BotonContinuarDeArticulosDanados);
    }

    /* METODO CREADO
     * Descripción: Presionar NO para no darle ingreso al articulo dañado
     * @autor Ricardo
     * @date 19/08/2024
     */
    public void presionarbotonNoDescripcionArticulo() throws InterruptedException {
    	Thread.sleep(1000);        
    	au.click(CEDIS_BotonNoIngresarArticuloDeArticulosDanados);
        au.scrollToElement(CEDIS_BotonContinuarDeArticulosDanados);
    }

    /* METODO CREADO
     * Descripción: Presionar SI para dar ingreso a articulo dañado
     * @autor Ricardo
     * @date 19/08/2024
     */
    public void presionarbotonSiDescripcionArticuloConMaximo() throws InterruptedException {
        Thread.sleep(1000);
        au.click(CEDIS_BotonSiIngresarArticuloDeArticulosDanados);
        Thread.sleep(1000);
        //Vanem
        String descripcion = au.driver.findElement(CEDIS_MensajeArticulosNoPuedeCantidadMayorPiezasRecibidas).getText();
        String textodescripcion = "El artículo no puede contener una cantidad mayor al número de piezas recibidas";

        if (descripcion.equals(textodescripcion)) {
            System.out.println("Se muestra mensaje de manera correcta");
        } else {
            System.err.println("Se muestra mensaje de manera incorrecta");
            testCase.addTextEvidenceCurrentStep("Se muestra mensaje de manera incorrecta");
        }
    }

    /* METODO CREADO
     * Descripción: Metodo para que tome la segunda fotografia de un articulo dañado
     * @autor Ricardo
     * @date 20/08/2024
     */
    public void tomarfotografiasegundoarticuloDanados() {
        au.scrollToElement(CEDIS_BotonTomaSegundaFotografiaArticuloDeArticulosDanados);
        au.click(CEDIS_BotonTomaSegundaFotografiaArticuloDeArticulosDanados);
        au.click(CEDIS_BotonActivarLaTomaDeFotografiaArticuloDanadoDeArticulosDanados);
        au.click(CEDIS_BotonConfirmarLaTomaDeFotografiaArticuloDanadoDeArticulosDanados);
    }
    
    public void tomarfotografiasegundoarticuloDanadosTT() throws InterruptedException {
        au.scrollToElement(CEDIS_BotonTomaSegundaFotografiaArticuloDeArticulosDanados);
        au.click(CEDIS_BotonTomaSegundaFotografiaArticuloDeArticulosDanados);
        Thread.sleep(1000);
        au.click(CEDIS_BotonActivarLaTomaDeFotografiaArticuloDanadoDeArticulosDanadosTT);
        Thread.sleep(1000);
        au.click(CEDIS_BotonConfirmarLaTomaDeFotografiaArticuloDanadoDeArticulosDanadosTT);
    }

    /* METODO CREADO
     * Descripción: Metodo para validar el texto de las instrucciones de dañados de tablet
     * @autor Ricardo
     * @date 30/08/2024
     */
    public void validacionInstruccionesDanadosTablet() { //ricardo 30/08/2024
        // Obtener el texto mostrado en la aplicación
        WebElement elemento = au.driver.findElement(CEDIS_InformacionInstruccionesDeArticulosDanados);
        String mensajeMostrado = elemento.getText().trim(); // Eliminar espacios al inicio y al final
        // Texto esperado
        String txtWarning =
                "1. Escanea o da clic al botón \"Ingresar código SKU\" para registrar cada uno de los artículos dañados.\r\n"
                        + "2. Coloca la cantidad de productos que recibiste.\r\n"
                        + "3. Toma fotografía de cada uno de los artículos dañados.";

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
     * Descripción: Método para validar que al dar clic en NO al querer ingresar un articulo dañado se quite la ventana
     * @autor Vanessa
     * @date 03/09/2024
     */
    public void validaringresarnuevamenteskuV() {
        try {
            boolean mensaje = au.driver.findElement(CEDIS_MensajeAlAgregarAritculoDanadoXPath).isDisplayed();
            if (mensaje!=true) {
                System.out.print("Se quita el mensaje correctamente");
                testCase.addTextEvidenceCurrentStep("Se quita el mensaje");
            } else {
                System.out.print("Se sigue mostrando");
            }
        } catch (NoSuchElementException e) {
            System.out.print("El elemento no se encontró en la página y es correcto.\n");
        }

    }

    /* METODO CREADO
     * Descripción: metodo actualizado de identificadores que cambiaron en la apk .9 para realizar la accion de tomar la fotografia de articulo dañado se agregan el dia 09/09/2024

     * @autor Ricardo
     * @date 09/09/2024
     */
    
    public void tomarfotografiaDanadosactualizado() throws InterruptedException {
    	Thread.sleep(3000);
        au.click(CEDIS_ClicBotonTomaFotografiaArticuloDeArticulosDanados);
        au.click(CEDIS_ClicBotonActivarLaTomaDeFotografiaArticuloDanadoDeArticulosDanados);
        au.click(CEDIS_ClicBotonConfirmarLaTomaDeFotografiaArticuloDanadoDeArticulosDanados);
    }

    
    
    /* METODO CREADO
     * Descripción: Ingresa las tres primeras letras
     * @autor Ricardo
     * @date 11/09/2024
     */
    public void ingresar3primerasletrasS() {
       
        sendTextUsingKeyEvents("JAM");
    }
    
    /* METODO CREADO
     * Descripción: metodo agregado para realizar la toma de la fotografia del segundo articulo agregado
     * @autor Ricardo
     * @date 11/09/2024
     */
    
    public void tomarfotografiaDanadossegundoarticulo() throws InterruptedException {
    	Thread.sleep(3000);
        au.click( Cedis_ClicBotonTomaFotografiaArticuloDeArticulosDanadossegundoarticulo);
        au.click(CEDIS_ClicBotonActivarLaTomaDeFotografiaArticuloDanadoDeArticulosDanados);
        au.click(CEDIS_ClicBotonConfirmarLaTomaDeFotografiaArticuloDanadoDeArticulosDanados);
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
	
	
	//Autor: Jorge Alberto Lugo Martinez 11/02/2025
	//Metodo para validar la información del encabezado
	public void validarEncabezadoRutasDanados() throws InterruptedException
	{
		if(au.driver.findElement(CEDIS_InfoGeneralDanadosImagenTienda).isEnabled()==false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el Logo de Oxxo de la pantalla de ARTÍCULOS DAÑADOS en la parte de inicio");
    	}    	
    	if (au.driver.findElements(CEDIS_InfoGeneralDanadosFecha).isEmpty()!= false) {    		
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el dato de la Fecha de la pantalla de ARTÍCULOS DAÑADOS en la parte de inicio");
    	}    	
    	if(au.driver.findElements(CEDIS_InfoGeneralDanadosRuta).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el dato de la Ruta de la pantalla de ARTÍCULOS DAÑADOS en la parte de inicio");
    	}
    	if(au.driver.findElements(CEDIS_InfoGeneralDanadosRutaDinamica).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el dato de la Ruta Dinamica de la pantalla de ARTÍCULOS DAÑADOS en la parte de inicio");
    	}
    	if(au.driver.findElements(CEDIS_InfoGeneralDanadosCrTienda).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el dato del CR de Tienda de la pantalla de ARTÍCULOS DAÑADOS en la parte de inicio");
    	}
    	if(au.driver.findElements(CEDIS_InfoGeneralDanadosNombreTienda).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el dato del nombre de la Tienda de la pantalla de ARTÍCULOS DAÑADOS en la parte de inicio");
    	}
    	if(au.driver.findElements(CEDIS_InfoGeneralDanadosTransferencia).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el dato del número de la transferencia de la pantalla de ARTÍCULOS DAÑADOS en la parte de inicio");
    	}
	}

}
