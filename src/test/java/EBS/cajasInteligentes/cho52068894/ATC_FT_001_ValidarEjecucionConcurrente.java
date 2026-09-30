package EBS.cajasInteligentes.cho52068894;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GenericMethods;
import EBS.util.GlobalVariables;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_001_ValidarEjecucionConcurrente extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_001_ValidarEjecucionConcurrente_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String ebsPattern = data.get("ebsPattern");
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		String entidad = data.get("entidad");
		String fechaInicio = data.get("fechaInicio");
		String fechaFin = data.get("fechaFin");
		String nombreEjecutarSolicitud = data.get("nombreEjecutarSolicitud");
		String tiendaId = data.get("tiendaId");
		String crTienda = data.get("crTienda");
		String crPlaza = data.get("crPlaza");
		String fechaFinSQLExpected = data.get("fechaFinSQLExpected");

		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();

		/*
		 * SQL Queries
		 */

		String verifyTienda = "SELECT * FROM apps.xxicm_jerarquiaoxxo_rem WHERE tiendaid IN ('" + tiendaId
				+ "') ORDER BY registro DESC";
		String verifyMAX = "SELECT MAX(registro) FROM apps.xxicm_jerarquiaoxxo_rem WHERE tiendaid = '" + tiendaId
				+ "' ORDER BY registro DESC";
		String verifyFechaCierre = "SELECT * FROM apps.xxco_tiendas_cnt_v WHERE cr_tienda = '" + crTienda
				+ "' AND cr_plaza = '" + crPlaza + "'";

		/*
		 * UTILERIAS
		 */
	utils.sql.SQLUtil dbAvebqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
				GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		GenericMethods action = new GenericMethods(u);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+ebsPattern);
		File Folder = new File(pathImg);
		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		// PASO 1
		addStep("Ingresar al ambiente de EBS.");
		// PASO 2
		addStep("Seleccionar idioma: Español Latinoamericano.");
		// PASO 3
		addStep("Ingresar usuario y contraseña.");
		// PASO 4
		addStep("Dar clic en el boton de 'Conectese'.");
		inicio.login();
		// PASO 5
		addStep("Ingresar a la responsabilidad 'MXOXXO-COXXXXX-ADMINISTRADOR'.");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Ingresar en Reportes: Run.");
		paginaInicial.clickOnCarpeta(carpeta);
		action.genericClick(u.getDriver(), paginaInicial.runCoAdminElement);
		u.hardWait(10);
		patternUtil.runApplet();
		// PASO 7
		addStep("Clic en opcion Solicitud unica, Aceptar.");
		patternUtil.solicitudUnica();
		// PASO 8
		addStep("Ingresar en el campo Nombre 'XXCO-Proceso Carga Entidades ICM', TAB.");
		patternUtil.typeAndTab(nombreEjecutarSolicitud);
		// PASO 9
		addStep("Ingresar los parametros:" + "Entidad: " + entidad + "Fecha Inicio: " + fechaInicio + "Fecha Fin: "
				+ fechaFin + "Aceptar.");
		patternUtil.typeParametros(entidad, fechaInicio, fechaFin);
		// PASO 10
		addStep("Clic en el boton de Ejecutar.");
		patternUtil.clickEjecutar();
		// PASO 11
		addStep("¿Ejecutar Otra Solicitud? No.");
		patternUtil.ejecutarOtraSolicitud("No");
		// PASO 12
		addStep("Dirigirse al menu: Ver(x) -> 'Solicitudes' -> 'Encontrar'.");
		patternUtil.encontrarSolicitudes();
		patternUtil.clickEncontrar();
		// PASO 13
		addStep("Visualizar LOG.");
		patternUtil.verificarEstado("ejecutandoVerde", 6000000);
		// u.hardWait(30);
		patternUtil.keyAlt("z"); // Visualizar el Registro
		u.hardWait(10);
		patternUtil.closeWindow();
		u.hardWait(3);
		patternUtil.closeWindow();
		u.hardWait(5);
		patternUtil.ventanaConfirmarSalir("a");
		u.close();
		// PASO 14
		addStep("Validar la tienda 'TIE-10TMP506F8' actualizada en BD.");

		System.out.println(GlobalVariables.DB_HOST_AVEBQA);
		System.out.println(verifyTienda);
		SQLResult verifyTiendaExec = dbAvebqa.executeQuery(verifyTienda);
		String fechaFinSQL = "";
		String registroSQL = "";

		/*
		 * Verificar que la fecha de fin se actualice con el valor adecuado que es
		 * '9998-12-31 00:00:00'
		 */
		boolean validChangeRes = verifyTiendaExec.isEmpty();
		if (!validChangeRes) {
			fechaFinSQL = verifyTiendaExec.getData(0, "fechafin");
			System.out.println("Fecha Fin: " + fechaFinSQL);
			assertEquals(fechaFinSQL, fechaFinSQLExpected);
			registroSQL = verifyTiendaExec.getData(0, "registro");
			System.out.println("Registro: " + registroSQL);
			testCase.addQueryEvidenceCurrentStep(verifyTiendaExec);
		} else if (validChangeRes) {
			testCase.addTextEvidenceCurrentStep("No se encontraron datos en la consulta " + verifyTienda);
			testCase.addQueryEvidenceCurrentStep(verifyTiendaExec);
		}

		/*
		 * Verificar que la fecha de 'Registro' maxima coincida con la fecha de registro
		 * del query anterior
		 */
		System.out.println(verifyMAX);
		SQLResult verifyMaxExec = dbAvebqa.executeQuery(verifyMAX);
		String maxRegistroSQL = "";
		boolean verifyMaxRes = verifyMaxExec.isEmpty();
		if (!verifyMaxRes) {
			maxRegistroSQL = verifyMaxExec.getData(0, "MAX(registro)");
			assertEquals(maxRegistroSQL, registroSQL);
			System.out.println("Max Registro: " + maxRegistroSQL);
			testCase.addQueryEvidenceCurrentStep(verifyMaxExec);
		} else if (verifyMaxRes) {
			testCase.addTextEvidenceCurrentStep("No se encontraron datos en la consulta " + verifyMAX);
			testCase.addQueryEvidenceCurrentStep(verifyMaxExec);
		}

		/*
		 * Verificar que la 'fecha de cierre' sea null
		 */
		System.out.println(verifyFechaCierre);
		SQLResult verifyFechaCierreExec = dbAvebqa.executeQuery(verifyFechaCierre);
		String fechaCierreSQL = "";
		boolean verifyFechaCierreRes = verifyFechaCierreExec.isEmpty();
		if (!verifyFechaCierreRes) {
			fechaCierreSQL = verifyFechaCierreExec.getData(0, "fecha_cierre");
			assertEquals(fechaCierreSQL, null);
			System.out.println("fecha_cierre: " + fechaCierreSQL);
			testCase.addQueryEvidenceCurrentStep(verifyFechaCierreExec);
		} else if (verifyMaxRes) {
			testCase.addTextEvidenceCurrentStep("No se encontraron datos en la consulta " + verifyFechaCierre);
			testCase.addQueryEvidenceCurrentStep(verifyFechaCierreExec);
		}

		dbAvebqa.close();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1.-Tener asignada la responsabilidad: 'MXOXXO-COXXXXX-ADMINISTRADOR' 2.-Tener tiendas que fueron reaperturas y que en la entidad jerarquia no han viajan a ICM.";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que el proceso de 'XXCO-Proceso Carga Entidades ICM' con el parametro jerarquia tome en cuentas tiendas que son reapertura. Prerrequisitos: 1.-Tener asignada la responsabilidad: 'MXOXXO-COXXXXX-ADMINISTRADOR'  2.-Tener tiendas que fueron reaperturas y que en la entidad jerarquia no han viajan a ICM.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "JesusH@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_ValidarEjecucionConcurrente_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
