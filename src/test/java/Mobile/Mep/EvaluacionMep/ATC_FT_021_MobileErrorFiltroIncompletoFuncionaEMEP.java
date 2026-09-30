package Mobile.Mep.EvaluacionMep;

import Mobile.Mep.om.*;
import Mobile.util.GlobalVariables;
import Mobile.Mep.EvaluacionMep.om.VistaEvaluacionMep;
import io.appium.java_client.remote.AutomationName;

import modelo.BaseExecution;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

import java.time.Duration;
import java.util.HashMap;

import static org.testng.Assert.assertTrue;

/**
* DESCRIPCIÓN: Compruebe que se muestra un mensaje de error cuando no hay resultados para filtrar
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	- MTC_FT_021_Mobile Error FiltroIncompleto Funciona EMEP
* 
* PREREQUISITOS: 
* 1. El usuario debe estar registrado en el sistema.
* 2. Tener los códigos Cr de la tienda: CrPlace: 10MON, CrStore: 50SYI.
* 3. Hay un período promocional activo (de N-1 a N+4).
* 4. El usuario ha iniciado sesión correctamente. 5. Solo hay grupos de espacios promocionales incompletos.
* 
* INSUMOS:
*   -Numero de empleado
*   -crTienda
*   -crPlaza
*   -pin 1111
*   -APK promoxxo ALDIA 2.0 en ambiente de QA
* 
* @author Ivan Yakab
* @date 12/Septiembre/2025
*/

public class ATC_FT_021_MobileErrorFiltroIncompletoFuncionaEMEP extends BaseExecution {	
	@SuppressWarnings("deprecation")
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_021_MobileErrorFiltroIncompletoFuncionaEMEP_test(HashMap<String, String> data) throws Exception {
		
		/******************************* Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script  ********************/
		DesiredCapabilities cap = new DesiredCapabilities();
		
		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appPackage", data.get("appPackage"));
		cap.setCapability("appActivity", data.get("appActivity"));
		
		String url = GlobalVariables.urlMep;
		
		Runtime.getRuntime().exec("adb shell pm clear \"" + data.get("appPackage") + "\"");
		Runtime.getRuntime().exec("adb shell pm clear com.android.chrome"); // Obligatorio para el Login OAUTH cache
		
    	/************************ Paso 1 - Ingresar a la App ***************************/
    	addStep("Acceder a la Aplicacion");		
    	
    	AppiumUtil au = null;

    	try {
    		au = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n"
					+ e.getMessage());
			e.printStackTrace();
		}
    	
		au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Esperar 10 Segundos por cada elemento que falte por aparecer
		testCase.addScreenShotCurrentStep(au, "testImage");

		
		UtilPostgreSql postgreSql = new UtilPostgreSql(testCase, this);
		VistaIdentificacionTienda identifTienda = new VistaIdentificacionTienda(testCase, au, this);
		VistaUsuariosNoExistentes usuariosNoExistentes = new VistaUsuariosNoExistentes(testCase, au, this);
		VistaChromeAppCache vistaChromeAppCache = new VistaChromeAppCache(testCase, au, this);
		VistaOAuth0 auth0 = new VistaOAuth0(testCase, au, this);
		VistaPanelDeControl vistaPanelDeControl = new VistaPanelDeControl(testCase, au, this);
		VistaEvaluacionMep evaluacionMep = new VistaEvaluacionMep(testCase, au, this);
		
		String passwordDb = PasswordUtil.decryptPassword(data.get("passwordDb"));
		String crTienda = data.get("crTienda");
		String crPlaza = data.get("crPlaza");
		String idPeriod = data.get("idPeriod");
		String numEmpleado = data.get("numEmpleado");
		
		/* Insert de Pops */

		String insertPopExecutableOne = data.get("insertPopExecutableOne");
		String insertPopExecutableTwo = data.get("insertPopExecutableTwo");
		String insertPopExecutableThree = data.get("insertPopExecutableThree");
		String insertPopExecutableFour = data.get("insertPopExecutableFour");
		
		/* Insert de Status de Pops */

		String insertPopExecutableOneStatus	 = data.get("insertPopExecutableOneStatus");
		String insertPopExecutableTwoStatus	 = data.get("insertPopExecutableTwoStatus");
		String insertPopExecutableThreeStatus = data.get("insertPopExecutableThreeStatus");
		String insertPopExecutableFourStatus = data.get("insertPopExecutableFourStatus");

		
		addStep("Activamos un Periodo Promocional");
		postgreSql.activarPeriodoPromocional(passwordDb, idPeriod);
		testCase.addScreenShotCurrentStep(au, "testImage");
		
		addStep("Metodo para insertar 4 pops con estatus EXECUTABLE - incompletos");
		postgreSql.insertar4PopsExecutable(passwordDb, crTienda,
				insertPopExecutableOne,
				insertPopExecutableTwo,
				insertPopExecutableThree,
				insertPopExecutableFour,
				insertPopExecutableOneStatus,
				insertPopExecutableTwoStatus,
				insertPopExecutableThreeStatus,
				insertPopExecutableFourStatus);
		testCase.addScreenShotCurrentStep(au, "testImage");

		addStep("Se abre la App Al Dia");
		identifTienda.LoginApp(crPlaza, crTienda);
		testCase.addScreenShotCurrentStep(au, "testImage");

    	addStep("Se abre la vista 'No existen usuarios aun'");
		assertTrue(usuariosNoExistentes.validarVistaUsuariosNoExistentes());
		testCase.addScreenShotCurrentStep(au, "testImage");

	    addStep("Hacer click en el btn siguiente");
		usuariosNoExistentes.clickBtnSiguiente();
		testCase.addScreenShotCurrentStep(au, "testImage");

	    addStep("Hacer click en el boton Continuar sin cuenta dentro de la App de Chrome");
		vistaChromeAppCache.clickBtnContinuarSinCuenta();
		testCase.addScreenShotCurrentStep(au, "testImage");
		
	    addStep("Hacer click en el boton Got It dentro de la App de Chrome - Privacy");
		vistaChromeAppCache.clickBtnGotItPrivacy();
		testCase.addScreenShotCurrentStep(au, "testImage");

	    addStep("Hacer click en el boton Continuar sin cuenta dentro de la App de Chrome y ingresamos el PIN");
		auth0.LoginOAuthGoogleTab(numEmpleado);
		testCase.addScreenShotCurrentStep(au, "testImage");
		
		addStep("Validar que se muestre la opcion Evaluacion MEP");
		vistaPanelDeControl.validarOpcionPromOxxoEvaluacionMep();
		testCase.addScreenShotCurrentStep(au, "testImage");
		
		addStep("Click en la opcion Evaluacion MEP");
		vistaPanelDeControl.clickOpcionEvaluacionMep();
		testCase.addScreenShotCurrentStep(au, "testImage");
		
	    addStep("Validar vista 'Evaluacion MEP'");
		evaluacionMep.validarVistaEvaluacionMep();
		testCase.addScreenShotCurrentStep(au, "testImage");
		
		addStep("Validar que no existan pops con estauts 'Completos'");
		assertTrue(evaluacionMep.validarNoExistenciaDePopsCompletos());
		testCase.addScreenShotCurrentStep(au, "testImage");

		addStep("Validar que existan pops con estauts 'Incompletos'");
		evaluacionMep.validarExistenciaDePopsIncompletos();
		testCase.addScreenShotCurrentStep(au, "testImage");

	    
	    addStep("Hacer click en el boton 'Filtro' dentro de la vista 'Evaluacion MEP'");
		evaluacionMep.clickBtnFiltro();
		testCase.addScreenShotCurrentStep(au, "testImage");
		
		addStep("Validar que se muestre la vista donde se puede seleccionar los filtros");
		evaluacionMep.validarVistaFiltros();
		testCase.addScreenShotCurrentStep(au, "testImage");
		
	    addStep("Hacer click en el boton 'Completado' dentro de la vista 'Filtrar por'");
		evaluacionMep.clickBtnFiltroCompletado();
		testCase.addScreenShotCurrentStep(au, "testImage");
		
	    addStep("Validar vista 'Evaluacion MEP'");
		evaluacionMep.validarVistaEvaluacionMep();
		testCase.addScreenShotCurrentStep(au, "testImage");
		
		addStep("Validar cuantos estatus 'Completos' se encuentran dentro de la vista 'Evaluacion MEP'");
		evaluacionMep.contabilizarPopsCompletos();
		testCase.addScreenShotCurrentStep(au, "testImage");
		
	    addStep("Validar cuantos estatus 'Incompletos' se encuentran dentro de la vista 'Evaluacion MEP'");
		evaluacionMep.contabilizarPopsIncompletos();
		testCase.addScreenShotCurrentStep(au, "testImage");
		
		addStep("Validar que no existan pops con estauts 'Completos'");
		assertTrue(evaluacionMep.validarNoExistenciaDePopsCompletos());
		testCase.addScreenShotCurrentStep(au, "testImage");
		
	    addStep("Validar que se muestre el mensaje de alerta 'Sin Resultados' 'No hay espacios promocionales que coincidan con el filtro seleccionado.'");
		evaluacionMep.validarMensajeFiltroSinResultados();
		testCase.addScreenShotCurrentStep(au, "testImage");
	}
	
		
	@Override
	public String setTestFullName() {
		return "ATC_FT_021_MobileErrorFiltroIncompletoFuncionaEMEP_test";
	}

	@Override
	public String setTestDescription() {
		return "Compruebe que se muestra un mensaje de error cuando no hay resultados para filtrar";
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
		return "1. El usuario debe estar registrado en el sistema.\r\n"
				+ "2. Tener los códigos Cr de la tienda: CrPlace: 10MON, CrStore: 50SYI.\r\n"
				+ "3. Hay un período promocional activo (de N-1 a N+4).\r\n"
				+ "4. El usuario ha iniciado sesión correctamente. 5. Solo hay grupos de espacios promocionales incompletos.";
	}

	@Override
	public void beforeTest() {
	}
}

