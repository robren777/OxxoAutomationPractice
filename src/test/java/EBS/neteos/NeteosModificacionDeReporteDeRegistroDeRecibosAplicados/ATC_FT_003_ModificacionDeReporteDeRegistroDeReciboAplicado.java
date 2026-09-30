package EBS.neteos.NeteosModificacionDeReporteDeRegistroDeRecibosAplicados;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.util.locales.IO.Excel;
import EBS.util.neteos.LoginNeteos.ClaseLogin;
import EBS.util.neteos.PaginaInicialNeteos.EBSPaginaInicial;
import EBS.util.neteos.SikuliNeteos.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
/**
 * CHG0053640 - HU04 / MVP2
 * MTC-FT-002	Ejecución correcta del concurrente  XXFC - Registro Recibos Aplicados por numero de cliente y salida en formato excel
 * 
 * PRERREQUISITOS: 
 *	1. Contar con la responsabilidad "MXOXXO-AR17ADM-AJUST-RECIBOS_ADTIVO_COM_OS".
 *
 * Validar la ejecución del concurrente  XXFC - Registro Recibos Aplicados por numero de cliente y que la salida sea en formato excel
 * @author ernestoh@hexaware.com
 */

public class ATC_FT_003_ModificacionDeReporteDeRegistroDeReciboAplicado extends BaseExecution {
 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_ModificacionDeReporteDeRegistroDeReciboAplicado_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String nombreSolicitud = data.get("nombreSolicitud");
		String fechaContableDeAplicacionDesde= data.get("fechaContableDeAplicacionDesde");
		String fechaContableDeAplicacionHasta= data.get("fechaContableDeAplicacionHasta");
		String nombreCliente= data.get("nombreCliente");
		String numeroCliente= data.get("numeroCliente");
		String numeroDeRecibo= data.get("numeroDeRecibo");
		String fechaAplicacionReciboDesde= data.get("numeroDeRecibo");
		String fechaAplicacionReciboHasta= data.get("fechaAplicacionReciboHasta");
		String metodoDeRecibo= data.get("metodoDeRecibo");
		
		String pathSystem = System.getProperty("user.home");
			
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
		System.out.println("ANTES LOGIN");
		inicio.login();		
		System.out.println("DESPUES LOGIN");	
		
		// PASO 5
		addStep("Ingresamos a la responsabilidad MXOXXO-AR17ADM-AJUSTES-RECIBOS_ADTIVO_OS");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		testCase.addScreenShotCurrentStep(" ");
		
		// PASO 6
		addStep("Seleccionar la opcion: Recibos");
		u.hardWait(4);
		paginaInicial.clickRecibos();
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(15);
		p.runApplet3();
			
		// PASO 7
		addStep("Consultar XXFC - Registro Recibos Aplicados");
		p.clickMenuVerSolicitudes();
		p.selectNewRequestAndSearchRequestByName(nombreSolicitud);
		testCase.addScreenShotCurrentStep(" ");
	
		// PASO 8
		addStep("Ingresar el nombre del concurrente  XXFC - Registro Recibos Aplicados y se ingresan los parametros Nombre Cliente");
		p.inputParametersForReportRegistroRecibosAplicados(fechaContableDeAplicacionDesde, fechaContableDeAplicacionHasta, nombreCliente, numeroCliente, numeroDeRecibo, fechaAplicacionReciboDesde, fechaAplicacionReciboHasta, metodoDeRecibo);
		testCase.addScreenShotCurrentStep(" ");
		p.nEnter(1);
		u.hardWait(15);

		//PASO 9
		addStep("Ejecutar");
		p.nTAB(5);
		p.nEnter(1);
		u.hardWait(15);
		testCase.addScreenShotCurrentStep(" ");
		p.nTAB(1);
		p.nEnter(1);
		u.hardWait(15);
			
		//PASO 10
		addStep("Validar la correcta ejecucion del reporte ");
		p.nTAB(7);
		p.nEnter(1);
		u.hardWait(15);
		testCase.addScreenShotCurrentStep(" ");
		
		//PASO 11
		addStep("Validar la salida en formato excel");
		p.downloadOutputAndViewLog();
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(" ");
		
		u.hardWait(100);
		String pathReport = pathSystem + "\\Downloads\\XXFC___Registro_Recibos_Aplica_";
		String date = Excel.getFormattedDate();
		String inputFileName = pathReport + date + ".xls";
		
		System.out.println("ANTES DE LEER EL ARCHIVO "+inputFileName);		
		
		//VALIDAR EXCEL DESCARGADO											
		assertEquals(true, Excel.searchDataExcel(inputFileName, "COMPAÑIA"));
		assertEquals(true, Excel.searchDataExcel(inputFileName, "DIVISA RECIBO"));
		assertEquals(true, Excel.searchDataExcel(inputFileName, "NUMERO DE RECIBO"));
		assertEquals(true, Excel.searchDataExcel(inputFileName, "NUMERO CLIENTE"));
		assertEquals(true, Excel.searchDataExcel(inputFileName, "CLIENTE"));
		assertEquals(true, Excel.searchDataExcel(inputFileName, "LOTE"));
		assertEquals(true, Excel.searchDataExcel(inputFileName, "FECHA APLICACION"));
		assertEquals(true, Excel.searchDataExcel(inputFileName, "CLIENTE RELACIONADO"));
		assertEquals(true, Excel.searchDataExcel(inputFileName, "NUMERO TRANSACCION"));
		assertEquals(true, Excel.searchDataExcel(inputFileName, "METODO DE RECIBO"));
		assertEquals(true, Excel.searchDataExcel(inputFileName, "DIVISA DE TRANSACCION"));
		assertEquals(true, Excel.searchDataExcel(inputFileName, "IMPORTE APLICADO EN DIVISA DE TRANSACCION"));
		assertEquals(true, Excel.searchDataExcel(inputFileName, "IMPORTE DE RECIBO ASIGNADO"));
		assertEquals(true, Excel.searchDataExcel(inputFileName, "DESCUENTO DEVENGADO"));
		assertEquals(true, Excel.searchDataExcel(inputFileName, "DESCUENTO NO DEVENGADO"));
		assertEquals(true, Excel.searchDataExcel(inputFileName, "IMPORTE TOTAL EN DIVISA FUNCIONAL"));
		//VALIDA EL VALOR DE BUSQUEDA
		assertEquals(true, Excel.searchDataExcel(inputFileName, "BON9206241N5"));
				
		u.hardWait(10);									
		for(int i=0;i<13;i++) {
			sk.screen.type(Key.TAB);
			}	
		sk.screen.type(Key.ENTER);
		u.hardWait(10);
		
		for(int i=0;i<2;i++) {
			sk.screen.type(Key.TAB);
			}
		sk.screen.type(Key.ENTER);
		addStep("Validar el reporte descargado de excel");
		u.hardWait(10);
		testCase.addScreenShotCurrentStep(" ");
		
		//CIERRA DRIVER GENERADO
		u.hardWait(3);
        String cerrarExcel = "TaskKill /im excel.exe /f";
        Runtime.getRuntime().exec(cerrarExcel);
		u.quit();
        String cerrarEdge = "TaskKill /im \"msedge.exe\" /f";
        Runtime.getRuntime().exec(cerrarEdge);
		sk.closeApp("Oracle Applications - AVEBQA");
		u.hardWait(5);
		p.nEnter(1);
		
		//ELIMNA ARCHIVO DESCARGADO PARA FUTURA EJECUCION
		Excel.deleteFile(inputFileName);
		
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_ModificacionDeReporteDeRegistroDeReciboAplicado_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar la ejecución del concurrente  XXFC - Registro Recibos Aplicados por numero de cliente y que la salida sea en formato excel";
	}

	@Override
	public String setTestDesigner() {
		return "ernestoh@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1. Contar con la responsabilidad \"MXOXXO-AR17ADM-AJUST-RECIBOS_ADTIVO_COM_OS\".";
	}

	@Override
	public void beforeTest() {

	}
}