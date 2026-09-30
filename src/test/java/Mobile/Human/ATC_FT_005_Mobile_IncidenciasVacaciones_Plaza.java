package Mobile.Human;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.Human.om.FormulariosTramites;
import Mobile.Human.om.LoginUserAndPasswordOxxoTesting;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

/**
 * MTC-FT-005-Validar Campos Tramite Incidencias en Vacaciones vs API WM Usuario Plaza 
 * @author Iv�n Yakab
 * Descripci�n: Validar que dentro del Tramite seleccionado se muestren pre cargados los datos del usuario Plaza y compararlos vs la API DE WM"
 * 
 * Pre condiciones para ejecutar Script automatizado:
 * 1.- Dentro del DataProvider se debe tener el usuario y la contrase�a (La contrase�a debe estar encriptada) para que funcione el pageObject LoginUserAndPasswordOxxoTesting. 
 * 2.- Dentro del DataProvider se debe ingresar los valores que t� esperas que tengan cada uno de los campos del formulario
 * 3.- Escribir correctamente los valores de los campos que queremos validar ya que es KeySensitive el DataProvider
 * 
 * Recomendaciones / Comentarios
 * 1.- Utilizar el usuario con el Rol del Nombre del Caso de Prueba
 * 2.- En caso de que no te permita loguearte ya que el Login tiene OTP (Via SMS), solicitar a Humand o PO del Proyecto que deshabiliten el OTP en la comunidad OXXO TESTING
 * 3.- Cerciorarse manualmente (Entrar al sitio web o App) antes de ejecutar este script que el usuario que ingresaste dentro del DataProvider se pueda loguear en comunidad Oxxo Testing
 * 
 * Dependencias:
 * 1.- DataLake no tiene ambiente de QA
 * 2.- Solicitar a Humand (Fabricante de la APP y Sitio web) o PO del proyecto usuarios para ejecutar pruebas en caso de que no se tengan 
 * 3.- Siempre debe de estar habilitado el login con usuario y contrase�a en comunidad Oxxo Testing, en caso de que no, solicitar a Humand que desactive el Login por OTP
 */

public class ATC_FT_005_Mobile_IncidenciasVacaciones_Plaza  extends BaseExecution {	
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_005_Mobile_IncidenciasVacaciones_Plaza_test(HashMap<String, String> data) throws Exception {
		/******************************* Inicio - Configuraci�n Pre eliminar de Dispositivo a Ejecutar Script  ********************/
		DesiredCapabilities cap = new DesiredCapabilities();
		
		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appPackage", data.get("appPackage"));
		cap.setCapability("appActivity", data.get("appActivity"));
		
		String url = "http://127.0.0.1:4723";
		
		/************************ Paso 1 - Ingresar a la App ***************************/
    	addStep("Acceder a la Aplicacion");		
    	
    	AppiumUtil appiumUtil = null;
    	try {
    		appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n"
					+ e.getMessage());
			e.printStackTrace();
		}
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");		
		
		// Instanciando Objetos
		LoginUserAndPasswordOxxoTesting login = new LoginUserAndPasswordOxxoTesting(testCase, appiumUtil, this);
		FormulariosTramites formTramites = new FormulariosTramites(testCase, appiumUtil, this); 
		
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Esperar 10 Segundos por cada elemento que falte por aparecer
		
		// Loguearse en la app con usuario y contrase�a
		login.loginOxxoTesting(data.get("user"), PasswordUtil.decryptPassword(data.get("password")));
		
		// Buscar el tr�mite y seleccionarlo
		formTramites.opcionTramites(data.get("tramiteABuscar"), data.get("tramiteASeleccionar"));
		
		// Obtener los valores de cada campo y realizar un assert para validarlo
		formTramites.validacionCampo(data.get("NumColaborador"), "Numero de Colaborador");
		formTramites.validacionCampo(data.get("NombreColab"), "Nombre de Colaborador");
		formTramites.validacionCampo(data.get("CorreoElectronico"), "Correo Electr�nico");
		formTramites.validacionCampo(data.get("CentroTrabajo"), "Centro de Trabajo");
		formTramites.validacionCampo(data.get("Region"), "Regi�n");
		formTramites.validacionCampo(data.get("Plaza"), "Plaza");
		formTramites.validacionCampo(data.get("FechaIngreso"), "Fecha de Ingreso");
		formTramites.validacionCampo(data.get("Puesto"), "Puesto");
	}
		
	@Override
	public String setTestFullName() {
		return "ATC_FT_005_Mobile_IncidenciasVacaciones_Plaza_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que dentro del Tramite seleccionado se muestren pre cargados los datos del usuario Plaza y compararlos vs la API DE WM";
	}

	@Override
	public String setTestDesigner() {
		return "Ivan Yakab";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1 Tener instalada la aplicaci�n Humand dentro de nuestro dispositivo \n"
				+ "2 Tener un usuario creado previamente\n"
				+ "3 No tener una sesi�n activa dentro de la aplicaci�n\n"
				+ "4 Acceder a la primera vista de Inicio de sesi�n\n";
	}

	@Override
	public void beforeTest() {
	}
}
