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
 * Validar el comportamiento correcto de las secciones: "Devolucion de mercancia" y "Devolucion de contenedores", al momento de no tener BOL y detonar candados
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_095_HandHeldDevolucionDeMercanciaYContenedores:CandadosConIngresoManual
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


public class ATC_FT_095_HandheldDevolucionDeMercanciaYContenedoresCandadosConIngresoManual extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_095_HandheldDevolucionDeMercanciaYContenedoresCandadosConIngresoManual_test(
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
		/*Paso 1 Se ubica en la pantalla: "Devoluciones a CEDIS", seccion: "Devolucion de contenedores", subseccion: "Canastillas"
		 * Resultado esperado: Se despliega correctamente la seccion: "Devolucion de contenedores", subseccion: "Canastillas"
		 */
		addStep("Se ubica en la pantalla: \"Devoluciones a CEDIS\", seccion: \"Devolucion de contenedores\", subseccion: \"Canastillas\"\r\n"
				+ "Resultado esperado: Se despliega correctamente la seccion: \"Devolucion de contenedores\", subseccion: \"Canastillas\"");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		devolucion.MoverSeccion("Canastillas");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
						
		/*Paso 2 Se da clic en el boton: "+", de cualquier opcion de color de las canastillas
		 * Resultado esperado: Se despliega el recuadro siguiente:
		 * *Mensaje: "Es necesario primero seleccionar un BOL para registrar contenedores"
		 * *Boton: "Aceptar"
		 */	
		addStep("Se da clic en el boton: \"+\", de cualquier opcion de color de las canastillas\r\n"
				+ "Resultado esperado: Se despliega el recuadro siguiente:\r\n"
				+ "*Mensaje: \"Es necesario primero seleccionar un BOL para registrar contenedores\"\r\n"
				+ "*Boton: \"Aceptar\"");							
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		devolucion.SeleccionarObjeto("Mas Azul");
		
		
		/*Paso 3 Se da clic en el boton: "Aceptar"
		 * Resultado esperado: Se cierra el recuadro desplegado
		 */				
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		devolucion.MensajeBolNecesario("Se da clic en el boton: \"Aceptar\"\r\n"
				+ "Resultado esperado: Se cierra el recuadro desplegado");
		
		/*Paso 4 Se desplaza hacia la seccion: "Devolucion de contenedores", subseccion: "Contenedores"
		 * Resultado esperado: Se despliega correctamente la seccion: "Devolucion de contenedores", subseccion: "Contenedores"
		 */		
		addStep("Se desplaza hacia la seccion: \"Devolucion de contenedores\", subseccion: \"Contenedores\"\r\n"
				+ "Resultado esperado: Se despliega correctamente la seccion: \"Devolucion de contenedores\", subseccion: \"Contenedores\"");
		devolucion.MoverSeccion("Contenedores");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
						
		/*Paso 5 Se da clic en el boton: "+", de cualquier opcion de tipo de contenedores
		 * Resultado esperado: Se despliega el recuadro siguiente:
		 * *Mensaje: "Es necesario primero seleccionar un BOL para registrar contenedores"
		 * *Boton: "Aceptar"
		 */		
		addStep("Se da clic en el boton: \"+\", de cualquier opcion de tipo de contenedores\r\n"
				+ "Resultado esperado: Se despliega el recuadro siguiente:\r\n"
				+ "*Mensaje: \"Es necesario primero seleccionar un BOL para registrar contenedores\"\r\n"
				+ "*Boton: \"Aceptar\"");		
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));			
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		devolucion.SeleccionarObjeto("Mas Tapas");
		
		/*Paso 6 Se da clic en el boton: "Aceptar"
		 * Resultado esperado: Se cierra el recuadro desplegado
		 */
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		devolucion.MensajeBolNecesario("Se da clic en el boton: \"Aceptar\"\r\n"
				+ "Resultado esperado: Se cierra el recuadro desplegado");
		
		//Se agrega un bol 
		Runtime.getRuntime().exec("adb shell input swipe 100 500 100 1450 100");// Se desliza hacia arriba de la pantalla
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		devolucion.SeleccionarPrimerBol("SinEvidencia", null);
					
		/*Paso 7 Se desplaza hacia la seccion: "Devolucion de contenedores", subseccion: "Toma de fotografia"
		 * Resultado esperado: Se despliega correctamente la seccion: "Devolucion de contenedores", subseccion: "Toma de fotografia", placeholder de toma de fotografia: "Evidencia del pedido"
		 */		
		addStep("Se desplaza hacia la seccion: \"Devolucion de contenedores\", subseccion: \"Toma de fotografia\"\r\n"
				+ "Resultado esperado: Se despliega correctamente la seccion: \"Devolucion de contenedores\", subseccion: \"Toma de fotografia\", placeholder de toma de fotografia: \"Evidencia del pedido\"");
		devolucion.MoverSeccion("Canastilla Devolucion");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 8 Se da clic en el boton con icono de camara
		 * Resultado esperado: Se despliega el recuadro siguiente:
		 * *Mensaje: "Indica los contenedores a devolver antes de tomar la fotografia"
		 * *Boton: "Aceptar"
		 */		
		addStep("Se da clic en el boton con icono de camara\r\n"
				+ "Resultado esperado: Se despliega el recuadro siguiente:\r\n"
				+ "*Mensaje: \"Indica los contenedores a devolver antes de tomar la fotografia\"\r\n"
				+ "*Boton: \"Aceptar\"");
		devolucion.SeleccionarObjeto("Foto Devolucion");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
				
		/*Paso 9 Se da clic en el boton: "Aceptar"
		 * Resultado esperado: Se cierra el recuadro desplegado
		 */		
		devolucion.MensajeIndicaContenedor("Se da clic en el boton: \"Aceptar\"\r\n"
				+ "Resultado esperado: Se cierra el recuadro desplegado");		
		
		//Se regresa hacía arriba para borrar el bol 
		Runtime.getRuntime().exec("adb shell input swipe 100 500 100 1450 100");// Se desliza hacia arriba de la pantalla
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		devolucion.SeleccionarPrimerBol("SinEvidencia", null);
		
		/*Paso 10 Se desplaza hacia la seccion: "Canastillas con devolucion"
		 * Resultado esperado: Se despliega correctamente la seccion: "Canastillas con devolucion", campo de ingreso de contenedores con el placeholder: "Ingresa codigo de contenedor"
		 */		
		addStep("Se desplaza hacia la seccion: \"Canastillas con devolucion\"\r\n"
				+ "Resultado esperado: Se despliega correctamente la seccion: \"Canastillas con devolucion\", campo de ingreso de contenedores con el placeholder: \"Ingresa codigo de contenedor\"");
		devolucion.MoverSeccion("Continuar");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 11 Se da clic en el campo de ingreso de contenedores con el placeholder: "Ingresa codigo de contenedor"
		 * Resultado esperado: Se despliega el recuadro siguiente:
		 * *Mensaje: "Es necesario primero seleccionar un BOL para registrar contenedores"
		 * *Boton: "Aceptar"
		 */
		devolucion.IngresarCanastilla("56654");
		/**addStep("Se da clic en el campo de ingreso de contenedores con el placeholder: \"Ingresa codigo de contenedor\"\r\n"
				+ "Resultado esperado: Se despliega el recuadro siguiente:\r\n"
				+ "*Mensaje: \"Es necesario primero seleccionar un BOL para registrar contenedores\"\r\n"
				+ "*Boton: \"Aceptar\"");
				*/
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		devolucion.MensajeBolNecesario("Se da clic en el campo de ingreso de contenedores con el placeholder: \"Ingresa codigo de contenedor\"\r\n"
				+ "Resultado esperado: Se despliega el recuadro siguiente:\r\n"
				+ "*Mensaje: \"Es necesario primero seleccionar un BOL para registrar contenedores\"\r\n"
				+ "*Boton: \"Aceptar\"");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));
	}


	@Override
	public String setTestFullName() {
		return "ATC-FT-095-HandHeld Devolucion de mercancia y contenedores: Candados con ingreso manual";
	}

	@Override
	public String setTestDescription() {
		return "Validar el comportamiento correcto de las secciones: \"Devolucion de mercancia\" y \"Devolucion de contenedores\", al momento de no tener BOL y detonar candados	";
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