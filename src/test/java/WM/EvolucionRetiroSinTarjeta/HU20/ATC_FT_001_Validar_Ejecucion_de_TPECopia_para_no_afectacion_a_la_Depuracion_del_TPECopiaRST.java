package WM.EvolucionRetiroSinTarjeta.HU20;

import modelo.BaseExecution;
import org.json.JSONObject;
import org.testng.annotations.Test;
import EBS.util.GlobalVariables;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import static org.testng.Assert.*;
import static org.testng.Assert.assertFalse;

public class ATC_FT_001_Validar_Ejecucion_de_TPECopia_para_no_afectacion_a_la_Depuracion_del_TPECopiaRST extends BaseExecution {
    /**
     * Ejecucion de job TPECopia
     * Desc:
     * Validar la ejecucion del job TPECopia para la afectacion de la Depuracion
     * @author Miguel Escamilla
     * @date 04/04/2024
     **/
    SeleniumUtil u = null;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_001_Validar_Ejecucion_de_TPECopia_para_no_afectacion_a_la_Depuracion_del_TPECopiaRST(HashMap<String, String> data) throws Exception {

        //BD FCWMREQA
        SQLUtil dbFCWMREQA = new SQLUtil(GlobalVariables.DB_HOST_FCWMREQA, GlobalVariables.DB_USER_FCWMREQA,
                GlobalVariables.DB_PASSWORD_FCWMREQA);


        System.out.println(
                "***************************************** PASO 1, 2 y 3 ************************************************");

        String queryTPE_TABLAS_COPIA = "SELECT FECHA_COPIADA, FECHA_TRUNCADA FROM TPEREP.TPE_TABLAS_COPIA WHERE TIPO IN('RST') AND STATUS ='P' AND TABLA_STATUS = 'A'";

        SQLResult Execute_dbFCWMREQA_Query = executeQuery(dbFCWMREQA, queryTPE_TABLAS_COPIA);

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
        System.out.println("Consulta: " + queryTPE_TABLAS_COPIA);

        String fechaCopiada = Execute_dbFCWMREQA_Query.getData(0, "FECHA_COPIADA");
        String fechaParticion = "";

        // Definir el formato de entrada
        DateTimeFormatter formatoEntrada = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {
            // Parsear la fecha en formato de entrada
            LocalDate fecha = LocalDate.parse(fechaCopiada, formatoEntrada);

            // Sumar 60 días a la fecha
            LocalDate fechaSumada = fecha.minusDays(60);

            // Definir el formato de salida
            DateTimeFormatter formatoSalida = DateTimeFormatter.ofPattern("yyyyMMdd");

            // Formatear la fecha sumada en formato de salida
            fechaParticion= fechaSumada.format(formatoSalida);

            // Mostrar la fecha formateada
            System.out.println("Fecha particiones: " + fechaParticion);
        } catch (Exception e) {
            System.out.println("Error al procesar la fecha: " + e.getMessage());
        }
        String queryUSER_TAB_PARTITIONS = "SELECT TABLE_OWNER, TABLE_NAME, PARTITION_NAME FROM ALL_TAB_PARTITIONS WHERE TABLE_NAME='TPE_FR_TRANSACTION_RST' AND PARTITION_NAME='RST_TRANS_" + fechaParticion + "'";

        Execute_dbFCWMREQA_Query = executeQuery(dbFCWMREQA, queryUSER_TAB_PARTITIONS);
        testCase.addQueryEvidenceCurrentStep(Execute_dbFCWMREQA_Query);
        System.out.println("Consulta: " + queryUSER_TAB_PARTITIONS);

        System.out.println(
                "***************************************** PASO 4 y 5 ************************************************");

        addStep("Ejecutar el job runTPECopia");
        System.setProperty("webdriver.chrome.driver","C:\\Chromedriver\\123.exe");
        //WebDriver driver=new ChromeDriver();

        SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);


        String pssDecrypted = PasswordUtil.decryptPassword(data.get("ps"));
        Control_mInicio cm = new Control_mInicio(u, data.get("user"), pssDecrypted);
        u.get(data.get("server"));

        //espera de carga de pagina y login
        u.waitForLoadPage();
        u.hardWait(20);
        u.waitForLoadPage();
        cm.logOn();

        //objeto json para pasar los datos para encontrar el job de control-M a utilizar
        JSONObject obj = new JSONObject(data.get("job"));
        ControlM job = new ControlM(u, testCase, obj);
        u.hardWait(15);
        boolean flag = job.searchJob();
        //validar que el job se encontro
        assertTrue(flag);
        u.hardWait(10);
        //ejecucion del job y obtencion de resultados de la ejecucion en control-M
        String resultado1 = job.executeJob();
        u.hardWait(180);
        System.out.println("Resultado de la ejecucion -> " + resultado1);
        System.out.println ("Valor de output :" + job.getOutput());

        u.hardWait(25);
        testCase.addTextEvidenceCurrentStep("Output: " + job.getOutput());

        u.hardWait(3);

        // cuando hace el view, y se termina se cierra la pantalla de vista pero no el job, aqui se cierra la consulta q se hizo
        job.closeViewpoint();
        u.hardWait(15);
        addStep("Se ejecuta el job runTPECopia");

        u.close();

        System.out.println(
                "***************************************** PASO 6, 7, 8 y 9 ************************************************");

        queryTPE_TABLAS_COPIA = "SELECT FECHA_COPIADA, FECHA_TRUNCADA FROM TPEREP.TPE_TABLAS_COPIA WHERE TIPO IN('RST') AND STATUS ='P' AND TABLA_STATUS = 'A'";

        Execute_dbFCWMREQA_Query = executeQuery(dbFCWMREQA, queryTPE_TABLAS_COPIA);

        conexionFCWMREQA = true;
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
        System.out.println("Consulta: " + queryTPE_TABLAS_COPIA);

        fechaCopiada = Execute_dbFCWMREQA_Query.getData(0, "FECHA_COPIADA");

        try {
            // Parsear la fecha en formato de entrada
            LocalDate fecha = LocalDate.parse(fechaCopiada, formatoEntrada);

            // Sumar 60 días a la fecha
            LocalDate fechaSumada = fecha.minusDays(60);

            // Definir el formato de salida
            DateTimeFormatter formatoSalida = DateTimeFormatter.ofPattern("yyyyMMdd");

            // Formatear la fecha sumada en formato de salida
            fechaParticion= fechaSumada.format(formatoSalida);

            // Mostrar la fecha formateada
            System.out.println("Fecha particiones: " + fechaParticion);
        } catch (Exception e) {
            System.out.println("Error al procesar la fecha: " + e.getMessage());
        }
        queryUSER_TAB_PARTITIONS = "SELECT TABLE_OWNER, TABLE_NAME, PARTITION_NAME FROM ALL_TAB_PARTITIONS WHERE TABLE_NAME='TPE_FR_TRANSACTION_RST' AND PARTITION_NAME='RST_TRANS_" + fechaParticion + "'";

        Execute_dbFCWMREQA_Query = executeQuery(dbFCWMREQA, queryUSER_TAB_PARTITIONS);
        testCase.addQueryEvidenceCurrentStep(Execute_dbFCWMREQA_Query);
        System.out.println("Consulta: " + queryUSER_TAB_PARTITIONS);

        queryTPE_TABLAS_COPIA = "SELECT * FROM (SELECT * FROM TPEREP.TPE_CONTROL_COPIA WHERE TABLA IN('TPE_FR_TRANSACTION') ORDER BY FECHAPROCESADA DESC) WHERE ROWNUM <= 5";
        Execute_dbFCWMREQA_Query = executeQuery(dbFCWMREQA, queryTPE_TABLAS_COPIA);
        testCase.addQueryEvidenceCurrentStep(Execute_dbFCWMREQA_Query);
        System.out.println("Consulta: " + queryTPE_TABLAS_COPIA);

    }

    @Override
    public String setTestFullName() {
        return "001-Validar Ejecucion de TPECopia para no afectacion a la Depuracion del TPECopiaRST.";
    }

    @Override
    public String setTestDescription() {
        return "Validar Ejecucion de TPECopia para no afectacion a la Depuracion del TPECopiaRST";
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
        return "El job runTPECopia debe estar instalado y en estatus on Hold en QA, Acceso a la bases de datos FCWMREQA";
    }

    @Override
    public void beforeTest() {

    }
}
