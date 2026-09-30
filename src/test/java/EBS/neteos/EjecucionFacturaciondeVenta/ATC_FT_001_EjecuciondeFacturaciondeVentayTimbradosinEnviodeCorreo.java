package EBS.neteos.EjecucionFacturaciondeVenta;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.neteos.NeteosCedisMVP2.om.pomNeteos;
import EBS.util.neteos.ModelosLoginNeteos.ModeloLoginEBS;
import EBS.util.neteos.PaginaInicialNeteos.EBSPaginaInicial;
import EBS.util.neteos.SikuliNeteos.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
/**
 * PRERREQUISITOS: 
 * 1. Contar con la responsabilidad "MXOXXO-AR17ADM-Consultas_ADMON_COM_OS".
 * 2. Tener remisiones pendientes por facturar.
 * 
 * @author priscilaestefaniam@hexaware.com
 */
public class ATC_FT_001_EjecuciondeFacturaciondeVentayTimbradosinEnviodeCorreo extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_EjecuciondeFacturaciondeVentayTimbradosinEnviodeCorreo_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String concurrente = data.get("rfc"); 
		String fecha1 = data.get("fecha1");
		String fecha2 = data.get("fecha2");
		String concepfactura = data.get("concepfactura");
		String imprimir = data.get("imprimir");
		String enviarcorreo = data.get("enviarcorreo");
		

		
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
		paginaInicial.clickCarpetaInformes();
		paginaInicial.clickOtrosInformes();
		u.hardWait(8);
		p.runApplet3();
		u.hardWait(60);
		sk.screen.type(Key.TAB);
		sk.screen.type(Key.TAB);
		sk.screen.type(Key.ENTER);
		
		// ******************************** PASO 6 ****************************************/
		addStep("Dirigirse al menu Ver (x): Solicitudes -> Ejecutar Nueva Solicitud.");		
		u.hardWait(5);
		p.vers();
		pom.screenshot();
		p.clickEjecutarNuevaSolicitud();
		assertTrue(pom.validarPantallaNeuvaSolicitud(),"No se encontro la pantalla de solicitud");
		
		/******************************** PASO 7 *****************************************/
		addStep("Buscar el proceso \"XXFC - Generacion de Factura de Venta Servicios Nacionales y Corresponsalias\".");
		p.enterText(concurrente);
		u.hardWait(6);
		pom.screenshot();
		assertTrue(pom.validarPantallaParametros(),"No se encontro la pantalla para agregar parametros");
		
		/******************************** PASO 8 ****************************************/
		addStep("Ingresar en los parametros");
		u.hardWait(8);
	    p.ingresarParametrosFacdeVenta(pathImg, fecha1, fecha2, concepfactura, imprimir, enviarcorreo);
		u.hardWait(8);
						
		/******************************** PASO 9 ****************************************/
		addStep("Dar clic en Aceptar y Ejecutar.");
		p.clickEjecutar();
		u.hardWait(10);
	    pom.screenshot();
		p.clickNo();
     	u.hardWait(8);
     	p.vers();
		u.hardWait(40);
		p.clickEncontrar();
		assertTrue(pom.validarPantallaEncontrarSolicitudes(),"No se muestra la pantalla Encontrar Solicitudes");
	

		/******************************** PASO 10 ****************************************/
		addStep("Validar el proceso XXFC - Generacion de Factura de Venta Servicios Nacionales y Corresponsalias finalice sin errores.");
		/******************************** PASO 11 ****************************************/
	   	addStep("Validar el proceso XXFC - Facturacion Digital Clientes OXXO finalice sin errores.");
	   	u.hardWait(20);
	   	assertTrue(pom.validarConcurrenteFinalizado(),"No se muestra el concurrente finalizado");
	   	sk.screen.type(Key.DOWN);
	   	sk.screen.type(Key.DOWN);
	   	sk.screen.type(Key.DOWN);
	   	u.hardWait(5);
	   	p.clickVisualizarDetalles();
	   	u.hardWait(5);
	   	pom.screenshot();
	   	p.closeActiveWindow();
	  
	  /******************************** PASO 12 ***************************************/
		addStep("Validar Log y Output del proceso XXFC - Generacion de Factura de Venta Servicios Nacionales y Corresponsalias.");
		p.clickVisualizarRegistro();
		assertTrue(pom.validarRegistro(),"No se muestra el concurrente finalizado");
		p.closeActiveWindow();
		u.hardWait(7);
		p.clickVisualizarOutput();
		assertTrue(pom.clickVisualizaOutput(),"Error al dar click en el boton visualiza Output");		
		p.descargarExcel();
		p.closeActiveWindow();
	
		 /******************************** PASO 12 ***************************************/
		addStep("Validar Log del proceso XXFC - Facturacion Digital Clientes OXXO.");
		
	}
			
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_001_EjecucionXXFCReporteRemisionesARSNyCanivelTiendaSNPendienteporFacturar_test";
	}

	@Override
	public String setTestDescription() {
		return "Ejecucion correcta del reporte XXFC - Reporte Remisiones AR SNyC a nivel Tienda para Corresponsalias con estatus de Facturado. El reporte con salida Excel debe integrar la combinación contable de ingresos y contener las siguientes columnas:\r\n"
				+ "COBRADOR\r\n"
				+ "RFC\r\n"
				+ "PLAZA\r\n"
				+ "COMISIONSINIVA\r\n"
				+ "IVACOMISION\r\n"
				+ "COMISION_MPE_SIN_IVA\r\n"
				+ "IVA_COMISION_MPE\r\n"
				+ "*Solo debe incluir las remisiones que no estan facturadas en AR.";
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
		return "1. Contar con la responsabilidad \"MXOXXO-AR17ADM-Consultas_ADMON_COM_OS\"\r\n"
				+ "2. Tener remisiones pendientes por facturar.";
	}

	@Override
	public void beforeTest() {

	}
}
