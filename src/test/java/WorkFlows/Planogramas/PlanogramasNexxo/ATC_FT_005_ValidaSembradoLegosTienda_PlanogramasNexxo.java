package WorkFlows.Planogramas.PlanogramasNexxo;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Jobs.JobUtils;
import WorkFlows.util.PlanogramasNexxo.sembradoUtils;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_005_ValidaSembradoLegosTienda_PlanogramasNexxo extends BaseExecution {
	

	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_005_ValidaSembradoLegosTienda_PlanogramasNexxo_test(HashMap<String, String> data) throws Exception {
		
	testCase.setProject_Name("Framework WorkFlows --> PLANOGRAMAS NEXXO");
	//SeleniumUtil u = new SeleniumUtil(new MEdgeTest());


	utils.sql.SQLUtil dbXXVA_SIV3 = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_XXVA_SIV3,GlobalVariables.DB_USER_XXVA_SIV3, 
			GlobalVariables.DB_PASSWORD_XXVA_SIV3);
	

	utils.sql.SQLUtil dbXXVA_SIV3_Admin = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_XXVA_SIV3,"XXVA_SIV3", 
			"xxvasiv3#QA");
	
	String QUERY_SIV3_XXFC_ITEM_SUP_PLAZA_TIENDA = "SELECT  ITEM_RTK,LOC_TYPE, PLAZA_ID, TIENDA_ID, STATUS, LAST_UPDATE_DATE,LAST_UPDATED_BY, SISTEMA \r\n"
			+ "FROM  XXVA_SIV3.xxva_item_sup_plaza_tienda where TRUNC(CREATE_DATE)= TRUNC(SYSDATE)and SISTEMA LIKE '%SEM%'\r\n"
			+ "and ROWNUM <=5";

	String QUERY_SIV3_xxva_eng_logs_engcreateitem  = "SELECT SEQ, MESSAGE, TYPE,SID, TRUNC(TIMESTAMP) FROM XXVA_SIV3.xxva_eng_logs_engcreateitem  WHERE ROWNUM <=5\r\n"
			+ "ORDER BY TIMESTAMP DESC";
	
	String selectItem = "SELECT ITEM_RTK FROM  XXVA_SIV3.xxva_item_sup_plaza_tienda where TRUNC(CREATE_DATE)= TRUNC(SYSDATE)and SISTEMA LIKE '%SEM%'\r\n"
			+ "and STATUS = 'PENDIENTE_ACTIVAR' and ROWNUM <=1";
	
	SeleniumUtil u = new SeleniumUtil(new IETest());

	String url = data.get("url");
	SQLResult queryResult;
	String resultadoEjecucion;
	boolean ejecucionCorrectaJob;
	u.get(url);
	
   sembradoUtils sembrado = new sembradoUtils(u,testCase);
	/**
	 * Variables
	 * 
	 */

	addStep("Inicio Sesión");
	sembrado.goOn();
	

	System.out.print(data.get("ps")); 
	sembrado.login(data.get("user"), data.get("ps"));
	
	addStep("Ingresa a sembrado de tienda");
	sembrado.ingresaSembradoTienda();
	
	addStep("Seleccionar Plaza, Tienda, Sección y Fase de sembrado");
	sembrado.sembrarTienda(data.get("plaza"),data.get("tienda"), data.get("seccion"), data.get("fase"));
	
	addStep("Inventario de movimientos generados por el sembrado ");
	sembrado.inventarioArticulos();
	
	addStep("Revisar la tabla XXFC_ITEM_SUP_PLAZA_TIENDA en PIEVE:");
	
	
	queryResult= executeQuery(dbXXVA_SIV3, QUERY_SIV3_XXFC_ITEM_SUP_PLAZA_TIENDA);
	testCase.addQueryEvidenceCurrentStep(queryResult);
	
	
	assertFalse(queryResult.isEmpty(), "No se encontraron registros");
	
	SeleniumUtil k = new SeleniumUtil(new FirefoxTest(), true);
    JobUtils jobUtil = new JobUtils(k, testCase, data, "jobs.xlsx");

	addStep("Ejecutar Job xxvaengcreateitem");
	
	resultadoEjecucion = jobUtil.ejecutar("xxvaengcreateitem");
	ejecucionCorrectaJob = jobUtil.fueEjecutadoCorrectamente(resultadoEjecucion);
	System.out.println("Resultado -------> "+resultadoEjecucion);
	System.out.println(ejecucionCorrectaJob);

	assertTrue(ejecucionCorrectaJob, "El job xxvaengcreateitem no se ejecuto correctamente");
	
addStep("Ejecutar Job xxvaengmonitoring");
	
	resultadoEjecucion = jobUtil.ejecutar("xxvaengmonitoring");
	ejecucionCorrectaJob = jobUtil.fueEjecutadoCorrectamente(resultadoEjecucion);
	System.out.println("Resultado -------> "+resultadoEjecucion);
	System.out.println(ejecucionCorrectaJob);

	assertTrue(ejecucionCorrectaJob, "El job xxvaengmonitoring no se ejecuto correctamente");
	
	addStep("Validar que la tabla xxva_eng_logs_engcreateitem see muestra la información correcta que el job proceso");
	queryResult= executeQuery(dbXXVA_SIV3, QUERY_SIV3_xxva_eng_logs_engcreateitem);
	testCase.addQueryEvidenceCurrentStep(queryResult);
	
	assertFalse(queryResult.isEmpty(), "No se encontraron registros");
	
	
	u.hardWait(10);
	addStep("Antes del envío del cambio de cobertura hacia RMS16 se identifica un artículo y se valida el estatus en la tabla ITEM_LOC");
	
	queryResult= executeQuery(dbXXVA_SIV3, selectItem);
	String item = queryResult.getFirstData();
	
	String selectLoc="SELECT LOC FROM  XXVA_SIV3.xxva_item_sup_plaza_tienda where ITEM_RTK = '"+item+"'  AND TRUNC(CREATE_DATE)= TRUNC(SYSDATE)and SISTEMA LIKE '%SEM%'\r\n"
			+ "and ROWNUM <=1";
	queryResult= executeQuery(dbXXVA_SIV3, selectLoc);
	String loc = queryResult.getFirstData();
	
	System.out.println("Item = "+item+" Loc = "+loc);
	
	testCase.addBoldTextEvidenceCurrentStep("ITEM --> "+item);
	
	testCase.addBoldTextEvidenceCurrentStep("ITEM --> "+loc);
	
	addStep("Revisión de artículos en RMS16");
	
	String QUERY_SIV3_RMS_ITEM_LOC ="select ITEM, LOC, LOCAL_ITEM_DESC, CREATE_DATETIME from  RMS.ITEM_LOC\r\n"
			+ "where ITEM = "+item+" and LOC = "+loc+" and rownum <=5";
	
	utils.sql.SQLUtil dbRMS16 = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS16,GlobalVariables.DB_USER_RMS16, 
			GlobalVariables.DB_PASSWORD_RMS16);
	
	queryResult= executeQuery(dbRMS16, QUERY_SIV3_RMS_ITEM_LOC);
	testCase.addQueryEvidenceCurrentStep(queryResult);
	u.getDriver().quit();
	assertFalse(queryResult.isEmpty(), "No se encontraron registros");
	
	
	
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Solicitar en hold el job StarMonitor y configurar el lego en el archivo csv";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Terminado. Se realiza el sembrado de legos a tiendas y que se realice correctamente la configuración de conjuntos.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_005_ValidaSembradoLegosTienda_PlanogramasNexxo_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
