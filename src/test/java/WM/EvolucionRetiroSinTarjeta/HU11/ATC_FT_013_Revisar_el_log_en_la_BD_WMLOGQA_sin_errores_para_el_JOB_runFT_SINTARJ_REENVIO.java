package WM.EvolucionRetiroSinTarjeta.HU11;
import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import modelo.BaseExecution;
import EBS.util.GlobalVariables;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_013_Revisar_el_log_en_la_BD_WMLOGQA_sin_errores_para_el_JOB_runFT_SINTARJ_REENVIO extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_013_Revisar_el_log_en_la_BD_WMLOGQA_sin_errores_para_el_JOB_runFT_SINTARJ_REENVIO_test(HashMap<String, String> data)
	throws Exception{
		
		/**
		 * Plataforma de Oracle: Tener conexion a la BD FCWMLOG
		 * Desc: Revisar el log en la BD WMLOGQAsin errores para el JOB (runFT_SINTARJ_REENVIO)
		 * @author Mario Garza
		 * @date 21/03/2024
		 **/
		 //BD FCWMLOG
        SQLUtil dbFCWMLOG = new SQLUtil(GlobalVariables.DB_HOST_FCWMLQA_WMLOG2, GlobalVariables.DB_USER_FCWMLQA_WMLOG2,
                GlobalVariables.DB_PASSWORD_FCWMLQA_WMLOG2);

        System.out.println(
                "***************************************** PASO 1 ************************************************");
      //Se realiza la consulta
        String queryWM_LOG_RUN = "SELECT * FROM (SELECT * FROM WMLOG.WM_LOG_RUN WHERE INTERFACE='ReenvioFT' ORDER BY START_DT DESC) WHERE ROWNUM = 1";
      //Se guarda en la variable
        SQLResult executeFCWMLOG = executeQuery(dbFCWMLOG, queryWM_LOG_RUN);
        //Guardamos el id de la columna 0
        String runID = executeFCWMLOG.getData(0, "RUN_ID");

        boolean conexionFCWMLOG = true;
        conexionFCWMLOG = executeFCWMLOG.isEmpty();
      //Se toma la evidencia
        addStep("Ingresar a la BD de FCWMLOG");
        testCase.addTextEvidenceCurrentStep("Conexion: FCWMLOG");
        testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMLQA_WMLOG2);

        addStep("Se ingresa a la Base de datos FCWMLOG para validar la información de la tabla WM_LOG_RUN");
        testCase.addQueryEvidenceCurrentStep(executeFCWMLOG);
      //Se valida si la conexion  no fue exitosa
        assertFalse(conexionFCWMLOG, "La conexion no fue exitosa");

        if (!conexionFCWMLOG){
            System.out.println("Conexion con la base de datos FCWMLOG se realizo de manera exitosa");
        }
        System.out.println("Consulta: " + queryWM_LOG_RUN);
        //Se realiza la consulta con el id que se guardo
        String queryWM_LOG_ERROR = "SELECT * FROM WMLOG.WM_LOG_ERROR WHERE RUN_ID='" + runID + "'";
        System.out.println("Consulta: " + queryWM_LOG_ERROR);
        SQLResult executeFCWMLOGThread = executeQuery(dbFCWMLOG, queryWM_LOG_ERROR);
        testCase.addQueryEvidenceCurrentStep(executeFCWMLOGThread);

		
	}

	@Override
	public void beforeTest() {
	}	

	@Override
	public String setTestDescription() {
		return "Revisar el log en la BD WMLOGQAsin errores para el JOB (runFT_SINTARJ_REENVIO)";
	}

	@Override
	public String setTestDesigner() {
		return "Mario.garza@grupotimexico.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_013_Revisar_el_log_en_la_BD_WMLOGQA_sin_errores_para_el_JOB_runFT_SINTARJ_REENVIO";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "La configuraciones deben estar creadas de la BD FCWMLOG";
	}

	@AfterMethod
	public void afterMethod() {

	}

}
