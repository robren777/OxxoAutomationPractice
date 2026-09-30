package Mobile.cedis.mvp3.tablet.sprint8;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

public class ATC_FT_019_TabletValidacionDelFlujoAlSeleccionarMODIFICARRECEPCIONDesdeLaPantallaFUE extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_019_TabletValidacionDelFlujoAlSeleccionarMODIFICARRECEPCIONDesdeLaPantallaFUE_test(
			HashMap<String, String> data) throws Exception {

		/*******************************
		 * Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script
		 ********************/
		DesiredCapabilities cap = new DesiredCapabilities();

		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appium:appPackage", data.get("appPackageP"));
		cap.setCapability("appium:appActivity", data.get("appActivityP"));
		cap.setCapability("ip", data.get("ip"));
		cap.setCapability("userLogin", data.get("userLogin"));
		cap.setCapability("passwordLogin", data.get("passwordLogin"));
		cap.setCapability("numTransferencia", data.get("numTransferencia"));

		String url = "http://127.0.0.1:4723/";

		// Cerrar Apps
		Runtime.getRuntime().exec("adb shell pm clear " + data.get("appPackage"));

		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}

		// Abre la aplicación
		Runtime.getRuntime().exec("adb shell am start " + data.get("appPackage"));

		
		//=================================== Inicio de Caso =====================================//

		 /* 
		 * Paso 1  - Se inicia el caso por parte del usuario en la pantalla de FUE de una transferencia iniciada el flujo por la opción de Devoluciones a CEDIS 
		*/
		
		/* 
		 * Paso 2  - ?Se muestra la pantalla de Formato Único de Entrega con sus campos correspondientes.
				Se muestran los botones de 
				-"MODIFICAR RECEPCIÓN"
				-"MODIFICAR DEVOLUCIONES" 
				-"TERMINAR"
		*/
		
		/* 
		 * Paso 3  - Usuario selecciona el botón de "MODIFICAR RECEPCIÓN"
		*/
		
		/* 
		 * Paso 4  - ?Se envía el caso a la pantalla de Pantalla Recepción Pedidos  a CEDIS en la cual se carga la información capturada previamente.
		*/
		
		/* 
		 * Paso 5  - Se valida que se muestre el llenado de información de pantalla de Recepción Pedidos a CEDIS   donde se actualiza la información de cajas y de la sección de Recepción Cajas sin etiqueta / Etiqueta dañada para después  seleccionar Continuar.
		*/
		
		/* 
		 * Paso 6  - ?Se muestra la sección de Sobrantes / Dañados con la información precargada
		*/
		
		/* 
		 * Paso 7  - Se valida que se muestre el llenado de información de la sección de Sobrantes / Dañados para después  seleccionar Terminar una vez actualizada la información en pantalla
		*/
		
		/* 
		 * Paso 8  - ?Se muestra la pantalla de Resumen Recepción con la información precargada con la información actualizada
		*/
		
		/* 
		 * Paso 9  - Se observa la información correspondiente a la pantalla de Resumen  Recepción y se selecciona el botón de Firma de Acuerdo
		*/
		
		/* 
		 * Paso 10  - ?Se muestra la pantalla de Acuerdo de Entrega en la cual se muestra la información correspondiente y donde se mostrara el checkbox de "cumplimiento de proceso de entrega" al final de la pantalla, arriba del botón "TERMINAR RECEPCIÓN".
		*/
		
		/* 
		 * Paso 11 - Se realiza la firma de Acuerdo de  Entrega y se selecciona el botón de TERMINAR RECEPCIÓN
		*/
		
		/* 
		 * Paso 12 - ?Se muestra la pantalla de Formato Único de Entrega con sus campos correspondientes mostrando actualizada información .
					Se muestran los botones de 
					-"MODIFICAR RECEPCIÓN"
					-"MODIFICAR DEVOLUCIONES" 
					-"TERMINAR"
		*/
		
		addStep("Pendiente actualizar script");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		

		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));

	}

	/*CEDIS MVP3: MTC-FT-019 Tablet Validación del flujo al seleccionar MODIFICAR RECEPCIÓN desde la pantalla FUE
	  Desc: Caso de prueba en el que se valida el flujo a tomar cuando se seleccione la opción de MODIFICAR RECEPCIÓN en la pantalla de FUE
	  @autor: Vanessa Torres
	  @date: 04/02/2025
	 */

	@Override
	public String setTestFullName() {
		return "ATC_FT_019_TabletValidacionDelFlujoAlSeleccionarMODIFICARRECEPCIONDesdeLaPantallaFUE";
	}

	@Override
	public String setTestDescription() {
		return "Caso de prueba en el que se valida el flujo a tomar cuando se seleccione la opción de "
				+ "MODIFICAR RECEPCIÓN en la pantalla de FUE";
	}

	@Override
	public String setTestDesigner() {
		return "Vanessa Torres";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1. Contar con Transferencias por atender en la handheld\r\n"
				+ "2. Haber iniciado el flujo de la transferencia con la opción de Devoluciones a CEDIS \r\n"
				+ "3. Contar con la transferencia en la pantalla de FUE";
	}

	@Override
	public void beforeTest() {
	}

}