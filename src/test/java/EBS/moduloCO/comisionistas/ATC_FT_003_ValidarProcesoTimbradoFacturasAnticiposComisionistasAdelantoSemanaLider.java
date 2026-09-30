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
import EBS.util.pageObjectModel.EBSNuevoSistemaICM;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_003_ValidarProcesoTimbradoFacturasAnticiposComisionistasAdelantoSemanaLider extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_003_ValidarProcesoTimbradoFacturasAnticiposComisionistasAdelantoSemanaLider_test(
			HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = GlobalVariables.getMduenasHexEbsUsr;
		String password = GlobalVariables.getMduenasHexEbsPwd;
		String responsabilidad = data.get("responsabilidad");
		String nuevoSistema = "Nuevo sistema ICM";//data.get("carpeta1");
		String ebsPattern = data.get("ebsPattern");
		String rfc = "RAFA7208284EA"; //data.get("rfc");
		String reportes = data.get("carpeta2");
		String responsabilidad2 = data.get("responsabilidad2");
		String concurrente = data.get("concurrente");
		String fecha = data.get("fecha");

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		EBSNuevoSistemaICM sistemaICM = new EBSNuevoSistemaICM(u, testCase);
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
		addStep("Ingresar a la responsabilidad 'MXOXXO-CO10QRI-USUARIO'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		u.hardWait(2);
		// PASO 6
		addStep("Ingresar a la opción Nuevo sistema ICM --> Pago Adelantado Semanal del Líder");
		paginaInicial.clickOnCarpeta(nuevoSistema);
		u.hardWait(2);
		paginaInicial.clickPagoAdelantadoSemanalDelLider();
		u.hardWait(3);
		// PASO 7
		addStep("Crear un nuevo pago adelantado semanal del líder, ingresando los datos");
		sistemaICM.clickBoton("ir");
		u.waitForLoadPage();
		sistemaICM.clickBoton("crear");
		u.hardWait(3);
		sistemaICM.typeAndEnter(rfc);
		sistemaICM.clickBoton("aplicar");
		u.waitForLoadPage();
		sistemaICM.clickBoton("pagina inicial");
		u.hardWait(8);
		// PASO 8
		addStep("Validar se genere la factura de AP. Dirigirse al menú Ver(x) -> Solicitudes ->  Encontrar.");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		paginaInicial.clickOnCarpeta(reportes);
		paginaInicial.clickRunReportesCO10QRIUsuario();
		u.hardWait(10);
		patternUtil.runApplet();
		u.hardWait(30);
		patternUtil.keyAlt("c");
		u.hardWait(3);
		patternUtil.encontrarSolicitudes();
		patternUtil.clickEncontrar();
		u.hardWait(3);
		// PASO 9
		addStep("Validar y contabilizar la factura generada");
		patternUtil.clickVisualizarOutput();
		u.hardWait(5);
		// assertEquals(p.getText(text), text);
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		u.hardWait(5);
		patternUtil.keyAlt("a");
		// PASO 10
		addStep("Ejecutar el proceso 'XXCO-Proceso de timbrado de facturas de anticipos de comisionistas'.");
		/*patternUtil.clickOracle();
		u.hardWait(3);
		patternUtil.closeWindowInsideApplet();
		u.hardWait(1);*/
		patternUtil.closeWindowInsideApplet();
		u.hardWait(1);
		patternUtil.clickImage("cambiarResponsabilidad");
		u.hardWait(3);
		/*patternUtil.clickImage("buscarMxOxxo");
		u.hardWait(3);
		patternUtil.enterText(responsabilidad2);
		u.hardWait(3);
		patternUtil.keyAlt("b");*/
		patternUtil.nDOWN(6);
		patternUtil.nEnter(1);
		u.hardWait(3);
		patternUtil.encontrarSolicitudes();
		u.hardWait(3);
		patternUtil.keyAlt("n");
		u.hardWait(3);
		patternUtil.typeAndTab(concurrente);
		u.hardWait(5);
		patternUtil.enterText(fecha);
		patternUtil.nTAB(1);
		patternUtil.enterText(fecha);
		patternUtil.keyAlt("a");
		addStep("Clic en el boton de Ejecutar.");
		patternUtil.clickEjecutar();
		u.hardWait(3);
		patternUtil.ejecutarOtraSolicitud("No");
		/*u.hardWait(5);
		patternUtil.closeWindowInsideApplet();
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
		return "Verificar muestre información la siguiente información en tablas:\r\n" + 
				"Tablas XXCO_CFD_FACTDIG_ENC, XXCO_CFD_FACDIG_ENC_TMP\r\n" + 
				"REC_CODIGO_POSTAL (Valor  64480)\r\n" + 
				"REC_REGIMEN_FISCAL  (Valor 623)\r\n" + 
				"EXPORTACION  (Valor 01)\r\n" + 
				"Tablas XXCO_CFD_FACTDIG_LIN, XXCO_CFD_FACDIG_LIN_TMP\r\n" + 
				"OBJETO_IMPUESTOS  (Valor 02) 1.- Acceso a responsabilidad 'MXOXXO-CO10TMP-Usuario', 'MXOXXO-COXXXXX-Administrado'";
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
		return "1.- Acceso a responsabilidad 'MXOXXO-CO10TMP-Usuario', 'MXOXXO-COXXXXX-Administrado'";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}