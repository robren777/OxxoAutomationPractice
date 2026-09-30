package Mobile.cedis.om;

import org.openqa.selenium.By;

import exceptions.FrameworkException;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;




public class A01_Login_Punto_De_Venta {
    private AppiumUtil au;
    private TestCase testCase;
	private BaseExecution baseExecution;
    // -------------------------------- Identificadores -------------------------------- //
    /**
	private By CEDIS_DireccionIpXpos = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/textinput_placeholder");
	private By CEDIS_Puerto = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/etPuerto");
    private By CEDIS_Guardar = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btnSaveIP");
    private By CEDIS_BotonOKGuardarDireccionIp = By.id("android:id/button1");
    /**
     */

    private By CEDIS_DireccionIpXpos = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/etIpAddress\"]");
    private By CEDIS_Puerto = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/etPuerto\"]");
    private By CEDIS_Guardar = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnSaveIP\"]");
    private By CEDIS_BotonOKGuardarDireccionIp = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]");
   
    
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
       
    public A01_Login_Punto_De_Venta(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
        this.testCase = testCase;
        this.au = au;
        this.baseExecution = baseExecution;
    }
    
    /**
     * Método - Login con punto de venta 
     * @param ip corresponde a la ip del punto de venta al cual se conecta
      **/
    
    /**
     * Método Login con punto de venta.
     *
     * Este método verifica que se realice el login al punto de venta validando que la ip corresponde 
     * a la ip del punto de venta al cual se conecta
     *
     * @author Alejandro Valdez
     * @date 
     * @throws FrameworkException
     */
    
        
    public void loginPV(String ip) {
      au.driver.hideKeyboard();
      au.sendKeys(CEDIS_DireccionIpXpos, ip);
      au.sendKeys(CEDIS_Puerto, "53030");
      au.click(CEDIS_Guardar); 
      au.click(CEDIS_BotonOKGuardarDireccionIp); 
    
    }
    

}
