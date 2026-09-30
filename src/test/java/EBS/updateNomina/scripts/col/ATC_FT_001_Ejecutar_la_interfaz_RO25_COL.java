package EBS.updateNomina.scripts.col;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.updateNomina.om.EBSPaginaInicialColombiaUpdNom;
import EBS.updateNomina.om.Login;
import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_Ejecutar_la_interfaz_RO25_COL extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_001_Ejecutar_la_interfaz_RO25_COL_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("serverEBSCol");
		String userName = data.get("usernameEBSCol");
		String password = PasswordUtil.decryptPassword(data.get("passwordEBSCol"));
		String controlMUser = data.get("controlMUser");
		String controlMPwd = data.get("controlMPwd");
		String cntUsername = data.get("cntUsername");
		String cntPassword =  data.get("cntPassword");
		String responsabilidad = data.get("responsabilidad");
		String nombre = data.get("nombre");
		String entidad = data.get("entidad");
		String compania = data.get("compania");
		String estadoFinanciero = data.get("estadoFinanciero");
		String crSuperior = data.get("crSuperior");
		String fecha_VI = data.get("fecha_VI");
		String prefijo = data.get("prefijo");
		String descripcion =  data.get("descripcion");


		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		//EBSPaginaInicialColombia paginaInicialCol = new EBSPaginaInicialColombia(u, testCase);
		EBSPaginaInicialColombiaUpdNom paginaInicialColUpdNom = new EBSPaginaInicialColombiaUpdNom(u, testCase);

		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		File Folder = new File(pathImg);

		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		

		JSONObject obj = new JSONObject(data.get("job"));
		Control_mInicio CM = new Control_mInicio(u, controlMUser, controlMPwd);
		ControlM controlM = new ControlM(u, testCase, obj);

		

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// PASO 1
		addStep("ingresar a Oracle EBS http://fcfin5.femcom.net:8010/");
		System.out.println("Paso 1: INICIO");
		inicio.loginColombia();
		System.out.println("Paso 1: FIN");
		
		// PASO 2
		System.out.println("Paso 2: INICIO");
		addStep("Ingresar a la Responsabilidad \"MXOXXO-GLXXXXX-Super Usuario\"");
		paginaInicialColUpdNom.clicEnResponsabilidad();
		System.out.println("Paso 2: FIN");

		// PASO 3
		addStep("Ingresar al apartado de CRs -Alta de centros de Responsabilidad\n");
		System.out.println("Paso 3: INICIO");
		paginaInicialColUpdNom.clicEnAltaDeCRs();
		System.out.println("Paso 3: FIN");

		// PASO 4
		addStep("\"Llenar los siguientes campos\n" +
				"Compa��a: 474\n" +
				"Edo. Fin. : 01BOG\n" +
				"Cr. Superior: 10BGA\n" +
				"Fecha Vigencia BI: a elegir (generalmente es la actual)\n" +
				"Adem�s se debe captura el prefijo y una descripci�n para el CR\n" +
				"NOTA: para hacer la b�squeda presionar Cntrl + F11\"\n");
		

		
		// PASO 5
		addStep("\".-Oprimir el bot�n \"\"Obtener CR Valido\"\" donde el sufijo se llenar� de manera autom�tica.\n" +
				"Seguido del bot�n \"\"Genera CRs\"\" \"\n");

		
		// PASO 6 
		addStep("\"Ingresar a controlM con usuario y contrase�a valido:\n" +
				"http://10.184.56.194:18080/ControlM/\"\n");
		System.out.println("Paso 6 ingresar a controlM: INICIO ");
		u.get(GlobalVariables.CONTROL_M_SERVER);
		u.waitForLoadPage();
		CM.logOn();
		testCase.addScreenShotCurrentStep(u, "control m inicio");
		System.out.println("Paso 6 ingresar a controlM: FIN");
		// PASO 7
		addStep("Ejecutar la interfaz runNewCRO25_COL carpeta: TQACO_WM_COL\n");
		addStep("Ejecutar interfaz S01 Carpeta: TQA_TEST_WM99_INT");
//		boolean flag = controlM.searchJob();
//		assertFalse(!flag, "No se encontro el job");
//		System.out.println("Job Status " + controlM.getCurrentStatus());
//		//Ejecucion
//		String resultadoDelJob = controlM.executeJob();
//		testCase.addTextEvidenceCurrentStep("Resultado de la ejecuci�n " + resultadoDelJob);
//		System.out.println("Resultado de la ejecuci�n " + resultadoDelJob);
//
//		//Valor del output
//		testCase.addTextEvidenceCurrentStep("Output " + controlM.getOutput());
//		System.out.println("Output " + controlM.getOutput());
//
//		//Validacion del caso
//		boolean testResult = true;
//		if (resultadoDelJob.equals("Failure")) {
//			testResult = false;
//			testCase.addTextEvidenceCurrentStep("Fallo la ejecuci�n del job ");
//			System.out.println("Fallo la ejecuci�n del job ");
//		}

//		assertTrue(testResult, "La ejecucion del job fue exitosa");
//		controlM.closeViewpoint();

		// PASO 8
		addStep("\"Ingresar  a la siguiente liga de CNT:\n" +
				"https://opvcntnp1.femcom.net:8443/pls/colombia/tiendas\n" +
				"para iniciar sesion\"\n");
		System.out.println("Paso 8: INICIO ");
		u.getDriver().quit();
		u = new SeleniumUtil(new MEdgeTest(), true);
		Login login = new Login(u, testCase);

		login.IngresarCntCol(cntUsername, cntPassword);
		System.out.println("Paso 8: FIN ");

		// PASO 9
		addStep("Ingresar plaza, En este caso es 10BGA y damos clic en \"CONTINUAR\"\n");
		System.out.println("Paso 9: INICIO ");
		login.SeleccionarPlaza(crSuperior);
		System.out.println("Paso 9: FIN ");

    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_001_Ejecutar_la_interfaz_RO25_COL";
    }

    @Override
    public String setTestDescription() {
        return "Ejecuci�n de la interfaz RO25(COL) envie la tienda nueva hacia CNT";
    }

    @Override
    public String setTestDesigner() {
        return "Mario Alberto De Le�n Rodr�guez";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }

    @Override
    public String setPrerequisites() {
        return "Acceso a EBS, CNT, Control M";
    }

    @Override
    public void beforeTest() {

    }
}