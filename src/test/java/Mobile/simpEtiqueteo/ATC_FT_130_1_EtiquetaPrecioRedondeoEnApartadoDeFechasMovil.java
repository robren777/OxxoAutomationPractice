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
 * Se validara que al aplicar un precio de redondeo se genere una etiqueta de precio unitario y una etiqueta resumida del paquete para ser impresas desde el apartado de busqueda de fechas movil
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC-FT-130_Etiqueta_precio_redondeo_en_apartado_de_fechas_movil
 *
 * PRERREQUISITOS:
 * 1. Tener conectado el punto de venta
2. Conectarse a BD de XPOS
3. Abrir aplicación Filezilla y conectare a servidor FTP
4. Utilizar CR con buzon de entrada y salida
5. Contar con archivo ITM que cuente con TRAN_TYPE = 11
6. Contar con dispositivo movil con la APK de Simplificación Etiqueteo en tienda
 *
 * INSUMOS:
 * Contar con archivo ITM que cuente con TRAN_TYPE = 11
 * @author Mario Salazar
 * @date 22 Enero 2025
 */
public class ATC_FT_130_1_EtiquetaPrecioRedondeoEnApartadoDeFechasMovil extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_130_1_EtiquetaPrecioRedondeoEnApartadoDeFechasMovil_test (HashMap<String, String> data) throws Exception {
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
		String skuITM = data.get("skuITM");
		
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
    	
    	SimpEtiqueteo simpEt = new SimpEtiqueteo(testCase, appiumUtil, this );
		SQLSimpEtiqueteo sqlSimp = new SQLSimpEtiqueteo(testCase, this);
		String BDSatelites = data.get("serverSatelites");

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
		addStep("Selecciona la opción Excelencia Operativa");
		simpEt.ExcelenciaOperativa();
		//PASO 9
		/*********/
		addStep("Selecciona la opcion de Etiqueteo");
		simpEt.Etiqueteo();
		//PASO 10
		/*********/
		addStep("Se presiona el boton Aceptar");
		simpEt.aceptarColocarPapel();
		//PASO 11
        /*********/
		addStep("\"En BD XPOS, correr los siguientes query en tabla concentradora (construida por Dev) para buscar el artículo al que se le aplicó el precio por redondeo, por medio del ITM que se consumirá por DS50:\r\n"
				+ "SELECT ITEM_ID, SKU, NAME, AMOUNT, AMOUNT_PROMO, VALIDITY_PROMO_STT, VALIDITY_PROMO_END, UNIT_QUANTITY, INVENTORY, AMOUNT_PKG, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "WHERE SKU ='Introduce aquí el sku que deseas consultar");
		String queryPaso2 = "SELECT SKU, NAME, AMOUNT, AMOUNT_PROMO, UNIT_QUANTITY, AMOUNT_PKG, INVENTORY, STATUS_CODE, PLANOGROUP, TRAY, LOCATION, FRONTS FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "WHERE SKU =" + "'"+skuITM+"'" ; 
		SQLResult resultPaso3 =sqlSimp.sqlExecute(queryPaso2, BDSatelites, sqlSimp.DB_USER_XPOS, sqlSimp.DB_PASSWORD_XPOS);
		sqlSimp.imprimirResult(resultPaso3);
		String Q3SKU = resultPaso3.getData(0, "SKU");

		//PASO 12
        /*********/
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
		List<String>ITM = new ArrayList<String>();
		ITM.add(Q3SKU);
		sqlSimp.consultaAOrigen(ITM,BDSatelites);
		//PASO 13
		addStep("Selecciona la opcion de Fecha");
		simpEt.clickBusquedaFecha();
		//PASO 14
		addStep("Se selecciona el rango de fecha que quieres imprimir");
		simpEt.seleccionarDiaDelSistema();
		//PASO 15
		addStep("Se presiona el boton \"Revisar Etiquetas\"");
		simpEt.revisarEtiquetasFechas();
		//PASO 16
		addStep("Selecciona opción Imprimir");
	    simpEt.imprimirEtiquetas();
	    String ContenidoEtiqueta = simpEt.leerContenidoArchivo(simpEt.obtenerArchivoDeEtiquetasMasReciente("Z:\\INV\\logs"));
	    System.out.println(ContenidoEtiqueta);
	    simpEt.validarContenidoEtiquetasImpresas(ContenidoEtiqueta, "sku", skuITM);
	}
	
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
	}

	@Override
	public String setPrerequisites() {
		return    "\"1. Tener conectado el punto de venta\r\n"
				+ "2. Conectarse a BD de XPOS\r\n"
				+ "3. Abrir aplicación Filezilla y conectare a servidor FTP\r\n"
				+ "4. Utilizar CR con buzon de entrada y salida\r\n"
				+ "5. Contar con archivo ITM que cuente con TRAN_TYPE = 11\r\n"
				+ "6. Contar con dispositivo movil con la APK de Simplificación Etiqueteo en tienda\""
				+ "7. Ejecutar de forma manual los pasos 1-3";
	}
	@Override
	public String setTestDescription() {
		return	  "Se validara que al aplicar un precio de redondeo se genere una etiqueta de precio unitario y una etiqueta resumida del paquete para ser impresas desde el apartado de busqueda de fechas movil";
	}

	@Override
	public String setTestDesigner() {
		return "Mario Salazar";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_130_1_EtiquetaPrecioRedondeoEnApartadoDeFechasMovil";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
