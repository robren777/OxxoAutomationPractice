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

public class ATC_FT_114_HandheldContinuarAutoscrollQuePosicionaAutomaticamenteEnLaSeccionDondeFaltaLaEvidenciaFotografiaDeCanastillasConDevolucion extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_114_HandheldContinuarAutoscrollQuePosicionaAutomaticamenteEnLaSeccionDondeFaltaLaEvidenciaFotografiaDeCanastillasConDevolucion_test(
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

		Thread.sleep(1000);

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
		DeseaComenzar.Cargando();//linea agregada por Juan
		DeseaComenzar.SeleccionarObjeto("Devolucion CEDIS");//linea agregada por Juan
		
		B05_DevolucionMercancia POM_Devolucion = new B05_DevolucionMercancia(testCase, appiumUtil, this);
		System.out.println("Seleccionando BOL\n");
		Thread.sleep(1000);
		POM_Devolucion.SeleccionarPrimerBol("", "");//Considerar agregar en el excel el Bol a seleccionar o utilizar el metodo de SeleccionarPrimerBol
		Thread.sleep(1000);
		System.out.println("Ingresando canastilla en Devolucion de contenedores\n");
		Thread.sleep(1000);
		POM_Devolucion.AumentarCanastilla(1, "Azul");
		System.out.println("Ingresando evidencia en Devolucion de contenedores\n");
		Thread.sleep(1000);
		POM_Devolucion.TomarFotoDevolucion();		
		
		// Paso #1.- Estar en el mensaje: "No se cuenta con la fotografia de canastillas con devolucion, favor de tomar la fotografia para continuar. ACEPTAR"
		// Resultado esperado: Se muestra mensaje
		System.out.println("\nPaso #1: Estar en el mensaje: \"No se cuenta con la fotografia de canastillas con devolucion, favor de tomar la fotografia para continuar. ACEPTAR\"...");
		Thread.sleep(2000);
		addStep("Estar en el mensaje: \"No se cuenta con la fotografia de canastillas con devolucion, favor de tomar la fotografia para continuar. ACEPTAR\"",
				"Resultado esperado: Se muestra la sección \"Cajas sin etiquetas/etiquetas dañadas\" con la caja que falto de recepcionar");
		POM_Devolucion.IngresarCanastilla("123458");
		Thread.sleep(1000);
		POM_Devolucion.SeleccionarObjeto("Continuar");
		Thread.sleep(2000);
		POM_Devolucion.ValidacionMensajeDeCanastillaConDevolucionSinFotografia();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		// Paso #2.- Selecciona ACEPTAR
		// Resultado esperado: El sistema automaticamente te ubica en la parte donde hace falta la evidencia (fotografia)
		System.out.println("\nPaso #2: Selecciona ACEPTAR...");
		Thread.sleep(2000);
		addStep("Selecciona ACEPTAR",
				"Resultado esperado: El sistema automaticamente te ubica en la parte donde hace falta la evidencia (fotografia)");
		POM_Devolucion.SeleccionarObjeto("Aceptar");
		Thread.sleep(2000);
		POM_Devolucion.ValidarDespliegueRegistroDeCanastillaConDevolucion();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		// Cerrar Apps
		System.out.println("Terminando caso y cerrando OaD...");
	}


	@Override		
	public String setTestFullName() {
		return " MTC-FT-114 Handheld Continuar: Autoscroll que posiciona automaticamente en la seccion donde falta la evidencia (fotografia) de canastillas con devolucion ";
	}

	@Override
	public String setTestDescription() {
		return " Validar que al aceptar el mensaje fotografias de canastillas con devolucion la pantalla haga un autoscroll hasta la parte donde hace falta la evidencia";
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
		return "*Haber realizado la recepcion de una transferencia que contenga al menos bultos tipo canastilla\n"
				+ "*Haber ingresado al menos 2 canastillas para devolucion";
	}

	@Override
	public void beforeTest() {
	}

}