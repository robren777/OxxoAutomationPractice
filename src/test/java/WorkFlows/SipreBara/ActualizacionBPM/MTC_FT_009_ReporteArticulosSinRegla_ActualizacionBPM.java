package WorkFlows.SipreBara.ActualizacionBPM;

import static org.testng.Assert.assertEqualsNoOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.SipreBara.pom_metodosSipreBara_userAdmin;
import WorkFlows.util.ultimus.pomUltimus;
import modelo.BaseExecution;
import utils.ExcelUtil;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class MTC_FT_009_ReporteArticulosSinRegla_ActualizacionBPM extends BaseExecution{
	static int rowCount;
	
	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_009_ReporteArticulosSinRegla_ActualizacionBPM_Test(HashMap<String, String> data) throws Exception {
		
	testCase.setProject_Name("Framework WorkFlows --> SIPRE BARA");
	

	/**
	 * Variables
	 * 
	 */
	String userUltimus = data.get("userUltimus");
	String userSipre = GlobalVariables.SIPRE_BARA_USER;
	String psSipre = GlobalVariables.SIPRE_BARA_PS;
	String BASE_PATH = System.getProperty("user.home");

	
	/**
	 * 
	 * Utiler�as
	 */
	
	
	SeleniumUtil u = new SeleniumUtil(new FirefoxTest(), true);
	u.get(data.get("url"));
	pomUltimus pom = new pomUltimus(u);
	pom_metodosSipreBara_userAdmin pom_userAdmin = new pom_metodosSipreBara_userAdmin(u, testCase);
	
	utils.sql.SQLUtil dbSipreBara = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_SIPREBARA,
			GlobalVariables.DB_USER_SIPREBARA, GlobalVariables.DB_PASSWORD_SIPREBARA);

	
	/*-----------------------------------------------------------------------------------------*/
		addStep("Inicia Sesi�n en Ultimus");
		pom.loginUltimus(userUltimus, "");
		testCase.addScreenShotCurrentStep(u, "");
		
	/*-----------------------------------------------------------------------------------------*/
		addStep("Selecciona Iniciar");
	
		pom.seleccionarVista("INICIAR");
		testCase.addScreenShotCurrentStep(u, "");
	
	/*-----------------------------------------------------------------------------------------*/
		addStep("Inicia sesi�n en SIPRE_BARA");
	
		pom.openSipreBara(userSipre, psSipre);
		u.hardWait(4);
		testCase.addScreenShotCurrentStep(u, "");
		
	/*-----------------------------------------------------------------------------------------*/
		addStep("Generar Reporte");
		
		pom_userAdmin.generaReporte("ArtSinRegla");
	    
		/*-----------------------------------------------------------------------------------------*/
		addStep("Validar que reporte cuente con formato esperado");
	
		try {
		
		u.hardWait(10);
		
		
		String path = BASE_PATH+"\\Downloads\\ReporteArtSinRegla.xlsx";
		String nombreHoja = "ReporteArtSinRegla";

		
		ExcelUtil excelUtil = new ExcelUtil(path);
		HashMap<String, ArrayList<String>> hashtable  = excelUtil.getRows(nombreHoja);
		XSSFWorkbook workBook = new XSSFWorkbook(path);
		XSSFSheet sheet = workBook.getSheet(nombreHoja);
		 rowCount = sheet.getPhysicalNumberOfRows()-1;
		
		String formatoEsperado [] =  data.get("estructura").split(",");
		
		 testCase.addTextEvidenceCurrentStep("------------Formato Esperado---------");
		 System.out.println("------------Formato esperado---------");
		for(int i = 0; i<formatoEsperado.length; i++) {
				
			testCase.addTextEvidenceCurrentStep(formatoEsperado[i]);
		    System.out.println(formatoEsperado[i]);

		}

      
		testCase.addTextEvidenceCurrentStep("------------Formato Archivo---------");
        System.out.println("------------Formato Archivo---------");
        System.out.println("Registros archivo------>"+rowCount);
		for (Entry<String, ArrayList<String>> map : hashtable.entrySet() ) {
	    	
		       
		       // System.out.println(map.getValue());
		        
		        String NombreColumna =map.getKey().toString();
		        testCase.addTextEvidenceCurrentStep(NombreColumna);

		        System.out.println(NombreColumna);


		  
		    }
		 		workBook.close();
		assertEqualsNoOrder(formatoEsperado, hashtable.keySet().toArray(),"El formato del archivo "
				+ "no coincide con el esperado");
	
	}catch(Exception e) {
	
		System.out.println("Error --->"+e);

	}
	

		addStep("Validar lo mostrado en el Reporte mediante la siguiente Consulta:");
	  	
	  	testCase.addTextEvidenceCurrentStep("Tabla de la interfaz");
		testCase.addScreenShotCurrentStep(u, "");
	
	    
	    
	    String queryReporte = "SELECT T.ITEM,\r\n"
	    		+ " T.ITEM_DESC,\r\n"
	    		+ " T.DIVISION_NAME,\r\n"
	    		+ " T.GROUP_NAME,\r\n"
	    		+ " XXWF_SIPRE_ARTS_SIN_RP_PKG.GETDIASSINREGLA (T.ITEM) AS FECHA\r\n"
	    		+ " FROM (SELECT DISTINCT ITEM.ITEM,\r\n"
	    		+ " ITEM.ITEM_DESC,\r\n"
	    		+ " ITEM.DIVISION_NAME,\r\n"
	    		+ " ITEM.GROUP_NAME\r\n"
	    		+ " FROM XXWF_SIPRE_ITEM_ESTRUCT_CAT_MV ITEM,\r\n"
	    		+ " XXWF_SIPRE_ITEM_LORP_V ILORP,\r\n"
	    		+ " XXWF_SIPRE_ITEM_ZONA_COST_INV INV\r\n"
	    		+ " WHERE ITEM.ITEM = ILORP.ITEM\r\n"
	    		+ " AND INV.ITEM = ILORP.ITEM\r\n"
	    		+ " AND ITEM.ITEM = INV.ITEM\r\n"
	    		+ " AND ILORP.CLAVE_PRECIOGRUPO IS NULL\r\n"
	    		+ " AND ILORP.DIVISION_RTK IS NOT NULL\r\n"
	    		+ " AND ILORP.GROUP_RTK IS NOT NULL\r\n"
	    		+ " AND ILORP.PAQDEREGLAS = 'BARA'\r\n"
	    		+ " AND NOT EXISTS\r\n"
	    		+ " (SELECT NULL\r\n"
	    		+ " FROM XXWF_SIPRE_LOG_ARTS_SIN_RP ASR\r\n"
	    		+ " WHERE ASR.ESTATUS = 'NUEVO'\r\n"
	    		+ " AND ASR.REGLA = 'COMPETENCIA' AND ITEM.ITEM = ASR.ITEM\r\n"
	    		+ " AND ASR.PAQREGLA = ILORP.PAQDEREGLAS)\r\n"
	    		+ " UNION\r\n"
	    		+ " SELECT DISTINCT ITEM.ITEM,\r\n"
	    		+ " ITEM.ITEM_DESC,\r\n"
	    		+ " ITEM.DIVISION_NAME,\r\n"
	    		+ " ITEM.GROUP_NAME\r\n"
	    		+ " FROM XXWF_SIPRE_ITEM_ESTRUCT_CAT_MV ITEM,\r\n"
	    		+ " XXVA_ITEM_LORP ILORP,\r\n"
	    		+ " XXWF_SIPRE_ITEM_ZONA_COST_INV INV\r\n"
	    		+ " WHERE ITEM.ITEM = ILORP.ITEM\r\n"
	    		+ " AND ILORP.DIVISION_RTK IS NOT NULL\r\n"
	    		+ " AND ILORP.GROUP_RTK IS NOT NULL\r\n"
	    		+ " AND INV.ITEM = ILORP.ITEM\r\n"
	    		+ " AND ITEM.ITEM NOT IN (SELECT TARTS.ITEM FROM (\r\n"
	    		+ " SELECT COUNT(ASR.PAQREGLA) AS TOTAL, ASR.ITEM AS ITEM\r\n"
	    		+ " FROM XXWF_SIPRE_LOG_ARTS_SIN_RP ASR\r\n"
	    		+ " WHERE ASR.ESTATUS = 'NUEVO'\r\n"
	    		+ " AND PAQREGLA = 'BARA'\r\n"
	    		+ " AND ASR.REGLA = 'COMPETENCIA'\r\n"
	    		+ " HAVING COUNT (ASR.PAQREGLA) <= 4\r\n"
	    		+ " GROUP BY ASR.ITEM) TARTS\r\n"
	    		+ " WHERE TARTS.ITEM = ITEM.ITEM)\r\n"
	    		+ " HAVING COUNT (ITEM.ITEM) < ( select count(distinct paqdereglas) from xxva_lorp_siv)\r\n"
	    		+ " GROUP BY ITEM.ITEM,\r\n"
	    		+ " ITEM.ITEM_DESC,\r\n"
	    		+ " ITEM.DIVISION_NAME,\r\n"
	    		+ " ITEM.GROUP_NAME) T";
	    
	    
		SQLResult queryResultTabla = executeQuery(dbSipreBara, queryReporte);
	  	
		testCase.addTextEvidenceCurrentStep("Tabla de la consulta");
		testCase.addQueryEvidenceCurrentStep(queryResultTabla);
		
		int rowsConsulta = queryResultTabla.getRowCount();
		testCase.addTextEvidenceCurrentStep("Total registros de la DB: "+rowsConsulta);
		testCase.addTextEvidenceCurrentStep("Total registros del archivo: "+rowCount);
		//assertEquals(rowCount,rowsConsulta, "La tabla del archivo y la tabla de la db No tiene los mismos elementos");
		u.getDriver().quit();


	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "La carpeta de descargas debe ser por default";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar la Creacion, Contenido y Estructura del Reporte Articulo sin Regla";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_009_ReporteArticulosSinRegla_ActualizacionBPM_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
