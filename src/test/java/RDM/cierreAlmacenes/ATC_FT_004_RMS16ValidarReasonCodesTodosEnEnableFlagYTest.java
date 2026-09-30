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
* DESCRIPCI�N: Clase para ejecucion de Caso de Prueba ATC_FT_004_RMS16ValidarReasonCodesEnEnableFlagY.
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* -MTC_FT_004 RMS16 Validar Reason Codes En Enable Flag Y
* 
* PREREQUISITOS:
* - Contar con acceso a:"
				-BASE DE DATOS RMS16 FCMOMSIT
				-BASE DE DATOS EBS
				-CONTROL M
				-Todos los objetos de enable_flag en estado Y
* 
* INSUMOS:
* -Contar con los articulos necesarios creados en RMS tabla nb_fem_fif_stg
* 
* @author Cecilia Luna
* @date 12/Feb/2025
*/

 public class ATC_FT_004_RMS16ValidarReasonCodesTodosEnEnableFlagYTest extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_004_RMS16ValidarReasonCodesTodosEnEnableFlagYTest_test(HashMap<String, String> data) throws Exception{
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
        String accounting_date = data.get("accounting_date");
        String period_name = data.get("period_name");
        String restoreBtn = data.get("restore");
        String default_effective_date = data.get("default_effective_date");
        String PERIOD_NAME = data.get("PERIOD_NAME");

        /*
         * UTILERIAS
         */
        SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_RMS16, GlobalVariables.DB_USER_RMS16,
                GlobalVariables.DB_PASSWORD_RMS16);
        SQLUtil dbEbs = new SQLUtil(GlobalVariables.DB_HOST_EBS, GlobalVariables.DB_USER_EBS,
                GlobalVariables.DB_PASSWORD_EBS);
        SeleniumUtil u = new SeleniumUtil(new ChromeTest());

        // PASO 1
        addStep("Validar configuraci�n de Enable Flag en Y");
        System.out.println("PASO 1");

        testCase.addBoldTextEvidenceCurrentStep("Conectado a la BD RMS16: ");
        testCase.addTextEvidenceCurrentStep("Host: " + dbRms.getHost());
        testCase.addTextEvidenceCurrentStep("User: " + dbRms.getUser());

        String enableY = String.format("select IF_ID, ATTRIBUTE1 TRAN_CODE, ABS(h.LOOKUP_CODE) REASON_CODE, ENABLED_FLAG \n" +
                "                        from wmuser.wm_fnd_lookup_values h\n" +
                "                        where lookup_type = 'REASON_CODE_TYPE'\n" +
                "                        and attribute1 = 22\n" +
                "                        and ENABLED_FLAG = 'Y' and IF_ID = 'RO8_MX' order by lookup_code asc");
        System.out.println(enableY);

        SQLResult executeQueryEnableY = executeQuery(dbRms, enableY);

        boolean statusQueryEnableY = executeQueryEnableY.isEmpty();
        if (!statusQueryEnableY) {
            testCase.addQueryEvidenceCurrentStep(executeQueryEnableY);
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
        u.hardWait(70);

        ControlM control = new ControlM(u, testCase, obj); // si es uno por uno, debes instanciar esta clase
        control.searchJob(); // busca el job
        control.accionSinOutput();

        // PASO 4
        addStep("Validar Reference3 es Not Null");
        System.out.println("PASO 4");

        String reference3 = String.format("select ITEM, ITEM_DESC,REFERENCE_10 R10, REFERENCE_3 R3, REFERENCE_9 R9, TRAN_DATE, TRAN_CODE, TOTAL_COST, TOTAL_RETAIL, UNITS, \n" +
                "VAT_RATE \r\n"
                + "from rms_ext.nb_fem_fif_stg \r\n"
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
                + " con su póliza y cuentas contables.");
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
        assertFalse(statusQueryInterface, "No se encontraron resultados en EBS");

        // PASO 6
        addStep("Ejecutar el proceso import_retek_ciclico_MEX");
        System.out.println("PASO 6");
        u.hardWait(50);


        JSONObject objImpRetek = new JSONObject(data.get("import_retek_ciclico_MEX"));

        u.get(controlMServer);
        u.waitForLoadPage();
        u.hardWait(80);
        u.click(By.xpath(restoreBtn));
        u.waitForLoadPage();

        ControlM ImpRetek = new ControlM(u, testCase, objImpRetek); // Clase instanciada para job import_retek_ciclico_MEX
        ImpRetek.searchJob(); // busca el job import_retek_ciclico_MEX

        String resultadoEjecucionImpRetek = ImpRetek.accionSinOutput(); // ejecuta el job import_retek_ciclico_MEX

        assertTrue(resultadoEjecucionImpRetek.equals("Wait for Event") || resultadoEjecucionImpRetek.equals("Ended NOT OK"),
                "El job ciclo_ro8_new_MEX no se ejecuto correctamente");


        // PASO 7
        addStep("Ingresar a la BD EBS y validar que la información llegue a asientos");
        System.out.println("PASO 7");

        String ebs = String.format("select h.EXTERNAL_REFERENCE, h.RUNNING_TOTAL_DR, h.RUNNING_TOTAL_CR, h.NAME, h.DESCRIPTION \r\n" +
                "from GL.gl_je_headers h, apps.gl_je_sources s\n" +
                "where h.PERIOD_NAME = '%s' and h.default_effective_date = '%s'\r\n" +
                "and s.JE_SOURCE_NAME = h.je_source and s.user_je_source_name = 'Retek'\r\n" +
                "order by h.last_update_date desc", PERIOD_NAME, default_effective_date);
        System.out.println(ebs);

        SQLResult executeQueryebs = executeQuery(dbEbs, ebs);

        boolean statusQueryebs = executeQueryebs.isEmpty();
        if (!statusQueryebs) {
            testCase.addQueryEvidenceCurrentStep(executeQueryebs);
        }
        assertFalse(statusQueryebs, "No se encontraron resultados en EBS");


    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
        return "Contar con acceso a:\r\n"
                + "BASE DE DATOS FCMOMSIT (RMS16)c\r\n"
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
    public String setTestFullName() { return "ATC_FT_004_RMS16ValidarReasonCodesTodosEnEnableFlagYTest_test";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }

}
