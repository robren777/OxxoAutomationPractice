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

public class ATC_FT_059_CapacitacionSurtidoTradicional extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_059_CapacitacionSurtidoTradicional_test(HashMap<String, String> data)
			throws Exception {
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

		OAD OxxoAlDia = new OAD(testCase, appiumUtil, this);
		Thread.sleep(5000);

		// =========================================Inicio Ejecucion=========================================================//
		
		/*
		 * 1	Validar que se muestre pantalla de “Bienvenida”
		 */
		addStep("Validar que se muestre pantalla de “Bienvenida”");
		assertTrue(ProveedoresD.pantallaPrincipalProveedoresDirectos(),
				"La pantalla principal de la App Proveedores Directos no se muestra de manera correcta.");

		/*
		 * 2	Seleccionar “Capacitación con Visita Guiada”
		 */
		addStep("Seleccionar “Capacitación con Visita Guiada”");
		assertTrue(ProveedoresD.VGCapacitacion(), "Boton no disponible: \"Capacitacion con Visita Guiada\"");

		/*
		 * 3	Seleccionar “Surtido Tradicional (Venta a bordo)”
		 */
		addStep("Seleccionar “Surtido Tradicional (Venta a bordo)”");
		assertTrue(ProveedoresD.VGSutidoTradicional(), "Boton no disponible: \"Surtido Tradicional (Venta a bordo)\"");

		/*
		 * 4	Seleccionar “Por Captura Manual Escaneando artículo por artículo”
		 */
		addStep("Seleccionar “Por Captura Manual Escaneando artículo por articulo”");
		assertTrue(ProveedoresD.VGCapturaManual(), "Boton no disponible: Por Captura Manual");
		
		/*
		 * 5	Validar que se muestre pantalla “Generación De Pedido”
		 * 6	Validar mensaje: Estás realizando un proceso en la visita guiada... Ver mas - Botón “Salir”
		 * Nota: este mensaje se mostrara durante toda la visita guiada"
		 */
		addStep("Validar mensaje: Estás realizando un proceso en la visita guiada... Ver mas - Botón [Salir] "
				+ "Nota: este mensaje se mostrara durante toda la visita guiada");
		assertTrue(ProveedoresD.validarAvisoVisitaGuiada(), "error no se mestra aviso de manera correcta");
		
		
		/*
		 * 7	Validar mensaje de ayuda: Selecciona la opción "Generar Pedido y Entrega (Tradicional)" para iniciar el proceso de recepción de mercancía.
		 */
		addStep("Validar que se muestre pantalla “Generación De Pedido”"
				+ "Validar mensaje: Estás realizando un proceso en la visita guiada... Ver mas Botón “Salir”\n\n"
				+ "Validar mensaje de ayuda: Selecciona la opción \"Generar Pedido y Entrega (Tradicional)\" "
				+ "para iniciar el proceso de recepción de mercancía.");
		assertTrue(ProveedoresD.ValidarMensajeGeneracionPedidoEntrega(),
				"La pantalla Generacion de pedido no se muestra de la manera correcta");
		/*
		 * 8	Seleccionar “Generar Pedido y Entrega (Tradicional)”
		 */
		addStep("Seleccionar “Generar Pedido y Entrega (Tradicional)”");
		ProveedoresD.VGGenerarPedidoyEntrega();
		
		
		/*
		 * 9	Validar que se muestre mensaje de ayuda: Captura tu Fuente de abasto, mejor conocido como número de proveedor.
		 * 10	Llenar campo con fuente de bastos
		 */
		
		addStep("Validar que se muestre mensaje de ayuda: Captura tu Fuente de abasto, mejor conocido como número de proveedor."
				+ "\nLlenar campo con fuente de bastos");
		assertTrue(ProveedoresD.VGFuenteAbastoTB(), "No se muestra el texto de manera correcta.");
		ProveedoresD.precionaFuenteAbasto();
		/*
		 * 11	Validar mensaje de ayuda: Inicia con el registro del pedido, ya sea por medio de escaneo o por ingreso manual de código de barras. Da clic sobre el ícono de la palomita para agregar los artículos.
		 */
		addStep("Validar mensaje de ayuda: Inicia con el registro del pedido, ya sea por medio de escaneo o por ingreso manual de código de barras. Da clic sobre el ícono de la palomita para agregar los artículos.");
		assertTrue(ProveedoresD.VGTextoCodigoDeBarras(), "paso 11: no se muestra el mensaje de manera correcta");

		/*
		 * 12	Llenar campo de código de barras
		 */
		addStep("Llenar campo de código de barras");
		ProveedoresD.VGCodigoDeBarras();

		/*
		 * 13	Seleccionar icono de la paloma
		 */
		addStep("Seleccionar icono de la paloma");
		Thread.sleep(2000);
		ProveedoresD.VGpaloma();
		/*
		 * 14	Validar mensaje de ayuda: Por cada artículo puedes visualizar el identificador, descripción y código. También puedes editar la cantidad, así como eliminarlo del listado. Haz clic en el botón "+" o "-" para editar la cantidad.
		 */
		addStep("Validar mensaje de ayuda: Por cada artículo puedes visualizar el identificador, descripción y código. También puedes editar la cantidad, así como eliminarlo del listado. Haz clic en el botón \"+\" o \"-\" para editar la cantidad.");
		assertTrue(ProveedoresD.VGtxtPaso14(), "paso 14: no se muestra el mensaje de manera correcta");
		ProveedoresD.precionaFuenteAbasto();
		/*
		 * 15	Seleccionar botón “+”
		 */
		addStep("Seleccionar botón “+”");
		ProveedoresD.VGsumar();
		/*
		 * 16	Validar mensaje de ayuda: Al agregar artículos o editar las unidades de cada uno, se actualizan en tiempo real los campos de resumen en la parte inferior de la pantalla
		 * 17	Clic en la pantalla
		 */
		addStep("Validar mensaje de ayuda: Al agregar artículos o editar las unidades de cada uno, se actualizan en tiempo real los campos de resumen en la parte inferior de la pantalla", "Clic en la pantalla");
		assertTrue(ProveedoresD.VGtxtPaso15(), "paso 16: no se muestra el mensaje de manera correcta");
		
		//clic pantalla
		Thread.sleep(4000);
		ProveedoresD.VGsumar();
		/*
		 * 18	"Validar mensaje de ayuda:	Haz clic en “Continuar” cuando finalices la orden de compra"
		 */
		addStep("Validar mensaje:	Haz clic en “Continuar” cuando finalices la orden de compra");
		assertTrue(ProveedoresD.VGmensajeContunuar(), "paso 18 no se muestra el mensaje de manera correcta");
		ProveedoresD.VGcontinuar();
		/*
		 * 19	Validar mensaje de ayuda: Confirma la captura del pedido en la ventana emergente.
		 */
		addStep("Validar mensaje de ayuda: Confirma la captura del pedido en la ventana emergente.");
		assertTrue(ProveedoresD.validaMensajeConfirmaCaptura(),"paso 19 El mensaje no se muestra de la manera correcta");
		
		/*
		 * 20	Seleccionar “SI”
		 */
		addStep("Seleccionar [SI]");
		ProveedoresD.VGsi();

		/*
		 * 21	Validar mensaje de ayuda: Este es el resumen de pedido que se debe entregar en la tienda, en el cual se deben marcar los artículos una vez descargados del camión, ya sea uno por uno o todos a la vez.
		 * IMPORTANTE: Si no seleccionas uno de los SKU's de este resumen, no se considerarán dentro del pedido a entregar en la tienda.
		 */
		addStep("Validar mensaje de ayuda: Este es el resumen de pedido que se debe entregar en la tienda, en el cual se deben marcar los artículos una vez descargados del camión, ya sea uno por uno o todos a la vez. "
				+ "IMPORTANTE: Si no seleccionas uno de los SKU's de este resumen, no se considerarán dentro del pedido a entregar en la tienda.");
		boolean validacion = ProveedoresD.VGmensajeresumenArticulos();
		assertTrue(validacion, "error, el mensaje no se muestra de manera correcta");
		
		/*
		 * 22	Seleccionar botón check 
		 */
		addStep("Seleccionar botón check");
		ProveedoresD.VGcheck();

		/*
		 * 24	"Validar mensaje de ayuda: Haz clic en "Generar QR" cuando finalices la descarga de los artículos del pedido y te encuentres con el equipo de tienda para la verificación de la mercancía.
		 * IMPORTANTE: Si en dado caso requieres modificar el pedido, da clic en el botón
		 * ""Modificar pedido"". "
		 */		
		addStep("Validar mensaje: Validar mensaje de ayuda: Haz clic en \"Generar QR\" cuando finalices la descarga de los artículos del pedido ");
		assertTrue(ProveedoresD.VGmensajeGenerarQR(), "paso 24 El mensaje no se muestra de la manera correcta");
		
		/*
		 * 25	Seleccionar “GENERAR QR”
		 */
		addStep("Seleccionar “GENERAR QR”");
		ProveedoresD.VGgenerarQR();
		Thread.sleep(4000);
		
		//ProveedoresD.ScrollPasosLecturaQR();
		/*
		 * 26	"Validar mensaje de ayuda: Se genera un código QR el cual incluye los artículos del pedido realizado, este mismo. ser leído con la Hand Held por parte del equipo de la tienda OXXO. Continúa la visita guiada directamente en la HandHeld:
		 * Botón: Ver pasos para lectura de QR en la Hand Held"
		 */
		addStep("Validar mensaje de ayuda: Se genera un código QR el cual incluye los artículos del pedido realizado, este mismo. ser leído con la Hand Held por parte del equipo de la tienda OXXO. Continúa la visita guiada directamente en la HandHeld:");
		assertTrue(ProveedoresD.VGpantallaQR(), "paso 26 la pantalla no se muesta como deberia");

		/*
		 * 27	Seleccionar botón “Ver pasos para lectura de QR en la Hand Held”
		 */
		addStep("Seleccionar botón “Ver pasos para lectura de QR en la Hand Held”");
		ProveedoresD.VGPasosLecturaQR();
		/*
		 * 28	"Validar mensaje de ayuda:
		 * Pasos para lectura de QR en Hand Held
		 * Excelencia Operativa >
		 * Herramienta de Compra >
		 * Herramienta de Compra COMETA >
		 * Verificación de Mercancía Pedidos Proveedor Directo >
		 * Leer QR para Preventa o Pedido
		 * 
		 */

		addStep("Validar mensaje de ayuda: Pasos para lectura de QR en Hand Held \n" + "- Excelencia Operativa >\n"
				+ "- Herramienta de Compra >\n" + "- Herramienta de Compra COMETA >\n"
				+ "- Verificación de Mercancía Pedidos Proveedor Directo >" + "- Leer QR para Preventa o Pedido");
		assertTrue(OxxoAlDia.ValidarMensajePasosLecturaQR(),"paso 28 El mensaje no se muestra de la manera correcta");
		OxxoAlDia.copiarQR();
		
		// cerrar app proveedores
		Thread.sleep(5000);
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");

		// abrir app Oxxo al Dia
		appiumUtil.driver.activateApp(data.get("appPackage")+"/"+data.get("appActivity"));
		Thread.sleep(5000);

		Login login = new Login(testCase, appiumUtil, this);

		addStep("Abrir aplicación Mi OXXO al día");
		login.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));
		
		OxxoAlDia.ingresarModuloHerramientas();
		OxxoAlDia.opcionVerificacionMercancia();
		OxxoAlDia.opcionleerQR();
		Thread.sleep(4000);
		OxxoAlDia.pegarQR();
		
		Thread.sleep(2000);
		OxxoAlDia.ValidarMensajeQRPrueba();
		
		/*
		 * 39	"Validar mensaje de ayuda: 
		 * Captura el número de remisión, este mismo te lo proporciona el proveedor en el paso previo a la verificación de la mercancía. "
		 */
		addStep("Validar mensaje: Captura el número de remisión, este mismo te lo proporciona el proveedor en el paso previo a la verificación de la mercancía.");
		assertTrue(OxxoAlDia.ValidarMensajeRemision(),"paso 39 El mensaje no se muestra de la manera correcta");
		Thread.sleep(2000);
		
		/*
		 * 40	Seleccionar campo de numero de remisión
		 * 41	Clic en pantalla
		 */
		addStep("Ingresar número de remisión manualmente");
		OxxoAlDia.ingresarNoRemision("1398");
		 /* 
		 * 
		 * 42	"Validar mensaje de ayuda:
		 * Se visualiza los artículos a verificar, En caso de haber incidencias, se clasifica en dañado, caduco o faltante.
		 * Ejemplo: Agrega 1 unidad en Caducos. 
		 * IMPORTANTE: Si en dado caso se detectan incidencias durante la verificación, se solicitará que se verifique un porcentaje mayor del pedido original"
		 */

		addStep("Validar mensaje: Se visualiza los artículos a verificar, En caso de haber incidencias, se clasifica en dañado, caduco o faltante."
				+ "Ejemplo: Agrega 1 unidad en Caducos. "
				+ "IMPORTANTE: Si en dado caso se detectan incidencias durante la verificación, se solicitará que se verifique un porcentaje mayor del pedido original");
		assertTrue(OxxoAlDia.ValidarMensajeArticulos(),"paso 42 El mensaje no se muestra de la manera correcta");
		
		
		/*
		 * 43	seleccionar caducos
		 * 44	"Validar mensaje de ayuda:
		 * Después de haber verificado la mercancía, captura a cifra de proveedor. Esta cifra te la comparte directamente el proveedor."
		 */		
		Thread.sleep(3000);
		addStep("Validar mensaje: Después de haber verificado la mercancía, captura a cifra de proveedor. Esta cifra te la comparte directamente el proveedor.");
		assertTrue(OxxoAlDia.ValidarMensajeDespuesMercancia(),"Error El mensaje no se muestra de la manera correcta");
		/*
		 * 45	Seleccionar campo de cifra de proveedor
		 */
		addStep("Agregar cifra de proveedor manualmente");
		OxxoAlDia.ingresarCifraProveedor("1313");
		
		/*
		 * 46	"Validar mensaje de ayuda:
		 * Da clic en el botón “Terminar” para visualizar el resumen e ir al paso de captura de firmas y comentarios."
		 */
		
		addStep("Validar mensaje: Da clic en el botón “Terminar” para visualizar el resumen e ir al paso de captura de firmas y comentarios.");
		assertTrue(OxxoAlDia.ValidarMensajeTerminarVG(),"Error, el mensaje no se muestra de manera correcta");
		/*
		 * 47	Seleccionar botón “TERMINAR”
		 */
		addStep("Seleccionar botón “TERMINAR”");
		OxxoAlDia.opcionTerminar();
		/*
		 * 48	Validar mensaje de ayuda: se muestra resumen de pedido de los artículos
		 */
		Thread.sleep(4000);
		addStep("Validar mensaje: se muestra resumen de pedido de los artículos");
		assertTrue(OxxoAlDia.ValidarMensajeResumenPedido(),"paso 48 El mensaje no se muestra de la manera correcta");
		/*
		 * 49	Clic en pantalla
		 */
		OxxoAlDia.clicTabla();
		
		/*
		 * 50	"Validar mensaje de ayuda:
		 * PROVEEDOR
		 * El siguiente paso es seleccionar una opción en la sección “Observaciones Proveedor”.
		 * Ejemplo: Selecciona “Otros” para un comentario de captura libre."
		 */
		addStep("Validar mensaje de ayuda:"
				+ "PROVEEDOR El siguiente paso es seleccionar una opción en la sección “Observaciones Proveedor”.");
		assertTrue(OxxoAlDia.ValidarMensajeSelecionarProveedor(), "50 el mensaje no se muestra de la manera correcta");
		
		/*
		 * 51	Clic en la sección “Observaciones Proveedor”.
		 */
		Thread.sleep(5000); 
		addStep("Clic en la sección “Observaciones Proveedor”.");
		OxxoAlDia.CampoObservacionesProveedor();
		
		/*
		 * 52	Seleccionar “OTROS”
		 */
		addStep("Seleccionar “OTROS”");
		OxxoAlDia.LlenarObservacionesOtros();
		/*
		 * 53	"Validar mensaje de ayuda:
		 * PROVEEDOR
		 * Escribe un comentario a modo de observación respecto al acuerdo de entrega de la OC."
		 */
		addStep("Validar mensaje:"
				+ " PROVEEDOR  Escribe un comentario a modo de observación respecto al acuerdo de entrega de la OC.");
		assertTrue(OxxoAlDia.ValidarMensajeComentarioProveedor(),"paso 53 El mensaje no se muesta de mandera Correcta");
		
		/*
		 * 54	Seleccionar campo de comentario
		 * 55	Se escribe un cometario
		 */
		
		addStep("Se escribe un cometario");
		OxxoAlDia.ingresarComentarioProveedor("Un Comentario");
		
		/*
		 * 56	"Validar mensaje de ayuda:
		 * PROVEEDOR
		 * Haz clic sobre el recuadro destinado para capturar tu firma."
		 */
		addStep("Validar mensaje de ayuda: "
				+ "PROVEEDOR  Haz clic sobre el recuadro destinado para capturar tu firma.");
		assertTrue(OxxoAlDia.ValidarMensajeFirmaProveedor(), "paso 56 El mensaje no se muestra de la mandera correcta");
		
		
		/*
		 * 57	Seleccionar campo Firma Proveedor
		 */
		addStep("Clic en campo de firma");
		OxxoAlDia.campoFirmaPrveedor();

		/*
		 * 58	"Validar mensaje de ayuda:
		 * Firma dentro del recuadro para el acuerdo de entrega."
		 */
		addStep("Validar mensaje: Firma dentro del recuadro para el acuerdo de entrega.");
		assertTrue(OxxoAlDia.ValidarMensajeDentroFirma(),"paso 58 El mensaje no se muestra de la manera correcta");
		
		
		/*
		 * 59	Se agrega firma
		 */
		addStep("Se agrega firma");
		OxxoAlDia.clicfirma();
		OxxoAlDia.ingresarFirma();
		/*
		 * 60	"Validar mensaje de ayuda:
		 * Da clic en “Continuar” para seguir con el proceso."
		 */
		addStep("Validar mensaje de ayuda: Da clic en “Continuar” para seguir con el proceso.");
		assertTrue(OxxoAlDia.ValidarMensajeFirmaContinuar(),"paso 60 El mensaje no se muestra la manera Correcta");		
		
		/*
		 * 61	Seleccionar “CONTINUAR”
		 */
		addStep("Seleccionar “CONTINUAR”");
		OxxoAlDia.seleccionarContinuar();
		
		
		/*
		 * 62	"Validar mensaje de ayuda:
		 * TIENDA
		 * El siguiente paso es seleccionar una opción en la sección “Observaciones Personal Tienda"".
		 * Ejemplo: Selecciona ‘Sin Comentarios” en esta ocasión."
		 * 
		 */
		addStep("Validar mensaje de ayuda:" + "TIENDA"
				+ "El siguiente paso es seleccionar una opción en la sección “Observaciones Personal Tienda\""
				+ "Ejemplo: Selecciona ‘Sin Comentarios” en esta ocasión.");
		assertTrue(OxxoAlDia.ValidarMensajeObservacionesTienda(),"paso 62 El mensaje no se muesta de manera correcta");
		
		
		/*
		 * 63	Clic en el campo Observaciones Personal Tienda
		 */
		addStep("Clic en el campo Observaciones Personal Tienda");
		OxxoAlDia.CampoObservacionesTienda();
		
		/*
		 * 64	Seleccionar “SIN COMENTARIOS”
		 */
		addStep("Seleccionar “SIN COMENTARIOS”");
		OxxoAlDia.LlenarObservacionesSinComentarios();
		
		/*
		 * 65	"Validar mensaje de ayuda:
		 * Haz clic sobre el recuadro destinado para la firma del personal de tienda."
		 */
		addStep("Validar mensaje de ayuda: Haz clic sobre el recuadro destinado para la firma del personal de tienda.");
		assertTrue(OxxoAlDia.ValidarMensajeRecuadroFirmaTienda(),"paso 65 El mensaje no se muestra de manera correcta");
		
		/*
		 * 66	Seleccionar campo Firma Personal Tienda
		 */
		addStep("Seleccionar campo Firma Personal Tienda");
		OxxoAlDia.clicRecuadroFrimaTienda();
		
		/*
		 * 67	"Validar mensaje de ayuda:
		 * Firma dentro del recuadro para el acuerdo de entrega."
		 */
		addStep("Validar mensaje de ayuda: Firma dentro del recuadro para el acuerdo de entrega.");
		assertTrue(OxxoAlDia.ValidarMensajeDentroFirma(),"paso 67 El mensaje no se muestra de manera correcta");
		
		/*
		 * 68	Se agrega firma
		 */
		addStep("Se agrega firma");
		OxxoAlDia.clicfirma();
		OxxoAlDia.ingresarFirma();
		
		/*
		 * 69	"Validar mensaje de ayuda:
		 * Da clic en “Continuar” para seguir con el proceso."
		 */
		addStep("Validar mensaje: Da clic en “Continuar” para seguir con el proceso.");
		assertTrue(OxxoAlDia.ValidarMensajeFirmaContinuar(),"paso 69 El mensaje no se muestra de manera correcta");
		
		/*
		 * 70	Seleccionar “CONTINUAR”
		 */
		addStep("Seleccionar “CONTINUAR”");
		OxxoAlDia.seleccionarContinuar();
		/*
		 * 71	"Validar mensaje de ayuda:
		 * Selecciona la opción para confirmar que se cumplió con el proceso de entrega."
		 */
		addStep("Validar mensaje:Selecciona la opción para confirmar que se cumplió con el proceso de entrega.");
		assertTrue(OxxoAlDia.ValidarMensajeSeCumplioProcesoEntrega(),"paso 71 El mensaje no se muestra de manera correcta");
		
		/*
		 * 72	Seleccionar “se cumplió con el proceso de entrega”
		 */
		addStep("Seleccionar “se cumplió con el proceso de entrega”");
		OxxoAlDia.CheckSeCumplioProcesoEntrega();
		
		/*
		 * 73	"Validar mensaje de ayuda:
		 * Haz clic en “Terminar” para finalizar el proceso de acuerdo de entrega."
		 */
		addStep("Validar mensaje: Haz clic en “Terminar” para finalizar el proceso de acuerdo de entrega.");
		assertTrue(OxxoAlDia.ValidarMensajeTerminarParaFinalizar(),"paso 73 El mensaje no se muestra de manera correcta");
		
		/*
		 * 74	Seleccionar “TERMINAR”
		 */
		addStep("Seleccionar “TERMINAR”");
		OxxoAlDia.opcionTerminar();
		
		/*
		 * 75	"Validar mensaje de ayuda:
		 * Se genera la Tira de Entrega del pedido, posteriormente da clic en el botón “Finalizar” en la parte inferior de la pantalla para terminar con el proceso.
		 * Los comprobantes oficiales se visualizarán directamente en el portal de proveedores COMETA.
		 * Botón “CONTINUAR”"
		 */
		Thread.sleep(3000);  //3 SEGUNDOS
		addStep("Validar mensaje de ayuda:"
				+ "Se genera la Tira de Entrega del pedido, posteriormente da clic en el botón “Finalizar” en la parte inferior de la pantalla para terminar con el proceso."
				+ "Los comprobantes oficiales se visualizarán directamente en el portal de proveedores COMETA. Botón “CONTINUAR”");
		assertTrue(OxxoAlDia.ValidarMensajeTiraEntregaPedido(),"paso 75 El mensaje no se muestra de manera correcta");
		
		/*
		 * 76	Seleccionar “CONTINUAR”
		 */
		addStep("Seleccionar “CONTINUAR”");
		OxxoAlDia.selecionarContinuar();
		
		/*
		 * 77	Seleccionar “FINALIZAR”
		 */
		addStep("Seleccionar “FINALIZAR”");
		OxxoAlDia.opcionFinalizar();
		
		
		
		/*
		 * 78	"Validar pantalla de felicitaciones:
		 * Icono paloma
		 * ¡Felicidades!
		 * Se ha concluido con la Capacitación de Visita Guiada.
		 * Todos los datos capturados dentro de esta capacitación fueron de prueba, por lo que no se verán afectados los inventarios de la tienda.
		 * Botón “FINALIZAR”"
		 */
		addStep("Validar pantalla de Felicitaciones \n"
				+ "Icono paloma \n"
				+ "Se ha concluido con la Capacitación de Visita Guiada. \n"
				+ "Todos los datos capturados dentro de esta capacitación fueron de prueba, \n"
				+ "por lo que no se verán afectados los inventarios de la tienda.");
		assertTrue(OxxoAlDia.ValidarPantallaFelicitaciones(),"paso 78 la pantalla felicitaciones no se muestra de la manera Correcta");
	
		/*
		 * 79	Seleccionar “FINALIZAR”
		 */
		addStep("Seleccionar “FINALIZAR”");
		OxxoAlDia.opcionTerminar();

	}
	
	/*
	 * Proveedores Directos MVP3:
	 * MTC-FT-083 Flujo de capacitación Surtido Tradicional
	 * Desc: Validar la secuencia y guía correcta en el flujo de capacitación al proveedor para una recepción tipo Tradicional,  que la leyenda informativa "Estas realizando un proceso en la visita guiada de capacitación, estos datos no serán guardados o almacenados en el punto de venta" sea visible durante todo el flujo.
	 * 
	 * @autor Miguel Angel Bautista Marroquin
	 * 
	 * @date 20/09/2024
	 * 
	 */

	@Override
	public String setTestFullName() {
		return "ATC_FT_059_CapacitacionSurtidoTradicional";
	}

	@Override
	public String setTestDescription() {
		return "Validar la secuencia y guía correcta en el flujo de capacitación al proveedor para una recepción tipo Tradicional,  que la leyenda informativa \"Estas realizando un proceso en la visita guiada de capacitación, estos datos no serán guardados o almacenados en el punto de venta\" sea visible durante todo el flujo.";
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
		return "1.Contar con la versión mas reciente del APK Proveedores directos \r\n"
				+ "2.Contar con un dispositivo móvil con Android 11";
	}

	@Override
	public void beforeTest() {

	}

}
