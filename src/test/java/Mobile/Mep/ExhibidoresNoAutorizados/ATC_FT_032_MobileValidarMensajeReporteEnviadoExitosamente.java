package Mobile.Mep.ExhibidoresNoAutorizados;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.Mep.ExhibidoresNoAutorizados.om.VistaExhNoAutorizadas;
import Mobile.Mep.ExhibidoresNoAutorizados.om.VistaReporteExhNoAutorizados;
import Mobile.Mep.om.VistaChromeAppCache;
import Mobile.Mep.om.VistaIdentificacionTienda;
import Mobile.Mep.om.VistaOAuth0;
import Mobile.Mep.om.VistaPanelDeControl;
import Mobile.Mep.om.VistaUsuariosNoExistentes;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

/**
* DESCRIPCIÓN: Validar que despues de haber tomado una foto, haber seleccionado una opcion en el campo "Selecciona la exhibicion",  haber seleccionado una opcion en el campo "Selecciona proveedor" y haber ingresado un valor numerico entre el 1 al 100 dentro del campo "Especifica cantidad", se habilite el boton "finalizar" y una vez haciendo click en el boton finalizar, se despliegue un mensaje de alerta con título: "Reporte enviado exitosamente" "El reporte de faltante se ha realizado con exito, equipo mercadeo revisará la solicitud en breve". 
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* -MTC-FT-032-Mensaje de alerta de "Reporte enviado exitosamente" al completar todos los campos
* 
* PREREQUISITOS:
* 1. El usuario debe estar registrado en el sistema
* 2. El usuario ya debio de haber hecho un login exitoso y así lograr visualizar el panel de control/Home y sus diferentes secciones, en este caso nos enfocaremos en la seccion PromOxxo
* 3. Tener los códigos Cr de la tienda: CrPlace: <CrPlaza>, CrStore: <CrTienda> 
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

public class ATC_FT_032_MobileValidarMensajeReporteEnviadoExitosamente extends BaseExecution {	
	@SuppressWarnings("deprecation")
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_032_MobileValidarMensajeReporteEnviadoExitosamente_test(HashMap<String, String> data) throws Exception {

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


		
		VistaIdentificacionTienda identifTienda = new VistaIdentificacionTienda(testCase, au, this);
		VistaUsuariosNoExistentes usuariosNoExistentes = new VistaUsuariosNoExistentes(testCase, au, this);
		VistaChromeAppCache vistaChromeAppCache = new VistaChromeAppCache(testCase, au, this);
		VistaOAuth0 auth0 = new VistaOAuth0(testCase, au, this);
		VistaPanelDeControl vistaPanelDeControl = new VistaPanelDeControl(testCase, au, this);
		VistaReporteExhNoAutorizados vistaReporteExhNoAutorizados = new VistaReporteExhNoAutorizados(testCase, au, this); 
		VistaExhNoAutorizadas vistaExhNoAutorizadas = new VistaExhNoAutorizadas(testCase, au, this);
				
		String opcionTipoExhibicion = data.get("opcionTipoExhibicion");
		String valorProveedor = data.get("valorProveedor");
		String valorCantidad = data.get("valorCantidad");
		String btnEnabled = data.get("btnEnabled");
		
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
		
    	addStep("Click en la opcion 'Exhibidores no autorizados' dentro de la vista 'Exhibidores no autorizados y otros'");
		vistaReporteExhNoAutorizados.clickOpcionExhibidoresNoAutorizados();
		testCase.addScreenShotCurrentStep(au, "testImage");

	    addStep("Validar vista 'Exhibiciones no autorizadas'");
		vistaExhNoAutorizadas.validarVistaExhNoAutorizadas();
		testCase.addScreenShotCurrentStep(au, "testImage");
		
    	addStep("Click en las letras 'Tomar foto' para tomar fotografias");
		vistaExhNoAutorizadas.clickCampoTomarFoto();
		testCase.addScreenShotCurrentStep(au, "testImage");
		
    	addStep("Click en la opcion 'While using the app' en Funcionalidad de toma de fotografia");
		vistaExhNoAutorizadas.androidAlertAllowTakePictures();
		testCase.addScreenShotCurrentStep(au, "testImage");
		
	    addStep("Validar vista 'Toma de fotografia' en Exhibiciones no autorizadas");
		vistaExhNoAutorizadas.validarVistaTomaDeFotografia();
		testCase.addScreenShotCurrentStep(au, "testImage");
		
		addStep("Hacer click en el 'Ciruclo - Toma de Fotografia' dentro de la funcionalidad 'Toma de Fotografia'");
		vistaExhNoAutorizadas.clickIconoTomarUnaFotografia();
		testCase.addScreenShotCurrentStep(au, "testImage");	
		
    	addStep("Validar que exista el Boton 'Felcha' 'Siguiente' en la vista Tomar fotografia");
		vistaExhNoAutorizadas.validarExistenciaBtnFlechaSiguiente();
		testCase.addScreenShotCurrentStep(au, "testImage");
		
    	addStep("Validar que exista el Boton 'Felcha' 'Siguiente' en la vista Tomar fotografia");
		vistaExhNoAutorizadas.clickBtnFlechaSiguiente();
		testCase.addScreenShotCurrentStep(au, "testImage");
		
    	addStep("Click en el campo 'Tipo de Exhibicion' para que despliegue las opciones");
		vistaExhNoAutorizadas.clickCampoSelectTipoExhibicion();
		testCase.addScreenShotCurrentStep(au, "testImage");

    	addStep("Click en la opcion '" + opcionTipoExhibicion + "'");
		vistaExhNoAutorizadas.seleccionarOpcionTipoExhibicion(opcionTipoExhibicion);
		testCase.addScreenShotCurrentStep(au, "testImage");

    	addStep("Ingresamos un valor dentro del campo 'Selecciona Proveedor'");
		vistaExhNoAutorizadas.ingresaValorCampoProveedor(valorProveedor);
		testCase.addScreenShotCurrentStep(au, "testImage");

		addStep("Ingresamos el valor de '" + valorCantidad + "' dentro del campo 'Especifica Cantidad'");
		vistaExhNoAutorizadas.ingresarValorCampoEspecificaCantidad(valorCantidad);
		testCase.addScreenShotCurrentStep(au, "testImage");
		
		addStep("Validar que el boton Finalizar este habilitado");
		String estaHabilitado = vistaExhNoAutorizadas.validarBtnFinalizarHabilitado();
		assertEquals(estaHabilitado, btnEnabled);
		testCase.addScreenShotCurrentStep(au, "testImage");
		
		addStep("Click en el boton 'Finalizar'");
		vistaExhNoAutorizadas.clickBtnFinalizar();
		testCase.addScreenShotCurrentStep(au, "testImage");

    	addStep("Validar informacion de la alerta");
		vistaExhNoAutorizadas.validarAlertaReporteEnviadoExitosamente();
		testCase.addScreenShotCurrentStep(au, "testImage");

	}
	
		
	@Override
	public String setTestFullName() {
		return "ATC_FT_032_MobileValidarMensajeReporteEnviadoExitosamente_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que despues de haber tomado una foto, haber seleccionado una opcion en el campo \"Selecciona la exhibicion\",  haber seleccionado una opcion en el campo \"Selecciona proveedor\" y haber ingresado un valor numerico entre el 1 al 100 dentro del campo \"Especifica cantidad\", se habilite el boton \"finalizar\" y una vez haciendo click en el boton finalizar, se despliegue un mensaje de alerta con título: \"Reporte enviado exitosamente\" \"El reporte de faltante se ha realizado con exito, equipo mercadeo revisará la solicitud en breve\". ";
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
		return "1. El usuario debe estar registrado en el sistema 2. El usuario ya debio de haber hecho un login exitoso y así lograr visualizar el panel de control/Home y sus diferentes secciones, en este caso nos enfocaremos en la seccion PromOxxo  3. Tener los códigos Cr de la tienda: CrPlaza <CrPlaza>, CrStore: <CrTienda> ";
	}

	@Override
	public void beforeTest() {
	}
}

