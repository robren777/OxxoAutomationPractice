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

/**
 * DESCRIPCIÓN: 
 *  Asegurar que al realizar la impresión desde la selección "Fechas" dese el dispositivo movil, se impriman las etiquetas sin que se encuentren duplicadas y que estas esten en orden correcto asi como tambien las etiquetas que sufrieron cambios de descripcion ya sea corta o larga no sean impresas
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC_FT_112_ValidarEtiquetasDuplicadasOrdenDeImpresionYCambioDeDescripcionEnBusquedaPorFechasMovil
 *
 * PRERREQUISITOS:
 * "1. Tener conectado el punto de venta
2. Tener la impresora conectada y con papel para la impresión de etiquetas
3. Tener bandera de Etiqueteo encendida
4. Conectarse a BD de XPOS
5 Abrir aplicación Filezilla y conectarse
6. Utilizar CR con buzon de entrada y salida
7. Contar con 1 archivo ITM con TRAN_TYPE = 11  pra cmabio de precio, 10 para cambio de descirpcion corta y 12 para cambio de descripcion larga
8. Contar con dispositivo movil con la APK de Simplificación Etiqueteo en tienda"
 * 
 * @author Mario A. De Leon Rdz.
 * @date 30 Enero 2025
 */
public class ATC_FT_112_ValidarEtiquetasDuplicadasOrdenDeImpresionYCambioDeDescripcionEnBusquedaPorFechasMovil extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_112_ValidarEtiquetasDuplicadasOrdenDeImpresionYCambioDeDescripcionEnBusquedaPorFechasMovil_test(HashMap<String, String> data) throws Exception {
		String ip = data.get("IP");
		String serverXposStore = "jdbc:sqlserver://"+ip+":1433;databaseName=XposStore;encrypt=true;trustServerCertificate=true;loginTimeout=30";
		String serverSatelites = "jdbc:sqlserver://"+ip+":1433;databaseName=Satelites;encrypt=true;trustServerCertificate=true;loginTimeout=30";
		
		String sku1 = data.get("sku1");
		String sku2 = data.get("sku2");
		String sku3 = data.get("sku3");
		String sku4 = data.get("sku4");
		String sku5 = data.get("sku5");
		String sku6 = data.get("sku6");
		String sku7 = data.get("sku7");
		String sku8 = data.get("sku8");
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
    	sqlSimp.sqlActualizaFechaPlanograma(serverSatelites);
    	
    	Thread.sleep(2000);
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));

		//PASO 16 -----------------------------------------------------------------------------------------------------------------------------
		addStep("Seleccionar app Oxxo al día");
		simp.iniciarApp(data.get("appPackage"), data.get("appActivity"));
		//PASO 17 -----------------------------------------------------------------------------------------------------------------------------
		addStep("Escribir usuario");
		simp.loginUsuario(data.get("userLogin"));
		//PASO 18 -----------------------------------------------------------------------------------------------------------------------------
		addStep("Escribir contraseña");
		simp.loginContrasena(PasswordUtil.decryptPassword(data.get("passwordLogin")));
		//PASO 19 -----------------------------------------------------------------------------------------------------------------------------
		addStep("Selecciona la opción ingresar");
		simp.loginIngresar();
		//PASO 20 -----------------------------------------------------------------------------------------------------------------------------
		addStep("Selecciona la opción Excelencia Operativa");
		simp.ExcelenciaOperativa();
		//PASO 21 -----------------------------------------------------------------------------------------------------------------------------
		addStep("Selecciona la opcion de Etiqueteo");
		simp.Etiqueteo();
		//PASO 22 -----------------------------------------------------------------------------------------------------------------------------
		addStep("Se presiona el boton Aceptar");
		simp.aceptarColocarPapel();
		//PASO 23 -----------------------------------------------------------------------------------------------------------------------------
		addStep("Selecciona la opcion de Fecha");
		simp.clickBusquedaFecha();
		//PASO 24 -----------------------------------------------------------------------------------------------------------------------------
		addStep("\"Correr el siguiente  query en BD de XPOS para validar que la informacion fue modificada correctamente\r\n"
				+ "\r\n"
				+ "SELECT ITEM_ID, SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FOURNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "WHERE SKU IN ('capturar SKU 1', 'capturar SKU 2', 'capturar SKU 3', 'capturar SKU 4', 'capturar SKU 5', 'capturar SKU 6, 'capturar SKU 7, 'capturar SKU 8')\"");
		List<String> skus = new ArrayList<String>();
		skus.add(sku1);
		skus.add(sku2);
		skus.add(sku3);
		skus.add(sku4);
		skus.add(sku5);
		skus.add(sku6);
		skus.add(sku7);
		skus.add(sku8);
		sqlSimp.consultarSKUEnConcentradora(skus, serverSatelites);
		//PASO 25 -----------------------------------------------------------------------------------------------------------------------------
		addStep("\"Correr el siguiente query en tablas origen (ya existentes en XPOS), para corroborar que el resultado anterior coincida con el resultado de esta consulta:\r\n"
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
				+ "AND i.SKU IN ('capturar SKU 1', 'capturar SKU 2', 'capturar SKU 3', 'capturar SKU 4', 'capturar SKU 5', 'capturar SKU 6', 'capturar SKU 7', 'capturar SKU 8')\r\n"
				+ "GROUP BY V.KA_CVE_VTA, C.NAME, P.AMOUNT, I.ITEM_ID\r\n"
				+ "ORDER BY V.KA_CVE_VTA ASC;\r\n"
				+ "\r\n"
				+ "Consulta de Soporte para checar inventario:\r\n"
				+ "select * from Satelites.hc.FNKRDXUNITS(''capturar SKU 1');\r\n"
				+ "select * from Satelites.hc.FNKRDXUNITS(''capturar SKU 2');\r\n"
				+ "select * from Satelites.hc.FNKRDXUNITS(''capturar SKU 3');\r\n"
				+ "select * from Satelites.hc.FNKRDXUNITS(''capturar SKU 4');\r\n"
				+ "select * from Satelites.hc.FNKRDXUNITS(''capturar SKU 5');\r\n"
				+ "select * from Satelites.hc.FNKRDXUNITS(''capturar SKU 6');\r\n"
				+ "select * from Satelites.hc.FNKRDXUNITS(''capturar SKU 7');\r\n"
				+ "select * from Satelites.hc.FNKRDXUNITS(''capturar SKU 8');\"");

		sqlSimp.consultaAOrigen(skus, serverSatelites);
		sqlSimp.consultaKrdxUnits(sku1, serverSatelites);
		sqlSimp.consultaKrdxUnits(sku2, serverSatelites);
		sqlSimp.consultaKrdxUnits(sku3, serverSatelites);
		sqlSimp.consultaKrdxUnits(sku4, serverSatelites);
		sqlSimp.consultaKrdxUnits(sku5, serverSatelites);
		sqlSimp.consultaKrdxUnits(sku6, serverSatelites);
		sqlSimp.consultaKrdxUnits(sku7, serverSatelites);
		sqlSimp.consultaKrdxUnits(sku8, serverSatelites);
		//PASO 26 -----------------------------------------------------------------------------------------------------------------------------
		addStep("Se selecciona el rango de fecha que quieres imprimir");
		simp.seleccionarDiaDelSistemaEnFechas();
		//PASO 27 -----------------------------------------------------------------------------------------------------------------------------
		addStep("Se presiona el boton \"Revisar Etiquetas\"");
		simp.revisarEtiquetasFechas();
		//PASO 28 -----------------------------------------------------------------------------------------------------------------------------
		addStep("Se presiona el boton Imprimir");
		simp.imprimirEtiquetas();
		//PASO 29 -----------------------------------------------------------------------------------------------------------------------------
		addStep("Se presiona el boton Aceptar");
		simp.aceptarCortarEtiquetas();
		String ContenidoEtiqueta = simp.leerContenidoArchivo(simp.obtenerArchivoDeEtiquetasMasReciente("Z:\\INV\\logs"));
		simp.validarNoContenidoEtiquetasImpresas(ContenidoEtiqueta, "sku", sku7);
		simp.validarNoContenidoEtiquetasImpresas(ContenidoEtiqueta, "sku", sku8);
    	}
	
		
	@Override
	public String setTestFullName() {
		return "ATC_FT_112_ValidarEtiquetasDuplicadasOrdenDeImpresionYCambioDeDescripcionEnBusquedaPorFechasMovil";
	}

	@Override
	public String setTestDescription() {
		return "Asegurar que al realizar la impresión desde la selección \"Fechas\" dese el dispositivo movil, se impriman las etiquetas "
				+ "sin que se encuentren duplicadas y que estas esten en orden correcto asi como tambien las etiquetas que sufrieron "
				+ "cambios de descripcion ya sea corta o larga no sean impresas";
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
		return "\"1. Tener conectado el punto de venta\r\n"
				+ "2. Tener la impresora conectada y con papel para la impresión de etiquetas\r\n"
				+ "3. Tener bandera de Etiqueteo encendida\r\n"
				+ "4. Conectarse a BD de XPOS\r\n"
				+ "5 Abrir aplicación Filezilla y conectarse\r\n"
				+ "6. Utilizar CR con buzon de entrada y salida\r\n"
				+ "7. Contar con 1 archivo ITM con TRAN_TYPE = 11  pra cmabio de precio, 10 para cambio de descirpcion corta y 12 para cambio de descripcion larga\r\n"
				+ "8. Contar con dispositivo movil con la APK de Simplificación Etiqueteo en tienda\""
				+ "9. Ejecutar de forma manual pasos 1-15 ";
	}

	@Override
	public void beforeTest() {
	}
}

