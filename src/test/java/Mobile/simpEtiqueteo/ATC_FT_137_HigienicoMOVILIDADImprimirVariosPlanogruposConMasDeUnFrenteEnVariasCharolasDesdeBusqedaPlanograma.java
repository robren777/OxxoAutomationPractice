package Mobile.simpEtiqueteo;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;

import Mobile.simpEtiqueteo.om.CSVSimpEtiqueteo;
import Mobile.simpEtiqueteo.om.SQLSimpEtiqueteo;
import Mobile.simpEtiqueteo.om.SimpEtiqueteo;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;
import utils.sql.SQLResult;

/**
 * DESCRIPCIÓN: 
 *  "
Realizar impresion de varios planogrupos donde los articulos tengan mas de un frente en varias charolas y verificar que en la impresion salga ordenada de izquiera a derecha de abajo hacia arriba
 y tambien verificar que las etiquetas no salgan duplicadas, validar que salga en orden alfabetico"
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC_FT_081_ValidarOrdenDeImpresionDeEtiquetasEnBusquedaDeArticuloMovil
 *
 * PRERREQUISITOS:
 * "1. Contar con un equipo XPOS con la bandera de etiqueteo encendida.
2.-Contar con dispositivo HH con la aplicacion de oxxo al dia.
3. Contar con artículo que tenga mas de un frente en varias charolas
4. Establecer conexión a la BD de XPOS
5. Correr script ""ActualizaFechaPlanograma"" en BD de XPOS para ""resetear"" la conexión con planogramas
6. Establecer conexión a la BD FCPVEQA_SIV3
7. Establecer conexión con FTP (Nota: el consumo de archivo .csv, se realiza de manera automática por job:starMonitor) "
 * 
 * @author Mario A. De Leon Rdz.
 * @date 30 Enero 2025
 */
public class ATC_FT_137_HigienicoMOVILIDADImprimirVariosPlanogruposConMasDeUnFrenteEnVariasCharolasDesdeBusqedaPlanograma extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_137_HigienicoMOVILIDADImprimirVariosPlanogruposConMasDeUnFrenteEnVariasCharolasDesdeBusqedaPlanograma_test(HashMap<String, String> data) throws Exception {
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
		
		
		//PASO 1 --------------------------------------------------------------------------------------
		addStep("\"Correr el siguiente query en tabla concentradora (construida por Dev) en la BD de XPOS para:\r\n"
				+ "1. Identificar articulos con mas de un frente en varias charolas  para imprimir varios planogrupos seleccionar el primer resultado\r\n"
				+ "\r\n"
				+ "SELECT top (5) SKU, NAME, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU, INVENTORY,AMOUNT FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "WHERE PLANOGROUP <> 'NULL' and fronts between 2 and 6 and INVENTORY <> '0' \r\n"
				+ "ORDER BY SKU\"");
		SQLResult resultConcentradoraPaso1 = sqlSimp.conseguirSKUSparaValidarEnRMS(true, true, true, serverSatelites, "FRONTS BETWEEN '2' and '6' ");
		List<SQLResult> resultPaso1 = sqlSimp.utilizarMultiplesSKUEnRMS(resultConcentradoraPaso1, serverSatelites, " ", 2);
		String sku1Paso1 = resultPaso1.get(0).getData(0, "SKU");
		String sku2Paso1 = resultPaso1.get(1).getData(0, "SKU");
		System.out.println("SKU 1: "+sku1Paso1+"\n");
		sqlSimp.imprimirResult(resultPaso1.get(0));
		System.out.println("SKU 2: "+sku2Paso1+"\n");
		sqlSimp.imprimirResult(resultPaso1.get(1));
		//PASO 2--------------------------------------------------------------------------------------
		addStep("\"Correr el siguiente query en tablas origen (ya existentes en la BD de XPOS), para corroborar que el resultado anterior coincida con el resultado de esta consulta\r\n"
				+ "SELECT I.ITEM_ID, V.KA_CVE_VTA SKU, P.AMOUNT PRECIO, C.NAME STATUS, SUM(KA_UDS * M.MULTIPLIER) CANTIDAD_EN_INVENTARIO\r\n"
				+ "FROM SATELITES.IO.KA_KARDX_VW V, XposStore.dbo.MOVEMENT M, XposStore.dbo.ITEM I, XposStore.dbo.CODE     C, XposStore.dbo.ITEM_BUSINESS_UNIT_LINK IBUL, XposStore.dbo.price P\r\n"
				+ "WHERE V.KA_CVE_MOV = M.CODE\r\n"
				+ "AND V.KA_CVE_VTA = I.SKU\r\n"
				+ "AND I.ITEM_STATUS_CODE_ID = C.CODE_ID\r\n"
				+ "AND IBUL.ITEM_ID = I.ITEM_ID AND IBUL.IS_ACTIVE = 1\r\n"
				+ "AND P.ITEM_BUSINESS_UNIT_LINK_ID = IBUL.ITEM_BUSINESS_UNIT_LINK_ID AND P.IS_ACTIVE = 1\r\n"
				+ "AND FROM_DATE>=(SELECT MAX(FROM_DATE) FROM XposStore.dbo.price p WHERE p.ITEM_BUSINESS_UNIT_LINK_ID=IBUL.ITEM_BUSINESS_UNIT_LINK_ID AND p.is_active = 1)\r\n"
				+ "AND i.SKU='colocar aqui el sku'\r\n"
				+ "GROUP BY V.KA_CVE_VTA, C.NAME, P.AMOUNT, I.ITEM_ID\r\n"
				+ "ORDER BY V.KA_CVE_VTA ASC\"");
		List<String> skusPaso2 = new ArrayList<>();
		skusPaso2.add(sku1Paso1);
		skusPaso2.add(sku2Paso1);
		sqlSimp.consultaAOrigen(skusPaso2, serverSatelites);
		//PASO 3--------------------------------------------------------------------------------------
		addStep("\"En BD FCPVEQA_SIV3, correr el siguiente query en tablas para consultar los frentes de varios articulos en distintas charolas en mas de un planogrupo:\r\n"
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
		SQLResult resultSIV3SKU1Paso3 = sqlSimp.sqlPlanogramaSIV3(sku1Paso1, plaza, tienda, serverSIV3, usernameSIV3, passwordSIV3);
		SQLResult resultSIV3SKU2Paso3 = sqlSimp.sqlPlanogramaSIV3(sku2Paso1, plaza, tienda, serverSIV3, usernameSIV3, passwordSIV3);
		//PASO 4--------------------------------------------------------------------------------------
		addStep("Seleccionar app Oxxo al día");
		simp.iniciarApp(data.get("appPackage"), data.get("appActivity"));
		//PASO 5--------------------------------------------------------------------------------------
		addStep("Escribir usuario");
		simp.loginUsuario(data.get("userLogin"));
		//PASO 6--------------------------------------------------------------------------------------
		addStep("Escribir contraseña");
		simp.loginContrasena(PasswordUtil.decryptPassword(data.get("passwordLogin")));
		//PASO 7--------------------------------------------------------------------------------------
		addStep("Selecciona la opción ingresar");
		simp.loginIngresar();
		//PASO 8--------------------------------------------------------------------------------------
		addStep("Selecciona la opción Excelencia Operativa");
		simp.ExcelenciaOperativa();
		//PASO 9--------------------------------------------------------------------------------------
		addStep("Selecciona la opcion de Etiqueteo");
		simp.Etiqueteo();
		//PASO 10--------------------------------------------------------------------------------------
		addStep("Selecciona el Botón Aceptar");
		simp.aceptarColocarPapel();
		//PASO 11--------------------------------------------------------------------------------------
		addStep("Seleccionar la opción Búsqueda de planograma");
		simp.clickBusquedaPlanograma();
		//PASO 12--------------------------------------------------------------------------------------
		addStep("Seleccionar el planogrupo al que pertenece el articulo al que se le van agregar frentes");
		simp.buscarYAbrirPlanogrupo(resultPaso1.get(0).getData(0, "PLANOGROUP")); //TODO
		//PASO 13--------------------------------------------------------------------------------------
		addStep("Cerrar la aplicación oxxo al dia>presionando boton central android>precionar boton multitarea y deslizar swipe a aplicación oxxo al dia");
		simp.cerrarApp(data.get("appPackage"));
		//PASO 14--------------------------------------------------------------------------------------
		addStep("\"Modifica los siguientes campos en archivo csv para agregar a los dos articulos seleccionados de distintos planogramas con un frente más en la misma charola y posterior carga el archivo en el servidor FTP(oxvctmpq1.femcom.net):\r\n"
				+ "# Frentes: Ingresa un número de frentes a agregar (ejemplo si tenía 1, ingresa 2)\r\n"
				+ "Accion: Ingresa el texto Modificar\"");
		String FrentesIncrementado1 = String.valueOf(Integer.parseInt(resultPaso1.get(0).getData(0, "FRONTS")) + 1);//Se incrementa en 1 el resultado original
		String FrentesIncrementado2 = String.valueOf(Integer.parseInt(resultPaso1.get(1).getData(0, "FRONTS")) + 1);//Se incrementa en 1 el resultado original
		
		String[][] columnasYValores1 = {
	             {"UPC", sku1Paso1},
	             {"Lego", resultSIV3SKU1Paso3.getData(0, "LEGO")},
	             {"Charola", resultPaso1.get(0).getData(0, "TRAY")},
	             {"# Frentes", FrentesIncrementado1},
	             {"Ubicación en la bandeja", resultPaso1.get(0).getData(0, "LOCATION")}, 
	             {"Accion", "MODIFICAR"}
	             };
		csvSimp.editarCSV2(testname, "1", columnasYValores1);
		csvSimp.loadCSVtoFTP(testname, "1");
		String[][] columnasYValores2 = {
	             {"UPC", sku2Paso1},
	             {"Lego", resultSIV3SKU2Paso3.getData(0, "LEGO")},
	             {"Charola", resultPaso1.get(1).getData(0, "TRAY")},
	             {"# Frentes", FrentesIncrementado2},
	             {"Ubicación en la bandeja", resultPaso1.get(1).getData(0, "LOCATION")}, 
	             {"Accion", "MODIFICAR"}
	             };
		csvSimp.editarCSV2(testname, "2", columnasYValores2);
		csvSimp.loadCSVtoFTP(testname, "2");
		System.out.println("\n"+sku2Paso1);
		System.out.println("\n"+resultSIV3SKU2Paso3.getData(0, "LEGO"));
		//PASO 15--------------------------------------------------------------------------------------
		addStep("\"En BD FCPVEQA_SIV3, correr el siguiente query en tablas origen para corroborar que se haya actualizado el dato de frentes en los artículos seleccionado:\r\n"
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
		sqlSimp.sqlPlanogramaSIV3(sku1Paso1, plaza, tienda, serverSIV3, usernameSIV3, passwordSIV3);
		sqlSimp.sqlPlanogramaSIV3(sku2Paso1, plaza, tienda, serverSIV3, usernameSIV3, passwordSIV3);
		//PASO 16--------------------------------------------------------------------------------------
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
		//PASO 17--------------------------------------------------------------------------------------
		addStep("Seleccionar app Oxxo al día");
		simp.iniciarApp(data.get("appPackage"), data.get("appActivity"));
		//PASO 18--------------------------------------------------------------------------------------
		addStep("Escribir usuario");
		simp.loginUsuario(data.get("userLogin"));
		//PASO 19--------------------------------------------------------------------------------------
		addStep("Escribir contraseña");
		simp.loginContrasena(PasswordUtil.decryptPassword(data.get("passwordLogin")));
		//PASO 20--------------------------------------------------------------------------------------
		addStep("Selecciona la opción ingresar");
		simp.loginIngresar();
		//PASO 21--------------------------------------------------------------------------------------
		addStep("Selecciona la opción Excelencia Operativa");
		simp.ExcelenciaOperativa();
		//PASO 22--------------------------------------------------------------------------------------
		addStep("Selecciona la opcion de Etiqueteo");
		simp.Etiqueteo();
		//PASO 23--------------------------------------------------------------------------------------
		addStep("Selecciona el Botón Aceptar");
		simp.aceptarColocarPapel();
		csvSimp.cambioStatusCSV(sku2Paso1, "FRONTS", FrentesIncrementado2, testname, "2", serverSatelites);
		//PASO 24--------------------------------------------------------------------------------------
		addStep("\"En BD XPOS, correr el siguiente query en tabla concentradora (construida por Dev), para corroborar que se haya actualizado el dato de frente, en el artículo seleccionado.\r\n"
				+ "\r\n"
				+ "SELECT ITEM_ID, SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "WHERE SKU ='Introduce aquí el sku que deseas consultar'\"");
		SQLResult result1Paso24 = sqlSimp.consultarSKUEnConcentradora(sku1Paso1, serverSatelites);
		Assert.assertEquals(result1Paso24.getData(0, "FRONTS"), FrentesIncrementado1);
		SQLResult result2Paso24 = sqlSimp.consultarSKUEnConcentradora(sku2Paso1, serverSatelites);
		Assert.assertEquals(result2Paso24.getData(0, "FRONTS"), FrentesIncrementado2);
		//PASO 25--------------------------------------------------------------------------------------
		addStep("Seleccionar la opción Búsqueda de planograma");
		simp.clickBusquedaPlanograma();
		//PASO 26--------------------------------------------------------------------------------------
		addStep("Seleccionar los planogrupos al que pertenece el articulo modificado por csv");
		simp.buscarYAbrirPlanogrupo(resultPaso1.get(0).getData(0, "PLANOGROUP"));
		//PASO 27--------------------------------------------------------------------------------------
		addStep("Damos click al icono de impresión de la charola al que pertenece el articulo modificado");
		simp.imprimirTodasLasCharolas();
		//PASO 28--------------------------------------------------------------------------------------
		addStep("Seleccionamos aceptar");
		simp.aceptarCortarEtiquetas();
		String ContenidoEtiqueta = simp.leerContenidoArchivo(simp.obtenerArchivoDeEtiquetasMasReciente("Z:\\INV\\logs"));
		System.out.println(ContenidoEtiqueta);
		simp.validarContenidoEtiquetasImpresas(ContenidoEtiqueta, "sku", sku1Paso1);
		// Cerrar Apps
		Thread.sleep(3000);
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
    	}
	
		
	@Override
	public String setTestFullName() {
		return "ATC_FT_137_HigienicoMOVILIDADImprimirVariosPlanogruposConMasDeUnFrenteEnVariasCharolasDesdeBusqedaPlanograma";
	}

	@Override
	public String setTestDescription() {
		return "\"\r\n"
				+ "Realizar impresion de varios planogrupos donde los articulos tengan mas de un frente en varias charolas y verificar que en la impresion salga ordenada de izquiera a derecha de abajo hacia arriba "
				+ "y tambien verificar que las etiquetas no salgan duplicadas, validar que salga en orden alfabetico\"";
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
		return "\"1. Contar con un equipo XPOS con la bandera de etiqueteo encendida.\r\n"
				+ "2.-Contar con dispositivo HH con la aplicacion de oxxo al dia.\r\n"
				+ "3. Contar con artículo que tenga mas de un frente en varias charolas\r\n"
				+ "4. Establecer conexión a la BD de XPOS\r\n"
				+ "5. Correr script \"\"ActualizaFechaPlanograma\"\" en BD de XPOS para \"\"resetear\"\" la conexión con planogramas\r\n"
				+ "6. Establecer conexión a la BD FCPVEQA_SIV3\r\n"
				+ "7. Establecer conexión con FTP (Nota: el consumo de archivo .csv, se realiza de manera automática por job:starMonitor) \"";
	}

	@Override
	public void beforeTest() {
	}
}

