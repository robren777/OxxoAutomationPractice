package WM.EvolucionRetiroSinTarjeta.HU16;

import modelo.BaseExecution;
import org.testng.annotations.Test;
import EBS.util.GlobalVariables;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

import java.util.HashMap;

import static org.testng.Assert.assertFalse;

public class ATC_FT_001_Validar_registros_en_base_de_datos_TPEREP_y_tabla_TPE_FR_TRANSACTION_RST extends BaseExecution {
    /**
     * Consulta a la base de datos TPEREP/FCWMREQA
     * Desc:
     * Validar registros en base de datos TPEREP y tabla TPE_FR_TRANSACTION_RST con cosulta
     * @author Miguel Escamilla
     * @date 12/04/2024
     **/
    @Test(dataProvider = "data-provider")
    public void ATC_FT_001_Validar_registros_en_base_de_datos_TPEREP_y_tabla_TPE_FR_TRANSACTION_RST(HashMap<String, String> data) throws Exception {


        //BD FCWMREQA
        SQLUtil dbFCWMREQA = new SQLUtil(GlobalVariables.DB_HOST_FCWMREQA, GlobalVariables.DB_USER_FCWMREQA,
                GlobalVariables.DB_PASSWORD_FCWMREQA);

        System.out.println(
                "***************************************** PASO 1 y 2 ************************************************");

        String queryTPE_FR_TRANSACTION_RST = "SELECT * FROM (SELECT APPLICATION, ENTITY, FOLIO, CREATION_DATE, WM_STATUS_FT, WM_STATUS_WO01 FROM TPEREP.TPE_FR_TRANSACTION_RST" +
                " WHERE WM_STATUS_FT = 'L' AND ENTITY IN('CNKTA', 'HPAY', 'FTECH') AND OPERATION = 'TRN03' AND WM_CODE = '101' ORDER BY CREATION_DATE DESC) WHERE ROWNUM <= 10";

        SQLResult executeQuery = executeQuery(dbFCWMREQA, queryTPE_FR_TRANSACTION_RST);

        boolean conexionFCWMREQA = true;
        conexionFCWMREQA = executeQuery.isEmpty();

        addStep("Ingresar a la BD de FCWMREQA");
        testCase.addTextEvidenceCurrentStep("Conexion: FCWMREQA");
        testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMREQA);

        addStep("Se ingresa a la Base de datos FCWMREQA para validar la conexion");
        testCase.addQueryEvidenceCurrentStep(executeQuery);
        assertFalse(conexionFCWMREQA, "La conexion no fue exitosa");

        if (!conexionFCWMREQA){
            System.out.println("Conexion con la base de datos FCWMREQA se realizo de manera exitosa");
        }

        System.out.println("Consulta: " + queryTPE_FR_TRANSACTION_RST);
    }

    @Override
    public String setTestFullName() {
        return "001 - Validar registros en base de datos TPEREP y tabla TPE_FR_TRANSACTION_RST";
    }

    @Override
    public String setTestDescription() {
        return "Validar registros en base de datos TPEREP y tabla TPE_FR_TRANSACTION_RST";
    }

    @Override
    public String setTestDesigner() {
        return "Miguel Angel Escamilla Reyes";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }

    @Override
    public String setPrerequisites() {
        return "Acceso a la base de datos TPEREP";
    }

    @Override
    public void beforeTest() {

    }
}
