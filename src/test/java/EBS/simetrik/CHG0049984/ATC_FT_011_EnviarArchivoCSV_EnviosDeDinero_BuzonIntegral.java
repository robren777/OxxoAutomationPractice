package EBS.simetrik.CHG0049984;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import EBS.util.EBSDeskApp.pomSimetrik;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/*
 * DESCRIPCION:
 * Ejecuta el JOB encargado de enviar los archivos CSV al Buzon Integral
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCA:
 * MTC-FT-011-Enviar archivo CSV de ENVIO DE DINERO a buz�n integral (Job)
 * 
 * PREREQUISITOS:
 * Haber solicitado el JOB: XXFC_GL_BZNIN_SIMETRIK_OUT del Folder: TQA_SIMETRIK. Para el usuario especificado en el Data Provider
 * 
 * INSUMOS:
 * -Usuario y password para CTRL-M
 * -User, password, host y puerto para conexion al servidor SFTP de EBS QA
 * -Ruta donde se envian los archios CSV
 * 
 * @author 1000068915 - Jorge Hernandez
 * @date 20/Diciembre/2024
 */

public class ATC_FT_011_EnviarArchivoCSV_EnviosDeDinero_BuzonIntegral extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_011_EnviarArchivoCSV_EnviosDeDinero_BuzonIntegral_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		String url = data.get("urlCtrlM");
		String user = data.get("userCtrlM");
		String password = PasswordUtil.decryptPassword(data.get("passwordCtrlM"));
		String sftpHOST = data.get("sftpHOST");
		int sftpPORT = Integer.parseInt(data.get("sftpPORT"));
		String sftpUser = data.get("sftpUser");
		String sftpPassword = PasswordUtil.decryptPassword(data.get("sftpPassword"));
		String ruta = data.get("ruta");

		boolean result;

		/*
		 * UTILERIAS
		 */
		u = new SeleniumUtil(new ChromeTest(), true);
		pomSimetrik pom = new pomSimetrik(u, testCase);
		
		// PASO 1 - 2
		addStep("Ingresar a CTRL-M con la liga: http://10.184.56.194:18080/ControlM/ ");
		testCase.addBoldTextEvidenceCurrentStep("");
		result = pom.getControlM(user, password, url);
		assertTrue(result, "Fallo al iniciar sesion en CTRL-M");

		// PASO 3
		addStep("Ejecutar el JOB: XXFC_GL_BZNIN_SIMETRIK_OUT_1 \r\n"
				+ "Folder: TQA_SIMETRIK");
		String status = pom.releaseJOB(data.get("cm_xxfc_gl_bznin_simetrik_out_1_RELEASE"));
		
		result = pom.ejecutarJOBStatus(status, data.get("cm_xxfc_gl_bznin_simetrik_out_1_RUN_NOW"), data.get("cm_xxfc_gl_bznin_simetrik_out_1_RERUN"));
		assertTrue(result, "No se ha ejecutado exitosamente el JOB");
		
		result = pom.rerunJOB(data.get("cm_xxfc_gl_bznin_simetrik_out_1_RERUN"));
		assertTrue(result, "No se ha seleccionado Rerun en el JOB");
		
		
		result = pom.holdJOB(data.get("cm_xxfc_gl_bznin_simetrik_out_1_HOLD"));
		assertTrue(result, "No se ha Holdeado el JOB");
		
		
		// PASO 4 - 5
		addStep("Realizar la conexion al buzon SFTP INTEGRAL: \r\n"
				+ "\r\n"
				+ "HOST: oxzaisdq01.FEMCOM.NET\r\n"
				+ "PUERTO: 22\r\n"
				+ "USER NAME: oxzsaisdq01\r\n"
				+ "PASSWORD: ******");
		result = pom.validarConexion(sftpHOST, sftpPORT, sftpUser, sftpPassword);
		assertTrue(result, "No se ha realizado la conexion exitosamente");
		
		addStep("Validar que se haya enviado el archivo CSV al buzon integral en la siguiente ruta: /QA/EBS_ENVIO_DE_DINERO");
		result = pom.validarCSVGenerados(sftpHOST, sftpPORT, sftpUser, sftpPassword, ruta);
		assertTrue(result, "No se vizualisa el log del concurrente ejecutado");
		
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_011_EnviarArchivoCSV_EnviosDeDinero_BuzonIntegral_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se envie el archivo CSV Envios de Dinero al buzon integral al ejecutar el Job correspondiente.";
	}

	@Override
	public String setTestDesigner() {
		return "1000068915 - Jorge Hernandez";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Haber solicitado el JOB: XXFC_GL_BZNIN_SIMETRIK_OUT del Folder: TQA_SIMETRIK. Para el usuario especificado en el Data Provider";
	}

	@Override
	public void beforeTest() {

	}

	@AfterMethod
	public void afterMethod() {
		try {
			u.getDriver().quit();
		} catch (Exception e) {

		}
	}
}
