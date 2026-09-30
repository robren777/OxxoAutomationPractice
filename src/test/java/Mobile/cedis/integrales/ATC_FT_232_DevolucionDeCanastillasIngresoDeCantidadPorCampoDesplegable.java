package Mobile.cedis.integrales;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

/**
import Mobile.cedis.om.A01_Login_Punto_De_Venta;
import Mobile.cedis.om.A02_Login_Lider;
import Mobile.cedis.om.A03_Menus_y_submenus;
import Mobile.cedis.om.A04_MenuHerramientas;
import Mobile.cedis.om.C01_Listado_de_transferencias_a_verificar;
import Mobile.cedis.om.C02_Verificacion_De_Pedido_Y_Permisos;
*/
import Mobile.cedis.om.A01_Login_Punto_De_Venta;
import Mobile.cedis.om.A02_Login_Lider;
import Mobile.cedis.om.A03_Menus_y_submenus;
import Mobile.cedis.om.A04_MenuHerramientas;
import Mobile.cedis.om.A05_LoginAreaDistribucion;
import Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia;
import Mobile.cedis.om.B05_DevolucionMercancia;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_232_DevolucionDeCanastillasIngresoDeCantidadPorCampoDesplegable extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_232_DevolucionDeCanastillasIngresoDeCantidadPorCampoDesplegable_test(
			HashMap<String, String> data) throws Exception {

		/*******************************
		 * Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script
		 ********************/
		DesiredCapabilities caps = new DesiredCapabilities();

		caps.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		caps.setCapability("deviceName", data.get("deviceName"));
		caps.setCapability("platformVersion", data.get("platformVersion"));
		caps.setCapability("platformName", data.get("platformName"));
		caps.setCapability("appPackage", data.get("appPackage"));
		caps.setCapability("appActivity", data.get("appActivity"));

		String url = "http://127.0.0.1:4723/";

		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, caps);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n" + e.getMessage());
			e.printStackTrace();
		}

		/***********************************************************************************************/

		// Apuntado de Punto de Venta
		A01_Login_Punto_De_Venta LoginPDVOaD = new A01_Login_Punto_De_Venta(testCase, appiumUtil, this);
		System.out.println("\nLogeando Punto de Venta\n");
		Thread.sleep(2000);
		LoginPDVOaD.loginPV(data.get("ip"));

		// Login usuario Lider
		A02_Login_Lider LoginLiderOaD = new A02_Login_Lider(testCase, appiumUtil, this);
		System.out.println("Logeando Lider\n");
		Thread.sleep(2000);
		LoginLiderOaD.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));

		// Menu principal hasta Herramientas de COMETA
		A03_Menus_y_submenus MenuOaD = new A03_Menus_y_submenus(testCase, appiumUtil, this);
		System.out.println("Dando clic en Excelencia Operativa -> HC y COMETA\n");
		Thread.sleep(2000);
		MenuOaD.logeoInicioHerramientas();

		// En el menu de herramientas doy clic en la verificacion de cedis
		A04_MenuHerramientas ModulosOaD = new A04_MenuHerramientas(testCase, appiumUtil, this);
		System.out.println("Dando clic en Recepcion de Pedidos CEDIS\n");
		Thread.sleep(2000);
		ModulosOaD.clickBtnRecepcionPedidosCEDIS();

		// En el menu de herramientas doy clic en la verificacion de cedis
		A05_LoginAreaDistribucion LoginAreaDistribucion = new A05_LoginAreaDistribucion(testCase, appiumUtil, this);
		System.out.println("Logeando Area de Distribucion CEDIS\n");
		Thread.sleep(2000);
		LoginAreaDistribucion.LoginAreaDistribucion();

		B01_RecepcionPedidosSeleccionarTransferencia POM_ListadoDeRecepcion = new B01_RecepcionPedidosSeleccionarTransferencia(
		testCase, appiumUtil, this);
		System.out.println("Entrando a la primera transferencia por Recepcionar de CEDIS\n");
		Thread.sleep(2000);
		POM_ListadoDeRecepcion.SeleccionarPrimeraTransferencia(true);
		
		Mobile.cedis.om.B10_DeseaComenzar DeseaComenzar = new Mobile.cedis.om.B10_DeseaComenzar(testCase, appiumUtil, this);//linea agregada por Juan 
		System.out.println("Entrando a Devolucion CEDIS\n");
		Thread.sleep(2000);
		DeseaComenzar.Cargando();//linea agregada por Juan
		DeseaComenzar.SeleccionarObjeto("Devolucion CEDIS");//linea agregada por Juan
		Thread.sleep(2000);
		
		
		
		B05_DevolucionMercancia POM_Devolucion = new B05_DevolucionMercancia(testCase, appiumUtil, this);
		
		
		
		// Paso #1.- Se ubica en la pantalla: "Devoluciones a Cedis" y ya se selecciono BOL 
		// Resultado esperado: Se ubica en la pantalla: "Devoluciones a Cedis" y se muestra BOL seleccionado
		System.out.println("\nPaso #1: Se ubica en la pantalla: \"Devoluciones a Cedis\" y ya se selecciono BOL");
		addStep("Se ubica en la pantalla: \\\"Devoluciones a Cedis\\\" y ya se selecciono BOL",
				"Resultado esperado: Se ubica en la pantalla: \"Devoluciones a Cedis\" y se muestra BOL seleccionado");
		POM_Devolucion.ValidacionEncabezado();
		Thread.sleep(1000);
		POM_Devolucion.SeleccionarBOLSinCheck("", "");
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		
		// Paso #2.- Se da clic en la cantidad de la canastilla color: "Azul"  
		// Resultado esperado: Se despliega un campo de texto para ingresar la cantidad
		System.out.println("\nPaso #2: Se da clic en la cantidad de la canastilla color: \"Azul\"");
		addStep("Se da clic en la cantidad de la canastilla color: \"Azul\" ",
				"Resultado esperado: Se despliega un campo de texto para ingresar la cantidad");
		POM_Devolucion.MoverSeccion("Canastillas");
		Thread.sleep(1000);
		POM_Devolucion.SeleccionarObjeto("Cantidad Azul");
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		
		
		// Paso #3.- Se ingresa una cantidad en el campo desplegado y se confirma  
		// Resultado esperado: Se actualiza la cantidad de la canastilla, correctamente, con el valor ingresado
		System.out.println("\nPaso #3: Se ingresa una cantidad en el campo desplegado y se confirma");
		addStep("Se ingresa una cantidad en el campo desplegado y se confirma",
				"Resultado esperado: Se actualiza la cantidad de la canastilla, correctamente, con el valor ingresado");
		POM_Devolucion.sendTextUsingKeyEvents(appiumUtil, "5");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(3000);
		POM_Devolucion.SeleccionarObjeto("Confirmar");
		Thread.sleep(1000);
		POM_Devolucion.ValidarCantidadCanastillaColorAgregada("azul", "5");
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		
		
		// Paso #4.- Se da clic en la cantidad de la canastilla color: "Amarilla"   
		// Resultado esperado: Se despliega un campo de texto para ingresar la cantidad
		System.out.println("\nPaso #4: Se da clic en la cantidad de la canastilla color: \"Amarilla\" ");
		addStep("Se da clic en la cantidad de la canastilla color: \"Amarilla\" ",
				"Resultado esperado: Se despliega un campo de texto para ingresar la cantidad");
		POM_Devolucion.SeleccionarObjeto("Cantidad Amarilla");
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		
		
		// Paso #5.- Se ingresa una cantidad en el campo desplegado y se confirma  
		// Resultado esperado: Se actualiza la cantidad de la canastilla, correctamente, con el valor ingresado
		System.out.println("\nPaso #5: Se ingresa una cantidad en el campo desplegado y se confirma");
		addStep("Se ingresa una cantidad en el campo desplegado y se confirma",
				"Resultado esperado: Se actualiza la cantidad de la canastilla, correctamente, con el valor ingresado");
		POM_Devolucion.sendTextUsingKeyEvents(appiumUtil, "10");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(3000);
		POM_Devolucion.SeleccionarObjeto("Confirmar");
		Thread.sleep(1000);
		POM_Devolucion.ValidarCantidadCanastillaColorAgregada("amarilla", "10");
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		
		
		// Paso #6.- Se da clic en la cantidad de la canastilla color: "Verde"   
		// Resultado esperado: Se despliega un campo de texto para ingresar la cantidad
		System.out.println("\nPaso #6: Se da clic en la cantidad de la canastilla color: \"Verde\" ");
		addStep("Se da clic en la cantidad de la canastilla color: \"Verde\" ",
				"Resultado esperado: Se despliega un campo de texto para ingresar la cantidad");
		POM_Devolucion.SeleccionarObjeto("Cantidad Verde");
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		
		
		// Paso #7.- Se ingresa una cantidad en el campo desplegado y se confirma  
		// Resultado esperado: Se actualiza la cantidad de la canastilla, correctamente, con el valor ingresado
		System.out.println("\nPaso #7: Se ingresa una cantidad en el campo desplegado y se confirma");
		addStep("Se ingresa una cantidad en el campo desplegado y se confirma",
				"Resultado esperado: Se actualiza la cantidad de la canastilla, correctamente, con el valor ingresado");
		POM_Devolucion.sendTextUsingKeyEvents(appiumUtil, "15");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(3000);
		POM_Devolucion.SeleccionarObjeto("Confirmar");
		Thread.sleep(1000);
		POM_Devolucion.ValidarCantidadCanastillaColorAgregada("verde", "15");
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		
		// Paso #8.- Se da clic en la cantidad de la canastilla color: "Naranja"   
		// Resultado esperado: Se despliega un campo de texto para ingresar la cantidad
		System.out.println("\nPaso #8: Se da clic en la cantidad de la canastilla color: \"Naranja\" ");
		addStep("Se da clic en la cantidad de la canastilla color: \"Naranja\" ",
				"Resultado esperado: Se despliega un campo de texto para ingresar la cantidad");
		POM_Devolucion.SeleccionarObjeto("Cantidad Naranja");
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		
		
		// Paso #9.- Se ingresa una cantidad en el campo desplegado y se confirma  
		// Resultado esperado: Se actualiza la cantidad de la canastilla, correctamente, con el valor ingresado
		System.out.println("\nPaso #9: Se ingresa una cantidad en el campo desplegado y se confirma");
		addStep("Se ingresa una cantidad en el campo desplegado y se confirma",
				"Resultado esperado: Se actualiza la cantidad de la canastilla, correctamente, con el valor ingresado");
		POM_Devolucion.sendTextUsingKeyEvents(appiumUtil, "25");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(3000);
		POM_Devolucion.SeleccionarObjeto("Confirmar");
		Thread.sleep(1000);
		POM_Devolucion.ValidarCantidadCanastillaColorAgregada("naranja", "25");
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		
		
		// Paso #10.- Se da clic en la cantidad de la canastilla color: "Otro"   
		// Resultado esperado: Se despliega un campo de texto para ingresar la cantidad
		System.out.println("\nPaso #10: Se da clic en la cantidad de la canastilla color: \"Otro\" ");
		addStep("Se da clic en la cantidad de la canastilla color: \"Otro\" ",
				"Resultado esperado: Se despliega un campo de texto para ingresar la cantidad");
		POM_Devolucion.SeleccionarObjeto("Cantidad Otro");
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		
		
		// Paso #11.- Se ingresa una cantidad en el campo desplegado y se confirma  
		// Resultado esperado: Se actualiza la cantidad de la canastilla, correctamente, con el valor ingresado
		System.out.println("\nPaso #11: Se ingresa una cantidad en el campo desplegado y se confirma");
		addStep("Se ingresa una cantidad en el campo desplegado y se confirma",
				"Resultado esperado: Se actualiza la cantidad de la canastilla, correctamente, con el valor ingresado");
		POM_Devolucion.sendTextUsingKeyEvents(appiumUtil, "40");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(3000);
		POM_Devolucion.SeleccionarObjeto("Confirmar");
		Thread.sleep(1000);
		POM_Devolucion.ValidarCantidadCanastillaColorAgregada("otro", "40");
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		

		// Cerrar Apps
		System.out.println("\n\nTerminando caso y cerrando OaD...\n\n");
	}


	@Override		
	public String setTestFullName() {
		return " MTC-FT-232 - Devolucion de Canastillas  - Ingreso de cantidad por campo desplegable";
	}

	@Override
	public String setTestDescription() {
		return " Validar que, al momento de dar clic en el campo de la cantidad de las canastillas a devolver, se despliegue un campo para ingresar la cantidad deseada";
	}

	@Override
	public String setTestDesigner() {
		return " Jesús Marcelo Gutiérrez Bravo - GTIM QA";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "*Haber realizado el proceso de recepcion necesario hasta llegar a la pantalla: Devolucion a Cedis (Recepcion, Resumen y Firmas)\n"
				+ "*Haber seleccionado BOL, para habilitar los campos de la devolucion de canastillas";
	}

	@Override
	public void beforeTest() {
	}

}