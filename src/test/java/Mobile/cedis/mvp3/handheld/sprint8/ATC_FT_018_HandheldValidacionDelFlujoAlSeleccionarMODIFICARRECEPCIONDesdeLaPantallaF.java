package Mobile.cedis.mvp3.handheld.sprint8;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_018_HandheldValidacionDelFlujoAlSeleccionarMODIFICARRECEPCIONDesdeLaPantallaF extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_018_HandheldValidacionDelFlujoAlSeleccionarMODIFICARRECEPCIONDesdeLaPantallaF_test(
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
		cap.setCapability("contenedor", data.get("contenedor"));

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

		// Ejecuta el logeo hacia el punto de venta
		Mobile.cedis.om.A01_Login_Punto_De_Venta login = new Mobile.cedis.om.A01_Login_Punto_De_Venta(testCase, appiumUtil, this);

		login.loginPV(data.get("ip"));

		// Ejecuta el logeo del lider
		Mobile.cedis.om.A02_Login_Lider loginLider = new Mobile.cedis.om.A02_Login_Lider(testCase, appiumUtil, this);
		// Obtiene valor del DataProvaider
		loginLider.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));

		// Ejecuta los pasos para llegar al menu de Herramientas de CEDIS
		Mobile.cedis.om.A03_Menus_y_submenus herramienta = new Mobile.cedis.om.A03_Menus_y_submenus(testCase, appiumUtil, this);
		herramienta.logeoInicioHerramientas();
		//Mobile.cedis.om.Tablet_A03_Menus_y_submenus herramienta = new Mobile.cedis.om.Tablet_A03_Menus_y_submenus(testCase, appiumUtil, this);
		//herramienta.logeoInicioHerramientas();		
		

		Mobile.cedis.om.A04_MenuHerramientas menuCedis = new Mobile.cedis.om.A04_MenuHerramientas(testCase, appiumUtil, this);
		menuCedis.clickBtnRecepcionPedidosCEDIS();// Selecciona la opcion de Recepción de Pedidos CCEDIS
		
		Mobile.cedis.om.A05_LoginAreaDistribucion loginRuta = new Mobile.cedis.om.A05_LoginAreaDistribucion(testCase, appiumUtil, this);
		loginRuta.LoginAreaDistribucion();//Se logea en Area de Distribución sin tomar evidencia
		
		Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia transferencia = new Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia(testCase, appiumUtil, this);
		transferencia.BuscarPrineraTransferencia(true, data.get("ip"));//Se selecciona la primera transferencia sin tomar evidencia		

		/*Paso 1.-Se inicia el caso por parte del usuario en la pantalla de FUE de una transferencia iniciada el flujo por la opción de Devoluciones a CEDIS
		 * Resultado esperado: Se muestra la pantalla de Formato Único de Entrega con sus campos correspondientes.
		 * Se muestran los botones de
		 * -"MODIFICAR RECEPCIÓN"
		 * -"MODIFICAR DEVOLUCIONES"
		 * -"TERMINAR"
		 */		
		addStep("Se inicia el caso por parte del usuario en la pantalla de FUE de una transferencia iniciada el flujo por la opción de Devoluciones a CEDIS \r\n"
				+ "Resultado esperado: Se muestra la pantalla de Formato Único de Entrega con sus campos correspondientes.\r\n"
				+ "Se muestran los botones de \r\n"
				+ "-\"MODIFICAR RECEPCIÓN\"\r\n"
				+ "-\"MODIFICAR DEVOLUCIONES\" \r\n"
				+ "-\"TERMINAR\"");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");					
		
		
		/*Paso 2 -Usuario selecciona el botón de "MODIFICAR RECEPCIÓN"
		 * Resultado esperado: Se envía el caso a la pantalla de Pantalla Recepción Pedidos  a CEDIS en la cual se carga la información capturada previamente.
		 */		
		addStep("Usuario selecciona el botón de \"MODIFICAR RECEPCIÓN\"\r\n"
				+ "Resultado esperado: Se envía el caso a la pantalla de Pantalla Recepción Pedidos  a CEDIS en la cual se carga la información capturada previamente.");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 3.-Se valida que se muestre el llenado de información de pantalla de Recepción Pedidos a CEDIS   donde se actualiza la información de cajas y de la sección de Recepción Cajas sin etiqueta / Etiqueta dañada para después  seleccionar Continuar.
		 * Resultado esperado: Se muestra la sección de Sobrantes / Dañados con la información precargada
		 */		
		addStep("Se valida que se muestre el llenado de información de pantalla de Recepción Pedidos a CEDIS   donde se actualiza la información de cajas y de la sección de Recepción Cajas sin etiqueta / Etiqueta dañada para después  seleccionar Continuar.\r\n"
				+ "Resultado esperado: Se muestra la sección de Sobrantes / Dañados con la información precargada");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 4.-Se valida que se muestre el llenado de información de la sección de Sobrantes / Dañados para después  seleccionar Terminar una vez actualizada la información en pantalla
		 * Resultado esperado: Se muestra la pantalla de Resumen Recepción con la información precargada con la información actualizada
		 */		
		addStep("Se valida que se muestre el llenado de información de la sección de Sobrantes / Dañados para después  seleccionar Terminar una vez actualizada la información en pantalla\r\n"
				+ "Resultado esperado: Se muestra la pantalla de Resumen Recepción con la información precargada con la información actualizada");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");					
		
		
		/*Paso 5.-Se observa la información correspondiente a la pantalla de Resumen  Recepción y se selecciona el botón de Firma de Acuerdo
		 * Resultado esperado: Se muestra la pantalla de Acuerdo de Entrega en la cual se muestra la información correspondiente y donde se mostrara el checkbox de "cumplimiento de proceso de entrega" al final de la pantalla, arriba del botón "TERMINAR RECEPCIÓN".
		 */		
		addStep("Se observa la información correspondiente a la pantalla de Resumen  Recepción y se selecciona el botón de Firma de Acuerdo\r\n"
				+ "Resultado esperado: Se muestra la pantalla de Acuerdo de Entrega en la cual se muestra la información correspondiente y donde se mostrara el checkbox de \"cumplimiento de proceso de entrega\" al final de la pantalla, arriba del botón \"TERMINAR RECEPCIÓN\".");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 6.-Se realiza la firma de Acuerdo de  Entrega y se selecciona el botón de TERMINAR RECEPCIÓN
		 * Resultado esperado: Se muestra la pantalla de Formato Único de Entrega con sus campos correspondientes mostrando actualizada información
		 * Se muestran los botones de
		 * -"MODIFICAR RECEPCIÓN"
		 * -"MODIFICAR DEVOLUCIONES"
		 * -"TERMINAR"
		 */		
		addStep("Se realiza la firma de Acuerdo de  Entrega y se selecciona el botón de TERMINAR RECEPCIÓN\r\n"
				+ "Resultado esperado: Se muestra la pantalla de Formato Único de Entrega con sus campos correspondientes mostrando actualizada información .\r\n"
				+ "Se muestran los botones de \r\n"
				+ "-\"MODIFICAR RECEPCIÓN\"\r\n"
				+ "-\"MODIFICAR DEVOLUCIONES\" \r\n"
				+ "-\"TERMINAR\"");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		
		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));
	}


	@Override		
	public String setTestFullName() {
		return "ATC_FT_018_HandheldValidacionDelFlujoAlSeleccionarMODIFICARRECEPCIONDesdeLaPantallaF";
	}

	@Override
	public String setTestDescription() {
		return "Caso de prueba en el que se valida el flujo a tomar cuando se seleccione la opción de MODIFICAR RECEPCIÓN en la pantalla de FUE";
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
		return "1.-Contar con Transferencias por atender en la handheld\r\n"
				+ "2.-Haber iniciado el flujo de la transferencia con la opción de Devoluciones a CEDIS \r\n"
				+ "3.-Contar con la transferencia en la pantalla de FUE";
	}

	@Override
	public void beforeTest() {
	}

}