package Mobile.simpEtiqueteo;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.simpEtiqueteo.om.CSVSimpEtiqueteo;
import Mobile.simpEtiqueteo.om.SQLSimpEtiqueteo;
import Mobile.simpEtiqueteo.om.SimpEtiqueteo;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

/**
 * DESCRIPCIÓN: 
 *  "Validar que en la pantalla ""Búsqueda por artículo"":
1. Al ingresar el sku y/o nombre de un artículo inexistente en tienda, la app muestre un mensaje advirtiendo que ese artículo no se encontró.
2. Al intentar ingresar emojis o caracteres especiales no permita capturar los datos
3. Permita salir de la pantalla sin haber agregado etiquetas (tanto con el botón de la app, como con botón nativo de android)"
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC_FT_107_BusquedaManualDeArtículoInexistenteMovil
 *
 * PRERREQUISITOS:
 *"
1.Tablet con mínimo Android 11
2. Tener instalado aplicativo oxxo al día en Handheld.
3. Tener conectado el dispositivo móvil a una red donde mantenga comunicación con el punto de venta.
4. Establecer conexión a la BD de XPOS"
 * 
 * @author Mario A. De Leon Rdz.
 * @date 30 Enero 2025
 */
public class ATC_FT_107_BusquedaManualDeArtículoInexistenteMovil extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_107_BusquedaManualDeArtículoInexistenteMovil_test(HashMap<String, String> data) throws Exception {
		//PREREQUISITOS
		String serverXposStore = data.get("serverXposStore");
		String serverSatelites = data.get("serverSatelites");

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
    	CSVSimpEtiqueteo csvSimp = new CSVSimpEtiqueteo(appiumUtil, testCase, this);
    	sqlSimp.encenderImpresoraVirtual(serverXposStore);
    	sqlSimp.sqlActualizaFechaPlanogramaMovil(serverSatelites);
    	
    	Thread.sleep(2000);
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
		
		//PASO 1 --------------------------------------------------------------------------------------------
		addStep("Ingresar a la opción \"Etiqueteo\" del aplicativo Oxxo al día (Iniciar sesión en Oxxo al día/seleccionar Excelencia Operativa/Etiqueteo)");
		appiumUtil.driver.activateApp(data.get("appPackage")+"/"+data.get("appActivity"));
		simp.login(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")));
		simp.ExcelenciaOperativa();
		simp.Etiqueteo();
		//PASO 2 --------------------------------------------------------------------------------------------
		addStep("Selecciona el Botón Aceptar");
		simp.aceptarColocarPapel();
		//PASO 3 --------------------------------------------------------------------------------------------
		addStep("\"En BD XPOS, correr el siguiente query en tabla concentradora (construida por Dev) para identificar que un sku no exista en el punto de venta:\r\n"
				+ "\r\n"
				+ "SELECT ITEM_ID, SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "WHERE SKU ='Ingresa el sku que se quiere dar de alta'\"");
		String skuPaso3="123456789";
		sqlSimp.consultarSKUEnConcentradora(skuPaso3, serverSatelites);
		//PASO 4 --------------------------------------------------------------------------------------------
		addStep("\"En BD XPOS, correr el siguiente query en tablas origen (ya existentes en XPOS), para corroborar que el sku no exista en el punto de venta:\r\n"
				+ "\r\n"
				+ "SELECT I.ITEM_ID, V.KA_CVE_VTA SKU, P.AMOUNT PRECIO, C.NAME STATUS, SUM(KA_UDS * M.MULTIPLIER) CANTIDAD_EN_INVENTARIO\r\n"
				+ "FROM SATELITES.IO.KA_KARDX_VW V, XposStore.dbo.MOVEMENT M, XposStore.dbo.CODE CO, XposStore.dbo.ITEM I, XposStore.dbo.CODE C, XposStore.dbo.ITEM_BUSINESS_UNIT_LINK IBUL, XposStore.dbo.price P\r\n"
				+ "WHERE V.KA_CVE_MOV = M.CODE\r\n"
				+ "AND CO.CODE_ID = M.MOVEMENT_TYPE_ID\r\n"
				+ "AND CO.NAME IN ('IN_UNIT_VALUE_MOVEMENT','INITIAL_MOVEMENT','OUT_UNIT_VALUE_MOVEMENT')\r\n"
				+ "AND V.KA_CVE_VTA = I.SKU\r\n"
				+ "AND I.ITEM_STATUS_CODE_ID = C.CODE_ID\r\n"
				+ "AND IBUL.ITEM_ID = I.ITEM_ID AND IBUL.IS_ACTIVE = 1\r\n"
				+ "AND P.ITEM_BUSINESS_UNIT_LINK_ID = IBUL.ITEM_BUSINESS_UNIT_LINK_ID AND P.IS_ACTIVE = 1\r\n"
				+ "AND FROM_DATE>=(SELECT MAX(FROM_DATE) FROM XposStore.dbo.price p WHERE p.ITEM_BUSINESS_UNIT_LINK_ID=IBUL.ITEM_BUSINESS_UNIT_LINK_ID AND p.is_active = 1)\r\n"
				+ "--AND i.SKU='Ingresa el sku de interés'\r\n"
				+ "GROUP BY V.KA_CVE_VTA, C.NAME, P.AMOUNT, I.ITEM_ID\r\n"
				+ "ORDER BY V.KA_CVE_VTA ASC\r\n"
				+ "\r\n"
				+ "SELECT * FROM Satelites.HC.FNKRDXUNITS('Ingresa el sku de interés');\"");
		sqlSimp.consultaAOrigen(skuPaso3, serverSatelites);

		//PASO 5 --------------------------------------------------------------------------------------------
		addStep("En la app móvil, capturar manualmente el sku del producto que no existe en el punto de venta");
		simp.buscarSKU(skuPaso3);
		//PASO 6 --------------------------------------------------------------------------------------------
		addStep("Seleccionar ícono [x] del campo de búsqueda del producto");
		simp.borrarBusquedaEtiquetaSKU();
		//PASO 7 --------------------------------------------------------------------------------------------
		addStep("En la app móvil, capturar manualmente el nombre de un producto que no existe en el punto de venta");
		simp.buscarSKU("comida para perro");
		//PASO 8 --------------------------------------------------------------------------------------------
		addStep("Seleccionar ícono [x] del campo de búsqueda del producto");
		simp.borrarBusquedaEtiquetaSKU();
		//PASO 9 --------------------------------------------------------------------------------------------
		addStep("Ingresar caracteres especiales y emojis");
		simp.buscarSKU("!!!! :)"); 
		//PASO 10 --------------------------------------------------------------------------------------------
		addStep("Seleccionar el ícono Regresar (<--) del aplicativo");
		simp.regresarAndroid();
		//PASO 11 --------------------------------------------------------------------------------------------
		addStep("En la app móvil, capturar manualmente el sku del producto que no existe en el punto de venta");
		simp.buscarSKU(skuPaso3);
		//PASO 12 --------------------------------------------------------------------------------------------
		addStep("Seleccionar el ícono Regresar (<--) nativo de android");
		simp.regresarAndroid();
		// Cerrar Apps
		Thread.sleep(3000);
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
    	}
	
		
	@Override
	public String setTestFullName() {
		return "ATC_FT_107_BusquedaManualDeArtículoInexistenteMovil";
	}

	@Override
	public String setTestDescription() {
		return "\"Validar que en la pantalla \"\"Búsqueda por artículo\"\":\r\n"
				+ "1. Al ingresar el sku y/o nombre de un artículo inexistente en tienda, la app muestre un mensaje advirtiendo que ese artículo no se encontró.\r\n"
				+ "2. Al intentar ingresar emojis o caracteres especiales no permita capturar los datos\r\n"
				+ "3. Permita salir de la pantalla sin haber agregado etiquetas (tanto con el botón de la app, como con botón nativo de android)\"";
	}

	@Override
	public String setTestDesigner() {
		return "Mario A. De Leon Rdz.";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "\"\r\n"
				+ "1.. Handheld con mínimo Android 11\r\n"
				+ "2. Tener instalado aplicativo oxxo al día en Handheld.\r\n"
				+ "3. Tener conectado el dispositivo móvil a una red donde mantenga comunicación con el punto de venta.\r\n"
				+ "4. Establecer conexión a la BD de XPOS\"";
	}

	@Override
	public void beforeTest() {
	}
}

