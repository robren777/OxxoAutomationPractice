package Mobile.POSMovil;

//import static org.testng.AssertJUnit.assertTrue;
import static org.testng.Assert.assertTrue;

import java.sql.ResultSet;
//import java.time.Duration;
import java.util.HashMap;
//import java.util.List;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
/*
import utils.password.PasswordUtil;
import org.testng.Assert;
import static org.testng.Assert.assertTrue;
*/
/**
import cedis.om.A01_Login_Punto_De_Venta;
import cedis.om.A02_Login_Lider;
import cedis.om.A03_Menus_y_submenus;
import cedis.om.A04_MenuHerramientas;
import cedis.om.C01_Listado_de_transferencias_a_verificar;
import cedis.om.C02_Verificacion_De_Pedido_Y_Permisos;
*/

import Mobile.POSMovil.*;
import Mobile.POSMovil.om.LoginPM;
import Mobile.POSMovil.om.PM;
/**
 * DESCRIPCIÓN:
 * Validar que, cuando se busca un artículo y no arroja ningún resultado de
 * dicho producto, el botón de "Seleccionar Todos" se deshabilita de forma
 * automática.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC-FT-022 TABLET Botón "Seleccionar Todos" deshabilitado
 *
 * PRERREQUISITOS:
 * 1. Punto de Venta
 * 2. Tablet
 * 3. Aplicación Oxxo al Dia
 * 4. Realizar el Login de Punto de Venta y Usuario Líder
 * 5. Ubicarse dentro del Modulo Devoluciones Especiales CEDIS
 * 6. Modulo Mercancía CEDIS, Encendido
 *
 * @author Juan Antonio Ibarra Alonso
 * @date 05 Junio 2025
 */

public class Debug_Juan extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void Debug_Juan_test(
			HashMap<String, String> data) throws Exception {

		/*******************************
		 * Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script
		 ********************/
		//testCase.setProject_Name("CEDIS");
		DesiredCapabilities cap = new DesiredCapabilities();

		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
//		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appium:appPackage", data.get("appPackageP"));
		cap.setCapability("appium:appActivity", data.get("appActivityP"));
		cap.setCapability("ip", data.get("ip"));
		cap.setCapability("userLogin", data.get("userLogin"));
		cap.setCapability("passwordLogin", data.get("passwordLogin"));
		cap.setCapability("rutaVPN", data.get("rutaVPN"));
		cap.setCapability("crTienda", data.get("crTienda"));
		
		//beforeTest(data.get("rutaVPN"));
		//beforeTest();


		String url = "http://127.0.0.1:4723/";
		

		System.out.println("Inicia DEBUG" );
		// Cerrar Apps
		//Runtime.getRuntime().exec("adb shell pm clear " + data.get("appPackage"));

		AppiumUtil appiumUtil = null;
		
		LoginPM LoginPosMovil  = new LoginPM(testCase, appiumUtil, this);
		LoginPosMovil.validarArchivoVPN();  
//	  	LoginPosMovil.validarYLevantarAppium();
//	  	
//	  	
		try {
			appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}

		
		// Abre la aplicación
		//Runtime.getRuntime().exec("adb shell am start " + data.get("appPackage"));
//		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		
//		Runtime.getRuntime().exec("adb shell am start \"" + data.get("appPackage") + "\"");
		//Precondición para el caso Validación y consultas de  
		//cedis.om.D01_Conexiones_BD coneccion = new cedis.om.D01_Conexiones_BD(testCase, appiumUtil, this);
//	  	LoginPM LoginPosMovil  = new LoginPM(testCase, appiumUtil, this);
//	  	LoginPosMovil.validarYLevantarAppium();
//		String horaDispositivo  = POSMOVIL.obtenerHoraDispositivo();
//		System.out.println("valor de horaDispositivo: " + horaDispositivo );
//		LoginPosMovil.obtenerIDTiendaNube("10MEX50DHA");
//		LoginPosMovil.loginUserV2("10MON50DHA","500");
//		LoginPosMovil.actualizarHoraRed("10MON50DHA");
//		LoginPosMovil.actualizarHoraFueraHorario("10MON50DHA");
//		LoginPosMovil.actualizarHoraRed("10MON50DHA");
//		LoginPosMovil.validarHoraVentaAlcohol("10MON50DHA");
//		LoginPosMovil.cambiarHoraRemota("10.217.24.117", "QA", "Comercio03", "04-06-2026 10:30:00");
//		cambiarHoraRemota("192.168.1.10", "Administrador", "1234", "04-06-2026 10:30:00");
//		LoginPosMovil.existePsExe();	
//		LoginPosMovil.cambiarHoraPV(data.get("crTienda"), "fueraHorario");		
//		LoginPosMovil.cambiarHoraPV(data.get("crTienda"), "dentroHorario");
//		LoginPosMovil.cambiarHoraPV(data.get("crTienda"), "actualizarHorario");
////		POSMOVIL.cambiarHoraPV(data.get("crTienda"), "prueba");
//		LoginPosMovil.actualizarHoraFueraHorario(data.get("crTienda"));		
//		LoginPosMovil.actualizarHoraIgualalPV(data.get("crTienda"));
//		Runtime.getRuntime().exec(
//			    "adb shell am start -n " + data.get("appPackage") + "/" + data.get("appActivity")
//			);
//		POSMOVIL.verificarLoginTiendaUserCrV2(data.get("crTienda"),data.get("salInic"));
		
	  	PM POSMOVIL = new PM(testCase, appiumUtil, this);
//		String valor = POSMOVIL.obtenerCRTienda(data.get("crTienda"));
//		System.out.println("valor de valor : " + valor );
		
//		ResultSet resultado = Pm.obtieneSKUSBD("10.217.24.117");
//		System.out.println("valor de resultado es: " + resultado );

//		coneccion.encenderVPN(data.get("rutaVPN"));
		//boolean conec = coneccion.encenderVPN(data.get("rutaVPN"));
		//assertTrue(conec, "No se encontro archivo de vpn en la ruta mencionada, favor de revisar.");
		//revisión donde se agrupa en una sola linea
		//assertTrue(coneccion.encenderVPN(data.get("rutaVPN")), "No se encontro archivo de vpn en la ruta mencionada, favor de revisar.");
		
		//boolean loginPDV = login.loginPDV(data.get("ip"));
		//assertTrue(loginPDV, "Fallo el login...");		 
		
		//assertTrue(login.loginPDV(data.get("ip")), "mensaje...");
		 
		/*
		boolean val1 =  Boolean.parseBoolean(coneccion.obtenerIPTienda(data.get("crTienda")));
		System.out.println("valor de val despues de convertirlo a booleano es: " + val1  );
		assertTrue(!val1, "No se encontro la ip de la tienda 50"+ data.get("crTienda") +", favor de revisar el dato captgurado en DataProveider del caso. de val1");
		*/
		//revisión en una sola linea 
		//assertTrue(Boolean.parseBoolean(coneccion.obtenerIPTienda(data.get("crTienda"))), "No se encontro la ip de la tienda 50"+ data.get("crTienda") +", favor de revisar el dato captgurado en DataProveider del caso.");
			
		//assertTrue(!Boolean.parseBoolean(coneccion.obtenerIPTienda(data.get("crTienda"))), "No se encontro la ip de la tienda 50"+ data.get("crTienda") +", favor de revisar el dato captgurado en DataProveider del caso.");
		//String ipTienda = coneccion.obtenerIPTienda(data.get("crTienda"));
		
		
		//System.out.println("valor de ipTienda es: " + ipTienda );
		//boolean val =  Boolean.parseBoolean(coneccion.obtenerIPTienda(data.get("crTienda")));
		//Assert.assertFalse(val, "mensaje fallado en la opción de Assert.assertFalse");
		//boolean val = Boolean.parseBoolean(ipTienda);		
	
		

		// Ejecuta el logeo hacia el punto de venta
		//cedis.om.A01_Login_Punto_De_Venta login = new cedis.om.A01_Login_Punto_De_Venta(testCase, appiumUtil, this);		
		//login.loginPV(ipTienda);
		

		// Ejecuta el logeo del lider
		//cedis.om.A02_Login_Lider loginLider = new cedis.om.A02_Login_Lider(testCase, appiumUtil, this);
		// Obtiene valor del DataProvaider
		//loginLider.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));		
		//addStep("Enciende la VPN");
		//coneccion.conexionrdmmtyv11();
		//coneccion.verificarConexionRdmMty();
		//coneccion.conexionEvo();
		//coneccion.conexionEvo();
		
		//String resultado = coneccion.busquedaArticulos(coneccion.obtenerIDTiendaNube(data.get("crTienda")), 8, 10);		
		//String resultado = coneccion.artExistente(coneccion.obtenerIDTiendaNube(data.get("crTienda")));//Se realiza la busqueda de un sku que no exista para la tienda
		//coneccion.articuloNuevo(data.get("crTienda"));		
		//coneccion.cantidadPendientes(data.get("crTienda"));
		
		//List<Articulo> resultado = coneccion.articulosTemporales(data.get("crTienda"), 3, 10); 
		//System.out.println("valor de resultado: " +resultado );
		
		//coneccion.busquedaArticulos(data.get("crTienda"), 3, 7);
		
		//System.out.println("resultado obtenido: " + resultado);		
		/*String sku = resultado.split(" - ")[0];//Se obtiene solo el SKU de la consulta 
		System.out.println("SKU obtenido: " + sku);
		String TipoDevolucion = resultado.split(" - ")[3];//Se obtiene solo el SKU de la consulta 
		System.out.println("TipoDevolucion obtenido: " + TipoDevolucion);	
		*/
		
		//coneccion.verificaronexionEvolucion();
		//coneccion.conectar();		
		//addStep("Se conecta con Evolución");
		//loginLider.cerrarVPN();

		// Ejecuta los pasos para llegar al menu de Herramientas de CEDIS
		//cedis.om.A03_Menus_y_submenus herramienta = new cedis.om.A03_Menus_y_submenus(testCase, appiumUtil, this);
		//herramienta.Cargando();
		//herramienta.logeoInicioHerramientas();
		

		//cedis.om.A04_MenuHerramientas menuCedis = new cedis.om.A04_MenuHerramientas(testCase, appiumUtil, this);
		//menuCedis.SeleccionarObjeto("MercanciaCEDIS");// Selecciona la opcion de Mercancia CEDIS
		//menuCedis.clickBtnDevolucionesespecialesCEDIS();// Selecciona la opcion de Devoluciones especiales CEDIS
//		menuCedis.clickBtnDevolucionesespecialesCEDIS();// Selecciona la opcion de Devoluciones especiales CEDIS
//		
//		//cedis.om.F01_Devoluciones_especiales mercanciaCedis = new cedis.om.F01_Devoluciones_especiales(testCase, appiumUtil, this);
//		//mercanciaCedis.cerrarTutorial();		
//		boolean carga = mercanciaCedis.cargandoPantalla2();
//		assertTrue(carga, "No se termino de realizar la carga de la pantalla de Devoluciones Especiales CEDIS.");
//		boolean mensajes = mercanciaCedis.cerrarMensajesActualizado();		
//		assertTrue(mensajes, "Se sigue mostrando un mensaje en la pantalla.");
//		//testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
//
//		
//
//		String resultado = coneccion.folioPendiente(data.get("crTienda"));
//		System.out.println("valor de resultado folio pendiente en debug:..." + resultado);
//		
//		
//		String folio = mercanciaCedis.GenerarFolio(resultado, data.get("crTienda"));
//		System.out.println("valor de folio en debug:..." + folio );
//		mercanciaCedis.SeleccionarObjeto("Consulta");
		
		
		//mercanciaCedis.cerrarTutorial();		
		//boolean carga = mercanciaCedis.cargandoPantalla2();
		//assertTrue(carga, "No se termino de realizar la carga.");
		/*
		boolean mensajes = mercanciaCedis.cerrarMensajesActualizado();
		assertTrue(mensajes, "Se sigue mostrando un mensaje en la pantalla.");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		*/
		
		//mercanciaCedis.SeleccionarObjeto("Catalogo");
		//mercanciaCedis.validarMensajeArticulosAgregados("CEDIS_CerrarArticulosNuevos");
		//mercanciaCedis.cerrarMensajes();
		//mercanciaCedis.validarMensajeTop10Mayor("CEDIS_CerrarTop10Mayor");
		
		//cedis.om.F02_Catalogo_Devoluciones catalogoCedis = new cedis.om.F02_Catalogo_Devoluciones(testCase, appiumUtil, this);
		//catalogoCedis.cargandoPantalla();		
		//catalogoCedis.SeleccionarObjeto("Boton_Seleccionar_Todos");
		//catalogoCedis.validarBotonInhabilitado("Boton_Mostrar_Todos");
		//catalogoCedis.SeleccionarTodosHabilitado();
		//catalogoCedis.seleccionarMotivo("Baja de producto");
		//catalogoCedis.seleccionarMotivo("Baja de producto");
		//catalogoCedis.crearFolioPendiente(2);
		//Runtime.getRuntime().exec("adb shell input keyevent 4");// realiza la acción de Back para dejar de desplegar
		
		//cedis.om.F03_Creacion_BOL creaCedis = new cedis.om.F03_Creacion_BOL(testCase, appiumUtil, this);
		//creaCedis.agregarArticulos();		

		/*String resultado = coneccion.cantidadPendientes(data.get("crTienda"));
		resultado = coneccion.cantidadPendientes(data.get("crTienda"));
		String folio = resultado.split(" - ")[0];// Se obtiene solo el SKU de la consulta
		mercanciaCedis.SeleccionarObjeto("Consulta");
		*/
		//cedis.om.F04_Consulta_Devolucion_Especial consDevolucion = new cedis.om.F04_Consulta_Devolucion_Especial(testCase, appiumUtil,this);
		//consDevolucion.SeleccionarObjeto("Campo Busqueda");
		/*
		consDevolucion.cargandoPantalla();
		consDevolucion.SeleccionarObjeto("Campo Busqueda");
		Runtime.getRuntime().exec("adb shell input text " + folio);//Capturar el numero de folio*/
		//consDevolucion.SeleccionarObjeto("EditarPrimerBol");
		//catalogoCedis.seleccionCheckBox(2);
		//catalogoCedis.SeleccionarObjeto("Agregar");
		
	  	addStep("Paso prueba");
	  	LoginPosMovil.selecUsuarioV2(data.get("crTienda"));

		// cerrar Apps
		//Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));		
		System.out.println("Termina revisión debug ");
	}

	

	@Override
	public String setTestFullName() {
		return "Debug_Juan";
	}

	@Override
	public String setTestDescription() {
		return "Validar que, cuando se busca un artículo y no arroja ningún resultado de dicho producto, el botón de \"Seleccionar Todos\" se deshabilita de forma automática.";
	}

	@Override
	public String setTestDesigner() {
		return "Juan Antonio Ibarra Alonso";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1. Punto de Venta\r\n" + //
				"2. Tablet\r\n" + //
				"3. Aplicación Oxxo al Dia\r\n" + //
				"4. Realizar el Login de Punto de Venta y Usuario Líder\r\n" + //
				"5. Ubicarse dentro del Modulo Devoluciones Especiales CEDIS\r\n" + //
				"6. Modulo Mercancía CEDIS, Encendido\r\n" + //
				"";
	}
	/*
	@BeforeMethod 
	public void beforeTest (String ruta) {

					
		//testCase = null;
		AppiumUtil appiumUtil = null;
		//addStep("prueba ");
		//addStep("Paso agregado y toma evidencia");
		cedis.om.D01_Conexiones_BD coneccion = new cedis.om.D01_Conexiones_BD(testCase, appiumUtil, this);
		boolean conec = coneccion.encenderVPN(ruta);
		assertTrue(conec, "No se encontro archivo de vpn en la ruta mencionada, favor de revisar.");
		

	}

	@BeforeMethod
	public void beforeTest() {
		// TODO Auto-generated method stub
		AppiumUtil appiumUtil = null;
		//addStep("prueba ");
		//addStep("Paso agregado y toma evidencia");
		cedis.om.D01_Conexiones_BD coneccion = new cedis.om.D01_Conexiones_BD(testCase, appiumUtil, this);
		//cap.setCapability("rutaVPN", data.get("rutaVPN"));
		boolean conec = coneccion.encenderVPN("C:\\Users\\GTIM\\.ssh");
		assertTrue(conec, "No se encontro archivo de vpn en la ruta mencionada, favor de revisar.");
	}

	*/

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}
	
	@AfterTest
	public void afterTest() {
		AppiumUtil appiumUtil = null;
		LoginPM POSMOVIL = new LoginPM(testCase, appiumUtil, this);
		POSMOVIL.cerrarVPN();
		
	}


}