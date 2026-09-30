package EBS.neteos.NeteosIngresosComerciales;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.util.neteos.LoginNeteos.ClaseLogin;
import EBS.util.neteos.PaginaInicialNeteos.EBSPaginaInicial;
import EBS.util.neteos.SikuliNeteos.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
/**
 * PRERREQUISITOS: 
 * 1. Haber ejecutado el timbrado previamente MTC-FT-002
 * 2. Con destinatarios configurados con el propósito Facturacion ServNac.
 * 
 *
 * DESCRIPCION: Validar el reenvío PDF y XML de la factura MTC-FT-002  para los destinatarios 
				configurados con el propósito Facturacion ServNac a nivel sucursal
 * 	
 * @author ricardor4@hexaware.com
 */

public class ATC_FT_001_ValidarprocesoXXFCFacturacionDigitalClientesOXXOSNcorreo extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ValidarprocesoXXFCFacturacionDigitalClientesOXXOSNcorreo_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		

		String concurrente = data.get("concurrente");
		String tipo = data.get("tipo");
		String inferior = data.get("inferior");
		String superior = data.get("superior");
		String reimprimir = data.get("reimprimir");

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		ClaseLogin inicio = new ClaseLogin(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"NETEOS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);

		PatternUtil p = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		/*
		 * PASOS DEL CASO DE PRUEBA
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
		addStep("Seleccionar la responsabilidad MXOXXO-AR17ADM-ADMON-COMERCIAL_ADMON_COM_OS -> Informes -> Otros.");
		
	    try {
			
	    	paginaInicial.clickOnResponsabilidad(responsabilidad);
					
					}
					catch (Exception e) {
						
						throw new AssertionError("Error al invocar responsabilidad" + e);
					
					}
		
		testCase.addScreenShotCurrentStep(" ");
		paginaInicial.clickInformesOtros();
		u.hardWait(6);
		p.runApplet();
		u.hardWait(60);
		// PASO 6
		addStep("Buscar el proceso XXFC - Facturacion Digital Clientes OXXO");
		
	    try {
			
			p.nEnter(1);
			testCase.addScreenShotCurrentStep(" ");
			u.hardWait(20);
			p.enterText(concurrente);
			testCase.addScreenShotCurrentStep(" ");
			u.hardWait(10);
			sk.screen.type(Key.DELETE);
			u.hardWait(3);
			sk.screen.type(tipo);
			p.nTAB(1);
			u.hardWait(3);
			sk.screen.type(inferior);
			p.nTAB(1);
			u.hardWait(3);
			sk.screen.type(superior);
			p.nTAB(1);
			u.hardWait(3);
			p.nTAB(3);
			sk.screen.type(reimprimir);
			u.hardWait(4);
			p.nTAB(1);
			u.hardWait(4);
			p.nEnter(1);
			u.hardWait(4);
			p.clickEjecutar();
			u.hardWait(60);
			testCase.addScreenShotCurrentStep(" ");
					
					}
					catch (Exception e) {
						
						throw new AssertionError("Error al ingresar parametros" + e);
					
					}
		

		p.clickNo();
		// PASO 7
		addStep("Validar el concurrente XXFC - Facturacion Digital Clientes OXXO finaliza correctamente");
		p.vers();
		u.hardWait(20);
		p.clickEncontrar();
		testCase.addScreenShotCurrentStep(" ");
		 u.hardWait(30);
		    assertTrue(p.validarEstadoFinal());
		    u.hardWait(20);
		// PASO 8
		addStep("Dar clic en Visualizar Output");
		
	    try {
			
	    	p.clickVisualizarOutput();
					
					}
					catch (Exception e) {
						
						throw new AssertionError("Error al invocar metodo" + e);
					
					}
		
		
		u.hardWait(20);
		testCase.addScreenShotCurrentStep(" ");
		u.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ValidarprocesoXXFCFacturacionDigitalClientesOXXOSNcorreo_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar el reenvío PDF y XML de la factura MTC-FT-002  para los destinatarios "
				+ "configurados con el propósito Facturacion ServNac a nivel sucursal.";
	}

	@Override
	public String setTestDesigner() {
		return "ricardor4@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1. Haber ejecutado el timbrado previamente MTC-FT-002"
				+ "2. Con destinatarios configurados con el propósito Facturacion ServNac.";
	}

	@Override
	public void beforeTest() {

	}
}