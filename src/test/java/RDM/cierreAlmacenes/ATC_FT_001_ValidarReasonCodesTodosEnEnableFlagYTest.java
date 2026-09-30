package RDM.cierreAlmacenes;

import static org.antlr.tool.ErrorManager.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;

import org.json.JSONObject;
import org.openqa.selenium.By;
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
* DESCRIPCI�N: Clase para ejecucion de Caso de Prueba ATC_FT_001_ValidarReasonCodesTodosEnEnableFlagY.
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* -MTC_FT_001 Validar Reason Codes En Enable Flag Y
* 
* PREREQUISITOS:
* - Contar con acceso a:"
				-BASE DE DATOS RMS
				-BASE DE DATOS EBS
				-CONTROL M
				-Todos los objetos de enable_flag en estado Y
* 
* INSUMOS:
* -Contar con los articulos necesarios creados en RMS tabla fem_fif_stg
* 
* @author Cecilia Luna
* @date 07/Feb/2025
*/

public class ATC_FT_001_ValidarReasonCodesTodosEnEnableFlagYTest extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_001_ValidarReasonCodesTodosEnEnableFlagYTest_test(HashMap<String, String> data) throws Exception{
        /*
         * VARIABLES
         */
        String tran_date = data.get("tran_date");
        String controlMUser = data.get("controlMUser");
        String controlMPass = PasswordUtil.decryptPassword(data.get("controlMPass"));
        String controlMServer = data.get("controlMServer");
        String inv_adj_user_id = data.get("inv_adj_user_id");
        String accounting_date = data.get("accounting_date");
        String period_name = data.get("period_name");
        String restoreBtn = data.get("restore");

        /*
         * UTILERIAS
         */
        SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_RMS, GlobalVariables.DB_USER_RMS,
                GlobalVariables.DB_PASSWORD_RMS);
        SQLUtil dbEbs = new SQLUtil(GlobalVariables.DB_HOST_EBS, GlobalVariables.DB_USER_EBS,
                GlobalVariables.DB_PASSWORD_EBS);
        SeleniumUtil u = new SeleniumUtil(new ChromeTest());

        // PASO 1
        addStep("Validar configuraci�n de Enable Flag en Y");
        System.out.println("PASO 1");

        testCase.addBoldTextEvidenceCurrentStep("Conectado a la BD RMS: ");
        testCase.addTextEvidenceCurrentStep("Host: " + dbRms.getHost());
        testCase.addTextEvidenceCurrentStep("User: " + dbRms.getUser());

        String enableY = String.format("select IF_ID, ATTRIBUTE1 TRAN_CODE, ABS(h.LOOKUP_CODE) REASON_CODE, ENABLED_FLAG \r\n"
                + "from wmuser.wm_fnd_lookup_values h\r\n"
                + "where lookup_type = 'REASON_CODE_TYPE'\r\n"
                + "and attribute1 = 22\r\n"
                + "and lookup_code between -200 and -93\r\n"
                + " and ENABLED_FLAG = 'Y' and IF_ID = 'RO8_MEX' order by lookup_code asc");
        System.out.println(enableY);

        SQLResult executeQueryEnableY = executeQuery(dbRms, enableY);

        boolean statusQueryEnableY = executeQueryEnableY.isEmpty();
        if (!statusQueryEnableY) {
            testCase.addQueryEvidenceCurrentStep(executeQueryEnableY);
        }

        // PASO 2
        addStep("Validar en la BD los ajustes de inventario a procesar "
				+ "con reference_3 y reference_9 en NULL.");
        System.out.println("PASO 2");

        String refNull = String.format("select ITEM, ITEM_DESC,REFERENCE_10 R10, REFERENCE_3 R3, REFERENCE_9 R9, TRAN_DATE, TRAN_CODE, TOTAL_COST, TOTAL_RETAIL, UNITS, VAT_RATE\r\n"
                + "from fem_fif_stg where tran_date >='%s'\r\n"
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
				+ " y inv_adj_user_id = " + inv_adj_user_id);



        // PASO 3
        addStep("Ejecutar el proceso runRO8_AJUST_MEX");
        System.out.println("PASO 3");

        JSONObject obj = new JSONObject(data.get("runRO8_AJUST_MEX"));
        Control_mInicio CM = new Control_mInicio(u, controlMUser, controlMPass);

        u.get(controlMServer);
        u.waitForLoadPage();
        CM.logOn(); // loguea
        u.hardWait(20);

        ControlM control = new ControlM(u, testCase, obj); // si es uno por uno, debes instanciar esta clase
        control.searchJob(); // busca el job
        control.accionSinOutput(); //Ejecuta el job

        // PASO 4
        addStep("Validar en la BD los ajustes de inventario con su poliza"
				+ "en el campo reference_3.");
        System.out.println("PASO 4");

        String reference3 = String.format("select ITEM, ITEM_DESC,REFERENCE_10 R10, REFERENCE_3 R3, REFERENCE_9 R9, TRAN_DATE, TRAN_CODE, TOTAL_COST, TOTAL_RETAIL, UNITS, \n" +
                "VAT_RATE \r\n"
                + "from fem_fif_stg \r\n"
                + "where tran_date = '%s'\r\n"
                + "and inv_adj_user_id = '%s'\r\n"
                + "and reference_3 is not null and reference_9 is not null", tran_date, inv_adj_user_id);
        System.out.println(reference3);

        SQLResult executeQueryReference3 = executeQuery(dbRms, reference3);

        boolean statusQueryReference3 = executeQueryReference3.isEmpty();
        if (!statusQueryReference3) {
            testCase.addQueryEvidenceCurrentStep(executeQueryReference3);
        }

        // PASO 5
        addStep("Ingresar a la BD EBS y validar los ajustes de inventario"
                + " con su poliza y cuentas contables.");
        System.out.println("PASO 5");

        testCase.addBoldTextEvidenceCurrentStep("Conectado a la BD EBS: ");
        testCase.addTextEvidenceCurrentStep("Host: " + dbEbs.getHost());
        testCase.addTextEvidenceCurrentStep("User: " + dbEbs.getUser());

        String refInterface = String.format("select h.status, h.group_id, ABS(h.ATTRIBUTE12) REASON_CODE, h.SEGMENT2, h.SEGMENT3,\r\n"
                + " h.SEGMENT4, h.ENTERED_DR, h.ENTERED_CR, h.REFERENCE1, \r\n"
                + "h.REFERENCE10 from gl.gl_interface h \r\n"
                + "where accounting_date = '%s'\r\n"
                + "and period_name = '%s'\r\n"
                + "and user_je_source_name = 'Retek' order by h.attribute12 desc", accounting_date, period_name);
        System.out.println(refInterface);

        SQLResult executeQueryInterface = executeQuery(dbEbs, refInterface);

        boolean statusQueryInterface = executeQueryInterface.isEmpty();
        if (!statusQueryInterface) {
            testCase.addQueryEvidenceCurrentStep(executeQueryInterface);
        }
        assertFalse(statusQueryInterface, "No se encontraron resultados en la tabla gl_interface");

        // PASO 6
        addStep("Ejecutar el proceso import_retek_ciclico_MEX");
        System.out.println("PASO 6");
        u.hardWait(20);

        JSONObject objImpRetek = new JSONObject(data.get("import_retek_ciclico_MEX"));

        u.get(controlMServer);
        u.waitForLoadPage();
        u.hardWait(15);
        u.click(By.xpath(restoreBtn));
        u.waitForLoadPage();

        ControlM ImpRetek = new ControlM(u, testCase, objImpRetek); // Clase instanciada para job import_retek_ciclico_MEX
        ImpRetek.searchJob(); // busca el job import_retek_ciclico_MEX

        String resultadoEjecucionImpRetek = ImpRetek.accionSinOutput(); // ejecuta el job import_retek_ciclico_MEX

        assertTrue(resultadoEjecucionImpRetek.equals("Wait for Event") || resultadoEjecucionImpRetek.equals("Ended NOT OK"),
                "El job import_retek_ciclico_MEX no se ejecuto correctamente");


        // PASO 7
        addStep("Ingresar a la BD EBS y validar que la informacion llegue a asientos EBS");
        System.out.println("PASO 7");

        String ebs = String.format("WITH FEM AS(\n" +
                "SELECT REFERENCE_3, reference_10, TOTAL_COST, UNITS FROM FEM_FIF_STG@FCRMSPRD_RMS100.US.ORACLE.COM\n" +
                "WHERE tran_date = '%s'\n" +
                "and TRAN_CODE IN (22)\n" +
                "and REASON_CODE IS NOT NULL)\n" +
                "select F.REFERENCE_3 FEM_FIF_REFERENCE, h.EXTERNAL_REFERENCE JOURNAL_REFERENCE, \n" +
                "    F.REFERENCE_10 REASON_CODE, F.TOTAL_COST, F.UNITS, h.RUNNING_TOTAL_DR, h.RUNNING_TOTAL_CR, h.NAME, h.DESCRIPTION \n" +
                "from FEM F, GL.gl_je_headers h, apps.gl_je_sources s\n" +
                "where F.REFERENCE_3 = h.EXTERNAL_REFERENCE\n" +
                "AND h.PERIOD_NAME = '%s'\n" +
                "and s.JE_SOURCE_NAME = h.je_source\n" +
                "and s.user_je_source_name = 'Retek' order by h.last_update_date desc\n",tran_date, period_name);
        System.out.println(ebs);

        SQLResult executeQueryebs = executeQuery(dbEbs, ebs);

        boolean statusQueryebs = executeQueryebs.isEmpty();
        if (!statusQueryebs) {
            testCase.addQueryEvidenceCurrentStep(executeQueryebs);
        }
        assertFalse(statusQueryInterface, "No se encontraron resultados en Asientos EBS");

    }


    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
        return "Contar con acceso a:\r\n"
                + "BASE DE DATOS RMS\r\n"
                + "BASE DE DATOS EBS\r\n"
                + "CONTROL M\r\n"
                + "\r\n"
                + "Contar con los objetos necesarios creados en R08\r\n"
                + "Todos los objetos de enable_flag en estado Y";
    }

    @Override
    public String setTestDescription() {
        return "Validar que al seleccionar los reason code registrado con"
                + " enable_flag con valor Y en R08, generando"
                + " el reporte satisfactoriamente";
    }

    @Override
    public String setTestDesigner() {
        return "ceciliaalejandral@hexaware.com";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_001_ValidarReasonCodesTodosEnEnableFlagYTest_test";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }

}
