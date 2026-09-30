package EBS.kyribaOxxoInternacional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import com.jcraft.jsch.JSchException;

import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.SSHConnector;
import EBS.util.pageObjectModel.ebsInternational.EBSPaginaInicialInternacional;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_05_ProcesoDePreCargaKyribaEstadosDeCuentaMedianteElJob extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_05_ProcesoDePreCargaKyribaEstadosDeCuentaMedianteElJob_test(HashMap<String, String> data) throws JSchException, IOException, IllegalAccessException, FindFailed, UnsupportedFlavorException {
        /*
         * VARIABLES
         */
        String ebsUrl = GlobalVariables.getEbsInternacional;
        String userName = GlobalVariables.getOperacionesEbsIntUsr;
        String password = GlobalVariables.getOperacionesEbsIntPwd;
        String responsabilidad = data.get("responsabilidad");
        String repositorioIncoming = data.get("repositorioIncoming");
        String repositorioIncomingProcesados = data.get("repositorioIncomingProcesados");
        JSONObject obj = new JSONObject(data.get("job"));
        String ebsPattern = data.get("ebsPattern");
        HashMap<String, Pattern> ImgPatter = new HashMap<>();
        ArrayList<Pattern> patterns = new ArrayList<>();
        ArrayList<String> ListImage = new ArrayList<>();
        Pattern pathimg = new Pattern();


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

        /*
         * Utilerias
         */
        SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);


        //PASO 1 Validar que haya archivos MT940 en el repositorio de Kyriba
        addStep("Validar que haya archivos MT940 en el repositorio de Kyriba");
        sshConnector.connect(ftpUser, ftpPwd, ftpHost, ftpPort);
        if (sshConnector.isConected()) {
            logger.info("Conectado al repositorio de Kyriba Internacional " + repositorioIncoming);
            testCase.addTextEvidenceCurrentStep("Conectado al repositorio de Kyriba Internacional " + repositorioIncoming);
            assert sshConnector.isConected();

            String dirIncoming = sshConnector.executeCommand("ls -l " + repositorioIncoming + " | grep '^-'");
            logger.info("Archivos en el directorio \n" + repositorioIncoming + "\n \n" + dirIncoming);
            testCase.addTextEvidenceCurrentStep("Archivos en el directorio \n" + repositorioIncoming);
            testCase.addTextEvidenceCurrentStep(dirIncoming);
            System.out.println("Archivos en el directorio \n" + repositorioIncoming + "\n \n" + dirIncoming);
            assertFalse(dirIncoming.isEmpty());
            testCase.addTextEvidenceCurrentStep("El directorio tiene archivos ");
            System.out.println("El directorio tiene archivos ");
            String dirIncomingPgpFiles = sshConnector.executeCommand("find " + repositorioIncoming + " -type f -name '*.PGP' -maxdepth 1 -exec basename {}  \\;");
            String pgpFile = dirIncomingPgpFiles.substring(0, dirIncomingPgpFiles.length() - 1);
            testCase.addBoldTextEvidenceCurrentStep(pgpFile);
            System.out.println(pgpFile);
        }
        sshConnector.disconnect();

        /*
         *Utilerias WEB
         */

        Control_mInicio CM = new Control_mInicio(u, GlobalVariables.CONTROL_M_USER, GlobalVariables.CONTROL_M_PWD);
        ControlM controlM = new ControlM(u, testCase, obj);

        //PASO 2 Ingresar a Control-M --> y ejecutar el job "XXPE_CE_KYRIBA_IN_ECTA_SH_PERU"
        addStep("Ingresar a Control-M --> y ejecutar el job " + obj.getString("job"));
        testCase.addTextEvidenceCurrentStep(obj.getString("job"));
        u.get(GlobalVariables.CONTROL_M_SERVER);
        u.hardWait(60);
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
        u.close();


        //PASO 3 Ingresar al repositorio de Kyriba y validar que no se visualicen los archivos MT940
        addStep("Ingresar al repositorio de Kyriba y validar que no se visualicen los archivos MT940");

        sshConnector.connect(ftpUser, ftpPwd, ftpHost, ftpPort);
        String dirIncomingPgpFiles;
        String pgpFile = null;
        if (sshConnector.isConected()) {
            logger.info("Conectado al repositorio de Kyriba Internacional " + repositorioIncoming);
            testCase.addTextEvidenceCurrentStep("Conectado al repositorio de Kyriba Internacional " + repositorioIncoming);
            assert sshConnector.isConected();

            String dirIncoming = sshConnector.executeCommand("ls -l " + repositorioIncoming + " | grep '^-'");
            logger.info("Archivos en el directorio \n" + repositorioIncoming + "\n \n" + dirIncoming);
            testCase.addTextEvidenceCurrentStep("Archivos en el directorio \n" + repositorioIncoming);
            testCase.addTextEvidenceCurrentStep(dirIncoming);
            System.out.println("Archivos en el directorio \n" + repositorioIncoming + "\n \n" + dirIncoming);
            dirIncomingPgpFiles = sshConnector.executeCommand("find " + repositorioIncoming + " -type f -name '*.PGP' -maxdepth 1 -exec basename {}  \\;");
            pgpFile = dirIncomingPgpFiles.substring(0, dirIncomingPgpFiles.length() - 1);
            assertTrue(dirIncoming.isEmpty());
            testCase.addTextEvidenceCurrentStep("El directorio no tiene archivos\n");
            testCase.addBoldTextEvidenceCurrentStep(pgpFile);
            System.out.println("El directorio no tiene archivos " + pgpFile);
        }
        sshConnector.disconnect();

        //PASO 4 Validar que los archivos procesados se encuentren en la carpeta de "procesados"
        addStep("Validar que los archivos procesados se encuentren en la carpeta de 'procesados'");
        System.out.println("Archivos encriptados '*.PGP' \n" + pgpFile);
        assert pgpFile != null;
        sshConnector.connect(ftpUser, ftpPwd, ftpHost, ftpPort);
        if (sshConnector.isConected()) {
            logger.info("Conectado al repositorio de Kyriba Internacional " + repositorioIncomingProcesados);
            System.out.println("Conectado al repositorio de Kyriba Internacional " + repositorioIncomingProcesados);
            testCase.addTextEvidenceCurrentStep("Conectado al repositorio de Kyriba Internacional " + repositorioIncomingProcesados);
            assert sshConnector.isConected();

            String dirIncomingProcesados = sshConnector.executeCommand("find " + repositorioIncomingProcesados + " -type f -name '" + pgpFile + "' -maxdepth 1 -exec basename {} \\;");
            dirIncomingProcesados = dirIncomingProcesados.substring(0, dirIncomingProcesados.length() - 1);
            System.out.println("Archivo encontrado \n" + dirIncomingProcesados);

            logger.info("Archivos en el directorio \n" + repositorioIncomingProcesados + "\n" + dirIncomingProcesados);
            testCase.addTextEvidenceCurrentStep("Archivos en el directorio \n" + dirIncomingProcesados);
            testCase.addTextEvidenceCurrentStep(dirIncomingProcesados);
            System.out.println("Archivos en el directorio \n" + repositorioIncomingProcesados + "\n" + dirIncomingProcesados);
            String txtFile = dirIncomingProcesados + ".txt";
            assertEquals(dirIncomingProcesados, pgpFile);
            testCase.addTextEvidenceCurrentStep("El directorio tiene archivos\n");
            testCase.addBoldTextEvidenceCurrentStep(dirIncomingProcesados + "\n");
            //PASO Validar que el archivo MT940 procesado sea desencriptado (.txt)
            addStep("Validar que el archivo MT940 procesado sea desencriptado (.txt)");
            System.out.println("Archivo desencriptado a buscar \n" + txtFile);
            String dirIncomingProcesadosTxtFiles = sshConnector.executeCommand("find " + repositorioIncomingProcesados + " -type f -name '" + txtFile + "' -maxdepth 1 -exec basename {}  \\;");
            dirIncomingProcesadosTxtFiles = dirIncomingProcesadosTxtFiles.substring(0, dirIncomingProcesadosTxtFiles.length() - 1);
            assertEquals(dirIncomingProcesadosTxtFiles, txtFile);
            assertFalse(dirIncomingProcesados.isEmpty());
            testCase.addTextEvidenceCurrentStep("El directorio tiene archivos\n");
            testCase.addBoldTextEvidenceCurrentStep(dirIncomingProcesadosTxtFiles + "\n");
            System.out.println("El directorio tiene archivos \n" + dirIncomingProcesadosTxtFiles + "\n");
        }
        sshConnector.disconnect();

        /*
         *Utilerias WEB
         */

        SeleniumUtil IeTest = new SeleniumUtil(new IETest(), true);
        LoginTool inicio = new LoginTool(IeTest, testCase, ebsUrl, userName, password);
        EBSPaginaInicialInternacional paginaInicial = new EBSPaginaInicialInternacional(IeTest, testCase);
        SikuliUtil sk = new SikuliUtil();
        String pathImg = sk.PatternPath("EBS\\"+ebsPattern);
        File Folder = new File(pathImg);
        PatternUtil patternUtil = new PatternUtil(sk, IeTest, pathimg, ListImage, ImgPatter, patterns, Folder);

        //PASO 6 Ingresar a EBS INTERNACIONAL --> Dirigirse a la responsabilidad PEOXXO-CMXXXXX-SUPER USUARIO --> Ver --> Solicitudes --> Encontrar
        addStep("Ingresar a EBS INTERNACIONAL " + GlobalVariables.getEbsInternacional + " --> Dirigirse a la responsabilidad PEOXXO-CMXXXXX-SUPER USUARIO --> Ver --> Solicitudes --> Encontrar");
        logger.info("EBS URL: " + ebsUrl);
        System.out.println("EBS URL: " + ebsUrl);
        logger.info("User Name: " + userName);
        System.out.println("User Name: " + userName);
        inicio.loginInternacional();
        paginaInicial.clicEnResponsabilidad(responsabilidad);
        paginaInicial.peCmSuperUsuarioOtro.click();
        paginaInicial.peCmSuperUsuarioConcurrente.click();
        IeTest.hardWait(15);
        patternUtil.runApplet();
        IeTest.hardWait(10);
        patternUtil.runApplet();
        IeTest.hardWait(30);
        patternUtil.windowMaximize();
        //Boton Encontrar
        patternUtil.clickPattern("EBSInternacionalEncontrarBtn",30);
        //Boton visualizar registro
        patternUtil.clickPattern("visualizarRegistroInternacional", 30);
        patternUtil.clickPattern("EBSInternacionalPrograma", 30);
        String programa = "Nombre de Archivo: ";
        String registro = patternUtil.getTextDelNavegador("EBSInternacionalPrograma",programa);
        System.out.println(registro);
        testCase.addTextEvidenceCurrentStep(registro);
        u.close();

    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_05_ProcesoDePreCargaKyribaEstadosDeCuentaMedianteElJob_test";
    }

    @Override
    public String setTestDescription() {
        return "*Al ejecutar el job 'XXPE_CE_KYRIBA_IN_ECTA_SH_PERU' se ejecutará automáticamente el proceso 'XXPE - Kyriba pre-carga de estados de cuenta' \n" +
                "*Los archivos MT940 serán trasladados del repositorio de Kyriba a la carpeta de 'procesados' \n" +
                "*Al ejecutar el proceso 'XXPE - Kyriba pre-carga de estados de cuenta' los archivos MT940 serán desencriptados (.txt)";
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
                "*Contar con acceso al repositorio de Kyriba";
    }

    @Override
    public void beforeTest() {

    }
}
