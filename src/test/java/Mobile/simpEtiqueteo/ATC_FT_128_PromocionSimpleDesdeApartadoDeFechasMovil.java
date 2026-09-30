package Mobile.simpEtiqueteo;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
 *  Se validara que al dar de alta distintas promocion se genere un etiqueta para ser impresa al igual que al expirar la promocion, realizando la consulta desde el apartado de busqueda de fechas movil
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC_FT_128_PromocionSimpleDesdeApartadoDeFechasMovil
 *
 * PRERREQUISITOS:
 * 1. Tener conectado el punto de venta
 * 2. Conectarse a BD de XPOS
 * 3. Abrir aplicación Filezilla y conectare a servidor FTP
 * 4. Utilizar CR con buzon de entrada y salida
 * 5. Contar con 1 archivo PRM configurado con promoción simple por precio final (Variable F), con vigencia de un día
 * 6. Contar con 1 archivo PRM configurado con promoción simple por porcentaje (Variable P), con vigencia de un día.
 * 7. Ejecutar el script ""Actualizarfechaplanograma"" en la BD de XPOS
 * 8. Contar con 1 archivo PRM configurado con promoción simple por monto (Variable A), con vigencia de un día
 * 9. Contar con dispositivo movil con la APK de Simplificación Etiqueteo en tienda
 *
 *INSUMOS:
 * Contar con 1 archivo PRM configurado con promoción simple por precio final (Variable F), con vigencia de un día
 * Contar con 1 archivo PRM configurado con promoción simple por porcentaje (Variable P), con vigencia de un día.
 * Contar con 1 archivo PRM configurado con promoción simple por monto (Variable A), con vigencia de un día
 * 
 * @author Mario A. De Leon Rdz.
 * @date 22 Enero 2025
 */
public class ATC_FT_128_PromocionSimpleDesdeApartadoDeFechasMovil extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_128_PromocionSimpleDesdeApartadoDeFechasMovil_test(HashMap<String, String> data) throws Exception {
		String ip = data.get("IP");
		String serverXposStore = "jdbc:sqlserver://"+ip+":1433;databaseName=XposStore;encrypt=true;trustServerCertificate=true;loginTimeout=30";
		String serverSatelites = "jdbc:sqlserver://"+ip+":1433;databaseName=Satelites;encrypt=true;trustServerCertificate=true;loginTimeout=30";
		String serverSIV3 = data.get("serverSIV3");
		String usernameSIV3 = data.get("usernameSIV3");
		String passwordSIV3 = PasswordUtil.decryptPassword(data.get("passwordSIV3"));
		String plaza = data.get("plaza");
		String tienda = data.get("tienda");
		String skuPRMFinal = data.get("skuPRMFinal");
		String skuPRMMonto = data.get("skuPRMMonto");
		String skuPRMPorcentaje = data.get("skuPRMPorcentaje");

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

		//PASO 8---------------------------------------------------------------------------------------
		addStep("Seleccionar app Oxxo al día");
		simp.iniciarApp(data.get("appPackage"), data.get("appActivity"));
		//PASO 9---------------------------------------------------------------------------------------
		addStep("Escribir usuario");
		simp.loginUsuario(data.get("userLogin"));
		//PASO 10 --------------------------------------------------------------------------------------
		addStep("Escribir contraseña");
		simp.loginContrasena(PasswordUtil.decryptPassword(data.get("passwordLogin")));
		//PASO 11 --------------------------------------------------------------------------------------
		addStep("Selecciona la opción ingresar");
		simp.loginIngresar();
		//PASO 12 --------------------------------------------------------------------------------------
		addStep("Selecciona la opción Excelencia Operativa");
		simp.ExcelenciaOperativa();
		//PASO 13 --------------------------------------------------------------------------------------
		addStep("Selecciona la opcion de Etiqueteo");
		simp.Etiqueteo();
		//PASO 14 --------------------------------------------------------------------------------------
		addStep("Se presiona el boton Aceptar");
		simp.aceptarColocarPapel();
		//PASO 15 --------------------------------------------------------------------------------------
		addStep("Selecciona la opcion de Fecha");
		simp.clickBusquedaFecha();
		//PASO 16 --------------------------------------------------------------------------------------
		addStep("Se selecciona el rango de fecha que quieres imprimir");
		simp.seleccionarDiaDelSistema();
		//PASO 17 --------------------------------------------------------------------------------------
		addStep("Se presiona el boton \"Revisar Etiquetas\"");
		simp.revisarEtiquetasFechas();
		//PASO 18 --------------------------------------------------------------------------------------
		addStep("Se presiona el boton \"Imprimir\"");
		simp.imprimirEtiquetas();
		simp.aceptarCortarEtiquetas();
		//PASO 19 --------------------------------------------------------------------------------------
		addStep("Adelantar 1 fin de día en el punto de venta y en el dispositivo movil para que las promociones simples aplicadas venzan");
		String fecha = appiumUtil.driver.getDeviceTime("DD-MM-YYYY");
		System.out.println("Fecha original: " + fecha +"\n");
		// Formatear y adelantar un día
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String nuevaFecha = LocalDate.parse(fecha, formatter).plusDays(1).format(formatter);
		
		System.out.println("Fecha con un día más: " + nuevaFecha+"\n");
		simp.cambioFechaRemoto(ip, nuevaFecha);
		//PASO 20 --------------------------------------------------------------------------------------
		addStep("\"En BD XPOS, correr los siguientes query en tabla concentradora (construida por Dev) para buscar el artículo al que se le aplicó la promoción simple por precio final, por medio del PRM que se consumirá por DS50:\r\n"
				+ "\r\n"
				+ "SELECT ITEM_ID, SKU, NAME, AMOUNT, AMOUNT_PROMO, VALIDITY_PROMO_STT, VALIDITY_PROMO_END, UNIT_QUANTITY, INVENTORY, AMOUNT_PKG, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "WHERE SKU ='Introduce aquí el sku que deseas consultar: (ITEM del PRM)'\"");
		String queryPaso20 = "SELECT TOP 1 SKU, NAME, AMOUNT, AMOUNT_PROMO, INVENTORY, STATUS_CODE, PLANOGROUP, VALIDITY_PROMO_STT, VALIDITY_PROMO_END "
				+ "FROM [Satelites].[dbo].[CO_LB_INTL_OX] WHERE SKU = '"+skuPRMFinal+"' "; 
		SQLResult resultPaso20 =sqlSimp.sqlExecute(queryPaso20, serverSatelites, sqlSimp.DB_USER_XPOS, sqlSimp.DB_PASSWORD_XPOS);
		sqlSimp.imprimirResult(resultPaso20);
		//PASO 21 --------------------------------------------------------------------------------------
		addStep("\"En BD XPOS, correr los siguientes query en tabla concentradora (construida por Dev) para buscar el artículo al que se le aplicó la promoción simple por porcentaje, por medio del PRM que se consumirá por DS50:\r\n"
				+ "\r\n"
				+ "SELECT I ITEM_ID, SKU, NAME, AMOUNT, AMOUNT_PROMO, VALIDITY_PROMO_STT, VALIDITY_PROMO_END, UNIT_QUANTITY, INVENTORY, AMOUNT_PKG, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "WHERE SKU ='Introduce aquí el sku que deseas consultar'\"");
		String queryPaso21 = "SELECT TOP 1 SKU, NAME, AMOUNT, AMOUNT_PROMO, INVENTORY, STATUS_CODE, PLANOGROUP, VALIDITY_PROMO_STT, VALIDITY_PROMO_END "
				+ "FROM [Satelites].[dbo].[CO_LB_INTL_OX] WHERE SKU = '"+skuPRMPorcentaje+"' "; 
		SQLResult resultPaso21 =sqlSimp.sqlExecute(queryPaso21, serverSatelites, sqlSimp.DB_USER_XPOS, sqlSimp.DB_PASSWORD_XPOS);
		sqlSimp.imprimirResult(resultPaso21);
		//PASO 22 --------------------------------------------------------------------------------------
		addStep("\"En BD XPOS, correr los siguientes query en tabla concentradora (construida por Dev) para buscar el artículo al que se le aplicó la promoción simple por monto, por medio del PRM que se consumirá por DS50:\r\n"
				+ "\r\n"
				+ "SELECT  ITEM_ID, SKU, NAME, AMOUNT, AMOUNT_PROMO, VALIDITY_PROMO_STT, VALIDITY_PROMO_END, UNIT_QUANTITY, INVENTORY, AMOUNT_PKG, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "WHERE SKU ='Introduce aquí el sku que deseas consultar'\"");
		String queryPaso22 = "SELECT TOP 1 SKU, NAME, AMOUNT, AMOUNT_PROMO, INVENTORY, STATUS_CODE, PLANOGROUP, VALIDITY_PROMO_STT, VALIDITY_PROMO_END "
				+ "FROM [Satelites].[dbo].[CO_LB_INTL_OX] WHERE SKU = '"+skuPRMMonto+"' "; 
		SQLResult resultPaso22 =sqlSimp.sqlExecute(queryPaso22, serverSatelites, sqlSimp.DB_USER_XPOS, sqlSimp.DB_PASSWORD_XPOS);
		sqlSimp.imprimirResult(resultPaso22);
		//PASO 23 --------------------------------------------------------------------------------------
		addStep("\"Ejecutar el siguiente query  en BD de XPOS para consultar la informacion actual en las tablas origen de XPOS de los 3 articulos seleccionados y que se les vencieron las promociones:\r\n"
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
				+ "AND i.SKU IN ('capturar SKU de PROMO 1', 'capturar SKU de PROMO 3', 'capturar SKU de PROMO 3')\r\n"
				+ "GROUP BY V.KA_CVE_VTA, C.NAME, P.AMOUNT, I.ITEM_ID\r\n"
				+ "ORDER BY V.KA_CVE_VTA ASC;\r\n"
				+ "\r\n"
				+ "Consulta de Soporte para checar inventario:\r\n"
				+ "select * from Satelites.hc.FNKRDXUNITS(''capturar SKU de PROMO 1');\r\n"
				+ "select * from Satelites.hc.FNKRDXUNITS(''capturar SKU de PROMO 2');\r\n"
				+ "select * from Satelites.hc.FNKRDXUNITS(''capturar SKU de PROMO 3');\"");
		List<String> skusPRM = new ArrayList<String>();
		skusPRM.add(skuPRMFinal);
		skusPRM.add(skuPRMPorcentaje);
		skusPRM.add(skuPRMMonto);
		sqlSimp.sqlPlanogramaSIV3(skusPRM, plaza, tienda, serverSIV3, usernameSIV3, passwordSIV3);
		sqlSimp.consultaKrdxUnits(skuPRMFinal, serverSatelites);
		sqlSimp.consultaKrdxUnits(skuPRMPorcentaje, serverSatelites);
		sqlSimp.consultaKrdxUnits(skuPRMMonto, serverSatelites);
		//PASO 24  --------------------------------------------------------------------------------------
		addStep("Ingresar de nuevo al modulo de  \"Etiqueteo\" del dispositivo movil, cuando las promociones hayan vencido, para asegurar que las etiquetas de los artículos contengan la información correcta");
		appiumUtil.driver.terminateApp(data.get("appPackage"));
		Thread.sleep(2000);
		appiumUtil.driver.activateApp(data.get("appPackage")+"/"+data.get("appActivity"));
		simp.login(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")));
		simp.ExcelenciaOperativa();
		simp.Etiqueteo();
		simp.aceptarColocarPapel();
		//PASO 25 --------------------------------------------------------------------------------------
		addStep("Colocar en la barra de busqueda el artículo al que se le agregó la promoción simple por descuento precio final (paso 1)");
		simp.buscarSKU(skuPRMFinal);
		//PASO 26 --------------------------------------------------------------------------------------
		addStep("Presionar 1 vez el boton de más para agregar la etiqueta a ser impresa");
		simp.agregarPrimerEtiquetaSKU();
		//PASO 27 --------------------------------------------------------------------------------------
		addStep("Presiona la \"X\" para eliminar la busqueda del articulo");
		simp.borrarBusquedaEtiquetaSKU();
		//PASO 28 --------------------------------------------------------------------------------------
		addStep("Colocar en la barra de busqueda el artículo al que se le agregó la promoción simple por descuento en porcentaje (paso 2)");
		simp.buscarSKU(skuPRMPorcentaje);
		//PASO 29 --------------------------------------------------------------------------------------
		addStep("Presionar 1 vez el boton de más para agregar la etiqueta a ser impresa");
		simp.agregarPrimerEtiquetaSKU();
		//PASO 30 --------------------------------------------------------------------------------------
		addStep("Presiona la \"X\" para eliminar la busqueda del articulo");
		simp.borrarBusquedaEtiquetaSKU();
		//PASO 31 --------------------------------------------------------------------------------------
		addStep("Seleccionar el artículo al que se le agregó la promoción simple por descuento de un monto (paso 3)");
		simp.buscarSKU(skuPRMMonto);
		//PASO 32 --------------------------------------------------------------------------------------
		addStep("Presionar 1 vez el boton de más para agregar la etiqueta a ser impresa");
		simp.agregarPrimerEtiquetaSKU();
		//PASO 33 --------------------------------------------------------------------------------------
		addStep("Presiona en la parte superior \"Lista de etiquetas\" para ver las etiquetas agregadas");
		simp.clickListaDeEtiquetas();
		//PASO 34 --------------------------------------------------------------------------------------
		addStep("Selecciona opción Imprimir");
		simp.imprimirEtiquetas();
		//PASO 35 --------------------------------------------------------------------------------------
		addStep("Se presiona el boton \"Aceptar\"");
		simp.aceptarCortarEtiquetas();
		//LEER ETIQUETAS EN LA RUTA ESPECIFICADA EN UNIDAD DE RED
		String ContenidoEtiqueta = simp.leerContenidoArchivo(simp.obtenerArchivoDeEtiquetasMasReciente("Z:\\INV\\logs"));
		System.out.println(ContenidoEtiqueta);
		//VALIDAR QUE LAS ETIQUETAS CONTENGAN<savingMoney>0</savingMoney>
		simp.validarContenidoEtiquetasImpresas(ContenidoEtiqueta, "savingMoney", "0");
		//REGRESAR PDV A FECHA ORIGINAL
		simp.cambioFechaRemoto(ip, fecha);
    	}
	
		
	@Override
	public String setTestFullName() {
		return "ATC_FT_128_PromocionSimpleDesdeApartadoDeFechasMovil";
	}

	@Override
	public String setTestDescription() {
		return "Se validara que al dar de alta distintas promocion se genere un etiqueta para ser impresa al igual que al expirar la promocion, realizando la consulta desde el apartado de busqueda de fechas movil\r\n";
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
				+ "2. Conectarse a BD de XPOS\r\n"
				+ "3. Abrir aplicación Filezilla y conectare a servidor FTP\r\n"
				+ "4. Utilizar CR con buzon de entrada y salida\r\n"
				+ "5. Contar con 1 archivo PRM configurado con promoción simple por precio final (Variable F), con vigencia de un día\r\n"
				+ "6. Contar con 1 archivo PRM configurado con promoción simple por porcentaje (Variable P), con vigencia de un día.\r\n"
				+ "7. Ejecutar el script \"\"Actualizarfechaplanograma\"\" en la BD de XPOS\r\n"
				+ "8. Contar con 1 archivo PRM configurado con promoción simple por monto (Variable A), con vigencia de un día\r\n"
				+ "9. Contar con dispositivo movil con la APK de Simplificación Etiqueteo en tienda\""
				+ "10. Ejecutar de forma manual pasos 1-7 ";
	}

	@Override
	public void beforeTest() {
	}
}

