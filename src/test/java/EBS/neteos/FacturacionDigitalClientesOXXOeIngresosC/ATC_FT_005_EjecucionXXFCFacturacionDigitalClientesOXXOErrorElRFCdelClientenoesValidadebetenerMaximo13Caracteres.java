package EBS.neteos.FacturacionDigitalClientesOXXOeIngresosC;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.neteos.NeteosCedisMVP2.om.pomNeteos;
import EBS.util.GlobalVariables;
import EBS.util.neteos.ModelosLoginNeteos.ModeloLoginEBS;
import EBS.util.neteos.PaginaInicialNeteos.EBSPaginaInicial;
import EBS.util.neteos.SikuliNeteos.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
/**
 * PRERREQUISITOS: 
 * 1.MXOXXO-AR17ADM-ADMON-COMERCIAL
 * 2. Contar con documentos pendientes por timbrar.
 * 3. Tener configurada la factura para lanzar el error.
 * 
 * @author priscilaestefaniam@hexaware.com
 */
public class ATC_FT_005_EjecucionXXFCFacturacionDigitalClientesOXXOErrorElRFCdelClientenoesValidadebetenerMaximo13Caracteres extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_EjecucionXXFCFacturacionDigitalClientesOXXOErrorElRFCdelClientenoesValidadebetenerMaximo13Caracteres_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		String concurrente = data.get("concurrente"); 
		String tipotransaccion = data.get("tipotransaccion");
		String numtransaccion = data.get("numtransaccion");
		String enviarcorreo = data.get("enviarcorreo");
		String reimprimirfactura = data.get("reimprimirfactura");
		String tablaerror = "select wm_error,trx_number from xxfc.xxfc_cfd_factura_digital_error where trx_number = '517809'";
		
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		ModeloLoginEBS modelo = new ModeloLoginEBS(u, testCase, ebsUrl, userName, password);
		pomNeteos pom = new pomNeteos(u, testCase, data);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"NETEOS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		PatternUtil p = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		utils.sql.SQLUtil dbEBS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
		GlobalVariables.DB_PASSWORD_AVEBQA);
		
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// ******************************** PASO 1 ****************************************/
		addStep("Ingresar al ambiente de EBS.");
		modelo.navigate(ebsUrl);
		testCase.addScreenShotCurrentStep(" ");
		logger.info("EBS URL: " + ebsUrl);
		
		// ******************************** PASO 2 ****************************************/
		addStep("Seleccionar idioma: Espanol Latinoamericano.");
		modelo.setLanguage();
		testCase.addScreenShotCurrentStep(" ");
		
		// ******************************** PASO 3 ****************************************/
		addStep("Ingresar usuario y contrasena.");
		modelo.setCredentials();
		testCase.addScreenShotCurrentStep(" ");
		
		
		// ******************************** PASO 4 ****************************************/
		addStep("Dar clic en el boton de 'Conectese'.");
		modelo.clickLogIn();
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(3);
		
		// ******************************** PASO 5 ****************************************/
		addStep("Seleccionar la responsabilidad \"MXOXXO-AR17ADM-ADMON-COMERCIAL\" -> Informes -> Otros. -> Solicitud Unica");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		testCase.addScreenShotCurrentStep(" ");
		paginaInicial.clickOnCarpeta(carpeta);
		u.hardWait(8);
		assertTrue(pom.clickOtros(),"Fallo al dar click en Otros");
		u.hardWait(30);
		p.runApplet3();
		u.hardWait(60);
		sk.screen.type(Key.ENTER);
		assertTrue(pom.validarPantallaNeuvaSolicitud(),"No se encontro la pantalla de solicitud");

		
		
		/******************************** PASO 6 *****************************************/
		addStep("Buscar el proceso \" XXFC - Facturacion Digital Clientes OXXO\".");
		p.enterText(concurrente);
		pom.screenshot();
		assertTrue(pom.validarPantallaParametros(),"No se encontro la pantalla para agregar parametros");
		
		/******************************** PASO 7 ****************************************/
		addStep("Ingresar en los parametros");
		u.hardWait(8);
		p.ingresarParametrosFacDigeIngresos(tipotransaccion, numtransaccion, enviarcorreo, reimprimirfactura);
		pom.screenshot();
		u.hardWait(8);
	
		/******************************** PASO 8 ****************************************/
		addStep("Dar clic en el boton de Ejecutar");
		p.clickEjecutar();
		u.hardWait(10);
	    pom.screenshot();
		p.clickNo();
     	u.hardWait(8);
     	p.vers();
		u.hardWait(40);
		p.clickEncontrar();
		assertTrue(pom.validarPantallaEncontrarSolicitudes(),"No se muestra la pantalla Encontrar Solicitudes");
		
		/******************************** PASO 9 ****************************************/
		addStep("Validar el concurrente finaliza.");
		u.hardWait(20);
		assertTrue(pom.validarConcurrenteFinalizado(),"No se muestra el concurrente finalizado");
		
    	/******************************** PASO 10 ***************************************/
		addStep("Dar clic en Visualizar Registro");
		p.clickVisualizarRegistro();
		assertTrue(pom.validarRegistro(),"No se muestra el concurrente finalizado");
		p.scrolldown();
		pom.screenshot();
		p.closeActiveWindow();
		u.hardWait(7);	
		p.closeWindow();
	
		/******************************** PASO 11 ***************************************/
		addStep("Validar en la tabla xxfc.xxfc_cfd_factura_digital_error que se muestre el error:\r\n"
				+ "\r\n"
				+ "select * from xxfc.xxfc_cfd_factura_digital_error;");
		SQLResult consultaerror = dbEBS.executeQuery(tablaerror);
		testCase.addQueryEvidenceCurrentStep(consultaerror);
		u.hardWait(5);
		p.closeWindow();
		sk.screen.type(Key.ENTER);
		u.quit();
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_005_EjecucionXXFCFacturacionDigitalClientesOXXOErrorElRFCdelClientenoesValidadebetenerMaximo13Caracteres_test";
	}

	@Override
	public String setTestDescription() {
		return "Ejecución de XXFC - Facturacion Digital Clientes OXXO con factura tenga un importe diferente a fin de revisar que se lance el error.";
	}

	@Override
	public String setTestDesigner() {
		return "priscilaestefaniam@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1.MXOXXO-AR17ADM-ADMON-COMERCIAL\r\n"
				+ "2. Contar con documentos pendientes por timbrar.\r\n"
				+ "3. Tener configurada la factura para lanzar el error.";
	}

	@Override
	public void beforeTest() {

	}
}
