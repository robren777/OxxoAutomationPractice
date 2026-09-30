package EBS.moduloCO.comisionistas;

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
import EBS.util.pageObjectModel.EBSNuevoSistemaICM;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_014_ValidarProcesoTimbradoFacturasAnticiposComisionistasFacturaDeComision extends BaseExecution {
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_014_ValidarProcesoTimbradoFacturasAnticiposComisionistasFacturaDeComision_test(HashMap<String, String> data)
			throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = GlobalVariables.getMduenasHexEbsUsr;
		String password = GlobalVariables.getMduenasHexEbsPwd;
		String responsabilidad = data.get("responsabilidad");
		String nuevo = data.get("carpeta1");
		String ebsPattern = data.get("ebsPattern");
		String comisionista = data.get("comisionista");
		String prestamo = data.get("carpeta2");
		String text = data.get("text");
		String concurrente = data.get("concurrente");
		String responsabilidad2 = data.get("responsabilidad2");
		String fecha = data.get("fecha");
		String descripcion = data.get("descripcion");
		String plantilla = data.get("plantilla");
		String importe = data.get("importe");

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		EBSNuevoSistemaICM nuevoSistema = new EBSNuevoSistemaICM(u,testCase);
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
		 * PASOS DEL CASO DE PRUEBA,  MATRIZ: COMISIONISTAS
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
		addStep("Ingresar a la opción Tienda-> Tienda Comisionista");
		paginaInicial.clickOnCarpeta(nuevo);
		paginaInicial.clickGeneracionDocumentosReversasCO10CHIUsuario();
		testCase.addScreenShotCurrentStep("responsabilidad1");
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("pantallaGDR");
		nuevoSistema.sendNombreComisionista(comisionista);
		testCase.addScreenShotCurrentStep("generarDocumentoDatos");
		nuevoSistema.clickBoton("ir");
		u.hardWait(5);
		nuevoSistema.clickCheckBoxDocumentos();	
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("generarDocumentoDatosCheckBox");
		nuevoSistema.clickBoton("generar documentos");
		testCase.addScreenShotCurrentStep("generarDocumento");
		u.waitForLoadPage();
		nuevoSistema.clickBoton("pagina inicial");
		u.waitForLoadPage();
		paginaInicial.clickOnCarpeta(prestamo);
		paginaInicial.clickRunPrestamosCO10CHIUsuario();
		testCase.addScreenShotCurrentStep("responsabilidad2");
		u.hardWait(15);
		patternUtil.runApplet();
		u.hardWait(20);
		testCase.addScreenShotCurrentStep("pantallaP");
		// PASO 7
		addStep("Capturar los datos necesarios, dar clic en Registrar.");
		patternUtil.enterText(descripcion);
		u.hardWait(3);
		patternUtil.nTAB(1);
		u.hardWait(3);
		patternUtil.enterText(plantilla);
		u.hardWait(3);
		patternUtil.nTAB(5);
		u.hardWait(3);
		patternUtil.enterText(comisionista);
		u.hardWait(3);
		patternUtil.nTAB(3);
		u.hardWait(3);
		patternUtil.enterText(importe);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("prestamoDatos");
		patternUtil.keyAlt("r");
		testCase.addScreenShotCurrentStep("prestamoDatosMsj");
		patternUtil.keyAlt("a");
		u.hardWait(3);
		// PASO 8
		addStep("Validar se genere la factura de AP. Dirigirse al menú Ver(x) -> Solicitudes ->  Encontrar.");
		patternUtil.encontrarSolicitudes();
		testCase.addScreenShotCurrentStep("coEncontraSolicitud");
		patternUtil.clickEncontrar();
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("coSolicitud");
		// PASO 9
		addStep("Validar y contabilizar la factura generada");
		patternUtil.clickVisualizarOutput();
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("coVisualizarOutput");
		patternUtil.closeWindow();
		assertEquals(p.getText(text), text);
		u.hardWait(3);
		//testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		u.hardWait(3);
		// PASO 10
		addStep("Ejecutar el proceso 'XXCO-Proceso de timbrado de facturas de anticipos de comisionistas'.");
		patternUtil.closeWindowInsideApplet();
		u.hardWait(1);
		patternUtil.closeWindowInsideApplet();
		u.hardWait(1);
		patternUtil.clickImage("cambiarResponsabilidad");
		u.hardWait(3);
		/*patternUtil.clickImage("buscarPorcentaje");
		u.hardWait(5);
		patternUtil.enterText(responsabilidad2);
		u.hardWait(3);
		patternUtil.keyAlt("b");*/
		patternUtil.nDOWN(6);
		testCase.addScreenShotCurrentStep("coCambioResponsabilidad");
		patternUtil.nEnter(1);
		u.hardWait(3);
		patternUtil.encontrarSolicitudes();
		testCase.addScreenShotCurrentStep("coNuevaSolicitud");
		u.hardWait(3);
		patternUtil.keyAlt("n");
		u.hardWait(3);
		patternUtil.typeAndTab(concurrente);
		u.hardWait(5);
		patternUtil.enterText(fecha);
		patternUtil.nTAB(1);
		patternUtil.enterText(fecha);
		//assertEquals(patternUtil.getTextDelNavegador("Concurrente", text), text);
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		// PASO 10
		addStep("Ejecutar el proceso 'XXCO-Proceso de timbrado de facturas de anticipos de comisionistas'.");
		patternUtil.clickOracle();
		patternUtil.keyAlt("j");
		u.hardWait(3);
		patternUtil.typeAndTab(concurrente);
		u.hardWait(5);
		patternUtil.keyAlt("a");
		testCase.addScreenShotCurrentStep("coNuevaSolicitudDatos");
		addStep("Clic en el boton de Ejecutar.");
		patternUtil.clickEjecutar();
		testCase.addScreenShotCurrentStep("coNuevaSolicitudEjecutar");
		u.hardWait(3);
		patternUtil.ejecutarOtraSolicitud("No");
		testCase.addScreenShotCurrentStep("coNuevaSolicitudNo");
		u.hardWait(10);
		patternUtil.encontrarSolicitudes();	
		u.hardWait(2);
		patternUtil.keyAlt("t");
		testCase.addScreenShotCurrentStep("coEncontraNuevaSolicitud");
		patternUtil.clickEncontrar();
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("coNuevaSolicitudResultado");
		u.hardWait(3);		
		patternUtil.closeWindow();
		u.hardWait(5);
		patternUtil.ventanaConfirmarSalir("a");
		u.close();
		
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ValidarConcurrenteXXFCPaso1_test";
	}

	@Override
	public String setTestDescription() {
		return "Descripcion y Prerequisitos";
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
		return "Validar que exista el concurrente 'XXFC - Preparar Reservas Inventario Fisico (Paso 1)' dentro de la responsabilidad: 'MXOXXO-WM17FAA-COORDINADOR EQP FRM' Acceso a la responsabilidad: 'MXOXXO-WM17FAA-COORDINADOR EQP FRM' ";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}