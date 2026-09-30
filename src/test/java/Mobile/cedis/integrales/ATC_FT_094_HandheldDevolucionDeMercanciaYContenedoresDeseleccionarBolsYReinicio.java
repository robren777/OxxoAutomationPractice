package Mobile.cedis.integrales;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

/**
 * DESCRIPCIÓN:
 * Validar el comportamiento correcto de las secciones: "Devolucion de mercancia" y "Devolucion de contenedores", al momento de deseleccionar BOL
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_094_HandHeldDevolucionDeMercanciaYContenedores:DeseleccionarBOLsYreinicio
 *
 * PRERREQUISITOS:
 * 1. Tener devoluciones tipo normal y especial programada, que abarquen los ultimos 14 dias y fechas mas antiguas
 * 2. Tener BOL seleccionado
 * 3. Tener datos ingresados en canastillas y contenedores
 * 4. Tener la fotografia tomada
 *
 * @author 
 * @date 
 */


public class ATC_FT_094_HandheldDevolucionDeMercanciaYContenedoresDeseleccionarBolsYReinicio extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_094_HandheldDevolucionDeMercanciaYContenedoresDeseleccionarBolsYReinicio_test(
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
		devolucion.SeleccionarPrimerBol("SinEvidencia", null);
		devolucion.AumentarCanastilla(1, "Azul");
		devolucion.MoverSeccion("Contenedores");
		devolucion.AumentarContenedores(3, "Tapas");
		Runtime.getRuntime().exec("adb shell input swipe 100 500 100 1450 100");// Se desliza hacia arriba de la pantalla 
		
		/*Paso 1 Se ubica en la pantalla: "Devoluciones a CEDIS", seccion: "Devolucion de mercancia"
		 * Resultado esperado: Se despliega correctamente la siguiente seccion:
		 * *seccion: "Devolucion de mercancia", con el boton siguiente:
		 * *Boton: "Seleccionar BOL"
		 */
		addStep("Se ubica en la pantalla: \"Devoluciones a CEDIS\", seccion: \"Devolucion de mercancia\"\r\n"
				+ "Resultado esperado: Se despliega correctamente la siguiente seccion:\r\n"
				+ "*seccion: \"Devolucion de mercancia\", con el boton siguiente:\r\n"
				+ "*Boton: \"Seleccionar BOL\"");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
						
		/*Paso 2 Se da clic en el boton: "Seleccionar BOL"
		 * Resultado esperado: Se despliega una ventana emergente con las especificaciones siguientes:
		 * *Listado de BOLs generados en tienda (especiales y normales), correspondientes a los ultimos 14 dias, ordenados desde el mas reciente al mas antiguo
		 * *Cada BOL listado, cuenta con un checkbox para seleccionarlo
		 * *Al final del listado se despliega el boton siguiente: "Aceptar"
		 */	
		addStep("Se da clic en el boton: \"Seleccionar BOL\"\r\n"
				+ "Resultado esperado: Se despliega una ventana emergente con las especificaciones siguientes:\r\n"
				+ "*Listado de BOLs generados en tienda (especiales y normales), correspondientes a los ultimos 14 dias, ordenados desde el mas reciente al mas antiguo\r\n"
				+ "*Cada BOL listado, cuenta con un checkbox para seleccionarlo\r\n"
				+ "*Al final del listado se despliega el boton siguiente: \"Aceptar\"");
		Runtime.getRuntime().exec("adb shell input swipe 20 1003 20 900 90");// Se desliza un poco hacía abajo
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 3 Se deselecciona el o los BOLs del listado desplegado seleccionados, mediante el checkbox correspondiente
		 * Resultado esperado: Se deselecciona la casilla tipo: "Checkbox", del BOL o BOLs, seleccionados
		 */
		devolucion.SeleccionarPrimerBol("tomaEvidencia", "Se deselecciona el o los BOLs del listado desplegado seleccionados, mediante el checkbox correspondiente"
				+ "Resultado esperado: Se deselecciona la casilla tipo: \"Checkbox\", del BOL o BOLs, seleccionados");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		
		/*Paso 4 Se da clic en el boton: "Aceptar"
		 * Resultado esperado: Se cierra la ventana emergente del listado de BOLs y no se guarda ningun BOL
		 */		
		addStep("Se da clic en el boton: \"Aceptar\"\r\n"
				+ "Resultado esperado: Se cierra la ventana emergente del listado de BOLs y no se guarda ningun BOL");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
						
		/*Paso 5 Se desplaza hacia la seccion: "Devolucion de contenedores", subseccion: "Canastillas"
		 * Resultado esperado: Se han reseteado los valores ingresados correctamente de la seccion: "Devolucion de contenedores", subseccion: "Canastillas"
		 */		
		addStep("Se desplaza hacia la seccion: \"Devolucion de contenedores\", subseccion: \"Canastillas\"\r\n"
				+ "Resultado esperado: Se han reseteado los valores ingresados correctamente de la seccion: \"Devolucion de contenedores\", subseccion: \"Canastillas\"");		
		devolucion.MoverSeccion("Canastillas");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 6 Se desplaza hacia la seccion: "Devolucion de contenedores", subseccion: "Contenedores"
		 * Resultado esperado: Se han reseteado los valores ingresados correctamente de la seccion: "Devolucion de contenedores", subseccion: "Contenedores"
		 */
		addStep("Se desplaza hacia la seccion: \"Devolucion de contenedores\", subseccion: \"Contenedores\"\r\n"
				+ "Resultado esperado: Se han reseteado los valores ingresados correctamente de la seccion: \"Devolucion de contenedores\", subseccion: \"Contenedores\"");
		devolucion.MoverSeccion("Contenedores");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
				
		/*Paso 7 Se desplaza hacia la seccion: "Devolucion de contenedores", subseccion: "Toma de fotografia"
		 * Resultado esperado: Se ha borrado la fotografia tomada correctamente de la seccion: "Devolucion de contenedores", subseccion: "Toma de fotografia"
		 */		
		addStep("Se desplaza hacia la seccion: \"Devolucion de contenedores\", subseccion: \"Toma de fotografia\"\r\n"
				+ "Resultado esperado: Se ha borrado la fotografia tomada correctamente de la seccion: \"Devolucion de contenedores\", subseccion: \"Toma de fotografia\"");
		devolucion.MoverSeccion("Canastilla Devolucion");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));
	}

	@Override
	public String setTestFullName() {
		return "ATC-FT-094-HandHeld Devolucion de mercancia y contenedores: Deseleccionar BOLs y reinicio";
	}

	@Override
	public String setTestDescription() {
		return "Validar el comportamiento correcto de las secciones: \"Devolucion de mercancia\" y \"Devolucion de contenedores\", al momento de deseleccionar BOL";
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
		return "1.-Tener devoluciones tipo normal y especial programada, que abarquen los ultimos 14 dias y fechas mas antiguas\r\n"
				+ "2.-Tener BOL seleccionado\r\n"
				+ "3.-Tener datos ingresados en canastillas y contenedores\r\n"
				+ "4.-Tener la fotografia tomada";
	}

	@Override
	public void beforeTest() {
	}

}