package EBS.mvp1_I24063_Conexion_KyribaF2;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.mvp1_Kyriba_F2.pom.pomKyribaF2;
import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
 * DESCRIPCI�N: "Validar la informaci�n del lote de pago -Concurrentes
 * finalizados normal sin errores -Validar tabla XXFC.XXFC_AP_KYRIBA_PAGOS se
 * encuentren con estatus en Proceso (P)".
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: ATC_FT_011_HU02_LogDeLoteDePago
 *
 * PREREQUISITOS: 1.- Tener acceso a aplicacion de QA EBS Mexico. (AVEBQA)\r\n"
 * 2.- Contar con acceso a EBS\r\n" 3.- Contar con factura pago en USD\r\n" 4.-
 * Tener acceso a Responsabilidad MXOXXO-APXXXXX-Super Usuario .
 *
 * INSUMOS (datos o informaci�n que necesita estar preparada o generada para
 * poder ejecutar el script automatizado): -Nombre de Responsabilidad -Nombre de
 * Carpeta -Nombre de PROVEEDOR
 *
 * @author Gilberto Aguayo Adame
 * @date 3/04/2025
 */
public class ATC_FT_011_HU02_LogDeLoteDePago extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_011_HU02_LogDeLoteDePago_test(HashMap<String, String> data) throws Exception {

		/*
		 * InternetExplorerOptions options = new InternetExplorerOptions();
		 * options.introduceFlakinessByIgnoringSecurityDomains();
		 */
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = GlobalVariables.getGAGUAYOHexEbsUsr;
		String password = GlobalVariables.getGAGUAYOHexEbsPwd;
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		String Subcarpeta = data.get("Subcarpeta");
		String campo = data.get("campo");
		String fechainicio = data.get("fechainicio");
		String fechafin = data.get("fechafin");
		String proveedor = data.get("PROVEEDOR");
		String LineaCaptura = data.get("LINEACAPTURA");
		String monto = data.get("MONTO");
		String taxIVA = data.get("TAXIVA");
		String FacNumber = data.get("FACTURA");

		/*
		 * TASA-0%IVA UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		pomKyribaF2 kyriba = new pomKyribaF2(u, testCase, data);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// PASO 1
		addStep("Ingresar a EBS --> Clic en Language y seleccionar Espa�ol --> Ingresar usuario y contrase�a --> Clic en Con�ctese\r\n"
				+ "");

		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		testCase.addScreenShotCurrentStep("save");
		inicio.login();
		p.popUpAuthenticacion();

		// PASO 2
		addStep("Seleccionar MXOXXO-APXXXXX-Super Usuario --> Otros --> Requests --> Run --> Clic en Cancelar --> "
				+ "Dirigirse al men� Ver --> Solicitudes --> Seleccionar Todas Mis Solicitudes --> Clic en Encontrar\r\n"
				+ "");

		paginaInicial.clickOnResponsabilidad(responsabilidad);
		kyriba.MXOXXOAPXXXXXSuperUsuarioOtrosRequestsRun();
		p.accepApp();
		p.pantallaCancelarSolicitud();
		assertTrue(p.pantallaEncontrarSolicitudes(), "No se pudo Ingresar a la Pantalla");
		testCase.addScreenShotCurrentStep("gg");
		p.clickEncontrarSolicitudes();
		// PASO 3
		addStep("Validar que el concurrente \"Crear Pagos\" finalice sin errores\r\n" + "");
		assertTrue(kyriba.PantallaSolicitudes(), "No se pudo Ingresar a la Pantalla Solicitudes");
		assertTrue(kyriba.CrearPagosFinalizadoNormal(), "El concurrente No finalizo correctamente");

		// PASO 4
		addStep("Validar que el concurrente \"Formatear Instrucciones de Pago\" finalice sin errores\r\n" + "");

		assertTrue(kyriba.FormatearInstruccionesDepagoFinaLNormal(), "El concurrente No finalizo correctamente");

		// PASO 5
		addStep("Validar que el concurrente \"Registrar Estado de Impresi�n\" finalice sin errores\r\n" + "");

		assertTrue(kyriba.RegistrarEstadoFinalizadoNormal(), "El concurrente No finalizo correctamente");

		p.cerrarApplicacion();
		u.hardWait(3);
		u.getDriver().quit();

		// PASO 6
		addStep("Ingresar a la base de datos de EBS \r\n" + "");
		SQLUtil dbEBS = new SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
				GlobalVariables.DB_PASSWORD_AVEBQA);
		String coneccion = "SELECT SYS_CONTEXT('USERENV', 'DB_NAME') FROM DUAL";
		testCase.addTextEvidenceCurrentStep("Conexion a la base de datos EBS exitosa");
		SQLResult Connect = dbEBS.executeQuery(coneccion);
		testCase.addQueryEvidenceCurrentStep(Connect);

		// PASO 7
		addStep("Validar tabla XXFC.XXFC_AP_KYRIBA_PAGOS se encuentren con estatus en Proceso (P)\r\n" + "");
		String query = "SELECT XXFC.XXFC_AP_KYRIBA_PAGOS.TRANSACTION_CODE,\r\n"
				+ "  XXFC.XXFC_AP_KYRIBA_PAGOS.REFERENCE_NUM,\r\n" + "  XXFC.XXFC_AP_KYRIBA_PAGOS.DEBIT_ACCOUNT,\r\n"
				+ "  XXFC.XXFC_AP_KYRIBA_PAGOS.TRANSACTION_AMOUNT,\r\n"
				+ "  XXFC.XXFC_AP_KYRIBA_PAGOS.PROCESS_STATUS,\r\n"
				+ "FROM XXFC.XXFC_AP_KYRIBA_PAGOS\r\n" + "WHERE XXFC.XXFC_AP_KYRIBA_PAGOS.PROCESS_STATUS = 'P'\r\n"
				+ "and ROWNUM <=20\r\n" + "ORDER BY XXFC.XXFC_AP_KYRIBA_PAGOS.TRANSACTION_DATE DESC";
		SQLResult result = dbEBS.executeQuery(query);
		testCase.addQueryEvidenceCurrentStep(result);
		assertTrue(result.getAllData().size() > 0); // Verificar que se localizan n�meros de pago

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_011_HU02_LogDeLoteDePago";
	}

	@Override
	public String setTestDescription() {
		return "\"Validar la informaci�n del lote de pago\r\n" + "-Concurrentes finalizados normal sin errores\r\n"
				+ "-Validar tabla XXFC.XXFC_AP_KYRIBA_PAGOS se encuentren con estatus en Proceso (P)\"\r\n" + "";
	}

	@Override
	public String setTestDesigner() {
		return "GilbertoA@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return "ATC_FT_011_HU02_LogDeLoteDePago";
	}

	@Override
	public String setPrerequisites() {
		return "\"-Contar con acceso a EBS\r\n" + "-Haber realizado un lote de pago para facturas en USD\"\r\n" + "";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}