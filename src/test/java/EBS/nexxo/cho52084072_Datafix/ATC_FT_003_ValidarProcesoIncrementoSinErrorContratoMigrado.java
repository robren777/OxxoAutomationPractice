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

public class ATC_FT_003_ValidarProcesoIncrementoSinErrorContratoMigrado extends BaseExecution {
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_003_ValidarProcesoIncrementoSinErrorContratoMigrado_test(HashMap<String, String> data)
			throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String incremento = data.get("carpeta1");
		String ebsPattern = data.get("ebsPattern");
		String fechaproceso = data.get("fechaproceso");
		String plaza = data.get("plaza");
		String metodoincremento = data.get("metodoincremento");
		String arrendamiento = data.get("arrendamiento");
		String aplicarincrementos = data.get("aplicarincrementos");
		String numero = data.get("numero");
		String fechainicio = data.get("fechainicio");
		String fechafinal = data.get("fechafinal");
		String nombreproveedor = data.get("nombreproveedor");
		String sucursal = data.get("sucursal");
		String numeroproveedor = data.get("numeroproveedor");
		String fechacronograma = data.get("fechacronograma");
		

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
		paginaInicial.clickOnCarpeta(incremento);
		paginaInicial.clickCalculoIncrementoRentas();
		u.hardWait(10);
		patternUtil.runApplet();
		u.hardWait(15);
		// PASO 7
		addStep("Ingresar los parámetros: Fecha de Proceso: ENE-2021 Plaza: 10VCZ Metodo de Incremento: INPC Arrendamiento: 37731 Aplicar Incrementos?: Si Aceptar.");
		patternUtil.keyAlt("l");
		patternUtil.enterText(fechaproceso);
		u.hardWait(1);
		patternUtil.nTAB(1);
		patternUtil.enterText(plaza);
		u.hardWait(8);
		patternUtil.enterText(metodoincremento);
		patternUtil.nTAB(1);
		patternUtil.enterText(arrendamiento);
		u.hardWait(1);
		patternUtil.nTAB(1);
		patternUtil.enterText(aplicarincrementos);
		u.hardWait(1);
		patternUtil.keyAlt("a");
		patternUtil.nDOWN(1);
		patternUtil.keyAlt("a");
		// PASO 8
		addStep("Dar clic en Ejecutar.");
		patternUtil.keyAlt("j");
		// PASO 9
		addStep("¿Ejecutar Otra Solicitud? No.");
		patternUtil.ejecutarOtraSolicitud("No");
		// PASO 10
		addStep("Dirigirse al menu: Ver(x) -> 'Solicitudes' -> 'Encontrar'.");
		u.hardWait(5);
		patternUtil.encontrarSolicitudes();
		u.hardWait(3);
		patternUtil.clickEncontrar();
		u.hardWait(5);
		addStep("Dar clic en Visualizar Output");
		patternUtil.verificarEstado("ejecutandoVerde", 6000000);
		// PASO 11
		addStep("Validamos los detalles de Pago.");
		patternUtil.closeWindowInsideApplet();
		patternUtil.nTAB(2);
		patternUtil.clickAbrir();
		patternUtil.nTAB(1);
		patternUtil.clickAbrir();
		patternUtil.nTAB(2);
		patternUtil.typeAndTab(numero);
		patternUtil.keyAlt("n");
		patternUtil.keyAlt("b");
		u.hardWait(5);
		patternUtil.clickImage("pagos");
		patternUtil.nTAB(8);
		patternUtil.nDOWN(5);
		patternUtil.textCopy();
		assertEquals(fechainicio,p.controlCopiar());
		patternUtil.nTAB(1);
		assertEquals(fechafinal, p.controlCopiar());
		patternUtil.nTAB(7);
		assertEquals(nombreproveedor, p.controlCopiar());
		patternUtil.nTAB(1);
		assertEquals(sucursal, p.controlCopiar());
		u.hardWait(3);
		patternUtil.keyAlt("t");
		u.hardWait(3);
		patternUtil.nTAB(9);
		assertEquals(nombreproveedor, p.controlCopiar());
		patternUtil.nTAB(1);
		assertEquals(numeroproveedor, p.controlCopiar());
		patternUtil.nTAB(1);
		assertEquals(sucursal, p.controlCopiar());
		patternUtil.closeWindowInsideApplet();
		patternUtil.closeWindowInsideApplet();
		patternUtil.closeWindowInsideApplet();
		// PASO 12
		addStep("Consultar los Cronogramas de Pago.");
		patternUtil.nTAB(7);
		patternUtil.clickAbrir();
		patternUtil.clickAbrir();
		patternUtil.closeWindowInsideApplet();
	    patternUtil.f11();
		u.hardWait(5);
		patternUtil.nTAB(5);
		u.hardWait(3);
		patternUtil.enterText(fechacronograma);
		u.hardWait(3);
		patternUtil.nTAB(5);
		u.hardWait(3);
		patternUtil.enterText(numero);
		u.hardWait(3);
		patternUtil.ctrlF11();
		u.hardWait(3);
		patternUtil.nTAB(4);
		assertTrue(patternUtil.validatePattern("provisorio"));
		patternUtil.nTAB(1);
		assertEquals(fechacronograma, p.controlCopiar());
		patternUtil.closeWindowInsideApplet();
		patternUtil.closeWindowInsideApplet();
		patternUtil.ventanaConfirmarSalir("a");
		u.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_ValidarProcesoIncrementoSinErrorContratoMigrado_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que el proceso de Incremento de Contrato finalice sin error al utilizar un contrato Migrado por Nexxo. 1.- Contar con acceso a la responsabilidad 'MXOXXO-PNXXXXX-Admon OS_ACT_FIJO_OS'. 2.- Previamente haber ejecutado el proceso 'XXFC PN Reclasificacion de Contratos y Direcciones NEXXO'  CP002.";
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

		return "1.- Contar con acceso a la responsabilidad 'MXOXXO-PNXXXXX-Admon OS_ACT_FIJO_OS'. 2.- Previamente haber ejecutado el proceso 'XXFC PN Reclasificacion de Contratos y Direcciones NEXXO' CP002.";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}