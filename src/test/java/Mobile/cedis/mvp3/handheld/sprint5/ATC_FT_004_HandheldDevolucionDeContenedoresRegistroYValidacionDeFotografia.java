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

public class ATC_FT_004_HandheldDevolucionDeContenedoresRegistroYValidacionDeFotografia extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_004_HandheldDevolucionDeContenedoresRegistroYValidacionDeFotografia_test(
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
		
		/*Paso 1 Se ubica en la pantalla: "Devoluciones a CEDIS", seccion: "Devolucion de contenedores", subseccion: "Toma de fotografia"
		 * Resultado esperado: Se despliega correctamente la seccion: "Devolucion de contenedores", subseccion: "Toma de fotografia", placeholder de toma de fotografia: "Evidencia del pedido"
		 */		
		Thread.sleep(2000);
		addStep("Se ubica en la pantalla: \"Devoluciones a CEDIS\", seccion: \"Devolucion de contenedores\", subseccion: \"Toma de fotografia\"\r\n"
				+ "Resultado esperado: Se despliega correctamente la seccion: \"Devolucion de contenedores\", subseccion: \"Toma de fotografia\", placeholder de toma de fotografia: \"Evidencia del pedido\"");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
						
		/*Paso 2 Se da clic en el boton con icono de camara y se cancela la toma de fotografia
		 * Resultado esperado: Se despliega el mensaje tipo: "Toast", siguiente:
		 * *Mensaje: "El usuario cancelo la operacion"
		 */
		devolucion.CancelarFotoDevolucion("Se da clic en el boton con icono de camara y se cancela la toma de fotografia\r\n"
				+ "Resultado esperado: Se despliega el mensaje tipo: \"Toast\", siguiente: *Mensaje: \"El usuario cancelo la operacion\"");		
		
		/*Paso 3 Se da clic en el boton con icono de camara y se realiza la toma de fotografia
		 * Resultado esperado: Se despliega el guardado de la fotografia correctamente de la forma siguiente:
		 * *Placeholder de toma de fotografia cambia su texto al nombre correspondiente a la fotografia
		 * *Debajo del placeholder y el boton con icono de camara, se despliega el nombre de la fotografia  con un boton con el icono: "X"
		 */		
		addStep("Se da clic en el boton con icono de camara y se realiza la toma de fotografia\r\n"
				+ "Resultado esperado: Se despliega el guardado de la fotografia correctamente de la forma siguiente:\r\n"
				+ "*Placeholder de toma de fotografia cambia su texto al nombre correspondiente a la fotografia\r\n"
				+ "*Debajo del placeholder y el boton con icono de camara, se despliega el nombre de la fotografia  con un boton con el icono: \"X\"");
		devolucion.TomarFotoDevolucion();
		Thread.sleep(2000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 4 Se da clic en el boton para eliminar la fotografia tomada: "X"
		 * Resultado esperado: Se elimina la fotografia tomada correctamente
		 */		
		addStep("Se da clic en el boton para eliminar la fotografia tomada: \"X\"\r\n"
				+ "Resultado esperado: Se elimina la fotografia tomada correctamente");
		devolucion.SeleccionarObjeto("Eliminar Foto Devolucion");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
						
		/*Paso 5 Se da clic en el boton con icono de camara y se realiza la toma de fotografia
		 * Resultado esperado: Se despliega el guardado de la fotografia correctamente de la forma siguiente:
		 * *Placeholder de toma de fotografia cambia su texto al nombre correspondiente a la fotografia
		 * *Debajo del placeholder y el boton con icono de camara, se despliega el nombre de la fotografia  con un boton con el icono: "X"
		 */		
		addStep("Se da clic en el boton con icono de camara y se realiza la toma de fotografia\r\n"
				+ "Resultado esperado: Se despliega el guardado de la fotografia correctamente de la forma siguiente:\r\n"
				+ "*Placeholder de toma de fotografia cambia su texto al nombre correspondiente a la fotografia\r\n"
				+ "*Debajo del placeholder y el boton con icono de camara, se despliega el nombre de la fotografia  con un boton con el icono: \"X\"");		
		devolucion.TomarFotoDevolucion();
		Thread.sleep(2000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 6 Se da clic en el boton con icono de camara 
		 * Resultado esperado: Se despliega el recuadro siguiente:
		 * *Mensaje: "Solo se permite agregar maximo 1 foto"
		 * *Boton: "Aceptar"
		 */		
		devolucion.SoloUnaFotoDevolucion("Se da clic en el boton con icono de camara\r\n"
				+ "Resultado esperado: Se despliega el recuadro siguiente:\r\n"
				+ "*Mensaje: \"Solo se permite agregar maximo 1 foto\"\r\n"
				+ "*Boton: \"Aceptar\"");
		
		/*Paso 7Se da clic en el boton: "Aceptar"
		 * Resultado esperado: Se cierra el recuadro desplegado
		 */		
		addStep("Se da clic en el boton: \"Aceptar\"\r\n"
				+ "Resultado esperado: Se cierra el recuadro desplegado");
		devolucion.SeleccionarObjeto("Aceptar MensajeUnaFoto");
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
		return "ATC_FT_004_HandheldDevolucionDeContenedoresRegistroYValidacionDeFotografia";
	}

	@Override
	public String setTestDescription() {
		return "Validar el comportamiento correcto de la seccion: \"Devolucion de contenedores\", subseccion: \"Toma de fotografia\"";
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
				+ "3.-Tener datos ingresados en canastillas y/o contenedores";
	}

	@Override
	public void beforeTest() {
	}

}