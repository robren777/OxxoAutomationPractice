package Mobile.cedis.integrales;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.cedis.om.A01_Login_Punto_De_Venta;
import Mobile.cedis.om.A02_Login_Lider;
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
import Mobile.cedis.om.B10_DeseaComenzar;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;



public class ATC_FT_196_ValidacionDelFlujoAlSeleccionarModificarDevolucionesDesdeLaPantallaFUE extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_196_ValidacionDelFlujoSeleccionarModificarDevolucionesDesdeLaPantallaFUE_test(
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
		//---------------------------------------- PRE INICIO ------------------------------------------------//

				//En el login de punto de venta ingreso la ip y el puerto
				A01_Login_Punto_De_Venta LoginPDVOaD = new A01_Login_Punto_De_Venta(testCase, appiumUtil, this);
				System.out.println("Logeando Punto de Venta");
				Thread.sleep(2000);
				LoginPDVOaD.loginPV("10.75.6.126");

				//En el login de lider ingreso el usuario y contraseña
				A02_Login_Lider LoginLiderOaD = new A02_Login_Lider(testCase, appiumUtil, this);
				System.out.println("Logeando Lider");
				Thread.sleep(2000);
				LoginLiderOaD.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));

				//En la pantalla principal doy clic en el boton de excelencia operativa, luego en la hc y al final e cometa
				A03_Menus_y_submenus MenuOaD = new A03_Menus_y_submenus(testCase, appiumUtil, this);
				System.out.println("Dando clic en Excelencia Operativa -> HC y COMETA");
				Thread.sleep(2000);
				MenuOaD.logeoInicioHerramientas();
				
				//En el menu de herramientas doy clic en la Recepción de Pedidos CEDIS
				A04_MenuHerramientas ModulosOaD = new A04_MenuHerramientas(testCase, appiumUtil, this);
				System.out.println("Dando clic en Recepción de Pedidos CEDIS");
				Thread.sleep(2000);
				ModulosOaD.clickBtnRecepcionPedidosCEDIS();
				
				//Logeo en Recepción de Pedidos CEDIS
				A05_LoginAreaDistribucion LoginRec = new A05_LoginAreaDistribucion(testCase, appiumUtil, this);
				System.out.println("Logueando área distribución...");
				LoginRec.LoginAreaDistribucion();
				
				//Seleccionamos transferencia del listado
				B01_RecepcionPedidosSeleccionarTransferencia Transfer = new B01_RecepcionPedidosSeleccionarTransferencia(testCase, appiumUtil, this);
				System.out.println("Selecionando transferencia...");
				Transfer.MoverHacia("Transferencia");
				Transfer.BuscarTransferencia(true,data.get("numTransferencia"));
				
				//Desea comenzar
				B10_DeseaComenzar comenzar = new B10_DeseaComenzar(testCase, appiumUtil, null);
				System.out.println("Seleccionando Recepcion de pedido...");
				//Thread.sleep(3000);
				comenzar.Cargando();
				comenzar.validarBotones();
				comenzar.SeleccionarObjeto("Recepcion Pedido");
				
				//Realizamos la recepción del pedido
				B02_RecepcionPedidosCEDIS PedidoRecepcion = new B02_RecepcionPedidosCEDIS(testCase, appiumUtil, this);
				System.out.println("Realizando la recepción del pedido...");
				PedidoRecepcion.DeslizarArriba();
				PedidoRecepcion.Tomar3Fotos();
				PedidoRecepcion.recepcionarUnaCaja();
				PedidoRecepcion.CapturaContenedor("756384");
				PedidoRecepcion.CapturaContenedor("756407");
				PedidoRecepcion.SeleccionarObjeto("Terminar");
				PedidoRecepcion.SeleccionarObjeto("Confirmar");
				PedidoRecepcion.SeleccionarObjeto("Si");
				System.out.println("Ingresando cajas sin etiqueta...");
				PedidoRecepcion.MoverSeccion("Cajas sin etiqueta");
				PedidoRecepcion.RegistrarSinEtiqueta();
				PedidoRecepcion.SeleccionarObjeto("Terminar Cajas Sin Etiqueta");
				PedidoRecepcion.SeleccionarObjeto("Confirmar");
				System.out.println("Capturando sobrantes...");
				PedidoRecepcion.CapturaContenedorSobrantes("013468");
				PedidoRecepcion.SeleccionarObjeto("Continuar Sobrantes");
				PedidoRecepcion.SeleccionarObjeto("Terminar Dañados");
				PedidoRecepcion.SeleccionarObjeto("Confirmar");
				
				//Resumen recepción
				System.out.println("Entrando a Resumen de Pedido...");
				B03_ResumenPedidosCEDIS rerec = new B03_ResumenPedidosCEDIS(testCase, appiumUtil, null);
				Thread.sleep(3000);
				rerec.ValidacionEncabezado();
				System.out.println("Seleccionando firma de acuerdo...");
				rerec.SeleccionarObjeto("Firma Acuerdo");
				
				//Firma de acuerdo recepción
				B04_AcuerdoEntrega acrec = new B04_AcuerdoEntrega(testCase, appiumUtil, null);
				System.out.println("Entrando a Firmas de Acuerdo Recepción...");
				acrec.ValidacionEncabezado();
				acrec.OpcionAreaDistribucion("", "", "Entrega correcta");
				acrec.FirmaAreaDistribucion("", "");
				acrec.OpcionPersonalTienda("", "", "Sin comentario");
				acrec.FirmaPersonalTienda("", "");
				acrec.TerminarRecepcion("", "");

				//Realizamos la devolución
				B05_DevolucionMercancia Devo = new B05_DevolucionMercancia(testCase, appiumUtil, null);
				System.out.println("Entrando a Devoluciones CEDIS...");
				Devo.SeleccionarBOLSinCheck("", "");
				Devo.AumentarCanastilla(1, "Azul");
				Devo.AumentarCanastilla(2, "Amarilla");
				Devo.AumentarCanastilla(3, "Verde");
				Devo.AumentarCanastilla(4, "Naranja");
				Devo.AumentarCanastilla(5, "Otro");
				Devo.AumentarContenedores(2, "Tapas");
				Devo.AumentarContenedores(3, "Envases");
				Devo.TomarFotoDevolucion();
				Devo.IngresarCanastilla("918274");
				Devo.TomarFotoCanastilla();
				Devo.SeleccionarObjeto("Continuar");
				
				//Resumen devolución
				B07_ResumenDevolucion redevo = new B07_ResumenDevolucion(testCase, appiumUtil, this);
				System.out.println("Entrando a Resumen Devolución...");
				redevo.ValidacionEncabezado();
				System.out.println("Entrando a Firma de Acuerdo");
				redevo.ClickFirmaAcuerdo("", "");

				//Firma de acuerdo devolución 
				B08_AcuerdoDevolucion acdevo = new B08_AcuerdoDevolucion(testCase, appiumUtil, this);
//				acdevo.OpcionAreaDistribucion("", "", "Devolucion correcta");
//				acdevo.FirmaAreaDistribucion("", "");
//				acdevo.OpcionPersonalTienda("", "", "Sin comentario");
//				acdevo.FirmaPersonalTienda("", "");
//				acdevo.TerminarRecepcion("","");
//				acdevo.SeleccionarObjeto("Confirmar");		
				acdevo.FirmaAreaDistribucion("", "");
				Thread.sleep(2000);
				System.out.println("Firma Personal de Tienda\n");
				Thread.sleep(1000);
				acdevo.FirmaPersonalTienda("", "");
				Thread.sleep(2000);
				Runtime.getRuntime().exec("adb shell input swipe 20 1003 20 900 90");//
				Thread.sleep(2000);
				System.out.println("Terminando Firmas de Acuerdo de Entrega\n");
				Thread.sleep(1000);
				acdevo.TerminarRecepcion("", "CEDIS_ConfirmarSeguroTerminarAcuerdo");
				
				//Formato Unico de Entrega FUE
				System.out.println("Entrando a Formato Unico de Entrega...");
				B09_FormatoUnicoEntrega fue = new B09_FormatoUnicoEntrega(testCase, appiumUtil, this);
			
		//=================================== Inicio de Caso =====================================//
		 /* 
		 * Paso 1  - Se inicia el caso por parte del usuario en la pantalla de FUE de una transferencia iniciada el flujo por la opción de Devoluciones a CEDIS 
		*/ 
		addStep("Se inicia el caso por parte del usuario en la pantalla de FUE de una transferencia iniciada el flujo por la opción de Devoluciones a CEDIS ");
		fue.ValidacionEncabezado();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 2  - ?Se muestra la pantalla de Formato Único de Entrega con sus campos correspondientes.
						Se muestran los botones de 
						-"MODIFICAR RECEPCIÓN"
						-"MODIFICAR DEVOLUCIONES" 
						-"TERMINAR"
		*/ 
		addStep("?Se muestra la pantalla de Formato Único de Entrega con sus campos correspondientes. "
				+ "Se muestran los botones de \r\n"
				+ "\"MODIFICAR RECEPCIÓN\"\r\n"
				+ "\"MODIFICAR DEVOLUCIONES\" \r\n"
				+ "\"TERMINAR\"");
		fue.BuscarSeccion("Botones");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 3  - Usuario selecciona el botón de "MODIFICAR DEVOLUCIONES" 
		*/ 
		addStep("Usuario selecciona el botón de \"MODIFICAR DEVOLUCIONES\"");
		fue.SeleccionarObjeto("Modificar Devolucion");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 4  - ?Se envía el caso a la pantalla de Pantalla Devoluciones CEDIS en la cual se muestra la pantalla CON la información capturada previamente. Permitiendo al usuario cargar nuevamente la información
		*/ 
		addStep("?Se envía el caso a la pantalla de Pantalla Devoluciones CEDIS en la cual se muestra la pantalla CON la información capturada previamente. Permitiendo al usuario cargar nuevamente la información");
		System.out.println("Redireccionando a Devoluciones...");
		redevo.subir();
		redevo.ValidacionEncabezado();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 5  - Se selecciona la opción de Continuar en la pantalla de Devoluciones CEDIS una vez llenada la información correspondiente en la pantalla.
		*/ 
		addStep("Se selecciona la opción de Continuar en la pantalla de Devoluciones CEDIS una vez llenada la información correspondiente en la pantalla.");
		Devo.SeleccionarBOLSinCheck("", "");
		Devo.AumentarCanastilla(3, "Azul");
		Devo.AumentarCanastilla(0, "Amarilla");
		Devo.AumentarCanastilla(1, "Verde");
		Devo.AumentarCanastilla(8, "Naranja");
		Devo.AumentarCanastilla(0, "Otro");
		Devo.AumentarContenedores(1, "Tapas");
		Devo.AumentarContenedores(0, "Envases");
		Devo.TomarFotoDevolucion();
		Devo.IngresarCanastilla("918635");
		Devo.TomarFotoCanastilla();
		Devo.SeleccionarObjeto("Continuar");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 6  - ?Se muestra la pantalla de Resumen Devolución con los campos correspondientes
		*/ 
		addStep("?Se muestra la pantalla de Resumen Devolución con los campos correspondientes");
		redevo.subir();
		redevo.ValidacionEncabezado();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 7  - Se selecciona la opción de Firma de Acuerdo en la pantalla de Resumen Devolución 
		*/ 
		addStep("Se selecciona la opción de Firma de Acuerdo en la pantalla de Resumen Devolución ");
		redevo.ClickFirmaAcuerdo("", "");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 8  - ?Se muestra la pantalla de Acuerdo de Devolución con los campos correspondientes donde el botón para finalizar el registro de las devoluciones ahora se renombrará a "TERMINAR DEVOLUCIÓN".
		*/ 
		addStep("?Se muestra la pantalla de Acuerdo de Devolución con los campos correspondientes donde el botón para finalizar el registro de las devoluciones ahora se renombrará a \"TERMINAR DEVOLUCIÓN\".");
		System.out.println("Realizando las firmas de acuerdo devolución...");
		acdevo.ValidacionBotonTerminarDevolucion();
		acdevo.OpcionAreaDistribucion("", "", "Devolucion correcta");
		acdevo.FirmaAreaDistribucion("", "");
		acdevo.OpcionPersonalTienda("", "", "Sin comentario");
		acdevo.FirmaPersonalTienda("", "");
		acdevo.TerminarRecepcion("","");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 9  - Se realiza la firma de Acuerdo de Devolución y selecciona el botón de TERMINAR DEVOLUCIÓN
		*/
		addStep("Se realiza la firma de Acuerdo de Devolución y selecciona el botón de TERMINAR DEVOLUCIÓN");
		acdevo.SeleccionarObjeto("Confirmar");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 10  - ?Se muestra la pantalla de Formato Único de Entrega con sus campos correspondientes, actualizando la información correspondiente a la sección de Devoluciones a CEDIS.
		*/ 
		addStep("?Se muestra la pantalla de Formato Único de Entrega con sus campos correspondientes, actualizando la información correspondiente a la sección de Devoluciones a CEDIS.");
		fue.ValidacionEncabezado();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		

		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));
	
	}
	
	/*CEDIS MVP3: MTC-FT-196 Validación del flujo al seleccionar MODIFICAR DEVOLUCIONES desde la pantalla FUE
	  Desc: Caso de prueba en el que se valida el flujo a tomar cuando se seleccione la opción de MODIFICAR DEVOLUCIONES en la pantalla de FUE
	  @autor: Vanessa Torres
	  @date: 04/02/2025
	 */
	
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Contar con Transferencias por atender en la handheld\\r\\n\"\r\n"
				+ "+ \"2. Haber iniciado el flujo de la transferencia con la opción de Devoluciones a CEDIS \\r\\n\"\r\n"
				+ "+ \"3. Contar con la transferencia en la pantalla de FUE";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Caso de prueba en el que se valida el flujo a tomar cuando se seleccione la opción de \"\r\n"
				+ "+ \"MODIFICAR DEVOLUCIONES en la pantalla de FUE";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Vanessa Torres";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_196_ValidacionDelFlujoAlSeleccionarModificarDevolucionesDesdeLaPantallaFUE";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	
}