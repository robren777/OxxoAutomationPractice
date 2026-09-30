package WorkFlows.SipreBara.ActualizacionBPM;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.SipreBara.pom_metodosSipreBara_userAdmin;
import WorkFlows.util.ultimus.pomUltimus;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class MTC_FT_013_ReporteEXCEPCIONesCONFiltros_ActualizacionBPM extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_013_ReporteEXCEPCIONesCONFiltros_ActualizacionBPM_Test(HashMap<String, String> data) throws Exception {
		
	testCase.setProject_Name("Framework WorkFlows --> SIPRE BARA");

	/**
	 * Variables
	 * 
	 */
	String userUltimus = data.get("userUltimus");
	String userSipre = GlobalVariables.SIPRE_BARA_USER;
	String psSipre = GlobalVariables.SIPRE_BARA_PS;
	String BASE_PATH = System.getProperty("user.home");
	int rowCount;

	
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
		
		pom_userAdmin.generaReporte("Excepciones");
	    
		/*-----------------------------------------------------------------------------------------*/
		/*	addStep("Validar lo mostrado en el Reporte mediante la siguiente Consulta:");
		  	
	    String queryReporte = "SELECT b.cr_plaza CR_PLAZA, to_char(a.item) ITEM, c.item_desc DESCRIPCION, c.division_name SUPERGRUPO,\r\n"
	    		+ "c.group_name CATEGORIA, c.dept_name SUB_CATEGORIA, c.class_name SEGMENTO, c.subclass_NAME SUB_SEGMENTO, \r\n"
	    		+ "(SELECT PRECIOGRUPO FROM xxva_item_lorp D WHERE D.CLAVE_PRECIOGRUPO = A.CLAVE_PRECIOGRUPO AND D.ITEM = A.ITEM \r\n"
	    		+ "GROUP BY PRECIOGRUPO) PRECIOGRUPO,\r\n"
	    		+ "             b.description ZONA_DE_PRECIO, \r\n"
	    		+ "             a.tiporegl TIPO_DE_REGLA, \r\n"
	    		+ "             a.pto_minimo REGLA_MINIMA, \r\n"
	    		+ "             a.pto_maximo REGLA_MAXIMA, \r\n"
	    		+ "             a.redondeo ESCENARIO_DE_REDONDEO, \r\n"
	    		+ "             a.costo COSTO_NETO_CON_IEPS, \r\n"
	    		+ "             a.vat_code VAT_CODE, \r\n"
	    		+ "             a.precio_competidor PRECIO_COMPETENCIA, \r\n"
	    		+ "             a.precio_minimo PRECIO_MINIMO_EN_REGLA, \r\n"
	    		+ "             a.precio_maximo PRECIO_MAXIMO_EN_REGLA, \r\n"
	    		+ "             a.selling_unit_retail PRECIO_DE_VENTA, \r\n"
	    		+ "             a.precio_referencia PRECIO_ZONA_DE_REFERENCIA,\r\n"
	    		+ "             a.cumplimiento INDICADOR_DE_CUMPLIMIENTO, \r\n"
	    		+ "             a.descartado_rac RACIONAL_DE_DESCARTADO,\r\n"
	    		+ "             (SELECT PUNTO_EJEC_NUEVO FROM XXWF_SIPRE_PUNTO_EJECUCION  pto \r\n"
	    		+ "                 WHERE pto.ITEM = a.item \r\n"
	    		+ "                 and pto.ZONE_GROUP_ID = a.zona_group_id\r\n"
	    		+ "                 and pto.ZONA_ID = a.zona_id) AS PTO_EJEC,\r\n"
	    		+ "             ( SELECT PUNTO_EJEC_NUEVO \r\n"
	    		+ "                 FROM XXWF_SIPRE_PUNTO_EJECUCION  pto1,\r\n"
	    		+ "                          XXWF_SIPRE_ZONAPAQUETES zp\r\n"
	    		+ "                 WHERE pto1.ITEM = a.item \r\n"
	    		+ "                 and pto1.ZONE_GROUP_ID = zp.ZONE_GROUP_REFERENCIA\r\n"
	    		+ "                 and pto1.ZONA_ID = zp.ZONE_REFERENCIA\r\n"
	    		+ "                 and zp.ZONE_GROUP_ID = a.zona_group_id\r\n"
	    		+ "                 and zp.ZONE_ID  = a.zona_id) AS PTO_EJEC_REFERENCIA,\r\n"
	    		+ "                 DECODE(a.selling_unit_retail, 0, a.selling_unit_retail, \r\n"
	    		+ "                     ((a.selling_unit_retail/(1+a.IVA/100)/(1+a.IEPS/100)) - a.costo)/(a.selling_unit_retail/(1+a.IVA/100)/(1+a.IEPS/100))) AS MARGEN_EJ\r\n"
	    		+ "       FROM xxwf_sipre_item_zona_cost_inv a,\r\n"
	    		+ "            xxwf_sipre_zona_precio_mv b,\r\n"
	    		+ "            xxwf_sipre_item_estruct_cat_mv c\r\n"
	    		+ "       \r\n"
	    		+ "      WHERE     a.zona_group_id = b.zone_group_id\r\n"
	    		+ "            AND a.zona_id = b.zone_id\r\n"
	    		+ "            AND a.item = c.item\r\n"
	    		+ "                 AND A.DIVISION_RTK = C.DIVISION\r\n"
	    		+ "            AND A.GROUP_RTK = C.GROUP_no\r\n"
	    		+ "           AND b.cr_plaza = 'Mexico Bara'\r\n"
	    		+ "           and c.DIVISION_NAME= 'Agua Purificada'\r\n"
	    		+ "           and a.zona_id = null\r\n";
	    
	    
		SQLResult queryReporte1 = executeQuery(dbSipreBara, queryReporte);
		int rowsConsulta = queryReporte1.getRowCount();

		testCase.addTextEvidenceCurrentStep("Tabla de la consulta");
		testCase.addQueryEvidenceCurrentStep(queryReporte1);
		
		//Valida la cantidad de registros en el archivo descargado
		String path = BASE_PATH+"\\Downloads\\ReporteCumplimiento.xlsx";
		String nombreHoja = "ReporteCumplimiento";

		ExcelUtil excelUtil = new ExcelUtil(path);
		HashMap<String, ArrayList<String>> hashtable  = excelUtil.getRows(nombreHoja);
		XSSFWorkbook workBook = new XSSFWorkbook(path);
		XSSFSheet sheet = workBook.getSheet(nombreHoja);
		 rowCount = sheet.getPhysicalNumberOfRows()-1;
		 
		testCase.addTextEvidenceCurrentStep("Total registros de la DB: "+rowsConsulta);
		testCase.addTextEvidenceCurrentStep("Total registros del archivo: "+rowCount);
		
		assertEquals(rowsConsulta,rowCount, "La tabla del archivo y la tabla de la db No tiene los mismos elementos");*/
		//u.getDriver().quit();

	
	
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
		return "Validar la Creacion, Contenido y Estructura del Reporte Excepciones CON Filtro";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_013_ReporteEXCEPCIONesCONFiltros_ActualizacionBPM_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
