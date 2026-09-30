package EBS.cajasInteligentes.cho52071604;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_ReporteDeEntradasYDevolucionesProveedoresSumarizado extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ReporteDeEntradasYDevolucionesProveedoresSumarizado_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String nombreSolicitud = data.get("nombre_solicitud");
		String ebsPattern = "EBS\\"+data.get("ebsPattern");
		String unidadOperativa = data.get("unidad_operativa");
		String fechaInicial = data.get("fecha_inicial");
		String fechaFinal = data.get("fecha_final");

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
		String pathImg = sk.PatternPath(ebsPattern);
		File Folder = new File(pathImg);
		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);

		/*
		 * PASOS DEL CASO DE PRUEBA. Matriz: cho52071604
		 */
		System.out.println("error " + ebsUrl);
		// PASO 1
		addStep("Ingresar al ambiente de EBS");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		// PASO 2
		addStep("Seleccionar idioma: Español Latinoamericano");
		// PASO 3
		addStep("Ingresar usuario y contraseña");
		// PASO 4
		addStep("Dar clic en el botón de 'Conectese'");
		inicio.login();
		// PASO 5
		addStep("Ingresar a la responsabilidad 'MXOXXO-AR10MON-AJUSTE-TRANSACCIONES'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar la carpeta Solicitudes y click en RUN");
		paginaInicial.clickSolicitudesAdmonACTFIJO();
		u.hardWait(10);
		patternUtil.runApplet();
		Thread.sleep(10000);
		// PASO 7
		addStep("Seleccionar la organizacion y Aceptar");
		u.hardWait(5);
		sk.screen.type(Key.TAB);
		u.hardWait(2);
		sk.screen.type(Key.TAB);
		u.hardWait(2);
		sk.screen.type(Key.ENTER);
		Thread.sleep(8000);
		// PASO 8
		addStep("Click en 'Ejecutar Nueva Solicitud' ");
		patternUtil.clickEjecutarNuevaSolicitud();
		u.hardWait(5);
		// PASO 9
		addStep("Se ingresa el nombre 'XXFC_ADQ: Reporte de Entradas y Devoluciones a Proveedor Sumarizado'");
		patternUtil.typeAndTab(nombreSolicitud);
		u.hardWait(3);
		// PASO 10
		addStep("Cerrar ventana 'Parametros' y cambiar la Unidad Operativa a " + unidadOperativa);
		patternUtil.clickCerrarVentanaParametros();
		u.hardWait(2);
		patternUtil.typeAndTab(unidadOperativa);
		u.hardWait(2);
		// PASO 11
		addStep("Ingresar los parametros de Fecha Inicial y Final");
		u.hardWait(2);
		sk.screen.type(Key.TAB);
		sk.screen.type(fechaInicial);
		u.hardWait(2);
		sk.screen.type(fechaFinal);
		sk.screen.type(Key.ENTER);
		u.hardWait(3);
		// PASO 12
		addStep("Click en Ejecutar ");
		patternUtil.clickEjecutar();
		u.hardWait(2);
		// PASO 13
		addStep("Ejecutar otra solicitud? No");
		patternUtil.ejecutarOtraSolicitud("No");
		u.hardWait(2);
		// PASO 14
		addStep("Click en Encontrar");
		patternUtil.clickEncontrar();
		u.hardWait(10);
		// PASO 15
		addStep("Click en 'Visualizar Output'");
		patternUtil.clickVisualizarOutput();

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "" + "1 Tener asignada la responsabilidad 'MXOXXO-IN17FAA-Admon_ACT_FIJO_OS'"
				+ "2 Existan Con devoluciones a proveedor en dias distintos";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "" + "Validar el reporte 'XXFC_ADQ: Reporte de Entradas y Devoluciones a Proveedor Sumarizado' "
				+ "Requisitos: " + "1 Tener asignada la responsabilidad 'MXOXXO-IN17FAA-Admon_ACT_FIJO_OS' "
				+ "2 Existan Con devoluciones a proveedor en dias distintos";

	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "JoseO@Hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_ReporteDeEntradasYDevolucionesProveedoresSumarizado";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
