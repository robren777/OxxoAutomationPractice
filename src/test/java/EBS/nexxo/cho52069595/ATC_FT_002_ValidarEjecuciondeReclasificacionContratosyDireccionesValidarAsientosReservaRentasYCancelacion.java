package EBS.nexxo.cho52069595;

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

public class ATC_FT_002_ValidarEjecuciondeReclasificacionContratosyDireccionesValidarAsientosReservaRentasYCancelacion
		extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_ValidarEjecuciondeReclasificacionContratosyDireccionesValidarAsientosReservaRentasYCancelacion_test(
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
		String ebsPattern = data.get("ebsPattern");
		String plazaOrigen = data.get("plazaOrigen");
		String plazaDestino = data.get("plazaDestino");
		String crOrigen = data.get("tiendaOrigen");
		String lote = data.get("lote");
		String periodo = data.get("periodo");

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
		addStep("Seleccionar " + subcarpeta + "de la carpeta " + carpeta);
		u.hardWait(2);
		paginaInicial.clickReclasificacionContratosDireccionesNexxo();
		u.hardWait(5);
		patternUtil.runApplet();
		// PASO 7
		addStep("Ingresar los parametros");
		u.hardWait(30);
		patternUtil.typeAndTab(plazaOrigen);
		sk.screen.type(crOrigen);
		patternUtil.nTAB(6);
		patternUtil.typeAndTab(plazaDestino);
		patternUtil.clickAceptarParametros();
		// PASO 8
		addStep("Ejecutar la solicitud");
		u.hardWait(10);
		patternUtil.clickEjecutar();
		// PASO 9
		u.hardWait(5);
		addStep("Ejecutar otra solicitud? No");
		patternUtil.ejecutarOtraSolicitud("No");
		// PASO 10
		addStep("Ver y encontrar solicitudes");
		u.hardWait(5);
		patternUtil.encontrarSolicitudes();
		patternUtil.clickEncontrar();
		// PASO 11
		addStep("Visualizar el Informe de Reclasificacion de Contratos y Direcciones NEXXO");
		u.hardWait(10);
		patternUtil.clickVisualizarOutput();
		u.hardWait(5);
		sk.screen.type(Key.ENTER);
		u.hardWait(15);
		patternUtil.ventanaWarningExcel();
		u.hardWait(20);
		patternUtil.appletMaximize();
		u.hardWait(5);
		patternUtil.closeWindow();
		u.hardWait(2);
		patternUtil.closeActiveWindow();
		// PASO 12
		addStep("Consultar las polizas creadas de cancelacion y reserva de rentas");
		u.hardWait(5);
		patternUtil.menuResponsabilidades();
		patternUtil.ClickenResponsabilidad("mxoxxoglxsuperusario");
		sk.screen.type(Key.ENTER);
		u.hardWait(3);
		sk.screen.type(Key.ENTER);
		u.hardWait(20);
		patternUtil.encontrarAsientos(lote, periodo);
		patternUtil.clickCerrarVentanaParametros();
		u.hardWait(5);
		patternUtil.clickRevisarAsiento();
		u.hardWait(20);
		patternUtil.closeWindow();
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
		return "" + "Validar la correcta ejecucion del proceso de " + "'Reclasificacion de Contratos y Direcciones' "
				+ "Validar los Asientos de Reserva de Rentas y Cancelacion " + "Requisitos: "
				+ "1 Tener asignada la responsabilidad 'MXOXXO-PNXXXXX-Super Usuario'";

	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "JoseO@Hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_002_ValidarEjecuciondeReclasificacionContratosyDireccionesValidarAsientosReservaRentasYCancelacion";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}