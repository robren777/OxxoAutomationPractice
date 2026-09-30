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
import Mobile.cedis.om.B02_RecepcionPedidosCEDIS;
import Mobile.cedis.om.B03_ResumenPedidosCEDIS;
import Mobile.cedis.om.B05_DevolucionMercancia;
import Mobile.cedis.om.B07_ResumenDevolucion;
import Mobile.cedis.om.B08_AcuerdoDevolucion;
import Mobile.cedis.om.B10_DeseaComenzar;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_157_TabletMaximoDeTransferenciasCandadoDeTransferenciaRecibidaPorXpos extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_157_TabletMaximoDeTransferenciasCandadoDeTransferenciaRecibidaPorXpos_test(
			HashMap<String, String> data) throws Exception {

		/*******************************
		 * Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script
		 ********************/
		DesiredCapabilities cap = new DesiredCapabilities();

		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appium:appPackage", data.get("appPackage"));
		cap.setCapability("appium:appActivity", data.get("appActivity"));
		// Tener en cuenta que para los Scripts de Tablet hay que poner el udid sino no funciona, se puso el udid de la tablet prestada (No es la de la galleta)
		cap.setCapability("udid", data.get("udId"));

		String url = "http://127.0.0.1:4723/";

		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}
		
		//Inicializacion de POMs
		B01_RecepcionPedidosSeleccionarTransferencia B01_RecepcionPedidosSeleccionarTransferencia = new B01_RecepcionPedidosSeleccionarTransferencia(testCase, appiumUtil, this);
		B10_DeseaComenzar B10_DeseaComenzar = new B10_DeseaComenzar(testCase, appiumUtil, this);
		B05_DevolucionMercancia B05_DevolucionMercancia = new B05_DevolucionMercancia(testCase, appiumUtil, this);
		B08_AcuerdoDevolucion B08_AcuerdoDevolucion = new B08_AcuerdoDevolucion(testCase, appiumUtil, this);
		B07_ResumenDevolucion B07_ResumenDevolucion = new B07_ResumenDevolucion(testCase, appiumUtil, this);
		B02_RecepcionPedidosCEDIS B02_RecepcionPedidosCEDIS = new B02_RecepcionPedidosCEDIS(testCase, appiumUtil, this);
		B03_ResumenPedidosCEDIS B03_ResumenPedidosCEDIS = new B03_ResumenPedidosCEDIS(testCase, appiumUtil, this);
		A05_LoginAreaDistribucion A05_LoginAreaDistribucion = new A05_LoginAreaDistribucion(testCase, appiumUtil, this);
		A03_Menus_y_submenus A03_Menus_y_submenus = new A03_Menus_y_submenus(testCase, appiumUtil, this);
		A04_MenuHerramientas A04_MenuHerramientas = new A04_MenuHerramientas(testCase, appiumUtil, this);
		A01_Login_Punto_De_Venta A01_Login_Punto_De_Venta = new A01_Login_Punto_De_Venta(testCase, appiumUtil, this);
		A02_Login_Lider A02_Login_Lider = new A02_Login_Lider(testCase, appiumUtil, this);
		Mobile.cedis.om.A05_LoginAreaDistribucion C05LoginAreaDistribucion = new Mobile.cedis.om.A05_LoginAreaDistribucion(testCase,
				appiumUtil, this);
		
		//---------------------------------------- PRE INICIO ------------------------------------------------//

		//En el login de punto de venta ingreso la ip y el puerto
		System.out.println("Logeando Punto de Venta");
		Thread.sleep(2000);
		// Se puso el punto de venta 10NLA50AXR
		A01_Login_Punto_De_Venta.loginPV(data.get("ip"));

		//En el login de lider ingreso el usuario y contraseña
		System.out.println("Logeando Lider");
		Thread.sleep(2000);
		A02_Login_Lider.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));

		//En la pantalla principal doy clic en el boton de excelencia operativa, luego en la hc y al final e cometa
		System.out.println("Dando clic en Excelencia Operativa -> HC y COMETA");
		Thread.sleep(8000);
		A03_Menus_y_submenus.logeoInicioHerramientas();
		
		//En el menu de herramientas doy clic en la Recepción de Pedidos CEDIS
		System.out.println("Dando clic en Recepción de Pedidos CEDIS");
		Thread.sleep(2000);
		A04_MenuHerramientas.clickBtnRecepcionPedidosCEDIS();
		
		// Login de Area de Distribucion
		System.out.println("\nLogeando Area de Distribucion...");
		Thread.sleep(2000);
		C05LoginAreaDistribucion.LoginAreaDistribucion();

		//=================================== Inicio de Caso =====================================//
 /* 
 * Paso 1  - Se ubica en la pantalla: "Recepcion de pedido Cedis"
 * Resultado esperado: Se despliega correctamente la pantalla: "Recepcion de pedido Cedis", donde se visualizan por lo menos dos transferencias sin iniciar
*/ 
		addStep("Se ubica en la pantalla: \"Recepcion de pedido Cedis\"", "Resultado esperado: Se despliega correctamente la pantalla: \"Recepcion de pedido Cedis\", donde se visualizan por lo menos dos transferencias sin iniciar");
		Thread.sleep(2000);
		B01_RecepcionPedidosSeleccionarTransferencia.ValidarDespliegueDeAlMenosDosTransferencias();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
/* 
 * Paso 2  - Se da clic en una de estas dos transferencias
 * Resultado esperado: Se despliega correctamente la pantalla de recepcion de esta transferencia
*/ 
		addStep("Se da clic en una de estas dos transferencias", "Resultado esperado: Se despliega correctamente la pantalla de recepcion de esta transferencia");
		Thread.sleep(2000);
		B01_RecepcionPedidosSeleccionarTransferencia.SeleccionarPrimeraTransferencia(true);
		Thread.sleep(2000);
		B10_DeseaComenzar.Cargando();
		Thread.sleep(2000);
		B10_DeseaComenzar.SeleccionarObjeto("Recepcion Pedido");
		Thread.sleep(2000);
		Runtime.getRuntime().exec("adb shell input swipe 100 500 100 1450 100");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.ValidacionEncabezado();
		Thread.sleep(2000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
/* 
 * Paso 3  - Se toman las fotografias de evidencia
 * Resultado esperado: Se tomaron y guardaron correctamente las 3 fotografias de evidencia
*/ 
		addStep("Se toman las fotografias de evidencia", "Resultado esperado: Se tomaron y guardaron correctamente las 3 fotografias de evidencia");
		B02_RecepcionPedidosCEDIS.Tomar3Fotos();
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.ValidarGuardadoDeLasTresFotografias();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
/* 
 * Paso 4  - Se realiza el ingreso de una cantidad significativa de cajas y canastillas
 * Resultado esperado: Se realizo el ingreso de cajas y canastillas correctamente y aumento el numero de cada seccion de conteo
 */ 
		addStep("Se realiza el ingreso de una cantidad significativa de cajas y canastillas", "Resultado esperado: Se realizo el ingreso de cajas y canastillas correctamente y aumento el numero de cada seccion de conteo");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("00001118000086753840-7501064113024-1");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("00001118000086753840-7501064113024-2");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("00001118000086753841-7501064191909-1");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("00001118000086753841-7501064191909-2");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("5667209");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("667445");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("7986595");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("756391");
		B02_RecepcionPedidosCEDIS.ValidarAumentoDeBultosEnTotalEntregado();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");		

/* 
 * Paso 5  - Se continua el flujo hasta llegar a la seccion de cajas sin etiqueta
 * Resultado esperado: Se despliega correctamente la seccion de cajas sin etiqueta
*/ 
		addStep("Se continua el flujo hasta llegar a la seccion de cajas sin etiqueta", "Resultado esperado: Se despliega correctamente la seccion de cajas sin etiqueta");
		B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Terminar");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Confirmar");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Si");
		Thread.sleep(2000);
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.ValidarDespliegueSeccionCajasSinEtiqueta();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
/* 
 * Paso 6  - Se realiza el ingreso de una cantidad significativa de cajas sin etiqueta
 * Resultado esperado: Se realizo el ingreso de cajas sin etiqueta correctamente
*/ 
		addStep("Se realiza el ingreso de una cantidad significativa de cajas sin etiqueta", "Resultado esperado: Se realizo el ingreso de cajas sin etiqueta correctamente");
		B02_RecepcionPedidosCEDIS.RegistrarSinEtiquetaTablet();
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.ValidarIngresoCajaSinEtiqueta();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
/* 
 * Paso 7  - Se continua el flujo hasta llegar a la seccion de sobrantes
 * Resultado esperado: Se despliega correctamente la seccion de sobrantes
*/ 
		addStep("Se continua el flujo hasta llegar a la seccion de sobrantes", "Resultado esperado: Se despliega correctamente la seccion de sobrantes");
		B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Terminar Cajas Sin Etiqueta");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Confirmar");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.ValidarDespliegueSeccionSobrantes();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
/* 
 * Paso 8  - Se realiza el ingreso de una cantidad significativa de sobrantes
 * Resultado esperado: Se realizo el ingreso de sobrantes correctamente
*/ 
		addStep("Se realiza el ingreso de una cantidad significativa de sobrantes", "Resultado esperado: Se realizo el ingreso de sobrantes correctamente");
		B02_RecepcionPedidosCEDIS.CapturaContenedorSobrantes("867445");
		B02_RecepcionPedidosCEDIS.CapturaContenedorSobrantes("863451");
		B02_RecepcionPedidosCEDIS.CapturaContenedorSobrantes("645521");
		B02_RecepcionPedidosCEDIS.CapturaContenedorSobrantes("645522");
		B02_RecepcionPedidosCEDIS.CapturaContenedorSobrantes("645523");
		Thread.sleep(2000);
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.ValidarIngresoSobrantes(5);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
/* 
 * Paso 9  - Se continua el flujo hasta llegar a la seccion de danados
 * Resultado esperado: Se despliega correctamente la seccion de danados
*/ 
		addStep("Se continua el flujo hasta llegar a la seccion de danados", "Resultado esperado: Se despliega correctamente la seccion de danados");
		B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Continuar Dañados");
		Thread.sleep(2000);
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.ValidarDespliegueSeccionDanados();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
/* 
 * Paso 10  - Se realiza el ingreso de una cantidad significativa de diferentes articulos danados (Cantidad de danados diferentes, no en un solo articulo meter una cantidad alta del mismo)
 * Resultado esperado: Se realizo el ingreso de danados correctamente
*/ 
		addStep("Se realiza el ingreso de una cantidad significativa de diferentes articulos danados (Cantidad de danados diferentes, no en un solo articulo meter una cantidad alta del mismo)", "Resultado esperado: Se realizo el ingreso de danados correctamente");
		B02_RecepcionPedidosCEDIS.SeleccionarArticulo("024", "", "");
		Thread.sleep(2000);
		for (int i = 0; i < 10; i++) {
			B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Aumentar Dañado");
		}
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.SeleccionarArticulo("909", "", "");
		Thread.sleep(2000);
		for (int i = 0; i < 20; i++) {
			B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Aumentar Dañado Dos");
		}
		Thread.sleep(2000);
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.ValidacionIngresoDanados(30);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
/* 
 * Paso 11 - Ahora volvemos hacia el listado de transferencias por recepcionar, para ingresar datos en otra transferencia  con una cantidad significativa de cajas, canastillas, cajas sin etiqueta, sobrantes y danados
 * Resultado esperado: Se despliega correctamente la pantalla: \"Recepcion de pedido Cedis\", donde se visualiza la transferencia que dejamos en proceso (icono amarillo) y por lo menos una transferencia sin iniciar
*/ 
		addStep("Ahora volvemos hacia el listado de transferencias por recepcionar, para ingresar datos en otra transferencia  con una cantidad significativa de cajas, canastillas, cajas sin etiqueta, sobrantes y danados", "Resultado esperado: Se despliega correctamente la pantalla: \"Recepcion de pedido Cedis\", donde se visualiza la transferencia que dejamos en proceso (icono amarillo) y por lo menos una transferencia sin iniciar");
		Runtime.getRuntime().exec("adb shell input keyevent 4");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Desea Salir");
		Thread.sleep(2000);
		Runtime.getRuntime().exec("adb shell input keyevent 4");
		Thread.sleep(2000);
		A05_LoginAreaDistribucion.LoginAreaDistribucion();
		Thread.sleep(2000);
		B01_RecepcionPedidosSeleccionarTransferencia.ValidacionIconoDePrimerTransferenciaEnProceso();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
/* 
 * Paso 12 - Se da clic en esta segunda transferencia que falta por llenar de datos
 * Resultado esperado: Se despliega correctamente la pantalla de recepcion de esta segunda transferencia
*/ 
		addStep("Se da clic en esta segunda transferencia que falta por llenar de datos", "Resultado esperado: Se despliega correctamente la pantalla de recepcion de esta segunda transferencia");
		B01_RecepcionPedidosSeleccionarTransferencia.SeleccionarSegundaTransferencia(true);
		Thread.sleep(2000);
		B10_DeseaComenzar.Cargando();
		Thread.sleep(2000);
		B10_DeseaComenzar.SeleccionarObjeto("Recepcion Pedido");
		Thread.sleep(2000);
		Runtime.getRuntime().exec("adb shell input swipe 100 500 100 1450 100");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.ValidacionEncabezado();
		Thread.sleep(2000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
/* 
 * Paso 13 - Se toman las fotografias de evidencia
 * Resultado esperado: Se tomaron y guardaron correctamente las 3 fotografias de evidencia
*/ 
		addStep("Se toman las fotografias de evidencia", "Resultado esperado: Se tomaron y guardaron correctamente las 3 fotografias de evidencia");
		B02_RecepcionPedidosCEDIS.Tomar3Fotos();
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.ValidarGuardadoDeLasTresFotografias();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
/* 
 * Paso 14 - Se realiza el ingreso de una cantidad significativa de cajas y canastillas
 * Resultado esperado: Se realizo el ingreso de cajas y canastillas correctamente y aumento el numero de cada seccion de conteo
*/ 
		addStep("Se realiza el ingreso de una cantidad significativa de cajas y canastillas", "Resultado esperado: Se realizo el ingreso de cajas y canastillas correctamente y aumento el numero de cada seccion de conteo");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("00001118000086753840-7501064113024-1");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("00001118000086753840-7501064113024-2");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("00001118000086753841-7501064191909-1");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("00001118000086753841-7501064191909-2");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("5667209");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("667445");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("7986595");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("756391");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.ValidarAumentoDeBultosEnTotalEntregado();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
/* 
 * Paso 15 - Se continua el flujo hasta llegar a la seccion de cajas sin etiqueta
 * Resultado esperado: Se despliega correctamente la seccion de cajas sin etiqueta
*/ 
		addStep("Se continua el flujo hasta llegar a la seccion de cajas sin etiqueta", "Resultado esperado: Se despliega correctamente la seccion de cajas sin etiqueta");
		B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Terminar");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Confirmar");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Si");
		Thread.sleep(2000);
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.ValidarDespliegueSeccionCajasSinEtiqueta();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
/* 
 * Paso 16 - Se realiza el ingreso de una cantidad significativa de cajas sin etiqueta
 * Resultado esperado: Se realizo el ingreso de cajas sin etiqueta correctamente
*/ 
		addStep("Se realiza el ingreso de una cantidad significativa de cajas sin etiqueta", "Resultado esperado: Se realizo el ingreso de cajas sin etiqueta correctamente");
		B02_RecepcionPedidosCEDIS.RegistrarSinEtiquetaTablet();
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.ValidarIngresoCajaSinEtiqueta();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
/* 
 * Paso 17 - Se continua el flujo hasta llegar a la seccion de sobrantes
 * Resultado esperado: Se despliega correctamente la seccion de sobrantes"
*/ 
		addStep("Se continua el flujo hasta llegar a la seccion de sobrantes", "Resultado esperado: Se despliega correctamente la seccion de sobrantes");
		B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Terminar Cajas Sin Etiqueta");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Confirmar");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.ValidarDespliegueSeccionSobrantes();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
/* 
 * Paso 18 - Se realiza el ingreso de una cantidad significativa de sobrantes
 * Resultado esperado: Se realizo el ingreso de sobrantes correctamente
*/
		addStep("Se realiza el ingreso de una cantidad significativa de sobrantes", "Resultado esperado: Se realizo el ingreso de sobrantes correctamente");
		B02_RecepcionPedidosCEDIS.CapturaContenedorSobrantes("767495");
		B02_RecepcionPedidosCEDIS.CapturaContenedorSobrantes("763491");
		B02_RecepcionPedidosCEDIS.CapturaContenedorSobrantes("545527");
		B02_RecepcionPedidosCEDIS.CapturaContenedorSobrantes("445528");
		B02_RecepcionPedidosCEDIS.CapturaContenedorSobrantes("345529");
		Thread.sleep(2000);
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.ValidarIngresoSobrantes(5);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
/* 
 * Paso 19 - Se continua el flujo hasta llegar a la seccion de danados
 * Resultado esperado: Se despliega correctamente la seccion de danados
*/
		addStep("Se continua el flujo hasta llegar a la seccion de danados", "Resultado esperado: Se despliega correctamente la seccion de danados");
		B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Continuar Dañados");
		Thread.sleep(2000);
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.ValidarDespliegueSeccionDanados();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
/* 
 * Paso 20 - Se realiza el ingreso de una cantidad significativa de diferentes articulos danados (Cantidad de danados diferentes, no en un solo articulo meter una cantidad alta del mismo)
 * Resultado esperado: Se realizo el ingreso de danados correctamente
*/
		addStep("Se realiza el ingreso de una cantidad significativa de diferentes articulos danados (Cantidad de danados diferentes, no en un solo articulo meter una cantidad alta del mismo)", "Resultado esperado: Se realizo el ingreso de danados correctamente");
		B02_RecepcionPedidosCEDIS.SeleccionarArticulo("024", "", "");
		Thread.sleep(2000);
		for (int i = 0; i < 15; i++) {
			B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Aumentar Dañado");
		}
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.SeleccionarArticulo("909", "", "");
		Thread.sleep(2000);
		for (int i = 0; i < 6; i++) {
			B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Aumentar Dañado Dos");
		}
		Thread.sleep(2000);
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.ValidacionIngresoDanados(21);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
/* 
 * Paso 21 - Ahora volvemos hacia el listado de transferencias por recepcionar, para terminar la primer transferencia que dejamos en proceso
 * Resultado esperado: Se despliega correctamente la pantalla: \"Recepcion de pedido Cedis\", donde se visualizan estas dos transferencias que estan en proceso (icono amarillo)
*/
		addStep("Ahora volvemos hacia el listado de transferencias por recepcionar, para terminar la primer transferencia que dejamos en proceso", "Resultado esperado: Se despliega correctamente la pantalla: \"Recepcion de pedido Cedis\", donde se visualizan estas dos transferencias que estan en proceso (icono amarillo)");
		Runtime.getRuntime().exec("adb shell input keyevent 4");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Desea Salir");
		Thread.sleep(2000);
		Runtime.getRuntime().exec("adb shell input keyevent 4");
		Thread.sleep(2000);
		A05_LoginAreaDistribucion.LoginAreaDistribucion();
		Thread.sleep(2000);
		B01_RecepcionPedidosSeleccionarTransferencia.ValidacionIconoDeSegundaTransferenciaEnProceso();
		B01_RecepcionPedidosSeleccionarTransferencia.ValidacionIconoSDeDosTransferenciasEnProceso();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
/* 
 * Paso 22 - Daremos clic en la primer transferencia que avanzamos e ingresamos datos
 * Resultado esperado: Se despliega correctamente esta transferencia con los datos que ingresamos correctamente (cajas, canastillas, cajas sin etiqueta, sobrantes y danados)
*/
		addStep("Daremos clic en la primer transferencia que avanzamos e ingresamos datos", "Resultado esperado: Se despliega correctamente esta transferencia con los datos que ingresamos correctamente (cajas, canastillas, cajas sin etiqueta, sobrantes y danados)");
		B01_RecepcionPedidosSeleccionarTransferencia.SeleccionarPrimeraTransferencia(true);
		Thread.sleep(2000);
		B10_DeseaComenzar.Cargando();
		Thread.sleep(2000);
		B10_DeseaComenzar.SeleccionarObjeto("Recepcion Pedido");
		Thread.sleep(2000);
		Runtime.getRuntime().exec("adb shell input swipe 100 500 100 1450 100");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.ValidacionEncabezado();
		B02_RecepcionPedidosCEDIS.ValidarGuardadoDeLasTresFotografias();
		B02_RecepcionPedidosCEDIS.ValidarAumentoDeBultosEnTotalEntregado();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
		Thread.sleep(2000);
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.ValidarDespliegueSeccionSobrantes();
		B02_RecepcionPedidosCEDIS.ValidarIngresoSobrantes(5);
		for (int i = 0; i < 2; i++) {
		    Runtime.getRuntime().exec("adb shell input swipe 500 1800 500 300 100");
		    Thread.sleep(3000);
		}
		B02_RecepcionPedidosCEDIS.ValidarDespliegueSeccionDanados();
		B02_RecepcionPedidosCEDIS.ValidacionIngresoDanados(30);
		Thread.sleep(2000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	}


	@Override		
	public String setTestFullName() {
		return " MTC-FT-157/Tabletmaximo de transferencias: Candado de transferencia recibida por Xpos";
	}

	@Override
	public String setTestDescription() {
		return " Validar que al momento de la avanzar el flujo de dos transferencias con una cantidad significativa de cajas, canastillas, cajas sin etiqueta, sobrantes y dañados, la aplicacion no presente fallos en el autoguardado debido a la alta carga de datos en cada una";
	}

	@Override
	public String setTestDesigner() {
		return " Vanessa Torres";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "* Tener dos transferencias con cantidad alta de bultos sin iniciar y que no hayan sido recibidas de forma tradicional Xpos\n"
				+ "* No tener avance en otras transferencias para evitar el candado de maximo de transferencias en proceso\n"
				+ "* Codigos para ingresar en los diversos apartados del flujo que abarca el autoguardado";
	}

	@Override
	public void beforeTest() {
	}

}