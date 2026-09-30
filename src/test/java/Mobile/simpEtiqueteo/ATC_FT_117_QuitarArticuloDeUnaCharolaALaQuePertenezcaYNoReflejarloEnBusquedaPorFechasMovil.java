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
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;
import utils.sql.SQLResult;

/**
 * DESCRIPCIÓN: 
 * Se validara que al quitar un articulo de una charola este pertenenciendo a más de una, no aparezca la etiqueta realizando la consulta desde la seccion de busqueda por fechas 
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC_FT_117_QuitarArticuloDeUnaCharolaALaQuePertenezcaYNoReflejarloEnBusquedaPorFechasMovil
 *
 * PRERREQUISITOS:
 *"1. Tener conectado el punto de venta.
2. Contar con un articulo que tenga mas de una charola y ubicacion asignada
3. Conectarse a BD de XPOS
4. Conectar a BD ""FCPVEQA_SIV3""
5. Abrir aplicación Filezilla y conectare a servidor FTP
6. Ejecutar el script ""Actualizarfechaplanograma"" en la BD de XPOS
7. Se necesita que el Job de StarMonitor se este consumiendo en automatico
8. Contar con dispositivo movil con la APK de Simplificación Etiqueteo en tienda"
 * 
 * @author Mario A. De Leon Rdz.
 * @date 30 Enero 2025
 */
public class ATC_FT_117_QuitarArticuloDeUnaCharolaALaQuePertenezcaYNoReflejarloEnBusquedaPorFechasMovil extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_117_QuitarArticuloDeUnaCharolaALaQuePertenezcaYNoReflejarloEnBusquedaPorFechasMovil_test(HashMap<String, String> data) throws Exception {
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
		addStep("\"Correr el siguiente  query en BD de XPOS para identificar un artículo que cuente con una asignacion en el planograma, este tienen que ser articulo activo y que cuente con inventario y se encuentre en 2 charolas distintas\r\n"
				+ "\r\n"
				+ "SELECT ITEM_ID, SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "WHERE PLANOGROUP <> 'NULL' AND STATUS_CODE = 'ACTIVE_ITEM_STATUS' AND INVENTORY > '1'\r\n"
				+ "ORDER BY SKU DESC, PLANOGROUP\"");
		String queryPaso1 = "SELECT TOP 20 SKU,TRAY,LOCATION, COUNT(*) AS Repetidos FROM [Satelites].[dbo].[CO_LB_INTL_OX] \n"
				+ "WHERE LEN(SKU) >= 13 AND SKU LIKE '75%' \n"
				+ "GROUP BY SKU,TRAY, LOCATION \n"
				+ "HAVING COUNT(*) > 1 \n"
				+ "ORDER BY COUNT(*) ASC ";
		SQLResult resultConcentradoraPaso1 = sqlSimp.sqlExecute(queryPaso1, serverSatelites, sqlSimp.DB_USER_XPOS, sqlSimp.DB_PASSWORD_XPOS);
		SQLResult resultPaso1 = sqlSimp.utilizarSKUEnRMS(resultConcentradoraPaso1, serverSatelites);
		String skuPaso1 = resultPaso1.getData(0, "SKU");
		System.out.println(skuPaso1);
		//PASO 2 --------------------------------------------------------------------------------------------
		addStep("\"Ejecutar el siguiente query en BD de XPOS:\r\n"
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
				+ "--AND i.SKU='ingresar sku'\r\n"
				+ "GROUP BY V.KA_CVE_VTA, C.NAME, P.AMOUNT, I.ITEM_ID\r\n"
				+ "ORDER BY V.KA_CVE_VTA ASC\"");
		sqlSimp.consultaAOrigen(skuPaso1, serverSatelites);
		//PASO 3 --------------------------------------------------------------------------------------------
		addStep("\"En BD xpos correr el siguiente query para validar la cantidad en inventario\r\n"
				+ "SELECT * FROM Satelites.HC.FNKRDXUNITS('sku');\"");

		sqlSimp.consultaKrdxUnits(skuPaso1, serverSatelites);
		SQLResult resultSIV3Paso3 = sqlSimp.sqlPlanogramaSIV3(skuPaso1, plaza, tienda, serverSIV3, usernameSIV3, passwordSIV3);
		//PASO 4 --------------------------------------------------------------------------------------------
		addStep("Modifica los siguientes campos en archivo csv para eliminar un artículo de una charola:\r\n"
				+ "\r\n"
				+ "1. En el campo \"\"Acción\"\" del artículo al que se actualizará la ubicación, ingresar el dato \"ELIMINAR\"");
		String[][] columnasYValores = {
	             {"UPC", skuPaso1},
	             {"Lego", resultSIV3Paso3.getData(0, "LEGO")},
	             {"Charola", resultPaso1.getData(0, "TRAY")},
	             {"# Frentes", resultPaso1.getData(0, "FRONTS")},
	             {"Ubicación en la bandeja", resultPaso1.getData(0, "LOCATION")}, 
	             {"Accion", "ELIMINAR"}
	             };
		csvSimp.editarCSV2(testname, "1", columnasYValores);

		//PASO 5 --------------------------------------------------------------------------------------------
		addStep("Utilizando la aplicación Filezilla realizar carga de los archivos CVS en el servidor FTP(oxvctmpq1.femcom.net):");
		csvSimp.loadCSVtoFTP(testname, "1");
		csvSimp.cambioStatusCSV(skuPaso1,"SKU",skuPaso1,testname, "1", serverSatelites);
		//PASO 6 --------------------------------------------------------------------------------------------
		addStep("\"Ejecutar el siguiente Query en BD FCPVEQA_SIV3\r\n"
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
				+ "  ,        DECODE (a.compartidos, NULL, NULL, XXVA_ENG_PLANOGRAMA_PKG.Get_SKU_Frente (a.legos_id, a.charola, a.ubicacion_bandeja, a.item, 'aprobado')) sku_frente\r\n"
				+ "  ,        s.tienda_id tienda\r\n"
				+ "  FROM     xxva_eng_planograma_aprobado a\r\n"
				+ "  ,        xxva_eng_legos_mv l\r\n"
				+ "  ,        xxva_sem_sembrado s\r\n"
				+ "  ,        xxva_sem_sembrado_mueble m\r\n"
				+ "  WHERE    a.item     != '0'\r\n"
				+ "  AND      l.legos_id  = a.legos_id\r\n"
				+ "  AND      l.legos_id  = m.legos_id\r\n"
				+ "  AND      s.sembrado_id = m.sembrado_id\r\n"
				+ "  AND      s.plaza_id  = '10MON'\r\n"
				+ "  AND      s.tienda_id = '50HZV'\r\n"
				+ "  AND      s.status    = 3\r\n"
				+ "  ORDER BY l.legos_desc\r\n"
				+ "  ,        a.charola\r\n"
				+ "  ,        a.ubicacion_bandeja\r\n"
				+ "  ) x;\"");
		sqlSimp.sqlPlanogramaSIV3(skuPaso1, plaza, tienda, serverSIV3, usernameSIV3, passwordSIV3);
		//PASO 7 --------------------------------------------------------------------------------------------
		addStep("Seleccionar app Oxxo al día");
		simp.iniciarApp(data.get("appPackage"), data.get("appActivity"));
		//PASO 8 --------------------------------------------------------------------------------------------
		addStep("Escribir usuario");
		simp.loginUsuario(data.get("userLogin"));
		//PASO 9 --------------------------------------------------------------------------------------------
		addStep("Escribir contraseña");
		simp.loginContrasena(PasswordUtil.decryptPassword(data.get("passwordLogin")));
		//PASO 10 --------------------------------------------------------------------------------------------
		addStep("Selecciona la opción ingresar");
		simp.loginIngresar();
		//PASO 11 --------------------------------------------------------------------------------------------
		addStep("Selecciona la opción Excelencia Operativa");
		simp.ExcelenciaOperativa();
		//PASO 12 --------------------------------------------------------------------------------------------
		addStep("Selecciona la opcion de Etiqueteo");
		simp.Etiqueteo();
		//PASO 13 --------------------------------------------------------------------------------------------
		addStep("Se presiona el boton Aceptar");
		simp.aceptarColocarPapel();
		//PASO 14 --------------------------------------------------------------------------------------------
		addStep("Selecciona la opcion de Fecha");
		simp.clickBusquedaFecha();
		//PASO 15 --------------------------------------------------------------------------------------------
		addStep("\"Correr el siguiente  query en BD de XPOS para validar que la informacion fue modificada correctamente\r\n"
				+ "\r\n"
				+ "SELECT  ITEM_ID, SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FOURNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU  FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "WHERE SKU = 'SKU del articulo seleccionado'\"");
		sqlSimp.consultarSKUEnConcentradora(skuPaso1, serverSatelites);
		//PASO 16 --------------------------------------------------------------------------------------------
		addStep("Se selecciona el rango de fecha que quieres imprimir");
		simp.seleccionarDiaDelSistemaEnFechas();
		//PASO 17 --------------------------------------------------------------------------------------------
		addStep("Se presiona el boton \"Revisar Etiquetas\"");
		simp.revisarEtiquetasFechas();
		simp.imprimirEtiquetas();
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
		return "ATC_FT_117_QuitarArticuloDeUnaCharolaALaQuePertenezcaYNoReflejarloEnBusquedaPorFechasMovil";
	}

	@Override
	public String setTestDescription() {
		return "Se validara que al quitar un articulo de una charola este pertenenciendo a más de una, no aparezca la etiqueta realizando la consulta desde la seccion de busqueda por fechas";
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
				+ "2. Contar con un articulo que tenga una charola y ubicacion asignado\r\n"
				+ "3. Conectarse a BD de XPOS\r\n"
				+ "4. Conectar a BD \"\"FCPVEQA_SIV3\"\"\r\n"
				+ "5. Abrir aplicación Filezilla y conectare a servidor FTP\r\n"
				+ "6. Ejecutar el script \"\"Actualizarfechaplanograma\"\" en la BD de XPOS\r\n"
				+ "7. Se necesita que el Job de StarMonitor se este consumiendo en automatico\r\n"
				+ "8. Contar con dispositivo movil con la APK de Simplificación Etiqueteo en tienda\r\n"
				+ "9. Fecha del sistema de HH y del Punto de Venta vinculado debe ser la misma\n"
				+ "10. Conectar unidad de red del punto de venta como unidad Z: para tener acceso a las etiquetas virtuales impresas\n";
	}

	@Override
	public void beforeTest() {
	}
}

