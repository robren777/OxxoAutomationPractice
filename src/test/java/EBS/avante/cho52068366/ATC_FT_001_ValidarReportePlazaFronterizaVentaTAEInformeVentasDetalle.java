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

public class ATC_FT_001_ValidarReportePlazaFronterizaVentaTAEInformeVentasDetalle extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_001_ValidarReportePlazaFronterizaVentaTAEInformeVentasDetalle_test(HashMap<String, String> data)
			throws Exception {
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
		String tipoDeInforme = data.get("tipoDeInforme");
		String responsabilidadGL = data.get("responsabilidadGL");
		String origen = data.get("origen");
		String periodo = data.get("periodo");
		String loteVenta = data.get("loteVenta");
		String loteDevoluciones = data.get("loteDevoluciones");

		HashMap<String, Pattern> ImgPattern = new HashMap<String, Pattern>();
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
		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPattern, patterns, Folder);

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
				+ " Fecha Final: " + fechaFinal + " Tipo de Informe: " + tipoDeInforme + " Aceptar.");
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
		sk.screen.type(tipoDeInforme);
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
		addStep("Clic en Visualizar Output.");
		patternUtil.keyAlt("l");
		u.hardWait(10);
		patternUtil.keyAlt("o");
		u.hardWait(10);
		patternUtil.focusWindow("MsExcel", 5);
		patternUtil.keyAlt("y");
		u.hardWait(2);
		patternUtil.appletMaximize();
		patternUtil.clickPattern("VentasTAE", 3);
		sk.screen.type(Key.DOWN);
		testCase.addScreenShotCurrentStep("Excel");
		String tiempoAire = patternUtil.textCopy();
		tiempoAire = tiempoAire.replace(",", "");
		tiempoAire = tiempoAire.replaceAll("\n", "");
		patternUtil.closeWindow();
		patternUtil.focusWindow("OracleLogo", 5);
		// PASO 13
		addStep("Validar la informacion del dia " + fechaInicial + " en GL.");
		patternUtil.clickPattern("mostrarNavegador", 3);
		patternUtil.clickPattern("cambiarResponsabilidad", 3);
		patternUtil.shiftTab();
		sk.screen.type(responsabilidadGL);
		patternUtil.keyAlt("b");
		patternUtil.doubleClickPattern("asientosGL", 5);
		u.hardWait(3);
		patternUtil.doubleClickPattern("enterGL", 5);
		u.hardWait(15);
		patternUtil.typeAndTab(loteVenta);
		u.hardWait(1);
		patternUtil.textBoxTypeRight("origenGL", 80, origen);
		u.hardWait(2);
		patternUtil.typeAndTab(periodo);
		testCase.addScreenShotCurrentStep("Asiento");
		u.hardWait(2);
		patternUtil.keyAlt("c");
		u.hardWait(10);
		patternUtil.clickPattern("selectAzul", 5);
		u.hardWait(3);
		patternUtil.clickPattern("clientesTerceros", 3);
		patternUtil.shiftTab();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep("Debito");
		String debito = patternUtil.textCopy();
		patternUtil.closeWindowInsideApplet();
		u.hardWait(3);
		patternUtil.f11();
		u.hardWait(5);
		patternUtil.textBoxTypeBelow("nombreLote", 30, loteDevoluciones);
		testCase.addScreenShotCurrentStep("Nombre de lote");
		patternUtil.ctrlF11();
		u.hardWait(10);
		patternUtil.clickPattern("selectAzul", 5);
		u.hardWait(3);
		patternUtil.clickPattern("clientesTerceros", 3);
		patternUtil.shiftTab();
		u.hardWait(1);
		String credito = patternUtil.textCopy();
		testCase.addScreenShotCurrentStep("Credito");
		patternUtil.closeWindow();
		patternUtil.ventanaConfirmarSalir("a");
		u.close();

		// PASO 14
		addStep("Validar Calculo del Reporte contra GL.");

		double creditoDouble = Double.parseDouble(credito);
		System.out.println("Credito " + creditoDouble);
		testCase.addTextEvidenceCurrentStep("Credito " + creditoDouble);
		double debitoDouble = Double.parseDouble(debito);
		System.out.println("Debito " + debitoDouble);
		testCase.addTextEvidenceCurrentStep("Debito " + debitoDouble);

		double devoluciones = creditoDouble / 1.08;
		System.out.println("Base Credito " + devoluciones);
		testCase.addTextEvidenceCurrentStep("Base Credito " + devoluciones);
		double venta = debitoDouble / 1.08;
		System.out.println("Base Debito " + venta);
		testCase.addTextEvidenceCurrentStep("Base Debito " + venta);
		double total = venta - devoluciones;
		String totalString = String.format("%.2f", total);
		System.out.println("Total " + totalString);
		testCase.addTextEvidenceCurrentStep("Total " + totalString);
		assertEquals(tiempoAire, totalString);

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ValidarReportePlazaFronterizaVentaTAEInformeVentasDetalle_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar reporte 'XXCO-Informe de Ventas' realice correctamente el cálculo para venta TAE de plazas de zona fronteriza (8%). Tener asignada la responsabilidad 'MXOXXO-COXXXXX-ADMINISTRADOR'. Tener un nombre de lote, asi como una Plaza, Tienda, Fecha de Inicio, Fecha Fin y el tipo de Reporte.";
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
