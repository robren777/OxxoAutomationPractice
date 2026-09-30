package EBS.nexxo.cho52084072_Datafix;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

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

public class ATC_FT_004_ValidarCronogramasPagoAprobadosEstadoRetenido extends BaseExecution{
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_004_ValidarCronogramasPagoAprobadosEstadoRetenido_test(HashMap<String, String> data)
			throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String arrendamientos = data.get("carpeta1");
		String ebsPattern = data.get("ebsPattern");
		String numero = data.get("numero");
		String nombre = data.get("nombre");
		String estadoaprobacion = data.get("estadoaprobacion");
		String unidadoperativa = data.get("unidadoperativa");
		String codigodireccion = data.get("codigodireccion");
		String propiedad = data.get("propiedad");
		String plazaorigen = data.get("plazaorigen");
		String plazadestino = data.get("plazadestino");
		String estado = data.get("estado");
		
		

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

		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);

		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: CHO52084072_Datafix
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
		u.hardWait(10);
		patternUtil.runApplet();
		u.hardWait(8);
		// PASO 7
		addStep("Consultar el número de contrato y dar clic en 'Encontrar'");
		patternUtil.nTAB(2);
		patternUtil.typeAndTab(numero);
		patternUtil.keyAlt("n");
		patternUtil.keyAlt("b");
		u.hardWait(5);
		// Se valida el contrato '10CDJ-50RUP' tiene en Estado Aprobación "Provisorio" y pertenece a la UO de Ciudad Juárez.
		assertEquals(nombre, p.controlCopiar());
		u.hardWait(3);
		patternUtil.nTAB(6);
		assertTrue(patternUtil.validatePattern("final"));
		assertEquals(nombre, p.controlCopiar());
		u.hardWait(3);
		patternUtil.clickImage("direcciones");
		patternUtil.nTAB(7);
		u.hardWait(3);
		patternUtil.clickImage("pagos");
		patternUtil.nTAB(16);
		u.hardWait(3);
		patternUtil.keyAlt("t");
		u.hardWait(3);
		patternUtil.clickImage("distribucioncuentas");
		patternUtil.nTAB(1);
		u.hardWait(3);
		patternUtil.closeWindowInsideApplet();
		u.hardWait(3);
		patternUtil.closeWindowInsideApplet();
		u.hardWait(3);
		patternUtil.closeWindowInsideApplet();
		// PASO 8
		addStep("Consultamos estado de los Cronogramas del Contrato Original.");
		patternUtil.nTAB(9);
		patternUtil.clickAbrir();
		patternUtil.clickAbrir();
		u.hardWait(3);
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
		//patternUtil.closeWindowInsideApplet();
        u.hardWait(3);
		patternUtil.nUP(9);
		patternUtil.clickAbrir();
		patternUtil.nTAB(3);
		patternUtil.clickAbrir();
		patternUtil.enterText(unidadoperativa);
		patternUtil.nTAB(3);
		patternUtil.enterText(codigodireccion);
		patternUtil.keyAlt("n");
		patternUtil.keyAlt("b");
		patternUtil.nTAB(1);
		p.compararCelda(codigodireccion);
		patternUtil.nTAB(1);
		p.compararCelda(propiedad);
		patternUtil.closeWindowInsideApplet();
		patternUtil.closeWindowInsideApplet();
		patternUtil.nUP(5);
		patternUtil.clickAbrir();
		patternUtil.nTAB(2);
		patternUtil.clickAbrir();
		// PASO 9
		addStep("Ejecutar Proceso 'XXFC PN Reclasificacion de Contratos y Direcciones NEXXO'.");
		patternUtil.enterText(plazaorigen);
		patternUtil.nTAB(2);
		patternUtil.enterText(codigodireccion);
		patternUtil.nTAB(5);
		patternUtil.enterText(plazadestino);
		patternUtil.keyAlt("a");
		u.hardWait(3);
		patternUtil.keyAlt("j");
		addStep("¿Ejecutar Otra Solicitud? No.");
		patternUtil.ejecutarOtraSolicitud("No");
		addStep("Dirigirse al menu: Ver(x) -> 'Solicitudes' -> 'Encontrar'.");
		u.hardWait(5);
		patternUtil.encontrarSolicitudes();
		u.hardWait(3);
		patternUtil.clickEncontrar();
		u.hardWait(5);
		patternUtil.verificarEstado("ejecutandoVerde", 6000000);
		patternUtil.closeWindowInsideApplet();
		patternUtil.nTAB(6);
		patternUtil.clickAbrir();
		patternUtil.nTAB(1);
		patternUtil.clickAbrir();
		patternUtil.nTAB(2);
		patternUtil.typeAndTab(numero);
		patternUtil.keyAlt("n");
		patternUtil.keyAlt("b");
		u.hardWait(8);
		assertEquals(nombre, p.controlCopiar());
		u.hardWait(3);
		patternUtil.nTAB(6);
		assertTrue(patternUtil.validatePattern("final"));
		u.hardWait(3);
		patternUtil.clickImage("pagos");
		u.hardWait(3);
		patternUtil.keyAlt("t");
		patternUtil.closeWindowInsideApplet();
		patternUtil.closeWindowInsideApplet();
		patternUtil.closeWindowInsideApplet();
		patternUtil.nTAB(8);
		patternUtil.clickAbrir();
		// PASO 10
		addStep("Validamos que no se generan Cronogramas de Pago al Contrato Nuevo.");
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
		patternUtil.closeWindowInsideApplet();
		addStep("Cerrar ventanas");
		patternUtil.closeWindow();
		addStep("Cerrar ventanas");
		patternUtil.closeWindow();
		u.hardWait(3);
		patternUtil.closeWindow();
		u.hardWait(5);
		patternUtil.ventanaConfirmarSalir("a");
		u.close();
		

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_ValidarCronogramasPagoAprobadosEstadoRetenido_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar sus Cronogramas de Pago Aprobados lleguen al Nuevo Contrato con estado de Retenido y los Provisionales lleguen correctos. Contar con acceso a la responsabilidad 'MXOXXO-PNXXXXX-Admon OS_ACT_FIJO_OS'. NOTA: Esta prueba solo se puede usar una vez ya que se necesitan nuevos datos para ejecutarla";
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

		return "Contar con acceso a la responsabilidad 'MXOXXO-PNXXXXX-Admon OS_ACT_FIJO_OS'.";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}
