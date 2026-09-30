package EBS.moduloSaldoDiario;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_006_SaldoDiarioComparativoSumarizado extends BaseExecution {
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_006_SaldoDiarioComparativoSumarizado_test(HashMap<String, String> data)
			throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = GlobalVariables.getRpalaciosNeoEbsUsr;
		String password = GlobalVariables.getRpalaciosNeoEbsPwd;
		String responsabilidad = data.get("responsabilidad");
		String ebsPattern = data.get("ebsPattern");
		String otros = data.get("carpeta1");
		String concurrente = data.get("concurrente");
		String tienda = data.get("tienda");
		String fechaInicial = data.get("fechaInicial");
		String fechaFinal = data.get("fechaFinal");

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+ebsPattern);
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Folder = new File(pathImg);
		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);

		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: SALDO DIARIO
		 */

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
		addStep("Dar clic en el boton de 'Conectese'.");
		inicio.login();
		// PASO 5
		addStep("Ingresar a la responsabilidad 'MXOXXO-GLXXXXX-Super Usuario'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Otros --> Solicitudes");
		paginaInicial.clickOnCarpeta(otros);
		paginaInicial.clickRunSolicitudesGLSuperUsuario();
		u.hardWait(3);
		patternUtil.runApplet();
		u.hardWait(3);
		// PASO 7
		addStep("Ingresar a la siguiente ruta: Ver/solicitudes/Ejecutar Solicitud");
		patternUtil.keyAlt("n");
		u.hardWait(3);
		// PASO 8
		addStep("Ejecutar proceso:   XXFC-Proceso de Saldo Diario Comparativo Sumarizado");
		patternUtil.typeAndTab(concurrente);
		patternUtil.nTAB(1);
		u.hardWait(3);
		// PASO 9
		addStep("Ingresa los valores de los parametros de ejecucion: Tienda, Fecha Inicial, Fecha Final");
		u.hardWait(3);
		patternUtil.escribirCajaTienda(tienda, fechaInicial, fechaFinal);
		u.hardWait(3);
		// PASO 10 
		addStep("Clic en el boton EJECUTAR");
		patternUtil.clickEjecutar();
		u.hardWait(3);
		patternUtil.ejecutarOtraSolicitud("No");
		u.hardWait(3);
		patternUtil.closeWindowInsideApplet();
		u.hardWait(3);
		patternUtil.closeWindow();
		u.hardWait(3);
		patternUtil.ventanaConfirmarSalir("a");
		u.close();
		
	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Validar que este proceso mantenga su funcionalidad no sea afectada.";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Acceso al ambiente de pruebas: Ingresar a la liga: http://oxvebsaq1.femcom.net:8000";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}