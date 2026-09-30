
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
 * - MTC-FT-080_Validar_Etiquetas_No_duplicadas_en_busqueda_de_artículo_Movil
 *
 * PRERREQUISITOS:
 *"1. Handheld con mínimo Android 11
 *	2. Tener instalado y configurado aplicativo oxxo al día en Handheld.
	3. Tener conectado el dispositivo móvil a una red donde mantenga comunicación con el punto de venta.
	4. Punto de venta con bandera encendida de Funcionalidad Etiquetas.
	5. Punto de venta con módulo de Etiquetas apagado.
	6. Establecer conexión a la BD de XPOS
	7. Correr script ""ActualizaFechaPlanograma"" en BD de XPOS para ""resetear"" la conexión con planogramas
	8. Solicitar alta de usuario y asociación a equipo de cómputo a BDSIV3 con soporte L3.
	9. Establecer conexión a la BD FCPVEQA_SIV3 "
 *
 * @author Mario Salazar
 * @date 22 Enero 2025
 */

public class ATC_FT_080_ValidarEtiquetasNoDuplicadasEnBusquedaDeArtículoMovil extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_080_ValidarEtiquetasNoDuplicadasEnBusquedaDeArtículoMovil_test (HashMap<String, String> data) throws Exception {
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
		String BDSiv3 = data.get("serverSIV3");
		String siv3User = data.get("usernameSIV3");
		String siv3Pass = PasswordUtil.decryptPassword(data.get("passwordSIV3"));
		String plaza = data.get("plaza");
		String tienda = data.get("tienda");
		
		SimpEtiqueteo simpEt = new SimpEtiqueteo(testCase, appiumUtil, this );
		SQLSimpEtiqueteo sqlSimp = new SQLSimpEtiqueteo(testCase, this);
		//INICIO DE SCRIPT
        /***************************************************/
    	
    	//PASO 1
        /*********/
		addStep("En la BD de XPOS, correr el siguiente query en tabla concentradora (construida por Dev) para:\r\n"
				+ "1. Identificar 4 artículos (activos y con inventario) asignados a planograma donde c/u de ellos pertenezca a una sola charola\r\n"
				+ "2. Identificar 1 artículo (activo y con inventario) asignado a planograma que se encuentre en más de una charola"
				+ "SELECT ITEM_ID, SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "WHERE PLANOGROUP <> 'NULL'\r\n"
				+ "ORDER BY SKU");
		
		SQLResult resultPaso1 = sqlSimp.consultaAConcentradoraSKUsConUnaUnicaCharola(BDSatelites);
		String sku1Paso1 = resultPaso1.getData(0, "sku");
		String sku2Paso1 = resultPaso1.getData(1, "sku");
		String sku3Paso1 = resultPaso1.getData(2, "sku");
		String sku4Paso1 = resultPaso1.getData(3, "sku");
		SQLResult result2Paso1 = sqlSimp.consultaAConcentradoraSKUsConMasDeUnaCharola(BDSatelites);
		String sku5Paso1 = result2Paso1.getData(0, "sku");
		
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
		List<String> skusPaso1 = new ArrayList<String>();
		skusPaso1.add(sku1Paso1);
		skusPaso1.add(sku2Paso1);
		skusPaso1.add(sku3Paso1);
		skusPaso1.add(sku4Paso1);
		skusPaso1.add(sku5Paso1);
		sqlSimp.sqlPlanogramaSIV3(skusPaso1, plaza, tienda, BDSiv3, siv3User, siv3Pass);
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
    	addStep("Capturar manualmente el sku completo de un producto que solo se encuentre en una charola (1er resultado de la búsqueda del paso 1) Ejemplo: 012388003207");
    	simpEt.buscarSKU(sku1Paso1);
    	
    	//PASO 6
        /*********/
    	addStep("Selecciona el Botón Agregar etiqueta +, para agregarla a la lista de etiquetas");
    	simpEt.agregarPrimerEtiquetaSKU();
    	
    	//PASO 7
        /*********/
    	addStep("Seleccionar ícono [x] del campo de búsqueda del producto");
    	simpEt.borrarBusquedaEtiquetaSKU();
    	//PASO 8
        /*********/
    	addStep("Capturar manualmente una parte del sku (dígitos de enmedio) de otro producto que solo se encuentre en una charola (2do resultado de la búsqueda del paso 1). Ejemplo: 388003");
    	simpEt.buscarSKU(sku2Paso1);
    	//PASO 9
        /*********/
    	addStep("Del resultado de la búsqueda, identifica el artículo del segundo resultado de la búsqueda del paso 1 y Presiona el ícono [+] 10 veces");
    	simpEt.agregarCantidadDeEtiquetas(10);
    	//PASO 10
        /*********/
    	addStep("Seleccionar ícono [x] del campo de búsqueda del producto");
    	simpEt.borrarBusquedaEtiquetaSKU();
    	//PASO 11
    	/*********/
    	addStep("Capturar manualmente una palabra completa de una parte del nombre de un producto que solo se encuentre en una charola (3er resultado de la búsqueda del paso 1) Ejemplo: original");
    	simpEt.buscarSKU(resultPaso1.getData(2, "name").trim());
    	
    	//PASO 12
        /*********/
    	addStep("Del resultado de la búsqueda, identifica el artículo del tercer resultado de la búsqueda del paso 1 y Presiona el ícono [+] 3 veces");
    	simpEt.agregarCantidadDeEtiquetas(3);
    	
    	//PASO 13
        /*********/
    	addStep("Seleccionar ícono [x] del campo de búsqueda del producto");
    	simpEt.borrarBusquedaEtiquetaSKU();
    	
    	//PASO 14
        /*********/
    	addStep("Capturar manualmente una palabra incompleta, de una parte del nombre de un producto que solo se encuentre en una charola (4to resultado de la búsqueda del paso 1) Ejemplo: origin");
    	simpEt.buscarSKU(resultPaso1.getData(3, "name").trim());
    	
    	//PASO 15
        /*********/
    	addStep("Del resultado de la búsqueda, identifica el artículo del cuarto resultado de la búsqueda del paso 1 y Presiona el ícono [+] 4 veces");
    	simpEt.agregarCantidadDeEtiquetas(4);
    	
    	//PASO 16
        /*********/
    	addStep("Seleccionar ícono [x] del campo \"Buscar Producto\"");
    	simpEt.borrarBusquedaEtiquetaSKU();
    	
    	//PASO 17
    	/*********/
    	addStep("Capturar manualmente sku de otro producto, que se encuentre en más de una charola (5to resultado de la búsqueda del paso 1)");
    	simpEt.buscarSKU(sku5Paso1);
    	
    	//PASO 18
        /*********/
    	addStep("Presiona ícono [+] 5 veces en una de las etiquetas mostradas");
    	simpEt.agregarCantidadDeEtiquetas(5);
    	
    	//PASO 19
        /*********/
    	addStep("Selecciona el ícono de notificación \"Lista de Etiquetas\"");
    	simpEt.clickListaDeEtiquetas();
    	
    	//PASO 20
        /*********/
    	addStep("Selecciona botón Imprimir");
    	simpEt.imprimirEtiquetas();
    	
    	//PASO 21
        /*********/
    	addStep("Selecciona botón Aceptar");
    	simpEt.aceptarCortarEtiquetas();
    	String ContenidoEtiqueta = simpEt.leerContenidoArchivo(simpEt.obtenerArchivoDeEtiquetasMasReciente("Z:\\INV\\logs"));
    	
		simpEt.validarContenidoArchivoNumEtiquetas(ContenidoEtiqueta, 23);
		simpEt.validarContenidoEtiquetasImpresas(ContenidoEtiqueta, "sku", sku1Paso1);
		simpEt.validarContenidoEtiquetasImpresasVariasEtiquetas(ContenidoEtiqueta, "sku", sku2Paso1, 10);
		simpEt.validarContenidoEtiquetasImpresasVariasEtiquetas(ContenidoEtiqueta, "sku", sku3Paso1, 3);
		simpEt.validarContenidoEtiquetasImpresasVariasEtiquetas(ContenidoEtiqueta, "sku", sku4Paso1, 4);
		simpEt.validarContenidoEtiquetasImpresasVariasEtiquetas(ContenidoEtiqueta, "sku", sku5Paso1, 5);
	}
	
	
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
	}

	@Override
	public String setPrerequisites() {
		return    "1. Ambientación de equipo en Reléase 2.302.0.0\n"
				+ "2. Versión Android 11\n"
				+ "3. Dispositivo HANDHELD\n"
				+ "4. Haber iniciado sesión con usuario genérico: Asesor/Líder\n"
				+ "5. Acceso a Internet\n"
				+ "6. Haber seleccionado clasificación Proveedor por primera vez en la semana\n"
				+ "7. Haber creado plantillas";
	}

	@Override
	public String setTestDescription() {
		return	  "\"Validar que:\r\n"
				+ "1. En la pantalla \"\"Búsqueda por artículo\"\", al buscar artículos de forma manual ya sea por sku o nombre de producto (de forma completa o parcial), se muestren los artículos que tengan coincidencias con ese dato.\r\n"
				+ "Nota: Para el caso de los artículos que existan en más de una charola, el resultado de la búsqueda muestre el número de etiquetas según sus frentes, con opción a agregar solo las que se deseen.\r\n"
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
	public String setTestFullName() {
		return "ATC_FT_080_ValidarEtiquetasNoDuplicadasEnBusquedaDeArtículoMovil";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
