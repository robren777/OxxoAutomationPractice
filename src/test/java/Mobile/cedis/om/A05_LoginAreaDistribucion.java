package Mobile.cedis.om;

import org.openqa.selenium.By;
import org.testng.Assert;

import exceptions.FrameworkException;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;




public class A05_LoginAreaDistribucion {
    private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;
	
	
	private By CEDIS_necesitaPermisos = By.xpath("//android.widget.TextView[@resource-id=\"android:id/alertTitle\"]");
	private By CEDIS_mensajePemiros = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\"]");
	private By CEDIS_btncancelarPermiso = By.xpath("//android.widget.Button[@resource-id=\"android:id/button3\"]");
    private By CEDIS_btnaceptarPermiso = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]");
    private By CEDIS_preguntaPermisos = By.xpath("//android.widget.TextView[@resource-id=\"com.android.permissioncontroller:id/permission_message\"]");
    private By CEDIS_btnmientrasAppUso = By.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_foreground_only_button\"]");
    private By CEDIS_btnsoloVezAppUso = By.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_one_time_button\"]");
    private By CEDIS_btndenegarAppUso = By.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_deny_button\"]");
    private By CEDIS_quierePermisos = By.xpath("//android.widget.TextView[@resource-id=\"com.android.permissioncontroller:id/permission_message\" and @text=\"¿Quieres permitir que OXXO al día acceda a las fotos y el contenido multimedia del dispositivo?\"]");
    private By CEDIS_btnpermitir = By.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_button\" and @text=\"PERMITIR\"]");
    private By CEDIS_btndenegar = By.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_deny_button\" and @text=\"DENEGAR\"]");
    
    private By CEDIS_tituloPantalla = By.xpath("//android.widget.TextView[@text=\"RECEPCIÓN DE PEDIDOS CEDIS\"]");
    private By CEDIS_rutaAreaDist = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/etext_user_LogCedis\"]");
    private By CEDIS_passwordAreaDist = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/etext_Pass_LogCedis\"]");
    private By CEDIS_submitButtonDist = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btn_LogCedis\"]");  
        
  
    /**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase A05_LoginAreaDistribucion
	 *
	 * @param au            (AppiumUtil): objeto de la clase
	 * @param testCase      (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 * @author Juan Ibarra
	 */
	
    public A05_LoginAreaDistribucion(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
        this.testCase = testCase;
        this.au = au;
        this.baseExecution = baseExecution;
    }
    
    /**
	 * Método para validar Login Area Distribucion
	 *
	 * Este método realiza el inicio de sesión en el área de distribución, ingresando una ruta y contraseña y 
	 * haciendo clic en el botón de enviar, asegurándose de manejar permisos y el teclado virtual.
	 *
	 * @author Juan Ibarra
	 * @date 
	 * @throws FrameworkException
	 */
    
    
    public void LoginAreaDistribucion() throws InterruptedException {
        /**
         * Método - Para realizar el logeo en la Recepción de Pedidos 
         * @param 
         */
    	AvanzarNecesitaPermisos();
    	au.driver.hideKeyboard();
        au.sendKeys(CEDIS_rutaAreaDist, "123");
        au.sendKeys(CEDIS_passwordAreaDist, "2");
    	au.click(CEDIS_submitButtonDist);  	
    	
    }
   
    /**
	 * Método para validar mensaje de para Avanzar necesita permisos
	 *
	 * Este método identifica el mensaje de permisos para avanzar que solicita la app asegurando que el flujo continúe.
	 *
	 * @author Juan Ibarra
	 * @date 
	 * @throws FrameworkException
	 */
        
    public void AvanzarNecesitaPermisos() throws InterruptedException {
        /**
         * Método - Para avanzar al momento de mostrar el mensaje de Necesita Permisos 
         * @param 
         */
    	
    	boolean necesitaPermiso;
    	necesitaPermiso= au.driver.findElements(CEDIS_necesitaPermisos).isEmpty(); //Se agrega a un booleano donde defina si el objeto se muestra en pantalla
    	//Se valida que el objeto se muestra en pantalla
    	if (necesitaPermiso != true ) {
    		au.click(CEDIS_btnaceptarPermiso);
    		au.click(CEDIS_btnmientrasAppUso);
    		au.click(CEDIS_btnpermitir);    		
 		}
    	boolean AppUso;
    	AppUso= au.driver.findElements(CEDIS_btnmientrasAppUso).isEmpty(); //Se agrega a un booleano donde defina si el objeto se muestra en pantalla
    	//Se valida que el objeto se muestra en pantalla
    	if (AppUso != true ) {    		
    		au.click(CEDIS_btnmientrasAppUso);
    		au.click(CEDIS_btnpermitir);    		
 		}    	
    }

    /**
	 * Método para validar mensaje Necesita permisos y cancelar
	 *
	 * Este método detecta el mensaje de solicitud de permisos al iniciar la app y la cancela automáticamente si está presente.
	 *
	 * @author Juan Ibarra
	 * @date 
	 * @throws FrameworkException
	 */
    
    
    public void CancelarNecesitaPermisos() throws InterruptedException {
        /**
         * Método - Para avanzar al momento de mostrar el mensaje de Necesita Permisos 
         * @param 
         */
    	
    	boolean necesitaPermiso;
    	necesitaPermiso= au.driver.findElements(CEDIS_necesitaPermisos).isEmpty(); //Se agrega a un booleano donde defina si el objeto se muestra en pantalla
    	//Se valida que el objeto se muestra en pantalla
    	if (necesitaPermiso != true ) {
    		au.click(CEDIS_btncancelarPermiso);    			
 		}    	
    }
    
    /**
	 * Método para Seleccionar objeto en pantalla
	 *
	 * Este método permite buscar y hacer clic sobre elementos comunes de la pantalla de distribución ("Ruta", "Password", "Entrar") si el nombre no coincide, registra el fallo y termina la prueba.
	 *
	 * @author Juan Ibarra
	 * @date 
	 * @throws FrameworkException
	 */
    
    public void SeleccionarObjeto(String ObjetoPantalla)
    {  	
    	/*Funcion para buscar objeto y dar click especifico en la pantalla 
    	 * Creado:	Juan Ibarra
    	 * Fecha: 	12-febrero-2025
    	 */
    	switch (ObjetoPantalla) {
        case "Ruta":
    		au.scrollToElement(CEDIS_rutaAreaDist); //Se agrego linea para que busque el objeto antes de darle click Juan 26/11/24
    		au.click(CEDIS_rutaAreaDist);
            break;
    	case "Password":
    		au.scrollToElement(CEDIS_passwordAreaDist); //Se agrego linea para que busque el objeto antes de darle click Juan 26/11/24
    		au.click(CEDIS_passwordAreaDist);
            break;
    	case "Entrar":
    		au.scrollToElement(CEDIS_submitButtonDist); //Se agrego linea para que busque el objeto antes de darle click Juan 26/11/24
    		au.click(CEDIS_submitButtonDist);
            break;
    
        default:
            baseExecution.addStep("Se falla el caso");
            //Se agrega el paso a mostrar en el PDF
    		Assert.fail("No se ha encontrado el objeto "+ ObjetoPantalla, null);
    		//Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria leyenda de null
    	}    	
    }
    
}    