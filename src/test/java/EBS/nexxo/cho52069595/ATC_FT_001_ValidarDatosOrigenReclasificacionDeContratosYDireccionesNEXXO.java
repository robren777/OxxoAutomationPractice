package EBS.nexxo.cho52069595;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.Sikuli.PatternUtil;
import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_ValidarDatosOrigenReclasificacionDeContratosYDireccionesNEXXO extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ValidarDatosOrigenReclasificacionDeContratosYDireccionesNEXXO_test(
			HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		String subcarpeta = data.get("subcarpeta");
		String numcontrato = data.get("numeroContrato");
		String lote = data.get("lote");
		String ebsPattern = data.get("ebsPattern");
		String periodo = data.get("periodo");
		String empresa = data.get("empresa");
		String solicitud = data.get("nombresolicitud");

		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+ebsPattern);
		File Folder = new File(pathImg);
		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);

		/*
		 * PASOS DEL CASO DE PRUEBA. Matriz: cho52069595
		 */
		System.out.println("error " + ebsUrl);
		// PASO 1
		addStep("Ingresar al ambiente de EBS");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		// PASO 2
		addStep("Seleccionar idioma: Espanol Latinoamericano");
		// PASO 3
		addStep("Ingresar usuario y contraseña");
		// PASO 4
		addStep("Dar clic en el botón de 'Conectese'");
		inicio.login();
		// PASO 5
		addStep("Ingresar a la responsabilidad " + responsabilidad);
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		u.hardWait(3);
		// PASO 6
		addStep("Seleccionar 'Visualizar Arrendamientos y Documentos' en " + subcarpeta + "de la carpeta " + carpeta);
		u.hardWait(2);
		paginaInicial.clickVisualizarArrendamientosDocs();
		u.hardWait(5);
		patternUtil.runApplet();
		// PASO 7
		addStep("Consultar el numero de contrato " + numcontrato + " y dar click en 'Encontrar' ");
		u.hardWait(20);
		sk.screen.type(Key.TAB);
		u.hardWait(3);
		sk.screen.type(Key.TAB);
		u.hardWait(4);
		sk.screen.type(numcontrato);
		u.hardWait(5);
		patternUtil.clickEncontrarArrendamiento();
		u.hardWait(5);
		patternUtil.clickAbrirArrendamiento();
		// PASO 8
		addStep("Consultamos Estatus de Cronograma del Contrato Original");
		u.hardWait(15);
		// patternUtil.ConsultaNumeroContrato();
		assertTrue(patternUtil.ConsultaNumeroContrato(), "Estado como No activo");
		u.hardWait(5);
		patternUtil.closeWindowUsingX();
		u.hardWait(4);
		// PASO 9
		addStep("Consultamos y visualizamos el Asiento de Reserva de Rentas del Contrato Original");
		patternUtil.clickMenuVentana();
		patternUtil.nDOWN(3);
		u.hardWait(2);
		sk.screen.type(Key.ENTER);
		u.hardWait(3);
		patternUtil.ScrollDown(2);
		u.hardWait(2);
		patternUtil.clickAprobarExportarPagos();
		u.hardWait(2);
		patternUtil.ScrollDown(1);
		u.hardWait(2);
		sk.screen.type(Key.ENTER);
		u.hardWait(5);
		sk.screen.type(Key.TAB);
		u.hardWait(2);
		sk.screen.type(Key.TAB);
		u.hardWait(2);
		patternUtil.typeAndTab(numcontrato);
		u.hardWait(3);
		patternUtil.cambiarEstadoPagoCronogramas();
		u.hardWait(3);
		patternUtil.clickEncontrarArrendamiento();
		u.hardWait(5);
		patternUtil.closeActiveWindow();
		u.hardWait(2);
		sk.screen.type(Key.ENTER);
		u.hardWait(5);
		sk.screen.type(Key.TAB);
		u.hardWait(2);
		sk.screen.type(Key.TAB);
		u.hardWait(2);
		patternUtil.typeAndTab(numcontrato);
		u.hardWait(3);
		patternUtil.clickEncontrarArrendamiento();
		u.hardWait(5);
		patternUtil.closeActiveWindow();
		u.hardWait(3);
		patternUtil.menuResponsabilidades();
		u.hardWait(3);
		patternUtil.ScrollDown(2);
		u.hardWait(3);
		patternUtil.ClickenResponsabilidad("mxoxxoglxsuperusario");
		sk.screen.type(Key.ENTER);
		u.hardWait(3);
		sk.screen.type(Key.ENTER);
		u.hardWait(20);
		patternUtil.encontrarAsientos(lote, periodo);
		patternUtil.clickCerrarVentanaParametros();
		u.hardWait(5);
		patternUtil.clickRevisarAsiento();
		u.hardWait(5);
		patternUtil.closeActiveWindow();
		u.hardWait(2);
		patternUtil.closeActiveWindow();
		u.hardWait(3);
		// PASO 10
		addStep("Ejecutar el proceso 'XXFC - IFRS Carga Automatica EBS' ");
		patternUtil.menuResponsabilidades();
		u.hardWait(5);
		patternUtil.ScrollDown(2);
		patternUtil.ClickenResponsabilidad("mxoxxoif099usuario");
		sk.screen.type(Key.TAB);
		u.hardWait(3);
		sk.screen.type(Key.ENTER);
		u.hardWait(10);
		patternUtil.appletMaximize();
		// PASO 11
		addStep("Ejecutar el proceso 'XXFC - IFRS Reporte de Carga De Contratos' ");
		u.hardWait(5);
		patternUtil.skLogin(userName, password);
		patternUtil.textBoxTypeRight("buscarEmpresa", 150, empresa);
		u.hardWait(3);
		patternUtil.clickCargaContratos();
		patternUtil.clickOracle();
		u.hardWait(5);
		patternUtil.encontrarSolicitudes();
		patternUtil.clickEncontrar();
		u.hardWait(8);
		patternUtil.clickEjecutarNuevaSolicitud();
		u.hardWait(5);
		patternUtil.clickEjecutarNuevaSolicitud();
		patternUtil.typeAndTab(solicitud);
		patternUtil.clickAceptarParametros();
		u.hardWait(3);
		patternUtil.clickEjecutar();
		u.hardWait(5);
		patternUtil.ejecutarOtraSolicitud("No");
		u.hardWait(5);
		patternUtil.verificarEstado("ejecutandoVerde", 15);
		u.hardWait(5);
		patternUtil.clickEncontrar();
		u.hardWait(5);
		// PASO 12
		addStep("Validamos el Reporte, dar click en Visualiar Output");
		patternUtil.clickVisualizarOutput();
		u.hardWait(5);
		sk.screen.type(Key.ENTER);
		u.hardWait(60);
		patternUtil.appletMaximize();
		u.hardWait(5);
		patternUtil.BusquedaExcel("10CDJ-5018S");
		patternUtil.closeWindow();
		u.hardWait(3);
		patternUtil.closeWindow();
		u.hardWait(5);
		// PASO 13
		addStep("Consultamos y Visualizamos Direccion Original");
		patternUtil.clickOracle();
		u.hardWait(5);
		patternUtil.menuResponsabilidades();
		u.hardWait(3);
		patternUtil.ScrollDown(4);
		patternUtil.ClickenResponsabilidad("mxoxxopnxxxxxsuperusuario");
		sk.screen.type(Key.ENTER);
		u.hardWait(3);
		sk.screen.type(Key.TAB);
		u.hardWait(3);
		sk.screen.type(Key.ENTER);

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1 Tener asignada la responsabilidad 'MXOXXO-PNXXXXX-Super Usuario'";

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "" + "Visualizar el Contrato Original antes de la ejecucion del proceso "
				+ "'XXFC PN Reclasificacion de Contratos y Direcciones NEXXO' "
				+ "Cronogramas, Rerva de rentas, datos de Reporte de Carga de Contratos IFRS y Direccion Original "
				+ "Requisitos: " + "1 Tener asignada la responsabilidad 'MXOXXO-PNXXXXX-Super Usuario'";

	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "JoseO@Hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_ValidarDatosOrigenReclasificacionDeContratosYDireccionesNEXXO";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}