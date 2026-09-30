package Mobile.cedis.mvp3.handheld.sprint5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.cedis.om.A03_Menus_y_submenus;
import Mobile.cedis.om.A04_MenuHerramientas;
import Mobile.cedis.om.A05_LoginAreaDistribucion;
import Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia;
import Mobile.cedis.om.B02_RecepcionPedidosCEDIS;
import Mobile.cedis.om.B03_ResumenPedidosCEDIS;
import Mobile.cedis.om.B04_AcuerdoEntrega;
import Mobile.cedis.om.B05_DevolucionMercancia;
import Mobile.cedis.om.B07_ResumenDevolucion;
import Mobile.cedis.om.B08_AcuerdoDevolucion;
import Mobile.cedis.om.B09_FormatoUnicoEntrega;
import Mobile.cedis.om.B10_DeseaComenzar;//linea agregada por Juan
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

/**
import Mobile.cedis.om.A01_Login_Punto_De_Venta;
import Mobile.cedis.om.A02_Login_Lider;
import Mobile.cedis.om.A03_Menus_y_submenus;
import Mobile.cedis.om.A04_MenuHerramientas;
import Mobile.cedis.om.C01_Listado_de_transferencias_a_verificar;
import Mobile.cedis.om.C02_Verificacion_De_Pedido_Y_Permisos;
*/

public class ATC_FT_035_HandheldAjustesFUEApartadoDevolucionDeContenedoresContenedores extends BaseExecution {

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_035_HandheldAjustesFUEApartadoDevolucionDeContenedoresContenedores_test(
			HashMap<String, String> data) throws Exception {
		/*CEDIS MVP3: ATC_FT_035_HandheldAjustesFUEApartadoDevolucionDeContenedoresContenedores
		  Desc: 
		  @autor: Ricardo Hdz
		  @date: 16/10/2024
		 */
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

		 
		// Menu principal hasta Herramientas de COMETA
				A03_Menus_y_submenus MenuOaD = new A03_Menus_y_submenus(testCase, appiumUtil, this);
				System.out.println("Dando clic en Excelencia Operativa -> HC y COMETA\n");
				Thread.sleep(2000);
				MenuOaD.logeoInicioHerramientas();
		 
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
				POM_PedidoRecepcion.CapturaContenedor(data.get("contenedor"));
				System.out.println("Terminando Recepcion\n");
				Thread.sleep(2000);
				POM_PedidoRecepcion.TerminarHastaResumen();
				
				B03_ResumenPedidosCEDIS POM_ResumenRecepcion = new B03_ResumenPedidosCEDIS(testCase, appiumUtil, this);
				System.out.println("Firma de acuerdo\n");
				Thread.sleep(2000);
				POM_ResumenRecepcion.FirmaAcuerdoSinEvidencia();
				
				B04_AcuerdoEntrega POM_FirmaAcuerdoRecepcion = new B04_AcuerdoEntrega(testCase, appiumUtil, this);
				System.out.println("Firma Area Distribucion\n");
				Thread.sleep(1000);
				POM_FirmaAcuerdoRecepcion.FirmaAreaDistribucion("", "");
				System.out.println("Firma Personal de Tienda\n");
				Thread.sleep(1000);
				POM_FirmaAcuerdoRecepcion.FirmaPersonalTienda("", "");
				System.out.println("Terminando Firmas de Acuerdo de Entrega\n");
				Thread.sleep(1000);
				POM_FirmaAcuerdoRecepcion.TerminarRecepcion("", "");
				
				B05_DevolucionMercancia POM_Devolucion = new B05_DevolucionMercancia(testCase, appiumUtil, this);
				System.out.println("Seleccionando BOL\n");
				Thread.sleep(1000);
				//POM_Devolucion.SeleccionarObjeto("Seleccionar Bol");
				//POM_Devolucion.SeleccionarBol("13360", "sin evidencia", "vacio");//Se comentariza para seleccionar el primer Bol
				POM_Devolucion.SeleccionarPrimerBol("Sin Evidencia", "");//agregado por Juan Ibarra
				System.out.println("Ingresando canastillas en Devolucion de contenedores\n");
				Thread.sleep(1000);
				POM_Devolucion.AumentarCanastilla(1, "Azul");
				POM_Devolucion.AumentarCanastilla(2, "Amarilla");
				POM_Devolucion.AumentarCanastilla(3, "Verde");
				POM_Devolucion.AumentarCanastilla(4, "Naranja");
				POM_Devolucion.AumentarCanastilla(5, "Otro");
				System.out.println("Ingresando contenedores en Devolucion de contenedores\n");
				Thread.sleep(1000);
				POM_Devolucion.AumentarContenedores(6, "Tapas");
				POM_Devolucion.AumentarContenedores(7, "Envases");
				POM_Devolucion.TomarFotoDevolucion();
				POM_Devolucion.MoverSeccion("Canastilla Devolucion");
				POM_Devolucion.IngresarCanastilla("6544");
				//Runtime.getRuntime().exec("adb shell input keyevent 66");//realiza el enter en la handheld
				POM_Devolucion.TomarFotoCanastilla();
				POM_Devolucion.MoverSeccion("Continuar");
				POM_Devolucion.SeleccionarObjeto("Continuar");
				B07_ResumenDevolucion Resumen = new B07_ResumenDevolucion(testCase, appiumUtil, this);
				Resumen.ClickFirmaAcuerdo("SinEvidencia", null);
				B08_AcuerdoDevolucion AcuerdoDev = new B08_AcuerdoDevolucion (testCase, appiumUtil, this);
				AcuerdoDev.FirmaAreaDistribucion("", "");
				System.out.println("Firma Personal de Tienda\n");
				Thread.sleep(1000);
				AcuerdoDev.FirmaPersonalTienda("", "");
				System.out.println("Terminando Firmas de Acuerdo de Entrega\n");
				Thread.sleep(1000);
				AcuerdoDev.TerminarRecepcion("", "CEDIS_ConfirmarSeguroTerminarAcuerdo");
				B09_FormatoUnicoEntrega FUE = new B09_FormatoUnicoEntrega (testCase, appiumUtil, this);


				/*Paso 1 Posicionarse en la pantalla "Formato unico de entrega"
				/*Resultado: Se muestra pantalla "Formato unico de entrega"		
				*/
				addStep("Posicionarse en la pantalla Formato unico de entrega\r\n" + 
				"Resultado: Se muestra pantalla Formato unico de entrega");
				FUE.ValidacionEncabezado();
				System.out.println("Se muestra la pantalla FORMATO ÚNICO DE ENTREGA");
				testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
				
				/*Paso 2 Dentro de la pantalla se encuentra la seccion: devolucion de contenedores
                /*Resultado: Se muestra la seccion devolucion de contenedores
				*/
				addStep("Dentro de la pantalla se encuentra la seccion: devolucion de contenedores\r\n"+ 
				"Resultado: Se muestra la seccion devolucion de contenedores");
				FUE.ValidacionDevolucionContenedores();
				System.out.println("Se muestra la seccion de DEVOLUCION DE CONTENEDORES\n");
				testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
				
				/*Paso 3 Dentro de la pantalla se encuentra la tabla: contenedores
                /*Resultado: Se muestra la tabla Contenedores en donde la*Columna derecha: contiene el total de los valores de los contenedores ingresados. *Filas: contienen los titulos: Tapas y Envases vacios.*Total, se sumalice el total en la parte inferior de la tabla de los contenedores."
				*/
				addStep("Dentro de la pantalla se encuentra la tabla contenedores\r\n" + 
				"Resultado: Se muestra la tabla Contenedores en donde la columna derecha contiene el total de los valores de los contenedores ingresados. Filas contienen los titulos Tapas y Envases vacios. Total, se sumalice el total en la parte inferior de la tabla de los contenedores.");
				FUE.ValidacionTapasYEnvasesVacios();
				System.out.println("Se muestra la TABLA DE CONTENEDORES de la seccion DEVOLUCION DE CONTENEDORES\n");
				Runtime.getRuntime().exec("adb shell input swipe 20 1003 20 900 90");// Se desliza un poco hacía abajo
				testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
	}
		@Override
		public String setTestFullName() {
			return "ATC-FT-123 Handheld Ajustes FUE-Apartado devolucion de contenedores(Contenedores)";
		}

		@Override
		public String setTestDescription() {
			return "Validar que en la pantalla formato unico de entrega el apartado devolucion de contenedores no haya sufrido ninguna afectacion.";
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
			return "1. App Oxxo al dia instalada en Handheld(version mas actual).\r\n"
					+ "2. Usuario y Contraseña de Aplicacion Oxxo al dia.\r\n"
					+ "3. Comunicacion entre HH y POS (red wifi (Handheld) y cableada (POS), que permitan conexion entre ambos dispositivos).\r\n"
					+ "4. P.V con acceso a la Herramienta de compra.\r\n"
					+ "5. Realizar flujo de recepcion hasta llegar a la pantalla Formato unico de entrega.\r\n"
					+ "6. Haber registrado contenedores en la pantalla devoluciones a cedis.";
		}

		@Override
		public void beforeTest() {
		}
	
	}
