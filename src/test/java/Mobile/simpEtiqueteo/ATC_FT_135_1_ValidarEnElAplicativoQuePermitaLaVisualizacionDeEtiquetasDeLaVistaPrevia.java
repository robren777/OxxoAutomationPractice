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
 * DESCRIPCIÓN: 
 *  Asegurar que la vista previa que tiene de las etiquetas desde el apartado de planograma esta tenga la informacion correcta y concide con todas las etiquetas pertenecientes a una misma charola de un mismo planogrupo.
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC_FT_135_1_ValidarEnElAplicativoQuePermitaLaVisualizacionDeEtiquetasDeLaVistaPrevia
 *
 * PRERREQUISITOS:
 * "1. Tener conectado el punto de venta.
2.-Contar con dispositivo Tablet con la aplicacion de oxxo al dia.
3. Tener la impresora conectada y con papel para la impresión de etiquetas"
 * 
 * @author Mario Salazar
 * @date 30 Enero 2025
 */
public class ATC_FT_135_1_ValidarEnElAplicativoQuePermitaLaVisualizacionDeEtiquetasDeLaVistaPrevia extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_135_1_ValidarEnElAplicativoQuePermitaLaVisualizacionDeEtiquetasDeLaVistaPrevia_test(HashMap<String, String> data) throws Exception{
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
    	
    	String usuario = data.get("userLogin");
    	String password = PasswordUtil.decryptPassword(data.get("passwordLogin"));
		// LLAMADO AL POM DE SIMPLIFICACION DE ETIQUETAS
        /***************************************************/  
    	SimpEtiqueteo simpEt = new SimpEtiqueteo(testCase, appiumUtil, this );
    	SQLSimpEtiqueteo sqlSimp = new SQLSimpEtiqueteo(testCase, this);
    	String BDSatelites = data.get("serverSatelites");
		
		
		//INICIO DE SCRIPT
        /***************************************************/
		
    	//PASO 1
        /*********/
		addStep("\"En BD XPOS, correr el siguiente query en tabla concentradora (construida por Dev), para corroborar que se haya actualizado el dato de frente, en el artículo seleccionado.\r\n"
				+ "\r\n"
				+ "SELECT ITEM_ID, SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS, FRONT_SKU FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "WHERE PLANOGRUOP ='Introduce aquí el PLANOGRUPO' AND TRAY = 'INTRODUCE EL # CHAROLA' que deseas consultar'\"");
		
		String querypaso1 = "SELECT SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, TRAY, LOCATION, FRONTS FROM [Satelites].[dbo].[CO_LB_INTL_OX]\r\n"
				+ "WHERE PLANOGROUP = 'Abarrotes' AND TRAY = '2' ";
		
		SQLResult resultpaso1 = sqlSimp.sqlExecute(querypaso1, BDSatelites, sqlSimp.DB_USER_XPOS, sqlSimp.DB_PASSWORD_XPOS);
		sqlSimp.imprimirResult(resultpaso1);
		String charola = resultpaso1.getData(0, "TRAY");
		String planogrupo = resultpaso1.getData(0,"PLANOGROUP");
		
    	//PASO 2
        /*********/
		addStep("Seleccionar app Oxxo al día");
		simpEt.iniciarApp(data.get("appPackage"), data.get("appActivity"));
		//PASO 3
        /*********/
    	addStep("Escribir usuario");
    	simpEt.loginUsuario(usuario);	
    	//PASO 4
        /*********/
    	addStep("Escribir contraseña");
    	simpEt.loginContrasena(password); 
    	//PASO 5
        /*********/
    	addStep("Selecciona la opción ingresar");
    	simpEt.loginIngresar();
    	//PASO 6
        /*********/
    	addStep("Selecciona la opción Excelencia Operativa");
    	simpEt.ExcelenciaOperativa();
    	//PASO 7
        /*********/
    	addStep("Selecciona la opcion de Etiqueteo");
    	simpEt.Etiqueteo();
    	//PASO 8
        /*********/
    	addStep("Selecciona el Botón Aceptar");
    	simpEt.aceptarColocarPapel();
    	//PASO 9
        /*********/
    	addStep("Seleccionar la opción Búsqueda de planograma");
    	simpEt.clickBusquedaPlanograma();
    	//PASO 10
        /*********/
    	addStep("seleccionar un planograma");
    	simpEt.buscarYAbrirPlanogrupo(planogrupo);
    	//PASO 11
        /*********/
    	addStep("Seleccionar el Icono de visualizacion del apartado de todas las charolas");
    	simpEt.visualizaAllCharolas();
    	//PASO 12
        /*********/
    	addStep("Seleccionar imprimir");
    	simpEt.imprimirEtiquetas();
		
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_135_1_ValidarEnElAplicativoQuePermitaLaVisualizacionDeEtiquetasDeLaVistaPrevia";
	}

	@Override
	public String setTestDescription() {
		return "Asegurar que la vista previa que tiene de las etiquetas desde el apartado de planograma esta tenga la informacion correcta y concide con todas las etiquetas pertenecientes a una misma charola de un mismo planogrupo.";
	}

	@Override
	public String setTestDesigner() {
		return "Mario Salazar";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "\"1. Tener conectado el punto de venta.\r\n"
				+ "2.-Contar con dispositivo Tablet con la aplicacion de oxxo al dia.\r\n"
				+ "3. Tener la impresora conectada y con papel para la impresión de etiquetas\"";
	}

	@Override
	public void beforeTest() {
	}

}
