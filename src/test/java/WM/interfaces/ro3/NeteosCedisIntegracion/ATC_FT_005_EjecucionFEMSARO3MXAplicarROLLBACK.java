//package WM.interfaces.ro3.NeteosCedisIntegracion;
//
//import static org.testng.Assert.assertEquals;
//import static org.testng.Assert.assertTrue;
//
//import java.util.HashMap;
//
//import org.json.JSONObject;
//import org.testng.annotations.Test;
//import utils.sql.SQLResult;
//import integrationServer.om.PakageManagment;
//import modelo.BaseExecution; import WM.util.GlobalVariables;
//import util.GlobalVariables;
//import utils.controlm.ControlM;
//import utils.controlm.pageObject.Control_mInicio;
//import utils.password.PasswordUtil;
//import utils.selenium.MEdgeTest;
//import utils.selenium.SeleniumUtil;
///**
// * PRERREQUISITOS: tener ordenes nuevas con REFERENCE_2 = null,REFERENCE_9 = null,REFERENCE_5 = null
// * tener RTV ligado a orden en la tabla XXFC.XXFC_RTV_COSTO_CONCEPTO
// *
// * DESCRIPCION: ejecuta la interface R03_MX
// * @author ricardor4@hexaware.com
// */
//public class ATC_FT_005_EjecucionFEMSARO3MXAplicarROLLBACK extends BaseExecution {
//
//	@Test(dataProvider = "data-provider")
//	public void ATC_FT_005_EjecucionFEMSARO3MXAplicarROLLBACK_test(HashMap<String, String> data) throws Exception {
//
//		/*
//		 * VARIABLES
//		 */
//
//		PakageManagment pok;	
//		String status = data.get("status");  // status exitoso
//		String user = data.get("user");
//		String ps = PasswordUtil.decryptPassword(data.get("ps"));
//		String server = data.get("server");
//		String searchedStatus = "R";
//		String run_id = "";
//		
//		String tdcIntegrationServerFormat = "select * from (SELECT Tbl.run_id,interface, start_dt, end_dt, status, server "
//				+ "FROM WMLOG.WM_LOG_RUN Tbl " + "WHERE INTERFACE LIKE '%RO3_MX%' "
//				+ "ORDER BY START_DT DESC) where rownum <=1";
//		
//		utils.sql.SQLUtil dbLog = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQA, GlobalVariables.DB_USER_FCWMLQA,GlobalVariables.DB_PASSWORD_FCWMLQA);
//		
//		/*
//		 * UTILERIAS
//		 */
//		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);
//		/*
//		 * PASOS DEL CASO DE PRUEBA
//		 */
//		utils.sql.SQLUtil RMS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS_MEX,
//				GlobalVariables.DB_USER_RMS_MEX, GlobalVariables.DB_PASSWORD_RMS_MEX2);
//		
//		String RTVORDER = data.get("rtvorder");
//		String tabla3RMS = "select RTV_ORDER_NO,WH,SUPPLIER_ID,REFERENCE_2,REFERENCE_5,REFERENCE_9 from  FEM_RTVHEAD_TEMP WHERE RTV_ORDER_NO ="+"'"+RTVORDER+"'";
//		
//		
//		// PASO 1
//		addStep("Validar que el insumo identificado");
//		SQLResult obtengovalidacion = RMS.executeQuery(tabla3RMS);
//		testCase.addQueryEvidenceCurrentStep(obtengovalidacion);
//		String validacion = obtengovalidacion.getData(0, "REFERENCE_2");
//
//		
//		//--------------------------------------------------------------------------
//		
//		
//		addStep("Ejecutar la interface: RO3_MX.Mappings:run");
//
//		pok = new PakageManagment(u, testCase);
//		
//		System.out.println(GlobalVariables.DB_HOST_FCWMLQA);
//		String contra = "http://" + user + ":" + ps + "@" + server + ":5555";
//		u.get(contra);
//
//		String dateExecution = pok.runIntefaceWmTwoButtonsWihtoutInputs10(data.get("interfase"), data.get("servicio"));
//		System.out.println("Respuesta dateExecution " + dateExecution);
//		System.out.println(tdcIntegrationServerFormat);
//		
//		SQLResult is = dbLog.executeQuery(tdcIntegrationServerFormat);
//		run_id = is.getData(0, "RUN_ID");
//		String status1 = is.getData(0, "STATUS");
//		System.out.println("RUN_ID = " + run_id + "\t Status: " + status1 );
//        
//		boolean valuesStatus = status1.equals(searchedStatus);// Valida si se encuentra en estatus R
//
//		while (valuesStatus) {			
//			status1 = is.getData(0, "STATUS");
//			run_id = is.getData(0, "RUN_ID");
//			valuesStatus = status1.equals(searchedStatus);
//			u.hardWait(2);			
//		}
//		
//		
//		// PASO 2
//		addStep("Validar que la interface aplica correctamente el Rollback.");
//		
//		
//		addStep("Validar que el insumo identificado");
//		SQLResult obtengovalidacion1 = RMS.executeQuery(tabla3RMS);
//		testCase.addQueryEvidenceCurrentStep(obtengovalidacion1);
//		String validacion1 = obtengovalidacion1.getData(0, "REFERENCE_2");
//		
//		testCase.addBoldTextEvidenceCurrentStep("el insumo identificado en los prerrequisitos no fue alterado");
//	    u.close();
//	} 
//
//	@Override
//	public String setTestFullName() {
//		return "ATC_FT_005_EjecucionFEMSARO3MXAplicarROLLBACK_test";
//	}
//
//	@Override
//	public String setTestDescription() {
//		return "Validar que la interface RO3_MX, aplique correctamente el rollback y mantener correctamente los registros pendientes para procesar.";
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
//		return "En wm_cfg_launcher se debe validar que este configurado el valor INTERFACE_NAME con RO3_MX en AUTO_ESTATUS en A y que el valor del CEDIS este configurado en ATTRIBUTE1."
//				+ "Verificar que exista información en las tablas de RMS."                                                                      
//				+ "Verificar que el CR del CEDIS exista en la siguiente tabla: XXFC.XXFC_MAPEO_CEDIS_PLAZA."                                                                                                                                                                      
//				+ "Validar en la tabla: FEM_RTVHEAD_TEMP exista información con la siguientes condiciones: REFERENCE_2 IS NULL AND REFERENCE_9 IS NULL";
//	}
//
//	@Override
//	public void beforeTest() {
//
//	}
//}