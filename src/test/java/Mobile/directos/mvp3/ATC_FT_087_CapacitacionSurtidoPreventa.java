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

public class ATC_FT_087_CapacitacionSurtidoPreventa extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_087_CapacitacionSurtidoPreventa_test(HashMap<String, String> data) throws Exception {
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
		 * 1	Abrir la aplicación “Proveedores directos”
		 * 2	Validar que se muestre pantalla de “Bienvenida”
		 * Se muestra la pantalla Capacitación con Visita Guiada con los siguientes componentes:
		 * Logotipo de Oxxo
		 * Texto: Selecciona el tipo de surtido
		 * Opción: Preventa
		 * Opción: Surtido Tradicional (Venta a bordo)
		 * Opción: Pedido Precargado (Sinergia/Reple)
		 */
		addStep("Abrir la aplicación [Proveedores directos]","Validar que se muestre pantalla de [Bienvenida]");
		assertTrue(ProveedoresD.pantallaPrincipalProveedoresDirectos(),
				"La pantalla principal de la App Proveedores Directos no se muestra de manera correcta.");
		
		/*
		 * 3	Seleccionar “Capacitación con Visita Guiada”
		 */
		
		addStep("Seleccionar “Capacitación con Visita Guiada”");
		assertTrue(ProveedoresD.VGCapacitacion(), "Boton no disponible: \"Capacitacion con Visita Guiada\"");
		
		/*
		 * 4	Seleccionar “Pereventa”
		 * 
		 */
		addStep("Seleccionar [Preventa]");
		ProveedoresD.opcionPreventa();
		
		/*
		 * 5	Seleccionar “Por Captura Manual Escaneando artículo por artículo”
		 */
		addStep("Seleccionar “Por Captura Manual Escaneando artículo por articulo”");
		assertTrue(ProveedoresD.VGCapturaManual(), "Boton no disponible: Por Captura Manual");
		
		/*
		 * 6	Validar que se muestre pantalla “Generación De Pedido”
		 * 7	Validar mensaje:
		 * Estés realizando un proceso en la visita guiada... Ver mas 
		 * Botón “Salir”
		 * Nota: este mensaje se mostrará durante toda la visita guiada
		 */
		addStep("Validar mensaje: Estés realizando un proceso en la visita guiada... Ver mas \n"
				+ "Botón “Salir” \n"
				+ "Nota: este mensaje se mostrará durante toda la visita guiada");
		boolean validacion = ProveedoresD.validarAvisoVisitaGuiada();
		assertTrue(validacion, "no se muestra de manera correcta el Aviso salir visita guiada");
		
		
		/*
		 * 8 Validar mensaje: 
		 * Selecciona la opción “Generar Pedido/ Preventa"" paa iniciar el proceso de recepción de mercancia."
		 */
		addStep("Validar mensaje:  Selecciona la opción “Generar Pedido/ Preventa\"\" paa iniciar el proceso de recepción de mercancia.");
		validacion = ProveedoresD.ValidarMensajePedidoEntrega();
		assertTrue(validacion,"Error el mensaje no se muestra de manera correcta");
		
		/*
		 * 9	Seleccionar “Generar Pedido/Preventa
		 */
		addStep("Seleccionar [Generar Pedido/Preventa]");
		ProveedoresD.opcionPedidoEntrega();
		
		/*
		 * 10	"Validar mensaje: 
		 * Captura tu Fuente de abasto, mejor conocido como número de proveedor."
		 */
		addStep("Validar mensaje: Captura tu Fuente de abasto, mejor conocido como número de proveedor.");
		assertTrue(ProveedoresD.VGFuenteAbastoTB(),"Error el mensaje no se muestra de manera correcta");
		
		/*
		 * 11	Ingresar fuente de abasto manualmente
		 */
		
		addStep("Ingresar fuente de abasto manualmente");
		ProveedoresD.ingresarFuenteAbasto("12321");
		Thread.sleep(1000); 
		/*
		 * 12	Validar mensaje:
		 * Inicia con el registro del pedido, ya sea por 
		 * medio de escaneo o por ingreso manual de 
		 * código de barras. Da clic sobre el ícono del 
		 * código de barra para agregar los artículos.
		 */
		addStep("Validar mensaje: "
				+ "Inicia con el registro del pedido, ya sea por "
				+ "medio de escaneo o por ingreso manual de "
				+ "código de barras. Da clic sobre el ícono del "
				+ "código de barra para agregar los artículos.");
		validacion = ProveedoresD.VGTxtoCodigoDeBarras1();
		assertTrue(validacion,"error, No se muestra el texto de manera correcta.");
		
		/*
		 * 13	Ingresar código de barras manualmente
		 */
		
		addStep("Ingresar código de barras manualmente");
		ProveedoresD.VGCodigoDeBarras();
		
		/*
		 * paso 14: validar mensaje: 
		 * Inicia con el registro del pedido, ya sea por 
		 * medio de escaneo o por ingreso manual de 
		 * código de barras. Da clic sobre el icono de la 
		 * palomita para agregar los artículos.
		 * 
		 */
		
		addStep("validar mensaje: \r\n"
				+ "Inicia con el registro del pedido, ya sea por \r\n"
				+ "medio de escaneo o por ingreso manual de \r\n"
				+ "código de barras. Da clic sobre el icono de la \r\n"
				+ "palomita para agregar los artículos.");
		validacion = ProveedoresD.VGTextoCodigoDeBarras();
		assertTrue(validacion,"error, No se muestra el texto de manera correcta.");
		
		/*
		 * 14	Seleccionar ✅
		 */
		addStep("Seleccionar ✅");
		ProveedoresD.VGpaloma();
		/*
		 * 15 Validar mensaje:
		 * Por cada artículo puedes visualizar el 
		 * identificador, descripción y código. También 
		 * puedes editar la cantidad, así como eliminarlo 
		 * del listado. Haz clic en el botón “+” o “-” para 
		 * editar la cantidad.
		 */
		addStep("Validar mensaje: \r\n"
				+ "Por cada artículo puedes visualizar el \r\n"
				+ "identificador, descripción y código. También \r\n"
				+ "puedes editar la cantidad, así como eliminarlo \r\n"
				+ "del listado. Haz clic en el botón “+” o “-” para \r\n"
				+ "editar la cantidad.");
		assertTrue(ProveedoresD.VGtxtPaso14(),"Error el mensaje no se muestra de manera correcta");
		
		/*
		 * 16	Seleccionar “+”
		 */
		addStep("Seleccionar [+]");
		ProveedoresD.VGsumar();
		
		/*
		 * 17	Validar mensaje de ayuda: 
		 * Al agregar artículos o editar las unidades 
		 * de cada uno, se actualizan en tiempo real los 
		 * campos de resumen en la parte inferior de la 
		 * pantalla
		 */
		addStep("Validar mensaje de ayuda: \r\n"
				+ "Al agregar artículos o editar las unidades \r\n"
				+ "de cada uno, se actualizan en tiempo real los \r\n"
				+ "campos de resumen en la parte inferior de la \r\n"
				+ "pantalla");
		assertTrue(ProveedoresD.VGtxtPaso15(),"Error el mensaje no se muestra de manera correcta");
		
		/*
		 * 18	Clic en la pantalla
		 */
		addStep("Clic en la pantalla");
		ProveedoresD.VGClicPantalla();
		
		/*
		 * 19	Validar mensaje de ayuda: Haz clic en “Continuar” cuando finalices la orden de compra
		 */
		addStep("Validar mensaje de ayuda: Haz clic en “Continuar” cuando finalices la orden de compra");
		ProveedoresD.VGmensajeContunuar();
		
		/*
		 * 20	Seleccionar “CONTINUAR”
		 */
		addStep("Seleccionar CONTINUAR]");
		ProveedoresD.VGcontinuar();
		
		/*
		 * 21	Validar mensaje de ayuda: Confirma la captura del pedido en la ventana emergente.
		 */
		addStep("Validar mensaje de ayuda: Confirma la captura del pedido en la ventana emergente.");
		assertTrue(ProveedoresD.validaMensajeConfirmaCaptura(),"Error el mensaje no se muestra de manera correcta");
		
		/*
		 * 22	Seleccionar “SI”
		 */
		addStep("Seleccionar “SI”");
		ProveedoresD.VGsi();
		
		/*
		 * 23	Validar mensaje: 
		 * Icono: camioncito 
		 * Al generarse este código QR y ser escaneado 
		 * con la Hand Held, es cuando se genera 
		 * una Orden de Compra para que pueda ser 
		 * entregada al día siguiente por el equipo de 
		 * reparto del proveedor. Hasta aquí la actividad 
		 * del preventista finaliza.
		 * Botón: Continuar
		*/
		addStep("Validar mensaje: \r\n"
				+ "Icono: camioncito \r\n"
				+ "Al generarse este código QR y ser escaneado \r\n"
				+ "con la Hand Held, es cuando se genera \r\n"
				+ "una Orden de Compra para que pueda ser \r\n"
				+ "entregada al día siguiente por el equipo de \r\n"
				+ "reparto del proveedor. Hasta aquí la actividad \r\n"
				+ "del preventista finaliza.  \r\n"
				+ "Botón: Continuar");
		validacion = ProveedoresD.ValidarMensajeAlGenerarseCodigoQR();
		assertTrue(validacion, "error, el mensaje no se muestra de la forma corecta"); 
		
		
		/*
		 * 24	Seleccionar “Continuar”
		 */
		addStep("Seleccionar [Continuar]");
		ProveedoresD.opcionContinuarQR();
		
		/*
		 * 25	Validar mensaje:
		 * Icono: caja
		 * Para efectos de esta capacitación se abrirá 
		 * el pedido que se generó por el preventista 
		 * para que el equipo de reparto confirme las 
		 * cantidades a entregar del pedido que se realizó 
		 * el día anterior.
		 * Botón: Continuar"
		 * */
		addStep("Validar mensaje: \r\n"
				+ "Icono: caja \r\n"
				+ "Para efectos de esta capacitación se abrirá \r\n"
				+ "el pedido que se generó por el preventista \r\n"
				+ "para que el equipo de reparto confirme las \r\n"
				+ "cantidades a entregar del pedido que se realizó \r\n"
				+ "el día anterior. \r\n"
				+ "Botón: Continuar");
		validacion = ProveedoresD.ValidarMensajeAlGenerarseCodigoQR2();
		assertTrue(validacion, "error, el mensaje no se muestra de la forma corecta");
		/*
		 * 26	Seleccionar “Continuar”
		 */
		
		addStep("Seleccionar “Continuar”");
		ProveedoresD.opcionContinuarQR();
		Thread.sleep(2000);
		
		/*
		 * 27	Validar mensaje: 
		 * Icono ✅
		 * ¡Muy Bien!
		 * Al día siguiente, llega el 
		 * equipo de reparto a la 
		 * tienda, y confirma las 
		 * cantidades disponibles 
		 * a entregar en el punto 
		 * de venta del pedido que 
		 * realizó el preventista un día 
		 * anterior.
		 * Botón: COMENZAR
		*/
		
		addStep("Validar mensaje: \r\n"
				+ "Icono ✅ \r\n"
				+ "¡Muy Bien! \r\n"
				+ "Al día siguiente, llega el \r\n"
				+ "equipo de reparto a la \r\n"
				+ "tienda, y confirma las \r\n"
				+ "cantidades disponibles \r\n"
				+ "a entregar en el punto \r\n"
				+ "de venta del pedido que \r\n"
				+ "realizó el preventista un día \r\n"
				+ "anterior.\r\n"
				+ "Botón: COMENZAR");

		validacion = ProveedoresD.ValidarMensajeMuyBien();
		assertTrue(validacion, "Error el mensaje no se muestra de manera correcta");
		
		/*
		 * 28	Seleccionar: COMENZAR
		 */
		addStep("Seleccionar: COMENZAR");
		ProveedoresD.opcionComenzar();
		/*29	"Validar mensaje: 
		 * Este es el Pedido generado por el preventista a entregar en el punto de venta, 
		 * en donde podrás confirmar las cantidades disponibles para la entrega.
		 * En dado caso que no cuentes con disponibilidad de algún SKU para su entrega, 
		 * se tendrá que modificar la cantidad."
		*/
		addStep("Validar mensaje:"
				+ "Este es el Pedido generado por el preventista a entregar en el punto de venta, \r\n"
				+ "en donde podrás confirmar las cantidades disponibles para la entrega. \r\n"
				+ "En dado caso que no cuentes con disponibilidad de algún SKU para su entrega, \r\n"
				+ "se tendrá que modificar la cantidad.\"","Clic pantalla");
		validacion = ProveedoresD.ValidarMensajePedidoGenerado();
		assertTrue(validacion, "Error el mensaje no se muestra de manera correcta");
		/*
		 * 30	Clic pantalla
		 */
		ProveedoresD.clicPantalla();
		
		/*
		 * 31	"Validar mensaje: 
		 * Una vez confirmando los artículos disponibles para la entrega, se dará clic en el botón “Continuar” 
		 * para generar el Código QR, el cual será escaneado con la Hand Held."
		 */
		addStep("Validar mensaje: "
				+ "Una vez confirmando los artículos disponibles para la entrega, se dará clic en el botón “Continuar” "
				+ "para generar el Código QR, el cual será escaneado con la Hand Held.");
		validacion = ProveedoresD.ValidarMensajeContinuar();
		assertTrue(validacion, "Error el mensaje no se muestra de manera correcta");
		/*
		 * 32	Seleccionar CONTINUAR
		 */
		addStep("Seleccionar CONTINUAR");
		ProveedoresD.clicContiuar();
		/*
		 * 33	Validar mensaje: 
		 * Se genera un código QR el cual incluye los artículos del pedido realizado, este mismo será leído con la Hand Held por parte del equipo de la tienda OXXO. Continúa la visita guiada directamente en la HandHeld:
		 * Botón: Ver pasos para generar QR en Hand Held
		*/
		addStep("Validar mensaje: \r\n"
				+ "Se genera un código QR el cual incluye los artículos del pedido realizado, este mismo será leído con la Hand Held por parte del equipo de la tienda OXXO. Continúa la visita guiada directamente en la HandHeld: \r\n"
				+ "Botón: Ver pasos para generar QR en Hand Held ");
		validacion = ProveedoresD.VGpantallaQR();
		assertTrue(validacion, "Error el mensaje no se muestra de manera correcta");
		
		/*
		 * 34	Seleccionar “Ver pasos para generar QR en Hand Held”
		 */
		addStep("Seleccionar [Ver pasos para generar QR en Hand Held]");
		ProveedoresD.VGPasosLecturaQR();
		
		/*
		 * 35	Validar que se muestre mensaje de ayuda:
		 * Pasos para lectura de QR en Hand Held
		 * Excelencia Operativa >
		 * Herramienta de Compra >
		 * Herramienta de Compra COMETA >
		 * Verificación de Mercancía Pedidos Proveedor Directo >
		 * Generación de Herramienta de compra 
		 */
		addStep("Validar que se muestre mensaje de ayuda: \r\n"
				+ "Pasos para lectura de QR en Hand Held \r\n"
				+ "Excelencia Operativa > \r\n"
				+ "Herramienta de Compra > \r\n"
				+ "Herramienta de Compra COMETA > \r\n"
				+ "Verificación de Mercancía Pedidos Proveedor Directo > \r\n"
				+ "Generación de Herramienta de compra ");
		validacion = OxxoAlDia.ValidarMensajePasosLecturaQR();
		assertTrue(validacion, "Error el mensaje no se muestra de manera correcta");
		/*
		 * 36	Abrir app Oxxo Al Día: Excelencia operativa>Herramienta de compra> Cometa> Verificación de Mercancía Pedidos Proveedor Directo > Leer QR para Preventa o Pedido
		 */
		addStep("Abrir app Oxxo Al Día: "
				+ "Excelencia operativa>Herramienta de compra> "
				+ "Cometa> "
				+ "Verificación de Mercancía Pedidos Proveedor Directo > "
				+ "Leer QR para Preventa o Pedido ");
		
		OxxoAlDia.copiarQR();//copia qr mostrado en la pantalla
		
		// cerrar app proveedores
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
		// abrir app Oxxo al Dia
		appiumUtil.driver.activateApp(data.get("appPackage")+"/"+data.get("appActivity"));
		Thread.sleep(5000);
		
		login.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));
		
		OxxoAlDia.ingresarModuloHerramientas();
		OxxoAlDia.opcionVerificacionMercancia();
		
		/*
		 * 37	Seleccionar “NO”
		 */
		addStep("Seleccionar [NO]");
		OxxoAlDia.opcionleerQR();
		
		/*
		 * 38	Escanear QR
		 */
		addStep("Escanear QR");
		Thread.sleep(4000);
		OxxoAlDia.pegarQR();
		
		/*
		 * 39	Seleccionar “ACEPTAR”
		 */
		addStep("Seleccionar [ACEPTAR]");
		Thread.sleep(3000);
		assertTrue(OxxoAlDia.ValidarMensajeQRPrueba(),"No se muestra mensaje correctamente");
		Thread.sleep(3000);
		/*
		 * 40	Validar mensaje: Una vez escaneado el QR con la Hand Held, nos solicitará capturar el número de remisión, este mismo te lo proporciona el proveedor.
		 */
		addStep("Validar mensaje: Una vez escaneado el QR con la Hand Held, nos solicitará capturar el número de remisión, este mismo te lo proporciona el proveedor.");
		validacion = OxxoAlDia.ValidarMensajeDespuesdeQR();
		assertTrue(validacion,"Error, no se muestra correctamente el mensaje, Una vez escaneado el QR con la Hand Held...");
		
		/*
		 * 41	Ingresar número de remisión manualmente
		 */
		addStep("Ingresar número de remisión manualmente");
		OxxoAlDia.ingresarNoRemision("1398");
		
		/*
		 * 42	"Validar mensaje:
		 * Una vez capturado el número de remisión, estaremos realizando la verificación de mercancía del pedido generado,"
		 * 43	Clic en pantalla
		 */
		addStep("Validar mensaje: Una vez capturado el número de remisión, estaremos realizando la verificación de mercancía del pedido generado.");
		assertTrue(OxxoAlDia.ValidarMensajeNoRemision(),"Error el mensaje no se muestra de manera correcta");
		
		/*
		 * 44	Validar mensaje: 
		 * Se visualiza los artículos a verificar. En caso de 
		 * haber incidencias, se clasifica en dañado, caduco o faltante.
		 * "Ejemplo: Resta 1 pieza y en automático se clasificará como faltante. 
		 * IMPORTANTE: Si en dado caso se detectan incidencias durante la verificación, el sistema te solicitará verificar más artículos del pedido original"
		 */
		addStep("Validar mensaje de ayuda:"
				+ "Se visualiza los artículos a verificar, En caso de haber incidencias, se clasifica en dañado, caduco o faltante.");
		assertTrue(OxxoAlDia.ValidarVisualizacionArticulo(),"el mensaje no se muestra de la manera correcta");
		/*
		 * 46	Seleccionar “-”
		 */
		addStep("Seleccionar “-”");
		OxxoAlDia.opcionRestar();
		
		/*
		 * 47	Validar mensaje: Después de haber verificado la mercancía, captura a cifra de proveedor. Esta cifra te la comparte directamente el proveedor.
		 */
		addStep("Validar mensaje: Después de haber verificado la mercancía, captura a cifra de proveedor. Esta cifra te la comparte directamente el proveedor.");
		assertTrue(OxxoAlDia.ValidarMensajeDespuesMercancia(),"paso 47 el mensaje no se muestra de la manera correcta");
		
		
		/*
		 * 48	Ingresar manualmente cifra del proveedor
		 * 49	Clic pantalla
		 */
		addStep("Agregar cifra de proveedor manualmente");
		OxxoAlDia.ingresarCifraProveedor("1313");
		
		/*
		 * 50	Validar mensaje: Da clic en el botón “Terminar” para visualizar el resumen e ir al paso de captura de firmas y comentarios.
		 */
		addStep("Validar mensaje: Da clic en el botón “Terminar” para visualizar el resumen e ir al paso "
				+ "de captura de firmas y comentarios.");
		assertTrue(OxxoAlDia.ValidarMensajeTerminarVG(),"el mensaje no se muestra de la manera correcta");
		
		/*
		 * 51	Seleccionar “TERMINAR”
		 */
		addStep("Seleccionar [TERMINAR]");
		OxxoAlDia.opcionTerminar();
		Thread.sleep(1000); 
		
		/*
		 * 52	Validar mensaje de ayuda: se muestra resumen de pedido de los artículos
		 */
		
		addStep("Validar mensaje de ayuda: se muestra resumen de pedido de los artículos");
		assertTrue(OxxoAlDia.ValidarMensajeResumenPedido(), "paso 52 el mensaje no se muestra de la manera correcta");
		OxxoAlDia.validarDummiesResumenPedido();
		
		/*
		 * 53	Clic en pantalla
		 */
		addStep("Clic en pantalla");
		OxxoAlDia.clicTabla();
		
		/*
		 * 54	Validar mensaje de ayuda:
		 * PROVEEDOR
		 * El siguiente paso es seleccionar una opción en la sección “Observaciones Proveedor”.
		 * Ejemplo: Selecciona “Otros” para un comentario de captura libre.
		 */
		addStep("Validar mensaje de ayuda:"
				+ "PROVEEDOR El siguiente paso es seleccionar una opción en la sección “Observaciones Proveedor”.");
		assertTrue(OxxoAlDia.ValidarMensajeSelecionarProveedor(), "el mensaje no se muestra de la manera correcta");
		
		/*
		 * 55	Clic en la sección “Observaciones Proveedor”.
		 */
		Thread.sleep(5000); 
		addStep("Clic en la sección “Observaciones Proveedor”.");
		OxxoAlDia.CampoObservacionesProveedor();
		
		/*
		 * 56	Seleccionar “OTROS”
		 */
		addStep("Seleccionar “OTROS”");
		OxxoAlDia.LlenarObservacionesOtros();

		/*
		 * 57	"Validar mensaje de ayuda:
		 * PROVEEDOR
		 * Escribe un comentario a modo de observación respecto al acuerdo de entrega de la OC.
		 */
		addStep("Validar mensaje:"
				+ " PROVEEDOR  Escribe un comentario a modo de observación respecto al acuerdo de entrega de la OC.");
		assertTrue(OxxoAlDia.ValidarMensajeComentarioProveedor(),"Error el mensaje no se muestra de manera correcta");
		
		
		/*
		 * 58	Seleccionar campo de comentario
		 */
		/*
		 * 59	Se escribe comentario manualmente
		 */
		addStep("Seleccionar campo de comentario", "Se escribe comentario manualmente");
		OxxoAlDia.ingresarComentarioProveedor("Un Comentario");
		
		/*
		 * 60 Validar mensaje de ayuda:
		 * PROVEEDOR
		 * Haz clic sobre el recuadro destinado para capturar tu firma.
		 */
		addStep("Validar mensaje de ayuda: "
				+ "PROVEEDOR  Haz clic sobre el recuadro destinado para capturar tu firma.");
		assertTrue(OxxoAlDia.ValidarMensajeFirmaProveedor(), "El mensaje no se muestra de la mandera correcta");
		 
		
		/*
		 * 61	Seleccionar campo Firma Proveedor
		 */
		addStep("Seleccionar campo Firma Proveedor");
		OxxoAlDia.campoFirmaPrveedor();
		/*
		 * 62	Validar mensaje de ayuda:
		 * Firma dentro del recuadro para el acuerdo de entrega.
		 */
		addStep("Validar mensaje: Firma dentro del recuadro para el acuerdo de entrega.");
		assertTrue(OxxoAlDia.ValidarMensajeDentroFirma(),"El mensaje no se muestra de la manera correcta");
		
		/*
		 * 63	Se agrega firma
		 */
		addStep("Se agrega firma");
		OxxoAlDia.clicfirma();
		OxxoAlDia.ingresarFirma();
		
		/*
		 * 64	Validar mensaje de ayuda: Da clic en “Continuar” para seguir con el proceso.
		 */
		addStep("Validar mensaje de ayuda: Da clic en “Continuar” para seguir con el proceso.");
		assertTrue(OxxoAlDia.ValidarMensajeFirmaContinuar(),"El mensaje no se muestra la manera Correcta");
		
		/*
		 * 65	Seleccionar “CONTINUAR”
		 */
		addStep("Seleccionar [CONTINUAR]");
		OxxoAlDia.seleccionarContinuar();
		
		/*
		 * 66	Validar mensaje de ayuda:
		 * TIENDA
		 * El siguiente paso es seleccionar una opción en la sección “Observaciones Personal Tienda".
		 * Ejemplo: Selecciona ‘Sin Comentarios” en esta ocasión."
		 */
		addStep("Validar mensaje de ayuda:" 
				+ "TIENDA"
				+ "El siguiente paso es seleccionar una opción en la sección “Observaciones Personal Tienda\""
				+ "Ejemplo: Selecciona ‘Sin Comentarios” en esta ocasión.");
		assertTrue(OxxoAlDia.ValidarMensajeObservacionesTienda(),"El mensaje no se muesta de manera correcta");
		
		/*
		 * 67	Clic en el campo Observaciones Personal Tienda
		 */
		addStep("Clic en el campo Observaciones Personal Tienda");
		OxxoAlDia.CampoObservacionesTienda();

		/*
		 * 68	Seleccionar “SIN COMENTARIOS”
		 */
		addStep("Seleccionar “SIN COMENTARIOS”");
		OxxoAlDia.LlenarObservacionesSinComentarios();
		
		/*
		 * 69	Validar mensaje de ayuda: 
		 * Haz clic sobre el recuadro destinado para la firma del personal de tienda.
		 */
		addStep("Validar mensaje de ayuda: "
			+ "Haz clic sobre el recuadro destinado para la firma del personal de tienda.");
		assertTrue(OxxoAlDia.ValidarMensajeRecuadroFirmaTienda(),"El mensaje no se muestra de manera correcta");
		
		
		/*
		 * 70	Seleccionar campo Firma Personal Tienda
		 */
		addStep("Seleccionar campo Firma Personal Tienda");
		OxxoAlDia.clicRecuadroFrimaTienda();
		
		
		/*
		 * 71	Validar mensaje de ayuda:
		 * Firma dentro del recuadro para el acuerdo de entrega.
		 */
		addStep("Validar mensaje de ayuda: Firma dentro del recuadro para el acuerdo de entrega.");
		assertTrue(OxxoAlDia.ValidarMensajeDentroFirma(),"El mensaje no se muestra de manera correcta");
		
		
		/*
		 * 72	Se agrega firma
		 */
		addStep("Se agrega firma");
		OxxoAlDia.clicfirma();
		OxxoAlDia.ingresarFirma();
		/*
		 * 73	Validar mensaje de ayuda:
		 * Da clic en “Continuar” para seguir con el proceso.
		 */
		addStep("Validar mensaje: Da clic en “Continuar” para seguir con el proceso.");
		assertTrue(OxxoAlDia.ValidarMensajeFirmaContinuar(),"El mensaje no se muestra de manera correcta");
		
		/*
		 * 74	Seleccionar “CONTINUAR”
		 */
		addStep("Seleccionar “CONTINUAR”");
		OxxoAlDia.seleccionarContinuar();
		
		/*
		 * 75	Validar mensaje de ayuda:
		 * Selecciona la opción para confirmar que se cumplió con el proceso de entrega.
		 */
		addStep("Validar mensaje: Selecciona la opción para confirmar que se cumplió con el proceso de entrega.");
		assertTrue(OxxoAlDia.ValidarMensajeSeCumplioProcesoEntrega(),"El mensaje no se muestra de manera correcta");
		
		
		/*
		 * 76	Seleccionar “se cumplió con el proceso de entrega”
		 */
		addStep("Seleccionar “se cumplió con el proceso de entrega”");
		OxxoAlDia.CheckSeCumplioProcesoEntrega();
		
		/*
		 * 77	Validar mensaje de ayuda:
		 * Haz clic en “Terminar” para finalizar el proceso de acuerdo de entrega.
		 */
		addStep("Validar mensaje: Haz clic en “Terminar” para finalizar el proceso de acuerdo de entrega.");
		assertTrue(OxxoAlDia.ValidarMensajeTerminarParaFinalizar(),"El mensaje no se muestra de manera correcta");
		
		/*
		 * 78	Seleccionar “TERMINAR”
		 */
		addStep("Seleccionar [TERMINAR]");
		OxxoAlDia.opcionTerminar();
		
		/*
		 * 79	Validar mensaje de ayuda:
		 * Se genera la Tira de Entrega del pedido, posteriormente da clic en el botón “Finalizar” en la parte inferior de la pantalla para terminar con el proceso.
		 * Los comprobantes oficiales se visualizarán directamente en el portal de proveedores COMETA.
		 * Botón “CONTINUAR”
		 */
		addStep("Validar mensaje de ayuda:"
				+ "Se genera la Tira de Entrega del pedido, posteriormente da clic en el botón “Finalizar” en la parte inferior de la pantalla para terminar con el proceso. "
				+ "Los comprobantes oficiales se visualizarán directamente en el portal de proveedores COMETA. "
				+ "Botón “CONTINUAR” ");
		assertTrue(OxxoAlDia.ValidarMensajeTiraEntregaPedido(),"El mensaje no se muestra de manera correcta");
		
		
		/*
		 * 80	Seleccionar “CONTINUAR”
		 */
		addStep("Seleccionar “CONTINUAR”");
		OxxoAlDia.selecionarContinuar();
		
		
		/*
		 * 81	Seleccionar “FINALIZAR”
		 */
		addStep("Seleccionar “FINALIZAR”");
		OxxoAlDia.opcionFinalizar();
		
		
		/*
		 * 82	Validar pantalla de felicitaciones:
		 * Icono paloma
		 * ¡Felicidades!
		 * Se ha concluido con la Capacitación de Visita Guiada.
		 * Todos los datos capturados dentro de esta capacitación fueron de prueba, por lo que no se verán afectados los inventarios de la tienda.
		 * Botón “FINALIZAR”
		 */
		addStep("Validar pantalla de Felicitaciones \n"
				+ "Icono paloma \n"
				+ "Se ha concluido con la Capacitación de Visita Guiada. \n"
				+ "Todos los datos capturados dentro de esta capacitación fueron de prueba, \n"
				+ "por lo que no se verán afectados los inventarios de la tienda.");
		assertTrue(OxxoAlDia.ValidarPantallaFelicitaciones(),"la pantalla felicitaciones no se muestra de la manera Correcta");
		
	}
	
	/*
	 * Proveedores Directos MVP3:
	 * MTC-FT-149 Flujo de capacitación Surtido Preventa
	 * Desc: Validar la secuencia y guía correcta en el flujo de capacitación al proveedor para una recepción tipo Surtido Preventa, que la leyenda informativa "Estas realizando un proceso en la visita guiada de capacitación, estos datos no serán guardados o almacenados en el punto de venta" sea visible durante todo el flujo.
	 * 
	 * @autor Miguel Bautista Marroquin
	 * 
	 * @date 25/10/2024
	 */

	@Override
	public String setTestFullName() {
		return "ATC_FT_087_CapacitacionSurtidoPreventa";
	}

	@Override
	public String setTestDescription() {
		return "Validar la secuencia y guía correcta en el flujo de capacitación al proveedor para una recepción tipo Surtido Preventa, que la leyenda informativa \"Estas realizando un proceso en la visita guiada de capacitación, estos datos no serán guardados o almacenados en el punto de venta\" sea visible durante todo el flujo.";
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
