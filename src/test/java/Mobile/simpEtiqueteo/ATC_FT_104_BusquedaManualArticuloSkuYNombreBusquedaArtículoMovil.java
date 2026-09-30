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
1. En la pantalla ""Lista de Etiquetas"", al buscar artículos de forma manual ya sea por sku o nombre de producto (de forma completa o parcial), se muestren los artículos que tengan coincidencias con ese dato.
Nota: Para el caso de los artículos que existan en más de una charola, el resultado de la búsqueda muestre el número de etiquetas según sus ubicaciones, con opción a agregar solo las que se deseen.
2. Que la información mostrada (etiqueta y datos) en las Cards de las pantallas ""Búsqueda por artículo"" y ""Lista de etiquetas""  sea correcta.
3. Y que en la ""Lista de Etiquetas"", solo se enlisten, las etiquetas de los artículos seleccionados, así como las cantidades indicadas.
"
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC_FT_104_BusquedaManualArticuloSkuYNombreBusquedaArtículoMovil
 *
 * PRERREQUISITOS:
 *"1. Handheld con mínimo Android 11
2. Tener instalado aplicativo oxxo al día en Handheld.
3. Tener conectado el dispositivo móvil a una red donde mantenga comunicación con el punto de venta.
4. Establecer conexión a la BD de XPOS
5.  Establecer conexión a la BD FCPVEQA_SIV3 "
 * 
 * @author Mario Salazar
 * @date 30 Enero 2025
 */
public class ATC_FT_104_BusquedaManualArticuloSkuYNombreBusquedaArtículoMovil extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_104_BusquedaManualArticuloSkuYNombreBusquedaArtículoMovil_test(HashMap<String, String> data) throws Exception {
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
		
		String BDXposStore = data.get("serverXposStore");
		String BDSatelites = data.get("serverSatelites");
		String BDSiv3 = data.get("serverSIV3");
		String plaza = data.get("plaza");
		String tienda = data.get("tienda");
		String userSIV3 =  data.get("usernameSIV3");
		String passSIV3 = PasswordUtil.decryptPassword(data.get("passwordSIV3"));
		System.out.println("Server SIV3: "+BDSiv3+" | User: "+userSIV3 +" | Pass: "+ passSIV3);
		
		SimpEtiqueteo simpEt = new SimpEtiqueteo(testCase, appiumUtil, this );
		SQLSimpEtiqueteo sqlSimp = new SQLSimpEtiqueteo(testCase, this);
    	//PASO 1
        /*********/
		addStep("En la BD de XPOS, correr el siguiente query en tabla concentradora (construida por Dev) para:\r\n"
				+ "1. Identificar 4 artículos (activos y con inventario) asignados a planograma donde c/u de ellos pertenezca a una sola charola\r\n"
				+ "2. Identificar 1 artículo (activo y con inventario) asignado a planograma que se encuentre en más de 2 charolas");
		String Querypaso1 = "SELECT top 4 ITEM_ID, SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, TRAY, LOCATION, FRONTS \r\n"
				+ "FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "WHERE SKU in (select SKU from [Satelites].[dbo].[CO_LB_INTL_OX] group by SKU HAVING COUNT(*) =1) \r\n"
				+ "and PLANOGROUP is not null\r\n"
				+ "and INVENTORY > 0 order by sku asc";
						
		SQLResult resulquery1 = sqlSimp.sqlExecute(Querypaso1, BDSatelites, sqlSimp.DB_USER_XPOS, sqlSimp.DB_PASSWORD_XPOS);
		sqlSimp.imprimirResult(resulquery1);
		String skuITEM1 = resulquery1.getData(0, "SKU");
		String skuITEM2 = resulquery1.getData(1, "SKU");
		String skuITEM3 = resulquery1.getData(2, "SKU");
		String skuITEM4 = resulquery1.getData(3, "SKU");
		String skuPalabra1 = resulquery1.getData(1, "NAME");
		skuPalabra1 = skuPalabra1.trim();
		String skuPalabra2 = resulquery1.getData(2, "NAME");
		skuPalabra2 = skuPalabra2.trim();
		String Querypaso2 = "select top 1 SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, TRAY, LOCATION, FRONTS   \r\n"
				+ "from [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "where SKU in (select SKU from [Satelites].[dbo].[CO_LB_INTL_OX] group by SKU HAVING COUNT(*) > 1)\r\n"
				+ "order by SKU asc";
		SQLResult resultquery2 = sqlSimp.sqlExecute(Querypaso2, BDSatelites, sqlSimp.DB_USER_XPOS, sqlSimp.DB_PASSWORD_XPOS);
		sqlSimp.imprimirResult(resultquery2);
		String skuITEM5 = resultquery2.getData(0, "SKU");
    	//PASO 2
        /*********/
		addStep("En la BD FCPVEQA_SIV3, correr el siguiente query en tablas origen y corroborar que el resultado del paso anterior coincida con el resultado de esta consulta:\r\n"
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
				+ "  --and a.item ='012388002491'\r\n"
				+ "  ORDER BY l.legos_desc\r\n"
				+ "  ,        a.charola\r\n"
				+ "  ,        a.ubicacion_bandeja\r\n"
				+ "  ) x;");
		List<String> skus = new ArrayList<String>();
		skus.add(skuITEM1);
		skus.add(skuITEM2);
		skus.add(skuITEM3);
		skus.add(skuITEM4);
		skus.add(skuITEM5);
		sqlSimp.sqlPlanogramaSIV3(skus, plaza, tienda, BDSiv3, userSIV3, passSIV3);
		sqlSimp.imprimirResult(resultquery2);
		 
		//PASO 3
        /*********/
    	addStep("Ingresar a la opción \"Etiqueteo\" del aplicativo Oxxo al día (Iniciar sesión en Oxxo al día/seleccionar Excelencia Operativa/Etiqueteo)");
    	simpEt.login(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")));
    	simpEt.ExcelenciaOperativa();
    	simpEt.Etiqueteo();

    	//PASO 4
        /*********/
    	addStep("Selecciona el Botón Aceptar");
    	simpEt.aceptarColocarPapel();
    	
    	//PASO 5
        /*********/
    	addStep("Capturar manualmente el sku de un producto que solo se encuentre en una charola (1er resultado de la búsqueda del paso 1) Ejemplo: 012388003207");
    	simpEt.buscarSKU(skuITEM1);
    	
    	//PASO 6
        /*********/
    	addStep("Selecciona el Botón Agregar etiqueta +, para agregarla a la lista de etiquetas");
    	simpEt.agregarPrimerEtiquetaSKU();
		
    	//PASO 7
        /*********/
    	addStep("Selecciona el ícono de notificación \"Lista de Etiquetas\"");
    	simpEt.clickListaDeEtiquetas();
    	
    	//PASO 8
        /*********/
    	addStep("Capturar manualmente una palabra completa de una parte del nombre de un producto que solo se encuentre en una charola (2do resultado de la búsqueda del paso 1) Ejemplo: original");
    	simpEt.buscarSKU(skuPalabra1);
    	
    	//PASO 9
        /*********/
    	addStep("Del resultado de la búsqueda, identifica el artículo del 2do resultado de la búsqueda del paso 1 y Presiona el ícono [+] 2 veces, para agregar la etiqueta");
    	simpEt.agregarCantidadDeEtiquetas(2);
    	
    	//PASO 10
        /*********/
    	addStep("Selecciona el ícono de notificación \"Lista de Etiquetas\"");
    	simpEt.clickListaDeEtiquetas();
    	
    	//PASO 11
    	/*********/
    	addStep("Capturar manualmente una palabra incompleta, de una parte del nombre de un producto que solo se encuentre en una charola (3er resultado de la búsqueda del paso 1) Ejemplo: origin");
    	simpEt.buscarSKU(skuPalabra2);
    	
    	//PASO 12
        /*********/
    	addStep("Del resultado de la búsqueda, identifica el artículo del 3er resultado de la búsqueda del paso 1 y Presiona el ícono [+] 3 veces, para agregar la etiqueta");
    	simpEt.agregarCantidadDeEtiquetas(3);
    	
    	//PASO 13
        /*********/
    	addStep("Selecciona el ícono de notificación \"Lista de Etiquetas\"");
    	simpEt.clickListaDeEtiquetas();
    	
    	//PASO 14
        /*********/
    	addStep("Capturar manualmente una parte del sku (dígitos de enmedio) de otro producto que solo se encuentre en una charola (4to resultado de la búsqueda del paso 1). Ejemplo: 388003");
    	simpEt.buscarSKU(skuITEM4);
    	
    	//PASO 15
        /*********/
    	addStep("Del resultado de la búsqueda, identifica el artículo del 4to resultado de la búsqueda del paso 1 y Presiona el ícono [+] 4 veces, para agregar la etiqueta");
    	simpEt.agregarCantidadDeEtiquetas(4);
    	
    	//PASO 16
        /*********/
    	addStep("Selecciona el ícono de notificación \"Lista de Etiquetas\"");
    	simpEt.clickListaDeEtiquetas();
    	
    	//PASO 17
        /*********/
    	addStep("Capturar manualmente el sku completo de un producto que  se encuentre en más de 2 charolas (5to resultado de la búsqueda del paso 1) Ejemplo: 012388003208");
    	simpEt.buscarSKU(skuITEM5);
    	
    	//PASO 18
        /*********/
    	addStep("Presiona ícono [+] 5 veces en una de las etiquetas mostradas");
    	simpEt.agregarCantidadDeEtiquetas(5);
    	
    	//PASO 19
        /*********/
    	addStep("Presiona ícono [+] 5 veces en una de las etiquetas mostradas");
    	simpEt.agregarCantidadDeEtiquetas(5);
    	
    	//PASO 20
        /*********/
    	addStep("Selecciona el ícono de notificación \"Lista de Etiquetas\"");
    	simpEt.clickListaDeEtiquetas();
    	
    	//PASO 21
        /*********/
    	addStep("Selecciona botón Imprimir");
    	simpEt.imprimirEtiquetas();
    	String ContenidoEtiqueta2 = simpEt.leerContenidoArchivo(simpEt.obtenerArchivoDeEtiquetasMasReciente("Z:\\INV\\logs"));
		System.out.println(ContenidoEtiqueta2);
		simpEt.validarContenidoEtiquetasImpresas(ContenidoEtiqueta2, "sku", skuITEM1);
		simpEt.validarContenidoEtiquetasImpresas(ContenidoEtiqueta2, "sku", skuITEM2);
		simpEt.validarContenidoEtiquetasImpresas(ContenidoEtiqueta2, "sku", skuITEM3);
		simpEt.validarContenidoEtiquetasImpresas(ContenidoEtiqueta2, "sku", skuITEM4);
		simpEt.validarContenidoEtiquetasImpresas(ContenidoEtiqueta2, "sku", skuITEM5);
    	//PASO 22
        /*********/
    	addStep("Selecciona botón Aceptar");
    	simpEt.aceptarCortarEtiquetas();
    	
	}
	
		
	@Override
	public String setTestFullName() {
		return "ATC_FT_104_BusquedaManualArticuloSkuYNombreBusquedaArtículoMovil";
	}

	@Override
	public String setTestDescription() {
		return "\"Validar que:\r\n"
				+ "1. En la pantalla \"\"Lista de Etiquetas\"\", al buscar artículos de forma manual ya sea por sku o nombre de producto (de forma completa o parcial), se muestren los artículos que tengan coincidencias con ese dato.\r\n"
				+ "Nota: Para el caso de los artículos que existan en más de una charola, el resultado de la búsqueda muestre el número de etiquetas según sus ubicaciones, con opción a agregar solo las que se deseen.\r\n"
				+ "\r\n"
				+ "2. Que la información mostrada (etiqueta y datos) en las Cards de las pantallas \"\"Búsqueda por artículo\"\" y \"\"Lista de etiquetas\"\"  sea correcta.\r\n"
				+ "\r\n"
				+ "3. Y que en la \"\"Lista de Etiquetas\"\", solo se enlisten, las etiquetas de los artículos seleccionados, así como las cantidades indicadas.\r\n"
				+ "\"";
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
				+ "1.. Handheld con mínimo Android 11\r\n"
				+ "2. Tener instalado aplicativo oxxo al día en Handheld.\r\n"
				+ "3. Tener conectado el dispositivo móvil a una red donde mantenga comunicación con el punto de venta.\r\n"
				+ "4. Establecer conexión a la BD de XPOS\r\n"
				+ "5.  Establecer conexión a la BD FCPVEQA_SIV3 \"";
	}

	@Override
	public void beforeTest() {
	}
}

