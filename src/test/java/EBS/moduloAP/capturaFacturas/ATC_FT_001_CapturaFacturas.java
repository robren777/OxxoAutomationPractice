package EBS.moduloAP.capturaFacturas;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GenericMethods;
import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_CapturaFacturas extends BaseExecution {
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_001_CapturaFacturas_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
        String userName = GlobalVariables.getMduenasHexEbsUsr;
        String password = GlobalVariables.getMduenasHexEbsPwd;
		String responsabilidad = data.get("responsabilidad");
		String ebsPattern = data.get("ebsPattern");
		String facturas = ("Facturas");//data.get("carpeta1");
		String ingreso = data.get("carpeta2");
		String tipo = data.get("tipo");
		String socioComercial = data.get("socioComercial");
		String noProveedor = data.get("noProveedor");
		String nombreSucursal = data.get("nombreSucursal");
		String fechaFactura = data.get("fechaFactura");
		String noFactura = data.get("noFactura");
		String importeFactura = data.get("importeFactura");
		String terminos = data.get("terminos");
		String metodoPago = data.get("metodoPago");
		String grupoPago = data.get("grupoPago");
		String estado = data.get("estado");
		String importe = data.get("importe");
		String cuentaDistribucion = data.get("cuentaDistribucion");
		String codigoImpuesto = data.get("codigoImpuesto");

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
		GenericMethods action = new GenericMethods(u);

		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);

		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: CHO52068895
		 */

		// PASO 1
		addStep("Ingresar al ambiente de EBS.");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		// PASO 2
		addStep("Seleccionar idioma: Espanol Latinoamericano.");
		// PASO 3
		addStep("Ingresar usuario y contrasena.");
		// PASO 4
		addStep("Dar clic en el boton de 'Conectese'.");
		inicio.login();
		// PASO 5
		addStep("Ingresar a la responsabilidad 'MXOXXO-AP10TIJ-USUARIO'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		testCase.addScreenShotCurrentStep("seleccionarResponsabilidad");
		// PASO 6
		addStep("Seleccionar: Facturas -> Ingreso -> Facturas");
		
		paginaInicial.clickOnCarpeta(facturas);
		u.hardWait(1);
		paginaInicial.clickOnCarpeta(ingreso);
		u.hardWait(1);
		paginaInicial.clickRunFacturasAP10TijUsuario();
		testCase.addScreenShotCurrentStep("seleccionarFactura");
		u.hardWait(90);
		patternUtil.runApplet();
		u.hardWait(40);
		testCase.addScreenShotCurrentStep("pantallaFactura");
		// PASO 7
		addStep("Tipo de factura");
		u.hardWait(5);	
		patternUtil.nTAB(1);//se cambio a 3 para que en la mv lo recorra uno mas
		u.hardWait(8);
		assertEquals(tipo, patternUtil.textCopy());
		u.hardWait(3);
		patternUtil.nTAB(2);
		u.hardWait(3);
		patternUtil.enterText(socioComercial);
		u.hardWait(3);
		patternUtil.nTAB(1);
		u.hardWait(3);
		assertEquals(noProveedor, patternUtil.textCopy());
		u.hardWait(3);
		patternUtil.nTAB(1);
		u.hardWait(3);
		patternUtil.enterText(nombreSucursal);
		u.hardWait(3);
		patternUtil.nTAB(1);
		u.hardWait(3);
		patternUtil.enterText(fechaFactura);
		u.hardWait(3);
		patternUtil.nTAB(1);
		u.hardWait(3);
		patternUtil.enterText(noFactura);
		u.hardWait(3);
		patternUtil.nTAB(2);
		u.hardWait(3);
		patternUtil.enterText(importeFactura);
		 testCase.addScreenShotCurrentStep("facturaDatos");
		// PASO 8 
		addStep("Se verifican los Términos de Pago, Método y Grupo de pago asignados al Proveedor");
		u.hardWait(3);
		patternUtil.nTAB(13);
		u.hardWait(13);
		assertEquals(terminos, patternUtil.textCopy());
		u.hardWait(3);
		patternUtil.nTAB(1);
		u.hardWait(3);
		assertEquals(metodoPago, patternUtil.textCopy());
		u.hardWait(3);
		patternUtil.nTAB(1);
		u.hardWait(3);
		assertEquals(grupoPago, patternUtil.textCopy());
		// PASO 9
		addStep("Se verifica la Cuenta Bancaria asignada al Proveedor ");
		u.hardWait(3);
		patternUtil.nTAB(15);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("proveedor");
		testCase.addTextEvidenceCurrentStep("Cuenta Bancaria asignada al Proveedor");
		u.hardWait(3);
		
		// PASO 10
		addStep("Detalle de la factura 'General' ");
		patternUtil.clickImage("estadoNoValidado");
		u.hardWait(8);
		assertEquals(estado, patternUtil.textCopy());
		u.hardWait(3);
		testCase.addTextEvidenceCurrentStep("Estatus de Pago y de Contabilización");
		u.hardWait(3);
		 testCase.addScreenShotCurrentStep("estatusPago");
		// PASO 11
		addStep("Detalle de la factura 'Líneas' ");
		patternUtil.keyAlt("2");
		u.hardWait(3);
		testCase.addTextEvidenceCurrentStep("Visualizar Pagos");
        u.hardWait(3);
        testCase.addScreenShotCurrentStep("pagos");
		// PASO 12
		addStep("Detalle de la factura 'Retenciones' ");
		patternUtil.keyAlt("3");
		testCase.addTextEvidenceCurrentStep("Retenciones");
		u.hardWait(3);
		 testCase.addScreenShotCurrentStep("retenciones");
		// PASO 13
		addStep("Detalle de la factura 'Visualizar Pagos' ");
		patternUtil.keyAlt("4");
		testCase.addTextEvidenceCurrentStep("Visualizar Pagos");
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("visualizarPagos");
		// PASO 14
		addStep("Detalle de la factura 'Pagos Programados' ");
		patternUtil.keyAlt("5");
		testCase.addTextEvidenceCurrentStep("Pagos Programados");
		u.hardWait(3);
		 testCase.addScreenShotCurrentStep("pagoProg");
		// PASO 15
		addStep("Detalle de la factura 'Anticipo' ");
		patternUtil.keyAlt("6");
		testCase.addTextEvidenceCurrentStep("Anticipo");
		u.hardWait(3);
		 testCase.addScreenShotCurrentStep("anticipo");
		patternUtil.closeWindow();
        patternUtil.ventanaConfirmarSalir("a");
        u.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_CapturaFacturas_test";
	}

	@Override
	public String setTestDescription() {
		return "Captura de factura";
	}

	@Override
	public String setTestDesigner() {
		return "Automation QA";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}