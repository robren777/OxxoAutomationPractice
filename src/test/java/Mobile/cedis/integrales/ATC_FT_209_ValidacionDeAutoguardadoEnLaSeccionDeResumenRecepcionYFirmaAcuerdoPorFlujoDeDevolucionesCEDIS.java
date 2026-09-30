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
import Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia;
import Mobile.cedis.om.B02_RecepcionPedidosCEDIS;
import Mobile.cedis.om.B03_ResumenPedidosCEDIS;
import Mobile.cedis.om.B04_AcuerdoEntrega;
import Mobile.cedis.om.B05_DevolucionMercancia;
import Mobile.cedis.om.B07_ResumenDevolucion;
import Mobile.cedis.om.B08_AcuerdoDevolucion;
import Mobile.cedis.om.B09_FormatoUnicoEntrega;
import Mobile.cedis.om.B10_DeseaComenzar;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_209_ValidacionDeAutoguardadoEnLaSeccionDeResumenRecepcionYFirmaAcuerdoPorFlujoDeDevolucionesCEDIS extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_209_ValidacionDeAutoguardadoEnLaSeccionDeResumenRecepcionYFirmaAcuerdoPorFlujoDeDevolucionesCEDIS_test(
			HashMap<String, String> data) throws Exception {

		/*******************************
		 * Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script
		 ********************/
		DesiredCapabilities cap = new DesiredCapabilities();

		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appPackage", data.get("appPackage"));
		cap.setCapability("appActivity", data.get("appActivity"));

		String url = "http://127.0.0.1:4723/";

		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}

	
		// Ejecuta el logeo hacia el punto de venta
		Mobile.cedis.om.A01_Login_Punto_De_Venta login = new Mobile.cedis.om.A01_Login_Punto_De_Venta(testCase, appiumUtil, this);

		login.loginPV(data.get("ip"));

		// Ejecuta el logeo del lider
		Mobile.cedis.om.A02_Login_Lider loginLider = new Mobile.cedis.om.A02_Login_Lider(testCase, appiumUtil, this);
		// Obtiene valor del DataProvaider
		loginLider.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));

		// Ejecuta los pasos para llegar al menu de Herramientas de CEDIS
		Mobile.cedis.om.A03_Menus_y_submenus  MenuOaD = new Mobile.cedis.om.A03_Menus_y_submenus(testCase, appiumUtil, this);
		MenuOaD.logeoInicioHerramientas();

		Mobile.cedis.om.A04_MenuHerramientas ModulosOaD = new Mobile.cedis.om.A04_MenuHerramientas(testCase, appiumUtil, this);
		ModulosOaD.clickBtnRecepcionPedidosCEDIS();// Selecciona la opcion de Recepcion de Pedidos CCEDIS
		
		Mobile.cedis.om.A05_LoginAreaDistribucion LoginAreaDistribucion = new Mobile.cedis.om.A05_LoginAreaDistribucion(testCase, appiumUtil, this);
		LoginAreaDistribucion.LoginAreaDistribucion();//Se logea en Area de Distribución sin tomar evidencia
		
		//OM A utilizar
		B01_RecepcionPedidosSeleccionarTransferencia POM_ListadoDeRecepcion = new B01_RecepcionPedidosSeleccionarTransferencia(testCase, appiumUtil, this);
		B02_RecepcionPedidosCEDIS PedidoRecepcion = new B02_RecepcionPedidosCEDIS(testCase, appiumUtil, this);
		B03_ResumenPedidosCEDIS ResumenCedis = new B03_ResumenPedidosCEDIS(testCase, appiumUtil, this);
		B04_AcuerdoEntrega Acuerdo = new B04_AcuerdoEntrega(testCase, appiumUtil, this);		
		B05_DevolucionMercancia POM_Devolucion = new B05_DevolucionMercancia(testCase, appiumUtil, this);			
		B07_ResumenDevolucion Resumen = new B07_ResumenDevolucion(testCase, appiumUtil, this);
		B08_AcuerdoDevolucion AcuerdoDev = new B08_AcuerdoDevolucion(testCase, appiumUtil, this);
		B09_FormatoUnicoEntrega FueRec = new B09_FormatoUnicoEntrega(testCase, appiumUtil, this);
		B10_DeseaComenzar POM_DeseaComenzar = new B10_DeseaComenzar(testCase, appiumUtil, this);
						
		/*Paso 1 Se capturan los datos correspondientes en la pantalla de logueo de Recepcion de PEDIDOS CEDIS  dando clic en Entrar	
		 * Resultado esperado: Se muestra la pantalla Recepcion de Pedidos CEDIS con el listado de Transferencias asignadas a la tienda.
		 * 
		 */		
		addStep("Se capturan los datos correspondientes en la pantalla de logueo de Recepcion de PEDIDOS CEDIS  dando clic en Entrar	Resultado esperado: Se muestra la pantalla Recepcion de Pedidos CEDIS con el listado de Transferencias asignadas a la tienda.");		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		POM_ListadoDeRecepcion.ValidacionEncabezado();
		Thread.sleep(5000);
		Runtime.getRuntime().exec("adb shell input swipe 20 1003 20 900 90");//
		Thread.sleep(3000);
		System.out.println("Se muestra la pantalla Recepcion de Pedidos CEDIS con el listado de Transferencias asignadas a la tienda.");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 2 Se selecciona una transferencia que no ha sido atendida por el usuario.	
		 * Resultado esperado: "Se muestra la pantalla de Recepcion de Pedidos CEDIS con las nuevas opciones de flujo, mostrara:
				-Cabecera de pedido: Fecha, Ruta, Ruta Dinámica, CR Tienda, Nombre Tienda, Líder Tienda.
				Mostrara el Título de :""¿Como deseas comenzar?""
				Mostrara las Instrucciones de: ""Puedes comenzar escaneando las cajas y canastillas del pedido, o bien, registrando las devoluciones del pedido. Selecciona la opción deseada.""
				Muestra los botones de:
				-"Recepcion de pedido"
				-"Devoluciones a CEDIS"
		 * 
		 */		
		addStep("Se selecciona una transferencia que no ha sido atendida por el usuario.	\r\n"
				+ "Resultado esperado: \"Se muestra la pantalla de Recepcion de Pedidos CEDIS con las nuevas opciones de flujo, mostrara:\r\n"
				+ "-Cabecera de pedido: Fecha, Ruta, Ruta Dinámica, CR Tienda, Nombre Tienda, Líder Tienda.\r\n"
				+ "	Mostrara el Título de :\"\"¿Como deseas comenzar?\"\"\r\n"
				+ "	Mostrara las Instrucciones de: \"\"Puedes comenzar escaneando las cajas y canastillas del pedido, o bien, registrando las devoluciones del pedido. Selecciona la opción deseada.\"\"\r\n"
				+ "	Muestra los botones de:\r\n"
				+ "	\"Recepcion de pedido\"\r\n"
				+ " \"Devoluciones a CEDIS\"");		
		System.out.println("Entrando a la primera transferencia por Recepcionar de CEDIS\n");
		Thread.sleep(2000);
		POM_ListadoDeRecepcion.BuscarTransferencia(true,data.get("numTransferencia"));
		Thread.sleep(2000);		
		POM_DeseaComenzar.Cargando();
		POM_DeseaComenzar.ValidacionEncabezado();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(5000);
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
		Thread.sleep(3000);
		System.out.println("Se muestra correctamente la pantalla de Recepcion de Pedidos CEDIS  ¿Como deseas comenzar?");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 3 Se selecciona la opción de Devoluciones a CEDIS	
		 * Resultado esperado: Se inicia el flujo de la transferencia mostrando la pantalla de Devoluciones CEDIS
		 * 
		 */		
		addStep("Se selecciona la opción de Devoluciones a CEDIS	Resultado esperado: Se inicia el flujo de la transferencia mostrando la pantalla de Devoluciones CEDIS");		
		POM_DeseaComenzar.SeleccionarObjeto("Devolucion CEDIS");
		Thread.sleep(3000);
		System.out.println("Se inicia el flujo de la transferencia mostrando la pantalla de Devoluciones CEDIS");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 4 Se selecciona la opción de Continuar en la pantalla de Devoluciones CEDIS una vez llenada la información correspondiente en la pantalla.	
		 * Resultado esperado: Se muestra la pantalla de Resumen Devolucion con los campos correspondientes
		 * 
		 */		
		addStep("Se selecciona la opción de Continuar en la pantalla de Devoluciones CEDIS una vez llenada la información correspondiente en la pantalla.	Resultado esperado: Se muestra la pantalla de Resumen Devolucion con los campos correspondientes");		
		System.out.println("Seleccionando BOL\n");
		Thread.sleep(1000);
		//POM_Devolucion.SeleccionarObjeto("Seleccionar Bol");
		//POM_Devolucion.SeleccionarBol("13360", "sin evidencia", "vacio");//Se comentariza para seleccionar el primer Bol
		POM_Devolucion.SeleccionarPrimerBol("Sin Evidencia", "");//agregado por Juan Ibarra
		System.out.println("Ingresando canastillas en Devolucion de contenedores\n");
		Thread.sleep(1000);
		POM_Devolucion.AumentarCanastilla(1, "Azul");
		POM_Devolucion.AumentarCanastilla(2, "Amarilla");
		POM_Devolucion.AumentarCanastilla(3, "Verde");
		POM_Devolucion.AumentarCanastilla(4, "Naranja");
		POM_Devolucion.AumentarCanastilla(5, "Otro");
		System.out.println("Ingresando contenedores en Devolucion de contenedores\n");
		Thread.sleep(1000);
		POM_Devolucion.AumentarContenedores(6, "Tapas");
		POM_Devolucion.AumentarContenedores(7, "Envases");
		POM_Devolucion.TomarFotoDevolucion();
		Thread.sleep(9000);		
		POM_Devolucion.MoverSeccion("Canastilla Devolucion");
		System.out.println("Ingresando canastillas con devolucion\n");
		Thread.sleep(8000);		
		POM_Devolucion.IngresarCanastilla("6544");
		Thread.sleep(5000);				
		POM_Devolucion.TomarFotoCanastilla();
		Thread.sleep(9900);
		POM_Devolucion.MoverSeccion("Continuar");
		Thread.sleep(3000);		
		System.out.println("Se finaliza la pantalla devoluciones a cedis\n");
		POM_Devolucion.SeleccionarObjeto("Continuar");
		Thread.sleep(4000);
		Resumen.ValidacionEncabezado();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(2000);
		System.out.println("Se muestra la pantalla de Resumen Devolucion con los campos correspondientes.");
		Thread.sleep(5000);
		Resumen.ClickFirmaAcuerdo("SinEvidencia", null);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
										
		/*Paso 5 Se selecciona la opción de Firma de Acuerdo en la pantalla de Resumen Devolucion 	Resultado esperado: Se muestra la pantalla de Acuerdo de Devolucion con los campos correspondientes donde el botón para finalizar el registro de las devoluciones ahora se renombrará a "TERMINAR Devolucion".
		 * 
		 */		
		addStep("Se selecciona la opción de Firma de Acuerdo en la pantalla de Resumen Devolucion 	Resultado esperado: Se muestra la pantalla de Acuerdo de Devolucion con los campos correspondientes donde el botón para finalizar el registro de las devoluciones ahora se renombrará a \"TERMINAR Devolucion\".");		
		AcuerdoDev.ValidacionEncabezado();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(4000);
		AcuerdoDev.FirmaAreaDistribucion("", "");
		Thread.sleep(2000);
		System.out.println("Firma Personal de Tienda\n");
		Thread.sleep(1000);
		AcuerdoDev.FirmaPersonalTienda("", "");
		Thread.sleep(2000);
		Runtime.getRuntime().exec("adb shell input swipe 20 1003 20 900 90");//
		Thread.sleep(2000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		System.out.println("Terminando Firmas de Acuerdo de Entrega\n");
		Thread.sleep(1000);
		AcuerdoDev.TerminarRecepcion("", "CEDIS_ConfirmarSeguroTerminarAcuerdo");
		Thread.sleep(1000);
		
		/*Paso 6 Se realiza la firma de Acuerdo de Devolucion y selecciona el botón de TERMINAR Devolucion	Resultado esperado: Se muestra la pantalla de Recepcion de Pedidos CEDIS en la sección de Recepcion Cajas / Canastillas
		 * 
		 */		
		addStep("Se realiza la firma de Acuerdo de Devolucion y selecciona el botón de TERMINAR Devolucion	Resultado esperado: Se muestra la pantalla de Recepcion de Pedidos CEDIS en la sección de Recepcion Cajas / Canastillas");		
		PedidoRecepcion.DeslizarArriba();
		Thread.sleep(2000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		System.out.println("Se muestra la pantalla de recepcion pedido cedis");
		PedidoRecepcion.MoverSeccion("Contenedor");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		PedidoRecepcion.MoverSeccion("Terminar");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 7 Se realiza el llenado de información de la sección de Recepcion Cajas / Canastillas para después seleccionar Terminar	Resultado esperado: Se muestra la sección de Recepcion Cajas sin etiqueta / Etiqueta dañada
		 * 
		 */		
		addStep(" Se realiza el llenado de información de la sección de Recepcion Cajas / Canastillas para después seleccionar Terminar	Resultado esperado: Se muestra la sección de Recepcion Cajas sin etiqueta / Etiqueta dañada");		
		System.out.println("Tomando las 3 evidencias obligatorias del pedido\n");
		Thread.sleep(2000);
		Runtime.getRuntime().exec("adb shell input swipe 100 500 100 1450 100");//
		PedidoRecepcion.Tomar3Fotos();
		System.out.println("Ingresando contenedores\n");
		Thread.sleep(2000);
		PedidoRecepcion.CapturaContenedor(data.get("contenedor"));
		System.out.println("Terminando Recepcion\n");
		Thread.sleep(2000);
		PedidoRecepcion.MoverSeccion("Terminar");
		Thread.sleep(2000);
		PedidoRecepcion.SeleccionarObjeto("Terminar");
		PedidoRecepcion.SeleccionarObjeto("Confirmar");
		PedidoRecepcion.SeleccionarObjeto("Si");
		Thread.sleep(2000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 8 Se realiza el llenado de información de la sección de Recepcion Cajas sin etiqueta / Etiqueta dañada para después  seleccionar Continuar 	Resultado esperado: Se muestra la sección de Sobrantes / Dañados
		 * 
		 */		
		addStep("Se realiza el llenado de información de la sección de Recepcion Cajas sin etiqueta / Etiqueta dañada para después  seleccionar Continuar 	Resultado esperado: Se muestra la sección de Sobrantes / Dañados");		
		Runtime.getRuntime().exec("adb shell input swipe 20 1165 20 900 90");// Se desliza un poco hacía abajo
		Thread.sleep(3000);
		PedidoRecepcion.RegistrarSinEtiqueta();
		System.out.println("Registrando datos de caja sin etiqueta\n");
		Runtime.getRuntime().exec("adb shell input swipe 21 1160 23 900 40");// Se desliza hacía abajo
		PedidoRecepcion.SeleccionarObjeto("Continuar a Faltantes");
		PedidoRecepcion.SeleccionarObjeto("Confirmar");
		System.out.println("Ingresando a apartado faltantes\n");
		Thread.sleep(2000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");	
		
		/*Paso 9 Se realiza el llenado de información de la sección de Sobrantes / Dañados para después  seleccionar Terminar	Resultado esperado: Se muestra la pantalla de Resumen Recepcion con la información correspondiente
		 * 
		 */		
		addStep("Se realiza el llenado de información de la sección de Sobrantes / Dañados para después  seleccionar Terminar	Resultado esperado: Se muestra la pantalla de Resumen Recepcion con la información correspondiente");		
		PedidoRecepcion.CapturaContenedorSobrantes("234561");
		System.out.println("Capturando faltante\n");
		PedidoRecepcion.SeleccionarObjeto("Continuar Dañados");
		System.out.println("Continuando a apartado dañados\n");
		PedidoRecepcion.SeleccionarArticulo(data.get("articulo"), "", "");
		System.out.println("Capturando articulo dañado\n");
		PedidoRecepcion.SeleccionarObjeto("Aumentar Dañado");
		PedidoRecepcion.SeleccionarObjeto("Terminar Dañados");
		Thread.sleep(3000);
		PedidoRecepcion.SeleccionarObjeto("Confirmar Dañados");
		System.out.println("Se finalizo con la captura de dañados y faltantes\n");
		Thread.sleep(3000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		ResumenCedis.TotalCajaSinEtiquetas();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		ResumenCedis.TotalDanados();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 10 Se observa la información correspondiente a la pantalla de Resumen  Recepcion y se selecciona el botón de Firma de Acuerdo	Resultado esperado: Se muestra la pantalla de Acuerdo de Entrega en la cual se muestra la información correspondiente y donde se mostrara el checkbox de "cumplimiento de proceso de entrega" al final de la pantalla, arriba del botón "TERMINAR Recepcion".
		 * 
		 */		
		addStep("Se observa la información correspondiente a la pantalla de Resumen  Recepcion y se selecciona el botón de Firma de Acuerdo	Resultado esperado: Se muestra la pantalla de Acuerdo de Entrega en la cual se muestra la información correspondiente y donde se mostrara el checkbox de \"cumplimiento de proceso de entrega\" al final de la pantalla, arriba del botón \"TERMINAR Recepcion\".");		
		ResumenCedis.SeleccionarObjeto("Firma Acuerdo");
		System.out.println("Se desplaza hacia el boton de firma de acuerdo\n");
		Thread.sleep(500);
		Acuerdo.ValidacionEncabezado();
		Thread.sleep(3000);
		System.out.println("Se ingresa a pantalla acuerdo de recepcion\n");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(4000);
		Acuerdo.FirmaAreaDistribucion("", "");
		Acuerdo.FirmaPersonalTienda("", "");
		System.out.println("Se ingresan firmas\n");
		Thread.sleep(4000);		
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
		Thread.sleep(2000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Acuerdo.SeleccionarObjeto("Terminar Recepcion");
		System.out.println("Se finaliza el proceso de firmas de acuerdo de entrega, se selecciona boton terminar recepcion");
		Acuerdo.SeleccionarObjeto("Confirmar");
		System.out.println("Se confirma mensaje de firmas de acuerdo");
		
		/*Paso 11 Se realiza la firma de Acuerdo de  Entrega y se selecciona el botón de TERMINAR Recepcion	Resultado esperado: Se muestra la pantalla de Formato Único de Entrega con sus campos correspondientes.
			Se muestran los botones de 
			-"MODIFICAR Recepcion"
			-"MODIFICAR DEVOLUCIONES" 
			-"TERMINAR"
		 */		
		addStep("Se realiza la firma de Acuerdo de  Entrega y se selecciona el botón de TERMINAR Recepcion	Resultado esperado: Se muestra la pantalla de Formato Único de Entrega con sus campos correspondientes.\r\n"
				+ "			Se muestran los botones de \r\n"
				+ "			-\"MODIFICAR Recepcion\"\r\n"
				+ "			-\"MODIFICAR DEVOLUCIONES\" \r\n"
				+ "			-\"TERMINAR\"");		
		FueRec.ValidacionEncabezado();
		Thread.sleep(3000);
		System.out.println("Se ingresa correctamente a la pantalla formato unico de entrega\n");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FueRec.BuscarSeccion("Botones");
		Thread.sleep(3000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		System.out.println("Se muestran los botones Modificar devoluciones, Modificar recepcion, y Terminar \n");
		
		/*Paso 12 Se cierre la aplicación de Oxxo al Dia, para después volverla a abrir	Resultado esperado: Se abre de nuevo la aplicación de Oxxo al día pidió el usuario y contraseña
		 * 
		 */		
		addStep("Se cierre la aplicación de Oxxo al Dia, para después volverla a abrir	Resultado esperado: Se abre de nuevo la aplicación de Oxxo al día pidió el usuario y contraseña");		
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));
		Thread.sleep(5000);
		Runtime.getRuntime().exec("adb shell am start " + data.get("appPackage"));
		Thread.sleep(5000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*Paso 13 Se loguea a la aplicación y se avanza hasta llegar de nuevo al listado de Transferencias por Recepcionar	Resultado esperado: Se muestra la pantalla Recepcion de Pedidos CEDIS con el listado de Transferencias asignadas a la tienda.
		 * 
		 */		
		addStep("Se loguea a la aplicación y se avanza hasta llegar de nuevo al listado de Transferencias por Recepcionar	Resultado esperado: Se muestra la pantalla Recepcion de Pedidos CEDIS con el listado de Transferencias asignadas a la tienda.");		
		loginLider.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));
		System.out.println("Dando clic en Excelencia Operativa -> HC y COMETA\n");
		Thread.sleep(2000);
		MenuOaD.logeoInicioHerramientas();
		System.out.println("Dando clic en Recepcion de Pedidos CEDIS\n");
		Thread.sleep(9000);
		ModulosOaD.clickBtnRecepcionPedidosCEDIS();
		System.out.println("Logeando Area de Distribucion CEDIS\n");
		LoginAreaDistribucion.LoginAreaDistribucion();
		Thread.sleep(2000);
		POM_ListadoDeRecepcion.ValidacionEncabezado();
		Thread.sleep(5000);
		Runtime.getRuntime().exec("adb shell input swipe 20 1003 20 900 90");//
		Thread.sleep(3000);
		System.out.println("Se muestra la pantalla Recepcion de Pedidos CEDIS con el listado de Transferencias asignadas a la tienda.");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 14 Se selecciona nuevamente la transferencia que se estaba atendiendo	Resultado esperado: "Se muestra la pantalla de Recepcion de Pedidos CEDIS con las nuevas opciones de flujo, mostrara:
			-Cabecera de pedido: Fecha, Ruta, Ruta Dinámica, CR Tienda, Nombre Tienda, Líder Tienda.
			Mostrara el Título de :""¿Como deseas comenzar?""
			Mostrara las Instrucciones de: ""Puedes comenzar escaneando las cajas y canastillas del pedido, o bien, registrando las devoluciones del pedido. Selecciona la opción deseada.""
			Muestra los botones de:
			-"Recepcion de pedido"
			-"Devoluciones a CEDIS"
		 * 
		 */		
		addStep("Se selecciona nuevamente la transferencia que se estaba atendiendo	Resultado esperado: \"Se muestra la pantalla de Recepcion de Pedidos CEDIS con las nuevas opciones de flujo, mostrara:\r\n"
				+ "			-Cabecera de pedido: Fecha, Ruta, Ruta Dinámica, CR Tienda, Nombre Tienda, Líder Tienda.\r\n"
				+ "			Mostrara el Título de :\"\"¿Como deseas comenzar?\"\"\r\n"
				+ "			Mostrara las Instrucciones de: \"\"Puedes comenzar escaneando las cajas y canastillas del pedido, o bien, registrando las devoluciones del pedido. Selecciona la opción deseada.\"\"\r\n"
				+ "			Muestra los botones de:\r\n"
				+ "			-\"Recepcion de pedido\"\r\n"
				+ "			-\"Devoluciones a CEDIS\"");		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		System.out.println("Entrando a la primera transferencia por Recepcionar de CEDIS\n");
		Thread.sleep(2000);
		POM_ListadoDeRecepcion.BuscarTransferencia(true,data.get("numTransferencia"));
		POM_DeseaComenzar.Cargando();
		POM_DeseaComenzar.ValidacionEncabezado();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(3000);
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
		Thread.sleep(3000);
		System.out.println("Se muestra correctamente la pantalla de Recepcion de Pedidos CEDIS  ¿Como deseas comenzar?");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 15 Se selecciona la opción de Devoluciones a CEDIS	Resultado esperado: Se inicia el flujo de la transferencia mostrando la pantalla de Devoluciones CEDIS la cual no contara con la información capturada previamente ya que no se permite el autoguardado en esta pantalla
		 * 
		 */		
		addStep("Se selecciona la opción de Devoluciones a CEDIS	Resultado esperado: Se inicia el flujo de la transferencia mostrando la pantalla de Devoluciones CEDIS la cual no contara con la información capturada previamente ya que no se permite el autoguardado en esta pantalla");		
		POM_DeseaComenzar.SeleccionarObjeto("Devolucion CEDIS");
		Thread.sleep(3000);
		System.out.println("Se inicia el flujo de la transferencia mostrando la pantalla de Devoluciones CEDIS");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		POM_Devolucion.MoverSeccion("Canastillas");
		Thread.sleep(3000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		POM_Devolucion.MoverSeccion("Contenedores");
		Thread.sleep(3000);
		POM_Devolucion.MoverSeccion("Canastilla Devolucion");
		Thread.sleep(3000);
		POM_Devolucion.MoverSeccion("Continuar");
		Thread.sleep(3000);
		POM_Devolucion.SeleccionarObjeto("Continuar");
		POM_Devolucion.SeleccionarObjeto("Confirmar");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
				
		/*Paso 16 Se selecciona la opción de Continuar en la pantalla de Devoluciones CEDIS una vez llenada la información correspondiente en la pantalla.	Resultado esperado: Se muestra la pantalla de Resumen Devolucion con los campos correspondientes
		 * 
		 */		
		addStep("Se selecciona la opción de Continuar en la pantalla de Devoluciones CEDIS una vez llenada la información correspondiente en la pantalla.	Resultado esperado: Se muestra la pantalla de Resumen Devolucion con los campos correspondientes");		
		Resumen.ValidacionEncabezado();
		Thread.sleep(6000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		System.out.println("Se muestra la pantalla de Resumen Devolucion con los campos correspondientes.");
		Thread.sleep(5000);
		Resumen.ClickFirmaAcuerdo("SinEvidencia", null);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 17 Se selecciona la opción de Firma de Acuerdo en la pantalla de Resumen Devolucion 	Resultado esperado: Se muestra la pantalla de Acuerdo de Devolucion con los campos correspondientes donde el botón para finalizar el registro de las devoluciones ahora se renombrará a "TERMINAR Devolucion".
		 * 
		 */		
		addStep("Se selecciona la opción de Firma de Acuerdo en la pantalla de Resumen Devolucion 	Resultado esperado: Se muestra la pantalla de Acuerdo de Devolucion con los campos correspondientes donde el botón para finalizar el registro de las devoluciones ahora se renombrará a \"TERMINAR Devolucion\".");		
		AcuerdoDev.ValidacionEncabezado();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(4000);
		AcuerdoDev.FirmaAreaDistribucion("", "");
		Thread.sleep(2000);
		System.out.println("Firma Personal de Tienda\n");
		Thread.sleep(1000);
		AcuerdoDev.FirmaPersonalTienda("", "");
		Thread.sleep(2000);
		Runtime.getRuntime().exec("adb shell input swipe 20 1003 20 900 90");//
		Thread.sleep(2000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		System.out.println("Terminando Firmas de Acuerdo de Entrega\n");
		Thread.sleep(1000);
		AcuerdoDev.TerminarRecepcion("", "CEDIS_ConfirmarSeguroTerminarAcuerdo");
		Thread.sleep(1000);
				
		/*Paso 18 Se realiza la firma de Acuerdo de Devolucion y selecciona el botón de TERMINAR Devolucion	Resultado esperado: Se muestra la pantalla de Recepcion de Pedidos CEDIS en la sección de Recepcion Cajas / Canastillas en la cual se muestra la información correspondiente a la sección de Recepcion Cajas / Canastillas , Recepcion Cajas sin etiqueta / Etiqueta dañada y Sobrantes / Dañados previamente capturada.
		 * 
		 */		
		addStep("Se realiza la firma de Acuerdo de Devolucion y selecciona el botón de TERMINAR Devolucion	Resultado esperado: Se muestra la pantalla de Recepcion de Pedidos CEDIS en la sección de Recepcion Cajas / Canastillas en la cual se muestra la información correspondiente a la sección de Recepcion Cajas / Canastillas , Recepcion Cajas sin etiqueta / Etiqueta dañada y Sobrantes / Dañados previamente capturada.");		
		Runtime.getRuntime().exec("adb shell input swipe 100 500 100 1450 100");//
		Thread.sleep(3000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		PedidoRecepcion.MoverSeccion("Contenedor");
		Thread.sleep(3000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		PedidoRecepcion.MoverSeccion("Sobrantes");
		Thread.sleep(3000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		PedidoRecepcion.MoverSeccion("Terminar");
		Thread.sleep(3000);
		PedidoRecepcion.SeleccionarObjeto("Terminar Dañados");
		PedidoRecepcion.SeleccionarObjeto("Confirmar");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 19 Se confirma que se muestre la misma información ya cargada para después  seleccionar Terminar	Resultado esperado: Se muestra la pantalla de Resumen Recepcion con la información correspondiente
		 * 
		 */		
		addStep("Se confirma que se muestre la misma información ya cargada para después  seleccionar Terminar	Resultado esperado: Se muestra la pantalla de Resumen Recepcion con la información correspondiente");		
		ResumenCedis.ValidacionEncabezado();
		Thread.sleep(6000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		ResumenCedis.TotalCajaSinEtiquetas();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		ResumenCedis.TotalDanados();
		System.out.println("Se muestra correctamente la informacion en la pantalla resumen recepcion\n");
		ResumenCedis.SeleccionarObjeto("Firma Acuerdo");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 20 Se observa la información correspondiente a la pantalla de Resumen  Recepcion y se selecciona el botón de Firma de Acuerdo	Resultado esperado: Se muestra la pantalla de Acuerdo de Entrega en la cual se pide que realice de nuevo la firma de acuerdo
		 * 
		 */		
		addStep("Se observa la información correspondiente a la pantalla de Resumen  Recepcion y se selecciona el botón de Firma de Acuerdo	Resultado esperado: Se muestra la pantalla de Acuerdo de Entrega en la cual se pide que realice de nuevo la firma de acuerdo");		
		Acuerdo.ValidacionEncabezado();
		Thread.sleep(5000);
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");//
		Thread.sleep(3000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Runtime.getRuntime().exec("adb shell input swipe 20 1003 20 900 90");
		Thread.sleep(3000);
		System.out.println("Se muestran las firmas en blanco para ingresarlas nuevamente\n");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
	}


	@Override		
	public String setTestFullName() {
		return "ATC_FT_209_ValidacionDeAutoguardadoEnLaSeccionDeResumenRecepcionYFirmaAcuerdoPorFlujoDeDevolucionesCEDIS";
	}

	@Override
	public String setTestDescription() {
		return "Caso de prueba en el que se valida que al retomar la transferencia que fue iniciada por Devoluciones CEDIS solicite de nuevo la información correspondiente a la sección de Resumen Recepcion y Firma Acuerdo. ";
	}

	@Override
	public String setTestDesigner() {
		return "Ricardo Hernandez Rios";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "*Contar con Transferencias por atender en la handheld\r\n"
				+ "*Haber iniciado el flujo de la transferencia con la opción de Devoluciones a CEDIS";
	}

	@Override
	public void beforeTest() {
	}

}