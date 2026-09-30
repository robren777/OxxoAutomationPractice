package Mobile.directos.mvp3;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;

import Mobile.directos.om.mvp3.OAD;
import Mobile.directos.om.mvp3.ProveedoresDirectos;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;


public class ATC_FT_157_OpcionesAlPresionarElBoteDeBasuraAppProveedoresDirectosTradicional
extends BaseExecution {
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_157_OpcionesAlPresionarElBoteDeBasuraAppProveedoresDirectosTradicional_test(HashMap<String, String> data) throws Exception{ 
  	setupTestData(data);
	DesiredCapabilities cap = new DesiredCapabilities();
	 
	cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
	cap.setCapability("deviceName", data.get("deviceName"));
	cap.setCapability("platformVersion", data.get("platformVersion"));
	cap.setCapability("platformName", data.get("platformName"));
	cap.setCapability("appPackage", data.get("appPackageP"));
	cap.setCapability("appActivity", data.get("appActivityP"));
	cap.setCapability("idle-timeout", 0);
	cap.setCapability("appium:settings[enableMultiWindows]", true);
	cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto
			
	String url = "http://127.0.0.1:4723";
	
	//======= Cerrar Aplicaciones =========
	Thread.sleep(5000);
	Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
	Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
	  
	//======================================================================================================
	AppiumUtil appiumUtil = null;
	try {
		appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}			
 //=======================================================================================================//		
	//Llamado al OM de Proveedores Dierectos
	ProveedoresDirectos ProvDirectos = new ProveedoresDirectos(testCase, appiumUtil, this);
	
	 //=======================================================================================================//
	/*
	 * Paso 1 - Abrir aplicación "Proveedores Directos"
	 */
	addStep("Abrir aplicación \"Proveedores Directos\"");
	Thread.sleep(2000);
	testCase.addScreenShotCurrentStep(appiumUtil,"testImage");
	/*
	 * Paso 2 - Muestra Pantalla de Bienvenida
	 */
	addStep("Muestra Pantalla de Bienvenida");
	Thread.sleep(4000);
	testCase.addScreenShotCurrentStep(appiumUtil,"testImage");
	
	/*
	 * Paso 3 - Seleccionar la opcion “Comenzar proceso de Recepcion de mercancia”
	 */
	addStep("Seleccionar la opcion “Comenzar proceso de Recepcion de mercancia”");
	ProvDirectos.opcionComenzarProcesoRepecionMercancia();
	
	/*
	 * Paso 4 - Muestra pantalla Generar pedido, en su estado inicial
	 */
	addStep("Muestra pantalla Generar pedido, en su estado inicial");
	boolean validacion = ProvDirectos.validaExistePantallaGenPedidoHtaCompra();
	assertTrue(validacion,"Error, No se mostro la pantalla: Generacion de Pedido");
	
	/*
	 * Paso 5 - Seleccionar opción "Generar Pedido y Entrega".
	 */
	addStep("Seleccionar opcion \"Generar Pedido y Entrega\".");
	ProvDirectos.tapRdBtnGenPedidoYEntrega();
	
	/*
	 * Paso 6 - La opción queda seleccionada
	 */
	addStep("La opción queda seleccionada");
	validacion = ProvDirectos.validaRbGenerarPedyEntregaChecked();
	assertTrue(validacion,"Error, No se encontro seleccionada la opcion: Generar Pedido y Entrega (Tradicional)");
	
	/*
	 * Paso 7 - Ingresar Fuente de abasto válido
	 */
	addStep("Ingresar Fuente de abasto valido");
	ProvDirectos.capturarFaValidaEvo(OAD.FaEvo);
	
	/*
	 * Paso 8 - El campo se llena correctamente
	 */
	addStep("El campo se llena correctamente");
	validacion = ProvDirectos.validaFACapturadaPantallaGenerarPedido(OAD.FaEvo);
	assertTrue(validacion,"Error, La fuente de Abasto No se quedo capturada correctamente");
	
	/*
	 * Paso 9 - Ingresar n cantidad de artículos (válidos) más al listado del pedido
	 */
	addStep("Ingresar n cantidad de artaculos (validos) mas al listado del pedido");
	ProvDirectos.capturarSkusPantallaGenerarPedido(OAD.SkusEvo);
	
	/*
	 * Paso 10 - Los artículos se agregan al listado de pedido
	 */
	addStep("Los artículos se agregan al listado de pedido");
	validacion = ProvDirectos.validaPantallaGenPedConArtAgregados();
	assertTrue(validacion,"Error, Los elementos agregados no se encontraron en el listado en pantalla");
	
	/*
	 * Paso 11 - Introducir cantidades aleatorias a los skus.
	 */
	addStep("Introducir cantidades aleatorias a los skus.");
	ProvDirectos.incrementarCantPantallaGP(2);
	
	/*
	 * Paso 12 - Se ingresan las cantidades aleatorias, El campo No. Artículos se llenará con los skus agregados al generar el pedido y el campo Total de Piezas deberá coincidir con la suma de las cantidades agregada de cada uno de ellos.
	 */
	addStep("Se ingresan las cantidades aleatorias, El campo No. Articulos se llenara con los skus agregados al generar el pedido y el campo Total de Piezas debera coincidir con la suma de las cantidades agregada de cada uno de ellos.");
	validacion = ProvDirectos.validaNoArtYTotalPiezasGP();
	assertTrue(validacion,"Error, Las cantidades de No.Articulos y Total de piezas no coinciden con los arituclos capturados");
	
	/*
	 * Paso 13 - Seleccionar el boton eliminar (bote de basura) de alguno de los artículos agregados
	 */
	addStep("Seleccionar el boton eliminar (bote de basura) de alguno de los articulos agregados");
	ProvDirectos.eliminar1ArticuloGP();
	
	/*
	 * Paso 14 - Validar que se muestre el mensaje ¿Esta seguro que desea eliminar el articulo 'X' del listado previamente capturado? Con las opciones “CONSERVAR” Y “ELIMINAR”
	 */
	addStep("Validar que se muestre el mensaje ¿Esta seguro que desea eliminar el articulo 'X' del listado previamente capturado? Con las opciones 'CONSERVAR' Y 'ELIMINAR'");
	validacion = ProvDirectos.validaMsgSeguroEliminarArticulo();
	assertTrue(validacion,"Error, No se encontro el mensaje: Seguro que desea eliminar el articulo.... , boton CONSERVAR, boton ELIMINAR");
	
	/*
	 * Paso 15 - Presionar la opción “CONSERVAR”
	 */
	addStep("Presionar la opcion 'CONSERVAR'");
	ProvDirectos.tapBtnConservar_MsgEliminarArtGP();
	
	/*
	 * Paso 16 - Validar que el articulo no se elimine de la lista
	 */
	addStep("Validar que el articulo no se elimine de la lista");
	validacion = ProvDirectos.validaPantallaGenPedConArtAgregados();
	assertTrue(validacion,"Error, El al articulo no fue conservado despues de cerrar el PopUp");
	
	/*
	 * Paso 17 - Seleccionar Continuar. Nota: Sistema valida que la información obligatoria esté completa. 
	 */
	addStep("Seleccionar Continuar. Nota: Sistema valida que la informacion obligatoria este completa.");
	ProvDirectos.tapBtnContinuarGP();
	
	/*
	 * Paso 18 - Muestra mensaje: ¿Está seguro que desea terminar el escaneo de productos? SI/NO
	 */
	addStep("Muestra mensaje: ¿Esta seguro que desea terminar el escaneo de productos? SI/NO");
	validacion = ProvDirectos.validaMsgSeguroTerminarEscaneo();
	assertTrue(validacion,"Error, No se encontro alguno de los sig elementos: Mensaje Seguro que desea terminar el escaneo, boton NO, Boton SI");
	
	/*
	 * Paso 19 - Selecciona la opcion Si.
	 */
	addStep("Selecciona la opcion Si.");
	ProvDirectos.tapBtnSiGral();
	ProvDirectos.msgAgregarArtAlCatalogo_SE();
	
	/*
	 * Paso 20 - Validar se muestre pantalla de Resumen de pedido donde se visualiza: 
		Nombre de la pantalla 'Resumen de articulos a descargar del camion'(Radio Button para check al inicio de cada articulo y uno en la parte superior para poder seleccionar todos los articulos, Articulo, Descripcion y Cant.)
		Campo No. De Articulos capturados 
		Campo No. De articulos entregados
		Cantidad capturada
		Cantidad entregada
		Boton Modificar pedido
		Boton Generar QR (deshabilitado)
	 */
	addStep("Validar se muestre pantalla de Resumen de pedido donde se visualiza: \r\n"
			+ "Nombre de la pantalla 'Resumen de articulos a descargar del camion'(Radio Button para check al inicio de cada articulo y uno en la parte superior para poder seleccionar todos los articulos, Articulo, Descripcion y Cant.)\r\n"
			+ "Campo No. De Articulos capturados \r\n"
			+ "Campo No. De articulos entregados\r\n"
			+ "Cantidad capturada\r\n"
			+ "Cantidad entregada\r\n"
			+ "Boton Modificar pedido\r\n"
			+ "Boton Generar QR (deshabilitado)");
	validacion = ProvDirectos.validarPantallaResumenDePedido();
	assertTrue(validacion,"Error, No se pudieron validar todos los elementos de la pantalla Resumen de Pedido");
	
	/*
	 * Paso 21 - Seleccionar el boton MODIFICAR PEDIDO
	 */
	addStep("Seleccionar el boton MODIFICAR PEDIDO");
	ProvDirectos.tapBtnModificarPedidoRP();
	
	/*
	 * Paso 22 - Validar que nos regrese a la pantalla principal con el pedido generado 
	 */
	addStep("Validar que nos regrese a la pantalla principal con el pedido generado ");
	validacion = ProvDirectos.validaExistePantallaGenPedidoHtaCompra();
	assertTrue(validacion,"Error, La aplicacion no regreso a la pantalla principal de Pedido Generado");
	
	/*
	 * Paso 23 - Seleccionar el boton eliminar (bote de basura) de alguno de los articulos agregados
	 */
	addStep("Seleccionar el boton eliminar (bote de basura) de alguno de los articulos agregados");
	ProvDirectos.eliminar1ArticuloGP();
	
	/*
	 * Paso 24 - Validar que se muestre el mensaje ¿Esta seguro que desea eliminar el articulo 'X' del listado previamente capturado? Con las opciones “CONSERVAR” Y “ELIMINAR”
	 */
	addStep("Validar que se muestre el mensaje ¿Esta seguro que desea eliminar el articulo 'X' del listado previamente capturado? Con las opciones 'CONSERVAR' Y 'ELIMINAR'");
	validacion = ProvDirectos.validaMsgSeguroEliminarArticulo();
	assertTrue(validacion,"Error, No se encontro el mensaje: Seguro que desea eliminar el articulo.... , boton CONSERVAR, boton ELIMINAR");
	
	/*
	 * Paso 25 - Presionar la opción “ELIMINAR”
	 */
	ProvDirectos.tapBtnEliminar_MsgEliminarArtGP();
	
	/*
	 * Paso 26 - Validar que el articulo se elimine de la lista
	 */
	addStep("Validar que el articulo se elimine de la lista");
	validacion = ProvDirectos.validaPantallaGenPedConArtAgregados();
	assertFalse(validacion, "Error, El auticulo no fue eliminado");
	
	/*
	 * Paso 27 - Seleccionar Continuar. Nota: Sistema valida que la información obligatoria esté completa.
	 */
	addStep("Seleccionar Continuar. Nota: Sistema valida que la información obligatoria esté completa.");
	ProvDirectos.tapBtnContinuarGP();
	
	/*
	 * Paso 28 - Muestra mensaje: ¿Esta seguro que desea terminar el escaneo de productos? SI/NO
	 */
	addStep("Muestra mensaje: ¿Esta seguro que desea terminar el escaneo de productos? SI/NO");
	validacion = ProvDirectos.validaMsgSeguroTerminarEscaneo();
	assertTrue(validacion,"Error, No se mostro el mensaje: ¿Esta seguro que desea terminar el escaneo de productos? SI/NO");
	
	/*
	 * Paso 29 - Selecciona la opción Si.
	 */
	ProvDirectos.tapBtnSiGral();
	
	/*
	 * Paso 30 - Validar se muestre pantalla de Resumen de pedido donde se visualiza: 
		Nombre de la pantalla 'Resumen de articulos a descargar del camion'(Radio Button para check al inicio de cada articulo y uno en la parte superior para poder seleccionar todos los articulos, Articulo, Descripcion y Cant.)
		Campo No. De Articulos capturados 
		Campo No. De articulos entregados
		Cantidad capturada
		Cantidad entregada
		Boton Modificar pedido
		Boton Generar QR (deshabilitado)
	 */
	addStep("Validar se muestre pantalla de Resumen de pedido donde se visualiza: \r\n"
			+ "Nombre de la pantalla 'Resumen de articulos a descargar del camion'(Radio Button para check al inicio de cada articulo y uno en la parte superior para poder seleccionar todos los articulos, Articulo, Descripcion y Cant.)\r\n"
			+ "Campo No. De Articulos capturados \r\n"
			+ "Campo No. De articulos entregados\r\n"
			+ "Cantidad capturada\r\n"
			+ "Cantidad entregada\r\n"
			+ "Boton Modificar pedido\r\n"
			+ "Boton Generar QR (deshabilitado)");
	validacion = ProvDirectos.validarPantallaResumenDePedido();
	assertTrue(validacion,"Error, No se pudieron validar todos los elementos de la pantalla Resumen de Pedido");
	
	/*
	 * Paso 31 - Seleccionar todos los articulos (Check all)
	 */
	addStep("Seleccionar todos los articulos (Check all)");
	ProvDirectos.tapRbCheckAll_RP();
	
	/*
	 * Paso 32 - Validar que todos los articulos se visualicen seleccionados, El boton 'Generar QR' se habilita
	 */
	addStep("Validar que todos los articulos se visualicen seleccionados, El boton 'Generar QR' se habilita");
	validacion = ProvDirectos.validaTodosArticulosSeleccionados_RP(1);
	assertTrue(validacion,"Error, No se mostraron todos los articulos seleccionados");
	validacion = ProvDirectos.validaBtGenerarPedidoHabilitado_RP();
	assertTrue(validacion,"Error, el boton : Generar QR No se ecuentra habilitado");
	
	/*
	 * Paso 33 - Presionar 'GENERAR QR'
	 */
	addStep("Presionar 'GENERAR QR'");
	ProvDirectos.tapBtGenerarQr_RP();
	
	/*
	 * Paso 34 - El CODIGO QR ESTARA DISPONIBLE DURANTE 9:59 MINUTOS
		Boton <TERMINAR>
		Boton <RESUMEN PEDIDO>
		Boton <MODIFICAR QR>
	 */
	addStep("El CODIGO QR ESTARA DISPONIBLE DURANTE 9:59 MINUTOS\r\n"
			+ "\r\n"
			+ "Boton <TERMINAR>\r\n"
			+ "Boton <RESUMEN PEDIDO>\r\n"
			+ "Boton <MODIFICAR QR>");
	validacion = ProvDirectos.validaPantallaQrGenerado();
	assertTrue(validacion,"Error, No se pudo validar la pantalla de QR generado");
	
	/*
	 * Paso 36 - Seleccionar la opción <MODIFICAR QR>
	 */
	addStep("Seleccionar la opcion <MODIFICAR QR>");
	ProvDirectos.tapModificarQR_pantallaQRGenerado();
	
	/*
	 * Paso 37 - Se muestra el mensaje “¿Estas seguro de regresar a editar el pedido?[CANCELAR][CONFIRMAR]
	 */
	addStep("Se muestra el mensaje “¿Estas seguro de regresar a editar el pedido?[CANCELAR][CONFIRMAR]");
	validacion = ProvDirectos.validaMsgSeguroEditarPedido();
	assertTrue(validacion,"Eror, No se mostro el mensaje: ¿Estas seguro de regresar a editar el pedido?[CANCELAR][CONFIRMAR]");
	
	/*
	 * Paso 38 - Seleccionar la opción confirmar
	 */
	addStep("Seleccionar la opcion confirmar");
	ProvDirectos.tapConfirmarRegresarAEditarPedido();
	
	/*
	 * Paso 39 - Validar que nos posicione en la pantalla principal con el pedido generado
	 */
	addStep("Validar que nos posicione en la pantalla principal con el pedido generado");
	validacion = ProvDirectos.validaExistePantallaGenPedidoHtaCompra();
	assertTrue(validacion,"Error, La aplicacion no regreso a la pantalla principal de Pedido Generado");
	
	/*
	 * Paso 40 - Seleccionar el boton eliminar (bote de basura) de alguno de los articulos agregados
	 */
	addStep("Seleccionar el boton eliminar (bote de basura) de alguno de los articulos agregados");
	ProvDirectos.eliminar1ArticuloGP();
	
	/*
	 * Paso 41 - Validar que se muestre el mensaje ¿Esta seguro que desea eliminar el articulo 'X' del listado previamente capturado? Con las opciones “CONSERVAR” Y “ELIMINAR”
	 */
	addStep("Validar que se muestre el mensaje ¿Esta seguro que desea eliminar el articulo 'X' del listado previamente capturado? Con las opciones 'CONSERVAR' Y 'ELIMINAR'");
	validacion = ProvDirectos.validaMsgSeguroEliminarArticulo();
	assertTrue(validacion,"Error, No se encontro el mensaje: Seguro que desea eliminar el articulo.... , boton CONSERVAR, boton ELIMINAR");
	
	/*
	 * Paso 42 - Presionar la opcion ELIMINAR'
	 */
	addStep("Presionar la opcion ELIMINAR'");
	ProvDirectos.tapBtnEliminar_MsgEliminarArtGP();
	
	/*
	 * Paso 43 - Validar que el articulo se elimine de la lista
	 */
	addStep("Validar que el articulo se elimine de la lista");
	validacion = ProvDirectos.validaPantallaGenPedConArtAgregados();
	assertFalse(validacion, "Error, El auticulo no fue eliminado");
	
	//=======================================Cerrar Aplicaciones========================================================//
	Thread.sleep(5000);
	Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
	Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
	//==================================================================================================================//
    
    
  }
  
  /*
   * Proveedores Directos MVP3: MTC-FT-157 Opciones al presionar el bote de Basura en la pantalla Generación de Pedido de La APP “Proveedores Directos”- Tradicional
   * Desc: •	Validar el mensaje correcto al presionar el bote de basura al inicio de la generación de un pedido Preventa 
	•	Validar el mensaje correcto al presionar el bote de basura de un artículo cuando se está modificando el pedido 
	•	Validar el mensaje correcto al presionar el bote de basura de un artículo cuando se está modificando un QR 
 
   * 
   * @autor Alex Aquino
   * @date 24/10/2024
   */
  

  @Override
  public String setTestFullName() {
  	return "ATC_FT_157_OpcionesAlPresionarElBoteDeBasuraAppProveedoresDirectosTradicional";
  }
  
  @Override
  public String setTestDescription() {
  	return "•Validar el mensaje correcto al presionar el bote de basura al inicio de la generación de un pedido Preventa \r\n"
  			+ "•Validar el mensaje correcto al presionar el bote de basura de un artículo cuando se está modificando el pedido \r\n"
  			+ "•Validar el mensaje correcto al presionar el bote de basura de un artículo cuando se está modificando un QR \r\n"
  			+ "";
  }  

  @Override
  public String setTestDesigner() {
  	return "Alex Aquino";
  }  

  @Override
  public String setTestInstanceID() {
  	return null;  
  }
  
  @Override
  public String setPrerequisites() {
  	return "1 Contar con el APK más reciente de “Proveedores Directos”\r\n"
  			+ "2 Contar con una fuente de abastos valida\r\n"
  			+ "3 Contar con artículos en vigencia 1 ";
  }
  
  @Override
  public void beforeTest() {
  }
  
  /*
   * Metodo
   * ******************************************************************************************
   * setupTestData - metodo usado para obtener la informacion/isumos necesarios para la ejecucion del caso de prueba
   *                 de la nube de Evolucion y evitar que el programa se interrumpa por el tiempo paja/idle timeout 
   *                 del driver original
   *                 (Fuente de Abasto/Num Proveedor, Skus, Etc.)   
   * 
   * @param data : data provider del caso automatizado(archivo.xlsx)
   * @return N/A
   */
  private void setupTestData(HashMap<String, String> data) {
      try {
          DesiredCapabilities cap = new DesiredCapabilities();
          cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
          cap.setCapability("deviceName", data.get("deviceName"));
          cap.setCapability("platformVersion", data.get("platformVersion"));
          cap.setCapability("platformName", data.get("platformName"));
          cap.setCapability("appPackage", data.get("appPackageP"));
          cap.setCapability("appActivity", data.get("appActivityP"));
          cap.setCapability("noReset", true);
          
          String url = "http://127.0.0.1:4723";
          
          AppiumUtil appiumUtil = new AppiumUtil(url, cap);
          OAD OxxoAlDia = new OAD(testCase, appiumUtil, this);
          OxxoAlDia.validaCrEnNubeEvolucion(data.get("IpPV"));
          OxxoAlDia.ObtenerFAValidaNubeEvo(10, 3);
          OxxoAlDia.obtenerSkusValidosxFANubeEvo(3, 3);
      } catch (Exception e) {
          Assert.fail("Error de conexion a la BD");
      }
  }

}
