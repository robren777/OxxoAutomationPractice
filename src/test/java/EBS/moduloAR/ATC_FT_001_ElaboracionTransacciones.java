package EBS.moduloAR;

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

public class ATC_FT_001_ElaboracionTransacciones extends BaseExecution {
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_001_ElaboracionTransacciones_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = GlobalVariables.getMduenasHexEbsUsr;
		String password = GlobalVariables.getMduenasHexEbsPwd;
        String ebsPattern = data.get("ebsPattern");
        String responsabilidad = data.get("responsabilidad");
        String transacciones = data.get("carpeta1");
        String origen = data.get("origen");
        String clase = data.get("clase");
        String tipo = data.get("tipo");
        String facturacion = data.get("facturacion");
        String terminoPago = data.get("terminoPago");
        String vendedor = data.get("vendedor");
        String formaPago = data.get("formaPago");
        String descripcion = data.get("descripcion");
        String udm = data.get("udm");
        String cantidad = data.get("cantidad");
        String precioUnitario = data.get("precioUnitario");
        String clasificacionImp = data.get("clasificacionImp");
        String impuesto = data.get("impuesto");
        String ingresos = data.get("ingresos");
   
		
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
		addStep("Ingresar responsabilidad MXOXXO-ARXXXXX-Super Usuario--> Transacciones --> Transacciones");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		paginaInicial.clickOnCarpeta(transacciones);
		paginaInicial.clickRunTransaccionesARSuperUsuario();
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("runApplet");
		patternUtil.runApplet2();
		u.hardWait(63);
		testCase.addScreenShotCurrentStep("runApplet");
		// PASO 3
		addStep("Seleccionar los datos de Transaccion….Origen: OXXO_10LIM_FAC-MAN Clase: Factura Tipo: FAC_ACU-COMERC-10LIM");
		patternUtil.typeAndTab(origen);
		u.hardWait(3);
		patternUtil.nTAB(2);
		patternUtil.enterText(clase);
		u.hardWait(3);
		patternUtil.nTAB(2);
		patternUtil.typeAndTab(tipo);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("datosTransaccion");
		// PASO 4
		addStep("Ingresar datos de Facturación AC COMERCIAL DEL PERU S.A.C. Termino Pago: CONTADO");
		patternUtil.clickImage("facturacion");
		u.hardWait(3);
		patternUtil.typeAndTab(facturacion);
		u.hardWait(3);
		patternUtil.clickImage("terminoPagoMx");
		u.hardWait(8);
		assertEquals(terminoPago, patternUtil.textCopy());
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("datosFacturacion");
		// PASO 5 
		addStep("Seleccionar la pestaña 'Más'");
		patternUtil.clickImage("masMx");
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("mas");
		patternUtil.clickImage("vendedorMx");
		u.hardWait(8);
		assertEquals(vendedor, patternUtil.textCopy());
		u.hardWait(3);
		patternUtil.clickImage("infoTransaccion");
		u.hardWait(3);
		patternUtil.enterText(formaPago);
		patternUtil.keyAlt("a");
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("mas2");
		// PASO 6 
		addStep("Hacemos clic en el botón 'Artículos Línea' ");
		patternUtil.keyAlt("l");
		u.hardWait(3);
		patternUtil.nTAB(2);
		patternUtil.enterText(descripcion);
		patternUtil.nTAB(1);
		patternUtil.enterText(udm);
		patternUtil.nTAB(1);
		patternUtil.enterText(cantidad);
		patternUtil.nTAB(1);
		patternUtil.enterText(precioUnitario);
		patternUtil.nTAB(2);
		patternUtil.enterText(clasificacionImp);
		u.hardWait(3);
		testCase.addTextEvidenceCurrentStep("Líneas");
		testCase.addScreenShotCurrentStep("Lineas");
		u.hardWait(3);
		patternUtil.closeWindowInsideApplet();
		u.hardWait(3);
		// PASO 7
		addStep("Hacemos clic en el botón 'Distribuciones' con la finalidad de revisar las cuentas contables que se afectarán");
		patternUtil.keyAlt("s");
		u.hardWait(3);
		testCase.addTextEvidenceCurrentStep("Distribuciones");
		testCase.addScreenShotCurrentStep("Distribuciones");
		patternUtil.nTAB(6);
		patternUtil.enterText(impuesto);
		patternUtil.nTAB(6);
		patternUtil.enterText(ingresos);
		testCase.addScreenShotCurrentStep("Distribuciones");
		patternUtil.clickImage("botonGrabar");
		testCase.addScreenShotCurrentStep("botonGrabar");
		u.hardWait(3);
		patternUtil.closeWindowInsideApplet();
		testCase.addScreenShotCurrentStep("closeWindowInsideApplet");
		u.hardWait(3);
		// PASO 8
		addStep("Por último, hacemos clic en el botón 'Finalizar' ");
		patternUtil.keyAlt("i");
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("i");
		patternUtil.keyAlt("a");
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("a");
		patternUtil.closeWindow();
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("closeWindow");
		patternUtil.ventanaConfirmarSalir("a");
		testCase.addScreenShotCurrentStep("a");
		u.close();
		
	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Elaboración de Transacciones";
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
		return "Elaboración de Transacciones";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}