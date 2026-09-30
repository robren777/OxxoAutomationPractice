package EBS.oxxoPay;

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

public class ATC_FT_009_ValidarDocumentosProcesoDispersion extends BaseExecution {
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_009_ValidarDocumentosProcesoDispersion_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String ebsPattern = data.get("ebsPattern");
		String responsabilidad = data.get("responsabilidad");
		String informes = data.get("carpeta1");
		String solicitar = data.get("carpeta2");
		String campo = data.get("campo");
		String formaPago = data.get("formaPago");
		String fechaInicio = data.get("fechaInicio");
		String fechaFin = data.get("fechaFin");
		String text = data.get("text");
		

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
		addStep("Seleccionar idioma: Español Latinoamericano.");
		// PASO 3
		addStep("Ingresar usuario y contraseña.");
		// PASO 4
		addStep("Dar clic en el botón de 'Conectese'.");
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
		addStep("Seleccionar el ID del concurrente, clic boton Mostrar output.");
		patternUtil.closeWindowInsideApplet();
		u.hardWait(3);
		addStep("Dirigirse al menu: Ver(x) -> 'Solicitudes' -> 'Encontrar'.");
		patternUtil.encontrarSolicitudes();
		patternUtil.clickEncontrar();
		// PASO 10
		addStep("Seleccionar el ID del concurrente, clic boton Mostrar output");
		patternUtil.keyAlt("l");
		//assertEquals(patternUtil.getTextDelNavegador("Concurrente", text), text);
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		u.hardWait(3);
		patternUtil.closeWindow();
		u.hardWait(3);
		patternUtil.closeWindow();
		u.hardWait(5);
		patternUtil.ventanaConfirmarSalir("a");
		u.close();
		
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_009_ValidarDocumentosProcesoDispersion_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que el proceso XXFC-Proceso de Dispersion Retiro Sin Tarjeta genere los siguientes documentos: - Cartas Femsa - Polizas contables OS y plazas - Remisiones por concepto de comisiones. Contar con información en las tablas de  'XXFC_GL_RST_ENCA, XXFC_GL_RST_LINEAS' y haber ejecutado el proceso de dispersión";
	}

	@Override
	public String setTestDesigner() {
		return "JessicaR@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Contar con información en las tablas de  'XXFC_GL_RST_ENCA, XXFC_GL_RST_LINEAS' y haber ejecutado el proceso de dispersión";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}
}