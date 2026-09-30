package EBS.cajasInteligentes.cho52071603;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

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

public class ATC_FT_001_ReporteDeConveniosEnEstatusDeCapturaErrorYProceso extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ReporteDeConveniosEnEstatusDeCapturaErrorYProceso_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String nombreSolicitud = data.get("nombre_solicitud");
		String ebsPattern = "EBS\\"+data.get("ebsPattern");

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
		addStep("Seleccionar idioma: Español Latinoamericano");
		// PASO 3
		addStep("Ingresar usuario y contraseña");
		// PASO 4
		addStep("Dar clic en el botón de 'Conectese'");
		inicio.login();
		// PASO 5
		addStep("Ingresar a la responsabilidad 'MXOXXO-AR10MON-AJUSTE-TRANSACCIONES'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar la carpeta Solicitudes y click en RUN");
		paginaInicial.clickTransaccionesSolicitudes();
		paginaInicial.clickAppTransacciones();
		u.hardWait(10);
		patternUtil.runApplet();
		u.hardWait(20);
		// PASO 7
		addStep("Clic en opcion Solicitud unica, Aceptar.");
		patternUtil.solicitudUnica();
		// PASO 8
		addStep("Se ingresa el nombre 'XXFC - Reporte de Convenios en estatus de captura, error y proceso'");
		u.hardWait(5);
		patternUtil.typeAndTab(nombreSolicitud);

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "" + "Tener asignada la responsabilidad 'MXOXXO-AR10MON-AJUSTE-TRANSACCIONES'";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "" + "Verificar que dentro de una responsabilidad con grupo de solicitudes 'Receivables AI'"
				+ "y aplicacion 'Receivables' exista el concurrente 'XXFC-Reporte de convenios en estatus de captura,error y proceso' "
				+ "Requisitos: " + "1 Tener asignada la responsabilidad 'MXOXXO-AR10MON-AJUSTE-TRANSACCIONES'";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "JoseO@Hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_ReporteDeConveniosEnEstatusDeCapturaErrorYProceso";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
