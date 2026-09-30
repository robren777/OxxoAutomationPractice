package EBS.kyribaOxxoInternacional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.SSHConnector;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.ebsInternational.EBSPaginaInicialInternacional;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_22_ValidarNoSePuedaProcesarArchivoMT940MasDeUnaVez extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_22_ValidarNoSePuedaProcesarArchivoMT940MasDeUnaVez_test(HashMap<String, String> data)
			throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = GlobalVariables.getEbsInternacional;
		String userName = GlobalVariables.getOperacionesEbsIntUsr;
		String password = GlobalVariables.getOperacionesEbsIntPwd;
		String ebsPattern = data.get("ebsPattern");
		String responsabilidad = data.get("responsabilidad");
		String fileName = data.get("fileName");
		String text = data.get("text");
		String repositorioIncoming = data.get("repositorioIncoming");
		String repositorioIncomingProcesados = data.get("repositorioIncomingProcesados");
		String file = data.get("file");
		String tabla = data.get("tabla");
		JSONObject obj = new JSONObject(data.get("job"));

		String consulta1 = "SELECT  FILE_ID, DATE_PROCESSED, STATUS, FILE_NAME FROM "+tabla+" WHERE FILE_NAME LIKE '" + file + "%'";

		/*
		 * FTP Kyriba Internacional
		 */

		String ftpHost = GlobalVariables.FTP_KYRIBA_INTERNACIONAL_HOST;
		int ftpPort = GlobalVariables.FTP_KYRIBA_INTERNACIONAL_PORT;
		String ftpUser = GlobalVariables.FTP_KYRIBA_INTERNACIONAL_USER;
		String ftpPwd = GlobalVariables.FTP_KYRIBA_INTERNACIONAL_PSW;

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicialInternacional paginaInicial = new EBSPaginaInicialInternacional(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+ebsPattern);
		Pattern pathimg = new Pattern();
		SSHConnector sshConnector = new SSHConnector();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		File Folder = new File(pathImg);

		Control_mInicio CM = new Control_mInicio(u, GlobalVariables.CONTROL_M_USER, GlobalVariables.CONTROL_M_PWD);
		ControlM controlM = new ControlM(u, testCase, obj);

		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);

		utils.sql.SQLUtil EBSOIEBSBDQ = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_OIEBSBDQ,
				GlobalVariables.DB_USER_OIEBSBDQ, GlobalVariables.DB_PASSWORD_OIEBSBDQ);

		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: KYRIBA INTERNACIONAL
		 */

		// PASO 1
		addStep("Ingresar a la BD de EBS INT --> Consultar la tabla XXFC_KYRIBA_BITACORA_MT940");//XXPE.XXPE_KYRIBA_BITACORA_MT940");
		// u.close();
		SQLResult validat = EBSOIEBSBDQ.executeQuery(consulta1);
		testCase.addQueryEvidenceCurrentStep(validat);
		//String atributo = validat.getData(0, "FILE_NAME");
		//System.out.print(atributo);
		boolean validar = validat.isEmpty();//atributo.equals(fileName);	
		assertFalse(validar, "No se encontro informaci�n del archivo indicado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		
		
		// PASO 2
		addStep("Colocar en el repositorio de Kyriba un archivo MT940 procesado con anterioridad con los permisos 666");
		sshConnector.connect(ftpUser, ftpPwd, ftpHost, ftpPort);
		if (sshConnector.isConected()) {
			logger.info("Conectado al repositorio de Kyriba Internacional Procesados " + repositorioIncomingProcesados);
			testCase.addTextEvidenceCurrentStep(
					"Conectado al repositorio de Kyriba Internacional " + repositorioIncomingProcesados);
			assert sshConnector.isConected();

			String dirIncoming = sshConnector.executeCommand("find " + repositorioIncomingProcesados
					+ " -type f -name '" + file + "'  -maxdepth 1 -exec basename {}  \\;");
			logger.info("Archivos en el directorio \n" + repositorioIncomingProcesados + "\n \n" + dirIncoming);
			testCase.addTextEvidenceCurrentStep("Archivos en el directorio \n" + repositorioIncomingProcesados);
			testCase.addTextEvidenceCurrentStep(dirIncoming);
			System.out.println("Archivos en el directorio \n" + repositorioIncomingProcesados + "\n \n" + dirIncoming);
			assertFalse(dirIncoming.isEmpty());
			testCase.addTextEvidenceCurrentStep("El directorio esta vacio");
			System.out.println("El directorio esta vacio");
			
			System.out.println("mv " + repositorioIncomingProcesados + "/" + file + " " + repositorioIncoming);
			String moveFile = sshConnector.executeCommand("mv " + repositorioIncomingProcesados + "/" + file + " " + repositorioIncoming);
			String changingPermissions = sshConnector.executeCommand("chmod 666 " + repositorioIncoming + "/" + file);
			logger.info("Archivos en el directorio \n" + repositorioIncoming + "\n \n" + moveFile);
			testCase.addTextEvidenceCurrentStep(moveFile);
			System.out.println("Archivos en el directorio \n" + repositorioIncoming + "\n \n" + moveFile);
			assertFalse(moveFile.isEmpty());
			testCase.addTextEvidenceCurrentStep("El archivo se movio de carpeta");
			System.out.println("El archivo se movio de carpeta");
			
			System.out.println("chmod 666 " + repositorioIncoming + "/" + file);
			
		}
		sshConnector.disconnect();
		
		// PASO 3
		addStep("Ejecutar el concurrente 'XXPE - Kyriba pre-carga de estados de cuenta' por medio del job '"+obj.getString("job")+"'");  //'XXPE_CE_KYRIBA_IN_ECTA_SH_PERU'"
		
		testCase.addTextEvidenceCurrentStep(obj.getString("job"));
		u.get(GlobalVariables.CONTROL_M_SERVER); u.hardWait(50); u.waitForLoadPage();
		CM.logOn(); testCase.addScreenShotCurrentStep(u, "control m inicio"); boolean
		flag = controlM.searchJob(); assertFalse(!flag, "No se encontro el job");
		
		System.out.println("Job Status " + controlM.getCurrentStatus()); 
		//Ejecucion
		String resultadoDelJon = controlM.executeJob();
		testCase.addTextEvidenceCurrentStep("Resultado de la ejecucion " +
		resultadoDelJon); System.out.println("Resultado de la ejecucion " +
		resultadoDelJon);
		 
		 //Valor del output 
		 testCase.addTextEvidenceCurrentStep("Output " +controlM.getOutput()); System.out.println("Output " + controlM.getOutput());
		  
		 //Validacion del caso
		 if (controlM.getOutput().toLowerCase().contains("error")) {
	        	testCase.addTextEvidenceCurrentStep("Fallo la ejecucion del job");
				throw new AssertionError("Existe un error en el Output");			
			}   
		assertEquals(resultadoDelJon, "Wait Condition");
	    controlM.closeViewpoint();
	    //u.hardWait(60);
		
		// PASO 4
		addStep("Ingresar a EBS Internacional --> Dirigirse a la responsabilidad "+responsabilidad+" -->  Dirigirse a Ver --> Solicitudes --> Encontrar");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		inicio.login();
		addStep("Ingresar a la responsabilidad '"+responsabilidad+"'");
		paginaInicial.clicEnResponsabilidad(responsabilidad);
		paginaInicial.clickRunconsultasPeCMSU();
		testCase.addScreenShotCurrentStep(u,"peCmSuConsultas");
		u.hardWait(60);
        patternUtil.runApplet();
        u.hardWait(5);
        patternUtil.runApplet();
      //patternUtil.runAppletEbsInternacional();
        u.hardWait(30);
		// PASO 5
		addStep("Revisar el log del concurrente dando clic en 'Visualizar Registro' y validar que se muestre el mensaje: El archivo ya fue cargado con anterioridad: NOMBRE_ARCHIVO ");
		addStep("Dirigirse al menú Ver(x) -> Solicitudes -> Encontrar.");
		patternUtil.closeWindowInsideApplet();
		u.hardWait(3);
		patternUtil.encontrarSolicitudes();
		testCase.addScreenShotCurrentStep(u,"peCmSuConsultas");
		patternUtil.keyAlt("o");
		u.hardWait(5);
		/*if(patternUtil.validatePattern("Xxcl_KyrivaCaragaDeEst")) {
		patternUtil.clickPattern("Xxcl_KyrivaCaragaDeEst", 10);
	     }*/	
		patternUtil.verificarEstado("ejecutandoVerde", 10);
		testCase.addScreenShotCurrentStep("clVisualizarProceso");
		patternUtil.keyAlt("l");
		u.hardWait(5);
		String textFile = text + file + ".txt";
		testCase.addScreenShotCurrentStep("clVisualizarOutput");
		assertEquals(patternUtil.getTextDelNavegador("kyribaCarga", textFile), textFile);
		u.hardWait(3);
		patternUtil.closeWindow();
		u.hardWait(3);

	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Validar que no se permita carga de CR sin Codigo Postal";
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
		return "Validar que no se permita carga de CR sin Codigo Postal";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}