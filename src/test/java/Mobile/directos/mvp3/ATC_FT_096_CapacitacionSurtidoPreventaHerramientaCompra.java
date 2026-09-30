package Mobile.directos.mvp3;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.directos.om.mvp3.Login;
import Mobile.directos.om.mvp3.OAD;
import Mobile.directos.om.mvp3.ProveedoresDirectos;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;
public class ATC_FT_096_CapacitacionSurtidoPreventaHerramientaCompra extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_096_CapacitacionSurtidoPreventaHerramientaCompra_test(
			HashMap<String, String> data) throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();

		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appPackage", data.get("appPackageP"));
		cap.setCapability("appActivity", data.get("appActivityP"));
		cap.setCapability("appium:settings[enableMultiWindows]", true);
		cap.setCapability("noReset", true); // Se debe de dejar esta opcion en true para que no se borre el cache y asi
											// lograr evitar la vista de ingresar la IP y puerto
		String url = "http://127.0.0.1:4723";

		// =======================================Cerrar Aplicaciones========================================================//
		Thread.sleep(5000);
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		// =======================================Inicio Aplicaciones========================================================//
		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n" + e.getMessage());
			e.printStackTrace();
		}
		Thread.sleep(3500);

		// =========================================Llamado al OM============================================================//
		ProveedoresDirectos ProveedoresD = new ProveedoresDirectos(testCase, appiumUtil, this);
		Login login = new Login(testCase, appiumUtil, this);
		OAD OxxoAlDia = new OAD(testCase, appiumUtil, this);
		Thread.sleep(5000);

		// =========================================Inicio Ejecucion=========================================================//

		/*
		 * 1 Abrir la aplicación “Proveedores directos”
		 * Se muestra pantalla principal de Proveedores Directos con los siguientes
		 * componentes Logotipo de Oxxo Texto: ¡Bienvenido!
		 * 
		 * Opción: Comenzar proceso de Recepción de mercancía Opción: Capacitación con
		 * Visita Guiada
		 */
		addStep("Abrir la aplicación [Proveedores directos]","Se muestra pantalla principal de Proveedores Directos con los siguientes componentes\r\n"
				+ "Logotipo de Oxxo\r\n" + "Texto: ¡Bienvenido!\r\n" + "\r\n"
				+ "Opción: Comenzar proceso de Recepción de mercancía\r\n" + "Opción: Capacitación con Visita Guiada");
		assertTrue(ProveedoresD.pantallaPrincipalProveedoresDirectos(),
				"La pantalla principal de la App Proveedores Directos no se muestra de manera correcta.");

		/*
		 * 2 Seleccionar la opción “Capacitación con Visita Guiada”
		 */
		addStep("Seleccionar la opción “Capacitación con Visita Guiada”");
		assertTrue(ProveedoresD.VGCapacitacion(), "Boton no disponible: [Capacitacion con Visita Guiada]");

		/*
		 * 3 Seleccionar la opción “Preventa”
		 */
		addStep("Seleccionar la opción [Preventa]");
		ProveedoresD.opcionPreventa();

		/*
		 * 4 Seleccionar la opción “Por Herramienta de Compra”
		 */
		addStep("Seleccionar la opción [Por Herramienta de Compra]");
		ProveedoresD.OpcionHerramientaCompra();

		/*
		 * 6 Validar que muestre la opción “Escanear QR de pedido o QR de Hta. de
		 * compra” seleccionada con el mensaje Validar mensaje: Selecciona la opción
		 * “Escanear QR de pedido o QR de Hta. de compra” para iniciar el proceso de
		 * recepción de mercancía. Solicita al equipo de tienda que te proporcione el
		 * código QR de la herramienta de compra correspondiente a tu fuente de abasto.
		 * 
		 * Botón: Ver pasos para generar QR en Hand Held
		 */

		addStep("Validar que muestre la opción “Escanear QR de pedido o QR de Hta. de compra” seleccionada con el mensaje "
				+ "Validar mensaje: "
				+ "Selecciona la opción “Escanear QR de pedido o QR de Hta. de compra” para iniciar el proceso de recepción de mercancía. "
				+ "Solicita al equipo de tienda que te proporcione el código QR de la herramienta de compra correspondiente a tu fuente de abasto. "
				+ "Botón: Ver pasos para generar QR en Hand Held");
		boolean validacion = OxxoAlDia.ValidarMensajeEscanearQRpedidoT();
		assertTrue(validacion, "error, no se muestra de manera correcta mensaje de visista guiada");
		OxxoAlDia.opcionVerPasosGenerarQR();

		/*
		 * ? Verificar que en el Botón “Ver pasos para generar QR en Hand Held" indique
		 * los pasos a seguir en la App Oxxo al día Pasos en HH para abrir Herramienta
		 * de Compra Excelencia Operativa > Herramienta de Compra > Herramienta de
		 * Compra COMETA > Verificación de Mercancía Pedidos Proveedor Directo >
		 * Generación de Herramienta de Compra botón [Siguiente]
		 */
		addStep("Verificar mensaje :" + "Pasos en HH para abrir Herramienta de Compra \r\n"
				+ "Excelencia Operativa > \r\n" + "Herramienta de Compra > \r\n" + "Herramienta de Compra COMETA > \r\n"
				+ "Verificación de Mercancía Pedidos \r\n" + "Proveedor Directo > \r\n"
				+ "Generación de Herramienta de Compra \r\n" + "botón [Siguiente] ");
		validacion = ProveedoresD.MensajePasosAbrirHerramientaCompra();
		assertTrue(validacion, "error, No se muestra de manera correcta el mensaje de visita guiada");

		/*
		 * 8 Seleccionar “siguiente”
		 */
		addStep("Seleccionar [siguiente]");
		ProveedoresD.opcionSiguiente();

		/*
		 * 9 "Validar mensaje: Dar clic en botón "Continuar" para ir a Herramienta de
		 * Compra. Nota: Para fines de este proceso de capacitación, se estará abriendo
		 * un pedido meramente ilustrativo al dar clic en continuar, como si se hubiera
		 * escaneado el código QR.
		 */
		addStep("Validar mensaje: " + "Dar clic en botón \"Continuar\" para ir a Herramienta de Compra. "
				+ "Nota: Para fines de este proceso de capacitación, se estará abriendo un pedido meramente "
				+ "ilustrativo al dar clic en continuar, como si se hubiera escaneado el código QR.");
		validacion = ProveedoresD.MensajeBotonContinuar();
		assertTrue(validacion, "Error, el mensaje no se muestra de manera correcta");

		/*
		 * 10 Seleccionar “CONTINUAR”
		 */
		addStep("Seleccionar [CONTINUAR]");
		ProveedoresD.VGcontinuar();

		/*
		 * 11 Escanear QR
		 */
		addStep("pegar QR");
		OxxoAlDia.pegarQRp();
		Thread.sleep(5000);

		/*
		 * 12 Validar mensaje: Al abrir la herramienta de compra en el celular podrás
		 * aplicar el sugerido a todos los artículos activos en la tienda o aplicarlo de
		 * manera manual.
		 */
		addStep("Validar mensaje: Al abrir la herramienta de compra en el celular podrás aplicar el sugerido a todos los artículos activos en la tienda o aplicarlo de manera manual.");
		validacion = ProveedoresD.MensajeWarning();
		assertTrue(validacion, "Error el mensaje no se visualiza correctamente");


		/*
		 * 13 Seleccionar botón “NO”
		 */
		addStep("Seleccionar botón [NO]");
		ProveedoresD.VGsi();

		/*
		 * PASO 14 Validar mensaje de ayuda: Al visualizar la
		 * herramienta de compra podrás capturar la cantidad de pedido para cada
		 * artículo, en donde podrás visualizar también el inventario de cada uno y el
		 * sugerido de compra
		 */
		addStep("Validar mensaje de ayuda: Al visualizar la herramienta de compra podrás capturar la cantidad de pedido para cada artículo, "
				+ "en donde podrás visualizar también el inventario de cada uno y el sugerido de compra");
		validacion = ProveedoresD.MensajeVisualizarHC();
		assertTrue(validacion, "Error el mensaje no se visualiza correctamente");
		ProveedoresD.clicSumar();

		/*
		 * 15 Validar mensaje de ayuda: “Presiona el botón ""+"" para incrementar la
		 * cantidad de pedido del artículo. También podrás capturar de manera manual la
		 * cantidad en caso que así lo requieras.”
		 */
		addStep("Validar mensaje: Presiona el botón “+” para incrementar la cantidad de pedido del artículo. "
				+ "También podrás capturar de manera manual la cantidad en caso que así lo requieras.", "Presionar el Botón [+]");
		validacion = ProveedoresD.MensajeBotonMas();
		assertTrue(validacion, "Error el mensaje no se visualiza correctamente");

		/*
		 * 16 Presionar el Botón “+”
		 */
		ProveedoresD.clicSumar();
		ProveedoresD.clicSumar();
		ProveedoresD.clicSumar();
		Thread.sleep(3000);

		/*
		 * 17 Validar mensaje: Después de haber modificado los artículos para la
		 * generación del pedido, daremos clic en el botón “Continuar” en la parte
		 * inferior de la pantalla. Al dar clic se generará el QR, el cual debe ser
		 * escaneado con la Hand Held
		 */

		addStep("Validar mensaje: Después de haber modificado los artículos para la generación del pedido, "
				+ "daremos clic en el botón “Continuar” en la parte inferior de la pantalla. Al dar clic se generará el QR, "
				+ "el cual debe ser escaneado con la Hand Held", "Seleccionar [CONTINUAR]");
		validacion = ProveedoresD.MensajeModificadoArticulos();
		assertTrue(validacion, "Error el mensaje no se visualiza correctamente");

		/*
		 * 18 Seleccionar “CONTINUAR”
		 */
		ProveedoresD.clicContiuar();

		/*
		 * 19 Validar mensaje: “Al generarse este código QR y ser escaneado con la Hand
		 * Held, es cuando se genera una Orden de Compra para que pueda ser entregada al
		 * día siguiente por el equipo de reparto del proveedor. Hasta aquí la actividad
		 * del preventista finaliza. [Continuar]”
		 */
		addStep("Validar mensaje: \r\n" + "Icono: camioncito \r\n" + "Al generarse este código QR y ser escaneado \r\n"
				+ "con la Hand Held, es cuando se genera \r\n" + "una Orden de Compra para que pueda ser \r\n"
				+ "entregada al día siguiente por el equipo de \r\n"
				+ "reparto del proveedor. Hasta aquí la actividad \r\n" + "del preventista finaliza.  \r\n"
				+ "Botón: Continuar", "Seleccionar [CONTINUAR]");
		validacion = ProveedoresD.ValidarMensajeAlGenerarseCodigoQR();
		assertTrue(validacion, "error, el mensaje no se muestra de la forma corecta");

		/*
		 * 20 Seleccionar “Continuar”
		 */
		addStep("Seleccionar [Continuar]");
		ProveedoresD.opcionContinuarQR();

		/*
		 * Validar mensaje: “Para efectos de esta capacitación se abrirá el pedido que
		 * se generó por el preventista para que el equipo de reparto confirme las
		 * cantidades a entregar del pedido que se realizó el día anterior. [Continuar]”
		 */
		addStep("Validar mensaje: \r\n" + "Icono: caja \r\n" + "Para efectos de esta capacitación se abrirá \r\n"
				+ "el pedido que se generó por el preventista \r\n" + "para que el equipo de reparto confirme las \r\n"
				+ "cantidades a entregar del pedido que se realizó \r\n" + "el día anterior. \r\n"
				+ "Botón: Continuar");
		validacion = ProveedoresD.ValidarMensajeAlGenerarseCodigoQR2();
		assertTrue(validacion, "error, el mensaje no se muestra de la forma corecta");
		
		//clic continuar
		ProveedoresD.opcionContinuarQR();
		Thread.sleep(3000);
		
		/*
		 * 21 Validar mensaje: Estás realizando un proceso en la visita guiada... Ver
		 * más Botón “Salir” Nota: este mensaje se mostrará durante toda la visita
		 * guiada
		 */
		addStep("Validar mensaje: Estés realizando un proceso en la visita guiada... Ver mas \n" + "Botón “Salir” \n"
				+ "Nota: este mensaje se mostrará durante toda la visita guiada");
		validacion = ProveedoresD.validarAvisoVisitaGuiada();
		assertTrue(validacion, "no se muestra de manera correcta el Aviso salir visita guiada");
		

		/*
		 * 22 Seleccionar “Continuar”
		 * 23 Validar mensaje: Icono ✅ ¡Muy Bien! Al día siguiente, llega el equipo de
		 * reparto a la tienda, y confirma las cantidades disponibles a entregar en el
		 * punto de venta del pedido que realizó el preventista un día anterior. Botón:
		 * COMENZAR
		 */

		addStep("Validar mensaje: \r\n" + "Icono ✅ \r\n" + "¡Muy Bien! \r\n" + "Al día siguiente, llega el \r\n"
				+ "equipo de reparto a la \r\n" + "tienda, y confirma las \r\n" + "cantidades disponibles \r\n"
				+ "a entregar en el punto \r\n" + "de venta del pedido que \r\n" + "realizó el preventista un día \r\n"
				+ "anterior.\r\n" + "Botón: COMENZAR");

		validacion = ProveedoresD.ValidarMensajeMuyBien();
		assertTrue(validacion, "Error, el mensaje no se muestra de manera correcta");

		/*
		 * 24 Seleccionar Bóton “Comenzar”
		 */
		addStep("Seleccionar: COMENZAR");
		ProveedoresD.opcionComenzar();

		/*
		 * 25 Validar que el mensaje señale el primer articulo del pedido con el texto
		 * siguiente: “Este es el Pedido generado por el preventista a entregar en el
		 * punto de venta, en donde podrás confirmar las cantidades disponibles para la
		 * entrega. En dado caso que no cuentes con disponibilidad de algún SKU para su
		 * entrega, se tendría que modificar la cantidad.”
		 */

		addStep("Validar mensaje:"
				+ "Este es el Pedido generado por el preventista a entregar en el punto de venta, \r\n"
				+ "en donde podrás confirmar las cantidades disponibles para la entrega. \r\n"
				+ "En dado caso que no cuentes con disponibilidad de algún SKU para su entrega, \r\n"
				+ "se tendrá que modificar la cantidad.\"");
		validacion = ProveedoresD.ValidarMensajePedidoGenerado();
		assertTrue(validacion, "Error, el mensaje no se muestra de manera correcta");
		ProveedoresD.clicPantalla();
		
		/*
		 * 26 Validar que se resalte el botón “CONTIUAR” con el mensaje “Una vez
		 * confirmado los artículos disponibles para la entrega, se dará clic en el
		 * botón ""Continuar"" para generar el Código QR, el cual será escaneado con la
		 * Hand Held.”
		 */
		addStep("Validar mensaje: "
				+ "Una vez confirmando los artículos disponibles para la entrega, se dará clic en el botón “Continuar” "
				+ "para generar el Código QR, el cual será escaneado con la Hand Held.");
		validacion = ProveedoresD.ValidarMensajeContinuar();
		assertTrue(validacion, "Error, el mensaje no se muestra de manera correcta");

		/*
		 * 27 Seleccionar “Continuar”
		 */
		addStep("Seleccionar [CONTINUAR]");
		ProveedoresD.clicContiuar();
		
		/*
		 * 28 Validar que se genere un QR con el mensaje “Se genera un código QR el cual
		 * incluye los artículos del pedido realizado, este mismo será leído con la Hand
		 * Held por parte del equipo de la tienda OXXO. Continúa la visita guiada
		 * directamente en la Hand Held: [Ver pasos para lectura de QR en Hand Held.]”
		 */
		addStep("Validar mensaje: \r\n"
				+ "Se genera un código QR el cual incluye los artículos del pedido realizado, este mismo será leído con la Hand Held por parte del equipo de la tienda OXXO. Continúa la visita guiada directamente en la HandHeld: \r\n"
				+ "Botón: Ver pasos para generar QR en Hand Held ");
		validacion = ProveedoresD.VGpantallaQR();
		assertTrue(validacion, "Error, el mensaje no se muestra de manera correcta");
		
		/*
		 * 29 Seleccionar “Ver pasos para lectura de QR en Hand Held.”
		 */
		addStep("Seleccionar [Ver pasos para generar QR en Hand Held]");
		ProveedoresD.VGPasosLecturaQR();

		/*
		 * Validar mensaje: Pasos para lectura de QR en Hand Held Excelencia Operativa >
		 * Herramienta de Compra > Herramienta de Compra COMETA > Verificación de
		 * Mercancía Pedidos Proveedor Directo > Leer QR para Preventa o Pedido
		 */
		addStep("Validar que se muestre mensaje de ayuda: \r\n" + "Pasos para lectura de QR en Hand Held \r\n"
				+ "Excelencia Operativa > \r\n" + "Herramienta de Compra > \r\n" + "Herramienta de Compra COMETA > \r\n"
				+ "Verificación de Mercancía Pedidos Proveedor Directo > \r\n" + "Leer QR para Preventa o Pedido ");
		validacion = OxxoAlDia.ValidarMensajePasosLecturaQR();
		assertTrue(validacion, "Error, no se muestra mensaje de la manera correcta");

		// copiar codigo QR
		OxxoAlDia.copiarQR();
		// cerrar app proveedores
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");

		/*
		 * 30 Abrir app Oxxo Al Día: Excelencia operativa>Herramienta de compra> Cometa>
		 * Verificación de Mercancía Pedidos Proveedor Directo>Leer QR para Preventa o
		 * Pedido
		 */

		addStep("Abrir app Oxxo Al Día: " + "Excelencia operativa>Herramienta de compra> " + "Cometa> "
				+ "Verificación de Mercancía Pedidos Proveedor Directo > " + "Leer QR para Preventa o Pedido ");

		// abrir app Oxxo al Dia
		appiumUtil.driver.activateApp(data.get("appPackage") + "/" + data.get("appActivity"));
		Thread.sleep(5000);
		login.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));

		OxxoAlDia.ingresarModuloHerramientas();
		OxxoAlDia.opcionVerificacionMercancia();

		/*
		 * 31 Seleccionar “NO”
		 */
		addStep("Seleccionar “NO”");
		OxxoAlDia.opcionleerQR();

		/*
		 * 32 Escanear QR
		 */
		addStep("Escanear QR");
		Thread.sleep(4000);
		OxxoAlDia.pegarQR();

		/*
		 * 33 Seleccionar “ACEPTAR”
		 */

		addStep("Seleccionar [ACEPTAR]");
		Thread.sleep(3000);
		assertTrue(OxxoAlDia.ValidarMensajeQRPrueba(), "Error, el mensaje no se muestra de manera correcta");
		//OxxoAlDia.aceptar();
		Thread.sleep(3000);

		/*
		 * 35 Validar mensaje: Una vez escaneado el QR con la Hand Held, nos solicitará
		 * capturar el número de remisión, este mismo te lo proporciona el proveedor.
		 */
		addStep("Validar mensaje: Una vez escaneado el QR con la Hand Held, nos solicitará capturar el número de remisión, este mismo te lo proporciona el proveedor.");
		validacion = OxxoAlDia.ValidarMensajeDespuesdeQR();
		assertTrue(validacion,"Error, no se meustra correctamente el mensaje, Una vez escaneado el QR con la Hand Held...");


		/*
		 * 36 Ingresar número de remisión manualmente
		 */
		addStep("Ingresar número de remisión manualmente");
		OxxoAlDia.ingresarNoRemision("1398");
		/*
		 * 37 Validar mensaje: Una vez capturado el número de remisión, estaremos
		 * realizando la verificación de mercancía del pedido generado,
		 * 38 Clic pantalla 
		 * 39 Clic pantalla
		 */
		addStep("Validar mensaje: Una vez capturado el número de remisión, estaremos realizando la verificación de mercancía del pedido generado.");
		assertTrue(OxxoAlDia.ValidarMensajeNoRemision(), "Error, el mensaje no se muestra de manera correcta");

		/*
		 * 40 "Validar mensaje: Se visualiza los artículos a verificar. En caso de haber
		 * incidencias, se clasifica en dañado, caduco o faltante. Ejemplo: Resta 1
		 * pieza y en automático se clasificará como faltante. IMPORTANTE: Si en dado
		 * caso se detectan incidencias durante la verificación, el sistema te
		 * solicitará verificar más artículos del pedido original
		 */

		addStep("Validar mensaje de ayuda:"
				+ "Se visualiza los artículos a verificar, En caso de haber incidencias, se clasifica en dañado, caduco o faltante.");
		assertTrue(OxxoAlDia.ValidarVisualizacionArticulo(), "el mensaje no se muestra de la manera correcta");

		/*
		 * 41 Seleccionar “-”
		 */
		addStep("Seleccionar [-]");
		OxxoAlDia.opcionRestar();

		/*
		 * Validar mensaje: Después de haber verificado la mercancía, captura a cifra de
		 * proveedor. Esta cifra te la comparte directamente el proveedor.
		 */
		addStep("Validar mensaje: Después de haber verificado la mercancía, captura a cifra de proveedor. Esta cifra te la comparte directamente el proveedor.");
		assertTrue(OxxoAlDia.ValidarMensajeDespuesMercancia(), "el mensaje no se muestra de la manera correcta");

		/*
		 * 43 Agregar cifra de proveedor manualmente
		 */
		addStep("Agregar cifra de proveedor manualmente");
		OxxoAlDia.ingresarCifraProveedor("1313");

		/*
		 * 45 Validar mensaje: Da clic en el botón “Terminar” para visualizar el resumen
		 * e ir al paso de captura de firmas y comentarios.
		 */
		addStep("Validar mensaje: Da clic en el botón “Terminar” para visualizar el resumen e ir al paso "
				+ "de captura de firmas y comentarios.");
		assertTrue(OxxoAlDia.ValidarMensajeTerminarVG(), "paso 40 el mensaje no se muestra de la manera correcta");

		/*
		 * 46 Seleccionar “TERMINAR”
		 */
		addStep("Seleccionar [TERMINAR]");
		OxxoAlDia.opcionTerminar();

		/*
		 * 47 Validar mensaje de ayuda: se muestra resumen de pedido de los artículos
		 */
		addStep("Validar mensaje de ayuda: se muestra resumen de pedido de los artículos");
		assertTrue(OxxoAlDia.ValidarMensajeResumenPedido(), "paso 47 el mensaje no se muestra de la manera correcta");
		OxxoAlDia.clicTablaSinEvidencia();

		/*
		 * 42 Validar mensaje de ayuda: PROVEEDOR El siguiente paso es seleccionar una
		 * opción en la sección “Observaciones Proveedor”.
		 * 
		 * Ejemplo: Selecciona “Otros” para un comentario de captura libre.
		 */
		addStep("Validar mensaje de ayuda:"
				+ "PROVEEDOR El siguiente paso es seleccionar una opción en la sección “Observaciones Proveedor”.");
		assertTrue(OxxoAlDia.ValidarMensajeSelecionarProveedor(), "paso 42 el mensaje no se muestra de la manera correcta");
		Thread.sleep(5000);

		/*
		 * 48 Clic en la sección “Observaciones Proveedor”.
		 */
		addStep("Clic en la sección “Observaciones Proveedor”.");
		OxxoAlDia.CampoObservacionesProveedor();

		/*
		 * 49 Seleccionar “OTROS”
		 */
		addStep("Seleccionar “OTROS”");
		OxxoAlDia.LlenarObservacionesOtros();

		/*
		 * 50 Validar mensaje de ayuda: PROVEEDOR Escribe un comentario a modo de
		 * observación respecto al acuerdo de entrega de la OC.
		 */
		addStep("Validar mensaje:"
				+ " PROVEEDOR  Escribe un comentario a modo de observación respecto al acuerdo de entrega de la OC.");
		assertTrue(OxxoAlDia.ValidarMensajeComentarioProveedor(), "El mensaje no se muestra de mandera Correcta");

		/*
		 * 52 Se escribe comentario manualmente
		 */
		addStep("Se escribe comentario manualmente");
		OxxoAlDia.ingresarComentarioProveedor("Un Comentario");

		/*
		 * 53 Validar mensaje de ayuda: PROVEEDOR Haz clic sobre el recuadro destinado
		 * para capturar tu firma.
		 */
		addStep("Validar mensaje de ayuda: "
				+ "PROVEEDOR  Haz clic sobre el recuadro destinado para capturar tu firma.");
		assertTrue(OxxoAlDia.ValidarMensajeFirmaProveedor(), "El mensaje no se muestra de la mandera correcta");

		/*
		 * 54 Seleccionar campo Firma Proveedor
		 */
		addStep("Seleccionar campo Firma Proveedor");
		OxxoAlDia.campoFirmaPrveedor();

		/*
		 * 55 Validar mensaje de ayuda: Firma dentro del recuadro para el acuerdo de
		 * entrega.
		 */
		addStep("Validar mensaje: Firma dentro del recuadro para el acuerdo de entrega.");
		assertTrue(OxxoAlDia.ValidarMensajeDentroFirma(), "El mensaje no se muestra de la manera correcta");

		/*
		 * 56 Inserte una firma
		 */
		addStep("Se agrega firma");
		OxxoAlDia.clicfirma();
		OxxoAlDia.ingresarFirma();

		/*
		 * 57 Validar mensaje de ayuda: Da clic en “Continuar” para seguir con el
		 * proceso.
		 */
		addStep("Validar mensaje de ayuda: Da clic en “Continuar” para seguir con el proceso.");
		assertTrue(OxxoAlDia.ValidarMensajeFirmaContinuar(), "Error, el mensaje no se muestra de manera correcta");

		/*
		 * 58 Seleccionar “CONTINUAR”
		 */
		addStep("Seleccionar [CONTINUAR]");
		OxxoAlDia.seleccionarContinuar();

		/*
		 * Validar mensaje de ayuda: TIENDA El siguiente paso es seleccionar una opción
		 * en la sección “Observaciones Personal Tienda"".
		 * 
		 * Ejemplo: Selecciona ‘Sin Comentarios” en esta ocasión.
		 */
		addStep("Validar mensaje de ayuda:" + "TIENDA"
				+ "El siguiente paso es seleccionar una opción en la sección “Observaciones Personal Tienda\""
				+ "Ejemplo: Selecciona ‘Sin Comentarios” en esta ocasión.");
		assertTrue(OxxoAlDia.ValidarMensajeObservacionesTienda(), "El mensaje no se muestra de manera correcta");

		/*
		 * 59 Clic en el campo Observaciones Personal Tienda
		 */
		addStep("Clic en el campo Observaciones Personal Tienda");
		OxxoAlDia.CampoObservacionesTienda();

		/*
		 * 60 Seleccionar “SIN COMENTARIOS”
		 */
		addStep("Seleccionar [SIN COMENTARIOS]");
		OxxoAlDia.LlenarObservacionesSinComentarios();

		/*
		 * 61 Validar mensaje de ayuda: Haz clic sobre el recuadro destinado para la
		 * firma del personal de tienda.
		 */
		addStep("Validar mensaje de ayuda: "
				+ "Haz clic sobre el recuadro destinado para la firma del personal de tienda.");
		assertTrue(OxxoAlDia.ValidarMensajeRecuadroFirmaTienda(), "El mensaje no se muestra de manera correcta");

		/*
		 * 62 Seleccionar campo Firma Personal Tienda
		 */
		addStep("Seleccionar campo Firma Personal Tienda");
		OxxoAlDia.clicRecuadroFrimaTienda();

		/*
		 * 63 Validar mensaje de ayuda: Firma dentro del recuadro para el acuerdo de
		 * entrega.
		 */
		addStep("Validar mensaje de ayuda: Firma dentro del recuadro para el acuerdo de entrega.");
		assertTrue(OxxoAlDia.ValidarMensajeDentroFirma(), "El mensaje no se muestra de manera correcta");

		/*
		 * 64 Inserte una firma
		 */
		addStep("Se agrega firma");
		OxxoAlDia.clicfirma();
		OxxoAlDia.ingresarFirma();

		/*
		 * 65 Validar mensaje de ayuda: Da clic en “Continuar” para seguir con el
		 * proceso.
		 */
		addStep("Validar mensaje: Da clic en “Continuar” para seguir con el proceso.");
		assertTrue(OxxoAlDia.ValidarMensajeFirmaContinuar(), "El mensaje no se muestra de manera correcta");

		/*
		 * 66 Seleccionar “CONTINUAR”
		 */
		addStep("Seleccionar [CONTINUAR]");
		OxxoAlDia.seleccionarContinuar();

		/*
		 * 67 Validar mensaje de ayuda: “Selecciona la opción para confirmar que se
		 * cumplió con el proceso de entrega.”
		 */
		addStep("Validar mensaje: Selecciona la opción para confirmar que se cumplió con el proceso de entrega.");
		assertTrue(OxxoAlDia.ValidarMensajeSeCumplioProcesoEntrega(), "El mensaje no se muestra de manera correcta");
		/*
		 * 68 Seleccionar la opción “Se cumplió con el proceso de entrega”
		 */
		addStep("Seleccionar [se cumplió con el proceso de entrega]");
		OxxoAlDia.CheckSeCumplioProcesoEntrega();

		/*
		 * Validar mensaje de ayuda: Haz clic en “Terminar” para finalizar el proceso de
		 * acuerdo de entrega.
		 */
		addStep("Validar mensaje: Haz clic en “Terminar” para finalizar el proceso de acuerdo de entrega.");
		assertTrue(OxxoAlDia.ValidarMensajeTerminarParaFinalizar(), "El mensaje no se muestra de manera correcta");

		/*
		 * 69 Seleccionar “TERMINAR”
		 */
		addStep("Seleccionar [TERMINAR]");
		OxxoAlDia.opcionTerminar();

		/*
		 * Validar mensaje de ayuda: Se genera la Tira de Entrega del pedido,
		 * posteriormente da clic en el botón “Finalizar” en la parte inferior de la
		 * pantalla para terminar con el proceso.
		 * 
		 * Los comprobantes oficiales se visualizarán directamente en el portal de
		 * proveedores COMETA.
		 * 
		 * Botón “CONTINUAR”
		 */
		addStep("Validar mensaje de ayuda:"
				+ "Se genera la Tira de Entrega del pedido, posteriormente da clic en el botón “Finalizar” en la parte inferior de la pantalla para terminar con el proceso. "
				+ "Los comprobantes oficiales se visualizarán directamente en el portal de proveedores COMETA. "
				+ "Botón “CONTINUAR” ");
		assertTrue(OxxoAlDia.ValidarMensajeTiraEntregaPedido(), "El mensaje no se muestra de manera correcta");

		/*
		 * 70 Seleccionar “CONTINUAR”
		 */
		addStep("Seleccionar [CONTINUAR]");
		OxxoAlDia.selecionarContinuar();

		/*
		 * 71 Seleccionar “FINALIZAR”
		 */
		addStep("Seleccionar [FINALIZAR]");
		OxxoAlDia.opcionFinalizar();

		/*
		 * 73 Validar pantalla de felicitaciones: Icono paloma ¡Felicidades! Se ha
		 * concluido con la Capacitación de Visita Guiada. Todos los datos capturados
		 * dentro de esta capacitación fueron de prueba, por lo que no se verán
		 * afectados los inventarios de la tienda. Botón “FINALIZAR”
		 */
		addStep("Validar pantalla de Felicitaciones \n" + "Icono paloma \n"
				+ "Se ha concluido con la Capacitación de Visita Guiada. \n"
				+ "Todos los datos capturados dentro de esta capacitación fueron de prueba, \n"
				+ "por lo que no se verán afectados los inventarios de la tienda.");
		assertTrue(OxxoAlDia.ValidarPantallaFelicitaciones(),
				"la pantalla felicitaciones no se muestra de la manera Correcta");


	}

	/*
	 * Proveedores Directos MVP3:
	 * MTC-FT-159 Flujo de capacitación Surtido Preventa por herramienta de compra
	 * Desc: Validar la secuencia y guía correcta en el flujo de capacitación al proveedor para una recepción tipo Surtido Preventa por herramienta de compra,  que la leyenda informativa "Estas realizando un proceso en la visita guiada de capacitación, estos datos no serán guardados o almacenados en el punto de venta" sea visible durante todo el flujo.
	 * 
	 * @autor Miguel Angel Bautista Marroquin
	 * 
	 * @date 25/10/2024
	 */
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_096_CapacitacionSurtidoPreventaHerramientaCompra";
	}

	@Override
	public String setTestDescription() {
		return "Validar la secuencia y guía correcta en el flujo de capacitación al proveedor para una recepción tipo Surtido Preventa por herramienta de compra,  que la leyenda informativa \"Estas realizando un proceso en la visita guiada de capacitación, estos datos no serán guardados o almacenados en el punto de venta\" sea visible durante todo el flujo.";
	}

	@Override
	public String setTestDesigner() {
		return "Miguel Bautista";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1.	Contar con la versión mas reciente del APK Proveedores directos \r\n"
			 + "2.	Contar con un dispositivo móvil con Android 11";
	}

	@Override
	public void beforeTest() {
		
	}

}
