package EBS.librosElectronicos.cho52082193;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.sikuli.script.Pattern;
import org.testng.Assert;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GenericMethods;
import EBS.util.SplitStringForEveryNChar;
import EBS.util.pageObjectModel.ebsInternational.EBSPaginaInicialInternacional;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_003_ExtraccionLibroRegistroDeCompras extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_003_ExtraccionLibroRegistroDeCompras_test(HashMap<String, String> data) throws Exception {
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
		String reporte = data.get("reporte");
		String periodo = data.get("periodo");
		String LibroRegistrosDeComprasLog =data.get("registro");
		int tiempoDeEjecucion = 10800000;

		String ebsPattern = data.get("ebsPattern");

		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
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
		addStep("Ingresar a la responsabilidad 'PEOXXO-GLXXXXX-CONSULTA'");
		paginaInicial.clicEnResponsabilidad(responsabilidad);
		testCase.addScreenShotCurrentStep(u, "IngresarALaResponsabilidad");
		// PASO 6
		addStep("Seleccionar: LAD ADD-On Perú -> Informes.");
		paginaInicial.clicEnCarpeta(carpeta);
		u.hardWait(2);
		action.genericClick(u.getDriver(), paginaInicial.informesPeGlSUElement);
		testCase.addScreenShotCurrentStep(u, "SeleccionarLADADDOnPeruInformes");
		u.hardWait(10);
		patternUtil.runAppletEbsInternacional();
		u.hardWait(4);
		testCase.addScreenShotCurrentStep("SeleccionarSolicitudUnicaDarClicEnAceptar");
		patternUtil.submitANewRequest("s", "a");
		// PASO 7
		addStep("Ingresar en el campo Nombre: 'XXPE - Libro Registros de Compras', tab.");
		patternUtil.typeAndTab(nombreEjecutarSolicitud);
		u.hardWait(2);
		// PASO 8
		addStep("Ingresar los parámetros.");
		patternUtil.typeAndTab(juegoDeLibros);
		patternUtil.typeAndTab(entidadLegal);
		patternUtil.typeAndTab(reporte);
		u.hardWait(2);
		patternUtil.doubleClickPattern("EBSInternacionalRegistroDeCompras", 5);
		patternUtil.typeAndTab(periodo);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("XXPELibrosRegistroDeCompras");
		patternUtil.keyAlt("a"); // Aceptar
		// PASO 9
		addStep("Dar clic en Ejecutar.");
		patternUtil.clickPattern("EBSInternacionalEjecutarBtn", 5);
		// PASO 10
		addStep("Seleccionar 'No' en diálogo de Decisión '¿Ejecutar otra solicitud? Si/No'.");
		patternUtil.keyAlt("n"); // Con esto seleccionas 'NO'
		// PASO 11
		addStep("Dirigirse al menú Ver(x) -> Solicitudes -> Encontrar.");
		patternUtil.encontrarSolicitudes();
		patternUtil.clickPattern("EBSInternacionalEncontrarBtn", 5);
		patternUtil.verificarEstado("EBSInternacionalEstadoEjecucionNormalVerde", tiempoDeEjecucion, "r");
		// PASO 12
		addStep("Dar clic en Visualizar Registro.");
		patternUtil.keyAlt("z"); // Visualizar el Registro
		patternUtil.appletMaximize();
		u.hardWait(15);
		patternUtil.clickPattern(LibroRegistrosDeComprasLog, 10);
		patternUtil.selectAll();
		String lFileName = patternUtil.textCopy();
		lFileName = StringUtils.substringAfterLast(lFileName, "l_file_name    : ");
		lFileName = StringUtils.substringBeforeLast(lFileName, ".TXT\n" +
				"Start loop invoice_id ");
		System.out.println(lFileName);
		String[] LE = SplitStringForEveryNChar.splitToNChar(lFileName, 2);
		Assert.assertEquals(LE[0], "LE");
		patternUtil.closeWindow();

		// PASO 13
		addStep("Dar clic en Visualizar Output."); // Para validar esto seria necesario automatizar el como guardar y buscar un archivo de excel
		/*patternUtil.keyAlt("l");
		patternUtil.appletMaximize();
		testCase.addScreenShotCurrentStep("outPut");*/
		patternUtil.closeWindow();
		u.close();
		


	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_ExtraccionLibroRegistroDeCompras_test";
	}

	@Override
	public String setTestDescription() {
		return "Verificar el proceso finalice correctamente mostrando una salida en formato '.TXT' y las columnas estén separadas mediante '|'. Verificar que el Log muestre en el campo 'l_file_name' la nomenclatura sugerida para el nombrado del archivo '.txt'. El usuario debe tener asignada la responsabilidad 'PEOXXO-GLXXXXX-CONSULTA'";
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
		return "El usuario debe tener asignada la responsabilidad 'PEOXXO-GLXXXXX-CONSULTA'";
	}

	@Override
	public void beforeTest() {

	}
}
