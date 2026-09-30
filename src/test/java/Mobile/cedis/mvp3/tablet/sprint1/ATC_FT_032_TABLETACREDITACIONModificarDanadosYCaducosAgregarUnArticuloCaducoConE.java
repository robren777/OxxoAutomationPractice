package Mobile.cedis.mvp3.tablet.sprint1;


import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import javax.annotation.Nonnull;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_032_TABLETACREDITACIONModificarDanadosYCaducosAgregarUnArticuloCaducoConE extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_032_TABLETACREDITACIONModificarDanadosYCaducosAgregarUnArticuloCaducoConE_test(HashMap<String, String> data) throws Exception {
		
		/******************************* Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script  ********************/
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
		//cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto
		
		String url = "http://127.0.0.1:4723/";

		Runtime.getRuntime().exec("adb shell pm clear " + data.get("appPackage"));
				
    	@SuppressWarnings("null")
		@Nonnull
		AppiumUtil appiumUtil = null;
    	try {
    		appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicación. \n"
					+ e.getMessage());
			e.printStackTrace();
		}
    	
    	//Abre la aplicación 
		Runtime.getRuntime().exec("adb shell am start " + data.get("appPackage"));
							
		//Ejecuta el logeo hacia el punto de venta
		Mobile.cedis.om.A01_Login_Punto_De_Venta login = new Mobile.cedis.om.A01_Login_Punto_De_Venta(testCase, appiumUtil, this); 
		login.loginPV(data.get("ip"));
		
		//Ejecuta el logeo del lider
		Mobile.cedis.om.A02_Login_Lider loginLider = new Mobile.cedis.om.A02_Login_Lider(testCase, appiumUtil, this); 
		//Obtiene valor del DataProvaider
		loginLider.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));
		
		//Ejecuta los pasos para llegar al menu de Herramientas de CEDIS
		Mobile.cedis.om.Tablet_A03_Menus_y_submenus herramienta = new Mobile.cedis.om.Tablet_A03_Menus_y_submenus(testCase, appiumUtil, this);
		herramienta.logeoInicioHerramientas();
		
		Mobile.cedis.om.A04_MenuHerramientas menuCedis =  new Mobile.cedis.om.A04_MenuHerramientas(testCase, appiumUtil, this);
		menuCedis.clickBtnVerificacionMercanciaCEDIS();//Selecciona la opcion de Verificacion
								
		Mobile.cedis.om.C01_Listado_de_transferencias_a_verificar listadoverificacion = new Mobile.cedis.om.C01_Listado_de_transferencias_a_verificar(testCase, appiumUtil, this);
		listadoverificacion.busquedaNumTransferencia(data.get("numTransferencia"));//Realiza la busqueda de una verificación 
		
		Mobile.cedis.om.C02_Verificacion_De_Pedido_Y_Permisos verificacion = new Mobile.cedis.om.C02_Verificacion_De_Pedido_Y_Permisos(testCase, appiumUtil, this);
		Thread.sleep(3000);
		//verificacion.necesitaPermisos();
		verificacion.barraProceso();
		verificacion.darClickBotonBultos();
		verificacion.darClickPrimerBulto();
		verificacion.tomarFotoTablet();
		Thread.sleep(2000);
		verificacion.verificarArticuloTablet();
		Thread.sleep(2000);
		verificacion.darClickBotonBultos();
		verificacion.darClickSegundoBulto();
		verificacion.tomarFotoTablet();
		Thread.sleep(2000);
		verificacion.verificarArticuloTablet();
		Thread.sleep(2000);
		verificacion.continuarCaducosDirecto();
		
		//SCRIPT DEL CASO DE PRUEBA//
		
		Mobile.cedis.om.C04_Acreditacion_de_Caducos caducos = new Mobile.cedis.om.C04_Acreditacion_de_Caducos(testCase, appiumUtil, this);
		
		/* Paso 1 Se ubica en la pantalla "Resumen", hasta abajo del flujo de Acreditación
		 * Resultado esperado: Se despliega correctamente el botón: "Modificar Dañados y Caducos"
		 */
		//danados.pantallaDanados();
		addStep("Se ubica en la pantalla \"Resumen\", hasta abajo del flujo de Acreditación\r\n"
				+ "Resultado esperado: Se despliega correctamente el botón: \"Modificar Dañados y Caducos\"");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*Paso 2 Se da clic en el botón: "Modificar Dañados y Caducos"
		 * Resultado esperado: Se despliega el siguiente mensaje: "¿Desea modificar los artículos Dañados y Caducos?" [NO] [SI]
		 */
		//danados.pantallaDanados();
		addStep("Se da clic en el botón: \"Modificar Dañados y Caducos\"\r\n"
				+ "Resultado esperado: Se despliega el siguiente mensaje: \"¿Desea modificar los artículos Dañados y Caducos?\" [NO] [SI]");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*Paso 3 Se da clic en el botón : "Si"
		 * Resultado esperado: Se despliega correctamente el apartado de "Articulos Dañados"
		 */
		//danados.pantallaDanados();
		addStep("Se da clic en el botón : \"Si\"\r\n"
				+ "Resultado esperado: Se despliega correctamente el apartado de \"Articulos Dañados\"");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 4 Se da clic en el botón : "Continuar"
		 * Resultado esperado: Se despliega el siguiente mensaje: "¿Ha terminado la Devolucion de Articulos Dañados?" [NO] [SI]
		 */
		//danados.pantallaDanados();
		addStep("Se da clic en el botón : \"Continuar\"\r\n"
				+ "Resultado esperado: Se despliega el siguiente mensaje: \"¿Ha terminado la Devolucion de Articulos Dañados?\" [NO] [SI]");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/* Paso 5 Se da clic en el botón : "Si"
		 * Resultado esperado: Se despliega el siguiente mensaje: "¿Hay articulos Caducos en este Pedido?" [NO] [SI]
		 */
		//danados.pantallaDanados();
		addStep("Se da clic en el botón : \"Si\"\r\n"
				+ "Resultado esperado: Se despliega el siguiente mensaje: \"¿Hay articulos Caducos en este Pedido?\" [NO] [SI]");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*Paso 6 Se da clic en el botón : "Si"
		 * Resultado esperado: Se despliega correctamente el apartado de "Articulos Caducos"
		 */
		//danados.pantallaDanados();
		addStep("Se da clic en el botón : \"Si\"\r\n"
				+ "Resultado esperado: Se despliega correctamente el apartado de \"Articulos Caducos\"");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*Paso 7 Se da clic en el boton: "Ingresar codigo SKU"
		 * Resultado esperado: Se despliega un campo flotante con el placeholder: "Escribe el SKU o nombre del articulo"
		 */
		//danados.pantallaDanados();
		addStep("Se da clic en el boton: \"Ingresar codigo SKU\"\r\n"
				+ "Resultado esperado: Se despliega un campo flotante con el placeholder: \"Escribe el SKU o nombre del articulo\"");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 8 Se da clic sobre el campo desplegado y se escriben los primeros 3 caracteres del nombre o sku del articulo ingresado con su maximo verificado en el apartado de Dañados
		 * Resultado esperado: Se despliega un listado de los articulos relacionados a los caracteres ingresados
		 */
		//danados.pantallaDanados();
		addStep("Se da clic sobre el campo desplegado y se escriben los primeros 3 caracteres del nombre o sku del articulo ingresado con su maximo verificado en el apartado de Dañados\r\n"
				+ "Resultado esperado: Se despliega un listado de los articulos relacionados a los caracteres ingresados");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 9 Se da clic sobre el articulo que ingresamos su maximo verificado en el apartado de Dañados
		 * Resultado esperado: Se despliega el siguiente mensaje: "Ingresaste el siguiente articulo: [SKU][NOMBRE DEL ARTICULO] ¿Deseas continuar?" [NO] [SI]
		 */
		//danados.pantallaDanados();
		addStep("Se da clic sobre el articulo que ingresamos su maximo verificado en el apartado de Dañados\r\n"
				+ "Resultado esperado: Se despliega el siguiente mensaje: \"Ingresaste el siguiente articulo: [SKU][NOMBRE DEL ARTICULO] ¿Deseas continuar?\" [NO] [SI]");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 10 Se da clic en el botón : "Si"
		 * Resultado esperado: Se despliega el siguiente mensaje: "El artículo no puede contener una cantidad mayor al número de piezas recibidas" [CONFIRMAR]
		 */
		//danados.pantallaDanados();
		addStep("Se da clic en el botón : \"Si\"\r\n"
				+ "Resultado esperado: Se despliega el siguiente mensaje: \"El artículo no puede contener una cantidad mayor al número de piezas recibidas\" [CONFIRMAR]");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		// cerrar Apps
		Thread.sleep(5000);		
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));
	}

	/*CEDIS MVP3: MTC-FT-030/TABLET ACREDITACIÓN "Modificar Dañados y Caducos" - Agregar y quitar artículos dañados y caducos
	  Desc:Validar que las instrucciones que se muestran en la pantalla de dañados en verificación estén actualizadas de acuerdo al cambio de redacción.
	  @autor: Juan Ibarra
	  @date: 08/12/2024
	 */

	@Override
	public String setTestFullName() {
		return "ATC_FT_032_TABLETACREDITACIONModificarDanadosYCaducosAgregarUnArticuloCaducoConE";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al dar clic en: \"Modificar Dañados y Caducos\", no nos permita agregar como caduco un artículo al cual previamente se ingreso su cantidad máxima verificada como dañados";
	}

	@Override
	public String setTestDesigner() {
		return "Juan Ibarra";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1.-Haber recepcionado un pedido con cajas/canastillas\r\n"
				+ "2.-Haber verificado ese mismo pedido\r\n"
				+ "3.-Haber ingresado el máximo verificado de un artículo como dañado/s para que no nos dejé ingresarlo como artículo caduco";
	}

	@Override
	public void beforeTest() {
	}

}