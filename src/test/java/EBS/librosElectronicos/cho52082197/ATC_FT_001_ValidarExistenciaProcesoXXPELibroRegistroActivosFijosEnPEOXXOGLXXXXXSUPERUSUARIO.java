package EBS.librosElectronicos.cho52082197;

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

public class ATC_FT_001_ValidarExistenciaProcesoXXPELibroRegistroActivosFijosEnPEOXXOGLXXXXXSUPERUSUARIO
		extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_001_ValidarExistenciaProcesoXXPELibroRegistroActivosFijosEnPEOXXOGLXXXXXSUPERUSUARIO_test(
			HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		String nombreEjecutarSolicitud = data.get("nombreEjecutarSolicitud");

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
		addStep("Seleccionar idioma: Espanol Latinoamericano.");
		// PASO 3
		addStep("Ingresar usuario y contrasena.");
		// PASO 4
		addStep("Dar clic en el boton de 'Conectese'.");
		inicio.loginInternacional();
		paginaInicial.setLanguage("Latin American Spanish");
		paginaInicial.home();
		// PASO 5
		addStep("Ingresar a la responsabilidad 'PEOXXO-GLXXXXX-SUPER USUARIO'");
		paginaInicial.clicEnResponsabilidad(responsabilidad);
		testCase.addScreenShotCurrentStep(responsabilidad);
		// PASO 6
		addStep("Seleccionar: LAD ADD-On Peru -> Informes.");
		paginaInicial.clicEnCarpeta(carpeta);
		testCase.addScreenShotCurrentStep(carpeta);
		u.hardWait(2);
		action.genericClick(u.getDriver(), paginaInicial.informesPeGlSUElement);
		testCase.addScreenShotCurrentStep("applet");
		u.hardWait(10);
		patternUtil.runAppletEbsInternacional();
		u.hardWait(4);
		// PASO 7
		addStep("Seleccionar Solicitud Unica, dar clic en Aceptar.");
		patternUtil.submitANewRequest("s", "a");
		// PASO 8
		addStep("Ingresar en el campo Nombre: 'XXPE- Libro Registro de Activos Fijos', tab.");
		patternUtil.typeAndTab(nombreEjecutarSolicitud);
		testCase.addScreenShotCurrentStep(nombreEjecutarSolicitud);
		u.hardWait(3);
		patternUtil.textBoxTypeRight("EBSInternacionalReporteLabel", 30, "070100");
		testCase.addScreenShotCurrentStep("EBSInternacionalReporte");
		assertTrue(patternUtil.validatePattern("EBSInternacionalReporte070100"));
		testCase.addScreenShotCurrentStep("EBSInternacionalReporte070100");
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
		return "Confirmar que el proceso exista dentro de la responsabilidad asignada. Contar con acceso a la responsabilidad 'PEOXXO-GLXXXXX-SUPER USUARIO'.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "JesusH@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
