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
 *  Validar que:
 *  1. En la pantalla ""Búsqueda por artículo"", al buscar artículos de forma manual ya sea por sku o nombre de producto (de forma completa o parcial), se muestren los artículos que tengan coincidencias con ese dato.
 *  Nota: Para el caso de los artículos que existan en más de una charola, el resultado de la búsqueda muestre el número de etiquetas según sus frentes, con opción a agregar solo las que se deseen.
 *  2. Que la información mostrada (etiqueta y datos) en las Cards de las pantallas ""Búsqueda por artículo"" y ""Lista de etiquetas""  sea correcta.
 *  3. Y que en la ""Lista de Etiquetas"", solo se enlisten, las etiquetas de los artículos seleccionados, así como las cantidades indicadas.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC_FT_081_ValidarOrdenDeImpresionDeEtiquetasEnBusquedaDeArticuloMovil
 *
 * PRERREQUISITOS:
 *1. Handheld con mínimo Android 11
2. Tener instalado y configurado aplicativo oxxo al día en Handheld.
3. Tener conectado el dispositivo móvil a una red donde mantenga comunicación con el punto de venta.
4. Punto de venta con bandera encendida de Funcionalidad Etiquetas.
5. Punto de venta con módulo de Etiquetas apagado.
6. Establecer conexión a la BD de XPOS
7. Correr script "ActualizaFechaPlanograma" en BD de XPOS para "resetear" la conexión con planogramas
8. Solicitar alta de usuario y asociación a equipo de cómputo a BDSIV3 con soporte L3.
9. Establecer conexión a la BD FCPVEQA_SIV3 
 *
 * 
 * @author Mario A. De Leon Rdz.
 * @date 22 Enero 2025
 */
public class ATC_FT_081_ValidarOrdenDeImpresionDeEtiquetasEnBusquedaDeArticuloMovil extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_081_ValidarOrdenDeImpresionDeEtiquetasEnBusquedaDeArticuloMovil_test(HashMap<String, String> data) throws Exception {
		//PREREQUISITOS
		String serverXposStore = data.get("serverXposStore");
		String serverSatelites = data.get("serverSatelites");
		String serverDS50 = data.get("serverDS50");
		String serverSIV3 = data.get("serverSIV3");
		String usernameSIV3 = data.get("usernameSIV3");
		String passwordSIV3 = PasswordUtil.decryptPassword(data.get("passwordSIV3"));
		String plaza = data.get("plaza");
		String tienda = data.get("tienda");

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
		
		//PASO 1 --------------------------------------------------------------------------------------
		addStep("Ingresar a la opción \"Etiqueteo\" del aplicativo Oxxo al día (Iniciar sesión en Oxxo al día/seleccionar Excelencia Operativa/Etiqueteo)");
		//Thread.sleep(5000);
		appiumUtil.driver.activateApp(data.get("appPackage")+"/"+data.get("appActivity"));
		simp.login(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")));
		simp.validateLoginSucess();
		simp.ExcelenciaOperativa();
		simp.Etiqueteo();
		//PASO 2--------------------------------------------------------------------------------------
		addStep("Selecciona el Botón Aceptar");
		simp.aceptarColocarPapel();
		//PASO 3--------------------------------------------------------------------------------------
		addStep("\"En BD de XPOS, correr el siguiente query en tabla concentradora (construida por Dev) para:\r\n"
				+ "Identificar un artículo activo, con inventario y que pertenezca a un planogrupo que inicie con el siguiente rango de letras: N-V\r\n"
				+ "\r\n"
				+ "SELECT ITEM_ID, SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "WHERE PLANOGROUP <> 'NULL'\r\n"
				+ "ORDER BY PLANOGROUP, TRAY, LOCATION;\"");
		String queryBuscarNV = "SELECT TOP 1  SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, TRAY, LOCATION, FRONTS "
                + "FROM [Satelites].[dbo].[CO_LB_INTL_OX] "
                + "WHERE "
                + "    PLANOGROUP IS NOT NULL "
                + "    AND STATUS_CODE = 'ACTIVE_ITEM_STATUS' "
                + "    AND INVENTORY > 0 "
                + "    AND PLANOGROUP LIKE '[N-V]%'"
                + "GROUP BY  SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, TRAY, LOCATION, FRONTS "
                + "HAVING COUNT(SKU) = 1 "
                + "ORDER BY PLANOGROUP ASC ";
		SQLResult resultArticuloNV = sqlSimp.sqlExecute(queryBuscarNV, serverSatelites, sqlSimp.DB_USER_XPOS, sqlSimp.DB_PASSWORD_XPOS);
		sqlSimp.imprimirResult(resultArticuloNV);
		String skuPaso3 = resultArticuloNV.getData(0, "SKU");
		//PASO 4--------------------------------------------------------------------------------------
		addStep("\"En la BD FCPVEQA_SIV3, correr el siguiente query en tablas origen y corroborar que el resultado del paso anterior coincida con el resultado de esta consulta:\r\n"
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
				+ "  ) x;\"");
		sqlSimp.sqlPlanogramaSIV3(skuPaso3, plaza, tienda, serverSIV3, usernameSIV3, passwordSIV3);
		//PASO 5--------------------------------------------------------------------------------------
		addStep("En la app móvil, capturar manualmente el sku del resultado de la búsqueda anterior");
		simp.buscarSKU(skuPaso3);
		//PASO 6--------------------------------------------------------------------------------------
		addStep("Presiona una vez Botón [Agregar etiqueta], sobre el símbolo  [+]");
		simp.agregarPrimerEtiquetaSKU();
		//PASO 7--------------------------------------------------------------------------------------
		addStep("Seleccionar imagen de la etiqueta, para abrir información completa del artículo");
		simp.abrirPrimerEtiquetaSKU();
		//PASO 8--------------------------------------------------------------------------------------
		addStep("Seleccionar [x]");
		simp.cerrarDetallesEtiquetaSKU();
		//PASO 9--------------------------------------------------------------------------------------
		addStep("Seleccionar ícono [x] del campo \"Buscar Producto\"");
		simp.borrarBusquedaEtiquetaSKU();
		//PASO 10--------------------------------------------------------------------------------------
		addStep("\"En BD XPOS, correr el siguiente query en tabla concentradora (construida por Dev) para identificar un artículo que cumpla con lo siguiente:\r\n"
				+ "1. Que esté activo\r\n"
				+ "2. Que cuente con inventario\r\n"
				+ "3. Pertenezca a un planogrupo que inicie con una letra de la A-D\r\n"
				+ "4. Que su segunda letra se encuentre entre la letra G-Z\r\n"
				+ "\r\n"
				+ "SELECT ITEM_ID, SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "WHERE PLANOGROUP <> 'NULL'\r\n"
				+ "ORDER BY PLANOGROUP, TRAY, LOCATION;\"");
		String queryBuscarADGZ = "SELECT TOP 1  SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, TRAY, LOCATION, FRONTS "
                + "FROM [Satelites].[dbo].[CO_LB_INTL_OX] "
                + "WHERE "
                + "    PLANOGROUP IS NOT NULL "
                + "    AND STATUS_CODE = 'ACTIVE_ITEM_STATUS' "
                + "    AND INVENTORY > 0 "
                + "    AND PLANOGROUP LIKE '[A-D][G-Z]%'"
                + "GROUP BY  SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, TRAY, LOCATION, FRONTS "
                + "HAVING COUNT(SKU) = 1 "
                + "ORDER BY PLANOGROUP ASC";
		SQLResult resultArticuloADGZ = sqlSimp.sqlExecute(queryBuscarADGZ, serverSatelites, sqlSimp.DB_USER_XPOS, sqlSimp.DB_PASSWORD_XPOS);
		sqlSimp.imprimirResult(resultArticuloADGZ); //Para debug
		String skuPaso10 = resultArticuloADGZ.getData(0, "SKU");
		//PASO 11--------------------------------------------------------------------------------------
		addStep("\"En la BD FCPVEQA_SIV3, correr el siguiente query en tablas origen y corroborar que el resultado del paso anterior coincida con el resultado de esta consulta:\r\n"
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
				+ "  ) x;\"");
		SQLResult siv3Paso11 = sqlSimp.sqlPlanogramaSIV3(resultArticuloADGZ.getData(0, "SKU"), plaza, tienda, serverSIV3, usernameSIV3, passwordSIV3);
		sqlSimp.imprimirResult(siv3Paso11); //Para debug
		//PASO 12--------------------------------------------------------------------------------------
		addStep("En la app móvil, capturar manualmente el sku del resultado de la búsqueda anterior");
		simp.buscarSKU(skuPaso10);
		//PASO 13--------------------------------------------------------------------------------------
		addStep("Seleccionar Botón [Agregar etiqueta], sobre el texto \"Agregar etiqueta\"");
		simp.agregarPrimerEtiquetaSKU();
		//PASO 14--------------------------------------------------------------------------------------
		addStep("Seleccionar imagen");
		simp.abrirPrimerEtiquetaSKU();
		//PASO 15--------------------------------------------------------------------------------------
		addStep("Seleccionar [x]");
		simp.cerrarDetallesEtiquetaSKU();
		//PASO 16--------------------------------------------------------------------------------------
		addStep("Seleccionar ícono [x] del campo \"Buscar Producto\"");
		simp.borrarBusquedaEtiquetaSKU();
		//PASO 17--------------------------------------------------------------------------------------
		addStep("\"En BD XPOS, correr el siguiente query en tabla concentradora (construida por Dev) para identificar un artículo que cumpla con lo siguiente:\r\n"
				+ "1. Que esté activo\r\n"
				+ "2. Que cuente con inventario\r\n"
				+ "3. Pertenezca a un planogrupo distinto al seleccionado en el paso anterior.\r\n"
				+ "4. Que inicie con la misma letra de ese planogrupo\r\n"
				+ "5. Que su segunda letra se encuentre entre la letra A-F\r\n"
				+ "6. Que se encuentre en una charola del 2 en adelante\r\n"
				+ "\r\n"
				+ "SELECT ITEM_ID, SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "WHERE PLANOGROUP <> 'NULL'\r\n"
				+ "ORDER BY PLANOGROUP, TRAY, LOCATION;\"");
		String planogrupoConsultaADGZ = resultArticuloADGZ.getData(0, "PLANOGROUP");
		String queryBuscarAF = "SELECT TOP 1  SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, TRAY, LOCATION, FRONTS "
                + "FROM [Satelites].[dbo].[CO_LB_INTL_OX] "
                + "WHERE "
                + "    PLANOGROUP IS NOT NULL "
                + "    AND STATUS_CODE = 'ACTIVE_ITEM_STATUS' "
                + "    AND INVENTORY > 0 "
                + "    AND PLANOGROUP <> '"+planogrupoConsultaADGZ+"'"
                + "    AND PLANOGROUP LIKE LEFT('"+planogrupoConsultaADGZ+"', 1) + '[A-F]%'"
                + "    AND TRAY > 2";
		SQLResult resultArticuloAF = sqlSimp.sqlExecute(queryBuscarAF, serverSatelites, sqlSimp.DB_USER_XPOS, sqlSimp.DB_PASSWORD_XPOS);
		sqlSimp.imprimirResult(resultArticuloAF); //Para debug
		String skuPaso17 = resultArticuloAF.getData(0, "SKU");
		//PASO 18--------------------------------------------------------------------------------------
		addStep("\"En la BD FCPVEQA_SIV3, correr el siguiente query en tablas origen y corroborar que el resultado del paso anterior coincida con el resultado de esta consulta:\r\n"
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
				+ "  ) x;\"");
		SQLResult siv3Paso18 = sqlSimp.sqlPlanogramaSIV3(resultArticuloAF.getData(0, "SKU"), plaza, tienda, serverSIV3, usernameSIV3, passwordSIV3);
		sqlSimp.imprimirResult(siv3Paso18); //Para debug
		//PASO 19--------------------------------------------------------------------------------------
		addStep("En la app móvil, capturar manualmente el sku del resultado de la búsqueda anterior");
		simp.buscarSKU(skuPaso17);
		//PASO 20--------------------------------------------------------------------------------------
		addStep("Seleccionar imagen de la etiqueta, para abrir información completa del artículo");
		simp.agregarPrimerEtiquetaSKU();
		//PASO 21--------------------------------------------------------------------------------------
		addStep("Presiona una vez Botón [Agregar etiqueta], sobre el símbolo  [+]");
		simp.abrirPrimerEtiquetaSKU();
		//PASO 22--------------------------------------------------------------------------------------
		addStep("Seleccionar [x]");
		simp.cerrarDetallesEtiquetaSKU();
		//PASO 23--------------------------------------------------------------------------------------
		addStep("Seleccionar ícono [x] del campo \"Buscar Producto\"");
		simp.borrarBusquedaEtiquetaSKU();
		//PASO 24--------------------------------------------------------------------------------------
		addStep("\"En BD XPOS, correr el siguiente query en tabla concentradora (construida por Dev) para identificar un artículo que cumpla con lo siguiente:\r\n"
				+ "1. Que esté activo\r\n"
				+ "2. Que cuente con inventario\r\n"
				+ "3. Se encuentre en el mismo planogrupo seleccionado en el paso anterior pero en la charola 1\r\n"
				+ "4. Que su ubicación sea de la 2 en adelante\r\n"
				+ "\r\n"
				+ "SELECT ITEM_ID, SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "WHERE PLANOGROUP <> 'NULL'\r\n"
				+ "ORDER BY PLANOGROUP, TRAY, LOCATION;\"");
		String planogrupoConsultaAF = resultArticuloAF.getData(0, "PLANOGROUP");
		String queryBuscarPaso24 = "SELECT TOP 1  SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, TRAY, LOCATION, FRONTS "
                + "FROM [Satelites].[dbo].[CO_LB_INTL_OX] "
                + "WHERE "
                + "    PLANOGROUP IS NOT NULL "
                + "    AND STATUS_CODE = 'ACTIVE_ITEM_STATUS' "
                + "    AND INVENTORY > 0 "
                + "    AND PLANOGROUP = '"+planogrupoConsultaAF+"'"
                + "	   AND TRAY = 1"
                + "	   AND LOCATION > 2"
                + "GROUP BY  SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, TRAY, LOCATION, FRONTS "
                + "HAVING COUNT(SKU) = 1 " 
                + "ORDER BY PLANOGROUP ASC";
		SQLResult resultArticuloPaso24 = sqlSimp.sqlExecute(queryBuscarPaso24, serverSatelites, sqlSimp.DB_USER_XPOS, sqlSimp.DB_PASSWORD_XPOS);
		sqlSimp.imprimirResult(resultArticuloPaso24);
		String skuPaso24 = resultArticuloPaso24.getData(0, "SKU");
		//PASO 25--------------------------------------------------------------------------------------
		addStep("\"En la BD FCPVEQA_SIV3, correr el siguiente query en tablas origen y corroborar que el resultado del paso anterior coincida con el resultado de esta consulta:\r\n"
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
				+ "  ) x;\"");
		SQLResult siv3Paso25 = sqlSimp.sqlPlanogramaSIV3(resultArticuloPaso24.getData(0, "SKU"), plaza, tienda, serverSIV3, usernameSIV3, passwordSIV3);
		sqlSimp.imprimirResult(siv3Paso25); //Para debug
		//PASO 26--------------------------------------------------------------------------------------
		addStep("En la app móvil, capturar manualmente el sku del resultado de la búsqueda anterior");
		simp.buscarSKU(skuPaso24);
		//PASO 27--------------------------------------------------------------------------------------
		addStep("Seleccionar imagen de la etiqueta, para abrir información completa del artículo");
		simp.agregarPrimerEtiquetaSKU();
		//PASO 28--------------------------------------------------------------------------------------
		addStep("Presiona una vez el botón [Agregar etiqueta], sobre el texto \"Agregar etiqueta\"");
		simp.abrirPrimerEtiquetaSKU();
		//PASO 29--------------------------------------------------------------------------------------
		addStep("Seleccionar [x]");
		simp.cerrarDetallesEtiquetaSKU();
		//PASO 30--------------------------------------------------------------------------------------
		addStep("Seleccionar ícono [x] del campo \"Buscar Producto\"");
		simp.borrarBusquedaEtiquetaSKU();
		//PASO 31--------------------------------------------------------------------------------------
		addStep("\"En BD XPOS, correr el siguiente query en tabla concentradora (construida por Dev) para identificar un artículo que cumpla con lo siguiente:\r\n"
				+ "1. Que esté activo\r\n"
				+ "2. Que cuente con inventario\r\n"
				+ "3. Se encuentre en el mismo planogrupo seleccionado en el paso anterior.\r\n"
				+ "4. Que se encuentre en la misma charola del paso anterior\r\n"
				+ "5. Pero que su ubicación sea 1\r\n"
				+ "\r\n"
				+ "SELECT ITEM_ID, SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "WHERE PLANOGROUP <> 'NULL'\r\n"
				+ "ORDER BY PLANOGROUP, TRAY, LOCATION;\"");
		String queryBuscarPaso31 = "SELECT TOP 1  SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, TRAY, LOCATION, FRONTS "
                + "FROM [Satelites].[dbo].[CO_LB_INTL_OX] "
                + "WHERE "
                + "    PLANOGROUP IS NOT NULL "
                + "    AND STATUS_CODE = 'ACTIVE_ITEM_STATUS' "
                + "    AND INVENTORY > 0 "
                + "    AND PLANOGROUP = '"+planogrupoConsultaAF+"'"
                + "	   AND TRAY = 1"
                + "	   AND LOCATION = 1"
                + "GROUP BY  SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, TRAY, LOCATION, FRONTS "
                + "HAVING COUNT(SKU) = 1 "
                + "ORDER BY PLANOGROUP ASC";
		SQLResult resultArticuloPaso31 = sqlSimp.sqlExecute(queryBuscarPaso31, serverSatelites, sqlSimp.DB_USER_XPOS, sqlSimp.DB_PASSWORD_XPOS);
		sqlSimp.imprimirResult(resultArticuloPaso31);
		String skuPaso31 = resultArticuloPaso31.getData(0, "SKU");
		//PASO 32--------------------------------------------------------------------------------------
		addStep("\"En la BD FCPVEQA_SIV3, correr el siguiente query en tablas origen y corroborar que el resultado del paso anterior coincida con el resultado de esta consulta:\r\n"
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
				+ "  ) x;\"");
		SQLResult siv3Paso32 = sqlSimp.sqlPlanogramaSIV3(resultArticuloPaso31.getData(0, "SKU"), plaza, tienda, serverSIV3, usernameSIV3, passwordSIV3);
		sqlSimp.imprimirResult(siv3Paso32); //Para debug
		//PASO 33--------------------------------------------------------------------------------------
		addStep("En la app móvil, capturar manualmente el sku del resultado de la búsqueda anterior");
		simp.buscarSKU(skuPaso31);
		//PASO 34--------------------------------------------------------------------------------------
		addStep("Seleccionar imagen de la etiqueta, para abrir información completa del artículo");
		simp.agregarPrimerEtiquetaSKU();
		//PASO 35--------------------------------------------------------------------------------------
		addStep("Presiona una vez el botón [Agregar etiqueta], sobre el texto \"Agregar etiqueta\"");
		simp.abrirPrimerEtiquetaSKU();
		//PASO 36--------------------------------------------------------------------------------------
		addStep("Seleccionar [x]");
		simp.cerrarDetallesEtiquetaSKU();
		//PASO 37--------------------------------------------------------------------------------------
		addStep("Seleccionar ícono [x] del campo \"Buscar Producto\"");
		simp.borrarBusquedaEtiquetaSKU();
		//PASO 38--------------------------------------------------------------------------------------
		addStep("\"En BD XPOS, correr el siguiente query en tabla concentradora (construida por Dev) para identificar un artículo que cumpla con lo siguiente:\r\n"
				+ "Dar doble click sobre un artículo activo, con inventario y que pertenezca a un planogrupo que inicie con el siguiente rango de letras: E-M\r\n"
				+ "\r\n"
				+ "SELECT ITEM_ID, SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, TRAY, LOCATION, FRONTS FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "WHERE PLANOGROUP <> 'NULL'\r\n"
				+ "ORDER BY PLANOGROUP, TRAY, LOCATION;\"");
		String queryBuscarEM = "SELECT TOP 1  SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, TRAY, LOCATION, FRONTS "
                + "FROM [Satelites].[dbo].[CO_LB_INTL_OX] "
                + "WHERE "
                + "    PLANOGROUP IS NOT NULL "
                + "    AND STATUS_CODE = 'ACTIVE_ITEM_STATUS' "
                + "    AND INVENTORY > 0 "
                + "    AND PLANOGROUP LIKE '[E-M]%'"
                + "GROUP BY  SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, TRAY, LOCATION, FRONTS "
                + "HAVING COUNT(SKU) = 1 "
                + "ORDER BY PLANOGROUP ASC ";
		SQLResult resultArticuloEM = sqlSimp.sqlExecute(queryBuscarEM, serverSatelites, sqlSimp.DB_USER_XPOS, sqlSimp.DB_PASSWORD_XPOS);
		sqlSimp.imprimirResult(resultArticuloEM);
		String skuPaso38 = resultArticuloEM.getData(0, "SKU");
		//PASO 39--------------------------------------------------------------------------------------
		addStep("\"En la BD FCPVEQA_SIV3, correr el siguiente query en tablas origen y corroborar que el resultado del paso anterior coincida con el resultado de esta consulta:\r\n"
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
				+ "  ) x;\"");
		SQLResult siv3Paso39 = sqlSimp.sqlPlanogramaSIV3(resultArticuloEM.getData(0, "SKU"), plaza, tienda, serverSIV3, usernameSIV3, passwordSIV3);
		sqlSimp.imprimirResult(siv3Paso39); //Para debug
		//PASO 40--------------------------------------------------------------------------------------
		addStep("En la app móvil, capturar manualmente el sku del resultado de la búsqueda anterior");
		simp.buscarSKU(skuPaso38);
		//PASO 41--------------------------------------------------------------------------------------
		addStep("Seleccionar imagen de la etiqueta, para abrir información completa del artículo");
		simp.agregarPrimerEtiquetaSKU();
		//PASO 42--------------------------------------------------------------------------------------
		addStep("Presiona una vez el botón [Agregar etiqueta], sobre el texto \"Agregar etiqueta\"");
		simp.abrirPrimerEtiquetaSKU();
		//PASO 43--------------------------------------------------------------------------------------
		addStep("Seleccionar [x]");
		simp.cerrarDetallesEtiquetaSKU();
		//PASO 44--------------------------------------------------------------------------------------
		addStep("Seleccionar ícono [x] del campo \"Buscar Producto\"");
		simp.borrarBusquedaEtiquetaSKU();
		//PASO 45--------------------------------------------------------------------------------------
		addStep("\"En BD XPOS, correr el siguiente query en tabla concentradora (construida por Dev) para identificar un artículo ASLEP, activo y con inventario\r\n"
				+ "\r\n"
				+ "SELECT ITEM_ID, SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU FROM [Satelites].[dbo].[CO_LB_INTL_OX] "
				+ "WHERE INVENTORY>0 AND STATUS_CODE='ACTIVE_ITEM_STATUS' AND PLANOGROUP IS NULL\r\n"
				+ "ORDER BY PLANOGROUP, INVENTORY\"");
		SQLResult resultArticuloPaso45 =sqlSimp.consultaAConcentradoraTop1(true, true, false, serverSatelites);
		sqlSimp.imprimirResult(resultArticuloPaso45);
		String skuPaso45 = resultArticuloPaso45.getData(0, "SKU");
		//PASO 46--------------------------------------------------------------------------------------
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
		SQLResult resultOrigenPaso46 = sqlSimp.consultaAOrigen(resultArticuloPaso45.getData(0, "SKU"), serverSatelites);
		sqlSimp.imprimirResult(resultOrigenPaso46);
		//PASO 47--------------------------------------------------------------------------------------
		addStep("\"En la BD FCPVEQA_SIV3, correr el siguiente query en tablas origen y corroborar que el resultado del paso anterior coincida con el resultado de esta consulta:\r\n"
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
				+ "  ) x;\"");
		SQLResult siv3Paso47 = sqlSimp.sqlPlanogramaSIV3(resultArticuloPaso45.getData(0, "SKU"), plaza, tienda, serverSIV3, usernameSIV3, passwordSIV3);
		sqlSimp.imprimirResult(siv3Paso47); //Para debug
		//PASO 48--------------------------------------------------------------------------------------
		addStep("En la app móvil, capturar manualmente el sku del resultado de la búsqueda anterior");
		simp.buscarSKU(skuPaso45);
		//PASO 49--------------------------------------------------------------------------------------
		addStep("Seleccionar imagen de la etiqueta, para abrir información completa del artículo");
		simp.agregarPrimerEtiquetaSKU();
		//PASO 50--------------------------------------------------------------------------------------
		addStep("Presiona una vez el botón [Agregar etiqueta], sobre el texto \"Agregar etiqueta\"");
		simp.abrirPrimerEtiquetaSKU();
		//PASO 51--------------------------------------------------------------------------------------
		addStep("Seleccionar [x]");
		simp.cerrarDetallesEtiquetaSKU();
		//PASO 52--------------------------------------------------------------------------------------
		addStep("Selecciona el ícono de notificación \"Lista de Etiquetas\"");
		simp.clickListaDeEtiquetas();
		//PASO 53--------------------------------------------------------------------------------------
		addStep("Selecciona Botón Imprimir");
		simp.imprimirEtiquetas();
		//PASO 54--------------------------------------------------------------------------------------
		addStep("Selecciona botón Aceptar");
		simp.aceptarCortarEtiquetas();
		String ContenidoEtiqueta = simp.leerContenidoArchivo(simp.obtenerArchivoDeEtiquetasMasReciente("Z:\\INV\\logs"));
		System.out.println(ContenidoEtiqueta);
		
		/*TODO: VALIDAR QUE
		 * ETIQUETA 1 = PASO 45
		 * ETIQUETA 2 = PASO 38
		 * ETIQUETA 3 = PASO 31
		 * ETIQUETA 4 = PASO 24
		 * ETIQUETA 5 = PASO 17
		 * ETIQUETA 6 = PASO 10
		 * ETIQUETA 7 = PASO 3
		 */
		List<String> resultadosBDD = new ArrayList<String>();
		resultadosBDD.add(resultArticuloPaso45.getData(0, "SKU")); //PASO 45, ASLEP
		resultadosBDD.add(resultArticuloEM.getData(0, "SKU")); //PASO 38, PLANOGRUPO RANGO E-M
		resultadosBDD.add(resultArticuloPaso31.getData(0, "SKU")); //PASO 31
		resultadosBDD.add(resultArticuloPaso24.getData(0, "SKU")); //PASO 24
		resultadosBDD.add(resultArticuloAF.getData(0, "SKU"));	//PASO 17
		resultadosBDD.add(resultArticuloADGZ.getData(0, "SKU")); //PASO 10
		resultadosBDD.add(resultArticuloNV.getData(0, "SKU")); //PASO 3
		for (String articulo : resultadosBDD) {System.out.println(articulo);}
		simp.validarContenidoArchivoOrdenEtiquetas2(ContenidoEtiqueta, resultadosBDD);
		// Cerrar Apps
		Thread.sleep(3000);
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
    	}
	
		
	@Override
	public String setTestFullName() {
		return "ATC_FT_081_ValidarOrdenDeImpresionDeEtiquetasEnBusquedaDeArticuloMovil";
	}

	@Override
	public String setTestDescription() {
		return "Asegurar que:\r\n"
				+ "1. En la pantalla \"Búsqueda por artículo\" permita agregar etiquetas, desde la Card como en su pantalla de Detalle, con la opción \"Agregar etiqueta\" (sobre el texto y con Botón [+])\r\n"
				+ "\r\n"
				+ "2. Que la información mostrada (etiqueta y datos) en el Detalle de las pantallas \"Búsqueda por artículo\" y \"Lista de etiquetas\"  sea correcta.\r\n"
				+ "\r\n"
				+ "3. En las etiquetas impresas, se considera el siguiente orden:\r\n"
				+ "3.1Primero considerará los artículos ASLEP\r\n"
				+ "3.2Posterior se considerará por el planogrupo al que pertenecen (orden alfabético)\r\n"
				+ "3.3 Cuando haya más de un artículo en el mismo planogrupo, se considerará por la charola a la que pertenecen (orden númerico)\r\n"
				+ "3.4Cuando haya más de un artículo en el mismo planogrupo y charola, se considerará por la ubicación a la que pertenecen (orden númerico)";
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
		return "\"1. Handheld con mínimo Android 11\r\n"
				+ "2. Tener instalado y configurado aplicativo oxxo al día en Handheld.\r\n"
				+ "3. Tener conectado el dispositivo móvil a una red donde mantenga comunicación con el punto de venta.\r\n"
				+ "4. Punto de venta con bandera encendida de Funcionalidad Etiquetas.\r\n"
				+ "5. Punto de venta con módulo de Etiquetas apagado.\r\n"
				+ "6. Establecer conexión a la BD de XPOS\r\n"
				+ "7. Correr script \"\"ActualizaFechaPlanograma\"\" en BD de XPOS para \"\"resetear\"\" la conexión con planogramas\r\n"
				+ "8. Solicitar alta de usuario y asociación a equipo de cómputo a BDSIV3 con soporte L3.\r\n"
				+ "9. Establecer conexión a la BD FCPVEQA_SIV3 \"";
	}

	@Override
	public void beforeTest() {
	}
}

