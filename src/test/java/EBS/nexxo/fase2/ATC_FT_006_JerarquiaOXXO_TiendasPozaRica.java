package EBS.nexxo.fase2;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.Sikuli.MetodosSikuli;
import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_006_JerarquiaOXXO_TiendasPozaRica extends BaseExecution {
	
@Test(dataProvider = "data-provider")
	
	public void ATC_FT_006_JerarquiaOXXO_TiendasPozaRica_Test(HashMap<String, String> data)
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

		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
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
		p.cerrarApplet();
		u.close();
		
		// PASO 5
		addStep("Se valida en las tablas intermedias de Oracle EBS que se deposite la informacion con la nueva estructura para la fusion de las tiendas de Poza Rica hacia Veracruz.");
		//No hay tablas en la matriz
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_JerarquiaOXXO_TiendasPozaRica_Test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que la informacion de los Atributos de las tiendas de Poza Rica se genere con la nueva estructura de Megaplaza Veracruz"
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