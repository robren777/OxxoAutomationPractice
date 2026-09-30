package WM.EvolucionRetiroSinTarjeta.HU07;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_010_Ejecucion_del_proceso_de_dispersion_de_CNKTA_desde_el_portal_de_EBS_DEV_OXPAY extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_010_Ejecucion_del_proceso_de_dispersion_de_CNKTA_desde_el_portal_de_EBS_DEV_OXPAY_test(HashMap<String, String> data)
			throws Exception {
		
		/**
		 * Plataforma de EBS http://fcrebsqa.femcom.net:8011/OA_HTML/AppsLocalLogin.jsp
		 * Desc: Ejecucion del proceso de dispersion de CNKTA desde el portal de EBS de manera manual para validar que siga generando los docuemntos
		 * @author Mario Garza
		 * @date 05/03/2024
		 **/
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server"); 
		String userName = data.get("userName");
		//PasswordUtil.generateEncryptPass();
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String ebsPattern = data.get("ebsPattern");
		String informes = data.get("carpeta1");
		String solicitar = data.get("carpeta2"); 
		String tipo = data.get("tipo");
		String fechaInicio = data.get("fechaInicio"); 
		String fechaFin = data.get("fechaFin"); 
		String finalizado = data.get("finalizado");
		

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+ebsPattern);
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		File Folder = new File(pathImg);

		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);

		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: Proceso de dispersion de RST
		 * Llamada de Metodos, clases y objetos
		 */
		
        // PASO 1
		addStep("Ingresar al ambiente de EBS.");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		addStep("Seleccionar idioma: Español Latinoamericano.");
		// PASO 2
		addStep("Ingresar usuario y contraseña.");
		inicio.login();
		testCase.addScreenShotCurrentStep("Login");
		addStep("Dar clic en el boton de 'Conectese'.");
		// PASO 3
		addStep("Ingresar a la responsabilidad '17ADM-SSERVICIOS_ADTIVO_OS'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		testCase.addScreenShotCurrentStep("Responsabilidad");
		addStep("Seleccionar Informes Solicitar -> Standard");
		paginaInicial.clickOnCarpeta(informes);
		paginaInicial.clickOnCarpeta(solicitar);
		paginaInicial.clickStandard17admServicios();
		testCase.addScreenShotCurrentStep("Infomres Solicitar -> Standard");
		u.hardWait(8);
		//PASO 4
		addStep("Descargar el ejecutable java frmServlet y se ejecuta");
		patternUtil.frmServlet();
		testCase.addScreenShotCurrentStep("frmServlet");
		u.hardWait(5);
		patternUtil.runApplet();
		//PASO 5
		patternUtil.solicitudUnica();
		u.hardWait(5);
		addStep("Selecciona los 3 puntitos en el Nombre para buscar el concurrente ");
		patternUtil.buscarNombreConcurrente();
		testCase.addScreenShotCurrentStep("Buscar concurrente");
		// PASO 6
		addStep("Escribe el concurrente: XXFC-Proceso de Dispersion de retiros sin tarjeta ");
		patternUtil.typeAndBuscar(tipo);
		testCase.addScreenShotCurrentStep("Tipo concurrente");
		// PASO 7
		addStep("Selecciona los 3 puntitos para buscar la forma de pago PYIO49");
		patternUtil.buscarFormaDePago();
		testCase.addScreenShotCurrentStep("Forma de pago");
		u.hardWait(5);
		// PASO 8
		addStep("Selecciona lo forma de pago 'PYIO49'");
		patternUtil.seleccionaFormaDePagoDEV_OXPAY();
		testCase.addScreenShotCurrentStep("PYIO49");
		// PASO 9 y 10
		addStep("Selecciona la fecha incio - La fecha se modifica en el archivo .xls");
		patternUtil.escribeFechaInicio(fechaInicio);
		testCase.addScreenShotCurrentStep("Fecha inicio");
		addStep("Selecciona la fecha fin - La fecha se modifica en el archivo .xls");
		patternUtil.escribeFechaFin(fechaFin);
		testCase.addScreenShotCurrentStep("Fecha fin");
		// PASO 11
		addStep("Selecciona Ejecutar");
		patternUtil.clickEjecutar();
		testCase.addScreenShotCurrentStep("Ejecutar");
		// PASO 12
		patternUtil.ejecutarOtraSolicitud("No");
		// PASO 13
		addStep("Encontrar Solicitudes");
		patternUtil.encontrarSolicitudes();
		testCase.addScreenShotCurrentStep("Encontrar soicitudes");
		addStep("Da click en Encontrar");
		patternUtil.clickEncontrar();
		testCase.addScreenShotCurrentStep("Encontrar");
		u.hardWait(10);
		addStep("Dar clic en refrescar Datos");
		patternUtil.clickEnoctrarYRefrescarDatos();
		testCase.addScreenShotCurrentStep("Refrescar Datos");
		addStep("Vizualizar Registro");
		patternUtil.clickVisualizaRegistro();
		testCase.addScreenShotCurrentStep("Visualizar Registro");
        // PASO 14 ------Pendiente de realizar 
		patternUtil.clickCerrarVentanaParametros();
		u.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_010_Ejecucion_del_proceso_de_dispersion_de_CNKTA_desde_el_portal_de_EBS_DEV_OXPAY";
	}

	@Override
	public String setTestDescription() {
		return "Ejecucion del proceso de dispersion de CNKTA desde el portal de EBS de manera manual para validar que siga generando los docuemntos";
	}

	@Override
	public String setTestDesigner() {
		return "Mario.garza@grupotimexico.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Tener la responsabilidad '17ADM-SSERVICIOS_ADTIVO_OS'. ";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}
	@AfterMethod
	public void afterMethod() {

	}
}
