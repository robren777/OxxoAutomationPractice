package WEB.Migracion_FTP_SFTP_ATT;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.testng.annotations.AfterMethod; // Importación necesaria
import org.testng.annotations.Test;

import WM.util.GlobalVariables;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_017_EjecucionJobDepurarHistoricoTEF extends BaseExecution {

	// 1. Se declara SeleniumUtil a nivel de clase para que sea accesible por el AfterMethod
    private SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_017_EjecucionJobDepurarHistoricoTEF_test(HashMap<String, String> data) throws Exception {
        /** Descripción: Ejecución de Job Depurar Historico TEF
         
         ATC_FT_017_EjecucionJobDepurarHistoricoTEF
         
         Prerequisitos: 
         1. Acceso al aplicativo Control M.
         2. Credenciales vigentes (user/ps).
         3. Job de SFTP ordenado en el servidor correspondiente.
         @autor Alejandro Montalvo
         @date 07/04/2026
         */

        // 2. Inicialización de utilería usando la variable de clase
        u = new SeleniumUtil(new ChromeTest(), true);
        JSONObject jobData = new JSONObject(data.get("job"));
        Control_mInicio CM = new Control_mInicio(u, GlobalVariables.ControlM_USER, GlobalVariables.ControlM_PWD);

        // *************************** Paso 1: Login ***************************
        addStep("Acceder al aplicativo Control M y realizar LogOn");
        u.get(GlobalVariables.ControlM_URL);
        u.waitForLoadPage();     
        testCase.addScreenShotCurrentStep(u, "Se accede al portal Control M");
        CM.logOn();
        testCase.addScreenShotCurrentStep(u, "Login_ControlM");

        // *************************** Paso 2: Búsqueda ***************************
        addStep("Buscar el Job en el Viewpoint: " + jobData.getString("job"));
        ControlM control = new ControlM(u, testCase, jobData);
        WebDriver driver = u.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        boolean found = wait.until(d -> {
            try {
                return control.searchJob();
            } catch (FindFailed e) {
                return false; 
            }
        });
        
        assertTrue(found, "No se encontró el job solicitado en Control M.");
        testCase.addScreenShotCurrentStep(u, "Job_Encontrado");

        // *************************** Paso 3: Ejecución ***************************
        addStep("Ejecutar el Job y monitorear el estatus final");
        String resultadoEjecucion = control.executeJob();
        System.out.println("Resultado inicial de la ejecución -> " + resultadoEjecucion);

        int timeout = data.containsKey("timeout_job") ? Integer.parseInt(data.get("timeout_job")) : 30000;
        Thread.sleep(timeout);

        // *************************** Paso 4: Validación ***************************
        addStep("Validar que el Job termine con estatus 'Ended OK'");
        
        String statusEjecucion = control.getNewStatus();
        String output = control.getOutput();
        
        System.out.println("Status final detectado: " + statusEjecucion);
        testCase.addTextEvidenceCurrentStep("Status de ejecución: " + statusEjecucion);
        testCase.addTextEvidenceCurrentStep("Output resumido: " + (output.length() > 500 ? output.substring(0, 500) : output));

        boolean jobExitoso = statusEjecucion.equalsIgnoreCase("Ended OK") || statusEjecucion.equalsIgnoreCase("Wait for Event");
        
        testCase.addScreenShotCurrentStep(u, "Status_Final_Job");
        assertTrue(jobExitoso, "El Job no terminó correctamente. Estatus actual: " + statusEjecucion);
    }

    @AfterMethod
    public void afterMethod() {
        if (u != null) {
            u.quit();
            System.out.println("Cierre preventivo de navegador ejecutado en AfterMethod.");
        }
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_017_EjecucionJobDepurarHistoricoTEF";
    }

    @Override
    public String setTestDescription() {
        return "Validar la correcta ejecución de Jobs de depuracion para el proyecto AT&T en Control M.";
    }

    @Override
    public String setTestDesigner() {
        return "Alejandro Montalvo Sillas";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
        return "1. Acceso al aplicativo Control M.\n" +
               "2. Credenciales vigentes (user/ps).\n" +
               "3. Job de SFTP ordenado en el servidor correspondiente.";
    }
}