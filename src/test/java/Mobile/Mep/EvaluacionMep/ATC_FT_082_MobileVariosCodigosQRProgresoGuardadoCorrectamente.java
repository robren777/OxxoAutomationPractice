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
 * DESCRIPCIÓN: Compruebe que el progreso guardado de varios códigos QR funciona correctamente
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *     -MTC-FT-082-Mobile Varios códigos QR progreso guardado correctamente
 *
 * PREREQUISITOS:
 *     1. El usuario debe estar registrado en el sistema.
 *     2. Contar con los códigos Cr de la tienda: CrPlace: 10MON, CrStore: 50SYI.
 *     3. Hay un periodo promocional activo (de N-1 a N+4).
 *     4. El usuario ha iniciado sesión correctamente.
 *     5. El usuario se encuentra en la pantalla "Evaluación MEP".
 *     6. Hay al menos un grupo de espacio promocional sin completar sin códigos Aruco (p. ej., Cuarto Frío).
 *
 * INSUMOS:
 *   -Numero de empleado
 *   -crTienda
 *   -crPlaza
 *   -pin 1111
 *   -APK promoxxo ALDIA 2.0 en ambiente de QA 
 *   
 * @author Ivan Yakab
 * 
 * @date 01/12/2025
 */

public class ATC_FT_082_MobileVariosCodigosQRProgresoGuardadoCorrectamente extends BaseExecution {
	@SuppressWarnings("deprecation")
    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_082_MobileVariosCodigosQRProgresoGuardadoCorrectamente_test(HashMap<String, String> data) throws Exception {
        
		/******************************* Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script  ********************/
		DesiredCapabilities cap = new DesiredCapabilities();
		
		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appPackage", data.get("appPackage"));
		cap.setCapability("appActivity", data.get("appActivity"));
    	
		String url = GlobalVariables.urlMep;

    	/************************ Paso 1 - Ingresar a la App ***************************/
    	addStep("Acceder a la Aplicacion");		

    	AppiumUtil au = null;
    	
		String directorioDestinoFolder = data.get("directorioDestinoFolder");
    	String nameOriginalFilePng = data.get("nameOriginalFilePng");
        	
    	utilPhotoEmulator photoEMEP = new utilPhotoEmulator();
    	photoEMEP.CopiarMoverRenombrar(nameOriginalFilePng, directorioDestinoFolder);

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
		VistaOAuth0 auth0 = new VistaOAuth0(testCase, au, this);
		VistaPanelDeControl vistaPanelDeControl = new VistaPanelDeControl(testCase, au, this);
		VistaEvaluacionMep evaluacionMep = new VistaEvaluacionMep(testCase, au, this);
		
		String passwordDb = PasswordUtil.decryptPassword(data.get("passwordDb"));
		String crTienda = data.get("crTienda");
		String crPlaza = data.get("crPlaza");
		String idPeriod = data.get("idPeriod");
		String numEmpleado = data.get("numEmpleado");
		String queryInsertRecordOne = data.get("queryInsertRecordOne");
		String queryInsertRecordTwo = data.get("queryInsertRecordTwo");
		String queryInsertRecordThree = data.get("queryInsertRecordThree");
		String queryInsertRecordOneStatus = data.get("queryInsertRecordOneStatus");
		String queryInsertRecordTwoStatus = data.get("queryInsertRecordTwoStatus");
		String queryInsertRecordThreeStatus = data.get("queryInsertRecordThreeStatus");

		

		/*
		addStep("Activamos un Periodo Promocional");
		postgreSql.activarPeriodoPromocional(passwordDb, idPeriod);
		testCase.addScreenShotCurrentStep(au, "testImage");
		*/
		
		addStep("Insertamos 3 pops en status Executable dentro de un grupo de espacio promocional, en este caso, Cabecera");
		postgreSql.insertarTresPop(passwordDb, crTienda, queryInsertRecordOne, queryInsertRecordTwo, queryInsertRecordThree, queryInsertRecordOneStatus, queryInsertRecordTwoStatus, queryInsertRecordThreeStatus);
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

		/*
	    addStep("Hacer click en el boton Continuar sin cuenta dentro de la App de Chrome");
		vistaChromeAppCache.clickBtnContinuarSinCuenta();
		testCase.addScreenShotCurrentStep(au, "testImage");
		
	    addStep("Hacer click en el boton Got It dentro de la App de Chrome - Privacy");
		vistaChromeAppCache.clickBtnGotItPrivacy();
		testCase.addScreenShotCurrentStep(au, "testImage");
		 */
	    
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
		
		addStep("Click en el boton 'Tomar Foto' dentro del primer POP");
		evaluacionMep.clickBtnTomarFotoPrimerPop();
		testCase.addScreenShotCurrentStep(au, "testImage");

    	addStep("Click en la opcion 'While using the app' en Funcionalidad de toma de fotografia");
		evaluacionMep.androidAlertAllowTakePictures();
		testCase.addScreenShotCurrentStep(au, "testImage");

	    addStep("Validar vista 'Toma de Fotografia'");
		evaluacionMep.validarVistaTomaDeFotografia();
		testCase.addScreenShotCurrentStep(au, "testImage");
		
		addStep("Click en el icono para tomar una fotografia en la funcionalidad 'Toma de fotografia'");
		evaluacionMep.clickIconoTomarUnaFotografia();
		testCase.addScreenShotCurrentStep(au, "testImage");
		
		addStep("Click en el icono 'Flecha' 'Siguiente' dentro de la funcionalidad Toma de Fotografia");
		evaluacionMep.clickBtnFlechaSiguiente();
		testCase.addScreenShotCurrentStep(au, "testImage");

	    addStep("Validar vista 'Confirmacion de Fotografias tomadas'");
		evaluacionMep.validarVistaConfirmacionDeUnaFotografia();
		testCase.addScreenShotCurrentStep(au, "testImage");
		
	    addStep("Validar que se muestre una fotografia dentro de la vista 'Galeria de Fotos'");
		evaluacionMep.validarExistenciaDeUnaFotografiaEnGaleria();
		testCase.addScreenShotCurrentStep(au, "testImage");
		
		addStep("Click en el boton 'Continuar'");
    	evaluacionMep.clickBtnContinuar();
    	testCase.addScreenShotCurrentStep(au, "testImage");
    	
    	addStep("Click en el boton 'Guardar Avance'");
    	evaluacionMep.clickBtnGuardarAvance();
    	testCase.addScreenShotCurrentStep(au, "testImage");

    	addStep("Validamos que exista el mensaje de alerta '¿Quieres guardar este proceso?' 'Todos los cambios realizados serán guardados y podrás retomarlo más tarde para continuar con el siguiente paso.' dentro de la vista Folio de Produccion");
    	evaluacionMep.validarMensajeQuieresGuardarEsteProceso();
    	testCase.addScreenShotCurrentStep(au, "testImage");
    	
    	addStep("Click en el boton 'Continuar'");
    	evaluacionMep.clickBtnContinuar();
    	testCase.addScreenShotCurrentStep(au, "testImage");
    	
    	addStep("Validar vista 'Evaluacion MEP'");
    	evaluacionMep.validarVistaEvaluacionMep();
    	testCase.addScreenShotCurrentStep(au, "testImage");
    	
    	addStep("Validar 2/3 Folios completados'");
    	evaluacionMep.validarDosFoliosDeTres();
    	testCase.addScreenShotCurrentStep(au, "testImage");
    	
    	addStep("Validar que se muestre etiqueta 'En Curso'");
    	evaluacionMep.validarEtiquetaEnCurso();
    	testCase.addScreenShotCurrentStep(au, "testImage");
    	
    	addStep("Validar que se muestre el boton 'Continuar evaluacion'");
    	evaluacionMep.validarBtnContinuarEvaluacion();
    	testCase.addScreenShotCurrentStep(au, "testImage");
    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
        // Mismo que PREREQUISITOS en documentación
        return "1. El usuario debe estar registrado en el sistema. " + "2. Contar con los códigos Cr de la tienda: CrPlace: 10MON, CrStore: 50SYI. " + "3. Hay un periodo promocional activo (de N-1 a N+4). " + "4. El usuario ha iniciado sesión correctamente. " + "5. El usuario se encuentra en la pantalla \"Evaluación MEP\". " + "6. Hay al menos un grupo de espacio promocional sin completar sin códigos Aruco (p. ej., Cuarto Frío).";
    }

    @Override
    public String setTestDescription() {
        // Mismo que DESCRIPCIÓN en documentación
        return "Compruebe que el progreso guardado de varios códigos QR funciona correctamente";
    }

    @Override
    public String setTestDesigner() {
        // Mismo que @author
        return "Ivan Yakab";
    }

    @Override
    public String setTestFullName() {
        // Mismo nombre de la clase
        return "ATC_FT_082_MobileVariosCodigosQRProgresoGuardadoCorrectamente_test";
    }

    @Override
    public String setTestInstanceID() {
        // Dejar null
        return null;
    }
}
