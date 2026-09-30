package Mobile.cedis.mvp3.handheld.sprint5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_001_HandheldDevolucionDeMercanciaRegistroYValidacionDeBols extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_001_HandheldDevolucionDeMercanciaRegistroYValidacionDeBols_test(
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

		Mobile.cedis.om.A04_MenuHerramientas menuCedis = new Mobile.cedis.om.A04_MenuHerramientas(testCase, appiumUtil, this);
		menuCedis.clickBtnRecepcionPedidosCEDIS();// Selecciona la opcion de Recepción de Pedidos CCEDIS
		
		Mobile.cedis.om.A05_LoginAreaDistribucion loginRuta = new Mobile.cedis.om.A05_LoginAreaDistribucion(testCase, appiumUtil, this);
		loginRuta.LoginAreaDistribucion();//Se logea en Area de Distribución sin tomar evidencia
		
		Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia transferencia = new Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia(testCase, appiumUtil, this);
		//transferencia.BuscarPrineraTransferencia(true, data.get("ip"));//Se selecciona la primera transferencia sin tomar evidencia
		transferencia.BuscarTransferencia(true,data.get("numTransferencia"));
		
		Mobile.cedis.om.B10_DeseaComenzar DeseaComenzar = new Mobile.cedis.om.B10_DeseaComenzar(testCase, appiumUtil, this);
		DeseaComenzar.Cargando();
		DeseaComenzar.SeleccionarObjeto("Recepcion Pedido"); 
		
		Mobile.cedis.om.B02_RecepcionPedidosCEDIS pantallatransferencia = new Mobile.cedis.om.B02_RecepcionPedidosCEDIS(testCase, appiumUtil, this);
		pantallatransferencia.DeslizarArriba();
		pantallatransferencia.Tomar3Fotos();
		pantallatransferencia.CapturaContenedor(data.get("contenedor"));//Se captura los contenedores que se encuentran en el excel
		pantallatransferencia.TerminarHastaResumen();
		
		Mobile.cedis.om.B03_ResumenPedidosCEDIS resumen = new Mobile.cedis.om.B03_ResumenPedidosCEDIS(testCase, appiumUtil, this);
		resumen.FirmaAcuerdoSinEvidencia();
		
		Mobile.cedis.om.B04_AcuerdoEntrega acuerdo = new Mobile.cedis.om.B04_AcuerdoEntrega(testCase, appiumUtil, this);
		acuerdo.FirmaAreaDistribucion("SinEvidencia", null);
		acuerdo.FirmaPersonalTienda("SinEvidencia", null);
		acuerdo.TerminarRecepcion("SinEvidencia", null);
		
		
		Mobile.cedis.om.B05_DevolucionMercancia devolucion = new Mobile.cedis.om.B05_DevolucionMercancia(testCase, appiumUtil, this);

		/*Paso 1 Se ubica en la pantalla: "Devoluciones a CEDIS"
		 * Resultado Esperado: Se despliega correctamente la siguiente seccion:
		 * *seccion: informacion de tienda, con los datos de ejemplo siguientes:
		 * *Fecha: [1-Enero-2024 10:19]
		 * *Ruta: [123]
		 * *CR Tienda: [10GUD50GAT]
		 * *Nombre Tienda: [Abastos GDL]
		 * *Lider Tienda: [lider]
		 * *Transferencia: [3132123800]
		 */
		Thread.sleep(500);
		devolucion.ValidacionEncabezado();
		addStep("Se ubica en la pantalla: \"Devoluciones a CEDIS\"\r\n"
				+ "Resultado Esperado: Se despliega correctamente la siguiente seccion:\r\n"
				+ "*seccion: informacion de tienda, con los datos de ejemplo siguientes:\r\n"
				+ "*Fecha: [1-Enero-2024 10:19]\r\n"
				+ "*Ruta: [123]\r\n"
				+ "*CR Tienda: [10GUD50GAT]\r\n"
				+ "*Nombre Tienda: [Abastos GDL]\r\n"
				+ "*Lider Tienda: [lider]\r\n"
				+ "*Transferencia: [3132123800]");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*Paso 2 Se desplaza hacia la seccion: "Devolucion de mercancia"
		 * Resultado Esperado: Se despliega correctamente la siguiente seccion:
		 * *seccion: "Devolucion de mercancia", con el boton siguiente:
		 * *Boton: "Seleccionar BOL"
		 */
				
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 29 900 40");// Se desliza hacía abajo
		addStep("Se desplaza hacia la seccion: \"Devolucion de mercancia\"\r\n"
				+ "Resultado Esperado: Se despliega correctamente la siguiente seccion:\r\n"
				+ "*seccion: \"Devolucion de mercancia\", con el boton siguiente:\r\n"
				+ "*Boton: \"Seleccionar BOL\"");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 3 Se da clic en el boton: "Seleccionar BOL"
		 * Resultado Esperado: Se despliega una ventana emergente con las especificaciones siguientes:
		 * *Listado de BOLs generados en tienda (especiales y normales), correspondientes a los ultimos 14 dias, ordenados desde el mas reciente al mas antiguo
		 * *Cada BOL listado, cuenta con un checkbox para seleccionarlo
		 * *Al final del listado se despliega el boton siguiente: "Aceptar"
		 */
		
		addStep("Se da clic en el boton: \"Seleccionar BOL\"\r\n"
				+ "Resultado Esperado: Se despliega una ventana emergente con las especificaciones siguientes:\r\n"
				+ "*Listado de BOLs generados en tienda (especiales y normales), correspondientes a los ultimos 14 dias, ordenados desde el mas reciente al mas antiguo\r\n"
				+ "*Cada BOL listado, cuenta con un checkbox para seleccionarlo\r\n"
				+ "*Al final del listado se despliega el boton siguiente: \"Aceptar\"");		
		devolucion.SeleccionarObjeto("SeleccionarBol");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		//Pendiente validar que solo muestre los últimos 14 días de BOls

		/*Paso 4 Se da clic en el boton: "Aceptar"
		 * Resultado Esperado: Se cierra la ventana emergente del listado de BOLs y no se guarda ningun BOL
		 */
		
		addStep("Se da clic en el boton: \"Aceptar\"\r\n"
				+ "Resultado Esperado: Se cierra la ventana emergente del listado de BOLs y no se guarda ningun BOL");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 5 Se da clic nuevamente en el boton: "Seleccionar BOL"
		 * Resultado Esperado: Se despliega una ventana emergente con las especificaciones siguientes:
		 * *Listado de BOLs generados en tienda (especiales y normales), correspondientes a los ultimos 14 dias, ordenados desde el mas reciente al mas antiguo
		 * *Cada BOL listado, cuenta con un checkbox para seleccionarlo
		 * *Al final del listado se despliega el boton siguiente: "Aceptar"
		 */
		
		addStep("Se da clic nuevamente en el boton: \"Seleccionar BOL\"\r\n"
				+ "Resultado Esperado: Se despliega una ventana emergente con las especificaciones siguientes:\r\n"
				+ "*Listado de BOLs generados en tienda (especiales y normales), correspondientes a los ultimos 14 dias, ordenados desde el mas reciente al mas antiguo\r\n"
				+ "*Cada BOL listado, cuenta con un checkbox para seleccionarlo\r\n"
				+ "*Al final del listado se despliega el boton siguiente: \"Aceptar\"");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*Paso 6 Se selecciona uno o mas BOLs del listado desplegado, mediante el checkbox correspondiente
		 * Resultado Esperado: Se selecciona la casilla tipo: "Checkbox", del BOL o BOLs, seleccionados
		 */
		
		addStep("Se selecciona uno o mas BOLs del listado desplegado, mediante el checkbox correspondiente\r\n"
				+ "Resultado Esperado: Se selecciona la casilla tipo: \"Checkbox\", del BOL o BOLs, seleccionados");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 7 Se da clic en el boton: "Aceptar"
		 * Resultado Esperado: Se cierra la ventana emergente del listado de BOLs y se despliega debajo del boton: "Seleccionar BOL", el BOL o BOLs, seleccionados anteriormente, en forma de lista no editable
		 */
		
		addStep("Se da clic en el boton: \"Aceptar\"\r\n"
				+ "Resultado Esperado: Se cierra la ventana emergente del listado de BOLs y se despliega debajo del boton: \"Seleccionar BOL\", el BOL o BOLs, seleccionados anteriormente, en forma de lista no editable");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		

		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));

	}

	/*CEDIS MVP3: MTC-FT-030/TABLET ACREDITACIÓN "Modificar Dañados y Caducos" - Agregar y quitar artículos dañados y caducos
	  Desc:Validar que las instrucciones que se muestran en la pantalla de dañados en verificación estén actualizadas de acuerdo al cambio de redacción.
	  @autor: Juan Ibarra
	  @date: 08/12/2024
	 */

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_HandheldDevolucionDeMercanciaRegistroYValidacionDeBols";
	}

	@Override
	public String setTestDescription() {
		return "Validar el inicio de la pantalla: \"Devoluciones a CEDIS\" y comportamiento correcto del boton: \"Seleccionar BOL\"";
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
		return "1.Tener devoluciones tipo normal y especial programada, que abarquen los ultimos 14 dias y fechas mas antiguas";
	}

	@Override
	public void beforeTest() {
	}

}