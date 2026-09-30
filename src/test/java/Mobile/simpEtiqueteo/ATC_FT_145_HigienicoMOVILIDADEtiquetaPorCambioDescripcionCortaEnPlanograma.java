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
 *  Validar que al enviar un cambio de descripción de un artículo, se  vea reflejado en el módulo de Impresión de etiquetas del Punto de venta, para que permita la impresión de etiqueta con los datos actualizados
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC_FT_145_HigienicoMOVILIDADEtiquetaPorCambioDescripcionCortaEnPlanograma
 *
 * PRERREQUISITOS:
 * "1. Contar con un equipo XPOS .
2.-Contar con dispositivo HH con la aplicacion de oxxo al dia.
3. Contar con un archivo ITEM  configurado para cambio de descripcion corta trantype 10.
4. Conexión a Servidor FTP por medio de FileZilla(El CR a utilizar debe contar con buzon de bajada y subida activo)
5.Conectarce a la BD de xpos"
 * 
 * @author Amauri Rodriguez
 * @date 30 Enero 2025
 */
public class ATC_FT_145_HigienicoMOVILIDADEtiquetaPorCambioDescripcionCortaEnPlanograma extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_145_HigienicoMOVILIDADEtiquetaPorCambioDescripcionCortaEnPlanograma_test(HashMap<String, String> data) throws Exception {
		
				
		/******************************* Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script  ********************/
		DesiredCapabilities cap = new DesiredCapabilities();
		
		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appium:appPackage", data.get("appPackage"));
		cap.setCapability("appium:appActivity", data.get("appActivity"));
		cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto
		
		String XposStore = data.get("serverXposStore");
		String Satelites = data.get("serverSatelites");
		String sku = data.get("sku");
		String descripcion = data.get("descripcion");

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
		se.loginUsuario(data.get("userLogin"));
		//PASO 06
		addStep("Escribir contrasena");
		se.loginContrasena(PasswordUtil.decryptPassword(data.get("passwordLogin")));
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
		addStep("En BD XPOS, correr el siguiente query en tablas origen (ya existentes en XPOS), para buscar el art�culo al que se le modifico la descripci�n");
		SQLResult resultadoPaso10 = sqlSimpE.consultarSKUEnConcentradora(sku, Satelites);
		String descripcion10 = resultadoPaso10.getData(0, "NAME");
		System.out.println(descripcion10+"\n");
		String planogrupo = resultadoPaso10.getData(0, "PLANOGROUP");
		System.out.println(descripcion+"\n");
		assertEquals(descripcion10, descripcion);
		
		//PASO 11
		addStep("Selecciona el Boton Aceptar");		
		se.aceptarColocarPapel();
		
		//PASO 12
		addStep("Seleccionar la opcion Busqueda de planograma");		
		se.clickBusquedaPlanograma();
		
		//PASO 13
		addStep("Seleccionar el planogrupo al que pertenece el articulo el cual se dio el cambio de descripcion");		
		se.buscarYAbrirPlanogrupo(planogrupo);
		
		//PASO 14
		addStep("Seleccionamos imprimir todas las charolas");		
		se.imprimirTodasLasCharolas();
		
		//PASO 15
		addStep("Seleccionamos aceptar");		
		se.aceptarCortarEtiquetas();
		
		String ContenidoEtiqueta = se.leerContenidoArchivo(se.obtenerArchivoDeEtiquetasMasReciente("Z:\\INV\\logs"));
		se.validarContenidoEtiquetasImpresas(ContenidoEtiqueta, "name", descripcion10);
		
    	}
	
		
	@Override
	public String setTestFullName() {
		return "ATC_FT_145_HigienicoMOVILIDADEtiquetaPorCambioDescripcionCortaEnPlanograma";
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

