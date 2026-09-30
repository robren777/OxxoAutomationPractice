package Mobile.cedis.integrales;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_218_ValidacionGeneralDelBotonCONSULTAFUEDIGITAL extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_218_ValidacionGeneralDelBotonCONSULTAFUEDIGITAL_test(
			HashMap<String, String> data) throws Exception {

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
		Runtime.getRuntime().exec("adb shell am start " + data.get("appPackage"));

		// Ejecuta el logeo hacia el punto de venta
		Mobile.cedis.om.A01_Login_Punto_De_Venta login = new Mobile.cedis.om.A01_Login_Punto_De_Venta(testCase, appiumUtil, this);

		login.loginPV(data.get("ip"));

		// Ejecuta el logeo del lider
		Mobile.cedis.om.A02_Login_Lider loginLider = new Mobile.cedis.om.A02_Login_Lider(testCase, appiumUtil, this);
		// Obtiene valor del DataProvaider
		loginLider.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));
				
		
		/*Paso 1.-Ingresar al menu principal de Oxxo al dia
		*Resultado Esperado: Se ingresa correctamente al menu principal
		*/
		addStep("Ingresar al menu principal de Oxxo al dia\r\n"
				+ "Resultado Esperado: Se ingresa correctamente al menu principal");		
		Mobile.cedis.om.A03_Menus_y_submenus herramienta = new Mobile.cedis.om.A03_Menus_y_submenus(testCase, appiumUtil, this);
		herramienta.Cargando();		
		herramienta.ValidacionPantallaMenuPrincipal();
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*Paso 2.-Seleccionar el icono de Excelencia Operativa
		*Resultado Esperado: Se despliega la pantalla de Excelencia Operativa
		*/
		addStep("Seleccionar el icono de Excelencia Operativa\r\n"
				+ "Resultado Esperado: Se despliega la pantalla de Excelencia Operativa");
		herramienta.SeleccionarObjeto("Excelencia");
		Thread.sleep(1000);
		herramienta.ValidacionPantallaExcelenciaOperativa();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		

		/*Paso 3.-Seleccionar el icono de Herramienta de Compra
		*Resultado Esperado: Se despliega el submenu de la pantalla de HERRAMIENTA DE COMPRA
		*/
		addStep("Seleccionar el icono de Herramienta de Compra\r\n"
				+ "Resultado Esperado: Se despliega el submenu de la pantalla de HERRAMIENTA DE COMPRA");		
		herramienta.SeleccionarObjeto("Herramienta");		
		Thread.sleep(1000);
		herramienta.ValidacionPantallaHerramientaCompra();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		
		/*Paso 4.-Seleccionar el icono de Cometa
		*Resultado Esperado: Se despliega la pantalla de Herramientas
		*/
		addStep("Seleccionar el icono de Cometa\r\n"
				+ "Resultado Esperado: Se despliega la pantalla de Herramientas");
		herramienta.SeleccionarObjeto("Cometa");		
		Mobile.cedis.om.A04_MenuHerramientas menuCedis = new Mobile.cedis.om.A04_MenuHerramientas(testCase, appiumUtil, this);		
		Thread.sleep(1000);
		menuCedis.ValidacionPantallaHerramientas();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		menuCedis.clickBtnRecepcionPedidosCEDIS();// Selecciona la opcion de Recepción de Pedidos CCEDIS
		
		//Se realiza el logeo en la recepción para que no se presente del defecto productivo de donde marca error de Token 
		Mobile.cedis.om.A05_LoginAreaDistribucion loginRuta = new Mobile.cedis.om.A05_LoginAreaDistribucion(testCase, appiumUtil, this);
		loginRuta.LoginAreaDistribucion();//Se logea en Area de Distribución sin tomar evidencia
		Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia transferencia = new Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia(testCase, appiumUtil, this);
		Thread.sleep(1000);
		transferencia.ValidacionEncabezado();		
		Runtime.getRuntime().exec("adb shell input keyevent 4");//Se raliza un regreso a la pantalla anterior
		Thread.sleep(500);
		Runtime.getRuntime().exec("adb shell input keyevent 4");//Se raliza un regreso a la pantalla anterior

		/*Paso 5.-Validar que la opcion Consulta Tiras de entrega (3) se haya rrealizado el renombramiento correcto del titulo y el nombre sea "Consulta FUE Digital"
		*Resultado Esperado: ? La opcion "Consultar Tiras de Entrega" se muestra correctamente renombrada a "Consulta FUE Digital"
		*/
		addStep("Validar que la opcion Consulta Tiras de entrega (3) se haya rrealizado el renombramiento correcto del titulo y el nombre sea \"Consulta FUE Digital\"\r\n"
				+ "Resultado Esperado: ? La opcion \"Consultar Tiras de Entrega\" se muestra correctamente renombrada a \"Consulta FUE Digital\"");
		menuCedis.ValidacionLeyendaConsultaFUEDigital();
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");		
		

		/*Paso 6.-Seleccionar la opcion que lleva por nombre "Consulta FUE Digital"
		*Resultado Esperado: Se ingresa a la pantalla "Consulta FUE Digital", y se muestra correctamente el contenido del encabezado con los siguientes datos:
		*Imagen de una tienda OXXO.
		*Fecha.
		*CR Tienda.
		*Nombre tienda.
		*/
		addStep("Seleccionar la opcion que lleva por nombre \"Consulta FUE Digital\"\r\n"
				+ "Resultado Esperado: Se ingresa a la pantalla \"Consulta FUE Digital\", y se muestra correctamente el contenido del encabezado con los siguientes datos:\r\n"
				+ "*Imagen de una tienda OXXO.\r\n"
				+ "*Fecha.\r\n"
				+ "*CR Tienda.\r\n"
				+ "*Nombre tienda.");
		menuCedis.clickBtnConsultaFUEDigital();
		Mobile.cedis.om.E01_ConsultaFUE FUEDigital = new Mobile.cedis.om.E01_ConsultaFUE(testCase, appiumUtil, this);
		Thread.sleep(1500);
		FUEDigital.Cargando();
		FUEDigital.ValidacionEncabezado();
		FUEDigital.ValidacionCampos();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");	

		/*Paso 7.-Seleccionar el radio button de Tranferencia y busca un número de transferencia que sea válida
		*Resultado Esperado: Se habilita el apartado para el ingreso del numero de transferencia a buscar y Se despliega correctamente la transferencia la cual fue realizada su busqueda al haber ingresado el  numero de la misma.
		*/
		addStep("Seleccionar el radio button de Tranferencia y busca un número de transferencia que sea válida\r\n"
				+ "Resultado Esperado: Se habilita el apartado para el ingreso del numero de transferencia a buscar y Se despliega correctamente la transferencia la cual fue realizada su busqueda al haber ingresado el  numero de la misma.");
		FUEDigital.BusquedaTransferencia(FUEDigital.BuscarPrimeraTransferencia());
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");// Se desliza hacía abajo		
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
				

		/*Paso 8.-Seleccionar el radio button de Fecha y busca la fecha de una transferencia que sea válida
		*Resultado Esperado: Se habilita el apartado para el ingreso de fecha  de la transferencia a buscar y se despliega correctamente la transferencia la cual fue realizada su busqueda por la fecha de la misma.
		*/
		addStep("Seleccionar el radio button de Fecha y busca la fecha de una transferencia que sea válida\r\n"
				+ "Resultado Esperado: Se habilita el apartado para el ingreso de fecha  de la transferencia a buscar y se despliega correctamente la transferencia la cual fue realizada su busqueda por la fecha de la misma.");
		FUEDigital.SeleccionarObjeto("Radio Fecha");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(1000);
		FUEDigital.IngresoFecha();
		Thread.sleep(1000);
		FUEDigital.ValidarIngresoFecha();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*Paso 9.-Seleccionar el radio button de Fecha y busca la fecha de una transferencia que sea invalida (incorrecta)
		*Resultado Esperado: Se habilita el apartado para el ingreso de fecha  de la transferencia a buscar y Se despliega correctamente el mensaje: No se encontro un pedido en la fecha previamente consultada ACEPTAR.
		*/
		addStep("Seleccionar el radio button de Fecha y busca la fecha de una transferencia que sea invalida (incorrecta)\r\n"
				+ "Resultado Esperado: Se habilita el apartado para el ingreso de fecha  de la transferencia a buscar y Se despliega correctamente el mensaje: No se encontro un pedido en la fecha previamente consultada ACEPTAR.");
		FUEDigital.ValidarDespliegueCalendario();
		Thread.sleep(1000);
		FUEDigital.SeleccionarObjeto("Calendario");
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(1000);
		FUEDigital.IngresarFechaPosterior();
		Thread.sleep(1000);
		FUEDigital.SeleccionarObjeto("Aceptar Fecha");
		Thread.sleep(1000);
		FUEDigital.ValidarMensajeFechaIncorrecta();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(1000);	

		/*Paso 10.-Seleccionar el radio button de Transferencia y busca un número de transferencia que no sea válida
		*Resultado Esperado: Se habilita el apartado para el ingreso del numero de transferencia a buscar y se despliega correctamente el mensaje: No se encontro un pedido en la fecha previamente consultada ACEPTAR.
		*/
		addStep("Seleccionar el radio button de Transferencia y busca un número de transferencia que no sea válida\r\n"
				+ "Resultado Esperado: Se habilita el apartado para el ingreso del numero de transferencia a buscar y se despliega correctamente el mensaje: No se encontro un pedido en la fecha previamente consultada ACEPTAR.");
		FUEDigital.SeleccionarObjeto("Aceptar Mensaje");
		Thread.sleep(1000);
		FUEDigital.SeleccionarObjeto("Radio Transferencia");
		Thread.sleep(1000);
		FUEDigital.IngresoTransferenciaIncorrecta();
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(1000);
		FUEDigital.SeleccionarObjeto("Buscar");
		Thread.sleep(1000);
		FUEDigital.ValidarMensajeTransferenciaIncorrecta();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUEDigital.SeleccionarObjeto("Aceptar Mensaje");
		Thread.sleep(1000);

		/*Paso 11.-Validar que las columnas se muestren de manera correcta (TRANSFERENCIA por lado izquierdo y RECEPCIÓN por lado derecho)
		*Resultado Esperado: Se ingresa a la pantalla "Consulta FUE Digital", y se muestra las columnas: "Transferencia" lado izq, "Recepcion" lado derecho.
		*/
		addStep("Validar que las columnas se muestren de manera correcta (TRANSFERENCIA por lado izquierdo y RECEPCIÓN por lado derecho)\r\n"
				+ "Resultado Esperado: Se ingresa a la pantalla \"Consulta FUE Digital\", y se muestra las columnas: \"Transferencia\" lado izq, \"Recepcion\" lado derecho.");
		Runtime.getRuntime().exec("adb shell input keyevent 4"); // Realiza la acción de Back en la handheld
		Thread.sleep(1000);
		menuCedis.clickBtnConsultaFUEDigital();
		//Thread.sleep(3000);
		FUEDigital.Cargando();
		FUEDigital.ValidacionCampos();
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		

		/*Paso 12.-Validar que en el listado de transferencias se muestren las recepciones ordenadas del mas reciente al mas antiguo.
		*Resultado Esperado: Se ingresa a la pantalla "Consulta FUE Digital", y en listado de transferencias se muestra la mas reciente hasta la mas antigua.
		*/
		addStep("Validar que en el listado de transferencias se muestren las recepciones ordenadas del mas reciente al mas antiguo.\r\n"
				+ "Resultado Esperado: Se ingresa a la pantalla \"Consulta FUE Digital\", y en listado de transferencias se muestra la mas reciente hasta la mas antigua.");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
		Thread.sleep(1000);
		FUEDigital.ValidarOrdenTransferencias();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(1000);

		/*Paso 13.-Seleccionar una transferencia de la cual desees visualizar el FUE.
		*Resultado Esperado: Se muestra la pantalla FUE de la transferencia seleccionada la cual solo es de visualizacion con los siguientes elementos: Entregado, Cajas Sin Etiqueta, Faltantes, Sobrantes, Danados, Firma Faltante/Sobrante/Danado, Devolucion de Canastillas, Canastillas con Devolucion, Devolucion de Contenedores, Firma Devoluciones, Movimientos Tienda.
		*/
		addStep("Seleccionar una transferencia de la cual desees visualizar el FUE.\r\n"
				+ "Resultado Esperado: Se muestra la pantalla FUE de la transferencia seleccionada la cual solo es de visualizacion con los siguientes elementos: Entregado, Cajas Sin Etiqueta, Faltantes, Sobrantes, Danados, Firma Faltante/Sobrante/Danado, Devolucion de Canastillas, Canastillas con Devolucion, Devolucion de Contenedores, Firma Devoluciones, Movimientos Tienda.");
		FUEDigital.SeleccionarObjeto("Entrar Primer Transferencia");
		Thread.sleep(5000);
		FUEDigital.ValidacionTituloPantallaYDatosGenerales();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUEDigital.ValidacionEntregados();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUEDigital.ValidacionCajaSinEtiqueta();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUEDigital.ValidacionFaltantes();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUEDigital.ValidacionSobrantes();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUEDigital.ValidacionDanados();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUEDigital.ValidacionFirmaRecepcion();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUEDigital.ValidacionDevolucionCanastillas();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUEDigital.ValidacionCanastillasConDevolucion();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUEDigital.ValidacionDevolucionContenedores();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUEDigital.ValidacionFirmasDevoluciones();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUEDigital.ValidacionMovimientoTiendaYBotonVolver();
		//testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Runtime.getRuntime().exec("adb shell input swipe 20 1003 20 900 50");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*Paso 14.-Validar que las transferencias que se muestran en el listado de paginacion sean entregas en un historico de 3 meses atras.
		*Resultado Esperado: Se muestran las transferencias en listado de historico de 3 meses atras a la fecha actual.
		*/
		addStep("Validar que las transferencias que se muestran en el listado de paginacion sean entregas en un historico de 3 meses atras.\r\n"
				+ "Resultado Esperado: Se muestran las transferencias en listado de historico de 3 meses atras a la fecha actual.");
		

		/*Paso 15.-Seleccionar una transferencia que se muestran en el listado de paginacion de la cual se encuentra con una fecha anterior a 2 mes atras.
		*Resultado Esperado: Se muestra el FUE de la transferencia seleccionada la cual es menor a 2 mes de antiguedad para poder visualizar su informacion.
		*/
		addStep("Seleccionar una transferencia que se muestran en el listado de paginacion de la cual se encuentra con una fecha anterior a 2 mes atras.\r\n"
				+ "Resultado Esperado: Se muestra el FUE de la transferencia seleccionada la cual es menor a 2 mes de antiguedad para poder visualizar su informacion.");
		

																			
		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));
	}


	@Override		
	public String setTestFullName() {
		return "Nombre caso de prueba ";
	}

	@Override
	public String setTestDescription() {
		return "Descripción del caso de prueba";
	}

	@Override
	public String setTestDesigner() {
		return "Nombre quien construye";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Pre Requisitos";
	}

	@Override
	public void beforeTest() {
	}

}