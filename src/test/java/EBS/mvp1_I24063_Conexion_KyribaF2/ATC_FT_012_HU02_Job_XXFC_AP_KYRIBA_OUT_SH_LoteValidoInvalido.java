package EBS.mvp1_I24063_Conexion_KyribaF2;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.json.JSONObject;
import org.sikuli.script.Pattern;
import org.testng.Assert;
import org.testng.annotations.Test;

import EBS.mvp1_Kyriba_F2.pom.pomKyribaF2;
import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.SSHConnector;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
 * DESCRIPCI�N: Validar que al ejecutar el job XXFC_AP_KYRIBA_OUT_SH con lote
 * valido e invalido solo procese el correcto
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * -ATC_FT_012_HU02_Job_XXFC_AP_KYRIBA_OUT_SH_LoteValidoInvalido
 * 
 * PREREQUISITOS: "-"-Contar con acceso a EBS -Haber realizado un lote de pago
 * para facturas en USD -Haber realizado un lote de pago para facturas USD
 * incorrecto"
 *
 *
 *
 * INSUMOS (datos o informaci�n que necesita estar preparada o generada para
 * poder ejecutar el script automatizado): -Nombre de Responsabilidad -Nombre de
 * Carpeta -Nombre de PROVEEDOR
 *
 * @author Gilberto Aguayo Adame
 * @date 3/04/2025
 */
public class ATC_FT_012_HU02_Job_XXFC_AP_KYRIBA_OUT_SH_LoteValidoInvalido extends BaseExecution {
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_012_HU02_Job_XXFC_AP_KYRIBA_OUT_SH_LoteValidoInvalido_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * InternetExplorerOptions options = new InternetExplorerOptions();
		 * options.introduceFlakinessByIgnoringSecurityDomains();
		 */
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = GlobalVariables.getOperacionesEbsIntUsr;
		String password = GlobalVariables.getOperacionesEbsIntPwd;
		String CMuserName = GlobalVariables.CtrlM_User;
		String CMpassword = GlobalVariables.CtrlM_Pss;
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
		String FacNumber1 = data.get("Factura1");
		String FacNumber2 = data.get("Factura2");
		String FacNumber3 = data.get("Factura3");
		String FacNumber4 = data.get("Factura4");
		String repositorioIncoming = data.get("repositorioIncoming");
		String ftpHost = GlobalVariables.FTP_KYRIBA_Mexico_HOST;
		int ftpPort = GlobalVariables.FTP_KYRIBA_Mexico_PORT;
		String ftpUser = GlobalVariables.FTP_KYRIBA_Mexico_USER;
		String ftpPwd = GlobalVariables.FTP_KYRIBA_Mexico_PSW;

		/*
		 * TASA-0%IVA UTILERIAS
		 */

		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		SSHConnector sshConnector = new SSHConnector();
		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// PASO 1
		addStep("Ingresar a Control-M --> Ejecutar el job XXFC_AP_KYRIBA_OUT_SH\r\n" + "");

		u = new SeleniumUtil(new ChromeTest(), true);
		JSONObject obj = new JSONObject(data.get("JOB"));

		Control_mInicio CM = new Control_mInicio(u, CMuserName, CMpassword);

		u.get(data.get("ctrl_url"));

		u.waitForLoadPage();
		CM.logOn();

		addStep("Inicio de job");
		ControlM control = new ControlM(u, testCase, obj);

		control.searchJob();

		String resultado = control.executeJob();

		System.out.println("Resultado de la ejecuci�n: " + resultado);
		System.out.println("Valor de output : " + control.getOutput());

		u.close();

		// PASO 2
		addStep("Ingresar a EBS --> Clic en Language y seleccionar Espa�ol --> Ingresar usuario y contrase�a --> Clic en Con�ctese\r\n"
				+ "");

		u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		pomKyribaF2 kyriba = new pomKyribaF2(u, testCase, data);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		testCase.addScreenShotCurrentStep("save");
		inicio.login();
		p.popUpAuthenticacion();

		// PASO 3
		addStep("Seleccionar MXOXXO-APXXXXX-Super Usuario --> Otros --> Requests --> Run --> "
				+ "Clic en Cancelar --> Dirigirse al men� Ver --> Solicitudes --> Seleccionar Todas Mis Solicitudes --> Clic en Encontrar\r\n"
				+ "");

		paginaInicial.clickOnResponsabilidad(responsabilidad);
		kyriba.MXOXXOAPXXXXXSuperUsuarioOtrosRequestsRun();
		p.accepApp();
		p.pantallaCancelarSolicitud();
		assertTrue(p.pantallaEncontrarSolicitudes(), "No se pudo Ingresar a la Pantalla");
		testCase.addScreenShotCurrentStep("gg");
		p.clickEncontrarSolicitudes();

		// PASO 4
		addStep("Localizar el concurrente \"XXFC - Generar archivo de pagos para Kyriba\"\r\n" + "");

		kyriba.clickVisualizarRegistro();

		// PASO 5
		addStep("Dar clic en el bot�n Visualizar Registro\r\n" + "");

		assertTrue(kyriba.VisualizarRegistroFinalizadoNormal(), "No se visualiza el registro");

		// p.cerrarApplicacion();
		// u.hardWait(3);
		u.getDriver().quit();

		// PASO 6
		addStep("Ingresar al servidor de EBS --> Localizar el repositorio de Kyriba: /ebs_data/outcoming/kyriba\r\n"
				+ "");
		sshConnector.connect(ftpUser, ftpPwd, ftpHost, ftpPort);
		String dirIncomingPgpFiles;
		assert sshConnector.isConected();
		String[] pgpFiles = new String[0];
		if (sshConnector.isConected()) {
			logger.info("Conectado al repositorio de Kyriba Internacional " + repositorioIncoming);
			testCase.addTextEvidenceCurrentStep(
					"Conectado al repositorio de Kyriba Internacional " + repositorioIncoming);

			// PASO 7
			addStep("Validar que se visualicen los archivos generados\r\n" + "");
			String dirIncoming = sshConnector.executeCommand("ls -l " + repositorioIncoming + " | grep '^-'");
			System.out.println("Resultado " + dirIncoming);
			assertFalse(dirIncoming.isEmpty(), "El directorio " + dirIncoming + " no tiene archivos ");

			dirIncomingPgpFiles = sshConnector.executeCommand(
					"find " + repositorioIncoming + " -type f -name '*.PGP' -maxdepth 1 -exec basename {}  \\;");
			assertFalse(dirIncomingPgpFiles.isEmpty(), "No hay archivos *PGP");
			String separador = "n";
			pgpFiles = dirIncomingPgpFiles.split(separador);
			testCase.addBoldTextEvidenceCurrentStep("El directorio tiene archivos\n");
			String filePGP = Arrays.toString(pgpFiles);
			testCase.addTextEvidenceCurrentStep(filePGP);
			System.out.println("El directorio tiene archivos " + filePGP);

			// PASO 8
			addStep("\"Validar que los nombres de los archivos cuenten con el siguiente formato esperado. Ejemplo: \r\n"
					+ "FEMSATMS.NC4.IMPORT.2022042217291500002.PY_TRANSFER.PY_ST_00116_FC.NULL.NULL.GPG\"\r\n" + "");

			String regex = "^FEMSATMS\\.NC4\\.IMPORT\\.\\d{19}\\.PY_TRANSFER\\.PY_[A-Z]{2}_\\d{5}_FC\\.NULL\\.NULL\\.PGP$";

			for (String archivo : pgpFiles) {
				archivo = archivo.trim().replaceAll("\r", ""); // Limpieza adicional
				System.out.println("Validando: " + archivo); // Debug
				Assert.assertTrue(archivo.matches(regex), "Formato inv�lido: " + archivo

				);
			}

			testCase.addTextEvidenceCurrentStep(filePGP);
			// PASO 9
			addStep("Validar que los archivos se encuentren encriptados (extensi�n .PGP)\r\n" + "");
			for (String archivo : pgpFiles) {
				// Verificar extensi�n
				Assert.assertTrue(archivo.endsWith(".PGP"), "Extensi�n incorrecta: " + archivo);

				// Verificar encriptaci�n v�a SSH
				String comando = "file " + repositorioIncoming + "/" + archivo;
				String resultado1 = sshConnector.executeCommand(comando);

				Assert.assertTrue(resultado1.contains("PGP") || resultado1.contains("encrypted"),
						"Archivo no encriptado: " + archivo + "\nResultado: " + resultado1);
			}
			testCase.addTextEvidenceCurrentStep(filePGP);
		}
		sshConnector.disconnect();
		// PASO 10
		addStep("Ingresar a la base de datos de EBS \r\n" + "");
		SQLUtil dbEBS = new SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
				GlobalVariables.DB_PASSWORD_AVEBQA);
		String coneccion = "SELECT SYS_CONTEXT('USERENV', 'DB_NAME') FROM DUAL";
		testCase.addTextEvidenceCurrentStep("Conexion a la base de datos EBS exitosa");
		SQLResult Connect = dbEBS.executeQuery(coneccion);
		testCase.addQueryEvidenceCurrentStep(Connect);
		// PASO 11
		addStep("Validar en la tabla XXFC.XXFC_AP_KYRIBA_PAGOS el estatus de los pagos\r\n" + "");
		String query = "SELECT XXFC.XXFC_AP_KYRIBA_PAGOS.TRANSACTION_CODE,\r\n"
				+ "  XXFC.XXFC_AP_KYRIBA_PAGOS.REFERENCE_NUM,\r\n" + "  XXFC.XXFC_AP_KYRIBA_PAGOS.DEBIT_ACCOUNT,\r\n"
				+ "  XXFC.XXFC_AP_KYRIBA_PAGOS.TRANSACTION_AMOUNT,\r\n"
				+ "  XXFC.XXFC_AP_KYRIBA_PAGOS.PROCESS_STATUS,\r\n" + "FROM XXFC.XXFC_AP_KYRIBA_PAGOS\r\n"
				+ "WHERE XXFC.XXFC_AP_KYRIBA_PAGOS.REFERENCE_NUM in ('"+FacNumber1+"','"+FacNumber1+"','"+FacNumber1+"','"+FacNumber1+"')"
				+ "and XXFC.XXFC_AP_KYRIBA_PAGOS.PROCESS_STATUS = 'F' ";
		SQLResult result = dbEBS.executeQuery(query);
		testCase.addQueryEvidenceCurrentStep(result);
		assertTrue(result.getAllData().size() > 0); // Verificar que se localizan n�meros de pago

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_012_HU02_Job_XXFC_AP_KYRIBA_OUT_SH_LoteValidoInvalido";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al ejecutar el job XXFC_AP_KYRIBA_OUT_SH con lote valido e invalido solo procese el correcto\r\n"
				+ "";
	}

	@Override
	public String setTestDesigner() {
		return "GilbertoA@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return "ATC_FT_012_HU02_Job_XXFC_AP_KYRIBA_OUT_SH_LoteValidoInvalido";
	}

	@Override
	public String setPrerequisites() {
		return "\"-Contar con acceso a EBS\r\n" + "-Haber realizado un lote de pago para facturas en USD\r\n"
				+ "-Haber realizado un lote de pago para facturas USD incorrecto\"\r\n" + "";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}