package EBS.nexxo.cho52088158;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.Sikuli.MetodosSikuli;
import EBS.Sikuli.PatternUtil;
import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_002_EjecucionProcesoXXFCListadoTiendasMigracionesTipoCRT extends BaseExecution {
	
@Test(dataProvider = "data-provider")
	
	public void ATC_FT_002_EjecucionProcesoXXFCListadoTiendasMigracionesTipoCRT_Test(HashMap<String, String> data)
	          throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String nombre = data.get("nombre");
		String compania = data.get("compania");
		String estadoFinanciero = data.get("estadoFinanciero");
		String crSuperior = data.get("crSuperior");
		String tipoCr = data.get("tipoCr");
		
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);

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
		addStep("Ingresar a la Responsabilidad MXFINA-GLXXXXX-Super Usuario_INF_FIN_OS.");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		paginaInicial.carpetaSolicitudesElement.click();
		paginaInicial.carpetaRequestElement.click();
		paginaInicial.runStandardElement.click();
		
		// PASO 3
		addStep("Ejecucion del concurrente: "
				+ "Ver > Solicitudes > Ejecutar una nueva Solicitud > Solicitud Unica > Aceptar > XXCO-Proceso Carga Entidades ICM NEXXO ");
		p.runApplet();
		p.inicio();
		p.ingresarCampo(nombre);
		
		// PASO 4
		addStep("Ingresar los datos correspondientes a la Entidad y las fechas para los cuales se requiere enviar la informacion hacia ICM. ");
		p.soloIngresarCampo(compania);
		p.ingresarCampo(estadoFinanciero);
		p.ingresarCampo(crSuperior);
		p.presionarTabNVeces(2);
		p.ingresarCampo(tipoCr);
		p.aceptarArchivo1();
		
		// PASO 5
		addStep("Se valida en las tablas intermedias de Oracle EBS que se deposite la informacion con la nueva estructura para la fusion de las tiendas de Poza Rica hacia Veracruz.");
		p.ejecutar();
		p.otraSolicitud();
		p.vers();
		p.encontrar();
		
		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);
		patternUtil.verificarEstado("ejecutandoVerde", 6000000);
		p.altF4Aceptar();
		u.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_EjecucionProcesoXXFCListadoTiendasMigracionesTipoCRT_Test";
	}

	@Override
	public String setTestDescription() {
		return "Ejecucion del Proceso con los parametros que se muestran como obligatorios para ingresar."
				+ "Prerequisitos: Acceso a la responsabilidad: "
				+ "MXFINA-GLXXXXX-Super Usuario_INF_FIN_OS";
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
				+ "MXFINA-GLXXXXX-Super Usuario_INF_FIN_OS";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

}