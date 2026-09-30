package Mobile.cedis.mvp3.handheld.sprint7;

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
import Mobile.cedis.om.B10_DeseaComenzar;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_013_FaltanteDe1De2CajasConElMismoArticuloConEnvase extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_013_FaltanteDe1De2CajasConElMismoArticuloConEnvase_test(
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
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
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
				System.out.println("Logeando Recepción de Pedido CEDIS");
				Thread.sleep(2000);
				LoginRec.LoginAreaDistribucion();
				
				//Seleccionamos transferencia del listado
				B01_RecepcionPedidosSeleccionarTransferencia Transfer = new B01_RecepcionPedidosSeleccionarTransferencia(testCase, appiumUtil, this);
				System.out.println("Seleccionando la primera trasnferencia");
				Thread.sleep(2000);
				Transfer.BuscarTransferencia(true,data.get("numTransferencia"));
				
				//Seleccion de recepción por Recepción de pedido
				B10_DeseaComenzar DeseaComenzar = new B10_DeseaComenzar(testCase, appiumUtil, this);
				System.out.println("Seleccionando comenzar por Recepción de Pedido");
				DeseaComenzar.Cargando();
				DeseaComenzar.SeleccionarObjeto("Recepcion Pedido");
				
				//Dar entrada a canastillas secos y refrigerados
				B02_RecepcionPedidosCEDIS recepcionar = new B02_RecepcionPedidosCEDIS(testCase, appiumUtil, this);
				System.out.println("Recepcionando una canastilla seco....");
				recepcionar.recepcionarUnaCanastillaSecos();
				System.out.println("Recepcionando una canastilla refrigerada...");
				recepcionar.recepcionarUnaCanastillaRefrigerados();
				recepcionar.SeleccionarObjeto("Terminar Recepcion");
				recepcionar.SeleccionarObjeto("Confirmar");
				recepcionar.SeleccionarObjeto("Si");
				


		//=================================== Inicio de Caso =====================================//

		B03_ResumenPedidosCEDIS resumen = new B03_ResumenPedidosCEDIS(testCase, appiumUtil, this);
		B04_AcuerdoEntrega firma = new B04_AcuerdoEntrega(testCase, appiumUtil, this);
		B05_DevolucionMercancia devo = new B05_DevolucionMercancia(testCase, appiumUtil, this);
		B07_ResumenDevolucion redevo = new B07_ResumenDevolucion(testCase, appiumUtil, this);
		B08_AcuerdoDevolucion acdevo = new B08_AcuerdoDevolucion(testCase, appiumUtil, this);

				
		 /* 
		 * Paso 1  - Estar en la sección "Cajas sin etiquetas/etiquetas dañadas" y se muestran por registrar 
		 * las cajas con artículos de envases que no se recepcionaron.
		*/ 
		addStep(" Estar en la sección \"Cajas sin etiquetas/etiquetas dañadas\" y se muestran por registrar");
		recepcionar.MoverSeccion("Cajas sin etiqueta");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /*
		 * Paso 2  - ?Se muestra la sección "Cajas sin etiquetas/etiquetas dañadas" con las cajas que no se 
		 * recepcionaron con artículos de envases.
		*/ 
		addStep("?Se muestra la sección \"Cajas sin etiquetas/etiquetas dañadas\" con las cajas que no se recepcionaron con artículos de envases.");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 3  - Registra 1 de 2 cajas (1) con artículos envases manualmente.
		*/ 
		addStep("Registra 1 de 2 cajas (1) con artículos envases manualmente.");
		recepcionar.RegistrarSinEtiqueta();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 4  - ?Se registra correctamente 1 de 2 cajas (1) sin etiqueta/etiqueta dañada.
		*/ 
		addStep("?Se registra correctamente 1 de 2 cajas (1) sin etiqueta/etiqueta dañada.");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 5  - Termina todo el proceso de recepción del pedido y llega hasta el FUE 
		*/
		addStep("Termina todo el proceso de recepción del pedido y llega hasta el FUE");
		recepcionar.TerminarHastaResumen();
		resumen.FirmaAcuerdoSinEvidencia();
		firma.FirmaAreaDistribucion("tomaEvidencia", "descPaso");
		firma.FirmaPersonalTienda("tomaEvidencia", "descPaso");
		firma.SeleccionarObjeto("Terminar Recepcion");
		firma.SeleccionarObjeto("Confirmar");
		devo.SeleccionarObjeto("Continuar");
		devo.SeleccionarObjeto("Confirmar");
		redevo.ClickFirmaAcuerdo("","");
		acdevo.OpcionAreaDistribucion("tomaEvidencia", "descPaso", "Devolucion correcta");
		acdevo.FirmaAreaDistribucion("tomaEvidencia", "descPaso");
		acdevo.OpcionPersonalTienda("tomaEvidencia", "descPaso", "Sin comentario");
		acdevo.FirmaPersonalTienda("tomaEvidencia","descPaso");
		acdevo.SeleccionarObjeto("Cumplio Proceso");
		acdevo.SeleccionarObjeto("Terminar Devolución");
		acdevo.SeleccionarObjeto("Confirmar");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 6  - ?Se termina correctamente el proceso de recepción y se llegó hasta el FUE
		*/ 
		addStep("?Se termina correctamente el proceso de recepción y se llegó hasta el FUE");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /*
		 * Paso 7  - Dentro del resumen FUE se encuentra la sección "Faltantes" y viene el registro de la caja (2) 
		 * del mismo artículo que no se recibió 
		*/ 
		addStep("Dentro del resumen FUE se encuentra la sección \"Faltantes\" y viene el registro de la caja (2) del mismo artículo que no se recibió");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 8  - ?Se muestra registrado la caja (2) con artículos de envase  como faltante  en el resumen FUE
		*/ 
		addStep("?Se muestra registrado la caja (2) con artículos de envase  como faltante  en el resumen FUE");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 9  - Finaliza el proceso de recepción para que se muestre el mensaje del recepción de pedido exitoso
		*/ 
		addStep("Finaliza el proceso de recepción para que se muestre el mensaje del recepción de pedido exitoso");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 10  - ?Se muestra el mensaje de recepción exitosa
		*/ 
		addStep("?Se muestra el mensaje de recepción exitosa");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		

		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));

	}

	/*CEDIS MVP3: MTC-FT-013 Faltante de 1 de 2 cajas con el mismo articulo con envase
	  Desc: Validar que se registre correctamente el faltante en la cantidad de artículos con  1 de 2 cajas con el mismo articulo con envase.
	  @autor: Vanessa Torres
	  @date: 04/02/2025
	 */

	@Override
	public String setTestFullName() {
		return "ATC_FT_013_FaltanteDe1De2CajasConElMismoArticuloConEnvase";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se registre correctamente el faltante en la cantidad de artículos con  1 de 2 cajas con el mismo articulo con envase.";
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
		return "1. ASN/Pedido con artículos de envases (2 cajas de mismo articulo)\r\n"
				+ "2. Apk OaD con la función de envases \r\n"
				+ "3. No haber recibido las 2 cajas en recepción para poder recibir 1 como caja sin etiqueta";
	}

	@Override
	public void beforeTest() {
	}

}