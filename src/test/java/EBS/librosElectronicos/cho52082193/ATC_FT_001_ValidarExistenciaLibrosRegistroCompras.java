package EBS.librosElectronicos.cho52082193;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GenericMethods;
import EBS.util.pageObjectModel.ebsInternational.EBSPaginaInicialInternacional;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_ValidarExistenciaLibrosRegistroCompras extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_001_ValidarExistenciaLibrosRegistroCompras_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		String nombreEjecutarSolicitud = data.get("nombreEjecutarSolicitud");
		String juegoDeLibros = data.get("juegoDeLibros");
		String entidadLegal = data.get("entidadLegal");

		String ebsPattern = data.get("ebsPattern");

		HashMap<String, Pattern> ImgPatter = new HashMap<>();
		ArrayList<Pattern> patterns = new ArrayList<>();
		ArrayList<String> ListImage = new ArrayList<>();
		Pattern pathimg = new Pattern();

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		SikuliUtil sk = new SikuliUtil();
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicialInternacional paginaInicial = new EBSPaginaInicialInternacional(u, testCase);
		GenericMethods action = new GenericMethods(u);
		String pathImg = sk.PatternPath("EBS\\"+ebsPattern);
		File Folder = new File(pathImg);
		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		// PASO 1
		addStep("Ingresar al ambiente de EBS.");
		// PASO 2
		addStep("Seleccionar idioma: Español Latinoamericano.");
		// PASO 3
		addStep("Ingresar usuario y contraseña.");
		// PASO 4
		addStep("Dar clic en el botón de 'Conéctese'.");
		inicio.loginInternacional();
		paginaInicial.setLanguage("Latin American Spanish");
		paginaInicial.home();
		// PASO 5
		addStep("Ingresar a la responsabilidad 'PEOXXO-GLXXXXX-SUPER USUARIO'");
		paginaInicial.clicEnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar: LAD ADD-On Perú -> Informes.");
		paginaInicial.clicEnCarpeta(carpeta);
		u.hardWait(2);
		action.genericClick(u.getDriver(), paginaInicial.informesPeGlSUElement);
		u.hardWait(10);
		patternUtil.runAppletEbsInternacional();
		u.hardWait(4);
		patternUtil.submitANewRequest("s", "a");
		// PASO 7
		addStep("Ingresar en el campo Nombre: 'XXPE - Libro Registros de Compras', tab.");
		patternUtil.typeAndTab(nombreEjecutarSolicitud);
		u.hardWait(2);
		patternUtil.typeAndTab(juegoDeLibros);
		patternUtil.typeAndTab(entidadLegal);
		u.hardWait(3);
		patternUtil.clickPattern("EBSInternacionalThreeDotsBtn", 10);
		u.hardWait(5);
		assertTrue(patternUtil.validatePattern("EBSInternacionalRegistroDeCompras"));
		testCase.addScreenShotCurrentStep("XXPELibrosRegistroDeCompras");
		patternUtil.keyAlt("c");
		patternUtil.keyAlt("d");
		patternUtil.closeWindow();
		u.close();

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Contar con acceso a la responsabilidad 'PEOXXO-GLXXXXX-SUPER USUARIO'.";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Confirmar que el proceso exista dentro de la responsabilidad asignada. Prerrequisito: Contar con acceso a la responsabilidad 'PEOXXO-GLXXXXX-SUPER USUARIO'.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "JesusH@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_ValidarExistenciaLibrosRegistroCompras_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
