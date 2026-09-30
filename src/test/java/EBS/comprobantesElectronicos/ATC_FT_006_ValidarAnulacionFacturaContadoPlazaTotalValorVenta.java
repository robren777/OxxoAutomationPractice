package EBS.comprobantesElectronicos;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.ebsInternational.EBSPaginaInicialInternacional;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_006_ValidarAnulacionFacturaContadoPlazaTotalValorVenta extends BaseExecution {
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_006_ValidarAnulacionFacturaContadoPlazaTotalValorVenta_test(HashMap<String, String> data)
			throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = GlobalVariables.getEbsInternacional;
		String userName = GlobalVariables.getJreyesHexEbsIntUsr;
		String password = GlobalVariables.getJreyesHexEbsIntPwd;
		String ebsPattern = data.get("ebsPattern");
		String responsabilidad = data.get("responsabilidad");
		String transacciones = data.get("carpeta1");
        String clase = data.get("clase");
        String tipo = data.get("tipo");
        String transaccionDesde = data.get("transaccionDesde");
        String transaccionHasta = data.get("transaccionHasta");
        String nombre2 = data.get("nombre2");
        String url = data.get("url");
		String motivo = data.get("motivo");
		String linea = data.get("linea");
		

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
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: COMPROBANTES ELECTRÓNICOS
		 */

		// PASO 1
		addStep("Ingresar EBS Peru");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		inicio.login();
		// PASO 2
		addStep("Ingresar responsabilidad PEOXXO-AR10LIM-TRANSACCIONES--> Transacciones");
		paginaInicial.clicEnResponsabilidad(responsabilidad);
		paginaInicial.clicEnCarpeta(transacciones);
		paginaInicial.clickRunTransaccionesAR10LIM();
		u.hardWait(5);
		patternUtil.runAppletEbsInternacional();
		u.hardWait(10);
		// PASO 3
		addStep("Buscar factura del ejercicio anterior (CONTADO) Numero: #Factura ctrl+F11");
		patternUtil.f11();
		u.hardWait(3);
		patternUtil.nTAB(2);
		patternUtil.enterText(transaccionDesde);
		patternUtil.ctrlF11();
		u.hardWait(3);
		// PASO 4
		addStep("Ir en menú Acciones-->Crédito");
		patternUtil.acciones();
		patternUtil.keyAlt("r");
		u.hardWait(3);
		// PASO 5
		addStep("Ingresar datos Motivo: XXPE-ERROR EN IMPORTES (se puede seleccionar otro según sea) Asignación Crédito: Lineas e Impuesto (siempre elegir este para anular en su totalidad) Linea: 100%");
		patternUtil.nTAB(4);
		u.hardWait(3);
		patternUtil.keyAlt("a");
		patternUtil.enterText(motivo);
		patternUtil.clickImage("linea");
		patternUtil.enterText(linea);
		u.hardWait(3);
		// PASO 6
		addStep("Clic Finalizar. Se muestra nuevo Numero (NC)");
		patternUtil.keyAlt("f");
		// PASO 7
		addStep("Timbrar documento en Sunat Concurrente: XXPE - AR Facturacion y/o Nota de Credito Digital Clase Transacción: Factura Tipo Transacción: FAC_ACU-COMERC-10LIM Trans Inferior/Superior: #factura Visualizar OutPut");
		u.hardWait(3);
		patternUtil.typeAndTab(nombre2);
		u.hardWait(3);
		patternUtil.nTAB(1);
		patternUtil.enterText(clase);
		u.hardWait(3);
		patternUtil.nTAB(1);
		patternUtil.enterText(tipo);
		u.hardWait(3);
		patternUtil.nTAB(2);
		patternUtil.enterText(transaccionDesde);
		u.hardWait(3);
		patternUtil.nTAB(1);
		patternUtil.enterText(transaccionHasta);
		u.hardWait(3);
		patternUtil.keyAlt("a");
		u.hardWait(3);
		addStep("¿Ejecutar Otra Solicitud? No.");
		patternUtil.ejecutarOtraSolicitud("No");
		u.hardWait(3);
		addStep("Dirigirse al menu: Ver(x) -> 'Solicitudes' -> 'Encontrar'.");
		patternUtil.encontrarSolicitudes();
		patternUtil.clickEncontrar();
		u.hardWait(3);
		addStep("Dar clic en Visualizar Output");
		patternUtil.clickVisualizarOutput();
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("Factura");
		patternUtil.closeWindow();
		u.hardWait(3);
		patternUtil.closeWindow();
		u.hardWait(5);
		patternUtil.ventanaConfirmarSalir("a");
		u.close();
		// PASO 8
		addStep("Ir a portal Acepta -->Home En Busqueda Razón Social -->Factura -->Emitidos (clic en el numero de facturas) Ingresar #Serie-->Buscar");
		logger.info("EBS URL: " + url);
		u.waitForLoadPage();
		testCase.addScreenShotCurrentStep("Factura");
		// PASO 9
		addStep("Dar clic en Visualizar OutputEn pantalla de Factura Clic botón XML");
		paginaInicial.clickVistaXML();
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("Vista XML");
		u.close();
	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Se anula factura (CREDITO) por medio de una Nota de Credito (Accion) y en XML tags LineExtensionAmount, TaxInclusiveAmount";
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
		return "";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}