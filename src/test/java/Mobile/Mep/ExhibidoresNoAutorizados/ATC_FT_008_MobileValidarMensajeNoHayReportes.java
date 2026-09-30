package Mobile.Mep.ExhibidoresNoAutorizados;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.Mep.ExhibidoresNoAutorizados.om.VistaReporteExhNoAutorizados;
import Mobile.Mep.ExhibidoresNoAutorizados.om.VistaTodosLosReportes;
import Mobile.Mep.om.UtilPostgreSql;
import Mobile.Mep.om.VistaChromeAppCache;
import Mobile.Mep.om.VistaIdentificacionTienda;
import Mobile.Mep.om.VistaOAuth0;
import Mobile.Mep.om.VistaPanelDeControl;
import Mobile.Mep.om.VistaUsuariosNoExistentes;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

/**
* DESCRIPCIÓN: Verificar que se muestre el mensaje "No hay materiales reportados" "Crea un reporta para verlo en esta lista" cuando no existan materiales reportados.
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* -MTC-FT-008-Validar  mensaje "No hay materiales reportados" dentro de la vista "Todos los reportes" cuando no existen materiales reportados
* 
* PREREQUISITOS:
* 1. El usuario debe estar registrado en el sistema
* 2. El usuario ya debio de haber hecho un login exitoso y así lograr visualizar el panel de control/Home y sus diferentes secciones, en este caso nos enfocaremos en la seccion PromOxxo
* 3. Tener los códigos Cr de la tienda: CrPlace: <CrPlaza>, CrStore: <CrTienda> 
* 4. No haber reportado ningun material dentro de la vista "Reporte de exhibidores no autorizados"
* 
* INSUMOS:
* 	-Numero de empleado
*   -crTienda
*   -crPlaza
*   -pin 1111
*   -APK promoxxo ALDIA 2.0 en ambiente de QA
* 
* @author Ivan Yakab
* @date 12/Septiembre/2025
*/

public class ATC_FT_008_MobileValidarMensajeNoHayReportes extends BaseExecution {	
	@SuppressWarnings("deprecation")
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_008_MobileValidarMensajeNoHayReportes_test(HashMap<String, String> data) throws Exception {

		/******************************* Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script  ********************/
		DesiredCapabilities cap = new DesiredCapabilities();
		
		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appPackage", data.get("appPackage"));
		cap.setCapability("appActivity", data.get("appActivity"));
		
		
		String url = "http://127.0.0.1:4723";
		
		
		
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
		VistaReporteExhNoAutorizados vistaReporteExhNoAutorizados = new VistaReporteExhNoAutorizados(testCase, au, this); 
		VistaTodosLosReportes vistaTodosLosReportes = new VistaTodosLosReportes(testCase, au, this);
		
		addStep("Validar la existencia de reportes y en caso de que existan eliminarlos");
		postgreSql.ValidarExistenciaReportesYEliminar(PasswordUtil.decryptPassword(data.get("passwordDb")), data.get("crTienda"));
		testCase.addScreenShotCurrentStep(au, "testImage");

		addStep("Se abre la App Al Dia");
		identifTienda.LoginApp(data.get("crPlaza"),data.get("crTienda"));
		testCase.addScreenShotCurrentStep(au, "testImage");

    	addStep("Se abre la vista 'No existen usuarios aun'");
		usuariosNoExistentes.validarVistaUsuariosNoExistentes();
		testCase.addScreenShotCurrentStep(au, "testImage");

	    addStep("Hacer click en el btn siguiente");
		usuariosNoExistentes.clickBtnSiguiente();
		testCase.addScreenShotCurrentStep(au, "testImage");

	    addStep("Hacer click en el boton Continuar sin cuenta dentro de la App de Chrome");
		vistaChromeAppCache.clickBtnContinuarSinCuenta();
		testCase.addScreenShotCurrentStep(au, "testImage");
		
	    addStep("Hacer click en el boton Continuar sin cuenta dentro de la App de Chrome y ingresamos el PIN");
		auth0.LoginOAuthGoogleTab(data.get("numEmpleado"));
		testCase.addScreenShotCurrentStep(au, "testImage");
		
		addStep("Click en opcion 'Reporte de Exhibidores no autorizados'");
		vistaPanelDeControl.clickOpcionReporteExhNoAutorizados();
		testCase.addScreenShotCurrentStep(au, "testImage");
		
		addStep("Validar vista 'Reporte de Exhibidores no autorizados'");
		vistaReporteExhNoAutorizados.validarVistaReporteExhNoAutorizados();
		testCase.addScreenShotCurrentStep(au, "testImage");
		
    	addStep("Click en el boton de 'Ver todos los reportes' dentro de la vista 'Exhibidores no autorizados y otros'");
		vistaReporteExhNoAutorizados.clickBtnVerTodosLosReportes();
		testCase.addScreenShotCurrentStep(au, "testImage");
		
    	addStep("Validar que exista el mensaje 'No hay materiales reportados'");
		vistaTodosLosReportes.validarMensajeNoHayMaterialesReportados();
		testCase.addScreenShotCurrentStep(au, "testImage");

	}
	
		
	@Override
	public String setTestFullName() {
		return "ATC_FT_008_MobileValidarMensajeNoHayReportes_test";
	}

	@Override
	public String setTestDescription() {
		return "Verificar que se muestre el mensaje \"No hay materiales reportados\" \"Crea un reporta para verlo en esta lista\" cuando no existan materiales reportados.";
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
		return "1. El usuario debe estar registrado en el sistema 2. El usuario ya debio de haber hecho un login exitoso y así lograr visualizar el panel de control/Home y sus diferentes secciones, en este caso nos enfocaremos en la seccion PromOxxo  3. Tener los códigos Cr de la tienda: CrPlace: <CrPlaza>, CrStore: <CrTienda> 4. No haber reportado ningun material dentro de la vista \"Reporte de exhibidores no autorizados\"";
	}

	@Override
	public void beforeTest() {
	}
}

