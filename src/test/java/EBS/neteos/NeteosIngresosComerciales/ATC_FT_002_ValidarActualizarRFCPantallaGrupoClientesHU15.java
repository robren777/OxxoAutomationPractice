package EBS.neteos.NeteosIngresosComerciales;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

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
 *                    Haber ejecutado el caso: ATC_FT_001_ValidarAltaExitosaRFCPantallaGrupoClientesHU15
 *                 
 *
 * DESCRIPCION: 
 * Validar permita actualizar RFC en la pantalla de Grupo de Clientes
 * 
 * @author ricardor4@hexaware.com
 */
public class ATC_FT_002_ValidarActualizarRFCPantallaGrupoClientesHU15 extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_ValidarActualizarRFCPantallaGrupoClientesHU15_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String grupo = data.get("grupo");
		String servicio = data.get("servicio");
		String rfc = data.get("rfc");
		
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
					
					throw new AssertionError("Error al ingresar responsabilidad" + e);
				
				}
		
		testCase.addScreenShotCurrentStep(" ");
		
		// PASO 6
		addStep("Ingresar a la opcion: Frecuencia Facturacion SN / Corresponsalias.");
		
		 try {
				
			 paginaInicial.clickFrecuenciaFacturacionSNCorresponsalias();
				
				}
				catch (Exception e) {
					
					throw new AssertionError("Error al invocar metodo" + e);
				
				}
		
		testCase.addScreenShotCurrentStep(" ");
		// PASO 7
	    addStep("Ingresar Grupo y Servicio");
	    
	    try {
			
		    paginaInicial.seleccionargrupo(grupo);
		    testCase.addScreenShotCurrentStep(" ");
		    u.hardWait(5);
		    paginaInicial.seleccionarservicio(servicio);
		    testCase.addScreenShotCurrentStep(" ");
				
				}
				catch (Exception e) {
					
					throw new AssertionError("Error al ingresar parametros" + e);
				
				}
	    
		// PASO 8
	    addStep("Dar clic en el boton de Ir");
		 try {
				
			 paginaInicial.clickIrSNC();
				
				}
				catch (Exception e) {
					
					throw new AssertionError("Error al invocar metodo" + e);
				
				}
	    
	    testCase.addScreenShotCurrentStep(" ");
	    // PASO 9
	    addStep("Dar clic en Ver Items");
	    
	    try {
			
	    	paginaInicial.clickbuscarItemsAdtivos();
				
				}
				catch (Exception e) {
					
					throw new AssertionError("Error al invocar metodo" + e);
				
				}
	    
	    testCase.addScreenShotCurrentStep(" ");
	    u.hardWait(6);
	    // PASO 10
	    addStep("Dar clic en Actualizar Item de la sección de items.");
	    try {
			
	    	 paginaInicial.clickActualizarSNC();
				
				}
				catch (Exception e) {
					
					throw new AssertionError("Error al invocar metodo" + e);
				
				}
	    
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep(" ");
	    // PASO 11
	    addStep("Ingresar RFC");
	    try {
			
	    	 paginaInicial.seleccionarrfc2(rfc);
				
				}
				catch (Exception e) {
					
					throw new AssertionError("Error al invocar metodo" + e);
				
				}
	    
	    testCase.addScreenShotCurrentStep(" ");
	    p.nTAB(1);
	    u.hardWait(5);
	    // PASO 12
	    addStep("Dar clic en Aplicar");
	    try {
			
		    paginaInicial.clickAplicar2();
				
				}
				catch (Exception e) {
					
					throw new AssertionError("Error al invocar metodo" + e);
				
				}
	    
	    testCase.addScreenShotCurrentStep(" ");
	    u.hardWait(7);
	    // PASO 13
	    addStep("Dar clic en Ok");
	    u.hardWait(5);
	    testCase.addScreenShotCurrentStep(" ");
	    // PASO 14
	    addStep("Validar el RFC fue actualizado, dar clic en Ver Items");
	    assertTrue(p.validarMensajeActualizacion());
	    testCase.addScreenShotCurrentStep(" ");
	    p.nEnter(1);
	    u.hardWait(5);
		u.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_ValidarActualizarRFCPantallaGrupoClientesHU15_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar permita actualizar RFC en la pantalla de Grupo de Clientes";
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
				+ "Haber ejecutado el caso: ATC_FT_001_ValidarAltaExitosaRFCPantallaGrupoClientesHU15";
		
	}

	@Override
	public void beforeTest() {

	}
}