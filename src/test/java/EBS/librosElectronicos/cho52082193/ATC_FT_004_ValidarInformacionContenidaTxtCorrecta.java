package EBS.librosElectronicos.cho52082193;

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

public class ATC_FT_004_ValidarInformacionContenidaTxtCorrecta extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_004_ValidarInformacionContenidaTxtCorrecta_test(HashMap<String, String> data) throws Exception {
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
		addStep("Validar se muestren las columnas.");

		// PASO 2
		addStep("Validar la columna 22 'Impuesto al Consumo de las Bolsas de Plástico' el valor por defecto sea '0.00' en todos los registros.");

		// PASO 3
		addStep("Ingresar usuario y contrasena.");

		// PASO 4
		addStep("Dar clic en el boton de 'Conectese'.");
		inicio.login();

		// PASO 5
		addStep("Ingresar a la responsabilidad 'PEOXXO-GLXXXXX-CONSULTA'");
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
		addStep("Ingresar en el campo Nombre: 'XXPE - Libros Registro de Compras', tab.");
		patternUtil.typeAndTab(nombreEjecutarSolicitud);

		// PASO 8
		addStep("Ingresar los parametros:");

		// PASO 9
		addStep("Dar clic en Ejecutar.");
		patternUtil.keyAlt("e");
		// PASO 10
		addStep("Seleccionar 'No' en diálogo de Decision '¿Ejecutar otra solicitud? Si/No'");
		patternUtil.ejecutarOtraSolicitud("No");
		// PASO 11
		addStep("Dirigirse al menu Ver(x) -> Solicitudes -> Encontrar.");
		patternUtil.encontrarSolicitudes();
		patternUtil.clickEncontrar();

		// PASO 12
		addStep("Dar clic en Visualizar Output.");
		patternUtil.verificarEstado("ejecutandoVerde", 6000000);
		patternUtil.keyAlt("z"); // Visualizar el Registro

		// PASO 13
		addStep("Dar clic en Visualizar Registro.");

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_ValidarInformacionContenidaTxtCorrecta_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar la información de acuerdo a los campos requeridos para su envio a SUNAT.";
	}

	@Override
	public String setTestDesigner() {
		return "JesusH@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Haber ejecutado la extracción del ATC_FT_003_ExtraccionLibroRegistroDeCompras.";
	}

	@Override
	public void beforeTest() {

	}
}
