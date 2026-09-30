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

public class ATC_FT_15_ValidarProcesoEstadosCuentaMedianteEjecucionJob  extends BaseExecution{
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_15_ValidarProcesoEstadosCuentaMedianteEjecucionJo_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = GlobalVariables.getEbsInternacional;
        String userName = GlobalVariables.getOperacionesEbsIntUsr;
        String password = GlobalVariables.getOperacionesEbsIntPwd;
        String ebsPattern = data.get("ebsPattern");
        String responsabilidad = data.get("responsabilidad");
        String text = data.get("text");
        String fileName = data.get("fileName");
		String statementNumber = data.get("statementNumber");
		String estado = data.get("estado");
		JSONObject obj = new JSONObject(data.get("job"));
		
		String consulta1 = "SELECT STATEMENT_NUMBER, ESTADO, LAST_UPDATE_DATE FROM apps.XXCL_CE_KYRIBA_EDOS_BANCA WHERE FILE_NAME LIKE '"+fileName+"%'";
		//String consulta2 = "SELECT * FROM apps.ce_statement_headers_int WHERE STATEMENT_NUMBER = '"+statementNumber+"'";
		//String consulta3 = "SELECT * FROM apps.ce_statement_lines_interface WHERE STATEMENT_NUMBER = '"+statementNumber+"'";
		String consultaQ2P1 = "SELECT BANK_ACCOUNT_NUM, BANK_NAME, CONTROL_TOTAL_DR, STATEMENT_NUMBER FROM apps.ce_statement_headers_int WHERE STATEMENT_NUMBER = '%s'";
		String consultaQ2P2 = "SELECT CONTROL_TOTAL_CR, CONTROL_BEGIN_BALANCE, CONTROL_END_BALANCE, CREATION_DATE FROM apps.ce_statement_headers_int WHERE STATEMENT_NUMBER = '%s'";
		
		String consultaQ3P1 = "SELECT BANK_ACCOUNT_NUM, LINE_NUMBER, TRX_CODE, AMOUNT, STATEMENT_NUMBER FROM apps.ce_statement_lines_interface WHERE STATEMENT_NUMBER = '%s'";
		String consultaQ3P2 = "SELECT TRX_TEXT, CURRENCY_CODE, BANK_TRX_NUMBER, CREATION_DATE FROM apps.ce_statement_lines_interface WHERE STATEMENT_NUMBER = '%s'";

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicialInternacional paginaInicial = new EBSPaginaInicialInternacional(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+ebsPattern);
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		File Folder = new File(pathImg);
		
		Control_mInicio CM = new Control_mInicio(u, GlobalVariables.CONTROL_M_USER, GlobalVariables.CONTROL_M_PWD);
	    ControlM controlM = new ControlM(u, testCase, obj);
	    
	    MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);
		
		utils.sql.SQLUtil EBSOIEBSBDQ = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_OIEBSBDQ, GlobalVariables.DB_USER_OIEBSBDQ, GlobalVariables.DB_PASSWORD_OIEBSBDQ);

		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: KYRIBA INTERNACIONAL
		 */
		
		// PASO 1
		addStep("Ingresar a Control-M --> Ejecutar el job "+obj.getString("job"));//'XXPE_CE_KYRIBA_INSERT_INTERFACE_SH'");
		testCase.addTextEvidenceCurrentStep(obj.getString("job"));
        u.get(GlobalVariables.CONTROL_M_SERVER);
        u.hardWait(50);
        u.waitForLoadPage();
        CM.logOn();
        testCase.addScreenShotCurrentStep(u, "control m inicio");
        boolean flag = controlM.searchJob();
        assertFalse(!flag, "No se encontro el job");

        System.out.println("Job Status " + controlM.getCurrentStatus());
        //Ejecucion
        String resultadoDelJon = controlM.executeJob();
        testCase.addTextEvidenceCurrentStep("Resultado de la ejecucion " + resultadoDelJon);
        System.out.println("Resultado de la ejecucion " + resultadoDelJon);

        //Valor del output
        testCase.addTextEvidenceCurrentStep("Output " + controlM.getOutput());
        System.out.println("Output " + controlM.getOutput());

        //Validacion del caso
        if (controlM.getOutput().toLowerCase().contains("error")) {
        	testCase.addTextEvidenceCurrentStep("Fallo la ejecucion del job");
			throw new AssertionError("Existe un error en el Output");			
		}
        
		assertEquals(resultadoDelJon, "Wait Condition");
        controlM.closeViewpoint();
        //u.hardWait(60);
        
		// PASO 2
		addStep("Dirigirse a la responsabilidad "+responsabilidad+" --> Ver --> Solicitudes --> Todas Mis Solicitudes --> Encontrar ");
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
		
		addStep("Dirigirse al menu Ver(x) -> Solicitudes -> Encontrar.");
		patternUtil.closeWindowInsideApplet();
		u.hardWait(3);
		patternUtil.encontrarSolicitudes();
		testCase.addScreenShotCurrentStep("clEncontrarSolicitudes");
		patternUtil.keyAlt("o");
		u.hardWait(5);
		// PASO 3
		addStep("Dar clic en 'Output' para visualizar la salida del proceso 'Kyriba carga de estados de cuenta'");	
		/*if(patternUtil.validatePattern("Xxcl_KyrivaCaragaDeEst")) {
		patternUtil.clickPattern("Xxcl_KyrivaCaragaDeEst", 10);
	   }*/	
		patternUtil.verificarEstado("ejecutandoVerde", 10);			
		testCase.addScreenShotCurrentStep("clVisualizarProceso");
		patternUtil.keyAlt("l");
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("clVisualizarOutput");
		//assertEquals(patternUtil.getTextDelNavegador("kyribaCarga", text), text);
		u.hardWait(3);
		patternUtil.closeWindow();
		u.hardWait(30);
		
		// PASO 4
		addStep("Ingresar a la base de datos de EBS INTERNACIONAL --> Validar el estatus 'PROCESADO' en el repositorio intermedio (xxpe.xxpe_ce_kyriba_edos_banca)");
		SQLResult validat = EBSOIEBSBDQ.executeQuery(consulta1);
		String atributo = "", atributo1 = "";
		if(!validat.isEmpty()){
			atributo = validat.getData(0, "ESTADO");
			atributo1 = validat.getData(0, "STATEMENT_NUMBER");
		}
		testCase.addQueryEvidenceCurrentStep(validat);
		boolean validar = atributo.equals(estado);
		System.out.print(atributo);
		assertFalse(!validar, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		
		// PASO 5
		addStep("Validar la informacion del estado de cuenta en la tablas de CASH: *apps.ce.statement_headers_int *apps.ce_statement_lineas_interface");
		consultaQ2P1 = String.format(consultaQ2P1, atributo1);
		consultaQ2P2 = String.format(consultaQ2P2, atributo1);
		SQLResult validat2 = EBSOIEBSBDQ.executeQuery(consultaQ2P1);
		SQLResult validat2p2 = EBSOIEBSBDQ.executeQuery(consultaQ2P2);
		String atributo2 = "";
		if(!validat2.isEmpty()){
			atributo2 = validat2.getData(0, "STATEMENT_NUMBER");
		}
		testCase.addQueryEvidenceCurrentStep(validat2);
		testCase.addQueryEvidenceCurrentStep(validat2p2);
		boolean validar2 = validat2.isEmpty();//atributo2.equals(statementNumber);
		System.out.print(atributo2);
		assertFalse(validar2, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");

		consultaQ3P1 = String.format(consultaQ3P1, atributo1);
		consultaQ3P2 = String.format(consultaQ3P2, atributo1);
		SQLResult validat3 = EBSOIEBSBDQ.executeQuery(consultaQ3P1);
		SQLResult validat3p2 = EBSOIEBSBDQ.executeQuery(consultaQ3P2);
		String atributo3 = "";
		if(!validat3.isEmpty()){
			atributo3 = validat3.getData(0, "STATEMENT_NUMBER");
		}
		testCase.addQueryEvidenceCurrentStep(validat3);
		testCase.addQueryEvidenceCurrentStep(validat3p2);
		boolean validar3 = validat3.isEmpty();//atributo3.equals(statementNumber);
		System.out.print(atributo3);
		assertFalse(validar3, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		

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