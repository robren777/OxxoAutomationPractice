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

/**
 * DESCRIPCIÓN: 
 *  Validar que en el módulo de  busqueda por articulo del sipositivo movil, se muestre el artículo que se dio de alta en tienda, para que permita la impresión de su respectiva etiqueta
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC-FT-097_Etiqueta_por_alta_de_articulo_en_busqueda_de_articulos_Movil
 *
 * PRERREQUISITOS:
 * 1. Contar con archivo ITM configurado para Alta de artículo (TRAN_TYPE=1)
2. Contar con archivo ILS (con artículo que se dará de alta, asociado a un proveedor)
3. Handheld con mínimo Android 11
4. Tener instalado aplicativo oxxo al día en Handheld.
5. Tener conectado el dispositivo móvil a una red donde mantenga comunicación con el punto de venta.
6. Establecer conexión a la BD de XPOS
7. Conexión a Servidor FTP DS50 por medio de FileZilla
8. El CR a utilizar debe tener un buzón de bajada y subida
 *
 * INSUMOS:
 * 1. Contar con archivo ITM configurado para Alta de artículo (TRAN_TYPE=1)
 * 2. Contar con archivo ILS (con artículo que se dará de alta, asociado a un proveedor)
 * @author Mario Salazar
 * @date 22 Enero 2025
 */
public class ATC_FT_097_EtiquetaPorAltaDeArticuloEnBusquedaDeArticulosMovil extends BaseExecution {
	
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_097_EtiquetaPorAltaDeArticuloEnBusquedaDeArticulosMovil_test(HashMap<String, String> data) throws Exception {
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
    	
   
		
		SimpEtiqueteo simpEt = new SimpEtiqueteo(testCase, appiumUtil, this );
		SQLSimpEtiqueteo sqlSimp = new SQLSimpEtiqueteo(testCase, this);
		String BDXposStore = data.get("serverXposStore");
		String BDSatelites = data.get("serverSatelites");
		String skuITM =  data.get("skuITM");

		//INICIO DE SCRIPT
        /***************************************************/
		
		//PASO 1
        /*********/
		addStep("PRERREQUISITO: En BD XPOS, correr el siguiente query en tablas origen (ya existentes en XPOS), para buscar el artículo del ITM que se dará de alta por DS50:\r\n"
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
				+ "ORDER BY V.KA_CVE_VTA ASC");
		
		//PASO 2 
        /*********/
		
		addStep("PRERREQUISITO: En BD XPOS, correr el siguiente query en tabla concentradora (construida por Dev) para buscar el artículo del ITM que se dará de alta por DS50:\r\n"
				+ "\r\n"
				+ "SELECT ITEM_ID, SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "WHERE SKU ='Ingresa el sku que se quiere dar de alta'");
				
		//PASO 3 
        /*********/
		addStep("PRERREQUISITO: Carga en buzón (por medio de FTP) el archivo ITM (que incluye el artículo que se dará de alta en el punto de venta) para que la DS50 lo consuma.");
				
		//PASO 4
        /*********/
		addStep("PRERREQUISITO: Carga en buzón (por medio de FTP) el archivo ILS (que asocia el artículo dado de alta a un proveedor) para que la DS50 lo consuma.");
		
		//PASO 5
        /*********/
		addStep("PRERREQUISITO: Carga en buzón (por medio de FTP) el archivo ILS (que asocia el artículo dado de alta a un proveedor) para que la DS50 lo consuma.");
				
		//PASO 6
        /*********/
		addStep("PRERREQUISITO: Realizar los siguientes pasos para generar una Orden de compra con el artículo que se dio de alta\r\n"
				+ "1.Abrir Herramienta de compra: Journal/Opciones/Herramienta de compra\r\n"
				+ "2. Crear OC: Nueva/Buscar/Seleccionar proveedor al que se asignó el artículo dado de alta/Buscar/Identificar artículo que se dio de alta (con Inventario Op en 0) y en campo \"\"Ordenar\"\", ingresar cantidad mayor a 1/Cerrar Orden/Sí/\"");
				
		//PASO 7
        /*********/
		addStep("\"PRERREQUISITO: Realizar los siguientes pasos para recibir la orden de compra colocada, para que el artículo que se dio de alta tenga inventario:\r\n"
				+ "1. Abrir OC creada/Colocar \"\"x\"\" numero en el campo de \"\"Remisión\"\"/Identificar artículo que se dio de alta para ingresar en campo \"\"Recibido\"\" la misma cantidad que se ordenó/Enter/Ingresar \"\"x\"\" cantidad en campo \"\"Cifra control\"\"/Recibir/Si/Si\"");
		
		//PASO 8
		 /*********/
		addStep("\"En BD XPOS, correr el siguiente query en tablas origen (ya existentes en XPOS), para buscar el artículo del ITM que se dió de alta por DS50:\r\n"
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
				+ "--AND i.SKU='Ingresa el sku que se dió de alta'\r\n"
				+ "GROUP BY V.KA_CVE_VTA, C.NAME, P.AMOUNT, I.ITEM_ID\r\n"
				+ "ORDER BY V.KA_CVE_VTA ASC\"");
		sqlSimp.consultaAOrigen(skuITM, BDSatelites);
	
		//PASO 9
		/*********/
		addStep("Ingresar a la opción \"Etiqueteo\" del aplicativo Oxxo al día (Iniciar sesión en Oxxo al día/seleccionar Excelencia Operativa/Etiqueteo)");
		simpEt.login(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")));
    	simpEt.ExcelenciaOperativa();
    	simpEt.Etiqueteo();
    	
		//PASO 10
		/*********/
		addStep("Selecciona el Botón Aceptar");
		simpEt.aceptarColocarPapel();
		
		//PASO 11
        /*********/
		addStep("\"\"En BD XPOS, correr el siguiente query en tabla concentradora (construida por Dev) para buscar el artículo que se dió de alta:\r\n"
				+ "\r\n"
				+ "SELECT ITEM_ID, SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "WHERE SKU ='Ingresa el sku que se dio de alta'");
		sqlSimp.consultarSKUEnConcentradora(skuITM, BDSatelites);
		//PASO 12
        /*********/
		addStep("Capturar manualmente el sku que se dio de alta");
		simpEt.buscarSKU(skuITM);
								
		//PASO 13
		addStep("Presiona una vez el ícono [+] en la etiqueta");
		simpEt.agregarPrimerEtiquetaSKU();
		
		//PASO 14
		addStep("Selecciona el ícono de notificación \"Lista de Etiquetas\"");
		simpEt.clickListaDeEtiquetas();
		
		//PASO 15
		addStep("Selecciona opción Imprimir");
		simpEt.imprimirEtiquetas();
		
		//PASO 16
		addStep("Selecciona botón Aceptar");
	    simpEt.aceptarCortarEtiquetas();
	    simpEt.esperaPantallaDeCarga();
	    String ContenidoEtiqueta = simpEt.leerContenidoArchivo(simpEt.obtenerArchivoDeEtiquetasMasReciente("Z:\\INV\\logs"));
	    simpEt.validarContenidoEtiquetasImpresas(ContenidoEtiqueta, "sku", skuITM);
		// Cerrar Apps
		
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		
    	}
	
		
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		return    "\"1. Contar con archivo ITM configurado para Alta de artículo (TRAN_TYPE=1)\r\n"
				+ "2. Contar con archivo ILS (con artículo que se dará de alta, asociado a un proveedor)\r\n"
				+ "3. Handheld con mínimo Android 11\r\n"
				+ "4. Tener instalado aplicativo oxxo al día en Handheld.\r\n"
				+ "5. Tener conectado el dispositivo móvil a una red donde mantenga comunicación con el punto de venta.\r\n"
				+ "6. Establecer conexión a la BD de XPOS\r\n"
				+ "7. Conexión a Servidor FTP DS50 por medio de FileZilla\r\n"
				+ "8. El CR a utilizar debe tener un buzón de bajada y subida\""
				+ "9. Ejecutar pasos 1-7 de forma manual";
	}

	@Override
	public String setTestDescription() {
		return	  "Validar que en el módulo de  busqueda por articulo del sipositivo movil, se muestre el artículo que se dio de alta en tienda, para que permita la impresión de su respectiva etiqueta";
	}

	@Override
	public String setTestDesigner() {
		return "Mario Salazar";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_097_EtiquetaPorAltaDeArticuloEnBusquedaDeArticulosMovil";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	
}

