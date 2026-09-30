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

public class ATC_FT_002_Validar_Depuracion_de_TPECopiaRST extends BaseExecution {
    /**
     * Ejecucion de job TPECopiaRST
     * Desc:
     * Validar la ejecucion del job TPECopiaRST
     * @author Miguel Escamilla
     * @date 04/04/2024
     **/
    @Test(dataProvider = "data-provider")
    public void ATC_FT_002_Validar_Depuracion_de_TPECopiaRST(HashMap<String, String> data) throws Exception {


        //BD FCTPEQA_S1_MTY
        SQLUtil dbFCTPEQA_S1_MTY = new SQLUtil(GlobalVariables.DB_HOST_FCTPE, GlobalVariables.DB_USER_FCTPE,
                GlobalVariables.DB_PASSWORD_FCTPE);

        //BD FCWMREQA
        SQLUtil dbFCWMREQA = new SQLUtil(GlobalVariables.DB_HOST_FCWMREQA, GlobalVariables.DB_USER_FCWMREQA,
                GlobalVariables.DB_PASSWORD_FCWMREQA);

        String fechaCopiar = data.get("Fecha a Copiar");

        System.out.println(
                "***************************************** PASO 1 y 2 ************************************************");

        String queryTPE_FR_TRANSACTION = "SELECT COUNT (*) From TPEUSER.TPE_FR_TRANSACTION WHERE APPLICATION IN('MRC','MPC') AND TRUNC (CREATION_DATE) = TO_DATE ('" + fechaCopiar + "', 'DD/MM/YY')";

        SQLResult Execute_dbFCTPEQA_S1_MTY_Query = executeQuery(dbFCTPEQA_S1_MTY, queryTPE_FR_TRANSACTION);

        boolean conexionFCTPEQA_S1_MTY = true;
        conexionFCTPEQA_S1_MTY = Execute_dbFCTPEQA_S1_MTY_Query.isEmpty();

        addStep("Ingresar a la BD de FCTPEQA_S1_MTY");
        testCase.addTextEvidenceCurrentStep("Conexion: FCTPEQA_S1_MTY");
        testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCTPE);

        addStep("Se ingresa a la Base de datos FCTPEQA_S1_MTY para validar la información de la tabla TPE_FR_TRANSACTION");
        testCase.addQueryEvidenceCurrentStep(Execute_dbFCTPEQA_S1_MTY_Query);
        assertFalse(conexionFCTPEQA_S1_MTY, "La conexion no fue exitosa");

        if (!conexionFCTPEQA_S1_MTY){
            System.out.println("Conexion con la base de datos FCTPEQA_S1_MTY se realizo de manera exitosa");
        }
        System.out.println("Consulta: " + queryTPE_FR_TRANSACTION);

        System.out.println(
                "***************************************** PASO 3, 4, 5 y 6 ************************************************");

        String queryTPE_FR_TRANSACTION_RST = "SELECT * FROM (SELECT APPLICATION, ENTITY, FOLIO, CREATION_DATE, WM_STATUS_FT, WM_STATUS_WO01 FROM TPEREP.TPE_FR_TRANSACTION_RST WHERE APPLICATION IN('MRC','MPC') AND WM_STATUS_FT IN('L') " +
                "AND WM_STATUS_WO01 IN ('L') AND TRUNC (CREATION_DATE) = TO_DATE ('" + fechaCopiar + "', 'DD/MM/YY')) WHERE ROWNUM <= 5";

        String queryTPE_TABLAS_COPIA = "SELECT FECHA_COPIADA, FECHA_TRUNCADA FROM TPEREP.TPE_TABLAS_COPIA WHERE TIPO IN('RST') AND STATUS ='P' AND TABLA_STATUS = 'A'";

        SQLResult Execute_dbFCWMREQA_Query = executeQuery(dbFCWMREQA, queryTPE_FR_TRANSACTION_RST);

        boolean conexionFCWMREQA = true;
        conexionFCWMREQA = Execute_dbFCWMREQA_Query.isEmpty();

        addStep("Ingresar a la BD de FCWMREQA");
        testCase.addTextEvidenceCurrentStep("Conexion: FCWMREQA");
        testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMREQA);

        addStep("Se ingresa a la Base de datos FCWMREQA para validar la conexion");
        testCase.addQueryEvidenceCurrentStep(Execute_dbFCWMREQA_Query);
        assertFalse(!conexionFCWMREQA, "La conexion no fue exitosa");

        if (conexionFCWMREQA){
            System.out.println("Conexion con la base de datos FCWMREQA se realizo de manera exitosa");
        }
        System.out.println("Consulta: " + queryTPE_FR_TRANSACTION_RST);

        Execute_dbFCWMREQA_Query =  executeQuery(dbFCWMREQA, queryTPE_TABLAS_COPIA);
        testCase.addQueryEvidenceCurrentStep(Execute_dbFCWMREQA_Query);
        System.out.println("Consulta: " + queryTPE_TABLAS_COPIA);

        String fechaCopiada = Execute_dbFCWMREQA_Query.getData(0, "FECHA_COPIADA");
        String fechaParticion = "";

        // Definir el formato de entrada
        DateTimeFormatter formatoEntrada = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {
            // Parsear la fecha en formato de entrada
            LocalDate fecha = LocalDate.parse(fechaCopiada, formatoEntrada);

            // Sumar 60 días a la fecha
            LocalDate fechaSumada = fecha.minusDays(61);

            // Definir el formato de salida
            DateTimeFormatter formatoSalida = DateTimeFormatter.ofPattern("yyyyMMdd");

            // Formatear la fecha sumada en formato de salida
            fechaParticion= fechaSumada.format(formatoSalida);

            // Mostrar la fecha formateada
            System.out.println("Fecha particiones: " + fechaParticion);
        } catch (Exception e) {
            System.out.println("Error al procesar la fecha: " + e.getMessage());
        }
        String queryPARTITIONS = "SELECT TABLE_OWNER, TABLE_NAME, PARTITION_NAME FROM ALL_TAB_PARTITIONS WHERE TABLE_NAME='TPE_FR_TRANSACTION_RST' AND PARTITION_NAME='RST_TRANS_" + fechaParticion + "'";
        Execute_dbFCWMREQA_Query =  executeQuery(dbFCWMREQA, queryPARTITIONS);
        testCase.addQueryEvidenceCurrentStep(Execute_dbFCWMREQA_Query);
        System.out.println("Consulta: " + queryPARTITIONS);

        System.out.println(
                "***************************************** PASO 7, 8 y 9 ************************************************");

        addStep("Ejecutar el job runTPECopiaRST");
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
        u.hardWait(25);
        //ejecucion del job y obtencion de resultados de la ejecucion en control-M
        String resultado1 = job.executeJob();
        u.hardWait(20);
        System.out.println("Resultado de la ejecucion -> " + resultado1);
        System.out.println ("Valor de output :" +job.getOutput());

        u.hardWait(10);
        //resultado esperado para la finalizacion de ejecucion del job
        assertEquals(resultado1, "Ended OK");

        u.hardWait(3);

        // cuando hace el view, y se termina se cierra la pantalla de vista pero no el job, aqui se cierra la consulta q se hizo
        job.closeViewpoint();
        u.hardWait(15);
        addStep("Se ejecuta de manera exitosa el job runTPECopiaRST");

        u.close();

        System.out.println(
                "***************************************** PASO 10, 11, 12, 13 y 14 ************************************************");

        queryTPE_FR_TRANSACTION_RST = "SELECT COUNT (*) From TPEREP.TPE_FR_TRANSACTION_RST WHERE APPLICATION IN('MRC','MPC') AND TRUNC (CREATION_DATE) = TO_DATE ('" + fechaCopiar + "', 'DD/MM/YY')";

        String queryTPE_CONTROL_COPIA = "SELECT STATUS,TABLA, FECHAPROCESADA, TOTALDESTINO, TOTALFUENTE FROM TPEREP.TPE_CONTROL_COPIA WHERE TABLA IN('TPE_FR_TRANSACTION') AND TRUNC (FECHAPROCESADA) = TO_DATE ('" + fechaCopiar + "', 'DD/MM/YY')";

        Execute_dbFCWMREQA_Query = executeQuery(dbFCWMREQA, queryTPE_FR_TRANSACTION_RST);

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
        System.out.println("Consulta: " + queryTPE_FR_TRANSACTION_RST);

        Execute_dbFCWMREQA_Query =  executeQuery(dbFCWMREQA, queryTPE_TABLAS_COPIA);
        testCase.addQueryEvidenceCurrentStep(Execute_dbFCWMREQA_Query);
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
        queryPARTITIONS = "SELECT TABLE_OWNER, TABLE_NAME, PARTITION_NAME FROM ALL_TAB_PARTITIONS WHERE TABLE_NAME='TPE_FR_TRANSACTION_RST' AND PARTITION_NAME='RST_TRANS_" + fechaParticion + "'";
        Execute_dbFCWMREQA_Query =  executeQuery(dbFCWMREQA, queryPARTITIONS);
        testCase.addQueryEvidenceCurrentStep(Execute_dbFCWMREQA_Query);
        System.out.println("Consulta: " + queryPARTITIONS);

        Execute_dbFCWMREQA_Query = executeQuery(dbFCWMREQA, queryTPE_CONTROL_COPIA);
        testCase.addQueryEvidenceCurrentStep(Execute_dbFCWMREQA_Query);
        System.out.println("Consulta: " + queryTPE_CONTROL_COPIA);

        Execute_dbFCWMREQA_Query = executeQuery(dbFCWMREQA, queryTPE_FR_TRANSACTION_RST);
        testCase.addQueryEvidenceCurrentStep(Execute_dbFCWMREQA_Query);
        System.out.println("Consulta: " + queryTPE_FR_TRANSACTION_RST);
    }

    @Override
    public String setTestFullName() {
        return "002-Validar Depuracion de TPECopiaRST";
    }

    @Override
    public String setTestDescription() {
        return "Validar Depuracion de TPECopiaRST";
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
        return "El job runTPECopiaRST debe estar instalado y en estatus on Hold en QA, Acceso a la bases de datos FCWMREQA y FCTPEQA_S1_MTY";
    }

    @Override
    public void beforeTest() {

    }
}
