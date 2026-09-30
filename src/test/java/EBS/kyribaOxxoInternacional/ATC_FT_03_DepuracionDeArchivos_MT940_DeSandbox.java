package EBS.kyribaOxxoInternacional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.testng.annotations.Test;

import com.jcraft.jsch.JSchException;

import EBS.util.GlobalVariables;
import EBS.util.SSHConnector;
import modelo.BaseExecution;

public class ATC_FT_03_DepuracionDeArchivos_MT940_DeSandbox extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_03_DepuracionDeArchivos_MT940_DeSandbox_test(HashMap<String, String> data) throws JSchException, IOException, IllegalAccessException {

        /*
         * VARIABLES
         */
        String ebsUrl = GlobalVariables.getEbsInternacional;
        String ebsUserName = GlobalVariables.getJhernandezHexEbsIntUsr;
        String ebsPassword = GlobalVariables.getJhernandezHexEbsIntPwd;
        String responsabilidad = data.get("responsabilidad");
        String repositorioIncoming = data.get("repositorioIncoming");
        String repositorioIncomingProcesados = data.get("repositorioIncomingProcesados");

        /*
         *FTP Kyriba Internacional
         */

        String ftpHost = GlobalVariables.FTP_KYRIBA_INTERNACIONAL_HOST;
        int ftpPort = GlobalVariables.FTP_KYRIBA_INTERNACIONAL_PORT;
        String ftpUser = GlobalVariables.FTP_KYRIBA_INTERNACIONAL_USER;
        String ftpPwd = GlobalVariables.FTP_KYRIBA_INTERNACIONAL_PSW;

        /*
         * Utilerías
         */
        SSHConnector sshConnector = new SSHConnector();

        //PASO 1 Ingresar al repositorio de Kyriba EBS y validar que se encuentre vacío
        addStep("Ingresar al repositorio de Kyriba EBS y validar que se encuentre vacío");

        sshConnector.connect(ftpUser, ftpPwd, ftpHost, ftpPort);
        String file = "FEMSATMS.NC4.EXPORT.2023083008291442648.STAT.ST_01740_FC.MT940.PGP";//"FEMSATMS.NC4.EXPORT.2022110205065726230.STAT.ST_01647_FC.MT940.PGP";

        Properties p = System.getProperties();
        String dirProject = p.getProperty("user.dir");
        System.out.println("dirProject: "+dirProject);

        if (sshConnector.isConected()) {
            logger.info("Conectado al repositorio de Kyriba Internacional " + repositorioIncoming);
            assert sshConnector.isConected();
            //String dir = sshConnector.executeCommand("find /ebs_data_qa/incoming/kyriba/pe -name '*.txt' && find /ebs_data_qa/incoming/kyriba/pe -name '*.PGP'");
//            String dirIncoming = sshConnector.executeCommand("ls -l " + repositorioIncoming + " | grep '^-'");
            String dirIncomingPgpFiles = sshConnector.executeCommand("find " + repositorioIncoming + " -type f -name '" + file + "' -maxdepth 1 -exec basename {}  \\;");
            dirIncomingPgpFiles = dirIncomingPgpFiles.substring(0, dirIncomingPgpFiles.length() - 1);
            System.out.println("Archivos encriptados \"*.PGP\" \n" + dirIncomingPgpFiles);


//            if (dirIncomingPgpFiles.isEmpty() && dirIncomingTxtFiles.isEmpty()) {
//
//            } else {
//
//            }
//            String pgpFile = dirIncomingPgpFiles.substring(0, dirIncomingPgpFiles.length() - 1);
//            System.out.println(pgpFile);

//            assertTrue(dirIncoming.isEmpty());
//
//            String dirOutcoming = sshConnector.executeCommand("ls -l " + repositorioOutcoming + " | grep '^-'");
//            System.out.println("Resultado Outcoming " + dirOutcoming);
//            assertTrue(dirOutcoming.isEmpty());
        }
        sshConnector.disconnect();

    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_03_DepuracionDeArchivos_MT940_DeSandbox_test";
    }

    @Override
    public String setTestDescription() {
        return "Validar que al transportar los archivos MT940 desde sandbox hasta el buzón de Kyriba, los archivos serán depurados de sandbox automáticamente ";
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
