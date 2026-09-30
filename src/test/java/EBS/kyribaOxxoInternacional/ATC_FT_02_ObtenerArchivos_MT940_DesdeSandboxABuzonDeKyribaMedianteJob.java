package EBS.kyribaOxxoInternacional;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import org.json.JSONObject;
import org.sikuli.script.FindFailed;
import org.testng.annotations.Test;

import com.jcraft.jsch.JSchException;

import EBS.util.GlobalVariables;
import EBS.util.SSHConnector;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_02_ObtenerArchivos_MT940_DesdeSandboxABuzonDeKyribaMedianteJob extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_02_ObtenerArchivos_MT940_DesdeSandboxABuzonDeKyribaMedianteJob_test(HashMap<String, String> data) throws JSchException, IOException, IllegalAccessException, FindFailed {
        /*
         * VARIABLES
         */
        String repositorioIncoming = data.get("repositorioIncoming");
        JSONObject obj = new JSONObject(data.get("job"));

        /*
         *FTP Kyriba Internacional
         */

        String ftpHost = GlobalVariables.FTP_KYRIBA_INTERNACIONAL_HOST;
        int ftpPort = GlobalVariables.FTP_KYRIBA_INTERNACIONAL_PORT;
        String ftpUser = GlobalVariables.FTP_KYRIBA_INTERNACIONAL_USER;
        String ftpPwd = GlobalVariables.FTP_KYRIBA_INTERNACIONAL_PSW;

        /*
         * Utilerías FTP
         */
        SSHConnector sshConnector = new SSHConnector();

        //PASO 1 Ingresar al repositorio de Kyriba EBS y validar que se encuentre vacío
        addStep("Ingresar al repositorio de Kyriba EBS y validar que se encuentre vacío");
        sshConnector.connect(ftpUser, ftpPwd, ftpHost, ftpPort);
        assert sshConnector.isConected();
        if (sshConnector.isConected()) {
            logger.info("Conectado al repositorio de Kyriba Internacional " + repositorioIncoming);
            testCase.addTextEvidenceCurrentStep("Conectado al repositorio de Kyriba Internacional " + repositorioIncoming);

            String dirIncoming = sshConnector.executeCommand("ls -l " + repositorioIncoming + " | grep '^-'");
            logger.info("Archivos en el directorio \n" + repositorioIncoming + "\n \n" + dirIncoming);
            testCase.addTextEvidenceCurrentStep("Archivos en el directorio \n" + repositorioIncoming);
            testCase.addTextEvidenceCurrentStep(dirIncoming);
            System.out.println("Archivos en el directorio \n" + repositorioIncoming + "\n \n" + dirIncoming);
            assertTrue(dirIncoming.isEmpty());
            testCase.addTextEvidenceCurrentStep("El directorio esta vacio");
            System.out.println("El directorio esta vacio");
        }
        sshConnector.disconnect();

        /*
         *Utilerias WEB
         */

        SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
        Control_mInicio CM = new Control_mInicio(u, GlobalVariables.CONTROL_M_USER, GlobalVariables.CONTROL_M_PWD);
        ControlM controlM = new ControlM(u, testCase, obj);

        //PASO 2 Ingresar a Control-M --> y ejecutar el job "XXPE_CE_KYRIBA_IN_SH_PERU"
        addStep("Ingresar a Control-M --> y ejecutar el job " + obj.getString("job"));
        testCase.addTextEvidenceCurrentStep(obj.getString("job"));
        u.get(GlobalVariables.CONTROL_M_SERVER);
        u.hardWait(80);
        u.waitForLoadPage();
        CM.logOn();
        testCase.addScreenShotCurrentStep(u, "control m inicio");
        boolean flag = controlM.searchJob();
        assertFalse(!flag, "No se encontro el job");

        System.out.println("Job Status " + controlM.getCurrentStatus());
        //Ejecucion
        String resultadoDelJon = controlM.executeJob();
        testCase.addTextEvidenceCurrentStep("Resultado de la ejecución " + resultadoDelJon);
        System.out.println("Resultado de la ejecución " + resultadoDelJon);

        //Valor del output
        testCase.addTextEvidenceCurrentStep("Output " + controlM.getOutput());
        System.out.println("Output " + controlM.getOutput());

        //Validacion del caso
        boolean testResult = true;
        if (resultadoDelJon.equals("Failure")) {
            testResult = false;
            testCase.addTextEvidenceCurrentStep("Fallo la ejecución del job ");
            System.out.println("Fallo la ejecución del job ");
        }

        assertTrue(testResult, "La ejecucion del job fue exitosa");
        controlM.closeViewpoint();


        //PASO 3 Ingresar al repositorio de Kyriba y validar que se encuentren los archivos MT940
        addStep("Ingresar al repositorio de Kyriba y validar que  se encuentren los archivos MT940");
        sshConnector.connect(ftpUser, ftpPwd, ftpHost, ftpPort);
        String dirIncomingPgpFiles;
        assert sshConnector.isConected();
        String[] pgpFiles = new String[0];
        if (sshConnector.isConected()) {
            logger.info("Conectado al repositorio de Kyriba Internacional " + repositorioIncoming);
            testCase.addTextEvidenceCurrentStep("Conectado al repositorio de Kyriba Internacional " + repositorioIncoming);

            String dirIncoming = sshConnector.executeCommand("ls -l " + repositorioIncoming + " | grep '^-'");
            System.out.println("Resultado " + dirIncoming);
            assertFalse(dirIncoming.isEmpty(), "El directorio " + dirIncoming + " no tiene archivos ");

            dirIncomingPgpFiles = sshConnector.executeCommand("find " + repositorioIncoming + " -type f -name '*.PGP' -maxdepth 1 -exec basename {}  \\;");
            assertFalse(dirIncomingPgpFiles.isEmpty(), "No hay archivos *PGP");
            String separador = "n";
            pgpFiles = dirIncomingPgpFiles.split(separador);

            testCase.addBoldTextEvidenceCurrentStep("El directorio tiene archivos\n");
            testCase.addTextEvidenceCurrentStep(Arrays.toString(pgpFiles));
            System.out.println("El directorio tiene archivos " + Arrays.toString(pgpFiles));
        }
        sshConnector.disconnect();

        //PASO 4 Validar que los archivos MT940 se encuentren encriptados (extensión .PGP)
        addStep("Validar que los archivos MT940 se encuentren encriptados (extensión .PGP)");
        System.out.println("Archivos encriptados \"*.PGP\" \n \n" + Arrays.toString(pgpFiles));
        testCase.addBoldTextEvidenceCurrentStep("Archivos encriptados \"*.PGP\" \n \n");
        testCase.addTextEvidenceCurrentStep(Arrays.toString(pgpFiles));

        u.close();

    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_02_ObtenerArchivos_MT940_DesdeSandboxABuzonDeKyribaMedianteJob_test";
    }

    @Override
    public String setTestDescription() {
        return "*Validar que se puedan obtener los archivos MT940 desde sandbox al buzón de Kyriba mediante el job 'XXPE_CE_KYRIBA_IN_SH_PERU'\n" +
                "*Los archivos MT940 se recibirán encriptados (.PGP)";
    }

    @Override
    public String setTestDesigner() {
        return "JesusH@hexaware.com";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }

    @Override
    public String setPrerequisites() {
        return "*Contar con acceso a Control-M\n" +
                "*Contar con acceso al repositorio de Kyriba\n" +
                "*Contar con archivos MT940 en sandbox";
    }

    @Override
    public void beforeTest() {

    }
}
