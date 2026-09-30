package WEB.updateNomina.scripts.mcr.mex;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import WEB.tools.LoginTool;
import WEB.updateNomina.om.EBSMex;
import WEB.updateNomina.om.UpdateNomina;
import WEB.util.GlobalVariables;
import WEB.util.pageObjectModel.EBSExtraccion;
import WEB.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeEspTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;


public class ATC_FT_002_Ejecutar_la_interfaz_OS10_MX_Sociedad_F099 extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_Ejecutar_la_interfaz_OS10_MX_Sociedad_F099_test(HashMap<String, String> data) throws Exception {

		//VARIABLES
		String ebsUrl = data.get("serverEBS");
		String userName = data.get("usernameEBS");
		String password = PasswordUtil.decryptPassword(data.get("passwordEBS"));
		String responsabilidad = data.get("responsabilidad");
		String controlMUser = data.get("controlMUser");
		String controlMPwd = PasswordUtil.decryptPassword(data.get("controlMPwd"));

		JSONObject obj = new JSONObject(data.get("job"));
		JSONObject obj2 = new JSONObject(data.get("job2"));
		
		String crSuperior = data.get("crSuperior");

		//UTILERIAS WEB
		SeleniumUtil u = new SeleniumUtil(new MEdgeEspTest(), true);//MEdgeTest(), true);

		Control_mInicio controlMInicio = new Control_mInicio(u, controlMUser, controlMPwd);
		ControlM controlMJob = new ControlM(u, testCase, obj);
		ControlM controlMJob2 = new ControlM(u, testCase, obj2);

		UpdateNomina updNom = new UpdateNomina(u, testCase);
		//UTILERIAS SIKULI
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("WEB\\"+"EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Folder = new File(pathImg);

		//EBS
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		EBSExtraccion extraccion = new EBSExtraccion(u, testCase);

		EBSMex ebsMex = new EBSMex(sk,u, testCase,pathimg,ListImage,ImgPatter,patterns,Folder);

		// PASO 1
		
		addStep("ingresar a Oracle EBS con usuario y contrasena valido: http://fcrebsqa.femcom.net:8011/OA_HTML/AppsLocalLogin.jsp");
		inicio.login();
		
		// PASO 2
		addStep("Ingresar a la Responsabilidad 'MXOXXO-GLXXXXX-Super Usuario'");
		paginaInicial.clickOnResponsabilidad("MXOXXO-GLXXXXX-Super Usuario");
		
		// PASO 3
		addStep("Ingresar al apartado de CRs -Alta de centros de Responsabilidad");
		paginaInicial.carpetaCRsElement.click();
		paginaInicial.clickAltaCentrosResponsabilidad();
		
		// PASO 4 Y 5
		addStep("Llenar los siguientes campos" +
				"Compa�ia: 099" +
				"Edo. Fin. : 01MTY" +
				"Cr. Superior: 10MON" +
				"Fecha Vigencia BI: a elegir (generalmente es la actual)" +
				"Ademas se debe captura el prefijo y una descripcion para el CR" +
				"NOTA: para hacer la busqueda presionar Cntrl + F11");
		addStep("\".-Oprimir el boton \"\"Obtener CR Valido\"\" donde el sufijo se llenar� de manera automatica.\n" +
				"Seguido del boton \"\"Genera CRs\"\" \"\n");
		ebsMex.ejecutararchivofrmservlet(Folder, "00099", "01MTY",crSuperior, "01-sep-2024", "36", "pruebas auto qa 3");


		// PASO 6
		addStep("Ingresar a Control -M con usuario y contraseña valido http://10.184.56.194:18080/ControlM/");
		// PASO 7
		addStep("\"Pasar informacion a NOMCOM\n" +
				"Grupo: TQA_BAJO_DEMANDA\n" +
				"Job: CntaSAP.sh\"\n");
		updNom.logInControlMyEjecutarJob(controlMInicio, controlMJob);

		// PASO 8
		addStep("\"ingresar a la BD EBS \n" +
				"usuario:APPSVIEW\n" +
				"host: oxfdbfinq00.femcom.net\n" +
				"puerto: 1535\n" +
				"servicio: AVEBQA\"\n");
		SQLUtil dbAvebqa = new SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
				GlobalVariables.DB_PASSWORD_AVEBQA);
		System.out.println("Conectado a BD AVEBQA para EBS Mexico");

		// PASO 9
		addStep("Validar que se haya insertado el CR con estatus activo: SELECT * FROM apps.wm_centro_responsabilidad_ro25 where estado ='A';");

		String queryCrActivo = "SELECT * FROM " +
				"(SELECT ORACLE_CR,ORACLE_CR_SUPERIOR,DESCRIPTION, ESTADO, ORACLE_EF, ORACLE_EF_DESC,ORACLE_CIA,ORACLE_CIA_DESC,CREATION_DATE,WM_STATUS" +
				"    FROM apps.wm_centro_responsabilidad_ro25" +
				"    ORDER BY creation_date DESC)" +
				"WHERE ROWNUM = 1";
		SQLResult resultCrActivo = executeQuery(dbAvebqa, queryCrActivo);
		testCase.addQueryEvidenceCurrentStep(resultCrActivo);

		// PASO 10
		addStep("\"validar en la tabla  apps.XXFC_MCR_TO_SAP que exista el CR creado y que tenga estatus E y que la sociedad asociada sea la F099:\n" +
				"Select  * from apps.XXFC_MCR_TO_SAP where ceco = '' and cr_type ='' and division_financiera='F099';\"\n");

		String Tienda = resultCrActivo.getData(0,"ORACLE_CR");
		String queryMcrSap = "SELECT ID_MCR_TO_SAP, SOCIEDAD, ESTADO_FINANCIERO, CR_SUPERIOR, CECO, NOMBRE, DESCRIPCION, FECHA_CARGA_STG, ESTATUS " +
				"FROM apps.XXFC_MCR_TO_SAP WHERE CECO='"+Tienda+"'";
		System.out.println(queryMcrSap);
		SQLResult resultMcrSap = executeQuery(dbAvebqa, queryMcrSap);
		System.out.println(resultMcrSap.getData(0,"ESTATUS"));
		testCase.addQueryEvidenceCurrentStep(resultMcrSap);
		

		// PASO 11
		addStep("\"Ingresar a Control-M con usuario y contrase�a valido:\n" +
				" http://10.184.56.194:18080/ControlM/\"\n");
		// PASO 12
		addStep("ejecutar interfaz Job: runCS01 Carpeta: TQA_TEST_WM99_INT");
		
		//updNom.logInControlMyEjecutarJob(controlMInicio, controlMJob2);
		updNom.ejecutarJob(controlMJob2);
		
		// PASO 14
		addStep("Validar en la tabla apps.XXFC_MCR_TO_SAP que haya caido la informacion con estatus enviado 'E' \n");
//		String queryMcrSapStatusE = "SELECT * FROM apps.XXFC_MCR_TO_SAP WHERE ceco = '" + Tienda + "' and division_financiera='F099'"; //TODO: A�ADIR EL VALOR CR_TYPE y EL VALOR STATUS E, revisar evidencias HANA MCR o OS10
//		System.out.println(queryMcrSapStatusE);
		SQLResult resultMcrSapStatusE = executeQuery(dbAvebqa, queryMcrSap);
		System.out.println(resultMcrSapStatusE.getData(0,"ESTATUS"));
		testCase.addQueryEvidenceCurrentStep(resultMcrSapStatusE);
		
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_Ejecutar_la_interfaz_OS10_MX_Sociedad_F099";
	}

	@Override
	public String setTestDescription() {
		return "Ejecucion de la interfaz OS10 MX  envie la informacion a SAP del CECO y Division financiera de las tiendas creadas en CNT con estatus activo";
	}

	@Override
	public String setTestDesigner() {
		return "Mario Alberto De Leon Rodriguez";
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
