package Mobile.simpEtiqueteo;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.simpEtiqueteo.om.SQLSimpEtiqueteo;
import Mobile.simpEtiqueteo.om.SimpEtiqueteo;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;
import utils.sql.SQLResult;

/**
 * DESCRIPCIÓN: 
 * Realizar impresion de  varios planogrupos donde los articulos tenga  mas de una ubicación en varias charolas verificar que en la impresion salga ordenada 
 * de izquierda a derecha de abajo hacia arriba y tambien verificar que las etiquetas no salgan duplicadas validar que salga en orden alfabetico
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC_FT_149_HigienicoMOVILIDADEtiquetaPromocionSimpleEnBusquedaDePlanograma
 *
 * PRERREQUISITOS:
 * 1. Tener conectado el punto de venta con la bandera de etiqueteo encendida.
 * 2. Contar con dispositivo HH con la aplicacion de oxxo al dia.
 * 3. Tener la impresora conectada y con papel para la impresión de etiquetas
 * 4. Contar con artículos que tenga una ubicación en varias charolas de varios planogrupos. A los cuales se les agregará mas de una ubicacion separada en varias charolas de varios planogrupos.
 * 5. Correr script "ActualizaFechaPlanograma" en BD de XPOS para "resetear" la conexión con planogramas
 * 6. Establecer conexión a la BD FCPVEQA_SIV3
 * 7. Establecer conexión con FTP (Nota: el consumo de archivo .csv, se realiza de manera automática por job:starMonitor)
 *
 * @author Mario A. De Leon Rdz.
 * @date 22 Enero 2025
 */
public class ATC_FT_149_HigienicoMOVILIDADEtiquetaPromocionSimpleEnBusquedaDePlanograma extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_149_HigienicoMOVILIDADEtiquetaPromocionSimpleEnBusquedaDePlanograma_test(HashMap<String, String> data) throws Exception {
		String ip = data.get("IP");
		String serverXposStore = "jdbc:sqlserver://"+ip+":1433;databaseName=XposStore;encrypt=true;trustServerCertificate=true;loginTimeout=30";
		String serverSatelites = "jdbc:sqlserver://"+ip+":1433;databaseName=Satelites;encrypt=true;trustServerCertificate=true;loginTimeout=30";
		String skuPRMFinal = data.get("skuPRMFinal");
		String skuPRMMonto = data.get("skuPRMMonto");
		String skuPRMPorcentaje = data.get("skuPRMPorcentaje");

		//Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script
		DesiredCapabilities cap = new DesiredCapabilities();
		
		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appium:appPackage", data.get("appPackageP"));
		cap.setCapability("appium:appActivity", data.get("appActivityP"));
		cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto
		
		String url = "http://127.0.0.1:4723/";
		
		// Cerrar Apps
		Thread.sleep(5000);
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");

    	
    	AppiumUtil appiumUtil = null;
    	try {
    		appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n"
					+ e.getMessage());
			e.printStackTrace();
		}
    	SimpEtiqueteo simp = new SimpEtiqueteo(testCase, appiumUtil, this);
    	SQLSimpEtiqueteo sqlSimp = new SQLSimpEtiqueteo(testCase, this);
    	sqlSimp.encenderImpresoraVirtual(serverXposStore);
    	sqlSimp.sqlActualizaFechaPlanogramaMovil(serverSatelites);

    	Thread.sleep(2000);
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));

		//PASO 5 --------------------------------------------------------------------------------------
		addStep("Seleccionar app Oxxo al día");
		simp.iniciarApp(data.get("appPackage"), data.get("appActivity"));
		//REGRESAR PDV A FECHA ORIGINAL
		String fecha = appiumUtil.driver.getDeviceTime("DD-MM-YYYY");
		simp.cambioFechaRemoto(ip, fecha);
		//PASO 6---------------------------------------------------------------------------------------
		addStep("Escribir usuario");
		simp.loginUsuario(data.get("userLogin"));
		//PASO 7 --------------------------------------------------------------------------------------
		addStep("Escribir contraseña");
		simp.loginContrasena(PasswordUtil.decryptPassword(data.get("passwordLogin")));
		//PASO 8 --------------------------------------------------------------------------------------
		addStep("Selecciona la opción ingresar");
		simp.loginIngresar();
		//PASO 9 --------------------------------------------------------------------------------------
		addStep("Selecciona la opción Excelencia Operativa");
		simp.ExcelenciaOperativa();
		//PASO 10 --------------------------------------------------------------------------------------
		addStep("Selecciona la opcion de Etiqueteo");
		simp.Etiqueteo();
		//PASO 11 --------------------------------------------------------------------------------------
		addStep("En BD XPOS, correr el siguiente query en tabla concentradora (construida por Dev) para buscar los artículos a los que se le aplicaron las promociones simples con descuento por precio final, por porcentaje y por monto, por medio del PRM que se consumirá por DS50:\r\n"
				+ "\r\n"
				+ "SELECT ITEM_ID, SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "WHERE SKU  IN  ('SKU1','SKU2','SKU3')--'Ingresa los sku seleccionados en el paso 1'");
		String queryPaso11 = "SELECT SKU, NAME, AMOUNT, AMOUNT_PROMO, INVENTORY, STATUS_CODE, PLANOGROUP, VALIDITY_PROMO_STT, VALIDITY_PROMO_END "
				+ "FROM [Satelites].[dbo].[CO_LB_INTL_OX] WHERE SKU IN ('"+skuPRMFinal+"', '"+skuPRMMonto+"','"+skuPRMPorcentaje+"' ) "; 
		SQLResult resultPaso11 =sqlSimp.sqlExecute(queryPaso11, serverSatelites, sqlSimp.DB_USER_XPOS, sqlSimp.DB_PASSWORD_XPOS);
		sqlSimp.imprimirResult(resultPaso11);
		//PASO 12 --------------------------------------------------------------------------------------
		addStep("Se presiona el boton Aceptar");
		simp.aceptarColocarPapel();
		//PASO 13 --------------------------------------------------------------------------------------
		addStep("Seleccionar la opcion de planogrupo");
		simp.clickBusquedaPlanograma();
		//PASO 14 --------------------------------------------------------------------------------------
		addStep("Seleccionamos el planogrupo al que se le realizo el cambio ");
		simp.buscarYAbrirPlanogrupo(resultPaso11.getData(0, "PLANOGROUP"));
		//PASO 15 --------------------------------------------------------------------------------------
		addStep("Damos click al icono de impresión del planogrupo imprimiendo solo los cambios mostrados");
		simp.imprimirEtiquetasActualizadas();
		//PASO 16 --------------------------------------------------------------------------------------
		addStep("Seleccionamos aceptar");
		simp.aceptarCortarEtiquetas();
		String ContenidoEtiqueta1 = simp.leerContenidoArchivo(simp.obtenerArchivoDeEtiquetasMasReciente("Z:\\INV\\logs"));
		System.out.println(ContenidoEtiqueta1);		
		//se valida que se incluyan etiquetas con promocion activa
		simp.validarNoContenidoEtiquetasImpresas(ContenidoEtiqueta1, "savingMoney", "0");
		//PASO 17 --------------------------------------------------------------------------------------
		addStep("Adelantar 1 fin de día en el punto de venta y en el dispositivo movil para que las promociones simples aplicadas venzan");
		System.out.println("Fecha original: " + fecha +"\n");
		// Formatear y adelantar un día
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String nuevaFecha = LocalDate.parse(fecha, formatter).plusDays(1).format(formatter);
		
		System.out.println("Fecha con un día más: " + nuevaFecha+"\n");
		simp.cambioFechaRemoto(ip, nuevaFecha);

		//PASO 18  --------------------------------------------------------------------------------------
		addStep("Seleccionar app Oxxo al día");
		appiumUtil.driver.terminateApp(data.get("appPackage"));

		Thread.sleep(2000);
		simp.iniciarApp(data.get("appPackage"), data.get("appActivity"));
		//PASO 19 --------------------------------------------------------------------------------------
		addStep("Escribir usuario");
		simp.loginUsuario(data.get("userLogin"));
		//PASO 20 --------------------------------------------------------------------------------------
		addStep("Escribir contraseña");
		simp.loginContrasena(PasswordUtil.decryptPassword(data.get("passwordLogin")));
		//PASO 21 --------------------------------------------------------------------------------------
		addStep("Selecciona la opción ingresar");
		simp.loginIngresar();
		//PASO 22 --------------------------------------------------------------------------------------
		addStep("Selecciona la opción Excelencia Operativa");
		simp.ExcelenciaOperativa();
		//PASO 23 --------------------------------------------------------------------------------------
		addStep("Selecciona la opcion de Etiqueteo");
		simp.Etiqueteo();
		//PASO 24 --------------------------------------------------------------------------------------
		addStep("Selecciona el Botón Aceptar");
		simp.aceptarColocarPapel();
		//PASO 25 --------------------------------------------------------------------------------------
		addStep("Seleccionar la opcion de planogrupo");
		simp.clickBusquedaPlanograma();
		//PASO 26 --------------------------------------------------------------------------------------
		addStep("Seleccionamos el planogrupo al que se le realizo el cambio ");
		simp.buscarYAbrirPlanogrupo(resultPaso11.getData(0, "PLANOGROUP"));
		//PASO 27 --------------------------------------------------------------------------------------
		addStep("Damos click al icono de impresión del planogrupo imprimiendo solo los cambios mostrados");
		simp.imprimirEtiquetasActualizadas();
		//PASO 28 --------------------------------------------------------------------------------------
		addStep("Seleccionamos aceptar");
		simp.aceptarCortarEtiquetas();
		
		//LEER ETIQUETAS EN LA RUTA ESPECIFICADA EN UNIDAD DE RED
		String ContenidoEtiqueta2 = simp.leerContenidoArchivo(simp.obtenerArchivoDeEtiquetasMasReciente("Z:\\INV\\logs"));
		System.out.println(ContenidoEtiqueta2);
		//VALIDAR QUE LAS ETIQUETAS CONTENGAN<savingMoney>0</savingMoney>
		simp.validarContenidoEtiquetasImpresas(ContenidoEtiqueta2, "savingMoney", "0");
		//REGRESAR PDV A FECHA ORIGINAL
		simp.cambioFechaRemoto(ip, fecha);
    	}
	
		
	@Override
	public String setTestFullName() {
		return "ATC_FT_149_HigienicoMOVILIDADEtiquetaPromocionSimpleEnBusquedaDePlanograma";
	}

	@Override
	public String setTestDescription() {
		return "\"Validar que:\r\n"
				+ "1. Al aplicarle una promoción simple por precio final a un artículo, se muestre 1 etiqueta: con su precio promocional, lo mismo al aplicarle una promoción simple por porcentaje y por monto.\r\n"
				+ "2. En la impresión se agrupe considerando el orden de impresión planogrupo, charola y ubicación\r\n"
				+ "3. Asegurar que al vencer el precio promocional, las etiquetas vuelvan a mostrar los datos correctos sin aplicar promoción y solo con su precio regular.\"";
	}

	@Override
	public String setTestDesigner() {
		return "Mario A. De León Rdz.";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1. HandHeld.\n"
				+ "2. Usuario y Contraseña de Aplicación.\n"
				+ "3. Comunicación entre HH y POS.\n"
				+ "4. QR Repartidor con FA inválida"
				+ "5. Fecha del sistema de HH y del Punto de Venta vinculado debe ser la misma"
				+ "7. Ejecutar de forma manual pasos 1-4";
	}

	@Override
	public void beforeTest() {
	}
}

