package EBS.simetrik.CHG0049983;

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
 * Ejecuta el JOB encargado de generar el documento csv de BUS
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCA:
 * MTC-FT-013-Generar archivo CSV de BUS Automaticamente (Job)
 * 
 * PREREQUISITOS:
 * Haber solicitado el JOB: XXFC_GL_SIMETRIK_BUS_OUT_SH del Folder: TQA_SIMETRIK, con STATUS WAIT FOR EVENT y HOLDEADO. Para el usuario especificado en el Data Provider
 * 
 * INSUMOS:
 * -Usuario y password para CTRL-M
 * -User, password, host y puerto para conexion al servidor SFTP de EBS QA
 * -Ruta donde se genera el archvio CSV
 * 
 * @author 1000068915 - Jorge Hernandez
 * @date 19/Diciembre/2024
 */

public class ATC_FT_013_GenerarArchivoCSV_BUS_Automatica extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_013_GenerarArchivoCSV_BUS_Automatica_test(HashMap<String, String> data)
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
		String docName =  data.get("docName");

		boolean result;

		/*
		 * UTILERIAS
		 */
		u = new SeleniumUtil(new ChromeTest(), true);
		pomSimetrik pom = new pomSimetrik(u, testCase);

		// PASO 1 - 2
		addStep("Ingresar a CTRL-M con la liga: http://10.184.56.194:18080/ControlM/ ");
		testCase.addBoldTextEvidenceCurrentStep("Iniciar sesion en CTRL-M.");
		result = pom.getControlM(user, password, url);
		assertTrue(result, "Fallo al iniciar sesion en CTRL-M");

		// PASO 3
		addStep("Ejecutar el JOB: XXFC_GL_SIMETRIK_BUS_OUT_SH \r\n"
				+ "\r\n"
				+ "Folder: TQA_SIMETRIK. ");
		
		String startTime = pom.releaseJOB(data.get("cm_xxfc_gl_simetrik_bus_out_sh_RELEASE"));
		
		result = pom.ejecutarJOBStatus(startTime, data.get("cm_xxfc_gl_simetrik_bus_out_sh_RUN_NOW"), data.get("cm_xxfc_gl_simetrik_bus_out_sh_RERUN"));
		assertTrue(result, "No se ha ejecutado exitosamente el JOB");
		
		result = pom.holdJOB(data.get("cm_xxfc_gl_simetrik_bus_out_sh_HOLD"));
		assertTrue(result, "No se ha Holdeado el JOB");
		
		// PASO 4 
		addStep("Realizar la conexion al servidor: \r\n"
				+ " HOST: 10.184.48.60 \r\n"
				+ " PUERTO: 22 \r\n"
				+ " USER NAME: usdload \r\n"
				+ " PASSWORD: ********* ");
		result = pom.validarConexion(sftpHOST, sftpPORT, sftpUser, sftpPassword);
		assertTrue(result, "No se ha realizado la conexion exitosamente");
		
		// PASO 5
		addStep("Validar los archivos creados por el JOB en la siguiente ruta: /ebs_data/outcoming/simetrik/F2_BUS_EBS");
		result = pom.validarCSVGenerado(sftpHOST, sftpPORT, sftpUser, sftpPassword, ruta, docName);
		assertTrue(result, "Fallo al verificar la informacion del archivo");
		
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_013_GenerarArchivoCSV_BUS_Automatica_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se genere el archivo CSV de BUS de EBS al ejecutar el Job correspondiente.";
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
		return "Haber solicitado el JOB: XXFC_GL_SIMETRIK_BUS_OUT_SH del Folder: TQA_SIMETRIK, con STATUS WAIT FOR EVENT y HOLDEADO. Para el usuario especificado en el Data Provider";
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
