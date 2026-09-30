//package WM.interfaces.ro3.NeteosCedisIntegracion;
//
//import static org.testng.Assert.assertEquals;
//import static org.testng.Assert.assertTrue;
//import static org.testng.AssertJUnit.assertTrue;
//import java.util.HashMap;
//import org.testng.annotations.Test;
//
//import modelo.BaseExecution; import WM.util.GlobalVariables;
//import util.GlobalVariables;
//import utils.selenium.MEdgeTest;
//import utils.selenium.SeleniumUtil;
//import utils.sql.SQLResult;
///**
// * PRERREQUISITOS: haber ejecutado la interface RO3_MX
// * tener ordenes con errores 
// *
// * DESCRIPCION: verifica que el error sea marcado con estaus -10 en el campo REFERENCE_2
// * @author ricardor4@hexaware.com
// */
//public class ATC_FT_004_EjecucionFEMSARO3MXMantenerRegistros10 extends BaseExecution {
//
//	@Test(dataProvider = "data-provider")
//	public void ATC_FT_004_EjecucionFEMSARO3MXMantenerRegistros10_test(HashMap<String, String> data) throws Exception {
//
//		/*
//		 * VARIABLES
//		 */
//
//		
//
//		/*
//		 * UTILERIAS
//		 */
//		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);
//		utils.sql.SQLUtil RMS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS_MEX,
//				GlobalVariables.DB_USER_RMS_MEX, GlobalVariables.DB_PASSWORD_RMS_MEX2);
//		
//		String RTVORDER = data.get("rtvorder");
//		String tabla3RMS = "select RTV_ORDER_NO,WH,SUPPLIER_ID,REFERENCE_2,REFERENCE_5,REFERENCE_9 from  FEM_RTVHEAD_TEMP WHERE RTV_ORDER_NO ="+"'"+RTVORDER+"'";
//		/*
//		 * PASOS DEL CASO DE PRUEBA
//		 */
//
//		
//		// PASO 1
//		addStep("Ejecutar la interface: RO3_MX.Mappings:run");
//		// PASO 2
//		addStep("Validar que el insumo identificado en los prerrequisitos se haya marcado con -10");
//		SQLResult obtengovalidacion = RMS.executeQuery(tabla3RMS);
//		testCase.addQueryEvidenceCurrentStep(obtengovalidacion);
//		String validacion = obtengovalidacion.getData(0, "REFERENCE_2");
//		
//		try	{
//			assertEquals(validacion,"-10");
//			testCase.addBoldTextEvidenceCurrentStep("Validacion Exitosa");
//		}
//		catch(Exception e)
//		{
//			testCase.addBoldTextEvidenceCurrentStep("el insumo identificado en los prerrequisitos no se marco con -10");
//			
//		}
//		u.close();
//	}
//
//	@Override
//	public String setTestFullName() {
//		return "ATC_FT_004_EjecucionFEMSARO3MXMantenerRegistros10_test";
//	}
//
//	@Override
//	public String setTestDescription() {
//		return "Validar que la interface RO3_MX reporte los errores encontrados y mantener los registros marcados con -10";
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