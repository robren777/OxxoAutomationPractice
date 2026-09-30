package EBS.facturacionGlobal.cho52070512;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GenericMethods;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_ValidacionProcesoXXFCProcesoIntegracionVentasPOSIMAFase1 extends BaseExecution {
	
@Test(dataProvider = "data-provider")
	
	public void ATC_FT_001_ValidacionProcesoXXFCProcesoIntegracionVentasPOSIMAFase1_Test(HashMap<String, String> data)
	          throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String mitad1 = data.get("mitad1");
		String mitad2 = data.get("mitad2");
		String periodo = data.get("periodo");
		
		
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
		GenericMethods genericM = new GenericMethods(u);
		
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
		addStep("Ingresar a la Responsabilidad MXOXXO-FSXXXXX-Usuario");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		paginaInicial.runReportesElement.click();
		
		// PASO 3
		addStep("En el menu de Ver > Solicitudes > Ejecutar Nueva Solicitud, buscar el concurrente: XXFC-Proceso Integracion de Ventas POS/IMA.");
		p.runApplet();
		p.inicio();
		p.ingresarNombreAcentos(mitad1, mitad2);
		u.hardWait(2);
		
		
		// PASO 4
		addStep("Ingresar el parametro de Periodo: DIC-2020.");
		p.soloIngresarCampo(periodo);
		p.enter();

		// PASO 5
		addStep("Validar que el concurrente termine de manera correcta.");
		p.ejecutar();
		p.otraSolicitud();
		p.vers();
		p.encontrar();
		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);
		patternUtil.verificarEstado("ejecutandoVerde", 6000000);
		
		// PASO 6
		addStep("Validar que se generen las facturas para las tiendas: 50NIP, 50TUC, 50UMA, 50IRU, 50ABE y 50CUF de manera correcta en las tablas");
		u.hardWait(2);
		p.altF4Aceptar();
		u.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ValidacionProcesoXXFCProcesoIntegracionVentasPOSIMAFase1_Test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que el proceso XXFC-Proceso Integracion de Ventas POS/IMA genere facturas para las tiendas: 50NIP, 50TUC, 50UMA, 50IRU, 50ABE y 50CUF"
				+ "Prerequisitos: Acceso a la responsabilidad: MXOXXO-FSXXXXX-Usuario, Tener registros en tablas: FEM_FIF_STG FEM_FIF_STG_SD MES: DIC-2020";
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
		return "Prerequisitos: Acceso a la responsabilidad: MXOXXO-FSXXXXX-Usuario, Tener registros en tablas: FEM_FIF_STG FEM_FIF_STG_SD MES: DIC-2020";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

}