package Mobile.simpEtiqueteo;

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
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
/**
 * DESCRIPCIÓN: 
 * Asegurar que al momento de relizar un cambio de charola este no despliegue una actualizacion de etiqueta, pero al momento de lanzar un cambio de precio este despliegue la actualizacion de etiqueta con los datos nuevos del cambio de charola.
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC-FT-131_Imprimir_articulo_en_fechas_tras_un_cambio_de_charola_y_despues_realizar_un_cambio_de_precio
 *
 * PRERREQUISITOS:
 * "1. Tener conectado el punto de venta.
2.-Contar con dispositivo HH con la aplicacion de oxxo al dia.
3. Tener la impresora conectada y con papel para la impresión de etiquetas
4. Contar con archivo CSV configurado para cambio de charola se debera tambien tener el job Star monitor ejecutandose de forma automatica
5. Contar con 1 archivo ITM configurado para Cambio de Precio (TRAN_TYPE=11)"
 *
 * INSUMOS:
 * Contar con archivo CSV configurado para cambio de charola se debera tambien tener el job Star monitor ejecutandose de forma automatica
 * Contar con 1 archivo ITM configurado para Cambio de Precio (TRAN_TYPE=11)"
 * @author Amauri Rodriguez
 * @date 22 Enero 2025
 */
public class ATC_FT_131_ImprimirArticuloEnFechasTrasUnCambioDeCharolaYDespuesRealizarUnCambioDePrecio extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_131_ImprimirArticuloEnFechasTrasUnCambioDeCharolaYDespuesRealizarUnCambioDePrecio_test(HashMap<String, String> data) throws Exception {
		
				
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
		String precio = data.get("precio");
		String charola = data.get("charola");

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
		SeleniumUtil u;
    	Thread.sleep(2000);
		
		// Cerrar Apps
		Thread.sleep(5000);
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		
		sqlSimpE.encenderImpresoraVirtual(XposStore);
	
		//PASO 21
		addStep("Seleccionar app Oxxo al dia");
		se.iniciarApp(data.get("appPackage"), data.get("appActivity"));
		//PASO 22
		addStep("Escribir usuario");
		se.loginUsuario(data.get("userLogin"));
		//PASO 23
		addStep("Escribir contrasena");
		se.loginContrasena(PasswordUtil.decryptPassword(data.get("passwordLogin")));
		//PASO 24
		addStep("Selecciona la opcion ingresar");		
		se.loginIngresar();
		//PASO 25
		addStep("Selecciona la opcion Excelencia Operativa");		
		se.ExcelenciaOperativa();
	
		//PASO 26
		addStep("Selecciona la opcion de Etiqueteo");		
		se.Etiqueteo();

		//PASO 27
		addStep("Selecciona el Boton Aceptar");		
		se.aceptarColocarPapel();
		se.esperaPantallaDeCarga();
		
		//PASO 28
		addStep("Selecciona la opcion de Fecha");		
		se.clickBusquedaFecha();
		
		//PASO 29
		addStep("Correr el siguiente  query en BD de XPOS para validar que el precio fue modificado correctamente:");		
		SQLResult resultadoPaso29 = sqlSimpE.consultarSKUEnConcentradora(sku, Satelites);
		sqlSimpE.imprimirResult(resultadoPaso29);
		String cantidad29 = sqlSimpE.formatPrecioDosDecimales(resultadoPaso29.getData(0, "AMOUNT"));
		String tray29 = resultadoPaso29.getData(0, "TRAY");
		
		se.validarCambioDePrecio(precio, cantidad29);
		se.validarCambioDeCharola(charola, tray29);
		
		//PASO 29
		addStep("Ejecutar el siguiente query  en BD de XPOS para corroborar que se aplic� el cambio de precio al articulo:");		
		SQLResult resultadoPaso30 = sqlSimpE.consultaAOrigen(sku, Satelites);
		String cantidad30 = resultadoPaso30.getData(0, "PRECIO");	
		se.validarCambioDePrecio(precio, cantidad30);
		
		//PASO 31
		addStep("Se selecciona el rango de fecha que quieres imprimir");		
		se.seleccionarDiaDelSistema();
		
		//PASO 32
		addStep("Se presiona el boton \"Revisar Etiquetas\"");		
		se.revisarEtiquetasFechas();
		
		//PASO 33
		addStep("Se presiona el boton \"Imprimir\"");		
		se.imprimirEtiquetas();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		String ContenidoEtiqueta = se.leerContenidoArchivo(se.obtenerArchivoDeEtiquetasMasReciente("Z:\\INV\\logs"));
		se.validarContenidoEtiquetasImpresas(ContenidoEtiqueta, "tray", tray29);
		se.validarContenidoEtiquetasImpresas(ContenidoEtiqueta, "salePrice", cantidad29);
		
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");

    	}
	
		
	@Override
	public String setTestFullName() {
		return "ATC_FT_131_ImprimirArticuloEnFechasTrasUnCambioDeCharolaYDespuesRealizarUnCambioDePrecio";
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
		return "Realizar Pasos 1 - 20 como pre-requisitos";
	}

	@Override
	public void beforeTest() {
	}
}

