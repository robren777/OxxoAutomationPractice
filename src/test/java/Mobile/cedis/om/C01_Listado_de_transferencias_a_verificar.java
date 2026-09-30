package Mobile.cedis.om;

import org.openqa.selenium.By;
import org.testng.Assert;

import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class C01_Listado_de_transferencias_a_verificar {
	
    private TestCase testCase;
    private AppiumUtil au;
    private BaseExecution baseExecution;
    
	// Encabezado de la pantalla //
	private By CEDIS_leyendaVerificacion = By.xpath("//android.widget.TextView[@text=\"VERIFICACIÓN DE MERCANCÍA PEDIDO CEDIS\"]");
    private By CEDIS_btnAyuda = By.xpath("//android.widget.Button[@content-desc=\"Ayuda\"]");
    private By CEDIS_btnTutorial = By.xpath("//android.widget.Button[@content-desc=\"Tutorial\"]");
    
    // Información de la Tienda //
    private By CEDIS_frameInfoTienda = By.xpath("//android.view.ViewGroup[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/include_verificacion_mercancia\"]/android.widget.FrameLayout");
    private By CEDIS_imagenTiendaOxxo = By.xpath("//android.widget.ImageView[@content-desc=\"OXXO al día\"]");
    private By CEDIS_leyendaFecha = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvDateText\"]");
    private By CEDIS_tituloFechaActual = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleDate\"]");
    private By CEDIS_tituloCRTienda = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleStore\"]");
    private By CEDIS_tituloNombreTienda = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleNameStore\"]");
    
    // Opciones de Busqueda de Transferencia //
    private By CEDIS_radiogroupBusquedaTransferencia = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/radio_group_pedidos_verificacion");
    private By CEDIS_radioOpcionTransferencia = By.xpath("//android.widget.RadioButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/radiobutton_transferencias_mercancia\"]");
    private By CEDIS_radioOpcionEstatus = By.xpath("//android.widget.RadioButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/radiobutton_estatus_mercancia\"]");
    private By CEDIS_radioOpcionFecha = By.xpath("//android.widget.RadioButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/radiobutton_fecha_mercancia\"]");
    
    // Identificadores al elegir opción de busqueda TRANSFERENCIA //
    private By CEDIS_cuadroTxtBuscarTransferencia = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/et_autocomplete_transferencia\"]");
    private By CEDIS_btnBuscarTransferencia = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/button_filter_verificacion_mercancia\"]");
    private By CEDIS_mensajeNumPedidoNoEncontrado = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"El número de pedido ingresado no existe\"]");
    private By CEDIS_btnAceptarNumPedidoNoEncontrado = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]");
    
    // Identificadores al elegir opción de busqueda ESTATUS //
    private By CEDIS_btnDesplegarListaEstatus = By.xpath("//android.widget.Spinner[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/spinner_estatus\"]");
    private By CEDIS_btnTxtElegirEstatusTodos = By.xpath("//android.widget.CheckedTextView[@resource-id=\"android:id/text1\" and @text=\"TODOS\"]");
    private By CEDIS_btnTxtElegirEstatusPorVerificar = By.xpath("//android.widget.CheckedTextView[@resource-id=\"android:id/text1\" and @text=\"POR VERIFICAR\"]");
    private By CEDIS_btnTxtElegirEstatusVerificado = By.xpath("//android.widget.CheckedTextView[@resource-id=\"android:id/text1\" and @text=\"VERIFICADO\"]");
    private By CEDIS_btnTxtElegirEstatusVencido = By.xpath("//android.widget.CheckedTextView[@resource-id=\"android:id/text1\" and @text=\"VENCIDO\"]");
    private By CEDIS_mensajeNoExisteVerificado = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"No existen registros con estatus de \"verificado\"]");
    private By CEDIS_btnAceptarNoExisteVerificado = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]");
    
    // Identificadores al elegir opción de busqueda FECHA //
    private By CEDIS_btnTxtEscribirFecha = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/et_verificacion_mercancia_fecha\"]");
    private By CEDIS_btnCalendario = By.xpath("//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/imageCalendarFilter\"]");
    private By CEDIS_mensajeFechaNoEncontrada = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"La fecha seleccionada no existe\"]");
    private By CEDIS_btnAceptarMensajeFechaNoEncontrada = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]");
    private By CEDIS_mensajeIngresoIncorrectoFecha = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"Los datos ingresados deben ser en Formato DD/MM/AAAA\"]");
    private By CEDIS_btnAceptarMensajeIngresoIncorrectoFecha = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]");

    // Lista de los pedidos o Transferencias //
    private By CEDIS_contentListaTransferencias = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/ll_content_list_verify");
    private By CEDIS_listaEncabezadoTransferencia = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleTransferenciaHeader");
    private By CEDIS_listaEncabezadoEstatus = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleEstatusHeader");
    private By CEDIS_listaEncabezadoFecha = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleFechaHeader");
    private By CEDIS_contenedorTransferencias = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/rv_verify_containers");
    
    // Identificadores del primer pedido dentro de la lista//
    private By CEDIS_btnPrimerPedido = By.xpath("//android.widget.LinearLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ll_body_container_search_verify_item\"]");
    private By CEDIS_textoNumPrimerPedido = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleTransferencia\" and @text=\"*\"]");
    private By CEDIS_textoEstatusPrimerPedido = By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleEstatus\"])[1]");
    private By CEDIS_textoFechaPrimerPedido = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleFecha\" and @text=\"*\"]");
    
    // Para avanzar o atrasar el listado de los pedidos //
    private By CEDIS_btnSiguienteBloquePedido = By.xpath("//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/button_rigth_filter_mercancia\"]");
    private By CEDIS_btnAnteriorBloquePedido = By.xpath("//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/button_left_filter_mercancia\"]");
    
    // Identificadores del mensaje de un pedido VERIFICADO //
    private By CEDIS_mensajePedidoVerificado = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\"]");
    private By CEDIS_btnAceptarMensajePedidoVerificado = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]");

    // Identificadores del mensaje de un pedido VENCIDO //
    private By CEDIS_mensajePedidoVencido = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"El pedido seleccionado ha vencido \"]");
    private By CEDIS_btnAceptarMensajePedidoVencido = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]");
    
    public C01_Listado_de_transferencias_a_verificar(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
		this.testCase = testCase;
		this.au = au;
		this.baseExecution = baseExecution;
	}
    
    public void clickPrimeraTransferencia()
    {
    	au.click(CEDIS_btnPrimerPedido);
    }
    
    public void busquedaNumTransferencia(String numTransferencia)
    {
        au.click(CEDIS_radioOpcionTransferencia);
    	au.driver.hideKeyboard();
        au.sendKeys(CEDIS_cuadroTxtBuscarTransferencia, numTransferencia);
        au.click(CEDIS_btnBuscarTransferencia);
        au.scrollToElement(CEDIS_btnPrimerPedido);
        au.click(CEDIS_btnPrimerPedido);
    }
    
    public void busquedaEstatusTransferenciaPorVerificar()
    {
    	au.click(CEDIS_radioOpcionEstatus);
    	au.click(CEDIS_btnTxtElegirEstatusPorVerificar);
    	au.click(CEDIS_btnPrimerPedido);
    }
    
    //Funcion de busqueda de la primera transferencia que cuente con estatus Por Verificar Creado: Juan Ibarra
    public void busquedaPrimeraTransferenciaPorVerificar() throws InterruptedException {
    	au.click(CEDIS_radioOpcionEstatus);
    	au.click(CEDIS_btnDesplegarListaEstatus);
    	au.click(CEDIS_btnTxtElegirEstatusPorVerificar);
    	boolean primerPedido;
    	primerPedido= au.driver.findElements(CEDIS_mensajeNoExisteVerificado).isEmpty(); //Se agrega a un booleano donde defina si el objeto se muestra en pantalla
    	//Se valida que el objeto se muestra en pantalla
    	if (primerPedido != true ) {
    		baseExecution.addStep("Se falla el caso");
    		//Se agrega el paso a mostrar en el PDF
    		Assert.fail("No se ha encontrado una transferencia con estatus \"Por Verificar\" que seleccionar", null);
    		//Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria leyenda de null
 		}else{//si no se cumple continua con los pasos de la función
 			au.scrollToElement(CEDIS_btnPrimerPedido);
 			au.click(CEDIS_btnPrimerPedido);
 		}    	 
    }
    
    public void busquedaPrimeraTransferenciaPorVerificar_Marcelo() throws InterruptedException {
        // Mensaje de inicio del método
        System.out.println("Iniciando el método: busquedaPrimeraTransferenciaPorVerificar_Marcelo");

        // Hacer clic en el radio botón de estatus
        System.out.println("Preparándose para hacer clic en el radio botón de estatus...");
        Thread.sleep(1000);
        au.click(CEDIS_radioOpcionEstatus);
        Thread.sleep(1000);
        System.out.println("Clic en el radio botón de estatus realizado.");

        // Desplegar la lista de estatus
        System.out.println("Preparándose para desplegar la lista de estatus...");
        Thread.sleep(1000);
        au.click(CEDIS_btnDesplegarListaEstatus);
        Thread.sleep(1000);
        System.out.println("Lista de estatus desplegada.");

        // Seleccionar el estatus "Por Verificar"
        System.out.println("Preparándose para seleccionar el estatus 'Por Verificar'...");
        Thread.sleep(1000);
        au.click(CEDIS_btnTxtElegirEstatusPorVerificar);
        Thread.sleep(1000);
        System.out.println("Estatus 'Por Verificar' seleccionado.");

        // Realizar scroll hasta el primer pedido
        System.out.println("Preparándose para hacer scroll hasta el primer pedido...");
        Thread.sleep(1000);
        au.scrollToElement(CEDIS_btnPrimerPedido);
        Thread.sleep(1000);
        System.out.println("Scroll completado.");

        // Hacer clic en el primer pedido
        System.out.println("Preparándose para hacer clic en el primer pedido...");
        Thread.sleep(1000);
        au.click(CEDIS_btnPrimerPedido);
        Thread.sleep(1000);
        System.out.println("Clic en el primer pedido realizado.");

        // Mensaje de finalización del método
        System.out.println("Finalización del método: busquedaPrimeraTransferenciaPorVerificar_Marcelo");
    }


}

  
