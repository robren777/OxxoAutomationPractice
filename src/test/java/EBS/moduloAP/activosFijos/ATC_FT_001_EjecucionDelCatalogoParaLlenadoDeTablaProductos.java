package EBS.moduloAP.activosFijos;

import static org.testng.Assert.assertFalse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_001_EjecucionDelCatalogoParaLlenadoDeTablaProductos extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_001_EjecucionDelCatalogoParaLlenadoDeTablaProductos_test(HashMap<String, String> data) throws Exception {
    	
    	testCase.setProject_Name("TI01 Homologación CxP -MVP3 V1 Activos fijos");    
        /*
         * VARIABLES
         */
        String ebsUrl = GlobalVariables.getAvanteServer;
        String userName = GlobalVariables.getRpalaciosNeoEbsUsr;
        String password = GlobalVariables.getRpalaciosNeoEbsPwd;

        String ebsPattern = data.get("ebsPattern");
        String responsabilidad = data.get("responsabilidad");
        String concurrente = data.get("concurrente");
        String companias = data.get("companias");
        String plaza = data.get("plaza");
        String catalogo = data.get("catalogo");
        String txtout = data.get("txtout");
        
        HashMap<String, Pattern> ImgPatter = new HashMap<>();
        ArrayList<Pattern> patterns = new ArrayList<>();
        ArrayList<String> ListImage = new ArrayList<>();
        Pattern pathimg = new Pattern();

        utils.sql.SQLUtil dbAvebqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
        String selectStatusI = "SELECT ID_ARTICULO,DESCRIPCION,WM_STATUS FROM XXFC.XXFC_XP_CAT_CLAVES_ARTICULO WHERE WM_STATUS = 'L'";
        /*
         * UTILERIAS
         */
        SeleniumUtil u = new SeleniumUtil(new IETest(), true);
        SikuliUtil sk = new SikuliUtil();
        LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
        EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
        String pathImg = sk.PatternPath("EBS\\"+ebsPattern);
        File Folder = new File(pathImg);
        PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);

		/*
		 * PASOS DEL CASO DE PRUEBA,
		 */
		
		// PASO 1
		addStep("Ingresar EBS Mexico");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		//PASO 2
		inicio.login();
		//PASO 3
		addStep("Ingresar responsabilidad MXOXXO-MXRXXXXX-Super Usuario--> Otros --> Concurrent");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		paginaInicial.EjecutarOtros();
		paginaInicial.clickRunTransaccionesMxSuperUsuario();
		u.hardWait(10);
		
		//Paso 4 Y 5
		addStep("Abrir el archivo descargado de JAVA y Da clic en el botón RUN de archivo de JAVA");
		patternUtil.runApplet();
		u.hardWait(70);
		testCase.addScreenShotCurrentStep("RunApplet");
		
		//PASO 6
		addStep("Da clic en el botón de Submit a new Request");
		patternUtil.nTAB(4);
		patternUtil.nEnter(1);
		testCase.addScreenShotCurrentStep("NewRequest Appelet");
		u.hardWait(3);
		
		//PASO 7
		addStep("Ingresa el concurrente \r\n"
				+ "XXFC - Extracción de catálogos para portal de proveedores HOM");
		patternUtil.enterText(concurrente);
		u.hardWait(3);
		patternUtil.nTAB(1);
		patternUtil.nDOWN(2);
		patternUtil.nEnter(1);
		testCase.addScreenShotCurrentStep("NewRequest");
		u.hardWait(3);
		
		//PASO 8
		addStep("Ingresa los siguientes parametros:\r\n"
				+ "\r\n"
				+ "- Compañía\r\n"
				+ "- Plaza\r\n"
				+ "- Catalogo\r\n"
				+ "- TXT OUT\r\n"
				+ "- Fecha");
		patternUtil.enterText(companias);
		patternUtil.nTAB(1);
		patternUtil.enterText(plaza);
		patternUtil.nTAB(1);
		patternUtil.enterText(catalogo);
		patternUtil.nTAB(1);
		patternUtil.enterText(txtout);
		testCase.addScreenShotCurrentStep("Parametros");
		patternUtil.nEnter(1);
		
		//PASO 9
		addStep("Da clic en el botón submit");
		patternUtil.nTAB(6);	
		patternUtil.nEnter(1);
		testCase.addScreenShotCurrentStep("Submit");
		
		//PASO 10
		addStep("Da clic en el botón submit");
		patternUtil.nTAB(1);
		patternUtil.nEnter(2);
		testCase.addScreenShotCurrentStep("Submit 2");
		
		//PASO 10
		addStep("Da clic en el botón de LOG");
		patternUtil.ViewLogKey();
		testCase.addScreenShotCurrentStep("Log");
		
		// PASO 12 y 13 *********************************************************************
		
		addStep("Validar que la tabla XXFC_XP_CAT_CLAVES_ARTICULO wm_status='I'");		
		System.out.println(GlobalVariables.DB_HOST_AVEBQA);
		SQLResult paso12 = executeQuery(dbAvebqa, selectStatusI);		
		boolean av1 = paso12.isEmpty();
			if (!av1) {
				testCase.addQueryEvidenceCurrentStep(paso12);
				} 
		System.out.println(av1);		
		assertFalse(av1, "No se obtiene informacion de la consulta en la tabla " + data.get("tabla"));
		
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_001_EjecucionDelCatalogoParaLlenadoDeTablaProductos";
    }

    @Override
    public String setTestDescription() {
        return "Ejecutar mediante EBS el concurrente \r\n"
        		+ "XXFC - Extracción de catálogos para portal de proveedores HOM para el llenado de la tabla XXFC_XP_CAT_CLAVES_ARTICULO y este sea enviado al portal ";
    }

    @Override
    public String setTestDesigner() {
        return "Yair Lizarraga";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }

    @Override
    public String setPrerequisites() {
        return "* Tener acceso a EBS\r\n"
        		+ "* Tener acceso a las BD de AVBQA";
    }

    @Override
    public void beforeTest() {

    }
    
}