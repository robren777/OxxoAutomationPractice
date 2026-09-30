package Mobile.simpEtiqueteo;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;

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
 *DESCRIPCIÓN: 
 *  Validar que al actualizar en más de una ocasión el precio de Redondeo (Multiunit) de un artículo, el último cambio sea el que se vea reflejado en el 
 *  módulo de Impresión de etiquetas de la app Oxxo Al Día, para que permita la impresión de etiqueta con los datos actualizados desde pestaña de planogramas se muestren 2 etiquetas: 1 con su precio regular y la otra con el precio total del paquete
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC-FT-148.1_Higienico_MOVILIDAD_Imprimir_Etiqueta_Precio_Redondeo_multiunit_en_busqueda_por_planograma
 *
 * PRERREQUISITOS:
 * 1. Tener conectado el punto de venta.
 * 2.-Contar con dispositivo Tablet con la aplicacion de oxxo al dia.
 * 3. Contar con archivo ITM configurado para cambio de precion de redondeo multiunit(TranType 11)
 * 4. Tener bandera de Etiqueteo encendida
 * 5. Conexión a Servidor FTP por medio de FileZilla(El CR a utilizar debe contar con buzon de bajada y subida activo)
 * 
 * @author Mario A. De Leon Rdz.
 * @date 22 Enero 2025
 */
public class ATC_FT_148_1_HigienicoMOVILIDADImprimirEtiquetaPrecioRedondeoMultiunitEnBusquedaPorPlanograma extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_148_1_HigienicoMOVILIDADImprimirEtiquetaPrecioRedondeoMultiunitEnBusquedaPorPlanograma_test(HashMap<String, String> data) throws Exception {
		//PREREQUISITOS
		String serverXposStore = data.get("serverXposStore");
		String serverSatelites = data.get("serverSatelites");
		String skuITM = data.get("skuITM");

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
		
		//PASO 4-------------------------------------------------------------------------------------------------------------------------
		addStep("Seleccionar app Oxxo al día");
		simp.iniciarApp(data.get("appPackage"), data.get("appActivity"));
		//PASO 5-------------------------------------------------------------------------------------------------------------------------
		addStep("Escribir usuario");
		simp.loginUsuario(data.get("userLogin"));
		//PASO 6-------------------------------------------------------------------------------------------------------------------------
		addStep("Escribir contraseña");
		simp.loginContrasena(PasswordUtil.decryptPassword(data.get("passwordLogin")));
		//PASO 7-------------------------------------------------------------------------------------------------------------------------
		addStep("Selecciona la opción ingresar");
		simp.loginIngresar();
		//PASO 8-------------------------------------------------------------------------------------------------------------------------
		addStep("Selecciona la opción Excelencia Operativa");
		simp.ExcelenciaOperativa();
		//PASO 9-------------------------------------------------------------------------------------------------------------------------
		addStep("Selecciona la opcion de Etiqueteo");
		simp.Etiqueteo();
		//PASO 10-------------------------------------------------------------------------------------------------------------------------
		addStep("Se presiona el boton Aceptar");
		simp.aceptarColocarPapel();
		//PASO 11-------------------------------------------------------------------------------------------------------------------------
		addStep("\"En BD XPOS, correr los siguientes query en tabla concentradora (construida por Dev) para buscar el artículo al que se le aplicó el precio por redondeo, por medio del ITM que se consumirá por DS50:\r\n"
				+ "\r\n"
				+ "SELECT ITEM_ID, SKU, NAME, AMOUNT, AMOUNT_PROMO, VALIDITY_PROMO_STT, VALIDITY_PROMO_END, UNIT_QUANTITY, INVENTORY, AMOUNT_PKG, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "WHERE SKU ='Introduce aquí el sku que deseas consultar'\"");
		String queryPaso11 = "SELECT TOP 1 SKU, NAME, AMOUNT, AMOUNT_PROMO, INVENTORY, STATUS_CODE, PLANOGROUP, VALIDITY_PROMO_STT, VALIDITY_PROMO_END, UNIT_QUANTITY, AMOUNT_PKG "
				+ "FROM [Satelites].[dbo].[CO_LB_INTL_OX] WHERE SKU = '"+skuITM+"' AND UNIT_QUANTITY <> 0 "; 
		SQLResult resultPaso11 =sqlSimp.sqlExecute(queryPaso11, serverSatelites, sqlSimp.DB_USER_XPOS, sqlSimp.DB_PASSWORD_XPOS);
		sqlSimp.imprimirResult(resultPaso11);
		
		//PASO 12-------------------------------------------------------------------------------------------------------------------------
		addStep("\"Ejecutar el siguiente query  en BD de XPOS para consultar la informacion actual en las tablas origen de XPOS con el articulos seleccionado al que se le aplicó el cambio de precio por redondeo:\r\n"
				+ "\r\n"
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
				+ "AND i.SKU IN ('capturar SKU de PROMO 1')\r\n"
				+ "GROUP BY V.KA_CVE_VTA, C.NAME, P.AMOUNT, I.ITEM_ID\r\n"
				+ "ORDER BY V.KA_CVE_VTA ASC;\r\n"
				+ "\r\n"
				+ "Consulta de Soporte para checar inventario:\r\n"
				+ "select * from Satelites.hc.FNKRDXUNITS(''capturar SKU de PROMO 1');\"");
		sqlSimp.consultaAOrigen(skuITM, serverSatelites);
		sqlSimp.consultaKrdxUnits(skuITM, serverSatelites);
		//PASO 13-------------------------------------------------------------------------------------------------------------------------
		addStep("Selecciona la opcion de Planograma");
		simp.clickBusquedaPlanograma();
		//PASO 14-------------------------------------------------------------------------------------------------------------------------
		addStep("Se selecciona el planograma que queramos imprimir");
		simp.buscarYSeleccionarPlanogrupo(resultPaso11.getData(0, "PLANOGROUP"));
		simp.imprimirEtiquetas();
		//PASO 15-------------------------------------------------------------------------------------------------------------------------
		addStep("Se presiona el boton de imprimir");
		String ContenidoEtiqueta = simp.leerContenidoArchivo(simp.obtenerArchivoDeEtiquetasMasReciente("Z:\\INV\\logs"));
		simp.validarContenidoEtiquetasImpresas(ContenidoEtiqueta, "UnitQuantity", resultPaso11.getData(0, "UNIT_QUANTITY"));
		// Cerrar Apps
		Thread.sleep(5000);
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
    	}
	
		
	@Override
	public String setTestFullName() {
		return "ATC_FT_148_1_HigienicoMOVILIDADImprimirEtiquetaPrecioRedondeoMultiunitEnBusquedaPorPlanograma";
	}

	@Override
	public String setTestDescription() {
		return " Validar que al actualizar en más de una ocasión el precio de Redondeo (Multiunit) de un artículo, el último cambio sea el que se vea reflejado en el módulo de Impresión de etiquetas del Punto de venta, para que permita la impresión de etiqueta con los datos actualizados desde pestaña de planogramas se muestren 2 etiquetas: 1 con su precio regular y la otra con el precio total del paquete";
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
		return "1. Tener conectado el punto de venta.\r\n"
				+ "2. Contar con dispositivo Tablet con la aplicacion de oxxo al dia.\r\n"
				+ "3. Contar con archivo ITM configurado para cambio de precion de redondeo multiunit(TranType 11)\r\n"
				+ "4. Tener bandera de Etiqueteo encendida\r\n"
				+ "5. Conexión a Servidor FTP por medio de FileZilla(El CR a utilizar debe contar con buzon de bajada y subida activo)\r\n"
				+ "6. Tener una unidad de red asignada a 'Z:' conectada al punto de venta en el dispositivo que se ejecutará el caso"
				+ "7. Ejecutar de forma manual pasos 1-3";
	}

	@Override
	public void beforeTest() {
	}
}

