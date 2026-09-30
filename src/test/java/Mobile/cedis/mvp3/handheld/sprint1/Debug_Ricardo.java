package Mobile.cedis.mvp3.handheld.sprint1;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.cedis.om.B07_ResumenDevolucion;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

public class Debug_Ricardo extends BaseExecution {
	private AppiumUtil appiumUtil;
	@Test(dataProvider = "data-provider", enabled = true)
	
	public void Debug_Ricardo_test(HashMap<String, String> data) throws Exception   {
	
		/*******************************
		 * Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script
		 ********************/
		DesiredCapabilities cap = new DesiredCapabilities();

		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appium:appPackage", data.get("appPackageP"));
		cap.setCapability("appium:appActivity", data.get("appActivityP"));
		cap.setCapability("ip", data.get("ip"));
		cap.setCapability("userLogin", data.get("userLogin"));
		cap.setCapability("passwordLogin", data.get("passwordLogin"));
		cap.setCapability("numTransferencia", data.get("numTransferencia"));
		cap.setCapability("contenedor", data.get("contenedor"));
		cap.setCapability("articulo", data.get("articulo"));

		String url = "http://127.0.0.1:4723/";

		// Cerrar Apps
		Runtime.getRuntime().exec("adb shell pm clear " + data.get("appPackage"));

		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}

		// Abre la aplicación
		/*Runtime.getRuntime().exec("adb shell am start " + data.get("appPackage"));
		  // Ejecuta el logeo hacia el punto de venta
				Mobile.cedis.om.A01_Login_Punto_De_Venta login = new Mobile.cedis.om.A01_Login_Punto_De_Venta(testCase, appiumUtil, this);
				login.loginPV(data.get("ip"));

		// Ejecuta el logeo del lider
				Mobile.cedis.om.A02_Login_Lider loginLider = new Mobile.cedis.om.A02_Login_Lider(testCase, appiumUtil, this);
				// Obtiene valor del DataProvaider
				loginLider.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));

				
				
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
				LoginAreaDistribucion.LoginAreaDistribucion();
				Thread.sleep(2000);*/

				//OM A utilizar
				B07_ResumenDevolucion Resumen = new B07_ResumenDevolucion(testCase, appiumUtil, this);

		
				/* Paso 1 . Se capturan los datos correspondientes en la pantalla de logueo de Recepción de PEDIDOS CEDIS  dando clic en Entrar	
				   Resultado: Se muestra la pantalla Recepción de Pedidos CEDIS con el listado de Transferencias asignadas a la tienda.
				 
				addStep("Se capturan los datos correspondientes en la pantalla de logueo de Recepción de PEDIDOS CEDIS  dando clic en Entrar	\r\n"
						+ "		   Resultado: Se muestra la pantalla Recepción de Pedidos CEDIS con el listado de Transferencias asignadas a la tienda.");
				POM_ListadoDeRecepcion.ValidacionEncabezado();
				Thread.sleep(5000);
				Runtime.getRuntime().exec("adb shell input swipe 20 1003 20 900 90");//
				Thread.sleep(3000);
				System.out.println("Se muestra la pantalla Recepción de Pedidos CEDIS con el listado de Transferencias asignadas a la tienda.");
				testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

				/* Paso 2 Se selecciona una transferencia que no ha sido atendida por el usuario.
				   Resultado:Se muestra la pantalla de Recepción de Pedidos CEDIS con las nuevas opciones de flujo, mostrara:-Cabecera de pedido: Fecha, Ruta, Ruta Dinámica, CR Tienda, Nombre Tienda, Líder Tienda.
					Mostrara el Título de :"¿Como deseas comenzar?"
					Mostrara las Instrucciones de: "Puedes comenzar escaneando las cajas y canastillas del pedido, o bien, registrando las devoluciones del pedido. Selecciona la opción deseada."
					Muestra los botones de:
					-"Recepción de pedido"
					-"Devoluciones a CEDIS"
				 
				addStep("Se selecciona una transferencia que no ha sido atendida por el usuario.\r\n"
						+ "		   Resultado:Se muestra la pantalla de Recepción de Pedidos CEDIS con las nuevas opciones de flujo, mostrara:-Cabecera de pedido: Fecha, Ruta, Ruta Dinámica, CR Tienda, Nombre Tienda, Líder Tienda.\r\n"
						+ "			Mostrara el Título de :\"¿Como deseas comenzar?\"\r\n"
						+ "			Mostrara las Instrucciones de: \"Puedes comenzar escaneando las cajas y canastillas del pedido, o bien, registrando las devoluciones del pedido. Selecciona la opción deseada.\"\r\n"
						+ "			Muestra los botones de:\r\n"
						+ "			-\"Recepción de pedido\"\r\n"
						+ "			-\"Devoluciones a CEDIS\"");

				System.out.println("Entrando a la primera transferencia por Recepcionar de CEDIS\n");
				Thread.sleep(2000);
				POM_ListadoDeRecepcion.BuscarTransferencia(true,data.get("numTransferencia"));
				POM_DeseaComenzar.Cargando();
				POM_DeseaComenzar.ValidacionEncabezado();
				testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
				Thread.sleep(3000);
				Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
				Thread.sleep(3000);
				System.out.println("Se muestra correctamente la pantalla de Recepción de Pedidos CEDIS  ¿Como deseas comenzar?");
				testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		

				/* Paso 3 Se selecciona la opción de Devoluciones a CEDIS.
				  Resultado: Se inicia el flujo de la transferencia mostrando la pantalla de Devoluciones CEDIS
				 
				addStep("Se selecciona la opción de Devoluciones a CEDIS.\r\n"
						+ "		  Resultado: Se inicia el flujo de la transferencia mostrando la pantalla de Devoluciones CEDIS");
				POM_DeseaComenzar.SeleccionarObjeto("Devolucion CEDIS");
				Thread.sleep(3000);
				System.out.println("Se inicia el flujo de la transferencia mostrando la pantalla de Devoluciones CEDIS");
				testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
				/* Paso 4 Se selecciona la opción de Continuar en la pantalla de Devoluciones CEDIS una vez llenada la información correspondiente en la pantalla.	
				   Resultado: Se muestra la pantalla de Resumen Devolución con los campos correspondientes
				 
				addStep("Se selecciona la opción de Continuar en la pantalla de Devoluciones CEDIS una vez llenada la información correspondiente en la pantalla.	\r\n"
						+ "		   Resultado: Se muestra la pantalla de Resumen Devolución con los campos correspondientes");
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
				Thread.sleep(5000);		
				POM_Devolucion.MoverSeccion("Canastilla Devolucion");
				System.out.println("Ingresando canastillas con devolucion\n");
				Thread.sleep(10000);		
				POM_Devolucion.IngresarCanastilla("6544");
				Thread.sleep(8000);
				POM_Devolucion.TomarFotoCanastilla();
				Thread.sleep(9000);
				POM_Devolucion.MoverSeccion("Continuar");
				Thread.sleep(3000);		
				System.out.println("Se finaliza la pantalla devoluciones a cedis\n");
				POM_Devolucion.SeleccionarObjeto("Continuar");
				Thread.sleep(4000);
				Resumen.ValidacionEncabezado();
				testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
				Thread.sleep(2000);
				System.out.println("Se muestra la pantalla de Resumen Devolución con los campos correspondientes.");
				Thread.sleep(5000);
				Resumen.ClickFirmaAcuerdo("SinEvidencia", null);
				testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

				/* Paso 5 Se selecciona la opción de Firma de Acuerdo en la pantalla de Resumen Devolución 	
				   Resultado: Se muestra la pantalla de Acuerdo de Devolución con los campos correspondientes donde el botón para finalizar el registro de las devoluciones ahora se renombrará a "TERMINAR DEVOLUCIÓN".
				 
				addStep("Se selecciona la opción de Firma de Acuerdo en la pantalla de Resumen Devolución 	\r\n"
						+ "		   Resultado: Se muestra la pantalla de Acuerdo de Devolución con los campos correspondientes donde el botón para finalizar el registro de las devoluciones ahora se renombrará a \"TERMINAR DEVOLUCIÓN\".");
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

				/* Paso 6 Se realiza la firma de Acuerdo de Devolución y selecciona el botón de TERMINAR DEVOLUCIÓN	
				   Resultado: Se muestra la pantalla de Recepción de Pedidos CEDIS en la sección de Recepción Cajas / Canastillas
				 
				addStep("Se realiza la firma de Acuerdo de Devolución y selecciona el botón de TERMINAR DEVOLUCIÓN	\r\n"
						+ "		   Resultado: Se muestra la pantalla de Recepción de Pedidos CEDIS en la sección de Recepción Cajas / Canastillas");
				PedidoRecepcion.DeslizarArriba();
				Thread.sleep(2000);
				testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
				System.out.println("Se muestra la pantalla de recepcion pedido cedis");
				PedidoRecepcion.MoverSeccion("Contenedor");
				testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
				PedidoRecepcion.MoverSeccion("Terminar");
				testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

				/* Paso 7 .Se realiza el llenado de información de la sección de Recepción Cajas / Canastillas para después seleccionar Terminar	
				 * Resultado: Se muestra la sección de Recepción Cajas sin etiqueta / Etiqueta dañada
				 
				
				addStep("Se realiza el llenado de información de la sección de Recepción Cajas / Canastillas para después seleccionar Terminar	Resultado: Se muestra la sección de Recepción Cajas sin etiqueta / Etiqueta dañada");
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
				

				/* Paso 8 Se realiza el llenado de información de la sección de Recepción Cajas sin etiqueta / Etiqueta dañada para después  seleccionar Continuar 	
				 * Resultado: Se muestra la sección de Sobrantes / Dañados
				 
				
				addStep("Se realiza el llenado de información de la sección de Recepción Cajas sin etiqueta / Etiqueta dañada para después  seleccionar Continuar Resultado: Se muestra la sección de Sobrantes / Dañados");
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
		

				/* Paso 9 Se realiza el llenado de información de la sección de Sobrantes / Dañados para después  seleccionar Terminar	
				 * Resultado: Se muestra la pantalla de Resumen Recepción con la información correspondiente
				 
				
				addStep("Se realiza el llenado de información de la sección de Sobrantes / Dañados para después  seleccionar Terminar	Resultado: Se muestra la pantalla de Resumen Recepción con la información correspondiente");
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
				Thread.sleep(3000);				
				testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
				ResumenCedis.TotalSobrantes();								
				Thread.sleep(3000);
				testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

				/* Paso 10 Se cierre la aplicación de Oxxo al Dia, para después volverla a abrir	
				   Resultado: Se abre de nuevo la aplicación de Oxxo al día pidió el usuario y contraseña
				 
				addStep("Se cierre la aplicación de Oxxo al Dia, para después volverla a abrir	\r\n"
						+ "		   Resultado: Se abre de nuevo la aplicación de Oxxo al día pidió el usuario y contraseña");
				Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));
				Thread.sleep(5000);
				Runtime.getRuntime().exec("adb shell am start " + data.get("appPackage"));
				Thread.sleep(6000);
				System.out.println("Se abre de nuevo la aplicación de Oxxo al día pidió el usuario y contraseña");
				testCase.addScreenShotCurrentStep(appiumUtil, "testImage");


				/* Paso 11 Se loguea a la aplicación y se avanza hasta llegar de nuevo al listado de Transferencias por Recepcionar	
				   Resultado: Se muestra la pantalla Recepción de Pedidos CEDIS con el listado de Transferencias asignadas a la tienda.
				
				addStep("Se loguea a la aplicación y se avanza hasta llegar de nuevo al listado de Transferencias por Recepcionar	\r\n"
						+ "		   Resultado: Se muestra la pantalla Recepción de Pedidos CEDIS con el listado de Transferencias asignadas a la tienda.");
				loginLider.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));
				System.out.println("Dando clic en Excelencia Operativa -> HC y COMETA\n");
				Thread.sleep(2000);
				MenuOaD.logeoInicioHerramientas();
				System.out.println("Dando clic en Recepcion de Pedidos CEDIS\n");
				Thread.sleep(2000);
				ModulosOaD.clickBtnRecepcionPedidosCEDIS();
				System.out.println("Logeando Area de Distribucion CEDIS\n");
				LoginAreaDistribucion.LoginAreaDistribucion();
				Thread.sleep(2000);
				POM_ListadoDeRecepcion.ValidacionEncabezado();
				Thread.sleep(5000);
				Runtime.getRuntime().exec("adb shell input swipe 20 1003 20 900 90");//
				Thread.sleep(3000);
				System.out.println("Se muestra la pantalla Recepción de Pedidos CEDIS con el listado de Transferencias asignadas a la tienda.");
				testCase.addScreenShotCurrentStep(appiumUtil, "testImage");


				/* Paso 12 .Se selecciona nuevamente la transferencia que se estaba atendiendo	
				   Resultado:  Resultado: Se muestra la pantalla de Recepción de Pedidos CEDIS con las nuevas opciones de flujo, mostrara:-Cabecera de pedido: Fecha, Ruta, Ruta Dinámica, CR Tienda, Nombre Tienda, Líder Tienda.
					Mostrara el Título de :"¿Como deseas comenzar?"
					Mostrara las Instrucciones de: "Puedes comenzar escaneando las cajas y canastillas del pedido, o bien, registrando las devoluciones del pedido. Selecciona la opción deseada."
					Muestra los botones de:
					-"Recepción de pedido"
					-"Devoluciones a CEDIS"
				 
				addStep("Se selecciona nuevamente la transferencia que se estaba atendiendo	\r\n"
						+ "		   Resultado:  Resultado: Se muestra la pantalla de Recepción de Pedidos CEDIS con las nuevas opciones de flujo, mostrara:-Cabecera de pedido: Fecha, Ruta, Ruta Dinámica, CR Tienda, Nombre Tienda, Líder Tienda.\r\n"
						+ "			Mostrara el Título de :\"¿Como deseas comenzar?\"\r\n"
						+ "			Mostrara las Instrucciones de: \"Puedes comenzar escaneando las cajas y canastillas del pedido, o bien, registrando las devoluciones del pedido. Selecciona la opción deseada.\"\r\n"
						+ "			Muestra los botones de:\r\n"
						+ "			-\"Recepción de pedido\"\r\n"
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
				System.out.println("Se muestra correctamente la pantalla de Recepción de Pedidos CEDIS  ¿Como deseas comenzar?");
				testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		/*
		 * Paso 13 Se selecciona la opción de Devoluciones a CEDIS Resultado: Se inicia el flujo de la transferencia mostrando la pantalla de Devoluciones CEDIS la cual no contara con la información capturada previamente ya que no se permite el autoguardado en esta pantalla
		 
				addStep("Se selecciona la opción de Devoluciones a CEDIS Resultado: Se inicia el flujo de la transferencia mostrando la pantalla de Devoluciones CEDIS la cual no contara con la información capturada previamente ya que no se permite el autoguardado en esta pantalla");
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
				Thread.sleep(4000);	
			    testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
				POM_Devolucion.SeleccionarObjeto("Continuar");
				POM_Devolucion.SeleccionarObjeto("Confirmar");
	
		/*
		 * Paso 14 Se selecciona la opción de Continuar en la pantalla de Devoluciones CEDIS una vez llenada la información correspondiente en la pantalla.	Resultado: Se muestra la pantalla de Resumen Devolución con los campos correspondientes
		 */
		
		addStep("Se selecciona la opción de Continuar en la pantalla de Devoluciones CEDIS una vez llenada la información correspondiente en la pantalla.	Resultado: Se muestra la pantalla de Resumen Devolución con los campos correspondientes");
		Resumen.ValidacionEncabezado();
		Thread.sleep(8000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		System.out.println("Se muestra la pantalla de Resumen Devolución con los campos correspondientes.");
		Thread.sleep(5000);
		Resumen.ClickFirmaAcuerdo("SinEvidencia", null);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");


			
	
	}
   
    
	/*
	  CEDIS MVP3: MTC-FT-008/ATC_FT_008_HandHeld_Acreditacion_Mensaje_al_seleccionar_un_SKU_de_un_caduco_del_listado_filtrado
	  Desc: Validar que al seleccionar un SKU del listado se muestre un mensaje de confirmación 
	  @autor: Ricardo Hdz
	  @date: 08/13/2024
	 */


	@Override
	public String setTestFullName() {
		return "ATC_FT_208_ValidacionDeAutoguardadoEnLaSeccionDeLasPantallasDeRecepcionPorFlujoDeDevolucionesCEDIS";
	}

	@Override
	public String setTestDescription() {
		return "Caso de prueba en el que se valida que al retomar la transferencia que fue iniciada por Devoluciones CEDIS permanezca con la información correspondiente a la sección de Recepción Cajas / Canastillas , Recepción Cajas sin etiqueta / Etiqueta dañada y Sobrantes / Dañados ";
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
		return "1. *Contar con Transferencias por atender en la handheld\r\n"
				+ "2. *Haber iniciado el flujo de la transferencia con la opción de Devoluciones a CEDIS ";
	}

	@Override
	public void beforeTest() {
	}
}
