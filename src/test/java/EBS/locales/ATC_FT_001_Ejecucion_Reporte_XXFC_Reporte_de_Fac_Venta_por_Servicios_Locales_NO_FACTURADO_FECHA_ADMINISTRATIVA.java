package EBS.locales;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import EBS.util.locales.IO.Excel;
import EBS.util.locales.SQL.OracleDBConeection;
import EBS.util.locales.Sikuli.CommonMethodsSikuli;
import EBS.util.neteos.LoginNeteos.ClaseLogin;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;


/**
* DESCRIPCI�N: Clase de utileria para generar reporte de Fcat Venta por Servicios Locales - No Facturado - Con Captura de Fecha Administrativa
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	-MTC-FT-001-Ejecuci�n Reporte XXFC - Reporte de Fac. Venta por Servicios Locales - NO FACTURADO/FECHA ADMINISTRATIVA
* 
* PREREQUISITOS:
* 	- Acceso al portal web EBS
* 	- Usar Microsoft Edge en modo Internet Explorer
* 
* INSUMOS:
* 	- Tener datos de Fac. Venta por Servicios Locales en Base de Datos
* 
* @author Francisco Javier Mendoza Tamayo
* @date 17/Diciembre/2024
*/

public class ATC_FT_001_Ejecucion_Reporte_XXFC_Reporte_de_Fac_Venta_por_Servicios_Locales_NO_FACTURADO_FECHA_ADMINISTRATIVA extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void  ATC_FT_001_Ejecucion_Reporte_XXFC_Reporte_de_Fac_Venta_por_Servicios_Locales_NO_FACTURADO_FECHA_ADMINISTRATIVA_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		By usuarios = By.xpath("//*[@id=\"LIMXOXXO-HELP-DESK\"]/a/div[2]");
		String cambioResponsabilidad = data.get("cambioResponsabilidad");
		String nombreReporte = data.get("nombreReporte");
		String estadoFactura = data.get("estadoFactura");
		String fechaAdministrativaInicial = data.get("fechaAdministrativaInicial");
		String fechaAdministrativaFinal = data.get("fechaAdministrativaFinal");
		String userBD = data.get("userBD");
		String passwordBD = data.get("passwordBD");
		String responsabilidadReporte = data.get("responsabilidadReporte");
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
		utils.sql.SQLUtil 	AVANTE = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
				GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
		PatternUtil p = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		
	
		String path = "\\Patterns\\Locales\\";
		

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
		addStep("Ingresamos a la responsabilidad MXOXXO-HELP-DESK");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		testCase.addScreenShotCurrentStep(" ");
		
		u.hardWait(8);
		// PASO 6
		addStep("Seleccionar la opcion: Usuarios");
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(4);
		u.click(usuarios);
		testCase.addScreenShotCurrentStep(" ");
		// RunApplet
		CommonMethodsSikuli.waitForAndClickImageContinue("checkboxIAcceptTheRiskAndWantToRunThisApplication.png", path, "buttonRun.png");
		// PASO 7
		addStep("Cambiar Responsabilidad");
		boolean statusSeccionUsuarios = CommonMethodsSikuli.waitForImage("seccionUsuarios.png", path);
		assertTrue(statusSeccionUsuarios);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(" ");
		boolean statusCloseSeccionUsuarios = CommonMethodsSikuli.waitForClickOnTheImageOnTheRight("closeSeccionUsuarios3.png", path);
		assertTrue(statusCloseSeccionUsuarios);
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(" ");
		boolean statusCambiarResponsabilidad = CommonMethodsSikuli.waitForAndClickImage("cambiarResponsabilidad.png", path);
		assertTrue(statusCambiarResponsabilidad);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(" ");
		// PASO 8
		addStep("Seleccionar Responsabilidad");
		testCase.addScreenShotCurrentStep(" ");
		boolean optionResponsabilidad = CommonMethodsSikuli.optionResponsabilidad(cambioResponsabilidad,path);
		assertTrue(optionResponsabilidad);
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(" ");
		boolean statusBotonAceptarResponsabilidades = CommonMethodsSikuli.waitForAndClickImage("botonAceptarResponsabilidades.png", path);
		assertTrue(statusBotonAceptarResponsabilidades);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(" ");
		// PASO 9
		addStep("Ir a Solicitudes");
		testCase.addScreenShotCurrentStep(" ");
		boolean statusOptionMenuVer = CommonMethodsSikuli.waitForAndClickImage("optionMenuVer.png", path);
		assertTrue(statusOptionMenuVer);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(" ");
		boolean statusOptionSolicitudes = CommonMethodsSikuli.waitForAndClickImage("optionSolicitudes_Ver.png", path);
		assertTrue(statusOptionSolicitudes);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(" ");
		// PASO 10
		addStep("Ejecutar Solicitud");
		u.hardWait(3);
		boolean statusEjecutarNuevaSolicitud = CommonMethodsSikuli.waitForAndClickImage("EjecutarNuevaSolicitud.png", path);
		assertTrue(statusEjecutarNuevaSolicitud);
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(3);
		boolean statusNombreReporte = CommonMethodsSikuli.waitForAndSendKeysImage("Nombre_EjecutarEstaSolicitud.png", nombreReporte, path);
		assertTrue(statusNombreReporte);
		u.hardWait(3);
		boolean statusTABNombreReporte = CommonMethodsSikuli.waitTABImage("Nombre_EjecutarEstaSolicitud.png", path);
		assertTrue(statusTABNombreReporte);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(" ");
		// PASO 11
		addStep("Capturar Par�metros");
		boolean statusInputEstadoFacturaParametros = CommonMethodsSikuli.waitForAndSendKeysImage("inputEstadoFactura_Parametros.png", estadoFactura, path);
		assertTrue(statusInputEstadoFacturaParametros);
		u.hardWait(3);
		boolean statusInputFechaAdministrativaInicialParametros = CommonMethodsSikuli.waitForAndSendKeysImage("inputFechaAdministrativaInicial_Parametros.png", fechaAdministrativaInicial, path);
		assertTrue(statusInputFechaAdministrativaInicialParametros);
		u.hardWait(3);
		CommonMethodsSikuli.writeCursor(fechaAdministrativaFinal);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(" ");
		boolean statusAceptarParametros = CommonMethodsSikuli.waitForAndClickImage("botonAceptarParametros.png", path);
		assertTrue(statusAceptarParametros);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(" ");
		// PASO 11
		addStep("Ejecutar");
		boolean statusEjecutar = CommonMethodsSikuli.waitForAndClickImage("botonEjecutar.png", path);
		assertTrue(statusEjecutar);
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(3);
		boolean statusOptionNo = CommonMethodsSikuli.waitForAndClickImage("optionNo.png", path);
		assertTrue(statusOptionNo);
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(3);
		// PASO 12
		addStep("Econtrar");
		boolean statusEncontrar = CommonMethodsSikuli.waitForAndClickImage("botonEncontrar.png", path);
		assertTrue(statusEncontrar);
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(3);
		// PASO 13
		addStep("Refrescar Datos");
		boolean statusRefrescarDatos = CommonMethodsSikuli.waitForAndClickImage("botonRefrescarDatos.png", path);
		assertTrue(statusRefrescarDatos);
		u.hardWait(5);
		boolean statusRefrescarDatos2 = CommonMethodsSikuli.waitForAndClickImage("botonRefrescarDatos.png", path);
		assertTrue(statusRefrescarDatos2);
		u.hardWait(5);
		boolean statusRefrescarDatos3 = CommonMethodsSikuli.waitForAndClickImage("botonRefrescarDatos.png", path);
		assertTrue(statusRefrescarDatos3);
		testCase.addScreenShotCurrentStep(" ");
		// PASO 14
		addStep("Visualizar Output");
		boolean statusVisualizarOutput = CommonMethodsSikuli.waitForAndClickImage("botonVisualizarOutput_Solicitudes.png", path);
		assertTrue(statusVisualizarOutput);
		u.hardWait(5);
		
		// PASO 15
		addStep("Extraer informaci�n de Base de Datos");
		String pathResultados = pathSystem + "\\Downloads\\resultados.xlsx";
		String sheetNameResultados = "Resultados";
		String pathReport = pathSystem + "\\Downloads\\XXFC___Reporte_de_Fac__Venta_p_";
		String startDate = Excel.convertDateFormat(fechaAdministrativaInicial);
		String endDate = Excel.convertDateFormat(fechaAdministrativaFinal);
		System.out.println("Fecha Inicial formateada: " + startDate);
		System.out.println("Fecha Final formateada: " + endDate);
		boolean statusBD = OracleDBConeection.extractingInformationFromTheDatabase(userBD, passwordBD, pathResultados, sheetNameResultados, startDate, endDate, responsabilidadReporte);
		assertTrue(statusBD);
		
		CommonMethodsSikuli.moveMouseRandomly(1, 1000);
		Excel.cleanNullTextInColumn(pathResultados, sheetNameResultados, 0);
		CommonMethodsSikuli.moveMouseRandomly(1, 1000);
		Excel.cleanNullTextInColumn(pathResultados, sheetNameResultados, 2);
		CommonMethodsSikuli.moveMouseRandomly(1, 1000);
		Excel.cleanNullTextInColumn(pathResultados, sheetNameResultados, 12);
		CommonMethodsSikuli.moveMouseRandomly(1, 1000);
		Excel.deleteColumn(pathResultados, sheetNameResultados, 13);
		CommonMethodsSikuli.moveMouseRandomly(1, 1000);
		Excel.formatDateColumn(pathResultados, sheetNameResultados, 1, "Fecha Administrativa");
		CommonMethodsSikuli.moveMouseRandomly(1, 1000);
		Excel.formatMonthToUppercase(pathResultados, sheetNameResultados, 1);
		CommonMethodsSikuli.moveMouseRandomly(1, 1000);
		
		
		Excel.formatNumericColumn(pathResultados, sheetNameResultados, 9);
		CommonMethodsSikuli.moveMouseRandomly(1, 1000);
		Excel.addNbspToColumn(pathResultados, sheetNameResultados, 5);
		CommonMethodsSikuli.moveMouseRandomly(1, 1000);
		Excel.deleteFirstRow(pathResultados, sheetNameResultados);
		CommonMethodsSikuli.moveMouseRandomly(1, 1000);
		
		String date = Excel.getFormattedDate();
		String inputFileName = pathReport + date + ".xls";

		boolean statusReportValidate = Excel.validateExcelDate(pathResultados, inputFileName);
		assertTrue(statusReportValidate);
		CommonMethodsSikuli.moveMouseRandomly(1, 1000);
		// PASO 15
		u.hardWait(3);
		addStep("Cerrar Oracle");
		boolean statusJava = CommonMethodsSikuli.waitForAndClickImage("iconoJava.png", path);
		assertTrue(statusJava);
		u.hardWait(3);
		boolean statusCerrarOracle = CommonMethodsSikuli.waitForAndClickImageRightTop("cerrarOracle.png", path);
		assertTrue(statusCerrarOracle);
		u.hardWait(3);
		boolean statusAceptarSalirOracleApplications = CommonMethodsSikuli.waitForAndClickImage("botonAceptarSalirOracleApplications.png", path);
		assertTrue(statusAceptarSalirOracleApplications);
		u.hardWait(3);
		
		
		if (u != null) {
			u.quit();
		}
		
		
		Excel.deleteFile(pathResultados);
		Excel.deleteFile(inputFileName);
		
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_Ejecucion_Reporte_XXFC_Reporte_de_Fac_Venta_por_Servicios_Locales_NO_FACTURADO_FECHA_ADMINISTRATIVA";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se genere Reporte_XXFC_Reporte_de_Fac_Venta_por_Servicios_Locales_NO_FACTURADO_FECHA_ADMINISTRATIVA";
	}

	@Override
	public String setTestDesigner() {
		return "franciscojavierm3@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Contar con ambientaci�n en BD para los registros de reporte";
	}

	@Override
	public void beforeTest() {

	}
}