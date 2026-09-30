package Mobile.cedis.om;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.netty.handler.timeout.TimeoutException;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class B09_FormatoUnicoEntrega {
    private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;
	
	//Sección Encabezado
	private By CEDIS_TituloPantalla = By.xpath("//android.widget.TextView[@text=\"FORMATO ÚNICO DE ENTREGA\"]");
	private By CEDIS_Fecha = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvDateText\" and @text=\"Fecha: \"]");
	private By CEDIS_ValorFecha = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleDate\"]");
	private By CEDIS_Ruta = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleRute\"]");
	private By CEDIS_RutaDinamica = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleRuteD\"]");
	private By CEDIS_CRTienda = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleStore\"]");
	private By CEDIS_NombreTienda = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleNameStore\"]");	
	private By CEDIS_Lider = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleLeaderStore\"]");
	private By CEDIS_Transferencia = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTransferNumber\"]");
	
	//Sección Entregados
	private By CEDIS_LeyendaFormatoUnicoEntrega = By.xpath("//android.widget.TextView[@text=\"FORMATO ÚNICO DE ENTREGA \"]");
	private By CEDIS_LeyendaEntregados = By.xpath("//android.widget.TextView[@text=\"ENTREGADOS\"]");
	private By CEDIS_LeyendaTipoContenedor = By.xpath("//android.widget.TextView[@text=\"TIPO \n"	+ "CONTENEDOR\"]");	
	private By CEDIS_LeyendaTotalPedido = By.xpath("//android.widget.TextView[@text=\"TOTAL PEDIDO\"]");
    private By CEDIS_LeyendaTotalEntregado = By.xpath("//android.widget.TextView[@text=\"TOTAL ENTREGADO\"]");
    private By CEDIS_DetallesCajas = By.xpath("//android.widget.TextView[@text=\"Cajas\"]");
    private By CEDIS_DetallesSecos = By.xpath("//android.widget.TextView[@text=\"Secos\"]");
    private By CEDIS_DetallesRefrigerados = By.xpath("//android.widget.TextView[@text=\"Refrigerados\"]");
    
    private By CEDIS_TotalPedidoCajas = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/lblCajasPed\"]");
    private By CEDIS_TotalEntregadoCajas = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/lblCajasDet\"]");
    private By CEDIS_TotalPedidoSecos = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/lblSecosPed\"]");
    private By CEDIS_TotalEntregadoSecos = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/lblSecosDet\"]");
    private By CEDIS_TotalPedidoRefrigerados = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/lblRefriPed\"]");
    private By CEDIS_TotalEntregadoRefrigerados = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/lblRefriDet\"]");
    private By CEDIS_LeyendaTotalEntregados = By.xpath("(//android.widget.TextView[@text=\"Total\"])[1]");
    private By CEDIS_TotalEntregadosPedido = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ttpedid\"]");
    private By CEDIS_TotalEntregadosEntregado = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ttent\"]");
    
    //Sección ENTRADA DE CAJAS SIN ETIQUETAS/ETIQUETAS DAÑADAS
    private By CEDIS_EntradaCajasSinEtiqueta = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvWOFUE\" and  @text=\"ENTRADA DE CAJAS SIN ETIQUETAS/ETIQUETAS DAÑADAS\"]");
    private By CEDIS_LeyendaDescripcionCajasSinEtiqueta = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleWooType\" and @text=\"DESCRIPCIÓN\"]");
    private By CEDIS_CodigoContenedorCajasSinEtiqueta = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleTotalDeliveredWoo\" and @text=\"CÓDIGO CONTENEDOR\"]");
    private By CEDIS_DescripcionCajasSinEtiqueta = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvWOtype\"]");
    private By CEDIS_NumCajasSinEtiqueta = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvWOcode\"]");
    private By CEDIS_LeyendaTotalCajasSinEtiqueta = By.xpath("(//android.widget.LinearLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/layoutContainer\"])[3]");
    private By CEDIS_ArticuloDescripcion = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvWOtype\" and @text=\"CERVEZA VICTORIA MEGA RETORNABLE 12\"]");
    private By CEDIS_PrimerRegistroSinEtiqueta = By.xpath("(//android.widget.LinearLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/layoutContainer\"])[1]/android.widget.LinearLayout");
    //Para obtener el total de valores realizar la función de ------------
    
    //Sección Faltantes
    private By CEDIS_LeyendaFaltante = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textView36\" and @text=\"FALTANTES\"]");
    private By CEDIS_TipoContenedorFaltante = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleContainerType\" and @text=\"TIPO\n" + "CONTENEDOR\"]");
    private By CEDIS_CodigoContenedorFaltante = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleTotalOrder\" and @text=\"CÓDIGO\n" + "CONTENEDOR\"]");
    private By CEDIS_TotalFaltante = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleTotalDelivered\" and @text=\"TOTAL\"]");
    private By CEDIS_LeyendaCajaFaltantes = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvContainerType\" and @text=\"Cajas\"]");
    private By CEDIS_LeyendaSecosFaltante = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvContainerType\" and @text=\"Canastillas Secos\"]");
    private By CEDIS_LeyendaRefrigeradosFaltante = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvContainerType\" and @text=\"Canastillas Refrigerados\"]");
    //Para obtener el valor total de cada uno utilizar la función de 
    private By CEDIS_DescripcionCodigoFaltantes = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTotalOrder\"]");
    private By CEDIS_TotalFaltantes = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTotalDelivered\"]");
 
    //Sección Sobrantes
    private By CEDIS_LeyendaSobrante = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textSDob\" and @text=\"SOBRANTES\"]");
    private By CEDIS_CodigoContenedor = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleContainerType\" and @text=\"CÓDIGO CONTENEDOR\"]");
    private By CEDIS_SkuSobrantes = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvLeftoverType\"]");
    private By CEDIS_LeyendaTotalSobrantes = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvLeftoverType\" and @text=\"Total Sobrantes\"]");
    //Para que obtenga el valor Tptañ de Sobrante usar el metodo 
    //private By CEDIS_CantidadTotalSobrantes = By.xpath("com.femco.oxxo.operaciones.mitienda.debug:id/tvTotalLeftover","text:=.*");
     
    //Sección Dañados
    private By CEDIS_LeyendaDanados = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textDdob\" and @text=\"DAÑADOS\"]");
    private By CEDIS_ColumnaCodigoArticulos = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleContainerType\" and @text=\"CÓDIGO ARTÍCULO\"]");
    private By CEDIS_ColumnaDanados = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleTotalOrder\" and @text=\"DAÑADOS\"]");
    private By CEDIS_TotalDanados = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvDamagedType\" and @text=\"Total Dañados\"]");
    // La cantidad total de Dañados se obtiene del metodo 
    //private By CEDIS_CantidadDanados = By.xpath("com.femco.oxxo.operaciones.mitienda.debug:id/tvCounterDamage","text:=.*");
    
    //Sección Dañados FirmaFaltante
    private By CEDIS_LeyendaFirmaFaltante = By.xpath("//android.widget.TextView[@text=\"FIRMA FALTANTE/SOBRANTE/DAÑADO\"]");
    private By CEDIS_LeyendaObservacionAreaFaltante = By.xpath("//android.widget.TextView[@text=\"Observaciones Área de distribución\"]");
    private By CEDIS_ComentarioObservacionAreaFaltante = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textObservacionEntregaDistribuidor\"]");
    private By CEDIS_LeyendaFirmaAreaFaltante = By.xpath("//android.widget.TextView[@text=\"Firma Área de distribución\"]");
    private By CEDIS_PadFirmaAreaFaltante = By.xpath("//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/EtFirmaObs1\"]");
    private By CEDIS_LeyendaObservacionPersonalFaltante = By.xpath("//android.widget.TextView[@text=\"Observaciones Personal Tienda\"]");
    private By CEDIS_ComentarioObservacionPersonalFaltante = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtObservacionEntregaTienda\"]");
    private By CEDIS_LeyendaFirmaPersonalFaltante = By.xpath("//android.widget.TextView[@text=\"Firma Personal Tienda\"]");
    private By CEDIS_PadFirmaPersonalFaltante = By.xpath("//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/etfirmaAP1\"]");
    
    //Sección Devolucion de Mercancia
    private By CEDIS_LeyendaDevolucionMercancia = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textPS\" and @text=\"DEVOLUCIÓN DE MERCANCÍA\"]");
    private By CEDIS_LeyendaBolDevolver = By.xpath("//android.widget.TextView[@text=\" BOL´S A DEVOLVER\"]");
    private By CEDIS_LeyendaEspecial = By.xpath("//android.widget.TextView[@text=\"Especial\"]");
    private By CEDIS_NumBOLEspecial = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtBolEspecial\"]");
    private By CEDIS_LeyendaNormal = By.xpath("//android.widget.TextView[@text=\"Normal\"]");
    private By CEDIS_NumBOLNormal = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtBolNormal\"]");
    private By CEDIS_LeyendaTotalDevolucionMercancia = By.xpath("//android.widget.TextView[@text=\"Total\"]");
    private By CEDIS_TotalDevolucionMercancia = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtTotalBols\"]");

    //Sección Devolucion de Canastillas
    private By CEDIS_LeyendaDevolucionCanastilla = By.xpath("//android.widget.TextView[@text=\"DEVOLUCIÓN DE CANASTILLAS\"]");
    private By CEDIS_ColumnaCanastilla = By.xpath("//android.widget.TextView[@text=\" CANASTILLAS\"]");
    private By CEDIS_LeyendaAzul = By.xpath("//android.widget.TextView[@text=\"Azul\"]");
    private By CEDIS_TotalAzul = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtAzul\"]");
    private By CEDIS_LeyendaAmarilla = By.xpath("//android.widget.TextView[@text=\"Amarilla\"]");
    private By CEDIS_TotalAmarilla = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtAmarilla\"]");
    private By CEDIS_LeyendaVerde = By.xpath("//android.widget.TextView[@text=\"Verde\"]");
    private By CEDIS_TotalVerde = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtVerde\"]");
    private By CEDIS_LeyendaNaranja = By.xpath("//android.widget.TextView[@text=\"Naranja\"]");
    private By CEDIS_TotalNaranja = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtNaranja\"]");
    private By CEDIS_LeyendaOtro = By.xpath("//android.widget.TextView[@text=\"Otro\"]");
    private By CEDIS_TotalOtro = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtOtro\"]");
    private By CEDIS_LeyendaTotalCanastillas = By.xpath("(//android.widget.TextView[@text=\"Total\"])[1]");
    private By CEDIS_TotalCanastillas = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtTotalColor\"]");
    
    //Sección Devolucion de canastillas con Devolucion
    private By CEDIS_LeyendaCanastillasDevolucion = By.xpath("//android.widget.TextView[@text=\"CANASTILLAS CON DEVOLUCIÓN\"]");
    private By CEDIS_CodigoContenedorCanastillasDevolucion = By.xpath("//android.widget.TextView[@text=\" CÓDIGO CONTENEDOR\"]");
    private By CEDIS_CodigosCanastillasDevolucion = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtCodigos\"]");
    private By CEDIS_LeyendaTotalCanastillasDevolucion = By.xpath("(//android.widget.TextView[@text=\"Total\"])[2]");
    private By CEDIS_TotalCanastillasDevolucion = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtTotalCodigos\"]");
        
    //Sección Devolucion de Contenedores
    private By CEDIS_LeyendaDevolucionContenedor = By.xpath("//android.widget.TextView[@text=\"DEVOLUCIÓN DE CONTENEDORES\"]");
    private By CEDIS_LeyendaContenedoresDevolucion = By.xpath("//android.widget.TextView[@text=\"CONTENEDORES\"]");
    private By CEDIS_LeyendaTapasDevolucion = By.xpath("//android.widget.TextView[@text=\"Tapas de canastillas\"]");  
    private By CEDIS_CantidadTapasDevolucion = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textTapasCont\"]");
    private By CEDIS_LeyendaEnvaseDevolucion = By.xpath("//android.widget.TextView[@text=\"Envases vacíos\"]");
    private By CEDIS_CantidadEnvaseDevolucion = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textEnvCont\"]");
    private By CEDIS_LeyendaTotalDevolucion = By.xpath("//android.widget.TextView[@text=\"Total\"]");
    private By CEDIS_TotalDevolucion = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textdevCont\"]");
    

    //Sección Firma Devolucion
    private By CEDIS_LeyendaFirmaDevolucion = By.xpath("//android.widget.TextView[@text=\"FIRMA DEVOLUCIONES\"]");
    private By CEDIS_LeyendaObservacionAreaDevolucion = By.xpath("//android.widget.TextView[@text=\"Observaciones Área de distribución\"]");
    private By CEDIS_ComentarioObservacionAreaDevolucion = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ObservacionDistribuidorDevolucion\"]");
    private By CEDIS_LeyendaFirmaAreaDevolucion = By.xpath("//android.widget.TextView[@text=\"Firma Área de distribución\"]");
    private By CEDIS_PadFirmaAreaDevolucion = By.xpath("//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/firmaAP2\"]");
    private By CEDIS_LeyendaObservacionPersonalDevolucion = By.xpath("//android.widget.TextView[@text=\"Observaciones Personal Tienda\"]");
    private By CEDIS_ComentarioObservacionPersonalDevolucion = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ObservacionTiendaDevolucion\"]");
    private By CEDIS_LeyendaFirmaPersonalDevolucion = By.xpath("//android.widget.TextView[@text=\"Firma Personal Tienda\"]");
    private By CEDIS_PadFirmaPersonalDevolucion = By.xpath("//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/firmaAP3\"]");
    
    //Sección Movimiento tienda
    private By CEDIS_LeyendaMovimientoTienda = By.xpath("//android.widget.TextView[@text=\"Movimiento Tienda\"]");
    private By CEDIS_LeyendaBol = By.xpath("//android.widget.TextView[@text=\" BOL\"]");
    private By CEDIS_LeyendaRecepcion = By.xpath("//android.widget.TextView[@text=\"Recepción\"]");
    private By CEDIS_BOLRecepcion = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textRecep\"]");
    private By CEDIS_LeyendaFaltanteSobrenteDanados = By.xpath("//android.widget.TextView[@text=\"Faltante/Sobrante/Dañado\"]");
    private By CEDIS_BOLFaltanteSobrenteDanados = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/FSDBOL\"]");
    private By CEDIS_LeyendaDevolucionEspecial = By.xpath("//android.widget.TextView[@text=\"Devolución Especial Programada\"]");
    private By CEDIS_BOLDevolucionEspecial = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/DevMecBOL\"]");
    private By CEDIS_LeyendaDevolucionNormal = By.xpath("//android.widget.TextView[@text=\"Devolución Normal\"]");
    private By CEDIS_BOLDevolucionNormal = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/DevCanBOL\"]");
    
    
    //Sección botones
    private By CEDIS_ModificarDevolucion = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnModDev\" and @text=\"MODIFICAR DEVOLUCIONES\"]");
    private By CEDIS_ModificarRecepcion = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnModReception\" and @text=\"MODIFICAR RECEPCIÓN\"]");
    private By CEDIS_TerminarFUE = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnFinish\" and @text=\"TERMINAR\"]");

    //Sección mensajes 
    private By CEDIS_Cargando = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"Cargando\"]");
    private By CEDIS_Loader = By.xpath("//android.widget.TextView[@text=\"Cargando\"]");
    private By CEDIS_LeyendaRecepcionPedido = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"Recepción de Pedido Exitoso.\"]");
    private By CEDIS_ConfirmarPedido = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\" and @text=\"CONFIRMAR\"]");
    private By CEDIS_Continuar = By.id("android:id/button1");
    private By CEDIS_MensajeRecepcionExitosa = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\"]");
    
    private By CEDIS_LeyendaErrorEnviarCorreo = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"Error al enviar el correo.\"]");// FALTA Confirmar que sean parametros correctos 
    private By CEDIS_ReintentarCorreo = By.xpath("//android.widget.Button[@resource-id=\"android:id/Button\" and @text=\"REINTENTAR\"]"); //FALTA Confirmar que sean parametros correctos
    
    private By CEDIS_LeyendaNorecibido = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\"]"); //FALTA Confirmar que sean parametros correctos ya que el texto puede cambiar
    private By CEDIS_AcapetarNorecibido = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\" and @text=\"ACEPTAR\"]");
    

    /**
 * Constructor
 ******************************************************************************************/
	/**
	 * Constructor de la clase Login
	 * @author Alejandro Valdez
	 * @param au (AppiumUtil): objeto de la clase 
	 * @param testCase (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 */
    public B09_FormatoUnicoEntrega(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
        this.testCase = testCase;
        this.au = au;
        this.baseExecution = baseExecution;
    }
    
    public int TotalEntegadoPedido () throws IOException {
    	//Funcion para obtener el Total Entegado Pedido de la sección de Creado: Juan Ibarra
    	au.scrollToElement(CEDIS_TotalEntregadosPedido);
    	String TotalEntegadoPedido = au.driver.findElement(CEDIS_TotalEntregadosPedido).getText();//Se obtiene el valor de Total de Pedidos Entregado
    	int CantidadEntegadosPedido = Integer.parseInt(TotalEntegadoPedido);
    	return CantidadEntegadosPedido;        
    }
    
    public int TotalEntegadoEntregado () throws IOException {
    	//Funcion para obtener el Total Entegado Pedido Creado: Juan Ibarra
    	au.scrollToElement(CEDIS_TotalEntregadosEntregado);
    	String TotalEntegadoEntregado = au.driver.findElement(CEDIS_TotalEntregadosEntregado).getText();//Se obtiene el valor de Total de Pedidos Entregado
    	int CantidadEntegadosEntregados = Integer.parseInt(TotalEntegadoEntregado);
    	return CantidadEntegadosEntregados;        
    }
    
    public void SeleccionarObjeto(String ObjetoPantalla)
    {  	
    	//Funcion para buscar objeto y dar click especifico en la pantalla Creado: Juan Ibarra    	
    	switch (ObjetoPantalla) {
    	case "Modificar Devolucion":
    		au.scrollToElement(CEDIS_ModificarDevolucion);
    		au.click(CEDIS_ModificarDevolucion);
            break;
    	case "Terminar":
    		au.scrollToElement(CEDIS_TerminarFUE);
    		au.click(CEDIS_TerminarFUE);
            break;             
    	case "Confirmar Pedido":
    		au.scrollToElement(CEDIS_ConfirmarPedido);
    		au.click(CEDIS_ConfirmarPedido);
            break;    	   
    	case "Continuar":
    		au.click(CEDIS_Continuar);
            break;
    	case "Modificar Recepción"://Se agrega opción 19 - Febrero -2025 Juan Ibarra
    		au.scrollToElement(CEDIS_ModificarRecepcion);
    		au.click(CEDIS_ModificarRecepcion);
            break;
        default:
            baseExecution.addStep("Se falla el caso");
            //Se agrega el paso a mostrar en el PDF
    		Assert.fail("No se ha encontrado el objeto "+ ObjetoPantalla, null);
    		//Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria leyenda de null
    	}    	
    }

    public void Terminar(String tomaEvidencia,String descPaso) throws InterruptedException {
    	//Funcion que toma   Creado: Juan Ibarra
    	au.scrollToElement(CEDIS_TerminarFUE);
    	if (tomaEvidencia.equals("tomaEvidencia")) {
    		baseExecution.addStep(descPaso);    		
    		testCase.addScreenShotCurrentStep(au, "testImage");
    		Thread.sleep(2000);
        	au.click(CEDIS_TerminarFUE);  
        	
    		}else {
    			au.click(CEDIS_TerminarFUE);
    		}    		
    }
    
    public void ValidacionEncabezado() throws IOException, InterruptedException {
    	//Funcion que permite validar que se muestran los campo correspondientes al encabezado de la pantalla  Creado: Ricas 
    	Thread.sleep(7000);    	
    	if(au.driver.findElement(CEDIS_TituloPantalla).isEnabled()==false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el Titulo pantalla FORMATO ÚNICO DE ENTREGA en la parte de inicio", null);
    	}    	
    	if (au.driver.findElements(CEDIS_Fecha).isEmpty()!= false) {    		
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Fecha de la pantalla de FORMATO ÚNICO DE ENTREGA en la parte de inicio", null);
    	}    	
    	if(au.driver.findElements(CEDIS_ValorFecha).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Valor Fecha de la pantalla de FORMATO ÚNICO DE ENTREGA en la parte de inicio", null);
    	}
    	
    	if(au.driver.findElements(CEDIS_Ruta).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Ruta de la pantalla de FORMATO ÚNICO DE ENTREGA en la parte de inicio", null);
    	}    	
    	if(au.driver.findElements(CEDIS_RutaDinamica).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Ruta Dinamica de la pantalla de FORMATO ÚNICO DE ENTREGA en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_CRTienda).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de CR Tienda de la pantalla de FORMATO ÚNICO DE ENTREGA en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_NombreTienda).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Nombre Tienda de la pantalla de FORMATO ÚNICO DE ENTREGA en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_Lider).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Lider de la pantalla de FORMATO ÚNICO DE ENTREGA en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_Transferencia).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Transferencia de la pantalla de FORMATO ÚNICO DE ENTREGA en la parte de inicio", null);
    	}
    	
    }
    
    public void ValidacionDevolucionContenedores() throws IOException, InterruptedException {
    	//Funcion que permite validar el contenido de Devolucion de contenedores Creado: Ricas Fecha: 18-10-2024
    	au.scrollToElement(CEDIS_TotalDevolucion);
    	Thread.sleep(1000);
    	if(au.driver.findElement(CEDIS_TotalDevolucion).isEnabled()==false) {
    		baseExecution.addStep("Se falla el caso");
    		Assert.fail("No se ha encontrado el apartado DEVOLUCION DE CONTENEDORES en la pantalla de FUE", null);
    	}
    }
    
    public void ValidacionTapasYEnvasesVacios() throws IOException, InterruptedException {
    	//Funcion que permite validar el contenido de Tapas y Envases Creado: Ricas Fecha: 18-10-2024
	  if(au.driver.findElement(CEDIS_LeyendaContenedoresDevolucion).isEnabled()==false) {
    		baseExecution.addStep("Se falla el caso");
    		Assert.fail("No se ha encontrado el apartado TAPAS DE CANASTILLAS en el apartado de Devolucion de contenedores\", null");
    	}
    	if(au.driver.findElement(CEDIS_CantidadTapasDevolucion).isEnabled()==false) {
    		baseExecution.addStep("Se falla el caso");
    		Assert.fail("No se ha encontrado el apartado TOTAL DE CANASTILLAS en el apartado de Devolucion de contenedores\", null");
    	}
    	if(au.driver.findElement(CEDIS_LeyendaEnvaseDevolucion).isEnabled()==false) {
    		baseExecution.addStep("Se falla el caso");
    		Assert.fail("No se ha encontrado el apartado Envases Vacios en el apartado de Devolucion de contenedores\", null");
    	}
    	if(au.driver.findElement(CEDIS_CantidadEnvaseDevolucion).isEnabled()==false) {
    		baseExecution.addStep("Se falla el caso");
    		Assert.fail("No se ha encontrado el apartado TOTAL DE ENVASES VACIOS en el apartado de Devolucion de contenedores\", null");
    	}    	
    	if(au.driver.findElement(CEDIS_TotalDevolucion).isEnabled()==false) {
    		baseExecution.addStep("Se falla el caso");
    		Assert.fail("No se ha encontrado el apartado TOTAL NUMERICO DE CONTENEDORES en el apartado de Devolucion de contenedores\", null");
    	}    	
    }
    
    public void ValidacionMovientoTienda() throws IOException, InterruptedException {
    	//Funcion que validar que se muestra la sección Movimiento Tienda FUE Creado: Ricas Fecha Creación: 18-10-2024
  	    au.scrollToElement(CEDIS_ModificarDevolucion);
	    Thread.sleep(1000);
    	if(au.driver.findElement(CEDIS_LeyendaMovimientoTienda).isEnabled()==false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el Titulo MOVIMIENTO TIENDA en la parte de FORMATO UNICO DE ENTREGA", null);
    	}    
    }

  
    public void RecepcionExitosa() throws IOException, InterruptedException {
    	//Funcion que validar que se haga la recepción Exitosa de la transferencia, validando el tiempo de proceso de Recepción y el mensaje de Error al enviar el correo que se muestra en la pantalla del FUE 
    	//Creado: Juan Ibarra	Fecha Creación: 25-10-2024    	 
    	au.scrollToElement(CEDIS_TerminarFUE);
    	au.click(CEDIS_TerminarFUE);    	
    	int intentos = 0;    	
    	while (intentos < 12 ) {
    		if(au.driver.findElements(CEDIS_LeyendaRecepcionPedido).isEmpty()!= true) {    		
    			System.out.println("La pantalla ya no muestra la barra de carga en la pantalla de FUE");
    			break;
        	}    		
    		intentos++;    		
    		try {
    			// Espera el tiempo determinado antes de volver a intentar
                System.out.println("No se ha mostrado la leyenda de Recepción de Pedido Exitoso en la pantalla, se realiza otro intento de espera");
                Thread.sleep(6500);                
            } catch (InterruptedException e) {
                System.err.println("Error al esperar entre intentos: " + e.getMessage());               
                System.out.println("La pantalla no muestra el mensaje de Recepción de Pedido Exitoso");
    			break;  		    
            }    		
    		//if (au.driver.findElement(CEDIS_LeyendaErrorEnviarCorreo).isEnabled()==true) { 
    		//if (au.driver.findElement(CEDIS_LeyendaErrorEnviarCorreo).isEnabled()) {
    		if(au.driver.findElements(CEDIS_ReintentarCorreo).isEmpty()!= true) {
    			au.click(CEDIS_ReintentarCorreo);    			
    		}    		
    		if (intentos>10) {//Se va vlaidando la cantidad de veces que se ha estado en espera de la recepción exitosa  
    			baseExecution.addStep("Se falla el caso");
    			Assert.fail("Se ha superado los 60 segundos de tolerancia", null);//Se falla el caso al pasar un tiempo de espera
    		}
    		//if (au.driver.findElement(CEDIS_LeyendaNorecibido).isEnabled()==true) {
    		if(au.driver.findElements(CEDIS_AcapetarNorecibido).isEmpty()!= true) {
    			baseExecution.addStep("Se falla el caso");
    			Assert.fail("Se muestra mensaje donde No sé recibió con exito la orden de compra", null);//Se falla el caso al pasar un tiempo de espera    			
    		}    		
    	}
    	System.out.println("Sale del flujo de While");
    }
    
    
    public void Prueba() throws IOException, InterruptedException {
    	//Funcion que validar que se haga la recepción Exitosa de la transferencia, validando el tiempo de proceso de Recepción y el mensaje de Error al enviar el correo que se muestra en la pantalla del FUE 
    	//Creado: Juan Ibarra	Fecha Creación: 25-10-2024 	 
    	
    	int intentos = 0;    	
    	while (intentos < 12 ) {
    		if(au.driver.findElements(CEDIS_LeyendaRecepcionPedido).isEmpty()!= true) {
    		//if(au.driver.findElements(CEDIS_LeyendaRecepcionPedido).isEmpty()) {
    			System.out.println("La pantalla ya no muestra la barra de carga en la pantalla de FUE");
    			break;
        	}    		
    		intentos++;    		
    		try {
    			// Espera el tiempo determinado antes de volver a intentar
                System.out.println("No se ha mostrado la leyenda de Recepción de Pedido Exitoso en la pantalla, se realiza otro intento de espera");
                Thread.sleep(6500);                
            } catch (InterruptedException e) {
                System.err.println("Error al esperar entre intentos: " + e.getMessage());               
                System.out.println("La pantalla no muestra el mensaje de Recepción de Pedido Exitoso");
    			break;  		    
            }    		
    		//if (au.driver.findElement(CEDIS_LeyendaErrorEnviarCorreo).isEnabled()==true) { 
    		//if (au.driver.findElement(CEDIS_LeyendaErrorEnviarCorreo).isEnabled()) {
    		if(au.driver.findElements(CEDIS_LeyendaErrorEnviarCorreo).isEmpty()!= true) {
    			au.click(CEDIS_ReintentarCorreo);    			
    		}    		
    		if (intentos>10) {//Se va vlaidando la cantidad de veces que se ha estado en espera de la recepción exitosa  
    			baseExecution.addStep("Se falla el caso");
    			Assert.fail("Se ha superado los 60 segundos de tolerancia", null);//Se falla el caso al pasar un tiempo de espera
    		}
    		//if (au.driver.findElement(CEDIS_LeyendaNorecibido).isEnabled()==true) {
    		if(au.driver.findElements(CEDIS_AcapetarNorecibido).isEmpty()!= true) {
    			baseExecution.addStep("Se falla el caso");
    			Assert.fail("Se muestra mensaje donde No sé recibió con exito la orden de compra", null);//Se falla el caso al pasar un tiempo de espera    			
    		}    		
    	}    	
    }
    
  public void ValidacionContenidoMovientoTienda() throws IOException, InterruptedException {
    	//Funcion que permite validar el contenido de Movimiento Tienda Creado: Ricas Fecha: 18-10-2024
	  Thread.sleep(1000);
    	if(au.driver.findElement(CEDIS_LeyendaRecepcion).isEnabled()==false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el Titulo RECEPCION en el apartado MOVIMIENTO TIENDA", null);
    	}   
    	if(au.driver.findElement(CEDIS_BOLRecepcion).isEnabled()==false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el BOL DE RECEPCIÓN en el apartado MOVIMIENTO TIENDA", null);
    	}   
    	if(au.driver.findElement(CEDIS_LeyendaFaltanteSobrenteDanados).isEnabled()==false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el Titulo FALTANTE/SOBRANTE/DAÑADO  en el apartado MOVIMIENTO TIENDA", null);
    	}   
    	if(au.driver.findElement(CEDIS_BOLFaltanteSobrenteDanados).isEnabled()==false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el BOL DE FALTANTE/SOBRANTE/DAÑADO  en el apartado MOVIMIENTO TIENDA", null);
    	}
    	if(au.driver.findElement(CEDIS_LeyendaDevolucionEspecial).isEnabled()==false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el Titulo DEVOLUCION ESPECIAL  en el apartado MOVIMIENTO TIENDA", null);
    	}
    	if(au.driver.findElement(CEDIS_BOLDevolucionEspecial).isEnabled()==false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el BOL DE DEVOLUCION ESPECIAL en el apartado MOVIMIENTO TIENDA", null);
    	}
    	if(au.driver.findElement(CEDIS_LeyendaDevolucionNormal).isEnabled()==false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el Titulo DEVOLUCION NORMAL en el apartado MOVIMIENTO TIENDA", null);
    	}
    	if(au.driver.findElement(CEDIS_BOLDevolucionNormal).isEnabled()==false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el BOL DE DEVOLUCION NORMAL en el apartado MOVIMIENTO TIENDA", null);
    	}
    }
  
  public void ValidacionCajasSinEtiqueta() throws IOException, InterruptedException {
	  //Funcion para validar la seccion de cajas sin etiqueta
	  //Creado: Jesus Marcelo Gutierrez Bravo
	  //Fecha Creación: 27-01-2025
	  Thread.sleep(1000);
	  au.scrollToElement(CEDIS_LeyendaTotalCajasSinEtiqueta);
	  Thread.sleep(1000);
	  if(au.driver.findElement(CEDIS_LeyendaTotalCajasSinEtiqueta).isEnabled()==false) {
  		baseExecution.addStep("Se falla el caso");        
  		Assert.fail("No se ha encontrado la sección de Cajas Sin Etiqueta/Danada", null);
  	} 
	        
  }
  
  public void FinalizarFlujo() throws IOException, InterruptedException {
	  //Funcion para terminar el flujo hasta que salga el mensaje de Recepcion Exitosa
	  //Creado: Jesus Marcelo Gutierrez Bravo
	  //Fecha Creación: 27-01-2025
	  Thread.sleep(1000);
	  au.scrollToElement(CEDIS_TerminarFUE);
	  Thread.sleep(1000);
	  if(au.driver.findElement(CEDIS_TerminarFUE).isEnabled()==false) {
  		baseExecution.addStep("Se falla el caso");        
  		Assert.fail("No se ha encontrado el boton de Terminar de la pantalla del FUE", null);
  	} 
	  au.click(CEDIS_TerminarFUE);
  }
    
  public void BuscarSeccion(String ObjetoPantalla) throws IOException, InterruptedException
  {  	
  	//Funcion para buscar objeto, validar que se muestre el campo y/o dar click a objeto especifico en la pantalla Creado: Juan Ibarra Fecha Creación: 27-01-2025    	
  	switch (ObjetoPantalla) {    	
  	case "Entregados":    		
  		au.scrollToElement(CEDIS_TotalEntregadosEntregado);  		
  		if (au.driver.findElement(CEDIS_LeyendaFormatoUnicoEntrega).isEnabled()==false || au.driver.findElement(CEDIS_LeyendaEntregados).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Leyenda Formato Unico Entrega o Leyenda Entregados", null);
  		}
  		if (au.driver.findElement(CEDIS_LeyendaTipoContenedor).isEnabled()==false || au.driver.findElement(CEDIS_LeyendaTotalPedido).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Leyenda Tipo Contenedor o Leyenda Total Pedido", null);
  		}  		
  		if (au.driver.findElement(CEDIS_LeyendaTotalEntregado).isEnabled()==false || au.driver.findElement(CEDIS_DetallesCajas).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Leyenda Total Entregado o Detalles Cajas", null);
  		}
  		if (au.driver.findElement(CEDIS_DetallesSecos).isEnabled()==false || au.driver.findElement(CEDIS_DetallesRefrigerados).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Detalles Secos o Detalles Refrigerados", null);
  		}  		
  		if (au.driver.findElement(CEDIS_LeyendaTotalEntregados).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Leyenda Total Entregados", null);
  		}  		
  		break;
  	case "Sin etiqueta":
  		au.scrollToElement(CEDIS_LeyendaDescripcionCajasSinEtiqueta);
  		if (au.driver.findElement(CEDIS_EntradaCajasSinEtiqueta).isEnabled()==false || au.driver.findElement(CEDIS_LeyendaDescripcionCajasSinEtiqueta).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Entrada Cajas Sin Etiqueta o el campo de Leyenda Descripcion Cajas Sin Etiqueta", null);
  		}
  		if (au.driver.findElement(CEDIS_CodigoContenedorCajasSinEtiqueta).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Codigo Contenedor Cajas Sin Etiqueta", null);
  		}
  		break;
  	case "Faltantes":    		
  		au.scrollToElement(CEDIS_LeyendaFaltante);
  		if (au.driver.findElement(CEDIS_LeyendaFaltante).isEnabled()==false || au.driver.findElement(CEDIS_TipoContenedorFaltante).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Leyenda Faltante o el campo de Tipo Contenedor Faltante", null);
  		}  		
  		if (au.driver.findElement(CEDIS_CodigoContenedorFaltante).isEnabled()==false || au.driver.findElement(CEDIS_TotalFaltante).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Codigo Contenedor Faltante o el campo de Total Faltante", null);
  		}
  		au.scrollToElement(CEDIS_LeyendaCajaFaltantes);
  		if (au.driver.findElement(CEDIS_LeyendaCajaFaltantes).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Leyenda Caja Faltantes o el campo de Leyenda Secos Faltante", null);
  		}
  		au.scrollToElement(CEDIS_LeyendaSecosFaltante);
  		if (au.driver.findElement(CEDIS_LeyendaSecosFaltante).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Leyenda Canastillas Secos", null);
  		}
  		au.scrollToElement(CEDIS_LeyendaRefrigeradosFaltante);
  		if (au.driver.findElement(CEDIS_LeyendaRefrigeradosFaltante).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Leyenda Refrigerados Faltante", null);
  		}
  		break;    	       
  	case "Sobrantes":    		
  		au.scrollToElement(CEDIS_LeyendaSobrante);
  		if (au.driver.findElement(CEDIS_LeyendaSobrante).isEnabled()==false || au.driver.findElement(CEDIS_CodigoContenedor).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Leyenda Sobrante o el campo de Codigo Contenedor", null);
  		}
  		if (au.driver.findElement(CEDIS_LeyendaTotalSobrantes).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Leyenda Total Sobrantes", null);
  		}
  		break;
  	case "Dañados":    		
  		au.scrollToElement(CEDIS_TotalDanados);
  		if (au.driver.findElement(CEDIS_LeyendaDanados).isEnabled()==false || au.driver.findElement(CEDIS_ColumnaCodigoArticulos).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Leyenda Dañados o el campo de Columna Codigo Articulos", null);
  		}
  		if (au.driver.findElement(CEDIS_ColumnaDanados).isEnabled()==false || au.driver.findElement(CEDIS_TotalDanados).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Columna Dañados o el campo de Total Dañados", null);
  		}
  		break;
  	case "Firma Recepción":  		 
  		au.scrollToElement(CEDIS_PadFirmaAreaFaltante);
  		if (au.driver.findElement(CEDIS_LeyendaFirmaFaltante).isEnabled()==false || au.driver.findElement(CEDIS_LeyendaObservacionAreaFaltante).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Leyenda Firma Faltante o Leyenda Observacion Area Faltante", null);
  		}
  		if (au.driver.findElement(CEDIS_LeyendaFirmaAreaFaltante).isEnabled()==false || au.driver.findElement(CEDIS_PadFirmaAreaFaltante).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Leyenda Firma Area Faltante o Pad Firma Area Faltante", null);
  		}
  		au.scrollToElement(CEDIS_PadFirmaPersonalFaltante);
  		if (au.driver.findElement(CEDIS_LeyendaObservacionPersonalFaltante).isEnabled()==false || au.driver.findElement(CEDIS_LeyendaFirmaPersonalFaltante).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Leyenda Observacion Personal Faltante o Leyenda Firma Personal Faltante", null);
  		}  		
  		if (au.driver.findElement(CEDIS_PadFirmaPersonalFaltante).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Pad Firma Personal Faltante", null);
  		}  		
        break;
  	case "Devolución Canastillas":    		
  		au.scrollToElement(CEDIS_LeyendaTotalCanastillas);
  		if (au.driver.findElement(CEDIS_LeyendaDevolucionCanastilla).isEnabled()==false || au.driver.findElement(CEDIS_ColumnaCanastilla).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Leyenda Devolucion Canastilla o Columna Canastilla", null);
  		}
  		if (au.driver.findElement(CEDIS_LeyendaAzul).isEnabled()==false || au.driver.findElement(CEDIS_LeyendaAmarilla).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Leyenda Azul o Leyenda Amarilla", null);
  		}
  		if (au.driver.findElement(CEDIS_LeyendaVerde).isEnabled()==false || au.driver.findElement(CEDIS_LeyendaNaranja).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Leyenda Verde o Leyenda Naranja", null);
  		}
  		if (au.driver.findElement(CEDIS_LeyendaOtro).isEnabled()==false || au.driver.findElement(CEDIS_LeyendaTotalCanastillas).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Leyenda Otro o Leyenda Total Canastillas", null);
  		}
        break;
  	case "Canastillas con Devolución":    		
  		au.scrollToElement(CEDIS_LeyendaTotalCanastillasDevolucion);
  		if (au.driver.findElement(CEDIS_LeyendaCanastillasDevolucion).isEnabled()==false || au.driver.findElement(CEDIS_CodigoContenedorCanastillasDevolucion).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Leyenda Canastillas Devolucion o Codigo Contenedor Canastillas Devolucion", null);
  		}
  		if (au.driver.findElement(CEDIS_LeyendaTotalCanastillasDevolucion).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Leyenda Total Canastillas Devolucion", null);
  		}
        break;
  	case "Devolución Contenedores":	
  		au.scrollToElement(CEDIS_LeyendaFirmaDevolucion);  		
  		if (au.driver.findElement(CEDIS_LeyendaDevolucionContenedor).isEnabled()==false || au.driver.findElement(CEDIS_LeyendaContenedoresDevolucion).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Leyenda Devolucion Contenedor o Leyenda Contenedores Devolucion", null);
  		}  		
  		if (au.driver.findElement(CEDIS_LeyendaTapasDevolucion).isEnabled()==false || au.driver.findElement(CEDIS_LeyendaEnvaseDevolucion).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Leyenda Tapas Devolucion o Leyenda Envase Devolucion", null);
  		}
  		if (au.driver.findElement(CEDIS_LeyendaTotalDevolucion).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Leyenda Total Devolucion", null);
  		}
        break;
  	case "Firma Devolución":  		
  		au.scrollToElement(CEDIS_PadFirmaAreaDevolucion);
  		if (au.driver.findElement(CEDIS_LeyendaFirmaDevolucion).isEnabled()==false || au.driver.findElement(CEDIS_LeyendaObservacionAreaDevolucion).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Leyenda Firma Devolucion o Leyenda Observacion Area Devolucion", null);
  		}
  		if (au.driver.findElement(CEDIS_LeyendaFirmaAreaDevolucion).isEnabled()==false || au.driver.findElement(CEDIS_PadFirmaAreaDevolucion).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Leyenda Firma Area Devolucion o Pad Firma Area Devolucion", null);
  		}
  		au.scrollToElement(CEDIS_LeyendaFirmaPersonalDevolucion);
  		if (au.driver.findElement(CEDIS_LeyendaObservacionPersonalDevolucion).isEnabled()==false || au.driver.findElement(CEDIS_LeyendaFirmaPersonalDevolucion).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Leyenda Observacion Personal Devolucion o Leyenda Firma Personal Devolucion", null);
  		}  		
  		if (au.driver.findElement(CEDIS_PadFirmaPersonalDevolucion).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Pad Firma Personal Devolucion", null);
  		}
        break;
  	case "Movimiento Tienda":    		
  		au.scrollToElement(CEDIS_BOLDevolucionNormal);  		
  		if (au.driver.findElement(CEDIS_LeyendaMovimientoTienda).isEnabled()==false || au.driver.findElement(CEDIS_LeyendaBol).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Leyenda Movimiento Tienda o Leyenda Bol", null);
  		}
  		if (au.driver.findElement(CEDIS_LeyendaRecepcion).isEnabled()==false || au.driver.findElement(CEDIS_LeyendaFaltanteSobrenteDanados).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Leyenda Recepcion o Leyenda Faltante Sobrente Danados", null);
  		}
  		if (au.driver.findElement(CEDIS_LeyendaDevolucionEspecial).isEnabled()==false || au.driver.findElement(CEDIS_LeyendaDevolucionNormal).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Leyenda Devolucion Especial o Leyenda Devolucion Normal", null);
  		}
        break;
  	case "Botones":    		
  		au.scrollToElement(CEDIS_TerminarFUE);  		
  		Runtime.getRuntime().exec("adb shell input swipe 20 1003 20 900 90");// Se desliza un poco hacía abajo
  		Thread.sleep(500);
  		if (au.driver.findElement(CEDIS_ModificarDevolucion).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Modificar Devolucion", null);
  		}
  		if (au.driver.findElement(CEDIS_ModificarRecepcion).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Modificar Recepcion", null);
  		}
  		if (au.driver.findElement(CEDIS_TerminarFUE).isEnabled()==false) {
  			baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Terminar", null);
  		}
        break;
  	default:
          baseExecution.addStep("Se falla el caso"); //Se agrega el paso a mostrar en el PDF
  		Assert.fail("No se ha encontrado la "+ ObjetoPantalla, null); //Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria leyenda de null
  	}    	
  }

  public void esperaDinamicaLoaderFinalizarRecepcion() {
	// Descripción: Función para esperar a que el segundo loader que sale cuando terminamos la recepcion del pedido, una vez que desaparece termina de ejecutarse
	// Creado: Jesus Marcelo Gutierrez Bravo
	// Fecha Creación: 14-02-2025
	  
    // Configura un tiempo máximo de espera de minutos para verificar la desaparición del loader
    WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofMinutes(5));   

    try {
        wait.until(driver -> {
            try {
                // Intentar encontrar el loader en la pantalla
                WebElement loader = au.driver.findElement(CEDIS_Loader);
                
                // Si el loader sigue visible, imprimir un mensaje indicando que aún está en pantalla
                if (loader.isDisplayed()) {
                    System.out.println("Loader #2 aún visible...");
                    return false;  // Continuar esperando mientras el loader esté visible
                } else {
                    // Si el loader desaparece de la pantalla, imprimir un mensaje
                    System.out.println("\nLoader #2 desaparecido.");
                    return true;  // Terminar la espera cuando el loader ya no está visible
                }
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                // Si no se encuentra el loader o se vuelve obsoleto, significa que desapareció
                System.out.println("\nLoader #2 desaparecido.");
                return true;  // Terminar la espera si no se encuentra el loader
            }
        });

    } catch (TimeoutException e) {
        // Si el loader no desaparece en el tiempo establecido (5 minutos), mostrar un mensaje de error
        System.out.println("Error: El Loader #2 no desapareció en 5 minutos.");
    }
}


public void FinalizarFlujoFUE() throws IOException, InterruptedException {
    // Descripción: Función para bajar hasta los botones de al final del FUE y darle click en Terminar, una vez que pasaron 3 segundos termina de ejecutarse
    // Creado: Jesus Marcelo Gutierrez Bravo
    // Fecha Creación: 14-02-2025
    
    // Esperar para asegurarse de que la página esté lista
    Thread.sleep(1000);
    
    // Realizar el desplazamiento al elemento
    au.scrollToElement(CEDIS_TerminarFUE);
    Thread.sleep(1000);
	//testCase.addScreenShotCurrentStep(au, "testImage");
    
    // Verificar si el botón de "Terminar" está habilitado
    if(au.driver.findElement(CEDIS_TerminarFUE).isEnabled() == false) {
        baseExecution.addStep("Se falla el caso");        
        Assert.fail("No se ha encontrado el botón de Terminar de la pantalla del FUE", null);
    } 
    
    // Hacer click en el botón de "Terminar"
    au.click(CEDIS_TerminarFUE);
    Thread.sleep(3000);
}

public void esperaDinamicaValidarDespliegueMensajeRecepcionExitosa() {
	// Descripción: Función que espera el despliegue del mensaje Recepcion exitosa, una vez que aparece termina de ejecutarse
    // Creado: Jesus Marcelo Gutierrez Bravo
    // Fecha Creación: 14-02-2025
	
	// Configura un tiempo máximo de espera de minutos para verificar la desaparición del loader
    WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofMinutes(5));   

    try {
        wait.until(driver -> {
            try {
                // Busca el mensaje de "Recepción Exitosa" en la pantalla
                WebElement mensajeRecepcionExitosa = au.driver.findElement(CEDIS_MensajeRecepcionExitosa);
                
                // Si el mensaje está visible, significa que ya apareció
                if (mensajeRecepcionExitosa.isDisplayed()) {
                    System.out.println("\n¡Mensaje de Recepción Exitosa apareció!");
                    return true;  // Salir de la espera, ya que el mensaje ha aparecido
                }
                
                // Si el mensaje no está visible, seguir esperando
                return false;
                
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                // Si no se encuentra el mensaje o se vuelve obsoleto, continuar esperando
                return false;
            }
        });
        
    } catch (TimeoutException e) {
        // Si el mensaje no aparece dentro del tiempo límite, mostrar un error
        System.out.println("Error: El mensaje de 'Recepción Exitosa' no apareció en 5 minutos.");
    }
}



public void ValidacionRegistroSinEtiqueta() throws InterruptedException {
	  	//Valida que se muestra el primer registro de caja sin etiqueta Creado: Vanessa 28/02/25
	    au.scrollToElement(CEDIS_LeyendaTotalCajasSinEtiqueta);
	    Thread.sleep(1000);
	if(au.driver.findElement(CEDIS_LeyendaTotalCajasSinEtiqueta).isDisplayed()==false) {
		baseExecution.addStep("Se falla el caso");        
		Assert.fail("No se encontró el registro de la Caja Sin Etiqueta", null);
		}
	}

  
  
  //Función para validar que exista el dato de Devolución Normal dentro de la sección de Movimiento Tienda del FUE
  //Creado por: Jorge Lugo 19/02/2025
  public void validarDatoDevNormal() throws IOException, InterruptedException
  {
	  Thread.sleep(3000);
	  au.scrollToElement(CEDIS_ModificarDevolucion);
	  Thread.sleep(3000);
	  
	  if (au.driver.findElements(CEDIS_BOLDevolucionNormal).isEmpty() == true) {
			Assert.fail("No se muestra el dato de BOL de Devolución Normal ingresado en Devoluciones.", null);
	  }
  }
  
}    