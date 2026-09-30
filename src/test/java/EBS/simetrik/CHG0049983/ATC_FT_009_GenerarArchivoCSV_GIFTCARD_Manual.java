package EBS.simetrik.CHG0049983;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.EBSDeskApp.pomSimetrik;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/*
 * DESCRIPCION:
 * Ejecuta el concurrente en EBS encargado de generar el documento csv de Giftcard
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCA:
 * MTC-FT-009-Generar archivo CSV de GIFTCARD Manual (Concurrente)
 * 
 * PREREQUISITOS:
 * -Acceso a la responsabilidad 'MXOXXO-GL-BackOffice Conciliaciones SF y SNF'.
 * -Usar Google Chrome
 * -Acceso al aplicativo EBS
 * 
 * INSUMOS:
 * -Usuario y password para EBS
 * -Nombre del concurrente a ejcutar
 * -User, password, host y puerto para conexion al servidor SFTP de EBS QA
 * -Ruta donde se genera el archvio CSV
 * 
 * @author 1000068915 - Jorge Hernandez
 * @date 19/Diciembre/2024
 */

public class ATC_FT_009_GenerarArchivoCSV_GIFTCARD_Manual extends BaseExecution {
	
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")

	public void ATC_FT_009_GenerarArchivoCSV_GIFTCARD_Manual_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		String PatternsImg = "EBS_SIMETRIK";
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String concurrente = data.get("concurrente");
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
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		pomSimetrik pom = new pomSimetrik(u,testCase, PatternsImg);
		
		// PASO 1
		addStep("Ingresar a EBS mediante el el enlace: \r\n"
				+ "\r\n"
				+ ebsUrl);
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		inicio.loginInternacional();
		
		
		// PASO 2
		addStep("Dirigirse a la responsabilidad MXOXXO-GL-BackOffice Conciliaciones SF y SNF. Seleccionar la opcion Ejecutar Concurrentes ");
		testCase.addBoldTextEvidenceCurrentStep("");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		paginaInicial.clickEjecutarConcurrentes();
		pom.startEBS();
		
		// PASO 3
		addStep("Presionar el boton submit a New Request");
		result = pom.clicSubmitNewRequest();
		assertTrue(result, "Fallo al dar clic en el boton Submit a New Request");
	
		
		// PASO 4
		addStep("Ingresar el concurrente: \"XXFC - GL Giftcard Linea Envio Simetrik\" en el campo \"Name\" ");
		result = pom.fillNameConcurrente(concurrente);
		assertTrue(result, "Fallo al ingresar el nombre del concurrente en el campo Name");
		
		// PASO 5
		addStep("Seleccionar fecha inicio y fecha final. Dar clic en el Boton OK");
		result = pom.clicBtnOk();
		assertTrue(result, "Fallo al dar clic en el boton Ok");
		
		// PASO 6
		addStep("Dar clic en el boton Submit");
		result = pom.clicBtnSubmit();
		assertTrue(result, "Fallo al dar clic en el boton Submit");
		
		String id = pom.getRequestId();
		if(id.equals(null)) {
			result = false;
		}
		else {
			result = true;
		}
		assertTrue(result, "No se ha encontrado el id de la ejecucion");
		
		// PASO 7
		addStep("En la pantalla Decision dar clic en el boton No");
		result = pom.clicBtnDesicionNo();
		assertTrue(result, "Fallo al dar clic en el boton NO");
		
		
		// PASO 8
		addStep("Cerrar la pantalla Submit a request. En la pantalla principal seleccionar la opcion Ejecutar Concurrente");
		testCase.addBoldTextEvidenceCurrentStep("En flujo automatizado no es necesario ir a la pantalla principal y seleccinar la opcion Ejecutar Concurrente");
	
		// PASO 9
		addStep("Dar clic en el boton Find.");
		testCase.addBoldTextEvidenceCurrentStep("Se ingresan los valores de busqueda para obtener un resultado mas especifico");
		result = pom.fillFindSpecificRequest(id);
		assertTrue(result, "No se encontro el concurrente ejecutado");
		
		// PASO 10
		addStep("Seleccionar concurrente ejecutado y dar clic en el boton View Log");
		result = pom.clicBtnViewLog();
		assertTrue(result, "No se visualiza el log del concurrente ejecutado");
		
		// PASO 11 - 12
		addStep("Realizar la conexion al servidor: \r\n"
				+ " HOST: 10.184.48.60 \r\n"
				+ " PUERTO: 22 \r\n"
				+ " USER NAME: usdload \r\n"
				+ " PASSWORD: ********* ");
		result = pom.validarConexion(sftpHOST, sftpPORT, sftpUser, sftpPassword);
		assertTrue(result, "No se ha realizado la conexion exitosamente");
		
		addStep("Validar los archivos creados por el JOB en la siguiente ruta: /ebs_data/outcoming/simetrik/F2_GIFTCARD_EBS");
		result = pom.validarCSVGenerado(sftpHOST, sftpPORT, sftpUser, sftpPassword, ruta, docName);
		assertTrue(result, "Fallo al verificar la informacion del archivo");
		
		
		

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_009_GenerarArchivoCSV_GIFTCARD_Manual_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se genere el archivo CSV de Giftcard de EBS al ejecutar el concurrente de manera Manual en EBS.";
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
		return "Acceso a la responsabilidad 'MXOXXO-GL-BackOffice Conciliaciones SF y SNF'.";
	}

	@Override
	public void beforeTest() {

	}
	
	@AfterMethod
	public void afterMethod() {
		try {
            Process process = Runtime.getRuntime().exec(GlobalVariables.User_Dir + "\\tools\\killtask.bat");
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
