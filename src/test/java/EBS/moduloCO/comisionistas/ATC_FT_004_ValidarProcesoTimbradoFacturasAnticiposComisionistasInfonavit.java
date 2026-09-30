package EBS.moduloCO.comisionistas;

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

public class ATC_FT_004_ValidarProcesoTimbradoFacturasAnticiposComisionistasInfonavit extends BaseExecution {
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_004_ValidarProcesoTimbradoFacturasAnticiposComisionistasInfonavit_test(HashMap<String, String> data)
			throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = GlobalVariables.getMduenasHexEbsUsr;
		String password = GlobalVariables.getMduenasHexEbsPwd;
		String responsabilidad = data.get("responsabilidad");
		String reportes = data.get("carpeta1");
		String ebsPattern = data.get("ebsPattern");
		String comisionista = data.get("comisionista");
		String descripcion = data.get("descripcion");
		String plantilla = data.get("plantilla");
		String importe = data.get("importe");
		String text = data.get("text");
		String concurrente = data.get("concurrente");
		String fechaCaptura = "01-SEP-23";//data.get("fechaCaptura");
		String proveedor = data.get("proveedor");
		String responsabilidad2 = data.get("responsabilidad2");
		String fecha = "01-SEP-23";//data.get("fecha");

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
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: COMISIONISTAS
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
		addStep("Ingresar a la responsabilidad 'MXOXXO-CO10TMP-USUARIO'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Ingresar a la opcion Tienda-> Tienda Comisionista");
		paginaInicial.clickOnCarpeta(reportes);
		paginaInicial.clickRunGeneracionPagosCuentaComisionistas();
		testCase.addScreenShotCurrentStep("responsabilidad");
		u.hardWait(10);
		patternUtil.runApplet();
		u.hardWait(25);
		testCase.addScreenShotCurrentStep("pantallaPCC");
		// PASO 7
		addStep("Capturar los datos necesarios, dar clic en Registrar.");
		patternUtil.nTAB(1);
		patternUtil.enterText(descripcion);
		u.hardWait(3);
		patternUtil.nTAB(1);
		u.hardWait(3);
		patternUtil.enterText(plantilla);
		u.hardWait(3);
		patternUtil.nTAB(1);
		u.hardWait(3);
		patternUtil.enterText(fechaCaptura);
		u.hardWait(3);
		patternUtil.nTAB(1);
		u.hardWait(3);
		patternUtil.enterText(proveedor);
		u.hardWait(3);
		patternUtil.clickImage("comisionista");
		u.hardWait(3);
		patternUtil.enterText(comisionista);
		u.hardWait(3);
		patternUtil.nTAB(1);
		u.hardWait(3);
		patternUtil.enterText(importe);
		u.hardWait(3);
		patternUtil.nTAB(1);
		u.hardWait(3);
		patternUtil.clickImage("guardar");
		testCase.addScreenShotCurrentStep("pagosDatos");
		u.hardWait(3);
		patternUtil.keyAlt("r");
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("pagosExitoso");
		patternUtil.keyAlt("a");
		u.hardWait(3);
		// PASO 8
		addStep("Validar se genere la factura de AP. Dirigirse al menu Ver(x) -> Solicitudes ->  Encontrar.");
		patternUtil.encontrarSolicitudes();
		testCase.addScreenShotCurrentStep("coEncontraSolicitud");
		u.hardWait(3);
		patternUtil.clickEncontrar();
		testCase.addScreenShotCurrentStep("coSolicitud");
		u.hardWait(3);
		// PASO 9
		addStep("Validar y contabilizar la factura generada");
		patternUtil.clickVisualizarOutput();
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("coVisualizarOutput");
		//assertEquals(p.getText(text), text);
        patternUtil.closeWindow();
        u.hardWait(3);
    	//testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		u.hardWait(5);
		// PASO 10
		addStep("Ejecutar el proceso 'XXCO-Proceso de timbrado de facturas de anticipos de comisionistas'.");
		patternUtil.closeWindowInsideApplet();
		u.hardWait(1);
		patternUtil.closeWindowInsideApplet();
		u.hardWait(1);
		patternUtil.clickImage("cambiarResponsabilidad");
		u.hardWait(3);
		/*patternUtil.clickImage("buscarPorcentaje");
		u.hardWait(3);
		patternUtil.enterText(responsabilidad2);
		testCase.addScreenShotCurrentStep("coCambiarResponsabilidad");
		u.hardWait(3);
		patternUtil.keyAlt("b");*/		
		patternUtil.nDOWN(6);
		patternUtil.nEnter(1);
		u.hardWait(3);
		patternUtil.encontrarSolicitudes();
		testCase.addScreenShotCurrentStep("coNuevaSolicitud");
		u.hardWait(3);
		patternUtil.keyAlt("n");
		u.hardWait(5);
		patternUtil.typeAndTab(concurrente);
		u.hardWait(5);
		patternUtil.enterText(fecha);
		patternUtil.nTAB(1);
		patternUtil.enterText(fecha);
		patternUtil.nTAB(2);
		testCase.addScreenShotCurrentStep("coNuevaSolicitudDatos");
		patternUtil.keyAlt("a");
		addStep("Clic en el boton de Ejecutar.");
		patternUtil.clickEjecutar();
		testCase.addScreenShotCurrentStep("coNuevaSolicitudEjecutar");
		u.hardWait(10);
		patternUtil.ejecutarOtraSolicitud("No");
		testCase.addScreenShotCurrentStep("coNuevaSolicitudNo");
		u.hardWait(15);
		patternUtil.encontrarSolicitudes();	
		u.hardWait(3);
		patternUtil.keyAlt("t");
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("coEncontraNuevaSolicitud");
		patternUtil.clickEncontrar();
		testCase.addScreenShotCurrentStep("coNuevaSolicitudResultado");
		u.hardWait(3);
		/*patternUtil.closeWindowInsideApplet();
		u.hardWait(5);
		patternUtil.closeWindowInsideApplet();
		u.hardWait(5);
		patternUtil.closeWindow();
		u.hardWait(5);
		patternUtil.ventanaConfirmarSalir("a");
		u.close();*/
		
	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Verificar muestre información la siguiente información en tablas: Tablas XXCO_CFD_FACTDIG_ENC, XXCO_CFD_FACDIG_ENC_TMP REC_CODIGO_POSTAL (Valor  64480) REC_REGIMEN_FISCAL  (Valor 623) EXPORTACION  (Valor 01) Tablas XXCO_CFD_FACTDIG_LIN, XXCO_CFD_FACDIG_LIN_TMP OBJETO_IMPUESTOS  (Valor 02)";
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
		return "1.- Acceso a responsabilidad 'MXOXXO-CO10TMP-Usuario', 'MXOXXO-COXXXXX-Administrado'. 2.-  Solicitado update en Anticipos existentes ";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}