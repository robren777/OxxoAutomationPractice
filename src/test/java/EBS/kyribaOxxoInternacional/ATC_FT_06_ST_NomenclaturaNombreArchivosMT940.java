package EBS.kyribaOxxoInternacional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONObject;
import org.sikuli.script.FindFailed;
import org.testng.annotations.Test;

import com.jcraft.jsch.JSchException;

import EBS.util.GlobalVariables;
import EBS.util.SSHConnector;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_06_ST_NomenclaturaNombreArchivosMT940 extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_06_ST_NomenclaturaNombreArchivosMT940_test(HashMap<String, String> data)
			throws JSchException, IOException, IllegalAccessException, FindFailed {
		/*
		 * VARIABLES
		 */
		String repositorioIncomingProcesados = data.get("repositorioIncomingProcesados");
		String file = data.get("file");
		String inicio = data.get("inicio");
		String stat = data.get("stat");
		String formato = data.get("formato");
		String extension = data.get("extension");
		String desen = data.get("desen");
		String pgpFile = null;
		String txtFile = null;
		JSONObject obj = new JSONObject(data.get("job"));

		/*
		 * FTP Kyriba Internacional
		 */

		String ftpHost = GlobalVariables.FTP_KYRIBA_INTERNACIONAL_HOST;
		int ftpPort = GlobalVariables.FTP_KYRIBA_INTERNACIONAL_PORT;
		String ftpUser = GlobalVariables.FTP_KYRIBA_INTERNACIONAL_USER;
		String ftpPwd = GlobalVariables.FTP_KYRIBA_INTERNACIONAL_PSW;

		/*
		 * Utilerias WEB
		 */
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		Control_mInicio CM = new Control_mInicio(u, GlobalVariables.CONTROL_M_USER, GlobalVariables.CONTROL_M_PWD);
		ControlM controlM = new ControlM(u, testCase, obj);

		/*
		 * Utilerías FTP
		 */
		SSHConnector sshConnector = new SSHConnector();

		// PASO 1
		addStep("Ingresar a Control-M --> y ejecutar el job '"+obj.getString("job")+"'");
		testCase.addTextEvidenceCurrentStep(obj.getString("job"));
		u.get(GlobalVariables.CONTROL_M_SERVER);
		u.hardWait(50);
		u.waitForLoadPage();
		CM.logOn();
		testCase.addScreenShotCurrentStep(u, "control m inicio");
		boolean flag = controlM.searchJob();
		assertFalse(!flag, "No se encontro el job");

		System.out.println("Job Status " + controlM.getCurrentStatus());
		// Ejecucion
		String resultadoDelJon = controlM.executeJob();
		testCase.addTextEvidenceCurrentStep("Resultado de la ejecucion " + resultadoDelJon);
		System.out.println("Resultado de la ejecucion " + resultadoDelJon);

		// Valor del output
		testCase.addTextEvidenceCurrentStep("Output " + controlM.getOutput());
		System.out.println("Output " + controlM.getOutput());

		// Validacion del caso
		if (controlM.getOutput().toLowerCase().contains("error")) {
        	testCase.addTextEvidenceCurrentStep("Fallo la ejecucion del job");
			throw new AssertionError("Existe un error en el Output");			
		}
        
		assertEquals(resultadoDelJon, "Wait Condition");
        controlM.closeViewpoint();
        u.hardWait(60);
		
		// PASO 2
		addStep("Validar que el archivo MT940 procesado sea desencriptado (.txt)");
		sshConnector.connect(ftpUser, ftpPwd, ftpHost, ftpPort);
		if (sshConnector.isConected()) {
			logger.info("Conectado al repositorio de Kyriba Internacional Procesados " + repositorioIncomingProcesados);
			testCase.addTextEvidenceCurrentStep(
					"Conectado al repositorio de Kyriba Internacional " + repositorioIncomingProcesados);
			assert sshConnector.isConected();
			pgpFile = sshConnector.executeCommand("find " + repositorioIncomingProcesados + " -type f -name '" + file + "' -maxdepth 1 -exec basename {}  \\;");
			txtFile = sshConnector.executeCommand("find " + repositorioIncomingProcesados + " -type f -name '" + file + ".txt' -maxdepth 1 -exec basename {}  \\;");
			pgpFile = pgpFile.substring(0, pgpFile.length() - 1);
			txtFile = txtFile.substring(0, txtFile.length() - 1);

		}
		sshConnector.disconnect();

		System.out.println("Archivos desencriptados \"*.txt\" \n" + pgpFile);
		assert pgpFile != null;
		assertFalse(pgpFile.isEmpty(),"No existen archivos \"*.txt\" en el repositorio " + repositorioIncomingProcesados + " " + pgpFile);
		testCase.addBoldTextEvidenceCurrentStep("Archivos desencriptados \"*.txt\" \n" + txtFile);
		// PASO 3
		addStep("Validar la nomenclatura del nombre del archivo MT940.EJEMPLO: FEMSATMS.NC4.EXPORT.2022111505065726254.STAT.ST_01647_FC.MT940.PGP");
		System.out.println(pgpFile.length());
		String inicioText = pgpFile.substring(0, 20);

		testCase.addTextEvidenceCurrentStep("Nomenclatura del archivo encriptado");
		if (pgpFile.length() == 66) {
			if (inicioText.length() == 20) {
				assertEquals(inicioText, inicio);
				testCase.addTextEvidenceCurrentStep("Generado por Kyriba");
				testCase.addTextEvidenceCurrentStep(inicioText);
				System.out.println(inicioText);
			}
			String fechaText = pgpFile.substring(20, 39);
			if (fechaText.length() == 19) {
				testCase.addTextEvidenceCurrentStep("Fecha");
				testCase.addTextEvidenceCurrentStep(fechaText);
				System.out.println(fechaText);
			}
			String statText = pgpFile.substring(39, 45);
			if (statText.length() == 6) {
				assertEquals(statText, stat);
				testCase.addTextEvidenceCurrentStep("Statement");
				testCase.addTextEvidenceCurrentStep(statText);
				System.out.println(statText);
			}
			String bancoText = pgpFile.substring(45, 47);
			if (bancoText.length() == 2) {
				testCase.addTextEvidenceCurrentStep("Banco");
				testCase.addTextEvidenceCurrentStep(bancoText);
				System.out.println(bancoText);
			}
			String separadorText = pgpFile.substring(47,48);
			if (separadorText.length()==1) {
				System.out.println(separadorText);
			}
			String sociedadText = pgpFile.substring(48,53);
			if(sociedadText.length()==5) {
				testCase.addTextEvidenceCurrentStep("Sociedad");
				testCase.addTextEvidenceCurrentStep(sociedadText);
				System.out.println(sociedadText);
			}
			String separadorText2 = pgpFile.substring(53,54);
			if(separadorText2.length()==1) {
				System.out.println(separadorText2);
			}
			String unidadText = pgpFile.substring(54,56);
			if(unidadText.length()==2) {
				testCase.addTextEvidenceCurrentStep("Unidad");
				testCase.addTextEvidenceCurrentStep(unidadText);
				System.out.println(unidadText);
			}
			String formatoText = pgpFile.substring(56,62);
			if(formatoText.length()==6) {
				assertEquals(formatoText, formato);
				testCase.addTextEvidenceCurrentStep("Formato");
				testCase.addTextEvidenceCurrentStep(formatoText);
				System.out.println(formatoText);
			}
			String extensionText = pgpFile.substring(62,66);
			if(extensionText.length()==4) {
				assertEquals(extensionText, extension);
				testCase.addTextEvidenceCurrentStep("Extensión");
				testCase.addTextEvidenceCurrentStep(extensionText);
				System.out.println(extensionText);
			}
		}
		System.out.println(txtFile.length());
		testCase.addScreenShotCurrentStep(u, "Nomenclatura del archivo desencriptado");
		if (txtFile.length() == 70) {
			if (inicioText.length() == 20) {
				assertEquals(inicioText, inicio);
				testCase.addTextEvidenceCurrentStep("Generado por Kyriba:");
				testCase.addTextEvidenceCurrentStep(inicioText);
				System.out.println(inicioText);
			}
			String fechaText = txtFile.substring(20, 39);
			if (fechaText.length() == 19) {
				testCase.addTextEvidenceCurrentStep("Fecha:");
				testCase.addTextEvidenceCurrentStep(fechaText);
				System.out.println(fechaText);
			}
			String statText = txtFile.substring(39, 45);
			if (statText.length() == 6) {
				assertEquals(statText, stat);
				testCase.addTextEvidenceCurrentStep("Statement:");
				testCase.addTextEvidenceCurrentStep(statText);
				System.out.println(statText);
			}
			String bancoText = txtFile.substring(45, 47);
			if (bancoText.length() == 2) {
				testCase.addTextEvidenceCurrentStep("Banco:");
				testCase.addTextEvidenceCurrentStep(bancoText);
				System.out.println(bancoText);
			}
			String separadorText = txtFile.substring(47,48);
			if (separadorText.length()==1) {
				System.out.println(separadorText);
			}
			String sociedadText = txtFile.substring(48,53);
			if(sociedadText.length()==5) {
				testCase.addTextEvidenceCurrentStep("Sociedad:");
				testCase.addTextEvidenceCurrentStep(sociedadText);
				System.out.println(sociedadText);
			}
			String separadorText2 = txtFile.substring(53,54);
			if(separadorText2.length()==1) {
				System.out.println(separadorText2);
			}
			String unidadText = txtFile.substring(54,56);
			if(unidadText.length()==2) {
				testCase.addTextEvidenceCurrentStep("Unidad:");
				testCase.addTextEvidenceCurrentStep(unidadText);
				System.out.println(unidadText);
			}
			String formatoText = txtFile.substring(56,62);
			if(formatoText.length()==6) {
				assertEquals(formatoText, formato);
				testCase.addTextEvidenceCurrentStep("Formato:");
				testCase.addTextEvidenceCurrentStep(formatoText);
				System.out.println(formatoText);
			}
			String extensionText = txtFile.substring(62,66);
			if(extensionText.length()==4) {
				assertEquals(extensionText, extension);
				testCase.addTextEvidenceCurrentStep("Extension:");
				testCase.addTextEvidenceCurrentStep(extensionText);
				System.out.println(extensionText);
			}
			String desenText = txtFile.substring(66,70);
			if(desenText.length()==4) {
				assertEquals(desenText, desen);
				testCase.addTextEvidenceCurrentStep("Desencriptado");
				testCase.addTextEvidenceCurrentStep(desenText);
				System.out.println(desenText);
			}
		}

		u.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_02_ObtenerArchivos_MT940_DesdeSandboxABuzonDeKyribaMedianteJob_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar la nomenclatura del nombre de los archivos MT940:FEMSATMS.NC4.EXPORT.2022111505065726254.STAT.ST_01647_FC.MT940.PGP.txt *Contar con acceso al repositorio de Kyriba";
	}

	@Override
	public String setTestDesigner() {
		return "JessicaR@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "*Contar con acceso al repositorio de Kyriba";
	}

	@Override
	public void beforeTest() {

	}
}