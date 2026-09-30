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
 *  "Validar que:
1. Al buscar un artículo que ya tiene etiquetas agregadas, se muestre su card correspondiente con la cantidad de etiquetas agregadas previamente
2. Al presionar el Botón [Imprimir], te permita cancelar la acción."
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - ATC_FT_109_1_BuscarEtiquetasProvenientesDeDiversasBusquedasManuales
 *
 * PRERREQUISITOS:
 *
1. Tablet con mínimo Android 11
2. Tener instalado aplicativo oxxo al día en Handheld.
3. Tener conectado el dispositivo móvil a una red donde mantenga comunicación con el punto de venta.
4. Establecer conexión a la BD de XPOS
 * 
 * @author Mario Salazar
 * @date 30 Enero 2025
 */
public class ATC_FT_109_1_BuscarEtiquetasProvenientesDeDiversasBusquedasManuales extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_109_1_BuscarEtiquetasProvenientesDeDiversasBusquedasManuales_test(HashMap<String, String> data) throws Exception {
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
		
    	String BDSatelites = data.get("serverSatelites");
    	SimpEtiqueteo simpEt = new SimpEtiqueteo(testCase, appiumUtil, this );
		SQLSimpEtiqueteo sqlSimp = new SQLSimpEtiqueteo(testCase, this);
		
    	//PASO 1
        /*********/
		addStep("\"En BD XPOS, ccorrer el siguiente query en tabla concentradora (construida por Dev) para identificar 2 artículos ASLEP, activo y con inventario\r\n"
				+ "\r\n"
				+ "SELECT ITEM_ID, SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "ORDER BY PLANOGROUP, INVENTORY\"");
		
		String Querypaso1 = "SELECT TOP 2 SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, TRAY, LOCATION, FRONTS \r\n"
				+ "FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "WHERE PLANOGROUP IS NULL\r\n"
				+ "AND INVENTORY > 0\r\n"
				+ "AND STATUS_CODE = 'ACTIVE_ITEM_STATUS'\r\n"
				+ "ORDER BY PLANOGROUP, INVENTORY";
						
		SQLResult resulquery1 = sqlSimp.sqlExecute(Querypaso1, BDSatelites, sqlSimp.DB_USER_XPOS, sqlSimp.DB_PASSWORD_XPOS);
		sqlSimp.imprimirResult(resulquery1);

		String skuITEM1 = resulquery1.getData(0, "SKU");
		String skuITEM2 = resulquery1.getData(1, "SKU");
						
    	//PASO 2
        /*********/
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
		
		List<String> skus = new ArrayList<String>();
		skus.add(skuITEM1);
		skus.add(skuITEM2);
		
		sqlSimp.consultaAOrigen(skus, BDSatelites);
		//PASO 3
        /*********/
    	addStep("Ingresar a la opción \"Etiqueteo\" del aplicativo Oxxo al día (Iniciar sesión en Oxxo al día/seleccionar Excelencia Operativa/Etiqueteo)");
    	simpEt.loginTablet(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")));
    	simpEt.ExcelenciaOperativa();
    	simpEt.Etiqueteo();
    	
    	//PASO 4
        /*********/
    	addStep("Selecciona el Botón Aceptar");
    	simpEt.aceptarColocarPapel();
    	
    	//PASO 5
        /*********/
    	addStep("En la app móvil, capturar manualmente el sku del 1er artículo seleccionado en la búsqueda del paso 1 (ASLEP, activo, con inventario)");
    	simpEt.buscarSKU(skuITEM1);
    	
    	//PASO 6
        /*********/
    	addStep("Presiona botón [+] 5 veces");
    	simpEt.agregarCantidadDeEtiquetas(5);
		
    	//PASO 7
        /*********/
    	addStep("Seleccionar ícono [x] del campo de búsqueda del producto");
    	simpEt.borrarBusquedaEtiquetaSKU();
    	
    	//PASO 8
        /*********/
    	addStep("En la app móvil, capturar manualmente el sku del 2do artículo seleccionado en la búsqueda del paso 1 (ASLEP, activo, con inventario)");
    	simpEt.buscarSKU(skuITEM2);
    	
    	//PASO 9
        /*********/
    	addStep("Presiona botón [+] 5 veces");
    	simpEt.agregarCantidadDeEtiquetas(5);
    	
    	//PASO 10
        /*********/
    	addStep("Seleccionar ícono [x] del campo de búsqueda del producto");
    	simpEt.borrarBusquedaEtiquetaSKU();
    	
    	//PASO 11
    	/*********/
    	addStep("En la app móvil, captura nuevamente el sku del 1er artículo seleccionado en la búsqueda del paso 1 (ASLEP, activo, con inventario)");
    	simpEt.buscarSKU(skuITEM1);
    	
    	//PASO 12
        /*********/
    	addStep("Presiona botón [+] una vez");
    	simpEt.agregarCantidadDeEtiquetas(2, true);

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
    	addStep("Selecciona Aceptar");
    	simpEt.aceptarCortarEtiquetas();
    	String ContenidoEtiqueta2 = simpEt.leerContenidoArchivo(simpEt.obtenerArchivoDeEtiquetasMasReciente("Z:\\INV\\logs"));
		System.out.println(ContenidoEtiqueta2);
		simpEt.validarContenidoEtiquetasImpresas(ContenidoEtiqueta2, "sku", skuITEM1);
		simpEt.validarContenidoEtiquetasImpresas(ContenidoEtiqueta2, "sku", skuITEM2);
		// CERRAR APP
		Thread.sleep(5000);
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
		
    	}
	
		
	@Override
	public String setTestFullName() {
		return "ATC_FT_109_1_BuscarEtiquetasProvenientesDeDiversasBusquedasManuales";
	}

	@Override
	public String setTestDescription() {
		return "\"Validar que:\r\n"
				+ "1. Al buscar un artículo que ya tiene etiquetas agregadas, se muestre su card correspondiente con la cantidad de etiquetas agregadas previamente\r\n"
				+ "2. Al presionar el Botón [Imprimir], te permita cancelar la acción.\"";
	}

	@Override
	public String setTestDesigner() {
		return "Mario Salazar";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "\"\r\n"
				+ "1.. Tablet con mínimo Android 11\r\n"
				+ "2. Tener instalado aplicativo oxxo al día en Handheld.\r\n"
				+ "3. Tener conectado el dispositivo móvil a una red donde mantenga comunicación con el punto de venta.\r\n"
				+ "4. Establecer conexión a la BD de XPOS\r\n"
				+ "\"";
	}

	@Override
	public void beforeTest() {
	}
}
