package EBS.librosElectronicos.cho52082198;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.ebsInternational.EBSPaginaInicialInternacional;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_023_ValidarExtraccionLibroInventarioBalancesBalanceComprobacion extends BaseExecution {
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_023_ValidarExtraccionLibroInventarioBalancesBalanceComprobacion_test(
			HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String ebsPattern = data.get("ebsPattern");
		String responsabilidad = data.get("responsabilidad");
		String lad = data.get("carpeta1");
		String campo = data.get("campo");
		String juegoLibros = data.get("juegoLibros");
		String entidadLegal = data.get("entidadLegal");
		String reporte = data.get("reporte");
		String periodo = data.get("periodo");
		String text = data.get("text");
		String text2 = data.get("text2");

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicialInternacional paginaInicial = new EBSPaginaInicialInternacional(u, testCase);
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
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: CHO52082198
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
		addStep("Ingresar a la responsabilidad 'PEOXXO-GLXXXXX-CONSULTA'");
		paginaInicial.clicEnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar: LAD ADD-On Peru -> Informes ");
		paginaInicial.clicEnCarpeta(lad);
		paginaInicial.clickRunInformesPeGlCo();
		u.hardWait(10);
		patternUtil.runAppletEbsInternacional();
		u.hardWait(4);
		// PASO 7
		addStep("Seleccionar Solicitud Unica, dar clic en Aceptar.");
		patternUtil.submitANewRequest("s", "a");
		// PASO 8
		addStep("Ingresar en el campo Nombre: 'XXPE - Libro Inventarios y Balances', tab.");
		patternUtil.typeAndTab(campo);
		u.hardWait(2);
		assertTrue(patternUtil.validatePattern("inventariosBalances"));
		testCase.addScreenShotCurrentStep("inventariosBalances");
		// PASO 9
		addStep("Ingresar los parámetros. Juego de Libros: LD_PE_OXXO Entidad Legal: CADENA DE COMERCIO PERU S.A.C. Reporte: 030100 Periodo: DIC-20");
		patternUtil.enterText(juegoLibros);
		patternUtil.nTAB(1);
		patternUtil.enterText(entidadLegal);
		patternUtil.nTAB(1);
		patternUtil.enterText(reporte);
		patternUtil.nTAB(1);
		patternUtil.enterText(periodo);
		patternUtil.nTAB(1);
		u.hardWait(3);
		patternUtil.keyAlt("a");
		u.hardWait(3);
		// PASO 10
		addStep("Dar clic en Ejecutar.");
		patternUtil.keyAlt("j");
		// PASO 11
		addStep("¿Ejecutar Otra Solicitud? No.");
		patternUtil.ejecutarOtraSolicitud("No");
		// PASO 12
		addStep("Dirigirse al menú Ver(x) -> Solicitudes -> Encontrar.");
		patternUtil.encontrarSolicitudes();
		patternUtil.keyAlt("o");
		u.hardWait(25);
		// PASO 13
		addStep("Dar clic en Visualizar Output.");
		patternUtil.keyAlt("r");
		patternUtil.keyAlt("r");
		patternUtil.keyAlt("r");
		testCase.addScreenShotCurrentStep("Ejecutado normal");
		u.hardWait(5);
		patternUtil.keyAlt("l");
		u.hardWait(5);
		assertEquals(patternUtil.getTextDelNavegador("libroBalancesCP023", text), text);
		u.hardWait(3);
		patternUtil.closeWindow();
		u.hardWait(3);
		// PASO 14
		addStep("Dar clic en Visualizar Registro.");
		patternUtil.keyAlt("z");
		u.hardWait(5);
		assertEquals(patternUtil.getTextDelNavegador("registroInventarioCP009", text2), text2);
		u.hardWait(3);
		patternUtil.closeWindow();
		u.close();

	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Verificar el proceso finalice correctamente mostrando una salida en formato '.TXT' y las columnas estén separadas mediante '|'. Verificar que el Log muestre en el campo 'l_file_name' la nomenclatura sugerida para el nombrado del arhivo '.txt'. El usuario debe tener asignadas la responsabilidad 'PEOXXO-GLXXXXX-CONSULTA'. ";
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
		return "El usuario debe tener asignadas la responsabilidad 'PEOXXO-GLXXXXX-CONSULTA'. ";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}
}