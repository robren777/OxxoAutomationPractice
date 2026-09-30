package Mobile.simpEtiqueteo;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
 *  "\"Validar que:\r\n"
	"1. Al seleccionar el botón [Cancelar] de la opción \"\"Vaciar lista\"\" se mantenga el listado de etiquetas\r\n"
	"2. Al seleccionar el botón [Aceptar] de la opción \"\"Vaciar lista\"\" se eliminen las etiquetas agregadas y te regrese a la pantalla de \"\"Busqueda por artículo\"\"\""
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC_FT_110_1_VaciarListaDeArticulosBuscados
 *
 * PRERREQUISITOS:
 *"
1.Tablet con mínimo Android 11
2. Tener instalado aplicativo oxxo al día en Handheld.
3. Tener conectado el dispositivo móvil a una red donde mantenga comunicación con el punto de venta.
4. Establecer conexión a la BD de XPOS
"
 * 
 * @author Mario A. De Leon Rdz.
 * @date 30 Enero 2025
 */
public class ATC_FT_110_1_VaciarListaDeArticulosBuscados extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_110_1_VaciarListaDeArticulosBuscados_test(HashMap<String, String> data) throws Exception {
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
    	sqlSimp.encenderImpresoraVirtual(serverXposStore);
    	sqlSimp.sqlActualizaFechaPlanogramaMovil(serverSatelites);
    	
    	Thread.sleep(2000);
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
		
		//PASO 1 --------------------------------------------------------------------------------------------
		addStep("\"En BD XPOS, ccorrer el siguiente query en tabla concentradora (construida por Dev) para identificar 2 artículos ASLEP, inactivos y con inventario\r\n"
				+ "\r\n"
				+ "SELECT ITEM_ID, SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "ORDER BY PLANOGROUP, INVENTORY\"");
		String queryPaso1 = "SELECT TOP 2 ITEM_ID, SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "WHERE INVENTORY>0 AND STATUS_CODE='INACTIVE_ITEM_STATUS' AND PLANOGROUP IS NULL AND LEN(SKU) >= 13 AND SKU LIKE '75%' order by sku desc";
		SQLResult resultPaso1 = sqlSimp.sqlExecute(queryPaso1, serverSatelites, sqlSimp.DB_USER_XPOS, sqlSimp.DB_PASSWORD_XPOS);
		String sku1Paso1 = resultPaso1.getData(0, "SKU");
		String sku2Paso1 = resultPaso1.getData(1, "SKU");
		//PASO 2 --------------------------------------------------------------------------------------------
		addStep("\"En BD XPOS, correr el siguiente query en tablas origen (ya existentes en XPOS), para corroborar que el resultado anterior coincida con el resultado de esta consulta:\r\n"
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
		List<String> skusPaso1 = new ArrayList<String>();
		skusPaso1.add(sku1Paso1);
		skusPaso1.add(sku2Paso1);
		sqlSimp.consultaAOrigen(skusPaso1, serverSatelites);
		sqlSimp.consultaKrdxUnits(sku1Paso1, serverSatelites);
		sqlSimp.consultaKrdxUnits(sku2Paso1, serverSatelites);
		//PASO 3 --------------------------------------------------------------------------------------------
		addStep("Ingresar a la opción \"Etiqueteo\" del aplicativo Oxxo al día (Iniciar sesión en Oxxo al día/seleccionar Excelencia Operativa/Etiqueteo)");
		appiumUtil.driver.activateApp(data.get("appPackage")+"/"+data.get("appActivity"));
		simp.loginTablet(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")));
		simp.ExcelenciaOperativa();
		simp.Etiqueteo();
		//PASO 4 --------------------------------------------------------------------------------------------
		addStep("Selecciona el Botón Aceptar");
		simp.aceptarColocarPapel();
		//PASO 5 --------------------------------------------------------------------------------------------
		addStep("En la app móvil, capturar manualmente el primer sku del artículo seleccionado en la búsqueda anterior (ASLEP, inactivo, con inventario)");
		simp.buscarSKU(sku1Paso1);
		//PASO 6 --------------------------------------------------------------------------------------------
		addStep("Presiona botón [+] n cantidad de veces");
		simp.agregarCantidadDeEtiquetas(5);
		//PASO 7 --------------------------------------------------------------------------------------------
		addStep("Seleccionar ícono [x] del campo de búsqueda del producto");
		simp.borrarBusquedaEtiquetaSKU();
		//PASO 8 --------------------------------------------------------------------------------------------
		addStep("En la app móvil, capturar manualmente el segundo sku del artículo seleccionado en la búsqueda anterior (ASLEP, inactivo, con inventario)");
		simp.buscarSKU(sku2Paso1);
		//PASO 9 --------------------------------------------------------------------------------------------
		addStep("Presiona botón [+] n cantidad de veces");
		simp.agregarCantidadDeEtiquetas(5);
		//PASO 10 --------------------------------------------------------------------------------------------
		addStep("Selecciona el ícono de notificación \"Lista de Etiquetas\"");
		simp.clickListaDeEtiquetas();
		//PASO 11 --------------------------------------------------------------------------------------------
		addStep("Seleccionar opción \"Vaciar Lista\"");
		simp.clickListaDeEtiquetas();
		//PASO 12 --------------------------------------------------------------------------------------------
		addStep("Selecciona la opción Cancelar");
		simp.cancelarVaciarListaDeEtiquetas();
		//PASO 13 --------------------------------------------------------------------------------------------
		addStep("Seleccionar opción \"Vaciar Lista\"");
		simp.clickListaDeEtiquetas();
		//PASO 14 --------------------------------------------------------------------------------------------
		addStep("Selecciona la opción Aceptar");
		simp.aceptarColocarPapel();
		// Cerrar Apps
		Thread.sleep(3000);
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
    	}
	
		
	@Override
	public String setTestFullName() {
		return "ATC_FT_110_1_VaciarListaDeArticulosBuscados";
	}

	@Override
	public String setTestDescription() {
		return "\"Validar que:\r\n"
				+ "1. Al seleccionar el botón [Cancelar] de la opción \"\"Vaciar lista\"\" se mantenga el listado de etiquetas\r\n"
				+ "2. Al seleccionar el botón [Aceptar] de la opción \"\"Vaciar lista\"\" se eliminen las etiquetas agregadas y te regrese a la pantalla de \"\"Busqueda por artículo\"\"\"";
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
				+ "4. Establecer conexión a la BD de XPOS\r\n"
				+ "\"";
	}

	@Override
	public void beforeTest() {
	}
}

