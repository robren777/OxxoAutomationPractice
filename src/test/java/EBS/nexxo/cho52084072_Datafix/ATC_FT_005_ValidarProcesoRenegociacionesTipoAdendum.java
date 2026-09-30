package EBS.nexxo.cho52084072_Datafix;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.Sikuli.MetodosSikuli;
import EBS.Sikuli.PatternUtil;
import EBS.tools.CargaDeArchivosTool;
import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.CargaArchivos_RenegociacionContratos;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_005_ValidarProcesoRenegociacionesTipoAdendum extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_005_ValidarProcesoRenegociacionesTipoAdendum_test(HashMap<String, String> data)
			throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String renegociacion = data.get("carpeta1");
		String ebsPattern = data.get("ebsPattern");
		String numero = data.get("numero");
		String firmaadendum = data.get("firmaadendum");
		String inicioadendum = data.get("inicioadendum");
		String final1 = data.get("final1");
		String years = data.get("years");
		String mes = data.get("mes");
		String dias = data.get("dias");
		String clausula = data.get("clausula");
		String pagos = data.get("pagos");
		String escenario = data.get("escenario");
		String categoria = data.get("categoria");
		String titulo = data.get("titulo");
		String descripcion = data.get("descripcion");
		String categoria2 = data.get("categoria2");
		String titulo2 = data.get("titulo2");
		String descripcion2 = data.get("descripcion2");
		String filepath = data.get("filepath");
		String filepath2 = data.get("filepath2");
		String mensual = data.get("mensual");
		String inpc = data.get("inpc");
		String periodicidad = data.get("periodicidad");
		String fechaincremento = data.get("fechaincremento");
		String opinion = data.get("opinion");
		String saldoamortizar = data.get("saldoamortizar");
		String amortmensual = data.get("amortmensual");
		String arrendador = data.get("arrendador");
		String sucursal = data.get("sucursal");
		String estatusaprobacion = data.get("estatusaprobacion");
		String nombre = data.get("nombre");
		String fechafinal = data.get("fechafinal");
		String navegador = data.get("navegador");

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		SeleniumUtil u2 = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		LoginTool inicio2 = new LoginTool(u2, testCase, navegador, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		CargaDeArchivosTool archivosTool = new CargaDeArchivosTool(u, testCase);
		CargaArchivos_RenegociacionContratos renegociacionContratos = new CargaArchivos_RenegociacionContratos(u,
				testCase);
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
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: CHO52084072_Datafix
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
		addStep("Ingresar a la responsabilidad 'MXOXXO-APXXXXX-ADMON-PROVEEDORES_OPE_CAT_OS'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar: Incrementos de Rentas -> Calculo de Incrementos de Rentas");
		paginaInicial.clickOnCarpeta(renegociacion);
		paginaInicial.clickCapturaRenegociacionCancelacionContratos();
		u.hardWait(3);
		patternUtil.runApplet();
		u.hardWait(5);
		// PASO 7
		addStep("Consultamos el Contrato '23454' seleccionando Tipo de Solicitud 'Renegociación - Adendum'.");
		patternUtil.typeAndTab(numero);
		patternUtil.keyAlt("n");
		u.hardWait(3);
		patternUtil.keyAlt("s");
		u.hardWait(3);
		// PASO 8
		addStep("Capturamos la Renegociación.");
		// patternUtil.keyAlt("a");
		patternUtil.clickImage("firmaaden");
		u.hardWait(3);
		patternUtil.enterText(firmaadendum);
		patternUtil.nTAB(1);
		patternUtil.enterText(inicioadendum);
		patternUtil.nTAB(1);
		patternUtil.enterText(final1);
		patternUtil.nTAB(1);
		patternUtil.enterText(years);
		patternUtil.nTAB(1);
		patternUtil.enterText(mes);
		patternUtil.nTAB(1);
		patternUtil.enterText(dias);
		patternUtil.nTAB(1);
		patternUtil.enterText(clausula);
		patternUtil.nTAB(1);
		patternUtil.enterText(pagos);
		patternUtil.nTAB(1);
		patternUtil.enterText(escenario);
		patternUtil.nTAB(1);
		u.hardWait(5);
		patternUtil.clickImage("clip");
		u.hardWait(8);
		patternUtil.nTAB(1);
		patternUtil.enterText(categoria);
		patternUtil.nTAB(1);
		patternUtil.nDOWN(2);
		patternUtil.keyAlt("a");
		patternUtil.enterText(titulo);
		patternUtil.nTAB(1);
		u.hardWait(3);
		patternUtil.enterText(descripcion);
		patternUtil.nTAB(2);
		u.hardWait(3);
		sk.screen.mouseMove();
		sk.screen.click();
		patternUtil.closeWindow();
		u.hardWait(3);
		patternUtil.clickImage("explorer");
		inicio2.login();
		u.close();
		renegociacionContratos.selectFileCurrentProject(filepath);
		renegociacionContratos.clickEjecutar();
		patternUtil.closeWindow();
		patternUtil.clickOracle(); 
		patternUtil.keyAlt("s");
		patternUtil.clickImage("sec"); 
		patternUtil.nTAB(1);
		patternUtil.enterText(categoria2); 
		patternUtil.nTAB(1);
		patternUtil.enterText(titulo2); 
		patternUtil.nTAB(1);
		patternUtil.enterText(descripcion2); 
		patternUtil.nTAB(2);
		u.hardWait(3);
		sk.screen.mouseMove();
		sk.screen.click();
		patternUtil.closeWindow();
		u.hardWait(3);
		patternUtil.clickImage("explorer");
		inicio2.login();
		u.close();
		renegociacionContratos.selectFileCurrentProject(filepath2);
		renegociacionContratos.clickEjecutar();
		patternUtil.closeWindow();
		patternUtil.clickOracle(); 
		patternUtil.keyAlt("s");
		patternUtil.closeWindowInsideApplet(); 
		patternUtil.clickImage("pagos");
		patternUtil.clickImage("mensual"); 
		patternUtil.enterText(mensual);
		patternUtil.nTAB(7); 
		patternUtil.enterText(inpc); 
		patternUtil.nTAB(1);
		patternUtil.enterText(periodicidad); 
		patternUtil.nTAB(2);
		patternUtil.enterText(fechaincremento); 
		patternUtil.nTAB(1);
		patternUtil.enterText(opinion); 
		patternUtil.nTAB(2);
		patternUtil.enterText(saldoamortizar); 
		patternUtil.nTAB(1);
		patternUtil.enterText(amortmensual); 
		patternUtil.nTAB(1);
		patternUtil.enterText(arrendador); 
		patternUtil.nTAB(1);
		patternUtil.enterText(sucursal); 
		patternUtil.nTAB(1); 
		// PASO 9
		addStep("Dar clic en 'Solicitar Aprobación'."); 
		patternUtil.keyAlt("s");
		patternUtil.keyAlt("a"); 
		patternUtil.keyAlt("s"); 
		patternUtil.keyAlt("a"); 
		// PASO 10 
		addStep("Consultamos el Estado de la Solicitud de Enmienda.");
		patternUtil.closeWindowInsideApplet(); 
		patternUtil.nTAB(6);
		patternUtil.clickAbrir(); 
		patternUtil.nTAB(1); 
		patternUtil.clickAbrir();
		patternUtil.nTAB(5); 
		patternUtil.enterText(numero); 
		patternUtil.keyAlt("n");
		patternUtil.nTAB(4); 
		assertEquals(estatusaprobacion, p.controlCopiar());
		//PASO 11 
		addStep("Validamos el Contrato y sus detalles de Pago.");
		patternUtil.closeWindowInsideApplet(); 
		patternUtil.nUP(6);
		patternUtil.clickAbrir(); 
		patternUtil.nTAB(1); 
		patternUtil.clickAbrir();
		u.hardWait(5); 
		patternUtil.nTAB(2); 
		patternUtil.enterText(numero);
		patternUtil.keyAlt("n"); 
		patternUtil.clickAbrir(); 
		assertEquals(nombre,p.controlCopiar()); 
		patternUtil.nTAB(1); 
		assertEquals(numero,p.controlCopiar()); 
		u.hardWait(3); 
		patternUtil.clickImage("pagos");
		patternUtil.nDOWN(4); 
		patternUtil.nTAB(8); 
		assertEquals(fechafinal,p.controlCopiar()); 
		patternUtil.keyAlt("t"); 
		patternUtil.nTAB(11); 
		// PASO 12
		addStep("Consultar los Cronogramos de Pago.");
		patternUtil.closeWindowInsideApplet(); 
		patternUtil.closeWindowInsideApplet();
		patternUtil.closeWindowInsideApplet(); 
		patternUtil.nTAB(10);
		patternUtil.clickAbrir(); 
		u.hardWait(1); 
		patternUtil.clickAbrir();
		patternUtil.closeWindowInsideApplet(); 
		patternUtil.f11(); 
		u.hardWait(5);
		patternUtil.nTAB(10); 
		u.hardWait(3); 
		patternUtil.enterText(numero);
		u.hardWait(5); 
		patternUtil.ctrlF11(); 
		u.hardWait(3); 
		patternUtil.closeText();
		patternUtil.closeWindow(); 
		u.hardWait(5);
		patternUtil.ventanaConfirmarSalir("a"); 
		u.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_005_ValidarProcesoRenegociacionesTipoAdendum_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que el proceso de Renegociaciones Tipo Adendum finalice sin error al utilizar un contrato Migrado por Nexxo. 1.- Contar con acceso a la responsabilidad 'MXOXXO-PNXXXXX-Super Usuario'. 2.- Previamente haber ejecutado el proceso 'XXFC PN Reclasificacion de Contratos y Direcciones NEXXO' CP004.";
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

		return "1.- Contar con acceso a la responsabilidad 'MXOXXO-PNXXXXX-Super Usuario'. 2.- Previamente haber ejecutado el proceso 'XXFC PN Reclasificacion de Contratos y Direcciones NEXXO'  CP004.";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}