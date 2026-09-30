package EBS.nexxo.cho52069595;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.Sikuli.PatternUtil;
import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_008_EjecucionProcesoXXFCPNProcesoDeReservaDeRentas extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_008_EjecucionProcesoXXFCPNProcesoDeReservaDeRentas_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		String subcarpeta = data.get("subcarpeta");
		String ebsPattern = data.get("ebsPattern");
		String factura = data.get("factura");
		String unidadOperativa = data.get("unidadOperativa");
		String plaza = data.get("plaza");
		String crOrigen = data.get("tiendaOrigen");
		String contrato = data.get("contrato");
		String lote = data.get("lote");
		String periodo = data.get("periodo");
		String periodoFinal = data.get("periodoFinal");
		String pagosExportados = data.get("pagos");

		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+ebsPattern);
		File Folder = new File(pathImg);
		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		System.out.println("error " + ebsUrl);
		// PASO 1
		addStep("Ingresar al ambiente de EBS");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		// PASO 2
		addStep("Seleccionar idioma: Espanol Latinoamericano");
		// PASO 3
		addStep("Ingresar usuario y contraseña");
		// PASO 4
		addStep("Dar clic en el botón de 'Conectese'");
		inicio.login();
		// PASO 5
		addStep("Ingresar a la responsabilidad " + responsabilidad);
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		u.hardWait(3);
		// PASO 6
		addStep("Seleccionar " + subcarpeta + "de la carpeta " + carpeta);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("Seleccionar " + subcarpeta + "de la carpeta " + carpeta);
		paginaInicial.clickOnProcesoReserva();
		u.hardWait(5);
		patternUtil.runApplet();
		u.hardWait(30);
		// PASO 7
		addStep("Ingresar los parametros de : " + plaza);
		testCase.addScreenShotCurrentStep("Ingreso de parametros");
		patternUtil.typeAndTab(plaza);
		u.hardWait(5);
		patternUtil.typeAndTab(periodo);
		u.hardWait(5);
		patternUtil.typeAndTab(contrato);
		sk.screen.type(Key.ENTER);
		// PASO 8
		addStep("Dar click en Ejecutar");
		testCase.addScreenShotCurrentStep("Ejecutar Solicitud");
		patternUtil.clickEjecutar();
		u.hardWait(5);
		// PASO 9
		addStep("Ejecutar otra solicitud? No");
		testCase.addScreenShotCurrentStep("Ejecutar otra solicitud");
		patternUtil.ejecutarOtraSolicitud("No");
		u.hardWait(5);
		// PASO 10
		addStep("Ver y Encontrar Solicitudes");
		patternUtil.encontrarSolicitudes();
		u.hardWait(10);
		patternUtil.clickEncontrar();
		u.hardWait(10);
		// PASO 11
		addStep("Visualizamos el Output");
		patternUtil.clickVisualizarOutput();
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("Output Solicitud Reserva de Rentas");
		u.hardWait(10);
		patternUtil.closeWindow();
		u.hardWait(5);
		patternUtil.closeActiveWindow();
		// PASO 12
		addStep("Consultamos la Poliza creade de Reserva de Rentas");
		patternUtil.menuResponsabilidades();
		u.hardWait(5);
		patternUtil.ClickenResponsabilidad("mxoxxoglxsuperusario");
		u.hardWait(5);
		patternUtil.nEnter(2);
		u.hardWait(15);
		patternUtil.encontrarAsientos(lote, periodo);
		patternUtil.clickCerrarVentanaParametros();
		u.hardWait(5);
		patternUtil.clickRevisarAsiento();
		u.hardWait(20);
		patternUtil.closeWindow();
		sk.screen.type(Key.ENTER);
		u.hardWait(5);
		patternUtil.closeWindow();

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1 Tener asignada la responsabilidad 'MXOXXO-PNXXXXX-Super Usuario'"
				+ "2 Previamente haber ejecutado el proceso 'XXFC PN Reclasificacion de Contratos y Direcciones NEXXO' CP002";

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "" + "Validar la correcta ejecucion de la Reserva de Rentas posterior "
				+ "a la ejecucion del Proceso de Reclasificacion " + "Requisitos: "
				+ "1 Tener asignada la responsabilidad 'MXOXXO-PNXXXXX-Super Usuario'"
				+ "2 Previamente haber ejecutado el proceso 'XXFC PN Reclasificacion de Contratos y Direcciones NEXXO' CP002";

	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "JoseO@Hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_008_EjecucionProcesoXXFCPNProcesoDeReservaDeRentas";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}