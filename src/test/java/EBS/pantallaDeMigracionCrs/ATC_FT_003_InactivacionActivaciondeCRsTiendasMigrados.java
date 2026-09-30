package EBS.pantallaDeMigracionCrs;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.Sikuli.MetodosSikuli;
import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSExtraccion;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_003_InactivacionActivaciondeCRsTiendasMigrados extends BaseExecution {
	
@Test(dataProvider = "data-provider")
	
	public void ATC_FT_003_InactivacionActivaciondeCRsTiendasMigrados_Test(HashMap<String, String> data)
	          throws Exception {
	/*
	 * VARIABLES
	 */
	String ebsUrl = data.get("server");
	String userName = data.get("userName");
	String password = PasswordUtil.decryptPassword(data.get("password"));
	String responsabilidad = data.get("responsabilidad");
	String compania = data.get("compania");
	String estadoFinanciero = data.get("estadoFinanciero");
	String crSuperior = data.get("crSuperior");
	String fecha = data.get("fechabi");
	String archivo = data.get("archivo");
	String nombreValores = data.get("nombrevalores");
	String num = data.get("num");
	String numerocr = data.get("numerocr");
	String numerocr1 = data.get("numerocr1");
	String numerocr2 = data.get("numerocr2");
	String numerocr3 = data.get("numerocr3");
	String numerocr4 = data.get("numerocr4");
	/*
	 * UTILERIAS
	 */
	SeleniumUtil u = new SeleniumUtil(new IETest(), true);
	LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
	EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
	EBSExtraccion extraccion = new EBSExtraccion(u, testCase);
	SikuliUtil sk = new SikuliUtil();
	String pathImg = sk.PatternPath("EBS\\"+"EBS");
	Pattern pathimg = new Pattern();
	ArrayList<String> ListImage = new ArrayList<String>();
	HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
	ArrayList<Pattern> patterns = new ArrayList<Pattern>();
	File Fol = new File(pathImg);
	MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
	
	/*
	 * PASOS DEL CASO DE PRUEBA, MATRIZ: PantallaDeMigracionCrs
	 */

	// PASO 1
	addStep("Ingresar a EBS AVEBQA a la siguiente ruta: http://fcrebsqa.femcom.net:8011");
	logger.info("EBS URL: " + ebsUrl);
	System.out.println("EBS URL: " + ebsUrl);
	logger.info("User Name: " + userName);
	System.out.println("User Name: " + userName);
	inicio.login();
	
	// PASO 2
	addStep("Ingresar a la Responsabilidad MXOXXO-GLXXXXX-Super Usuario.");
	paginaInicial.clickOnResponsabilidad(responsabilidad);
	
	// PASO 3
	addStep("Ir a CRs > Transferencia de CRs.");
	paginaInicial.carpetaCRsElement.click();
	paginaInicial.transCRsElement.click();
	
	// PASO 4
	addStep("Se ingresan los datos de la Plaza de la cual se van a migrar los CRs hacia la nueva Mega Plaza. Se da clic en buscar.");
	extraccion.companiaElement.sendKeys(compania);
	extraccion.setEstadoFinanciero(estadoFinanciero);
	u.hardWait(2);
	extraccion.crSuperiorElement.sendKeys(crSuperior);
	u.hardWait(2);
	testCase.addScreenShotCurrentStep("e ingresan los datos de la Plaza de la cual se van a migrar los CRs hacia la nueva Mega Plaza. Se da clic en buscar.");
	extraccion.clickBotonBuscar();
	// PASO 5
	addStep("Se da clic en el boton siguiente");
	extraccion.clickBotonSiguiente();
	extraccion.SetFecha(fecha);
	extraccion.seleccionarArchivo(extraccion.seleccionarArchivoSinSendkeys(archivo));
	// PASO 6
	addStep("Se da clic en el boton valida archivo");
	extraccion.clickValidarArchivo();
	// PASO 7
	addStep("Se da clic en el boton siguiente");
	// PASO 8
    addStep("Se da clic en el boton confirmar");
	testCase.addScreenShotCurrentStep("Se da clic en el boton valida archivo");
	u.waitForLoadPage();
	paginaInicial.home();
	paginaInicial.clickAltaCentrosResponsabilidad();
	p.runApplet();
	u.hardWait(3);
	sk.screen.keyDown(Key.F4);
	sk.screen.keyUp(Key.F4);
	p.nTAB(6);
	sk.screen.keyDown(Key.ENTER);
	sk.screen.keyUp(Key.ENTER);
	u.hardWait(2);
	sk.screen.keyDown(Key.ENTER);
	sk.screen.keyUp(Key.ENTER);
	u.hardWait(2);
	sk.screen.keyDown(Key.ENTER);
	sk.screen.keyUp(Key.ENTER);
	u.hardWait(2);
	sk.screen.keyDown(Key.ENTER);
	sk.screen.keyUp(Key.ENTER);
	u.hardWait(2);
	p.nTAB(5);
	sk.screen.keyDown(Key.ENTER);
	sk.screen.keyUp(Key.ENTER);
	u.hardWait(2);
	// PASO 9
    addStep("Dentro de Oracle se ingresa a la Responsabilidad de MXOXXO-GLXXXXX-Super Usuario al menu: Configuracion > Flexfields > Clave > Seguridad > Define");
	testCase.addScreenShotCurrentStep("Dentro de Oracle se ingresa a la Responsabilidad de MXOXXO-GLXXXXX-Super Usuario al menu: Configuracion > Flexfields > Clave > Seguridad > Define");
	sk.screen.keyDown(Key.ENTER);
	sk.screen.keyUp(Key.ENTER);
	u.hardWait(2);
	p.llenarJuegoValores(nombreValores);
	sk.screen.keyDown(Key.F4);
	sk.screen.keyUp(Key.F4);
	p.nTAB(22);
	sk.screen.keyDown(Key.ENTER);
	sk.screen.keyUp(Key.ENTER);
    p.nTAB(2);
    sk.screen.keyDown(Key.ENTER);
	sk.screen.keyUp(Key.ENTER);
	p.buscarArchivoTab(compania);
	p.buscarArchivoTab(num);
	// PASO 10
	addStep("Se ingresa el nombre del Flexfield en el que se consultaran las reglas de seguridad.");
	testCase.addScreenShotCurrentStep("Se ingresa el nombre del Flexfield en el que se consultaran las reglas de seguridad.");
	sk.screen.keyDown(Key.ENTER);
    sk.screen.keyUp(Key.ENTER);
    p.lamparaBusqueda();
    u.hardWait(2);
    p.clickCR();
    p.buscarArchivoTab(numerocr);
	sk.screen.keyDown(Key.ENTER);
    sk.screen.keyUp(Key.ENTER);
    // PASO 11
    addStep("Se consultan las Reglas del Estado Financiero y del Distrito, para validar que se hayan asociado correctamente los CRs migrados.");
	testCase.addScreenShotCurrentStep("Se consultan las Reglas del Estado Financiero y del Distrito, para validar que se hayan asociado correctamente los CRs migrados.");
    p.lamparaBusqueda();
    u.hardWait(2);
    p.buscarArchivoTab(numerocr1);
	sk.screen.keyDown(Key.ENTER);
    sk.screen.keyUp(Key.ENTER);
    // PASO 12
    addStep("Dentro de la Responsabilidad de MXOXXO-GLXXXXX-Super Usuario, se ingresa al menu: CRs > Maestro de CRs.");
	testCase.addScreenShotCurrentStep("Dentro de la Responsabilidad de MXOXXO-GLXXXXX-Super Usuario, se ingresa al menu: CRs > Maestro de CRs.");
    p.lamparaBusqueda();
    u.hardWait(2);
    p.buscarArchivoTab(numerocr2);
	sk.screen.keyDown(Key.ENTER);
    sk.screen.keyUp(Key.ENTER);
    p.lamparaBusqueda();
    u.hardWait(2);
    p.buscarArchivoTab(numerocr3);
	sk.screen.keyDown(Key.ENTER);
    sk.screen.keyUp(Key.ENTER);
    p.lamparaBusqueda();
    u.hardWait(2);
    // PASO 13
    addStep("Se ingresa el numero de la Compañía y el Estado Financiero a consultar.");
	testCase.addScreenShotCurrentStep("Se ingresa el numero de la Compañía y el Estado Financiero a consultar.");
	 // PASO 14
    addStep("Se consultan los CRs tiendas que se migraron y se valida que la informacion se muestre de manera correcta.");
	testCase.addScreenShotCurrentStep("Se consultan los CRs tiendas que se migraron y se valida que la informacion se muestre de manera correcta.");
    p.buscarArchivoTab(numerocr4);
	sk.screen.keyDown(Key.ENTER);
    sk.screen.keyUp(Key.ENTER);
    sk.screen.keyDown(Key.F4);
	sk.screen.keyUp(Key.F4);
	u.hardWait(2);
	sk.screen.keyDown(Key.F4);
	sk.screen.keyUp(Key.F4);
	u.hardWait(2);
	sk.screen.keyDown(Key.ENTER);
	sk.screen.keyUp(Key.ENTER);
	testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
	u.close();
		
		
}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_InactivacionActivaciondeCRsTiendasMigrados_Test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se realice correctamente la inactivacion/activacion de los CRs Tiendas migrados hacia Mega Plaza Veracruz."
				+ "Nota: se modifico el flujo del caso porque el archivo que genera de excel se tiene que modificar manualmente"
				+ "Prerequisitos: Acceso a la responsabilidad: "
				+ "MXOXXO-GLXXXXX-Super Usuario, Acceso a BD de instancia AVEBQA";
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
		return "Prerequisitos: Acceso a la responsabilidad: "
				+ "MXOXXO-GLXXXXX-Super Usuario, Acceso a BD de instancia AVEBQA";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

}