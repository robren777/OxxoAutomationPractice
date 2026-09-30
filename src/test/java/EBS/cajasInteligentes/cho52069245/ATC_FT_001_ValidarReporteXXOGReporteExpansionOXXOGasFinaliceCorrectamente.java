package EBS.cajasInteligentes.cho52069245;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_ValidarReporteXXOGReporteExpansionOXXOGasFinaliceCorrectamente extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ValidarReporteXXOGReporteExpansionOXXOGasFinaliceCorrectamente_test(
			HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String nombreSolicitud = data.get("nombre_solicitud");
		String ebsPattern = "EBS\\"+data.get("ebsPattern");
		String carpeta = data.get("carpeta");
		String plaza = data.get("plaza");
		String fechaInicial = data.get("fecha_inicial");
		String fechaFinal = data.get("fecha_final");

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
		String pathImg = sk.PatternPath(ebsPattern);
		File Folder = new File(pathImg);
		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);

		/*
		 * PASOS DEL CASO DE PRUEBA. Matriz: cho52069245
		 */
		System.out.println("error " + ebsUrl);
		// PASO 1
		addStep("Ingresar al ambiente de EBS");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		// PASO 2
		addStep("Seleccionar idioma: Español Latinoamericano");
		// PASO 3
		addStep("Ingresar usuario y contraseña");
		// PASO 4
		addStep("Dar clic en el botón de 'Conectese'");
		inicio.login();
		// PASO 5
		addStep("Ingresar a la responsabilidad 'MXOXXO-OM17FAA-CONSULTA FRM' ");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar Otros, solicitudes, Requests y RUN");
		paginaInicial.clickOnCarpeta(carpeta);
		u.hardWait(8);
		paginaInicial.clickRunRequestConsultaFRM();
		u.hardWait(3);
		patternUtil.runApplet();
		u.hardWait(5);
		// PASO 7
		addStep("Seleccionar Solicitud Unica y Aceptar");
		patternUtil.solicitudUnica();
		u.hardWait(5);
		// PASO 8
		addStep("Ingresar en el campo el nombre " + nombreSolicitud);
		patternUtil.typeAndTab(nombreSolicitud);
		u.hardWait(2);
		// PASO 9
		addStep("Ingresar los parametros " + plaza + "," + fechaInicial + "," + fechaFinal);
		sk.screen.type(Key.TAB);
		u.hardWait(2);
		sk.screen.type(plaza);
		u.hardWait(2);
		sk.screen.type(Key.TAB);
		u.hardWait(2);
		sk.screen.type(fechaInicial);
		u.hardWait(2);
		sk.screen.type(fechaFinal);
		u.hardWait(2);
		patternUtil.clickAceptarParametros();
		u.hardWait(5);
		// PASO 10
		addStep("Click en ejecutar");
		patternUtil.clickEjecutar();
		u.hardWait(3);
		// PASO 11
		addStep("Ejecutar nueva Solicitud? No");
		patternUtil.ejecutarOtraSolicitud("No");
		u.hardWait(5);
		// PASO 12
		addStep("Dirigirse al menu Ver, Solicitudes y Encontrar");
		patternUtil.encontrarSolicitudes();
		u.hardWait(15);
		// PASO 13
		addStep("Click en Visualizar Output");
		patternUtil.clickEncontrar();
		u.hardWait(5);
		patternUtil.clickVisualizarOutput();
		u.hardWait(3);
		sk.screen.type(Key.ENTER);
		u.hardWait(3);
		patternUtil.ventanaWarningExcel();
		u.hardWait(3);
		patternUtil.appletMaximize();
		u.hardWait(10);
		patternUtil.closeWindow();
		u.hardWait(3);
		patternUtil.closeWindow();
		u.hardWait(2);
		sk.screen.type(Key.ENTER);
		u.hardWait(3);
		patternUtil.closeWindow();

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "" + "1 Tener asignada la responsabilidad 'MXOXXO-OM17FAA-CONSULTA FRM'"
				+ "2 El usuario debe estar ingresado en la tabla 'apps.xxfc_mapeos_varios'";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "" + "Validar el reporte finalice sin error y muestre los datos correctos a nivel Cabecera"
				+ "Requisitos: " + "1 Tener asignada la responsabilidad 'MXOXXO-OM17FAA-CONSULTA FRM'"
				+ "2 El usuario debe estar ingresado en la tabla 'apps.xxfc_mapeos_varios'";

	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "JoseO@Hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_ValidarReporteXXOGReporteExpansionOXXOGasFinaliceCorrectamente";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
