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

public class ATC_FT_010_RealizarRenegociacionTipoAdendum extends BaseExecution {


	@Test(dataProvider = "data-provider")
	public void ATC_FT_010_RealizarRenegociacionTipoAdendum_test(HashMap<String, String> data) throws Exception {

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
		String lote = data.get("lote");
		String firmaAdendum = data.get("firmaAdendum");
		String inicioAdendum = data.get("inicioAdendum");
		String finalAdendum = data.get("final");
		String escenario = data.get("escenario");
		String mensualNeg = data.get("mensualNeg");
		String porcentaje = data.get("porcentaje");
		String periodicidad = data.get("periodicidad");
		String inicioIncrementos = data.get("inicioIncrementos");
		String opinionValor = data.get("opinionValor");
		String arrendador = data.get("arrendador");
		String sucursal = data.get("sucursal");
		String nombreText;
		String periodo = data.get("periodo");
		String periodoFinal = data.get("periodoFinal");
		String moneda = data.get("moneda");


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
		addStep("Ingresar usuario y contraseña");
		// PASO 4
		addStep("Dar clic en el botón de 'Conectese'");
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
		u.hardWait(40);
		// PASO 7
		addStep("Consultamos el numero de contrato : " + contrato);
		testCase.addScreenShotCurrentStep("Ventana Captura de Renegociacion/Cancelacion de Contratos");
		patternUtil.typeAndTab(contrato);
		u.hardWait(10);
		nombreText = patternUtil.SimpleCopyText();
		assertEquals(nombreText, plaza + "-" + crOrigen);
		u.hardWait(8);
		sk.screen.type("n", Key.ALT);
		u.hardWait(10);
		patternUtil.textBoxTypeRight("firmaAdendum", 100, firmaAdendum);
		u.hardWait(5);
		patternUtil.typeAndTab(inicioAdendum);
		u.hardWait(5);
		sk.screen.type(finalAdendum);
		u.hardWait(5);
		patternUtil.nTAB(4);
		u.hardWait(5);
		patternUtil.typeAndTab("s");
		u.hardWait(5);
		patternUtil.typeAndTab("s");
		u.hardWait(5);
		patternUtil.typeAndTab(escenario);
		u.hardWait(5);
		patternUtil.clickPattern("pagos", 5);
		u.hardWait(5);
		patternUtil.textBoxTypeRight("mensualNegociada", 50, mensualNeg);
		u.hardWait(5);
		patternUtil.textBoxTypeRight("formula", 50, "");
		u.hardWait(5);
		sk.screen.type(porcentaje);
		u.hardWait(5);
		sk.screen.type(Key.TAB);
		u.hardWait(5);
		sk.screen.type(Key.TAB);
		u.hardWait(5);
		patternUtil.typeAndTab(periodicidad);
		u.hardWait(5);
		sk.screen.type(Key.TAB);
		u.hardWait(5);
		patternUtil.typeAndTab(inicioIncrementos);
		u.hardWait(5);
		patternUtil.typeAndTab(opinionValor);
		u.hardWait(5);
		patternUtil.clickPattern("proveedores", 5);
		u.hardWait(5);
		patternUtil.textBoxTypeBelow("arrendador", 30, arrendador);
		u.hardWait(5);
		patternUtil.typeAndTab(sucursal);
		u.hardWait(5);
		patternUtil.keyAlt("s");
		paginaInicial.clickOnReportesReserva();
		u.hardWait(5);
		patternUtil.runApplet();
		u.hardWait(30);
		patternUtil.solicitudUnica();
		u.hardWait(10);
		// PASO 7
		addStep("Ingresar el nombre : " + nombre);

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1 Tener asignada la responsabilidad 'MXOXXO-PNXXXXX-Admon_OS_ACT_FIJO_OS'";

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

		return "ATC_FT_010_RealizarRenegociacionTipoAdendum";


	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}