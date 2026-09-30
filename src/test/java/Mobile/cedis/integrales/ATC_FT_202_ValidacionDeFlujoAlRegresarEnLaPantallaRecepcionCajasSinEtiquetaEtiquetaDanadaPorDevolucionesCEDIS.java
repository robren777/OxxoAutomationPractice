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
import Mobile.cedis.om.B05_DevolucionMercancia;
import Mobile.cedis.om.B07_ResumenDevolucion;
import Mobile.cedis.om.B08_AcuerdoDevolucion;
import Mobile.cedis.om.B10_DeseaComenzar;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_202_ValidacionDeFlujoAlRegresarEnLaPantallaRecepcionCajasSinEtiquetaEtiquetaDanadaPorDevolucionesCEDIS extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_202_ValidacionDeFlujoAlRegresarEnLaPantallaRecepcionCajasSinEtiquetaEtiquetaDanadaPorDevolucionesCEDIS_test(
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

		// Cerrar Apps y eliminar caché
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
		LoginPDVOaD.loginPV(data.get("ip"));

		//En el login de lider ingreso el usuario y contraseña
		A02_Login_Lider LoginLiderOaD = new A02_Login_Lider(testCase, appiumUtil, this);
		System.out.println("Logeando Lider");
		Thread.sleep(2000);
		LoginLiderOaD.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));

		//En la pantalla principal doy clic en el boton de excelencia operativa, luego en la hc y al final e cometa
		A03_Menus_y_submenus MenuOaD = new A03_Menus_y_submenus(testCase, appiumUtil, this);
		System.out.println("Dando clic en Excelencia Operativa -> HC y COMETA");
		Thread.sleep(8000);
		MenuOaD.logeoInicioHerramientas();
		
		//En el menu de herramientas doy clic en la Recepción de Pedidos CEDIS
		A04_MenuHerramientas ModulosOaD = new A04_MenuHerramientas(testCase, appiumUtil, this);
		System.out.println("Dando clic en Recepción de Pedidos CEDIS");
		Thread.sleep(2000);
		ModulosOaD.clickBtnRecepcionPedidosCEDIS();
		
		//Logeo en Recepción de Pedidos CEDIS
		A05_LoginAreaDistribucion LoginRec = new A05_LoginAreaDistribucion(testCase, appiumUtil, this);

		
		//Seleccionamos transferencia del listado
		B01_RecepcionPedidosSeleccionarTransferencia Transfer = new B01_RecepcionPedidosSeleccionarTransferencia(testCase, appiumUtil, this);

		
		B10_DeseaComenzar DeseaComenzar = new B10_DeseaComenzar(testCase, appiumUtil, this);
		B05_DevolucionMercancia Devo = new B05_DevolucionMercancia(testCase, appiumUtil, null);
		B07_ResumenDevolucion redevo = new B07_ResumenDevolucion(testCase, appiumUtil, this);
		B08_AcuerdoDevolucion acdevo = new B08_AcuerdoDevolucion(testCase, appiumUtil, this);
		B02_RecepcionPedidosCEDIS PedidoRecepcion = new B02_RecepcionPedidosCEDIS(testCase, appiumUtil, this);
		B10_DeseaComenzar comenzar = new B10_DeseaComenzar(testCase, appiumUtil, null);

		//=================================== Inicio de Caso =====================================//

		 /* 
		 * Paso 1  - Se capturan los datos correspondientes en la pantalla de logueo de Recepción de PEDIDOS CEDIS  dando clic en Entrar
		*/ 
		addStep("Se capturan los datos correspondientes en la pantalla de logueo de Recepción de PEDIDOS CEDIS  dando clic en Entrar");
		LoginRec.LoginAreaDistribucion();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 2  - ?Se muestra la pantalla Recepción de Pedidos CEDIS con el listado de Transferencias asignadas a la tienda.
		*/ 
		addStep("?Se muestra la pantalla Recepción de Pedidos CEDIS con el listado de Transferencias asignadas a la tienda.");
		Transfer.MoverHacia("Transferencia");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		/* 
		 * Paso 3  - Se selecciona una transferencia que no ha sido atendida por el usuario.
		*/ 
		addStep("Se selecciona una transferencia que no ha sido atendida por el usuario.");
		Transfer.BuscarTransferencia(true,data.get("numTransferencia"));
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		 /* 
		 * Paso 4  - Se muestra la pantalla de Recepción de Pedidos CEDIS con las nuevas opciones de flujo, mostrara:
					-Cabecera de pedido: Fecha, Ruta, Ruta Dinámica, CR Tienda, Nombre Tienda, Líder Tienda.

					Mostrara el Título de :"¿Como deseas comenzar?"
					Mostrara las Instrucciones de: "Puedes comenzar escaneando las cajas y canastillas del pedido, o bien, 
					registrando las devoluciones del pedido. Selecciona la opción deseada."

					Muestra los botones de:
					-"Recepción de pedido"
					-"Devoluciones a CEDIS"
		*/
		addStep("Se muestra la pantalla de Recepción de Pedidos CEDIS con las nuevas opciones de flujo, mostrara:\r\n"
				+ "					-Cabecera de pedido: Fecha, Ruta, Ruta Dinámica, CR Tienda, Nombre Tienda, Líder Tienda.\r\n"
				+ "					Mostrara el Título de :\"¿Como deseas comenzar?\"\r\n"
				+ "					Mostrara las Instrucciones de: \"Puedes comenzar escaneando las cajas y canastillas del pedido, o bien, \r\n"
				+ "					registrando las devoluciones del pedido. Selecciona la opción deseada.\"\r\n"
				+ "					Muestra los botones de:\r\n"
				+ "					-\"Recepción de pedido\"\r\n"
				+ "					-\"Devoluciones a CEDIS\"");
		Thread.sleep(5000);
		comenzar.validarBotones();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 5  - Se selecciona la opción de Devoluciones a CEDIS
		*/ 
		addStep("Se selecciona la opción de Devoluciones a CEDIS");
		DeseaComenzar.Cargando();
		DeseaComenzar.SeleccionarObjeto("Devolucion CEDIS");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 6  - ?Se inicia el flujo de la transferencia mostrando la pantalla de Devoluciones CEDIS
		*/ 
		addStep("?Se inicia el flujo de la transferencia mostrando la pantalla de Devoluciones CEDIS");
		Devo.ValidacionEncabezado();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 7  - Se selecciona la opción de Continuar en la pantalla de Devoluciones CEDIS una vez llenada 
		 * la información correspondiente en la pantalla.
		*/ 
		addStep("Se selecciona la opción de Continuar en la pantalla de Devoluciones CEDIS una vez llenada la información correspondiente en la pantalla.");
		Devo.SeleccionarBOLSinCheck("", "");
		Devo.AumentarCanastilla(1, "Azul");
		Devo.AumentarCanastilla(2, "Amarilla");
		Devo.AumentarCanastilla(3, "Verde");
		Devo.AumentarCanastilla(4, "Naranja");
		Devo.AumentarCanastilla(5, "Otro");
		Devo.AumentarContenedores(1, "Tapas");
		Devo.AumentarContenedores(1, "Envases");
		Devo.TomarFotoDevolucion();
		Devo.IngresarCanastilla("756478");
		Devo.TomarFotoCanastilla();
		System.out.println("Seleccionando botón continuar...");
		Devo.SeleccionarObjeto("Continuar");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 8  - ?Se muestra la pantalla de Resumen Devolución con los campos correspondientes
		*/ 
		addStep("?Se muestra la pantalla de Resumen Devolución con los campos correspondientes");
		redevo.ValidacionEncabezado();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 9  - Se selecciona la opción de Firma de Acuerdo en la pantalla de Resumen Devolución 
		*/ 
		addStep("Se selecciona la opción de Firma de Acuerdo en la pantalla de Resumen Devolución");
		redevo.ClickFirmaAcuerdo("", "");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 10  - ?Se muestra la pantalla de Acuerdo de Devolución con los campos correspondientes donde el 
		 * botón para finalizar el registro de las devoluciones ahora se renombrará a "TERMINAR DEVOLUCIÓN".
		*/ 
		addStep("?Se muestra la pantalla de Acuerdo de Devolución con los campos correspondientes donde el botón para finalizar el registro de las devoluciones ahora se renombrará a \"TERMINAR DEVOLUCIÓN\".");
		acdevo.ValidacionBotonTerminarDevolucion();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 11  - Se realiza la firma de Acuerdo de Devolución y selecciona el botón de TERMINAR DEVOLUCIÓN
		*/ 
		addStep("Se realiza la firma de Acuerdo de Devolución y selecciona el botón de TERMINAR DEVOLUCIÓN");
		acdevo.subir();
		acdevo.OpcionAreaDistribucion("", "", "Devolucion correcta");
		acdevo.FirmaAreaDistribucion("", "");
		acdevo.OpcionPersonalTienda("", "", "Sin comentario");
		acdevo.FirmaPersonalTienda("", "");
		acdevo.TerminarRecepcion("","");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 12  - ?Se muestra la pantalla de Recepción de Pedidos CEDIS en la sección de Recepción Cajas / Canastillas
		*/ 
		addStep("?Se muestra la pantalla de Recepción de Pedidos CEDIS en la sección de Recepción Cajas / Canastillas");
		PedidoRecepcion.subir();
		Thread.sleep(1000);
		PedidoRecepcion.ValidacionEncabezado();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 13  - Se realiza el llenado de información de la sección de Recepción Cajas / Canastillas para después seleccionar  Terminae
		*/ 
		addStep("Se realiza el llenado de información de la sección de Recepción Cajas / Canastillas para después seleccionar Terminar");
		PedidoRecepcion.subir();
		PedidoRecepcion.Tomar3Fotos();
		PedidoRecepcion.recepcionarUnaCaja();
		PedidoRecepcion.CapturaContenedor("667209");
		PedidoRecepcion.CapturaContenedor("986595");
		PedidoRecepcion.SeleccionarObjeto("Terminar");
		PedidoRecepcion.SeleccionarObjeto("Confirmar");
		PedidoRecepcion.SeleccionarObjeto("Si");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 14  - ?Se muestra la sección de Recepción Cajas sin etiqueta / Etiqueta dañada
		*/ 
		addStep("?Se muestra la sección de Recepción Cajas sin etiqueta / Etiqueta dañada");
		PedidoRecepcion.MoverSeccion("Cajas sin etiqueta");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 15  - Se realiza el llenado de información de la sección de Recepción Cajas sin etiqueta / Etiqueta dañada para después  seleccionar Regresar
		*/ 
		addStep("Se realiza el llenado de información de la sección de Recepción Cajas sin etiqueta / Etiqueta dañada para después  seleccionar Regresar");
		PedidoRecepcion.RegistrarSinEtiqueta();
		acdevo.sendKeysUsingKeyEvents(appiumUtil, "B");
		PedidoRecepcion.SeleccionarObjeto("Desea Salir");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		 /* 
		 * Paso 16  - ?Se regresa a la pantalla de Resumen Devolución 
		*/ 
		addStep("?Se regresa a la pantalla de Resumen Devolución");
		redevo.subir();
		redevo.ValidacionEncabezado();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));

	}

	/*CEDIS MVP3: MTC-FT-202 Validación de flujo al regresar en la pantalla Recepción Cajas sin etiqueta / Etiqueta dañada por Devoluciones CEDIS
	  Desc: Caso de prueba en el que se valida el flujo a tomar cuando se seleccione la opción de Regresar en la pantalla de Recepción Cajas sin etiqueta / Etiqueta dañada para una transferencia iniciada por Devoluciones CEDIS
	  @autor: Vanessa Torres
	  @date: 04/02/2025
	 */

	@Override
	public String setTestFullName() {
		return "ATC_FT_202_ValidacionDeFlujoAlRegresarEnLaPantallaRecepcionCajasSinEtiquetaEtiquetaDanadaPorDevolucionesCEDIS";
	}

	@Override
	public String setTestDescription() {
		return "Caso de prueba en el que se valida el flujo a tomar cuando se seleccione la opción de Regresar "
				+ "en la pantalla de Recepción Cajas sin etiqueta / Etiqueta dañada para una transferencia iniciada por Devoluciones CEDIS";
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
		return "1. Contar con Transferencias por atender en la handheld\r\n"
				+ "2. Haber iniciado el flujo de la transferencia con la opción de Devoluciones a CEDIS";
	}

	@Override
	public void beforeTest() {
	}

}