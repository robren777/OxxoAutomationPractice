package Mobile.simpEtiqueteo;


import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
 *  Asegurar que al agregar al menos un frente en la misma charola, a un artículo, este incremento de frentes no aumente la cantidad de etiquetas a imprimir, para dar cumplimiento a la regla de negocio de solo imprimir una etiqueta por frente (frentes en misma charola)
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC_FT_084_SubirFrentesDeArticuloEnMismaCharolaYReflejarloEnBusquedaDeArticuloMovil
 *
 * PRERREQUISITOS:
 *  "1. Handheld con mínimo Android 11
2. Tener instalado aplicativo oxxo al día en Handheld.
3. Tener conectado el dispositivo móvil a una red donde mantenga comunicación con el punto de venta.
4. Establecer conexión a la BD de XPOS
5. Correr script ""ActualizaFechaPlanograma"" en BD de XPOS para ""resetear"" la conexión con planogramas
6. Establecer conexión a la BD FCPVEQA_SIV3
7. Archivo .csv para realizar cambios en planogramas
8. Establecer conexión con FTP (Nota: el consumo de archivo .csv, se realiza de manera automática por job:starMonitor) "
 * 
 * @author Mario Salazar y Mario De León
 * @date 30 Enero 2025
 */
public class ATC_FT_084_SubirFrentesDeArticuloEnMismaCharolaYReflejarloEnBusquedaDeArticuloMovil extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	
	public void ATC_FT_084_SubirFrentesDeArticuloEnMismaCharolaYReflejarloEnBusquedaDeArticuloMovil_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Simplificacion de Etiqueteo en Tienda");
		
		/******************************* INICIO - CONFIGURACION PRE ELIMINAR DE DISPOSITIVO A  EJECUTAR SCRIPT  ********************/
		DesiredCapabilities cap = new DesiredCapabilities();
		
		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appium:appPackage", data.get("appPackage"));
		cap.setCapability("appium:appActivity", data.get("appActivity"));
		cap.setCapability("noReset", true); //Evita borrado de cache
		
		String serverSatelites = data.get("serverSatelites");
		String url = "http://127.0.0.1:4723/";

		// CERRAR APPS
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
    	
    	
    	SimpEtiqueteo simpEt = new SimpEtiqueteo(testCase, appiumUtil, this );
    	Thread.sleep(2000);
    	appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
    	

		String BDSatelites = data.get("serverSatelites");
		String plaza = data.get("plaza");
		String tienda = data.get("tienda");
		String serverSIV3 = data.get("serverSIV3");
		String usernameSIV3 = data.get("usernameSIV3");
		String passwordSIV3 = PasswordUtil.decryptPassword(data.get("passwordSIV3"));
    	CSVSimpEtiqueteo csvSimpEt = new CSVSimpEtiqueteo (appiumUtil,testCase, this);
		SQLSimpEtiqueteo sqlSimp = new SQLSimpEtiqueteo(testCase, this);
		String testname = setTestFullName();
    	//PASO 1
        /*********/
		addStep("Ingresar a la opción \"Etiqueteo\" del aplicativo Oxxo al día (Iniciar sesión en Oxxo al día/seleccionar Excelencia Operativa/Etiqueteo)");
		simpEt.login(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")));
    	simpEt.ExcelenciaOperativa();
    	simpEt.Etiqueteo();
    	
    	//PASO 2
        /*********/
    	addStep("Selecciona el Botón Aceptar");
    	simpEt.aceptarColocarPapel();
    	
    	//PASO 3
        /*********/
    	addStep("\"En BD XPOS, correr el siguiente query en tabla concentradora (construida por Dev) para identificar un artículo activo, con inventario, que se encuentre en una sola charola y con un solo frente\r\n"
    			+ "\r\n"
    			+ "SELECT ITEM_ID, SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
    			+ "WHERE PLANOGROUP <> 'NULL'\r\n"
    			+ "ORDER BY SKU\"");
    	String queryPaso3 = "SELECT TOP 20 SKU, COUNT(*) AS Repetidos FROM [Satelites].[dbo].[CO_LB_INTL_OX] \n"
				+ "WHERE LEN(SKU) >= 13 AND SKU LIKE '75%' AND PLANOGROUP IS not null AND  INVENTORY > 0 AND FRONTS = 1 AND STATUS_CODE = 'ACTIVE_ITEM_STATUS' "
				+ "GROUP BY SKU "
				+ "HAVING COUNT(*) = 1 ";
		SQLResult resultConcentradoraPaso3 = sqlSimp.sqlExecuteSinEv(queryPaso3, serverSatelites, sqlSimp.DB_USER_XPOS, sqlSimp.DB_PASSWORD_XPOS);
		SQLResult resultPaso3 = sqlSimp.utilizarSKUEnRMS(resultConcentradoraPaso3, serverSatelites);
		String skuITM = resultPaso3.getData(0, "SKU");
		System.out.println(skuITM);
    	//PASO 4
        /*********/
    	addStep("En BD XPOS, correr el siguiente query en tablas origen (ya existentes en XPOS), para corroborar que el resultado anterior coincida con el resultado de esta consulta:\r\n"
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
    	List<String> skus = new ArrayList<String>();
		skus.add(skuITM);
    	sqlSimp.consultaAOrigen(skus, BDSatelites);
    	//PASO 5
        /*********/
    	addStep("En la BD FCPVEQA_SIV3, correr el siguiente query en tablas origen y corroborar que el resultado del paso 3 coincida con el resultado de esta consulta:\r\n"
    			+ "\r\n"
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
    			+ "  ) x;");
    	
    	SQLResult resultPaso5SIV3 = sqlSimp.sqlPlanogramaSIV3(resultPaso3.getData (0, "SKU"), plaza, tienda, serverSIV3, usernameSIV3, passwordSIV3);
    	//PASO 6
        /*********/
    	addStep("En la app móvil, capturar manualmente el sku del resultado de la búsqueda anterior");
    	simpEt.buscarSKU(skuITM);
    	//PASO 7 
        /*********/
    	addStep("Seleccionar imagen de la etiqueta, para abrir información completa del artículo");
    	simpEt.abrirPrimerEtiquetaSKU();
    	//PASO 8
        /*********/
    	addStep("Seleccionar [x]");
    	simpEt.cerrarDetallesEtiquetaSKU();
    	appiumUtil.driver.pressKey(new KeyEvent(AndroidKey.BACK));
		simpEt.esperaPantallaDeCarga();
    	//PASO 9
        /*********/
    	addStep("\"Modifica los siguientes campos en archivo csv para agregar al artículo un frente más en la misma charola y posterior carga el archivo en el servidor FTP(oxvctmpq1.femcom.net):\r\n"
    			+ "# Frentes: Ingresa un número de frentes a agregar (ejemplo si tenía 1, ingresa 2)\r\n"
    			+ "Accion: Ingresa el texto Modificar\"");
    	String FrentesIncrementado = String.valueOf(Integer.parseInt(resultPaso3.getData(0, "FRONTS")) + 1);
    	String[][] columnasYValoresFila2 = {
	             {"UPC", skuITM },
	             {"Lego", resultPaso5SIV3.getData(0, "LEGO")},
	             {"Charola", resultPaso3.getData(0, "TRAY")},
	             {"Ubicación en la bandeja", resultPaso3.getData(0, "LOCATION")},
	             {"# Frentes",FrentesIncrementado},
	             {"Accion", "MODIFICAR"}
	         };
		
    	csvSimpEt.editarCSV2(testname, "1", columnasYValoresFila2);
    	csvSimpEt.loadCSVtoFTP(testname, "1");
    	csvSimpEt.cambioStatusCSV(skuITM, "FRONTS", FrentesIncrementado, testname,"1",serverSatelites);
    	
    	//PASO 10
        /*********/
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
    	
    	sqlSimp.sqlActualizaFechaPlanogramaConEv(BDSatelites);
    	
    	Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
    	
    	//PASO 11
    	/*********/
    	addStep("Ingresar nuevamente a la opción \"Etiqueteo\" del aplicativo Oxxo al día (Iniciar sesión en Oxxo al día/seleccionar Excelencia Operativa/Etiqueteo)");
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		Thread.sleep(1000);
		simpEt.iniciarApp(data.get("appPackage"), data.get("appActivity"));
		Thread.sleep(3000);
		simpEt.login(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")));
		simpEt.validateLoginSucess();
		simpEt.ExcelenciaOperativa();
		simpEt.Etiqueteo();
    	//PASO 12
        /*********/
    	addStep("Selecciona el Botón Aceptar");
    	simpEt.aceptarColocarPapel();
    	//PASO 13
        /*********/
    	addStep("\"En BD XPOS, correr el siguiente query en tabla concentradora (construida por Dev), para corroborar que se haya actualizado el dato de frente, en el artículo seleccionado.\r\n"
    			+ "\r\n"
    			+ "SELECT ITEM_ID, SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
    			+ "WHERE SKU ='Introduce aquí el sku que deseas consultar'\"");
    	
    	String Querypaso11 = ("SELECT SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, TRAY, LOCATION, FRONTS "
    			+ "FROM [Satelites].[dbo].[CO_LB_INTL_OX] WHERE SKU = '" + skuITM + "'");
    	SQLResult resultPaso13 =sqlSimp.sqlExecute(Querypaso11, BDSatelites, sqlSimp.DB_USER_XPOS, sqlSimp.DB_PASSWORD_XPOS);
		sqlSimp.imprimirResult(resultPaso13);
    	//PASO 14
        /*********/
    	addStep("En la BD FCPVEQA_SIV3, correr el siguiente query en tablas origen y corroborar que el resultado del paso 3 coincida con el resultado de esta consulta:\r\n"
    			+ "\r\n"
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
    			+ "  ) x;");
    	SQLResult resultPaso14 =sqlSimp.sqlPlanogramaSIV3(skuITM, plaza, tienda, serverSIV3, usernameSIV3, passwordSIV3);
    	sqlSimp.imprimirResult(resultPaso14);
    	//PASO 15
        /*********/
    	addStep("En la app móvil, capturar manualmente el sku del artículo al que se le agregó el frente");
    	simpEt.buscarSKU(skuITM);
    	
    	//PASO 16
        /*********/
    	addStep("Seleccionar imagen de la etiqueta, para abrir información completa del artículo");
    	simpEt.abrirPrimerEtiquetaSKU();
    	
    	//PASO 17
    	/*********/
    	addStep("Selecciona [x]");
    	simpEt.cerrarDetallesEtiquetaSKU();
    	
    	//PASO 18
        /*********/
    	addStep("Presiona una vez el ícono [+] en la etiqueta mostrada");
    	simpEt.agregarCantidadDeEtiquetas(1);
    	
    	//PASO 19
        /*********/
    	addStep("Selecciona el ícono de notificación \"Lista de Etiquetas\"");
    	simpEt.clickListaDeEtiquetas();
    	
    	//PASO 20
        /*********/
    	addStep("Seleccionar imagen de la etiqueta, para abrir información completa del artículo");
    	simpEt.abrirPrimerEtiquetaSKU();
    	
    	//PASO 21
        /*********/
    	addStep("Selecciona [x]");
    	simpEt.cerrarDetallesEtiquetaSKU();
    	
    	//PASO 21
        /*********/
    	addStep("Selecciona opción Imprimir");
    	simpEt.imprimirEtiquetas();
    	
    	//PASO 21
        /*********/
    	addStep("Selecciona botón Aceptar");
    	simpEt.aceptarCortarEtiquetas();
    	
	}
	
	
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		return    "\"1. Handheld con mínimo Android 11\r\n"
				+ "2. Tener instalado aplicativo oxxo al día en Handheld.\r\n"
				+ "3. Tener conectado el dispositivo móvil a una red donde mantenga comunicación con el punto de venta.\r\n"
				+ "4. Establecer conexión a la BD de XPOS\r\n"
				+ "5. Correr script \"\"ActualizaFechaPlanograma\"\" en BD de XPOS para \"\"resetear\"\" la conexión con planogramas\r\n"
				+ "6. Establecer conexión a la BD FCPVEQA_SIV3\r\n"
				+ "7. Archivo .csv para realizar cambios en planogramas\r\n"
				+ "8. Establecer conexión con FTP (Nota: el consumo de archivo .csv, se realiza de manera automática por job:starMonitor) \"";
	}

	@Override
	public String setTestDescription() {
		return	  "Asegurar que al agregar al menos un frente en la misma charola, a un artículo, este incremento de frentes no aumente la cantidad de etiquetas a imprimir, para dar cumplimiento a la regla de negocio de solo imprimir una etiqueta por frente (frentes en misma charola)";
	}

	@Override
	public String setTestDesigner() {
		return "Mario Salazar y Mario De León";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_084_SubirFrentesDeArticuloEnMismaCharolaYReflejarloEnBusquedaDeArticuloMovil";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}

