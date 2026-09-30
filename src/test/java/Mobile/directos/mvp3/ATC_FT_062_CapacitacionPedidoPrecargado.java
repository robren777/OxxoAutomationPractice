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

public class ATC_FT_062_CapacitacionPedidoPrecargado extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_062_CapacitacionPedidoPrecargado_test(HashMap<String, String> data) throws Exception {
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

		// =======================================Cerrar
		// Aplicaciones========================================================//
		Thread.sleep(5000);
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		// =======================================Inicio
		// Aplicaciones========================================================//
		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n" + e.getMessage());
			e.printStackTrace();
		}
		Thread.sleep(3500);

		// =========================================Llamado al OM============================================================//
		// Login login = new Login(testCase, appiumUtil, this);
		OAD OxxoAlDia = new OAD(testCase, appiumUtil, null);
		ProveedoresDirectos ProveedoresD = new ProveedoresDirectos(testCase, appiumUtil, this);
		Thread.sleep(5000);

		// ==================Pre-requisito==================//
		/*
		 * 1 Validar que se muestre pantalla de “Bienvenida”
		 */
		addStep("Validar que se muestre pantalla de “Bienvenida”");
		assertTrue(ProveedoresD.pantallaPrincipalProveedoresDirectos(),
				"La pantalla principal de la App Proveedores Directos no se muestra de manera correcta.");

		/*
		 * 2 Seleccionar “Capacitación con Visita Guiada”
		 */

		addStep("Seleccionar “Capacitación con Visita Guiada”");
		assertTrue(ProveedoresD.VGCapacitacion(), "Boton no disponible: \"Capacitacion con Visita Guiada\"");

		/*
		 * 3 Seleccionar “Pedido Precargado (Sinergia/Reple)”
		 */
		addStep("Seleccionar “Pedido Precargado (Sinergia/Reple)”");
		ProveedoresD.OpcionPedidoPrecargado();
		
		/*
		 * 4 Validar que se muestre pantalla “Generación De Pedido”
		 */

		/*
		 * 5 "Validar mensaje: Estas realizando un proceso en la visita guiada... Ver
		 * mas Botón “Salir” Nota: este mensaje se mostrara durante toda la visita
		 * guiada"
		 */
		addStep("Validar mensaje: Estas realizando un proceso en la visita guiada... Ver mas");
		assertTrue(ProveedoresD.validarAvisoVisitaGuiada(), "error el Aviso no se muestra");

		/*
		 * 6 Validar mensaje: seleccionar la opción de escanear QR de pedido para Leer
		 * la orden de compra precargada. El QR a escanear te lo debe proporcionar el
		 * equipo de la tienda, este mismo lo va a encontrar en el apartado de pedidos
		 * precargados. Botón: Ver pasos para abrir QR de Pedido Precargado
		 */
		
		addStep("Validar mensaje: seleccionar la opción de escanear QR de pedido para Leer \r\n"
				+ "la orden de compra precargada. El QR a escanear te lo debe proporcionar el \r\n"
				+ "equipo de la tienda, este mismo lo va a encontrar en el apartado de pedidos \r\n"
				+ "precargados. Botón: Ver pasos para abrir QR de Pedido Precargado");
		assertTrue(ProveedoresD.mensajeEscanearQR(), "paso 6 El mensaje no se muestra de la manera correcta");
		ProveedoresD.opcionPedidoQR();
		
		/*
		 * 8 Validar mensaje de ayuda: 
		 * Pasos en HH para abrir QR de Pedido Precargado
		 * Excelencia Operativa > 
		 * Herramienta de Compra > 
		 * Herramienta de Compra COMETA >
		 * Verificación de Mercancía Pedidos Proveedor Directo > 
		 * Pedidos Precargados
		 * Botón: Siguiente
		 */

		addStep("Validar mensaje de ayuda: \r\n"
				+ "Pasos en HH para abrir QR de Pedido Precargado \r\n"
				+ "Excelencia Operativa > \r\n"
				+ "Herramienta de Compra > \r\n"
				+ "Herramienta de Compra COMETA > \r\n"
				+ "Verificación de Mercancía Pedidos Proveedor Directo > \r\n"
				+ "Pedidos Precargados \r\n"
				+ "Botón: Siguiente");
		assertTrue(OxxoAlDia.ValidarMensajePasosAbrirQR(), "paso 8 El mensaje no se muestra de la manera correcta");
		/*
		 * 9 Seleccionar “Siguiente”
		 */
		
		addStep("Seleccionar “siguiente”");
		ProveedoresD.opcionSiguiente();
		/*
		 * 10 Validar mensaje de ayuda: Dar clic en botón “Continuar” para leer QR de
		 * Pedido Precargado Nota: Para fines de este proceso de capacitación, se estará
		 * abriendo un pedido meramente ilustrativo al dar clic en continuar, como si se
		 * hubiera escaneado el código QR.
		 */
		
		addStep("Validar mensaje de ayuda: Dar clic en botón “Continuar” para leer QR de\r\n"
				+ "Pedido Precargado Nota: Para fines de este proceso de capacitación, se estará\r\n"
				+ "abriendo un pedido meramente ilustrativo al dar clic en continuar, como si se\r\n"
				+ "hubiera escaneado el código QR.");
		assertTrue(OxxoAlDia.ValidarMensajeContinuar(), "El mensaje no se muestra de la manera correcta");
		
		/*
		 * 11 Seleccionar “CONTINUAR”
		 */
		addStep("Seleccionar “CONTINUAR”");
		OxxoAlDia.continuar();

		/*
		 * 12 "Validar mensaje de ayuda: Este es el Pedido Precargado a entregar en la
		 * tienda, en donde podras confirmar las cantidades disponibles para la entrega.
		 * 
		 * En dado caso que no cuentes con disponibilidad de algtn SKU para su entrega,
		 * se tendré que modificar la cantidad."
		 */

		Thread.sleep(1000);
		addStep("Validar mensaje de ayuda: Este es el Pedido Precargado a entregar en la tienda");
		assertTrue(OxxoAlDia.ValidarMensajeArticulosPedido(), "El mensaje no se muestra de la manera correcta");
		OxxoAlDia.DummyPrecargado();
		Thread.sleep(3000);
		
		/*
		 * 13 "Validar mensaje: “Estés realizando un proceso en la visita guiada... Ver
		 * mas Botón [Salir]” mostrado en la parte superior"
		 */
		addStep("Validar mensaje: Estas realizando un proceso en la visita guiada... Ver mas");
		assertTrue(ProveedoresD.validarAvisoVisitaGuiada(), "error el Aviso no se muestra");
	

		/*
		 * 15 "Validar mensaje de ayuda: Una vez confirmando los artículos disponibles
		 * para la entrega, se dará clic en el botón “Continuar” para generar el Código
		 * QR, el cual será escaneado con la Hand Held."
		 */
		addStep("Validar mensaje: Una vez confirmando los artículos disponibles para la entrega, se dará clic en el botón “Continuar” para generar el Código QR, el cual será escaneado con la Hand Held.");
		assertTrue(OxxoAlDia.ValidarMensajeContinuarArticulos(), "El mensaje no se muestra de la manera correcta");
		/*
		 * 16 Seleccionar “CONTINUAR”
		 */
		addStep("Seleccionar “CONTINUAR”");
		ProveedoresD.clicContiuar();

		/*
		 * 17 "Validar mensaje de ayuda: Se genera un código QR el cual incluye los
		 * artículos del pedido realizado, este mismo será leído con la Hand Held por
		 * parte del equipo de la tienda OXXO. Continúa la visita guiada directamente en
		 * la HandHeld: Botón: ver pasos lectura de QR en la Hand Held"
		 */
		/*
		 * 18 Seleccionar botón “Ver pasos para lectura de QR en la Hand Held”
		 */

		addStep("Validar mensaje: Se genera un código QR el cual incluye los artículos del pedido realizado, este mismo será leído con la Hand Held "
				+ "por parte del equipo de la tienda OXXO. Continúa la visita guiada directamente en la HandHeld: "
				+ "Botón: ver pasos lectura de QR en la Hand Held");
		assertTrue(OxxoAlDia.ValidarMensajeSeGeneraQR(), "El mensaje no se muestra de la manera correcta");

		
		addStep("Seleccionar botón “Ver pasos para lectura de QR en la Hand Held”");
		OxxoAlDia.opcionVerPasosGenerarQR();

		/*
		 * 19 "Validar mensaje de ayuda:
		 * 
		 * Pasos para lectura de QR en Hand Held Excelencia Operativa > Herramienta de
		 * Compra > Herramienta de Compra COMETA > Verificación de Mercancía Pedidos
		 * Proveedor Directo > Leer QR para Preventa o Pedido""
		 */
		addStep("Validar mensaje de ayuda: Pasos para lectura de QR en Hand Held \n" + "- Excelencia Operativa >\n"
				+ "- Herramienta de Compra >\n" + "- Herramienta de Compra COMETA >\n"
				+ "- Verificación de Mercancía Pedidos Proveedor Directo >" + "- Leer QR para Preventa o Pedido");
		assertTrue(OxxoAlDia.ValidarMensajePasosLecturaQR(),"paso 19 El mensaje no se muestra de la manera correcta");

		/*
		 * 20 Tomar la Hand Held y abrir la aplicación “Oxxo al día”
		 */
		addStep("Tomar la Hand Held y abrir la aplicación [Oxxo al día]");
		OxxoAlDia.copiarQR();

		// cerrar app proveedores
		Thread.sleep(5000);
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
		
		
		// abrir app Oxxo al Dia
		appiumUtil.driver.activateApp(data.get("appPackage")+"/"+data.get("appActivity"));
		Thread.sleep(5000);

		Login login = new Login(testCase, appiumUtil, this);
		//OAD6 OxxoAlDia = new OAD6(testCase, appiumUtil, null);
		//Login login = new Login(testCase, appiumUtil, this);

		addStep("Abrir aplicación Mi OXXO al día");
		login.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));
		Thread.sleep(5000);
		/*
		 * 21 Seleccionar “Excelencia operativa” en la Han Held		
		 * 22 "Seleccionar “Herramienta de compra”	"
		 */
		OxxoAlDia.ingresarModuloHerramientas();
		/*
		 * 23 Seleccionar “COMETA”
		 * 24 Seleccionar “Verificación de Mercancía Pedidos Proveedor Directo”
		 */
		OxxoAlDia.opcionVerificacionMercancia();
		
		/*
		 * 25 Seleccionar “Leer QR para Preventa o Pedido”
		 * 26 Seleccionar “NO”
		 */
		OxxoAlDia.opcionleerQR();

		/*
		 * 27 Acercar QR para leerlo con la HH
		 */
		Thread.sleep(4000);
		OxxoAlDia.pegarQR();
		/*
		 * 28 "Validar mensaje:
		 * Este código QR contiene información de prueba, por lo cual no se estará
		 * generando una Orden de Compra Opción: ACEPTAR""
		 * 29 Seleccionar “ACEPTAR”
		 */
		Thread.sleep(2000);
		addStep("Validar mensaje: Este código QR contiene información de prueba, por lo cual no se estará generando una Orden de Compra");
		assertTrue(OxxoAlDia.ValidarMensajeQRPrueba(),"paso 28 No se muestra mensaje correctamente");
		Thread.sleep(2000);
		/*
		 * 30 "Validar mensaje de ayuda: Una vez escaneado el QR con la Hand Held, nos
		 * solicitará capturar el número de remisión, este misma te lo proporciona el
		 * proveedor." 31 "Validar mensaje: “Estés realizando un proceso en la visita
		 * guiada... Ver mas Botón [Salir]” mostrado en la parte superior"
		 */
		addStep("Validar mensaje: Una vez escaneado el QR con la Hand Held, nos solicitará capturar el número de remisión, este misma te lo proporciona el proveedor.");
		assertTrue(OxxoAlDia.ValidarMensajeDespuesdeQR(),"paso 30 EL mensaje no se muestra de la manera correcta.");
	

		/*
		 * 32 Seleccionar campo de numero de remisión
		 */
		addStep("Ingresar número de remisión manualmente");
		OxxoAlDia.ingresarNoRemision("1398");

		/*
		 * 33 "Validar mensaje de ayuda: Una vez capturado el número de remisión,
		 * estaremos realizando la verificación de mercancía del pedido generado. "
		 */
		addStep("Validar mensaje: Una vez capturado el número de remisión, estaremos realizando la verificación de mercancía del pedido generado.");
		assertTrue(OxxoAlDia.ValidarMensajeNoRemision(),"paso 33 El 3mensaje no se muestra de la manera correcta");
		

		/*
		 * 34 Clic en el número de remisión
		 */
		

		/*
		 * 35 Validar mensaje de ayuda:
		 *
		 * Se visualiza los artículos a verificar, En caso de haber incidencias, se
		 * clasifica en dañado, caduco o faltante. Ejemplo: Resta 1 pieza y en
		 * automático se clasificará como faltante. IMPORTANTE: Si en dado caso se
		 * detectan incidencias durante la verificación, se solicitará que se verifique
		 * un porcentaje mayor del pedido original
		 */
		addStep("Validar mensaje de ayuda: \r\n"
				+ "Se visualiza los artículos a verificar, En caso de haber incidencias, se \r\n"
				+ "clasifica en dañado, caduco o faltante. Ejemplo: Resta 1 pieza y en \r\n"
				+ "automático se clasificará como faltante. IMPORTANTE: Si en dado caso se \r\n"
				+ "detectan incidencias durante la verificación, se solicitará que se verifique \r\n"
				+ "un porcentaje mayor del pedido original");
		assertTrue(OxxoAlDia.ValidarVisualizacionArticulo(),"paso 35, el mensaje no se muestra de la manera correcta");
		
		
		/*
		 * 36 Seleccionar botón restar producto “-”
		 */
		addStep("Seleccionar botón restar producto “-”");
		OxxoAlDia.opcionRestar();

		/*
		 * 37 "Validar mensaje de ayuda: Después de haber verificado la mercancía,
		 * captura a cifra de proveedor. Esta cifra te la comparte directamente el
		 * proveedor."
		 */

		addStep("Validar mensaje: Después de haber verificado la mercancía, captura a cifra de proveedor. Esta cifra te la comparte directamente el proveedor.");
		assertTrue(OxxoAlDia.ValidarMensajeDespuesMercancia(),"paso 37 el mensaje no se muestra de la manera correcta");

		/*
		 * 38 Seleccionar campo de cifra de proveedor
		 */

		/*
		 * 39 Click en cifra de proveedor
		 */
		addStep("Se ingresa cifra del proveedor");
		OxxoAlDia.ingresarCifraProveedor("1313");
		/*
		 * 40 " Validar mensaje de ayuda: Da clic en el botón “Terminar” para visualizar
		 * el resumen e ir al paso de captura de firmas y comentarios."
		 */
		addStep("Validar mensaje: Da clic en el botón “Terminar” para visualizar el resumen e ir al paso de captura de firmas y comentarios.");
		
		assertTrue(OxxoAlDia.ValidarMensajeTerminarVG(),"paso 40 el mensaje no se muestra de la manera correcta");
		/*
		 * 41 Seleccionar botón “TERMINAR”
		 */
		addStep("Seleccionar botón “TERMINAR”");
		OxxoAlDia.opcionTerminar();
		/*
		 * 42 "Validar mensaje de ayuda: se muestra resumen de pedido de los artículos
		 * Nota: validar que los artículos mostrados sean datos Dummy"
		 */
		Thread.sleep(1000); 
		addStep("Validar mensaje de ayuda: se muestra resumen de pedido de los artículos");
		assertTrue(OxxoAlDia.ValidarMensajeResumenPedido(), "paso 42 el mensaje no se muestra de la manera correcta");
		OxxoAlDia.validarDummiesResumenPedido();

		/*
		 * 43 Clic en la tabla de artículos
		 */
		addStep("Clic en la tabla de artículos");
		OxxoAlDia.clicTabla();

		/*
		 * 44 "Validar mensaje de ayuda: PROVEEDOR El siguiente paso es seleccionar una
		 * opción en la sección “Observaciones Proveedor”. Ejemplo: Selecciona “Otros”
		 * para un comentario de captura libre."
		 */
		addStep("Validar mensaje de ayuda:"
				+ "PROVEEDOR El siguiente paso es seleccionar una opción en la sección “Observaciones Proveedor”.");
		assertTrue(OxxoAlDia.ValidarMensajeSelecionarProveedor(), "paso 44 el mensaje no se muestra de la manera correcta");
		

		/*
		 * 45 Clic en la sección “Observaciones Proveedor”.
		 */
		Thread.sleep(5000); 
		addStep("Clic en la sección “Observaciones Proveedor”.");
		OxxoAlDia.CampoObservacionesProveedor();
		/*
		 * 46 Seleccionar “OTROS”
		 */
		addStep("Seleccionar “OTROS”");
		OxxoAlDia.LlenarObservacionesOtros();

		/*
		 * 47 "Validar mensaje de ayuda: PROVEEDOR Escribe un comentario a modo de
		 * observación respecto al acuerdo de entrega de la OC."
		 */
		addStep("Validar mensaje:"
				+ " PROVEEDOR  Escribe un comentario a modo de observación respecto al acuerdo de entrega de la OC.");
		assertTrue(OxxoAlDia.ValidarMensajeComentarioProveedor(),"El mensaje no se muesta de mandera Correcta");
		

		/*
		 * 48 Clic en pantalla
		 */

		/*
		 * 49 Seleccionar campo de comentario
		 */

		/*
		 * 50 Clic en pantalla
		 */
		addStep("Seleccionar campo de comentario");
		OxxoAlDia.ingresarComentarioProveedor("Un Comentario");

		/*
		 * 51 "Validar mensaje de ayuda: PROVEEDOR Haz clic sobre el recuadro destinado
		 * para capturar tu firma."
		 **/

		addStep("Validar mensaje de ayuda: "
				+ "PROVEEDOR  Haz clic sobre el recuadro destinado para capturar tu firma.");
		assertTrue(OxxoAlDia.ValidarMensajeFirmaProveedor(), "El mensaje no se muestra de la mandera correcta");
		

		/*
		 * 52 Clic en campo de firma
		 */
		addStep("Clic en campo de firma");
		OxxoAlDia.campoFirmaPrveedor();

		/*
		 * 53 Validar mensaje de ayuda: Firma dentro del recuadro para el acuerdo de entrega.
		 */
		addStep("Validar mensaje: Firma dentro del recuadro para el acuerdo de entrega.");
		assertTrue(OxxoAlDia.ValidarMensajeDentroFirma(),"El mensaje no se muestra de la manera correcta");
		

		/*
		 * 54 Se agrega firma
		 */
		addStep("Se agrega firma");
		OxxoAlDia.clicfirma();
		OxxoAlDia.ingresarFirma();
		/*
		 * 55 Clic en pantalla
		 */

		/*
		 * 56
		 * "Validar mensaje de ayuda: Da clic en “Continuar” para seguir con el proceso."
		 */
		addStep("Validar mensaje de ayuda: Da clic en “Continuar” para seguir con el proceso.");
		assertTrue(OxxoAlDia.ValidarMensajeFirmaContinuar(),"El mensaje no se muestra la manera Correcta");
		

		/*
		 * 57 Seleccionar “CONTINUAR”
		 */
		addStep("Seleccionar “CONTINUAR”");
		OxxoAlDia.seleccionarContinuar();

		/*
		 * 58 "Validar mensaje de ayuda: TIENDA El siguiente paso es seleccionar una
		 * opción en la sección “Observaciones Personal Tienda"". Ejemplo: Selecciona
		 * ‘Sin Comentarios” en esta ocasión."
		 */
		addStep("Validar mensaje de ayuda:" + " TIENDA "
				+ "El siguiente paso es seleccionar una opción en la sección “Observaciones Personal Tienda\""
				+ "Ejemplo: Selecciona ‘Sin Comentarios” en esta ocasión.");
		assertTrue(OxxoAlDia.ValidarMensajeObservacionesTienda(),"El mensaje no se muesta de manera correcta");
		

		/*
		 * 59 Clic en el campo Observaciones Personal Tienda
		 */
		addStep("Clic en el campo Observaciones Personal Tienda");
		OxxoAlDia.CampoObservacionesTienda();

		/*
		 * 60 Seleccionar “SIN COMENTARIOS”
		 */
		addStep("Seleccionar “SIN COMENTARIOS”");
		OxxoAlDia.LlenarObservacionesSinComentarios();

		/*
		 * 61 Validar mensaje de ayuda: 
		 * Haz clic sobre el recuadro destinado para la firma del personal de tienda.
		 */
		addStep("Validar mensaje de ayuda: Haz clic sobre el recuadro destinado para la firma del personal de tienda.");
		assertTrue(OxxoAlDia.ValidarMensajeRecuadroFirmaTienda(),"El mensaje no se muestra de manera correcta");
		

		/*
		 * 62 Seleccionar campo Firma Personal Tienda
		 */
		addStep("Seleccionar campo Firma Personal Tienda");
		OxxoAlDia.clicRecuadroFrimaTienda();

		/*
		 * 63
		 * "Validar mensaje de ayuda: Firma dentro del recuadro para el acuerdo de entrega."
		 */
		addStep("Validar mensaje de ayuda: Firma dentro del recuadro para el acuerdo de entrega.");
		assertTrue(OxxoAlDia.ValidarMensajeDentroFirma(),"El mensaje no se muestra de manera correcta");
		
		/*
		 * 64 Se agrega firma
		 */
		addStep("Se agrega firma");
		OxxoAlDia.clicfirma();
		OxxoAlDia.ingresarFirma();

		/*
		 * 65 "Validar mensaje de ayuda: Da clic en “Continuar” para seguir con el
		 * proceso."
		 */
		addStep("Validar mensaje: Da clic en “Continuar” para seguir con el proceso.");
		assertTrue(OxxoAlDia.ValidarMensajeFirmaContinuar(),"El mensaje no se muestra de manera correcta");
		
		/*
		 * 66 Seleccionar “CONTINUAR”
		 */
		addStep("Seleccionar [CONTINUAR]");
		OxxoAlDia.seleccionarContinuar();
		Thread.sleep(2000);
		/*
		 * 67 Validar mensaje de ayuda:
		 * Selecciona la opción para confirmar que se cumplió con el proceso de entrega.
		 */
		addStep("Validar mensaje: Selecciona la opción para confirmar que se cumplió con el proceso de entrega.");
		assertTrue(OxxoAlDia.ValidarMensajeSeCumplioProcesoEntrega(),"El mensaje no se muestra de manera correcta");
		
		addStep("selecionar Se Cumplio Proceso de entrega");
		OxxoAlDia.CheckSeCumplioProcesoEntrega();

		/*
		 * 68 Se muestra pantalla de “Tira de entrega”
		 * 70
		 * Validar mensaje de ayuda: Haz clic en “Terminar” para finalizar el proceso de acuerdo de entrega.
		 */
		addStep("Validar mensaje: Haz clic en “Terminar” para finalizar el proceso de acuerdo de entrega.");
		assertTrue(OxxoAlDia.ValidarMensajeTerminarParaFinalizar(),"El mensaje no se muestra de manera correcta");
		

		/*
		 * 71 Seleccionar “TERMINAR”
		 */
		addStep("Seleccionar “TERMINAR”");
		OxxoAlDia.opcionTerminar();
		/*
		 * 72 "Validar mensaje de ayuda: Se genera la Tira de Entrega del pedido,
		 * posteriormente da clic en el botón “Finalizar” en la parte inferior de la
		 * pantalla para terminar con el proceso. Los comprobantes oficiales se
		 * visualizarán directamente en el portal de proveedores COMETA. Botón
		 * “CONTINUAR”"
		 */
		addStep("Validar mensaje de ayuda:"
				+ "Se genera la Tira de Entrega del pedido, posteriormente da clic en el botón “Finalizar” en la parte inferior de la pantalla para terminar con el proceso."
				+ "Los comprobantes oficiales se visualizarán directamente en el portal de proveedores COMETA. Botón “CONTINUAR”");
		assertTrue(OxxoAlDia.ValidarMensajeTiraEntregaPedido(),"El mensaje no se muestra de manera correcta");
		

		/*
		 * 73 Seleccionar “CONTINUAR”
		 */
		addStep("Seleccionar “CONTINUAR”");
		OxxoAlDia.selecionarContinuar();
		OxxoAlDia.validarDummiesTiraEntrega();

		/*
		 * 75 "Seleccionar “FINALIZAR”"
		 */
		addStep("Seleccionar “FINALIZAR”");
		OxxoAlDia.opcionFinalizar();

		/*
		 * 76 "Validar pantalla de felicitaciones: Icono paloma ¡Felicidades! Se ha
		 * concluido con la Capacitación de Visita Guiada. Todos los datos capturados
		 * dentro de esta capacitación fueron de prueba, por lo que no se verán
		 * afectados los inventarios de la tienda.
		 * 
		 * Botón “FINALIZAR”"
		 */
		addStep("Validar pantalla de Felicitaciones \n"
				+ "Icono paloma \n"
				+ "Se ha concluido con la Capacitación de Visita Guiada. \n"
				+ "Todos los datos capturados dentro de esta capacitación fueron de prueba, \n"
				+ "por lo que no se verán afectados los inventarios de la tienda.");
		assertTrue(OxxoAlDia.ValidarPantallaFelicitaciones(),"la pantalla felicitaciones no se muestra de la manera Correcta");

		/*
		 * 77 Seleccionar “FINALIZAR”
		 */
		addStep("Seleccionar “FINALIZAR”");
		OxxoAlDia.opcionTerminar();

	}

	/*
	 * Proveedores Directos MVP3:
	 * MTC-FT-088 Flujo de capacitación Pedido Precargado
	 * Desc: Validar la secuencia y guía correcta en el flujo de capacitación al proveedor para una recepción tipo reple,  que la leyenda informativa "Estas realizando un proceso en la visita guiada de capacitación, estos datos no serán guardados o almacenados en el punto de venta" sea visible durante todo el flujo.
	 * 
	 * @autor Miguel Angel Bautista Marroquin
	 * 
	 * @date 20/09/2024
	 * 
	 */

	@Override
	public String setTestFullName() {
		return "ATC_FT_062_CapacitacionPedidoPrecargado";
	}

	@Override
	public String setTestDescription() {
		return "Validar la secuencia y guía correcta en el flujo de capacitación al proveedor para una recepción tipo reple,  que la leyenda informativa \"Estas realizando un proceso en la visita guiada de capacitación, estos datos no serán guardados o almacenados en el punto de venta\" sea visible durante todo el flujo.";
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
