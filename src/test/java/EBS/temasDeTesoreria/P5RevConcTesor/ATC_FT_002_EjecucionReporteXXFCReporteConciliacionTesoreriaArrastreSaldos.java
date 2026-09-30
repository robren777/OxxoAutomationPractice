package EBS.temasDeTesoreria.P5RevConcTesor;

import static org.testng.Assert.assertFalse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.Sikuli.MetodosSikuli;
import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_002_EjecucionReporteXXFCReporteConciliacionTesoreriaArrastreSaldos extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_EjecucionReporteXXFCReporteConciliacionTesoreriaArrastreSaldos_Test(
			HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String recurso1 = data.get("recurso1");
		String recurso2 = data.get("recurso2");
		String compania = data.get("compania");
		String origen = data.get("origen");
		String plaza = data.get("plaza");
		String cuentaCorriente = data.get("cuentacorriente");
		String tipoReporte = data.get("tiporeporte");
		String tipoSalida = data.get("tiposalida");
		String periodo1 = data.get("periodo1");
		String periodoFebrero = data.get("periodofebrero");
		String credito = data.get("credito");
		String saldoFinal = data.get("saldofinal");
		String credito2 = data.get("credito2");
		String saldoFinal2 = data.get("saldofinal2");
		String periodo2 = data.get("periodo2");
		String credito3 = data.get("credito3");
		String saldoFinal3 = data.get("saldofinal3");
		String periodo3 = data.get("periodo3");
		
		/*
		 * SQL Queries
		 */
		String consulta1 = "SELECT PLAZA,CUENTA_CORRIENTE,DIVISA,PERIODO,SALDO_INICIAL,DEBITO,CREDITO,SALDO_FINAL FROM XXFC.XXFC_XTR_SALDOS_TESORERIA\r\n"
				+ "WHERE 1=1\r\n"
				+ "AND PLAZA IN ('10APO')\r\n"
				+ "AND CUENTA_CORRIENTE = '19EC0018'\r\n"
				+ "AND DIVISA = 'MXN'\r\n"
				+ "AND PERIODO = 'FEB-2021'";
		
		
		String consulta2 = "SELECT PLAZA,CUENTA_CORRIENTE,DIVISA,PERIODO,SALDO_INICIAL,DEBITO,CREDITO,SALDO_FINAL FROM XXFC.XXFC_XTR_SALDOS_TESORERIA\r\n"
				+ "WHERE 1=1\r\n"
				+ "AND PLAZA IN ('10APO')\r\n"
				+ "AND CUENTA_CORRIENTE = '19EC0018'\r\n"
				+ "AND DIVISA = 'MXN'\r\n"
				+ "AND PERIODO = 'MAR-2021'";
		
		String consulta3 = "SELECT PLAZA,CUENTA_CORRIENTE,DIVISA,PERIODO,SALDO_INICIAL,DEBITO,CREDITO,SALDO_FINAL FROM XXFC.XXFC_XTR_SALDOS_TESORERIA\r\n"
				+ "WHERE 1=1\r\n"
				+ "AND PLAZA IN ('10APO')\r\n"
				+ "AND CUENTA_CORRIENTE = '19EC0018'\r\n"
				+ "AND DIVISA = 'MXN'\r\n"
				+ "AND PERIODO = 'ABR-2021'";
		
		/*
		 * UTILERIAS
		 */
		utils.sql.SQLUtil EBSAVANTE = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA,GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		/*
		 * PASOS DEL CASO DE PRUEBA, Matriz: temasDeTesoreria.P5RevConcTesor
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
		addStep("Ingresar a la Responsabilidad de 'MXXXXX-GLXXXXX-Consulta'.");
		testCase.addScreenShotCurrentStep(u, "'MXXXXX-GLXXXXX-Consulta'.");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		paginaInicial.clickConsultas();
		paginaInicial.runAccount();
		p.runApplet();
		u.hardWait(3);
		// PASO 6
		addStep("Se realiza la Ejecucion del Proceso de Actualización de Saldos Iniciales de Tesoreria.");
		p.vers();
		p.clickNuevaSolicitud();
		// PASO 7
		addStep("Se valida a nivel tabla de BD XXFC_XTR_SALDOS_TESORERIA que se vea la actualizacion correcta del saldo para el periodo FEB-2021.");
		p.buscarArchivoTab(recurso1);
		p.buscarArchivo(compania);
		p.buscarArchivoTab(plaza);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		p.buscarArchivoTab(cuentaCorriente);
		p.buscarArchivoTab(periodoFebrero);
		testCase.addScreenShotCurrentStep("Ingresar los parametros correspondientes");
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		//Validacion de atributos de bases de datos
		addStep("Validar CREDITO,SALDO_FINAL");
		System.out.println(consulta1);
		SQLResult validat = EBSAVANTE.executeQuery(consulta1);
		String atributo = validat.getData(0, "CREDITO");
		boolean validar = atributo.equals(credito);
		System.out.print(atributo);
		assertFalse(!validar, "La respuesta no tiene el el resultado esperado.");
		
		SQLResult validat2 = EBSAVANTE.executeQuery(consulta1);
		String atributo2 = validat2.getData(0, "SALDO_FINAL");
		boolean validar2 = atributo2.equals(saldoFinal);
		System.out.print(atributo2);
		assertFalse(!validar2, "La respuesta no tiene el el resultado esperado.");
		u.hardWait(3);
		p.ejecutar();
		p.ejecutarBotonNo();
		// PASO 8
		addStep("Se realiza la Ejecución del reporte: Ver > Solicitudes > Ejecutar una nueva Solicitud > Solicitud Unica > Aceptar > XXFC - Reporte de Conciliacion FEMCO (Tesoreria)");
		p.clickNuevaSolicitud();
		u.hardWait(3);
		p.buscarArchivoTab(recurso2);
		testCase.addScreenShotCurrentStep("Ingresar el recurso correspondientes");
		u.hardWait(2);
		sk.screen.click();
		p.ClickConciliacionTesoreria();
		// PASO 9
		addStep("Ingresar los parametros correspondientes a la plaza que se requiere hacer la validacion.");
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		p.buscarArchivo(compania);
		sk.screen.keyDown(Key.DELETE);
		sk.screen.keyUp(Key.DELETE);
		p.buscarArchivo(plaza);
		sk.screen.keyDown(Key.DELETE);
		sk.screen.keyUp(Key.DELETE);
		p.buscarArchivoTab(origen);
		p.buscarArchivoTab(cuentaCorriente);
		p.buscarArchivoTab(tipoReporte);
		p.buscarArchivoTab(tipoSalida);
		p.buscarArchivoTab(periodo1);
		testCase.addScreenShotCurrentStep("Ingresar los parametros correspondientes");
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		// PASO 10
		addStep("Se valida que el reporte para el periodo de MAR-2021 muestra correctamente el Saldo Inicial tomado del Saldo Final del periodo anterior FEB-2021.");
		u.hardWait(3);
		p.ejecutar();
		p.ejecutarBotonNo();
		p.encontrar();
		// PASO 11
		addStep("Se realiza la Ejecucion del Proceso de Actualizacion de Saldos Iniciales de Tesoreria.");
		p.vers();
		p.clickNuevaSolicitud();
		// PASO 12
		addStep("Se valida a nivel tabla de BD XXFC_XTR_SALDOS_TESORERIA que se vea la actualizacion correcta del saldo para el periodo MAR-2021.");
		p.buscarArchivoTab(recurso1);
		p.buscarArchivo(compania);
		p.buscarArchivoTab(plaza);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		p.buscarArchivoTab(cuentaCorriente);
		p.buscarArchivoTab(periodo1);
		testCase.addScreenShotCurrentStep("Ingresar los parametros correspondientes");
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		//Validacion de atributos de bases de datos
		addStep("Validar CREDITO,SALDO_FINAL");
		System.out.println(consulta2);
		SQLResult validatMarzo = EBSAVANTE.executeQuery(consulta2);
		String atributoMarzo = validatMarzo.getData(0, "CREDITO");
		boolean validarMarzo = atributoMarzo.equals(credito2);
		System.out.print(atributoMarzo);
		assertFalse(!validarMarzo, "La respuesta no tiene el el resultado esperado.");
		
		SQLResult validat2Marzo = EBSAVANTE.executeQuery(consulta2);
		String atributo2Marzo = validat2Marzo.getData(0, "SALDO_FINAL");
		boolean validar2Marzo = atributo2Marzo.equals(saldoFinal2);
		System.out.print(atributo2Marzo);
		assertFalse(!validar2Marzo, "La respuesta no tiene el el resultado esperado.");
		u.hardWait(2);
		p.ejecutar();
		p.ejecutarBotonNo();
		// PASO 13
		addStep("Se realiza la Ejecucion del reporte: Ver > Solicitudes > Ejecutar una nueva Solicitud > Solicitud Unica > Aceptar > XXFC - Reporte de Conciliacion FEMCO (Tesoreria)");
		p.clickNuevaSolicitud();
		u.hardWait(2);
		sk.screen.click();
		p.buscarArchivoTab(recurso2);
		testCase.addScreenShotCurrentStep("Ingresar recurso correspondientes");
		u.hardWait(2);
		sk.screen.click();
		p.ClickConciliacionTesoreria();
		// PASO 14
		addStep("Ingresar los parametros correspondientes a la plaza que se requiere hacer la validacion");
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		p.buscarArchivo(compania);
		sk.screen.keyDown(Key.DELETE);
		sk.screen.keyUp(Key.DELETE);
		p.buscarArchivo(plaza);
		sk.screen.keyDown(Key.DELETE);
		sk.screen.keyUp(Key.DELETE);
		p.buscarArchivoTab(origen);
		u.hardWait(2);
		p.buscarArchivoTab(cuentaCorriente);
		u.hardWait(2);
		p.buscarArchivoTab(tipoReporte);
		u.hardWait(2);
		p.buscarArchivoTab(tipoSalida);
		u.hardWait(2);
		p.buscarArchivoTab(periodo2);
		testCase.addScreenShotCurrentStep("Ingresar los parametros correspondientes");
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		// PASO 15
		addStep("Se valida que el reporte para el periodo de ABR-2021 muestra correctamente el Saldo Inicial tomado del Saldo Final del periodo anterior MAR-2021.");
		p.ejecutar();
		p.ejecutarBotonNo();
		p.encontrar();
		// PASO 16
		addStep("Se realiza la Ejecucion del Proceso de Actualizacion de Saldos Iniciales de Tesoreria.");
		p.vers();
		p.clickNuevaSolicitud();
		// PASO 17
		addStep("Se valida a nivel tabla de BD XXFC_XTR_SALDOS_TESORERIA que se vea la actualizacion correcta del saldo para el periodo ABR-2021.");
		p.buscarArchivoTab(recurso1);
		u.hardWait(2);
		p.buscarArchivo(compania);
		u.hardWait(2);
		p.buscarArchivoTab(plaza);
		u.hardWait(2);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		p.buscarArchivoTab(cuentaCorriente);
		u.hardWait(2);
		p.buscarArchivoTab(periodo3);
		testCase.addScreenShotCurrentStep("Ingresar los parametros correspondientes");
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		//Validacion de atributos de bases de datos
		addStep("Validar CREDITO,SALDO_FINAL");
		System.out.println(consulta3);
		SQLResult v = EBSAVANTE.executeQuery(consulta3);
		String aAbril = v.getData(0, "CREDITO");
		boolean va = aAbril.equals(credito3);
		System.out.print(aAbril);
		assertFalse(!va, "La respuesta no tiene el el resultado esperado.");
		
		String aAbril2 = v.getData(0, "SALDO_FINAL");
		boolean va2 = aAbril2.equals(saldoFinal3);
		System.out.print(aAbril2);
		assertFalse(!va2, "La respuesta no tiene el el resultado esperado.");
		u.hardWait(2);
		p.ejecutar();
		p.ejecutarBotonNo();
		// PASO 18
		addStep("Se realiza la Ejecución del reporte: Ver > Solicitudes > Ejecutar una nueva Solicitud > Solicitud Unica > Aceptar > XXFC - Reporte de Conciliacion FEMCO (Tesoreria)");
		p.clickNuevaSolicitud();
		u.hardWait(3);
		p.buscarArchivoTab(recurso2);
		testCase.addScreenShotCurrentStep("Ingresar el recurso correspondiente");
		u.hardWait(2);
		sk.screen.click();
		p.ClickConciliacionTesoreria();
		// PASO 19
		addStep("Ingresar los parametros correspondientes a la plaza que se requiere hacer la validacion.");
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		p.buscarArchivo(compania);
		sk.screen.keyDown(Key.DELETE);
		sk.screen.keyUp(Key.DELETE);
		p.buscarArchivo(plaza);
		sk.screen.keyDown(Key.DELETE);
		sk.screen.keyUp(Key.DELETE);
		p.buscarArchivoTab(origen);
		u.hardWait(2);
		p.buscarArchivoTab(cuentaCorriente);
		u.hardWait(2);
		p.buscarArchivoTab(tipoReporte);
		u.hardWait(2);
		p.buscarArchivoTab(tipoSalida);
		u.hardWait(2);
		p.buscarArchivoTab(periodo3);
		testCase.addScreenShotCurrentStep("Ingresar los parametros correspondientes");
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		// PASO 20
		addStep("Se valida que el reporte para el periodo de MAY-2021 muestra correctamente el Saldo Inicial tomado del Saldo Final del periodo anterior ABR-2021.");
		p.ejecutar();
		p.ejecutarBotonNo();
		p.encontrar();
		sk.screen.keyDown(Key.ALT);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyDown(Key.ALT);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		sk.screen.keyUp(Key.ALT);
		sk.screen.keyDown(Key.ALT);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		sk.screen.keyUp(Key.ALT);
		sk.screen.keyDown(Key.ALT);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		sk.screen.keyUp(Key.ALT);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		u.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_EjecucionReporteXXFCReporteConciliacionTesoreriaArrastreSaldos_Test";
	}

	@Override
	public String setTestDescription() {
		return "Ejecutar el Reporte XXFC - Reporte de Conciliacion FEMCO (Tesoreria), para validar que en los siguientes periodos después de Revaluacion del CP-001 se vea la afectacion en el arrastre de los Saldos Iniciales y Finales."
				+ "Pre-requisitos: Acceso a las responsabilidades: MXXXXX-GLXXXXX-Consulta." 
				+ "Acceso a BD de instancia AVEBQA"
				+ "Haber realizado la Revaluación para la Plaza 10APO de Compañía 00099 para periodo FEB-2021.";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Acceso a las responsabilidades: MXXXXX-GLXXXXX-Consulta." + "Acceso a BD de instancia AVEBQA"
				+ "Haber realizado la Revaluación para la Plaza 10APO de Compañía 00099 para periodo FEB-2021.";

	}

	@Override
	public void beforeTest() {

	}
}