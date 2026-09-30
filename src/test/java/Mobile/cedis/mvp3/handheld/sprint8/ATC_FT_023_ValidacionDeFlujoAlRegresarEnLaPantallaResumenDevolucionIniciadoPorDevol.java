package Mobile.cedis.mvp3.handheld.sprint8;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

public class ATC_FT_023_ValidacionDeFlujoAlRegresarEnLaPantallaResumenDevolucionIniciadoPorDevol extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_023_ValidacionDeFlujoAlRegresarEnLaPantallaResumenDevolucionIniciadoPorDevol_test(
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
		 * Paso 1  - Se capturan los datos correspondientes en la pantalla de logueo de Recepción de PEDIDOS CEDIS  dando clic en Entrar
		*/ 
		
		/* 
		 * Paso 2  - ?Se muestra la pantalla Recepción de Pedidos CEDIS con el listado de Transferencias asignadas a la tienda.
		*/ 
		
		/* 
		 * Paso 3  - Se selecciona una transferencia que no ha sido atendida por el usuario.
		*/ 
		
		/* 
		 * Paso 4  - ?Se muestra la pantalla de Recepción de Pedidos CEDIS con las nuevas opciones de flujo, mostrara:
					-Cabecera de pedido: Fecha, Ruta, Ruta Dinámica, CR Tienda, Nombre Tienda, Líder Tienda.

					Mostrara el Título de :"¿Como deseas comenzar?"
					Mostrara las Instrucciones de: "Puedes comenzar escaneando las cajas y canastillas del pedido, o bien, registrando las devoluciones del pedido. Selecciona la opción deseada."

					Muestra los botones de:
					-"Recepción de pedido"
					"Devoluciones a CEDIS"
		*/ 
		
		/* 
		 * Paso 5  - Se selecciona la opción de Devoluciones a CEDIS
		*/ 
		
		/* 
		 * Paso 6  - ?Se inicia el flujo de la transferencia mostrando la pantalla de Devoluciones CEDIS
		*/ 
		
		/* 
		 * Paso 7  - Se selecciona la opción de Continuar en la pantalla de Devoluciones CEDIS una vez llenada la información correspondiente en la pantalla.
		*/ 
		
		/* 
		 * Paso 8  - ?Se muestra la pantalla de Resumen Devolución con los campos correspondientes
		*/ 
		
		/* 
		 * Paso 9  - Se selecciona la opción de Regresar en la pantalla de Resumen Devolución 
		*/ 
		
		/* 
		 * Paso 10  - ?Se regresa a la pantalla de Devoluciones CEDIS
		*/ 
		addStep("Pendiente actualizar script");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		

		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));

	}

	/*CEDIS MVP3:  MTC-FT-023 Validación de flujo al regresar en la pantalla Resumen Devolución iniciado por Devoluciones CEDIS
	  Desc: Caso de prueba en el que se valida el flujo a tomar cuando se seleccione la opción de Regresar en la pantalla de Resumen Devolución para una transferencia iniciada por Devoluciones CEDIS
	  @autor: Vanessa Torres
	  @date: 04/02/2025
	 */

	@Override
	public String setTestFullName() {
		return "ATC_FT_023_ValidacionDeFlujoAlRegresarEnLaPantallaResumenDevolucionIniciadoPorDevol";
	}

	@Override
	public String setTestDescription() {
		return "Caso de prueba en el que se valida el flujo a tomar cuando se seleccione la opción de Regresar en la "
				+ "pantalla de Resumen Devolución para una transferencia iniciada por Devoluciones CEDIS";
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
				+ "2. Haber iniciado el flujo de la transferencia con la opción de Devoluciones a CEDIS";
	}

	@Override
	public void beforeTest() {
	}

}