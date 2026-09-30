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
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_015_ValidarRazonSocialCadenaComercialOXXO extends BaseExecution {
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_015_ValidarRazonSocialCadenaComercialOXXO_test(HashMap<String, String> data)
			throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = GlobalVariables.getMduenasHexEbsUsr;
		String password = GlobalVariables.getMduenasHexEbsPwd;
		String responsabilidad = data.get("responsabilidad");
		String configuracion = data.get("carpeta1");
		String consultas = data.get("carpeta2");
		String ebsPattern = data.get("ebsPattern");
		String tipo = data.get("tipo");
		String text = data.get("text");
		String concurrente = data.get("concurrente");
		String buscar = data.get("buscar");
		String comisionista = data.get("comisionista");
		String responsabilidad2 = data.get("responsabilidad2");
		String fecha = data.get("fecha");
		String concurrente2 = data.get("concurrente2");
		String responsabilidad3 = data.get("responsabilidad3");
		String reportes = data.get("carpeta1");
		String campo = data.get("campo");
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
		addStep("Ingresar a la responsabilidad 'MXOXXO-APXXXXX-Super Usuario'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar: Configuración --> Consultas --> Cuentas a Pagar");
		paginaInicial.clickOnCarpeta(configuracion);
		paginaInicial.clickOnCarpeta(consultas);
		paginaInicial.clickRunCuentasAPagarAPSuperUsuario();
		testCase.addScreenShotCurrentStep("responsabilidad");
		u.hardWait(10);
		patternUtil.runApplet();
		u.hardWait(25);
		testCase.addScreenShotCurrentStep("pantallaCP");
		// PASO 7
		addStep("Dar F11");
		patternUtil.f11();
		u.hardWait(5);
		patternUtil.enterText(tipo);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("datos");
		patternUtil.ctrlF11();
		u.hardWait(3);
		// PASO 8
		addStep("Validar razon social CADENA COMERCIAL OXXO");
		patternUtil.nTAB(5);
		u.hardWait(3);
		patternUtil.nDOWN(41);
		u.hardWait(3);
		patternUtil.nTAB(2);
		u.hardWait(25);
		testCase.addScreenShotCurrentStep("razonComercial");
		assertEquals(patternUtil.textCopy(),text);
		u.hardWait(3);
		// PASO 9
		addStep("Ejecutar con currente 'XXCO-Proceso de Generacion de Descuentos por Pago de Salarios de Empleados de Comisionistas'");
		patternUtil.closeWindowInsideApplet();
		u.hardWait(3);
		patternUtil.clickImage("cambiarResponsabilidad");
		u.hardWait(3);
		/*patternUtil.clickImage("buscarPorcentaje");
		u.hardWait(3);
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
		patternUtil.clickImage("buscarXX");
		u.hardWait(3);
		patternUtil.enterText(buscar);
		patternUtil.keyAlt("b");
		u.hardWait(5);
		patternUtil.nDOWN(4);
		patternUtil.keyAlt("a");
		testCase.addScreenShotCurrentStep("coNuevaSolicitudDatos1");
		// PASO 10
		addStep("Ingresar los parametros, clic boton Aceptar'");
		u.hardWait(3);
		patternUtil.enterText(comisionista);
		patternUtil.keyAlt("a");
		testCase.addScreenShotCurrentStep("coNuevaSolicitudDatos2");
		u.hardWait(3);
		patternUtil.keyAlt("a");
		testCase.addScreenShotCurrentStep("coNuevaSolicitudDatos3");
		u.hardWait(3);
		// PASO 11
		addStep("Clic en el boton de Ejecutar.");
		patternUtil.clickEjecutar();
		testCase.addScreenShotCurrentStep("coNuevaSolicitudEjecutar");
		// PASO 12
		addStep("�Ejecutar Otra Solicitud? No.");
		patternUtil.ejecutarOtraSolicitud("No");
		testCase.addScreenShotCurrentStep("coNuevaSolicitudNo");
		// PASO 13
		addStep("Dirigirse al menu: Ver(x) -> 'Solicitudes' -> 'Encontrar'.");
		patternUtil.encontrarSolicitudes();
		testCase.addScreenShotCurrentStep("coEncontraNuevaSolicitud");
		patternUtil.clickEncontrar();
		u.hardWait(3);
		// PASO 14
		addStep("Dar clic en Visualizar Output");
		patternUtil.clickVisualizarOutput();
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("coVisualizarOutput");
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		u.hardWait(5);
		// PASO 15
		addStep("Ejecutar el proceso 'XXCO-Proceso de timbrado de facturas de anticipos de comisionistas'.");
		//patternUtil.clickOracle();
		patternUtil.keyAlt("a");
		u.hardWait(3);
		patternUtil.closeWindowInsideApplet();
		u.hardWait(1);
		patternUtil.clickImage("cambiarResponsabilidad");
		u.hardWait(3);
		/*patternUtil.clickImage("buscarPorcentaje");
		u.hardWait(3);
		patternUtil.enterText(responsabilidad3);
		u.hardWait(3);
		patternUtil.keyAlt("b");*/
		patternUtil.nDOWN(6);
		testCase.addScreenShotCurrentStep("coCambioResponsabilidad");
		patternUtil.nEnter(1);
		u.hardWait(3);
		patternUtil.encontrarSolicitudes();
		testCase.addScreenShotCurrentStep("coNuevaSolicitud2");
		u.hardWait(3);
		patternUtil.keyAlt("n");
		u.hardWait(3);
		patternUtil.typeAndTab(concurrente2);
		u.hardWait(5);
		patternUtil.enterText(fecha);
		patternUtil.nTAB(1);
		patternUtil.enterText(fecha);
		patternUtil.keyAlt("a");
		testCase.addScreenShotCurrentStep("coNuevaSolicitudDatos2");
		addStep("Clic en el boton de Ejecutar.");
		patternUtil.clickEjecutar();
		testCase.addScreenShotCurrentStep("coNuevaSolicitudEjecutar2");
		u.hardWait(3);
		patternUtil.ejecutarOtraSolicitud("No");
		testCase.addScreenShotCurrentStep("coNuevaSolicitudNo2");
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