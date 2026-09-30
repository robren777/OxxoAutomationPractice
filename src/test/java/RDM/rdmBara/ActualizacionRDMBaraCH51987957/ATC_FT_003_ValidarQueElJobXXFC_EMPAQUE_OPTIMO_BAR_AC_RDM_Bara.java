package RDM.rdmBara.ActualizacionRDMBaraCH51987957;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import org.sikuli.script.Pattern;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import RDM.util.metodosAdicionales.MetodosSikulli;
import RDM.util.metodosExtra.LoginRDM;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.SikuliUtil;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_003_ValidarQueElJobXXFC_EMPAQUE_OPTIMO_BAR_AC_RDM_Bara extends BaseExecution {
	SeleniumUtil u = new SeleniumUtil(new IETest(),true);
	/**
	 * REREQUISITOS: 
	 *		1.- No exista datos en la Vista "XXFC_EO_ITEMS_ V"
	 * 
	 * DESCRIPCION: Validar la correcta funcionalidad del boton delete dentro de la pantalla de seguimiento de auditorias de almacenaje
	 * 
	 * @author franciscot2@hexaware.com
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_ValidarQueElJobXXFC_EMPAQUE_OPTIMO_BAR_AC_RDM_Bara_test (HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 * 
		 * Validar que al ejecutarse el JOB "XXFC_EMPAQUE_OPTIMO_BAR" sin información en la vista 
		 * "XXFC_EO_:ITEMS_V", no debe de marcar error o guardar algún "log" en la "ERROR_LOG"
	    */
		
		String url = data.get("url");
		String userName = data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String user = data.get("userCM");
		String pass = PasswordUtil.decryptPassword(data.get("passwordCM"));
		
		SikuliUtil sk = new SikuliUtil();
		
		
		String pathImg = sk.PatternPath("RDM\\"+"CH51987957");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikulli p = new MetodosSikulli(sk, pathimg, ListImage, ImgPatter, patterns, Fol);
		LoginRDM login = new LoginRDM(u, testCase, url);
		
		/*
		 * PASO 1: INGRESAR A RDM  ////// EL PASO SE COMENTA YA QUE EXISTE EN LA MATRIZ DE CASOS DE PRUEBA PERO NO ES RELEVANTE PARA EL TC AU
		*/
		
//		addStep("Ingresar a RDM");
//		u.get(login.getUrl());	
//		u.hardWait(15);
//		addStep("Ingresar a usuario y password");
//	    p.enviarTexto(userName);
//	    p.nTAB(1);
//	    u.hardWait(10);
//	    p.enviarTexto(password);
//	    p.nTAB(1);
//	    u.hardWait(10);
//	    testCase.addScreenShotCurrentStep("PrimerCaptura");
//	    p.clickImagen("Login");
//	    u.hardWait(15);
	    
	    /*
		 * PASO 2: Consultar la vista "XXFC_EO_ITEMS_V" con los siguientes criterios:
		*/
	    boolean flagP1=false;
	    addStep("Consultar la vista \"XXFC_EO_ITEMS_V\" con los siguientes criterios:");	
	    String querty1 = "Select * from XXFC_EO_ITEMS_V where FACILITY_ID = 'PR' and INVENTORY_QTY = 0 and STD_CASEPACK <> EO_STD_CASEPACK";
	    utils.sql.SQLUtil dbBARAqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RDM_BARA,
				GlobalVariables.DB_USER_RDM_BARA, GlobalVariables.DB_PASSWORD_RDM_BARA);
	    utils.sql.SQLResult resultadoContenedor1= dbBARAqa.executeQuery(querty1);
	    testCase.addQueryEvidenceCurrentStep(resultadoContenedor1);
		if(!resultadoContenedor1.isEmpty())
		flagP1=true;
		assertTrue(flagP1, "El registro existe en la BD");
		u.hardWait(5);
	    /*
		 * PASO 3: Ejecutar el JOB "XXFC_EMPAQUE_OPTIMO_BAR":
		*/
	    
	    addStep("Ejecutar el JOB \"XXFC_EMPAQUE_OPTIMO_BAR");	
	    u.getDriver().manage().deleteAllCookies();
		u.get(GlobalVariables.CtrlM_URL);
		u.getDriver().manage().window().maximize();
		u.getDriver().manage().window().fullscreen();
		//u.hardWait(250);
		
		String job1=data.get("job1");
		u.hardWait(10);
		Control_mInicio cm = new Control_mInicio(u, "", "");
		u.hardWait(15);
		p.doubleClickAndCopyTextBoxRight("loginCM", 10);
		u.hardWait(15);
		p.enviarTexto(user);
		u.hardWait(15);
		p.doubleClickAndCopyTextBoxRight("loginCMC", 10);
		u.hardWait(15);
		p.enviarTexto(pass);
		u.hardWait(15);
		JSONObject jobFEM1 = new JSONObject(job1);
		u.hardWait(15);
		u.waitForLoadPage();
		cm.logOn();
		u.hardWait(15);
		ControlM jobCM = new ControlM(u, testCase, jobFEM1);
		u.hardWait(4);
		boolean bandera;
		bandera = jobCM.searchJob();
		assertTrue(bandera, "No fue posible encontrar el JOB");
		u.hardWait(3);
		
		String resultado = jobCM.executeJob();
		u.hardWait(120);
		
		addStep("Resultado de la ejecucion: " + resultado);
		System.out.println("Resultado de la ejecucion -> " + resultado);
		testCase.addBoldTextEvidenceCurrentStep("Output: ");
		System.out.println("Valor de output :" + jobCM.getOutput());
		testCase.addTextEvidenceCurrentStep(jobCM.getOutput());
		if (jobCM.getOutput().toLowerCase().contains("error")) {
			throw new AssertionError("Existe un error en el Output");
		}
		assertEquals(resultado, "Wait Condition");

		u.hardWait(3);
		
		jobCM.closeViewpoint();
		
	    
	    /*
		 * PASO 4: Consultar la tabla "ERROR_LOG" para validar que no exista algún registro correspondiente al Proceso "XXFC_EMPAQUE_OPTIMO_PKG"
		*/
	    
	    addStep("Consultar la tabla ERROR_LOG para validar que no exista algún registro correspondiente al Proceso XXFC_EMPAQUE_OPTIMO_PKG");
	    String querty2 = "Select * from XXFC_EO_ITEMS_V where FACILITY_ID = 'PR' and INVENTORY_QTY = 0 and STD_CASEPACK <> EO_STD_CASEPACK";
	    utils.sql.SQLResult resultadoContenedor2= dbBARAqa.executeQuery(querty2);
	    testCase.addQueryEvidenceCurrentStep(resultadoContenedor2);
	    boolean flag3 = false; 
	    if(!resultadoContenedor2.isEmpty())
	    	flag3=true;
	    assertTrue(flag3, "El registro existe en la BD");
		u.hardWait(5);
		
		
		u.close();
	}
	    
	    
	  //testCase.addQueryEvidenceCurrentStep(resultadoContenedor3);

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@AfterMethod
	public void afterTest() {
		if(u!=null)
			u.close();
	}

	@Override
	public String setPrerequisites() {
		return "No exista datos en la Vista XXFC_EO_ITEMS_ V";
	}

	@Override
	public String setTestDescription() {
		return "Validar la correcta funcionalidad del boton delete dentro de la pantalla de seguimiento de auditorias de almacenaje";
	}

	@Override
	public String setTestDesigner() {
		return "FranciscoT2@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_ValidarQueElJobXXFC_EMPAQUE_OPTIMO_BAR_AC_RDM_Bara_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
