package Mobile.directos.om.mvp3;


import java.time.Duration;

import org.openqa.selenium.By;

import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class Login {
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
    private AppiumUtil au;
    private BaseExecution baseExecution;
    /*
     * Localizadores de inicio de sesi�n
     * */
  private By user = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/etUserName");
  private By password = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/etPassword");
  private By btnLogin = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btnLogin");

  /*
  
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
    public Login(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
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
    public void login(String nombreDeUsuario, String contraDeUsuario){
      au.driver.hideKeyboard();
      au.sendKeys(user, nombreDeUsuario);
      au.sendKeys(password, contraDeUsuario);
      au.click(btnLogin);
      au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
      
    }    
    
}

