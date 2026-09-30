package Mobile.cedis.om;


import org.openqa.selenium.By;

import exceptions.FrameworkException;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;




public class A02_Login_Lider {

    private TestCase testCase;
    private AppiumUtil au;
    private BaseExecution baseExecution;
    

    // -------------------------------- Identificadores -------------------------------- //
    private By CEDIS_CampoIngresarUsuario = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/etUserName\"]");
    private By CEDIS_CampoIngresarContrasena = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/etPassword\"]");
    private By CEDIS_BotonIngresar = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnLogin\"]");
    
	/**************************************
	 * Constructor de la clase Login
	 * 
	 * @author Vanessa Torres
	 * @param au            (AppiumUtil): objeto de la clase
	 * @param testCase      (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 */

	public A02_Login_Lider(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
		this.testCase = testCase;
		this.au = au;
		this.baseExecution = baseExecution;
	}
	
	
	/*
	 * Método - Login con user y password
	 *
	 * @param nombreDeUsuario El usuario con el que se iniciara sesion
	 * 
	 * @param contraDeUsuario El password con el que iniciara sesion
	 *
	 * Pre requisito: 1. Se debe completar la primer ventana donde se ingresa la ip
	 * y el puerto hasta llegar al Login 2. No se debe eliminar el cache ni los
	 * datos de la app
	 */
    
	/**
	 * Método Login con user y password para usuario lider
	 * 
	 * Este metodo es para realizar el login con el usuario lider se realiza correctamente al validar el usuario y password ingresados.
	 *
	 * @author Vanessa Torres
	 * @date 
	 * @throws FrameworkException
	 */
	
    public void login(String nombreDeUsuario, String contraDeUsuario){
      au.driver.hideKeyboard();
      au.sendKeys(CEDIS_CampoIngresarUsuario, nombreDeUsuario);
      au.sendKeys(CEDIS_CampoIngresarContrasena, contraDeUsuario);
      au.click(CEDIS_BotonIngresar); 
    }    
    

}    
