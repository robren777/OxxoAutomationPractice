package EBS.mvp1_I24063_Conexion_KyribaF2;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.json.JSONObject;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import EBS.util.SSHConnector;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
 * DESCRIPCI�N: ""Validar el proceso de generaci�n del archivo de pagos genere
 * correctamente los archivos en el repositorio mediante la ejecuci�n del job
 * XXFC_AP_KYRIBA_OUT_SH -Ruta: /ebs_data/outcoming/kyriba -Pagos en la tabla
 * XXFC.XXFC_AP_KYRIBA_PAGOS con estatus en proceso = "F"
 *
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * -ATC_FT_019_HU03_ArchivoDePagoUSD PREREQUISITOS: " -Contar con acceso a EBS
 * -Haber realizado un lote de pago para facturas en USD -Contar con registros
 * de pagos en la tabla intermedia XXFC.XXFC_AP_KYRIBA_PAGOS con estatus en
 * proceso = ""P"""
 *
 * 
 * INSUMOS (datos o informaci�n que necesita estar preparada o generada para
 * poder ejecutar el script automatizado): -Nombre de Responsabilidad -Nombre de
 * Carpeta -Nombre de PROVEEDOR
 *
 * @author Gilberto Aguayo Adame
 * @date 4/03/2025
 */

public class ATC_FT_020_HU03_RepositorioArchivoDePago extends BaseExecution {
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_020_HU03_RepositorioArchivoDePago_test(HashMap<String, String> data) throws Exception {

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
		String FacNumber = data.get("FACTURA");
		String repositorioIncoming = data.get("repositorioIncoming");
		String ftpHost = GlobalVariables.FTP_KYRIBA_Mexico_HOST;
		int ftpPort = GlobalVariables.FTP_KYRIBA_Mexico_PORT;
		String ftpUser = GlobalVariables.FTP_KYRIBA_Mexico_USER;
		String ftpPwd = GlobalVariables.FTP_KYRIBA_Mexico_PSW;
		String Pago1 = data.get("PAGO1");
		String Pago2 = data.get("PAGO2");
		/*
		 * TASA-0%IVA UTILERIAS
		 */
		u = new SeleniumUtil(new ChromeTest(), true);

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

			// PASO 3
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
		}
		sshConnector.disconnect();
		// PASO 4
		addStep("Ingresar a la base de datos de EBS \r\n" + "");

		SQLUtil dbEBS = new SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
				GlobalVariables.DB_PASSWORD_AVEBQA);
		String coneccion = "SELECT SYS_CONTEXT('USERENV', 'DB_NAME') FROM DUAL";
		testCase.addTextEvidenceCurrentStep("Conexion a la base de datos EBS exitosa");
		SQLResult Connect = dbEBS.executeQuery(coneccion);
		testCase.addQueryEvidenceCurrentStep(Connect); // PASO 5
		addStep("Validar en la tabla XXFC.XXFC_AP_KYRIBA_PAGOS el estatus de los pagos\r\n" + "");
		String query = "SELECT XXFC.XXFC_AP_KYRIBA_PAGOS.TRANSACTION_CODE,\r\n"
				+ "  XXFC.XXFC_AP_KYRIBA_PAGOS.REFERENCE_NUM,\r\n" + "  XXFC.XXFC_AP_KYRIBA_PAGOS.DEBIT_ACCOUNT,\r\n"
				+ "  XXFC.XXFC_AP_KYRIBA_PAGOS.TRANSACTION_AMOUNT,\r\n"
				+ "  XXFC.XXFC_AP_KYRIBA_PAGOS.PROCESS_STATUS,"
				+ "FROM XXFC.XXFC_AP_KYRIBA_PAGOS\r\n" + "WHERE XXFC.XXFC_AP_KYRIBA_PAGOS.REFERENCE_NUM in ('" + Pago1
				+ "','" + Pago2 + "')"
						+ "and XXFC.XXFC_AP_KYRIBA_PAGOS.PROCESS_STATUS = 'F'";
		SQLResult result = dbEBS.executeQuery(query);
		testCase.addQueryEvidenceCurrentStep(result);
		assertTrue(result.getAllData().size() > 0); // Verificar que se localizan n�meros de pago

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_020_HU03_RepositorioArchivoDePago";
	}

	@Override
	public String setTestDescription() {
		return "\"Validar el proceso de generaci�n del archivo de pagos genere correctamente los archivos en el repositorio mediante la ejecuci�n del job XXFC_AP_KYRIBA_OUT_SH\r\n"
				+ "-Ruta: /ebs_data/outcoming/kyriba\r\n"
				+ "-Pagos en la tabla XXFC.XXFC_AP_KYRIBA_PAGOS con estatus en proceso = \"\"F\"\"\"\r\n" + "";
	}

	@Override
	public String setTestDesigner() {
		return "GilbertoA@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return "ATC_FT_020_HU03_RepositorioArchivoDePago";
	}

	@Override
	public String setPrerequisites() {
		return "\"-Contar con acceso a EBS\r\n" + "-Haber realizado un lote de pago para facturas en USD\r\n"
				+ "-Contar con registros de pagos en la tabla intermedia XXFC.XXFC_AP_KYRIBA_PAGOS con estatus en proceso = \"\"P\"\"\"\r\n"
				+ "";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}