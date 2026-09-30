package EBS.habilitacionNESConveniosFinancieros;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSCrearConvenios;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;


public class ATC_FT_001_ValidarPantallaCreacionConvenioMuestreSegmentosMaestrosYsePuedanSeleccionar extends BaseExecution {

	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ValidarPantallaCreacionConvenioMuestreSegmentosMaestrosYsePuedanSeleccionar_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String form = data.get("form");
		String rfc = data.get("rfcFacturacion");
		String correo = data.get("correo");
		String ebsPattern = data.get("ebsPattern");
		String num;
		
		

		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		EBSCrearConvenios convenioUnico = new EBSCrearConvenios(u, testCase);
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
		addStep("Ingresar usuario y contrase�a");
		// PASO 4
		addStep("Dar clic en el bot�n de 'Conectese'");
		inicio.login();
		// PASO 5
		addStep("Seleccionar la responsabilidad de " + responsabilidad);
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Dar clic en el menu:" + responsabilidad + ">" + form);
		paginaInicial.clickCreacionConvenios();
		u.hardWait(5);
		convenioUnico.selectNegocio();
		u.hardWait(5);
		convenioUnico.typeRFC(rfc);
		u.hardWait(5);
		convenioUnico.typeCorreo(correo);
		u.hardWait(5);
		u.scroll(200);
		u.hardWait(5);
		convenioUnico.fillTipoNegociacion();
		num = convenioUnico.GenerarConvenio();
		System.out.println(num);
		u.hardWait(5);
		patternUtil.runOpenOrDownload();
		u.hardWait(10);
		patternUtil.closeWindow();
		
		
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Contar con la responsabilidad registrada "
				+ "Debera de contarse con registros a procesar";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que se muestre el concurrente para ejecutar Reporte de Consultas ITEMS Cobros de Servicios."
				+ "Prerequisitos: Contar con la responsabilidad registrada "
				+ "Debera de contarse con registros a procesar";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "JoseO@Hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_ValidarPantallaCreacionConvenioMuestreSegmentosMaestrosYsePuedanSeleccionar";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
