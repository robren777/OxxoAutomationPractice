package EBS.neteos.NeteosEliminacionCompensacionSaldos;

import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import EBS.util.locales.IO.Excel;
import EBS.util.locales.Sikuli.CommonMethodsSikuli;
import EBS.util.neteos.DBConnection.OracleDBConnection;
import EBS.util.neteos.LoginNeteos.ClaseLogin;
import EBS.util.neteos.PaginaInicialNeteos.EBSPaginaInicial;
import EBS.util.neteos.SikuliNeteos.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
/**
 * PRERREQUISITOS: Tener informacion para la fecha ingresada en la tabla de XXFC_PAGO_SERVICIOS
 *
 * Validar la ejecución del reporte XXFC - Reporte Detalle de Comisiones Facturadas a nivel Transaccion 
 * que se muestre la informacion para el rango de fecha y rango de rfc ingresado
 * @author ernestoh@hexaware.com
 */

public class ATC_FT_001_ReporteDetalleDeComisionesFacturadasNivelTransaccion extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ReporteDetalleDeComisionesFacturadasNivelTransaccion_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		
		String nombreSolicitud = data.get("nombreSolicitud");
		String startDate= data.get("fechaInical");
		String endDate = data.get("fechaFinal");
		String startRfc = data.get("rfcInicial");
		String endRfc = data.get("rfcFinal");
		String service = data.get("servicio");
		String sendMail = data.get("enviarCorreo");
		String userBD = data.get("userBD");
		String passwordBD = data.get("passwordBD");
		
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
		addStep("Ingresamos a la responsabilidad MXOXXO-AR17ADM-AJUSTE-TRANSACCIONES");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		testCase.addScreenShotCurrentStep(" ");
		
		// PASO 6
		addStep("Seleccionar la opcion: Solicitudes -> Run");
		u.hardWait(4);
		paginaInicial.clickCarpetaSolicitudes();
		u.hardWait(4);
		testCase.addScreenShotCurrentStep(" ");
		paginaInicial.clickRunSolicitudes();
		u.hardWait(30);
		p.runApplet();
		u.hardWait(40);
			
		// PASO 7
		addStep("Consultar Reporte de Conmisiones Facturadas");
		p.closeNewRequestAndClickMenuVerSolicitudes();
		p.selectNewRequestAndSearchRequestByName(nombreSolicitud);
		testCase.addScreenShotCurrentStep(" ");
		
		// PASO 8
		addStep("Ingresar el nombre del concurrente  XXFC - Reporte Detalle de Comisiones Facturadas a nivel Transaccion se ingresan los parametros Fecha Inicio, Fecha Fin, rfc inicial, rfc final, Enviar correo: NO ");
		p.inputParametersForReportDetailsOfCommissionsBilledAtTransactionLevel(startDate, endDate, startRfc, endRfc, service, sendMail);
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
		addStep("Validar la correcta ejecucion del reporte  XXFC - Reporte Detalle de Comisiones Facturadas a nivel Transaccion");
		p.nTAB(7);
		p.nEnter(1);
		u.hardWait(15);
		testCase.addScreenShotCurrentStep(" ");
		
		
		//PASO 11
		addStep("Validar la salida en formato excel");
		p.downloadOutputAndViewLog();
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(" ");
		
		addStep("Compara excel descargado vs consulta a base de datos");
		String pathResultados = pathSystem + "\\Downloads\\resultados.xlsx";
		String sheetNameResultados = "Resultados";
		String pathReport = pathSystem + "\\Downloads\\XXFC___Reporte_Detalle_de_Comi_";

		boolean statusBD = OracleDBConnection.extractingInformationFromTheDatabase_startEndDate_startEndRfc(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA, pathResultados, sheetNameResultados, startDate, endDate, startRfc, endRfc);
		assertTrue(statusBD);
		
		//CAMBIA EL NOMBRE DE LA COLUMNA RAZON_SOCIAL A RAZON SOCIAL, VALORES OBTENIDOS DE LA CONSULTA A BASE DE DATOS
		CommonMethodsSikuli.moveMouseRandomly(1, 1000);
		Map<String, String> cambioNombreColumna = new HashMap<>();
		cambioNombreColumna.put("RAZON_SOCIAL", "RAZON SOCIAL");
		Excel.renameColumns(pathResultados, sheetNameResultados, cambioNombreColumna);	
		
		//SELECCIONA EXCEL DESCARGADO Y COMPARA CON LO OBTENIDO EN LA CONSULTA DE BASE DE DATOS
		String date = Excel.getFormattedDate();
		//MODIFICAR POR FECHA DE SERVIDOR
		String inputFileName = pathReport + date + ".xls";
		assertEquals(true, Excel.validateExcelDate(pathResultados, inputFileName));
		
		String values=Excel.validateExcelDateReturnValues(pathResultados, inputFileName).stream().collect(Collectors.joining(", "));			
		u.hardWait(10);									
		for(int i=0;i<17;i++) {
			sk.screen.type(Key.TAB);
			}	
		sk.screen.type(Key.ENTER);
		u.hardWait(5);
		sk.screen.type(Key.TAB);
		sk.screen.type(Key.TAB);
		sk.screen.type(Key.ENTER);
		u.hardWait(10);
		testCase.addScreenShotCurrentStep(" ");
		testCase.addQueryEvidenceCurrentStep(OracleDBConnection.executeQueryAndReturnFirstData(startDate, endDate, startRfc, endRfc));
		testCase.addQueryEvidenceCurrentStep(OracleDBConnection.executeQueryAndReturnSecoundData(startDate, endDate, startRfc, endRfc));
		testCase.addTextEvidenceCurrentStep(values);

		//CIERRA DRIVER GENERADO
		u.hardWait(3);
        String cerrarExcel = "TaskKill /im excel.exe /f";
        Runtime.getRuntime().exec(cerrarExcel);
		u.quit();
        String cerrarEdge = "TaskKill /im \"msedge.exe\" /f";
        Runtime.getRuntime().exec(cerrarEdge);
		sk.closeApp("Oracle Applications - AVEBQA");
		u.hardWait(5);
		sk.screen.type(Key.ENTER);
		
		//ELIMNA ARCHIVOS DESCARGADOS PARA FUTURA EJECUCION
		Excel.deleteFile(pathResultados);
		Excel.deleteFile(inputFileName);
	
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_011_ReporteDetalleDeComisionesFacturadasNivelTransaccion_RangoDeFecha_RangoRfc_Y_Servicio_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar la ejecución del reporte XXFC - Reporte Detalle de Comisiones Facturadas a nivel Transaccion que se muestre la informacion para el rango de fecha y rango de rfc ingresado";
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
		return "Tener informacion para la fecha ingresada en la tabla de XXFC_PAGO_SERVICIOS";
	}

	@Override
	public void beforeTest() {

	}
}