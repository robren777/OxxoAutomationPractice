package EBS.nexxo.cho52084072;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.Sikuli.MetodosSikuli;
import EBS.Sikuli.PatternUtil;
import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_EjecuciondelProcesoXXFCPNReclasificaciondeContratos extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_001_EjecuciondelProcesoXXFCPNReclasificaciondeContratos_test(HashMap<String, String> data)
			throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String arrendamientos = data.get("carpeta1");
		String numero = data.get("numero");
		String nombre = data.get("nombre");
		String estadoaprobacion = data.get("estadoaprobacion");
		String unidadoperativa = data.get("unidadoperativa");
		String codigodireccion = data.get("codigodireccion");
		String propiedad = data.get("propiedad");
		String plazaorigen = data.get("plazaorigen");
		String plazadestino = data.get("plazadestino");
		String estadopago = data.get("estadopago");

		/*
		 * UTILERIAS
		 */

		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		PatternUtil pu = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: CHO52084072
		 */

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
		addStep("Seleccionar: Arrendamientos y Documentos : Arrendamiento Principal -> Visualizar Arredamientos y Documentos");
		paginaInicial.clickOnCarpeta(arrendamientos);
		paginaInicial.clickVisualizarArrendamientosDocumentos();
		p.runApplet();
		// PASO 7
		addStep("Consultar el número de contrato y dar clic en 'Encontrar'");
		u.hardWait(4);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		p.typeAndTab(numero);
		p.keyAlt("n");
		p.keyAlt("b");
		u.hardWait(3);
		assertEquals(nombre, p.controlCopiar());
		u.hardWait(3);
		pu.nTAB(6);
		pu.validatePattern("final");
		assertTrue(pu.validatePattern("final"));
		p.compararCelda(estadoaprobacion);
		u.hardWait(3);
		pu.clickImage("direcciones");
		pu.nTAB(7);
		u.hardWait(3);
		pu.clickImage("pagos");
		pu.nTAB(16);
		u.hardWait(3);
		pu.keyAlt("t");
		u.hardWait(3);
		pu.clickImage("distribucioncuentas");
		pu.nTAB(1);
		u.hardWait(3);
		pu.closeWindowInsideApplet();
		u.hardWait(3);
		pu.closeWindowInsideApplet();
		u.hardWait(3);
		pu.closeWindowInsideApplet();
		// PASO 8
		addStep("Consultamos estado de los Cronogramas del Contrato Original.");
		pu.nTAB(9);
		pu.clickAbrir();
		pu.clickAbrir();
		u.hardWait(3);
		pu.closeWindowInsideApplet();
		pu.f11();
		u.hardWait(3);
		pu.nTAB(10);
		u.hardWait(3);
		pu.enterText(numero);
		u.hardWait(3);
		pu.ctrlF11();
		u.hardWait(3);
		pu.closeText();
		u.hardWait(3);
		pu.nUP(9);
		pu.clickAbrir();
		pu.nTAB(3);
		pu.clickAbrir();
		pu.enterText(unidadoperativa);
		pu.nTAB(3);
		pu.enterText(codigodireccion);
		pu.keyAlt("n");
		pu.keyAlt("b");
		pu.nTAB(1);
		p.compararCelda(codigodireccion);
		pu.nTAB(1);
		p.compararCelda(propiedad);
		pu.closeWindowInsideApplet();
		pu.closeWindowInsideApplet();
		pu.nUP(5);
		pu.clickAbrir();
		pu.nTAB(2);
		pu.clickAbrir();
		// PASO 9
		addStep("Ejecutar Proceso 'XXFC PN Reclasificacion de Contratos y Direcciones NEXXO'.");
		pu.enterText(plazaorigen);
		pu.nTAB(2);
		pu.enterText(codigodireccion);
		pu.nTAB(5);
		pu.enterText(plazadestino);
		pu.keyAlt("a");
		u.hardWait(3);
		pu.keyAlt("j");
		addStep("¿Ejecutar Otra Solicitud? No.");
		pu.ejecutarOtraSolicitud("No");
		//------------------------------------
		pu.nTAB(6);
		pu.clickAbrir();
		pu.nTAB(1);
		pu.clickAbrir();
		pu.nTAB(2);
		pu.typeAndTab(numero);
		pu.keyAlt("n");
		pu.keyAlt("b");
		u.hardWait(5);
		p.compararCelda(nombre);
		u.hardWait(3);
		pu.nTAB(6);
		p.compararCelda(estadoaprobacion);
		u.hardWait(3);
		pu.clickImage("pagos");
		pu.nTAB(16);
		u.hardWait(3);
		pu.keyAlt("t");
		pu.closeWindowInsideApplet();
		pu.closeWindowInsideApplet();
		pu.closeWindowInsideApplet();
		pu.nTAB(8);
		pu.clickAbrir();
		// PASO 10
		addStep("Validamos que no se generan Cronogramas de Pago al Contrato Nuevo.");
		pu.closeWindowInsideApplet();
		pu.f11();
		u.hardWait(5);
		pu.nTAB(10);
		u.hardWait(3);
		pu.enterText(numero);
		u.hardWait(5);
		pu.ctrlF11();
		u.hardWait(3);
		pu.compararImagenEstadoPago(estadopago);
		pu.closeText();
		pu.closeWindowInsideApplet();
		addStep("Cerrar ventanas");
		u.hardWait(3);
		pu.closeWindow();
		addStep("Cerrar ventanas");
		pu.closeWindow();
		u.hardWait(3);
		pu.closeWindow();
		pu.closeWindow();
		u.hardWait(5);
		pu.ventanaConfirmarSalir("a");
		u.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_EjecuciondelProcesoXXFCPNReclasificaciondeContratos_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar sus Cronogramas de Pago Aprobados lleguen al Nuevo Contrato con estado de Retenido y los Provisionales lleguen correctos para contrato con un arrendador Pre-requisitos: Contar con acceso a la responsabilidad 'MXOXXO-PNXXXXX-Admon OS_ACT_FIJO_OS'.NOTA : ES NECESARIO ACTUALIZAR LOS DATOS EN CADA PRUEBA";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Contar con acceso a la responsabilidad 'MXOXXO-PNXXXXX-Admon OS_ACT_FIJO_OS'.";
	}

	@Override
	public void beforeTest() {
		
	}

}
