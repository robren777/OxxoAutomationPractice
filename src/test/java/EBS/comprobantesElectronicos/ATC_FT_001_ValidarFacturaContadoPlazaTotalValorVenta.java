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

public class ATC_FT_001_ValidarFacturaContadoPlazaTotalValorVenta extends BaseExecution {
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_001_ValidarFacturaContadoPlazaTotalValorVenta_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = GlobalVariables.getEbsInternacional;
        String userName = GlobalVariables.getJreyesHexEbsIntUsr;
        String password = GlobalVariables.getJreyesHexEbsIntPwd;
        String ebsPattern = data.get("ebsPattern");
        String responsabilidad = data.get("responsabilidad");
        String transacciones = data.get("carpeta1");
        String origen = data.get("origen");
        String clase = data.get("clase");
        String tipo = data.get("tipo");
        String facturacion = data.get("facturacion");
        String terminoPago = data.get("terminoPago");
        String descripcion = data.get("descripcion");
        String udm = data.get("udm");
        String cantidad = data.get("cantidad");
        String precioUnitario = data.get("precioUnitario");
        String clasificacionImp = data.get("clasificacionImp");
        String nombre = data.get("nombre");
        String nombrePeriodo = data.get("nombrePerido");
        String transaccionDesde = data.get("transaccionDesde");
        String transaccionHasta = data.get("transaccionHasta");
        String nombre2 = data.get("nombre2");
        String url = data.get("url");
		
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
		addStep("Seleccionar los datos de Transaccion….Origen: OXXO_10LIM_FAC-MAN Clase: Factura Tipo: FAC_ACU-COMERC-10LIM");
		patternUtil.typeAndTab(origen);
		u.hardWait(3);
		patternUtil.keyAlt("a");
		u.hardWait(3);
		patternUtil.nTAB(2);
		patternUtil.enterText(clase);
		u.hardWait(3);
		patternUtil.nTAB(2);
		patternUtil.typeAndTab(tipo);
		u.hardWait(3);
		// PASO 4
		addStep("Ingresar datos de Facturación AC COMERCIAL DEL PERU S.A.C. Termino Pago: CONTADO");
		patternUtil.clickImage("facturacion");
		u.hardWait(3);
		patternUtil.typeAndTab(facturacion);
		u.hardWait(3);
		patternUtil.clickImage("terminoPago");
		u.hardWait(3);
		patternUtil.typeAndTab(terminoPago);
		u.hardWait(3);
		// PASO 5 
		addStep("Ingresar Line Items (en Descripcion, ademas del concepto, agregar comentario, y validar que se muestre en PDF)");
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
		patternUtil.closeWindowInsideApplet();
		u.hardWait(3);
		patternUtil.keyAlt("i");
		// PASO 6
		addStep("Generar correlativo Ir a LAD ADD-On Peru--> Informes Concurrente: Proceso de Numeración de Transacción Num Transaccion Desde/Hasta: (num Factura) Ejecutar -> Visualizar OutPut (Se obtiene #Sunat)");
		u.hardWait(3);
		patternUtil.nDOWN(2);
		patternUtil.clickAbrir();
		patternUtil.nDOWN(1);
		patternUtil.clickAbrir();
		u.hardWait(3);
		patternUtil.keyAlt("a");
		u.hardWait(3);
		patternUtil.typeAndTab(nombre);
		u.hardWait(3);
		patternUtil.enterText(nombrePeriodo);
		patternUtil.nTAB(1);
		patternUtil.enterText(transaccionDesde);
		patternUtil.nTAB(1);
		patternUtil.enterText(transaccionHasta);
		u.hardWait(3);
		patternUtil.keyAlt("a");
		u.hardWait(3);
		patternUtil.keyAlt("j");
		u.hardWait(3);
		addStep("¿Ejecutar Otra Solicitud? Si.");
		patternUtil.ejecutarOtraSolicitud("Si");
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
		return "Se valida factura de Contado de FAC_ACU-COMERC-10LIM y tags LineExtensionAmount, TaxInclusiveAmount Responsabilidad Plaza, 10LIM FAC_ACU-COMERC-10LIM Validacion PDF, XML (tablas y portal Acepta), solo en XML se muestra LineExtensionAmount, TaxInclusiveAmount";
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
		return "Responsabilidad Plaza, 10LIM FAC_ACU-COMERC-10LIM Validacion PDF, XML (tablas y portal Acepta), solo en XML se muestra LineExtensionAmount, TaxInclusiveAmount";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}