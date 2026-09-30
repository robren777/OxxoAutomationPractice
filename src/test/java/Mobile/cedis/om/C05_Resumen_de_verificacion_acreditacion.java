package Mobile.cedis.om;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

////////////////

public class C05_Resumen_de_verificacion_acreditacion {
    private TestCase testCase;
    private AppiumUtil au;
    private BaseExecution baseExecution;

    // Opcion Navegacion
    private By CEDIS_LeyendaVerificacionMercancia = By.xpath("//android.widget.TextView[@text=\"VERIFICACIÓN DE MERCANCÍA PEDIDO CEDIS\"]");
    private By CEDIS_IconoTienda = By.xpath("//android.widget.ImageView[@content-desc=\"OXXO al día\"]");
    private By CEDIS_LeyendaFecha = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvDateText\"]");
    private By CEDIS_ValorFecha = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleDate\"]");
    private By CEDIS_ResumenRuta = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleRute");
    private By CEDIS_ResumenRutaDinamica = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleRuteD");
    private By CEDIS_LeyendaCRTienda = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleStore\"]");
    private By CEDIS_LeyendaTienda = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleNameStore\"]");
    private By CEDIS_LeyendaTransferencia = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTransferNumber\"]");
    private By CEDIS_LeyendaResumen = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/text_view_title_resume\"]");

    // Secciónde Listado Articulos Faltantes
    private By CEDIS_LeyListArtFaltantes = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/text_view_title_list_missing\"]");
    private By CEDIS_LeyArtFaltantes = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_item_title_sku\" and @text=\"ARTÍCULO\"]");
    private By CEDIS_LeyDesFaltantes = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_item_title_description\" and @text=\"DESCRIPCIÓN\"]");
    private By CEDIS_LeyCantFaltantes = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_item_title_count\" and @text=\"CANTIDAD\"]");
    private By CEDIS_LeyEvidFaltantes = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_item_title_verify\" and @text=\"EVIDENCIA\"]");
    private By CEDIS_LeyCanSecFaltantes = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_header_title_returned\" and @elementId=\"00000000-0000-3a56-ffff-ffff00000b3e\"]");
    private By CEDIS_ArtNuSecFaltantes = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_item_title_sku\" and @elementId=\"00000000-0000-3a56-ffff-ffff00000b43\"]");
    private By CEDIS_DescSecFaltantes = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_item_title_description\" and @elementId=\"00000000-0000-3a56-ffff-ffff00000b44\"]");
    private By CEDIS_BtnEvidSecFaltantes = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_header_evidence_returned\" and @text=\"Evidencia\"]");
    private By CEDIS_CerrarEvidenciaFaltantes = By.xpath(
            "//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/close_alert_image\"]");
    private By CEDIS_ImagenEvidenciaFaltantes = By.xpath(
            "//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/image_online_repository\"]");
    private By CEDIS_LeyCanRefFaltantes = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_header_title_returned\" and @elementId=\"00000000-0000-3a56-ffff-ffff00000b4a\"]");
    private By CEDIS_LeyTotArtFaltantes = By.xpath(
            "//android.widget.TextView[@text=\"Total Piezas:\" and elementId=\"00000000-0000-3a56-ffff-ffff00000ad7\"]");
    private By CEDIS_TotalFaltantes = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTotalItemsMissing\"]");

    // Secciónde Listado Articulos Sobrantes
    private By CEDIS_LeyListArtSobrantes = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/text_view_title_list_returned\" and elementId=\"00000000-0000-3a56-ffff-ffff00000ada\"]");
    private By CEDIS_LeyArtSobrantes = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_item_title_sku\" and @elementId=\"00000000-0000-3a56-ffff-ffff00000ade\"]");
    private By CEDIS_LeyDesSobrantes = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_item_title_description\" and @elementId=\"00000000-0000-3a56-ffff-ffff00000adf\"]");
    private By CEDIS_LeyCantSobrantes = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_item_title_count\" and @elementId=\"00000000-0000-3a56-ffff-ffff00000ae0\"]");
    private By CEDIS_LeyEvidSobrantes = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_item_title_verify\" and @text=\"EVIDENCIA\"]");
    private By CEDIS_LeyCanSecSobrantes = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_header_title_returned\" and @elementId=\"00000000-0000-3a56-ffff-ffff00000b0a\"]");
    private By CEDIS_ArtNuSecSobrantes = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_item_title_sku\" and @elementId=\"00000000-0000-3a56-ffff-ffff00000b0f\"]");
    private By CEDIS_DescSecSobrantes = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_item_title_description\" and @elementId=\"00000000-0000-3a56-ffff-ffff00000b10\"]");
    private By CEDIS_BtnEvidSecSobrantes = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_header_evidence_returned\" and @elementId=\00000000-0000-3a56-ffff-ffff00000b0b\"]");
    private By CEDIS_CerrarEvidenciaSobrantes = By.xpath(
            "//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/close_alert_image\"]");
    private By CEDIS_ImagenEvidenciaSobrantes = By.xpath(
            "//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/image_online_repository\"]");
    private By CEDIS_LeyTotArtSobrantes = By.xpath(
            "//android.widget.TextView[@text=\"Total Piezas:\" and elementId=\"00000000-0000-3a56-ffff-ffff00000ae4\"]");
    private By CEDIS_TotalSobrantes = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTotalItemsReturned\"]");

    // Secciónde Listado Articulos Dañados
    private By CEDIS_LeyListArtDanados = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/text_view_title_list_damage\" and elementId=\"00000000-0000-3a56-ffff-ffff00000ae7\"]");
    private By CEDIS_LeyArtDanados = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_item_title_sku\" and @elementId=\"00000000-0000-3a56-ffff-ffff00000aeb\"]");
    private By CEDIS_LeyDesDanados = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_item_title_description\" and @elementId=\"00000000-0000-3a56-ffff-ffff00000aec\"]");
    private By CEDIS_LeyCantDanados = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_item_title_count\" and @elementId=\"00000000-0000-3a56-ffff-ffff00000aed\"]");
    private By CEDIS_LeyEvidDanados = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_item_title_verify\" and @elementId=\"00000000-0000-3a56-ffff-ffff00000aee\"]");
    private By CEDIS_ArtNuSecDanados = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_item_title_sku\" and @elementId=\"00000000-0000-3a56-ffff-ffff00000b30\"]");
    private By CEDIS_DescSecDanados = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_item_title_description\" and @elementId=\"00000000-0000-3a56-ffff-ffff00000b31\"]");
    private By CEDIS_CantSecDanados = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_item_title_count\" and @elementId=\"00000000-0000-3a56-ffff-ffff00000b32\"]");
    private By CEDIS_BtnEvidSecDanados = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_item_title_evidence\" and @elementId=\"00000000-0000-3a56-ffff-ffff00000b33\"]");
    private By CEDIS_CerrarEvidenciaDanados = By.xpath(
            "//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/close_alert_image\"]");
    private By CEDIS_ImagenEvidenciaDanados = By.xpath(
            "//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/image_online_repository\"]");
    private By CEDIS_LeyTotArtDanados = By.xpath(
            "//android.widget.TextView[@text=\"Total Piezas:\" and elementId=\"00000000-0000-3a56-ffff-ffff00000af1\"]");
    private By CEDIS_TotalDanados = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTotalItemsDamage\"]");

    // Secciónde Listado Articulos Caducos
    private By CEDIS_LeyListArtCaducos = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/text_view_title_list_expired\"]");
    private By CEDIS_LeyArtCaducos = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_item_title_sku\" and @elementId=\"00000000-0000-3a56-ffff-ffff00000af8\"]");
    private By CEDIS_LeyDescCaducos = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_item_title_description\" and @elementId=\"00000000-0000-3a56-ffff-ffff00000af9\"]");
    private By CEDIS_LeyCantCaducos = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_item_title_count\" and @elementId=\"00000000-0000-3a56-ffff-ffff00000afa\"]");
    private By CEDIS_LeyEviCaducos = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_item_title_verify\" and @elementId=\"00000000-0000-3a56-ffff-ffff00000afb\"]");
    private By CEDIS_ArtCaducos = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_item_title_sku\" and @elementId=\"00000000-0000-3a56-ffff-ffff00000b22\"]");
    private By CEDIS_DescCaducos = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_item_title_description\" and @elementId=\"00000000-0000-3a56-ffff-ffff00000b23\"]");
    private By CEDIS_LeyTotalCaducos = By.xpath(
            "//android.widget.TextView[@text=\"Total Piezas:\" and @elementId=\"00000000-0000-3a56-ffff-ffff00000afe\"]");
    private By CEDIS_CantCaducos = By.xpath(
            "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTotalItemsExpired\"]");

    private By CEDIS_BtnContinuar = By.xpath(
            "//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/buttonFinishVerifyResume\"]");
    private By CEDIS_BtnModDanCadu = By.xpath(
            "//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/buttonEditDamageExpiredResume\"]");
    private By CEDIS_LeyendaModDanCadu = By.xpath(
            "//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"¿Desea modificar los articulos Dañados y Caducos?\"]");
    private By CEDIS_BtnNoModDanCadu = By
            .xpath("//android.widget.Button[@resource-id=\"android:id/button2\" and @text=\"NO\"]");
    private By CEDIS_BtnSiModDanCadu = By
            .xpath("//android.widget.Button[@resource-id=\"android:id/button1\" and @text=\"SI\"]");
    
    private By CEDIS_MensajeModDanCadu = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\"]");

    private By CEDIS_LeyendaDevolArt = By.xpath(
            "//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"¿Ha terminado la Devolución de Artículos?\"]");
    private By CEDIS_BtnNoDevolArt = By
            .xpath("//android.widget.Button[@resource-id=\"android:id/button2\" and @text=\"NO\"]");
    private By CEDIS_BtnSiDevolArt = By
            .xpath("//android.widget.Button[@resource-id=\"android:id/button1\" and @text=\"SI\"]");
    private By CEDIS_ProgresoBarra = By.xpath(
            "//android.widget.ProgressBar[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/progressBar\"]");
    private By CEDIS_LndVerifExitNoDev = By.xpath(
            "//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"La Verificación fue realizada con éxito y no hay devoluciones.\"]");
    private By CEDIS_Finalizar = By
            .xpath("//android.widget.Button[@resource-id=\"android:id/button1\" and @text=\"FINALIZAR\"]");
    private By CEDIS_BtnSI = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]");
    private By CEDIS_BtnNO = By.xpath("//android.widget.Button[@resource-id=\"android:id/button2\"]");

    /**
     * Constructor
     ******************************************************************************************/
    /**
     * Constructor de la clase Menus_y_submenus
     * 
     * @author Juan Ibarra
     * @param au            (AppiumUtil): objeto de la clase
     * @param testCase      (TestCase): objeto de la clase
     * @param baseExecution (BaseExecution): objeto de la clase
     */

    public C05_Resumen_de_verificacion_acreditacion(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
        this.testCase = testCase;
        this.au = au;
        this.baseExecution = baseExecution;
    }

    public void modificarDanadoCaducos(String opcionSiNo, String tomaEvidencia, String descPaso)
            throws InterruptedException {
        /**
         * Método - Botón Modificar Dañados y Caducos
         *
         * @param opcionSiNo    Recibe Si o No para el botón que se seleccionara en la
         *                      pantalla
         * @param tomaEvidencia Se confirma con el valor de tomaEvidencia capturando la
         *                      evidencia del mensaje ¿Desea modificar los articulos
         *                      Dañados y Caducos?
         * @param descPaso      Se recibe la descripción del caso de prueba a colocar en la evidencia, 
         * 						tomando en cuenta solo cuando si se tome evidencia 
         *
         *                      
         */

        au.scrollToElement(CEDIS_BtnModDanCadu);
        au.click(CEDIS_BtnModDanCadu);
        if (tomaEvidencia.equals("tomaEvidencia")) {
            if (opcionSiNo.equals("Si")) {
                baseExecution.addStep(descPaso);
                Thread.sleep(2000);
                testCase.addScreenShotCurrentStep(au, "testImage");
                au.click(CEDIS_BtnSiModDanCadu);
            } else {
                baseExecution.addStep(descPaso);
                Thread.sleep(2000);
                testCase.addScreenShotCurrentStep(au, "testImage");
                au.click(CEDIS_BtnNoModDanCadu);
            }
        } else {
            if (opcionSiNo.equals("Si")) {
                au.click(CEDIS_BtnSiModDanCadu);
            } else {
                au.click(CEDIS_BtnNoModDanCadu);
            }
        }
    }

    public void Continuar(String opcionSiNo, String tomaEvidencia, String descPaso) throws InterruptedException {
        /**
         * Método - Botón Continuar
         *
         * @param opcionSiNo    Recibe Si o No para el botón que se seleccionara en la
         *                      pantalla
         * @param tomaEvidencia Se confirma con el valor de tomaEvidencia capturando la
         *                      evidencia del mensaje ¿Ha terminado la Devolución de Artículos?
         * @param descPaso      Se recibe la descripción del caso de prueba a colocar en la evidencia, 
         * 						tomando en cuenta solo cuando si se tome evidencia
         */

        au.scrollToElement(CEDIS_BtnContinuar);
        au.click(CEDIS_BtnContinuar);
        if (tomaEvidencia.equals("tomaEvidencia")) {
            if (opcionSiNo.equals("Si")) {
                baseExecution.addStep(descPaso);
                Thread.sleep(2000);
                testCase.addScreenShotCurrentStep(au, "testImage");
                au.click(CEDIS_BtnSiDevolArt);
            } else {
                baseExecution.addStep(descPaso);
                Thread.sleep(2000);
                testCase.addScreenShotCurrentStep(au, "testImage");
                au.click(CEDIS_BtnNoDevolArt);
            }
        } else {
            if (opcionSiNo.equals("Si")) {
                au.click(CEDIS_BtnSiDevolArt);
            } else {
                au.click(CEDIS_BtnNoDevolArt);
            }
        }
    }

    public void barraProceso() {
        au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        int intentos = 0;
        boolean barra = au.driver.findElement(CEDIS_ProgresoBarra).isDisplayed();
        if (barra = true) {
            while (barra = true) {
                intentos++;
                au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                if (intentos > 12) {
                    baseExecution.addStep("La pantalla sigue cargando la barra de procesos");//Se agrega el paso a mostrar en el PDF
            		Assert.fail("No permite continuar al seguir cargando la barra de procesos", null);//Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria leyenda de null            		
                }
            }
        }
    }

    public void validarBotonesvalidarMensajeEnResumen(String opcionSiNo, Boolean tomaEvidenciaBoton, Boolean tomaEvidenciaClick, String descPasoBoton, String descPasoEsperadoBoton, String descPasoClick, String descPasoEsperadoClick)
            throws InterruptedException {
        /**
         * Método - Validar que se vea el Botón: "Modificar Dañados y Caducos" (Con pasos y evidencia - Boton) y tambien
         * Validar que al darle click en este botón se despliegue la ventanita correspondiente (Con pasos y evidencia - Click)
         *
         * @autor Marcelo Gutiérrez
         *
         * @param opcionSiNo "Si" o "No" para que opcion se le dara al mensaje de modificar dañados y caducos
         * @param tomaEvidenciaBoton True para tomar la foto donde se vea el botón
         * @param tomaEvidenciaClick True para tomar la foto al mensaje cuando se le da click al botón
         * @param descPasoBoton Aqui pones el paso para cuando necesitas tomor foto que se vea el botón
         * @param descPasoEsperadoBoton Aqui pones el resultado esperado para cuando necesitar tomor foto que se vea el botón
         * @param descPasoClick Aqui pones el paso para cuando necesitas tomor foto que se vea el mensaje
         * @param descPasoEsperadoClcik Aqui pones el resultado esperado para cuando necesitar tomor foto que se vea el mensaje
         */

        // Desplazar hasta que el botón sea visible
        au.scrollToElement(CEDIS_BtnModDanCadu);

        // Validar si se requiere tomar evidencia de la visibilidad del botón
        if (tomaEvidenciaBoton) {
            baseExecution.addStep(descPasoBoton, descPasoEsperadoBoton);
            testCase.addScreenShotCurrentStep(au, "testImage");
        }

        // Validar si se requiere tomar evidencia del clic y seleccionar la opción correspondiente
        if (tomaEvidenciaClick) {
            baseExecution.addStep(descPasoClick, descPasoEsperadoClick);
            au.click(CEDIS_BtnModDanCadu);
            Thread.sleep(1000);
            testCase.addScreenShotCurrentStep(au, "testImage");
            // Ejecutar la acción según la opción seleccionada
            if (opcionSiNo.equals("Si")) {
                au.click(CEDIS_BtnSiModDanCadu);
            } else {
                au.click(CEDIS_BtnNoModDanCadu);
            }
        } else {
            au.click(CEDIS_BtnModDanCadu);
            Thread.sleep(1000);
            // Ejecutar la acción según la opción seleccionada
            if (opcionSiNo.equals("Si")) {
                au.click(CEDIS_BtnSiModDanCadu);
            } else {
                au.click(CEDIS_BtnNoModDanCadu);
            }
        }
    }
    
    public void ValidacionBotonModDanadosYCaducos() throws IOException, InterruptedException {
    	  //Descripcion: Método para validar el despliegue correcto del boton Modificar Dañados y Caducos
    	  //Autor: Jesus Marcelo Gutierrez Bravo
    	  Thread.sleep(3000);
    	  au.scrollToElement(CEDIS_BtnModDanCadu);
    	  Thread.sleep(1000);
    	  if(au.driver.findElement(CEDIS_BtnModDanCadu).isEnabled() == false) {
      		baseExecution.addStep("No se ha encontrado el boton Modificar Dañados y Caducos");        
      		Assert.fail("No se ha encontrado el boton Modificar Dañados y Caducos", null);
    		testCase.addScreenShotCurrentStep(au, "testImage");
      	} 
      }
    
    public void ValidacionMensajeModDanadosYCaducos() throws IOException, InterruptedException {
	    // Descripción: Método para validar el texto del mensaje cuando queremos modificar dañados y caducos.
	    // Autor: Jesús Marcelo Gutiérrez Bravo
	    
	    // Esperar 1 segundo para la carga de elementos en la página
	    Thread.sleep(1000);
	    
	    // Obtener el mensaje desplegado en la página que indica falta de fotografía
	    WebElement Mensaje = au.driver.findElement(CEDIS_LeyendaModDanCadu);
	    String MensajeDesplegado = Mensaje.getText(); // Capturamos el texto del mensaje actual en pantalla
	    String MensajeCorrecto = "¿Desea modificar los articulos Dañados y Caducos?"; // Texto esperado

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
    
    public void ValidacionMensajeModificarDanadosYCaducos() throws IOException, InterruptedException {
	    // Descripción: Método para validar el texto del mensaje de advertencia
	    // que indica que si queremos modificar dañados y caducos
	    // Autor: Jesús Marcelo Gutiérrez Bravo
	    // Fecha: 12/02/2025
	    
	    // Esperar 1 segundo para la carga de elementos en la página
	    Thread.sleep(1000);
	    
	    // Obtener el mensaje desplegado en la página que indica falta de fotografía
	    WebElement Mensaje = au.driver.findElement(CEDIS_MensajeModDanCadu);
	    String MensajeDesplegado = Mensaje.getText(); // Capturamos el texto del mensaje actual en pantalla
	    String MensajeCorrecto = "¿Desea modificar los articulos Dañados y Caducos?"; // Texto esperado

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
    
    public void SeleccionarObjeto(String ObjetoPantalla)
    {  	
    	//Funcion para buscar objeto y dar click especifico en la pantalla Creado: Juan Ibarra    	
    	switch (ObjetoPantalla) {
    	case "Boton Modificar Danados Y Caducos":
    		au.scrollToElement(CEDIS_BtnModDanCadu);
    		au.click(CEDIS_BtnModDanCadu);
            break;
    	case "Boton Continuar":
    		au.scrollToElement(CEDIS_BtnContinuar);
    		au.click(CEDIS_BtnContinuar);
            break;
    	case "Boton No":
    		au.scrollToElement(CEDIS_BtnNO);
    		au.click(CEDIS_BtnNO);
            break;
    	case "Boton Si":
    		au.scrollToElement(CEDIS_BtnSI);
    		au.click(CEDIS_BtnSI);
            break; 
    	    	            
        default:
            baseExecution.addStep("Se falla el caso");
            //Se agrega el paso a mostrar en el PDF
    		Assert.fail("No se ha encontrado el objeto "+ ObjetoPantalla, null);
    		//Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria leyenda de null
    	}    	
    }
    
  //Autor: Jorge Alberto Lugo Martinez 11/02/2025
   	//Metodo para validar la información del encabezado
   	public void validarEncabezadoRutasResumen() throws InterruptedException
   	{
   		if(au.driver.findElement(CEDIS_IconoTienda).isEnabled()==false) {
       		baseExecution.addStep("Se falla el caso");        
       		Assert.fail("No se ha encontrado el Logo de Oxxo de la pantalla de RESUMEN DE ACREDITACIÓN en la parte de inicio");
       	}    	
       	if (au.driver.findElements(CEDIS_ValorFecha).isEmpty()!= false) {    		
       		baseExecution.addStep("Se falla el caso");        
       		Assert.fail("No se ha encontrado el dato de la Fecha de la pantalla de RESUMEN DE ACREDITACIÓN en la parte de inicio");
       	}    	
       	if(au.driver.findElements(CEDIS_ResumenRuta).isEmpty()!= false) {
       		baseExecution.addStep("Se falla el caso");        
       		Assert.fail("No se ha encontrado el dato de la Ruta de la pantalla de RESUMEN DE ACREDITACIÓN en la parte de inicio");
       	}
       	if(au.driver.findElements(CEDIS_ResumenRutaDinamica).isEmpty()!= false) {
       		baseExecution.addStep("Se falla el caso");        
       		Assert.fail("No se ha encontrado el dato de la Ruta Dinamica de la pantalla de RESUMEN DE ACREDITACIÓN en la parte de inicio");
       	}
       	if(au.driver.findElements(CEDIS_LeyendaCRTienda).isEmpty()!= false) {
       		baseExecution.addStep("Se falla el caso");        
       		Assert.fail("No se ha encontrado el dato del CR de Tienda de la pantalla de RESUMEN DE ACREDITACIÓN en la parte de inicio");
       	}
       	if(au.driver.findElements(CEDIS_LeyendaTienda).isEmpty()!= false) {
       		baseExecution.addStep("Se falla el caso");        
       		Assert.fail("No se ha encontrado el dato del nombre de la Tienda de la pantalla de RESUMEN DE ACREDITACIÓN en la parte de inicio");
       	}
       	if(au.driver.findElements(CEDIS_LeyendaTransferencia).isEmpty()!= false) {
       		baseExecution.addStep("Se falla el caso");        
       		Assert.fail("No se ha encontrado el dato del número de la transferencia de la pantalla de RESUMEN DE ACREDITACIÓN en la parte de inicio");
       	}
   	}
}