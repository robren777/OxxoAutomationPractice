package EBS.neteos.NeteosReporteHistorialDeFacturacionSNyCorresponsalias;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

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
 * CHG0053977 - HU10 / MVP2
 * MTC-FT-003	Ejecución XXFC - Ejecución XXFC - Reporte Historial de Facturacion por Cliente de SN y Corresponsalias - Servicios Nacionales,Tipo Comisión %
 * 
 * PRERREQUISITOS: 
 *	1. Contar con la responsabilidad "MXOXXO-AR17ADM-Consultas_ADMON_COM_OS".
 *  2. Contar con registros de remisiones de servicio corresponsalías.
 *
 * Ejecución correcta del reporte XXFC - Reporte Historial de Facturacion por Cliente de SN y Corresponsalias que al lanzarlo con un servicio de 
 * servicios nacionales muestre el tipo de comisión %, las nuevas columnas con el detalle de comisiones Comisión socio, Comisión Banco y (Comisión cliente vacío).
 * @author ernestoh@hexaware.com
 */

public class ATC_FT_003_ReporteHistorialDeFacturacionSNyCorresponsalias extends BaseExecution {
 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_ReporteHistorialDeFacturacionSNyCorresponsalias_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String nombreSolicitud = data.get("nombreSolicitud");
		String rfc= data.get("rfc");
		String periodo= data.get("periodo");
		String plaza= data.get("plaza");
		String fechaInicio= data.get("fechaInicio");
		String fechaFin= data.get("fechaFin");
		String servicio= data.get("servicio");
		
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
		addStep("Ingresamos a la responsabilidad MXOXXO-AR17ADM-Consultas_ADMON_COM_OS");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		testCase.addScreenShotCurrentStep(" ");
		
		// PASO 6
		addStep("Seleccionar la opcion: Recibos -> Receipts");
		u.hardWait(4);
		paginaInicial.clickCarpetaRecibos();
		u.hardWait(4);
		testCase.addScreenShotCurrentStep(" ");
		paginaInicial.clickReceiptsRecibos();
		u.hardWait(15);
		p.runApplet3();

		// PASO 7
		addStep("Consultar XXFC - Reporte Historial de Facturacion por Cliente de SN y Corresponsalias");
		p.clickMenuVerSolicitudes();
		p.selectNewRequestAndSearchRequestByName(nombreSolicitud);
		testCase.addScreenShotCurrentStep(" ");
	
		// PASO 8
		addStep("Ingresar los parametros RFC y Periodo");
		p.inputParametersForReportRegistroRecibosAplicados(rfc, periodo, plaza, fechaInicio, fechaFin, servicio);
		testCase.addScreenShotCurrentStep(" ");
		p.nEnter(1);
		u.hardWait(15);

		//PASO 9
		addStep("Ejecutar");
		p.tabEnterHardWait(5, 1, 15);
		testCase.addScreenShotCurrentStep(" ");
		p.tabEnterHardWait(1, 1, 15);
			
		//PASO 10
		addStep("Validar la correcta ejecucion del reporte ");
		p.tabEnterHardWait(7, 1, 15);
		testCase.addScreenShotCurrentStep(" ");
		
		//PASO 11
		addStep("Validar la salida en formato excel");
		p.downloadOutputAndViewLog();
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(" ");
		
		u.hardWait(100);
		String pathReport = pathSystem + "\\Downloads\\XXFC___Reporte_Historial_de_Fa_";
		String date = Excel.getFormattedDate();
		String inputFileName = pathReport + date + ".xls";
		
		System.out.println("ANTES DE LEER EL ARCHIVO "+inputFileName);		
		
		//VALIDAR EXCEL DESCARGADO	
		assertEquals(true, Excel.searchDataExcel(inputFileName, "API6609273E0-1"));
		assertEquals(true, Excel.searchDataExcel(inputFileName, "%"));
				
		u.hardWait(10);			
		p.cicloForWithTabAndWait(16, 1, 10);
		
		p.cicloForWithTabAndWait(2, 1, 1);
		addStep("Validar el reporte descargado de excel");
		u.hardWait(10);
		testCase.addScreenShotCurrentStep(" ");
		p.nRight(17);
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
		return "ATC_FT_003_ReporteHistorialDeFacturacionSNyCorresponsalias_test";
	}

	@Override
	public String setTestDescription() {
		return "Ejecución correcta del reporte XXFC - Reporte Historial de Facturacion por Cliente de SN y Corresponsalias que al lanzarlo con un servicio de servicios nacionales muestre el tipo de comisión %, las nuevas columnas con el detalle de comisiones Comisión socio, Comisión Banco y (Comisión cliente vacío).";
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
		return "1. Contar con la responsabilidad \"MXOXXO-AR17ADM-Consultas_ADMON_COM_OS\".\r\n"
				+ "2. Contar con registros de remisiones de servicio corresponsalías.";
	}

	@Override
	public void beforeTest() {

	}
}