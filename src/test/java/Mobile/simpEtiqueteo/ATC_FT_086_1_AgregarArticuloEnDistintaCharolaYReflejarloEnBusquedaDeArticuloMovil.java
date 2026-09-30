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
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;
import utils.sql.SQLResult;

/**
 * DESCRIPCIÓN: 
 *  Se validara que al agregar un artículo en al menos una charola más, este genere un aumento a la cantidad de etiquetas a imprimir.
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC_FT_086_1_AgregarArticuloEnDistintaCharolaYReflejarloEnBusquedaDeArticuloMovil
 *
 * PRERREQUISITOS:
 *"1. Tablet con mínimo Android 11
2. Tener instalado aplicativo oxxo al día en Handheld.
3. Tener conectado el dispositivo móvil a una red donde mantenga comunicación con el punto de venta.
4. Establecer conexión a la BD de XPOS
5. Correr script ""ActualizaFechaPlanograma"" en BD de XPOS para ""resetear"" la conexión con planogramas
6. Establecer conexión a la BD FCPVEQA_SIV3
7. Archivo .csv para realizar cambios en planogramas
8. Establecer conexión con FTP (Nota: el consumo de archivo .csv, se realiza de manera automática por job:starMonitor) "
 * 
 * @author Mario A. De Leon Rdz.
 * @date 30 Enero 2025
 */
public class ATC_FT_086_1_AgregarArticuloEnDistintaCharolaYReflejarloEnBusquedaDeArticuloMovil extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_086_1_AgregarArticuloEnDistintaCharolaYReflejarloEnBusquedaDeArticuloMovil_test(HashMap<String, String> data) throws Exception {
		//PREREQUISITOS
		String serverXposStore = data.get("serverXposStore");
		String serverSatelites = data.get("serverSatelites");
		String serverSIV3 = data.get("serverSIV3");
		String usernameSIV3 = data.get("usernameSIV3");
		String passwordSIV3 = PasswordUtil.decryptPassword(data.get("passwordSIV3"));
		String plaza = data.get("plaza");
		String tienda = data.get("tienda");
		String testname = this.setTestFullName();

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
		simp.loginTablet(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")));
		simp.ExcelenciaOperativa();
		simp.Etiqueteo();
		//PASO 2 --------------------------------------------------------------------------------------------
		addStep("Selecciona el Botón Aceptar");
		simp.aceptarColocarPapel();
		//PASO 3 --------------------------------------------------------------------------------------------
		addStep("\"En BD XPOS, correr el siguiente query en tabla concentradora (construida por Dev) para identificar un artículo, activo con inventario,  que se encuentre en una sola charola\r\n"
				+ "\r\n"
				+ "SELECT ITEM_ID, SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "WHERE PLANOGROUP <> 'NULL'\r\n"
				+ "ORDER BY SKU\"");
		SQLResult resultConcentradoraPaso3 = sqlSimp.conseguirSKUSparaValidarEnRMS(true, true, true, serverSatelites, "LOCATION = '5' ");
		SQLResult resultPaso3 = sqlSimp.utilizarSKUEnRMS(resultConcentradoraPaso3, serverSatelites, "ORDER BY LOCATION DESC");
		String skuPaso3 = resultPaso3.getData(0, "SKU");
		System.out.println(skuPaso3);
		//PASO 4 --------------------------------------------------------------------------------------------
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
				+ "ORDER BY V.KA_CVE_VTA ASC\"");
		sqlSimp.consultaAOrigen(skuPaso3, serverSatelites);

		//PASO 5 --------------------------------------------------------------------------------------------
		addStep("\"En BD FCPVEQA_SIV3, correr el siguiente query en tablas origen y corroborar que el resultado del paso 3 coincida con el resultado de esta consulta:\r\n"
				+ "SELECT   ROWNUM row_num, x.*\r\n"
				+ "  FROM    (\r\n"
				+ "/* commented out gfigueira 24-FEB-10\r\n"
				+ "  SELECT   XXVA_ENG_WEB_HOME_PKG.escape_sc (l.planogrupo_desc) planogrupo\r\n"
				+ "  ,        XXVA_ENG_WEB_HOME_PKG.escape_sc (l.mueble_tienda_desc) mueble*/\r\n"
				+ "  SELECT   REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (l.planogrupo_desc   ,'á','a'),'é','e'),'í','i'),'ó','o'),'ú','u'),'¿?','A'),'ÿ','E'),'¿?','I'),'ÿ','O'),'ÿ','U'),'ñ','n'),'¿`','N') planogrupo\r\n"
				+ "  ,        REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (l.mueble_tienda_desc,'á','a'),'é','e'),'í','i'),'ó','o'),'ú','u'),'¿?','A'),'ÿ','E'),'¿?','I'),'ÿ','O'),'ÿ','U'),'ñ','n'),'¿`','N') mueble\r\n"
				+ "  ,        l.tamano_desc tramos\r\n"
				+ "  ,        a.charola\r\n"
				+ "  ,        DECODE (a.compartidos, NULL, NULL, SUBSTR (a.compartidos, 1, INSTR (a.compartidos, '.') - 1)) charola_compartida\r\n"
				+ "  ,        a.ubicacion_bandeja\r\n"
				+ "  ,        a.num_frentes frentes\r\n"
				+ "  ,        a.item sku\r\n"
				+ "  ,        DECODE (a.compartidos, NULL, NULL, XXVA_SIV3.XXVA_ENG_PLANOGRAMA_PKG.Get_SKU_Frente (a.legos_id, a.charola, a.ubicacion_bandeja, a.item, 'aprobado')) sku_frente\r\n"
				+ "  ,        s.tienda_id tienda\r\n"
				+ "  FROM     XXVA_SIV3.xxva_eng_planograma_aprobado a\r\n"
				+ "  ,        XXVA_SIV3.xxva_eng_legos_mv l\r\n"
				+ "  ,        XXVA_SIV3.xxva_sem_sembrado s\r\n"
				+ "  ,        XXVA_SIV3.xxva_sem_sembrado_mueble m\r\n"
				+ "  WHERE    a.item     != '0'\r\n"
				+ "  AND      l.legos_id  = a.legos_id\r\n"
				+ "  AND      l.legos_id  = m.legos_id\r\n"
				+ "  AND      s.sembrado_id = m.sembrado_id\r\n"
				+ "  AND      s.plaza_id  = '10MON'\r\n"
				+ "  AND      s.tienda_id = '50HZV'\r\n"
				+ "  AND      s.status    = 3\r\n"
				+ "  and a.item ='Ingresa el sku que se requiere consultar'\r\n"
				+ "  ORDER BY l.legos_desc\r\n"
				+ "  ,        a.charola\r\n"
				+ "  ,        a.ubicacion_bandeja\r\n"
				+ "  ) x;\"");
		SQLResult resultSIV3Paso5 = sqlSimp.sqlPlanogramaSIV3(skuPaso3, plaza, tienda, serverSIV3, usernameSIV3, passwordSIV3);
		sqlSimp.imprimirResult(resultSIV3Paso5);
		//PASO 6 --------------------------------------------------------------------------------------------
		addStep("En la app móvil, capturar manualmente el artículo seleccionado de la búsqueda anterior");
		simp.buscarSKU(skuPaso3);
		appiumUtil.driver.pressKey(new KeyEvent(AndroidKey.BACK));
		simp.esperaPantallaDeCarga();
		//PASO 7 --------------------------------------------------------------------------------------------
		addStep("\"Modifica los siguientes campos en archivo csv para agregar el artículo en otra charola más y posterior carga el archivo en el servidor FTP(oxvctmpq1.femcom.net):\r\n"
				+ "1. Copiar toda la fila del artículo en su charola actual, en una nueva fila y actualiza los 3 campos siguientes:\r\n"
				+ "Charola: Ingresa nueva charola (ejemplo si tenía 1, ingresa 2)\r\n"
				+ "Ubicación en la bandeja: Ingresa nueva ubicación (ejemplo si tenía 1, ingresa 2)\r\n"
				+ "Accion: Ingresa el texto AGREGAR\"");
		String CharolaIncrementada = String.valueOf(Integer.parseInt(resultPaso3.getData(0, "TRAY")) + 1);
		String UbicacionIncrementada = String.valueOf(Integer.parseInt(resultPaso3.getData(0, "LOCATION")) + 1);
		String[][] columnasYValores = {
	             {"UPC", skuPaso3},
	             {"Lego", resultSIV3Paso5.getData(0, "LEGO")},
	             {"Charola", CharolaIncrementada}, //Se incrementa en 1 el resultado original
	             {"# Frentes", resultPaso3.getData(0, "FRONTS")},
	             {"Ubicación en la bandeja", UbicacionIncrementada}, //Se incrementa en 1 el resultado original
	             {"Accion", "AGREGAR"}
	             };
		csvSimp.editarCSV2(testname, "1", columnasYValores);
		csvSimp.loadCSVtoFTP(testname, "1");
		csvSimp.cambioStatusCSV(skuPaso3,"LOCATION",UbicacionIncrementada,testname, "1", serverSatelites, "ORDER BY LOCATION DESC");
		//PASO 8 --------------------------------------------------------------------------------------------
		addStep("\"Correr script \"\"ActualizaFechaPlanograma\"\" en BD de XPOS para \"\"resetear\"\" la conexión con planogramas:\r\n"
				+ "USE XposStore\r\n"
				+ "DECLARE @fecha VARCHAR(100)\r\n"
				+ "SET @fecha = CONVERT(VARCHAR, GETDATE() -1, 111)\r\n"
				+ "EXEC Satelites.stl.SET_PARAM @Type = 'CONFIG'\r\n"
				+ "							,@Name = 'SIMP_ETIQ_IMP_PLANOG'\r\n"
				+ "							,@Value = @fecha\r\n"
				+ "							,@Level = 'Company'\r\n"
				+ "\r\n"
				+ "SELECT\r\n"
				+ "	VALUE\r\n"
				+ "FROM SYSTEM_PARAM_VALUE\r\n"
				+ "WHERE SYSTEM_PARAM_CATALOG_ID IN (SELECT\r\n"
				+ "		SYSTEM_PARAM_CATALOG_ID\r\n"
				+ "	FROM SYSTEM_PARAM_CATALOG\r\n"
				+ "	WHERE code = 'SIMP_ETIQ_IMP_PLANOG')\"");
		sqlSimp.sqlActualizaFechaPlanogramaMovil(serverSatelites);
		//PASO 9 --------------------------------------------------------------------------------------------
		addStep("Ingresar nuevamente a la opción \"Etiqueteo\" del aplicativo Oxxo al día (Iniciar sesión en Oxxo al día/seleccionar Excelencia Operativa/Etiqueteo)");
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		Thread.sleep(1000);
		simp.iniciarApp(data.get("appPackage"), data.get("appActivity"));
		Thread.sleep(3000);
		simp.loginTablet(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")));
		simp.validateLoginSucess();
		simp.ExcelenciaOperativa();
		simp.Etiqueteo();
		//PASO 10 --------------------------------------------------------------------------------------------
		addStep("Selecciona el Botón Aceptar");
		simp.aceptarColocarPapel();
		//PASO 11 --------------------------------------------------------------------------------------------
		addStep("\"En BD XPOS, correr el siguiente query en tabla concentradora (construida por Dev), para corroborar que se hayan actualizado el dato de la cantidad de charolas, en el artículo seleccionado.\r\n"
				+ "\r\n"
				+ "SELECT ITEM_ID, SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "WHERE SKU ='Introduce aquí el sku que deseas consultar'\"");
		sqlSimp.consultarSKUEnConcentradora(skuPaso3, serverSatelites);
		//PASO 12 --------------------------------------------------------------------------------------------
		addStep("\"En BD FCPVEQA_SIV3, correr el siguiente query en tablas origen, para corroborar que se haya agregado la nueva charola en el artículo seleccionado:\r\n"
				+ "SELECT   ROWNUM row_num, x.*\r\n"
				+ "  FROM    (\r\n"
				+ "/* commented out gfigueira 24-FEB-10\r\n"
				+ "  SELECT   XXVA_ENG_WEB_HOME_PKG.escape_sc (l.planogrupo_desc) planogrupo\r\n"
				+ "  ,        XXVA_ENG_WEB_HOME_PKG.escape_sc (l.mueble_tienda_desc) mueble*/\r\n"
				+ "  SELECT   REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (l.planogrupo_desc   ,'á','a'),'é','e'),'í','i'),'ó','o'),'ú','u'),'¿?','A'),'ÿ','E'),'¿?','I'),'ÿ','O'),'ÿ','U'),'ñ','n'),'¿`','N') planogrupo\r\n"
				+ "  ,        REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (REPLACE (l.mueble_tienda_desc,'á','a'),'é','e'),'í','i'),'ó','o'),'ú','u'),'¿?','A'),'ÿ','E'),'¿?','I'),'ÿ','O'),'ÿ','U'),'ñ','n'),'¿`','N') mueble\r\n"
				+ "  ,        l.tamano_desc tramos\r\n"
				+ "  ,        a.charola\r\n"
				+ "  ,        DECODE (a.compartidos, NULL, NULL, SUBSTR (a.compartidos, 1, INSTR (a.compartidos, '.') - 1)) charola_compartida\r\n"
				+ "  ,        a.ubicacion_bandeja\r\n"
				+ "  ,        a.num_frentes frentes\r\n"
				+ "  ,        a.item sku\r\n"
				+ "  ,        DECODE (a.compartidos, NULL, NULL, XXVA_SIV3.XXVA_ENG_PLANOGRAMA_PKG.Get_SKU_Frente (a.legos_id, a.charola, a.ubicacion_bandeja, a.item, 'aprobado')) sku_frente\r\n"
				+ "  ,        s.tienda_id tienda\r\n"
				+ "  FROM     XXVA_SIV3.xxva_eng_planograma_aprobado a\r\n"
				+ "  ,        XXVA_SIV3.xxva_eng_legos_mv l\r\n"
				+ "  ,        XXVA_SIV3.xxva_sem_sembrado s\r\n"
				+ "  ,        XXVA_SIV3.xxva_sem_sembrado_mueble m\r\n"
				+ "  WHERE    a.item     != '0'\r\n"
				+ "  AND      l.legos_id  = a.legos_id\r\n"
				+ "  AND      l.legos_id  = m.legos_id\r\n"
				+ "  AND      s.sembrado_id = m.sembrado_id\r\n"
				+ "  AND      s.plaza_id  = '10MON'\r\n"
				+ "  AND      s.tienda_id = '50HZV'\r\n"
				+ "  AND      s.status    = 3\r\n"
				+ "  and a.item ='Ingresa el sku que se requiere consultar'\r\n"
				+ "  ORDER BY l.legos_desc\r\n"
				+ "  ,        a.charola\r\n"
				+ "  ,        a.ubicacion_bandeja\r\n"
				+ "  ) x;\"");
		SQLResult resultSIV3Paso12 = sqlSimp.sqlPlanogramaSIV3(skuPaso3, plaza, tienda, serverSIV3, usernameSIV3, passwordSIV3);
		//PASO 13 --------------------------------------------------------------------------------------------
		addStep("En la app móvil, capturar manualmente el sku del artículo al que se le agregó el frente en nueva charola");
		System.out.println("\n FIN PASO 13 \n");
		simp.buscarSKU(skuPaso3);
		//PASO 14 --------------------------------------------------------------------------------------------
		addStep("Selecciona etiqueta perteneciente a la charola donde ya se encontraba inicialmente el artículo");
		simp.abrirPrimerEtiquetaSKU();
		//PASO 15 --------------------------------------------------------------------------------------------
		addStep("Selecciona [x]");
		simp.cerrarDetallesEtiquetaSKU();
		//PASO 16 --------------------------------------------------------------------------------------------
		addStep("Selecciona etiqueta perteneciente a la nueva charola dondese agregó el artículo");
		simp.abrirEtiquetaSKU(2);
		//PASO 17 --------------------------------------------------------------------------------------------
		addStep("Selecciona [x]");
		simp.cerrarDetallesEtiquetaSKU();
		//PASO 18 --------------------------------------------------------------------------------------------
		addStep("Presiona una vez el ícono [+] en la etiqueta perteneciente a la nueva charola donde se agregó el artículo");
		simp.agregarEtiquetaSKU(2);
		//PASO 19 --------------------------------------------------------------------------------------------
		addStep("Selecciona el ícono de notificación \"Lista de Etiquetas\"");
		simp.clickListaDeEtiquetas();
		//PASO 20 --------------------------------------------------------------------------------------------
		addStep("Seleccionar imagen de la etiqueta, para abrir información completa del artículo");
		simp.abrirPrimerEtiquetaSKU();
		//PASO 21 --------------------------------------------------------------------------------------------
		addStep("Selecciona [x]");
		simp.cerrarDetallesEtiquetaSKU();
		//PASO 22 --------------------------------------------------------------------------------------------
		addStep("Selecciona opción Imprimir");
		simp.imprimirEtiquetas();
		//PASO 23 --------------------------------------------------------------------------------------------
		addStep("Selecciona botón Aceptar");
		simp.aceptarCortarEtiquetas();
		String ContenidoEtiqueta = simp.leerContenidoArchivo(simp.obtenerArchivoDeEtiquetasMasReciente("Z:\\INV\\logs"));
		System.out.println(ContenidoEtiqueta);
		simp.validarContenidoEtiquetasImpresas(ContenidoEtiqueta, "tray", CharolaIncrementada);
		simp.validarContenidoEtiquetasImpresas(ContenidoEtiqueta, "location", UbicacionIncrementada);
		// Cerrar Apps
		Thread.sleep(3000);
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
    	}
	
		
	@Override
	public String setTestFullName() {
		return "ATC_FT_086_1_AgregarArticuloEnDistintaCharolaYReflejarloEnBusquedaDeArticuloMovil";
	}

	@Override
	public String setTestDescription() {
		return "Se validara que al agregar un artículo en al menos una charola más, este genere un aumento a la cantidad de etiquetas a imprimir.";
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
		return "\"1. Tablet con mínimo Android 11\r\n"
				+ "2. Tener instalado aplicativo oxxo al día en Handheld.\r\n"
				+ "3. Tener conectado el dispositivo móvil a una red donde mantenga comunicación con el punto de venta.\r\n"
				+ "4. Establecer conexión a la BD de XPOS\r\n"
				+ "5. Correr script \"\"ActualizaFechaPlanograma\"\" en BD de XPOS para \"\"resetear\"\" la conexión con planogramas\r\n"
				+ "6. Establecer conexión a la BD FCPVEQA_SIV3\r\n"
				+ "7. Archivo .csv para realizar cambios en planogramas\r\n"
				+ "8. Establecer conexión con FTP (Nota: el consumo de archivo .csv, se realiza de manera automática por job:starMonitor) \"";
	}

	@Override
	public void beforeTest() {
	}
}

