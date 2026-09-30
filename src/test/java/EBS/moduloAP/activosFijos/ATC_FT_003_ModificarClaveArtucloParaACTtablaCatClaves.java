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

public class ATC_FT_003_ModificarClaveArtucloParaACTtablaCatClaves extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_003_ModificarClaveArtucloParaACTtablaCatClaves_test(HashMap<String, String> data) throws Exception {
        
    	
    	testCase.setProject_Name("TI01 Homologación CxP -MVP3 V1 Activos fijos");  
    	/*
         * VARIABLES
         */
        String ebsUrl = GlobalVariables.getAvanteServer;
        String userName = GlobalVariables.getRpalaciosNeoEbsUsr;
        String password = GlobalVariables.getRpalaciosNeoEbsPwd;

        String ebsPattern = data.get("ebsPattern");
        String responsabilidad = data.get("responsabilidad");
        String responsabilidad2 = data.get("responsabilidad2");
        String concurrente = data.get("concurrente");
        String companias = data.get("companias");
        String plaza = data.get("plaza");
        String catalogo = data.get("catalogo");
        String txtout = data.get("txtout");
        String articuloID = data.get("articuloID");
        String cuenta = data.get("cuenta");
        
        HashMap<String, Pattern> ImgPatter = new HashMap<>();
        ArrayList<Pattern> patterns = new ArrayList<>();
        ArrayList<String> ListImage = new ArrayList<>();
        Pattern pathimg = new Pattern();

        utils.sql.SQLUtil dbAvebqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
        String selectStatus = "SELECT ID_ARTICULO,DESCRIPCION,WM_STATUS FROM XXFC.XXFC_XP_CAT_CLAVES_ARTICULO WHERE WM_STATUS = 'L'";
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
		 * PASOS DEL CASO DE PRUEBA
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
		addStep("Selecciona la Responsabilidad de MXOXXO-CC17FAA-Usuario-ACT_FIJO_OS");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		testCase.addScreenShotCurrentStep("NewRequest Appelet");
		
		//PASO 4
		addStep("Selecciona la carpeta de Cargos Centrales");
		paginaInicial.EjecutarCargosCentrales();
		
		//PASO 5
		addStep("Selecciona la opción de Captura de claves de articulo");
		paginaInicial.clickRunCapturaClaveArticulo();
		testCase.addScreenShotCurrentStep("NewRequest Appelet");
		u.hardWait(10);
		
		//Paso 6 y 7
		addStep("Abrir el archivo descargado de JAVA"
				+"Da clic en el botón RUN de archivo de JAVA");
		patternUtil.runApplet();
		testCase.addScreenShotCurrentStep("NewRequest Appelet");
		u.hardWait(70);
		
		//PASO 8
		addStep("Modificar uno o mas de los siguientes campos :\r\n"
				+ "Articulo ID\r\n"
				+ "Cuenta\r\n"
				+ "y clic en guardar");
		patternUtil.enterText(articuloID);
		patternUtil.nTAB(1);
		patternUtil.enterText(cuenta);
		patternUtil.cerrarCapturaClaves();
		testCase.addScreenShotCurrentStep("NewRequest Appelet");
		patternUtil.nEnter(2);
		
		
		//PASO 9 
		addStep("Selecciona la Responsabilidad de MXOXXO-APXXXXX-Super Usuario > Other > Concurrent");
		addStep("Ingresar EBS Mexico");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		inicio.login();
		paginaInicial.clickOnResponsabilidad(responsabilidad2);
		paginaInicial.EjecutarOtros();
		paginaInicial.clickRunTransaccionesMxSuperUsuario();
		testCase.addScreenShotCurrentStep("Responsabilidad");
		u.hardWait(10);
		
		patternUtil.runApplet();
		u.hardWait(70);
		
		patternUtil.nTAB(4);
		patternUtil.nEnter(1);
		testCase.addScreenShotCurrentStep("NewRequest Appelet dos");
		u.hardWait(3);
		
		//PASO 10 Y 11
		addStep("Da clic en View > Request"
				+ "Ingresa el concurrente \r\n"
				+ "XXFC - Extracción de catálogos para portal de proveedores HOM ");
		patternUtil.enterText(concurrente);
		u.hardWait(3);
		patternUtil.nTAB(1);
		patternUtil.nDOWN(2);
		testCase.addScreenShotCurrentStep("Request");
		patternUtil.nEnter(1);
		u.hardWait(3);
		
		//PASO 12
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
		u.hardWait(10);
		
		//PASO 13
		addStep("Da clic en el botón submit");
		patternUtil.nTAB(6);	
		testCase.addScreenShotCurrentStep("Submit1");
		patternUtil.nEnter(1);
		u.hardWait(10);
		
		//PASO 14
		addStep("Da clic en el botón submit");
		patternUtil.nTAB(1);
		testCase.addScreenShotCurrentStep("Submit");
		patternUtil.nEnter(2);
		u.hardWait(10);
		
		//PASO 15
		addStep("Da clic en el botón de LOG");
		patternUtil.ViewLogKey();
		testCase.addScreenShotCurrentStep("VieWlOG");
		
		// PASO 16 *********************************************************************
		
		addStep("Validar la tabla XXFC_XP_CAT_CLAVES_ARTICULO ");		
		System.out.println(GlobalVariables.DB_HOST_AVEBQA);
		SQLResult paso12 = executeQuery(dbAvebqa, selectStatus);		
		boolean av1 = paso12.isEmpty();
			if (!av1) {
				testCase.addQueryEvidenceCurrentStep(paso12);
				} 
		System.out.println(av1);		
		assertFalse(av1, "No se obtiene informacion de la consulta en la tabla " + data.get("tabla"));
		
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_003_ModificarClaveArtucloParaACTtablaCatClaves_test";
    }

    @Override
    public String setTestDescription() {
        return "Modificar mediante EBS una clave de articulo para que su actualización se vea reflejada en la tabla XXFC_XP_CAT_CLAVES_ARTICULO y este sea enviado con la modificación al portal ";
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
        		+ "* Tener acceso a las BD de AVANTE";
    }

    @Override
    public void beforeTest() {

    }
    
}