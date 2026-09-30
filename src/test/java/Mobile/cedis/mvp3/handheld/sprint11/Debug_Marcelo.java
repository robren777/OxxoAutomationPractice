package Mobile.cedis.mvp3.handheld.sprint11;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.cedis.om.B05_DevolucionMercancia;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

public class Debug_Marcelo extends BaseExecution {
	@SuppressWarnings("static-access")
	@Test(dataProvider = "data-provider", enabled = true)
	public void Debug_Marcelo_test(
			HashMap<String, String> data) throws Exception {

		/*******************************
		 * Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script
		 ********************/
		DesiredCapabilities caps = new DesiredCapabilities();

		caps.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		caps.setCapability("deviceName", data.get("deviceName"));
		caps.setCapability("platformVersion", data.get("platformVersion"));
		caps.setCapability("platformName", data.get("platformName"));

		String url = "http://127.0.0.1:4723/";

		// Cerrar Apps
		Thread.sleep(1000);

		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, caps);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n" + e.getMessage());
			e.printStackTrace();
		}

		Thread.sleep(1000);

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
		Thread.sleep(3000);
		POM_Devolucion.SeleccionarObjeto("Confirmar");
		Thread.sleep(1000);
		POM_Devolucion.ValidarCantidadCanastillaColorAgregada("otro", "40");
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		

		// Cerrar Apps
		System.out.println("\n\nTerminando caso y cerrando OaD...\n\n");
		//Thread.sleep(8000);

	}

	@Override
	public String setTestFullName() {
		return "MTC-FT-001 - Devolucion de Canastillas  - Ingreso de cantidad por campo desplegable";
	}

	@Override
	public String setTestDescription() {
		return "Validar que, al momento de dar clic en el campo de la cantidad de las canastillas a devolver, se despliegue un campo para ingresar la cantidad deseada";
	}

	@Override
	public String setTestDesigner() {
		return "Jesús Marcelo Gutiérrez Bravo";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "\"*Haber realizado el proceso de recepcion necesario hasta llegar a la pantalla:  \"\"Devolucion a Cedis\"\" (Recepcion, Resumen y Firmas)\r\n"
				+ "*Haber seleccionado BOL, para habilitar los campos de la devolucion de canastillas\"";
	}

	@Override
	public void beforeTest() {
	}

}