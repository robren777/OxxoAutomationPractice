package EBS.nexxo.fase2;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.Sikuli.MetodosSikuli;
import EBS.Sikuli.PatternUtil;
import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_007_EgresosComisionistas_TiendasPozaRica extends BaseExecution {
	
@Test(dataProvider = "data-provider")
	
	public void ATC_FT_007_EgresosComisionistas_TiendasPozaRica_Test(HashMap<String, String> data)
	          throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String nombre = data.get("nombre");
		String entidad = data.get("entidad");
		
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		utils.sql.SQLUtil dbAvebqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
				GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
		
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		File Folder = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		
		/*
		 * SQL Queries
		 */
		String ValidChange = "SELECT * FROM EGRESOSCOMISIONISTA@FCOMICM.US.ORACLE.COM "
				+ "WHERE 1=1 "
				+ "AND TIENDAID LIKE '%10VCZ%50JBX%' "
				+ "ORDER BY REGISTRO DESC";
		String ValidChange2 = "SELECT * FROM EGRESOSCOMISIONISTA@FCOMICM.US.ORACLE.COM "
				+ "WHERE 1=1 "
				+ "AND TIENDAID LIKE '%10VCZ%50DYB%' "
				+ "ORDER BY REGISTRO DESC";	
		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: CHO52084165
		 */

		// PASO 1
		addStep("Ingresar a EBS AVEBQA a la siguiente ruta: http://fcrebsqa.femcom.net:8011");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		inicio.login();
		
		// PASO 2
		addStep("Ingresar a la Responsabilidad MXOXXO-COXXXXX-ADMINISTRADOR.");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		paginaInicial.carpetaReportesElement.click();
		paginaInicial.runElement.click();
		
		// PASO 3
		addStep("Ejecucion del concurrente: "
				+ "Ver > Solicitudes > Ejecutar una nueva Solicitud > Solicitud Unica > Aceptar > XXCO-Proceso Carga Entidades ICM NEXXO ");
		p.inicio();
		p.ingresarCampo(nombre);
		
		// PASO 4
		addStep("Ingresar los datos correspondientes a la Entidad y las fechas para los cuales se requiere enviar la informacion hacia ICM. ");
		p.parametrosEntidad(entidad);
		p.ejecutar();
		p.otraSolicitud();
		p.vers();
		p.encontrar();
		
		// PASO 5
		addStep("Se valida en las tablas intermedias de Oracle EBS que se deposite la informacion con la nueva estructura para la fusion de las tiendas de Poza Rica hacia Veracruz.");
		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);
		patternUtil.verificarEstado("ejecutandoVerde", 6000000);
		p.altF4Aceptar();
		u.close();
		
		System.out.println(GlobalVariables.DB_HOST_AVEBQA);
		System.out.println(ValidChange);
		SQLResult ValidChangeExec = dbAvebqa.executeQuery(ValidChange);

		boolean ValidChangeRes = ValidChangeExec.isEmpty();
		if (!ValidChangeRes) {
			testCase.addQueryEvidenceCurrentStep(ValidChangeExec);
		} else if (ValidChangeRes) {
			testCase.addTextEvidenceCurrentStep("No se encontraron datos en la consulta " + ValidChange);
			testCase.addQueryEvidenceCurrentStep(ValidChangeExec);
		}
		
		System.out.println(GlobalVariables.DB_HOST_AVEBQA);
		System.out.println(ValidChange2);
		SQLResult ValidChangeExec2 = dbAvebqa.executeQuery(ValidChange2);

		boolean ValidChangeRes2 = ValidChangeExec2.isEmpty();
		if (!ValidChangeRes2) {
			testCase.addQueryEvidenceCurrentStep(ValidChangeExec2);
		} else if (ValidChangeRes2) {
			testCase.addTextEvidenceCurrentStep("No se encontraron datos en la consulta " + ValidChange2);
			testCase.addQueryEvidenceCurrentStep(ValidChangeExec2);
		}
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_007_EgresosComisionistas_TiendasPozaRica_Test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que la informacion de los Atributos de las tiendas de Poza Rica se genere con la nueva estructura de Megaplaza Veracruz. "
				+ "Prerequisitos: Acceso a la responsabilidad: MXOXXO-COXXXXX-ADMINISTRADOR, Acceso a BD de instancia AVEBQA";
	}

	@Override
	public String setTestDesigner() {
		return "edwin.ramirez@neoris.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Acceso a la responsabilidad: "
				+ "MXOXXO-COXXXXX-ADMINISTRADOR, Acceso a BD de instancia AVEBQA";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

}