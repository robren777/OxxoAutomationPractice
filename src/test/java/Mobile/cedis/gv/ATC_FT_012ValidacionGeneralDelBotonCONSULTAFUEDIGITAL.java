package Mobile.cedis.gv;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_012ValidacionGeneralDelBotonCONSULTAFUEDIGITAL extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_012ValidacionGeneralDelBotonCONSULTAFUEDIGITAL_test(HashMap<String, String> data)
			throws Exception {

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

		String url = "http://127.0.0.1:4723/";

		// Cerrar Apps
		Runtime.getRuntime().exec("adb shell pm clear " + data.get("appPackage"));

		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n" + e.getMessage());
			e.printStackTrace();
		}

		// Abre la aplicación
		Runtime.getRuntime().exec("adb shell am start " + data.get("appPackage"));

		// Ejecuta el logeo hacia el punto de venta
		Mobile.cedis.om.A01_Login_Punto_De_Venta login = new Mobile.cedis.om.A01_Login_Punto_De_Venta(testCase, appiumUtil, this);
		login.loginPV(data.get("ip"));

		// Paso #1.- Ingresar al menu principal de Oxxo al dia
		// Resultado Esperado del Paso #1: Se ingresa correctamente al menu principal
		System.out.println("Paso #1: Ingresar al menu principal de Oxxo al dia...");
		Mobile.cedis.om.A02_Login_Lider loginLider = new Mobile.cedis.om.A02_Login_Lider(testCase, appiumUtil, this);
		loginLider.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));
		Thread.sleep(8000);
		addStep("Ingresar al menú principal de Oxxo al día.", "Se ingresa correctamente al menú principal");
		Mobile.cedis.om.A03_Menus_y_submenus herramienta = new Mobile.cedis.om.A03_Menus_y_submenus(testCase, appiumUtil, this);
		herramienta.ValidacionPantallaMenuPrincipal();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		herramienta.SeleccionarObjeto("Excelencia");

		// Paso #2.- Seleccionar el icono de Excelencia Operativa
		// Resultado Esperado del Paso #2: Se despliega la pantalla de Excelencia
		// Operativa
		System.out.println("Paso #2: Seleccionar el icono de Excelencia Operativa...");
		addStep("Seleccionar el icono de Excelencia Operativa.", "Se despliega la pantalla de Excelencia Operativa");
		Thread.sleep(2000);
		herramienta.ValidacionPantallaExcelenciaOperativa();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		// Paso #3.- Seleccionar el icono de Herramienta de Compra.
		// Resultado esperado del Paso #3: Se despliega el submenú de la HC
		System.out.println("Paso #3: Seleccionar el icono de Herramienta de Compra...");
		herramienta.SeleccionarObjeto("Herramienta");
		addStep("Seleccionar el icono de Herramienta de Compra.",
				"Se despliega el submenú de la pantalla de Herramienta de Compra");
		Thread.sleep(2000);
		herramienta.ValidacionPantallaHerramientaCompra();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		// Paso #4.- Seleccionar el icono de Cometa.
		// Resultado esperado del Paso #4: Se despliega la pantalla de Herramientas
		Mobile.cedis.om.A04_MenuHerramientas C04MenuHerramienta = new Mobile.cedis.om.A04_MenuHerramientas(testCase, appiumUtil,
				this);
		System.out.println("Paso #4: Seleccionar el icono de Cometa...");
		System.out.println("Paso Necesario: Se iniciará el login de ruta para tener token válido y continuar el caso...");
		herramienta.SeleccionarObjeto("Cometa");
		addStep("Seleccionar el icono de Cometa.", "Se despliega la pantalla de Herramientas");
		Thread.sleep(2000);
		C04MenuHerramienta.ValidacionPantallaHerramientas();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		// IMPORTANTE - Aqui se hara el login de ruta porque si no se realiza no carga
		// el token en el Modulo de: Consulta FUE Digital

		C04MenuHerramienta.clickBtnRecepcionPedidosCEDIS();
		Mobile.cedis.om.A05_LoginAreaDistribucion C05LoginAreaDistribucion = new Mobile.cedis.om.A05_LoginAreaDistribucion(testCase,
				appiumUtil, this);
		C05LoginAreaDistribucion.LoginAreaDistribucion();
		Thread.sleep(2000);
		Runtime.getRuntime().exec("adb shell input keyevent 4");
		Thread.sleep(2000);
		Runtime.getRuntime().exec("adb shell input keyevent 4");
		Thread.sleep(2000);
		// --------------------------------------------------------------------------------------------------------------------------

		// Paso #5.- Validar que la opción Consulta Tiras de entrega (3) se haya
		// realizado el renombramiento correcto del título y el nombre sea "Consulta FUE Digital"
		// Resultado esperado del Paso #5: La opción "Consultar Tiras de Entrega" se
		// muestra correctamente renombrada a "Consulta FUE Digital"
		System.out.println(
				"Paso #5: Validando que la opción 'Consulta Tiras de Entrega' se haya renombrado correctamente a 'Consulta FUE Digital'...");
		addStep("Validar que la opción Consulta Tiras de entrega (3) se haya realizado el renombramiento correcto del título y el nombre sea \"Consulta FUE Digital\"",
				"Resultado esperado: La opción \"Consultar Tiras de Entrega\" se muestra correctamente renombrada a \"Consulta FUE Digital\"");
		C04MenuHerramienta.ValidacionLeyendaConsultaFUEDigital();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		// Paso #6.- Seleccionar la opción que lleva por nombre "Consulta FUE Digital"
		// Resultado esperado: Se ingresa a la pantalla "Consulta FUE Digital", y se
		// muestra correctamente el contenido del encabezado con los siguientes datos:
		// * Imagen de una tienda OXXO.
		// * Fecha.
		// * CR Tienda.
		// * Nombre tienda.
		System.out.println("Paso #6: Seleccionando la opción 'Consulta FUE Digital'...");
		C04MenuHerramienta.clickBtnConsultaFUEDigital();
		Thread.sleep(2000);
		System.out.println("Se hizo clic en 'Consulta FUE Digital'.");
		Mobile.cedis.om.E01_ConsultaFUE E01_ConsultaFUE = new Mobile.cedis.om.E01_ConsultaFUE(testCase, appiumUtil, this);
		E01_ConsultaFUE.ValidacionEncabezado();
		System.out.println("Validación de encabezado realizada.");
		E01_ConsultaFUE.ValidacionCampos();
		System.out.println("Validación de campos realizada.");
		addStep("Seleccionar la opción que lleva por nombre \"Consulta FUE Digital\"",
				"Resultado esperado: Se ingresa a la pantalla \"Consulta FUE Digital\", y se muestra correctamente el contenido del encabezado con los siguientes datos:\r\n"
						+ "*Imagen de una tienda OXXO.\r\n" + "Fecha.\r\n" + "CR Tienda.\r\n" + "Nombre tienda.");
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		System.out.println("Captura de pantalla tomada para el Paso #6.");

		// Paso 7 Seleccionar el radio button de Transferencia y busca un número de transferencia que sea válida
		// Resultado esperado: Se habilita el apartado para el ingreso del número de transferencia a buscar y Se despliega correctamente la transferencia la cual
		// fue realizada su búsqueda al haber ingresado el numero de la misma.
		System.out.println(
				"Paso #7: Seleccionar el radio button de Transferencia y busca un número de transferencia que sea válida...");
		addStep("Seleccionar el radio button de Transferencia y busca un número de transferencia que sea válida\r\n",
				"Resultado esperado: Se habilita el apartado para el ingreso del número de transferencia a buscar y Se despliega correctamente la transferencia la cual fue realizada su búsqueda al haber ingresado el  numero de la misma.");
		E01_ConsultaFUE.SeleccionarObjeto("Radio Transferencia");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(2000);
		E01_ConsultaFUE.IngresoTransferencia();
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		E01_ConsultaFUE.SeleccionarObjeto("Buscar");
		Thread.sleep(1000);
		E01_ConsultaFUE.ValidarIngresoTransferencia();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(1000);

		// Paso 8.- Seleccionar el radio button de Fecha y busca la fecha de una transferencia válida
		// Resultado esperado: Se habilita el apartado para el ingreso de fecha de la transferencia a buscar y se despliega correctamente la transferencia la cual
		// fue realizada su busqueda por la fecha de la misma.
		System.out.println("Paso #8: Seleccionando el radio button de Fecha y buscar fecha valida...");
		E01_ConsultaFUE.SeleccionarObjeto("Radio Fecha");
		addStep("Seleccionar el radio button de Fecha y busca la fecha de una transferencia que sea válida",
				"Se habilita el apartado para el ingreso de fecha de la transferencia.");
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(1000);
		E01_ConsultaFUE.IngresoFecha();
		Thread.sleep(1000);
		E01_ConsultaFUE.ValidarIngresoFecha();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(1000);

		// Paso 9.- Seleccionar el radio button de Fecha y busca una transferencia con
		// fecha incorrecta
		// Resultado esperado: Se habilita el apartado para el ingreso de fecha de la
		// transferencia a buscar y Se despliega correctamente el mensaje: No se
		// encontro un pedido en la fecha previamente consultada ACEPTAR.
		System.out.println("Paso #9: Buscar Transferencia con Fecha Incorrecta...");
		E01_ConsultaFUE.ValidarDespliegueCalendario();
		Thread.sleep(1000);
		E01_ConsultaFUE.SeleccionarObjeto("Calendario");
		Thread.sleep(1000);
		addStep("Seleccionar el radio button de Fecha y busca la fecha de una transferencia que sea inválida",
				"Se habilita el apartado para el ingreso de fecha incorrecta.");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(1000);
		E01_ConsultaFUE.IngresarFechaPosterior();
		Thread.sleep(1000);
		E01_ConsultaFUE.SeleccionarObjeto("Aceptar Fecha");
		Thread.sleep(1000);
		E01_ConsultaFUE.ValidarMensajeFechaIncorrecta();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(1000);

		// Paso 10.- Buscar una transferencia no válida
		// Resultado esperado: Se habilita el apartado para el ingreso del numero de
		// transferencia a buscar y se despliega correctamente el mensaje: No se
		// encontro un pedido en la fecha previamente consultada ACEPTAR.
		System.out.println("Paso #10: Buscando una transferencia con número no válido...");
		E01_ConsultaFUE.SeleccionarObjeto("Aceptar Mensaje");
		Thread.sleep(1000);
		addStep("Buscar un número de transferencia no válido",
				"Se despliega el mensaje: 'No se encontró un pedido en la fecha previamente consultada'.");
		E01_ConsultaFUE.SeleccionarObjeto("Radio Transferencia");
		Thread.sleep(1000);
		E01_ConsultaFUE.IngresoTransferenciaIncorrecta();
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(1000);
		E01_ConsultaFUE.SeleccionarObjeto("Buscar");
		Thread.sleep(1000);
		E01_ConsultaFUE.ValidarMensajeTransferenciaIncorrecta();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		E01_ConsultaFUE.SeleccionarObjeto("Aceptar Mensaje");
		Thread.sleep(1000);

		// Paso 11.- Validar que las columnas se muestren correctamente (TRANSFERENCIA a
		// la izquierda y RECEPCIÓN a la derecha)
		// Resultado esperado: Se ingresa a la pantalla "Consulta FUE Digital", y se
		// muestra las columnas: "Transferencia" lado izq, "Recepcion" lado derecho.
		System.out.println(
				"Paso #11: Validando que las columnas 'TRANSFERENCIA' y 'RECEPCIÓN' se muestren correctamente...");
		Runtime.getRuntime().exec("adb shell input keyevent 4"); // Realiza la acción de Back en la handheld
		Thread.sleep(1000);
		C04MenuHerramienta.clickBtnConsultaFUEDigital();
		Thread.sleep(3000);
		addStep("Validar que las columnas se muestren correctamente",
				"Se muestran las columnas 'TRANSFERENCIA' a la izquierda y 'RECEPCIÓN' a la derecha.");
		E01_ConsultaFUE.ValidacionCampos();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(1000);

		// Paso 12.- Validar que las transferencias se muestren ordenadas de lo más
		// reciente a lo más antiguo
		// Resultado esperado: Se ingresa a la pantalla "Consulta FUE Digital", y en
		// listado de transferencias se muestra la mas reciente hasta la mas antigua.
		System.out.println(
				"Paso #12: Validando que las transferencias se muestren ordenadas de más reciente a más antiguo...");
		addStep("Validar que las transferencias se muestren ordenadas",
				"Se ingresa a la pantalla y se muestra el listado de transferencias ordenado de la más reciente a la más antigua.");
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
		Thread.sleep(1000);
		E01_ConsultaFUE.ValidarOrdenTransferencias();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(1000);

		// Paso 13.- Seleccionar una transferencia para visualizar el FUE
		// Resultado esperado: Se muestra la pantalla FUE de la transferencia
		// seleccionada la cual solo es de visualizacion con los siguientes elementos:
		// Entregado, Cajas Sin Etiqueta, Faltantes, Sobrantes, Danados, Firma
		// Faltante/Sobrante/Danado, Devolucion de Canastillas, Canastillas con
		// Devolucion, Devolucion de Contenedores, Firma Devoluciones, Movimiento Tienda
		System.out.println("Paso #13: Seleccionando la transferencia para validar el FUE correspondiente...");
		E01_ConsultaFUE.SeleccionarObjeto("Entrar Primer Transferencia");
		Thread.sleep(5000);
		addStep("Seleccionar una transferencia para visualizar el FUE",
				"Se muestra la pantalla FUE de la transferencia seleccionada.");
		E01_ConsultaFUE.ValidacionTituloPantallaYDatosGenerales();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		E01_ConsultaFUE.ValidacionEntregados();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		E01_ConsultaFUE.ValidacionCajaSinEtiqueta();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		E01_ConsultaFUE.ValidacionFaltantes();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		E01_ConsultaFUE.ValidacionSobrantes();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		E01_ConsultaFUE.ValidacionDanados();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		E01_ConsultaFUE.ValidacionFirmaRecepcion();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		E01_ConsultaFUE.ValidacionDevolucionCanastillas();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		E01_ConsultaFUE.ValidacionCanastillasConDevolucion();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		E01_ConsultaFUE.ValidacionDevolucionContenedores();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		E01_ConsultaFUE.ValidacionFirmasDevoluciones();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		E01_ConsultaFUE.ValidacionMovimientoTiendaYBotonVolver();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Runtime.getRuntime().exec("adb shell input swipe 20 1003 20 900 50");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		System.out.println(
				"\nSe ha finalizado exitosamente el script: \nMTC-FT-012-Validación General del botón CONSULTA FUE DIGITAL\n");
		Thread.sleep(10000);

		// Cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));
	}

	@Override
	public String setTestFullName() {
		return "MTC-FT-012-Validación General del botón CONSULTA FUE DIGITAL";
	}

	@Override
	public String setTestDescription() {
		return "Validacion que al entrar en la opcion \"COMETA\" del menu \"Herramienta de Compra\", todos los elementos y la información se muestren de manera correcta";
	}

	@Override
	public String setTestDesigner() {
		return "Juan Ibarra";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1. App Oxxo al dia instalada en Handheld.\r\n" + "2. Usuario y Contraseña de Aplicacion Oxxo al dia\r\n"
				+ "3. Comunicacion entre HH y POS (red wifi (Handheld) y cableada (POS), que permitan conexion entre ambos dispositivos).\r\n"
				+ "4. P.V con acceso a la Herramienta de compra.\r\n"
				+ "5. Contar con los accesos aplicacion Oxxo al dia. Usuario: lider, Contraseña: 2";
	}

	@Override
	public void beforeTest() {
	}

}