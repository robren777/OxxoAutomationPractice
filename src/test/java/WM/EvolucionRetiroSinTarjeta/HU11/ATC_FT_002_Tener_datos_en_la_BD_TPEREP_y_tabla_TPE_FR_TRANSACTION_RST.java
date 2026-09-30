package WM.EvolucionRetiroSinTarjeta.HU11;
import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import modelo.BaseExecution;
import EBS.util.GlobalVariables;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_002_Tener_datos_en_la_BD_TPEREP_y_tabla_TPE_FR_TRANSACTION_RST extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_Tener_datos_en_la_BD_TPEREP_y_tabla_TPE_FR_TRANSACTION_RST_test(HashMap<String, String> data)
	throws Exception{
		/**
		 * Plataforma de Oracle: Tener conexion a la BD FCWMREQA
		 * Desc: Validar configuraciones para la ejecucion del job runTPECopia.RST.sh en la tabla TPE_TABLAS_COPIA
		 * @author Mario Garza
		 * @date 14/03/2024
		 **/
		/*
		 * Utilerias
		 *********************************************************************/
		SQLUtil dbFCWMREQA = new SQLUtil(GlobalVariables.DB_HOST_FCWMREQA, GlobalVariables.DB_USER_FCWMREQA,
				GlobalVariables.DB_PASSWORD_FCWMREQA);


        System.out.println(
                "***************************************** PASO 1 y 2 ************************************************");
      //Se realiza la consulta
        String queryTPE_FR_TRANSACTION_RST = "SELECT APPLICATION, ENTITY, FOLIO, CREATION_DATE,OPERATION,WM_CODE, WM_STATUS_FT, WM_STATUS_WO01 FROM TPEREP.TPE_FR_TRANSACTION_RST\r\n"
        		+ "WHERE WM_STATUS_FT = 'L' AND ENTITY IN('CNKTA', 'HPAY', 'FTECH') and rownum <= 5";
      //Se guarda en la variable
        SQLResult executeQuery = executeQuery(dbFCWMREQA, queryTPE_FR_TRANSACTION_RST);

        boolean conexionFCWMREQA = true;
        conexionFCWMREQA = executeQuery.isEmpty();
      //Se toma la evidencia
        addStep("Ingresar a la BD de FCWMREQA");
        testCase.addTextEvidenceCurrentStep("Conexion: FCWMREQA");
        testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMREQA);

        addStep("Se ingresa a la Base de datos FCWMREQA para validar la conexion");
        testCase.addQueryEvidenceCurrentStep(executeQuery);
      //Se valida si la conexion  no fue exitosa
        assertFalse(conexionFCWMREQA, "La conexion no fue exitosa");

        if (!conexionFCWMREQA){
            System.out.println("Conexion con la base de datos FCWMREQA se realizo de manera exitosa");
        }

        System.out.println("Consulta: " + queryTPE_FR_TRANSACTION_RST);

		
	}

	@Override
	public void beforeTest() {
	}	

	@Override
	public String setTestDescription() {
		return "Validar configuraciones para la ejecucion del job runTPECopia.RST.sh en la tabla TPE_TABLAS_COPIA";
	}

	@Override
	public String setTestDesigner() {
		return "Mario.garza@grupotimexico.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_Tener_datos_en_la_BD_TPEREP_y_tabla_TPE_FR_TRANSACTION_RST";
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
