package Mobile.cedis.mvp3.handheld.sprint3;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

public class ATC_FT_007_ValidacionDeFormatoJsonTeniendoHoraDesfasadaConEntreElEquipoConElServid extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_007_ValidacionDeFormatoJsonTeniendoHoraDesfasadaConEntreElEquipoConElServid_test(
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

		Mobile.cedis.om.C03_Acreditacion_de_Danados danados = new Mobile.cedis.om.C03_Acreditacion_de_Danados(testCase, appiumUtil,this);

		/*
		 * Paso 1 Posicionarse en la pantalla de Acreditación, para capturar el listado de articulos dañados.
		 * Resultado:? Se muestra pantalla para capturar el listado de articulos
		 * dañados.
		 */
		danados.pantallaDanados();
		addStep("Posicionarse en la pantalla de Acreditación, para capturar el listado de articulos dañados."
				+ "Resultado:? Se muestra pantalla para capturar el listado de articulos dañados.");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		

		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));
		
		//=================================== Inicio de Caso =====================================//
//
//		String consultaPAVZONEG = "select * from rdm100.xxfc_pav where ROWNUM <= 10 order by INSERT_DATE desc";
//		//Para el script anterior(select * from rdm100.xxfc_pav) se modifica ya que se tarda en procesar y si se quita el _zone_v regresa una cantidad de datos grande
//		//por lo que se modifica a ese script para que muestre datos correctamente
//		String consultaPAVZONEe = "select * from rdm100.xxfc_pav_zone_v where DEST_ID = '21222' and rownum <= 1";
//		
//		
//		addStep("Se realiza la consulta a la BD de RDM MTY");
//		SQLResult resultQueryIS = dbLog.executeQuery(consultaPAVZONEe);
//		boolean validacosnulta= resultQueryIS.isEmpty();
//		System.out.print(validacosnulta);
//		testCase.addQueryEvidenceCurrentStep(resultQueryIS);
//		assertFalse(validacosnulta, "No existen documentos de tipo DOC_TYPE='INV' pendientes de procesar");

		//=================================== Inicio de Caso =====================================//
		/*
		 *  Paso 1 - Se dirige a la pantalla del FUE (Formato Único de Entrega) 
		 */
		addStep("Se dirige a la pantalla del FUE (Formato Único de Entrega)");
		
//		/*
//		 *  Paso 2 - ?Se tiene que verificar que la información coincida con lo que se haya registrado tanto en Recepción, 
//		Cajas Sin Etiquetas, Faltantes, Sobrantes y Dañados como en Devolución de Mercancía, Devolución de Canastillas, 
//		Canastillas en Tienda y Devolución de Contenedores (en caso de que halla registro en cada una de estas secciones),
//		sus respectivas firmas y el número de BOL en cada movimiento de tienda (Recepción, Faltante/Sobrante/Dañado, 
//		Dev. de Mercancía y Dev. de Canastillas)
//		 */
		addStep("?Se tiene que verificar que la información coincida con lo que se haya registrado tanto en Recepción, \r\n"
				+ "//		Cajas Sin Etiquetas, Faltantes, Sobrantes y Dañados como en Devolución de Mercancía, Devolución de Canastillas, \r\n"
				+ "//		Canastillas en Tienda y Devolución de Contenedores (en caso de que halla registro en cada una de estas secciones),\r\n"
				+ "//		sus respectivas firmas y el número de BOL en cada movimiento de tienda (Recepción, Faltante/Sobrante/Dañado, \r\n"
				+ "//		Dev. de Mercancía y Dev. de Canastillas)");
//		/*
//		 *  Paso 3 - Al final de la pantalla del FUE, se muestra los botones de MODIFICAR DEVOLUCIONES Y TERMINAR
//		 */
		addStep("Al final de la pantalla del FUE, se muestra los botones de MODIFICAR DEVOLUCIONES Y TERMINAR");
//		/*
//		 *  Paso 4 - ?Se muestran dichos botones seleccionado el botón de TERMINAR
//		 */
		addStep("?Se muestran dichos botones seleccionado el botón de TERMINAR");
//		/*
//		 *  Paso 5 - Se realiza el proceso del correo generando el documento del FUE y se finaliza el proceso con el 
//		mensaje "Recepción de Pedido Exitoso" con la opción de CONFIRMAR
//		 */
		addStep("Se realiza el proceso del correo generando el documento del FUE y se finaliza el proceso con el mensaje "
				+ "\"Recepción de Pedido Exitoso\" con la opción de CONFIRMAR");
//		/*
//		 *  Paso 6 - ?Dar clic en: "CONFIRMAR" con lo cual se termina el proceso de recepción correctamente
//		 */
		addStep("?Dar clic en: \"CONFIRMAR\" con lo cual se termina el proceso de recepción correctamente");
		
//		/*
//		 *  Paso 7 - Abrir el POSTMAN y realizar la configuración del endpoint para consultar la información
//		 */
		addStep("Abrir el POSTMAN y realizar la configuración del endpoint para consultar la información");
		
//		 *  Paso 8 - ?Se abre el postman donde se muestra el cuadro para colocar la liga del endpoint  
//		https://zdbzprfctlpy01.azurewebsites.net/api/getRankingW150
		addStep("?Se abre el postman donde se muestra el cuadro para colocar la liga del endpoint  https://zdbzprfctlpy01.azurewebsites.net/api/getRankingW150\r\n");
//		 */
//		 *  Paso 9 - En pestaña de BODY agregar el código del JSON Request
//		 */
		addStep("?En pestaña de BODY agregar el código del JSON Request");
//		 */
//		 *  Paso 10 - ?Se muestra el response de manera exitosa.
//		 */
		addStep("?Se muestra el response de manera exitosa.");
//		 */
//		 *  Paso 11 - Se revisa la información que hay en el response donde se muestran los campos con el siguiente formato:
//		Fecha: "yyyy-MM-dd" (Ejemplo: 2024-05-22)
//		Hora: "hh:mm:ss" (Ejemplo: 15:11:23)
//		Fecha y Hora: "yyyy-MM-dd hh:mm:ss" (Ejemplo: 2024-05-22 15:11:23)
//		 */
		addStep("Se revisa la información que hay en el response donde se muestran los campos con el siguiente formato:\r\n"
				+ "Fecha: \"yyyy-MM-dd\" (Ejemplo: 2024-05-22)\r\n"
				+ "Hora: \"hh:mm:ss\" (Ejemplo: 15:11:23)\r\n"
				+ "Fecha y Hora: \"yyyy-MM-dd hh:mm:ss\" (Ejemplo: 2024-05-22 15:11:23)");
//		 */
//		 *  Paso 12 - ?El formato Json se muestra de manera correcta.
//		 */
		addStep("?El formato Json se muestra de manera correcta");
//		 */
//		 *  Paso 13 - Se realiza la consulta en el servidor del registro de la transferencia realizada
//		 */
		addStep("Se realiza la consulta en el servidor del registro de la transferencia realizada");
//		 */
//		 *  Paso 14 - ?Se confirma que se muestra el registro de la transferencia registrada en el servidor aun y con que la hora entre la handheld sea diferente.
//		 */
		addStep("?Se confirma que se muestra el registro de la transferencia registrada en el servidor aun y con que la hora entre la handheld sea diferente.");
	}
	
		/*
		 * Cedis MVP3 Sprint 2: ATC-FT-007 Validación de formato Json teniendo hora desfasada con entre el equipo con el servidor
		 * Descripción:Verificar que al concluir el proceso al tener la hora desfazada entre la handheld y el servidor el archivo Json mantenga la estructura correcta.
		 * @autor Vanessa Torres
		 * @date 23/10/2024
		 */ 
		 	
		@Override
		public String setTestFullName() {
			return "ATC-FT-007 Validación de formato Json teniendo hora desfasada con entre el equipo con el servidor";
		}
		
		@Override
		public String setTestDescription() {
			return "Verificar que al concluir el proceso al tener la hora desfazada entre la handheld y el servidor el archivo Json mantenga la estructura correcta.";
		}
		
		@Override
		public String setTestDesigner() {
			return "Vanessa Torres";
		}
		
		@Override
		public String setTestInstanceID() {
			return null;
		}
		
		@Override 
		public String setPrerequisites() {
			return "1. Realizar el proceso completo de Recepción de Transferencia de una Tienda "
					+ "teniendo diferencia entre la hora del servidor y el dispositivo";
		}
		
		@BeforeTest
		public void beforeTest() {
		}

}