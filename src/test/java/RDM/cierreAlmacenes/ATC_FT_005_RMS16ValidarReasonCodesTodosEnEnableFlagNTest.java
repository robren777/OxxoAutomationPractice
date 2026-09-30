package RDM.cierreAlmacenes;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import RDM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
* DESCRIPCI�N: Clase para ejecucion de Caso de Prueba ATC_FT_005_RMS16ValidarReasonCodesEnEnableFlagN.
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* -MTC_FT_005 RMS16 Validar Reason Codes En Enable Flag N
* 
* PREREQUISITOS:
* - Contar con acceso a:"
				-BASE DE DATOS RMS16 FCMOMSIT
				-BASE DE DATOS EBS
				-CONTROL M
				-Todos los objetos de enable_flag en estado N
* 
* INSUMOS:
* -Contar con los articulos necesarios creados en RMS tabla nb_fem_fif_stg
* 
* @author Cecilia Luna
* @date 12/Feb/2025
*/

public class ATC_FT_005_RMS16ValidarReasonCodesTodosEnEnableFlagNTest extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_005_RMS16ValidarReasonCodesTodosEnEnableFlagNTest_test(HashMap<String, String> data) throws Exception{
        /*
         * VARIABLES
         */
        String tran_date = data.get("tran_date");
        String tran_code = data.get("tran_code");
        String cr_plaza = data.get("cr_plaza");
        String controlMUser = data.get("controlMUser");
        String controlMPass = PasswordUtil.decryptPassword(data.get("controlMPass"));
        String controlMServer = data.get("controlMServer");
        String inv_adj_user_id = data.get("inv_adj_user_id");

        /*
         * UTILERIAS
         */
        SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_RMS16, GlobalVariables.DB_USER_RMS16,
                GlobalVariables.DB_PASSWORD_RMS16);
        SQLUtil dbEbs = new SQLUtil(GlobalVariables.DB_HOST_EBS, GlobalVariables.DB_USER_EBS,
                GlobalVariables.DB_PASSWORD_EBS);
        SeleniumUtil u = new SeleniumUtil(new ChromeTest());

        // PASO 1
        addStep("Validar configuraci�n de Enable Flag en N");
        System.out.println("PASO 1");

        testCase.addBoldTextEvidenceCurrentStep("Conectado a la BD RMS16: ");
        testCase.addTextEvidenceCurrentStep("Host: " + dbRms.getHost());
        testCase.addTextEvidenceCurrentStep("User: " + dbRms.getUser());

        String enableN = String.format("select IF_ID, ATTRIBUTE1 TRAN_CODE, ABS(h.LOOKUP_CODE) REASON_CODE, ENABLED_FLAG \n" +
                "                        from wmuser.wm_fnd_lookup_values h\n" +
                "                        where lookup_type = 'REASON_CODE_TYPE'\n" +
                "                        and attribute1 = 22\n" +
                "                        and ENABLED_FLAG = 'N' and IF_ID = 'RO8_MX' order by lookup_code asc");
        System.out.println(enableN);

        SQLResult executeQueryEnableN = executeQuery(dbRms, enableN);

        boolean statusQueryEnableN = executeQueryEnableN.isEmpty();
        if (!statusQueryEnableN) {
            testCase.addQueryEvidenceCurrentStep(executeQueryEnableN);
        }

        // PASO 2
        addStep("Validar en la BD los ajustes de inventario a procesar con "
                + "tran_code 22, plaza 15CMF,reference_3 y reference_9 en NULL.");
        System.out.println("PASO 2");

        String refNull = String.format("select ITEM, ITEM_DESC,REFERENCE_10 R10, REFERENCE_3 R3, REFERENCE_9 R9, TRAN_DATE, TRAN_CODE, TOTAL_COST, TOTAL_RETAIL, UNITS, VAT_RATE\r\n"
                + "from rms_ext.nb_fem_fif_stg where tran_date >='%s'\r\n"
                + "and inv_adj_user_id = '%s'\r\n"
                + "and reference_3 is null and reference_9 is null", tran_date, inv_adj_user_id);
        System.out.println(refNull);

        SQLResult executeQuery = executeQuery(dbRms, refNull);

        boolean statusQuery = executeQuery.isEmpty();
        if (!statusQuery) {
            testCase.addQueryEvidenceCurrentStep(executeQuery);
        }

        assertFalse(statusQuery, "No se encontraron resultados en Fem_fif_stg "
        + "con tran_date = " + tran_date
        + " y TRAN_CODE = " + tran_code
        + " y CR_PLAZA = " + cr_plaza);


        // PASO 3
        addStep("Ejecutar el proceso runRO08_MX_AJUST carpeta TQA_BO_1011_NUCLEO");
        System.out.println("PASO 3");

        JSONObject obj = new JSONObject(data.get("runRO08_MX_AJUST"));
        Control_mInicio CM = new Control_mInicio(u, controlMUser, controlMPass);

        u.get(controlMServer);
        u.waitForLoadPage();
        CM.logOn(); // loguea
        u.hardWait(40);

        ControlM control = new ControlM(u, testCase, obj); // si es uno por uno, debes instanciar esta clase
        control.searchJob(); // busca el job
        control.accionSinOutput();

        // PASO 4
        addStep("Validar en la BD los ajustes de inventario con codigo -1029"
                + "en el campo reference_3.");
        System.out.println("PASO 4");

        String refCodigo = String.format("select ITEM, ITEM_DESC,REFERENCE_10 R10, REFERENCE_3 R3, REFERENCE_9 R9, TRAN_DATE, TRAN_CODE, TOTAL_COST, TOTAL_RETAIL, UNITS, \n" +
                "VAT_RATE \r\n"
                + "from rms_ext.nb_fem_fif_stg \r\n"
                + "where tran_date = '%s'\r\n"
                + "and inv_adj_user_id = '%s'\r\n"
                + "and reference_3 = -1029", tran_date, inv_adj_user_id);
        System.out.println(refCodigo);

        SQLResult executeQueryCodigo = executeQuery(dbRms, refCodigo);

        boolean statusQueryCodigo = executeQueryCodigo.isEmpty();
        if (!statusQueryCodigo) {
            testCase.addQueryEvidenceCurrentStep(executeQueryCodigo);
        }
        Assert.assertFalse(statusQueryCodigo, "No se encontraron resultados en rms_ext.nb_fem_fif_stg "
                + "con reference_3 = -1029");

    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
        return "Contar con acceso a:\r\n"
                + "BASE DE DATOS FCMOMSIT (RMS16)\r\n"
                + "BASE DE DATOS EBS\r\n"
                + "CONTROL M\r\n"
                + "\r\n"
                + "Contar con los objetos necesarios creados en R08\r\n"
                + "Todos los objetos de enable_flag en estado N";
    }

    @Override
    public String setTestDescription() {
        return "Validar que al seleccionar los reason code registrado con"
                + " enable_flag con valor N en R08, generando"
                + " el reporte satisfactoriamente";
    }

    @Override
    public String setTestDesigner() {
        return "ceciliaalejandral@hexaware.com";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_005_RMS16ValidarReasonCodesTodosEnEnableFlagNTest_test";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }

}
