package EBS.avante.cho52068366;

import static org.testng.AssertJUnit.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution; import EBS.util.GenericMethods; import EBS.util.patternObject.PatternUtil;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_004_ValidarReportePlazaNoFronterizaVentaTAEInformeVentasAcumuladas extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_004_ValidarReportePlazaNoFronterizaVentaTAEInformeVentasAcumuladas_test(
			HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String ebsPattern = data.get("ebsPattern")+"\\EBS";
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		String nombreEjecutarSolicitud = data.get("nombreEjecutarSolicitud");
		String plaza = data.get("plaza");
		String tienda = data.get("tienda");
		String fechaInicial = data.get("fechaInicial");
		String fechaFinal = data.get("fechaFinal");
		String tipoDeInformeR = data.get("tipoDeInformeR");
		String tipoDeInformeD = data.get("tipoDeInformeD");

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
		GenericMethods action = new GenericMethods(u);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath(ebsPattern);
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
		inicio.login();
		// PASO 5
		addStep("Ingresar a la responsabilidad 'MXOXXO-COXXXXX-ADMINISTRADOR'.");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Ingresar en Reportes: Run.");
		paginaInicial.clickOnCarpeta(carpeta);
		action.genericClick(u.getDriver(), paginaInicial.runCoAdminElement);
		u.hardWait(10);
		patternUtil.runApplet();
		// PASO 7
		addStep("Clic en opcion Solicitud unica, Aceptar.");
		u.hardWait(5);
		patternUtil.solicitudUnica();
		// PASO 8
		addStep("Ingresar en el campo Nombre 'XXCO-Informe de Ventas', TAB.");
		patternUtil.typeAndTab(nombreEjecutarSolicitud);
		// PASO 9
		addStep("Ingresar los parametros: Plaza: " + plaza + " Tienda: " + tienda + " Fecha Inicial: " + fechaInicial
				+ " Fecha Final: " + fechaFinal + " Tipo de Informe: " + tipoDeInformeR + " Aceptar. ");
		patternUtil.keyAlt("l");
		u.hardWait(2);
		patternUtil.typeAndTab(plaza);
		u.hardWait(2);
		patternUtil.typeAndTab(tienda);
		u.hardWait(3);
		patternUtil.keyAlt("a");
		sk.screen.type(fechaInicial);
		u.hardWait(2);
		sk.screen.type(fechaFinal);
		u.hardWait(2);
		sk.screen.type(tipoDeInformeR);
		testCase.addScreenShotCurrentStep("Consulta");
		patternUtil.keyAlt("a");
		// PASO 10
		addStep("Clic en el boton de Ejecutar.");
		patternUtil.clickEjecutar();
		patternUtil.ejecutarOtraSolicitud("No");
		// PASO 11
		addStep("Dirigirse al menu: Ver(x) : 'Solicitudes' : 'Encontrar'.");
		patternUtil.encontrarSolicitudes();
		patternUtil.clickEncontrar();
		// PASO 12
		u.hardWait(5);
		addStep("Clic en Visualizar Output. El calculo del reporte acumulado para la plaza de zona fronteriza es correcto respecto al detallado(16%).");
		patternUtil.keyAlt("l");
		u.hardWait(10);
		patternUtil.keyAlt("o");
		u.hardWait(10);
		patternUtil.focusWindow("MsExcel", 5);
		patternUtil.keyAlt("y");
		u.hardWait(2);
		patternUtil.appletMaximize();
		u.hardWait(2);
		patternUtil.clickPattern("ventasAcumuladasTAE", 3);
		sk.screen.type(Key.DOWN);
		testCase.addScreenShotCurrentStep("tiempoAire");
		String tiempoAire = patternUtil.textCopy();
		tiempoAire = tiempoAire.replace(",", "");
		tiempoAire = tiempoAire.replaceAll("\n", "");
		tiempoAire = tiempoAire.split("\\.")[0];
		logger.info("Total Ventas TAE: " + tiempoAire);
		System.out.println("Total Ventas TAE: " + tiempoAire);
		patternUtil.closeWindow();
		patternUtil.focusWindow("OracleLogo", 5);
		u.hardWait(3);
		patternUtil.clickPattern("ejecutarNuevaSolicitud", 5);
		u.hardWait(3);
		patternUtil.typeAndTab(nombreEjecutarSolicitud);
		patternUtil.keyAlt("l");
		u.hardWait(2);
		patternUtil.typeAndTab(plaza);
		u.hardWait(2);
		patternUtil.typeAndTab(tienda);
		u.hardWait(3);
		patternUtil.keyAlt("a");
		sk.screen.type(fechaInicial);
		u.hardWait(2);
		sk.screen.type(fechaFinal);
		u.hardWait(2);
		sk.screen.type(tipoDeInformeD);
		testCase.addScreenShotCurrentStep("tipoDeInformeD");
		patternUtil.keyAlt("a");
		patternUtil.clickEjecutar();
		patternUtil.ejecutarOtraSolicitud("No");
		u.hardWait(3);
		patternUtil.encontrarSolicitudes();
		patternUtil.clickEncontrar();
		u.hardWait(3);
		patternUtil.keyAlt("l");
		u.hardWait(10);
		patternUtil.keyAlt("o");
		u.hardWait(10);
		patternUtil.focusWindow("MsExcel", 5);
		patternUtil.keyAlt("y");
		u.hardWait(2);
		patternUtil.appletMaximize();
		u.hardWait(2);
		patternUtil.clickPattern("informeDetalladoTotal", 5);
		patternUtil.nTAB(6);
		testCase.addScreenShotCurrentStep("tiempoAireDetallado");
		String tiempoAireDetallado = patternUtil.textCopy();
		tiempoAireDetallado = tiempoAireDetallado.replace(",", "");
		tiempoAireDetallado = tiempoAireDetallado.replaceAll("\n", "");
		tiempoAireDetallado = tiempoAireDetallado.split("\\.")[0];

		logger.info("Total Ventas TAE: " + tiempoAireDetallado);
		assertEquals("Tiempo Aire: ", tiempoAire, tiempoAireDetallado);
		testCase.addTextEvidenceCurrentStep("Tiempo Aire Resumido: " + tiempoAire + "\n");
		testCase.addTextEvidenceCurrentStep("Tiempo Aire Detallado: " + tiempoAireDetallado + "\n");

		patternUtil.closeWindow();
		patternUtil.focusWindow("OracleLogo", 5);
		patternUtil.closeWindow();
		u.hardWait(3);
		patternUtil.ventanaConfirmarSalir("a");
		u.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_ValidarReportePlazaNoFronterizaVentaTAEInformeVentasAcumuladas_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar reporte 'XXCO-Informe de Ventas' realice correctamente el cálculo para venta TAE de plazas de zona no fronteriza (16%). Tener asignada la responsabilidad 'MXOXXO-COXXXXX-ADMINISTRADOR'. Tener un nombre de lote, asi como una Plaza, Tienda, Fecha de Inicio, Fecha Fin y el tipo de Reporte.";
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
		return "Tener asignada la responsabilidad 'MXOXXO-COXXXXX-ADMINISTRADOR'. Tener un nombre de lote, asi como una Plaza, Tienda, Fecha de Inicio, Fecha Fin y el tipo de Reporte.";
	}

	@Override
	public void beforeTest() {

	}
}
