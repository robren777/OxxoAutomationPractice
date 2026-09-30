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

public class ATC_FT_065_CapacitacionSurtidoTradicionalHerramientaCompra extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_065_CapacitacionSurtidoTradicionalHerramientaCompra_test(HashMap<String, String> data) throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();

		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appPackage", data.get("appPackageP"));
		cap.setCapability("appActivity", data.get("appActivityP"));
		// cap.setCapability("appium:settings[allowInvisibleElements]", true);
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
		
		OAD OxxoAlDia = new OAD(testCase, appiumUtil, null);
		Login login = new Login(testCase, appiumUtil, this);
		ProveedoresDirectos ProveedoresD = new ProveedoresDirectos(testCase, appiumUtil, this);
		Thread.sleep(5000);
		
		
		/*
		 * 1	Abrir la aplicación “Proveedores directos”
		 * 2	Validar que se muestre pantalla de “Bienvenida”
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
		 * 4	Seleccionar “Surtido Tradicional (Venta a bordo)”
		 */
		addStep("Seleccionar “Surtido Tradicional (Venta a bordo)”");
		ProveedoresD.OpcionSurtidoTradicional();
		/*
		 * 5	Seleccionar “Por Herramienta de compra”
		 */
		addStep("Seleccionar “Por Herramienta de compra”");
		ProveedoresD.OpcionHerramientaCompra();
		Thread.sleep(5000);
		
		/*
		 * 6	"Validar mensaje: 
		 * Estas realizando un proceso en la visita guiada... Ver mas
		 * Botón “Salir”
		 * Nota: este mensaje se mostrará durante toda la visita guiada"
		 */
		addStep("Validar mensaje: Estas realizando un proceso en la visita guiada... Ver mas");
		assertTrue(ProveedoresD.validarAvisoVisitaGuiada(), "error no se muestra aviso");
		
		
		/*
		 * 7	"Validar mensaje: 
		 * Selecciona la opción “Escanear QR de pedido o QR de Hta. de compra” para iniciar el proceso de recepción de mercancía.
		 * Solicita al equipo de tienda que te proporcione el código QR de la herramienta de compra correspondiente a tu fuente de abasto.
		 * Botón: Ver pasos para generar QR en Hand Held"
		 */
		
		addStep("Validar mensaje: Selecciona la opción “Escanear QR de pedido o QR de Hta. de compra” para iniciar el proceso de recepción de mercancía. "
				+ "Solicita al equipo de tienda que te proporcione el código QR de la herramienta de compra correspondiente a tu fuente de abasto."
				+ "Botón: Ver pasos para generar QR en Hand Held\"");
		
		assertTrue(OxxoAlDia.ValidarMensajeEscanearQRpedidoT(), "El mensaje no se muestra de la forma correcta");
		
		/*
		 * 8	Seleccionar “Ver pasos para generar QR en Hand Held”
		 */
		addStep("Seleccionar “Ver pasos para generar QR en Hand Held”");
		ProveedoresD.opcionPedidoQR();
		
		
		/*
		 * 9	Validar que se muestre mensaje de ayuda: 
		 * Pasos para lectura de QR en Hand Held
		 * 		Excelencia Operativa >
		 * Herramienta de Compra >
		 * Herramienta de Compra COMETA >
		 * Verificación de Mercancía Pedidos Proveedor Directo >
		 * Generación de Herramienta de compra 
		 * Botón: Siguiente
		 */
		
		addStep("Verificar mensaje :"
				+ "Pasos en HH para abrir Herramienta de Compra \r\n"
				+ "Excelencia Operativa > \r\n"
				+ "Herramienta de Compra > \r\n"
				+ "Herramienta de Compra COMETA > \r\n"
				+ "Verificación de Mercancía Pedidos \r\n"
				+ "Proveedor Directo > \r\n"
				+ "Generación de Herramienta de Compra \r\n"
				+ "botón [Siguiente] ");
		boolean validacion = ProveedoresD.MensajePasosLecturaQR();
		assertTrue(validacion, "error, No se muestra de manera correcta el mensaje de visita guiada");

		
		
		/*
		 * 10	Seleccionar “siguiente”
		 */
		addStep("Seleccionar “siguiente”");
		ProveedoresD.opcionSiguiente();
		
		/*
		 * 11	Validar mensaje:
		 * Selecciona la opción “Continuar” para escanear el código QR de la herramienta de compra.
		 */
		addStep("Validar mensaje: Selecciona la opción “Continuar” para escanear el código QR de la herramienta de compra.");
		ProveedoresD.validarMensajeContunuar();
		
		/*
		 * 12	Seleccionar “CONTINUAR”
		 */
		addStep("Seleccionar [CONTINUAR]");
		ProveedoresD.VGcontinuar();
		/*
		 * 13	Escanear QR
		 */
		
		addStep("pegar QR");
		OxxoAlDia.pegarQRp();
		Thread.sleep(5000);
		
		/*
		 * se muestra warning “¿Desea aplicar  el sugerido a todos los artículos?”
		 * Opción: NO  Opción: SI
		 */
		addStep("¿Desea aplicar  el sugerido a todos los artículos?");
		validacion = ProveedoresD.ValidacionWarning();
		assertTrue(validacion, "Error, no se muestra el warning correcto");
		
		/*
		 * 15	Validar mensaje: Al abrir la herramienta de compra en el celular podrás aplicar el sugerido a todos los artículos activos en la tienda o aplicarlo de manera manual.
		 */
		addStep("Validar mensaje: Al abrir la herramienta de compra en el celular podrás aplicar el sugerido a todos los artículos activos en la tienda o aplicarlo de manera manual.");
		validacion = ProveedoresD.MensajeWarning();
		assertTrue(validacion,"Error el mensaje no se visualiza correctamente");
		
		/*
		 * 16	Seleccionar botón “SI”
		 */
		ProveedoresD.VGsi();
		
		
		/*
		 * 17	Validar mensaje de ayuda: 
		 * Al visualizar la herramienta de compra podrás capturar la cantidad de pedido para cada artículo, en donde podrás visualizar también el inventario de cada uno y el sugerido de compra
		 */
		addStep("Validar mensaje de ayuda: Al visualizar la herramienta de compra podrás capturar la cantidad de pedido para cada artículo, "
				+ "en donde podrás visualizar también el inventario de cada uno y el sugerido de compra", "Clic en la pantalla");
		 validacion = ProveedoresD.MensajeVisualizarHC();
		assertTrue(validacion, "Error el mensaje no se visualiza correctamente");

		/*
		 * 18	Clic en la pantalla
		 */
		ProveedoresD.clicSumar();
		
		/*
		 * 19	Validar mensaje: Presiona el botón “+” para incrementar la cantidad de pedido del artículo.
		 * También podrás capturar de manera manual la cantidad en caso que así lo requieras.
		 */
		addStep("Validar mensaje: Presiona el botón “+” para incrementar la cantidad de pedido del artículo. "
				+ "También podrás capturar de manera manual la cantidad en caso que así lo requieras.", "Seleccionar [+]");
		validacion = ProveedoresD.MensajeBotonMas();
		assertTrue(validacion, "Error el mensaje no se visualiza correctamente");
		/*
		 * 20	Seleccionar “+”
		 */
		ProveedoresD.clicSumar();
		ProveedoresD.clicSumar();
		ProveedoresD.clicSumar();
		Thread.sleep(2000);
		
		/*
		 * 21	"Validar mensaje: Después de haber modificado los artículos para la generación del pedido, daremos clic en el botón “Continuar” en la parte inferior de la pantalla. Al dar clic se generará el QR, el cual debe ser escaneado con la Hand Held"
		 */
		addStep("Validar mensaje: Después de haber modificado los artículos para la generación del pedido, "
				+ "daremos clic en el botón “Continuar” en la parte inferior de la pantalla. Al dar clic se generará el QR, "
				+ "el cual debe ser escaneado con la Hand Held","Seleccionar [CONTINUAR]");
		validacion = ProveedoresD.MensajeModificadoArticulos();
		assertTrue(validacion, "Error el mensaje no se visualiza correctamente");
		
		/*
		 * 22	Seleccionar “CONTINUAR”
		 */
		ProveedoresD.clicContiuar();
		
		/*
		 * 23	Validar mensaje:
		 * Pasos para lectura de QR en Hand Held
		 * Excelencia Operativa >
		 * Herramienta de Compra >
		 * Herramienta de Compra COMETA >
		 * Verificación de Mercancía Pedidos Proveedor Directo >
		 * Leer QR para Preventa o Pedido
		 */
		addStep("Validar que se muestre mensaje de ayuda: \r\n"
				+ "Pasos para lectura de QR en Hand Held \r\n"
				+ "Excelencia Operativa > \r\n"
				+ "Herramienta de Compra > \r\n"
				+ "Herramienta de Compra COMETA > \r\n"
				+ "Verificación de Mercancía Pedidos Proveedor Directo > \r\n"
				+ "Leer QR para Preventa o Pedido ");
		validacion = OxxoAlDia.ValidarMensajePasosLecturaQR();
		assertTrue(validacion, "Error, no se muestra mensaje pasos para lectura de qr");
		
		//copiar codigo QR
		OxxoAlDia.copiarQR();
		// cerrar app proveedores
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");

		
		/*
		 * 24	Abrir app Oxxo Al Día: Excelencia operativa>Herramienta de compra> Cometa> Verificación de Mercancía Pedidos Proveedor Directo > Leer QR para Preventa o Pedido
		 */
		addStep("Abrir app Oxxo Al Día: "
				+ "Excelencia operativa>Herramienta de compra> "
				+ "Cometa> "
				+ "Verificación de Mercancía Pedidos Proveedor Directo > "
				+ "Leer QR para Preventa o Pedido ");
		
		// abrir app Oxxo al Dia
		appiumUtil.driver.activateApp(data.get("appPackage")+"/"+data.get("appActivity"));
		Thread.sleep(5000);
		login.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));
		
		OxxoAlDia.ingresarModuloHerramientas();
		OxxoAlDia.opcionVerificacionMercancia();
		
		/*
		 * 25	Seleccionar “NO”
		 */
		addStep("Seleccionar “NO”");
		OxxoAlDia.opcionleerQR();
		
		/*
		 * 26	Escanear QR
		 */
		addStep("Escanear QR");
		Thread.sleep(4000);
		OxxoAlDia.pegarQR();
		
		/*
		 * 27	Seleccionar “ACEPTAR”
		 */
		addStep("Seleccionar “ACEPTAR”");
		Thread.sleep(3000);
		assertTrue(OxxoAlDia.ValidarMensajeQRPrueba(),"No se muestra mensaje correctamente");
		//OxxoAlDia.aceptar();
		
		Thread.sleep(3000);
		
		/*
		 * 28	Validar mensaje: Una vez escaneado el QR con la Hand Held, nos solicitará capturar el número de remisión, este mismo te lo proporciona el proveedor.
		 */
		addStep("Validar mensaje: Una vez escaneado el QR con la Hand Held, nos solicitará capturar el número de remisión, este mismo te lo proporciona el proveedor.");
		validacion = OxxoAlDia.ValidarMensajeDespuesdeQR();
		assertTrue(validacion,"Error, no se meustra correctamente el mensaje, Una vez escaneado el QR con la Hand Held...");
		
		/*
		 * 29	Ingresar número de remisión manualmente
		 */
		addStep("Ingresar número de remisión manualmente");
		OxxoAlDia.ingresarNoRemision("1398");
		
		/*
		 * 30	Validar mensaje: Una vez capturado el número de remisión, estaremos realizando la verificación de mercancía del pedido generado,
		 */
		addStep("Validar mensaje: Una vez capturado el número de remisión, estaremos realizando la verificación de mercancía del pedido generado.");
		assertTrue(OxxoAlDia.ValidarMensajeNoRemision(),"El mensaje no se muestra de la manera correcta");
		
		
		/*
		 * 31	Validar mensaje: 
		 * 	Se visualiza los artículos a verificar. En caso de haber incidencias, se clasifica en dañado, caduco o faltante.
		 * 
		 * Ejemplo: Resta 1 pieza y en automático se clasificará como faltante.
		 * 
		 * IMPORTANTE: Si en dado caso se detectan incidencias durante la verificación, el sistema te solicitará verificar más artículos del pedido original
		 */
		
		addStep("Validar mensaje de ayuda:"
				+ "Se visualiza los artículos a verificar, En caso de haber incidencias, se clasifica en dañado, caduco o faltante.");
		assertTrue(OxxoAlDia.ValidarVisualizacionArticulo(),"paso 31 el mensaje no se muestra de la manera correcta");
		
		/*
		 * 32	Seleccionar “-”
		 */
		addStep("Seleccionar “-”");
		OxxoAlDia.opcionRestar();
		
		/*
		 * 33	Clic pantalla
		 * 34	Validar mensaje: Después de haber verificado la mercancía, captura a cifra de proveedor. Esta cifra te la comparte directamente el proveedor.
		 */
		addStep("Validar mensaje: Después de haber verificado la mercancía, captura a cifra de proveedor. Esta cifra te la comparte directamente el proveedor.", "Clic pantalla");
		assertTrue(OxxoAlDia.ValidarMensajeDespuesMercancia(),"paso 35 el mensaje no se muestra de la manera correcta");
		
		/*
		 * 35	Agregar cifra de proveedor manualmente
		 */
		addStep("Agregar cifra de proveedor manualmente");
		OxxoAlDia.ingresarCifraProveedor("1313");
		
		/*
		 * 36	Validar mensaje: Da clic en el botón “Terminar” para visualizar el resumen e ir al paso de captura de firmas y comentarios.
		 */
		addStep("Validar mensaje: Da clic en el botón “Terminar” para visualizar el resumen e ir al paso "
				+ "de captura de firmas y comentarios.");
		assertTrue(OxxoAlDia.ValidarMensajeTerminarVG(),"40 el mensaje no se muestra de la manera correcta");
		
		/*
		 * 37	Seleccionar “TERMINAR”
		 */
		addStep("Seleccionar [TERMINAR]");
		OxxoAlDia.opcionTerminar();
		
		/*
		 * 38	Validar mensaje de ayuda: se muestra resumen de pedido de los artículos
		 */
		addStep("Validar mensaje de ayuda: se muestra resumen de pedido de los artículos");
		assertTrue(OxxoAlDia.ValidarMensajeResumenPedido(), "paso 38 el mensaje no se muestra de la manera correcta");
		
		/*
		 * 39	Clic en pantalla
		 */
		addStep("Clic en pantalla");
		OxxoAlDia.clicTabla();
		
		/*
		 * 40	Validar mensaje de ayuda:
		 * PROVEEDOR
		 * El siguiente paso es seleccionar una opción en la sección “Observaciones Proveedor”.
		 * 
		 * Ejemplo: Selecciona “Otros” para un comentario de captura libre.
		 */
		addStep("Validar mensaje de ayuda:"
				+ "PROVEEDOR El siguiente paso es seleccionar una opción en la sección “Observaciones Proveedor”.");
		assertTrue(OxxoAlDia.ValidarMensajeSelecionarProveedor(), "42 el mensaje no se muestra de la manera correcta");
		Thread.sleep(5000);
		
		/*
		 * 41	Clic en la sección “Observaciones Proveedor”.
		 */
		addStep("Clic en la sección “Observaciones Proveedor”.");
		OxxoAlDia.CampoObservacionesProveedor();
		
		/*
		 * 42	Seleccionar “OTROS”
		 */
		addStep("Seleccionar “OTROS”");
		OxxoAlDia.LlenarObservacionesOtros();
		
		/*
		 * 43	Validar mensaje de ayuda:
		 * PROVEEDOR
		 * Escribe un comentario a modo de observación respecto al acuerdo de entrega de la OC.
		 */
		addStep("Validar mensaje:"
				+ " PROVEEDOR  Escribe un comentario a modo de observación respecto al acuerdo de entrega de la OC.");
		assertTrue(OxxoAlDia.ValidarMensajeComentarioProveedor(),"El mensaje no se muesta de mandera Correcta");
		
		/*
		 * 44	Seleccionar campo de comentario
		 * 45	Se escribe comentario manualmente
		 */
		addStep("Se escribe comentario manualmente");
		OxxoAlDia.ingresarComentarioProveedor("Un Comentario");
		
		/*
		 * 46	Validar mensaje de ayuda:
		 * PROVEEDOR 
		 * Haz clic sobre el recuadro destinado para capturar tu firma.
		 */
		addStep("Validar mensaje de ayuda: "
				+ "PROVEEDOR  Haz clic sobre el recuadro destinado para capturar tu firma.");
		assertTrue(OxxoAlDia.ValidarMensajeFirmaProveedor(), "El mensaje no se muestra de la mandera correcta");
		
		/*
		 * 47	Seleccionar campo Firma Proveedor
		 */
		addStep("Seleccionar campo Firma Proveedor");
		OxxoAlDia.campoFirmaPrveedor();
		
		/*
		 * 48	Validar mensaje de ayuda:
		 * Firma dentro del recuadro para el acuerdo de entrega.
		 */
		addStep("Validar mensaje: Firma dentro del recuadro para el acuerdo de entrega.");
		assertTrue(OxxoAlDia.ValidarMensajeDentroFirma(),"El mensaje no se muestra de la manera correcta");
		
		/*
		 * 49	Se agrega firma
		 */
		addStep("Se agrega firma");
		OxxoAlDia.clicfirma();
		OxxoAlDia.ingresarFirma();
		
		/*
		 * 50	Validar mensaje de ayuda: Da clic en “Continuar” para seguir con el proceso.
		 */
		addStep("Validar mensaje de ayuda: Da clic en “Continuar” para seguir con el proceso.");
		assertTrue(OxxoAlDia.ValidarMensajeFirmaContinuar(),"El mensaje no se muestra la manera Correcta");
		
		/*
		 * 51	Seleccionar “CONTINUAR”
		 * 52	Seleccionar “CONTINUAR”
		 */
		addStep("Seleccionar [CONTINUAR]");
		OxxoAlDia.seleccionarContinuar();
		
		/*
		 * 53	Validar mensaje de ayuda:
		 * TIENDA
		 * El siguiente paso es seleccionar una opción en la sección “Observaciones Personal Tienda"".
		 * 
		 * Ejemplo: Selecciona ‘Sin Comentarios” en esta ocasión.
		 */
		addStep("Validar mensaje de ayuda:" 
				+ "TIENDA"
				+ "El siguiente paso es seleccionar una opción en la sección “Observaciones Personal Tienda\""
				+ "Ejemplo: Selecciona ‘Sin Comentarios” en esta ocasión.");
		assertTrue(OxxoAlDia.ValidarMensajeObservacionesTienda(),"El mensaje no se muesta de manera correcta");
		
		/*
		 * 54	Clic en el campo Observaciones Personal Tienda
		 */
		addStep("Clic en el campo Observaciones Personal Tienda");
		OxxoAlDia.CampoObservacionesTienda();
		
		/*
		 * 55	Seleccionar “SIN COMENTARIOS”
		 */
		addStep("Seleccionar “SIN COMENTARIOS”");
		OxxoAlDia.LlenarObservacionesSinComentarios();
		
		/*
		 * 56	Validar mensaje de ayuda: Haz clic sobre el recuadro destinado para la firma del personal de tienda.
		 */
		addStep("Validar mensaje de ayuda: "
				+ "Haz clic sobre el recuadro destinado para la firma del personal de tienda.");
		assertTrue(OxxoAlDia.ValidarMensajeRecuadroFirmaTienda(),"El mensaje no se muestra de manera correcta");
		
		/*
		 * 59	Seleccionar campo Firma Personal Tienda
		 */
		addStep("Seleccionar campo Firma Personal Tienda");
		OxxoAlDia.clicRecuadroFrimaTienda();
		
		/*
		 * 57	Validar mensaje de ayuda: Firma dentro del recuadro para el acuerdo de entrega.
		 */
		addStep("Validar mensaje de ayuda: Firma dentro del recuadro para el acuerdo de entrega.");
		assertTrue(OxxoAlDia.ValidarMensajeDentroFirma(),"El mensaje no se muestra de manera correcta");
		
		/*
		 * 	58 Se agrega firma
		 */
		addStep("Se agrega firma");
		OxxoAlDia.clicfirma();
		OxxoAlDia.ingresarFirma();
		
		/*
		 * 59	Validar mensaje de ayuda:Da clic en “Continuar” para seguir con el proceso.
		 */
		addStep("Validar mensaje: Da clic en “Continuar” para seguir con el proceso.");
		assertTrue(OxxoAlDia.ValidarMensajeFirmaContinuar(),"El mensaje no se muestra de manera correcta");
		
		/*
		 * 60	Seleccionar “CONTINUAR”
		 */
		addStep("Seleccionar [CONTINUAR]");
		OxxoAlDia.seleccionarContinuar();
		
		
		/*
		 * 61	Validar mensaje de ayuda: 
		 * Selecciona la opción para confirmar que se cumplió con el proceso de entrega.
		 */
		addStep("Validar mensaje: Selecciona la opción para confirmar que se cumplió con el proceso de entrega.");
		assertTrue(OxxoAlDia.ValidarMensajeSeCumplioProcesoEntrega(),"El mensaje no se muestra de manera correcta");
		
		/*
		 * 62	Seleccionar “se cumplió con el proceso de entrega”
		 */
		addStep("Seleccionar “se cumplió con el proceso de entrega”");
		OxxoAlDia.CheckSeCumplioProcesoEntrega();
		
		/*
		 * 63	"Validar mensaje de ayuda: 
		 * Haz clic en “Terminar” para finalizar el proceso de acuerdo de entrega."
		 */
		addStep("Validar mensaje: Haz clic en “Terminar” para finalizar el proceso de acuerdo de entrega.");
		assertTrue(OxxoAlDia.ValidarMensajeTerminarParaFinalizar(),"El mensaje no se muestra de manera correcta");
		
		/*
		 * 64	Seleccionar “TERMINAR”
		 */
		addStep("Seleccionar [TERMINAR]");
		OxxoAlDia.opcionTerminar();
		
		/*
		 * 65	Validar mensaje de ayuda:
		 * Se genera la Tira de Entrega del pedido, posteriormente da clic en el botón “Finalizar” en la parte inferior de la pantalla para terminar con el proceso.
		 * 
		 * Los comprobantes oficiales se visualizarán directamente en el portal de proveedores COMETA.
		 * 
		 * Botón “CONTINUAR”
		 */
		addStep("Validar mensaje de ayuda:"
				+ "Se genera la Tira de Entrega del pedido, posteriormente da clic en el botón “Finalizar” en la parte inferior de la pantalla para terminar con el proceso. "
				+ "Los comprobantes oficiales se visualizarán directamente en el portal de proveedores COMETA. "
				+ "Botón “CONTINUAR” ");
		assertTrue(OxxoAlDia.ValidarMensajeTiraEntregaPedido(),"El mensaje no se muestra de manera correcta");
		
		/*
		 * 66	Seleccionar “CONTINUAR”
		 */
		addStep("Seleccionar “CONTINUAR”");
		OxxoAlDia.selecionarContinuar();
		
		/*
		 * 67	Seleccionar “FINALIZAR”
		 */
		addStep("Seleccionar “FINALIZAR”");
		OxxoAlDia.opcionFinalizar();
		
		/*
		 * 68	Validar pantalla de felicitaciones:
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
	 * MTC-FT-108 Flujo de capacitación Surtido Tradicional por herramienta de compra
	 * Desc: Validar la secuencia y guía correcta en el flujo de capacitación al proveedor para una recepción tipo Surtido Tradicional por herramienta de compra,  que la leyenda informativa "Estas realizando un proceso en la visita guiada de capacitación, estos datos no serán guardados o almacenados en el punto de venta" sea visible durante todo el flujo.
	 * 
	 * @autor Miguel Angel Bautista Marroquin
	 * 
	 * @date 25/10/2024
	 * 
	 */

	@Override
	public String setTestFullName() {
		return "ATC_FT_065_CapacitacionSurtidoTradicionalHerramientaCompra";
	}

	@Override
	public String setTestDescription() {
		return "Validar la secuencia y guía correcta en el flujo de capacitación al proveedor para una recepción tipo Surtido Tradicional por herramienta de compra,  que la leyenda informativa \"Estas realizando un proceso en la visita guiada de capacitación, estos datos no serán guardados o almacenados en el punto de venta\" sea visible durante todo el flujo.";
	}

	@Override
	public String setTestDesigner() {
		return "Miguel Bautista Marroquin";
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
