package Mobile.cedis.integrales;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

/**
import Mobile.cedis.om.A01_Login_Punto_De_Venta;
import Mobile.cedis.om.A02_Login_Lider;
import Mobile.cedis.om.A03_Menus_y_submenus;
import Mobile.cedis.om.A04_MenuHerramientas;
import Mobile.cedis.om.C01_Listado_de_transferencias_a_verificar;
import Mobile.cedis.om.C02_Verificacion_De_Pedido_Y_Permisos;
*/
import Mobile.cedis.om.A03_Menus_y_submenus;
import Mobile.cedis.om.A04_MenuHerramientas;
import Mobile.cedis.om.A05_LoginAreaDistribucion;
import Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia;
import Mobile.cedis.om.B02_RecepcionPedidosCEDIS;
import Mobile.cedis.om.B03_ResumenPedidosCEDIS;
import Mobile.cedis.om.B10_DeseaComenzar;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_146_HandheldCambioDePantallaResumenDeEntrega extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_146_HandheldCambioDePantallaResumenDeEntrega_test(
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
		cap.setCapability("articulo", data.get("articulo"));

		String url = "http://127.0.0.1:4723/";

		// Cerrar Apps
		Runtime.getRuntime().exec("adb shell pm clear " + data.get("appPackage"));

		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n"
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

		 
		// Menu principal hasta Herramientas de COMETA
				A03_Menus_y_submenus MenuOaD = new A03_Menus_y_submenus(testCase, appiumUtil, this);
				System.out.println("Dando clic en Excelencia Operativa -> HC y COMETA\n");
				Thread.sleep(2000);
				MenuOaD.logeoInicioHerramientas();
				Thread.sleep(8000);
		 
				// En el menu de herramientas doy clic en la verificacion de cedis
				A04_MenuHerramientas ModulosOaD = new A04_MenuHerramientas(testCase, appiumUtil, this);
				System.out.println("Dando clic en Recepcion de Pedidos CEDIS\n");
				Thread.sleep(2000);
				ModulosOaD.clickBtnRecepcionPedidosCEDIS();
		 
				// En el menu de herramientas doy clic en la verificacion de cedis
				A05_LoginAreaDistribucion LoginAreaDistribucion = new A05_LoginAreaDistribucion(testCase, appiumUtil, this);
				System.out.println("Logeando Area de Distribucion CEDIS\n");
				Thread.sleep(2000);
				LoginAreaDistribucion.LoginAreaDistribucion();
		 
				B01_RecepcionPedidosSeleccionarTransferencia POM_ListadoDeRecepcion = new B01_RecepcionPedidosSeleccionarTransferencia(
				testCase, appiumUtil, this);
				System.out.println("Entrando a la primera transferencia por Recepcionar de CEDIS\n");
				Thread.sleep(2000);								
				//POM_ListadoDeRecepcion.SeleccionarPrimeraTransferencia(true);//Se comentarizo la linea por parte de Juan para realizar la prueba				 
				POM_ListadoDeRecepcion.BuscarTransferencia(true,data.get("numTransferencia"));//Linea agregada por Juan para realizar la prueba donde se manda un numero de transferencia especifica
				
				B10_DeseaComenzar POM_DeseaComenzar = new B10_DeseaComenzar(testCase, appiumUtil, this);//linea agregada por Juan 
				POM_DeseaComenzar.Cargando();//linea agregada por Juan
				POM_DeseaComenzar.SeleccionarObjeto("Recepcion Pedido");//linea agregada por Juan
								
				B02_RecepcionPedidosCEDIS POM_PedidoRecepcion = new B02_RecepcionPedidosCEDIS(testCase, appiumUtil, this);
				System.out.println("Tomando las 3 evidencias obligatorias del pedido\n");
				Thread.sleep(2000);
				POM_PedidoRecepcion.DeslizarArriba();
				POM_PedidoRecepcion.Tomar3Fotos();
				System.out.println("Ingresando contenedores\n");
				Thread.sleep(2000);
				/** Se comentarizan por parte de Juan 
				POM_PedidoRecepcion.CapturaContenedor("00071118000733555813-7501071120077-1");
				POM_PedidoRecepcion.CapturaContenedor("557206");
				POM_PedidoRecepcion.CapturaContenedor("549652");
				*/
				POM_PedidoRecepcion.CapturaContenedor(data.get("contenedor"));//linea agregada por Juan 
				System.out.println("Terminando Recepcion\n");
				Thread.sleep(2000);
				POM_PedidoRecepcion.SeleccionarObjeto("Terminar Recepcion");
				POM_PedidoRecepcion.SeleccionarObjeto("Terminar");				
				POM_PedidoRecepcion.SeleccionarObjeto("Confirmar");
				POM_PedidoRecepcion.SeleccionarObjeto("Si");
				System.out.println("Ingresando a seccion cajas sin etiquetas\n");
				POM_PedidoRecepcion.SeleccionarObjeto("Registrar");
				POM_PedidoRecepcion.SeleccionarObjeto("Si");
				POM_PedidoRecepcion.SeleccionarObjeto("Check Articulo No Escaneable");
				Thread.sleep(2000);
				POM_PedidoRecepcion.SeleccionarObjeto("Agregar Evidencia");
				POM_PedidoRecepcion.SeleccionarObjeto("Tomar Foto Articulo NoEscaneable");
				POM_PedidoRecepcion.SeleccionarObjeto("Confirmar Foto Articulo No Escaneable");
				POM_PedidoRecepcion.SeleccionarObjeto("Terminar Cajas Sin Etiqueta");	
				POM_PedidoRecepcion.SeleccionarObjeto("Confirmar");
				System.out.println("Ingresando a seccion sobrantes\n");
				POM_PedidoRecepcion.CapturaContenedorSobrantes("7501071120012");
				System.out.println("Ingresando un sobrante\n");
				POM_PedidoRecepcion.CapturaContenedorSobrantes("7501071120013");
				System.out.println("Ingresando otro sobrante\n");
				POM_PedidoRecepcion.CapturaContenedorSobrantes("7501071120014");
				System.out.println("Terminando de capturar los sobrantes\n");
				//System.out.println("Terminando Capturar sobrantes");
				POM_PedidoRecepcion.SeleccionarObjeto("Continuar Dañados");
				Thread.sleep(1000);
				System.out.println("Ingresando a seccion dañados\n");
				//POM_PedidoRecepcion.SeleccionarArticulo("7501071120077","","");//Se comentariza la linea	
				POM_PedidoRecepcion.SeleccionarArticulo(data.get("articulo"), "", "");
				System.out.println("Se ingreso articulo dañado\n");
				POM_PedidoRecepcion.SeleccionarObjeto("Aumentar Dañado");
				Thread.sleep(3000);
				POM_PedidoRecepcion.SeleccionarObjeto("Terminar Dañados");	
				POM_PedidoRecepcion.SeleccionarObjeto("Confirmar Dañados");	
				System.out.println("Se finalizo seccion dañados y sobrantes\n");

				B03_ResumenPedidosCEDIS ResumenRecepcion = new B03_ResumenPedidosCEDIS(testCase, appiumUtil, this);
				/*Paso 1 Posicionarse en la pantalla Recepcion de pedido cedis apartado resumen de entrega de pedido	
				/* Resultado: Se muestra la pantalla correctamente"
				*/
				addStep("Posicionarse en la pantalla Recepcion de pedido cedis apartado resumen de entrega de pedido\r\n" + 
				"Resultado: Se muestra la pantalla correctamente");
				Thread.sleep(3000);				
				ResumenRecepcion.ValidacionEncabezado();
				System.out.println("Se muestra la pantalla Resumen Entrega de Pedido De Recepcion Pedido Cedis\n");
				testCase.addScreenShotCurrentStep(appiumUtil, "testImage");						
				
				/*Paso 2 "Las pantallas que componen al resumen de entrega, Resumen entrega de pedido, entrada de cajas sin etiquetas/etiquetas dañadas, detalle faltantes,  sobrantes, dañados muestran registro de informacion capturada""
				/*Resultado: Se muestra la pantalla y las secciones mencionadas con informacion capturada
				*/
				ResumenRecepcion.SeccionesResumenRecepcion("Las pantallas que componen al resumen de entrega, Resumen entrega de pedido, entrada de cajas sin etiquetas/etiquetas dañadas, detalle faltantes,  sobrantes, dañados muestran registro de informacion capturada\r\n"
						+ "Resultado: Se muestra la pantalla y las secciones mencionadas con informacion capturada");
				System.out.println("Se muestran todas las secciones correspondientes que componen el resumen de entrega\n");
				Runtime.getRuntime().exec("adb shell input swipe 20 1003 20 900 90");// Se desliza un poco hacía abajo
				/*Paso 3 Presionar el boton firma de acuerdo
				/*Resultado: Se actualizo el titulo y aparece como devolucion especial programada
				*/
				addStep("Presionar el boton firma de acuerdo\r\n"
						+ "Resultado: Se habilita la pantalla acuerdo de entrega correctamente");
				ResumenRecepcion.FirmaAcuerdoSinEvidencia();
				System.out.println("Se muestra la pantalla acuerdo de entrega correctamente\n");
				Thread.sleep(2000);
				testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
				
				/*Paso 4 Presionar el boton de retroceso. Este boton tiene forma de flecha hacia la izquierda
				/*Resultado: Nos dirige a la pantalla de resumen entrega de pedido y se verifica que los datos permanezcan guardados correctamente.
				*/

				Runtime.getRuntime().exec("adb shell input keyevent 4");// realiza la acción de Back en la handheld
				Thread.sleep(1000);
				Runtime.getRuntime().exec("adb shell input swipe 100 500 100 1450 100");// Se desliza hacia arriba de la pantalla 
				Thread.sleep(1000);
				ResumenRecepcion.SeccionesResumenRecepcion("\"Presionar el boton de retroceso. Este boton tiene forma de flecha hacia la izquierda\r\n"
						+ "Resultado: Nos dirige a la pantalla de resumen entrega de pedido y se verifica que los datos permanezcan guardados correctamente.");
				System.out.println("Se regresa a la pantalla resumen entrega de pedido y los datos permanezcan guardados correctamente\n");
				testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
				
				// Cerrar Apps
				  System.out.println("Terminando caso y cerrando OaD...");
				  Thread.sleep(1500);
				  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
				
	}
		@Override
		public String setTestFullName() {
			return "ATC_FT_146_HandheldCambioDePantallaResumenDeEntrega";
		}

		@Override
		public String setTestDescription() {
			return "Validar que si el operador distribucion se cambia de pantallas (ir hacia atras o hacia adelante), la pantalla resumen de entrega mantendra la informacion guardada previamente. ";
		}

		@Override
		public String setTestDesigner() {
			return "Ricardo Hdz Rios";
		}

		@Override
		public String setTestInstanceID() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String setPrerequisites() {
			return "1. App Oxxo al dia instalada en Handheld.\r\n"
					+ "2. Usuario y Contraseña de Aplicacion Oxxo al dia.\r\n"
					+ "3. Comunicacion entre HH y POS (red wifi (Handheld) y cableada (POS), que permitan conexion entre ambos dispositivos).\r\n"
					+ "4. P.V con acceso a la Herramienta de compra.\r\n"
					+ "5. Realizar flujo de verificacion hasta llegar a la pantalla de caducos\r\n"
					+ "6. Dispositivo HandHeld\r\n"
					+ "7. Posicionarse en la pantalla de resumen de entrega.\r\n"
					+ "8 Haber ingresado informacion en las pantallas de recepcion.\r\n"
					+ "9.Haber dejado faltantes.\r\n"
					+ "10. Haber ingresado cajas sin etiquetas/dañadas.\r\n"
					+ "11.Haber capturado sobrantes y dañados.";
		}

		@Override
		public void beforeTest() {
		}		

}