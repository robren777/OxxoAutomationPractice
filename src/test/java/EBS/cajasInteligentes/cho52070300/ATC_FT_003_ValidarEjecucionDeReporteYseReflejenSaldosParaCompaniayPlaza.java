package EBS.cajasInteligentes.cho52070300;

import java.util.HashMap;

import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GenericSikuliMethods;
import EBS.util.GlobalVariables;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_003_ValidarEjecucionDeReporteYseReflejenSaldosParaCompaniayPlaza extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_ValidarEjecucionDeReporteYseReflejenSaldosParaCompaniayPlaza_test(
			HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String nombreSolicitud = data.get("nombre_solicitud");
		String compania = data.get("compania");
		String plaza = data.get("plaza1");
		String plaza2 = data.get("plaza2");
		String origen = data.get("origen");
		String cuentaCorriente = data.get("cuenta_corriente");
		String tipoReporte = data.get("tipoReporte");
		String tipoSalida = data.get("tipoSalida");
		String periodo = data.get("periodo");

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		GenericSikuliMethods SkMethod = new GenericSikuliMethods();
		SikuliUtil sk = new SikuliUtil();

		Pattern pathimg = new Pattern(
				GlobalVariables.PATTERN_EBS+"MTCFT001_NoErrorXMLBalanza\\checkbox.png");
		Pattern pathimg1 = new Pattern(
				GlobalVariables.PATTERN_EBS+"MTCFT001_NoErrorXMLBalanza\\Run_Applet.jpg");
		Pattern imgBtnEjecutar = new Pattern(
				GlobalVariables.PATTERN_EBS+"MTCFT003_EjecucionDeReporteSaldosParaCompaniaYPlaza\\Ejecutar_Solicitud.jpg");
		Pattern imgBtnEjecOtroSi = new Pattern(
				GlobalVariables.PATTERN_EBS+"MTCFT003_EjecucionDeReporteSaldosParaCompaniaYPlaza\\Ejecucion_Si.jpg");
		Pattern imgBtnVer = new Pattern(
				GlobalVariables.PATTERN_EBS+"MTCFT003_EjecucionDeReporteSaldosParaCompaniaYPlaza\\verX.jpg");
		Pattern imgBtnSolicitudes = new Pattern(
				GlobalVariables.PATTERN_EBS+"MTCFT003_EjecucionDeReporteSaldosParaCompaniaYPlaza\\Solicitudes.jpg");
		Pattern imgBtnEncontrarSolicitudes = new Pattern(
				GlobalVariables.PATTERN_EBS+"MTCFT003_EjecucionDeReporteSaldosParaCompaniaYPlaza\\Encontrar_Solicitudes.jpg");
		Pattern imgBtnOutput = new Pattern(
				GlobalVariables.PATTERN_EBS+"MTCFT003_EjecucionDeReporteSaldosParaCompaniaYPlaza\\Visualizar_Output.jpg");
		Pattern imgBtnEncontrar = new Pattern(
				GlobalVariables.PATTERN_EBS+"MTCFT003_EjecucionDeReporteSaldosParaCompaniaYPlaza\\Encontrar.jpg");
		Pattern imgBtnRefresh = new Pattern(
				GlobalVariables.PATTERN_EBS+"MTCFT003_EjecucionDeReporteSaldosParaCompaniaYPlaza\\Refrescar_Datos.jpg");
		Pattern imgOpenXLS = new Pattern(
				GlobalVariables.PATTERN_EBS+"MTCFT003_EjecucionDeReporteSaldosParaCompaniaYPlaza\\open_xls.jpg");

		/*
		 * PASOS DEL CASO DE PRUEBA. Matriz: cho52070300
		 */
		System.out.println("error " + ebsUrl);
		// PASO 1
		addStep("Ingresar al ambiente de EBS.");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		// PASO 2
		addStep("Seleccionar idioma: Español Latinoamericano.");
		// PASO 3
		addStep("Ingresar usuario y contraseña.");
		// PASO 4
		addStep("Dar clic en el botón de 'Conéctese'.");
		inicio.login();
		// PASO 5
		addStep("Ingresar a la responsabilidad 'MXXXXX-GLXXXXX-Consulta'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar la opcion: Solicitudes>Request>Standard");
		paginaInicial.clickConsultaSolicitudes();
		paginaInicial.clickRequestStandard();
		// addStep("Click para habilitar la Applet de JAVA \n ");
		Thread.sleep(5000);
		sk.screen.click(pathimg);
		// addStep("Click en boton RUN \n ");
		// PASO 7
		addStep("Seleccionar 'solicitud unica'");
		Thread.sleep(5000);
		sk.screen.click(pathimg1);
		// PASO 8
		addStep("Se ingresa el nombre 'XXFC - Reporte de Conciliacion FEMCO Cartas' ");
		Thread.sleep(25000);
		sk.screen.type(Key.TAB);
		// PASO 9
		addStep("Se ingresan los parametros: Compania,Plaza,Origen,Cuenta Corriente, Tipo de Reporte, Tipo de Salida, Periodo");
		Thread.sleep(10000);
		sk.screen.type(Key.ENTER);
		Thread.sleep(2000);
		sk.screen.type(nombreSolicitud);
		Thread.sleep(2000);
		sk.screen.type(Key.TAB);

		Thread.sleep(2000);
		sk.screen.type(compania);
		Thread.sleep(2000);
		sk.screen.type(Key.TAB);

		Thread.sleep(2000);
		sk.screen.type(plaza);
		Thread.sleep(2000);
		sk.screen.type(Key.TAB);

		Thread.sleep(2000);
		sk.screen.type(origen);
		Thread.sleep(2000);
		sk.screen.type(Key.TAB);

		Thread.sleep(2000);
		sk.screen.type(cuentaCorriente);
		Thread.sleep(2000);
		sk.screen.type(Key.TAB);

		Thread.sleep(2000);
		sk.screen.type(tipoReporte);
		// Thread.sleep(2000);
		// sk.screen.type(Key.TAB);

		Thread.sleep(2000);
		sk.screen.type(tipoSalida);
		// Thread.sleep(2000);
		// sk.screen.type(Key.TAB);

		Thread.sleep(2000);
		sk.screen.type(periodo);
		Thread.sleep(3000);
		sk.screen.type(Key.TAB);

		Thread.sleep(3000);
		sk.screen.type(Key.ENTER);

		// PASO 10
		addStep("Seleccionar 'Ejecutar'");
		Thread.sleep(2000);
		sk.screen.click(imgBtnEjecutar);

		// PASO 11
		addStep("Seleccionar 'Si' en el mensaje '¿Ejecutar otra solicitud?'");
		Thread.sleep(10000);
		sk.screen.click(imgBtnEjecOtroSi);

		// PASO 12
		addStep("Se ingresan los parametros: Compania,Plaza,Origen,Cuenta Corriente, Tipo de Reporte, Tipo de Salida, Periodo");
		Thread.sleep(1000);
		sk.screen.type(Key.TAB);

		Thread.sleep(5000);
		sk.screen.type(compania);
		Thread.sleep(2000);
		sk.screen.type(Key.TAB);

		Thread.sleep(2000);
		sk.screen.type(plaza2);
		Thread.sleep(2000);
		sk.screen.type(Key.TAB);

		Thread.sleep(2000);
		sk.screen.type(origen);
		Thread.sleep(2000);
		sk.screen.type(Key.TAB);

		Thread.sleep(2000);
		sk.screen.type(cuentaCorriente);
		Thread.sleep(2000);
		sk.screen.type(Key.TAB);

		Thread.sleep(2000);
		sk.screen.type(tipoReporte);
		// Thread.sleep(2000);
		// sk.screen.type(Key.TAB);

		Thread.sleep(2000);
		sk.screen.type(tipoSalida);
		// Thread.sleep(2000);
		// sk.screen.type(Key.TAB);

		Thread.sleep(2000);
		sk.screen.type(periodo);
		Thread.sleep(3000);
		sk.screen.type(Key.TAB);

		Thread.sleep(3000);
		sk.screen.type(Key.ENTER);

		// PASO 13
		addStep("Seleccionar 'Ejecutar'");
		Thread.sleep(2000);
		sk.screen.click(imgBtnEjecutar);

		// PASO 14
		addStep("Seleccionar 'No' en el mensaje '¿Ejecutar otra solicitud?'");
		Thread.sleep(5000);
		sk.screen.type(Key.TAB);
		sk.screen.type(Key.ENTER);

		// PASO 15
		addStep("Seleccionar la opcion en el menu: Ver(x)>Solicitudes>Encontrar");
		Thread.sleep(8000);
		sk.screen.click(imgBtnVer);
		Thread.sleep(8000);
		sk.screen.click(imgBtnSolicitudes);
		Thread.sleep(10000);
		sk.screen.type(Key.TAB);
		Thread.sleep(1000);
		sk.screen.type(Key.TAB);
		Thread.sleep(1000);
		sk.screen.type(Key.TAB);
		Thread.sleep(1000);
		sk.screen.type(Key.TAB);
		Thread.sleep(1000);
		sk.screen.type(Key.TAB);
		Thread.sleep(1000);
		sk.screen.type(Key.TAB);
		Thread.sleep(1000);
		sk.screen.type(Key.TAB);
		Thread.sleep(1000);
		sk.screen.type(Key.ENTER);
		Thread.sleep(60000);
		sk.screen.click(imgBtnRefresh);
		Thread.sleep(90000);
		sk.screen.click(imgBtnRefresh);

		// PASO 16
		addStep("Visualizar Output");
		Thread.sleep(5000);
		sk.screen.click(imgBtnOutput);
		Thread.sleep(3000);
		sk.screen.click(imgOpenXLS);
		Thread.sleep(15000);
		SkMethod.MasterSikuliClose(sk);
		sk.screen.type(Key.TAB);
		Thread.sleep(1000);
		sk.screen.type(Key.TAB);
		Thread.sleep(1000);
		sk.screen.type(Key.TAB);
		Thread.sleep(1000);
		sk.screen.type(Key.TAB);
		Thread.sleep(1000);
		sk.screen.type(Key.TAB);
		Thread.sleep(5000);
		sk.screen.click(imgBtnOutput);
		Thread.sleep(3000);
		sk.screen.click(imgOpenXLS);
		Thread.sleep(10000);
		SkMethod.MasterSikuliClose(sk);

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return ""
				+ "1 Haber Cargado archivo .csv con el proceso 'XXFC - Carga de Saldos Iniciales de Cartas' del escenario 'MTC-FT-001'"
				+ "2 Validar la carga de Saldo Inicial del periodo 'SEP-2020' en la tabla 'XXFC.XXFC_GL_SALDOS_CARTAS' para la tienda 10CTL con el proceso"
				+ " 'XXFC - Procesos Calcula Saldos Iniciales Cartas' del escenario 'MTC-FT-002'"
				+ "3 Acceso a la responsabilidad 'MXXXXX-GLXXXXX-Consulta'.";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar la ejecucion del reporte 'XXFC - Reporte de Conciliacion FEMCO Cartas' reflejen los saldos para la compania y plaza ingresada"
				+ "Requisitos: "
				+ "1 Haber Cargado archivo .csv con el proceso 'XXFC - Carga de Saldos Iniciales de Cartas' del escenario 'MTC-FT-001' "
				+ "2 Validar la carga de Saldo Inicial del periodo 'SEP-2020' en la tabla 'XXFC.XXFC_GL_SALDOS_CARTAS' para la tienda 10CTL con el proceso "
				+ " 'XXFC - Procesos Calcula Saldos Iniciales Cartas' del escenario 'MTC-FT-002' "
				+ "3 Acceso a la responsabilidad 'MXXXXX-GLXXXXX-Consulta'.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "JoseO@Hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_003_ValidarEjecucionDeReporteYseReflejenSaldosParaCompaniayPlaza";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
