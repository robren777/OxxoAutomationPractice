package WorkFlows.SipreBara.ActualizacionBPM;

import static org.testng.Assert.assertEquals;
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

public class MTC_FT_016_ValidarReporteListadoCategorias_ActualizacionBPM  extends BaseExecution{
	static int rowCount;
	
	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_016_ValidarReporteListadoCategorias_ActualizacionBPM_Test(HashMap<String, String> data) throws Exception {
		
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
		
		pom_userAdmin.generaReporte("listadoCategoria");
	    
		/*-----------------------------------------------------------------------------------------*/
		addStep("Validar que reporte cuente con formato esperado");
	
		try {
		
		u.hardWait(10);
		
		
		String path = BASE_PATH+"\\Downloads\\ReporteListadoCategorias.xlsx";
		String nombreHoja = "ReporteListadoCategorias";

		
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
	  		  	
	    String queryReporte = "SELECT DIVISION AS SuperGrupo_Id,DIVISION_NAME AS SuperGrupo,\r\n"
	    		+ "GROUP_NO AS Categoria_Id,GROUP_NAME AS Categoria,PAQDEREGLAS AS PAQDEREGLAS\r\n"
	    		+ " FROM XXWF_SIPRE_REGLA_EXCLUIR ORDER BY DIVISION\r\n";
	    
	    
		SQLResult queryResultTabla = executeQuery(dbSipreBara, queryReporte);
		int rowsConsulta = queryResultTabla.getRowCount();

		testCase.addTextEvidenceCurrentStep("Tabla de la consulta");

		
		testCase.addQueryEvidenceCurrentStep(queryResultTabla);
		
		testCase.addTextEvidenceCurrentStep("Total registros de la DB: "+rowsConsulta);
		testCase.addTextEvidenceCurrentStep("Total registros del archivo: "+rowCount);
	assertEquals(rowCount,rowsConsulta, "La tabla del archivo y la tabla de la db No tiene los mismos elementos");
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
		return "Validar la Creacion, Contenido y Estructura del Reporte Listado de Categorias";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_016_ValidarReporteListadoCategorias_ActualizacionBPM_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
