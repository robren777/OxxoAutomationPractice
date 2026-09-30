package Mobile.directos.mvp3;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.directos.om.mvp3.Login;
import Mobile.directos.om.mvp3.OAD;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_103_BusquedaOrdenesRecibidas extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_103_CasoPrueba103_test(HashMap<String, String> data) throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();

		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appPackage", data.get("appPackage"));
		cap.setCapability("appActivity", data.get("appActivity"));
		cap.setCapability("appium:settings[enableMultiWindows]", true);
		cap.setCapability("noReset", true); // Se debe de dejar esta opcion en true para que no se borre el cache y asi
											// lograr evitar la vista de ingresar la IP y puerto
		String url = "http://127.0.0.1:4723";

		// =======================================Cerrar Aplicaciones========================================================//
		Thread.sleep(5000);
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		// =======================================Inicio Aplicaciones========================================================//
		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n" + e.getMessage());
			e.printStackTrace();
		}
		Thread.sleep(3500);

		// =========================================Llamado al OM============================================================//
		Login login = new Login(testCase, appiumUtil, this);
		OAD OxxoAlDia = new OAD(testCase, appiumUtil, this);
		Thread.sleep(5000);

		// =========================================Inicio Ejecucion=========================================================//
		login.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));
		
		/*
		 * paso 1: Ingresar a la app "Oxxo al día" en el Menú Excelencia Operativa/Herramienta de compra/Cometa/Verificación de Mercancía Pedidos Proveedores Directos
		 */
		addStep("Ingresar a la app \"Oxxo al día\" en el Menú Excelencia Operativa/Herramienta de compra/Cometa/Verificación de Mercancía Pedidos Proveedores Directos");
		OxxoAlDia.ingresarModuloHerramientas();
		OxxoAlDia.opcionVerificacionMercancia();
		
		/*
		 * paso 2: Validar que nos muestre la pantalla “PROVEEDORES DIRECTOS” con las opciones 
		 * Búsqueda pedidos Precargados
		 * Leer QR para Preventa o Pedido
		 * Generación de herramienta de compra
		 * Órdenes de compra recibidas
		 */
		addStep("Validar que nos muestre la pantalla “PROVEEDORES DIRECTOS” con las opciones \r\n"
				+ "Búsqueda pedidos Precargados \r\n"
				+ "Leer QR para Preventa o Pedido \r\n"
				+ "Generación de herramienta de compra \r\n"
				+ "Órdenes de compra recibidas ");
		boolean validacion = OxxoAlDia.pantallaProveedoresDirectos();
		assertTrue(validacion,"Error, la pantalla de proveedores directos no se mestra correctamente");
		
		/*
		 * paso 3: Seleccionar la opción “Órdenes de compra recibidas”
		 */
		addStep("Seleccionar la opción “Órdenes de compra recibidas”");
		OxxoAlDia.opcionOrdenesCompraRecibidas();
		
		/*
		 * paso 4: Validar que se muestre la pantalla “ÓRDENES DE COMPRA RECIBIDAS”
		 *  
		 *  icon ⬅️(Regresar)
		 *  título ÓRDENES DE COMPRA RECIBIDAS
		 *  icon  i (Tutoriales)
		 *  BÚSQUEDA POR:
		 *  Radio button Número de Pedido
		 *  Radio button Proveedor
		 *  Texto Número de Pedido/Proveedor.
		 *  Text Box 
		 *  Botón "BUSCAR”
		 *  Texto PEDIDODS
		 *  Información en forma de tabla PROVEEDOR|PEDIDO|FECHA|CANTIDAD
		 */
		addStep("Validar que se muestre la pantalla “ÓRDENES DE COMPRA RECIBIDAS”"
				+ "icon ⬅️(Regresar) \r\n"
				+ "título ÓRDENES DE COMPRA RECIBIDAS \r\n"
				+ "icon  i (Tutoriales) \r\n"
				+ "BÚSQUEDA POR: \r\n"
				+ "Radio button Número de Pedido \r\n"
				+ "Radio button Proveedor \r\n"
				+ "Texto Número de Pedido/Proveedor. \r\n"
				+ "Text Box \r\n"
				+ "Botón \"BUSCAR” \r\n"
				+ "Texto PEDIDODS \r\n"
				+ "Información en forma de tabla PROVEEDOR|PEDIDO|FECHA|CANTIDAD");
		validacion = OxxoAlDia.pantallaOrdenesCompraRecibidas();
		assertTrue(validacion, "error, la pantalla no se muestra de manera correcta");
		
		/*
		 * paso 5: Presionar “BUSCAR”
		 */
		addStep("Presionar [BUSCAR]");
		OxxoAlDia.opcionBuscarOrdenesCompra();
		
		
		
		/*
		 * paso 6: Validar que muestre el mensaje “Para buscar, primero selecciona un tipo de búsqueda [ACEPTAR]”
		 */
		addStep("Validar que muestre el mensaje “Para buscar, primero selecciona un tipo de búsqueda [ACEPTAR]”");
		validacion = OxxoAlDia.mensajeOrdenesRecibidasSinSelecion();
		assertTrue(validacion,"error, No se muestra el mensaje correcto");
		
		/*
		 * paso 7: Presionar “ACEPTAR”
		 */
		addStep("Presionar [ACEPTAR]");
		OxxoAlDia.opcionSiGeneral();
		
		/*
		 * paso 8: Validar que nos regrese a la pantalla “ÓRDENES DE COMPRA RECIBIDAS”
		 */
		addStep("Validar que nos regrese a la pantalla [ÓRDENES DE COMPRA RECIBIDAS]");
		validacion = OxxoAlDia.pantallaOrdenesCompraRecibidas();
		assertTrue(validacion, "error, la pantalla no se muestra de manera correcta");
		
		/*
		 * paso 9: Seleccionar el radio button “Número de Pedido”
		 */
		addStep("Seleccionar el radio button [Número de Pedido]");
		OxxoAlDia.opcionNumeroPedido();
		
		/*
		 * paso 10: Validar que Muestre check en color verde
		 */
		addStep("Validar que Muestre check en color verde");
		validacion = OxxoAlDia.validarcheckNumeroPedido();
		assertTrue(validacion, "error, no se muestra selecionado [Número de Pedido]");
		
		
		/*
		 * paso 11 Presionar el botón “BUSCAR”
		 */
		addStep("Presionar el botón [BUSCAR]");
		OxxoAlDia.opcionBuscarOrdenesCompra();
		
		/*
		 * paso 12: Validar que se muestre el mensaje “Por favor captura un número de pedido para continuar. [ACEPTAR]”
		 */
		addStep("Validar que se muestre el mensaje [Por favor captura un número de pedido para continuar.]");
		validacion = OxxoAlDia.mensajeOrdenesRecibidasSinCapturarNumero();
		assertTrue(validacion,"error, No se muestra el mensaje correcto");
		
		/*
		 * paso 13: Presionar “ACEPTAR”
		 */
		addStep("Presionar [ACEPTAR]");
		OxxoAlDia.opcionSiGeneral();
		
		
		/*
		 * paso 14: Ingresar un número de pedido incorrecto
		 */
		addStep("Ingresar un número de pedido incorrecto");
		OxxoAlDia.ingresarOrdenCompraIncorrecta();
		
		/*
		 * paso 15: Presionar el botón “BUSCAR”
		 */
		addStep("Presionar el botón [BUSCAR]");
		OxxoAlDia.opcionBuscarOrdenesCompra();
		
		/*
		 * paso 16: Validar que muestre el mensaje [El Número de Pedido capturado no se encuentra.]
		 */
		addStep("Validar que muestre el mensaje [El Número de Pedido capturado no se encuentra.]");
		validacion = OxxoAlDia.mensajeOrdenesRecibidasNoSeEncuentra();
		assertTrue(validacion,"error, No se muestra el mensaje correcto");
		
		/*
		 * paso 17: Presionar “ACEPTAR”
		 */
		addStep("Presionar [ACEPTAR]");
		OxxoAlDia.opcionSiGeneral();
		
		/*
		 * paso 18: Validar que nos regrese a la pantalla “ÓRDENES DE COMPRA RECIBIDAS”
		 */
		addStep("Validar que nos regrese a la pantalla [ÓRDENES DE COMPRA RECIBIDAS]");
		validacion = OxxoAlDia.pantallaOrdenesCompraRecibidas();
		assertTrue(validacion, "error, la pantalla no se muestra de manera correcta");
		
		/*
		 * paso 19: Seleccionar el radio button “Proveedor”
		 */
		addStep("Seleccionar el radio button [Proveedor]");
		OxxoAlDia.opcionProveedor();
		
		/*
		 * paso 20: Validar que Muestre check en color verde
		 */
		addStep("Validar que Muestre check en color verde");
		validacion = OxxoAlDia.validarcheckProveedor();
		assertTrue(validacion, "Error no se encuetra seleccionada la opcion [Proveedor]");
		
		/*
		 * paso 21: Presionar el botón “BUSCAR”
		 */
		addStep("Presionar [BUSCAR]");
		OxxoAlDia.opcionBuscarOrdenesCompra();
		
		/*
		 * paso 22: Validar que se muestre el mensaje [Por favor captura un número de proveedor para continuar.]
		 */
		addStep("Validar que se muestre el mensaje [Por favor captura un número de proveedor para continuar.]");
		validacion = OxxoAlDia.mensajeOrdenesRecibidasSinProveedor();
		assertTrue(validacion,"error, No se muestra el mensaje correcto");
		
		/*
		 * paso 23: Presionar “ACEPTAR”
		 */
		addStep("Presionar [ACEPTAR]");
		OxxoAlDia.opcionSiGeneral();
		
		/*
		 * paso 24: Ingresar un número de proveedor incorrecto
		 */
		addStep("Ingresar un número de proveedor incorrecto");
		OxxoAlDia.ingresarOrdenCompraIncorrecta();
		
		/*
		 * paso 25: Presionar el botón “BUSCAR”
		 */
		addStep("Presionar [BUSCAR]");
		OxxoAlDia.opcionBuscarOrdenesCompra();
		
		
		/*
		 * paso 26: Validar que muestre el mensaje “El Proveedor capturado no se encuentra. [ACEPTAR]”
		 */
		
		addStep("Validar que muestre el mensaje “El Proveedor capturado no se encuentra. [ACEPTAR]”");
		validacion = OxxoAlDia.mensajeOrdenesRecibidasProveedorNoSeEncuentra();
		assertTrue(validacion, "Error, no se mustra el mensaje [El Proveedor capturado no se encuentra.]");
		
		/*
		 * paso 27: Presionar “ACEPTAR”
		 */
		addStep("Presionar [ACEPTAR]");
		OxxoAlDia.opcionSiGeneral();
		
		/*
		 * paso 28: Validar que nos regrese a la pantalla “ÓRDENES DE COMPRA RECIBIDAS”
		 */
		addStep("Validar que nos regrese a la pantalla [ÓRDENES DE COMPRA RECIBIDAS]");
		validacion = OxxoAlDia.pantallaOrdenesCompraRecibidas();
		assertTrue(validacion, "error, la pantalla no se muestra de manera correcta");
		
		/*
		 * paso 29: Seleccionar el radio button “Número de Pedido”
		 */
		addStep("Seleccionar el radio button [Número de Pedido]");
		OxxoAlDia.opcionNumeroPedido();
		
		/*
		 * paso 30: Validar que Muestre check en color verde
		 */
		addStep("Validar que Muestre check en color verde");
		validacion = OxxoAlDia.validarcheckNumeroPedido();
		assertTrue(validacion, "error, no se muestra selecionado [Número de Pedido]");
		
		/*
		 * paso 31: Ingresar un número de pedido correcto de una recepción de días pasados
		 */
		addStep("Ingresar un número de pedido correcto de una recepción de días pasados");
		OxxoAlDia.obtenerNumeroPedido(data.get("IpEquipo"));
		OxxoAlDia.ingresarNoPedido();
		
		/*
		 * paso 32: Presionar el botón “BUSCAR”
		 */
		addStep("Presionar [BUSCAR]");
		OxxoAlDia.opcionBuscarOrdenesCompra();
		
		/*
		 * paso 33: Validar que se muestre el registro en el apartado de “PEDIDOS” mostrando correctamente el proveedor, folio, fecha y cantidad.
		 */
		addStep("Validar que se muestre el registro en el apartado de “PEDIDOS” mostrando correctamente el proveedor, folio, fecha y cantidad.");
		validacion = OxxoAlDia.validacionOrdenRecibida();
		assertTrue(validacion, "Error, no se muestra el registro");
		
		
		/*
		 * paso 34: Ingresar un número de proveedor existente
		 */
		addStep("Ingresar un número de proveedor existente");
		OxxoAlDia.opcionProveedor();
		OxxoAlDia.obtenerNumeroProveedor();
		OxxoAlDia.ingresarNoProveedor();
		
		/*
		 * paso 35: Presionar el botón “BUSCAR”
		 */
		addStep("Presionar [BUSCAR]");
		OxxoAlDia.opcionBuscarOrdenesCompra();
		
		/*
		 * paso 36: Validar que muestre la información disponible
		 */
		addStep("Validar que muestre la información disponible");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");	
		
	}
	
	/*
	 * Proveedores Directos MVP3:
	 * MTC-FT-179- Búsqueda de Órdenes de compra recibidas
	 * Desc: Validación de los warnings que muestra al realizar mal la búsqueda del pedido
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 28/10/2024
	 */

	@Override
	public String setTestFullName() {
		return "ATC_FT_103_BusquedaOrdenesRecibidas";
	}

	@Override
	public String setTestDescription() {
		return "Validación de los warnings que muestra al realizar mal la búsqueda del pedido";
	}

	@Override
	public String setTestDesigner() {
		return "Miguel Bautista";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1.-Contar con la aplicación más reciente de Oxxo al día \r\n"
				+ "2.- Validar con el equipo de Desarrollo que la opción esta deshabilitada en las BD de Movilidad\r\n"
				+ "3.- Validar en la tabla hc_purchaseorder órdenes de compra recibidas ";
	}

	@Override
	public void beforeTest() {

	}

}
