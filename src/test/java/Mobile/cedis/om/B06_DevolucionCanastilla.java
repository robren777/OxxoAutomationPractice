package Mobile.cedis.om;

import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class B06_DevolucionCanastilla {
    private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;

    /**
    private By CEDIS_LeyendaDevolCanastilla = By.xpath("//android.widget.TextView[@text=\"DEVOLUCIÓN DE CANASTILLAS\"]");
    private By CEDIS_btn_SeleccionarBolDevolCanastilla = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnSelectBol\" and @text=\"text:=SELECCIONAR BOL\"]");
    private By CEDIS_LeyendaBolSeleccionarCanastilla = By.xpath("com.femco.oxxo.operaciones.mitienda.debug:id/txtbol_item_list","text:=.*","mcindex:=0");//Seleccione el primer bol que encuentre
    private By CEDIS_CheckBolPorSeleccionarCanastilla = By.xpath("com.femco.oxxo.operaciones.mitienda.debug:id/chkbol","ischecked:=False","mcindex:=0");//Seleccione el primer bol que encuentre
    private By CEDIS_CheckBolSeleccionadoCanastilla = By.xpath("com.femco.oxxo.operaciones.mitienda.debug:id/chkbol","ischecked:=True","mcindex:=0");//Seleccione el primer bol que encuentre
    
    private By CEDIS_TomarFotoDevolucionCanastilla = By.xpath("accessibilityid:=Obturador","resourceid:=org\.codeaurora\.snapcam:id/shutter_button");
    private By CEDIS_ConfirmarFotoDevolucionCanastilla = By.xpath("accessibilityid:=Listo","resourceid:=org\.codeaurora\.snapcam:id/done_button");
    
    private By CEDIS_SeleccionarBolDevolucionCanastilla = By.xpath("resourceid:=android:id/message","text:=Debe seleccionar el o los BOL´s ");
    private By CEDIS_AceptarBolDevolucionCanastilla = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]");,"text:=ACEPTAR");
    private By CEDIS_IniciarCaptura = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnScanReturnedLayette\" and @text=\"text:=INICIAR CAPTURA\"]");
    private By CEDIS_IniciarEscaneo = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnScanReturnedLayette\" and @text=\"text:=INICIAR ESCANEO\"]");
    private By CEDIS_EliminarCodigoDevolucion = By.xpath("com.femco.oxxo.operaciones.mitienda.debug:id/tvDeleteReturned","text:=ELIMINAR CÓDIGO");
    private By CEDIS_LeyendaCodigoDevolucion = By.xpath("com.femco.oxxo.operaciones.mitienda.debug:id/tvCodeReturned","text:=CÓDIGO.*");
    private By CEDIS_IngresarCodigoCanastilla = By.xpath("com.femco.oxxo.operaciones.mitienda.debug:id/editTextNoScanReturned","text:=Ingresa código de canastilla");
    private By CEDIS_LeyendaCodigoNoValidoDevolucionCanasta = By.xpath("//android.widget.TextView[@text=\"El código de contenedor ingresado no es válido\"]");
    private By CEDIS_btn_AceptarLeyendaCodigoNoValidoDevolucionCanast = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]");,"text:=ACEPTAR");
    private By CEDIS_LeyendaCodigoRegistradoCanastilla = By.xpath("resourceid:=android:id/message","text:=El código escaneado ya fue registrado como devuelto en el paso anterior\.");
    private By CEDIS_AceptarLeyendaCodigoRegistradoCanastilla = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]");,"text:=ACEPTAR");
    private By CEDIS_LeyendaCodigoPartePedidoDeveolucion = By.xpath("resourceid:=android:id/message","text:=El código escaneado es parte del pedido\. No se permite registrarlo como Devolución de Canastillas\.");
    private By CEDIS_AceptarCodigoPartePedidoDeveolucion = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]");,"text:=ACEPTAR");
    private By CEDIS_LeyendaTotalDevoCanasta = By.xpath("com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleTotalReturnedLayette","text:=TOTAL DEVOLUCIÓN DE CANASTILLAS");
    private By CEDIS_CantTotalDevolucionCanastilla = By.xpath("com.femco.oxxo.operaciones.mitienda.debug:id/numTotalReturnedLayette","text:=.*");
    private By CEDIS_LeyendaCanastillaTienda = By.xpath("com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleLayetteStore","text:=CANASTILLA EN TIENDA");
    private By CEDIS_IniciarCapturaCanastillaTienda = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnScanLayetteStore\" and @text=\"text:=INICIAR CAPTURA\"]");
    private By CEDIS_IniciarEscaneoCanastillaTienda = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnScanLayetteStore\" and @text=\"text:=NICIAR ESCANEO\"]");
    private By CEDIS_IngresarCodigoCanastillaTienda = By.xpath("com.femco.oxxo.operaciones.mitienda.debug:id/editTextNoScanLayette","text:=Ingresa código de canastilla");
    private By CEDIS_LeyendaTotalDevCanasTienda = By.xpath("com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleTotalLayetteStore","text:=TOTAL DEVOLUCIÓN DE CANASTILLAS EN TIENDA");
    private By CEDIS_CantTotalDevCanasTienda = By.xpath("com.femco.oxxo.operaciones.mitienda.debug:id/numTotalLayetteStore","text:=.*");
    private By CEDIS_LeyendaDevolucionContenedor = By.xpath("com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleContainers","text:=DEVOLUCIÓN DE CONTENEDORES");
    
    
    private By CEDIS_LeyendaTotalDevolucion = By.xpath("com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleTotalContainers","text:=TOTAL DEVOLUCIÓN");
    private By CEDIS_CantTotalDevolucion = By.xpath("com.femco.oxxo.operaciones.mitienda.debug:id/numTotalContainers","text:=.*");
    private By CEDIS_TerminarDevolucionCanastilla = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnFinishContainers\" and @text=\"text:=TERMINAR\"]");
    private By CEDIS_LeyendaSeleccionarBolDevolucion = By.xpath("resourceid:=android:id/message","text:=No ha seleccionado el o los BOL´s ¿Está seguro que desea continuar sin generar una devolución \?");
    private By CEDIS_CancelarSeleccionarBolDevolucion = By.xpath("//android.widget.Button[@resource-id=\"android:id/button2\"]");,"text:=CANCELAR");
    private By CEDIS_ConfirmarSeleccionarBolDevolucion = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]");,"text:=CONFIRMAR");
    private By CEDIS_LeyendaConfirmarDevolucion = By.xpath("isenabled:=True","resourceid:=android:id/message","text:=¿Está seguro de confirmar el término de la captura de la Devolución Canastillas Vacías\? ");
    private By CEDIS_CancelarDevolucionCanastilla = By.xpath("//android.widget.Button[@resource-id=\"android:id/button2\"]");,"text:=CANCELAR");
    private By CEDIS_ConfirmarDevolucionCanastilla = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]");,"text:=CONFIRMAR");


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
    public B06_DevolucionCanastilla(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
        this.testCase = testCase;
        this.au = au;
        this.baseExecution = baseExecution;
    }
    
    /**
     * Método - Login con user y password
	 *
     * @param nombreDeUsuario El usuario con el que se iniciara sesion
     * @param contraDeUsuario El password con el que inicar� sesion
     *
     *  Pre requisito: 
     *  1. Se debe completar la primer ventana donde se ingresa la ip y el puerto hasta llegar al Login
     *  2. No se debe eliminar el cache ni los datos de la app
     */
    /**
    public void login(String nombreDeUsuario, String contraDeUsuario){
      au.driver.hideKeyboard();
      au.sendKeys(user, nombreDeUsuario);
      au.sendKeys(password, contraDeUsuario);
      au.click(btnLogin);
    }    
    */

}    