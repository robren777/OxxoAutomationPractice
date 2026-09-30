package WM.EvolucionRetiroSinTarjeta.HU12;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_003_Validar_Metodo_de_Pago_para_la_Forma_de_Pago_PYIO26 extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_003_Validar_Metodo_de_Pago_para_la_Forma_de_Pago_PYIO26(HashMap<String, String> data) throws Exception {
        /**
         * Metodo de pago para la Forma de Pago PYIO26
         * Desc:
         * Validar el metodo de pago para la forma de pago PYIO26
         * @author Miguel Escamilla
         * @date 09/04/2024
         **/
        //BD AVEBQA
        SQLUtil dbAVEBQA = new SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
                GlobalVariables.DB_PASSWORD_AVEBQA);

        System.out.println(
                "***************************************** PASO 1 y 2 ************************************************");

        String queryXXFC_MPC_GL_CONFIG_COMI = "SELECT FORMA_PAGO, METODO_PAGO, TIPO_COMISION, APLICA_IVA, PERTENECE_A, ESTADO FROM XXFC.XXFC_MPC_GL_CONFIG_COMI WHERE FORMA_PAGO = 'PYIO26' " +
                "AND ESTADO='A' " +
                "AND APLICA_IVA = 'y' " +
                "AND PERTENECE_A = 'PROVEEDOR' " +
                "AND TIPO_COMISION = 'V'";

        SQLResult executeQuery = executeQuery(dbAVEBQA, queryXXFC_MPC_GL_CONFIG_COMI);

        boolean conexionAVEBQA = true;
        conexionAVEBQA = executeQuery.isEmpty();

        addStep("Ingresar a la BD de AVEBQA");
        testCase.addTextEvidenceCurrentStep("Conexion: AVEBQA");
        testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);

        addStep("Se ingresa a la Base de datos AVEBQA para validar la conexion");
        testCase.addQueryEvidenceCurrentStep(executeQuery);
        assertFalse(conexionAVEBQA, "La conexion no fue exitosa");

        if (!conexionAVEBQA){
            System.out.println("Conexion con la base de datos AVEBQA se realizo de manera exitosa");
        }

        System.out.println("Consulta: " + queryXXFC_MPC_GL_CONFIG_COMI);
    }

    @Override
    public String setTestFullName() {
        return "003 - Validar Método de Pago para la Forma de Pago: 'PYIO26'";
    }

    @Override
    public String setTestDescription() {
        return "Validar Método de Pago para la Forma de Pago: 'PYIO26'";
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
        return "Acceso a base de datos AVEBQA";
    }
    @Override
    public void beforeTest() {

    }
}
