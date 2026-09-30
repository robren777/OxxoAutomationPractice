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
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;
import utils.sql.SQLResult;
/**
 * DESCRIPCIÓN: 
 * "
Realizar impresion de  varios planogrupos donde los articulos tenga  mas de una ubicación en varias charolas verificar que en la impresion salga ordenada de izquiera a derecha de abajo hacia arriba 
y tambien verificar que las etiquetas no salgan duplicadas validar que salga en orden alfabetico"
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC_FT_139_1_HigienicoMOVILIDADImprimirVariosPlanogruposConMasDeUnaUbicacionEnVariasCharolas
 *
 * PRERREQUISITOS:
 * "1. Tener conectado el punto de venta con la bandera de etiqueteo encendida.
2.-Contar con dispositivo Tablet con la aplicacion de oxxo al dia.
3. Tener la impresora conectada y con papel para la impresión de etiquetas
4. Contar con artículos que tenga una ubicación en varias charolas de varios planogrupos. A los cuales se les agregará mas de una ubicacion separada en varias charolas de varios planogrupos.
5. Correr script ""ActualizaFechaPlanograma"" en BD de XPOS para ""resetear"" la conexión con planogramas
6. Establecer conexión a la BD FCPVEQA_SIV3
7. Establecer conexión con FTP (Nota: el consumo de archivo .csv, se realiza de manera automática por job:starMonitor) "
 * 
 * @author Mario Salazar
 * @date 30 Enero 2025
 */
public class ATC_FT_139_1_HigienicoMOVILIDADImprimirVariosPlanogruposConMasDeUnaUbicacionEnVariasCharolas extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_139_1_HigienicoMOVILIDADImprimirVariosPlanogruposConMasDeUnaUbicacionEnVariasCharolas_test (HashMap<String, String> data) throws Exception{
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
		String articuloSKU = data.get("SKU");
		String plaza = data.get("plaza");
		String tienda = data.get("tienda");
		String serverSIV3 = data.get("serverSIV3");
		String usernameSIV3 = data.get("usernameSIV3");
		String passwordSIV3 = PasswordUtil.decryptPassword(data.get("passwordSIV3"));
		
		CSVSimpEtiqueteo csvSimpEt = new CSVSimpEtiqueteo(appiumUtil,testCase, this); 	
		SimpEtiqueteo simpEt = new SimpEtiqueteo(testCase, appiumUtil, this );
		SQLSimpEtiqueteo sqlSimp = new SQLSimpEtiqueteo(testCase, this);
		String testname = setTestFullName();
    	
		//INICIO DE SCRIPT
        /***************************************************/
    	//PASO 1
        /*********/
    	addStep("En BD XPOS, correr el siguiente query en tabla concentradora (construida por Dev) para identificar un artículo activo, con inventario y que pertenezca a un planogrupo\r\n"
    			+ "\r\n"
    			+ "SELECT ITEM_ID, SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
    			+ "WHERE PLANOGROUP <> 'NULL'\r\n"
    			+ "ORDER BY PLANOGROUP, TRAY, LOCATION");
    	
    	String queryPaso1 = "SELECT  SKU, NAME, PLANOGROUP, TRAY, LOCATION, FRONTS, INVENTORY, AMOUNT FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
    			+ " WHERE PLANOGROUP is not null  and INVENTORY <> '0'\r\n"
    			+ " and SKU =  '" + articuloSKU + "'"; 
    	
		SQLResult resultPaso1 =sqlSimp.sqlExecute(queryPaso1, BDSatelites, sqlSimp.DB_USER_XPOS, sqlSimp.DB_PASSWORD_XPOS);
		sqlSimp.imprimirResult(resultPaso1);
		String Q1SKU = resultPaso1.getData(0, "SKU");
		String Q1planogrupo = resultPaso1.getData(0, "PLANOGROUP");
		String Q2planogrupo = resultPaso1.getData(3, "PLANOGROUP");
		String ubicacion = resultPaso1.getData(0,"location");
    	//PASO 2
        /*********/
    	addStep("En BD XPOS, correr el siguiente query en tablas origen (ya existentes en XPOS), para corroborar que el resultado anterior coincida con el resultado de esta consulta:\r\n"
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
    			+ "ORDER BY V.KA_CVE_VTA ASC");
    	List<String>ITM = new ArrayList<String>();
    	ITM.add(Q1SKU);
    	SQLResult resultPaso2 = sqlSimp.consultaAOrigen(ITM, BDSatelites);
		sqlSimp.imprimirResult(resultPaso2);
    	//PASO 3
        /*********/
    	addStep("En BD FCPVEQA_SIV3, correr el siguiente query en tablas origen y corroborar que el resultado del paso 6 coincida con el resultado de esta consulta:\r\n"
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
    			+ "  ) x;");
    	SQLResult resultPaso3=sqlSimp.sqlPlanogramaSIV3(Q1SKU, plaza, tienda, serverSIV3, usernameSIV3, passwordSIV3);
    	//PASO 4
        /*********/
    	addStep("Seleccionar app Oxxo al día");
    	simpEt.iniciarApp(data.get("appPackage"), data.get("appActivity"));
    	//PASO 5
        /*********/
    	addStep("Escribir usuario");
    	simpEt.loginUsuario(data.get("userLogin"));
    	//PASO 6
        /*********/
    	addStep("Escribir contraseña");
    	simpEt.loginContrasena(PasswordUtil.decryptPassword(data.get("passwordLogin")));
    	//PASO 7
        /*********/
    	addStep("Selecciona la opción ingresar");
    	simpEt.loginIngresar();
    	//PASO 8
        /*********/
    	addStep("Selecciona la opcion de Excelencia operativa");
    	simpEt.ExcelenciaOperativa();
    	//PASO 9
        /*********/
    	addStep("Selecciona la opción de Etiqueteo");
    	simpEt.Etiqueteo();
    	//PASO 10
        /*********/
    	addStep("Selecciona el Botón Aceptar");
    	simpEt.aceptarColocarPapel();
    	//PASO 11
    	/*********/
    	addStep("Seleccionar la opción Búsqueda de planograma");
    	simpEt.clickBusquedaPlanograma();
    	//PASO 12
        /*********/
    	addStep("Seleccionar el planogrupo al que pertenece el articulo al que se le van agregar mas ubicaciones");
    	simpEt.buscarYAbrirPlanogrupo(Q1planogrupo);
    	simpEt.regresarAPlanograma();
    	simpEt.buscarYAbrirPlanogrupo(Q2planogrupo);
    	//PASO 13
        /*********/
    	addStep("Cerrar la aplicación oxxo al dia>presionando boton central android>precionar boton multitarea y deslizar swipe a aplicación oxxo al dia");
    	simpEt.cerrarApp(data.get("appPackage"));
    	//PASO 14
        /*********/
    	addStep("\"Modifica los siguientes campos en archivo csv para cambiar la ubicación de un artículo en distintos planogramas con mas de una charola  FTP(oxvctmpq1.femcom.net):\r\n"
    			+ "Nota: Como el movimiento necesario se requiere realizar en un campo que conforma la llave de ese artículo, se realizarán los siguientes 2 movimientos:\r\n"
    			+ "\r\n"
    			+ "\r\n"
    			+ "1.  actualiza los 2 campos siguientes:\r\n"
    			+ "Ubicación en la bandeja: Ingresa nueva ubicación (ejemplo si tenía 1, ingresa 2)\r\n"
    			+ "Accion: Ingresa el texto AGREGAR\r\n"
    			+ "2. Copiar toda la fila del artículo anterior en una nueva fila y actualiza los 2 campos siguientes:\r\n"
    			+ "Ubicación en la bandeja: Ingresa nueva ubicación (ejemplo si tenía 1, ingresa 5)\r\n"
    			+ "Accion: Ingresa el texto AGREGAR\"");
    	String[][] columnasYValoresFila2 = {
	             {"UPC", Q1SKU},
	             {"Lego", resultPaso3.getData(0, "LEGO")},
	             {"Charola", "1"},
	             {"Ubicación en la bandeja", ubicacion + 1},
	             {"Accion", "AGREGAR"},
	         };
		String[][] columnasYValoresFila3 = {
	             {"UPC", Q1SKU},
	             {"Lego", resultPaso3.getData(0, "LEGO")},
	             {"Charola", "1"},
	             {"Ubicación en la bandeja",ubicacion + 1 },
	             {"Accion", "AGREGAR"},
	         };
		csvSimpEt.editarCSV2(testname, "1", columnasYValoresFila2);
		csvSimpEt.loadCSVtoFTP(testname, "1");
		csvSimpEt.editarCSV2(testname, "1", columnasYValoresFila3);
		csvSimpEt.loadCSVtoFTP(testname, "1");
		csvSimpEt.cambioStatusCSV(articuloSKU, "location", ubicacion , testname, Q2planogrupo, BDSatelites);
    	//PASO 15
        /*********/
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
    	sqlSimp.sqlPlanogramaSIV3(Q1SKU, plaza, tienda, serverSIV3, usernameSIV3, passwordSIV3);
    	//PASO 16
        /*********/
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
    	sqlSimp.sqlActualizaFechaPlanogramaConEv(BDSatelites);
    	//PASO 17
        /*********/
    	addStep("Seleccionar app Oxxo al día");
    	simpEt.iniciarApp(data.get("appPackage"), data.get("appActivity"));
    	//PASO 18
        /*********/
    	addStep("Escribir usuario");
    	simpEt.loginUsuario(data.get("userLogin"));
    	//PASO 19
        /*********/
    	addStep("Escribir contraseña");
    	simpEt.loginContrasena(PasswordUtil.decryptPassword(data.get("passwordLogin")));
    	//PASO 20
        /*********/
    	addStep("Selecciona la opción ingresar");
    	simpEt.loginIngresar();
    	//PASO 21
        /*********/
    	addStep("Selecciona la opción Excelencia Operativa");
    	simpEt.ExcelenciaOperativa();
    	//PASO 22
        /*********/
    	addStep("Selecciona la opcion de Etiqueteo");
    	simpEt.Etiqueteo();
    	//PASO 23
        /*********/
    	addStep("Selecciona el Botón Aceptar");
    	simpEt.aceptarColocarPapel();
    	//PASO 24
        /*********/
    	addStep("Seleccionar la opción Búsqueda de planograma");
    	simpEt.clickBusquedaPlanograma();
    	//PASO 25
        /*********/
    	addStep("En BD XPOS, correr el siguiente query en tabla concentradora (construida por Dev), para corroborar que se haya actualizado el dato de ubicación, en el artículo seleccionado\\r\\n"
    			+ "SELECT ITEM_ID, SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
    			+ "WHERE SKU ='Introduce aquí el sku que deseas consultar'");
    	sqlSimp.consultaKrdxUnits(Q1SKU, BDSatelites);
    	//PASO 26
        /*********/
    	addStep("Seleccionar los planogrupos al que pertenece el articulo modificado por csv(agregamos un planogrupo primer planogrupo)");
    	simpEt.buscarYAbrirPlanogrupo(Q1planogrupo);
    	//PASO 27
        /*********/
    	addStep("Damos click al icono de impresión de la charola al que pertenece el articulo modificado");
    	simpEt.imprimirCharola("1");
    	//PASO 28
        /*********/
    	addStep("Seleccionamos aceptar");
    	simpEt.aceptarCortarEtiquetas();
    	//PASO 29
        /*********/
    	addStep("Seleccionar los planogrupos al que pertenece el articulo modificado por csv(agregamos un planogrupo segundo planogrupo con el mismo producto)");
    	simpEt.buscarYAbrirPlanogrupo(Q2planogrupo);
    	//PASO 30'
        /*********/
    	addStep("Damos click al icono de impresión de la charola al que pertenece el articulo modificado");
    	simpEt.imprimirCharola("1");
    	//PASO 31'
        /*********/
    	addStep("Seleccionamos aceptar");
    	simpEt.aceptarCortarEtiquetas();
    	String ContenidoEtiqueta2 = simpEt.leerContenidoArchivo(simpEt.obtenerArchivoDeEtiquetasMasReciente("Z:\\INV\\logs"));
		System.out.println(ContenidoEtiqueta2);
		simpEt.validarContenidoEtiquetasImpresas(ContenidoEtiqueta2, "sku", resultPaso1.getData(3, "SKU"));
	}
		
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		return    "1. Tener conectado el punto de venta con la bandera de etiqueteo encendida.\r\n"
				+ "2.-Contar con dispositivo HH con la aplicacion de oxxo al dia.\r\n"
				+ "3. Tener la impresora conectada y con papel para la impresión de etiquetas\r\n"
				+ "4. Contar con artículos que tenga una ubicación en varias charolas de varios planogrupos. A los cuales se les agregará mas de una ubicacion separada en varias charolas de varios planogrupos.\r\n"
				+ "5. Correr script \"ActualizaFechaPlanograma\" en BD de XPOS para \"resetear\" la conexión con planogramas\r\n"
				+ "6. Establecer conexión a la BD FCPVEQA_SIV3\r\n"
				+ "7. Establecer conexión con FTP (Nota: el consumo de archivo .csv, se realiza de manera automática por job:starMonitor";
	}

	@Override
	public String setTestDescription() {
		return	  "Realizar impresion de  varios planogrupos donde los articulos tenga  mas de una ubicación en varias charolas verificar que en la impresion salga ordenada de izquiera a derecha de abajo hacia arriba y tambien verificar que las etiquetas no salgan duplicadas validar que salga en orden alfabetico\"";
	}

	@Override
	public String setTestDesigner() {
		return "Mario Salazar";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_139_1_HigienicoMOVILIDADImprimirVariosPlanogruposConMasDeUnaUbicacionEnVariasCharolas";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
