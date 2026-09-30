package Mobile.simpEtiqueteo;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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
 *  Validar que en el módulo de Impresión de etiquetas del Punto de venta,no se muestre el artículo que se le cambio la descripcion larga,desde la pestaña de planogramas
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC_FT_146_HigienicoMOVILIDADIntentarImprimirEtiquetaPorCambioDescripcionLargaEnBusquedaPorPlanograma
 *
 * PRERREQUISITOS:
 * "1. Tener conectado el punto de venta.
2.-Contar con dispositivo HH con la aplicacion de oxxo al dia.
3. Contar un archivo ITM configurado para Cambio de Descripción larga (TranType = 12)
4. Tener bandera de Etiqueteo encendida
5. Conexión a Servidor FTP por medio de FileZilla(El CR a utilizar debe contar con buzon de bajada y subida activo)"
 * 
 * @author Amauri Rodriguez
 * @date 30 Enero 2025
 */
public class ATC_FT_146_HigienicoMOVILIDADIntentarImprimirEtiquetaPorCambioDescripcionLargaEnBusquedaPorPlanograma extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_146_HigienicoMOVILIDADIntentarImprimirEtiquetaPorCambioDescripcionLargaEnBusquedaPorPlanograma_test(HashMap<String, String> data) throws Exception {
		
				
		/******************************* Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script  ********************/
		DesiredCapabilities cap = new DesiredCapabilities();
		
		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appium:appPackage", data.get("appPackage"));
		cap.setCapability("appium:appActivity", data.get("appActivity"));
		cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto
		
		String user = data.get("userLogin");
		String pass = PasswordUtil.decryptPassword(data.get("passwordLogin"));
		String XposStore = data.get("serverXposStore");
		String Satelites = data.get("serverSatelites");
		String sku = data.get("sku");
		String descripcion = data.get("descripcion");
		String amount = data.get("precio");

		String url = "http://127.0.0.1:4723/";
		
    	AppiumUtil appiumUtil = null;
    	try {
    		appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}
    	SQLSimpEtiqueteo sqlSimpE = new SQLSimpEtiqueteo(testCase, null);
		SimpEtiqueteo se = new SimpEtiqueteo(testCase, appiumUtil, this);
    	Thread.sleep(2000);
		
		// Cerrar Apps
		Thread.sleep(5000);
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		
		sqlSimpE.encenderImpresoraVirtual(XposStore);
		
		appiumUtil.driver.activateApp(data.get("appPackage")+"/"+data.get("appActivity"));
		Thread.sleep(5000);

		//PASO 04
		addStep("Seleccionar app Oxxo al dia");
		se.iniciarApp(data.get("appPackage"), data.get("appActivity"));
		//PASO 05
		addStep("Escribir usuario");
		se.loginUsuario(user);
		//PASO 06
		addStep("Escribir contrasena");
		se.loginContrasena(pass);
		//PASO 07
		addStep("Selecciona la opcion ingresar");	
		se.loginIngresar();
		Thread.sleep(5000);
		
		//PASO 08
		addStep("Selecciona la opcion Excelencia Operativa");		
		se.ExcelenciaOperativa();
	
		//PASO 09
		addStep("Selecciona la opcion de Etiqueteo");		
		se.Etiqueteo();

		//PASO 10
		addStep("Selecciona el Boton Aceptar");		
		se.aceptarColocarPapel();
		
		//PASO 11
		addStep("Seleccionar la opcion de planogrupo");		
		se.clickBusquedaPlanograma();
		
		//PASO 12
		addStep("Seleccionar el planogrupo al que pertenece el ITM");		
		String queryPaso12 = "SELECT PLANOGROUP FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "WHERE SKU = '"+sku+"'"; 
		SQLResult resultadoPaso12 = sqlSimpE.sqlExecuteSinEv(queryPaso12, Satelites, sqlSimpE.DB_USER_XPOS, sqlSimpE.DB_PASSWORD_XPOS);
		String planogrupo = resultadoPaso12.getData(0, "PLANOGROUP");
		se.buscarYAbrirPlanogrupo(planogrupo);
		
		//PASO 13 
		addStep("En BD XPOS, correr el siguiente query en tabla concentradora (construida por Dev) para buscar el art�culo al que se le modific� la descripci�n larga, por medio del ITM que se consumi� por DS50:");		
		SQLResult resultadoPaso13 = sqlSimpE.consultarSKUEnConcentradora(sku, Satelites);
		String descripcion13 = resultadoPaso13.getData(0, "NAME");
		String amount13 = resultadoPaso13.getData(0, "AMOUNT");
		
		assertEquals(descripcion13, descripcion);
		assertEquals(amount13, amount);
		
    	}
	
		
	@Override
	public String setTestFullName() {
		return "ATC_FT_146_HigienicoMOVILIDADIntentarImprimirEtiquetaPorCambioDescripcionLargaEnBusquedaPorPlanograma";
	}

	@Override
	public String setTestDescription() {
		return "Validar que en el modulo de Impresion de etiquetas del Punto de venta, se muestre el articulo "
				+ "que se dio de alta en tienda, para que permita la impresion de su respectiva etiqueta desde "
				+ "la pesta�a de planogramas";
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
		return "Realizar Pasos 1 - 3 como pre-requisitos";
	}

	@Override
	public void beforeTest() {
	}
}

