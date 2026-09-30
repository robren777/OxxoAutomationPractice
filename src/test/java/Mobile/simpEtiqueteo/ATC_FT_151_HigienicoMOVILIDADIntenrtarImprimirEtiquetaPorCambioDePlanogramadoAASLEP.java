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
 *  Validar que al pasar de un articulo planogramado a Aslep este no se muestre en el apartado de planograma para imprimir
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC_FT_151_HigienicoMOVILIDADIntenrtarImprimirEtiquetaPorCambioDePlanogramadoAASLEP
 *
 * PRERREQUISITOS:
 * "1. Tener conectado el punto de venta.
2.-Contar con dispositivo HH con la aplicacion de oxxo al dia.
3. Contar con archivo CSV configurado para pasar de un articulo planogramado a ASLEP se debera tambien tener el job Star monitor ejecutandose de forma automatica
4. Tener bandera de Etiqueteo encendida
5. Conexión a Servidor FTP por medio de FileZilla (El CR a utilizar debe contar con buzon de bajada y subida activo)"
 * 
 * @author Mario A. De Leon Rdz.
 * @date 30 Enero 2025
 */
public class ATC_FT_151_HigienicoMOVILIDADIntenrtarImprimirEtiquetaPorCambioDePlanogramadoAASLEP extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_151_HigienicoMOVILIDADIntenrtarImprimirEtiquetaPorCambioDePlanogramadoAASLEP_test(HashMap<String, String> data) throws Exception {
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
		addStep("\"En BD XPOS, correr el siguiente query en tabla concentradora (construida por Dev) para identificar un artículo activo, con inventario, que se encuentre en una sola charola con más de un frente\r\n"
				+ "\r\n"
				+ "SELECT ITEM_ID, SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "WHERE PLANOGROUP <> 'NULL'\r\n"
				+ "ORDER BY SKU\"");
		SQLResult resultConcentradoraPaso1 = sqlSimp.conseguirSKUSparaValidarEnRMS(true, true, true, serverSatelites, "TRAY BETWEEN '1' and '4' ");
		SQLResult resultPaso1 = sqlSimp.utilizarSKUEnRMS(resultConcentradoraPaso1, serverSatelites, "ORDER BY TRAY ASC");
		String skuPaso1 = resultPaso1.getData(0, "SKU");
		System.out.println(skuPaso1);
		//PASO 2 --------------------------------------------------------------------------------------------
		addStep("\"En BD XPOS, correr el siguiente query en tablas origen (ya existentes en XPOS), para corroborar que el resultado anterior coincida con el resultado de esta consulta:\r\n"
				+ "SELECT I.ITEM_ID, V.KA_CVE_VTA SKU, P.AMOUNT PRECIO, C.NAME STATUS, SUM(KA_UDS * M.MULTIPLIER) CANTIDAD_EN_INVENTARIO\r\n"
				+ "FROM SATELITES.IO.KA_KARDX_VW V, XposStore.dbo.MOVEMENT M, XposStore.dbo.ITEM I, XposStore.dbo.CODE     C, XposStore.dbo.ITEM_BUSINESS_UNIT_LINK IBUL, XposStore.dbo.price P\r\n"
				+ "WHERE V.KA_CVE_MOV = M.CODE\r\n"
				+ "AND V.KA_CVE_VTA = I.SKU\r\n"
				+ "AND I.ITEM_STATUS_CODE_ID = C.CODE_ID\r\n"
				+ "AND IBUL.ITEM_ID = I.ITEM_ID AND IBUL.IS_ACTIVE = 1\r\n"
				+ "AND P.ITEM_BUSINESS_UNIT_LINK_ID = IBUL.ITEM_BUSINESS_UNIT_LINK_ID AND P.IS_ACTIVE = 1\r\n"
				+ "AND FROM_DATE>=(SELECT MAX(FROM_DATE) FROM XposStore.dbo.price p WHERE p.ITEM_BUSINESS_UNIT_LINK_ID=IBUL.ITEM_BUSINESS_UNIT_LINK_ID AND p.is_active = 1)\r\n"
				+ "AND i.SKU='Ingresa el sku seleccionado en el paso anterior'\r\n"
				+ "GROUP BY V.KA_CVE_VTA, C.NAME, P.AMOUNT, I.ITEM_ID\r\n"
				+ "ORDER BY V.KA_CVE_VTA ASC\"");
		sqlSimp.consultaAOrigen(skuPaso1, serverSatelites);
		//PASO 3 --------------------------------------------------------------------------------------------
		addStep("\"En BD FCPVEQA_SIV3, correr el siguiente query en tablas origen y corroborar que el resultado del paso 6 coincida con el resultado de esta consulta:\r\n"
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
				+ "  AND      s.plaza_id  = 'Plaza'\r\n"
				+ "  AND      s.tienda_id = 'CR Tienda'\r\n"
				+ "  AND      s.status    = 3\r\n"
				+ "  and a.item ='Ingresar SKU'\r\n"
				+ "  ORDER BY l.legos_desc\r\n"
				+ "  ,        a.charola\r\n"
				+ "  ,        a.ubicacion_bandeja\r\n"
				+ "  ) x;\"");
		SQLResult resultSIV3Paso3 = sqlSimp.sqlPlanogramaSIV3(skuPaso1, plaza, tienda, serverSIV3, usernameSIV3, passwordSIV3);
		//PASO 4 --------------------------------------------------------------------------------------------
		addStep("Escribir usuario");
		simp.iniciarApp(data.get("appPackage"), data.get("appActivity"));	
		simp.loginUsuario(data.get("userLogin"));
		//PASO 5 --------------------------------------------------------------------------------------------
		addStep("Escribir contraseña");
		simp.loginContrasena(PasswordUtil.decryptPassword(data.get("passwordLogin")));
		//PASO 6 --------------------------------------------------------------------------------------------
		addStep("Selecciona la opción ingresar");
		simp.loginIngresar();
		//PASO 7 --------------------------------------------------------------------------------------------
		addStep("Selecciona la opción Excelencia Operativa");
		simp.ExcelenciaOperativa();
		//PASO 8 --------------------------------------------------------------------------------------------
		addStep("Selecciona la opcion de Etiqueteo");		
		simp.Etiqueteo();	
		//PASO 9 --------------------------------------------------------------------------------------------
		addStep("Selecciona el Botón Aceptar");
		simp.aceptarColocarPapel();
		//PASO 10 --------------------------------------------------------------------------------------------
		addStep("Seleccionar la opcion de planogrupo");
		simp.clickBusquedaPlanograma();
		appiumUtil.driver.pressKey(new KeyEvent(AndroidKey.BACK));
		//PASO 11 --------------------------------------------------------------------------------------------
		addStep("\"Modifica los siguientes campos en archivo csv para quitar un artículo del planograma y pase a ser ASLEP, y posterior carga el archivo en el servidor FTP(oxvctmpq1.femcom.net):\r\n"
				+ "1. Identifica todas las filas de las charolas donde se encuentre el artículo y actualiza el campo:\r\n"
				+ "Accion: Ingresa el texto ELIMINAR\"");
		String[][] columnasYValores = {
	             {"UPC", skuPaso1},
	             {"Lego", resultSIV3Paso3.getData(0, "LEGO")},
	             {"Charola", resultPaso1.getData(0, "TRAY")},
	             {"# Frentes", resultPaso1.getData(0, "FRONTS")},
	             {"Ubicación en la bandeja", resultPaso1.getData(0, "LOCATION")}, 
	             {"Accion", "ELIMINAR"}
	             };
		csvSimp.editarCSV2(testname, "1", columnasYValores);
		csvSimp.loadCSVtoFTP(testname, "1");
		csvSimp.cambioStatusCSV(skuPaso1, testname, "1", serverSatelites);
		//PASO 12 --------------------------------------------------------------------------------------------
		addStep("\"En BD FCPVEQA_SIV3, correr el siguiente query en tablas origen para corroborar que se haya actualizado el dato de frente en el artículo seleccionado:\r\n"
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
				+ "  AND      s.plaza_id  = 'Plaza'\r\n"
				+ "  AND      s.tienda_id = 'CR Tienda'\r\n"
				+ "  AND      s.status    = 3\r\n"
				+ "  and a.item ='Ingresar SKU'\r\n"
				+ "  ORDER BY l.legos_desc\r\n"
				+ "  ,        a.charola\r\n"
				+ "  ,        a.ubicacion_bandeja\r\n"
				+ "  ) x;\"");
		sqlSimp.sqlPlanogramaSIV3(skuPaso1, plaza, tienda, serverSIV3, usernameSIV3, passwordSIV3);
		//PASO 13 --------------------------------------------------------------------------------------------
		addStep("\"Correr script \"\"ActualizaFechaPlanograma\"\" en BD de XPOS para \"\"resetear\"\" la conexión con planogramas:\r\n"
				+ "--Descripcion: actualiza parametro de fecha de planograma(SIMP_ETIQ_IMP_PLANOG), a la fecha del dia de ayer.\r\n"
				+ "--con la finalidad de invocar nuevamente el servicio de planpogramas\r\n"
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
				+ "	WHERE code = 'SIMP_ETIQ_IMP_PLANOG')\r\n"
				+ "\"");
		sqlSimp.sqlActualizaFechaPlanogramaConEv(serverSatelites);
		//PASO 14 --------------------------------------------------------------------------------------------
		addStep("Seleccionar app Oxxo al día");
		appiumUtil.driver.terminateApp(data.get("appPackage"));
		Thread.sleep(2000);
		simp.iniciarApp(data.get("appPackage"), data.get("appActivity"));
		//PASO 15 --------------------------------------------------------------------------------------------
		addStep("Escribir usuario");
		simp.loginUsuario(data.get("userLogin"));
		//PASO 16 --------------------------------------------------------------------------------------------
		addStep("Escribir contraseña");
		simp.loginContrasena(PasswordUtil.decryptPassword(data.get("passwordLogin")));
		//PASO 17 --------------------------------------------------------------------------------------------
		addStep("Selecciona la opción ingresar");
		simp.loginIngresar();
		//PASO 18 --------------------------------------------------------------------------------------------
		addStep("Selecciona la opción Excelencia Operativa");
		simp.ExcelenciaOperativa();
		//PASO 19 --------------------------------------------------------------------------------------------
		addStep("Selecciona la opcion de Etiqueteo");
		simp.Etiqueteo();
		//PASO 20 --------------------------------------------------------------------------------------------
		addStep("Selecciona el Botón Aceptar");
		simp.aceptarColocarPapel();
		//PASO 21 --------------------------------------------------------------------------------------------
		addStep("seleccionar el planogrupo");
		simp.clickBusquedaPlanograma();
		//PASO 22 --------------------------------------------------------------------------------------------
		addStep("Se selecciona planogrupo al que se modifico por csv");
		simp.buscarYAbrirPlanogrupo(resultPaso1.getData(0, "PLANOGROUP"));
		simp.imprimirCharola(resultPaso1.getData(0, "TRAY"));
		simp.aceptarCortarEtiquetas();

		String ContenidoEtiqueta = simp.leerContenidoArchivo(simp.obtenerArchivoDeEtiquetasMasReciente("Z:\\INV\\logs"));
		simp.validarNoContenidoEtiquetasImpresas(ContenidoEtiqueta, "sku", skuPaso1);
		
		// Cerrar Apps
		Thread.sleep(3000);
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
    	}
	
		
	@Override
	public String setTestFullName() {
		return "ATC_FT_151_HigienicoMOVILIDADIntenrtarImprimirEtiquetaPorCambioDePlanogramadoAASLEP";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al pasar de un articulo planogramado a Aslep este no se muestre en el apartado de planograma para imprimir";
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
		return "\"1. Tener conectado el punto de venta.\r\n"
				+ "2.-Contar con dispositivo HH con la aplicacion de oxxo al dia.\r\n"
				+ "3. Contar con archivo CSV configurado para pasar de un articulo planogramado a ASLEP se debera tambien tener el job Star monitor ejecutandose de forma automatica\r\n"
				+ "4. Tener bandera de Etiqueteo encendida\r\n"
				+ "5. Conexión a Servidor FTP por medio de FileZilla (El CR a utilizar debe contar con buzon de bajada y subida activo)\""
				+ "6. Conectar unidad de red del punto de venta como unidad Z: para tener acceso a las etiquetas virtuales impresas\n";
	}

	@Override
	public void beforeTest() {
	}
}

