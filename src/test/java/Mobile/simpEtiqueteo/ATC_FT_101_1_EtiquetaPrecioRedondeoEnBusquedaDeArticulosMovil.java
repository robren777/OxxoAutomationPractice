package Mobile.simpEtiqueteo;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
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
 *  "Validar que:
1. Al aplicarle un precio de redondeo a un artículo, se muestren 2 etiquetas: 1 con su precio regular y la otra con el precio total del paquete.
2. En la impresión se agrupe el precio normal y de paquete del artículo, considerando el orden de impresión planogrupo, charola y ubicación"
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC_FT_101_1_EtiquetaPrecioRedondeoEnBusquedaDeArticulosMovil
 *
 * PRERREQUISITOS:
 * "1. Contar con 1 archivo ITM configurado con precio redondeo (TRAN_TYPE=11)
2. Tablet con mínimo Android 11
3. Tener instalado aplicativo oxxo al día en Handheld.
4. Tener conectado el dispositivo móvil a una red donde mantenga comunicación con el punto de venta.
5. Establecer conexión a la BD de XPOS
6. Conexión a Servidor FTP DS50 por medio de FileZilla
7. El CR a utilizar debe tener un buzón de bajada y subida"
 * 
 * @author Mario A. De Leon Rdz.
 * @date 30 Enero 2025
 */
public class ATC_FT_101_1_EtiquetaPrecioRedondeoEnBusquedaDeArticulosMovil extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_101_1_EtiquetaPrecioRedondeoEnBusquedaDeArticulosMovil_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Simplificacion de Etiqueteo en Tienda");
		
		/******************************* Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script  ********************/
		DesiredCapabilities cap = new DesiredCapabilities();
		
		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appium:appPackage", data.get("appPackage"));
		cap.setCapability("appium:appActivity", data.get("appActivity"));
		cap.setCapability("noReset", true); //Evita borrado de cache
		
		
		String url = "http://127.0.0.1:4723/";

		// Cerrar Apps
		Thread.sleep(5000);
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		
    	AppiumUtil appiumUtil = null;
    	try {
    		appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}
    	
    	Thread.sleep(2000);
    	appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
    	
    	String BDXposStore = data.get("serverXposStore");
		String BDSatelites = data.get("serverSatelites");
		String BDSiv3 = data.get("serverSIV3");
		String skuITEM1 = data.get("skuITM1");
		String skuITEM2 = data.get("skuITM2");
		
		SimpEtiqueteo simpEt = new SimpEtiqueteo(testCase, appiumUtil, this );
		SQLSimpEtiqueteo sqlSimp = new SQLSimpEtiqueteo(testCase, this);
		//INICIO DE SCRIPT
        /***************************************************/
		
    	//PASO 4
        /*********/
    	addStep("Ingresar a la opción \"Etiqueteo\" del aplicativo Oxxo al día (Iniciar sesión en Oxxo al día/seleccionar Excelencia Operativa/Etiqueteo)");
    	simpEt.iniciarApp(data.get("appPackage"), data.get("appActivity"));
    	simpEt.loginTablet(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")));
    	simpEt.ExcelenciaOperativa();
    	simpEt.Etiqueteo();
    	
    	//PASO 5
        /*********/
    	addStep("Selecciona el Botón Aceptar");
    	simpEt.aceptarColocarPapel();
    	
    	//PASO 6
        /*********/
    	addStep("\"En BD XPOS, correr los siguientes query en tabla concentradora (construida por Dev) para buscar el artículo en planogrupo al que se le aplicó el precio por redondeo, por medio del ITM que se consumirá por DS50:\r\n"
    			+ "\r\n"
    			+ "SELECT ITEM_ID, SKU, NAME, AMOUNT, AMOUNT_PROMO, UNIT_QUANTITY, INVENTORY, AMOUNT_PKG, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
    			+ "WHERE SKU ='Introduce aquí el sku que deseas consultar'\"");
    	
    	String queryPaso6 = "SELECT SKU, NAME, AMOUNT, AMOUNT_PROMO, UNIT_QUANTITY, INVENTORY, AMOUNT_PKG, STATUS_CODE, PLANOGROUP FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
    			+     			"WHERE SKU ='" + skuITEM1 +"'";
    	
		SQLResult resultPaso6 =sqlSimp.sqlExecute(queryPaso6, BDSatelites, sqlSimp.DB_USER_XPOS, sqlSimp.DB_PASSWORD_XPOS);
		sqlSimp.imprimirResult(resultPaso6);
		String sku1 = resultPaso6.getData(0, "SKU");
    	
		
    	//PASO 7
        /*********/
    	addStep("\"En BD XPOS, correr los siguientes query en tabla concentradora (construida por Dev) para buscar el artículo ASLEP al que se le aplicó el precio por redondeo, por medio del ITM que se consumirá por DS50:\r\n"
    			+ "\r\n"
    			+ "SELECT ITEM_ID, SKU, NAME, AMOUNT, AMOUNT_PROMO, UNIT_QUANTITY, INVENTORY, AMOUNT_PKG, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
    			+ "WHERE SKU ='Introduce aquí el sku que deseas consultar'\"");
    	
    	String queryPaso7 = "SELECT SKU, NAME, AMOUNT, AMOUNT_PROMO, UNIT_QUANTITY, INVENTORY, AMOUNT_PKG, STATUS_CODE, PLANOGROUP \r\n"
    			+ "FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
    			+ "where INVENTORY > 1 \r\n"
    			+ "and PLANOGROUP is null\r\n"
    			+ "and SKU ='" + skuITEM2 +"'";
    	
		SQLResult resultPaso7 =sqlSimp.sqlExecute(queryPaso7, BDSatelites, sqlSimp.DB_USER_XPOS, sqlSimp.DB_PASSWORD_XPOS);
		sqlSimp.imprimirResult(resultPaso7);
		String sku2 = resultPaso7.getData(0, "SKU");
    	
    	//PASO 8
        /*********/
    	addStep("Capturar manualmente el artículo en planogrupo al que se le agregó el precio por redondeo (paso 1)");
    	simpEt.buscarSKU(sku1);
    	
    	//PASO 9
        /*********/
    	addStep("Presiona una vez el ícono [+] en las etiquetas (multiempaque y precio regular) de un artículo en l misma ubicación");
    	simpEt.agregarPrimerEtiquetaSKU();
    	
    	Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");// Se desliza hacía abajo

    	simpEt.agregarPrimerEtiquetaSKU();	
    	
    	//PASO 10
        /*********/
    	addStep("Seleccionar ícono [x] del campo de búsqueda del producto");
    	simpEt.borrarBusquedaEtiquetaSKU();
    	
    	//PASO 11
    	/*********/
    	addStep("Capturar manualmente el artículo ASLEP al que se le agregó el precio por redondeo (paso 7)");
    	simpEt.buscarSKU(sku2);
    	
    	//PASO 12
        /*********/
    	addStep("Presiona una vez el ícono [+] en las etiquetas (multiempaque y precio regular) del artículo aslep buscado");
    	simpEt.agregarPrimerEtiquetaSKU();
    	simpEt.agregarPrimerEtiquetaSKU();
    	
    	//PASO 13
        /*********/
    	addStep("Selecciona el ícono de notificación \"Lista de Etiquetas\"");
    	simpEt.clickListaDeEtiquetas();
    	
    	//PASO 14
        /*********/
    	addStep("Selecciona opción Imprimir");
    	simpEt.imprimirEtiquetas();
    	
    	//PASO 15
        /*********/
    	addStep("Selecciona botón Aceptar");
    	simpEt.aceptarCortarEtiquetas();
    	String ContenidoEtiqueta2 = simpEt.leerContenidoArchivo(simpEt.obtenerArchivoDeEtiquetasMasReciente("Z:\\INV\\logs"));
		System.out.println(ContenidoEtiqueta2);
		simpEt.validarContenidoEtiquetasImpresas(ContenidoEtiqueta2, "sku", skuITEM1);
		simpEt.validarContenidoEtiquetasImpresas(ContenidoEtiqueta2, "sku", skuITEM2);
		// Cerrar Apps
		Thread.sleep(5000);
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		
    	}
	
		
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		return    "\"1. Contar con 1 archivo ITM configurado con precio redondeo (TRAN_TYPE=11)\r\n"
				+ "2. Tablet con mínimo Android 11\r\n"
				+ "3. Tener instalado aplicativo oxxo al día en Handheld.\r\n"
				+ "4. Tener conectado el dispositivo móvil a una red donde mantenga comunicación con el punto de venta.\r\n"
				+ "5. Establecer conexión a la BD de XPOS\r\n"
				+ "6. Conexión a Servidor FTP DS50 por medio de FileZilla\r\n"
				+ "7. El CR a utilizar debe tener un buzón de bajada y subida\""
				+ "8. Ejecutar pasos 1-3 como prerrequisito";
	}

	@Override
	public String setTestDescription() {
		return	  "\"Validar que:\r\n"
				+ "1. Al aplicarle un precio de redondeo a un artículo, se muestren 2 etiquetas: 1 con su precio regular y la otra con el precio total del paquete.\r\n"
				+ "2. En la impresión se agrupe el precio normal y de paquete del artículo, considerando el orden de impresión planogrupo, charola y ubicación\"";
	}

	@Override
	public String setTestDesigner() {
		return "Mario Salazar";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_101_1_EtiquetaPrecioRedondeoEnBusquedaDeArticulosMovil";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
