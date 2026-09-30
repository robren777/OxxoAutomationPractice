package WM.EvolucionRetiroSinTarjeta.HU09;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;


public class ATC_FT_001_Acceso_a_Base_de_datos_TPEREP extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_001_Acceso_a_Base_de_datos_TPEREP(HashMap<String, String> data) throws Exception {
        /**
         * Consulta a la base de datos TPEREP/FCWMREQA
         * Desc:
         * Validar acceso a base de datos TPEREP con consulta
         * @author Miguel Escamilla
         * @date 25/01/2024
         **/

        //BD FCWMREQA
        SQLUtil dbFCWMREQA = new SQLUtil(GlobalVariables.DB_HOST_FCWMREQA, GlobalVariables.DB_USER_FCWMREQA,
                GlobalVariables.DB_PASSWORD_FCWMREQA);


        System.out.println(
                "***************************************** PASO 1 y 2 ************************************************");

        String queryFCWMREQA_TPE_FR_TRANSACTION_RST = "SELECT * FROM ( SELECT APPLICATION, ENTITY, FOLIO, CREATION_DATE FROM TPEREP.TPE_FR_TRANSACTION_RST ORDER BY CREATION_DATE DESC ) WHERE ROWNUM <= 5";

        System.out.println("Consulta: " + queryFCWMREQA_TPE_FR_TRANSACTION_RST);
        SQLResult Execute_dbFCWMREQA_Query = executeQuery(dbFCWMREQA, queryFCWMREQA_TPE_FR_TRANSACTION_RST);

        boolean conexionFCWMREQA = true;
        conexionFCWMREQA = Execute_dbFCWMREQA_Query.isEmpty();

        addStep("Ingresar a la BD de FCWMREQA");
        testCase.addTextEvidenceCurrentStep("Conexion: FCWMREQA");
        testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMREQA);

        addStep("Se ingresa a la Base de datos FCWMREQA para validar la conexion");
        testCase.addQueryEvidenceCurrentStep(Execute_dbFCWMREQA_Query);
        assertFalse(conexionFCWMREQA, "La conexion no fue exitosa");

        if (!conexionFCWMREQA){
            System.out.println("Conexion con la base de datos FCWMREQA se realizo de manera exitosa");
        }

        System.out.println("Se imprime una muestra de datos:");
        List<String> columnNames = Execute_dbFCWMREQA_Query.getColumnNames();
        Map<String, String> datos = Execute_dbFCWMREQA_Query.getRowMap(1);

        columnNames.forEach( x -> { System.out.println(x + ": " + datos.get(x)); } );



    }

    @Override
    public void beforeTest() {
        // TODO Auto-generated method stub

    }

    @Override
    public String setPrerequisites() {
        // TODO Auto-generated method stub
        return "Acceso a Base de datos TPEREP";
    }

    @Override
    public String setTestDescription() {
        // TODO Auto-generated method stub
        return "Validar acceso a Base de datos TPEREP";
    }

    @Override
    public String setTestDesigner() {
        // TODO Auto-generated method stub
        return "Miguel Angel Escamilla Reyes";
    }

    @Override
    public String setTestFullName() {
        // TODO Auto-generated method stub
        return "001-Acceso a Base de datos TPEREP";
    }

    @Override
    public String setTestInstanceID() {
        // TODO Auto-generated method stub
        return null;
    }

}
