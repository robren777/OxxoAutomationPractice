package Mobile.cedis.mvp3.tablet.sprint5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

public class ATC_FT_069_TabletMaximoDeTransferenciasCandadoDeTransferenciaRecibidaPorXpos extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_069_TabletMaximoDeTransferenciasCandadoDeTransferenciaRecibidaPorXpos_test(
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
			assertTrue(false, "Hubo un problema al acceder a la aplicación. \n"
					+ e.getMessage());
			e.printStackTrace();
		}

		// Abre la aplicación
		Runtime.getRuntime().exec("adb shell am start " + data.get("appPackage"));

		//=================================== Inicio de Caso =====================================//
		 /* 
		 * Paso 1  - Se ubica en la pantalla: "Recepcion de pedido Cedis" 
		*/ 

		 /*
		 * Paso 2  - ?Se despliega correctamente la pantalla: "Recepcion de pedido Cedis", donde se visualizan 
		 * por lo menos dos transferencias sin iniciar 
		*/ 

		 /* 
		 * Paso 3  - Se da clic en una de estas dos transferencias 
		*/ 

		 /* 
		 * Paso 4  - ?Se despliega correctamente la pantalla de recepcion de esta transferencia 
		*/ 

		 /* 
		 * Paso 5  - Se toman las fotografias de evidencia 
		*/ 

		 /* 
		 * Paso 6  - ?Se tomaron y guardaron correctamente las 3 fotografias de evidencia 
		*/ 

		 /* 
		 * Paso 7  - Se realiza el ingreso de una cantidad significativa de cajas y canastillas 
		*/ 

		 /* 
		 * Paso 8  - ?Se realizo el ingreso de cajas y canastillas correctamente y aumento el numero de cada 
		 * seccion de conteo 
		*/ 

		 /* 
		 * Paso 9  - Se continua el flujo hasta llegar a la seccion de cajas sin etiqueta 
		*/ 

		 /* 
		 * Paso 10  - ?Se despliega correctamente la seccion de cajas sin etiqueta 
		*/ 

		 /* 
		 * Paso 11 - Se realiza el ingreso de una cantidad significativa de cajas sin etiqueta 
		*/ 

		 /* 
		 * Paso 12 - ?Se realizo el ingreso de cajas sin etiqueta correctamente 
		*/ 

		 /* 
		 * Paso 13 - Se continua el flujo hasta llegar a la seccion de sobrantes 
		*/ 

		 /* 
		 * Paso 14 - ?Se despliega correctamente la seccion de sobrantes 
		*/ 

		 /* 
		 * Paso 15 - Se realiza el ingreso de una cantidad significativa de sobrantes 
		*/ 

		 /* 
		 * Paso 16 - ?Se realizo el ingreso de sobrantes correctamente 
		*/ 

		 /* 
		 * Paso 17 - Se continua el flujo hasta llegar a la seccion de danados 
		*/ 

		 /* 
		 * Paso 18 - ?Se despliega correctamente la seccion de danados 
		*/ 

		 /* 
		 * Paso 19 - Se realiza el ingreso de una cantidad significativa de diferentes articulos danados 
		 * (Cantidad de danados diferentes, no en un solo articulo meter una cantidad alta del mismo) 
		*/ 

		 /* 
		 * Paso 20 - ?Se realizo el ingreso de danados correctamente 
		*/ 

		 /*
		 * Paso 21 - Ahora volvemos hacia el listado de transferencias por recepcionar, para ingresar datos en 
		 * otra transferencia  con una cantidad significativa de cajas, canastillas, cajas sin etiqueta, sobrantes y danados 
		*/ 

		 /* 
		 * Paso 22 - ?Se despliega correctamente la pantalla: "Recepcion de pedido Cedis", donde se visualiza la 
		 * transferencia que dejamos en proceso (icono amarillo) y por lo menos una transferencia sin iniciar 
		*/ 

		 /* 
		 * Paso 23 - Se da clic en esta segunda transferencia que falta por llenar de datos 
		*/ 

		 /* 
		 * Paso 24 - ?Se despliega correctamente la pantalla de recepcion de esta segunda transferencia 
		*/ 

		 /* 
		 * Paso 25 - Se toman las fotografias de evidencia 
		*/ 

		 /* 
		 * Paso 26 - ?Se tomaron y guardaron correctamente las 3 fotografias de evidencia 
		*/ 

		 /* 
		 * Paso 27 - Se realiza el ingreso de una cantidad significativa de cajas y canastillas 
		*/ 

		 /* 
		 * Paso 28 - ?Se realizo el ingreso de cajas y canastillas correctamente y aumento el numero de cada seccion 
		 * de conteo 
		*/ 

		 /* 
		 * Paso 29 - Se continua el flujo hasta llegar a la seccion de cajas sin etiqueta 
		*/ 

		 /* 
		 * Paso 30 - ?Se despliega correctamente la seccion de cajas sin etiqueta 
		*/ 

		 /* 
		 * Paso 31 - Se realiza el ingreso de una cantidad significativa de cajas sin etiqueta 
		*/ 

		 /* 
		 * Paso 32 - ?Se realizo el ingreso de cajas sin etiqueta correctamente 
		*/ 

		 /* 
		 * Paso 33 - Se continua el flujo hasta llegar a la seccion de sobrantes 
		*/ 

		 /* 
		 * Paso 34 - ?Se despliega correctamente la seccion de sobrantes 
		*/ 

		 /* 
		 * Paso 35 - Se realiza el ingreso de una cantidad significativa de sobrantes 
		*/ 

		 /* 
		 * Paso 36 - ?Se realizo el ingreso de sobrantes correctamente 
		*/ 

		 /* 
		 * Paso 37 - Se continua el flujo hasta llegar a la seccion de danados 
		*/ 

		 /* 
		 * Paso 38 - ?Se despliega correctamente la seccion de danados 
		*/ 

		 /* 
		 * Paso 39 - Se realiza el ingreso de una cantidad significativa de diferentes articulos danados 
		 * (Cantidad de danados diferentes, no en un solo articulo meter una cantidad alta del mismo) 
		*/ 

		 /* 
		 * Paso 40 - ?Se realizo el ingreso de danados correctamente 
		*/ 

		 /*
		 * Paso 41 - Ahora volvemos hacia el listado de transferencias por recepcionar, para terminar la primer transferencia
		 *  que dejamos en proceso 
		*/ 

		 /* 
		 * Paso 42 - ?Se despliega correctamente la pantalla: "Recepcion de pedido Cedis", donde se visualizan estas
		 *  dos transferencias que estan en proceso (icono amarillo) 
		*/ 

		 /* 
		 * Paso 43 - Daremos clic en la primer transferencia que avanzamos e ingresamos datos 
		*/ 

		 /* 
		 * Paso 44 - ?Se despliega correctamente esta transferencia con los datos que ingresamos correctamente 
		 * (cajas, canastillas, cajas sin etiqueta, sobrantes y danados)
		*/
		
		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));

	}

	 /*
	  CEDIS MVP3: MTC-FT-069/Tablet Maximo de transferencias: Candado de transferencia recibida por Xpos
	  Desc: Validar que al momento de la avanzar el fujo de dos transferencias con una cantidad significativa de cajas, canastillas, cajas sin etiqueta, sobrantes y dañados, la aplicacion no presente fallos en el autoguardado debido a la alta carga de datos en cada una
	  @autor: Vanessa Torres
	  @date: 04/02/2025
	 */

	@Override
	public String setTestFullName() {
		return "ATC_FT_069_TabletMaximoDeTransferenciasCandadoDeTransferenciaRecibidaPorXpos";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al momento de la avanzar el fujo de dos transferencias con una cantidad significativa de "
				+ "cajas, canastillas, cajas sin etiqueta, sobrantes y dañados, la aplicacion no presente fallos en el "
				+ "autoguardado debido a la alta carga de datos en cada una";
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
		return "1. Tener dos transferencias con cantidad alta de bultos sin iniciar y que no hayan sido recibidas de forma tradicional Xpos\r\n"
				+ "2. No tener avance en otras transferencias para evitar el candado de maximo de transferencias en proceso\r\n"
				+ "3. Codigos para ingresar en los diversos apartados del flujo que abarca el autoguardado"
;
	}

	@Override
	public void beforeTest() {
	}

}