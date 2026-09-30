package EBS.neteos.NeteosServiciosCorresponsalias;

import static org.testng.Assert.assertEquals;
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
 * PRERREQUISITOS: 1. Acceso a la responsabilidad MXOXXO-AR17ADM-Usuario_ADTIVO_OS
 *                 2. Contar con Remisiones de Corresponsalias
 *
 * DESCRIPCION: 
 * Validar se genera la factura de Venta correctamente para Corresponsalias con su nuevo tipo de transaccion FAC_Correspon_17ADM
 * 
 * @author ricardor4@hexaware.com
 */
public class ATC_FT_001_XXFCGeneracionFacturaVentaServiciosNacionalesCorresponsaliascorresponsaliasBBVAHU48 extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_XXFCGeneracionFacturaVentaServiciosNacionalesCorresponsaliascorresponsaliasBBVAHU48_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String concurrente = data.get("concurrente");
		String rfc = data.get("rfc");
		String fecha1 = data.get("fecha1");
		String fecha2 = data.get("fecha2");
		String encuentraError = "error";
		
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
		addStep("Ingresar en la responsabilidad MXOXXO-AR17ADM-Usuario_ADTIVO_OS");
	    try {
			
	    	paginaInicial.clickOnResponsabilidad(responsabilidad);
					
					}
					catch (Exception e) {
						
						throw new AssertionError("Error al invocar responsabilidad" + e);
					
					}
		
		addStep("Seleccionar la opcion Informes -> Otros.");
		paginaInicial.clickInformesOtros();
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(4);
		p.runApplet();
		u.hardWait(60);
		// PASO 6
		addStep("Cerrar ventana Ejecutar una Nueva Solicitud y dirigirse al menu: Ver (x) -> Solicitudes -> Ejecutar Nueva Solicitud");
		p.closeActiveWindow();
		u.hardWait(6);
		p.vers();
		u.hardWait(4);
		
	    try {
			
	    	p.clickEjecutarNuevaSolicitud();
	    	testCase.addScreenShotCurrentStep(" ");
					}
					catch (Exception e) {
						
						throw new AssertionError("Error al invocar metodo" + e);
					
					}
		
		u.hardWait(4);
		// PASO 7
	    addStep("Ingresar el proceso XXFC - Generacion de Factura de Venta Servicios Nacionales y Corresponsalias");
	    
	    try {
			
	    	p.enterText(concurrente);
	    	testCase.addScreenShotCurrentStep(" ");
					}
					catch (Exception e) {
						
						throw new AssertionError("Error al ingresar concurrente" + e);
					
					}
	    
	    testCase.addScreenShotCurrentStep(" ");
	    u.hardWait(8);
	    // PASO 8
	    addStep("Ingresar los parametros:"
	    		+ "RFC: BBA830831LJ2"
	    		+ "Fecha inicio: 20-DIC-2023"
	    		+ "Fecha Fin: 20-DIC-2023");
	    
	    
	    try {
			
		    sk.screen.type(rfc);
			p.nTAB(1);
			sk.screen.type(fecha1);
			u.hardWait(5);
			sk.screen.type(fecha2);
			testCase.addScreenShotCurrentStep(" ");
			u.hardWait(5);
			p.nTAB(2);
		    p.nEnter(1);
					
					}
					catch (Exception e) {
						
						throw new AssertionError("Error al ingresar parametros" + e);
					
					}
	    

		// PASO 9
	    addStep("Dar clic en Aceptar y Ejecutar");
	    u.hardWait(12);
	    
	    try {
			
	    	p.clickEjecutar();
					
					}
					catch (Exception e) {
						
						throw new AssertionError("Error al invocar metodo" + e);
					
					}
	    
	    u.hardWait(12);
	    p.clickNo();
	    u.hardWait(30);
	    p.vers();
	    testCase.addScreenShotCurrentStep(" ");
		// PASO 10
	    addStep("Dar clic en Encontrar.");
	    u.hardWait(5);
	    testCase.addScreenShotCurrentStep(" ");
	    u.hardWait(60);
	    p.clickEncontrar();
	    p.verificarEstado("ejecutandoVerde", 6000000);
	    assertTrue(p.validarEstadoFinal());
	    u.hardWait(20);
	    sk.screen.type(Key.DOWN);
	    u.hardWait(4);
	    // PASO 11
	    addStep("Validar generación de Factura Venta AR con el nuevo tipo de transacción FAC_Correspon_17ADM.");
	    sk.screen.type("z",Key.ALT);
	    u.hardWait(20);
	    assertEquals("factura generada",p.getTextParaErrores(encuentraError));
	    u.hardWait(30);
	    testCase.addScreenShotCurrentStep(" ");
		u.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_XXFCGeneracionFacturaVentaServiciosNacionalesCorresponsaliascorresponsaliasBBVAHU48_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar se genera la factura de Venta correctamente para Corresponsalias con su nuevo tipo de transaccion FAC_Correspon_17ADM";
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
		return "1. Acceso a la responsabilidad MXOXXO-AR17ADM-Usuario_ADTIVO_OS"
				+ "2. Contar con Remisiones de Corresponsalias.";
	}

	@Override
	public void beforeTest() {

	}
}