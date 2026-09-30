package Mobile.simpEtiqueteo;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.simpEtiqueteo.om.SQLSimpEtiqueteo;
import Mobile.simpEtiqueteo.om.SimpEtiqueteo;

//import directos.mobile.om.AppProveedoresDirectos;

//import directos.mobile.om.OxxoalDia;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
//import om.MetodosExtra;
import utils.AppiumUtil;
import utils.password.PasswordUtil;
import utils.sql.SQLResult;
/**
 * DESCRIPCIÓN: 
 * Validar que en el módulo de Impresión de etiquetas de la app Oxxo Al Día, se muestre el artículo que se dio de alta en tienda, para que permita la impresión de su respectiva etiqueta desde la pestaña de planogramas
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC-FT-144_Higienico_MOVILIDAD_Imprimir_Etiqueta_por_alta_de_articulo_en_busqueda_por_planograma
 *
 * PRERREQUISITOS:
 * "1. Tener conectado el punto de venta.
2.-Contar con dispositivo HH con la aplicacion de oxxo al dia.
3. Contar con archivo ITM configurado para Alta de artículo TRAN TYPE =1
4.Contar con el archivo CSV (exista el job starMonitor consumiendo en forma automatica)
5. Tener bandera de Etiqueteo encendida
6. Conexión a Servidor FTP por medio de FileZilla"

 * 
 * @author Amauri Rodriguez
 * @date 22 Enero 2025
 */
public class ATC_FT_144_HigienicoMOVILIDADImprimirEtiquetaPorAltaDeArticuloEnBusquedaPorPlanograma extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_144_HigienicoMOVILIDADImprimirEtiquetaPorAltaDeArticuloEnBusquedaPorPlanograma_test(HashMap<String, String> data) throws Exception {
		//PREREQUISITOS
		String serverXposStore = data.get("serverXposStore");
		String Satelites = data.get("serverSatelites");
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
		
		String sku = data.get("sku");
		String planogrupoSIV = data.get("planogrupo");
    	
    	AppiumUtil appiumUtil = null;
    	try {
    		appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n"
					+ e.getMessage());
			e.printStackTrace();
		}
    	SimpEtiqueteo se = new SimpEtiqueteo(testCase, appiumUtil, this);
    	SQLSimpEtiqueteo sqlSimpE = new SQLSimpEtiqueteo(testCase, this);
    	sqlSimpE.encenderImpresoraVirtual(serverXposStore);
    	sqlSimpE.sqlActualizaFechaPlanogramaMovil(Satelites);
    	
    	Thread.sleep(2000);
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
		
		//PASO 6
		addStep("Correr script \"ActualizaFechaPlanograma\" en BD de XPOS para \"resetear\" la conexi�n con planogramas:\r\n"
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
				+ "");
		sqlSimpE.sqlActualizaFechaPlanograma(Satelites);	
	
		//PASO 7
		addStep("Seleccionar app Oxxo al dia");
		se.iniciarApp(data.get("appPackage"), data.get("appActivity"));
		//PASO 8
		addStep("Escribir usuario");
		se.loginUsuario(data.get("userLogin"));
		//PASO 9
		addStep("Escribir contrasena");
		se.loginContrasena(PasswordUtil.decryptPassword(data.get("passwordLogin")));
		//PASO 10
		addStep("Selecciona la opcipn ingresar");		
		se.loginIngresar();
		
		//PASO 11
		addStep("Selecciona la opci�n Excelencia Operativa");		
		se.ExcelenciaOperativa();
	
		//PASO 12
		addStep("Selecciona la opcion de Etiqueteo");		
		se.Etiqueteo();
		
		//PASO 13
		addStep("Correr el siguiente  query en BD de XPOS para validar que la informacion fue modificada correctamente\r\n"
				+ "SELECT * FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "WHERE SKU = 'SKU del articulo seleccionado'");		
		String queryPaso13 = "SELECT SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, TRAY, LOCATION, FRONTS  FROM [Satelites].[dbo].[CO_LB_INTL_OX] "
					+  "WHERE SKU = '"+ sku +"'"; 
		SQLResult resultadoPaso13 = sqlSimpE.sqlExecute(queryPaso13, Satelites, sqlSimpE.DB_USER_XPOS, sqlSimpE.DB_PASSWORD_XPOS);
		String planogrupo = resultadoPaso13.getData(0, "PLANOGROUP");
		assertEquals(planogrupoSIV, planogrupo);
		
		//PASO 14
		addStep("Selecciona el Boton Aceptar");		
		se.aceptarColocarPapel();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		//PASO 15
		addStep("Seleccionar la opci�n B�squeda de planograma");		
		se.clickBusquedaPlanograma();
		
		//PASO 16
		addStep("Seleccionar el planogrupo al que pertenece el articulo el cual se dio de alta, identificado por el circulo rojo indicando las actualizaciones y seleccionando el check ");		
		se.buscarYSeleccionarPlanogrupo(planogrupo);
		
		//PASO 17
		addStep("Damos click al icono de impresion del planogrupo imprimiendo solo los cambios mostrados");		
		se.imprimirEtiquetas();
		
		//PASO 18
		addStep("Seleccionamos aceptar");		
		se.aceptarCortarEtiquetas();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		String ContenidoEtiqueta = se.leerContenidoArchivo(se.obtenerArchivoDeEtiquetasMasReciente("Z:\\INV\\logs"));
		se.validarContenidoEtiquetasImpresas(ContenidoEtiqueta, "planogram", planogrupo);
		
    	}
		
	@Override
	public String setTestFullName() {
		return "ATC_FT_144_HigienicoMOVILIDADImprimirEtiquetaPorAltaDeArticuloEnBusquedaPorPlanograma";
	}

	@Override
	public String setTestDescription() {
		return "Validar que en el modulo de Impresion de etiquetas del Punto de venta, se muestre el articulo "
				+ "que se dio de alta en tienda, para que permita la impresion de su respectiva etiqueta desde "
				+ "la pestana de planogramas";
	}

	@Override
	public String setTestDesigner() {
		return "Amauri Rodriguez";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Realizar Pasos 1 - 5 como pre-requisitos";
	}

	@Override
	public void beforeTest() {
	}
}
