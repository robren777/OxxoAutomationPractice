package Mobile.Mep.PackingList.om;

import java.time.Duration;

import Mobile.Mep.om.VistaIdentificacionTienda;
import Mobile.Mep.om.VistaOAuth0;
import Mobile.Mep.om.VistaPanelDeControl;
import Mobile.Mep.om.VistaUsuariosNoExistentes;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class PackingListUtils {
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
    private AppiumUtil au;
    private BaseExecution baseExecution;
    private VistaIdentificacionTienda identifTienda;
    private VistaUsuariosNoExistentes usuariosNoExistentes;
    private VistaOAuth0 auth0;
    
    /**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase PackingListUtils 
	 * @author GTIM
	 * @param au (AppiumUtil): objeto de la clase 
	 * @param testCase (TestCase): objeto de la clase
	 */
	public PackingListUtils(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
        this.testCase = testCase;
        this.au = au;
        this.baseExecution = baseExecution;
        au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Esperar 10 Segundos por cada elemento que falte por aparecer
        this.identifTienda = new VistaIdentificacionTienda(testCase, au, baseExecution);
    	this.usuariosNoExistentes = new VistaUsuariosNoExistentes(testCase, au, baseExecution);
    	this.auth0 = new VistaOAuth0(testCase, au, baseExecution);
	}
	

    /**
	 * Haz logIn y navega a Dashboard screen
	 *
	 * @author Gtim
	 * @param crPlaza Es el valor de crPlaza para hacer login
	 * @param crTienda Valor de crTienda para hacer login
	 * @param numEmpleado valor del numero de empleado para hacer logIn
	 * @date 09/07/2026
	 */
	public void logingAndGoToDashboardScreen(String crPlaza, String crTienda, String numEmpleado) {
		try {
			//"Se abre la App Al Dia");
			identifTienda.loginApp(crPlaza,crTienda);
			//"Click al pop up");
			identifTienda.clickBtnPopup();
			//"Se abre la vista 'No existen usuarios aun'");
			usuariosNoExistentes.validarVistaUsuariosNoExistentes();
		    //"Hacer click en el btn iniciar sesion");
			usuariosNoExistentes.clickIniciarSesion();
		    //"Hacer click en el boton Continuar sin cuenta dentro de la App de Chrome y ingresamos el PIN");
			auth0.loginUser(numEmpleado);
		} catch (Exception e) {
			throw new RuntimeException("Problema detectado al intentar hacer logIn y desplazarse a la pantalla de Dashboard: ", e);
		}
	}
}
