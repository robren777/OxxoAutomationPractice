package EBS.MigracionServiciosCashInOut.Scripts;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
/**
* DESCRIPCIÓN: Realiza la busqueda de la poliza OS
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	-MTC-FT-028- Validar Poliza contable en OS
* 
* PREREQUISITOS:
* 	- Ejecucion del script ATC_FT_026_ValidarConcurrenteProcesoDispersion
* 
* INSUMOS:
* 
* @author Michelle Ceja
* @date 25/Noviembre/2024
*/
public class ATC_FT_028_ValidaPolizaOs extends BaseExecution{

	@Test(dataProvider = "data-provider")

	public void ATC_FT_028_ValidaPolizaOs_test(HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("I24053 - Migración servicios Cash-in y Cash-out");

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String ebsPattern = data.get("ebsPattern");
		String responsabilidad = data.get("responsabilidad");
		String factura = data.get("nfactura");
		String informes = data.get("carpeta1");
		String solicitar = data.get("carpeta2");
		String polizaOs = data.get("polizaOs");
		String periodo = data.get("periodo");
		String journal = data.get("asiento");

		

		

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
		File Fol = new File(pathImg);
		File Folder = new File(pathImg);

		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);
		

		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: RST Devoluciones OXXO PAY
		 */

		// PASO 1
		addStep("Ingresar al ambiente de EBS.");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		// PASO 2
		
		inicio.login();
		// PASO 5
		addStep("Ingresar a la responsabilidad 'MXOXXO-GL17ADM-SERVICIOS'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar: Informes -> Solicitar -> Standard");
		paginaInicial.clickOnCarpeta(informes);
		paginaInicial.clickOnCarpeta(solicitar);
		paginaInicial.clickRunStandardGLServicios();
		u.hardWait(10);
		patternUtil.runApplet();
		u.hardWait(8);
		// PASO 7
		addStep("Ingresar a la Responsabilidad MXOXXO-ARXXXXX-Super Usuario");
		patternUtil.clickImage("cancelarsolicitud");
		patternUtil.clickImage("cambiarResponsabilidad");
		patternUtil.nDOWN(4);
		testCase.addScreenShotCurrentStep("");
		patternUtil.clickImage("aceptarperi");
		addStep("Ingresar en la siguiente ruta para buscar consultar la poliza: Consulta > Asiento");

		patternUtil.doubleClickPattern("consulta", 4);
		patternUtil.nDOWN(3);
		testCase.addScreenShotCurrentStep("");

		u.hardWait(3);

		//despues del double click en consulta 3 tabs
		addStep("Validar poliza OS");
		patternUtil.clickImage("2");
		patternUtil.enterText(polizaOs);


		u.hardWait(3);
		patternUtil.clickImage("periodoOs");
		patternUtil.enterText(periodo);
		testCase.addScreenShotCurrentStep("");

		u.hardWait(2);
		patternUtil.clickImage("encontrarOs");
		patternUtil.clickImage("revisarAsiento");
		
		String journalEbs = patternUtil.SimpleCopyText();
        assertEquals(journal, journalEbs, "El nombre del journal correspondiente a la polizaOs no coincide");
        
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("");
		patternUtil.closeWindow();
		patternUtil.ventanaConfirmarSalir("a");
		
		//u.close();




		


		
		


		


		
		
		
	}

	
	
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Tener movimientos de retiro en la tabla XXFC_MPC_GL_TPC. Tener instalado el Java v.8. Acceso aplicación EBS. Ejecutar el proceso de dispersion";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que al efectuar el proceso de dispersion, esta genere una nota de remisión en AR y este correcta";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_028_ValidaPolizaOs_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
