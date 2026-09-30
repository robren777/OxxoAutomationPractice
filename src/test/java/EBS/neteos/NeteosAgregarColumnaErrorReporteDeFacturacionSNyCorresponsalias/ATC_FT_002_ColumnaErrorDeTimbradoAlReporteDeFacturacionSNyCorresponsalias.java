package EBS.neteos.NeteosAgregarColumnaErrorReporteDeFacturacionSNyCorresponsalias;

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
 *  * CHG0052618 - HU12 / MVP2	
 * MTC-FT-002	Ejecución XXFC - Reporte de Facturacion SN y Corresponsalias - Columna de Error.
 * 
 * PRERREQUISITOS: 
 *	1. Contar con la responsabilidad "MXOXXO-AR17ADM-ADMON-COMERCIAL_ADMON_COM_OS".
 *  2.  Haber ejecutado correctamente el MTC-FT-001.
 *  3. Tener configurado el correo para en la sucursal del cliente a timbrar..
 *
 * Validar reporte XXFC - Reporte de Facturacion SN y Corresponsalias muestre mensaje de error en la nueva columna de ERROR DE TIMBRADO.
 * @author ernestoh@hexaware.com
 */

public class ATC_FT_002_ColumnaErrorDeTimbradoAlReporteDeFacturacionSNyCorresponsalias extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_ColumnaErrorDeTimbradoAlReporteDeFacturacionSNyCorresponsalias_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String nombreSolicitud = data.get("nombreSolicitud");
		String startDate= data.get("fechaInicio");
		String endDate = data.get("fechaFinal");
		String rfc = data.get("rfc");
		String razonSocial = data.get("razonSocial");
		
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
		paginaInicial.clickReceiptsRecibos();
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(15);
		p.runApplet3();
			
		// PASO 7
		addStep("Consultar XXFC-Clientes OXXO e ING Locales Facturacion Digital");
		p.clickMenuVerSolicitudes();
		p.selectNewRequestAndSearchRequestByName(nombreSolicitud);
		testCase.addScreenShotCurrentStep(" ");
	
		// PASO 8
		addStep("Ingresar el nombre del concurrente  XXFC - Reporte de Facturacion SN y Corresponsalias y se ingresan los parametros Fecha Inicio, Fecha Fin y Rfc");
		p.inputParametersForReportFacturacionSNAndCorresponsalias(startDate, endDate, razonSocial, rfc);
		p.nEnter(1);
		testCase.addScreenShotCurrentStep(" ");
		//p.nEnter(1);
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
		addStep("Validar la correcta ejecucion del reporte XXFC - Reporte de Facturacion SN y Corresponsalias");
		p.nTAB(7);
		p.nEnter(1);
		u.hardWait(15);
		testCase.addScreenShotCurrentStep(" ");
		
		//PASO 11
		addStep("Validar la salida en formato excel");
		p.downloadOutputAndViewLog();
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(" ");

		u.hardWait(10);
		String pathReport = pathSystem + "\\Downloads\\XXFC___Reporte_de_Facturacion__";
		String date = Excel.getFormattedDate();
		String inputFileName = pathReport + date + ".xls";
		
		System.out.println("ANTES DE LEER EL ARCHIVO "+inputFileName);		
		//VALIDAR EXCEL DESCARGADO											
		assertEquals(true, Excel.searchDataExcel(inputFileName, "ERROR TIMBRADO"));
		//VALIDA QUE MUESTRE MENSAJE DE ERROR EN TIMBRADO
		assertEquals(true, Excel.searchDataExcel(inputFileName, "Error, El RFC API6609273E0-1 del Cliente Autobuses de la Piedad S.A. de C.V. no es Validae"));
		
		u.hardWait(10);									
		for(int i=0;i<13;i++) {
			sk.screen.type(Key.TAB);
			}	
		sk.screen.type(Key.ENTER);
		u.hardWait(10);
		testCase.addScreenShotCurrentStep(" ");
		for(int i=0;i<30;i++) {
			sk.screen.type(Key.RIGHT);
			}
		addStep("Validar que el reporte muestre la nueva columna de ERROR DE TIMBRADO");
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
		return "ATC_FT_002_ColumnaErrorDeTimbradoAlReporteDeFacturacionSNyCorresponsalias_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar reporte XXFC - Reporte de Facturacion SN y Corresponsalias muestre mensaje de error en la nueva columna de ERROR DE TIMBRADO.";
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
		return "1. Contar con la responsabilidad \"MXOXXO-AR17ADM-ADMON-COMERCIAL_ADMON_COM_OS\".\r\n"
				+ "2.  Haber ejecutado correctamente el MTC-FT-001.\r\n"
				+ "3. Tener configurado el correo para en la sucursal del cliente a timbrar.";
	}

	@Override
	public void beforeTest() {

	}
}