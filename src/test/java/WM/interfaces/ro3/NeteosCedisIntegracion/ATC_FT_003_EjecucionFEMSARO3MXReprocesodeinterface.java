//package WM.interfaces.ro3.NeteosCedisIntegracion;
//
//import java.util.HashMap;
//
//import org.testng.annotations.Test;
//
//import modelo.BaseExecution; import WM.util.GlobalVariables;
//import util.GlobalVariables;
//import utils.selenium.MEdgeTest;
//import utils.selenium.SeleniumUtil;
//import utils.sql.SQLResult;
///**
// * PRERREQUISITOS: haber ejecutado la interface RO3_MX
// * tener ordenes con errores pendientes para ordenes de tipo AR
// *
// * DESCRIPCION: Validar que la interface RO3_MX solo reprocese la información RETURNS (RTV - AR)
// * @author ricardor4@hexaware.com
// */
//public class ATC_FT_003_EjecucionFEMSARO3MXReprocesodeinterface extends BaseExecution {
//
//	@Test(dataProvider = "data-provider")
//	public void ATC_FT_003_EjecucionFEMSARO3MXReprocesodeinterface_test(HashMap<String, String> data) throws Exception {
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
//		//------------------------------AQUI OBTENEMOS EL REFERENCE 5 DE LA SEGUNDA VALIDACION  DE RMS----
//		SQLResult obtengoReference = RMS.executeQuery(tabla3RMS);
//		String REFERENCE = obtengoReference.getData(0, "REFERENCE_5");
//		
//		String tabla4EBS = "select INTERFACE_LINE_ID,INTERFACE_LINE_ATTRIBUTE1,INTERFACE_LINE_ATTRIBUTE3 from XXFC.XXFC_XP_INTERFACE_SALESCREDITS WHERE INTERFACE_LINE_ATTRIBUTE1 ="+"'"+REFERENCE+"'";
//		String tabla5EBS = "select INTERFACE_LINE_ID,INTERFACE_LINE_ATTRIBUTE1,INTERFACE_LINE_ATTRIBUTE3 from XXFC.XXFC_XP_INTERFACE_LINES WHERE INTERFACE_LINE_ATTRIBUTE1 ="+"'"+REFERENCE+"'";
//		
//
//		/*
//		 * PASOS DEL CASO DE PRUEBA
//		 */
//
//		
//		// PASO 1
//		addStep("Ejecutar la interface: RO3_MX.Mappings:run");
//		// PASO 2
//		addStep("Validar en EBS en la tabla XXFC_XP_INTER_SALESCREDITS con el filtro de invoice_num enviado el valor de la REFERENCE_5 obtener el invoice_id de la consulta obtenida.");
//        System.out.println(tabla4EBS);
//      	SQLResult v4 = AVANTE.executeQuery(tabla4EBS);
//        testCase.addQueryEvidenceCurrentStep(v4);
//		// PASO 3
//		addStep("Validar en EBS en la tabla XXFC_XP_INTERFACE_LINES con el filtro de invoice_id.");
//        System.out.println(tabla5EBS);
//      	SQLResult v5 = AVANTE.executeQuery(tabla5EBS);
//        testCase.addQueryEvidenceCurrentStep(v5);
//		u.close();
//	}
//
//	@Override
//	public String setTestFullName() {
//		return "ATC_FT_003_EjecucionFEMSARO3MXReprocesodeinterface_test";
//	}
//
//	@Override
//	public String setTestDescription() {
//		return "Validar que la interface RO3_MX solo reprocese la información RETURNS (RTV - AR).";
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
//		return "Validar que exista registros pendientes en estatus L en la tabla: WM_RA_INTERFACE_SC_ALL";
//	}
//
//	@Override
//	public void beforeTest() {
//
//	}
//}
