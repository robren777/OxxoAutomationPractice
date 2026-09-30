package EBS.cajasInteligentes.cho52068367;

import java.util.HashMap;

import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GenericSikuliMethods;
import EBS.util.GlobalVariables;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_ValidarNoErrorXMLBalanzaYseGeneraXML extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ValidarNoErrorXMLBalanzaYseGeneraXML_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String nombreSolicitud = data.get("nombre_solicitud");
		String rfc = data.get("rfc");
		String periodo = data.get("periodo");

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		GenericSikuliMethods SkMethod = new GenericSikuliMethods();

		SikuliUtil sk = new SikuliUtil();

		Pattern pathimg = new Pattern(
				GlobalVariables.PATTERN_EBS+"MTCFT001_NoErrorXMLBalanza\\checkbox.png");
		Pattern pathimg1 = new Pattern(
				GlobalVariables.PATTERN_EBS+"MTCFT001_NoErrorXMLBalanza\\Run_Applet.jpg");
		Pattern imgBtnEjecutar = new Pattern(
				GlobalVariables.PATTERN_EBS+"MTCFT001_NoErrorXMLBalanza\\Ejecutar_Solicitud.jpg");
		Pattern imgBtnVer = new Pattern(
				GlobalVariables.PATTERN_EBS+"MTCFT001_NoErrorXMLBalanza\\verX.jpg");
		Pattern imgBtnSolicitudes = new Pattern(
				GlobalVariables.PATTERN_EBS+"MTCFT001_NoErrorXMLBalanza\\Solicitudes.jpg");
		Pattern imgBtnEncontrarSolicitudes = new Pattern(
				GlobalVariables.PATTERN_EBS+"MTCFT001_NoErrorXMLBalanza\\Encontrar_Solicitudes.jpg");
		Pattern imgBtnOutput = new Pattern(
				GlobalVariables.PATTERN_EBS+"MTCFT001_NoErrorXMLBalanza\\Visualizar_Output.jpg");
		Pattern imgBtnRefresh = new Pattern(
				GlobalVariables.PATTERN_EBS+"MTCFT001_NoErrorXMLBalanza\\Refrescar_Datos.jpg");
		Pattern imgOpenXLS = new Pattern(
				GlobalVariables.PATTERN_EBS+"MTCFT001_NoErrorXMLBalanza\\open_xls.jpg");

		/*
		 * PASOS DEL CASO DE PRUEBA. Matriz: cho52068367
		 */
		System.out.println("error " + ebsUrl);
		// PASO 1
		addStep("Ingresar al ambiente de EBS.");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		// PASO 2
		addStep("Seleccionar idioma: Espanol Latinoamericano.");
		// PASO 3
		addStep("Ingresar usuario y contrasena.");
		// PASO 4
		addStep("Dar clic en el botón de 'Conectese'.");
		inicio.login();
		// PASO 5
		addStep("Ingresar a la responsabilidad 'MXOXXO-FSXXXXX-Usuario_FISCAL_OS'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar la opcion 'XXFC - SAT Contabilidad Electronica' ");
		paginaInicial.clickContabilidadElectronica();
		// PASO 7
		addStep("Seleccionar la opcion 'Solicitudes de Contabilidad Electronica'");
		paginaInicial.clickSolicitudesContabilidadElectronica();
		// addStep("Click para habilitar la Applet de JAVA \n ");
		Thread.sleep(5000);
		sk.screen.click(pathimg);
		// addStep("Click en boton RUN \n ");
		Thread.sleep(5000);
		sk.screen.click(pathimg1);
		// PASO 8
		addStep("Se ingresa el nombre 'XXFC - SAT Genera XML de Balanza' ");
		Thread.sleep(10000);
		sk.screen.type(Key.TAB);
		// PASO 9
		addStep("Se Ingresan los parametros: RFC,Periodo,Tipo de envio");
		Thread.sleep(10000);
		sk.screen.type(Key.ENTER);
		Thread.sleep(2000);
		sk.screen.type(nombreSolicitud);
		Thread.sleep(1000);
		sk.screen.type(Key.TAB);
		Thread.sleep(1000);
		sk.screen.type(rfc);
		Thread.sleep(1000);
		sk.screen.type(Key.TAB);
		Thread.sleep(1000);
		sk.screen.type(periodo);
		Thread.sleep(2000);
		sk.screen.type("N");
		Thread.sleep(1000);
		sk.screen.type(Key.ENTER);
		// PASO 10
		addStep("Click en Ejecutar");
		Thread.sleep(1000);
		sk.screen.click(imgBtnEjecutar);
		// PASO 11
		addStep("Seleccionar 'No' en el mensaje de decision '¿Ejecutar otra solicitud?'");
		Thread.sleep(1000);
		sk.screen.type(Key.TAB);
		Thread.sleep(3000);
		sk.screen.type(Key.ENTER);
		// PASO 12
		addStep("Seleccionar la opcion en el menu: Ver(x)>Solicitudes>Encontrar");
		Thread.sleep(8000);
		sk.screen.click(imgBtnVer);
		Thread.sleep(8000);
		sk.screen.click(imgBtnSolicitudes);
		Thread.sleep(15000);
		sk.screen.click(imgBtnEncontrarSolicitudes);
		// PASO 13
		Thread.sleep(60000);
		sk.screen.click(imgBtnRefresh);
		addStep("Visualizar Output");
		Thread.sleep(10000);
		sk.screen.click(imgBtnOutput);
		Thread.sleep(3000);
		sk.screen.click(imgOpenXLS);
		Thread.sleep(10000);
		SkMethod.MasterSikuliClose(sk);

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Acceso a la responsabilidad 'MXOXXO-FSXXXXX-Usuario_FISCAL_OS'.";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que al ejecutar el concurrente 'XXFC - SAT Genera XML de Balanza' finalice sin error y genere el XML correctamente "
				+ "Requisitos: " + "1 Acceso a la responsabilidad 'MXOXXO-FSXXXXX-Usuario_FISCAL_OS'.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "JoseO@Hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_ValidarNoErrorXMLBalanzaYseGeneraXML";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
