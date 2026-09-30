package WM.interfaces.ro3.NeteosCedisIntegracion;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
/**
 * PRERREQUISITOS: haber ejecutado la interface RO3_MX
 * tener ordenes con errores de tipo error al publicar
 *
 * DESCRIPCION: verifica que los errores se marquen con estatus E
 * @author ricardor4@hexaware.com
 */
public class ATC_FT_006_EjecucionFEMSARO3MXErrores extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_EjecucionFEMSARO3MXErrores_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		
		String runid = data.get("runid");
		String tabla1 = "SELECT RUN_ID,INTERFACE,START_DT,END_DT,STATUS FROM WMLOG.WM_LOG_RUN WHERE RUN_ID ="+""+runid+"";
		String tabla2 = "SELECT RUN_ID,DESCRIPTION,MESSAGE FROM WMLOG.WM_LOG_ERROR WHERE RUN_ID ="+""+runid+"";
		String tabla3 = "SELECT THREAD_ID,PARENT_ID,NAME,START_DT FROM WMLOG.WM_LOG_THREAD where PARENT_ID ="+""+runid+"";

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);
		utils.sql.SQLUtil 	FCWMLQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQA,
				GlobalVariables.DB_USER_FCWMLQA, GlobalVariables.DB_PASSWORD_FCWMLQA);
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		
		// PASO 1
		addStep("Ejecutar la interface: RO3_MX.Mappings:run");
		// PASO 2
		addStep("Validar que en la tabla WM_LOG_RUN el registro del último log de la interfaz haya terminado con status = E");
		
		System.out.println(tabla1);
     	SQLResult v1 = FCWMLQA.executeQuery(tabla1);
        testCase.addQueryEvidenceCurrentStep(v1);
        
		// PASO 3
		addStep("Validar que en la tabla de WM_LOG_ERROR se muestre el registro relacionado al RUN_ID del log el error.");
		
		System.out.println(tabla2);
     	SQLResult v2 = FCWMLQA.executeQuery(tabla2);
        testCase.addQueryEvidenceCurrentStep(v2);
        
		// PASO 4
		addStep("Validar que en la tabla de WM_LOG_THREAD muestre con el PARENT_ID el error.");
		
		System.out.println(tabla3);
     	SQLResult v3 = FCWMLQA.executeQuery(tabla3);
        testCase.addQueryEvidenceCurrentStep(v3);
        
		// PASO 5
		addStep("Se valida que se envió correo de Error.");
		u.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_EjecucionFEMSARO3MXErrores_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que los diferentes Errores  se guarden en las tablas: WM_LOG_RUN y WM_LOG_ERROR con estatus E:"
				+ "Error al publicar los documentos";
	}

	@Override
	public String setTestDesigner() {
		return "ricardor4@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "En wm_cfg_launcher se debe validar que este configurado el valor INTERFACE_NAME con RO3_MX en AUTO_ESTATUS en A y que el valor del CEDIS este configurado en ATTRIBUTE1."
				+ "Verificar que exista información en las tablas de RMS."
				+ "Verificar que el CR del CEDIS exista en la siguiente tabla: XXFC.XXFC_MAPEO_CEDIS_PLAZA."
				+ "Validar en la tabla: FEM_RTVHEAD_TEMP exista información con la siguientes condiciones: REFERENCE_2 IS NULL AND REFERENCE_9 IS NULL";
	}

	@Override
	public void beforeTest() {

	}
}