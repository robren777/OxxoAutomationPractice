package WM.EvolucionRetiroSinTarjeta.HU11;
import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import modelo.BaseExecution;
import EBS.util.GlobalVariables;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_003_Validar_que_exista_el_ID_de_HPAY_en_la_BD_FCWM6QA extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_Validar_que_exista_el_ID_de_HPAY_en_la_BD_FCWM6QA_test(HashMap<String, String> data)
	throws Exception{
		
		/**
		 * Plataforma de Oracle: Tener conexion a la BD FCWM6QA
		 * Desc: Validar que exista el ID de HPAY en la BD FCWM6QA
		 * @author Mario Garza
		 * @date 16/03/2024
		 **/
		/*
		 * Utilerias
		 *********************************************************************/
		 //BD FCWM6QA
        SQLUtil dbFCWM6QA = new SQLUtil(GlobalVariables.DB_HOST_FCWM6QA_QAVIEW, GlobalVariables.DB_USER_FCWM6QA_QAVIEW,
                GlobalVariables.DB_PASSWORD_FCWM6QA_QAVIEW);



        System.out.println(
                "***************************************** PASO 1 y 2 ************************************************");
      //Se realiza la consulta
        String queryWM_FT_TRN_CONFIG = "select * from WMUSER.WM_FT_TRN_CONFIG WHERE ID='2'";
      //Se guarda en la variable
        SQLResult executeQuery = executeQuery(dbFCWM6QA, queryWM_FT_TRN_CONFIG);

        boolean conexionFCWM6QA = true;
        conexionFCWM6QA = executeQuery.isEmpty();
      //Se toma la evidencia
        addStep("Ingresar a la BD de FCWM6QA");
        testCase.addTextEvidenceCurrentStep("Conexion: FCWM6QA");
        testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWM6QA_QAVIEW);

        addStep("Se ingresa a la Base de datos FCWM6QA para validar la conexion");
        testCase.addQueryEvidenceCurrentStep(executeQuery);
      //Se valida si la conexion  no fue exitosa
        assertFalse(conexionFCWM6QA, "La conexion no fue exitosa");

        if (!conexionFCWM6QA){
            System.out.println("Conexion con la base de datos FCWM6QA se realizo de manera exitosa");
        }

        System.out.println("Consulta: " + queryWM_FT_TRN_CONFIG);


		
	}

	@Override
	public void beforeTest() {
	}	

	@Override
	public String setTestDescription() {
		return "Validar que exista el ID de HPAY en la BD FCWM6QA";
	}

	@Override
	public String setTestDesigner() {
		return "Mario.garza@grupotimexico.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_Validar_que_exista_el_ID_de_HPAY_en_la_BD_FCWM6QA";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "La configuraciones deben estar creadas en la tabla TPE_TABLAS_COPIA de la BD FCWMREQA";
	}

	@AfterMethod
	public void afterMethod() {

	}

}
