//package WM.interfaces.ro3.NeteosCedisIntegracion;
//
//import static org.testng.Assert.assertEquals;
//import static org.testng.Assert.assertTrue;
//
//import java.util.HashMap;
//
//import org.json.JSONObject;
//import org.testng.annotations.Test;
//
//import modelo.BaseExecution; import WM.util.GlobalVariables;
//import util.GlobalVariables;
//import utils.controlm.ControlM;
//import utils.controlm.pageObject.Control_mInicio;
//import utils.selenium.MEdgeTest;
//import utils.selenium.SeleniumUtil;
//import utils.sql.SQLResult;
//
//
//public class EjecucionInterface extends BaseExecution {
//
//	@Test(dataProvider = "data-provider")
//	public void EjecucionInterface_test(HashMap<String, String> data) throws Exception {
//
//		/*
//		 * VARIABLES
//		 */
//
//		/*
//		 * VARIABLES
//		 */
//		
//		String runid = "";
//		String tabla1 = "SELECT RUN_ID,INTERFACE,START_DT,END_DT,STATUS FROM WMLOG.WM_LOG_RUN WHERE RUN_ID ="+"'"+runid+"'";
//		String tabla2 = "SELECT RUN_ID,DESCRIPTION,MESSAGE FROM WMLOG.WM_LOG_ERROR WHERE RUN_ID ="+"'"+runid+"'";
//		String tabla3 = "SELECT THREAD_ID,PARENT_ID,NAME,START_DT FROM WMLOG.WM_LOG_THREAD";
//
//		/*
//		 * UTILERIAS
//		 */
//		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);
//		utils.sql.SQLUtil 	WMQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_WMQA,
//				GlobalVariables.DB_USER_WMQA, GlobalVariables.DB_PASSWORD_WMQA);
//
//		/*
//		 * VARIABLES
//		 */
//		//--------------------------------Segunda validacion RMS-------------------------------------------
//		utils.sql.SQLUtil RMS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS_MEX,
//				GlobalVariables.DB_USER_RMS_MEX, GlobalVariables.DB_PASSWORD_RMS_MEX);
//		
//		String RTVORDER = data.get("rtvorder");
//		String tabla3RMS = "select RTV_ORDER_NO,WH,SUPPLIER_ID,REFERENCE_2,REFERENCE_5,REFERENCE_9 from  FEM_RTVHEAD_TEMP WHERE RTV_ORDER_NO ="+"'"+RTVORDER+"'";
//		
//	
//		//--------------------------------Tercera validacion EBS-------------------------------------------
//		
//		utils.sql.SQLUtil 	AVANTE = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
//				GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
//		
//		//------------------------------AQUI OBTENEMOS EL REFERENCE 5 DE LA SEGUNDA VALIDACION  DE RMS----
//		SQLResult obtengoReference = RMS.executeQuery(tabla3RMS);
//		String REFERENCE = obtengoReference.getData(0, "REFERENCE_2");
//		
//		String tabla4EBS = "select INVOICE_ID,INVOICE_NUM,ATTRIBUTE2,REFERENCE_2 from XXFC.XXFC_XP_INVOICES_INTERFACE WHERE INVOICE_ID ="+"'"+REFERENCE+"'";
//		String tabla5EBS = "select INVOICE_ID,INVOICE_LINE_ID,LINE_NUMBER,AMOUNT,DESCRIPTION from XXFC.XXFC_XP_INVOICE_LINES_INTER WHERE  INVOICE_ID ="+"'"+REFERENCE+"'";
//		
//		//------------------------------VALIDACION FINAL RMS----
//		
//
//		String tabla6RMS = "select RTV_ORDER_NO REFERENCE_2,REFERENCE_5,REFERENCE_9  from FEM_RTVHEAD_TEMP  WHERE RTV_ORDER_NO ="+"'"+RTVORDER+"'";
//		
//		/*
//		 * PASOS DEL CASO DE PRUEBA
//		 */
//		// PASO 2
//		addStep("Validar que el insumo identificado en los prerrequisitos se haya modificado las columna REFERENCE_2 con el valor -10, el cual se tomara es valor para consultarlo en EBS.");
//		System.out.println(tabla3RMS);
//     	SQLResult v3 = RMS.executeQuery(tabla3RMS);
//        testCase.addQueryEvidenceCurrentStep(v3);
//        
//		// PASO 3
//		addStep("Validar en EBS en la tabla XXFC_XP_INVOICES_INTERFACE con el filtro de invoice_num enviado el valor de la REFERENCE_5 obtener el invoice_id de la consulta obtenida.");
//		 System.out.println(tabla4EBS);
//	      	SQLResult v4 = AVANTE.executeQuery(tabla4EBS);
//	        testCase.addQueryEvidenceCurrentStep(v4);
//	        
//		// PASO 4
//		addStep("Validar en EBS en la tabla XXFC_XP_INVOICE_LINES_INTER con el filtro de invoice_id");
//		
//		 System.out.println(tabla5EBS);
//	      	SQLResult v5 = AVANTE.executeQuery(tabla5EBS);
//	        testCase.addQueryEvidenceCurrentStep(v5);
//	        
//		// PASO 5
//		addStep("Validar en RETEK en la tabla FEM_RTVHEAD_TEMP se hayan actualizado los datos del insumo en las columnas REFERENCE_2, REFERENCE_5 y REFERENCE_9");
//		
//        System.out.println(tabla6RMS);
//      	SQLResult v6 = RMS.executeQuery(tabla6RMS);
//        testCase.addQueryEvidenceCurrentStep(v6);
//		
//        
//        //---------------------------------------------------------------------------------------------------------------------------------------------------------
//        
//        addStep("Validar que en la tabla WM_LOG_RUN el registro del último log de la interfaz haya terminado con status = E");
//		
//		System.out.println(tabla1);
//     	SQLResult v1 = WMQA.executeQuery(tabla1);
//        testCase.addQueryEvidenceCurrentStep(v1);
//        
//		// PASO 3
//		addStep("Validar que en la tabla de WM_LOG_ERROR se muestre el registro relacionado al RUN_ID del log el error.");
//		
//		System.out.println(tabla2);
//     	SQLResult v2 = WMQA.executeQuery(tabla2);
//        testCase.addQueryEvidenceCurrentStep(v2);
//        
//		// PASO 4
//		addStep("Validar que en la tabla de WM_LOG_THREAD muestre con el PARENT_ID el error.");
//		
//		System.out.println(tabla3);
//     	SQLResult v32 = WMQA.executeQuery(tabla3);
//        testCase.addQueryEvidenceCurrentStep(v32);
//        
//        
//        //--------------------------------------------------------------------------------------------------------------------------------------------------
//        // PARTES DE 
//        
//        
//		/*// PASO 1
//		addStep("Ejecutar la interface: RO3_MX.Mappings:run");
//		// PASO 1
//				addStep("Acceder a Control M ");
//				 
//					Control_mInicio cm = new Control_mInicio(u, data.get("user"),data.get("ps"));
//					u.get(data.get("server"));
//					u.hardWait(40); 
//					u.waitForLoadPage();
//					cm.logOn();
//					  
//						JSONObject obj = new JSONObject(data.get("jobs")); 
//						ControlM job = new ControlM(u, testCase, obj); 
//						boolean flag = job.searchJob(); 
//						assertTrue(flag);
//								  
//						String resultado = job.executeJob();
//						System.out.println("Resultado de la ejecucion -> " + resultado);
//						assertEquals(resultado, "Ended OK");
//				
//						u.hardWait(4); 
//						job.closeViewpoint(); */
//		u.close();
//	}
//
//	@Override
//	public String setTestFullName() {
//		return "EjecucionInterface_test";
//	}
//
//	@Override
//	public String setTestDescription() {
//		return "Validar que la interface RO3_MX se ejecute y y mostrar datos de tablas antes de la ejecucion";
//	}
//
//	@Override
//	public String setTestDesigner() {
//		return "ricardor4@hexaware.com";
//	}
//
//	@Override
//	public String setTestInstanceID() {
//		return null;
//	}
//
//	@Override
//	public String setPrerequisites() {
//		return "No haber ejecutado la interface RO3_MX";
//	}
//
//	@Override
//	public void beforeTest() {
//
//	}
//}
