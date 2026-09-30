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

public class ATC_FT_002_ValidarEjecucionTimbradoFacturasAnticipoComisionistas extends BaseExecution {
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_002_ValidarEjecucionTimbradoFacturasAnticipoComisionistas_test(HashMap<String, String> data)
			throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = GlobalVariables.getMduenasHexEbsUsr;
		String password = GlobalVariables.getMduenasHexEbsPwd;
		String responsabilidad = data.get("responsabilidad");
		String reportes = data.get("carpeta1");
		String campo = data.get("campo");
		String ebsPattern = data.get("ebsPattern");
		String buscar = data.get("buscar");
		String comisionista = data.get("comisionista");
		String responsabilidad2 = data.get("responsabilidad2");
		String campo2 = data.get("campo2");

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
		addStep("Ingresar a la responsabilidad 'MXOXXO-CO10TMP-USUARIO'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar: Reportes -> Run");
		paginaInicial.clickOnCarpeta(reportes);
		paginaInicial.clickRunReportesCO10CHIUsuario();
		u.hardWait(10);
		patternUtil.runApplet();
		u.hardWait(20);
		// PASO 7
		addStep("Clic en opcion Solicitud unica, Aceptar.");
		patternUtil.solicitudUnica();
		// PASO 8
		addStep("Ingresar en el campo Nombre 'XXCO-Proceso de Generación de Descuentos por Pago de Salarios de Empleados de Comisionistas' ");
		patternUtil.typeAndTab(campo);
		u.hardWait(3);
		patternUtil.clickImage("buscarXX");
		u.hardWait(3);
		patternUtil.enterText(buscar);
		patternUtil.keyAlt("b");
		u.hardWait(5);
		patternUtil.nDOWN(4);
		patternUtil.keyAlt("a");
		// PASO 9
		addStep("Ingresar los parametros, clic boton Aceptar'");
		u.hardWait(3);
		patternUtil.enterText(comisionista);
		u.hardWait(3);
		patternUtil.keyAlt("a");
		u.hardWait(3);
		patternUtil.keyAlt("a");
		u.hardWait(3);
		// PASO 10
		addStep("Clic en el boton de Ejecutar.");
		patternUtil.clickEjecutar();
		// PASO 11
		addStep("¿Ejecutar Otra Solicitud? No.");
		patternUtil.ejecutarOtraSolicitud("No");
		// PASO 12
		addStep("Dirigirse al menu: Ver(x) -> 'Solicitudes' -> 'Encontrar'.");
		patternUtil.encontrarSolicitudes();
		patternUtil.clickEncontrar();
		u.hardWait(3);
		// PASO 13
		addStep("Dar clic en Visualizar Output");
		patternUtil.clickVisualizarOutput();
		u.hardWait(5);
		// PASO 14
		addStep("Ingresar a la responsabilidad MXOXXO-APXXXXX-Super Usuario");
		/*u.close();
		u.hardWait(3);
		patternUtil.clickImage("oracle");
		u.hardWait(3);*/
		patternUtil.keyAlt("a");
		u.hardWait(3);
		patternUtil.closeWindowInsideApplet();
		u.hardWait(3);
		patternUtil.clickImage("cambiarResponsabilidad");
		/*patternUtil.clickImage("buscarPorcentaje");
		patternUtil.enterText(responsabilidad2);
		patternUtil.keyAlt("b");*/
		u.hardWait(3);
		patternUtil.nDOWN(1);
		patternUtil.keyAlt("a");
		// PASO 15
		addStep("Ingresar a la opcion Facturas - Entrar - Facturas ");
		patternUtil.keyAlt("b");
		u.hardWait(3);
		patternUtil.keyAlt("b");
		u.hardWait(3);
		patternUtil.nDOWN(1);
		u.hardWait(3);
		patternUtil.keyAlt("b");
		u.hardWait(25);
		// PASO 16
		addStep("Clic en el icono lupa, escribir el numero de factura, clic boton Encontrar");
		patternUtil.clickImage("lamparabusqueda");
		u.hardWait(3);
		testCase.addTextEvidenceCurrentStep("Factura generada");
		u.hardWait(3);
		// PASO 17
		addStep("Validar y contabilizar la factura");
		patternUtil.closeWindowInsideApplet();
		u.hardWait(3);
		testCase.addTextEvidenceCurrentStep("Validación factura");
		u.hardWait(3);
		// PASO 18
		addStep("Ejecutar el proceso 'XXCO-Proceso de timbrado de facturas de anticipos de comisionistas'.");
		patternUtil.encontrarSolicitudes();
		patternUtil.keyAlt("n");
		u.hardWait(3);
		patternUtil.typeAndTab(campo2);
		u.hardWait(3);
		patternUtil.keyAlt("c");
		patternUtil.closeWindowInsideApplet();
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