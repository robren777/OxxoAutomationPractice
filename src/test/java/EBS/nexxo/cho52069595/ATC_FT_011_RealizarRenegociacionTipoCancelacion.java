package EBS.nexxo.cho52069595;



import static org.testng.Assert.assertEquals;

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


public class ATC_FT_011_RealizarRenegociacionTipoCancelacion extends BaseExecution {

	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_011_RealizarRenegociacionTipoCancelacion_test(HashMap<String, String> data)
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
		String nombre = data.get("nombre");
		String unidadOperativa = data.get("unidadOperativa");
		String plaza = data.get("plaza");
		String crOrigen = data.get("tiendaOrigen");
		String contrato = data.get("contrato");
		String fechaCierre = data.get("fechaCierre");
		String motivo = data.get("motivo");
		String pagoPenal = data.get("pagoPenal");
		String nombreText;

		

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
		addStep("Ingresar usuario y contrase�a");
		// PASO 4
		addStep("Dar clic en el bot�n de 'Conectese'");
		inicio.login();
		// PASO 5
		addStep("Ingresar a la responsabilidad " + responsabilidad);
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		u.hardWait(3);
		// PASO 6
		addStep("Seleccionar " + subcarpeta + "de la carpeta " + carpeta);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("Seleccionar " + subcarpeta + "de la carpeta " + carpeta);
		paginaInicial.clickOnCapturaRenegociacionContratos();
		u.hardWait(5);
		patternUtil.runApplet();
		u.hardWait(30);
		// PASO 7
		addStep("Consultamos el numero de contrato : " + contrato);
		testCase.addScreenShotCurrentStep("Ventana Captura de Renegociacion/Cancelacion de Contratos");
		patternUtil.typeAndTab(contrato);
		u.hardWait(5);
		nombreText = patternUtil.SimpleCopyText();
		assertEquals(nombreText, plaza + "-" + crOrigen);
		u.hardWait(8);
		sk.screen.type(Key.TAB);
		u.hardWait(5);
		sk.screen.type("c");
		sk.screen.type("n",Key.ALT);
		u.hardWait(10);
		patternUtil.textBoxTypeRight("fechaCierre", 50, fechaCierre);
		u.hardWait(5);
		patternUtil.typeAndTab(motivo);
		u.hardWait(5);
		sk.screen.type("n");
		u.hardWait(5);
		patternUtil.keyAlt("s");
		
		
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return  "1 Tener asignada la responsabilidad 'MXOXXO-PNXXXXX-Admon_OS_ACT_FIJO_OS'";
				
				
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return ""
				+ "Validar que se Asigne el Nombre de Usuario en "
				+ "solicitudes de Renegociacion "
				+ "Prerequisitos:"
				+ "1 Tener asignada la responsabilidad 'MXOXXO-PNXXXXX-Admon_OS_ACT_FIJO_OS'";
				
		
				
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "JoseO@Hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_011_RealizarRenegociacionTipoCancelacion_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}