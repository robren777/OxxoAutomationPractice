//package WM.interfaces.ro3.NeteosCedisIntegracion;
//
//import static org.junit.Assert.assertFalse;
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
///**
// * PRERREQUISITOS: haber ejecutado la interface RO3_MX
// * tener ordenes con errores de tipo RTV en ordenes AP
// *
// * DESCRIPCION: Validar que la interface RO3_MX solo procese la informacion RETURNS (RTV - AP)
// * @author ricardor4@hexaware.com
// */
//public class ATC_FT_001_EjecucionFEMSARO3MXRegistrosAP extends BaseExecution {
//
//	@Test(dataProvider = "data-provider")
//	public void ATC_FT_001_EjecucionFEMSARO3MXRegistrosAP_test(HashMap<String, String> data) throws Exception {
//
//		/*
//		 * VARIABLES
//		 */
//		//--------------------------------Segunda validacion RMS-------------------------------------------
//		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);
//		utils.sql.SQLUtil RMS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS_MEX,
//				GlobalVariables.DB_USER_RMS_MEX, GlobalVariables.DB_PASSWORD_RMS_MEX2);
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
//		//------------------------------AQUI OBTENEMOS EL REFERENCE 2 DE LA SEGUNDA VALIDACION  DE RMS----
//		SQLResult obtengoReference = RMS.executeQuery(tabla3RMS);
//		String REFERENCE = obtengoReference.getData(0, "REFERENCE_2");
//		
//		String tabla4EBS = "select INVOICE_ID,INVOICE_NUM,ATTRIBUTE2,REFERENCE_2,DESCRIPTION from XXFC.XXFC_XP_INVOICES_INTERFACE WHERE INVOICE_ID ="+"'"+REFERENCE+"'";
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
//		u.close();
//	}
//
//	@Override
//	public String setTestFullName() {
//		return "ATC_FT_001_EjecucionFEMSARO3MXRegistrosAP_test";
//	}
//
//	@Override
//	public String setTestDescription() {
//		return "Validar que la interface RO3_MX solo procese la informacion RETURNS (RTV - AP), con filtro de MX y la configuración del cfg_launcher con el CR CEDIS configurado.";
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
//				+ "Verificar que exista informacion en las tablas de RMS"
//				+ "Verificar que el CR del CEDIS exista en la siguiente tabla: XXFC.XXFC_MAPEO_CEDIS_PLAZA"
//				+ "Validar en la tabla: FEM_RTVHEAD_TEMP exista información con la siguientes condiciones: REFERENCE_2 IS NULL AND REFERENCE_9 IS NULL.";
//	}
//
//	@Override
//	public void beforeTest() {
//
//	}
//}