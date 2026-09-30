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

public class ATC_FT_194_HandheldValidacionDelFlujoAlSeleccionarMODIFICARRECEPCIONDesdeLaPantallaFUE extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_194_HandheldValidacionDelFlujoAlSeleccionarMODIFICARRECEPCIONDesdeLaPantallaFUE_test(
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

		// Ejecuta los pasos para llegar al menu de Herramientas de CEDIS
		Mobile.cedis.om.A03_Menus_y_submenus herramienta = new Mobile.cedis.om.A03_Menus_y_submenus(testCase, appiumUtil, this);
		herramienta.logeoInicioHerramientas();

		Mobile.cedis.om.A04_MenuHerramientas menuCedis = new Mobile.cedis.om.A04_MenuHerramientas(testCase, appiumUtil, this);
		menuCedis.clickBtnRecepcionPedidosCEDIS();// Selecciona la opcion de Recepción de Pedidos CCEDIS
		
		Mobile.cedis.om.A05_LoginAreaDistribucion loginRuta = new Mobile.cedis.om.A05_LoginAreaDistribucion(testCase, appiumUtil, this);
		loginRuta.LoginAreaDistribucion();//Se logea en Area de Distribución sin tomar evidencia
		
		
		//Flujo Previo mencionado en el pre-requisito 
		
		Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia transferencia = new Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia(testCase, appiumUtil, this);
		transferencia.BuscarTransferencia(true,data.get("numTransferencia"));
		
		Mobile.cedis.om.B10_DeseaComenzar DeseaComenzar = new Mobile.cedis.om.B10_DeseaComenzar(testCase, appiumUtil, this);
		DeseaComenzar.Cargando();
		DeseaComenzar.SeleccionarObjeto("Devolucion CEDIS");
		
		Mobile.cedis.om.B05_DevolucionMercancia devolucion = new Mobile.cedis.om.B05_DevolucionMercancia(testCase, appiumUtil, this);		
		Thread.sleep(500);
		devolucion.ValidacionEncabezado();
		devolucion.SeleccionarPrimerBol("SinEvidencia", null);				
		devolucion.AumentarCanastilla(2, "Azul");
		devolucion.MoverSeccion("Contenedores");
		devolucion.AumentarContenedores(3, "Tapas");		
		devolucion.TomarFotoDevolucion();		
		devolucion.IngresarCanastilla("654654");
		devolucion.TomarFotoCanastilla();
		devolucion.SeleccionarObjeto("Continuar");		

		Mobile.cedis.om.B07_ResumenDevolucion ResumenDevolucion = new Mobile.cedis.om.B07_ResumenDevolucion(testCase, appiumUtil, this);
		Thread.sleep(500);
		ResumenDevolucion.ValidacionEncabezado();		
		ResumenDevolucion.ClickFirmaAcuerdo("SinEvidencia", "");		

		Mobile.cedis.om.B08_AcuerdoDevolucion AcuerdoDevolucion = new Mobile.cedis.om.B08_AcuerdoDevolucion(testCase, appiumUtil, this);
		Thread.sleep(500);
		AcuerdoDevolucion.ValidacionEncabezado();
		AcuerdoDevolucion.FirmaAreaDistribucion("", "");
		AcuerdoDevolucion.FirmaPersonalTienda("", "");
		AcuerdoDevolucion.TerminarRecepcion("", "");		
		
	
		Mobile.cedis.om.B02_RecepcionPedidosCEDIS pantallatransferencia = new Mobile.cedis.om.B02_RecepcionPedidosCEDIS(testCase, appiumUtil, this);
		pantallatransferencia.DeslizarArriba();
		Thread.sleep(500);
		pantallatransferencia.ValidacionEncabezado();
		pantallatransferencia.Tomar3Fotos();		
		pantallatransferencia.CapturaContenedor(data.get("contenedor"));//Se captura los contenedores que se encuentran en el excel
		//pantallatransferencia.MoverSeccion("Terminar");
		pantallatransferencia.SeleccionarObjeto("Terminar");
		pantallatransferencia.SeleccionarObjeto("Confirmar");		
		pantallatransferencia.SeleccionarObjeto("Si");		
		pantallatransferencia.RegistrarSinEtiqueta();
		pantallatransferencia.SeleccionarObjeto("Terminar Cajas Sin Etiqueta");			
		pantallatransferencia.SeleccionarObjeto("Confirmar");
		pantallatransferencia.CapturaContenedorSobrantes("654654");
		pantallatransferencia.SeleccionarObjeto("Continuar Dañados");
		pantallatransferencia.SeleccionarArticulo(data.get("articulo"), "", "");
		pantallatransferencia.SeleccionarObjeto("Aumentar Dañado");		
		pantallatransferencia.SeleccionarObjeto("Terminar Dañados");
		pantallatransferencia.SeleccionarObjeto("Confirmar");

		Mobile.cedis.om.B03_ResumenPedidosCEDIS resumen = new Mobile.cedis.om.B03_ResumenPedidosCEDIS(testCase, appiumUtil, this);		
		Thread.sleep(500);
		resumen.ValidacionEncabezado();
		resumen.FirmaAcuerdoSinEvidencia();
		
		Mobile.cedis.om.B04_AcuerdoEntrega acuerdo = new Mobile.cedis.om.B04_AcuerdoEntrega(testCase, appiumUtil, this);
		acuerdo.FirmaAreaDistribucion("SinEvidencia", null);
		acuerdo.FirmaPersonalTienda("SinEvidencia", null);
		acuerdo.TerminarRecepcion("", "");
								
		/*1.-Se inicia el caso por parte del usuario en la pantalla de FUE de una transferencia iniciada el flujo por la opción de Devoluciones a CEDIS 
		Resultado Esperado: Se muestra la pantalla de Formato único de Entrega con sus campos correspondiente. Se muestran los botones de:
		*MODIFICAR RECEPCIÓN
		*MODIFICAR DEVOLUCIONES
		*TERMINAR
		 */
		addStep("Se inicia el caso por parte del usuario en la pantalla de FUE de una transferencia iniciada el flujo por la opción de Devoluciones a CEDIS \r\n"
				+ "		Resultado Esperado: Se muestra la pantalla de Formato único de Entrega con sus campos correspondiente. Se muestran los botones de:\r\n"
				+ "		*MODIFICAR RECEPCIÓN\r\n"
				+ "		*MODIFICAR DEVOLUCIONES\r\n"
				+ "		*TERMINAR");
		Mobile.cedis.om.B09_FormatoUnicoEntrega FUE = new Mobile.cedis.om.B09_FormatoUnicoEntrega(testCase, appiumUtil, this);
		Thread.sleep(500);
		FUE.ValidacionEncabezado();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Entregados");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Sin etiqueta");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Faltantes");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");		
		FUE.BuscarSeccion("Sobrantes");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Dañados");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Firma Recepción");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Devolución Canastillas");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Canastillas con Devolución");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Devolución Contenedores");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Firma Devolución");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Movimiento Tienda");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Botones");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");		
			
		
		/*2.-Usuario selecciona el botón de "MODIFICAR RECEPCIÓN"
		Resultado Esperado: Se envía el caso a la pantalla de Pantalla Recepción Cajas sin etiqueta / Etiqueta dañada en la Se carga la información capturada previamente.
		 */
		addStep("Usuario selecciona el botón de \"MODIFICAR RECEPCIÓN\"\r\n"
				+ "		Resultado Esperado: Se envía el caso a la pantalla de Pantalla Recepción Cajas sin etiqueta / Etiqueta dañada en la Se carga la información capturada previamente.");
		FUE.SeleccionarObjeto("Modificar Recepción");
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Runtime.getRuntime().exec("adb shell input swipe 100 500 100 1450 100");// realiza la acción de deslizar hacia arriba
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		pantallatransferencia.MoverSeccion("Contenedor");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");		
		
		

		/*3.-Se valida que se muestre el llenado de información de la sección de Recepción Cajas sin etiqueta / Etiqueta dañada para después  seleccionar Continuar una vez actualizada la información.
		Resultado Esperado: Se muestra la sección de Sobrantes / Dañados con la información precargada
		 */		
		addStep("Se valida que se muestre el llenado de información de la sección de Recepción Cajas sin etiqueta / Etiqueta dañada para después  seleccionar Continuar una vez actualizada la información.\r\n"
				+ "		Resultado Esperado: Se muestra la sección de Sobrantes / Dañados con la información precargada");
		pantallatransferencia.MoverSeccion("Sobrantes");
		pantallatransferencia.CapturaContenedorSobrantes("894654");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");	
		pantallatransferencia.MoverSeccion("Dañados");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");				
		pantallatransferencia.SeleccionarObjeto("Aumentar Dañado");
		pantallatransferencia.SeleccionarObjeto("Aumentar Dañado");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		

		/*4.-Se valida que se muestre el llenado de información de la sección de Sobrantes / Dañados para después  seleccionar Terminar una vez actualizada la información en pantalla
		Resultado Esperado: Se muestra la pantalla de Resumen Recepción con la información precargada con la información actualizada
		 */		
		addStep("Se valida que se muestre el llenado de información de la sección de Sobrantes / Dañados para después  seleccionar Terminar una vez actualizada la información en pantalla\r\n"
				+ "		Resultado Esperado: Se muestra la pantalla de Resumen Recepción con la información precargada con la información actualizada");		
		pantallatransferencia.SeleccionarObjeto("Terminar Dañados");
		Thread.sleep(500);	
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		pantallatransferencia.SeleccionarObjeto("Confirmar");
		Thread.sleep(500);
		resumen.ValidacionEncabezado();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		

		/*5.-Se observa la información correspondiente a la pantalla de Resumen  Recepción y se selecciona el botón de Firma de Acuerdo
		Resultado Esperado: Se muestra la pantalla de Acuerdo de Entrega en la Se muestra la información correspondiente y donde se mostrara el checkbox de "cumplimiento de proceso de entrega" al final de la pantalla, arriba del botón "TERMINAR RECEPCIÓN".
		 */		
		addStep("Se observa la información correspondiente a la pantalla de Resumen  Recepción y se selecciona el botón de Firma de Acuerdo\r\n"
				+ "		Resultado Esperado: Se muestra la pantalla de Acuerdo de Entrega en la Se muestra la información correspondiente y donde se mostrara el checkbox de \"cumplimiento de proceso de entrega\" al final de la pantalla, arriba del botón \"TERMINAR RECEPCIÓN\".");		
		resumen.SeleccionarObjeto("Resumen Total Pedido");
		Thread.sleep(500);	
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		resumen.SeleccionarObjeto("Sobrantes Total Sobrantes");
		Thread.sleep(500);	
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		resumen.SeleccionarObjeto("Total Dañados");
		Thread.sleep(500);	
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");		
		resumen.FirmaAcuerdoSinEvidencia();
		Thread.sleep(500);	
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");				
		
		acuerdo.FirmaAreaDistribucion("SinEvidencia", null);
		acuerdo.FirmaPersonalTienda("SinEvidencia", null);		
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		acuerdo.SeleccionarObjeto("Terminar Recepcion");
		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		acuerdo.SeleccionarObjeto("Confirmar");
				

		/*6.-Se realiza la firma de Acuerdo de  Entrega y se selecciona el botón de TERMINAR RECEPCIÓN
		Resultado Esperado: "Se muestra la pantalla de Formato único de Entrega con sus campos correspondientes mostrando actualizada información. Se muestran los botones de:
		*MODIFICAR RECEPCIÓN
		*MODIFICAR DEVOLUCIONES
		*TERMINAR"
		 */		
		addStep("Se realiza la firma de Acuerdo de  Entrega y se selecciona el botón de TERMINAR RECEPCIÓN\r\n"
				+ "		Resultado Esperado: Se muestra la pantalla de Formato único de Entrega con sus campos correspondientes mostrando actualizada información. Se muestran los botones de:\r\n"
				+ "		*MODIFICAR RECEPCIÓN\r\n"
				+ "		*MODIFICAR DEVOLUCIONES\r\n"
				+ "		*TERMINAR");		
		Thread.sleep(500);
		FUE.ValidacionEncabezado();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Entregados");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Sin etiqueta");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Faltantes");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");		
		FUE.BuscarSeccion("Sobrantes");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Dañados");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Firma Recepción");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Devolución Canastillas");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Canastillas con Devolución");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Devolución Contenedores");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Firma Devolución");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Movimiento Tienda");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		FUE.BuscarSeccion("Botones");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");		

										
		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));
	}


	@Override		
	public String setTestFullName() {
		return "ATC-FT-194-Handheld Validacion del flujo al seleccionar MODIFICAR RECEPCION desde la pantalla FUE";
	}

	@Override
	public String setTestDescription() {
		return "Caso de prueba en el que se valida el flujo a tomar cuando se seleccione la opción de MODIFICAR RECEPCIÓN en la pantalla de FUE";
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
		return "*Contar con Transferencias por atender en la handheld\r\n"
				+ "*Haber iniciado el flujo de la transferencia con la opción de Devoluciones a CEDIS\r\n"
				+ "*Contar con la transferencia en la pantalla de FUE";
	}

	@Override
	public void beforeTest() {
	}

}